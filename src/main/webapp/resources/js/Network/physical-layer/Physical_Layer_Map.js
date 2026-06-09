/* Beginning for tree events and changing the styles */
function getContext() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

// To let the layer tab opened when the page is loaded
	
//function CreateMap_PhysicalLayer(ListProject,ListManhole,ListHandhole,fiberList,distribBoardList,fiberTubes,fiberStrands,fiberAuxiliary_Data,tubesAuxiliaries,strandsAuxiliaries,trenchList,trenchAuxiliary_Data,NodeList,filterFlag){
function CreateMap_PhysicalLayer(ListProject,ListManhole,ListHandhole,fiberList,distribBoardList,fiberTubes,fiberStrands,fiberAuxiliary_Data,tubesAuxiliaries,strandsAuxiliaries,trenchList,trenchAuxiliary_Data,NodeList,systemLong,systemLat){

	panPath = [];   // An array of points the current panning action will use
	panQueue = [];  // An array of subsequent panTo actions to take
	STEPS = 80;	
	markersManhole=[];		
	markersHandhole=[];
	markerClusterJunction = [];
	markerClusterSite = [];
	markersDistBoard=[];
	markersController = [];
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
						
	markerClusterJunction = new MarkerClusterer();
	markerClusterJunction.setMap(map);// to be checked !!!!
		
	markerClusterSite = new MarkerClusterer();
	markerClusterSite.setMap(map);// to be checked !!!!			

	markerClusterController = new MarkerClusterer();
	markerClusterController.setMap(map);
	
	center=new google.maps.LatLng(systemLat,systemLong);					
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
			
	markerClusterManhole.setOptions( {					  					
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
	
	$(".Initial_CurrentPhysicalLayer > .TreeSpan").on("click",function(){
		console.log("click1");
		map.setZoom(7);	
		map.setCenter(center);
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
	}
	
	if(ListHandhole!=null) {
		for(i=0;i<ListHandhole.length;i++){
			if(ListHandhole[i][5]>0){
				create_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"Junction","");											
				HandholeCheckFilter(ListHandhole[i][0]);
			}
			else {
				create_Marker_Click(ListHandhole[i][0],ListHandhole[i][1],ListHandhole[i][2],ListHandhole[i][3],markersHandhole,markerClusterHandhole,"No_Junction","");
				HandholeCheckFilter(ListHandhole[i][0]);
			}		
		}
	}
	
	allElementsCheckFilter();	
	filterMap_Labels("MANHOLE",markersManhole,"manholesMapCheck_Labels","marker-position-manhole","red");
	filterMap_Labels("HANDHOLE",markersHandhole,"handholesMapCheck_Labels","marker-position-handhole","#E5C523");	
	filterMap_Labels("DistributionBoard",markersDistBoard,"dBMapCheck_Labels","marker-position-dB","#5665F9");
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

function haversine_distance(lat1, lng1, lat2, lng2) {
	
	//console.log("Calculating LoS distance");

    var R = 6371; // Earth radius in kilometers

    lat1 = lat1 * (Math.PI / 180);
    lat2 = lat2 * (Math.PI / 180);

    var difflat = lat2 - lat1;
    var difflon = (lng2 - lng1) * (Math.PI / 180);

    var d = 2 * R * Math.asin(
        Math.sqrt(
            Math.sin(difflat / 2) * Math.sin(difflat / 2) +
            Math.cos(lat1) * Math.cos(lat2) *
            Math.sin(difflon / 2) * Math.sin(difflon / 2)
        )
    );
	
    return parseFloat(d.toFixed(3));
}


/*
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
*/

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

// to be deleted
/*
$(document).ready(function () {
	
	document.getElementById("Defaultbutton").click();
	$('#tree').toggleClass('orange');
	$('#gis').toggleClass('orange');


	document.getElementById("DefaultRightbutton").click();
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
*/