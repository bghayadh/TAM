function handleMapDropdownChange(selectedValue) {

    if (mapFlag == "1") {
        resetDistinctAndClusters();
        if (selectedValue === "cableBased") {
            showElementLocationPoints(
                elementsArray,
                filteredGridData,
                "mapPointsNames_",
                "mapPoints_"
            );
        } else if (selectedValue === "gridBased" && generateFlag == "1") {
            renderGridBasedMode();
        }

    } else {
        showOnMap();
    }
}

function resetDistinctAndClusters() {

    distinctDB = [];
    distinctJct = [];
    distinctCustomers = [];
    distinctSites = [];
    distinctManholes = [];
    distinctManholesWithJct = [];
    distinctHandholesWithJct = [];
    distinctHandholes = [];

    markerClusterDB.clearMarkers();
    markerClusterJct.clearMarkers();
    markerClusterCustomers.clearMarkers();
    markerClusterHandholes.clearMarkers();
    markerClusterHandholesWithJct.clearMarkers();
    markerClusterManholes.clearMarkers();
    markerClusterManholesWithJct.clearMarkers();
    markerClusterSites.clearMarkers();
}

function renderGridBasedMode() {

    window["mapPointsBasedOnGrid_" + cableID] = [];
    window["mapPointsNamesBasedOnGrid_" + cableID] = [];

    processCableSrcDstArray(
        cableID,
        "mapPointsNamesBasedOnGrid_",
        srcDstCableList,
        "mapPointsBasedOnGrid_"
    );

    window["mapPointsBasedOnGrid_" + cableID].push(
        new google.maps.LatLng(
            srcDstCableList[0][3],
            srcDstCableList[0][2]
        )
    );

    showElementLocationPoints(
        elementsArray,
        filteredGridData,
        "mapPointsNamesBasedOnGrid_",
        "mapPointsBasedOnGrid_"
    );
}

function showHideSourceMarker() {

    $('.showHideSrcCheckbox').bind("change", function() {
        for (var x = 0;x < srcID.length;x++) {
            if (srcID[x][1] == "warehouse") {
                markersArray = markersSites;
                clusterArray = markerClusterSites;
            }
            else if (srcID[x][1] == "customer") {
                markersArray = markersCustomer;
                clusterArray = markerClusterCustomers;
            }
            else if (srcID[x][1] == "manhole") {
                markersArray = markersManholes;
                clusterArray = markerClusterManholes;
            }
            else if (srcID[x][1] == "manholewithJct") {
                markersArray = markersManholesWithJct;
                clusterArray = markerClusterManholesWithJct;
            }
            else if (srcID[x][1] == "handhole") {
                markersArray = markersHandholes;
                clusterArray = markerClusterHandholes;
            }
            else if (srcID[x][1] == "handholewithJct") {
                markersArray = markersHandholesWithJct;
                clusterArray = markerClusterHandholesWithJct;
            }
            else if (srcID[x][1] == "DB") {
                markersArray = markersDB;
                clusterArray = markerClusterDB;
            }
            ID = srcID[x][0];
            if ($(this).is(':checked')) {
                if (markersArray[ID].getMap() == null) {
                    clusterArray.removeMarker(markersArray[ID]);
                    markersArray[ID].setMap(map);
                    clusterArray.addMarker(markersArray[ID]);
                }
            }
            else {
                markersArray[ID].setMap(null);
                clusterArray.removeMarker(markersArray[ID]);
            }
        }


    });

}

function showHideDestinationMarker() {

    $('.showHideDstCheckbox').bind("change", function() {
        for (var x = 0;x < dstID.length;x++) {
            if (dstID[x][1] == "warehouse") {
                markersArray = markersSites;
                clusterArray = markerClusterSites;
            }
            else if (dstID[x][1] == "customer") {
                markersArray = markersCustomer;
                clusterArray = markerClusterCustomers;
            }
            else if (dstID[x][1] == "manhole") {
                markersArray = markersManholes;
                clusterArray = markerClusterManholes;
            }
            else if (dstID[x][1] == "manholewithJct") {
                markersArray = markersManholesWithJct;
                clusterArray = markerClusterManholesWithJct;
            }
            else if (dstID[x][1] == "handhole") {
                markersArray = markersHandholes;
                clusterArray = markerClusterHandholes;
            }
            else if (dstID[x][1] == "handholewithJct") {
                markersArray = markersHandholesWithJct;
                clusterArray = markerClusterHandholesWithJct;
            }
            else if (dstID[x][1] == "DB") {
                markersArray = markersDB;
                clusterArray = markerClusterDB;
            }
            ID = dstID[x][0];
            if ($(this).is(':checked')) {
                if (markersArray[ID].getMap() == null) {
                    clusterArray.removeMarker(markersArray[ID]);
                    markersArray[ID].setMap(map);
                    clusterArray.addMarker(markersArray[ID]);
                }
            }
            else {
                markersArray[ID].setMap(null);
                clusterArray.removeMarker(markersArray[ID]);
            }
        }
    });
}

function showHideCable() {

    $('.showHideCableCheckbox').bind("change", function() {
        if ($(this).is(':checked')) {
            if (pathArray.length > 0) {
                for (var v = 0;v < allCables.length;v++) {
                    pathArray[allCables[v]].setMap(map);
                }
            }
        }
        else {
            if (pathArray.length > 0) {
                for (var v = 0;v < allCables.length;v++) {
                    pathArray[allCables[v]].setMap(null);
                }
            }
        }

    });
}
function showCableRelatedPath() {

    if (showRelPathFlag == "notOpened") {
        showRelPathFlag = "Opened";
        document.getElementById("relatedPathCount").textContent = "(" + allRelatedPathCables.length + ")";
    }

    if (relatedPathArray.length > 0) {
        $('.showHideRelatedCableCheckbox').bind("change", function() {
            if ($(this).is(':checked')) {
                for (var v = 0;v < allRelatedPathCables.length;v++) {
                    relatedPathArray[allRelatedPathCables[v]].setMap(map);
                }
            }
            else {
                for (var v = 0;v < allRelatedPathCables.length;v++) {
                    relatedPathArray[allRelatedPathCables[v]].setMap(null);
                }
            }

        });
    }
    else {
        alert("No Related Path to show or hide!")

    }

}

function showHidePts(className) {

    if (className == "showHideAllDbCheckbox") {
        clusterArray = markerClusterDB;
        markersArray = markersDB;
        distinctArray = distinctDB;
        clusterArray.clearMarkers();

    }
    else if (className == "showHideAllJctCheckbox") {
        clusterArray = markerClusterJct;
        markersArray = markersJct;
        distinctArray = distinctJct;
        clusterArray.clearMarkers();

    }
    else if (className == "showHideAllCustCheckbox") {
        clusterArray = markerClusterCustomers;
        markersArray = markersCustomer;
        distinctArray = distinctCustomers;
        clusterArray.clearMarkers();

    }
    else if (className == "showHideAllHandholesCheckbox") {
        clusterArray = markerClusterHandholes;
        markersArray = markersHandholes;
        distinctArray = distinctHandholes;
    }
    else if (className == "showHideAllHandholesWithJctCheckbox") {
        clusterArray = markerClusterHandholesWithJct;
        markersArray = markersHandholesWithJct;
        distinctArray = distinctHandholesWithJct;
    }
    else if (className == "showHideAllManholesCheckbox") {
        clusterArray = markerClusterManholes;
        markersArray = markersManholes;
        distinctArray = distinctManholes;
        clusterArray.clearMarkers();
    }
    else if (className == "showHideAllManholesWithJctCheckbox") {
        clusterArray = markerClusterManholesWithJct;
        markersArray = markersManholesWithJct;
        distinctArray = distinctManholesWithJct;
        clusterArray.clearMarkers();

    }
    else if (className == "showHideAllSitesCheckbox") {
        clusterArray = markerClusterSites;
        markersArray = markersSites;
        distinctArray = distinctSites;
        clusterArray.clearMarkers();

    }

    $('.' + className).bind("change", function() {

        if ($(this).is(':checked')) {
            for (var x = 0;x < distinctArray.length;x++) {
                ID = distinctArray[x];
                if (markersArray[ID].getMap() == null) {
                    clusterArray.removeMarker(markersArray[ID]);
                    markersArray[ID].setMap(map);
                    clusterArray.addMarker(markersArray[ID]);
                }
            }
        }
    });
}
