function initTrunchEvents() {
    console.log("Hello, just before putting click even in trench initialization");

    trenchAuxTabEvents();
    $("#saveTrench").on('click', function() {
        saveTrenchPath();
    });

    $("#generateTrenchFromPath").on("click", function() {
        console.log("Welcome to generate trench from Path");
        $("#trenchGenCableId").val("");
        $("#trenchGenCableName").val("");
        $("#appendToTrenchAuxPoints").prop("checked", false);
        $("#generateTrenchModal").modal("show");
    });

    console.log("Just before auto complete of trencGenCableId");

    pathGenCableIdAutoComplete("trenchGenCableId", "trenchGenCableName");
    pathGenCableNameAutoComplete("trenchGenCableId", "trenchGenCableName");

    /*	
        $("#trenchGenCableId").autocomplete({
            appendTo: "#generateTrenchModal",
            source: debounce(function(request, response, event, ui) {
                console.log("REQUEST FIRED");
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/SearchFiber',
                    data: {
                        "searchId": $("#trenchGenCableId").val(),
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
            position: {
                my: "left top",
                at: "left bottom",
                collision: "flipfit"
            },
            select: function(event, ui) {
                this.value = (ui.item ? ui.item[0] : '');
                $("#trenchGenCableName").val(ui.item[1]);
                return false;
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            return $('<li class="each"></li>').data("ui-autocomplete-item", item)
                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                    item[0] + '</span><br><span class="desc">' +
                    item[1] + '</span></div>').appendTo(ul);
        };
        $("#trenchGenCableId").focus(function() {
            console.log("Focus on trenchGenCableId");
            if (this.value == "") {
                $(this).autocomplete("search");
            }
        });
        ///////////////////////////////
        $("#trenchGenCableName").autocomplete({
            appendTo: "#generateTrenchModal",
            source: debounce(function(request, response, event, ui) {
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: getContext() + '/SearchFiber',
                    data: {
                        "searchId": $("#trenchGenCableName").val(),
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
            position: {
                my: "left top",
                at: "left bottom",
                collision: "flipfit"
            },
            select: function(event, ui) {
                this.value = (ui.item ? ui.item[1] : '');
                $("#trenchGenCableId").val(ui.item[0]);
                return false;
            }
        }).data("ui-autocomplete")._renderItem = function(ul, item) {
            return $('<li class="each"></li>').data("ui-autocomplete-item", item)
                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                    item[1] + '</span><br><span class="desc">' +
                    item[0] + '</span></div>').appendTo(ul);
        };
        $("#trenchGenCableName").focus(function() {
            if (this.value == "") {
                $(this).autocomplete("search");
            }
        });
    */

    $("#confirmGenerateTrench").on('click', function() {
        console.log("Welcome generate from trench confirm");
        if ($("#trenchGenCableId").val().trim() == "" || $("#trenchGenCableName").val().trim() == "") {
            alert("Please select the fiber path to generate from it");
            return;
        }

        console.log("Just before send ajax request");

        var token = $('input[name="csrfToken"]').attr('value');
        $.ajax({
            type: "POST",
            headers: {
                'X-CSRFToken': token
            },
            url: getContext() + "/getPathForTrenchGen",
            data: {
                ID: $("#trenchGenCableId").val(),
                append: $("#appendToTrenchAuxPoints").is(":checked")
            },
            beforeSend: function() {
                $("#loaderDivTrench").show();
            },
            success: function(data) {
                $("#loaderDivTrench").hide();
                $("#generateTrenchModal").modal("hide");
                console.log("Success of the ajax");
                indextrench = 0;
                if (data && data.auxData) {
                    AuxAppendBOQ(data.auxData, "", "", TargetTrench, indextrench);
                }

                const objDiv = document.getElementById("auxiliary_trenchTable");
                objDiv.scrollTop = objDiv.scrollHeight;
                calculateDistanceSourceDestination(
                    $("#SourceTrenchLat").val(),
                    $("#SourceTrenchLng").val(),
                    $("#DestinationTrenchLat").val(),
                    $("#DestinationTrenchLng").val(),
                    "auxiliary_trenchTable"
                );
            },
            error: function(xhr, status, error) {
                $("#loaderDivTrench").hide();
                console.error(error);
                alert("Error");
            }
        });
    });
}

function pathGenCableIdAutoComplete(fiberPathId, fiberPathName) {
    // Arguments here are the div id of cable id and cable name.	
    $("#" + fiberPathId).autocomplete({
        //appendTo: "#generateTrenchModal",
        source: debounce(function(request, response, event, ui) {
            console.log("REQUEST FIRED");
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/SearchFiber',
                data: {
                    "searchId": $("#" + fiberPathId).val(),
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
        position: {
            my: "left top",
            at: "left bottom",
            collision: "flipfit"
        },
        select: function(event, ui) {
            this.value = (ui.item ? ui.item[0] : '');
            $("#" + fiberPathName).val(ui.item[1]);
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>').data("ui-autocomplete-item", item)
            .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                item[0] + '</span><br><span class="desc">' +
                item[1] + '</span></div>').appendTo(ul);
    };
    $("#" + fiberPathId).focus(function() {
        console.log("Focus on Auto Complete for Cable ID Generate From Path with ID: ", fiberPathId);
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });
}

function pathGenCableNameAutoComplete(fiberPathId, fiberPathName) {
    // Arguments here are the div id of cable id and cable name.
    $("#" + fiberPathName).autocomplete({
        //appendTo: "#generateTrenchModal",
        source: debounce(function(request, response, event, ui) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/SearchFiber',
                data: {
                    "searchId": $("#" + fiberPathName).val(),
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
        position: {
            my: "left top",
            at: "left bottom",
            collision: "flipfit"
        },
        select: function(event, ui) {
            this.value = (ui.item ? ui.item[1] : '');
            $("#" + fiberPathId).val(ui.item[0]);
            return false;
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $('<li class="each"></li>').data("ui-autocomplete-item", item)
            .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                item[1] + '</span><br><span class="desc">' +
                item[0] + '</span></div>').appendTo(ul);
    };
    $("#" + fiberPathName).focus(function() {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });
}