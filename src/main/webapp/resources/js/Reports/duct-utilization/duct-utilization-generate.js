function onGenerateClick() {

    generateFlag = "1";
    prepareGenerateProcess();

    if (!validateGenerateInputs()) {
        return;
    }

    callGenerateReport();
}

function validateGenerateInputs() {
    if ($("#ductPath").val() === "") {
        alert("Please enter a cable!");
        return false;
    }

    return true;
}

function prepareGenerateProcess() {

    resetAllLegendCheckboxes();

    showRelPathFlag = "notOpened";
    jctElementsFlag = "notOpened";

    closeInfoWindows();

    resetMapData();

    rebuildGridTable();
}

function callGenerateReport() {

    showLoader();
    ductID = $("#ductPath").val().split(":")[0];

    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: getContext() + "/GenerateDuctUtilizationReport",
        dataType: "json",
        data: {
            ductID: ductID
        },

        success: function(data) {

            console.log("Success in Ajax, the returned  data duct is ", data.duct);
            console.log("Success in Ajax, the returned  data duct aux points is ", data.ductRenderPoints);
            console.log("Success in Ajax, the returned  data reportSegments is ", data.reportSegments);

            handleGenerateSuccess(data);
            hideLoader();
        },

        error: function(error) {

            handleGenerateError(error);
            hideLoader();
        }
    });
}

function handleGenerateSuccess(data) {

    if (data != null) {
        duct = data.duct;
        listDuctAuxPoints = data.listDuctAuxPoints; // It contains the aux points of the duct without the source and destination.
        ductRenderPoints = data.ductRenderPoints; // It contains the aux points of the duct with the source and destination.
        auxPointIndex = data.ductPointById; // It is map where the key is the aux ID and the value is the aux point details.
		console.log("auxPointIndex  is ", auxPointIndex );
        fiberCablesDetails = data.fiberCablesDetails; // It is map of map, where key is the cable id and value is map for cable details
        ReportArrayGlobal = data.reportSegments; // Duct Segment Information
		

        prepareDuctSectionDrawings(ReportArrayGlobal);

        if (ReportArrayGlobal.length == 0) {
            alert("There is no data to display");
            $('#totalCables').val('0');
            $('#totalStrands').val('0');
            $('#geoDistance').val('0');

            return;
        }
		
		const reportDataForGrid = ReportArrayGlobal.map(row => {

		    const newRow = { ...row };

		    const cables = newRow.cables;

		    if (cables && typeof cables === "object" && !Array.isArray(cables)) {
		        newRow.cables = Object.entries(cables)
		            .map(([k, v]) => `${k} → ${v}`)
		            .join(" , ");
		    }

		    return newRow;
		});
		
		reportDataForGrid.forEach(segment => {
		    window.reportSegmentByAuxId[segment.fromAuxId] = segment;
		});
		

        initializeGrid(reportDataForGrid);

        window["mapPointsNames_" + ductID] = [];
        window["mapPoints_" + ductID] = [];

        srcDstDuctList = duct;

        processDuctSrcDstArray(ductID, "mapPointsNames_", duct, "mapPoints_");

        window["bounds_" + ductID] = new google.maps.LatLngBounds();
        window["bounds_" + ductID].extend(
            new google.maps.LatLng(
                parseFloat(duct.srcLat),
                parseFloat(duct.srcLong)
            )
        );

        window["bounds_" + ductID].extend(
            new google.maps.LatLng(
                parseFloat(duct.destLat),
                parseFloat(duct.destLong)
            )
        );

        processDuctAuxPoints(ductID, listDuctAuxPoints, "mapPointsNames_");

        window["mapPoints_" + ductID].push(
            new google.maps.LatLng(
                parseFloat(duct.destLat),
                parseFloat(duct.destLong)
            )
        );

        var totalCables = 0;
        $('#totalCables').val(totalCables);

        var totalStrands = 0;
        $('#totalStrands').val(totalStrands);


        var totalGeoDistance = 0;
        $('#geoDistance').val(totalGeoDistance);


        if (allPaths.includes(ductID) == false) {
            allPaths.push(ductID);
        }
        buildPath(ductID, pathArray, $("#ductPath").val().split(":")[1], window["mapPoints_" + ductID], '#8e8080', 0.6, 10, '#8e8080', 1);
        pathArray[ductID].setMap(map);
        $('.showHideCableCheckbox').prop('checked', true);
        $(".showHideCableCheckbox").attr('disabled', false);

        map.fitBounds(window["bounds_" + ductID]);
    }
}


function handleGenerateError(error) {

    console.log("The error is " + error);
}

function prepareDuctSectionDrawings(reportSegments) {

    reportSegments.forEach(segment => {

        sortSegmentCables(segment);

        segment.drawingHtml = buildDuctSectionSvg(segment);
    });
}

function sortSegmentCables(segment) {

    if (!segment.cables) return;

    const sortedEntries = Object.entries(segment.cables)
        .sort((a, b) => {

            const sortingA = parseInt(
                fiberCablesDetails[a[0]]?.sorting || 999999
            );

            const sortingB = parseInt(
                fiberCablesDetails[b[0]]?.sorting || 999999
            );

            return sortingA - sortingB;
        });

    segment.cables = Object.fromEntries(sortedEntries);
}

function buildDuctSectionSvg(segment) {

    const cx = 50;
    const cy = 55;
    const R = 45;

    const cablesModel = buildSegmentCableModel(segment);

    let circles = buildInitialCircles(cablesModel, cx, cy, R);

    resolveCollisions(circles, cx, cy, R);

    return renderSvg(circles);
}

function buildSegmentCableModel(segment) {

    const cables = [];
    const cablesMap = segment.cables || {};

    Object.keys(cablesMap).forEach(cableId => {

        const cableName = cablesMap[cableId];

        const cableInfo = fiberCablesDetails?.[cableId];

        const tubes = Number(cableInfo?.numberOfTubes ?? 1);
        const strands = Number(cableInfo?.numberOfStrands ?? 1);
        const sorting = Number(cableInfo?.sorting ?? 999999);

        const totalStrands = tubes * strands;

        cables.push({
            cableId,
            name: cableName || cableInfo?.fiberCableName || cableId,
            sorting,
            r: calculateCableRadius(totalStrands)
        });
    });

    return cables;
}

function calculateCableRadius(totalStrands) {

    const minR = 4;
    const maxR = 18;

    const t = Math.log(totalStrands + 1) / Math.log(300);

    return minR + t * (maxR - minR);
}

function buildInitialCircles(cables, cx, cy, R) {

    const sorted = [...cables].sort((a, b) => a.sorting - b.sorting);

    const circles = [];

    // Special layout for crowded ducts
    if (sorted.length >= 8) {

        const bottomRowCount = Math.ceil(sorted.length / 2);
        const topRowCount = sorted.length - bottomRowCount;

        const floorY = cy + R;

        sorted.forEach((c, index) => {

            let row;
            let positionInRow;
            let rowCount;

            if (index < bottomRowCount) {

                row = 0;
                positionInRow = index;
                rowCount = bottomRowCount;

            } else {

                row = 1;
                positionInRow = index - bottomRowCount;
                rowCount = topRowCount;
            }

            const spacing =
                rowCount === 1
                    ? 0
                    : (2 * (R - c.r * 1.2)) / (rowCount - 1);

            const x =
                cx -
                (R - c.r * 1.2) +
                positionInRow * spacing;

            const y =
                floorY -
                c.r * 1.05 -
                row * (c.r * 2.3);

            circles.push({
                cableId: c.cableId,
                name: c.name,
                r: c.r,
                x,
                y
            });
        });

        return circles;
    }

    // Existing layout for 1-7 cables

    const startAngle = Math.PI * 1.15;
    const endAngle = Math.PI * 1.85;

    for (let i = 0;i < sorted.length;i++) {

        const c = sorted[i];

        const angle =
            startAngle +
            0.5 * (endAngle - startAngle);

        const radius = R - c.r;

        circles.push({
            cableId: c.cableId,
            name: c.name,
            r: c.r,
            x: cx + radius * Math.cos(angle),
            y: cy + radius * Math.sin(angle)
        });
    }

    return circles;
}

function resolveCollisions(circles, cx, cy, R) {

    const iterations = 90;

    const startAngle = Math.PI * 1.15;
    const endAngle = Math.PI * 1.85;

    const floorY = cy + R;

    const packedMode = circles.length >= 8;

    for (let iter = 0;iter < iterations;iter++) {

        // Arc guidance only for normal mode
        if (!packedMode) {

            for (const c of circles) {

                const targetY = floorY - c.r * 1.02;

                c.y += (targetY - c.y) * 0.05;
            }

            for (let i = 0;i < circles.length;i++) {

                const c = circles[i];

                let t;

                if (circles.length === 2) {
                    t = i === 0 ? 0.35 : 0.65;
                } else {
                    t =
                        circles.length === 1
                            ? 0.5
                            : i / (circles.length - 1);
                }

                const angle =
                    startAngle +
                    t * (endAngle - startAngle);

                const targetX =
                    cx +
                    (R - c.r * 1.05) *
                    Math.cos(angle);

                c.x += (targetX - c.x) * 0.18;
            }
        }

        // Collision solver
        for (let i = 0;i < circles.length;i++) {
            for (let j = i + 1;j < circles.length;j++) {

                const a = circles[i];
                const b = circles[j];

                const dx = b.x - a.x;
                const dy = b.y - a.y;

                const dist =
                    Math.sqrt(dx * dx + dy * dy) || 0.0001;

                const minDist = a.r + b.r;

                if (dist < minDist) {

                    const overlap =
                        (minDist - dist) * 0.75;

                    const nx = dx / dist;
                    const ny = dy / dist;

                    a.x -= nx * overlap;
                    a.y -= ny * overlap;

                    b.x += nx * overlap;
                    b.y += ny * overlap;
                }
            }
        }

        // Boundary constraint
        for (const c of circles) {

            const dx = c.x - cx;
            const dy = c.y - cy;

            const dist =
                Math.sqrt(dx * dx + dy * dy) || 0.0001;

            const maxDist = R - c.r * 1.02;

            if (dist > maxDist) {

                const scale = maxDist / dist;

                c.x = cx + dx * scale;
                c.y = cy + dy * scale;
            }
        }
    }
}

function keepInsideDuct(circles, cx, cy, R) {

    const floorY = cy + R;
    const leftLimit = cx - R;
    const rightLimit = cx + R;

    for (const c of circles) {

        // lock bottom
        c.y = floorY - c.r * 1.02;

        // clamp X inside duct
        const minX = leftLimit + c.r;
        const maxX = rightLimit - c.r;

        if (c.x < minX) c.x = minX;
        if (c.x > maxX) c.x = maxX;
    }
}

function renderSvg(circles) {

    const cx = 50;
    const cy = 55;
    const R = 45;

    let svg = `
        <svg width="120" height="120" viewBox="0 0 100 110">
    `;

    svg += `
        <circle cx="${cx}" cy="${cy}" r="${R}"
            fill="none"
            stroke="#333"
            stroke-width="2"/>
    `;

    circles.forEach((c, i) => {

        svg += `
            <circle
                cx="${c.x.toFixed(2)}"
                cy="${c.y.toFixed(2)}"
                r="${c.r}"
                fill="${getCableColor(i)}"
                stroke="#000"
                stroke-width="0.8"/>
        `;
    });

    svg += `</svg>`;

    return svg;
}

function getCableColor(i) {

    const colors = [
        "#4285F4", "#DB4437", "#F4B400", "#0F9D58", "#AB47BC",
        "#00ACC1", "#FF7043", "#5C6BC0", "#9CCC65", "#26A69A"
    ];

    return colors[i % colors.length];
}

function processDuctAuxPoints(ductID, ductAuxData, windowMapPointsNames) {

    for (let i = 0;i < ductAuxData.length;i++) {

        const point = ductAuxData[i];

        window["mapPoints_" + ductID].push(
            new google.maps.LatLng(
                parseFloat(point.lat),
                parseFloat(point.long)
            )
        );

        window["bounds_" + ductID].extend(
            new google.maps.LatLng(
                parseFloat(point.lat),
                parseFloat(point.long)
            )
        );

        let auxPoint = point.auxPointName || "";

        window["" + windowMapPointsNames + ductID]
            .splice(
                window["" + windowMapPointsNames + ductID].length - 1,
                0,
                auxPoint
            );
    }
}

function processDuctSrcDstArray(ductID, windowMapPointsNames, duct, windowMapPoints) {

    if (!duct) {
        return;
    }

    let src = duct.sourceName || "";
    let dst = duct.destinationName || "";

    window["" + windowMapPointsNames + ductID].push(src);
    window["" + windowMapPointsNames + ductID].push(dst);

    window["" + windowMapPoints + ductID].push(
        new google.maps.LatLng(
            parseFloat(duct.srcLat),
            parseFloat(duct.srcLong)
        )
    );
}