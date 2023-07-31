//function to get selected rows for save or delete
 
 //  openPopupShops
 var rowindxShops=0;
 var itemNumber = 0;
 var ShopLocation=[];
 var shopMarkers=[];
 var showShops=0;
$("#DNModalShops").on("shown.bs.modal", function(){
      $('.modal-backdrop.in').css('opacity', '0.9');
});
 function openPopShops(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 rowindxShops = (buttonNodeRowIndx[0].rowIndex - 1);
	 itemNumber=buttonNodeRowIndx[0].rowIndex;
	 console.log("Row index of clicked button:" +rowindxShops);
	 console.log("Row index of clicked button:" +buttonNodeRowIndx[0].rowIndex);

	 
		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());
		 $("#DNModalShops").modal("show");

		 var element = document.getElementById("popupShops");
         element.innerHTML = "Item # " +itemNumber;
			// SetCalcPopUp();
 }
// end of openPopupShops
 
function insertRowBelowShops(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrowsShops('addRowBelowShops');

  rowindxShops++ ;
  itemNumber++;
	var belowIndex = parseInt(itemNumber);
	console.log("belowIndex:" +belowIndex);

		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());
		//Update popup Nb in header
		var element = document.getElementById("popupShops");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
				initMap();

		//	SetCalcPopUp();

 }

// Insert Row Above fct
 function insertRowAboveShops(){

	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
   addrowsShops('addRowAboveShops');
//----------------------------------------

	console.log("aboveIndex:" +rowindxShops);

		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());
		//Update popup Nb in header
		var element = document.getElementById("popupShops");
		element.innerHTML = "Item # " +parseInt(itemNumber);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
				initMap();

			//SetCalcPopUp();

}// End insertRowAbove fct in popup


function nextRowShops(){

	// Get total nb of rows
	var rowCount = $("#ShopsTab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);
	itemNumber++;
	rowindxShops++ ;
	var nextIndex = parseInt(itemNumber);
	console.log("Next Index" +nextIndex);

	if(rowindxShops >= 0 && rowindxShops < rowCount) {
		console.log("Welcome to Next function"+rowindxShops);


		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());

		//Update popup Nb in header

		var element = document.getElementById("popupShops");
			//alert(nextIndex);
			//alert(rowindxActions);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxActions exceed the row count
	else if (rowindxShops >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
 			addrowsShops('addRowShops');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());

			//alert(rowindxActions);
		
			//Update popup Nb in header
			var element = document.getElementById("popupShops");
			element.innerHTML = "Item # " +nextIndex;
			}
							initMap();
			
}


//Add rows fct in popup

 function addrowsShops(stat){
	 
		 var rowCount= $("#ShopsTab >tbody tr").length;
		console.log("RowCount in BOQ:" +rowCount);
		console.log("rowindxShops in BOQ:" +rowindxShops);
		
		var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='chekbox_rowShops' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopShops(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"

	 	+"<td name='shopsId' hidden><input  name='shopsId'  type='text' style='width:150px;' class='form-control text-input'></td>"

	 	+"<td name = 'Shopsname'><input name='Shopsname' type='text' style='width:100%;' class='form-control text-input'/></td>"
	 	+"<td name = 'Longtitude' hidden><input name='Longtitude' type='text' style='width:100px;' class='form-control text-input'/></td>"
	 	+"<td name = 'Latitude' hidden><input name='Latitude' type='text' style='width:100px;' class='form-control text-input'/></td>"
	 	+"<td name = 'createDate' hidden><input name='createDate' type='text' style='width:100px;' class='form-control text-input' readonly/></td>"
	 	+"<td name = 'lastModifiedDate' hidden><input name='lastModifiedDate' type='text' style='width:100px;' class='form-control text-input' readonly/></td>"
	 	+"<td name = 'agentShopsId' hidden><input name='agentShopsId' type='text' style='width:100px;' class='form-control text-input' readonly/></td>"
	 	
	 	var myDiv = document.getElementById("ShopsTab");
 	    myDiv.scrollTop = myDiv.scrollHeight;
 	    
		 if (stat == "addRowShops")
        {
     $("#ShopsTab > tbody").append(markup);
     		rowCount++;
     
   }
   if(stat == "addRowBelowShops")
   {
     $("#ShopsTab > tbody tr").eq(rowindxShops).after(markup);
     rowCount++;
   }
   if(stat == "addRowAboveShops")
   {
      $("#ShopsTab > tbody tr").eq(rowindxShops).before(markup);
     rowCount= itemNumber+1;
   }
////////////////////////////////////////////////////////////////
//AUTOCOMPLETE


	function getAllShopsId()
	{
		SnArray = [];
		var SnValue = "";
		$("input[name=shopsId]").each(function(){
			if($(this).val() == "")
				SnValue = "empty";
			else
				SnValue = $(this).val();
			SnArray.push(SnValue);
		});
		return SnArray;   
	}
	
	//to be deleted
	/*function SetIndexRow(obj)
	{
		var row_index = $(obj).parent().parent().index();
		$("#RowIndex").val(row_index);
	}

	function SetIndexRowLFP(obj)
	{
		var row_index = $(obj).parent().parent().index();
		$("#RowIndexLFP").val(row_index);
	}*/
	function getContextPath() {
		   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}
	
	function getAllShopsName()
	{
		SiArray = [];
		var SiValue = "";
		$("input[name=Shopsname]").each(function(){
			if($(this).val() == "")
				SiValue = "empty";
			else
				SiValue = $(this).val();
			SiArray.push(SiValue);
		});
		return SiArray;   
	}
	
	  $('#ShopsTab > tbody > tr').eq(rowCount-1).find('input[name ="shopsId"]').autocomplete({
	    	    source: function(request, response, event, ui) {
	    	    	console.log ("dd"+i);
	    	    	 var RowIndex = document.getElementById('RowIndex').value;
	    	       // SnArray = getAllSitesName();
    			// var sitename = SnArray[RowIndex];
    				var ctx =getContextPath(); 
    			
//    			 
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                url: ctx+'/getSelectedShop',
		                 data: {
								"Shops" : request.term,
								 "agentID" :  $("#agentID").val(),
								 
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.ListShops);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		        }, minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {			
					this.value = (ui.item ? ui.item[0]:'');
					$(this).parents("tr").find('input[name ="Shopsname"]').val(ui.item[1]);
					$(this).parents("tr").find('input[name ="Latitude"]').val(ui.item[3]);
				    $(this).parents("tr").find('input[name ="Longtitude"]').val(ui.item[2]);
					
					return false;
					
				}
			}).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
               .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                   item[0] + "</span><br><span class='desc'>" +
                   item[1] +"</span></div>")
               .appendTo(ul);
       	};
        $('#ShopsTab > tbody > tr').eq(rowCount-1).find('input[name ="shopsId"]').focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
	        }
		}); // Ending for item autocomplete in Boq table    			


var previousShopId;     
	var ctx =getContextPath();
	$('#ShopsTab > tbody > tr').eq(rowCount-1).find('input[name ="Shopsname"]').autocomplete ({
	   source: function(request, response, event, ui) {
	    	 $.ajax({
	    		 type: "GET",
		      	 contentType: "application/json; charset=utf-8",
		         url: ctx+'/getSelectedShop',
		         data: {
	    		 		"Shops" : request.term,
						"agentID" :  $("#agentID").val(),		 
						 },
		         dataType: "json",
		         success: function (data) {
		                 if (data != null) {
		                         response(data.selectedShop);
		                         console.log(data.selectedShop);
		                     	
		                 }
		         },
		         error: function(result) {
		        	 		alert("Error");
		                 }
		         });
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {	
		        	
		var shopid = ui.item[0];
		var SameShop=0;
		
		$("#ShopsTab").find('tr').each(function(){
			if(ui.item[1]===$(this).find('input[name ="Shopsname"]').val())	{
				SameShop =1;
				alert("this shop Name is already selected in this agent");
			    return false;
			}
		});			
		if(SameShop ===0){
			this.value = (ui.item ? ui.item[1]:'');
			$(this).parents("tr").find('input[name ="shopsId"]').val(ui.item[0]);
			$(this).parents("tr").find('input[name ="Latitude"]').val(ui.item[3]);
			$(this).parents("tr").find('input[name ="Longtitude"]').val(ui.item[2]);
			if(previousShopId != shopid) {
				if(previousShopId != "") {shopMarkers[previousShopId].setMap(null);}
				previousShopId=ui.item[0];
				infowindow = new google.maps.InfoWindow();
				console.log(shopMarkers);	
				const marker = new google.maps.Marker({
					    position: new google.maps.LatLng(ui.item[3], ui.item[2]),
						map: map,		
						ID:shopid,
						data: "<div>Shop Name: " +ui.item[1]+"<br>Longitude: "+ui.item[2]+"<br>Latitude: "+ui.item[3]+"</div>",
				});

				marker.metadata = { id: shopid };
				shopMarekrs=[];
				shopMarkers[shopid] = marker;
				shopMarkers.push(marker);
						        				    
				google.maps.event.addListener(marker, "click", function (e) {
					infowindow.setContent(this.data); 
					infowindow.open(map,this);
				});
						                 		
			}
		}
		return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	    	return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[1] + "</span><br><span class='desc'>" +
            item[0] +"</span></div>")
            .appendTo(ul);
       	};
   $('#ShopsTab > tbody > tr').eq(rowCount-1).find('input[name ="Shopsname"]').focus(function(){
       		
       		previousShopId=$(this).parents("tr").find('input[name ="shopsId"]').val();
			if (this.value == ""){
				$(this).autocomplete("search");
	        }
		}); // Ending for item autocomplete in Boq table 
       	
   $('#ShopsTab > tbody > tr').eq(rowCount-1).find('input[name ="Shopsname"]').change(function(){
       		
       		previousShopId=$(this).parents("tr").find('input[name ="shopsId"]').val();
       		
       		if(this.value == "") {
	    		if(previousShopId != "") {
	    			shopMarkers[previousShopId].setMap(null);
	    		}
	    		$(this).parents("tr").find('input[name ="shopsId"]').val("");
	    		previousShopId="";
	    	}
		}); // Ending for on change autocomplete in Boq table 
	


}


//delete fct Actions
function deleteBoqRowShops() {
	 
	 slctDelShops = [];
	console.log("rowindxShops " +rowindxShops);
	 $("#ShopsTab > tbody").each(function(){
		 //var AssignID = $(this).parent().parent().children('td[name="AssignID"]').children('input').val();
		 var ShopID =$("#popupID").val();
		//alert("ShopID"+ShopID);
		
		$("#ShopsTab >tbody").find("tr").eq(rowindxShops).remove();
		var RowsToDelete =  document.getElementById("RowToDeleteShops").value;
		var shopID = document.getElementById("popupShopsId").value;
       var Myresult = "";

       console.log("RowsToDelete is "+RowsToDelete);
       if (RowsToDelete != ""){
    	   
       Myresult = RowsToDelete ;
       }
       console.log("Myresult is "+Myresult);
		 Myresult += ShopID+",";
        document.getElementById("RowToDeleteShops").value = Myresult;
        shopMarkers[shopID].setMap(null);
	 		});
	 	
			  
		// Get Nb of rows after delete
	var rowNodeCount = $("#ShopsTab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowNodeCount);
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});

	 if (rowindxShops == 0 && rowNodeCount ==0) {

		  $("#DNModalShops").modal("hide");

		  }
	  if(rowindxShops >= 0 && rowindxShops < rowNodeCount) {

		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());

			//Update popup Nb in header 

			var element = document.getElementById("popupShops");
			element.innerHTML = "Item # " +itemNumber;  
			//NodeRowIndex++;
	 }
	    // Show previous record 
		 if (rowindxShops >= rowNodeCount){
			rowindxShops--;
			itemNumber--;
		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());
			//Update popup Nb in header 
			var element = document.getElementById("popupShops");
			element.innerHTML = "Item # " +itemNumber; 
		}
		
		console.log("rowNodeIndex: "+rowindxShops);
	 }// End delete fct 

	//Prev fct in popup

	 $('#DNModalShops').on('input', function() {

		//SetCalcPopUp();

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});



	//Prev fct in popup
	 $(function(){
	$('#DNModalShops').on('input', function() {

		//SetCalcPopUp();

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});



	 $("button[name='previousRowShops']").on("click", function(){

	  	if(rowindxShops > 0) {
	  		console.log("Welcome to previous!");
	  		rowindxShops-- ;
	  		itemNumber--;
	  		var PrevIndex = parseInt(rowindxShops);
	  		console.log("PrevIndex" +PrevIndex);
		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());
		    //Update popup Nb in header
					var element = document.getElementById("popupShops");
					element.innerHTML = "Item # " +itemNumber;
									initMap();
					
	      		 }

		   	// Close popup on row 0 (end of prev fct)

	      		   else if (rowindxShops == 0) {
	      		    	$("#DNModalShops").modal("hide");
	      	      }

	  	});// end prev fct


	  //Send input values from popup to boq when any popup input change
	  	 $('#popupShopsId,#popupShopsName,#popupLogntitude,#popupLatitude,#popupCreationDate,#popupLastModifiedDate,#popupID').on(' focusout ', function() {



	  		
	 		$("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val($('#popupShopsId').val());
			$("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val($('#popupShopsName').val());
			$("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val($('#popupLogntitude').val());
			$("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val($('#popupLatitude').val());
			 	
			$("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val($('#popupCreationDate') .val());
			$("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val($('#popupLastModifiedDate').val());
			$("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val($('#popupID').val());
			  				//initMap();
			  

	  	  	 	//SetCalcPopUp();
	  	      }); // end fct



	  	   	// Close popup function
	  	   $("button[name='closePopup']").on("click", function(){
	  		  console.log("Welcome to close");
	  		  console.log("On close"+rowindxActions);

	  		  // Send input values from popup to boq table
			console.log("PrevIndex" +PrevIndex);
		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());
	  
	  		    $("#DNModalShops").modal("hide");
	  			//SetCalcPopUp();




	  	 }); // end close fct

	  	    	    

	  // Send input values from popup to boq table and close the popup using ESC
	  	document.addEventListener('keydown', function(event){
	      	if(event.key === "Escape"){
		$('#popupShopsId').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="shopsId"]').children('input').val());
	 	$('#popupShopsName').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Shopsname"]').children('input').val());
	 	$('#popupLogntitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Longtitude"]').children('input').val());
	 	$('#popupLatitude').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="Latitude"]').children('input').val());
		$('#popupCreationDate') .val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="createDate"]').children('input').val());
		$('#popupLastModifiedDate').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="lastModifiedDate"]').children('input').val());
		$('#popupID').val($("#ShopsTab >tbody").find("tr").eq(rowindxShops).find('td[name="agentShopsId"]').children('input').val());
				
	  	    $("#DNModalShops").modal("hide");
	  		//SetCalcPopUp();

	      }
	   });// end close fct using esc
	   $('.modal-content').resizable({
			handles: "e" ,

			});

			$('.modal-dialog').draggable({
			handle: ".modal-header, .modal-footer"
			});

			$('#DNModalShops').on('show.bs.modal', function() {
			$(this).find('.modal-body').css({
			'max-height': '100%',
			});
			});

		  	//Reset the popup to original size after resizing
			$('#DNModalShops').on('hidden.bs.modal', function() {
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
	 function PopupDynamicDeleteShops () {
		
		 var RowsToDelete =  document.getElementById("RowToDeleteShops").value;
		 RowsToDelete.slice(0, RowsToDelete.length - 1);
		 
		var rows = RowsToDelete.split(",");
		 $("#ShopsTab > tbody").each(function(){
 		 //var lfpID = $(this).parent().parent().children('td[name="lfpID"]').children('input').val();
 		 for (let i = 0; i < rows.length-1; i++) {
			 slctDelShops.push(rows[i]);
			 
	    			}
	 			});
			 }
	 