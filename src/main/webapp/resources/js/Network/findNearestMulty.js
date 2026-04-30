let checkedCounts = {};

function viewNearestMultyPointEvent() {
    $("#viewNeareMulty").on('click', function() {
        var closestDisRange = document.getElementById("closestMultyDisRange").value;

        if (closestDisRange === "" || isNaN(closestDisRange)) {
            alert("Please enter a number in the input field.");
            return;

        }
        var urlString = "";
        var nop = document.getElementById("noP_Multy").value;
        var circleRange_multy = document.getElementById("circleRange_multy").value;
        var closestDisRange = document.getElementById("closestMultyDisRange").value;
        console.log("zeinaaa");
        $("#Multy_auxiliary > tbody").find('input[name="record"]').each(function() {
            var $row = $(this).closest('tr');
            var indexSite = $row.index();
            var valuesArray = [];  // Create an empty array to store the values

            // Assuming you want to loop through multiple elements with the same pattern
            $('input[id^="Seq_Multy"]').each(function(index) {
                var seq = $(this).val();  // Get the value of each input
                valuesArray.push(seq);  // Push the value to the array
            });

            var name = $row.find('td[name="siteId_Multy"] input').val();
            var lng = $row.find('td[name="siteLng_Multy"] input').val();
            var lat = $row.find('td[name="siteLat_Multy"] input').val();
            var locationNum = $row.find('td[name="location_number"] input').val(); // Get the location number
            console.log(locationNum);
            var circleDraw = $row.find('input[name="circleRange' + indexSite + '"]').prop('checked') ? 1 : 0;
            var squareDraw = $row.find('input[name="squareRange' + indexSite + '"]').prop('checked') ? 1 : 0;
            var rowMultyIndex = indexSite;
            if ($("#circleRange_multy").prop('checked')) {
                checkedOption = "circleRange_multy";
                urlString += "&seq=" + valuesArray + "";
                urlString += "&name=" + name + "";
                urlString += "&lng=" + lng + "";
                urlString += "&lat=" + lat + "";
                urlString += "&locationNum=" + locationNum + "";
                urlString += "&circleDraw=" + circleDraw + "";
                urlString += "&squareDraw=" + squareDraw + "";

                urlString += "&closestDisRange=" + closestDisRange + "";
                urlString += "&nop=" + $("#noP_Multy").val() + "";
                urlString += "&updateModfUser=" + updateModfUser;
                urlString += "&rowMultyIndex=" + rowMultyIndex;
                window.location.href = getContext() + "/findNearestMulty?Checked=" + checkedOption + urlString;
            }
        });
    });
}
function uncheckAll(ptListObject, ptDataObject) {
    checkedCounts = {};
    $(".ManholeBOQ").each(function() {
        $(this).prop("checked", false);
    });
    $(".HandholeBOQ").each(function() {
        $(this).prop("checked", false);
    });
    $(".DBBOQ").each(function() {
        $(this).prop("checked", false);
    });
    $(".NodeBOQ").each(function() {
        $(this).prop("checked", false);
    });
    $(".StrandBOQ").each(function() {
        $(this).prop("checked", false);
    });
    $(".TubeBOQ").each(function() {
        $(this).prop("checked", false);
    });
    $(".FiberBOQ").each(function() {
        $(this).prop("checked", false);
    });
    $(".dataCheckbox").each(function() {
        $(this).prop("checked", false); // Uncheck each checkbox

        // Extract the index from the checkbox name
        var index = $(this).attr("name").replace("dataCheckbox", "");

        // Call viewOnMap to remove the markers
        viewOnMap(this, ptListObject["ptList" + index], ptDataObject["ptData" + index]);
    });
}

var ptListObject, ptDataObject;
function openFindNearestMultySite(checkedOption, rowInfo, noP, closestDisRange, ptList, ptData, getRelatedPoints, Lats, Longs, circleDraw, squareDraw, locationNum, rowMultyIndex) {

    console.log(locationNum);
    $("#fiberCitySearch").modal("show");
    $('a[href="#MultyClosest"]').click();
    $("#circleRange_multy").prop("checked", true);

    $("#noP_Multy").val(noP);
    $("#getRelatedPoints_Multy").val(getRelatedPoints);
    $("#closestMultyDisRange").val(closestDisRange);
    showPointsType = getRelatedPoints;

    if (getRelatedPoints == '1') {
        $("#getRelatedPoints").prop('checked', true);
    } else {
        $("#getRelatedPoints").prop('checked', false);
    }

    var rowData = JSON.parse(rowInfo);

    var drawCircle = JSON.parse(circleDraw);
    var drawSquare = JSON.parse(squareDraw);
    var locationNum = JSON.parse(locationNum);
    var tableBody = $("#Multy_auxiliary > tbody");
    tableBody.empty(); // Clear existing table rows
    ptList = ptList.replace(/[\n\t\r]/g, '');  // Remove control characters
    ptData = ptData.replace(/[\n\t\r]/g, '');
    ptListObject = JSON.parse(ptList);  // Assuming ptList is passed as JSON string
    ptDataObject = JSON.parse(ptData);  // Assuming ptData is passed as JSON string

    const uncheckButton = document.getElementById('uncheckAll');

    // Add a click event listener
    uncheckButton.addEventListener('click', function() {
        uncheckAll(ptListObject, ptDataObject);
    });

    // Initialize indexSite counter


    var indexSite = 0;

    for (var key in rowData) {
        if (rowData.hasOwnProperty(key)) {
            var row = rowData[key];
            let seq = [];
            seq = row[2].split(',');
            var circleChecked = Number(drawCircle[indexSite]) === 1 ? "checked" : ""; // Check if the first index of drawCircle is 1
            var squareChecked = Number(drawSquare[indexSite]) === 1 ? "checked" : ""; // Check if the first index of drawSquare is 1

            var varx = ptListObject["ptList" + locationNum[indexSite]];
            var varxz = ptDataObject["ptData" + locationNum[indexSite]];

            // Initialize total count for both objects
            let totalCount = 0;

            // List of keys to count for varx
            let keysToCountVarx = ['Distribution_Board', 'Handhole', 'Manhole', 'NodeList', 'fiber'];

            // List of keys to count for varxz
            let keysToCountVarxz = ['fiber_Strands', 'fiber_Tubes'];

            // Function to count the array lengths of specified keys
            function countProperties(obj, keys) {
                let count = 0;
                keys.forEach(key => {
                    if (obj[key] && Array.isArray(obj[key])) {
                        count += obj[key].length;
                    }
                });
                return count;
            }

            // Count elements in varx and varxz
            totalCount += countProperties(varx, keysToCountVarx);
            totalCount += countProperties(varxz, keysToCountVarxz);

            console.log("Total count:", totalCount);


            // Assuming you already calculated the totalCount

            var markup = "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px;' name='record'></td>"
                + "<td class='headcol' name='Seq'><input name='Seq_Multy' id='Seq_Multy" + indexSite + "' class='form-control text-input' type='text' style='max-width:60px;position:relative;' value='" + seq[indexSite] + "'></td>"
                + "<td><input type='checkbox' class='dataCheckbox' onchange='viewOnMap(this, ptListObject[\"ptList" + locationNum[indexSite] + "\"], ptDataObject[\"ptData" + locationNum[indexSite] + "\"] )' name='dataCheckbox" + locationNum[indexSite] + "' style='position:relative;top:10px; width:100px;'></td>"
                + "<td name='auxRefSite'><a href='#' data-total-count='" + totalCount + "'class='auxRefSiteLink' data-index='" + indexSite + "' style='width:350px;'><p style='height:10px;margin-left:20px;color:#00757C;margin-top:10px;width:150px;'>View Result</p></a></td>"
                + "<td name='siteId_Multy'><input name='siteId_Multy" + indexSite + "' id='siteId_Multy" + indexSite + "' class='form-control' type='text' style='width:330px;position:relative;' value='" + row[1] + "'></td>"
                + "<td name='siteLng_Multy'><input name='siteLng_Multy" + indexSite + "' id='siteLng_Multy" + indexSite + "' class='form-control' type='text' style='width:220px;position:relative;' value='" + row[0] + "'></td>"
                + "<td name='siteLat_Multy'><input name='siteLat_Multy" + indexSite + "' id='siteLat_Multy" + indexSite + "' class='form-control' type='text' style='width:220px;position:relative;' value='" + row[3] + "'></td>"
                + "<td name='circleRange'><input class='circleRange' type='checkbox' name='circleRange" + indexSite + "' class='form-check-input' style='position:relative;left:20px;top:10px;' data-lat='" + row[3] + "' data-lng='" + row[0] + "' " + circleChecked + "></td>"
                + "<td name='squareRange'><input type='checkbox' class='squareRange' name='squareRange" + indexSite + "' class='form-check-input' style='position:relative;left:20px;top:10px;' data-lat='" + row[3] + "' data-lng='" + row[0] + "' " + squareChecked + "></td>"
                + "<td name='location_number'><input type='text' name='location_number' class='form-control' value='" + locationNum[indexSite] + "'  readonly style='width:100px;'></td></tr>";// Add location number input




            // Create the markup for each row

            // Add the markup to your table or desired element (example)

            tableBody.append(markup);


            // Correctly bind the change event for dataCheckbox checkboxes



            // Ensure that autocomplete functionality is maintained
            autoCompleteMultiPoint("siteId_Multy", "siteLng_Multy", "siteLat_Multy", indexSite, "auxPtAutocomplete");


            // draw circle and square depending on the checkboxes 
            var lat = row[3];
            var lng = row[0];
            const myLatLng = {
                lat: parseFloat(lat),
                lng: parseFloat(lng)
            };
            new google.maps.Marker({
                position: myLatLng,
                map,
                title: ` ${locationNum[indexSite]}`, // Set title to include location number
            });
            // Check if the circle checkbox is checked
            var isCircleChecked = $("input[name='circleRange" + indexSite + "']").is(':checked');
            var circleRadius = closestDisRange * 1.609344 * 1000; // Convert miles to meters

            // Calculate the bounds of the circle even if not displayed
            var bounds;
            if (isCircleChecked) {
                var circle = new google.maps.Circle({
                    center: myLatLng,
                    radius: circleRadius
                });

                // Get the bounds of the circle
                bounds = circle.getBounds();

                // If the circle is checked, display it
                circle.setOptions({
                    strokeColor: "blue",
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: "blue",
                    fillOpacity: 0.1,
                    map,
                    clickable: false
                });
                displayZoneMap(circle);
                map.setCenter(myLatLng);
                map.fitBounds(bounds);
            } else {
                // Manually calculate the circle bounds without showing it on the map
                var circle = new google.maps.Circle({
                    center: myLatLng,
                    radius: circleRadius
                });
                bounds = circle.getBounds(); // Get the bounds of the "invisible" circle
            }

            // Check if the square checkbox is checked
            var isSquareChecked = $("input[name='squareRange" + indexSite + "']").is(':checked');
            if (isSquareChecked && bounds) {
                // Draw boundary lines using the circle's bounds
                var ne = bounds.getNorthEast(); // LatLng of the rectangle's northeast corner
                var sw = bounds.getSouthWest(); // LatLng of the rectangle's southwest corner

                var startlangPath = [new google.maps.LatLng(sw.lat(), sw.lng()), new google.maps.LatLng(ne.lat(), sw.lng())];
                drawLine("#FF0000", startlangPath);

                var startlatgPath = [new google.maps.LatLng(sw.lat(), sw.lng()), new google.maps.LatLng(sw.lat(), ne.lng())];
                drawLine("#FF0000", startlatgPath);

                var endlangPath = [new google.maps.LatLng(ne.lat(), sw.lng()), new google.maps.LatLng(ne.lat(), ne.lng())];
                drawLine("#006400", endlangPath);

                var endlatgPath = [new google.maps.LatLng(ne.lat(), ne.lng()), new google.maps.LatLng(sw.lat(), ne.lng())];
                drawLine("#006400", endlatgPath);
            }

        }
        indexSite++;
    }
    // Click handler for the 'View Result' links
    $(document).on('click', '.auxRefSiteLink', function(e) {
        var indexSite = $(this).data('index');
        console.log("Clicked row index: " + indexSite);

        // Show modal and clear previous content
        $("#siteModalAuxiliary").modal('show');


        // Get the ptList and ptData for the clicked row
        ptList = ptList.replace(/[\n\t\r]/g, '');  // Remove control characters
        ptData = ptData.replace(/[\n\t\r]/g, '');

        var ptListObject = JSON.parse(ptList);  // Assuming ptList is passed as JSON string
        var ptDataObject = JSON.parse(ptData);  // Assuming ptData is passed as JSON string

        var $row = $(this).closest('tr'); // Get the closest row
        var locationNumber = $row.find('input[name="location_number"]').val(); // Get the location_number from the row

        // Construct the keys using the location number
        var ptListKey = "ptList" + locationNumber;   // Example: ptListlocation_1
        var ptDataKey = "ptData" + locationNumber;   // Example: ptDatalocation_1
        if (ptListObject.hasOwnProperty(ptListKey)) {

        } else {
            console.log(`${ptListKey} not found in ptListObject.`);
        }
        // Arrays to store combined data for the clicked row only
        var finalArrayFibers = [];
        var finalArrayTubes = [];
        var finalArrayStrands = [];
        var finalArrayManholes = [];
        var finalArrayHandholes = [];
        var finalArrayDB = [];
        var finalArrayNodes = [];

        // Process ptList for the clicked row (ptListKey)
        if (ptListObject.hasOwnProperty(ptListKey)) {
            if (ptListObject[ptListKey].hasOwnProperty("Manhole")) {
                var arrayManhole = ptListObject[ptListKey].Manhole;
                finalArrayManholes = finalArrayManholes.concat(arrayManhole);
            }

            if (ptListObject[ptListKey].hasOwnProperty("Handhole")) {
                var arrayHandhole = ptListObject[ptListKey].Handhole;
                finalArrayHandholes = finalArrayHandholes.concat(arrayHandhole);
            }

            if (ptListObject[ptListKey].hasOwnProperty("Distribution_Board")) {
                var arrayDB = ptListObject[ptListKey].Distribution_Board;
                finalArrayDB = finalArrayDB.concat(arrayDB);
            }

            if (ptListObject[ptListKey].hasOwnProperty("fiber")) {
                var arrayFibers = ptListObject[ptListKey].fiber;
                finalArrayFibers = finalArrayFibers.concat(arrayFibers);
            }

            if (ptListObject[ptListKey].hasOwnProperty("NodeList")) {
                var arrayNodes = ptListObject[ptListKey].NodeList;
                finalArrayNodes = finalArrayNodes.concat(arrayNodes);
            }
        }

        // Process ptData for the clicked row (ptDataKey)
        if (ptDataObject.hasOwnProperty(ptDataKey)) {
            if (ptDataObject[ptDataKey].hasOwnProperty("fiber_Tubes")) {
                var arrayTubes = ptDataObject[ptDataKey].fiber_Tubes;
                finalArrayTubes = finalArrayTubes.concat(arrayTubes);
            }

            if (ptDataObject[ptDataKey].hasOwnProperty("fiber_Strands")) {
                var arrayStrands = ptDataObject[ptDataKey].fiber_Strands;
                finalArrayStrands = finalArrayStrands.concat(arrayStrands);
            }
        }
        var $row = $(this).closest('tr'); // Get the closest row
        var locationNumber = $row.find('input[name="location_number"]').val(); // Get the location_number from the row

        console.log("Location Number: " + locationNumber);

        // Retrieve `data-total-count` from the clicked row's `<a>` element
        var totalCount = $(this).attr("data-total-count");
        console.log("Total Count for Location " + locationNumber + ": " + totalCount);


        // Append the data for the clicked row only
        appendNearestFiberPathsTableMulty(finalArrayFibers, finalArrayTubes, finalArrayStrands, totalCount, locationNumber);
        appendNearestManholesTableMulty(finalArrayManholes, totalCount, locationNumber);
        appendNearestHandholesTableMulty(finalArrayHandholes, totalCount, locationNumber);
        appendNearestDBoardTableMulty(finalArrayDB, totalCount, locationNumber);
        appendNearestNodeTableMulty(finalArrayNodes, totalCount, locationNumber);
        // Update the counts for this specific row
        $("#totalManhole_multy").val(finalArrayManholes.length);
        $("#totalHandhole_multy").val(finalArrayHandholes.length);
        $("#totalDB_Multy").val(finalArrayDB.length);
        updateSelectAllCheckbox('.ManholeBOQ', 'selectAllManhole_multy');
        updateSelectAllCheckbox('.HandholeBOQ', 'selectAllHandhole_multy');
        updateSelectAllCheckbox('.DBBOQ', 'selectAllDB_multy');
        updateSelectAllCheckbox('.NodeBOQ', 'selectAllNode_multy');
        updateSelectAllCheckbox('.StrandBOQ', 'selectAllStrand_multy');
        updateSelectAllCheckbox('.TubeBOQ', 'selectAllTube_multy');
        updateSelectAllCheckbox('.FiberBOQ', 'selectAllFiber_multy');

    });

    // Function to clear modal content
    function clearModalContent() {
        $("#searchManhTBody_multy").empty();
        $("#searchHanhTBody_multy").empty();
        $("#searchDBoardTBody_multy").empty();
        $("#nearStrandId_multy").empty();
        $("#nearTubeId_multy").empty();
        $("#nearFiberId_multy").empty();
        $("#searchNodeTBody_multy").empty();
    }

    // Clear the modal when closed
    $("#siteModalAuxiliary").on('hidden.bs.modal', function() {
        clearModalContent();
    });
    $('#siteModalAuxiliary').on('show.bs.modal', function() {
        $(this).removeAttr('aria-hidden'); // Remove aria-hidden
    });
}
//  hold circles and squares for each location 
const circlesMap = {};
const squaresMap = {};

// Function to handle checkbox changes
function onCheckboxChange(indexSite, circleRadius, lat, lng) {
    const myLatLng = { lat: parseFloat(lat), lng: parseFloat(lng) };

    // Handle the circle checkbox
    const isCircleChecked = $(`input[name='circleRange${indexSite}']`).is(':checked');

    // Create the circle if it doesn't exist
    if (!circlesMap[indexSite]) {
        circlesMap[indexSite] = new google.maps.Circle({
            center: myLatLng,
            radius: circleRadius,
            strokeColor: "blue",
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: "blue",
            fillOpacity: 0.1,
            map: null, // Initially not displayed on the map
            clickable: false
        });
    }

    // Show or hide the circle based on checkbox state
    if (isCircleChecked) {
        circlesMap[indexSite].setMap(map); // Show the circle
    } else {
        circlesMap[indexSite].setMap(null); // Hide the circle
    }

    // Handle the square checkbox independently
    const isSquareChecked = $(`input[name='squareRange${indexSite}']`).is(':checked');

    if (isSquareChecked) {
        // Use the circle to determine the bounds for the square
        const bounds = circlesMap[indexSite].getBounds();
        const ne = bounds.getNorthEast();
        const sw = bounds.getSouthWest();

        // Define the paths for the square lines
        const startLangPath = [
            new google.maps.LatLng(sw.lat(), sw.lng()),
            new google.maps.LatLng(ne.lat(), sw.lng())
        ];
        const startLatgPath = [
            new google.maps.LatLng(sw.lat(), sw.lng()),
            new google.maps.LatLng(sw.lat(), ne.lng())
        ];
        const endLangPath = [
            new google.maps.LatLng(ne.lat(), sw.lng()),
            new google.maps.LatLng(ne.lat(), ne.lng())
        ];
        const endLatgPath = [
            new google.maps.LatLng(ne.lat(), ne.lng()),
            new google.maps.LatLng(sw.lat(), ne.lng())
        ];

        // Check if squares have already been drawn
        if (!squaresMap[indexSite]) {
            // Draw and store lines in squaresMap
            squaresMap[indexSite] = [
                drawLine("#FF0000", startLangPath),
                drawLine("#FF0000", startLatgPath),
                drawLine("#006400", endLangPath),
                drawLine("#006400", endLatgPath)
            ];
            console.log(squaresMap);
        }
    } else {
        // Remove the square lines if they exist
        if (squaresMap[indexSite]) {
            squaresMap[indexSite].forEach(line => {
                if (line) {
                    line.setMap(null); // Remove each line from the map
                }
            });
            delete squaresMap[indexSite]; // Clean up the reference
        }
    }
}

// Event listener for the checkboxes
$(document).on('change', 'input.circleRange, input.squareRange', function() {
    const indexSite = $(this).attr('name').replace(/^\D+/g, ''); // Extract index from checkbox name
    const circleRadius = $("#closestMultyDisRange").val() * 1.609344 * 1000; // Convert miles to meters
    const lat = $(`#siteLat_Multy${indexSite}`).val();
    const lng = $(`#siteLng_Multy${indexSite}`).val();

    // Call onCheckboxChange with required parameters
    onCheckboxChange(indexSite, circleRadius, lat, lng);
});


function areAllChecked(selector) {
    var allChecked = true;

    // Check if there are any elements matching the selector
    if ($(selector).length === 0) {
        return false; // If no elements are found, return false
    }

    // Iterate over each checkbox matching the selector
    $(selector).each(function() {
        var isChecked = $(this).is(":checked");

        if (!isChecked) {
            allChecked = false;
            return false; // Exit loop early if any checkbox is unchecked
        }
    });

    return allChecked;
}

// Function to update the select-all checkbox based on the status of other checkboxes
function updateSelectAllCheckbox(selectorToCheck, selectAllCheckboxId) {
    var allChecked = areAllChecked(selectorToCheck); // Use the helper function to check

    // Update the selectAll checkbox based on whether all checkboxes are checked
    $('#' + selectAllCheckboxId).prop('checked', allChecked);
}



function appendNearestManholesTableMulty(result, totalCount, locationNumber) {
    var markupManh = "";
    var nearestManResMulty = document.getElementById("findNearestManRes_multy");
    nearestManResMulty.innerHTML = '';
    // Check if the element exists
    if (nearestManResMulty) {
        if (result.length == 0) {
            nearestManResMulty.innerHTML = '<p style="color:#ff0000;font-size: 1.4em;">There is no result</p>';
        } else {
            for (var i = 0;i < result.length;i++) {
                var manholeId = result[i][0]; // Get the Manhole ID
                if ($("#circleRange_multy").is(":checked")) {


                    // Check if the manhole ID exists in markersManhole and is displayed on the map
                    var isChecked = markersManhole[manholeId] && markersManhole[manholeId].getMap() ? "checked" : "";

                    markupManh += "<tr style='height: 40px;'>" +
                        "<td><input type='checkbox' class='ManholeBOQ' name='M" + locationNumber + "' id='BOOQ_" + manholeId + "' " + isChecked + "></td>" +
                        "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + manholeId + "' readonly></td>" +
                        "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][1] + "' readonly></td>" +
                        "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][2] + "' readonly></td>" +
                        "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][3] + "' readonly></td>" +
                        "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +
                        "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +

                        "</tr>";
                } else {
                    if (result[i][9] == null || result[i][9] == "") {

                        markupManh += "<tr style='height: 30px;'><td><input name='M" + locationNumber + "' type='checkbox' class='ManholeBOQ' id='BOOQ_" + manholeId + "' " + isChecked + "></td><td>" + result[i][0] + "</td><td name='manholeId' style='min-width:250px;'>" + result[i][1] + "</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='" + result[i][2] + "' readonly></input></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='" + result[i][3] + "' readonly></input></td><td style='width:100px;'>" + (result[i][7]) + "</td><td style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px;' name='DDistanceB' onclick='getDrivingDistance(this)'>Get Distance</button></td></tr>";
                    } else {
                        markupManh += "<tr style='height: 30px;'><td><input name='M" + locationNumber + "' type='checkbox' class='ManholeBOQ' id=BOOQ_" + result[i][0] + " ></td><td>" + result[i][0] + "</td><td name='manholeId' style='min-width:250px;'>" + result[i][1] + "</td><td style='width:150px;'>" + result[i][2] + "</td><td style='width:150px;'>" + result[i][3] + "</td><td style='width:100px;'>" + (result[i][7]) + "</td><td style='min-width:90px;'><label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>" + (result[i][8]) + "</label></td></tr>";
                    }
                }
            }

            // Append the rows to the table
            $("#searchManhTBody_multy").append(markupManh);
        }
    } else {
        console.error("Element with ID 'findNearestManRes_Multy' not found.");
    }

    // Other function logic
    if ($("#circleRange_multy").is(":checked")) {
        drivingDistance("findNearstManhole_multy");
    }
    makeAllSortable();

    $("#selectAllManhole_multy").click(function() {
        var manholeIds = [];
        var manholeId = [];
        var isChecked = $(this).is(":checked");
        $('.ManholeBOQ').prop('checked', isChecked);

        if (isChecked) {
            console.log(isChecked);

            $('input[name="M' + locationNumber + '"]:checked').each(function() {

                var id = $(this).attr('id');
                console.log(locationNumber);
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice(5));
                console.log("Checkbox ID:", id);
                if (id && id.startsWith("BOOQ_")) {
                    manholeIds.push(id.slice(5)); // Adjust slicing as needed
                }
            });


            // Clear previous markers
            markerClusterManhole.clearMarkers();

            // Check corresponding manhole checkboxes in the tree and add markers
            manholeIds.forEach(function(manholeId) {
                $("#" + manholeId).children('input:checkbox').prop('checked', true); // Check the checkbox in the tree

                // Add marker to map
                // Use just manholeId here since it doesn't need the 'Manhole_f_' prefix
                if (markersManhole[manholeId]) {
                    markersManhole[manholeId].setMap(map);
                    markerClusterManhole.addMarker(markersManhole[manholeId]);
                } else {
                    console.warn("No marker found for ID:", manholeId); // Update this to use manholeId only
                }
            });
        } else {
            console.log(isChecked);
            // Uncheck the corresponding manhole checkboxes in the tree
            $('input[name="M' + locationNumber + '"]').each(function() {

                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice(5));

                if (id && id.startsWith("BOOQ_")) {
                    manholeId.push(id.slice(5));
                    $("#network_tree").find("input[type='checkbox'][id='Manhole_f_" + manholeId + "']").prop('checked', false);
                }
            });

            // Clear markers if any manholes are unchecked
            markerClusterManhole.clearMarkers();

            // Check corresponding manhole checkboxes in the tree and add markers
            manholeId.forEach(function(manholeId) {
                $("#" + manholeId).children('input:checkbox').prop('checked', false); // Check the checkbox in the tree

                // Add marker to map
                // Use just manholeId here since it doesn't need the 'Manhole_f_' prefix
                if (markersManhole[manholeId]) {
                    markersManhole[manholeId].setMap(null);
                    markerClusterManhole.removeMarker(markersManhole[manholeId]);
                } else {
                    console.warn("No marker found for ID:", manholeId); // Update this to use manholeId only
                }
            });
        }

        if ($(".Manhole").length === $(".Manhole:checked").length) {

            // Check the AllManholes checkbox if all are checked
            $(".AllManholes").prop('checked', true);
        }

        else {
            $(".AllManholes").prop('checked', false);

        }
    });

    // Single row checkbox logic remains the same
    $('.ManholeBOQ').click(function() {
        var ManId = $(this).attr('id').split("BOOQ_")[1];
        updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), ManId);

        if ($(this).is(':checked')) {
            $("#" + ManId).children('input:checkbox').prop('checked', true);
            markersManhole[ManId].setMap(map);
            markerClusterManhole.addMarker(markersManhole[ManId]);
        } else {
            $("#" + ManId).children('input:checkbox').prop('checked', false);
            markersManhole[ManId].setMap(null);
            markerClusterManhole.removeMarker(markersManhole[ManId]);
        }
    });
}

// Object to store checked IDs by location number

// This function will handle the update logic for checking and unchecking checkboxes
function updateCheckedCount(locationNumber, totalCount, isChecked, manholeId) {
    // Initialize the array for this location if it doesn't exist
    if (!(locationNumber in checkedCounts)) {
        checkedCounts[locationNumber] = [];
    }

    // If the checkbox is checked and the ID is not already in the array, add it
    if (isChecked && !checkedCounts[locationNumber].includes(manholeId)) {
        checkedCounts[locationNumber].push(manholeId); // Add manholeId to the array
        //  console.log(`Added ID: ${manholeId} for location ${locationNumber}`);
    }
    // If unchecked, remove the ID from the array
    else if (!isChecked) {
        checkedCounts[locationNumber] = checkedCounts[locationNumber].filter(item => item !== manholeId);
        //   console.log(`Removed ID: ${manholeId} for location ${locationNumber}`);
    }

    // Track the number of checked items for this location
    const checkedItemsCount = checkedCounts[locationNumber].length;

    // Log the number of checked items / total count


    // After updating the checked IDs, compare the array length with totalCount
    if (checkedItemsCount === Number(totalCount)) {
        console.log(`Location ${locationNumber}: All checkboxes are checked`);

        // Trigger any action, for example, check a "View on Map" checkbox
        var checkbox = $(`input[name='dataCheckbox${locationNumber}']`);
        if (checkbox.length > 0) {
            checkbox.prop('checked', true);
        } else {
            console.warn(`Checkbox with name='dataCheckbox${locationNumber}' not found!`);
        }
    }
    else {
        // Trigger any action, for example, check a "View on Map" checkbox
        var checkbox = $(`input[name='dataCheckbox${locationNumber}']`);
        if (checkbox.length > 0) {
            checkbox.prop('checked', false);
        } else {
            console.warn(`Checkbox with name='dataCheckbox${locationNumber}' not found!`);
        }
    }
}



function appendNearestHandholesTableMulty(result, totalCount, locationNumber) {

    var markupHandh = "";
    document.getElementById("findNearestHandRes_Multy").innerHTML = '';

    if (result.length == 0) {
        document.getElementById("findNearestHandRes_Multy").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
    }
    else {
        for (var i = 0;i < result.length;i++) {
            var handholeId = result[i][0];
            if ($("#circleRange_multy").is(":checked")) {
                var handholeId = result[i][0]; // Get the Manhole ID
                var isChecked = markersHandhole[handholeId] && markersHandhole[handholeId].getMap() ? "checked" : "";
                markupHandh += "<tr style='height: 40px;'>" +
                    "<td><input type='checkbox' class='HandholeBOQ' name='H" + locationNumber + "' id='BOOQ_" + handholeId + "' " + isChecked + "></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + handholeId + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][1] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][2] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][3] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +

                    "</tr>";
                //	markupHandh += "<tr style='height: 30px;'>" +"<td><input type='checkbox' name='H"+locationNumber+"' class='HandholeBOQ' id='BOOQ_" + handholeId + "' " + isChecked + "></td>"+result[i][0]+"</td><td name ='handholeId' style='min-width:250px;'>"+result[i][1]+"</td><td name ='handholeName' style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"
            }
            else {
                if (result[i][8] == null || result[i][8] == "") {
                    markupHandh += "<tr style='height: 30px;'>" + "<td><input type='checkbox' name='H" + locationNumber + "' class='HandholeBOQ' id='BOOQ_" + handholeId + "' " + isChecked + "></td><td  >" + result[i][0] + "</td><td style='min-width:250px;'>" + result[i][1] + "</td><td name='LONGG' style='width:150px;'><td name ='handholeName' style='min-width:250px;'>" + result[i][1] + "</td><input name='LONGG' style='border: none;' value='" + result[i][2] + "' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='" + result[i][3] + "' readonly></input ></td><td style='width:100px;'>" + (result[i][7]) + "</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"
                } else {
                    markupHandh += "<tr style='height: 30px;'>" + "<td><input type='checkbox' name='H" + locationNumber + "' class='HandholeBOQ' id='BOOQ_" + handholeId + "' " + isChecked + "><td  >" + result[i][0] + "</td><td style='min-width:250px;'>" + result[i][1] + "</td><td style='width:150px;'>" + result[i][2] + "</td><td name ='handholeName' style='min-width:250px;'>" + result[i][1] + "</td><td style='width:150px;'>" + result[i][3] + "</td><td style='width:100px;'>" + (result[i][7]) + "</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>" + (result[i][8]) + "</label></td></tr>"
                }
            }
        }
    }
    $("#searchHanhTBody_multy").append("");
    $("#searchHanhTBody_multy").append(markupHandh);
    if ($("#circleRange_multy").is(":checked")) {
        drivingDistance("findNearstHandhole_multy");
    }
    makeAllSortable();

    $("#selectAllHandhole_multy").click(function() {
        var handholeIds = [];
        var isChecked = $(this).is(":checked");
        $('.HandholeBOQ').prop('checked', isChecked);

        if (isChecked) {
            console.log("Collecting IDs of checked checkboxes...");

            $('input[name="H' + locationNumber + '"]:checked').each(function() {

                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice(5));

                console.log("Checkbox ID:", id);
                if (id && id.startsWith("BOOQ_")) {
                    handholeIds.push(id.slice(5)); // Adjust slicing as needed
                }
            });


            // Clear previous markers
            markerClusterHandhole.clearMarkers();

            // Check corresponding handhole checkboxes in the tree and add markers
            handholeIds.forEach(function(handholeId) {
                $("#" + handholeId).children('input:checkbox').prop('checked', true); // Check the checkbox in the tree

                // Add marker to map
                if (markersHandhole[handholeId]) {
                    markersHandhole[handholeId].setMap(map);
                    markerClusterHandhole.addMarker(markersHandhole[handholeId]);
                } else {
                    console.warn("No marker found for ID:", handholeId);
                }
            });
        } else {
            // Uncheck the corresponding handhole checkboxes in the tree
            $('input[name="H' + locationNumber + '"]').each(function() {

                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice(5));

                if (id && id.startsWith("BOOQ_")) {
                    handholeIds.push(id.slice(5));
                }
            });

            // Clear markers if any handholes are unchecked
            markerClusterHandhole.clearMarkers();
            // Check corresponding handhole checkboxes in the tree and add markers
            handholeIds.forEach(function(handholeId) {
                $("#" + handholeId).children('input:checkbox').prop('checked', false); // Check the checkbox in the tree

                // Add marker to map
                if (markersHandhole[handholeId]) {
                    markersHandhole[handholeId].setMap(null);
                    markerClusterHandhole.removeMarker(markersHandhole[handholeId]);
                } else {
                    console.warn("No marker found for ID:", handholeId);
                }
            });
        }

        // Check if all Handhole checkboxes are checked
        if ($(".Handhole").length === $(".Handhole:checked").length) {
            // Check the AllHandholes checkbox if all are checked
            $(".AllHandholes").prop('checked', true);
        } else {
            // Uncheck the AllHandholes checkbox
            $(".AllHandholes").prop('checked', false);
        }
    });


    // checking single row checbox from boq
    $('.HandholeBOQ').click(function() {

        var HandId = $(this).attr('id').split("BOOQ_")[1];
        updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), HandId);

        if ($(this).is(':checked')) {
            $("#" + HandId).children('input:checkbox').prop('checked', true);
            markersHandhole[HandId].setMap(map);
            markerClusterHandhole.addMarker(markersHandhole[HandId]);
        }
        else {
            $("#" + HandId).children('input:checkbox').prop('checked', false);
            markersHandhole[HandId].setMap(null);
            markerClusterHandhole.removeMarker(markersHandhole[HandId]);
        }
    });

}
function appendNearestNodeTableMulty(result, totalCount, locationNumber) {
    var markupNode = "";
    document.getElementById("findNearestNodeRes_Multy").innerHTML = '';

    if (result.length == 0) {
        document.getElementById("findNearestNodeRes_Multy").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
    }
    else {
        for (var i = 0;i < result.length;i++) {
            var nodeId = result[i][0]; // Get the Manhole ID
            if ($("#circleRange_multy").is(":checked")) {

                var isChecked = markersNodeActive[nodeId] && markersNodeActive[nodeId].getMap() ? "checked" : "";
                markupNode += "<tr style='height: 40px;'>" +
                    "<td><input type='checkbox' class='NodeBOQ' name='N" + locationNumber + "' id='BOQ_" + result[i][0] + "' " + isChecked + "></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[0] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][1] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][5] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][6] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +
                    "</tr>";
            }
            else {
                if (result[i][10] == null || result[i][10] == "") {
                    markupNode += "<tr style='height: 30px;'><td><input type='checkbox' name='N" + locationNumber + "' class='NodeBOQ' id=BOQ_" + result[i][0] + "' " + isChecked + "></td><td style='min-width:150px;'>" + result[i][0] + "</td><td style='min-width:150px;'>" + result[i][1] + "</td><td  name='LONGG' style='min-width:150px;'><input name='LONGG' style='border: none;' value='" + result[i][5] + "' readonly></input ></td><td style='min-width:150px;'  name='LATT'><input name='LATT' style='border: none;' value='" + result[i][6] + "' readonly></input ></td><td style='min-width:50px;'>" + result[i][9] + "</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"
                }
            }
        }
    }
    $("#searchNodeTBody_multy").append("");
    $("#searchNodeTBody_multy").append(markupNode);


    if ($("#circleRange_multy").is(":checked")) {
        drivingDistance("findNearstNode_multy");
    }

    makeAllSortable();

    $("#selectAllNode_multy").click(function() {
        var nodeIds = [];
        var isChecked = $(this).is(":checked");
        $('.NodeBOQ').prop('checked', isChecked);

        if (isChecked) {
            $('input[name="N' + locationNumber + '"]:checked').each(function() {
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), $(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim());

                nodeIds.push($(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim());
            });

            // Clear all markers before adding new ones
            markerClusterMSANNodes.clearMarkers();
            markerClusterDWDMNodes.clearMarkers();
            markerClusterSDHNodes.clearMarkers();
            markerClusterGPONNodes.clearMarkers();
            markerClusterEntSwitchNodes.clearMarkers();

            nodeIds.forEach(function(nodeId) {
                $("#" + nodeId).children('input:checkbox').prop('checked', true);

                if (markersNodeActive[nodeId]) {
                    markersNodeActive[nodeId].setMap(map);
                    const type = window[nodeId][8];

                    if (type === "MSAN") markerClusterMSANNodes.addMarker(markersNodeActive[nodeId]);
                    else if (type === "DWDM") markerClusterDWDMNodes.addMarker(markersNodeActive[nodeId]);
                    else if (type === "SDH") markerClusterSDHNodes.addMarker(markersNodeActive[nodeId]);
                    else if (type === "GPON") markerClusterGPONNodes.addMarker(markersNodeActive[nodeId]);
                    else if (type === "SWITCH") markerClusterEntSwitchNodes.addMarker(markersNodeActive[nodeId]);
                } else {
                    console.warn("No marker found for ID:", nodeId);
                }
            });
        } else {
            $('input[name="N' + locationNumber + '"]').each(function() {

                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), $(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim());
                if (id && id.startsWith("BOQ_")) {
                    nodeIds.push(id.slice(4).replace(/['"]+/g, '').trim());
                }
            });

            nodeIds.forEach(function(nodeId) {
                $("#" + nodeId).children('input:checkbox').prop('checked', false);
            });

            // Clear markers if unchecked
            markerClusterMSANNodes.clearMarkers();
            markerClusterDWDMNodes.clearMarkers();
            markerClusterSDHNodes.clearMarkers();
            markerClusterGPONNodes.clearMarkers();
            markerClusterEntSwitchNodes.clearMarkers();
            nodeIds.forEach(function(nodeId) {
                $("#" + nodeId).children('input:checkbox').prop('checked', false);

                if (markersNodeActive[nodeId]) {
                    markersNodeActive[nodeId].setMap(map);
                    const type = window[nodeId][8];

                    if (type === "MSAN") markerClusterMSANNodes.removeMarker(markersNodeActive[nodeId]);
                    else if (type === "DWDM") markerClusterDWDMNodes.removeMarker(markersNodeActive[nodeId]);
                    else if (type === "SDH") markerClusterSDHNodes.removeMarker(markersNodeActive[nodeId]);
                    else if (type === "GPON") markerClusterGPONNodes.removeMarker(markersNodeActive[nodeId]);
                    else if (type === "SWITCH") markerClusterEntSwitchNodes.removeMarker(markersNodeActive[nodeId]);
                } else {
                    console.warn("No marker found for ID:", nodeId);
                }
            });
        }
    });

    $('.NodeBOQ').click(function() {


        var nodeId = $(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim();
        updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), nodeId);
        if ($(this).is(':checked')) {
            $("#" + nodeId).children('input:checkbox').prop('checked', true);
            markersNodeActive[nodeId].setMap(map);
            const type = window[nodeId][8];

            if (type === "MSAN") markerClusterMSANNodes.addMarker(markersNodeActive[nodeId]);
            else if (type === "DWDM") markerClusterDWDMNodes.addMarker(markersNodeActive[nodeId]);
            else if (type === "SDH") markerClusterSDHNodes.addMarker(markersNodeActive[nodeId]);
            else if (type === "GPON") markerClusterGPONNodes.addMarker(markersNodeActive[nodeId]);
            else if (type === "SWITCH") markerClusterEntSwitchNodes.addMarker(markersNodeActive[nodeId]);
        } else {
            $("#" + nodeId).children('input:checkbox').prop('checked', false);
            markersNodeActive[nodeId].setMap(null);
            const type = window[nodeId][8];

            if (type === "MSAN") markerClusterMSANNodes.removeMarker(markersNodeActive[nodeId]);
            else if (type === "DWDM") markerClusterDWDMNodes.removeMarker(markersNodeActive[nodeId]);
            else if (type === "SDH") markerClusterSDHNodes.removeMarker(markersNodeActive[nodeId]);
            else if (type === "GPON") markerClusterGPONNodes.removeMarker(markersNodeActive[nodeId]);
            else if (type === "SWITCH") markerClusterEntSwitchNodes.removeMarker(markersNodeActive[nodeId]);
        }
    });
}

function appendNearestDBoardTableMulty(result, totalCount, locationNumber) {
    var markupDBoard = "";
    document.getElementById("findNearestDbRes_Multy").innerHTML = '';

    if (result.length == 0) {
        document.getElementById("findNearestDbRes_Multy").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
    }
    else {
        for (var i = 0;i < result.length;i++) {
            var dbId = result[i][0]; // Get the Manhole ID
            if ($("#circleRange_multy").is(":checked")) {
                var isChecked = markersDistBoard[dbId] && markersDistBoard[dbId].getMap() ? "checked" : "";
                markupDBoard += "<tr style='height: 40px;'>" +
                    "<td><input type='checkbox' class='DBBOQ' name='D" + locationNumber + "' id='BOQ_" + result[i][0] + "' " + isChecked + "></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[0] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][3] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][1] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + result[i][2] + "' readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +
                    "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value= readonly></td>" +

                    "</tr>";


            }
            else {
                if (result[i][10] == null || result[i][10] == "") {




                    markupDBoard += "<tr style='height: 30px;'><td><input type='checkbox' name='D" + locationNumber + "' class='DBBOQ' id=BOQ_" + result[i][0] + "' " + isChecked + "></td><td style='min-width:150px;'>" + result[i][0] + "</td><td style='min-width:150px;'>" + result[i][3] + "</td><td  name='LONGG' style='min-width:150px;'><input name='LONGG' style='border: none;' value='" + result[i][1] + "' readonly></input ></td><td style='min-width:150px;'  name='LATT'><input name='LATT' style='border: none;' value='" + result[i][2] + "' readonly></input ></td><td style='min-width:50px;'>" + result[i][9] + "</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"
                } else {
                    markupDBoard += "<tr style='height: 30px;'><td><input type='checkbox' name='D" + locationNumber + "' class='DBBOQ' id=BOQ_" + result[i][0] + "' " + isChecked + "></td><td style='min-width:150px;'>" + result[i][0] + "</td><td style='min-width:150px;'>" + result[i][3] + "</td><td style='min-width:150px;'>" + result[i][1] + "</td><td style='min-width:150px;'>" + result[i][2] + "</td><td style='min-width:50px;'>" + result[i][9] + "</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>" + (result[i][10]) + "</label></td></tr>"
                }
            }
        }
    }
    $("#searchDBoardTBody_multy").append("");
    $("#searchDBoardTBody_multy").append(markupDBoard);
    if ($("#circleRange_multy").is(":checked")) {
        drivingDistance("findNearstDB_multy");
    }
    makeAllSortable();

    $("#selectAllDB_multy").click(function() {

        var distBoardIds = [];
        var isChecked = $(this).is(":checked");
        $('.DBBOQ').prop('checked', isChecked); // Assuming your checkboxes have a class DistBoardBOQ

        if (isChecked) {

            $('input[name="D' + locationNumber + '"]:checked').each(function() {
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), $(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim());

                distBoardIds.push($(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim()); // Adjust slicing as needed

            });


            // Clear previous markers
            markerClusterBackboneDistBoard.clearMarkers();
            markerClusterMetroDistBoard.clearMarkers();
            markerClusterAccessDistBoard.clearMarkers();

            // Check corresponding DistBoard checkboxes in the tree and add markers
            distBoardIds.forEach(function(distBoardId) {
                $("#" + distBoardId).children('input:checkbox').prop('checked', true); // Check the checkbox in the tree

                // Add marker to map based on type
                if (markersDistBoard[distBoardId]) {
                    markersDistBoard[distBoardId].setMap(map);
                    if (window[distBoardId][8] === "backbone") {
                        markerClusterBackboneDistBoard.addMarker(markersDistBoard[distBoardId]);
                    } else if (window[distBoardId][8] === "metro") {
                        markerClusterMetroDistBoard.addMarker(markersDistBoard[distBoardId]);
                    } else if (window[distBoardId][8] === "access") {
                        markerClusterAccessDistBoard.addMarker(markersDistBoard[distBoardId]);
                    }
                } else {
                    console.warn("No marker found for ID:", distBoardId);
                }
            });
        } else {

            $('input[name="D' + locationNumber + '"]').each(function() {
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), $(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim());
                distBoardIds.push($(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim());

            });

            distBoardIds.forEach(function(distBoardId) {
                $("#" + distBoardId).children('input:checkbox').prop('checked', false);
            });

            // Clear markers if any DistBoards are unchecked
            markerClusterBackboneDistBoard.clearMarkers();
            markerClusterMetroDistBoard.clearMarkers();
            markerClusterAccessDistBoard.clearMarkers();

            distBoardIds.forEach(function(distBoardId) {
                $("#" + distBoardId).children('input:checkbox').prop('checked', false); // Check the checkbox in the tree

                // Add marker to map based on type
                if (markersDistBoard[distBoardId]) {
                    markersDistBoard[distBoardId].setMap(null);
                    if (window[distBoardId][8] === "backbone") {
                        markerClusterBackboneDistBoard.removeMarker(markersDistBoard[distBoardId]);
                    } else if (window[distBoardId][8] === "metro") {
                        markerClusterMetroDistBoard.removeMarker(markersDistBoard[distBoardId]);
                    } else if (window[distBoardId][8] === "access") {
                        markerClusterAccessDistBoard.removeMarker(markersDistBoard[distBoardId]);
                    }
                } else {
                    console.warn("No marker found for ID:", distBoardId);
                }
            });
        }
    });


    // checking single row checbox from boq
    $('.DBBOQ').click(function() {


        var DBdId = $(this).attr('id').split("BOQ_")[1].replace(/['"]+/g, '').trim();
        updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), DBdId);
        console.log(DBdId);
        if ($(this).is(':checked')) {
            $("#" + DBdId).children('input:checkbox').prop('checked', true);
            markersDistBoard[DBdId].setMap(map);
            if (window["" + DBdId][8] == "backbone") {
                markerClusterBackboneDistBoard.addMarker(markersDistBoard[DBdId]);
            }
            else if (window["" + DBdId][8] == "metro") {
                markerClusterMetroDistBoard.addMarker(markersDistBoard[DBdId]);
            }
            else if (window["" + DBdId][8] == "access") {
                markerClusterAccessDistBoard.addMarker(markersDistBoard[DBdId]);
            }
        }
        else {
            $("#" + DBdId).children('input:checkbox').prop('checked', false);
            markersDistBoard[DBdId].setMap(null);
            if (window["" + DBdId][8] == "backbone") {
                markerClusterBackboneDistBoard.removeMarker(markersDistBoard[DBdId]);
            }
            else if (window["" + DBdId][8] == "metro") {
                markerClusterMetroDistBoard.removeMarker(markersDistBoard[DBdId]);
            }
            else if (window["" + DBdId][8] == "access") {
                markerClusterAccessDistBoard.removeMarker(markersDistBoard[DBdId]);
            }
        }
    });

}
function appendNearestFiberPathsTableMulty(finalArrayFibers, finalArrayTubes, finalArrayStrands, totalCount, locationNumber) {

    // Clear previous rows in each table
    $("#nearestFiber tbody tr:not(:first)").remove();
    $("#nearestTube tbody tr:not(:first)").remove();
    $("#nearestStrand tbody tr:not(:first)").remove();
    $("#fiberMulty").html("");
    $("#tubeMulty").html("");
    $("#strandMulty").html("");
    // Append fibers
    let fiberRows = "";
    if (finalArrayFibers.length === 0) {
        $("#fiberMulty").html("<p style='color:red; font-size: 1.4em; text-align: left;'>There is no result</p>");
    } else {
        finalArrayFibers.forEach(function(fiber) {

            var isChecked = fiberArray[fiber[4]] && fiberArray[fiber[4]].getMap() ? "checked" : "";

            fiberRows += "<tr style='height: 40px;'>" +
                "<td><input type='checkbox' class='FiberBOQ' name='F" + locationNumber + "' id='BOOQ_" + fiber[4] + "' " + isChecked + "></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + fiber[4] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + fiber[13] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + fiber[6] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + fiber[9] + "' readonly></td>" +
                "</tr>";
        });
    }
    $("#nearFiberId_multy").append("");
    $("#nearFiberId_multy").append(fiberRows);
    $("#selectAllFiber_multy").click(function() {
        var fiberIds = [];
        var isChecked = $(this).is(":checked");
        $('.FiberBOQ').prop('checked', isChecked);

        if (isChecked) {

            $('input[name="F' + locationNumber + '"]:checked').each(function() {

                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice(5));

                if (id && id.startsWith("BOOQ_")) {
                    fiberIds.push(id.slice(5)); // Adjust slicing as needed
                }
            });

            // Clear previous paths
            fiberArray.forEach(fiber => fiber.setMap(null));

            // Check corresponding fiber checkboxes in the tree and add fiber paths
            fiberIds.forEach(function(fiberId) {
                $("#" + fiberId).children('input:checkbox').prop('checked', true); // Check the checkbox in the tree

                // Add fiber path to map

                const path = window["mapPoints_" + fiberId];
                buildPath(
                    fiberId,
                    path,
                    fiberArray,
                    allFiberCables,
                    "FiberPath_f_",
                    window['FiberColor_' + window[fiberId][22]],
                    0.7,
                    4.5,
                    'blue',
                    13
                );
                fiberArray[fiberId].setMap(null);
                fiberArray[fiberId].setMap(map);

            });
        } else {
            // Uncheck the corresponding fiber checkboxes in the tree
            $('input[name="F' + locationNumber + '"]').each(function() {

                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice(5));

                if (id && id.startsWith("BOOQ_")) {
                    var fiberId = id.slice(5);
                    $("#" + fiberId).children('input:checkbox').prop('checked', false); // Check the checkbox in the tree

                    $("#network_tree").find("input[type='checkbox'][id='FiberPath_f_" + fiberId + "']").prop('checked', false);
                }
            });

            // Clear all fiber paths
            fiberArray.forEach(fiber => fiber.setMap(null));
        }

        // Check/uncheck the AllFibers checkbox
        if ($(".FiberBOQ").length === $(".FiberBOQ:checked").length) {
            $(".AllFibers").prop('checked', true);
        } else {
            $(".AllFibers").prop('checked', false);
        }
    });


    $('.FiberBOQ').click(function() {

        var fiberId = $(this).attr('id').split("BOOQ_")[1];
        updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), fiberId);
        if ($(this).is(':checked')) {
            $("#" + fiberId).children('input:checkbox').prop('checked', false);
            $("#" + fiberId).children('input:checkbox').prop('checked', true);
            const path = window["mapPoints_" + fiberId];
            buildPath(
                fiberId,
                path,
                fiberArray,
                allFiberCables,
                "FiberPath_f_",
                window['FiberColor_' + window[fiberId][22]],
                0.7,
                4.5,
                'blue',
                13
            );
            fiberArray[fiberId].setMap(null);
            fiberArray[fiberId].setMap(map);
        } else {
            $("#" + fiberId).children('input:checkbox').prop('checked', false);
            fiberArray[fiberId].setMap(null);
            fiberArray[fiberId].mapLabel.setMap(null);
        }
    });

    // Append tubes
    let tubeRows = "";
    if (finalArrayTubes.length === 0) {
        $("#tubeMulty").html("<p style='color:red; font-size: 1.4em; text-align: left;'>There is no result</p>");
    } else {
        finalArrayTubes.forEach(function(tube) {

            var isChecked = tubeArray[tube[0]] && tubeArray[tube[0]].getMap() ? "checked" : "";

            tubeRows += "<tr style='height: 40px;'>" +
                "<td><input type='checkbox' class='TubeBOQ' name='T" + locationNumber + "' id='BOOQ_" + tube[0] + "' " + isChecked + "></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + tube[0] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + tube[13] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + tube[6] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + fiber[9] + "' readonly></td>" +
                "</tr>";

        });
    }
    $("#nearTubeId_multy").append("");
    $("#nearTubeId_multy").append(tubeRows);

    $("#selectAllTube_multy").click(function() {
        var tubeIds = [];
        var isChecked = $(this).is(":checked");
        $('.TubeBOQ').prop('checked', isChecked);

        if (isChecked) {
            console.log("Collecting IDs of checked checkboxes...");

            $('input[name="T' + locationNumber + '"]:checked').each(function() {


                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice(5));
                console.log("Checkbox ID:", id);
                if (id && id.startsWith("BOOQ_")) {
                    tubeIds.push(id.slice(5)); // Extract the tube ID
                }
            });

            console.log("Collected Tube IDs:", tubeIds);

            // Clear previous paths
            tubeArray.forEach(tube => tube.setMap(null));

            // Check corresponding tube checkboxes in the tree and add tube paths
            tubeIds.forEach(function(tubeId) {
                $("#" + tubeId).children('input:checkbox').prop('checked', true); // Check the checkbox in the tree

                // Add tube path to map

                buildPath(
                    tubeId,
                    window["mapPoints_" + tubeId],
                    tubeArray,
                    allTubes,
                    "Tube",
                    'green',
                    0.7,
                    3.3,
                    'green',
                    0
                );
                tubeArray[tubeId].setMap(null);
                tubeArray[tubeId].setMap(map);

            });
        } else {
            // Uncheck the corresponding tube checkboxes in the tree
            $('input[name="T' + locationNumber + '"]').each(function() {


                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice[5]);
                if (id && id.startsWith("BOOQ_")) {
                    var tubeId = id.slice(5);
                    $("#network_tree").find("input[type='checkbox'][id='Tube_" + tubeId + "']").prop('checked', false);
                }
            });

            // Clear all tube paths
            tubeArray.forEach(tube => tube.setMap(null));
        }

        // Check/uncheck the AllTubes checkbox
        if ($(".TubeBOQ").length === $(".TubeBOQ:checked").length) {
            $(".AllTubes").prop('checked', true);
        } else {
            $(".AllTubes").prop('checked', false);
        }
    });

    $('.TubeBOQ').click(function() {
        var tubeId = $(this).attr('id').split("BOOQ_")[1];
        updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), tubeId);
        if ($(this).is(':checked')) {
            $("#" + tubeId).children('input:checkbox').prop('checked', false);
            $("#" + tubeId).children('input:checkbox').prop('checked', true);
            buildPath(tubeId, window["mapPoints_" + tubeId], tubeArray, allTubes, "Tube", 'green', 0.7, 3.3, 'green', 0);
            tubeArray[tubeId].setMap(null);
            tubeArray[tubeId].setMap(map);

        } else {
            $("#" + tubeId).children('input:checkbox').prop('checked', false);
            tubeArray[tubeId].setMap(null);
            tubeArray[tubeId].mapLabel.setMap(null);
        }
    });


    // Append strands
    let strandRows = "";
    if (finalArrayStrands.length === 0) {
        $("#strandMulty").html("<p style='color:red; font-size: 1.4em; text-align: left;'>There is no result</p>");
    } else {

        finalArrayStrands.forEach(function(strand) {
            var isChecked = strandArray[strand[0]] && strandArray[strand[0]].getMap() ? "checked" : "";

            strandRows += "<tr style='height: 40px;'>" +
                "<td><input type='checkbox' class='StrandBOQ' name='S" + locationNumber + "' id='BOOQ_" + strand[0] + "' " + isChecked + "></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + strand[0] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + strand[13] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + strand[6] + "' readonly></td>" +
                "<td style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' value='" + strand[9] + "' readonly></td>" +

                "</tr>";
        });
    }
    $("#nearStrandId_multy").append("");
    $("#nearStrandId_multy").append(strandRows);
    $("#selectAllStrand_multy").click(function() {
        var strandIds = [];
        var isChecked = $(this).is(":checked");
        $('.StrandBOQ').prop('checked', isChecked);

        if (isChecked) {
            console.log("Collecting IDs of checked checkboxes...");

            $('input[name="S' + locationNumber + '"]:checked').each(function() {


                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice[5]);
                console.log("Checkbox ID:", id);
                if (id && id.startsWith("BOOQ_")) {
                    strandIds.push(id.slice(5)); // Extract the strand ID
                }
            });

            console.log("Collected Strand IDs:", strandIds);

            // Clear previous paths
            strandArray.forEach(strand => strand.setMap(null));

            // Check corresponding strand checkboxes in the tree and add strand paths
            strandIds.forEach(function(strandId) {
                $("#" + strandId).children('input:checkbox').prop('checked', true); // Check the checkbox in the tree

                // Add strand path to map
                buildPath(
                    strandId,
                    window["mapPoints_" + strandId],
                    strandArray,
                    allStrands,
                    "Strand",
                    'purple',
                    0.7,
                    2.8,
                    'purple',
                    0
                );
                strandArray[strandId].setMap(null);
                strandArray[strandId].setMap(map);

            });
        } else {
            // Uncheck the corresponding strand checkboxes in the tree
            $('input[name="S' + locationNumber + '"]').each(function() {


                var id = $(this).attr('id');
                updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), id.slice[5]);
                if (id && id.startsWith("BOOQ_")) {
                    var strandId = id.slice(5);
                    $("#network_tree").find("input[type='checkbox'][id='Strand_" + strandId + "']").prop('checked', false);
                }
            });

            // Clear all strand paths
            strandArray.forEach(strand => strand.setMap(null));
        }

        // Check/uncheck the AllStrands checkbox
        if ($(".StrandBOQ").length === $(".StrandBOQ:checked").length) {
            $(".AllStrands").prop('checked', true);
        } else {
            $(".AllStrands").prop('checked', false);
        }
    });


    $('.StrandBOQ').click(function() {
        var strandId = $(this).attr('id').split("BOQ_")[1];
        updateCheckedCount(locationNumber, totalCount, $(this).is(":checked"), strandId);
        console.log(strandId);
        if ($(this).is(':checked')) {
            $("#" + strandId).children('input:checkbox').prop('checked', false);
            $("#" + strandId).children('input:checkbox').prop('checked', true);
            buildPath(strandId, window["mapPoints_" + strandId], strandArray, allStrands, "Strand", 'purple', 0.7, 2.8, 'purple', 0);
            strandArray[strandId].setMap(null);
            strandArray[strandId].setMap(map);
        } else {
            $("#" + strandId).children('input:checkbox').prop('checked', false);
            strandArray[strandId].setMap(null);
            strandArray[strandId].mapLabel.setMap(null);
        }
    });
}

function autoCompleteMultiPoint(SiteID, LongID, LatID, index, checkboxclass) {

    // Track the autocomplete instance globally for each input field
    var autocompleteInstance = null;

    // Track the input field value
    var previousValue = $("#" + SiteID + index).val();

    // Create a function to recreate the input field with the same value
    function recreateInputField() {
        // Get the old input field and remove it
        var oldInput = $("#" + SiteID + index);
        var parent = oldInput.parent();  // Get the parent element to reinsert the new input
        var inputValue = oldInput.val();  // Get the value before deleting
        if (inputValue === "Enter a location") {
            inputValue = "";  // Remove the placeholder value
        }

        // Remove the old input field
        oldInput.remove();

        // Create a new input element
        var newInput = $("<input/>", {
            type: "text",
            id: SiteID + index,
            value: inputValue,
            name: SiteID + index,
            style: 'width:330px; position:relative;',
            class: oldInput.attr("class"),  // Retain the class of the old input field
            placeholder: oldInput.attr("placeholder"),  // Retain the placeholder of the old input
        });

        // Append the new input element back to the parent element
        parent.append(newInput);

        // Reapply the previous value
        newInput.val(inputValue);

        // Reinitialize autocomplete
        applyAutocomplete(newInput);
    }

    function applyAutocomplete(inputElement) {
        $(inputElement).autocomplete({
            source: function(request, response) {
                var searchValue = inputElement.val();
                var selectedType = "";

                // Determine the selected checkbox type
                $('input:checkbox[class="' + checkboxclass + '"]:checked').each(function() {
                    selectedType = $(this).attr("id").split("_")[0]; // Get the prefix (Site, Manhole, etc.)
                });

                var url, dataTarget = {};
                switch (selectedType) {
                    case "Site":
                        url = 'GetAllWarehouse';
                        dataTarget = { "WareName": searchValue, "warehouseName": searchValue, "SiteId": searchValue };
                        break;
                    case "Manhole":
                        url = 'getManholeData';
                        dataTarget = { "search": searchValue };
                        break;
                    case "Customer":
                        url = 'GetAllNetworkCustomer';
                        dataTarget = { "search": searchValue };
                        break;
                    case "Handhole":
                        url = 'getHandholeData';
                        dataTarget = { "search": searchValue };
                        break;
                    case "db":
                        url = 'getDbData';
                        dataTarget = { "search": searchValue };
                        break;
                    case "Place":
                    case "Generic":
                        var addressInput = document.getElementById(SiteID + index);
                        var autocomplete = new google.maps.places.Autocomplete(addressInput);
                        autocomplete.setTypes(['geocode']);
                        google.maps.event.addListener(autocomplete, 'place_changed', function() {
                            var place = autocomplete.getPlace();
                            if (!place.geometry) return;

                            // Set latitude and longitude
                            $("#" + LongID + index).val(place.geometry.location.lng());
                            $("#" + LatID + index).val(place.geometry.location.lat());
                        });
                        return; // Prevent further AJAX calls
                    default:
                        url = 'emptyUrl'; // Default case
                }

                // Make AJAX call if a valid URL is set
                if (url !== "emptyUrl") {
                    $.ajax({
                        type: "GET",
                        contentType: "application/json; charset=utf-8",
                        url: getContext() + '/' + url,
                        data: dataTarget,
                        dataType: "json",
                        success: function(data) {
                            if (data) {
                                response(data.globalList);
                            }
                        },
                        error: function() {
                            alert("Error occurred while fetching data.");
                        }
                    });
                }
            },
            minLength: 0,
            maxShowItems: 40,
            scroll: true,
            select: function(event, ui) {
                let lat, lng;
                const prefix = ui.item[0].split("_")[0];

                // Extract latitude and longitude based on the prefix
                if (["WARE", "MH", "HH", "DB"].includes(prefix)) {
                    lat = ui.item[4];
                    lng = ui.item[3];
                } else if (prefix === "CUST") {
                    lat = ui.item[5];
                    lng = ui.item[4];
                }

                $("#" + SiteID + index).val(ui.item[0]);
                $("#" + LongID + index).val(lng);
                $("#" + LatID + index).val(lat);

                const locationNum = $(this).closest('tr').find("input[name='location_number']").val();
                console.log(locationNum);

                updateMarker(locationNum);
                return false;  // Prevent default behavior
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            const prefix = item[0].split("_")[0];
            let listItem;

            // Render the autocomplete list item based on the prefix
            listItem = $("<li class='each'>").append(
                "<div class='acItem'><span class='name' style='font-weight:bold'>" + item[0] + "</span><br><span class='desc'>" + item[1] + (item[2] ? ', ' + item[2] : '') + "</span></div>"
            );

            return listItem.appendTo(ul);
        };

        $(inputElement).focus(function() {
            if (this.value === "") {
                $(this).autocomplete("search", "");
            }
        });
    }

    // Handle checkbox change event to recreate input
    $('input:checkbox[class="' + checkboxclass + '"]').change(function() {
        // If Place or Generic is unchecked, delete and recreate the input field
        recreateInputField();
    });

    // Initially apply autocomplete to the existing input
    applyAutocomplete($("#" + SiteID + index));
}


// Function to update the marker position

function updateMarker(locationNum) {
    const row = $(`#Multy_auxiliary input[name='location_number'][value='${locationNum}']`).closest('tr');
    const lat = parseFloat(row.find(`input[name^='siteLat_Multy']`).val());
    const lng = parseFloat(row.find(`input[name^='siteLng_Multy']`).val());

    if (!isNaN(lat) && !isNaN(lng)) {
        const locationData = locationsArray[locationNum];
        console.log("zeinaaa");
        console.log(locationsArray);
        if (locationData) {
            locationData.marker.setPosition({ lat, lng });
            locationData.lat = lat;
            locationData.lng = lng;
            console.log(`Updated marker for ${locationNum} to new position:`, { lat, lng });
        } else {
            console.error(`Marker not found for location number: ${locationNum}`);
        }
    } else {
        console.error('Invalid latitude or longitude:', lat, lng);
    }
}