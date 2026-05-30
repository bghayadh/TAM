function calculateDistanceInit() {
    $("#calculateGeoDistanceTube").click(function() {
        calculateGeoDistance("", "sourcelong", "sourcelat", "destinationlong", "destinationlat", "auxiliaryTableTubes", "auxGeoDistanceTube", "totalGeoDistanceTube");
    });
    $("#calculateGeoDistanceFiberTube").click(function() {
        calculateGeoDistance("", "tubeSource_Long", "tubeSource_Lat", "tubeDestination_Long", "tubeDestination_Lat", "TubeAuxTable", "auxGeoDistanceFiberTube", "totalGeoDistanceFiberTube");
    });
    $("#calculateGeoDistanceFiberStrand").click(function() {
        calculateGeoDistance("", "strandSource_Long", "strandSource_Lat", "strandDestination_Long", "strandDestination_Lat", "StrandAuxTable", "auxGeoDistanceFiberStrand", "totalGeoDistanceFiberStrand");
    });
    $("#calculateGeoDistanceStrand").click(function() {
        calculateGeoDistance("", "sourcelongstrand", "sourcelatstrand", "destinationlongstrand", "destinationlatstrand", "auxiliaryTableStrands", "auxGeoDistanceStrand", "totalGeoDistanceStrand");
    });
    $("#calculateGeoDistanceTrench").click(function() {
        calculateGeoDistance("", "SourceTrenchLng", "SourceTrenchLat", "DestinationTrenchLng", "DestinationTrenchLat", "auxiliary_trenchTable", "auxGeoDistanceTrench", "totalGeoDistanceTrench");
    });
    $("#calculateGeoDistanceDuct").click(function() {
        calculateGeoDistance("", "SourceDuctLng", "SourceDuctLat", "DestinationDuctLng", "DestinationDuctLat", "auxiliary_ductTable", "auxGeoDistanceDuct", "totalGeoDistanceDuct");
    });

    $("#calculateGeodistance").click(function() {
        calculateGeoDistance("FiberPathId", "SourceLng", "SourceLat", "DestinationLng", "DestinationLat", "auxiliaryTable", "auxGeoDistance", "totalGeoDistance");
        geoFlag = 0;
    });

    $("#calculateDrivingDistanceTube").click(function() {
        calculateDrivingDistance("sourcelong", "sourcelat", "destinationlong", "destinationlat", "tube", "tubeDistanceLstAuxToDestDrivg", "tubeTotalDistanceDrivg");
    });
    $("#calculateDrivingDistanceFiberTube").click(function() {
        calculateDrivingDistance("tubeSource_Long", "tubeSource_Lat", "tubeDestination_Long", "tubeDestination_Lat", "FiberTube", "fiberTubeDistanceLstAuxToDestDrivg", "fiberTubeTotalDistanceDrivg");
    });
    $("#calculateDrivingDistanceFiberStrand").click(function() {
        calculateDrivingDistance("strandSource_Long", "strandSource_Lat", "strandDestination_Long", "strandDestination_Lat", "FiberStrand", "fiberStrandDistanceLstAuxToDestDrivg", "fiberStrandTotalDistanceDrivg");
    });
    $("#calculateDrivingDistanceStrand").click(function() {
        calculateDrivingDistance("sourcelongstrand", "sourcelatstrand", "destinationlongstrand", "destinationlatstrand", "strand", "strandDistanceLstAuxToDestDrivg", "strandTotalDistanceDrivg");
    });
    $("#calculateDrivingDistanceTrench").click(function() {
        calculateDrivingDistance("SourceTrenchLng", "SourceTrenchLat", "DestinationTrenchLng", "DestinationTrenchLat", "trench", "trenchDistanceLstAuxToDestDrivg", "trenchTotalDistanceDrivg");
    });
    $("#calculateDrivingDistanceDuct").click(function() {
        calculateDrivingDistance("SourceDuctLng", "SourceDuctLat", "DestinationDuctLng", "DestinationDuctLat", "duct", "ductDistanceLstAuxToDestDrivg", "ductTotalDistanceDrivg");
    });
    $("#calculatedrivingdistance").click(function() {
        calculateDrivingDistance("SourceLng", "SourceLat", "DestinationLng", "DestinationLat", "fiber", "distanceLstAuxToDestDrivg", "totalDistanceDrivg", "FiberDrivDist");
    });

    $('#sortingDistance').click(function() {
        sortingByLineOfSite("SourceLng", "SourceLat", "DestinationLng", "DestinationLat", "auxiliaryTable", "aux_Lat", "aux_Long", "", "FiberLength")
    });
    $('#sortByDistanceFiberTube').click(function() {
        sortingByLineOfSite("tubeSource_Long", "tubeSource_Lat", "tubeDestination_Long", "tubeDestination_Lat", "TubeAuxTable", "tubeAuxiliary_Lat", "tubeAuxiliary_Lng", "FiberTube", "FiberTubeLength");
    });
    $('#sortByDistanceFiberStrand').click(function() {
        sortingByLineOfSite("strandSource_Long", "strandSource_Lat", "strandDestination_Long", "strandDestination_Lat", "StrandAuxTable", "strandAuxiliary_Lat", "strandAuxiliary_Lng", "FiberStrand", "FiberStrandLength");
    });
    $('#sortByDistanceTube').click(function() {
        sortingByLineOfSite("sourcelong", "sourcelat", "destinationlong", "destinationlat", "auxiliaryTableTubes", "auxTube_Lat", "auxTube_Long", "Tube", "TubeLength");
    });
    $('#sortByDistanceStrand').click(function() {
        sortingByLineOfSite("sourcelongstrand", "sourcelatstrand", "destinationlongstrand", "destinationlatstrand", "auxiliaryTableStrands", "auxStrand_Lat", "auxStrand_Long", "Strand", "StrandLength");
    });
    $('#sortByDistanceTrench').click(function() {
        sortingByLineOfSite("SourceTrenchLng", "SourceTrenchLat", "DestinationTrenchLng", "DestinationTrenchLat", "auxiliary_trenchTable", "aux_Lattrench", "aux_Longtrench", "Trench", "trenchLength");
    });
    $('#sortByDistanceDuct').click(function() {
        sortingByLineOfSite("SourceDuctLng", "SourceDuctLat", "DestinationDuctLng", "DestinationDuctLat", "auxiliary_ductTable", "aux_Latduct", "aux_Longduct", "Duct", "ductLength");
    });
}


function calculateDistanceSourceDestination(sLat, sLng, dLat, dLng, tId) {
    console.log("tId is ", tId);
    var auxArrayP = [];
    var countrowAuxiliaryFO = $('#' + tId + ' >tbody >tr').length;

    $('#' + tId + ' >tbody >tr').each(function() {

        var latAux = $(this).find("td:eq(4) input[type='text']").val();
        var lngAux = $(this).find("td:eq(3) input[type='text']").val();
        auxArrayP.push({ lat: latAux, lng: lngAux });

    });

    if (countrowAuxiliaryFO > 0) {
        var sourceFirstAux = haversine_distance(sLat, sLng, auxArrayP[0].lat, auxArrayP[0].lng);

        var betweenAux = 0;
        var totalDistanceis = 0;
        $("#" + tId + " >tbody >tr").eq(0).find('td').eq(5).children().val(parseFloat(sourceFirstAux).toFixed(3));

        for (var c = 0;c < countrowAuxiliaryFO - 1;c++) {
            var strictBetween = haversine_distance(auxArrayP[c].lat, auxArrayP[c].lng, auxArrayP[c + 1].lat, auxArrayP[c + 1].lng);
            betweenAux += strictBetween;
            $("#" + tId + " >tbody >tr").eq(c + 1).find('td').eq(5).children().val(parseFloat(strictBetween).toFixed(3));
        }

        var destinationLastAux = haversine_distance(auxArrayP[countrowAuxiliaryFO - 1].lat, auxArrayP[countrowAuxiliaryFO - 1].lng, dLat, dLng);
        totalDistanceis = sourceFirstAux + betweenAux + destinationLastAux;

        if (tId == 'auxiliaryTable') {

            $("#FiberLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#totalDistance").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDest").val(parseFloat(destinationLastAux).toFixed(3));
        }
        else if (tId == 'auxiliaryTableStrands') {

            $("#totalDistanceStrand").val(parseFloat(totalDistanceis).toFixed(3));
            $("#StrandLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestStrnd").val(parseFloat(destinationLastAux).toFixed(3));
        }
        else if (tId == 'auxiliaryTableTubes') {

            $("#totalDistanceTube").val(parseFloat(totalDistanceis).toFixed(3));
            $("#TubeLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestTube").val(parseFloat(destinationLastAux).toFixed(3));
        }
        else if (tId == 'auxiliary_trenchTable') {
            $("#trenchLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestTrench").val(parseFloat(destinationLastAux).toFixed(3));
            $("#totalDistanceTrench").val(parseFloat(totalDistanceis).toFixed(3));
        }
        else if (tId == 'auxiliary_ductTable') {
            $("#ductsLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestDuct").val(parseFloat(destinationLastAux).toFixed(3));
            $("#totalDistanceDuct").val(parseFloat(totalDistanceis).toFixed(3));
        }
    } else {
        totalDistanceis = haversine_distance(sLat, sLng, dLat, dLng);
        if (tId == 'auxiliaryTable') {
            $("#FiberLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#totalDistance").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDest").val('0.0');
        }
        else if (tId == 'auxiliaryTableStrands') {
            $("#StrandLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#totalDistanceStrand").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestStrnd").val('0.0');
        }
        else if (tId == 'auxiliaryTableTubes') {
            $("#totalDistanceTube").val(parseFloat(totalDistanceis).toFixed(3));
            $("#TubeLength").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestTube").val('0.0');
        }
        else if (tId == 'auxiliary_ductTable') {
            $("#totalDistanceDuct").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestDuct").val('0.0');
        }
        else if (tId == 'auxiliary_trenchTable') {
            $("#totalDistanceTrench").val(parseFloat(totalDistanceis).toFixed(3));
            $("#distanceLstAuxToDestTrench").val('0.0');
        }
    }
    auxArrayP = [];
}


function calculateGeoDistance(fiberId, sourceLng, sourceLat, destinationLng, destinationLat, tableID, tdName, totGeoDistance) {
    console.log("welcome to calculateGeoDistance method");

    var allAuxData = [];
    if (tableID == "auxiliaryTable") {
        var sourceLng = document.getElementById("" + sourceLng).value;
        var sourceLat = document.getElementById("" + sourceLat).value;
        var destinationLng = document.getElementById("" + destinationLng).value;
        var destinationLat = document.getElementById("" + destinationLat).value;

        var fiberId = document.getElementById("" + fiberId).value;
        getSelectedFiberCableRows(sourceLat, sourceLng, fiberId);
        allAuxData = dict;
    }
    else if (tableID == "auxiliaryTableStrands") {
        var sourceLng = document.getElementById("" + sourceLng).value;
        var sourceLat = document.getElementById("" + sourceLat).value;
        var destinationLng = document.getElementById("" + destinationLng).value;
        var destinationLat = document.getElementById("" + destinationLat).value;

        getSelectedStrandRows(sourceLat, sourceLng);
        allAuxData = dictStrandsAuxiliary;
    }
    else if (tableID == "auxiliary_trenchTable") {
        var sourceLng = document.getElementById("" + sourceLng).value;
        var sourceLat = document.getElementById("" + sourceLat).value;
        var destinationLng = document.getElementById("" + destinationLng).value;
        var destinationLat = document.getElementById("" + destinationLat).value;
        getSelectedTrenchAux(sourceLat, sourceLng);
        allAuxData = dict;
    }
    else if (tableID == "auxiliary_ductTable") {
        var sourceLng = document.getElementById("" + sourceLng).value;
        var sourceLat = document.getElementById("" + sourceLat).value;
        var destinationLng = document.getElementById("" + destinationLng).value;
        var destinationLat = document.getElementById("" + destinationLat).value;
        getSelectedDuctAux(sourceLat, sourceLng);
        allAuxData = dict;
    }
    else if (tableID == "TubeAuxTable") {
        var sourceLng = $("#" + sourceLng + indexTubeForAuxs).val();
        var sourceLat = $("#" + sourceLat + indexTubeForAuxs).val();
        var destinationLng = $("#" + destinationLng + indexTubeForAuxs).val();
        var destinationLat = $("#" + destinationLat + indexTubeForAuxs).val();

        var relTubeId = $("#tubeIdHeader").text();
        getSelectedTubesAux(relTubeId, indexTubeAux, indexTube);
        allAuxData = window["Auxpts_Tubes" + relTubeId];
    }
    else if (tableID == "StrandAuxTable") {
        var sourceLng = $("#" + sourceLng + indexStrandForAuxs).val();
        var sourceLat = $("#" + sourceLat + indexStrandForAuxs).val();
        var destinationLng = $("#" + destinationLng + indexStrandForAuxs).val();
        var destinationLat = $("#" + destinationLat + indexStrandForAuxs).val();

        var relStrandId = $("#strandIdHeader").text();
        getSelectedStrandsAux(StrandRefTube[1], StrandRefTube[2], relStrandId, StrandRefTube);
        allAuxData = window["Auxpts_Strands" + relStrandId];
    }
    else {
        var sourceLng = document.getElementById("" + sourceLng).value;
        var sourceLat = document.getElementById("" + sourceLat).value;
        var destinationLng = document.getElementById("" + destinationLng).value;
        var destinationLat = document.getElementById("" + destinationLat).value;

        getSelectedTubeRows(sourceLat, sourceLng);
        allAuxData = dictTubesAuxiliary;
    }

    var latitudeSrc = 0;
    var longitudeSrc = 0;
    var latitudeDst = 0;
    var longitudeDst = 0;

    if (allAuxData.length > 0) {
        var totalGeoDistance = 0.0;
        for (h = 0;h < allAuxData.length;h++) {
            if (h == 0) {
                latitudeSrc = sourceLat;
                longitudeSrc = sourceLng;
            } else {
                latitudeSrc = allAuxData[h - 1].aux_Latitude;
                longitudeSrc = allAuxData[h - 1].aux_Longitude;
            }

            if (h == allAuxData.length - 1) {
                latitudeDst = destinationLat;
                longitudeDst = destinationLng;
            }
            else {
                latitudeDst = allAuxData[h].aux_Latitude;
                longitudeDst = allAuxData[h].aux_Longitude;
            }

            var distance = (google.maps.geometry.spherical.computeDistanceBetween(new google.maps.LatLng(latitudeSrc, longitudeSrc), new google.maps.LatLng(latitudeDst, longitudeDst)) / 1000).toFixed(3);

            $("#" + tableID + " >tbody").find("tr").eq(h).find('td[name="' + tdName + '"]').children('input').val(distance);
            totalGeoDistance += parseFloat(distance)
        }
        $("#" + totGeoDistance).val(parseFloat(totalGeoDistance).toFixed(3));
    }
    else {
        latitudeSrc = sourceLat;
        longitudeSrc = sourceLng;
        latitudeDst = destinationLat;
        longitudeDst = destinationLng;
        var distance = (google.maps.geometry.spherical.computeDistanceBetween(new google.maps.LatLng(latitudeSrc, longitudeSrc), new google.maps.LatLng(latitudeDst, longitudeDst)) / 1000).toFixed(3);
        totalGeoDistance = parseFloat(distance)
        $("#" + totGeoDistance).val(parseFloat(totalGeoDistance).toFixed(3));
    }
}


function calculateDrivingDistance(sourceLng, sourceLat, destinationLng, destinationLat, type, lstAuxToDst, totalDist, drivingForm) {

    console.log("welcome to driving diatnace, the type is ", type);

    if (type == "FiberTube") {
        var sourceLng = $("#" + sourceLng + indexTubeForAuxs).val();
        var sourceLat = $("#" + sourceLat + indexTubeForAuxs).val();
        var destinationLng = $("#" + destinationLng + indexTubeForAuxs).val();
        var destinationLat = $("#" + destinationLat + indexTubeForAuxs).val();
    }
    else if (type == "FiberStrand") {
        var sourceLng = $("#" + sourceLng + indexStrandForAuxs).val();
        var sourceLat = $("#" + sourceLat + indexStrandForAuxs).val();
        var destinationLng = $("#" + destinationLng + indexStrandForAuxs).val();
        var destinationLat = $("#" + destinationLat + indexStrandForAuxs).val();
    }
    else {
        var sourceLng = document.getElementById("" + sourceLng).value;
        var sourceLat = document.getElementById("" + sourceLat).value;
        var destinationLng = document.getElementById("" + destinationLng).value;
        var destinationLat = document.getElementById("" + destinationLat).value;
    }

    var mapPoints = [];
    var directionsService = [];
    var directionsDisplay = [];
    var newmapPoints = [];
    var start, end;
    var waypts = [];
    var orderArray = [];
    var drivingDistance = [];
    var allAuxData = [];

    if (type == "fiber") {
        fiberId = document.getElementById("FiberPathId").value;
        getSelectedFiberCableRows(sourceLat, sourceLng, fiberId);
        allAuxData = dict;
    }
    else if (type == "strand") {
        getSelectedStrandRows(sourceLat, sourceLng);
        allAuxData = dictStrandsAuxiliary;
    }
    else if (type == "trench") {
        getSelectedTrenchAux(sourceLat, sourceLng);
        allAuxData = dict;
    }
    else if (type == "duct") {
        getSelectedDuctAux(sourceLat, sourceLng);
        allAuxData = dict;
    }
    else if (type == "FiberTube") {
        var relTubeId = $("#tubeIdHeader").text();
        getSelectedTubesAux(relTubeId, indexTubeAux, indexTube);
        allAuxData = window["Auxpts_Tubes" + relTubeId];
    }
    else if (type == "FiberStrand") {
        var relStrandId = $("#strandIdHeader").text();
        getSelectedStrandsAux(StrandRefTube[1], StrandRefTube[2], relStrandId, StrandRefTube);
        allAuxData = window["Auxpts_Strands" + relStrandId];
    }
    else {
        getSelectedTubeRows(sourceLat, sourceLng);
        allAuxData = dictTubesAuxiliary;
    }

    //get the original data from dictionary
    for (h = 0;h < allAuxData.length;h++) {
        mapPoints.push(new google.maps.LatLng(allAuxData[h].aux_Latitude, allAuxData[h].aux_Longitude));
    }
    for (var s = 0;s < mapPoints.length;s = s + 10) {
        orderArray.push(mapPoints[s]);
    }
    orderArray.unshift(new google.maps.LatLng(sourceLat, sourceLng));
    orderArray.push(mapPoints.slice(-1).pop());
    orderArray.push(new google.maps.LatLng(destinationLat, destinationLng));

    //Divide route to several parts because max stations limit is 25 (23 waypoints + 1 origin + 1 destination)
    for (var i = 0, parts = [], max = 25 - 1;i < orderArray.length;i = i + max) {
        parts.push(orderArray.slice(i, i + max + 1));
    }

    //Send requests to service to get route (for stations count <= 25 only one request will be sent)
    for (var i = 0;i < parts.length;i++) {
        newmapPoints = [];
        waypts = [];
        for (var j = 1;j < parts[i].length - 1;j++)
            newmapPoints.push(parts[i][j]);
        newmapPoints.push(parts[i][parts[i].length - 1]);
        newmapPoints.unshift(parts[i][0]);

        //get start , destination and waypoints coordinates
        for (var k = 0;k < newmapPoints.length;k++) {
            if (k >= 1 && k <= newmapPoints.length - 2) {
                waypts.push({
                    location: newmapPoints[k],
                    stopover: true
                });
            }
            if (k == 0)
                start = newmapPoints[k];

            if (k == newmapPoints.length - 1)
                end = newmapPoints[k];
        }

        //google maps driving travel mode with direction API request
        var request = {
            origin: start,
            destination: end,
            waypoints: waypts,
            travelMode: google.maps.TravelMode.DRIVING,
            unitSystem: google.maps.UnitSystem.METRIC
        };
        directionsService.push(new google.maps.DirectionsService());
        var instance = directionsService.length - 1;
        directionsDisplay.push(new google.maps.DirectionsRenderer({
            preserveViewport: true
        }));
        directionsService[instance].route(request, function(response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                computeDrivingDistance(response);
            }
            else if (status == "OVER_QUERY_LIMIT") {
                wait = true;
                setTimeout("wait = true", 1000);
                alert("Timeout");
            }
            else {
                alert("directions response " + status);
                $("#" + lstAuxToDst).val("0");
                $("#" + totalDist).val("0");
            }
        });

        //calculate driving distance and appending tables
        var drivingDist = 0;
        var totaldrivingDist = 0;
        function computeDrivingDistance(result) {
            var myroute = result.routes[0];
            for (i = 0;i < myroute.legs.length;i++) {
                drivingDist = (myroute.legs[i].distance.value / 1000).toFixed(3);
                totaldrivingDist += myroute.legs[i].distance.value;
                drivingDistance.push(drivingDist);
            }
            for (p = 0;p <= drivingDistance.length - 1;p++) {

                $("#" + lstAuxToDst).val(drivingDistance[p]);
                $("#" + totalDist).val((totaldrivingDist / 1000).toFixed(3));
                $("#" + drivingForm).val((totaldrivingDist / 1000).toFixed(3));
            }
        }
    }
}


function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function loadFiberList() {
    $('body').append('<div id="loading"><img id="loading-image" src="' + getContext() + '/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')

    $.ajax({
        url: "${pageContext.request.contextPath}/getFiberScript",
        type: "GET",
        success: async function(response) {
            fiberList = response.fiberList || [];
            auxDat = response.auxDat || [];

            console.log("FiberList loaded:", fiberList);
            console.log("Raw Aux Data loaded:", auxDat);

            // ---- Group auxiliary data by fiber cable ----
            let auxMap = {};
            auxDat.forEach(row => {
                let fiberId = row[0];
                let lng = parseFloat(row[1]);
                let lat = parseFloat(row[2]);
                let seq = parseInt(row[3]);

                if (!auxMap[fiberId]) auxMap[fiberId] = [];
                auxMap[fiberId].push({
                    aux_Longitude: lng,
                    aux_Latitude: lat,
                    aux_seqSorting: seq
                });
            });

            auxGrouped = Object.keys(auxMap).map(fiberId => ({
                fiberId,
                auxPoints: auxMap[fiberId]
            }));

            // ---- Loop all fibers with delay ----
            for (let i = 0;i < fiberList.length;i++) {
                const fiber = fiberList[i];
                const fiberId = fiber[0];
                const sourceLng = parseFloat(fiber[1]);
                const sourceLat = parseFloat(fiber[2]);
                const destinationLng = parseFloat(fiber[3]);
                const destinationLat = parseFloat(fiber[4]);

                getSelectedFiberAux(fiberId);
                const auxPoints = dict.length ? dict : [];

                calculateGeoDistanceBulk(
                    fiberId,
                    auxPoints,
                    sourceLng,
                    sourceLat,
                    destinationLng,
                    destinationLat
                );

                console.log(`Fiber ${fiberId} processed.`);
                await delay(20);
            }

            console.log("All fibers processed. GeoDistances:", geoDistances);

            // ---- Send geoDistances to server ----
            $.ajax({
                url: "${pageContext.request.contextPath}/updateGeoDistances",
                type: "POST",
                data: { geoDistances: JSON.stringify(geoDistances) },
                success: function(response) {
                    console.log("Server Response:", response);
                    $("#loading").remove();

                },
                error: function(err) {
                    console.error("Error sending geoDistances:", err);
                }
            });
        },
        error: function(err) {
            console.error("Error loading fiber list:", err);
        }
    });
}

// fill global dict
function getSelectedFiberAux(fiberId) {
    dict = [];
    const auxEntry = auxGrouped.find(a => a.fiberId == fiberId);
    if (auxEntry) {
        auxEntry.auxPoints.forEach(aux => {
            dict.push({
                aux_Name: "Aux_Point " + aux.aux_seqSorting,
                aux_Longitude: aux.aux_Longitude,
                aux_Latitude: aux.aux_Latitude,
                aux_seqSorting: aux.aux_seqSorting
            });
        });
    }
}

function calculateGeoDistanceBulk(fiberId, aux, sourceLng, sourceLat, destinationLng, destinationLat) {
    const allAuxData = aux || [];
    let totalGeoDistance = 0.0;

    // Ensure floats
    sourceLat = parseFloat(sourceLat);
    sourceLng = parseFloat(sourceLng);
    destinationLat = parseFloat(destinationLat);
    destinationLng = parseFloat(destinationLng);


    if (allAuxData.length > 0) {
        for (let h = 0;h < allAuxData.length + 1;h++) {
            let latitudeSrc = (h === 0) ? sourceLat : parseFloat(allAuxData[h - 1].aux_Latitude);
            let longitudeSrc = (h === 0) ? sourceLng : parseFloat(allAuxData[h - 1].aux_Longitude);
            let latitudeDst = (h === allAuxData.length) ? destinationLat : parseFloat(allAuxData[h].aux_Latitude);
            let longitudeDst = (h === allAuxData.length) ? destinationLng : parseFloat(allAuxData[h].aux_Longitude);


            if (!isNaN(latitudeSrc) && !isNaN(longitudeSrc) && !isNaN(latitudeDst) && !isNaN(longitudeDst)) {
                const segmentDistance = google.maps.geometry.spherical
                    .computeDistanceBetween(
                        new google.maps.LatLng(latitudeSrc, longitudeSrc),
                        new google.maps.LatLng(latitudeDst, longitudeDst)
                    ) / 1000;

                totalGeoDistance += segmentDistance;
            } else {
                console.warn(`â ï¸ Skipped invalid coords for fiber ${fiberId} at segment ${h}`);
            }
        }
    } else {
        // No aux points, direct link
        if (!isNaN(sourceLat) && !isNaN(sourceLng) && !isNaN(destinationLat) && !isNaN(destinationLng)) {
            totalGeoDistance = google.maps.geometry.spherical
                .computeDistanceBetween(
                    new google.maps.LatLng(sourceLat, sourceLng),
                    new google.maps.LatLng(destinationLat, destinationLng)
                ) / 1000;

        } else {
            console.warn(`Invalid main coordinates for fiber ${fiberId}`);
        }
    }

    geoDistances.push({
        fiberId: fiberId,
        distance: parseFloat(totalGeoDistance.toFixed(3))
    });


}

function updateLineOfSites() {


    $('body').append('<div id="loading"><img id="loading-image" src="' + getContext() + '/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')

    $.ajax({
        url: "${pageContext.request.contextPath}/updateLineOfSites",
        type: "GET",
        success: async function(response) {

            if (response) {

                console.log(response);



                $("#loading").remove();
            }

        },
        error: function(err) {
            console.error("Error loading fiber list:", err);
        }
    });
}

async function updateCities(item) {
    $('body').append('<div id="loading"><img id="loading-image" src="' + getContext() + '/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>');

    if (item === "fiber") {
        try {
            // Fetch fiber list
            const response = await $.ajax({
                url: `${pageContext.request.contextPath}/getFiberInfoCity`,
                type: "GET"
            });

            // Check if response contains fiber list
            if (response && response.fiberList) {
                const fiberList = response.fiberList;
                const geocoder = new google.maps.Geocoder();
                const fiberCityList = [];

                // Loop through each fiber and fetch city data
                for (let i = 0;i < fiberList.length;i++) {
                    const fiber = fiberList[i];
                    const fiberId = fiber[0];
                    const srcLng = parseFloat(fiber[1]);
                    const srcLat = parseFloat(fiber[2]);
                    const destLng = parseFloat(fiber[3]);
                    const destLat = parseFloat(fiber[4]);

                    // Geocode for source and destination cities (await)
                    const srcCity = await getCityFromCoords(srcLat, srcLng, geocoder);
                    const destCity = await getCityFromCoords(destLat, destLng, geocoder);

                    // Add fiber city data to list
                    fiberCityList.push({
                        fiberId: fiberId,
                        srcCity: srcCity,
                        destCity: destCity
                    });

                    console.log(fiberId + " --> srcCity: " + srcCity + " and distCity: " + destCity);
                    // Add delay between requests to prevent throttling

                }

                console.log("All fibers processed. City list:", fiberCityList);

                $.ajax({
                    url: `${pageContext.request.contextPath}/saveFiberCities`, // POST to server
                    type: "POST",
                    data: { fiberCityList: JSON.stringify(fiberCityList) },
                    success: function(response) {
                        console.log("Server Response:", response);
                        $("#loading").remove();  // Remove loading screen after processing
                    },
                    error: function(err) {
                        console.error("Error sending fiber city data:", err);
                    }
                });

            } else {
                console.warn("No fiber data found.");
                $("#loading").remove();  // Remove loading screen if no data
            }
        } catch (err) {
            console.error("Error loading fiber list:", err);
            $("#loading").remove();  // Remove loading screen on error
        }
    }

    else if (item === "manhole") {
        try {
            // Fetch fiber list
            const response = await $.ajax({
                url: `${pageContext.request.contextPath}/getManholeInfoCity`,
                type: "GET"
            });

            // Check if response contains fiber list
            if (response && response.manholeList) {
                const manholeList = response.manholeList;
                const geocoder = new google.maps.Geocoder();
                const manholeCityList = [];

                // Loop through each fiber and fetch city data
                for (let i = 0;i < manholeList.length;i++) {
                    const manhole = manholeList[i];
                    const manholeId = manhole[0];
                    const lng = parseFloat(manhole[1]);
                    const lat = parseFloat(manhole[2]);

                    // Geocode for source and destination cities (await)
                    const city = await getCityFromCoords(lat, lng, geocoder);

                    // Add fiber city data to list
                    manholeCityList.push({
                        manholeId: manholeId,
                        city: city

                    });

                    console.log(manholeId + " --> City: " + city);
                    // Add delay between requests to prevent throttling

                }

                console.log("All manholes processed. City list:", manholeCityList);

                $.ajax({
                    url: `${pageContext.request.contextPath}/saveManholeCities`, // POST to server
                    type: "POST",
                    data: { manholeCityList: JSON.stringify(manholeCityList) },
                    success: function(response) {
                        console.log("Server Response:", response);
                        $("#loading").remove();  // Remove loading screen after processing
                    },
                    error: function(err) {
                        console.error("Error sending fiber city data:", err);
                    }
                });

            } else {
                $("#loading").remove();  // Remove loading screen if no data
            }
        } catch (err) {
            console.error("Error loading  list:", err);
            $("#loading").remove();  // Remove loading screen on error
        }

    }

    else if (item === "handhole") {
        try {
            // Fetch fiber list

            const response = await $.ajax({
                url: `${pageContext.request.contextPath}/getHandholeInfoCity`,
                type: "GET"
            });

            // Check if response contains fiber list
            if (response && response.handholeList) {

                const handholeList = response.handholeList;
                const geocoder = new google.maps.Geocoder();
                const handholeCityList = [];

                // Loop through each fiber and fetch city data
                for (let i = 0;i < handholeList.length;i++) {
                    const handhole = handholeList[i];
                    const handholeId = handhole[0];
                    const lng = parseFloat(handhole[1]);
                    const lat = parseFloat(handhole[2]);

                    // Geocode for source and destination cities (await)
                    const city = await getCityFromCoords(lat, lng, geocoder);

                    // Add fiber city data to list
                    handholeCityList.push({
                        handholeId: handholeId,
                        city: city

                    });

                    console.log(handholeId + " --> City: " + city);
                    // Add delay between requests to prevent throttling

                }

                console.log("All handholes processed. City list:", handholeCityList);

                $.ajax({
                    url: `${pageContext.request.contextPath}/saveHandholeCities`, // POST to server
                    type: "POST",
                    data: { handholeCityList: JSON.stringify(handholeCityList) },
                    success: function(response) {
                        console.log("Server Response:", response);
                        $("#loading").remove();  // Remove loading screen after processing
                    },
                    error: function(err) {
                        console.error("Error sending fiber city data:", err);
                    }
                });

            } else {
                $("#loading").remove();  // Remove loading screen if no data
            }
        } catch (err) {
            console.error("Error loading  list:", err);
            $("#loading").remove();  // Remove loading screen on error
        }

    }
}

// Function to get city name from geocode results
function getCityFromCoords(lat, lng, geocoder) {
    return new Promise((resolve) => {
        const latlng = new google.maps.LatLng(lat, lng);

        geocoder.geocode({ latLng: latlng }, function(results, status) {
            if (status === google.maps.GeocoderStatus.OK && results[0]) {
                let city = "";

                // Look for "locality" type in address components
                for (let i = 0;i < results[0].address_components.length;i++) {
                    const comp = results[0].address_components[i];
                    if (comp.types.includes("locality")) {
                        city = comp.long_name;
                        break;
                    }
                }

                // Fallback to first part of formatted address if locality not found
                if (!city) {
                    city = results[0].formatted_address.split(",")[0];
                }

                resolve(city);
            } else {
                console.warn("Geocoder failed for", lat, lng, "â", status);
                resolve("");  // Resolve with empty string if geocoding fails
            }
        });
    });
}

function sortingByLineOfSite(srcLng, srcLat, dstLng, dstLat, tableID, auxiliaryLat, auxiliaryLng, target, pathLength) {
	
	console.log("Welcome to soring by line of site, the table ID is ", tableID);

    if (target == "FiberTube") {
        var sourceLng = $("#" + srcLng + indexTubeForAuxs).val();
        var sourceLat = $("#" + srcLat + indexTubeForAuxs).val();
        var destinationLng = $("#" + dstLng + indexTubeForAuxs).val();
        var destinationLat = $("#" + dstLat + indexTubeForAuxs).val();
    }
    else if (target == "FiberStrand") {
        var sourceLng = $("#" + srcLng + indexStrandForAuxs).val();
        var sourceLat = $("#" + srcLat + indexStrandForAuxs).val();
        var destinationLng = $("#" + dstLng + indexStrandForAuxs).val();
        var destinationLat = $("#" + dstLat + indexStrandForAuxs).val();
    }
    else {
        var sourceLng = document.getElementById("" + srcLng).value;
        var sourceLat = document.getElementById("" + srcLat).value;
        var destinationLng = document.getElementById("" + dstLng).value;
        var destinationLat = document.getElementById("" + dstLat).value;
    }

    cableAuxData = [];
    var rowCount = $('#' + tableID + ' >tbody >tr').length;
    var totalLengthBetweenAux = 0;
    var indexToSplice = 0, count = 0;

    if (rowCount > 0) {

        //Initialize the minimum Distance by Calculating the distance from source to first Aux point in array 
        var srcAuxMinLength = haversine_distance(sourceLat, sourceLng, $("#" + tableID + " >tbody >tr").eq(0).find('td').eq(4).children().val(), $("#" + tableID + " >tbody >tr").eq(0).find('td').eq(3).children().val());

        $('#' + tableID + ' >tbody >tr').each(function() {
            var latAux = $(this).find("td:eq(4) input[type='text']").val();
            var lngAux = $(this).find("td:eq(3) input[type='text']").val();
            var nameAux = $(this).find("td:eq(2) input[type='text']").val();

            //Calculate the distance from source to each Aux point in array 
            var sourceFirstAuxLngth = haversine_distance(sourceLat, sourceLng, latAux, lngAux);

            if (parseFloat(sourceFirstAuxLngth) <= parseFloat(srcAuxMinLength)) {
                srcAuxMinLength = sourceFirstAuxLngth;
                indexToSplice = count;
            }
            count++;
            cableAuxData.push({ auxLength: sourceFirstAuxLngth, lat: latAux, lng: lngAux, nameAux: nameAux });
        });

        $("#" + tableID + " > tbody").empty();

        if (tableID == "auxiliaryTable") {

            //Append first row with the closet point to source
            var markup = "<tr><td><input type='checkbox' class='rowAboveBelow' id='AuxFiber0' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberSeq' class='headcol'><input name='fiberCableSeq'  class='form-control text-input' type='text' value='1' readonly/></td>"
                + "<td name='auxiliary_Name'><input id='aux_Point0' class='form-control text-input' type='text' style='width:250px;position:relative;' value='" + cableAuxData[indexToSplice].nameAux + "' /></td>"
                + "<td name='auxiliary_Longitude'><input onchange='geoDistanceFlag()' id='aux_Long0' name='aux_Long'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='" + cableAuxData[indexToSplice].lng + "' /></td>"
                + "<td name='auxiliary_Latitude'> <input onchange='geoDistanceFlag()' id='aux_Lat0' name='aux_Lat'  style='width:200px;position:relative;'  type='text'  value='" + cableAuxData[indexToSplice].lat + "' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliary_Length'> <input id='aux_Len0'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + parseFloat(cableAuxData[indexToSplice].auxLength) + "'  /></td>"
                + "<td name='auxDrivingDist'> <input id='auxDrivingDist0' value='0' style='width:92px;position:relative;' type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistance'> <input id='auxGeoDistance0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "</tr>";

        }
        else if (tableID == "auxiliaryTableStrands") {

            var markup = "<tr><td><input class='rowAboveBelowStrand' id='AuxStrand0'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberSeq' class='headcol'><input name='strandSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='1' readonly/></td>"
                + "<td name='auxiliary_NameStrand' id='auxiliary_NameStrand0'><input id='aux_PointStrand0'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='" + cableAuxData[indexToSplice].nameAux + "' /></td>"
                + "<td name='auxiliary_Longitude'><input id='auxStrand_Long0' name='auxStrand_Long' class='form-control text-input' type='text' value='" + cableAuxData[indexToSplice].lng + "' style='width:150px;position:relative;' /></td>"
                + "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxStrand_Lat0' name='auxStrand_Lat' style='width:150px;position:relative;' value='" + cableAuxData[indexToSplice].lat + "'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliary_Length'> <input id='aux_LenStrand0'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + parseFloat(cableAuxData[indexToSplice].auxLength) + "'  /></td>"
                + "<td name='auxDrivingDistStrand'> <input id='auxStrandDrivingDist0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceStrand'> <input id='auxStrandGeoDistance0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";


        }
        else if (tableID == "auxiliary_trenchTable") {

            var markup = "<tr id='auxiliaryTrench_Row0' ><td><input class='rowAboveBelowTrench' id='Auxtrench0'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='TrenchSeq' class='headcol'><input name='TrenchSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='1' readonly/></td>"
                + "<td name='auxiliaryTrench_Name' id='auxiliaryTrench_Name0'><input id='aux_PointTrench0'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='" + cableAuxData[indexToSplice].nameAux + "' /></td>"
                + "<td name='auxiliaryTrench_Longitude'><input id='aux_Longtrench0' name='aux_Longtrench' class='form-control text-input' type='text' value='" + cableAuxData[indexToSplice].lng + "' style='width:150px;position:relative;' /></td>"
                + "<td name='auxiliaryTrench_Latitude' style='width:200px'> <input id='aux_Lattrench0' name='aux_Lattrench' style='width:150px;position:relative;' value='" + cableAuxData[indexToSplice].lat + "'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliaryTrench_Length'> <input id='aux_LenTrench0'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + parseFloat(cableAuxData[indexToSplice].auxLength) + "'  /></td>"
                + "<td name='auxDrivingDistTrench'> <input id='auxTrenchDrivingDist0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceTrench'> <input id='auxTrenchGeoDistance0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";


        }
        else if (tableID == "auxiliary_ductTable") {

            var markup = "<tr id='auxiliaryDuct_Row0' ><td name='AuxDuct' ><input class='rowAboveBelowDuct' id='Auxduct0'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='DuctSeq' class='headcol'><input name='DuctSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='1' readonly/></td>"
                + "<td name='auxiliaryDuct_Name' id='auxiliaryDuct_Name0'><input id='aux_PointDuct0'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='" + cableAuxData[indexToSplice].nameAux + "' /></td>"
                + "<td name='auxiliary_LongitudeDuct'><input id='aux_Longduct0' name='aux_Longduct' class='form-control text-input' type='text' value='" + cableAuxData[indexToSplice].lng + "' style='width:150px;position:relative;' /></td>"
                + "<td name='auxiliary_LatitudeDuct' style='width:200px'> <input id='aux_Latduct0' name='aux_Latduct' style='width:150px;position:relative;' value='" + cableAuxData[indexToSplice].lat + "'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliaryDuct_Length'> <input id='aux_LenDuct0'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + parseFloat(cableAuxData[indexToSplice].auxLength) + "'  /></td>"
                + "<td name='auxDrivingDistDuct'> <input id='auxDuctDrivingDist0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceDuct'> <input id='auxDuctGeoDistance0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";
        }
        else if (tableID == "TubeAuxTable") {
            var markup = "<tr id='tubeAux_Row0'><td name='AuxFiberTube' ><input class='rowAboveBelowFiberTube' id='AuxFiberTube0'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberTubeAuxSeq' class='headcol' ><input name='tubeAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='1' readonly/></td>"
                + "<td name='TubeAuxiliary' id='TubeAuxiliary0' ><input id='tubeAuxiliary0'  class='form-control text-input' type='text' style='width:200px;position:relative;'  value='" + cableAuxData[indexToSplice].nameAux + "' /></td>"
                + "<td name='TubeAuxiliary_Longitude'><input id='tubeAuxiliary_Lng0' name='tubeAuxiliary_Lng' class='form-control text-input' type='text' style='width:150px;position:relative;'  value='" + cableAuxData[indexToSplice].lng + "' /></td>"
                + "<td name='TubeAuxiliary_Latitude' style='width:200px'> <input id='tubeAuxiliary_Lat0' name='tubeAuxiliary_Lat' style='width:150px;position:relative;'  type='text' value='" + cableAuxData[indexToSplice].lat + "'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='TubeAuxiliaryLng'> <input id='tubeAuxiliary_Len0'style='width:150px;position:relative;'  readonly type='text' value='" + cableAuxData[indexToSplice].auxLength + "'   class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxDrivingDistFiberTube'> <input id='auxFiberTubeDrivingDist0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceFiberTube'> <input id='auxFiberTubeGeoDistance0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "</tr>";
        }
        else if (tableID == "StrandAuxTable") {
            var markup = "<tr id='strandAux_Row0'><td name='AuxFiberStrand' ><input class='rowAboveBelowFiberStrand' id='AuxFiberStrand0'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberStrandAuxSeq' class='headcol' ><input name='strandAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='1' readonly/></td>"
                + "<td name='StrandAuxiliary' id='StrandAuxiliary0' ><input id='strandAuxiliary0'  class='form-control text-input' type='text' style='width:200px;position:relative;'  value='" + cableAuxData[indexToSplice].nameAux + "' /></td>"
                + "<td name='StrandAuxiliary_Longitude'><input id='strandAuxiliary_Lng0' name='strandAuxiliary_Lng' class='form-control text-input' type='text' style='width:150px;position:relative;'  value='" + cableAuxData[indexToSplice].lng + "' /></td>"
                + "<td name='StrandAuxiliary_Latitude' style='width:200px'> <input id='strandAuxiliary_Lat0' name='strandAuxiliary_Lat' style='width:150px;position:relative;'  type='text' value='" + cableAuxData[indexToSplice].lat + "'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='StrandAuxiliary_Length'> <input id='strandAuxiliary_Len0'style='width:150px;position:relative;'  readonly type='text' value='" + cableAuxData[indexToSplice].auxLength + "'   class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxDrivingDistFiberStrand'> <input id='auxFiberStrandDrivingDist0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceFiberStrand'> <input id='auxFiberStrandGeoDistance0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "</tr>";
        }
        else {

            var markup = "<tr><td><input class='rowAboveBelowTube' id='AuxTube0'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberSeq' class='headcol'><input name='tubeSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='1' readonly/></td>"
                + "<td name='auxiliary_NameTube' id='auxiliary_NameTube0'><input id='aux_PointTube0'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='" + cableAuxData[indexToSplice].nameAux + "' /></td>"
                + "<td name='auxiliary_Longitude'><input id='auxTube_Long0' name='auxTube_Long' class='form-control text-input' type='text' value='" + cableAuxData[indexToSplice].lng + "' style='width:150px;position:relative;' /></td>"
                + "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxTube_Lat0' name='auxTube_Lat' style='width:150px;position:relative;' value='" + cableAuxData[indexToSplice].lat + "'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliary_Length'> <input id='aux_LenTube0'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + parseFloat(cableAuxData[indexToSplice].auxLength) + "'  /></td>"
                + "<td name='auxDrivingDistTube'> <input id='auxTubeDrivingDist0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceTube'> <input id='auxTubeGeoDistance0' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";


        }
        $("#" + tableID + " > tbody").append(markup);


        //Remove the closest point to source from array
        cableAuxData.splice(indexToSplice, 1);

        markup = "";
        var newRowCount = 1;

        //Enter the loop if more than one Aux point exists in table except the closest point
        while (cableAuxData.length >= 1) {

            //Initialize the min Distance ( the dist between Closest Aux point already appended & 1st Aux in array after splice)
            minDistanceBtwAux = haversine_distance($("#" + auxiliaryLat + "" + (newRowCount - 1)).val(), $("#" + auxiliaryLng + "" + (newRowCount - 1)).val(), cableAuxData[0].lat, cableAuxData[0].lng);

            for (var i = 0;i < cableAuxData.length;i++) {

                var distanceBtwAux = haversine_distance($("#" + auxiliaryLat + "" + (newRowCount - 1)).val(), $("#" + auxiliaryLng + "" + (newRowCount - 1)).val(), cableAuxData[i].lat, cableAuxData[i].lng);

                //Change the distance in array
                cableAuxData[i].auxLength = distanceBtwAux;

                if (distanceBtwAux <= minDistanceBtwAux) {
                    minDistanceBtwAux = distanceBtwAux;
                    //Set the index of element to splice from cableAuxData array 
                    indexToSplice = i;
                }
            }

            markup = "";

            if (tableID == "auxiliaryTable") {

                //Append the row of closest Aux point 	    	
                markup = "<tr><td><input type='checkbox' class='rowAboveBelow' id='AuxFiber" + (newRowCount) + "' style='position:relative;left:20px;top:10px' name='record'></td>"
                    + "<td name='fiberSeq' class='headcol'><input name='fiberCableSeq'  class='form-control text-input' type='text' value='" + (newRowCount + 1) + "' readonly/></td>"
                    + "<td name='auxiliary_Name'><input id='aux_Point" + (newRowCount) + "' class='form-control text-input' type='text' style='width:250px;position:relative;' /></td>"
                    + "<td name='auxiliary_Longitude'><input onchange='geoDistanceFlag()' id='aux_Long" + (newRowCount) + "' name='aux_Long'  class='form-control text-input' type='text'  style='width:200px;position:relative;'/></td>"
                    + "<td name='auxiliary_Latitude'> <input onchange='geoDistanceFlag()' id='aux_Lat" + (newRowCount) + "' name='aux_Lat'  style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxiliary_Length'> <input id='aux_Len" + (newRowCount) + "' style='width:150px;position:relative;' type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxDrivingDist'> <input id='auxDrivingDist" + (newRowCount) + "' value='0' style='width:92px;position:relative;' type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxGeoDistance'> <input id='auxGeoDistance" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "</tr>";

                $("#" + tableID + " > tbody").append(markup);
                $("#aux_Point" + (newRowCount)).val(cableAuxData[indexToSplice].nameAux);
                $("#aux_Long" + (newRowCount)).val(cableAuxData[indexToSplice].lng);
                $("#aux_Lat" + (newRowCount)).val(cableAuxData[indexToSplice].lat);
                $("#aux_Len" + (newRowCount)).val(parseFloat(cableAuxData[indexToSplice].auxLength));

            }
            else if (tableID == "auxiliaryTableStrands") {

                var markup = "<tr><td name='AuxStrand' ><input class='rowAboveBelowStrand' id='AuxStrand" + (newRowCount) + "'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                    + "<td name='fiberSeq' class='headcol'><input name='strandSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (newRowCount + 1) + "' readonly/></td>"
                    + "<td name='auxiliary_NameStrand' id='auxiliary_NameStrand" + (newRowCount) + "'><input id='aux_PointStrand" + (newRowCount) + "'  class='form-control text-input' type='text' style='width:200px;position:relative;' /></td>"
                    + "<td name='auxiliary_Longitude'><input id='auxStrand_Long" + (newRowCount) + "' name='auxStrand_Long' class='form-control text-input' type='text' style='width:150px;position:relative;' /></td>"
                    + "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxStrand_Lat" + (newRowCount) + "' name='auxStrand_Lat' style='width:150px;position:relative;'   type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxiliary_Length'> <input id='aux_LenStrand" + (newRowCount) + "' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'  /></td>"
                    + "<td name='auxDrivingDistStrand'> <input id='auxStrandDrivingDist" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxGeoDistanceStrand'> <input id='auxStrandGeoDistance" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";

                $("#" + tableID + " > tbody").append(markup);
                $("#aux_PointStrand" + (newRowCount)).val(cableAuxData[indexToSplice].nameAux);
                $("#auxStrand_Long" + (newRowCount)).val(cableAuxData[indexToSplice].lng);
                $("#auxStrand_Lat" + (newRowCount)).val(cableAuxData[indexToSplice].lat);
                $("#aux_LenStrand" + (newRowCount)).val(parseFloat(cableAuxData[indexToSplice].auxLength));
            }
            else if (tableID == "auxiliary_trenchTable") {

                var markup = "<tr id='auxiliaryTrench_Row" + newRowCount + "'><td name='AuxTrench' ><input class='rowAboveBelowTrench' id='Auxtrench" + (newRowCount) + "'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                    + "<td name='TrenchSeq' class='headcol'><input name='TrenchSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (newRowCount + 1) + "' readonly/></td>"
                    + "<td name='auxiliaryTrench_Name' id='auxiliaryTrench_Name" + (newRowCount) + "'><input id='aux_PointTrench" + (newRowCount) + "'  class='form-control text-input' type='text' style='width:200px;position:relative;' /></td>"
                    + "<td name='auxiliaryTrench_Longitude'><input id='aux_Longtrench" + (newRowCount) + "' name='aux_Longtrench' class='form-control text-input' type='text' style='width:150px;position:relative;' /></td>"
                    + "<td name='auxiliaryTrench_Latitude' style='width:200px'> <input id='aux_Lattrench" + (newRowCount) + "' name='aux_Lattrench' style='width:150px;position:relative;'   type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxiliaryTrench_Length'> <input id='aux_LenTrench" + (newRowCount) + "' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'  /></td>"
                    + "<td name='auxDrivingDistTrench'> <input id='auxTrenchDrivingDist" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxGeoDistanceTrench'> <input id='auxTrenchGeoDistance" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";

                $("#" + tableID + " > tbody").append(markup);
                $("#aux_PointTrench" + (newRowCount)).val(cableAuxData[indexToSplice].nameAux);
                $("#aux_Longtrench" + (newRowCount)).val(cableAuxData[indexToSplice].lng);
                $("#aux_Lattrench" + (newRowCount)).val(cableAuxData[indexToSplice].lat);
                $("#aux_LenTrench" + (newRowCount)).val(parseFloat(cableAuxData[indexToSplice].auxLength));

            }
            else if (tableID == "auxiliary_ductTable") {
                var markup = "<tr id='auxiliaryDuct_Row" + newRowCount + "'><td name='AuxDuct' ><input class='rowAboveBelowDuct' id='Auxduct" + (newRowCount) + "'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                    + "<td name='DuctSeq' class='headcol'><input name='DuctSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (newRowCount + 1) + "' readonly/></td>"
                    + "<td name='auxiliaryDuct_Name' id='auxiliaryDuct_Name" + (newRowCount) + "'><input id='aux_PointDuct" + (newRowCount) + "'  class='form-control text-input' type='text' style='width:200px;position:relative;' /></td>"
                    + "<td name='auxiliary_LongitudeDuct'><input id='aux_Longduct" + (newRowCount) + "' name='aux_Longduct' class='form-control text-input' type='text' style='width:150px;position:relative;' /></td>"
                    + "<td name='auxiliary_LatitudeDuct' style='width:200px'> <input id='aux_Latduct" + (newRowCount) + "' name='aux_Latduct' style='width:150px;position:relative;'   type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxiliaryDuct_Length'> <input id='aux_LenDuct" + (newRowCount) + "' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'  /></td>"
                    + "<td name='auxDrivingDistDuct'> <input id='auxDuctDrivingDist" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxGeoDistanceDuct'> <input id='auxDuctGeoDistance" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";

                $("#" + tableID + " > tbody").append(markup);
                $("#aux_PointDuct" + (newRowCount)).val(cableAuxData[indexToSplice].nameAux);
                $("#aux_Longduct" + (newRowCount)).val(cableAuxData[indexToSplice].lng);
                $("#aux_Latduct" + (newRowCount)).val(cableAuxData[indexToSplice].lat);
                $("#aux_LenDuct" + (newRowCount)).val(parseFloat(cableAuxData[indexToSplice].auxLength));
            }
            else if (tableID == "TubeAuxTable") {

                markup = "<tr id='tubeAux_Row" + newRowCount + "'><td name='AuxFiberTube' ><input class='rowAboveBelowFiberTube' id='AuxFiberTube" + newRowCount + "'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                    + "<td name='fiberTubeAuxSeq' class='headcol' ><input name='tubeAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (newRowCount + 1) + "' readonly/></td>"
                    + "<td name='TubeAuxiliary' id='TubeAuxiliary" + newRowCount + "' ><input id='tubeAuxiliary" + newRowCount + "'  class='form-control text-input' type='text' style='width:200px;position:relative;'  /></td>"
                    + "<td name='TubeAuxiliary_Longitude'><input id='tubeAuxiliary_Lng" + newRowCount + "' name ='tubeAuxiliary_Lng' class='form-control text-input' type='text' style='width:150px;position:relative;'  /></td>"
                    + "<td name='TubeAuxiliary_Latitude' style='width:200px'> <input id='tubeAuxiliary_Lat" + newRowCount + "' name='tubeAuxiliary_Lat' style='width:150px;position:relative;'  type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='TubeAuxiliaryLng'> <input id='tubeAuxiliary_Len" + newRowCount + "'style='width:150px;position:relative;'  readonly type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxDrivingDistFiberTube'> <input id='auxFiberTubeDrivingDist" + newRowCount + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxGeoDistanceFiberTube'> <input id='auxFiberTubeGeoDistance" + newRowCount + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "</tr>";

                $("#" + tableID + " > tbody").append(markup);
                $("#tubeAuxiliary" + (newRowCount)).val(cableAuxData[indexToSplice].nameAux);
                $("#tubeAuxiliary_Lng" + (newRowCount)).val(cableAuxData[indexToSplice].lng);
                $("#tubeAuxiliary_Lat" + (newRowCount)).val(cableAuxData[indexToSplice].lat);
                $("#tubeAuxiliary_Len" + (newRowCount)).val(parseFloat(cableAuxData[indexToSplice].auxLength));
            }
            else if (tableID == "StrandAuxTable") {

                markup = "<tr id='strandAux_Row" + newRowCount + "'><td name='AuxFiberStrand' ><input class='rowAboveBelowFiberStrand' id='AuxFiberStrand" + newRowCount + "'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                    + "<td name='fiberStrandAuxSeq' class='headcol' ><input name='tubeAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (newRowCount + 1) + "' readonly/></td>"
                    + "<td name='StrandAuxiliary' id='StrandAuxiliary" + newRowCount + "' ><input id='strandAuxiliary" + newRowCount + "'  class='form-control text-input' type='text' style='width:200px;position:relative;'  /></td>"
                    + "<td name='StrandAuxiliary_Longitude'><input id='strandAuxiliary_Lng" + newRowCount + "' name ='strandAuxiliary_Lng' class='form-control text-input' type='text' style='width:150px;position:relative;'  /></td>"
                    + "<td name='StrandAuxiliary_Latitude' style='width:200px'> <input id='strandAuxiliary_Lat" + newRowCount + "' name='strandAuxiliary_Lat' style='width:150px;position:relative;'  type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='StrandAuxiliary_Length'> <input id='strandAuxiliary_Len" + newRowCount + "'style='width:150px;position:relative;'  readonly type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxDrivingDistFiberStrand'> <input id='auxFiberStrandDrivingDist" + newRowCount + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxGeoDistanceFiberStrand'> <input id='auxFiberStrandGeoDistance" + newRowCount + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "</tr>";

                $("#" + tableID + " > tbody").append(markup);
                $("#strandAuxiliary" + (newRowCount)).val(cableAuxData[indexToSplice].nameAux);
                $("#strandAuxiliary_Lng" + (newRowCount)).val(cableAuxData[indexToSplice].lng);
                $("#strandAuxiliary_Lat" + (newRowCount)).val(cableAuxData[indexToSplice].lat);
                $("#strandAuxiliary_Len" + (newRowCount)).val(parseFloat(cableAuxData[indexToSplice].auxLength));
            }
            else {
                var markup = "<tr><td name='AuxTube' ><input class='rowAboveBelowTube' id='AuxTube" + (newRowCount) + "'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                    + "<td name='fiberSeq' class='headcol'><input name='tubeSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (newRowCount + 1) + "' readonly/></td>"
                    + "<td name='auxiliary_NameTube' id='auxiliary_NameTube" + (newRowCount) + "'><input id='aux_PointTube" + (newRowCount) + "'  class='form-control text-input' type='text' style='width:200px;position:relative;' /></td>"
                    + "<td name='auxiliary_Longitude'><input id='auxTube_Long" + (newRowCount) + "' name='auxTube_Long' class='form-control text-input' type='text' style='width:150px;position:relative;' /></td>"
                    + "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxTube_Lat" + (newRowCount) + "' name='auxTube_Lat' style='width:150px;position:relative;'   type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxiliary_Length'> <input id='aux_LenTube" + (newRowCount) + "' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'  /></td>"
                    + "<td name='auxDrivingDistTube'> <input id='auxTubeDrivingDist" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                    + "<td name='auxGeoDistanceTube'> <input id='auxTubeGeoDistance" + (newRowCount) + "' value='0' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";

                $("#" + tableID + " > tbody").append(markup);
                $("#aux_PointTube" + (newRowCount)).val(cableAuxData[indexToSplice].nameAux);
                $("#auxTube_Long" + (newRowCount)).val(cableAuxData[indexToSplice].lng);
                $("#auxTube_Lat" + (newRowCount)).val(cableAuxData[indexToSplice].lat);
                $("#aux_LenTube" + (newRowCount)).val(parseFloat(cableAuxData[indexToSplice].auxLength));
            }


            totalLengthBetweenAux += cableAuxData[indexToSplice].auxLength;
            newRowCount++;

            if (cableAuxData.length == 1) {
                var destLastAuxDistance = haversine_distance(cableAuxData[0].lat, cableAuxData[0].lng, destinationLat, destinationLng);
                var lengthLastAux = haversine_distance($("#" + auxiliaryLat + "" + (newRowCount - 1)).val(), $("#" + auxiliaryLng + "" + (newRowCount - 1)).val(), cableAuxData[0].lat, cableAuxData[0].lng);
                $("#distanceLstAuxToDest" + target).val(parseFloat(destLastAuxDistance).toFixed(3));
                //$("#distanceLstAuxToDestTube").val(parseFloat(destLastAuxDistance).toFixed(3));
                //$("#distanceLstAuxToDestStrand").val(parseFloat(destLastAuxDistance).toFixed(3));

            }

            //Remove the Aux point from array 
            cableAuxData.splice(indexToSplice, 1);

            var totalLength = srcAuxMinLength + totalLengthBetweenAux + destLastAuxDistance;
            $("#totalDistance" + target).val(parseFloat(totalLength).toFixed(3));
            $("#" + pathLength).val(parseFloat(totalLength).toFixed(3));

        }

        $("table[id='" + tableID + "'] tr").focusin(function() {
            $("table[id='" + tableID + "'] tr").removeClass("ativeRecord")
            $(this).addClass("ativeRecord");
        });

        if (tableID == "auxiliaryTable") {
            $("td[name='auxiliary_Name']").each(function(ind) {
                AuxPointAutoComplete("auxPtAutocomplete", "aux_Point" + ind, "aux_Long" + ind, "aux_Lat" + ind, "SourceLat", "SourceLng", "DestinationLat", "DestinationLng", "auxiliaryTable", ind);
            });
        }
        else if (tableID == "auxiliaryTableStrands") {
            $("td[name='auxiliary_NameStrand']").each(function(indStrnd) {
                AuxPointAutoComplete("auxPtStrandAutocomplete", "aux_PointStrand" + indStrnd, "auxStrand_Long" + indStrnd, "auxStrand_Lat" + indStrnd, "sourcelatstrand", "sourcelongstrand", "destinationlatstrand", "destinationlongstrand", "auxiliaryTableStrands", indStrnd);
            });
        }
        else if (tableID == "auxiliary_trenchTable") {
            $("td[name='auxiliaryTrench_Name']").each(function(indTrnch) {
                AuxPointAutoComplete("auxPtTrenchAutocomplete", "aux_PointTrench" + indTrnch, "aux_Longtrench" + indTrnch, "aux_Lattrench" + indTrnch, "SourceTrenchLat", "SourceTrenchLng", "DestinationTrenchLat", "DestinationTrenchLng", "auxiliary_trenchTable", indTrnch);
            });
        }
        else if (tableID == "auxiliary_ductTable") {
            $("td[name='auxiliaryDuct_Name']").each(function(indDuct) {
                AuxPointAutoComplete("auxPtDuctAutocomplete", "aux_PointDuct" + indDuct, "aux_Longduct" + indDuct, "aux_Latduct" + indDuct, "SourceDuctLat", "SourceDuctLng", "DestinationDuctLat", "DestinationDuctLng", "auxiliary_ductTable", indDuct);
            });
        }
        else if (tableID == "TubeAuxTable") {
            $("td[name='TubeAuxiliary']").each(function(indFiberTube) {
                AuxPointAutoComplete("FiberTubeAuxAutoComplete", "tubeAuxiliary" + indFiberTube, "tubeAuxiliary_Lng" + indFiberTube, "tubeAuxiliary_Lat" + indFiberTube, "", "", "", "", "TubeAuxTable", indFiberTube);
            });
        }
        else if (tableID == "StrandAuxTable") {
            $("td[name='StrandAuxiliary']").each(function(indFiberStrand) {
                AuxPointAutoComplete("FiberStrandAuxAutoComplete", "strandAuxiliary" + indFiberStrand, "strandAuxiliary_Lng" + indFiberStrand, "strandAuxiliary_Lat" + indFiberStrand, "", "", "", "", "StrandAuxTable", indFiberStrand);
            });
        }
        else {
            $("td[name='auxiliary_NameTube']").each(function(indTubee) {
                AuxPointAutoComplete("auxPtTubeAutocomplete", "aux_PointTube" + indTubee, "auxTube_Long" + indTubee, "auxTube_Lat" + indTubee, "sourcelat", "sourcelong", "destinationlat", "destinationlong", "auxiliaryTableTubes", indTubee);
            });
        }

    }

    // row Count is zero
    else {
        totalLength = haversine_distance(sourceLat, sourceLng, destinationLat, destinationLng);
        $("#" + pathLength).val(parseFloat(totalLength).toFixed(3));

        $("#totalDistance" + target).val(parseFloat(totalLength).toFixed(3));
        $("#distanceLstAuxToDest" + target).val('0.0');
    }
}
