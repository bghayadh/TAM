function saveDistributionBoard() {

    var dbAlertType = "";
    var dbRowsTotalNum = document.getElementById("DistributionBoardRowsNum").value;
    var dbColsTotalNum = document.getElementById("DistributionBoardColsNum").value;

    distboardNumModules = parseInt(document.getElementById("DistributionBoardNumModules").value) || 0;
    distboardRowPerModule = parseInt(document.getElementById("DistributionBoardRowPerModule").value) || 0;


    dbAlertType = "";
    $("#DbMappingTable > tbody").find('input[name="record"]').each(function() {

        var dbRowColIndex = $(this).parent().parent().children('td[name="Index"]').children('input').val();
        var dbRowNum = $(this).parent().parent().children('td[name="RowIndex"]').children('input').val();
        var dbColNum = $(this).parent().parent().children('td[name="ColIndex"]').children('input').val();

        if (dbRowNum == "") {
            dbAlertType = "alertEmptyRow";
            return false; // breaks
        }
        else if (parseFloat(dbRowNum) > parseFloat(dbRowsTotalNum)) {
            dbAlertType = "alertRow";
            return false; // breaks
        }
        else if (dbColNum == "") {
            dbAlertType = "alertEmptyCol";
            return false; // breaks
        }
        else if (parseFloat(dbColNum) > parseFloat(dbColsTotalNum)) {
            dbAlertType = "alertCol";
            return false; // breaks
        }
    });

    if (isNaN(document.getElementById("DistributionBoardLong").value) == true) {
        alert("Incorrect Format of longitude.");
        return false;
    }
    else if (isNaN(document.getElementById("DistributionBoardLat").value) == true) {
        alert("Incorrect Format of latitude.");
        return false;
    }
    else if (document.getElementById("boardCity").value == "") {
        alert("City cannot be empty. ");
        return false;
    }
    ////
    else if (document.getElementById("DistributionBoardSite").value == "" && document.getElementById('site_DBAutoComplete').checked) {
        alert("SiteID cannot be empty. ");
        return false;
    }
    else if (document.getElementById("DistributionBoardSiteName").value == "" && document.getElementById('site_DBAutoComplete').checked) {
        alert("Site Name cannot be empty. ");
        return false;
    }
    else if (document.getElementById("DistributionBoardWarehouse").value == "" && document.getElementById('site_DBAutoComplete').checked) {
        alert("WarehouseID cannot be empty. ");
        return false;
    }

    else if (document.getElementById("DistributionBoardClient").value == "" && document.getElementById('customer_DBAutoComplete').checked) {
        alert("ClientID cannot be empty. ");
        return false;
    }
    else if (document.getElementById("DistributionBoardClientName").value == "" && document.getElementById('customer_DBAutoComplete').checked) {
        alert("Client Name cannot be empty. ");
        return false;
    }
    else if (document.getElementById("DistributionBoardClientPhoneNb").value == "" && document.getElementById('customer_DBAutoComplete').checked) {
        alert("Phone Number cannot be empty. ");
        return false;
    }
    else if (distboardNumModules == 0 || distboardRowPerModule == 0) {
        alert("Please Enter Number of Modules");
        return false;
    }
    else if (distboardRowPerModule == 0) {
        alert("Please Enter Number of Rows Per Module");
        return false;
    }


    ////
    else if (document.getElementById("DistributionBoardName").value != "" && document.getElementById("DistributionBoardLong").value != "" && document.getElementById("DistributionBoardLat").value != null
        && dbAlertType != "alertRow" && dbAlertType != "alertCol" && dbAlertType != "alertEmptyCol" && dbAlertType != "alertEmptyRow") {

        var countBoq = $("#DbMappingTable > tbody").children().length;
        var totalPorts = $("#DistributionBoardRowsNum").val() * $("#DistributionBoardColsNum").val();
        //distributionBoardCount=null;
        if (countBoq > totalPorts) {
            alert("Number of Assigned ports are larger than the maximum Board's ports Capacity ")
        }
        else {
            DistributionBoardId = document.getElementById("DistributionBoardId").value;;
            DistributionBoardName = document.getElementById("DistributionBoardName").value;
            DistributionBoardLong = document.getElementById("DistributionBoardLong").value;
            DistributionBoardLat = document.getElementById("DistributionBoardLat").value;
            DistributionBoardSite = document.getElementById("DistributionBoardSite").value;
            DistributionBoardSiteName = document.getElementById("DistributionBoardSiteName").value;
            DistributionBoardWarehouse = document.getElementById("DistributionBoardWarehouse").value;
            DistributionBoardClient = document.getElementById("DistributionBoardClient").value;
            DistributionBoardClientName = document.getElementById("DistributionBoardClientName").value;
            DistributionBoardClientPhoneNb = document.getElementById("DistributionBoardClientPhoneNb").value;
            DistributionBoardCapacity = document.getElementById("DistributionBoardCapacity").value;
            DistributionBoardRowsNum = document.getElementById("DistributionBoardRowsNum").value;
            DistributionBoardColsNum = document.getElementById("DistributionBoardColsNum").value;
            DistributionBoardFront = document.getElementById("DistributionBoardFront").value;
            DistributionBoardBack = document.getElementById("DistributionBoardBack").value;
            DBEngineerName = document.getElementById("DBEngineerName").value;
            DBInstaller = document.getElementById("DBInstaller").value;
            DBDeploymentType = document.getElementById("DBDeploymentType").value;
            DBAdaptorPanelType = document.getElementById("DBAdaptorPanelType").value;
            boardCity = document.getElementById("boardCity").value;
            dbNetLevel = document.getElementById("DBnetlevel").value;
            boardCreatedDate = document.getElementById("boardCreationDate").value;
            distBoardMappingFlag = document.querySelector("#DBMappingFlag ").value;
            distBoardType = document.getElementById("DBType").value;
            distboardControllerId = document.getElementById("DBController").value;
            distboardControllerName = document.getElementById("DBControllerName").value;
            distboardSerialNum = document.getElementById("DBSerialNum").value;
            distboardRowCount = $("#rowCounting option:selected").text();
            /*			
                        distboardNumModules = document.getElementById("DistributionBoardNumModules").value;
                        distboardRowPerModule = document.getElementById("DistributionBoardRowPerModule").value;
            */

            if ($("#projectIdDB").is(":visible") && $("#projectNameDB").is(":visible")) {
                if ($("#DBProjectId").val() == "") {
                    IdNodeSelectedTemp = "CurrentPhysicalLayer";
                }
                else {
                    IdNodeSelectedTemp = $("#DBProjectId").val();
                }
            }


            let kitData = [];

            // Loop through each row in the table body
            $("#DbKit tbody tr").each(function() {
                let row = {
                    kitSerialNum: $(this).find("td[name='serialNum'] input").val(),
                    kitType: $(this).find("td[name='type'] input").val(),
                    kitId: $(this).find("input[name='record']").attr("id"),

                };
                kitData.push(row);
            });

            let moduleData = [];

            // Loop through each row in the table body
            $("#DbModule tbody tr").each(function() {
                let row = {
                    moduleId: $(this).find("input[name='record']").attr("id"),
                    modulePosition: $(this).find("td[name='modulePos'] input").val(),
                    kitSerialNum: $(this).find("td[name='kitSerialNum'] input").val(),
                    orientation: $(this).find("td[name='orientation'] input").val(),
                    lowestPortNum: $(this).find("td[name='lowestPortNum'] input").val(),
                    sesorsPerPortNum: $(this).find("td[name='sensorsPerPortNum'] input").val(),
                    sensorCount: $(this).find("td[name='sensorCount'] input").val(),
                    occupiedSensorMask: $(this).find("td[name='occupiedSensorMask'] input").val(),
                };
                moduleData.push(row);
            });


            var locationId = "";
            var locationName = "";
            var location = "";
            var isSiteChecked = "";
            var isClientChecked = "";

            if (document.getElementById('site_DBAutoComplete').checked) {
                isSiteChecked = true;
            }

            if (document.getElementById('customer_DBAutoComplete').checked) {
                isClientChecked = true;
            }
            if (isSiteChecked == true) {
                locationId = DistributionBoardSite;
                locationName = DistributionBoardSiteName;
                location = DistributionBoardWarehouse;
            } else {
                locationId = DistributionBoardClient;
                locationName = DistributionBoardClientName;
                location = DistributionBoardClientPhoneNb;
            }
            isSiteChecked = false;
            isClientChecked = false;
            countCablesFront = 0; countCablesBack = 0; countRows = 0; countCols = 0;
            getSelectedRowsDbMapping();
            distributionBoardId = "";

            var token = $('input[name="csrfToken"]').attr('value');
            console.log(deletedKitIds);
            console.log(dict);
            $.ajax({
                type: "POST",
                headers: {
                    'X-CSRFToken': token
                },
                url: getContext() + '/saveDistributionBoard',
                data: {
                    "DistributionBoardId": DistributionBoardId,
                    "DistributionBoardName": DistributionBoardName,
                    "DistributionBoardLong": DistributionBoardLong,
                    "DistributionBoardLat": DistributionBoardLat,
                    "DistributionBoardSite": locationId,
                    "DistributionBoardSiteName": locationName,
                    "DistributionBoardWarehouse": location,
                    "rowCounting": distboardRowCount,
                    "DistributionBoardCapacity": DistributionBoardCapacity,
                    "DistributionBoardRowsNum": DistributionBoardRowsNum,
                    "DistributionBoardColsNum": DistributionBoardColsNum,
                    "DistributionBoardFront": DistributionBoardFront,
                    "DistributionBoardBack": DistributionBoardBack,
                    "boardCreatedDate": boardCreatedDate,
                    "boardCity": boardCity,
                    "dbNetLevel": dbNetLevel,
                    "DBEngineerName": DBEngineerName,
                    "DBInstaller": DBInstaller,
                    "DBDeploymentType": DBDeploymentType,
                    "DBAdaptorPanelType": DBAdaptorPanelType,
                    "distBoardMappingFlag": distBoardMappingFlag,
                    "dictParameter": dict,
                    "ProjectId": IdNodeSelectedTemp,
                    "updateModfUser": updateModfUser,
                    "type": distBoardType,
                    "controllerId": distboardControllerId,
                    "controllerName": distboardControllerName,
                    "serialNum": distboardSerialNum,
                    "deletedKitIds": deletedKitIds,
                    "deletedModuleIds": deletedModuleIds,
                    "kitData": JSON.stringify(kitData),
                    "moduleData": JSON.stringify(moduleData),
                    "distboardNumModules": distboardNumModules,
                    "distboardRowPerModule": distboardRowPerModule


                },
                dataType: "json",
                success: function(data) {
                    console.log(deletedKitIds);
                    if (data != null) {
                        console.log(distBoardType);
                        window["" + data.distributionBoardId] = [];
                        window["" + data.distributionBoardId] = [data.distributionBoardId, DistributionBoardLong, DistributionBoardLat, DistributionBoardName, DistributionBoardCapacity, locationId, IdNodeSelectedTemp, boardCity, dbNetLevel, DBEngineerName, DBInstaller, DBDeploymentType, DBAdaptorPanelType];


                        if (actiondistBoardContext == "Insert") {
                            if (distBoardType == "passive") {
                                if (dbNetLevel == "backbone") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/backboneDB.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                }
                                else if (dbNetLevel == "metro") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/metroDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                }
                                else if (dbNetLevel == "access") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/accessDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                }
                                $("#DistributionBoard_" + dbNetLevel + "__" + IdNodeSelectedTemp).append(str);

                            }

                            else if (distBoardType == "active") {
                                console.log(distboardControllerId);
                                if (dbNetLevel == "backbone") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/backboneDB.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                    $("#" + distboardControllerId).append(str);
                                }

                                else if (dbNetLevel == "metro") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/metroDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                    $("#" + distboardControllerId).append(str);
                                }
                                else if (dbNetLevel == "access") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/accessDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                    $("#" + distboardControllerId).append(str);



                                }
                            }
                        }
                        else if (actiondistBoardContext == "Update") {

                            console.log("DBoldNtwLevel and dbNetLevel are" + DBoldNtwLevel + " " + dbNetLevel);
                            if (DBoldNtwLevel != dbNetLevel) {
                                console.log("Different DB Network Level");
                                $("#" + data.distributionBoardId).remove();
                                if (dbNetLevel == "backbone") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/backboneDB.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                }
                                else if (dbNetLevel == "metro") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/metroDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                }
                                else if (dbNetLevel == "access") {
                                    str = "<ul><li id='" + data.distributionBoardId + "'  class='DistributionBoard' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/accessDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li></ul>";
                                }
                                $("#DistributionBoard_" + dbNetLevel + "__" + IdNodeSelectedTemp).append(str);
                            }



                            else {
                                if (dbNetLevel == "backbone") {
                                    $("#" + data.distributionBoardId + "> .TreeSpan").html("<img class='image' src='" + getContext() + "/resources/NetworkImages/backboneDB.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li>");
                                }
                                else if (dbNetLevel == "metro") {
                                    $("#" + data.distributionBoardId + "> .TreeSpan").html("<img class='image' src='" + getContext() + "/resources/NetworkImages/metroDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li>");
                                }
                                else if (dbNetLevel == "access") {
                                    $("#" + data.distributionBoardId + "> .TreeSpan").html("<img class='image' src='" + getContext() + "/resources/NetworkImages/accessDb.png'> " + DistributionBoardName + "/" + data.distributionBoardId + " </span></li>");
                                }
                            }
                        }

                        $("#" + data.distributionBoardId).children(':checkbox').prop("checked", true);

                        $("#" + data.distributionBoardId).contextmenu(function() {
                            menuName = singleDistBoard;
                            selectedDistBoardContext = $(this).attr('id');
                            selectedDistBoardName = $(this).text();
                            openContext(selectedDistBoardContext, selectedDistBoardName, singleDistBoard, event);
                        });

                        if (dbNetLevel == "backbone") {
                            removeDistBoardMarker(data.distributionBoardId);
                            create_DB_Marker_Click(data.distributionBoardId, DistributionBoardName, DistributionBoardLong, DistributionBoardLat, markersDistBoard, markerClusterBackboneDistBoard, "", "");

                            markerClusterBackboneDistBoard.addMarker(markersDistBoard["" + data.distributionBoardId]);
                            clusterName = markerClusterBackboneDistBoard;
                        }
                        else if (dbNetLevel == "metro") {
                            removeDistBoardMarker(data.distributionBoardId);
                            create_DB_Marker_Click(data.distributionBoardId, DistributionBoardName, DistributionBoardLong, DistributionBoardLat, markersDistBoard, markerClusterMetroDistBoard, "", "");

                            markerClusterMetroDistBoard.addMarker(markersDistBoard["" + data.distributionBoardId]);
                            clusterName = markerClusterMetroDistBoard;
                        }
                        else {
                            removeDistBoardMarker(data.distributionBoardId);
                            create_DB_Marker_Click(data.distributionBoardId, DistributionBoardName, DistributionBoardLong, DistributionBoardLat, markersDistBoard, markerClusterAccessDistBoard, "", "");

                            markerClusterAccessDistBoard.addMarker(markersDistBoard["" + data.distributionBoardId]);
                            clusterName = markerClusterAccessDistBoard;
                        }

                        DBCheckFilter(data.distributionBoardId, clusterName);

                        if (data.countConnections.length > 0) {
                            countCablesFront = data.countConnections[0][2];
                            countCablesBack = data.countConnections[0][3];
                            countRows = data.countConnections[0][0];
                            countCols = data.countConnections[0][1];
                        }
                        map.setZoom(11);
                        if ($("#dBMapCheck_Labels").prop("checked") == true) {
                            markersDistBoard[data.distributionBoardId].setLabel({ text: DistributionBoardName, className: "marker-position-dB", color: "#5665F9" });
                        }
                        panTo(DistributionBoardLat, DistributionBoardLong);

                        $("#" + data.distributionBoardId + " > .TreeSpan").css("display", "inline");

                        // remove the selection of previous item if exist and add it to the new one

                        if (IdSelectedTemp != "") {
                            $("#" + IdSelectedTemp + " > .TreeSpan").removeClass("selected-span");
                            $("#" + IdSelectedTemp + " > .TreeSpan").css("background", "");
                        }
                        $("#" + data.distributionBoardId + " > .TreeSpan").addClass("selected-span");
                        $("#" + data.distributionBoardId + " > .TreeSpan").css("background-color", "#97b9cc");
                        IdSelectedTemp = data.distributionBoardId;


                        var childrenInitial = $("#initial_ul_" + IdNodeSelectedTemp + "").find(' > ul > li');
                        var children = $("#DistributionBoard_f_" + IdNodeSelectedTemp + "").find(' > ul > li');
                        var networkLevelFolder = $("#DistributionBoard_f_" + IdNodeSelectedTemp + "").find(' > ul > li >ul >li');



                        $("#initial_ul_" + IdNodeSelectedTemp + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
                        $("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
                        $("#" + IdNodeSelectedTemp + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
                        $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');



                        $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
                        $("#DistributionBoard_f_" + IdNodeSelectedTemp + "").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');



                        children.show('fast');
                        childrenInitial.show('fast');
                        networkLevelFolder.show('fast');


                        // scroll to the created db
                        offset = $("#" + data.distributionBoardId).offset().top;
                        offset2 = $("#initial_ul_CurrentPhysicalLayer").offset().top;
                        offsettot = (offset - offset2);

                        $("#network_tree").animate({ scrollTop: offsettot }, "slow");
                    }
                },
                error: function(result) {
                    alert("Error");
                }
            });

            $("#" + data.distributionBoardId + " > .TreeSpan").css("display", "inline");

            $("#distributionBoardModal").modal('hide');

            $("#distributionBoardModal").find("input,textarea,select")
                .val('').end().find("input[type=checkbox], input[type=radio]")
                .prop("checked", "")
                .end();
            if ($("#dBMapCheck_Labels").prop("checked") == true) {
                markersDistBoard[data.distributionBoardId].setLabel({ text: DistributionBoardName, className: "marker-position-dB", color: "#5665F9" });
            }
        }
    }

    else {
        //alert(" Missing Fields !!! ");
        if (dbAlertType == "alertRow") {
            alert("Please enter a corresponding row number");
        }
        else if (dbAlertType == "alertCol") {
            alert("Please enter a corresponding column number");
        }
        else if (dbAlertType == "alertEmptyRow") {
            alert("Board Mapping row is empty! Please enter a corresponding row");

        }
        else if (dbAlertType == "alertEmptyCol") {
            alert("Board Mapping column is empty! Please enter a corresponding column");

        }

        else {
            alert(" Missing Fields !!! ");
        }
    }
}

function removeDistBoardMarker(boardId) {

    if (!markersDistBoard[boardId]) {
        return;
    }

    markerClusterBackboneDistBoard.removeMarker(markersDistBoard[boardId]);
    markerClusterMetroDistBoard.removeMarker(markersDistBoard[boardId]);
    markerClusterAccessDistBoard.removeMarker(markersDistBoard[boardId]);

    markersDistBoard[boardId].setMap(null);

    delete markersDistBoard[boardId];
}

// get the selected ports of distribition board
function getSelectedRowsDbMapping() {

    dict = [];
    dictUpdate = [];
    var portId = "";

    $("#DbMappingTable > tbody").find('input[name="record"]').each(function() {

        if ($(this).is(":checked")) {

            var rowColIndex = $(this).parent().parent().children('td[name="Index"]').children('input').val();

            var rowNum = $(this).parent().parent().children('td[name="RowIndex"]').children('input').val();
            var colNum = $(this).parent().parent().children('td[name="ColIndex"]').children('input').val();

            var patchType = $(this).parent().parent().children('td[name="patchType"]').children('input').val();

            var nearPortNum = $(this).parent().parent().children('td[name="nearPortNum"]').children('input').val();
            var nearModule = $(this).parent().parent().children('td[name="nearModule"]').children('input').val();

            var fP_Status = $(this).parent().parent().children('td[name="FP_Status"]').children('select').val();
            var fP_LocationType = $(this).parent().parent().children('td[name="FP_LocationType"]').children('select').val();
            var fP_LocationID = $(this).parent().parent().children('td[name="FP_LocationID"]').children('input').val();
            var fP_LocationM = $(this).parent().parent().children('td[name="FP_LocationM"]').children('input').val();
            var fP_Location = $(this).parent().parent().children('td[name="FP_Location"]').children('input').val();
            var fP_Equipment = $(this).parent().parent().children('td[name="FP_Equipment"]').children('select').val();
            var fP_EquipmentType = $(this).parent().parent().children('td[name="FP_EquipmentType"]').children('input').val();

            var farKitSerialNum = $(this).parent().parent().children('td[name="farKitSerialNum"]').children('input').val();
            var farModule = $(this).parent().parent().children('td[name="farModule"]').children('input').val();
            var farPortNum = $(this).parent().parent().children('td[name="farPortNum"]').children('input').val();



            var fP_EquipmentID = $(this).parent().parent().children('td[name="FP_EquipmentID"]').children('input').val();
            var fP_EquipmentName = $(this).parent().parent().children('td[name="FP_EquipmentName"]').children('input').val();
            var fP_Address = $(this).parent().parent().children('td[name="FP_Address"]').children('input').val();
            var fP_JunctionID = $(this).parent().parent().children('td[name="FP_JunctionID"]').children('input').val();
            var fP_JunctionName = $(this).parent().parent().children('td[name="FP_JunctionName"]').children('input').val();
            //added
            var fP_StrandNb = $(this).parent().parent().children('td[name="FP_StrandNb"]').children('input').val();
            var fP_StrandColor = $(this).parent().parent().children('td[name="FP_StrandColor"]').children('select').val();
            var fP_StrandID = $(this).parent().parent().children('td[name="FP_StrandID"]').children('input').val();
            var fP_StrandName = $(this).parent().parent().children('td[name="FP_StrandName"]').children('input').val();
            var fP_TubeNb = $(this).parent().parent().children('td[name="FP_TubeNb"]').children('input').val();
            var fP_TubeColor = $(this).parent().parent().children('td[name="FP_TubeColor"]').children('select').val();
            var fP_TubeID = $(this).parent().parent().children('td[name="FP_TubeID"]').children('input').val();
            var fP_TubeName = $(this).parent().parent().children('td[name="FP_TubeName"]').children('input').val();
            var fP_FiberID = $(this).parent().parent().children('td[name="FP_FiberID"]').children('input').val();
            var fP_FiberName = $(this).parent().parent().children('td[name="FP_FiberName"]').children('input').val();

            var bP_LocationType = $(this).parent().parent().children('td[name="BP_LocationType"]').children('select').val();
            var bP_LocationID = $(this).parent().parent().children('td[name="BP_LocationID"]').children('input').val();
            var bP_LocationM = $(this).parent().parent().children('td[name="BP_LocationM"]').children('input').val();
            var bP_Location = $(this).parent().parent().children('td[name="BP_Location"]').children('input').val();
            var bP_Equipment = $(this).parent().parent().children('td[name="BP_Equipment"]').children('select').val();
            var bP_EquipmentType = $(this).parent().parent().children('td[name="BP_EquipmentType"]').children('input').val();
            var bP_EquipmentID = $(this).parent().parent().children('td[name="BP_EquipmentID"]').children('input').val();
            var bP_EquipmentName = $(this).parent().parent().children('td[name="BP_EquipmentName"]').children('input').val();

            var backKitSerialNum = $(this).parent().parent().children('td[name="backKitSerialNum"]').children('input').val();
            var backModule = $(this).parent().parent().children('td[name="backModule"]').children('input').val();
            var backPortNum = $(this).parent().parent().children('td[name="backPortNum"]').children('input').val();

            var bP_Address = $(this).parent().parent().children('td[name="BP_Address"]').children('input').val();
            var bP_JunctionID = $(this).parent().parent().children('td[name="BP_JunctionID"]').children('input').val();
            var bP_JunctionName = $(this).parent().parent().children('td[name="BP_JunctionName"]').children('input').val();
            //

            var bP_Status = $(this).parent().parent().children('td[name="BP_Status"]').children('select').val();
            var bP_StrandNb = $(this).parent().parent().children('td[name="BP_StrandNb"]').children('input').val();
            var bP_StrandColor = $(this).parent().parent().children('td[name="BP_StrandColor"]').children('select').val();
            var bP_StrandID = $(this).parent().parent().children('td[name="BP_StrandID"]').children('input').val();
            var bP_StrandName = $(this).parent().parent().children('td[name="BP_StrandName"]').children('input').val();
            var bP_TubeNb = $(this).parent().parent().children('td[name="BP_TubeNb"]').children('input').val();
            var bP_TubeColor = $(this).parent().parent().children('td[name="BP_TubeColor"]').children('select').val();
            var bP_TubeID = $(this).parent().parent().children('td[name="BP_TubeID"]').children('input').val();
            var bP_TubeName = $(this).parent().parent().children('td[name="BP_TubeName"]').children('input').val();
            var bP_FiberID = $(this).parent().parent().children('td[name="BP_FiberID"]').children('input').val();
            var bP_FiberName = $(this).parent().parent().children('td[name="BP_FiberName"]').children('input').val();

            console.log("igggg");
            console.log(patchType);

            if (actiondistBoardContext == "Update") {
                if (window["DB_Mapper" + selectedDistBoardContext] != "") {
                    portId = $(this).parent().parent().attr('id');
                    if (typeof portId == 'undefined') {
                        portId = "";
                    }
                    index = window["DB_Mapper" + selectedDistBoardContext].findIndex(x => x == "" + rowColIndex + "," + rowNum + "," + colNum + "," + portId + "," + fP_Status + "," + fP_LocationType + "," + fP_LocationID + "," + fP_LocationM + "," + fP_Location + "," + fP_EquipmentType + "," + fP_Equipment + "," + fP_EquipmentID + "," + fP_EquipmentName + "," + fP_Address + "," + fP_StrandNb + "," + fP_StrandID + "," + fP_StrandName + "," + fP_TubeNb + "," + fP_TubeID + "," + fP_TubeName + "," + fP_FiberID + "," + fP_FiberName + "," + bP_Status + "," + bP_LocationType + "," + bP_LocationID + "," + bP_LocationM + "," + bP_Location + "," + bP_EquipmentType + "," + bP_Equipment + "," + bP_EquipmentID + "," + bP_EquipmentName + "," + bP_Address + "," + bP_StrandNb + "," + bP_StrandID + "," + bP_StrandName + "," + bP_TubeNb + "," + bP_TubeID + "," + bP_TubeName + "," + bP_FiberID + "," + bP_FiberName);
                    if (index == -1 && !window["DB_" + portId]) {
                        dict.push({
                            "rowColIndex": rowColIndex,
                            "rowNum": rowNum,
                            "colNum": colNum,
                            "backModule": backModule,
                            "backKitSerialNum": backKitSerialNum,
                            "backPortNum": backPortNum,
                            "farKitSerialNum": farKitSerialNum,
                            "farModule": farModule,
                            "farPortNum": farPortNum,
                            "patchType": patchType,
                            "nearPortNum": nearPortNum,
                            "nearModule": nearModule,
                            "portId": portId,
                            "fP_Status": fP_Status,
                            "fP_LocationType": fP_LocationType,
                            "fP_LocationID": fP_LocationID,
                            "fP_LocationM": fP_LocationM,
                            //"fP_Location":(fP_Location  == "" ||fP_Location == null ? fP_Location : "" ) ,
                            "fP_Location": fP_Location,
                            "fP_EquipmentType": fP_EquipmentType,
                            "fP_Equipment": fP_Equipment,
                            "fP_EquipmentID": fP_EquipmentID,
                            "fP_EquipmentName": fP_EquipmentName,
                            "fP_Address": fP_Address,
                            "fP_JunctionID": fP_JunctionID,
                            "fP_JunctionName": fP_JunctionName,
                            //added
                            "fP_StrandNb": fP_StrandNb,
                            "fP_StrandColor": fP_StrandColor,
                            "fP_StrandID": fP_StrandID,
                            "fP_StrandName": fP_StrandName,
                            "fP_TubeNb": fP_TubeNb,
                            "fP_TubeColor": fP_TubeColor,
                            "fP_TubeID": fP_TubeID,
                            "fP_TubeName": fP_TubeName,
                            "fP_FiberID": fP_FiberID,
                            "fP_FiberName": fP_FiberName,
                            //
                            //added
                            "bP_LocationType": bP_LocationType,
                            "bP_LocationID": bP_LocationID,
                            "bP_LocationM": bP_LocationM,
                            //"bP_Location":(bP_Location  == "" ||bP_Location == null ? bP_Location : "" ) ,
                            "bP_Location": bP_Location,
                            "bP_EquipmentType": bP_EquipmentType,
                            "bP_Equipment": bP_Equipment,
                            "bP_EquipmentID": bP_EquipmentID,
                            "bP_EquipmentName": bP_EquipmentName,
                            "bP_Address": bP_Address,
                            "bP_JunctionID": bP_JunctionID,
                            "bP_JunctionName": bP_JunctionName,
                            //
                            "bP_StrandNb": bP_StrandNb,
                            "bP_StrandColor": bP_StrandColor,
                            "bP_StrandID": bP_StrandID,
                            "bP_StrandName": bP_StrandName,
                            "bP_TubeNb": bP_TubeNb,
                            "bP_TubeColor": bP_TubeColor,
                            "bP_TubeID": bP_TubeID,
                            "bP_TubeName": bP_TubeName,
                            "bP_FiberID": bP_FiberID,
                            "bP_FiberName": bP_FiberName
                        });

                    }
                    else if (index == -1 && window["DB_" + portId] || (rowNum != window["DB_" + portId][0] || colNum != window["DB_" + portId][1] || fP_Equipment != window["DB_" + portId][6] || bP_StrandName != window["DB_" + portId][8])) {
                        // Insert to be done
                        dict.push({
                            "rowColIndex": rowColIndex,
                            "rowNum": rowNum,
                            "colNum": colNum,
                            "portId": portId,
                            "fP_Status": fP_Status,
                            "fP_LocationType": fP_LocationType,
                            "fP_LocationID": fP_LocationID,
                            "fP_LocationM": fP_LocationM,
                            "fP_Location": fP_Location,
                            "fP_EquipmentType": fP_EquipmentType,
                            "fP_Equipment": fP_Equipment,
                            "fP_EquipmentID": fP_EquipmentID,
                            "fP_EquipmentName": fP_EquipmentName,
                            "fP_Address": fP_Address,
                            "fP_JunctionID": fP_JunctionID,
                            "fP_JunctionName": fP_JunctionName,
                            "backKitSerialNum": backKitSerialNum,
                            "backModule": backModule,
                            "backPortNum": backPortNum,
                            "farKitSerialNum": farKitSerialNum,
                            "farModule": farModule,
                            "farPortNum": farPortNum,
                            "patchType": patchType,
                            "nearPortNum": nearPortNum,
                            "nearModule": nearModule,
                            //added
                            "fP_StrandNb": fP_StrandNb,
                            "fP_StrandColor": fP_StrandColor,
                            "fP_StrandID": fP_StrandID,
                            "fP_StrandName": fP_StrandName,
                            "fP_TubeNb": fP_TubeNb,
                            "fP_TubeColor": fP_TubeColor,
                            "fP_TubeID": fP_TubeID,
                            "fP_TubeName": fP_TubeName,
                            "fP_FiberID": fP_FiberID,
                            "fP_FiberName": fP_FiberName,
                            //
                            "bP_Status": bP_Status,
                            //added
                            "bP_LocationType": bP_LocationType,
                            "bP_LocationID": bP_LocationID,
                            "bP_LocationM": bP_LocationM,
                            "bP_Location": bP_Location,
                            "bP_EquipmentType": bP_EquipmentType,
                            "bP_Equipment": bP_Equipment,
                            "bP_EquipmentID": bP_EquipmentID,
                            "bP_EquipmentName": bP_EquipmentName,
                            "bP_Address": bP_Address,
                            "bP_JunctionID": bP_JunctionID,
                            "bP_JunctionName": bP_JunctionName,
                            //
                            "bP_StrandNb": bP_StrandNb,
                            "bP_StrandColor": bP_StrandColor,
                            "bP_StrandID": bP_StrandID,
                            "bP_StrandName": bP_StrandName,
                            "bP_TubeNb": bP_TubeNb,
                            "bP_TubeColor": bP_TubeColor,
                            "bP_TubeID": bP_TubeID,
                            "bP_TubeName": bP_TubeName,
                            "bP_FiberID": bP_FiberID,
                            "bP_FiberName": bP_FiberName
                        });

                    }

                }
                else {
                    dict.push({
                        "rowColIndex": rowColIndex,
                        "rowNum": rowNum,
                        "colNum": colNum,
                        "portId": portId,
                        "fP_Status": fP_Status,
                        "fP_LocationType": fP_LocationType,
                        "fP_LocationID": fP_LocationID,
                        "fP_LocationM": fP_LocationM,
                        "fP_Location": fP_Location,
                        "fP_EquipmentType": fP_EquipmentType,
                        "fP_Equipment": fP_Equipment,
                        "fP_EquipmentID": fP_EquipmentID,
                        "fP_EquipmentName": fP_EquipmentName,
                        "fP_Address": fP_Address,
                        "fP_JunctionID": fP_JunctionID,
                        "fP_JunctionName": fP_JunctionName,
                        "backKitSerialNum": backKitSerialNum,
                        "backModule": backModule,
                        "backPortNum": backPortNum,
                        "farKitSerialNum": farKitSerialNum,
                        "farModule": farModule,
                        "farPortNum": farPortNum,
                        "patchType": patchType,
                        "nearPortNum": nearPortNum,
                        "nearModule": nearModule,
                        //added
                        "fP_StrandNb": fP_StrandNb,
                        "fP_StrandColor": fP_StrandColor,
                        "fP_StrandID": fP_StrandID,
                        "fP_StrandName": fP_StrandName,
                        "fP_TubeNb": fP_TubeNb,
                        "fP_TubeColor": fP_TubeColor,
                        "fP_TubeID": fP_TubeID,
                        "fP_TubeName": fP_TubeName,
                        "fP_FiberID": fP_FiberID,
                        "fP_FiberName": fP_FiberName,
                        //
                        "bP_Status": bP_Status,
                        //added
                        "bP_LocationType": bP_LocationType,
                        "bP_LocationID": bP_LocationID,
                        "bP_LocationM": bP_LocationM,
                        "bP_Location": bP_Location,
                        "bP_EquipmentType": bP_EquipmentType,
                        "bP_Equipment": bP_Equipment,
                        "bP_EquipmentID": bP_EquipmentID,
                        "bP_EquipmentName": bP_EquipmentName,
                        "bP_Address": bP_Address,
                        "bP_JunctionID": bP_JunctionID,
                        "bP_JunctionName": bP_JunctionName,
                        //
                        "bP_StrandNb": bP_StrandNb,
                        "bP_StrandColor": bP_StrandColor,
                        "bP_StrandID": bP_StrandID,
                        "bP_StrandName": bP_StrandName,
                        "bP_TubeNb": bP_TubeNb,
                        "bP_TubeColor": bP_TubeColor,
                        "bP_TubeID": bP_TubeID,
                        "bP_TubeName": bP_TubeName,
                        "bP_FiberID": bP_FiberID,
                        "bP_FiberName": bP_FiberName
                    });
                }
            }
            else {
                dict.push({

                    "rowColIndex": rowColIndex,
                    "rowNum": rowNum,
                    "colNum": colNum,
                    "portId": portId,
                    "fP_Status": fP_Status,
                    "fP_LocationType": fP_LocationType,
                    "fP_LocationID": fP_LocationID,
                    "fP_LocationM": fP_LocationM,
                    "backKitSerialNum": backKitSerialNum,
                    "backModule": backModule,
                    "backPortNum": backPortNum,
                    "farKitSerialNum": farKitSerialNum,
                    "farModule": farModule,
                    "farPortNum": farPortNum,
                    "patchType": patchType,
                    "nearPortNum": nearPortNum,
                    "nearModule": nearModule,
                    //"fP_Location":(fP_Location  == "" || fP_Location == null ? fP_Location : "" ) ,
                    "fP_Location": fP_Location,
                    "fP_EquipmentType": fP_EquipmentType,
                    "fP_Equipment": fP_Equipment,
                    "fP_EquipmentID": fP_EquipmentID,
                    "fP_EquipmentName": fP_EquipmentName,
                    "fP_Address": fP_Address,
                    "fP_JunctionID": fP_JunctionID,
                    "fP_JunctionName": fP_JunctionName,
                    //added
                    "fP_StrandNb": fP_StrandNb,
                    "fP_StrandColor": fP_StrandColor,
                    "fP_StrandID": fP_StrandID,
                    "fP_StrandName": fP_StrandName,
                    "fP_TubeNb": fP_TubeNb,
                    "fP_TubeColor": fP_TubeColor,
                    "fP_TubeID": fP_TubeID,
                    "fP_TubeName": fP_TubeName,
                    "fP_FiberID": fP_FiberID,
                    "fP_FiberName": fP_FiberName,
                    //

                    "bP_Status": bP_Status,
                    //added
                    "bP_LocationType": bP_LocationType,
                    "bP_LocationID": bP_LocationID,
                    "bP_LocationM": bP_LocationM,
                    //"bP_Location":(bP_Location  == "" ||bP_Location == null ? bP_Location : "" ) ,
                    "bP_Location": bP_Location,
                    "bP_EquipmentType": bP_EquipmentType,
                    "bP_Equipment": bP_Equipment,
                    "bP_EquipmentID": bP_EquipmentID,
                    "bP_EquipmentName": bP_EquipmentName,
                    "bP_Address": bP_Address,
                    "bP_JunctionID": bP_JunctionID,
                    "bP_JunctionName": bP_JunctionName,
                    //
                    "bP_StrandNb": bP_StrandNb,
                    "bP_StrandColor": bP_StrandColor,
                    "bP_StrandID": bP_StrandID,
                    "bP_StrandName": bP_StrandName,
                    "bP_TubeNb": bP_TubeNb,
                    "bP_TubeColor": bP_TubeColor,
                    "bP_TubeID": bP_TubeID,
                    "bP_TubeName": bP_TubeName,
                    "bP_FiberID": bP_FiberID,
                    "bP_FiberName": bP_FiberName
                });
            }
        }
    });
}