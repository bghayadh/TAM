$(document).on("triggerListenersEvent", function() {
    $(function() {

        viewNearestPointEvent();
        viewNearestMultyPointEvent();
        searchConnectedButtonEvents();


        $(".mapMenuItem").on('click', function() {
            document.getElementById("mapMenu").classList.remove('show-menu');
        });

        $(".mapSubMenuItem").on('click', function() {
            document.getElementById("mapMenu").classList.remove('show-menu');
        });

        $(".delPathMenuItem").on('click', function() {
            console.log("*****menu Id " + $(this).parent().attr('id'));
            document.getElementById("deletePathMenu").classList.remove('show-menu');
        });


        $("#deleteMan").click(function() {
            console.log("para " + layer_arr[0] + " par2 " + layer_arr[1] + " par3 " + layer_arr[2])
            deletePhysicalLayers(layer_arr[0], layer_arr[1], layer_arr[2]);
            $("#DeleteModal").modal('hide');
        });

        $("#deleteManjunction").click(function() {
            deleteJunction("Manhole", "Junction", selectedManholeJct, selectedManIdContext);
            $("#DeleteModal").modal('hide');
        });
        $("#deleteHandjunction").click(function() {
            deleteJunction("Handhole", "Junction", selectedHandholeJct, selectedHandIdContext);
            $("#DeleteModal").modal('hide');
        });

        $("#deleteFiber").click(function() {
            deleteFiberCable(layer_arr[0], layer_arr[1], layer_arr[2]);
            $("#DeleteModal").modal('hide');
        });

        $("#deleteTrench").click(function() {
            deleteTrenchPath(layer_arr[0], layer_arr[1], layer_arr[2]);
            console.log("para " + layer_arr[0] + "par2 " + layer_arr[1] + "par3 " + layer_arr[2])
            $("#deleteTrench").hide();
            $("#DeleteModal").modal('hide');

        });

        $("#deletePlanningProject").click(function() {
            $("#DeleteProjectModal").modal("hide");
            deletePhysicalLayers("Project", "", selectedProjectIdContext);
        });

        $("#deleteImplementProject").click(function() {
            deletePhysicalLayers("Project", "", selectedProjectIdContext);
            $("#DeleteProjectModal").modal("hide");
        });


        $("#deleteTermination").click(function() {
            $("#DeleteModal").modal('hide');
        });

        $("#moveCancel").click(function() {
            $("#MoveModal").modal('hide');
        });

        $("#deleteProjectCancellation").click(function() {
            $("#DeleteProjectModal").modal('hide');
        });


        $("#confirmClose").click(function() {
            $("#ConfirmModal").modal('hide');
        });

        $('#getRelatedPoints').click(function() {
            if ($(this).is(":checked")) {
                $(this).val('1');
            }
            else {
                $(this).val('0');
            }
        });
        $('#getRelatedPointsCon').click(function() {
            if ($(this).is(":checked")) {
                $(this).val('1');
            }
            else {
                $(this).val('0');
            }
        });
        $('#getRelatedPointsFilter').click(function() {
            if ($(this).is(":checked")) {
                $(this).val('1');
            }
            else {
                $(this).val('0');
            }
        });

        var button = document.getElementById('updateOnMySD');

        button.addEventListener('mouseover', function() {
            if (!button.disabled) {
                button.style.cursor = 'pointer';
            } else {
                button.style.cursor = 'default';
            }
        });

        button.addEventListener('mouseout', function() {
            button.style.cursor = 'default';
        });

        $("#addManhole").on('click', function() {
            console.log("add manhole");
            document.getElementById("projectIdManhole").style.display = "block";
            document.getElementById("projectNameManhole").style.display = "block";
            document.getElementById("ManholeDMDiv").style.display = "none";

            var city = "";
            $('#manholeModal').find('input:text').val('');
            $("#manholeHeader").text("Manhole: ");
            $("#manholeModal").modal('show');
            actionManholeContext = "Insert";

            geocoder = new google.maps.Geocoder();
            //IdNodeSelectedTemp="CurrentPhysicalLayer";
            var latlng = new google.maps.LatLng(getCoords().split(" ")[0], getCoords().split(" ")[1]);
            geocoder.geocode({ 'latLng': latlng }, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {

                    if (results[2]) {
                        city = results[2].formatted_address;
                    }
                    else if (results[3]) {
                        city = results[3].formatted_address;
                    }
                    else if (results[4]) {
                        city = results[4].formatted_address;
                    }
                    else if (results[5]) {
                        city = results[5].formatted_address;
                    }
                    else {
                        alert("No results found");
                    }


                } else {
                    alert("getting the latitude, longitude and the city failed due to a connection problem, please try again");
                }

                $("#ManholeLat").val("" + getCoords().split(" ")[0]);
                $("#ManholeLong").val("" + getCoords().split(" ")[1]);
                $("#ManholeCity").val(city.split(", ")[0]);
            });

        });

        $("#addHandhole").on('click', function() {

            document.getElementById("projectIdHandhole").style.display = "block";
            document.getElementById("projectNameHandhole").style.display = "block";
            document.getElementById("HandholeDMDiv").style.display = "none";

            var city = "";
            $('#handholeModal').find('input:text').val('');
            $("#handholeHeader").text("Handhole: ");
            $("#handholeModal").modal('show');
            actionHandholeContext = "Insert";
            geocoder = new google.maps.Geocoder();
            //IdNodeSelectedTemp="CurrentPhysicalLayer";

            var latlng = new google.maps.LatLng(getCoords().split(" ")[0], getCoords().split(" ")[1]);
            geocoder.geocode({ 'latLng': latlng }, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {

                    if (results[2]) {
                        city = results[2].formatted_address;
                    }
                    else if (results[3]) {
                        city = results[3].formatted_address;
                    }
                    else if (results[4]) {
                        city = results[4].formatted_address;
                    }
                    else if (results[5]) {
                        city = results[5].formatted_address;
                    }
                    else {
                        alert("No results found");
                    }
                } else {
                    alert("getting the latitude, longitude and the city failed due to a connection problem, please try again");
                }
                $("#HandholeLat").val("" + getCoords().split(" ")[0]);
                $("#HandholeLong").val("" + getCoords().split(" ")[1]);
                $("#HandholeCity").val(city.split(", ")[0]);
            });

        });

        $("#addDistributionBoard").on('click', function() {
            document.getElementById("projectIdDB").style.display = "block";
            document.getElementById("projectNameDB").style.display = "block";
            $("#DistributionBoardheader").text("Distribution Board: ");
            $('#distributionBoardModal').find('input:text').val('');
            $("#distributionBoardModal").modal('show');
            $("#DbMappingTable > tbody").empty();
            $(".nav-link").removeClass('active');
            $("#distBoard-tab").addClass('active');
            geocoder = new google.maps.Geocoder();
            actiondistBoardContext = "Insert";
            document.querySelector("#DBMappingFlag").value = "new DB";

            //
            $("#site_DBAutoComplete").prop("checked", true);
            $('#site_DBAutoComplete').val('1');
            document.getElementById("customer_DBAutoComplete").checked = false;
            $('#customer_DBAutoComplete').val('0');
            document.getElementById("DBClientId").style.display = "none";
            document.getElementById("DBClientName").style.display = "none";
            document.getElementById("BDClientPhoneNb").style.display = "none";
            document.getElementById("BDWarehouse").style.display = "block";
            document.getElementById("DBSite").style.display = "block";
            document.getElementById("DBSiteName").style.display = "block";
            //

            var latlng = new google.maps.LatLng(getCoords().split(" ")[0], getCoords().split(" ")[1]);

            geocoder.geocode({ 'latLng': latlng }, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {

                    if (results[2]) {
                        city = results[2].formatted_address;
                    }
                    else if (results[3]) {
                        city = results[3].formatted_address;
                    }
                    else if (results[4]) {
                        city = results[4].formatted_address;
                    }
                    else if (results[5]) {
                        city = results[5].formatted_address;
                    }
                    else {
                        alert("No results found");
                    }
                } else {
                    alert("getting the latitude, longitude and the city failed due to a connection problem, please try again");
                }
                $("#DistributionBoardLat").val("" + getCoords().split(" ")[0]);
                $("#DistributionBoardLong").val("" + getCoords().split(" ")[1]);
                $("#boardCity").val(city.split(", ")[0]);
                $("#DBType").val("passive");

            });
            $(".tab-pane").removeClass('active');
            $("#D_Board").addClass('active');
        });

        $("#selectCable").on('click', function() {
            //console.log("select Cablee is "+selectedPath +" and id "+selectedPathId);
            listenerPathDelete = map.addListener('click', deleteAuxPath);
            deleting = true;

        });
        $("#approveDelete").on('click', function(e) {
            console.log("approveDelete selected Cablee is " + selectedPathId);
            console.log("MarkerArrayId " + MarkerArrayId);

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/DeleteFiberPathAux',
                data: {
                    "markersArray": MarkerArrayId,
                    "fiberpathID": selectedPathId
                },
                dataType: "json",
                success: function(data) {
                    //console.log("selected Cablee mapPoints beforee "+window["mapPoints_"+selectedPathId]);
                    //console.log("array of marker position "+selectedMarkersPos);

                    if (PathsArray.length > 0) {
                        // console.log("delete arraay "); 
                        for (d = 0;d < selectedMarkersPos.length;d++) {
                            var selectedMarkersId = selectedMarkersPos[d].Id;
                            for (var i = 0;i < window["mapPoints_" + selectedPathId].length;i++) {
                                //console.log("WINDOW " +window["mapPoints_"+selectedPathId][i]);
                                let longLatStr = String(window["mapPoints_" + selectedPathId][i]);
                                longLatStr = longLatStr.substring(1, longLatStr.length - 1).split(", ");
                                //console.log("enter "+longLatStr);
                                //console.log("enter window "+longLatStr[0]+" and "+longLatStr[1]);
                                //console.log("enter pos "+selectedMarkersPos[d].lat+" and "+selectedMarkersPos[d].long);
                                if (longLatStr[0] === String(selectedMarkersPos[d].lat) && longLatStr[1] === String(selectedMarkersPos[d].long)) {
                                    console.log("EQUAL ");
                                    window["mapPoints_" + selectedPathId].splice(i, 1);
                                    break;
                                }
                            }
                        }
                    }

                    eventDelete = 0;
                    MarkerArrayId = [];
                    selectedMarkersPos = [];
                    MarkersArray = [];
                    PathsArray = [];
                    selectedMarkers = [];
                    deleting = false;
                    for (i = 0;i < MarkerAuxArray.length;i++) {
                        if (MarkerAuxArray.length > 0) {
                            MarkerAuxArray[i].setMap(null);

                        }
                    }
                    alert(MarkerAuxArray.length + " points have been deleted");
                    MarkerAuxArray = [];
                    if (typeof listenerPathDelete !== 'undefined') {
                        google.maps.event.removeListener(listenerPathDelete);
                    }


                    fiberArray[selectedPathId].setMap(null);
                    buildPath(selectedPathId, window["mapPoints_" + selectedPathId], fiberArray, allFiberCables, "FiberPath_f_", window['FiberColor_' + window['' + selectedPathId][22]], 0.7, 4.5, 'blue', 13);
                    fiberArray[selectedPathId].setMap(map);
                    //console.log("selected Cablee mapPoints after "+window["mapPoints_"+selectedPathId]);

                },
                error: function(result) {
                    alert("Error");
                }
            });


        });
        //cancel from delete menu path
        $("#cancelDeletePathMenu").on('click', function() {

            if (typeof listenerPathDelete !== 'undefined') {
                google.maps.event.removeListener(listenerPathDelete);
            }
            deleting = false;
            for (i = 0;i < MarkerAuxArray.length;i++) {
                if (MarkerAuxArray.length > 0) {
                    MarkerAuxArray[i].setMap(null);

                }
            }

        });

        $('.fiberMode').on('click', function() {
            // in the handler, 'this' refers to the box clicked on
            var $choice = $(this);
            if ($choice.is(":checked")) {
                $('.fiberMode').prop("checked", false);
                $choice.prop("checked", true);
                selectedFiberMode = $(this).attr('name');
                console.log(selectedFiberMode);
            }
        });

        $("#viewFiberPath").on('click', function(e) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/fiberPathSrcToDst',
                data: {},
                dataType: "json",
                success: function(data) {
                    if (data.fiberPathSrcDst != null) {
                        fetchSearchedFiberPath(data.fiberPathSrcDst);
                    }
                    else
                        alert("No fiber cables were found!");
                },
                error: function(result) {
                    alert("Error");
                }
            });
        });



        var tabhref = "custom-tabs-FindACable";
        $('#fiberCitySearch a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
            var target = $(e.target).attr("href") // activated tab
            tabhref = target;
        });

        $("#open-popup-btn").click(function() {
            //$('a[href="#custom-tabs-FindACable"]').click();
            $(' a[href="#' + tabhref + '"]').click();
            $("#fiberCitySearch").modal('show');

        });
        $("#customerDetails").autocomplete({
            source: function(request, response) {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/GetAllNetworkCustomer',
                    data: {
                        "search": $("#customerDetails").val(),
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data && data.globalList) {
                            response(data.globalList);
                        } else {
                            response([]); // Return empty array if no data
                        }
                    },
                    error: function() {
                        alert("Error fetching data");
                    }
                });
            },
            minLength: 0,
            maxShowItems: 40,
            scroll: true,
            select: function(event, ui) {
                if (ui.item) {
                    // Update fields based on the selected item
                    $("#customerDetails").val(ui.item[0]); // Customer ID
                    $("#closestLongPoint").val(ui.item[4]); // Longitude
                    $("#closestLatPoint").val(ui.item[5]);  // Latitude
                    $("#customerName").val(ui.item[1]);     // Customer Name
                } else {
                    alert("No item selected!");
                }

                return false; // Prevent default behavior of autocomplete
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[0] + "</span><br><span class='desc'>" +
                    item[1] + ', ' + item[2] + "</span></div>")
                .appendTo(ul);
        };

        // Automatically trigger autocomplete when the input is focused and empty
        $("#customerDetails").focus(function() {
            if (this.value === "") {
                $(this).autocomplete("search");
            }
        });


        //Customer Service & ref ID in find nearest popup
        $("#serviceReference").autocomplete({

            source: function(request, response) {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/ServiceReferenceAutocomplete',
                    data: {
                        "search": $("#serviceReference").val(),
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data != null) {
                            response(data.serviceList);
                        }
                    },
                    error: function(result) {
                        alert("Error");
                    }
                });
            }, minLength: 0, maxShowItems: 40, scroll: true,
            select: function(event, ui) {
                this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
                return false;
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            return $("<li class='each'>").append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                item[0] + "</span><br><span class='desc'>" +
                item[1] + "</span></div>").appendTo(ul);
        };

        $("#serviceReference").focus(function() {
            if (this.value == "") {
                $(this).autocomplete("search");
            }
        });
        $("#selectHeaderSearch").on('change', function() {
            $("#autoCompleteHeaderSearch").val("");
        });

        $("#autoCompleteConnectedSearch").on('focusout', function() {

            var ss = $("#autoCompleteConnectedSearch").val();

            $("#autoCompleteHeaderSearch").val(ss);
            $("#headerSearchLong").val($("#connectedSearchLong").val());
            $("#headerSearchLat").val($("#connectedSearchLat").val());


            console.log("" + $("#autoCompleteHeaderSearch").val());
            console.log("" + $("#headerSearchLong").val());
            console.log("" + $("#headerSearchLat").val());
        });

        $("#selectConnectedSearch").on('change', function() {
            $("#selectHeaderSearch").val($("#selectConnectedSearch").val());
            $("#autoCompleteConnectedSearch").val("");

        });


        $("#showClosePoints").on('click', function() {
            window["showClosePointsLong"] = getCoords().split(" ")[1];
            window["showClosePointsLat"] = getCoords().split(" ")[0];
            if (closePointPopupFlag == "notOpened") {
                $("#closePtsLong").val(window["showClosePointsLong"]);
                $("#closePtsLat").val(window["showClosePointsLat"]);
                closePointPopupFlag = "";
            }
            $("#showClosePointsPopup").modal('show');

        });

        $("#captureNewCoordinate").on('click', function() {

            $("#searchCloseManhTBody").empty();
            $("#searchCloseHandTBody").empty();
            $("#searchCloseDbTBody").empty();
            $("#searchCloseNodeTBody").empty();
            $("#searchCloseManhTBody").html("");
            $("#searchCloseHandTBody").html("");
            $("#searchCloseDbTBody").html("");
            $("#searchCloseNodeTBody").html("");
            $("#closePtsDistanceRange").val("");
            $("#totalCloseDB").val("");
            $("#totalCloseNode").val("");
            $("#totalCloseManhole").val("");
            $("#totalCloseHandhole").val("");
            document.getElementById("findCloseNodePts").innerHTML = "";
            document.getElementById("findCloseManholePts").innerHTML = "";
            document.getElementById("findCloseHandholePts").innerHTML = "";
            document.getElementById("findCloseDbPts").innerHTML = "";

            // active the first tab
            $('#showClosePointsPopup ul.nav-tabs li a').removeClass('active');
            $('#showClosePointsPopup ul.nav-tabs li:first-child a').addClass('active');
            // active the first form
            $('#showClosePointsPopup .tab-pane').removeClass('active');
            $('#showClosePointsPopup .tab-pane:first-child').addClass('active');

            $("#closePtsLong").val(window["showClosePointsLong"]);
            $("#closePtsLat").val(window["showClosePointsLat"]);

        });

        $("#searchClosePoints").on('click', function() {

            var closePtLong = $("#closePtsLong").val();
            var closePtLat = $("#closePtsLat").val();
            var closePtDistRange = $("#closePtsDistanceRange").val();

            if (closePtDistRange == "" || closePtLong == "" || closePtLat == "") {
                alert("Some input fields are missing!")
            }
            else if (isNaN(closePtLong) == true || isNaN(closePtLat) == true || isNaN(closePtDistRange) == true) {
                alert("Incorrect Format ! Longitude, Latitude and Distance Range should be numbers.")
            }
            else {


                var pointDist = 0;
                var closePointsDataTemp = [];


                //Loop through all manholes to get the distance and filter the closest manholes
                for (var x = 0;x < allManholesID.length;x++) {
                    pointDist = Number(closePointsHaversineDistance(closePtLat, closePtLong, window["" + allManholesID[x]][3], window["" + allManholesID[x]][2]));

                    if (pointDist < closePtDistRange) {
                        //Add all manhole details to the temp array
                        closePointsDataTemp = window["" + allManholesID[x]];
                        // Append the calculated distance to the array on last free position 
                        closePointsDataTemp[7] = (pointDist);
                        // Add the temp array to the main array that will contains all closest manholes
                        closePointsData.push(closePointsDataTemp);
                        //Clear the temp array to use it on next iteration
                        closePointsDataTemp = [];
                    }

                }
                appendManholesClosePoints(closePointsData);
                $("#totalCloseManhole").val(closePointsData.length);
                closePointsData = [];//Clear the main array after appending all manholes to use it for next elements (HH/DB/NODES)

                //Loop through all HHs to get the distance and filter the closest HHs
                for (var x = 0;x < allHandholesID.length;x++) {
                    pointDist = Number(closePointsHaversineDistance(closePtLat, closePtLong, window["" + allHandholesID[x]][3], window["" + allHandholesID[x]][2]));

                    if (pointDist < closePtDistRange) {
                        closePointsDataTemp = window["" + allHandholesID[x]];
                        closePointsDataTemp[7] = (pointDist);
                        closePointsData.push(closePointsDataTemp);
                        closePointsDataTemp = []; // Clear the temp array to use it on next iteration
                    }
                }
                appendHandholesClosePoints(closePointsData);
                $("#totalCloseHandhole").val(closePointsData.length);
                closePointsData = [];//Clear the main array after appending all HH to use it for next elements (DB/NODES)

                //Loop through all DB to get the distance and filter the closest HHs
                for (var x = 0;x < allDB.length;x++) {
                    pointDist = Number(closePointsHaversineDistance(closePtLat, closePtLong, window["" + allDB[x]][2], window["" + allDB[x]][1]));

                    if (pointDist < closePtDistRange) {
                        closePointsDataTemp = window["" + allDB[x]];
                        closePointsDataTemp[9] = (pointDist);
                        closePointsData.push(closePointsDataTemp);
                        closePointsDataTemp = []; // Clear the temp array to use it on next iteration
                    }
                }
                appendDbClosePoints(closePointsData);
                $("#totalCloseDB").val(closePointsData.length);
                closePointsData = [];//Clear the main array after appending all HH to use it for next elements (NODES)

                //Loop through all nodes to get the distance and filter the closest HHs
                for (var x = 0;x < allNodes.length;x++) {
                    pointDist = Number(closePointsHaversineDistance(closePtLat, closePtLong, window["" + allNodes[x]][6], window["" + allNodes[x]][5]));

                    if (pointDist < closePtDistRange) {
                        closePointsDataTemp = window["" + allNodes[x]];
                        closePointsDataTemp[9] = (pointDist);
                        closePointsData.push(closePointsDataTemp);
                        closePointsDataTemp = []; // Clear the temp array to use it on next iteration
                    }
                }
                appendNodeClosePoints(closePointsData);
                $("#totalCloseNode").val(closePointsData.length);
                closePointsData = [];

            }

        });

        // Click event to get coordinates and store them in localStorage
        $("#getCoordinatePoint").on('click', function() {
            var coords = getCoords().split(" ");
            var long = coords[1];
            var lat = coords[0];

            // Store coordinates in localStorage
            localStorage.setItem("getCoorPointLong", long);
            localStorage.setItem("getCoorPointLat", lat);

            console.log("Stored getCoorPointLong: " + long);
            console.log("Stored getCoorPointLat: " + lat);
        });

        // Click event to set coordinates into input fields
        $("#setCoordBtn").on('click', function() {
            // Retrieve coordinates from localStorage
            var long = localStorage.getItem("getCoorPointLong");
            var lat = localStorage.getItem("getCoorPointLat");

            // Check if values exist before setting
            if (long && lat) {
                $("#closestLongPoint").val(long);
                $("#closestLatPoint").val(lat);
            } else {
                console.log("No coordinates found in localStorage.");
            }

            console.log("Set closestLongPoint: " + $("#closestLongPoint").val());
            console.log("Set closestLatPoint: " + $("#closestLatPoint").val());
        });

        // Click event to use coordinates for other operations
        $("#setCoordOperationBtn").on('click', function() {
            // Retrieve coordinates from localStorage
            var long = localStorage.getItem("getCoorPointLong");
            var lat = localStorage.getItem("getCoorPointLat");

            console.log("Retrieved Lng from localStorage: " + long);
            console.log("Retrieved Lat from localStorage: " + lat);

            if (long && lat) {
                $("#Lng").val(long);
                $("#Lat").val(lat);
            } else {
                console.log("No coordinates found in localStorage.");
            }

            console.log("Set Lng: " + $("#Lng").val());
            console.log("Set Lat: " + $("#Lat").val());
        });

        $("#setStartPointBtn").on('click', function() {
            // Retrieve coordinates from localStorage
            var long = localStorage.getItem("getCoorPointLong");
            var lat = localStorage.getItem("getCoorPointLat");

            // Check if values exist before setting
            if (long && lat) {
                $("#startLongPoint").val(long);
                $("#startLatPoint").val(lat);
                console.log("Set startLongPoint: " + $("#startLongPoint").val());
                console.log("Set startLatPoint: " + $("#startLatPoint").val());
            } else {
                console.log("No coordinates found in localStorage.");
            }
        });

        // Click event to set end point coordinates from localStorage
        $("#setEndPointBtn").on('click', function() {
            // Retrieve coordinates from localStorage
            var long = localStorage.getItem("getCoorPointLong");
            var lat = localStorage.getItem("getCoorPointLat");

            // Check if values exist before setting
            if (long && lat) {
                $("#endLongPoint").val(long);
                $("#endLatPoint").val(lat);
                console.log("Set endLongPoint: " + $("#endLongPoint").val());
                console.log("Set endLatPoint: " + $("#endLatPoint").val());
            } else {
                console.log("No coordinates found in localStorage.");
            }
        });

        // Click event to set coordinates in auxiliary table
        $("#setCoordinateFiberAux").on('click', function() {
            var checkedRow;

            if ($("#auxiliaryTable input[type=checkbox]:checked").length > 1) {
                alert("Please check only one row to set coordinate.");
            } else {
                $("#auxiliaryTable input[type=checkbox]:checked").each(function() {
                    checkedRow = $(this).closest("tr")[0].rowIndex;
                });

                // Retrieve coordinates from localStorage
                var long = localStorage.getItem("getCoorPointLong");
                var lat = localStorage.getItem("getCoorPointLat");

                // Check if values exist before setting
                if (long && lat) {
                    $("#auxiliaryTable >tbody").find("tr").eq(checkedRow - 1).find('td[name="auxiliary_Longitude"]').children('input').val(long);
                    $("#auxiliaryTable >tbody").find("tr").eq(checkedRow - 1).find('td[name="auxiliary_Latitude"]').children('input').val(lat);
                } else {
                    console.log("No coordinates found in localStorage.");
                }

                calculateDistanceSourceDestination(
                    $("#SourceLat").val(), $("#SourceLng").val(),
                    $("#DestinationLat").val(), $("#DestinationLng").val(),
                    "auxiliaryTable"
                );
            }
        });

        // Click event to set coordinates for strands
        $("#setCoordinateStrandAux").on('click', function() {
            $('.rowAboveBelowStrand').each(function() {
                if (this.checked) {
                    var rowIndex = $(this).closest('tr');
                    var currentIndex = rowIndex.index();

                    // Retrieve coordinates from localStorage
                    var long = localStorage.getItem("getCoorPointLong");
                    var lat = localStorage.getItem("getCoorPointLat");

                    // Check if values exist before setting
                    if (long && lat) {
                        $(this).parents("tr").find('input[name="auxStrand_Lat"]').val(lat);
                        $(this).parents("tr").find('input[name="auxStrand_Long"]').val(long);
                    } else {
                        console.log("No coordinates found in localStorage.");
                    }
                }
                calculateDistanceSourceDestination(
                    $("#sourcelatstrand").val(), $("#sourcelongstrand").val(),
                    $("#destinationlatstrand").val(), $("#destinationlongstrand").val(),
                    "auxiliaryTableStrands"
                );
            });
        });

        // Click event to set coordinates for trenches
        $("#setCoordinateTrenchAux").on('click', function() {
            $('.rowAboveBelowTrench').each(function() {
                if (this.checked) {
                    var rowIndex = $(this).closest('tr');
                    var currentIndex = rowIndex.index();

                    // Retrieve coordinates from localStorage
                    var long = localStorage.getItem("getCoorPointLong");
                    var lat = localStorage.getItem("getCoorPointLat");

                    // Check if values exist before setting
                    if (long && lat) {
                        $(this).parents("tr").find('input[name="aux_Lattrench"]').val(lat);
                        $(this).parents("tr").find('input[name="aux_Longtrench"]').val(long);
                    } else {
                        console.log("No coordinates found in localStorage.");
                    }
                }
                calculateDistanceSourceDestination(
                    $("#SourceTrenchLat").val(), $("#SourceTrenchLng").val(),
                    $("#DestinationTrenchLat").val(), $("#DestinationTrenchLng").val(),
                    "auxiliary_trenchTable"
                );
            });
        });

        // Click event to set coordinates for ducts
        $("#setCoordinateDuctAux").on('click', function() {
            $('.rowAboveBelowDuct').each(function() {
                if (this.checked) {
                    var rowIndex = $(this).closest('tr');
                    var currentIndex = rowIndex.index();

                    // Retrieve coordinates from localStorage
                    var long = localStorage.getItem("getCoorPointLong");
                    var lat = localStorage.getItem("getCoorPointLat");

                    // Check if values exist before setting
                    if (long && lat) {
                        $(this).parents("tr").find('input[name="aux_Latduct"]').val(lat);
                        $(this).parents("tr").find('input[name="aux_Longduct"]').val(long);
                    } else {
                        console.log("No coordinates found in localStorage.");
                    }
                }
                calculateDistanceSourceDestination(
                    $("#SourceDuctLat").val(), $("#SourceDuctLng").val(),
                    $("#DestinationDuctLat").val(), $("#DestinationDuctLng").val(),
                    "auxiliary_ductTable"
                );
            });
        });

        // Click event to set coordinates for tubes
        $("#setCoordinateTubeAux").on('click', function() {
            $('.rowAboveBelowTube').each(function() {
                if (this.checked) {
                    var rowIndex = $(this).closest('tr');
                    var currentIndex = rowIndex.index();

                    // Retrieve coordinates from localStorage
                    var long = localStorage.getItem("getCoorPointLong");
                    var lat = localStorage.getItem("getCoorPointLat");

                    // Check if values exist before setting
                    if (long && lat) {
                        $(this).parents("tr").find('input[name="auxTube_Lat"]').val(lat);
                        $(this).parents("tr").find('input[name="auxTube_Long"]').val(long);
                    } else {
                        console.log("No coordinates found in localStorage.");
                    }
                }
                calculateDistanceSourceDestination(
                    $("#sourcelat").val(), $("#sourcelong").val(),
                    $("#destinationlat").val(), $("#destinationlong").val(),
                    "auxiliaryTableTubes"
                );
            });
        });

        // Call autocomplete functions (assuming these are properly defined elsewhere)
        autoCompleteSearchHeader('autoCompleteHeaderSearch', 'selectHeaderSearch', 'headerSearchLong', 'headerSearchLat');
        autoCompleteSearchHeader('autoCompleteConnectedSearch', 'selectConnectedSearch', 'connectedSearchLong', 'connectedSearchLat');
    });
});// end trigger 
