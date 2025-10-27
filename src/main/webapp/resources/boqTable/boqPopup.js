var slctDel= [];
var rowindx =0;
var rowParams = {};

function openPop(element){	
	var buttonRowIndx = $(element).closest("tr");
    rowindx = (buttonRowIndx[0].rowIndex - 1);    
    //Send input values from Boq table  to popup
	sendValBoqToPopup(rowindx);
    $("#processModal").modal("show");
}// end open popup fct

function sendValBoqToPopup(indxRow){      
	$('#popupProcName').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procName"]').children('input').val());
	$('#popupProcStatus') .val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procProcStatus"]').children('input').val()); 
	$('#popupProcClassName').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procClassName"]').children('input').val());
	$('#popupProcCronExpr').val($("#boqTable >tbody").find("tr").eq(indxRow).find('td[name="procCronExpr"]').children('input').val());							 
	var element = document.getElementById("popupNb");
    	element.innerHTML = "Process # " +(indxRow+1);
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
		$('table#boqTable tr:eq('+(newRowIndx+1)+') td:nth-child(1) input').focus();
	}
	if (position =="above"){
		$("#boqTable > tbody tr").eq(rowindx).before(markup);			
		newRowIndx =  rowindx;
    	//boqAutocomplete(newRowIndx);
		$('table#boqTable tr:eq('+(newRowIndx+1)+') td:nth-child(1) input').focus();
	}
		//rowInputListener((newRowIndx+1));
} // end add new row

function htmlBOQRowInsertion(rowParams){
	//name, model, partnum, barcode
	var markup = "<tr><td><input type='checkbox' name='record'><span class='dotStatus'></span><button type='button' name='popUpMenu' href = '#' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
				+"<td name='procName'><input name='procName' type='text' value='"+ rowParams.name +"' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
            	+"<td name='procStatus'>"
    			+"<input name='procStatus' type='text' value='"+ rowParams.status +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
    			+"<td name='className'>"
    	     	+"<input name='className' type='text' value='"+ rowParams.className +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
 				+"<td name='cronExpr'>"
				+"<input name='cronExpr' type='text' value='"+  rowParams.cronExpr +"'style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				+"<td name='procCalend' style='text-align:center;'><button type='button' name='popUpMenu' href = '#' onclick='openPop(this)' class='btn btn-default' style='margin:0;'><i class='fas fa-desktop'></i></button></td></tr>"; 
	return markup;
}

$(function(){
	// Resize and drag the popup
	$('.modal-content').resizable({
		handles: "e" ,
	});
	 
	$('.modal-dialog').draggable({
		handle: ".modal-header, .modal-footer"
	});
	   
	$('#processModal').on('show.bs.modal', function() {
		$(this).find('.modal-body').css({
			'max-height': '100%',
		});
	});
	 
	//Reset the popup to original size after resizing 
	$('#processModal').on('hidden.bs.modal', function() {
		$(this).find('.modal-content').css({'width': '', 'height': ''});
	})
	 
	//Reset popup position after it has been dragged and closed
	$('body').on('hidden.bs.modal', function() {
		$('.modal-dialog').css({'top': '', 'left':''});
	})
			      
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