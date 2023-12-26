 function addRowToNode() {
    rowcountNode = $("#ToNodeTable >tbody tr").length;
    console.log("rowcountNode:" + rowcountNode);

    var ctx = getContextPath(); // Define or replace this function

    var markup =
        "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td><td name='serialNumber'>" +
        "<input name='NodeId'  class='form-control text-input' type='text' id=" + "NodeId_" + rowcountNode + " style='width:200px;position:relative;left:11px;'/></td>" +
        "<td name='NodeName' style='width:200px'> <input name='NodeName' id=" + "NodeName_" + rowcountNode + " style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>" +
        "<td name='NodeType' style='width:200px'> <input name='NodeType' id=" + "NodeType_" + rowcountNode + " style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

    $("#ToNodeTable > tbody").append(markup);

 $('#NodeId_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: {
                    Node: request.term
                },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log(222);
                }
            });
        },
        minLength: 0,
        maxResults: 40, // Use maxResults instead of maxShowItems
        scroll: true,

        select: function (event, ui) {
            this.value = ui.item ? ui.item[2] : '';
            document.getElementById("NodeName_" + rowcountNode).value = ui.item[3];
            document.getElementById("NodeType_" + rowcountNode).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
                    item[2] + "</b></span><br><span class='name' >" +
                    item[3] + ", " + item[4] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#NodeId_' + rowcountNode).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });







   $('#NodeName_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: {
                    Node: request.term
                },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log(222);
                }
            });
        },
        minLength: 0,
        maxResults: 40, // Use maxResults instead of maxShowItems
        scroll: true,

        select: function (event, ui) {
             this.value = ui.item ? ui.item[3] : '';
            document.getElementById("NodeId_" + rowcountNode).value = ui.item[2];
            document.getElementById("NodeType_" + rowcountNode).value = ui.item[4];
           return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
                    item[3] + "</b></span><br><span class='name' >" +
                    item[2] + ", " + item[4] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#NodeName_' + rowcountNode).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });



    
    
   $('#NodeType_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: {
                    Node: request.term
                },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log(222);
                }
            });
        },
        minLength: 0,
        maxResults: 40, // Use maxResults instead of maxShowItems
        scroll: true,

        select: function (event, ui) {
            this.value = ui.item ? ui.item[4] : '';
            document.getElementById("NodeId_" + rowcountNode).value = ui.item[2];
            document.getElementById("NodeName_" + rowcountNode).value = ui.item[3];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
                    item[4] + "</b></span><br><span class='name' >" +
                    item[2] + ", " + item[3] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#NodeType_' + rowcountNode).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });
}
 