var slctDel= [];
var rowindx =0;
var rowParams = {};

function openPop(element){	
	var buttonRowIndx = $(element).closest("tr");
    rowindx = (buttonRowIndx[0].rowIndex - 1);    
    //Send input values from Boq table  to popup
	sendValBoqToPopup(rowindx);
    $("#popupModal").modal("show");
}// end open popup fct

function sendValBoqToPopup(indxRow){
	$('#popupProcName').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procName"]').children('input').val());
	console.log("the status of the switch at indxRow: " ,indxRow + " is: " ,$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').prop('checked'));
	$('#popupProcStatus').prop('checked', $("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').prop('checked'));
	$('#popupProcStatus').next('label').text($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').next('label').text()); 
	$('#popupProcClassName').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procClassName"]').children('input').val());
	$('#popupProcCronExpr').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procCronExpr"]').children('input').val());							 
	var element = document.getElementById("popupNb");
    	element.innerHTML = "Process # " +(indxRow+1);
}

function sendValPopupToBoq(indxRow){	
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procName"]').children('input').val($('#popupProcName').val());	
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').prop('checked',$('#popupProcStatus').prop('checked'));
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procStatus"]').find('input[type="checkbox"]').next('label').text($('#popupProcStatus').next('label').text());		
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procClassName"]').children('input').val($('#popupProcClassName').val());
	$("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procCronExpr"]').children('input').val($('#popupProcCronExpr').val());
/*	
	var element = document.getElementById("popupNb");
    	element.innerHTML = "Process # " +(indxRow+1); */
}


function addNewRow(position){
	rowParams = {"name" : "", "status" : 0, "className" : "", "cronExpr": ""}; 	
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
  console.log("Switch clicked:", el.id, "Checked:", isChecked);
}


function htmlBOQRowInsertion(rowParams){
	//name, model, partnum, barcode

	function generateSwitchId() {
		return "switch_" + Date.now() + "_" + Math.floor(Math.random() * 100000);
	}
				
	var switchId = generateSwitchId();
	var statusSwitch = "<div class='custom-control custom-switch'><input type='checkbox' class='custom-control-input' id='" + switchId + "'"
					   + (rowParams.status === "Enabled" ? " checked" : "") + " onclick='handleSwitchClick(this)'>"
					   +"<label class='custom-control-label' for='" + switchId + "'>" 
					   + (rowParams.status === "Enabled" ? "Enabled" : "Disabled") + "</label></div>";
					  
	var markup = "<tr><td><input type='checkbox' name='record'><span class='dotStatus'></span><button type='button' name='popUpMenu' href = '#' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
				+"<td name='procName'><input name='procName' type='text' value='"+ rowParams.name +"' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
            	+"<td name='procStatus'>"
				+statusSwitch+"</td>"				
    			//+"<input name='procStatus' type='text' value='"+ rowParams.status +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"												
    			+"<td name='procClassName'>"
    	     	+"<input name='procClassName' type='text' value='"+ rowParams.className +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
 				+"<td name='procCronExpr'>"
				+"<input name='procCronExpr' type='text' value='"+  rowParams.cronExpr +"'style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				+"<td name='procCalend' style='text-align:center;'><button type='button' name='popUpMenu' href = '#' onclick='openPop(this)' class='btn btn-default' style='margin:0;'><i class='fas fa-calendar-alt'></i></button></td>"
				+"<td name='procRunManual' style='text-align:center;'><button type='button' name='procRunManual' href = '#' onclick='runProc(this)' class='btn btn-primary BtnActive' style='margin:0;'>Run Now</button></td>"				
				+"<td name='procID'><input type='text' style='width:200px;' readonly value= 0 class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>"; 
	return markup;
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
	console.log("RowIndx" +rowindx);
	rowindx++;
	$("tr").eq(rowindx).remove();
	
	// Get Nb of rows after delete 
	var rowCount = $("#boqTable >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);
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
	// Get the table and container elements
	console.log("Updating table height");
	var table = document.getElementById("boqTable");
	console.log(table.offsetHeight);
  	var tr = document.getElementById("boq_tr");
	var rowCount = $("#boqTable >tbody tr").length;
  	console.log(tr.offsetHeight);
	console.log(rowCount);
  	//console.log(boqArray.length);
	if (rowCount > 8)
		rowCount = 8;
  	table.style.height = (120 + tr.offsetHeight * rowCount) + "px";
  	console.log("new height : "+table.offsetHeight)
}


$(function(){
	
	//remove selected rows from boq
	$(".delete-row").click(function(){	
		slctDelOrd =[];
		var checked="false"; //when no checkbox is checked
		var proc_Pk ="";
		$("#boqTable > tbody").find('input[name="record"]').each(function(){
			if($(this).is(":checked")){
				checked="true"; //when 1 or more checkbox is checked		 
				proc_Pk=$(this).parent().parent().children('td[name="procID"]').children('input').val();
	        	if( proc_Pk !=0) {
	        		slctDelOrd.push(proc_Pk);
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
});