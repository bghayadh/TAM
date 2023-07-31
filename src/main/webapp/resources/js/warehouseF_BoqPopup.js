
//PopupFinance
var rowindxFinance=0;

function openPopFinance(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 rowindxFinance = (buttonNodeRowIndx[0].rowIndex - 1);
	 console.log("Row index of clicked button:" +rowindxFinance);
	 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
	 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
	 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
	 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
	
	  
		
	    if(tech_2g == true){
		    
			$('#popuptech_2g').prop('checked', true);}
	    else{		    
			$('#popuptech_2g').prop('checked', false);
			}

	    if(tech_3g == true)
			$('#popuptech_3g').prop('checked', true);
	    else
			$('#popuptech_3g').prop('checked', false);


	    if(tech_4g == true)
			$('#popuptech_4g').prop('checked', true);
	    else
			$('#popuptech_4g').prop('checked', false);

	    if(tech_5g == true)
			$('#popuptech_5g').prop('checked', true);
	    else
			$('#popuptech_5g').prop('checked', false);


	 
		$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
	 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
		$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
	
		$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
		$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
		$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
		$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
		$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
		$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
		$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
		$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
		$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
		$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
		$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
		$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
		$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
		$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
		$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
		$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
		$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
		$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
		$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
		$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
		$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
		$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
		$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
		

		
		 $("#DNModalFinance").modal("show");

		 var element = document.getElementById("popupFinance");
      element.innerHTML = "Item # " +rowindxFinance;
			// SetCalcPopUp();
}
//end of openPopupFinance

function insertRowBelowFinance(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrowsFinance('addRowBelowFinance');

  rowindxFinance++ ;
	var belowIndex = parseInt(rowindxFinance);
	console.log("belowIndex:" +belowIndex);

	$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
	$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
	 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
	 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
	 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
	 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
	
	  
		
	    if(tech_2g == true){
		    
			$('#popuptech_2g').prop('checked', true);}
	    else{		    
			$('#popuptech_2g').prop('checked', false);
			}

	    if(tech_3g == true)
			$('#popuptech_3g').prop('checked', true);
	    else
			$('#popuptech_3g').prop('checked', false);


	    if(tech_4g == true)
			$('#popuptech_4g').prop('checked', true);
	    else
			$('#popuptech_4g').prop('checked', false);

	    if(tech_5g == true)
			$('#popuptech_5g').prop('checked', true);
	    else
			$('#popuptech_5g').prop('checked', false);

	$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
	$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
	$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
	$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
	$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
	$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
	$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
	$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
	$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
	$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
	$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
	$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
	$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
	$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
	$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
	$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
	$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
	$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
	$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
	$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
	$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
	$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
	$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
	



		//Update popup Nb in header
		var element = document.getElementById("popupFinance");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();
 }

//Insert Row Above fct
function insertRowAboveFinance(){

	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
addrowsFinance('addRowAboveFinance');
//----------------------------------------

	console.log("aboveIndex:" +rowindxFinance);

	$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
	$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
	 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
	 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
	 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
	 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
	
	  
		
	    if(tech_2g == true){
		    
			$('#popuptech_2g').prop('checked', true);}
	    else{		    
			$('#popuptech_2g').prop('checked', false);
			}

	    if(tech_3g == true)
			$('#popuptech_3g').prop('checked', true);
	    else
			$('#popuptech_3g').prop('checked', false);


	    if(tech_4g == true)
			$('#popuptech_4g').prop('checked', true);
	    else
			$('#popuptech_4g').prop('checked', false);

	    if(tech_5g == true)
			$('#popuptech_5g').prop('checked', true);
	    else
			$('#popuptech_5g').prop('checked', false);

	$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
	$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
	$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
	$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
	$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
	$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
	$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
	$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
	$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
	$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
	$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
	$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
	$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
	$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
	$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
	$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
	$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
	$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
	$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
	$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
	$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
	$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
	$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
	
	

		var element = document.getElementById("popupFinance");
		element.innerHTML = "Item # " +parseInt(rowindxFinance);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();

}// End insertRowAbove fct in popup


function nextRowFinance(){

	// Get total nb of rows
	var rowCount = $("#bisotab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);

	rowindxFinance++ ;
	var nextIndex = parseInt(rowindxFinance);
	console.log("Next Index" +nextIndex);

	if(rowindxFinance >= 0 && rowindxFinance < rowCount) {
		console.log("Welcome to Next function"+rowindxFinance);
//alert("1");
$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");

 
	
   if(tech_2g == true){
	    
		$('#popuptech_2g').prop('checked', true);}
   else{		    
		$('#popuptech_2g').prop('checked', false);
		}

   if(tech_3g == true)
		$('#popuptech_3g').prop('checked', true);
   else
		$('#popuptech_3g').prop('checked', false);


   if(tech_4g == true)
		$('#popuptech_4g').prop('checked', true);
   else
		$('#popuptech_4g').prop('checked', false);

   if(tech_5g == true)
		$('#popuptech_5g').prop('checked', true);
   else
		$('#popuptech_5g').prop('checked', false);

$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());




		//Update popup Nb in header

		var element = document.getElementById("popupFinance");
			//alert(nextIndex);
			//alert(rowindxActions);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxActions exceed the row count
	else if (rowindxFinance >= rowCount) {
		//alert("2");
		
		console.log("ADD NEW ROW USING NEXT BUTTON");
			addrowsFinance('addRowFinance');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

			$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
		 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
			$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
			 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
			 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
			 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
			 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
			
			  
				
			    if(tech_2g == true){
				    
					$('#popuptech_2g').prop('checked', true);}
			    else{		    
					$('#popuptech_2g').prop('checked', false);
					}

			    if(tech_3g == true)
					$('#popuptech_3g').prop('checked', true);
			    else
					$('#popuptech_3g').prop('checked', false);


			    if(tech_4g == true)
					$('#popuptech_4g').prop('checked', true);
			    else
					$('#popuptech_4g').prop('checked', false);

			    if(tech_5g == true)
					$('#popuptech_5g').prop('checked', true);
			    else
					$('#popuptech_5g').prop('checked', false);

			$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
			$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
			$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
			$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
			$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
			$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
			$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
			$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
			$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
			$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
			$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
			$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
			$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
			$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
			$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
			$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
			$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
			$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
			$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
			$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
			$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
			
		
			

			//alert(rowindxActions);
		
			//Update popup Nb in header
			var element = document.getElementById("popupFinance");
			element.innerHTML = "Item # " +nextIndex;
			}
}


//Add rows fct in popup

function addrowsFinance(stat){
	 
		 var rowCount= $("#bisotab >tbody tr").length;
		console.log("RowCount in BOQ:" +rowCount);
		console.log("rowindxFinance in BOQ:" +rowindxFinance);
  var now = new Date();
       var firstday  = (new Date().getFullYear(), new Date().getMonth(),1, 0);		
      //  var lastday = new Date(now.getFullYear(), now.getMonth(), 1);
				var d = new Date(now.getFullYear(), now.getMonth(), 0);
				var mm = ("0" + (d.getMonth() + 1)) .slice(-2);
				var dd = ("0" + d.getDate()).slice(-2);
				var yy = d.getFullYear();
				
				var lastday = yy + '-' + mm + '-' + dd;
				
				var d1 =   new Date(now.getFullYear(), now.getMonth() - 1, 1);
				var mm1 = ("0" + (d1.getMonth() + 1)) .slice(-2);
				var dd1 = ("0" + d1.getDate()).slice(-2);
				var yy1 = d1.getFullYear();
				var firstday = yy1 + '-' + mm1 + '-' + dd1;

				var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopFinance(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
					
							+"<td name='startDate'><input type='date' style='width:200px;'  value='" + firstday + "' class='form-control text-input'></td>"
							+"<td name='endDate'><input type='date' style='width:200px;' value='" + lastday + "' class='form-control text-input'></td>"
							+"<td name='population'><input type='text' value=0 class='form-control text-input'/></td>"
							+"<td name='technologies'><div style='width:180px'><input style='margin-top:10px;' type='checkbox' name='tech_2g' /> 2G <input style='margin-top:10px;' type='checkbox' name='tech_3g'  /> 3G <input style='margin-top:10px;' type='checkbox' name='tech_4g'  /> 4G <input style='margin-top:10px;' type='checkbox' name='tech_5g'  /> 5G</div></td>"
							+"<td name='2gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='3gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='4gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='5gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='Availability2G'><input type='text' value=0  class='form-control text-input' /></td>"
							+"<td name='Availability3G'><input type='text' value=0  class='form-control text-input' /></td>"
							+"<td name='Availability4G'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='Availability5G'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='grossADS'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='countOfSSO'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='customerBase'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='data'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='voice'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='smsRevenue'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='grossRevenue'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='dataTraffic'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='totalSMSTrafficOG'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='totalVoiceTrafficOG'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='totalVoiceTrafficIC'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='totalSMSTrafficIC'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='totalOpexMon'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='ProfitAndLoss'><input type='text' value=0 class='form-control text-input' /></td>"
							+"<td name='ProfitLossID'><input type='text' value=0 readonly class='form-control text-input' /></td></tr>";
							
							
		
		    var myDiv = document.getElementById("bisotab");
    	    myDiv.scrollTop = myDiv.scrollHeight;
		 if (stat == "addRowFinance")
     {
		    // alert("3");
  $("#bisotab  > tbody").append(markup);
	rowCount++;
  
}
if(stat == "addRowBelowFinance")
{		   //  alert("4");

  $("#bisotab > tbody tr").eq(rowindxFinance).after(markup);
  rowCount++;
}
if(stat == "addRowAboveFinance")
{
   $("#bisotab > tbody tr").eq(rowindxFinance).before(markup);
  rowCount= rowindxFinance+1;
}
 $('#bisotab tr td input').change(function() {
				console.log("Passes here");
				var cell = $(this).val();
				var column_name = $(this).parent().attr('name');
				
	            if ((column_name == 'no2gSites') ) {
		            console.log("Passes here");
	                  // validate Qty
						 if (($. isNumeric(cell))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}
				}
	            if ((column_name == 'profitAndLoss2g') ) {
	                  // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}
						 
				}
	            if ((column_name == 'no2g3g4gSites') ) {
	                  // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}

				}
	            if ((column_name == 'no2g3gSites') ) {
	                  // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}

				}
	        
	            if ((column_name == 'profitAndLoss2g3g') ) {
	                  // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}
				}
	            if ((column_name == 'profitAndLoss2g3g4g') ) {
	                  // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}

				}
	            if ((column_name == 'totalSites') ) {
	                  // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}
				}

	            if ((column_name == 'totalSumProfit') ) {
	                  // validate Qty
						 if (($. isNumeric(cell ))== false) {
						 alert('Qty is  not Numeric');
						 this.focus();
						 return false;}
						 
				}

			});
}


//delete fct Actions
function deleteBoqRowFinance() {
	 
	slctDel = [];
	console.log("rowindxFinance " +rowindxFinance);
	 $("#bisotab > tbody").each(function(){
		 //var AssignID = $(this).parent().parent().children('td[name="AssignID"]').children('input').val();
		 var FinanceID =$("#popupProfitLossID").val();
		//alert("ShopID"+ShopID);
		
		$("#bisotab >tbody").find("tr").eq(rowindxFinance).remove();
		var RowsToDelete =  document.getElementById("RowToDeleteFinance").value;
   var Myresult = "";


   if (RowsToDelete != ""){

   Myresult = RowsToDelete ;
   }
   console.log("Myresult is "+Myresult);
		 Myresult += FinanceID+",";
    document.getElementById("RowToDeleteFinance").value = Myresult;

	 		});
	 	
			  
		// Get Nb of rows after delete
	var rowNodeCount = $("#bisotab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowNodeCount);
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});

	 if (rowindxFinance == 0 && rowNodeCount ==0) {

		  $("#DNModalFinance").modal("hide");

		  }
	  if(rowindxFinance >= 0 && rowindxFinance < rowNodeCount) {
		  
			$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
		 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
			$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
			 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
			 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
			 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
			 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
			
			  
				
			    if(tech_2g == true){
				    
					$('#popuptech_2g').prop('checked', true);}
			    else{		    
					$('#popuptech_2g').prop('checked', false);
					}

			    if(tech_3g == true)
					$('#popuptech_3g').prop('checked', true);
			    else
					$('#popuptech_3g').prop('checked', false);


			    if(tech_4g == true)
					$('#popuptech_4g').prop('checked', true);
			    else
					$('#popuptech_4g').prop('checked', false);

			    if(tech_5g == true)
					$('#popuptech_5g').prop('checked', true);
			    else
					$('#popuptech_5g').prop('checked', false);

			$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
			$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
			$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
			$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
			$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
			$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
			$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
			$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
			$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
			$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
			$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
			$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
			$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
			$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
			$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
			$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
			$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
			$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
			$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
			$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
			$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
			

			//Update popup Nb in header 

			var element = document.getElementById("popupFinance");
			element.innerHTML = "Item # " +rowindxFinance;  
			//NodeRowIndex++;
	 }
	    // Show previous record 
		 if (rowindxFinance >= rowNodeCount){
			 rowindxFinance--;
				$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
			 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
				$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
				 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
				 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
				 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
				 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
				
				  
					
				    if(tech_2g == true){
					    
						$('#popuptech_2g').prop('checked', true);}
				    else{		    
						$('#popuptech_2g').prop('checked', false);
						}

				    if(tech_3g == true)
						$('#popuptech_3g').prop('checked', true);
				    else
						$('#popuptech_3g').prop('checked', false);


				    if(tech_4g == true)
						$('#popuptech_4g').prop('checked', true);
				    else
						$('#popuptech_4g').prop('checked', false);

				    if(tech_5g == true)
						$('#popuptech_5g').prop('checked', true);
				    else
						$('#popuptech_5g').prop('checked', false);

				$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
				$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
				$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
				$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
				$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
				$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
				$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
				$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
				$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
				$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
				$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
				$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
				$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
				$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
				$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
				$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
				$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
				$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
				$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
				$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
				$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
				$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
				$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
				
			//Update popup Nb in header 
			var element = document.getElementById("popupFinance");
			element.innerHTML = "Item # " +rowindxFinance; 
		}
		
		console.log("rowNodeIndex: "+rowindxFinance);
	 }// End delete fct 

	//Prev fct in popup

	 $('#DNModalFinance').on('input', function() {

		//SetCalcPopUp();

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});



	//Prev fct in popup
	 $(function(){
	$('#DNModalFinance').on('input', function() {

		//SetCalcPopUp();

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});



	 $("button[name='previousRowFinance']").on("click", function(){

	  	if(rowindxFinance > 0) {
	  		console.log("Welcome to previous!");
	  		rowindxFinance-- ;

	  		var PrevIndex = parseInt(rowindxFinance);
	  		console.log("PrevIndex" +PrevIndex);
	  		$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
		 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
			$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
			 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
			 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
			 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
			 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
			
			  
				
			    if(tech_2g == true){
				    
					$('#popuptech_2g').prop('checked', true);}
			    else{		    
					$('#popuptech_2g').prop('checked', false);
					}

			    if(tech_3g == true)
					$('#popuptech_3g').prop('checked', true);
			    else
					$('#popuptech_3g').prop('checked', false);


			    if(tech_4g == true)
					$('#popuptech_4g').prop('checked', true);
			    else
					$('#popuptech_4g').prop('checked', false);

			    if(tech_5g == true)
					$('#popuptech_5g').prop('checked', true);
			    else
					$('#popuptech_5g').prop('checked', false);

			$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
			$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
			$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
			$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
			$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
			$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
			$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
			$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
			$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
			$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
			$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
			$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
			$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
			$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
			$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
			$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
			$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
			$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
			$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
			$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
			$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
			
		    //Update popup Nb in header
					var element = document.getElementById("popupFinance");
					element.innerHTML = "Item # " +PrevIndex;
	      		 }

		   	// Close popup on row 0 (end of prev fct)

	      		   else if (rowindxFinance == 0) {
	      		    	$("#DNModalFinance").modal("hide");
	      	      }

	  	});// end prev fct
	  	

	  //Send input values from popup to boq when any popup input change
	  	 $('#popupstartdate,#popupenddate,#popuppopulation,#popuptech_2g,#popuptech_3g,#popuptech_4g,#popuptech_5g,#popup2gUtilization,#popup3gUtilization,#popup4gUtilization,#popup5gUtilization,#popupAvailability2G,#popupAvailability3G,#popupAvailability4G,#popupAvailability5G,#popupgrossADS,#popupcountOfSSO,#popupcustomerBase,#popupdata,#popupvoice,#popupsmsRevenue,#popupgrossRevenue,#popupdataTraffic,#popuptotalSMSTrafficOG,#popuptotalVoiceTrafficOG,#popuptotalVoiceTrafficIC,#popuptotalSMSTrafficIC,#popuptotalOpexMon,#popupProfitAndLoss,#popupProfitLossID').on(' focusout ', function() {


			 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]');
			 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]');
			 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]');
			 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]');
			

	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val($('#popupstartdate').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val($('#popupenddate').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val($('#popuppopulation').val());
	 	
	if ($('#popuptech_2g').is(':checked')) {
		tech_2g.prop('checked',true);
	}
	else{
	
		tech_2g.prop('checked', false);
	}
	
	if ($('#popuptech_3g').is(':checked')) {
		tech_3g.prop('checked',true);
	}
	else{
		tech_3g.prop('checked', false);
	}
	if ($('#popuptech_4g').is(':checked')) {
		tech_4g.prop('checked',true);
	}
	else{
	
		tech_4g.prop('checked', false);
	}
	if ($('#popuptech_5g').is(':checked')) {
		tech_5g.prop('checked',true);
	}
	else{
	
		tech_5g.prop('checked', false);
	}
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val($('#popup2gUtilization').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val($('#popup3gUtilization').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val($('#popup4gUtilization').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val($('#popup5gUtilization').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val($('#popupAvailability2G').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val($('#popupAvailability3G').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val($('#popupAvailability4G').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val($('#popupAvailability5G').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val($('#popupgrossADS').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val($('#popupcountOfSSO').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val($('#popupcustomerBase').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val($('#popupdata').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val($('#popupvoice').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val($('#popupsmsRevenue').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val($('#popupgrossRevenue').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val($('#popupdataTraffic').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val($('#popuptotalSMSTrafficOG').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val($('#popuptotalVoiceTrafficOG').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val($('#popuptotalVoiceTrafficIC').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val($('#popuptotalSMSTrafficIC').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val($('#popuptotalOpexMon').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val($('#popupProfitAndLoss').val());
	 		$("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val($('#popupProfitLossID').val());
			
			  

	  	  	 	//SetCalcPopUp();
	  	      }); // end fct



	  	   	// Close popup function
	  	   $("button[name='closePopup']").on("click", function(){
	  		  console.log("Welcome to close");
	  		  console.log("On close"+rowindxFinance);

	  		  // Send input values from popup to boq table
			console.log("PrevIndex" +PrevIndex);
			$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
		 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
			$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
			 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
			 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
			 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
			 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
			
			  
				
			    if(tech_2g == true){
				    
					$('#popuptech_2g').prop('checked', true);}
			    else{		    
					$('#popuptech_2g').prop('checked', false);
					}

			    if(tech_3g == true)
					$('#popuptech_3g').prop('checked', true);
			    else
					$('#popuptech_3g').prop('checked', false);


			    if(tech_4g == true)
					$('#popuptech_4g').prop('checked', true);
			    else
					$('#popuptech_4g').prop('checked', false);

			    if(tech_5g == true)
					$('#popuptech_5g').prop('checked', true);
			    else
					$('#popuptech_5g').prop('checked', false);

			$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
			$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
			$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
			$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
			$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
			$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
			$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
			$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
			$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
			$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
			$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
			$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
			$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
			$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
			$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
			$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
			$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
			$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
			$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
			$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
			$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
			$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
			
	
	  		    $("#DNModalFinance").modal("hide");
	  			//SetCalcPopUp();




	  	 }); // end close fct

	  	    	    

	  // Send input values from popup to boq table and close the popup using ESC
	  	document.addEventListener('keydown', function(event){
	      	if(event.key === "Escape"){
	      		console.log("PrevIndex" +PrevIndex);
	      		$('#popupstartdate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="startDate"]').children('input').val());
	    	 	$('#popupenddate').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="endDate"]').children('input').val());
	    		$('#popuppopulation') .val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="population"]').children('input').val());
	    		 var tech_2g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
	    		 var tech_3g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
	    		 var tech_4g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
	    		 var tech_5g = $("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
	    		
	    		  
	    			
	    		    if(tech_2g == true){
	    			    
	    				$('#popuptech_2g').prop('checked', true);}
	    		    else{		    
	    				$('#popuptech_2g').prop('checked', false);
	    				}

	    		    if(tech_3g == true)
	    				$('#popuptech_3g').prop('checked', true);
	    		    else
	    				$('#popuptech_3g').prop('checked', false);


	    		    if(tech_4g == true)
	    				$('#popuptech_4g').prop('checked', true);
	    		    else
	    				$('#popuptech_4g').prop('checked', false);

	    		    if(tech_5g == true)
	    				$('#popuptech_5g').prop('checked', true);
	    		    else
	    				$('#popuptech_5g').prop('checked', false);

	    		$('#popup2gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="2gUtilization"]').children('input').val());
	    		$('#popup3gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="3gUtilization"]').children('input').val());
	    		$('#popup4gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="4gUtilization"]').children('input').val());
	    		$('#popup5gUtilization').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="5gUtilization"]').children('input').val());
	    		$('#popupAvailability2G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability2G"]').children('input').val());
	    		$('#popupAvailability3G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability3G"]').children('input').val());
	    		$('#popupAvailability4G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability4G"]').children('input').val());
	    		$('#popupAvailability5G').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="Availability5G"]').children('input').val());
	    		$('#popupgrossADS').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossADS"]').children('input').val());
	    		$('#popupcountOfSSO').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="countOfSSO"]').children('input').val());
	    		$('#popupcustomerBase').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="customerBase"]').children('input').val());
	    		$('#popupdata').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="data"]').children('input').val());
	    		$('#popupvoice').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="voice"]').children('input').val());
	    		$('#popupsmsRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="smsRevenue"]').children('input').val());
	    		$('#popupgrossRevenue').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="grossRevenue"]').children('input').val());
	    		$('#popupdataTraffic').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="dataTraffic"]').children('input').val());
	    		$('#popuptotalSMSTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficOG"]').children('input').val());
	    		$('#popuptotalVoiceTrafficOG').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficOG"]').children('input').val());
	    		$('#popuptotalVoiceTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalVoiceTrafficIC"]').children('input').val());
	    		$('#popuptotalSMSTrafficIC').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalSMSTrafficIC"]').children('input').val());
	    		$('#popuptotalOpexMon').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="totalOpexMon"]').children('input').val());
	    		$('#popupProfitAndLoss').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitAndLoss"]').children('input').val());
	    		$('#popupProfitLossID').val($("#bisotab >tbody").find("tr").eq(rowindxFinance).find('td[name="ProfitLossID"]').children('input').val());
	    		
				
	  	    $("#DNModalFinance").modal("hide");
	  		//SetCalcPopUp();

	      }
	   });// end close fct using esc
	   $('.modal-content').resizable({
			handles: "e" ,

			});

			$('.modal-dialog').draggable({
			handle: ".modal-header, .modal-footer"
			});

			$('#DNModalFinance').on('show.bs.modal', function() {
			$(this).find('.modal-body').css({
			'max-height': '100%',
			});
			});

		  	//Reset the popup to original size after resizing
			$('#DNModalFinance').on('hidden.bs.modal', function() {
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

	 //dynamic delete fct for Shops
	 function PopupDynamicDeleteFinance () {
		 
		 var RowsToDelete =  document.getElementById("RowToDeleteFinance").value;
		 RowsToDelete.slice(0, -1);
		var rows = RowsToDelete.split(",");
		 $("#bisotab > tbody").each(function(){
		 //var lfpID = $(this).parent().parent().children('td[name="lfpID"]').children('input').val();
		 for (let i = 0; i < rows.length; i++) {
			 slctDel.push(rows[i]);
	    			}
	 			});
			 }