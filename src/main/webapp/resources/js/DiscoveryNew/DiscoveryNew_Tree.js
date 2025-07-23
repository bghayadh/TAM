var ListManagers = null;
var getApproval ="";
var searchPopupPerm = '1';
var findConnedtedPerm='1';
var projects='1';
var readManhole='1';
var writeManhole='1';
var addManhole='1';
var addHandhole='1';
var delManhole='1';
var readHandhole='1';
var writeHandhole='1';
var delHandhole='1';
var readFiber='1';
var writeFiber='1';
var addFiber='1';
var delFiber='1';
var readDB='1';
var writeDB='1';
var addDB='1';
var delDB='1';
// STEP 1: Define the menu
$(document).on('change', '.AllManagers', function () {
    // Get whether the checkbox was checked or not
    const isChecked = $(this).prop('checked');

    // Find all checkbox inputs within the same parent <li>
    $(this).closest('li')
        .find("input[type='checkbox']")
        .not(this) // exclude the parent itself
        .prop('checked', isChecked);
});

function CreateTree_DiscoveryNew(listProjectManager,listAssetManager,listFinanceManager) {

    // Create the initial tree structure for "Discovery New"
    var str_CurrentTree = "<ul style='margin-left:15px;'><li id='initial_ul_CurrentDiscoveryLayer' class='Initial_CurrentDiscoveryLayer'><input type='checkbox' class='allElements' unchecked name='filter'></input> <span id='initial_Span_CurrentDiscoveryLayer' class='Parentfolder'> <i class='fa fa-folder' style='color: #08526D;'></i></span><span class='TreeSpan' style='color:black;width:436px'>Discovery New </span></li></ul>";
    $("#discoveryNew_tree").append(str_CurrentTree);

    // Add Project Manager folder
    if (readProjectM === '1') {
        var str = "<ul><li id='ProjectManager_f_CurrentDiscoveryLayer' style='display:none;' class='ProjectManager_f_CurrentDiscoveryLayer'><input type='checkbox' unchecked class='AllManagers checkFilter'></input> <span id='ProjectManager_spanFolder' class='Parentfolder'><i class='fa fa-folder' style='color: #08526D'></i></span><span id='ProjectManager_span' class='TreeSpan' style='color:black;width:395px'>Project/Operation Manager </span></li></ul>";
        $("#initial_ul_CurrentDiscoveryLayer").append(str);
    }

    // Add Asset Manager folder
    if (readAssetM === '1') {
        var str = "<ul><li id='AssetManager_f_CurrentDiscoveryLayer' style='display:none;' class='AssetManager_f_CurrentDiscoveryLayer'><input type='checkbox' unchecked class='AllManagers checkFilter'></input> <span id='AssetManager_spanFolder' class='Parentfolder'><i class='fa fa-folder' style='color: #08526D'></i></span><span id='AssetManager_span' class='TreeSpan' style='color:black;width:395px'>Asset Manager </span></li></ul>";
        $("#initial_ul_CurrentDiscoveryLayer").append(str);
    }

    // Add Finance Manager folder
    if (readFinanceM === '1') {
        var str = "<ul><li id='FinanceManager_f_CurrentDiscoveryLayer' style='display:none;' class='FinanceManager_f_CurrentDiscoveryLayer'><input type='checkbox' unchecked class='AllManagers checkFilter'></input> <span id='FinanceManager_spanFolder' class='Parentfolder'><i class='fa fa-folder' style='color: #08526D'></i></span><span id='FinanceManager_span' class='TreeSpan' style='color:black;width:395px'>Finance Manager </span></li></ul>";
        $("#initial_ul_CurrentDiscoveryLayer").append(str);
    }

    // Create Manager Nodes in the tree
    if (listProjectManager != null) {
        for (var i = 0; i < listProjectManager.length; i++) {
            window["" + listProjectManager[i][0]] = [];
            window["" + listProjectManager[i][0]] = listProjectManager[i]; 
		   var str = `<ul><li id='${listProjectManager[i][28]}' class='pMANAGER' style='display:none;width:100px;'><input type='checkbox' class='Manager checkFilter' name='Element'></input> <span class='TreeSpan' style='color:black;width:355px'> ${listProjectManager[i][4]}</span>
			<br><span class='TreeSpan' style='color:rgb(51, 51, 51);width:355px;margin-left:17px;font-size:13px;display:none'> ${listProjectManager[i][11]}</span></li></ul>`;
                $(`#ProjectManager_f_CurrentDiscoveryLayer`).append(str);
            
        }
        AllManagerCheckFilter();
    }
	$("#ProjectManager_span").on("contextmenu", function (event) {
	    const $this = $(this);
	    const selectedId = $this.parent().attr('id');

	    // Save selected ID to global if needed
	    selectedpMANAGERIdContext = selectedId;
	    IdpMANAGERSelectedTemp = $this.parents().eq(2).attr('id');

	    // Open context menu
	    openContext(selectedId, "", PmContext, event);
	});
			
			if (listAssetManager != null) {
			
			      for (var i = 0; i < listAssetManager.length; i++) {
			          window["" + listAssetManager[i][0]] = [];
			          window["" + listAssetManager[i][0]] = listAssetManager[i]; 
				   var str = `<ul><li id='${listAssetManager[i][28]}' class='aMANAGER' style='display:none;width:100px;'><input type='checkbox' class='Manager checkFilter' name='Element'></input> <span class='TreeSpan' style='color:black;width:355px'> ${listAssetManager[i][4]}</span>
					<br><span class='TreeSpan' style='color:rgb(51, 51, 51);width:355px;margin-left:17px;font-size:13px;display:none'> ${listAssetManager[i][11]}</span></li></ul>`;
			              $(`#AssetManager_f_CurrentDiscoveryLayer`).append(str);
			          
			      }
			      AllManagerCheckFilter();
			  }
			  
			  $("#AssetManager_span").on("contextmenu", function (event) {
			  	    const $this = $(this);
			  	    const selectedId = $this.parent().attr('id');

			  	    // Save selected ID to global if needed
			  	    selectedpMANAGERIdContext = selectedId;
			  	    IdaMANAGERSelectedTemp = $this.parents().eq(2).attr('id');

			  	    // Open context menu
			  	    openContext(selectedId, "", AmContext, event);
			  	});	  
			  
			  
			$(".aMANAGER > .TreeSpan").contextmenu(function(){				
						selectedaMANAGERIdContext=$(this).parent().attr('id');
						IdpMANAGERSelectedTemp=$(this).parents().eq(2).attr('id');		
					
					});	
					
					if (listFinanceManager != null) {
								      for (var i = 0; i < listFinanceManager.length; i++) {
								          window["" + listFinanceManager[i][0]] = [];
								          window["" + listFinanceManager[i][0]] = listFinanceManager[i]; 
									    var str = `<ul><li id='${listFinanceManager[i][28]}' class='fMANAGER' style='display:none;width:100px;'><input type='checkbox' class='Manager checkFilter' name='Element'></input> <span class='TreeSpan' style='color:black;width:355px'> ${listFinanceManager[i][4]}</span>
										<br><span class='TreeSpan' style='color:rgb(51, 51, 51);width:355px;margin-left:17px;font-size:13px;display:none'> ${listFinanceManager[i][11]}</span></li></ul>`;
								              $(`#FinanceManager_f_CurrentDiscoveryLayer`).append(str);
								          
								      }
								      AllManagerCheckFilter();
								  }
								  
								  
								  
								  $("#FinanceManager_span").on("contextmenu", function (event) {
								  	  	    const $this = $(this);
								  	  	    const selectedId = $this.parent().attr('id');

								  	  	    // Save selected ID to global if needed
								  	  	    selectedpMANAGERIdContext = selectedId;
								  	  	    IdaMANAGERSelectedTemp = $this.parents().eq(2).attr('id');

								  	  	    // Open context menu
								  	  	    openContext(selectedId, "", FmContext, event);
								  	  	});	
								$(".fMANAGER > .TreeSpan").contextmenu(function(){				
											selectedfMANAGERIdContext=$(this).parent().attr('id');
											IdpMANAGERSelectedTemp=$(this).parents().eq(2).attr('id');		
										
										});	
    // Context menu for Manager elements

    // Enable tree folder collapse/expand functionality
    treeCollapseFolder(".Parentfolder", null, ".Parentfolder");
    treeCollapseFolder(".folder", "fast", ".folder");

    // Apply selection behavior to tree spans


    // Enable hover effects for tree spans
    MouseHoveringSpans(null);

    // Ensure consistent display styles for tree elements
    $('.TreeSpan').css("display", "inline");
	
	
	$(".TreeSpan").on("click", function () {
		document.querySelectorAll('.first-div').forEach(function(div) {
		  div.style.display = 'block';
		});

		// Show all elements with class 'second-div'
		document.querySelectorAll('.second-div').forEach(function(div) {
		  div.style.display = 'none';
		});
	    var parentLi = $(this).closest("li");
	    var liId = parentLi.attr("id");
	
		    // Remove background and class from all other spans
		    $(".TreeSpan").not(this).css("background-color", "").removeClass("selected-span");

		    // Add background and class to the clicked span
		    $(this).css("background-color", "#97b9cc").addClass("selected-span");

		    // Store selected ID globally
		   
	    console.log("Clicked TreeSpan ID:", liId);

	    if (parentLi.hasClass("pMANAGER")) {
			  if (saveProjectM == "0") {
				document.getElementById("saveButton").disabled = true;

				}
				else{
			document.getElementById("saveButton").disabled = false;
							}
			if(approveRejectProjectM == "0"){
				document.getElementById("notifactionDropdown").disabled = true;
	}
			else {
				document.getElementById("notifactionDropdown").disabled = false;
				getApproval= "Project Manager";
			}
	        console.log("From Project Manager folder");
	        let foundIndex = -1;
	        listProjectManager.forEach(function (item, index) {
	            if (item[0] == liId) {
					if(item[8]== "Operation Manager"){
						getApproval= "Operation Manager";
					}
			
	                foundIndex = index;
	                console.log("Matched Project Manager array at index:", index, item);
	            }
	        });

	        if (foundIndex !== -1) {
	            populateForm(listProjectManager, foundIndex);
	        }

	        if (writeProjectM == "0") {
	            disableAllFields();
	        } else {
	            enableAllFields();
	        }

	    } else if (parentLi.hasClass("aMANAGER")) {
			if (saveAssetM == "0") {
			document.getElementById("saveButton").disabled = true;

							}
			else{
				document.getElementById("saveButton").disabled = false;
					}
					if(approveRejectAssetM == "0"){
					document.getElementById("notifactionDropdown").disabled = true;
}
								else {
									document.getElementById("notifactionDropdown").disabled = false;
									getApproval= "Asset Unit";
								}
	        console.log("From Asset Manager folder");
	        let foundIndex = -1;
	        listAssetManager.forEach(function (item, index) {
	            if (item[0] == liId) {
	                foundIndex = index;
	                console.log("Matched Asset Manager array at index:", index, item);
	            }
	        });

	        if (foundIndex !== -1) {
	            populateForm(listAssetManager, foundIndex);
	        }

	        if (writeAssetM == "0") {
	            disableAllFields();
	        } else {
	            enableAllFields();
	        }

	    } else if (parentLi.hasClass("fMANAGER")) {
	        console.log("From Finance Manager folder");
			if (saveFinanceM == "0") {
			document.getElementById("saveButton").disabled = true;

							}
			else{
				document.getElementById("saveButton").disabled = false;
			}
			if(approveRejectFinanceM == "0"){
				document.getElementById("notifactionDropdown").disabled = true;
}
						else {
							document.getElementById("notifactionDropdown").disabled = false;
							getApproval= "Finance";
						}
	        let foundIndex = -1;
	        listFinanceManager.forEach(function (item, index) {
	            if (item[0] == liId) {
	                foundIndex = index;
	                console.log("Matched Finance Manager array at index:", index, item);
	            }
	        });

	        if (foundIndex !== -1) {
	            populateForm(listFinanceManager, foundIndex);
	        }

	        if (writeFinanceM == "0") {
	            disableAllFields();
	        } else {
	            enableAllFields();
	        }

	    } else {
	        console.log("From an unknown or top-level folder");
	    }
	});


	
	
	
	}
	function checkToNode() {
		    var Data = {}; // Initialize a data object
		    Data.toNodeArray = []; // Array to hold valid rows' data

		    // Iterate through each 'record' checkbox in the table
		    $("#ToNodeTable > tbody > tr").find('input[name="record"]').each(function () {
		        // Get the value of the NodeId input field
		        var nodeIdInput = $(this).parent().parent().children('td[name="NodeId"]').children('input');
		        var nodeIdValue = nodeIdInput.val();

		        // If NodeId is empty, remove the row
		        if (nodeIdValue === '') {
		            console.warn("Removing row with empty NodeId");
		            $(this).parents("tr").remove();
		        } else {
		            // Extract values for NodeId, NodeName, and NodeType
		            var node_Name = $(this).parent().parent().children('td[name="NodeName"]').children('input').val();
		            var node_Type = $(this).parent().parent().children('td[name="NodeType"]').children('input').val();

		            // Add the row's data to the toNodeArray
		            Data.toNodeArray.push({ NodeId: nodeIdValue, NodeName: node_Name, NodeType: node_Type });
		        }
		    });

		    console.log("Aggregated Data:", Data.toNodeArray); // Log the aggregated data
		}
		function getContextPath() {
		   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}
		function addRowToNode() {
    checkToNode();
    var rowcountNodes = $("#toNodes >tbody tr").length;// Count existing rows
    console.log("rowcountNode:" + rowcountNodes);
	var rowcountNode= rowcountNodes+1;
	console.log(rowcountNode);
    var ctx = getContextPath();

    // Dynamically create a new row
    var markup =
        "<tr><td style='text-align:center;'><input type='checkbox' name='record' id='record_" + rowcountNode + "'></td>" +
        "<td name='NodeId'>" +
        "<input name='NodeToId' class='form-control text-input' type='text' id='NodeToId_" + rowcountNode + "' style='width:100%;box-sizing:border-box;'/></td>" +
        "<td name='NodeName'>" +
        "<input name='NodeToName' id='NodeToName_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td>" +
        "<td name='NodeType'>" +
        "<input name='NodeToType' id='NodeToType_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

    $("#toNodes > tbody").append(markup);

    // NodeId autocomplete setup
    $('#NodeToId_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: { Node: request.term },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log("Error fetching autocomplete data");
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            this.value = ui.item ? ui.item[2] : '';
            document.getElementById("NodeToName_" + rowcountNode).value = ui.item[3];
            document.getElementById("NodeToType_" + rowcountNode).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = "<div class='acItem'><span class='desc'><b>" +
            item[2] + "</b></span><br><span class='name'>" +
            item[3] + ", " + item[4] + "</span></div>";

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    // NodeName autocomplete setup
    $('#NodeToName_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: { Node: request.term },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log("Error fetching autocomplete data");
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            this.value = ui.item ? ui.item[3] : '';
            document.getElementById("NodeToId_" + rowcountNode).value = ui.item[2];
            document.getElementById("NodeToType_" + rowcountNode).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = "<div class='acItem'><span class='desc'><b>" +
            item[3] + "</b></span><br><span class='name'>" +
            item[2] + ", " + item[4] + "</span></div>";

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    // NodeType autocomplete setup
    $('#NodeToType_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: { Node: request.term },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log("Error fetching autocomplete data");
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            this.value = ui.item ? ui.item[4] : '';
            document.getElementById("NodeToId_" + rowcountNode).value = ui.item[2];
            document.getElementById("NodeToName_" + rowcountNode).value = ui.item[3];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = "<div class='acItem'><span class='desc'><b>" +
            item[4] + "</b></span><br><span class='name'>" +
            item[2] + ", " + item[3] + "</span></div>";

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    // Focus triggers autocomplete search
    ['NodeToId', 'NodeToName', 'NodeToType'].forEach(function (field) {
        $('#'+field+'_' + rowcountNode).focus(function () {
            if (this.value === "") {
                $(this).autocomplete("search");
            }
        });
    });

    // Aggregate the data for all rows
    var Data = { toNodeArray: [] };
    $("#toNodes  > tbody > tr").find('input[name="record"]').each(function () {
        var node_Id = $(this).parent().parent().children('td[name="NodeToId"]').children('input').val();
        var node_Name = $(this).parent().parent().children('td[name="NodeToName"]').children('input').val();
        var node_Type = $(this).parent().parent().children('td[name="NodeToType"]').children('input').val();

        Data.toNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
    });

    
}
function checkFromNode() {
    var Data = {};
    Data.fromNodeArray = [];

    $("#fromNodes > tbody > tr").find('input[name="record"]').each(function () {
        var ID = $(this).parent().parent().children('td[name="NodeId"]').children('input')[0].value;
        if (ID === '') {
            $(this).parents("tr").remove();
        } else {
            var node_Id = $(this).parent().parent().children('td[name="NodeFromId"]').children('input').val();
            var node_Name = $(this).parent().parent().children('td[name="NodeFromName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeFromType"]').children('input').val();

            Data.fromNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
        }
    });

}

function addRowFromNode() {
	checkFromNode();
		    var rowcountNodes = $("#fromNodes >tbody tr").length; // Count existing rows
		 	var rowcountNode= rowcountNodes+1;
		    var ctx = getContextPath();

		    // Dynamically create a new row
		    var markup =
		        "<tr><td style='text-align:center;'><input type='checkbox' name='record' id='record_" + rowcountNode + "'></td>" +
		        "<td name='NodeId'>" +
		        "<input name='NodeFromId' class='form-control text-input' type='text' id='NodeFromId_" + rowcountNode + "' style='width:100%;box-sizing:border-box;'/></td>" +
		        "<td name='NodeName'>" +
		        "<input name='NodeFromName' id='NodeFromName_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td>" +
		        "<td name='NodeType'>" +
		        "<input name='NodeFromType' id='NodeFromType_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

		    $("#fromNodes > tbody").append(markup);

		    // NodeId autocomplete setup
		    $('#NodeFromId_' + rowcountNode).autocomplete({
		        source: function (request, response) {
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		                url: ctx + '/GetAllNode',
		                data: { Node: request.term },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.ListNode);
		                    }
		                },
		                error: function (result) {
		                    console.log("Error fetching autocomplete data");
		                }
		            });
		        },
		        minLength: 0,
		        select: function (event, ui) {
		            this.value = ui.item ? ui.item[2] : '';
		            document.getElementById("NodeFromName_" + rowcountNode).value = ui.item[3];
		            document.getElementById("NodeFromType_" + rowcountNode).value = ui.item[4];
		            return false;
		        }
		    }).autocomplete("instance")._renderItem = function (ul, item) {
		        var appendString = "<div class='acItem'><span class='desc'><b>" +
		            item[2] + "</b></span><br><span class='name'>" +
		            item[3] + ", " + item[4] + "</span></div>";

		        return $("<li class='each'>").append(appendString).appendTo(ul);
		    };

		    // NodeName autocomplete setup
		    $('#NodeFromName_' + rowcountNode).autocomplete({
		        source: function (request, response) {
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		                url: ctx + '/GetAllNode',
		                data: { Node: request.term },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.ListNode);
		                    }
		                },
		                error: function (result) {
		                    console.log("Error fetching autocomplete data");
		                }
		            });
		        },
		        minLength: 0,
		        select: function (event, ui) {
		            this.value = ui.item ? ui.item[3] : '';
		            document.getElementById("NodeFromId_" + rowcountNode).value = ui.item[2];
		            document.getElementById("NodeFromType_" + rowcountNode).value = ui.item[4];
		            return false;
		        }
		    }).autocomplete("instance")._renderItem = function (ul, item) {
		        var appendString = "<div class='acItem'><span class='desc'><b>" +
		            item[3] + "</b></span><br><span class='name'>" +
		            item[2] + ", " + item[4] + "</span></div>";

		        return $("<li class='each'>").append(appendString).appendTo(ul);
		    };

		    // NodeType autocomplete setup
		    $('#NodeFromType_' + rowcountNode).autocomplete({
		        source: function (request, response) {
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		                url: ctx + '/GetAllNode',
		                data: { Node: request.term },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.ListNode);
		                    }
		                },
		                error: function (result) {
		                    console.log("Error fetching autocomplete data");
		                }
		            });
		        },
		        minLength: 0,
		        select: function (event, ui) {
		            this.value = ui.item ? ui.item[4] : '';
		            document.getElementById("NodeFromId_" + rowcountNode).value = ui.item[2];
		            document.getElementById("NodeFromName_" + rowcountNode).value = ui.item[3];
		            return false;
		        }
		    }).autocomplete("instance")._renderItem = function (ul, item) {
		        var appendString = "<div class='acItem'><span class='desc'><b>" +
		            item[4] + "</b></span><br><span class='name'>" +
		            item[2] + ", " + item[3] + "</span></div>";

		        return $("<li class='each'>").append(appendString).appendTo(ul);
		    };

		    // Focus triggers autocomplete search
		    ['NodeFromId', 'NodeFromName', 'NodeFromType'].forEach(function (field) {
		        $('#'+field+'_' + rowcountNode).focus(function () {
		            if (this.value === "") {
		                $(this).autocomplete("search");
		            }
		        });
		    });

		    // Aggregate the data for all rows
		    var Data = { toNodeArray: [] };
		    $("#fromNodes > tbody > tr").find('input[name="record"]').each(function () {
		        var node_Id = $(this).parent().parent().children('td[name="NodeFromId"]').children('input').val();
		        var node_Name = $(this).parent().parent().children('td[name="NodeFromName"]').children('input').val();
		        var node_Type = $(this).parent().parent().children('td[name="NodeFromType"]').children('input').val();

		        Data.toNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
		    });

		    
		}
				
		

// Function to collapse/expand tree folders
function treeCollapseFolder(selector, type, clss) {
    if (selector == null) {
        selector = ".folder";
    }
    $(selector).bind('click', function (e) {
        var children = $(this).parent().find(' > ul > li');
        if (children.is(":visible")) {
            children.hide(type);
            $(this).parent().children(clss).find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
        } else {
            children.show(type);
            $(this).parent().children(clss).find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
        }
        e.stopPropagation();
    });
}

// Function for tree span selection behavior


// Function for hover effects on tree spans
function MouseHoveringSpans(selector) {
    if (selector == null) {
        selector = "li > .TreeSpan";
    }
    $(selector).unbind("mouseover");
    $(selector).bind("mouseover", function () {
        $(this).addClass('backgroundTree');
    }).on("mouseout", function () {
        $(this).removeClass('backgroundTree');
    });
}

$(document).ready(function () {
    $('#saveButton').on('click', function () {
        // Collect simple input fields
		var action =document.getElementById("actionButtonText").textContent;
	
																	
		var numericFields = [
		    { id: 'Qty', label: 'Qty' },
		    { id: 'rate', label: 'Rate' },
		    { id: 'discountAmount', label: 'Discount Amount' },
		    { id: 'tax', label: 'Tax' },
		    { id: 'netRate', label: 'Net Rate' },
		    { id: 'totalAt', label: 'Total Amount' }
		];

		for (var i = 0; i < numericFields.length; i++) {
		    var field = numericFields[i];
		    var val = $('#' + field.id).val().trim();

		    // Log the current field being checked
		    console.log(`Validating field: ${field.label}, value: "${val}"`);

		    if (val && isNaN(val)) {
		        alert(field.label + " must be a valid number.");
		        return;
		    }
		}

        // Collect 'toNodes' table data
		// For 'toNodes'
		var toNodes = [];
			$('#toNodes tbody tr').each(function () {
			    var nodeId = $(this).find('input[name="NodeToId"]').val();
			    var nodeName = $(this).find('input[name="NodeToName"]').val();
			    var nodeType = $(this).find('input[name="NodeToType"]').val();
			    // Only add if at least one field is present
			    if (nodeId || nodeName || nodeType) {
			        toNodes.push([nodeId, nodeName, nodeType]);
			    }
			});

			// For 'fromNodes'
			var fromNodes = [];
			$('#fromNodes tbody tr').each(function () {
			    var nodeId = $(this).find('input[name="NodeFromId"]').val();
			    var nodeName = $(this).find('input[name="NodeFromName"]').val();
			    var nodeType = $(this).find('input[name="NodeFromType"]').val();
			    if (nodeId || nodeName || nodeType) {
			        fromNodes.push([nodeId, nodeName, nodeType]);
			    }
			});
      

        // Get context path (ensure this function is defined elsewhere in your code)
        var ctx = getContextPath();

        // Send AJAX request
        $.ajax({
            type: "POST",
           
            url: ctx + '/SaveFormTreeDN',
            dataType: "json",
            data: {
				        itemId: $('#itemId').val(),
				        itemName: $('#itemName').val(),
				        DitemModel: $('#DitemModel').val(),
				        DitemPartNumber: $('#DitemPartNumber').val(),
				        popupTransType: $('#popupTransType').val(),
				        description: $('#description').val(),
				        popupElementName: $('#popupElementName').val(),
				        address: $('#address').val(),
				        PoId: $('#PoId').val(),
				        WoId: $('#WoId').val(),
				        fromSlotId: $('#fromSlotId').val(),
				        toSlotId: $('#toSlotId').val(),
				        fromSiteId: $('#fromSiteId').val(),
				        toSiteId: $('#toSiteId').val(),
				        farId: $('#farId').val(),
				        macAddress: $('#macAddress').val(),
				        Qty: $('#Qty').val(),
				        rate: $('#rate').val(),
				        discountAmount: $('#discountAmount').val(),
				        tax: $('#tax').val(),
				        netRate: $('#netRate').val(),
				        totalAt: $('#totalAt').val(),
				        oldSerialNum: $('#oldSerialNum').val(),
				        serialNum: $('#serialNum').val(),
				        transId: $('#transId').val(),
				        DnItemId: $('#DnItemId').val(),
				        DnItemNotes: $('#DnItemNotes').val(),
						toNodes: JSON.stringify(toNodes),
						fromNodes: JSON.stringify(fromNodes),
						slctFromNodeDel : JSON.stringify(slctFromNodeDel),
						slctToNodeDel : JSON.stringify(slctToNodeDel),
						action : action,
					    getApproval : getApproval,
					    readProjectM: readProjectM,
					   readAssetM:   readAssetM, 
					   readFinanceM : readFinanceM,
					   writeProjectM : writeProjectM,
					   writeAssetM: writeAssetM,
					   writeFinanceM: writeFinanceM,
					   saveProjectM: saveProjectM,
					   saveAssetM : saveProjectM, 
					   saveFinanceM : saveFinanceM,
					   approveRejectProjectM : approveRejectProjectM,
					   approveRejectAssetM : approveRejectAssetM,
					   approveRejectFinanceM : approveRejectFinanceM},
                      success: function (response) {
						if (response.savedItem && response.savedItem.length > 0) {
						    localStorage.setItem('SavedItemID', response.savedItem[0]);
						    localStorage.setItem('SavedItem', JSON.stringify(response.savedItem)); // Save as JSON string
						    location.reload(); // reload AFTER saving
						}
						
              
			
				
            },
            error: function (xhr, status, error) {
                alert("Save failed: " + error);
            }
        });
    });
	
});




// Function to check all manager nodes
function AllManagerCheckFilter() {
    // Add your logic here if needed
}
function toggleSelectAllToNodes() {
    $("#toNodes > tbody").find('input[name="record"]').each(function () {
		if ($(this).hasClass('allChecked')) {
			$('input[type="checkbox"]', '#toNodes').prop('checked', false);
		} 
		else {
			$('input[type="checkbox"]', '#toNodes').prop('checked', true);
		}

		$(this).toggleClass('allChecked');
});
}

function toggleSelectAllFromNodes() {
    $("#fromNodes > tbody").find('input[name="record"]').each(function () {
		if ($(this).hasClass('allChecked')) {
			$('input[type="checkbox"]', '#fromNodes').prop('checked', false);
		} 
		else {
			$('input[type="checkbox"]', '#fromNodes').prop('checked', true);
		}

		$(this).toggleClass('allChecked');
    });
}
	function populateForm(data, index) {
		console.log("ye");
	    function setValue(id, value) {
	        const el = document.getElementById(id);
	        if (el) el.value = value || '';
	        else console.warn(`Element with ID '${id}' not found.`);
	    }
        var  purchaseOrder= data[index][9]+ ":" + data[index][12] +":" + data[index][13]+":"+data[index][14];
		var  tosite= data[index][38]+ ":" + data[index][37] +":" + data[index][36];
		var  fromsite= data[index][25]+ ":" + data[index][26] +":" + data[index][24];
	    setValue('itemId', data[index][2]);            // dniItemcode
	    setValue('itemName', data[index][3]);          // dniItemname
	    setValue('DitemModel', data[index][29]);
	    setValue('DitemPartNumber', data[index][30]);
	    setValue('popupTransType', data[index][4]);
	    setValue('description', data[index][41]);
	    setValue('popupElementName', data[index][5]);
	    setValue('PoId', purchaseOrder);
	    setValue('WoId', data[index][15]);
	    setValue('fromSlotId', data[index][32]);
	    setValue('toSlotId', data[index][35]);
	    setValue('fromSiteId', fromsite);
	    setValue('toSiteId', tosite);
	    setValue('farId', data[index][33]);
	    setValue('macAddress', data[index][34]);
		setValue('Qty', toSafeNumber(data[index][17]));
		setValue('rate', toSafeNumber(data[index][18]));
		setValue('discountAmount', toSafeNumber(data[index][19]));
		setValue('tax', toSafeNumber(data[index][20]));
		setValue('netRate', toSafeNumber(data[index][21]));
		setValue('totalAt', toSafeNumber(data[index][23]));
	    setValue('oldSerialNum', data[index][27]);
	    setValue('serialNum', data[index][40]);
	    setValue('DnItemNotes', data[index][6]);
	    setValue('DnItemId', data[index][0]);
		setValue('transId', data[index][1]);

	    // Populate the To and From Nodes tables
	    populateNodesTable(data[index][42], data[index][43]);
	}
	function toSafeNumber(val) {
	  return val === null || val === undefined || val === '' ? '0' : String(val);
	}
	 function populateNodesTable(toNode, fromNode) {
		console.log("yess");
	    const toNodesTableBody = document.querySelector('#toNodes tbody');
	    const fromNodesTableBody = document.querySelector('#fromNodes tbody');
	    let rowcountNode = 0; // Initialize row counter for unique ID generation

	    // Clear existing rows
	    toNodesTableBody.innerHTML = '';
	    fromNodesTableBody.innerHTML = '';

	    // Parse and Populate "To Nodes" table
	    const toNodeArrayStr = toNode; // JSON string for toNodeArray
	    let toNodeArray = [];
		if(toNodeArrayStr!=null){
	    try {
	        toNodeArray = JSON.parse(toNodeArrayStr).toNodeArray || [];
			console.log(toNodeArray);
	    } catch (e) {
	        console.error('Error parsing toNodeArray:', e);
	    }
		

	    if (toNodeArray && Array.isArray(toNodeArray)) {
	        toNodeArray.forEach(node => {
					if (!(node.NodeId === " " && node.NodeName === " " && node.NodeType === " ")) {
	            const markup = `
				<tr>
				                           <td style="width:5%; text-align:center;">
				                               <input type="checkbox" name="record" id="${node.NodeId}" style="margin-top:10px">
				                           </td>
				                           <td name="NodeId" style="width:31%;">
				                               <input name="NodeToId" class="form-control text-input" type="text" id="NodeToId_${rowcountNode}" value="${node.NodeId}" style="width:100%;box-sizing:border-box;">
				                           </td>
				                           <td name="NodeName" style="width:31%;">
				                               <input name="NodeToName" id="NodeToName_${rowcountNode}" value="${node.NodeName}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
				                           </td>
				                           <td name="NodeType" style="width:31%;">
				                               <input name="NodeToType" id="NodeToType_${rowcountNode}" value="${node.NodeType}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
				                           </td>
				                       </tr>
	            `;
	            toNodesTableBody.innerHTML += markup;
	            rowcountNode++;
				} // Increment row counter for unique IDs
	        });
	    }
}
	    // Reset row counter for "From Nodes" table
	    rowcountNode = 0;

	    // Parse and Populate "From Nodes" table
	    const fromNodeArrayStr = fromNode; // JSON string for fromNodeArray
	    let fromNodeArray = [];
		if(fromNodeArrayStr !=null){
	    try {
	        fromNodeArray = JSON.parse(fromNodeArrayStr).fromNodeArray || [];
	    } catch (e) {
	        console.error('Error parsing fromNodeArray:', e);
	    }
		

	    if (fromNodeArray && Array.isArray(fromNodeArray)) {
	        fromNodeArray.forEach(node => {
				if (!(node.NodeId === " " && node.NodeName === " " && node.NodeType === " ")) {
	            const markup = `
				<tr>
				                            <td style="width:5%; text-align:center;">
				                                <input type="checkbox" name="record" id="${node.NodeId}" style="margin-top:10px">
				                            </td>
				                            <td name="NodeId" style="width:31%;">
				                                <input name="NodeFromId" class="form-control text-input" type="text" id="NodeFromId_${rowcountNode}" value="${node.NodeId}" style="width:100%;box-sizing:border-box;">
				                            </td>
				                            <td name="NodeName" style="width:31%;">
				                                <input name="NodeFromName" id="NodeFromName_${rowcountNode}" value="${node.NodeName}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
				                            </td>
				                            <td name="NodeType" style="width:31%;">
				                                <input name="NodeFromType" id="NodeFromType_${rowcountNode}" value="${node.NodeType}" style="width:100%;box-sizing:border-box;" type="text" class="form-control text-input ui-widget ui-widget-content ui-corner-all">
				                            </td>
				                        </tr>
	            `;
	            fromNodesTableBody.innerHTML += markup;
	            rowcountNode++; 
				}// Increment row counter for unique IDs
	        });
	    }
		}
	}
	// Delete selected rows from the "To Nodes" table

	
function afterSave(response){
	//zeina 
	 savedItemList = [response.savedItem];
	
	 if((savedItemList[0][8]== 'Project Manager' || savedItemList[0][8]== 'Operation Manager') & (savedItemList[0][31] == '-- Select Option --' )){
	
		if(savedItemList[0][8]== 'Project Manager'){
		getApproval="Project Manager";
		}
		else if (savedItemList[0][8]== 'Operation Manager'){
			getApproval="Operation Manager";
		}
				if (readProjectM === '1') {
					document.querySelectorAll('.first-div').forEach(function(div) {
								  div.style.display = 'block';
								});

								// Show all elements with class 'second-div'
								document.querySelectorAll('.second-div').forEach(function(div) {
								  div.style.display = 'none';
								});
				   
					populateForm(savedItemList, 0);
				    const $targetLi = $("#" + savedItemList[0][0]);
				    if ($targetLi.length === 0) {
				        console.warn("Target LI not found:", response.savedItem[0]);
				        return;
				    }

				    // Expand all parent folders by simulating click on their .Parentfolder span
				    $targetLi.parents("ul").each(function () {
				        const $parentLi = $(this).closest("li");
				        const $folderIcon = $parentLi.children(".Parentfolder");

				        if ($folderIcon.length && !$parentLi.children("ul").is(":visible")) {
				            $folderIcon.trigger("click");
				        }
				    });

				    // Also open the root folder explicitly
					$("#initial_Span_CurrentDiscoveryLayer").trigger("click");

				    // Clear previous highlight and highlight selected node
				    $(".TreeSpan").css("background-color", "").removeClass("selected-span");

				    const $treeSpan = $targetLi.children(".TreeSpan").first();
				    $treeSpan.css("background-color", "#97b9cc").addClass("selected-span");

				    // Scroll into view
				    $treeSpan[0].scrollIntoView({ behavior: "smooth", block: "center" });

				 	if(writeProjectM == '0'){
							//enable fields 
							disableAllFields();
						}
						else{
							enableAllFields();
						}
						if(saveProjectM== '0'){
							document.getElementById("saveButton").disabled = true;
						}
						else{
						document.getElementById("saveButton").disabled = false;
									}
							if(approveRejectAssetM == "0"){
							document.getElementById("notifactionDropdown").disabled = true;
							}
							else {
							document.getElementById("notifactionDropdown").disabled = false;
																
															
							}
				}
				
		
	
		
		
		
	 }
	 
	
	else if((savedItemList[0][8]== 'Project Manager' & savedItemList[0][31] == 'Approved' ) ||  (savedItemList[0][8]== 'Asset Unit' & savedItemList[0][31] == '-- Select Option --' )){
		getApproval="Asset Unit";
		if (readAssetM === '1') {
			document.querySelectorAll('.first-div').forEach(function(div) {
											  div.style.display = 'block';
											});

											// Show all elements with class 'second-div'
											document.querySelectorAll('.second-div').forEach(function(div) {
											  div.style.display = 'none';
											});
			populateForm(savedItemList, 0);
		    const $targetLi = $("#" + savedItemList[0][0]);
		    if ($targetLi.length === 0) {
		        console.warn("Target LI not found:", response.savedItem[0]);
		        return;
		    }

		    // Expand all parent folders by simulating click on their .Parentfolder span
		    $targetLi.parents("ul").each(function () {
		        const $parentLi = $(this).closest("li");
		        const $folderIcon = $parentLi.children(".Parentfolder");

		        if ($folderIcon.length && !$parentLi.children("ul").is(":visible")) {
		            $folderIcon.trigger("click");
		        }
		    });

		    // Also open the root folder explicitly
			$("#initial_Span_CurrentDiscoveryLayer").trigger("click");

		    // Clear previous highlight and highlight selected node
		    $(".TreeSpan").css("background-color", "").removeClass("selected-span");

		    const $treeSpan = $targetLi.children(".TreeSpan").first();
		    $treeSpan.css("background-color", "#97b9cc").addClass("selected-span");

		    // Scroll into view
		    $treeSpan[0].scrollIntoView({ behavior: "smooth", block: "center" });

		 	if(writeAssetM == '0'){
					//enable fields 
					disableAllFields();
				}
				else{
					enableAllFields();
				}
				if(saveAssetM== '0'){
					document.getElementById("saveButton").disabled = true;
				}
				else{
				document.getElementById("saveButton").disabled = false;
							}
					if(approveRejectAssetM == "0"){
					document.getElementById("notifactionDropdown").disabled = true;
					}
					else {
					document.getElementById("notifactionDropdown").disabled = false;
														
													
					}
		}
		
		else{
			
			document.querySelectorAll('.first-div').forEach(function(div) {
											  div.style.display = 'none';
											});

											// Show all elements with class 'second-div'
											document.querySelectorAll('.second-div').forEach(function(div) {
											  div.style.display = 'block';
											});
											document.querySelector('.second-div .centered-message').innerHTML = 
											    "Project Manager Action Done for " + savedItemList[0][0] + 
											    " <br>with Transaction " + savedItemList[0][4] ;		
												
														/*Set the modal title and body
					   $("#modelHeader").text("Approval Status");
					   $("#modelBody").text("Project Manager Approval Done for "+ savedItemList[0][0]+" with transaction "+savedItemList[0][4]);

					 
					   $("#approvalModal").modal("show");
*/
			}
		}

	else if((savedItemList[0][8]== 'Asset Unit' & savedItemList[0][31] == 'Approved' ) ||  (savedItemList[0][8]== 'Finance' & savedItemList[0][31] == '-- Select Option --' )){
			
			getApproval="Finance";
			if (readFinanceM === '1') {
				document.querySelectorAll('.first-div').forEach(function(div) {
												  div.style.display = 'block';
												});

												// Show all elements with class 'second-div'
												document.querySelectorAll('.second-div').forEach(function(div) {
												  div.style.display = 'none';
												});	   
						populateForm(savedItemList, 0);
					    const $targetLi = $("#" + savedItemList[0][0]);
					    if ($targetLi.length === 0) {
					        console.warn("Target LI not found:", response.savedItem[0]);
					        return;
					    }

					    // Expand all parent folders by simulating click on their .Parentfolder span
					    $targetLi.parents("ul").each(function () {
					        const $parentLi = $(this).closest("li");
					        const $folderIcon = $parentLi.children(".Parentfolder");

					        if ($folderIcon.length && !$parentLi.children("ul").is(":visible")) {
					            $folderIcon.trigger("click");
					        }
					    });

					    // Also open the root folder explicitly
						$("#initial_Span_CurrentDiscoveryLayer").trigger("click");

					    // Clear previous highlight and highlight selected node
					    $(".TreeSpan").css("background-color", "").removeClass("selected-span");

					    const $treeSpan = $targetLi.children(".TreeSpan").first();
					    $treeSpan.css("background-color", "#97b9cc").addClass("selected-span");

					    // Scroll into view
					    $treeSpan[0].scrollIntoView({ behavior: "smooth", block: "center" });

					 	if(writeFinanceM == '0'){
								//enable fields 
								disableAllFields();
							}
							else{
								enableAllFields();
							}
							if(saveFinanceM== '0'){
								document.getElementById("saveButton").disabled = true;
							}
							else{
							document.getElementById("saveButton").disabled = false;
										}
								if(approveRejectFinanceM == "0"){
								document.getElementById("notifactionDropdown").disabled = true;
								}
								else {
								document.getElementById("notifactionDropdown").disabled = false;
																	
																
								}
					}
					else{
						document.querySelectorAll('.first-div').forEach(function(div) {
						 div.style.display = 'none';
							});
                      // Show all elements with class 'second-div'
								document.querySelectorAll('.second-div').forEach(function(div) {
								 div.style.display = 'block';
											});
											document.querySelector('.second-div .centered-message').innerHTML = 
												 "Asset Unit Action Done for " + savedItemList[0][0] + 
													    " <br>with Transaction " + savedItemList[0][4] ;		
																					
								
						/* Set the modal title and body
						$("#modelHeader").text("Approval Status");
						$("#modelBody").text("Finance Manager Approval Done for "+ savedItemList[0][0]+" with transaction "+savedItemList[0][4]);
                        $("#approvalModal").modal("show");
						*/
					}
		
		}
		
		else{
			
		if(savedItemList[0][8]== 'Operation Manager' & savedItemList[0][31] == 'Approved' ){
				
			document.querySelectorAll('.first-div').forEach(function(div) {
									 div.style.display = 'none';
										});
			                      // Show all elements with class 'second-div'
											document.querySelectorAll('.second-div').forEach(function(div) {
											 div.style.display = 'block';
														});
														document.querySelector('.second-div .centered-message').innerHTML = 
															 savedItemList[0][0] + " with Transaction " + savedItemList[0][4] 
															 +" <br> Completely Approved By the Operational Manager" ;		
					/*			
						
					$("#modelHeader").text("Approval Status");
					$("#modelBody").text("Approval Completly Done for "+ savedItemList[0][0]+" with transaction "+savedItemList[0][4]);
					$("#approvalModal").modal("show");
									
				*/
				
				}
				else if(savedItemList[0][8]== 'Finance' & savedItemList[0][31] == 'Approved' ){
								
							document.querySelectorAll('.first-div').forEach(function(div) {
													 div.style.display = 'none';
														});
							                      // Show all elements with class 'second-div'
															document.querySelectorAll('.second-div').forEach(function(div) {
															 div.style.display = 'block';
																		});
																		document.querySelector('.second-div .centered-message').innerHTML = 
																			 savedItemList[0][0] + " with Transaction " + savedItemList[0][4] 
																			 +" <br> Completely Approved By the Finance Manager" ;		
									/*			
										
									$("#modelHeader").text("Approval Status");
									$("#modelBody").text("Approval Completly Done for "+ savedItemList[0][0]+" with transaction "+savedItemList[0][4]);
									$("#approvalModal").modal("show");
													
								*/
								
								}
			
				}
	
	}
	
window.addEventListener('load', function () {
					
    const savedItemRaw = localStorage.getItem('SavedItem');
	const savedItemsId= localStorage.getItem('SavedItemsID');
	const approvlStatus= localStorage.getItem('approvlStatus');
	
	
	
	
	
    if (savedItemRaw) {
        try {
            const savedItem = JSON.parse(savedItemRaw);
            const response = { savedItem: savedItem };

            afterSave(response); // Call your existing function with the parsed data

            // Clean up so it doesn't run again after another reload
            localStorage.removeItem('SavedItem');
            localStorage.removeItem('SavedItemID');
        } catch (e) {
            console.error("Failed to parse SavedItem from localStorage:", e);
        }
    }
	
	if (savedItemsId !=null && savedItemsId !== "") {
	
		document.querySelectorAll('.first-div').forEach(function(div) {
		 div.style.display = 'none';
												});

												// Show all elements with class 'second-div'
												document.querySelectorAll('.second-div').forEach(function(div) {
												  div.style.display = 'block';
												});
												document.querySelector('.second-div .centered-message').innerHTML = 
												 approvlStatus+   " Action Done for " + savedItemsId;
		
												 localStorage.removeItem('approvlStatus');
												 localStorage.removeItem('SavedItemsID');
		
		}
});

function SavePAF(status,List,approvalStatus) {
    const checkedIds = [];

    // Step 1: Collect IDs of checked boxes
	if(approvalStatus== 'Project Manager'){
    $("#ProjectManager_f_CurrentDiscoveryLayer")
        .find("li.pMANAGER input.Manager.checkFilter:checked")
        .each(function () {
            const managerId = $(this).closest("li.pMANAGER").attr("id");
            if (managerId) {
                checkedIds.push(managerId);
            }
        });
		}
		
		else if(approvalStatus== 'Asset Unit'){
		$("#AssetManager_f_CurrentDiscoveryLayer")
		    .find("li.aMANAGER input.Manager.checkFilter:checked")
		    .each(function () {
		        const managerId = $(this).closest("li.aMANAGER").attr("id");
		        if (managerId) {
		            checkedIds.push(managerId);
		        }
		    });
			}
		
			else if(approvalStatus== 'Finance'){
				$("#FinanceManager_f_CurrentDiscoveryLayer")
				    .find("li.fMANAGER input.Manager.checkFilter:checked")
				    .each(function () {
				        const managerId = $(this).closest("li.fMANAGER").attr("id");
				        if (managerId) {
				            checkedIds.push(managerId);
				        }
				    });
					}

    console.log("Checked IDs:", checkedIds);

    // Step 2: Filter listProjectManager based on checked IDs
    const selectedItems = List.filter(pafM => 
        checkedIds.includes(pafM[0])
    );

    console.log("Selected  Items:", selectedItems);
	
	//zeina
	var ctx = getContextPath();
	
	// Send AJAX request
	    $.ajax({
	        type: "POST",
	       
	        url: ctx + '/SaveAction',
	        dataType: "json",
	        data: {
				
				        listItems: JSON.stringify(selectedItems),
				     	action : status,
					    getApproval : approvalStatus,
					    readProjectM: readProjectM,
					   readAssetM:   readAssetM, 
					   readFinanceM : readFinanceM,
					   writeProjectM : writeProjectM,
					   writeAssetM: writeAssetM,
					   writeFinanceM: writeFinanceM,
					   saveProjectM: saveProjectM,
					   saveAssetM : saveProjectM, 
					   saveFinanceM : saveFinanceM,
					   approveRejectProjectM : approveRejectProjectM,
					   approveRejectAssetM : approveRejectAssetM,
					   approveRejectFinanceM : approveRejectFinanceM},
	                  success: function (response) {
					localStorage.setItem('SavedItemsID', response.Ids);
					localStorage.setItem('approvlStatus', response.approvlStatus); // Save as JSON string
					location.reload(); // reload AFTER saving
									
	                
			
				
	        },
	        error: function (xhr, status, error) {
	            alert("Save failed: " + error);
	        }
	    });

    // Optional: do something with `selectedProjectManagers`, e.g. save or send
}


