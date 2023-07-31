//function to get selected rows for save or delete
 
 //  openPopupArea
 var rowindxArea=0;
 var itemNumber;
 var areaPolygons=[];

///////////////////////////////////////////////////////openPopArea////////////////////////////////////////////////////////////////
 function openPopArea(element) {

	 var buttonNodeRowIndx = $(element).closest("tr");
	 itemNumber=buttonNodeRowIndx[0].rowIndex;
	 rowindxArea = (buttonNodeRowIndx[0].rowIndex - 1);
	// console.log("Row index of clicked button:" +rowindxArea);

		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
		

		
		 $("#DNModalArea").modal("show");

		tempPopupareaId = $("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val();

		 var element = document.getElementById("popupArea");
         element.innerHTML = "Item # " +itemNumber;
			// SetCalcPopUp();
 }
// end of openPopupArea
 /////////////////////////////////////////////////insertRowBelowArea/////////////////////////////////////////////////////////////////////
function insertRowBelowArea(){

//console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrowsArea('addRowBelowArea');

  rowindxArea++ ;
  itemNumber++;
	var belowIndex = parseInt(itemNumber);
	//console.log("belowIndex:" +belowIndex);
		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
		//Update popup Nb in header
		var element = document.getElementById("popupArea");
		element.innerHTML = "Item # " +parseInt(belowIndex);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

		//	SetCalcPopUp();

 }
///////////////////////////////////////////////////////insertRowAboveArea/////////////////////////////////////////////////////////////////////////////////
// Insert Row Above fct
 function insertRowAboveArea(){

//	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
   addrowsArea('addRowAboveArea');
//----------------------------------------

	//console.log("aboveIndex:" +rowindxArea);
	
		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
		
		
		//Update popup Nb in header
		var element = document.getElementById("popupArea");
		element.innerHTML = "Item # " +parseInt(itemNumber);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			//SetCalcPopUp();

}// End insertRowAbove fct in popup
////////////////////////////////////////////////nextRowArea////////////////////////////////////////////////////////////////////////////////////////

function nextRowArea(){

	// Get total nb of rows
	var rowCount = $("#AreaTab >tbody tr").length;
	//console.log("rowCount in BoQ:" +rowCount);

	rowindxArea++ ;
	itemNumber++;
	var nextIndex = parseInt(itemNumber);
	//console.log("Next Index" +nextIndex);

	if(rowindxArea >= 0 && rowindxArea < rowCount) {
		//console.log("Welcome to Next function"+rowindxArea);


		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
			tempPopupareaId = $("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val();
	
		//Update popup Nb in header

		var element = document.getElementById("popupArea");
			//alert(nextIndex);
			//alert(rowindxActions);
			
			element.innerHTML = "Item # " +nextIndex;

			 }

	// Add new row when rowindxActions exceed the row count
	else if (rowindxArea >= rowCount) {
		//console.log("ADD NEW ROW USING NEXT BUTTON");
 			addrowsArea('addRowArea');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
	
			//alert(rowindxActions);
		
			//Update popup Nb in header
			var element = document.getElementById("popupArea");
			element.innerHTML = "Item # " +nextIndex;
			}
}
/////////////////////////////////////////////////////addrowsArea////////////////////////////////////////////////////////////////////////////////////////

//Add rows fct in popup

 function addrowsArea(stat){
	 
		 var rowCount= $("#AreaTab >tbody tr").length;
	//	console.log("RowCount in BOQ:" +rowCount);
		//console.log("rowindxArea in BOQ:" +rowindxArea);
		
		var markup = "<tr><td><input type='checkbox' name='chekbox_rowArea' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopArea(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		+"<td name='areaId' hidden><input  name='areaId'  type='text'  style='width:150px;' class='form-control text-input'></td>"
	 	+"<td name = 'Areaname'><input name='Areaname' type='text'  style='width:100%;' class='form-control text-input'/></td>"
	 	+"<td name = 'areaLong' hidden><input name='areaLong' type='text'  style='width:100%;' class='form-control text-input'/></td>"
	 	+"<td name = 'areaLat' hidden><input name='areaLat' type='text'  style='width:100%;' class='form-control text-input'/></td>"
	 	+"<td name = 'agentAreaId' hidden><input name='agentAreaId'  type='text' style='width:200px;' class='form-control text-input' readonly/></td></tr>"
	 	
	 	
	 	var myDiv = document.getElementById("AreaTab");
 	    myDiv.scrollTop = myDiv.scrollHeight;
 	    
		 if (stat == "addRowArea")
        {
     $("#AreaTab > tbody").append(markup);
     		rowCount++;
     
   }
   if(stat == "addRowBelowArea")
   {
     $("#AreaTab > tbody tr").eq(rowindxArea).after(markup);
     rowCount++;
   }
   if(stat == "addRowAboveArea")
   {
      $("#AreaTab > tbody tr").eq(rowindxArea).before(markup);
     rowCount= rowindxArea+1;
   }
                                              ////////////////////////////////////////////////////////////////
//AUTOCOMPLETE


    /*function getAllAreaId()
	{
		AnArray = [];
		var AnValue = "";
		$("input[name=areaId]").each(function(){
		if($(this).val() == "")
		AnValue = "empty";
		else
		AnValue = $(this).val();
		AnArray.push(AnValue);
		});
		return AnArray;   
	}*/
	
	///////////////////////////////
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

	////////////////////////////////
	function getContextPath() {
		   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}
	////////////////////////////////
	/*function getAllAreaname()
			{
				AiArray = [];
				var AiValue = "";
				$("input[name=Areaname]").each(function(){
					if($(this).val() == "")
						AiValue = "empty";
					else
						AiValue = $(this).val();
					AiArray.push(AiValue);
				});
				return AiArray;   
			}*/
	var ctx =getContextPath(); 
			
	///////////////////////////////////////////////////////////
	//to be deleted
	/*$('#AreaTab > tbody > tr').eq(rowCount-1).find('input[name ="areaId"]').autocomplete({
	   source: function(request, response, event, ui) {
			$.ajax({
			      type: "GET",
			      contentType: "application/json; charset=utf-8",
			      url: ctx+'/GetAllAreas',
			      data: {
						"areaId" : request.term,
						},
			      dataType: "json",
			      success: function (data) {
			      if (data != null) {response(data.listAreas);}
			       },
			      error: function(result) {alert("Error");}
			  });
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {			
				this.value = (ui.item ? ui.item[0]:'');
				$(this).parents("tr").find('input[name ="Areaname"]').val(ui.item[1]);
				$(this).parents("tr").find('input[name ="areaLong"]').val(ui.item[2]);
				$(this).parents("tr").find('input[name ="areaLat"]').val(ui.item[3]);
				return false;
						
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                    item[0] + "</span><br><span class='desc'>" +
	                    item[1] +"</span></div>")
	                .appendTo(ul);
	        	};
	        	 $('#AreaTab > tbody > tr').eq(rowCount-1).find('input[name ="areaId"]').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			});*/ // Ending for item autocomplete in Boq table    			
	
var Areaname;

var previousAreaId =null;
var prevBorders =null;
$('#AreaTab > tbody > tr').eq(rowCount-1).find('input[name ="Areaname"]').autocomplete ({
	source: areas,
	minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui,request, response) {
		this.value = (ui.item ? ui.item[1]:'');
		$(this).parents("tr").find('input[name ="areaId"]').val(ui.item[0]);
				console.log("previousAreaId == "+previousAreaId);
  	  			if(previousAreaId != ui.item[0]) {
  	  	  			if(previousAreaId != ""){
						if(prevBorders != "N/A" && prevBorders != null){
							areaPolygons[previousAreaId].setMap(null);
						}
  	  	  	  		}
  	  				this.value = (ui.item ? ui.item[1]:'');
  	  				previousAreaId = ui.item[0];
  	  				Tabora.length=0;
  	   	    		var splittingCoordinate=[];
  	   	    		var CoordinatesArray=[];
  	   	    		
  	   	    		console.log("result == "+ui.item[2]);
  	   	    		if(ui.item[2] != "N/A"){
  	   	    		prevBorders = ui.item[2];
  	   	    		splittingCoordinate=ui.item[2].split(":");
  	   	    		for(i=0;i<splittingCoordinate.length;i++) {
  	   	    			Tabora.push({
  	     				      lat:parseFloat(splittingCoordinate[i].split(",")[0]),
  	     				      lng: parseFloat(splittingCoordinate[i].split(",")[1])
  	     				});
  	   	    		}
  	   	     		areaPolygon= new google.maps.Polygon({
  	      		  	    	path: Tabora,
  	  	       		  	    id:ui.item[0],
  	  	       		  	    geodesic: false,
  	  	       		  	    strokeColor: '#06038D',
  	  	       		  	    strokeOpacity: 1.0,
  	  	       		  	    fillOpacity: 0.01,
  	  	       		  	    strokeWeight: 5,
  	  	       		  	    map: map
  	      		  	 	});
  	   	    
  	   	     		areaPolygon.metadata = { id: ui.item[0] };
  	   	     		areaPolygons=[];
  	   	     		areaPolygons[ui.item[0]] = areaPolygon;
  	   	     		areaPolygons.push(areaPolygon);
  	   	    		}else{

							prevBorders = "N/A";
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
	    $('#AreaTab > tbody > tr').eq(rowCount-1).find('input[name ="Areaname"]').focus(function(){
	    	previousAreaId =$(this).parents("tr").find('input[name ="areaId"]').val();
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			}); // Ending for item autocomplete in Boq table  
	    
	    $('#AreaTab > tbody > tr').eq(rowCount-1).find('input[name ="Areaname"]').change(function(){
	    	
	    	if(this.value == "") {
	    	console.log(prevBorders);
	    		if(previousAreaId != "") {
	    			if(prevBorders != null && prevBorders != "N/A"){
	    			areaPolygons[previousAreaId].setMap(null);
	    			}
	    		}
	    		$(this).parents("tr").find('input[name ="areaId"]').val("");
	    		previousAreaId="";
	    	}else {
	    		
	    		previousAreaId =$(this).parents("tr").find('input[name ="areaId"]').val();
	    	}
	    	
	    });
	    
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//delete fct Actions
function deleteBoqRowArea() {
	 var areaId = $("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val();
		
		 		//alert(areaId);
	 
	 slctDelArea = [];
	//console.log("rowindxArea " +rowindxArea);
	 $("#AreaTab > tbody").each(function(){
	 
		 var AreaID =$("#popupagentAreaId").val();
		
		$("#AreaTab >tbody").find("tr").eq(rowindxArea).remove();
		var RowsToDelete =  document.getElementById("RowToDeleteArea").value;
       var Myresult = "";


       if (RowsToDelete != ""){

       Myresult = RowsToDelete ;
       }
     //  console.log("Myresult is "+Myresult);
		 Myresult += AreaID+",";
        document.getElementById("RowToDeleteArea").value = Myresult;

	 		});
	 	
			  
		// Get Nb of rows after delete
	var rowNodeCount = $("#AreaTab >tbody tr").length;
	//console.log("rowCount in BoQ:" +rowNodeCount);
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});

	 if (rowindxArea == 0 && rowNodeCount ==0) {

		  $("#DNModalArea").modal("hide");

		  }
	  if(rowindxArea >= 0 && rowindxArea < rowNodeCount) {

		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
		
			//Update popup Nb in header 

			var element = document.getElementById("popupArea");
			element.innerHTML = "Item # " +itemNumber;  
			//NodeRowIndex++;
	 }
	    // Show previous record 
		 if (rowindxArea >= rowNodeCount){
			rowindxArea--;
			itemNumber--;
		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
			//Update popup Nb in header 
			var element = document.getElementById("popupArea");
			element.innerHTML = "Item # " +itemNumber; 
		}
		
		 for (i = 0;i<Object.keys(areaToDraw).length;i++){
			        			Tabora.length=0;
			        		  	areaCoordinates=areaToDraw[Object.keys(areaToDraw)[i]];
			        		  	 
			        			  	if(Object.keys(areaToDraw)[i] == areaId ){
			areaToDraw[Object.keys(areaToDraw)[i]].length=0;
			delete areaToDraw[areaId];
			areacheck=1;
					        	
				        			  	}
			        	  }	
			        	          	  initMap();
			        	   
		//console.log("rowNodeIndex: "+rowindxArea);
	 }// End delete fct 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Prev fct in popup

	 $('#DNModalArea').on('input', function() {

		//SetCalcPopUp();

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});



	//Prev fct in popup
	 $(function(){
	$('#DNModalArea').on('input', function() {

		//SetCalcPopUp();

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});

                    ////////////////////////////////

	 $("button[name='previousRowArea']").on("click", function(){

	  	if(rowindxArea> 0) {
	  	//	console.log("Welcome to previous!");
	  		rowindxArea-- ;
	  		itemNumber--;
	  		var PrevIndex = parseInt(itemNumber);
	  	//	console.log("PrevIndex" +PrevIndex);
			
		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
		    //Update popup Nb in header
		    
		 tempPopupareaId = $("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val();
		    
					var element = document.getElementById("popupArea");
					element.innerHTML = "Item # " +PrevIndex;
	      		 }

		   	// Close popup on row 0 (end of prev fct)

	      		   else if (rowindxArea == 0) {
	      		    	$("#DNModalArea").modal("hide");
	      	      }

	  	});// end prev fct
                       ///////////////////////////////////

	  //Send input values from popup to boq when any popup input change
	  	 $('#popupareaId,#popupareaName,#popupagentAreaId').on(' focusout ', function() {
	  	 
	  	 
	 		$("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val($('#popupareaId').val());
	 		$("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val($('#popupareaName').val());
	 		$("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val($('#popupagentAreaId').val());


	  	  	 	//SetCalcPopUp();
	  	      }); // end fct



	  	   	// Close popup function
	  	   $("button[name='closePopup']").on("click", function(){
	  		 // console.log("Welcome to close");
	  		 // console.log("On close"+rowindxArea);

	  		  // Send input values from popup to boq table
			//console.log("PrevIndex" +PrevIndex);
		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
			
			
			
			tempPopupareaId = $("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val();
	
	  		    $("#DNModalArea").modal("hide");
	  			//SetCalcPopUp();




	  	 }); // end close fct

	  	    	    

	  // Send input values from popup to boq table and close the popup using ESC
	  	document.addEventListener('keydown', function(event){
	      	if(event.key === "Escape"){
	      	
	      	//	console.log("PrevIndex" +PrevIndex);
		$('#popupareaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val());
		$('#popupareaName').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="Areaname"]').children('input').val());
		$('#popupagentAreaId').val($("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="agentAreaId"]').children('input').val());
			
		tempPopupareaId = $("#AreaTab >tbody").find("tr").eq(rowindxArea).find('td[name="areaId"]').children('input').val();
			
			
	  	    $("#DNModalArea").modal("hide");
	  		//SetCalcPopUp();

	      }
	   });// end close fct using esc
	   $('.modal-content').resizable({
			handles: "e" ,

			});

			$('.modal-dialog').draggable({
			handle: ".modal-header, .modal-footer"
			});

			$('#DNModalArea').on('show.bs.modal', function() {
			$(this).find('.modal-body').css({
			'max-height': '100%',
			});
			});

		  	//Reset the popup to original size after resizing
			$('#DNModalArea').on('hidden.bs.modal', function() {
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 //dynamic delete fct for Area
	 function PopupDynamicDeleteArea () {
		 
		 var RowsToDelete =  document.getElementById("RowToDeleteArea").value;
		 RowsToDelete.slice(0, -1);
		var rows = RowsToDelete.split(",");
		
		 $("#AreaTab > tbody").each(function(){
 		 //var lfpID = $(this).parent().parent().children('td[name="lfpID"]').children('input').val();
 		 for (let i = 0; i < rows.length; i++) {
			 slctDelArea.push(rows[i]);
	    			}
	 			});
			 }
	 