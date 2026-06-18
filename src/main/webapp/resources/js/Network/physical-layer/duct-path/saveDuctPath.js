function saveDuctPath() {
	console.log("Welcome to saveDuctPath");
    ductsLength = $("#ductsLength").val();
    checkLongLatInRows("auxiliary_ductTable", "auxiliary_LongitudeDuct", "auxiliary_LatitudeDuct");

    if ($("#SourceDuct").val() == "" || $("#DestinationDuct").val() == "") {
        alert("Missing fields!");
    }
    else if (!isInDesiredForm($("#ductsFiberCables").val()) || !isInDesiredForm($("#ductsTubes").val()) || !isInDesiredForm($("#ductsStrands").val())) {
        alert("Incorrect data format!");
    }
    else if (isNaN(ductsLength) == true || $("#ductsLength").val() == "") {
        alert('Duct length must be a number');
        return false;
    }
    else {
        if ($("#SourceDuct").val() != "" && $("#DestinationDuct").val() != "" && $("#ductName").val() == "") {
            var automaticName = $("#SourceDuct").val().split(":")[1] + "_" + $("#DestinationDuct").val().split(":")[1];
            $("#ductName").val(automaticName);
        }

        ductName = document.getElementById("ductName").value;
        ductsFiberCables = document.getElementById("ductsFiberCables").value;
        ductsLength = document.getElementById("ductsLength").value;
        ductsStrands = document.getElementById("ductsStrands").value;
        ductsTubes = document.getElementById("ductsTubes").value;
        SourceDuct = document.getElementById("SourceDuct").value;
        DestinationDuct = document.getElementById("DestinationDuct").value;
        SourceDuctLng = document.getElementById("SourceDuctLng").value;
        DestinationDuctLng = document.getElementById("DestinationDuctLng").value;
        SourceDuctLat = document.getElementById("SourceDuctLat").value;
        DestinationDuctLat = document.getElementById("DestinationDuctLat").value;
        srcCityDuct = document.getElementById("srcCityDuct").value;
        dstCityDuct = document.getElementById("dstCityDuct").value;
        ductCreatedByUser = document.getElementById("crtdByDuct").value;
        ductModifiedByUser = lstModfUser;
        ductAuxFlag = document.querySelector("#ductAuxFlag").value
        ductCreatedDate = document.getElementById("popupCreateDateDuct").value;
        trenchId = selectedTrenchContext;
        var savedDuctId = document.getElementById("ductId").value;
        totalDrivingDistance = document.getElementById("ductTotalDistanceDrivg").value;
        totalGeoDistance = document.getElementById("totalGeoDistanceDuct").value;
        lastAuxToDestDistance = document.getElementById("distanceLstAuxToDestDuct").value;
        lastAuxToDestDrivDistance = document.getElementById("ductDistanceLstAuxToDestDrivg").value;
        drawingType = document.getElementById("ductDrawingBy").value;
        ductOwner = document.getElementById("ductOwner").value;
        ductInstaller = document.getElementById("ductInstaller").value;
        ductEngineerName = document.getElementById("ductEngineerName").value;

        getSelectedDuctAux(SourceDuctLat, SourceDuctLng);
        var token = $('input[name="csrfToken"]').attr('value');

        $(".origination,.termination").addClass('disabled');

        $.ajax({
            type: "POST",
            headers: {
                'X-CSRFToken': token
            },
            url: getContext() + '/saveDuct',
            async: false,
            data: {
                "ductID": savedDuctId,
                "trenchId": trenchId,
                "ductName": ductName,
                "ductsFiberCables": ductsFiberCables,
                "ductsLength": ductsLength,
                "ductsStrands": ductsStrands,
                "ductsTubes": ductsTubes,
                "SourceDuct": SourceDuct,
                "DestinationDuct": DestinationDuct,
                "SourceDuctLng": SourceDuctLng,
                "DestinationDuctLng": DestinationDuctLng,
                "SourceDuctLat": SourceDuctLat,
                "DestinationDuctLat": DestinationDuctLat,
                "srcCityDuct": srcCityDuct,
                "dstCityDuct": dstCityDuct,
                "actionDuctContext": actionDuctContext,
                "dictParameter": dict,
                "ductCreatedByUser": ductCreatedByUser,
                "ductModifiedByUser": ductModifiedByUser,
                "ductCreatedDate": ductCreatedDate,
                "ductAuxFlag": ductAuxFlag,
                "totalDrivingDistance": totalDrivingDistance,
                "totalGeoDistance": totalGeoDistance,
                "lastAuxToDestDistance": lastAuxToDestDistance,
                "lastAuxToDestDrivDistance": lastAuxToDestDrivDistance,
                "drawingType": drawingType,
                "ductOwner": ductOwner,
                "ductEngineerName": ductEngineerName,
                "ductInstaller": ductInstaller
            },
            dataType: "json",
            success: function(data) {

                if (data != null) {

                    // Case of save new duct
                    if (savedDuctId == "") {

                        //if more than 1 duct exist under the ducts folder( No need to append the ducts folder (the ducts folder already exists) ) 
                        if ($("#" + selectedTrenchContext + "_f").find('ul').length > 0) {
                            str = "<ul><li id='" + data.ductId + "' class='Duct' style='display:none;width:100px;'><input type='checkbox' class='DUCT checkFilter' unchecked class='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/duct.png' style='opacity:0.6' > " + ductName + " / " + data.ductId + " </span></li></ul>";
                            $("#" + selectedTrenchContext + "_f").append(str);
                        }

                        //No previous duct exist
                        else {
                            // Append a folder beside the trench
                            $("<span class='folder' > <i class='fa fa-folder-open' style='color: #08526D'></i></span>").insertBefore("#" + selectedTrenchContext + "> .TreeSpan");

                            // Append Ducts folder 
                            str = "<ul><li id='" + selectedTrenchContext + "_f' class='ductsFolder'> <input type='checkbox' class='trenchDucts checkFilter' checked name='filter'></input> <span  class='folder' ><i class='fa fa-folder-open' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:236px'>Ducts </span></li></ul></li></ul>";
                            $("#" + selectedTrenchContext).append(str);


                            //Append the saved duct to the Ducts folder created above
                            str = "<ul><li id='" + data.ductId + "' class='Duct' style='display:none;width:100px;'><input type='checkbox' class='DUCT checkFilter' checked class='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/duct.png' style='opacity:0.6' > " + ductName + " / " + data.ductId + " </span></li></ul>";
                            $("#" + selectedTrenchContext + "_f").append(str);

                            pathCheckFilter(TargetDuct, "parentFolderCheck", selectedTrenchContext, "14", ductArray, allDucts, directionHashmapDuct, routeDisplayDuct, "", "", "", "");


                            $("#" + trenchId + "_f > .TreeSpan").contextmenu(function() {
                                menuName = menuDucts;
                                selectedTrenchContext = $(this).parents().eq(2).attr('id');
                                IdNodeSelectedTemp = $(this).parents().eq(4).attr('id').split("Trench_f_")[1];
                                openContext("", "", menuDucts, event);
                            });

                        }

                        $("#" + data.ductId + " > .TreeSpan").contextmenu(function() {
                            menuName = menuSingleDuct;
                            selectedTrenchContext = $(this).parents().eq(2).attr('id').split("_f")[0];
                            selectedDuctContext = $(this).parent().attr('id');
                            openContext(selectedDuctContext, "duct", menuSingleDuct, event);
                        });

                        pathCheckFilter(TargetDuct, "", data.ductId, "19", ductArray, allDucts, directionHashmapDuct, routeDisplayDuct, "", "", "", "");

                        window['ductCheckPoints_' + data.ductId] = "unchecked";
                        window['ductCheckSequence_' + data.ductId] = "unchecked";
                        window['ductCheckRealPoints_' + data.ductId] = "unchecked";
                    }
                    // Case of update duct
                    else {
                        $("#" + data.ductId).children(':checkbox').prop("checked", true);
                        $("#" + data.ductId + "> .TreeSpan").html("<img class='image' src='" + getContext() + "/resources/NetworkImages/duct.png' style='opacity:0.6'  >  " + ductName + " / " + data.ductId + "");
                    }

                    window["" + data.ductId] = [];
                    window["mapPoints_" + data.ductId] = [];
                    window["mapPointsNames_" + data.ductId] = [];
                    window["bounds_" + data.ductId] = new google.maps.LatLngBounds();

                    if (SourceDuct.includes("WARE") == true) {
                        var srcWareId = SourceDuct.split(":")[0];
                        var srcId = SourceDuct.split(":")[2];
                        var srcName = SourceDuct.split(":")[1];
                    }
                    else if (SourceDuct.includes("CUST") == true) {
                        var srcWareId = "null";
                        var srcId = SourceDuct.split(":")[0];
                        var srcName = SourceDuct.split(":")[1];
                    }
                    else if (SourceDuct.includes("MH") == true || SourceDuct.includes("HH") == true || SourceDuct.includes("DB") == true) {
                        var srcWareId = "null";
                        var srcId = SourceDuct.split(":")[0];
                        var srcName = SourceDuct.split(":")[1];
                    }
                    else {
                        var srcWareId = "null";
                        var srcId = "null";
                        var srcName = SourceDuct;
                    }

                    if (DestinationDuct.includes("WARE") == true) {
                        var dstWareId = DestinationDuct.split(":")[0];
                        var dstId = DestinationDuct.split(":")[2];
                        var dstName = DestinationDuct.split(":")[1];
                    }
                    else if (DestinationDuct.includes("CUST") == true) {
                        var dstWareId = "null";
                        var dstId = DestinationDuct.split(":")[0];
                        var dstName = DestinationDuct.split(":")[1];
                    }
                    else if (DestinationDuct.includes("MH") == true || DestinationDuct.includes("HH") == true || DestinationDuct.includes("DB") == true) {
                        var dstWareId = "null";
                        var dstId = DestinationDuct.split(":")[0];
                        var dstName = DestinationDuct.split(":")[1];
                    }
                    else {
                        var dstWareId = "null";
                        var dstId = "null";
                        var dstName = DestinationDuct;
                    }

                    window["" + data.ductId] = [data.ductId, ductName, srcWareId, srcId, srcName, dstWareId, dstId, dstName, SourceDuctLat, SourceDuctLng, DestinationDuctLng, DestinationDuctLat, srcCityDuct, dstCityDuct, ductsFiberCables, ductsTubes, ductsStrands, ductsLength, trenchId, drawingType, ductOwner, ductInstaller, ductEngineerName];
                    window["mapPoints_" + data.ductId].push(new google.maps.LatLng(SourceDuctLat, SourceDuctLng));
                    window["bounds_" + data.ductId].extend(new google.maps.LatLng(SourceDuctLat, SourceDuctLng));
                    window["mapPointsNames_" + data.ductId].push(SourceDuct);

                    if (typeof data.ductAuxiliaryList !== 'undefined') {
                        if (data.ductAuxiliaryList.length != 0) {
                            for (i = 0;i < data.ductAuxiliaryList.length;i++) {
                                var lng = data.ductAuxiliaryList[i][0];
                                var lat = data.ductAuxiliaryList[i][1];
                                if (data.ductAuxiliaryList[i][3] != "null") {
                                    var ductAuxPoint = data.ductAuxiliaryList[i][3] + ":" + data.ductAuxiliaryList[i][5] + ":" + data.ductAuxiliaryList[i][4];
                                }
                                else {
                                    if (data.ductAuxiliaryList[i][4].split("_")[0] == "MH" || data.ductAuxiliaryList[i][4].split("_")[0] == "HH" || data.ductAuxiliaryList[i][4].split("_")[0] == "DB") {
                                        var ductAuxPoint = data.ductAuxiliaryList[i][4] + ":" + data.ductAuxiliaryList[i][5];
                                    }
                                    else if (data.ductAuxiliaryList[i][5].includes("Auxiliary_Point") == true) {
                                        var ductAuxPoint = data.ductAuxiliaryList[i][10] + ":" + data.ductAuxiliaryList[i][5];
                                    }
                                    else {
                                        var ductAuxPoint = data.ductAuxiliaryList[i][5];
                                    }
                                }
                                window["mapPoints_" + data.ductId].push(new google.maps.LatLng(lat, lng));
                                window["bounds_" + data.ductId].extend(new google.maps.LatLng(lat, lng));
                                window["mapPointsNames_" + data.ductId].push(ductAuxPoint);
                            }
                        }
                    }
                    ductAuxPoint = "";

                    window["mapPoints_" + data.ductId].push(new google.maps.LatLng(DestinationDuctLat, DestinationDuctLng));
                    window["bounds_" + data.ductId].extend(new google.maps.LatLng(DestinationDuctLat, DestinationDuctLng));
                    window["mapPointsNames_" + data.ductId].push(DestinationDuct);

                    if ($("#ductDrawingBy").val() == "DRIVING") {
                        if (ductArray[data.ductId]) {
                            ductArray[data.ductId].setMap(null);
                        }
                        if (directionHashmapDuct.get(data.ductId) != undefined) {
                            for (ii = 0;ii < directionHashmapDuct.get(data.ductId).length;ii++) {
                                directionHashmapDuct.get(data.ductId)[ii].setMap(null);
                            }
                        }
                        buildDrivingPath(data.ductId, window["mapPoints_" + data.ductId], "duct", "#8e8080", directionHashmapDuct);
                    }
                    else {
                        if (directionHashmapDuct.get(data.ductId) != undefined) {
                            for (ii = 0;ii < directionHashmapDuct.get(data.ductId).length;ii++) {
                                directionHashmapDuct.get(data.ductId)[ii].setMap(null);
                            }
                        }

                        if (ductArray[data.ductId]) {
                            ductArray[data.ductId].setPath(window["mapPoints_" + data.ductId]);
                            ductArray[data.ductId].setMap(map);
                        }
                        else {
                            buildPath(data.ductId, window["mapPoints_" + data.ductId], ductArray, allDucts, "Duct", '#8e8080', 0.6, 10, '#8e8080', 1);
                            ductArray[data.ductId].setMap(map);
                        }
                    }

                    Create_Duct(data.ductId);
                    $("#" + data.ductId + "> .TreeSpan").css("display", "inline");
                    $("#ductCheckAllBoq").prop('checked', true);
                    map.fitBounds(window["bounds_" + data.ductId]);

                    ModalReset("ductModal");
                    /*
                                        $('.tree li#Trench_f_' + IdNodeSelectedTemp + ' .TreeSpan').unbind("mouseover");
                                        $('.tree li#Trench_f_' + IdNodeSelectedTemp + ' .TreeSpan').bind("mouseover", function(e) {
                                            $(this).addClass('backgroundTree');
                                        }).on("mouseout", function(e) {
                                            $(this).removeClass('backgroundTree');
                    
                                        });
                    
                                        $('.tree li#Trench_f_' + IdNodeSelectedTemp + ' .TreeSpan').on('click', function(e) {
                                            $(".tree li#Trench_f_" + IdNodeSelectedTemp + " span").css("background-color", "");
                                            $(this).css("background-color", "#97b9cc");
                    
                                            $(".tree li#Trench_f_" + IdNodeSelectedTemp + "  span").removeClass("selected-span");
                                            $(this).addClass("selected-span");
                                        });
                    */
                    pathMapListener(data.ductId, "Duct");

                    //window["countDuct"+trenchId]=null;
                    $("#" + data.ductId).children(':checkbox').prop("checked", true);
                }
                const tbody = $("#auxiliary_ductTable tbody")[0];
                tbody.innerHTML = "";
                dict = [];
				data = null;
            },
            error: function(result) {
                alert("Error: " + result);
            }
        });
        clearCreateFromMap(markerArrayAux);
    }
}
