function DBMappingData(DistBoardMappingPts, panelInfo) {
    var rowPerModule = panelInfo[0];
    var totalModules = panelInfo[1];
    if (DistBoardMappingPts) {
        window["DB_Mapper" + selectedDistBoardContext] = [];
        window["DB_Mapper" + selectedDistBoardContext] = DistBoardMappingPts;

        const numRows = parseInt($("#DistributionBoardRowsNum").val());
        const numCols = parseInt($("#DistributionBoardColsNum").val());
        const direction = $("#rowCounting").val().toLowerCase();

        dBBoqIndex = 0;
        let markup = "";
        let port_Location = "Select an Option";

        for (i = 0;i < DistBoardMappingPts.length;i++) {

            const fpLocationOptions = generateLocationOptions(DistBoardMappingPts[i][5]);
            if (DistBoardMappingPts[i][5] != null && DistBoardMappingPts[i][5] != 'null' && DistBoardMappingPts[i][5] != "")
                port_Location = DistBoardMappingPts[i][5];
            const fpEquipmentOptions = generateEquipmentOptions(port_Location, DistBoardMappingPts[i][10]);

            // BP
            const bpLocationOptions = generateLocationOptions(DistBoardMappingPts[i][27]);
            if (DistBoardMappingPts[i][27] != null && DistBoardMappingPts[i][27] != 'null' && DistBoardMappingPts[i][27] != "")
                port_Location = DistBoardMappingPts[i][27];
            const bpEquipmentOptions = generateEquipmentOptions(port_Location, DistBoardMappingPts[i][32]);

            const fpLocType = DistBoardMappingPts[i][5];
            const fpLocationReadonly =
                (fpLocType === "Manhole" || fpLocType === "Handhole" || fpLocType === "Customer") ? "readonly" : "";

            const bpLocType = DistBoardMappingPts[i][27];
            const bpLocationReadonly = (bpLocType === "Manhole" || bpLocType === "Handhole" || bpLocType === "Customer") ? "readonly" : "";

            const fpEqIsDist = DistBoardMappingPts[i][10] === "DistBoard";
            const fpReadonly = fpEqIsDist ? "" : "readonly";

            const bpEqIsDist = DistBoardMappingPts[i][32] === "DistBoard";
            const bpReadonly = bpEqIsDist ? "" : "readonly";

            const fpStrandNumber = (DistBoardMappingPts[i][36] === null || DistBoardMappingPts[i][36] === undefined || DistBoardMappingPts[i][36] === "null" ? "" : DistBoardMappingPts[i][36]);
            const fpStrandData = getColorByNumber(fpStrandNumber);
            const fpStrandColor = fpStrandData.color;
            const fpStrandTextColor = fpStrandData.text;

            const fpTubeNumber = (DistBoardMappingPts[i][37] === null || DistBoardMappingPts[i][37] === undefined || DistBoardMappingPts[i][37] === "null" ? "" : DistBoardMappingPts[i][37]);
            const fpTubeData = getColorByNumber(fpTubeNumber);
            const fpTubeColor = fpTubeData.color;
            const fpTubeTextColor = fpTubeData.text;

            const bpStrandNumber = (DistBoardMappingPts[i][38] === null || DistBoardMappingPts[i][38] === undefined || DistBoardMappingPts[i][38] === "null" ? "" : DistBoardMappingPts[i][38]);
            const bpStrandData = getColorByNumber(bpStrandNumber);
            const bpStrandColor = bpStrandData.color;
            const bpStrandTextColor = bpStrandData.text;

            const bpTubeNumber = (DistBoardMappingPts[i][39] === null || DistBoardMappingPts[i][39] === undefined || DistBoardMappingPts[i][39] === "null" ? "" : DistBoardMappingPts[i][39]);
            const bpTubeData = getColorByNumber(bpTubeNumber);
            const bpTubeColor = bpTubeData.color;
            const bpTubeTextColor = bpTubeData.text;

            window["DB_" + DistBoardMappingPts[i][3]] = [];
            window["DB_" + DistBoardMappingPts[i][3]] = DistBoardMappingPts[i];

            var f_statusOption = "", b_statusOption = "";
            if (DistBoardMappingPts[i][4] == "Connected") {
                f_statusOption = "<option value='Connected' selected >Connected</option><option value='Disconnected'>Disconnected</option><option value='Incomplete'>Incomplete</option>";
            }
            else if (DistBoardMappingPts[i][4] == "Disconnected") {
                f_statusOption = "<option value='Disconnected' selected >Disconnected</option><option value='Connected'>Connected</option><option value='Incomplete'>Incomplete</option>";
            }
            else if (DistBoardMappingPts[i][4] == "Incomplete") {
                f_statusOption = "<option value='Incomplete' selected >Incomplete</option><option value='Connected'>Connected</option><option value='Disconnected'>Disconnected</option>";
            }
            else {
                f_statusOption = "<option value='None' selected>Select an Option</option><option value='Connected' >Connected</option><option value='Disconnected'>Disconnected</option><option value='Incomplete'>Incomplete</option>";
            }

            if (DistBoardMappingPts[i][14] == "Connected") {
                b_statusOption = "<option value='Connected' selected >Connected</option><option value='Disconnected'>Disconnected</option><option value='Incomplete'>Incomplete</option>";
            }
            else if (DistBoardMappingPts[i][14] == "Disconnected") {
                b_statusOption = "<option value='Disconnected' selected >Disconnected</option><option value='Connected'>Connected</option><option value='Incomplete'>Incomplete</option>";
            }
            else if (DistBoardMappingPts[i][14] == "Incomplete") {
                b_statusOption = "<option value='Incomplete' selected >Incomplete</option><option value='Connected'>Connected</option><option value='Disconnected'>Disconnected</option>";
            }
            else {
                b_statusOption = "<option value='None' >Select an Option</option><option value='Connected' >Connected</option><option value='Disconnected'>Disconnected</option><option value='Incomplete'>Incomplete</option>";
            }
            let backKitVal = DistBoardMappingPts[i][54] ? DistBoardMappingPts[i][54] : "";
            let backPortVal = DistBoardMappingPts[i][55] ? DistBoardMappingPts[i][55] : "";

            markup += "<tr id='" + DistBoardMappingPts[i][3] + "'><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='Index' class='headcol'><input id='index" + i + "' name='Index' value='" + DistBoardMappingPts[i][0] + "' class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
                + "<td name='nearModule'><input name='nearModule' value='" + DistBoardMappingPts[i][48] + "' class='form-control text-input' type='text' style='width:70px;position:relative;'/></td>"
                + "<td name='nearPortNum'> <input id='nearPortNum" + i + "' name='nearPortNum' value='" + DistBoardMappingPts[i][49] +
                "' class='form-control text-input' type='text' style='width:70px;position:relative;'/>" +
                "</td>" +
                "<td name='RowIndex'>" +
                "<input id='rowIndex" + i + "' name='rowIndex' value='" + DistBoardMappingPts[i][1] +
                "' class='form-control text-input rowIndex' type='text' style='width:60px;position:relative;'/>" +
                "</td>" +
                "<td name='ColIndex'>" +
                "<input id='colIndex" + i + "' name='colIndex' value='" + DistBoardMappingPts[i][2] +
                "' class='form-control text-input colIndex' type='text' style='width:60px;position:relative;'/>" +
                "</td>"
                + "<td name='patchType'><input name='patchType' value='"
                + (DistBoardMappingPts[i][50] === null || DistBoardMappingPts[i][50] === undefined || DistBoardMappingPts[i][50] === "null" ? "" : DistBoardMappingPts[i][50])
                + "' class='form-control text-input portIndex' type='text' style='width:240px;position:relative;'/></td>"
                + "<td style='background-color:#00757C' width='-10px'></td>"
                + "<td name='FP_Status'><select class='form-control' name='FP_Status' id='FP_Status" + dBBoqIndex + "'>" + f_statusOption + "</select></td>"
                + "<td name='FP_LocationType'><select class='form-control' name='FP_locationType' id='FP_LocationType" + dBBoqIndex + "'>"
                + fpLocationOptions + "</select></td>"
                + "<td name='FP_LocationID'><input name='FP_locationID' value='" + DistBoardMappingPts[i][6] + "' id='FP_LocationID" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_LocationM'><input name='FP_locationM' value='" + DistBoardMappingPts[i][7] + "' id='FP_LocationM" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_Location'><input name='FP_location' value='" + DistBoardMappingPts[i][8] + "' id='FP_Location" + dBBoqIndex + "' class='form-control' type='text' "
                + fpLocationReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='FP_Equipment'>"
                + "<select class='form-control' name='FP_equipment' id='FP_equipment" + dBBoqIndex + "'>" + fpEquipmentOptions + "</select></td>"
                + "<td name='FP_EquipmentID'><input name='FP_equipmentID' value='" + DistBoardMappingPts[i][11] + "' id='FP_equipmentID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_EquipmentName'><input name='FP_equipmentName' value='" + DistBoardMappingPts[i][12] + "' id='FP_equipmentName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_EquipmentType'><input name='FP_equipmentType' value='" + DistBoardMappingPts[i][9] + "' id='FP_equipmentType" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='farKitSerialNum'><input name='farKitSerialNum' value='"
                + (DistBoardMappingPts[i][51] === null || DistBoardMappingPts[i][51] === undefined || DistBoardMappingPts[i][51] === "null"
                    ? "" : DistBoardMappingPts[i][51])
                + "' id='farKitSerialNum" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + fpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='farModule'><input name='farModule' value='"
                + (DistBoardMappingPts[i][52] === null || DistBoardMappingPts[i][52] === undefined || DistBoardMappingPts[i][52] === "null"
                    ? "" : DistBoardMappingPts[i][52])
                + "' id='farModule" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + fpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='farPortNum'><input name='farPortNum' value='"
                + (DistBoardMappingPts[i][53] === null || DistBoardMappingPts[i][53] === undefined || DistBoardMappingPts[i][53] === "null"
                    ? "" : DistBoardMappingPts[i][53])
                + "' id='farPortNum" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + fpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='FP_Address'><input name='FP_Address' value='"
                + (DistBoardMappingPts[i][13] === null || DistBoardMappingPts[i][13] === undefined || DistBoardMappingPts[i][13] === "null"
                    ? "" : DistBoardMappingPts[i][13])
                + "' id='FP_Address" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_JunctionID'><input name='FP_junctionID' value='" + DistBoardMappingPts[i][44] + "' id='FP_junctionID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_JunctionName'><input name='FP_junctionName' value='" + DistBoardMappingPts[i][45] + "' id='FP_junctionName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_StrandNb'><input name='FP_strandNb' value='" + fpStrandNumber + "' id='FP_strandNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='FP_StrandColor'>"
                + "<select class='form-control' name='FP_strandcolor' id='FP_strandcolor" + dBBoqIndex + "' "
                + "style='background-color:" + fpStrandColor + "; color:" + fpStrandTextColor + "'>"
                + colorOptions(fpStrandColor)
                + "</select>"
                + "</td>"
                + "<td name='FP_StrandID'><input name='FP_strandID' value='"
                + (DistBoardMappingPts[i][21] === null || DistBoardMappingPts[i][21] === undefined || DistBoardMappingPts[i][21] === "null"
                    ? "" : DistBoardMappingPts[i][21])
                + "' id='FP_strandID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_StrandName'><input name='FP_strandName' value='"
                + (DistBoardMappingPts[i][22] === null || DistBoardMappingPts[i][22] === undefined || DistBoardMappingPts[i][22] === "null"
                    ? "" : DistBoardMappingPts[i][22])
                + "' id='FP_strandName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_TubeNb'><input name='FP_tubeNb' value='" + fpTubeNumber + "' id='FP_tubeNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='FP_TubeColor'>"
                + "<select class='form-control' name='FP_tubecolor' id='FP_tubecolor" + dBBoqIndex + "' "
                + "style='background-color:" + fpTubeColor + "; color:" + fpTubeTextColor + "'>"
                + colorOptions(fpTubeColor)
                + "</select>"
                + "</td>"
                + "<td name='FP_TubeID'><input name='FP_tubeID' value='"
                + (DistBoardMappingPts[i][23] === null || DistBoardMappingPts[i][23] === undefined || DistBoardMappingPts[i][23] === "null"
                    ? "" : DistBoardMappingPts[i][23])
                + "' id='FP_tubeID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_TubeName'><input name='FP_tubeName' value='"
                + (DistBoardMappingPts[i][24] === null || DistBoardMappingPts[i][24] === undefined || DistBoardMappingPts[i][24] === "null"
                    ? "" : DistBoardMappingPts[i][24])
                + "' id='FP_tubeName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_FiberID'><input name='FP_fiberID' value='"
                + (DistBoardMappingPts[i][25] === null || DistBoardMappingPts[i][25] === undefined || DistBoardMappingPts[i][25] === "null"
                    ? "" : DistBoardMappingPts[i][25])
                + "' id='FP_fiberID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_FiberName'><input name='FP_fiberName' value='"
                + (DistBoardMappingPts[i][26] === null || DistBoardMappingPts[i][26] === undefined || DistBoardMappingPts[i][26] === "null"
                    ? "" : DistBoardMappingPts[i][26])
                + "' id='FP_fiberName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td style='background-color:#00757C'></td>"
                + "<td name='BP_Status'><select class='form-control' name='BP_Status' id='BP_Status" + dBBoqIndex + "'>" + b_statusOption + "</select></td>"
                + "<td name='BP_LocationType'><select class='form-control' name='BP_locationType' id='BP_LocationType" + dBBoqIndex + "'>"
                + bpLocationOptions + "</select></td>"
                + "<td name='BP_LocationID'><input name='BP_locationID' value='" + DistBoardMappingPts[i][28] + "' id='BP_LocationID" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_LocationM'><input name='BP_locationM' value='" + DistBoardMappingPts[i][29] + "' id='BP_LocationM" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_Location'><input name='BP_location' value='" + DistBoardMappingPts[i][30] + "' id='BP_Location" + dBBoqIndex + "' class='form-control' type='text' "
                + bpLocationReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='BP_Equipment'>"
                + "<select class='form-control' name='BP_equipment' id='BP_equipment" + dBBoqIndex + "'>" + bpEquipmentOptions + "</select>"
                + "<td name='BP_EquipmentID'><input name='BP_equipmentID' value='" + DistBoardMappingPts[i][33] + "' id='BP_equipmentID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_EquipmentName'><input name='BP_equipmentName' value='" + DistBoardMappingPts[i][34] + "' id='BP_equipmentName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_EquipmentType'><input name='BP_equipmentType' value='" + DistBoardMappingPts[i][31] + "' id='BP_equipmentType" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='backKitModule'><input name='backKitModule' value='"
                + (backKitVal === null || backKitVal === undefined || backKitVal === "null"
                    ? "" : backKitVal)
                + "' id='backKitModule" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + bpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='backPortNum'><input name='backPortNum' value='" + backPortVal + "' id='backPortNum" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + bpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='BP_Address'><input name='BP_Address' value='"
                + (DistBoardMappingPts[i][35] === null || DistBoardMappingPts[i][35] === undefined || DistBoardMappingPts[i][35] === "null" ? "" : DistBoardMappingPts[i][35])
                + "'id='BP_Address" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_JunctionID'><input name='BP_junctionID' value='" + DistBoardMappingPts[i][46] + "' id='BP_junctionID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_JunctionName'><input name='BP_junctionName' value='" + DistBoardMappingPts[i][47] + "' id='BP_junctionName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_StrandNb'><input name='BP_strandNb' value='" + bpStrandNumber + "' id='BP_strandNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='BP_StrandColor'>"
                + "<select class='form-control' name='BP_strandcolor' id='BP_strandcolor" + dBBoqIndex + "' "
                + "style='background-color:" + bpStrandColor + "; color:" + bpStrandTextColor + "'>"
                + colorOptions(bpStrandColor)
                + "</select>"
                + "</td>"
                + "<td name='BP_StrandID'><input name='BP_strandID' value='"
                + (DistBoardMappingPts[i][15] === null || DistBoardMappingPts[i][15] === undefined || DistBoardMappingPts[i][15] === "null"
                    ? "" : DistBoardMappingPts[i][15])
                + "' id='BP_strandID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_StrandName'><input name='BP_strandName' value='"
                + (DistBoardMappingPts[i][16] === null || DistBoardMappingPts[i][16] === undefined || DistBoardMappingPts[i][16] === "null"
                    ? "" : DistBoardMappingPts[i][16])
                + "' id='BP_strandName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_TubeNb'><input name='BP_tubeNb' value='" + bpTubeNumber + "' id='BP_tubeNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='BP_TubeColor'>"
                + "<select class='form-control' name='BP_tubecolor' id='BP_tubecolor" + dBBoqIndex + "' "
                + "style='background-color:" + bpTubeColor + "; color:" + bpTubeTextColor + "'>"
                + colorOptions(bpTubeColor)
                + "</select>"
                + "</td>"
                + "<td name='BP_TubeID'><input name='BP_tubeID' value='"
                + (DistBoardMappingPts[i][17] === null || DistBoardMappingPts[i][17] === undefined || DistBoardMappingPts[i][17] === "null"
                    ? "" : DistBoardMappingPts[i][17])
                + "' id='BP_tubeID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_TubeName'><input name='BP_tubeName' value='"
                + (DistBoardMappingPts[i][18] === null || DistBoardMappingPts[i][18] === undefined || DistBoardMappingPts[i][18] === "null"
                    ? "" : DistBoardMappingPts[i][18])
                + "' id='BP_tubeName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_FiberID'><input name='BP_fiberID' value='"
                + (DistBoardMappingPts[i][19] === null || DistBoardMappingPts[i][19] === undefined || DistBoardMappingPts[i][19] == "null"
                    ? "" : DistBoardMappingPts[i][19])
                + "' id='BP_fiberID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_FiberName'><input name='BP_fiberName' value='"
                + (DistBoardMappingPts[i][20] === null || DistBoardMappingPts[i][20] === undefined || DistBoardMappingPts[i][20] === "null"
                    ? "" : DistBoardMappingPts[i][20])
                + "' id='BP_fiberName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td></tr>"
            dBBoqIndex++;
        }
        $("#DbMappingTable > tbody").html(markup);

        indexRowColEvents({
            numRows,
            numCols,
            direction,
            rowPerModule,
            totalModules
        });
        dbAutoCompleteForMapping();
        dbTubeStrandNoColor();
        locationTypeChange();
        equipmentChange();
    }
}

function getColorByNumber(num) {
    numberMap = {
        "0": { color: "", text: "" },
        "1": { color: "blue", text: "white" },
        "2": { color: "orange", text: "white" },
        "3": { color: "green", text: "white" },
        "4": { color: "brown", text: "white" },
        "5": { color: "gray", text: "white" },
        "6": { color: "white", text: "black" },
        "7": { color: "red", text: "white" },
        "8": { color: "black", text: "white" },
        "9": { color: "yellow", text: "black" },
        "10": { color: "violet", text: "white" },
        "11": { color: "pink", text: "black" },
        "12": { color: "aqua", text: "black" }
    };

    return numberMap[num] || { color: "", text: "" };
}

function colorOptions(selectedColor) {
    return Object.entries(numberMap)
        .map(([number, info]) => {
            const selected = info.color === selectedColor ? "selected" : "";
            return `<option value="${info.color}" ${selected}>${info.color}</option>`;
        })
        .join("");
}

function dbAutoCompleteForMapping() {
    console.log("Welcome to dbAutoCompleteForMapping");
    const fields = [
        { name: 'FP_FiberID', func: dbAutoCompleteFiberID },
        { name: 'BP_FiberID', func: dbAutoCompleteFiberID },
        { name: 'FP_FiberName', func: dbAutoCompleteFiberName },
        { name: 'BP_FiberName', func: dbAutoCompleteFiberName },
        { name: 'FP_TubeID', func: dbAutoCompleteTubeID },
        { name: 'BP_TubeID', func: dbAutoCompleteTubeID },
        { name: 'FP_TubeName', func: dbAutoCompleteTubeName },
        { name: 'BP_TubeName', func: dbAutoCompleteTubeName },
        { name: 'FP_StrandID', func: dbAutoCompleteStrandID },
        { name: 'BP_StrandID', func: dbAutoCompleteStrandID },
        { name: 'FP_StrandName', func: dbAutoCompleteStrandName },
        { name: 'BP_StrandName', func: dbAutoCompleteStrandName },
        { name: 'FP_EquipmentID', func: dbAutoCompleteEquipmentID },
        { name: 'BP_EquipmentID', func: dbAutoCompleteEquipmentID },
        { name: 'FP_EquipmentName', func: dbAutoCompleteEquipmentName },
        { name: 'BP_EquipmentName', func: dbAutoCompleteEquipmentName },
        { name: 'FP_EquipmentType', func: dbAutoCompleteEquipmentType },
        { name: 'BP_EquipmentType', func: dbAutoCompleteEquipmentType },
        { name: 'FP_JunctionID', func: dbAutoCompleteJunctionID },
        { name: 'BP_JunctionID', func: dbAutoCompleteJunctionID },
        { name: 'FP_JunctionName', func: dbAutoCompleteJunctionName },
        { name: 'BP_JunctionName', func: dbAutoCompleteJunctionName },
        { name: 'FP_LocationID', func: dbAutoCompleteLocationID },
        { name: 'FP_LocationM', func: dbAutoCompleteLocationM }
    ];

    fields.forEach(f => {
        $("#DbMappingTable").on("focus", `td[name='${f.name}'] input`, function() {
            let $input = $(this);
            if (!$input.data("ui-autocomplete")) {
                f.func($input);
            }
        });
    });
}

function dbAutoCompleteFiberID(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            console.log("fiber id");
            // Always read values dynamically
            let fiberID = input.attr("id");
            let fId = input.val();
            let tId = "";
            let sId = "";
            let row = input.closest("tr");

            if (fiberID.startsWith("FP")) {
                tId = row.find("input[name='FP_tubeID']").val();
                sId = row.find("input[name='FP_strandID']").val();
            }
            else if (fiberID.startsWith("BP")) {
                tId = row.find("input[name='BP_tubeID']").val();
                sId = row.find("input[name='BP_strandID']").val();
            }

            let searchId = sId || tId || fId;

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
            // Set the next td's input value
            $(this).closest("td").next().find('input').val(ui.item[1]);
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>').data("ui-autocomplete-item", item)
            .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                item[0] + '</span><br><span class="desc">' +
                item[1] + '</span></div>').appendTo(ul);
    };
    input.focus(function() {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });
}

function dbAutoCompleteFiberName(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            // Always read values dynamically
            let fiberNameID = input.attr("id");
            let fName = input.val();
            let tId = "";
            let sId = "";
            let row = input.closest("tr");

            if (fiberNameID.startsWith("FP")) {
                tId = row.find("input[name='FP_tubeID']").val();
                sId = row.find("input[name='FP_strandID']").val();
            }
            else if (fiberNameID.startsWith("BP")) {
                tId = row.find("input[name='BP_tubeID']").val();
                sId = row.find("input[name='BP_strandID']").val();
            }

            let searchId = sId || tId || fName;

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
            this.value = (ui.item ? ui.item[1] : '');
            // Set the next td's input value
            $(this).closest("td").prev().find('input').val(ui.item[0]);
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>').data("ui-autocomplete-item", item)
            .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                item[1] + '</span><br><span class="desc">' +
                item[0] + '</span></div>').appendTo(ul);
    };
    input.focus(function() {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });
}

function dbAutoCompleteTubeID(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            let tubeID = input.attr("id");
            console.log("tubeID is " + tubeID);
            let tId = input.val();
            console.log("tId is " + tId);
            let sId = "";
            let row = input.closest("tr");

            if (tubeID.startsWith("FP")) {
                sId = row.find("input[name='FP_strandID']").val();
            }
            else if (tubeID.startsWith("BP")) {
                sId = row.find("input[name='BP_strandID']").val();
            }

            let searchId = sId || tId;

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/SearchTube',
                data: { "searchId": searchId },
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

        minLength: 0, maxShowItems: 4, scroll: true,

        select: function(event, ui) {

            // Set the TubeID
            this.value = ui.item ? ui.item[0] : '';

            // Set TubeName (next td input)
            $(this).closest("td").next().find('input').val(ui.item[1]);

            let row = $(this).closest("tr");

            // detect FP or BP
            let prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";

            // Fill fields dynamically
            row.find(`input[name='${prefix}_FiberID']`).val(ui.item[2]);
            row.find(`input[name='${prefix}_TubeName']`).val(ui.item[1]);
            row.find(`input[name='${prefix}_FiberName']`).val(ui.item[3]);
            row.find(`input[name='${prefix}_TubeNb']`).val(ui.item[4]);

            // Set tube color
            row.find(`select[name='${prefix}_TubeColor']`).val(ui.item[5]);

            // Call color setter
            let colorID = row.find(`select[name='${prefix}_TubeColor']`).attr("id");
            let numberID = row.find(`input[name='${prefix}_TubeNb']`).attr("id");

            tubeStrandSetColor(colorID, numberID);

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

    input.focus(function() {
        if (this.value === "") {
            $(this).autocomplete("search");
        }
    });
}

function dbAutoCompleteTubeName(input) {

    input.autocomplete({
        source: debounce(function(request, response) {

            let tubeNameID = input.attr("id");
            let tName = input.val();
            let sId = "";
            let row = input.closest("tr");

            if (tubeNameID.startsWith("FP")) {
                sId = row.find("input[name='FP_strandID']").val();
            }
            else if (tubeNameID.startsWith("BP")) {
                sId = row.find("input[name='BP_strandID']").val();
            }

            let searchId = sId || tName;

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/SearchTube',
                data: { "searchId": searchId },
                dataType: "json",
                success: function(data) {
                    if (data != null) {
                        response(data.glist);
                        console.log(data.glist);
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

            // Set the TubeName
            this.value = ui.item ? ui.item[1] : "";

            let row = $(this).closest("tr");
            let prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";

            // Set TubeID (previous td)
            row.find(`input[name='${prefix}_TubeID']`).val(ui.item[0]);

            // Set other fiber fields
            row.find(`input[name='${prefix}_FiberID']`).val(ui.item[2]);
            row.find(`input[name='${prefix}_FiberName']`).val(ui.item[3]);
            row.find(`input[name='${prefix}_TubeNb']`).val(ui.item[4]);

            // Set tube color
            row.find(`select[name='${prefix}_TubeColor']`).val(ui.item[5]);

            // Call your color setter
            let colorID = row.find(`select[name='${prefix}_TubeColor']`).attr("id");
            let numberID = row.find(`input[name='${prefix}_TubeNb']`).attr("id");
            tubeStrandSetColor(colorID, numberID);

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

    // Auto-open menu when field is empty
    input.focus(function() {
        if (this.value === "") {
            $(this).autocomplete("search");
        }
    });
}


function dbAutoCompleteStrandID(input) {

    input.autocomplete({

        source: debounce(function(request, response) {

            let strandID = input.attr("id");
            console.log("strandID is " + strandID);
            let sId = input.val();
            let tId = "";
            let row = input.closest("tr");

            if (strandID.startsWith("FP")) {
                tId = row.find("input[name='FP_tubeID']").val();
            }
            else if (strandID.startsWith("BP")) {
                tId = row.find("input[name='BP_tubeID']").val();
            }

            let searchId = tId || sId;

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + "/SearchMappingStrand",
                data: { "searchId": searchId },
                dataType: "json",
                success: function(data) {
                    if (data != null) {
                        response(data.glist);
                        console.log(data.glist);
                    }
                },
                error: function() { alert("Error"); }
            });

        }, 900),

        minLength: 0,
        maxShowItems: 4,
        scroll: true,

        select: function(event, ui) {

            // Set StrandID
            this.value = ui.item ? ui.item[0] : "";

            let row = $(this).closest("tr");
            let prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";

            // Fill related fields
            row.find(`input[name='${prefix}_StrandName']`).val(ui.item[1]);
            row.find(`input[name='${prefix}_FiberID']`).val(ui.item[2]);
            row.find(`input[name='${prefix}_FiberName']`).val(ui.item[3]);
            row.find(`input[name='${prefix}_TubeID']`).val(ui.item[4]);
            row.find(`input[name='${prefix}_TubeName']`).val(ui.item[5]);

            // Tube number & color
            row.find(`input[name='${prefix}_TubeNb']`).val(ui.item[6]);
            row.find(`select[name='${prefix}_TubeColor']`).val(ui.item[7]);

            // Call color setter
            tubeStrandSetColor(
                row.find(`select[name='${prefix}_TubeColor']`).attr("id"),
                row.find(`input[name='${prefix}_TubeNb']`).attr("id")
            );

            return false;
        }

    }).data("ui-autocomplete")._renderItem = function(ul, item) {

        return $('<li class="each"></li>')
            .data("ui-autocomplete-item", item)
            .append(
                `<div class="acItem">
                    <span class="name" style="font-weight:bold">${item[0]}</span><br>
                    <span class="desc">${item[1]}, ${item[3]}, Tube ${item[4]}</span>
                </div>`
            )
            .appendTo(ul);
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}


function dbAutoCompleteStrandName(input) {

    input.autocomplete({

        source: debounce(function(request, response) {

            let strandNameID = input.attr("id");
            console.log("strandNameID is " + strandNameID);
            let sName = input.val();
            let tId = "";
            let row = input.closest("tr");

            if (strandNameID.startsWith("FP")) {
                tId = row.find("input[name='FP_tubeID']").val();
            }
            else if (strandNameID.startsWith("BP")) {
                tId = row.find("input[name='BP_tubeID']").val();
            }

            let searchId = tId || sName;

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + "/SearchMappingStrand",
                data: { "searchId": searchId },
                dataType: "json",
                success: function(data) {
                    if (data != null) {
                        response(data.glist);
                        console.log(data.glist);
                    }
                },
                error: function() { alert("Error"); }
            });

        }, 900),

        minLength: 0,
        maxShowItems: 4,
        scroll: true,

        select: function(event, ui) {

            // Set StrandName
            this.value = ui.item ? ui.item[1] : "";

            let row = $(this).closest("tr");
            let prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";

            // Fill related fields
            row.find(`input[name='${prefix}_StrandID']`).val(ui.item[0]);
            row.find(`input[name='${prefix}_FiberID']`).val(ui.item[2]);
            row.find(`input[name='${prefix}_FiberName']`).val(ui.item[3]);
            row.find(`input[name='${prefix}_TubeID']`).val(ui.item[4]);
            row.find(`input[name='${prefix}_TubeName']`).val(ui.item[5]);

            // Tube Nb & Color
            row.find(`input[name='${prefix}_TubeNb']`).val(ui.item[6]);
            row.find(`select[name='${prefix}_TubeColor']`).val(ui.item[7]);

            // Color setter
            tubeStrandSetColor(
                row.find(`select[name='${prefix}_TubeColor']`).attr("id"),
                row.find(`input[name='${prefix}_TubeNb']`).attr("id")
            );

            return false;
        }

    }).data("ui-autocomplete")._renderItem = function(ul, item) {

        return $('<li class="each"></li>')
            .data("ui-autocomplete-item", item)
            .append(
                `<div class="acItem">
                    <span class="name" style="font-weight:bold">${item[1]}</span><br>
                    <span class="desc">${item[0]}, ${item[3]}, Tube ${item[4]}</span>
                </div>`
            )
            .appendTo(ul);
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbAutoCompleteEquipmentID(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            let searchVal = input.val();
            equip = input.closest("td").prev().find('select').val();
            console.log("equip is " + equip);
            if (equip == "DistBoard") {
                url = 'getDbData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (equip == "Node") {
                url = 'getNodeData';
                dataTarget = {
                    "searchs": searchVal,
                }
            }
            else {
                url = 'emptyUrl';
            }

            if (url != "emptyUrl") {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/' + url,
                    data: dataTarget,
                    dataType: "json",
                    success: function(data) {
                        if (data != null) response(data.globalList);
                    },
                    error: function() { alert("Error"); }
                });
            }
        }, 900),
        minLength: 0,
        maxShowItems: 4,
        scroll: true,
        select: function(event, ui) {
            this.value = ui.item ? ui.item[0] : "";
            let row = $(this).closest("tr");
            let prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";
            row.find(`input[name='${prefix}_equipmentName']`).val(ui.item[1]);
            if (equip == "DistBoard") {
                row.find(`input[name='${prefix}_equipmentType']`).val("Distribution Board");
            }
            else if (equip == "Node") {
                row.find(`input[name='${prefix}_equipmentType']`).val(ui.item[2]);
            }
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>')
            .data("ui-autocomplete-item", item)
            .append(`<div class="acItem"><span class="name" style="font-weight:bold">${item[0]}</span><br><span class="desc">${item[1]}</span></div>`)
            .appendTo(ul);
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbAutoCompleteEquipmentName(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            let searchVal = input.val();
            row = input.closest("tr");
            prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";
            equip = row.find(`select[name='${prefix}_equipment']`).val();
            console.log("equip is " + equip);
            if (equip == "DistBoard") {
                url = 'getDbData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (equip == "Node") {
                url = 'getNodeData';
                dataTarget = {
                    "searchs": searchVal,
                }
            }
            else {
                url = 'emptyUrl';
            }

            if (url != "emptyUrl") {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/' + url,
                    data: dataTarget,
                    dataType: "json",
                    success: function(data) {
                        if (data != null) response(data.globalList);
                    },
                    error: function() { alert("Error"); }
                });
            }
        }, 900),
        minLength: 0,
        maxShowItems: 4,
        scroll: true,
        select: function(event, ui) {
            this.value = ui.item ? ui.item[1] : "";
            row.find(`input[name='${prefix}_equipmentID']`).val(ui.item[0]);
            if (equip == "DistBoard") {
                row.find(`input[name='${prefix}_equipmentType']`).val("Distribution Board");
            }
            else if (equip == "Node") {
                row.find(`input[name='${prefix}_equipmentType']`).val(ui.item[2]);
            }
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>')
            .data("ui-autocomplete-item", item)
            .append(`<div class="acItem"><span class="name" style="font-weight:bold">${item[1]}</span><br><span class="desc">${item[0]}</span></div>`)
            .appendTo(ul);
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbAutoCompleteEquipmentType(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            let searchVal = input.val();
            row = input.closest("tr");
            prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";
            equip = row.find(`select[name='${prefix}_equipment']`).val();
            console.log("equip is " + equip);
            if (equip == "DistBoard") {
                url = 'getDbData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (equip == "Node") {
                url = 'getNodeData';
                dataTarget = {
                    "searchs": searchVal,
                }
            }
            else {
                url = 'emptyUrl';
            }

            if (url != "emptyUrl") {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/' + url,
                    data: dataTarget,
                    dataType: "json",
                    success: function(data) {
                        if (data != null) response(data.globalList);
                    },
                    error: function() { alert("Error"); }
                });
            }
        }, 900),
        minLength: 0,
        maxShowItems: 4,
        scroll: true,
        select: function(event, ui) {
            row.find(`input[name='${prefix}_equipmentID']`).val(ui.item[0]);
            row.find(`input[name='${prefix}_equipmentName']`).val(ui.item[1]);
            if (equip == "DistBoard") {
                row.find(`input[name='${prefix}_equipmentType']`).val("Distribution Board");
            }
            else if (equip == "Node") {
                this.value = ui.item ? ui.item[2] : "";
            }
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>')
            .data("ui-autocomplete-item", item)
            .append(`<div class="acItem"><span class="name" style="font-weight:bold">${item[0]}</span><br><span class="desc">${item[1]}</span></div>`)
            .appendTo(ul);
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbAutoCompleteJunctionID(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            row = input.closest("tr");
            prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";
            let searchVal = input.val();

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + "/SearchJunction",
                data: { searchId: searchVal },
                dataType: "json",
                success: function(data) {
                    if (data != null) response(data.clist);
                },
                error: function() { alert("Error"); }
            });
        }, 900),
        minLength: 0,
        maxShowItems: 4,
        scroll: true,
        select: function(event, ui) {
            this.value = ui.item ? ui.item[0] : "";

            // Fill related fields
            row.find(`input[name='${prefix}_junctionName']`).val(ui.item[1]);

            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>')
            .data("ui-autocomplete-item", item)
            .append(`<div class="acItem"><span class="name" style="font-weight:bold">${item[0]}</span><br><span class="desc">${item[1]}</span></div>`)
            .appendTo(ul);
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbAutoCompleteJunctionName(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            row = input.closest("tr");
            prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";
            let searchVal = input.val();

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + "/SearchJunction",
                data: { searchId: searchVal },
                dataType: "json",
                success: function(data) {
                    if (data != null) response(data.clist);
                },
                error: function() { alert("Error"); }
            });
        }, 900),
        minLength: 0,
        maxShowItems: 4,
        scroll: true,
        select: function(event, ui) {
            this.value = ui.item ? ui.item[1] : "";

            // Fill related fields
            row.find(`input[name='${prefix}_junctionID']`).val(ui.item[0]);

            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>')
            .data("ui-autocomplete-item", item)
            .append(`<div class="acItem"><span class="name" style="font-weight:bold">${item[1]}</span><br><span class="desc">${item[0]}</span></div>`)
            .appendTo(ul);
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbAutoCompleteLocationID(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            let searchVal = input.val();
            locationType = input.closest("td").prev().find('select').val();
            console.log("locationType is " + locationType);
            if (locationType == "Customer") {
                url = 'GetAllNetworkCustomer';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (locationType == "Site") {
                url = 'GetAllWarehouse';
                dataTarget = {
                    "WareName": searchVal,
                    "warehouseName": searchVal,
                    "SiteId": searchVal,
                }
            }
            else if (locationType == "Manhole") {
                url = 'getManholeData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (locationType == "Handhole") {
                url = 'getHandholeData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (locationType == "DB") {
                url = 'getDbData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else {
                url = 'emptyUrl';
            }

            if (url != "emptyUrl") {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/' + url,
                    data: dataTarget,
                    dataType: "json",
                    success: function(data) {
                        if (data != null) response(data.globalList);
                    },
                    error: function() { alert("Error"); }
                });
            }
        }, 900),
        minLength: 0,
        maxShowItems: 4,
        scroll: true,
        select: function(event, ui) {
            let row = $(this).closest("tr");
            let prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";
            row.find(`input[name='${prefix}_locationM']`).val(ui.item ? ui.item[1] : '');

            if (locationType == "Customer" || locationType == "Manhole" || locationType == "Handhole" || locationType == "DB") {
                this.value = ui.item ? ui.item[0] : "";
            }
            else if (locationType == "Site") {
                this.value = ui.item ? ui.item[2] : "";
                row.find(`input[name='${prefix}_location']`).val(ui.item ? ui.item[0] : '');
            }
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        if (item[0].split("_")[0] == "WARE") {
            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[2] + "</span><br><span class='desc'>" +
                    item[0] + ', ' + item[1] + "</span></div>")
                .appendTo(ul);
        }
        else {
            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[0] + "</span><br><span class='desc'>" +
                    item[1] + ', ' + item[2] + "</span></div>")
                .appendTo(ul);
        }
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbAutoCompleteLocationM(input) {
    input.autocomplete({
        source: debounce(function(request, response) {
            let searchVal = input.val();

            row = input.closest("tr");
            prefix = input.attr("id").startsWith("FP") ? "FP" : "BP";
            locationType = row.find(`select[name='${prefix}_locationType']`).val();
            console.log("locationType is " + locationType);
            if (locationType == "Customer") {
                url = 'GetAllNetworkCustomer';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (locationType == "Site") {
                url = 'GetAllWarehouse';
                dataTarget = {
                    "WareName": searchVal,
                    "warehouseName": searchVal,
                    "SiteId": searchVal,
                }
            }
            else if (locationType == "Manhole") {
                url = 'getManholeData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (locationType == "Handhole") {
                url = 'getHandholeData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else if (locationType == "DB") {
                url = 'getDbData';
                dataTarget = {
                    "search": searchVal,
                }
            }
            else {
                url = 'emptyUrl';
            }

            if (url != "emptyUrl") {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/' + url,
                    data: dataTarget,
                    dataType: "json",
                    success: function(data) {
                        if (data != null) response(data.globalList);
                    },
                    error: function() { alert("Error"); }
                });
            }
        }, 900),
        minLength: 0,
        maxShowItems: 4,
        scroll: true,
        select: function(event, ui) {
            this.value = ui.item ? ui.item[1] : "";

            if (locationType == "Customer" || locationType == "Manhole" || locationType == "Handhole" || locationType == "DB") {
                row.find(`input[name='${prefix}_locationID']`).val(ui.item ? ui.item[0] : '');
            }
            else if (locationType == "Site") {
                row.find(`input[name='${prefix}_locationID']`).val(ui.item ? ui.item[2] : '');
                row.find(`input[name='${prefix}_location']`).val(ui.item ? ui.item[0] : '');
            }
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        if (item[0].split("_")[0] == "CUST" || item[0].split("_")[0] == "WARE") {
            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[1] + "</span><br><span class='desc'>" +
                    item[0] + ', ' + item[2] + "</span></div>")
                .appendTo(ul);
        }
        else {
            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[0] + "</span><br><span class='desc'>" +
                    item[1] + ', ' + item[2] + "</span></div>")
                .appendTo(ul);
        }
    };

    input.focus(function() {
        if (this.value === "") $(this).autocomplete("search");
    });
}

function dbTubeStrandNoColor() {

    $("#DbMappingTable").on("change", "select[name='FP_tubecolor'], select[name='BP_tubecolor']", function() {
        prefix = $(this).attr("id").startsWith("FP") ? "FP" : "BP";
        //equip = row.find(`select[name='${prefix}_equipment']`).val();		
        const row = $(this).closest("tr");
        const numberField = row.find(`input[name='${prefix}_tubeNb']`);
        tubeStrandColorSelect(this, numberField[0]);
    });

    $("#DbMappingTable").on("change", "select[name='FP_strandcolor'], select[name='BP_strandcolor']", function() {
        prefix = $(this).attr("id").startsWith("FP") ? "FP" : "BP";
        const row = $(this).closest("tr");
        const numberField = row.find(`input[name='${prefix}_strandNb']`);
        tubeStrandColorSelect(this, numberField[0]);
    });

    $("#DbMappingTable").on("change", "input[name='FP_tubeNb'], input[name='BP_tubeNb']", function() {
        prefix = $(this).attr("id").startsWith("FP") ? "FP" : "BP";
        const row = $(this).closest("tr");
        const colorField = row.find(`select[name='${prefix}_tubecolor']`);
        tubeStrandNoChange(this, colorField[0]);
    });

    $("#DbMappingTable").on("change", "input[name='FP_strandNb'], input[name='BP_strandNb']", function() {
        prefix = $(this).attr("id").startsWith("FP") ? "FP" : "BP";
        const row = $(this).closest("tr");
        const colorField = row.find(`select[name='${prefix}_strandcolor']`);
        tubeStrandNoChange(this, colorField[0]);
    });
}

function tubeStrandColorSelect(colorElement, numberElement) {
    const color = colorElement.value;

    // Mapping colors to strand/tube numbers
    const colorMap = {
        blue: { num: "1", text: "white" },
        orange: { num: "2", text: "white" },
        green: { num: "3", text: "white" },
        brown: { num: "4", text: "white" },
        gray: { num: "5", text: "white" },
        white: { num: "6", text: "black" },
        red: { num: "7", text: "white" },
        black: { num: "8", text: "white" },
        yellow: { num: "9", text: "black" },
        violet: { num: "10", text: "white" },
        pink: { num: "11", text: "black" },
        aqua: { num: "12", text: "black" }
    };

    // If color not in map, clear style and exit
    if (!colorMap[color]) {
        colorElement.classList.remove("colored-select");
        colorElement.style.backgroundColor = "";
        colorElement.style.color = "";
        numberElement.value = "";
        return;
    }

    // Apply new background + text color

    colorElement.classList.add("colored-select");
    colorElement.style.backgroundColor = color;
    colorElement.style.color = colorMap[color].text;

    // Set the tube/strand number
    numberElement.value = colorMap[color].num;
}

function tubeStrandNoChange(numberElement, colorElement) {
    const number = numberElement.value;
    console.log("number is " + number);

    // Mapping tube/strand numbers to colors and text colors
    const numberMap = {
        "1": { color: "blue", text: "white" },
        "2": { color: "orange", text: "white" },
        "3": { color: "green", text: "white" },
        "4": { color: "brown", text: "white" },
        "5": { color: "gray", text: "white" },
        "6": { color: "white", text: "black" },
        "7": { color: "red", text: "white" },
        "8": { color: "black", text: "white" },
        "9": { color: "yellow", text: "black" },
        "10": { color: "violet", text: "white" },
        "11": { color: "pink", text: "black" },
        "12": { color: "aqua", text: "black" }
    };

    if (!numberMap[number]) {
        // Clear if invalid or empty
        colorElement.value = "";
        colorElement.classList.remove("colored-select");
        colorElement.style.backgroundColor = "";
        colorElement.style.color = "";
        return;
    }

    // Apply color
    colorElement.value = numberMap[number].color;
    colorElement.classList.add("colored-select");
    colorElement.style.backgroundColor = numberMap[number].color;
    colorElement.style.color = numberMap[number].text;
}

function tubeStrandSetColor(colorID, numberID) {

    if (document.getElementById(colorID).value == "blue") {
        document.getElementById(colorID).style.backgroundColor = "blue";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "1";
    }
    else if (document.getElementById(colorID).value == "orange") {
        document.getElementById(colorID).style.backgroundColor = "orange";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "2";
    }
    else if (document.getElementById(colorID).value == "green") {
        document.getElementById(colorID).style.backgroundColor = "green";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "3";
    }
    else if (document.getElementById(colorID).value == "brown") {
        document.getElementById(colorID).style.backgroundColor = "brown";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "4";
    }
    else if (document.getElementById(colorID).value == "gray") {
        document.getElementById(colorID).style.backgroundColor = "gray";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "5";
    }
    else if (document.getElementById(colorID).value == "white") {
        document.getElementById(colorID).style.backgroundColor = "white";
        document.getElementById(colorID).style.color = "black";
        document.getElementById(numberID).value = "6";
    }
    else if (document.getElementById(colorID).value == "red") {
        document.getElementById(colorID).style.backgroundColor = "red";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "7";
    }
    else if (document.getElementById(colorID).value == "black") {
        document.getElementById(colorID).style.backgroundColor = "black";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "8";
    }
    else if (document.getElementById(colorID).value == "yellow") {
        document.getElementById(colorID).style.backgroundColor = "yellow";
        document.getElementById(colorID).style.color = "black";
        document.getElementById(numberID).value = "9";
    }
    else if (document.getElementById(colorID).value == "violet") {
        document.getElementById(colorID).style.backgroundColor = "violet";
        document.getElementById(colorID).style.color = "white";
        document.getElementById(numberID).value = "10";
    }
    else if (document.getElementById(colorID).value == "pink") {
        document.getElementById(colorID).style.backgroundColor = "pink";
        document.getElementById(colorID).style.color = "black";
        document.getElementById(numberID).value = "11";
    }
    else if (document.getElementById(colorID).value == "aqua") {
        document.getElementById(colorID).style.backgroundColor = "aqua";
        document.getElementById(colorID).style.color = "black";
        document.getElementById(numberID).value = "12";
    }
}

function strandTubeSetColor(strandTubeNumber, ID) {
    if (strandTubeNumber == "1") {
        document.getElementById(ID).value = "blue";
        document.getElementById(ID).style.backgroundColor = "blue";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "2") {
        document.getElementById(ID).value = "orange";
        document.getElementById(ID).style.backgroundColor = "orange";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "3") {
        document.getElementById(ID).value = "green";
        document.getElementById(ID).style.backgroundColor = "green";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "4") {
        document.getElementById(ID).value = "brown";
        document.getElementById(ID).style.backgroundColor = "brown";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "5") {
        document.getElementById(ID).value = "gray";
        document.getElementById(ID).style.backgroundColor = "gray";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "6") {
        document.getElementById(ID).value = "white";
        document.getElementById(ID).style.backgroundColor = "white";
        document.getElementById(ID).style.color = "black";
    }
    else if (strandTubeNumber == "7") {
        document.getElementById(ID).value = "red";
        document.getElementById(ID).style.backgroundColor = "red";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "8") {
        document.getElementById(ID).value = "black";
        document.getElementById(ID).style.backgroundColor = "black";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "9") {
        document.getElementById(ID).value = "yellow";
        document.getElementById(ID).style.backgroundColor = "yellow";
        document.getElementById(ID).style.color = "black";
    }
    else if (strandTubeNumber == "10") {
        document.getElementById(ID).value = "violet";
        document.getElementById(ID).style.backgroundColor = "violet";
        document.getElementById(ID).style.color = "white";
    }
    else if (strandTubeNumber == "11") {
        document.getElementById(ID).value = "pink";
        document.getElementById(ID).style.backgroundColor = "pink";
        document.getElementById(ID).style.color = "black";
    }
    else if (strandTubeNumber == "12") {
        document.getElementById(ID).value = "aqua";
        document.getElementById(ID).style.backgroundColor = "aqua";
        document.getElementById(ID).style.color = "black";
    }
    else if (strandTubeNumber > 12 || strandTubeNumber == "") {
        document.getElementById(ID).value = "";
        document.getElementById(ID).style.backgroundColor = "";
        document.getElementById(ID).style.color = "";
    }
}



const LOCATION_TYPES = ["Select an Option", "Customer", "Site", "Manhole", "Handhole"];
const EQUIPMENT_MAP = {
    "Customer": ["Custom", "Node", "DistBoard"],
    "Site": ["Node", "DistBoard"],
    "Manhole": ["Node", "DistBoard"],
    "Handhole": ["Node", "DistBoard"],
    "Select an Option": ["Custom", "Node", "DistBoard"]   // fallback
};

function locationTypeChange() {
    // FP Location change -> update FP equipment for the same row
    $("#DbMappingTable").on("change", "select[name='FP_locationType']", function() {
        const id = $(this).attr("id");                // e.g. "FP_LocationType3"
        const index = id.replace('FP_LocationType', ''); // "3"
        console.log("index of FP_LocationType is " + index);
        const loc = $(this).val();                    // e.g. "Customer"
        const equipSelect = $("#FP_equipment" + index);
        console.log("before if statement, equipSelect.length is " + equipSelect.length);
        if (equipSelect.length) {
            console.log("after if statement, equipSelect.length is " + equipSelect.length);
			console.log("loc is " +loc);
            const newOptions = generateEquipmentOptions(loc);
            console.log("newOptions for FP equipment is " + newOptions);
            equipSelect.html(newOptions);
            // optionally set equipment type field values / readonlys here
            // e.g. $('#FP_Location'+index).prop("readonly", loc==='Customer' || loc==='Manhole' || loc==='Handhole');
            $('#FP_LocationM' + index).val('');
            $('#FP_Location' + index).val('');
            $('#FP_LocationID' + index).val('');
        }

        if (loc == "Manhole" || loc == "Handhole" || loc == "Customer") {
            $('#FP_Location' + index).prop("readonly", true);
        }
        else if (loc == "Site") {
            $('#FP_Location' + index).prop("readonly", false);
        }
    });

    // BP Location change -> update BP equipment
    $("#DbMappingTable").on("change", "select[name='BP_locationType']", function() {
        const index = $(this).attr("id").replace('BP_LocationType', '');
        const loc = $(this).val();
        const equipSelect = $("#BP_equipment" + index);
        if (equipSelect.length) {
            equipSelect.html(generateEquipmentOptions(loc));
            $('#BP_LocationM' + index).val('');
            $('#BP_Location' + index).val('');
            $('#BP_LocationID' + index).val('');
        }

        if (loc == "Manhole" || loc == "Handhole" || loc == "Customer") {
            $('#BP_Location' + index).prop("readonly", true);
        }
        else if (loc == "Site") {
            $('#BP_Location' + index).prop("readonly", false);
        }
    });
}

// returns options only (string)
function generateLocationOptions(selectedValue) {
    return LOCATION_TYPES.map(type => {
        // for the "Select an Option" keep value empty or whatever you prefer
        const value = (type === "Select an Option") ? "" : type;
        const sel = (type === selectedValue) ? " selected" : "";
        return `<option value="${value}"${sel}>${type}</option>`;
    }).join("");
}

// returns options only (string) for equipment based on locationType
function generateEquipmentOptions(locationType, selectedValue) {
    const list = EQUIPMENT_MAP[locationType] || ["Custom", "Node", "DistBoard"];
    let opts = `<option value="">Select an Option</option>`;
    list.forEach(v => {
        const sel = (v === selectedValue) ? " selected" : "";
        opts += `<option value="${v}"${sel}>${v}</option>`;
    });
    return opts;
}

function equipmentChange() {

    $("#DbMappingTable").on("change", "select[name='BP_equipment']", function() {
        let selectedValue = $(this).val();
        let row = $(this).closest("tr");
        if (selectedValue === "DistBoard") {
            // Make inputs editable
            row.find("input[name='backKitModule']").prop("readonly", false);
            row.find("input[name='backKitModule']").val('');
            row.find("input[name='backPortNum']").prop("readonly", false);
            row.find("input[name='backPortNum']").val('');
        } else {
            // Make inputs readonly again
            row.find("input[name='backKitModule']").prop("readonly", true);
            row.find("input[name='backKitModule']").val('');
            row.find("input[name='backPortNum']").prop("readonly", true);
            row.find("input[name='backPortNum']").val('');
        }
    });

    $("#DbMappingTable").on("change", "select[name='FP_equipment']", function() {
        let selectedValue = $(this).val();
        let row = $(this).closest("tr");
        if (selectedValue === "DistBoard") {
            // Make inputs editable
            row.find("input[name='farKitSerialNum']").prop("readonly", false);
            row.find("input[name='farKitSerialNum']").val('');
            row.find("input[name='farModule']").prop("readonly", false);
            row.find("input[name='farModule']").val('');
            row.find("input[name='farPortNum']").prop("readonly", false);
            row.find("input[name='farPortNum']").val('');
        } else {
            // Make inputs readonly again
            row.find("input[name='farKitSerialNum']").prop("readonly", true);
            row.find("input[name='farKitSerialNum']").val('');
            row.find("input[name='farModule']").prop("readonly", true);
            row.find("input[name='farModule']").val('');
            row.find("input[name='farPortNum']").prop("readonly", true);
            row.find("input[name='farPortNum']").val('');
        }
    });
}

function indexRowColEvents(config) {

    // INDEX → ROW/COL
    $("#DbMappingTable").on("input", "input[name='Index']", function() {
        const row = $(this).closest("tr");
        const index = parseInt($(this).val().trim(), 10);

        if (isNaN(index)) {
            row.find("input[name='rowIndex']").val("");
            row.find("input[name='colIndex']").val("");
            return;
        }

        const { row: r, col: c } = calculateRowColFromIndex(
            index,
            config.numRows,
            config.numCols,
            config.direction,
            config.rowPerModule,
            config.totalModules
        );

        if (r === "" || c === "") {
            $(this).val("");
            row.find("input[name='rowIndex']").val("");
            row.find("input[name='colIndex']").val("");
            return;
        }

        row.find("input[name='rowIndex']").val(r);
        row.find("input[name='colIndex']").val(c);
    });


    // ROW/COL → INDEX
    $("#DbMappingTable").on("input",
        "input[name='rowIndex'], input[name='colIndex']",
        function() {
            const row = $(this).closest("tr");
            const r = parseInt(row.find("input[name='rowIndex']").val().trim(), 10);
            const c = parseInt(row.find("input[name='colIndex']").val().trim(), 10);

            if (isNaN(r) || isNaN(c)) {
                row.find("input[name='Index']").val("");
                return;
            }

            const index = calculateIndexFromRowCol(
                r,
                c,
                config.numRows,
                config.numCols,
                config.direction,
                config.rowPerModule,
                config.totalModules
            );

            if (index === "") {
                row.find("input[name='Index']").val("");
                return;
            }

            row.find("input[name='Index']").val(index);
        }
    );
}

function calculateRowColFromIndex(index, numRows, numCols, direction, rowPerModule, totalModules) {
    const totalPorts = numRows * numCols;
    if (index < 1 || index > totalPorts) {
        alert(`Invalid Port: ${index}. Must be between 1 and ${totalPorts}.`);
        return { row: "", col: "" };
    }

    direction = direction.toLowerCase().replace(/\s+/g, "");

    // --- CASE 1: simple (no modules) ---
    if (!rowPerModule || totalModules <= 1) {
        let row, col;
        if (direction === "uptodown" || direction === "downtoup") {
            row = Math.ceil(index / numCols);
            col = (index - 1) % numCols + 1;
        }/* else if (direction === "downtoup") {
						 				             const reversedRow = Math.ceil(index / numCols);
						 				             col = (index - 1) % numCols + 1;
						 				             row = numRows - reversedRow + 1;
						 				         } */else {
            alert("Invalid direction value.");
            return { row: "", col: "" };
        }
        return { row, col };
    }

    // --- CASE 2: module-aware (horizontal modules) ---
    const colsPerModule = Math.floor(numCols / totalModules); // ensure integer value
    const portsPerModule = numRows * colsPerModule;

    if (isNaN(portsPerModule) || portsPerModule <= 0) {
        console.error("Invalid portsPerModule.");
        return { row: "", col: "" };
    }

    const moduleIndex = Math.ceil(index / portsPerModule); // 1-based
    const portInModule = index - (moduleIndex - 1) * portsPerModule;

    if (isNaN(portInModule) || portInModule <= 0) {
        console.error("Invalid portInModule.");
        return { row: "", col: "" };
    }

    let localRow, localCol;

    if (direction === "uptodown" || direction === "downtoup") {
        localRow = Math.ceil(portInModule / colsPerModule);
        localCol = (portInModule - 1) % colsPerModule + 1;
    }/* else if (direction === "downtoup") {
						 				         const reversedRow = Math.ceil(portInModule / colsPerModule);
						 				         localCol = (portInModule - 1) % colsPerModule + 1;
						 				         localRow = numRows - reversedRow + 1;
						 				     } */else {
        alert("Invalid direction value.");
        return { row: "", col: "" };
    }

    const globalCol = (moduleIndex - 1) * colsPerModule + localCol;

    return { row: localRow, col: globalCol };
}

function calculateIndexFromRowCol(row, col, numRows, numCols, direction, rowPerModule, totalModules) {

    // Normalize direction
    direction = direction.toLowerCase().replace(/\s+/g, "");

    // Validate row
    if (row < 1 || row > numRows) {
        alert(`Invalid Row: ${row}. Must be between 1 and ${numRows}.`);
        return "";
    }

    // Validate column
    if (col < 1 || col > numCols) {
        alert(`Invalid Column: ${col}. Must be between 1 and ${numCols}.`);
        return "";
    }

    // --- CASE 1: simple (no modules) ---
    if (!rowPerModule || totalModules <= 1) {

        if (direction === "uptodown") {
            const index = (row - 1) * numCols + col;
            console.log(`Simple → Row=${row}, Col=${col}, Port=${index}`);
            return index;
        }

        else if (direction === "downtoup") {
            const reversedRow = numRows - row + 1;
            const index = (reversedRow - 1) * numCols + col;
            console.log(`Simple (Down→Up) → Row=${row}, Col=${col}, Port=${index}`);
            return index;
        }

        else {
            alert("Invalid direction value.");
            return "";
        }
    }

    // --- CASE 2: module-aware (horizontal modules) ---
    const colsPerModule = numCols / totalModules;
    const portsPerModule = numRows * colsPerModule;
    const moduleIndex = Math.ceil(col / colsPerModule);
    const localCol = col - (moduleIndex - 1) * colsPerModule;

    console.log(`[DEBUG] Row=${row}, Col=${col}, Module=${moduleIndex}, LocalCol=${localCol}`);

    let portInModule;

    if (direction === "uptodown" || direction === "downtoup") {
        portInModule = (row - 1) * colsPerModule + localCol;
    }
    /* else if (direction === "downtoup") {
         const reversedRow = numRows - row + 1;
         portInModule = (reversedRow - 1) * colsPerModule + localCol;
     } */
    else {
        alert("Invalid direction value.");
        return "";
    }

    const index = (moduleIndex - 1) * portsPerModule + portInModule;
    return index;
}

$(document).ready(function() {

    $("#add_RackRow").on('click', function() {
        var rowPerModule = 0;
        var totalModules = 0;

        $.ajax({
            type: "GET",
            url: getContext() + '/findModuleDetails',
            data: {
                "selectedDistBoardContext": selectedDistBoardContext
            },
            beforeSend: function() {
                $("#loaderDivDB").show();
            },
            dataType: "json",
            success: function(data) {
                $("#loaderDivDB").hide();

                console.log("before " + index)
                index = 0

                if (data != null) {

                    rowPerModule = data.panelInfo[0];
                    totalModules = data.panelInfo[1];

                } // end of data != null	
            },
            error: function(result) {
                alert("Error");
            }
        });


        countRackBoq = $("#DbMappingTable > tbody").children().length;
        dBBoqIndex = countRackBoq;
        var totalPorts = $("#DistributionBoardRowsNum").val() * $("#DistributionBoardColsNum").val();
        console.log("dBBoqIndex " + dBBoqIndex);
        var addMark4 = '<option value=' + '""' + ' style=' + '"background-color: white;"' + '></option>' +
            '<option value=' + '"blue"' + ' style=' + '"background-color: white; color:black"' + '>blue</option>' +
            '<option value=' + '"orange"' + ' style=' + '"background-color: white; color:black"' + '>orange</option>' +
            '<option value=' + '"green"' + ' style=' + '"background-color: white; color:black"' + '>green</option>' +
            '<option value=' + '"brown"' + ' style=' + '"background-color: white; color:black"' + '>brown</option>' +
            '<option value=' + '"gray"' + ' style=' + '"background-color: white; color:black"' + '>gray</option>' +
            '<option value=' + '"white"' + ' style=' + '"background-color: white; color:black"' + '>white</option>' +
            '<option value=' + '"red"' + ' style=' + '"background-color: white; color:black"' + '>red</option>' +
            '<option value=' + '"black"' + ' style=' + '"background-color: white; color:black"' + '>black</option>' +
            '<option value=' + '"yellow"' + ' style=' + '"background-color: white; color:black"' + '>yellow</option>' +
            '<option value=' + '"violet"' + ' style=' + '"background-color: white; color:black"' + '>violet</option>' +
            '<option value=' + '"pink"' + ' style=' + '"background-color: white; color:black"' + '>pink</option>' +
            '<option value=' + '"aqua"' + ' style=' + '"background-color: white; color:black"' + '>aqua</option>';
        if (totalPorts > countRackBoq) {

            var markup = "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='Index' class='headcol'><input name='Index' id='index" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
                + "<td name='nearModule'><input name='nearModule'  class='form-control text-input' type='text' style='width:70px;position:relative;'/></td>"
                + "<td name='nearPortNum'><input id='nearPortNum" + dBBoqIndex + "' name='nearPortNum'  class='form-control text-input' type='text' style='width:70px;position:relative;'/></td>"
                + "<td name='RowIndex'><input id='rowIndex" + dBBoqIndex + "' name='rowIndex'  class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
                + "<td name='ColIndex'><input id='colIndex" + dBBoqIndex + "' name='colIndex'  class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
                + "<td name='patchType'><input name='patchType'  class='form-control text-input' type='text' style='width:240px;position:relative;'/></td>"
                + "<td style='background-color:#00757C' width='-10px'></td>"
                + "<td name='FP_Status'><select class='form-control' name='FP_Status' id='FP_Status" + dBBoqIndex + "'><option value='None' selected>Select an Option</option><option value='Connected'>Connected</option><option value='Disconnected'>Disconnected</option><option value='Incomplete'>Incomplete</option></select></td>"
                + "<td name='FP_LocationType'>"
                + "<select class='form-control' name='FP_locationType' id='FP_LocationType" + dBBoqIndex + "'>"
                + "<option value='None' selected>Select an Option</option>"
                + "<option value='Customer'>Customer</option>"
                + "<option value='Site'>Site</option>"
                + "<option value='Manhole'>Manhole</option>"
                + "<option value='Handhole'>Handhole</option>"
                + "</select>"
                + "</td>"
                + "<td name='FP_LocationID'><input name='FP_locationID' id='FP_LocationID" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_LocationM'><input name='FP_locationM' id='FP_LocationM" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_Location'><input name='FP_locationM' id='FP_Location" + dBBoqIndex + "' class='form-control' type='text' readonly style='width:190px;position:relative;'/></td>"
                + "<td name='FP_Equipment'>"
                + "<select class='form-control' name='FP_equipment' id='FP_equipment" + dBBoqIndex + "'></select>"
                + "</td>"
                //+"<td name='FP_EquipmentType'><input name=' FP_equipmentType' id='FP_equipmentType"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_EquipmentID'><input name='FP_equipmentID' id='FP_equipmentID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_EquipmentName'><input name='FP_equipmentName' id='FP_equipmentName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_EquipmentType'><input name=' FP_equipmentType' id='FP_equipmentType" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='farKitSerialNum'><input name='farKitSerialNum' id='farKitSerialNum" + dBBoqIndex + "' class='form-control text-input' type='text' readonly style='width:190px;position:relative;'/></td>"
                + "<td name='farModule'><input name='farModule' id='farModule" + dBBoqIndex + "' class='form-control text-input' type='text' readonly style='width:190px;position:relative;'/></td>"
                + "<td name='farPortNum'><input name='farPortNum' id='farPortNum" + dBBoqIndex + "' class='form-control text-input' type='text' readonly style='width:190px;position:relative;'/></td>"
                + "<td name='FP_Address'><input name='FP_Address' id='FP_Address" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_JunctionID'><input name='FP_junctionID' id='FP_junctionID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_JunctionName'><input name='FP_junctionName' id='FP_junctionName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"

                //added
                + "<td name='FP_StrandNb'><input name='FP_strandNb' id='FP_strandNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='FP_StrandColor'>"
                + "<select class='form-control' name='FP_strandcolor' id='FP_strandcolor" + dBBoqIndex + "'>" + addMark4 + "</select>"
                + "</td>"
                + "<td name='FP_StrandID'><input name='FP_strandID' id='FP_strandID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_StrandName'><input name='FP_strandName' id='FP_strandName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_TubeNb'><input name='FP_tubeNb' id='FP_tubeNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;' /></td>"
                + "<td name='FP_TubeColor'>"
                + "<select class='form-control' name='FP_tubecolor' id='FP_tubecolor" + dBBoqIndex + "'>" + addMark4 + "</select>"
                + "</td>"
                + "<td name='FP_TubeID'><input name='FP_tubeID' id='FP_tubeID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_TubeName'><input name='FP_tubeName' id='FP_tubeName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_FiberID'><input name='FP_fiberID' id='FP_fiberID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_FiberName'><input name='FP_fiberName' id='FP_fiberName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td style='background-color:#00757C' width='-10px'></td>"
                + "<td name='BP_Status'><select class='form-control' name='BP_Status' id='BP_Status" + dBBoqIndex + "'><option value='None' selected>Select an Option</option><option value='Connected'>Connected</option><option value='Disconnected'>Disconnected</option><option value='Incomplete'>Incomplete</option></select></td>"
                + "<td name='BP_LocationType'>"
                + "<select class='form-control' name='BP_locationType' id='BP_LocationType" + dBBoqIndex + "'>"
                + "<option value='None' selected>Select an Option</option>"
                + "<option value='Customer'>Customer</option>"
                + "<option value='Site'>Site</option>"
                + "<option value='Manhole'>Manhole</option>"
                + "<option value='Handhole'>Handhole</option>"
                + "</select>"
                + "</td>"
                + "<td name='BP_LocationID'><input name='BP_locationID' id='BP_LocationID" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_LocationM'><input name='BP_locationM' id='BP_LocationM" + dBBoqIndex + "' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_Location'><input name='BP_locationM' id='BP_Location" + dBBoqIndex + "' class='form-control' type='text' readonly style='width:190px;position:relative;'/></td>"
                + "<td name='BP_Equipment'>"
                + "<select class='form-control' name='BP_equipment' id='BP_equipment" + dBBoqIndex + "'></select>"
                + "</td>"
                + "<td name='BP_EquipmentID'><input name='BP_equipmentID' id='BP_equipmentID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_EquipmentName'><input name='BP_equipmentName' id='BP_equipmentName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_EquipmentType'><input name=' BP_equipmentType' id='BP_equipmentType" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='backKitModule'><input name='backKitModule' id='backKitModule" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;' readonly/></td>"
                + "<td name='backPortNum'><input name='backPortNum' id='backPortNum" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;' readonly/></td>"
                + "<td name='BP_Address'><input name='BP_Address' id='BP_Address" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_JunctionID'><input name='BP_junctionID' id='BP_junctionID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_JunctionName'><input name='BP_junctionName' id='BP_junctionName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_StrandNb'><input name='BP_strandNb' id='BP_strandNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='BP_StrandColor'>"
                + "<select class='form-control' name='BP_strandcolor' id='BP_strandcolor" + dBBoqIndex + "'>" + addMark4 + "</select>"
                + "</td>"
                + "<td name='BP_StrandID'><input name='BP_strandID' id='BP_strandID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_StrandName'><input name='BP_strandName' id='BP_strandName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_TubeNb'><input name='BP_tubeNb' id='BP_tubeNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;' /></td>"
                + "<td name='BP_TubeColor'>"
                + "<select class='form-control' name='BP_tubecolor' id='BP_tubecolor" + dBBoqIndex + "'>" + addMark4 + "</select>"
                + "</td>"
                + "<td name='BP_TubeID'><input name='BP_tubeID' id='BP_tubeID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_TubeName'><input name='BP_tubeName' id='BP_tubeName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_FiberID'><input name='BP_fiberID' id='BP_fiberID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_FiberName'><input name='BP_fiberName' id='BP_fiberName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td></tr>"

            $("#DbMappingTable > tbody").append(markup);
            dBBoqIndex++;
            objDiv = document.getElementById("DbMappingTable");
            objDiv.scrollTop = objDiv.scrollHeight;
        }
        else {
            alert("Sorry no more available ports.")
        }
    });

    $("#delete_RackRow").click(function() {
        slctDelTemp = [];
        $("#DbMappingTable > tbody").find('input[name="record"]').each(function() {
            var portId = $(this).parent().parent().attr('id');

            if ($(this).is(":checked")) {
                delArray = [];
                delArray = [{ "portId": portId }];
                console.log(portId);
                if (window["DB_" + portId]) {
                    slctDel.push({ "portId": portId });
                }
                slctDelTemp.push({ "portId": portId });
            }
        });

        if (slctDelTemp.length == 0) {
            alert(' Select Row(s) to Delete');
            return false;
        }

        $("#DbMappingTable > tbody").find('input[name="record"]').each(function() {
            if ($(this).is(":checked")) {
                $(this).parents("tr").remove();
            }
        });
        console.log(slctDel);
    });

    $("#sortByIndex").click(function() {
        dbmappingdata = [];

        $("#DbMappingTable > tbody").find('input[name="record"]').each(function(i) {
            var index = $(this).find('input[name="Index"]').val();
            if (window["DB_Mapper" + selectedDistBoardContext] != "") {
                portId = $(this).parent().parent().attr('id');
                if (typeof portId == 'undefined') {
                    portId = "";
                }
            }
            var rowColIndex = $(this).parent().parent().children('td[name="Index"]').children('input').val();
            var rowNum = $(this).parent().parent().children('td[name="RowIndex"]').children('input').val();
            var colNum = $(this).parent().parent().children('td[name="ColIndex"]').children('input').val();
            var fP_Status = $(this).parent().parent().children('td[name="FP_Status"]').children('select').val();
            var fP_LocationType = $(this).parent().parent().children('td[name="FP_LocationType"]').children('select').val();
            var fP_LocationID = $(this).parent().parent().children('td[name="FP_LocationID"]').children('input').val();
            var fP_LocationM = $(this).parent().parent().children('td[name="FP_LocationM"]').children('input').val();
            var fP_Location = $(this).parent().parent().children('td[name="FP_Location"]').children('input').val();
            var fP_Equipment = $(this).parent().parent().children('td[name="FP_Equipment"]').children('select').val();
            var fP_EquipmentType = $(this).parent().parent().children('td[name="FP_EquipmentType"]').children('input').val();
            var fP_EquipmentID = $(this).parent().parent().children('td[name="FP_EquipmentID"]').children('input').val();
            var fP_EquipmentName = $(this).parent().parent().children('td[name="FP_EquipmentName"]').children('input').val();
            var fP_Address = $(this).parent().parent().children('td[name="FP_Address"]').children('input').val();
            var fP_StrandNb = $(this).parent().parent().children('td[name="FP_StrandNb"]').children('input').val();
            var fP_StrandColor = $(this).parent().parent().children('td[name="FP_StrandColor"]').children('select').val();
            var fP_StrandID = $(this).parent().parent().children('td[name="FP_StrandID"]').children('input').val();
            var fP_StrandName = $(this).parent().parent().children('td[name="FP_StrandName"]').children('input').val();
            var fP_TubeNb = $(this).parent().parent().children('td[name="FP_TubeNb"]').children('input').val();
            var fP_TubeColor = $(this).parent().parent().children('td[name="FP_TubeColor"]').children('select').val();
            var fP_TubeID = $(this).parent().parent().children('td[name="FP_TubeID"]').children('input').val();
            var fP_TubeName = $(this).parent().parent().children('td[name="FP_TubeName"]').children('input').val();
            var fP_FiberID = $(this).parent().parent().children('td[name="FP_FiberID"]').children('input').val();
            var fP_FiberName = $(this).parent().parent().children('td[name="FP_FiberName"]').children('input').val();
            var bP_LocationType = $(this).parent().parent().children('td[name="BP_LocationType"]').children('select').val();
            var bP_LocationID = $(this).parent().parent().children('td[name="BP_LocationID"]').children('input').val();
            var bP_LocationM = $(this).parent().parent().children('td[name="BP_LocationM"]').children('input').val();
            var bP_Location = $(this).parent().parent().children('td[name="BP_Location"]').children('input').val();
            var bP_Equipment = $(this).parent().parent().children('td[name="BP_Equipment"]').children('select').val();
            var bP_EquipmentType = $(this).parent().parent().children('td[name="BP_EquipmentType"]').children('input').val();
            var bP_EquipmentID = $(this).parent().parent().children('td[name="BP_EquipmentID"]').children('input').val();
            var bP_EquipmentName = $(this).parent().parent().children('td[name="BP_EquipmentName"]').children('input').val();
            var bP_Address = $(this).parent().parent().children('td[name="BP_Address"]').children('input').val();
            var bP_Status = $(this).parent().parent().children('td[name="BP_Status"]').children('select').val();
            var bP_StrandNb = $(this).parent().parent().children('td[name="BP_StrandNb"]').children('input').val();
            var bP_StrandColor = $(this).parent().parent().children('td[name="BP_StrandColor"]').children('select').val();
            var bP_StrandID = $(this).parent().parent().children('td[name="BP_StrandID"]').children('input').val();
            var bP_StrandName = $(this).parent().parent().children('td[name="BP_StrandName"]').children('input').val();
            var bP_TubeNb = $(this).parent().parent().children('td[name="BP_TubeNb"]').children('input').val();
            var bP_TubeColor = $(this).parent().parent().children('td[name="BP_TubeColor"]').children('select').val();
            var bP_TubeID = $(this).parent().parent().children('td[name="BP_TubeID"]').children('input').val();
            var bP_TubeName = $(this).parent().parent().children('td[name="BP_TubeName"]').children('input').val();
            var bP_FiberID = $(this).parent().parent().children('td[name="BP_FiberID"]').children('input').val();
            var bP_FiberName = $(this).parent().parent().children('td[name="BP_FiberName"]').children('input').val();
            var fP_JunctionID = $(this).parent().parent().children('td[name="FP_JunctionID"]').children('input').val();
            var fP_JunctionName = $(this).parent().parent().children('td[name="FP_JunctionName"]').children('input').val();
            var bP_JunctionID = $(this).parent().parent().children('td[name="BP_JunctionID"]').children('input').val();
            var bP_JunctionName = $(this).parent().parent().children('td[name="BP_JunctionName"]').children('input').val();

            var nearModule = $(this).parent().parent().children('td[name="nearModule"]').children('input').val();
            var nearPortNum = $(this).parent().parent().children('td[name="nearPortNum"]').children('input').val();
            var nearPatchType = $(this).parent().parent().children('td[name="patchType"]').children('input').val();

            var farKitSerialNum = $(this).parent().parent().children('td[name="farKitSerialNum"]').children('input').val();
            var farModule = $(this).parent().parent().children('td[name="farModule"]').children('input').val();
            var farPortNum = $(this).parent().parent().children('td[name="farPortNum"]').children('input').val();

            var backKitModule = $(this).parent().parent().children('td[name="backKitModule"]').children('input').val();
            var backPortNum = $(this).parent().parent().children('td[name="backPortNum"]').children('input').val();

            dbmappingdata.push([Number(rowColIndex),
            Number(rowNum),
            Number(colNum),
                portId,
                fP_Status,
                fP_LocationType,
                fP_LocationID,
                fP_LocationM,
                fP_Location,
                fP_EquipmentType,
                fP_Equipment,
                fP_EquipmentID,
                fP_EquipmentName,
                fP_Address,
                bP_Status,
                bP_StrandID,
                bP_StrandName,
                bP_TubeID,
                bP_TubeName,
                bP_FiberID,
                bP_FiberName,
                fP_StrandID,
                fP_StrandName,
                fP_TubeID,
                fP_TubeName,
                fP_FiberID,
                fP_FiberName,
                bP_LocationType,
                bP_LocationID,
                bP_LocationM,
                bP_Location,
                bP_EquipmentType,
                bP_Equipment,
                bP_EquipmentID,
                bP_EquipmentName,
                bP_Address,
                fP_StrandNb,
                fP_TubeNb,
                bP_StrandNb,
                bP_TubeNb,
                fP_StrandColor,
                fP_TubeColor,
                bP_StrandColor,
                bP_TubeColor,
                fP_JunctionID,
                fP_JunctionName,
                bP_JunctionID,
                bP_JunctionName,
                nearModule, nearPortNum, nearPatchType,
                farKitSerialNum, farModule, farPortNum,
                backKitModule, backPortNum
            ]);
        });

        var totalmodules = Number($("#DistributionBoardNumModules"));
        var rowsPerModule = Number($("#DistributionBoardRowPerModule"));

        var panelInfo = [rowsPerModule, totalmodules];
        //dbmappingdata.sort(sortFunction);
        dbmappingdata = dbmappingdata.sort((a, b) => a[0] - b[0]);
        $("#DbMappingTable > tbody").empty();
        DBMappingData(dbmappingdata, panelInfo);
    });

    if (writeDB === '1') {
        $("#BP_assignCable").autocomplete({
            source: function(request, response, event, ui) {
                var searchId = $("#BP_assignCable").val();
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

            }, minLength: 0, maxShowItems: 4, scroll: true,
            select: function(event, ui) {
                fibercable_arr = [];
                $("#BP_assignCable").val(ui.item[0]);
                fibercable_arr.push(ui.item[1]);
                fibercable_arr.push(ui.item[0]);

                return false;
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            return $('<li class="each"></li>').data("ui-autocomplete-item", item)
                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                    item[0] + '</span><br><span class="desc">' +
                    item[1] + '</span></div>').appendTo(ul);
        };
        $("#BP_assignCable").focus(function() {
            if (this.value == "") {
                $(this).autocomplete("search");
            }
        });

        $("#assign_Cable").on('click', function() {
            var selected_port = $('#selected_Port').find(":selected").val();
            $('#selected_Port').children('option[value="title"]').css('display', 'none');
            if (fibercable_arr.length) {
                $("#DbMappingTable > tbody").find('input[name="record"]').each(function() {
                    if ($(this).is(":checked")) {
                        if (selected_port == "frontPort") {
                            $(this).parent().parent().children('td[name="FP_FiberID"]').children('input').val(fibercable_arr[1]);
                            $(this).parent().parent().children('td[name="FP_FiberName"]').children('input').val(fibercable_arr[0]);
                        }
                        else if (selected_port == "backPort") {
                            $(this).parent().parent().children('td[name="BP_FiberID"]').children('input').val(fibercable_arr[1]);
                            $(this).parent().parent().children('td[name="BP_FiberName"]').children('input').val(fibercable_arr[0]);
                        }
                    }
                });
            }
        });

        $("#BP_assignTube").autocomplete({
            source: function(request, response, event, ui) {
                searchId = $("#BP_assignTube").val();

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
                            //console.log(data.glist);
                        }
                    },
                    error: function(result) {
                        alert("Error");
                    }
                });

            }, minLength: 0, maxShowItems: 4, scroll: true,
            select: function(event, ui) {
                fibertube_arr = [];
                $("#BP_assignTube").val(ui.item[0]);
                fibertube_arr.push(ui.item[1]);
                fibertube_arr.push(ui.item[0]);
                fibertube_arr.push(ui.item[4]);
                fibertube_arr.push(ui.item[5]);
                return false;
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            return $('<li class="each"></li>').data("ui-autocomplete-item", item)
                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                    item[0] + '</span><br><span class="desc">' +
                    item[1] + '</span></div>').appendTo(ul);
        };

        $("#BP_assignTube").focus(function() {
            if (this.value == "") {
                $(this).autocomplete("search");
            }
        });

        $("#assign_Tube").on('click', function() {
            var selected_port = $('#selected_Port').find(":selected").val();
            $('#selected_Port').children('option[value="title"]').css('display', 'none');
            if (fibertube_arr.length > 0) {
                $("#DbMappingTable > tbody").find('input[name="record"]').each(function(i) {
                    if ($(this).is(":checked")) {
                        if (selected_port == "frontPort") {
                            $(this).parent().parent().children('td[name="FP_TubeID"]').children('input').val(fibertube_arr[1]);
                            $(this).parent().parent().children('td[name="FP_TubeName"]').children('input').val(fibertube_arr[0]);
                            $("#FP_tubecolor" + i).val(fibertube_arr[3]);
                            tubeStrandSetColor("FP_tubecolor" + i, "FP_tubeNb" + i);
                        }
                        else if (selected_port == "backPort") {
                            $(this).parent().parent().children('td[name="BP_TubeID"]').children('input').val(fibertube_arr[1]);
                            $(this).parent().parent().children('td[name="BP_TubeName"]').children('input').val(fibertube_arr[0]);
                            $("#BP_tubecolor" + i).val(fibertube_arr[3]);
                            tubeStrandSetColor("BP_tubecolor" + i, "BP_tubeNb" + i);;
                        }
                    }
                });
            }
        });
    }
});