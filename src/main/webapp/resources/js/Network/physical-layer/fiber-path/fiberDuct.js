function loadFiberDuctTable(dataArray) {
    const fiberDuctTableBody = $("#fiberDuctTable tbody");
    fiberDuctTableBody.empty();
    if (!dataArray || dataArray.length === 0) {
        updateFiberDuctTotals();
        return;
    }

    $.each(dataArray, function(index, item) {
        appendFiberDuctRow(item);
    });

    refreshFiberDuctSequence();
    calculateFiberDuctLineDistances();
    /*
        $("#fiberDuctTable tbody tr").each(function() {
            initFiberDuctRowAutoComplete($(this));
        });
    */
    bindFiberDuctAutoCompleteOnFocus()
}

function appendFiberDuctRow(data = {}) {

    const fiberDuctTableBody = $("#fiberDuctTable tbody");
    let rowCount = $("#fiberDuctTable tbody tr").length + 1;
    let row = `
        <tr class="fiberDuctRow">

            <td>
                <input type="checkbox" class="fiberDuctRowCheck">
            </td>

            <td class="fiberDuctSequence headcol">
                ${data.sequenceNo || rowCount}
            </td>

            <td>
                <input type="text" class="form-control ductId"
                    value="${data.ductId || ''}">
            </td>

            <td>
                <input type="text" class="form-control ductName"
                    value="${data.ductName || ''}">
            </td>

            <td>
                <input type="text" class="form-control fromSequence fiberAuxSeqAC"
                    value="${data.fromSequence || ''}">
            </td>

            <td>
                <input type="text" class="form-control fromAuxId fiberAuxIdAC"
                    value="${data.fromAuxId || ''}">
            </td>

            <td>
                <input type="text" class="form-control fromAuxName fiberAuxNameAC"
                    value="${data.fromAuxName || ''}">
            </td>

            <td>
                <input type="text" class="form-control fromLongitude"
                    value="${data.fromLongitude || ''}">
            </td>

            <td>
                <input type="text" class="form-control fromLatitude"
                    value="${data.fromLatitude || ''}">
            </td>

            <td>
                <input type="text" class="form-control toSequence fiberAuxSeqAC"
                    value="${data.toSequence || ''}">
            </td>

            <td>
                <input type="text" class="form-control toAuxId fiberAuxIdAC"
                    value="${data.toAuxId || ''}">
            </td>

            <td>
                <input type="text" class="form-control toAuxName fiberAuxNameAC"
                    value="${data.toAuxName || ''}">
            </td>

            <td>
                <input type="text" class="form-control toLongitude"
                    value="${data.toLongitude || ''}">
            </td>

            <td>
                <input type="text" class="form-control toLatitude"
                    value="${data.toLatitude || ''}">
            </td>

            <td>
                <input type="text" class="form-control distance"
                    readonly
                    value="${data.distanceKm || ''}">
            </td>

            <td>
                <input type="text" class="form-control geoDistance"
                    readonly
                    value="${data.geoDistanceKm || ''}">
            </td>

        </tr>
    `;

    fiberDuctTableBody.append(row);
}

function bindFiberDuctButtons() {

    $("#selectAll_fiberDuct").off("click").on("click", function() {

        let allChecked =
            $(".fiberDuctRowCheck").length ===
            $(".fiberDuctRowCheck:checked").length;

        $(".fiberDuctRowCheck").prop("checked", !allChecked);

        $("#fiberDuctTable tbody tr").toggleClass(
            "selectedRow",
            !allChecked
        );

    });

    $("#addFiberDuctBelow").off("click").on("click", function() {

        const $tbody = $("#fiberDuctTable tbody");
        const $checkedRow = getFirstCheckedFiberDuctRow();
        const $newRow = $(appendFiberDuctRowHtml());

        if ($checkedRow.length) {
            $checkedRow.after($newRow);
        } else {
            $tbody.append($newRow);
        }

        refreshFiberDuctSequence();

        const $targetRow = $checkedRow.length ? $checkedRow.next() : $tbody.find("tr:last");

        initFiberDuctRowAutoComplete($targetRow);

        $(".fiberDuctRowCheck").prop("checked", false);
        $("#fiberDuctTable tbody tr").removeClass("selectedRow activeRecord");

        $targetRow.find(".fiberDuctRowCheck").prop("checked", true);
        $targetRow.addClass("selectedRow activeRecord");
        $targetRow.find(".ductId").focus();
    });


    $("#addFiberDuctAbove").off("click").on("click", function() {

        const $tbody = $("#fiberDuctTable tbody");
        const $checkedRow = getFirstCheckedFiberDuctRow();
        const $newRow = $(appendFiberDuctRowHtml());

        if ($checkedRow.length) {
            $checkedRow.before($newRow);
        } else {
            $tbody.prepend($newRow);
        }

        refreshFiberDuctSequence();
        initFiberDuctRowAutoComplete($newRow);

        $(".fiberDuctRowCheck").prop("checked", false);
        $("#fiberDuctTable tbody tr").removeClass("selectedRow activeRecord");

        $newRow.find(".fiberDuctRowCheck").prop("checked", true);
        $newRow.addClass("selectedRow activeRecord");
        $newRow.find(".ductId").focus();

    });

    $("#deleteFiberDuct").off("click").on("click", function() {

        let selectedRow = getSelectedFiberDuctRow();
        if (selectedRow.length === 0) {
            alert("Please select row");
            return;
        }

        selectedRow.remove();
        refreshFiberDuctSequence();
        updateFiberDuctTotals();
    });

    $("#calculateFiberDuctGeoDistance").off("click").on("click", function() {

        calculateFiberDuctGeoDistances();

    });

}

function appendFiberDuctRowHtml() {

    return `
        <tr class="fiberDuctRow">
            <td>
                <input type="checkbox" class="fiberDuctRowCheck">
            </td>

            <td class="fiberDuctSequence headcol"></td>
            <td><input type="text" class="form-control ductId"></td>
            <td><input type="text" class="form-control ductName"></td>
            <td><input type="text" class="form-control fromSequence fiberAuxSeqAC"></td>
            <td><input type="text" class="form-control fromAuxId fiberAuxIdAC"></td>
            <td><input type="text" class="form-control fromAuxName fiberAuxNameAC"></td>
            <td><input type="text" class="form-control fromLongitude"></td>
            <td><input type="text" class="form-control fromLatitude"></td>
            <td><input type="text" class="form-control toSequence fiberAuxSeqAC"></td>
            <td><input type="text" class="form-control toAuxId fiberAuxIdAC"></td>
            <td><input type="text" class="form-control toAuxName fiberAuxNameAC"></td>
            <td><input type="text" class="form-control toLongitude"></td>
            <td><input type="text" class="form-control toLatitude"></td>
            <td><input type="text" class="form-control distance" readonly></td>
            <td><input type="text" class="form-control geoDistance" readonly></td>
        </tr>
    `;
}

function getFirstCheckedFiberDuctRow() {

    return $("#fiberDuctTable tbody")
        .find(".fiberDuctRowCheck:checked")
        .first()
        .closest("tr");
}

function getSelectedFiberDuctRow() {
    return $("#fiberDuctTable tbody .fiberDuctRowCheck:checked").closest("tr");
}

function refreshFiberDuctSequence() {

    $("#fiberDuctTable tbody tr").each(function(index) {
        $(this).find(".fiberDuctSequence").text(index + 1);
    });
}

function calculateFiberDuctLineDistances() {

    let total = 0;

    $("#fiberDuctTable tbody tr").each(function() {

        let row = $(this);

        let fromLng = parseFloat(row.find(".fromLongitude").val());
        let fromLat = parseFloat(row.find(".fromLatitude").val());

        let toLng = parseFloat(row.find(".toLongitude").val());
        let toLat = parseFloat(row.find(".toLatitude").val());

        if (
            isNaN(fromLng) ||
            isNaN(fromLat) ||
            isNaN(toLng) ||
            isNaN(toLat)
        ) {
            row.find(".distance").val("");
            return;
        }

        let distance = haversine_distance(fromLat, fromLng, toLat, toLng);

        row.find(".distance").val(distance.toFixed(2));

        total += distance;

    });

    $("#totalDistanceFiberDuct").val(total.toFixed(2));

}

function calculateFiberDuctGeoDistances() {

    let totalGeo = 0;

    $("#fiberDuctTable tbody tr").each(function() {
        calculateFiberDuctLineDistances();

        let row = $(this);

        let fromLng = parseFloat(row.find(".fromLongitude").val());
        let fromLat = parseFloat(row.find(".fromLatitude").val());

        let toLng = parseFloat(row.find(".toLongitude").val());
        let toLat = parseFloat(row.find(".toLatitude").val());

        if (
            isNaN(fromLng) ||
            isNaN(fromLat) ||
            isNaN(toLng) ||
            isNaN(toLat)
        ) {
            row.find(".geoDistance").val("");
            return;
        }

        let geo = google_geo_distance(fromLat, fromLng, toLat, toLng);
        row.find(".geoDistance").val(geo);
        totalGeo += geo;

    });

    $("#totalGeoDistanceFiberDuct").val(totalGeo.toFixed(2));

}


function google_geo_distance(lat1, lng1, lat2, lng2) {

    if (
        lat1 == null || lng1 == null ||
        lat2 == null || lng2 == null
    ) {
        return 0;
    }

    var p1 = new google.maps.LatLng(parseFloat(lat1), parseFloat(lng1));
    var p2 = new google.maps.LatLng(parseFloat(lat2), parseFloat(lng2));

    var d = google.maps.geometry.spherical.computeDistanceBetween(p1, p2);

    return parseFloat((d / 1000).toFixed(3)); // km
}

function updateFiberDuctTotals() {

    calculateFiberDuctLineDistances();
    calculateFiberDuctGeoDistances();

}

function getFiberDuct() {
    let fiberDuctDict = [];

    $("#fiberDuctTable tbody tr").each(function() {

        let row = $(this);

        fiberDuctDict.push({

            sequence: row.find(".fiberDuctSequence").text(),

            ductId: row.find(".ductId").val(),
            ductName: row.find(".ductName").val(),

            fromSequence: row.find(".fromSequence").val(),
            fromAuxId: row.find(".fromAuxId").val(),
            fromAuxName: row.find(".fromAuxName").val(),
            fromLongitude: row.find(".fromLongitude").val(),
            fromLatitude: row.find(".fromLatitude").val(),

            toSequence: row.find(".toSequence").val(),
            toAuxId: row.find(".toAuxId").val(),
            toAuxName: row.find(".toAuxName").val(),
            toLongitude: row.find(".toLongitude").val(),
            toLatitude: row.find(".toLatitude").val(),

            distance: row.find(".distance").val(),
            geoDistance: row.find(".geoDistance").val()

        });

    });

    return fiberDuctDict;
}

function bindFiberDuctAutoCompleteOnFocus() {

    const $table = $("#fiberDuctTable");

    $table.off("focus.autocomplete", "input").on("focus.autocomplete", "input", function() {

        const $input = $(this);

        if ($input.data("ac-initialized")) return;

        const $row = $input.closest("tr");

        initFiberDuctRowAutoComplete($row);
    });
}

function initFiberDuctRowAutoComplete($row) {

    $row.find(".fiberAuxSeqAC").each(function() {
        const input = $(this);
        if (!input.data("ac-initialized")) {
            fiberAuxAutoComplete(input, "SEQ");
            input.data("ac-initialized", true);
        }
    });

    $row.find(".fiberAuxIdAC").each(function() {
        const input = $(this);
        if (!input.data("ac-initialized")) {
            fiberAuxAutoComplete(input, "ID");
            input.data("ac-initialized", true);
        }
    });

    $row.find(".fiberAuxNameAC").each(function() {
        const input = $(this);
        if (!input.data("ac-initialized")) {
            fiberAuxAutoComplete(input, "NAME");
            input.data("ac-initialized", true);
        }
    });

    $row.find(".ductId").each(function() {
        const input = $(this);
        if (!input.data("ac-initialized")) {
            fiberDuctAutoComplete(input, "ID");
            input.data("ac-initialized", true);
        }
    });

    $row.find(".ductName").each(function() {
        const input = $(this);
        if (!input.data("ac-initialized")) {
            fiberDuctAutoComplete(input, "NAME");
            input.data("ac-initialized", true);
        }
    });
}