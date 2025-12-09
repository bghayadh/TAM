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
	console.log("number is " +number);

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
function generateEquipmentOptions(locationType, includeDefault = true) {
    const list = EQUIPMENT_MAP[locationType] || [];
    let opts = "";
    if (includeDefault) opts += `<option value="">Select an Option</option>`;
    list.forEach(v => { opts += `<option value="${v}">${v}</option>`; });
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
            /*backKitModule.prop("readonly", true);
            backPortNumInput.prop("readonly", true);*/
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