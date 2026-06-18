function showOnMap() {
    clearDuctVisualizationLayer();
    clearAllClusters();

    if (!ReportArrayGlobal) return;

    ReportArrayGlobal.forEach(segment => {

        const type = detectAuxType(segment.fromAuxId, segment.fromAuxName);
        const bucket = resolveMarkerBucket(type);

        if (!bucket) {
            return;
        }

        if (bucket.arr[segment.fromAuxId]) {
            return;
        }

        const marker = createMarker(
            segment.fromAuxId,
            segment.fromLongitude,
            segment.fromLatitude,
            segment.fromAuxName,
            resolveIcon(bucket.icon, window.ductLayer.viewMode),
            bucket.target
        );

        marker.segment = segment;

        bucket.arr[segment.fromAuxId] = marker;

        attachMarkerBehavior(marker);
        applyRenderingPolicy(marker, type);

    });

    map.fitBounds(window["bounds_" + ductID]);
    map.setZoom(10);

    //Scroll to the map div
    document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
}

function attachMarkerBehavior(marker) {

    google.maps.event.addListener(marker, "click", function() {

        openAuxWithDuct(this);
    });
}

function applyRenderingPolicy(marker, type) {

    const cluster = resolveCluster(type);

    if (window.ductLayer.viewMode === "clusterMode" && cluster) {
        console.log("the mode is clusterMode");
        cluster.addMarker(marker);
    }
    else {
        marker.setMap(map);
    }
}

function setDuctViewMode(mode) {

    window.ductLayer.viewMode = mode;

    clearDuctVisualizationLayer();
    clearAllClusters();

    showOnMap();
}

function openAuxWithDuct(marker) {

    const content = `
        <div style="min-width:240px">

            <div><b>AUX ID:</b> ${marker.segment.fromAuxId}</div>
            <div><b>Name:</b> ${marker.segment.fromAuxName}</div>
            <div><b>Sequence:</b> ${marker.segment.fromSequence}</div>

            <hr/>

            <button id="openDuctSvgBtn"
                style="padding:6px 10px; cursor:pointer;">
                View Duct Section
            </button>

        </div>
    `;

    infoWindow.close();
    infoWindow.setContent(content);
    infoWindow.open(map, marker);

    google.maps.event.addListenerOnce(infoWindow, "domready", function() {
        document.getElementById("openDuctSvgBtn")
            .addEventListener("click", function() {
                openSegmentOverlay(marker.segment);
            });
    });
}

function openSegmentOverlay(segment) {

    const auxId = segment.fromAuxId;

    // already open
    if (window.ductLayer.overlays.has(auxId)) {
        return;
    }

    const overlay = new google.maps.OverlayView();

    overlay.onAdd = function() {

        const div = document.createElement("div");

        div.style.position = "absolute";
        div.style.zIndex = 1000;

        div.innerHTML = `
	        <div style="
	            position:relative;
	            background:white;
	            border:1px solid #ccc;
	            padding:5px;
	            border-radius:4px;
	        ">

	            <button
	                class="closeDuctOverlayBtn"
	                data-aux-id="${auxId}"
	                style="
	                    position:absolute;
	                    top:2px;
	                    right:2px;
	                    cursor:pointer;
	                ">
	                X
	            </button>

	            ${segment.drawingHtml}

	        </div>
	    `;

        this.div = div;

        const panes = this.getPanes();
        panes.floatPane.appendChild(div);

        const btn = div.querySelector(".closeDuctOverlayBtn");

        btn.addEventListener("click", function() {
            closeSegmentOverlay(auxId);
        });
    };

    overlay.draw = function() {

        const projection = this.getProjection();

        const pos = projection.fromLatLngToDivPixel(
            new google.maps.LatLng(segment.fromLatitude, segment.fromLongitude)
        );

        const scale = getOverlayScale();

        console.log("Zoom =", map.getZoom(), "Scale =", scale);

        if (!this.div) return;
        this.div.style.left = pos.x + "px";
        this.div.style.top = pos.y + "px";
        this.div.style.transformOrigin = "top left";
        this.div.style.transform = `scale(${scale})`;

        console.log("this.div.style.transform is ", this.div.style.transform);
        console.log(
            this.div.getBoundingClientRect().width,
            this.div.getBoundingClientRect().height
        );
    };

    overlay.onRemove = function() {

        if (this.div) {
            this.div.remove();
            this.div = null;
        }
    };

    window.ductLayer.overlays.set(auxId, overlay);

    overlay.setMap(map);

    google.maps.event.addListenerOnce(map, "idle", () => {
        overlay.draw();
    });
}

function closeSegmentOverlay(auxId) {

    const overlay =
        window.ductLayer.overlays.get(auxId);

    if (!overlay) {
        console.log("No overlay existed");
        return;
    }

    overlay.setMap(null);

    window.ductLayer.overlays.delete(auxId);
}

function getOverlayScale() {
    const zoom = map.getZoom();

    const minZoom = 6;
    const maxZoom = 16;

    const minScale = 0.2;
    const maxScale = 1.3; // keep this tight to avoid huge overlays

    const t = (zoom - minZoom) / (maxZoom - minZoom);
    const clamped = Math.max(0, Math.min(1, t));

    // soft curve (reduces aggressive growth at high zoom)
    const eased = Math.pow(clamped, 0.8);

    return minScale + (maxScale - minScale) * eased;
}

/*
function getOverlayScale() {
    const zoom = map.getZoom();

    if (zoom <= 6) return 0.2;
    if (zoom <= 7) return 0.4;
    if (zoom <= 8) return 0.8;
    if (zoom <= 9) return 1.2;
    return 2.0;
}


function getOverlayScale() {
    const zoom = map.getZoom();

    const minZoom = 6;
    const maxZoom = 16;

    const minScale = 0.7;   // never too small
    const maxScale = 1.4;   // never too large

    const t = (zoom - minZoom) / (maxZoom - minZoom);
    const clamped = Math.max(0, Math.min(1, t));

    return minScale + (maxScale - minScale) * clamped;
}
*/