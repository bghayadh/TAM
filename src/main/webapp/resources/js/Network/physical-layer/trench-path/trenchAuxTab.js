function trenchAuxTabEvents() {

    $("#trench_aux_tab").on("click", function() {
        trenchAuxTab();
    });

    /*
    |------------------------------------------------------------
    | Track previous AUX name value
    |------------------------------------------------------------
    */
    $(document).on("focusin", "#auxiliary_trenchTable td[name='auxiliary_Name'] input", function() {
        $(this).data("val", $(this).val());
    });


    /*
    |------------------------------------------------------------
    | SELECT ALL
    |------------------------------------------------------------
    */

    /*	
        $(document).on('click', '#selectAllTrench_Aux', function() {
    
            if ($(this).hasClass('allChecked')) {
    
                $('input[type="checkbox"]', '#auxiliary_trenchTable').prop('checked', false);
    
                showHideAllPoints(selectedTrenchContext, "trenchCheckSequence", "Hide");
    
                window['trenchCheckPoints_' + selectedTrenchContext] = "unchecked";
                window['trenchCheckRealPoints_' + selectedTrenchContext] = "unchecked";
                window['trenchCheckSequence_' + selectedTrenchContext] = "unchecked";
    
                var index = allTrenchsShowPoint.indexOf(selectedTrenchContext);
                if (index > -1) {
                    allTrenchsShowPoint.splice(index, 1);
                }
    
                var index = allTrenchsShowRealPoint.indexOf(selectedTrenchContext);
                if (index > -1) {
                    allTrenchsShowRealPoint.splice(index, 1);
                }
    
                var index = allTrenchsShowSeq.indexOf(selectedTrenchContext);
                if (index > -1) {
                    allTrenchsShowSeq.splice(index, 1);
                }
    
                $("#trenchCheckIconSequence").hide();
                $("#trenchCheckIconBox").hide();
                $("#trenchCheckIconRealPoints").hide();
    
            }
            else {
    
                checkLabel = "checked";
    
                allcheckedLabels = [];
    
                if ($(".checkboxSpan:checked").length > 0) {
                    $(".checkboxSpan").each(function() {
                        if ($(this).is(":checked")) {
                            var id = $(this).attr('id');
                            allcheckedLabels.push(id);
                        }
                    });
                }
    
                $('input[type="checkbox"]', '#auxiliary_trenchTable').prop('checked', true);
                showHideAllPoints(selectedTrenchContext, "trenchCheckSequence", "Show");
                window['trenchCheckPoints_' + selectedTrenchContext] = "checked";
                $("#trenchCheckIconBox").show();
    
                if (allTrenchsShowPoint.includes(selectedTrenchContext) == false) {
                    allTrenchsShowPoint.push(selectedTrenchContext);
                }
            }
    
            $(this).toggleClass('allChecked');
    
            syncAllTrenchBoq();
        });
    */

    $(document).on('click', '#selectAllTrench_Aux', function() {

        if ($(this).hasClass('allChecked')) {

            $('#auxiliary_trenchTable tbody input[name="record"]').prop('checked', false);

            setTimeout(() => {
                showHideAllPoints(
                    selectedTrenchContext,
                    "trenchCheckSequence",
                    "Hide"
                );
            }, 0);

            window['trenchCheckPoints_' + selectedTrenchContext] = "unchecked";
            window['trenchCheckRealPoints_' + selectedTrenchContext] = "unchecked";
            window['trenchCheckSequence_' + selectedTrenchContext] = "unchecked";

            allTrenchsShowPoint = allTrenchsShowPoint.filter(x => x !== selectedTrenchContext);
            allTrenchsShowRealPoint = allTrenchsShowRealPoint.filter(x => x !== selectedTrenchContext);
            allTrenchsShowSeq = allTrenchsShowSeq.filter(x => x !== selectedTrenchContext);

            $("#trenchCheckIconSequence").hide();
            $("#trenchCheckIconBox").hide();
            $("#trenchCheckIconRealPoints").hide();

        } else {

            checkLabel = "checked";

            allcheckedLabels = $("#auxiliary_trenchTable tbody input[name='record']:checked")
                .map(function() {
                    return this.id;
                })
                .get();

            $('#auxiliary_trenchTable tbody input[name="record"]').prop('checked', true);

            setTimeout(() => {
                showHideAllPoints(
                    selectedTrenchContext,
                    "trenchCheckSequence",
                    "Show"
                );
            }, 0);

            window['trenchCheckPoints_' + selectedTrenchContext] = "checked";

            $("#trenchCheckIconBox").show();

            if (!allTrenchsShowPoint.includes(selectedTrenchContext)) {
                allTrenchsShowPoint.push(selectedTrenchContext);
            }
        }

        $(this).toggleClass('allChecked');
        syncAllTrenchBoq();
    });

    /*	
        $("#delete_Trench_Aux").click(function() {
            slctDel = [];
            $("#auxiliary_trenchTable > tbody").find('input[name="record"]').each(function() {
                if ($(this).is(":checked")) {
                    slctDel.push($(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val());
                    if (actionTrenchContext == "Update") {
    
                        //Hide the deleted point from Map
                        if ($(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().includes("Auxiliary_Point") == true) {
                            var AuxPtId = $(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0];
                            if (siteCltSrcMarkers[AuxPtId]) {
                                siteCltSrcMarkers[AuxPtId].setMap(null);
                            }
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val() == "null") {
                            var AuxPtId = "null".concat("_" + $(this).parent().parent().children('td[name="TrenchSeq"]').children('input').val() + "_" + selectedTrenchContext);
                            if (siteCltSrcMarkers[AuxPtId]) {
                                siteCltSrcMarkers[AuxPtId].setMap(null);
                            }
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split("_")[0] == "WARE") {
    
                            if (siteCltSrcMarkers[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]]) {
                                siteCltSrcMarkers[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]].setMap(null);
                            }
    
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split("_")[0] == "MH") {
                            markersManhole[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]].setMap(null);
                            markerClusterManhole.removeMarker(markersManhole[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]]);
                            $("#" + $(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split("_")[0] == "HH") {
                            markersHandhole[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]].setMap(null);
                            markerClusterHandhole.removeMarker(markersHandhole[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]]);
                            $("#" + $(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split("_")[0] == "DB") {
                            markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]].setMap(null);
                            Id = $(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0];
                            if (window["" + Id][8] == "backbone") {
                                markerClusterBackboneDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]]);
                            }
                            else if (window["" + Id][8] == "metro") {
                                markerClusterMetroDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]]);
                            }
                            else if (window["" + Id][8] == "access") {
                                markerClusterAccessDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]]);
                            }
    
                            $("#" + $(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
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
    
            $("#auxiliary_trenchTable").find('input[name="record"]').each(function() {
                var rowIndex = $(this).closest('tr');
                var currentIndex = rowIndex.index();
    
                $(this).parents("tr").find('input[name ="TrenchSeq"]').val(currentIndex + 1);
            });
    
            calculateDistanceSourceDestination($("#SourceTrenchLat").val(), $("#SourceTrenchLng").val(), $("#DestinationTrenchLat").val(), $("#DestinationTrenchLng").val(), "auxiliary_trenchTable");
    
        });
    
    */

    $("#delete_Trench_Aux").click(function() {

        const $table = $("#auxiliary_trenchTable");
        const $checked = $table.find("input[name='record']:checked");

        if ($checked.length === 0) {
            alert("Select Row(s) to Delete");
            return;
        }

        const slctDel = [];

        $checked.each(function() {

            const $row = $(this).closest("tr");

            const auxName = $row.find('td[name="auxiliaryTrench_Name"] input').val();
            const seq = $row.find('td[name="TrenchSeq"] input').val();

            slctDel.push(auxName);

            removeAuxiliaryMarker(auxName, seq, selectedTrenchContext);

            $row.remove();
        });

        if (slctDel.length === 0) {
            alert("Select Row(s) to Delete");
            return;
        }

        $table.find("input[name='record']").each(function() {
            const $row = $(this).closest("tr");
            $row.find('input[name="TrenchSeq"]').val($row.index() + 1);
        });

        calculateDistanceSourceDestination(
            $("#SourceTrenchLat").val(),
            $("#SourceTrenchLng").val(),
            $("#DestinationTrenchLat").val(),
            $("#DestinationTrenchLng").val(),
            "auxiliary_trenchTable"
        );
    });


    /*
    |------------------------------------------------------------
    | AUX NAME CHANGE
    |------------------------------------------------------------
    */
    $(document).on(
        "change",
        "#auxiliary_trenchTable td[name='auxiliary_Name'] input",
        function() {

            const prevVal = $(this).data("val");
            const currentVal = $(this).val();

            if (prevVal === currentVal) return;

            const seq = $(this)
                .closest("tr")
                .find("td[name='TrenchSeq'] input")
                .val();

            removeTrenchMarkerByAuxName(
                prevVal,
                seq,
                selectedTrenchContext
            );

            syncAllTrenchBoq();
        }
    );


    /*
    |------------------------------------------------------------
    | CHECKBOX HANDLING
    |------------------------------------------------------------
    */
    $(document).on(
        "change",
        "#auxiliary_trenchTable input[name='record']",
        function() {

            const $row = $(this).closest("tr");

            const lat = parseFloat(
                $row.find("td[name='auxiliaryTrench_Latitude'] input").val()
            );

            const lng = parseFloat(
                $row.find("td[name='auxiliaryTrench_Longitude'] input").val()
            );

            const name = $row
                .find("td[name='auxiliaryTrench_Name'] input")
                .val();

            const seq = $row
                .find("td[name='TrenchSeq'] input")
                .val();

            const isChecked = $(this).is(":checked");

            console.log("Trench Row Checked:", isChecked, name);

            if (isNaN(lat) || isNaN(lng)) return;


            /*
            |------------------------------------------------------------
            | RESET STATE
            |------------------------------------------------------------
            */
            if (isChecked) {

                if (
                    $(".rowAboveBelowTrench:checked").length !== 0 &&
                    (
                        window['trenchCheckPoints_' + selectedTrenchContext] === "checked" ||
                        window['trenchCheckRealPoints_' + selectedTrenchContext] === "checked"
                    )
                ) {

                    showHideAllPoints(
                        selectedTrenchContext,
                        "trenchCheckSequence",
                        "Hide"
                    );

                    window['trenchCheckPoints_' + selectedTrenchContext] = "unchecked";
                    window['trenchCheckRealPoints_' + selectedTrenchContext] = "unchecked";
                    window['trenchCheckSequence_' + selectedTrenchContext] = "unchecked";

                    allTrenchsShowPoint =
                        (allTrenchsShowPoint || []).filter(
                            x => x !== selectedTrenchContext
                        );

                    allTrenchsShowRealPoint =
                        (allTrenchsShowRealPoint || []).filter(
                            x => x !== selectedTrenchContext
                        );
                }
            }


            /*
            |------------------------------------------------------------
            | UNCHECK
            |------------------------------------------------------------
            */
            if (!isChecked) {

                removeTrenchMarkerByAuxName(
                    name,
                    seq,
                    selectedTrenchContext
                );

                syncAllTrenchBoq();

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

            if ($(".rowAboveBelowTrench:checked").length === 1) {
                map.setZoom(15);
                panTo(lat, lng);

            } else {
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
                    new google.maps.LatLng(last.lat, last.lng)
                );

                window["checkedTrenchPoints_" + selectedTrenchContext] = bounds;

                map.fitBounds(bounds);
            }


            /*
            |------------------------------------------------------------
            | CREATE MARKER
            |------------------------------------------------------------
            */
            createOrUpdateTrenchMarker(
                name,
                seq,
                lat,
                lng
            );
        }
    );
}



function removeTrenchMarkerByAuxName(prevVal, seq, contextId) {

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



function createOrUpdateTrenchMarker(name, seq, lat, lng) {

    if (!name) return;

    const auxNameVal = name;


    /*
    |------------------------------------------------------------
    | NULL
    |------------------------------------------------------------
    */
    if (auxNameVal === "null") {

        const AuxId =
            "null_" + seq + "_" + selectedTrenchContext;

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
    syncAllTrenchBoq();
}



function syncAllTrenchBoq() {

    if (
        $("#Manhole_f_CurrentPhysicalLayer")
            .find(".Manhole:checked")
            .length === 0
    ) {

        $("#manholeCheckAllBoq").prop("checked", false);

    } else {

        $("#manholeCheckAllBoq").prop("checked", true);
    }


    if (
        $("#Handhole_f_CurrentPhysicalLayer")
            .find(".Handhole:checked")
            .length === 0
    ) {

        $("#handholeCheckAllBoq").prop("checked", false);

    } else {

        $("#handholeCheckAllBoq").prop("checked", true);
    }


    if (
        $("#DistributionBoard_f_CurrentPhysicalLayer")
            .find(".DistBoard:checked")
            .length === 0
    ) {

        $("#distBoardCheckAllBoq").prop("checked", false);

    } else {

        $("#distBoardCheckAllBoq").prop("checked", true);
    }
}



function trenchAuxTab() {

    console.log("Hello, trench aux clicked.");
    const trenchAuxFlag = document.querySelector("#trenchAuxFlag").value;
    uncheckAutoCompleteCheckboxes("auxPtAutocomplete");

    if (!(actionTrenchContext === "Update" &&
        trenchAuxFlag === "not_opened")) {
        return;
    }

    document.querySelector("#trenchAuxFlag").value = "opened";
    trenchAuxData = [];
    checkedPoints = [];
    console.log("Just before sending ajax for method: findTrenchAuxDetails");

    $.ajax({
        type: "GET",
        url: getContext() + "/findTrenchDetails",
        data: {
            ID: selectedTrenchContext
        },
        beforeSend: function() {
            $("#loaderDivTrench").show();
        },
        success: function(data) {
            $("#loaderDivTrench").hide();
            indextrench = 0;
            console.log("Success of the ajax");
            if (data && data.auxData) {
                AuxAppendBOQ(
                    data.auxData,
                    "",
                    "",
                    TargetTrench,
                    indextrench
                );
                trenchAuxData = data.auxData;
            }
            const objDiv = document.getElementById("auxiliary_trenchTable");
            objDiv.scrollTop = objDiv.scrollHeight;
            calculateDistanceSourceDestination(
                $("#SourceTrenchLat").val(),
                $("#SourceTrenchLng").val(),
                $("#DestinationTrenchLat").val(),
                $("#DestinationTrenchLng").val(),
                "auxiliary_trenchTable"
            );
        },

        error: function() {
            alert("Error");
        }
    });
}