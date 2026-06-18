function onGenerateClick() {

    generateFlag = "1";

    prepareGenerateProcess();

    if (!validateGenerateInputs()) {
        return;
    }

    callGenerateReport();
}

function validateGenerateInputs() {

    if ($("#fiberCable").val() === "") {

        alert("Please enter a cable!");
        return false;
    }

    return true;
}

function prepareGenerateProcess() {

    resetAllLegendCheckboxes();

    showRelPathFlag = "notOpened";
    jctElementsFlag = "notOpened";

    closeInfoWindows();

    resetMapData();

    rebuildGridTable();
}

function callGenerateReport() {

    showLoader();

    cableID = $("#fiberCable").val().split(":")[0];

    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: getContext() + "/GenerateStrandUtilizationReport",
        dataType: "json",
        data: {
            cableID: cableID
        },

        success: function(data) {
            handleGenerateSuccess(data);
            hideLoader();
        },

        error: function(error) {

            handleGenerateError(error);
            hideLoader();
        }
    });
}

function handleGenerateSuccess(data) {

    // MOVE THE ENTIRE SUCCESS BLOCK HERE

    if (data != null) {

        ReportArrayGlobal = data.listStrandsUtilization;
        manHandList = data.manHandList;
        manHandoleList = data.manHandoleList;
        if (ReportArrayGlobal.length == 0) {
            alert("There is no data to display");
            $('#totalStrands').val('0');
            $('#totalUsedStrands').val('0');
            $('#totalNotUsedStrands').val('0');
        }

        initializeGrid(ReportArrayGlobal);

        window["mapPointsNames_" + cableID] = [];
        window["mapPoints_" + cableID] = [];

        srcDstCableList = data.fiberList;

        processCableSrcDstArray(cableID, "mapPointsNames_", data.fiberList, "mapPoints_");

        window["bounds_" + cableID] = new google.maps.LatLngBounds();
        window["bounds_" + cableID].extend(new google.maps.LatLng(data.fiberList[0][1], data.fiberList[0][0]));
        window["bounds_" + cableID].extend(new google.maps.LatLng(data.fiberList[0][3], data.fiberList[0][2]));


        processFiberAuxPoints(cableID, data.fiberAuxData, "mapPointsNames_");
        window["mapPoints_" + cableID].push(new google.maps.LatLng(data.fiberList[0][3], data.fiberList[0][2]));


        var totalStrands = parseFloat(data.fiberList[0][11]) * parseFloat(data.fiberList[0][12]);
        $('#totalStrands').val(totalStrands);

        var totalUsedStr = parseFloat(data.frontTotalUsedStrands) + parseFloat(data.backTotalUsedStrands) + parseFloat(data.jctTotalUsedStrands);
        $('#totalUsedStrands').val(totalUsedStr);


        var totalNotUsedStrands = parseFloat(totalStrands) - parseFloat(totalUsedStr);
        $('#totalNotUsedStrands').val(totalNotUsedStrands);




        if (allCables.includes(cableID) == false) {
            allCables.push(cableID);
        }
        buildPath(cableID, fiberCableArray, $("#fiberCable").val().split(":")[1], window["mapPoints_" + cableID], data.fiberList[0][10], 0.7, 4.5, 'blue', 13);
        fiberCableArray[cableID].setMap(map);
        $('.showHideCableCheckbox').prop('checked', true);
        $(".showHideCableCheckbox").attr('disabled', false);

        map.fitBounds(window["bounds_" + cableID]);


        //if(data.fiberAuxDataRelatedPath.length >0) {
        for (var c = 0;c < data.relatedPathCables.length;c++) {
            var pathID = data.relatedPathCables[c][0];
            if (allRelatedPathCables.includes(pathID) == false) {
                allRelatedPathCables.push(pathID);
            }
            window["mapPoints_" + pathID] = [];
            window["mapPoints_" + pathID].push(new google.maps.LatLng(data.relatedPathCables[c][2], data.relatedPathCables[c][1]));

            for (var y = 0;y < data.fiberAuxDataRelatedPath.length;y++) {
                if (data.fiberAuxDataRelatedPath[y][0] == pathID) {
                    window["mapPoints_" + pathID].push(new google.maps.LatLng(data.fiberAuxDataRelatedPath[y][2], data.fiberAuxDataRelatedPath[y][1]));
                }

            }
            window["mapPoints_" + pathID].push(new google.maps.LatLng(data.relatedPathCables[c][4], data.relatedPathCables[c][3]));
            buildPath(pathID, relatedPathArray, data.relatedPathCables[c][11], window["mapPoints_" + pathID], data.relatedPathCables[c][12], 0.7, 4.5, 'blue', 13);			
        }
    }
    hideLoader();
}


function handleGenerateError(error) {

    console.log("The error is " + error);
}


function processFiberAuxPoints(cableID, fiberAuxData, windowMapPointsNames) {

    for (i = 0;i < fiberAuxData.length;i++) {//PUSH AUXILIARY POINTS OF FIBER CABLE into window to use it in show on map
        window["mapPoints_" + cableID].push(new google.maps.LatLng(fiberAuxData[i][1], fiberAuxData[i][0]));
        window["bounds_" + cableID].extend(new google.maps.LatLng(fiberAuxData[i][1], fiberAuxData[i][0]));

        if (fiberAuxData[i][2] == "") {
            auxPoint = "";
        }
        else if (fiberAuxData[i][2] != "null") {
            auxPoint = fiberAuxData[i][2] + ":" + fiberAuxData[i][3] + ":" + fiberAuxData[i][4];
        }
        else {

            if (fiberAuxData[i][3].startsWith("MH") == true || fiberAuxData[i][3].startsWith("HH") == true || fiberAuxData[i][3].startsWith("DB") == true) {
                auxPoint = fiberAuxData[i][3] + ":" + fiberAuxData[i][4];
            }
            else if (fiberAuxData[i][4].includes("Auxiliary_Point") == true) {
                auxPoint = fiberAuxData[i][6] + ":" + fiberAuxData[i][4];
            }
            else {
                auxPoint = fiberAuxData[i][4];
            }
        }
        window["" + windowMapPointsNames + cableID].splice(window["" + windowMapPointsNames + cableID].length - 1, 0, auxPoint);// insert at before last index which is the destination
    }
}


function processCableSrcDstArray(cableID, windowMapPointsNames, fiberList, windowMapPoints) {

    if (fiberList.length > 0) {

        //Push src
        if (fiberList[0][4] != "null") {
            src = fiberList[0][4] + ":" + fiberList[0][5] + ":" + fiberList[0][6];
        }
        else {
            if (fiberList[0][5].startsWith("MH") == true || fiberList[0][5].startsWith("HH") == true || fiberList[0][5].startsWith("DB") == true || fiberList[0][5].startsWith("CUST") == true) {
                src = fiberList[0][5] + ":" + fiberList[0][6];
            }
            else {
                src = fiberList[0][6];
            }
        }
        window["" + windowMapPointsNames + cableID].push(src);

        //Push dst
        if (fiberList[0][7] != "null") {
            dst = fiberList[0][7] + ":" + fiberList[0][8] + ":" + fiberList[0][9];
        }
        else {
            if (fiberList[0][8].startsWith("MH") == true || fiberList[0][8].startsWith("HH") == true || fiberList[0][8].startsWith("DB") == true || fiberList[0][8].startsWith("CUST") == true) {
                dst = fiberList[0][8] + ":" + fiberList[0][9];
            }
            else {
                dst = fiberList[0][9];
            }
        }
        window["" + windowMapPointsNames + cableID].push(dst);

        window["" + windowMapPoints + cableID].push(new google.maps.LatLng(fiberList[0][1], fiberList[0][0]));

    }
}