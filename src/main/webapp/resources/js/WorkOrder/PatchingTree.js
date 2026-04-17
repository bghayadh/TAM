var patchForm=0;
var taskForm=0;
var selectedWorkOrderIdContext="";
var workOrderMenu;
var singleWO;
var patchingLayerMenu;
var patchingLayer;
var checkedPatchingIds = []; 
var checkedTaskIds=[];
function CreateTree_PatchingWorkOrder(PatchingList) {
console.log(PatchingList);
    // Create the initial tree structure for "Discovery New"
    var str_CurrentTree = "<ul style='margin-left:15px;'><li id='initial_ul_CurrentPatchingLayer' class='initial_ul_CurrentPatchingLayer'><input type='checkbox' class='allElements' unchecked name='filter'></input> <span id='initial_Span_CurrentPatchingLayer' class='Parentfolder'> <i class='fa fa-folder' style='color: #08526D;'></i></span><span class='TreeSpan' style='color:black;width:436px'>Patching Work Order </span></li></ul>";
    $("#patchingWorkOrder_tree").append(str_CurrentTree);
	 workOrderMenu = [
	         {
	             'icon': 'folder-plus',
	             'name': 'Create New Work Order',
	             action: () => {
					patchForm = 1;
					taskForm = 0;
					document.getElementById("saveButton").disabled = false;
					document.getElementById("deleteButton").disabled = false;
					// show patching form
					        document.querySelectorAll('.first-div').forEach(div => div.style.display = 'block');
					        document.querySelectorAll('.second-div').forEach(div => div.style.display = 'none');
							document.querySelectorAll('.third-div').forEach(div => div.style.display = 'none');
							clearFields();
							document.getElementById("patchingStatus").value = "Draft";
							console.log("yessss");
	               
	         }},
		 {
		              'icon': 'folder-plus',
		              'name': 'Create New Task',
		              action: () => {
		 				patchForm = 0;
						taskForm=1;
		 				document.getElementById("saveButton").disabled = false;
		 				document.getElementById("deleteButton").disabled = false;
						taskAutoComplete();
		 				// show patching form
		 				        document.querySelectorAll('.first-div').forEach(div => div.style.display = 'none');
		 				        document.querySelectorAll('.second-div').forEach(div => div.style.display = 'none');
		 						document.querySelectorAll('.third-div').forEach(div => div.style.display = 'block');
		 						clearFields();
								enableTaskForm();
								document.getElementById("taskPatchingId").value=selectedWorkOrderIdContext;
		                
		          }},
			  {
			  					    'icon': 'trash',
			  					    'name': 'Delete Task',
			  					    'action': () => {
			  					        $('#DeleteModal').find('input:text').val('');
			  					        $("#DeleteHeader").text("Task Delete: ");

			  					        // ✅ COUNT CHECKED PATCHING WOs + GET IDs
			  					      
			  					        var checkedCount = 0;
			  					        
			  					        $('.Task.checkFilter:checked').each(function() {
			  					            var taskId = $(this).closest('.TaskNode').attr('id');
			  					            checkedTaskIds.push(taskId);
			  					            checkedCount++;
			  					        });
			  					        
			  					        console.log("Checked task count:", checkedCount);
			  					        console.log("Checked task IDs:", checkedTaskIds);

			  					        if (checkedCount === 0) {
			  					            alert("No Tasks have been checked for deletion");
			  					            return;
			  					        }

			  					        $("#deletebody").text("Are you sure you want to delete " + checkedCount + " item(s)?");
			  					        $("#DeleteModal").modal('show');
			  					        $("#deleteTask").show();
			  					        $("#deletePatching").hide();
			  					        
			  					   
			  					       
			  					    }
			  					}];
    // Add Project Manager folder
	 singleWO = new ContextMenu({
	     'theme': 'default',
	     'items': workOrderMenu
	 });
	 
	
			 
	
	 
	  patchingLayerMenu = [
	          {
	              'icon': 'folder-plus',
	              'name': 'Create New Work Order',
	              action: () => {
	 				patchForm = 1;
	 				document.getElementById("saveButton").disabled = false;
	 				document.getElementById("deleteButton").disabled = false;
	 				// show patching form
	 				        document.querySelectorAll('.first-div').forEach(div => div.style.display = 'block');
	 				        document.querySelectorAll('.second-div').forEach(div => div.style.display = 'none');
	 						document.querySelectorAll('.third-div').forEach(div => div.style.display = 'none');
	 						clearFields();

	                
	          }}, 											{
						    'icon': 'trash',
						    'name': 'Delete Patching Work Order',
						    'action': () => {
						        $('#DeleteModal').find('input:text').val('');
						        $("#DeleteHeader").text("Patching Work Order Delete: ");

						        // ✅ COUNT CHECKED PATCHING WOs + GET IDs
						      
						        var checkedCount = 0;
						        
						        $('.WO.checkFilter:checked').each(function() {
						            var patchingId = $(this).closest('.PatchingWO').attr('id');
						            checkedPatchingIds.push(patchingId);
						            checkedCount++;
						        });
						        
						        console.log("Checked patching count:", checkedCount);
						        console.log("Checked patching IDs:", checkedPatchingIds);

						        if (checkedCount === 0) {
						            alert("No Work Orders have been checked for deletion");
						            return;
						        }

						        $("#deletebody").text("Are you sure you want to delete " + checkedCount + " item(s)?");
						        $("#DeleteModal").modal('show');
						        $("#deleteTask").hide();
						        $("#deletePatching").show();
						        
						   
						       
						    }
						}
];
	 // Add Project Manager folder
	  patchingLayer = new ContextMenu({
	      'theme': 'default',
	      'items': patchingLayerMenu
	  });		 
			

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
		$(".PatchingWO > .TreeSpan").contextmenu(function() {
		
		       selectedWorkOrderIdContext = $(this).parent().attr('id');
		    
			   console.log(selectedWorkOrderIdContext);
		       menuName = singleWO;
		       openContext(selectedWorkOrderIdContext, "", singleWO, event);
		   });
		   $(".initial_ul_CurrentPatchingLayer > .TreeSpan").contextmenu(function() {
		   		    menuName = patchingLayer;
		   	       openContext("", "", patchingLayer, event);
		   	   });
			   
			   
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
	
	
	// ✅ Expand "Patching Work Order" root and show all PatchingWO


	
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
			
			
			
			
			taskAutoComplete();
			
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

	function bindHierarchicalCheckboxes() {
	    // 1. ROOT → ALL patches + tasks
	    $(document).on('change', '.allElements', function() {
	        var isChecked = $(this).is(':checked');
	        $('.WO.checkFilter, .Task.checkFilter').prop('checked', isChecked);
	    });
	    
	    // 2. WO → ITS child tasks only
	    $(document).on('change', '.WO.checkFilter', function() {
	        var $woLi = $(this).closest('.PatchingWO');
	        var isChecked = $(this).is(':checked');
	        
	        // Toggle ONLY tasks under THIS WO
	        $woLi.find('> ul > li.TaskNode .Task.checkFilter').prop('checked', isChecked);
	    });
	    
	    // 3. Task change → update parent WO (if all siblings checked)
	    $(document).on('change', '.Task.checkFilter', function() {
	        var $taskLi = $(this).closest('.TaskNode');
	        var $woLi = $taskLi.closest('.PatchingWO');
	        var $woCheckbox = $woLi.find('.WO.checkFilter');
	        
	        // Check if ALL tasks in this WO are checked
	        var allTasksChecked = $woLi.find('.Task.checkFilter:checked').length === 
	                             $woLi.find('.Task.checkFilter').length;
	        
	        $woCheckbox.prop('checked', allTasksChecked);
	    });
	    
	    // 4. Root sync (if any child unchecked)
	    $(document).on('change', '.WO.checkFilter, .Task.checkFilter', function() {
	        var allChecked = $('.WO.checkFilter:checked, .Task.checkFilter:checked').length ===
	                         $('.WO.checkFilter, .Task.checkFilter').length;
	        $('.allElements').prop('checked', allChecked);
	    });
	}

	// ✅ Call after tree creation
bindHierarchicalCheckboxes();		

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
		
		$("#farNearKitSerialNum").val(task.farNearKitSerialNum || "");
		$("#farNearModule").val(task.farNearModule || "");
		$("#farNearPortNum").val(task.farNearPortNum || "");

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
		$("#fpStrandNb").val(task.fpStrandNb || "");
		$("#fpStrandId").val(task.fpStrandId || "");
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

	    /* ================= BP ================= */

	    // --- BP Location ---
	    $("#bpLocationType").val(task.bpLocationType || "");
	    $("#bpLocationId").val(task.bpLocationId || "");
	    $("#bpLocationName").val(task.bpLocationName || "");
	    $("#bpLocation").val(task.bpLocation || "");

	    // --- BP Equipment ---
	    $("#bpEquipmentType").val(task.bpEquipmentType || "");
	    $("#bpEquipment").val(task.bpEquipment || "");
	    $("#bpEquipmentId").val(task.bpEquipmentId || "");
	    $("#bpEquipmentName").val(task.bpEquipmentName || "");

	    // --- BP Address / Status ---
	    $("#bpAddress").val(task.bpAddress || "");
	    $("#bpStatus").val(task.bpStatus || "");

	    // --- BP Strand / Tube ---
	    $("#bpStrandColor").val(task.bpStrandColor || "");
	    $("#bpTubeColor").val(task.bpTubeColor || "");
	    $("#bpStrandId").val(task.bpStrandId || "");
		$("#bpStrandNb").val(task.bpStrandNb || "");
	    $("#bpStrandName").val(task.bpStrandName || "");
	    $("#bpTubeId").val(task.bpTubeId || "");
	    $("#bpTubeName").val(task.bpTubeName || "");
		$("#bpTubeNb").val(task.bpTubeNb || "");
		

	    // --- BP Fiber ---
	    $("#bpFiberId").val(task.bpFiberId || "");
	    $("#bpFiberName").val(task.bpFiberName || "");

	    // --- BP Junction ---
	    $("#bpJunctionId").val(task.bpJunctionId || "");
	    $("#bpJunctionName").val(task.bpJunctionName || "");

	    /* ================= BACK ================= */

	    $("#backModule").val(task.backModule || "");
	    $("#backKitSerialNum").val(task.backKitSerialNum || "");
	    $("#backPortNum").val(task.backPortNum || "");

		
		tubeStrandSetColor("fpStrandColor", "fpStrandNb");
		tubeStrandSetColor("fpTubeColor", "fpTubeNb");
		
		tubeStrandSetColor("bpStrandColor", "bpStrandNb");
		tubeStrandSetColor("bpTubeColor", "bpTubeNb");
	    // --- Enable / Disable ---
	    if (task.taskStatus === "Completed") {
	        disableTaskForm();
	    } else {
	        enableTaskForm();
	    }
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
		console.log("Tree container:", $("#patchingWorkOrder_tree").length, $("#patchingWorkOrder_tree"));
		console.log("Initial UL:", $("#initial_ul_CurrentPatchingLayer").length, $("#initial_ul_CurrentPatchingLayer"));
		console.log("Container scrollable?", $("#patchingWorkOrder_tree").css("overflow-y"));

		       
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
						       document.getElementById("patchingId").value = data.id;

						       var dateText = "";
						       if (data.lastModifiedDate) {
						           dateText = data.lastModifiedDate.split(" ")[0];
						       }

							   if(patchingId === ""){
						       var patchingLi =
						           "<ul>" +
						               "<li id='" + data.id + "' class='PatchingWO' style='display:none;width:100px;'>" +
						                   "<input type='checkbox' class='WO checkFilter' name='Element'></input> " +
						                   "<span class='TreeSpan' style='color:black;width:355px'>" +
						                       data.id + " -- " + dateText +
						                   "</span>" +
						               "</li>" +
						           "</ul>";

						       // add new WO under root
						       $("#initial_ul_CurrentPatchingLayer").append(patchingLi);
							   PatchingList.push(data.node);//zeina
							   bindTreeEventsForNewNodes();
							   $("#"+data.id+" > .TreeSpan").contextmenu(function() {
							   
							   	       selectedWorkOrderIdContext = $(this).parent().attr('id');
							   	    
							   		   console.log(selectedWorkOrderIdContext);
							   	       menuName = singleWO;
							   	       openContext(selectedWorkOrderIdContext, "", singleWO, event);
							   	   });
}
						       var newNode = $("#" + data.id);

						       // 1) show ALL patching orders ONLY NOW (not on reload)
						       $("#initial_ul_CurrentPatchingLayer").find("> ul > li.PatchingWO").show();

						       // 2) open root folder icon - FIXED for SVG Font Awesome
						       var $iconSvg = $("#initial_Span_CurrentPatchingLayer svg");
						       console.log("SVG icon found:", $iconSvg.length);  // DEBUG: Should be 1
						       
						       if ($iconSvg.length > 0) {
						           $iconSvg.removeClass("fa-folder").addClass("fa-folder-open");
						           console.log("Icon toggled successfully");  // DEBUG
						       } else {
						           console.error("No SVG icon found in #initial_Span_CurrentPatchingLayer");
						       }

						       // 3) highlight new WO
						       $(".TreeSpan").css("background-color", "").removeClass("selected-span");
						       newNode.find("> .TreeSpan")
						           .addClass("selected-span")
						           .css("background-color", "#97b9cc");

						       // 4) scroll to it with some offset
						       var scrollOffset = newNode.position().top;
						       $("#left").animate({ scrollTop: scrollOffset - 150 }, "slow");

						       if (data.status === "Completed") {
						           disablePatchingForm();
						       } else {
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

			
			var bpLocationType = document.getElementById("bpLocationType").value;
			var bpLocationId = document.getElementById("bpLocationId").value;
			var bpLocationName = document.getElementById("bpLocationName").value;
			var bpLocation = document.getElementById("bpLocation").value;

			// ================= BP EQUIPMENT =================
			var bpEquipmentType = document.getElementById("bpEquipmentType").value;
			var bpEquipment = document.getElementById("bpEquipment").value;
			var bpEquipmentId = document.getElementById("bpEquipmentId").value;
			var bpEquipmentName = document.getElementById("bpEquipmentName").value;

			// ================= BP ADDRESS / STATUS =================
			var bpAddress = document.getElementById("bpAddress").value;
			var bpStatus = document.getElementById("bpStatus").value;

			// ================= BP STRAND / TUBE =================
			var bpStrandColor = document.getElementById("bpStrandColor").value;
			var bpTubeColor = document.getElementById("bpTubeColor").value;

			var bpStrandId = document.getElementById("bpStrandId").value;
			var bpStrandName = document.getElementById("bpStrandName").value;

			var bpTubeId = document.getElementById("bpTubeId").value;
			var bpTubeName = document.getElementById("bpTubeName").value;

			var bpFiberId = document.getElementById("bpFiberId").value;
			var bpFiberName = document.getElementById("bpFiberName").value;

			// ================= BP JUNCTION =================
			var bpJunctionId = document.getElementById("bpJunctionId").value;
			var bpJunctionName = document.getElementById("bpJunctionName").value;

			// ================= BACK =================
			var backModule = document.getElementById("backModule").value;
			var backKitSerialNum = document.getElementById("backKitSerialNum").value;
			var backPortNum = document.getElementById("backPortNum").value; 
			
	
			
			var farNearKitSerialNum = document.getElementById("farNearKitSerialNum").value; 
			var farNearModule = document.getElementById("farNearModule").value; 
			var farNearPortNum = document.getElementById("farNearPortNum").value; 
			var fpStrandNb = document.getElementById("fpStrandNb").value; 
			var fpStrandId = document.getElementById("fpStrandId").value; 
			var bpStrandNb = document.getElementById("bpStrandNb").value; 
			var bpTubeNb = document.getElementById("bpTubeNb").value; 
			
			if (!rowColIndex || !rowNumber || !columnNumber) {
			     alert(" RowColIndex, Row Number, and Column Number are required!");
			     return;  // Stop saving
			 }
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

					"farNearKitSerialNum": farNearKitSerialNum,
					 "farNearModule": farNearModule,
					  "farNearPortNum": farNearPortNum,

					  "fpStrandNb": fpStrandNb,
					  "fpStrandId": fpStrandId,
					  "bpStrandNb": bpStrandNb,
					  "bpTubeNb": bpTubeNb,
					
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
			        "fpJunctionName": fpJunctionName, 
					"bpLocationType": bpLocationType,
					"bpLocationId": bpLocationId,
					"bpLocationName": bpLocationName,
					"bpLocation": bpLocation,

					"bpEquipmentType": bpEquipmentType,
					"bpEquipment": bpEquipment,
					"bpEquipmentId": bpEquipmentId,
					"bpEquipmentName": bpEquipmentName,

					"bpAddress": bpAddress,
					"bpStatus": bpStatus,

					"bpStrandColor": bpStrandColor,
					"bpTubeColor": bpTubeColor,

					"bpStrandId": bpStrandId,
					"bpStrandName": bpStrandName,

					"bpTubeId": bpTubeId,
					"bpTubeName": bpTubeName,

					"bpFiberId": bpFiberId,
					"bpFiberName": bpFiberName,

					"bpJunctionId": bpJunctionId,
					"bpJunctionName": bpJunctionName,

					// ================= BACK =================
					"backModule": backModule,
					"backKitSerialNum": backKitSerialNum,
					"backPortNum": backPortNum
			    },

			    dataType: "json",

				success: function (data) {
				    alert("Saved done");

				    // NEW TASK
				    if (woTaskId === "") {
				        console.log("Creating new task...");
				        
				        var patchingId = taskPatchingId;
				        var $patchLi = $("#" + patchingId);

				        if ($patchLi.length) {
				            // 1) ADD FOLDER if missing
				            if (!$patchLi.find("> .folder").length) {
				                var $oldSpan = $patchLi.find("> .TreeSpan").first();
				                var oldText = $oldSpan.text();

				                $patchLi.empty();
				                
				                var patchHtml = 
				                    "<input type='checkbox' class='WO checkFilter' name='Element'></input> " +
				                    "<span class='folder'>" +  // ✅ Added folder class
				                        "<i class='fa fa-folder-open' style='color: #08526D;'></i>" +  // ✅ CLOSED by default
				                    "</span>" +
				                    "<span class='TreeSpan' style='color:black;width:355px'>" + oldText + "</span>" +
				                    "<ul></ul>";  // ✅ Child container

				                $patchLi.append(patchHtml);
								// Enable tree folder collapse/expand functionality
								
								  treeCollapseFolder(".folder", "fast", ".folder");//zeina 
								  
								  
				            }

				            // 2) ADD NEW TASK
				            var $childUl = $patchLi.children("ul").first();
				            var taskText = data.woTaskId + " -- " + data.taskType;

				            var $newTaskLi = $("<li>", {
				                id: data.woTaskId,
				                class: "TaskNode",
				                style: "display: block; width: 100px;"
				            }).append(
				                "<input type='checkbox' class='Task checkFilter' name='Element'></input> " +
				                "<span class='TreeSpan' style='color:black;width:340px;'>" + taskText + "</span>"
				            );

				            $childUl.append($newTaskLi);

				            // ✅ REBIND EVENTS for new nodes
							var newTask = data.task;

							      // 🔥 find correct patching
							      var patching = PatchingList.find(p => p.patchingId === patchingId);

							      if (patching) {
							          // 🔥 add task
							          patching.tasks.push(newTask);

							          console.log("Task added successfully");
}
				            bindTreeEventsForNewNodes();
							
				        }

				        $("#woTaskId").val(data.woTaskId);
				    }

				    // ✅ FIX #1: SHOW ALL TASKS under this patching
				    $("#" + patchingId + " > ul > li.TaskNode").show();

				    // ✅ FIX #2: OPEN PARENT FOLDER (use folder span, not SVG)
				    var $folderSpan = $("#" + patchingId + " > .folder");
					console.log($folderSpan);
			        //var $icon = $folderSpan.find('li');
					var $icon = $("#" + patchingId +" svg");
					console.log("SVG icon found:", $icon.length);  // DEBUG: Should be 1
									  
				    
				    console.log("Folder span found:", $folderSpan.length, "Icon found:", $icon.length);
				    
				    if ($folderSpan.length && $icon.length) {
				        // Show children AND change to open icon
				        $folderSpan.closest("li").find("> ul > li").show();
				        $icon.removeClass("fa-folder").addClass("fa-folder-open");
				    }

				    // ✅ FIX #3: HIGHLIGHT NEW TASK
				    var $newNode = $("#" + data.woTaskId);
				    $(".TreeSpan").removeClass("selected-span").css("background-color", "");
				    $newNode.find("> .TreeSpan")
				        .addClass("selected-span")
				        .css("background-color", "#97b9cc");

				    // ✅ FIX #4: SCROLL TO NEW TASK
				    var scrollOffset = $newNode.position().top - 50;  // 50px offset
				    $("#left").animate({ scrollTop: scrollOffset }, "slow");

				    // Form status
				    if (data.taskStatus === "Completed") {
				        disableTaskForm();
				    } else {
				        enableTaskForm();
				    }
				}
,

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
    document.getElementById("taskType").disabled = true;

    document.getElementById("creationDate").disabled = true;
    document.getElementById("completionDate").disabled = true;

    // keep read-only
    document.getElementById("lastModificationDate").disabled = true;

    document.getElementById("dbId").disabled = true;
    document.getElementById("dbPortId").disabled = true;

    document.getElementById("rowColIndex").disabled = true;
    document.getElementById("rowNumber").disabled = true;
    document.getElementById("columnNumber").disabled = true;

    document.getElementById("nearModule").disabled = true;
    document.getElementById("nearPortNum").disabled = true;
    document.getElementById("nearPatchType").disabled = true;

    /* ================= FP ================= */

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

    /* ================= BP ================= */

    document.getElementById("bpLocationType").disabled = true;
    document.getElementById("bpLocationId").disabled = true;
    document.getElementById("bpLocationName").disabled = true;
    document.getElementById("bpLocation").disabled = true;

    document.getElementById("bpEquipmentType").disabled = true;
    document.getElementById("bpEquipment").disabled = true;
    document.getElementById("bpEquipmentId").disabled = true;
    document.getElementById("bpEquipmentName").disabled = true;

    document.getElementById("bpAddress").disabled = true;
    document.getElementById("bpStatus").disabled = true;

    document.getElementById("bpStrandColor").disabled = true;
    document.getElementById("bpTubeColor").disabled = true;

    document.getElementById("bpStrandId").disabled = true;
    document.getElementById("bpStrandName").disabled = true;

    document.getElementById("bpTubeId").disabled = true;
    document.getElementById("bpTubeName").disabled = true;

    document.getElementById("bpFiberId").disabled = true;
    document.getElementById("bpFiberName").disabled = true;

    document.getElementById("bpJunctionId").disabled = true;
    document.getElementById("bpJunctionName").disabled = true;

    /* ================= BACK ================= */

    document.getElementById("backModule").disabled = true;
    document.getElementById("backKitSerialNum").disabled = true;
    document.getElementById("backPortNum").disabled = true;

    document.getElementById("taskPatchingId").disabled = true;

	
	document.getElementById("farNearKitSerialNum").disabled = true;
	document.getElementById("farNearModule").disabled = true;
	document.getElementById("farNearPortNum").disabled = true;
	document.getElementById("fpStrandNb").disabled = true;
	document.getElementById("fpStrandId").disabled = true;
	document.getElementById("bpStrandNb").disabled = true;
	document.getElementById("bpTubeNb").disabled = true;
}

function enableTaskForm() {
    const enable = [
        "woTaskId", "taskStatus", "taskType",
        "creationDate", "completionDate",

        "dbId", "dbPortId",
        "rowColIndex", "rowNumber", "columnNumber",
        "nearModule", "nearPortNum", "nearPatchType",

        /* ================= FP ================= */
        "fpLocationType", "fpLocationId", "fpLocationName", "fpLocation",
        "fpEquipmentType", "fpEquipment", "fpEquipmentId", "fpEquipmentName",
        "fpAddress", "fpTubeNb",
        "fpStrandColor", "fpTubeColor",
        "fpStrandName", "fpTubeId", "fpTubeName",
        "fpFiberId", "fpFiberName",
        "fpKitSerialNum", "fpModule", "fpPortNum",
        "fpJunctionId", "fpJunctionName",

        /* ================= BP ================= */
        "bpLocationType", "bpLocationId", "bpLocationName", "bpLocation",
        "bpEquipmentType", "bpEquipment", "bpEquipmentId", "bpEquipmentName",
        "bpAddress", "bpStatus",
        "bpStrandColor", "bpTubeColor",
        "bpStrandId", "bpStrandName",
        "bpTubeId", "bpTubeName",
        "bpFiberId", "bpFiberName",
        "bpJunctionId", "bpJunctionName",

        /* ================= BACK ================= */
        "backModule", "backKitSerialNum", "backPortNum",

        "taskPatchingId, farNearKitSerialNum, farNearModule, farNearPortNum, fpStrandNb, fpStrandId, bpStrandNb, bpTubeNb"
    ];

    enable.forEach(id => {
        const el = document.getElementById(id);
        if (el) el.disabled = false;
    });

    // keep read-only
    const readOnly = ["lastModificationDate"];
    readOnly.forEach(id => {
        const el = document.getElementById(id);
        if (el) el.disabled = true;
    });
}


function deletePatchingTree(){
	
	$.ajax({
		         type: "GET",
		         contentType: "application/json; charset=utf-8",
		         url: getContext()+'/deletePatchingWoTree',
		         data: {
		        	 "checkedPatchingIds[]": checkedPatchingIds,
		      
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
};
$("#deleteTask").click(function() {
	$.ajax({
		         type: "GET",
		         contentType: "application/json; charset=utf-8",
		         url: getContext()+'/deleteTaskTree',
		         data: {
		        	 "patchingId": patchingId,
		      
		         },
		         dataType: "json",
		         success: function (data) {
		        	
					
					location.reload();
		     		
		         },
		         error: function(result) {
		             alert("Error");
		         }
		     });
});

function deleteTaskTree(){
	
	$.ajax({
		         type: "GET",
		         contentType: "application/json; charset=utf-8",
		         url: getContext()+'/deleteTaskTree',
		         data: {
		        	 "checkedTaskIds[]": checkedTaskIds,
		      
		         },
		         dataType: "json",
		         success: function (data) {
		        	
					var $parentWO = $("#" + checkedTaskIds[0]).closest('.PatchingWO');
					   var parentId = $parentWO.attr('id');
					   
					  
					 
					checkedTaskIds.forEach(function(taskId) {
					       // Remove task LI
					       $("#" + taskId).remove();
					      
					   });
					  

						 $("#DeleteModal").modal('hide');
		         },
		         error: function(result) {
		             alert("Error");
		         }
		     });
};
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

function clearFields() {
    const container = document.getElementById('right');
    if (!container) return;

    // Clear all inputs
    const inputs = container.querySelectorAll('input');
    inputs.forEach(input => {
        if (input.type === 'checkbox' || input.type === 'radio') {
            input.checked = false;
        } else {
            input.value = '';
        }
    });

    // Clear all selects
    const selects = container.querySelectorAll('select');
    selects.forEach(select => {
        select.selectedIndex = 0; // first option: "-- Select Option --"
    });

    // Clear all textareas
    const textareas = container.querySelectorAll('textarea');
    textareas.forEach(area => {
        area.value = '';
    });
}



function expandRootPatchingFolder() {
    var rootLi = $("#initial_ul_CurrentPatchingLayer");

    // Show ALL direct patching orders under this root
    rootLi.find("> ul > li.PatchingWO").show();

    // Set icon to open
    $("#initial_Span_CurrentPatchingLayer i")
        .removeClass("fa-folder")
        .addClass("fa-folder-open");
}

// call it after building tree
function bindTreeEventsForNewNodes() {

    // click behavior (same as your big $(".TreeSpan").on("click", ...) block)
    $(".TreeSpan").off("click").on("click", function () {

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

        // PATCHING CLICK
        if (parentLi.hasClass("PatchingWO")) {

            console.log("PATCHING clicked");

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
                patchForm = 1;
                taskForm = 0;
            } else {
                console.warn("Patching not found for liId:", liId);
                document.getElementById("saveButton").disabled = true;
                document.getElementById("deleteButton").disabled = true;
            }
        }

        // TASK CLICK
        else if (parentLi.hasClass("TaskNode")) {

            console.log("TASK clicked");

            document.querySelectorAll('.first-div').forEach(div => div.style.display = 'none');
            document.querySelectorAll('.second-div').forEach(div => div.style.display = 'none');
            document.querySelectorAll('.third-div').forEach(div => div.style.display = 'block');

            var taskFound = false;

            for (var i = 0; i < PatchingList.length; i++) {
                var tasks = PatchingList[i].tasks || [];

                for (var j = 0; j < tasks.length; j++) {
                    if (tasks[j].woTaskId == liId) {
                        populateTask(tasks[j]);
                        patchForm = 0;
                        taskForm  = 1;
                        document.getElementById("saveButton").disabled = false;
                        document.getElementById("deleteButton").disabled = false;
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

    // context menus
    $(".PatchingWO > .TreeSpan").off("contextmenu").on("contextmenu", function (event) {
        selectedWorkOrderIdContext = $(this).parent().attr('id');
       menuName = singleWO;
        openContext(selectedWorkOrderIdContext, "", singleWO, event);
    });

    $(".initial_ul_CurrentPatchingLayer > .TreeSpan").off("contextmenu").on("contextmenu", function (event) {
        menuName = patchingLayer;
        openContext("", "", patchingLayer, event);
    });

    // hover effect
    MouseHoveringSpans(null);
}
function openContext(id, name, menuName, event) {



		     // open the menu with a delay
		     const time = menuName.isOpen() ? 100 : 0;

		     // hide the current menu (if any)
		     menuName.hide();

		     // display menu at mouse click position
		     setTimeout(() => { menuName.show(event.pageX, event.pageY) }, time);

		     // close the menu if the user clicks anywhere on the screen
		     document.addEventListener('contextmenu', hideContext, true);
		     document.addEventListener('click', hideContext, true);
		    
		     window["menuName"] = menuName;
		     event.preventDefault();
		 }


		 function hideContext() {

		     // hide the menu
		     if (window["menuName"] != "") {
		         window["menuName"].hide();
		         //hideMenu();
		         // remove the listener from the document
		         document.removeEventListener('contextmenu', hideContext);
		         document.removeEventListener('click', hideContext);
		     }
		 }
		
		function  deleteTermination(){
		 		$("#DeleteModal").modal('hide');
		 	};
			
			function ClosingConfirm(){
				var result= confirm('are you sure you want to close?')
				if (result== false){
					$("#DeleteModal").modal('show');
					}
				 if (result== true){
				 $("#DeleteModal").modal('hide');
				}
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

			document.addEventListener('DOMContentLoaded', function() {
				document.getElementById('fpStrandColor').addEventListener('change', function() {
					tubeStrandSetColor('fpStrandColor', 'fpStrandNb');
				});

				document.getElementById('fpStrandNb').addEventListener('change', function() {
				    const colorElement = document.getElementById('fpStrandColor');
				    const numberElement = document.getElementById('fpStrandNb');
				    tubeStrandNoChange(numberElement, colorElement);
				});

				document.getElementById('fpTubeColor').addEventListener('change', function() {
					tubeStrandSetColor('fpTubeColor', 'fpTubeNb');
				});

				document.getElementById('fpTubeNb').addEventListener('change', function() {
				    const colorElement = document.getElementById('fpTubeColor');
				    const numberElement = document.getElementById('fpTubeNb');
				    tubeStrandNoChange(numberElement, colorElement);
				});
				});
				
				
				
				document.addEventListener('DOMContentLoaded', function() {
								document.getElementById('bpStrandColor').addEventListener('change', function() {
									tubeStrandSetColor('bpStrandColor', 'bpStrandNb');
								});

								document.getElementById('bpStrandNb').addEventListener('change', function() {
								    const colorElement = document.getElementById('bpStrandColor');
								    const numberElement = document.getElementById('bpStrandNb');
								    tubeStrandNoChange(numberElement, colorElement);
								});

								document.getElementById('bpTubeColor').addEventListener('change', function() {
									tubeStrandSetColor('bpTubeColor', 'bpTubeNb');
								});

								document.getElementById('bpTubeNb').addEventListener('change', function() {
								    const colorElement = document.getElementById('bpTubeColor');
								    const numberElement = document.getElementById('bpTubeNb');
								    tubeStrandNoChange(numberElement, colorElement);
								});
								});
								
								
								
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

							
												function debounce(fn, delay) {
												    var timer;
												    return function() {
												      var args = [].slice.call(arguments);
												      var context = this;
												      if (timer) {
												        window.clearTimeout(timer);
												      }
												      timer = window.setTimeout(function() {
												        fn.apply(context, args);
												      }, delay);
												    };
												  };
function taskAutoComplete(){
	$("#dbPortId").autocomplete({
															source: debounce(function(request, response) {

																let sName = $("#dbPortId").val();
																var url = "";
																      var dataTarget = {};

																$.ajax({
																	type: "GET",
																	url: getContext() + '/getAllDbPort',
																	contentType: "application/json; charset=utf-8",
																	dataType: "json",
																	data: {
																		"searchId": sName
																	},
																	success: function(data) {
																		if (data != null) {
																			response(data.glist);
																			console.log(data.glist)
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

																$("#dbId").val(ui.item[1]);
														

															

																return false;
															}
														})
															.data("ui-autocomplete")._renderItem = function(ul, item) {

																return $('<li class="each"></li>')
																	.data("ui-autocomplete-item", item)
																	.append(
																		'<div class="acItem">' +
																		'<span class="name" style="font-weight:bold">' + item[0] + '</span><br>' +
																		'<span class="desc">' + item[1]  + '</span>' +
																		'</div>'
																	)
																	.appendTo(ul);
															};

														// FIXED selector (lowercase p)
														$("#dbPortId").focus(function() {
															if (this.value === "") {
																$(this).autocomplete("search");
															}
});

 var  locationType="";
$("#fpLocationId").autocomplete({
															source: debounce(function(request, response) {

																var  searchVal = $("#fpLocationId").val();
															  locationType = $("#fpLocationType").val();
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
																	position: {
																	    my: "left top",
																	    at: "left bottom",
																	    collision: "flipfit"
																	},
																    select: function(event, ui) {
																      
																		document.getElementById("fpLocationName").value=ui.item[1];
																      
																        if (locationType == "Customer" || locationType == "Manhole" || locationType == "Handhole" || locationType == "DB") {
																            this.value = ui.item ? ui.item[0] : "";
																        }
																        else if (locationType == "Site") {
																            this.value = ui.item ? ui.item[2] : "";
																         
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

																	$("#fpLocationId").focus(function() {
																    if (this.value === "") $(this).autocomplete("search");
																});
															

				
				
				

				
				
				
				$("#fpStrandId").autocomplete({
												source: debounce(function(request, response, event, ui) {

													let sId = $("#fpStrandId").val();
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

													$("#fpStrandName").val(ui.item[1]);
													$("#fpTubeId").val(ui.item[2]);
													$("#fpFiberId").val(ui.item[3]);
													$("#fpTubeName").val(ui.item[5]);
													$("#fpFiberName").val(ui.item[4]);
													$("#fpStrandNb").val(ui.item[6]);
													$("#fpTubeNb").val(ui.item[8]);

													if (ui.item[7] !== "" && ui.item[7] != null) {

														$("#fpTubeColor").val(ui.item[9]);
														$("#fpStrandColor").val(ui.item[7]);

														tubeStrandSetColor("fpStrandColor", "fpStrandNb");
														tubeStrandSetColor("fpTubeColor", "fpTubeNb");
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

											$("#fpStrandId").focus(function() {
												if (this.value === "") {
													$(this).autocomplete("search");
												}
											});


											$("#fpStrandName").autocomplete({
												source: debounce(function(request, response) {

													let sName = $("#fpStrandName").val();

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

													$("#fpStrandId").val(ui.item[0]);
													$("#fpTubeId").val(ui.item[2]);
													$("#fpFiberId").val(ui.item[3]);
													$("#fpTubeName").val(ui.item[5]);
													$("#fpFiberName").val(ui.item[4]);
													$("#fpStrandNb").val(ui.item[6]);
													$("#fpTubeNb").val(ui.item[8]);

													// Correct condition AND correct ID names
													if (ui.item[7] !== "" && ui.item[7] != null) {

														$("#fpTubeColor").val(ui.item[9]);
														$("#fpStrandColor").val(ui.item[7]); // FIXED CAPITAL LETTER

														// Set colors correctly
														tubeStrandSetColor("fpStrandColor", "fpStrandNb");
														tubeStrandSetColor("fpTubeColor", "fpTubeNb");
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
											$("#fpStrandName").focus(function() {
												if (this.value === "") {
													$(this).autocomplete("search");
												}
											});







											$("#bpStrandId").autocomplete({
												source: debounce(function(request, response, event, ui) {

													let sId = $("#bpStrandId").val();
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

													$("#bpStrandName").val(ui.item[1]);
													$("#bpTubeId").val(ui.item[2]);
													$("#bpFiberId").val(ui.item[3]);
													$("#bpTubeName").val(ui.item[5]);
													$("#bpFiberName").val(ui.item[4]);
													$("#bpStrandNb").val(ui.item[6]);
													$("#bpTubeNb").val(ui.item[8]);

													if (ui.item[7] !== "" && ui.item[7] != null) {

														$("#bpTubeColor").val(ui.item[9]);
														$("#bpStrandColor").val(ui.item[7]);

														tubeStrandSetColor("bpStrandColor", "bpStrandNb");
														tubeStrandSetColor("bpTubeColor", "bpTubeNb");
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

											$("#bpStrandId").focus(function() {
												if (this.value === "") {
													$(this).autocomplete("search");
												}
											});


											$("#bpStrandName").autocomplete({
												source: debounce(function(request, response) {

													let sName = $("#bpStrandName").val();

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

													$("#bpStrandId").val(ui.item[0]);
													$("#bpTubeId").val(ui.item[2]);
													$("#bpFiberId").val(ui.item[3]);
													$("#bpTubeName").val(ui.item[5]);
													$("#bpFiberName").val(ui.item[4]);
													$("#bpStrandNb").val(ui.item[6]);
													$("#bpTubeNb").val(ui.item[8]);

													// Correct condition AND correct ID names
													if (ui.item[7] !== "" && ui.item[7] != null) {

														$("#bpTubeColor").val(ui.item[9]);
														$("#bpStrandColor").val(ui.item[7]); // FIXED CAPITAL LETTER

														// Set colors correctly
														tubeStrandSetColor("bpStrandColor", "bpStrandNb");
														tubeStrandSetColor("bpTubeColor", "bpTubeNb");
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
											$("#bpStrandName").focus(function() {
												if (this.value === "") {
													$(this).autocomplete("search");
												}
											});




											$("#fpFiberId").autocomplete({
																source: debounce(function(request, response, event, ui) {
																	console.log("fiber id");
																	var fId = $("#fpFiberId").val();
																	var cId = $("#fpFiberId").val();
																	var sId = $("#fpStrandId").val();
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
																	$("#fpFiberName").val(ui.item[1]);


																	return false;
																}
															}).data("ui-autocomplete")._renderItem = function(ul, item) {
																return $('<li class="each"></li>').data("ui-autocomplete-item", item)
																	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
																		item[0] + '</span><br><span class="desc">' +
																		item[1] + '</span></div>').appendTo(ul);
															};
															$("#fpFiberId").focus(function() {
																if (this.value == "") {
																	$(this).autocomplete("search");
																}
															});
															///////////////////////////////
															$("#fpFiberName").autocomplete({
																source: debounce(function(request, response, event, ui) {
																	var fName = $("#fpFiberName").val();
																	var cId = $("#fpTubeId").val();
																	var sId = $("#fpStrandId").val();
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
																	$("#fpFiberId").val(ui.item[0]);


																	return false;
																}
															}).data("ui-autocomplete")._renderItem = function(ul, item) {
																return $('<li class="each"></li>').data("ui-autocomplete-item", item)
																	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
																		item[1] + '</span><br><span class="desc">' +
																		item[0] + '</span></div>').appendTo(ul);
															};
															$("#fpFiberName").focus(function() {
																if (this.value == "") {
																	$(this).autocomplete("search");
																}
															});




															$("#bpFiberId").autocomplete({
																source: debounce(function(request, response, event, ui) {
																	console.log("fiber id");

																	var fId = $("#bpFiberId").val();
																	var cId = $("#bpTubeId").val();
																	var sId = $("#bpStrandId").val();

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
																	$("#bpFiberName").val(ui.item[1]);
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

															$("#bpFiberId").focus(function() {
																if (this.value == "") {
																	$(this).autocomplete("search");
																}
															});

															//////////////////////////////////////////

															$("#bpFiberName").autocomplete({
																source: debounce(function(request, response, event, ui) {

																	var fName = $("#bpFiberName").val();
																	var cId = $("#bpTubeId").val();
																	var sId = $("#bpStrandId").val();

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
																	$("#bpFiberId").val(ui.item[0]);
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

															$("#bpFiberName").focus(function() {
																if (this.value == "") {
																	$(this).autocomplete("search");
																}
															});







															$("#fpTubeId").autocomplete({
																source: debounce(function(request, response, event, ui) {
																	var cName = $("#fpTubeId").val();
																	var sId = $("#fpStrandId").val();
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
																	$("#fpFiberId").val(ui.item[2]);
																	$("#fpTubeName").val(ui.item[1]);
																	$("#fpFiberName").val(ui.item[3]);
																	$("#fpTubeNb").val(ui.item[4]);

																	if (ui.item[5] != "" || ui.item[5] != null) {
																		$("#fpTubeColor").val(ui.item[5]);
																		tubeStrandSetColor("fpTubeColor", "fpTubeNb");
																	}


																	return false;
																}
															}).data("ui-autocomplete")._renderItem = function(ul, item) {
																return $('<li class="each"></li>').data("ui-autocomplete-item", item)
																	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
																		item[0] + '</span><br><span class="desc">' +
																		item[1] + ', ' + item[3] + '</span></div>').appendTo(ul);
															};
															$("#fpTubeId").focus(function() {
																if (this.value == "") {
																	$(this).autocomplete("search");
																}
															});

															$("#fpTubeName").autocomplete({
																source: debounce(function(request, response, event, ui) {
																	var cName = $("#fpTubeName").val();
																	var sId = $("#fpStrandId").val();
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

																	$("#fpFiberId").val(ui.item[2]);
																	$("#fpTubeId").val(ui.item[0]);
																	$("#fpFiberName").val(ui.item[3]);
																	$("#fpTubeNb").val(ui.item[4]);

																	if (ui.item[5] != "" || ui.item[5] != null) {
																		$("#fpTubeColor").val(ui.item[5]);
																		tubeStrandSetColor("fpTubeColor", "fpTubeNb");
																	}

																	return false;
																}
															}).data("ui-autocomplete")._renderItem = function(ul, item) {
																return $('<li class="each"></li>').data("ui-autocomplete-item", item)
																	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
																		item[1] + '</span><br><span class="desc">' +
																		item[0] + ', ' + item[3] + '</span></div>').appendTo(ul);
															};
															$("#fpTubeName").focus(function() {
																if (this.value == "") {
																	$(this).autocomplete("search");
																}
															});



															$("#bpTubeId").autocomplete({
																source: debounce(function(request, response, event, ui) {

																	var cName = $("#bpTubeId").val();
																	var sId = $("#bpStrandId").val();

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

																	$("#bpFiberId").val(ui.item[2]);
																	$("#bpTubeName").val(ui.item[1]);
																	$("#bpFiberName").val(ui.item[3]);
																	$("#bpTubeNb").val(ui.item[4]);

																	if (ui.item[5] != "" || ui.item[5] != null) {
																		$("#bpTubeColor").val(ui.item[5]);
																		tubeStrandSetColor("bpTubeColor", "bpTubeNb");
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

															$("#bpTubeId").focus(function() {
																if (this.value == "") {
																	$(this).autocomplete("search");
																}
															});

															$("#bpTubeName").autocomplete({
															    source: debounce(function (request, response, event, ui) {

															        var cName = $("#bpTubeName").val();
															        var sId = $("#bpStrandId").val();
															        var searchId;

															        if (sId !== "") {
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
															            success: function (data) {
															                if (data != null) {
															                    response(data.clist);
															                }
															            },
															            error: function (result) {
															                alert("Error");
															            }
															        });

															    }, 900),
															    minLength: 0,
															    maxShowItems: 4,
															    scroll: true,
															    select: function (event, ui) {

															        this.value = (ui.item ? ui.item[1] : '');

															        $("#bpFiberId").val(ui.item[2]);
															        $("#bpTubeId").val(ui.item[0]);
															        $("#bpFiberName").val(ui.item[3]);
															        $("#bpTubeNb").val(ui.item[4]);

															        // Better check: use && instead of || so it must be non-empty AND non-null
															        if (ui.item[5] !== "" && ui.item[5] != null) {
															            $("#bpTubeColor").val(ui.item[5]);
															            tubeStrandSetColor("bpTubeColor", "bpTubeNb");
															        }

															        return false;
															    }
															}).data("ui-autocomplete")._renderItem = function (ul, item) {

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

															$("#bpTubeName").focus(function () {
															    if (this.value === "") {
															        $(this).autocomplete("search");
															    }
															});
															$("#bpLocationId").autocomplete({
																														source: debounce(function(request, response) {

																															var  searchVal = $("#bpLocationId").val();
																														  locationType = $("#bpLocationType").val();
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
																																position: {
																																    my: "left top",
																																    at: "left bottom",
																																    collision: "flipfit"
																																},
																															    select: function(event, ui) {
																															      
																																	document.getElementById("bpLocationName").value=ui.item[1];
																															      
																															        if (locationType == "Customer" || locationType == "Manhole" || locationType == "Handhole" || locationType == "DB") {
																															            this.value = ui.item ? ui.item[0] : "";
																															        }
																															        else if (locationType == "Site") {
																															            this.value = ui.item ? ui.item[2] : "";
																															         
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

																																$("#bpLocationId").focus(function() {
																															    if (this.value === "") $(this).autocomplete("search");
																															});
																														

																			
																			
																			
}