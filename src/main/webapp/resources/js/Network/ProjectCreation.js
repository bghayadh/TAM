var projectflag= {};
function getProject(projectId){
	 	
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if(projectflag[projectId] == 0){
    	$.ajax({
    		type: "GET",
    		contentType: "application/json; charset=utf-8",
    		url: getContext()+'/getProject',
    		data: {	
    			 "selectedProjectIdContext":projectId			
    		},
    		dataType: "json",
    		success: function (data) {
    			if (data != null) {
    				//if (data.searchResult != "Failed") {
						
    					  var physicalLayerList =  JSON.parse(data.physicalLayerList); // No need to parse again
		            	  var physicalLayerData =  JSON.parse(data.physicalLayerData);
		                 // console.log("physicalLayerListaaa "+physicalLayerList)
		                  
		                  
		                  ListManhole = physicalLayerList["Manhole"]
                		  ListManholeJunction  = physicalLayerList["Junction_Manhole"]
        				  ListHandhole = physicalLayerList["Handhole"]
						  ListHandholeJunction = physicalLayerList["Junction_Handhole"]
						  JunctionList = physicalLayerList["JunctionList"]
						  distribBoardList = physicalLayerList["Distribution_Board"]  
						  trenchList = physicalLayerList["Trench"]
						  trenchAuxiliary_Data = physicalLayerData["trench_Auxiliary"]  
						  ductList = physicalLayerList["duct"]
						  ductAuxiliary_Data = physicalLayerData["ductAuxiliary"] 
						  
						  fiberList = physicalLayerList["fiber"]
						  fiberTubes   = physicalLayerData["fiber_Tubes"]
						  fiberStrands = physicalLayerData["fiber_Strands"]
						  fiberAuxiliary_Data = physicalLayerData["fiber_Auxiliary"]
						  tubesAuxiliaries  = physicalLayerData["tubes_Auxiliaries"]
						  strandsAuxiliaries = physicalLayerData["strands_Auxiliaries"]
    					appendProjectElement(ListManhole,ListManholeJunction,ListHandhole,ListHandholeJunction,JunctionList,fiberList,fiberAuxiliary_Data,fiberTubes,tubesAuxiliaries,fiberStrands,strandsAuxiliaries,distribBoardList,trenchList,trenchAuxiliary_Data,ductList,ductAuxiliary_Data);
		                  
		               // in order to set all markers related to this project in case we click the checkbox of project
		                  if ($('#'+projectId+' .projectallElements:checked').length > 0) {
								mainPathCheckFilter($("#"+"FiberPath_f_"+projectId).children('input'));
								mainPathCheckFilter($("#"+"Trench_f_"+projectId).children('input'));
								
								
								  checkAllCheckboxes(projectId); 									
							
							}
		                  
		                  //allProjectElementsCheckFilter();
		                //these checkboxes related for cuurentphysicallayer and not project ex: fiberCheckAllBoq is checked when all fiber cable checked in currrent physical layer 
		      			$("#fiberCheckAllBoq").prop("checked",false);
		      			$("#tubeCheckAllBoq").prop("checked",false);
		      			$("#strandCheckAllBoq").prop("checked",false);
		      			$("#trenchCheckAllBoq").prop("checked",false);
		      			$("#ductCheckAllBoq").prop("checked",false);
							
    				    projectflag[projectId] = 1;	
    				
    				$("#loading").remove();	
    			}
    		},
    		error: function(result) {
    			alert("Error");
    		}
    		});
    }
	else {
		$("#loading").remove();	
	}
	

}


//this method is used to build project or append project element into current physical layer 
function appendProjectElement(ListManhole,ListManholeJunction,ListHandhole,ListHandholeJunction,JunctionList,fiberList,fiberAuxiliary_Data,fiberTubes,tubesAuxiliaries,fiberStrands,strandsAuxiliaries,distribBoardList,trenchList,trenchAuxiliary_Data,ductList,ductAuxiliary_Data){
	
	if(ListManhole!=null) {
		for(i=0;i<ListManhole.length;i++){
			window[""+ListManhole[i][0]]=[];
			window[""+ListManhole[i][0]]=ListManhole[i];
			allManholesID.push(ListManhole[i][0]);
	
			//Junction exists
			if(ListManhole[i][5]>0){
				str="<ul><li id='"+ListManhole[i][0]+"' class='MANHOLE' style='display:none;width:100px;'><input type='checkbox' class='Manhole checkFilter' class='filter' name='Element' ></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='"+getContext()+"/resources/NetworkImages/manholeRed.png'>  "+ListManhole[i][1]+" </span><ul><li id='"+ListManhole[i][0]+"_f' style='display:none;' class='junctionFolder'> <input type='checkbox' class='ManholeJct checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Junctions </span></li></ul></li></ul>";
				//$("#Manhole_f_CurrentPhysicalLayer").append(str);  	
				$("#Manhole_f_"+ListManhole[i][4]+"").append(str); 
				
				create_Marker_Click(ListManhole[i][0],ListManhole[i][1],ListManhole[i][2],ListManhole[i][3],markersManhole,markerClusterManhole,"Junction","");				
				ManholeCheckFilter(ListManhole[i][0]);
				markersManhole[ListManhole[i][0]].setMap(null);
	
			}
			else {
				str="<ul><li id='"+ListManhole[i][0]+"' class='MANHOLE' style='display:none;width:100px;'><input type='checkbox' class='Manhole' class='filter checkFilter' name='Element' ></input> <span class='TreeSpan' style='color:black;width:355px'><img src='"+getContext()+"/resources/NetworkImages/manholeRed.png'> "+ListManhole[i][1]+"</span></li></ul>";
				//$("#Manhole_f_CurrentPhysicalLayer").append(str);  	
				$("#Manhole_f_"+ListManhole[i][4]+"").append(str); 
				
				create_Marker_Click(ListManhole[i][0],ListManhole[i][1],ListManhole[i][2],ListManhole[i][3],markersManhole,markerClusterManhole,"No_Junction","");				
				ManholeCheckFilter(ListManhole[i][0]);	
				markersManhole[ListManhole[i][0]].setMap(null);
			}
			treeCollapseFolder("#" +ListManhole[i][0]+ " .folder","fast",".folder");
			
			
			
			$("#"+ListManhole[i][0]+" > .TreeSpan").contextmenu(function(){					
				selectedManIdContext=$(this).parent().attr('id');
				IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("Manhole_f_")[1];
				menuName=singleManhole;			
				openContext(selectedManIdContext,"",singleManhole,event);
		});	
		
			}
			//AllManholeCheckFilter();
			
		}
      
      
      if(ListManholeJunction!=null){
  		for(i=0;i<ListManholeJunction.length;i++){
  			var manholeJctId=ListManholeJunction[i][0];
  			manholeJctName=ListManholeJunction[i][1];
  				
  			window[""+manholeJctId]=[];
  			window[""+manholeJctId]=ListManholeJunction[i];

  			str="<ul><li id='"+ListManholeJunction[i][0]+"' class='JUNCTION' style='display:none;width:100px;'><input type='checkbox' class='JctManholes checkFilter' class='filter'></input><span  class='TreeSpan' style='color:black;width:195px'><img src='"+getContext()+"/resources/NetworkImages/junction.png'> "+manholeJctName+" </span></i></li></ul>";		
  			$("#"+ListManholeJunction[i][2]+"_f").append(str);
  			$("#Manhole_f_"+ListManholeJunction[i][2]+"").children('.folder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');					
  			
  			$( "#"+manholeJctId+" > .TreeSpan" ).bind("contextmenu",function(){
  				selectedManIdContext=$(this).parents().eq(4).attr('id');
  				selectedManholeJct=$(this).parent().attr('id');
  				IdNodeSelectedTemp=$(this).parents().eq(6).attr('id').split("Manhole_f_")[1];
  				openContext(selectedManIdContext,"",singleJunction,event)
  			});
  				
  			CreateJunctionClickEvent(manholeJctId,"Manhole");
  			}	
  			
  	}
      junctionCheckFilter("Manhole");
      
      
      if(ListHandhole!=null){
  		for(i=0;i<ListHandhole.length;i++){
  			window[""+ListHandhole[i][0]]=[];
  			window[""+ListHandhole[i][0]]=ListHandhole[i];
  			allHandholesID.push(ListHandhole[i][0]);	
  			
  		if(ListHandhole[i][5]>0){
  			str="<ul><li id='"+ListHandhole[i][0]+"' class='HANDHOLE' style='display:none;width:100px;'><input type='checkbox' class='Handhole checkFilter' class='filter' name='Element' ></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='"+getContext()+"/resources/NetworkImages/handholeYellow.png'>  "+ListHandhole[i][1]+" </span><ul><li id='"+ListHandhole[i][0]+"_f' style='display:none;' class='junctionFolder'> <input type='checkbox' class='HandholeJct checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Junctions </span></li></ul></li></ul>";
  			//$("#Handhole_f_CurrentPhysicalLayer").append(str);
  			$("#Handhole_f_"+ListHandhole[i][4]+"").append(str); 
  			
  			create_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"Junction","");										
  			HandholeCheckFilter(ListHandhole[i][0]);
  			markersHandhole[ListHandhole[i][0]].setMap(null);
  					
  		}
  		else {
  			str="<ul><li id='"+ListHandhole[i][0]+"' class='HANDHOLE' style='display:none;width:100px;'><input type='checkbox' class='Handhole' class='filter checkFilter' name='Element' ></input> <span class='TreeSpan' style='color:black;width:355px'><img src='"+getContext()+"/resources/NetworkImages/handholeYellow.png'> "+ListHandhole[i][1]+"</span></li></ul>";
  			//$("#Handhole_f_CurrentPhysicalLayer").append(str); 
  			$("#Handhole_f_"+ListHandhole[i][4]+"").append(str); 
  			
  			create_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"No_Junction","");
  			HandholeCheckFilter(ListHandhole[i][0]);
  			markersHandhole[ListHandhole[i][0]].setMap(null);
  		}
  		treeCollapseFolder("#" +ListHandhole[i][0]+ " .folder","fast",".folder");
  		}
  		//AllHandholeCheckFilter();				
  		}
      
      
      if(ListHandholeJunction!=null){
			for(i=0;i<ListHandholeJunction.length;i++){
			var handholeJctId=ListHandholeJunction[i][0];
			handholeJctName=ListHandholeJunction[i][1];
			window[""+handholeJctId]=[];
			window[""+handholeJctId]=ListHandholeJunction[i];
				
			str="<ul><li id='"+ListHandholeJunction[i][0]+"' class='JUNCTION_H' style='display:none;width:100px;'><input type='checkbox' class='JctHandholes checkFilter' class='filter'></input><span  class='TreeSpan' style='color:black;width:195px'><img src='"+getContext()+"/resources/NetworkImages/junction.png'> "+handholeJctName+" </span></i></li></ul>";						
			$("#"+ListHandholeJunction[i][2]+"_f").append(str);
				
			$( "#"+handholeJctId+" > .TreeSpan" ).bind("contextmenu",function(){
				selectedHandIdContext=$(this).parents().eq(4).attr('id');
				selectedHandholeJct=$(this).parent().attr('id');
				IdNodeSelectedTemp=$(this).parents().eq(6).attr('id').split("Handhole_f_")[1];
				openContext(selectedHandIdContext,"",singleHandholeJunction,event)
			});
				
			CreateJunctionClickEvent(handholeJctId,"Handhole");
			}	
		}
		junctionCheckFilter("Handhole");
		
		
		
		if(JunctionList!=null) {
			for(i=0;i<JunctionList.length;i++){
				window[""+JunctionList[i][0]]=[];
				window[""+JunctionList[i][0]]=JunctionList[i];
				allJunctionsID.push(JunctionList[i][0]);
		
				str="<ul><li id='"+JunctionList[i][0]+"' class='JUNCTION' style='display:none;width:100px;'><input type='checkbox' class='Junction' class='filter checkFilter' name='Element' ></input> <span class='TreeSpan' style='color:black;width:195px'><img src='"+getContext()+"/resources/NetworkImages/junction.png'> "+JunctionList[i][1]+"</span></li></ul>";
				//$("#Junction_f_CurrentPhysicalLayer").append(str); 
				$("#Junction_f_"+JunctionList[i][9]+"").append(str);
				
				create_Junction_Marker_Click(JunctionList[i][0],JunctionList[i][1],JunctionList[i][7],JunctionList[i][8],markersJunction,markerClusterJunction,"Junction","");	
				JunctionCheckFilter(JunctionList[i][0]);
				CreateJunctionClickEvent(JunctionList[i][0],"");
				markersJunction[JunctionList[i][0]].setMap(null);
				
				
				$("#"+JunctionList[i][0]+" > .TreeSpan").contextmenu(function(){				
					selectedJuncIdContext=$(this).parent().attr('id');
					IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("Junction_f_")[1];
					//menuName=singleJunction;	
					menuName= singleJunctionMenu;
					selectedManholeJct = selectedJuncIdContext;
					selectedManIdContext = "";
					selectedJct = selectedJuncIdContext;
					openContext(selectedJuncIdContext,"",singleJunctionMenu,event);
					

				});
			}		
				
		}
		if(junctionFlag==0) {
			AllJunctionCheckFilter();
		}
		
		
		
		
		
		if(fiberList!=null){
			for(i=0;i<fiberList.length;i++){
				allFiberCables.push(fiberList[i][4]);
				
				if(fiberList[i][21]=="backbone") {
				
					if(fiberList[i][11]>0){
						str="<ul><li id='"+fiberList[i][4]+"' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable checkFilter' class='filter'></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='"+getContext()+"/resources/NetworkImages/fiber.png'>  "+fiberList[i][13]+" / "+fiberList[i][4]+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes"+fiberList[i][4]+"' class='clearPoints'> </span><ul><li id='"+fiberList[i][4]+"_f' style='display:none;'> <input type='checkbox' class='FiberTubes checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Tubes <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes_f"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes_f"+fiberList[i][4]+"' class='clearPoints'></span></li></ul></li></ul>";						
					}
					else{
						str="<ul><li id='"+fiberList[i][4]+"' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable' class='filter checkFilter'></input> <span class='TreeSpan' style='color:black;width:355px'><img src='"+getContext()+"/resources/NetworkImages/fiber.png'> "+fiberList[i][13]+" / "+fiberList[i][4]+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes"+fiberList[i][4]+"' class='clearPoints'></span></li></ul>";
					}
					$("#FiberPath_backbone__"+fiberList[i][14]).append(str);
			
				}
				else if(fiberList[i][21]=="metro") {
					
					if(fiberList[i][11]>0){
						str="<ul><li id='"+fiberList[i][4]+"' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable checkFilter' class='filter'></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='"+getContext()+"/resources/NetworkImages/fiber.png'>  "+fiberList[i][13]+" / "+fiberList[i][4]+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes"+fiberList[i][4]+"' class='clearPoints'> </span><ul><li id='"+fiberList[i][4]+"_f' style='display:none;'> <input type='checkbox' class='FiberTubes checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Tubes <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes_f"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes_f"+fiberList[i][4]+"' class='clearPoints'></span></li></ul></li></ul>";						
					}
					else{
						str="<ul><li id='"+fiberList[i][4]+"' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable' class='filter checkFilter'></input> <span class='TreeSpan' style='color:black;width:355px'><img src='"+getContext()+"/resources/NetworkImages/fiber.png'> "+fiberList[i][13]+" / "+fiberList[i][4]+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes"+fiberList[i][4]+"' class='clearPoints'></span></li></ul>";
					}
					$("#FiberPath_metro__"+fiberList[i][14]).append(str);
				}
				else if(fiberList[i][21]=="access") {
					
					if(fiberList[i][11]>0){
						str="<ul><li id='"+fiberList[i][4]+"' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable checkFilter' class='filter'></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='"+getContext()+"/resources/NetworkImages/fiber.png'>  "+fiberList[i][13]+" / "+fiberList[i][4]+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes"+fiberList[i][4]+"' class='clearPoints'> </span><ul><li id='"+fiberList[i][4]+"_f' style='display:none;'> <input type='checkbox' class='FiberTubes checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Tubes <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes_f"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes_f"+fiberList[i][4]+"' class='clearPoints'></span></li></ul></li></ul>";						
					}
					else{
						str="<ul><li id='"+fiberList[i][4]+"' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable' class='filter checkFilter'></input> <span class='TreeSpan' style='color:black;width:355px'><img src='"+getContext()+"/resources/NetworkImages/fiber.png'> "+fiberList[i][13]+" / "+fiberList[i][4]+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes"+fiberList[i][4]+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes"+fiberList[i][4]+"' class='clearPoints'></span></li></ul>";
					}
					$("#FiberPath_access__"+fiberList[i][14]).append(str);
				}

		
				////////**********	Loading all FIBERS with auxiliaries data  ***********//////////

				var fiberId=fiberList[i][4];
				window[""+fiberList[i][4]]=[];
				window["mapPoints_"+fiberList[i][4]]=[];
				// array of fiber auxiliary names
				window["mapPointsNames_"+fiberList[i][4]]=[];
				
				//Case of wareHouse
				if(fiberList[i][5] !="null"){
					src = fiberList[i][5]+":" +fiberList[i][7]+":"+fiberList[i][6];		
				}
				else {
				 if (fiberList[i][6].split("_")[0]=="MH" || fiberList[i][6].split("_")[0]=="HH" ||fiberList[i][6].split("_")[0]=="DB" || fiberList[i][6].split("_")[0]=="CUST") {
						src  = fiberList[i][6]+":" +fiberList[i][7];	
					}
					else {
						src = fiberList[i][7];
					}
				}
				window["mapPointsNames_"+fiberList[i][4]].push(src);
				
				//Case of wareHouse
				if(fiberList[i][8] !="null"){
					dst = fiberList[i][8]+":" +fiberList[i][10]+":"+fiberList[i][9];		
				}
				else {
					if (fiberList[i][9].split("_")[0]=="MH" || fiberList[i][9].split("_")[0]=="HH" ||fiberList[i][9].split("_")[0]=="DB" || fiberList[i][9].split("_")[0]=="CUST") {
						dst  = fiberList[i][9]+":" +fiberList[i][10];	
					}
					else {
						dst = fiberList[i][10];
					}
				}
				
				window["mapPointsNames_"+fiberList[i][4]].push(dst);
				
				
				// fiber boundaries and cable points
				window[""+fiberList[i][4]]=fiberList[i];				
				window["bounds_"+fiberList[i][4]] = new google.maps.LatLngBounds();			
				var myLatLng = new google.maps.LatLng(fiberList[i][1],fiberList[i][0]);
				window["bounds_"+fiberList[i][4]].extend(myLatLng);
				window["mapPoints_"+fiberList[i][4]].push(myLatLng);

				myLatLng = new google.maps.LatLng(fiberList[i][3],fiberList[i][2]);
				window["bounds_"+fiberList[i][4]].extend(myLatLng);
				window["mapPoints_"+fiberList[i][4]].push(myLatLng);
					
				STEPS = 100;
				Create_FiberPath(fiberList[i][4]);
				pathCheckFilter(TargetFiber,"",fiberList[i][4],"20",fiberArray,allFiberCables,directionHashmap,routeDisplay,"TUBE",tubeArray,directionHashmapTube,allTubes);
				window['fiberCheckPoints_'+fiberList[i][4]] = "unchecked";
				window['fiberCheckRealPoints_'+fiberList[i][4]] = "unchecked";
				window['fiberCheckSequence_'+fiberList[i][4]] = "unchecked";
				// for seting fiber cable color
				fiberIdList.push(fiberList[i][4]);
				fiberOwnerList.push(fiberList[i][22]);
				window['FiberColor_'+fiberList[i][22]] = fiberList[i][23];

				$( "#"+fiberList[i][4]+" > .TreeSpan" ).bind("contextmenu",function(){
					IdNodeSelectedTemp = $(this).parents().eq(2).attr('id').split("__")[1];
					selectedFiberContext=$(this).parent().attr('id');
					openContext(selectedFiberContext,"fiber",singleFiber,event)
				});
				
				$( "#"+fiberList[i][4]+"_f > span" ).bind("contextmenu",function(){
					selectedFiberContext=$(this).parents().eq(2).attr('id');
					selectedTube=$(this).parent().attr('id');
					openContext(selectedTube,"",menuTubePath,event)
				});
				
				treeCollapseFolder("#" +fiberList[i][4]+ " .folder","fast",".folder");
				
				
				pathCheckFilter("initial_ul","parentFolderCheck","FiberPath_backbone__"+fiberList[i][14],"","","","","","","","","");
				pathCheckFilter("initial_ul","parentFolderCheck","FiberPath_metro__"+fiberList[i][14],"","","","","","","","","");
				pathCheckFilter("initial_ul","parentFolderCheck","FiberPath_access__"+fiberList[i][14],"","","","","","","","","");
				pathCheckFilter("initial_ul","parentFolderCheck","FiberPath_f_"+fiberList[i][14],"","","","","","","","","");
			}
			
		}
		
		
		// fiber auxiliary points
		if(fiberAuxiliary_Data!=null){
			for(i=0;i<fiberAuxiliary_Data.length;i++){ 
				myLatLng=new google.maps.LatLng(""+fiberAuxiliary_Data[i][1],""+fiberAuxiliary_Data[i][0]);	
				
				if(fiberAuxiliary_Data[i][3] =="") {
					auxPoint="";
				}
				//Case of wareHouse
				else if(fiberAuxiliary_Data[i][3] !="null"){
					auxPoint = fiberAuxiliary_Data[i][3]+":" +fiberAuxiliary_Data[i][5]+":"+fiberAuxiliary_Data[i][4];		
				}
				else {
					
					if (fiberAuxiliary_Data[i][4].split("_")[0]=="MH" || fiberAuxiliary_Data[i][4].split("_")[0]=="HH" ||fiberAuxiliary_Data[i][4].split("_")[0]=="DB") {
						auxPoint = fiberAuxiliary_Data[i][4]+":" +fiberAuxiliary_Data[i][5];	
					}
					else if (fiberAuxiliary_Data[i][5].includes("Auxiliary_Point")==true) {
						auxPoint = fiberAuxiliary_Data[i][7]+":"+fiberAuxiliary_Data[i][5];
					}
					else {
						auxPoint = fiberAuxiliary_Data[i][5];
					}
				}
				window["mapPointsNames_"+fiberAuxiliary_Data[i][6]].splice(window["mapPointsNames_"+fiberAuxiliary_Data[i][6]].length-1, 0,auxPoint);// insert at before last index which is the destination
				window["mapPoints_"+fiberAuxiliary_Data[i][6]].splice(window["mapPoints_"+fiberAuxiliary_Data[i][6]].length-1, 0,myLatLng);// insert at before last index which is the destination
				window["bounds_"+fiberAuxiliary_Data[i][6]].extend(myLatLng);				
			}
		}
		
		
		if(fiberTubes!=null){
			
			for(i=0;i<fiberTubes.length;i++){
				var tubeId=fiberTubes[i][0];
				var savedTubeId = fiberTubes[i][0];//initialized here and used in save tube function
				allTubes.push(tubeId);				
				tubeName=fiberTubes[i][13];
				var tubeNumber=fiberTubes[i][15];
				var tubeColor=fiberTubes[i][16];
				if(fiberTubes[i][11]>0){				
					str="<ul><li id='"+tubeId+"' class='TUBE' style='display:none;width:100px;'><input type='checkbox' class='FiberTube checkFilter' class='filter'></input> <span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:274px'> <span style='font-size:12px; font-weight:bold; transform: translateY(-4px); color:"+tubeColor+"'> "+tubeNumber+"</span> <img style='color: #08526D; margin-bottom:-3px; margin-left:-25px' src='"+getContext()+"/resources/NetworkImages/core.png'> "+tubeName+" / "+tubeId+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsStrands"+tubeId+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsStrands"+tubeId+"' class='clearPoints'> </span><ul><li id='"+tubeId+"_f' style='display:none;' class='strandsFolder'> <input type='checkbox' class='TubeStrands checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:236px'>Strands </span></li></ul></li></ul>";
				}
				else{
					
					str="<ul><li id='"+tubeId+"' class='TUBE' style='display:none;width:100px;'><input type='checkbox' class='FiberTube checkFilter' name='filter'></input> <span class='TreeSpan' style='color:black;width:274px'> <span style='font-size:12px; font-weight:bold; transform: translateY(-4px); color:"+tubeColor+"'> "+tubeNumber+"</span> <img style='color: #08526D; margin-bottom:-3px; margin-left:-25px' src='"+getContext()+"/resources/NetworkImages/core.png'> "+tubeName+" / "+tubeId+" <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsStrands"+tubeId+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsStrands"+tubeId+"' class='clearPoints'> </span></li></ul>";	
				}
				//console.log("the tube id is " +fiberTubes[i][12]);
				$("#"+fiberTubes[i][12]+"_f").append(str);
																			  
				pathCheckFilter(TargetTube,"parentFolderCheck",fiberTubes[i][12],"14",tubeArray,allTubes,directionHashmapTube,routeDisplayTube,"STRAND",strandArray,directionHashmapStrand,allStrands);
				pathCheckFilter(TargetTube,"",tubeId,"14",tubeArray,allTubes,directionHashmapTube,routeDisplayTube,"STRAND",strandArray,directionHashmapStrand,allStrands);

				window["Auxpts_Tubes"+tubeId]=[];	        	  	
				window[""+tubeId]=[];
				window["mapPoints_"+tubeId]=[];	 
				window["mapPointsNames_"+tubeId]=[];
				window["bounds_"+tubeId] = new google.maps.LatLngBounds();
		
				// tube auxiliary names and boundaries
				window[""+tubeId]=fiberTubes[i];
				
				if(fiberTubes[i][5] !="null"){
					tubeSrc = fiberTubes[i][5]+":" +fiberTubes[i][7]+":"+fiberTubes[i][6];		
				}
				else {
				 if (fiberTubes[i][6].split("_")[0]=="MH" || fiberTubes[i][6].split("_")[0]=="HH" ||fiberTubes[i][6].split("_")[0]=="DB" || fiberTubes[i][6].split("_")[0]=="CUST") {
					 tubeSrc  = fiberTubes[i][6]+":" +fiberTubes[i][7];	
					}
					else {
						tubeSrc = fiberTubes[i][7];
					}
				}
				window["mapPointsNames_"+tubeId].push(tubeSrc);
				
				if(fiberTubes[i][8] !="null"){
					tubeDst = fiberTubes[i][8]+":" +fiberTubes[i][10]+":"+fiberTubes[i][9];		
				}
				else {
					if (fiberTubes[i][9].split("_")[0]=="MH" || fiberTubes[i][9].split("_")[0]=="HH" || fiberTubes[i][9].split("_")[0]=="DB" || fiberTubes[i][9].split("_")[0]=="CUST") {
						tubeDst  = fiberTubes[i][9]+":" +fiberTubes[i][10];	
					}
					else {
						tubeDst = fiberTubes[i][10];
					}
				}
				
				window["mapPointsNames_"+tubeId].push(tubeDst);

				var myLatLng = new google.maps.LatLng(fiberTubes[i][2],fiberTubes[i][1]);
				window["bounds_"+tubeId].extend(myLatLng);
				window["mapPoints_"+tubeId].push(myLatLng);

				myLatLng = new google.maps.LatLng(fiberTubes[i][4],fiberTubes[i][3]);
				window["bounds_"+tubeId].extend(myLatLng);
				window["mapPoints_"+tubeId].push(myLatLng);					
										
				Create_FiberTubeClick_Event(tubeId);
				
				window['tubeCheckPoints_'+tubeId] = "unchecked";
				window['tubeCheckSequence_'+tubeId] = "unchecked";
				window['tubeCheckRealPoints_'+tubeId] = "unchecked";
		
						
				$( "#"+tubeId+" > span" ).bind("contextmenu",function(){
					selectedFiberContext=$(this).parents().eq(4).attr('id');
					selectedTube=$(this).parent().attr('id');						
					openContext(selectedTube,"tube",singleTube,event)
				});
				$( "#"+tubeId+"_f > span" ).bind("contextmenu",function(){
					selectedFiberContext=$(this).parents().eq(6).attr('id');
					selectedTube=$(this).parents().eq(2).attr('id');
					selectedStrandPath=$(this).parent().attr('id');
					openContext(selectedStrandPath,"",menuStrandPath,event)
				});
				
				treeCollapseFolder("#" +tubeId+ " .folder","fast",".folder");

			}		
		}
		
		if(typeof tubesAuxiliaries !== 'undefined'){
			for(i=0;i<tubesAuxiliaries.length;i++){ 
				var tubeId=tubesAuxiliaries[i][0];				   
				var lng=tubesAuxiliaries[i][1];
				var lat=tubesAuxiliaries[i][2];
				
				if(tubesAuxiliaries[i][3] !="null"){
					var tubeAuxPoint = tubesAuxiliaries[i][3]+":" +tubesAuxiliaries[i][5]+":"+tubesAuxiliaries[i][4];		
				}
				else {
					if (tubesAuxiliaries[i][4].split("_")[0]=="MH" || tubesAuxiliaries[i][4].split("_")[0]=="HH" ||tubesAuxiliaries[i][4].split("_")[0]=="DB") {
						var tubeAuxPoint = tubesAuxiliaries[i][4]+":" +tubesAuxiliaries[i][5];	
					}
					else if (tubesAuxiliaries[i][5].includes("Auxiliary_Point")==true) {
						var tubeAuxPoint = tubesAuxiliaries[i][8]+":"+tubesAuxiliaries[i][5];
					}
					else {
						var tubeAuxPoint = tubesAuxiliaries[i][5];
					}
				}
				
				var distanceTube=tubesAuxiliaries[i][6];
				var seqTubeSorting =tubesAuxiliaries[i][7];
				var drivingDistance =tubesAuxiliaries[i][9];
				var geoDistance =tubesAuxiliaries[i][10];
				
				window["Auxpts_Tubes"+tubeId].push({
					"tubeId":tubeId,
					"aux_Name" : tubeAuxPoint,								    
					"aux_Longitude" : lng,
					"aux_Latitude" : lat,
					"distance_From_Source":distanceTube,
					"seqSorting" : seqTubeSorting,
					"drivingDistance":drivingDistance,
					"geoDistance":geoDistance
				});
				
				
				myLatLng=new google.maps.LatLng(lat,lng);
				window["mapPointsNames_"+tubeId].splice(window["mapPointsNames_"+tubeId].length-1, 0,tubeAuxPoint);// insert at before last index which is the destination
				window["mapPoints_"+tubeId].splice(window["mapPoints_"+tubeId].length-1, 0,myLatLng);// insert at before last index which is the destination

			  }
		  }
		
		if(fiberStrands!=null){  
			for(i=0;i<fiberStrands.length;i++){

				var strandId=fiberStrands[i][0];
				var savedStrandId = fiberStrands[i][0];//initialized here and used in save strand function
				allStrands.push(fiberStrands[i][0]);
				var strandNumber=fiberStrands[i][15];
				var strandColor=fiberStrands[i][16];

				str="<ul><li id='"+fiberStrands[i][0]+"' class='STRAND' style='display:none;width:100px;'><input type='checkbox' class='FiberStrand checkFilter' class='filter'></input> <span  class='TreeSpan' style='color:black;width:195px'> <span style='font-size:12px; font-weight:bold; transform: translateY(-4px); color:"+strandColor+"'> "+strandNumber+"</span> <img style='margin-bottom:-3px; margin-left:-25px' src='"+getContext()+"/resources/NetworkImages/strand.png'> "+fiberStrands[i][13]+" / "+fiberStrands[i][0]+" </span></li></ul>";		
				$("#"+fiberStrands[i][11]+"_f").append(str);
				
				pathCheckFilter(TargetStrand,"parentFolderCheck",fiberStrands[i][11],"14",strandArray,allStrands,directionHashmapStrand,routeDisplayStrand,"","","","");
				pathCheckFilter(TargetStrand,"",fiberStrands[i][0],"14",strandArray,allStrands,directionHashmapStrand,routeDisplayStrand,"","","","");

				window["Auxpts_Strands"+fiberStrands[i][0]]=[];	        	  	
				window[""+fiberStrands[i][0]]=[];
				window["mapPoints_"+fiberStrands[i][0]]=[];	 
				window["mapPointsNames_"+fiberStrands[i][0]]=[];
				window["bounds_"+fiberStrands[i][0]] = new google.maps.LatLngBounds();		
				
				window[""+fiberStrands[i][0]]=fiberStrands[i];
				
				if(fiberStrands[i][5] !="null"){
					strandSrc = fiberStrands[i][5]+":" +fiberStrands[i][7]+":"+fiberStrands[i][6];		
				}
				else {
				 if (fiberStrands[i][6].split("_")[0]=="MH" || fiberStrands[i][6].split("_")[0]=="HH" ||fiberStrands[i][6].split("_")[0]=="DB" || fiberStrands[i][6].split("_")[0]=="CUST") {
					 strandSrc  = fiberStrands[i][6]+":" +fiberStrands[i][7];	
					}
					else {
						strandSrc = fiberStrands[i][7];
					}
				}
				window["mapPointsNames_"+fiberStrands[i][0]].push(strandSrc);
				
				if(fiberStrands[i][8] !="null"){
					strandDst = fiberStrands[i][8]+":" +fiberStrands[i][10]+":"+fiberStrands[i][9];		
				}
				else {
					if (fiberStrands[i][9].split("_")[0]=="MH" || fiberStrands[i][9].split("_")[0]=="HH" || fiberStrands[i][9].split("_")[0]=="DB" || fiberStrands[i][9].split("_")[0]=="CUST") {
						strandDst  = fiberStrands[i][9]+":" +fiberStrands[i][10];	
					}
					else {
						strandDst = fiberStrands[i][10];
					}
				}				
				window["mapPointsNames_"+fiberStrands[i][0]].push(strandDst);

				// array of each array boundaries 
				var myLatLng = new google.maps.LatLng(fiberStrands[i][2],fiberStrands[i][1]);
				window["bounds_"+fiberStrands[i][0]].extend(myLatLng);
				window["mapPoints_"+fiberStrands[i][0]].push(myLatLng);
				myLatLng = new google.maps.LatLng(fiberStrands[i][4],fiberStrands[i][3]);
				window["bounds_"+fiberStrands[i][0]].extend(myLatLng);
				window["mapPoints_"+fiberStrands[i][0]].push(myLatLng);
				Create_FiberStrand(fiberStrands[i][0]);
				window['strandCheckPoints_'+fiberStrands[i][0]] = "unchecked";
				window['strandCheckSequence_'+fiberStrands[i][0]] = "unchecked";
				window['strandCheckRealPoints_'+fiberStrands[i][0]] = "unchecked";
		

				$( "#"+fiberStrands[i][0]+" > span" ).bind("contextmenu",function(){
				    selectedFiberContext=$(this).parents().eq(4).attr('id');
				    selectedStrand=$(this).parent().attr('id');
			        selectedTube=$(this).parents().eq(2).attr('id');
			        selectedFiberMain=$(this).parents().eq(6).attr('id').split("_f")[0];
					openContext(selectedStrand,"strand",singleStrand,event)
				});
			}
		}

		if(strandsAuxiliaries){
			for(i=0;i<strandsAuxiliaries.length;i++){
				var strandId=strandsAuxiliaries[i][0];
				var lng=strandsAuxiliaries[i][1];
				var lat=strandsAuxiliaries[i][2];
				
				if(strandsAuxiliaries[i][3] !="null"){
					var strandAuxPoint = strandsAuxiliaries[i][3]+":" +strandsAuxiliaries[i][5]+":"+strandsAuxiliaries[i][4];		
				}
				else {
					if (strandsAuxiliaries[i][4].split("_")[0]=="MH" || strandsAuxiliaries[i][4].split("_")[0]=="HH" ||strandsAuxiliaries[i][4].split("_")[0]=="DB") {
						var strandAuxPoint = strandsAuxiliaries[i][4]+":" +strandsAuxiliaries[i][5];	
					}
					else if (strandsAuxiliaries[i][5].includes("Auxiliary_Point")==true) {
						var strandAuxPoint = strandsAuxiliaries[i][8]+":"+strandsAuxiliaries[i][5];
					}
					else {
						var strandAuxPoint = strandsAuxiliaries[i][5];
					}
				}
				
				var distanceStrand=strandsAuxiliaries[i][6];
				var seqStrandSorting =strandsAuxiliaries[i][7];
				var drivingDistance =strandsAuxiliaries[i][9];
				var geoDistance =strandsAuxiliaries[i][10];

				
				myLatLng=new google.maps.LatLng(lat,lng);
				window["mapPointsNames_"+strandId].splice(window["mapPointsNames_"+strandId].length-1, 0,strandAuxPoint);// insert at before last index which is the destination
				window["mapPoints_"+strandId].splice(window["mapPoints_"+strandId].length-1, 0,myLatLng);// insert at before last index which is the destination

				// strands auxiliary points
				window["Auxpts_Strands"+strandId].push({
					"strandId":strandId,            
					"aux_Name" : strandAuxPoint,								    
					"aux_Longitude" : lng,
					"aux_Latitude" : lat,
					"distance_From_Source":distanceStrand,
					"seqSorting" : seqStrandSorting,
					"drivingDistance":drivingDistance,
					"geoDistance":geoDistance
				});							
		   }
	   }
		
		
		
		
		
		
		if(distribBoardList!=null){
		for(i=0;i<distribBoardList.length;i++){
		    allDB.push(distribBoardList[i][0]);
			window[""+distribBoardList[i][0]]=[];
			window[""+distribBoardList[i][0]]=distribBoardList[i];
			
			if(distribBoardList[i][8]=="backbone") {
				str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/backboneDB.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
				//$("#DistributionBoard_backbone__CurrentPhysicalLayer").append(str);
				$("#DistributionBoard_backbone__"+distribBoardList[i][6]).append(str);
				
				create_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterBackboneDistBoard,"",boardCity);
				//markerClusterBackboneDistBoard.addMarker(markersDistBoard[""+distribBoardList[i][0]]);
				markerClusterBackboneDistBoard.removeMarker(distribBoardList[i][0]);
				clusterName=markerClusterBackboneDistBoard;
				
				/*
				HandholeCheckFilter(ListHandhole[i][0]);
      			markersHandhole[ListHandhole[i][0]].setMap(null);*/
			}
			else if(distribBoardList[i][8]=="metro") {
				str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/metroDb.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
				//$("#DistributionBoard_metro__CurrentPhysicalLayer").append(str);
				$("#DistributionBoard_metro__"+distribBoardList[i][6]).append(str);
				
				create_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterMetroDistBoard,"",boardCity);
				//markerClusterMetroDistBoard.addMarker(markersDistBoard[""+distribBoardList[i][0]]);
				markerClusterMetroDistBoard.removeMarker(distribBoardList[i][0]);
				clusterName=markerClusterMetroDistBoard;
			}
			else if(distribBoardList[i][8]=="access") {
				str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/accessDb.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
				//$("#DistributionBoard_access__CurrentPhysicalLayer").append(str);
				$("#DistributionBoard_access__"+distribBoardList[i][6]).append(str);
				
				create_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterAccessDistBoard,"",boardCity);
				//markerClusterAccessDistBoard.addMarker(markersDistBoard[""+distribBoardList[i][0]]);
				markerClusterAccessDistBoard.removeMarker(distribBoardList[i][0]);
				clusterName=markerClusterAccessDistBoard;
			}
			
			 DistributionBoardCheckFilter(distribBoardList[i][0],"",clusterName);
			
			$(".DistributionBoard > .TreeSpan").contextmenu(function(){
				menuName=singleDistBoard;
				IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("__")[1];
				selectedDistBoardContext=$(this).parents().attr('id');
				selectedDistBoardName=$(this).text();
				openContext(selectedDistBoardContext,selectedDistBoardName,singleDistBoard,event);
									
			});	
			
		}
	}
		
		
		if(trenchList!=null){
		for(i=0;i<trenchList.length;i++){
			var trenchId=trenchList[i][0];
			allTrenches.push(trenchId);
			window[""+trenchId]=[];
			window[""+trenchId]=trenchList[i];
			str="<ul><li id='"+trenchId+"'  class='Trench' style='display:none;width:100px;'><input type='checkbox' class='TRENCH checkFilter' unchecked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/trench.png' style='opacity:0.6'> "+trenchList[i][1]+" / "+trenchId+ " <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTrench"+trenchId+"' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTrench"+trenchId+"' class='clearPoints'> </span></li></ul>";
			//$("#Trench_f_CurrentPhysicalLayer").append(str);
			$("#Trench_f_"+trenchList[i][17]+"").append(str);

			window[""+trenchId]=[];
			window["mapPoints_"+trenchId]=[];
			
			// array of fiber auxiliary names
			window["mapPointsNames_"+trenchId]=[];	
			
			pathCheckFilter(TargetTrench,"",trenchId,"18",trenchArray,allTrenches,directionHashmapTrench,routeDisplayTrench,"Duct",ductArray,directionHashmapDuct,allDucts);

			
			if(trenchList[i][2] !="null"){
				trenchSrc = trenchList[i][2]+":" +trenchList[i][4]+":"+trenchList[i][3];		
			}
			else {
				if (trenchList[i][3].split("_")[0]=="MH" || trenchList[i][3].split("_")[0]=="HH" ||trenchList[i][3].split("_")[0]=="DB" || trenchList[i][3].split("_")[0]=="CUST") {
					trenchSrc  = trenchList[i][3]+":" +trenchList[i][4];	
				}
				else {
					trenchSrc = trenchList[i][4];
				}
			}				
			window["mapPointsNames_"+trenchId].push(trenchSrc);
			
			if(trenchList[i][5] !="null"){
				trenchDst = trenchList[i][5]+":" +trenchList[i][7]+":"+trenchList[i][6];		
			}
			else {
				if (trenchList[i][6].split("_")[0]=="MH" || trenchList[i][6].split("_")[0]=="HH" || trenchList[i][6].split("_")[0]=="DB" || trenchList[i][6].split("_")[0]=="CUST") {
					trenchDst  = trenchList[i][6]+":" +trenchList[i][7];	
				}
				else {
					trenchDst = trenchList[i][7];
				}
			}
			window["mapPointsNames_"+trenchId].push(trenchDst);
			
			// fiber boundaries and cable points
			window[""+trenchId]=trenchList[i];				
			window["bounds_"+trenchId] = new google.maps.LatLngBounds();			

			var myLatLng = new google.maps.LatLng(trenchList[i][8],trenchList[i][9]);
			window["bounds_"+trenchId].extend(myLatLng);
			window["mapPoints_"+trenchId].push(myLatLng);

			myLatLng = new google.maps.LatLng(trenchList[i][11],trenchList[i][10]);
			window["bounds_"+trenchId].extend(myLatLng);
			window["mapPoints_"+trenchId].push(myLatLng);
			Create_Trench(trenchId);
			
			window['trenchCheckPoints_'+trenchId] = "unchecked";
			window['trenchCheckSequence_'+trenchId] = "unchecked";
			window['trenchCheckRealPoints_'+trenchId] = "unchecked";
	
			$("#"+trenchId+"_f > .TreeSpan").contextmenu(function(){	
				menuName=menuDucts;
				selectedTrenchContext=$(this).parents().eq(2).attr('id');
				IdNodeSelectedTemp=$(this).parents().eq(4).attr('id').split("Trench_f_")[1];					
				openContext(selectedTrenchContext,"",menuDucts,event);
			});		
			$( "#"+trenchId+" > .TreeSpan" ).bind("contextmenu",function(){
				IdNodeSelectedTemp = $(this).parents().eq(2).attr('id').split("Trench_f_")[1];
				selectedTrenchContext=$(this).parent().attr('id');
				openContext(selectedTrenchContext,"trench",singleTrench,event)
			});
			
			pathCheckFilter("initial_ul","parentFolderCheck","Trench_f_"+trenchList[i][17],"","","","","","","","","");
		}	
		
	}
		
		
		
		if(trenchAuxiliary_Data!=null){
		for(i=0;i<trenchAuxiliary_Data.length;i++){ 
			
			trenchId=trenchAuxiliary_Data[i][5];
			if(trenchAuxiliary_Data[i][2] !="null"){
				var trenchAuxPoint = trenchAuxiliary_Data[i][2]+":" +trenchAuxiliary_Data[i][4]+":"+trenchAuxiliary_Data[i][3];		
			}
			else {
				if (trenchAuxiliary_Data[i][3].split("_")[0]=="MH" || trenchAuxiliary_Data[i][3].split("_")[0]=="HH" ||trenchAuxiliary_Data[i][3].split("_")[0]=="DB") {
					var trenchAuxPoint = trenchAuxiliary_Data[i][3]+":" +trenchAuxiliary_Data[i][4];	
				}
				else if (trenchAuxiliary_Data[i][4].includes("Auxiliary_Point")==true) {
					var trenchAuxPoint = trenchAuxiliary_Data[i][8]+":"+trenchAuxiliary_Data[i][4];
				}
				else {
					var trenchAuxPoint = trenchAuxiliary_Data[i][4];
				}
			}
			
			if(window["mapPoints_"+trenchId]){
				myLatLng=new google.maps.LatLng(""+trenchAuxiliary_Data[i][1],""+trenchAuxiliary_Data[i][0]);				
				window["mapPoints_"+trenchId].splice(window["mapPoints_"+trenchId].length-1, 0,myLatLng);// insert at before last index which is the destination
				window["mapPointsNames_"+trenchId].splice(window["mapPointsNames_"+trenchId].length-1, 0,trenchAuxPoint);// insert at before last index which is the destination
				window["bounds_"+trenchId].extend(myLatLng);
			}
		}
}
		
		if(typeof ductList !=='undefined'){	
			for(i=0;i<ductList.length;i++){
				var ductId=ductList[i][0];
				allDucts.push(ductList[i][0]);
				window[""+ductList[i][0]]=[];
				window[""+ductList[i][0]]=ductList[i];
				
				if($("#"+ductList[i][18]).find('> ul > li > .folder').length==0){
					$("<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span>").insertBefore("#"+ductList[i][18]+"> .TreeSpan");
					str="<ul><li id='"+ductList[i][18]+"_f' style='display:none;' class='ductsFolder'> <input type='checkbox' class='trenchDucts checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:236px'> Ducts <img src='"+getContext()+"/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTrench"+window[""+ductList[i][0]][18]+"_f' class='pushPoints'> <img src='"+getContext()+"/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTrench"+window[""+ductList[i][0]][18]+"_f' class='clearPoints'></span></li></ul>";
					$("#"+ductList[i][18]).append(str);
					$("#"+ductList[i][18]+"_f > .TreeSpan").contextmenu(function(){	
						menuName=menuDucts;
						selectedTrenchContext=$(this).parents().eq(2).attr('id');
						IdNodeSelectedTemp=$(this).parents().eq(4).attr('id').split("Trench_f_")[1];					
						openContext("","",menuDucts,event);
					});
					
					pathCheckFilter(TargetDuct,"parentFolderCheck",ductList[i][18],"14",ductArray,allDucts,directionHashmapDuct,routeDisplayDuct,"","","","");
				}
						
				str="<ul><li id='"+ductList[i][0]+"'  class='Duct' style='display:none;width:100px;'><input type='checkbox' class='DUCT checkFilter' unchecked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/duct.png' style='opacity:0.6'> "+ductList[i][1]+" / "+ductList[i][0]+" </span></li></ul>";	
				$("#"+window[""+ductList[i][0]][18]+"_f").append(str);
				window["mapPoints_"+ductList[i][0]]=[];					
				window["mapPointsNames_"+ductList[i][0]]=[];	
				
				if(ductList[i][2] !="null"){
					ductSrc = ductList[i][2]+":" +ductList[i][4]+":"+ductList[i][3];		
				}
				else {
					if (ductList[i][3].split("_")[0]=="MH" || ductList[i][3].split("_")[0]=="HH" || ductList[i][3].split("_")[0]=="DB" || ductList[i][3].split("_")[0]=="CUST") {
						ductSrc  = ductList[i][3]+":" +ductList[i][4];	
					}
					else {
						ductSrc = ductList[i][4];
					}
				}	
				window["mapPointsNames_"+ductList[i][0]].push(ductSrc);
				
				if(ductList[i][5] !="null"){
					ductDst = ductList[i][5]+":" +ductList[i][7]+":"+ductList[i][6];		
				}
				else {
					if (ductList[i][6].split("_")[0]=="MH" || ductList[i][6].split("_")[0]=="HH" || ductList[i][6].split("_")[0]=="DB" || ductList[i][6].split("_")[0]=="CUST") {
						ductDst  = ductList[i][6]+":" +ductList[i][7];	
					}
					else {
						ductDst = ductList[i][7];
					}
				}
				window["mapPointsNames_"+ductList[i][0]].push(ductDst);
						
						
				window["bounds_"+ductList[i][0]] = new google.maps.LatLngBounds();			
				var myLatLng = new google.maps.LatLng(ductList[i][8],ductList[i][9]);
				window["bounds_"+ductList[i][0]].extend(myLatLng);
				window["mapPoints_"+ductList[i][0]].push(myLatLng);
		
				myLatLng = new google.maps.LatLng(ductList[i][11],ductList[i][10]);
				window["bounds_"+ductList[i][0]].extend(myLatLng);
				window["mapPoints_"+ductList[i][0]].push(myLatLng);
				Create_Duct(ductList[i][0]);
				
				window['ductCheckPoints_'+ductList[i][0]] = "unchecked";
				window['ductCheckSequence_'+ductList[i][0]] = "unchecked";
				window['ductCheckRealPoints_'+ductList[i][0]] = "unchecked";
				
				pathCheckFilter(TargetDuct,"",ductList[i][0],"19",ductArray,allDucts,directionHashmapDuct,routeDisplayDuct,"","","","");
						
				$("#"+ductList[i][0]+" > .TreeSpan").bind("contextmenu",function(){
					menuName=menuSingleDuct;
					selectedTrenchContext=$(this).parents().eq(2).attr('id').split("_f")[0];
					selectedDuctContext=$(this).parent().attr('id');
					IdNodeSelectedTemp=$(this).parents().eq(6).attr('id').split("Trench_f_")[1];	
					openContext(selectedDuctContext,"duct",menuSingleDuct,event);
							
				});
				
				treeCollapseFolder("#" +ductList[i][18]+ " .folder","fast",".folder");
			   }
			}

				if(ductAuxiliary_Data!=null){
					for(i=0;i<ductAuxiliary_Data.length;i++){ 
						
						if(ductAuxiliary_Data[i][2] !="null"){
							var ductAuxPoint = ductAuxiliary_Data[i][2]+":" +ductAuxiliary_Data[i][4]+":"+ductAuxiliary_Data[i][3];		
						}
						else {
							if (ductAuxiliary_Data[i][3].split("_")[0]=="MH" || ductAuxiliary_Data[i][3].split("_")[0]=="HH" ||ductAuxiliary_Data[i][3].split("_")[0]=="DB") {
								var ductAuxPoint = ductAuxiliary_Data[i][3]+":" +ductAuxiliary_Data[i][4];	
							}
							else if (ductAuxiliary_Data[i][4].includes("Auxiliary_Point")==true) {
								var ductAuxPoint = ductAuxiliary_Data[i][8]+":"+ductAuxiliary_Data[i][4];
							}
							else {
								var ductAuxPoint = ductAuxiliary_Data[i][4];
							}
						}
						if(window["mapPoints_"+ductAuxiliary_Data[i][5]]){
							myLatLng=new google.maps.LatLng(""+ductAuxiliary_Data[i][1],""+ductAuxiliary_Data[i][0]);				
							window["mapPoints_"+ductAuxiliary_Data[i][5]].splice(window["mapPoints_"+ductAuxiliary_Data[i][5]].length-1, 0,myLatLng);// insert at before last index which is the destination
							window["mapPointsNames_"+ductAuxiliary_Data[i][5]].splice(window["mapPointsNames_"+ductAuxiliary_Data[i][5]].length-1, 0,ductAuxPoint);// insert at before last index which is the destination
			
							window["bounds_"+ductAuxiliary_Data[i][5]].extend(myLatLng);
						}
					}
				}
				
			
				
      
      
      
      
  		$(".HANDHOLE > .TreeSpan").contextmenu(function(){
      					
  					selectedHandIdContext=$(this).parent().attr('id');
  					IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("Handhole_f_")[1];
  					menuName=singleHandhole;			
  					openContext(selectedHandIdContext,"",singleHandhole,event);
  		});
      
      
      
      
      
      $('.TreeSpan').css("display", "inline");
      MouseHoveringSpans(null);	
      
      
      
      ListManhole =[];
      ListManholeJunction =[];
      ListHandhole =[];
      ListHandholeJunction =[];
      JunctionList =[];
      fiberList =[];
      fiberAuxiliary_Data =[];
      fiberTubes =[];
      tubesAuxiliaries =[];
      fiberStrands =[];
      strandsAuxiliaries =[];
      distribBoardList =[];
      trenchList =[];
      trenchAuxiliary_Data =[];
      ductList =[];
      ductAuxiliary_Data =[];
}

function checkAllCheckboxes(projectID) {
	//check all element checkboxes related to that project 
    $("#"+projectID).find("input[type='checkbox']").prop('checked', true);
    
    //set handhole marker
    $("#"+projectID).find(".Handhole:checked" ).each(function(){

		id=$(this).parent().attr('id');
		if(markersHandhole[id].getMap()==null){
			markersHandhole[id].setMap(map);			
			markerClusterHandhole.addMarker(markersHandhole[id]);
		}				
    });
    
    //set manhole marker
    $("#"+projectID).find(".Manhole:checked" ).each(function(){
		id=$(this).parent().attr('id');
		if(markersManhole[id].getMap()==null){
			markersManhole[id].setMap(map);			
			markerClusterManhole.addMarker(markersManhole[id]);
		}		
	});
    //set junction marker
    $("#"+projectID).find(".Junction:checked" ).each(function(){
		id=$(this).parent().attr('id');
		if(markersJunction[id].getMap()==null){
			markersJunction[id].setMap(map);			
			markerClusterJunction.addMarker(markersJunction[id]);
		}		
	});	
    //set backbone distboards marker
    $("#DistributionBoard_backbone__"+projectID).find(".DistBoard:checked" ).each(function(){
		id=$(this).parent().attr('id');
		if(markersDistBoard[id].getMap()==null){
			markersDistBoard[id].setMap(map);			
			markerClusterBackboneDistBoard.addMarker(markersDistBoard[id]);
		}		
	});	
    //set metro distboards marker
    $("#DistributionBoard_metro__"+projectID).find(".DistBoard:checked" ).each(function(){
		id=$(this).parent().attr('id');
		if(markersDistBoard[id].getMap()==null){
			markersDistBoard[id].setMap(map);			
			markerClusterMetroDistBoard.addMarker(markersDistBoard[id]);
		}		
	});	
    //set metro access marker
    $("#DistributionBoard_access__"+projectID).find(".DistBoard:checked" ).each(function(){
		id=$(this).parent().attr('id');
		if(markersDistBoard[id].getMap()==null){
			markersDistBoard[id].setMap(map);			
			markerClusterAccessDistBoard.addMarker(markersDistBoard[id]);
		}		
	});	
    
}

function allProjectElementsCheckFilter(){
	$(".projectallElements").bind("change",function() {
		var start=Date.now();
		
			
		if ($(this).is(':checked')){
			
			$(this).parent().find('ul > li').each(function(){				
				
					$(this).children('input:checkbox').prop('checked', true);
					
						if($(this).hasClass('FIBER')){						
							singleFiberCheckFilter($(this).children('input:checkbox'))				
					}
						else if($(this).hasClass('TUBE')){
							singleTubeCheckFilter($(this).children('input:checkbox'));
						}
						else if($(this).hasClass('STRAND')){
							singleStrandCheckFilter($(this).children('input:checkbox'));
						}
																				
					if($(this).children('input:checkbox').hasClass('Manhole')){
						id=$(this).attr('id');
						if(markersManhole[id].getMap()==null){
							markersManhole[id].setMap(map);			
							markerClusterManhole.addMarker(markersManhole[id]);						
							//$("#manholeCheckAllBoq").prop("checked",true);
						}
					}					
					if($(this).children('input:checkbox').hasClass('Handhole')){
						id=$(this).attr('id');
						if(markersHandhole[id].getMap()==null){
							markersHandhole[id].setMap(map);			
							markerClusterHandhole.addMarker(markersHandhole[id]);
							//$("#handholeCheckAllBoq").prop("checked",true);
						}
					}
					if($(this).children('input:checkbox').hasClass('Junction')){
						id=$(this).attr('id');
						if(markersJunction[id].getMap()==null){
							markersJunction[id].setMap(map);			
							markerClusterJunction.addMarker(markersJunction[id]);
							//$("#junctionCheckAllBoq").prop("checked",true);
						}								
					}
					if($(this).children('input:checkbox').hasClass('DistBoard')){
						id=$(this).attr('id');
						if(markersDistBoard[id].getMap()==null){
							markersDistBoard[id].setMap(map);
							if(window[""+id][8]=="backbone") {
								markerClusterBackboneDistBoard.addMarker(markersDistBoard[id]);
							}
							else if(window[""+id][8]=="metro") {
								markerClusterMetroDistBoard.addMarker(markersDistBoard[id]);
							}
							else if(window[""+id][8]=="access") {
								markerClusterAccessDistBoard.addMarker(markersDistBoard[id]);
							}			
							//$("#distBoardCheckAllBoq").prop("checked",true);
						}
					}
					if($(this).children('input:checkbox').hasClass('Nodes')){
						id=$(this).attr('id');
						if(markersNodeActive[id].getMap()==null){
							markersNodeActive[id].setMap(map);	
							if(window[""+id][8]=="MSAN") {
								markerClusterMSANNodes.addMarker(markersNodeActive[id]);
							}
							else if(window[""+id][8]=="DWDM") {
								markerClusterDWDMNodes.addMarker(markersNodeActive[id]);
							}
							else if(window[""+id][8]=="SDH") {
								markerClusterSDHNodes.addMarker(markersNodeActive[id]);
							}
							else if(window[""+id][8]=="GPON") {
								markerClusterGPONNodes.addMarker(markersNodeActive[id]);
							}	
							else if(window[""+id][8]=="SWITCH") {
								markerClusterEntSwitchNodes.addMarker(markersNodeActive[id]);
							}	
							//$("#nodesActiveCheckAllBoq").prop("checked",true);
						}
					}
					if($(this).hasClass('Trench')){
						singleTrenchCheckFilter($(this).children('input:checkbox'));
					}
					if($(this).hasClass('Duct')){
						singleDuctCheckFilter($(this).children('input:checkbox'));
					}
				//} // End of else in case node is not loaded
		});
			/*these checkboxes related for cuurentphysicallayer and not project ex: 
			 * fiberCheckAllBoq is checked when all fiber cable checked in currrent physical layer */
			$("#fiberCheckAllBoq").prop("checked",false);
			$("#tubeCheckAllBoq").prop("checked",false);
			$("#strandCheckAllBoq").prop("checked",false);
			$("#trenchCheckAllBoq").prop("checked",false);
			$("#ductCheckAllBoq").prop("checked",false);
	}
		else{ // In case Current Physical Layer ('.allElements') is unchecked.
				
			$(this).parent().find('ul > li').each(function(){				
				$(this).children('input:checkbox').prop('checked', false);
				if($(this).hasClass('FIBER')){
					singleFiberUnCheckFilter($(this).children('input:checkbox'));
				}
				else if($(this).hasClass('TUBE')){
					singleTubeUnCheckFilter($(this).children('input:checkbox'));
				}
				else if($(this).hasClass('STRAND')){
					singleStrandUnCheckFilter($(this).children('input:checkbox'));
				}
				else if($(this).hasClass('Trench')){
					singleTrenchUnCheckFilter($(this).children('input:checkbox'));
				}	
				else if($(this).hasClass('Duct')){
					singleDuctUnCheckFilter($(this).children('input:checkbox'));
				}	
				
				//////////////////777
				if($(this).children('input:checkbox').hasClass('Manhole')){
					id=$(this).attr('id');
					markersManhole[id].setMap(null);			
					markerClusterManhole.removeMarker(markersManhole[id]);						
					//$("#manholeCheckAllBoq").prop("checked",true);
					
				}					
				if($(this).children('input:checkbox').hasClass('Handhole')){
					id=$(this).attr('id');
					markersHandhole[id].setMap(null);			
					markerClusterHandhole.removeMarker(markersHandhole[id]);
					//$("#handholeCheckAllBoq").prop("checked",true);
					
				}
				if($(this).children('input:checkbox').hasClass('Junction')){
					id=$(this).attr('id');
					markersJunction[id].setMap(null);			
					markerClusterJunction.removeMarker(markersJunction[id]);
					//$("#junctionCheckAllBoq").prop("checked",true);
													
				}
				if($(this).children('input:checkbox').hasClass('DistBoard')){
					id=$(this).attr('id');
					markersDistBoard[id].setMap(null);
					if(window[""+id][8]=="backbone") {
						markerClusterBackboneDistBoard.removeMarker(markersDistBoard[id]);
					}
					else if(window[""+id][8]=="metro") {
						markerClusterMetroDistBoard.removeMarker(markersDistBoard[id]);
					}
					else if(window[""+id][8]=="access") {
						markerClusterAccessDistBoard.removeMarker(markersDistBoard[id]);
					}			
					//$("#distBoardCheckAllBoq").prop("checked",true);
					
				}
				if($(this).children('input:checkbox').hasClass('Nodes')){
					id=$(this).attr('id');
					markersNodeActive[id].setMap(null);	
					if(window[""+id][8]=="MSAN") {
						markerClusterMSANNodes.removeMarker(markersNodeActive[id]);
					}
					else if(window[""+id][8]=="DWDM") {
						markerClusterDWDMNodes.removeMarker(markersNodeActive[id]);
					}
					else if(window[""+id][8]=="SDH") {
						markerClusterSDHNodes.removeMarker(markersNodeActive[id]);
					}
					else if(window[""+id][8]=="GPON") {
						markerClusterGPONNodes.removeMarker(markersNodeActive[id]);
					}	
					else if(window[""+id][8]=="SWITCH") {
						markerClusterEntSwitchNodes.removeMarker(markersNodeActive[id]);
					}	
					//$("#nodesActiveCheckAllBoq").prop("checked",true);
					
				}
				//////////////////////
				
				
				
			});			
			
			for(var t=0;t<siteCltSrcMarkers.length;t++) {
				siteCltSrcMarkers[siteCltSrcMarkers[t].ID].setMap(null);
			}
			for (var u =0;u<allCablesShowPoint.length;u++) {
				window['fiberCheckPoints_'+allCablesShowPoint[u]] = "unchecked";
			}
			for (var z =0;z<allTubesShowPoint.length;z++) {
				window['tubeCheckPoints_'+allTubesShowPoint[z]] = "unchecked";
			}
			for (var a =0;a<allStrandsShowPoint.length;a++) {
				window['strandCheckPoints_'+allStrandsShowPoint[a]] = "unchecked";
			}
			for (var l =0;l<allTrenchsShowPoint.length;l++) {
				window['trenchCheckPoints_'+allTrenchsShowPoint[l]] = "unchecked";
			}
			for (var w =0;w<allDuctsShowPoint.length;w++) {
				window['ductCheckPoints_'+allDuctsShowPoint[w]] = "unchecked";
			}
			for (var x =0;x<allCablesShowRealPoint.length;x++) {
				window['fiberCheckRealPoints_'+allCablesShowRealPoint[x]] = "unchecked";
			}
			for (var o =0;o<allTubesShowRealPoint.length;o++) {
				window['tubeCheckRealPoints_'+allTubesShowRealPoint[o]] = "unchecked";
			}
			for (var b =0;b<allStrandsShowRealPoint.length;b++) {
				window['strandCheckRealPoints_'+allStrandsShowRealPoint[b]] = "unchecked";
			}
			for (var m =0;m<allTrenchsShowRealPoint.length;m++) {
				window['trenchCheckRealPoints_'+allTrenchsShowRealPoint[m]] = "unchecked";
			}
			for (var q =0;q<allDuctsShowRealPoint.length;q++) {
				window['ductCheckRealPoints_'+allDuctsShowRealPoint[q]] = "unchecked";
			}
			for (var y =0;y<allCablesShowSeq.length;y++) {
				window['fiberCheckSequence_'+allCablesShowSeq[y]] = "unchecked";
			}
			for (var v =0;v<allTubesShowSeq.length;v++) {
				window['tubeCheckSequence_'+allTubesShowSeq[v]] = "unchecked";
			}
			for (var c =0;c<allStrandsShowSeq.length;c++) {
				window['strandCheckSequence_'+allStrandsShowSeq[c]] = "unchecked";
			}
			for (var b =0;b<allDuctsShowSeq.length;b++) {
				window['ductCheckSequence_'+allDuctsShowSeq[b]] = "unchecked";
			}
			for (var a =0;a<allTrenchsShowSeq.length;a++) {
				window['trenchCheckSequence_'+allTrenchsShowSeq[a]] = "unchecked";
			}			
		}
	});
}

