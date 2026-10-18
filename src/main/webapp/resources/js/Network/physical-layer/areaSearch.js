/*
 * areaSearch.js
 * Hold Ctrl and drag on the map to select an area. The floating "Area Search"
 * popup opens automatically and lists the Manholes / Handholes whose markers are
 * currently DISPLAYED on the map and fall inside the selected area.
 * The popup is non-modal and is closed only with its X button.
 *
 * "Displayed" = the marker is on the map (non-clustered map) or inside its
 * MarkerClusterer (markerClusterManhole / markerClusterHandhole), which is exactly
 * what the tree checkboxes do (checked -> setMap + addMarker, unchecked -> removed).
 *
 * Row data (name, project...) comes from physicalLayerList and from window[id]
 * (filled by appendProjectElement for opened projects). No AJAX.
 *   Row layout  : [0]=ID, [1]=Name, [2]=Longitude, [3]=Latitude, [4]=Project ID
 *   Project name: physicalLayerList['Project'] rows [0]=ID, [1]=Name;
 *                 unknown project id => "Current Physical Layer" (live network)
 *
 * Needs : jQuery (+ jQuery UI draggable/resizable), Bootstrap tabs, Google Maps global `map`,
 *         CSS class .ativeRecord (Network_Index.css), areaSearch.css, AreaSearchPopup.jsp.
 * Include after Physical_Layer_Map.js and ProjectCreation.js.
 */
(function (window, $) {
    'use strict';

    var LIVE_NETWORK_NAME = 'Current Physical Layer';
    var POPUP = '#areaSearchPopup';
    var MAP_DIV_ID = 'mapContainer';
    var MIN_DRAG_PIXELS = 5;

    var state = {
        armed: false,        // "Select Area (no Ctrl)" button pressed: next plain drag selects
        ctrlDown: false,     // Ctrl is currently held (cursor becomes a crosshair over the map)
        dragging: false,
        startPx: null,       // {x, y} relative to the map container
        startClient: null,   // {x, y} client coordinates (for the visual box)
        box: null,           // temporary div shown while dragging
        rect: null,          // google.maps.Rectangle kept after the selection
        hint: null,
        positioned: false
    };

    /* ------------------------------------------------------------------ */
    /* Generic helpers                                                    */
    /* ------------------------------------------------------------------ */

    function esc(value) {
        return $('<div>').text(value === null || value === undefined ? '' : value).html();
    }

    function stripHtml(html) {
        return $('<div>').html(html || '').text();
    }

    /* ------------------------------------------------------------------ */
    /* Pixel -> LatLng (Web Mercator, so we don't depend on Maps events)  */
    /* ------------------------------------------------------------------ */

    function latLngToWorld(lat, lng, scale) {
        var sin = Math.min(Math.max(Math.sin(lat * Math.PI / 180), -0.9999), 0.9999);
        return {
            x: (lng + 180) / 360 * scale,
            y: (0.5 - Math.log((1 + sin) / (1 - sin)) / (4 * Math.PI)) * scale
        };
    }

    function worldToLatLng(x, y, scale) {
        var n = Math.PI - 2 * Math.PI * y / scale;
        return {
            lat: 180 / Math.PI * Math.atan(0.5 * (Math.exp(n) - Math.exp(-n))),
            lng: x / scale * 360 - 180
        };
    }

    function containerPixelToLatLng(px, py) {
        var center = map.getCenter();
        var scale = 256 * Math.pow(2, map.getZoom());
        var c = latLngToWorld(center.lat(), center.lng(), scale);
        var div = map.getDiv();
        return worldToLatLng(
            c.x + (px - div.clientWidth / 2),
            c.y + (py - div.clientHeight / 2),
            scale
        );
    }

    function areaFromPixels(p1, p2) {
        var a = containerPixelToLatLng(p1.x, p1.y);
        var b = containerPixelToLatLng(p2.x, p2.y);
        return {
            north: Math.max(a.lat, b.lat),
            south: Math.min(a.lat, b.lat),
            east: Math.max(a.lng, b.lng),
            west: Math.min(a.lng, b.lng)
        };
    }

    /* ------------------------------------------------------------------ */
    /* Displayed markers                                                  */
    /* ------------------------------------------------------------------ */

    function isDisplayed(marker, clusterer) {
        if (!marker) return false;
        if (marker.getMap && marker.getMap()) return true;
        return false;
    }

    function displayedMarkers(markersArray, clusterer) {
        var result = [];
        var seen = {};

        function add(m) {
            if (!m || m.ID === undefined || seen[m.ID]) return;
            seen[m.ID] = true;
            result.push(m);
        }

        // markers inside the clusterer (checked in the tree, possibly hidden under a cluster icon)
        if (clusterer && typeof clusterer.getMarkers === 'function') {
            clusterer.getMarkers().forEach(add);
        }
        // markers drawn directly on the map (non-clustered map)
        if (Array.isArray(markersArray)) {
            markersArray.forEach(function (m) { if (isDisplayed(m, clusterer)) add(m); });
        }
        return result;
    }

    /* ------------------------------------------------------------------ */
    /* Row lookup (name, project...)                                      */
    /* ------------------------------------------------------------------ */

    function buildProjectNameMap() {
        var map = {};
        var list = (typeof physicalLayerList !== 'undefined' && physicalLayerList && physicalLayerList['Project']) || [];
        for (var i = 0; i < list.length; i++) map[String(list[i][0])] = list[i][1];
        return map;
    }

    function buildRowLookup(type) {
        var lookup = {};
        var base = (typeof physicalLayerList !== 'undefined' && physicalLayerList) ? physicalLayerList[type] : null;
        if (Array.isArray(base)) {
            base.forEach(function (r) { if (Array.isArray(r)) lookup[String(r[0])] = r; });
        }
        return lookup;
    }

    function rowFor(marker, lookup) {
        var id = String(marker.ID);
        var row = lookup[id];
        if (!row) {
            var w = window[id];
            if (Array.isArray(w)) row = w;
        }
        if (row) return row;

        // fallback: build the row from the marker itself
        return [id, stripHtml(marker.data), marker.getPosition().lng(), marker.getPosition().lat(), null];
    }

    function projectNameOf(row, projectMap) {
        var name = projectMap[String(row[4])];
        return name ? name : LIVE_NETWORK_NAME;
    }

    /* ------------------------------------------------------------------ */
    /* Search + render                                                    */
    /* ------------------------------------------------------------------ */

    function inside(pos, area) {
        var lat = pos.lat(), lng = pos.lng();
        return lat >= area.south && lat <= area.north && lng >= area.west && lng <= area.east;
    }

    function findRows(type, area) {
        var markersArray, clusterer;
        if (type === 'Manhole') {
            markersArray = (typeof markersManhole !== 'undefined') ? markersManhole : null;
            clusterer = (typeof markerClusterManhole !== 'undefined') ? markerClusterManhole : null;
        } else {
            markersArray = (typeof markersHandhole !== 'undefined') ? markersHandhole : null;
            clusterer = (typeof markerClusterHandhole !== 'undefined') ? markerClusterHandhole : null;
        }

        var lookup = buildRowLookup(type);
        var rows = [];
        displayedMarkers(markersArray, clusterer).forEach(function (m) {
            if (inside(m.getPosition(), area)) rows.push(rowFor(m, lookup));
        });
        return rows;
    }

    function renderTable(tbodySelector, totalSelector, rows, projectMap) {
        var html = '';
        rows.forEach(function (row) {
            html += '<tr data-id="' + esc(row[0]) + '">' +
                '<td style="text-align:center;"><input type="checkbox" class="areaSearchChk" data-id="' + esc(row[0]) + '"></td>' +
                '<td style="text-align:center;">' + esc(row[0]) + '</td>' +
                '<td>' + esc(row[1]) + '</td>' +
                '<td style="text-align:center;">' + esc(row[2]) + '</td>' +
                '<td style="text-align:center;">' + esc(row[3]) + '</td>' +
                '<td>' + esc(projectNameOf(row, projectMap)) + '</td>' +
                '</tr>';
        });
        $(tbodySelector).html(html);
        $(totalSelector).val(rows.length);
    }

    function searchArea(area) {
        var projectMap = buildProjectNameMap();
        var manholes = findRows('Manhole', area);
        var handholes = findRows('Handhole', area);

        renderTable('#areaSearchManTBody', '#areaSearchTotalMan', manholes, projectMap);
        renderTable('#areaSearchHandTBody', '#areaSearchTotalHand', handholes, projectMap);

        $('#areaSearchSelectAllMan, #areaSearchSelectAllHand').prop('checked', false);
        $('#areaSearchInfo').text(
            'Area: ' + area.south.toFixed(5) + ', ' + area.west.toFixed(5) +
            '  to  ' + area.north.toFixed(5) + ', ' + area.east.toFixed(5)
        );
        return { manholes: manholes, handholes: handholes };
    }

    /* ------------------------------------------------------------------ */
    /* Popup                                                              */
    /* ------------------------------------------------------------------ */

    function applyPermissions() {
        // same conditions the tree uses in Physical_Layer_Tree.js
        var man = readManhole === '1' ||
            (readManhole === '0' && (treeExceptionMan === '1' || writeExceptionMan === '1'));
        var hand = readHandhole === '1' ||
            (readHandhole === '0' && (treeExceptionHand === '1' || writeExceptionHand === '1'));

        $('#areaSearchMan-tab').parent().toggle(man);
        $('#areaSearchHand-tab').parent().toggle(hand);
        if (!man && hand) $('#areaSearchHand-tab').tab('show');
    }

    var EDGE_MARGIN = 10;  // gap kept from the bottom of the screen when minimized

    // Minimize: collapse to the header and dock it at the bottom of the screen.
    // Restore : back to full size, centered on the screen.
    // The user can still drag it anywhere afterwards.
    function setMinimized(min) {
        var $p = $(POPUP);
        var wasMin = $p.hasClass('areaSearchMinimized');
        if (wasMin === min) return;

        $p.toggleClass('areaSearchMinimized', min);
        $('#areaSearchMinBtn').attr('title', min ? 'Restore' : 'Minimize');

        var winW = $(window).width();
        var winH = $(window).height();
        var w = $p.outerWidth();
        var h = $p.outerHeight();   // measured after the class change

        if (min) {
            $p.css({
                top: Math.max(0, winH - h - EDGE_MARGIN) + 'px',
                left: Math.min(Math.max(0, parseInt($p.css('left'), 10) || 0), Math.max(0, winW - w)) + 'px'
            });
        } else {
            $p.css({
                top: Math.max(0, (winH - h) / 2) + 'px',
                left: Math.max(0, (winW - w) / 2) + 'px'
            });
        }
    }

    function openPopup() {
        var $p = $(POPUP);
        setMinimized(false);   // new results are always shown expanded
        if (!$p.is(':visible')) {
            $p.show();
            if (!state.positioned) {
                // first open only: center of the screen; later opens keep the last position
                $p.css({
                    top: Math.max(0, ($(window).height() - $p.outerHeight()) / 2) + 'px',
                    left: Math.max(0, ($(window).width() - $p.outerWidth()) / 2) + 'px'
                });
                state.positioned = true;
            }
        }
        try { applyPermissions(); } catch (e) { /* permission variables not available */ }
    }

    function closePopup() {
        clearAll();
        // leave the minimized state without moving the popup, so it reopens where it was closed
        $(POPUP).removeClass('areaSearchMinimized').hide();
        $('#areaSearchMinBtn').attr('title', 'Minimize');
    }

    function clearAll() {
        cancelDrag();
        removeRectangle();
        $('#areaSearchManTBody, #areaSearchHandTBody').empty();
        $('#areaSearchTotalMan, #areaSearchTotalHand').val('');
        $('#areaSearchSelectAllMan, #areaSearchSelectAllHand').prop('checked', false);
        $('#areaSearchInfo').text('');
    }

    /* ------------------------------------------------------------------ */
    /* Drag selection on the map                                          */
    /* ------------------------------------------------------------------ */

    function showHint() {
        hideHint();
        state.hint = $('<div>')
            .text('Click and drag on the map to select an area  (Esc to cancel)')
            .css({
                position: 'fixed', top: '70px', left: '50%', transform: 'translateX(-50%)',
                background: '#2678CC', color: '#fff', padding: '6px 16px',
                borderRadius: '4px', zIndex: 5000, fontSize: '14px', pointerEvents: 'none'
            })
            .appendTo('body');
    }

    function hideHint() {
        if (state.hint) { state.hint.remove(); state.hint = null; }
    }

    function removeRectangle() {
        if (state.rect) { state.rect.setMap(null); state.rect = null; }
    }

    function removeBox() {
        if (state.box) { state.box.remove(); state.box = null; }
    }

    // The crosshair is applied through a CSS class (see areaSearch.css) because Google Maps
    // sets its hand cursor inline on inner elements.
    function updateCursor() {
        var on = state.ctrlDown || state.armed || state.dragging;
        document.body.classList.toggle('areaSearchCrosshair', on);
    }

    // Injected from JS so the cursor does not depend on areaSearch.css being deployed/refreshed.
    function injectCursorStyle() {
        if (document.getElementById('areaSearchCursorStyle')) return;
        var css =
            'body.areaSearchCrosshair #' + MAP_DIV_ID + ',' +
            'body.areaSearchCrosshair #' + MAP_DIV_ID + ' *,' +
            'body.areaSearchCrosshair .gm-style,' +
            'body.areaSearchCrosshair .gm-style * { cursor: crosshair !important; }';
        var style = document.createElement('style');
        style.id = 'areaSearchCursorStyle';
        style.appendChild(document.createTextNode(css));
        document.head.appendChild(style);
    }

    function setCtrl(down) {
        if (state.ctrlDown === down) return;
        state.ctrlDown = down;
        updateCursor();
    }

    // Esc while the "Select Area (no Ctrl)" mode is waiting for the drag cancels that mode.
    // Capture phase so Google Maps can never swallow the key.
    function onArmedKeyDown(ev) {
        if (ev.key === 'Escape') cancelDrag();
    }

    function setArmed(on) {
        state.armed = on;
        document.removeEventListener('keydown', onArmedKeyDown, true);
        if (on) {
            document.addEventListener('keydown', onArmedKeyDown, true);
            showHint();
        } else {
            hideHint();
        }
        updateCursor();
    }

    function cancelDrag() {
        state.dragging = false;
        removeBox();
        $(document).off('.areaSearchDrag');
        setArmed(false);   // also refreshes the cursor
    }

    function relativeToMap(ev) {
        var r = document.getElementById(MAP_DIV_ID).getBoundingClientRect();
        return { x: ev.clientX - r.left, y: ev.clientY - r.top };
    }

    function updateBox(ev) {
        var x1 = Math.min(state.startClient.x, ev.clientX);
        var y1 = Math.min(state.startClient.y, ev.clientY);
        state.box.css({
            left: x1 + 'px', top: y1 + 'px',
            width: Math.abs(ev.clientX - state.startClient.x) + 'px',
            height: Math.abs(ev.clientY - state.startClient.y) + 'px'
        });
    }

    function onMouseDown(ev) {
        if (ev.button !== 0) return;
        if (!(ev.ctrlKey || state.armed)) return;
        if (typeof map === 'undefined' || !map) return;

        // keep Google Maps from starting a pan / click
        ev.stopPropagation();
        ev.preventDefault();

        removeRectangle();
        state.dragging = true;
        updateCursor();
        state.startPx = relativeToMap(ev);
        state.startClient = { x: ev.clientX, y: ev.clientY };
        state.box = $('<div>').css({
            position: 'fixed', left: ev.clientX + 'px', top: ev.clientY + 'px', width: 0, height: 0,
            border: '2px solid #3394FF', background: 'rgba(51,148,255,.15)',
            zIndex: 4000, pointerEvents: 'none'
        }).appendTo('body');

        $(document).on('mousemove.areaSearchDrag', function (e) { if (state.dragging) updateBox(e); });
        $(document).on('mouseup.areaSearchDrag', onMouseUp);
        $(document).on('keydown.areaSearchDrag', function (e) { if (e.key === 'Escape') cancelDrag(); });
    }

    function onMouseUp(ev) {
        if (!state.dragging) return;
        var endPx = relativeToMap(ev);
        var start = state.startPx;
        cancelDrag();

        if (Math.abs(endPx.x - start.x) < MIN_DRAG_PIXELS || Math.abs(endPx.y - start.y) < MIN_DRAG_PIXELS) return;

        var area = areaFromPixels(start, endPx);

        // keep the selected area visible on the map
        state.rect = new google.maps.Rectangle({
            map: map,
            clickable: false,
            strokeColor: '#3394FF',
            strokeWeight: 2,
            fillColor: '#3394FF',
            fillOpacity: 0.12,
            bounds: { north: area.north, south: area.south, east: area.east, west: area.west }
        });

        openPopup();
        searchArea(area);
    }

    /* ------------------------------------------------------------------ */
    /* Row selection (same technique as handleRowClick in the page)       */
    /* ------------------------------------------------------------------ */

    function bindTable(tableId, selectAllId) {
        $(document).on('click', tableId + ' tbody tr', function () {
            $(tableId + ' tbody tr').removeClass('ativeRecord');
            $(this).addClass('ativeRecord');
        });

        $(document).on('change', selectAllId, function () {
            $(tableId + ' tbody .areaSearchChk').prop('checked', $(this).is(':checked'));
        });
    }

    function getSelected(type) {
        var tableId = (type === 'Handhole') ? '#areaSearchHandhole' : '#areaSearchManhole';
        var ids = [];
        $(tableId + ' tbody .areaSearchChk:checked').each(function () {
            ids.push(String($(this).data('id')));
        });
        return ids; // IDs of the ticked rows
    }

    /* ------------------------------------------------------------------ */
    /* Init                                                               */
    /* ------------------------------------------------------------------ */

    $(function () {
        var $popup = $(POPUP);

        if ($.fn.draggable) $popup.draggable({ handle: '.areaSearchHeader', containment: 'window' });
        if ($.fn.resizable) $popup.resizable({ minWidth: 560, minHeight: 320 });

        // capture phase: runs before Google Maps sees the mouse down
        var mapDiv = document.getElementById(MAP_DIV_ID);
        if (mapDiv) {
            mapDiv.addEventListener('mousedown', onMouseDown, true);
            // keeps the cursor right even if Ctrl was pressed while the window had no focus
            mapDiv.addEventListener('mousemove', function (ev) { setCtrl(ev.ctrlKey); }, true);
        }

        // Ctrl pressed -> crosshair, Ctrl released -> back to the normal map cursor.
        // Capture phase so Google Maps can never swallow the key events.
        document.addEventListener('keydown', function (ev) { setCtrl(ev.ctrlKey || ev.key === 'Control'); }, true);
        document.addEventListener('keyup', function (ev) { setCtrl(ev.key === 'Control' ? false : ev.ctrlKey); }, true);
        window.addEventListener('blur', function () { setCtrl(false); });
        injectCursorStyle();

        $('#areaSearchCloseBtn').on('click', closePopup);
        $('#areaSearchMinBtn').on('click', function () {
            setMinimized(!$popup.hasClass('areaSearchMinimized'));
        });
        $('#areaSearchClearBtn').on('click', clearAll);
        $('#areaSearchDrawBtn').on('click', function () { setArmed(true); });

        bindTable('#areaSearchManhole', '#areaSearchSelectAllMan');
        bindTable('#areaSearchHandhole', '#areaSearchSelectAllHand');
    });

    window.AreaSearch = {
        open: openPopup,
        close: closePopup,
        clear: clearAll,
        search: searchArea,       // AreaSearch.search({north, south, east, west})
        getSelected: getSelected  // AreaSearch.getSelected('Manhole' | 'Handhole') -> array of ticked IDs
    };

})(window, jQuery);
