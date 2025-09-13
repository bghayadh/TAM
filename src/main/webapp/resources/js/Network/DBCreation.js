var DBFlag = 0;



function getDB(type,url,id,tr,showDBflag){
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if(DBFlag == 0){

    	$.ajax({
    		type: "GET",
    		contentType: "application/json; charset=utf-8",
    		url: getContext()+'/getDB',
    		data: {	
							
    		},
    		dataType: "json",
    		success: function (data) {
    			if (data != null) {
					
    				if (data.searchResult != "Failed") {
					    $("#DistributionBoard_f_CurrentPhysicalLayer input[type=checkbox]").unbind();
						
				     	createController(data.ControllerList);
						
						 createDB(data.DBList);
    				     if ($('.AllDistBoards ').is(':checked') || $('#distBoardCheckAllBoq').is(':checked')){
    				    	
							
							DBLayerCheckAll();
    				    	
						}
    					$('#DistributionBoard_f_CurrentPhysicalLayer .TreeSpan').css("display", "inline"); // The purpose of this command is to let the background color width in mouse hovering or mouse select to be same span text width	
    				    treeCollapseFolder("#DistributionBoard_f_CurrentPhysicalLayer .folder","fast",".folder");
    					tree_prop_selection("#DistributionBoard_f_CurrentPhysicalLayer .TreeSpan");
    					MouseHoveringSpans("#DistributionBoard_f_CurrentPhysicalLayer .TreeSpan");		//>>>>>>>>>>>> Hover event in tree elements
    					
    					DBFlag = 1;	
    				}
    				$("#loading").remove();	
					if(showDBflag != null){
						console.log(showDBflag.length)
						if(showDBflag.length==3){
					showDB(showDBflag[0],showDBflag[1],showDBflag[2]);
					}
					
					else{
						showHideRealPoints(showDBflag[0],showDBflag[1],showDBflag[2]);
						
					}
					}
    			}
    		},
    		error: function(result) {
    			alert("Error");
    		}
    		});
    }
	

}
function DBLayerCheckAll(){
	
	$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",true);	
	$("#DistributionBoard_backbone__CurrentPhysicalLayer input[type=checkbox]").prop("checked",true);	
	$("#DistributionBoard_metro__CurrentPhysicalLayer input[type=checkbox]").prop("checked",true);	
	$("#DistributionBoard_access__CurrentPhysicalLayer input[type=checkbox]").prop("checked",true);
	$("#BackboneControllerDB__CurrentPhysicalLayer").prop("checked",true);
	$("#MetroControllerDB__CurrentPhysicalLayer").prop("checked",true);
	$("#AccessControllerDB__CurrentPhysicalLayer").prop("checked",true);

	controllerLayerCheckAll("backbone");
	controllerLayerCheckAll("metro");
	controllerLayerCheckAll("access");
	$("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){
	
	var ID =$(this).attr('id');
	var DBID = "";
	var controllerID= "";
	
	
	if (ID && ID.includes("Controller")) {
		           return true;
	  
	   }
		
		
		
		
		else {
			
			DBID=ID;
		}

		   // Skip if marker doesn't exist
		   if (!markersDistBoard[DBID]) {
		       return;
		   }

		$("#"+DBID).children(':checkbox').prop( "checked", true );
		if(markersDistBoard[DBID].getMap() == null ){
			markersDistBoard[DBID].setMap(map);
				
			if(window[""+DBID][8]=="backbone") {
			markerClusterBackboneDistBoard.addMarker(markersDistBoard[DBID]);
												}
												else if(window[""+DBID][8]=="metro") {
													markerClusterMetroDistBoard.addMarker(markersDistBoard[DBID]);
												}
												else if(window[""+DBID][8]=="access") {
													markerClusterAccessDistBoard.addMarker(markersDistBoard[DBID]);
												}		
					
			
		}
		$("#"+DBID).children(':checkbox').prop( "checked", true );	
		});
	
	if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked" ).length == 0 ){
		$("#distBoardCheckAllBoq").prop("checked",false);			
	}
	else{
		$("#distBoardCheckAllBoq").prop("checked",true);			
	}
	
}
function DBLayerUnCheckAll(){

	$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",false);	
		$("#DistributionBoard_backbone__CurrentPhysicalLayer  input[type=checkbox]").prop("checked",false);	
		$("#DistributionBoard_metro__CurrentPhysicalLayer  input[type=checkbox]").prop("checked",false);	
		$("#DistributionBoard_access__CurrentPhysicalLayer  input[type=checkbox]").prop("checked",false);
			
		markerClusterBackboneDistBoard.clearMarkers();
		markerClusterMetroDistBoard.clearMarkers();
		markerClusterAccessDistBoard.clearMarkers();
		$("#BackboneControllerDB__CurrentPhysicalLayer").prop("checked",false);
		$("#MetroControllerDB__CurrentPhysicalLayer").prop("checked",false);
		$("#AccessControllerDB__CurrentPhysicalLayer").prop("checked",false);

		controllerLayerUnCheckAll("backbone");
		controllerLayerUnCheckAll("metro");
		controllerLayerUnCheckAll("access");


	$("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){			
		var DB=$(this).attr('id');		
		var DBID =$(this).attr('id');
				if (DBID && DBID.startsWith("Controller")) {
				            return true; // continue to next
				   }

				   // Skip if marker doesn't exist
				   if (!markersDistBoard[DBID]) {
				       return;
				   }
			
			markersDistBoard[DB].setMap(null);	
		$("#"+DB).children(':checkbox').prop( "checked", false );
	});
		
		$("#network_tree").find(".DistBoard:checked" ).each(function(){

		id=$(this).parent().attr('id');
		if(markersDistBoard[id].getMap()==null){
			markersDistBoard[id].setMap(map);
			if(window[""+DBID][8]=="backbone") {
			markerClusterBackboneDistBoard.addMarker(markersDistBoard[DBID]);
												}
												else if(window[""+DBID][8]=="metro") {
													markerClusterMetroDistBoard.addMarker(markersDistBoard[DBID]);
												}
												else if(window[""+DBID][8]=="access") {
													markerClusterAccessDistBoard.addMarker(markersDistBoard[DBID]);
												}
		}		
	});							

}


function createDB(distribBoardList, transfer){





	 
		if(distribBoardList!=null){
					for(i=0;i<distribBoardList.length;i++){
						
					    allDB.push(distribBoardList[i][0]);
						window[""+distribBoardList[i][0]]=[];
						window[""+distribBoardList[i][0]]=distribBoardList[i];
						
						if(distribBoardList[i][9] == "passive"){
						if(distribBoardList[i][8]=="backbone") {
							str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/backboneDB.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
							$("#DistributionBoard_backbone__"+distribBoardList[i][6]).append(str);
				
							create_DB_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterBackboneDistBoard,"","");				
							DBCheckFilter(distribBoardList[i][0],markerClusterBackboneDistBoard);
						}
						else if(distribBoardList[i][8]=="metro") {
							str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/metroDb.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
							$("#DistributionBoard_metro__"+distribBoardList[i][6]).append(str);
							create_DB_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterMetroDistBoard,"","");				
							DBCheckFilter(distribBoardList[i][0],markerClusterMetroDistBoard);
																
						}
						else if(distribBoardList[i][8]=="access") {
							str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/accessDb.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
							$("#DistributionBoard_access__"+distribBoardList[i][6]).append(str);
							create_DB_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterAccessDistBoard,"","");				
							DBCheckFilter(distribBoardList[i][0],markerClusterAccessDistBoard);
																
						}
						}
						
						else if (distribBoardList[i][9] == "active"){
							if(distribBoardList[i][8]=="backbone") {
								console.log("y");
							str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/backboneDB.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
							$("#"+distribBoardList[i][10]).append(str);
							create_DB_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterBackboneDistBoard,"","");				
							DBCheckFilter(distribBoardList[i][0],markerClusterBackboneDistBoard);
													}
							
							else  if(distribBoardList[i][8]=="metro") {
								str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/metroDb.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
								$("#"+distribBoardList[i][10]).append(str);
								create_DB_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterBackboneDistBoard,"","");				
								DBCheckFilter(distribBoardList[i][0],markerClusterMetroDistBoard);
							}							
							else  if(distribBoardList[i][8]=="access") {
							str="<ul><li id='"+distribBoardList[i][0]+"'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/accessDb.png'> "+distribBoardList[i][3]+"/"+distribBoardList[i][0]+" </span></li></ul>";
							$("#"+distribBoardList[i][10]).append(str);
						     create_DB_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,markerClusterBackboneDistBoard,"","");				
							DBCheckFilter(distribBoardList[i][0],markerClusterAccessDistBoard);
																															}
																								
						}
						
						$(".DistributionBoard > .TreeSpan").contextmenu(function(){
							menuName=singleDistBoard;
							IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("__")[1];
							selectedDistBoardContext=$(this).parents().attr('id');
							console.log("uo");
							selectedDistBoardName=$(this).text();
							openContext(selectedDistBoardContext,selectedDistBoardName,singleDistBoard,event);
												
						});	
						
					}
				
		$(".DistBoard  > .TreeSpan").contextmenu(function(){
			menuName=singleDistBoard;
			console.log("uo");
			selectedDistBoardContext=$(this).attr('id');
												selectedDistBoardName=$(this).text();
												openContext(selectedDistBoardContext,selectedDistBoardName,singleDistBoard,event);							
		});	
		
		 
	    //nodeLayerCheckAll();
	}
	AllDistributionBoardCheckFilter("DistributionBoard_backbone__CurrentPhysicalLayer",markerClusterBackboneDistBoard);
    AllDistributionBoardCheckFilter("DistributionBoard_metro__CurrentPhysicalLayer",markerClusterMetroDistBoard);
    AllDistributionBoardCheckFilter("DistributionBoard_access__CurrentPhysicalLayer",markerClusterAccessDistBoard);
	AllDistributionBoardCheckFilter("DistributionBoard_f_CurrentPhysicalLayer","");
   
}


function create_DB_Marker_Click(Id,Name,Long,Lat,markers,marker_Cluster,Type,city) {

console.log("uuuuuuu");
	const pos = new google.maps.LatLng(Lat,Long);
	var data="<div>" +Name+ "</div>"; 
	var mapIcon;
	var markerType="";

	iconBackboneDB = {
			url:getContext()+"/resources/NetworkImages/backboneDB.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size
			
		};
		iconMetroDB = {
			url:getContext()+"/resources/NetworkImages/metroDb.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size
			
		};
		iconAccessDB = {
			url:getContext()+"/resources/NetworkImages/accessDb.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size
			
		};
		
		
		markerClusterBackboneDistBoard.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/blueCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
		
		markerClusterMetroDistBoard.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/dbMetroCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
		
		markerClusterAccessDistBoard.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/dbAccessCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
		
		
    if (markers == markersDistBoard && marker_Cluster == markerClusterBackboneDistBoard) {
		 mapIcon = iconBackboneDB; 
		 markerType="DistributionBoard";
    }
	 	else if (markers == markersDistBoard && marker_Cluster == markerClusterMetroDistBoard) {
		 mapIcon = iconMetroDB; 
		 markerType="DistributionBoard";
    }
	else if (markers == markersDistBoard && marker_Cluster == markerClusterAccessDistBoard) {
		 mapIcon = iconAccessDB; 
		 markerType="DistributionBoard";
    }
	
	 
	

	 if(!markers[Id]){
		console.log("ywwww");
		 Mapmarker = new google.maps.Marker({
			position: pos,
			data: data,
			icon: mapIcon,
			ID: Id,
		});
		markers.metadata = { id: Id };
		markers[Id] = Mapmarker;
		markers.push(Mapmarker);
		
			
	    google.maps.event.addListener(Mapmarker, "click", function (e) {
			var IdSelected=this.ID;
		
			dbFileId=$("#"+IdSelected).parent().parent().attr('id').split("__")[1];
			if(!dbFileId){
				dbFileId=$("#"+IdSelected).parent().parent().parent().parent().attr('id').split("__")[1];
					
			}
				var childrenInitial=$("#initial_ul_"+dbFileId+"").find(' > ul > li');
   			var children =  $("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li');
     
			var networkLevelFolder =  $("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li > ul > li ');
			var networkControllerFolders =  $("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li > ul > li ul > li');
			var networkController =  $("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li > ul > li ul > li ul > li');

			
			if(IdSelected!=IdSelectedTemp){	
				if(IdSelectedTemp!=""){
					$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
					$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
				}
				IdSelectedTemp=IdSelected;							
			}
			childrenInitial.show('fast');
			if(!children.is(":visible")){
				children.show();
			}
			networkLevelFolder.show(); 
			networkControllerFolders.show(); 
			networkController.show(); 
			
			$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");

			$("#initial_ul_"+dbFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			//$("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				
			$("#"+dbFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
			$("#"+markerType+"_f_"+dbFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');

			$("#"+markerType+"_f_"+dbFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li > ul > li  > ul > li > .folder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+markerType+"_f_"+dbFileId+"").find(' > ul > li > ul > li  > ul > li > ul > li>  .folder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
								
			offset=$("#"+IdSelected).offset().top;
			projectOffset=$("#initial_ul_"+dbFileId).offset().top;
			offsetTotal=(offset-projectOffset);
			$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
			if(draw==true){
				createPathh(e);
			}
			
			if(deleting == true){ 
				deleteAuxPath(e);
				MarkerArrayId.push(Id);
			}
		 });
			
		

	 } 

	else{
		if(markers[Id].map!=map){
			markers[Id].setMap(map);
		}
		markers[Id].setPosition(pos);
		markers[Id].data=data;
	}
	 
	$("#" + Id + " .TreeSpan").on('click', function (e) {
	    e.preventDefault();
	    e.stopPropagation();
	        // Now pan to the marker
	        panTo(markers[Id].getPosition().lat(), markers[Id].getPosition().lng());
	        map.setZoom(11);
		

	        if (typeof infowindow !== 'undefined') {
	            infowindow.close();
	        } else {
	            infowindow = new google.maps.InfoWindow();
	        }
console.log("yal");
	        infowindow.setContent(Id);
	        infowindow.open(map, markers[Id]);
	    });

}

function DBCheckFilter(Id,clusterName){

		$("#"+Id).children('input').bind("change",function() {				
			var folderID = $(this).parents().eq(4).attr('id');
			let $parent = $(this).parents().eq(2);
			let parentId = $(this).parents().eq(2).attr("id");
			if ($(this).is(':checked')){
				markersDistBoard[Id].setMap(map);					
				clusterName.addMarker(markersDistBoard[Id]);
				}
			else{
				if(folderID == "initial_ul_CurrentPhysicalLayer"){
					$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",false);				
				}
				 else {
                    $("#DistributionBoard_f_"+folderID+ " > .AllDistBoards").prop("checked",false);				
            }				
				markersDistBoard[Id].setMap(null);	
				clusterName.removeMarker(markersDistBoard[Id]);			
			}
		 	if ($(this).parents().eq(2).find('.DistributionBoard :checked').length == $(this).parents().eq(2).find('.DistributionBoard').length) {
								
				if (parentId && !parentId.toLowerCase().includes("controller")) {
					$parent.children('input[type=checkbox]').prop('checked', true);
									
				}
							
			 }
			 else{
			
				if (parentId && !parentId.toLowerCase().includes("controller")) {
				$parent.children('input[type=checkbox]').prop('checked', false);
			}		 }	
		 
			if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard :checked" ).length == 0 ){
				$("#distBoardCheckAllBoq").prop("checked",false);			
			}
			else{
				$("#distBoardCheckAllBoq").prop("checked",true);			
			}
			if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard :checked" ).length ==  $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard" ).length ){
				$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",true);	
			}
			else{
				$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",false);		
			}
		});
	}

//transfer id for the controller transfered from different layer 

	// Utility to get container selector based on network layer
	function getContainerSelector(networkLayer) {
	    switch (networkLayer) {
	        case "backbone": return "#DistributionBoard_backboneController__CurrentPhysicalLayer";
	        case "metro": return "#DistributionBoard_metroController__CurrentPhysicalLayer";
	        case "access": return "#DistributionBoard_accessController__CurrentPhysicalLayer";
	        default: return "";
	    }
	}


	function controllerLayerCheckAll(layer) {
	
	    let selector = "#DistributionBoard_" + layer + "Controller__CurrentPhysicalLayer"; // Added #
	    
	
	    // Check main checkbox for this layer
	   

	    // Loop over its <li> elements inside <ul>
	    $(selector).find('ul > li').each(function () {
	        let controllerId = $(this).attr('id');

	        if (!controllerId) return;
           if(controllerId.includes("Controller")){
			
	        // Check the checkbox inside this li
	        $(this).children(':checkbox').prop("checked", true);

	        // Show the marker if it’s hidden
	        if (markersController[controllerId] && markersController[controllerId].getMap() == null) {
	            markersController[controllerId].setMap(map);

	           markerClusterController.addMarker(markersController[controllerId]);
	                   }
					   }
		
					   
		
		 
	    });
	}


function controllerLayerUnCheckAll(layer) {
   

    let selector = "#DistributionBoard_" + layer + "Controller__CurrentPhysicalLayer";
   
	  $(selector).prop("checked", false);

    // Loop over all <li> inside the UL(s) of this layer
    $(selector).find('ul > li').each(function () {
        let controllerId = $(this).attr('id');
        

        if (!controllerId) return;

        // Uncheck the li’s own checkbox
		if(controllerId.includes("Controller")){
			$(this).children(':checkbox').prop("checked", false);
        // Remove marker from map if it’s visible
        if (markersController[controllerId] && markersController[controllerId].getMap() != null) {
		
            markersController[controllerId].setMap(null);

            markerClusterController.removeMarker(markersController[controllerId]);
             }
			 }
		
    });
}


	var markersController = [];
	var markerClusterController;
	
	function createController(controllerList,DBList) {
	
	    markersController = [];

	    markerClusterController = new MarkerClusterer();
	    markerClusterController.setMap(map);

	   
	   for (let i = 0; i < controllerList.length; i++) {
	 

    allCont.push(controllerList[i][0]);
	window[""+controllerList[i][0]]=[];
	window[""+controllerList[i][0]]=controllerList[i];



	        let layer = controllerList[i][4]; // networkLayer
	        let id = controllerList[i][0];
	        let name = controllerList[i][3];
			let dbCount = controllerList[i][5];

		  

			if (layer == "backbone") {
				
				if(dbCount > 0){
					str="<ul><li id='"+id+"' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
					$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
								
				}
				
				else{
				str="<ul><li id='"+id+"' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
				$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
				}			
				$(".bController > .TreeSpan").contextmenu(function (event) {
				         selectedControllerId = $(this).parent().attr('id');
					    selectedControllerName = $(this).text();
				        openContext(selectedControllerId, selectedControllerName, singleController, event);
				    });

			}
			else if (layer == "metro") {
				if(dbCount > 0){
					
					str="<ul><li id='"+id+"'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>					<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span>  <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
										
							   $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
												
								}
								
			else{

				str="<ul><li id='"+id+"'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
						
			   $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
			}
			$(".mController > .TreeSpan").contextmenu(function (event) {
			         selectedControllerId = $(this).parent().attr('id');
			         selectedControllerName = $(this).text();
			        openContext(selectedControllerId, selectedControllerName, singleController, event);
			    });

			}
		
				
			else if (layer == "access") {
				if(dbCount > 0){
					str="<ul><li id='"+id+"'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>	<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
											
												    $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
												
								}
								
								else{
				str="<ul><li id='"+id+"'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
							
								    $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
			    }
				$(".aController > .TreeSpan").contextmenu(function (event) {
				         selectedControllerId = $(this).parent().attr('id');
				         selectedControllerName = $(this).text();
				        openContext(selectedControllerId, selectedControllerName, singleController, event);
				    });

			}
			
			
			createControllerMarkerClick(
			    id,
			    name,
			    controllerList[i][1], 
			    controllerList[i][2], 
			    markersController,
			   markerClusterController
				
			);

			controllerCheckFilter(
			    id,
			   markerClusterController
			); 

			
			
				
				
			
	    // Context menu binding for controllers
				
	 AllControllerCheckFilter("DistributionBoard_backboneController__CurrentPhysicalLayer", markerClusterController);
	 AllControllerCheckFilter("DistributionBoard_metroController__CurrentPhysicalLayer", markerClusterController);
	 AllControllerCheckFilter("DistributionBoard_accessController__CurrentPhysicalLayer", markerClusterController);
	}
	}
	function createControllerMarkerClick(Id, name, long, lat, markers, markerClusterController) {
	    const pos = new google.maps.LatLng(lat, long);

	    // Controller icon
	    const iconController = {
	        url: getContext() + "/resources/NetworkImages/controller,.png",
	        scaledSize: new google.maps.Size(30, 20),
	    };

	    // Apply cluster options only once
	    if (!markerClusterController.optionsApplied) {
	        markerClusterController.setOptions({
	            minimumClusterSize: 2,
	            styles: [
	                {
	                    url: getContext() + '/resources/clusterIcons/blackCluster.png',
	                    height: 60,
	                    width: 60,
	                    anchorText: [-3, -3],
	                    textColor: "white"
	                }
	            ],
	            calculator: function (markers, numStyles) {
	                if (markers.length < 2) {
	                    return null; // no cluster bubble for <2 markers
	                }
	                return { text: markers.length.toString(), index: 1 };
	            }
	        });
	        markerClusterController.optionsApplied = true;
	    }

	    // --- CREATE OR UPDATE MARKER ---
	    if (!markers[Id]) {
	        // Create new marker
	        const Mapmarker = new google.maps.Marker({
	            position: pos,
	            icon: iconController,
	            ID: Id
	        });

	        markers[Id] = Mapmarker; // store marker only (not yet added to cluster!)

	        // --- CLICK EVENT on marker ---
	        google.maps.event.addListener(Mapmarker, "click", function (e) {
	            const IdSelected = this.ID;

	            dbFileId = $("#" + IdSelected).parent().parent().attr('id').split("__")[1];
			   const childrenInitial = $("#initial_ul_" + dbFileId + "").find(' > ul > li ');
				var children =  $("#DistributionBoard_f_"+dbFileId+"").find(' > ul > li > ul >li');
			    const networkLevelFolder = $("#DistributionBoard_f_"+dbFileId+"").find(' > ul > li > ul > li  ul > li');

	            // Selection logic
	            if (IdSelected != IdSelectedTemp) {
	                if (IdSelectedTemp != "") {
	                    $("#" + IdSelectedTemp + " > .TreeSpan").removeClass("selected-span").css("background", "");
	                }
	                IdSelectedTemp = IdSelected;
	            }

	            childrenInitial.show('fast');
	            if (!children.is(":visible")) {
	                children.show();
	            }
	            networkLevelFolder.show();

	            $("#" + IdSelected + " > .TreeSpan").addClass("selected-span").css("background-color", "#97b9cc");

	            $("#initial_ul_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
	            $("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
	            $("#" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
	            $("#Controller_f_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
	            $("#Controller_f_" + dbFileId + "").find(' > ul > li > .Parentfolder >svg').removeClass('fa fa-folder').addClass('fa-folder-open');

	            const offset = $("#" + IdSelected).offset().top;
	            const projectOffset = $("#initial_ul_" + dbFileId).offset().top;
	            const offsetTotal = (offset - projectOffset);

	            $("#network_tree").animate({ scrollTop: offsetTotal }, "slow");

	           
	        });

	    } else {
	        // Update existing marker position
	        if (markers[Id].map !== map) {
	            markers[Id].setMap(map);
	        }
	        markers[Id].setPosition(pos);
	    }

	    // --- CLICK EVENT on tree span ---
	    $("#" + Id + " .TreeSpan")
	        .not($("#" + Id + "_f .TreeSpan"))
	        .off("click")
	        .on("click", function () {
	            const marker = markers[Id];
	            panTo(marker.getPosition().lat(), marker.getPosition().lng());
	            map.setZoom(11);

	            if (typeof infowindow !== 'undefined') {
	                infowindow.close();
	            } else {
	                infowindow = new google.maps.InfoWindow();
	            }

	            infowindow.setContent(Id);
	            infowindow.open(map, marker); 
	        });
	}



	function AllControllerCheckFilter(containerId, cluster) {
	   
	    // Bind change only to the main checkbox (assuming it's the first input in the container)
	    $("#" + containerId).find('input').first().bind("change", function () {
	     
	        // Clear cluster before updating
	        if (!cluster) {
	            markerClusterController.clearMarkers();
	        } else {
	            cluster.clearMarkers();
	        }

	        // Create an array to store DB IDs
	      


	        // Process checking/unchecking
	        if ($(this).is(':checked')) {
				
	            if (containerId.includes("backbone")) {
	                controllerLayerCheckAll("backbone");
	            } else if (containerId.includes("metro")) {
	                controllerLayerCheckAll("backbone");
	            } else {
	                controllerLayerCheckAll("access");
	            }
	        } else {
	            if (containerId.includes("backbone")) {
	                controllerLayerUnCheckAll("backbone");
	            } else if (containerId.includes("metro")) {
	                controllerLayerUnCheckAll("backbone");
	            } else {
	                controllerLayerUnCheckAll("access");
	            }
	        }

	      
	    });
	}





	$(document).on('change', '.metroControllerDB', function () {
	     if ($(this).is(':checked')) {
			
	        controllerLayerCheckAll("metro");
	    } else {
	        controllerLayerUnCheckAll("metro");
	    }
	});

	$(document).on('change', '.accessControllerDB', function () {
	    if ($(this).is(':checked')) {
			
	        controllerLayerCheckAll("access");
	    } else {
	        controllerLayerUnCheckAll("access");
	    }
	});

		
	 
	function controllerCheckFilter(controllerId, cluster) {

		  // Bind change event on checkbox inside the controller element
	    $("#" + controllerId).children('input[type=checkbox]').off('change').on('change', function () {
	        var folderID = $(this).parents().eq(4).attr('id'); // Adjust based on your DOM depth
			let containerId = "#" + controllerId;
					      let allIds =[];  
					        // find the <li> children inside that container
					        $(containerId).find("li.DistributionBoard").each(function() {
					          allIds.push($(this).attr("id"));
					        });
	        if ($(this).is(':checked')) {
					  
	            // Show marker and add to cluster
	            if (markersController[controllerId]) {
	                markersController[controllerId].setMap(map);
	                cluster.addMarker(markersController[controllerId]);
				
	            }
		
   
				  
	        } else {
					  
	            // Uncheck the "AllDistBoards" checkbox related to this folder
	            if (folderID === "initial_ul_CurrentPhysicalLayer") {
	                $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", false);
	            } else {
	                $("#DistributionBoard_f_" + folderID + " > .AllDistBoards").prop("checked", false);
	            }
	            // Remove marker from map and cluster
	            if (markersController[controllerId]) {
	                markersController[controllerId].setMap(null);
	                cluster.removeMarker(markersController[controllerId]);
	            }
	        }
			
			

	        // Update parent checkbox if all children are checked/unchecked
	        var parentContainer = $(this).parents().eq(2);
	        var allChecked = parentContainer.find('.ControllerPannel :checked').length === parentContainer.find('.ControllerPannel').length;
	        parentContainer.children('input[type=checkbox]').prop('checked', allChecked);

	        // Update global select-all checkbox based on checked nodes
	        var checkedDbs = $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard :checked").length;
	        var totalDbs = $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard ").length;

	        $("#distBoardCheckAllBoq").prop("checked", checkedDbs > 0);
	        $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", checkedDbs === totalDbs);
	    });
	}

	function appendControllerToTree(data) {
	    let str = "";
	    let dbCount = parseInt(data.controllerDBCount || "0");
	    let id= data.controllerId;
	    let name= data.controllerName;
	    if (data.networkLayer == "backbone") {
			
			if(dbCount > 0){
			
				str="<ul><li id='"+id+"' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
				$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
							
			}
			
			else{
			str="<ul><li id='"+id+"' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
			$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
			}			
			$(".bController > .TreeSpan").contextmenu(function (event) {
			         selectedControllerId = $(this).parent().attr('id');
				   selectedControllerName = $(this).text();
			        openContext(selectedControllerId, selectedControllerName, singleController, event);
			    });

		}
		else if (data == "metro") {
			if(dbCount.length > 0){
				
				str="<ul><li id='"+id+"'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>					<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span>  <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
									
						   $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
											
							}
							
		else{

			str="<ul><li id='"+id+"'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
					
		   $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
		}
		$(".mController > .TreeSpan").contextmenu(function (event) {
		         selectedControllerId = $(this).parent().attr('id');
		         selectedControllerName = $(this).text();
		        openContext(selectedControllerId, selectedControllerName, singleController, event);
		    });

		}

			
		else if (data.networkLayer == "access") {
			if(dbCount >  0){
				str="<ul><li id='"+id+"'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>	<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
										
											    $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
											
							}
							
							else{
			str="<ul><li id='"+id+"'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+name+"/"+id+" </span></li></ul>";
						
							    $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
		    }
			$(".aController > .TreeSpan").contextmenu(function (event) {
			         selectedControllerId = $(this).parent().attr('id');
			         selectedControllerName = $(this).text();
			        openContext(selectedControllerId, selectedControllerName, singleController, event);
			    });

		}
		
	








	    
	  
	}

	function handleUpdateController(data, cont, db) {
	   
	    if (data.networkLayer != dbContNtLevel) {
	      
	      
	        $("#" + data.controllerId).remove();
	        createController(cont);
			createDB(db);
	    } else {
	        let imgPath = "";
	        if (data.networkLayer == "backbone") {
	            imgPath = "/resources/NetworkImages/controller,.png";
	        } else if (data.networkLayer == "metro") {
	            imgPath = "/resources/NetworkImages/controller,.png";
	        } else if (data.networkLayer == "access") {
	            imgPath = "/resources/NetworkImages/controller,.png";
	        }
			
	        $("#" + data.controllerId + "> .TreeSpan").html(
	            "<img class='image' src='" + getContext() + imgPath + "'> " +
	            data.controllerName + "/" + data.controllerId + " </span></li>"
	        );
	    }
	}
