





 var rowindx =0;
 var rowindxAssign=0;
 var rowindxActions=0;
 var rowindxDCP=0;
 		 //Open popup fct
 		 
 		 //openPopDCP

function openPopDCP(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 rowindxDCP = (buttonNodeRowIndx[0].rowIndex - 1);
	 console.log("Row index of clicked button:" +rowindxDCP);
	 
		$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
	 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
		$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
		$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
		$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
		$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
		$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
		$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
		$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
		$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
		$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
		$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());

		
		 $("#DNModalDCP").modal("show");

		 var element = document.getElementById("popupDCP");
         element.innerHTML = "Item # " +rowindxDCP;
         console.log(rowindxDCP);
			// SetCalcPopUp();
 }
//openPopActions

function openPopActions(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 rowindxActions = (buttonNodeRowIndx[0].rowIndex - 1);
	 console.log("Row index of clicked button:" +rowindxActions);
	 
		$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
	 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
		$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
		$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
		$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
		$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
		$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());

		
		 $("#DNModalActions").modal("show");

		 var element = document.getElementById("popupActions");
         element.innerHTML = "Item # " +rowindxActions;
         console.log(rowindxActions);
			// SetCalcPopUp();
 }
 function openPopAssign(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 rowindxAssign = (buttonNodeRowIndx[0].rowIndex - 1);
	 console.log("Row index of clicked button:" +rowindxAssign);

	 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
		$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
		$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
		$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
		$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
		$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());

		 $("#DNModalAssign").modal("show");

		 var element = document.getElementById("popupAssign");
         element.innerHTML = "Item # " +rowindxAssign;
         console.log(rowindxAssign);
			// SetCalcPopUp();
 }

 function openPopLFP(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 rowindx = (buttonNodeRowIndx[0].rowIndex - 1);
	 console.log("Row index of clicked button::" +rowindx);

	 $('#popuplinkID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkID"]').children('input').val());
		$('#popuplinkName') .val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkName"]').children('input').val());
		$('#popuplongg').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="longg"]').children('input').val());

		$('#popuplatitude').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="latitude"]').children('input').val());
	  //console.log("option selected in transtype "+$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').children('option:selected').val());
		$('#popupReason').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="Reason"]').children('input').val());
		$('#popuplfpdescription').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpdescription"]').children('input').val());
		$('#popuplfpID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpID"]').children('input').val());

		 $("#DNModalLFP").modal("show");

		 var element = document.getElementById("popupLFP");
         element.innerHTML = "Item # " +rowindx;
         console.log(rowindx);
			// SetCalcPopUp();
 }


/////////////////////////////////////////////

function insertRowBelowDCP(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrowsDCP('addRowBelowDCP');

  rowindxDCP++ ;
	var belowIndex = parseInt(rowindxDCP);
	console.log("belowIndex:" +belowIndex);


	$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
	$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
	$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
	$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
	$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
	$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
	$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
	$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
	$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
	$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
	$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());

		//Update popup Nb in header
		var element = document.getElementById("popupDCP");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();

 }
 

function insertRowBelowActions(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrowsActions('addRowBelowActions');

  rowindxActions++ ;
	var belowIndex = parseInt(rowindxActions);
	console.log("belowIndex:" +belowIndex);


	$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
	$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
	$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
	$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
	$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
	$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());

		//Update popup Nb in header
		var element = document.getElementById("popupActions");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();

 }
 
function insertRowBelowAssign(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
	 val =$("#popupassignDate").val();
     val1 = Date.parse(val);
	
 if (isNaN(val1) == true) {
         
          alert('Assign Date is not valid');
          return false;
        }
        else {
  addrowsAssign('addRowBelowAssign');

	rowindxAssign++ ;
	var belowIndex = parseInt(rowindxAssign);
	console.log("belowIndex:" +belowIndex);


 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
	$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
	$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
	$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
	$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
	$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
	$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
	$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());

		//Update popup Nb in header
		var element = document.getElementById("popupAssign");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();
}
 }// End insertRowBelow fct in popup



    	function insertRowBelow(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrows('addRowBelow');

	rowindx++ ;
	var belowIndex = parseInt(rowindx);
	console.log("belowIndex:" +belowIndex);


	$('#popuplinkID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkID"]').children('input').val());
	$('#popuplinkName') .val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkName"]').children('input').val());
	$('#popuplongg').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="longg"]').children('input').val());

	$('#popuplatitude').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="latitude"]').children('input').val());
  //console.log("option selected in transtype "+$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').children('option:selected').val());
	$('#popupReason').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="Reason"]').children('input').val());
	$('#popuplfpdescription').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpdescription"]').children('input').val());
	$('#popuplfpID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpID"]').children('input').val());
 

		//Update popup Nb in header
		var element = document.getElementById("popupLFP");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();

 }// End insertRowBelow fct in popup


 // Insert Row Above fct
  function insertRowAboveDCP(){

	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
    addrowsDCP('addRowAboveDCP');
//----------------------------------------

	console.log("aboveIndex:" +rowindxDCP);

	$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
	$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
	$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
	$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
	$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
	$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
	$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
	$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
	$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
	$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
	$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());
	
		//Update popup Nb in header
		var element = document.getElementById("popupDCP");
		element.innerHTML = "Item # " +parseInt(rowindxDCP);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();

 }// End insertRowAbove fct in popup
 
  function insertRowAboveActions(){

	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
    addrowsActions('addRowAboveActions');
//----------------------------------------

	console.log("aboveIndex:" +rowindxActions);

	$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
	$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
	$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
	$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
	$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
	$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());
	
		//Update popup Nb in header
		var element = document.getElementById("popupActions");
		element.innerHTML = "Item # " +parseInt(rowindxActions);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();

 }// End insertRowAbove fct in popup
 
 
 function insertRowAboveAssign(){

	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
 
 val =$("#popupassignDate").val();
     val1 = Date.parse(val);
	
 if (isNaN(val1) == true) {
         
          alert('Assign Date is not valid');
          return false;
        }
        else {
           addrowsAssign('addRowAboveAssign');
//----------------------------------------

	console.log("aboveIndex:" +rowindxAssign);
 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
	$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
	$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
	$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
	$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
	$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
	$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
	$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());
	
		//Update popup Nb in header
		var element = document.getElementById("popupAssign");
		element.innerHTML = "Item # " +parseInt(rowindxAssign);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();
}
 }// End insertRowAbove fct in popup
 
 
 function insertRowAbove(){

	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
  addrows('addRowAbove');
//----------------------------------------

	console.log("aboveIndex:" +rowindx);

	$('#popuplinkID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkID"]').children('input').val());
	$('#popuplinkName') .val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkName"]').children('input').val());
	$('#popuplongg').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="longg"]').children('input').val());

	$('#popuplatitude').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="latitude"]').children('input').val());
  //console.log("option selected in transtype "+$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').children('option:selected').val());
	$('#popupReason').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="Reason"]').children('input').val());
	$('#popuplfpdescription').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpdescription"]').children('input').val());
	$('#popuplfpID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpID"]').children('input').val());


		//Update popup Nb in header
		var element = document.getElementById("popupLFP");
		element.innerHTML = "Item # " +parseInt(rowindx);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();

 }// End insertRowAbove fct in popup



  // Next Fct in popup
  
   function nextRowDCP(){

	// Get total nb of rows
	var rowCount = $("#dcproblemTab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);

	rowindxDCP++ ;
	var nextIndex = parseInt(rowindxDCP);
	console.log("Next Index" +nextIndex);

	if(rowindxDCP >= 0 && rowindxDCP < rowCount) {
		console.log("Welcome to Next function"+rowindxDCP);

		$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
	 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
		$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
		$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
		$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
		$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
		$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
		$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
		$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
		$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
		$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
		$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());

		//Update popup Nb in header

		var element = document.getElementById("popupDCP");
			//alert(nextIndex);
			//alert(rowindxActions);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxActions exceed the row count
	else if (rowindxDCP >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
    addrowsDCP('addRowDCP');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
			
			$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
		 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
			$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
			$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
			$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
			$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
			$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
			$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
			$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
			$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
			$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
			$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());

			//alert(rowindxActions);
		
			//Update popup Nb in header
			var element = document.getElementById("popupDCP");
			element.innerHTML = "Item # " +nextIndex;
			}
 }
  
  
    function nextRowActions(){

	// Get total nb of rows
	var rowCount = $("#actionTab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);

	rowindxActions++ ;
	var nextIndex = parseInt(rowindxActions);
	console.log("Next Index" +nextIndex);

	if(rowindxActions >= 0 && rowindxActions < rowCount) {
		console.log("Welcome to Next function"+rowindxActions);

		$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
	 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
		$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
		$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
		$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
		$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
		$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());

		//Update popup Nb in header

		var element = document.getElementById("popupActions");
			//alert(nextIndex);
			//alert(rowindxActions);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxActions exceed the row count
	else if (rowindxActions >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
    addrowsActions('addRowActions');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
			
			$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
		 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
			$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
			$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
			$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
			$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
			$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());

			//alert(rowindxActions);
		
			//Update popup Nb in header
			var element = document.getElementById("popupActions");
			element.innerHTML = "Item # " +nextIndex;
			}
 }
  
  
  function nextRowAssign(){

	// Get total nb of rows
	var rowCount = $("#AssignTab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);

	rowindxAssign++ ;
	var nextIndex = parseInt(rowindxAssign);
	console.log("Next Index" +nextIndex);
	 val =$("#popupassignDate").val();
     val1 = Date.parse(val);
	
 if (isNaN(val1) == true) {
         
          alert('Assign Date is not valid');
          return false;
        }
        else {
	if(rowindxAssign >= 0 && rowindxAssign < rowCount) {
		console.log("Welcome to Next function"+rowindxAssign);

	 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
		$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
		$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
		$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
		$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
		$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());

		//Update popup Nb in header

		var element = document.getElementById("popupAssign");
			//alert(nextIndex);
			//alert(rowindxAssign);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxAssign exceed the row count
	else if (rowindxAssign >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
    addrowsAssign('addRowAssign');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
			
		 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
			$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
			$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
			$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
			$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
			$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
			$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
			$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());

			//alert(rowindxAssign);
		
			//Update popup Nb in header
			var element = document.getElementById("popupAssign");
			element.innerHTML = "Item # " +nextIndex;
			}
			
 }}
  
  
 function nextRow(){

	// Get total nb of rows
	var rowCount = $("#lfpTab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);

	rowindx++ ;
	var nextIndex = parseInt(rowindx);
	console.log("Next Index" +nextIndex);

	if(rowindx >= 0 && rowindx < rowCount) {
		console.log("Welcome to Next function"+rowindx);


		$('#popuplinkID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkID"]').children('input').val());
		$('#popuplinkName') .val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkName"]').children('input').val());
		$('#popuplongg').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="longg"]').children('input').val());

		$('#popuplatitude').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="latitude"]').children('input').val());
	  //console.log("option selected in transtype "+$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').children('option:selected').val());
		$('#popupReason').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="Reason"]').children('input').val());
		$('#popuplfpdescription').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpdescription"]').children('input').val());
		$('#popuplfpID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpID"]').children('input').val());
	 


			//Update popup Nb in header
			var element = document.getElementById("popupLFP");
			//alert(nextIndex);
			//alert(rowindx);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindx exceed the row count
	else if (rowindx >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
    addrows('addRow');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();
			//console.log("AAAAAAAAa"+$("#formStatus").text());
//------------------------------------

	$('#popuplinkID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkID"]').children('input').val());
	$('#popuplinkName') .val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkName"]').children('input').val());
	$('#popuplongg').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="longg"]').children('input').val());

	$('#popuplatitude').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="latitude"]').children('input').val());
  //console.log("option selected in transtype "+$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').children('option:selected').val());
	$('#popupReason').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="Reason"]').children('input').val());
	$('#popuplfpdescription').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpdescription"]').children('input').val());
	$('#popuplfpID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpID"]').children('input').val());
 
//	alert(rowindx);

			//Update popup Nb in header
			var element = document.getElementById("popupLFP");
			element.innerHTML = "Item # " +nextIndex;
			}


	

	
 }

 // End next fct in popup
 
 
 //Add rows fct in popup
 
  function addrowsDCP(stat){
 		 var rowCount= $("#dcproblemTab >tbody tr").length;
	console.log("Row Count in BOQ:" +rowCount);
	
	  var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowDCP' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopDCP(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	  	
	  	+"<td name = 'type'><input name='type' type='text' style='width:200px;' class='form-control text-input' /></td>"

	  	+"<td name = 'siteID'><input name='siteID' type='text' style='width:200px;' class='form-control text-input'/></td>"
	  	+"<td name = 'siteName'><input name='siteName' type='text' style='width:200px;' class='form-control text-input'/></td>"

	  	+"<td name = 'nodeID'><input name='nodeID' type='text' style='width:200px;' class='form-control text-input'/></td>"
	  	+"<td name = 'nName'><input name='nName' type='text' style='width:200px;' class='form-control text-input'/></td>"

	  	+"<td name = 'cabinet'><input name='cabinet' type='text' style='width:200px;' class='form-control text-input'/></td>"
	  	+"<td name = 'slot'><input name='slot' type='text' style='width:200px;' class='form-control text-input'/></td>"
	  	
	  	+"<td name = 'board'><input name='board' type='text' style='width:200px;' class='form-control text-input'/></td>"

	  	+"<td name = 'antenna'><input name='antenna' type='text' style='width:200px;' class='form-control text-input'/></td>"
	  	+"<td name = 'version'><input name='version' type='text' style='width:200px;' class='form-control text-input'/></td>"
	  	+"<td name = 'note'><input name='note' type='text' style='width:400px;' class='form-control text-input' /></td>"
	  	+"<td name = 'ID'><input name='ID' type='text' style='width:200px;' class='form-control text-input' readonly/></td>"

	 var myDiv = document.getElementById("dcproblemTab");
	 	    myDiv.scrollTop = myDiv.scrollHeight;
	 	    
		 if (stat == "addRowDCP")
         {
      $("#dcproblemTab > tbody").append(markup);
      		rowCount++;
      
    }
    if(stat == "addRowBelowDCP")
    {
      $("#dcproblemTab > tbody tr").eq(rowindxDCP).after(markup);
      rowCount++;
    }
    if(stat == "addRowAboveDCP")
    {
       $("#dcproblemTab > tbody tr").eq(rowindxDCP).before(markup);
      rowCount= rowindxDCP+1;
    }
 ////////////////////////////////////////////////////////////////
//AUTOCOMPLETE

			

			function getAllSitesName()
			{
				SnArray = [];
				var SnValue = "";
				$("input[name=siteName]").each(function(){
					if($(this).val() == "")
						SnValue = "empty";
					else
						SnValue = $(this).val();
					SnArray.push(SnValue);
				});
				return SnArray;   
			}

			function SetIndexRow(obj)
			{
				var row_index = $(obj).parent().parent().index();
				$("#RowIndex").val(row_index);
			}
			function getAllSitesID()
			{
				SiArray = [];
				var SiValue = "";
				$("input[name=siteID]").each(function(){
					if($(this).val() == "")
						SiValue = "empty";
					else
						SiValue = $(this).val();
					SiArray.push(SiValue);
				});
				return SiArray;   
			}

				function SetIndexRowLFP(obj)
				{
					var row_index = $(obj).parent().parent().index();
					$("#RowIndexLFP").val(row_index);
				}

			 $('#lfpTab tr td input').on('focus', function() {

					SetIndexRowLFP(this);
				});

				
			 $('#dcproblemTab tr td input').on('focus', function() {

					SetIndexRow(this);
				});

			
			function SetIndexRowAssign(obj)
			{
				var row_index = $(obj).parent().parent().index();
				$("#RowIndex").val(row_index);
			}


			 $('#AssignTab tr td input').on('focus', function() {

				 SetIndexRowAssign(this);
				});

			
			function SetIndexRowAssign(obj)
			{
				var row_index = $(obj).parent().parent().index();
				$("#RowIndex").val(row_index);
			}
			function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
			$('#dcproblemTab > tbody > tr').eq(rowCount-1).find('input[name ="siteName"]').autocomplete({
			    	    source: function(request, response, event, ui) {
			    	    //	console.log ("dd"+i);
			    	    		var ctx =getContextPath(); 
			    	    	
			    	    	 var RowIndex = document.getElementById('RowIndex').value;
			    	        SnArray = getAllSitesName();
		     			 var sitename = SnArray[RowIndex];
		     			
		     			 if(sitename != "empty")
			        		 {
			    	    	console.log ("hello");
			        		 
		     				 siten = sitename.split(":");
				        		 siten = sitename[0];
				             }
		     			 else
		     			 
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
				                     
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error AUTO");
				                 }
				             });
				        }, minLength:0, maxShowItems: 4, scroll:true,
						select: function(event, ui) {			
							this.value = (ui.item ? ui.item[1]:'');
							$(this).parents("tr").find('input[name ="siteID"]').val(ui.item[2]);
							return false;
							
						}
					}).autocomplete("instance")._renderItem = function(ul, item) {
			            return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                    item[1] + "</span><br><span class='desc'>" +
		                    item[2] +"</span></div>")
		                .appendTo(ul);
		        	};
				$('#dcproblemTab > tbody > tr').eq(rowCount-1).find('input[name ="siteName"]').focus(function(){
					if (this.value == ""){
						$(this).autocomplete("search");
			        }
				}); 
				
				 $('#dcproblemTab > tbody > tr').eq(rowCount-1).find('input[name ="siteID"]').autocomplete({
		    	    source: function(request, response, event, ui) {
		    	    	//console.log ("dd"+i);
		    	    		var ctx =getContextPath(); 
		    	    	
		    	    	 var RowIndex = document.getElementById('RowIndex').value;
		    	        SnArray = getAllSitesID();
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
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error AUTO");
			                 }
			             });
			        }, minLength:0, maxShowItems: 4, scroll:true,
					select: function(event, ui) {			
						this.value = (ui.item ? ui.item[2]:'');
						$(this).parents("tr").find('input[name ="siteName"]').val(ui.item[1]);
						return false;
						
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                    item[2] + "</span><br><span class='desc'>" +
	                    item[1] +"</span></div>")
	                .appendTo(ul);
	        	};
			$('#dcproblemTab > tbody > tr').eq(rowCount-1).find('input[name ="siteID"]').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			});
				// Ending for item autocomplete in Boq table    			
			


 }   
 
 function addrowsActions(stat){
	 console.log("add row clicked");
	 var eyyy = moment().format('L') +" "+ moment().format('LT'); // 04/26/2021 
	 	
	 var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowAction' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopActions(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	 	+"<td name = 'creationDate'><input name='creationDate' type='text' style='width:200px;' value='" +eyyy+ "' class='form-control text-input' readonly/></td>"

	 	+"<td name = 'team'><input name='team' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 	+"<td name = 'employee'><input name='employee' type='text' style='width:200px;' class='form-control text-input'/></td>"

	 	+"<td name = 'action'><input name='action' type='text' style='width:200px;' class='form-control text-input'/></td>"

	 	+"<td name = 'description'><input name='description' type='text' style='width:400px;' class='form-control text-input'/></td>"
	 	+"<td name = 'status'><input name='status' type='text' style='width:200px;' class='form-control text-input'/></td>"
	 	+"<td name = 'actionID'><input name='actionID' type='text' style='width:200px;' class='form-control text-input'  readonly /></td>"
	
	 var myDiv = document.getElementById("actionTab");
	 	    myDiv.scrollTop = myDiv.scrollHeight;
	
		 if (stat == "addRowActions")
         {
      $("#actionTab > tbody").append(markup);
    }
    if(stat == "addRowBelowActions")
    {
      $("#actionTab > tbody tr").eq(rowindxActions).after(markup);
    }
    if(stat == "addRowAboveActions")
    {
       $("#actionTab > tbody tr").eq(rowindxActions).before(markup);
    }
 ////////////////////////////////////////////////////////////////

 }   
 
function addrowsAssign(stat){

 		 var rowCount= $("#AssignTab >tbody tr").length;
     var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowAssign' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopAssign(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
 		+"<td name='assignDate'><input  name='assignDate' type='date' style='width:200px;' class='form-control text-input'></td>"

 	 	+"<td name = 'assignTo'><input name='assignTo' type='text' style='width:200px;' class='form-control text-input'/></td>"

 	 	+"<td name = 'assignBy'><input name='assignBy' type='text' style='width:200px;' class='form-control text-input'/></td>"

 	 	+"<td name = 'requiredAction'><input name='requiredAction' type='text' style='width:200px;' class='form-control text-input'/></td>"
 	 	+"<td name = 'note'><input name='note' type='text' style='width:400px;' class='form-control text-input'/></td>"
 	 	+"<td name = 'createDate'><input name='createDate' type='text' style='width:200px;' class='form-control text-input' readonly/></td>"
 	 	+"<td name = 'lastModifiedDate'><input name='lastModifiedDate' type='text' style='width:200px;' class='form-control text-input' readonly/></td>"
 	 	+"<td name = 'assignId'><input name='assignId' type='text' style='width:200px;' class='form-control text-input' readonly/></td>";

 var myDiv = document.getElementById("AssignTab");
	 	    myDiv.scrollTop = myDiv.scrollHeight;

		 if (stat == "addRowAssign")
         {
      $("#AssignTab > tbody").append(markup);
           		rowCount++;
      
    }
    if(stat == "addRowBelowAssign")
    {
      $("#AssignTab > tbody tr").eq(rowindxAssign).after(markup);
      rowCount++;
 
    }
    if(stat == "addRowAboveAssign")
    {
       $("#AssignTab > tbody tr").eq(rowindxAssign).before(markup);
         rowCount= rowindxAssign+1;
    
    }

	    	//alert("rowCount "+rowCount)


$('#AssignTab > tbody > tr').eq(rowCount-1).find('input[name ="assignTo"]').autocomplete({
		    	    source: function(request, response, event, ui) {
		    	    		var ctx =getContextPath(); 
		    	    	
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
				                 url: ctx+'/GetUserJobTitle',
			                 data: {
									"users" : request.term,
									 
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.UsersList);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error AUTO");
			                 }
			             });
			        }, minLength:0, maxShowItems: 4, scroll:true,
					select: function(event, ui) {			
						this.value = (ui.item ? ui.item[0]:'');
						return false;
						
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                	.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			            item[0] + "</span><br><span class='desc'>" +
		                item[1] + " "+ item[2] + " "+  item[3] +"</span></div>")
		                .appendTo(ul);
	        	};
			$('#AssignTab > tbody > tr').eq(rowCount-1).find('input[name ="assignTo"]').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			});
				// Ending for item autocomplete in Boq table    			
			


			 $('#AssignTab tr td input').on('focus', function() {

				 SetIndexRowAssign(this);
				});


				 $('#AssignTab > tbody > tr').eq(rowCount-1).find('input[name ="assignBy"]').autocomplete({
		    	    source: function(request, response, event, ui) {

		    	    	console.log ("dd");
		    	    		var ctx =getContextPath(); 
		    	    	
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
				                 url: ctx+'/GetUserJobTitle',
			                 data: {
									"users" : request.term,
									 
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.UsersList);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error AUTO");
			                 }
			             });
			        }, minLength:0, maxShowItems: 4, scroll:true,
					select: function(event, ui) {			
						this.value = (ui.item ? ui.item[0]:'');
						return false;
						
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                	.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			            item[0] + "</span><br><span class='desc'>" +
		                item[1] + " "+ item[2] + " "+  item[3] +"</span></div>")
		                .appendTo(ul);
	        	};
			$('#AssignTab > tbody > tr').eq(rowCount-1).find('input[name ="assignBy"]').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			});
				// Ending for item autocomplete in Boq table    			
			


			 $('#AssignTab tr td input').on('focus', function() {

				 SetIndexRowAssign(this);
				});

			
			function SetIndexRowAssign(obj)
			{
				var row_index = $(obj).parent().parent().index();
				$("#RowIndex").val(row_index);
			}
			function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
     
     

 ////////////////////////////////////////////////////////////////

 }   
 
 function addrows(stat){
     console.log("add row clicked");

	    	
	var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowLFP' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopLFP(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"

		+"<td name = 'linkID'><input name='linkID' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name = 'linkName'><input name='linkName' type='text' style='width:200px;' class='form-control text-input'/></td>"

		+"<td name = 'longg'><input name='longg' type='text' style='width:200px;' class='form-control text-input'/></td>"

		+"<td name = 'latitude'><input name='latitude' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name = 'Reason'><input name='Reason' type='text' style='width:200px;' class='form-control text-input'/></td>"
		+"<td name = 'lfpdescription'><input name='lfpdescription' type='text' style='width:400px;' class='form-control text-input'/></td>"
		+"<td name = 'lfpID'><input name='lfpID' type='text' style='width:200px;' class='form-control text-input' readonly/></td>";


var myDiv = document.getElementById("lfpTab");
	 	    myDiv.scrollTop = myDiv.scrollHeight;

		 if (stat == "addRow")
         {
      $("#lfpTab > tbody").append(markup);
    }
    if(stat == "addRowBelow")
    {
      $("#lfpTab > tbody tr").eq(rowindx).after(markup);
    }
    if(stat == "addRowAbove")
    {
       $("#lfpTab > tbody tr").eq(rowindx).before(markup);
    }


      
     
     

////////////autocomplete for BOQ MODEL PARTNUMBER TABLE/////////////////////

 ////////////////////////////////////////////////////////////////

 }   

//delete fct Actions
 function deleteBoqRowDCP() {
	 
	 slctDelDCP = [];
 	console.log("rowindxDCP " +rowindxDCP);
 	 $("#dcproblemTab > tbody").each(function(){
 		 //var AssignID = $(this).parent().parent().children('td[name="AssignID"]').children('input').val();
 		 var DCPID =$("#popupID").val();
 		//alert("DCPID"+DCPID);
 		
 		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).remove();
 		var RowsToDelete =  document.getElementById("RowToDeleteDCP").value;
        var Myresult = "";


        if (RowsToDelete != ""){

        Myresult = RowsToDelete ;
        }
        console.log("Myresult is "+Myresult);
 		 Myresult += DCPID+",";
         document.getElementById("RowToDeleteDCP").value = Myresult;

 	 		});
 	 	
			  
 		// Get Nb of rows after delete
 	var rowNodeCount = $("#dcproblemTab >tbody tr").length;
 	console.log("rowCount in BoQ:" +rowNodeCount);
 	 $("#formStatus").text("Not Saved");
 	 $('.dot').css({"background-color" : "orange"});

 	 if (rowindxDCP == 0 && rowNodeCount ==0) {

 		  $("#DNModalDCP").modal("hide");

 		  }
 	  if(rowindxDCP >= 0 && rowindxDCP < rowNodeCount) {

 			$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
 		 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
 			$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
 			$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
 			$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
 			$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
 			$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
 			$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
 			$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
 			$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
 			$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
 			$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());

 			//Update popup Nb in header 

 			var element = document.getElementById("popupDCP");
 			element.innerHTML = "Item # " +rowindxDCP;  
 			//NodeRowIndex++;
 	 }
 	    // Show previous record 
 		 if (rowindxDCP >= rowNodeCount){
 			rowindxDCP--;

 			$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
 		 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
 			$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
 			$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
 			$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
 			$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
 			$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
 			$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
 			$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
 			$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
 			$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
 			$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());
  
 			//Update popup Nb in header 
 			var element = document.getElementById("popupDCP");
 			element.innerHTML = "Item # " +rowindxDCP; 
 		}
 		
 		console.log("rowNodeIndex: "+rowindxDCP);
 	 }// End delete fct 
 

 
//delete fct Actions
 function deleteBoqRowActions() {
	 
	 slctDelAssign = [];
 	console.log("rowindxActions " +rowindxActions);
 	 $("#actionTab > tbody").each(function(){
 		 //var AssignID = $(this).parent().parent().children('td[name="AssignID"]').children('input').val();
 		 var ActionsID =$("#popupactionID").val();
 	//	alert("ActionsID"+ActionsID);
 		
 		$("#actionTab >tbody").find("tr").eq(rowindxActions).remove();
 		var RowsToDelete =  document.getElementById("RowToDeleteActions").value;
        var Myresult = "";


        if (RowsToDelete != ""){

        Myresult = RowsToDelete ;
        }
        console.log("Myresult is "+Myresult);
 		 Myresult += ActionsID+",";
         document.getElementById("RowToDeleteActions").value = Myresult;

 	 		});
 	 	
			  
 		// Get Nb of rows after delete
 	var rowNodeCount = $("#actionTab >tbody tr").length;
 	console.log("rowCount in BoQ:" +rowNodeCount);
 	 $("#formStatus").text("Not Saved");
 	 $('.dot').css({"background-color" : "orange"});

 	 if (rowindxActions == 0 && rowNodeCount ==0) {

 		  $("#DNModalActions").modal("hide");

 		  }
 	  if(rowindxActions >= 0 && rowindxActions < rowNodeCount) {

 			$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
 		 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
 			$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
 			$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
 			$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
 			$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
 			$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());

 			//Update popup Nb in header 

 			var element = document.getElementById("popupActions");
 			element.innerHTML = "Item # " +rowindxActions;  
 			//NodeRowIndex++;
 	 }
 	    // Show previous record 
 		 if (rowindxActions >= rowNodeCount){
 			rowindxActions--;

 			$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
 		 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
 			$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
 			$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
 			$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
 			$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
 			$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());
  
 			//Update popup Nb in header 
 			var element = document.getElementById("popupActions");
 			element.innerHTML = "Item # " +rowindxActions; 
 		}
 		
 		console.log("rowNodeIndex: "+rowindxActions);
 	 }// End delete fct 
 
 //delete fct Assign
 function deleteBoqRowAssign() {
	 
	 slctDelAssign = [];
 	console.log("rowindxAssign " +rowindxAssign);
 	 $("#AssignTab > tbody").each(function(){
 		 //var AssignID = $(this).parent().parent().children('td[name="AssignID"]').children('input').val();
 		 var AssignID =$("#popupAssignId").val();
 		//alert("AssignID"+AssignID);
 		
 		$("#AssignTab >tbody").find("tr").eq(rowindxAssign).remove();
 		var RowsToDelete =  document.getElementById("RowToDeleteAssign").value;
        var Myresult = "";


        if (RowsToDelete != ""){

        Myresult = RowsToDelete ;
        }
        console.log("Myresult is "+Myresult);
 		 Myresult += AssignID+",";
         document.getElementById("RowToDeleteAssign").value = Myresult;

 	 		});
 	 	
			  
 		// Get Nb of rows after delete
 	var rowNodeCount = $("#AssignTab >tbody tr").length;
 	console.log("rowCount in BoQ:" +rowNodeCount);
 	 $("#formStatus").text("Not Saved");
 	 $('.dot').css({"background-color" : "orange"});

 	 if (rowindxAssign == 0 && rowNodeCount ==0) {

 		  $("#DNModalAssign").modal("hide");

 		  }
 	  if(rowindxAssign >= 0 && rowindxAssign < rowNodeCount) {

 		 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
 			$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
 			$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
 			$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
 			$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
 			$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
 			$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
 			$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());

 			//Update popup Nb in header 

 			var element = document.getElementById("popupAssign");
 			element.innerHTML = "Item # " +rowindxAssign;  
 			//NodeRowIndex++;
 	 }
 	    // Show previous record 
 		 if (rowindxAssign >= rowNodeCount){
 			rowindxAssign--;

 		 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
 			$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
 			$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
 			$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
 			$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
 			$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
 			$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
 			$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());
  
 			//Update popup Nb in header 
 			var element = document.getElementById("popupAssign");
 			element.innerHTML = "Item # " +rowindxAssign; 
 		}
 		
 		console.log("rowNodeIndex: "+rowindxAssign);
 	 }// End delete fct 


////////////////////////////////////////////

 
//Prev fct in popup

 $('#DNModalDCP').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



//Prev fct in popup
 $(function(){
$('#DNModalDCP').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



 $("button[name='previousRowDCP']").on("click", function(){

  	if(rowindxDCP > 0) {
  		console.log("Welcome to previous!");
  		rowindxDCP-- ;

  		var PrevIndex = parseInt(rowindxDCP);
  		console.log("PrevIndex" +PrevIndex);
  		
		$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
	 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
		$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
		$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
		$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
		$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
		$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
		$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
		$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
		$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
		$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
		$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());

	    //Update popup Nb in header
				var element = document.getElementById("popupDCP");
				element.innerHTML = "Item # " +PrevIndex;
      		 }

	   	// Close popup on row 0 (end of prev fct)

      		   else if (rowindxDCP == 0) {
      		    	$("#DNModalDCP").modal("hide");
      	      }

  	});// end prev fct


  //Send input values from popup to boq when any popup input change
  	 $('#popuptype,#popupsiteID,#popupsiteName,#popupnodeID,#popupnName,#popupcabinet,#popupslot,#popupboard,#popupantenna,#popupversion,#popupnote,#popupID').on(' focusout ', function() {


 		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val($('#popuptype').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val($('#popupsiteID').val());
		 	
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val($('#popupsiteName') .val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val($('#popupnodeID').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val($('#popupnName').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val($('#popupcabinet').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val($('#popupslot').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val($('#popupboard').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val($('#popupantenna').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val($('#popupversion').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val($('#popupnote').val());
		$("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val($('#popupID').val());

  		 
  

  	  	 	//SetCalcPopUp();
  	      }); // end fct



  	   	// Close popup function
  	   $("button[name='closePopup']").on("click", function(){
  		  console.log("Welcome to close");
  		  console.log("On close"+rowindxActions);

  		  // Send input values from popup to boq table
		$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
	 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
		$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
		$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
		$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
		$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
		$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());
  
  		    $("#DNModalActions").modal("hide");
  			//SetCalcPopUp();




  	 }); // end close fct

  	    	    

  // Send input values from popup to boq table and close the popup using ESC
  	document.addEventListener('keydown', function(event){
      	if(event.key === "Escape"){
          	
    		$('#popuptype').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="type"]').children('input').val());
    	 	$('#popupsiteID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteID"]').children('input').val());
    		$('#popupsiteName') .val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="siteName"]').children('input').val());
    		$('#popupnodeID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nodeID"]').children('input').val());
    		$('#popupnName').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="nName"]').children('input').val());
    		$('#popupcabinet').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="cabinet"]').children('input').val());
    		$('#popupslot').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="slot"]').children('input').val());
    		$('#popupboard').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="board"]').children('input').val());
    		$('#popupantenna').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="antenna"]').children('input').val());
    		$('#popupversion').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="version"]').children('input').val());
    		$('#popupnote').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="note"]').children('input').val());
    		$('#popupID').val($("#dcproblemTab >tbody").find("tr").eq(rowindxDCP).find('td[name="ID"]').children('input').val());

  	    $("#DNModalDCP").modal("hide");
  		//SetCalcPopUp();

      }
   });// end close fct using esc
   $('.modal-content').resizable({
		handles: "e" ,

		});

		$('.modal-dialog').draggable({
		handle: ".modal-header, .modal-footer"
		});

		$('#DNModalDCP').on('show.bs.modal', function() {
		$(this).find('.modal-body').css({
		'max-height': '100%',
		});
		});

	  	//Reset the popup to original size after resizing
		$('#DNModalDCP').on('hidden.bs.modal', function() {
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
		}); // 
 });
 
 $('#DNModalActions').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



//Prev fct in popup
 $(function(){
$('#DNModalActions').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



 $("button[name='previousRowActions']").on("click", function(){

  	if(rowindxActions > 0) {
  		console.log("Welcome to previous!");
  		rowindxActions-- ;

  		var PrevIndex = parseInt(rowindxActions);
  		console.log("PrevIndex" +PrevIndex);
  		
		$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
	 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
		$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
		$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
		$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
		$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
		$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());

	    //Update popup Nb in header
				var element = document.getElementById("popupAssign");
				element.innerHTML = "Item # " +PrevIndex;
      		 }

	   	// Close popup on row 0 (end of prev fct)

      		   else if (rowindxActions == 0) {
      		    	$("#DNModalActions").modal("hide");
      	      }

  	});// end prev fct


  //Send input values from popup to boq when any popup input change
  	 $('#popupcreationdate,#popupteam,#popupemployee,#popupaction,#popupdescription,#popupstatus,#popupactionID').on(' focusout ', function() {

  		    $("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val($('#popupcreationdate').val());
  	  	 	$("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val($('#popupteam').val());
  	  	 	$("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val($('#popupemployee').val());
  	  	 	$("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val($('#popupaction').val());
  	  	 	$("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val($('#popupdescription').val());
  	  	 	$("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val($('#popupstatus').val());
  	  	 	$("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val($('#popupactionID').val());

  	  	 	//SetCalcPopUp();
  	      }); // end fct



  	   	// Close popup function
  	   $("button[name='closePopup']").on("click", function(){
  		  console.log("Welcome to close");
  		  console.log("On close"+rowindxActions);

  		  // Send input values from popup to boq table
		$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
	 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
		$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
		$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
		$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
		$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
		$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());
  
  		    $("#DNModalActions").modal("hide");
  			//SetCalcPopUp();




  	 }); // end close fct

  	    	    

  // Send input values from popup to boq table and close the popup using ESC
  	document.addEventListener('keydown', function(event){
      	if(event.key === "Escape"){
          	
    		$('#popupcreationdate').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="creationDate"]').children('input').val());
    	 	$('#popupteam').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="team"]').children('input').val());
    		$('#popupemployee') .val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="employee"]').children('input').val());
    		$('#popupaction').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="action"]').children('input').val());
    		$('#popupdescription').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="description"]').children('input').val());
    		$('#popupstatus').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="status"]').children('input').val());
    		$('#popupactionID').val($("#actionTab >tbody").find("tr").eq(rowindxActions).find('td[name="actionID"]').children('input').val());

  	    $("#DNModalActions").modal("hide");
  		//SetCalcPopUp();

      }
   });// end close fct using esc
   $('.modal-content').resizable({
		handles: "e" ,

		});

		$('.modal-dialog').draggable({
		handle: ".modal-header, .modal-footer"
		});

		$('#DNModalActions').on('show.bs.modal', function() {
		$(this).find('.modal-body').css({
		'max-height': '100%',
		});
		});

	  	//Reset the popup to original size after resizing
		$('#DNModalActions').on('hidden.bs.modal', function() {
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
		}); // 
 });
 
 
$('#DNModalAssign').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



//Prev fct in popup
 $(function(){
$('#DNModalAssign').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



 $("button[name='previousRowAssign']").on("click", function(){
 
 val =$("#popupassignDate").val();
     val1 = Date.parse(val);
	
 if (isNaN(val1) == true) {
         
          alert('Assign Date is not valid');
          return false;
        }
        else {
  	if(rowindxAssign > 0) {
  		console.log("Welcome to previous!");
  		rowindxAssign-- ;

  		var PrevIndex = parseInt(rowindxAssign);
  		console.log("PrevIndex" +PrevIndex);
  		
	 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
		$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
		$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
		$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
		$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
		$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());

	    //Update popup Nb in header
				var element = document.getElementById("popupAssign");
				element.innerHTML = "Item # " +PrevIndex;
      		 }

	   	// Close popup on row 0 (end of prev fct)

      		   else if (rowindxAssign == 0) {
      		    	$("#DNModalAssign").modal("hide");
      	      }
}
  	});// end prev fct


  //Send input values from popup to boq when any popup input change
  	 $('#popupassignDate,#popupassignTo,#popupassignBy,#popupAction,#popupNote,#popupCreateDate,#popupLastModifiedDate,#popupAssignId').on(' focusout ', function() {


  	  		 
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val($('#popupassignDate').val());
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val($('#popupassignTo').val());
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val($('#popupassignBy').val());
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val($('#popupAction').val());
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val($('#popupNote').val());
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val($('#popupCreateDate').val());
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val($('#popupLastModifiedDate').val());
  	  	 	$("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val($('#popupAssignId').val());
  	  		
  	  	 
 	  		//SetCalcPopUp();
  	      }); // end fct



  	   	// Close popup function
  	   $("button[name='closePopup']").on("click", function(){
  		  console.log("Welcome to close");
  		  console.log("On close"+rowindxAssign);

  		  // Send input values from popup to boq table
  	 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
		$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
		$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
		$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
		$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
		$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());
  
  		    $("#DNModalAssign").modal("hide");
  			//SetCalcPopUp();




  	 }); // end close fct

  	    	    

  // Send input values from popup to boq table and close the popup using ESC
  	document.addEventListener('keydown', function(event){
      	if(event.key === "Escape"){
          	
     	 	$('#popupassignDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignDate"]').children('input').val());
    		$('#popupassignTo') .val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignTo"]').children('input').val());
    		$('#popupassignBy').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignBy"]').children('input').val());
    		$('#popupAction').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="requiredAction"]').children('input').val());
    		$('#popupNote').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="note"]').children('input').val());
    		$('#popupCreateDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="createDate"]').children('input').val());
    		$('#popupLastModifiedDate').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="lastModifiedDate"]').children('input').val());
    		$('#popupAssignId').val($("#AssignTab >tbody").find("tr").eq(rowindxAssign).find('td[name="assignId"]').children('input').val());

  	    $("#DNModalAssign").modal("hide");
  		//SetCalcPopUp();

      }
   });// end close fct using esc
   $('.modal-content').resizable({
		handles: "e" ,

		});

		$('.modal-dialog').draggable({
		handle: ".modal-header, .modal-footer"
		});

		$('#DNModalAssign').on('show.bs.modal', function() {
		$(this).find('.modal-body').css({
		'max-height': '100%',
		});
		});

	  	//Reset the popup to original size after resizing
		$('#DNModalAssign').on('hidden.bs.modal', function() {
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
		}); // 
 });
var slctDelAssign = [];

 
//delete function of Devices Caused Problem Tab
 function deleterowsAssign(){
	 slctDelAssign = [];
					  
		 $("#AssignTab > tbody").find('input[name="chekbox_rowAssign"]').each(function(){
		
				if($(this).is(":checked")){
			 
					 var ID = $(this).parent().parent().children('td[name="assignId"]').children('input').val();
					 if(ID != null)
						 slctDelAssign.push(ID);
					 $(this).parents("tr").remove();
            } 
	  
        });
		//	alert("slctDelAssign"+slctDelAssign);
			 
    	if (slctDelAssign.length == 0) {
           	alert(' Select Row(s) to Delete');
			
         	return false;
         }	            
       }

 var slctDelDCP = [];
 
 function deleterowsDCP(){
		slctDelDCP = [];
					  
		 $("#dcproblemTab > tbody").find('input[name="chekbox_rowDCP"]').each(function(){
		
				if($(this).is(":checked")){
			 
					 var ID = $(this).parent().parent().children('td[name="ID"]').children('input').val();
						 if(ID != null)
						 slctDelDCP.push(ID);
					 $(this).parents("tr").remove();
				} 
   
      		  });
		
    	if (slctDelDCP.length == 0) {
           	alert(' Select Row(s) to Delete');
         	return false;
         }	            
       }
 //end of //delete function of Devices Caused Problem Tab
var slctDelLFP = [];

 //delete function of Link Failure Places Tab
 function deleterowsLFP(){
	 slctDelLFP = [];
					  
	 $("#lfpTab > tbody").find('input[name="chekbox_rowLFP"]').each(function(){
		
				if($(this).is(":checked")){
			 
					 var lfpID = $(this).parent().parent().children('td[name="lfpID"]').children('input').val();
					 if(lfpID != null)
					 	//	alert("lfpid"+lfpID);
					 
						 slctDelLFP.push(lfpID);
						
					 $(this).parents("tr").remove();
         } 
     });

	 if (slctDelLFP.length == 0) {
        	alert(' Select Row(s) to Delete');
			
      	return false;
      }	            
    }
 //end of //delete function of Link Failure Places Tab

 var slctDelActions = [];
 
//delete function of Actions Tab 
 function deleterows(){
	  
		slctDelActions = [];
		var dictObj = {};
		 $("#actionTab > tbody").find('input[name="chekbox_rowAction"]').each(function(){
				if($(this).is(":checked")){
					 var actionID = $(this).parent().parent().children('td[name="actionID"]').children('input').val();
					 if(actionID != null)
						 slctDelActions.push(actionID);
					 $(this).parents("tr").remove();
          } 
			
				  
      	});
		 //end of //delete function of Actions Tab

		 if (slctDelActions.length == 0) {
	        	alert(' Select Row(s) to Delete');
				
	      	return false;
	      }	                    
       }
//END of //delete function of Actions Tab 


 // Delete row fctLFp
 function deleteBoqRow() {
		 
	 slctDelLFP = [];
 	console.log("RowIndx " +rowindx);
 	 $("#lfpTab > tbody").each(function(){
 		 //var lfpID = $(this).parent().parent().children('td[name="lfpID"]').children('input').val();
 		 var lfpID =$("#popuplfpID").val();
 		//alert("lfpid"+lfpID);
 		
 		$("#lfpTab >tbody").find("tr").eq(rowindx).remove();
 		var RowsToDelete =  document.getElementById("RowToDeleteLFP").value;
        var Myresult = "";


        if (RowsToDelete != ""){

        Myresult = RowsToDelete ;
        }
        console.log("Myresult is "+Myresult);
 		 Myresult += lfpID+",";
         document.getElementById("RowToDeleteLFP").value = Myresult;
 		
					
   
});
 	 	
			  
 		// Get Nb of rows after delete
 	var rowNodeCount = $("#lfpTab >tbody tr").length;
 	console.log("rowCount in BoQ:" +rowNodeCount);
 	 $("#formStatus").text("Not Saved");
 	 $('.dot').css({"background-color" : "orange"});

 	 if (rowindx == 0 && rowNodeCount ==0) {

 		  $("#DNModalLFP").modal("hide");

 		  }
 	  if(rowindx >= 0 && rowindx < rowNodeCount) {
 		  //NodeRowIndex++;
 		  
	$('#popuplfpID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpID"]').children('input').val());
	$('#popuplinkID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkID"]').children('input').val());
	$('#popuplinkName') .val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkName"]').children('input').val());
	$('#popuplongg').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="longg"]').children('input').val());
	$('#popuplatitude').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="latitude"]').children('input').val());
	$('#popupReason').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="Reason"]').children('input').val());
	$('#popuplfpdescription').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpdescription"]').children('input').val());
 		//Update popup Nb in header 
 			var element = document.getElementById("popupLFP");
 			element.innerHTML = "Item # " +rowindx;  
 			//NodeRowIndex++;
 	 }
 	    // Show previous record 
 		 if (rowindx >= rowNodeCount){
 			rowindx--;


 			$('#popuplfpID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpID"]').children('input').val());
 			$('#popuplinkID').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkID"]').children('input').val());
 			$('#popuplinkName') .val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="linkName"]').children('input').val());
 			$('#popuplongg').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="longg"]').children('input').val());
 			$('#popuplatitude').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="latitude"]').children('input').val());
 			$('#popupReason').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="Reason"]').children('input').val());
 			$('#popuplfpdescription').val($("#lfpTab >tbody").find("tr").eq(rowindx).find('td[name="lfpdescription"]').children('input').val());
  
 			//Update popup Nb in header 
 			var element = document.getElementById("popupLFP");
 			element.innerHTML = "Item # " +rowindx; 
 		}
 		
 		console.log("rowNodeIndex: "+rowindx);
 	 }// End delete fct 


			