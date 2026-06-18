function showHideAllPoints(pathID, checkSeqWindowID, action) {
    showHidePointsArray = [];
    if (window["mapPointsNames_" + pathID] != undefined) {
        if ((filterFlag == 2 || filterFlag == 1) && showPointsType == "0") {
            $('#Manhole_f_CurrentPhysicalLayer').find(' > ul > li ').each(function() {
                var manHandDbName = $(this).text().trim();
                if (manHandDbName && manHandDbName.includes("Junctions")) {
                    manHandDbName = manHandDbName.split("Junctions")[0].replaceAll(' ', '');
                }
                allTreePoints.push($(this).attr('id') + ":" + manHandDbName);
            });
            $('#Handhole_f_CurrentPhysicalLayer').find(' > ul > li ').each(function() {
                var manHandDbName = $(this).text().trim();
                if (manHandDbName && manHandDbName.includes("Junctions")) {
                    manHandDbName = manHandDbName.split("Junctions")[0].replaceAll(' ', '');
                }
                allTreePoints.push($(this).attr('id') + ":" + manHandDbName);
            });
            $('#DistributionBoard_f_CurrentPhysicalLayer').find(' > ul > li ').each(function() {
                allTreePoints.push($(this).attr('id') + ":" + $(this).text().trim());
            });

            window["mapPointsNamesTemp"] = [];
            for (var x = 0;x < window["mapPointsNames_" + pathID].length;x++) {
                if (window["mapPointsNames_" + pathID][x] && (window["mapPointsNames_" + pathID][x].includes("MH_") || window["mapPointsNames_" + pathID][x].includes("HH_")
                    || window["mapPointsNames_" + pathID][x].includes("DB_"))) {

                    if (allTreePoints.includes(window["mapPointsNames_" + pathID][x]) == true) {
                        window["mapPointsNamesTemp"].push(window["mapPointsNames_" + pathID][x]);
                    }
                    else {
                        window["mapPointsNamesTemp"].push("empty");
                    }
                }
                else {
                    window["mapPointsNamesTemp"].push(window["mapPointsNames_" + pathID][x]);
                }
            }

            showHidePointsArray = window["mapPointsNamesTemp"];
            window["mapPointsNamesTemp"] = [];
            allTreePoints = [];
        }
        else {
            showHidePointsArray = window["mapPointsNames_" + pathID];
        }

        if (action == "Show") {
            for (var x = 0;x < showHidePointsArray.length;x++) {
                if (x == 0) {
                    var type = "Source";
                }
                else if (x == showHidePointsArray.length - 1) {
                    var type = "Destination";
                }
                else {
                    var type = String(x);
                }

                if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("WARE_") == true) {
                    var wareID = showHidePointsArray[x].split(":")[0];
                    var wareName = showHidePointsArray[x].split(":")[1];
                    var longLat = String(window["mapPoints_" + pathID][x]).replaceAll(/[( )]/g, '');
                    //createSiteCltMarker(wareID,showHidePointsArray[x],longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);
                    if (!allSiteID.includes(wareID)) {
                        allSiteID.push(wareID)
                        create_Site_Marker_Click(wareID, wareName, longLat.split(",")[1], longLat.split(",")[0], markersSite, markerClusterSite, "Site", "")
                    }
                    if (markersSite[wareID]) {
                        if (markersSite[wareID].getMap() == null) {
                            markerClusterSite.removeMarker(markersSite[wareID]);
                            markersSite[wareID].setMap(map);
                            markerClusterSite.addMarker(markersSite[wareID]);
                            if ($("#" + wareID).children('input:checkbox').length > 0) {
                                $("#" + wareID).children(':checkbox').prop("checked", true);
                            }
                            $("#siteCheckAllBoq").prop("checked", true);
                        }

                        //Show seq if site label is checked 
                        if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("sitesMapCheck_Labels") == true) {
                                markersSite[wareID].setLabel({ text: type + " // " + wareID + " : " + showHidePointsArray[x].split(":")[1], className: "marker-position-site", color: "red" });
                            }
                            else {
                                markersSite[wareID].setLabel({ text: type, className: "marker-position-sequence", color: "red" });
                            }
                        }
                        //Show Seq is unchecked
                        else {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("sitesMapCheck_Labels") == true) {
                                markersSite[wareID].setLabel({ text: wareID + " : " + showHidePointsArray[x].split(":")[1], className: "marker-position-site", color: "red" });
                            }
                            else {
                                //if(markerClusterSite[wareID].getLabel()!="undefined") {
                                markersSite[wareID].setLabel(null);
                                //}
                            }
                        }
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("CUST_") == true) {
                    var cltID = showHidePointsArray[x].split(":")[0];
                    var longLat = String(window["mapPoints_" + pathID][x]).replaceAll(/[( )]/g, '');

                    createSiteCltMarker(cltID, showHidePointsArray[x], longLat.split(",")[0], longLat.split(",")[1], siteCltSrcMarkers);

                    //Show seq is checked 
                    if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                        if (allcheckedLabels.length > 0 && allcheckedLabels.includes("clientsMapCheck_Labels") == true) {
                            siteCltSrcMarkers[cltID].setLabel({ text: type + " // " + showHidePointsArray[x], className: "marker-position-site", color: "red" });
                        }
                        else {
                            siteCltSrcMarkers[cltID].setLabel({ text: type, className: "marker-position-sequence", color: "red" });
                        }
                    }
                    //Show Seq is unchecked
                    else {
                        if (allcheckedLabels.length > 0 && allcheckedLabels.includes("clientsMapCheck_Labels") == true) {
                            siteCltSrcMarkers[cltID].setLabel({ text: showHidePointsArray[x], className: "marker-position-site", color: "red" });
                        }
                        else {
                            if (siteCltSrcMarkers[cltID].getLabel() != "undefined") {
                                siteCltSrcMarkers[cltID].setLabel(null);
                            }
                        }
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("N/A") == true) {
                    var idNA = showHidePointsArray[x].concat("_" + type + "_" + pathID);
                    var longLat = String(window["mapPoints_" + pathID][x]).replaceAll(/[( )]/g, '');
                    createSiteCltMarker(idNA, idNA, longLat.split(",")[0], longLat.split(",")[1], siteCltSrcMarkers);
                    if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                        siteCltSrcMarkers[idNA].setLabel({ text: type, className: "marker-position-sequence", color: "red" });
                    }
                    else {
                        if (siteCltSrcMarkers[idNA].getLabel() != "undefined") {
                            siteCltSrcMarkers[idNA].setLabel(null);
                        }
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("MH_") == true && readManhole === '1') {
                    var manID = showHidePointsArray[x].split(":")[0];
                    if (markersManhole[manID]) {
                        if (markersManhole[manID].getMap() == null) {
                            markerClusterManhole.removeMarker(markersManhole[manID]);
                            markersManhole[manID].setMap(map);
                            markerClusterManhole.addMarker(markersManhole[manID]);
                            $("#" + manID).children(':checkbox').prop("checked", true);
                            $("#manholeCheckAllBoq").prop("checked", true);
                        }
                        //Show seq is checked 
                        if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("manholesMapCheck_Labels") == true) {
                                markersManhole[manID].setLabel({ text: type + " // " + showHidePointsArray[x].split(":")[1], className: "marker-position-manhole", color: "red" });
                            }
                            else {
                                markersManhole[manID].setLabel({ text: type, className: "marker-position-sequence", color: "red" });
                            }
                        }
                        //Show Seq is unchecked
                        else {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("manholesMapCheck_Labels") == true) {
                                markersManhole[manID].setLabel({ text: showHidePointsArray[x].split(":")[1], className: "marker-position-manhole", color: "red" });
                            }
                            else {
                                //if(markersManhole[manID].getLabel()!="undefined") {
                                markersManhole[manID].setLabel(null);
                                //}
                            }
                        }
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("HH_") == true && readHandhole === '1') {
                    var handID = showHidePointsArray[x].split(":")[0];
                    if (markersHandhole[handID]) {
                        if (markersHandhole[handID].getMap() == null) {
                            markerClusterHandhole.removeMarker(markersHandhole[handID]);
                            markersHandhole[handID].setMap(map);
                            markerClusterHandhole.addMarker(markersHandhole[handID]);
                            $("#" + handID).children(':checkbox').prop("checked", true);
                            $("#handholeCheckAllBoq").prop("checked", true);
                        }
                        //Show Sequence is checked
                        if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("handholesMapCheck_Labels") == true) {
                                markersHandhole[handID].setLabel({ text: type + " // " + showHidePointsArray[x].split(":")[1], className: "marker-position-handhole", color: "#E5C523" });
                            }
                            else {
                                markersHandhole[handID].setLabel({ text: type, className: "marker-position-sequence", color: "#E5C523" });
                            }
                        }
                        //Show Sequence is unchecked
                        else {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("handholesMapCheck_Labels") == true) {
                                markersHandhole[handID].setLabel({ text: showHidePointsArray[x].split(":")[1], className: "marker-position-handhole", color: "#E5C523" });
                            }
                            else {
                                if (markersHandhole[handID].getLabel() != "undefined") {
                                    markersHandhole[handID].setLabel(null);
                                }
                            }
                        }
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("DB_") == true && readDB === '1') {
                    var dbID = showHidePointsArray[x].split(":")[0];
                    if (markersDistBoard[dbID]) {
                        if (markersDistBoard[dbID].getMap() == null) {
                            if (window["" + dbID][8] == "backbone") {
                                className = markerClusterBackboneDistBoard;
                            }
                            else if (window["" + dbID][8] == "metro") {
                                className = markerClusterMetroDistBoard;
                            }
                            else if (window["" + dbID][8] == "access") {
                                className = markerClusterAccessDistBoard;
                            }
                            className.removeMarker(markersDistBoard[dbID]);
                            markersDistBoard[dbID].setMap(map);
                            className.addMarker(markersDistBoard[dbID]);
                            $("#" + dbID).children(':checkbox').prop("checked", true);
                            $("#distBoardCheckAllBoq").prop("checked", true);
                        }
                        if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("dBMapCheck_Labels") == true) {
                                markersDistBoard[dbID].setLabel({ text: type + " // " + showHidePointsArray[x].split(":")[1], className: "marker-position-dB", color: "#5665F9" });
                            }
                            else {
                                markersDistBoard[dbID].setLabel({ text: type, className: "marker-position-sequence", color: "#5665F9" });
                            }
                        }
                        else {
                            if (allcheckedLabels.length > 0 && allcheckedLabels.includes("dBMapCheck_Labels") == true) {
                                markersDistBoard[dbID].setLabel({ text: showHidePointsArray[x].split(":")[1], className: "marker-position-dB", color: "#5665F9" });
                            }
                            else {
                                if (markersDistBoard[dbID].getLabel() != "undefined") {
                                    markersDistBoard[dbID].setLabel(null);
                                }
                            }
                        }
                    }
                }
                // Case of null auxiliary point
                else if (showHidePointsArray[x] == null || showHidePointsArray[x] == "null" || (showHidePointsArray[x] && showHidePointsArray[x].includes("null"))) {
                    var AuxId = "null".concat("_" + type + "_" + pathID);
                    var longLat = String(window["mapPoints_" + pathID][x]).replaceAll(/[( )]/g, '');

                    createSiteCltMarker(AuxId, AuxId, longLat.split(",")[0], longLat.split(",")[1], siteCltSrcMarkers);
                    if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                        siteCltSrcMarkers[AuxId].setLabel({ text: type, className: "marker-position-sequence", color: "green" });
                    }
                    else {
                        if (siteCltSrcMarkers[AuxId].getLabel() != "undefined") {
                            siteCltSrcMarkers[AuxId].setLabel(null);
                        }
                    }
                }
                else if (showHidePointsArray[x].includes("Auxiliary_Point")) {
                    var auxID = showHidePointsArray[x].split(":")[0];
                    var longLat = String(window["mapPoints_" + pathID][x]).replaceAll(/[( )]/g, '');

                    createSiteCltMarker(auxID, auxID, longLat.split(",")[0], longLat.split(",")[1], siteCltSrcMarkers);
                    if (window['' + checkSeqWindowID + '_' + pathID] == "checked") {
                        siteCltSrcMarkers[auxID].setLabel({ text: type, className: "marker-position-sequence", color: "green" });
                    }
                    else {
                        if (siteCltSrcMarkers[auxID].getLabel() != "undefined") {
                            siteCltSrcMarkers[auxID].setLabel(null);
                        }
                    }
                }

            }
        }
        //Action is hide points
        else {

            for (var x = 0;x < showHidePointsArray.length;x++) {

                if (x == 0) {
                    var type = "Source";
                }
                else if (x == showHidePointsArray.length - 1) {
                    var type = "Destination";
                }
                else {
                    var type = String(x);
                }

                if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("WARE_") == true) {
                    var wareID = showHidePointsArray[x].split(":")[0];
                    if (markersSite[wareID]) {
                        markersSite[wareID].setMap(null);
                        markerClusterSite.removeMarker(markersSite[wareID]);
                        $("#" + wareID).children(':checkbox').prop("checked", false);
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("CUST_") == true) {
                    var cltID = showHidePointsArray[x].split(":")[0];
                    if (siteCltSrcMarkers[cltID]) {
                        siteCltSrcMarkers[cltID].setMap(null);
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("N/A") == true) {
                    var idNA = showHidePointsArray[x].concat("_" + type + "_" + pathID);
                    if (siteCltSrcMarkers[idNA]) {
                        siteCltSrcMarkers[idNA].setMap(null);
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("MH_") == true && readManhole === '1') {
                    var manID = showHidePointsArray[x].split(":")[0];
                    if (markersManhole[manID]) {
                        markersManhole[manID].setMap(null);
                        markerClusterManhole.removeMarker(markersManhole[manID]);
                        $("#" + manID).children(':checkbox').prop("checked", false);
                    }
                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("HH_") == true && readHandhole === '1') {
                    var handID = showHidePointsArray[x].split(":")[0];
                    if (markersHandhole[handID]) {
                        markersHandhole[handID].setMap(null);
                        markerClusterHandhole.removeMarker(markersHandhole[handID]);
                        $("#" + handID).children(':checkbox').prop("checked", false);
                    }

                }
                else if (showHidePointsArray[x] && showHidePointsArray[x].startsWith("DB_") == true && readDB === '1') {
                    var dbID = showHidePointsArray[x].split(":")[0];
                    if (markersDistBoard[dbID]) {
                        markersDistBoard[dbID].setMap(null);
                        if (window["" + dbID][8] == "backbone") {
                            markerClusterBackboneDistBoard.removeMarker(markersDistBoard[dbID]);
                        }
                        else if (window["" + dbID][8] == "metro") {
                            markerClusterMetroDistBoard.removeMarker(markersDistBoard[dbID]);
                        }
                        else if (window["" + dbID][8] == "access") {
                            markerClusterAccessDistBoard.removeMarker(markersDistBoard[dbID]);
                        }
                        $("#" + dbID).children(':checkbox').prop("checked", false);
                    }
                }
                else if (showHidePointsArray[x] == null || showHidePointsArray[x] == "null" || (showHidePointsArray[x] && showHidePointsArray[x].includes("null"))) {
                    var AuxId = "null".concat("_" + type + "_" + pathID);
                    if (siteCltSrcMarkers[AuxId]) {
                        siteCltSrcMarkers[AuxId].setMap(null);
                    }
                }
                else if (showHidePointsArray[x].includes("Auxiliary_Point")) {
                    var auxID = showHidePointsArray[x].split(":")[0];
                    if (siteCltSrcMarkers[auxID]) {
                        siteCltSrcMarkers[auxID].setMap(null);
                    }
                }
            }
        }
        showHidePointsArray = [];
    }
}
