var flag = 0;
function getFiberPath(type, url, id, tr) {
    $('body').append('<div id="loading"><img id="loading-image" src="' + getContext() + '/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if (flag == 0) {

        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/getFiberPath',
            data: {
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    if (data.searchResult != "Failed") {
                        $("#FiberPath_f_CurrentPhysicalLayer input[type=checkbox]").unbind();// removed 
                        CreateFiberPath(data.fiber, data.fiber_Tubes, data.fiber_Strands, data.fiber_Auxiliary, data.tubes_Auxiliaries, data.strands_Auxiliaries);

                        if (type == "showPath") {
                            showPath(url, id, tr);
                        } else {
                            if ($('#FiberPath_f_CurrentPhysicalLayer > .AllFiberCables').is(':checked') || $('.allElements').is(':checked')) {// in order to check all the folders and cables under main fiber folder
                                mainPathCheckFilter($("#" + "FiberPath_f_CurrentPhysicalLayer").children('input'));

                            }
                            if ($('#fiberCheckAllBoq').is(':checked')) {// in order to check all the folder of the boq and cables under main fiber folder
                                fiberLayerCheckAll();
                            }
                            if ($('#tubeCheckAllBoq').is(':checked')) {// in order to check all the folder of the boq and cables under main fiber folder
                                tubeLayerCheckAll();
                            }
                            if ($('#strandCheckAllBoq').is(':checked')) {// in order to check all the folder of the boq and cables under main fiber folder
                                strandLayerCheckAll();
                            }

                        }
                        $('.TreeSpan').css("display", "inline"); // The purpose of this command is to let the background color width in mouse hovering or mouse select to be same span text width
                        treeCollapseFolder("#FiberPath_f_CurrentPhysicalLayer .folder", "fast", ".folder");

                        flag = 1;
                    }
                    $("#loading").remove();
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }
}

function CreateFiberPath(fiberList, fiberTubes, fiberStrands, fiberAuxiliary_Data, tubesAuxiliaries, strandsAuxiliaries) {

    fiberArray = [];
    fiberArrayFilter = [];
    tubeArray = [];
    strandArray = [];
    allFiberCables = [];
    allTubes = [];
    allStrands = [];

    /////////////*********************	FiberCables Creation In tree	***********************///////////////

    if (fiberList != null) {
        for (i = 0;i < fiberList.length;i++) {
            allFiberCables.push(fiberList[i][4]);

            if (fiberList[i][21] == "backbone") {

                if (fiberList[i][11] > 0) {
                    str = "<ul><li id='" + fiberList[i][4] + "' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable checkFilter' class='filter'></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='" + getContext() + "/resources/NetworkImages/fiber.png'>  " + fiberList[i][13] + " / " + fiberList[i][4] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + fiberList[i][4] + "' class='clearPoints'> </span><ul><li id='" + fiberList[i][4] + "_f' style='display:none;'> <input type='checkbox' class='FiberTubes checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Tubes <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes_f" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes_f" + fiberList[i][4] + "' class='clearPoints'></span></li></ul></li></ul>";
                }
                else {
                    str = "<ul><li id='" + fiberList[i][4] + "' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable' class='filter checkFilter'></input> <span class='TreeSpan' style='color:black;width:355px'><img src='" + getContext() + "/resources/NetworkImages/fiber.png'> " + fiberList[i][13] + " / " + fiberList[i][4] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + fiberList[i][4] + "' class='clearPoints'></span></li></ul>";
                }
                $("#FiberPath_backbone__" + fiberList[i][14]).append(str);

            }
            else if (fiberList[i][21] == "metro") {

                if (fiberList[i][11] > 0) {
                    str = "<ul><li id='" + fiberList[i][4] + "' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable checkFilter' class='filter'></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='" + getContext() + "/resources/NetworkImages/fiber.png'>  " + fiberList[i][13] + " / " + fiberList[i][4] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + fiberList[i][4] + "' class='clearPoints'> </span><ul><li id='" + fiberList[i][4] + "_f' style='display:none;'> <input type='checkbox' class='FiberTubes checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Tubes <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes_f" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes_f" + fiberList[i][4] + "' class='clearPoints'></span></li></ul></li></ul>";
                }
                else {
                    str = "<ul><li id='" + fiberList[i][4] + "' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable' class='filter checkFilter'></input> <span class='TreeSpan' style='color:black;width:355px'><img src='" + getContext() + "/resources/NetworkImages/fiber.png'> " + fiberList[i][13] + " / " + fiberList[i][4] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + fiberList[i][4] + "' class='clearPoints'></span></li></ul>";
                }
                $("#FiberPath_metro__" + fiberList[i][14]).append(str);
            }
            else if (fiberList[i][21] == "access") {

                if (fiberList[i][11] > 0) {
                    str = "<ul><li id='" + fiberList[i][4] + "' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable checkFilter' class='filter'></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:355px' class='TreeSpan'><img style='color: #08526D;' src='" + getContext() + "/resources/NetworkImages/fiber.png'>  " + fiberList[i][13] + " / " + fiberList[i][4] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + fiberList[i][4] + "' class='clearPoints'> </span><ul><li id='" + fiberList[i][4] + "_f' style='display:none;'> <input type='checkbox' class='FiberTubes checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span style='color:black;width:315px' class='TreeSpan'>Tubes <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes_f" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes_f" + fiberList[i][4] + "' class='clearPoints'></span></li></ul></li></ul>";
                }
                else {
                    str = "<ul><li id='" + fiberList[i][4] + "' class='FIBER' style='display:none;width:100px;'><input type='checkbox' class='FiberCable' class='filter checkFilter'></input> <span class='TreeSpan' style='color:black;width:355px'><img src='" + getContext() + "/resources/NetworkImages/fiber.png'> " + fiberList[i][13] + " / " + fiberList[i][4] + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTubes" + fiberList[i][4] + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTubes" + fiberList[i][4] + "' class='clearPoints'></span></li></ul>";
                }
                $("#FiberPath_access__" + fiberList[i][14]).append(str);
            }


            ////////**********	Loading all FIBERS with auxiliaries data  ***********//////////

            var fiberId = fiberList[i][4];
            window["" + fiberList[i][4]] = [];
            window["mapPoints_" + fiberList[i][4]] = [];
            // array of fiber auxiliary names

            window["mapPointsNames_" + fiberList[i][4]] = [];
            //Case of wareHouse
            if (fiberList[i][5] && fiberList[i][5] != "null") {
                if (fiberList[i][4] == 'FIBER2025_58') {
                }
                src = fiberList[i][5] + ":" + fiberList[i][7] + ":" + fiberList[i][6];
            }
            else {
                if (fiberList[i][6] && (fiberList[i][6].split("_")[0] == "MH" || fiberList[i][6].split("_")[0] == "HH" || fiberList[i][6].split("_")[0] == "DB" || fiberList[i][6].split("_")[0] == "CUST")) {
                    src = fiberList[i][6] + ":" + fiberList[i][7];
                }
                else {
                    src = fiberList[i][7];
                }
            }
            if (fiberList[i][4] == 'FIBER2025_58') {
                console.log("src is ", src);
            }
            window["mapPointsNames_" + fiberList[i][4]].push(src);

            //Case of wareHouse
            if (fiberList[i][6] && fiberList[i][8] != "null") {
                dst = fiberList[i][8] + ":" + fiberList[i][10] + ":" + fiberList[i][9];
            }
            else {
                if (fiberList[i][9] && (fiberList[i][9].split("_")[0] == "MH" || fiberList[i][9].split("_")[0] == "HH" || fiberList[i][9].split("_")[0] == "DB" || fiberList[i][9].split("_")[0] == "CUST")) {
                    dst = fiberList[i][9] + ":" + fiberList[i][10];
                }
                else {
                    dst = fiberList[i][10];
                }
            }

            window["mapPointsNames_" + fiberList[i][4]].push(dst);


            // fiber boundaries and cable points
            window["" + fiberList[i][4]] = fiberList[i];
            window["bounds_" + fiberList[i][4]] = new google.maps.LatLngBounds();
            var myLatLng = new google.maps.LatLng(fiberList[i][1], fiberList[i][0]);
            window["bounds_" + fiberList[i][4]].extend(myLatLng);
            window["mapPoints_" + fiberList[i][4]].push(myLatLng);

            myLatLng = new google.maps.LatLng(fiberList[i][3], fiberList[i][2]);
            window["bounds_" + fiberList[i][4]].extend(myLatLng);
            window["mapPoints_" + fiberList[i][4]].push(myLatLng);

            STEPS = 100;
            Create_FiberPath(fiberList[i][4]);
            pathCheckFilter(TargetFiber, "", fiberList[i][4], "20", fiberArray, allFiberCables, directionHashmap, routeDisplay, "TUBE", tubeArray, directionHashmapTube, allTubes);
            window['fiberCheckPoints_' + fiberList[i][4]] = "unchecked";
            window['fiberCheckRealPoints_' + fiberList[i][4]] = "unchecked";
            window['fiberCheckSequence_' + fiberList[i][4]] = "unchecked";
            // for seting fiber cable color
            fiberIdList.push(fiberList[i][4]);
            fiberOwnerList.push(fiberList[i][22]);
            window['FiberColor_' + fiberList[i][22]] = fiberList[i][23];

            $("#" + fiberList[i][4] + " > .TreeSpan").bind("contextmenu", function() {
                IdNodeSelectedTemp = $(this).parents().eq(2).attr('id').split("__")[1];
                selectedFiberContext = $(this).parent().attr('id');
                openContext(selectedFiberContext, "fiber", singleFiber, event)
            });

            $("#" + fiberList[i][4] + "_f > span").bind("contextmenu", function() {
                selectedFiberContext = $(this).parents().eq(2).attr('id');
                selectedTube = $(this).parent().attr('id');
                openContext(selectedTube, "", menuTubePath, event)
            });
        }
        pathCheckFilter("initial_ul", "parentFolderCheck", "FiberPath_backbone__CurrentPhysicalLayer", "", "", "", "", "", "", "", "", "");
        pathCheckFilter("initial_ul", "parentFolderCheck", "FiberPath_metro__CurrentPhysicalLayer", "", "", "", "", "", "", "", "", "");
        pathCheckFilter("initial_ul", "parentFolderCheck", "FiberPath_access__CurrentPhysicalLayer", "", "", "", "", "", "", "", "", "");
        pathCheckFilter("initial_ul", "parentFolderCheck", "FiberPath_f_CurrentPhysicalLayer", "", "", "", "", "", "", "", "", "");
    }


    // fiber auxiliary points
    if (fiberAuxiliary_Data != null) {
        for (i = 0;i < fiberAuxiliary_Data.length;i++) {
            myLatLng = new google.maps.LatLng("" + fiberAuxiliary_Data[i][1], "" + fiberAuxiliary_Data[i][0]);

            if (fiberAuxiliary_Data[i][3] == "") {
                auxPoint = "";
            }
            //Case of wareHouse
            else if (fiberAuxiliary_Data[i][3] != "null" && fiberAuxiliary_Data[i][3] != null) {
                auxPoint = fiberAuxiliary_Data[i][3] + ":" + fiberAuxiliary_Data[i][5] + ":" + fiberAuxiliary_Data[i][4];
            }
            else {

                if (fiberAuxiliary_Data[i][4] && (fiberAuxiliary_Data[i][4].split("_")[0] == "MH" || fiberAuxiliary_Data[i][4].split("_")[0] == "HH" || fiberAuxiliary_Data[i][4].split("_")[0] == "DB")) {
                    auxPoint = fiberAuxiliary_Data[i][4] + ":" + fiberAuxiliary_Data[i][5];
                }
                else if (fiberAuxiliary_Data[i][5] && fiberAuxiliary_Data[i][5].includes("Auxiliary_Point") == true) {
                    auxPoint = fiberAuxiliary_Data[i][7] + ":" + fiberAuxiliary_Data[i][5];
                }
                else {
                    auxPoint = fiberAuxiliary_Data[i][5];
                }
            }
            window["mapPointsNames_" + fiberAuxiliary_Data[i][6]].splice(window["mapPointsNames_" + fiberAuxiliary_Data[i][6]].length - 1, 0, auxPoint);// insert at before last index which is the destination
            window["mapPoints_" + fiberAuxiliary_Data[i][6]].splice(window["mapPoints_" + fiberAuxiliary_Data[i][6]].length - 1, 0, myLatLng);// insert at before last index which is the destination
            window["bounds_" + fiberAuxiliary_Data[i][6]].extend(myLatLng);
        }
    }

    if (fiberTubes != null) {
        for (i = 0;i < fiberTubes.length;i++) {
            var tubeId = fiberTubes[i][0];
            var savedTubeId = fiberTubes[i][0];//initialized here and used in save tube function
            allTubes.push(tubeId);
            tubeName = fiberTubes[i][13];
            var tubeNumber = fiberTubes[i][15];
            var tubeColor = fiberTubes[i][16];
            if (fiberTubes[i][11] > 0) {
                str = "<ul><li id='" + tubeId + "' class='TUBE' style='display:none;width:100px;'><input type='checkbox' class='FiberTube checkFilter' class='filter'></input> <span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:274px'> <span style='font-size:12px; font-weight:bold; transform: translateY(-4px); color:" + tubeColor + "'> " + tubeNumber + "</span> <img style='color: #08526D; margin-bottom:-3px; margin-left:-25px' src='" + getContext() + "/resources/NetworkImages/core.png'> " + tubeName + " / " + tubeId + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsStrands" + tubeId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsStrands" + tubeId + "' class='clearPoints'> </span><ul><li id='" + tubeId + "_f' style='display:none;' class='strandsFolder'> <input type='checkbox' class='TubeStrands checkFilter' unchecked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:236px'>Strands </span></li></ul></li></ul>";
            }
            else {
                str = "<ul><li id='" + tubeId + "' class='TUBE' style='display:none;width:100px;'><input type='checkbox' class='FiberTube checkFilter' name='filter'></input> <span class='TreeSpan' style='color:black;width:274px'> <span style='font-size:12px; font-weight:bold; transform: translateY(-4px); color:" + tubeColor + "'> " + tubeNumber + "</span> <img style='color: #08526D; margin-bottom:-3px; margin-left:-25px' src='" + getContext() + "/resources/NetworkImages/core.png'> " + tubeName + " / " + tubeId + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsStrands" + tubeId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsStrands" + tubeId + "' class='clearPoints'> </span></li></ul>";
            }
            $("#" + fiberTubes[i][12] + "_f").append(str);

            pathCheckFilter(TargetTube, "parentFolderCheck", fiberTubes[i][12], "14", tubeArray, allTubes, directionHashmapTube, routeDisplayTube, "STRAND", strandArray, directionHashmapStrand, allStrands);
            pathCheckFilter(TargetTube, "", tubeId, "14", tubeArray, allTubes, directionHashmapTube, routeDisplayTube, "STRAND", strandArray, directionHashmapStrand, allStrands);

            window["Auxpts_Tubes" + tubeId] = [];
            window["" + tubeId] = [];
            window["mapPoints_" + tubeId] = [];
            window["mapPointsNames_" + tubeId] = [];
            window["bounds_" + tubeId] = new google.maps.LatLngBounds();

            // tube auxiliary names and boundaries
            window["" + tubeId] = fiberTubes[i];

            if (fiberTubes[i][5] != "null") {
                tubeSrc = fiberTubes[i][5] + ":" + fiberTubes[i][7] + ":" + fiberTubes[i][6];
            }
            else {
                if (fiberTubes[i][6].split("_")[0] == "MH" || fiberTubes[i][6].split("_")[0] == "HH" || fiberTubes[i][6].split("_")[0] == "DB" || fiberTubes[i][6].split("_")[0] == "CUST") {
                    tubeSrc = fiberTubes[i][6] + ":" + fiberTubes[i][7];
                }
                else {
                    tubeSrc = fiberTubes[i][7];
                }
            }
            window["mapPointsNames_" + tubeId].push(tubeSrc);

            if (fiberTubes[i][8] != "null") {
                tubeDst = fiberTubes[i][8] + ":" + fiberTubes[i][10] + ":" + fiberTubes[i][9];
            }
            else {
                if (fiberTubes[i][9].split("_")[0] == "MH" || fiberTubes[i][9].split("_")[0] == "HH" || fiberTubes[i][9].split("_")[0] == "DB" || fiberTubes[i][9].split("_")[0] == "CUST") {
                    tubeDst = fiberTubes[i][9] + ":" + fiberTubes[i][10];
                }
                else {
                    tubeDst = fiberTubes[i][10];
                }
            }

            window["mapPointsNames_" + tubeId].push(tubeDst);

            var myLatLng = new google.maps.LatLng(fiberTubes[i][2], fiberTubes[i][1]);
            window["bounds_" + tubeId].extend(myLatLng);
            window["mapPoints_" + tubeId].push(myLatLng);

            myLatLng = new google.maps.LatLng(fiberTubes[i][4], fiberTubes[i][3]);
            window["bounds_" + tubeId].extend(myLatLng);
            window["mapPoints_" + tubeId].push(myLatLng);

            Create_FiberTubeClick_Event(tubeId);

            window['tubeCheckPoints_' + tubeId] = "unchecked";
            window['tubeCheckSequence_' + tubeId] = "unchecked";
            window['tubeCheckRealPoints_' + tubeId] = "unchecked";


            $("#" + tubeId + " > span").bind("contextmenu", function() {
                selectedFiberContext = $(this).parents().eq(4).attr('id');
                selectedTube = $(this).parent().attr('id');
                openContext(selectedTube, "tube", singleTube, event)
            });
            $("#" + tubeId + "_f > span").bind("contextmenu", function() {
                selectedFiberContext = $(this).parents().eq(6).attr('id');
                selectedTube = $(this).parents().eq(2).attr('id');
                selectedStrandPath = $(this).parent().attr('id');
                openContext(selectedStrandPath, "", menuStrandPath, event)
            });

        }
    }

    // tube auxiliary points array
    if (typeof tubesAuxiliaries !== 'undefined') {
        for (i = 0;i < tubesAuxiliaries.length;i++) {
            var tubeId = tubesAuxiliaries[i][0];
            var lng = tubesAuxiliaries[i][1];
            var lat = tubesAuxiliaries[i][2];

            if (tubesAuxiliaries[i][3] != "null") {
                var tubeAuxPoint = tubesAuxiliaries[i][3] + ":" + tubesAuxiliaries[i][5] + ":" + tubesAuxiliaries[i][4];
            }
            else {
                if (tubesAuxiliaries[i][4].split("_")[0] == "MH" || tubesAuxiliaries[i][4].split("_")[0] == "HH" || tubesAuxiliaries[i][4].split("_")[0] == "DB") {
                    var tubeAuxPoint = tubesAuxiliaries[i][4] + ":" + tubesAuxiliaries[i][5];
                }
                else if (tubesAuxiliaries[i][5].includes("Auxiliary_Point") == true) {
                    var tubeAuxPoint = tubesAuxiliaries[i][8] + ":" + tubesAuxiliaries[i][5];
                }
                else {
                    var tubeAuxPoint = tubesAuxiliaries[i][5];
                }
            }

            var distanceTube = tubesAuxiliaries[i][6];
            var seqTubeSorting = tubesAuxiliaries[i][7];
            var drivingDistance = tubesAuxiliaries[i][9];
            var geoDistance = tubesAuxiliaries[i][10];

            window["Auxpts_Tubes" + tubeId].push({
                "tubeId": tubeId,
                "aux_Name": tubeAuxPoint,
                "aux_Longitude": lng,
                "aux_Latitude": lat,
                "distance_From_Source": distanceTube,
                "seqSorting": seqTubeSorting,
                "drivingDistance": drivingDistance,
                "geoDistance": geoDistance
            });


            myLatLng = new google.maps.LatLng(lat, lng);
            window["mapPointsNames_" + tubeId].splice(window["mapPointsNames_" + tubeId].length - 1, 0, tubeAuxPoint);// insert at before last index which is the destination
            window["mapPoints_" + tubeId].splice(window["mapPoints_" + tubeId].length - 1, 0, myLatLng);// insert at before last index which is the destination

        }
    }

    /////////////*********************	Strands Creation In tree	***********************///////////////

    if (fiberStrands != null) {
        for (i = 0;i < fiberStrands.length;i++) {

            var strandId = fiberStrands[i][0];
            var savedStrandId = fiberStrands[i][0];//initialized here and used in save strand function
            allStrands.push(fiberStrands[i][0]);
            var strandNumber = fiberStrands[i][15];
            var strandColor = fiberStrands[i][16];

            str = "<ul><li id='" + fiberStrands[i][0] + "' class='STRAND' style='display:none;width:100px;'><input type='checkbox' class='FiberStrand checkFilter' class='filter'></input> <span  class='TreeSpan' style='color:black;width:195px'> <span style='font-size:12px; font-weight:bold; transform: translateY(-4px); color:" + strandColor + "'> " + strandNumber + "</span> <img style='margin-bottom:-3px; margin-left:-25px' src='" + getContext() + "/resources/NetworkImages/strand.png'> " + fiberStrands[i][13] + " / " + fiberStrands[i][0] + " </span></li></ul>";
            $("#" + fiberStrands[i][11] + "_f").append(str);

            pathCheckFilter(TargetStrand, "parentFolderCheck", fiberStrands[i][11], "14", strandArray, allStrands, directionHashmapStrand, routeDisplayStrand, "", "", "", "");
            pathCheckFilter(TargetStrand, "", fiberStrands[i][0], "14", strandArray, allStrands, directionHashmapStrand, routeDisplayStrand, "", "", "", "");

            window["Auxpts_Strands" + fiberStrands[i][0]] = [];
            window["" + fiberStrands[i][0]] = [];
            window["mapPoints_" + fiberStrands[i][0]] = [];
            window["mapPointsNames_" + fiberStrands[i][0]] = [];
            window["bounds_" + fiberStrands[i][0]] = new google.maps.LatLngBounds();

            window["" + fiberStrands[i][0]] = fiberStrands[i];

            if (fiberStrands[i][5] != "null") {
                strandSrc = fiberStrands[i][5] + ":" + fiberStrands[i][7] + ":" + fiberStrands[i][6];
            }
            else {
                if (fiberStrands[i][6].split("_")[0] == "MH" || fiberStrands[i][6].split("_")[0] == "HH" || fiberStrands[i][6].split("_")[0] == "DB" || fiberStrands[i][6].split("_")[0] == "CUST") {
                    strandSrc = fiberStrands[i][6] + ":" + fiberStrands[i][7];
                }
                else {
                    strandSrc = fiberStrands[i][7];
                }
            }
            window["mapPointsNames_" + fiberStrands[i][0]].push(strandSrc);

            if (fiberStrands[i][8] != "null") {
                strandDst = fiberStrands[i][8] + ":" + fiberStrands[i][10] + ":" + fiberStrands[i][9];
            }
            else {
                if (fiberStrands[i][9].split("_")[0] == "MH" || fiberStrands[i][9].split("_")[0] == "HH" || fiberStrands[i][9].split("_")[0] == "DB" || fiberStrands[i][9].split("_")[0] == "CUST") {
                    strandDst = fiberStrands[i][9] + ":" + fiberStrands[i][10];
                }
                else {
                    strandDst = fiberStrands[i][10];
                }
            }
            window["mapPointsNames_" + fiberStrands[i][0]].push(strandDst);

            // array of each array boundaries 
            var myLatLng = new google.maps.LatLng(fiberStrands[i][2], fiberStrands[i][1]);
            window["bounds_" + fiberStrands[i][0]].extend(myLatLng);
            window["mapPoints_" + fiberStrands[i][0]].push(myLatLng);
            myLatLng = new google.maps.LatLng(fiberStrands[i][4], fiberStrands[i][3]);
            window["bounds_" + fiberStrands[i][0]].extend(myLatLng);
            window["mapPoints_" + fiberStrands[i][0]].push(myLatLng);
            Create_FiberStrand(fiberStrands[i][0]);
            window['strandCheckPoints_' + fiberStrands[i][0]] = "unchecked";
            window['strandCheckSequence_' + fiberStrands[i][0]] = "unchecked";
            window['strandCheckRealPoints_' + fiberStrands[i][0]] = "unchecked";


            $("#" + fiberStrands[i][0] + " > span").bind("contextmenu", function() {
                selectedFiberContext = $(this).parents().eq(4).attr('id');
                selectedStrand = $(this).parent().attr('id');
                selectedTube = $(this).parents().eq(2).attr('id');
                selectedFiberMain = $(this).parents().eq(6).attr('id').split("_f")[0];
                openContext(selectedStrand, "strand", singleStrand, event)
            });
        }
    }

    if (strandsAuxiliaries) {
        for (i = 0;i < strandsAuxiliaries.length;i++) {
            var strandId = strandsAuxiliaries[i][0];
            var lng = strandsAuxiliaries[i][1];
            var lat = strandsAuxiliaries[i][2];

            if (strandsAuxiliaries[i][3] != "null") {
                var strandAuxPoint = strandsAuxiliaries[i][3] + ":" + strandsAuxiliaries[i][5] + ":" + strandsAuxiliaries[i][4];
            }
            else {
                if (strandsAuxiliaries[i][4].split("_")[0] == "MH" || strandsAuxiliaries[i][4].split("_")[0] == "HH" || strandsAuxiliaries[i][4].split("_")[0] == "DB") {
                    var strandAuxPoint = strandsAuxiliaries[i][4] + ":" + strandsAuxiliaries[i][5];
                }
                else if (strandsAuxiliaries[i][5].includes("Auxiliary_Point") == true) {
                    var strandAuxPoint = strandsAuxiliaries[i][8] + ":" + strandsAuxiliaries[i][5];
                }
                else {
                    var strandAuxPoint = strandsAuxiliaries[i][5];
                }
            }

            var distanceStrand = strandsAuxiliaries[i][6];
            var seqStrandSorting = strandsAuxiliaries[i][7];
            var drivingDistance = strandsAuxiliaries[i][9];
            var geoDistance = strandsAuxiliaries[i][10];


            myLatLng = new google.maps.LatLng(lat, lng);
            window["mapPointsNames_" + strandId].splice(window["mapPointsNames_" + strandId].length - 1, 0, strandAuxPoint);// insert at before last index which is the destination
            window["mapPoints_" + strandId].splice(window["mapPoints_" + strandId].length - 1, 0, myLatLng);// insert at before last index which is the destination

            // strands auxiliary points
            window["Auxpts_Strands" + strandId].push({
                "strandId": strandId,
                "aux_Name": strandAuxPoint,
                "aux_Longitude": lng,
                "aux_Latitude": lat,
                "distance_From_Source": distanceStrand,
                "seqSorting": seqStrandSorting,
                "drivingDistance": drivingDistance,
                "geoDistance": geoDistance
            });
        }
    }
    fiberList = [], fiberTubes = [], fiberStrands = [], fiberAuxiliary_Data = [], tubesAuxiliaries = [], strandsAuxiliaries = [];
    filterMap_CableLabels("FIBER", fiberArray, "fiberMapCheck_Labels");
    filterMap_CableLabels("TUBE", tubeArray, "tubeMapCheck_Labels");
    filterMap_CableLabels("STRAND", strandArray, "strandMapCheck_Labels")
}