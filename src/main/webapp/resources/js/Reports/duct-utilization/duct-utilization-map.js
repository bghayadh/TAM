window.initMap = function() {

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

    initializeMapControls();
    initializeClusterManagers();
    initializeInfoWindows();

    $("#legendDiv").toggle();

    getLongLatMouseMove(map);
};


function initializeMapControls() {
    const mapLegendControlDiv = document.createElement("div");
    ShowHideMapLegend(mapLegendControlDiv, map);
    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(mapLegendControlDiv);

    const DefaultZoomDiv = document.createElement("div");
    DefaultZoomControl(DefaultZoomDiv, map);
    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);
}

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
        var center = new google.maps.LatLng(1, 38);
        map.setCenter(center);
        map.setZoom(7);
    });
}


function initializeClusterManagers() {

    markerClusterDB = createCluster('blueCluster.png');
    markerClusterJct = createCluster('orangeCluster.png');
    markerClusterCustomers = createCluster('customerCluster.png');
    markerClusterSites = createCluster('pinkCluster.png');

    markerClusterManholes = createCluster('redCluster.png');
    markerClusterManholesWithJct = createCluster('redCluster.png');

    markerClusterHandholes = createCluster('yellowCluster.png');
    markerClusterHandholesWithJct = createCluster('yellowCluster.png');
}

function createCluster(iconFile) {

    var cluster = new MarkerClusterer();
    cluster.setMap(map);

    cluster.setOptions({
        minimumClusterSize: 2,
        styles: [{
            url: `${getContext()}/resources/clusterIcons/${iconFile}`,
            height: 60,
            width: 60,
            anchorText: [-3, -3]
        }],
        calculator: function(markers) {
            if (markers.length >= 1) {
                return { text: markers.length, index: 3 };
            }
        }
    });

    return cluster;
}

function initializeInfoWindows() {
    infoWindow = new google.maps.InfoWindow();
    cableInfoWindow = new google.maps.InfoWindow();
}


function getLongLatMouseMove(map) {
    map.addListener("mousemove", (mapsMouseEvent) => {
        $("#mapLongLat").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(13) + " || "
            + mapsMouseEvent.latLng.toJSON().lng.toFixed(13), null, 2));
    });
}





function closeInfoWindows() {

    infoWindow.close();
    cableInfoWindow.close();
}

function resetMapData() {

    clearMapClusters();
    clearMapArrays();
    clearCablePaths();
    resetCounters();
    recenterMap();
}

function clearMapClusters() {

    markerClusterDB.clearMarkers();
    markerClusterJct.clearMarkers();
    markerClusterCustomers.clearMarkers();

    markerClusterHandholes.clearMarkers();
    markerClusterHandholesWithJct.clearMarkers();

    markerClusterManholes.clearMarkers();
    markerClusterManholesWithJct.clearMarkers();

    markerClusterSites.clearMarkers();
}

function clearMapArrays() {

    markersDB = [];
    markersJct = [];
    markersCustomer = [];

    markersHandholes = [];
    markersHandholesWithJct = [];

    markersManholes = [];
    markersManholesWithJct = [];

    markersSites = [];
}

function clearCablePaths() {

    // MOVE EXISTING
    // fiberCableArray clearing code

    // MOVE EXISTING
    // relatedPathArray clearing code
}

function recenterMap() {

    var center = new google.maps.LatLng(1, 38);

    map.setCenter(center);
    map.setZoom(6);
}

function buildPath(
    Id,
    fiberArray,
    Name,
    path,
    strokeColor,
    strokeOpacity,
    strokeWeight,
    fontColor,
    IdNb
) {

    if (!fiberArray[Id]) {

        var idInfo =
            "<b style='font-size:13px;'><u>ID: </u></b>" + Id;

        var nameInfo =
            "<b style='font-size:13px;'><u>Name: </u></b>" + Name;

        var data =
            "<div></br>" +
            idInfo +
            "</br>" +
            nameInfo +
            "</div>";

        flightPath = new google.maps.Polyline({

            path: path,
            geodesic: false,
            strokeColor: strokeColor,
            ID: Id,
            strokeOpacity: strokeOpacity,
            strokeWeight: strokeWeight,
            data: data
        });

        flightPath.metadata = {
            id: Id
        };

        fiberArray[Id] = flightPath;
        fiberArray.push(flightPath);

        google.maps.event.addListener(
            flightPath,
            'click',
            function(event) {

                cableInfoWindow.close();
                cableInfoWindow.setContent(this.data);
                cableInfoWindow.setPosition(event.latLng);
                cableInfoWindow.open(map);
            }
        );
    }
}

function createMarker(ID, longitude, latitude, Name, iconImg, markersArray, markerClusterArray, target) {

    markerId = ID;



    const pos = new google.maps.LatLng(latitude, longitude);
    if (iconImg == "customerIcon.png") {
        markerIcon = {
            url: getContext() + "/resources/NetworkImages/" + iconImg,
            scaledSize: new google.maps.Size(40, 40),
        };
    }
    else if (iconImg == "redSiteIcon.png") {
        markerIcon = {
            url: getContext() + "/resources/NetworkImages/" + iconImg,
            scaledSize: new google.maps.Size(35, 35),
        };
    }
    else if (iconImg == "handholeGreen.png") {
        markerIcon = {
            url: getContext() + "/resources/NetworkImages/" + iconImg,
            scaledSize: new google.maps.Size(10, 10),
        };
    }

    else {
        markerIcon = {
            url: getContext() + "/resources/NetworkImages/" + iconImg,
            scaledSize: new google.maps.Size(20, 20),
        };
    }

    var jctID = "";
    var jctName = "";

    if (target == "manholeWithJct") {

        if (manHandoleList.includes(ID)) {
            for (var i = 0;i < manHandList.length;i++) {
                if (manHandList[i][0] === ID) {
                    jctID = manHandList[i][2];
                    jctName = manHandList[i][3];
                    break; // Exit the loop once the target is found
                }
            }
            var manIdInfo = "<b style='font-size:13px;'><u>Manhole ID: </u></b>" + ID;
            var manNameInfo = "<b style='font-size:13px;'><u>Manhole Name: </u></b>" + Name;
            var jctIdInfo = "<b style='font-size:13px;'><u>Junction ID: </u></b>" + jctID;
            var jctNameInfo = "<b style='font-size:13px;'><u>Junction Name: </u></b>" + jctName;
            var data = "<div></br>" + manIdInfo + "</br>" + manNameInfo + "</br>" + jctIdInfo + "</br>" + jctNameInfo + "</div>";
        }
        else {
            var manIdInfo = "<b style='font-size:13px;'><u>Manhole ID: </u></b>" + ID;
            var manNameInfo = "<b style='font-size:13px;'><u>Manhole Name: </u></b>" + Name;
            var data = "<div></br>" + manIdInfo + "</br>" + manNameInfo + "</div>";
        }

    }
    else if (target == "manhole") {

        var manIdInfo = "<b style='font-size:13px;'><u>Manhole ID: </u></b>" + ID;
        var manNameInfo = "<b style='font-size:13px;'><u>Manhole Name: </u></b>" + Name;
        var data = "<div></br>" + manIdInfo + "</br>" + manNameInfo + "</div>";
    }
    else if (target == "handholeWithJct") {

        if (manHandoleList.includes(ID)) {
            for (var i = 0;i < manHandList.length;i++) {
                if (manHandList[i][0] === ID) {
                    jctID = manHandList[i][2];
                    jctName = manHandList[i][3];
                    break; // Exit the loop once the target is found
                }
            }
            var handholeIdInfo = "<b style='font-size:13px;'><u>Handhole ID: </u></b>" + ID;
            var handholeNameInfo = "<b style='font-size:13px;'><u>Handhole Name: </u></b>" + Name;
            var jctIdInfo = "<b style='font-size:13px;'><u>Junction ID: </u></b>" + jctID;
            var jctNameInfo = "<b style='font-size:13px;'><u>Junction Name: </u></b>" + jctName;
            var data = "<div></br>" + handholeIdInfo + "</br>" + handholeNameInfo + "</br>" + jctIdInfo + "</br>" + jctNameInfo + "</div>";

        }
        else {
            var handholeIdInfo = "<b style='font-size:13px;'><u>Handhole ID: </u></b>" + ID;
            var handholeNameInfo = "<b style='font-size:13px;'><u>Handhole Name: </u></b>" + Name;
            var data = "<div></br>" + handholeIdInfo + "</br>" + handholeNameInfo + "</div>";
        }
    }
    else if (target == "handhole") {

        var handholeIdInfo = "<b style='font-size:13px;'><u>Handhole ID: </u></b>" + ID;
        var handholeNameInfo = "<b style='font-size:13px;'><u>Handhole Name: </u></b>" + Name;
        var data = "<div></br>" + handholeIdInfo + "</br>" + handholeNameInfo + "</div>";
    }
    else if (target == "site") {

        var idInfo = "<b style='font-size:13px;'><u>Site ID: </u></b>" + ID;
        var nameInfo = "<b style='font-size:13px;'><u>Site Name: </u></b>" + Name;
        var data = "<div></br>" + idInfo + "</br>" + nameInfo + "</div>";

    }
    else if (target == "customer") {

        var idInfo = "<b style='font-size:13px;'><u>Customer ID: </u></b>" + ID;
        var nameInfo = "<b style='font-size:13px;'><u>Customer Name: </u></b>" + Name;
        var data = "<div></br>" + idInfo + "</br>" + nameInfo + "</div>";

    }
    else if (target == "junction") {

        var idInfo = "<b style='font-size:13px;'><u>Junction ID: </u></b>" + ID;
        var nameInfo = "<b style='font-size:13px;'><u>Junction Name: </u></b>" + Name;
        var data = "<div></br>" + idInfo + "</br>" + nameInfo + "</div>";

    }
    else if (target == "DB") {

        var idInfo = "<b style='font-size:13px;'><u>DB ID: </u></b>" + ID;
        var nameInfo = "<b style='font-size:13px;'><u>DB Name: </u></b>" + Name;
        var data = "<div></br>" + idInfo + "</br>" + nameInfo + "</div>";

    }

    if (!markersArray[markerId]) {
        elementMarker = new google.maps.Marker({
            position: pos,
            ID: markerId,
            icon: markerIcon,
            data: data,
        });
        elementMarker.metadata = { id: markerId };
        markersArray[markerId] = elementMarker;
        markersArray.push(elementMarker);
        markerClusterArray.addMarker(markersArray["" + markerId]);
        markersArray[markerId].setMap(map);

        google.maps.event.addListener(elementMarker, "click", function(e) {
            infoWindow.close();
            infoWindow.setContent(this.data);
            infoWindow.open(map, this);
        });

    }
    else {
        if (markersArray[markerId].map != map) {
            markersArray[markerId].setMap(map);
            markerClusterArray.addMarker(markersArray["" + markerId]);

        }
        markersArray[markerId].setPosition(pos);
    }
    if (mapFlag == "1") {
        infoWindow.close();
        cableInfoWindow.close();
    }
}
