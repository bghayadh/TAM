function setDuctViewMode(mode) {

    window.ductLayer.viewMode = mode;

    clearDuctVisualizationLayer();
    clearAllClusters();

    showOnMap();
}

function showHideDuct() {

    console.log("showHideDuct");

    const checked = $('.showHideDuctCheckbox').is(':checked');

    for (let v = 0;v < pathArray.length;v++) {

        if (!pathArray[v]) continue;

        pathArray[v].setMap(checked ? map : null);
    }
}

function showHidePts(checkboxClass) {

    const checked = $('.' + checkboxClass).is(':checked');

    const bucketMap = {
        showHideAllManholesCheckbox: "manhole",
        showHideAllManholesWithJctCheckbox: "manholeWithJct",

        showHideAllHandholesCheckbox: "handhole",
        showHideAllHandholesWithJctCheckbox: "handholeWithJct",

        showHideAllDbCheckbox: "DB",
        showHideAllJctCheckbox: "junction",

        showHideAllCustCheckbox: "customer",
        showHideAllSitesCheckbox: "site"
    };

    const markerType = bucketMap[checkboxClass];

    if (!markerType) {
        console.warn("Unknown checkbox:", checkboxClass);
        return;
    }

    const markers =
        window.ductLayer?.markersByType?.[markerType];

    if (!markers) {
        return;
    }

    Object.values(markers).forEach(marker => {

        if (!marker) {
            return;
        }

        // Cluster mode
        if (window.ductLayer.viewMode === "clusterMode") {

            const type = detectAuxType(
                marker.segment?.fromAuxId,
                marker.segment?.fromAuxName
            );

            const cluster = resolveCluster(type);

            if (cluster) {

                if (checked) {
                    cluster.addMarker(marker);
                }
                else {
                    cluster.removeMarker(marker);
                }
            }
        }

        // Normal mode
        else {
            marker.setMap(checked ? map : null);
        }
    });
}

function legendCheckBoxesStatus() {

    $('.showHideSequencesCheckbox').prop('disabled', false);

    const hasSections =
        ReportArrayGlobal && ReportArrayGlobal.length > 0;

    $('.showHideDuctSectionCheckbox')
        .prop('disabled', !hasSections);

    const byType = window.ductLayer?.markersByType || {};

    const mappings = [
        ['.showHideAllManholesCheckbox', 'manhole', '#manholesCount'],
        ['.showHideAllManholesWithJctCheckbox', 'manholeWithJct', '#manholesCountWithJct'],
        ['.showHideAllHandholesCheckbox', 'handhole', '#handholesCount'],
        ['.showHideAllHandholesWithJctCheckbox', 'handholeWithJct', '#handholesCountWithJct'],
        ['.showHideAllDbCheckbox', 'DB', '#dbCount'],
        ['.showHideAllJctCheckbox', 'junction', '#jctCount'],
        ['.showHideAllCustCheckbox', 'customer', '#custCount'],
        ['.showHideAllSitesCheckbox', 'site', '#sitesCount']
    ];

    mappings.forEach(([selector, type, countSelector]) => {

        const count =
            Object.keys(byType[type] || {}).length;

        $(selector)
            .prop('disabled', count === 0)
            .prop('checked', count > 0);

        $(countSelector).text(`(${count})`);
    });

    // Duct Path
    $('#ductCount').text(
        `(${pathArray && pathArray[ductID] ? 1 : 0})`
    );

    // Duct Sections
    $('#sectionCount').text(
        `(${ReportArrayGlobal ? ReportArrayGlobal.length : 0})`
    );

    // Related Cables
    $('#relatedPathCount').text(
        `(${Object.keys(relatedPathArray || {}).filter(k => isNaN(k)).length})`
    );
}

function showHideDuctSection() {

    const checked = $('.showHideDuctSectionCheckbox').is(':checked');

    // If unchecked → remove all overlays
    if (!checked) {
        if (window.ductLayer?.overlays) {
            window.ductLayer.overlays.forEach(o => o.setMap(null));
            window.ductLayer.overlays.clear();
        }
        return;
    }

    // If no data → nothing to show
    if (!ReportArrayGlobal || ReportArrayGlobal.length === 0) {
        return;
    }

    const bounds = new google.maps.LatLngBounds();
    let hasBounds = false;

    ReportArrayGlobal.forEach(segment => {

        const auxId = segment.fromAuxId;

        // expand bounds
        if (segment.fromLatitude && segment.fromLongitude) {
            bounds.extend(new google.maps.LatLng(segment.fromLatitude, segment.fromLongitude));
            hasBounds = true;
        }

        // already exists → skip
        if (window.ductLayer.overlays.has(auxId)) {
            return;
        }

        // create overlay using existing logic
        openSegmentOverlay(segment);
    });

    // fit map once
    if (hasBounds) {
        map.fitBounds(bounds);
    }
}


function showCableRelatedPath() {

    const checked =
        $('.showHideRelatedCableCheckbox').is(':checked');

    if (relatedPathArray.length === 0) {
        alert("No Related Path to show or hide!");
        return;
    }

    for (let i = 0;i < relatedPathArray.length;i++) {

        if (!relatedPathArray[i]) {
            continue;
        }

        relatedPathArray[i].setMap(
            checked ? map : null
        );
    }
}


function showHideSequences() {

    const byType = window.ductLayer?.markersByType;

    if (!byType) return;

    const show = $('.showHideSequencesCheckbox').is(':checked');

    Object.values(byType).forEach(bucket => {
        Object.values(bucket).forEach(marker => {

            if (!marker?.segment?.fromSequence) return;

            marker.setLabel(
                show
                    ? {
                        text: String(marker.segment.fromSequence),
                        color: "black",
                        fontSize: "12px",
                        fontWeight: "bold"
                    }
                    : null
            );
        });
    });
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

/*
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
*/