function saveTrenchPath() {

    trenchLength = $("#trenchLength").val();
    checkLongLatInRows("auxiliary_trenchTable", "auxiliaryTrench_Longitude", "auxiliaryTrench_Latitude");

    if ($("#SourceTrench").val() == "" || $("#DestinationTrench").val() == "" || $("#trenchName").val() == "") {
        alert("Missing fields!");
    }
    else if (!isInDesiredForm($("#NumDucts").val()) || !isInDesiredForm($("#trenchCapacity").val())) {
        alert("Incorrect data format!");
    }
    else if (isNaN(trenchLength) == true || $("#trenchLength").val() == "") {
        alert('Trench length must be a number.');
        return false;
    }
    else {
        if ($("#SourceTrench").val() != "" && $("#DestinationTrench").val() != "" && $("#trenchName").val() == "") {
            var automaticName = $("#SourceTrench").val().split(":")[1] + "_" + $("#DestinationTrench").val().split(":")[1];
            $("#trenchName").val(automaticName);
        }

        trenchName = document.getElementById("trenchName").value;
        trenchCapacity = document.getElementById("trenchCapacity").value;
        trenchLength = document.getElementById("trenchLength").value;
        NumDucts = 0;
        SourceTrench = document.getElementById("SourceTrench").value;
        DestinationTrench = document.getElementById("DestinationTrench").value;
        SourceTrenchLng = document.getElementById("SourceTrenchLng").value;
        DestinationTrenchLng = document.getElementById("DestinationTrenchLng").value;
        SourceTrenchLat = document.getElementById("SourceTrenchLat").value;
        DestinationTrenchLat = document.getElementById("DestinationTrenchLat").value;
        srcCityTrench = document.getElementById("srcCityTrench").value;
        dstCityTrench = document.getElementById("dstCityTrench").value;
        trenchCreatedByUser = document.getElementById("crtdByTrench").value;
        trenchOwner = document.getElementById("trenchOwner").value;
        trenchEngineerName = document.getElementById("trenchEngineerName").value;
        trenchInstaller = document.getElementById("trenchInstaller").value;
        trenchModifiedByUser = lstModfUser;
        savedTrenchId = document.getElementById("trenchId").value;
        trenchAuxFlag = document.querySelector("#trenchAuxFlag").value
        trenchCreatedDate = document.getElementById("popupCreateDateTrench").value;
        totalDrivingDistance = document.getElementById("trenchTotalDistanceDrivg").value;
        totalGeoDistance = document.getElementById("totalGeoDistanceTrench").value;
        lastAuxToDestDistance = document.getElementById("distanceLstAuxToDestTrench").value;
        lastAuxToDestDrivDistance = document.getElementById("trenchDistanceLstAuxToDestDrivg").value;
        drawingType = document.getElementById("trenchDrawingBy").value;


        getSelectedTrenchAux(SourceTrenchLat, SourceTrenchLng);
        var token = $('input[name="csrfToken"]').attr('value');

        if (actionTrenchContext == "Update") {
            NumDucts = $("#" + $("#trenchId").val()).find(">ul >li >ul >li ").length;
        }

        $(".origination,.termination").addClass('disabled');
        var token = $('input[name="csrfToken"]').attr('value');

        $.ajax({
            type: "POST",
            headers: {
                'X-CSRFToken': token
            },
            url: getContext() + '/saveTrench',
            async: false,
            data: {
                "trenchID": savedTrenchId,
                "trenchName": trenchName,
                "trenchCapacity": trenchCapacity,
                "trenchLength": trenchLength,
                "NumDucts": NumDucts,
                "SourceTrench": SourceTrench,
                "DestinationTrench": DestinationTrench,
                "SourceTrenchLng": SourceTrenchLng,
                "DestinationTrenchLng": DestinationTrenchLng,
                "SourceTrenchLat": SourceTrenchLat,
                "dictParameter": dict,
                "DestinationTrenchLat": DestinationTrenchLat,
                "srcCityTrench": srcCityTrench,
                "dstCityTrench": dstCityTrench,
                "actionTrenchContext": actionTrenchContext,
                "ProjectId": IdNodeSelectedTemp,
                "trenchCreatedByUser": trenchCreatedByUser,
                "trenchModifiedByUser": trenchModifiedByUser,
                "trenchAuxFlag": trenchAuxFlag,
                "trenchCreatedDate": trenchCreatedDate,
                "totalDrivingDistance": totalDrivingDistance,
                "totalGeoDistance": totalGeoDistance,
                "lastAuxToDestDistance": lastAuxToDestDistance,
                "lastAuxToDestDrivDistance": lastAuxToDestDrivDistance,
                "drawingType": drawingType,
                "trenchOwner": trenchOwner,
                "trenchEngineerName": trenchEngineerName,
                "trenchInstaller": trenchInstaller
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
					const trenchId = data.trenchId;
                    //Save trench case
                    if (savedTrenchId == "") {

                        str = "<ul><li id='" + data.trenchId + "'  class='Trench' style='display:none;width:100px;'><input type='checkbox' class='TRENCH checkFilter' unchecked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/trench.png' style='opacity:0.6'> " + trenchName + " / " + data.trenchId + " <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTrench" + data.trenchId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTrench" + data.trenchId + "' class='clearPoints'> </span></li></ul>";
                        $("#Trench_f_" + IdNodeSelectedTemp + "").append(str);

                        window['trenchCheckPoints_' + data.trenchId] = "unchecked";
                        window['trenchCheckSequence_' + data.trenchId] = "unchecked";
                        window['trenchCheckRealPoints_' + data.trenchId] = "unchecked";
						
						console.log("Just before add contextmenu of saving new trench");

                        $("#" + data.trenchId + " > .TreeSpan").contextmenu(function() {
                            //selectedTrenchContext = data.trenchId;
							selectedTrenchContext = trenchId;
                            menuName = singleTrench;
                            openContext(selectedTrenchContext, "trench", singleTrench, event);
                        });
                        pathCheckFilter(TargetTrench, "", data.trenchId, "18", trenchArray, allTrenches, directionHashmapTrench, routeDisplayTrench, "Duct", ductArray, directionHashmapDuct, allDucts);
                    }
                    //Update trench case
                    else {

                        $("#" + data.trenchId + "> .TreeSpan").html("<img src='" + getContext() + "/resources/NetworkImages/trench.png' style='opacity:0.6' >  " + trenchName + " / " + data.trenchId + "  <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTrench" + data.trenchId + "' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTrench" + data.trenchId + "' class='clearPoints'>  ");

                        // For update (To hide the duct from Map if exist)
                        $("#" + data.trenchId).find(' > ul > li >ul >li').each(function() {
                            var ductId = $(this).attr('id');
                            if (ductArray[ductId]) {
                                ductArray[ductId].setMap(null);
                            }

                        });


                        NumDucts = $("#" + data.trenchId).find(">ul >li >ul >li ").length;
                        if (NumDucts > 0) {
                            $("#" + data.trenchId + "_f > .folder").unbind('click');
                            $("#" + data.trenchId + "> .folder").remove();
                            $("<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span>").insertBefore("#" + data.trenchId + "> .TreeSpan");

                            $("#" + data.trenchId + "> ul").remove();

                            //Append the duct Folder to the saved trench 
                            str = "<ul><li id='" + data.trenchId + "_f' style='display:none;' class='ductsFolder'> <input type='checkbox' class='trenchDucts checkFilter' checked name='filter'></input> <span  class='folder' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:236px'> Ducts <img src='" + getContext() + "/resources/NetworkImages/check.png' hidden style='margin-left:60px' id='pushPointsTrench" + data.trenchId + "_f' class='pushPoints'> <img src='" + getContext() + "/resources/NetworkImages/remove.png' hidden style='margin-left:10px' id='cancelPointsTrench" + data.trenchId + "_f' class='clearPoints'></span></li></ul>";
                            $("#" + data.trenchId).append(str);

                            $("#" + data.trenchId + "_f > .TreeSpan").contextmenu(function() {
                                menuName = menuDucts;
                                selectedTrenchContext = $(this).parents().eq(2).attr('id');
                                IdNodeSelectedTemp = $(this).parents().eq(4).attr('id').split("Trench_f_")[1];
                                openContext("", "", menuDucts, event);
                            });

                            pathCheckFilter(TargetDuct, "parentFolderCheck", data.trenchId, "14", ductArray, allDucts, directionHashmapDuct, routeDisplayDuct, "", "", "", "");

                            // Check the checkbox of duct folder
                            $("#" + data.trenchId).find(' > ul > li').each(function() {
                                $(this).find('input:checkbox:first').prop('checked', true);
                            });

                            $("#ductCheckAllBoq").prop('checked', true);

                            //Append the ducts related to the saved trench 
                            for (i = 0;i < data.ductList.length;i++) {
                                str = "<ul><li id='" + data.ductList[i][0] + "'  class='Duct' style='display:none;width:100px;'><input type='checkbox' class='DUCT checkFilter' unchecked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/duct.png' style='opacity:0.6'> " + data.ductList[i][1] + " / " + data.ductList[i][0] + "</span></li></ul>";
                                $("#" + data.ductList[i][18] + "_f").append(str);

                                //Add the right click menu to the appended duct
                                $("#" + data.ductList[i][0] + " > .TreeSpan").bind("contextmenu", function() {
                                    menuName = menuSingleDuct;
                                    selectedTrenchContext = $(this).parents().eq(2).attr('id').split("_f")[0];
                                    selectedDuctContext = $(this).parent().attr('id');
                                    IdNodeSelectedTemp = $(this).parents().eq(6).attr('id').split("Trench_f_")[1];
                                    openContext(selectedDuctContext, "duct", menuSingleDuct, event);
                                });
                                // Check the checkbox of appended duct 
                                $("#" + data.ductList[i][0]).children(':checkbox').prop("checked", true);

                                // Create the duct path and add it to map
                                buildPath(data.ductList[i][0], window["mapPoints_" + data.ductList[i][0]], ductArray, allDucts, "Duct", '#8e8080', 0.6, 10, '#8e8080', 1);
                                ductArray[data.ductList[i][0]].setMap(map);
                                Create_Duct(data.ductList[i][0]);

                                pathCheckFilter(TargetDuct, "", data.ductList[i][0], "19", ductArray, allDucts, directionHashmapDuct, routeDisplayDuct, "", "", "", "");
                            }
                        }
                    }
                    window["" + data.trenchId] = [];
                    window["mapPoints_" + data.trenchId] = [];
                    window["mapPointsNames_" + data.trenchId] = [];
                    window["bounds_" + data.trenchId] = new google.maps.LatLngBounds();

                    if (SourceTrench.includes("WARE") == true) {
                        var srcWareId = SourceTrench.split(":")[0];
                        var srcId = SourceTrench.split(":")[2];
                        var srcName = SourceTrench.split(":")[1];
                    }
                    else if (SourceTrench.includes("CUST") == true) {
                        var srcWareId = "null";
                        var srcId = SourceTrench.split(":")[0];
                        var srcName = SourceTrench.split(":")[1];
                    }
                    else if (SourceTrench.includes("MH") == true || SourceTrench.includes("HH") == true || SourceTrench.includes("DB") == true) {
                        var srcWareId = "null";
                        var srcId = SourceTrench.split(":")[0];
                        var srcName = SourceTrench.split(":")[1];
                    }
                    else {
                        var srcWareId = "null";
                        var srcId = "null";
                        var srcName = SourceTrench;
                    }

                    if (DestinationTrench.includes("WARE") == true) {
                        var dstWareId = DestinationTrench.split(":")[0];
                        var dstId = DestinationTrench.split(":")[2];
                        var dstName = DestinationTrench.split(":")[1];
                    }
                    else if (DestinationTrench.includes("CUST") == true) {
                        var dstWareId = "null";
                        var dstId = DestinationTrench.split(":")[0];
                        var dstName = DestinationTrench.split(":")[1];
                    }
                    else if (DestinationTrench.includes("MH") == true || DestinationTrench.includes("HH") == true || DestinationTrench.includes("DB") == true) {
                        var dstWareId = "null";
                        var dstId = DestinationTrench.split(":")[0];
                        var dstName = DestinationTrench.split(":")[1];
                    }
                    else {
                        var dstWareId = "null";
                        var dstId = "null";
                        var dstName = DestinationTrench;
                    }

                    window["" + data.trenchId] = [data.trenchId, trenchName, srcWareId, srcId, srcName, dstWareId, dstId, dstName, SourceTrenchLat, SourceTrenchLng, DestinationTrenchLng, DestinationTrenchLat, srcCityTrench, dstCityTrench, NumDucts, trenchCapacity, trenchLength, IdNodeSelectedTemp, drawingType, trenchOwner, trenchInstaller, trenchEngineerName];
                    window["mapPoints_" + data.trenchId].push(new google.maps.LatLng(SourceTrenchLat, SourceTrenchLng));
                    window["bounds_" + data.trenchId].extend(new google.maps.LatLng(SourceTrenchLat, SourceTrenchLng));
                    window["mapPointsNames_" + data.trenchId].push(SourceTrench);

                    if (typeof data.trenchAuxiliaryList !== 'undefined') {
                        if (data.trenchAuxiliaryList.length != 0) {
                            for (i = 0;i < data.trenchAuxiliaryList.length;i++) {
                                var lng = data.trenchAuxiliaryList[i][0];
                                var lat = data.trenchAuxiliaryList[i][1];
                                if (data.trenchAuxiliaryList[i][3] != "null") {
                                    var trenchAuxPoint = data.trenchAuxiliaryList[i][3] + ":" + data.trenchAuxiliaryList[i][5] + ":" + data.trenchAuxiliaryList[i][4];
                                }
                                else {
                                    if (data.trenchAuxiliaryList[i][4].split("_")[0] == "MH" || data.trenchAuxiliaryList[i][4].split("_")[0] == "HH" || data.trenchAuxiliaryList[i][4].split("_")[0] == "DB") {
                                        var trenchAuxPoint = data.trenchAuxiliaryList[i][4] + ":" + data.trenchAuxiliaryList[i][5];
                                    }
                                    else if (data.trenchAuxiliaryList[i][5].includes("Auxiliary_Point") == true) {
                                        var trenchAuxPoint = data.trenchAuxiliaryList[i][10] + ":" + data.trenchAuxiliaryList[i][5];
                                    }
                                    else {
                                        var trenchAuxPoint = data.trenchAuxiliaryList[i][5];
                                    }
                                }
                                window["mapPoints_" + data.trenchId].push(new google.maps.LatLng(lat, lng));
                                window["bounds_" + data.trenchId].extend(new google.maps.LatLng(lat, lng));
                                window["mapPointsNames_" + data.trenchId].push(trenchAuxPoint);
                            }
                        }
                    }
                    trenchAuxPoint = "";
                    window["mapPoints_" + data.trenchId].push(new google.maps.LatLng(DestinationTrenchLat, DestinationTrenchLng));
                    window["bounds_" + data.trenchId].extend(new google.maps.LatLng(DestinationTrenchLat, DestinationTrenchLng));
                    window["mapPointsNames_" + data.trenchId].push(DestinationTrench);

                    if ($("#trenchDrawingBy").val() == "DRIVING") {
                        if (trenchArray[data.trenchId]) {
                            trenchArray[data.trenchId].setMap(null);
                        }
                        if (directionHashmapTrench.get(data.trenchId) != undefined) {
                            for (ii = 0;ii < directionHashmapTrench.get(data.trenchId).length;ii++) {
                                directionHashmapTrench.get(data.trenchId)[ii].setMap(null);
                            }
                        }
                        buildDrivingPath(data.trenchId, window["mapPoints_" + data.trenchId], "trench", "brown", directionHashmapTrench);
                    }
                    else {
                        if (directionHashmapTrench.get(data.trenchId) != undefined) {
                            for (ii = 0;ii < directionHashmapTrench.get(data.trenchId).length;ii++) {
                                directionHashmapTrench.get(data.trenchId)[ii].setMap(null);
                            }
                        }

                        if (trenchArray[data.trenchId]) {
                            trenchArray[data.trenchId].setPath(window["mapPoints_" + data.trenchId]);
                            trenchArray[data.trenchId].setMap(map);
                        }
                        else {
                            buildPath(data.trenchId, window["mapPoints_" + data.trenchId], trenchArray, allTrenches, "Trench_f_", 'brown', 0.4, 20, 'brown', 1);
                            trenchArray[data.trenchId].setMap(map);
                        }
                    }

                    map.fitBounds(window["bounds_" + data.trenchId]);
                    Create_Trench(data.trenchId);

                    $("#" + data.trenchId).children(':checkbox').prop("checked", true);
                    $("#" + data.trenchId + "> .TreeSpan").css("display", "inline");
                    pathMapListener(data.trenchId, "Trench_f_");
                    $("#trenchCheckAllBoq").prop("checked", true);
                    /*
                                        $('.tree li#Trench_f_' + IdNodeSelectedTemp + ' .TreeSpan').unbind("mouseover");
                                        $('.tree li#Trench_f_' + IdNodeSelectedTemp + ' .TreeSpan').bind("mouseover", function(e) {
                                            $(this).addClass('backgroundTree');
                                        }).on("mouseout", function(e) {
                                            $(this).removeClass('backgroundTree');
                    
                                        });
                    
                                        $('.tree li#Trench_f_' + IdNodeSelectedTemp + ' .TreeSpan').on('click', function(e) {
                                            $(".tree li#Trench_f_" + IdNodeSelectedTemp + "  span").removeClass("selected-span");
                                            $(".tree li#Trench_f_" + IdNodeSelectedTemp + " span").css("background-color", "");                        
                                            $(this).addClass("selected-span");
                                        });
                    */
					console.log("cleaning data");
                    data = null;
                }
				console.log("cleaning aux");
                const tbody = $("#auxiliary_trenchTable tbody")[0];
                tbody.innerHTML = "";
                dict = [];
            },
            error: function(result) {
                alert("Error: " + result);
            }
        });
        countTrench = null;
        ModalReset("trenchModal");
        clearCreateFromMap(markerArrayAux);
    }
}