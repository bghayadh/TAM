
//PopupFinance
var rowindxarea=0;

function openPopFinance(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 rowindxarea = (buttonNodeRowIndx[0].rowIndex - 1);
	 console.log("Row index of clicked button:" +rowindxarea);
	 
		$('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
	 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
		$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
		$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
		$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
		$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
		$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
		$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
		$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
		$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
		$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());
		

		
		 $("#DNModalFinance").modal("show");

		 var element = document.getElementById("popupFinance");
        element.innerHTML = "Item # " +rowindxarea;
			// SetCalcPopUp();
}
//end of openPopupFinance

function insertRowBelowFinance(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrowsFinance('addRowBelowFinance');

  rowindxarea++ ;
	var belowIndex = parseInt(rowindxarea);
	console.log("belowIndex:" +belowIndex);

	$('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
	$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
	$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
	$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
	$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
	$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
	$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
	$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
	$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
	$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());

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

	console.log("aboveIndex:" +rowindxarea);
	$('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
	$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
	$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
	$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
	$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
	$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
	$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
	$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
	$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
	$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());
		//Update popup Nb in header
		var element = document.getElementById("popupFinance");
		element.innerHTML = "Item # " +parseInt(rowindxarea);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();

}// End insertRowAbove fct in popup


function nextRowFinance(){

	// Get total nb of rows
	var rowCount = $("#yaratab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);

	rowindxarea++ ;
	var nextIndex = parseInt(rowindxarea);
	console.log("Next Index" +nextIndex);

	if(rowindxarea >= 0 && rowindxarea < rowCount) {
		console.log("Welcome to Next function"+rowindxarea);

		$('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
	 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
		$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
		$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
		$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
		$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
		$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
		$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
		$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
		$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
		$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());

		//Update popup Nb in header

		var element = document.getElementById("popupFinance");
			//alert(nextIndex);
			//alert(rowindxActions);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxActions exceed the row count
	else if (rowindxarea >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
			addrowsFinance('addRowFinance');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

			$('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
		 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
			$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
			$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
			$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
			$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
			$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
			$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
			$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
			$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
			$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());

			//alert(rowindxActions);
		
			//Update popup Nb in header
			var element = document.getElementById("popupFinance");
			element.innerHTML = "Item # " +nextIndex;
			}
}


//Add rows fct in popup

function addrowsFinance(stat){
	 
		 var rowCount= $("#yaratab >tbody tr").length;
		console.log("RowCount in BOQ:" +rowCount);
		console.log("rowindxarea in BOQ:" +rowindxarea);
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
				
		var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopFinance(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td><td name='startDate'>"
	    	+"<input name='startdate' type='date' value='" + firstday + "' style='width:400px;' /></td>"
			+"<td name='endDate'>"
			+"<input name='enddate' type='date' value='" + lastday + "' style='width:400px;' /></td>"
			+"<td name='no2gSites'>"
	        +"<input  type='text' style='width:250px;' value= 0></td>"
			+"<td name='profitAndLoss2g'><input style='width:250px;'type='text' value= 0></td>"
			+"<td name='no2g3gSites'><input  style='width:250px;' type='text' value= 0></td>"
				+"<td name='profitAndLoss2g3g'><input style='width:250px;' type='text'value= 0></td>"
			+"<td name='no2g3g4gSites'><input  style='width:250px;' type='text' value=0></td>"
			+"<td name='profitAndLoss2g3g4g'><input type='text'  style='width:250px;' value= 0></td>"
			+"<td name='totalSites'><input type='text' style='width:250px;' value= 0 ></td>"
			+"<td name='totalSumProfit'><input type='text' style='width:250px;' value= 0 ></td>"
			+"<td name='id' ><input name='id'type='text' style='width:250px;'value= 0 readonly></td></tr>";
		    $("#yaratab > tbody").append(markup);

		    var myDiv = document.getElementById("yaratab");
    	    myDiv.scrollTop = myDiv.scrollHeight;
    	   
		 if (stat == "addRowFinance")
     {
  $("#yaratab > tbody").append(markup);
  		rowCount++;
  
}
if(stat == "addRowBelowFinance")
{
  $("#yaratab > tbody tr").eq(rowindxarea).after(markup);
  rowCount++;
}
if(stat == "addRowAboveFinance")
{
   $("#yaratab > tbody tr").eq(rowindxarea).before(markup);
  rowCount= rowindxarea+1;
}
 $('#yaratab tr td input').change(function() {
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

////////////////////////////////////////////////////////////////
//AUTOCOMPLETE

}


//delete fct Actions
function deleteBoqRowFinance() {
	 
	slctDel = [];
	console.log("rowindxarea " +rowindxarea);
	 $("#yaratab > tbody").each(function(){
		 //var AssignID = $(this).parent().parent().children('td[name="AssignID"]').children('input').val();
		 var FinanceID =$("#popupID").val();
		//alert("ShopID"+ShopID);
		
		$("#yaratab >tbody").find("tr").eq(rowindxarea).remove();
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
	var rowNodeCount = $("#yaratab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowNodeCount);
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});

	 if (rowindxarea == 0 && rowNodeCount ==0) {

		  $("#DNModalFinance").modal("hide");

		  }
	  if(rowindxarea >= 0 && rowindxarea < rowNodeCount) {
		  
		   $('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
		 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
			$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
			$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
			$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
			$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
			$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
			$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
			$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
			$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
			$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());

			//Update popup Nb in header 

			var element = document.getElementById("popupFinance");
			element.innerHTML = "Item # " +rowindxarea;  
			//NodeRowIndex++;
	 }
	    // Show previous record 
		 if (rowindxarea >= rowNodeCount){
			 rowindxarea--;
			    $('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
			 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
				$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
				$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
				$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
				$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
				$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
				$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
				$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
				$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
				$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());
			//Update popup Nb in header 
			var element = document.getElementById("popupFinance");
			element.innerHTML = "Item # " +rowindxarea; 
		}
		
		console.log("rowNodeIndex: "+rowindxarea);
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

	  	if(rowindxarea > 0) {
	  		console.log("Welcome to previous!");
	  		rowindxarea-- ;

	  		var PrevIndex = parseInt(rowindxarea);
	  		console.log("PrevIndex" +PrevIndex);
	  	    $('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
		 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
			$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
			$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
			$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
			$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
			$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
			$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
			$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
			$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
			$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());

		    //Update popup Nb in header
					var element = document.getElementById("popupFinance");
					element.innerHTML = "Item # " +PrevIndex;
	      		 }

		   	// Close popup on row 0 (end of prev fct)

	      		   else if (rowindxarea == 0) {
	      		    	$("#DNModalFinance").modal("hide");
	      	      }

	  	});// end prev fct


	  //Send input values from popup to boq when any popup input change
	  	 $('#popupstartdate,#popupenddate,#popupno2gSites,#popupprofitAndLoss2g,#popupno2g3gSites,#popupprofitAndLoss2g3g,#popupno2g3g4gSites,#popupprofitAndLoss2g3g4g,#popuptotalSites,#popuptotalSumProfit,#popupID').on(' focusout ', function() {

	  		
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val($('#popupstartdate').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val($('#popupenddate').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val($('#popupno2gSites').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val($('#popupprofitAndLoss2g').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val($('#popupno2g3gSites').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val($('#popupprofitAndLoss2g3g').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val($('#popupno2g3g4gSites').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val($('#popupprofitAndLoss2g3g4g').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val($('#popuptotalSites').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val($('#popuptotalSumProfit').val());
	 		$("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val($('#popupID').val());
			
			  

	  	  	 	//SetCalcPopUp();
	  	      }); // end fct



	  	   	// Close popup function
	  	   $("button[name='closePopup']").on("click", function(){
	  		  console.log("Welcome to close");
	  		  console.log("On close"+rowindxarea);

	  		  // Send input values from popup to boq table
			console.log("PrevIndex" +PrevIndex);
			  $('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startdate"]').children('input').val());
			 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
				$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
				$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
				$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
				$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
				$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
				$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
				$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
				$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
				$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());

	  
	  		    $("#DNModalFinance").modal("hide");
	  			//SetCalcPopUp();




	  	 }); // end close fct

	  	    	    

	  // Send input values from popup to boq table and close the popup using ESC
/*	  	document.addEventListener('keydown', function(event){
	      	if(event.key === "Escape"){
	      		console.log("PrevIndex" +PrevIndex);
	      	  $('#popupstartdate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="startDate"]').children('input').val());
			 	$('#popupenddate').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="endDate"]').children('input').val());
				$('#popupno2gSites') .val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2gSites"]').children('input').val());
				$('#popupprofitAndLoss2g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g"]').children('input').val());
				$('#popupno2g3gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3gSites"]').children('input').val());
				$('#popupprofitAndLoss2g3g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g"]').children('input').val());
				$('#popupno2g3g4gSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="no2g3g4gSites"]').children('input').val());
				$('#popupprofitAndLoss2g3g4g').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="profitAndLoss2g3g4g"]').children('input').val());
				$('#popuptotalSites').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSites"]').children('input').val());
				$('#popuptotalSumProfit').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="totalSumProfit"]').children('input').val());
				$('#popupID').val($("#yaratab >tbody").find("tr").eq(rowindxarea).find('td[name="id"]').children('input').val());

				
	  	    $("#DNModalFinance").modal("hide");
	  		//SetCalcPopUp();

	      }
	   });// end close fct using esc */
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
		 $("#yaratab > tbody").each(function(){
		 //var lfpID = $(this).parent().parent().children('td[name="lfpID"]').children('input').val();
		 for (let i = 0; i < rows.length; i++) {
			 slctDel.push(rows[i]);
	    			}
	 			});
			 }
	 