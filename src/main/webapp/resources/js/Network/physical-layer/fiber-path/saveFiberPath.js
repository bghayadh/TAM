function saveFiberPath() {
	
	console.log("Welcome to saveFiberPath");

    if (geoFlag == 1) {
        calculateGeoDistance("FiberPathId", "SourceLng", "SourceLat", "DestinationLng", "DestinationLat", "auxiliaryTable", "auxGeoDistance", "totalGeoDistance");
    }
    let token = $('input[name="csrfToken"]').attr('value');
    widthOfDiv = $("#left").width();
    fiber_length = $("#FiberLength").val();
    driving_dist = $("#FiberDrivDist").val();
    checkLongLatInRows("auxiliaryTable", "auxiliary_Longitude", "auxiliary_Latitude");

    if ($("#Source").val() == "" || $("#Destination").val() == "" || $("#fiberName").val() == "") {
        alert("Missing fields!");
    }
    else if ($("#tubesTable > tbody").children().length > $("#NumTubes").val()) {
        alert("The number of tubes already exceeds " + $("#NumTubes").val());
    }
    else if (isNaN(fiber_length) == true || $("#FiberLength").val() == "") {
        alert('Fiber length must be a number');
        return false;
    }
    else if (isNaN(driving_dist) == true || $("#FiberDrivDist").val() == "") {
        alert('Driving distance must be a number');
        return false;
    }
    else if (!isInDesiredForm($("#NumTubes").val())) {
        alert("Number of tubes must be a number");
    }
    else if (!isInDesiredForm($("#NumStrands").val())) {
        alert("Number of strands must be a number");
    }
    else if ($("#strandsTable > tbody").children().length > $("#NumStrands").val()) {
        alert("The number of strands already exceeds " + $("#NumStrands").val());
    }
    else if (document.getElementById("srcCity").value == "") {
        alert("Source City cannot be empty. ");
        return false;
    }
    else if (document.getElementById("dstCity").value == "") {
        alert("Destination City cannot be empty. ");
        return false;
    }
    else {

        allAuxDictStrand = [];
        allAuxDictTube = [];

        fiberId = document.getElementById("FiberPathId").value;
        ItemCodeId = document.getElementById("ItemCodeId").value;
        fiberName = document.getElementById("fiberName").value;
        FiberLength = document.getElementById("FiberLength").value;
        fiberCableSize = document.getElementById("fiberCableSize").value;
        NumTubes = document.getElementById("NumTubes").value;
        NumStrands = document.getElementById("NumStrands").value;
        Condiut_Id = document.getElementById("Condiut_Id").value;
        Condiut_Name = document.getElementById("Condiut_Name").value;
        fibertype = document.getElementById("fibertype").value;
        fiberdeployment = document.getElementById("fiberdeployment").value;
        fibernetlevel = document.getElementById("fibernetlevel").value;
        fiberowner = document.getElementById("fiberowner").value;
        fiberEngineerName = document.getElementById("fiberEngineerName").value;
        fiberInstaller = document.getElementById("fiberInstaller").value;
        source = document.getElementById("Source").value;
        destination = document.getElementById("Destination").value;
        sourceLng = document.getElementById("SourceLng").value;
        sourceLat = document.getElementById("SourceLat").value;
        sourceCity = document.getElementById("srcCity").value;
        destinationLng = document.getElementById("DestinationLng").value;
        destinationLat = document.getElementById("DestinationLat").value;
        destinationCity = document.getElementById("dstCity").value;
        fiberCableCreatedByUser = document.getElementById("crtdByFiberCable").value;
        fiberCableModifiedByUser = lstModfUser;
        fiberAuxFlag = document.querySelector("#fiberAuxFlag ").value
        totalDrivingDistance = document.getElementById("totalDistanceDrivg").value;
        drawingType = document.getElementById("drawingBy").value;
        totalGeoDistance = document.getElementById("totalGeoDistance").value;
        lastAuxToDestDistance = document.getElementById("distanceLstAuxToDest").value;
        lastAuxToDestDrivDistance = document.getElementById("distanceLstAuxToDestDrivg").value;
        cableCreatedDate = document.getElementById("popupCreateDateFiber").value;
        //related cable
        relatedStrandNb = document.getElementById("relatedCableStrandNb").value;
        relatedStrandColor = document.getElementById("relatedCableStrandColor").value;
        relatedStrandID = document.getElementById("relatedCableStrandID").value;
        relatedStrandName = document.getElementById("relatedCableStrandName").value;
        relatedTubeNb = document.getElementById("relatedCableTubeNb").value;
        relatedTubeColor = document.getElementById("relatedCableTubeColor").value;
        relatedTubeID = document.getElementById("relatedCableTubeID").value;
        relatedTubeName = document.getElementById("relatedCableTubeName").value;
        relatedCableID = document.getElementById("relatedCableID").value;
        relatedCableName = document.getElementById("relatedCableName").value;
        otherLastMileID = document.getElementById("otherLastMileID").value;
        otherLastMileName = document.getElementById("otherLastMileName").value;
        otherSideLocationID = document.getElementById("relatedCableLocationID").value;
        otherSideLocationName = document.getElementById("relatedCableLocationName").value;
        otherSideLocationCity = document.getElementById("relatedCableCity").value;
        otherSideLocationType = document.getElementById("relatedCableLocationType").value;

        //acces junction
        jundict = [];
        var junctId = "";
        $("#relatedCableJctTable > tbody").find('input[name="record"]').each(function() {

            var junID = $(this).parent().parent().children('td[name="LastMileJunctionID"]').children('input').val();
            var junName = $(this).parent().parent().children('td[name="LastMileJunctionName"]').children('input').val();

            var junctId = $(this).parent().parent().attr('id');
            junctId = $(this).parent().parent().attr('id');
            if (typeof junctId == 'undefined') {
                junctId = "";
            }
            jundict.push({
                "junctId": junctId,
                "junID": junID,
                "junName": junName
            });
        });
        //
        getSelectedFiberCableRows(sourceLat, sourceLng, fiberId);
		console.log("Just before sending ajax for saving");
        $.ajax({
            type: "POST",
            headers: {
                'X-CSRFToken': token
            },
            url: getContext() + '/fiberPathSave',
            data: {
                "fiberpathID": fiberId,
                "ItemCodeId": ItemCodeId,
                "fiberName": fiberName,
                "FiberLength": FiberLength,
                "fiberCableSize": fiberCableSize,
                "NumTubes": NumTubes,
                "NumStrands": NumStrands,
                "Condiut_Id": Condiut_Id,
                "Condiut_Name": Condiut_Name,
                "fibertype": fibertype,
                "fiberdeployment": fiberdeployment,
                "fibernetlevel": fibernetlevel,
                "fiberowner": fiberowner,
                "fiberInstaller": fiberInstaller,
                "fiberEngineerName": fiberEngineerName,
                "Source": source,
                "Destination": destination,
                "sourceLng": sourceLng,
                "sourceLat": sourceLat,
                "sourceCity": sourceCity,
                "destinationLng": destinationLng,
                "destinationLat": destinationLat,
                "destinationCity": destinationCity,
                "fiberCableCreatedByUser": fiberCableCreatedByUser,
                "fiberCableModifiedByUser": fiberCableModifiedByUser,
                "fiberAuxFlag": fiberAuxFlag,
                "dictParameter": dict,
				"dictParameterFiberDuct": getFiberDuct(),
                "dictParameterTubes": dictTubes,
                "dictParameterStrands": dictStrands,
                "dictParameterTubesAux": allAuxDictTube,
                "dictParameterStrandsAux": allAuxDictStrand,
                "selectedFiberMode": selectedFiberMode,
                "ProjectId": IdNodeSelectedTemp,
                "totalDrivingDistance": totalDrivingDistance,
                "drawingType": drawingType,
                "totalGeoDistance": totalGeoDistance,
                "lastAuxToDestDistance": lastAuxToDestDistance,
                "lastAuxToDestDrivDistance": lastAuxToDestDrivDistance,
                "cableCreatedDate": cableCreatedDate,
                "relatedStrandNb": relatedStrandNb,
                "relatedStrandColor": relatedStrandColor,
                "relatedStrandID": relatedStrandID,
                "relatedStrandName": relatedStrandName,
                "relatedTubeNb": relatedTubeNb,
                "relatedTubeColor": relatedTubeColor,
                "relatedTubeID": relatedTubeID,
                "relatedTubeName": relatedTubeName,
                "relatedCableID": relatedCableID,
                "relatedCableName": relatedCableName,
                "otherLastMileID": otherLastMileID,
                "otherLastMileName": otherLastMileName,
                "otherSideLocationID": otherSideLocationID,
                "otherSideLocationName": otherSideLocationName,
                "otherSideLocationCity": otherSideLocationCity,
                "otherSideLocationType": otherSideLocationType,
                "dictParameterJunct": jundict,
                "updateModfUser": updateModfUser,

            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    if (fiberId == "") {
                        str = "<ul><li id='" + data.FiberPathId + "' class='FIBER' style='display:none; width:100px;'><input type='checkbox' class='FiberCable checkFilter' checked class='filter'></input> <span class='TreeSpan' style='color:black;'><img src='" + getContext() + "/resources/NetworkImages/fiber.png'>  " + fiberName + " / " + data.FiberPathId + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + data.FiberPathId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + data.FiberPathId + "' class='clearPoints'> </span></li></ul>";
                        $("#FiberPath_" + fibernetlevel + "__" + IdNodeSelectedTemp).append(str);

                        $(".origination,.termination").addClass('disabled');
                        window['fiberCheckPoints_' + data.FiberPathId] = "unchecked";
                        window['fiberCheckRealPoints_' + data.FiberPathId] = "unchecked";
                        window['fiberCheckSequence_' + data.FiberPathId] = "unchecked";

                        // checkbox tree events for the new saved cable								
                        pathCheckFilter(TargetFiber, "", data.FiberPathId, "20", fiberArray, allFiberCables, directionHashmap, routeDisplay, "TUBE", tubeArray, directionHashmapTube, allTubes);

                    }
                    else {
                        $("#" + data.FiberPathId + "> .TreeSpan").html("<img src='" + getContext() + "/resources/NetworkImages/fiber.png'>  " + fiberName + " / " + data.FiberPathId + "  <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + data.FiberPathId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + data.FiberPathId + "' class='clearPoints'>  ");

                        $("#" + data.FiberPathId).find(' > ul > li >ul >li').each(function() {
                            var tubeId = $(this).attr('id');
                            if (tubeArray[tubeId]) {
                                tubeArray[tubeId].setMap(null);
                            }
                            $("#" + tubeId).find('>ul >li>ul >li').each(function() {
                                var strandId = $(this).attr('id');
                                if (strandArray[strandId]) {
                                    strandArray[strandId].setMap(null);
                                }
                            });
                        });

                        if (oldNtwLevel != fibernetlevel) {
                            $("#" + data.FiberPathId).remove();
                            str = "<ul><li id='" + data.FiberPathId + "' class='FIBER' style='display:none; width:100px;'><input type='checkbox' class='FiberCable checkFilter' checked class='filter'></input> <span class='TreeSpan' style='color:black;'><img src='" + getContext() + "/resources/NetworkImages/fiber.png'>  " + fiberName + " / " + data.FiberPathId + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + data.FiberPathId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + data.FiberPathId + "' class='clearPoints'> </span></li></ul>";
                            $("#FiberPath_" + fibernetlevel + "__" + IdNodeSelectedTemp).append(str);
                        }
                    }
                    window["" + data.FiberPathId] = [];
                    window["mapPoints_" + data.FiberPathId] = [];
                    window["mapPointsNames_" + data.FiberPathId] = [];
                    window["bounds_" + data.FiberPathId] = new google.maps.LatLngBounds();

                    var numberOfAssoc_Tubes = dictTubes.length;
                    var numberOfAssoc_Strands = dictStrands.length;

                    if (source.includes("WARE") == true) {
                        var srcWareId = source.split(":")[0];
                        var srcId = source.split(":")[2];
                        var srcName = source.split(":")[1];
                    }
                    else if (source.includes("CUST") == true) {
                        var srcWareId = "null";
                        var srcId = source.split(":")[0];
                        var srcName = source.split(":")[1];
                    }
                    else if (source.includes("MH") == true || source.includes("HH") == true || source.includes("DB") == true) {
                        var srcWareId = "null";
                        var srcId = source.split(":")[0];
                        var srcName = source.split(":")[1];
                    }
                    else {
                        var srcWareId = "null";
                        var srcId = "null";
                        var srcName = source;
                    }
                    if (destination.includes("WARE") == true) {
                        var dstWareId = destination.split(":")[0];
                        var dstId = destination.split(":")[2];
                        var dstName = destination.split(":")[1];
                    }
                    else if (destination.includes("CUST") == true) {
                        var dstWareId = "null";
                        var dstId = destination.split(":")[0];
                        var dstName = destination.split(":")[1];
                    }
                    else if (destination.includes("MH") == true || destination.includes("HH") == true || destination.includes("DB") == true) {
                        var dstWareId = "null";
                        var dstId = destination.split(":")[0];
                        var dstName = destination.split(":")[1];
                    }
                    else {
                        var dstWareId = "null";
                        var dstId = "null";
                        var dstName = destination;
                    }
                    window["" + data.FiberPathId] = [sourceLng, sourceLat, destinationLng, destinationLat, data.FiberPathId, srcWareId, srcId, srcName, dstWareId, dstId, dstName, numberOfAssoc_Tubes, numberOfAssoc_Strands, fiberName, IdNodeSelectedTemp, sourceCity, destinationCity, NumTubes, NumStrands, FiberLength, drawingType, fiberowner, fiberInstaller, fiberEngineerName, fiberCableSize];

                    myLatLng = new google.maps.LatLng(sourceLat, sourceLng);
                    window["mapPoints_" + data.FiberPathId].push(myLatLng);

                    window["mapPointsNames_" + data.FiberPathId].push(source);
                    window["bounds_" + data.FiberPathId].extend(myLatLng);

                    //PUSH AUXILIARY POINTS OF FIBER CABLE
                    for (i = 0;i < data.fiberAux.length;i++) {
                        myLatLng = new google.maps.LatLng(data.fiberAux[i][1], data.fiberAux[i][0]);
                        window["mapPoints_" + data.FiberPathId].push(myLatLng);

                        if (data.fiberAux[i][3] != "null") {
                            var auxPoint = data.fiberAux[i][3] + ":" + data.fiberAux[i][5] + ":" + data.fiberAux[i][4];
                        }
                        else {
                            if (data.fiberAux[i][4].split("_")[0] == "MH" || data.fiberAux[i][4].split("_")[0] == "HH" || data.fiberAux[i][4].split("_")[0] == "DB") {
                                var auxPoint = data.fiberAux[i][4] + ":" + data.fiberAux[i][5];
                            }
                            else if (data.fiberAux[i][5].includes("Auxiliary_Point") == true) {
                                var auxPoint = data.fiberAux[i][8] + ":" + data.fiberAux[i][5];
                            }
                            else {
                                var auxPoint = data.fiberAux[i][5];
                            }
                        }
                        window["mapPointsNames_" + data.FiberPathId].push(auxPoint);
                        window["bounds_" + data.FiberPathId].extend(myLatLng);
                    }
                    window["mapPoints_" + data.FiberPathId].push(new google.maps.LatLng(destinationLat, destinationLng));
                    window["bounds_" + data.FiberPathId].extend(new google.maps.LatLng(destinationLat, destinationLng));
                    window["mapPointsNames_" + data.FiberPathId].push(destination);
                    auxPoint = "";
                    if ($("#drawingBy").val() == "DRIVING") {

                        if (fiberArray[fiberId]) {
                            fiberArray[fiberId].setMap(null);
                        }

                        if (directionHashmap.get(fiberId) != undefined) {
                            for (ii = 0;ii < directionHashmap.get(fiberId).length;ii++) {
                                directionHashmap.get(fiberId)[ii].setMap(null);
                            }
                        }

                        var path = window["mapPoints_" + data.FiberPathId];
                        buildDrivingPath(data.FiberPathId, window["mapPoints_" + data.FiberPathId], "FiberPath_f_", window['FiberColor_' + fiberowner], directionHashmap);
                    }
                    else {
                        var path = window["mapPoints_" + data.FiberPathId]
                        if (fiberArray[data.FiberPathId]) {
                            if (directionHashmap.get(data.FiberPathId) != undefined) {
                                for (ii = 0;ii < directionHashmap.get(data.FiberPathId).length;ii++) {
                                    directionHashmap.get(data.FiberPathId)[ii].setMap(null);
                                }
                            }
                            fiberArray[data.FiberPathId].setOptions({ strokeColor: window['FiberColor_' + fiberowner] });
                            fiberArray[data.FiberPathId].setPath(path);
                            fiberArray[data.FiberPathId].setMap(map);
                        }
                        else {
                            if (directionHashmap.get(data.FiberPathId) != undefined) {
                                for (ii = 0;ii < directionHashmap.get(data.FiberPathId).length;ii++) {
                                    directionHashmap.get(data.FiberPathId)[ii].setMap(null);
                                }
                            }
                            buildPath(data.FiberPathId, path, fiberArray, allFiberCables, "FiberPath_f_", window['FiberColor_' + fiberowner], 0.7, 4.5, 'blue', 13);
                            fiberArray[data.FiberPathId].setMap(map);
                        }

                    }

                    map.fitBounds(window["bounds_" + data.FiberPathId]);
                    $("#fiberCheckAllBoq").prop("checked", true);

                    if (dictTubes.length == 0) {
                        $("#" + data.FiberPathId + "> .folder").html("");
                        $("#" + data.FiberPathId + "_f").children('ul').remove();
                        $("#" + data.FiberPathId + "_f").remove();
                    }
                    else {
                        $("#" + data.FiberPathId + "> .folder").remove();
                        $("<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span>").insertBefore("#" + data.FiberPathId + "> .TreeSpan");

                        $("#" + data.FiberPathId + "> ul").remove();
                        str = "<ul><li id='" + data.FiberPathId + "_f' class='Tube_folder' style='display:none;'> <input type='checkbox' class='FiberTubes checkFilter' checked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;' > Tubes <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes_f" + data.FiberPathId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes_f" + data.FiberPathId + "' class='clearPoints'></span></li></ul>";
                        $("#" + data.FiberPathId).append(str);

                        pathCheckFilter(TargetTube, "parentFolderCheck", data.FiberPathId, "14", tubeArray, allTubes, directionHashmapTube, routeDisplayTube, "STRAND", strandArray, directionHashmapStrand, allStrands);

                    }
                    $("#" + data.FiberPathId + " > span").unbind();
                    $("#" + data.FiberPathId + "_f > span").unbind();

                    Create_FiberPath(data.FiberPathId);

                    $("#" + data.FiberPathId + " > .TreeSpan").bind("contextmenu", function() {
                        IdNodeSelectedTemp = $(this).parents().eq(2).attr('id').split("__")[1];
                        console.log("IdNodeSelectedTemp " + IdNodeSelectedTemp);
                        selectedFiberContext = $(this).parent().attr('id');
                        openContext(selectedFiberContext, "fiber", singleFiber, event)
                    });

                    $("#" + data.FiberPathId + "_f > .TreeSpan").bind("contextmenu", function() {
                        selectedFiberContext = $(this).parents().eq(2).attr('id');
                        selectedTube = $(this).parent().attr('id');
                        openContext(selectedTube, "", menuTubePath, event)
                    });

                    if ($("#fiberMapCheck_Labels").prop("checked") == true) {
                        fiberArray[data.FiberPathId].mapLabel.setMap(map);
                    }
                    //Add all tubes to the saved cable
                    if (dictTubes.length != 0) {
                        $("#tubeCheckAllBoq").prop("checked", true);
                        if (data.tubeIdArrayList) {
                            for (i = 0;i < data.tubeIdArrayList.length;i++) {
                                //var tubeID=data.tubeIdArrayList[i][0];
                                window["" + data.tubeIdArrayList[i][0]] = [];
                                window["" + data.tubeIdArrayList[i][0]] = data.tubeIdArrayList[i];
                                window["mapPoints_" + data.tubeIdArrayList[i][0]] = [];
                                window["mapPointsNames_" + data.tubeIdArrayList[i][0]] = [];

                                window['tubeCheckPoints_' + data.tubeIdArrayList[i][0]] = "unchecked";
                                window['tubeCheckSequence_' + data.tubeIdArrayList[i][0]] = "unchecked";
                                window['tubeCheckRealPoints_' + data.tubeIdArrayList[i][0]] = "unchecked";
                                if (data.tubeIdArrayList[i][5] != "null") {
                                    tubeSrc = data.tubeIdArrayList[i][5] + ":" + data.tubeIdArrayList[i][7] + ":" + data.tubeIdArrayList[i][6];
                                }
                                else {
                                    if (data.tubeIdArrayList[i][6].split("_")[0] == "MH" || data.tubeIdArrayList[i][6].split("_")[0] == "HH" || data.tubeIdArrayList[i][6].split("_")[0] == "DB" || data.tubeIdArrayList[i][6].split("_")[0] == "CUST") {
                                        tubeSrc = data.tubeIdArrayList[i][6] + ":" + data.tubeIdArrayList[i][7];
                                    }
                                    else {
                                        tubeSrc = data.tubeIdArrayList[i][7];
                                    }
                                }
                                window["mapPointsNames_" + data.tubeIdArrayList[i][0]].push(tubeSrc);

                                if (data.tubeIdArrayList[i][8] != "null") {
                                    tubeDst = data.tubeIdArrayList[i][8] + ":" + data.tubeIdArrayList[i][10] + ":" + data.tubeIdArrayList[i][9];
                                }
                                else {
                                    if (data.tubeIdArrayList[i][9].split("_")[0] == "MH" || data.tubeIdArrayList[i][9].split("_")[0] == "HH" || data.tubeIdArrayList[i][9].split("_")[0] == "DB" || data.tubeIdArrayList[i][9].split("_")[0] == "CUST") {
                                        tubeDst = data.tubeIdArrayList[i][9] + ":" + data.tubeIdArrayList[i][10];
                                    }
                                    else {
                                        tubeDst = data.tubeIdArrayList[i][10];
                                    }
                                }

                                window["bounds_" + data.tubeIdArrayList[i][0]] = new google.maps.LatLngBounds();
                                var myLatLng = new google.maps.LatLng(data.tubeIdArrayList[i][2], data.tubeIdArrayList[i][1]);
                                window["mapPoints_" + data.tubeIdArrayList[i][0]].push(myLatLng);
                                window["bounds_" + data.tubeIdArrayList[i][0]].extend(myLatLng);

                                if (typeof window["Auxpts_Tubes" + data.tubeIdArrayList[i][0]] != 'undefined') {
                                    for (k = 0;k < window["Auxpts_Tubes" + data.tubeIdArrayList[i][0]].length;k++) {
                                        myLatLng = new google.maps.LatLng(window["Auxpts_Tubes" + data.tubeIdArrayList[i][0]][k].aux_Latitude, window["Auxpts_Tubes" + data.tubeIdArrayList[i][0]][k].aux_Longitude)
                                        window["mapPoints_" + data.tubeIdArrayList[i][0]].push(myLatLng);
                                        window["bounds_" + data.tubeIdArrayList[i][0]].extend(myLatLng);
                                        window["mapPointsNames_" + data.tubeIdArrayList[i][0]].push(window["Auxpts_Tubes" + data.tubeIdArrayList[i][0]][k].aux_Name);
                                    }
                                }
                                window["mapPointsNames_" + data.tubeIdArrayList[i][0]].push(tubeDst);

                                myLatLng = new google.maps.LatLng(window["" + data.tubeIdArrayList[i][0]][4], window["" + data.tubeIdArrayList[i][0]][3]);
                                window["mapPoints_" + data.tubeIdArrayList[i][0]].push(myLatLng);
                                window["bounds_" + data.tubeIdArrayList[i][0]].extend(myLatLng);

                                path = window["mapPoints_" + data.tubeIdArrayList[i][0]];
                                buildPath(data.tubeIdArrayList[i][0], path, tubeArray, allTubes, "Tube", 'green', 0.7, 3.3, 'green', 0);
                                tubeArray[data.tubeIdArrayList[i][0]].setMap(map);

                                if (window["" + data.tubeIdArrayList[i][0]][11] > 0) {
                                    str = "<ul><li id='" + data.tubeIdArrayList[i][0] + "' class='TUBE' style='display:none;width:100px;'><input type='checkbox' class='FiberTube checkFilter' checked class='filter'></input> <span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;'> <div style='margin-bottom:-17px;margin-left:6px; font-size:12px; font-weight:bold; color:" + data.tubeIdArrayList[i][16] + "'>" + data.tubeIdArrayList[i][15] + "</div><img style='color: #08526D;' src='" + getContext() + "/resources/NetworkImages/core.png'>  " + data.tubeIdArrayList[i][13] + " / " + data.tubeIdArrayList[i][0] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsStrands" + data.tubeIdArrayList[i][0] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsStrands" + data.tubeIdArrayList[i][0] + "' class='clearPoints'> </span><ul><li id='" + data.tubeIdArrayList[i][0] + "_f' style='display:none;' class='strandsFolder'> <input type='checkbox' class='TubeStrands' checked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;'>Strands </span></li></ul></li></ul>";
                                    console.log("strands corresponding to this tube are not null ");
                                }
                                else {
                                    str = "<ul><li id='" + data.tubeIdArrayList[i][0] + "' class='TUBE' style='display:none;width:100px;'><input type='checkbox' class='FiberTube checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;'> <div style='margin-bottom:-17px;margin-left:6px; font-size:12px; font-weight:bold; color:" + data.tubeIdArrayList[i][16] + "'>" + data.tubeIdArrayList[i][15] + "</div> <img src='" + getContext() + "/resources/NetworkImages/core.png'>  " + data.tubeIdArrayList[i][13] + " / " + data.tubeIdArrayList[i][0] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsStrands" + data.tubeIdArrayList[i][0] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsStrands" + data.tubeIdArrayList[i][0] + "' class='clearPoints'>  </span></li></ul>";
                                }
                                $("#" + data.FiberPathId + "_f").append(str);

                                Create_FiberTubeClick_Event(data.tubeIdArrayList[i][0]);

                                pathCheckFilter(TargetTube, "", data.tubeIdArrayList[i][0], "14", tubeArray, allTubes, directionHashmapTube, routeDisplayTube, "STRAND", strandArray, directionHashmapStrand, allStrands);

                                $("#" + data.tubeIdArrayList[i][0] + " > span").bind("contextmenu", function() {
                                    selectedFiberContext = $(this).parents().eq(4).attr('id');
                                    selectedTube = $(this).parent().attr('id');
                                    openContext(selectedTube, "tube", singleTube, event)
                                });

                                // bind strands folder to the context menu event
                                $("#" + data.tubeIdArrayList[i][0] + "_f > .TreeSpan").bind("contextmenu", function() {
                                    selectedFiberContext = $(this).parents().eq(6).attr('id');
                                    selectedTube = $(this).parent().parent().parent().attr('id');
                                    selectedStrandPath = $(this).parent().attr('id');
                                    openContext(selectedStrandPath, "", menuStrandPath, event)
                                });
                                pathCheckFilter(TargetStrand, "parentFolderCheck", data.tubeIdArrayList[i][0], "14", strandArray, allStrands, directionHashmapStrand, routeDisplayStrand, "", "", "", "");

                                if ($("#tubeMapCheck_Labels").prop("checked") == true) {
                                    tubeArray[data.tubeIdArrayList[i][0]].mapLabel = new MapLabel({
                                        text: data.tubeIdArrayList[i][13],
                                        position: lablCoords,
                                        fontSize: 13,
                                        fontColor: 'green',
                                        align: 'top',

                                    });
                                    tubeArray[data.tubeIdArrayList[i][0]].mapLabel.setMap(map);
                                }
                                else {
                                    tubeArray[data.tubeIdArrayList[i][0]].mapLabel = new MapLabel({
                                        text: data.tubeIdArrayList[i][13],
                                        position: lablCoords,
                                        fontSize: 13,
                                        fontColor: 'green',
                                        align: 'top',
                                    });
                                }
                            }
                        } // end tube append 

                        //Add all Strands to the saved cable
                        if (data.strandsOfTubes) {
                            for (i = 0;i < data.strandsOfTubes.length;i++) {
                                var strandID = data.strandsOfTubes[i][0];
                                window["mapPointsNames_" + data.strandsOfTubes[i][0]] = [];
                                window["" + data.strandsOfTubes[i][0]] = [];
                                window["" + data.strandsOfTubes[i][0]] = data.strandsOfTubes[i];
                                window["mapPoints_" + data.strandsOfTubes[i][0]] = [];
                                window["bounds_" + data.strandsOfTubes[i][0]] = new google.maps.LatLngBounds();
                                var myLatLng = new google.maps.LatLng(data.strandsOfTubes[i][2], data.strandsOfTubes[i][1]);
                                window["mapPoints_" + data.strandsOfTubes[i][0]].push(myLatLng);
                                window["bounds_" + data.strandsOfTubes[i][0]].extend(myLatLng);

                                window['strandCheckPoints_' + data.strandsOfTubes[i][0]] = "unchecked";
                                window['strandCheckSequence_' + data.strandsOfTubes[i][0]] = "unchecked";
                                window['strandCheckRealPoints_' + data.strandsOfTubes[i][0]] = "unchecked";

                                str = "<ul><li id='" + data.strandsOfTubes[i][0] + "' class='STRAND' style='display:none;'><input type='checkbox' class='FiberStrand checkFilter' checked class='filter'></input> <span class='TreeSpan' style='color:black;'><div style='margin-bottom:-17px;margin-left:6px;font-size:12px; font-weight:bold;color:" + data.strandsOfTubes[i][16] + "'> " + data.strandsOfTubes[i][15] + "</div>  <img src='" + getContext() + "/resources/NetworkImages/strand.png'> " + data.strandsOfTubes[i][13] + " / " + data.strandsOfTubes[i][0] + " </span></li></ul>";
                                $("#" + data.strandsOfTubes[i][11] + "_f").append(str);

                                pathCheckFilter(TargetStrand, "", data.strandsOfTubes[i][0], "14", strandArray, allStrands, directionHashmapStrand, routeDisplayStrand, "", "", "", "");
                                if (data.strandsOfTubes[i][5] != "null") {
                                    strandSrc = data.strandsOfTubes[i][5] + ":" + data.strandsOfTubes[i][7] + ":" + data.strandsOfTubes[i][6];
                                }
                                else {
                                    if (data.strandsOfTubes[i][6].split("_")[0] == "MH" || data.strandsOfTubes[i][6].split("_")[0] == "HH" || data.strandsOfTubes[i][6].split("_")[0] == "DB" || data.strandsOfTubes[i][6].split("_")[0] == "CUST") {
                                        strandSrc = data.strandsOfTubes[i][6] + ":" + data.strandsOfTubes[i][7];
                                    }
                                    else {
                                        strandSrc = data.strandsOfTubes[i][7];
                                    }
                                }
                                window["mapPointsNames_" + data.strandsOfTubes[i][0]].push(strandSrc);

                                if (data.strandsOfTubes[i][8] != "null") {
                                    strandDst = data.strandsOfTubes[i][8] + ":" + data.strandsOfTubes[i][10] + ":" + data.strandsOfTubes[i][9];
                                }
                                else {
                                    if (data.strandsOfTubes[i][9].split("_")[0] == "MH" || data.strandsOfTubes[i][9].split("_")[0] == "HH" || data.strandsOfTubes[i][9].split("_")[0] == "DB" || data.strandsOfTubes[i][9].split("_")[0] == "CUST") {
                                        strandDst = data.strandsOfTubes[i][9] + ":" + data.strandsOfTubes[i][10];
                                    }
                                    else {
                                        strandDst = data.strandsOfTubes[i][10];
                                    }
                                }
                                if (typeof window["Auxpts_Strands" + data.strandsOfTubes[i][0]] != 'undefined') {
                                    for (k = 0;k < window["Auxpts_Strands" + data.strandsOfTubes[i][0]].length;k++) {
                                        myLatLng = new google.maps.LatLng(window["Auxpts_Strands" + data.strandsOfTubes[i][0]][k].aux_Latitude, window["Auxpts_Strands" + data.strandsOfTubes[i][0]][k].aux_Longitude)
                                        window["mapPoints_" + data.strandsOfTubes[i][0]].push(myLatLng);
                                        window["bounds_" + data.strandsOfTubes[i][0]].extend(myLatLng);
                                        window["mapPointsNames_" + data.strandsOfTubes[i][0]].push(window["Auxpts_Strands" + data.strandsOfTubes[i][0]][k].aux_Name);
                                    }
                                }
                                window["mapPointsNames_" + data.strandsOfTubes[i][0]].push(strandDst);

                                lat = window["" + data.strandsOfTubes[i][0]][4];
                                lng = window["" + data.strandsOfTubes[i][0]][3];
                                myLatLng = new google.maps.LatLng(lat, lng);
                                window["mapPoints_" + data.strandsOfTubes[i][0]].push(myLatLng);
                                window["bounds_" + data.strandsOfTubes[i][0]].extend(myLatLng);

                                path = window["mapPoints_" + data.strandsOfTubes[i][0]];
                                buildPath(data.strandsOfTubes[i][0], path, strandArray, allStrands, "Strand", 'purple', 0.7, 2.8, 'purple', 0);
                                strandArray[data.strandsOfTubes[i][0]].setMap(map);

                                Create_FiberStrand(data.strandsOfTubes[i][0]);

                                $("#" + data.strandsOfTubes[i][0] + " > span").bind("contextmenu", function() {
                                    selectedFiberContext = $(this).parents().eq(4).attr('id');
                                    selectedStrand = $(this).parent().attr('id');
                                    selectedTube = $(this).parent().attr('id');
                                    openContext(selectedStrand, "strand", singleStrand, event)
                                });
                                if ($("#strandMapCheck_Labels").prop("checked") == true) {
                                    strandArray[data.strandsOfTubes[i][0]].mapLabel.setMap(map);
                                }
                            }
                        }
                        $("#tubesTable > tbody").empty();
                        $("#strandsTable > tbody").empty();

                    } // finish else condition if dictTubes.length != 0


                    // append the fiber cable data to the boq							
                    $('.tree li#FiberPath_f_' + IdNodeSelectedTemp + ' .TreeSpan').unbind("mouseover");
                    $('.tree li#FiberPath_f_' + IdNodeSelectedTemp + ' .TreeSpan').bind("mouseover", function(e) {
                        $(this).addClass('backgroundTree');
                    }).on("mouseout", function(e) {
                        $(this).removeClass('backgroundTree');

                    });


                    $('.tree li#FiberPath_f_' + IdNodeSelectedTemp + ' .TreeSpan').on('click', function(e) {
                        console.log("IdNodeSelectedTemp hereeeeeeeee" + IdNodeSelectedTemp);
                        //$("#initial_ul_"+IdNodeSelectedTemp+"").find(' > ul > li').css("background-color", "");
                        $(".tree li#FiberPath_f_" + IdNodeSelectedTemp + " span").css("background-color", "");
                        $(this).css("background-color", "#97b9cc");

                        //$("#initial_ul_"+IdNodeSelectedTemp+"").find(' > ul > li').removeClass("selected-span");
                        $(".tree li#FiberPath_f_" + IdNodeSelectedTemp + "  span").removeClass("selected-span");
                        $(this).addClass("selected-span");
                    });

                    /// event to directly display the created fiber cable into the tree if children are displayed
                    var children = $("#FiberPath_f_" + IdNodeSelectedTemp + "").find(' > ul > li');
                    var networkLevelFolder = $("#FiberPath_" + fibernetlevel + "_" + IdNodeSelectedTemp + "").find(' > ul > li');
                    if (!children.is(":visible") || !networkLevelFolder.is(":visible")) {
                        $("#FiberPath_f_" + IdNodeSelectedTemp + "").children('.folder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');
                        $("#initial_ul_" + IdNodeSelectedTemp + "").children('.folder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');
                        $("#FiberPath_" + fibernetlevel + "_" + IdNodeSelectedTemp + "").children('.folder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');
                    }

                    children.show('fast');
                    networkLevelFolder.show('fast');

                    $("#" + data.FiberPathId + " > .TreeSpan ").addClass("selected-span");
                    $("#" + data.FiberPathId + " > .TreeSpan").css("background-color", "#97b9cc");

                    // show the cable in the tree 
                    pathMapListener(data.FiberPathId, "FiberPath_f_");
                    $("#" + data.FiberPathId).children(':checkbox').prop("checked", true);

                    ModalReset("fiberPathModal");

                    if (actionFiberContext == "Update") {
                        $(".tree li > .TreeSpan").on('click', function(e) {
                            IdSelectedTemp = $(this).parent().attr('id');
                            e.stopPropagation();
                        });
                    }
                }
				
				$("#auxiliaryTable > tbody").empty();

                $('.TreeSpan').css("display", "inline");
                data = null
                dict = []
                dictTubes = []
                dictStrands = []
                allAuxDictTube = [];
                allAuxDictStrand = [];
            },
            error: function(result) {
                alert("Error");
            }
        });
        clearCreateFromMap(markerArrayAux);
    }
    EnableOrigination = false;
}