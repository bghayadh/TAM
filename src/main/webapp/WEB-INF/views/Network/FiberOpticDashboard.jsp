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
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
        <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
        <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
	
	    <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRowsStrandUtilizationReport.js"></script>
		
		
		 <!--Network_Index.css is included here in order to use the css of right click menu  -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">
		
	    
	    <!--  MultiSelect Script -->
        <link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet"    >
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>	
			
			
		<!-- Google Maps Script -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	
	    <!-- export scripts -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
        
        <!-- To Draw Borders on map for start/end coordinates -->
        <script src="${pageContext.request.contextPath}/resources/js/Network/BordersFindNearest.js"></script>

  
       
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
height: 700px;
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

</style>
<body>
  <c:set var="pg" value="dashboard" scope="session"  />
  <jsp:include page="../header.jsp"></jsp:include>
	
	<div Style=" left: 0; bottom: 0;" id="StrandUtilizationReportDiv">
	<div class="container-fluid">     
	     <div class="row"><p></p></div>
			

	  

<div class="container-fluid" >     
	<br>			
<div class="wrapper" style="margin-top:-10px;">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
 
  
<div id="mapOperationModal"class="modal fade  custom-class-assignedto-modal" tabindex="-1"role="dialog" aria-labelledby="exampleModalCenterTitle"aria-hidden="true"  data-keyboard="false" data-backdrop="static"><div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
	<div class="modal-content" style="width: 850px;"><div class="modal-header"style="background-color: #2678CC ; height: 55px; "><h5  class="modal-title"
style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Operations</h5><div style="float: right;"><button class="btn btn-save"style="color: black; font-weight:bold; margin-top:-6px" onclick="MapOperationmarking()">Add Marker</button><button type="button"  onClick="clearMarkers()" class="btn btn-save" style=" margin-left:10px;color: black; font-weight:bold; margin-top:-6px" id="clearMarkers">Clear Markers</button>
<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"><i class='fa fa-times'></i></button><a class="close modalMinimize ml-3"> <i
class='fa fa-minus icon-to-change'></i></a></div></div><div class="modal-body"><div class="tab-pane" ><p></p><div class="container-fluid">
<div class="row">
    <div  style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                    <b>By Site</b>
                    <input type="checkbox" id="site_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                </span>
            </div>
        </div>
    </div>

    <div style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                    <b>By Client</b>
                  <input type="checkbox" id="customer_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                </span>
            </div>
        </div>
    </div>
 
   <div style="display: none;" id="manholeSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 120px; font-size: 12px;">
                        <b>By Manhole</b>
                        <input type="checkbox" id="manhole_operationAutoComplete" style="margin-left: 10px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>


   <div style="display: none;" id="handholeSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 120px; font-size: 12px;">
                        <b>By Handhole</b>
                        <input type="checkbox" id="handhole_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div style="display: none;" id="dbSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                        <b>By DB</b>
                        <input type="checkbox" id="db_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>

    <div style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 95px; font-size: 12px;">
                    <b>By Place</b>
                    <input type="checkbox" id="place_operationAutoComplete" style="margin-left: 10px;" class="mapOperationAutoComplete" onclick="placeFeild()">
                </span>
            </div>
        </div>
    </div>

    <div>
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 95px; font-size: 12px;">
                    <b>Generic</b>
                    <input type="checkbox" id="generic_operationAutoComplete" style="margin-left: 10px;" class="mapOperationAutoComplete" onclick="placeFeild()">
                </span>
            </div>
        </div>
    </div>
</div>


<br>
									<div class="row">
										<div class="col-sm-9">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceDiv">
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Search</b></span> <input
														type="text" id="Searchh"  class="form-control text-input"  />
														 <input type="text" id="placeSearch"  class="form-control text-input" style="display:none" placeholder="" />														
												</div>
											</div>
										</div>	
										
										
											<div class="col-sm-3"  id="setCoordOperation" style="display:none;">
											<div class="form-group">
												<button id="setCoordOperationBtn" title="Get Coordinate From Map" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">Set Coordinate</button>
											</div>
										</div>									
									</div>
									
									
									
										<div class="row" >
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
												<div class="row" >
										<div class="col-md-9">
											<div class="form-group" >
												<div class="input-group-prepend"  >
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Longitude </b></span>
														 	<input type="text" id="Lng" class="form-control text-input" readonly  />
												</div>
											</div>
										</div>										
									</div>							
                               <div class="row">
										<div class="col-md-9">
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
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
		</div>
  <div class="panel panel-default" style="margin-bottom:3px;" >
 
    <div id="collapseTwo" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
			<div style="text-align: center; margin-top: 10px;">
		<div><input id="mapLongLat" type='text' disabled style="width:300px;height:35px; text-align: center; margin-left:52px;position:relative;top:-1px;" /></div>
		</div>
		
	 <!--  	<button type="button" class="btn btn-light" data-placement="top"title="Map Operations" data-toggle="modal" onclick="mapOperation()"><i class="fas fa-toolbox"></i></button> -->
      <div class="legendContainer">
    
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
</div>

</body>

<script>

var map ;
var fiberIdList = [];
var fiberOwnerList = [];
var allFiberCables = [];
var fiberArray = [];


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


function MapOperationmarking(){
	var search= document.getElementById("Searchh").value.toString();
	var place= document.getElementById("placeSearch").value.toString();
	var markerName= document.getElementById("markerN").value.toString();
	var placeId=Math.floor(Math.random() * 1000)+place;
	var genericId;
	    if (place) {
	       
	        genericId = Math.floor(Math.random() * 1000) + place;
	    } else {
	         genericId = Math.floor(Math.random() * 1000) + markerName;
	    }
	var id=search.split(":")[0];
	var type =id.split("_")[0];
	var name=search.split(":")[1];
	var Lng=document.getElementById("Lng").value;
	var Lat=document.getElementById("Lat").value;
	console.log(Lng);
	console.log("type"+type);

	       
	    


		
		

		
	 if(document.getElementById("place_operationAutoComplete").checked){
			placeMarkers=[];
			placeMarkers=[placeId];
		
	createPlaceMarker(placeId,place,"",Lat,Lng,placeMarkers,"place");

	panTo(Lat, Lng);
	map.setZoom(6);
					if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(place);
				infowindow.open(map,placeMarkers[""+placeId]);
			
	}


	

	}
var iconPlace ={
		url:""+getContext()+"/resources/markers/Map-Marker-PNG-File.png", // url
		scaledSize: new google.maps.Size(35, 35), // scaled size
	};
placeMarkers=[];
function MapOperationmarking(){
	var search= document.getElementById("Searchh").value.toString();
	var place= document.getElementById("placeSearch").value.toString();
	var markerName= document.getElementById("markerN").value.toString();
	var placeId=Math.floor(Math.random() * 1000)+place;
	var genericId;
	    if (place) {
	       
	        genericId = Math.floor(Math.random() * 1000) + place;
	    } else {
	         genericId = Math.floor(Math.random() * 1000) + markerName;
	    }
	var id=search.split(":")[0];
	var type =id.split("_")[0];
	var name=search.split(":")[1];
	var Lng=document.getElementById("Lng").value;
	var Lat=document.getElementById("Lat").value;
	console.log(Lng);
	console.log("type"+type);

	       
	    
	if(document.getElementById("generic_operationAutoComplete").checked){
		
		placeMarkers=[genericId];
	createPlaceMarker(genericId,place,markerName,Lat,Lng,placeMarkers,"generic");

	panTo(Lat, Lng);
	map.setZoom(6);
					if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(place ? place : markerName);
				infowindow.open(map,placeMarkers[""+genericId]);
			
	}

		
		

		
	 if(document.getElementById("place_operationAutoComplete").checked){
			placeMarkers=[placeId];
		
	createPlaceMarker(placeId,place,"",Lat,Lng,placeMarkers,"place");

	panTo(Lat, Lng);
	map.setZoom(6);
					if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(place);
				infowindow.open(map,placeMarkers[""+placeId]);
			
	}


	else if(type=="MH"){
	$("#"+id).children('input:checkbox').prop('checked', true);
	$("#manholeCheckAllBoq").prop('checked', true);
	markerClusterManhole.addMarker(markersManhole[""+id]);
	ManholeCheckFilter(id);	
	panTo(Lat, Lng);
				map.setZoom(11);
				if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(id);
				infowindow.open(map,markersManhole[""+id]);
							
				
	    	
			


	}
	else if(type=="HH"){
	$("#"+id).children('input:checkbox').prop('checked', true);
	$("#handholeCheckAllBoq").prop('checked', true);
	markerClusterHandhole.addMarker(markersHandhole[""+id]);
	HandholeCheckFilter(id);	
	panTo(Lat, Lng);
	map.setZoom(11);
				if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(id);
				infowindow.open(map,markersHandhole[""+id]);
							
	}
	else if(type=="DB"){
		$("#"+id).children('input:checkbox').prop('checked', true);
		$("#distBoardCheckAllBoq").prop('checked', true);
		
		if(window[""+id][8]=="backbone") {
			markerClusterBackboneDistBoard.addMarker(markersDistBoard[""+id]);
			DistributionBoardCheckFilter(id,"",markerClusterBackboneDistBoard);	
		}
		else if(window[""+id][8]=="metro") {
			markerClusterMetroDistBoard.addMarker(markersDistBoard[""+id]);
			DistributionBoardCheckFilter(id,"",markerClusterMetroDistBoard);	

		}
		else if(window[""+id][8]=="access") {
			markerClusterAccessDistBoard.addMarker(markersDistBoard[""+id]);
			DistributionBoardCheckFilter(id,"",markerClusterAccessDistBoard);	
		}
		panTo(Lat, Lng);
		map.setZoom(11);
				if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(id);
				infowindow.open(map,markersDistBoard[""+id]);
	}
	else if(type=="WARE"){
	if(siteFlag == 0){
		getSite("addMaker",id);
		}
		
		else{
			
			sitMarker(id);
		}

	}
	else if(type=="CUST"){
	createSiteCltMarker(id,search,Lat,Lng,siteCltSrcMarkers);
	panTo(Lat, Lng);
	map.setZoom(6);
				if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(id);
				infowindow.open(map,siteCltSrcMarkers[""+id]);
	}


	}

	function mapOperationAutoComplete(checkboxClass,srcID,srcLong,srcLat){

		var url ="emptyUrl";
		var dataTarget="";

		if($('#'+srcID).data('ui-autocomplete') != undefined){
			$('#'+srcID).autocomplete("destroy");
		}
		
		$("#"+srcID).autocomplete({
			source: function(request, response) {

			var search= $("#"+srcID).val();
			var checkedCheckboxAutocomplete=" ";
			
		//Get the id of checked checkbox
		$('input:checkbox[class="' + checkboxClass + '"]:checked').each(function () {
			var checkedCheckbox =  $(this).attr("id");
			checkedCheckboxAutocomplete = checkedCheckbox.split("_");			
			checkedCheckboxAutocomplete=checkedCheckboxAutocomplete[0]; 	
		});

		//On change , get the id of changed checked checkbox
		$("."+checkboxClass).change(function() {
			var checkedCheckbox = this.id;
			checkedCheckboxAutocomplete = checkedCheckbox.split("_");			
			checkedCheckboxAutocomplete=checkedCheckboxAutocomplete[0];
		});
			     
		if(checkedCheckboxAutocomplete=="manhole") {


			url ='getManholeData';
			dataTarget = {					
				"search":search,
			}
		}
		else if(checkedCheckboxAutocomplete=="handhole") {
			
		
			url ='getHandholeData';
			dataTarget = {					
				"search":search,
			}
		}
		else if(checkedCheckboxAutocomplete=="db") {
		
			url ='getDbData';
			dataTarget = {					
				"search":search,
			}
		}
		else if(checkedCheckboxAutocomplete=="site") {

			url='GetAllWarehouse';
			dataTarget = {
	       		"WareName":search,
				"warehouseName" : search,
				"SiteId":search,
			 }
		}
		
		else if(checkedCheckboxAutocomplete=="customer") {

			url='GetAllNetworkCustomer';
			dataTarget = {					
					"search":search,
				}
		}

	  if(url !="emptyUrl") {
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/'+url,
				data: dataTarget,				
			 	dataType: "json",
				success: function (data) {
					if (data != null) {
						response(data.globalList);                   
					}
				},				
				error: function(result) {
					alert("Error");
				}
			});	
		}				
		}, minLength:0, maxShowItems: 40, scroll:true,
			select: function (event, ui) {		   

				if(ui.item[0].split("_")[0]=="MH"){
					this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
					$("#"+srcLong).val(ui.item[3]);
					$("#"+srcLat).val(ui.item[4]);
				}	
				else if(ui.item[0].split("_")[0]=="HH"){
					this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
					$("#"+srcLong).val(ui.item[3]);
					$("#"+srcLat).val(ui.item[4]);
					}
				else if(ui.item[0].split("_")[0]=="DB"){
					this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
					$("#"+srcLong).val(ui.item[3]);
					$("#"+srcLat).val(ui.item[4]);
				}		
				else if(ui.item[0].split("_")[0]=="WARE"){
					$("#"+srcLong).val(ui.item[3]);
					$("#"+srcLat).val(ui.item[4]);
					this.value = (ui.item ? ui.item[0]+':'+ui.item[1]+':'+ui.item[2] : '');
			}	
			else{
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				$("#"+srcLong).val(ui.item[4]);
				$("#"+srcLat).val(ui.item[5]);
				}	
				return false;
			},
				}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
					
						if(item[0].split("_")[0]=="WARE" || item[0].split("_")[0]=="CUST" ) {
							 return $("<li class='each'>")
				                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
				                   item[0] + "</span><br><span class='desc'>" +
				                    item[1] +', '+ item[2] + "</span></div>")
				                .appendTo(ul);
						}
						else {
							 return $("<li class='each'>")
				                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
				                   item[0] + "</span><br><span class='desc'>" +
				                    item[1] + "</span></div>")
				                .appendTo(ul);
							}
				};
			$("#"+srcID).focus(function(){
				   if (this.value == ""){
					   $(this).autocomplete("search");
				   }						
			});
	} 

	function createPlaceMarker(Id, Name, markerName, Lat, Long, placeMarkers, type) {
	    // Define the icon based on the type
	    let icon;
	    if (type.startsWith("place")) {
	        icon = iconPlace;
	    } else if (type.startsWith("generic")) {
	        icon = iconGeneric;
	    } else {
	        console.error("Unknown type:", type);
	        return;
	    }

	    // Create marker position and data
	    const pos = new google.maps.LatLng(Lat, Long);
	     const data = `<div>${Name ? Name : markerName}</div>`;

	    // Check if the marker already exists
	    if (!placeMarkers[Id]) {
	        // Create a new marker
	        const placeMarker = new google.maps.Marker({
	            position: pos,
	            icon: icon,
	            map: map
	        });

	        // Attach metadata to the marker
	        placeMarker.metadata = { id: Id };
	       placeMarkers[Id] = placeMarker;

	        // Create and attach the info window
	        const infowindow = new google.maps.InfoWindow({
	            content: data
	        });

	        google.maps.event.addListener(placeMarker, 'click', function () {
	            infowindow.open(map, placeMarker);
	        });
	    } else {
	        // Update existing marker
	        const existingMarker = placeMarkers[Id];
	        if (existingMarker.map !== map) {
	            existingMarker.setMap(map);
	        }
	        existingMarker.setPosition(pos);
	        existingMarker.setIcon(icon);

	        // Update existing info window content
	        const infowindow = new google.maps.InfoWindow({
	            content: data
	        });

	        google.maps.event.clearListeners(existingMarker, 'click'); // Remove existing click listeners
	        google.maps.event.addListener(existingMarker, 'click', function () {
	            infowindow.open(map, existingMarker);
	        });
	    }

	    // Make sure the marker is added to the map
	    placeMarkers[Id].setMap(map);
	}

	function placeAutoComplete() {


		      var address = (document.getElementById('placeSearch'));
		      var autocomplete = new google.maps.places.Autocomplete(address);
		      autocomplete.setTypes(['geocode']);
		      google.maps.event.addListener(autocomplete, 'place_changed', function() {
		          var place = autocomplete.getPlace();
		          if (!place.geometry) {
		              return;
		          }

		      var address = '';
		      if (place.address_components) {
		          address = [
		              (place.address_components[0] && place.address_components[0].short_name || ''),
		              (place.address_components[1] && place.address_components[1].short_name || ''),
		              (place.address_components[2] && place.address_components[2].short_name || '')
		              ].join(' ');
		      }
		      document.getElementById('Lat').value = place.geometry.location.lat();
		      document.getElementById('Lng').value = place.geometry.location.lng();
		
		      
		      });
		

		 google.maps.event.addDomListener(window, 'load', placeAutoComplete);
		 }
		 
		 function mapOperation(){
		clearMapOperationFeilds();
		$( "#mapOperationModal" ).modal('show');
		uncheckAutoCompleteCheckboxes("mapOperationAutoComplete");
		$("#loading").remove();
		$("#Searchh").unbind();
		mapOperationAutoComplete("mapOperationAutoComplete","Searchh","Lng","Lat");
		}
	function mapFeilds(){
		clearMapOperationFeilds();
		document.getElementById('placeSearch').style.display = "none";
		 document.getElementById('Searchh').style.display = "block";     
		}
	function placeFeild(){
		clearMapOperationFeilds();
	document.getElementById('placeSearch').style.display = "block";
	document.getElementById('Searchh').style.display = "none";
	placeAutoComplete();
	} 
	function clearMapOperationFeilds(){
		   document.getElementById('placeSearch').value = "";
			 document.getElementById('Searchh').value = "";
			 document.getElementById('Lat').value = "";
			 document.getElementById('Lng').value = "";
			 document.getElementById('markerN').value = "";
	}

	function sitMarker(id){
		
			$("#"+id).children('input:checkbox').prop('checked', true);
	                            $("#siteCheckAllBoq").prop('checked', true);
		
	                           markerClusterSite.addMarker(markersSite[""+id]);	
	                           panTo(Lat, Lng);
	                           map.setZoom(6);
				if(typeof infowindow!== 'undefined'){
					infowindow.close();
				}
				else{
					infowindow = new google.maps.InfoWindow();
				}

				infowindow.setContent(id);
				infowindow.open(map,siteCltSrcMarkers[""+id]);
				
							}


	function ClosingConfirm(){
		var c=null;
	 if($("#mapOperationModal").is(':visible')){
		c=$("#mapOperationModal");}
	

		var result= confirm('are you sure you want to close?')
			if (result== false){
				c.modal('show');
				}
			 if (result== true){
			   c.modal('hide');
			   
			}
		}
	var panPath = [];

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



</script>
<script
  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=geometry,places&v=3.43">
</script>

<!-- 2️⃣ Load MapLabel AFTER Maps API is ready -->
<script src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>
<script>
  window.onload = function () {
    initMap();
  };
</script>
	
</html>
