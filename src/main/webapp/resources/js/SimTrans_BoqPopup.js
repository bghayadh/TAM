
var rowindx =0;
 var rowcountSerial =0;
 var serialRowIndex=0;

 function openPop(element) {	
     var buttonRowIndx = $(element).closest("tr");
     rowindx = (buttonRowIndx[0].rowIndex - 1);
    
    //Send input values from Boq table  to popup
	sendValBoqToPopup(rowindx);

        			  
    $("#trModal").modal("show");
    AutocompleteOnLoad();
    				   
}// end open popup fct

 function showMsg(element) {	
     var buttonRowIndx = $(element).closest("tr");
     rowindx = (buttonRowIndx[0].rowIndex - 1); 
    let color= $("#bisotab >tbody").find("tr").eq(rowindx).find('span[class="dotStatus"]').css( "background-color" );
    if(color == "rgb(255, 165, 0)" ){
    return;
    }else
	 sendValBoqToPopup(rowindx);
     $('#trModal').modal('show');
     $('#serialNum-tab').tab('show');
     AutocompleteOnLoad();
			   
}// end show msg boq fct

 function showMsgSerial(element) {	
     var buttonRowIndx = $(element).closest("tr");
     serialRowIndex = (buttonRowIndx[0].rowIndex - 1);
     var msg = $("#serialNoTable >tbody").find("tr").eq(serialRowIndex).find('td[name="checkboxSerial"]').children('input').val();
     if(msg.length==0){
     return;
     }else
	
    alert(msg);
    				   
}// end show msg serial boq fct

function sendValBoqToPopup(indxRow){

	$('#popupSourceType').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSourceType"]').children('input').val());
	$('#popupSource').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSource"]').children('input').val());
	$('#popupSourceID').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSourceID"]').children('input').val());
	$('#popupSourceMSISDN').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSourceMSISDN"]').children('input').val());
	$('#popupDestinationType').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trDestinationType"]').children('input').val());
	$('#popupDestination').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trDestination"]').children('input').val());
	$('#popupDestinationID').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trDestinationID"]').children('input').val());
	$('#popupCreationDate').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trCreationDate"]').children('input').val());
	$('#popupLastModifiedDate').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trLastModifiedDate"]').children('input').val());
	$('#popupTransactionItemID').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trItemID"]').children('input').val());
	$('#totNmbr').val($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trTotQty"]').children('input').val());
	$("#simCardsQty").val($('#totNmbr').val());
		
	// Send hidden concatenated value of serial/Model/PartNum from Boq to Serial table in popup
	var parsedSerial;
	parsedSerial = JSON.parse($("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNo"]').children('input').val());
		$("#serialNoTable > tbody").html(" ");
	if(parsedSerial != null){
	var serNum,msisdn,simID,serialItemID,msg="";
	$.each(parsedSerial.serialArray,function(i,value ){
	serNum= (parsedSerial.serialArray[i].serial_no === null) ? '' : parsedSerial.serialArray[i].serial_no;
	msisdn= (parsedSerial.serialArray[i].msisdn === null) ? '' : parsedSerial.serialArray[i].msisdn;
	simID= (parsedSerial.serialArray[i].simID === null) ? '' : parsedSerial.serialArray[i].simID;
	serialItemId= (parsedSerial.serialArray[i].serialItemId === null) ? '' : parsedSerial.serialArray[i].serialItemId;
	
			if(typeof jsonObjectError !== 'undefined'){
			if(jsonObjectError.incorrectSerials.length !== 0 || jsonObjectError.incorrectMSISDN.length !== 0 ){
		error="";
				if(jsonObjectError.incorrectSerials.includes(serNum)){
		error+="Wrong Serial: "+serNum+"\n";
		}
						if(jsonObjectError.incorrectMSISDN.includes(msisdn)){
		error+="Wrong MSISDN: "+msisdn+"\n";
		}
		msg=error;
		}
		}//end iff for json object 
				var itemRowSerial = "<tr>";
			itemRowSerial = itemRowSerial + "<td name ='checkboxSerial'><input type='checkbox' value='" + msg +"' style='width: 14px;height: 13px;position:relative;left:6px;'  name='record'><span class='dotStatus' id='dotStatusBoq' onclick='showMsgSerial(this)' style='position:relative;left:3px;'></span></td>"
			itemRowSerial = itemRowSerial + "<td name='serialNumber'><input name='serialNumber' type='text' value='" + serNum +"' id=" + "serialNumber_" + rowcountSerial +"   style='width:200px;position:relative;left:11px;' class='form-control text-input'/></td>";
			itemRowSerial = itemRowSerial + "<td name='msisdn' style='width:200px'><input name='msisdn'   type='text' value='" + msisdn + "' id=" + "msisdn_" + rowcountSerial +"  style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial = itemRowSerial + "<td  name='id' style='width:200px'><input name='id'   type='text'   value='" + simID + "' id=" + "id_" + rowcountSerial +" style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial = itemRowSerial + "<td hidden name='serialId' style='width:200px'><input name='serialId' hidden  type='text'  readonly value='" + serialItemId + "'  style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			
			itemRowSerial = itemRowSerial + "</tr>";
			$("#serialNoTable > tbody").append(itemRowSerial);
			if(msg !== ''){

			 $("#serialNoTable >tbody").find("tr").eq(i).find('td[name="checkboxSerial"]').children('input').val(msg)
			 $("#serialNoTable >tbody").find("tr").eq(i).find('span[id="dotStatusBoq"]').css({"background-color" : "red"});			 
			}//end error
		
	});//end for each
	}	//end not empty

	    	  var element = document.getElementById("popupNb");
    			  element.innerHTML = "Item # " +indxRow;
		 
}

function sendValPopupToBoq(indxRow){
	 // checkSerial();
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSourceType"]').children('input').val($('#popupSourceType').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSource"]').children('input').val($('#popupSource').val());	
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSourceID"]').children('input').val($('#popupSourceID').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trSourceMSISDN"]').children('input').val($('#popupSourceMSISDN').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trDestinationType"]').children('input').val($('#popupDestinationType').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trDestination"]').children('input').val($('#popupDestination').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trDestinationID"]').children('input').val($('#popupDestinationID').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trCreationDate"]').children('input').val($('#popupCreationDate').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trLastModifiedDate"]').children('input').val($('#popupLastModifiedDate').val());
	  $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trItemID"]').children('input').val($('#popupTransactionItemID').val());

	    // Send concatenated Serial Table rows to boq table
	 	 var Data = {};
	 	 var serial_no="";
	 	 var msisdn ="";
	 	 var simID = "";
	 	 var serialItemId = "";
	 	 var indx;
	 	 Data.serialArray=[];
	 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){
		rowcountSerial =  $("#serialNoTable >tbody tr").length;
 // Remove the row where SerialNumber is empty
	var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
		 msisdn = $(this).parent().parent().children('td[name="msisdn"]').children('input').val();
		 simID = $(this).parent().parent().children('td[name="id"]').children('input').val();
		if (serNum == '') {
			$(this).parents("tr").remove();
			 rowcountSerial=--rowcountSerial;
	         $("#totNmbr").val(rowcountSerial);
         	 $("#simCardsQty").val(rowcountSerial);
         }
	     else{
	         serial_no = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	         msisdn = $(this).parent().parent().children('td[name="msisdn"]').children('input').val();
	    	 simID = $(this).parent().parent().children('td[name="id"]').children('input').val();
	    	 serialItemId = $(this).parent().parent().children('td[name="serialId"]').children('input').val();
	    	

			    Data.serialArray.push({serial_no:serial_no, msisdn:msisdn, simID:simID,serialItemId:serialItemId});

	      }
	  	});

	    	$("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="serialNo"]').children('input').val(JSON.stringify(Data));
	    	 $("#bisotab >tbody").find("tr").eq(indxRow).find('td[name="trTotQty"]').children('input').val($("#totNmbr").val());

}
function addNewRow(position){ 
 
 		        	var markup = "<tr><td><input type='checkbox' name='record' value='' style='width: 14px;height: 13px;position:relative;left:6px;'><span class='dotStatus' id='dotStatus' onclick='showMsg(this)' style='position:relative;left:3px;'></span><button type='button' href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default'  style='margin-bottom:3px;'><i class='fas fa-desktop'></i></button></td>"
 						+"<td name='trSource'>"
 						+"<input name='source' type='text' style='width:200px;' class='form-control text-input'/></td>"
 						+"<td hidden name='trSourceType'>"
 						+"<input name='sourceType' readonly type='text' style='width:200px;' class='form-control text-input'/></td>"
 						+"<td hidden name='trSourceID'>"
 						+"<input name='sourceID' type='text' style='width:200px;' class='form-control text-input'/></td>"
 						+"<td  name='trSourceMSISDN'>"
 						+"<input name='sourceMSISDN' type='text'     style='width:200px;' class='form-control text-input'/></td>"
 						+"<td  name='trDestination'>"
 						+"<input name='destination' type='text'    style='width:200px;' class='form-control text-input'/></td>"
 						+"<td hidden name='trDestinationType'>"
 						+"<input name='destinationType' readonly type='text'    style='width:200px;' class='form-control text-input'/></td>"					
 						+"<td hidden name='trDestinationID'>"
 						+"<input name='destinationID' type='text'  style='width:200px;' class='form-control text-input'/></td>"
 						 +"<td  name='trTotQty'>"
 						+"<input name='totQty' type='text' readonly value= 0 style='width:200px;' class='form-control text-input'/></td>"
 						+"<td  name='trCreationDate'>"
 						+"<input name='creationDate' type='text'   style='width:200px;' class='form-control text-input' readonly/></td>"
 						+"<td  name='trLastModifiedDate'>"
 						+"<input name='lastModifiedDate' type='text'   style='width:200px;' class='form-control text-input' readonly/></td>"
 						+"<td name='trItemID'><input type='text' readonly value= 0 style='width:200px;' class='form-control text-input'></td>"
    					+"<td hidden style='width:0px;border-width: 0px;' name='serialNo'><input type='text' style='width:200px;'   value ='null' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
    					
    					+"</tr>"; 


		if (position == "next"){
            $("#bisotab > tbody").append(markup);
			
			newRowCount =  $("#bisotab >tbody tr").length;
      		boqAutocomplete(newRowCount,"bisotab");
      		AutocompleteOnLoad()
     	}
     	
     	
     	if (position =="below"){
			$("#bisotab > tbody tr").eq(rowindx).after(markup);
			
			belowRowCount =  $("#bisotab >tbody tr").length;
      		boqAutocomplete(belowRowCount,"bisotab");
      		AutocompleteOnLoad()
		}
		if (position =="above"){
			$("#bisotab > tbody tr").eq(rowindx).before(markup);
			
			aboveRowCount =  rowindx+1;
      		boqAutocomplete(aboveRowCount,"bisotab");
      		AutocompleteOnLoad()
		}
		
		var myDiv = document.getElementById("bisotab");
    	    myDiv.scrollTop = myDiv.scrollHeight;
    	    
    	var rowCount = document.getElementById('bisotab').rows.length;
 			          rowCount=--rowCount;
  			          $("#totqty").val(rowCount);  
  			        boqAutocomplete(rowCount,"bisotab");
  			        AutocompleteOnLoad()
    	   
            
    	     	    
} // end add new row  	


//boqAutocomplete fct
function boqAutocomplete(rowCnt,tableIndx){

	var ctx=getContextPath();
	var tableID=tableIndx;
	
		
	
	//source autocomplete
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="source"]').autocomplete({
	source: function(request, response) {


			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/GetAllLocations',
			data: {
				"location" : request.term
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.listLocations);

				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
		
  if(ui.item[0] == $(this).parents("tr").find('input[name ="destinationID"]').val() ){
			this.value = "";
			$(this).parents("tr").find('input[name ="sourceID"]').val("");
			$(this).parents("tr").find('input[name ="sourceType"]').val("");
					 	 
	}
else{
	
			this.value = (ui.item ? ui.item[1] : '');
			$(this).parents("tr").find('input[name ="sourceID"]').val(ui.item[0]);
			$(this).parents("tr").find('input[name ="sourceType"]').val(ui.item[2]);
					 	 
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	// unsaved = true;
}

			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	

		 	return $('<li class="each"></li>').data( "item.autocomplete", item )
 		    	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
 	             item[0] + '</span><br><span class="desc">'  +
 	             item[1] +'</span><span class="desc">'+";" + 
 	             item[2] + '</span></div>').appendTo(ul);

	};
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="source"]').focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	}); // end source autocomplete
	
	
		//sourceID autocomplete
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="sourceID"]').autocomplete({
	source: function(request, response) {


			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/GetAllLocations',
			data: {
				"location" : request.term
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.listLocations);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
  if(ui.item[0] == $(this).parents("tr").find('input[name ="destinationID"]').val() ){
			this.value = "";
			$(this).parents("tr").find('input[name ="sourceID"]').val("");
			$(this).parents("tr").find('input[name ="sourceType"]').val("");
					 	 
	}
else{
	
			this.value = (ui.item ? ui.item[1] : '');
			$(this).parents("tr").find('input[name ="sourceID"]').val(ui.item[0]);
			$(this).parents("tr").find('input[name ="sourceType"]').val(ui.item[2]);
					 	 
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

}
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	

		 	return $('<li class="each"></li>').data( "item.autocomplete", item )
 		    	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
 	             item[0] + '</span><br><span class="desc">'  +
 	             item[1] +'</span><span class="desc">'+";" + 
 	             item[2] + '</span></div>').appendTo(ul);

		
	};
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="sourceID"]').focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	}); // end source id autocomplete
	
			//destination ID autocomplete
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="destinationID"]').autocomplete({
	source: function(request, response) {


			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/GetAllLocations',
			data: {
				"location" : request.term
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.listLocations);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
  if(ui.item[0] == $(this).parents("tr").find('input[name ="sourceID"]').val() ){
			this.value = "";
			$(this).parents("tr").find('input[name ="destinationID"]').val("");
			$(this).parents("tr").find('input[name ="destinationType"]').val("");
					 	 
	}
else{
	
			this.value = (ui.item ? ui.item[1] : '');
			$(this).parents("tr").find('input[name ="destinationID"]').val(ui.item[0]);
			$(this).parents("tr").find('input[name ="destinationType"]').val(ui.item[2]);
					 	 
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	// unsaved = true;
}
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	

		 	return $('<li class="each"></li>').data( "item.autocomplete", item )
 		    	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
 	             item[0] + '</span><br><span class="desc">'  +
 	             item[1] +'</span><span class="desc">'+";" + 
 	             item[2] + '</span></div>').appendTo(ul);

		
	};
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="destinationID"]').focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	}); // end destinationID autocomplete
	
	
				//destination  autocomplete
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="destination"]').autocomplete({
	source: function(request, response) {


			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/GetAllLocations',
			data: {
				"location" : request.term
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.listLocations);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
  if(ui.item[0] == $(this).parents("tr").find('input[name ="sourceID"]').val() ){
			this.value = "";
			$(this).parents("tr").find('input[name ="destinationID"]').val("");
			$(this).parents("tr").find('input[name ="destinationType"]').val("");
					 	 
	}
else{
	
			this.value = (ui.item ? ui.item[1] : '');
			$(this).parents("tr").find('input[name ="destinationID"]').val(ui.item[0]);
			$(this).parents("tr").find('input[name ="destinationType"]').val(ui.item[2]);
					 	 
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

}
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	

		 	return $('<li class="each"></li>').data( "item.autocomplete", item )
 		    	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
 	             item[0] + '</span><br><span class="desc">'  +
 	             item[1] +'</span><span class="desc">'+";" + 
 	             item[2] + '</span></div>').appendTo(ul);

		
	};
	
	$('#'+tableID+' > tbody > tr').eq(rowCnt-1).find('input[name ="destination"]').focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	}); // end destinationID autocomplete
	
	

} // end boqAutocomplete fct

    			 
// Next Fct in popup
function nextRow(){

  // Get total nb of rows
   var rowCount = $("#bisotab >tbody tr").length;
   
	      		        	 
   rowindx++ ;
   var nextIndex = parseInt(rowindx);
   
	     
	if(rowindx >= 0 && rowindx < rowCount) {
		
		sendValBoqToPopup(nextIndex);
		AutocompleteOnLoad();
	   	}			 
   
   // Add new row when rowindx exceed the row count
   	else if (rowindx >= rowCount) {
    	
    	//rowindx = nextIndex;
    	addNewRow("next");	  
	 	sendValBoqToPopup(nextIndex);
	 	AutocompleteOnLoad(); 
	 }	

}// End next fct in popup

// Insert row below fct

 function insertRowBelow(){
		addNewRow("below");
		
		rowindx++ ;
		var belowIndex = parseInt(rowindx);   
		
		sendValBoqToPopup(belowIndex);
		 AutocompleteOnLoad();
		
   } // End insertRowBelow fct in popup
   
// Insert Row Above fct

 function insertRowAbove(){
 
 	addNewRow("above");
 	sendValBoqToPopup(rowindx);
	AutocompleteOnLoad();

	            
   }// End insertRowAbove fct in popup
   
// Delete row fct   
 function deleteBoqRow() {

	
	rowindx++;
	$("tr").eq(rowindx).remove();
	
	// Get Nb of rows after delete 
	var rowCount = $("#bisotab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);
	   	 
	rowindx--;
	 
	if (rowindx == 0 && rowCount == 0) {

		$("#trModal").modal("hide");
		
	                
	}
 
 
	else if(rowindx >= 0 && rowindx < rowCount) {
	
		sendValBoqToPopup(rowindx);
		AutocompleteOnLoad();
	}

	// Show previous record 
	else if (rowindx >= rowCount){

		rowindx--;

		sendValBoqToPopup(rowindx);
		AutocompleteOnLoad();
	}
   var rowCount = document.getElementById('bisotab').rows.length;
  rowCount=--rowCount;
  $("#totqty").val(rowCount);  

}// End Delete fct  
  
  
  								
function checkSerial() {
	 var serialMsID = "";
		 var Data = {};
	 	 var serial_no="";
	 	 var msisdn ="";
	 	 var simID = "";
	 	 var serialItemID = "";
	 	 Data.serialArray=[];
	 	 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){
		 // Remove the row where SerialNumber is empty
   		 rowcountSerial =  $("#serialNoTable >tbody tr").length;
    	var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input')[0].value;
    	msisdn = $(this).parent().parent().children('td[name="msisdn"]').children('input').val();
    	simID = $(this).parent().parent().children('td[name="id"]').children('input').val();
		if (serNum == '' || msisdn == '' || simID == '') {
		$(this).parents("tr").remove();
		 rowcountSerial=--rowcountSerial;
	     $("#totNmbr").val(rowcountSerial);
	 	 $("#simCardsQty").val(rowcountSerial);
         }
	     else {
 
	        var serNum_value = $(this).parent().parent().children('td[name="serialNumber"]').children('input')[0].value;
	        var msisdn_value = $(this).parent().parent().children('td[name="msisdn"]').children('input').val();
	    	var id_value = $(this).parent().parent().children('td[name="id"]').children('input').val();
	    	var serialItemId_value = $(this).parent().parent().children('td[name="serialId"]').children('input').val();
			    serial_no = serNum_value;
			    msisdn = msisdn_value;
			    simID = id_value;
			    serialItemId = serialItemId_value;
			    Data.serialArray.push({serial_no:serNum_value, msisdn:msisdn_value, simID:id_value,serialItemId:serialItemId_value });
				 }
		  	});

	    $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input')[0].value = JSON.stringify(Data);

} // end check serial fct

	 function getAllSimIdSerial(){
	 	SimArray = [];
	 	var sim = "";
	 	$("input[name=id]").each(function(){
	 		if($(this).val() == "")
	 			sim = "empty";
	 		else
	 			sim = $(this).val();
	 		SimArray.push(sim);
	 	});
	 	return SimArray;   
	 }//end get all sim id serial function
	 
	 
	
 function addRowSerial(){
 
	checkSerial();
	rowcountSerial =  $("#serialNoTable >tbody tr").length;
	console.log("rowcountSerial:" +rowcountSerial);
	
    var ctx = getContextPath();
	
	var markup = "<tr><td><input type='checkbox' style='width: 14px;height: 13px;position:relative;left:6px;'  name='record'><span class='dotStatus' id='dotStatusBoq' onclick='showMsgSerial(this)' style='position:relative;left:3px;'></span></td>"
	+"<td name='serialNumber'><input name='serialNumber' id=" + "serialNumber_" + rowcountSerial +"  class='form-control text-input' type='text' style='width:200px;position:relative;left:11px;'/></td>"
	+ "<td name='msisdn' style='width:200px'> <input name='msisdn' id=" + "msisdn_" + rowcountSerial +" style='width:200px;position:relative;left:11px;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
	+ "<td name='id'  style='width:200px'> <input name='id'  style='width:200px;position:relative;left:11px;' id=" + "id_" + rowcountSerial +" type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td>"
	+ "<td name='serialId' hidden  style='width:200px'> <input name='serialId' hidden  style='width:200px;position:relative;left:11px;' readonly type='text' value=0 class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td>"
	+"</tr>";
	$("#serialNoTable > tbody").append(markup);
	
		AutocompleteOnLoad();

        rowcountSerial=++rowcountSerial;
  		$("#totNmbr").val(rowcountSerial); 
  		$("#simCardsQty").val(rowcountSerial);
 }
 
 
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}      

	function AutocompleteOnLoad() {	 
		var ctx = getContextPath();

		  $("input[name='serialNumber']").each(function(i, el) {
			  $(el).autocomplete({
			  source: function(request, response, event, ui) {
			   	  
			  var location  = $('#popupSourceID').val();
			  if(location == ""){
				  location  ="empty";
			  }
			  else {
				  location  != "empty";										   
				  location  =$('#popupSourceID').val();
			  }	 
			  
	  
		  $.ajax({ 
			  type: "GET",
			  contentType: "application/json; charset=utf-8",
			  url: ctx+'/GetAllSimSerials',
			  data: {
					  "serialNumber" : request.term,
					  "location":location,
		  },
			  dataType: "json",
			  success: function (data) {
									   
			  if (data != null) {
				  response(data.ListSim);
			  }
			  },
			  error: function(result) {
				  console.log(222);
								   
			  }
			  });
			  }, minLength:0, maxShowItems: 4, scroll:true,
			  select: function(event, ui) {
				simIDs=getAllSimIdSerial();
				var n = simIDs.includes(ui.item[2]);

			if(n){
	 			if(this.value != ''){
					if(ui.item[0].includes(this.value)){
	 	 			 this.value = (ui.item ? ui.item[0]  : '');
					 $(this).parents("tr").find('input[name ="msisdn"]').val(ui.item[1]);				
					 $(this).parents("tr").find('input[name ="id"]').val(ui.item[2]);
					 }
				}
	  
				   alert("Serial: "+ui.item[0]+"\nMSISDN: "+ui.item[1]+"\nSimID: "+ui.item[2]+"\nAlready Exist!");
					
		    }else{
		  this.value = (ui.item ? ui.item[0]  : '');
		   $(this).parents("tr").find('input[name ="msisdn"]').val(ui.item[1]);				
		   $(this).parents("tr").find('input[name ="id"]').val(ui.item[2]);							
		  $("#formStatus").text("Not Saved");
		  $('.dot').css({"background-color" : "orange"});
	  }				  
	  
					  return false;
			  }
			  }).autocomplete("instance")._renderItem = function(ul, item) {
						   
					  return $('<li class="each"></li>').data( "item.autocomplete", item )
						.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						  item[0] + "</span><br><span class='desc'>" +
						  item[1] + "</span><span class='desc'>" +";"+
						  item[2] + "</span></div>")
				  .appendTo(ul);
				
			   };
			  $("input[name='serialNumber']").focus(function(){
			  
			  $("#formStatus").text("Not Saved");
			  $('.dot').css({"background-color" : "orange"});
				  
				  if (this.value == ""){
					  $(this).autocomplete("search");
					 }
		  
			  });
		   });
		   
		  $("input[name='msisdn']").each(function(i, el) {
					  $(el).autocomplete({
	  
		  source: function(request, response, event, ui) {
						  
			  var location  = $('#popupSourceID').val();
			  if(location == ""){
				  location  ="empty";
			  }
			  else {
				  location  != "empty";										   
				  location  =$('#popupSourceID').val();
	  
			  }	 
							   
		  $.ajax({
			  type: "GET",
			  contentType: "application/json; charset=utf-8",
			  url:ctx+'/GetAllSimMSISDN',
			  data: {
					  "msisdn" : request.term,
					  "location":location,
			  },
			  dataType: "json",
			  success: function (data) {
				  if (data != null) {
					  response(data.ListSim);
				  }
			  },
			  error: function(result) {
				  console.log(222);
			  }
		  });
		  }, minLength:0, maxShowItems: 4, scroll:true,
		  select: function(event, ui) {
					simIDs=getAllSimIdSerial();
					var n = simIDs.includes(ui.item[2]);
				  if(n){
				  if(this.value != ''){
				 if(ui.item[1].includes(this.value)){
				 this.value = (ui.item ? ui.item[1]  : '');
				 $(this).parents("tr").find('input[name ="serialNumber"]').val(ui.item[0]);				
				 $(this).parents("tr").find('input[name ="id"]').val(ui.item[2]);
				 }
				}
			 alert("Serial : "+ui.item[0]+"\nAnd MSISDN : "+ui.item[1]+"\nAlready Exist !");
		 	 
				}
					  else{
					  this.value = (ui.item ? ui.item[1]  : '');
					   $(this).parents("tr").find('input[name ="serialNumber"]').val(ui.item[0]);				
					   $(this).parents("tr").find('input[name ="id"]').val(ui.item[2]);
								
		  $("#formStatus").text("Not Saved");
		  $('.dot').css({"background-color" : "orange"});
	  }
	  
				  return false;
		  }
		  }).autocomplete("instance")._renderItem = function(ul, item) {
						  
		  return $('<li class="each"></li>').data( "item.autocomplete", item )
							.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						  item[1] + "</span><br><span class='desc'>" +
						  item[0] + "</span><span class='desc'>" +";"+
						  item[2] + "</span></div>")
				  .appendTo(ul);
					
		  };
			  $("input[name='msisdn']").focus(function(){
			  
			  $("#formStatus").text("Not Saved");
			  $('.dot').css({"background-color" : "orange"});
			  
				  if (this.value == ""){
					  $(this).autocomplete("search");
					 }
		  
			  });
		   });	 
		   
		   		  $("input[name='id']").each(function(i, el) {
					  $(el).autocomplete({
	  
		  source: function(request, response, event, ui) {
						  
			  var location  = $('#popupSourceID').val();
			  if(location == ""){
				  location  ="empty";
			  }
			  else {
				  location  != "empty";										   
				  location  =$('#popupSourceID').val();
	  
			  }	 
							   
		  $.ajax({
			  type: "GET",
			  contentType: "application/json; charset=utf-8",
			  url:ctx+'/GetAllSimID',
			  data: {
					  "id" : request.term,
					  "location":location,
			  },
			  dataType: "json",
			  success: function (data) {
				  if (data != null) {
					  response(data.ListSim);
				  }
			  },
			  error: function(result) {
				  console.log(222);
			  }
		  });
		  }, minLength:0, maxShowItems: 4, scroll:true,
		  select: function(event, ui) {
					simIDs=getAllSimIdSerial();
					var n = simIDs.includes(ui.item[2]);
					if(n){
	  
					   alert("Serial: "+ui.item[0]+"\nMSISDN: "+ui.item[1]+"\nSimID: "+ui.item[2]+"\nAlready Exist!");
								
						  }
					  else{
					  this.value = (ui.item ? ui.item[2]  : '');
					   $(this).parents("tr").find('input[name ="serialNumber"]').val(ui.item[0]);				
					   $(this).parents("tr").find('input[name ="msisdn"]').val(ui.item[1]);
								
		  $("#formStatus").text("Not Saved");
		  $('.dot').css({"background-color" : "orange"});
	  }
	  
				  return false;
		  }
		  }).autocomplete("instance")._renderItem = function(ul, item) {
						  
		  return $('<li class="each"></li>').data( "item.autocomplete", item )
							.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						  item[2] + "</span><br><span class='desc'>" +
						  item[1] + "</span><span class='desc'>" +";"+
						  item[0] + "</span></div>")
				  .appendTo(ul);
					
		  };
			  $("input[name='id']").focus(function(){
			  
			  $("#formStatus").text("Not Saved");
			  $('.dot').css({"background-color" : "orange"});
			  
				  if (this.value == ""){
					  $(this).autocomplete("search");
					 }
		  
			  });
		   });
							 } 
   

$(function(){
var ctx = getContextPath();
	
// Add new row in serialNo table using Enter key 
 $("#serialNoTable > tbody").keyup(function (event)  {
	if (event.keyCode == 13) {
		addRowSerial(); 
	}
 }); // End Add row using enter key 
 
	
   
// Resize and drag the popup 

	$('.modal-content').resizable({
	 	handles: "e" ,

    });

   $('.modal-dialog').draggable({
	  handle: ".modal-header, .modal-footer"
   });

   $('#trModal').on('show.bs.modal', function() {
	  $(this).find('.modal-body').css({
	  'max-height': '100%',
    });
  });
 
//Reset the popup to original size after resizing 
			       
   $('#trModal').on('hidden.bs.modal', function() {
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
   
   // Send concatenated Serial Table rows to boq table on any change in td
  $('#serialNoTable').on('focusout', function() {
	 sendValPopupToBoq(rowindx);
	}); // end fct
   
 //Send input values from popup to boq when any popup input change
 $('#popupSourceType, #popupSource,#popupSourceID,#popupSourceMSISDN,#popupDestinationType,#popupDestination,#popupDestinationID,#popupCreationDate,#popupLastModifiedDate,#popupItemID,#totNmbr').on('change', function() {
      
    sendValPopupToBoq(rowindx);
 
      });
      
       $('#popupSourceType, #popupSource,#popupSourceID,#popupSourceMSISDN,#popupDestinationType,#popupDestination,#popupDestinationID,#popupCreationDate,#popupLastModifiedDate,#popupItemID,#totNmbr').on('focusout', function() {
      
    sendValPopupToBoq(rowindx);
 
      });
   
// Close popup function  				
  $("button[name='closePopup']").on("click", function(){
	 
	
  //  Send input values from popup to boq table using close button 
  	sendValPopupToBoq(rowindx);
	    					
	  $("#trModal").modal("hide");

		 	
	});   // end close fct
	
// Close popup using ESC
	document.addEventListener('keydown', function(event){
	  if(event.key === "Escape"){
		
	    
	// Send input values from popup to boq table and close the popup using ESC 
	sendValPopupToBoq(rowindx);
	
		
		$("#trModal").modal("hide");

	   }
   });// end close fct using esc
   

// Prev fct in popup
 $("button[name='previousRow']").on("click", function(){
	
	if(rowindx > 0) {
		
		rowindx-- ;
	    var PrevIndex = parseInt(rowindx);
	    sendValBoqToPopup(PrevIndex);

	   
	 }
   
   // Close popup on row 0 (end of prev)
	 else if (rowindx == 0) {
	     $("#trModal").modal("hide");
	    }
  });   // end prev fct in popup
 
        

  });

  