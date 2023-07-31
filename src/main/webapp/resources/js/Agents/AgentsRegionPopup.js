 var rowindx =0;

 var rowindxRegion=0;
var itemNumber;
var regionPolygons=[];
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function openPopRegion(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 itemNumber=buttonNodeRowIndx[0].rowIndex;
	 rowindxRegion = (buttonNodeRowIndx[0].rowIndex - 1);
	 
	// console.log("Row index of clicked button:" +rowindxRegion);
	   //Send input values from Boq table  to popup

		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

		         tempPopupregionId=$('#popupregionId').val();
		//console.log("tempPopupregionId  "+tempPopupregionId);
		 $("#DNModalRegion").modal("show");

		 var element = document.getElementById("popupRegion");
         element.innerHTML = "Item # " +itemNumber;
        // console.log(rowindxRegion);
         
			// SetCalcPopUp();
 }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//openPopRegion
// Next Fct in popup
  
   function nextRowRegion(){

	// Get total nb of rows
	var rowCount = $("#RegionTab >tbody tr").length;
//	console.log("rowCount in BoQ:" +rowCount);

	rowindxRegion++ ;
	itemNumber++;
	var nextIndex = parseInt(itemNumber);
	//console.log("Next Index" +nextIndex);

	if(rowindxRegion >= 0 && rowindxRegion < rowCount) {
	//	console.log("Welcome to Next function"+rowindxRegion);

		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

         tempPopupregionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();

		//Update popup Nb in header

		var element = document.getElementById("popupRegion");
			//alert(nextIndex);
			//alert(rowindxRegion);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxRegion exceed the row count
	else if (rowindxRegion >= rowCount) {
		//console.log("ADD NEW ROW USING NEXT BUTTON");
    addrowsRegion('addRowRegion');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

		
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());
         tempPopupregionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();


			//alert(rowindxRegion);
		
			//Update popup Nb in header
			var element = document.getElementById("popupRegion");
			element.innerHTML = "Item # " +nextIndex;
			}
 }
  
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
   

/////////////////////////////////////////////

function insertRowBelowRegion(){

	//console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrowsRegion('addRowBelowRegion');

  rowindxRegion++ ;
  itemNumber++;
	var belowIndex = parseInt(itemNumber);
	//console.log("belowIndex:" +belowIndex);

			$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());


		//Update popup Nb in header
		var element = document.getElementById("popupRegion");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();

 }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 // Insert Row Above fct
  function insertRowAboveRegion(){

	//console.log("ADD NEW ROW USING INSERTAbove BUTTON");
    addrowsRegion('addRowAboveRegion');
//----------------------------------------

	//console.log("aboveIndex:" +rowindxRegion);

	
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

	
		//Update popup Nb in header
		var element = document.getElementById("popupRegion");
		element.innerHTML = "Item # " +parseInt(itemNumber);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();

 }// End insertRowAbove fct in popup
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 function deleteBoqRowRegion() {
	 var regionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();
 		
 		//alert(regionId);
	 slctDelRegion = [];
 //	console.log("rowindxRegion " +rowindxRegion);
 	 $("#RegionTab > tbody").each(function(){
 		 //var AssignID = $(this).parent().parent().children('td[name="AssignID"]').children('input').val();
 		 var RegionID =$("#popupagentRegionId").val();
 		//alert("RegionID "+RegionID);
 		
 		$("#RegionTab >tbody").find("tr").eq(rowindxRegion).remove();
 		var RowsToDelete =  document.getElementById("RowToDeleteRegion").value;
        var Myresult = "";


        if (RowsToDelete != ""){

        Myresult = RowsToDelete ;
        }
     //   console.log("Myresult is "+Myresult);
 		 Myresult += RegionID+",";
         document.getElementById("RowToDeleteRegion").value = Myresult;

 	 		});
 	 	
			  
 		// Get Nb of rows after delete
 	var rowNodeCount = $("#RegionTab >tbody tr").length;
 //	console.log("rowCount in BoQ:" +rowNodeCount);
 	 $("#formStatus").text("Not Saved");
 	 $('.dot').css({"background-color" : "orange"});

 	 if (rowindxRegion == 0 && rowNodeCount ==0) {

 		  $("#DNModalRegion").modal("hide");

 		  }
 	  if(rowindxRegion >= 0 && rowindxRegion < rowNodeCount) {

 				
		
			$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

 			//Update popup Nb in header 

 			var element = document.getElementById("popupRegion");
 			element.innerHTML = "Item # " +itemNumber;  
 			//NodeRowIndex++;
 	 }
 	    // Show previous record 
 		 if (rowindxRegion >= rowNodeCount){
 			rowindxRegion--;
 			itemNumber--;

 		
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

 			//Update popup Nb in header 
 			var element = document.getElementById("popupRegion");
 			element.innerHTML = "Item # " +itemNumber; 
 		}
 								
 		for (i = 0;i<Object.keys(regionToDraw).length;i++){
			        			RegionSet.length=0;

			        			RegionCoordinates=regionToDraw[Object.keys(regionToDraw)[i]];
			        		  	 
			        			  	if(Object.keys(regionToDraw)[i] == regionId ){
								regionToDraw[Object.keys(regionToDraw)[i]].length=0;
								delete regionToDraw[regionId];
								regioncheck=1;
				        			  			}
								        	  }	
								        	  		 initMap();           
								        	  
 		
 	//	console.log("rowNodeIndex: "+rowindxRegion);
 	 }// End delete fct 
 
//////////////////////////







  // Next Fct in popup
  
   function nextRowRegion(){

	// Get total nb of rows
	var rowCount = $("#RegionTab >tbody tr").length;
	//console.log("rowCount in BoQ:" +rowCount);

	rowindxRegion++ ;
	itemNumber++;
	var nextIndex = parseInt(itemNumber);
	//console.log("Next Index" +nextIndex);

	if(rowindxRegion >= 0 && rowindxRegion < rowCount) {
		//console.log("Welcome to Next function"+rowindxRegion);
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

         tempPopupregionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();

		//Update popup Nb in header

		var element = document.getElementById("popupRegion");
			//alert(nextIndex);
			//alert(rowindxRegion);
			
			element.innerHTML = "Item # " +itemNumber;

			 }

	// Add new row when rowindxRegion exceed the row count
	else if (rowindxRegion >= rowCount) {
	//	console.log("ADD NEW ROW USING NEXT BUTTON");
    addrowsRegion('addRowRegion');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

		
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

         tempPopupregionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();

			//alert(rowindxRegion);
		
			//Update popup Nb in header
			var element = document.getElementById("popupRegion");
			element.innerHTML = "Item # " +nextIndex;
			}
 }
  
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
   
 
 
 //Add rows fct in popup
 
  function addrowsRegion(stat){
  
  		 var rowCount= $("#RegionTab >tbody tr").length;
  
	   var markup = "<tr><td><input type='checkbox' name='chekbox_rowRegion' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopRegion(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name='regionId' hidden><input  name='regionId'  type='text' style='width:auto;' class='form-control text-input'></td>"

	 	+"<td name = 'Regionname'><input name='Regionname' type='text'  style='width:100%;' class='form-control text-input'/></td>"
	 	+"<td name = 'Regioncode' hidden><input name='Regioncode' type='text'  style='width:150px;' class='form-control text-input'/></td>"
	 	+"<td name = 'agentRegionId' hidden><input name='agentRegionId'  type='text' style='width:150px;' class='form-control text-input' readonly/></td>"
	 	+"<td name = 'RegionLong' hidden><input name='RegionLong'  type='text' style='width:150px;' class='form-control text-input' /></td>"
	 	+"<td name = 'RegionLat' hidden><input name='RegionLat'  type='text' style='width:150px;' class='form-control text-input' /></td></tr>"
	 
				////////////////////
										   	
	 	
	 	
		 if (stat == "addRowRegion")
         {
      $("#RegionTab > tbody").append(markup);
           		rowCount++;
      
    }
    if(stat == "addRowBelowRegion")
    {
      $("#RegionTab > tbody tr").eq(rowindxRegion).after(markup);
           rowCount++;
      
    }
    if(stat == "addRowAboveRegion")
    {
       $("#RegionTab > tbody tr").eq(rowindxRegion).before(markup);
            rowCount= rowindxRegion+1;
       
    } 
 ////////////////////////////////////////////////////////////////
//AUTOCOMPLETE
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));   
	} 
	var ctx = getContextPath();
	//to be deleted
					 /* $('#RegionTab > tbody > tr').eq(rowCount-1).find('input[name ="regionId"]').autocomplete({
				
			    	    source: function(request, response) {
			    	    //	console.log ("dd"+i);
			    	    	 var RowIndex = document.getElementById('RowIndex').value;
			    	       // SnArray = getAllSitesName();
		     			// var sitename = SnArray[RowIndex];
		     			
//		     			 
				             $.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url: ctx+'/GetAllRegion',
				                 data: {
										"regionId" : request.term,
										 
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {
				                         response(data.ListRegion);
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
				        }, minLength:0, maxShowItems: 4, scroll:true,
						select: function(event, ui) {			
							this.value = (ui.item ? ui.item[0]:'');
							$(this).parents("tr").find('input[name ="Regionname"]').val(ui.item[1]);
							$(this).parents("tr").find('input[name ="Regioncode"]').val(ui.item[2]);
										$(this).parents("tr").find('input[name ="RegionLong"]').val(ui.item[3]);
			$(this).parents("tr").find('input[name ="RegionLat"]').val(ui.item[4]);

			 initMap();           
							
							return false;
							
						}
					}).autocomplete("instance")._renderItem = function(ul, item) {
			            return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                    item[0] + "</span><br><span class='desc'>" +
		                    item[1] + "</span><br><span class='desc'>" +
		                    item[2] +"</span></div>")
		                .appendTo(ul);
		        	};
				$('#RegionTab > tbody > tr').eq(rowCount-1).find('input[name ="regionId"]').focus(function(){
					if (this.value == ""){
						$(this).autocomplete("search");
			        }
				});*/
				 // Ending for item autocomplete in Boq table    			
var previousRegionId =null;
 $('#RegionTab > tbody > tr').eq(rowCount-1).find('input[name ="Regionname"]').autocomplete({
	 source:regions,
	 minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {	

		this.value = (ui.item ? ui.item[1]:'');
		$(this).parents("tr").find('input[name ="regionId"]').val(ui.item[0]);
		$(this).parents("tr").find('input[name ="Regioncode"]').val(ui.item[2]);
	
        if(previousRegionId != ui.item[0]) {
        	if(previousRegionId != ""){
        		if(RegionPrevBorders != "N/A" && RegionPrevBorders != null){
            		regionPolygons[previousRegionId].setMap(null);}
        	}
			previousRegionId = ui.item[0];
			Tabora.length=0;
 	    	var splittingCoordinate=[];
 	    	var CoordinatesArray=[];
 	    	if(ui.item[3] != "N/A"){
 	    		RegionPrevBorders = ui.item[3];
 	    		splittingCoordinate=ui.item[3].split(":");
	 	    	for(i=0;i<splittingCoordinate.length;i++) {
	 	    		Tabora.push({
	    				lat:parseFloat(splittingCoordinate[i].split(",")[0]),
	    				lng: parseFloat(splittingCoordinate[i].split(",")[1])
	    			});
	 	    	}
				regionPolygon = new google.maps.Polygon({
	       		  	    path: Tabora,
	       		  	    geodesic: false,
	       		  	    strokeColor: '#00ff00',
	       		  	    strokeOpacity: 1.0,
	       		  	    fillOpacity: 0.01,
	       		  	    id:ui.item[0],
	       		  	    strokeWeight:5,
	       		  	    map: map
	       		  	  });
	
	       		  	regionPolygon.metadata = { id: ui.item[0] };
		    	     	regionPolygons=[];
		    	     	regionPolygons[ui.item[0]] = regionPolygon;
		    	     	regionPolygons.push(regionPolygon);
	             	}else{
	             		RegionPrevBorders = "N/A";
		             	}

	 	    	}
		return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
	 return $("<li class='each'>")
	 .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	 item[1] + "</span><br><span class='desc'>" +
	 item[2] + "</span><br><span class='desc'>" +
	 item[0] +"</span></div>")
	 .appendTo(ul);
	};
	$('#RegionTab > tbody > tr').eq(rowCount-1).find('input[name ="Regionname"]').focus(function(){
		previousRegionId =$(this).parents("tr").find('td[name="regionId"]').val();
		if (this.value == ""){
			$(this).autocomplete("search");
		}
	});		
	$('#RegionTab > tbody > tr').eq(rowCount-1).find('input[name ="Regionname"]').change(function(){
		if(this.value == "") {
    		if(previousRegionId != "") {
    			if(RegionPrevBorders != null && RegionPrevBorders != "N/A"){
        			regionPolygons[previousRegionId].setMap(null);
        			RegionPrevBorders = null;
        		}
    		}
    		$(this).parents("tr").find('input[name ="regionId"]').val("");
    		$(this).parents("tr").find('input[name ="Regioncode"]').val("");
    		previousRegionId="";
    	}else {
    		
    		previousRegionId =$(this).parents("tr").find('td[name="regionId"]').val();

    	}
	});		
				////////  to be deleted  			
 		 /* $('#RegionTab > tbody > tr').eq(rowCount-1).find('input[name ="Regioncode"]').autocomplete({
			    	    source: function(request, response) {
			    	    	//console.log ("dd"+i);
			    	    	// var RowIndex = document.getElementById('RowIndex').value;
			    	       // SnArray = getAllSitesID();
				             $.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url: ctx+'/GetAllRegion',
				                 data: {
										"Regioncode" : request.term,
										 
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {
				                         response(data.ListRegion);
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
				        }, minLength:0, maxShowItems: 4, scroll:true,
						select: function(event, ui) {			
							this.value = (ui.item ? ui.item[2]:'');
							$(this).parents("tr").find('input[name ="regionId"]').val(ui.item[0]);
							$(this).parents("tr").find('input[name ="Regionname"]').val(ui.item[1]);
										$(this).parents("tr").find('input[name ="RegionLong"]').val(ui.item[3]);
			$(this).parents("tr").find('input[name ="RegionLat"]').val(ui.item[4]);

			 initMap();           
							
							return false;
							
						}
					}).autocomplete("instance")._renderItem = function(ul, item) {
			            return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                	item[2] + "</span><br><span class='desc'>" +
		                    item[1] + "</span><br><span class='desc'>" +
		                    item[0] +"</span></div>")
		                .appendTo(ul);
		        	};
				$('#RegionTab > tbody > tr').eq(rowCount-1).find('input[name ="Regioncode"]').focus(function(){
					if (this.value == ""){
						$(this).autocomplete("search");
			        }
				});  */
 }   
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
//Prev fct in popup

 $('#DNModalRegion').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   

				
				////////////////////
				
//Prev fct in popup
	 $(function(){

$('#DNModalRegion').on('input', function() {

	//SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



 $("button[name='previousRowRegion']").on("click", function(){

  	if(rowindxRegion > 0) {
  		//console.log("Welcome to previous!");
  		rowindxRegion-- ;
  		itemNumber--;

  		var PrevIndex = parseInt(itemNumber);
  		//console.log("PrevIndex" +PrevIndex);
  		
	
		
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());
         tempPopupregionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();


	    //Update popup Nb in header
				var element = document.getElementById("popupRegion");
				element.innerHTML = "Item # " +PrevIndex;
      		 }

	   	// Close popup on row 0 (end of prev fct)

      		   else if (rowindxRegion == 0) {
      		    	$("#DNModalRegion").modal("hide");
      	      }

  	});// end prev fct


  //Send input values from popup to boq when any popup input change
	  				$('#popupregionId,#popupregionName,#popupregionCode,#popupregionId').on(' focusout ', function() {

		


	 				$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val($('#popupregionId').val());
 			 		$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val($('#popupregionName').val());
 			 		$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val($('#popupregionCode').val());
 			 		$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val($('#popupagentRegionId').val());
 		
		

  		 
  

  	  	 	//SetCalcPopUp();
  	      }); // end fct



  	   	// Close popup function
  	   $("button[name='closePopup']").on("click", function(){
  		 // console.log("Welcome to close");
  		//  console.log("On close"+rowindxRegion);

  		  // Send input values from popup to boq table
		
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());

           tempPopupregionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();
  
  		    $("#DNModalRegion").modal("hide");
  			//SetCalcPopUp();




  	 }); // end close fct

  	    	    

  // Send input values from popup to boq table and close the popup using ESC
  	document.addEventListener('keydown', function(event){
      	if(event.key === "Escape"){
          	
		$('#popupregionId') .val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val());
		$('#popupregionName').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regionname"]').children('input').val());
	    $('#popupregionCode').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="Regioncode"]').children('input').val());
		$('#popupagentRegionId').val($("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="agentRegionId"]').children('input').val());


         tempPopupregionId=$("#RegionTab >tbody").find("tr").eq(rowindxRegion).find('td[name="regionId"]').children('input').val();

  	    $("#DNModalRegion").modal("hide");
  		//SetCalcPopUp();

      }
   });// end close fct using esc
   
   

				 // Ending for item autocomplete in Boq table    			
			
//////////////////////////////////

				////////////////////
				






   $('.modal-content').resizable({
		handles: "e" ,

		});

		$('.modal-dialog').draggable({
		handle: ".modal-header, .modal-footer"
		});

		$('#DNModalRegion').on('show.bs.modal', function() {
		$(this).find('.modal-body').css({
		'max-height': '100%',
		});
		});

	  	//Reset the popup to original size after resizing
		$('#DNModalRegion').on('hidden.bs.modal', function() {
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
 });
	 });
	 
 function PopupDynamicDeleteRegion () {
		 
		 var RowsToDelete =  document.getElementById("RowToDeleteRegion").value;
		 RowsToDelete.slice(0, -1);
		var rows = RowsToDelete.split(",");
		
		 $("#RegionTab > tbody").each(function(){
 		 //var lfpID = $(this).parent().parent().children('td[name="lfpID"]').children('input').val();
 		 for (let i = 0; i < rows.length-1; i++) {
			 slctDelRegion.push(rows[i]);
	    			}
	 			});
			 }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		