var rowIndxService=0;
var rowIndxSurvey= 0;


function addServiceRow(target){
	
	var rowCount= $("#customerServicesTable >tbody tr").length;

	var markup = "<tr><td style='text-align:center;width:100px'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openServicePopup(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	           	+"<td  name='serviceId'><input type='text' class='form-control text-input'/></td>"
	           
	           	+"<td style='width:280px' name='serviceType'><select class='form-control' name='serviceType'><option value='None' selected>Select an Option</option><option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' >Kenstream</option><option value='KNL' >KNL</option><option value='LNK' >LNK</option><option value='MS' >MS</option><option value='Rent' >Rent</option><option value='SAT' >SAT</option><option value='TEL_ISDN' >TEL_ISDN</option></select></td>"
			
				+"<td style='display:none' name='billingCode'><input type='text' class='form-control text-input'/></td>"
	           	+"<td style='display:none' name='circuitNo'><input type='text'  class='form-control text-input' /></td>"
	           	+"<td name='longitude'><input type='text'  class='form-control text-input' /></td>"
	           	+"<td name='latitude'><input type='text' class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='custServiceID'><input type='text' value ='' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='customerID'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td  name='creationDate'><input  type='text'  class='form-control text-input' readonly /></td>"
	           	+"<td  name='lastModDate'><input type='text'  class='form-control text-input' readonly /></td>"
	           	+"<td class='hidden-td' name='category'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='circuitID'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='refID'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='num'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='mediaType'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='txMedia'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='status'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='capacityMB'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='regionID'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='linkName'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='radioID'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='radioName'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td class='hidden-td' name='portDesc'><input type='text' hidden class='form-control text-input' /></td>"
	           	+"<td name='createSurvey'  style='width:150px;'><button type='button' disabled >Create Survey</button></td>"
	           	+"</tr>";
	
	var myDiv = document.getElementById("customerServicesTable");
	myDiv.scrollTop = myDiv.scrollHeight;
	
	if (target == "addNewRow"){
	    $("#customerServicesTable > tbody").append(markup);
	    rowCount++;
	}
	if(target == "addRowBelow"){
		$("#customerServicesTable > tbody tr").eq(rowIndxService).after(markup);
	    rowCount++;
	 }
	if(target == "addRowAbove"){
	    $("#customerServicesTable > tbody tr").eq(rowIndxService).before(markup);
	    rowCount= rowIndxService+1;
	 }
	
}
function openServicePopup(element) {

	 var buttonRowIndx = $(element).closest("tr");
	 rowIndxService = (buttonRowIndx[0].rowIndex - 1);

		$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
		$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
		$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
		$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
		$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
		$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
		$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
		$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
		$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
		$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
		$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
		$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
		$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
		$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
		$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
		$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
		$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
		$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
		$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
		$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
		$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
		$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		

		$("#customerServicePopup").modal("show");

		 var element = document.getElementById("popupHeader");
         element.innerHTML = "Service # " +(rowIndxService+1);
}

function openSurveyPopup(element) {

	 var buttonRowIndx = $(element).closest("tr");
	 rowIndxSurvey = (buttonRowIndx[0].rowIndex - 1);

	var survID = $("#customerSurveysTable >tbody").find("tr").eq(rowIndxSurvey).find('td[name="surveyID"]').children('input').val();		

	$('#surveyPopupLongitude').val($("#customerSurveysTable >tbody").find("tr").eq(rowIndxSurvey).find('td[name="surveyLongitude"]').children('input').val());
	$('#surveyPopupLatitude').val($("#customerSurveysTable >tbody").find("tr").eq(rowIndxSurvey).find('td[name="surveyLatitude"]').children('input').val());		
	$('#surveyPopupCustID').val($("#customerID").val());
	$('#popupSurveyID').val($("#customerSurveysTable >tbody").find("tr").eq(rowIndxSurvey).find('td[name="surveyID"]').children('input').val());		
	$('#surveyPopupCreationDate').val($("#customerSurveysTable >tbody").find("tr").eq(rowIndxSurvey).find('td[name="surveyCreationDate"]').children('input').val());		


	$.ajax({
		type : "GET",
		url: getContextPath()+'/GetSurveyDetails',
		dataType : "json",
		data : {
			"survID":survID,   
											
		},
		success : function(data) {
			appendManholes(data.manholeList);
			appendHandholes(data.handholeList);
			appendDBs(data.dbList);
			appendNodes(data.nodeList);
			appendCables(data.cablesList);
			appendTubes(data.tubesList);
			appendStrands(data.strandsList)

					
		},
		error : function(error) {
			console.log("The error is " + error);
		}
	});

		$("#surveyPopup").modal("show");

		
}
function appendManholes(result){
		var markupManh="";
		document.getElementById("findNearestManRes").innerHTML = "";
		$("#manholeSurveyRes").empty();
		$("#manholeSurveyRes").html("");

										
		if (result.length==0){
			document.getElementById("findNearestManRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		}
		else {
			for(var i =0 ; i<result.length;i++){
					markupManh +="<tr style='height: 30px;'><td name='ID' ><input name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td><td name='name' style='min-width:250px;'><input name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;' name='linearDistance' ><input name='linearDistance' style='border: none;' value='"+result[i][4]+"' readonly></input></td><td style='width:100px;' name='drivingDistance' ><input name='drivingDistance' style='border: none;' value='"+result[i][5]+"' readonly></input></td><td name='geoDistance' ><input name='geoDistance' style='border: none;' value='"+result[i][6]+"' readonly></input></td></tr>"
			  }
		}		
		$("#manholeSurveyRes").append(markupManh);	
		$("#totalManhole").val(result.length);
  }

function appendDBs(result){
	var markupDBoard="";
	document.getElementById("findNearestDbRes").innerHTML = "";
	$("#dbSurveyRes").empty();
	$("#dbSurveyRes").html("");

												
		if (result.length==0){
			document.getElementById("findNearestDbRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		}
		else {
			for(var i =0 ; i<result.length;i++){
				markupDBoard +="<tr style='height: 30px;'><td name='ID' ><input name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td><td name='name' style='min-width:250px;'><input name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;' name='linearDistance' ><input name='linearDistance' style='border: none;' value='"+result[i][4]+"' readonly></input></td><td style='width:100px;' name='drivingDistance' ><input name='drivingDistance' style='border: none;' value='"+result[i][5]+"' readonly></input></td><td name='geoDistance' ><input name='geoDistance' style='border: none;' value='"+result[i][6]+"' readonly></input></td></tr>"
			  }
		}		
		$("#dbSurveyRes").append(markupDBoard);	
		$("#totalDB").val(result.length);
  }
function appendNodes(result){
	var markupNode="";		
	document.getElementById("findNearestNodeRes").innerHTML = "";
	$("#nodeSurveyRes").empty();
	$("#nodeSurveyRes").html("");


													
		if (result.length==0){
			document.getElementById("findNearestNodeRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		}
		else {
			for(var i =0 ; i<result.length;i++){
				markupNode +="<tr style='height: 30px;'><td name='ID' ><input name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td><td name='name' style='min-width:250px;'><input name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;' name='linearDistance' ><input name='linearDistance' style='border: none;' value='"+result[i][4]+"' readonly></input></td><td style='width:100px;' name='drivingDistance' ><input name='drivingDistance' style='border: none;' value='"+result[i][5]+"' readonly></input></td><td name='geoDistance' ><input name='geoDistance' style='border: none;' value='"+result[i][6]+"' readonly></input></td></tr>"
			  }
		}		
		$("#nodeSurveyRes").append(markupNode);	
		$("#totalNode").val(result.length);
  }

function appendCables(result){
		var markupFiber="";	
		$("#fiberSurveyRes").html("");	
										
		if (result.length==0){
			markupFiber ="<tr style='height:20px;color:#ff0000;font-size: 1.2em;'><td>There is no result<td></tr>"
		}
		else {
			for(var i =0 ; i<result.length;i++){
				 markupFiber +="<tr ><td style='min-width: 150px;' class='row-pad' name='ID'><input name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td><td style='min-width: 150px;' name='name'><input name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='min-width: 350px;' name='source'><input name='source' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='min-width: 350px;' name='destination'><input name='destination' style='border: none;' value='"+result[i][3]+"' readonly></input ></td></tr>"
			 }
		}		
		$("#fiberSurveyRes").append(markupFiber);	
  }
function appendTubes(result){
		var markupTube="";	
		$("#tubeSurveyRes").html("");	
										
		if (result.length==0){
			markupTube ="<tr style='height:20px;color:#ff0000;font-size: 1.2em;'><td>There is no result<td></tr>"
		}
		else {
			for(var i =0 ; i<result.length;i++){
				 markupTube +="<tr ><td style='min-width: 150px;' class='row-pad' name='ID'><input name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td><td style='min-width: 150px;' name='name'><input name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='min-width: 350px;' name='source'><input name='source' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='min-width: 350px;' name='destination'><input name='destination' style='border: none;' value='"+result[i][3]+"' readonly></input ></td></tr>"
			 }
		}		
		$("#tubeSurveyRes").append(markupTube);	
  }
function appendStrands(result){
		var markupStrand="";	
		$("#strandSurveyRes").html("");	
										
		if (result.length==0){
			markupStrand ="<tr style='height:20px;color:#ff0000;font-size: 1.2em;'><td>There is no result<td></tr>"
		}
		else {
			for(var i =0 ; i<result.length;i++){
				 markupStrand +="<tr ><td style='min-width: 150px;' class='row-pad' name='ID'><input name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td><td style='min-width: 150px;' name='name'><input name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='min-width: 350px;' name='source'><input name='source' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='min-width: 350px;' name='destination'><input name='destination' style='border: none;' value='"+result[i][3]+"' readonly></input ></td></tr>"
			 }
		}		
		$("#strandSurveyRes").append(markupStrand);	
  }
function appendHandholes(result){
		var markupHandh="";		
		document.getElementById("findNearestHandRes").innerHTML = "";
		$("#handholeSurveyRes").empty();
		$("#handholeSurveyRes").html("");

								
		if (result.length==0){
			document.getElementById("findNearestHandRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		}
		else {
			for(var i =0 ; i<result.length;i++){
					markupHandh +="<tr style='height: 30px;'><td name='ID' ><input name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td><td name='name' style='min-width:250px;'><input name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;' name='linearDistance' ><input name='linearDistance' style='border: none;' value='"+result[i][4]+"' readonly></input></td><td style='width:100px;' name='drivingDistance' ><input name='drivingDistance' style='border: none;' value='"+result[i][5]+"' readonly></input></td><td name='geoDistance' ><input name='geoDistance' style='border: none;' value='"+result[i][6]+"' readonly></input></td></tr>"
			  }
		}		
		$("#handholeSurveyRes").append(markupHandh);	
		$("#totalHandhole").val(result.length);

  }


function insertServiceBelow(){

  addServiceRow('addRowBelow');
  rowIndxService++ ;

   var belowIndex = parseInt(rowIndxService);
	$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
	$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
	$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
	$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
	$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
	$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
	$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
	$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
	$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
	$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
	$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
	$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
	$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
	$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
	$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
	$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
	$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
	$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
	$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
	$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
	$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
	$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		
	
	//Update popup Nb in header
	var element = document.getElementById("popupHeader");
	element.innerHTML = "Service # " +parseInt(belowIndex+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
 }
function insertServiceAbove(){

  addServiceRow('addRowAbove');

	$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
	$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
	$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
	$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
	$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
	$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
	$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
	$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
	$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
	$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
	$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
	$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
	$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
	$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
	$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
	$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
	$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
	$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
	$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
	$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
	$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
	$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		
	
	//Update popup Nb in header
	var element = document.getElementById("popupHeader");
	element.innerHTML = "Service # " +parseInt(rowIndxService);	

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}


function nextServiceRow(){

  // Get total nb of rows
  var rowCount = $("#customerServicesTable >tbody tr").length;
  rowIndxService++ ;
  var nextIndex = parseInt(rowIndxService);

  if(rowIndxService >= 0 && rowIndxService < rowCount) {

	$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
	$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
	$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
	$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
	$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
	$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
	$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
	$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
	$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
	$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
	$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
	$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
	$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
	$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
	$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
	$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
	$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
	$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
	$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
	$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
	$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
	$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		
	
	//Update popup Nb in header
	var element = document.getElementById("popupHeader");
	element.innerHTML = "Service # " +(nextIndex+1);
  }

  // Add new row when rowindxActions exceed the row count
  else if (rowIndxService >= rowCount) {
		
	addServiceRow('addNewRow');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

	$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
	$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
	$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
	$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
	$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
	$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
	$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
	$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
	$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
	$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
	$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
	$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
	$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
	$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
	$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
	$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
	$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
	$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
	$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
	$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
	$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
	$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		
	
	//Update popup Nb in header
	var element = document.getElementById("popupHeader");
	element.innerHTML = "Service # " +(nextIndex+1);
	}
}
function previousServiceRow() {

	if(rowIndxService > 0) {
		rowIndxService-- ;
		var PrevIndex = parseInt(rowIndxService);
		
		$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
		$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
		$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
		$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
		$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
		$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
		$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
		$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
		$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
		$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
		$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
		$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
		$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
		$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
		$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
		$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
		$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
		$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
		$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
		$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
		$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
		$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		
		
		//Update popup Nb in header
		 var element = document.getElementById("popupHeader");
		 element.innerHTML = "Service # " +(PrevIndex+1);
	  }
	 // Close popup on row 0 (end of prev fct)
	else if (rowIndxService == 0) {
	    $("#customerServicePopup").modal("hide");
	}
  } 

function validateCustomerServiceInputs() {
	
	validation="false";
	$("#customerServicesTable > tbody ").find('input[name="record"]').each(function(){
		
		
		var servType=$(this).parent().parent().children('td[name="Index"]').children('select').val();
		var lng =$(this).parent().parent().children('td[name="longitude"]').children('input').val();
		var lat =$(this).parent().parent().children('td[name="latitude"]').children('input').val();
		
		if(isNaN(lng) ==true ){
				alert("Incorrect Format of longitude.");
				validation="true";
				return false;
		}
		if(lng =="" ){
			alert("Longitude cannot be empty.");
			validation="true";
			return false;
		}
		if(lat =="" ){
			alert("Latitude cannot be empty.");
			validation="true";
			return false;
		}

		if(isNaN(lat) ==true ){
			alert("Incorrect Format of latitude.");
			validation="true";
			return false;
		}
				
	})
	return validation;
}

function deleteBoqServiceRow() {
	 
	slctDelArray.push($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());
	$("#customerServicesTable >tbody").find("tr").eq(rowIndxService).remove();
	
	// Get Nb of rows after delete
	var rowServiceCount = $("#customerServicesTable >tbody tr").length;
	
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});

	 if (rowIndxService == 0 && rowServiceCount ==0) {
		  $("#customerServicePopup").modal("hide");
	  }
	  else if(rowIndxService >= 0 && rowIndxService < rowServiceCount) {		
		
		$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
		$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
		$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
		$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
		$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
		$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
		$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
		$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
		$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
		$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
		$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
		$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
		$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
		$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
		$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
		$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
		$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
		$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
		$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
		$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
		$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
		$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		

		//Update popup Nb in header 
		var element = document.getElementById("popupHeader");
		element.innerHTML = "Service # " +(rowIndxService+1);  
	  }
	  // Show previous record 
	   else if (rowIndxService >= rowServiceCount){
			rowIndxService--;
			$('#popupServiceType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val());
			$('#popupBillingCode').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val());
			$('#popupCircuitNo').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val());
			$('#popupLongitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val());
			$('#popupLatitude').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val());		
			$('#custServID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val());		
			$('#popupCustomerID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val());		
			$('#serviceCreationDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val());		
			$('#servicelstModfDate').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val());		
			$('#popupServiceCat').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val());		
			$('#popupCircuitID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val());		
			$('#popupRefID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val());		
			$('#popupNum').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val());		
			$('#popupMedType').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val());		
			$('#popupTxMedia').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val());		
			$('#popupStatus').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val());		
			$('#popupCapacityMB').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val());		
			$('#popupRegionID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val());		
			$('#popupLinkName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val());		
			$('#popupRadioID').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val());		
			$('#popupRadioName').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val());		
			$('#popupPortDesc').val($("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val());		

			//Update popup Nb in header 
			var element = document.getElementById("popupHeader");
			element.innerHTML = "Service # " +(rowIndxService+1);  
		}
	}// End delete fct 
	
	
function createSurvey(serviceID,customerID,customerName,longitude,latitude,serviceRef){
	
	
	var urlString = "";
	checkedOption = "circleRange"; 
	urlString += "&closestLatPoint="+latitude+"";
	urlString += "&closestLongPoint="+longitude+"";
	urlString += "&closestDisRange="+50+"";
	urlString += "&noP=";
	urlString += "&getRelatedPoints="+0+"";	 
	urlString += "&CustomerID="+customerID+"";	
	urlString += "&CustomerName="+customerName+"";	
	urlString += "&serviceReq="+serviceID+"";	
	urlString += "&serviceRef="+serviceRef+"";	

    window.open(getContextPath() + "/NetworkPhysicalLayer?Checked=" + checkedOption + urlString, '_blank');

	
	
	
}

function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

$(function(){
	
// Close popup function
$("button[name='closeCustServicePopup']").on("click", function(){
	  		
	// Send input values from popup to boq table
	 $("#customerServicesTable > tbody").find("tr").eq(rowIndxService).find('td[name="serviceType"]').children('select').val($('#popupServiceType').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="billingCode"]').children('input').val($('#popupBillingCode').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitNo"]').children('input').val($('#popupCircuitNo').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="longitude"]').children('input').val($('#popupLongitude').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="latitude"]').children('input').val($('#popupLatitude').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="custServiceID"]').children('input').val($('#custServID').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="customerID"]').children('input').val($('#popupCustomerID').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="creationDate"]').children('input').val($('#serviceCreationDate').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="lastModDate"]').children('input').val($('#servicelstModfDate').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="category"]').children('input').val($('#popupServiceCat').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="circuitID"]').children('input').val($('#popupCircuitID').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="refID"]').children('input').val($('#popupRefID').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="num"]').children('input').val($('#popupNum').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="mediaType"]').children('input').val($('#popupMedType').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="txMedia"]').children('input').val($('#popupTxMedia').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="status"]').children('input').val($('#popupStatus').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="capacityMB"]').children('input').val($('#popupCapacityMB').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="regionID"]').children('input').val($('#popupRegionID').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="linkName"]').children('input').val($('#popupLinkName').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioID"]').children('input').val($('#popupRadioID').val());
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="radioName"]').children('input').val($('#popupRadioName').val());  
	 $("#customerServicesTable >tbody").find("tr").eq(rowIndxService).find('td[name="portDesc"]').children('input').val($('#popupPortDesc').val());

	$("#customerServicePopup").modal("hide");
}); // end close fct
		    

 $(".delete-row").click(function(){
    var checked="false"; //when no checkbox is checked
	var servicePk ="";
   
	$("#customerServicesTable > tbody").find('input[name="record"]').each(function(){
	
		if($(this).is(":checked")){
			checked="true"; 	 
        	servicePk=$(this).parent().parent().children('td[name="custServiceID"]').children('input').val();
        				
			if(servicePk !=""){
        		slctDelArray.push(servicePk);
      		}   
      		$(this).parents("tr").remove();  
      	  } 
      });
     if(checked=="false"){
       alert(' Select Row(s) to Delete');
    	return false;
      }	                 

   });// end delete row

$(".deleteSurveyRow").click(function(){
    var checked="false"; //when no checkbox is checked
	var surveyPk ="";
   
	$("#customerSurveysTable > tbody").find('input[name="record"]').each(function(){
	
		if($(this).is(":checked")){
			checked="true"; 	 
        	surveyPk=$(this).parent().parent().children('td[name="surveyID"]').children('input').val();
        				
			if(surveyPk !=""){
        		slctSurvDelArray.push(surveyPk);
      		}   
      		$(this).parents("tr").remove();  
      	  } 
      });
     if(checked=="false"){
       alert(' Select Row(s) to Delete');
    	return false;
      }	                 

   });// end delete row
})