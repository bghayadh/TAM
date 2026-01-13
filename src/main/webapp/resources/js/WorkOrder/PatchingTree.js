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
				document.getElementById("deleteButton").disabled = false;
				patchForm=1;
				taskForm=0;
	        } else {
	            console.warn("Patching not found for liId:", liId);
				document.getElementById("saveButton").disabled = true;
				document.getElementById("deleteButton").disabled = true;
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
						document.getElementById("deleteButton").disabled = false;
						
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
				document.getElementById("deleteButton").disabled = true;
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
		$("#taskPatchingId").val(task.patchingId || "");
	    $("#taskType").val(task.taskType || "");
	    $("#taskStatus").val(task.taskStatus || "-- Select Option --");

	    $("#creationDate").val(convertToDatetimeLocal(task.creationDate));
	    $("#lastModificationDate").val(convertToDatetimeLocal(task.lastModifiedDate));
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

	
	if(patchForm === 1){
		var patchingId = document.getElementById("patchingId").value;
		var assignedTo = document.getElementById("assignedTo").value;
		var createdDate = document.getElementById("createdDate").value;
		var lastModifiedDate = document.getElementById("lastModifiedDate").value;

		// SELECT
		var patchingStatus = document.getElementById("patchingStatus").value;

		// DATETIME
		var plannedExecutionDate = document.getElementById("plannedExecutionDate").value;
		var actualExecutionDate = document.getElementById("actualExecutionDate").value;

		// TEXTAREA
		var patchingNote = document.getElementById("patchingNote").value;
		var token = $('input[name="csrfToken"]').attr('value');

		       
		          $.ajax({
		              type: "POST",
		              headers: {
		                  'X-CSRFToken': token
		              },
		              url: getContext() + '/savePatchingOrder',
		              async: false,
		          
		
		                   data: {
							"patchingId": patchingId,
						    "patchingStatus" : patchingStatus,
						    "assignedTo" : assignedTo,
							"plannedExecutionDate" : plannedExecutionDate,
						     "actualExecutionDate" : actualExecutionDate,
							 "createdDate"  : createdDate,
							  "lastModifiedDate" : lastModifiedDate,
							  "patchingNote" : patchingNote
		                   },
		                   dataType: "json",
		                   success: function(data) {
							alert("Saved done");
							
		                      if(data.status === "Completed"){
								
								
								disablePatchingForm();
								
							  }
							  else{
								
								enablePatchingForm();
							  }
		                   },
		                   error: function(result) {
		                       alert("Error");
		                   }
		               });
		
		
		
		
		
	}
	
	else if(taskForm === 1){
		
	

		    // BASIC TASK INFO
		    var woTaskId = document.getElementById("woTaskId").value;
		    var taskStatus = document.getElementById("taskStatus").value;
		    var taskType = document.getElementById("taskType").value; 
			var taskPatchingId = document.getElementById("taskPatchingId").value;
			
		    // DATES
		    var creationDate = document.getElementById("creationDate").value;
		    var lastModifiedDate = document.getElementById("lastModificationDate").value;
		    var completionDate = document.getElementById("completionDate").value;

		    // DB INFO
		    var dbId = document.getElementById("dbId").value;
		    var dbPortId = document.getElementById("dbPortId").value;

		    // POSITIONING
		    var rowColIndex = document.getElementById("rowColIndex").value;
		    var rowNumber = document.getElementById("rowNumber").value;
		    var columnNumber = document.getElementById("columnNumber").value;

		    // NEAR SIDE
		    var nearModule = document.getElementById("nearModule").value;
		    var nearPortNum = document.getElementById("nearPortNum").value;
		    var nearPatchType = document.getElementById("nearPatchType").value;

		    // FP LOCATION
		    var fpLocationType = document.getElementById("fpLocationType").value;
		    var fpLocationId = document.getElementById("fpLocationId").value;
		    var fpLocationName = document.getElementById("fpLocationName").value;
		    var fpLocation = document.getElementById("fpLocation").value;

		    // FP EQUIPMENT
		    var fpEquipmentType = document.getElementById("fpEquipmentType").value;
		    var fpEquipment = document.getElementById("fpEquipment").value;
		    var fpEquipmentId = document.getElementById("fpEquipmentId").value;
		    var fpEquipmentName = document.getElementById("fpEquipmentName").value;

		    // FP ADDRESS / TUBE
		    var fpAddress = document.getElementById("fpAddress").value;
		    var fpTubeNb = document.getElementById("fpTubeNb").value;

		    // STRAND / TUBE COLORS
		    var fpStrandColor = document.getElementById("fpStrandColor").value;
		    var fpTubeColor = document.getElementById("fpTubeColor").value;

		    // STRAND / TUBE IDENTIFIERS
		    var fpStrandName = document.getElementById("fpStrandName").value;
		    var fpTubeId = document.getElementById("fpTubeId").value;
		    var fpTubeName = document.getElementById("fpTubeName").value;

		    // FIBER / KIT
		    var fpFiberId = document.getElementById("fpFiberId").value;
		    var fpFiberName = document.getElementById("fpFiberName").value;
		    var fpKitSerialNum = document.getElementById("fpKitSerialNum").value;

		    // MODULE / PORT
		    var fpModule = document.getElementById("fpModule").value;
		    var fpPortNum = document.getElementById("fpPortNum").value;

		    // JUNCTION
		    var fpJunctionId = document.getElementById("fpJunctionId").value;
		    var fpJunctionName = document.getElementById("fpJunctionName").value;

		    
			$.ajax({
			    type: "POST",
			    headers: {
			        'X-CSRFToken': token
			    },
			    url: getContext() + '/saveTaskOrder',
			    async: false,

			    data: {
			        "woTaskId": woTaskId,
			        "taskStatus": taskStatus,
			        "taskType": taskType,
					"taskPatchingId" : taskPatchingId,

			        "creationDate": creationDate,
			        "lastModifiedDate": lastModifiedDate,
			        "completionDate": completionDate,

			        "dbId": dbId,
			        "dbPortId": dbPortId,

			        "rowColIndex": rowColIndex,
			        "rowNumber": rowNumber,
			        "columnNumber": columnNumber,

			        "nearModule": nearModule,
			        "nearPortNum": nearPortNum,
			        "nearPatchType": nearPatchType,

			        "fpLocationType": fpLocationType,
			        "fpLocationId": fpLocationId,
			        "fpLocationName": fpLocationName,
			        "fpLocation": fpLocation,

			        "fpEquipmentType": fpEquipmentType,
			        "fpEquipment": fpEquipment,
			        "fpEquipmentId": fpEquipmentId,
			        "fpEquipmentName": fpEquipmentName,

			        "fpAddress": fpAddress,
			        "fpTubeNb": fpTubeNb,

			        "fpStrandColor": fpStrandColor,
			        "fpTubeColor": fpTubeColor,

			        "fpStrandName": fpStrandName,
			        "fpTubeId": fpTubeId,
			        "fpTubeName": fpTubeName,
			        "fpFiberId": fpFiberId,

			        "fpFiberName": fpFiberName,
			        "fpKitSerialNum": fpKitSerialNum,

			        "fpModule": fpModule,
			        "fpPortNum": fpPortNum,

			        "fpJunctionId": fpJunctionId,
			        "fpJunctionName": fpJunctionName
			    },

			    dataType: "json",

			    success: function (data) {
			        alert("Saved done");

			        if (data.taskStatus === "Completed") {
			            disableTaskForm();
			        }
					else{
						
						enableTaskForm();
					}
			    },

			    error: function (result) {
			        alert("Error");
			    }
			});

	
		
	}
	
	
	
	
	
	
	
}
function getContext() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function disablePatchingForm() {

    document.getElementById("patchingId").disabled = true;
    document.getElementById("patchingStatus").disabled = true;
    document.getElementById("assignedTo").disabled = true;

    document.getElementById("plannedExecutionDate").disabled = true;
    document.getElementById("actualExecutionDate").disabled = true;

    document.getElementById("createdDate").disabled = true;
    document.getElementById("lastModifiedDate").disabled = true;

    document.getElementById("patchingNote").disabled = true;
}
function enablePatchingForm() {

    document.getElementById("patchingId").disabled = false;
    document.getElementById("patchingStatus").disabled = false;
    document.getElementById("assignedTo").disabled = false;

    document.getElementById("plannedExecutionDate").disabled = false;
    document.getElementById("actualExecutionDate").disabled = false;

    // keep these read-only if you want
    document.getElementById("createdDate").disabled = true;
    document.getElementById("lastModifiedDate").disabled = true;

    document.getElementById("patchingNote").disabled = false;
}
function disableTaskForm() {

    document.getElementById("woTaskId").disabled = true;
    document.getElementById("taskStatus").disabled = true;
	document.getElementById("taskPatchingId").disabled = true;
    document.getElementById("taskType").disabled = true;

    document.getElementById("creationDate").disabled = true;
    document.getElementById("lastModificationDate").disabled = true;
    document.getElementById("completionDate").disabled = true;

    document.getElementById("dbId").disabled = true;
    document.getElementById("dbPortId").disabled = true;

    document.getElementById("rowColIndex").disabled = true;
    document.getElementById("rowNumber").disabled = true;
    document.getElementById("columnNumber").disabled = true;

    document.getElementById("nearModule").disabled = true;
    document.getElementById("nearPortNum").disabled = true;
    document.getElementById("nearPatchType").disabled = true;

    document.getElementById("fpLocationType").disabled = true;
    document.getElementById("fpLocationId").disabled = true;
    document.getElementById("fpLocationName").disabled = true;
    document.getElementById("fpLocation").disabled = true;

    document.getElementById("fpEquipmentType").disabled = true;
    document.getElementById("fpEquipment").disabled = true;
    document.getElementById("fpEquipmentId").disabled = true;
    document.getElementById("fpEquipmentName").disabled = true;

    document.getElementById("fpAddress").disabled = true;
    document.getElementById("fpTubeNb").disabled = true;

    document.getElementById("fpStrandColor").disabled = true;
    document.getElementById("fpTubeColor").disabled = true;

    document.getElementById("fpStrandName").disabled = true;
    document.getElementById("fpTubeId").disabled = true;
    document.getElementById("fpTubeName").disabled = true;
    document.getElementById("fpFiberId").disabled = true;

    document.getElementById("fpFiberName").disabled = true;
    document.getElementById("fpKitSerialNum").disabled = true;

    document.getElementById("fpModule").disabled = true;
    document.getElementById("fpPortNum").disabled = true;

    document.getElementById("fpJunctionId").disabled = true;
    document.getElementById("fpJunctionName").disabled = true;
}

function enableTaskForm() {

    document.getElementById("woTaskId").disabled = false;
    document.getElementById("taskStatus").disabled = false;
    document.getElementById("taskType").disabled = false;

    document.getElementById("creationDate").disabled = false;
    document.getElementById("completionDate").disabled = false;

    // keep these read-only like patching
    document.getElementById("lastModificationDate").disabled = true;

    document.getElementById("dbId").disabled = false;
    document.getElementById("dbPortId").disabled = false;

    document.getElementById("rowColIndex").disabled = false;
    document.getElementById("rowNumber").disabled = false;
    document.getElementById("columnNumber").disabled = false;

    document.getElementById("nearModule").disabled = false;
    document.getElementById("nearPortNum").disabled = false;
    document.getElementById("nearPatchType").disabled = false;

    document.getElementById("fpLocationType").disabled = false;
    document.getElementById("fpLocationId").disabled = false;
    document.getElementById("fpLocationName").disabled = false;
    document.getElementById("fpLocation").disabled = false;

    document.getElementById("fpEquipmentType").disabled = false;
    document.getElementById("fpEquipment").disabled = false;
    document.getElementById("fpEquipmentId").disabled = false;
    document.getElementById("fpEquipmentName").disabled = false;

    document.getElementById("fpAddress").disabled = false;
    document.getElementById("fpTubeNb").disabled = false;

    document.getElementById("fpStrandColor").disabled = false;
    document.getElementById("fpTubeColor").disabled = false;

    document.getElementById("fpStrandName").disabled = false;
    document.getElementById("fpTubeId").disabled = false;
    document.getElementById("fpTubeName").disabled = false;
    document.getElementById("fpFiberId").disabled = false;

    document.getElementById("fpFiberName").disabled = false;
    document.getElementById("fpKitSerialNum").disabled = false;

    document.getElementById("fpModule").disabled = false;
    document.getElementById("fpPortNum").disabled = false;

    document.getElementById("fpJunctionId").disabled = false;
    document.getElementById("fpJunctionName").disabled = false;
	document.getElementById("taskPatchingId").disabled = false;
}

function deletePatchAndTask(){
	
	if(patchForm === 1){
		var patchingId = document.getElementById("patchingId").value;

		$.ajax({
		         type: "GET",
		         contentType: "application/json; charset=utf-8",
		         url: getContext()+'/deletePatchingWO',
		         data: {
		        	 "patchingId": patchingId,
		      
		         },
		         dataType: "json",
		         success: function (data) {
		        	
					alert ("delete Patching WO done");
					location.reload();
		     		
		         },
		         error: function(result) {
		             alert("Error");
		         }
		     });
		
		
	}
	
	
	else if(taskForm === 1){
		
		var taskId = document.getElementById("woTaskId").value;

				$.ajax({
				         type: "GET",
				         contentType: "application/json; charset=utf-8",
				         url: getContext()+'/deleteWOTask',
				         data: {
				        	 "taskId": taskId,
				      
				         },
				         dataType: "json",
				         success: function (data) {
				        	
				     		alert ("delete WO Task done");
							location.reload();
				     		
				         },
				         error: function(result) {
				             alert("Error");
				         }
				     });
		}
	
	
	
}
