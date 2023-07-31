
	function onSrcPlaceChanged(){
	    
		    const place = srcCityAutocomplete.getPlace();
		    if (!place.geometry) {
				$("#srcCitySearchNames").placeHolder = "Enter a city";
		    }

		    
		}
	function onDstPlaceChanged(){
	    
	    const place = dstCityAutocomplete.getPlace();
	    if (!place.geometry) {
				$("#dstCitySearchNames").placeHolder = "Enter a city";
	    }

	    
	}
	function LatLanMouse(map){		  
//mouse lat long
				map.addListener("mousemove", (mapsMouseEvent) => {
					
				    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(13) +" || "
				    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(13), null, 2));	    
				  }); 
}

	function panTo(newLat, newLng) {
	if (panPath.length > 0) {
	  // We are already panning...queue this up for next move
	  panQueue.push([newLat, newLng]);
	} else {
	  // Lets compute the points we'll use
	  panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
	  var curLat = map.getCenter().lat();
	  var curLng = map.getCenter().lng();
	  var dLat = (newLat - curLat)/STEPS;
	  var dLng = (newLng - curLng)/STEPS;

	  for (var i=0; i < STEPS; i++) {
		panPath.push([curLat + dLat * i, curLng + dLng * i]);
	  }
	  panPath.push([newLat, newLng]);
	  panPath.shift();      // LAZY SYNCRONIZED LOCK
	  setTimeout(doPan, 20);
	}
}
		 
function doPan() {
	var next = panPath.shift();
	if (next != null) {
	  // Continue our current pan action
	  map.panTo( new google.maps.LatLng(next[0], next[1]));
	  setTimeout(doPan, 20 );
	} else {
	  // We are finished with this pan - check if there are any queue'd up locations to pan to 
	  var queued = panQueue.shift();
	  if (queued != null) {
		panTo(queued[0], queued[1]);
	  }
	}
}

function handleLocationError(browserHasGeolocation, infowindow, pos) {
	infowindow.setPosition(pos);
	infowindow.setContent(
		browserHasGeolocation
		? "Error: The Geolocation service failed."
		: "Error: Your browser doesn't support geolocation."
	);
	infowindow.open(map);
	onsole.log("@handleLocationError method");
}

//The CenterControl adds a control to the map that recenters the map
//This constructor takes the control DIV as an argument.
function CenterControl(controlDiv, map) {
//Set CSS for the control border.
const controlUI = document.createElement("div");
controlUI.style.backgroundColor = "#fff";
controlUI.style.border = "2px solid #fff";
controlUI.style.borderRadius = "3px";
controlUI.style.boxShadow = "0 2px 6px rgba(0,0,0,.3)";
controlUI.style.cursor = "pointer";
controlUI.style.marginTop = "10px";
controlUI.style.textAlign = "center";
controlUI.title = "Click to recenter the map";
controlDiv.appendChild(controlUI);
//Set CSS for the control interior.
const controlText = document.createElement("div");
controlText.style.color = "rgb(25,25,25)";
controlText.style.fontFamily = "Roboto,Arial,sans-serif";
controlText.style.fontSize = "16px";
controlText.style.lineHeight = "36px";
controlText.style.paddingLeft = "5px";
controlText.style.paddingRight = "5px";
controlText.innerHTML = "Center Map";
controlUI.appendChild(controlText);
//Setup the click event listeners: simply set the map to Chicago.
controlUI.addEventListener("click", () => {
map.setCenter({lat: 33.8547, lng: 35.8623});


});


}

 function updateMap(){

    // deselect all the choices checked before
      $("#default").prop('checked', false);
      $("#landscape").prop('checked', false);
      $("#water").prop('checked', false);
      $("#transit").prop('checked', false);
      $("#poi").prop('checked', false);
      $("#road").prop('checked', false);

	 button = document.getElementById('blankMap');
	 data = button.getAttribute('data-map');
	 //console.log("Data is"+data);
     $('#nodeBtn').removeClass('activee');
     $('#cellBtn').removeClass('activee');
    
	   var centerlatlng = new google.maps.LatLng(Lat, Long);
	   var myOptions = {
	       zoom: 7,
	       center: centerlatlng,
	       disableDefaultUI : true,
	       zoomControl: true,
			zoomControlOptions: {
				position: google.maps.ControlPosition.LEFT_CENTER,
			},
	       scaleControl: true,
	       
	   };
 map = new google.maps.Map(document.getElementById("mapContainer"), myOptions);

	   map.setOptions({ styles: style});
	   const controlDiv2 = document.createElement("div");
	   DefaultZoomControl(controlDiv2, map);
	   map.controls[google.maps.ControlPosition.LEFT_CENTER].push(controlDiv2);
	   LatLanMouse(map);
var icon={
			   
			   path: google.maps.SymbolPath.CIRCLE,
		        fillColor: 'green',
		        fillOpacity: 0.9,
		        strokeColor: 'green',
		        strokeOpacity: 0.9,
		        strokeWeight: 1,
		        scale: 4	   
	   };
	   if(!(lst=="" || lst==null)){
	   for(i=0;i<lst.length;i++){

		    markerId=lst[i][2];
		    
			var latSite=lst[i][3];
			var lngSite=lst[i][4];
			const pos = new google.maps.LatLng(latSite,lngSite);

			
			const marker = new google.maps.Marker({
			        position: pos,
			        map:map,
			        icon:icon,
			        ID:markerId
			       
			         	    });}
	   }
	 }
	 
// add conditions for default google maps and its styles 
$('#default').click(function () {

setdefaultoptions(map);
	
})

$('#blank').click(function () {

setblankoptions(map);
	
})

$('#poi').click(function () {
	SetPoiOptions(map);
	
})

$('#road').click(function () {

	SetRoadOptions(map);

})

$('#transit').click(function () {
	SetTransitOptions(map);

})

$('#water').click(function () {

	SetWaterOptions(map);
	
})
// end of custom map styles function by yara 
	 
	 
// start of blank map styles by yara  
$('#mapgeography').click(function () {

	SetLightGeographyOptions(map);
 
})

$('#maplabels').click(function () {
	SetCountryBorders(map);	
		 
	})
		
$('#countrynames').click(function () {
			SetCountryNames(map);
		
	})
			
$('#countryprovince').click(function () {
			SetCountryProvice(map);
		
		})	 
		

			function restMAP(){
				var start = performance.now();
				document.getElementById("mapContainer").innerHTML ="";
				          			document.getElementById("network_tree").innerHTML ="";
					          		   	document.getElementById("dropDownCheckDiv").innerHTML ="";
	
						          			map = new google.maps.Map(document.getElementById("mapContainer"), {
						          										 
						          						center: { lat: 1, lng: 38 },

						          				 		mapTypeControl: true,

						          				 			mapTypeId: google.maps.MapTypeId.ROADMAP,
						          				 			mapTypeControlOptions: {
						          				 				style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
						          				 				position: google.maps.ControlPosition.TOP_CENTER,
						          				 			},
						          				 			
						          				 			
						          				 			zoomControl: true,
						          				 			zoomControlOptions: {
						          				 				position: google.maps.ControlPosition.LEFT_CENTER,
						          				 			},
						          				 			scaleControl: true,
						          				 			streetViewControl: true,
						          				 			streetViewControlOptions: {
						          				 				position: google.maps.ControlPosition.LEFT_TOP,
						          				 			},

						          				 			fullscreenControl: true,
						          				 			
						          				 });

map.setOptions({ minZoom: 3, maxZoom: 28});
						          			 var end = performance.now();
console.log("MAP: "+ (end-start));
restingMap();
	}

function restingMap(){

	var start = performance.now();
	str="<div id='coords' hidden style='font-size:13px;color:black; font-family: 'Times New Roman', Times, serif;font-weight:bold;background:#fbfbfb; opacity: 0.8;'></div>";
	$("#filterSection").append(str);
						          			
	str="<div class='dropDownControl' id='ddControl' style='width:165px;height:25px;margin-left:-5px;'>Filter Labels <img class='dropDownArrow' src='http://maps.gstatic.com/mapfiles/arrow-down.png'/></div><div class = 'dropDownOptionsDiv' id='myddOptsDiv' style='width:165px;'><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='manholesMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Manholes</span></div><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='handholesMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Handholes</span></div><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='fiberMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Fiber Cables</span></div> <div class='checkboxContainer' style='width:150px;'><input type='checkbox' id='tubeMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Fiber Tubes</span></div><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='strandMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Fiber Strands</span></div><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='dBMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Distribution Board</span></div><div class='checkboxContainer' style='width:150px;'><input type='checkbox' id='trenchMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Trench</span></div><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='ductMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Ducts</span></div><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='sitesMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Sites</span></div><div class='checkboxContainer' style='width:165px;'><input type='checkbox' id='clientsMapCheck_Labels' class='checkboxSpan'/><span class='checkboxLabel' style='font-Size:14px;'>Clients</span></div></div>";
	$("#dropDownCheckDiv").append(str);

	$("#ddControl").on("click",function(){
						          				
	if($("#myddOptsDiv").is(":visible")){

			console.log("visible");
			document.getElementById('myddOptsDiv').style.display = 'none';
	}
	else{
		console.log("not visible");
		document.getElementById('myddOptsDiv').style.display = 'block';
	}
	})
	coordsDiv = document.getElementById("coords");
	console.log("--- coordsDiv---"+$("#coords").html());
						          			  
	map.controls[google.maps.ControlPosition.BOTTOM_LEFT].push(coordsDiv);
						          			
	if(typeof mapMouseMoveListener !== 'undefined'){ // to remove the listener after submit if it was previously created 
		google.maps.event.removeListener(mapMouseMoveListener);
	}    
/*						          			  function showLatLng(event){
						          				coordsDiv.textContent =
						          			            'lat: ' + event.latLng.lat().toFixed(5) +", lng: " + event.latLng.lng().toFixed(5);
						          			  }
						          			  
						          			  mapMouseMoveListener = map.addListener('mousemove', showLatLng);
*/						          			  
						          			
	map.setOptions({ minZoom: 3, maxZoom: 28});
						          				
	document.getElementById("hidden_input").value="SNC";
	var end = performance.now();
	console.log("restingMap: "+ (end-start));

}