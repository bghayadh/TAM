function checkToNode() {
    var Data = {};
    Data.toNodeArray = [];

    $("#ToNodeTable > tbody > tr").find('input[name="record"]').each(function () {
        var ID = $(this).parent().parent().children('td[name="NodeId"]').children('input')[0].value;
        if (ID === '') {
      
            $(this).parents("tr").remove();
        } else {
            var node_Id = $(this).parent().parent().children('td[name="NodeId"]').children('input').val();
            var node_Name = $(this).parent().parent().children('td[name="NodeName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeType"]').children('input').val();

            Data.toNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
        }
    });

    // Assuming that rowindx is defined globally
    $("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="toNode"]').children('input')[0].value = JSON.stringify(Data);
}

function checkFromNode() {
    var Data = {};
    Data.fromNodeArray = [];

    $("#FromNodeTable > tbody > tr").find('input[name="record"]').each(function () {
        var ID = $(this).parent().parent().children('td[name="NodeId"]').children('input')[0].value;
        if (ID === '') {
            $(this).parents("tr").remove();
        } else {
            var node_Id = $(this).parent().parent().children('td[name="NodeId"]').children('input').val();
            var node_Name = $(this).parent().parent().children('td[name="NodeName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeType"]').children('input').val();

            Data.fromNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
        }
    });

    // Assuming that rowindx is defined globally
    $("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="fromNode"]').children('input')[0].value = JSON.stringify(Data);
}


function addRowToNode() {
    checkToNode();
    var rowcountNode = $("#ToNodeTable >tbody tr").length;
    console.log("rowcountNode:" + rowcountNode);

    var ctx = getContextPath();

    var markup =
        "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td><td name='NodeId'>" +
        "<input name='NodeId' class='form-control text-input' type='text' id='NodeId_" + rowcountNode + "' style='width:200px;position:relative;left:11px;'/></td>" +
        "<td name='NodeName' style='width:200px'> <input name='NodeName' id='NodeName_" + rowcountNode + "' style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>" +
        "<td name='NodeType' style='width:200px'> <input name='NodeType' id='NodeType_" + rowcountNode + "' style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

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
        maxResults: 40,
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
        maxResults: 40,
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
        maxResults: 40,
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
    
       var Data = {};
    Data.toNodeArray = [];
$("#ToNodeTable > tbody > tr").find('input[name="record"]').each(function () {
           var node_Id = $(this).parent().parent().children('td[name="NodeId"]').children('input').val();
            var node_Name = $(this).parent().parent().children('td[name="NodeName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeType"]').children('input').val();

            Data.toNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
        
    });


    // Assuming that rowindx is defined globally
    $("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="toNode"]').children('input')[0].value = JSON.stringify(Data);
        
}


function addRowFromNode() {
    checkFromNode();

    var rowcountNode = $("#FromNodeTable >tbody tr").length;
    console.log("rowcountNode:" + rowcountNode);

    var ctx = getContextPath();

    var markup =
        "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td><td name='NodeId'>" +
        "<input name='NodeId' class='form-control text-input' type='text' id='fromNodeId_" + rowcountNode + "' style='width:200px;position:relative;left:11px;'/></td>" +
        "<td name='NodeName' style='width:200px'> <input name='NodeName' id='fromNodeName_" + rowcountNode + "' style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>" +
        "<td name='NodeType' style='width:200px'> <input name='NodeType' id='fromNodeType_" + rowcountNode + "' style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

    $("#FromNodeTable > tbody").append(markup);

    $('#fromNodeId_' + rowcountNode).autocomplete({
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
        maxResults: 40,
        scroll: true,

        select: function (event, ui) {
            this.value = ui.item ? ui.item[2] : '';
            document.getElementById("fromNodeName_" + rowcountNode).value = ui.item[3];
            document.getElementById("fromNodeType_" + rowcountNode).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
                    item[2] + "</b></span><br><span class='name' >" +
                    item[3] + ", " + item[4] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#fromNodeId_' + rowcountNode).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });

    $('#fromNodeName_' + rowcountNode).autocomplete({
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
        maxResults: 40,
        scroll: true,

        select: function (event, ui) {
             this.value = ui.item ? ui.item[3] : '';
            document.getElementById("fromNodeId_" + rowcountNode).value = ui.item[2];
            document.getElementById("fromNodeType_" + rowcountNode).value = ui.item[4];
           return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
                    item[3] + "</b></span><br><span class='name' >" +
                    item[2] + ", " + item[4] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#fromNodeName_' + rowcountNode).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });

    $('#fromNodeType_' + rowcountNode).autocomplete({
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
        maxResults: 40,
        scroll: true,

        select: function (event, ui) {
            this.value = ui.item ? ui.item[4] : '';
            document.getElementById("fromNodeId_" + rowcountNode).value = ui.item[2];
            document.getElementById("fromNodeName_" + rowcountNode).value = ui.item[3];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
                    item[4] + "</b></span><br><span class='name' >" +
                    item[2] + ", " + item[3] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#fromNodeType_' + rowcountNode).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });
     var Data = {};
    Data.fromNodeArray = [];

    $("#FromNodeTable > tbody > tr").find('input[name="record"]').each(function () {
           var node_Id = $(this).parent().parent().children('td[name="NodeId"]').children('input').val();
            var node_Name = $(this).parent().parent().children('td[name="NodeName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeType"]').children('input').val();

            Data.fromNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
        
    });

    // Assuming that rowindx is defined globally
    $("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="fromNode"]').children('input')[0].value = JSON.stringify(Data);
    
}

$(".delete-To-node").click(function () {
 slctDel = [];
 
 $("#ToNodeTable > tbody").find('input[name="record"]').each(function () {
 if ($(this).is(":checked")) {
	slctDel.push($(this).parent().parent().children('td[name="toNode"]').children('input').val());
	console.log("The selected delete is " + slctDel);
	 
	 if(allDelSerials.includes($(this).parent().parent().children('td[name="toNode"]').children('input').val())==false) {
	 	allDelSerials.push($(this).parent().parent().children('td[name="toNode"]').children('input').val());
     }
 }
 });
	console.log("The selected delete after is " + slctDel);

	
 	if (slctDel.length == 0) {
    	alert(' Select Row(s) to Delete');
        return false;
     
     }
	$("#ToNodeTable > tbody").find('input[name="record"]').each(function () {
    	if ($(this).is(":checked")) {
       	$(this).parents("tr").remove();
       
       	}
     

	});
			var Data = {};
    Data.toNodeArray = [];
$("#ToNodeTable > tbody > tr").find('input[name="record"]').each(function () {
           var node_Id = $(this).parent().parent().children('td[name="NodeId"]').children('input').val();
            var node_Name = $(this).parent().parent().children('td[name="NodeName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeType"]').children('input').val();

            Data.toNodeArray.push({ NodeId: Node_Id, NodeName: Node_Name, NodeType: Node_Type });
        
    });


   $("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="toNode"]').children('input')[0].value = JSON.stringify(Data);
         });


