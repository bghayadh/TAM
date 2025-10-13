<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Fiber Optic Dashboard</title>
     <link rel="shortcut icon" href="">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">
<script src="${pageContext.request.contextPath}/resources/js/context-menu.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/xlsx.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jszip.js"></script>

</head>
<style>

.fixed-header{
	opacity: 1;
	filter: alpha(opacity=100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.mapButtons {
  color: orange;
  background-color: white;
  border-color: #939191f5;
  transition: border 0.3s; 
}

.mapButtons:hover{
    color: white;
	background-color: orange;
}

.showElement-Location:hover{
    color: white;
	background-color: #007bff;
}


.showElement-Location {
background-color:white;
color: #007bff;
 border-color: #939191f5;

}


#mapContainer {
height: 583px;
}
        
.legendHeader {
overflow: hidden;
background-color: #08526d;
padding: 10px 0px;
}

.legendContainer{
height: 800px;
position: relative;
}

.box{
width: 100%;
height: 100%;            
position: absolute;
top: 10px;
left: 0;
}

.stack-top{
z-index: 3;
margin: 120px; 
}

.title {
  margin:5px 0px;
  font-size: 25px;
  font-weight:600;
  font-family: 'Times New Roman', Times, serif;
}

.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 400px;
  overflow : auto;
}

 		
/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .input[type=text] {
    width: 100%;
    margin-top: 0;
  }
}



/* Extra small devices (phones, 600px and down) */
@media only screen and (max-width: 600px) {
  .modal-content, .modal-dialog {
  max-height: 100%;
  max-width: 100%;
  
 }
}
/* Small devices (portrait tablets and large phones, 600px and up) */
@media only screen and (max-width: 600px) {
.modal-content, .modal-dialog {
max-height: 100%;
max-width: 100%;

}
}

/* Medium devices (landscape tablets, 768px and up) */
@media only screen and (max-width: 768px) {
 .modal-content, .modal-dialog {
  max-height: 100%;
  max-width: 100%;
  
}
} 

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (max-width: 992px) {
  .modal-content, .modal-dialog {
  
  max-height: 100%;
        max-width: 100%;
  
 }
} 

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
 .modal-content, .modal-dialog {
 
 max-height: 100%;
 max-width: 100%;
}
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	width: 350px;
	z-index:9999;
}

.tooltip-container {
	position: relative;
    display: inline-block;
}
.tooltip-container .tooltip-text {
	visibility: hidden;
	width: 300px;
	background-color: gray;
    color: #fff;
    text-align: center;
    border-radius: 5px;
	padding: 5px;
	position: absolute;
	z-index: 1;
	bottom: 100%;
	left: 50%;
	margin-left: -100px;
	opacity: 0;
	transition: opacity 0.3s;            
}

.tooltip-container:hover .tooltip-text {
	visibility: visible;
	opacity: 1;
}

.mapDropdown {
    margin-left: 10px;
    padding: 5px;
    font-size: 1.2em;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #4CAF50; 
    color: white;
    cursor: pointer;
}

.mapDropdown option {
    color: black;
}
.btn-save {
    background-color: #C2CBC0 !important;
    border-color: #C2CBC0;
}

#fiberDashboard .fiberOwnerRow {
    height: 25px; /* adjust spacing */
}
</style>
<body>
  <c:set var="pg" value="dashboard" scope="session"  />
  <jsp:include page="../header.jsp"></jsp:include>
	
	<div class="container-fluid">     
	     <div class="row"><p></p></div>
			

	  

<div class="container-fluid" >     
	<br>			
<div class="wrapper" style="margin-top:-10px;">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
 
  
<div id="operationModal"class="modal fade  custom-class-assignedto-modal" tabindex="-1"role="dialog" aria-labelledby="exampleModalCenterTitle"aria-hidden="true"  data-keyboard="false" data-backdrop="static"><div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
	<div class="modal-content" style="width: 600px; height: 300px;"><div class="modal-header"style="background-color: #2678CC ; height: 55px; "><h5  class="modal-title"
style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Search By Place</h5><div style="float: right;"><button class="btn btn-save"style="color: black;  font-weight:bold; margin-top:-6px" onclick="MapOperationmarking()">Add Marker</button><button type="button"  onClick="clearMarkers()" class="btn btn-save" style=" margin-left:10px;color: black; font-weight:bold; margin-top:-6px" id="clearMarkers">Clear Markers</button>
<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"><i class='fa fa-times'></i></button><a class="close modalMinimize ml-3"> <i
class='fa fa-minus icon-to-change'></i></a></div></div><div class="modal-body"><div class="tab-pane" ><p></p><div class="container-fluid">



<br>
									<div class="row justify-content-center">
										<div class="col-sm-10" style="text-align: center">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceDiv">
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Search</b></span> 
														 <input type="text" id="placeSearch"  class="form-control text-input"  placeholder="" />														
												</div>
											</div>
										</div>	
										
										
																		
									</div>
									
									
									
											<div class="row justify-content-center">
										<div  id="markerName" class="col-sm-9" style="display:none;">
											<div class="form-group" >
												<div class="input-group-prepend"  >
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Marker Name </b></span>
														 	<input
														type="text" id="markerN"  class="form-control text-input"  />
												</div>
											</div>
										</div>										
									</div>			
													<div class="row justify-content-center">
										<div class="col-md-10">
											<div class="form-group" >
												<div class="input-group-prepend"  >
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Longitude </b></span>
														 	<input type="text" id="Lng" class="form-control text-input" readonly  />
												</div>
											</div>
										</div>										
									</div>							
                            	<div class="row justify-content-center">
										<div class="col-md-10">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px"> Latitude </b></span> <input type="text" id="Lat" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
			</div>
					</div>
					
				</div>
			</div>
		</div>
		</div>
  <div class="panel panel-default" style="margin-bottom:3px;" >
 
    <div id="collapseTwo" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
			<div style="text-align: center; margin-top: 10px;">
		<div><input id="mapLongLat" type='text' disabled style="width:300px;height:35px; text-align: center; margin-left:52px;position:relative;top:-1px;" /> 	<button type="button" class="btn btn-light" data-placement="top"title="Map Operations" data-toggle="modal" onclick="mapOperation()"><i class="fas fa-search"></i></button> 
 </div>
		</div>
		
	     <div class="legendContainer">
  <div class="card-body">      
   <div class="box stack-top" id="legendDiv" style="position: relative;bottom:50px;width: 200px; float:left; background:white; margin:37px; margin-top:150px;display: none">   
      <!-- Header -->
     <div class="legendHeader" id="legendHeader" style="height: 30px"> <span style="color:white;font-weight:bold; font-size:1.9ex;display:inline-block;position: relative;margin-left:30px;">Fiber Owners Legend</span> </div>

      <!-- Table -->
      <div id="tableDiv">
        <table id="fiberDashboard" style="width: 90%; border-collapse: collapse; margin-top:7px; margin-bottom:7px">
          <c:forEach var="ownerColor" items="${fiberOwnersWithColors}">
            <tr class="fiberOwnerRow" style="height: 15px;">
              <!-- Color box + owner name -->
              <td style="padding-left: 15px; padding-top: 10px; display: flex; align-items: center; gap: 8px;">
                <div style="width: 40px; height: 3px; background-color: ${ownerColor[1]};"></div>
                <label style="color: black; font-weight: bold; font-size: 1em; margin: 0;">
                  ${ownerColor[0]}
                </label>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>

    </div>
  </div>

  <div class="card-body">
    <div class="box" id="mapContainer"></div>
  </div>
</div>

    </div>
 </div>
      </div>
      </div>  
    </div>
  </div>
</div>

</body>

<script>

var map ;
var fiberIdList = [];
var fiberOwnerList = [];
var allFiberCables = [];
var fiberArray = [];
var panPath = [];
var panQueue = [];
var STEPS = 80;


function initMap() {
    map = new google.maps.Map(document.getElementById("mapContainer"), {
        center: { lat: 1, lng: 38 },
        zoom: 6,
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

    var fiberList = ${fiberListJson != null ? fiberListJson : '[]'};
    var fiberAuxiliary_Data = ${fiberAuxiliaryDataJson != null ? fiberAuxiliaryDataJson : '[]'};

    console.log(fiberList);
    console.log(fiberAuxiliary_Data);

    if (fiberList != null) {
        for (let i = 0; i < fiberList.length; i++) {
            const fiberId = fiberList[i][4];
            allFiberCables.push(fiberId);

            // Initialize data structures
            window[fiberId] = [];
            window["mapPoints_" + fiberId] = [];
            window["mapPointsNames_" + fiberId] = [];

            // ---------- Source Point ----------
            let src;
            if (fiberList[i][5] && fiberList[i][5] !== "null") {
                src = fiberList[i][5] + ":" + fiberList[i][7] + ":" + fiberList[i][6];
            } else if (
                fiberList[i][6] &&
                ["MH", "HH", "DB", "CUST"].includes(fiberList[i][6].split("_")[0])
            ) {
                src = fiberList[i][6] + ":" + fiberList[i][7];
            } else {
                src = fiberList[i][7];
            }
            window["mapPointsNames_" + fiberId].push(src);

            // ---------- Destination Point ----------
            let dst;
            if (fiberList[i][6] && fiberList[i][8] !== "null") {
                dst = fiberList[i][8] + ":" + fiberList[i][10] + ":" + fiberList[i][9];
            } else if (
                fiberList[i][9] &&
                ["MH", "HH", "DB", "CUST"].includes(fiberList[i][9].split("_")[0])
            ) {
                dst = fiberList[i][9] + ":" + fiberList[i][10];
            } else {
                dst = fiberList[i][10];
            }
            window["mapPointsNames_" + fiberId].push(dst);

            // ---------- Map Points & Bounds ----------
            window[fiberId] = fiberList[i];
            window["bounds_" + fiberId] = new google.maps.LatLngBounds();

            let srcLatLng = new google.maps.LatLng(fiberList[i][1], fiberList[i][0]);
            let dstLatLng = new google.maps.LatLng(fiberList[i][3], fiberList[i][2]);

            window["bounds_" + fiberId].extend(srcLatLng);
            window["bounds_" + fiberId].extend(dstLatLng);

            window["mapPoints_" + fiberId].push(srcLatLng);
            window["mapPoints_" + fiberId].push(dstLatLng);

            // ---------- Misc ----------
            window["fiberCheckPoints_" + fiberId] = "unchecked";
            window["fiberCheckRealPoints_" + fiberId] = "unchecked";
            window["fiberCheckSequence_" + fiberId] = "unchecked";

            fiberIdList.push(fiberId);
            fiberOwnerList.push(fiberList[i][22]);
            window["FiberColor_" + fiberList[i][22]] = fiberList[i][23];
        }
    }

    // ---------- Auxiliary Points ----------
    if (fiberAuxiliary_Data != null) {
        for (let i = 0; i < fiberAuxiliary_Data.length; i++) {
            let auxLatLng = new google.maps.LatLng(
                fiberAuxiliary_Data[i][1],
                fiberAuxiliary_Data[i][0]
            );

            let auxPoint = "";
            if (fiberAuxiliary_Data[i][3] && fiberAuxiliary_Data[i][3] !== "null") {
                auxPoint =
                    fiberAuxiliary_Data[i][3] +
                    ":" +
                    fiberAuxiliary_Data[i][5] +
                    ":" +
                    fiberAuxiliary_Data[i][4];
            } else if (
                fiberAuxiliary_Data[i][4] &&
                ["MH", "HH", "DB"].includes(fiberAuxiliary_Data[i][4].split("_")[0])
            ) {
                auxPoint =
                    fiberAuxiliary_Data[i][4] + ":" + fiberAuxiliary_Data[i][5];
            } else if (
                fiberAuxiliary_Data[i][5] &&
                fiberAuxiliary_Data[i][5].includes("Auxiliary_Point")
            ) {
                auxPoint =
                    fiberAuxiliary_Data[i][7] + ":" + fiberAuxiliary_Data[i][5];
            } else {
                auxPoint = fiberAuxiliary_Data[i][5];
            }

            let fiberAuxId = fiberAuxiliary_Data[i][6];

            // Insert auxiliary point before destination
            let mapPoints = window["mapPoints_" + fiberAuxId];
            let mapNames = window["mapPointsNames_" + fiberAuxId];
            if (mapPoints && mapNames) {
                mapPoints.splice(mapPoints.length - 1, 0, auxLatLng);
                mapNames.splice(mapNames.length - 1, 0, auxPoint);
                window["bounds_" + fiberAuxId].extend(auxLatLng);
            }
        }
    }

    // ---------- Build Paths for All Fibers ----------
    for (let j = 0; j < fiberIdList.length; j++) {
        let fiberId = fiberIdList[j];
        let path = window["mapPoints_" + fiberId];
        let color = window["FiberColor_" + window["" + fiberId][22]] || "blue";
        buildPath(
            fiberId,
            path,
            fiberArray,
            allFiberCables,
            "FiberPath_f_",
            color,
            0.7,
            4.5,
            "blue",
            13
        );
        fiberArray[fiberId].setMap(map);
    }
    getLongLatMouseMove(map); 
    placeAutoComplete();
    $( "#legendDiv" ).draggable(); 
  //Add legend button under zoom control on map
	const mapLegendControlDiv = document.createElement("div");
	ShowHideMapLegend(mapLegendControlDiv, map);
    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(mapLegendControlDiv);

    const DefaultZoomDiv = document.createElement("div");
    DefaultZoomControl(DefaultZoomDiv, map);
    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

   
    $("#legendDiv").toggle(); // to open the legend 

    $("#operationModal").draggable(); 
    

}


function getContext() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function buildPath(
	    Id,
	    path,
	    pathArray,
	    allPathArray,
	    folder,
	    strokeColor,
	    strokeOpacity,
	    strokeWeight,
	    fontColor,
	    IdNb
	) {
	    const flightPath = new google.maps.Polyline({
	        path: path,
	        geodesic: false,
	        strokeColor: strokeColor,
	        ID: Id,
	        strokeOpacity: strokeOpacity,
	        strokeWeight: strokeWeight,
	    });

	    flightPath.metadata = { id: Id };
	    pathArray[Id] = flightPath;
	    pathArray.push(flightPath);

	    if (!allPathArray.includes(Id)) {
	        allPathArray.push(Id);
	    }

	    const lablCoords = google.maps.geometry.spherical.interpolate(
	        path[0],
	        path[1],
	        1
	    );

	    const text =
	        IdNb !== 0
	            ? window["" + Id][IdNb]
	            : $("#" + Id + " > .TreeSpan").text().split("/")[0];

	    pathArray[Id].mapLabel = new MapLabel({
	        text: text,
	        position: lablCoords,
	        fontSize: 13,
	        fontColor: fontColor,
	        align: "top",
	    });

	    // ---------- Event Listeners ----------
	    flightPath.addListener("click", function (e) {
	        const IdSelected = this.ID;
	        pathMapListener(IdSelected, folder);
	        if (typeof draw !== "undefined" && draw === true) {
	            createPathh(e);
	        }
	    });

	    const menuBox = document.getElementById("deletePathMenu");
	    google.maps.event.addListener(flightPath, "rightclick", function (e) {
	        for (let prop in e) {
	            if (e[prop] instanceof MouseEvent) {
	                ShowContextMenuGoolge(menuBox, e[prop].clientX, e[prop].clientY);
	                break;
	            }
	        }
	        e.stop();
	        window.selectedPathId = Id;
	        window.selectedPath = flightPath;

	        if (typeof deleting !== "undefined" && deleting === true) {
	            deleteAuxPath(e);
	        }

	        google.maps.event.addListener(map, "click", function () {
	            HideContextMenuGoolge(menuBox);
	        });
	    });
	}
function getLongLatMouseMove(map){		  
	map.addListener("mousemove", (mapsMouseEvent) => {
	   $("#mapLongLat").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(13) +" || "
	    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(13), null, 2));	    
	});					 
}

//======================= Global Variables =======================
let placeMarkers = {};
let infowindow;
var iconPlace = {
    url: getContext() + "/resources/markers/Map-Marker-PNG-File.png",
    scaledSize: new google.maps.Size(35, 35),
};


let autocomplete;

function initAutocomplete() {
  const input = document.getElementById("placeSearch");

  // Initialize Google Autocomplete
  autocomplete = new google.maps.places.Autocomplete(input, {
    types: ['geocode'], // or 'establishment' if you prefer business places
    fields: ['geometry', 'name', 'formatted_address'],
  });

  // When a place is selected from the dropdown
  autocomplete.addListener("place_changed", function () {
    const place = autocomplete.getPlace();

    if (!place.geometry || !place.geometry.location) {
      alert("No location data found for the selected place.");
      return;
    }

    const lat = place.geometry.location.lat();
    const lng = place.geometry.location.lng();

    // Fill the fields
    document.getElementById("Lat").value = lat.toFixed(6);
    document.getElementById("Lng").value = lng.toFixed(6);

    console.log("Selected place:", place.formatted_address);
    console.log("Latitude:", lat, "Longitude:", lng);
  });
}

// Initialize autocomplete once the Google API loads
window.initAutocomplete = initAutocomplete;



// ======================= Place Marker Function =======================
function MapOperationmarking() {
    const place = document.getElementById("placeSearch").value.trim();
    const markerName = document.getElementById("markerN").value.trim();
    const Lat = document.getElementById("Lat").value.trim();
    const Lng = document.getElementById("Lng").value.trim();

    if (!place && !markerName) {
        alert("Please enter a place or marker name.");
        return;
    }

    const placeId = Math.floor(Math.random() * 1000) + (place || markerName);

    createPlaceMarker(placeId, place, markerName, Lat, Lng);

    panTo(Lat, Lng);
    map.setZoom(9);

    if (!infowindow) {
        infowindow = new google.maps.InfoWindow();
    } else {
        infowindow.close();
    }

    infowindow.setContent(place || markerName);
    infowindow.open(map, placeMarkers[placeId]);
}

// ======================= Create Marker =======================
function createPlaceMarker(Id, Name, markerName, Lat, Long) {
    const pos = new google.maps.LatLng(parseFloat(Lat), parseFloat(Long));
    const label = Name || markerName;

    // Remove old marker with same ID if exists
    if (placeMarkers[Id]) {
        placeMarkers[Id].setMap(null);
    }

    const marker = new google.maps.Marker({
        position: pos,
        icon: iconPlace,
        map: map
    });

    placeMarkers[Id] = marker;

    const info = new google.maps.InfoWindow({ content: label });

    google.maps.event.addListener(marker, 'click', function () {
        info.open(map, marker);
    });
}

// ======================= Google Place Autocomplete =======================
function placeAutoComplete() {
    const address = document.getElementById('placeSearch');
    const autocomplete = new google.maps.places.Autocomplete(address);
    autocomplete.setTypes(['geocode']);

    google.maps.event.addListener(autocomplete, 'place_changed', function () {
        const place = autocomplete.getPlace();
        if (!place.geometry) return;

        document.getElementById('Lat').value = place.geometry.location.lat();
        document.getElementById('Lng').value = place.geometry.location.lng();
    });
}

google.maps.event.addDomListener(window, 'load', placeAutoComplete);

// ======================= Modal & Field Controls =======================
function mapOperation() {
    clearMapOperationFeilds();
    $("#operationModal").modal('show');
}

function placeFeild() {
    clearMapOperationFeilds();
    document.getElementById('placeSearch').style.display = "block";
    document.getElementById('Searchh').style.display = "none";
    placeAutoComplete();
}

function clearMapOperationFeilds() {
    document.getElementById('placeSearch').value = "";
    document.getElementById('Lat').value = "";
    document.getElementById('Lng').value = "";
    document.getElementById('markerN').value = "";
}

// ======================= Smooth Pan to Marker =======================
function panTo(newLat, newLng) {
    const STEPS = 20;
    const panPath = [];
    const panQueue = [];

    if (panPath.length > 0) {
        panQueue.push([newLat, newLng]);
    } else {
        panPath.push("LOCK");
        const curLat = map.getCenter().lat();
        const curLng = map.getCenter().lng();
        const dLat = (newLat - curLat) / STEPS;
        const dLng = (newLng - curLng) / STEPS;

        for (let i = 0; i < STEPS; i++) {
            panPath.push([curLat + dLat * i, curLng + dLng * i]);
        }
        panPath.push([newLat, newLng]);
        panPath.shift();

        function doPan() {
            const next = panPath.shift();
            if (next) {
                map.panTo(new google.maps.LatLng(next[0], next[1]));
                setTimeout(doPan, 20);
            } else if (panQueue.length > 0) {
                const queued = panQueue.shift();
                panTo(queued[0], queued[1]);
            }
        }

        setTimeout(doPan, 20);
    }
}

// ======================= Closing Confirmation =======================
function ClosingConfirm() {
    const modal = $("#operationModal");
    const result = confirm('Are you sure you want to close?');
    if (result) modal.modal('hide');
    else modal.modal('show');
}

function clearMarkers(){
		  	for (var key in placeMarkers) {
	    if (placeMarkers.hasOwnProperty(key)) {
	          var marker = placeMarkers[key];
	        marker.setMap(null);
	    }
	    }
}

//Add legend button under zoom control on map
function ShowHideMapLegend(controlDiv, map) {
	
    const controlUI = document.createElement("dv");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"><i class="fas fa-arrow-right fa-lg "></i></button>';
    controlUI.title = "Open Legend";
    controlDiv.appendChild(controlUI);

    controlUI.addEventListener("click", () => {
    	$("#legendDiv").toggle();        
     });

  }
 
//Add defaultZoom button under zoom control on map
function DefaultZoomControl(controlDiv, map) {
	
    const controlUI = document.createElement("div");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.style.marginTop = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;" ><i class="fa fa-undo fa-lg "></i></button>';
    controlUI.title = "Reset Default Zoom";
    controlDiv.appendChild(controlUI);

    controlUI.addEventListener("click", () => {
    	var center=new google.maps.LatLng(1,38);
        map.setCenter(center);
		map.setZoom(7);	
     });

  }


</script>
<script
  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=geometry,places&v=3.43&amp">
</script>

<!-- 2️⃣ Load MapLabel AFTER Maps API is ready -->
<script src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>
<script>
  window.onload = function () {
    initMap();
  };
</script>
	
</html>
