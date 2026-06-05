function rebuildGridTable() {

    $("#gridTable").remove();

    createGridTableHtml();
}

function createGridTableHtml() {

    $("#tableGrid").append(
        buildGridTableMarkup()
    );
}

function buildGridTableMarkup() {
    return `
        <table id="gridTable" class="table table-striped table-bordered almgrid-table">
            <thead>
                <tr class="header fixed-header">
                    <th>
                        Duct Section Drawing
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        Cable Qty
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        From Sequence
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        From Auxiliary ID
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        From Auxiliary Name
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        From Longitude
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        From Latitude
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        To Sequence
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        To Auxiliary ID
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
						To Auxiliary Name
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        To Longitude
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        To Latitude
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter" data-toggle="dropdown">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter"
                                    data-toggle="dropdown"
                                    disabled
                                    style="display:none;">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>

                    <th>
                        <li class="filter-dropdown dropdown">
                            <button class="almgrid-filter"
                                    data-toggle="dropdown"
                                    disabled
                                    style="display:none;">
                                <i class="fa fa-list almgrid-filter-i"></i>
                            </button>
                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
                        </li>
                    </th>
                </tr>

                <tr>
                    <th></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
                    <th><input type="text" disabled class="almgrid-search" style="display:none"></th>
                    <th><input type="text" disabled class="almgrid-search" style="display:none"></th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    `;
}
function initializeGrid(reportData) {

    var almgrid = new AlmgridTable({
        tableId: "gridTable",
        dataArray: reportData,
        selectCheckbox: false,
        drawTableGrid: drawTableGrid
    });
}

function drawTableGrid(tableId, dataArray) {

    // MOVE huge drawTableGrid callback body here

    var tableBody = document.querySelector("#" + tableId + " tbody");
    var columnLinkNb = this.columnLinkNb;
    var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
    var gridContainerId = tableId + "_container";
    $(gridContainer).attr('id', gridContainerId);
    $(tableBody).empty();


    // Clear the map when the data in grid is filtered
    if (typeof markerClusterDB !== 'undefined' && markerClusterDB !== null) {
        markerClusterDB.clearMarkers();
    }
    if (typeof markerClusterJct !== 'undefined' && markerClusterJct !== null) {
        markerClusterJct.clearMarkers();
    }
    if (typeof markerClusterCustomers !== 'undefined' && markerClusterCustomers !== null) {
        markerClusterCustomers.clearMarkers();
    }
    if (typeof markerClusterHandholes !== 'undefined' && markerClusterHandholes !== null) {
        markerClusterHandholes.clearMarkers();
    }
    if (typeof markerClusterHandholesWithJct !== 'undefined' && markerClusterHandholesWithJct !== null) {
        markerClusterHandholesWithJct.clearMarkers();
    }
    if (typeof markerClusterManholes !== 'undefined' && markerClusterManholes !== null) {
        markerClusterManholes.clearMarkers();
    }
    if (typeof markerClusterManholesWithJct !== 'undefined' && markerClusterManholesWithJct !== null) {
        markerClusterManholesWithJct.clearMarkers();
    }
    if (typeof markerClusterSites !== 'undefined' && markerClusterSites !== null) {
        markerClusterSites.clearMarkers();
    }

    //Clear all arrays and inputs related to map when the data in grid is filtered
    distinctDB = [];
    distinctJct = [];
    distinctCustomers = [];
    distinctSites = [];
    distinctManholes = [];
    distinctManholesWithJct = [];
    distinctHandholes = [];
    distinctHandholesWithJct = [];
    markersSites = [];
    markersManholes = [];
    markersManholesWithJct = [];
    markersHandholes = [];
    markersHandholesWithJct = [];
    markersCustomer = [];
    markersJct = [];
    markersDB = [];
    mapFlag = "0";
    resetCounters();
    resetAllLegendCheckboxes();
    window["mapPointsNamesBasedOnGrid_" + cableID] = [];
    window["mapPointsBasedOnGrid_" + cableID] = [];

    var center = new google.maps.LatLng(1, 38);
    map.setCenter(center);
    map.setZoom(6);


    if (dataArray.length > 0) {
        window["mapPointsNamesBasedOnGrid_" + cableID] = [];
        window["mapPointsBasedOnGrid_" + cableID] = [];

        var ArrayKeys = Object.keys(dataArray[0]);
        var columnVal;
        var data = [];//for export
        var jctUsedStrands = 0, dbUsedStrands = 0;
        exportArrayGrid = [];
        jctElementsIDArray = [];
        jctElementsFlag = "notOpened";
        data.push('\r');
        data.push(["Strand #", "Tube #", "Location Type", "Location ID", "Location Name", "Location Longitude", "Location Latitude", "Element Type", "Element ID", "F/B or A/B", "Related Path", "Port Index", "Port Row", "Port Column", "Status", "Equipment Type", "Equipment ID", "Equipment Name"]);

        filteredGridData = dataArray; // used in draw on map 

        for (var i = 0;i < dataArray.length;i++) {
            data.push('\r');
            for (var j = 0;j < ArrayKeys.length;j++) {
                columnVal = ArrayKeys[j];
                if (columnVal != "showLocation" && columnVal != "showElement") {
                    data.push(dataArray[i][ArrayKeys[j]]);
                }
                if (columnVal == "elementID") {
                    if (dataArray[i][ArrayKeys[j]].includes("JCT") == true && jctElementsIDArray.includes(dataArray[i][ArrayKeys[j]]) == false) {
                        jctElementsIDArray.push(dataArray[i][ArrayKeys[j]]);
                    }
                }

            }
        }

        exportArrayGrid.push(data);
    }
    else {
        filteredGridData = [];
    }

    // Method for pagination almgrid-pagecount-box
    $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
    $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

    // For global search textbox
    $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

    var paginationId = tableId + "_pagination";


    // Page Rows number
    var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
    nbRows = parseInt(nbRows);

    this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys: ArrayKeys, selectCheckbox: this.selectCheckbox });

    // Drawing for the first time
    if (this.initFlag == 0) {
        var tables = document.getElementsByClassName('almgrid-table');
        for (var i = 0;i < tables.length;i++) {
            resizableGrid(tables[i]);
        }

    }
    this.initFlag++;
}

function showElement(concatIDLongLat, rowIndex) {

    var ID = concatIDLongLat.split(":")[0].trim();
    var longitude = concatIDLongLat.split(":")[1].trim();
    var latitude = concatIDLongLat.split(":")[2].trim();
    var Name = concatIDLongLat.split(":")[3].trim();

    var latLng = new google.maps.LatLng(latitude, longitude);
    map.setZoom(15);
    map.panTo(latLng);


    if (mapFlag == "0") { // Show on map is not clicked before (markers are not set on map)

        if (ID.includes("DB_")) {
            $('.showHideAllDbCheckbox').prop('checked', true);
            $(".showHideAllDbCheckbox").attr('disabled', false);
            document.getElementById("dbCount").textContent = "";
            if (!markersDB[ID]) {
                distinctDB.push(ID); //  this array is used when checking all db from legend
                createMarker(ID, longitude, latitude, Name, 'backboneDB.png', markersDB, markerClusterDB, "DB")

            }
            document.getElementById("dbCount").textContent = "(" + distinctDB.length + ")";
        }
        else if (ID.includes("JCT_")) {

            document.getElementById("jctCount").textContent = "";
            document.getElementById("manholesCountWithJct").textContent = "";

            /*if(!markersJct[ID]){
                distinctJct.push(ID); //  this array is used when checking all jct from legend
                createMarker(ID,longitude,latitude,Name,'junctionOrange.png',markersJct,markerClusterJct)
            }  */

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/getSingleJctElementsDetails',
                data: {
                    "ID": ID
                },
                dataType: "json",
                success: function(data) {
                    singleJctArray = data.singleJctList;

                    if (data.singleJctList.length > 0) {
                        for (z = 0;z < singleJctArray.length;z++) {
                            if (singleJctArray[z][2] != null && singleJctArray[z][2] != "null") {


                                if (singleJctArray[z][2].startsWith("MH_") == true) {
                                    var ID = singleJctArray[z][2];
                                    var manholeName = singleJctArray[z][3];

                                    if (manholeName.endsWith("_J")) {

                                        if (distinctManholesWithJct.includes(ID) == false) {
                                            distinctManholesWithJct.push(ID);
                                            if (!markersManholesWithJct[ID]) {
                                                createMarker(ID, singleJctArray[z][4], singleJctArray[z][5], manholeName, "manholeJct.png", markersManholesWithJct, markerClusterManholesWithJct, "manholeWithJct")
                                            }
                                            else {
                                                markersManholesWithJct[ID].setMap(map);
                                                markerClusterManholesWithJct.addMarker(markersManholesWithJct["" + ID]);
                                            }
                                        }
                                    }

                                }
                                else if (singleJctArray[z][2].startsWith("HH_") == true) {
                                    var ID = showPointsArray[x].split(":")[0];
                                    var ID = singleJctArray[z][2];
                                    var handholeName = singleJctArray[z][3];

                                    if (handholeName.endsWith("_J")) {

                                        if (distinctHandholesWithJct.includes(ID) == false) {
                                            distinctHandholesWithJct.push(ID);
                                            if (!markersHandholesWithJct[ID]) {
                                                createMarker(ID, singleJctArray[z][4], singleJctArray[z][5], handholeName, "handholeYellowJct.png", markersHandholesWithJct, markerClusterHandholesWithJct, "handholeWithJct")
                                            }
                                            else {
                                                markersHandholesWithJct[ID].setMap(map);
                                                markerClusterHandholesWithJct.addMarker(markersHandholesWithJct["" + ID]);
                                            }
                                        }
                                    }


                                }


                            }

                            else {//case of junction that does not belongs to mh or hh

                                if (distinctJct.includes(singleJctArray[z][0]) == false) {
                                    ID = singleJctArray[z][0];
                                    var longitude = singleJctArray[z][4];
                                    var latitude = singleJctArray[z][5];
                                    var Name = singleJctArray[z][1];
                                    distinctJct.push(ID);
                                    if (!markersJct[ID]) {
                                        createMarker(ID, longitude, latitude, Name, 'junctionOrange.png', markersJct, markerClusterJct, "junction")
                                    }
                                    else {
                                        markersJct[ID].setMap(map);
                                        markerClusterJct.addMarker(markersJct["" + ID]);
                                    }
                                }


                            }
                        }
                    }
                    document.getElementById("jctCount").textContent = "(" + distinctJct.length + ")";
                    document.getElementById("manholesCountWithJct").textContent = "(" + distinctManholesWithJct.length + ")";
                    if (distinctManholesWithJct.length > 0) {
                        $('.showHideAllManholesWithJctCheckbox').prop('checked', true);
                        $(".showHideAllManholesWithJctCheckbox").attr('disabled', false);
                    }
                    else {
                        $('.showHideAllManholesWithJctCheckbox').prop('checked', false);
                        $(".showHideAllManholesWithJctCheckbox").attr('disabled', true);
                    }
                    if (distinctHandholesWithJct.length > 0) {
                        $('.showHideAllHandholesWithJctCheckbox').prop('checked', true);
                        $(".showHideAllHandholesWithJctCheckbox").attr('disabled', false);
                    }
                    else {
                        $('.showHideAllHandholesWithJctCheckbox').prop('checked', false);
                        $(".showHideAllHandholesWithJctCheckbox").attr('disabled', true);
                    }
                    if (distinctJct.length > 0) {
                        $('.showHideAllJctCheckbox').prop('checked', true);
                        $(".showHideAllJctCheckbox").attr('disabled', false);
                    }
                    else {
                        $('.showHideAllJctCheckbox').prop('checked', false);
                        $(".showHideAllJctCheckbox").attr('disabled', true);
                    }



                },
                error: function(result) {
                    alert("Error");
                }
            });


        }

    }// end mapFlag condition
    else {
        if (ID.includes("DB_")) {
            if (markersDB[ID]) {
                if (markersDB[siteID].getMap() == null) {
                    markersDB[siteID].setMap(map);
                    markerClusterDB.addMarker(markersDB[siteID]);
                }
            }
        }
        else if (ID.includes("JCT_")) {

            if (markersJct[ID]) {
                if (markersJct[ID].getMap() == null) {
                    markersJct[ID].setMap(map);
                    markerClusterJct.addMarker(markersJct[ID]);
                }
            }
            else if (markersManholesWithJct[ID]) {
                if (markersManholesWithJct[ID].getMap() == null) {
                    markersManholesWithJct[ID].setMap(map);
                    markerClusterManholesWithJct.addMarker(markersManholesWithJct[ID]);
                }
            }
            else if (markersHandholesWithJct[ID]) {
                if (markersHandholesWithJct[ID].getMap() == null) {
                    markersHandholesWithJct[ID].setMap(map);
                    markerClusterHandholesWithJct.addMarker(markersHandholesWithJct[ID]);
                }
            }
        }
    }

    //Scroll to the map div
    document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });

}

function showLocation(ID, rowIndex) {

    var longitude = filteredGridData[rowIndex]["longitude"];
    var latitude = filteredGridData[rowIndex]["latitude"];
    var Name = filteredGridData[rowIndex]["locationName"];
    var locationType = filteredGridData[rowIndex]["locationType"];

    var latLng = new google.maps.LatLng(latitude, longitude);
    map.setZoom(16);
    map.panTo(latLng);


    if (mapFlag == "0") { // Show on map is not clicked before (markers are not set on map)

        if (locationType == "Customer") {
            $('.showHideAllCustCheckbox').prop('checked', true);
            $(".showHideAllCustCheckbox").attr('disabled', false);
            document.getElementById("custCount").textContent = "";
            if (!markersCustomer[ID]) {
                distinctCustomers.push(ID); //  this array is used when checking all cust from legend
                createMarker(ID, longitude, latitude, Name, 'customerIcon.png', markersCustomer, markerClusterCustomers, "customer")
            }
            document.getElementById("custCount").textContent = "(" + distinctCustomers.length + ")";
        }
        else if (locationType == "Site") {
            $('.showHideAllSitesCheckbox').prop('checked', true);
            $(".showHideAllSitesCheckbox").attr('disabled', false);
            document.getElementById("sitesCount").textContent = "";
            if (!markersSites[ID]) {
                distinctSites.push(ID); //  this array is used when checking all sites from legend
                createMarker(ID, longitude, latitude, Name, 'redSiteIcon.png', markersSites, markerClusterSites, "site")
            }
            document.getElementById("sitesCount").textContent = "(" + distinctSites.length + ")";
        }
        else if (locationType == "Manhole") {

            if (Name.endsWith("_J")) {
                $('.showHideAllManholesWithJctCheckbox').prop('checked', true);
                $(".showHideAllManholesWithJctCheckbox").attr('disabled', false);
                document.getElementById("manholesCountWithJct").textContent = "";

                if (!markersManholesWithJct[ID]) {
                    distinctManholesWithJct.push(ID); //  this array is used when checking all manholes from legend
                    createMarker(ID, longitude, latitude, Name, "manholeJct.png", markersManholesWithJct, markerClusterManholesWithJct, "manholeWithJct")
                }

            }
            else {
                $('.showHideAllManholesCheckbox').prop('checked', true);
                $(".showHideAllManholesCheckbox").attr('disabled', false);
                document.getElementById("manholesCount").textContent = "";

                if (!markersManholes[ID]) {
                    distinctManholes.push(ID); //  this array is used when checking all manholes from legend
                    createMarker(ID, longitude, latitude, Name, "manholeRed.png", markersManholes, markerClusterManholes, "manhole")
                }

            }
            document.getElementById("manholesCountWithJct").textContent = "(" + distinctManholesWithJct.length + ")";
            document.getElementById("manholesCount").textContent = "(" + distinctManholes.length + ")";
        }
        else if (locationType == "Handhole") {

            if (Name.endsWith("_J")) {
                $('.showHideAllHandholesWithJctCheckbox').prop('checked', true);
                $(".showHideAllHandholesWithJctCheckbox").attr('disabled', false);
                document.getElementById("handholesCountWithJct").textContent = "";

                if (!markersHandholesWithJct[ID]) {
                    distinctHandholesWithJct.push(ID);
                    createMarker(ID, longitude, latitude, Name, "handholeYellowJct.png", markersHandholesWithJct, markerClusterHandholesWithJct, "handholeWithJct")
                }

            }
            else {
                $('.showHideAllHandholesCheckbox').prop('checked', true);
                $(".showHideAllHandholesCheckbox").attr('disabled', false);
                document.getElementById("handholesCount").textContent = "";

                if (!markersHandholes[ID]) {
                    distinctHandholes.push(ID); //  this array is used when checking all handholes from legend
                    createMarker(ID, longitude, latitude, Name, "handholeYellow.png", markersHandholes, markerClusterHandholes, "handhole")
                }
            }

            document.getElementById("handholesCountWithJct").textContent = "(" + distinctHandholesWithJct.length + ")";
            document.getElementById("handholesCount").textContent = "(" + distinctHandholes.length + ")";

        }
        else if (locationType == "DB") {
            $('.showHideAllDbCheckbox').prop('checked', true);
            $(".showHideAllDbCheckbox").attr('disabled', false);
            document.getElementById("dbCount").textContent = "";
            if (!markersDB[ID]) {
                distinctDB.push(ID); //  this array is used when checking all db from legend
                //createDbMarker(ID,longitude,latitude,Name);
                createMarker(ID, longitude, latitude, Name, 'backboneDB.png', markersDB, markerClusterDB, "DB")

            }
            document.getElementById("dbCount").textContent = "(" + distinctDB.length + ")";
        }

    }// end mapFlag condition
    else {

        if (locationType == "Customer") {
            if (markersCustomer[ID]) {
                if (markersCustomer[ID].getMap() == null) {
                    markersCustomer[ID].setMap(map);
                    markerClusterCustomers.addMarker(markersCustomer[ID]);
                }
            }
        }
        else if (locationType == "Site") {
            if (markersSites[ID]) {
                if (markersSites[ID].getMap() == null) {
                    markersSites[ID].setMap(map);
                    markerClusterSites.addMarker(markersSites[ID]);
                }
            }
        }
        else if (locationType == "Manhole") {
            if (Name.endsWith("_J")) {
                if (markersManholesWithJct[ID]) {
                    if (markersManholesWithJct[ID].getMap() == null) {
                        markersManholesWithJct[ID].setMap(map);
                        markerClusterManholesWithJct.addMarker(markersManholesWithJct[ID]);
                    }
                }
            }
            else {
                if (markersManholes[ID]) {
                    if (markersManholes[ID].getMap() == null) {
                        markersManholes[ID].setMap(map);
                        markerClusterManholes.addMarker(markersManholes[ID]);
                    }
                }
            }

        }
        else if (locationType == "Handhole") {
            if (Name.endsWith("_J")) {
                if (markersHandholesWithJct[ID]) {
                    if (markersHandholesWithJct[ID].getMap() == null) {
                        markersHandholesWithJct[ID].setMap(map);
                        markerClusterHandholesWithJct.addMarker(markersHandholesWithJct[ID]);
                    }
                }
            }
            else {
                if (markersHandholes[ID]) {
                    if (markersHandholes[ID].getMap() == null) {
                        markersHandholes[ID].setMap(map);
                        markerClusterHandholes.addMarker(markersHandholes[ID]);
                    }
                }
            }
        }
        else if (locationType == "DB") {
            if (markersDB[ID]) {
                if (markersDB[ID].getMap() == null) {
                    markersDB[ID].setMap(map);
                    markerClusterDB.addMarker(markersDB[ID]);
                }
            }
        }
    }

    //Scroll to the map div
    document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });

}