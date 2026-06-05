function showOnMap() {
    distinctDB = [];
    distinctJct = [];
    distinctCustomers = [];
    distinctSites = [];
    distinctManholes = [];
    distinctManholesWithJct = [];
    distinctHandholesWithJct = [];
    distinctHandholes = [];

    markerClusterDB.clearMarkers();
    markerClusterJct.clearMarkers();
    markerClusterCustomers.clearMarkers();
    markerClusterHandholes.clearMarkers();
    markerClusterHandholesWithJct.clearMarkers();
    markerClusterManholes.clearMarkers();
    markerClusterManholesWithJct.clearMarkers();
    markerClusterSites.clearMarkers();
    mapFlag = "1";

    srcID = [];
    dstID = [];
    showPointsArray = [];


    if (generateFlag == "1") {
        var dropdownSelectedValue = document.getElementById('mapDropdown').value;
        cableID = $("#fiberCable").val().split(":")[0];

        if (jctElementsFlag == "notOpened") {// to get the junctionn details in case of element type in grid is junction
            jctElementsFlag = "Opened";
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/getJctElementsDetails',
                data: {
                    "jctElementsIDArray": jctElementsIDArray
                },
                dataType: "json",
                success: function(data) {
                    elementsArray = data.jctList;
                    if (dropdownSelectedValue == "cableBased") {
                        showElementLocationPoints(data.jctList, filteredGridData, "mapPointsNames_", "mapPoints_");
                    }
                    else if (dropdownSelectedValue == "gridBased") {
                        window["mapPointsBasedOnGrid_" + cableID] = [];
                        window["mapPointsNamesBasedOnGrid_" + cableID] = [];
                        processCableSrcDstArray(cableID, "mapPointsNamesBasedOnGrid_", srcDstCableList, "mapPointsBasedOnGrid_");
                        window["mapPointsBasedOnGrid_" + cableID].push(new google.maps.LatLng(srcDstCableList[0][3], srcDstCableList[0][2]));
                        showElementLocationPoints(data.jctList, filteredGridData, "mapPointsNamesBasedOnGrid_", "mapPointsBasedOnGrid_");
                    }

                },
                error: function(result) {
                    alert("Error");
                }
            });

        }
        else {// the elements that are junctions already have all details (ajax call before) 		    
            if (dropdownSelectedValue == "cableBased") {
                showElementLocationPoints(elementsArray, filteredGridData, "mapPointsNames_", "mapPoints_");
            }
            else if (dropdownSelectedValue == "gridBased") {
                window["mapPointsBasedOnGrid_" + cableID] = [];
                window["mapPointsNamesBasedOnGrid_" + cableID] = [];
                processCableSrcDstArray(cableID, "mapPointsNamesBasedOnGrid_", srcDstCableList, "mapPointsBasedOnGrid_");
                window["mapPointsBasedOnGrid_" + cableID].push(new google.maps.LatLng(srcDstCableList[0][3], srcDstCableList[0][2]));
                showElementLocationPoints(elementsArray, filteredGridData, "mapPointsNamesBasedOnGrid_", "mapPointsBasedOnGrid_");
            }
        }


    }
    //Scroll to the map div
    document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
}

function showElementLocationPoints(elementsDetailsArray, filteredGridArray, windowMapPointsNames, windowMapPoints) {

    //To show the elements in grid that are junctions or belongs to mh/hh with jct in case of junction element type
    if (elementsDetailsArray.length > 0) {

        for (var z = 0;z < elementsDetailsArray.length;z++) {

            if (elementsDetailsArray[z][2] != null && elementsDetailsArray[z][2] != "null") {

                if (elementsDetailsArray[z][2].startsWith("MH_") == true) {
                    var ID = elementsDetailsArray[z][2];
                    var manholeName = elementsDetailsArray[z][3];

                    if (manholeName.endsWith("_J")) {
                        if (distinctManholesWithJct.includes(ID) == false) {
                            distinctManholesWithJct.push(ID);
                            if (!markersManholesWithJct[ID]) {
                                createMarker(ID, elementsDetailsArray[z][4], elementsDetailsArray[z][5], manholeName, "manholeJct.png", markersManholesWithJct, markerClusterManholesWithJct, "manholeWithJct")
                            }
                            else {
                                markersManholesWithJct[ID].setMap(map);
                                markerClusterManholesWithJct.addMarker(markersManholesWithJct["" + ID]);
                            }
                        }
                    }

                }
                else if (elementsDetailsArray[z][2].startsWith("HH_") == true) {
                    var ID = showPointsArray[x].split(":")[0];
                    var ID = elementsDetailsArray[z][2];
                    var handholeName = elementsDetailsArray[z][3];

                    if (handholeName.endsWith("_J")) {

                        if (distinctHandholesWithJct.includes(ID) == false) {
                            distinctHandholesWithJct.push(ID);
                            if (!markersHandholesWithJct[ID]) {
                                createMarker(ID, elementsDetailsArray[z][4], elementsDetailsArray[z][5], handholeName, "handholeYellowJct.png", markersHandholesWithJct, markerClusterHandholesWithJct, "handholeWithJct")
                            }
                            else {
                                markersHandholesWithJct[ID].setMap(map);
                                markerClusterHandholesWithJct.addMarker(markersHandholesWithJct["" + ID]);
                            }
                        }
                    }


                }


            }

            else {//case of junction that does not belongs to mh or hh

                if (distinctJct.includes(elementsDetailsArray[z][0]) == false) {
                    ID = elementsDetailsArray[z][0];
                    var longitude = elementsDetailsArray[z][4];
                    var latitude = elementsDetailsArray[z][5];
                    var Name = elementsDetailsArray[z][1];
                    distinctJct.push(ID);
                    if (!markersJct[ID]) {
                        createMarker(ID, longitude, latitude, Name, 'junctionOrange.png', markersJct, markerClusterJct, "junction")
                    }
                    else {
                        markersJct[ID].setMap(map);
                        markerClusterJct.addMarker(markersJct["" + ID]);
                    }
                }


            }
        }
    }


    //To show the aux points of the cable
    if (window["" + windowMapPointsNames + cableID] != undefined) {
        showPointsArray = window["" + windowMapPointsNames + cableID];

        for (var x = 0;x < showPointsArray.length;x++) {
            if (showPointsArray[x].startsWith("WARE_") == true) {
                var wareID = showPointsArray[x].split(":")[1];
                var longLat = String(window["" + windowMapPoints + cableID][x]).replaceAll(/[( )]/g, '');

                if (x == 0) {// only src
                    srcID.push([wareID, "warehouse"]);

                    if (!markersSites[wareID]) {
                        createMarker(wareID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[2], 'redSiteIcon.png', markersSites, markerClusterSites, "site")
                    }
                    else {
                        markersSites[wareID].setMap(map);
                        markerClusterSites.addMarker(markersSites["" + wareID]);
                    }
                }
                else if (x == (showPointsArray.length - 1)) {// only destination 
                    dstID.push([wareID, "warehouse"]);

                    if (!markersSites[wareID]) {
                        createMarker(wareID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[2], 'redSiteIcon.png', markersSites, markerClusterSites, "site")
                    }
                    else {
                        markersSites[wareID].setMap(map);
                        markerClusterSites.addMarker(markersSites["" + wareID]);
                    }
                }
                else {
                    if (distinctSites.includes(wareID) == false) {

                        distinctSites.push(wareID);

                        if (!markersSites[wareID]) {
                            createMarker(wareID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[2], 'redSiteIcon.png', markersSites, markerClusterSites, "site")
                        }
                        else {
                            markersSites[wareID].setMap(map);
                            markerClusterSites.addMarker(markersSites["" + wareID]);
                        }
                    }
                }

            }
            else if (showPointsArray[x].startsWith("CUST_") == true) {
                var ID = showPointsArray[x].split(":")[0];
                var longLat = String(window["" + windowMapPoints + cableID][x]).replaceAll(/[( )]/g, '');

                if (x == 0) {// only src  
                    srcID.push([ID, "customer"]);
                    if (!markersCustomer[ID]) {
                        createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], 'customerIcon.png', markersCustomer, markerClusterCustomers, "customer")
                    }
                    else {
                        markersCustomer[ID].setMap(map);
                        markerClusterCustomers.addMarker(markersCustomer["" + ID]);
                    }
                }
                else if (x == (showPointsArray.length - 1)) {// only destination 
                    dstID.push([ID, "customer"]);
                    if (!markersCustomer[ID]) {
                        createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], 'customerIcon.png', markersCustomer, markerClusterCustomers, "customer")
                    }
                    else {
                        markersCustomer[ID].setMap(map);
                        markerClusterCustomers.addMarker(markersCustomer["" + ID]);
                    }
                }
                else {
                    if (distinctCustomers.includes(ID) == false) {

                        distinctCustomers.push(ID);
                        if (!markersCustomer[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], 'customerIcon.png', markersCustomer, markerClusterCustomers, "customer")
                        }
                        else {
                            markersCustomer[ID].setMap(map);
                            markerClusterCustomers.addMarker(markersCustomer["" + ID]);
                        }
                    }
                }
            }
            else if (showPointsArray[x].startsWith("MH_") == true) {
                var ID = showPointsArray[x].split(":")[0];
                var longLat = String(window["" + windowMapPoints + cableID][x]).replaceAll(/[( )]/g, '');
                var manholeName = showPointsArray[x].split(":")[1];

                if (manholeName.endsWith("_J")) {

                    if (x == 0) {// only src  
                        srcID.push([ID, "manholewithJct"]);
                        if (!markersManholesWithJct[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "manholeJct.png", markersManholesWithJct, markerClusterManholesWithJct, "manholeWithJct")
                        }
                        else {
                            markersManholesWithJct[ID].setMap(map);
                            markerClusterManholesWithJct.addMarker(markersManholesWithJct["" + ID]);
                        }
                    }
                    else if (x == (showPointsArray.length - 1)) {// only destination 
                        dstID.push([ID, "manholewithJct"]);
                        if (!markersManholesWithJct[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "manholeJct.png", markersManholesWithJct, markerClusterManholesWithJct, "manholeWithJct")
                        }
                        else {
                            markersManholesWithJct[ID].setMap(map);
                            markerClusterManholesWithJct.addMarker(markersManholesWithJct["" + ID]);
                        }
                    }
                    else {
                        if (distinctManholesWithJct.includes(ID) == false) {

                            distinctManholesWithJct.push(ID);
                            if (!markersManholesWithJct[ID]) {
                                createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "manholeJct.png", markersManholesWithJct, markerClusterManholesWithJct, "manholeWithJct")
                            }
                            else {
                                markersManholesWithJct[ID].setMap(map);
                                markerClusterManholesWithJct.addMarker(markersManholesWithJct["" + ID]);
                            }
                        }
                    }
                }
                else {
                    if (x == 0) {// only src 
                        srcID.push([ID, "manhole"]);
                        if (!markersManholes[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "manholeRed.png", markersManholes, markerClusterManholes, "manhole")
                        }
                        else {
                            markersManholes[ID].setMap(map);
                            markerClusterManholes.addMarker(markersManholes["" + ID]);
                        }
                    }
                    else if (x == (showPointsArray.length - 1)) {// only destination 
                        dstID.push([ID, "manhole"]);
                        if (!markersManholes[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "manholeRed.png", markersManholes, markerClusterManholes, "manhole")
                        }
                        else {
                            markersManholes[ID].setMap(map);
                            markerClusterManholes.addMarker(markersManholes["" + ID]);
                        }
                    }
                    else {
                        if (distinctManholes.includes(ID) == false) {

                            distinctManholes.push(ID);
                            if (!markersManholes[ID]) {
                                createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "manholeRed.png", markersManholes, markerClusterManholes, "manhole")
                            }
                            else {
                                markersManholes[ID].setMap(map);
                                markerClusterManholes.addMarker(markersManholes["" + ID]);
                            }
                        }

                    }
                }
            }
            else if (showPointsArray[x].startsWith("HH_") == true) {
                var ID = showPointsArray[x].split(":")[0];
                var longLat = String(window["" + windowMapPoints + cableID][x]).replaceAll(/[( )]/g, '');
                var handholeName = showPointsArray[x].split(":")[1];

                if (handholeName.endsWith("_J")) {

                    if (x == 0) {// only src  
                        srcID.push([ID, "handholewithJct"]);
                        if (!markersHandholesWithJct[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "handholeYellowJct.png", markersHandholesWithJct, markerClusterHandholesWithJct, "handholeWithJct")
                        }
                        else {
                            markersHandholesWithJct[ID].setMap(map);
                            markerClusterHandholesWithJct.addMarker(markersHandholesWithJct["" + ID]);
                        }
                    }
                    else if (x == (showPointsArray.length - 1)) {// only  destination 
                        dstID.push([ID, "handholewithJct"]);
                        if (!markersHandholesWithJct[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "handholeYellowJct.png", markersHandholesWithJct, markerClusterHandholesWithJct, "handholeWithJct")
                        }
                        else {
                            markersHandholesWithJct[ID].setMap(map);
                            markerClusterHandholesWithJct.addMarker(markersHandholesWithJct["" + ID]);
                        }
                    }
                    else {
                        if (distinctHandholesWithJct.includes(ID) == false) {

                            distinctHandholesWithJct.push(ID);
                            if (!markersHandholesWithJct[ID]) {
                                createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "handholeYellowJct.png", markersHandholesWithJct, markerClusterHandholesWithJct, "handholeWithJct")
                            }
                            else {
                                markersHandholesWithJct[ID].setMap(map);
                                markerClusterHandholesWithJct.addMarker(markersHandholesWithJct["" + ID]);
                            }
                        }
                    }
                }
                else {
                    if (x == 0) {// only src  
                        srcID.push([ID, "handhole"]);
                        if (!markersHandholes[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "handholeYellow.png", markersHandholes, markerClusterHandholes, "handhole")
                        }
                        else {
                            markersHandholes[ID].setMap(map);
                            markerClusterHandholes.addMarker(markersHandholes["" + ID]);
                        }
                    }
                    else if (x == (showPointsArray.length - 1)) {// only destination 
                        dstID.push([ID, "handhole"]);
                        if (!markersHandholes[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "handholeYellow.png", markersHandholes, markerClusterHandholes, "handhole")
                        }
                        else {
                            markersHandholes[ID].setMap(map);
                            markerClusterHandholes.addMarker(markersHandholes["" + ID]);
                        }
                    }
                    else {
                        if (distinctHandholes.includes(ID) == false) {

                            distinctHandholes.push(ID);
                            if (!markersHandholes[ID]) {
                                createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], "handholeYellow.png", markersHandholes, markerClusterHandholes, "handhole")
                            }
                            else {
                                markersHandholes[ID].setMap(map);
                                markerClusterHandholes.addMarker(markersHandholes["" + ID]);
                            }
                        }
                    }
                }

            }
            else if (showPointsArray[x].startsWith("DB_") == true) {
                var ID = showPointsArray[x].split(":")[0];
                var longLat = String(window["" + windowMapPoints + cableID][x]).replaceAll(/[( )]/g, '');

                if (x == 0) {// only src  
                    srcID.push([ID, "DB"]);
                    if (!markersDB[ID]) {
                        createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], 'backboneDB.png', markersDB, markerClusterDB, "DB")
                    }
                    else {
                        markersDB[ID].setMap(map);
                        markerClusterDB.addMarker(markersDB["" + ID]);
                    }
                }
                if (x == (showPointsArray.length - 1)) {// only destination 
                    dstID.push([ID, "DB"]);
                    if (!markersDB[ID]) {
                        createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], 'backboneDB.png', markersDB, markerClusterDB, "DB")
                    }
                    else {
                        markersDB[ID].setMap(map);
                        markerClusterDB.addMarker(markersDB["" + ID]);
                    }
                }
                else {
                    if (distinctDB.includes(ID) == false) {

                        distinctDB.push(ID);
                        if (!markersDB[ID]) {
                            createMarker(ID, longLat.split(",")[1], longLat.split(",")[0], showPointsArray[x].split(":")[1], 'backboneDB.png', markersDB, markerClusterDB, "DB")
                        }
                        else {
                            markersDB[ID].setMap(map);
                            markerClusterDB.addMarker(markersDB["" + ID]);
                        }
                    }
                }
            }
        }
        map.fitBounds(window["bounds_" + cableID]);


    }



    //To show all location type in grid & the element in case of DB
    for (var i = 0;i < filteredGridArray.length;i++) {

        if (filteredGridArray[i]["locationType"] == "Customer") {
            if (distinctCustomers.includes(filteredGridArray[i]["locationID"]) == false) {
                ID = filteredGridArray[i]["locationID"];
                distinctCustomers.push(ID);
                if (!markersCustomer[ID]) {
                    createMarker(ID, filteredGridArray[i]["longitude"], filteredGridArray[i]["latitude"], filteredGridArray[i]["locationName"], 'customerIcon.png', markersCustomer, markerClusterCustomers, "customer")
                }
                else {
                    markersCustomer[ID].setMap(map);
                    markerClusterCustomers.addMarker(markersCustomer["" + ID]);
                }
            }
        }// end customer case		
        else if (filteredGridArray[i]["locationType"] == "Site") {
            if (distinctSites.includes(filteredGridArray[i]["locationID"]) == false) {
                ID = filteredGridArray[i]["locationID"];
                distinctSites.push(ID);
                if (!markersSites[ID]) {
                    createMarker(ID, filteredGridArray[i]["longitude"], filteredGridArray[i]["latitude"], filteredGridArray[i]["locationName"], 'redSiteIcon.png', markersSites, markerClusterSites, "site")
                }
                else {
                    markersSites[ID].setMap(map);
                    markerClusterSites.addMarker(markersSites["" + ID]);
                }
            }
        }// end site case	
        else if (filteredGridArray[i]["locationType"] == "Manhole") {
            var manholeName = filteredGridArray[i]["locationName"];

            if (manholeName.endsWith("_J")) {
                if (distinctManholesWithJct.includes(filteredGridArray[i]["locationID"]) == false) {
                    ID = filteredGridArray[i]["locationID"];
                    distinctManholesWithJct.push(ID);
                    if (!markersManholesWithJct[ID]) {
                        createMarker(ID, filteredGridArray[i]["longitude"], filteredGridArray[i]["latitude"], filteredGridArray[i]["locationName"], "manholeJct.png", markersManholesWithJct, markerClusterManholesWithJct, "manholeWithJct")
                    }
                    else {
                        markersManholesWithJct[ID].setMap(map);
                        markerClusterManholesWithJct.addMarker(markersManholesWithJct["" + ID]);
                    }
                }
            }
            else {
                if (distinctManholes.includes(filteredGridArray[i]["locationID"]) == false) {
                    ID = filteredGridArray[i]["locationID"];
                    distinctManholes.push(ID);
                    if (!markersManholes[ID]) {
                        createMarker(ID, filteredGridArray[i]["longitude"], filteredGridArray[i]["latitude"], filteredGridArray[i]["locationName"], "manholeRed.png", markersManholes, markerClusterManholes, "manhole")
                    }
                    else {
                        markersManholes[ID].setMap(map);
                        markerClusterManholes.addMarker(markersManholes["" + ID]);
                    }
                }
            }
        }// end Manhole case	

        else if (filteredGridArray[i]["locationType"] == "Handhole") {
            var handholeName = filteredGridArray[i]["locationName"];

            if (handholeName.endsWith("_J")) {
                if (distinctHandholesWithJct.includes(filteredGridArray[i]["locationID"]) == false) {
                    ID = filteredGridArray[i]["locationID"];
                    distinctHandholesWithJct.push(ID);
                    if (!markersHandholesWithJct[ID]) {
                        createMarker(ID, filteredGridArray[i]["longitude"], filteredGridArray[i]["latitude"], filteredGridArray[i]["locationName"], "handholeYellowJct.png", markersHandholesWithJct, markerClusterHandholesWithJct, "handholeWithJct")
                    }
                    else {
                        markersHandholesWithJct[ID].setMap(map);
                        markerClusterHandholesWithJct.addMarker(markersHandholesWithJct["" + ID]);
                    }
                }
            }
            else {
                if (distinctHandholes.includes(filteredGridArray[i]["locationID"]) == false) {
                    ID = filteredGridArray[i]["locationID"];
                    distinctHandholes.push(ID);
                    if (!markersHandholes[ID]) {
                        createMarker(ID, filteredGridArray[i]["longitude"], filteredGridArray[i]["latitude"], filteredGridArray[i]["locationName"], "handholeYellow.png", markersHandholes, markerClusterHandholes, "handhole")
                    }
                    else {
                        markersHandholes[ID].setMap(map);
                        markerClusterHandholes.addMarker(markersHandholes["" + ID]);
                    }
                }
            }

        }// end Handhole case	
        else if (filteredGridArray[i]["locationType"] == "DB") {
            if (distinctDB.includes(filteredGridArray[i]["locationID"]) == false) {
                ID = filteredGridArray[i]["locationID"];
                distinctDB.push(ID);
                if (!markersDB[ID]) {
                    createMarker(ID, filteredGridArray[i]["longitude"], filteredGridArray[i]["latitude"], filteredGridArray[i]["locationName"], 'backboneDB.png', markersDB, markerClusterDB, "DB")
                }
                else {
                    markersDB[ID].setMap(map);
                    markerClusterDB.addMarker(markersDB["" + ID]);
                }
            }
        }// end db case


        //Now check the element type on each row in case of DB (the case of jct element type is added at the beggining of the function)
        if (filteredGridArray[i]["elementType"] == "DB") {
            if (distinctDB.includes(filteredGridArray[i]["elementID"]) == false) {
                ID = filteredGridArray[i]["elementID"];
                var longitude = filteredGridArray[i]["showElement"].split(":")[1].trim();
                var latitude = filteredGridArray[i]["showElement"].split(":")[2].trim();
                var Name = filteredGridArray[i]["showElement"].split(":")[3].trim();
                distinctDB.push(ID);
                if (!markersDB[ID]) {
                    createMarker(ID, longitude, latitude, Name, 'backboneDB.png', markersDB, markerClusterDB, "DB")
                }
                else {
                    markersDB[ID].setMap(map);
                    markerClusterDB.addMarker(markersDB["" + ID]);
                }
            }
        }// end db case		
    }

    var dropdownSelectedValue = document.getElementById('mapDropdown').value;

    if (distinctCustomers.length > 0) {
        $('.showHideAllCustCheckbox').prop('checked', true);
        $(".showHideAllCustCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllCustCheckbox').prop('checked', false);
        $(".showHideAllCustCheckbox").attr('disabled', true);
    }
    if (distinctSites.length > 0) {
        $('.showHideAllSitesCheckbox').prop('checked', true);
        $(".showHideAllSitesCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllSitesCheckbox').prop('checked', false);
        $(".showHideAllSitesCheckbox").attr('disabled', true);
    }
    if (distinctManholes.length > 0) {
        $('.showHideAllManholesCheckbox').prop('checked', true);
        $(".showHideAllManholesCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllManholesCheckbox').prop('checked', false);
        $(".showHideAllManholesCheckbox").attr('disabled', true);
    }
    if (distinctManholesWithJct.length > 0) {
        $('.showHideAllManholesWithJctCheckbox').prop('checked', true);
        $(".showHideAllManholesWithJctCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllManholesWithJctCheckbox').prop('checked', false);
        $(".showHideAllManholesWithJctCheckbox").attr('disabled', true);
    }
    if (distinctHandholes.length > 0) {
        $('.showHideAllHandholesCheckbox').prop('checked', true);
        $(".showHideAllHandholesCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllHandholesCheckbox').prop('checked', false);
        $(".showHideAllHandholesCheckbox").attr('disabled', true);
    }
    if (distinctHandholesWithJct.length > 0) {
        $('.showHideAllHandholesWithJctCheckbox').prop('checked', true);
        $(".showHideAllHandholesWithJctCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllHandholesWithJctCheckbox').prop('checked', false);
        $(".showHideAllHandholesWithJctCheckbox").attr('disabled', true);
    }
    if (distinctDB.length > 0) {
        $('.showHideAllDbCheckbox').prop('checked', true);
        $(".showHideAllDbCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllDbCheckbox').prop('checked', false);
        $(".showHideAllDbCheckbox").attr('disabled', true);
    }
    if (distinctJct.length > 0) {
        $('.showHideAllJctCheckbox').prop('checked', true);
        $(".showHideAllJctCheckbox").attr('disabled', false);
    }
    else {
        $('.showHideAllJctCheckbox').prop('checked', false);
        $(".showHideAllJctCheckbox").attr('disabled', true);
    }

    if (srcID.length > 0) {

        $(".showHideSrcCheckbox").attr('disabled', false);

        if (dropdownSelectedValue == "gridBased") {
            $('.showHideSrcCheckbox').prop('checked', false);

            if (srcID[0][1] == "warehouse") {
                markersArray = markersSites;
                clusterArray = markerClusterSites;
            }
            else if (srcID[0][1] == "customer") {
                markersArray = markersCustomer;
                clusterArray = markerClusterCustomers;
            }
            else if (srcID[0][1] == "manhole") {
                markersArray = markersManholes;
                clusterArray = markerClusterManholes;
            }
            else if (srcID[0][1] == "manholewithJct") {
                markersArray = markersManholesWithJct;
                clusterArray = markerClusterManholesWithJct;
            }
            else if (srcID[0][1] == "handhole") {
                markersArray = markersHandholes;
                clusterArray = markerClusterHandholes;
            }
            else if (srcID[0][1] == "handholewithJct") {
                markersArray = markersHandholesWithJct;
                clusterArray = markerClusterHandholesWithJct;
            }
            else if (srcID[0][1] == "DB") {
                markersArray = markersDB;
                clusterArray = markerClusterDB;
            }
            ID = srcID[0][0];
            markersArray[ID].setMap(null);
            clusterArray.removeMarker(markersArray[ID]);
        }
        else { //Case of cable based ( we should show the src and dst) 
            $('.showHideSrcCheckbox').prop('checked', true);
        }
    }
    else {
        $('.showHideSrcCheckbox').prop('checked', false);
        $(".showHideSrcCheckbox").attr('disabled', true);
    }

    if (dstID.length > 0) {
        $(".showHideDstCheckbox").attr('disabled', false);

        if (dropdownSelectedValue == "gridBased") {
            $('.showHideDstCheckbox').prop('checked', false);

            if (dstID[0][1] == "warehouse") {
                markersArray = markersSites;
                clusterArray = markerClusterSites;
            }
            else if (dstID[0][1] == "customer") {
                markersArray = markersCustomer;
                clusterArray = markerClusterCustomers;
            }
            else if (dstID[0][1] == "manhole") {
                markersArray = markersManholes;
                clusterArray = markerClusterManholes;
            }
            else if (dstID[0][1] == "manholewithJct") {
                markersArray = markersManholesWithJct;
                clusterArray = markerClusterManholesWithJct;
            }
            else if (dstID[0][1] == "handhole") {
                markersArray = markersHandholes;
                clusterArray = markerClusterHandholes;
            }
            else if (dstID[0][1] == "handholewithJct") {
                markersArray = markersHandholesWithJct;
                clusterArray = markerClusterHandholesWithJct;
            }
            else if (dstID[0][1] == "DB") {
                markersArray = markersDB;
                clusterArray = markerClusterDB;
            }
            ID = dstID[0][0];

            markersArray[ID].setMap(null);
            clusterArray.removeMarker(markersArray[ID]);

        }
        else { //cable based			
            $('.showHideDstCheckbox').prop('checked', true);

        }
    }
    else {
        $('.showHideDstCheckbox').prop('checked', false);
        $(".showHideDstCheckbox").attr('disabled', true);
    }
    document.getElementById("sitesCount").textContent = "(" + distinctSites.length + ")";
    document.getElementById("manholesCount").textContent = "(" + distinctManholes.length + ")";
    document.getElementById("manholesCountWithJct").textContent = "(" + distinctManholesWithJct.length + ")";
    document.getElementById("handholesCount").textContent = "(" + distinctHandholes.length + ")";
    document.getElementById("handholesCountWithJct").textContent = "(" + distinctHandholesWithJct.length + ")";
    document.getElementById("custCount").textContent = "(" + distinctCustomers.length + ")";
    document.getElementById("jctCount").textContent = "(" + distinctJct.length + ")";
    document.getElementById("dbCount").textContent = "(" + distinctDB.length + ")";

    //This is used to add the markers in cluster not around the cluster
    var currentCenter = map.getCenter();
    var currentZoom = map.getZoom();
    map.setCenter(currentCenter);
    map.setZoom(currentZoom);

} // End of showElementLocationPoints