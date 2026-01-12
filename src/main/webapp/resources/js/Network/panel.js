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

function drawPanelDiagram(numRowsFromDb, numColumnsFromDb, controllerID, controllerName, dbId, dbName, rowPerModule, rowCounting, totalModule, statusResult) {

console.log(numRowsFromDb);


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

	// RU size in pixels (1U = 44.45px or 45px)
	var RU_PX = 45; // 1U = 45px, adjust based on your visual preference

	var railsXLeft = 40;
	var railsXRight = stageWidth - railsXLeft - sideRailWidth;
	var railsY = 40;
	var railsHeight = Math.max(stageHeight - 80, panelHeight + 40);

	// Helper function to create the fixed rails with vertically centered holes
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

		// Compute the number of holes that will fit vertically within the rail height
		var holesCount = Math.floor(height / RU_PX); // One hole for each 1U based on rail height

		// Create holes starting from the top inside the rail
		for (var i = 0; i < holesCount; i++) {
			var holeY = y + i * RU_PX + RU_PX / 2; // Start from the top of the rail and center each hole

			var hole = new Konva.Circle({
				x: x + width / 2, // Place hole at the center of the rail
				y: holeY,         // Vertically centered based on 1U intervals from the top
				radius: 6,
				fill: "#ecf0f1",   // Light fill color for the hole
				stroke: "#444",
				strokeWidth: 1
			});

			layer.add(hole);
		}



		return rail;
	}

	// Create the left and right fixed rails with centered holes
	var leftRail = createFixedRail(railsXLeft, railsY, sideRailWidth, railsHeight, "fixedLeftRail");
	var rightRail = createFixedRail(railsXRight, railsY, sideRailWidth, railsHeight, "fixedRightRail");

	// Draw the rails and holes
	layer.draw();


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

	// ---------------------- RU move sound (insert AFTER panelGroup is created) ----------------------
	// Uses your RU_PX and railsY variables and your playHeavyClick() function.
	// It only plays when the panel crosses a new RU while dragging (no sound on grab).
	let _lastRU = null;

	// initialize baseline when user starts dragging (no sound on grab)
	panelGroup.on('dragstart', () => {
		// compute RU index relative to railsY
		const relY = panelGroup.y() - railsY;
		_lastRU = Math.round(relY / RU_PX);
	});

	// during drag, check if RU changed
	panelGroup.on('dragmove', () => {
		// Relative Y position of the panel from the top of the rails
		const relY = panelGroup.y() - railsY;

		// Calculate the closest 1U boundary based on RU_PX
		const currentRU = Math.round(relY / RU_PX);  // Current 1U position based on the Y position

		// Only trigger sound when the rack crosses a new 1U boundary (relative to the last sound trigger)
		if (_lastRU !== currentRU) {
			_lastRU = currentRU;
			playHeavyClick(); // Play sound every time the rack crosses a 1U boundary
		}

		// Optional: Limit movement to 1U increments to ensure precise snapping
		const snappedY = Math.round(relY / RU_PX) * RU_PX;
		panelGroup.y(railsY + snappedY);  // Keep the rack position locked to the 1U boundary during dragging
	});




	// reset on dragend (so next dragstart re-initializes); we DO NOT play sound here
	panelGroup.on("dragend", function() {
		// Calculate relative Y position from the rails top
		const relY = panelGroup.y() - railsY;

		// Snap the position to the nearest 1U (RU_PX)
		const snappedRelY = Math.round(relY / RU_PX) * RU_PX;
		panelGroup.y(railsY + snappedRelY);  // Snap to the nearest 1U position

		// Play sound after snap if the position has changed
		const snappedIndex = Math.round(snappedRelY / RU_PX);
		if (snappedIndex !== lastSnappedIndex) {
			lastSnappedIndex = snappedIndex;
			playHeavyClick(); // Play sound after snapping
		}

		layer.draw();  // Redraw the layer to reflect the changes
	});


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

	panelBackground.on("click", function() {
		alert("Whole panel clicked");
	});

	// existing connector points (kept for compatibility)

	// function to create a simple port (kept) — now accepts numberLabel as 4th parameter
	function createPort(group, x, y, id, numberLabel, statusResult) {
		const portWidth = 20;
		const portHeight = 15;
		const notchWidth = 10;
		const notchHeight = 4;

		// Default fill color
		let fillColor = "";
console.log(statusResult.length);
		// Check if the id exists in statusResult and if its status is 'Active'
		let isConnected = false;
		let isDisConnected = false;
		let isIncomplete = false;
		for (let i = 0; i < statusResult.length; i++) {
		
			if (statusResult[i][0] === id) { // index 0 is id
				if (statusResult[i][1] === "Connected") { // index 1 is status
					fillColor = "#6AA84F";
					isConnected = true;
				}
				else if (statusResult[i][1] === "Disconnected") { // index 1 is status
								fillColor = "black";
								isDisConnected = true;
							}
							else if (statusResult[i][1] === "Incomplete"){
								fillColor = "orange";
								isIncomplete = true;
							}
				break; // id found, no need to continue
			}
		}
		if(fillColor === ""){
			
			fillColor = "black"
		}

		// Create the port shape
		let slotFillColor = isConnected ? "#6AA84F" : isDisConnected ?"black" : isIncomplete ? "orange" : "black";  // Slot color is based on port status
		var port = new Konva.Line({

			points: [
				x, y,
				x + 5, y,
				x + 5, y - notchHeight,
				x + 5 + notchWidth, y - notchHeight,
				x + 5 + notchWidth, y,
				x + portWidth, y,
				x + portWidth, y + portHeight,
				x, y + portHeight
			],
			closed: true,
			stroke: slotFillColor,
			strokeWidth: 1.5,
			fill: fillColor,
			id: id
		});

		group.add(port);

		// Create the slots inside the port
		for (let i = 0; i < 4; i++) {
		
			let slot = new Konva.Rect({
				x: x + 2 + i * 4,
				y: y + 4,
				width: 3,
				height: 9,
				fill: slotFillColor,
				listening: false
			});
			group.add(slot);
		}



		port.on("click", function(e) {

			console.log("Clicked " + this.id());


			$("#portModal").modal('show');



			$.ajax({
				type: "GET",
				url: getContext() + '/findPanelPortData',
				data: {
					"selectedDistBoardContext": selectedDistBoardContext,
					"portIndex": this.id()
				},

				dataType: "json",
				success: function(data) {

					console.log(data);

					if (!data.panelPortData || data.panelPortData.length === 0) {
						const modal = document.getElementById("portModal");

						if (!modal) return;

						// RESET ALL INPUTS
						const allInputs = modal.querySelectorAll("input[type='text'], input[type='number']");
						allInputs.forEach(input => {
							input.value = "";
						});

						// RESET ALL SELECTS
						const allSelects = modal.querySelectorAll("select");
						allSelects.forEach(select => {
							select.selectedIndex = 0;
							select.style.backgroundColor = "white";
						});


						$("#portIndex, #portIndex1").val(id);


						const { row, col } = calculateRowColFromIndex(id, data.panelInfo[2], data.panelInfo[3], data.panelInfo[4], data.panelInfo[0], data.panelInfo[1]);


						$("#portRowNum, #portRowNum1").val(row);

						$("#portColNum, #portColNum1").val(col);




					}

					else {
						// ===================== BASIC PORT INFO =====================
						if (data.panelPortData[0][0] != null) {
							$("#portIndex, #portIndex1").val(data.panelPortData[0][0]);
						}

						if (data.panelPortData[0][1] != null) {
							$("#portRowNum, #portRowNum1").val(data.panelPortData[0][1]);
						}

						if (data.panelPortData[0][2] != null) {
							$("#portColNum, #portColNum1").val(data.panelPortData[0][2]);
						}

						if (data.panelPortData[0][48] != null) {
							$("#portModule, #portModule1").val(data.panelPortData[0][48]);
						}

						if (data.panelPortData[0][49] != null) {
							$("#portNum, #portNum1").val(data.panelPortData[0][49]);
						}

						if (data.panelPortData[0][50] != null) {
							$("#portPatchType, #portPatchType1").val(data.panelPortData[0][50]);
						}


						// ===================== FRONT & BACK STATUS =====================
						if (data.panelPortData[0][4] != null) {
							$("#portFrontStatus").val(data.panelPortData[0][4]);
						}

						if (data.panelPortData[0][14] != null) {
							$("#portBackStatus").val(data.panelPortData[0][14]);
						}


						// ===================== LOCATION TYPE =====================
						if (data.panelPortData[0][5] != null) {
							$("#portFrontLocationType").val(data.panelPortData[0][5]);
						}

						if (data.panelPortData[0][27] != null) {
							$("#portBackLocationType").val(data.panelPortData[0][27]);
						}


						// ===================== LOCATION ID =====================
						if (data.panelPortData[0][6] != null) {
							$("#portFrontLocationID").val(data.panelPortData[0][6]);
						}

						if (data.panelPortData[0][28] != null) {
							$("#portBackLocationID").val(data.panelPortData[0][28]);
						}


						// ===================== LOCATION NAME =====================
						if (data.panelPortData[0][7] != null) {
							$("#portFrontLocationName").val(data.panelPortData[0][7]);
						}

						if (data.panelPortData[0][29] != null) {
							$("#portBackLocationName").val(data.panelPortData[0][29]);
						}


						// ===================== FRONT EQUIPMENT =====================
						if (data.panelPortData[0][10] != null && data.panelPortData[0][10] != 'null') {
							let value = data.panelPortData[0][10];							

							if ($("#portFrontEquipment option[value='" + value + "']").length === 0) {
								$("#portFrontEquipment").append(
									$("<option></option>").val(value).text(value)
								);
							}
							$("#portFrontEquipment").val(value);
						}

						if (data.panelPortData[0][11] != null) {
							$("#portFrontEquipmentID").val(data.panelPortData[0][11]);
						}

						if (data.panelPortData[0][12] != null) {
							$("#portFrontEquipmentName").val(data.panelPortData[0][12]);
						}

						if (data.panelPortData[0][9] != null) {
							$("#portFrontEquipmentType").val(data.panelPortData[0][9]);
						}


						// ===================== BACK EQUIPMENT =====================
						if (data.panelPortData[0][32] != null) {
							let value = data.panelPortData[0][32];

							if ($("#portBackEquipment option[value='" + value + "']").length === 0) {
								$("#portBackEquipment").append(
									$("<option></option>").val(value).text(value)
								);
							}

							$("#portBackEquipment").val(value);
						}

						if (data.panelPortData[0][33] != null) {
							$("#portBackEquipmentID").val(data.panelPortData[0][33]);
						}

						if (data.panelPortData[0][34] != null) {
							$("#portBackEquipmentName").val(data.panelPortData[0][34]);
						}

						if (data.panelPortData[0][31] != null) {
							$("#portBackEquipmentType").val(data.panelPortData[0][31]);
						}

						if (data.panelPortData[0][51] != null) {
							$("#portFrontKitSerialNum").val(data.panelPortData[0][51]);
						}
						
						if (data.panelPortData[0][56] != null) {
							$("#portBackKitSerialNum").val(data.panelPortData[0][56]);
						}						

						if (data.panelPortData[0][54] != null) {
							$("#portBackModule").val(data.panelPortData[0][54]);
						}

						if (data.panelPortData[0][49] != null) {
							$("#PortFrontNum").val(data.panelPortData[0][53]);
							console.log("PortFrontNum is " +data.panelPortData[0][53]);
						}
/*						
						if (data.panelPortData[0][56] != null) {
						$("#PortFrontNum").val(data.panelPortData[0][49]);
						}
*/						

						if (data.panelPortData[0][55] != null) {
							$("#PortBackNum").val(data.panelPortData[0][55]);
						}

						if (data.panelPortData[0][52] != null) {
							$("#portFrontModule").val(data.panelPortData[0][52]);
						}

						if (data.panelPortData[0][13] != null) {
							$("#portFrontAddress").val(data.panelPortData[0][13]);
						}

						if (data.panelPortData[0][34] != null) {
							$("#portBackAddress").val(data.panelPortData[0][34]);
						}

						if (data.panelPortData[0][44] != null) {
							$("#portFrontJunctionID").val(data.panelPortData[0][44]);
						}

						if (data.panelPortData[0][46] != null) {
							$("#portBackJunctionID").val(data.panelPortData[0][46]);
						}

						if (data.panelPortData[0][45] != null) {
							$("#portFrontJunctionName").val(data.panelPortData[0][45]);
						}

						if (data.panelPortData[0][47] != null) {
							$("#portBackJunctionName").val(data.panelPortData[0][47]);
						}

						if (data.panelPortData[0][36] != null) {
							$("#portFrontStrandNumber").val(data.panelPortData[0][36]);
						}

						if (data.panelPortData[0][38] != null) {
							$("#portBackStrandNumber").val(data.panelPortData[0][38]);
						}
						if (data.panelPortData[0][40] != null) {
							$("#PortFrontStrandColor").val(data.panelPortData[0][40]);
							if (data.panelPortData[0][40] != null || data.panelPortData[0][40] != "") {
								strandTubeSetColor(data.panelPortData[0][36], 'PortFrontStrandColor');
							}

						}

						if (data.panelPortData[0][42] != null) {
							$("#PortBackStrandColor").val(data.panelPortData[0][42]);
							if (data.panelPortData[0][42] != null || data.panelPortData[0][42] != "") {
								strandTubeSetColor(data.panelPortData[0][38], 'PortBackStrandColor');
							}

						}

						if (data.panelPortData[0][21] != null) {
							$("#portFrontStrandID").val(data.panelPortData[0][21]);
						}

						if (data.panelPortData[0][15] != null) {
							$("#portBackStrandName").val(data.panelPortData[0][15]);
						}
						if (data.panelPortData[0][22] != null) {
							$("#portFrontStrandName").val(data.panelPortData[0][22]);
						}

						if (data.panelPortData[0][16] != null) {
							$("#portBackStrandID").val(data.panelPortData[0][16]);
						}

						if (data.panelPortData[0][37] != null) {
							$("#portFrontTubeNumber").val(data.panelPortData[0][37]);
						}

						if (data.panelPortData[0][39] != null) {
							$("#portBackTubeNumber").val(data.panelPortData[0][39]);
						}

						if (data.panelPortData[0][41] != null) {
							$("#portFrontTubeColor").val(data.panelPortData[0][41]);
							if (data.panelPortData[0][41] != null || data.panelPortData[0][41] != "") {
								strandTubeSetColor(data.panelPortData[0][37], 'portFrontTubeColor');
							}

						}

						if (data.panelPortData[0][43] != null) {
							$("#portBackTubeColor").val(data.panelPortData[0][43]);
							if (data.panelPortData[0][43] != null || data.panelPortData[0][43] != "") {
								strandTubeSetColor(data.panelPortData[0][39], 'portBackTubeColor');
							}


						}

						if (data.panelPortData[0][23] != null) {
							$("#portFrontTubeID").val(data.panelPortData[0][23]);
						}

						if (data.panelPortData[0][17] != null) {
							$("#portBackTubeID").val(data.panelPortData[0][17]);
						}

						if (data.panelPortData[0][24] != null) {
							$("#portFrontTubeName").val(data.panelPortData[0][24]);
						}

						if (data.panelPortData[0][18] != null) {
							$("#portBackTubeName").val(data.panelPortData[0][18]);
						}

						if (data.panelPortData[0][25] != null) {
							$("#portFrontFiberID").val(data.panelPortData[0][25]);
						}

						if (data.panelPortData[0][19] != null) {
							$("#portBackFiberID").val(data.panelPortData[0][19]);
						}

						if (data.panelPortData[0][26] != null) {
							$("#portFrontFiberName").val(data.panelPortData[0][26]);
						}

						if (data.panelPortData[0][20] != null) {
							$("#portBackFiberName").val(data.panelPortData[0][20]);
						}
					}


					$("#portFrontStrandID").autocomplete({
						source: debounce(function(request, response, event, ui) {

							let sId = $("#portFrontStrandID").val();
							console.log("strand id:", sId);

							$.ajax({
								type: "GET",
								url: getContext() + '/SearchMappingStrand',
								contentType: "application/json; charset=utf-8",
								dataType: "json",
								data: {
									searchId: sId
								},
								success: function(data) {
									if (data != null) {
										console.log(data.glist);
										response(data.glist);
									}
								},
								error: function() {
									alert("Error");
								}
							});

						}, 900),

						minLength: 0,
						maxShowItems: 4,
						scroll: true,

						select: function(event, ui) {

							if (!ui.item) return false;

							this.value = ui.item[0];

							$("#portFrontStrandName").val(ui.item[1]);
							$("#portFrontTubeID").val(ui.item[2]);
							$("#portFrontFiberID").val(ui.item[3]);
							$("#portFrontTubeName").val(ui.item[5]);
							$("#portFrontFiberName").val(ui.item[4]);
							$("#portFrontStrandNumber").val(ui.item[6]);
							$("#portFrontTubeNumber").val(ui.item[8]);

							if (ui.item[7] !== "" && ui.item[7] != null) {

								$("#portFrontTubeColor").val(ui.item[9]);
								$("#PortFrontStrandColor").val(ui.item[7]);

								tubeStrandSetColor("PortFrontStrandColor", "portFrontStrandNumber");
								tubeStrandSetColor("portFrontTubeColor", "portFrontTubeNumber");
							}

							return false;
						}
					})
						.data("ui-autocomplete")._renderItem = function(ul, item) {

							return $("<li class='each'></li>")
								.data("ui-autocomplete-item", item)
								.append(
									"<div class='acItem'>" +
									"<span class='name' style='font-weight:bold'>" + item[0] + "</span><br>" +
									"<span class='desc'>" + item[1] + ", " + item[4] + ", " + item[5] + "</span>" +
									"</div>"
								)
								.appendTo(ul);
						};

					$("#portFrontStrandID").focus(function() {
						if (this.value === "") {
							$(this).autocomplete("search");
						}
					});


					$("#portFrontStrandName").autocomplete({
						source: debounce(function(request, response) {

							let sName = $("#portFrontStrandName").val();

							$.ajax({
								type: "GET",
								url: getContext() + '/SearchMappingStrand',
								contentType: "application/json; charset=utf-8",
								dataType: "json",
								data: {
									"searchId": sName
								},
								success: function(data) {
									if (data != null) {
										response(data.glist);
									}
								},
								error: function() {
									alert("Error");
								}
							});

						}, 900),

						minLength: 0,
						maxShowItems: 4,
						scroll: true,

						select: function(event, ui) {

							if (!ui.item) return false;

							this.value = ui.item[1];

							$("#portFrontStrandID").val(ui.item[0]);
							$("#portFrontTubeID").val(ui.item[2]);
							$("#portFrontFiberID").val(ui.item[3]);
							$("#portFrontTubeName").val(ui.item[5]);
							$("#portFrontFiberName").val(ui.item[4]);
							$("#portFrontStrandNumber").val(ui.item[6]);
							$("#portFrontTubeNumber").val(ui.item[8]);

							// Correct condition AND correct ID names
							if (ui.item[7] !== "" && ui.item[7] != null) {

								$("#portFrontTubeColor").val(ui.item[9]);
								$("#PortFrontStrandColor").val(ui.item[7]); // FIXED CAPITAL LETTER

								// Set colors correctly
								tubeStrandSetColor("PortFrontStrandColor", "portFrontStrandNumber");
								tubeStrandSetColor("portFrontTubeColor", "portFrontTubeNumber");
							}

							return false;
						}
					})
						.data("ui-autocomplete")._renderItem = function(ul, item) {

							return $('<li class="each"></li>')
								.data("ui-autocomplete-item", item)
								.append(
									'<div class="acItem">' +
									'<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
									'<span class="desc">' + item[0] + ', ' + item[4] + ', ' + item[5] + '</span>' +
									'</div>'
								)
								.appendTo(ul);
						};

					// FIXED selector (lowercase p)
					$("#portFrontStrandName").focus(function() {
						if (this.value === "") {
							$(this).autocomplete("search");
						}
					});







					$("#portBackStrandID").autocomplete({
						source: debounce(function(request, response, event, ui) {

							let sId = $("#portBackStrandID").val();
							console.log("strand id:", sId);

							$.ajax({
								type: "GET",
								url: getContext() + '/SearchMappingStrand',
								contentType: "application/json; charset=utf-8",
								dataType: "json",
								data: {
									searchId: sId
								},
								success: function(data) {
									if (data != null) {
										console.log(data.glist);
										response(data.glist);
									}
								},
								error: function() {
									alert("Error");
								}
							});

						}, 900),

						minLength: 0,
						maxShowItems: 4,
						scroll: true,

						select: function(event, ui) {

							if (!ui.item) return false;

							this.value = ui.item[0];

							$("#portBackStrandName").val(ui.item[1]);
							$("#portBackTubeID").val(ui.item[2]);
							$("#portBackFiberID").val(ui.item[3]);
							$("#portBackTubeName").val(ui.item[5]);
							$("#portBackFiberName").val(ui.item[4]);
							$("#portBackStrandNumber").val(ui.item[6]);
							$("#portBackTubeNumber").val(ui.item[8]);

							if (ui.item[7] !== "" && ui.item[7] != null) {

								$("#portBackTubeColor").val(ui.item[9]);
								$("#PortBackStrandColor").val(ui.item[7]);

								tubeStrandSetColor("PortBackStrandColor", "portBackStrandNumber");
								tubeStrandSetColor("portBackTubeColor", "portBackTubeNumber");
							}

							return false;
						}
					})
						.data("ui-autocomplete")._renderItem = function(ul, item) {

							return $("<li class='each'></li>")
								.data("ui-autocomplete-item", item)
								.append(
									"<div class='acItem'>" +
									"<span class='name' style='font-weight:bold'>" + item[0] + "</span><br>" +
									"<span class='desc'>" + item[1] + ", " + item[4] + ", " + item[5] + "</span>" +
									"</div>"
								)
								.appendTo(ul);
						};

					$("#portBackStrandID").focus(function() {
						if (this.value === "") {
							$(this).autocomplete("search");
						}
					});


					$("#portBackStrandName").autocomplete({
						source: debounce(function(request, response) {

							let sName = $("#portBackStrandName").val();

							$.ajax({
								type: "GET",
								url: getContext() + '/SearchMappingStrand',
								contentType: "application/json; charset=utf-8",
								dataType: "json",
								data: {
									"searchId": sName
								},
								success: function(data) {
									if (data != null) {
										response(data.glist);
									}
								},
								error: function() {
									alert("Error");
								}
							});

						}, 900),

						minLength: 0,
						maxShowItems: 4,
						scroll: true,

						select: function(event, ui) {

							if (!ui.item) return false;

							this.value = ui.item[1];

							$("#portBackStrandID").val(ui.item[0]);
							$("#portBackTubeID").val(ui.item[2]);
							$("#portBackFiberID").val(ui.item[3]);
							$("#portBackTubeName").val(ui.item[5]);
							$("#portBackFiberName").val(ui.item[4]);
							$("#portBackStrandNumber").val(ui.item[6]);
							$("#portBackTubeNumber").val(ui.item[8]);

							// Correct condition AND correct ID names
							if (ui.item[7] !== "" && ui.item[7] != null) {

								$("#portBackTubeColor").val(ui.item[9]);
								$("#PortBackStrandColor").val(ui.item[7]); // FIXED CAPITAL LETTER

								// Set colors correctly
								tubeStrandSetColor("PortBackStrandColor", "portBackStrandNumber");
								tubeStrandSetColor("portBackTubeColor", "portBackTubeNumber");
							}

							return false;
						}
					})
						.data("ui-autocomplete")._renderItem = function(ul, item) {

							return $('<li class="each"></li>')
								.data("ui-autocomplete-item", item)
								.append(
									'<div class="acItem">' +
									'<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
									'<span class="desc">' + item[0] + ', ' + item[4] + ', ' + item[5] + '</span>' +
									'</div>'
								)
								.appendTo(ul);
						};

					// FIXED selector (lowercase p)
					$("#portBackStrandName").focus(function() {
						if (this.value === "") {
							$(this).autocomplete("search");
						}
					});








					$("#portFrontFiberID").autocomplete({
						source: debounce(function(request, response, event, ui) {
							console.log("fiber id");
							var fId = $("#portFrontFiberID").val();
							var cId = $("#portFrontTubeID").val();
							var sId = $("#portFrontStrandID").val();
							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fId;
							}
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
										console.log(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});
						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[0] : '');
							$("#portFrontFiberName").val(ui.item[1]);


							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[0] + '</span><br><span class="desc">' +
								item[1] + '</span></div>').appendTo(ul);
					};
					$("#portFrontFiberID").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});
					///////////////////////////////
					$("#portFrontFiberName").autocomplete({
						source: debounce(function(request, response, event, ui) {
							var fName = $("#portFrontFiberName").val();
							var cId = $("#portFrontTubeID").val();
							var sId = $("#portFrontStrandID").val();
							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fName;
							}
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",

								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});
						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {

							this.value = (ui.item ? ui.item[1] : '');
							$("#portFrontFiberID").val(ui.item[0]);


							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[1] + '</span><br><span class="desc">' +
								item[0] + '</span></div>').appendTo(ul);
					};
					$("#portFrontFiberName").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});




					$("#portBackFiberID").autocomplete({
						source: debounce(function(request, response, event, ui) {
							console.log("fiber id");

							var fId = $("#portBackFiberID").val();
							var cId = $("#portBackTubeID").val();
							var sId = $("#portBackStrandID").val();

							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fId;
							}

							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
										console.log(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900),
						minLength: 0,
						maxShowItems: 4,
						scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[0] : '');
							$("#portBackFiberName").val(ui.item[1]);
							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>')
							.data("ui-autocomplete-item", item)
							.append(
								'<div class="acItem">' +
								'<span class="name" style="font-weight:bold">' + item[0] + '</span><br>' +
								'<span class="desc">' + item[1] + '</span>' +
								'</div>'
							)
							.appendTo(ul);
					};

					$("#portBackFiberID").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});

					//////////////////////////////////////////

					$("#portBackFiberName").autocomplete({
						source: debounce(function(request, response, event, ui) {

							var fName = $("#portBackFiberName").val();
							var cId = $("#portBackTubeID").val();
							var sId = $("#portBackStrandID").val();

							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fName;
							}

							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900),
						minLength: 0,
						maxShowItems: 4,
						scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[1] : '');
							$("#portBackFiberID").val(ui.item[0]);
							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>')
							.data("ui-autocomplete-item", item)
							.append(
								'<div class="acItem">' +
								'<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
								'<span class="desc">' + item[0] + '</span>' +
								'</div>'
							)
							.appendTo(ul);
					};

					$("#portBackFiberName").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});







					$("#portFrontTubeID").autocomplete({
						source: debounce(function(request, response, event, ui) {
							var cName = $("#portFrontTubeID").val();
							var sId = $("#portFrontStrandID").val();
							if (sId != "") {
								searchId = sId;

								console.log("tube id");
							} else {
								searchId = cName;
								//url="SearchStrandTube";
							}
							console.log("sid " + searchId);
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",

								url: getContext() + '/SearchTube',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.clist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});
						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[0] : '');
							$("#portFrontFiberID").val(ui.item[2]);
							$("#portFrontTubeName").val(ui.item[1]);
							$("#portFrontFiberName").val(ui.item[3]);
							$("#portFrontTubeNumber").val(ui.item[4]);

							if (ui.item[5] != "" || ui.item[5] != null) {
								$("#portFrontTubeColor").val(ui.item[5]);
								tubeStrandSetColor("portFrontTubeColor", "portFrontTubeNumber");
							}


							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[0] + '</span><br><span class="desc">' +
								item[1] + ', ' + item[3] + '</span></div>').appendTo(ul);
					};
					$("#portFrontTubeID").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});

					$("#portFrontTubeName").autocomplete({
						source: debounce(function(request, response, event, ui) {
							var cName = $("#portFrontTubeName").val();
							var sId = $("#portFrontStrandID").val();
							if (sId != "") {
								searchId = sId;
								//  url="SearchStrandTube";
							} else {
								searchId = cName;
								// url="SearchStrandTube";
							}
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",

								url: getContext() + '/SearchTube',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.clist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[1] : '');

							$("#portFrontFiberID").val(ui.item[2]);
							$("#portFrontTubeID").val(ui.item[0]);
							$("#portFrontFiberName").val(ui.item[3]);
							$("#portFrontTubeNumber").val(ui.item[4]);

							if (ui.item[5] != "" || ui.item[5] != null) {
								$("#portFrontTubeColor").val(ui.item[5]);
								tubeStrandSetColor("portFrontTubeColor", "portFrontTubeNumber");
							}

							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[1] + '</span><br><span class="desc">' +
								item[0] + ', ' + item[3] + '</span></div>').appendTo(ul);
					};
					$("#portFrontTubeName").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});



					$("#portBackTubeID").autocomplete({
						source: debounce(function(request, response, event, ui) {

							var cName = $("#portBackTubeID").val();
							var sId = $("#portBackStrandID").val();

							if (sId != "") {
								searchId = sId;
								console.log("tube id");
							} else {
								searchId = cName;
							}

							console.log("sid " + searchId);

							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchTube',
								data: {
									"searchId": searchId
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.clist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900),
						minLength: 0,
						maxShowItems: 4,
						scroll: true,
						select: function(event, ui) {

							this.value = (ui.item ? ui.item[0] : '');

							$("#portBackFiberID").val(ui.item[2]);
							$("#portBackTubeName").val(ui.item[1]);
							$("#portBackFiberName").val(ui.item[3]);
							$("#portBackTubeNumber").val(ui.item[4]);

							if (ui.item[5] != "" || ui.item[5] != null) {
								$("#portBackTubeColor").val(ui.item[5]);
								tubeStrandSetColor("portBackTubeColor", "portBackTubeNumber");
							}

							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {

						return $('<li class="each"></li>')
							.data("ui-autocomplete-item", item)
							.append(
								'<div class="acItem">' +
								'<span class="name" style="font-weight:bold">' + item[0] + '</span><br>' +
								'<span class="desc">' + item[1] + ', ' + item[3] + '</span>' +
								'</div>'
							)
							.appendTo(ul);
					};

					$("#portBackTubeID").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});

					///////////////////////////////////////////////////////////////

					$("#portBackTubeName").autocomplete({
						source: debounce(function(request, response, event, ui) {

							var cName = $("#portBackTubeName").val();
							var sId = $("#portBackStrandID").val();

							if (sId != "") {
								searchId = sId;
							} else {
								searchId = cName;
							}

							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchTube',
								data: {
									"searchId": searchId
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.clist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900),
						minLength: 0,
						maxShowItems: 4,
						scroll: true,
						select: function(event, ui) {

							this.value = (ui.item ? ui.item[1] : '');

							$("#portBackFiberID").val(ui.item[2]);
							$("#portBackTubeID").val(ui.item[0]);
							$("#portBackFiberName").val(ui.item[3]);
							$("#portBackTubeNumber").val(ui.item[4]);

							if (ui.item[5] != "" || ui.item[5] != null) {
								$("#portBackTubeColor").val(ui.item[5]);
								tubeStrandSetColor("portBackTubeColor", "portBackTubeNumber");
							}

							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {

						return $('<li class="each"></li>')
							.data("ui-autocomplete-item", item)
							.append(
								'<div class="acItem">' +
								'<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
								'<span class="desc">' + item[0] + ', ' + item[3] + '</span>' +
								'</div>'
							)
							.appendTo(ul);
					};

					$("#portBackTubeName").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});

				},
				error: function(result) {
					alert("Error");
				}
			});






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
	// SECOND CASE: rowPerModule specified (Modules numbering logic)
	else
		if ((rowPerModule > 0 && rowPerModule !== "null") && totalModule > 0) {
			numberingArray = [];
			let tmp = [];
			for (let r = 0; r < numRowsFromDb; r++) {
				tmp[r] = new Array(numColumnsFromDb).fill(null);
			}

			let n = 1; // numbering counter

			// Check module orientation
			const dividesRows = (rowPerModule * totalModule === numRowsFromDb);
			const dividesCols = (numColumnsFromDb % totalModule === 0);

			if (dividesRows) {
				// ✅ Modules are stacked by ROWS
				for (let m = 0; m < totalModule; m++) {
					let startRow = m * rowPerModule;
					let endRow = startRow + rowPerModule - 1;

					if (rc === "Up To Down") {
						for (let r = startRow; r <= endRow; r++) {
							for (let c = 0; c < numColumnsFromDb; c++) {
								tmp[r][c] = n++;
							}
						}
					} else { // Down To Up
						for (let r = endRow; r >= startRow; r--) {
							for (let c = 0; c < numColumnsFromDb; c++) {
								tmp[r][c] = n++;
							}
						}
					}
				}
			}

			else if (dividesCols) {
				// ✅ Modules are stacked by COLUMNS
				let colsPerModule = Math.floor(numColumnsFromDb / totalModule);

				for (let m = 0; m < totalModule; m++) {
					let startCol = m * colsPerModule;
					let endCol = startCol + colsPerModule - 1;

					if (rc === "Up To Down") {
						for (let r = 0; r < rowPerModule; r++) {
							for (let c = startCol; c <= endCol; c++) {
								tmp[r][c] = n++;
							}
						}
					} else { // Down To Up
						for (let r = rowPerModule - 1; r >= 0; r--) {
							for (let c = startCol; c <= endCol; c++) {
								tmp[r][c] = n++;
							}
						}
					}
				}
			}

			// ✅ Flatten into numberingArray (same draw order you currently use)
			for (let r = 0; r < numRowsFromDb; r++) {
				for (let c = 0; c < numColumnsFromDb; c++) {
					numberingArray.push(tmp[r][c]);
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
				createPort(panelGroup, x, y, label, label, statusResult);
				usedIndex++;
			} else {
				// original behavior (no numberingArray) — preserve exact behaviour
				createPort(panelGroup, x, y, portCount, portCount, statusResult);
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
				try { ctx.close(); } catch (e) { }
			}, 500);
		} catch (e) {
			console.warn("Audio error:", e);
		}
	}

	// --- NEW: create full vertical brackets (left and right) aligned to RU_PX ---
	// These brackets are added inside panelGroup so they move with the panel
	function createVerticalBracket(group, side /* 'left'|'right' */) {
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
		// store relative Y positions within panelGroup (so we can snap to them)
		for (var i = 0; i < slots; i++) {
			// absolute Y of the RU line on the stage
			var absHoleY = railsY + i * RU_PX;
			// now compute the relative Y inside the panelGroup coordinate system
			// panelGroup.y() is variable; but we define the relative pos as absHoleY - panelGroup.y()
			// but at creation time we just set relative placeholders: we'll compute absolute target on drop
			var relY = absHoleY - (panelGroup.y());
			// create a small circle hole representation on the bracket (only if inside the panel height)
			var centerY = relY;

		}

		return {
			side: side,
			strip: strip,

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

	panelGroup.dragBoundFunc(function(pos) {
		var xAllowed = this.x();
		var minY = railsY;
		var maxY = railsY + railsHeight - panelHeight;
		var newY = pos.y;
		if (newY < minY) newY = minY;
		if (newY > maxY) newY = maxY;
		return { x: xAllowed, y: newY };
	});

	panelGroup.on("dragend", function() {
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
	window.addEventListener("keydown", function(e) {
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
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('portFrontLocationType').addEventListener('change', function() {
		var locationType = this.value;
		var equipmentSelect = document.getElementById('portEquipment');

		// Clear the current options in the Equipment select
		equipmentSelect.innerHTML = '';

		// Create a new option to add to the Equipment select
		var optionNone = document.createElement('option');
		optionNone.value = 'none';
		optionNone.text = 'Select an Option';
		equipmentSelect.appendChild(optionNone);

		// Check if the location type is 'Customer'
		if (locationType === 'Customer') {
			// Add Customer-specific options
			var optionCustom = document.createElement('option');
			optionCustom.value = 'Custom';
			optionCustom.text = 'Custom';
			equipmentSelect.appendChild(optionCustom);
		}

		// Add the common options (Node and DistBoard)
		var optionNode = document.createElement('option');
		optionNode.value = 'Node';
		optionNode.text = 'Node';
		equipmentSelect.appendChild(optionNode);

		var optionDistBoard = document.createElement('option');
		optionDistBoard.value = 'DistBoard';
		optionDistBoard.text = 'DB';
		equipmentSelect.appendChild(optionDistBoard);
	});
});


document.addEventListener('DOMContentLoaded', function() {
	// Get elements
	var locationTypeElement = document.getElementById('portFrontLocationType');
	var equipmentSelect = document.getElementById('portFrontEquipment');

	if (!locationTypeElement) {
		console.error('Element #portFrontLocationType not found!');
		return;
	}
	if (!equipmentSelect) {
		console.error('Element #portFrontEquipment not found!');
		return;
	}

	// Add change listener
	locationTypeElement.addEventListener('change', function() {
		var locationType = this.value;

		// Clear current options
		equipmentSelect.innerHTML = '';

		// Add default option
		var optionNone = document.createElement('option');
		optionNone.value = 'none';
		optionNone.text = 'Select an Option';
		equipmentSelect.appendChild(optionNone);

		// Conditional option
		if (locationType === 'Customer') {
			var optionCustom = document.createElement('option');
			optionCustom.value = 'Custom';
			optionCustom.text = 'Custom';
			equipmentSelect.appendChild(optionCustom);
		}

		// Common options
		var optionNode = document.createElement('option');
		optionNode.value = 'Node';
		optionNode.text = 'Node';
		equipmentSelect.appendChild(optionNode);

		var optionDistBoard = document.createElement('option');
		optionDistBoard.value = 'DistBoard';
		optionDistBoard.text = 'DB';
		equipmentSelect.appendChild(optionDistBoard);
	});
});

document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('PortFrontStrandColor').addEventListener('change', function() {
		tubeStrandSetColor('PortFrontStrandColor', 'portFrontStrandNumber');
	});

	document.getElementById('portFrontStrandNumber').addEventListener('change', function() {
	    const colorElement = document.getElementById('PortFrontStrandColor');
	    const numberElement = document.getElementById('portFrontStrandNumber');
	    tubeStrandNoChange(numberElement, colorElement);
	});

	document.getElementById('portFrontTubeColor').addEventListener('change', function() {
		tubeStrandSetColor('portFrontTubeColor', 'portFrontTubeNumber');
	});

	document.getElementById('portFrontTubeNumber').addEventListener('change', function() {
	    const colorElement = document.getElementById('portFrontTubeColor');
	    const numberElement = document.getElementById('portFrontTubeNumber');
	    tubeStrandNoChange(numberElement, colorElement);
	});
	

});








document.addEventListener('DOMContentLoaded', function() {
	// Get elements
	var locationTypeElement = document.getElementById('portBackLocationType');
	var equipmentSelect = document.getElementById('portBackEquipment');

	if (!locationTypeElement) {
		console.error('Element #portBackLocationType not found!');
		return;
	}
	if (!equipmentSelect) {
		console.error('Element #portBackEquipment not found!');
		return;
	}

	// Add change listener
	locationTypeElement.addEventListener('change', function() {
		var locationType = this.value;

		// Clear current options
		equipmentSelect.innerHTML = '';

		// Add default option
		var optionNone = document.createElement('option');
		optionNone.value = 'none';
		optionNone.text = 'Select an Option';
		equipmentSelect.appendChild(optionNone);

		// Conditional option
		if (locationType === 'Customer') {
			var optionCustom = document.createElement('option');
			optionCustom.value = 'Custom';
			optionCustom.text = 'Custom';
			equipmentSelect.appendChild(optionCustom);
		}

		// Common options
		var optionNode = document.createElement('option');
		optionNode.value = 'Node';
		optionNode.text = 'Node';
		equipmentSelect.appendChild(optionNode);

		var optionDistBoard = document.createElement('option');
		optionDistBoard.value = 'DistBoard';
		optionDistBoard.text = 'DB';
		equipmentSelect.appendChild(optionDistBoard);
	});
});

document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('PortBackStrandColor').addEventListener('change', function() {
		tubeStrandSetColor('PortBackStrandColor', 'portBackStrandNumber');
	});

	document.getElementById('portBackStrandNumber').addEventListener('change', function() {
	    const colorElement = document.getElementById('PortBackStrandColor');
	    const numberElement = document.getElementById('portBackStrandNumber');
	    tubeStrandNoChange(numberElement, colorElement);
	});

	document.getElementById('portBackTubeColor').addEventListener('change', function() {
		tubeStrandSetColor('portBackTubeColor', 'portBackTubeNumber');
	});

	document.getElementById('portBackTubeNumber').addEventListener('change', function() {
		    const colorElement = document.getElementById('portBackTubeColor');
		    const numberElement = document.getElementById('portBackTubeNumber');
		    tubeStrandNoChange(numberElement, colorElement);
		});
});





function savePanelPort() {

	var portIndex = document.getElementById("portIndex").value;
	var portModule = document.getElementById("portModule").value;
	var portRowNum = document.getElementById("portRowNum").value;
	var portColNum = document.getElementById("portColNum").value;
	var portNum = document.getElementById("portNum").value;
	var portPatchType = document.getElementById("portPatchType").value;

	var portFrontStatus = document.getElementById("portFrontStatus").value;
	var portFrontLocationType = document.getElementById("portFrontLocationType").value;
	var portFrontLocationID = document.getElementById("portFrontLocationID").value;
	var portFrontLocationName = document.getElementById("portFrontLocationName").value;

	var portFrontEquipment = document.getElementById("portFrontEquipment").value;
	var portFrontEquipmentID = document.getElementById("portFrontEquipmentID").value;
	var portFrontEquipmentName = document.getElementById("portFrontEquipmentName").value;
	var portFrontEquipmentType = document.getElementById("portFrontEquipmentType").value;

	var portFrontKitSerialNum = document.getElementById("portFrontKitSerialNum").value;
	var portFrontModuleValue = document.getElementById("portFrontModule").value;
	var portFrontNum = document.getElementById("PortFrontNum").value;

	var portFrontAddress = document.getElementById("portFrontAddress").value;
	var portFrontJunctionID = document.getElementById("portFrontJunctionID").value;
	var portFrontJunctionName = document.getElementById("portFrontJunctionName").value;

	var portFrontStrandNumber = document.getElementById("portFrontStrandNumber").value;
	var portFrontStrandColor = document.getElementById("PortFrontStrandColor").value;
	var portFrontStrandID = document.getElementById("portFrontStrandID").value;
	var portFrontStrandName = document.getElementById("portFrontStrandName").value;

	var portFrontTubeNumber = document.getElementById("portFrontTubeNumber").value;
	var portFrontTubeColor = document.getElementById("portFrontTubeColor").value;
	var portFrontTubeID = document.getElementById("portFrontTubeID").value;
	var portFrontTubeName = document.getElementById("portFrontTubeName").value;

	var portFrontFiberID = document.getElementById("portFrontFiberID").value;
	var portFrontFiberName = document.getElementById("portFrontFiberName").value;

	var portIndex1 = document.getElementById("portIndex1").value;
	var portModule1 = document.getElementById("portModule1").value;
	var portRowNum1 = document.getElementById("portRowNum1").value;
	var portColNum1 = document.getElementById("portColNum1").value;
	var portNum1 = document.getElementById("portNum1").value;
	var portPatchType1 = document.getElementById("portPatchType1").value;

	var portBackStatus = document.getElementById("portBackStatus").value;
	var portBackLocationType = document.getElementById("portBackLocationType").value;
	var portBackLocationID = document.getElementById("portBackLocationID").value;
	var portBackLocationName = document.getElementById("portBackLocationName").value;

	var portBackEquipment = document.getElementById("portBackEquipment").value;
	var portBackEquipmentID = document.getElementById("portBackEquipmentID").value;
	var portBackEquipmentName = document.getElementById("portBackEquipmentName").value;
	var portBackEquipmentType = document.getElementById("portBackEquipmentType").value;

	var portBackKitSerialNum = document.getElementById("portBackKitSerialNum").value;
	var portBackModule = document.getElementById("portBackModule").value;
	//var portBackModuleValue = document.getElementById("portBackModule").value;
	var portBackNum = document.getElementById("PortBackNum").value;

	var portBackAddress = document.getElementById("portBackAddress").value;
	var portBackJunctionID = document.getElementById("portBackJunctionID").value;
	var portBackJunctionName = document.getElementById("portBackJunctionName").value;

	var portBackStrandNumber = document.getElementById("portBackStrandNumber").value;
	var portBackStrandColor = document.getElementById("PortBackStrandColor").value;
	var portBackStrandID = document.getElementById("portBackStrandID").value;
	var portBackStrandName = document.getElementById("portBackStrandName").value;

	var portBackTubeNumber = document.getElementById("portBackTubeNumber").value;
	var portBackTubeColor = document.getElementById("portBackTubeColor").value;
	var portBackTubeID = document.getElementById("portBackTubeID").value;
	var portBackTubeName = document.getElementById("portBackTubeName").value;

	var portBackFiberID = document.getElementById("portBackFiberID").value;
	var portBackFiberName = document.getElementById("portBackFiberName").value;


	var token = $('input[name="csrfToken"]').attr('value');
	$.ajax({
		type: "POST",
		headers: {
			'X-CSRFToken': token
		},
		url: getContext() + '/savePanelPort',
		data: {

			"selectedDistBoardContext": selectedDistBoardContext,
			// ---------- FRONT ----------
			"portIndex": portIndex,
			"portModule": portModule,
			"portRowNum": portRowNum,
			"portColNum": portColNum,
			"portNum": portNum,
			"portPatchType": portPatchType,

			"portFrontStatus": portFrontStatus,
			"portFrontLocationType": portFrontLocationType,
			"portFrontLocationID": portFrontLocationID,
			"portFrontLocationName": portFrontLocationName,

			"portFrontEquipment": portFrontEquipment,
			"portFrontEquipmentID": portFrontEquipmentID,
			"portFrontEquipmentName": portFrontEquipmentName,
			"portFrontEquipmentType": portFrontEquipmentType,

			"portFrontKitSerialNum": portFrontKitSerialNum,
			"portFrontModuleValue": portFrontModuleValue,
			"portFrontNum": portFrontNum,

			"portFrontAddress": portFrontAddress,
			"portFrontJunctionID": portFrontJunctionID,
			"portFrontJunctionName": portFrontJunctionName,

			"portFrontStrandNumber": portFrontStrandNumber,
			"portFrontStrandColor": portFrontStrandColor,
			"portFrontStrandID": portFrontStrandID,
			"portFrontStrandName": portFrontStrandName,

			"portFrontTubeNumber": portFrontTubeNumber,
			"portFrontTubeColor": portFrontTubeColor,
			"portFrontTubeID": portFrontTubeID,
			"portFrontTubeName": portFrontTubeName,

			"portFrontFiberID": portFrontFiberID,
			"portFrontFiberName": portFrontFiberName,

			// ---------- BACK ----------
			"portIndex1": portIndex1,
			"portModule1": portModule1,
			"portRowNum1": portRowNum1,
			"portColNum1": portColNum1,
			"portNum1": portNum1,
			"portPatchType1": portPatchType1,

			"portBackStatus": portBackStatus,
			"portBackLocationType": portBackLocationType,
			"portBackLocationID": portBackLocationID,
			"portBackLocationName": portBackLocationName,

			"portBackEquipment": portBackEquipment,
			"portBackEquipmentID": portBackEquipmentID,
			"portBackEquipmentName": portBackEquipmentName,
			"portBackEquipmentType": portBackEquipmentType,

			"portBackModule": portBackModule,
			//"portBackModuleValue" : portBackModuleValue,
			"portBackNum": portBackNum,
			"portBackKitSerialNum" :portBackKitSerialNum,

			"portBackAddress": portBackAddress,
			"portBackJunctionID": portBackJunctionID,
			"portBackJunctionName": portBackJunctionName,

			"portBackStrandNumber": portBackStrandNumber,
			"portBackStrandColor": portBackStrandColor,
			"portBackStrandID": portBackStrandID,
			"portBackStrandName": portBackStrandName,

			"portBackTubeNumber": portBackTubeNumber,
			"portBackTubeColor": portBackTubeColor,
			"portBackTubeID": portBackTubeID,
			"portBackTubeName": portBackTubeName,

			"portBackFiberID": portBackFiberID,
			"portBackFiberName": portBackFiberName

		},
		dataType: "json",
		success: function(data) {

			if (data != null) {

				$("#portModal").modal('hide');
				var numRowsFromDb = Number(data.numRows || 0);
				var numColumnsFromDb = Number(data.numColumns || 0);
				drawPanelDiagram(numRowsFromDb, numColumnsFromDb, data.controllerID, data.controllerName, data.dbId,
					data.dbName, data.rowPerModule, data.rowCounting, data.totalNumModule, data.statusResult);


			}
		},
		error: function(result) {
			alert("Error");
		}
	});

}






