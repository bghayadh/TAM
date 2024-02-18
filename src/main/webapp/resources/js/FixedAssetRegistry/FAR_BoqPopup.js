function getContextPath() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
 }
 
 var ctx = getContextPath();
$(function(){
    





 //////////////////////////////////////// FORMVIEW DELETE BUTTON /////////////////////////////
 
 $("#deleteButton").click(  function() {
    console.log('delete now');
    var farID = document.getElementById("farcode").value;
       $.ajax({
           type : "GET",
           url : ctx+"/FixedAssetRegistryFormViewDelete",
           dataType : "json",
           data : {
               "farID" : $("#farcode").val()
           },
           success : function(data) {
               //console.log("The returned data is " +data.BassamTest);
               //window.history.back();
               location.replace(ctx+"/FixedAssetRegistryListView");
           },
           error : function(error) {
               console.log("The error is " + error);
           }
       });
    
})



/////////////////// MODEL PART NB BOQ DELETE ROW //////////////////////

//var slctDelModPart = [];
$(".delete-row").click(function(){

    var checked = false;
    var modPart_pk = "" ;
    

   $("#bisotab > tbody").find('input[name="record"]').each(function(){

       if($(this).is(":checked")){
           checked = true;
           modPart_pk = $(this).parent().parent().children('td[name="farItemID"]').children('input').val();
           if(modPart_pk != 0){
               slctDelModPart.push(modPart_pk);
               }
           $(this).parents("tr").remove();
        console.log("The selected delete is "+slctDelModPart);
           
       }

   });

   if ( checked == false){
       alert('select row(s) to delete ');

       return false;
       }
   
});




/////////////////// SERIAL BOQ DELETE ROW ////////////////

//var slctDelSerial = [];
$(".delete-row1").click(function(){
	
	var checked = false;
	 var serial_pk = "" ;

	$("#SerialNumberTab > tbody").find('input[name="done"]').each(function(){
		if($(this).is(":checked")){
	         checked = true;
	         serial_pk = $(this).parent().parent().children('td[name="farSerialID"]').children('input').val();
       	 if(serial_pk != 0){
       		 slctDelSerial.push(serial_pk);
	        	 }
       	 $(this).parents("tr").remove();
         console.log("The selected delete is "+slctDelSerial);
            
        }
	
	});

	 if ( checked == false){
		 alert('select row(s) to delete ');

		 return false;
		 }
	 
});	




////////////////// NODE BOQ DELETE ROW //////////////////

  //  var slctDelNode = [];
    $(".deletes-row").click(function(){
  
        var checked = false;
        var node_pk = "" ;
         
       $("#ahmadtab > tbody").find('input[name="records"]').each(function(){
  
           if($(this).is(":checked")){
               checked = true;
               node_pk = $(this).parent().parent().children('td[name="farNodeID"]').children('input').val();
               if(node_pk != 0){
                   slctDelNode.push(node_pk);
                   }
               $(this).parents("tr").remove();
            console.log("The selected delete is "+slctDelNode);
               
           }
  
           
       });
  
       if ( checked == false){
           alert('select row(s) to delete ');
  
           return false;
           }
       
               
  });





///////////// SITE BOQ DELETE ROW //////////////

//var slctDelSite = [];
$(".deletesite-row").click(function(){

    var checked = false;
    var site_pk = "" ;

    $("#sitetab > tbody").find('input[name="recordsite"]').each(function(){
        if($(this).is(":checked")){
            checked = true;
            site_pk = $(this).parent().parent().children('td[name="farSiteID"]').children('input').val();
            if(site_pk != 0){
                slctDelSite.push(site_pk);
                }
            $(this).parents("tr").remove();
        console.log("The selected delete is "+slctDelSite);
            
        }

        
    });

    if ( checked == false){
        alert('select row(s) to delete ');

        return false;
        }
    
            
});



////////////////////// SELECT ALL NODE BOQ ROWS  ////////////////

$('body').on('click', '#selectsAll', function  () {

    if ($(this).hasClass('allChecked')) {
        $('input[name="records"]', '#ahmadtab').prop('checked', false);
    } else {
        $('input[name="records"]', '#ahmadtab').prop('checked', true);
        }
        $(this).toggleClass('allChecked');

});





////////////////////// SELECT ALL SITE BOQ ROWS ////////////////

$('body').on('click', '#selectAllSite', function  () {
	       

    if ($(this).hasClass('allChecked')) {
            $('input[name="recordsite"]', '#sitetab').prop('checked', false);
        } else {
            $('input[name="recordsite"]', '#sitetab').prop('checked', true);
            }
            $(this).toggleClass('allChecked');


});	






/////////////////////// SELECT ALL SERIAL BOQ ROWS /////////////////

$('body').on('click', '#SelectAllSerialNumber', function  () {

    if ($(this).hasClass('allChecked')) {
        $('input[name="done"]', '#SerialNumberTab').prop('checked', false);
    } else {
        $('input[name="done"]', '#SerialNumberTab').prop('checked', true);
        }
        $(this).toggleClass('allChecked');

});








///////////////////////  SELECT ALL MODEL PART NB BOQ ROWS ////////////////

$('body').on('click', '#selectAll', function  () {

    if ($(this).hasClass('allChecked')) {
        $('input[name="record"]', '#bisotab').prop('checked', false);
    } else {
        $('input[name="record"]', '#bisotab').prop('checked', true);
        }
        $(this).toggleClass('allChecked');

});

});	







////////////////////// ADD SERIAL NB BOQ TABLE ROW ///////////////////////

function addSerialrows(stat){

	var ctx = getContextPath();
					
	RowCount =  $("#SerialNumberTab >tbody tr").length;	

	var markup = "<tr><td><input type ='checkbox' name ='done'><button type='button' href='#' name='serialNoPopupMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name = 'SNserialNumber'><input name='inputSerialNb' id = 'inputSerialNb"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNMacAddress'><input name='inputMacAddress' id = 'inputMacAddress"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNmodel'><input name='inputModel' id = 'inputModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNpartNumber' ><input name='inputpartNumber' id = 'inputpartNumber"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNsite' ><input name='inputsite' id = 'inputsite"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNposition' ><input name='inputPosition' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='farSerialID' ><input name='farSerialID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"

		 
      $("#SerialNumberTab > tbody").append(markup);

	if(stat == "addRow"){
            SeialNBboqAutocomplete(RowCount,"SerialNumberTab");
	     }
		     

            ///////////// used to keep headers in the table //////////////
			const fartblSN = document.getElementById("SerialNumberTab");
            if(stat == "addRow"){
		    fartblSN.scrollTop = fartblSN.scrollHeight;}


	

} // RND ADDING A SERIAL NUMBER BOQ ROW







////////////////////// ADD MODEL & PARTNUMBER BOQ TABLE ROW  ///////////////////////

function addModelPartrow(stat){

	var ctx = getContextPath();
						
	RowCount =  $("#bisotab >tbody tr").length;	

           
   var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
       +"<td name = 'itemModel'><input name='itemModel' id = 'itemModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name = 'itemPartNum'><input name='itemPartNum'  id = 'itemPartNum"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name ='qtyPartNum' ><input name='qtyPartNum' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name ='farItemID' ><input name='farItemID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"
       +"<td name = 'primary'><input name='primary' onclick='onlyOne(this)' type='checkbox'/></td></tr>";


       $("#bisotab > tbody").append(markup);

       if(stat == "addRow"){
            PNboqAutocomplete(RowCount,"bisotab");
       }
       
       
///////////// used to keep headers in the table //////////////////
		const fartblMODELPARNB = document.getElementById("bisotab");
       if(stat == "addRow"){
       fartblMODELPARNB.scrollTop = fartblMODELPARNB.scrollHeight;}
       

} // END OF ADDDING A MODEL PARTNB BOQ ROW








////////////////////// ADD NODE BOQ TABLE ROW ///////////////////////

function addNoderows(stat){ 

	var ctx = getContextPath();
						
	RowCount =  $("#ahmadtab >tbody tr").length;	
    
          
    var markup = "<tr><td><input type='checkbox' name='records'><button type = 'button' href='#' name='popUpNodeMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
        +"<td name = 'nodeID'><input name='nodeID' id = 'nodeID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'node_Name'><input name='node_Name' id = 'node_Name"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'node_Type'><input name='node_Type' id = 'node_Type"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'farNodeID'><input name='farNodeID' readonly value = 0  type='text' style='width:200px;' class='form-control text-input'/></td>"
    
    $("#ahmadtab > tbody").append(markup);

    if (stat == "addRow"){
            NodeboqAutocomplete(RowCount,"ahmadtab");
          }
    
    
              ///////////// used to keep headers in the table ///////////////////
			    const fartblNODE = document.getElementById("ahmadtab");
                if (stat == "addRow"){
                fartblNODE.scrollTop = fartblNODE.scrollHeight;}
                
                
        

     } // END ADDING A NODE BOQ ROW
     
     
     
     
     
     
     
     
     
    ///////////////////// ADD SITE ROW IN BOQ TABLE /////////////////////// 
     
   function addSiterows(stat){
   
	var ctx = getContextPath();
						
	RowCount =  $("#sitetab >tbody tr").length;	
   
     var markup = "<tr><td><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	+"<td name = 'wareID'><input name='wareID' id = 'wareID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'siteID'><input name='siteID' id = 'siteID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'siteName'><input name='siteName' id = 'siteName"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'farSiteID'><input name='farSiteID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"
	

          $("#sitetab > tbody").append(markup);	

if (stat == "addRow"){
        SiteboqAutocomplete(RowCount,"sitetab");
    }

	////////////// used to keep headers in the table /////////////////////
	const fartblSITE = document.getElementById("sitetab");
    if (stat == "addRow"){
    fartblSITE.scrollTop = fartblSITE.scrollHeight;}

	} // END ADDING A SITE BOQ ROW
	
	
	
	


var ModPartRowIndex = 0;
var SerialRowIndex = 0;
var NodeRowIndex =0;
var SiteRowIndex = 0;










////////////////////// OPEN MODEL & PART NB POPUP //////////////////

function openModPartPopUp(element){

var buttonModPartRowIndx = $(element).closest("tr");
ModPartRowIndex = (buttonModPartRowIndx[0].rowIndex - 1);


///////////////// Send input values from Boq table to popup ///////////////////

    $('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
    $('#popupModelPartID').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val());

    ///////////// Check if primary checkbox in boq table is checked //////////////////
    
            if ($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
                $('#popupPrimary').prop('checked',true);
                console.log("Popup checkbox is checked")
            }
        else 
            {
        $('#popupPrimary').prop('checked', false);
        console.log("Popup checkbox is UNCHECKED")
        
            }

    
    //////////// Update popup Nb in header /////////////////
     
     var modPartElement = document.getElementById("popupNbModpart");
     modPartElement.innerHTML = "Item # " +(ModPartRowIndex+1);		  


$("#modPartModal").modal("show");
}











////////////////////// OPEN SERIAL NB POPUP //////////////////

function openSerialPopUp(element){

var buttonSerialRowIndx = $(element).closest("tr");
SerialRowIndex = (buttonSerialRowIndx[0].rowIndex - 1);


//////////////// Send input values from Boq table to popup ////////////////////

        $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
        $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val());
        $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val()); 
        $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
        $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
        $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
        $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val());


//////////////// Update popup Nb in header /////////////////// 

var serialElement = document.getElementById("popupNbSerialno");
serialElement.innerHTML = "Item # " +(SerialRowIndex+1);


$("#serialnoModal").modal("show");
}











////////////////////// OPEN NODE POPUP //////////////////

function openNodePopUp(element) {

var buttonNodeRowIndx = $(element).closest("tr");
NodeRowIndex = (buttonNodeRowIndx[0].rowIndex - 1);
console.log("Row index of clicked button::" +NodeRowIndex);

    //////////////// Send input values from Boq table to popup /////////////////////
    
    $('#popupNodeId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
    $('#popupNodeName').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
    $('#popupNodeType').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val());
    $('#popupNodeSeqId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val());

    ///////////////// SHOW THE POPUP /////////////
    
    $("#nodeModal").modal("show");

    ///////////////// SET THE INDEX NUMBER ///////////////
    
    var element = document.getElementById("popupNbNode");
    element.innerHTML = "Item # " +(NodeRowIndex+1);
    console.log(NodeRowIndex);
}










////////////////////// OPEN SITE POPUP //////////////////

function openSitePopUp(element){

var buttonSiteRowIndx = $(element).closest("tr");
SiteRowIndex = (buttonSiteRowIndx[0].rowIndex - 1);
    console.log("Rowindex of clicked button in Sitepopup:" +$(this).closest('tr').index());
    
    ////////////////// Send input values from Boq table to popup /////////////////////
    
    $('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
    $('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
    $('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
    $('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val());

    
    //////////////// Update popup Nb in header ////////////////
     
    var siteElement = document.getElementById("popupNbSite");
    siteElement.innerHTML = "Item # " +(SiteRowIndex+1);

        console.log(SiteRowIndex);
        
    
    $("#siteModal").modal("show");
}









///////////// INSERT BELOW ROW IN MODEL PART NB POPUP /////////////////

function insertModPartRowBelow(){
						
	RowCount =  $("#bisotab >tbody tr").length;	

           
   var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
       +"<td name = 'itemModel'><input name='itemModel' id = 'itemModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name = 'itemPartNum'><input name='itemPartNum'  id = 'itemPartNum"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name ='qtyPartNum' ><input name='qtyPartNum' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name ='farItemID' ><input name='farItemID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"
       +"<td name = 'primary'><input name='primary' onclick='onlyOne(this)' type='checkbox'/></td></tr>";

    $("#bisotab > tbody tr").eq(ModPartRowIndex).after(markup);

ModPartRowIndex++;
var belowIndex = parseInt(ModPartRowIndex);
console.log("belowIndex:" +belowIndex);

$('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
    $('#popupModelPartID').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val());

    /////////////// Check if primary checkbox in boq table is checked //////////////////
    
        if ($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
                $('#popupPrimary').prop('checked',true);
                console.log("Popup checkbox is checked")
            }
        else 
        {
        $('#popupPrimary').prop('checked', false);
        console.log("Popup checkbox is UNCHECKED")
        
        }

       PNboqAutocomplete(RowCount,"bisotab");

      //////////// Update popup Nb in header //////////////////////

       var element = document.getElementById("popupNbModpart");
       element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

        /////////////// Change status ////////////////////
        
        $("#formStatus").text("Not Saved");
        $('.dot').css({"background-color" : "orange"});
       

}









///////////// INSERT BELOW ROW IN SERIAL POPUP /////////////////

function insertSerialRowBelow(){

					
	RowCount =  $("#SerialNumberTab >tbody tr").length;	

	var markup = "<tr><td><input type ='checkbox' name ='done'><button type='button' href='#' name='serialNoPopupMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name = 'SNserialNumber'><input name='inputSerialNb' id = 'inputSerialNb"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNMacAddress'><input name='inputMacAddress' id = 'inputMacAddress"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNmodel'><input name='inputModel' id = 'inputModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNpartNumber' ><input name='inputpartNumber' id = 'inputpartNumber"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNsite' ><input name='inputsite' id = 'inputsite"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNposition' ><input name='inputPosition' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='farSerialID' ><input name='farSerialID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"


	$("#SerialNumberTab > tbody tr").eq(SerialRowIndex).after(markup);

     SerialRowIndex++;
     var belowIndex = parseInt(SerialRowIndex);


    $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
    $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val());
    $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
    $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
    $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
    $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
    $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val());

    
    SeialNBboqAutocomplete(RowCount,"SerialNumberTab");
    
    ///////////// Update popup Nb in header //////////////////
    
    var element = document.getElementById("popupNbSerialno");
    element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
    

}










///////////// INSERT BELOW ROW IN NODE POPUP /////////////////

function insertNodeRowBelow(){

	RowCount =  $("#ahmadtab >tbody tr").length;	    
          
    var markup = "<tr><td><input type='checkbox' name='records'><button type = 'button' href='#' name='popUpNodeMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
        +"<td name = 'nodeID'><input name='nodeID' id = 'nodeID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'node_Name'><input name='node_Name' id = 'node_Name"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'node_Type'><input name='node_Type' id = 'node_Type"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'farNodeID'><input name='farNodeID' readonly value = 0  type='text' style='width:200px;' class='form-control text-input'/></td>"
    
    
    $("#ahmadtab > tbody tr").eq(NodeRowIndex).after(markup);

    NodeRowIndex++;
    var belowIndex = parseInt(NodeRowIndex);
    console.log("belowIndex:" +belowIndex);

    $('#popupNodeId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
    $('#popupNodeName').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
    $('#popupNodeType').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val());
    $('#popupNodeSeqId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val());



            NodeboqAutocomplete(RowCount,"ahmadtab");

    //////////////// Update popup Nb in header ////////////////////
    
    var element = document.getElementById("popupNbNode");
    element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

    //////////////// Change status /////////////////
    
    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});

    
}









///////////// INSERT BELOW ROW IN SITE POPUP /////////////////

function insertSiteRowBelow(){
						
	RowCount =  $("#sitetab >tbody tr").length;	
   
     var markup = "<tr><td><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	+"<td name = 'wareID'><input name='wareID' id = 'wareID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'siteID'><input name='siteID' id = 'siteID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'siteName'><input name='siteName' id = 'siteName"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'farSiteID'><input name='farSiteID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"
	
	$("#sitetab > tbody tr").eq(SiteRowIndex).after(markup);
	
     SiteRowIndex++;
    var belowIndex = parseInt(SiteRowIndex);
    console.log("belowIndex:" +belowIndex);

    $('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
    $('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
    $('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
    $('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val());


        SiteboqAutocomplete(RowCount,"sitetab");

    ///////////// Update popup Nb in header /////////////////////
    
    var element = document.getElementById("popupNbSite");
    element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
        //SetCalcPopUp();

}









///////////// INSERT ABOVE ROW IN MODEL PART NUMBER POPUP /////////////////

    function insertModPartRowAbove(){


	RowCount =  $("#bisotab >tbody tr").length;	

           
   var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
       +"<td name = 'itemModel'><input name='itemModel' id = 'itemModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name = 'itemPartNum'><input name='itemPartNum'  id = 'itemPartNum"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name ='qtyPartNum' ><input name='qtyPartNum' type='text' style='width:200px;' class='form-control text-input'/></td>"
       +"<td name ='farItemID' ><input name='farItemID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"
       +"<td name = 'primary'><input name='primary' onclick='onlyOne(this)' type='checkbox'/></td></tr>";

    
    $("#bisotab > tbody tr").eq(ModPartRowIndex).before(markup);

    $('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
    $('#popupModelPartID').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val());

    /////////////// Check if primary checkbox in boq table is checked //////////////////////
     
            if ($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
                $('#popupPrimary').prop('checked',true);
                console.log("Popup checkbox is checked")
            }
        else 
            {
        $('#popupPrimary').prop('checked', false);
        console.log("Popup checkbox is UNCHECKED")
        
            }
            
     PNboqAutocomplete(RowCount,"bisotab"); 

    /////////////// Update popup Nb in header //////////////////////
    var element = document.getElementById("popupNbModpart");
    element.innerHTML = "Item # " +(parseInt(ModPartRowIndex)+1);

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});

    //SetCalcPopUp();
    }









///////////// INSERT ABOVE ROW IN SERIAL POPUP /////////////////

 function insertSerialRowAbove(){

					
	RowCount =  $("#SerialNumberTab >tbody tr").length;	

	var markup = "<tr><td><input type ='checkbox' name ='done'><button type='button' href='#' name='serialNoPopupMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name = 'SNserialNumber'><input name='inputSerialNb' id = 'inputSerialNb"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNMacAddress'><input name='inputMacAddress' id = 'inputMacAddress"+RowCount+"' type='text' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all text-input'/></td>"
		+"<td name = 'SNmodel'><input name='inputModel' id = 'inputModel"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNpartNumber' ><input name='inputpartNumber' id = 'inputpartNumber"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNsite' ><input name='inputsite' id = 'inputsite"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='SNposition' ><input name='inputPosition' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name ='farSerialID' ><input name='farSerialID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"


 
       $("#SerialNumberTab > tbody tr").eq(SerialRowIndex).before(markup);

    $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
    $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val());
    $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
    $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
    $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
    $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
    $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val());


     SeialNBboqAutocomplete(RowCount,"SerialNumberTab"); 

    ///////////////// Update popup Nb in header //////////////////////
    var element = document.getElementById("popupNbSerialno");
    element.innerHTML = "Item # " +(parseInt(SerialRowIndex)+1);

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});

}








///////////// INSERT ABOVE ROW IN NODE POPUP /////////////////

function insertNodeRowAbove(){

	RowCount =  $("#ahmadtab >tbody tr").length;	
              
    var markup = "<tr><td><input type='checkbox' name='records'><button type = 'button' href='#' name='popUpNodeMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
        +"<td name = 'nodeID'><input name='nodeID' id = 'nodeID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'node_Name'><input name='node_Name' id = 'node_Name"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'node_Type'><input name='node_Type' id = 'node_Type"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
        +"<td name = 'farNodeID'><input name='farNodeID' readonly value = 0  type='text' style='width:200px;' class='form-control text-input'/></td>"
    
       $("#ahmadtab > tbody tr").eq(NodeRowIndex).before(markup);
       
     $('#popupNodeId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
     $('#popupNodeName').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
     $('#popupNodeType').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val());
     $('#popupNodeSeqId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val());



            NodeboqAutocomplete(RowCount,"ahmadtab");

     //////////////// Update popup Nb in header ///////////////////////
     
     var element = document.getElementById("popupNbNode");
     element.innerHTML = "Item # " +(parseInt(NodeRowIndex)+1);

     $("#formStatus").text("Not Saved");
     $('.dot').css({"background-color" : "orange"});

}











///////////// INSERT ABOVE ROW IN SITE POPUP /////////////////

function insertSiteRowAbove(){
						
	RowCount =  $("#sitetab >tbody tr").length;	
   
     var markup = "<tr><td><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	+"<td name = 'wareID'><input name='wareID' id = 'wareID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'siteID'><input name='siteID' id = 'siteID"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'siteName'><input name='siteName' id = 'siteName"+RowCount+"' type='text' style='width:200px;' class='form-control text-input'/></td>"
	+"<td name = 'farSiteID'><input name='farSiteID' type='text' readonly value = 0 style='width:200px;' class='form-control text-input'/></td>"
	
 
       $("#sitetab > tbody tr").eq(SiteRowIndex).before(markup);

     $('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
     $('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
     $('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
     $('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val());


        SiteboqAutocomplete(RowCount,"sitetab");

     /////////////////// Update popup Nb in header ////////////////////
     
     var element = document.getElementById("popupNbSite");
     element.innerHTML = "Item # " +(parseInt(SiteRowIndex)+1);

     $("#formStatus").text("Not Saved");
     $('.dot').css({"background-color" : "orange"});

}











///////////// NEXT ROW IN MODEL PART NB POPUP /////////////////

function nextModPartRow(){

var rowModPartCount = $("#bisotab >tbody tr").length;
console.log("rowCount in BoQ:" +rowModPartCount);

ModPartRowIndex++ ;
var nextModPartIndex = parseInt(ModPartRowIndex);
console.log("Next Index" +nextModPartIndex);

if(ModPartRowIndex >= 0 && ModPartRowIndex < rowModPartCount) {

console.log("Welcome to Next function");

    $('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
    $('#popupModelPartID').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val());

    ////////////// Check if primary checkbox in boq table is checked ///////////////////////
     
        if ($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
                $('#popupPrimary').prop('checked',true);
                console.log("Popup checkbox is checked")
            }
        else 
        {
        $('#popupPrimary').prop('checked', false);
        console.log("Popup checkbox is UNCHECKED")
        
        }

///////////// update popu  header //////////////////////////

var element = document.getElementById("popupNbModpart");
element.innerHTML = "Item # " +(nextModPartIndex+1);
}
else if (ModPartRowIndex >= rowModPartCount) {

console.log("ADD NEW ROW USING NEXT BUTTON");


/////////////// change status //////////////////////

$("#formStatus").text("Not Saved");
$('.dot').css({"background-color" : "orange"});

addModelPartrow("addRow");

$('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
$('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
$('#popupQty').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
$('#popupModelPartID').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val());

//Check if primary checkbox in boq table is checked 
    if ($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
            $('#popupPrimary').prop('checked',true);
            console.log("Popup checkbox is checked")
        }
    else 
    {
    $('#popupPrimary').prop('checked', false);
    console.log("Popup checkbox is UNCHECKED")
    
    }

    
    var element = document.getElementById("popupNbModpart");
    element.innerHTML = "Item # " +(nextModPartIndex+1);
}


}










///////////// NEXT ROW IN SERIAL POPUP /////////////////

function nextSerialRow(){

var rowSerialCount = $("#SerialNumberTab >tbody tr").length;
console.log("rowCount in BoQ:" +rowSerialCount);

SerialRowIndex++ ;
var nextSerialIndex = parseInt(SerialRowIndex);
console.log("Next Index" +nextSerialIndex);

if(SerialRowIndex >= 0 && SerialRowIndex < rowSerialCount) {

    console.log("Welcome to Next function");

    $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
    $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val());
    $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
    $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
    $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
    $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
    $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val());



    /////////////////// update header popup /////////////////////
    
    var element = document.getElementById("popupNbSerialno");
    element.innerHTML = "Item # " +(nextSerialIndex+1);
}
else if (SerialRowIndex >= rowSerialCount) {

    console.log("ADD NEW ROW USING NEXT BUTTON");


    ////////////// change status ////////////////////
    
    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
    
    addSerialrows("addRow");
    
        $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
        $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val());
        $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
        $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
        $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
        $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
        $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val());

        var element = document.getElementById("popupNbSerialno");
        element.innerHTML = "Item # " +(nextSerialIndex+1);
}
    

}












///////////// NEXT ROW IN NODE POPUP /////////////////

function nextNodeRow(){

var rowNodeCount = $("#ahmadtab >tbody tr").length;
console.log("rowCount in BoQ:" +rowNodeCount);

NodeRowIndex++ ;
var nextNodeIndex = parseInt(NodeRowIndex);
console.log("Next Index" +nextNodeIndex);

if(NodeRowIndex >= 0 && NodeRowIndex < rowNodeCount) {

    console.log("Welcome to Next function");

    $('#popupNodeId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
    $('#popupNodeName').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
    $('#popupNodeType').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val());
    $('#popupNodeSeqId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val());

    var element = document.getElementById("popupNbNode");
    element.innerHTML = "Item # " +(nextNodeIndex+1);
}
else if (NodeRowIndex >= rowNodeCount) {

    console.log("ADD NEW ROW USING NEXT BUTTON");

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});

    addNoderows("addRow");
    
        $('#popupNodeId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
        $('#popupNodeName').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
        $('#popupNodeType').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val());
        $('#popupNodeSeqId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val());

        var element = document.getElementById("popupNbNode");
        element.innerHTML = "Item # " +(nextNodeIndex+1);
}
    

}











///////////// NEXT ROW IN SITE POPUP /////////////////

function nextSiteRow(){

var rowSiteCount = $("#sitetab >tbody tr").length;
console.log("rowCount in BoQ:" +rowSiteCount);

SiteRowIndex++ ;
var nextSiteIndex = parseInt(SiteRowIndex);
console.log("Next Index" +nextSiteIndex);

if(SiteRowIndex >= 0 && SiteRowIndex < rowSiteCount) {


    $('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
    $('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
    $('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
    $('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val());



     /////////////////////////// update header //////////////////////////
     
    var element = document.getElementById("popupNbSite");
    element.innerHTML = "Item # " +(nextSiteIndex+1);
}
else if (SiteRowIndex >= rowSiteCount) {

    console.log("ADD NEW ROW USING NEXT BUTTON");

    //////////////// change status ////////////////////
    
    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
    
    addSiterows("addRow");
    

        $('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
        $('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
        $('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
        $('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val());

        ////////////////////// update header //////////////////////////
        
        var element = document.getElementById("popupNbSite");
        element.innerHTML = "Item # " +(nextSiteIndex+1);
}
    

}










///////////// DELETE ROW IN MODEL PART NUMBER POPUP /////////////////

function deleteModPartBoqRow() {

console.log("RowIndx " +ModPartRowIndex);


$("#bisotab >tbody").find("tr").eq(ModPartRowIndex).remove();
//console.log($("#partnodeTab > tbody").find("tr").eq(""+NodeRowIndex).html());

////////////// Get Nb of rows after delete /////////////////////
var rowModPartCount = $("#bisotab >tbody tr").length;
console.log("rowCount in BoQ:" +rowModPartCount);

//NodeRowIndex--;

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
    
    //SetCalcPopUp();
    if (ModPartRowIndex == 0 && rowModPartCount ==0) {

        $("#modPartModal").modal("hide");

        }
    if(ModPartRowIndex >= 0 && ModPartRowIndex < rowModPartCount) {
        
        $('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
        $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
        $('#popupQty').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
        $('#popupModelPartID').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val());

        ////////////////// Check if primary checkbox in boq table is checked ///////////////////////////////
         
            if ($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
                    $('#popupPrimary').prop('checked',true);
                    console.log("Popup checkbox is checked")
                }
            else 
            {
            $('#popupPrimary').prop('checked', false);
            console.log("Popup checkbox is UNCHECKED")
            
            }

        /////////////////// Update popup Nb in header //////////////////////////////
        var element = document.getElementById("popupNbModpart");
        element.innerHTML = "Item # " +(ModPartRowIndex+1);  
        //NodeRowIndex++;
    }
    ///////////////////  Show previous record ////////////////////////////
    
        if (ModPartRowIndex >= rowModPartCount){
        ModPartRowIndex--;


        $('#popupItemModel').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val());
        $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val());
        $('#popupQty').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val());
        $('#popupModelPartID').val($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val());

        //////////////////// Check if primary checkbox in boq table is checked /////////////////////////// 
            if ($("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').is(':checked')) {
                    $('#popupPrimary').prop('checked',true);
                    console.log("Popup checkbox is checked")
                }
            else 
            {
            $('#popupPrimary').prop('checked', false);
            console.log("Popup checkbox is UNCHECKED")
            
            }

        /////////////////// Update popup Nb in header ///////////////////////////// 
        var element = document.getElementById("popupNbModpart");
        element.innerHTML = "Item # " +(ModPartRowIndex+1); 
    }

    }











///////////// DELETE ROW IN SERIAL POPUP /////////////////

function deleteSerialBoqRow() {

console.log("RowIndx " +SerialRowIndex);

$("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).remove();

//////////////////// Get Nb of rows after delete ///////////////////////////

var rowSerialCount = $("#SerialNumberTab >tbody tr").length;
console.log("rowCount in BoQ:" +rowSerialCount);

//NodeRowIndex--;

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
    
    //SetCalcPopUp();
    if (SerialRowIndex == 0 && rowSerialCount ==0) {

        $("#serialnoModal").modal("hide");

        }
    if(SerialRowIndex >= 0 && SerialRowIndex < rowSerialCount) {

        $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
        $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val());
        $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
        $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
        $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
        $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
        $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val());

        ////////////// Update popup Nb in header /////////////////////////
        
        var element = document.getElementById("popupNbSerialno");
        element.innerHTML = "Item # " +(SerialRowIndex+1);  
        //NodeRowIndex++;
    }
    //////////////// Show previous record ///////////////////////////
     
        if (SerialRowIndex >= rowSerialCount){
        SerialRowIndex--;
        
        $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val());
        $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val());
        $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val());
        $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val());
        $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val());
        $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val());
        $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val());

        //////////////// Update popup Nb in header ////////////////////////
         
        var element = document.getElementById("popupNbSerialno");
        element.innerHTML = "Item # " +(SerialRowIndex+1); 
    }

    }













///////////// DELETE ROW IN NODE POPUP /////////////////

function deleteNodeBoqRow() {

console.log("RowIndx " +NodeRowIndex);
//NodeRowIndex++;

$("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).remove();

////////////////// Get Nb of rows after delete ///////////////////////////////

var rowNodeCount = $("#ahmadtab >tbody tr").length;
console.log("rowCount in BoQ:" +rowNodeCount);

//NodeRowIndex--;

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
    
    if (NodeRowIndex == 0 && rowNodeCount ==0) {

        $("#nodeModal").modal("hide");

        }
    if(NodeRowIndex >= 0 && NodeRowIndex < rowNodeCount) {
        //NodeRowIndex++;
        $('#popupNodeId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
        $('#popupNodeName').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
        $('#popupNodeType').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val());
        $('#popupNodeSeqId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val());

        ////////////////// Update popup Nb in header /////////////////////////
         
        var element = document.getElementById("popupNbNode");
        element.innerHTML = "Item # " +(NodeRowIndex+1);  
        //NodeRowIndex++;
    }
    
    /////////////////// Show previous record ////////////////////////////
    
///////////// DELETE ROW IN SERIAL POPUP /////////////////
        if (NodeRowIndex >= rowNodeCount){
        NodeRowIndex--;


        $('#popupNodeId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val());
        $('#popupNodeName').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val());
        $('#popupNodeType').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val());
        $('#popupNodeSeqId').val($("#ahmadtab > tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val());

        //Update popup Nb in header 
        var element = document.getElementById("popupNbNode");
        element.innerHTML = "Item # " +(NodeRowIndex+1); 
    }

    console.log("rowNodeCount: "+rowNodeCount);
    console.log("rowNodeIndex: "+NodeRowIndex);
    }













///////////// DELETE ROW IN SITE POPUP /////////////////

function deleteSiteBoqRow() {

console.log("RowIndx " +SiteRowIndex);
//NodeRowIndex++;

$("#sitetab >tbody").find("tr").eq(SiteRowIndex).remove();

/////////////////// Get Nb of rows after delete ////////////////////////////

var rowSiteCount = $("#sitetab >tbody tr").length;
console.log("rowCount in BoQ:" +rowSiteCount);

//NodeRowIndex--;

    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
    
    if (SiteRowIndex == 0 && rowSiteCount ==0) {

        $("#siteModal").modal("hide");

        }
    if(SiteRowIndex >= 0 && SiteRowIndex < rowSiteCount) {
        //NodeRowIndex++;
        $('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
        $('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
        $('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
        $('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val());

        //////////////////// Update popup Nb in header ////////////////////////////////
         
        var element = document.getElementById("popupNbSite");
        element.innerHTML = "Item # " +(SiteRowIndex+1);  
        //NodeRowIndex++;
    }
    ////////////////// Show previous record /////////////////////////////
     
        if (SiteRowIndex >= rowSiteCount){
        SiteRowIndex--;


        $('#popupWareId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val());
        $('#popupSiteId').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val());
        $('#popupSiteName').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val());
        $('#popupSiteSeqID').val($("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val());

        /////////////////////// Update popup Nb in header //////////////////////////
         
        var element = document.getElementById("popupNbSite");
        element.innerHTML = "Item # " +(SiteRowIndex+1); 
    }

    console.log("rowNodeCount: "+rowSiteCount);
    console.log("rowNodeIndex: "+SiteRowIndex);
    }



    // ******************************************************************************************************************* //
    
    
    function getContextPath() {
        return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
     }


  $(function(){









    ////////////////////// NODE ID NAME AND TYPE  AUTOCOMPLETE IN NODE PPOPUP /////////////////////////////////


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
			this.value = ui.item[2];
			$("#popupNodeName").val(ui.item[3]);
			$("#popupNodeType").val(ui.item[4]);
			return false;
		}
	  }).autocomplete("instance")._renderItem = function(ul, item) {
		return $('<li class="each"></li>').data( "item.autocomplete", item )
		.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		item[2] + "</span><br><span class='desc'>" +
	    item[3] + ', ' +item[4]+'</span></div>').appendTo(ul);
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
		this.value = ui.item[3];
		$("#popupNodeId").val(ui.item[2]);
		$("#popupNodeType").val(ui.item[4]);
		return false;

	}
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $('<li class="each"></li>').data( "item.autocomplete", item )
	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	item[3] + "</span><br><span class='desc'>" +
	item[2] + ', ' +item[4]+'</span></div>').appendTo(ul);
};
$("#popupNodeName").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}						
}); // End NodeName autocomplete in Nodepopup

 //Node type popup autocomplete
$("#popupNodeType").autocomplete({
	source: function(request, response) {
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetAllNode',
		data: {
			"Node": $("#popupNodeType").val(),
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
		this.value = ui.item[4];
		$("#popupNodeId").val(ui.item[2]);
		$("#popupNodeName").val(ui.item[3]);
		return false;

	}
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $('<li class="each"></li>').data( "item.autocomplete", item )
	.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	item[4] + "</span><br><span class='desc'>" +
	item[2] + ', ' +item[3]+'</span></div>').appendTo(ul);
};
$("#popupNodeType").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
		}						
}); // End NodeType autocomplete in Nodepopup







    ////////////////////// WARE ID AUTOCOMPLETE IN SITE PPOPUP /////////////////////////////////

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
                    console.log('Warehouse data: ;'+ data.globalList);
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
    }); 










 
    ////////////////////// SITE ID AUTOCOMPLETE IN SITE PPOPUP /////////////////////////////////

    $("#popupSiteId").autocomplete({
    source: function(request, response) {
        var wareID =$("#popupWareId").val();
        var siteName =$("#popupSiteName").val();
        
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx+'/GetAllWarehouse',
                data: {
							  "warehouseName" : request.term,						 

                    },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.globalList);
                        console.log('Site data: ;'+ data.globalList);
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
    }); 











    ////////////////////// SITE NAME AUTOCOMPLETE IN SITE PPOPUP /////////////////////////////////
    
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
                            console.log('Site data: ;'+ data.globalList);
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
        }); 











    ////////////////////// ITEM MODEL AUTOCOMPLETE IN MODEL & PART NB PPOPUP /////////////////////////////////
   
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
                    });












    ////////////////////// ITEM PARTNB AUTOCOMPLETE IN MODEL & PARTNB PPOPUP /////////////////////////////////

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
                }); 









    ////////////////////// SERIAL NB AUTOCOMPLETE IN SERIAL PPOPUP /////////////////////////////////

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
       
    
    
    
    
    
    
    
    
    ////////////////////// MODEL AUTOCOMPLETE IN SERIAL PPOPUP /////////////////////////////////

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










    ////////////////////// PART NB AUTOCOMPLETE IN SERIAL PPOPUP /////////////////////////////////

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
                             console.log(0000);
                             
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











    ////////////////////// NODE ID AUTOCOMPLETE IN SERIAL PPOPUP /////////////////////////////////

 








    ////////////////////// SITE AUTOCOMPLETE IN SERIAL PPOPUP /////////////////////////////////

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








    //////////////////////// RESIZE ANDDRAG THE POPUP ///////////////////////

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
    










    //////////////////// RESET THE POPUP TO ORIGINAL SIZE AFTER RESZING ////////////////////////////////////// 

    $('#nodeModal,#siteModal,#modPartModal,#serialnoModal').on('hidden.bs.modal', function() {
        $(this).find('.modal-content').css({'width': '', 'height': ''});
    })







    //////////////////// RESET THE POPUP POSITION AFTER IT HAS BEEN DRAGGED AND CLOSED //////////////////////////////////////
    
    $('body').on('hidden.bs.modal', function() { 
    $('.modal-dialog').css({'top': '', 'left':''});
    })










    ////////////////////////////// MINIMIZE AND MAXIMIZE FUNCTION FOR POPUP ///////////////////////////

    $(".modalMinimize").on("click", function(){

    $(".modal-body").slideToggle();
    $(".modal-footer").slideToggle();

    ////////////////////// Toggle between minimize/maximize icons ///////////////////////
    
    var iconToChange = $('.icon-to-change');
    if(iconToChange.hasClass('fa-window-restore')){
        iconToChange.removeClass('fa-window-restore')
        .addClass('fa-minus')
    }
    else{
        iconToChange.addClass('fa-window-restore')
        .removeClass('fa-minus')
    }    		         
    }); 

















    ////////////////////////////////// CLOSE FUNCTION IN MODEL PART NUMBER POPUP ///////////////////////////////////////

    /////////////////////   Close button function in modPartNum popup ///////////////////////////////////
    
    $("button[name='closeModPartPopup']").on("click", function(){
                        
    console.log("Welcome to modPartNumPopup close");
    console.log("On close"+ModPartRowIndex);
    
    //////////////////////// Send input values from popup to boq table ///////////////////////////////////////
    
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
        $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val($('#popupItemPartno').val());
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val($('#popupQty').val());
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val($('#popupModelPartID').val());


    //////////////////////// Check if primary checkbox in popup is checked ////////////////////////////////////
    
        if ($('#popupPrimary').is(':checked')) {
            /////////////////// Call onlyOne method to check only one checkbox in popup and boq ////////////////////////////////
            onlyOne();
                $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked',true);
                console.log("Boq primary checkbox is checked")
            }
            else 
                {
                $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked', false);
            console.log("Boq primary is UNCHECKED")
                
                }
            

    $("#modPartModal").modal("hide");				 	
    }); 
    
    
    ////////////////////////////////// Close the modPartNum popup using ESC /////////////////////////////////////
     	  		    	   
    document.addEventListener('keydown', function(event){
            
    if(event.key === "Escape"){
    
    ///////////////////////////// Send input values from popup to boq table //////////////////////////////////////
    
        $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
        $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val($('#popupItemPartno').val());
        $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val($('#popupQty').val());
        $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val($('#popupModelPartID').val());

    /////////////////////////////// Check if primary checkbox in popup is checked //////////////////////////////////
    
        if ($('#popupPrimary').is(':checked')) {
        
            /////////////////////// Call onlyOne method to check only one checkbox in popup and boq //////////////////
            
            onlyOne();
                $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked',true);
                    console.log("Boq primary checkbox is checked")
                }
            else 
                    {
                $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked', false);
                console.log("Boq primary is UNCHECKED")
                    }
                
        $("#modPartModal").modal("hide");				
    }
});










    ////////////////////////////////// CLOSE FUNCTION IN NODE POPUP ///////////////////////////////////////

////////////////////////////   Close button function in node popup ///////////////////////////////////////

$("button[name='closeNodePopup']").on("click", function(){
                
    console.log("Welcome to nodePopup close");
    console.log("On close"+NodeRowIndex);
    
///////////////////////////// Send input values from popup to boq table ////////////////////////////////////////

    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val($('#popupNodeId').val());
    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val($('#popupNodeName').val());
    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val($('#popupNodeType').val());
    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val($('#popupNodeSeqId').val());

    $("#nodeModal").modal("hide");				 	
    }); 


///////////////////////////// Close the node popup using ESC ////////////////////////////////////////// 
        
    document.addEventListener('keydown', function(event){
            
    if(event.key === "Escape"){

    /////////////////////////// Send input values from popup to boq table ///////////////////////////
    
        $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val($('#popupNodeId').val());
        $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val($('#popupNodeName').val());
        $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val($('#popupNodeType').val());
        $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val($('#popupNodeSeqId').val());

        $("#nodeModal").modal("hide");
            
    }
});
















    ////////////////////////////////// CLOSE FUNCTION IN SITE POPUP ///////////////////////////////////////
    
    $("button[name='closeSitePopup']").on("click", function(){
        
    console.log("Welcome to sitePopup close");
    console.log("On close"+SiteRowIndex);
    
    ///////////////////////////////// Send input values from popup to boq table ////////////////////////////
    
    $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val($('#popupWareId').val());
        $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val($('#popupSiteId').val());
    $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val($('#popupSiteName').val());
    $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val($('#popupSiteSeqID').val());

    $("#siteModal").modal("hide");				 	
    }); 


    ////////////////////////////////// Close the site popup using ESC ////////////////////////////////// 	  		    	   
    document.addEventListener('keydown', function(event){
            
        if(event.key === "Escape"){
        
        ////////////////////////////// Send input values from popup to boq table ///////////////////////////////////
        
            $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val($('#popupWareId').val());
            $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val($('#popupSiteId').val());
            $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val($('#popupSiteName').val());
            $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val($('#popupSiteSeqID').val());

            $("#siteModal").modal("hide");		
        }
    });










    ////////////////////////////////// CLOSE FUNCTION IN SERIAL POPUP ///////////////////////////////////////
    
    $("button[name='closeSerialnoPopup']").on("click", function(){

    console.log("Welcome to SerialNoPopup close");
    console.log("On close"+SerialRowIndex);
    
   ////////////////////////////////// Send input values from popup to boq table ///////////////////////////////////////
   
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val($('#popupSerialno').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val($('#popupMacAddress').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val($('#popupModel').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val($('#popupPartnum').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val($('#popupSite').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val($('#popupPosition').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val($('#popupSerialSeqID').val());


    $("#serialnoModal").modal("hide");				 	
    });


 /////////////////////////////// Close the serialNo popup using ESC ////////////////////////////////////// 
        
    document.addEventListener('keydown', function(event){
            
    if(event.key === "Escape"){


    //////////////////////////////// Send input values from popup to boq table ////////////////////////////////
           
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val($('#popupSerialno').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val($('#popupMacAddress').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val($('#popupModel').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val($('#popupPartnum').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val($('#popupSite').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val($('#popupPosition').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val($('#popupSerialSeqID').val());

        $("#serialnoModal").modal("hide");		
            
    }
});












////////////////////////////////////////// BUTTON PREVIOUS IN MODEL & PART NB POPUP ////////////////////////

$("button[name='previousModPartRow']").on("click", function(){

if(ModPartRowIndex > 0) {
        console.log("Welcome to previous");

        ModPartRowIndex-- ;
        var modPartPrevIndex = parseInt(ModPartRowIndex);
        console.log("PrevIndex" +modPartPrevIndex);

    var prevItmModel = $("#bisotab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="itemModel"]').children('input').val();
    $('#popupItemModel').val(prevItmModel);
    
    var prevItmPart = $("#bisotab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="itemPartNum"]').children('input').val();
    $('#popupItemPartno').val(prevItmPart);

        var prevQty = $("#bisotab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="qtyPartNum"]').children('input').val();
    $('#popupQty').val(prevQty);

    var popupModParID = $("#bisotab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="farItemID"]').children('input').val();
    $('#popupModelPartID').val(popupModParID);

        if ($("#bisotab >tbody").find("tr").eq(modPartPrevIndex).find('td[name="primary"]').children('input').is(':checked')) {

        $('#popupPrimary').prop('checked',true);
        console.log("Prev Popup checkbox is checked")
    }
    else 
        {
    $('#popupPrimary').prop('checked', false);
    console.log("Prev Popup checkbox is UNCHECKED")
    
        }
    
    
    /////////////////// Update popup Nb in header ///////////////////////
    
        var modPartElement = document.getElementById("popupNbModpart");
        modPartElement.innerHTML = "Item # " +(modPartPrevIndex+1);
    }
            
      /////////////////////////// Close node popup on row 0 (end of prev) ////////////////////////////////
    else if (ModPartRowIndex == 0) {
        $("#modPartModal").modal("hide");
                
}
});













 //////////////////////////////////////// BUTTON PREVIOUS IN SERIAL POPUP ///////////////////////////////////

$("button[name='previousSerialRow']").on("click", function(){

if(SerialRowIndex > 0) {
    console.log("Welcome to previous");

    SerialRowIndex -- ;
    var serialPrevIndex = parseInt(SerialRowIndex );
    console.log("PrevIndex" +serialPrevIndex);

    $('#popupSerialno').val($("#SerialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNserialNumber"]').children('input').val());
    $('#popupMacAddress').val($("#SerialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNMacAddress"]').children('input').val());
    $('#popupModel').val($("#SerialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNmodel"]').children('input').val()); 
    $('#popupPartnum').val($("#SerialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNpartNumber"]').children('input').val());
    $('#popupSite').val($("#SerialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNsite"]').children('input').val());
    $('#popupPosition').val($("#SerialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="SNposition"]').children('input').val());  
   $('#popupSerialSeqID').val($("#SerialNumberTab >tbody").find("tr").eq(serialPrevIndex).find('td[name="farSerialID"]').children('input').val());

//////////////////// Update popup Nb in header //////////////////////////////

var serialElement = document.getElementById("popupNbSerialno");
serialElement.innerHTML = "Item # " +(serialPrevIndex+1);
}
    
//////////////////////////// Close node popup on row 0 (end of prev) /////////////////////////////////

else if (serialRowindex  == 0) {
$("#serialnoModal").modal("hide");
        
}
});












 //////////////////////////////////////// BUTTON PREVIOUS IN NODE POPUP ///////////////////////////////////
 
    $("button[name='previousNodeRow']").on("click", function(){

    console.log("index in previous "+prevNodeName);
    if(NodeRowIndex > 0) {
        console.log("Welcome to previous");

        NodeRowIndex-- ;
        var nodePrevIndex = parseInt(NodeRowIndex);
        console.log("PrevIndex" +nodePrevIndex);

        var prevNodeId =$("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val();
        $('#popupNodeId').val(prevNodeId);

        var prevNodeName =$("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val();
        $('#popupNodeName').val(prevNodeName); 

       var prevNodeType =$("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val();
        $('#popupNodeType').val(prevNodeType); 

        var prevNodeSeqID =$("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val();
        $('#popupNodeSeqId').val(prevNodeSeqID);

    ///////////////////////// Update popup Nb in header /////////////////////////////// 
    
    var nodeElement = document.getElementById("popupNbNode");
    nodeElement.innerHTML = "Item # " +(nodePrevIndex+1);
    }

    /////////////////////////// Close node popup on row 0 (end of prev) ///////////////////////////
    
    else if (NodeRowIndex == 0) {
    $("#nodeModal").modal("hide");
                
    }
    });













 //////////////////////////////////////// BUTTON PREVIOUS IN SITE POPUP ///////////////////////////////////
 
$("button[name='previousSiteRow']").on("click", function(){

    if(SiteRowIndex > 0) {
            console.log("Welcome to previous");

            SiteRowIndex-- ;
            var sitePrevIndex = parseInt(SiteRowIndex);
            console.log("PrevIndex" +sitePrevIndex);

            var prevWareId =$("#sitetab >tbody").find("tr").eq(sitePrevIndex).find('td[name="wareID"]').children('input').val();
            $('#popupWareId').val(prevWareId);
            
            var prevSiteId =$("#sitetab >tbody").find("tr").eq(sitePrevIndex).find('td[name="siteID"]').children('input').val();
            $('#popupSiteId').val(prevSiteId);

            var prevSiteName =$("#sitetab >tbody").find("tr").eq(sitePrevIndex).find('td[name="siteName"]').children('input').val();
            $('#popupSiteName').val(prevSiteName); 
        
            var prevSiteSeqID =$("#sitetab >tbody").find("tr").eq(sitePrevIndex).find('td[name="farSiteID"]').children('input').val();
            $('#popupSiteSeqID').val(prevSiteSeqID);

        /////////////////////////// Update popup Nb in header ////////////////////////////// 
        
            var siteElement = document.getElementById("popupNbSite");
            siteElement.innerHTML = "Item # " +(sitePrevIndex+1);
        }
                
          ////////////////////// Close node popup on row 0 (end of prev) //////////////////////////
          
        else if (SiteRowIndex == 0) {
        $("#siteModal").modal("hide");
                    
    }
});

















/////////////////////////////// SEND INPUT VALUE FROM POPUP MODEL & PARTNB POPUP TO BOQ /////////////////////////////////////////

    $('#popupItemModel,#popupItemPartno,#popupQty,#popupPrimary,#popupModelPartID').on(' focusout ', function() {
                        
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="itemPartNum"]').children('input').val($('#popupItemPartno').val());
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="qtyPartNum"]').children('input').val($('#popupQty').val());
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="farItemID"]').children('input').val($('#popupModelPartID').val());
                  
    if ($('#popupPrimary').is(':checked')) {
    
    ////////////////////////// Call onlyOne method to check only one checkbox in popup and boq //////////////////////////
    
    onlyOne();
    $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked',true);
        console.log("Boq primary checkbox is checked")
            }
            else 
            {
                $("#bisotab >tbody").find("tr").eq(ModPartRowIndex).find('td[name="primary"]').children('input').prop('checked', false);
                    console.log("Boq primary is UNCHECKED")
                }	

            });












/////////////////////////////// SEND INPUT VALUE FROM SERIAL POPUP TO BOQ /////////////////////////////////////////

    $('#popupSerialno,#popupMacAddress,#popupModel,#popupPartnum,#popupSite,#popupPosition,#popupSerialSeqID').on(' focusout ', function() {
        
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNserialNumber"]').children('input').val($('#popupSerialno').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNMacAddress"]').children('input').val($('#popupMacAddress').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNmodel"]').children('input').val($('#popupModel').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNpartNumber"]').children('input').val($('#popupPartnum').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNsite"]').children('input').val($('#popupSite').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="SNposition"]').children('input').val($('#popupPosition').val());
    $("#SerialNumberTab >tbody").find("tr").eq(SerialRowIndex).find('td[name="farSerialID"]').children('input').val($('#popupSerialSeqID').val());


});










/////////////////////////////// SEND INPUT VALUE FROM NODE POPUP TO BOQ /////////////////////////////////////////

    $('#popupNodeId,#popupNodeName,#popupNodeSeqId').on(' focusout ', function() {

    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="nodeID"]').children('input').val($('#popupNodeId').val());
    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Name"]').children('input').val($('#popupNodeName').val());
    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="node_Type"]').children('input').val($('#popupNodeType').val());
    $("#ahmadtab >tbody").find("tr").eq(NodeRowIndex).find('td[name="farNodeID"]').children('input').val($('#popupNodeSeqId').val());


});












/////////////////////////////// SEND INPUT VALUE FROM SITE POPUP TO BOQ /////////////////////////////////////////

    $('#popupWareId,#popupSiteId,#popupSiteName,#popupSiteSeqID').on(' focusout ', function() {
        
    $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="wareID"]').children('input').val($('#popupWareId').val());
    $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteID"]').children('input').val($('#popupSiteId').val());
    $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="siteName"]').children('input').val($('#popupSiteName').val());
    $("#sitetab >tbody").find("tr").eq(SiteRowIndex).find('td[name="farSiteID"]').children('input').val($('#popupSiteSeqID').val());

});



  });
  
  
  
  
  
  
  
  
  
  
  
// FUNCTION MODEL & PARTNUMBER TAB AUTOCOMPLETE
					 
function PNboqAutocomplete(rowCnt,tableIndx){

	var ctx=getContextPath();
	var tableID=tableIndx;
														 
								   
	
	//ITEM MODEL autocomplete
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
		        	this.value = ui.item[0];
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

   source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: {
                    Node: request.term
                },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log(222);
                }
            });
        },
        minLength: 0,
        maxResults: 40,
        scroll: true,

        select: function (event, ui) {
            this.value = ui.item ? ui.item[2] : '';
            document.getElementById("node_Name" + rowCnt).value = ui.item[3];
            document.getElementById("node_Type" + rowCnt).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
            item[2] + "</b></span><br><span class='name' >" +
            item[3] + ", " + item[4] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#nodeID' + rowCnt).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }

        
    });
    
     $('#node_Name' + rowCnt).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: {
                    Node: request.term
                },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log(222);
                }
            });
        },
        minLength: 0,
        maxResults: 40,
        scroll: true,

        select: function (event, ui) {
            this.value = ui.item ? ui.item[3] : '';
            document.getElementById("nodeID" + rowCnt).value = ui.item[2];
            document.getElementById("node_Type" + rowCnt).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
            item[3] + "</b></span><br><span class='name' >" +
            item[2] + ", " + item[4] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#node_Name' + rowCnt).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });

         $('#node_Type' + rowCnt).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: {
                    Node: request.term
                },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log(222);
                }
            });
        },
        minLength: 0,
        maxResults: 40,
        scroll: true,

        select: function (event, ui) {
            this.value = ui.item ? ui.item[4] : '';
            document.getElementById("nodeID" + rowCnt).value = ui.item[2];
            document.getElementById("node_Name" + rowCnt).value = ui.item[3];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = ("<div class='acItem'><span class='desc'><b>" +
            item[4] + "</b></span><br><span class='name' >" +
            item[3] + ", " + item[2] + "</span></div>");

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    $('#node_Type' + rowCnt).focus(function () {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });


} // end NodeboqAutocomplete fct

