function initSectionVisibility() {

    if (readDB == 1) {
        var dbSection = document.getElementById("dbSection");
        if (dbSection) dbSection.style.display = "block";
    }

    if (readManhole == 1) {
        var manholeSection = document.getElementById("manholeSection");
        if (manholeSection) manholeSection.style.display = "block";
    }

    if (readHandhole == 1) {
        var handholeSection = document.getElementById("handholeSection");
        if (handholeSection) handholeSection.style.display = "block";
    }
}

function initAfterPageLoad() {
    makeAllSortable();

    if (checkedOption === "circleRange") {
        calculateGeoDistanceNearestPoints("findNearstHandhole", handholeSurveyArray);
        calculateGeoDistanceNearestPoints("findNearstManhole", manholeSurveyArray);
        calculateGeoDistanceNearestPoints("findNearstDB", dbSurveyArray);
        calculateGeoDistanceNearestPoints("findNearstNode", nodeSurveyArray);
        getAllSurveyArrays("nearFiberId", fiberCableSurveyArray);
        getAllSurveyArrays("nearTubeId", fiberTubesSurveyArray);
        getAllSurveyArrays("nearStrandId", fiberStrandsSurveyArray);
    }
}

function initPermissionTabs() {

    function toggleElementDisplay(element, permission) {
        if (element) {
            element.style.display = permission === '1' ? 'block' : 'none';
        }
    }

    toggleElementDisplay(document.getElementById('fiber-search-tab'), searchPopupPerm);
    toggleElementDisplay(document.getElementById('custom-tabs-filter-tab'), searchPopupPerm);
    toggleElementDisplay(document.getElementById('closest-search-tab'), searchPopupPerm);
    toggleElementDisplay(document.getElementById('MultyClosest-search-tab'), searchPopupPerm);
    toggleElementDisplay(document.getElementById('connectedSearch-tab'), findConnedtedPerm);
}

function buildFilterSection() {

    var list = physicalLayerList || {};
    var filterSection = $('#filterSection');

    filterSection.empty();

    Object.keys(list).forEach(function(key) {
        var label = key;

        if (key === 'Project') {
            filterSection.append(
                "<div class='row' style='margin-left:-15px;'>" +
                "<div class='col-md-6'>" +
                "<div class='input-group-prepend'>" +
                "<span style='font-size: 14px;width:200px;' class='input-group-text'><b>Project</b></span>" +
                "<input type='text' name='filteredField' id='FilteredProject' class='form-control text-input' placeholder='Project'/>" +
                "</div>" +
                "</div>" +
                "</div><p></p><p></p>"
            );
            return;
        }

        if (key.indexOf('_') !== -1) {
            label = key.replace('_', ' ');
        }

        label = label.charAt(0).toUpperCase() + label.slice(1);

        filterSection.append(
            "<div class='row hashMapList' style='margin-left:-15px;' id='hashMapList'>" +
            "<div class='col-md-6'>" +
            "<div class='input-group-prepend'>" +
            "<span style='font-size: 14px;width:200px;' class='input-group-text'><b>" + label + "</b></span>" +
            "<input type='text' name='filteredField' id='Filtered" + key + "' class='form-control text-input' placeholder='" + label + "'/>" +
            "</div>" +
            "</div>" +
            "</div><p></p><p></p>"
        );
    });
}

function initMap() {

    $("#default").prop('checked', true);
    $("#landscape").prop('checked', true);
    $("#water").prop('checked', true);
    $("#transit").prop('checked', true);
    $("#poi").prop('checked', true);
    $("#road").prop('checked', true);
    $("#blank").prop('checked', false);
    $("#mapgeography").prop('checked', false);
    $("#maplabels").prop('checked', false);
    $("#countrynames").prop('checked', false);
    $("#countryprovince").prop('checked', false);

    var button = document.getElementById('customMap');
    var data = button ? button.getAttribute('data-map') : null;

    document.getElementById("network_tree").innerHTML = "";
    $("#network_tree").resizable({ handles: "s" });

    var directionsDisplay = new google.maps.DirectionsRenderer();
    var directionsService = new google.maps.DirectionsService();

    createdUser = $("#crtdByFiberCable").val();
    lstModfUser = $("#modifiedByFiberCable").val();

    map = new google.maps.Map(document.getElementById("mapContainer"), {
        center: { lat: Number(systemLat), lng: Number(systemLong) },
        mapTypeControl: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
            position: google.maps.ControlPosition.TOP_CENTER
        },
        zoomControl: true,
        zoomControlOptions: {
            position: google.maps.ControlPosition.LEFT_CENTER
        },
        scaleControl: true,
        streetViewControl: true,
        streetViewControlOptions: {
            position: google.maps.ControlPosition.TOP_LEFT
        },
        fullscreenControl: true
    });

    map.setOptions({ minZoom: 3, maxZoom: 28 });
    directionsDisplay.setMap(map);

    if (typeof restingMap === 'function') {
        restingMap();
    }

    $("#open-popup-btn").removeAttr('disabled');

    buildFilterSection();

    if (typeof physicalLayerFilter === 'function') {
        physicalLayerFilter();
    }

    if (typeof CreateTree_PhysicalLayer === 'function') {
        CreateTree_PhysicalLayer(
            physicalLayerList['Project'],
            physicalLayerList['Manhole'],
            physicalLayerList['Handhole'],
            physicalLayerList['fiber'],
            physicalLayerList['Distribution_Board'],
            physicalLayerList['controllerList'],
            physicalLayerData['fiber_Tubes'],
            physicalLayerData['fiber_Strands'],
            physicalLayerData['fiber_Auxiliary'],
            physicalLayerData['tubes_Auxiliaries'],
            physicalLayerData['strands_Auxiliaries'],
            physicalLayerList['Trench'],
            physicalLayerData['trench_Auxiliary'],
            physicalLayerList['Junction_Manhole'],
            physicalLayerList['Junction_Handhole'],
            filterFlag,
            physicalLayerList['duct'],
            physicalLayerData['ductAuxiliary'],
            physicalLayerList['Node']
        );
    }

    if (typeof CreateMap_PhysicalLayer === 'function') {
        CreateMap_PhysicalLayer(
            physicalLayerList['Project'],
            physicalLayerList['Manhole'],
            physicalLayerList['Handhole'],
            physicalLayerList['fiber'],
            physicalLayerList['Distribution_Board'],
            physicalLayerData['fiber_Tubes'],
            physicalLayerData['fiber_Strands'],
            physicalLayerData['fiber_Auxiliary'],
            physicalLayerData['tubes_Auxiliaries'],
            physicalLayerData['strands_Auxiliaries'],
            physicalLayerList['Trench'],
            physicalLayerData['trench_Auxiliary'],
            physicalLayerList['Node'],
            systemLong,
            systemLat
        );
    }

    if (checkedOption === "circleRange") {
        if (typeof openFindNearest === 'function') {
            openFindNearest(
                checkedOption,
                closestLatPoint,
                closestLongPoint,
                closestDisRange,
                noP,
                physicalLayerList['Manhole'],
                physicalLayerList['Handhole'],
                physicalLayerList['Distribution_Board'],
                physicalLayerList['controllerList'],
                physicalLayerList['fiber'],
                physicalLayerData['fiber_Strands'],
                physicalLayerData['fiber_Tubes'],
                physicalLayerList['Node'],
                getRelatedPoints,
                startLng,
                endLng,
                startLat,
                endLat,
                CustomerID,
                CustomerName,
                serviceReq,
                serviceRef
            );
        }
    } else if (checkedOption === "StartEnd") {
        if (typeof openFindBetweenMarkers === 'function') {
            openFindBetweenMarkers(
                checkedOption,
                startLongPoint,
                startLatPoint,
                endLongPoint,
                endLatPoint,
                physicalLayerList['Manhole'],
                physicalLayerList['Handhole'],
                physicalLayerList['Distribution_Board'],
                physicalLayerList['fiber'],
                physicalLayerData['fiber_Strands'],
                physicalLayerData['fiber_Tubes'],
                physicalLayerList['Node'],
                getRelatedPoints
            );
        }
    } else if (checkedOption === "circleRange_multy") {
        if (typeof openFindNearestMultySite === 'function') {
            openFindNearestMultySite(
                checkedOption,
                rowData,
                noOfPoints,
                closestDisRange,
                ptList,
                ptData,
                getRelatedPoints,
                borderCircleLatitudes,
                borderCircleLongitudes,
                circleDraw,
                squareDraw,
                locationNumber,
                rowMultyIndex
            );
        }
    } else if (checkedOption === "connected") {
        if (typeof openSearchConnected === 'function') {
            openSearchConnected(
                checkedOption,
                siteId,
                selectConnectedSearch,
                connectedSearchLong,
                connectedSearchLat,
                connectedViewOnMap,
                physicalLayerData['fiber_Strands'],
                physicalLayerData['fiber_Tubes'],
                physicalLayerList['fiber'],
                physicalLayerList['Manhole'],
                physicalLayerList['Handhole'],
                physicalLayerList['Distribution_Board'],
                physicalLayerList['controllerList'],
                distribBoardListSize,
                getRelatedPoints,
                fpPath,
                bpPath,
                physicalLayerList['Node']
            );
        }
    }
}

function showPassword() {
    var pwd = document.getElementById("password");
    if (pwd) {
        pwd.type = "text";
    }

    var icon = document.getElementById("pwdIcon");
    if (icon && icon.classList.contains('fa-eye')) {
        icon.classList.replace('fa-eye', 'fa-eye-slash');
    }
}

function hidePassword() {
    var pwd = document.getElementById("password");
    if (pwd) {
        pwd.type = "password";
    }

    var icon = document.getElementById("pwdIcon");
    if (icon && icon.classList.contains('fa-eye-slash')) {
        icon.classList.replace('fa-eye-slash', 'fa-eye');
    }
}