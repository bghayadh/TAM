var nodeFlag = 0;
function getNode(type,url,id,tr){
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if(nodeFlag == 0){
    	$.ajax({
    		type: "GET",
    		contentType: "application/json; charset=utf-8",
    		url: getContext()+'/getNode',
    		data: {					
    		},
    		dataType: "json",
    		success: function (data) {
    			if (data != null) {
    				if (data.searchResult != "Failed") {
					    $("#NodeActive_f_CurrentPhysicalLayer input[type=checkbox]").unbind();// removed 
    				    createNode(data.nodeList);
    				    //if ($('.AllNodeActive').is(':checked') || $('.allElements').is(':checked')){// in order to check all the folders and cables under main fiber folder
    				    if ($('.AllNodeActive').is(':checked') || $('#nodesActiveCheckAllBoq').is(':checked')){
    				    // AllNodesTreeCheckFilter("NodeActive_f_CurrentPhysicalLayer","");
    				    	nodeLayerCheckAll();
    				    	
						}
    					$('#NodeActive_f_CurrentPhysicalLayer .TreeSpan').css("display", "inline"); // The purpose of this command is to let the background color width in mouse hovering or mouse select to be same span text width	
    				    treeCollapseFolder("#NodeActive_f_CurrentPhysicalLayer .folder","fast",".folder");
    					tree_prop_selection("#NodeActive_f_CurrentPhysicalLayer .TreeSpan");
    					MouseHoveringSpans("#NodeActive_f_CurrentPhysicalLayer .TreeSpan");		//>>>>>>>>>>>> Hover event in tree elements
    					
    					nodeFlag = 1;	
    				}
    				$("#loading").remove();	
    			}
    		},
    		error: function(result) {
    			alert("Error");
    		}
    		});
    }
	

}




function createNode(nodeList){
	markersNodeActive=[];
	
	markerClusterMSANNodes = new MarkerClusterer();
	markerClusterMSANNodes.setMap(map);

	markerClusterDWDMNodes = new MarkerClusterer();
	markerClusterDWDMNodes.setMap(map);

	markerClusterSDHNodes = new MarkerClusterer();
	markerClusterSDHNodes.setMap(map);

	markerClusterGPONNodes = new MarkerClusterer();
	markerClusterGPONNodes.setMap(map);
	
	markerClusterEntSwitchNodes = new MarkerClusterer();
	markerClusterEntSwitchNodes.setMap(map);
	
	for(i=0;i<nodeList.length;i++){
	    allNodes.push(nodeList[i][0]);
		window[""+nodeList[i][0]]=[];
		window[""+nodeList[i][0]]=nodeList[i];
	    console.log(" //////////nodeList "+nodeList[i]);	

		if(nodeList[i][8]=='MSAN') {
			str="<ul><li id='"+nodeList[i][0]+"'  class='NodeActive' style='display:none;width:100px;'><input type='checkbox' class='Nodes checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' style='width:16px; height:16px;' src='"+getContext()+"/resources/NetworkImages/MSANNodeIcon.png'> "+ nodeList[i][1]+"/"+nodeList[i][7] +" </span></li></ul>";
			$("#Entreprise_MSAN__CurrentPhysicalLayer").append(str);
			create_Node_Marker_Click(nodeList[i][0],nodeList[i][1],nodeList[i][5],nodeList[i][6],markersNodeActive,markerClusterMSANNodes,"","");				
			NodeActiveCheckFilter(nodeList[i][0],markerClusterMSANNodes);
		}
		else if(nodeList[i][8]=='SWITCH') {
			str="<ul><li id='"+nodeList[i][0]+"'  class='NodeActive' style='display:none;width:100px;'><input type='checkbox' class='Nodes checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' style='width:16px; height:16px;' src='"+getContext()+"/resources/NetworkImages/EntSwitchNodeIcon.png'> "+ nodeList[i][1]+"/"+nodeList[i][7] +" </span></li></ul>";
			$("#Entreprise_SWITCH__CurrentPhysicalLayer").append(str);
			create_Node_Marker_Click(nodeList[i][0],nodeList[i][1],nodeList[i][5],nodeList[i][6],markersNodeActive,markerClusterEntSwitchNodes,"","");				
			NodeActiveCheckFilter(nodeList[i][0],markerClusterEntSwitchNodes);
		}
		else if(nodeList[i][8]=='DWDM') {
			str="<ul><li id='"+nodeList[i][0]+"'  class='NodeActive' style='display:none;width:100px;'><input type='checkbox' class='Nodes checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' style='width:16px; height:16px;' src='"+getContext()+"/resources/NetworkImages/DWDMNodeIcon.png'>"+ nodeList[i][1]+"/"+nodeList[i][7] +" </span></li></ul>";
			$("#Transmission_DWDM__CurrentPhysicalLayer").append(str);
			create_Node_Marker_Click(nodeList[i][0],nodeList[i][1],nodeList[i][5],nodeList[i][6],markersNodeActive,markerClusterDWDMNodes,"","");				
			NodeActiveCheckFilter(nodeList[i][0],markerClusterDWDMNodes);
		}
		
		else if(nodeList[i][8]=='SDH') {
			str="<ul><li id='"+nodeList[i][0]+"'  class='NodeActive' style='display:none;width:100px;'><input type='checkbox' class='Nodes checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' style='width:16px; height:16px;' src='"+getContext()+"/resources/NetworkImages/SDHNodeIcon.png'>"+ nodeList[i][1]+"/"+nodeList[i][7] +" </span></li></ul>";
			$("#Transmission_SDH__CurrentPhysicalLayer").append(str);
			create_Node_Marker_Click(nodeList[i][0],nodeList[i][1],nodeList[i][5],nodeList[i][6],markersNodeActive,markerClusterSDHNodes,"","");				
			NodeActiveCheckFilter(nodeList[i][0],markerClusterSDHNodes);
		}
		
		else if(nodeList[i][8]=='GPON') {
			str="<ul><li id='"+nodeList[i][0]+"'  class='NodeActive' style='display:none;width:100px;'><input type='checkbox' class='Nodes checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' style='width:16px; height:16px;' src='"+getContext()+"/resources/NetworkImages/GPONNodeIcon.png'>"+ nodeList[i][1]+"/"+nodeList[i][7] +" </span></li></ul>";
			$("#Transmission_GPON__CurrentPhysicalLayer").append(str);
			create_Node_Marker_Click(nodeList[i][0],nodeList[i][1],nodeList[i][5],nodeList[i][6],markersNodeActive,markerClusterGPONNodes,"","");				
			NodeActiveCheckFilter(nodeList[i][0],markerClusterGPONNodes);
		}
		$(".NodeActive > .TreeSpan").contextmenu(function(){
			menuName=singleNodeActive;
			IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("__")[1];
			selectedNodeAcvtiveContext=$(this).parents().attr('id');
			selectedNodeActiveName=$(this).text();
			openContext(selectedNodeAcvtiveContext,selectedNodeActiveName,singleNodeActive,event);
								
		});	
		
		 
	    //nodeLayerCheckAll();
	}
	AllNodesTreeCheckFilter("Entreprise_MSAN__CurrentPhysicalLayer",markerClusterMSANNodes);
    AllNodesTreeCheckFilter("Transmission_DWDM__CurrentPhysicalLayer",markerClusterDWDMNodes);
    AllNodesTreeCheckFilter("Transmission_SDH__CurrentPhysicalLayer",markerClusterSDHNodes);
    AllNodesTreeCheckFilter("Transmission_GPON__CurrentPhysicalLayer",markerClusterGPONNodes);
    AllNodesTreeCheckFilter("Entreprise_SWITCH__CurrentPhysicalLayer",markerClusterEntSwitchNodes);
    AllNodesTreeCheckFilter("NodeActive_f_CurrentPhysicalLayer","");
   
}

function create_Node_Marker_Click(Id,Name,Long,Lat,markers,marker_Cluster,Type,city) {


	const pos = new google.maps.LatLng(Lat,Long);
	var data="<div>" +Name+ "</div>"; 
	var mapIcon;
	var markerType="";

	iconMSANNode = {
			url:getContext()+"/resources/NetworkImages/MSANNodeIcon.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size
			
		};
		iconSDHNode = {
			url:getContext()+"/resources/NetworkImages/SDHNodeIcon.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size
			
		};
		iconDWDMNode = {
			url:getContext()+"/resources/NetworkImages/DWDMNodeIcon.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size
			
		};
		iconGPONNode = {
			url:getContext()+"/resources/NetworkImages/GPONNodeIcon.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size
			
		};
		
		iconEntSwitchNode = {
				url:getContext()+"/resources/NetworkImages/EntSwitchNodeIcon.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
		
		markerClusterMSANNodes.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/nodeMSANCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
		
		markerClusterEntSwitchNodes.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/nodeEntSwitchCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
		
		markerClusterSDHNodes.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/nodeSDHCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
		
		markerClusterDWDMNodes.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/nodeDWDMCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
		
		markerClusterGPONNodes.setOptions({								
			minimumClusterSize: 2,
			styles: [
			         {
			        	 url: getContext()+'/resources/clusterIcons/nodeGPONCluster.png',
				         height: 60,
				         width:60,
				         anchorText:[-3,-3]
				      },
			],
			calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length,index:1}; 
			}                   
		});
	 
    if (markers == markersNodeActive && marker_Cluster == markerClusterMSANNodes) {
		 mapIcon = iconMSANNode; 
		 markerType="NodeActive";
    }
	 	else if (markers == markersNodeActive && marker_Cluster == markerClusterDWDMNodes) {
		 mapIcon = iconDWDMNode; 
		 markerType="NodeActive";
    }
	else if (markers == markersNodeActive && marker_Cluster == markerClusterSDHNodes) {
		 mapIcon = iconSDHNode; 
		 markerType="NodeActive";
    }
	else if (markers == markersNodeActive && marker_Cluster == markerClusterGPONNodes) {
		 mapIcon = iconGPONNode; 
		 markerType="NodeActive";
    }
	else if (markers == markersNodeActive && marker_Cluster == markerClusterEntSwitchNodes) {
		 mapIcon = iconEntSwitchNode; 
		 markerType="NodeActive";
   }
	 
	 

	 if(!markers[Id]){
		
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
			
			nodeFileId=$("#"+IdSelected).parent().parent().attr('id').split("__")[1];
				var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
   			var children =  $("#"+markerType+"_f_"+nodeFileId+"").find(' > ul > li');
            var networkLevelFolder =  $("#"+markerType+"_f_"+nodeFileId+"").find(' > ul > li >ul >li');

			
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
			
			$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");

			$("#initial_ul_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
			$("#"+markerType+"_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');

			$("#"+markerType+"_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+markerType+"_f_"+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
		
			offset=$("#"+IdSelected).offset().top;
			projectOffset=$("#initial_ul_"+nodeFileId).offset().top;
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
	 
	$("#"+Id+" .TreeSpan").not($("#"+Id+"_f"+" .TreeSpan")).on('click', function () {

	panTo(markers[Id].getPosition().lat(), markers[Id].getPosition().lng());
	map.setZoom(11);
	if(typeof infowindow!== 'undefined'){
		infowindow.close();
	}
	else{
		infowindow = new google.maps.InfoWindow();
	}

	infowindow.setContent(markers[Id].data);
	infowindow.open(map,markers[Id]);
				
	});

}