function getContextPath() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
 }
 

 
$(function(){

var ctx = getContextPath();
var myTable;


$("#deleteButton").click(  function() {
  var arID = document.getElementById("arcode").value;
	 $.ajax({
		 type : "GET",
		 url : ctx+"/AssetRegistryFormViewDelete",
		 dataType : "json",
		 data : {
			 "arID" : $("#arcode").val()
		 },
		 success : function(data) {
			 location.replace(ctx+"/AssetRegistryListView");
		 },
		 error : function(error) {
			 console.log("The error is " + error);
		 }
	 });
  
})
 $(".delete-row-modelpart").click(function(){
	 
	var checked = false;
	var modPart_pk = "" ;
	
	$("#partmodTab > tbody").find('input[name="record"]').each(function(){
		if($(this).is(":checked")){
			checked = true;
			modPart_pk = $(this).parent().parent().children('td[name="arItemID"]').children('input').val();
			if(modPart_pk != 0){
				slctDelModPart.push(modPart_pk);
				}
			$(this).parents("tr").remove();
		}
	});

	if ( checked == false){
		alert('select row(s) to delete ');
		return false;
	}
				
});

$(".delete-row-node").click(function(){
	 
		  var checked = false;
		  var node_pk = "" ;
			
		  $("#partnodeTab > tbody").find('input[name="record1"]').each(function(){
			  if($(this).is(":checked")){
				  checked = true;
				  node_pk = $(this).parent().parent().children('td[name="arNodeID"]').children('input').val();
				  if(node_pk != 0){
					  slctDelNode.push(node_pk);
					  }
				  $(this).parents("tr").remove();
			  }
		  });

		  if ( checked == false){
			  alert('select row(s) to delete ');
			  return false;
		}	 
	 });

$(".delete-row-site").click(function(){
		 
	var checked = false;
	var site_pk = "" ;
	
	$("#sitetab > tbody").find('input[name="recordsite"]').each(function(){
		if($(this).is(":checked")){
			checked = true;
			site_pk = $(this).parent().parent().children('td[name="arSiteID"]').children('input').val();
			if(site_pk != 0){
				slctDelSite.push(site_pk);
				}
			$(this).parents("tr").remove();
		}
	});

	if ( checked == false){
		alert('select row(s) to delete ');
		return false;
		}
				
});

$(".delete-row-serial").click(function(){
	
		var checked = false;
		var serial_pk = "" ;
		
		$("#ARserialNumberTab > tbody").find('input[name="chekbox_rowSerialNb"]').each(function(){
			if($(this).is(":checked")){
				checked = true;
				serial_pk = $(this).parent().parent().children('td[name="arSerialID"]').children('input').val();
				if(serial_pk != 0){
					slctDelSerial.push(serial_pk);
					}
				$(this).parents("tr").remove();
			}
		});

		if ( checked == false){
			alert('select row(s) to delete ');

			return false;
			}
					
	});

$('body').on('click', '#selectAll-modelpart', function  () {

	if ($(this).hasClass('allChecked')) {
			$('input[name="record"]', '#partmodTab').prop('checked', false);
		} else {
			$('input[name="record"]', '#partmodTab').prop('checked', true);
			}
			$(this).toggleClass('allChecked');
	
});

//select all serial number rows in boq//
$('body').on('click', '#selectAll-serial', function  () {

	if ($(this).hasClass('allChecked')) {
			$('input[name="chekbox_rowSerialNb"]', '#ARserialNumberTab').prop('checked', false);
		} else {
			$('input[name="chekbox_rowSerialNb"]', '#ARserialNumberTab').prop('checked', true);
			}
			$(this).toggleClass('allChecked');


});

//select all site rows in boq//
$('body').on('click', '#selectAll-site', function  () {

	   if ($(this).hasClass('allChecked')) {
			   $('input[name="recordsite"]', '#sitetab').prop('checked', false);
		   } else {
			   $('input[name="recordsite"]', '#sitetab').prop('checked', true);
			   }
			   $(this).toggleClass('allChecked');
  
   
   });

//select all node rows in boq
 $('body').on('click', '#selectAll-node', function  () {

	 if ($(this).hasClass('allChecked')) {
			 $('input[name="record1"]', '#partnodeTab').prop('checked', false);
		 } else {
			 $('input[name="record1"]', '#partnodeTab').prop('checked', true);
			 }
			 $(this).toggleClass('allChecked');
	 
 });
});	



	function addModelPartrow(stat){
	  var ctx = getContextPath();

		
RowCount =  $("#partmodTab >tbody tr").length;
		
 var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   +"<td name = 'itemModel'><input name='itemModel' id = 'itemModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name = 'itemPartNum'><input name='itemPartNum' id = 'itemPartNum"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name ='qtyPartNum' ><input name='qtyPartNum' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name ='arItemID' ><input name='arItemID' readonly value = '0' type='text' style='width:200px;' class='form-control text-input'/></td>"																																	  
		   +"<td name = 'primary'><input name='primary' onclick='onlyOne(this)' type='checkbox'/></td></tr>";
		
		
			$("#partmodTab > tbody").append(markup);
			
	    if(stat == "addRow") {
            PNboqAutocomplete(RowCount,"partmodTab");
     	}
		   
		  
		//set headers of table on top
	
		  myTable = document.getElementById("partmodTab");
		  if (stat == "addRow"){
		 myTable.scrollTop = myTable.scrollHeight;}
	  	 
	  
}   // End of AddRow-ModelPart function






function addSerialrows(stat){
	var ctx = getContextPath();
			
			
	RowCount =  $("#partmodTab >tbody tr").length;		
			
	var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowSerialNb'><button type='button' href='#' name='serialNoPopupMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name = 'SNserialNumber'><input name='inputSerialNb' id = 'inputSerialNb"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNmodel'><input name='inputModel'  id = 'inputModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNpartNumber' ><input name='inputpartNumber' id = 'inputpartNumber"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNsite' ><input name='inputsite'  id = 'inputsite"+RowCount+"'  type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNposition' ><input name='inputPosition' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='arSerialID' ><input name='arSerialID' readonly value = '0' type='text' style='width:200px;' class='form-control text-input'/></td></tr>"

          $("#ARserialNumberTab > tbody").append(markup);		

		   if(stat == "addRow"){
            SeialNBboqAutocomplete(RowCount,"ARserialNumberTab");
		   }
		   
		   //set headers of table on top
	
		  myTable = document.getElementById("ARserialNumberTab");
		  if (stat == "addRow"){
		   myTable.scrollTop = myTable.scrollHeight;}
	  
		}





function addSiterows(stat){
var ctx = getContextPath();

RowCount =  $("#sitetab >tbody tr").length;

var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
 +"<td name = 'wareID'><input name='wareID'  id = 'wareID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'siteID'><input name='siteID' id = 'siteID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'siteName'><input name='siteName' id = 'siteName"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'arSiteID'><input name='arSiteID' readonly value = 0 type='text' style='width:200px;' class='form-control text-input'/></td></tr>";



$("#sitetab > tbody").append(markup);

 if (stat == "addRow"){
      SiteboqAutocomplete(RowCount,"sitetab");
 }
 
 
             //set headers of table on top

		    myTable = document.getElementById("sitetab");
		  if (stat == "addRow"){
		 myTable.scrollTop = myTable.scrollHeight;}

}	






function addNoderows(stat){ 
var ctx = getContextPath();


RowCount =  $("#partnodeTab >tbody tr").length;

 var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record1'><button type = 'button' href='#' name='popUpNodeMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	 +"<td name = 'nodeID'><input name='nodeID' id = 'nodeID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 +"<td name = 'node_Name'><input name='node_Name' id = 'node_Name"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 +"<td name = 'arNodeID'><input name='arNodeID' readonly value = 0 type='text' style='width:200px;' class='form-control text-input'/></td></tr>";



$("#partnodeTab > tbody").append(markup);

	 if (stat == "addRow"){
      NodeboqAutocomplete(RowCount,"partnodeTab");
	}

		 //set headers of table on top
		    myTable = document.getElementById("partnodeTab");
		  if (stat == "addRow"){
		 myTable.scrollTop = myTable.scrollHeight;}
	 
} 







///////////////////////////OpenPopup functions  /////////////////////////////////////////////////////

var NodeRowIndex =0;
var SiteRowIndex = 0;
var SerialRowIndex = 0;
var ModPartRowIndex = 0;

function openNodePopUp(element) {
	var buttonNodeRowIndx = $(element).closest("tr");
	NodeRowIndex = (buttonNodeRowIndx[0].rowIndex - 1);
	
	//Send input values from Boq table to popup
	$('#popupNodeId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
	$('#popupNodeName').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
	$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());

	$("#nodeModal").modal("show");

	var element = document.getElementById("popupNbNode");
    element.innerHTML = "Item # " +(NodeRowIndex+1);
	// SetCalcPopUp();
 }
 
function openSitePopUp(element){
	var buttonSiteRowIndx = $(element).closest("tr");
	SiteRowIndex = (buttonSiteRowIndx[0].rowIndex - 1);
		
	//Send input values from Boq table to popup
	$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
	$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
	$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
	$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());

	    
	 //Update popup Nb in header 
	 var siteElement = document.getElementById("popupNbSite");
	 siteElement.innerHTML = "Item # " +(SiteRowIndex+1);		  
	 
	 $("#siteModal").modal("show");
}
	 
function openSerialPopUp(element){
	var buttonSerialRowIndx = $(element).closest("tr");
	SerialRowIndex = (buttonSerialRowIndx[0].rowIndex - 1);
	
	//Send input values from Boq table to popup
 	$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
    $('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val()); 
    $('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
    $('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
    $('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
    $('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());

	    
	 //Update popup Nb in header 
	 var serialElement = document.getElementById("popupNbSerialno");
	 serialElement.innerHTML = "Item # " +(SerialRowIndex+1);

	$("#serialnoModal").modal("show");
}

function openModPartPopUp(element){
	
	var buttonModPartRowIndx = $(element).closest("tr");
	ModPartRowIndex = (buttonModPartRowIndx[0].rowIndex - 1);

	//Send input values from Boq table to popup
 	$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
 	$('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
 	$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
	$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());


 	//Check if primary checkbox in boq table is checked 
 	if ($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
		$('#popupPrimary').prop('checked',true);
	}
	else{
		$('#popupPrimary').prop('checked', false);
	}
	 //Update popup Nb in header 
	 var modPartElement = document.getElementById("popupNbModpart");
	 modPartElement.innerHTML = "Item # " +(ModPartRowIndex + 1);		  
	 
	 $("#modPartModal").modal("show");
}

///////////////////////////INSERT ROW BELOW functions  /////////////////////////////////////////////////////

function insertNodeRowBelow(){

RowCount =  $("#partnodeTab >tbody tr").length;

 var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record1'><button type = 'button' href='#' name='popUpNodeMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	 +"<td name = 'nodeID'><input name='nodeID' id = 'nodeID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 +"<td name = 'node_Name'><input name='node_Name' id = 'node_Name"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 +"<td name = 'arNodeID'><input name='arNodeID' readonly value = 0 type='text' style='width:200px;' class='form-control text-input'/></td></tr>";

$("#partnodeTab > tbody tr").eq(NodeRowIndex).after(markup);


	NodeRowIndex++;
	var belowIndex = parseInt(NodeRowIndex);

	$('#popupNodeId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
    $('#popupNodeName').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
	$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());


    NodeboqAutocomplete(RowCount,"partnodeTab");

	//Update popup Nb in header
	var element = document.getElementById("popupNbNode");
	element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}






function insertSiteRowBelow(){


RowCount =  $("#sitetab >tbody tr").length;

var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
 +"<td name = 'wareID'><input name='wareID'  id = 'wareID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'siteID'><input name='siteID' id = 'siteID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'siteName'><input name='siteName' id = 'siteName"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'arSiteID'><input name='arSiteID' readonly value = 0 type='text' style='width:200px;' class='form-control text-input'/></td></tr>";

	

	$("#sitetab > tbody tr").eq(SiteRowIndex).after(markup);

	SiteRowIndex++;
	var belowIndex = parseInt(SiteRowIndex);

	$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
	$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
	$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
	$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());


    SiteboqAutocomplete(RowCount,"sitetab");

	//Update popup Nb in header
	var element = document.getElementById("popupNbSite");
	element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}

function insertModPartRowBelow(){

 var RowCount =  $("#partmodTab >tbody tr").length;
 var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   +"<td name = 'itemModel'><input name='itemModel' id = 'itemModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name = 'itemPartNum'><input name='itemPartNum' id = 'itemPartNum"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name ='qtyPartNum' ><input name='qtyPartNum' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name ='arItemID' ><input name='arItemID' readonly value = '0' type='text' style='width:200px;' class='form-control text-input'/></td>"																																	  
		   +"<td name = 'primary'><input name='primary' onclick='onlyOne(this)' type='checkbox'/></td></tr>";
	

	$("#partmodTab > tbody tr").eq(ModPartRowIndex).after(markup);
    
	
	ModPartRowIndex++;
	var belowIndex = parseInt(ModPartRowIndex);


	$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
	$('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
	$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
	$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());

	//Check if primary checkbox in boq table is checked 
	if ($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
		$('#popupPrimary').prop('checked',true);
	}
	else{
		$('#popupPrimary').prop('checked', false);
	}
		
     PNboqAutocomplete(RowCount,"partmodTab"); 	
		
	//Update popup Nb in header
	var element = document.getElementById("popupNbModpart");
	element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	//SetCalcPopUp();
}

 function insertSerialRowBelow(){
 
 
	RowCount =  $("#ARserialNumberTab >tbody tr").length;		
			
	var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowSerialNb'><button type='button' href='#' name='serialNoPopupMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name = 'SNserialNumber'><input name='inputSerialNb' id = 'inputSerialNb"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNmodel'><input name='inputModel'  id = 'inputModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNpartNumber' ><input name='inputpartNumber' id = 'inputpartNumber"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNsite' ><input name='inputsite'  id = 'inputsite"+RowCount+"'  type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNposition' ><input name='inputPosition' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='arSerialID' ><input name='arSerialID' readonly value = '0' type='text' style='width:200px;' class='form-control text-input'/></td></tr>"
 
 
	$("#ARserialNumberTab > tbody tr").eq(SerialRowIndex).after(markup);
 
	SerialRowIndex++;
	var belowIndex = parseInt(SerialRowIndex);


	$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
	$('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
	$('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
    $('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
	$('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
	$('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());


     SeialNBboqAutocomplete(RowCount,"ARserialNumberTab"); 	

	//Update popup Nb in header
	var element = document.getElementById("popupNbSerialno");
	element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
}

///////////////////////////INSERT ROW ABOVE functions  /////////////////////////////////////////////////////

function insertNodeRowAbove(){



RowCount =  $("#partnodeTab >tbody tr").length;

 var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record1'><button type = 'button' href='#' name='popUpNodeMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	 +"<td name = 'nodeID'><input name='nodeID' id = 'nodeID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 +"<td name = 'node_Name'><input name='node_Name' id = 'node_Name"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 +"<td name = 'arNodeID'><input name='arNodeID' readonly value = 0 type='text' style='width:200px;' class='form-control text-input'/></td></tr>";

$("#partnodeTab > tbody tr").eq(NodeRowIndex).before(markup);

	
	$('#popupNodeId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
	$('#popupNodeName').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
	$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());

    NodeboqAutocomplete(RowCount,"partnodeTab");

	//Update popup Nb in header
	var element = document.getElementById("popupNbNode");
	element.innerHTML = "Item # " +(parseInt(NodeRowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}




 function insertSiteRowAbove(){

RowCount =  $("#sitetab >tbody tr").length;

var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
 +"<td name = 'wareID'><input name='wareID'  id = 'wareID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'siteID'><input name='siteID' id = 'siteID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'siteName'><input name='siteName' id = 'siteName"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
 +"<td name = 'arSiteID'><input name='arSiteID' readonly value = 0 type='text' style='width:200px;' class='form-control text-input'/></td></tr>";
	
	
       $("#sitetab > tbody tr").eq(SiteRowIndex).before(markup);
	
	$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
	$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
	$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
	$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());


    SiteboqAutocomplete(RowCount,"sitetab");

	//Update popup Nb in header
	var element = document.getElementById("popupNbSite");
	element.innerHTML = "Item # " +(parseInt(SiteRowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}






function insertSerialRowAbove(){


 
	RowCount =  $("#ARserialNumberTab >tbody tr").length;		
			
	var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowSerialNb'><button type='button' href='#' name='serialNoPopupMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name = 'SNserialNumber'><input name='inputSerialNb' id = 'inputSerialNb"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNmodel'><input name='inputModel'  id = 'inputModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNpartNumber' ><input name='inputpartNumber' id = 'inputpartNumber"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNsite' ><input name='inputsite'  id = 'inputsite"+RowCount+"'  type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNposition' ><input name='inputPosition' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='arSerialID' ><input name='arSerialID' readonly value = '0' type='text' style='width:200px;' class='form-control text-input'/></td></tr>"
 
 
 
       $("#ARserialNumberTab > tbody tr").eq(SerialRowIndex).before(markup);
	
	$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
	$('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
	$('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
	$('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
	$('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
	$('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());


     SeialNBboqAutocomplete(RowCount,"ARserialNumberTab"); 

	//Update popup Nb in header
	var element = document.getElementById("popupNbSerialno");
	element.innerHTML = "Item # " +(parseInt(SerialRowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}


function insertModPartRowAbove(){


RowCount =  $("#partmodTab >tbody tr").length;
		
 var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   +"<td name = 'itemModel'><input name='itemModel' id = 'itemModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name = 'itemPartNum'><input name='itemPartNum' id = 'itemPartNum"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name ='qtyPartNum' ><input name='qtyPartNum' type='text' style='width:200px;' class='form-control text-input'/></td>"
		   +"<td name ='arItemID' ><input name='arItemID' readonly value = '0' type='text' style='width:200px;' class='form-control text-input'/></td>"																																	  
		   +"<td name = 'primary'><input name='primary' onclick='onlyOne(this)' type='checkbox'/></td></tr>";
		   	
	$("#partmodTab > tbody tr").eq(ModPartRowIndex).before(markup);

	
	$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
	$('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
	$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
	$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());

	//Check if primary checkbox in boq table is checked 
	if ($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
		$('#popupPrimary').prop('checked',true);
	}
	else{
		$('#popupPrimary').prop('checked', false);
	}
	
	
	PNboqAutocomplete(RowCount,"partmodTab");
	
	//Update popup Nb in header
	var element = document.getElementById("popupNbModpart");
	element.innerHTML = "Item # " +(parseInt(ModPartRowIndex)+1);

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

	
}

///////////////////////////NEXT functions  /////////////////////////////////////////////////////

function nextNodeRow(){
	
	var rowNodeCount = $("#partnodeTab >tbody tr").length;
	NodeRowIndex++ ;
	var nextNodeIndex = parseInt(NodeRowIndex);
	
	if(NodeRowIndex >= 0 && NodeRowIndex < rowNodeCount) {
		$('#popupNodeId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
		$('#popupNodeName').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
		$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());

		var element = document.getElementById("popupNbNode");
		element.innerHTML = "Item # " +(nextNodeIndex+1);
	}
	else if (NodeRowIndex >= rowNodeCount) {
	
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		//SetCalcPopUp();
		addNoderows("addRow");
		
		$('#popupNodeId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
		$('#popupNodeName').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
		$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());

		var element = document.getElementById("popupNbNode");
		element.innerHTML = "Item # " +(nextNodeIndex+1);
	}
			
}// End node next fct 

function nextSiteRow(){
	var rowSiteCount = $("#sitetab >tbody tr").length;
	SiteRowIndex++ ;
	var nextSiteIndex = parseInt(SiteRowIndex);
	if(SiteRowIndex >= 0 && SiteRowIndex < rowSiteCount) {
		
		$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
		$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
		$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
		$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());

		var element = document.getElementById("popupNbSite");
		element.innerHTML = "Item # " +(nextSiteIndex+1);
	}
	else if (SiteRowIndex >= rowSiteCount) {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		//SetCalcPopUp();
		addSiterows("addRow");
		
		$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
		$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
		$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
		$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());

		var element = document.getElementById("popupNbSite");
		element.innerHTML = "Item # " +(nextSiteIndex+1);
	}
}// End next fct in popup

function nextSerialRow(){

	var rowSerialCount = $("#ARserialNumberTab >tbody tr").length;
	SerialRowIndex++ ;
	var nextSerialIndex = parseInt(SerialRowIndex);
	
	if(SerialRowIndex >= 0 && SerialRowIndex < rowSerialCount) {
		
		$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
		$('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
		$('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
		$('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
		$('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
	    $('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());

		var element = document.getElementById("popupNbSerialno");
		element.innerHTML = "Item # " +(nextSerialIndex+1);
	}
	else if (SerialRowIndex >= rowSerialCount) {
		
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		//SetCalcPopUp();
		addSerialrows("addRow");
		     
		$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
		$('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
		$('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
		$('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
		$('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
		$('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());

		var element = document.getElementById("popupNbSerialno");
		element.innerHTML = "Item # " +(nextSerialIndex+1);
	}
}// End next fct in popup

function nextModPartRow(){
	var rowModPartCount = $("#partmodTab >tbody tr").length;
	ModPartRowIndex++ ;
	var nextModPartIndex = parseInt(ModPartRowIndex);
	
	if(ModPartRowIndex >= 0 && ModPartRowIndex < rowModPartCount) {
		$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
		$('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
		$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
		$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());

		//Check if primary checkbox in boq table is checked 
		if ($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
			$('#popupPrimary').prop('checked',true);
		}
		else{
		$('#popupPrimary').prop('checked', false);
		}
		
		var element = document.getElementById("popupNbModpart");
		element.innerHTML = "Item # " +(nextModPartIndex+1);
	}
	else if (ModPartRowIndex >= rowModPartCount) {
		
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		//SetCalcPopUp();
			
		addModelPartrow("addRow");
		     
		$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
	    $('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
		$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
		$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());

		//Check if primary checkbox in boq table is checked 
	 	if ($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
	 		$('#popupPrimary').prop('checked',true);
	 	}
	 	else{
	 		$('#popupPrimary').prop('checked', false);
	 	}
	 	var element = document.getElementById("popupNbModpart");
		element.innerHTML = "Item # " +(nextModPartIndex+1);
	}
}// End next fct in popup

///////////////////////////DELETE functions  /////////////////////////////////////////////////////

function deleteNodeBoqRow() {

	//NodeRowIndex++;
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).remove();

	// Get Nb of rows after delete
	var rowNodeCount = $("#partnodeTab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowNodeCount);

	//NodeRowIndex--;

	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	 //SetCalcPopUp();
	  if (NodeRowIndex == 0 && rowNodeCount ==0) {
	  	$("#nodeModal").modal("hide");
	  }
	  
	  if(NodeRowIndex >= 0 && NodeRowIndex < rowNodeCount) {
		  //NodeRowIndex++;
			$('#popupNodeId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
			$('#popupNodeName').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
			$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());

			//Update popup Nb in header 
			var element = document.getElementById("popupNbNode");
			element.innerHTML = "Item # " +(NodeRowIndex+1);  
			//NodeRowIndex++;
	 }
	    // Show previous record 
		 if (NodeRowIndex >= rowNodeCount){
			NodeRowIndex--;

			$('#popupNodeId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
			$('#popupNodeName').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
			$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());

			//Update popup Nb in header 
			var element = document.getElementById("popupNbNode");
			element.innerHTML = "Item # " +(NodeRowIndex+1); 
		}

}// End delete fct 

function deleteSiteBoqRow() {
	//NodeRowIndex++;
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).remove();
	
	// Get Nb of rows after delete
	var rowSiteCount = $("#sitetab >tbody tr").length;

	//NodeRowIndex--;
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
	//SetCalcPopUp();
	if (SiteRowIndex == 0 && rowSiteCount ==0) {
		$("#siteModal").modal("hide");
	}
	if(SiteRowIndex >= 0 && SiteRowIndex < rowSiteCount) {
		//NodeRowIndex++;
		$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
		$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
		$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
		$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());

	 	//Update popup Nb in header 
	 	var element = document.getElementById("popupNbSite");
	 	element.innerHTML = "Item # " +(SiteRowIndex+1);  
	 	//NodeRowIndex++;
	 }
	 // Show previous record 
	 if (SiteRowIndex >= rowSiteCount){
	 	SiteRowIndex--;
	 	$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
		$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
		$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
		$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());

		//Update popup Nb in header 
	 	var element = document.getElementById("popupNbSite");
	 	element.innerHTML = "Item # " +(SiteRowIndex+1); 
	 }
}// End delete fct 

function deleteSerialBoqRow() {
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).remove();
	// Get Nb of rows after delete
	var rowSerialCount = $("#ARserialNumberTab >tbody tr").length;
	//NodeRowIndex--;
	
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
	//SetCalcPopUp();
	if (SerialRowIndex == 0 && rowSerialCount ==0) {
		$("#serialnoModal").modal("hide");
	}
	if(SerialRowIndex >= 0 && SerialRowIndex < rowSerialCount) {
		$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
		$('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
		$('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
		$('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
		$('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
		$('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());

		//Update popup Nb in header 
		var element = document.getElementById("popupNbSerialno");
		element.innerHTML = "Item # " +(SerialRowIndex+1);  
		 //NodeRowIndex++;
	}
	// Show previous record 
	if (SerialRowIndex >= rowSerialCount){
		SerialRowIndex--;
		
		$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
		$('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
		$('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
		$('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
		$('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
	    $('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());

		//Update popup Nb in header 
		 var element = document.getElementById("popupNbSerialno");
		 element.innerHTML = "Item # " +(SerialRowIndex+1);
	}
}// End delete fct 

function deleteModPartBoqRow() {
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).remove();
	
	// Get Nb of rows after delete
	var rowModPartCount = $("#partmodTab >tbody tr").length;
	//NodeRowIndex--;
	
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
	//SetCalcPopUp();
	if (ModPartRowIndex == 0 && rowModPartCount ==0) {
		$("#modPartModal").modal("hide");
	}
	if(ModPartRowIndex >= 0 && ModPartRowIndex < rowModPartCount) {
		$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
		$('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
		$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
		$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());

		//Check if primary checkbox in boq table is checked 
		if ($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
			$('#popupPrimary').prop('checked',true);
		}
		else{
			$('#popupPrimary').prop('checked', false);
		}
		
		//Update popup Nb in header 
		var element = document.getElementById("popupNbModpart");
		element.innerHTML = "Item # " +(ModPartRowIndex+1);  
		//NodeRowIndex++;
	}
	// Show previous record 
	if (ModPartRowIndex >= rowModPartCount){
		ModPartRowIndex--;
		
		$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
		$('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
		$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
		$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());

		//Check if primary checkbox in boq table is checked 
		if ($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
			$('#popupPrimary').prop('checked',true);
		}
		else{
			$('#popupPrimary').prop('checked', false);
		}
		//Update popup Nb in header 
		var element = document.getElementById("popupNbModpart");
		element.innerHTML = "Item # " +(ModPartRowIndex+1); 
		}
}// End delete fct 



function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
 $(function(){

 var ctx = getContextPath();

// WareId autocomplete in Sitepopup
$("#popupWareId").autocomplete({
	source: function(request, response) {
	var siteID =$("#popupSiteId").val();
	var siteName =$("#popupSiteName").val();
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetAllWarehouse',
		data: {
			"warehouseName": $("#popupWareId").val(),

		},
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
		}, minLength:0, maxShowItems: 40, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("popupSiteId").value= ui.item[2];
			document.getElementById("popupSiteName").value= ui.item[1];
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			 return $("<li class='each'>")
			 .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					 item[0] + "</span><br><span class='desc'>" +
					 item[1] + "</span><br><span class='desc'>" +
			         item[2] + "</span></div>")
			         .appendTo(ul);
			  };
	$("#popupWareId").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}
}); // End WareId autocomplete in Sitepopup

// SiteId autocomplete in Sitepopup
$("#popupSiteId").autocomplete({
	source: function(request, response) {
	var wareID =$("#popupWareId").val();
	var siteName =$("#popupSiteName").val();
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:  ctx+'/GetAllWarehouse',
		data: {
			"warehouseName": $("#popupSiteId").val(),

		},
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
		}, minLength:0, maxShowItems: 40, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[2]  : '');
			document.getElementById("popupWareId").value= ui.item[0];
			document.getElementById("popupSiteName").value= ui.item[1];
			return false;
		}
				  }).autocomplete("instance")._renderItem = function(ul, item) {
				 return $("<li class='each'>")
				 .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						 item[2] + "</span><br><span class='desc'>" +
						 item[1] + "</span><br><span class='desc'>" +
				         item[0] + "</span></div>")
				         .appendTo(ul);
				  };
				  $("#popupSiteId").focus(function(){
					  if (this.value == ""){
						  $(this).autocomplete("search");
			              }						
}); // End SiteId autocomplete in Sitepopup

// SiteName autocomplete in Sitepopup
$("#popupSiteName").autocomplete({
	source: function(request, response) {
		var wareID =$("#popupWareId").val();
		var siteID =$("#popupSiteId").val();
	 $.ajax({
	 	type: "GET",
	 	contentType: "application/json; charset=utf-8",
	 	url: ctx+'/GetAllWarehouse',
	 data: {
	 	"warehouseName": $("#popupSiteName").val(),

	 },
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
	}, minLength:0, maxShowItems: 40, scroll:true,
	 select: function(event, ui) {
						          this.value = (ui.item ? ui.item[1]  : '');
								  document.getElementById("popupWareId").value= ui.item[0];
			                      document.getElementById("popupSiteId").value= ui.item[2];
								  return false;
								}
					  }).autocomplete("instance")._renderItem = function(ul, item) {
					 return $("<li class='each'>")
					 .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
							 item[1] + "</span><br><span class='desc'>" +
							 item[0] + "</span><br><span class='desc'>" +
					         item[2] + "</span></div>")
					         .appendTo(ul);
					  };
					  $("#popupSiteName").focus(function(){
						  if (this.value == ""){
							  $(this).autocomplete("search");
				              }						
}); // End SiteName autocomplete in Sitepopup









// Item Model autocomplete  in ModelsAndPartNo popup
$("#popupItemModel").autocomplete({
	source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getModel',
			data: {
            	requestValue : request.term,
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
			document.getElementById("popupItemPartno").value= ui.item[3];
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
}); // end Item Model autocomplete in ModelsAndPartNo popup
						

// Item PartNo autocomplete  in ModelsAndPartNo popup
$("#popupItemPartno").autocomplete({
	source: function(request, response) {
		$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getPartNo',
		data: {
 			requestValue : request.term,
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
			this.value = ui.item[0];
			$("#popupItemModel").val(ui.item[3]);
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
			
			$("#popupItemPartno").focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
		}						
}); // end Item PartNo autocomplete in ModelsAndPartNo popup








   
   
   
   
   
   
   
   // SERIAL NUMBER AUTOCOMPLETE INSIDE SERIAL POPUP
   
   
	  
		   $("#popupSerialno").autocomplete({
			   source: function(request, response, event, ui) {
					$.ajax({ 
						type: "GET",
						contentType: "application/json; charset=utf-8",
						url: ctx+'/GetAllSerialNumber',
						data: {
							requestValue : request.term
						},
						dataType: "json",
						success: function (data) {
							if (data != null) {
								response(data.ListSerialNumber);
							}
						},
						error: function(result) {
							console.log("Error");
						}
					});
			   }, minLength:0, maxShowItems: 4, scroll:true,
			   select: function(event, ui) {
				   this.value = (ui.item[0]);
			           $("#popupModel").val(ui.item[1]);
			           $("#popupPartnum").val(ui.item[2]);
				   return false;
			   }
		   }).autocomplete("instance")._renderItem = function(ul, item) {
		
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                
			item[0] + "</span><br><span class='desc'>";
			if(item[1] != '-')
				appendString += item[1] + ",</span><span class='desc'>";
			
			if(item[2] != '-')
			appendString += item[2] + "</span>";

			appendString += "</div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
		};
	   $("#popupSerialno").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}

         });		
   
   
   
   
   





// ITEM MODEL AUTOCOMPLETE INSIDE SERIAL POPUP

		$("#popupModel").autocomplete({
		source: function(request, response, event, ui) {

			 $.ajax({ 
				 type: "GET",
				 contentType: "application/json; charset=utf-8",
				 url: ctx+'/getModel',
				 data: {
						requestValue : request.term																			  
				 },
				 dataType: "json",
				 success: function (data) {
					 console.log(0000);
					 
					 if (data != null) {
						 console.log(11);
						 
						 response(data.ListModels);
					 }
				 },
				 error: function(result) {
					 console.log(222);
					 
				 }
			 });
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			 console.log(1111100);
				
			this.value = (ui.item[0]);
			$("#popupPartnum").val(ui.item[3]);
			
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
			
    $("#popupModel").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}

});	








// ITEM PARTNB AUTOCOMPLETE INSIDE SERIAL POPUP

		$("#popupPartnum").autocomplete({
		source: function(request, response, event, ui) {
			 $.ajax({ 
				 type: "GET",
				 contentType: "application/json; charset=utf-8",
				 
				 url: ctx+'/getPartNo',
				 data: {
					 requestValue : request.term,
																		  
						
				 },
				 dataType: "json",
				 success: function (data) {
					 
					 if (data != null) {
						 console.log(11);
						 
						 response(data.ListPartNos);
					 }
				 },
				 error: function(result) {
					 console.log(222);
					 
				 }
			 });
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			 console.log(1111100);
				
			this.value = (ui.item[0]);
			$("#popupModel").val(ui.item[3]);			
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
			
$("#popupPartnum").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}

});	








 // NodeID autocomplete in Nodepopup
$("#popupNodeId").autocomplete({
	source: function(request, response) {
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetAllNode',
		data: {
			"Node" : $("#popupNodeId").val(),
		},
		dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.ListNode);
		}
		},
		error: function(result) {
			alert("Error");
		}
		});
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
			this.value = ui.item[0];
			$("#popupNodeName").val(ui.item[1]);
			return false;
		}
	  }).autocomplete("instance")._renderItem = function(ul, item) {
		return $('<li class="each"></li>').data( "item.autocomplete", item )
		.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		item[0] + "</span><br><span class='desc'>" +
		item[1] + '</span></div>').appendTo(ul);
	  };
	  $("#popupNodeId").focus(function(){
		  console.log("I am in the nodeID popup");
		  if (this.value == ""){
			  $(this).autocomplete("search");
              }						
}); // End NodeID autocomplete in Nodepopup

// NodeName autocomplete in Nodepopup
$("#popupNodeName").autocomplete({
	source: function(request, response) {
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetAllNode',
		data: {
			"Node": $("#popupNodeName").val(),
			},
		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.ListNode);
			}
	},
	error: function(result) {
		console.log("Error");
		}
	});
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
		this.value = ui.item[1];
		$("#popupNodeId").val(ui.item[0]);
		return false;

	}
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $('<li class="each"></li>').data( "item.autocomplete", item )
	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	item[1] + "</span><br><span class='desc'>" +
	item[0] + '</span></div>').appendTo(ul);
};
$("#popupNodeName").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}						
}); // End NodeName autocomplete in Nodepopup

 


$('#popupSite').autocomplete({
	source: function(request, response, event, ui) {
		 $.ajax({
			 type: "GET",
			 contentType: "application/json; charset=utf-8",
			 url:ctx+'/GetAllWarehouse',
			 data: {
				 "warehouseName" : request.term,
			 },
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
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] : '');
		return false;
	}
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $("<li class='each'>")
	.append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
		item[0] + "</span><br><span class='desc'>"+
		item[1] +', '+ item[2] + "</span></div>")
	.appendTo(ul);
};
$('#popupSite').focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
   }
});
//////////

// Resize and drag the popup
$('.modal-content').resizable({
	minHeight: 100,
	minWidth: 100
	});
	
$('.modal-dialog').draggable({
	handle: ".modal-header, .modal-footer"
	});
	
	
$('#nodeModal,#siteModal,#modPartModal,#serialnoModal').on('show.bs.modal', function() {
	$(this).find('.modal-body').css({
	'max-height': '100%',
	});
});

//Reset the popup to original size after resizing 
$('#nodeModal,#siteModal,#modPartModal,#serialnoModal').on('hidden.bs.modal', function() {
	$(this).find('.modal-content').css({'width': '', 'height': ''});
	})
	
//reset popup position after it has been dragged and closed
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







///////////////////////////Close functions in all popups /////////////////////////////////////////////////////

//   Close  function in NODE POPUP
$("button[name='closeNodePopup']").on("click", function(){
	// Send input values from popup to boq table
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val($('#popupNodeId').val());
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val($('#popupNodeName').val());
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val($('#popupNodeSeqId').val());

	$("#nodeModal").modal("hide");				 	
}); // end close fct

// Close the node popup using ESC
document.addEventListener('keydown', function(event){
if(event.key === "Escape"){
	// Send input values from popup to boq table
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val($('#popupNodeId').val());
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val($('#popupNodeName').val());
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val($('#popupNodeSeqId').val());

	$("#nodeModal").modal("hide");
}
});// end close fct using esc

//Close function in modPartNum popup
$("button[name='closeModPartPopup']").on("click", function(){
	// Send input values from popup to boq table
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val($('#popupItemPartno').val());
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val($('#popupQty').val());
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val($('#popupModePartNumID').val());

	//Check if primary checkbox in popup is checked
	if ($('#popupPrimary').is(':checked')) {
		//Call onlyOne method to check only one checkbox in popup and boq
		onlyOne();
		$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked',true);
	}
	else{
		$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked', false);
	}
	
	$("#modPartModal").modal("hide");				 	
}); // end close fct	

// Close ModelPartNumber popup using ESC 	  		    	   
document.addEventListener('keydown', function(event){
	if(event.key === "Escape"){
		// Send input values from popup to boq table
	    $("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
		$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val($('#popupItemPartno').val());
		$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val($('#popupQty').val());
		$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val($('#popupModePartNumID').val());


		//Check if primary checkbox in popup is checked
		if ($('#popupPrimary').is(':checked')) {
			//Call onlyOne method to check only one checkbox in popup and boq
			onlyOne();
			$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked',true);
		}
		else{
			$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked', false);
		}
		
		$("#modPartModal").modal("hide");				
	}
	
});// end close fct using esc

//Close function in SITE POPUP
$("button[name='closeSitePopup']").on("click", function(){
	// Send input values from popup to boq table
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val($('#popupWareId').val());
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val($('#popupSiteId').val());
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val($('#popupSiteName').val());
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val($('#popupSiteSeqID').val());

	$("#siteModal").modal("hide");				 	
}); // end close fct

// Close SITE popup using ESC 	  		    	   
document.addEventListener('keydown', function(event){
	if(event.key === "Escape"){
		// Send input values from popup to boq table
		$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val($('#popupWareId').val());
		$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val($('#popupSiteId').val());
		$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val($('#popupSiteName').val());
		$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val($('#popupSiteSeqID').val());

		$("#siteModal").modal("hide");		
	}
});// end close fct using esc

//Close function in serialNo popup
$("button[name='closeSerialnoPopup']").on("click", function(){
	// Send input values from popup to boq table
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val($('#popupSerialno').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val($('#popupModel').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val($('#popupPartnum').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val($('#popupSite').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val($('#popupPosition').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val($('#popupSerialID').val());


	$("#serialnoModal").modal("hide");				 	
}); // end close fct

//Close  serialNo popup using ESC
document.addEventListener('keydown', function(event){
	if(event.key === "Escape"){
	// Send input values from popup to boq table
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val($('#popupSerialno').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val($('#popupModel').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val($('#popupPartnum').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val($('#popupSite').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(serialRowindex).find('td[name="SNposition"]').children('input').val($('#popupPosition').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val($('#popupSerialID').val());


	$("#serialnoModal").modal("hide");		
	          					
	}
});// end close fct using esc


	 	 
///////////////////////////Previous functions  /////////////////////////////////////////////////////

$("button[name='previousNodeRow']").on("click", function(){
	if(NodeRowIndex > 0) {
		NodeRowIndex-- ;
		var nodePrevIndex = parseInt(NodeRowIndex);
		$('#popupNodeId').val($("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
		$('#popupNodeName').val($("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val()); 
		$('#popupNodeSeqId').val($("#partnodeTab > tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val());

		//Update popup Nb in header 
		var nodeElement = document.getElementById("popupNbNode");
		nodeElement.innerHTML = "Item # " +(nodePrevIndex+1);
	}
	
	// Close node popup on row 0 (end of prev)
	else if (NodeRowIndex == 0) {
		$("#nodeModal").modal("hide");
	}
});

$("button[name='previousSiteRow']").on("click", function(){
	if(SiteRowIndex > 0) {
	
	SiteRowIndex-- ;
	var sitePrevIndex = parseInt(SiteRowIndex);
	
	$('#popupWareId').val($("#sitetab >tbody").find("tr").eq(sitePrevIndex).find('td[name="wareID"]').children('input').val());
	$('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(sitePrevIndex).find('td[name="siteID"]').children('input').val());
	$('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(sitePrevIndex).find('td[name="siteName"]').children('input').val()); 
	$('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val());

	//Update popup Nb in header 
	var siteElement = document.getElementById("popupNbSite");
	siteElement.innerHTML = "Item # " +(sitePrevIndex+1);
    }
    // Close node popup on row 0 (end of prev)
    else if (SiteRowIndex == 0) {
    	$("#siteModal").modal("hide");
    }
});

$("button[name='previousSerialRow']").on("click", function(){
 	if(SerialRowIndex > 0) {
	 	SerialRowIndex -- ;
		var serialPrevIndex = parseInt(SerialRowIndex );
		
		$('#popupSerialno').val($("#ARserialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNserialNumber"]').children('input').val());
		$('#popupModel').val($("#ARserialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNmodel"]').children('input').val()); 
		$('#popupPartnum').val($("#ARserialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNpartNumber"]').children('input').val());
		$('#popupSite').val($("#ARserialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNsite"]').children('input').val());
		$('#popupPosition').val($("#ARserialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNposition"]').children('input').val());  
		$('#popupSerialID').val($("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val());

		//Update popup Nb in header 
		var serialElement = document.getElementById("popupNbSerialno");
		serialElement.innerHTML = "Item # " +(serialPrevIndex+1);
	}
		         
  // Close node popup on row 0 (end of prev)
	else if (serialRowindex  == 0) {
		$("#serialnoModal").modal("hide");
	}
});

$("button[name='previousModPartRow']").on("click", function(){
	if(ModPartRowIndex > 0) {
	
		ModPartRowIndex-- ;
	 	var modPartPrevIndex = parseInt(ModPartRowIndex);
	 	
	 	$('#popupItemModel').val($("#partmodTab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="itemModel"]').children('input').val());
	 	$('#popupItemPartno').val($("#partmodTab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="itemPartNum"]').children('input').val());
		$('#popupQty').val($("#partmodTab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="qtyPartNum"]').children('input').val());
		$('#popupModePartNumID').val($("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val());

	 	if ($("#partmodTab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="primary"]').children('input').is(':checked')) {
	 		$('#popupPrimary').prop('checked',true);
		}
		else{
			$('#popupPrimary').prop('checked', false);
			
		}
		
		//Update popup Nb in header 
		var modPartElement = document.getElementById("popupNbModpart");
		modPartElement.innerHTML = "Item # " +(modPartPrevIndex+1);
    }
    // Close node popup on row 0 (end of prev)
    else if (ModPartRowIndex == 0) {
    	$("#modPartModal").modal("hide");
    }
});


//send input values from popup Node  to boq directly on focuus
$('#popupNodeId,#popupNodeName,#popupNodeSeqId').on(' focusout ', function() {
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val($('#popupNodeId').val());
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val($('#popupNodeName').val());
	$("#partnodeTab >tbody").find("tr").eq(NodeRowIndex).find('td[name="arNodeID"]').children('input').val($('#popupNodeSeqId').val());

	//getTotalAT_SumQty();
	//amountsUpdate();
});

$('#popupWareId,#popupSiteId,#popupSiteName,#popupSiteSeqID').on(' focusout ', function() {
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val($('#popupWareId').val());
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val($('#popupSiteId').val());
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val($('#popupSiteName').val());
	$("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="arSiteID"]').children('input').val($('#popupSiteSeqID').val());

	//getTotalAT_SumQty();
	//amountsUpdate();
});

$('#popupSerialno,#popupModel,#popupPartnum,#popupSite,#popupPosition,#popupSerialID').on(' focusout ', function() {
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val($('#popupSerialno').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val($('#popupModel').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val($('#popupPartnum').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val($('#popupSite').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val($('#popupPosition').val());
	$("#ARserialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="arSerialID"]').children('input').val($('#popupSerialID').val());


	//getTotalAT_SumQty();
	//amountsUpdate();
});

$('#popupItemModel,#popupItemPartno,#popupQty,#popupPrimary,#popupModePartNumID').on(' focusout ', function() {
			 
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val($('#popupItemPartno').val());
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val($('#popupQty').val());
	$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="arItemID"]').children('input').val($('#popupModePartNumID').val());

 		
	if ($('#popupPrimary').is(':checked')) {
		//Call onlyOne method to check only one checkbox in popup and boq
		onlyOne();
		$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked',true);
	}
	else{
		$("#partmodTab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked', false);
	}
	//getTotalAT_SumQty();
	//amountsUpdate();
});

	

	 				
});







// FUNCTION MODEL & PARTNUMBER TAB AUTOCOMPLETE
					 
function PNboqAutocomplete(rowCnt,tableIndx){

	var ctx=getContextPath();
	var tableID=tableIndx;
														 
								   
	
	//ITEM CODE autocomplete
	$('#itemModel'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getModel',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
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
					$(this).parents("tr").find('input[name ="itemPartNum"]').val(ui.item[3]);
					 	 
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
	$('#itemModel'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});
	$('#itemModel'+rowCnt).focus();



		
	//ITEM PARTNO autocomplete
	$('#itemPartNum'+rowCnt).autocomplete({
	source: function(request, response) {
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:  ctx+'/getPartNo',
		data: {
        	requestValue : request.term,
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
$('#itemPartNum'+rowCnt).focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}
});


	
	
} // end PNboqAutocomplete fct

 
 
 
 

// FUNCTION SERIAL NB TAB AUTOCOMPLETE

function SeialNBboqAutocomplete(rowCnt,tableIndx){

	var ctx=getContextPath();
	var tableID=tableIndx;
	
	
	//SERIAL NUMBER autocomplete
	$('#inputSerialNb'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/GetAllSerialNumber',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
					response(data.ListSerialNumber);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
				   this.value = (ui.item[0]);
				   $(this).parents("tr").find('input[name ="inputModel"]').val(ui.item[1]);
				   $(this).parents("tr").find('input[name ="inputpartNumber"]').val(ui.item[2]);
				   return false;
			   }
		}).autocomplete("instance")._renderItem = function(ul, item) {
		
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                
			item[0] + "</span><br><span class='desc'>";
			if(item[1] != '-')
				appendString += item[1] + ",</span><span class='desc'>";
			
			if(item[2] != '-')
			appendString += item[2] + "</span>";

			appendString += "</div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
		};
	$('#inputSerialNb'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});
	$('#inputSerialNb'+rowCnt).focus();
														 
								   
	
	//ITEM MODEL autocomplete
	$('#inputModel'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getModel',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
					response(data.ListModels);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
					   this.value =  ui.item[0];
					   $(this).parents("tr").find('input[name ="inputpartNumber"]').val(ui.item[3]);
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
	$('#inputModel'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});



		
	//ITEM PARTNO autocomplete
	$('#inputpartNumber'+rowCnt).autocomplete({
	source: function(request, response) {
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:  ctx+'/getPartNo',
		data: {
        	requestValue : request.term,
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
					   this.value = ui.item[0];
					   $(this).parents("tr").find('input[name ="inputModel"]').val(ui.item[3]);
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
$('#inputpartNumber'+rowCnt).focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}
});


		
	// SITE autocomplete
	$('#inputsite'+rowCnt).autocomplete({
	source: function(request, response) {
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url:  ctx+'/GetAllWarehouse',
		data: {
        	"warehouseName" : request.term,
		 },
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
	 }, minLength:0, maxShowItems: 40, scroll:true,
	 select: function(event, ui) {
			this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] : '');
			return false;
		}
	
	}).autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li class='each'>")
		.append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
			item[0] + "</span><br><span class='desc'>"+
			item[1] +', '+ item[2] + "</span></div>")
		.appendTo(ul);
   };
   
$('#inputsite'+rowCnt).focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}
});




	// NODE ID autocomplete


	

} // end SeialNBboqAutocomplete fct      			 
   				 	  	   	 




// FUNCTION SITE TAB AUTOCOMPLETE

function SiteboqAutocomplete(rowCnt,tableIndx){

	var ctx=getContextPath();
	var tableID=tableIndx;
														 
								   
	
	//WARE ID autocomplete
	$('#wareID'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getWarehouseDetails',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
					response(data.ListWarehouses);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			 		this.value = ui.item[0] ;
			 		$(this).parents("tr").find('input[name ="siteID"]').val(ui.item[2]);
			 		$(this).parents("tr").find('input[name ="siteName"]').val(ui.item[1]);
			 		return false;
			 }
		}).autocomplete("instance")._renderItem = function(ul, item) {
			 		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
			 			
			 		item[0] + "</span><br><span class='desc'>" +
			 		item[1] + "</span><span class='desc'>,"+ 
			 		item[2] + "</span>";

			 		appendString += "</div>";
			 		return $("<li class='each'>").append(appendString).appendTo(ul);
			 	};
	$('#wareID'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});
	$('#wareID'+rowCnt).focus();
	
	
	
	
	
	//SITE ID autocomplete
	$('#siteID'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getWarehouseDetails',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
					response(data.ListWarehouses);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
						this.value =  ui.item[2];
						$(this).parents("tr").find('input[name ="wareID"]').val(ui.item[0]);
						$(this).parents("tr").find('input[name ="siteName"]').val(ui.item[1]);

						return false;
					}
		}).autocomplete("instance")._renderItem = function(ul, item) {
						
						var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
										
						item[2] + "</span><br><span class='desc'>" +
						item[0] + "</span><span class='desc'>" +","+ 
						item[1] + "</span><span class='desc'>";

						appendString += "</span></div>";
						return $("<li class='each'>").append(appendString).appendTo(ul);

					};
	$('#siteID'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});
	
	
	
	
	
	//SITE NAME autocomplete
	$('#siteName'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getWarehouseDetails',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
					response(data.ListWarehouses);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			    	    		this.value = (ui.item ? ui.item[1]  : '');
			    	    		$(this).parents("tr").find('input[name ="wareID"]').val(ui.item[0]);
			    	    		$(this).parents("tr").find('input[name ="siteID"]').val(ui.item[2]);
			    	    		
			    	    		return false;
			    	    	}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			    	    		
			    	    		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
			    	    	                
			    	    		item[1] + "</span><br><span class='desc'>" +
			    	    		item[0] + "</span><span class='desc'>" +","+ 
			    	    		item[2] + "</span>";

			    	    		appendString += "</div>";
			    	    		return $("<li class='each'>").append(appendString).appendTo(ul);
			    	    	};
	$('#siteName'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});
	



} // end SiteboqAutocomplete fct






// FUNCTION NODE TAB AUTOCOMPLETE

function NodeboqAutocomplete(rowCnt,tableIndx){

	var ctx=getContextPath();
	var tableID=tableIndx;
														 
					
	
	//NODE ID autocomplete
	$('#nodeID'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getAllNodes',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
					response(data.ListNodes);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			this.value = ui.item[0];
			$(this).parents("tr").find('input[name ="node_Name"]').val(ui.item[1]);
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
		return $('<li class="each"></li>').data( "item.autocomplete", item )
		.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		item[0] + "</span><br><span class='desc'>" +
		item[1] + '</span></div>').appendTo(ul);
   };
	$('#nodeID'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});
	$('#nodeID'+rowCnt).focus();
	
	
	
	
	
	//NODE NAME autocomplete
	$('#node_Name'+rowCnt).autocomplete({
	source: function(request, response) {
			$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getAllNodes',
			data: {
				requestValue : request.term,
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					console.log("Success here");
					response(data.ListNodes);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
		this.value = ui.item[1];
		$(this).parents("tr").find('input[name ="nodeID"]').val(ui.item[0]);
		return false;
	}
		}).autocomplete("instance")._renderItem = function(ul, item) {

		return $('<li class="each"></li>').data( "item.autocomplete", item )
		.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		item[1] + "</span><br><span class='desc'>" +
		item[0] + '</span></div>').appendTo(ul);
   };
	$('#node_Name'+rowCnt).focus(function(){
		if (this.value == ""){
		    
			$(this).autocomplete("search");
			}
	});
	



} // end NodeboqAutocomplete fct



	