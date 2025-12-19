var slctDel= [];
var rowindx =0;
var rowParams = {};
var fpInput; // It is used for selecting flatpickr input field element in the boq table or in the popup modal, the flatpickr is calendar.
var newValue; // It is used to get the new value of the flatpickr input field in the boq table or popup modal.

function openPop(element){	
	var buttonRowIndx = $(element).closest("tr");
    rowindx = (buttonRowIndx[0].rowIndex - 1);    
    //Send input values from Boq table  to popup
	console.log("openPopUp");
	sendValBoqToPopup(rowindx);
    $("#popupModal").modal("show");
}// end open popup fct

function sendValBoqToPopup(indxRow){
	$('#popupProcName').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procName"]').children('input').val());
	$('#popupProcStatus').prop('checked', $("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').prop('checked'));
	$('#popupProcStatus').next('label').text($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').next('label').text()); 
	$('#popupProcClassName').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procClassName"]').children('input').val());	
	$('#popupProcCronExpr').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procCronExpr"]').children('input').val());
	
	initPopupCron($('#popupProcCronExpr').val());

	newValue = $("#boqTable > tbody").find("tr").eq(indxRow).find('td[name="procStartDateTime"] input.proc-start-time').val();
	fpInput= $('#popupProcStartTime');

	// Update the input value
	fpInput.val(newValue);

	// Update Flatpickr internal date so the calendar opens correctly
	if (fpInput[0]._flatpickr) {
	    fpInput[0]._flatpickr.setDate(newValue, false); // false will not triggers onChange but true triggers onChange
	}

		
	var element = document.getElementById("popupNb");
    	element.innerHTML = "Process # " +(indxRow+1);
}

function sendValPopupToBoq(indxRow){	
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procName"]').children('input').val($('#popupProcName').val());	
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').prop('checked',$('#popupProcStatus').prop('checked'));
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').next('label').text($('#popupProcStatus').next('label').text());		
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procClassName"]').children('input').val($('#popupProcClassName').val());
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procCronExpr"]').children('input').val($('#popupProcCronExpr').val());
		
	fpInput = $("#boqTable > tbody").find("tr").eq(indxRow).find('td[name="procStartDateTime"] input.proc-start-time');
	newValue = $('#popupProcStartTime').val();

	// Update the input value
	fpInput.val(newValue);

	// Update Flatpickr internal date so the calendar opens correctly
	if (fpInput[0]._flatpickr) {
	    fpInput[0]._flatpickr.setDate(newValue, false); // false will not triggers onChange while true triggers onChange
	}
	
/*	
	var element = document.getElementById("popupNb");
    	element.innerHTML = "Process # " +(indxRow+1); */
}


function initPopupCron(cronVal) {
	let initializing = true; // flag to ignore first automatic set
	console.log("initPopupCron");
    $('#popupProcCronPicker').empty().jqCron({
        enabled_minute: true,
        multiple_dom: true,
        multiple_month: true,
        multiple_mins: true,
        multiple_dow: true,
        default_value: cronVal || '0 0 * * *',
        bind_to: $('#popupProcCronExpr'),
        bind_method: {
            set: function($el, val) { 
				// Always set the value
				const oldVal = $el.val();
				console.log("oldVal is ", oldVal);
				console.log("newVal is ", val);
				$el.val(val);

				// Ignore automatic initialization
				if (initializing) {
					return;
				}
				else {
					$("#formStatus").text("Not Saved");
					$('.dot').css({ "background-color": "orange" });
				}

/*
				// Mark as Not Saved only if value actually changed
				if (oldVal !== val) {
					console.log("New value");
				    $("#formStatus").text("Not Saved");
				    $('.dot').css({ "background-color": "orange" });
				} 
*/				
				//$el.val(val); 
			},
            get: function($el) { return $el.val(); }
        },
        no_reset_button: false
    });
	initializing = false;
}

function popupRunProc () {
	console.log("row index is " , rowindx);
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContextPath()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')	
	let procID = $("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procID"]').children('input').val();
	console.log("row procID is " , procID);
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url : cx+"/CommScopeRunProc",
		data : {
			"procID" : $("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procID"]').children('input').val(),
		    "docStatus": $("#docStatus").val(), //'${docStatus}',
			"procStatus" : $("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procStatus"] input[type="checkbox"]').next('label').text(),
			"procName" :  $("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procName"]').children('input').val(),
			"procClassName" : $("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procClassName"]').children('input').val(),
			"procCronExpr" : $("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procCronExpr"]').children('input').val(),
			"linkName" : "CommScope"
		},
		success : function(data) {
			console.log("The result is", data.Result);
			$("#loading").remove();
			alert(' The process successfully executed.');
		},
		error : function(error) {
			console.log("Error happened while executing the process, there is error  error is " + error);
		}
	});
}


function runProc (element) {
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContextPath()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')	
	let procID = $(element).closest("tr").find('td[name="procID"] input').val();
	console.log("row procID is " , procID);
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url : cx+"/CommScopeRunProc",
		data : {
			"procID" : $(element).closest("tr").find('td[name="procID"] input').val(),
		    "docStatus": $("#docStatus").val(), //'${docStatus}',			
			"procStatus" : $(element).closest("tr").find('td[name="procStatus"] input[type="checkbox"]').next('label').text(),
			"procName" : $(element).closest("tr").find('td[name="procName"] input').val(),
			"procClassName" : $(element).closest("tr").find('td[name="procClassName"] input').val(),
			"procCronExpr" : $(element).closest("tr").find('td[name="procCronExpr"] input').val(),
			"linkName" : "CommScope"
		},
		success : function(data) {
			console.log("The result is", data.Result);
			$("#loading").remove();
			alert(' The process successfully executed.');			
		},
		error : function(error) {
			console.log("Error happened while executing the process, there is error  error is " + error);
		}
	});
}


function addNewRow(position){
	rowParams = {"name" : "", "status" : 0, "className" : "", "startDateTime": "", "cronExpr": "", "procID" : 0}; 	
	var markup = htmlBOQRowInsertion(rowParams);
	if (position == "next"){
		$("#boqTable > tbody").append(markup);			
		newRowIndx =  parseInt($("#boqTable >tbody tr").length-1);
      	//boqAutocomplete(newRowIndx);
		$('table#boqTable tr:eq('+(newRowIndx+1)+') td:nth-child(2) input').focus();
	}
	if (position =="below"){
		$("#boqTable > tbody tr").eq(rowindx).after(markup);
		newRowIndx = parseInt(rowindx+1);
		//belowRowCount =  $("#bisotab >tbody tr").length;
      	//boqAutocomplete(newRowIndx);
		$('table#boqTable tr:eq('+(newRowIndx+1)+') td:nth-child(2) input').focus();
	}
	if (position =="above"){
		$("#boqTable > tbody tr").eq(rowindx).before(markup);			
		newRowIndx =  rowindx;
    	//boqAutocomplete(newRowIndx);
		$('table#boqTable tr:eq('+(newRowIndx+1)+') td:nth-child(2) input').focus();
	}
		initBOQFlatpickr(); // 🔹 initialize Flatpickr for the new row	
		updateContainerHeight();
		
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
				
		//rowInputListener((newRowIndx+1));
} // end add new row

function handleSwitchClick(el) {
	const isChecked = el.checked;
	const isPopup = el.id && el.id.startsWith("popup");

	// Update clicked switch label (plain DOM)
	const label = el.nextElementSibling;
	label.textContent = isChecked ? "Enabled" : "Disabled"; // We used .textContent because we used e1.nextElementSibling which return plain DOM element

	if (isPopup) {
		// Update corresponding row label (jQuery)
		const $rowLabel = $("#boqTable >tbody") // By using $rowLabel, it returns jQuery Object, so we can use .text instead of .textContent
		.find("tr").eq(rowindx)
		.find('td[name="procStatus"] input[type="checkbox"]')
		.next('label');
		$rowLabel.text(isChecked ? "Enabled" : "Disabled");
	}
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});  
}

function htmlBOQRowInsertion(rowParams){
	//name, model, partnum, barcode
	
	function generateSwitchId() {
		return "switch_" + Date.now() + "_" + Math.floor(Math.random() * 100000);
	}
				
	var switchId = generateSwitchId();
	var statusSwitch = "<div class='custom-control custom-switch'><input name='procStatus' type='checkbox' class='custom-control-input' id='" + switchId + "'"
					   + (rowParams.status === "Enabled" ? " checked" : "") + " onclick='handleSwitchClick(this)'>"
					   +"<label class='custom-control-label' for='" + switchId + "'>" 
					   + (rowParams.status === "Enabled" ? "Enabled" : "Disabled") + "</label></div>";
					  
	var markup = "<tr><td><input type='checkbox' name='record'><span class='dotStatus'></span><button type='button' name='popUpMenu' href = '#' onclick='openPop(this)' class='btn btn-default'><i class='fas fa-desktop'></i></button></td>"
				+"<td name='procName'><input name='procName' type='text' value='"+ rowParams.name +"' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
            	+"<td name='procStatus'>"
				+statusSwitch+"</td>"				
    			//+"<input name='procStatus' type='text' value='"+ rowParams.status +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"												
    			+"<td name='procClassName'>"
    	     	+"<input name='procClassName' type='text' value='"+ rowParams.className +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
				+"<td name='procStartDateTime'>"
				+"<div class='input-group'>"
				+"<input type='text' name='procStartDateTime' autocomplete='off' value='"+ (rowParams.startDateTime || "") +"'class='form-control proc-start-time' placeholder='Select Start Date/Time' style='width:200px;' />"
				+"<div class='input-group-append'>"
				+"<button class='btn btn-outline-secondary calendar-btn' type='button'><i class='fa fa-calendar'></i></button>"
				+"</div></div></td>"				
 				+"<td name='procCronExpr'>"
				+"<input name='procCronExpr' type='text' value='"+  rowParams.cronExpr +"'style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				+"<td name='procCalend' style='text-align:center;'><button type='button' name='popUpMenu' href = '#' onclick='openPop(this)' class='btn btn-default' style='margin:0;'><i class='fas fa-calendar-alt'></i></button></td>"
				+"<td name='procRunManual' style='text-align:center;'><button type='button' name='procRunManual' href = '#' onclick='runProc(this)' class='btn btn-primary BtnActive' style='margin:0;'>Run Now</button></td>"				
				+"<td name='procID'><input type='text' style='width:200px;' readonly value='" + rowParams.procID + "' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>"; 
	return markup;
}

function initBOQFlatpickr() {
	$("#boqTable .proc-start-time").each(function() {
		// Skip if already initialized
		if (this._flatpickr) return;

		flatpickr(this, {
			enableTime: true,
			dateFormat: "Y-m-d H:i:S",
			enableSeconds: true,
			time_24hr: true,
			allowInput: true,
			minuteIncrement: 1,
			appendTo: document.body
		});
	});
}


// Insert row below fct
 function insertRowBelow(){ 
	addNewRow("below");	
	rowindx++ ;
	var belowIndex = parseInt(rowindx);    	
	sendValBoqToPopup(belowIndex);
	
}// End insertRowBelow fct in popup   

// Insert Row Above fct
function insertRowAbove(){
	addNewRow("above");
 	sendValBoqToPopup(rowindx);
}// End insertRowAbove fct in popup   

 // Delete row fct
function deleteBoqRow() {
	proc_Pk = $("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="porItemId"]').children('input').val();
	if( proc_Pk !=0){
		slctDel.push($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="porItemId"]').children('input').val());
	}
	
	rowindx++;
	$("tr").eq(rowindx).remove();
	
	// Get Nb of rows after delete 
	var rowCount = $("#boqTable >tbody tr").length;
	rowindx--;
	if (rowindx == 0 && rowCount ==0) {
		$("#popupModal").modal("hide");
	}
  
	else if(rowindx >= 0 && rowindx < rowCount) {
		sendValBoqToPopup(rowindx);
	}

    // Show previous record 
	else if (rowindx >= rowCount){
		rowindx--;
		sendValBoqToPopup(rowindx);
	}
	updateContainerHeight();
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

} // End delete fct

// Next Fct in popup
function nextRow(){
	// Get total nb of rows
	var rowCount = $("#boqTable >tbody tr").length;	
	rowindx++ ;
	var nextIndex = parseInt(rowindx);	
		 
	if(rowindx >= 0 && rowindx < rowCount) {
		sendValBoqToPopup(nextIndex);
	}
					 
	// Add new row when rowindx exceed the row count
	else if (rowindx >= rowCount) {
		addNewRow("next");	  
	 	sendValBoqToPopup(nextIndex); 
	}
}// End next fct in popup
 

// Prev fct in popup
function prevRow(){
	if(rowindx > 0) {
		rowindx-- ;
		var PrevIndex = parseInt(rowindx);
		sendValBoqToPopup(PrevIndex);
	}
	// Close popup on row 0 (end of prev fct)
	else if (rowindx == 0) {
		$("#popupModal").modal("hide");
	}
}// end prev fct    

function updateContainerHeight() {
    const table = document.getElementById("boqTable");
    const rows = table.querySelectorAll("tbody tr");
    const rowCount = rows.length;

    if (rowCount === 0) {
        table.style.height = "auto";
        return;
    }

    // Delay height measurement until after the DOM renders
    setTimeout(() => {
        const rowHeight = rows[0].offsetHeight || 50; // fallback in case 0
        const maxRowsVisible = 8;
        const maxHeight = 40 + rowHeight * maxRowsVisible;
        const newHeight = 55 + rowCount * rowHeight;
        table.style.height = (newHeight > maxHeight ? maxHeight : newHeight) + "px";
    }, 0);
}


$(document).on("click", ".calendar-btn", function() {
	var input = $(this).closest(".input-group").find(".proc-start-time")[0];
	if (input && input._flatpickr) {
		input._flatpickr.open();
	}
});

$(function(){	
	//remove selected rows from boq
	$(".delete-row").click(function(){
		var checked="false"; //when no checkbox is checked
		var proc_Pk ="";
		$("#boqTable > tbody").find('input[name="record"]').each(function(){
			if($(this).is(":checked")){
				checked="true"; //when 1 or more checkbox is checked		 
				proc_Pk=$(this).parent().parent().children('td[name="procID"]').children('input').val();
	        	if( proc_Pk !=0) {
	        		slctDel.push(proc_Pk);
	      		}   
				$(this).parents("tr").remove();  
	      	} 
		});
		if(checked=="false"){
			alert(' Select Row(s) to Delete');    			
			return false;
		}
		updateContainerHeight();
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});	// end delete row
	
	
	// Resize and drag the popup
	$('.modal-content').resizable({
		handles: "e" ,
	});
	 
	$('.modal-dialog').draggable({
		handle: ".modal-header, .modal-footer"
	});
	   
	$('#popupModal').on('show.bs.modal', function() {
		$(this).find('.modal-body').css({
			'max-height': '100%',
		});
		$(this).data('oldEnforceFocus', $.fn.modal.Constructor.prototype._enforceFocus);
		$.fn.modal.Constructor.prototype._enforceFocus = function() {};		
	});
	
	// Restore focus enforcement after modal closes
	$('#popupModal').on('hidden.bs.modal', function() {
	    const oldEnforceFocus = $(this).data('oldEnforceFocus');
	    $.fn.modal.Constructor.prototype._enforceFocus = oldEnforceFocus;
	});

	// Initialize Flatpickr ONCE
	const startPicker = flatpickr("#popupProcStartTime", {
	    allowInput: true,
	    enableTime: true,
	    enableSeconds: true,
	    dateFormat: "Y-m-d H:i:S",
		time_24hr: true,
		minuteIncrement: 1,
	    clickOpens: false, // manual open only
/*		
	    onChange: function(selectedDates, dateStr) {
	        $("#formStatus").text("Not Saved");
	        $('.dot').css({"background-color": "orange"});
	    }
*/		
	});

	// Open calendar via button click
	$('#calendarButton').on('click', function() {
	    startPicker.open();
	});

	// Open calendar when input gets focus (e.g., click or tab)
	$('#popupProcStartTime').on('focus', function() {
	    startPicker.open();
	});
	
	$(document).on("click", ".calendar-btn", function(e) { // // $(document) is used because we need to confirm that the event will still work for the DOM that will be created after loading the page, because the event is listening on the document
		e.preventDefault();
		e.stopPropagation();

		var row = $(this).closest("tr");
		var input = row.find(".proc-start-time")[0];

		if (input && input._flatpickr) {
			input._flatpickr.open();
		} else if (input) {
			// In case something removed _flatpickr (rare)
			flatpickr(input, {
				enableTime: true,
				dateFormat: "Y-m-d H:i:S",
				enableSeconds: true,
				time_24hr: true,
				allowInput: true,
				minuteIncrement: 1
			}).open();
		}
	});
			
		 
	//Reset the popup to original size after resizing 
	$('#popupModal').on('hidden.bs.modal', function() {
		$(this).find('.modal-content').css({'width': '', 'height': ''});
	})
	 
	//Reset popup position after it has been dragged and closed
	$('body').on('hidden.bs.modal', function() {
		$('.modal-dialog').css({'top': '', 'left':''});
	})
	
	 //to select all checkbox or unselect them all from top table
	$('body').on('click', '#selectAll', function  () {
		if ($(this).hasClass('allChecked')) {
	    	$('input[type="checkbox"]', '#boqTable').prop('checked', false);
	    } 
	    else {
	    	$('input[type="checkbox"]', '#boqTable').prop('checked', true);
	    }
	    $(this).toggleClass('allChecked');
	})

	
	$("button[name='closeModPartPopup']").on("click", function(){	  
		sendValPopupToBoq(rowindx);
		$("#popupModal").modal("hide");
	}); // end close fct

	$('#popupProcName,#popupProcClassName, #popupProcCronExpr').on('focusout', function() {
		$("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procName"]').children('input').val($('#popupProcName').val());
		$("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procClassName"]').children('input').val($('#popupProcClassName').val());
		$("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procCronExpr"]').children('input').val($('#popupProcCronExpr').val());
	});
	
	$('#popupProcStatus').on('change', function() {		
		$("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procStatus"]').find('input[type="checkbox"]').prop('checked',$('#popupProcStatus').prop('checked'));
		handleSwitchClick(this);
		//handleSwitchClick($("#boqTable >tbody").find("tr").eq(rowindx).find('td[name="procStatus"]').find('input[type="checkbox"]'));
				
	});
			      
	// Minimize and Maximize fct for popup
	$(".modalMinimize").on("click", function(){
		$(".modal-body").slideToggle();
		$(".modal-footer").slideToggle();
	
		//Toggle between minimize/maximize icons
		var iconToChange = $('.icon-to-change');
		if(iconToChange.hasClass('fa-window-restore')) {
			iconToChange.removeClass('fa-window-restore')
			            .addClass('fa-minus')
		}
		else{
	 		iconToChange.addClass('fa-window-restore')
			             .removeClass('fa-minus')
		}    		         
	}); // end minimize/maximize fct

/*
	// jqCron for recurrence
	$('#popupProcCronPicker').jqCron({
		enabled_minute: true,
		multiple_dom: true,
		multiple_month: true,
		multiple_mins: true,
		multiple_dow: true,
		default_value: '0 0 * * *',
		bind_to: $('#popupProcCronExpr'),
		bind_method: {
			set: function($el, val) { $el.val(val); },
			get: function($el) { return $el.val(); }
		},
		no_reset_button: false
	});
*/	
});