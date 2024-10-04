var rowindx =0;
 var rowcountSerial =0;
 var serialRowIndex=0;
 var slctDelOrd =[];
var allDelSerials=[]; // to store all deleted serial numbers from different popups
var ctx = getContextPath();

 //Open popup fct 
 function openPop(element) {
 	
	var buttonRowIndx = $(element).closest("tr");
	rowindx = (buttonRowIndx[0].rowIndex - 1);
	     
	//Send input values from Boq table  to popup
	sendValBoqToPopup(rowindx);
	$("#poModal").modal("show");
  
	autoCompleteForPortMapping("","","popupWareID","popupLocID","popupLocName","popupLocType","popupCableID","popupCableName","popupCableType","popupTXStrandNb","popupRXStrandNb","popupTXStrandColor","popupRXStrandColor","popupTXTubeNb","popupRXTubeNb","popupTXTubeColor","popupRXTubeColor");

}// end open popup fct
     
function sendValBoqToPopup(indxRow){
	
	//Send input values from Boq table  to popup
	$('#popupportMappingID').val($("#bisotab >tbody").find("tr").eq(indxRow).attr('id'));
	$('#popupportRecordType') .val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="recordType"]').children('input').val()); 
	$('#popupportRecordType').prop('readonly', true);
	
	$('#popupMacAddress').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="MACAddress"]').children('input').val());
	var isMacReadonly = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="MACAddress"]').children('input').is('[readonly]');
	$('#popupMacAddress').prop('readonly', isMacReadonly);
	
	
	$('#popupSerialNb').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNb"]').children('input').val());
	var isSNReadonly = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNb"]').children('input').is('[readonly]');
	$('#popupSerialNb').prop('readonly', isSNReadonly);
	
	$('#popupPortAddress').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portAddress"]').children('input').val());
	var isPortAddReadonly = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portAddress"]').children('input').is('[readonly]');
	$('#popupPortAddress').prop('readonly', isPortAddReadonly);
	
	$('#popupPortStatus').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portStatus"]').children('input').val());
	var isPortStatusReadonly = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portStatus"]').children('input').is('[readonly]');
	
	$('#popupRefPortStatus').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="refStatus"]').children('select').val());
	
	$('#popupPortStatus').prop('readonly', isPortStatusReadonly);
	$('#popupRefPortStatus').prop('disabled', isPortStatusReadonly);
	
	$('#popupPortNb').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portNb"]').children('input').val());
	$('#popupLocType').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationType"]').children('select').val());
	$('#popupLocID').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationID"]').children('input').val());
	$('#popupLocName').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationName"]').children('input').val());
	$('#popupWareID').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="wareID"]').children('input').val());
	var isWareIDReadonly = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="wareID"]').children('input').is('[readonly]');
	$('#popupWareID').prop('readonly', isWareIDReadonly);
	
	$('#popupCableType').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableType"]').children('select').val());
	$('#popupTXStrandNb').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandNb"]').children('input').val());
	var isTxSTNBReadonly = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandNb"]').children('input').is('[readonly]');
	
	$('#popupTXStrandColor').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandColor"]').children('select').val());
	$('#popupRXStrandNb').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandNb"]').children('input').val());
	$('#popupRXStrandColor').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandColor"]').children('select').val());
	$('#popupTXTubeNb').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeNb"]').children('input').val());
	$('#popupTXTubeColor').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeColor"]').children('select').val());
	$('#popupRXTubeNb').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeNb"]').children('input').val());
	$('#popupRXTubeColor').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeColor"]').children('select').val());
	$('#popupCableID').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableID"]').children('input').val());
	$('#popupCableName').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableName"]').children('input').val());
	$('#popupCableLength').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableLength"]').children('input').val());
	//since strand and tube fields have the same status  
	$('#popupCableID').prop('readonly', isTxSTNBReadonly);
	$('#popupTXStrandNb').prop('readonly', isTxSTNBReadonly);
	$('#popupRXStrandNb').prop('readonly', isTxSTNBReadonly);
	$('#popupTXTubeNb').prop('readonly', isTxSTNBReadonly);
	$('#popupRXTubeNb').prop('readonly', isTxSTNBReadonly);
	$('#popupTXStrandColor').prop('disabled', isTxSTNBReadonly);
	$('#popupRXStrandColor').prop('disabled', isTxSTNBReadonly);
	$('#popupTXTubeColor').prop('disabled', isTxSTNBReadonly);
	$('#popupRXTubeColor').prop('disabled', isTxSTNBReadonly);
	
	tubeStrandPortMappingSetColor("popupTXStrandColor","popupTXStrandNb");
    tubeStrandPortMappingSetColor("popupRXStrandColor","popupRXStrandNb");
    tubeStrandPortMappingSetColor("popupTXTubeColor","popupTXTubeNb");
    tubeStrandPortMappingSetColor("popupRXTubeColor","popupRXTubeNb");
								 
	
		var element = document.getElementById("popupNb");
    	element.innerHTML = "port # " +(indxRow+1);
  		  
    		
} //end sendValBoqToPopup fct

function sendValPopupToBoq(indxRow){
	
// Send input values from popup to boq table
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="recordType"]').children('input').val($('#popupportRecordType').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="MACAddress"]').children('input').val($('#popupMacAddress').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNb"]').children('input').val($('#popupSerialNb').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portAddress"]').children('input').val($('#popupPortAddress').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portStatus"]').children('input').val($('#popupPortStatus').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="refStatus"]').children('select').val($('#popupRefPortStatus').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portNb"]').children('input').val($('#popupPortNb').val());
	 
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationType"]').children('select').val($('#popupLocType').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationID"]').children('input').val($('#popupLocID').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationName"]').children('input').val($('#popupLocName').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="wareID"]').children('input').val($('#popupWareID').val());
	var isWareIDReadonly = $("#popupWareID").is('[readonly]');
	$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="wareID"]').children('input').prop('readonly', isWareIDReadonly);
		
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableType"]').children('select').val($('#popupCableType').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandNb"]').children('input').val($('#popupTXStrandNb').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandColor"]').children('select').val($('#popupTXStrandColor').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandNb"]').children('input').val($('#popupRXStrandNb').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandColor"]').children('select').val($('#popupRXStrandColor').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeNb"]').children('input').val($('#popupTXTubeNb').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeColor"]').children('select').val($('#popupTXTubeColor').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeNb"]').children('input').val($('#popupRXTubeNb').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeColor"]').children('select').val($('#popupRXTubeColor').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableID"]').children('input').val($('#popupCableID').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableName"]').children('input').val($('#popupCableName').val());
	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableLength"]').children('input').val($('#popupCableLength').val());
	 
	 var isTxStrandNbReadonly = $("#popupTXStrandNb").is('[readonly]');
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandNb"]').children('input').prop('readonly', isTxStrandNbReadonly);
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandNb"]').children('input').prop('readonly', isTxStrandNbReadonly);
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeNb"]').children('input').prop('readonly', isTxStrandNbReadonly);
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeNb"]').children('input').prop('readonly', isTxStrandNbReadonly);
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableID"]').children('input').prop('readonly', isTxStrandNbReadonly);
		
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandColor"]').children('select').prop('disabled', isTxStrandNbReadonly);
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandColor"]').children('select').prop('disabled', isTxStrandNbReadonly);
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeColor"]').children('select').prop('disabled', isTxStrandNbReadonly);
		$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeColor"]').children('select').prop('disabled', isTxStrandNbReadonly);
	 
		
		var idd = $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="MACAddress"]').children('input').attr('id');
		 var colorindex =idd.replace('MACAddress','');
		tubeStrandPortMappingSetColor("txStrandColor"+colorindex,"txStrandNb"+colorindex);
	    tubeStrandPortMappingSetColor("rxStrandColor"+colorindex,"rxStrandNb"+colorindex);
	    tubeStrandPortMappingSetColor("txTubeColor"+colorindex,"txTubeNb"+colorindex);
	    tubeStrandPortMappingSetColor("rxTubeColor"+colorindex,"rxTubeNb"+colorindex);
									 
	
 			
}

 


function htmlBOQRowInsertion(name){
	var locationTypeOptions= 
	 "<option value=''></option>"
	+"<option value='Customer'>Customer</option>"
	+"<option value='Site'>Site</option>";


var cableTypeOptions =
"<option value=''></option>"
+"<option value='Indoor'>Indoor</option>"
+"<option value='Outdoor'>Outdoor</option>";

var referenceStatusOptions =
"<option value=''></option>"
+"<option value='Up'>Up</option>"
+"<option value='Down'>Down</option>"
+"<option value='AdminDown'>Administratively Down</option>";

var colorOptions='<option value='+'""'+' style='+'"background-color: white;"'+'></option>'+	
		'<option value='+'"blue"'+' style='+'"background-color: white; color:black"'+'>blue</option>'+
		'<option value='+'"orange"'+' style='+'"background-color: white; color:black"'+'>orange</option>'+
		'<option value='+'"green"'+' style='+'"background-color: white; color:black"'+'>green</option>'+
		'<option value='+'"brown"'+' style='+'"background-color: white; color:black"'+'>brown</option>'+
		'<option value='+'"gray"'+' style='+'"background-color: white; color:black"'+'>gray</option>'+
		'<option value='+'"white"'+' style='+'"background-color: white; color:black"'+'>white</option>'+
		'<option value='+'"red"'+' style='+'"background-color: white; color:black"'+'>red</option>'+
		'<option value='+'"black"'+' style='+'"background-color: white; color:black"'+'>black</option>'+
		'<option value='+'"yellow"'+' style='+'"background-color: white; color:black"'+'>yellow</option>'+
		'<option value='+'"violet"'+' style='+'"background-color: white; color:black"'+'>violet</option>'+
		'<option value='+'"pink"'+' style='+'"background-color: white; color:black"'+'>pink</option>'+
		'<option value='+'"aqua"'+' style='+'"background-color: white; color:black"'+'>aqua</option>';



markup = "<tr>"
+ "<td><input type='checkbox' name='record'><button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
+"<td name='MACAddress'><input name='MACAddress' value='' id='MACAddress"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='serialNb'><input name='serialNb' value='' id='serialNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='portAddress'><input name='portAddress' value='' id='portAddress"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='portStatus'><input name='portStatus' value='' id='portStatus"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='refStatus'>"
	+"<select class='form-control' style='width:220px;position:relative;' name='refStatus' id='refStatus"+portBoqIndex+"'>"+referenceStatusOptions+"</select>"
+ "</td>"
+"<td name='portNb'><input name='portNb' value='' id='portNb"+portBoqIndex+"' class='form-control' type='number' style='width:190px;position:relative;'/></td>"


+"<td name='recordType'><input name='recordType' value='passive' id='recordType"+portBoqIndex+"' class='form-control' type='text' readonly style='width:190px;position:relative;'/></td>"
+"<td name='locationType'>"
+"<select class='form-control' style='width:190px;position:relative;' name='locationType' id='locationType"+portBoqIndex+"'>"+locationTypeOptions+"</select>"
   + "</td>"

+"<td name='locationID'><input name='locationID' value='' id='locationID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='locationName'><input name='locationName' value='' id='locationName"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='wareID'><input name='wareID' value='' id='wareID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"

+"<td name='cableType'>"
	+"<select class='form-control' style='width:190px;position:relative;' name='cableType' id='cableType"+portBoqIndex+"'>"+cableTypeOptions+"</select>"
+ "</td>"
	    
+"<td name='txStrandNb'><input name='txStrandNb' value='' id='txStrandNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='txStrandColor'>"
	+"<select class='form-control' style='width:190px;position:relative;' name='txStrandColor' id='txStrandColor"+portBoqIndex+"'>"+colorOptions+"</select>"
+ "</td>"

+"<td name='rxStrandNb'><input name='rxStrandNb' value='' id='rxStrandNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='rxStrandColor'>"
	+"<select class='form-control' style='width:190px;position:relative;' name='rxStrandColor' id='rxStrandColor"+portBoqIndex+"'>"+colorOptions+"</select>"
+ "</td>"

+"<td name='txTubeNb'><input name='txTubeNb' value='' id='txTubeNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='txTubeColor'>"
	+"<select class='form-control' style='width:190px;position:relative;' name='txTubeColor' id='txTubeColor"+portBoqIndex+"'>"+colorOptions+"</select>"
+ "</td>"

+"<td name='rxTubeNb'><input name='rxTubeNb' value='' id='rxTubeNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='rxTubeColor'>"
	+"<select class='form-control' style='width:190px;position:relative;' name='rxTubeColor' id='rxTubeColor"+portBoqIndex+"'>"+colorOptions+"</select>"
+ "</td>"

+"<td name='cableID'><input name='cableID' value='' id='cableID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='cableName'><input name='cableName' value='' id='cableName"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
+"<td name='cableLength'><input name='cableLength' value='' id='cableLength"+portBoqIndex+"' class='form-control' type='number' style='width:190px;position:relative;'/></td>"
+"<td name='createdDate'><input name='createdDate' value='' id='createdDate"+portBoqIndex+"' class='form-control'  type='text' readonly style='width:250px;position:relative;'/></td>"
+"<td name='lastModifiedDate'><input name='lastModifiedDate' value='' id='lastModifiedDate"+portBoqIndex+"' class='form-control' type='text' readonly style='width:250px;position:relative;'/></td>"

+ "</tr>";


return markup;	   
				
}
function addNewRow(position){ 
	    
	                
var markup = htmlBOQRowInsertion('');

		if (position == "next"){
			$("#bisotab > tbody").append(markup);
			
			newRowIndx =  parseInt($("#bisotab >tbody tr").length-1);
      		//boqAutocomplete(newRowIndx);
			$('table#bisotab tr:eq('+(newRowIndx+1)+') td:nth-child(2) input').focus();
     	}
     	if (position =="below"){
			$("#bisotab > tbody tr").eq(rowindx).after(markup);
			newRowIndx = parseInt(rowindx+1);
			//belowRowCount =  $("#bisotab >tbody tr").length;
      		//boqAutocomplete(newRowIndx);
			$('table#bisotab tr:eq('+(newRowIndx+1)+') td:nth-child(1) input').focus();
		}
		if (position =="above"){
			$("#bisotab > tbody tr").eq(rowindx).before(markup);
			
			newRowIndx =  rowindx;
      		//boqAutocomplete(newRowIndx);
			$('table#bisotab tr:eq('+(newRowIndx+1)+') td:nth-child(1) input').focus();
		}
		
		//var myDiv = document.getElementById("bisotab");
    	//myDiv.scrollTop = myDiv.scrollHeight
		
		addRowEvents();
		portBoqIndex++;			

		rowInputListener((newRowIndx+1));
    	
     	    
} // end add new row

function rowInputListener(Indx){
	 // select cell per row and col
	$('#bisotab tr:eq('+Indx+') td input').change(function() {
			
			var cell = $(this).val();
			var column_name = $(this).parent().attr('name');
			
    
     
    
		});
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
	var rowCount;
  if($("#popupportRecordType").val() =="passive") {
	rowindx++;
	$("tr").eq(rowindx).remove();
	slctDelPort.push({"portMappingID":  $("#popupportMappingID").val()});
	 rowCount = $("#bisotab >tbody tr").length;
  	 
	 rowindx--;
  }
  else if($("#popupportRecordType").val() =="active") {
	  
	  if(!$("#popupMacAddress").is('[readonly]')) {
		  $("#popupMacAddress").val("");
	  }
	  
	  if(!$("#popupSerialNb").is('[readonly]')) {
		  $("#popupSerialNb").val("");
	  }
	  
	  if(!$("#popupPortAddress").is('[readonly]')) {
		  $("#popupPortAddress").val("");
	  }
	  
	  if(!$("#popupPortStatus").is('[readonly]')) {
		  $("#popupPortStatus").val("");
	  }
	  
	  $("#popupRefPortStatus").val("");
	  $("#popupLocType").val("");
	  $("#popupLocID").val("");
	  $("#popupLocName").val("");
	  $("#popupWareID").val("");
	  $("#popupCableType").val("");
	  $("#popupCableLength").val("0");
	  $("#popupPortNb").val("0");
	  $("#popupTXStrandNb").val("");
	  $("#popupRXStrandNb").val("");
	  $("#popupTXStrandColor").val("");
	  $("#popupRXStrandColor").val("");
	  $("#popupTXTubeNb").val("");
	  $("#popupRXTubeNb").val("");
	  $("#popupTXTubeColor").val("");
	  $("#popupRXTubeColor").val("");
	  $("#popupCableID").val("");
	  $("#popupCableName").val("");
	  sendValPopupToBoq(rowindx)
	  
	  
  }
	// Get Nb of rows after delete 
/*	var rowCount = $("#bisotab >tbody tr").length;
	   	 
	 rowindx--;
	 */
	 if (rowindx == 0 && rowCount ==0) {
		$("#poModal").modal("hide");
		$("#bisotab > tbody > tr").each(function(i, row){

	
       });
		
	}
 
	else if(rowindx >= 0 && rowindx < rowCount) {
		sendValBoqToPopup(rowindx);
	
	}

   // Show previous record 
	else if (rowindx >= rowCount){

		rowindx--;
		sendValBoqToPopup(rowindx);
	}

}

/* function deleteBoqRow() {

	rowindx++;
	$("tr").eq(rowindx).remove();
	
	// Get Nb of rows after delete 
	var rowCount = $("#bisotab >tbody tr").length;
	   	 
	 rowindx--;
	 
	 if (rowindx == 0 && rowCount ==0) {
		$("#poModal").modal("hide");
		$("#bisotab > tbody > tr").each(function(i, row){
 
	
        });
		
	}
  
	else if(rowindx >= 0 && rowindx < rowCount) {
		sendValBoqToPopup(rowindx);
	
	}

    // Show previous record 
	else if (rowindx >= rowCount){

		rowindx--;
		sendValBoqToPopup(rowindx);
	}
 
 } // End delete fct*/
 
    	
 
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}

function addRowEvents() {
	 $("#locationType"+portBoqIndex).change(function(){
    	 var thisID = $(this).attr("id");
		var indexFor = thisID.replace('locationType','');
    	 $('#locationID'+indexFor).val('');
    	 $('#locationName'+indexFor).val('');
    	 $('#wareID'+indexFor).val('');

    	 if($(this).val()=="Customer"){
			 $('#wareID' + indexFor).attr('readonly', true);
			}
		else {
			 $('#wareID' + indexFor).attr('readonly', false);
			}
    								 
		});

     $("#cableType"+portBoqIndex).change(function(){
    	 var thisID = $(this).attr("id");
		var indexFor = thisID.replace('cableType','');
    	 $('#txStrandColor'+indexFor).val('');
    	 $('#txStrandNb'+indexFor).val('');
    	 $('#rxStrandColor'+indexFor).val('');
    	 $('#rxStrandNb'+indexFor).val('');

    	 $('#txTubeColor'+indexFor).val('');
    	 $('#txTubeNb'+indexFor).val('');
    	 $('#rxTubeColor'+indexFor).val('');
    	 $('#rxTubeNb'+indexFor).val('');

    	 $('#cableID'+indexFor).val('');
    	 $('#cableName'+indexFor).val('');
    	 $('#cableLength'+indexFor).val('');
    	 if($(this).val()==="Indoor" || $(this).val()===null || $(this).val()==="null"|| $(this).val()===""){
    		 $('#txStrandNb' + indexFor).attr('readonly', true);
    		 $('#rxStrandNb' + indexFor).attr('readonly', true);
    		 $('#txTubeNb' + indexFor).attr('readonly', true);
    		 $('#rxTubeNb' + indexFor).attr('readonly', true);
    		 $('#txStrandColor' + indexFor).prop('disabled', true);
    		 $('#rxStrandColor' + indexFor).prop('disabled', true);
    		 $('#txTubeColor' + indexFor).prop('disabled', true);
    		 $('#rxTubeColor' + indexFor).prop('disabled', true);
    		 $('#cableID' + indexFor).attr('readonly', true);
    		
	    	 
	    	 }
    	 if($(this).val()==="Outdoor"){
    		 $('#txStrandNb' + indexFor).attr('readonly', false);
    		 $('#rxStrandNb' + indexFor).attr('readonly', false);
    		 $('#txTubeNb' + indexFor).attr('readonly', false);
    		 $('#rxTubeNb' + indexFor).attr('readonly', false);
    		
    		 $('#txStrandColor' + indexFor).prop('disabled', false);
    		 $('#rxStrandColor' + indexFor).prop('disabled', false);
    		 $('#txTubeColor' + indexFor).prop('disabled', false);
    		 $('#rxTubeColor' + indexFor).prop('disabled', false);
    		 $('#cableID' + indexFor).attr('readonly', false);
    		 
	    	 
	    	 }
    	 
    								 
		});

     $("#txStrandColor"+portBoqIndex).change(function(){
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('txStrandColor','');
			colorId="txStrandColor"+indexFor;
			numberId="txStrandNb"+indexFor;
			tubeStrandPortMappingSetColor(colorId,numberId);	 
		});

     $("#rxStrandColor"+portBoqIndex).change(function(){
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('rxStrandColor','');
			colorId="rxStrandColor"+indexFor;
			numberId="rxStrandNb"+indexFor;
			tubeStrandPortMappingSetColor(colorId,numberId);	 
		});

     $("#txTubeColor"+portBoqIndex).change(function(){
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('txTubeColor','');
			colorId="txTubeColor"+indexFor;
			numberId="txTubeNb"+indexFor;
			tubeStrandPortMappingSetColor(colorId,numberId);	 
		});


     $("#rxTubeColor"+portBoqIndex).change(function(){
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('rxTubeColor','');
			colorId="rxTubeColor"+indexFor;
			numberId="rxTubeNb"+indexFor;
			tubeStrandPortMappingSetColor(colorId,numberId);	 
		});

     document.getElementById("txStrandNb"+portBoqIndex).addEventListener ("input" ,function() {
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('txStrandNb','');
			colorId="txStrandColor"+indexFor;
			numberId="txStrandNb"+indexFor;
			number=document.getElementById(numberId).value;
			strandTubePortMappingSetColor(number,colorId);
		
		});

     document.getElementById("rxStrandNb"+portBoqIndex).addEventListener ("input" ,function() {
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('rxStrandNb','');
			colorId="rxStrandColor"+indexFor;
			numberId="rxStrandNb"+indexFor;
			number=document.getElementById(numberId).value;
			strandTubePortMappingSetColor(number,colorId);
		
		});

     document.getElementById("rxTubeNb"+portBoqIndex).addEventListener ("input" ,function() {
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('rxTubeNb','');
			colorId="rxTubeColor"+indexFor;
			numberId="rxTubeNb"+indexFor;
			number=document.getElementById(numberId).value;
			strandTubePortMappingSetColor(number,colorId);
		
		});

     document.getElementById("txTubeNb"+portBoqIndex).addEventListener ("input" ,function() {
			var thisID = $(this).attr("id");
			//var indexFor = parseInt(thisID.substr(thisID.length-1));
			var indexFor = thisID.replace('txTubeNb','');
			colorId="txTubeColor"+indexFor;
			numberId="txTubeNb"+indexFor;
			number=document.getElementById(numberId).value;
			strandTubePortMappingSetColor(number,colorId);
		
		});

     autoCompleteForPortMapping(portBoqIndex,"bisotab","wareID","locationID","locationName","locationType","cableID","cableName","cableType","txStrandNb","rxStrandNb","txStrandColor","rxStrandColor","txTubeNb","rxTubeNb","txTubeColor","rxTubeColor");
	
}

function tubeStrandPortMappingSetColor(colorID,numberID) {

		if (document.getElementById(colorID).value=="blue"){
		   	 document.getElementById(colorID).style.backgroundColor = "blue"; 
		   	 document.getElementById(colorID).style.color = "white";
		     document.getElementById(numberID).value= "1";
		    }
		else if (document.getElementById(colorID).value=="orange"){
		     document.getElementById(colorID).style.backgroundColor = "orange"; 
		     document.getElementById(colorID).style.color = "white";
		     document.getElementById(numberID).value= "2";
		    }
		else if (document.getElementById(colorID).value=="green"){
		   	 document.getElementById(colorID).style.backgroundColor = "green";
		   	 document.getElementById(colorID).style.color = "white";
		   	 document.getElementById(numberID).value= "3";
		   	 }
		 else if(document.getElementById(colorID).value=="brown") {
		   	 document.getElementById(colorID).style.backgroundColor = "brown";
		   	 document.getElementById(colorID).style.color = "white";
		   	 document.getElementById(numberID).value= "4";
		    }
		 else if(document.getElementById(colorID).value=="gray") {
		   	 document.getElementById(colorID).style.backgroundColor = "gray"; 
		   	 document.getElementById(colorID).style.color = "white";
		   	 document.getElementById(numberID).value= "5";
		    }
		 else if(document.getElementById(colorID).value=="white") {
		   	 document.getElementById(colorID).style.backgroundColor = "white"; 
		   	 document.getElementById(colorID).style.color = "black";
		   	 document.getElementById(numberID).value= "6";
		    }	
		 else if(document.getElementById(colorID).value=="red"){
		     document.getElementById(colorID).style.backgroundColor = "red";
			 document.getElementById(colorID).style.color = "white";
			 document.getElementById(numberID).value= "7";
		 }
		else if(document.getElementById(colorID).value=="black") {
		  	 document.getElementById(colorID).style.backgroundColor = "black";
		  	 document.getElementById(colorID).style.color = "white";
		  	 document.getElementById(numberID).value= "8";
		   } 
		else if(document.getElementById(colorID).value=="yellow") {
		  	 document.getElementById(colorID).style.backgroundColor = "yellow";
		  	 document.getElementById(colorID).style.color = "black";
		  	 document.getElementById(numberID).value= "9";
		   } 	
		else if(document.getElementById(colorID).value=="violet") {
			 document.getElementById(colorID).style.backgroundColor = "violet"; 
			 document.getElementById(colorID).style.color = "white";
			 document.getElementById(numberID).value= "10";
		  }         
		else if(document.getElementById(colorID).value=="pink") {
			 document.getElementById(colorID).style.backgroundColor = "pink";
			 document.getElementById(colorID).style.color = "black";
			 document.getElementById(numberID).value= "11";
		  }
		else if(document.getElementById(colorID).value=="aqua") {
			 document.getElementById(colorID).style.backgroundColor = "aqua";
			 document.getElementById(colorID).style.color = "black";
			 document.getElementById(numberID).value= "12";
		 }
	}

function strandTubePortMappingSetColor(strandTubeNumber,ID) {
		
		 if (strandTubeNumber=="1"){
			 document.getElementById(ID).value = "blue";
		   	 document.getElementById(ID).style.backgroundColor = "blue"; 
		   	 document.getElementById(ID).style.color = "white";
		 }
		 else if (strandTubeNumber=="2"){
			 document.getElementById(ID).value ="orange";
		     document.getElementById(ID).style.backgroundColor = "orange"; 
		     document.getElementById(ID).style.color = "white";	  
		 }
		 else if (strandTubeNumber=="3"){
			 document.getElementById(ID).value ="green";
		   	 document.getElementById(ID).style.backgroundColor = "green";
		   	 document.getElementById(ID).style.color = "white";
		 }
		 else if (strandTubeNumber=="4"){
			 document.getElementById(ID).value ="brown";
		   	 document.getElementById(ID).style.backgroundColor = "brown";
		   	 document.getElementById(ID).style.color = "white";
		 }
		 else if (strandTubeNumber=="5"){
			 document.getElementById(ID).value ="gray";
		   	 document.getElementById(ID).style.backgroundColor = "gray"; 
		   	 document.getElementById(ID).style.color = "white";  
		 }
		 else if (strandTubeNumber=="6"){
			 document.getElementById(ID).value ="white";
		   	 document.getElementById(ID).style.backgroundColor = "white"; 
		   	 document.getElementById(ID).style.color = "black";
		 }
		 else if (strandTubeNumber=="7"){
			 document.getElementById(ID).value ="red";
	         document.getElementById(ID).style.backgroundColor = "red";
	    	 document.getElementById(ID).style.color = "white";
		 }
		 else if (strandTubeNumber=="8"){
			 document.getElementById(ID).value ="black";
	      	 document.getElementById(ID).style.backgroundColor = "black";
	      	 document.getElementById(ID).style.color = "white";
		 }
		 else if (strandTubeNumber=="9"){
			 document.getElementById(ID).value ="yellow";
	      	 document.getElementById(ID).style.backgroundColor = "yellow";
	      	 document.getElementById(ID).style.color = "black";
		 }
		 else if (strandTubeNumber=="10"){
			 document.getElementById(ID).value ="violet";
	   		 document.getElementById(ID).style.backgroundColor = "violet"; 
	   		 document.getElementById(ID).style.color = "white";
		 }
		 else if (strandTubeNumber=="11"){
			 document.getElementById(ID).value ="pink";
	   		 document.getElementById(ID).style.backgroundColor = "pink";
	   		 document.getElementById(ID).style.color = "black"; 	
		 }
		 else if (strandTubeNumber=="12"){
			 document.getElementById(ID).value ="aqua";
	  		 document.getElementById(ID).style.backgroundColor = "aqua";
	  		 document.getElementById(ID).style.color = "black";
		 }
		 else if (strandTubeNumber >12 || strandTubeNumber==""){
			 document.getElementById(ID).value ="";
	  		 document.getElementById(ID).style.backgroundColor = "";
	  		 document.getElementById(ID).style.color = "";	
		 }			 

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
  
  
function autoCompleteForPortMapping(ID,tableID,wareId,LocationID,LocationName,LocationType,fiberID,fiberName,cableType,txstrandNb,rxstrandNb,txstrandColor,rxstrandColor,txtubeNb,rxtubeNb,txtubeColor,rxtubeColor){
	var url ="emptyUrl";
	var dataTarget="";
	var search="";				
	//console.log("mapping autocomplete");
$("#"+LocationID+ID).autocomplete({
	source: debounce(function(request, response,event,ui) {
	    var searchs=$("#"+LocationID+ID).val();
		var line;
	    search= $("#"+LocationType+ID).val();
	   // console.log("debounce");
		if(search=="Customer"){
				url='GetAllNetworkCustomer';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if(search=="Site"){
				url='GetAllWarehouse';
				dataTarget = {
		       		"WareName":searchs,
					"warehouseName" : searchs,
					"SiteId":searchs,
				 }
			}
			else {
				url='emptyUrl';
			}
	if(url !="emptyUrl") {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			 //url: '${pageContext.request.contextPath}/'+url,
			 url: ctx+'/'+url,
			data: dataTarget,				
		 	dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.globalList);  
					                 
				}
			},								               
			  error: function(result) {
                  alert("Error");
              }
          });						               
		} 
/*END FUNCTION*/	},900), minLength:0, maxShowItems: 40, scroll:true,
 		select: function(event, ui) {
 			
			if(search=="Customer"){
				$("#"+LocationID+ID).val(ui.item ? ui.item[0] : '');
				$("#"+LocationName+ID).val(ui.item[1]);
				//$("#"+Location+ID).val(ui.item[3]);
			}
			else if(search=="Site"){
				$("#"+LocationName+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[2]);
				
				$("#"+wareId+ID).val(ui.item[0]);
				
			}	
		return false;
	},	
}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	
	if(item[0].split("_")[0]=="CUST" ) {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1] +', '+ item[2]+ "</span></div>")
            .appendTo(ul);
	}
	else if(item[0].split("_")[0]=="WARE") {
		 return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
           item[2] + "</span><br><span class='desc'>" +
            item[0] +', '+ item[1] + "</span></div>")
        .appendTo(ul);
	}
	
	};		    	    
						         
	$("#"+LocationID+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});		

	// autocomplete for locacation name
	$("#"+LocationName+ID).autocomplete({
	source: debounce( function(request, response,event,ui) {
	    var searchs=$("#"+LocationName+ID).val();
		var line;
	    search= $("#"+LocationType+ID).val();
	   
		if(search=="Customer"){
				url='GetAllNetworkCustomer';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if(search=="Site"){
				url='GetAllWarehouse';
				dataTarget = {
		       		"WareName":searchs,
					"warehouseName" : searchs,
					"SiteId":searchs,
				 }
			}
			else {
				url='emptyUrl';
			}
	if(url !="emptyUrl") {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			//url: '${pageContext.request.contextPath}/'+url,
			url: ctx+'/'+url,
			data: dataTarget,				
		 	dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.globalList);                   
				}
			},								               
			  error: function(result) {
                  alert("Error");
              }
          });						               
		} 
	},900), minLength:0, maxShowItems: 40, scroll:true,
 		select: function(event, ui) {
 			if(search=="Customer"){
 				$("#"+LocationID+ID).val(ui.item ? ui.item[0] : '');
				$("#"+LocationName+ID).val(ui.item[1]);
				//$("#"+Location+ID).val(ui.item[3]);
			}
			else if(search=="Site"){
				$("#"+LocationName+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[2]);
				$("#"+wareId+ID).val(ui.item[0]);
			}
			
		return false;
	},	
}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	
	if(item[0].split("_")[0]=="CUST" ) {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[1] + "</span><br><span class='desc'>" +
                item[0] +', '+item[2]+ "</span></div>")
            .appendTo(ul);
	}
	else if(item[0].split("_")[0]=="WARE") {
		 return $("<li class='each'>")
       .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
          item[1] + "</span><br><span class='desc'>" +
           item[0] +', '+ item[2] + "</span></div>")
       .appendTo(ul);
	}
	};		    	    
						         
	$("#"+LocationName+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});			
			    	    
		
		$("#"+fiberID+ID).autocomplete({
   	    source: debounce(function(request, response,event, ui) {

   	    		var searchId= $("#"+fiberID+ID).val();
   	    		if($("#"+cableType+ID).val()==="Outdoor"){
				url='SearchFiber';
				}
   	    		else{
   	    			url='emptyUrl';
	   	    		}
				 
				 if(url !="emptyUrl") {
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8", 
		                 //url: '${pageContext.request.contextPath}/'+url,
		                 url: ctx+'/'+url,
		                 data: {
							"searchId" : searchId,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.glist);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             }); 
   	    		}     
	        },900), minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {			
				this.value = (ui.item ? ui.item[0] : '');
				//$(this).parents("tr").find('input[name ="'+fiberName+'"]').val(ui.item[1]);
				$("#"+fiberName+ID).val(ui.item[1]);
				
				
				return false;
			}
		}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
   	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
   			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
               item[0] + '</span><br><span class="desc">' +
               item[1] + '</span></div>').appendTo(ul);
	};
	$("#"+fiberID+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
///////////////////////////////
$("#"+fiberName+ID).autocomplete({
   	    source: debounce(function(request, response,event, ui) {
				var searchId= $("#"+fiberName+ID).val();
				if($("#"+cableType+ID).val()==="Outdoor"){
					url='SearchFiber';
					}
	   	    		else{
	   	    			url='emptyUrl';
		   	    		}
					 
					 if(url !="emptyUrl") {
		            	 $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                  //url: '${pageContext.request.contextPath}/'+url,
		                  url: ctx+'/'+url,
		                 
		                 data: {
							"searchId" : searchId,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.glist);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });  
				 }      
	        },900), minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {
				
				this.value = (ui.item ? ui.item[1] : '');
				//$(this).parents("tr").find('input[name ="'+fiberID+'"]').val(ui.item[0]);
				$("#"+fiberID+ID).val(ui.item[0]);
				
				
				
					return false;
			}
		}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
   	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
   			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
               item[1] + '</span><br><span class="desc">' +
               item[0] + '</span></div>').appendTo(ul);
	};
	$("#"+fiberName+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});

	

}
 
	
 $(function(){


		
		
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
    

	


	    	
 //Send input values from popup to boq when any popup input change
 $('#popupportRecordType,#popupMacAddress, #popupSerialNb, #popupPortAddress,#popupPortStatus,#popupPortNb,#popupLocType,#popupLocID,#popupLocName,#popupWareID,#popupCableType,#popupTXStrandNb,#popupTXStrandColor,#popupRXStrandNb,#popupRXStrandColor,#popupTXTubeNb,#popupTXTubeColor,#popupRXTubeNb,#popupRXTubeColor,#popupCableID,#popupCableName,#popupCableLength').on(' focusout ', function() {

	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="recordType"]').children('input').val($('#popupportRecordType').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="MACAddress"]').children('input').val($('#popupMacAddress').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNb"]').children('input').val($('#popupSerialNb').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="portAddress"]').children('input').val($('#popupPortAddress').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="portStatus"]').children('input').val($('#popupPortStatus').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="portNb"]').children('input').val($('#popupPortNb').val());
	 
	 
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="locationType"]').children('select').val($('#popupLocType').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="locationID"]').children('input').val($('#popupLocID').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="locationName"]').children('input').val($('#popupLocName').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="wareID"]').children('input').val($('#popupWareID').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="cableType"]').children('select').val($('#popupCableType').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="txStrandNb"]').children('input').val($('#popupTXStrandNb').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="txStrandColor"]').children('select').val($('#popupTXStrandColor').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="rxStrandNb"]').children('input').val($('#popupRXStrandNb').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="rxStrandColor"]').children('select').val($('#popupRXStrandColor').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="txTubeNb"]').children('input').val($('#popupTXTubeNb').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="txTubeColor"]').children('select').val($('#popupTXTubeColor').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="rxTubeNb"]').children('input').val($('#popupRXTubeNb').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="rxTubeColor"]').children('select').val($('#popupRXTubeColor').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="cableID"]').children('input').val($('#popupCableID').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="cableName"]').children('input').val($('#popupCableName').val());
	 $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="cableLength"]').children('input').val($('#popupCableLength').val());
	
			
	    
      }); // end fct	
// Close popup function  				
   $("button[name='closeModPartPopup']").on("click", function(){
   
	    sendValPopupToBoq(rowindx);
  
	    $("#poModal").modal("hide");
		 
 }); // end close fct

 
 
    				 
// Send input values from popup to boq table and close the popup using ESC 
	document.addEventListener('keydown', function(event){
    	if(event.key === "Escape"){
			if($("#poModal").hasClass("show")){
				// Send input values from popup to boq table and close the popup using ESC 
				sendValPopupToBoq(rowindx);
				$("#poModal").modal("hide");
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
       
       del=[] 
     for(i=0;i<allDelSerials.length;i++){
    
     if(allDelSerials[i]!=""){
     del.push(allDelSerials[i]);
     }
    }
     
   
				
				
   	});// end prev fct  
   	
 

	 
	  
	  //popup dropdown on change events 
	  $("#popupLocType").change(function(){
	    	
			if($(this).val()=="Customer"){
				 $('#popupWareID').attr('readonly', true);
				}
			else {
				 $('#popupWareID').attr('readonly', false);
				}
	    	 $('#popupLocID').val('');
	    	 $('#popupLocName').val('');
	    	 $('#popupWareID').val('');
	    								 
			});
	  
	  $("#popupCableType").change(function(){
	    	
	    	 $('#popupTXStrandColor').val('');
	    	 $('#popupTXStrandNb').val('');
	    	 $('#popupRXStrandColor').val('');
	    	 $('#popupRXStrandNb').val('');

	    	 $('#popupTXTubeColor').val('');
	    	 $('#popupTXTubeNb').val('');
	    	 $('#popupRXTubeColor').val('');
	    	 $('#popupRXTubeNb').val('');

	    	 $('#popupCableID').val('');
	    	 $('#popupCableName').val('');
	    	 $('#popupCableLength').val('');
	    	 if($(this).val()==="Indoor" || $(this).val()===null || $(this).val()==="null"|| $(this).val()==="" ){
	    		 $('#popupTXStrandNb').attr('readonly', true);
	    		 $('#popupRXStrandNb').attr('readonly', true);
	    		 $('#popupTXTubeNb').attr('readonly', true);
	    		 $('#popupRXTubeNb').attr('readonly', true);
	    		 $('#popupTXStrandColor').prop('disabled', true);
	    		 $('#popupRXStrandColor').prop('disabled', true);
	    		 $('#popupTXTubeColor').prop('disabled', true);
	    		 $('#popupRXTubeColor').prop('disabled', true);
	    		 $('#popupCableID').attr('readonly', true);
	    		
		    	 
		    	 }
	    	 if($(this).val()==="Outdoor"){
	    		 $('#popupTXStrandNb').attr('readonly', false);
	    		 $('#popupRXStrandNb').attr('readonly', false);
	    		 $('#popupTXTubeNb').attr('readonly', false);
	    		 $('#popupRXTubeNb').attr('readonly', false);
	    		
	    		 $('#popupTXStrandColor').prop('disabled', false);
	    		 $('#popupRXStrandColor').prop('disabled', false);
	    		 $('#popupTXTubeColor').prop('disabled', false);
	    		 $('#popupRXTubeColor').prop('disabled', false);
	    		 $('#popupCableID').attr('readonly', false);
	    		 
		    	 
		    	 }
	    	 						 
			});
	  
	  $("#popupTXStrandColor").change(function(){
			tubeStrandPortMappingSetColor("popupTXStrandColor","popupTXStrandNb");	 
		});
	  
	  $("#popupRXStrandColor").change(function(){
			tubeStrandPortMappingSetColor("popupRXStrandColor","popupRXStrandNb");	 
		});
	  
	  $("#popupTXTubeColor").change(function(){
			tubeStrandPortMappingSetColor("popupTXTubeColor","popupTXTubeNb");	 
		});
	  
	  $("#popupRXTubeColor").change(function(){
			tubeStrandPortMappingSetColor("popupRXTubeColor","popupRXTubeNb");	 
		});
	  
	  document.getElementById("popupTXStrandNb").addEventListener ("input" ,function() {			
			number=document.getElementById("popupTXStrandNb").value;
			strandTubePortMappingSetColor(number,"popupTXStrandColor");
		
		});
	  
	  document.getElementById("popupRXStrandNb").addEventListener ("input" ,function() {			
			number=document.getElementById("popupRXStrandNb").value;
			strandTubePortMappingSetColor(number,"popupRXStrandColor");
		
		});
	  
	  document.getElementById("popupTXTubeNb").addEventListener ("input" ,function() {			
			number=document.getElementById("popupTXTubeNb").value;
			strandTubePortMappingSetColor(number,"popupTXTubeColor");
		
		});
	  
	  document.getElementById("popupRXTubeNb").addEventListener ("input" ,function() {
			number=document.getElementById("popupRXTubeNb").value;
			strandTubePortMappingSetColor(number,"popupRXTubeColor");
		
		});

	  
	  /*
		 ($('#popupMacAddress').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNb"]').children('input').val($('#popupSerialNb').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portAddress"]').children('input').val($('#popupPortAddress').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portStatus"]').children('input').val($('#popupPortStatus').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="portNb"]').children('input').val($('#popupPortNb').val());
		 
		 
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationType"]').children('select').val($('#popupLocType').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationID"]').children('input').val($('#popupLocID').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="locationName"]').children('input').val($('#popupLocName').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="wareID"]').children('input').val($('#popupWareID').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableType"]').children('select').val($('#popupCableType').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandNb"]').children('input').val($('#popupTXStrandNb').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txStrandColor"]').children('select').val($('#popupTXStrandColor').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandNb"]').children('input').val($('#popupRXStrandNb').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxStrandColor"]').children('select').val($('#popupRXStrandColor').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeNb"]').children('input').val($('#popupTXTubeNb').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="txTubeColor"]').children('select').val($('#popupTXTubeColor').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeNb"]').children('input').val($('#popupRXTubeNb').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="rxTubeColor"]').children('select').val($('#popupRXTubeColor').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableID"]').children('input').val($('#popupCableID').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableName"]').children('input').val($('#popupCableName').val());
		 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="cableLength"]').children('input').val($('#popupCableLength').val());*/
	

	
	
	
									
  });
  
     		         

    			  
  

    			 
   