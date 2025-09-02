/* Beginning for tree events and changing the styles */
function getContext() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}


// To let the layer tab opened when the page is loaded

	
function CreateMap_PhysicalLayer(ListProject,ListManhole,ListHandhole,fiberList,distribBoardList,fiberTubes,fiberStrands,fiberAuxiliary_Data,tubesAuxiliaries,strandsAuxiliaries,trenchList,trenchAuxiliary_Data,NodeList,filterFlag){

	var start = performance.now();
			panPath = [];   // An array of points the current panning action will use
			panQueue = [];  // An array of subsequent panTo actions to take
			STEPS = 80;
	
			markersManhole=[];		
			markersHandhole=[];
			//markersNodeActive=[];
			markerClusterJunction = [];
			markerClusterSite = [];
			markersDistBoard=[];
			markersDistBoardFilter=[];
			markersTempoDistBoard="";						
			markersTempoHandhole="";
			markersTempo="";
			markersJctTempo="";
			markersTempoHandholeJct="";	
			siteCltSrcMarkers=[];
			placeMarkers=[];
			markersJunction=[];
			markersSite=[];
			markersController=[];


	//		map.setZoom(6);
	
			markerClusterManhole = new MarkerClusterer();
			markerClusterManhole.setMap(map); // to be checked !!!!
			markerClusterHandhole = new MarkerClusterer();
			markerClusterHandhole.setMap(map);// to be checked !!!!
			
	
			markerClusterBackboneDistBoard = new MarkerClusterer();
			markerClusterBackboneDistBoard.setMap(map);
			
			markerClusterMetroDistBoard = new MarkerClusterer();
			markerClusterMetroDistBoard.setMap(map);
			
			markerClusterAccessDistBoard = new MarkerClusterer();
			markerClusterAccessDistBoard.setMap(map);
			
		/*	markerClusterMSANNodes = new MarkerClusterer();
			markerClusterMSANNodes.setMap(map);
			
			markerClusterDWDMNodes = new MarkerClusterer();
			markerClusterDWDMNodes.setMap(map);
			
			markerClusterSDHNodes = new MarkerClusterer();
			markerClusterSDHNodes.setMap(map);
			
			markerClusterGPONNodes = new MarkerClusterer();
			markerClusterGPONNodes.setMap(map);*/
			
			markerClusterJunction = new MarkerClusterer();
			markerClusterJunction.setMap(map);// to be checked !!!!
			
			markerClusterSite = new MarkerClusterer();
			markerClusterSite.setMap(map);// to be checked !!!!
	
			
	
			kenya=new google.maps.LatLng(1,38);					
			LatLanMouse(map);
	
			iconManhole = {
				url: getContext()+"/resources/NetworkImages/manholeRed.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
		
		
			iconHandhole ={
				url:""+getContext()+"/resources/NetworkImages/handholeYellow.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
		
			iconBackboneDistBoard = {
				url:getContext()+"/resources/NetworkImages/backboneDB.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
			iconMetroDistBoard = {
				url:getContext()+"/resources/NetworkImages/metroDb.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
			iconAccessDistBoard = {
				url:getContext()+"/resources/NetworkImages/accessDb.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
			iconManholeJct = {
				url: getContext()+"/resources/NetworkImages/manholeJct.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
			iconHandholeJct ={
				url:""+getContext()+"/resources/NetworkImages/handholeYellowJct.png", // url
				scaledSize: new google.maps.Size(20, 20), // scaled size
				
			};
			iconSite ={
				url:""+getContext()+"/resources/NetworkImages/redSiteIcon.png", // url
				scaledSize: new google.maps.Size(35, 35), // scaled size
			};
			iconPlace ={
				url:""+getContext()+"/resources/markers/Map-Marker-PNG-File.png", // url
				scaledSize: new google.maps.Size(35, 35), // scaled size
			};
			iconGeneric ={
				url:""+getContext()+"/resources/NetworkImages/generic.png", // url
				scaledSize: new google.maps.Size(40, 40), // scaled size
			};
			iconAuxPoint ={
				url:""+getContext()+"/resources/NetworkImages/handholeGreen.png", // url
				scaledSize: new google.maps.Size(10, 10), // scaled size
			};
			
			iconClient ={
					url:""+getContext()+"/resources/images/google-maps-client.png", // url
					scaledSize: new google.maps.Size(40, 40), // scaled size
			};
			
			/*iconMSANNode = {
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
				
			};*/
			
			
			// Manholes cluster Calculator
			
	/*		markerClusterManhole.setOptions( {					  					
				minimumClusterSize: 2,
				styles: [
				         {
				        	 url: getContext()+'/resources/clusterIcons/redCluster.png',
					         height: 60,
					         width:60,
					         anchorText:[-3,-3]
					      },
				],
				calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length, index: 3}; // red
				}                   
			});
			
			// Handholes cluster Calculator
			
			markerClusterHandhole.setOptions( {									
				minimumClusterSize: 2,
				styles: [
				         {
				        	 url: getContext()+'/resources/clusterIcons/yellowCluster.png',
					         height: 60,
					         width:60,
					         anchorText:[-4,-2]
					      },
				],
				calculator: function(markers, numStyles) {
				if (markers.length >= 1) return {text: markers.length, index: 2}; 
				}                   
			});
			
			
			
			
			// Backbone Distribution Boards cluster Calculator
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
			
			
			// Metro Distribution Boards cluster Calculator
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
			
			// Access Distribution Boards cluster Calculator
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
			});*/
			
		
	
			$(".Initial_CurrentPhysicalLayer > .TreeSpan").on("click",function(){
		console.log("click1");
				map.setZoom(7);	
				map.setCenter(kenya);
	
				google.maps.event.addListenerOnce(map, 'idle', function() {
						markerClusterManhole.repaint();
						markerClusterHandhole.repaint();
						if(	DBFlag == 1){
						markerClusterBackboneDistBoard.repaint();	
						markerClusterMetroDistBoard.repaint();	
						markerClusterAccessDistBoard.repaint();	
						}
						if(nodeFlag == 1){
							markerClusterMSANNodes.repaint();
							markerClusterSDHNodes.repaint();
							markerClusterDWDMNodes.repaint();
							markerClusterGPONNodes.repaint();	
							markerClusterEntSwitchNodes.repaint();
						}
				});				
			});
	 var end = performance.now();
	 var start2 = performance.now();
	
			if(ListManhole!=null){
				str="";
				for(i=0;i<ListManhole.length;i++){
					//Junction exists
					if(ListManhole[i][5]>0){	
						create_Marker_Click(ListManhole[i][0],ListManhole[i][1],ListManhole[i][2],ListManhole[i][3],markersManhole,markerClusterManhole,"Junction","");				
						//createManhole_Marker_Click(ListManhole[i][0],ListManhole[i][1],ListManhole[i][2],ListManhole[i][3],markersManhole,markerClusterManhole,"Junction");	
							
						ManholeCheckFilter(ListManhole[i][0]);						
					}
					else {
						create_Marker_Click(ListManhole[i][0],ListManhole[i][1],ListManhole[i][2],ListManhole[i][3],markersManhole,markerClusterManhole,"No_Junction","");				
						//createManhole_Marker_Click(ListManhole[i][0],ListManhole[i][1],ListManhole[i][2],ListManhole[i][3],markersManhole,markerClusterManhole,"No_Junction");				
						ManholeCheckFilter(ListManhole[i][0]);					
					}
				}
				//AllManholeCheckFilter();
			}
	
			if(ListHandhole!=null)
			{
				//console.log("ListHandhole is not null "+ListHandhole);
				for(i=0;i<ListHandhole.length;i++){
					if(ListHandhole[i][5]>0){
						
					create_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"Junction","");											
					//createHandhole_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"Junction");				
					HandholeCheckFilter(ListHandhole[i][0]);
					
			}
			else {
				create_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"No_Junction","");				
				//createHandhole_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"No_Junction");				
				HandholeCheckFilter(ListHandhole[i][0]);			
			}
		
				}		
				//AllHandholeCheckFilter();
			}
			/*if(distribBoardList!=null){
				for(i=0;i<distribBoardList.length;i++){		
					if(distribBoardList[i][8]=="backbone"){
						clusterName = markerClusterBackboneDistBoard;
					}
					else if(distribBoardList[i][8]=="metro"){
						clusterName = markerClusterMetroDistBoard;
					}
					else {
						clusterName = markerClusterAccessDistBoard;
					}
					create_Marker_Click(distribBoardList[i][0],distribBoardList[i][3],distribBoardList[i][1],distribBoardList[i][2],markersDistBoard,clusterName,"",distribBoardList[i][7]);
					DistributionBoardCheckFilter(distribBoardList[i][0],"",clusterName);
				}
			
				//AllDistributionBoardFilter();
				
			AllDistributionBoardCheckFilter("DistributionBoard_access__CurrentPhysicalLayer",markerClusterAccessDistBoard);
		    AllDistributionBoardCheckFilter("DistributionBoard_metro__CurrentPhysicalLayer",markerClusterMetroDistBoard);
		    AllDistributionBoardCheckFilter("DistributionBoard_backbone__CurrentPhysicalLayer",markerClusterBackboneDistBoard);
		    AllDistributionBoardCheckFilter("DistributionBoard_f_CurrentPhysicalLayer","")
			}
			
			
		
			
			if(NodeList != null){
			   for(i=0;i<NodeList.length;i++){
			     if(NodeList[i][8]=='MSAN'){
			            create_Marker_Click(NodeList[i][0],NodeList[i][1],NodeList[i][5],NodeList[i][6],markersNodeActive,markerClusterMSANNodes,"","");				
						NodeActiveCheckFilter(NodeList[i][0],markerClusterMSANNodes);
			   }
			 
			 else if(NodeList[i][8]=='DWDM'){
			            create_Marker_Click(NodeList[i][0],NodeList[i][1],NodeList[i][5],NodeList[i][6],markersNodeActive,markerClusterDWDMNodes,"","");				
						NodeActiveCheckFilter(NodeList[i][0],markerClusterDWDMNodes);
			   }
			   
			 else if(NodeList[i][8]=='SDH'){
			            create_Marker_Click(NodeList[i][0],NodeList[i][1],NodeList[i][5],NodeList[i][6],markersNodeActive,markerClusterSDHNodes,"","");				
						NodeActiveCheckFilter(NodeList[i][0],markerClusterSDHNodes);
			   }
			   
			 else if(NodeList[i][8]=='GPON'){
			            create_Marker_Click(NodeList[i][0],NodeList[i][1],NodeList[i][5],NodeList[i][6],markersNodeActive,markerClusterGPONNodes,"","");				
						NodeActiveCheckFilter(NodeList[i][0],markerClusterGPONNodes);
			   }
			   
			   }
			AllNodesTreeCheckFilter("Entreprise_MSAN__CurrentPhysicalLayer",markerClusterMSANNodes);
		    AllNodesTreeCheckFilter("Transmission_DWDM__CurrentPhysicalLayer",markerClusterDWDMNodes);
		    AllNodesTreeCheckFilter("Transmission_SDH__CurrentPhysicalLayer",markerClusterSDHNodes);
		    AllNodesTreeCheckFilter("Transmission_GPON__CurrentPhysicalLayer",markerClusterGPONNodes);
		    AllNodesTreeCheckFilter("NodeActive_f_CurrentPhysicalLayer","");
		}*/
			
	var end2 = performance.now();
			allElementsCheckFilter();	
			filterMap_Labels("MANHOLE",markersManhole,"manholesMapCheck_Labels","marker-position-manhole","red");
			filterMap_Labels("HANDHOLE",markersHandhole,"handholesMapCheck_Labels","marker-position-handhole","#E5C523");	
			filterMap_Labels("DistributionBoard",markersDistBoard,"dBMapCheck_Labels","marker-position-dB","#5665F9");
			/////////////called from CreateFiberPath()/////////////////7
			//filterMap_CableLabels("FIBER",fiberArray,"fiberMapCheck_Labels");
			//filterMap_CableLabels("TUBE",tubeArray,"tubeMapCheck_Labels");
			//filterMap_CableLabels("STRAND",strandArray,"strandMapCheck_Labels");
			
			//filterMap_CableLabels("FIBER",directionHashmap,"fiberMapCheck_Labels");
			filterMapTrenchDuctLabels("TRENCH",trenchArray,"trenchMapCheck_Labels");
			filterMapTrenchDuctLabels("DUCT",ductArray,"ductMapCheck_Labels");

			sitesMapLabel();
			clientsMapLabel();
	map.setZoom(6);
		
	//This is the google map menu that will open when right click on the google map
    MenuMap = document.getElementById("mapMenu");
    google.maps.event.addListener(map, 'rightclick', function (e) {
    	for (prop in e) {
    		if (e[prop] instanceof MouseEvent) {
			       ShowContextMenuGoolge(MenuMap, e[prop].clientX,e[prop].clientY); 
		           break;
		        }
		    }   
    /** this stop NOT stopping DOM 'context-menu' event from firing */
    e.stop();
    });

    google.maps.event.addListener(map, 'click', function () {	
		console.log("click2");
       HideContextMenuGoolge(MenuMap);
     });
    //This to hide the map menu when clicking on the mainDiv
    $("#mainDiv").on('click', function(){
		console.log("click3");
    	HideContextMenuGoolge(MenuMap);
    })
}
// The end of CreateMap_PhysicalLayer
function sitesMapLabel() {

	$("#sitesMapCheck_Labels").on('change',function(){
	if($(this).prop("checked")==true){
		
		if (checkLabel =="checked") {
			$('.SITE').each(function(){
				markersSite[$(this).attr('id')].setLabel(null);
			});
		}
		
			$('.SITE').each(function(){
			
			if(markersSite[$(this).attr('id')].getLabel()!=undefined && markersSite[$(this).attr('id')].getLabel()!=null) {
				var labelText =markersSite[$(this).attr('id')].getLabel().text +" // " + $(this).text();
				markersSite[$(this).attr('id')].setLabel({text: labelText, className:"marker-position-site",color:"red"});
			}
			else {
				var labelText =$(this).text();
				markersSite[$(this).attr('id')].setLabel({text: labelText  , className:"marker-position-site",color:"red"}); 	
			}
		
		});
	}
	else {
		//for(var i=0;i<allSites.length;i++) {	
			$('.SITE').each(function(){
			//Show sequence is checked
			if(markersSite[$(this).attr('id')].getLabel().text.includes("//") == true) {
				markersSite[$(this).attr('id')].setLabel({text: markersSite[$(this).attr('id')].getLabel().text.split("//")[0] , className:"marker-position-sequence",color:"red"});
			}
			//Show sequence is unchecked
			else {
				markersSite[$(this).attr('id')].setLabel(null);
			}
		});
	}
	});
 	
}
function clientsMapLabel() {

	$("#clientsMapCheck_Labels").on('change',function(){
	if($(this).prop("checked")==true){
		
		if (checkLabel =="checked") {
			for(var i=0;i<allClients.length;i++) {
				siteCltSrcMarkers[allClients[i].split(":")[0]].setLabel(null);
			}
		}
		
		for(var i=0;i<allClients.length;i++) {
			
			if(siteCltSrcMarkers[allClients[i].split(":")[0]].getLabel()!=undefined && siteCltSrcMarkers[allClients[i].split(":")[0]].getLabel()!=null) {
				siteCltSrcMarkers[allClients[i].split(":")[0]].setLabel({text: siteCltSrcMarkers[allClients[i].split(":")[0]].getLabel().text +" // " + allClients[i].split(":")[0]+":"+allClients[i].split(":")[1] , className:"marker-position-site",color:"red"});
			}
			else {
				siteCltSrcMarkers[allClients[i].split(":")[0]].setLabel({text: allClients[i].split(":")[0]+":"+allClients[i].split(":")[1] , className:"marker-position-site",color:"red"}); 	

			}
		
		}
	}
	else {
		for(var i=0;i<allClients.length;i++) {
			
			//Show sequence is checked
			if(siteCltSrcMarkers[allClients[i].split(":")[0]].getLabel().text.includes("//") == true) {
				siteCltSrcMarkers[allClients[i].split(":")[0]].setLabel({text: siteCltSrcMarkers[allClients[i].split(":")[0]].getLabel().text.split("//")[0] , className:"marker-position-sequence",color:"red"});
			}
			//Show sequence is unchecked
			else {
				siteCltSrcMarkers[allClients[i].split(":")[0]].setLabel(null);
			}
		}
	}
	});

}
// filter from map manholes,handholes, and distribution boards labels
function filterMap_Labels(className,RelativeArray,RelativeCheckboxId,labelClassName,color){

	$("#"+RelativeCheckboxId).on('change',function(){
	
		if($(this).prop("checked")==true){

			if (checkLabel =="checked") {
				$('.'+className).each(function(){
					RelativeArray[$(this).attr('id')].setLabel(null);
				});
			}
			
			//Show Seq is checked				
			$('.'+className).each(function(){
				
				if(RelativeArray[$(this).attr('id')].getLabel()!=undefined && RelativeArray[$(this).attr('id')].getLabel()!=null ) {
					

				if(className=="MANHOLE" || className=="HANDHOLE") {
						var labelText = RelativeArray[$(this).attr('id')].getLabel().text +" // " + $(this).text().split("Junctions")[0];
					}
					else {
						var labelText = RelativeArray[$(this).attr('id')].getLabel().text +" // " + $(this).text();
					}
					
					RelativeArray[$(this).attr('id')].setLabel({text: labelText , className:labelClassName,color:color});

				}
				else {
					if(className=="MANHOLE" || className=="HANDHOLE") {
						var labelText = $(this).text().split("Junctions")[0];
					}
					else {
						var labelText = $(this).text();
					}

					RelativeArray[$(this).attr('id')].setLabel({text: labelText, className:labelClassName,color:color});
				}
			});
			
		}
	
		else{
			//Show Seq is checked
			
				$('.'+className).each(function(){
					//Show sequence is checked
					if(RelativeArray[$(this).attr('id')].getLabel().text.includes("//") == true) {
						RelativeArray[$(this).attr('id')].setLabel({text: RelativeArray[$(this).attr('id')].getLabel().text.split("//")[0] , className:"marker-position-sequence",color:color});
					}
					//Show sequence is unchecked
					else {
						RelativeArray[$(this).attr('id')].setLabel(null);
					}
					
				});
			
			
		}
	});
}
function filterMapTrenchDuctLabels(className,RelativeArray,RelativeCheckboxId){
	
	$("#"+RelativeCheckboxId).on('change',function(){
		if($(this).prop("checked")==true){	
			
			$('.'+className).each(function(){
				
				//Case of line of site
				if(RelativeArray[$(this).parent().attr('id')]){
					//Check if the path is displayed on map before adding a label to it
					if(RelativeArray[$(this).parent().attr('id')].getMap() !=null) {
						RelativeArray[$(this).parent().attr('id')].mapLabel.setMap(map);
					}
				}
				
				// Case of driving
				if(directionHashmapTrench.get($(this).parent().attr('id')) != undefined) {
					//Check if the trench driving path is displayed on map before adding a label to it
					if(directionHashmapTrench.get($(this).parent().attr('id'))[0].getMap() != null) {
						directionHashmapTrench.get($(this).parent().attr('id')).mapLabel.setMap(map);
					}
				}
				if(directionHashmapDuct.get($(this).parent().attr('id')) != undefined) {
					//Check if the duct driving path is displayed on map before adding a label to it
					if(directionHashmapDuct.get($(this).parent().attr('id'))[0].getMap() != null) {
						directionHashmapDuct.get($(this).parent().attr('id')).mapLabel.setMap(map);
					}
				}
			});	
		}
	
		else{
			$('.'+className).each(function(){
			
				if(RelativeArray[$(this).parent().attr('id')]){
					RelativeArray[$(this).parent().attr('id')].mapLabel.setMap(null);
				}	
				
				if(directionHashmapTrench.get($(this).parent().attr('id'))) {
					directionHashmapTrench.get($(this).parent().attr('id')).mapLabel.setMap(null);
				}
				if(directionHashmapDuct.get($(this).parent().attr('id'))) {
					directionHashmapDuct.get($(this).parent().attr('id')).mapLabel.setMap(null);
				}
				
			});
		}
	});	
}
//filter from map fiber,tubes,and strands labels
function filterMap_CableLabels(className,RelativeArray,RelativeCheckboxId){
	$("#"+RelativeCheckboxId).on('change',function(){
		
		if($(this).prop("checked")==true){	
			$('.'+className).each(function(){
				
				//Case of line of site
				if(RelativeArray[$(this).attr('id')]){
					//Check if the path is displayed on map before adding a label to it
					if(RelativeArray[$(this).attr('id')].getMap() !=null) {
						RelativeArray[$(this).attr('id')].mapLabel.setMap(map);
					}
				}
				
				// Case of driving
				if(directionHashmapTube.get($(this).attr('id')) != undefined) {
					//Check if the tube driving path is displayed on map before adding a label to it
					if(directionHashmapTube.get($(this).attr('id'))[0].getMap() != null) {
						directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(map);
					}
				}
				
				// Case of driving
				if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
					//Check if the strand driving path is displayed on map before adding a label to it
					if(directionHashmapStrand.get($(this).attr('id'))[0].getMap() != null) {
						directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(map);
					}
				}
				
				
				if(directionHashmap.get($(this).attr('id')) != undefined) {
					//Check if the cable driving path is displayed on map before adding a label to it
					if(directionHashmap.get($(this).attr('id'))[0].getMap() != null) {
						directionHashmap.get($(this).attr('id')).mapLabel.setMap(map);
					}
				}				
			});	
		}
	
		else{
			$('.'+className).each(function(){
			
				if(RelativeArray[$(this).attr('id')]){
					RelativeArray[$(this).attr('id')].mapLabel.setMap(null);
				}	
				
				if(directionHashmapTube.get($(this).attr('id'))) {
					directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
				}
				if(directionHashmapStrand.get($(this).attr('id'))) {
					directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
				}
				if(directionHashmap.get($(this).attr('id'))) {
					directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
				}
			});
		}
	});
}	



/* TO BE DELETED 
 * 
 * // filter from map fiber,tubes,and strands labels
function filterMap_CableLabels(className,RelativeArray,RelativeCheckboxId){
	
	$("#"+RelativeCheckboxId).on('change',function(){
	
		if($(this).prop("checked")==true){

		$("#FiberPath_f_CurrentPhysicalLayer > .AllFiberCables").on('change',function(){	

			if($(this).prop("checked")==true){
				
				$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li').each(function(){		
				fiberArray[$(this).attr('id')].mapLabel.setMap(map);
				})

			} 
		})
			
			
			$('.'+className).each(function(){
				
				$(this).children('input:checkbox').on('change',function(){
					if($(this).prop("checked")==true){
		                if(directionHashmap.get($(this).parent().attr('id'))) {
			                    directionHashmap.get($(this).parent().attr('id')).mapLabel.setMap(map);
		                }
						if(RelativeArray[$(this).parent().attr('id')]){
							RelativeArray[$(this).parent().attr('id')].mapLabel.setMap(map);
						}
					} 
				});


			})
			
		}
	
		else{
			$('.'+className).each(function(){
				
                if(directionHashmap.get($(this).attr('id'))) {
	                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
                }
				
				if(RelativeArray[$(this).attr('id')]){
					RelativeArray[$(this).attr('id')].mapLabel.setMap(null);
				}
			})
		}
	});
}
*/	

//calculate distance of path
function haversine_distance(lat1,lng1,lat2,lng2) {
	var R = 3958.8; // Radius of the Earth in miles
	var lat1 = lat1 * (Math.PI/180); // Convert degrees to radians
	var lat2 = lat2 * (Math.PI/180); // Convert degrees to radians
	var difflat = lat2-lat1; // Radian difference (latitudes)
	var difflon = (lng2-lng1) * (Math.PI/180); // Radian difference (longitudes)
	
	var d = 2 * R * Math.asin(Math.sqrt(Math.sin(difflat/2)*Math.sin(difflat/2)+Math.cos(lat1)*Math.cos(lat2)*Math.sin(difflon/2)*Math.sin(difflon/2)));
	//return Math.round(1000*d)/1000;
	return parseFloat(d.toFixed(8));
}

function closePointsHaversineDistance(lat1, lng1, lat2, lng2) {
   // Radius of the Earth in meters
    var R = 6371000.0;
    
    // Convert degrees to radians
    var radLat1 = lat1 * (Math.PI / 180);
    var radLon1 = lng1 * (Math.PI / 180);
    var radLat2 = lat2 * (Math.PI / 180);
    var radLon2 = lng2 * (Math.PI / 180);
    
    // Haversine formula
    var dLat = radLat2 - radLat1;
    var dLon = radLon2 - radLon1;
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var distance = R * c;
    
    return distance; // Return distance in meters
}

function getCoords(){
	var coords=document.getElementById('mapText');
	coords=coords.value.slice(1,-1).split(" || ", 2); //This to remove the first and last double quote characters and create array of size 2 based on the separator.
	coordsPrime=coords[0] + " " +coords[1];	
	return coordsPrime;
}

// Functions to change the  color of arrows 
function changecolor(x) {
    
      x.style.color='gold';
    
  }

function returncolor(x) {
      x.style.color ='#08526D';
     
}

function returnNormal() {
	$("#network_tree").animate({height: "289px"});
	$(".searchcontainer").css("display", "block");
	$(".tabcontent").animate({height: "250px"});
	$('#arrow_down').show();
	$('#arrow_up').show();
	$('#arrow_up_normal').hide();
	$('#arrow_down_normal').hide();
	}

function FilterOptions(x){

	 var input, filter, table, tr, td, i, txtValue;
	 var str=x;
	 
		 if(str=='options'){
	  input = document.getElementById("searchOption");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("optionstable");
	  tr = table.getElementsByTagName("tr");

	  // Loop through all table rows, and hide those who don't match the search query
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[0];
	    if (td) {
	      txtValue = td.textContent || td.innerText;
	      if (txtValue.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }
	  }
		 }

		 else if(str=='layers'){

			 input = document.getElementById("layerSearch");
			  filter = input.value.toUpperCase();
			  table = document.getElementById("LayersTable");
			  tr = table.getElementsByTagName("tr");
              var div=table.rows[0];
			  // Loop through all table rows, and hide those who don't match the search query
			  for (i = 0; i < tr.length; i++) {
			    td = tr[i].getElementsByTagName("td")[0];
			    if (td) {
			      txtValue = td.textContent || td.innerText;
			      if (txtValue.toUpperCase().indexOf(filter) > -1) {
			    	 
			         tr[i].style.display = "";
			        
			      } else {
			        tr[i].style.display = "none";
			        
			      }
			    }

			    tr[0].style.display="";

			 }
		 }
	 }

$(document).ready(function () {
	
document.getElementById("Defaultbutton").click();
$('#tree').toggleClass('orange');
$('#gis').toggleClass('orange');


$("#left").resizable({
	handles: "e" 
});

$("#network_tree").resizable({
	handles: "s", 	

});

$('.modal-content').resizable({
	handles: "e" ,
	
	});
    
$('.modal-dialog').draggable({
	handle: ".modal-header, .modal-footer"
	});
//To be deleted
/*
$('#mapModal').on('hidden.bs.modal', function() {
	    $(this).find('.modal-content').css({'width': '', 'height': ''});
	})
	
$('#modalOrderTree').on('hidden.bs.modal', function() {
	    $(this).find('.modal-content').css({'width': '', 'height': ''});
	})
*/	
	
	
//reset popup position after it has been dragged and closed
$('body').on('hidden.bs.modal', function() { 
	$('.modal-dialog').css({'top': '', 'left':''});
})

$(".modalMinimize").on("click", function(){
	console.log("click4");
  $(".modal-body").slideToggle();
  $(".modal-footer").slideToggle();
  
  //Toggle between minimize/maximize icons
	var iconToChange = $('.icon-to-change');
	  if(iconToChange.hasClass('fa-window-restore')){
		 iconToChange.removeClass('fa-window-restore')
	       .addClass('fa-minus')
	  }
	  else{
		 iconToChange.addClass('fa-window-restore')
	       .removeClass('fa-minus')
	  }    		         
  }); // end minimize/maximize fct

    //Later use  
$('#arrow_up').click(function() { 
	console.log("click5");
	 $("#network_tree").animate({height: "0px"});
	 $(".searchcontainer").css("display", "none");
	 $(".tabcontent").animate({height: "613px"});
   	$('#arrow_down').hide();
   	$('#arrow_up').hide();
   	$('#arrow_down_normal').show();
   	       
    });	
$('#arrow_down').click(function() {
	console.log("click6");
    	$(".searchcontainer").css("display", "block");
    	$("#network_tree").animate({height: "572px"});
    	$('#arrow_down').hide();
    	$('#arrow_up').hide();
    	$('#arrow_up_normal').show();
    	
    
    });

		var TreeArray=[];

		TreeArray.push("Site");//>>>
		TreeArray.push("Node");////>>>> Site/Node/Cell Case
		TreeArray.push("Cell");//>>>

//$( "#sortable" ).sortable();
$( "#sortable" ).sortable({
		    cancel: ".unsortable_sup",
		    items: "li:not(.unsortable)",
		});
$( "#sortable" ).disableSelection();


});

