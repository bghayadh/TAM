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

        for (i = 0;i < DistBoardMappingPts.length;i++) {

            const fpLocationOptions = generateLocationOptions(DistBoardMappingPts[i][5]);
            const fpEquipmentOptions = generateEquipmentOptions(DistBoardMappingPts[i][5], DistBoardMappingPts[i][10]);

            // BP
            const bpLocationOptions = generateLocationOptions(DistBoardMappingPts[i][27]);
            const bpEquipmentOptions = generateEquipmentOptions(DistBoardMappingPts[i][27], DistBoardMappingPts[i][32]);

            const fpLocType = DistBoardMappingPts[i][5];
            const fpLocationReadonly =
                (fpLocType === "Manhole" || fpLocType === "Handhole" || fpLocType === "Customer") ? "readonly" : "";

            const bpLocType = DistBoardMappingPts[i][27];
            const bpLocationReadonly = (bpLocType === "Manhole" || bpLocType === "Handhole" || bpLocType === "Customer") ? "readonly" : "";

            const fpEqIsDist = DistBoardMappingPts[i][10] === "DistBoard";
            const fpReadonly = fpEqIsDist ? "" : "readonly";

            const bpEqIsDist = DistBoardMappingPts[i][32] === "DistBoard";
            const bpReadonly = bpEqIsDist ? "" : "readonly";

            const fpStrandNumber = DistBoardMappingPts[i][36];  // e.g. "3"
            const fpStrandData = getColorByNumber(fpStrandNumber);
            const fpStrandColor = fpStrandData.color;
            const fpStrandTextColor = fpStrandData.text;

            const fpTubeNumber = DistBoardMappingPts[i][37];  // e.g. "3"
            const fpTubeData = getColorByNumber(fpTubeNumber);
            const fpTubeColor = fpTubeData.color;
            const fpTubeTextColor = fpTubeData.text;

            const bpStrandNumber = DistBoardMappingPts[i][38];  // e.g. "3"
            const bpStrandData = getColorByNumber(bpStrandNumber);
            const bpStrandColor = bpStrandData.color;
            const bpStrandTextColor = bpStrandData.text;

            const bpTubeNumber = DistBoardMappingPts[i][39];  // e.g. "3"
            const bpTubeData = getColorByNumber(bpTubeNumber);
            const bpTubeColor = bpTubeData.color;
            const bpTubeTextColor = bpTubeData.text;

            window["DB_" + DistBoardMappingPts[i][3]] = [];
            window["DB_" + DistBoardMappingPts[i][3]] = DistBoardMappingPts[i];

            var f_statusOption = "", b_statusOption = "";
            if (DistBoardMappingPts[i][4] == "Active") {
                f_statusOption = "<option value='Active' selected >Active</option><option value='InActive'>Inactive</option>";
            }
            else if (DistBoardMappingPts[i][4] == "InActive") {
                f_statusOption = "<option value='InActive' selected >Inactive</option><option value='Active'>Active</option>";
            }
            else {
                f_statusOption = "<option value='None' selected>Select an Option</option><option value='Active'>Active</option><option value='InActive'>Inactive</option>";
            }

            if (DistBoardMappingPts[i][14] == "Active") {
                b_statusOption = "<option value='Active' selected >Active</option><option value='InActive'>Inactive</option>";
            }
            else if (DistBoardMappingPts[i][14] == "InActive") {
                b_statusOption = "<option value='InActive' selected >Inactive</option><option value='Active'>Active</option>";
            }
            else {
                b_statusOption = "<option value='None' selected>Select an Option</option><option value='Active'>Active</option><option value='InActive'>Inactive</option>";
            }
            let backKitVal = DistBoardMappingPts[i][54] ? DistBoardMappingPts[i][54] : "";
            let backPortVal = DistBoardMappingPts[i][55] ? DistBoardMappingPts[i][55] : "";

            markup += "<tr id='" + DistBoardMappingPts[i][3] + "'><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='Index'><input id='index" + i + "' name='Index' value='" + DistBoardMappingPts[i][0] + "' class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
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
                + "<td name='patchType'><input name='patchType' value='" + DistBoardMappingPts[i][50] + "' class='form-control text-input portIndex' type='text' style='width:240px;position:relative;'/></td>"
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
                + "<td name='farKitSerialNum'><input name='farKitSerialNum' value='" + DistBoardMappingPts[i][51] + "' id='farKitSerialNum" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + fpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='farModule'><input name='farModule' value='" + DistBoardMappingPts[i][52] + "' id='farModule" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + fpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='farPortNum'><input name='farPortNum' value='" + DistBoardMappingPts[i][53] + "' id='farPortNum" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + fpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='FP_Address'><input name='FP_Address' value='" + DistBoardMappingPts[i][13] + "' id='FP_Address" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_JunctionID'><input name='FP_junctionID' value='" + DistBoardMappingPts[i][44] + "' id='FP_junctionID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_JunctionName'><input name='FP_junctionName' value='" + DistBoardMappingPts[i][45] + "' id='FP_junctionName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_StrandNb'><input name='FP_strandNb' value='" + fpStrandNumber + "' id='FP_strandNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='FP_StrandColor'>"
                + "<select class='form-control' name='FP_strandcolor' id='FP_strandcolor" + dBBoqIndex + "' "
                + "style='background-color:" + fpStrandColor + "; color:" + fpStrandTextColor + "'>"
                + colorOptions(fpStrandColor)
                + "</select>"
                + "</td>"
                + "<td name='FP_StrandID'><input name='FP_strandID' value='" + DistBoardMappingPts[i][21] + "' id='FP_strandID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_StrandName'><input name='FP_strandName' value='" + DistBoardMappingPts[i][22] + "' id='FP_strandName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='FP_TubeNb'><input name='FP_tubeNb' value='" + fpTubeNumber + "' id='FP_tubeNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='FP_TubeColor'>"
                + "<select class='form-control' name='FP_tubecolor' id='FP_tubecolor" + dBBoqIndex + "' "
                + "style='background-color:" + fpTubeColor + "; color:" + fpTubeTextColor + "'>"
                + colorOptions(fpTubeColor)
                + "</select>"
                + "</td>"
                + "<td name='FP_TubeID'><input name='FP_tubeID' value='" + DistBoardMappingPts[i][23] + "' id='FP_tubeID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_TubeName'><input name='FP_tubeName' value='" + DistBoardMappingPts[i][24] + "' id='FP_tubeName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_FiberID'><input name='FP_fiberID' value='" + DistBoardMappingPts[i][25] + "' id='FP_fiberID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='FP_FiberName'><input name='FP_fiberName' value='" + DistBoardMappingPts[i][26] + "' id='FP_fiberName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
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
                + "<td name='backKitModule'><input name='backKitModule' value='" + backKitVal + "' id='backKitModule" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + bpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='backPortNum'><input name='backPortNum' value='" + backPortVal + "' id='backPortNum" + dBBoqIndex + "' class='form-control text-input' type='text' "
                + bpReadonly + " style='width:190px;position:relative;'/></td>"
                + "<td name='BP_Address'><input name='BP_Address' value='" + DistBoardMappingPts[i][35] + "'id='BP_Address" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_JunctionID'><input name='BP_junctionID' value='" + DistBoardMappingPts[i][46] + "' id='BP_junctionID" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_JunctionName'><input name='BP_junctionName' value='" + DistBoardMappingPts[i][47] + "' id='BP_junctionName" + dBBoqIndex + "' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_StrandNb'><input name='BP_strandNb' value='" + bpStrandNumber + "' id='BP_strandNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='BP_StrandColor'>"
                + "<select class='form-control' name='BP_strandcolor' id='BP_strandcolor" + dBBoqIndex + "' "
                + "style='background-color:" + bpStrandColor + "; color:" + bpStrandTextColor + "'>"
                + colorOptions(bpStrandColor)
                + "</select>"
                + "</td>"
                + "<td name='BP_StrandID'><input name='BP_strandID' value='" + DistBoardMappingPts[i][15] + "' id='BP_strandID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_StrandName'><input name='BP_strandName' value='" + DistBoardMappingPts[i][16] + "' id='BP_strandName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
                + "<td name='BP_TubeNb'><input name='BP_tubeNb' value='" + bpTubeNumber + "' id='BP_tubeNb" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
                + "<td name='BP_TubeColor'>"
                + "<select class='form-control' name='BP_tubecolor' id='BP_tubecolor" + dBBoqIndex + "' "
                + "style='background-color:" + bpTubeColor + "; color:" + bpTubeTextColor + "'>"
                + colorOptions(bpTubeColor)
                + "</select>"
                + "</td>"
                + "<td name='BP_TubeID'><input name='BP_tubeID' value='" + DistBoardMappingPts[i][17] + "' id='BP_tubeID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_TubeName'><input name='BP_tubeName' value='" + DistBoardMappingPts[i][18] + "' id='BP_tubeName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_FiberID'><input name='BP_fiberID' value='" + DistBoardMappingPts[i][19] + "' id='BP_fiberID" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
                + "<td name='BP_FiberName'><input name='BP_fiberName' value='" + DistBoardMappingPts[i][20] + "' id='BP_fiberName" + dBBoqIndex + "'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td></tr>"
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

    return numberMap[num] || { color: "white", text: "black" };
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
        //console.log("size of colorField is " +colorField.length);
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
    "Select an Option": []   // fallback
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
    const list = EQUIPMENT_MAP[locationType] || [];
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