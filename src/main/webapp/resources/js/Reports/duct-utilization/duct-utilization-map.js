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
    initializeMarkers();
    initializeInfoWindows();
    initOverlaySystem();

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

function initializeMarkers() {

    window.ductLayer = {
        markersByType: {
            DB: {},
            junction: {},
            manhole: {},
            manholeWithJct: {},
            handhole: {},
            handholeWithJct: {},
            customer: {},
            site: {}
        },

        clustersByType: {
            DB: null,
            junction: null,
            manhole: null,
            handhole: null,
            customer: null,
            site: null
        },

        overlays: new Map(),
        viewMode: "normalMode"
    };

    window.ductLayer.clustersByType.DB = createCluster('blueCluster.png');
    window.ductLayer.clustersByType.junction = createCluster('orangeCluster.png');

    window.ductLayer.clustersByType.manhole = createCluster('redCluster.png');
    window.ductLayer.clustersByType.handhole = createCluster('yellowCluster.png');

    window.ductLayer.clustersByType.customer = createCluster('customerCluster.png');
    window.ductLayer.clustersByType.site = createCluster('pinkCluster.png');

    console.log(window.ductLayer.clustersByType);


    window.AUX_MARKER_BUCKETS = {
        manhole: {
            normal: {
                arr: window.ductLayer.markersByType.manhole,
                cluster: window.ductLayer.clustersByType.manhole,
                icon: "manholeRed.png",
                target: "manhole"
            },
            withJunction: {
                arr: window.ductLayer.markersByType.manholeWithJct,
                cluster: window.ductLayer.clustersByType.manhole,
                icon: "manholeJct.png",
                target: "manholeWithJct"
            }
        },

        handhole: {
            normal: {
                arr: window.ductLayer.markersByType.handhole,
                cluster: window.ductLayer.clustersByType.handhole,
                icon: "handholeYellow.png",
                target: "handhole"
            },
            withJunction: {
                arr: window.ductLayer.markersByType.handholeWithJct,
                cluster: window.ductLayer.clustersByType.handhole,
                icon: "handholeYellowJct.png",
                target: "handholeWithJct"
            }
        },

        junction: {
            normal: {
                arr: window.ductLayer.markersByType.junction,
                cluster: window.ductLayer.clustersByType.junction,
                icon: "junctionOrange.png",
                target: "junction"
            }
        },

        DB: {
            normal: {
                arr: window.ductLayer.markersByType.DB,
                cluster: window.ductLayer.clustersByType.DB,
                icon: "backboneDB.png",
                target: "DB"
            }
        },

        site: {
            normal: {
                arr: window.ductLayer.markersByType.site,
                cluster: window.ductLayer.clustersByType.site,
                icon: "redSiteIcon.png",
                target: "site"
            }
        },

        customer: {
            normal: {
                arr: window.ductLayer.markersByType.customer,
                cluster: window.ductLayer.clustersByType.customer,
                icon: "customerIcon.png",
                target: "customer"
            }
        },
    };
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
    ductInfoWindow = new google.maps.InfoWindow();
}


function getLongLatMouseMove(map) {
    map.addListener("mousemove", (mapsMouseEvent) => {
        $("#mapLongLat").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(13) + " || "
            + mapsMouseEvent.latLng.toJSON().lng.toFixed(13), null, 2));
    });
}

function buildPath(
    Id,
    pathArray,
    Name,
    pathAux,
    strokeColor,
    strokeOpacity,
    strokeWeight,
    fontColor,
    IdNb
) {

    if (!pathArray[Id]) {

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

            path: pathAux,
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

        pathArray[Id] = flightPath;
        pathArray.push(flightPath);

        google.maps.event.addListener(
            flightPath,
            'click',
            function(event) {

                ductInfoWindow.close();
                ductInfoWindow.setContent(this.data);
                ductInfoWindow.setPosition(event.latLng);
                ductInfoWindow.open(map);
            }
        );
    }
}

function detectAuxType(id, name) {

    if (!id) {
        return { baseType: "unknown", relation: "normal" };
    }

    // DB
    if (id.startsWith("DB_")) {
        return { baseType: "DB", relation: "normal" };
    }

    // Junction standalone
    else if (id.startsWith("JCT_")) {
        return { baseType: "junction", relation: "normal" };
    }

    // Manhole
    else if (id.startsWith("MH_")) {

        const hasJunction =
            (name && name.endsWith("_J")) === true;

        return {
            baseType: "manhole",
            relation: hasJunction ? "withJunction" : "normal"
        };
    }

    // Handhole
    else if (id.startsWith("HH_")) {

        const hasJunction =
            (name && name.endsWith("_J")) === true;

        return {
            baseType: "handhole",
            relation: hasJunction ? "withJunction" : "normal"
        };
    }

    // Customer
    if (id.startsWith("CUST_")) {
        return { baseType: "customer", relation: "normal" };
    }

    // SITE (WARE_)
    const point = auxPointIndex?.[id];
    if (point?.wareID?.startsWith("WARE_")) {
        return { baseType: "site", relation: "normal" };
    }

    return { baseType: "unknown", relation: "normal" };
}


function resolveMarkerBucket(type) {

    if (!window.AUX_MARKER_BUCKETS) {
        console.warn("AUX_MARKER_BUCKETS not initialized yet");
        return null;
    }

    const base = window.AUX_MARKER_BUCKETS[type.baseType];
    if (!base) return null;

    const relation = type.relation || "normal";
    return base[relation] || null;
}

function resolveIcon(iconImg, mode) {

    const baseUrl = getContext() + "/resources/NetworkImages/";

    const config = {
        "customerIcon.png": { size: 40 },
        "redSiteIcon.png": { size: 35 },
        "handholeGreen.png": { size: 10 }
    };

    const rule = config[iconImg] || { size: 20 };

    const size = rule.size; // ✅ DEFINE IT HERE

    return {
        url: baseUrl + iconImg,
        scaledSize: new google.maps.Size(rule.size, rule.size),
        labelOrigin: new google.maps.Point(size + 8, size / 2)
    };
}

function createMarker(ID, longitude, latitude, Name, iconImg, target) {

    const pos = new google.maps.LatLng(latitude, longitude);

    //    const markerIcon = getIcon(iconImg);

    const marker = new google.maps.Marker({
        position: pos,
        ID: ID,
        icon: iconImg
    });

    return marker;
}



function removeMarker(id, type) {
    console.log("welcome to remove marker, the id is ", id + " and the type is: ", type);
    const bucket = resolveMarkerBucket(type);
    if (!bucket) return;

    const arr = bucket.arr;
    //const cluster = resolveCluster(type);
    const cluster = bucket.cluster;

    const marker = arr[id];
    if (!marker) {
        console.log("no marker found to be removed");
        return
    };

    marker.setMap(null);

    if (cluster) {
        cluster.removeMarker(marker);
    }

    delete arr[id];
}


function resolveCluster(type) {
    console.log("type =", type);
    console.log("baseType =", type?.baseType);
    const base = type.baseType;

    if (base === "DB") return window.ductLayer.clustersByType.DB;
    else if (base === "junction") return window.ductLayer.clustersByType.junction;
    else if (base === "manhole") return window.ductLayer.clustersByType.manhole;
    else if (base === "handhole") return window.ductLayer.clustersByType.handhole;
    else if (base === "customer") return window.ductLayer.clustersByType.customer;
    else if (base === "site") return window.ductLayer.clustersByType.site;

    return null;
}

function closeInfoWindows() {

    infoWindow.close();
    ductInfoWindow.close();
}

function resetMapData() {

    clearAllClusters();
    clearDuctVisualizationLayer();
    clearPaths();
    resetCounters();
    recenterMap();
}

function clearPaths() {

    Object.values(pathArray).forEach(path => {
        if (path?.setMap) path.setMap(null);
    });

    Object.keys(pathArray).forEach(key => delete pathArray[key]);

    if (Array.isArray(pathArray)) {
        pathArray.length = 0;
    }
}

function recenterMap() {

    var center = new google.maps.LatLng(1, 38);

    map.setCenter(center);
    map.setZoom(6);
}


function clearAllClusters() {
    const clusters = window.ductLayer?.clustersByType;
    if (!clusters) return;

    Object.keys(clusters).forEach(key => {
        const cluster = clusters[key];

        if (cluster) {
            cluster.clearMarkers?.();
            //cluster.setMap?.(null);
        }

        //clusters[key] = null;
    });
}

function clearDuctVisualizationLayer() {

    const layer = window.ductLayer;
    if (!layer) return;

    // 1. Clear markers
    const byType = layer.markersByType || {};

    Object.values(byType).forEach(typeGroup => {
        Object.values(typeGroup).forEach(marker => {
            marker?.setMap?.(null);
        });

        // IMPORTANT: mutate, don't replace reference
        Object.keys(typeGroup).forEach(k => delete typeGroup[k]);
    });

    // 2. Clear overlays
    if (layer.overlays instanceof Map) {
        for (const overlay of layer.overlays.values()) {
            overlay?.setMap?.(null);
        }
        layer.overlays.clear();
    }

    // 3. Close UI
    infoWindow?.close?.();
    ductInfoWindow?.close?.();
}
function initOverlaySystem() {

    map.addListener("zoom_changed", () => {
        window.ductLayer?.overlays?.forEach(o => o.draw?.());
    });

    map.addListener("drag", () => {
        window.ductLayer?.overlays?.forEach(o => o.draw?.());
    });
}