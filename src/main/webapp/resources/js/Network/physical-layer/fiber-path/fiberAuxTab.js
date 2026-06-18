function fiberAuxTabEvents() {

    $("#fiber_aux_tab").on("click", function() {
        fiberAuxTab();
    });

    /*
    |------------------------------------------------------------
    | Track previous AUX name value
    |------------------------------------------------------------
    */
    $(document).on("focusin", "td[name='auxiliary_Name'] input", function() {
        $(this).data("val", $(this).val());
    });

    $(document).on('click', '#selectAll_Aux', function() {

        console.log("Hello, selectAllAux (fiber)");

        if ($(this).hasClass('allChecked')) {

            $('#auxiliaryTable tbody input[name="record"]').prop('checked', false);

            setTimeout(() => {
                showHideAllPoints(
                    selectedFiberContext,
                    "fiberCheckSequence",
                    "Hide"
                );
            }, 0);

            window['fiberCheckPoints_' + selectedFiberContext] = "unchecked";
            window['fiberCheckRealPoints_' + selectedFiberContext] = "unchecked";
            window['fiberCheckSequence_' + selectedFiberContext] = "unchecked";

            allCablesShowPoint = allCablesShowPoint.filter(x => x !== selectedFiberContext);
            allCablesShowRealPoint = allCablesShowRealPoint.filter(x => x !== selectedFiberContext);
            allCablesShowSeq = allCablesShowSeq.filter(x => x !== selectedFiberContext);

            $("#fiberCheckIconBox").hide();
            $("#fiberCheckIconRealPoints").hide();
            $("#fiberCheckIconSequence").hide();

        } else {

            checkLabel = "checked";

            allcheckedLabels = $("#auxiliaryTable tbody input[name='record']:checked")
                .map(function() {
                    return this.id;
                })
                .get();

            $('#auxiliaryTable tbody input[name="record"]').prop('checked', true);

            setTimeout(() => {
                showHideAllPoints(
                    selectedFiberContext,
                    "fiberCheckSequence",
                    "Show"
                );
            }, 0);

            window['fiberCheckPoints_' + selectedFiberContext] = "checked";

            $("#fiberCheckIconBox").show();

            if (!allCablesShowPoint.includes(selectedFiberContext)) {
                allCablesShowPoint.push(selectedFiberContext);
            }
        }

        $(this).toggleClass('allChecked');
        syncAllBoq();
    });

    /*	
        $(document).on('click', '#selectAll_Aux', function() {
            console.log("Hello, this selectAllAux for fiber cable");
            if ($(this).hasClass('allChecked')) {
                $('input[type="checkbox"]', '#auxiliaryTable').prop('checked', false);
                showHideAllPoints(selectedFiberContext, "fiberCheckSequence", "Hide");
    
                window['fiberCheckPoints_' + selectedFiberContext] = "unchecked";
                window['fiberCheckRealPoints_' + selectedFiberContext] = "unchecked";
                window['fiberCheckSequence_' + selectedFiberContext] = "unchecked";
    
                //Remove the unchecked fiber points from array 
                var index = allCablesShowPoint.indexOf(selectedFiberContext);
                if (index > -1) {
                    allCablesShowPoint.splice(index, 1);
                }
    
                //Remove the unchecked fiber points from array 
                var index = allCablesShowRealPoint.indexOf(selectedFiberContext);
                if (index > -1) {
                    allCablesShowRealPoint.splice(index, 1);
                }
    
                //Remove the unchecked fiber points from array 
                var index = allCablesShowSeq.indexOf(selectedFiberContext);
                if (index > -1) {
                    allCablesShowSeq.splice(index, 1);
                }
    
                $("#fiberCheckIconBox").hide();
                $("#fiberCheckIconRealPoints").hide();
                $("#fiberCheckIconSequence").hide();
    
            }
            else {
                checkLabel = "checked";
                //Used to check if the labels in dropdown are checked 
                allcheckedLabels = [];
                if ($(".checkboxSpan:checked").length > 0) {
                    $(".checkboxSpan").each(function() {
                        if ($(this).is(":checked")) {
                            var id = $(this).attr('id');
                            allcheckedLabels.push(id);
                        }
                    });
                }
                $('input[type="checkbox"]', '#auxiliaryTable').prop('checked', true);
                showHideAllPoints(selectedFiberContext, "fiberCheckSequence", "Show");
                window['fiberCheckPoints_' + selectedFiberContext] = "checked";
                $("#fiberCheckIconBox").show();
    
                if (allCablesShowPoint.includes(selectedFiberContext) == false) {
                    allCablesShowPoint.push(selectedFiberContext);
                }
            }
            $(this).toggleClass('allChecked');
            syncAllBoq();
        });
    
    	
    */

    $("#delete_Aux").click(function() {

        const $table = $("#auxiliaryTable");
        const $checked = $table.find("input[name='record']:checked");

        if ($checked.length === 0) {
            alert("Select Row(s) to Delete");
            return;
        }

        const slctDel = [];

        $checked.each(function() {

            const $row = $(this).closest("tr");

            const auxName = $row.find('td[name="auxiliary_Name"] input').val();
            const seq = $row.find('td[name="fiberSeq"] input').val();

            slctDel.push(auxName);

            // SAFE marker removal
            removeAuxiliaryMarker(auxName, seq, selectedFiberContext);

            $row.remove();
        });

        if (slctDel.length === 0) {
            alert("Select Row(s) to Delete");
            return;
        }

        // resequence
        $table.find("input[name='record']").each(function() {
            const $row = $(this).closest("tr");
            $row.find('input[name="fiberCableSeq"]').val($row.index() + 1);
        });

        calculateDistanceSourceDestination(
            $("#SourceLat").val(),
            $("#SourceLng").val(),
            $("#DestinationLat").val(),
            $("#DestinationLng").val(),
            "auxiliaryTable"
        );
    });

    /*		
        $("#delete_Aux").click(function() {
            slctDel = [];
            //console.log(dict);
            $("#auxiliaryTable > tbody").find('input[name="record"]').each(function() {
                if ($(this).is(":checked")) {
                    slctDel.push($(this).parent().parent().children('td[name="aux_Long"]').children('input').val());
    
                    console.log("actionFiberContext " + actionFiberContext)
                    if (actionFiberContext == "Update") {
                        //Hide the deleted point from Map
                        if ($(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().includes("Auxiliary_Point") == true) {
                            var AuxPtId = $(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0];
                            if (siteCltSrcMarkers[AuxPtId]) {
                                siteCltSrcMarkers[AuxPtId].setMap(null);
                            }
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val() == "null") {
                            var AuxPtId = "null".concat("_" + $(this).parent().parent().children('td[name="fiberSeq"]').children('input').val() + "_" + selectedFiberContext);
                            if (siteCltSrcMarkers[AuxPtId]) {
                                siteCltSrcMarkers[AuxPtId].setMap(null);
                            }
                        }
                        //	else {
                        else if ($(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split("_")[0] == "WARE") {
    
                            if (siteCltSrcMarkers[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]]) {
                                siteCltSrcMarkers[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]].setMap(null);
                            }
    
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split("_")[0] == "MH") {
                            markersManhole[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]].setMap(null);
                            markerClusterManhole.removeMarker(markersManhole[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]]);
                            $("#" + $(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split("_")[0] == "HH") {
                            markersHandhole[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]].setMap(null);
                            markerClusterHandhole.removeMarker(markersHandhole[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]]);
                            $("#" + $(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                        else if ($(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split("_")[0] == "DB") {
                            markersDistBoard[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]].setMap(null);
                            Id = $(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0];
                            if (window["" + Id][8] == "backbone") {
                                markerClusterBackboneDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]]);
                            }
                            else if (window["" + Id][8] == "metro") {
                                markerClusterMetroDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]]);
    
                            }
                            else if (window["" + Id][8] == "access") {
                                markerClusterAccessDistBoard.removeMarker(markersDistBoard[$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]]);
                            }
    
                            $("#" + $(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val().split(":")[0]).children(':checkbox').prop("checked", false);
                        }
                        //}
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
            //Change the sequence number of all rows after delete
            $("#auxiliaryTable").find('input[name="record"]').each(function() {
                var rowIndex = $(this).closest('tr');
                var currentIndex = rowIndex.index();
                $(this).parents("tr").find('input[name ="fiberCableSeq"]').val(currentIndex + 1);
            });
    
            calculateDistanceSourceDestination($("#SourceLat").val(), $("#SourceLng").val(), $("#DestinationLat").val(), $("#DestinationLng").val(), "auxiliaryTable");
    
        });
    */

    /*
    |------------------------------------------------------------
    | AUX NAME CHANGE → cleanup old marker
    |------------------------------------------------------------
    */

    $(document).on("change", "td[name='auxiliary_Name'] input", function() {

        const prevVal = $(this).data("val");
        const currentVal = $(this).val();

        if (prevVal === currentVal) return;

        const seq = $(this)
            .closest("tr")
            .find("td[name='fiberSeq'] input")
            .val();

        removeMarkerByAuxName(prevVal, seq, selectedFiberContext);
        syncAllBoq();
    });

    /*
    |------------------------------------------------------------
    | CHECKBOX HANDLING (FULL RESTORED + FIXED)
    |------------------------------------------------------------
    */
    $(document).on("change", "#auxiliaryTable input[name='record']", function() {

        const $row = $(this).closest("tr");

        const lat = parseFloat($row.find("td[name='auxiliary_Latitude'] input").val());
        const lng = parseFloat($row.find("td[name='auxiliary_Longitude'] input").val());

        const name = $row.find("td[name='auxiliary_Name'] input").val();
        const seq = $row.find("td[name='fiberSeq'] input").val();

        const isChecked = $(this).is(":checked");

        console.log("Row Checked:", isChecked, name);

        /*
        |------------------------------------------------------------
        | SAFETY: invalid coordinates guard
        |------------------------------------------------------------
        */
        if (isNaN(lat) || isNaN(lng)) return;

        /*
        |------------------------------------------------------------
        | GLOBAL STATE RESET (RESTORED ORIGINAL BEHAVIOR)
        |------------------------------------------------------------
        */
        if (isChecked) {

            if ($(".rowAboveBelow:checked").length !== 0 &&
                (window['fiberCheckPoints_' + selectedFiberContext] === "checked" ||
                    window['fiberCheckRealPoints_' + selectedFiberContext] === "checked")) {

                showHideAllPoints(selectedFiberContext, "fiberCheckSequence", "Hide");

                window['fiberCheckPoints_' + selectedFiberContext] = "unchecked";
                window['fiberCheckRealPoints_' + selectedFiberContext] = "unchecked";
                window['fiberCheckSequence_' + selectedFiberContext] = "unchecked";

                allCablesShowPoint = (allCablesShowPoint || []).filter(x => x !== selectedFiberContext);
                allCablesShowRealPoint = (allCablesShowRealPoint || []).filter(x => x !== selectedFiberContext);
            }
        }

        /*
        |------------------------------------------------------------
        | UNCHECK → REMOVE MARKER + CLEAN POINT STATE
        |------------------------------------------------------------
        */
        if (!isChecked) {

            removeMarkerByAuxName(name, seq, selectedFiberContext);
            syncAllBoq();

            checkedPoints = (checkedPoints || []).filter(p =>
                !(p.lat === lat && p.lng === lng)
            );

            return;
        }

        /*
        |------------------------------------------------------------
        | MAP ZOOM / BOUNDS LOGIC (UNCHANGED BUT SAFE)
        |------------------------------------------------------------
        */
        checkedPoints = checkedPoints || [];
        checkedPoints.push({ lat, lng });

        if ($(".rowAboveBelow:checked").length === 1) {
            map.setZoom(15);
            panTo(lat, lng);
        } else {

            checkedPoints.sort((a, b) => a.lat - b.lat || a.lng - b.lng);

            const bounds = new google.maps.LatLngBounds();

            bounds.extend(new google.maps.LatLng(checkedPoints[0].lat, checkedPoints[0].lng));

            const last = checkedPoints[checkedPoints.length - 1];
            bounds.extend(new google.maps.LatLng(last.lat, last.lng));

            window["checkedPoints_" + selectedFiberContext] = bounds;

            map.fitBounds(bounds);
        }

        /*
        |------------------------------------------------------------
        | MARKER CREATION (FULL ORIGINAL LOGIC RESTORED + SAFE)
        |------------------------------------------------------------
        */

        createOrUpdateMarker(name, seq, lat, lng);
    });
}

function removeMarkerByAuxName(prevVal, seq, contextId) {

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

    /* ---------------- SITE ---------------- */
    if (type === "site") {

        if (siteCltSrcMarkers[id]) {
            siteCltSrcMarkers[id].setMap(null);
            siteCltSrcMarkers[id].setLabel?.(null);
        }
        return;
    }

    /* ---------------- MH ---------------- */
    if (type === "MH") {

        const m = markersManhole[id];

        if (m) {
            markerClusterManhole.removeMarker(m);
            m.setMap(null);
            m.setLabel?.(null);
            markerClusterManhole.repaint?.();   // 🔥 FIX
            $("#" + id).children(":checkbox").prop("checked", false);
        }
        return;
    }

    /* ---------------- HH ---------------- */
    if (type === "HH") {

        const m = markersHandhole[id];

        if (m) {
            markerClusterHandhole.removeMarker(m);
            m.setMap(null);
            m.setLabel?.(null);
            markerClusterHandhole.repaint?.();   // 🔥 FIX
            $("#" + id).children(":checkbox").prop("checked", false);
        }
        return;
    }

    /* ---------------- DB ---------------- */
    if (type === "DB") {

        const m = markersDistBoard[id];

        if (!m) return;

        let cluster = markerClusterAccessDistBoard;

        const dbInfo = window[id] || [];

        if (dbInfo[8] === "backbone") cluster = markerClusterBackboneDistBoard;
        else if (dbInfo[8] === "metro") cluster = markerClusterMetroDistBoard;

        cluster.removeMarker(m);
        m.setMap(null);
        m.setLabel?.(null);


        cluster.repaint?.(); // 🔥 FIX
        $("#" + id).children(":checkbox").prop("checked", false);
    }
}

function createOrUpdateMarker(name, seq, lat, lng) {
    if (!name) return;
    const auxNameVal = name;

    /*
    |---------------------------
    | 1. NULL
    |---------------------------
    */
    if (auxNameVal === "null") {

        const AuxId = "null_" + seq + "_" + selectedFiberContext;

        if (!siteCltSrcMarkers[AuxId]) {
            createSiteCltMarker(AuxId, AuxId, lat, lng, siteCltSrcMarkers);
        }

        siteCltSrcMarkers[AuxId].setLabel({
            text: auxNameVal,
            className: "marker-position-ware",
            color: "green"
        });
    }

    /*
    |---------------------------
    | 2. AUXILIARY POINT
    |---------------------------
    */
    else if (auxNameVal.includes("Auxiliary_Point")) {

        const wareID = auxNameVal.split(":")[0];

        if (!siteCltSrcMarkers[wareID]) {
            createSiteCltMarker(wareID, wareID, lat, lng, siteCltSrcMarkers);
        }

        siteCltSrcMarkers[wareID].setLabel({
            text: auxNameVal.split(":")[1],
            className: "marker-position-ware",
            color: "green"
        });
    }

    /*
    |---------------------------
    | 3. WARE
    |---------------------------
    */
    else if (auxNameVal.split("_")[0] === "WARE") {

        const wareID = auxNameVal.split(":")[0];

        if (!siteCltSrcMarkers[wareID]) {
            createSiteCltMarker(wareID, wareID, lat, lng, siteCltSrcMarkers);
        }

        siteCltSrcMarkers[wareID].setLabel({
            text: auxNameVal.split(":")[0] + " : " + auxNameVal.split(":")[2],
            className: "marker-position-ware",
            color: "red"
        });
    }

    /*
    |---------------------------
    | 4. MH
    |---------------------------
    */
    else if (auxNameVal.split("_")[0] === "MH") {

        const id = auxNameVal.split(":")[0];

        if (markersManhole[id]) {

            markerClusterManhole.removeMarker(markersManhole[id]);
            markersManhole[id].setMap(map);
            markerClusterManhole.addMarker(markersManhole[id]);

            $("#" + id).children(":checkbox").prop("checked", true);
            $("#manholeCheckAllBoq").prop("checked", true);

            markersManhole[id].setLabel({
                text: auxNameVal.split(":")[1],
                className: "marker-position-manhole",
                color: "red"
            });
        }
    }

    /*
    |---------------------------
    | 5. HH
    |---------------------------
    */
    else if (auxNameVal.split("_")[0] === "HH") {

        const id = auxNameVal.split(":")[0];

        if (markersHandhole[id]) {

            markerClusterHandhole.removeMarker(markersHandhole[id]);
            markersHandhole[id].setMap(map);
            markerClusterHandhole.addMarker(markersHandhole[id]);

            $("#" + id).children(":checkbox").prop("checked", true);
            $("#handholeCheckAllBoq").prop("checked", true);

            markersHandhole[id].setLabel({
                text: auxNameVal.split(":")[1],
                className: "marker-position-handhole",
                color: "#E5C523"
            });
        }
    }

    /*
    |---------------------------
    | 6. DB (SAFE FIX)
    |---------------------------
    */
    else if (auxNameVal.split("_")[0] === "DB") {

        const id = auxNameVal.split(":")[0];

        const dbInfo = window[id] || [];

        let cluster = markerClusterAccessDistBoard;

        if (dbInfo[8] === "backbone") cluster = markerClusterBackboneDistBoard;
        else if (dbInfo[8] === "metro") cluster = markerClusterMetroDistBoard;

        if (markersDistBoard[id]) {

            cluster.removeMarker(markersDistBoard[id]);
            markersDistBoard[id].setMap(map);
            cluster.addMarker(markersDistBoard[id]);

            $("#" + id).children(":checkbox").prop("checked", true);
            $("#distBoardCheckAllBoq").prop("checked", true);

            markersDistBoard[id].setLabel({
                text: auxNameVal.split(":")[1],
                className: "marker-position-dB",
                color: "#5665F9"
            });
        }
    }

    syncAllBoq();
}

/*
|------------------------------------------------------------
| CHECK-ALL SYNC (UNCHANGED)
|------------------------------------------------------------
*/

function syncAllBoq() {
    console.log("SyncAllBoq");
    if ($("#Manhole_f_CurrentPhysicalLayer").find(".Manhole:checked").length === 0) {
        console.log("Lengh is 0 for MH in tree");
        $("#manholeCheckAllBoq").prop("checked", false);
    } else {
        console.log("The length is ", $("#Manhole_f_CurrentPhysicalLayer").find(".Manhole:checked").length);
        $("#manholeCheckAllBoq").prop("checked", true);
    }

    if ($("#Handhole_f_CurrentPhysicalLayer").find(".Handhole:checked").length === 0) {
        $("#handholeCheckAllBoq").prop("checked", false);
    } else {
        $("#handholeCheckAllBoq").prop("checked", true);
    }

    if ($("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked").length === 0) {
        $("#distBoardCheckAllBoq").prop("checked", false);
    } else {
        $("#distBoardCheckAllBoq").prop("checked", true);
    }
}

function fiberAuxTab() {

    const fiberAuxFlag = document.querySelector("#fiberAuxFlag").value;

    uncheckAutoCompleteCheckboxes("auxPtAutocomplete");

    if (!(actionFiberContext === "Update" && fiberAuxFlag === "not_opened")) {
        return;
    }

    document.querySelector("#fiberAuxFlag").value = "opened";

    fiberCableAuxData = [];
    checkedPoints = [];

    $.ajax({
        type: "GET",
        url: getContext() + "/findFiberAuxDetails",
        data: { ID: selectedFiberContext },

        beforeSend: function() {
            $("#loaderDiv").show();
        },

        success: function(data) {

            $("#loaderDiv").hide();

            index = 0;

            if (data && data.auxData) {
                AuxAppendBOQ(data.auxData, "", "", TargetFiber, index);
                fiberCableAuxData = data.auxData;
            }

            const objDiv = document.getElementById("auxiliaryTable");
            objDiv.scrollTop = objDiv.scrollHeight;

            calculateDistanceSourceDestination(
                $("#SourceLat").val(),
                $("#SourceLng").val(),
                $("#DestinationLat").val(),
                $("#DestinationLng").val(),
                "auxiliaryTable"
            );
            data = null;
        },

        error: function() {
            alert("Error");
        }
    });
}

function removeAuxiliaryMarker(auxName, seq, context) {

    if (!auxName) return;

    const prefix = auxName.split("_")[0];
    const id = auxName.includes(":")
        ? auxName.split(":")[0]
        : auxName;

    if (auxName.includes("Auxiliary_Point")) {

        if (siteCltSrcMarkers[id]) {
            siteCltSrcMarkers[id].setMap(null);
        }

    } else if (auxName === "null") {

        const auxPtId =
            "null_" + seq + "_" + context;

        if (siteCltSrcMarkers[auxPtId]) {
            siteCltSrcMarkers[auxPtId].setMap(null);
        }

    } else if (prefix === "WARE") {

        if (siteCltSrcMarkers[id]) {
            siteCltSrcMarkers[id].setMap(null);
        }

    } else if (prefix === "MH") {

        const marker = markersManhole[id];

        if (marker) {
            marker.setMap(null);
            markerClusterManhole.removeMarker(marker);
        }

        $("#" + id).children(":checkbox").prop("checked", false);

    } else if (prefix === "HH") {

        const marker = markersHandhole[id];

        if (marker) {
            marker.setMap(null);
            markerClusterHandhole.removeMarker(marker);
        }

        $("#" + id).children(":checkbox").prop("checked", false);

    } else if (prefix === "DB") {

        const marker = markersDistBoard[id];

        if (marker) {

            marker.setMap(null);

            const type = window[id] ? window[id][8] : null;

            if (type === "backbone") {
                markerClusterBackboneDistBoard.removeMarker(marker);
            } else if (type === "metro") {
                markerClusterMetroDistBoard.removeMarker(marker);
            } else if (type === "access") {
                markerClusterAccessDistBoard.removeMarker(marker);
            }
        }

        $("#" + id).children(":checkbox").prop("checked", false);
    }
}