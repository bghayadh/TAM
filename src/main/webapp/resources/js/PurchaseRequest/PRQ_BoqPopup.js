var slctDel= [];
var rowindx =0;
var pri_Pk = "";

function openPop(element) {	
     var buttonRowIndx = $(element).closest("tr");
     rowindx = (buttonRowIndx[0].rowIndex - 1);
    
    //Send input values from Boq table  to popup
	sendValBoqToPopup(rowindx);
    $("#preqModal").modal("show");

}// end open popup fct

function sendValBoqToPopup(indxRow){
      
	$('#popupItem').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prItemCode"]').children('input').val());
	$('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prItemModel"]').children('input').val()); 
	$('#popupItemPart').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prItemPartNo"]').children('input').val());
	$('#popupBarcode').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prBarCode"]').children('input').val());
	$('#popupQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prQty"]').children('input').val());
	$('#popupCat1').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat1"]').children('input').val());
	$('#popupCat2').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat2"]').children('input').val());
	$('#popupCat3').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat3"]').children('input').val());
	$('#popupCat4').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat4"]').children('input').val());
	$('#popupSeq').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prSequ"]').children('input').val());
	$('#popupRate').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prRate"]').children('input').val());
	$('#popupTax') .val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prTax1"]').children('input').val());
	$('#popupDiscount') .val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prDiscountAmount"]').children('input').val());
	$('#popupNetrate').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prNetRate"]').children('input').val());
	$('#popupTotal').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prTotal"]').children('input').val());
	$('#popupTotalat').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prTotalAt"]').children('input').val());
	$('#popupPoQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poQty"]').children('input').val());
	$('#popupGrQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grQty"]').children('input').val());
	$('#popupArQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="arQty"]').children('input').val());
	$('#popupCipQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cipQty"]').children('input').val());
	$('#popupFarQty').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="farQty"]').children('input').val());
							 

	var element = document.getElementById("popupNb");
    	element.innerHTML = "Item # " +(indxRow+1);

}

function sendValPopupToBoq(indxRow){

      
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prItemCode"]').children('input').val($('#popupItem').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prItemModel"]').children('input').val($('#popupItemModel').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prItemPartNo"]').children('input').val($('#popupItemPart').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prBarCode"]').children('input').val($('#popupBarcode').val());	
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prQty"]').children('input').val($('#popupQty').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat1"]').children('input').val($('#popupCat1').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat2"]').children('input').val($('#popupCat2').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat3"]').children('input').val($('#popupCat3').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prCat4"]').children('input').val($('#popupCat4').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prSequ"]').children('input').val($('#popupSeq').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prRate"]').children('input').val($('#popupRate').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prTax1"]').children('input').val($('#popupTax').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prDiscountAmount"]').children('input').val($('#popupDiscount').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prNetRate"]').children('input').val($('#popupNetrate').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prTotal"]').children('input').val($('#popupTotal').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="prTotalAt"]').children('input').val($('#popupTotalat').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="poQty"]').children('input').val($('#popupPoQty').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="grQty"]').children('input').val($('#popupGrQty').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="arQty"]').children('input').val($('#popupArQty').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cipQty"]').children('input').val($('#popupCipQty').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="farQty"]').children('input').val($('#popupFarQty').val());


}
function boqRowInsrt(newRowCount, itmName, model, partNum, barcode){
	
	var markup = "<tr><td><input type='checkbox' name='record'><span class='dotStatus' id='dotStatus'></span><button type='button' href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td><td name='prItemCode'>"
				+"<input name='itmCode' type='text' id = 'itmCode"+newRowCount+"' value='"+ itmName +"' style='width:300px;' class='form-control text-input'/></td>"
				+"<td name='prItemModel'>"
				+"<input name='itmModel' type='text' id = 'itmModel"+newRowCount+"' value='"+ model +"' style='width:200px;' class='form-control text-input'/></td>"
				+"<td name='prItemPartNo'>"
				+"<input name='itmPartNo' type='text' id = 'itmPartNo"+newRowCount+"' value='"+ partNum +"' style='width:200px;' class='form-control text-input'/></td>"
				+"<td hidden name='prBarCode'>"
				+"<input name='barcode' type='text' value ='"+ barcode +"' style='width:200px;' class='form-control text-input'/></td>"
				+"<td name='prQty'><input type='text' value= 1 style='width:200px;' class='form-control text-input'></td>"
				+"<td name='prRate'><input type='text' value= 0 style='width:200px;' class='form-control text-input'></td>"
				+"<td name='prDiscountAmount'><input type='text'value= 0 style='width:200px;' class='form-control text-input'></td>"
				+"<td name='prTax1'><input type='text' value=0 style='width:200px;' class='form-control text-input'></td>"
				+"<td name='prNetRate'><input type='text' readonly value=0 style='width:200px;' class='form-control text-input'></td>"
				+"<td name='prTotal'><input type='text' readonly value=0 style='width:200px;' class='form-control text-input'></td>"
				+"<td name='prTotalAt'><input type='text' readonly value=0 style='width:200px;' class='form-control text-input'></td>"
				+"<td name='poQty'><input type='text' readonly style='width:200px;' class='form-control text-input'></td>"
				+"<td name='grQty'><input type='text' readonly style='width:200px;' class='form-control text-input'></td>"
				+"<td name='arQty'><input type='text' readonly style='width:200px;' class='form-control text-input'></td>"
				+"<td name='cipQty'><input type='text' readonly style='width:200px;' class='form-control text-input'></td>"
				+"<td name='farQty'><input type='text' readonly value='0' style='width:200px;' class='form-control text-input'></td>"
				+"<td name='prqItemId'><input type='text' readonly value= 0 style='width:200px;' class='form-control text-input'><input type='text' name='prqPoStatus' hidden value= '0'></td></tr>";
return markup;
}
function addNewRow(position){ 
	////////HTML row drawing
		var markup = boqRowInsrt('', '', '', '', '');
		
		if (position == "next"){
			RowCount =  $("#bisotab >tbody tr").length;
			$("#bisotab > tbody").append(markup);
			$("#bisotab > tbody tr:eq("+RowCount+")").find("td[name=prItemCode]").children('input').attr('id', 'itmCode'+RowCount);
			$("#bisotab > tbody tr:eq("+RowCount+")").find("td[name=prItemModel]").children('input').attr('id', 'itmModel'+RowCount);
			$("#bisotab > tbody tr:eq("+RowCount+")").find("td[name=prItemPartNo]").children('input').attr('id', 'itmPartNo'+RowCount);
			boqAutocomplete(RowCount,"bisotab");
			$('table#bisotab tr:last td:nth-child(2) input').focus();				
			boqInputsListenerWhileAdding((RowCount+1));
     	}
     	     	
     	else if (position =="below"){
			var indx = parseInt(rowindx+1);
			$("#bisotab > tbody tr").eq(rowindx).after(markup);
			$("#bisotab > tbody tr:eq("+indx+")").find("td[name=prItemCode]").children('input').attr('id', 'itmCode'+indx);	
			$("#bisotab > tbody tr:eq("+indx+")").find("td[name=prItemModel]").children('input').attr('id', 'itmModel'+indx);
			$("#bisotab > tbody tr:eq("+indx+")").find("td[name=prItemPartNo]").children('input').attr('id', 'itmPartNo'+indx);
			boqAutocomplete(indx,"bisotab");
			$('table#bisotab tr:eq('+indx+') td:nth-child(2) input').focus();				
			boqInputsListenerWhileAdding((indx+1));	
		}
		else if (position =="above"){
			$("#bisotab > tbody tr").eq(rowindx).before(markup);
			$("#bisotab > tbody tr:eq("+rowindx+")").find("td[name=prItemCode]").children('input').attr('id', 'itmCode'+rowindx);	
			$("#bisotab > tbody tr:eq("+rowindx+")").find("td[name=prItemModel]").children('input').attr('id', 'itmModel'+rowindx);
			$("#bisotab > tbody tr:eq("+rowindx+")").find("td[name=prItemPartNo]").children('input').attr('id', 'itmPartNo'+rowindx);
			boqAutocomplete(rowindx,"bisotab"); 
			$('table#bisotab tr:eq('+rowindx+') td:nth-child(2) input').focus();
			boqInputsListenerWhileAdding(rowindx+1);	
		}
		getSumQty_totalAT();		
					   
} // end add new row  	

function calculateParam(){
	    var netrate= parseFloat($(this).parent().parent().children('td[name="prRate"]').children('input').val()) - parseFloat($(this).parent().parent().children('td[name="prDiscountAmount"]').children('input').val());
		var tax1 = ((parseFloat($(this).parent().parent().children('td[name="prTax1"]').children('input').val()) * netrate)/100);
		var amount = parseFloat($(this).parent().parent().children('td[name="prQty"]').children('input').val()) * (netrate);
		var amountAT = parseFloat($(this).parent().parent().children('td[name="prQty"]').children('input').val()) * (netrate+tax1);
		$(this).parent().parent().children('td[name="prNetRate"]').children('input').val(netrate);
		$(this).parent().parent().children('td[name="prTotal"]').children('input').val(amount);
		$(this).parent().parent().children('td[name="prTotalAt"]').children('input').val(amountAT);
		getSumQty_totalAT();
}
// to check if the inserted barcode has a similar boq result , if exist , qty ++ .
function checkItemCodeBoqRow(bc){
		$("#bisotab > tbody").find('td[name="prItemCode"]').each(function(){	
		var index =  $(this).parent().closest("tr").index();
		var itemCode = 	($("#bisotab >tbody").find("tr").eq(index).find('td[name="prItemCode"]').children('input').val().split(":"))[0];
		if (itemCode == bc){
		var qty = $("#bisotab >tbody").find("tr").eq(index).find('td[name="prQty"]').children('input').val();
		qty++;
		$("#bisotab >tbody").find("tr").eq(index).find('td[name="prQty"]').children('input').val(qty);
		//$('#barcode').val("");

							}
							 });
}

function getSumQty_totalAT (){
 	var sumQntity = 0;
 	var sumtotAT = 0;
 	var sumDiscount = 0;
 	if (prtotword.value == ''){
 		prtotword.value=0;
 	}
 	if (prdiscamnt.value == ''){
 		prdiscamnt.value=0;
 		prdiscpercent.value=0;
 	}
 	if (proutstand.value == ''){
 		proutstand.value=0;
 	}
 	if (prpaidamnt.value == ''){
 		prpaidamnt.value=0;
 	}
         		 
	$("#bisotab > tbody > tr").each(function(i, row){
		sumQntity = sumQntity + parseFloat($(this).children('td[name="prQty"]').children('input').val());
		sumtotAT = sumtotAT + parseFloat($(this).children('td[name="prTotalAt"]').children('input').val());	
		sumDiscount = sumDiscount + parseFloat($(this).children('td[name="prDiscountAmount"]').children('input').val());	
	 });
		     
	 $('#prtotqty').val(sumQntity);
	 $('#prtotamnt').val(sumtotAT);
	 //$('#prdiscamnt').val(sumDiscount);
	 
	 updateAmounts ();
                        
};

function updateAmounts () {
	prtotword.value= parseFloat(prtotamnt.value) - parseFloat(prdiscamnt.value);
	proutstand.value=parseFloat(prtotword.value) - parseFloat(prpaidamnt.value);
}
// function to put listener on change while adding a row by (bisotab, next, below, above, barcode adder, sequance adder).
function boqInputsListenerWhileAdding(rowIndex){

	$('#bisotab tr:eq('+rowIndex+') td input').change(function() { // td:eq(4),td:eq(5),td:eq(6),td:eq(7) input
					
					var column_name = $(this).parent().attr('name');
					var cell = $(this).val();
					
				    if ((column_name == 'prQty') ) {
				          // validate Qty
							 if (($. isNumeric(cell))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
							 else {
								 calculateParam.bind(this)();
							 }
					}
					if ((column_name == 'prRate') ) {
				      // validate Rate
						 if (($. isNumeric(cell ))== false) {
						 alert('Rate is  not Numeric');
						 this.focus();
						 return false;} 
						 else {
							 calculateParam.bind(this)();
			
						 }
				}
				  if ((column_name == 'prDiscountAmount') ) {
				      // validate Discount
						 if (($. isNumeric(cell ))== false) {
						 alert('Discount is  not Numeric');
						 this.focus();
						 return false;}
						 else {
							 calculateParam.bind(this)();
			
						 }
				}
				if ((column_name == 'prTax1') ) {
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
//calculate footer parameters
function calcFooterDataOnChangeListener(){
				
				$('#bisotab tr td input').change(function() {
					
					var column_name = $(this).parent().attr('name');
					var cell = $(this).val();
					
				    if ((column_name == 'prQty') ) {
				          // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
							 else {
								 calculateParam.bind(this)();
							 }
					}
					else if ((column_name == 'prRate') ) {
				      // validate Rate
						 if (($. isNumeric(cell ))== false) {
						 alert('Rate is  not Numeric');
						 this.focus();
						 return false;} 
						 else {
							 calculateParam.bind(this)();
			
						 }
				}
					else if ((column_name == 'prDiscountAmount') ) {
				      // validate Discount
						 if (($. isNumeric(cell ))== false) {
						 alert('Discount is  not Numeric');
						 this.focus();
						 return false;}
						 else {
							 calculateParam.bind(this)();
						 }
				}
					else if ((column_name == 'prTax1') ) {
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
//////////////////////////////////

					 
function boqAutocomplete(rowCnt,tableIndx){

	var ctx=getContextPath();
	var tableID=tableIndx;
	
	//ITEM CODE autocomplete
	$('#itmCode'+rowCnt).autocomplete({
	source: function(request, response) {
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
					console.log("Success here");
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
	  	
	  	$('#itmCode'+rowCnt).focus(function(){
	  		if (this.value == ""){
	  			$(this).autocomplete("search");
			}
		}); // end ITEM CODE autocomplete
		
	//ITEM MODEL AUTOCOMPLETE
	$('#itmModel'+rowCnt).autocomplete({
				  
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
		
		$('#itmModel'+rowCnt).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}
		}); // end ITEM MODEL autocomplete
		
	//ITEM PARTNO autocomplete
	$('#itmPartNo'+rowCnt).autocomplete({
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
	
	$('#itmPartNo'+rowCnt).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	}); // end ITEM PARTNO autocomplete
	
	
} // end boqAutocomplete fct
//////////////////////////
//function to  get selected rows for save
				function getselectedrows () {
					dict = [];
					
					$("#bisotab > tbody").find('input[name="record"]').each(function(){
						    dict.push({
						    	 "prItemCode" : $(this).parent().parent().children('td[name="prItemCode"]').children('input').val(),
						    	 "prItemModel" : $(this).parent().parent().children('td[name="prItemModel"]').children('input').val(),
						    	 "prItemPartNo" : $(this).parent().parent().children('td[name="prItemPartNo"]').children('input').val(),
						    	 "prBarCode" : $(this).parent().parent().children('td[name="prBarCode"]').children('input').val(),
								 "prQty" : $(this).parent().parent().children('td[name="prQty"]').children('input').val(),
								 "prRate" : $(this).parent().parent().children('td[name="prRate"]').children('input').val(),
								 "prDiscountAmount" : $(this).parent().parent().children('td[name="prDiscountAmount"]').children('input').val(),
								 "prTax1" : $(this).parent().parent().children('td[name="prTax1"]').children('input').val(),
								 "prNetRate" : $(this).parent().parent().children('td[name="prNetRate"]').children('input').val(),
								 "prTotal" : $(this).parent().parent().children('td[name="prTotal"]').children('input').val(),
								 "prTotalAt" : $(this).parent().parent().children('td[name="prTotalAt"]').children('input').val(),
								 "poQty" : $(this).parent().parent().children('td[name="poQty"]').children('input').val(),
								 "grQty" : $(this).parent().parent().children('td[name="grQty"]').children('input').val(),
								"arQty" : $(this).parent().parent().children('td[name="arQty"]').children('input').val(),
								 "cipQty" : $(this).parent().parent().children('td[name="cipQty"]').children('input').val(),
								 "farQty" : $(this).parent().parent().children('td[name="farQty"]').children('input').val(),
								 "prqItemId" : $(this).parent().parent().children('td[name="prqItemId"]').children('input').val(),
								 "prqPoStatus" : $(this).parent().parent().children('td[name="prqItemId"]').children("input[name='prqPoStatus']").val()
							    });
					
		             
					});
		              
				}//end of selectedrows
//////////////////////////////////////////////////

			function checkedDataOnAction(){
				 // validate Ordered date cannot be null
				 val =$("#prorderdate").val();
			     val1 = Date.parse(val);
			   
			     if (isNaN(val1) == true) {
			          alert(' Ordered Date is not valid');
			          return false;
			        
			        }
			        
			         // validate Delivered Date cannot be null
				 val =$("#prdeliverydate").val();
			     val1 = Date.parse(val);
			  
			     if (isNaN(val1) == true) {
			          alert(' Delivered Date is not valid');
										  
				   return false;
			           
			        }
			     if ($("#prtotamnt").val() == '')  { prtotamnt.value = 0;  }
				  if ($("#prdiscamnt").val() == '')  { prdiscamnt.value = 0; }
				  if ($("#prdiscpercent").val() == '') { prdiscpercent.value = 0; }
				  if ($("#prpaidamnt").val() == '') { prpaidamnt.value = 0; }
				  if ($("#proutstand").val() == '') { proutstand.value = 0; }
				  if ($("#prtotqty").val() == '') { prtotqty.value = 0; }
				  if ($("#prtotword").val() == '') { prtotword.value = 0; }
				  
				  
				 // validate Total Amount to be number 
				 if (($. isNumeric( $("#prtotamnt").val()))== false) {
				 alert('Total Amount is  not Numeric');
				 return false;}
				 
				  // validate Discount Amount to be number
				 if (($. isNumeric( $("#prdiscamnt").val()))== false) {
				 alert('Discount Amount is  not Numeric');
				 return false;}
				 
				  // validate Discount pecent to be number
				 if (($. isNumeric( $("#prdiscpercent").val()))== false) {
				 alert('Discount pecent is  not Numeric');
				 return false;}
				 
				  // validate paid Amount to be number
				 if (($. isNumeric( $("#prpaidamnt").val()))== false) {
				 alert('paid Amount is  not Numeric');
				 return false;}
				 
				  // validate Outstanding to be number
				 if (($. isNumeric( $("#proutstand").val()))== false) {
				 alert('Outstanding is  not Numeric');
				 return false;}
				 
				  // validate Qty to be number
				 if (($. isNumeric( $("#prtotqty").val()))== false) {
				 alert('Qty is  not Numeric');
				 return false;}
				 
				 // validate nTotal to be number
				 if (($. isNumeric( $("#prtotword").val()))== false) {
				 alert('Net Total is  not Numeric');
				 return false;}
				 
				 
				}

///////////////////////////////////////

  
 function getAllBoqItemCodes()
 {
 	itemCodeBoqArray = [];
 	var ItemCodeValue = "";
$("#bisotab > tbody").find('td[name="prItemCode"]').each(function(){
 		if($(this).children('input').val() == ""){
 			ItemCodeValue = "empty";
		itemCodeBoqArray.push(ItemCodeValue);
		}
 		else{
 			ItemCodeValue = ($(this).children('input').val()).split(":");
 		itemCodeBoqArray.push(ItemCodeValue[0]);
	}
 	});
 	return itemCodeBoqArray;   
 }

 function checkifItemCodeExist(itemCode)
 {
	var exist = "";
	
	$("#bisotab > tbody").find('td[name="prItemCode"]').each(function(){
		
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
   
 

// Insert row below fct

 function insertRowBelow(){
		addNewRow("below");
		
		rowindx++;
		var belowIndex = parseInt(rowindx);   
		
		sendValBoqToPopup(belowIndex);
		 
		
   } 
// End insertRowBelow fct in popup
   
// Insert Row Above fct

 function insertRowAbove(){
 	addNewRow("above");
 	sendValBoqToPopup(rowindx);        
   }
// End insertRowAbove fct in popup
   
// Delete row fct
 function deleteBoqRow() {
	pri_Pk = $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="prqItemId"]').children('input').val();
	if( pri_Pk !=0){
		slctDel.push($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="prqItemId"]').children('input').val());
	}
	rowindx++;
	$("tr").eq(rowindx).remove();
/*	
	var sumQty=0;
	var sumTotalAt=0;
	$("#bisotab > tbody > tr").each(function(i, row) {
		sumQty = sumQty + parseFloat($(this).children('td[name="prQty"]').children('input').val());
		console.log("sumQty is:"+sumQty);
		sumTotalAt = sumTotalAt + parseFloat($(this).children('td[name="prTotalAt"]').children('input').val());
		console.log("sumTotalAt is:"+sumTotalAt);
	});
	$('#prtotqty').val(sumQty);
	$('#prtotamnt').val(sumTotalAt);
	prtotword.value= parseFloat(prtotamnt.value) - parseFloat(prdiscamnt.value);
	proutstand.value=parseFloat(prtotword.value) - parseFloat(prpaidamnt.value);
*/
		
		
	// Get Nb of rows after delete 
	var rowCount = $("#bisotab >tbody tr").length;	   	 
	rowindx--;
	 
	if (rowindx == 0 && rowCount == 0) {
		$("#preqModal").modal("hide");		
	}
	else if(rowindx >= 0 && rowindx < rowCount) {
		sendValBoqToPopup(rowindx);
	}
	// Show previous record 
	else if (rowindx >= rowCount) {
		rowindx--;
		sendValBoqToPopup(rowindx);
	}
	getSumQty_totalAT(); 
}// End Delete fct  
  
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}      
   

$(function(){
var ctx = getContextPath();
	
 // Item model autocomplete
 $("#popupItemModel").autocomplete({
	source: function(request, response) {

				
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getModel',
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
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0]  : '');
		document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
		document.getElementById("popupItemPart").value= ui.item[3];
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
					
	// Item Code&Name autocomplete 
$("#popupItem").autocomplete({
	source: function(request, response) {
				

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
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
		popupItem.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		document.getElementById("popupItemModel").value= ui.item[2];
		document.getElementById("popupItemPart").value= ui.item[3];
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
					
	$("#popupItem").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});

	// ItemPartNo autocomplete
	  			    
$("#popupItemPart").autocomplete({
	source: function(request, response) {

	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getPartNo',
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
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("popupItem").value= ui.item[1] + ":" + ui.item[2] ;
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

   $('#preqModal').on('show.bs.modal', function() {
	  $(this).find('.modal-body').css({
	  'max-height': '100%',
    });
  });
 
//Reset the popup to original size after resizing 
			       
   $('#preqModal').on('hidden.bs.modal', function() {
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
   });  // end  Minimize-Maximize fct
   
   
 //Send input values from popup to boq when any popup input change
 $('#popupItem,#popupItemModel, #popupItemPart, #popupQty,#popupRate,#popupTax,#popupDiscount,#popupBarcode,#popupSeq,#popupCat1,#popupCat2,#popupCat3,#popupCat4').on('focusout', function() {
      
   sendValPopupToBoq(rowindx);
   getTotalAT_SumQty();
   amountsUpdate();  
      });
   
// Close popup function  				
  $("button[name='closePopup']").on("click", function(){
	 
	
  //  Send input values from popup to boq table using close button 
  	sendValPopupToBoq(rowindx);
	    					
	  $("#preqModal").modal("hide");
	  
	   getTotalAT_SumQty();
	   amountsUpdate();
		 	
	});   // end close fct
	
// Close popup using ESC
	document.addEventListener('keydown', function(event){
	  if(event.key === "Escape"){
		if($("#preqModal").hasClass("show")){
			// Send input values from popup to boq table and close the popup using ESC 
			sendValPopupToBoq(rowindx);
			$("#preqModal").modal("hide");
			 getTotalAT_SumQty();
		     amountsUpdate();
		}
	   }
   });// end close fct using esc
   
   
 function getTotalAT_SumQty(){
  var sumQty =0;
  var sumTotalAt=0;	

	if (prtotword.value == ''){
		prtotword.value=0;
	}
    if (prdiscamnt.value == ''){
	    prdiscamnt.value=0;
	    prdiscpercent.value=0;
	}
	if (proutstand.value == ''){
	    proutstand.value=0;
	 }
    if (prpaidamnt.value == ''){
	    prpaidamnt.value=0;
	}
 
  
 $("#bisotab > tbody > tr").each(function(i, row){
	sumQty = sumQty + parseFloat($(this).children('td[name="prQty"]').children('input').val());
	sumTotalAt = sumTotalAt + parseFloat($(this).children('td[name="prTotalAt"]').children('input').val());
		});
	 $('#prtotqty').val(sumQty);
	 $('#prtotamnt').val(sumTotalAt);
		     	            	
 } 
 
 function amountsUpdate(){
  prtotword.value= parseFloat(prtotamnt.value) - parseFloat(prdiscamnt.value);
  proutstand.value=parseFloat(prtotword.value) - parseFloat(prpaidamnt.value);	                
} 
   				
						
// Calculate netRate, total, totalAt, sum of TotalAt in popup 
  $('#popupRate, #popupDiscount,#popupQty,#popupNetrate,#popupTax').on('change  ', function()  {
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
	  if ($('#popupTotalat').val() == '') {
	      $('#popupTotalat').val(0);
      }
      var popupNetRate = parseFloat($('#popupRate').val()) - parseFloat($('#popupDiscount').val());
	  $('#popupNetrate').val(popupNetRate);

	   var popupTotal = parseFloat($('#popupQty').val()) * (popupNetRate);
	   $('#popupTotal').val(popupTotal);

	   var popupTax = ((parseFloat($('#popupTax').val()) * popupNetRate)/100);

	   var popupTotalAt = parseFloat($('#popupQty').val()) * (popupNetRate+popupTax);
	   $('#popupTotalat').val(popupTotalAt);
	    
	});

// Prev fct in popup
 $("button[name='previousRow']").on("click", function(){
	
	if(rowindx > 0) {
		rowindx-- ;
	    var PrevIndex = parseInt(rowindx);
	    sendValBoqToPopup(PrevIndex);

	   
	 }
   
   // Close popup on row 0 (end of prev)
	 else if (rowindx == 0) {
	     $("#preqModal").modal("hide");
	    }
  });   // end prev fct in popup
 
 
// on change discount amount net toatl chnaged
$("#prdiscamnt").on("input", function(){
		// validate Discount Amount to be number
		if ($("#prdiscamnt").val() != '') {
			if ($. isNumeric( $("#prdiscamnt").val())== false) {
				alert('Discount Amount is  not Numeric');
				return false;}
		if (parseFloat(prdiscamnt.value) <= parseFloat(prtotamnt.value)) {
			prdiscpercent.value = (parseFloat(prdiscamnt.value) * 100) /parseFloat(prtotamnt.value);
			updateAmounts();
		}
		 else {
		 	alert('Discount Amount cannot be greater than Total Amount: ' + parseFloat(prtotamnt.value));
		}
	}
}); // end of on chnage discount amount

// on change paid amount outstanding changed 
$("#prpaidamnt").on("input", function(){
	// validate Discount Amount to be number
	if ($("#prpaidamnt").val() != '') {
		if ($. isNumeric( $("#prpaidamnt").val())== false) {
			alert('Paid Amount is  not Numeric');
			return false;
		}
		if (parseFloat(prpaidamnt.value) <= parseFloat(prtotword.value)) {
			updateAmounts ();
		} 
		else {
			prpaidamnt.value= '0';
			alert('Paid Amount cannot be greater than Net Total Amount: ' + parseFloat(prtotword.value));
		}
	}
}); // end of on chnage paid amount

			    
	 // on change Discount percent  Net total changed 
	      $("#prdiscpercent").on("input", function(){
	        	          	        		
        	// validate Discount Percent to be number
        	if ($("#prdiscpercent").val() != '') {
				 if ($. isNumeric( $("#prdiscpercent").val())== false) {
				 alert('Dsicount Percent is  not Numeric');
				 return false;}
			     prdiscamnt.value = ((parseFloat(prdiscpercent.value)*parseFloat(prtotamnt.value))/100);
			     updateAmounts ();
				}
		});  // end of on change Discount percent


//to select all cjeckbox or unselect them all from top table
		$('body').on('click', '#selectAll', function  () {
					   
      		if ($(this).hasClass('allChecked')) {
         		$('input[type="checkbox"]', '#bisotab').prop('checked', false);
      		}
      		 else {
      		 	$('input[type="checkbox"]', '#bisotab').prop('checked', true);
       		}
       		$(this).toggleClass('allChecked');
})

	
//remove selected rows from boq
$(".delete-row").click(function(){
	var checked="false"; //when no checkbox is checked  
	$("#bisotab > tbody").find('input[name="record"]').each(function(){
		if($(this).is(":checked")) { 
			checked="true"; //when 1 or more checkbox is checked
			pri_Pk=$(this).parent().parent().children('td[name="prqItemId"]').children('input').val();
			if (pri_Pk !=0) {
				slctDel.push(pri_Pk);
			}    
   			$(this).parents("tr").remove();  
	    }   //end of checked box in boq for delete
	});
	if(checked=="false") {					  
		alert(' Select Row(s) to Delete');
		return false;
	}
	getSumQty_totalAT();
});// end delete row
        
// auto complete for barcode in popup.
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
		$("#popupItem").val(ui.item[1] + ":" + ui.item[2]);
		$("#popupItemModel").val(ui.item[3]);
		$("#popupItemPart").val(ui.item[4]);

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
 
//ss barcode pop up selection
		
	// categories auto complete in popup
	//cat auto complete
	
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
			  "cat4Input":cat4,
		},
		
		dataType: "json",
		success: function (data) {
			 if (data != null) {
			 	response(data.ListCategory);
			 	Cat = data.ListCategory;
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
				$("#popupItem").val(data.item[0] + ":" + data.item[1]) ;
				$("#popupItemModel").val(data.item[3]);
				$("#popupItemPart").val(data.item[4]);

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

//End autocomplete in popup									

  });  