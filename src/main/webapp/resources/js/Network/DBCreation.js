var DBFlag = 0;
let singleControllerMenu = [
    {
        'icon': 'folder-plus',
        'name': 'Create New Controller',
        action: () => {
            $("#controllerHeader").text("Controller: ");
            $("#controllerModal").modal('show');
            $('#controllerModal').find('input:text').val('');
            document.getElementById("site_ControllerAutoComplete").checked = true;
            $('#site_ControllerAutoComplete').val('1');
            siteControllerChanged("#site_ControllerAutoComplete");


            $('#controllerModal').find('input:text').val('');
            actiondistControllerContext = "Insert";

            let select = document.getElementById("networkLayer");
            let targetText = networkLayer;

            for (let i = 0;i < select.options.length;i++) {
                if (select.options[i].text === targetText) {
                    select.selectedIndex = i;
                    break;
                }
            }
            dbContNtLevel = document.getElementById("networkLayer").value;
        }
    },
    {
        'icon': 'edit',
        'name': 'Edit or View Details',
        action: () => {
            $("#controllerModal").modal('show');
            actiondistControllerContext = "Update";
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/findControllerDetails',
                data: {
                    "selectedControllerContext": selectedControllerId
                },
                dataType: "json",
                success: function(data) {
                    console.log(data);
                    $("#controllerModal").find("input").val('').end();

                    $("#controllerHeader").text("Controller: " + data.ControllerDetails[0][0]);

                    $("#controllerId").val(selectedControllerId);
                    if (data.ControllerDetails[0][9] != null) {
                        if (data.ControllerDetails[0][9].split("_")[0] == "CUST") {//for check box site or client
                            document.getElementById("customer_ControllerAutoComplete").checked = true;
                            $('#customer_ControllerAutoComplete').val('1');
                            document.getElementById("site_ControllerAutoComplete").checked = false;
                            $('#site_ControllerAutoComplete').val('0');
                            document.getElementById("ContClientId").style.display = "block";
                            document.getElementById("ContClientName").style.display = "block";
                            document.getElementById("ContClientPhoneNb").style.display = "block";
                            document.getElementById("ContWarehouse").style.display = "none";
                            document.getElementById("ContSite").style.display = "none";
                            document.getElementById("ContSiteName").style.display = "none";


                        } else {
                            document.getElementById("site_ControllerAutoComplete").checked = true;
                            $('#site_ControllerAutoComplete').val('1');
                            document.getElementById("customer_ControllerAutoComplete").checked = false;
                            $('#customer_ControllerAutoComplete').val('0');
                            document.getElementById("ContClientId").style.display = "none";
                            document.getElementById("ContClientName").style.display = "none";
                            document.getElementById("ContClientPhoneNb").style.display = "none";
                            document.getElementById("ContWarehouse").style.display = "block";
                            document.getElementById("ContSite").style.display = "block";
                            document.getElementById("ContSiteName").style.display = "block";

                        }
                    }
                    //
                    if (data.ControllerDetails[0][9] == null) {
                        document.getElementById("site_ControllerAutoComplete").checked = true;
                        $('#site_ControllerAutoComplete').val('1');
                        document.getElementById("customer_ControllerAutoComplete").checked = false;
                        $('#customer_ControllerAutoComplete').val('0');
                        document.getElementById("ContClientId").style.display = "none";
                        document.getElementById("ContClientName").style.display = "none";
                        document.getElementById("ContClientPhoneNb").style.display = "none";
                        document.getElementById("ContWarehouse").style.display = "block";
                        document.getElementById("ContSite").style.display = "block";
                        document.getElementById("ContSiteName").style.display = "block";
                    }

                    if (data.ControllerDetails[0][1] != null) {
                        $("#ControllerName").val("" + data.ControllerDetails[0][1]);
                    }


                    if (data.ControllerDetails[0][9] != null) {
                        if (data.ControllerDetails[0][9].split("_")[0] == "CUST") {
                            $("#ControllerClient").val("" + data.ControllerDetails[0][9]);
                        }
                        else {
                            $("#ControllerSite").val("" + data.ControllerDetails[0][9]);
                        }
                    }
                    if (data.ControllerDetails[0][10] != null) {
                        if (data.ControllerDetails[0][9] != null) {
                            if (data.ControllerDetails[0][9].split("_")[0] == "CUST") {
                                $("#ControllerClientName").val("" + data.ControllerDetails[0][10]);
                            }
                            else {
                                $("#ControllerSiteName").val("" + data.ControllerDetails[0][10]);
                            }
                        }
                        else {
                            $("#ControllerSiteName").val("" + data.ControllerDetails[0][10]);
                        }
                    }


                    if (data.ControllerDetails[0][11] != null) {
                        if (data.ControllerDetails[0][9] != null) {
                            if (data.ControllerDetails[0][9].split("_")[0] == "CUST") {
                                $("#ControllerClientPhoneNb").val("" + data.ControllerDetails[0][11]);
                            }
                            else {
                                $("#ControllerWarehouse").val("" + data.ControllerDetails[0][11]);
                            }

                        }
                        else {
                            $("#ControllerWarehouse").val("" + data.ControllerDetails[0][11]);
                        }
                    }
                    if (data.ControllerDetails[0][17] != null) {
                        $("#controllerCity").val("" + data.ControllerDetails[0][17]);
                    }

                    if (data.ControllerDetails[0][15] != null) {
                        $("#ControllerLong").val("" + data.ControllerDetails[0][15]);
                    }
                    if (data.ControllerDetails[0][16] != null) {
                        $("#ControllerLat").val("" + data.ControllerDetails[0][16]);
                    }
                    if (data.ControllerDetails[0][2] != null) {
                        $("#serialNumb").val("" + data.ControllerDetails[0][2]);
                    }
                    if (data.ControllerDetails[0][3] != null) {
                        $("#macAddress").val("" + data.ControllerDetails[0][3]);
                    }
                    if (data.ControllerDetails[0][18] != null) {
                        $("#controllerCreateDate").val("" + data.ControllerDetails[0][18]);
                    }

                    if (data.ControllerDetails[0][20] != null) {
                        $("#controllerLastScanDate").val("" + data.ControllerDetails[0][20]);
                    }

                    if (data.ControllerDetails[0][21] != null) {
                        $("#ControllerStatus").val("" + data.ControllerDetails[0][21]);
                    }
                    if (data.ControllerDetails[0][19] != null) {
                        $("#controllerLastModifiedDate").val("" + data.ControllerDetails[0][19]);
                    }
                    if (data.ControllerDetails[0][4] != null) {
                        $("#ipAddress").val("" + data.ControllerDetails[0][4]);
                    }

                    if (data.ControllerDetails[0][5] != null) {
                        $("#subnetMask").val("" + data.ControllerDetails[0][5]);
                    }
                    if (data.ControllerDetails[0][6] != null) {
                        $("#gateWay").val("" + data.ControllerDetails[0][6]);
                    }

                    if (data.ControllerDetails[0][7] != null) {
                        $("#userName").val("" + data.ControllerDetails[0][7]);
                    }

                    if (data.ControllerDetails[0][8] != null) {
                        $("#password").val("" + data.ControllerDetails[0][8]);
                    }

                    if (data.ControllerDetails[0][12] != null) {
                        $("#numbOfPanels").val("" + data.ControllerDetails[0][12]);
                    }

                    if (data.ControllerDetails[0][13] != null) {
                        $("#numbOfPorts").val("" + data.ControllerDetails[0][13]);
                    }
                    if (data.ControllerDetails[0][14] != null) {
                        if (data.ControllerDetails[0][14] == "backbone") {
                            $("#networkLayer").prop("selectedIndex", 1);
                        }
                        else if (data.ControllerDetails[0][14] == "metro") {
                            $("#networkLayer").prop("selectedIndex", 2);
                        }
                        else if (data.ControllerDetails[0][14] == "access") {
                            $("#networkLayer").prop("selectedIndex", 3);
                        }
                    }
                    dbContNtLevel = document.getElementById("networkLayer").value;


                    // handle data here
                },
                error: function(result) {
                    alert("Error");
                }
            });
        }
    }
];

if (addDB === '1') {
    singleControllerMenu.unshift({
        icon: 'folder-plus',
        name: 'Create New Board',
        action: () => {

            // Reset header and modal
            document.getElementById("DistributionBoardheader").textContent = "Distribution Board: ";
            const modal = document.getElementById("distributionBoardModal");
            $(modal).modal('show');  // keep Bootstrap modal trigger
            modal.querySelectorAll('input').forEach(input => input.value = '');
            document.querySelector("#DbMappingTable > tbody").innerHTML = '';
            document.getElementById("DBMappingFlag").value = "new DB";
            document.getElementById('DBnetlevel').disabled = true;

            // Checkboxes setup
            const siteDB = document.getElementById("site_DBAutoComplete");
            const customerDB = document.getElementById("customer_DBAutoComplete");

            siteDB.checked = true;
            siteDB.value = "1";
            customerDB.checked = false;
            customerDB.value = "0";

            // Controller info
            document.getElementById("distController").style.display = "block";
            document.getElementById("distControllerName").style.display = "block";
            document.getElementById("DBController").value = selectedControllerId;
            document.getElementById("DBControllerName").value = selectedControllerName.split("/")[0];

            // Hide/show fields
            document.getElementById("DBClientId").style.display = "none";
            document.getElementById("DBClientName").style.display = "none";
            document.getElementById("BDClientPhoneNb").style.display = "none";
            document.getElementById("BDWarehouse").style.display = "block";
            document.getElementById("DBSite").style.display = "block";
            document.getElementById("DBSiteName").style.display = "block";

            // Reset context
            selectedDistBoardContext = "";
            actiondistBoardContext = "Insert";
            document.getElementById("DBType").value = "active";

            // Fetch controller location
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/getControllerLocation',
                async: false,
                data: { "selectedControllerId": selectedControllerId },
                dataType: "json",
                success: function(data) {


                    // Lat/Long
                    document.getElementById("DistributionBoardLat").value = data.ControllerLocation[0][1];
                    document.getElementById("DistributionBoardLong").value = data.ControllerLocation[0][0];

                    // WARE case
                    if (data.ControllerLocation[0][4].includes("WARE")) {
                        siteDB.checked = true;
                        siteDB.value = "1";
                        customerDB.checked = false;
                        customerDB.value = "0";

                        document.getElementById("DBClientId").style.display = "none";
                        document.getElementById("DBClientName").style.display = "none";
                        document.getElementById("BDClientPhoneNb").style.display = "none";

                        document.getElementById("BDWarehouse").style.display = "block";
                        document.getElementById("DBSite").style.display = "block";
                        document.getElementById("DBSiteName").style.display = "block";

                        document.getElementById("DistributionBoardSite").value = data.ControllerLocation[0][2];
                        document.getElementById("DistributionBoardSiteName").value = data.ControllerLocation[0][3];
                        document.getElementById("DistributionBoardWarehouse").value = data.ControllerLocation[0][4];
                    }
                    // Client case
                    else {
                        customerDB.checked = true;
                        customerDB.value = "1";
                        siteDB.checked = false;
                        siteDB.value = "0";

                        document.getElementById("BDWarehouse").style.display = "none";
                        document.getElementById("DBSite").style.display = "none";
                        document.getElementById("DBSiteName").style.display = "none";

                        document.getElementById("DBClientId").style.display = "block";
                        document.getElementById("DBClientName").style.display = "block";
                        document.getElementById("BDClientPhoneNb").style.display = "block";

                        document.getElementById("DistributionBoardClient").value = data.ControllerLocation[0][2];
                        document.getElementById("DistributionBoardClientName").value = data.ControllerLocation[0][3];
                        document.getElementById("DistributionBoardClientPhoneNb").value = data.ControllerLocation[0][4];
                    }

                    // City
                    document.getElementById("boardCity").value = data.ControllerLocation[0][5];

                    document.getElementById("boardCity").value = data.ControllerLocation[0][5];

                    let select = document.getElementById("DBnetlevel");
                    select.value = data.ControllerLocation[0][6];
                },
                error: function() {
                    alert("Error while fetching ControllerLocation");
                }
            });

        }
    });
}



singleController = new ContextMenu({
    'theme': 'default',
    'items': singleControllerMenu
});
function getDB(type, url, id, tr, showDBflag) {
    $('body').append('<div id="loading"><img id="loading-image" src="' + getContext() + '/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if (DBFlag == 0) {

        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/getDB',
            data: {
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    if (data.searchResult != "Failed") {
                        $("#DistributionBoard_f_CurrentPhysicalLayer input[type=checkbox]").unbind();
                        createController(data.ControllerList);
                        createDB(data.DBList);
                        if ($('.AllDistBoards ').is(':checked') || $('#distBoardCheckAllBoq').is(':checked')) {
                            DBLayerCheckAll();
                        }
                        $('#DistributionBoard_f_CurrentPhysicalLayer .TreeSpan').css("display", "inline"); // The purpose of this command is to let the background color width in mouse hovering or mouse select to be same span text width
                        DBFlag = 1;
                    }
                    $("#loading").remove();
                    if (showDBflag != null) {
                        if (showDBflag.length == 3) {
                            showDB(showDBflag[0], showDBflag[1], showDBflag[2]);
                        }
                        else {
                            showHideRealPoints(showDBflag[0], showDBflag[1], showDBflag[2]);
                        }
                    }
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }
}

function DBLayerCheckAll() {
    $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", true);
    $("#DistributionBoard_backbone__CurrentPhysicalLayer input[type=checkbox]").prop("checked", true);
    $("#DistributionBoard_metro__CurrentPhysicalLayer input[type=checkbox]").prop("checked", true);
    $("#DistributionBoard_access__CurrentPhysicalLayer input[type=checkbox]").prop("checked", true);
    $("#BackboneControllerDB__CurrentPhysicalLayer").prop("checked", true);
    $("#MetroControllerDB__CurrentPhysicalLayer").prop("checked", true);
    $("#AccessControllerDB__CurrentPhysicalLayer").prop("checked", true);
    controllerLayerCheckAll("backbone");
    controllerLayerCheckAll("metro");
    controllerLayerCheckAll("access");

    $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function() {
        var ID = $(this).attr('id');
        var DBID = "";
        var controllerID = "";

        if (ID && ID.includes("Controller")) {
            return true;
        }

        else {
            DBID = ID;
        }

        // Skip if marker doesn't exist
        if (!markersDistBoard[DBID]) {
            return;
        }

        $("#" + DBID).children(':checkbox').prop("checked", true);
        if (markersDistBoard[DBID].getMap() == null) {
            markersDistBoard[DBID].setMap(map);
            if (window["" + DBID][8] == "backbone") {
                markerClusterBackboneDistBoard.addMarker(markersDistBoard[DBID]);
            }
            else if (window["" + DBID][8] == "metro") {
                markerClusterMetroDistBoard.addMarker(markersDistBoard[DBID]);
            }
            else if (window["" + DBID][8] == "access") {
                markerClusterAccessDistBoard.addMarker(markersDistBoard[DBID]);
            }
        }
        $("#" + DBID).children(':checkbox').prop("checked", true);
    });

    if ($("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked").length == 0) {
        $("#distBoardCheckAllBoq").prop("checked", false);
    }
    else {
        $("#distBoardCheckAllBoq").prop("checked", true);
    }
}

function DBLayerUnCheckAll() {

    $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", false);
    $("#DistributionBoard_backbone__CurrentPhysicalLayer  input[type=checkbox]").prop("checked", false);
    $("#DistributionBoard_metro__CurrentPhysicalLayer  input[type=checkbox]").prop("checked", false);
    $("#DistributionBoard_access__CurrentPhysicalLayer  input[type=checkbox]").prop("checked", false);
    markerClusterBackboneDistBoard.clearMarkers();
    markerClusterMetroDistBoard.clearMarkers();
    markerClusterAccessDistBoard.clearMarkers();
    $("#BackboneControllerDB__CurrentPhysicalLayer").prop("checked", false);
    $("#MetroControllerDB__CurrentPhysicalLayer").prop("checked", false);
    $("#AccessControllerDB__CurrentPhysicalLayer").prop("checked", false);
    controllerLayerUnCheckAll("backbone");
    controllerLayerUnCheckAll("metro");
    controllerLayerUnCheckAll("access");

    $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function() {
        var DB = $(this).attr('id');
        var DBID = $(this).attr('id');
        if (DBID && DBID.startsWith("Controller")) {
            return true; // continue to next
        }
        // Skip if marker doesn't exist
        if (!markersDistBoard[DBID]) {
            return;
        }
        markersDistBoard[DB].setMap(null);
        $("#" + DB).children(':checkbox').prop("checked", false);
    });

    $("#network_tree").find(".DistBoard:checked").each(function() {
        id = $(this).parent().attr('id');
        if (markersDistBoard[id].getMap() == null) {
            markersDistBoard[id].setMap(map);
            if (window["" + DBID][8] == "backbone") {
                markerClusterBackboneDistBoard.addMarker(markersDistBoard[DBID]);
            }
            else if (window["" + DBID][8] == "metro") {
                markerClusterMetroDistBoard.addMarker(markersDistBoard[DBID]);
            }
            else if (window["" + DBID][8] == "access") {
                markerClusterAccessDistBoard.addMarker(markersDistBoard[DBID]);
            }
        }
    });
}

function createDB(distribBoardList, transfer) {
    if (typeof markerClusterBackboneDistBoard === "undefined") {
        var checkExist = setInterval(function() {
            if (typeof markerClusterBackboneDistBoard !== "undefined") {
                clearInterval(checkExist);
                console.log("markerClusterBackboneDistBoard is ready");
                createDB(distribBoardList, transfer);
            }
        }, 100);
        return; // exit for now
    }
    console.log(distribBoardList);

    if (distribBoardList != null) {
        for (i = 0;i < distribBoardList.length;i++) {
            allDB.push(distribBoardList[i][0]);
            window["" + distribBoardList[i][0]] = [];
            window["" + distribBoardList[i][0]] = distribBoardList[i];

            if (distribBoardList[i][9] === "passive" || distribBoardList[i][10] === "passive") {
                if (distribBoardList[i][8] == "backbone") {
                    str = "<ul><li id='" + distribBoardList[i][0] + "'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/backboneDB.png'> " + distribBoardList[i][3] + "/" + distribBoardList[i][0] + " </span></li></ul>";
                    $("#DistributionBoard_backbone__" + distribBoardList[i][6]).append(str);
                    create_DB_Marker_Click(distribBoardList[i][0], distribBoardList[i][3], distribBoardList[i][1], distribBoardList[i][2], markersDistBoard, markerClusterBackboneDistBoard, "", "");
                    DBCheckFilter(distribBoardList[i][0], markerClusterBackboneDistBoard);
                }
                else if (distribBoardList[i][8] == "metro") {
                    str = "<ul><li id='" + distribBoardList[i][0] + "'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/metroDb.png'> " + distribBoardList[i][3] + "/" + distribBoardList[i][0] + " </span></li></ul>";
                    $("#DistributionBoard_metro__" + distribBoardList[i][6]).append(str);
                    create_DB_Marker_Click(distribBoardList[i][0], distribBoardList[i][3], distribBoardList[i][1], distribBoardList[i][2], markersDistBoard, markerClusterMetroDistBoard, "", "");
                    DBCheckFilter(distribBoardList[i][0], markerClusterMetroDistBoard);
                }
                else if (distribBoardList[i][8] == "access") {
                    str = "<ul><li id='" + distribBoardList[i][0] + "'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/accessDb.png'> " + distribBoardList[i][3] + "/" + distribBoardList[i][0] + " </span></li></ul>";
                    $("#DistributionBoard_access__" + distribBoardList[i][6]).append(str);
                    create_DB_Marker_Click(distribBoardList[i][0], distribBoardList[i][3], distribBoardList[i][1], distribBoardList[i][2], markersDistBoard, markerClusterAccessDistBoard, "", "");
                    DBCheckFilter(distribBoardList[i][0], markerClusterAccessDistBoard);
                }
            }
            else if (distribBoardList[i][9] === "active" || distribBoardList[i][10] === "active") {
                if (distribBoardList[i][8] == "backbone") {
                    str = "<ul><li id='" + distribBoardList[i][0] + "'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/backboneDB.png'> " + distribBoardList[i][3] + "/" + distribBoardList[i][0] + " </span></li></ul>";
                    if (distribBoardList[i][10] === "active") {
                        $("#" + distribBoardList[i][11]).append(str);
                    }
                    else {
                        $("#" + distribBoardList[i][10]).append(str);
                    }
                    create_DB_Marker_Click(distribBoardList[i][0], distribBoardList[i][3], distribBoardList[i][1], distribBoardList[i][2], markersDistBoard, markerClusterBackboneDistBoard, "", "");
                    DBCheckFilter(distribBoardList[i][0], markerClusterBackboneDistBoard);
                }
                else if (distribBoardList[i][8] == "metro") {
                    str = "<ul><li id='" + distribBoardList[i][0] + "'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/metroDb.png'> " + distribBoardList[i][3] + "/" + distribBoardList[i][0] + " </span></li></ul>";
                    if (distribBoardList[i][10] === "active") {
                        $("#" + distribBoardList[i][11]).append(str);
                    }
                    else {
                        $("#" + distribBoardList[i][10]).append(str);
                    }
                    create_DB_Marker_Click(distribBoardList[i][0], distribBoardList[i][3], distribBoardList[i][1], distribBoardList[i][2], markersDistBoard, markerClusterBackboneDistBoard, "", "");
                    DBCheckFilter(distribBoardList[i][0], markerClusterMetroDistBoard);
                }
                else if (distribBoardList[i][8] == "access") {
                    str = "<ul><li id='" + distribBoardList[i][0] + "'  class='DistributionBoard' style='display:none;width:100px;'><input type='checkbox' class='DistBoard checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/accessDb.png'> " + distribBoardList[i][3] + "/" + distribBoardList[i][0] + " </span></li></ul>";
                    if (distribBoardList[i][10] === "active") {
                        $("#" + distribBoardList[i][11]).append(str);
                    }
                    else {
                        $("#" + distribBoardList[i][10]).append(str);
                    }
                    create_DB_Marker_Click(distribBoardList[i][0], distribBoardList[i][3], distribBoardList[i][1], distribBoardList[i][2], markersDistBoard, markerClusterBackboneDistBoard, "", "");
                    DBCheckFilter(distribBoardList[i][0], markerClusterAccessDistBoard);
                }
            }
        }

        $(".DistributionBoard > .TreeSpan").contextmenu(function() {
            menuName = singleDistBoard;
            let label = $(this).parents("li").eq(2).children(".TreeSpan").first().text().trim();
            if (label.startsWith("Controllers")) {
                IdNodeSelectedTemp = $(this).parents().eq(4).attr('id').split("__")[1];
                console.log("It is controller folder");
                console.log("IdNodeSelectedTemp is " + IdNodeSelectedTemp);
            }
            else {
                IdNodeSelectedTemp = $(this).parents().eq(2).attr('id').split("__")[1];
                console.log("It is passive");
            }
            selectedDistBoardContext = $(this).parents().attr('id');
            selectedDistBoardName = $(this).text();
            openContext(selectedDistBoardContext, selectedDistBoardName, singleDistBoard, event);
        });

        $(".DistBoard  > .TreeSpan").contextmenu(function() {
            menuName = singleDistBoard;
            selectedDistBoardContext = $(this).attr('id');
            selectedDistBoardName = $(this).text();
            openContext(selectedDistBoardContext, selectedDistBoardName, singleDistBoard, event);
        });
    }
    AllDistributionBoardCheckFilter("DistributionBoard_backbone__CurrentPhysicalLayer", markerClusterBackboneDistBoard);
    AllDistributionBoardCheckFilter("DistributionBoard_metro__CurrentPhysicalLayer", markerClusterMetroDistBoard);
    AllDistributionBoardCheckFilter("DistributionBoard_access__CurrentPhysicalLayer", markerClusterAccessDistBoard);
    AllDistributionBoardCheckFilter("DistributionBoard_f_CurrentPhysicalLayer", "");
}


function create_DB_Marker_Click(Id, Name, Long, Lat, markers, marker_Cluster, Type, city) {
    const pos = new google.maps.LatLng(Lat, Long);
    var data = "<div>" + Name + "</div>";
    var mapIcon;
    var markerType = "";

    iconBackboneDB = {
        url: getContext() + "/resources/NetworkImages/backboneDB.png", // url
        scaledSize: new google.maps.Size(20, 20), // scaled size

    };
    iconMetroDB = {
        url: getContext() + "/resources/NetworkImages/metroDb.png", // url
        scaledSize: new google.maps.Size(20, 20), // scaled size

    };
    iconAccessDB = {
        url: getContext() + "/resources/NetworkImages/accessDb.png", // url
        scaledSize: new google.maps.Size(20, 20), // scaled size
    };

    markerClusterBackboneDistBoard.setOptions({
        minimumClusterSize: 2,
        styles: [
            {
                url: getContext() + '/resources/clusterIcons/blueCluster.png',
                height: 60,
                width: 60,
                anchorText: [-3, -3]
            },
        ],
        calculator: function(markers, numStyles) {
            if (markers.length >= 1) return { text: markers.length, index: 1 };
        }
    });

    markerClusterMetroDistBoard.setOptions({
        minimumClusterSize: 2,
        styles: [
            {
                url: getContext() + '/resources/clusterIcons/dbMetroCluster.png',
                height: 60,
                width: 60,
                anchorText: [-3, -3]
            },
        ],
        calculator: function(markers, numStyles) {
            if (markers.length >= 1) return { text: markers.length, index: 1 };
        }
    });

    markerClusterAccessDistBoard.setOptions({
        minimumClusterSize: 2,
        styles: [
            {
                url: getContext() + '/resources/clusterIcons/dbAccessCluster.png',
                height: 60,
                width: 60,
                anchorText: [-3, -3]
            },
        ],
        calculator: function(markers, numStyles) {
            if (markers.length >= 1) return { text: markers.length, index: 1 };
        }
    });

    if (markers == markersDistBoard && marker_Cluster == markerClusterBackboneDistBoard) {
        mapIcon = iconBackboneDB;
        markerType = "DistributionBoard";
    }
    else if (markers == markersDistBoard && marker_Cluster == markerClusterMetroDistBoard) {
        mapIcon = iconMetroDB;
        markerType = "DistributionBoard";
    }
    else if (markers == markersDistBoard && marker_Cluster == markerClusterAccessDistBoard) {
        mapIcon = iconAccessDB;
        markerType = "DistributionBoard";
    }

    if (!markers[Id]) {
        Mapmarker = new google.maps.Marker({
            position: pos,
            data: data,
            icon: mapIcon,
            ID: Id,
        });
        markers.metadata = { id: Id };
        markers[Id] = Mapmarker;
        markers.push(Mapmarker);

        google.maps.event.addListener(Mapmarker, "click", function(e) {
            var IdSelected = this.ID;

            dbFileId = $("#" + IdSelected).parent().parent().attr('id').split("__")[1];
            if (!dbFileId) {
                dbFileId = $("#" + IdSelected).parent().parent().parent().parent().attr('id').split("__")[1];
            }
            var childrenInitial = $("#initial_ul_" + dbFileId + "").find(' > ul > li');
            var children = $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li');
            var networkLevelFolder = $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li ');
            var networkControllerFolders = $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li ul > li');
            var networkController = $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li ul > li ul > li');

            if (IdSelected != IdSelectedTemp) {
                if (IdSelectedTemp != "") {
                    $("#" + IdSelectedTemp + " > .TreeSpan").removeClass("selected-span");
                    $("#" + IdSelectedTemp + " > .TreeSpan").css("background", "");
                }
                IdSelectedTemp = IdSelected;
            }
            childrenInitial.show('fast');
            if (!children.is(":visible")) {
                children.show();
            }
            networkLevelFolder.show();
            networkControllerFolders.show();
            networkController.show();
            $("#" + IdSelected + " > .TreeSpan").addClass("selected-span");
            $("#" + IdSelected + " > .TreeSpan").css("background-color", "#97b9cc");
            $("#initial_ul_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li  > ul > li > .folder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li  > ul > li > ul > li>  .folder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');

            offset = $("#" + IdSelected).offset().top;
            projectOffset = $("#initial_ul_" + dbFileId).offset().top;
            offsetTotal = (offset - projectOffset);
            $("#network_tree").animate({ scrollTop: offsetTotal }, "slow");
            if (draw == true) {
                createPathh(e);
            }

            if (deleting == true) {
                deleteAuxPath(e);
                MarkerArrayId.push(Id);
            }
        });
    }
    else {
        if (markers[Id].map != map) {
            markers[Id].setMap(map);
        }
        markers[Id].setPosition(pos);
        markers[Id].data = data;
    }

    $("#" + Id + " .TreeSpan").on('click', function(e) {
        /*		
                e.preventDefault();
                e.stopPropagation();
        */
        // Now pan to the marker
        panTo(markers[Id].getPosition().lat(), markers[Id].getPosition().lng());
        map.setZoom(11);

        if (typeof infowindow !== 'undefined') {
            infowindow.close();
        } else {
            infowindow = new google.maps.InfoWindow();
        }
        infowindow.setContent(Id);
        infowindow.open(map, markers[Id]);
    });
}

function DBCheckFilter(Id, clusterName) {

    $("#" + Id).children('input').bind("change", function() {
        var folderID = $(this).parents().eq(4).attr('id');
        let $parent = $(this).parents().eq(2);
        let parentId = $(this).parents().eq(2).attr("id");
        if ($(this).is(':checked')) {
            markersDistBoard[Id].setMap(map);
            clusterName.addMarker(markersDistBoard[Id]);
        }
        else {
            if (folderID == "initial_ul_CurrentPhysicalLayer") {
                $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", false);
            }
            else {
                $("#DistributionBoard_f_" + folderID + " > .AllDistBoards").prop("checked", false);
            }
            markersDistBoard[Id].setMap(null);
            clusterName.removeMarker(markersDistBoard[Id]);
        }
        if ($(this).parents().eq(2).find('.DistributionBoard :checked').length == $(this).parents().eq(2).find('.DistributionBoard').length) {
            if (parentId && !parentId.toLowerCase().includes("controller")) {
                $parent.children('input[type=checkbox]').prop('checked', true);
            }
        }
        else {

            if (parentId && !parentId.toLowerCase().includes("controller")) {
                $parent.children('input[type=checkbox]').prop('checked', false);
            }
        }

        if ($("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard :checked").length == 0) {
            $("#distBoardCheckAllBoq").prop("checked", false);
        }
        else {
            $("#distBoardCheckAllBoq").prop("checked", true);
        }
        if ($("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard :checked").length == $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard").length) {
            $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", true);
        }
        else {
            $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", false);
        }
    });
}

//transfer id for the controller transfered from different layer 

// Utility to get container selector based on network layer
function getContainerSelector(networkLayer) {
    switch (networkLayer) {
        case "backbone": return "#DistributionBoard_backboneController__CurrentPhysicalLayer";
        case "metro": return "#DistributionBoard_metroController__CurrentPhysicalLayer";
        case "access": return "#DistributionBoard_accessController__CurrentPhysicalLayer";
        default: return "";
    }
}

function controllerLayerCheckAll(layer) {

    let selector = "#DistributionBoard_" + layer + "Controller__CurrentPhysicalLayer"; // Added #

    // Check main checkbox for this layer

    // Loop over its <li> elements inside <ul>
    $(selector).find('ul > li').each(function() {
        let controllerId = $(this).attr('id');
        if (!controllerId) return;
        if (controllerId.includes("Controller")) {
            // Check the checkbox inside this li
            $(this).children(':checkbox').prop("checked", true);

            // Show the marker if it’s hidden
            if (markersController[controllerId] && markersController[controllerId].getMap() == null) {
                markersController[controllerId].setMap(map);

                markerClusterController.addMarker(markersController[controllerId]);
            }
        }
    });
}


function controllerLayerUnCheckAll(layer) {

    let selector = "#DistributionBoard_" + layer + "Controller__CurrentPhysicalLayer";
    $(selector).prop("checked", false);

    // Loop over all <li> inside the UL(s) of this layer
    $(selector).find('ul > li').each(function() {
        let controllerId = $(this).attr('id');


        if (!controllerId) return;

        // Uncheck the li’s own checkbox
        if (controllerId.includes("Controller")) {
            $(this).children(':checkbox').prop("checked", false);
            // Remove marker from map if it’s visible
            if (markersController[controllerId] && markersController[controllerId].getMap() != null) {
                markersController[controllerId].setMap(null);
                markerClusterController.removeMarker(markersController[controllerId]);
            }
        }
    });
}

function createController(controllerList, DBList) {
    if (typeof markersController === "undefined") {
        var checkExist = setInterval(function() {
            if (typeof markersController !== "undefined") {
                clearInterval(checkExist);
                console.log("markersController is ready");
                createController(controllerList, DBList);
            }
        }, 100);
        return; // exit for now
    }

    for (let i = 0;i < controllerList.length;i++) {

        allCont.push(controllerList[i][0]);
        window["" + controllerList[i][0]] = [];
        window["" + controllerList[i][0]] = controllerList[i];

        let layer = controllerList[i][4]; // networkLayer
        let id = controllerList[i][0];
        let name = controllerList[i][3];
        let dbCount = controllerList[i][5];

        if (layer == "backbone") {
            if (dbCount > 0) {
                str = "<ul><li id='" + id + "' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
                $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
            }
            else {
                str = "<ul><li id='" + id + "' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
                $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
            }
            $(".bController > .TreeSpan").contextmenu(function(event) {
                selectedControllerId = $(this).parent().attr('id');
                selectedControllerName = $(this).text();
                openContext(selectedControllerId, selectedControllerName, singleController, event);
            });
        }
        else if (layer == "metro") {
            if (dbCount > 0) {
                str = "<ul><li id='" + id + "'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>					<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span>  <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
                $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
            }
            else {
                str = "<ul><li id='" + id + "'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
                $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
            }
            $(".mController > .TreeSpan").contextmenu(function(event) {
                selectedControllerId = $(this).parent().attr('id');
                selectedControllerName = $(this).text();
                openContext(selectedControllerId, selectedControllerName, singleController, event);
            });
        }

        else if (layer == "access") {
            if (dbCount > 0) {
                str = "<ul><li id='" + id + "'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>	<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
                $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
            }
            else {
                str = "<ul><li id='" + id + "'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
                $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
            }
            $(".aController > .TreeSpan").contextmenu(function(event) {
                selectedControllerId = $(this).parent().attr('id');
                selectedControllerName = $(this).text();
                openContext(selectedControllerId, selectedControllerName, singleController, event);
            });
        }

        createControllerMarkerClick(id, name, controllerList[i][1], controllerList[i][2], markersController, markerClusterController);
        controllerCheckFilter(id, markerClusterController);
        // Context menu binding for controllers
        AllControllerCheckFilter("DistributionBoard_backboneController__CurrentPhysicalLayer", markerClusterController);
        AllControllerCheckFilter("DistributionBoard_metroController__CurrentPhysicalLayer", markerClusterController);
        AllControllerCheckFilter("DistributionBoard_accessController__CurrentPhysicalLayer", markerClusterController);
    }
}

function createControllerMarkerClick(Id, name, long, lat, markers, markerClusterController) {
    const pos = new google.maps.LatLng(lat, long);

    // Controller icon
    const iconController = {
        url: getContext() + "/resources/NetworkImages/controller,.png",
        scaledSize: new google.maps.Size(30, 20),
    };

    // Apply cluster options only once
    if (!markerClusterController.optionsApplied) {
        markerClusterController.setOptions({
            minimumClusterSize: 2,
            styles: [
                {
                    url: getContext() + '/resources/clusterIcons/blackCluster.png',
                    height: 60,
                    width: 60,
                    anchorText: [-3, -3],
                    textColor: "white"
                }
            ],
            calculator: function(markers, numStyles) {
                if (markers.length < 2) {
                    return null; // no cluster bubble for <2 markers
                }
                return { text: markers.length.toString(), index: 1 };
            }
        });
        markerClusterController.optionsApplied = true;
    }

    // --- CREATE OR UPDATE MARKER ---
    if (!markers[Id]) {
        // Create new marker
        const Mapmarker = new google.maps.Marker({
            position: pos,
            icon: iconController,
            ID: Id
        });

        markers[Id] = Mapmarker; // store marker only (not yet added to cluster!)

        // --- CLICK EVENT on marker ---
        google.maps.event.addListener(Mapmarker, "click", function(e) {
            var IdSelected = this.ID;
            markerType = "DistributionBoard";
            dbFileId = $("#" + IdSelected).parent().parent().attr('id').split("__")[1];
            if (!dbFileId) {
                dbFileId = $("#" + IdSelected).parent().parent().parent().parent().attr('id').split("__")[1];
            }
            var childrenInitial = $("#initial_ul_" + dbFileId + "").find(' > ul > li');
            var children = $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li');
            var networkLevelFolder = $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li ');
            var networkControllerFolders = $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li ul > li');

            if (IdSelected != IdSelectedTemp) {
                if (IdSelectedTemp != "") {
                    $("#" + IdSelectedTemp + " > .TreeSpan").removeClass("selected-span");
                    $("#" + IdSelectedTemp + " > .TreeSpan").css("background", "");
                }
                IdSelectedTemp = IdSelected;
            }
            childrenInitial.show('fast');
            if (!children.is(":visible")) {
                children.show();
            }
            networkLevelFolder.show();
            networkControllerFolders.show();

            $("#" + IdSelected + " > .TreeSpan").addClass("selected-span");
            $("#" + IdSelected + " > .TreeSpan").css("background-color", "#97b9cc");
            $("#initial_ul_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li  > ul > li > .folder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + dbFileId + "").find(' > ul > li > ul > li  > ul > li > ul > li>  .folder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');

            offset = $("#" + IdSelected).offset().top;
            projectOffset = $("#initial_ul_" + dbFileId).offset().top;
            offsetTotal = (offset - projectOffset);
            $("#network_tree").animate({ scrollTop: offsetTotal }, "slow");

        });
    } else {
        // Update existing marker position
        if (markers[Id].map !== map) {
            markers[Id].setMap(map);
        }
        markers[Id].setPosition(pos);
    }

    // --- CLICK EVENT on tree span ---
    $("#" + Id + " .TreeSpan")
        .not($("#" + Id + "_f .TreeSpan"))
        .off("click")
        .on("click", function() {
            const marker = markers[Id];
            panTo(marker.getPosition().lat(), marker.getPosition().lng());
            map.setZoom(11);

            if (typeof infowindow !== 'undefined') {
                infowindow.close();
            } else {
                infowindow = new google.maps.InfoWindow();
            }
            infowindow.setContent(Id);
            infowindow.open(map, marker);
        });
}


function AllControllerCheckFilter(containerId, cluster) {

    // Bind change only to the main checkbox (assuming it's the first input in the container)
    $("#" + containerId).find('input').first().bind("change", function() {

        // Clear cluster before updating
        if (!cluster) {
            markerClusterController.clearMarkers();
        } else {
            cluster.clearMarkers();
        }

        // Create an array to store DB IDs

        // Process checking/unchecking
        if ($(this).is(':checked')) {

            if (containerId.includes("backbone")) {
                controllerLayerCheckAll("backbone");
            } else if (containerId.includes("metro")) {
                controllerLayerCheckAll("backbone");
            } else {
                controllerLayerCheckAll("access");
            }
        } else {
            if (containerId.includes("backbone")) {
                controllerLayerUnCheckAll("backbone");
            } else if (containerId.includes("metro")) {
                controllerLayerUnCheckAll("backbone");
            } else {
                controllerLayerUnCheckAll("access");
            }
        }
    });
}

$(document).on('change', '.metroControllerDB', function() {
    if ($(this).is(':checked')) {

        controllerLayerCheckAll("metro");
    } else {
        controllerLayerUnCheckAll("metro");
    }
});

$(document).on('change', '.accessControllerDB', function() {
    if ($(this).is(':checked')) {

        controllerLayerCheckAll("access");
    } else {
        controllerLayerUnCheckAll("access");
    }
});



function controllerCheckFilter(controllerId, cluster) {

    // Bind change event on checkbox inside the controller element
    $("#" + controllerId).children('input[type=checkbox]').off('change').on('change', function() {
        var folderID = $(this).parents().eq(4).attr('id'); // Adjust based on your DOM depth
        let containerId = "#" + controllerId;
        let allIds = [];
        // find the <li> children inside that container
        $(containerId).find("li.DistributionBoard").each(function() {
            allIds.push($(this).attr("id"));
        });
        if ($(this).is(':checked')) {

            // Show marker and add to cluster
            if (markersController[controllerId]) {
                markersController[controllerId].setMap(map);
                cluster.addMarker(markersController[controllerId]);
            }
        } else {

            // Uncheck the "AllDistBoards" checkbox related to this folder
            if (folderID === "initial_ul_CurrentPhysicalLayer") {
                $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", false);
            } else {
                $("#DistributionBoard_f_" + folderID + " > .AllDistBoards").prop("checked", false);
            }
            // Remove marker from map and cluster
            if (markersController[controllerId]) {
                markersController[controllerId].setMap(null);
                cluster.removeMarker(markersController[controllerId]);
            }
        }

        // Update parent checkbox if all children are checked/unchecked
        var parentContainer = $(this).parents().eq(2);
        var allChecked = parentContainer.find('.ControllerPannel :checked').length === parentContainer.find('.ControllerPannel').length;
        parentContainer.children('input[type=checkbox]').prop('checked', allChecked);

        // Update global select-all checkbox based on checked nodes
        var checkedDbs = $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard :checked").length;
        var totalDbs = $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard ").length;

        $("#distBoardCheckAllBoq").prop("checked", checkedDbs > 0);
        $("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked", checkedDbs === totalDbs);
    });
}

function appendControllerToTree(data) {
    let str = "";
    let dbCount = parseInt(data.controllerDBCount || "0");
    let id = data.controllerId;
    let name = data.controllerName;
    if (data.networkLayer == "backbone") {
        if (dbCount > 0) {
            str = "<ul><li id='" + id + "' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
            $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
        }
        else {
            str = "<ul><li id='" + id + "' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
            $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
        }
        $(".bController > .TreeSpan").contextmenu(function(event) {
            selectedControllerId = $(this).parent().attr('id');
            selectedControllerName = $(this).text();
            openContext(selectedControllerId, selectedControllerName, singleController, event);
        });
    }
    else if (data == "metro") {
        if (dbCount.length > 0) {
            str = "<ul><li id='" + id + "'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>					<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span>  <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
            $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
        }
        else {
            str = "<ul><li id='" + id + "'  class='mController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";

            $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
        }
        $(".mController > .TreeSpan").contextmenu(function(event) {
            selectedControllerId = $(this).parent().attr('id');
            selectedControllerName = $(this).text();
            openContext(selectedControllerId, selectedControllerName, singleController, event);
        });
    }

    else if (data.networkLayer == "access") {
        if (dbCount > 0) {
            str = "<ul><li id='" + id + "'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input>	<span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";
            $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
        }
        else {
            str = "<ul><li id='" + id + "'  class='aController' style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter' ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + name + "/" + id + " </span></li></ul>";

            $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
        }
        $(".aController > .TreeSpan").contextmenu(function(event) {
            selectedControllerId = $(this).parent().attr('id');
            selectedControllerName = $(this).text();
            openContext(selectedControllerId, selectedControllerName, singleController, event);
        });
    }
}

function handleUpdateController(data, cont, db) {

    if (data.networkLayer != dbContNtLevel) {
        $("#" + data.controllerId).remove();
        createController(cont);
        createDB(db);
    } else {
        let imgPath = "";
        if (data.networkLayer == "backbone") {
            imgPath = "/resources/NetworkImages/controller,.png";
        } else if (data.networkLayer == "metro") {
            imgPath = "/resources/NetworkImages/controller,.png";
        } else if (data.networkLayer == "access") {
            imgPath = "/resources/NetworkImages/controller,.png";
        }

        $("#" + data.controllerId + "> .TreeSpan").html(
            "<img class='image' src='" + getContext() + imgPath + "'> " +
            data.controllerName + "/" + data.controllerId + " </span></li>"
        );
    }
}

function addKit() {
    const markup = `<tr>
		         <td style='text-align:center;'><input  name='record' type='checkbox' style='vertical-align: middle; width:70px'></td>
		         <td name='serialNum'><input  class='form-control' type='text' style='width:400px;'/></td>
				 <td name='type'><input  class='form-control' type='text' style='width:400px;'/></td>
		          </tr>`;
    $("#DbKit > tbody").append(markup);
};


function deleteKit() {
    var check = 0;
    // Remove checked rows from the table
    $("#DbKit > tbody").find('input[name="record"]').each(function() {
        if ($(this).is(":checked")) {
            $(this).closest("tr").remove();
            let deletedId = $(this).attr("id");
            if (deletedId) {
                deletedKitIds.push(deletedId);
                check++; // store deleted id
            }
        }
    });
    if (check == 0) {

        alert("Select Row(s) to Delete");
    }
    console.log(deletedKitIds);
};

function selectAllKit(btn) {
    if ($(btn).hasClass('allChecked')) {
        // Uncheck all checkboxes with name="record"
        $('#DbKit input[name="record"]').prop('checked', false);
        $(btn).removeClass('allChecked');
    } else {
        // Check all checkboxes with name="record"
        $('#DbKit input[name="record"]').prop('checked', true);
        $(btn).addClass('allChecked');
    }
}

function selectAllModule(btn) {
    if ($(btn).hasClass('allChecked')) {
        // Uncheck all checkboxes with name="record"
        $('#DbModule input[name="record"]').prop('checked', false);
        $(btn).removeClass('allChecked');
    } else {
        // Check all checkboxes with name="record"
        $('#DbModule input[name="record"]').prop('checked', true);
        $(btn).addClass('allChecked');
    }
}

function addModule() {
    const markup = `<tr>
			 		         <td style='text-align:center;'><input  name='record' type='checkbox' style='vertical-align: middle; width:70px'></td>
			 		         <td name='modulePos'><input  class='form-control' type='text' style='width:80px;'/></td>
			 				 <td name='kitSerialNum'><input  class='form-control' type='text' style='width:300px;'/></td>
			 				 <td name='orientation'><input  class='form-control' type='text' style='width:300px;'/></td>
			 				 <td name='lowestPortNum'><input  class='form-control' type='text' style='width:800px;'/></td>
			 				 <td name='sensorsPerPortNum'><input  class='form-control' type='text' style='width:80px;'/></td>
			 				 <td name='sensorCount'><input  class='form-control' type='text' style='width:80px;'/></td>
			 				 <td name='occupiedSensorMask'><input  class='form-control' type='text' style='width:100px;'/></td>
			 		          </tr>`;
    $("#DbModule > tbody").append(markup);
};

function deleteModule() {
    var check = 0;
    // Remove checked rows from the table
    $("#DbModule > tbody").find('input[name="record"]').each(function() {
        if ($(this).is(":checked")) {
            $(this).closest("tr").remove();
            let deletedId = $(this).attr("id");

            if (deletedId) {
                deletedModuleIds.push(deletedId); // store deleted id
            }
            check++;
        }
    });

    if (check == 0) {
        alert("Select Row(s) to Delete");
    }
};

$("#saveController").click(function() {

    // Validate longitude
    if (isNaN(document.getElementById("ControllerLong").value)) {
        alert("Incorrect Format of longitude.");
        return false;
    }

    // Validate latitude
    else if (isNaN(document.getElementById("ControllerLat").value)) {
        alert("Incorrect Format of latitude.");
        return false;
    }

    else if (document.getElementById("controllerCity").value == "") {
        alert("City cannot be empty. ");
        return false;
    }
    ////
    else if (document.getElementById("ControllerSite").value == "" && document.getElementById('site_ControllerAutoComplete').checked) {
        alert("SiteID cannot be empty. ");
        return false;
    }
    else if (document.getElementById("ControllerSiteName").value == "" && document.getElementById('site_ControllerAutoComplete').checked) {
        alert("Site Name cannot be empty. ");
        return false;
    }
    else if (document.getElementById("ControllerWarehouse").value == "" && document.getElementById('site_ControllerAutoComplete').checked) {
        alert("WarehouseID cannot be empty. ");
        return false;
    }

    else if (document.getElementById("ControllerClient").value == "" && document.getElementById('customer_ControllerAutoComplete').checked) {
        alert("ClientID cannot be empty. ");
        return false;
    }
    else if (document.getElementById("ControllerClientName").value == "" && document.getElementById('customer_ControllerAutoComplete').checked) {
        alert("Client Name cannot be empty. ");
        return false;
    }
    else if (document.getElementById("ControllerClientPhoneNb").value == "" && document.getElementById('customer_ControllerAutoComplete').checked) {
        alert("Phone Number cannot be empty. ");
        return false;
    }

    // Check that both longitude and latitude are not empty
    else if (document.getElementById("ControllerLong").value !== "" &&
        document.getElementById("ControllerLat").value !== "") {

        let controllerId = document.getElementById("controllerId").value;
        let controllerName = document.getElementById("ControllerName").value;
        let longitude = document.getElementById("ControllerLong").value;
        let latitude = document.getElementById("ControllerLat").value;
        let serialNumber = document.getElementById("serialNumb").value;
        let macAddress = document.getElementById("macAddress").value;
        let createDate = document.getElementById("controllerCreateDate").value;
        let lastModifiedDate = document.getElementById("controllerLastModifiedDate").value;
        let lastScanDate = document.getElementById("controllerLastScanDate").value;
        let ipAddress = document.getElementById("ipAddress").value;
        let subnetMask = document.getElementById("subnetMask").value;
        let gateway = document.getElementById("gateWay").value;
        let userName = document.getElementById("userName").value;
        let password = document.getElementById("password").value;
        let numberOfPanels = document.getElementById("numbOfPanels").value;
        let numberOfPorts = document.getElementById("numbOfPorts").value;
        let networkLayer = document.getElementById("networkLayer").value;
        let ControllerSite = document.getElementById("ControllerSite").value;
        let ControllerSiteName = document.getElementById("ControllerSiteName").value;
        let ControllerWarehouse = document.getElementById("ControllerWarehouse").value;
        let ControllerClient = document.getElementById("ControllerClient").value;
        let ControllerClientName = document.getElementById("ControllerClientName").value;
        let ControllerClientPhoneNb = document.getElementById("ControllerClientPhoneNb").value;
        let ControllerCity = document.getElementById("controllerCity").value;
        let status = document.getElementById("ControllerStatus").value;

        var locationId = "";
        var locationName = "";
        var location = "";
        var isSiteChecked = "";
        var isClientChecked = "";

        if (document.getElementById('site_ControllerAutoComplete').checked) {
            isSiteChecked = true;
        }

        if (document.getElementById('customer_ControllerAutoComplete').checked) {
            isClientChecked = true;
        }
        if (isSiteChecked == true) {
            locationId = ControllerSite;
            locationName = ControllerSiteName;
            location = ControllerWarehouse;
        } else {
            locationId = ControllerClient;
            locationName = ControllerClientName;
            location = ControllerClientPhoneNb;
        }
        isSiteChecked = false;
        isClientChecked = false;

        var token = $('input[name="csrfToken"]').attr('value');


        $.ajax({
            type: "POST",
            headers: {
                'X-CSRFToken': token
            },
            url: getContext() + '/saveController',
            data: {
                "controllerId": controllerId,
                "controllerName": controllerName,
                "serialNumber": serialNumber,
                "macAddress": macAddress,
                "ipAddress": ipAddress,
                "subnetMask": subnetMask,
                "gateWay": gateway,
                "ControllerSite": locationId,
                "ControllerSiteName": locationName,
                "ControllerWarehouse": location,
                "userName": userName,
                "password": password,
                "numberOfPanels": numberOfPanels,
                "numberOfPorts": numberOfPorts,
                "networkLayer": networkLayer,
                "longitude": longitude,
                "latitude": latitude,
                "city": ControllerCity,
                "createDate": createDate,
                "lastScanDate": lastScanDate,
                "status": status,
                "actiondistControllerContext": actiondistControllerContext,
                "updateModfUser": updateModfUser
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {

                    if (data.actiondistControllerContext == "Insert") {
                        allCont.push(data.controllerId);
                        window["" + data.controllerId] = [];
                        window["" + data.controllerId] = [data.controllerId, data.lng, data.lat, data.controllerName, data.networkLayer]

                        if (networkLayer == "backbone") {


                            str = "<ul><li id='" + data.controllerId + "'  class='bController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
                            $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);

                            $(".bController > .TreeSpan").contextmenu(function(event) {
                                selectedControllerId = $(this).parent().attr('id');
                                console.log(selectedControllerId);
                                selectedControllerName = $(this).text();
                                openContext(selectedControllerId, selectedControllerName, singleController, event);
                            });

                        }
                        else if (networkLayer == "metro") {


                            str = "<ul><li id='" + data.controllerId + "'  class='mController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
                            $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);

                            $(".mController > .TreeSpan").contextmenu(function(event) {
                                selectedControllerId = $(this).parent().attr('id');
                                console.log(selectedControllerId);
                                selectedControllerName = $(this).text();
                                openContext(selectedControllerId, selectedControllerName, singleController, event);
                            });

                        }
                        else if (networkLayer == "access") {
                            str = "<ul><li id='" + data.controllerId + "'  class='aController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
                            $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);

                            $(".aController > .TreeSpan").contextmenu(function(event) {
                                selectedControllerId = $(this).parent().attr('id');
                                console.log(selectedControllerId);
                                selectedControllerName = $(this).text();
                                openContext(selectedControllerId, selectedControllerName, singleController, event);
                            });
                        }
                        createControllerMarkerClick(
                            data.controllerId,
                            data.controllerName,
                            data.lng,
                            data.lat,
                            markersController,
                            markerClusterController

                        );
                        controllerCheckFilter(
                            data.controllerId,
                            markerClusterController
                        );
                        markerClusterController.addMarker(markersController[data.controllerId]);
                    }
                    // Handle Update
                    else if (actiondistControllerContext == "Update") {

                        window["" + data.controllerId] = [];
                        window["" + data.controllerId] = [data.controllerId, data.lng, data.lat, data.controllerName, data.networkLayer]
                        var allLis = "";

                        // loop over your list and collect all <li> from each target

                        if (networkLayer != dbContNtLevel) {
                            if (data.DBList) {
                                var distribBoardList = data.DBList;
                                for (i = 0;i < distribBoardList.length;i++) {

                                    $("#" + distribBoardList[i][0]).remove();

                                    if (data.oldNetworkLevel == "backbone") {
                                        console.log("deleting backbone:", distribBoardList[i][0]);

                                        if (markersDistBoard[distribBoardList[i][0]]) {
                                            let marker = markersDistBoard[distribBoardList[i][0]];
                                            markerClusterBackboneDistBoard.removeMarker(marker);
                                            marker.setMap(null);
                                            delete markersDistBoard[distribBoardList[i][0]];
                                            markerClusterBackboneDistBoard.repaint(); // Ã°ÂÂÂ¥ force refresh
                                        }
                                    }
                                    else if (data.oldNetworkLevel == "metro") {
                                        console.log("deleting metro:", distribBoardList[i][0]);

                                        if (markersDistBoard[distribBoardList[i][0]]) {
                                            let marker = markersDistBoard[distribBoardList[i][0]];
                                            markerClusterMetroDistBoard.removeMarker(marker);
                                            marker.setMap(null);
                                            delete markersDistBoard[distribBoardList[i][0]];
                                            markerClusterMetroDistBoard.repaint(); // 
                                        }
                                    }
                                    else {
                                        console.log("deleting access:", distribBoardList[i][0]);

                                        if (markersDistBoard[distribBoardList[i][0]]) {
                                            let marker = markersDistBoard[distribBoardList[i][0]];
                                            markerClusterAccessDistBoard.removeMarker(marker);
                                            marker.setMap(null);
                                            delete markersDistBoard[distribBoardList[i][0]];
                                            markerClusterAccessDistBoard.repaint(); // 
                                        }
                                    }
                                }
                            }
                            $("#" + data.controllerId).remove();
                            if (markersController[data.controllerId]) {
                                markersController[data.controllerId].setMap(null);
                                markerClusterController.removeMarker(markersController[data.controllerId]);
                                delete markersController[data.controllerId];
                            }

                            if (data.controllerDBCount != "0") {


                                if (networkLayer == "backbone") {


                                    str = "<ul><li id='" + data.controllerId + "' class='bController' style='display:none;width:100px;'>"
                                        + "<input type='checkbox' class='ControllerPannel checkFilter filter' />"
                                        + "<span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span>"
                                        + "<span class='TreeSpan' style='color:black;width:355px'>"
                                        + "<img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> "
                                        + data.controllerName + "/" + data.controllerId
                                        + "</span></li></ul>";

                                    $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);

                                    $(".bController > .TreeSpan").contextmenu(function(event) {
                                        selectedControllerId = $(this).parent().attr('id');
                                        console.log(selectedControllerId);
                                        selectedControllerName = $(this).text();
                                        openContext(selectedControllerId, selectedControllerName, singleController, event);
                                    });
                                }
                                else if (networkLayer == "metro") {


                                    str = "<ul><li id='" + data.controllerId + "' class='mController' style='display:none;width:100px;'>"
                                        + "<input type='checkbox' class='ControllerPannel checkFilter filter' />"
                                        + "<span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span>"
                                        + "<span class='TreeSpan' style='color:black;width:355px'>"
                                        + "<img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> "
                                        + data.controllerName + "/" + data.controllerId
                                        + "</span></li></ul>";

                                    $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);

                                    $(".mController > .TreeSpan").contextmenu(function(event) {
                                        selectedControllerId = $(this).parent().attr('id');
                                        console.log(selectedControllerId);
                                        selectedControllerName = $(this).text();
                                        openContext(selectedControllerId, selectedControllerName, singleController, event);
                                    });
                                }
                                else if (networkLayer == "access") {
                                    str = "<ul><li id='" + data.controllerId + "' class='aController' style='display:none;width:100px;'>"
                                        + "<input type='checkbox' class='ControllerPannel checkFilter filter' />"
                                        + "<span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span>"
                                        + "<span class='TreeSpan' style='color:black;width:355px'>"
                                        + "<img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> "
                                        + data.controllerName + "/" + data.controllerId
                                        + "</span></li></ul>";

                                    $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);

                                    $(".aController > .TreeSpan").contextmenu(function(event) {
                                        selectedControllerId = $(this).parent().attr('id');
                                        console.log(selectedControllerId);

                                        selectedControllerName = $(this).text();
                                        openContext(selectedControllerId, selectedControllerName, singleController, event);
                                    });
                                }
                                createControllerMarkerClick(
                                    data.controllerId,
                                    data.controllerName,
                                    data.lng,
                                    data.lat,
                                    markersController,
                                    markerClusterController

                                );
                                controllerCheckFilter(
                                    data.controllerId,
                                    markerClusterController
                                );

                                markerClusterController.addMarker(markersController[data.controllerId]);



                                createDB(data.DBList);


                            }

                            else {

                                if (networkLayer == "backbone") {


                                    str = "<ul><li id='" + data.controllerId + "'  class='bController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
                                    $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);

                                    $(".bController > .TreeSpan").contextmenu(function(event) {
                                        selectedControllerId = $(this).parent().attr('id');
                                        console.log(selectedControllerId);
                                        selectedControllerName = $(this).text();
                                        openContext(selectedControllerId, selectedControllerName, singleController, event);
                                    });
                                }
                                else if (networkLayer == "metro") {


                                    str = "<ul><li id='" + data.controllerId + "'  class='mController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
                                    $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);

                                    $(".mController > .TreeSpan").contextmenu(function(event) {
                                        selectedControllerId = $(this).parent().attr('id');
                                        console.log(selectedControllerId);
                                        selectedControllerName = $(this).text();
                                        openContext(selectedControllerId, selectedControllerName, singleController, event);
                                    });
                                }
                                else if (networkLayer == "access") {


                                    str = "<ul><li id='" + data.controllerId + "'  class='aController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
                                    $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);

                                    $(".aController > .TreeSpan").contextmenu(function(event) {
                                        selectedControllerId = $(this).parent().attr('id');
                                        console.log(selectedControllerId);
                                        selectedControllerName = $(this).text();
                                        openContext(selectedControllerId, selectedControllerName, singleController, event);
                                    });


                                }
                                createControllerMarkerClick(
                                    data.controllerId,
                                    data.controllerName,
                                    data.lng,
                                    data.lat,
                                    markersController,
                                    markerClusterController

                                );
                                controllerCheckFilter(
                                    data.controllerId,
                                    markerClusterController
                                );

                                markerClusterController.addMarker(markersController[data.controllerId]);
                            }
                        }
                        else {
                            if (data.controllerDBCount != "0") {
                                $("#" + data.controllerId + "> .TreeSpan").html("<img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li>");
                            }

                            if (markersController[data.controllerId]) {
                                markersController[data.controllerId].setMap(null);
                                markerClusterController.removeMarker(markersController[data.controllerId]);
                                delete markersController[data.controllerId];
                            }
                            createControllerMarkerClick(
                                data.controllerId,
                                data.controllerName,
                                data.lng,
                                data.lat,
                                markersController,
                                markerClusterController

                            );
                            controllerCheckFilter(
                                data.controllerId,
                                markerClusterController
                            );

                            markerClusterController.addMarker(markersController[data.controllerId]);
                        }
                    }
                    var childrenInitial = $("#initial_ul_CurrentPhysicalLayer").find(' > ul > li');
                    var children = $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li');
                    var networkLevelFolder = $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li');
                    var controllerFolder = $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li >ul >li');
                    var singleCont = $("#" + data.controllerId).find(' > ul > li');
                    // FIX: don't overwrite with "yourControllerId"
                    var controllerId = data.controllerId;

                    // Select either <i> or <svg>
                    var $folderIcon = $("#" + controllerId + " .folder > i, #" + controllerId + " .folder > svg");

                    if ($folderIcon.length > 0) {
                        $folderIcon.removeClass("fa-folder").addClass("fa-folder-open");
                    } else {
                        console.warn("Folder icon NOT found for controllerId:", controllerId);
                    }
                    $("#" + data.controllerId).children(':checkbox').prop("checked", true);

                    $("#initial_ul_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
                    $("#initial_ul_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
                    $("#DistributionBoard_f_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');

                    $("#DistributionBoard_f_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
                    $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');

                    children.show('fast');
                    childrenInitial.show('fast');
                    networkLevelFolder.show('fast');
                    controllerFolder.show('fast');
                    singleCont.show('fast');

                    // scroll to the created db
                    offset = $("#" + data.controllerId).offset().top;
                    offset2 = $("#initial_ul_CurrentPhysicalLayer").offset().top;
                    offsettot = (offset - offset2);

                    $("#network_tree").animate({ scrollTop: offsettot }, "slow");

                    map.setZoom(11);
                    panTo(data.lat, data.lng);


                    $("#" + data.controllerId + " > .TreeSpan").css("display", "inline");

                    // Selection highlight
                    if (IdSelectedTemp != "") {
                        $("#" + IdSelectedTemp + " > .TreeSpan").removeClass("selected-span");
                        $("#" + IdSelectedTemp + " > .TreeSpan").css("background", "");
                    }
                    $("#" + data.controllerId + " > .TreeSpan").addClass("selected-span");
                    $("#" + data.controllerId + " > .TreeSpan").css("background-color", "#97b9cc");
                    IdSelectedTemp = data.controllerId;

                    //   expandTreeFolders();
                    scrollToController(data.controllerId);

                    $("#controllerModal").modal('hide');
                    $("#" + data.controllerId).contextmenu(function() {

                        menuName = singleController;
                        selectedControllerId = $(this).attr('id');
                        selectedContName = $(this).text();

                        openContext(selectedControllerId, selectedContName, singleController, event);
                    });
                }
            },
            error: function() {
                alert("Error");
            }
        });



        $("#controllerModel").modal('hide');
        $("#controllerModel").find("input,textarea,select").val('')
            .end().find("input[type=checkbox], input[type=radio]").prop("checked", "");

        /*    if ($("#dBMapCheck_Labels").prop("checked") == true) {
                markersDistBoard[data.distributionBoardId].setLabel({
                    text: DistributionBoardName,
                    className: "marker-position-dB",
                    color: "#5665F9"
                });
            } */

    }
    // If longitude or latitude are missing
    else {
        alert("Missing Fields !!!");
    }
});

// === Helper functions (you can define them above or below) ===
function appendControllerToTree(data, strdb) {
    console.log(data.ControllerList);
    let str = "";
    if (data.networkLayer == "backbone") {


        str = "<ul><li id='" + data.controllerId + "' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
        $("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);

    }
    else if (data.networkLayer == "metro") {
        str = "<ul><li id='" + data.controllerId + "' class='mController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
        $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
    }
    else if (data.networkLayer == "access") {
        str = "<ul><li id='" + data.controllerId + "' class='aController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> " + data.controllerName + "/" + data.controllerId + " </span></li></ul>";
        $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);

    }
}

function handleUpdateController(data) {
    console.log("okay");
    console.log(dbContNtLevel);
    console.log(data.controllerList);

    if (data.networkLayer != dbContNtLevel) {



        var allLis = "";

        // loop over your list and collect all <li> from each target

        let containerId = "#" + data.controllerId;

        // find the <li> children inside that container
        $(containerId).find("li.DistributionBoard").each(function() {
            allLis += this.outerHTML;
        });

        console.log(allLis);
        console.log("woww");
        // wrap everything inside one <ul>
        let str = "<ul>" + allLis + "</ul>";
        $("#" + data.controllerId).remove();
        createController(data.ControllerList, data.DBList);
    } else {
        let imgPath = "";
        if (data.networkLayer == "backbone") {
            imgPath = "/resources/NetworkImages/controller,.png";
        } else if (data.networkLayer == "metro") {
            imgPath = "/resources/NetworkImages/controller,.png";
        } else if (data.networkLayer == "access") {
            imgPath = "/resources/NetworkImages/controller,.png";
        }
        $("#" + data.controllerId + "> .TreeSpan").html(
            "<img class='image' src='" + getContext() + imgPath + "'> " +
            data.controllerName + "/" + data.controllerId + " </span></li>"
        );
    }
}

function expandTreeFolders() {
    $("#initial_ul_" + IdNodeSelectedTemp + " > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#initial_ul_Projects > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#" + IdNodeSelectedTemp + " > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#DistributionBoard_f_" + IdNodeSelectedTemp + "")
        .find(' > ul > li > .Parentfolder >svg')
        .removeClass('fa fa-folder').addClass('fa-folder-open');

    $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > ul > li").show('fast');
    $("#initial_ul_" + IdNodeSelectedTemp + " > ul > li").show('fast');
    $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > ul > li > ul > li").show('fast');
}

function scrollToDistributionBoard(id) {
    let offset = $("#" + id).offset().top;
    let offset2 = $("#initial_ul_CurrentPhysicalLayer").offset().top;
    let offsettot = (offset - offset2);
    $("#network_tree").animate({ scrollTop: offsettot }, "slow");
}

function scrollToController(id) {
    let offset = $("#" + id).offset().top;
    let offset2 = $("#initial_ul_CurrentPhysicalLayer").offset().top;
    let offsettot = (offset - offset2);
    $("#network_tree").animate({ scrollTop: offsettot }, "slow");
}
//zeinaaa
function siteControllerChanged(checkbox) {
    console.log("Site Controller changed Ã¢ÂÂ checked =", checkbox.checked);
    if ($(checkbox).is(":checked")) {
        document.getElementById("customer_ControllerAutoComplete").checked = false;
        $('#customer_ControllerAutoComplete').val('0');
        $(checkbox).val('1');
        isClientChecked = false;
        isSiteChecked = true;
        document.getElementById("ContClientId").style.display = "none";
        document.getElementById("ContClientName").style.display = "none";
        document.getElementById("ContClientPhoneNb").style.display = "none";
        document.getElementById("ContWarehouse").style.display = "block";
        document.getElementById("ContSite").style.display = "block";
        document.getElementById("ContSiteName").style.display = "block";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML

    }
    else {
        $(checkbox).val('0');
        isClientChecked = false;
        isSiteChecked = true;
        document.getElementById("customer_ControllerAutoComplete").checked = true;
        $('#customer_ControllerAutoComplete').val('1');
        document.getElementById("ContClientId").style.display = "block";
        document.getElementById("ContClientName").style.display = "block";
        document.getElementById("ContClientPhoneNb").style.display = "block";
        document.getElementById("ContWarehouse").style.display = "none";
        document.getElementById("ContSite").style.display = "none";
        document.getElementById("ContSiteName").style.display = "none";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML
    }

}

function customerControllerChanged(checkbox) {
    console.log("Customer Controller changed Ã¢ÂÂ checked =", checkbox.checked);

    if ($(checkbox).is(":checked")) {
        document.getElementById("site_ControllerAutoComplete").checked = false;
        $('#site_ControllerAutoComplete').val('0');
        $(checkbox).val('1');
        isClientChecked = true;
        isSiteChecked = false;
        document.getElementById("ContClientId").style.display = "block";
        document.getElementById("ContClientName").style.display = "block";
        document.getElementById("ContClientPhoneNb").style.display = "block";
        document.getElementById("ContWarehouse").style.display = "none";
        document.getElementById("ContSite").style.display = "none";
        document.getElementById("ContSiteName").style.display = "none";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML

    } else {
        $(checkbox).val('0');
        isClientChecked = false;
        isSiteChecked = true;
        document.getElementById("site_ControllerAutoComplete").checked = true;
        $('#site_ControllerAutoComplete').val('1');
        document.getElementById("ContClientId").style.display = "none";
        document.getElementById("ContClientName").style.display = "none";
        document.getElementById("ContClientPhoneNb").style.display = "none";
        document.getElementById("ContWarehouse").style.display = "block";
        document.getElementById("ContSite").style.display = "block";
        document.getElementById("ContSiteName").style.display = "block";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML
    }
}




$("#ControllerSite").autocomplete({
    source: debounce(function(request, response) {
        var searchkey = $("#ControllerSite").val();
        console.log("siteid");
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/GetAllWarehouse',
            data: {
                "WareName": searchkey,
                "warehouseName": searchkey,
                "SiteId": searchkey,
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }, 1000),
    minLength: 0, maxShowItems: 40, scroll: true,
    select: function(event, ui) {

        this.value = (ui.item ? ui.item[2] : '');
        $("#ControllerSiteName").val(ui.item[1]);
        $("#ControllerWarehouse").val(ui.item[0]);
        if (document.getElementById('customControllerCoordinates').checked) {
            console.log("customControllerCoordinates");
            if ($("#ControllerLong").val() == "" && $("#ControllerLat").val() == "") {
                $("#ControllerLong").val(ui.item[3]);
                $("#ControllerLat").val(ui.item[4]);
            }
        } else {
            $("#ControllerLong").val(ui.item[3]);
            $("#ControllerLat").val(ui.item[4]);
        }

        $("#controllerCity").val(ui.item[5]);
        //$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
        if (($("#ControllerName").val()) == "") {
            $("#ControllerName").val(ui.item[1] + "_DB_" + new Date().getFullYear());
        }
        return false;
    }
}).data("ui-autocomplete")._renderItem = function(ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

$("#ControllerSite").focus(function() {
    if (this.value == "") {
        $(this).autocomplete("search");
    }
});

$("#ControllerSiteName").autocomplete({
    source: debounce(function(request, response) {
        var searchkey = $("#ControllerSiteName").val();
        console.log("name");
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/GetAllWarehouse',
            data: {
                "WareName": searchkey,
                "warehouseName": searchkey,
                "SiteId": searchkey,
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }, 1000),
    minLength: 0, maxShowItems: 40, scroll: true,
    select: function(event, ui) {

        this.value = (ui.item ? ui.item[1] : '');
        $("#ControllerSite").val(ui.item[2]);
        $("#ControllerWarehouse").val(ui.item[0]);
        $("#ControllerLong").val(ui.item[3]);
        $("#ControllerLat").val(ui.item[4]);
        $("#controllerCity").val(ui.item[5]);
        //$("#DistributionBoardName").val(ui.item[1]+"_DB");
        if (($("#ControllerName").val()) == "") {
            $("#ControllerName").val(ui.item[1] + "_DB_" + new Date().getFullYear());
        }

        return false;
    }
}).data("ui-autocomplete")._renderItem = function(ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

$("#ControllerSiteName").focus(function() {
    if (this.value == "") {
        $(this).autocomplete("search");
    }
});

$("#ControllerWarehouse").autocomplete({
    source: debounce(function(request, response) {

        var searchkey = $("#ControllerWarehouse").val();
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/GetAllWarehouse',
            data: {
                "WareName": searchkey,
                "warehouseName": searchkey,
                "SiteId": searchkey,
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }, 1000),
    minLength: 0, maxShowItems: 40, scroll: true,
    select: function(event, ui) {

        this.value = (ui.item ? ui.item[0] : '');
        $("#ControllerSite").val(ui.item[2]);
        $("#ControllerSiteName").val(ui.item[1]);
        $("#ControllerLong").val(ui.item[3]);
        $("#ControllerLat").val(ui.item[4]);
        $("#controllerCity").val(ui.item[5]);
        //$("#DistributionBoardName").val(ui.item[1]+"_DB");
        if (($("#ControllerName").val()) == "") {
            $("#ControllerName").val(ui.item[1] + "_DB_" + new Date().getFullYear());
        }

        return false;
    }
}).data("ui-autocomplete")._renderItem = function(ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

$("#ControllerWarehouse").focus(function() {
    if (this.value == "") {
        $(this).autocomplete("search");
    }
});

//client auto complete
$("#ControllerClient").autocomplete({
    source: debounce(function(request, response) {
        var searchkey = $("#ControllerClient").val();
        console.log("id");
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/GetAllNetworkCustomer',
            data: {
                "search": searchkey,
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }, 1000),
    minLength: 0, maxShowItems: 40, scroll: true,
    select: function(event, ui) {

        this.value = (ui.item ? ui.item[0] : '');
        $("#ControllerClientName").val(ui.item[1]);
        $("#ControllerClientPhoneNb").val(ui.item[2]);
        if (document.getElementById('customControllerCoordinates').checked) {
            console.log("customControllerCoordinates");
            if ($("#ControllerLong").val() == "" && $("#ControllerLat").val() == "") {
                $("#ControllerLong").val(ui.item[4]);
                $("#ControllerLat").val(ui.item[5]);
            }
        } else {
            $("#ControllerLong").val(ui.item[4]);
            $("#ControllerLat").val(ui.item[5]);
        }

        $("#controllerCity").val(ui.item[3]);
        //$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
        if (($("#ControllerName").val()) == "") {
            $("#ControllerName").val(ui.item[1] + "_DB_" + new Date().getFullYear());
        }

        return false;
    }
}).data("ui-autocomplete")._renderItem = function(ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

$("#ControllerClient").focus(function() {
    if (this.value == "") {
        $(this).autocomplete("search");
    }
});

$("#ControllerClientName").autocomplete({
    source: debounce(function(request, response) {
        var searchkey = $("#ControllerClientName").val();
        console.log("name");

        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/GetAllNetworkCustomer',
            data: {
                "search": searchkey,
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }, 1000),
    minLength: 0, maxShowItems: 40, scroll: true,
    select: function(event, ui) {

        this.value = (ui.item ? ui.item[1] : '');
        $("#ControllerClient").val(ui.item[0]);
        $("#ControllerClientPhoneNb").val(ui.item[2]);
        $("#ControllerLong").val(ui.item[4]);
        $("#ControllerLat").val(ui.item[5]);
        $("#controllerCity").val(ui.item[3]);
        //$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
        if (($("#ControllerName").val()) == "") {
            $("#ControllerName").val(ui.item[1] + "_DB_" + new Date().getFullYear());
        }

        return false;
    }
}).data("ui-autocomplete")._renderItem = function(ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[1] + "</span><br><span class='desc'>" +
            item[0] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

$("#ControllerClientName").focus(function() {
    if (this.value == "") {
        $(this).autocomplete("search");
    }
});

$("#ControllerClientPhoneNb").autocomplete({
    source: debounce(function(request, response) {
        var searchkey = $("#ControllerClientPhoneNb").val();

        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/GetAllNetworkCustomer',
            data: {
                "search": searchkey,
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });

    }, 1000),
    minLength: 0, maxShowItems: 40, scroll: true,
    select: function(event, ui) {

        this.value = (ui.item ? ui.item[2] : '');
        $("#ControllerClient").val(ui.item[0]);
        $("#ControllerClientName").val(ui.item[1]);
        $("#ControllerLong").val(ui.item[4]);
        $("#ControllerLat").val(ui.item[5]);
        $("#controllerCity").val(ui.item[3]);
        //$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
        if (($("#ControllerName").val()) == "") {
            $("#ControllerName").val(ui.item[1] + "_DB_" + new Date().getFullYear());
        }

        return false;
    }
}).data("ui-autocomplete")._renderItem = function(ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[2] + "</span><br><span class='desc'>" +
            item[0] + ', ' + item[1] + "</span></div>")
        .appendTo(ul);
};

$("#ControllerClientPhoneNb").focus(function() {
    if (this.value == "") {
        $(this).autocomplete("search");
    }
});


function getContCity() {
    //console.log("getDBCity ");
    fillCityByGeocoding("controllerCity", $("#ControllerLat").val(), $("#ControllerLong").val(), geocoder);

}

$('#controllerModal').on('hide.bs.modal', function() {
    document.activeElement.blur(); // remove focus
});