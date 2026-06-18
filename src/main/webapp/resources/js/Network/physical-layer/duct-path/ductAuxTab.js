function ductAuxTabEvents() {

    $("#duct_aux_tab").on("click", function() {
        ductAuxTab();
    });


    /*
    |------------------------------------------------------------
    | Track previous AUX name value
    |------------------------------------------------------------
    */
    $(document).on(
        "focusin",
        "#auxiliary_ductTable td[name='auxiliaryDuct_Name'] input",
        function() {
            $(this).data("val", $(this).val());
        }
    );


    /*
    |------------------------------------------------------------
    | SELECT ALL
    |------------------------------------------------------------
    */
    $(document).on('click', '#selectAllDuct_Aux', function() {
        console.log("Welcome to select All Duct Aux Points");

        if ($(this).hasClass('allChecked')) {
            console.log("it has the allChecked class, so we need to uncheck");
            $('#auxiliary_ductTable tbody input[name="record"]').prop('checked', false);
            setTimeout(() => {
                showHideAllPoints(
                    selectedDuctContext,
                    "ductCheckSequence",
                    "Hide"
                );
            }, 0);

            window['ductCheckPoints_' + selectedDuctContext] = "unchecked";
            window['ductCheckRealPoints_' + selectedDuctContext] = "unchecked";
            window['ductCheckSequence_' + selectedDuctContext] = "unchecked";

            var index = allDuctsShowPoint.indexOf(selectedDuctContext);

            if (index > -1) {
                allDuctsShowPoint.splice(index, 1);
            }

            var index = allDuctsShowRealPoint.indexOf(selectedDuctContext);

            if (index > -1) {
                allDuctsShowRealPoint.splice(index, 1);
            }

            var index = allDuctsShowSeq.indexOf(selectedDuctContext);

            if (index > -1) {
                allDuctsShowSeq.splice(index, 1);
            }

            $("#ductCheckIconSequence").hide();
            $("#ductCheckIconBox").hide();
            $("#ductCheckIconRealPoints").hide();
        }
        else {
            console.log("It does not have allChecked class, so we need to check");
            checkLabel = "checked";
            allcheckedLabels = [];

            allcheckedLabels = $(".checkboxSpan:checked")
                .map(function() {
                    return this.id;
                })
                .get();

            $('#auxiliary_ductTable tbody input[name="record"]').prop('checked', true);

            setTimeout(() => {
                showHideAllPoints(
                    selectedDuctContext,
                    "ductCheckSequence",
                    "Show"
                );
            }, 0);

            window['ductCheckPoints_' + selectedDuctContext] = "checked";
            $("#ductCheckIconBox").show();
            if (allDuctsShowPoint.includes(selectedDuctContext) == false) {
                allDuctsShowPoint.push(selectedDuctContext);
            }
        }

        $(this).toggleClass('allChecked');
        syncAllDuctBoq();
    });

    $("#delete_Duct_Aux").click(function() {

        console.time("Duct Delete Aux");

        const $table = $("#auxiliary_ductTable");
        const $checked = $table.find("input[name='record']:checked");
        const totalRows = $table.find("input[name='record']");

        if ($checked.length === 0) {
            alert("Select Row(s) to Delete");
            return;
        }

        const deletingAll =
            $checked.length === totalRows.length;

        const rowsData = [];

        $checked.each(function() {

            const $row = $(this).closest("tr");

            const auxName =
                $row.find('td[name="auxiliaryDuct_Name"] input').val();

            const seq =
                $row.find('td[name="DuctSeq"] input').val();

            rowsData.push({ row: $row, auxName, seq });
            removeAuxiliaryMarker(auxName, seq, selectedDuctContext);
        });

        if (deletingAll) {
            const tbody = $table.find("tbody")[0];
            tbody.innerHTML = "";
        } else {

            rowsData.forEach(r => r.row.remove());
        }

        if (!deletingAll) {
            $table.find("input[name='record']").each(function() {
                const $row = $(this).closest("tr");
                $row.find('input[name="DuctSeq"]')
                    .val($row.index() + 1);
            });
        }

        calculateDistanceSourceDestination(
            $("#SourceDuctLat").val(),
            $("#SourceDuctLng").val(),
            $("#DestinationDuctLat").val(),
            $("#DestinationDuctLng").val(),
            "auxiliary_ductTable"
        );
    });

    /*
        $("#delete_Duct_Aux").click(function() {
            console.time("Duct Delete Aux");
            console.log("actionDuctContext is " +actionDuctContext);
            slctDel = [];
            $("#auxiliary_ductTable > tbody").find('input[name="record"]').each(function() {
                if ($(this).is(":checked")) {
                    slctDel.push($(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val());
                    if (actionDuctContext == "Update") {
                        console.log("After if statement, the actionDuctContext is " +actionDuctContext);
                        //Hide the deleted point from Map
                        if ($(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().includes("Auxiliary_Point") == true) {
                            var AuxPtId = $(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0];
                            if (siteCltSrcMarkers[AuxPtId]) {
                                siteCltSrcMarkers[AuxPtId].setMap(null);
                            }
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val() == "null") {
                            var AuxPtId = "null".concat("_" + $(this).parent().parent().children('td[name="DuctSeq"]').children('input').val() + "_" + selectedDuctContext);
                            if (siteCltSrcMarkers[AuxPtId]) {
                                siteCltSrcMarkers[AuxPtId].setMap(null);
                            }
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split("_")[0] == "WARE") {
    
                            if (siteCltSrcMarkers[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]]) {
                                siteCltSrcMarkers[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]].setMap(null);
                            }
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split("_")[0] == "MH") {
                            markersManhole[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]].setMap(null);
                            markerClusterManhole.removeMarker(markersManhole[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]]);
                            $("#" + $(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split("_")[0] == "HH") {
                            markersHandhole[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]].setMap(null);
                            markerClusterHandhole.removeMarker(markersHandhole[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]]);
                            $("#" + $(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split("_")[0] == "DB") {
                            markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]].setMap(null);
                            Id = $(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0];
                            if (window["" + Id][8] == "backbone") {
                                markerClusterBackboneDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]]);
                            }
                            else if (window["" + Id][8] == "metro") {
                                markerClusterMetroDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]]);
    
                            }
                            else if (window["" + Id][8] == "access") {
                                markerClusterAccessDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]]);
                            }
    
                            $("#" + $(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                    }
                    $(this).parents("tr").remove();
    
                }
            });
    
            for (i = 0;i <= slctDel.length;++i) {
                if (slctDel.length == 0) {
                    alert(' Select Row(s) to Delete');
                    return false;
                }
            }
        	
            console.time("Start Sorting Sequence");
        	
            $("#auxiliary_ductTable").find('input[name="record"]').each(function() {
                var rowIndex = $(this).closest('tr');
                var currentIndex = rowIndex.index();
                $(this).parents("tr").find('input[name ="DuctSeq"]').val(currentIndex + 1);
            });
        	
            console.timeEnd("End Sorting Sequence");
        	
            console.time("Calculating Distances");
            calculateDistanceSourceDestination($("#SourceDuctLat").val(), $("#SourceDuctLng").val(), $("#DestinationDuctLat").val(), $("#DestinationDuctLng").val(), "auxiliary_ductTable");
            console.timeEnd("End Calculating Distances");
            console.timeEnd("End Duct Delete Aux");
        });
    
    */
    /*
    |------------------------------------------------------------
    | AUX NAME CHANGE
    |------------------------------------------------------------
    */
    $(document).on(
        "change",
        "#auxiliary_ductTable td[name='auxiliaryDuct_Name'] input",
        function() {

            const prevVal = $(this).data("val");
            const currentVal = $(this).val();

            if (prevVal === currentVal) return;

            const seq = $(this)
                .closest("tr")
                .find("td[name='DuctSeq'] input")
                .val();

            removeDuctMarkerByAuxName(
                prevVal,
                seq,
                selectedDuctContext
            );

            syncAllDuctBoq();
        }
    );


    /*
    |------------------------------------------------------------
    | CHECKBOX HANDLING
    |------------------------------------------------------------
    */
    $(document).on(
        "change",
        "#auxiliary_ductTable input[name='record']",
        function() {

            const $row = $(this).closest("tr");

            const lat = parseFloat(
                $row.find("td[name='auxiliary_LatitudeDuct'] input").val()
            );

            const lng = parseFloat(
                $row.find("td[name='auxiliary_LongitudeDuct'] input").val()
            );

            const name = $row
                .find("td[name='auxiliaryDuct_Name'] input")
                .val();

            const seq = $row
                .find("td[name='DuctSeq'] input")
                .val();

            const isChecked = $(this).is(":checked");

            console.log("Duct Row Checked:", isChecked, name);

            if (isNaN(lat) || isNaN(lng)) return;


            /*
            |------------------------------------------------------------
            | RESET STATE
            |------------------------------------------------------------
            */
            if (isChecked) {

                if (
                    $(".rowAboveBelowDuct:checked").length !== 0 &&
                    (
                        window['ductCheckPoints_' + selectedDuctContext] === "checked" ||
                        window['ductCheckRealPoints_' + selectedDuctContext] === "checked"
                    )
                ) {

                    showHideAllPoints(
                        selectedDuctContext,
                        "ductCheckSequence",
                        "Hide"
                    );

                    window['ductCheckPoints_' + selectedDuctContext] = "unchecked";
                    window['ductCheckRealPoints_' + selectedDuctContext] = "unchecked";
                    window['ductCheckSequence_' + selectedDuctContext] = "unchecked";

                    allDuctsShowPoint =
                        (allDuctsShowPoint || []).filter(
                            x => x !== selectedDuctContext
                        );

                    allDuctsShowRealPoint =
                        (allDuctsShowRealPoint || []).filter(
                            x => x !== selectedDuctContext
                        );
                }
            }


            /*
            |------------------------------------------------------------
            | UNCHECK
            |------------------------------------------------------------
            */
            if (!isChecked) {

                removeDuctMarkerByAuxName(
                    name,
                    seq,
                    selectedDuctContext
                );

                syncAllDuctBoq();

                checkedPoints =
                    (checkedPoints || []).filter(
                        p => !(p.lat === lat && p.lng === lng)
                    );

                return;
            }


            /*
            |------------------------------------------------------------
            | MAP BOUNDS
            |------------------------------------------------------------
            */
            checkedPoints = checkedPoints || [];

            checkedPoints.push({ lat, lng });

            if ($(".rowAboveBelowDuct:checked").length === 1) {

                map.setZoom(15);

                panTo(lat, lng);
            }
            else {

                checkedPoints.sort(
                    (a, b) => a.lat - b.lat || a.lng - b.lng
                );

                const bounds = new google.maps.LatLngBounds();

                bounds.extend(
                    new google.maps.LatLng(
                        checkedPoints[0].lat,
                        checkedPoints[0].lng
                    )
                );

                const last = checkedPoints[checkedPoints.length - 1];

                bounds.extend(
                    new google.maps.LatLng(
                        last.lat,
                        last.lng
                    )
                );

                window["checkedDuctPoints_" + selectedDuctContext] = bounds;

                map.fitBounds(bounds);
            }


            /*
            |------------------------------------------------------------
            | CREATE MARKER
            |------------------------------------------------------------
            */
            createOrUpdateDuctMarker(
                name,
                seq,
                lat,
                lng
            );
        }
    );
}



function removeDuctMarkerByAuxName(prevVal, seq, contextId) {

    if (!prevVal) return;

    let id = null;
    let type = null;

    if (prevVal === "null") {

        id = "null_" + seq + "_" + contextId;

        type = "site";
    }
    else if (prevVal.includes("Auxiliary_Point")) {

        id = prevVal.split(":")[0];

        type = "site";
    }
    else if (prevVal.split("_")[0] === "WARE") {

        id = prevVal.split(":")[0];

        type = "site";
    }
    else {

        id = prevVal.split(":")[0];

        type = prevVal.split("_")[0];
    }

    if (!id) return;


    /*
    |------------------------------------------------------------
    | SITE
    |------------------------------------------------------------
    */
    if (type === "site") {

        if (siteCltSrcMarkers[id]) {

            siteCltSrcMarkers[id].setMap(null);

            siteCltSrcMarkers[id].setLabel?.(null);
        }

        return;
    }


    /*
    |------------------------------------------------------------
    | MH
    |------------------------------------------------------------
    */
    if (type === "MH") {

        const m = markersManhole[id];

        if (m) {

            markerClusterManhole.removeMarker(m);

            m.setMap(null);

            m.setLabel?.(null);

            markerClusterManhole.repaint?.();

            $("#" + id)
                .children(":checkbox")
                .prop("checked", false);
        }

        return;
    }


    /*
    |------------------------------------------------------------
    | HH
    |------------------------------------------------------------
    */
    if (type === "HH") {

        const m = markersHandhole[id];

        if (m) {

            markerClusterHandhole.removeMarker(m);

            m.setMap(null);

            m.setLabel?.(null);

            markerClusterHandhole.repaint?.();

            $("#" + id)
                .children(":checkbox")
                .prop("checked", false);
        }

        return;
    }


    /*
    |------------------------------------------------------------
    | DB
    |------------------------------------------------------------
    */
    if (type === "DB") {

        const m = markersDistBoard[id];

        if (!m) return;

        let cluster = markerClusterAccessDistBoard;

        const dbInfo = window[id] || [];

        if (dbInfo[8] === "backbone") {

            cluster = markerClusterBackboneDistBoard;
        }
        else if (dbInfo[8] === "metro") {

            cluster = markerClusterMetroDistBoard;
        }

        cluster.removeMarker(m);

        m.setMap(null);

        m.setLabel?.(null);

        cluster.repaint?.();

        $("#" + id)
            .children(":checkbox")
            .prop("checked", false);
    }
}



function createOrUpdateDuctMarker(name, seq, lat, lng) {

    if (!name) return;

    const auxNameVal = name;


    /*
    |------------------------------------------------------------
    | NULL
    |------------------------------------------------------------
    */
    if (auxNameVal === "null") {

        const AuxId =
            "null_" + seq + "_" + selectedDuctContext;

        if (!siteCltSrcMarkers[AuxId]) {

            createSiteCltMarker(
                AuxId,
                AuxId,
                lat,
                lng,
                siteCltSrcMarkers
            );
        }

        siteCltSrcMarkers[AuxId].setLabel({
            text: auxNameVal,
            className: "marker-position-ware",
            color: "green"
        });
    }


    /*
    |------------------------------------------------------------
    | AUXILIARY POINT
    |------------------------------------------------------------
    */
    else if (auxNameVal.includes("Auxiliary_Point")) {

        const wareID = auxNameVal.split(":")[0];

        if (!siteCltSrcMarkers[wareID]) {

            createSiteCltMarker(
                wareID,
                wareID,
                lat,
                lng,
                siteCltSrcMarkers
            );
        }

        siteCltSrcMarkers[wareID].setLabel({
            text: auxNameVal.split(":")[1],
            className: "marker-position-ware",
            color: "green"
        });
    }


    /*
    |------------------------------------------------------------
    | WARE
    |------------------------------------------------------------
    */
    else if (auxNameVal.split("_")[0] === "WARE") {

        const wareID = auxNameVal.split(":")[0];

        if (!siteCltSrcMarkers[wareID]) {

            createSiteCltMarker(
                wareID,
                wareID,
                lat,
                lng,
                siteCltSrcMarkers
            );
        }

        siteCltSrcMarkers[wareID].setLabel({
            text:
                auxNameVal.split(":")[0] +
                " : " +
                auxNameVal.split(":")[2],

            className: "marker-position-ware",
            color: "red"
        });
    }


    /*
    |------------------------------------------------------------
    | MH
    |------------------------------------------------------------
    */
    else if (auxNameVal.split("_")[0] === "MH") {

        const id = auxNameVal.split(":")[0];

        if (markersManhole[id]) {

            markerClusterManhole.removeMarker(markersManhole[id]);

            markersManhole[id].setMap(map);

            markerClusterManhole.addMarker(markersManhole[id]);

            $("#" + id)
                .children(":checkbox")
                .prop("checked", true);

            $("#manholeCheckAllBoq").prop("checked", true);

            markersManhole[id].setLabel({
                text: auxNameVal.split(":")[1],
                className: "marker-position-manhole",
                color: "red"
            });
        }
    }


    /*
    |------------------------------------------------------------
    | HH
    |------------------------------------------------------------
    */
    else if (auxNameVal.split("_")[0] === "HH") {

        const id = auxNameVal.split(":")[0];

        if (markersHandhole[id]) {

            markerClusterHandhole.removeMarker(markersHandhole[id]);

            markersHandhole[id].setMap(map);

            markerClusterHandhole.addMarker(markersHandhole[id]);

            $("#" + id)
                .children(":checkbox")
                .prop("checked", true);

            $("#handholeCheckAllBoq").prop("checked", true);

            markersHandhole[id].setLabel({
                text: auxNameVal.split(":")[1],
                className: "marker-position-handhole",
                color: "#E5C523"
            });
        }
    }


    /*
    |------------------------------------------------------------
    | DB
    |------------------------------------------------------------
    */
    else if (auxNameVal.split("_")[0] === "DB") {

        const id = auxNameVal.split(":")[0];

        const dbInfo = window[id] || [];

        let cluster = markerClusterAccessDistBoard;

        if (dbInfo[8] === "backbone") {

            cluster = markerClusterBackboneDistBoard;
        }
        else if (dbInfo[8] === "metro") {

            cluster = markerClusterMetroDistBoard;
        }

        if (markersDistBoard[id]) {

            cluster.removeMarker(markersDistBoard[id]);

            markersDistBoard[id].setMap(map);

            cluster.addMarker(markersDistBoard[id]);

            $("#" + id)
                .children(":checkbox")
                .prop("checked", true);

            $("#distBoardCheckAllBoq").prop("checked", true);

            markersDistBoard[id].setLabel({
                text: auxNameVal.split(":")[1],
                className: "marker-position-dB",
                color: "#5665F9"
            });
        }
    }

    syncAllDuctBoq();
}



function syncAllDuctBoq() {

    if (
        $("#Manhole_f_CurrentPhysicalLayer")
            .find(".Manhole:checked")
            .length === 0
    ) {

        $("#manholeCheckAllBoq").prop("checked", false);
    }
    else {

        $("#manholeCheckAllBoq").prop("checked", true);
    }


    if (
        $("#Handhole_f_CurrentPhysicalLayer")
            .find(".Handhole:checked")
            .length === 0
    ) {

        $("#handholeCheckAllBoq").prop("checked", false);
    }
    else {

        $("#handholeCheckAllBoq").prop("checked", true);
    }


    if (
        $("#DistributionBoard_f_CurrentPhysicalLayer")
            .find(".DistBoard:checked")
            .length === 0
    ) {

        $("#distBoardCheckAllBoq").prop("checked", false);
    }
    else {

        $("#distBoardCheckAllBoq").prop("checked", true);
    }
}



function ductAuxTab() {

    console.log("Hello, duct aux clicked.");

    const ductAuxFlag =
        document.querySelector("#ductAuxFlag").value;

    uncheckAutoCompleteCheckboxes("auxPtDuctAutocomplete");

    if (!(actionDuctContext === "Update" &&
        ductAuxFlag === "not_opened")) {

        return;
    }

    document.querySelector("#ductAuxFlag").value = "opened";

    ductAuxData = [];

    checkedPoints = [];

    console.log(
        "Just before sending ajax for method: findDuctDetails"
    );

    $.ajax({
        type: "GET",

        url: getContext() + "/findDuctDetails",

        data: {
            ID: selectedDuctContext
        },

        beforeSend: function() {
            $("#loaderDivDuct").show();
        },

        success: function(data) {

            $("#loaderDivDuct").hide();

            indexduct = 0;

            console.log("Success of the ajax");

            if (data && data.auxData) {

                AuxAppendBOQ(
                    data.auxData,
                    "",
                    "",
                    TargetDuct,
                    indexduct
                );

                ductAuxData = data.auxData;
            }

            const objDiv =
                document.getElementById("auxiliary_ductTable");

            objDiv.scrollTop = objDiv.scrollHeight;

            data = null;
        },

        error: function() {

            alert("Error");
        }
    });
}