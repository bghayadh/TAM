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
					    Cables Details
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
						Pan To
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
						Show From Aux
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
    window["mapPointsNamesBasedOnGrid_" + ductID] = [];
    window["mapPointsBasedOnGrid_" + ductID] = [];

    var center = new google.maps.LatLng(1, 38);
    map.setCenter(center);
    map.setZoom(6);


    if (dataArray.length > 0) {
        window["mapPointsNamesBasedOnGrid_" + ductID] = [];
        window["mapPointsBasedOnGrid_" + ductID] = [];

        var ArrayKeys = Object.keys(dataArray[0]);
        var columnVal;
        var data = [];//for export
        var jctUsedStrands = 0, dbUsedStrands = 0;
        exportArrayGrid = [];
        jctElementsIDArray = [];
        jctElementsFlag = "notOpened";
        data.push('\r');
        data.push(["Duct Section Drawing", "Cable Qty", "Cables Details", "From Sequence", "From Auxiliary ID", "From Auxiliary Name", "From Longitude", "From Latitude", "To Sequence", "To Auxiliary ID", "To Longitude", "To Latitude", "Pan To", "Show Element"]);

        filteredGridData = dataArray; // used in draw on map 

        for (var i = 0;i < dataArray.length;i++) {
            data.push('\r');
            for (var j = 0;j < ArrayKeys.length;j++) {
                columnVal = ArrayKeys[j];
                if (columnVal != "panTo" && columnVal != "showElement") {
                    data.push(dataArray[i][ArrayKeys[j]]);
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

function panToAux(id) {

    const aux = auxPointIndex[id];
    if (!aux) return;

    const latLng = new google.maps.LatLng(aux.lat, aux.long);

    map.panTo(latLng);
    map.setZoom(15);
    //Scroll to the map div
    document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
}

function toggleAuxPointMarker(el, id, name, lat, lng) {

    console.log("Welcome totoggleAux, the id is ", id);
    const checked = el.checked;
    console.log("checked is ", checked);
    const type = detectAuxType(id, name);
	
	const segment = window.reportSegmentByAuxId[id];
	


    const bucket = resolveMarkerBucket(type);
    if (!bucket) return;
    if (checked) {
        if (!bucket.arr[id]) {

            const pos = new google.maps.LatLng(lat, lng);

            console.log("checked is true, this after if the statement");
            const marker = createMarker(
                id,
                lng,
                lat,
                name,
                resolveIcon(bucket.icon, window.ductLayer.viewMode),
                bucket.target
            );


			marker.segment = segment;
            bucket.arr[id] = marker;


            attachMarkerBehavior(marker);
            applyRenderingPolicy(marker, type);

            map.panTo(pos);
            map.setZoom(15);
        }
    }
    else {
        console.log("marker existed and the check is ", checked + " and we need to remove the marker");
        removeMarker(id, type);
    }
}