var rowindx =0;
var rowcountSerial =0;
var serialRowIndex=0;
var  slctDelGds=[];
var allDelSerials=[]; // to store all deleted serial numbers from different popups
 function openPop(element) {
 
	var buttonRowIndx = $(element).closest("tr");
	rowindx = (buttonRowIndx[0].rowIndex - 1);
	
	
    sendValBoqToPopup(rowindx);
    $("#grModal").modal("show");
	
	AutocompleteOnLoad();
	
 }// end open popup fct

function sendValBoqToPopup(indxRow){

//Send input values from Boq table  to popup
	$('#popupItem').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemCode"]').children('input').val());
	$('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grItemModel"]').children('input').val()); 
	$('#popupItemPart').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grItemPartNo"]').children('input').val());
	$('#popupQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="qty"]').children('input').val());
	$('#popupRate').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rate"]').children('input').val());
	$('#popupTax').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="tax1"]').children('input').val());
	$('#popupDiscount').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="discountAmount"]').children('input').val());
	$('#popupNetRate').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="netRate"]').children('input').val());
	$('#popupTotal').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="total"]').children('input').val());
	$('#popupTotalAt').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="totalAt"]').children('input').val());
	$('#popupPoQty').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poQty"]').children('input').val());
	$('#popupPrQty').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prQty"]').children('input').val());
	$('#popupArQty').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="arQty"]').children('input').val());
	$('#popupCipQty').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cipQty"]').children('input').val());
	$('#popupFarQty').val( $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="farQty"]').children('input').val());
		
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
			itemRowSerial +=  "<td name='itemModel' style='width:200px'><input name='itemModel'   type='text' value='" + itmModel + "'  style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial +=  "<td name='itemPart' style='width:200px'><input name='itemPart'  type='text' value='" + itmPartNo + "'  style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial +=  "</tr>";
			$("#serialNoTable > tbody").append(itemRowSerial);
		});
		}		
 // Send hidden serialNumber value from Boq to popup
/*	var hiddenSerialModelPartNo = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNo"]').children('input').val();
 	 
	var concatSerModPart = hiddenSerialModelPartNo;
	var firstSplit =' ';
	var serModPartNum ='';
 	 
	//Split all rows
	firstSplit = concatSerModPart.split(',;');
	
 	    	 	
	// Clear serialNo table in each new popup
			
																													  
	$("#serialNoTable > tbody").html(" ");
 			    
	// 2nd split to get SerialNo, ItemModel, ItemPartNo of each row 
 	for (var z = 0; z < firstSplit.length-1; z++) {
	serModPartNum = firstSplit[z].split(',');
 	    	 		
 		//Set Serial Number/Model/Part Number values in popup
		for (var q = 0; q < serModPartNum.length-1; q++) {
 	    	 			
		var serialNum = serModPartNum[0];
		var itemModel = serModPartNum[1];
		var itemPartNum = serModPartNum[2];
		}	    	 	
		var itemRowSerial = "<tr>";
		itemRowSerial = itemRowSerial + "<td><input type='checkbox' name='record' style='position:relative;left:20px;top:10px'></td>"
		itemRowSerial = itemRowSerial + "<td name='serialNumber'><input name='serialNumber' class='form-control text-input' type='text'value='" + serialNum +"' style='width:200px;position:relative;left:11px;' class='form-control text-input '/></td>";
		itemRowSerial = itemRowSerial + "<td name='itemModel' style='width:200px'><input name='itemModel'  type='text' value='" + itemModel + "' style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
		itemRowSerial = itemRowSerial + "<td name='itemPart' style='width:200px'><input name='itemPart'    type='text' value='" + itemPartNum + "' style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
		itemRowSerial = itemRowSerial + "</tr>";
		$("#serialNoTable > tbody").append(itemRowSerial);   

}
*/

		//Update popup Nb in header 	 		
		var element = document.getElementById("popupNb");
		element.innerHTML = "Item # " +(indxRow+1);
		
		
} //end sendValBoqToPopup fct

function sendValPopupToBoq(indxRow){

 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="itemCode"]').children('input').val($('#popupItem').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grItemModel"]').children('input').val($('#popupItemModel').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grItemPartNo"]').children('input').val($('#popupItemPart').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="qty"]').children('input').val($('#popupQty').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rate"]').children('input').val($('#popupRate').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="tax1"]').children('input').val($('#popupTax').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="discountAmount"]').children('input').val($('#popupDiscount').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="netRate"]').children('input').val($('#popupNetRate').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="total"]').children('input').val($('#popupTotal').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="totalAt"]').children('input').val($('#popupTotalAt').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poQty"]').children('input').val($('#popupPoQty').val());
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
	        var itemMod = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
	    	

			    Data.serialArray.push({serial_no:serNum, itm_model:itemMod, itm_partno:itemPartnum});

	      }
	  	});
	    	$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNo"]').children('input')[0].value = JSON.stringify(Data);			

/*
	  var serialModelPartnum = "";
	//Get value of serialNumber, itemModel-PartNumber in each serialNumber table row 
	  $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){
	  
        var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	// Remove the row where SerialNumber is empty
	    if (serNum == '') {
	      $(this).parents("tr").remove();

	     }
            					
	     else {
	        					
	      var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	      var itemMod = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
	      var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();

	     serialModelPartnum += serNum +',' +itemMod +',' +itemPartnum + ','+';';
	        }
	    });

		
	    					
	     $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNo"]').children('input').val(serialModelPartnum);
	*/	   
         
}
///////////// function to check GR inputs validity for action buttons (save, approve, cancel)
	function checkedDataOnAction(){
				val =$("#grorderdate").val();
			     val1 = Date.parse(val);
			       if (isNaN(val1) == true) {
			          alert(' Ordered Date is not valid');
			          return false;
			            }
			        
			         // validate Delivered Date cannot be null
				 val =$("#grdeliverydate").val();
			     val1 = Date.parse(val);
			      if (isNaN(val1) == true) {
			          alert(' Delivered Date is not valid');
			          return false;
			            }
				 
				  if ($("#grtotamnt").val() == '')  { grtotamnt.value = 0;  }
				  if ($("#grdiscamnt").val() == '')  { grdiscamnt.value = 0; }
				  if ($("#grdiscpercent").val() == '') { grdiscpercent.value = 0; }
				  if ($("#grpaidamnt").val() == '') { grpaidamnt.value = 0; }
				  if ($("#groutstand").val() == '') { groutstand.value = 0; }
				  if ($("#grtotqty").val() == '') { grtotqty.value = 0; }
				  if ($("#grtotword").val() == '') { grtotword.value = 0; }
				  
				  
				 // validate Total Amount to be number 
				 if (($. isNumeric( $("#grtotamnt").val()))== false) {
				 alert('Total Amount is  not Numeric');
				 return false;}
				 
				  // validate Discount Amount to be number
				 if (($. isNumeric( $("#grdiscamnt").val()))== false) {
				 alert('Discount Amount is  not Numeric');
				 return false;}
				 
				  // validate Discount pecent to be number
				 if (($. isNumeric( $("#grdiscpercent").val()))== false) {
				 alert('Discount pecent is  not Numeric');
				 return false;}
				 
				  // validate paid Amount to be number
				 if (($. isNumeric( $("#grpaidamnt").val()))== false) {
				 alert('paid Amount is  not Numeric');
				 return false;}
				 
				  // validate Outstanding to be number
				 if (($. isNumeric( $("#groutstand").val()))== false) {
				 alert('Outstanding is  not Numeric');
				 return false;}
				 
				  // validate Qty to be number
				 if (($. isNumeric( $("#grtotqty").val()))== false) {
				 alert('Qty is  not Numeric');
				 return false;}
				 
				 // validate netTotal to be number
				 if (($. isNumeric( $("#grtotword").val()))== false) {
				 alert('Net Total is  not Numeric');
				 return false;}
				 
				 
				}
//////////////////////////////////////
////// function to fetch all boq rows 
function buildingSavedBOQ(boqRow){
 	
		var itemModel = boqRow.itemModel;

									if (itemModel == null)
										itemModel = "";
									else
										itemModel = boqRow.itemModel;

									var itemPartNumber = boqRow.itemPartNumber;
									if (itemPartNumber == null)
										itemPartNumber = "";
									else
										itemPartNumber = boqRow.itemPartNumber;
									var dotStatus = (boqRow.grStatus);

									var span;

									if (dotStatus == "1") {

										span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: chartreuse;'></span>";
									} else {

										span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: orange;'></span>";
									}

									var serialArrays = [];
									if (boqRow.serial_obj != null) {
										serialArrays
												.push(boqRow.serial_obj);
									} else {
										serialArrays.push(null);
									}

									var itemRow = "<tr>";
									itemRow = itemRow
											+ "<td><input type='checkbox' name='record'>"
											+ span
											+ "<button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:1px;'><i class='fas fa-desktop'></i></button></td>"
									itemRow = itemRow
											+ "<td name='itemCode'><input type='text' name='itmCode' value='" + boqRow.itemCode +":"+ boqRow.itemName + "' style='width:200px;' class='form-control text-input'/></td>";
									itemRow = itemRow
											+ "<td name='grItemModel'><input name='itmModel' type='text' value='" + itemModel + "' style='width:200px;' class='form-control text-input'/></td>";
									itemRow = itemRow
											+ "<td name='grItemPartNo'><input name='itmPartNo' type='text' value='" + itemPartNumber + "' style='width:200px;' class='form-control text-input'/></td>";
									itemRow = itemRow
											+ "<td name='qty'><input name='qty' type='text'  value='" + boqRow.qty +"' style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='rate'><input name='rate' type='text'  value='" + boqRow.rate +"' style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='discountAmount'><input  name='discountAmount' type='text' value='" + boqRow.discountAmount +"'  style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='tax1'><input name='tax1' type='text' value='" + boqRow.tax1 +"'  style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='netRate'><input type='text' value='" + boqRow.netRate +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='total'><input type='text' value='" + boqRow.total +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='totalAt'><input type='text' value='"  + boqRow.totalAt +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='poQty'><input type='text' value='" + boqRow.poQty +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='prQty'><input type='text' value='" + boqRow.prQty +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='arQty'><input type='text' value='" + boqRow.arQty +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='cipQty'><input type='text' value='" + boqRow.cipQty +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='farQty'><input type='text' value='" + boqRow.farQty +"' readonly style='width:200px;' class='form-control text-input'></td>";
									itemRow = itemRow
											+ "<td name='grItemId'><input type='text' value='" + boqRow.grItemId +"'readonly style='width:200px;' class='form-control text-input'><input type='text' name='grStatus' value='" + boqRow.grStatus +"'hidden></td>";
									itemRow = itemRow
											+ "<td name='serialNo'><input type='text'  style='width:200px;' value='" + serialArrays[0] +"'hidden class='form-control text-input'></td>";
									itemRow = itemRow + "</tr>";
									$("#bisotab > tbody").append(itemRow);

									var loadRowCount = $("#bisotab >tbody tr").length;
								

}				
function boqInputsListener(){
	
	
$('#bisotab tr td input').change(function() {
	var cell = $(this).val();
	var column_name = $(this).parent().attr('name');
	
	if ((column_name == 'qty') ) {
    	// validate Qty
    	if (($. isNumeric(cell ))== false) {
			alert('Qty is  not Numeric');
			this.focus();
			return false;
		}
		
		else{
			calculateParam.bind(this)();
		}
	}
	
	if ((column_name == 'rate') ) {
    	// validate Rate
		if (($. isNumeric(cell ))== false) {
			alert('Rate is  not Numeric');
			this.focus();
			return false;
		} 
		else {
			calculateParam.bind(this)();
	 	}
     }
     
     if ((column_name == 'discountAmount') ) {
      	// validate Discount
		if (($. isNumeric(cell ))== false) {
			alert('Discount is  not Numeric');
			this.focus();
			return false;
		}
		else{
			calculateParam.bind(this)();
		}
	}
	if ((column_name == 'tax1') ) {
    	// validate Discount
		if (($. isNumeric(cell ))== false) {
			alert('Tax is  not Numeric');
			this.focus();
			return false;
		}
		else {
			calculateParam.bind(this)();
		}
     }
});		

}			
function htmlBoqInsert(){
	
	 var markup = "<tr><td><input type='checkbox' name='record'><span class='dotStatus' id='dotStatus'></span><button type='button' href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td><td name='itemCode'>"	
	      		  	+"<input name='itmCode' type='text' style='width:200px;' class='form-control text-input'/></td>"
	      		  	+"<td name='grItemModel'>"
	      		  	+"<input name='itmModel' type='text' style='width:200px;' class='form-control text-input'/></td>"
	      		  	+"<td name='grItemPartNo'>"
	      		  	+"<input name='itmPartNo' type='text' style='width:200px;' class='form-control text-input'/></td>"
	      		  	+"<td name='qty'><input type='text' style='width:200px;' class='form-control text-input' value= 0></td>"
	      		  	+"<td name='rate'><input type='text' style='width:200px;' class='form-control text-input' value= 0></td>"
	      		    +"<td name='discountAmount'><input type='text' style='width:200px;' class='form-control text-input' value= 0></td>"
	      		  	+"<td name='tax1'><input type='text' style='width:200px;' class='form-control text-input' value=0></td>"
	      		  	+"<td name='netRate'><input type='text' style='width:200px;' class='form-control text-input' readonly  value= 0></td>"
	      		  	+"<td name='total'><input type='text' style='width:200px;' class='form-control text-input' readonly value= 0></td>"
	      		  	+"<td name='totalAt'><input type='text' style='width:200px;' class='form-control text-input' readonly value= 0></td>"
	      		  	+"<td name='poQty'><input type='text' style='width:200px;' class='form-control text-input' readonly></td>"
	      		  	+"<td name='prQty'><input type='text' style='width:200px;' class='form-control text-input' readonly></td>"
	      		  	+"<td name='arQty'><input type='text' style='width:200px;' class='form-control text-input' readonly></td>"
	      		  	+"<td name='cipQty'><input type='text' style='width:200px;' class='form-control text-input' readonly></td>"
	      		  	+"<td name='farQty'><input type='text' style='width:200px;' class='form-control text-input' readonly value='0'></td>"
	      		  	+"<td name='grItemId'><input type='text' style='width:200px;' class='form-control text-input' readonly value= 0><input type='text' name='grStatus' hidden value= '0'></td>"
	      		  	+"<td style='visibility:hidden;width:0px;border-width: 0px;' name='serialNo'><input type='text' style='width:200px;' hidden value='null' ></td></tr>";
	      		  //<input type='text' style='width:200px;' hidden  value ='null' class='ui-widget ui-widget-content ui-corner-all form-control text-input'>
                
				
	return markup;
}				
function addNewRow(position){ 
 
	  var markup = htmlBoqInsert();

		if (position == "next"){
			$("#bisotab > tbody").append(markup);
			
			newRowCount =  parseInt($("#bisotab >tbody tr").length-1);
      		boqAutocomplete(newRowCount);
			$('table#bisotab tr:eq('+(newRowCount+1)+') td:nth-child(2) input').focus();
     	}
     	if (position =="below"){
			$("#bisotab > tbody tr").eq(rowindx).after(markup);
			
			newRowCount =  parseInt(rowindx+1);
      		boqAutocomplete(newRowCount);
			$('table#bisotab tr:eq('+(newRowCount+1)+') td:nth-child(1) input').focus();
		}
		if (position =="above"){
			$("#bisotab > tbody tr").eq(rowindx).before(markup);
			
			newRowCount =  rowindx+1;
      		boqAutocomplete(rowindx);
			
			$('table#bisotab tr:eq('+newRowCount+') td:nth-child(1) input').focus();
		}
		rowInputListener((newRowCount+1));
	 
    	
    
} // end add new row
function rowInputListener(rowIndex){
	
	
		$('#bisotab tr:eq('+rowIndex+') td input').change(function() {
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
						// calculateParam.call(this);
					 }
			}
			if ((column_name == 'rate') ) {
              // validate Rate
				 if (($. isNumeric(cell ))== false) {
				 alert('Rate is  not Numeric');
				 this.focus();
				 return false;} 
				 else {
					 calculateParam.bind(this)();
 
				 }
        }
          if ((column_name == 'discountAmount') ) {
              // validate Discount
				 if (($. isNumeric(cell ))== false) {
				 alert('Discount is  not Numeric');
				 this.focus();
				 return false;}
				 else {
					 calculateParam.bind(this)();

				 }
        }
        if ((column_name == 'tax1') ) {
              // validate Discount
				 if (($. isNumeric(cell ))== false) {
				 alert('Tax is  not Numeric');
				 this.focus();
				 return false;}
				 else {
					 calculateParam.bind(this)();
				 }
        }
        
        if ((column_name == 'totalAt') ) {
            // validate Discount
				 if (($. isNumeric(cell ))== false) {
				 alert('Tax is  not Numeric');
				 this.focus();
				 return false;}
				 else {
				  	  getSumQty_totalAT();
				 }
      }
			
		});	
		
}
//Calculate params in all rows 
function calculateParam(){
	var netrate= parseFloat($(this).parent().parent().children('td[name="rate"]').children('input').val()) - parseFloat($(this).parent().parent().children('td[name="discountAmount"]').children('input').val());
	var tax1 = ((parseFloat($(this).parent().parent().children('td[name="tax1"]').children('input').val()) * netrate)/100);
	var amount = parseFloat($(this).parent().parent().children('td[name="qty"]').children('input').val()) * (netrate);
	var amountAT = parseFloat($(this).parent().parent().children('td[name="qty"]').children('input').val()) * (netrate+tax1);
	$(this).parent().parent().children('td[name="netRate"]').children('input').val(netrate);
	$(this).parent().parent().children('td[name="total"]').children('input').val(amount);
	$(this).parent().parent().children('td[name="totalAt"]').children('input').val(amountAT);
	getSumQty_totalAT();
	
}// end calc param fct


function getSumQty_totalAT (){
	var sumQntity = 0;
	var sumtotAT = 0;
	if (grtotword.value == ''){
		grtotword.value=0;
	}
	if (grdiscamnt.value == ''){
		grdiscamnt.value=0;
		grdiscpercent.value=0;
	}
	if (groutstand.value == ''){
		groutstand.value=0;
	}
	if (grpaidamnt.value == ''){
		grpaidamnt.value=0;
	}
	
	$("#bisotab > tbody > tr").each(function(i, row){
		sumQntity = sumQntity + parseFloat($(this).children('td[name="qty"]').children('input').val());
		sumtotAT = sumtotAT + parseFloat($(this).children('td[name="totalAt"]').children('input').val());			
	});
	
	$('#grtotqty').val(sumQntity);
	$('#grtotamnt').val(sumtotAT);
		                
	updateamounts ();
                        
};


function updateamounts() {

	grtotword.value= parseFloat(grtotamnt.value) - parseFloat(grdiscamnt.value);
	groutstand.value=parseFloat(grtotword.value) - parseFloat(grpaidamnt.value);
	
}// end update amount fct



//boqAutocomplete fct
function boqAutocomplete(rowCnt){ //,tableIndx

	var ctx=getContextPath();
	//var tableID=tableIndx;
	
	//ITEM CODE autocomplete
	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmCode"]').autocomplete({
	source: function(request, response,event, ui) {
		
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
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');  // 0 is itemCode,  1 is itemName, 2 is model, 3 partNo
			$(this).parents("tr").find('input[name ="itmModel"]').val(ui.item[2]);
			$(this).parents("tr").find('input[name ="itmPartNo"]').val(ui.item[3]);
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
	  	
	  	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmCode"]').focus(function(){
	  		$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	  		if (this.value == ""){
	  			$(this).autocomplete("search");
			}
		}); // end ITEM CODE autocomplete
		
	//ITEM MODEL AUTOCOMPLETE
	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmModel"]').autocomplete({
		
	source: function(request, response) {
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:  ctx+'/getModel',
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

		appendString += "</span></div>";
		return $("<li class='each'>").append(appendString).appendTo(ul);
		
	};
	
	$('#bisotab > tbody > tr:eq('+rowCnt+')').find('input[name ="itmPartNo"]').focus(function(){
	
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	}); // end ITEM PARTNO autocomplete
	
	/*
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="qty"]').focus(function(){
	
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="rate"]').focus(function(){
	
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="discountAmount"]').focus(function(){
	
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="tax1"]').focus(function(){
	
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});
	*/
	
} // end boqAutocomplete fct

     
     
 
     
// Next fct in poup 
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
	  
} //End next fct in popup

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
				document.getElementById("popupItemPart").value= ui.item[0] ;
				document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
				document.getElementById("popupItemModel").value= ui.item[3] ;
				 $(this).parents("tr").find('input[name ="itemModel"]').val(ui.item[3]);
				return false;

			}
			}).autocomplete("instance")._renderItem = function(ul, item) {
				var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
				item[0] + "</span><br><span class='desc'>" +
				item[1] + "</span><span class='desc'>" +","+ 
				item[2] + "</span><span class='desc'>";
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
	$("input[name='itemModel']").each(function(i, el) {
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
				document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
				document.getElementById("popupItemPart").value= ui.item[3] ;
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
			$("input[name='itemModel']").focus(function(){
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
 	
} // End insertRowAbove fct in popup 	


 // Delete row fct
 function deleteBoqRow() {

	console.log("RowIndx" +rowindx);
	rowindx++;
	$("tr").eq(rowindx).remove();
	
	// Get Nb of rows after delete 
	var rowCount = $("#bisotab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);
	   	 
	 rowindx--;
	 
	 if (rowindx == 0 && rowCount == 0) {
	    var sumQty=0;
	    var sumTotalAt=0;
		$("#grModal").modal("hide");
		$("#bisotab > tbody > tr").each(function(i, row){
 		sumQty = sumQty + parseFloat($(this).children('td[name="qty"]').children('input').val());
		sumTotalAt = sumTotalAt + parseFloat($(this).children('td[name="totalAt"]').children('input').val());
        });
        
		$('#grtotqty').val(sumQty);
		$('#grtotamnt').val(sumTotalAt);
		     	            	 
		grtotword.value= parseFloat(grtotamnt.value) - parseFloat(grdiscamnt.value);
		groutstand.value=parseFloat(grtotword.value) - parseFloat(grpaidamnt.value);  
				
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
	        var itemMod = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
	    
	   
			    serial_no = serNum;
			    itm_model = itemMod;
			    itm_partno = itemPartnum;
			    Data.serialArray.push({serial_no:serNum, itm_model:itemMod, itm_partno:itemPartnum});
			    
	      }
	  	});

	    	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input')[0].value = JSON.stringify(Data);

//var serialModelPartnum = "";
/*		
 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){

 // Remove the row where SerialNumber is empty
	var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
		if (serNum == '') {
			$(this).parents("tr").remove();
         }
	     else {
	        var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	        var itemMod = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
	    
	    serialModelPartnum += serNum +',' +itemMod +',' +itemPartnum + ','+';';
	      }
	  	});

	    $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input').val(serialModelPartnum);
	    console.log ("serialModelPartnum rows:" + $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input').val ());
*/
} // end check serial fct


	function addRowSerial(){

		checkSerial();
		rowcountSerial =  $("#serialNoTable >tbody tr").length;
		console.log("rowcountSerial::" +rowcountSerial);
		
		var ctx = getContextPath();
	
	var markup = "<tr><td><input type='checkbox' name='record' style='position:relative;left:20px;top:10px'></td><td name='serialNumber'>"
		+"<input name='serialNumber'  class='form-control text-input' type='text' style='width:200px;position:relative;left:11px;'/></td>"
   		+ "<td name='itemModel' style='width:200px'> <input name='itemModel' id=" + "itemModel_" + rowcountSerial +" type='text' style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
        + "<td name='itemPart' style='width:200px'> <input name='itemPart' id=" + "itemPart_" + rowcountSerial +" type='text'  style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

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
				console.log(0000);
			 
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
				document.getElementById("popupItemPart").value= ui.item[0] ;
				document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
				document.getElementById("popupItemModel").value= ui.item[3] ;
				$(this).parents("tr").find('input[name ="itemModel"]').val(ui.item[3]);
										
					return false;
				}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
					item[0] + "</span><br><span class='desc'>" +
					item[1] + "</span><span class='desc'>" +","+ 
					item[2] + "</span><span class='desc'>";
					if(item[3] != '-')
						appendString += ","+item[3] + "</span><span class='desc'>";
		
					appendString += "</span></div>";
					return $("<li class='each'>").append(appendString).appendTo(ul);
  		
	                };
	     $('#itemPart_'+rowcountSerial).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
	
		});
	
	
			

				// Item Model autocomplete in 1st tab
			$('#itemModel_'+rowcountSerial ).autocomplete({
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
		    
		   }, minLength:0, maxShowItems: 40, scroll:true,

				select: function(event, ui) {
				this.value = (ui.item ? ui.item[0]  : '');
				document.getElementById("popupItemModel").value= ui.item[0] ;
				document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
				document.getElementById("popupItemPart").value= ui.item[3] ;
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
	     $('#itemModel_'+rowcountSerial ).focus(function(){
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
            rowcountSerial =  $("#serialNoTable >tbody tr").length;
            console.log("rowcountSerial after delete" +rowcountSerial);
          
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
	       				
 }) //end Select fct 
 
 
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
		document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
		document.getElementById("popupItemPart").value= ui.item[3];
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
	
	$("#popupItemModel").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});
	
 //Itemcode & name autocomplete
 $("#popupItem").autocomplete({
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
		popupItem.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		document.getElementById("popupItemModel").value= ui.item[2];
		document.getElementById("popupItemPart").value= ui.item[3];
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
					
	$("#popupItem").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});

 // Item PartNo autocomplete  in 1st tab
 $("#popupItemPart").autocomplete({
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
		document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
		document.getElementById("popupItemModel").value= ui.item[3];
		return false;
	}

	}).autocomplete("instance")._renderItem = function(ul, item) {
		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
		item[0] + "</span><br><span class='desc'>" +
		item[1] + "</span><span class='desc'>" +","+ 
		item[2] + "</span><span class='desc'>";
		if(item[3] != '-')
			appendString += ","+item[3] + "</span><span class='desc'>";

		appendString += "</span></div>";
		return $("<li class='each'>").append(appendString).appendTo(ul);
	};
									
	$("#popupItemPart").focus(function(){
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

  $('#grModal').on('show.bs.modal', function() {
	$(this).find('.modal-body').css({
	'max-height': '100%',
   });
});

  
//Reset the popup to original size after resizing 
				       
  $('#grModal').on('hidden.bs.modal', function() {
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
     }); // end  Minimize-Maximize fct 
     
     
// Calculate netRate, total, totalAt, sum of TotalAt in popup 
	
	$('#popupRate, #popupDiscount,#popupQty,#popupNetRate,#popupTax').change(function() {
		if ($('#popupDiscount').val() == '') {
	        $('#popupDiscount').val(0);
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
	        				
	    var popupNetRate = parseFloat($('#popupRate').val()) - parseFloat($('#popupDiscount').val());
	    $('#popupNetRate').val(popupNetRate);

	    var popupTotal = parseFloat($('#popupQty').val()) * (popupNetRate);
	    $('#popupTotal').val(popupTotal);

	    var popupTax = ((parseFloat($('#popupTax').val()) * popupNetRate)/100);

	    var popupTotalAt = parseFloat($('#popupQty').val()) * (popupNetRate+popupTax);
	    $('#popupTotalAt').val(popupTotalAt);
	    	
	   });
	   
//Send input values from popup to boq when any popup input change
 $('#popupItem,#popupItemModel, #popupItemPart, #popupQty,#popupRate,#popupTax,#popupDiscount').on('focusout', function() {

 	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemCode"]').children('input').val($('#popupItem').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="grItemModel"]').children('input').val($('#popupItemModel').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="grItemPartNo"]').children('input').val($('#popupItemPart').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="qty"]').children('input').val($('#popupQty').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="rate"]').children('input').val($('#popupRate').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tax1"]').children('input').val($('#popupTax').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="discountAmount"]').children('input').val($('#popupDiscount').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="netRate"]').children('input').val($('#popupNetRate').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="total"]').children('input').val($('#popupTotal').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="totalAt"]').children('input').val($('#popupTotalAt').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="poQty"]').children('input').val($('#popupPoQty').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="prQty"]').children('input').val($('#popupPrQty').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="arQty"]').children('input').val($('#popupArQty').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="cipQty"]').children('input').val($('#popupCipQty').val());
	  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="farQty"]').children('input').val($('#popupFarQty').val());

				
	   getTotalAT_SumQty();
	   amountsUpdate();
	    
      }); // end fct
      
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
	        var itemMod = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
			    serial_no = serNum;
			    itm_model = itemMod;
			    itm_partno = itemPartnum;
			    Data.serialArray.push({serial_no:serNum, itm_model:itemMod, itm_partno:itemPartnum});
		  	});


	    	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input')[0].value = JSON.stringify(Data);
	    

   /*
   var serialModelPartnum = "";
   $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){
	  
					
	      var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	      var itemMod = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
	      var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();

	     serialModelPartnum += serNum +',' +itemMod +',' +itemPartnum + ','+';';
	        
	    });

		console.log("Concatenated serialModelPartnum:" +serialModelPartnum);
	    					
	     $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input').val(serialModelPartnum);
   */
	}); // end fct      
      	   
	   
// Close fct in popup

	$("button[name='closePopup']").on("click", function(){
	  
   // Send input values from popup to BoQ table
    sendValPopupToBoq(rowindx);
 
	 
         $("#grModal").modal("hide");
         
         getTotalAT_SumQty();
	     amountsUpdate();
	    			
	 }); // end close fct
	 
	 
 function getTotalAT_SumQty(){
  var sumQty =0;
  var sumTotalAt=0;	
  
   if (grtotword.value == ''){
   	grtotword.value=0;
   }
   if (grdiscamnt.value == ''){
	grdiscamnt.value=0;
	grdiscpercent.value=0;
   }
   if (groutstand.value == ''){
	groutstand.value=0;
   }
   if (grpaidamnt.value == ''){
	grpaidamnt.value=0;
   }
   
$("#bisotab > tbody > tr").each(function(i, row){
 		sumQty = sumQty + parseFloat($(this).children('td[name="qty"]').children('input').val());
		sumTotalAt = sumTotalAt + parseFloat($(this).children('td[name="totalAt"]').children('input').val());
	
   });
		$('#grtotqty').val(sumQty);
		$('#grtotamnt').val(sumTotalAt);
		     	            	
 }
 
function amountsUpdate(){
grtotword.value= parseFloat(grtotamnt.value) - parseFloat(grdiscamnt.value);
groutstand.value=parseFloat(grtotword.value) - parseFloat(grpaidamnt.value);                
}

// Send input values from popup to boq table and close the popup using ESC 
	document.addEventListener('keydown', function(event){
	  if(event.key === "Escape"){
	
	
  // Send input values from popup to BoQ table
	    sendValPopupToBoq(rowindx);
	    
	
         $("#grModal").modal("hide");
         
         getTotalAT_SumQty();
	     amountsUpdate();
	
	}

 });// end close fct using esc
 
// Prev fct in popup
 
 	$("button[name='previousRow']").on("click", function(){
		if(rowindx > 0) {
	    	console.log("Welcome to previous");
			rowindx-- ;
	      	var PrevIndex = parseInt(rowindx);
	      	
	      	sendValBoqToPopup(PrevIndex);
	      	

	  }
	   	// Close popup on row 0 
	          else if (rowindx == 0) {
	          	 $("#grModal").modal("hide");
	          		        	 
	           }
	  });  // end prev fct
	  
// on change discount amount net toatl changed
$("#grdiscamnt").on("input", function(){
	// validate Discount Amount to be number
	if ($("#grdiscamnt").val() != '') {
		if ($. isNumeric( $("#grdiscamnt").val())== false) {
			alert('Discount Amount is  not Numeric');
			return false;
		}
		if (parseFloat(grdiscamnt.value) <= parseFloat(grtotamnt.value)) {
			grdiscpercent.value = (parseFloat(grdiscamnt.value) * 100) /parseFloat(grtotamnt.value);
			updateamounts();
		} 
		else {
			alert('Discount Amount cannot be greater than Total Amount: ' + parseFloat(grtotamnt.value));
		}
	}
					    
}); // end of on change discount amount fct 

// on change paid amount outstanding changed 
$("#grpaidamnt").on("input", function(){
	// validate Discount Amount to be number
	if ($("#grpaidamnt").val() != '') {
		if ($. isNumeric( $("#grpaidamnt").val())== false) {
			alert('Paid Amount is  not Numeric');
			return false;
		}
		if (parseFloat(grpaidamnt.value) <= parseFloat(grtotword.value)) {
			updateamounts ();
		}
		else {
			grpaidamnt.value= '0';
			alert('Paid Amount cannot be greater than Net Total Amount: ' + parseFloat(grtotword.value));
		}
	}
					    
}); // end of on chnage paid amount

//restore basic discount amount if we remove dsicount percent after save
var maindiscamont = (parseFloat(grdiscamnt.value))
				    				    
// on change Discount percent  Net total changed 
$("#grdiscpercent").on("input", function(){
	// validate Discount Percent to be number
	if ($("#grdiscpercent").val() != '') {
		if ($. isNumeric( $("#grdiscpercent").val())== false) {
			alert('Dsicount Percent is  not Numeric');
			return false;
		}
		grdiscamnt.value = ((parseFloat(grdiscpercent.value)*parseFloat(grtotamnt.value))/100);
		updateamounts();
					    
	}
					
});// end of on change Discount percent


//to select all checkbox or unselect them all from top table
$('body').on('click', '#selectAll', function  () {
	if ($(this).hasClass('allChecked')) {
    	$('input[type="checkbox"]', '#bisotab').prop('checked', false);
    }
    else {
    	$('input[type="checkbox"]', '#bisotab').prop('checked', true);
   }
   $(this).toggleClass('allChecked');
       				
})// end select all fct
     					

//remove selected rows from boq
        $(".delete-row").click(function(){
        slctDelGds=[];
        	var checked="false"; //when no checkbox is checked
        	var gds_Pk ="";
        	
        	$("#bisotab > tbody").find('input[name="record"]').each(function(){

              if($(this).is(":checked")){
            	  checked="true"; //when 1 or more checkbox is checked 
        				gds_Pk=$(this).parent().parent().children('td[name="grItemId"]').children('input').val();
        				if( gds_Pk !=0){
        					slctDelGds.push(gds_Pk);
      			           		}   
      			           		    $(this).parents("tr").remove();  
      			                } 
        			});
        	if(checked=="false"){
           	 	alert(' Select Row(s) to Delete');
    			
             	return false;
           	   	}       
           			
           					  
        	

        	
			getSumQty_totalAT();
			            
        });// end delete-row
        
  
					
 });
      
     