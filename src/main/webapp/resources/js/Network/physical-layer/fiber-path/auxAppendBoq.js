/*
|--------------------------------------------------------------------------
| AUXILIARY TABLE EVENTS
|-------------------------------------------------------------------------- 
| Register ONCE only
| Put this in initialization js file (NOT inside AuxAppendBOQ)
|--------------------------------------------------------------------------
*/

// to be deleted
/*
// Active row highlight ONLY for auxiliary path tables
$(document).on("focusin", ".auxiliary-table-path tr", function() {

    const $row = $(this);
    const $table = $row.closest(".auxiliary-table-path");

    $table.find("tr.ativeRecord").removeClass("ativeRecord");
    $row.addClass("ativeRecord");
});
*/


// Distance recalculation
let auxDistanceTimer;

$(document).on(
    "focusout",
    ".auxiliary-table-path .aux-long, .auxiliary-table-path .aux-lat",
    function() {
		
		console.log("Focus out, this console from auxAppendBoq file");

        clearTimeout(auxDistanceTimer);

        auxDistanceTimer = setTimeout(() => {

            const $table = $(this).closest(".auxiliary-table-path");
            const tableId = $table.attr("id");

            const ctx = window._auxTableContext?.[tableId];

            if (!ctx) return;

            calculateDistanceSourceDestination(
                $("#" + ctx.SourceLat).val(),
                $("#" + ctx.SourceLng).val(),
                $("#" + ctx.DestinationLat).val(),
                $("#" + ctx.DestinationLng).val(),
                tableId
            );

        }, 300);
    }
);


$(document).on("focus", ".auxiliary-table-path .aux-name", function() {

    const $input = $(this);

    // already initialized
    if ($input.data("autocomplete-initialized")) {
        return;
    }

    const $table = $input.closest(".auxiliary-table-path");
    const tableId = $table.attr("id");

    const ctx = window._auxTableContext?.[tableId];

    if (!ctx) {
        return;
    }

    const pointId = $input.attr("id");

    const longId = $input
        .closest("tr")
        .find(".aux-long")
        .attr("id");

    const latId = $input
        .closest("tr")
        .find(".aux-lat")
        .attr("id");

    const rowIndex = $input
        .closest("tr")
        .data("row-index");

    AuxPointAutoComplete(
        ctx.auxPtAutocomplete,
        pointId,
        longId,
        latId,
        ctx.SourceLat,
        ctx.SourceLng,
        ctx.DestinationLat,
        ctx.DestinationLng,
        ctx.auxiliaryTable,
        rowIndex
    );

    $input.data("autocomplete-initialized", true);
});


// Total driving distance
$(document).on("focusout", ".auxiliary-table-path .aux-name", function() {

    const $table = $(this).closest(".auxiliary-table-path");
    const tableId = $table.attr("id");

    if (!tableId || !window._auxTableContext?.[tableId]) {
        return;
    }

    const ctx = window._auxTableContext[tableId];

    getTotalDrivingDistance(
        ctx.TargetId,
        ctx.SourceLat,
        ctx.SourceLng,
        ctx.DestinationLat,
        ctx.DestinationLng,
        ctx.target,
        ctx.totalDistanceDrivg,
        ctx.FiberDrivDist
    );
});



/*
|--------------------------------------------------------------------------
| MAIN FUNCTION
|--------------------------------------------------------------------------
*/

function AuxAppendBOQ(auxArray, insertType, OrigiTermination, Target, indx) {

    console.log("Welcome to appendBOQ");
    console.log("auxArray is ", auxArray);

    // ---------------------------------------------------------------------
    // Store table context
    // ---------------------------------------------------------------------

    window._auxTableContext = window._auxTableContext || {};
    window._auxTableContext[Target.auxiliaryTable] = Target;


    // ---------------------------------------------------------------------
    // Cached DOM
    // ---------------------------------------------------------------------

    const $table = $("#" + Target.auxiliaryTable);
    const $tbody = $table.find("tbody");

    const sourceLat = $("#" + Target.SourceLat).val();
    const sourceLng = $("#" + Target.SourceLng).val();
    const destLat = $("#" + Target.DestinationLat).val();
    const destLng = $("#" + Target.DestinationLng).val();


    // ---------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------

    function resolveAuxName(value, currentIndex) {

        // Import
        if (Target.Action === "Import") {
            return value[3] == null ? "null" : value[3];
        }

        // Empty row
        if (value[3] === "") {
            return "";
        }

        // Warehouse
        if (value[3] !== "null" && value[3] != null) {
            return value[3] + ":" + value[5] + ":" + value[4];
        }

        // Auxiliary point
        if (value[5] && value[5].includes("Auxiliary_Point")) {
            return value[10] + ":" + value[5];
        }

        // MH / HH / DB
        if (
            value[4] &&
            (
                value[4].split("_")[0] === "MH" ||
                value[4].split("_")[0] === "HH" ||
                value[4].split("_")[0] === "DB"
            )
        ) {
            return value[4] + ":" + value[5];
        }

        return value[5] || ("Auxiliary_Point " + (currentIndex + 1));
    }



    function getTargetConfig() {

        switch (Target.target) {

            case "fiber":
                return {

                    rowIdPrefix: "auxiliary_Row",

                    rowClass: "rowAboveBelow",

                    tdSeqName: "fiberSeq",
                    seqName: "fiberCableSeq",

                    auxNameTd: "auxiliary_Name",
                    auxLongitudeTd: "auxiliary_Longitude",
                    auxLatitudeTd: "auxiliary_Latitude",
                    auxLengthTd: "auxiliary_Length",

                    auxDrivingTd: "auxDrivingDist",
                    auxGeoTd: "auxGeoDistance",

                    auxPrefix: "aux",

                    seqWidth: "60px"
                };

            case "strand":
                return {

                    rowIdPrefix: "auxiliary_Row",

                    rowClass: "rowAboveBelowStrand",

                    tdSeqName: "fiberSeq",
                    seqName: "strandSeq",

                    auxNameTd: "auxiliary_NameStrand",
                    auxLongitudeTd: "auxiliary_Longitude",
                    auxLatitudeTd: "auxiliary_Latitude",
                    auxLengthTd: "auxiliary_Length",

                    auxDrivingTd: "auxDrivingDistStrand",
                    auxGeoTd: "auxGeoDistanceStrand",

                    auxPrefix: "auxStrand",

                    seqWidth: "60px"
                };

            case "trench":
                return {

                    rowIdPrefix: "auxiliaryTrench_Row",

                    rowClass: "rowAboveBelowTrench",

                    tdSeqName: "TrenchSeq",
                    seqName: "TrenchSeq",

                    auxNameTd: "auxiliaryTrench_Name",
                    auxLongitudeTd: "auxiliaryTrench_Longitude",
                    auxLatitudeTd: "auxiliaryTrench_Latitude",
                    auxLengthTd: "auxiliaryTrench_Length",

                    auxDrivingTd: "auxDrivingDistTrench",
                    auxGeoTd: "auxGeoDistanceTrench",

                    auxPrefix: "auxTrench",

                    seqWidth: "60px"
                };

            case "duct":
                return {

                    rowIdPrefix: "auxiliaryDuct_Row",

                    rowClass: "rowAboveBelowDuct",

                    tdSeqName: "DuctSeq",
                    seqName: "DuctSeq",

                    auxNameTd: "auxiliaryDuct_Name",
                    auxLongitudeTd: "auxiliary_LongitudeDuct",
                    auxLatitudeTd: "auxiliary_LatitudeDuct",
                    auxLengthTd: "auxiliaryDuct_Length",

                    auxDrivingTd: "auxDrivingDistDuct",
                    auxGeoTd: "auxGeoDistanceDuct",

                    auxPrefix: "auxDuct",

                    seqWidth: "60px"
                };

            case "tube":
                return {

                    rowIdPrefix: "tubeAux_Row",

                    rowClass: "rowAboveBelowFiberTube",

                    tdSeqName: "fiberTubeAuxSeq",
                    seqName: "tubeAuxSeq",

                    auxNameTd: "TubeAuxiliary",
                    auxLongitudeTd: "TubeAuxiliary_Longitude",
                    auxLatitudeTd: "TubeAuxiliary_Latitude",
                    auxLengthTd: "TubeAuxiliaryLng",

                    auxDrivingTd: "auxDrivingDistFiberTube",
                    auxGeoTd: "auxGeoDistanceFiberTube",

                    auxPrefix: "tubeAuxiliary",

                    seqWidth: "60px"
                };

            default:
                return {

                    rowIdPrefix: "auxiliary_Row",

                    rowClass: "rowAboveBelow",

                    tdSeqName: "fiberSeq",
                    seqName: "fiberCableSeq",

                    auxNameTd: "auxiliary_Name",
                    auxLongitudeTd: "auxiliary_Longitude",
                    auxLatitudeTd: "auxiliary_Latitude",
                    auxLengthTd: "auxiliary_Length",

                    auxDrivingTd: "auxDrivingDist",
                    auxGeoTd: "auxGeoDistance",

                    auxPrefix: "aux",

                    seqWidth: "60px"
                };
        }
    }

    const cfg = getTargetConfig();


    // ---------------------------------------------------------------------
    // Build HTML (FAST)
    // ---------------------------------------------------------------------

    let html = "";

    console.log("auxArray is ", auxArray);

    window._auxMapMarkerFlag = 0;

    auxArray.forEach(function(value, i) {

        let longitude = "";
        let latitude = "";
        let length = "";

        // -------------------------------------------------------------
        // Handle coordinates
        // -------------------------------------------------------------

        if (insertType === "createFromMap") {

            // Existing AUX coming from DB
            if (value.length === 11) {

                longitude = value[0] || "";
                latitude = value[1] || "";
                length = value[2] || "";
            }

            // Newly created AUX from map
            else {

                length = value[2] || "";

                let markerIndex = 0;

                if (
                    OrigiTermination === "NoOrigiNotermination" ||
                    OrigiTermination === "terminationOnly"
                ) {

                    markerIndex = window._auxMapMarkerFlag + 1;
                }

                else if (
                    OrigiTermination === "originationOnly" ||
                    OrigiTermination === "origi&termination"
                ) {

                    markerIndex = window._auxMapMarkerFlag;
                }

                else {

                    markerIndex = window._auxMapMarkerFlag;
                }

                if (MarkerArray[markerIndex]) {

                    longitude = MarkerArray[markerIndex].position.lng();
                    latitude = MarkerArray[markerIndex].position.lat();
                }

                window._auxMapMarkerFlag++;
            }
        }

        else {

            longitude = value[0] || "";
            latitude = value[1] || "";
            length = value[2] || "";
        }


        let auxDrivingDist = value[8] || 0;
        let auxGeoDistance = value[9] || 0;

        let auxName = resolveAuxName(value, i);


        // -------------------------------------------------------------
        // createFromMap special handling
        // -------------------------------------------------------------

        if (insertType === "createFromMap") {

            if (value.length !== 11) {

                if (
                    value[0] === 'NULL' &&
                    value[1] === 'NULL' &&
                    value[2] === 'NULL'
                ) {

                    auxName = "Auxiliary_Point " + (i + 1);
                }

                else {

                    auxName = value[0] + ":" + value[1];
                }
            }
        }


        // -------------------------------------------------------------
        // Determine runtime index
        // -------------------------------------------------------------

        let runtimeIndex;

        switch (Target.target) {

            case "trench":
                runtimeIndex = indextrench++;
                break;

            case "duct":
                runtimeIndex = indexduct++;
                break;

            default:
                runtimeIndex = index++;
                break;
        }


        // -------------------------------------------------------------
        // Build row
        // -------------------------------------------------------------


        html += `
	    <tr id="${cfg.rowIdPrefix}${runtimeIndex}">
	        <td>
	            <input 
	                class="${cfg.rowClass} row-select"
	                type="checkbox"
	                name="record"
	                style="position:relative;left:20px;top:10px"
	            >
	        </td>

	        <td name="${cfg.tdSeqName}">
	            <input
	                name="${cfg.seqName}"
	                class="form-control text-input"
	                type="text"
	                value="${runtimeIndex + 1}"
	                readonly
	                style="width:${cfg.seqWidth};position:relative;"
	            />
	        </td>

	        <td name="${cfg.auxNameTd}">
	            <input
	                id="${cfg.auxPrefix}_Point${runtimeIndex}"
	                class="form-control text-input aux-name"
	                type="text"
	                value="${auxName}"
	                style="width:200px;position:relative;"
	            />
	        </td>

	        <td name="${cfg.auxLongitudeTd}">
	            <input
	                id="${cfg.auxPrefix}_Long${runtimeIndex}"
	                name="${cfg.auxPrefix}_Long"
	                class="form-control text-input aux-long"
	                type="text"
	                value="${longitude}"
	                style="width:150px;position:relative;"
	            />
	        </td>

	        <td name="${cfg.auxLatitudeTd}">
	            <input
	                id="${cfg.auxPrefix}_Lat${runtimeIndex}"
	                name="${cfg.auxPrefix}_Lat"
	                class="form-control text-input ui-widget ui-widget-content ui-corner-all aux-lat"
	                type="text"
	                value="${latitude}"
	                style="width:150px;position:relative;"
	            />
	        </td>

	        <td name="${cfg.auxLengthTd}">
	            <input
	                id="${cfg.auxPrefix}_Len${runtimeIndex}"
	                class="form-control text-input ui-widget ui-widget-content ui-corner-all"
	                type="text"
	                value="${length}"
	                style="width:150px;position:relative;"
	            />
	        </td>

	        <td name="${cfg.auxDrivingTd}">
	            <input
	                id="${cfg.auxPrefix}DrivingDist${runtimeIndex}"
	                class="form-control text-input ui-widget ui-widget-content ui-corner-all"
	                type="text"
	                value="${auxDrivingDist}"
	                style="width:92px;position:relative;"
	            />
	        </td>

	        <td name="${cfg.auxGeoTd}">
	            <input
	                id="${cfg.auxPrefix}GeoDistance${runtimeIndex}"
	                class="form-control text-input ui-widget ui-widget-content ui-corner-all"
	                type="text"
	                value="${auxGeoDistance}"
	                style="width:92px;position:relative;"
	            />
	        </td>

	    </tr>
	    `;
    });


    // ---------------------------------------------------------------------
    // Create rows object once
    // ---------------------------------------------------------------------

    const $rows = $(html);

    // ---------------------------------------------------------------------
    // INSERT LOGIC
    // ---------------------------------------------------------------------

    const $checkedRow = $tbody
        .find("." + cfg.rowClass + ":checked")
        .first()
        .closest("tr");


    if (insertType === "rowAboveChecked") {

        if ($checkedRow.length) {

            $checkedRow.before($rows);

            $tbody.find("." + cfg.rowClass).prop("checked", false);

            $checkedRow.prev().find("." + cfg.rowClass).prop("checked", true);

        }
        else {
            $tbody.append($rows);
        }
    }

    else if (insertType === "rowBelowChecked") {

        if ($checkedRow.length) {

            $checkedRow.after($rows);

            $tbody.find("." + cfg.rowClass).prop("checked", false);

            $checkedRow.next().find("." + cfg.rowClass).prop("checked", true);

            $tbody.find("tr").removeClass("ativeRecord");

            $checkedRow.next().addClass("ativeRecord");
        }
        else {
            $tbody.append($rows);
        }
    }

    else {

        $tbody.append($rows);
    }


    // ---------------------------------------------------------------------
    // Final calculation ONCE
    // ---------------------------------------------------------------------

    calculateDistanceSourceDestination(
        sourceLat,
        sourceLng,
        destLat,
        destLng,
        Target.auxiliaryTable
    );
}

// Append to Auxiliary From Map
function appendingToAuxFromMap(modalId, AuxFlag, auxPtAutocomplete, srcDestAutoComplete, selectedID, pushPoints, cancelPoints, selectedTemp) {

    console.log("welcome to append to aux from map, the modalId is ", modalId + " selectedTemp is ", selectedTemp);

    clearCreateFromMap(markerArrayAux);
    Path_Array = [];
    auxLatLng = [];
    MarkerArray = [];
    markerArrayAux = [];
    draw = true;

    $("#" + modalId).find('input:file').val('');
    $("#" + modalId).find('input:checkbox').prop("checked", false);

    document.querySelector("#" + AuxFlag).value = "opened";

    //Uncheck all checkboxes when opening the popup
    uncheckAutoCompleteCheckboxes(auxPtAutocomplete);
    uncheckAutoCompleteCheckboxes(srcDestAutoComplete);

    $("#" + pushPoints + selectedTemp).removeAttr('hidden');
    $("#" + cancelPoints + selectedTemp).removeAttr('hidden');

    // map click create marker listener
    listener1 = map.addListener('click', createPathh);
    $("." + cancelPoints + selectedTemp).unbind('click');

    // abort drawing and disable listeners
    $("#" + cancelPoints + selectedTemp).on('click', function(event) {
        clearCreateFromMap(markerArrayAux);
        EnableOriginationFiber = false;
        $(".origination,.termination").addClass('disabled');
        event.stopPropagation();
    });

    $("#" + pushPoints + selectedTemp).unbind('click');
    $("#" + pushPoints + selectedTemp).bind('click', function(event) {

        $('body').append('<div id="loading"><img id="loading-image" src="' + getContext() + '/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')

        if (modalId == "fiberPathModal") {
            url = 'findFiberAuxDetails'
        }
        else if (modalId == "StrandModal") {
            url = 'findStrandDetails'
        }
        else if (modalId == "trenchModal") {
            url = 'findTrenchDetails'
        }
        else if (modalId == "ductModal") {
            url = 'findDuctDetails'
        }
        else {
            url = 'findTubeDetails'
        }
        for (var x = 0;x < MarkerArray.length;x++) {
            auxLat = MarkerArray[x].position.lat().toString().slice(0, -1);
            auxLng = MarkerArray[x].position.lng().toString().slice(0, -1);

            auxLatLng.push({
                "auxLat": auxLat,
                "auxLng": auxLng
            });
        }
        console.log("auxLatLng is ", auxLatLng);
        $.ajax({
            type: "GET",
            url: getContext() + '/' + url,
            data: {
                "ID": selectedID,
                "dictParameter": auxLatLng
            },
            dataType: "json",
            success: function(data) {
                auxDict = data.auxData;
                auxDetails = [];
                var auxLatLng = [];
                auxArray = data.auxPtSearch;
                auxDict.push.apply(auxDict, auxArray);

                console.log("auxDict is ", auxDict);

                if (modalId == "fiberPathModal") {
                    AuxAppendBOQ(auxDict, "createFromMap", "", TargetFiber, index);
                }
                else if (modalId == "StrandModal") {
                    AuxAppendBOQ(auxDict, "createFromMap", "", TargetStrand, index);
                }
                else if (modalId == "trenchModal") {
                    AuxAppendBOQ(auxDict, "createFromMap", "", TargetTrench, indextrench);
                }
                else if (modalId == "ductModal") {
                    AuxAppendBOQ(auxDict, "createFromMap", "", TargetDuct, indexduct);
                }
                else {
                    AuxAppendBOQ(auxDict, "createFromMap", "", TargetTube, index);
                }

                $("#loading").remove();
                $("#" + modalId).modal('show');

                //data = null
                auxArray = [];
            }, // end of success
            error: function(result) {
                alert("Error");
            }
        }) // end of ajax

        event.stopPropagation();
    })
}
