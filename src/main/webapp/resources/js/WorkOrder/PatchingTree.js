var patchForm=0;
var taskForm=0;




function CreateTree_PatchingWorkOrder(PatchingList) {
console.log(PatchingList);
    // Create the initial tree structure for "Discovery New"
    var str_CurrentTree = "<ul style='margin-left:15px;'><li id='initial_ul_CurrentPatchingLayer' class='initial_ul_CurrentPatchingLayer'><input type='checkbox' class='allElements' unchecked name='filter'></input> <span id='initial_Span_CurrentDiscoveryLayer' class='Parentfolder'> <i class='fa fa-folder' style='color: #08526D;'></i></span><span class='TreeSpan' style='color:black;width:436px'>Patching Work Order </span></li></ul>";
    $("#patchingWorkOrder_tree").append(str_CurrentTree);

    // Add Project Manager folder
   

    // Create Manager Nodes in the tree
	if (PatchingList != null) {
	    for (var i = 0; i < PatchingList.length; i++) {
	        var wo = PatchingList[i];

	        var hasTasks = Array.isArray(wo.tasks) && wo.tasks.length > 0; // ✅ only true when tasks exist

	        // basic LI for the patching (always created)
	        var patchingLi =
	            "<ul>" +
	                "<li id='" + wo.patchingId + "' class='PatchingWO'" +
	                    " style='display:none;width:100px;'>" +
	                    "<input type='checkbox' class='WO checkFilter' name='Element'>" +
	                    "</input> ";

	        // ✅ create folder icon ONLY if this patching has tasks
	        if (hasTasks) {
	            patchingLi +=
	                "<span class='folder'>" +
	                    "<i class='fa fa-folder' style='color: #08526D;'></i>" +
	                "</span>";
	        }

	        // text span (always)
			
					patchingLi +=
								    "<span class='TreeSpan' style='color:black;width:355px'>" +
								        wo.patchingId				+ " -- " + (wo.lastModifiedDate.split(" ")[0] || "") +"</span>";
								


	        // ✅ add children <ul> only when tasks exist
	        if (hasTasks) {
	            patchingLi += "<ul>";

	            for (var j = 0; j < wo.tasks.length; j++) {
	                var t = wo.tasks[j];
console.log(t);
	                patchingLi +=
	                    "<li id='" + t.woTaskId + "' class='TaskNode' style='display:none;width:100px;'>" +
	                        "<input type='checkbox' class='Task checkFilter' name='Element'>" +
	                        "</input> " +
							"<span class='TreeSpan' style='color:black;width:340px;'>" +
							                            (t.woTaskId || "") + " -- " + (t.taskType || "") +
							                        "</span>"+
	                     
	                    "</li>";
	            }

	            patchingLi += "</ul>";
	        }

	        patchingLi += "</li></ul>";

	        $("#initial_ul_CurrentPatchingLayer").append(patchingLi);
	    }
	}


			
			
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

	    var parentLi = $(this).closest("li");
	    var liId = parentLi.attr("id");

	    // Highlight selected span
	    $(".TreeSpan").not(this)
	        .css("background-color", "")
	        .removeClass("selected-span");

	    $(this)
	        .css("background-color", "#97b9cc")
	        .addClass("selected-span");

	    console.log("Clicked TreeSpan ID:", liId);
	    console.log("Parent LI classes:", parentLi.attr("class"));

	    // ================= PATCHING CLICK =================
	    if (parentLi.hasClass("PatchingWO")) {

	        console.log("PATCHING clicked");

	        // show patching form
	        document.querySelectorAll('.first-div').forEach(div => div.style.display = 'block');
	        document.querySelectorAll('.second-div').forEach(div => div.style.display = 'none');
			document.querySelectorAll('.third-div').forEach(div => div.style.display = 'none');


	        var foundIndex = PatchingList.findIndex(function (item) {
	            return item.patchingId == liId;
	        });

	        if (foundIndex !== -1) {
				
	            populateForm(PatchingList, foundIndex);
				document.getElementById("saveButton").disabled = false;
				patchForm=1;
				taskForm=0;
	        } else {
	            console.warn("Patching not found for liId:", liId);
				document.getElementById("saveButton").disabled = true;
	        }
	    }

	    // ================= TASK CLICK =================
	    else if (parentLi.hasClass("TaskNode")) {

	        console.log("TASK clicked");

	        // show task form
	        document.querySelectorAll('.first-div').forEach(div => div.style.display = 'none');
	        document.querySelectorAll('.second-div').forEach(div => div.style.display = 'none');
			document.querySelectorAll('.third-div').forEach(div => div.style.display = 'block');
			
	        var taskFound = false;

	        // find task inside patching list
	        for (var i = 0; i < PatchingList.length; i++) {
	            var tasks = PatchingList[i].tasks || [];

	            for (var j = 0; j < tasks.length; j++) {
	                if (tasks[j].woTaskId == liId) {
	                    populateTask(tasks[j]); 
						patchForm=0;
						taskForm=1;
						document.getElementById("saveButton").disabled = false;
						
						// ✅ send task object directly
	                    taskFound = true;
	                    break;
	                }
	            }

	            if (taskFound) break;
	        }

	        if (!taskFound) {
	            console.warn("Task not found for liId:", liId);
				document.getElementById("saveButton").disabled = true;
	        }
	    }

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





// Function to check all manager nodes

	function populateForm(data, index) {
		var WO = data[index];

	
		$("#patchingId").val(WO.patchingId || "");
		   $("#assignedTo").val(WO.assignedTo || "");
		   $("#patchingStatus").val(WO.patchingStatus || "-- Select Option --");
		   $("#patchingNote").val(WO.patchingNote || "");
		   $("#createdDate").val(WO.createdDate || "");
		   $("#lastModifiedDate").val(WO.lastModifiedDate || "");

		   $("#plannedExecutionDate").val(convertToDatetimeLocal(WO.plannedExecutionDate));
		   $("#actualExecutionDate").val(convertToDatetimeLocal(WO.actualExecutionDate));
	    
	}

	
	function convertToDatetimeLocal(dbDateStr) {
	    if (!dbDateStr || dbDateStr.trim() === "") return "";

	    var parts = dbDateStr.split(" ");
	    if (parts.length < 3) return "";

	    var datePart = parts[0]; // 01/05/2026
	    var timePart = parts[1]; // 09:30
	    var ampm     = parts[2]; // AM/PM

	    var datePieces = datePart.split("/");
	    var month = parseInt(datePieces[0], 10);
	    var day   = parseInt(datePieces[1], 10);
	    var year  = parseInt(datePieces[2], 10);

	    var timePieces = timePart.split(":");
	    var hour = parseInt(timePieces[0], 10);
	    var minute = parseInt(timePieces[1], 10);

	    if (ampm === "PM" && hour < 12) hour += 12;
	    else if (ampm === "AM" && hour === 12) hour = 0;

	    var yyyy = year.toString().padStart(4, "0");
	    var mm   = month.toString().padStart(2, "0");
	    var dd   = day.toString().padStart(2, "0");
	    var hh   = hour.toString().padStart(2, "0");
	    var min  = minute.toString().padStart(2, "0");

	    return `${yyyy}-${mm}-${dd}T${hh}:${min}`;
	}
	function populateTask(task) {

	    console.log("Populating TASK:", task);

	    // --- Basic Task Info ---
	    $("#woTaskId").val(task.woTaskId || "");
	    $("#taskType").val(task.taskType || "");
	    $("#taskStatus").val(task.taskStatus || "-- Select Option --");

	    $("#creationDate").val(convertToDatetimeLocal(task.creationDate));
	    $("#lastModifiedDate").val(convertToDatetimeLocal(task.lastModifiedDate));
	    $("#completionDate").val(convertToDatetimeLocal(task.completionDate));

	    // --- DB Info ---
	    $("#dbId").val(task.dbId || "");
	    $("#dbPortId").val(task.dbPortId || "");
	    $("#rowColIndex").val(task.rowColIndex || "");
	    $("#rowNumber").val(task.rowNumber || "");
	    $("#columnNumber").val(task.columnNumber || "");

	    // --- Near Module / Patch Info ---
	    $("#nearModule").val(task.nearModule || "");
	    $("#nearPortNum").val(task.nearPortNum || "");
	    $("#nearPatchType").val(task.nearPatchType || "");

	    // --- FP Location ---
	    $("#fpLocationType").val(task.fpLocationType || "");
	    $("#fpLocationId").val(task.fpLocationId || "");
	    $("#fpLocationName").val(task.fpLocationName || "");
	    $("#fpLocation").val(task.fpLocation || "");

	    // --- FP Equipment ---
	    $("#fpEquipmentType").val(task.fpEquipmentType || "");
	    $("#fpEquipment").val(task.fpEquipment || "");
	    $("#fpEquipmentId").val(task.fpEquipmentId || "");
	    $("#fpEquipmentName").val(task.fpEquipmentName || "");
	    $("#fpAddress").val(task.fpAddress || "");

	    // --- FP Tube / Strand ---
	    $("#fpTubeNb").val(task.fpTubeNb || "");
	    $("#fpStrandColor").val(task.fpStrandColor || "");
	    $("#fpTubeColor").val(task.fpTubeColor || "");
	    $("#fpStrandName").val(task.fpStrandName || "");
	    $("#fpTubeId").val(task.fpTubeId || "");
	    $("#fpTubeName").val(task.fpTubeName || "");

	    // --- FP Fiber / Kit ---
	    $("#fpFiberId").val(task.fpFiberId || "");
	    $("#fpFiberName").val(task.fpFiberName || "");
	    $("#fpKitSerialNum").val(task.fpKitSerialNum || "");

	    // --- FP Module / Port ---
	    $("#fpModule").val(task.fpModule || "");
	    $("#fpPortNum").val(task.fpPortNum || "");

	    // --- FP Junction ---
	    $("#fpJunctionId").val(task.fpJunctionId || "");
	    $("#fpJunctionName").val(task.fpJunctionName || "");
	}

function savePatchAndTask(){

	
	
}


