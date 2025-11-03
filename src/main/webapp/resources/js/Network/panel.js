   function openRightab(evt, functionality) {
	   var i, tabcontent, tablinks;
	   tabcontent = document.getElementsByClassName("rightcontent");
	   for (i = 0; i < tabcontent.length; i++) {
	     tabcontent[i].style.display = "none";
	   }
	   tablinks = document.getElementsByClassName("tablink");
	   for (i = 0; i < tablinks.length; i++) {
	     tablinks[i].className = tablinks[i].className.replace(" active", "");
	   }

	   document.getElementById(functionality).style.display = "block";
	   evt.currentTarget.className += " active";

	   // Show the close X only on the Panel button
	   if (functionality === "panelContainer") {
		   document.getElementById("panelBtn").style.display = "block";
	    // document.getElementById("closePanelX").style.display = "block";
	   var mapBtn = document.getElementById("DefaultRightbutton");
	   var panelBtn = document.getElementById("panelBtn");
	   mapBtn.className = mapBtn.className.replace(" active", "");
	   panelBtn.className += " active";

	     setTimeout(() => {
	       if (typeof drawPanelDiagram === "function") console.log("");
	     }, 100);
	   } else {
		   var mapBtn = document.getElementById("DefaultRightbutton");
		   var panelBtn = document.getElementById("panelBtn");
		   panelBtn.className = panelBtn.className.replace(" active", "");
		   mapBtn.className += " active";
	
	   }
	 }

	 function closePanel(event) {
	   event.stopPropagation(); // Prevent triggering the openRightab click
	   document.getElementById("panelContainer").style.display = "none";
	   document.getElementById("mapContainer").style.display = "block";
	   document.getElementById("panelBtn").style.display = "none";
	   
	   // Hide the close icon
	   //document.getElementById("closePanelX").style.display = "none";

	   // Make Map button active again
	   var mapBtn = document.getElementById("DefaultRightbutton");
	   var panelBtn = document.getElementById("panelBtn");
	   panelBtn.className = panelBtn.className.replace(" active", "");
	   mapBtn.className += " active";
	 }

function drawPanelDiagram(numRowsFromDb, numColumnsFromDb, controllerID, controllerName, dbId, dbName, rowPerModule, rowCounting) {
  const container = document.getElementById("panelStage");
  container.innerHTML = "";

  const wrapper = document.createElement("div");
  wrapper.style.display = "flex";
  wrapper.style.flexDirection = "column";
  wrapper.style.alignItems = "center";
  wrapper.style.width = "100%";

  // Create info text
  let infoText = "";

  if (controllerID && controllerName && controllerID != "null") {
    infoText += `${controllerID} / ${controllerName}`;
    infoText += " --- ";
  }
  if (dbName && dbId) {
    infoText += `${dbName} / ${dbId}`;
  }

  if (infoText) {
    const infoDiv = document.createElement("div");
    infoDiv.textContent = infoText;
    infoDiv.style.textAlign = "center";
    infoDiv.style.fontWeight = "bold";
    infoDiv.style.marginBottom = "10px";
    infoDiv.style.color = "black";
    wrapper.appendChild(infoDiv);
  }

  // Create canvas container
  const canvasDiv = document.createElement("div");
  canvasDiv.id = "konvaCanvasContainer";
  canvasDiv.style.width = "100%";
  canvasDiv.style.height = "100%";
  wrapper.appendChild(canvasDiv);

  // Append wrapper to panelStage
  container.appendChild(wrapper);

  // Now initialize Konva inside the new div

  // base measurements
  var portSpacingX = 35;   // already used for spacing ports horizontally
  var panelInnerPadding = 10;
  var sideRailWidth = 40;

  // dynamically calculate panel width based on number of columns
  var portWidth = 20; // approximate single port width
  var extraMargin = 40; // spacing buffer on both sides

  var panelWidth = numColumnsFromDb * portSpacingX + portWidth + panelInnerPadding - 20;

  // height still scales with number of rows
  var portSpacingY = 35;
  var panelHeight = numRowsFromDb * portSpacingY + 30;

  // stage size
  var stageWidth = panelWidth + sideRailWidth * 2 + 120;
  var stageHeight = Math.max(600, panelHeight + 160);

  const stage = new Konva.Stage({
    container: "konvaCanvasContainer",
    width: stageWidth,
    height: stageHeight,
    id: "mainPanelStage"
  });

  var layer = new Konva.Layer();
  stage.add(layer);

  // RU size in px (distance representing 1RU)
  var RU_PX = 168;
  var railsXLeft = 40;
  var railsXRight = stageWidth - railsXLeft - sideRailWidth;
  var railsY = 40;
  var railsHeight = Math.max(stageHeight - 80, panelHeight + 40);

  // helper: create fixed rails with some visual holes (kept as before)
  function createFixedRail(x, y, width, height, id) {
    var rail = new Konva.Rect({
      x: x,
      y: y,
      width: width,
      height: height,
      fill: "black",
      stroke: "#333",
      strokeWidth: 2,
      cornerRadius: 6,
      id: id
    });
    layer.add(rail);

    // keep previous visual holes as before (not used for RU snap)
    var holesCount = Math.max(4, Math.floor(height / 40));
    var spacing = height / (holesCount + 1);
    for (var i = 1; i <= holesCount; i++) {
      var hole = new Konva.Circle({
        x: x + width / 2,
        y: y + i * spacing,
        radius: 6,
        fill: "#ecf0f1",
        stroke: "#444",
        strokeWidth: 1
      });
      layer.add(hole);
    }

    rail.holesCount = holesCount;
    rail.holeSpacing = spacing;
    return rail;
  }

  var leftRail = createFixedRail(railsXLeft, railsY, sideRailWidth, railsHeight, "fixedLeftRail");
  var rightRail = createFixedRail(railsXRight, railsY, sideRailWidth, railsHeight, "fixedRightRail");

  // draw faint RU guide lines across rails (as before)
  for (let i = 0; i < Math.ceil(railsHeight / RU_PX) + 1; i++) {
    var yLine = railsY + i * RU_PX;
    var ruLine = new Konva.Line({
      points: [railsXLeft, yLine, railsXRight + sideRailWidth, yLine],
      stroke: "#000",
      strokeWidth: 0.3,
      dash: [6, 8],
      opacity: 0.06
    });
    layer.add(ruLine);
  }

  // panel group (draggable)
  var panelGroup = new Konva.Group({
    x: (stageWidth - panelWidth) / 2,
    y: railsY + Math.round((railsHeight - panelHeight) / 2),
    draggable: true,
    id: "panelGroup"
  });
  layer.add(panelGroup);

  var panelBackground = new Konva.Rect({
    x: 0,
    y: 0,
    width: panelWidth,
    height: panelHeight,
    fill: "#ecf0f1",
    stroke: "#111",
    strokeWidth: 2,
    cornerRadius: 8,
    id: "mainPanel"
  });
  panelGroup.add(panelBackground);

  panelBackground.on("click", function () {
    alert("Whole panel clicked");
  });

  // existing connector points (kept for compatibility)
  function createConnectorRelative(group, x, y, id) {
    var circ = new Konva.Circle({
      x: x,
      y: y,
      radius: 6,
      fill: "transparent",
      stroke: "#111",
      strokeWidth: 1,
      id: id
    });
    group.add(circ);
  }

  // existing four connectors (kept)
  createConnectorRelative(panelGroup, - (sideRailWidth / 2), leftRail.holeSpacing, "leftTopConnector");
  createConnectorRelative(panelGroup, - (sideRailWidth / 2), railsHeight - leftRail.holeSpacing, "leftBottomConnector");
  createConnectorRelative(panelGroup, panelWidth + (sideRailWidth / 2), rightRail.holeSpacing, "rightTopConnector");
  createConnectorRelative(panelGroup, panelWidth + (sideRailWidth / 2), railsHeight - rightRail.holeSpacing, "rightBottomConnector");

  // function to create a simple port (kept) — now accepts numberLabel as 4th parameter
  function createPort(group, x, y, id, numberLabel) {
    const portWidth = 20;
    const portHeight = 15;
    const notchWidth = 10;
    const notchHeight = 4;
    var port = new Konva.Line({
      points: [ x, y, x + 5, y, x + 5, y - notchHeight, x + 5 + notchWidth, y - notchHeight, x + 5 + notchWidth, y, x + portWidth, y, x + portWidth, y + portHeight, x, y + portHeight ],
      closed: true,
      stroke: "black",
      strokeWidth: 1.5,
      fill: "black",
      id: id
    });
    for (let i = 0; i < 4; i++) {
      let slot = new Konva.Rect({
        x: x + 2 + i * 4,
        y: y + 4,
        width: 3,
        height: 9,
        fill: "black",
        listening: false
      });
      group.add(slot);
    }
    port.on("click", function (e) {
      e.cancelBubble = true;
      alert("Clicked " + this.id());
    });
    group.add(port);
    var label = new Konva.Text({
      x: x,
      y: y + portHeight + 2,
      text: numberLabel ? numberLabel.toString() : "",
      width: portWidth,
      align: "center",
      fontSize: 7,
      fill: "black"
    });
    group.add(label);
  }

  // --- numbering logic (only implement first condition: rowPerModule null) ---
  let numberingArray = [];
  let seqCounter = 1;
  // normalize rowCounting string if exists
  let rc = (typeof rowCounting === 'string') ? rowCounting.trim() : rowCounting;

  if (rowPerModule == 0 || rowPerModule === "null") {
    if (rc === "Up To Down") {
      // top-left start, proceed rows top -> bottom
      for (let row = 0; row < numRowsFromDb; row++) {
        for (let col = 0; col < numColumnsFromDb; col++) {
          numberingArray.push(seqCounter++);
        }
      }
    } else if (rc === "Down To Up") {
		console.log("woww");
      // start from bottom-left, up
      // we push numbers in the visual order we'll draw ports so we must map correctly.
      // To keep drawing loop simple (row 0..N-1), we build a mapping where numberingArray[index] gives label for that drawn port.
      // Approach: build a 2D array labels[row][col], fill according to Down To Up, then flatten row-major for draw loop.
      let tmp = [];
      for (let r = 0; r < numRowsFromDb; r++) {
        tmp[r] = new Array(numColumnsFromDb);
      }
      let n = 1;
      for (let r = numRowsFromDb - 1; r >= 0; r--) {
        for (let c = 0; c < numColumnsFromDb; c++) {
          tmp[r][c] = n++;
        }
      }
      // flatten row-major so draw loop (row 0..N-1 col 0..M-1) can use numberingArray[index]
      for (let r = 0; r < numRowsFromDb; r++) {
        for (let c = 0; c < numColumnsFromDb; c++) {
          numberingArray.push(tmp[r][c]);
        }
      }
      // seqCounter already used not needed further
    } else {
      // default: treat as Up To Down if unknown or null
      for (let row = 0; row < numRowsFromDb; row++) {
        for (let col = 0; col < numColumnsFromDb; col++) {
          numberingArray.push(seqCounter++);
        }
      }
    }
  }

  // populate ports inside the panel (modified only to use numberingArray when present)
  var portSpacingX = 35;
  var portSpacingY = 35;
  var portHeightLocal = 15;
  var totalPortHeight = (numRowsFromDb - 1) * portSpacingY + portHeightLocal;
  var startX = panelInnerPadding;
  var startY = (panelHeight - totalPortHeight) / 2;

  let portCount = 1;       // fallback id counter if numberingArray not used
  let usedIndex = 0;       // index into numberingArray (if populated)

  for (let row = 0; row < numRowsFromDb; row++) {
    for (let col = 0; col < numColumnsFromDb; col++) {
      let x = startX + col * portSpacingX;
      let y = startY + row * portSpacingY;
      if (numberingArray.length > 0) {
        // use numbering array label, keep id as "P" + label
        let label = numberingArray[usedIndex];
        createPort(panelGroup, x, y, "P" + label, label);
        usedIndex++;
      } else {
        // original behavior (no numberingArray) — preserve exact behaviour
        createPort(panelGroup, x, y, "P" + portCount, portCount);
        portCount++;
      }
    }
  }

  // play sound (kept)
  function playHeavyClick() {
    try {
      const AudioCtx = window.AudioContext || window.webkitAudioContext;
      if (!AudioCtx) return;
      const ctx = new AudioCtx();

      const master = ctx.createGain();
      master.gain.value = 0.8;
      master.connect(ctx.destination);

      // Oscillator to simulate metallic tone
      const osc = ctx.createOscillator();
      osc.type = "sine"; // sine gives a pure tone, metallic
      osc.frequency.value = 900; // higher frequency for metallic tone

      const oscGain = ctx.createGain();
      oscGain.gain.setValueAtTime(0.001, ctx.currentTime);
      oscGain.gain.exponentialRampToValueAtTime(0.3, ctx.currentTime + 0.002);
      oscGain.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.15);

      osc.connect(oscGain);
      oscGain.connect(master);

      // Metallic noise burst (simulating impact transient)
      const bufferSize = ctx.sampleRate * 0.05;
      const noiseBuffer = ctx.createBuffer(1, bufferSize, ctx.sampleRate);
      const data = noiseBuffer.getChannelData(0);
      for (let i = 0; i < bufferSize; i++) {
        // metallic noise with more high frequency
        data[i] = (Math.random() * 2 - 1) * Math.pow(1 - i / bufferSize, 0.3);
      }

      const noiseSource = ctx.createBufferSource();
      noiseSource.buffer = noiseBuffer;

      const noiseGain = ctx.createGain();
      noiseGain.gain.setValueAtTime(0.25, ctx.currentTime);
      noiseGain.gain.exponentialRampToValueAtTime(0.001, ctx.currentTime + 0.06);

      const highpass = ctx.createBiquadFilter();
      highpass.type = "highpass";
      highpass.frequency.value = 2000; // emphasize metallic highs

      noiseSource.connect(highpass);
      highpass.connect(noiseGain);
      noiseGain.connect(master);

      osc.start(ctx.currentTime);
      noiseSource.start(ctx.currentTime);

      osc.stop(ctx.currentTime + 0.15);
      noiseSource.stop(ctx.currentTime + 0.06);

      // Close context after sound plays
      setTimeout(() => {
        try { ctx.close(); } catch (e) {}
      }, 500);
    } catch (e) {
      console.warn("Audio error:", e);
    }
  }

  // --- NEW: create full vertical brackets (left and right) aligned to RU_PX ---
  // These brackets are added inside panelGroup so they move with the panel
  function createVerticalBracket(group, side /* 'left'|'right' */ ) {
    var bracketX;
    var bracketX = (side === 'left')
      ? 0                          // start at panel left edge
      : panelWidth - 20;
    var bracketWidth = side === 'left' ? - (sideRailWidth / 2) : panelWidth + (sideRailWidth / 2);
    // For visuals, create a thin rectangle "bracket strip" on the panel edge
    var strip = new Konva.Rect({
      x: bracketWidth - 10, // slightly inside/outside for visibility
      y: 0,
      width: 20,
      height: panelHeight,
      fill: "black",
      stroke: "#222",
      strokeWidth: 1,
      cornerRadius: 4,
      opacity: 0.9,
      listening: false
    });
    group.add(strip);

    // compute number of RU slots that fit in the railsHeight
    var slots = Math.floor(railsHeight / RU_PX) + 1;
    var holePositions = []; // store relative Y positions within panelGroup (so we can snap to them)
    for (var i = 0; i < slots; i++) {
      // absolute Y of the RU line on the stage
      var absHoleY = railsY + i * RU_PX;
      // now compute the relative Y inside the panelGroup coordinate system
      // panelGroup.y() is variable; but we define the relative pos as absHoleY - panelGroup.y()
      // but at creation time we just set relative placeholders: we'll compute absolute target on drop
      var relY = absHoleY - (panelGroup.y());
      // create a small circle hole representation on the bracket (only if inside the panel height)
      var centerY = relY;
      if (centerY >= 0 && centerY <= panelHeight) {
        var hole = new Konva.Circle({
          x: bracketWidth + (side === 'left' ? 10 : 10), // center of the strip
          y: centerY,
          radius: 6,
          fill: "#fff",
          stroke: "#444",
          strokeWidth: 1,
          id: side + "_bracket_hole_" + i
        });
        group.add(hole);
        holePositions.push({ index: i, relY: centerY });
      } else {
        // still add the logical position so we can snap modules that are dropped outside initial panel rect
        holePositions.push({ index: i, relY: centerY });
      }
    }

    return {
      side: side,
      strip: strip,
      holes: holePositions
    };
  }

  // create both vertical brackets
  var leftBracket = createVerticalBracket(panelGroup, 'left');
  var rightBracket = createVerticalBracket(panelGroup, 'right');

  // --- NEW: create draggable modules in a staging area ---
  var stagingGroup = new Konva.Group({
    x: 20,
    y: railsY + railsHeight + 10, // below rails
    id: "stagingGroup"
  });
  layer.add(stagingGroup);

  function createStagingModule(name, x, y) {
    var w = 60, h = 28;
    var g = new Konva.Group({
      x: x,
      y: y,
      draggable: true,
      id: "module_" + name,
      name: "stagingModule"
    });

    var rect = new Konva.Rect({
      x: 0, y: 0, width: w, height: h, cornerRadius: 6,
      fill: "black", stroke: "#8e4b13", strokeWidth: 2
    });
    var txt = new Konva.Text({
      x: 6, y: 6, text: name, fontSize: 12, fill: "#fff"
    });

    g.add(rect);
    g.add(txt);
    stagingGroup.add(g);

    // drag boundary: keep inside stage
    g.dragBoundFunc(function(pos) {
      var nx = Math.max(0, Math.min(stage.width() - w, pos.x));
      var ny = Math.max(0, Math.min(stage.height() - h, pos.y));
      return { x: nx, y: ny };
    });

    // on dragend we try to snap to nearest bracket hole if close enough
    g.on('dragend', function() {
      var absPos = g.getAbsolutePosition();
      var centerX = absPos.x + w / 2;
      var centerY = absPos.y + h / 2;

      // check proximity to left rail area
      var leftRailZoneX = railsXLeft + sideRailWidth + 20; // a threshold zone to the right of left rail
      var rightRailZoneX = railsXRight - 20; // a threshold zone to the left of right rail

      // helper to compute nearest RU index given an absolute Y
      function nearestRUIndex(absY) {
        var relToRails = absY - railsY;
        var idx = Math.round(relToRails / RU_PX);
        idx = Math.max(0, Math.min(Math.floor(railsHeight / RU_PX), idx));
        return idx;
      }

      // decide which side to attach to (if any)
      var attached = false;
      var attachSide = null;
      var attachIndex = null;

      // if dropped near left side region
      if (centerX < (stageWidth / 2) && centerX < leftRailZoneX + 60) {
        attachSide = 'left';
        attachIndex = nearestRUIndex(centerY);
        attached = true;
      } else if (centerX >= (stageWidth / 2) && centerX > rightRailZoneX - 60) {
        attachSide = 'right';
        attachIndex = nearestRUIndex(centerY);
        attached = true;
      }

      if (attached) {
        // compute absolute Y of the RU target
        var targetAbsY = railsY + attachIndex * RU_PX;
        // compute panelGroup position and the panelGroup.x where bracket is located
        // bracket X inside panelGroup:
        var bracketOffsetX = (attachSide === 'left') ? (panelGroup.x() + (- (sideRailWidth / 2) + 0)) : (panelGroup.x() + (panelWidth + (sideRailWidth / 2) - 60));
        // But simpler: compute desired module absolute X to place it visually on the panel edge
        var targetAbsX;
        if (attachSide === 'left') {
          targetAbsX = panelGroup.x() + (- (sideRailWidth / 2) + 2); // a bit offset to visually sit on bracket
        } else {
          targetAbsX = panelGroup.x() + panelWidth + (sideRailWidth / 2) - 62; // right side
        }

        // threshold: only snap if within 50px vertically of the RU line
        var deltaY = Math.abs(centerY - targetAbsY);
        if (deltaY <= 50) {
          // snap: set module absolute pos so it visually sits centered on the bracket hole
          var newAbsX = targetAbsX;
          var newAbsY = targetAbsY - (h / 2);

          // convert absolute to parent coordinates (stage -> layer)
          // easiest is to remove from stagingGroup and add to layer (or to a dedicated attachedGroup)
          // we'll add to a new group attachedModulesGroup so modules stay on stage and can be controllable
          if (!stage.findOne('#attachedModulesGroup')) {
            var attachedModulesGroup = new Konva.Group({ id: 'attachedModulesGroup' });
            layer.add(attachedModulesGroup);
          }
          var attachedGroup = stage.findOne('#attachedModulesGroup');

          // remove from its parent and add to attachedGroup while preserving absolute position
          g.moveTo(attachedGroup);
          g.position({
            x: newAbsX - attachedGroup.x(),
            y: newAbsY - attachedGroup.y()
          });

          // mark as attached metadata
          g.attrs.attached = true;
          g.attrs.attachSide = attachSide;
          g.attrs.attachIndex = attachIndex;

          // make it non-draggable (or you can allow vertical movement only. Here we lock)
          g.draggable(false);

          // draw a small screw circle to indicate attachment on the bracket
          var screw = new Konva.Circle({
            x: newAbsX + (attachSide === 'left' ? 14 : 46),
            y: newAbsY + (h / 2),
            radius: 5,
            fill: "#f1c40f",
            stroke: "#b8860b",
            strokeWidth: 1,
            id: 'screw_' + g.id()
          });
          attachedGroup.add(screw);

          // play click sound
          playHeavyClick();

          layer.draw();
        } else {
          // not close enough: return module back to staging area (animate)
          g.to({
            x: 10,
            y: 10,
            duration: 0.3,
            onFinish: function() {
              layer.draw();
            }
          });
        }
      } else {
        // not attached: animate back to staging area position
        g.to({
          x: 10,
          y: 10,
          duration: 0.3,
          onFinish: function() {
            layer.draw();
          }
        });
      }
    });

    return g;
  }

  // --- ensure panelGroup draggable stays inside rails vertically and snaps to RU grid as before ---
  var initialGroupY = panelGroup.y();
  var lastSnappedIndex = Math.round((initialGroupY - railsY) / RU_PX);

  panelGroup.dragBoundFunc(function (pos) {
    var xAllowed = this.x();
    var minY = railsY;
    var maxY = railsY + railsHeight - panelHeight;
    var newY = pos.y;
    if (newY < minY) newY = minY;
    if (newY > maxY) newY = maxY;
    return { x: xAllowed, y: newY };
  });

  panelGroup.on("dragend", function () {
    var relY = panelGroup.y() - railsY;
    var snappedRelY = Math.round(relY / RU_PX) * RU_PX;
    snappedRelY = Math.max(0, Math.min(railsHeight - panelHeight, snappedRelY));
    panelGroup.y(railsY + snappedRelY);
    layer.draw();

    var snappedIndex = Math.round(snappedRelY / RU_PX);
    if (snappedIndex !== lastSnappedIndex) {
      lastSnappedIndex = snappedIndex;
      playHeavyClick();
    }

    // After panel moved, we must re-position attached modules (if any) so they visually remain aligned with bracket RU positions
    var attachedGroup = stage.findOne('#attachedModulesGroup');
    if (attachedGroup) {
      var modules = attachedGroup.find('.stagingModule');
      modules.each(function(mod) {
        if (mod.attrs.attached) {
          // recompute absolute target Y for this module based on its attachIndex
          var idx = mod.attrs.attachIndex;
          var targetAbsY = railsY + idx * RU_PX;
          var h = mod.getClientRect().height;
          // reposition maintaining same attachSide
          var targetAbsX;
          if (mod.attrs.attachSide === 'left') {
            targetAbsX = panelGroup.x() + (- (sideRailWidth / 2) + 2);
          } else {
            targetAbsX = panelGroup.x() + panelWidth + (sideRailWidth / 2) - 62;
          }
          // set module absolute position (convert to attachedGroup coords)
          mod.position({
            x: targetAbsX - attachedGroup.x(),
            y: targetAbsY - (h / 2) - attachedGroup.y()
          });
          // also move its screw indicator if present
          var screw = attachedGroup.findOne('#screw_' + mod.id());
          if (screw) {
            screw.position({
              x: targetAbsX + (mod.attrs.attachSide === 'left' ? 14 - attachedGroup.x() : 46 - attachedGroup.x()),
              y: targetAbsY - attachedGroup.y()
            });
          }
        }
      });
    }
  });

  // keyboard arrow behaviour (keeps existing functionality)
  window.addEventListener("keydown", function (e) {
    if (document.activeElement && (document.activeElement.tagName === "INPUT" || document.activeElement.tagName === "TEXTAREA")) return;
    if (e.key === "ArrowUp" || e.key === "ArrowDown") {
      e.preventDefault();
      var dir = e.key === "ArrowUp" ? -1 : 1;
      var currentY = panelGroup.y();
      var relY = currentY - railsY;
      var newRelY = relY + dir * RU_PX;
      newRelY = Math.max(0, Math.min(railsHeight - panelHeight, newRelY));
      panelGroup.y(railsY + newRelY);
      layer.draw();
      var snappedIndex = Math.round(newRelY / RU_PX);
      if (snappedIndex !== lastSnappedIndex) {
        lastSnappedIndex = snappedIndex;
        playHeavyClick();
      }
    }
  });

  layer.draw();
}
