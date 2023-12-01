var rowindx =0;
 var rowcountSerial =0;
 var serialRowIndex=0;
 var slctDelOrd =[];
var allDelSerials=[]; // to store all deleted serial numbers from different popups

 //Open popup fct 
 function openPop(element) {
 	
	var buttonRowIndx = $(element).closest("tr");
	rowindx = (buttonRowIndx[0].rowIndex - 1);
	//console.log("5555" );
	     
	    
	//Send input values from Boq table  to popup
	sendValBoqToPopup(rowindx);
	
	$("#poModal").modal("show");
    AutocompleteOnLoad();

}// end open popup fct
     
function sendValBoqToPopup(indxRow){

	//Send input values from Boq table  to popup
	$('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemCode"]').children('input').val());
	$('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemModel"]').children('input').val()); 
	$('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemPartNo"]').children('input').val());
	$('#popupBarcode').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poBarCode"]').children('input').val());
	$('#popupQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="qty"]').children('input').val());
	$('#popupCat1').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat1"]').children('input').val());
	$('#popupCat2').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat2"]').children('input').val());
	$('#popupCat3').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat3"]').children('input').val());
	$('#popupCat4').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat4"]').children('input').val());
	$('#popupSeq').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poSequ"]').children('input').val());
	$('#popupRate').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rate"]').children('input').val());
	$('#popupTax').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="tax1"]').children('input').val());
	$('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="discountAmount"]').children('input').val());
	$('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="netRate"]').children('input').val());
	$('#popupTotal').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="total"]').children('input').val());
	$('#popupTotalAt').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="totalAt"]').children('input').val());
	$('#popupGrQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grpQty"]').children('input').val());
	$('#popupPrQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prQty"]').children('input').val());
	$('#popupArQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="arQty"]').children('input').val());
	$('#popupCipQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cipQty"]').children('input').val());
	$('#popupFarQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="farQty"]').children('input').val());
								 
	
	// Send hidden concatenated value of serial/Model/PartNum from Boq to Serial table in popup
	var serial;
	serial = JSON.parse($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNo"]').children('input').val());
		$("#serialNoTable > tbody").html(" ");
		if(serial != null){
		var serNum, itmModel, itmPartNo;
		$.each(serial.serialArray, function(i, value) {
		serNum = (serial.serialArray[i].serial_no === null) ? '' : serial.serialArray[i].serial_no;
		itmModel = (serial.serialArray[i].itm_model === null) ? '' : serial.serialArray[i].itm_model;
		itmPartNo = (serial.serialArray[i].itm_partno === null) ? '' : serial.serialArray[i].itm_partno;
		 var itemRowSerial = "<tr>";
			itemRowSerial +=  "<td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
			itemRowSerial +=  "<td name='serialNumber'><input name='serialNumber' type='text' value='" + serNum +"'  style='width:200px;position:relative;left:11px;' class='form-control text-input'/></td>";
			itemRowSerial +=  "<td name='itmModel' style='width:200px'><input name='itmModel'   type='text' value='" + itmModel + "'  style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial +=  "<td name='itemPart' style='width:200px'><input name='itemPart'  type='text' value='" + itmPartNo + "'  style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial +=  "</tr>";
			$("#serialNoTable > tbody").append(itemRowSerial);
		});
		}
		var element = document.getElementById("popupNb");
    	element.innerHTML = "Item # " +(indxRow+1);
  		  
    		
} //end sendValBoqToPopup fct

function sendValPopupToBoq(indxRow){

// Send input values from popup to boq table
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemCode"]').children('input').val($('#popupItemCode').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemPartNo"]').children('input').val($('#popupItemPartno').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poBarCode"]').children('input').val($('#popupBarcode').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="qty"]').children('input').val($('#popupQty').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat1"]').children('input').val($('#popupCat1').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat2"]').children('input').val($('#popupCat2').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat3"]').children('input').val($('#popupCat3').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poCat4"]').children('input').val($('#popupCat4').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poSequ"]').children('input').val($('#popupSeq').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rate"]').children('input').val($('#popupRate').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="tax1"]').children('input').val($('#popupTax').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="discountAmount"]').children('input').val($('#popupDiscountAmount').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="netRate"]').children('input').val($('#popupNetRate').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="total"]').children('input').val($('#popupTotal').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="totalAt"]').children('input').val($('#popupTotalAt').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grpQty"]').children('input').val($('#popupGrQty').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prQty"]').children('input').val($('#popupPrQty').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="arQty"]').children('input').val($('#popupArQty').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cipQty"]').children('input').val($('#popupCipQty').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="farQty"]').children('input').val($('#popupFarQty').val());
	
 // Send concatenated Serial Table rows to boq table
	 	 var Data = {};
	 	 var serial_no="";
	 	 var itm_model ="";
	 	 var itm_partno = "";
	 	 var indx;
	 	 Data.serialArray=[];
	 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){
		indx = $(this).parents("tr").index()
 // Remove the row where SerialNumber is empty
	var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
		if (serNum == '') {
			$(this).parents("tr").remove();
         }
	     else {
	        var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	        var itemMod = $(this).parent().parent().children('td[name="itmModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
	    	

			    Data.serialArray.push({serial_no:serNum, itm_model:itemMod, itm_partno:itemPartnum});

	      }
	  	});
	    	$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNo"]').children('input')[0].value = JSON.stringify(Data);			
}

function buildingSavedBOQ(itmRow){
 	var barcode =  itmRow.poBarCode;
	if(barcode == null){
	barcode = "";
	}
	else{
	barcode =  itmRow.poBarCode;
	}
	var itemModel =  itmRow.itemModel;
   		if(itemModel == null)
   		itemModel = "";
   		else
   		itemModel =  itmRow.itemModel;


   	var itemPartNumber = itmRow.itemPartNumber;
	if(itemPartNumber == null)
		itemPartNumber = "";
	else
		itemPartNumber = itmRow.itemPartNumber;
 	var dotStatus = (itmRow.poItemStatus);
	var span;
	         				 
      if(dotStatus == "1")
     {
     
	
	   span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: chartreuse;'></span>";
     }
else 
     {
	 
   
	 span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: orange;'></span>";
	    }

	var serialArrays = [];
  if (itmRow.serial_obj != null) {
  serialArrays.push(itmRow.serial_obj);
  }
  else{
  serialArrays.push(null);
  }     
       
    var itemRow = "<tr>";
    itemRow= itemRow + "<td><input type='checkbox' name='record'>"+span+"<button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	itemRow =itemRow + "<td name='itemCode'><input type='text' name='itmCode' value='" + itmRow.itemCode +":"+ itmRow.itemName + "' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>";
	itemRow =itemRow + "<td name='itemModel'><input name='itmModel' type='text' value='" + itemModel + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>";
	itemRow =itemRow + "<td name='itemPartNo'><input name='itmPartNo' type='text' value='" + itemPartNumber + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>";		 			
	itemRow =itemRow + "<td hidden name='poBarCode'><input type='text' style='width:200px;' hidden value='" + barcode +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='qty'><input name='qty' type='text' style='width:200px;' value='" + itmRow.qty +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='rate'><input name='rate' type='text' style='width:200px;' value='" + itmRow.rate +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='discountAmount'><input  name='discountAmount' type='text' style='width:200px;' value='" + itmRow.discountAmount +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='tax1'><input name='tax1' type='text' style='width:200px;' value='" + itmRow.tax1 +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
    itemRow =itemRow + "<td name='netRate'><input type='text' style='width:200px;' value='" + itmRow.netRate +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
    itemRow =itemRow + "<td name='total'><input type='text' style='width:200px;' value='" + itmRow.total +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='totalAt'><input type='text' style='width:200px;' value='"  + itmRow.totalAt +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='grpQty'><input type='text' style='width:200px;' value='" + itmRow.grQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='prQty'><input type='text' style='width:200px;' value='" + itmRow.prQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='arQty'><input type='text' style='width:200px;' value='" + itmRow.arQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='cipQty'><input type='text' style='width:200px;' value='" + itmRow.cipQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='farQty'><input type='text' style='width:200px;' value='" + itmRow.farQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='porItemId'><input type='text' style='width:200px;' value='" + itmRow.pordItemId +"'readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'><input name='poItemStatus' type='text' value='" + (itmRow.poItemStatus !== null ? itmRow.poItemStatus : "0") + "'hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
	itemRow =itemRow + "<td name='serialNo'><input type='text'  style='width:200px;' value=" + serialArrays[0] +" hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";		
	itemRow =itemRow + "</tr>";
    $("#bisotab > tbody").append(itemRow);

    //var loadRowCount =  $("#bisotab >tbody tr").length;
    //boqAutocomplete(loadRowCount);
		        
}  


function htmlBOQRowInsertion(name, model, partnum, barcode){
	
	var markup = "<tr><td><input type='checkbox' name='record'><span class='dotStatus' id='dotStatus'></span><button type='button'  id ='popUpMenu' name='popUpMenu' href = '#' onclick='openPop(this)'  class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td><td name='itemCode'>"
    			+"<input name='itmCode' type='text' value='"+ name +"' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
            	+"<td name='itemModel'>"
    			+"<input name='itmModel' type='text' value='"+ model +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
    			+"<td name='itemPartNo'>"
    	     	+"<input name='itmPartNo' type='text' value='"+ partnum +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
    	     	+"<td hidden name='poBarCode'>"
    			+"<input name='barcode' type='text' value='"+ barcode +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
 				+"<td name='qty'><input name='quantity' style='width:200px;' type='text' value= 1 class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				+"<td name='rate'><input name='rate2' style='width:200px;' type='text' value= 0 class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='discountAmount'><input name='discountAmount2'style='width:200px;' type='text'value= 0 class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='tax1'><input name='tax2' style='width:200px;' type='text' value='0' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='netRate'><input name='netRate2' style='width:200px;' type='text' readonly value= 0 class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='total'><input name='total2' style='width:200px;' type='text' readonly value= 0 class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='totalAt'><input name='totalAt2' style='width:200px;' type='text' readonly value= 0 class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='grpQty'><input name='grpQty2' style='width:200px;' type='text' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='prQty'><input name='prQty2' style='width:200px;' type='text' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='arQty'><input name='arQty2' style='width:200px;' type='text' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='cipQty'><input name='cipQty2' style='width:200px;' type='text' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='farQty'><input name='farQty2' style='width:200px;' type='text' readonly value='0' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td name='porItemId'><input type='text' style='width:200px;' readonly value= 0 class='ui-widget ui-widget-content ui-corner-all form-control text-input'><input type='text' name='poItemStatus' hidden value= '0' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    			+"<td style='visibility:hidden;width:0px;border-width: 0px;' name='serialNo'><input type='text' style='width:200px;' hidden  value ='null' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>"; 
return markup;	   
				
}
function addNewRow(position){ 
	    
	                
var markup = htmlBOQRowInsertion('', '', '', '');

		if (position == "next"){
			$("#bisotab > tbody").append(markup);
			
			newRowIndx =  parseInt($("#bisotab >tbody tr").length-1);
      		boqAutocomplete(newRowIndx);
			$('table#bisotab tr:eq('+(newRowIndx+1)+') td:nth-child(2) input').focus();
     	}
     	if (position =="below"){
			$("#bisotab > tbody tr").eq(rowindx).after(markup);
			newRowIndx = parseInt(rowindx+1);
			//belowRowCount =  $("#bisotab >tbody tr").length;
      		boqAutocomplete(newRowIndx);
			$('table#bisotab tr:eq('+(newRowIndx+1)+') td:nth-child(1) input').focus();
		}
		if (position =="above"){
			$("#bisotab > tbody tr").eq(rowindx).before(markup);
			
			newRowIndx =  rowindx;
      		boqAutocomplete(newRowIndx);
			$('table#bisotab tr:eq('+(newRowIndx+1)+') td:nth-child(1) input').focus();
		}
		
		//var myDiv = document.getElementById("bisotab");
    	//myDiv.scrollTop = myDiv.scrollHeight
    						

			rowInputListener((newRowIndx+1));
    	
     	    
} // end add new row
function checkItemCodeBoqRow(code){
$("#bisotab > tbody").find('td[name="itemCode"]').each(function(){	
								 				var index =  $(this).parent().closest("tr").index();
								 			var itmCode = 	($("#bisotab >tbody").find("tr").eq(index).find('td[name="itemCode"]').children('input').val().split(":"))[0];
								 			if (itmCode == code){
									var qty = $("#bisotab >tbody").find("tr").eq(index).find('td[name="qty"]').children('input').val();
									qty++;
								$("#bisotab >tbody").find("tr").eq(index).find('td[name="qty"]').children('input').val(qty);
									//$('#barcode').val("");

							}
							 });
}
/////////////////
 function getAllItemCodeFromBarCode()
 {
 	ItemCodeArray = [];
 	var ItemCodeValues = "";
 	$("#bisotab > tbody").find('td[name="itemCode"]').each(function(){
 	
 		if($(this).children('input').val() == "")
 			ItemCodeValues = "empty";
 		else
 			ItemCodeValues = ($(this).children('input').val()).split(":");
 		ItemCodeArray.push(ItemCodeValues[0]);
 	});
 	return ItemCodeArray;   
 }

 function checkifItemCodeExist(itemCode)
 {
	var exist = "";
	$("#bisotab > tbody").find('td[name="itemCode"]').each(function(){
		
		ItemCodeValue = ($(this).children('input').val()).split(":");
		ItemCodeValue = ItemCodeValue[0];
		

		if(itemCode == ItemCodeValue){
			exist = "true";
			return false;
	   }
		else{
			exist = "false";
   		}
	});
	return exist;
 }
 
 ////////////////
 
 		//checking some validations before saving on the action buttons
	function checkedDataOnAction(){

		val1 = Date.parse($("#ordorderdate").val());
	  
	    if (isNaN(val1) == true) {
	         alert(' Ordered Date is not valid');
	         return false;
	          
	       }
	      
	    val1 = Date.parse($("#orddeliverydate").val());
	   
	    if (isNaN(val1) == true) {
	         alert(' Delivered Date is not valid');
	         return false;
	         
	       }
		 
		  if ($("#ordtotamnt").val() == '')  { ordtotamnt.value = 0;  }
		  if ($("#orddiscamnt").val() == '')  { orddiscamnt.value = 0; }
		  if ($("#orddiscpercent").val() == '') { orddiscpercent.value = 0; }
		  if ($("#ordpaidamnt").val() == '') { ordpaidamnt.value = 0; }
		  if ($("#ordoutstand").val() == '') { ordoutstand.value = 0; }
		  if ($("#ordtotqty").val() == '') { ordtotqty.value = 0; }
		  if ($("#ordNetTotal").val() == '') { ordNetTotal.value = 0; }
		  
		  
		 // validate Total Amount to be number 
		 if (($. isNumeric( $("#ordtotamnt").val()))== false) {
		 alert('Total Amount is  not Numeric');
		 return false;}
		 
		  // validate Discount Amount to be number
		 if (($. isNumeric( $("#orddiscamnt").val()))== false) {
		 alert('Discount Amount is  not Numeric');
		 return false;}
		 
		  // validate Discount pecent to be number
		 if (($. isNumeric( $("#orddiscpercent").val()))== false) {
		 alert('Discount pecent is  not Numeric');
		 return false;}
		 
		  // validate paid Amount to be number
		 if (($. isNumeric( $("#ordpaidamnt").val()))== false) {
		 alert('paid Amount is  not Numeric');
		 return false;}
		 
		  // validate Outstanding to be number
		 if (($. isNumeric( $("#ordoutstand").val()))== false) {
		 alert('Outstanding is  not Numeric');
		 return false;}
		 
		  // validate Qty to be number
		 if (($. isNumeric( $("#ordtotqty").val()))== false) {
		 alert('Qty is  not Numeric');
		 return false;}
		 
		 // validate netTotal to be number
		 if (($. isNumeric( $("#ordNetTotal").val()))== false) {
		 alert('Net Total is  not Numeric');
		 return false;}
		 
	 
	}
	
	///////////////////////////////////////////
	
        
	///////////////////////////////////////////
	function rowInputListener(Indx){
		 // select cell per row and col
		$('#bisotab tr:eq('+Indx+') td input').change(function() {
				
				var cell = $(this).val();
				var column_name = $(this).parent().attr('name');
				
              if ((column_name == 'qty') ) {
                    // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}
						 else {
							 
							 calculateParam.bind(this)();
							 
						 }
				}
				else if ((column_name == 'rate') ) {
                // validate Rate
					 if (($. isNumeric(cell ))== false) {
					 alert('Rate is  not Numeric');
					 this.focus();
					 return false;} 
					 else {
						 calculateParam.bind(this)();
	 
					 }
          }
				else if ((column_name == 'discountAmount') ) {
                // validate Discount
					 if (($. isNumeric(cell ))== false) {
					 alert('Discount is  not Numeric');
					 this.focus();
					 return false;}
					 else {
						 calculateParam.bind(this)();

					 }
          }
				else if ((column_name == 'tax1') ) {
                // validate Discount
					 if (($. isNumeric(cell ))== false) {
					 alert('Tax is  not Numeric');
					 this.focus();
					 return false;}
					 else {
						 calculateParam.bind(this)();
					 }
          }
          
         
			});
	}
	function boqInputListener(){
		   // select cell per row and col
		$('#bisotab tr td input').change(function() {
				
				var cell = $(this).val();
				var column_name = $(this).parent().attr('name');
				
              if ((column_name == 'qty') ) {
                    // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}
						 else {
							 
							 calculateParam.bind(this)();
							 
						 }
				}
				else if ((column_name == 'rate') ) {
                // validate Rate
					 if (($. isNumeric(cell ))== false) {
					 alert('Rate is  not Numeric');
					 this.focus();
					 return false;} 
					 else {
						 calculateParam.bind(this)();
	 
					 }
          }
				else if ((column_name == 'discountAmount') ) {
                // validate Discount
					 if (($. isNumeric(cell ))== false) {
					 alert('Discount is  not Numeric');
					 this.focus();
					 return false;}
					 else {
						 calculateParam.bind(this)();

					 }
          }
				else if ((column_name == 'tax1') ) {
                // validate Discount
					 if (($. isNumeric(cell ))== false) {
					 alert('Tax is  not Numeric');
					 this.focus();
					 return false;}
					 else {
						 calculateParam.bind(this)();
					 }
          }
          
         
			});
	}
///////////////////////////////////////////////
function calculateParam(){
	var netrate= parseFloat($(this).parent().parent().children('td[name="rate"]').children('input').val()) - parseFloat($(this).parent().parent().children('td[name="discountAmount"]').children('input').val());
	var tax1 = ((parseFloat($(this).parent().parent().children('td[name="tax1"]').children('input').val()) * netrate)/100);
	var amount = parseFloat($(this).parent().parent().children('td[name="qty"]').children('input').val()) * (netrate);
	var amountAT = parseFloat($(this).parent().parent().children('td[name="qty"]').children('input').val()) * (netrate+tax1);
	
	$(this).parent().parent().children('td[name="netRate"]').children('input').val(netrate);
	$(this).parent().parent().children('td[name="total"]').children('input').val(amount);
	$(this).parent().parent().children('td[name="totalAt"]').children('input').val(amountAT);
	getSumQty_totalAT();
}

function getSumQty_totalAT (){
	var sumQntity = 0;
	var sumtotAT = 0;
	var sumDiscount = 0;

	if (ordNetTotal.value == ''){
		ordNetTotal.value=0;
	}
	if (orddiscamnt.value == ''){
		orddiscamnt.value=0;
		orddiscpercent.value=0;
	}
	if (ordoutstand.value == ''){
		ordoutstand.value=0;
	}
	if (ordpaidamnt.value == ''){
		ordpaidamnt.value=0;
	}
	
	$("#bisotab > tbody > tr").each(function(i, row){
		sumQntity = sumQntity + parseFloat($(this).children('td[name="qty"]').children('input').val());
		sumtotAT = sumtotAT + parseFloat($(this).children('td[name="totalAt"]').children('input').val());
		sumDiscount = sumDiscount + parseFloat($(this).children('td[name="discountAmount"]').children('input').val());	

	});
	
	$('#ordtotqty').val(sumQntity);
	$('#ordtotamnt').val(sumtotAT);
	//$('#orddiscamnt').val(sumDiscount);

    updateamounts();
                        
}

function updateamounts(){
	//ordtotamnt.value = ordtotamnt.value + sumTotal;
	ordNetTotal.value= parseFloat(ordtotamnt.value) - parseFloat(orddiscamnt.value);
	ordoutstand.value=parseFloat(ordNetTotal.value) - parseFloat(ordpaidamnt.value);
}

 
  
//boqAutocomplete fct
function boqAutocomplete(rowCnt){

	var ctx=getContextPath();
	//var tableID=tableIndx;
	
	//var columns = [{name: 'Item Code', minWidth: '150px'}];	
	
	//ITEM CODE autocomplete
	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmCode"]').autocomplete({
	source: function(request, response,event, ui) {
		
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
 			url: ctx+'/getItemCode',
			data: {
				requestValue : request.term,
				barcode : $("#barcode").val()
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.ListItemDetails);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');  // 0 is itemCode,  1 is itemName, 2 is model, 3 partNo , 4 Barcode
			$(this).parents("tr").find('input[name ="itmModel"]').val(ui.item[2]);
			$(this).parents("tr").find('input[name ="itmPartNo"]').val(ui.item[3]);
					 
			return false;
		}
		}).autocomplete("instance")._renderItem= function(ul, item) {
		
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
                
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>";
			if(item[2] != '-')
				appendString += ","+item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";
			if(item[4] != '-')
				appendString += ","+item[4];

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
	  	};
	  	
	  	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmCode"]').focus(function(){
		  $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
				
	  		if (this.value == ""){
	  			$(this).autocomplete("search");
			}
		}); // end ITEM CODE autocomplete
		
	//ITEM MODEL AUTOCOMPLETE
	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmModel"]').autocomplete({
	//columns: columns,
	source: function(request, response) {
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:  ctx+'/getModel',
		data: {
			requestValue : request.term,
			barcode : $("#barcode").val(),
		 },
		 dataType: "json",
		 success: function (data) {
		 	if (data != null) {
		 		response(data.ListModels);
		 	}
		 },
		 error: function(result) {
		 	alert("Error");
		 }
	});
	}, minLength:0, maxShowItems: 4, scroll:true,
		 select: function(event, ui) {
				this.value = (ui.item ? ui.item[0] : '');
				$(this).parents("tr").find('input[name ="itmCode"]').val(ui.item[1]+ ":" + ui.item[2]);
				$(this).parents("tr").find('input[name ="itmPartNo"]').val(ui.item[3]);
						
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
					
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>"+"," +
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";
			if(item[4] != '-')
				appendString += ","+item[4];

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
		};
		
		$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmModel"]').focus(function(){
		 
		 $("#formStatus").text("Not Saved");
		 $('.dot').css({"background-color" : "orange"});
				
			if (this.value == ""){
				$(this).autocomplete("search");
			}
		}); // end ITEM MODEL autocomplete
		
	//ITEM PARTNO autocomplete
	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmPartNo"]').autocomplete({
	source: function(request, response) {
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:  ctx+'/getPartNo',
		data: {
        	requestValue : request.term,
        	barcode : $("#barcode").val()
        	
		 },
		 dataType: "json",
		 success: function (data) {
		 	if (data != null) {
		 		response(data.ListPartNos);
		 	}
		 },
		 error: function(result) {
		 	alert("Error");
		 }
		 
	 });
	 }, minLength:0, maxShowItems: 40, scroll:true,
	 select: function(event, ui) {
	 		this.value = (ui.item ? ui.item[0] : '');
				$(this).parents("tr").find('input[name ="itmCode"]').val(ui.item[1]+ ":" + ui.item[2]);
				$(this).parents("tr").find('input[name ="itmModel"]').val(ui.item[3]);
						
	return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
	
		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
		item[0] + "</span><br><span class='desc'>" +
		item[1] + "</span><span class='desc'>" +","+ 
		item[2] + "</span><span class='desc'>";
		if(item[3] != '-')
			appendString += ","+item[3] + "</span><span class='desc'>";
		if(item[4] != '-')
			appendString += ","+item[4];

		appendString += "</span></div>";
		return $("<li class='each'>").append(appendString).appendTo(ul);
		
	};
	
	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmPartNo"]').focus(function(){
		
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	}); // end ITEM PARTNO autocomplete
	/*
	$('#'+tableID+' > tbody > tr').eq(rowCnt).find('input[name ="qty"]').focus(function(){
	
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	});
	$('#'+tableID+' > tbody > tr').eq(rowCnt).find('input[name ="rate"]').focus(function(){
	
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	});
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt).find('input[name ="discountAmount"]').focus(function(){
	
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	});
	$('#'+tableID+' > tbody > tr').eq(rowCnt).find('input[name ="tax1"]').focus(function(){
	
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	});
	*/
	
} // end boqAutocomplete fct


  
// Next Fct in popup
function nextRow(){
	// Get total nb of rows
	var rowCount = $("#bisotab >tbody tr").length;
	
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
 
 
 function AutocompleteOnLoad() {	 
  var ctx = getContextPath();
	$("input[name='itemPart']").each(function(i, el) {
		$(el).autocomplete({
		source: function(request, response, event, ui) {
	$.ajax({ 
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getPartNo',
		data: {
			requestValue : request.term
	},
		dataType: "json",
		success: function (data) {
				                 
		if (data != null) {
			response(data.ListPartNos);
		}
		},
		error: function(result) {
			console.log(222);
			                 
		}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("popupItemPartno").value= ui.item[0] ;
			document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
			document.getElementById("popupItemModel").value= ui.item[3] ;
			 $(this).parents("tr").find('input[name ="itmModel"]').val(ui.item[3]);
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>" +","+ 
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";
			if(item[4] != '-')
				appendString += ","+item[4];

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
	     };
		$("input[name='itemPart']").focus(function(){
		
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
			
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
	
		});
	 });
	$("input[name='itmModel']").each(function(i, el) {
				$(el).autocomplete({
	
	source: function(request, response, event, ui) {
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:ctx+'/getModel',
		data: {
			requestValue : request.term
		},
		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.ListModels);
			}
		},
		error: function(result) {
			console.log(222);
		}
	});
	}, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0]  : '');
		document.getElementById("popupItemModel").value= ui.item[0] ;
		document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
		document.getElementById("popupItemPartno").value= ui.item[3] ;
		$(this).parents("tr").find('input[name ="itemPart"]').val(ui.item[3]);
		return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
					
		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
					
		item[0] + "</span><br><span class='desc'>" +
		item[1] + "</span><span class='desc'>"+"," +
		item[2] + "</span><span class='desc'>";
		if(item[3] != '-')
			appendString += ","+item[3] + "</span><span class='desc'>";
		if(item[4] != '-')
			appendString += ","+item[4];

		appendString += "</span></div>";
		return $("<li class='each'>").append(appendString).appendTo(ul);
	  		
	};
		$("input[name='itmModel']").focus(function(){
		
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
	
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

	console.log("RowIndx" +rowindx);
	rowindx++;
	$("tr").eq(rowindx).remove();
	
	// Get Nb of rows after delete 
	var rowCount = $("#bisotab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);
	   	 
	 rowindx--;
	 
	 if (rowindx == 0 && rowCount ==0) {
	    var sumQty=0;
	    var sumTotalAt=0;
		$("#poModal").modal("hide");
		$("#bisotab > tbody > tr").each(function(i, row){
 
 		sumQty = sumQty + parseFloat($(this).children('td[name="qty"]').children('input').val());
		//console.log("sumQty is:"+sumQty);
		sumTotalAt = sumTotalAt + parseFloat($(this).children('td[name="totalAt"]').children('input').val());
		//console.log("sumTotalAt is:"+sumTotalAt);
	
        });
		$('#ordtotqty').val(sumQty);
		$('#ordtotamnt').val(sumTotalAt);
		
		ordNetTotal.value= parseFloat(ordtotamnt.value) - parseFloat(orddiscamnt.value);
	    ordoutstand.value=parseFloat(ordNetTotal.value) - parseFloat(ordpaidamnt.value);
 
		
	}
  
	else if(rowindx >= 0 && rowindx < rowCount) {
		sendValBoqToPopup(rowindx);
	
	}

    // Show previous record 
	else if (rowindx >= rowCount){

		rowindx--;
		sendValBoqToPopup(rowindx);
	}
 
 } // End delete fct
 

     				


								
function checkSerial() {
var Data = {};
	 	 var serial_no="";
	 	 var itm_model ="";
	 	 var itm_partno = "";
	 	 var indx;
	 	 Data.serialArray=[];
 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){

 // Remove the row where SerialNumber is empty
	    
	    	var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input')[0].value;
		if (serNum == '') {
			$(this).parents("tr").remove();
         }
	     else {
	        var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	        var itemMod = $(this).parent().parent().children('td[name="itmModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
	    
	   
			    serial_no = serNum;
			    itm_model = itemMod;
			    itm_partno = itemPartnum;
			    Data.serialArray.push({serial_no:serNum, itm_model:itemMod, itm_partno:itemPartnum});
			    
	      }
	  	});
	    	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input')[0].value = JSON.stringify(Data);
} // end check serial fct
	    	

 function addRowSerial(){
 
	checkSerial();
	rowcountSerial =  $("#serialNoTable >tbody tr").length;
	console.log("rowcountSerial:" +rowcountSerial);
	
    var ctx = getContextPath();

	var markup = "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td><td name='serialNumber'>"
	+"<input name='serialNumber'  class='form-control text-input' type='text' style='width:200px;position:relative;left:11px;'/></td>"
	+ "<td name='itmModel' style='width:200px'> <input name='itmModel' id=" + "itmModel_" + rowcountSerial +" style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
	+ "<td name='itemPart' style='width:200px'> <input name='itemPart' id=" + "itemPart_" + rowcountSerial +" style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";
	$("#serialNoTable > tbody").append(markup);
	
	
	
		$('#itemPart_'+rowcountSerial).autocomplete({
			
		source: function(request, response) {
		$.ajax({ 
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getPartNo',
			data: {
				requestValue : request.term
		},
			dataType: "json",
			success: function (data) {
					                 
			if (data != null) {
				response(data.ListPartNos);
			}
			},
			error: function(result) {
				console.log(222);
				                 
			}
			});
			}, minLength:0, maxShowItems: 40, scroll:true,
		
			select: function(event, ui) {
				this.value = (ui.item ? ui.item[0]  : '');
				document.getElementById("popupItemPartno").value= ui.item[0] ;
				document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
				document.getElementById("popupItemModel").value= ui.item[3] ;
				 $(this).parents("tr").find('input[name ="itmModel"]').val(ui.item[3]);
				return false;
			}
			}).autocomplete("instance")._renderItem = function(ul, item) {
				var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
				item[0] + "</span><br><span class='desc'>" +
				item[1] + "</span><span class='desc'>" +","+ 
				item[2] + "</span><span class='desc'>";
				if(item[3] != '-')
					appendString += ","+item[3] + "</span><span class='desc'>";
				if(item[4] != '-')
					appendString += ","+item[4];

				appendString += "</span></div>";
				return $("<li class='each'>").append(appendString).appendTo(ul);
  		
	     };
		$('#itemPart_'+rowcountSerial).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
	
		});
	
	
    // Item Model autocomplete in 1st tab
    $('#itmModel_'+rowcountSerial ).autocomplete({
		source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url:ctx+'/getModel',
			data: {
				requestValue : request.term
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.ListModels);
				}
			},
			error: function(result) {
				console.log(222);
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("popupItemModel").value= ui.item[0] ;
			document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
			document.getElementById("popupItemPartno").value= ui.item[3] ;
			$(this).parents("tr").find('input[name ="itemPart"]').val(ui.item[3]);
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
					
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>"+"," +
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
	  		
	};
			$('#itmModel_'+rowcountSerial ).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
	
		});
	 
 }
 
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
 
	
 $(function(){

var ctx = getContextPath();
 
 // Add new row in serialNo table using Enter key 
 $("#serialNoTable > tbody").keyup(function (event)  {
	if (event.keyCode == 13) {
		addRowSerial(); 
	}
 }); // End Add row using enter key 
 
 
 
 // Delete row in serialNo Table	
 $(".delete-row-serial").click(function () {
 slctDel = [];
 
 $("#serialNoTable > tbody").find('input[name="record"]').each(function () {
 if ($(this).is(":checked")) {
	slctDel.push($(this).parent().parent().children('td[name="serialNumber"]').children('input').val());
	console.log("The selected delete is " + slctDel);
	 
	 if(allDelSerials.includes($(this).parent().parent().children('td[name="serialNumber"]').children('input').val())==false) {
	 	allDelSerials.push($(this).parent().parent().children('td[name="serialNumber"]').children('input').val());
     }
 }
 });
	console.log("The selected delete after is " + slctDel);

	for (i = 0; i <= slctDel.length; ++i) {
 	if (slctDel.length == 0) {
    	alert(' Select Row(s) to Delete');
        return false;
     }
     }
	$("#serialNoTable > tbody").find('input[name="record"]').each(function () {
    	if ($(this).is(":checked")) {
       	$(this).parents("tr").remove();
       
       	}
     

	});
		
 }); // end delete row fct
 
 //Select all checkbox in serialNo table
 $('body').on('click', '#selectAllSerial', function  () {
	if ($(this).hasClass('allChecked')) {
		$('input[type="checkbox"]', '#serialNoTable').prop('checked', false);
	} 
	else {
		$('input[type="checkbox"]', '#serialNoTable').prop('checked', true);
	}
	
	$(this).toggleClass('allChecked');
	       				
 }) // end select checkbox fct 
 
 
 
    // Item Model autocomplete in 1st tab
	$("#popupItemModel").autocomplete({
	source: function(request, response) {
   	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getModel',
		data: {
			requestValue : request.term
		},
	dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.ListModels);
		}
	},
		error: function(result) {
			alert("Error");
		}
	});
	}, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0]  : '');
		document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
		document.getElementById("popupItemPartno").value= ui.item[3];
		document.getElementById("popupBarcode").value= ui.item[4];					
		return false;
	}


	  }).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
						
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>"+"," +
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";
			if(item[4] != '-')
				appendString += ","+item[4];

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
		};


		$("#popupItemModel").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}						
		});
   
   	
	
		//Itemcode & name autocomplete
 $("#popupItemCode").autocomplete({
	source: function(request, response) {
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getItemCode',
		data: {
			requestValue : request.term
			},
		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.ListItemDetails);
			}
		},
		error: function(result) {
			alert("Error");
		}
	});
	
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
		popupItemCode.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		document.getElementById("popupItemModel").value= ui.item[2];
		document.getElementById("popupItemPartno").value= ui.item[3];
		document.getElementById("popupBarcode").value= ui.item[4];
		return false;
	}

	}).autocomplete("instance")._renderItem = function(ul, item) {
		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
                
		item[0] + "</span><br><span class='desc'>" +
		item[1] + "</span><span class='desc'>";
		if(item[2] != '-')
			appendString += ","+item[2] + "</span><span class='desc'>";
		if(item[3] != '-')
			appendString += ","+item[3] + "</span><span class='desc'>";
		if(item[4] != '-')
			appendString += ","+item[4];

		appendString += "</span></div>";
		return $("<li class='each'>").append(appendString).appendTo(ul);
	};
					
	$("#popupItemCode").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});
 	
 	
	
	
	// ItemPartNo autocomplete in 1st tab
					
	$("#popupItemPartno").autocomplete({
	source: function(request, response) {
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
	    url: ctx+'/getPartNo' ,
		data: {
			requestValue : request.term
		},
		dataType: "json",
        	success: function (data) {
         		if (data != null) {
                	response(data.ListPartNos);
				}
			 },
			 error: function(result) {
              	alert("Error");
              }
         });
         
         }, minLength:0, maxShowItems: 40, scroll:true,	

		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
			document.getElementById("popupItemModel").value= ui.item[3];
			document.getElementById("popupBarcode").value= ui.item[4];
			return false;
	    }


		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>" +","+ 
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";
			if(item[4] != '-')
				appendString += ","+item[4];

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
		};


	$("#popupItemPartno").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
			}						
		});
		
		
	// Resize and drag the popup
	$('.modal-content').resizable({
	handles: "e" ,
	
	});
	 
	$('.modal-dialog').draggable({
	handle: ".modal-header, .modal-footer"
	});
	   
	$('#poModal').on('show.bs.modal', function() {
	$(this).find('.modal-body').css({
	'max-height': '100%',
	});
	});
     
	//Reset the popup to original size after resizing 
	$('#poModal').on('hidden.bs.modal', function() {
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
		if(iconToChange.hasClass('fa-window-restore')){
     		iconToChange.removeClass('fa-window-restore')
    		            .addClass('fa-minus')
		}
		else{
     		iconToChange.addClass('fa-window-restore')
    		             .removeClass('fa-minus')
		}    		         
	}); // end minimize/maximize fct
    
 
   	
	// Calculate netRate, total, totalAt, sum of TotalAt in popup 
	$('#popupRate, #popupDiscountAmount,#popupQty,#popupNetRate,#popupTax').change(function() {
 	
		if ($('#popupDiscountAmount').val() == '') {
			$('#popupDiscountAmount').val(0);
		}

		if ($('#popupTax').val() == '') {
			$('#popupTax').val(0);
		}
		if ($('#popupRate').val() == '') {
			$('#popupRate').val(0);
		}
		if ($('#popupQty').val() == '') {
			$('#popupQty').val(0);
		}
		if ($('#popupTotalAt').val() == '') {
			$('#popupTotalAt').val(0);
        }

		var popupNetRate = parseFloat($('#popupRate').val()) - parseFloat($('#popupDiscountAmount').val());
		$('#popupNetRate').val(popupNetRate);

		var popupTotal = parseFloat($('#popupQty').val()) * (popupNetRate);
		$('#popupTotal').val(popupTotal);

		var popupTax = ((parseFloat($('#popupTax').val()) * popupNetRate)/100);
    					
		var popupTotalAt = parseFloat($('#popupQty').val()) * (popupNetRate+popupTax);
   
		$('#popupTotalAt').val(popupTotalAt);
     					
	});
	
// Send concatenated Serial Table rows to boq table on any change in td
  $('#serialNoTable').on('focusout', function() {
 
 
		 var Data = {};
	 	 var serial_no="";
	 	 var itm_model ="";
	 	 var itm_partno = "";
	 	 var indx;
	 	 Data.serialArray=[];
	 	 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){

 
	        var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	        var itemMod = $(this).parent().parent().children('td[name="itmModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
			    serial_no = serNum;
			    itm_model = itemMod;
			    itm_partno = itemPartnum;
			    Data.serialArray.push({serial_no:serNum, itm_model:itemMod, itm_partno:itemPartnum});
		  	});


	    	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input')[0].value = JSON.stringify(Data);
	    

}); // end fct



	    	
 //Send input values from popup to boq when any popup input change
 $('#popupItemCode,#popupItemModel, #popupItemPartno, #popupQty,#popupRate,#popupTax,#popupDiscountAmount,#popupBarcode,#popupSeq,#popupCat1,#popupCat2,#popupCat3,#popupCat4').on(' focusout ', function() {

 	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemCode"]').children('input').val($('#popupItemCode').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNo"]').children('input').val($('#popupItemPartno').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="poBarCode"]').children('input').val($('#popupBarcode').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="qty"]').children('input').val($('#popupQty').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="poCat1"]').children('input').val($('#popupCat1').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="poCat2"]').children('input').val($('#popupCat2').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="poCat3"]').children('input').val($('#popupCat3').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="poCat4"]').children('input').val($('#popupCat4').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="poSequ"]').children('input').val($('#popupSeq').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="rate"]').children('input').val($('#popupRate').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tax1"]').children('input').val($('#popupTax').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="discountAmount"]').children('input').val($('#popupDiscountAmount').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="netRate"]').children('input').val($('#popupNetRate').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="total"]').children('input').val($('#popupTotal').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="totalAt"]').children('input').val($('#popupTotalAt').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="grpQty"]').children('input').val($('#popupGrQty').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="prQty"]').children('input').val($('#popupPrQty').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="arQty"]').children('input').val($('#popupArQty').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="cipQty"]').children('input').val($('#popupCipQty').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="farQty"]').children('input').val($('#popupFarQty').val());
	
				
	    getTotalAT_SumQty();
	    amountsUpdate();
	    
      }); // end fct	
// Close popup function  				
   $("button[name='closePopup']").on("click", function(){
   
	 sendValPopupToBoq(rowindx);
  
	    $("#poModal").modal("hide");
	    getTotalAT_SumQty();
	    amountsUpdate();
	    
	    
		 
 }); // end close fct

 
 function getTotalAT_SumQty(){
  var sumQty =0;
  var sumTotalAt=0;	
   if (ordNetTotal.value == ''){ 
   ordNetTotal.value=0;
   }
   if (orddiscamnt.value == ''){
    orddiscamnt.value=0;
	orddiscpercent.value=0;
	}
  if (ordoutstand.value == ''){
  ordoutstand.value=0;
   }
   if (ordpaidamnt.value == ''){
   ordpaidamnt.value=0;
	}
 
  
 $("#bisotab > tbody > tr").each(function(i, row){
 
 		sumQty = sumQty + parseFloat($(this).children('td[name="qty"]').children('input').val());
		//console.log("sumQty is:"+sumQty);
		sumTotalAt = sumTotalAt + parseFloat($(this).children('td[name="totalAt"]').children('input').val());
		//console.log("sumTotalAt is:"+sumTotalAt);
	
   });
		$('#ordtotqty').val(sumQty);
		$('#ordtotamnt').val(sumTotalAt);
		     	            	
 }
 
 function amountsUpdate(){
	ordNetTotal.value= parseFloat(ordtotamnt.value) - parseFloat(orddiscamnt.value);
	ordoutstand.value=parseFloat(ordNetTotal.value) - parseFloat(ordpaidamnt.value);
 }
 
	       				 
// Send input values from popup to boq table and close the popup using ESC 
	document.addEventListener('keydown', function(event){
    	if(event.key === "Escape"){
			if($("#poModal").hasClass("show")){
				// Send input values from popup to boq table and close the popup using ESC 
				sendValPopupToBoq(rowindx);
				$("#poModal").modal("hide");
				getTotalAT_SumQty();
				amountsUpdate();
			}		       						
    	}
 });// end close fct using esc
    				
// Prev fct in popup

  $("button[name='previousRow']").on("click", function(){
        		        
   	if(rowindx > 0) {
   		
		rowindx-- ;
		var PrevIndex = parseInt(rowindx);
		sendValBoqToPopup(PrevIndex);
   		
   		
     }
     // Close popup on row 0 (end of prev fct)
	  else if (rowindx == 0) {
       		    	$("#poModal").modal("hide");
       }
   	
   	});// end prev fct  
   	

  	
 //to select all checkbox or unselect them all from top table
$('body').on('click', '#selectAll', function  () {
	if ($(this).hasClass('allChecked')) {
    	$('input[type="checkbox"]', '#bisotab').prop('checked', false);
    } 
    else {
    	$('input[type="checkbox"]', '#bisotab').prop('checked', true);
    }
    $(this).toggleClass('allChecked');
       				
   })   	
   
 // on change discount amount net total changed
$("#orddiscamnt").on("input", function(){
	// validate Discount Amount to be number
	if ($("#orddiscamnt").val() != '') {
		if ($. isNumeric( $("#orddiscamnt").val())== false) {
			alert('Discount Amount is  not Numeric');
			return false;
		}
		if (parseFloat(orddiscamnt.value) <= parseFloat(ordtotamnt.value)) {
			orddiscpercent.value = (parseFloat(orddiscamnt.value) * 100) /parseFloat(ordtotamnt.value);
			updateamounts();
		}
		else {
			alert('Discount Amount cannot be greater than Total Amount: ' + parseFloat(ordtotamnt.value));
		}
	}
}); 

 // on change paid amount outstanding changed 
$("#ordpaidamnt").on("input", function(){
	// validate Discount Amount to be number
	if ($("#ordpaidamnt").val() != '') {
		if ($. isNumeric( $("#ordpaidamnt").val())== false) {
			alert('Paid Amount is  not Numeric');
			return false;
		}
		if (parseFloat(ordpaidamnt.value) <= parseFloat(ordNetTotal.value)) {
			updateamounts ();
		}
		else {
			ordpaidamnt.value= '0';
			alert('Paid Amount cannot be greater than Net Total Amount: ' + parseFloat(ordNetTotal.value));
		}
	}
}); // end of on change paid amount

 //restore basic discount amount if we remove dsicount percent after save
var maindiscamont = (parseFloat(orddiscamnt.value))
				    				    
// on change Discount percent  Net total changed 
$("#orddiscpercent").on("input", function(){
	// validate Discount Percent to be number
	if ($("#orddiscpercent").val() != '') {
	if ($. isNumeric( $("#orddiscpercent").val())== false) {
		alert('Dsicount Percent is  not Numeric');
		return false;
	}
	orddiscamnt.value = ((parseFloat(orddiscpercent.value)*parseFloat(ordtotamnt.value))/100);
	updateamounts ();
	}
}); // end of on change Discount percent

				    

//remove selected rows from boq
 $(".delete-row").click(function(){
 		slctDelOrd =[];
        	var checked="false"; //when no checkbox is checked
			var po_Pk ="";
   
        	$("#bisotab > tbody").find('input[name="record"]').each(function(){

              if($(this).is(":checked")){
            	  checked="true"; //when 1 or more checkbox is checked		 
        				po_Pk=$(this).parent().parent().children('td[name="porItemId"]').children('input').val();
        				if( po_Pk !=0){
        					slctDelOrd.push(po_Pk);
      			            	}   
      			           		    $(this).parents("tr").remove();  
      			                } 
        			});
        	if(checked=="false"){
               	alert(' Select Row(s) to Delete');
    			
             	return false;
             }	          
			   getSumQty_totalAT();         
        });	// end delete row





 $("#popupBarcode").autocomplete({
 	source: function(request, response) {
 		var barcode=$("#popupBarcode").val();
 		
 		$.ajax({
 			type: "GET",
 			contentType: "application/json; charset=utf-8",
 			url: ctx+'/getBarcode',
 			data: {
				"requestValue": request.term
 			},
 			dataType: "json",
 			success: function (data) {
 				if (data != null) {
 					 response(data.ListBarcode);
				}
			},
			error: function(result) {
				alert("Error");
			}
			});
			}, minLength:0, maxShowItems: 40, scroll:true,	
			select: function(event, ui) {
				this.value = (ui.item ? ui.item[0]  : '');
				$("#popupItemCode").val(ui.item[1] + ":" + ui.item[2]);
				$("#popupItemModel").val(ui.item[3]);
				$("#popupItemPartno").val(ui.item[4]);
				$("#popupCat1").val("");
				$("#popupCat2").val("");
				$("#popupCat3").val("");
				$("#popupCat4").val("");
				$("#popupSeq").val("");
					
				return false;
			}
			}).autocomplete("instance")._renderItem = function(ul, item) {
				var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
				item[0] + "</span><br><span class='desc'>" +
				item[1] + "</span><span class='desc'>" +","+ 
				item[2] + "</span><span class='desc'>";
				if(item[3] != '-')
					appendString += ","+item[3] + "</span><span class='desc'>";
				if(item[4] != '-')
					appendString += ","+item[4];

				appendString += "</span></div>";
				return $("<li class='each'>").append(appendString).appendTo(ul);
			};
			
			$("#popupBarcode").focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
				}
			});
			
			
// categories auto complete in popup
$("#popupCat1").autocomplete({
	source: function(request, response) {
		var cat1 =($("#popupCat1").val().split(":"))[0];
		 var cat2 = ($("#popupCat2").val().split(":"))[0];
		 var cat3 = ($("#popupCat3").val().split(":"))[0];
		 var cat4 = ($("#popupCat4").val().split(":"))[0];
			
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: ctx+'/getParentCategory',
				data: {
					"catInput":cat1,
					"cat2Input":cat2,
					"cat3Input":cat3,
					"cat4Input":cat4
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.ListCategory);
				}
			},
			error: function(result) {
				alert("Error");
			}
			});
			}, minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
				$("#popupCat1").val(ui.item[0]+":"+ui.item[1]);
				$("#popupBarcode").val("");
				return false;
			}
			}).autocomplete("instance")._renderItem = function(ul, item) {
				return $("<li class='each'>")
			.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
			item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
			.appendTo(ul);
			};
			
			$("#popupCat1").focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
			}						
		});
		
$("#popupCat2").autocomplete({
source: function(request, response) {
	
	
	var cat1 =($("#popupCat1").val().split(":"))[0];
	var cat2 = ($("#popupCat2").val().split(":"))[0];
	var cat3 = ($("#popupCat3").val().split(":"))[0];
	var cat4 = ($("#popupCat4").val().split(":"))[0];
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getCat2',
		data: {
			"catInput":cat1,
			"cat2Input":cat2,
			"cat3Input":cat3,
			"cat4Input":cat4,
		},
		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.listCat);
			
	
			}
		},
		error: function(result) {
			alert("Error");
		}
	});
}, minLength:0, maxShowItems: 40, scroll:true,	
select: function(event, data) {

	$(this).val(data.item[0]+":"+data.item[1]);
	$("#popupBarcode").val("");

		return false;
}
}).autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li class='each'>")
		.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
		item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
		.appendTo(ul);
	};
	$("#popupCat2").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});
		
$("#popupCat3").autocomplete({
source: function(request, response) {
		
	var cat1=($("#popupCat1").val().split(":"))[0];
	var cat2 = ($("#popupCat2").val().split(":"))[0];
	var cat3 = ($("#popupCat3").val().split(":"))[0];
	var cat4 = ($("#popupCat4").val().split(":"))[0];
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getCat3',
		data: {
			"catInput":cat1,
			"cat2Input":cat2,
			"cat3Input":cat3,
			"cat4Input":cat4,
		},

		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.listCat);
		
			}
		},
		error: function(result) {
			alert("Error");
		}
	});
}, minLength:0, maxShowItems: 40, scroll:true,	
select: function(event, data) {

	$(this).val(data.item[0]+":"+data.item[1]);
	$("#popupBarcode").val("");
	return false;
}
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $("<li class='each'>")
	.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
	.appendTo(ul);
	};
	$("#popupCat3").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});


$("#popupCat4").autocomplete({
source: function(request, response) {
	
	var cat1=($("#popupCat1").val().split(":"))[0];
	var cat2 = ($("#popupCat2").val().split(":"))[0];
	var cat3 = ($("#popupCat3").val().split(":"))[0];
	var cat4 = ($("#popupCat4").val().split(":"))[0];
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getCat4',
		data: {
			"catInput":cat1,
			"cat2Input":cat2,
			"cat3Input":cat3,
			"cat4Input":cat4,
		},

		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.listCat);

			}
		},
		error: function(result) {
			alert("Error");
		}
	});
}, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, data) {

		$(this).val(data.item[0]+":"+data.item[1]);
		$("#popupBarcode").val("");
		return false;
}
}).autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li class='each'>")
		.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
				item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
				.appendTo(ul);
	};
	$("#popupCat4").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});


$("#popupSeq").autocomplete({
	
source: function(request, response) {
	
	var cat1=($("#popupCat1").val().split(":"))[1];
	var cat2 = ($("#popupCat2").val().split(":"))[1];
	var cat3 = ($("#popupCat3").val().split(":"))[1];
	var cat4 = ($("#popupCat4").val().split(":"))[1];
	var seq = $("#popupSeq").val();

	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getSequ',
		data: {
			"catInput":cat1,
			"cat2Input":cat2,
			"cat3Input":cat3,
			"cat4Input":cat4,
			"seqInput":seq,
		},

		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.listCat);
	
			}
		},
		error: function(result) {
			alert("Error");
		}
	});
}, minLength:0, maxShowItems: 40, scroll:true,		
select: function(event, data) {
	
		$(this).val(data.item[0]);
	
		$("#popupItemCode").val(data.item[0] + ":" + data.item[1]) ;
		$("#popupItemModel").val(data.item[3]);
		$("#popupItemPartno").val(data.item[4]);

		$("#popupBarcode").val("");
		return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
                
		item[0] + "</span><br><span class='desc'>" +
		item[1] + "</span><span class='desc'>" +","+ 
		item[2] + "</span><span class='desc'>";
		if(item[3] != '-')
			appendString += ","+item[3] + "</span><span class='desc'>";
		if(item[4] != '-')
			appendString += ","+item[4];

		appendString += "</span></div>";
		return $("<li class='each'>").append(appendString).appendTo(ul);
		};
		$("#popupSeq").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}						
		});	

	//End SS
				 
	
	/////////////end autocpmplete for purchase request
	
	
	
									
  });
  
     		         

    			  
  

    			 
   