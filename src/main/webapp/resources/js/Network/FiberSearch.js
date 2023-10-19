/*function sortTable(table, col, reverse) {
    var tb = table.tBodies[0], // use `<tbody>` to ignore `<thead>` and `<tfoot>` rows
        tr = Array.prototype.slice.call(tb.rows, 0), // put rows into array
        i;
    reverse = -((+reverse) || -1);
    
    tr = tr.sort(function (a, b) { // sort rows        
        if(!isNaN(a.cells[col].textContent) && !isNaN(b.cells[col].textContent))
        return reverse * ((+a.cells[col].textContent) - (+b.cells[col].textContent))
       return reverse // `-1 *` if want opposite order
            * (a.cells[col].textContent.trim() // using `.textContent.trim()` for test
                .localeCompare(b.cells[col].textContent.trim())
               );
    });
    for(i = 0; i < tr.length; ++i) tb.appendChild(tr[i]); // append each row in order
}*/

/*function makeSortable(table) {
    var th = table.tHead, i;
    th && (th = th.rows[0]) && (th = th.cells);
    if (th) i = th.length;
    else return; // if no `<thead>` then do nothing
    while (--i >= 0) (function (i) {
        var dir = 1;
        th[i].addEventListener('click', function () {sortTable(table, i, (dir = 1 - dir))});
    }(i));
}*/

/*function makeAllSortable(parent) {
    parent = parent || document.body;
    var t = parent.getElementsByTagName('table'), i = t.length;
    while (--i >= 0) makeSortable(t[i]);
}*/

//TO BE DELETED 
/*function TubeStrandAutoComplete(srcDestID,index,longitudeID,latitudeID,tableID,rowID){

$("#"+srcDestID).autocomplete({
	source: function(request, response) {
	var search= $("#"+srcDestID).val();
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/SearchForSource',
		data: {
			"search":search,
		},
		dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.searchResult);                   					 
		}
		},
		error: function(result) {
			alert("Error");
		}
		});
	
	},select: function (event, ui) {
		var sourceId=ui.item.label.split(":");					
		sourceId=sourceId[0];
		
		if(sourceId.split("_")[0]=="MH" || sourceId.split("_")[0]=="HH" ){
			$("#"+longitudeID).val(window[""+sourceId][2]);
			$("#"+latitudeID).val(window[""+sourceId][3]);

		}
		else{
			$("#"+longitudeID).val(window[""+sourceId][1]);
			$("#"+latitudeID).val(window[""+sourceId][2]);
		}
		var tubeDestLat = $(this).closest("tr").find("td:eq(10) input[type='text']").val();
		var tubeDestLong = $(this).closest("tr").find("td:eq(9) input[type='text']").val();
		var tubeSrcLat = $(this).closest("tr").find("td:eq(6) input[type='text']").val();
		var tubeSrcLong = $(this).closest("tr").find("td:eq(5) input[type='text']").val();
		var strandDestLat = $(this).closest("tr").find("td:eq(10) input[type='text']").val();
		var strandDestLong = $(this).closest("tr").find("td:eq(9) input[type='text']").val();
		var strandSrcLat = $(this).closest("tr").find("td:eq(7) input[type='text']").val();
		var strandSrcLong = $(this).closest("tr").find("td:eq(6) input[type='text']").val();
		var cableTubeID = $(this).closest("tr").find("td:eq(3) input[type='text']").val();
		var searchTarget = $(this).parent().attr("name");					

		if(searchTarget == "tubeSource"){
			var auxTubeTotalDist = calculateDistanceAuxTube($("#"+latitudeID).val(),$("#"+longitudeID).val(),tubeDestLat,tubeDestLong,"tubesTable",cableTubeID,index);
			var totalDistanceValue = (parseFloat(auxTubeTotalDist)+parseFloat($("#FiberLength").val())).toFixed(3);
			$("#tube_length"+index).val(parseFloat(auxTubeTotalDist).toFixed(3));
			$("#tube_total_length"+index).val(totalDistanceValue);
		}			

		else if (searchTarget == "tubeDestination"){
			var auxTubeTotalDist = calculateDistanceAuxTube(tubeSrcLat,tubeSrcLong,$("#"+latitudeID).val(),$("#"+longitudeID).val(),"tubesTable",cableTubeID,index);
			var totalDistanceValue = (parseFloat(auxTubeTotalDist)+parseFloat($("#FiberLength").val())).toFixed(3);
			$("#tube_length"+index).val(parseFloat(auxTubeTotalDist).toFixed(3));
			$("#tube_total_length"+index).val(totalDistanceValue);

		}				
		if(searchTarget == "strandSource"){
			var auxStrandTotalDist = calculateDistanceAuxTube($("#"+latitudeID).val(),$("#"+longitudeID).val(),strandDestLat,strandDestLong,"strandsTable",cableTubeID,index);
			$("#strand_length"+index).val(auxStrandTotalDist);

		}					
		else if (searchTarget == "strandDestination"){
			var auxStrandTotalDist = calculateDistanceAuxTube(strandSrcLat,strandSrcLong,$("#"+latitudeID).val(),$("#"+longitudeID).val(),"strandsTable",cableTubeID,index);
			$("#strand_length"+index).val(auxStrandTotalDist);

		}
		if(searchTarget == "TubeAuxiliary"){
			var sourceLat = $("#tubeSource_Lat"+indexTubeForAuxs).val();
			var sourceLong = $("#tubeSource_Long"+indexTubeForAuxs).val();
			var destinationLat =	$("#tubeDestination_Lat"+indexTubeForAuxs).val();
			var destinationLong = $("#tubeDestination_Long"+indexTubeForAuxs).val();

			var auxTubeTotalDistance = calculateDistanceAuxTube(sourceLat,sourceLong,destinationLat,destinationLong,"TubeAuxTable",idTubeForAuxs,indexTubeForAuxs);
			
			$("#saveTubeAux").click(function(){
				var totalDistanceValue = (parseFloat(auxTubeTotalDistance)+parseFloat($("#FiberLength").val())).toFixed(3);
				$("#tube_length"+indexTubeForAuxs).val(parseFloat(auxTubeTotalDistance).toFixed(3));
				$("#tube_total_length"+indexTubeForAuxs).val(totalDistanceValue);
			});
				
		}					
		else if (searchTarget == "StrandAuxiliary"){
			
			var sourceLat = $("#strandSource_Lat"+indexStrandForAuxs).val();
			var sourceLong = $("#strandSource_Long"+indexStrandForAuxs).val();
			var destinationLat = $("#strandDestination_Lat"+indexStrandForAuxs).val();
			var destinationLong = $("#strandDestination_Long"+indexStrandForAuxs).val();

			var totalDistanceValue = calculateDistanceAuxTube(sourceLat,sourceLong,destinationLat,destinationLong,"StrandAuxTable",idStrandForAuxs,indexStrandForAuxs);
			
			$("#saveStrandAux").click(function(){
				$("#strand_length"+indexStrandForAuxs).val(totalDistanceValue);
			});	

		}
		},
		appendTo : $("#"+tableID+" > tbody").find("#"+rowID), 
		minLength:0, maxShowItems: 40, scroll:true,			 
	});
$("#"+srcDestID).focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});
}*/

function ManHandHoleAutoCompleteJunction(ID,physicalLayer){

	$("#"+ID).autocomplete({
		source: function(request, response) {
		var search= $("#"+ID).val();
		var physicalLayerType = physicalLayer;
		var projectId =IdNodeSelectedTemp;

		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/SearchForManholeHandhole',
			data: {
				"physLayerSearch":search,
				"physicalLayer":physicalLayerType,
				"ProjectId": projectId
								
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.searchPhysicalLayerDetails);                   
				}
			},
			error: function(result) {
				alert("Error");
			}
			});
		},minLength:0, maxShowItems: 40, scroll:true,
		select: function (event, ui) {
		var physicalLayerType = physicalLayer;

		if(physicalLayerType == "Manhole") {
			manholeNameJct.value = (ui.item ? ui.item[0]  : '');
			manholeIdJct.value=ui.item[1];
			manholeJctLong.value=ui.item[2];
			manholeJctLat.value=ui.item[3];
			manholeJctCity.value=ui.item[4];
		}
		else {
			handholeNameJct.value = (ui.item ? ui.item[0]  : '');
			handholeIdJct.value=ui.item[1];
			handholeJctLong.value=ui.item[2];
			handholeJctLat.value=ui.item[3];
			handholeJctCity.value=ui.item[4];						
		}
		return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li class='each'>")
	    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	       item[0] + "</span></div>" )
	    .appendTo(ul);
	};
	$("#"+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
		});
}

function ItemCodeFiberAutoComplete(){

	$("#ItemCodeId").autocomplete({
		source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/getItemCode',
			data: {
				requestValue:request.term,
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.ListItemDetails);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		},
		minLength:0, maxShowItems: 40, scroll:true,
		select: function(event, ui) {
		ItemCodeId.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li class='each'>")
		.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		item[0] +":" + item[1]  +"</span></div>") 
		.appendTo(ul);
		};
	   
$("#ItemCodeId").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});
}
function SourceDestinationAutoComplete(checkboxClass,Type,srcID,srcLong,srcLat,srcCity,destLat,destLong,tableID,target){
	var url ="emptyUrl";
	var dataTarget="";
	if($('#'+srcID).data('ui-autocomplete') != undefined){
		$('#'+srcID).autocomplete("destroy");
	}	
	$("#"+srcID).autocomplete({
		source: function(request, response) {
		var search= $("#"+srcID).val();
		var checkedCheckboxAutocomplete=" ";		
	//Get the id of checked checkbox
	$('input:checkbox[class="' + checkboxClass + '"]:checked').each(function () {
		var checkedCheckbox =  $(this).attr("id");
		checkedCheckboxAutocomplete = checkedCheckbox.split("_");			
		checkedCheckboxAutocomplete=checkedCheckboxAutocomplete[0]; 	
	});

	//On change , get the id of changed checked checkbox
	$("."+checkboxClass).change(function() {
		var checkedCheckbox = this.id;
		checkedCheckboxAutocomplete = checkedCheckbox.split("_");			
		checkedCheckboxAutocomplete=checkedCheckboxAutocomplete[0];
	});
		     
	if(checkedCheckboxAutocomplete=="manhole") {
		url ='getManholeData';
		dataTarget = {					
			"search":search,
		}
	}
	else if(checkedCheckboxAutocomplete=="handhole") {
		url ='getHandholeData';
		dataTarget = {					
			"search":search,
		}
	}
	else if(checkedCheckboxAutocomplete=="db") {
		url ='getDbData';
		dataTarget = {					
			"search":search,
		}
	}
	else if(checkedCheckboxAutocomplete=="site") {
		url='GetAllWarehouse';
		dataTarget = {
       		"WareID":search,
			"warehouseName" : search,
			"SiteId":search,
		 }
	}	
	else if(checkedCheckboxAutocomplete=="customer") {
		url='GetAllNetworkCustomer';
		dataTarget = {					
				"search":search,
			}
	}
	else {
		url='emptyUrl';
	}

  if(url !="emptyUrl") {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/'+url,
			data: dataTarget,				
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
	}				
	}, minLength:0, maxShowItems: 40, scroll:true,
		select: function (event, ui) {		   

			if(ui.item[0].split("_")[0]=="MH"){
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				$("#"+srcLong).val(ui.item[3]);
				$("#"+srcLat).val(ui.item[4]);
				$("#"+srcCity).val(ui.item[2]);
				$("#"+Type).val("Manhole");	
			}	
			else if(ui.item[0].split("_")[0]=="HH"){
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				$("#"+srcLong).val(ui.item[3]);
				$("#"+srcLat).val(ui.item[4]);
				$("#"+srcCity).val(ui.item[2]);
				$("#"+Type).val("Handhole");			
			}			
			else if(ui.item[0].split("_")[0]=="DB"){
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				$("#"+srcLong).val(ui.item[3]);
				$("#"+srcLat).val(ui.item[4]);
				$("#"+srcCity).val(ui.item[2]);
				$("#"+Type).val("Distribution Board");	
			}		
			else if(ui.item[0].split("_")[0]=="WARE"){
				$("#"+srcLong).val(ui.item[3]);
				$("#"+srcLat).val(ui.item[4]);
				$("#"+Type).val("Site");
				$("#"+srcCity).val(ui.item[5]);
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1]+':'+ui.item[2] : '');
		}	
		else{
			this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
			$("#"+srcLong).val(ui.item[4]);
			$("#"+srcLat).val(ui.item[5]);
			$("#"+Type).val("Customer");
			$("#"+srcCity).val(ui.item[3]);		
		}	
			//No need to calculate the distance in case of origination or termination	
		if (checkboxClass !="OriginationAutoComplete" && checkboxClass !="TerminationAutoComplete" && checkboxClass !="tubeOriginationAutoComplete" 
			&& checkboxClass !="trenchOriginationAutoComplete" && checkboxClass !="ductOriginationAutoComplete" && checkboxClass !="strandOriginationAutoComplete" && checkboxClass !="ductTerminationAutoComplete" && checkboxClass !="trenchTerminationAutoComplete" && checkboxClass !="strandTerminationAutoComplete" && checkboxClass !="tubeTerminationAutoComplete")	{
			if(target=="source") {	
				calculateDistanceSourceDestination($("#"+srcLat).val(),$("#"+srcLong).val(),$("#"+destLat).val(),$("#"+destLong).val(),tableID);
			}
			else {
				calculateDistanceSourceDestination($("#"+destLat).val(),$("#"+destLong).val(),$("#"+srcLat).val(),$("#"+srcLong).val(),tableID);
			}
		}
			return false;
		},
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {		
					if(item[0].split("_")[0]=="WARE") {
						 return $("<li class='each'>")
			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                   item[0] + "</span><br><span class='desc'>" +
			                    item[1] +', '+ item[2] + "</span></div>")
			                .appendTo(
			                ul);
					}
					else if (item[0].split("_")[0]=="CUST") {
						return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                   item[0] + "</span><br><span class='desc'>" +
		                    item[1] + ', ' + item[6] +"</span></div>")
		                .appendTo(ul);						
					}
					else {
						 return $("<li class='each'>")
			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                   item[0] + "</span><br><span class='desc'>" +
			                    item[1] + "</span></div>")
			                .appendTo(ul);
						}
			};
		$("#"+srcID).focus(function(){
			   if (this.value == ""){
				   $(this).autocomplete("search");
			   }						
		});
}

function autoCompleteMultiPoint(SiteID,LongID,LatID,index,checkboxclass){

	$("#"+SiteID+index).autocomplete({
		source: function(request, response,event,ui) {
		    var searchs=$("#"+SiteID+index).val();
		    var search ="";
			var line;
		  $('input:checkbox[class="'+checkboxclass+'"]:checked').each(function () {
			var checkedCheckbox =  $(this).attr("id");
			search = checkedCheckbox.split("_");			
			search=search[0]; 	
		});
		
		$("."+checkboxclass).change(function() {
			var checkedCheckbox = this.id;
			search = checkedCheckbox.split("_");			
			search=search[0];
		});
				if(search=="Site"){
					url='GetAllWarehouse';
					dataTarget = {
			       		"WareName":searchs,
						"warehouseName" : searchs,
						"SiteId":searchs,
					 } 		
				}
			else {
				url='emptyUrl';
			}
		if(url !="emptyUrl") {
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/'+url,
				data: dataTarget,				
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
			} 
		}, minLength:0, maxShowItems: 40, scroll:true,
	 		select: function(event, ui) {
	 			
				if(ui.item[0].split("_")[0]=="WARE"){
				$("#"+SiteID+index).val(ui.item ? ui.item[0] : '');
					$("#"+LongID+index).val(ui.item[3]);
					$("#"+LatID+index).val(ui.item[4]);
					
				}
								
			return false;
		},	
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		if(item[0].split("_")[0]=="WARE") {
			 return $("<li class='each'>")
	        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	           item[2] + "</span><br><span class='desc'>" +
	            item[5] +', '+ item[1] + "</span></div>")
	        .appendTo(ul);
		}
	
		};		    	    
							         
		$("#"+SiteID+index).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
		        }
		});	
}

function AuxPointAutoComplete(checkboxClass,auxPtID,auxPtLong,auxPtLat,srcLat,srcLong,destLat,destLong,tableID,index,city){
	
	var url ="emptyUrl";
	var dataTarget="";
	$("#"+auxPtID).autocomplete({
		 source: function(request, response) {
		 var search= $("#"+auxPtID).val();
		 var checkedCheckboxAutocomplete="AuxPt";

	    $('input:checkbox[class="' + checkboxClass + '"]:checked').each(function () {
			var checkedCheckbox =  $(this).attr("id");
			checkedCheckboxAutocomplete = checkedCheckbox.split("_");			
			checkedCheckboxAutocomplete=checkedCheckboxAutocomplete[0]; 	
		  });
		    
		  $("."+checkboxClass).change(function() {
		     //get the id of checked checkbox
		      var checkedCheckbox = this.id;
		      checkedCheckboxAutocomplete = checkedCheckbox.split("_");			
		      checkedCheckboxAutocomplete=checkedCheckboxAutocomplete[0];	
		  }); 
		    if(checkedCheckboxAutocomplete=="Manhole") {
				url ='getManholeData';
				dataTarget = {					
					"search":search,
				}
			}
			else if(checkedCheckboxAutocomplete=="Handhole") {
				url ='getHandholeData';
				dataTarget = {					
					"search":search,
				}
			}
			else if(checkedCheckboxAutocomplete=="DB") {
				url ='getDbData';
				dataTarget = {					
					"search":search,
				}
			}
			else if(checkedCheckboxAutocomplete=="Site") {
				url='GetAllWarehouse';
				dataTarget = {
		       		"WareName":search,
					"warehouseName" : search,
					"SiteId":search,
				 }
			}
			else if(checkedCheckboxAutocomplete=="customer") {
				url='GetAllNetworkCustomer';
				dataTarget = {					
						"search":search,
				}			
			}
			else if(checkedCheckboxAutocomplete=="AuxPt") {
				url='emptyUrl';			
			}
			else {
				url='emptyUrl';
			}
	if(url !="emptyUrl") {
			  $.ajax({
				   type: "GET",
				   contentType: "application/json; charset=utf-8",
				   url: getContext()+'/'+url,
				   data: dataTarget,				
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
	}
	  }, minLength:0, maxShowItems: 40, scroll:true,

	  select: function (event, ui) {	
		  if(ui.item[0].split("_")[0]=="MH" || ui.item[0].split("_")[0]=="HH"){
			 this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
			 $("#"+auxPtLong).val(ui.item[3]);
			 $("#"+auxPtLat).val(ui.item[4]);
			 $("#"+city).val(ui.item[2]);
				 
		  }	
		  else if(ui.item[0].split("_")[0]=="DB"){
				 this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				 $("#"+auxPtLong).val(ui.item[3]);
				 $("#"+auxPtLat).val(ui.item[4]);
				 $("#"+city).val(ui.item[2]);
		 }	
		 else if(ui.item[0].split("_")[0]=="WARE") {
			 $("#"+auxPtLong).val(ui.item[3]);
			 $("#"+auxPtLat).val(ui.item[4]);
			 $("#"+city).val(ui.item[5]);
			 this.value = (ui.item ? ui.item[0]+':'+ui.item[1]+':'+ui.item[2] : '');
	     }
		 else{
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				$("#"+auxPtLong).val(ui.item[4]);
				$("#"+auxPtLat).val(ui.item[5]);
				$("#"+city).val(ui.item[3]);		
			}		
		if(tableID=="tubesTable") {
					var tubeDestLat = $(this).closest("tr").find("td:eq(11) input[type='text']").val();
					var tubeDestLong = $(this).closest("tr").find("td:eq(10) input[type='text']").val();
					var tubeSrcLat = $(this).closest("tr").find("td:eq(6) input[type='text']").val();
					var tubeSrcLong = $(this).closest("tr").find("td:eq(5) input[type='text']").val();
					var cableTubeID = $(this).closest("tr").find("td:eq(3) input[type='text']").val();
					var searchTarget = $(this).parent().attr("name");					

					if(searchTarget == "tubeSource"){
						var auxTubeTotalDist = calculateDistanceAuxTube($("#"+auxPtLat).val(),$("#"+auxPtLong).val(),tubeDestLat,tubeDestLong,"tubesTable",cableTubeID,index);
						var totalDistanceValue = (parseFloat(auxTubeTotalDist)+parseFloat($("#FiberLength").val())).toFixed(3);
						$("#tube_length"+index).val(parseFloat(auxTubeTotalDist).toFixed(3));
						$("#tube_total_length"+index).val(totalDistanceValue);
					}
					else if (searchTarget == "tubeDestination"){
						var auxTubeTotalDist = calculateDistanceAuxTube(tubeSrcLat,tubeSrcLong,$("#"+auxPtLat).val(),$("#"+auxPtLong).val(),"tubesTable",cableTubeID,index);
						var totalDistanceValue = (parseFloat(auxTubeTotalDist)+parseFloat($("#FiberLength").val())).toFixed(3);
						$("#tube_length"+index).val(parseFloat(auxTubeTotalDist).toFixed(3));
						$("#tube_total_length"+index).val(totalDistanceValue);
					}										
			}
		else if(tableID=="strandsTable") {
			var cableTubeID = $(this).closest("tr").find("td:eq(3) input[type='text']").val();
			var searchTarget = $(this).parent().attr("name");					
			var strandDestLat = $(this).closest("tr").find("td:eq(10) input[type='text']").val();
			var strandDestLong = $(this).closest("tr").find("td:eq(11) input[type='text']").val();
			var strandSrcLat = $(this).closest("tr").find("td:eq(7) input[type='text']").val();
			var strandSrcLong = $(this).closest("tr").find("td:eq(6) input[type='text']").val();

			if(searchTarget == "strandSource"){
				var auxStrandTotalDist = calculateDistanceAuxTube($("#"+auxPtLat).val(),$("#"+auxPtLong).val(),strandDestLat,strandDestLong,"strandsTable",cableTubeID,index);
				$("#strand_length"+index).val(auxStrandTotalDist);
			}					
			else if (searchTarget == "strandDestination"){
				var auxStrandTotalDist = calculateDistanceAuxTube(strandSrcLat,strandSrcLong,$("#"+auxPtLat).val(),$("#"+auxPtLong).val(),"strandsTable",cableTubeID,index);
				$("#strand_length"+index).val(auxStrandTotalDist);
			}
		}
		else if (tableID=="TubeAuxTable") {
			var sourceLat = $("#tubeSource_Lat"+indexTubeForAuxs).val();
			var sourceLong = $("#tubeSource_Long"+indexTubeForAuxs).val();
			var destinationLat =	$("#tubeDestination_Lat"+indexTubeForAuxs).val();
			var destinationLong = $("#tubeDestination_Long"+indexTubeForAuxs).val();

			var auxTubeTotalDistance = calculateDistanceAuxTube(sourceLat,sourceLong,destinationLat,destinationLong,"TubeAuxTable",idTubeForAuxs,indexTubeForAuxs);
			$("#totalDistanceFiberTube").val(parseFloat(auxTubeTotalDistance).toFixed(3));
			
			$("#saveTubeAux").click(function(){
				var totalDistanceValue = (parseFloat(auxTubeTotalDistance)+parseFloat($("#FiberLength").val())).toFixed(3);
				$("#tube_length"+indexTubeForAuxs).val(parseFloat(auxTubeTotalDistance).toFixed(3));
				$("#tube_total_length"+indexTubeForAuxs).val(totalDistanceValue);
			});
		}
		else if (tableID == "StrandAuxTable"){
			
			var sourceLat = $("#strandSource_Lat"+indexStrandForAuxs).val();
			var sourceLong = $("#strandSource_Long"+indexStrandForAuxs).val();
			var destinationLat = $("#strandDestination_Lat"+indexStrandForAuxs).val();
			var destinationLong = $("#strandDestination_Long"+indexStrandForAuxs).val();
			var totalDistanceValue = calculateDistanceAuxTube(sourceLat,sourceLong,destinationLat,destinationLong,"StrandAuxTable",idStrandForAuxs,indexStrandForAuxs);
			$("#saveStrandAux").click(function(){
				$("#strand_length"+indexStrandForAuxs).val(totalDistanceValue);
			});	
		}
		else {
				calculateDistanceSourceDestination($("#"+srcLat).val(),$("#"+srcLong).val(),$("#"+destLat).val(),$("#"+destLong).val(),tableID);
			}
				return false;
			   },
}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		if(item[0].split("_")[0]=="WARE" ) {
			 return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                   item[0] + "</span><br><span class='desc'>" +
                    item[1] +', '+ item[2] + "</span></div>")
                .appendTo(ul);
		}
		else {
			 return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                   item[0] + "</span><br><span class='desc'>" +
                    item[1] + "</span></div>")
                .appendTo(ul);
			}
};
	
	$("#"+auxPtID).focus(function(){
		  if (this.value == ""){
			  $(this).autocomplete("search");
		  }						
	});		 
}
function autoCompleteTubeId(tubeId){
	
	$(tubeId).autocomplete({
		source: tubeListFromBoq,
		 minLength:0,
		 appendTo : $("#strandsTable")
	}).on('focus', function() { $(this).keydown();

	});		
}
//site auto complete
$(function(){ 

$("#DistributionBoardSite").autocomplete({
	 source: debounce(function(request, response) {
		var searchkey = $("#DistributionBoardSite").val();
		console.log("siteid");
		  $.ajax({
	             type: "GET",
	             contentType: "application/json; charset=utf-8",
	             url:  getContext()+'/GetAllWarehouse',
	             data:  {					
					 "WareName":searchkey,
					 "warehouseName" : searchkey,
					 "SiteId":searchkey,
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
		 },1000), 
		 minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
								
			this.value = (ui.item ? ui.item[2] : '');
			$("#DistributionBoardSiteName").val(ui.item[1]);
			$("#DistributionBoardWarehouse").val(ui.item[0]);
			if (document.getElementById('customCoordinates').checked) {
				console.log("customCoordinates");
				if($("#DistributionBoardLong").val() =="" && $("#DistributionBoardLat").val() =="" ) {
					$("#DistributionBoardLong").val(ui.item[3]);
					$("#DistributionBoardLat").val(ui.item[4]);
				}
			}else{
				$("#DistributionBoardLong").val(ui.item[3]);
				$("#DistributionBoardLat").val(ui.item[4]);
			}
			
			$("#boardCity").val(ui.item[5]);
			//$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
			if(($("#DistributionBoardName").val()) ==""){
				$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
			}
			return false;
			}
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
			 return $("<li class='each'>")
		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		        item[0] + "</span><br><span class='desc'>" +
		         item[1] +', '+ item[2] + "</span></div>")
		     .appendTo(ul);
	};

$("#DistributionBoardSite").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});	

$("#DistributionBoardSiteName").autocomplete({
	 source: debounce(function(request, response) {
		var searchkey = $("#DistributionBoardSiteName").val();
		console.log("name");
		  $.ajax({
	             type: "GET",
	             contentType: "application/json; charset=utf-8",
	             url:  getContext()+'/GetAllWarehouse',
	             data: {					
					 "WareName":searchkey,
					 "warehouseName" : searchkey,
					 "SiteId":searchkey,
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
		},1000), 
		 minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
										
			this.value = (ui.item ? ui.item[1] : '');
			$("#DistributionBoardSite").val(ui.item[2]);
			$("#DistributionBoardWarehouse").val(ui.item[0]);
			$("#DistributionBoardLong").val(ui.item[3]);
			$("#DistributionBoardLat").val(ui.item[4]);
			$("#boardCity").val(ui.item[5]);
			//$("#DistributionBoardName").val(ui.item[1]+"_DB");
			if(($("#DistributionBoardName").val()) ==""){
				$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
			}

			return false;
			}
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
			 return $("<li class='each'>")
		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		        item[0] + "</span><br><span class='desc'>" +
		         item[1] +', '+ item[2] + "</span></div>")
		     .appendTo(ul);
	};

$("#DistributionBoardSiteName").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});	

$("#DistributionBoardWarehouse").autocomplete({
	 source:debounce( function(request, response) {

		var searchkey = $("#DistributionBoardWarehouse").val();
		  $.ajax({
	             type: "GET",
	             contentType: "application/json; charset=utf-8",
	             url:  getContext()+'/GetAllWarehouse',
	             data: {
	            	 "WareName":searchkey,
					 "warehouseName" : searchkey,
					 "SiteId":searchkey,
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
		 },1000), 
		 minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
										
			this.value = (ui.item ? ui.item[0] : '');
			$("#DistributionBoardSite").val(ui.item[2]);
			$("#DistributionBoardSiteName").val(ui.item[1]);
			$("#DistributionBoardLong").val(ui.item[3]);
			$("#DistributionBoardLat").val(ui.item[4]);
			$("#boardCity").val(ui.item[5]);
			//$("#DistributionBoardName").val(ui.item[1]+"_DB");
			if(($("#DistributionBoardName").val()) ==""){
				$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
			}

			return false;
			}
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
			 return $("<li class='each'>")
		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		        item[0] + "</span><br><span class='desc'>" +
		         item[1] +', '+ item[2] + "</span></div>")
		     .appendTo(ul);
	};

$("#DistributionBoardWarehouse").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});	

//client auto complete
$("#DistributionBoardClient").autocomplete({
	 source: debounce(function(request, response) {
		var searchkey = $("#DistributionBoardClient").val();
		console.log("id");
		  $.ajax({
	             type: "GET",
	             contentType: "application/json; charset=utf-8",
	             url:  getContext()+'/GetAllNetworkCustomer',
	             data:  {					
	            	 "search":searchkey,
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
		 },1000), 
		 minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
										
			this.value = (ui.item ? ui.item[0] : '');
			$("#DistributionBoardClientName").val(ui.item[1]);
			$("#DistributionBoardClientPhoneNb").val(ui.item[2]);
			if (document.getElementById('customCoordinates').checked) {
				console.log("customCoordinates");
				if($("#DistributionBoardLong").val() =="" && $("#DistributionBoardLat").val() =="" ) {
					$("#DistributionBoardLong").val(ui.item[4]);
					$("#DistributionBoardLat").val(ui.item[5]);
				}
			}else{
				$("#DistributionBoardLong").val(ui.item[4]);
				$("#DistributionBoardLat").val(ui.item[5]);
			}
			
			$("#boardCity").val(ui.item[3]);
			//$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
			if(($("#DistributionBoardName").val()) ==""){
				$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
			}

			return false;
			}
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
			 return $("<li class='each'>")
		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	    		 item[0] + "</span><br><span class='desc'>" +
		         item[1] +', '+item[2] +"</span></div>")
		     .appendTo(ul);
	};

$("#DistributionBoardClient").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});	

$("#DistributionBoardClientName").autocomplete({
	 source: debounce(function(request, response) {
		var searchkey = $("#DistributionBoardClientName").val();
		console.log("name");
		
		  $.ajax({
	             type: "GET",
	             contentType: "application/json; charset=utf-8",
	             url:  getContext()+'/GetAllNetworkCustomer',
	             data:  {					
	            	 "search":searchkey,
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
		 },1000), 
		 minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
										
			this.value = (ui.item ? ui.item[1] : '');
			$("#DistributionBoardClient").val(ui.item[0]);
			$("#DistributionBoardClientPhoneNb").val(ui.item[2]);
			$("#DistributionBoardLong").val(ui.item[4]);
			$("#DistributionBoardLat").val(ui.item[5]);
			$("#boardCity").val(ui.item[3]);
			//$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
			if(($("#DistributionBoardName").val()) ==""){
				$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
			}

			return false;
			}
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
			 return $("<li class='each'>")
		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	    		 item[1] + "</span><br><span class='desc'>" +
		         item[0] +', '+ item[2] + "</span></div>")
		     .appendTo(ul);
	};

$("#DistributionBoardClientName").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});	

$("#DistributionBoardClientPhoneNb").autocomplete({
	 source: debounce(function(request, response) {
		var searchkey = $("#DistributionBoardClientPhoneNb").val();
		
		  $.ajax({
	             type: "GET",
	             contentType: "application/json; charset=utf-8",
	             url:  getContext()+'/GetAllNetworkCustomer',
	             data:  {					
	            	 "search":searchkey,
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
		   
		 },1000), 
		 minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
										
			this.value = (ui.item ? ui.item[2] : '');
			$("#DistributionBoardClient").val(ui.item[0]);
			$("#DistributionBoardClientName").val(ui.item[1]);
			$("#DistributionBoardLong").val(ui.item[4]);
			$("#DistributionBoardLat").val(ui.item[5]);
			$("#boardCity").val(ui.item[3]);
			//$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
			if(($("#DistributionBoardName").val()) ==""){
				$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
			}

			return false;
			}
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
			 return $("<li class='each'>")
		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	    		 item[2] + "</span><br><span class='desc'>" +
		         item[0] +', '+ item[1] + "</span></div>")
		     .appendTo(ul);
	};

$("#DistributionBoardClientPhoneNb").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});
//Add conditions for default google maps and its styles 
$('#default').click(function () {
	setdefaultoptions(map);})
$('#blank').click(function () {
setblankoptions(map);})
$('#poi').click(function () {
SetPoiOptions(map);})
$('#road').click(function () {
SetRoadOptions(map);})
$('#transit').click(function () {
SetTransitOptions(map);})
$('#water').click(function () {
SetWaterOptions(map);})
$('#mapgeography').click(function () {
SetLightGeographyOptions(map);})
$('#maplabels').click(function () {
SetCountryBorders(map);})	
$('#countrynames').click(function () {
SetCountryNames(map);})		
$('#countryprovince').click(function () {
SetCountryProvice(map);})

document.getElementById("tubeColor").onchange = function() {
	if (document.getElementById("tubeColor").value=="blue"){
	   	 document.getElementById("tubeColor").style.backgroundColor = "blue"; 
	   	 document.getElementById("tubeColor").style.color = "white";
	     document.getElementById("tubeNumber").value= "1";
	    }
	else if (document.getElementById("tubeColor").value=="orange"){
	     document.getElementById("tubeColor").style.backgroundColor = "orange"; 
	     document.getElementById("tubeColor").style.color = "white";
	     document.getElementById("tubeNumber").value= "2";
	    }
	else if (document.getElementById("tubeColor").value=="green"){
	   	 document.getElementById("tubeColor").style.backgroundColor = "green";
	   	 document.getElementById("tubeColor").style.color = "white";
	   	 document.getElementById("tubeNumber").value= "3";
	   	 }
	 else if(document.getElementById("tubeColor").value=="brown") {
	   	 document.getElementById("tubeColor").style.backgroundColor = "brown";
	   	 document.getElementById("tubeColor").style.color = "white";
	   	 document.getElementById("tubeNumber").value= "4";
	    }
	 else if(document.getElementById("tubeColor").value=="gray") {
	   	 document.getElementById("tubeColor").style.backgroundColor = "gray"; 
	   	 document.getElementById("tubeColor").style.color = "white";
	   	 document.getElementById("tubeNumber").value= "5";
	    }
	 else if(document.getElementById("tubeColor").value=="white") {
	   	 document.getElementById("tubeColor").style.backgroundColor = "white"; 
	   	 document.getElementById("tubeColor").style.color = "black";
	   	 document.getElementById("tubeNumber").value= "6";
	    }	
	 else if(document.getElementById("tubeColor").value=="red"){
         document.getElementById("tubeColor").style.backgroundColor = "red";
    	 document.getElementById("tubeColor").style.color = "white";
    	 document.getElementById("tubeNumber").value= "7";
	 }
    else if(document.getElementById("tubeColor").value=="black") {
      	 document.getElementById("tubeColor").style.backgroundColor = "black";
      	 document.getElementById("tubeColor").style.color = "white";
      	 document.getElementById("tubeNumber").value= "8";
       } 
    else if(document.getElementById("tubeColor").value=="yellow") {
      	 document.getElementById("tubeColor").style.backgroundColor = "yellow";
      	 document.getElementById("tubeColor").style.color = "black";
      	 document.getElementById("tubeNumber").value= "9";
       } 	
    else if(document.getElementById("tubeColor").value=="violet") {
   		 document.getElementById("tubeColor").style.backgroundColor = "violet"; 
   		 document.getElementById("tubeColor").style.color = "white";
   		 document.getElementById("tubeNumber").value= "10";
   	  }         
    else if(document.getElementById("tubeColor").value=="pink") {
   		 document.getElementById("tubeColor").style.backgroundColor = "pink";
   		 document.getElementById("tubeColor").style.color = "black";
   		 document.getElementById("tubeNumber").value= "11";
      }
    else if(document.getElementById("tubeColor").value=="aqua") {
  		 document.getElementById("tubeColor").style.backgroundColor = "aqua";
  		 document.getElementById("tubeColor").style.color = "black";
  		 document.getElementById("tubeNumber").value= "12";
     }   
}

const input = document.getElementById("tubeNumber");
input.addEventListener ("input" ,function() {
	if (input.value== "1"){
		 document.getElementById("tubeColor").value = "blue";
	   	 document.getElementById("tubeColor").style.backgroundColor = "blue"; 
	   	 document.getElementById("tubeColor").style.color = "white";
	    }
	else if (input.value== "2"){
		 document.getElementById("tubeColor").value ="orange";
	     document.getElementById("tubeColor").style.backgroundColor = "orange"; 
	     document.getElementById("tubeColor").style.color = "white";	     
	    }
	else if (input.value== "3"){
		 document.getElementById("tubeColor").value ="green";
	   	 document.getElementById("tubeColor").style.backgroundColor = "green";
	   	 document.getElementById("tubeColor").style.color = "white";
	   	 }
	 else if(input.value== "4") {
		 document.getElementById("tubeColor").value ="brown";
	   	 document.getElementById("tubeColor").style.backgroundColor = "brown";
	   	 document.getElementById("tubeColor").style.color = "white";
	    }
	 else if(input.value== "5") {
		 document.getElementById("tubeColor").value ="gray";
	   	 document.getElementById("tubeColor").style.backgroundColor = "gray"; 
	   	 document.getElementById("tubeColor").style.color = "white";  	 
	    }
	 else if(input.value== "6") {
		 document.getElementById("tubeColor").value ="white";
	   	 document.getElementById("tubeColor").style.backgroundColor = "white"; 
	   	 document.getElementById("tubeColor").style.color = "black";
	    }	
	 else if(input.value== "7"){
		 document.getElementById("tubeColor").value ="red";
         document.getElementById("tubeColor").style.backgroundColor = "red";
    	 document.getElementById("tubeColor").style.color = "white";  	 
	 }
    else if(input.value== "8") {
    	 document.getElementById("tubeColor").value ="black";
      	 document.getElementById("tubeColor").style.backgroundColor = "black";
      	 document.getElementById("tubeColor").style.color = "white";
       } 
    else if(input.value== "9") {
    	document.getElementById("tubeColor").value ="yellow";
      	 document.getElementById("tubeColor").style.backgroundColor = "yellow";
      	 document.getElementById("tubeColor").style.color = "black";
       } 	
    else if(input.value== "10") {
    	 document.getElementById("tubeColor").value ="violet";
   		 document.getElementById("tubeColor").style.backgroundColor = "violet"; 
   		 document.getElementById("tubeColor").style.color = "white";
   	  }         
    else if(input.value== "11") {
    	document.getElementById("tubeColor").value ="pink";
   		 document.getElementById("tubeColor").style.backgroundColor = "pink";
   		 document.getElementById("tubeColor").style.color = "black"; 		
      }
    else if(input.value== "12") {
    	document.getElementById("tubeColor").value ="aqua";
  		 document.getElementById("tubeColor").style.backgroundColor = "aqua";
  		 document.getElementById("tubeColor").style.color = "black";
     }  
    else if(input.value > "12" || input.value == ""){
		 document.getElementById("tubeColor").value ="";
  		 document.getElementById("tubeColor").style.backgroundColor = "";
  		 document.getElementById("tubeColor").style.color = "";		
     }
});

document.getElementById("strandColor").onchange = function() {
	if (document.getElementById("strandColor").value=="blue"){
	   	 document.getElementById("strandColor").style.backgroundColor = "blue"; 
	   	 document.getElementById("strandColor").style.color = "white";
	     document.getElementById("strandNumber").value= "1";
	    }
	else if (document.getElementById("strandColor").value=="orange"){
	     document.getElementById("strandColor").style.backgroundColor = "orange"; 
	     document.getElementById("strandColor").style.color = "white";
	     document.getElementById("strandNumber").value= "2";
	    }
	else if (document.getElementById("strandColor").value=="green"){
	   	 document.getElementById("strandColor").style.backgroundColor = "green";
	   	 document.getElementById("strandColor").style.color = "white";
	   	 document.getElementById("strandNumber").value= "3";
	   	 }
	 else if(document.getElementById("strandColor").value=="brown") {
	   	 document.getElementById("strandColor").style.backgroundColor = "brown";
	   	 document.getElementById("strandColor").style.color = "white";
	   	 document.getElementById("strandNumber").value= "4";
	    }
	 else if(document.getElementById("strandColor").value=="gray") {
	   	 document.getElementById("strandColor").style.backgroundColor = "gray"; 
	   	 document.getElementById("strandColor").style.color = "white";
	   	 document.getElementById("strandNumber").value= "5";
	    }
	 else if(document.getElementById("strandColor").value=="white") {
	   	 document.getElementById("strandColor").style.backgroundColor = "white"; 
	   	 document.getElementById("strandColor").style.color = "black";
	   	 document.getElementById("strandNumber").value= "6";
	    }	
	 else if(document.getElementById("strandColor").value=="red"){
         document.getElementById("strandColor").style.backgroundColor = "red";
    	 document.getElementById("strandColor").style.color = "white";
    	 document.getElementById("strandNumber").value= "7";
	 }
    else if(document.getElementById("strandColor").value=="black") {
      	 document.getElementById("strandColor").style.backgroundColor = "black";
      	 document.getElementById("strandColor").style.color = "white";
      	 document.getElementById("strandNumber").value= "8";
       } 
    else if(document.getElementById("strandColor").value=="yellow") {
      	 document.getElementById("strandColor").style.backgroundColor = "yellow";
      	 document.getElementById("strandColor").style.color = "black";
      	 document.getElementById("strandNumber").value= "9";
       } 	
    else if(document.getElementById("strandColor").value=="violet") {
   		 document.getElementById("strandColor").style.backgroundColor = "violet"; 
   		 document.getElementById("strandColor").style.color = "white";
   		 document.getElementById("strandNumber").value= "10";
   	  }         
    else if(document.getElementById("strandColor").value=="pink") {
   		 document.getElementById("strandColor").style.backgroundColor = "pink";
   		 document.getElementById("strandColor").style.color = "black";
   		 document.getElementById("strandNumber").value= "11";
      }
    else if(document.getElementById("strandColor").value=="aqua") {
  		 document.getElementById("strandColor").style.backgroundColor = "aqua";
  		 document.getElementById("strandColor").style.color = "black";
  		 document.getElementById("strandNumber").value= "12";
     }   
}

const inputt = document.getElementById("strandNumber");
inputt.addEventListener ("input" ,function() {
	if (inputt.value== "1"){
		 document.getElementById("strandColor").value = "blue";
	   	 document.getElementById("strandColor").style.backgroundColor = "blue"; 
	   	 document.getElementById("strandColor").style.color = "white";
	    }
	else if (inputt.value== "2"){
		 document.getElementById("strandColor").value ="orange";
	     document.getElementById("strandColor").style.backgroundColor = "orange"; 
	     document.getElementById("strandColor").style.color = "white";	     
	    }
	else if (inputt.value== "3"){
		 document.getElementById("strandColor").value ="green";
	   	 document.getElementById("strandColor").style.backgroundColor = "green";
	   	 document.getElementById("strandColor").style.color = "white";
	   	 }
	 else if(inputt.value== "4") {
		 document.getElementById("strandColor").value ="brown";
	   	 document.getElementById("strandColor").style.backgroundColor = "brown";
	   	 document.getElementById("strandColor").style.color = "white";
	    }
	 else if(inputt.value== "5") {
		 document.getElementById("strandColor").value ="gray";
	   	 document.getElementById("strandColor").style.backgroundColor = "gray"; 
	   	 document.getElementById("strandColor").style.color = "white";  	 
	    }
	 else if(inputt.value== "6") {
		 document.getElementById("strandColor").value ="white";
	   	 document.getElementById("strandColor").style.backgroundColor = "white"; 
	   	 document.getElementById("strandColor").style.color = "black";
	    }	
	 else if(inputt.value== "7"){
		 document.getElementById("strandColor").value ="red";
         document.getElementById("strandColor").style.backgroundColor = "red";
    	 document.getElementById("strandColor").style.color = "white";  	 
	 }
    else if(inputt.value== "8") {
    	 document.getElementById("strandColor").value ="black";
      	 document.getElementById("strandColor").style.backgroundColor = "black";
      	 document.getElementById("strandColor").style.color = "white";
       } 
    else if(inputt.value== "9") {
    	document.getElementById("strandColor").value ="yellow";
      	 document.getElementById("strandColor").style.backgroundColor = "yellow";
      	 document.getElementById("strandColor").style.color = "black";
       } 	
    else if(inputt.value== "10") {
    	 document.getElementById("strandColor").value ="violet";
   		 document.getElementById("strandColor").style.backgroundColor = "violet"; 
   		 document.getElementById("strandColor").style.color = "white";
   	  }         
    else if(inputt.value== "11") {
    	document.getElementById("strandColor").value ="pink";
   		 document.getElementById("strandColor").style.backgroundColor = "pink";
   		 document.getElementById("strandColor").style.color = "black"; 		
      }
    else if(inputt.value== "12") {
    	document.getElementById("strandColor").value ="aqua";
  		 document.getElementById("strandColor").style.backgroundColor = "aqua";
  		 document.getElementById("strandColor").style.color = "black";
     }  
    else if(inputt.value > "12" || inputt.value == ""){
		 document.getElementById("strandColor").value ="";
  		 document.getElementById("strandColor").style.backgroundColor = "";
  		 document.getElementById("strandColor").style.color = "";		
     }
});

});

function autoCompleteForMapping(ID,tableID,rowID,Location,LocationID,LocationM,LocationType,equipment,equipmentID,equipmentName,equipmentType,strandID,strandName,tubeID,tubeName,fiberID,fiberName,strandNb,strandColor,tubeNb,tubeColor,junctionID,junctionName,networkLevel){
	var url ="emptyUrl";
	var dataTarget="";
	var search="";				
	//console.log("mapping autocomplete");
$("#"+LocationID+ID).autocomplete({
	source: debounce(function(request, response,event,ui) {
	    var searchs=$("#"+LocationID+ID).val();
		var line;
	    search= $("#"+LocationType+ID).val();
	    console.log("debounce");
		if(search=="Customer"){
				url='GetAllNetworkCustomer';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if(search=="Site"){
				url='GetAllWarehouse';
				dataTarget = {
		       		"WareName":searchs,
					"warehouseName" : searchs,
					"SiteId":searchs,
				 }
			}
			else if(search=="Manhole"){
				url ='getManholeData';
				dataTarget = {					
					"search":searchs,
				}			
			}
			else if (search=="Handhole"){
				url ='getHandholeData';
				dataTarget = {					
					"search":searchs,
				}
		}
		else {
			url='emptyUrl';
		}
	if(url !="emptyUrl") {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/'+url,
			data: dataTarget,				
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
		} 
/*END FUNCTION*/	},900), minLength:0, maxShowItems: 40, scroll:true,
 		select: function(event, ui) {
 			
			if(search=="Customer"){
				$("#"+LocationID+ID).val(ui.item ? ui.item[0] : '');
				$("#"+LocationM+ID).val(ui.item[1]);
				//$("#"+Location+ID).val(ui.item[3]);
			}
			else if(search=="Site"){
				$("#"+LocationM+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[2]);
				console.log("Location is " +ui.item[0]);
				console.log("ID is " +ID);
				$("#"+Location+ID).val(ui.item[0]);
				console.log("#FP_Location is " +$("#"+Location+ID).val());
			}
			else if(search=="Manhole"){
				$("#"+LocationM+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[0]);
				//$("#"+Location+ID).val(ui.item[2]);
							
			}
			else if (search=="Handhole"){
				$("#"+LocationM+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[0]);
				//$("#"+Location+ID).val(ui.item[2]);
			}			
		return false;
	},	
}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	
	if(item[0].split("_")[0]=="CUST" ) {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1] +', '+ item[2]+ "</span></div>")
            .appendTo(ul);
	}
	else if(item[0].split("_")[0]=="WARE") {
		 return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
           item[2] + "</span><br><span class='desc'>" +
            item[0] +', '+ item[1] + "</span></div>")
        .appendTo(ul);
	}
	else {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1]  +', '+ item[2] + "</span></div>")
            .appendTo(ul);
		}
	};		    	    
						         
	$("#"+LocationID+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});		

	// autocomplete for locacation name
	$("#"+LocationM+ID).autocomplete({
	source: debounce( function(request, response,event,ui) {
	    var searchs=$("#"+LocationM+ID).val();
		var line;
	    search= $("#"+LocationType+ID).val();
	    console.log("no debounce");
		if(search=="Customer"){
				url='GetAllNetworkCustomer';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if(search=="Site"){
				url='GetAllWarehouse';
				dataTarget = {
		       		"WareName":searchs,
					"warehouseName" : searchs,
					"SiteId":searchs,
				 }
			}
			else if(search=="Manhole"){
				url ='getManholeData';
				dataTarget = {					
					"search":searchs,
				}			
			}
			else if (search=="Handhole"){
				url ='getHandholeData';
				dataTarget = {					
					"search":searchs,
				}
		}
		else {
			url='emptyUrl';
		}
	if(url !="emptyUrl") {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/'+url,
			data: dataTarget,				
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
		} 
	},900), minLength:0, maxShowItems: 40, scroll:true,
 		select: function(event, ui) {
 			if(search=="Customer"){
 				$("#"+LocationID+ID).val(ui.item ? ui.item[0] : '');
				$("#"+LocationM+ID).val(ui.item[1]);
				//$("#"+Location+ID).val(ui.item[3]);
			}
			else if(search=="Site"){
				$("#"+LocationM+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[2]);
				$("#"+Location+ID).val(ui.item[0]);
			}
			else if(search=="Manhole"){
				$("#"+LocationM+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[0]);
				//$("#"+Location+ID).val(ui.item[2]);
							
			}
			else if (search=="Handhole"){
				$("#"+LocationM+ID).val(ui.item ? ui.item[1] : '');
				$("#"+LocationID+ID).val(ui.item[0]);
				//$("#"+Location+ID).val(ui.item[2]);
			}			
		return false;
	},	
}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	
	if(item[0].split("_")[0]=="CUST" ) {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[1] + "</span><br><span class='desc'>" +
                item[0] +', '+item[2]+ "</span></div>")
            .appendTo(ul);
	}
	else if(item[0].split("_")[0]=="WARE") {
		 return $("<li class='each'>")
       .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
          item[1] + "</span><br><span class='desc'>" +
           item[0] +', '+ item[2] + "</span></div>")
       .appendTo(ul);
	}
	else {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1] +', '+ item[2] + "</span></div>")
            .appendTo(ul);
		}
	};		    	    
						         
	$("#"+LocationM+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});			
// auto complete equipment	
if(equipment !=""){
	$("#"+equipmentID+ID).autocomplete({
		source:debounce(function(request, response,event,ui) {
		    var searchs=$("#"+equipmentID+ID).val();
			var line;
		    search= $("#"+equipment+ID).val();
		    console.log("throttle")
			 if(search=="DistBoard"){
				url='getDbData';
				dataTarget = {
		       		"search":searchs,
				 }
			}	
			else if(search=="Node"){
				url='getNodeData';
				dataTarget = {					
					"searchs":searchs,
				}
			}
			else {
				url='emptyUrl';
			}
		if(url !="emptyUrl") {
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/'+url,
				data: dataTarget,				
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
			} 
		},900), minLength:0, maxShowItems: 40, scroll:true,
	 		select: function(event, ui) {
	 			if(search=="DistBoard"){
					$("#"+equipmentID+ID).val(ui.item[0]);
					$("#"+equipmentName+ID).val(ui.item[1]);
					$("#"+equipmentType+ID).val("Distribution Board");
				}
				else if(search=="Node"){
					 $("#"+equipmentID+ID).val(ui.item[0]);
					$("#"+equipmentName+ID).val(ui.item[1]);
					$("#"+equipmentType+ID).val(ui.item[2]);
				}
							
			return false;
		},	
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		//if(item[0].split("_")[0]=="DB" ) {
			 return $("<li class='each'>")
	            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	               item[0]+ "</span><br><span class='desc'>" +
	                item[1] +"</span></div>")
	            .appendTo(ul);
		/*}
		else {
			 return $("<li class='each'>")
	            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	               item[0] + "</span><br><span class='desc'>" +
	                item[1] +', '+ item[2] + "</span></div>")
	            .appendTo(ul);
			}*/
		};		    	    
							         
		$("#"+equipmentID+ID).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
		        }
		});
	/////
	$("#"+equipmentName+ID).autocomplete({
		source: debounce(function(request, response,event,ui) {
		    var searchs=$("#"+equipmentName+ID).val();
			var line;
		    search= $("#"+equipment+ID).val();
		    console.log("no throttle");
			 if(search=="DistBoard"){
				url='getDbData';
				dataTarget = {
		       		"search":searchs,
				 }
			}	
			else if(search=="Node"){
				url='getNodeData';
				dataTarget = {					
					"searchs":searchs,
				}
			}
			else {
				url='emptyUrl';
			}
		if(url !="emptyUrl") {
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/'+url,
				data: dataTarget,				
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
			} 
		},900), minLength:0, maxShowItems: 40, scroll:true,
	 		select: function(event, ui) {
	 			if(search=="DistBoard"){
					$("#"+equipmentID+ID).val(ui.item[0]);
					$("#"+equipmentName+ID).val(ui.item[1]);
					$("#"+equipmentType+ID).val("Distribution Board");
				}
				else if(search=="Node"){
					 $("#"+equipmentID+ID).val(ui.item[0]);
					$("#"+equipmentName+ID).val(ui.item[1]);
					$("#"+equipmentType+ID).val(ui.item[2]);
				}
							
			return false;
		},	
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		//if(item[0].split("_")[0]=="DB" ) {
			 return $("<li class='each'>")
	            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	               item[1]+ "</span><br><span class='desc'>" +
	                item[0] +"</span></div>")
	            .appendTo(ul);
		/*}
		else {
			 return $("<li class='each'>")
	            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	               item[0] + "</span><br><span class='desc'>" +
	                item[1] +', '+ item[2] + "</span></div>")
	            .appendTo(ul);
			}*/
		};		    	    
							         
		$("#"+equipmentName+ID).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
		        }
		});	
		
	$("#"+equipmentType+ID).autocomplete({
		source: debounce(function(request, response,event,ui) {
		    var searchs=$("#"+equipmentType+ID).val();
			var line;
		    search= $("#"+equipment+ID).val();
			 if(search=="DistBoard"){
				url='getDbData';
				dataTarget = {
		       		"search":searchs,
				 }
			}	
			else if(search=="Node"){
				url='getNodeData';
				dataTarget = {					
					"searchs":searchs,
				}
			}
			else {
				url='emptyUrl';
			}
		if(url !="emptyUrl") {
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/'+url,
				data: dataTarget,				
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
			} 
		},900), minLength:0, maxShowItems: 40, scroll:true,
	 		select: function(event, ui) {
	 			if(search=="DistBoard"){
					$("#"+equipmentID+ID).val(ui.item[0]);
					$("#"+equipmentName+ID).val(ui.item[1]);
					$("#"+equipmentType+ID).val("Distribution Board");
				}
				else if(search=="Node"){
					 $("#"+equipmentID+ID).val(ui.item[0]);
					$("#"+equipmentName+ID).val(ui.item[1]);
					$("#"+equipmentType+ID).val(ui.item[2]);
				}
							
			return false;
		},	
	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		//if(item[0].split("_")[0]=="DB" ) {
			 return $("<li class='each'>")
	            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	               item[0]+ "</span><br><span class='desc'>" +
	                item[1] +"</span></div>")
	            .appendTo(ul);
		/*}
		else {
			 return $("<li class='each'>")
	            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	               item[0] + "</span><br><span class='desc'>" +
	                item[1] +', '+ item[2] + "</span></div>")
	            .appendTo(ul);
			}*/
		};		    	    
							         
		$("#"+equipmentType+ID).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
		        }
		});	
	}					    	    
		$("#"+strandID+ID).autocomplete({
	    	    source: debounce(function(request, response,event, ui) {
					 var sId= $("#"+strandID+ID).val();
		    	     /*var fId= $("#"+fiberID+ID).val();
		    	     var cId = $("#"+tubeID+ID).val();
		    	     if(cId != null ){
			    	     searchId = cId;	 
			    	 }else if(fId != null){
			    	     searchId = fId;
				     }else{
			    		 searchId = sId;
				     }*/
					 console.log("strand id");
				     searchId = sId;
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",		                 
		                 url: getContext()+'/SearchMappingStrand',
		                 data: {
		                	 "searchId" : searchId,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.glist);
		                         console.log(data.glist);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });		           
		        },900), minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {
					this.value = (ui.item ? ui.item[0] : '');
					$(this).parents("tr").find('input[name ="'+strandName+'"]').val(ui.item[1]);
					$(this).parents("tr").find('input[name ="'+tubeID+'"]').val(ui.item[2]);
					$(this).parents("tr").find('input[name ="'+fiberID+'"]').val(ui.item[3]);
					$(this).parents("tr").find('input[name ="'+tubeName+'"]').val(ui.item[5]);
					$(this).parents("tr").find('input[name ="'+fiberName+'"]').val(ui.item[4]);
					$(this).parents("tr").find('input[name ="'+strandNb+'"]').val(ui.item[6]);
					$(this).parents("tr").find('input[name ="'+tubeNb+'"]').val(ui.item[8]);
					if(strandColor !=""){
						$(this).parents("tr").find('select[name ="'+tubeColor+'"]').val(ui.item[9]);
						$(this).parents("tr").find('select[name ="'+strandColor+'"]').val(ui.item[7]);
						tubeStrandSetColor(strandColor+ID,strandNb+ID);
						tubeStrandSetColor(tubeColor+ID,tubeNb+ID);
					}
					if(strandID.includes("mJct") || strandID.includes("hJct")){
						$(this).parents("tr").find('select[name ="'+networkLevel+'"]').val(ui.item[10]);
					}
					return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[0] + '</span><br><span class="desc">' +
                   item[1] +', '+item[4] +', '+item[5] + '</span></div>').appendTo(ul);
		};
		$("#"+strandID+ID).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
  	        }
		});
  ///////////////////////////////
$("#"+strandName+ID).autocomplete({
	    	    source: debounce(function(request, response,event, ui) {
		    	     var sName =$("#"+strandName+ID).val(); 
		    	   /*  var fId= $("#"+fiberID+ID).val();
		    	     var cId = $("#"+tubeID+ID).val();
		    	     if(cId != null ){
			    	     searchId = cId;
			    	 }else if(fId != null){
			    	     searchId = fId;
				     }else{
			    		 searchId = sName;
				     }*/
				      searchId = sName;
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",		                 
		                 url: getContext()+'/SearchMappingStrand',
		                 data: {
							"searchId" : searchId,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.glist);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		        },900), minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {
					this.value = (ui.item ? ui.item[1] : '');
					$(this).parents("tr").find('input[name ="'+strandID+'"]').val(ui.item[0]);
					$(this).parents("tr").find('input[name ="'+tubeID+'"]').val(ui.item[2]);
					$(this).parents("tr").find('input[name ="'+fiberID+'"]').val(ui.item[3]);
					$(this).parents("tr").find('input[name ="'+tubeName+'"]').val(ui.item[5]);
					$(this).parents("tr").find('input[name ="'+fiberName+'"]').val(ui.item[4]);
					$(this).parents("tr").find('input[name ="'+strandNb+'"]').val(ui.item[6]);
					$(this).parents("tr").find('input[name ="'+tubeNb+'"]').val(ui.item[8]);
					
					if(strandColor !=""){
						$(this).parents("tr").find('select[name ="'+tubeColor+'"]').val(ui.item[9]);
						$(this).parents("tr").find('select[name ="'+strandColor+'"]').val(ui.item[7]);
						
						tubeStrandSetColor(strandColor+ID,strandNb+ID);
						tubeStrandSetColor(tubeColor+ID,tubeNb+ID);
					}
					
					if(strandID.includes("mJct") || strandID.includes("hJct")){
						$(this).parents("tr").find('select[name ="'+networkLevel+'"]').val(ui.item[10]);
					}
				/*	var tubesAutocomplete=[];
					var fibersAutocomplete=[];
					$.ajax({
           type: "GET",
           contentType: "application/json; charset=utf-8",
           url: getContext()+'/GetFiberTubeNames',
           data: {
           		cID : ui.item[2],
					fID : ui.item[3],
				},
           dataType: "json",
           success: function (data) {

               if (data != null) {
               	tubesAutocomplete=data.clist;
					fibersAutocomplete=data.flist;
					console.log(ID);
					$("#"+fiberName+ID).val(fibersAutocomplete[0]);   
					$("#"+tubeName+ID).val(tubesAutocomplete[0]);             
               }
           },
           error: function(result) {
               alert("Error");
           }
       }); */				
					return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[1] + '</span><br><span class="desc">' +
                   item[0] +', '+item[4] +', '+item[5] + '</span></div>').appendTo(ul);
		};
		$("#"+strandName+ID).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
  	        }
		});
		$("#"+fiberID+ID).autocomplete({
   	    source: debounce(function(request, response,event, ui) {
   	    	console.log("fiber id");
				 var fId= $("#"+fiberID+ID).val();
				 var cId = $("#"+tubeID+ID).val();
				 var sId= $("#"+strandID+ID).val();
		    	     if(sId != "" ){
			    	     searchId = sId;    	    
			    	 }else if(cId != ""){
			    	     searchId = cId;
				     }else{
			    		 searchId = fId;
				     }
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8", 
	                 url: getContext()+'/SearchFiber',
	                 data: {
						"searchId" : searchId,
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.glist);
	                         console.log(data.glist);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });      
	        },900), minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {			
				this.value = (ui.item ? ui.item[0] : '');
				$(this).parents("tr").find('input[name ="'+fiberName+'"]').val(ui.item[1]);
				
				if(fiberID.includes("mJct") || fiberID.includes("hJct")){
					$(this).parents("tr").find('select[name ="'+networkLevel+'"]').val(ui.item[2]);
				}
				return false;
			}
		}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
   	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
   			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
               item[0] + '</span><br><span class="desc">' +
               item[1] + '</span></div>').appendTo(ul);
	};
	$("#"+fiberID+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
///////////////////////////////
$("#"+fiberName+ID).autocomplete({
   	    source: debounce(function(request, response,event, ui) {
				var fName= $("#"+fiberName+ID).val();
				 var cId = $("#"+tubeID+ID).val();
				 var sId= $("#"+strandID+ID).val();
		    	     if(sId != "" ){
			    	     searchId = sId;
			    	 }else if(cId != ""){
			    	     searchId = cId;
				     }else{
			    		 searchId = fName;
				     } 
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 
	                 url: getContext()+'/SearchFiber',
	                 data: {
						"searchId" : searchId,
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.glist);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });        
	        },900), minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {
				
				this.value = (ui.item ? ui.item[1] : '');
				$(this).parents("tr").find('input[name ="'+fiberID+'"]').val(ui.item[0]);
				
				if(fiberID.includes("mJct") || fiberID.includes("hJct")){
					$(this).parents("tr").find('select[name ="'+networkLevel+'"]').val(ui.item[2]);
				}
				
					return false;
			}
		}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
   	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
   			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
               item[1] + '</span><br><span class="desc">' +
               item[0] + '</span></div>').appendTo(ul);
	};
	$("#"+fiberName+ID).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});

	$("#"+tubeID+ID).autocomplete({
		 source: debounce(function(request, response,event, ui) {
		     var cName =$("#"+tubeID+ID).val(); 
		     var sId= $("#"+strandID+ID).val();
		     if(sId != ""){
	   	     searchId = sId;
	   	   //  url="SearchStrandTube";
	   	     console.log("tube id");
	   	 }else{
	   		 searchId = cName;
	   		 //url="SearchStrandTube";
		     }
		     console.log("sid "+searchId );
	        $.ajax({
	            type: "GET",
	            contentType: "application/json; charset=utf-8",
	            
	            url: getContext()+'/SearchTube',
	            data: {
					"searchId" : searchId,
				 },
	            dataType: "json",
	            success: function (data) {
	                if (data != null) {
	                    response(data.clist);
	                }
	            },
	            error: function(result) {
	                alert("Error");
	            }
	        }); 
	   },900), minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0] : '');
			$(this).parents("tr").find('input[name ="'+fiberID+'"]').val(ui.item[2]);
			$(this).parents("tr").find('input[name ="'+tubeName+'"]').val(ui.item[1]);
			$(this).parents("tr").find('input[name ="'+fiberName+'"]').val(ui.item[3]);
			$(this).parents("tr").find('input[name ="'+tubeNb+'"]').val(ui.item[4]);
			
			if(tubeColor !=""){
				$(this).parents("tr").find('select[name ="'+tubeColor+'"]').val(ui.item[5]);
				tubeStrandSetColor(tubeColor+ID,tubeNb+ID);
			}
			
			if(tubeID.includes("mJct") || tubeID.includes("hJct")){
					$(this).parents("tr").find('select[name ="'+networkLevel+'"]').val(ui.item[6]);
				}
			return false;
					}
				}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	                   item[0] + '</span><br><span class="desc">' +
	                   item[1] +', ' +item[3]+ '</span></div>').appendTo(ul);
			};
	$("#"+tubeID+ID).focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	  }
	});

$("#"+tubeName+ID).autocomplete({
   source: debounce(function(request, response,event, ui) {
	     var cName =$("#"+tubeName+ID).val(); 
	     var sId= $("#"+strandID+ID).val();
	     if(sId != ""){
   	     searchId = sId;
   	   //  url="SearchStrandTube";
   	 }else{
   		 searchId = cName;
   		// url="SearchStrandTube";
	     }
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchTube',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   },900), minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[1] : '');
		$(this).parents("tr").find('input[name ="'+fiberID+'"]').val(ui.item[2]);
		$(this).parents("tr").find('input[name ="'+tubeID+'"]').val(ui.item[0]);
		$(this).parents("tr").find('input[name ="'+fiberName+'"]').val(ui.item[3]);
		$(this).parents("tr").find('input[name ="'+tubeNb+'"]').val(ui.item[4]);
		
		if(tubeColor !=""){
			$(this).parents("tr").find('select[name ="'+tubeColor+'"]').val(ui.item[5]);
			tubeStrandSetColor(tubeColor+ID,tubeNb+ID);
		}
		
		if(tubeID.includes("mJct") || tubeID.includes("hJct")){
			$(this).parents("tr").find('select[name ="'+networkLevel+'"]').val(ui.item[6]);
		}
	/*	var tubesAutocomplete=[];
		var fibersAutocomplete=[];
		$.ajax({
           type: "GET",
           contentType: "application/json; charset=utf-8",
           url: getContext()+'/GetFiberTubeNames',
           data: {
           		cID : ui.item[1],
					fID : ui.item[2],
				},
           dataType: "json",
           success: function (data) {

               if (data != null) {
               	//tubesAutocomplete=data.clist;
					fibersAutocomplete=data.flist;
					console.log(ID);
					$("#"+fiberName+ID).val(fibersAutocomplete[0]);   
					//$("#"+tubeID+ID).val(tubesAutocomplete[0]);             
               }
           },
           error: function(result) {
              alert("Error");
           }
       });*/						
					return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[1] + '</span><br><span class="desc">' +
                   item[0] +', ' +item[3]+ '</span></div>').appendTo(ul);
		};
$("#"+tubeName+ID).focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
});
if(junctionID !=""){
	$("#"+junctionID+ID).autocomplete({
	   source: function(request, response,event, ui) {
		     var searchId =$("#"+junctionID+ID).val(); 
		    
		   
	        $.ajax({
	            type: "GET",
	            contentType: "application/json; charset=utf-8",
	            
	            url: getContext()+'/SearchJunction',
	            data: {
					"searchId" : searchId,
				 },
	            dataType: "json",
	            success: function (data) {
	                if (data != null) {
	                    response(data.clist);
	                }
	            },
	            error: function(result) {
	                alert("Error");
	            }
	        });
	      
	   }, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			$("#"+junctionID+ID).val(ui.item[0]);
			$("#"+junctionName+ID).val(ui.item[1]);
			
				
			return false;
					}
				}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	                   item[0] + '</span><br><span class="desc">' +
	                   item[1] +'</span></div>').appendTo(ul);
			};
	$("#"+junctionID+ID).focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	  }
	});
	
	$("#"+junctionName+ID).autocomplete({
	   source: function(request, response,event, ui) {
		     var searchId =$("#"+junctionName+ID).val(); 
		    
		   
	        $.ajax({
	            type: "GET",
	            contentType: "application/json; charset=utf-8",
	            
	            url: getContext()+'/SearchJunction',
	            data: {
					"searchId" : searchId,
				 },
	            dataType: "json",
	            success: function (data) {
	                if (data != null) {
	                    response(data.clist);
	                }
	            },
	            error: function(result) {
	                alert("Error");
	            }
	        });
	      
	   }, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			$("#"+junctionID+ID).val(ui.item[0]);
			$("#"+junctionName+ID).val(ui.item[1]);
			
				
			return false;
					}
				}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	                   item[1] + '</span><br><span class="desc">' +
	                   item[0] +'</span></div>').appendTo(ul);
			};
	$("#"+junctionName+ID).focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	  }
	});
}
}
function autoCompleteforRelatedCable(){
 $("#relatedCableStrandID").autocomplete({
	    	    source: function(request, response,event, ui) {
					 var sId= $("#relatedCableStrandID").val();
		    	    
				     searchId = sId;
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",		                 
		                 url: getContext()+'/SearchMappingStrand',
		                 data: {
		                	 "searchId" : searchId,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.glist);
		                         console.log(data.glist);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });		           
		        }, minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {
				
					$("#relatedCableStrandID").val(ui.item[0]);
					$("#relatedCableStrandName").val(ui.item[1]);
					$("#relatedCableTubeID").val(ui.item[2]);
					$("#relatedCableTubeName").val(ui.item[5]);
					$("#relatedCableID").val(ui.item[3]);
					$("#relatedCableName").val(ui.item[4]);
					
					$("#relatedCableStrandNb").val(ui.item[6]);
					$("#relatedCableStrandColor").val(ui.item[7]);
					$("#relatedCableTubeNb").val(ui.item[8]);
					$("#relatedCableTubeColor").val(ui.item[9]);
					tubeStrandSetColor("relatedCableStrandColor","relatedCableStrandNb");
					tubeStrandSetColor("relatedCableTubeColor","relatedCableTubeNb");
					return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[0] + '</span><br><span class="desc">' +
                   item[1] +', '+item[4] +', '+item[5] + '</span></div>').appendTo(ul);
		};
		$("#relatedCableStrandID").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
  	        }
		});
		
  ///////////////////////////////
$("#relatedCableStrandName").autocomplete({
	    	    source: function(request, response,event, ui) {
		    	     var sName =$("#relatedCableStrandName").val(); 
		    	  
				      searchId = sName;
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",		                 
		                 url: getContext()+'/SearchMappingStrand',
		                 data: {
							"searchId" : searchId,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.glist);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		        }, minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {
					
					$("#relatedCableStrandID").val(ui.item[0]);
					$("#relatedCableStrandName").val(ui.item[1]);
					$("#relatedCableTubeID").val(ui.item[2]);
					$("#relatedCableTubeName").val(ui.item[5]);
					$("#relatedCableID").val(ui.item[3]);
					$("#relatedCableName").val(ui.item[4]);
				
					$("#relatedCableStrandNb").val(ui.item[6]);
					$("#relatedCableStrandColor").val(ui.item[7]);
					$("#relatedCableTubeNb").val(ui.item[8]);
					$("#relatedCableTubeColor").val(ui.item[9]);
					tubeStrandSetColor("relatedCableStrandColor","relatedCableStrandNb");
					tubeStrandSetColor("relatedCableTubeColor","relatedCableTubeNb");
					
					return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[1] + '</span><br><span class="desc">' +
                   item[0] +', '+item[4] +', '+item[5] + '</span></div>').appendTo(ul);
		};
		$("#relatedCableStrandName").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
  	        }
		});
		$("#relatedCableID").autocomplete({
   	    source: function(request, response,event, ui) {
				 var fId= $("#relatedCableID").val();
				 var cId = $("#relatedCableTubeID").val();
				 var sId= $("#relatedCableStrandID").val();
		    	     if(sId != "" ){
			    	     searchId = sId;    	    
			    	 }else if(cId != ""){
			    	     searchId = cId;
				     }else{
			    		 searchId = fId;
				     }
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8", 
	                 url: getContext()+'/SearchFiber',
	                 data: {
						"searchId" : searchId,
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.glist);
	                         console.log(data.glist);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });      
	        }, minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {			
				$("#relatedCableID").val(ui.item[0]);
				$("#relatedCableName").val(ui.item[1]);
				return false;
			}
		}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
   	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
   			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
               item[0] + '</span><br><span class="desc">' +
               item[1] + '</span></div>').appendTo(ul);
	};
	$("#relatedCableID").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
///////////////////////////////
$("#relatedCableName").autocomplete({
   	    source: function(request, response,event, ui) {
				var fName= $("#relatedCableName").val();
				 var cId = $("#relatedCableTubeID").val();
				 var sId= $("#relatedCableStrandID").val();
		    	     if(sId != "" ){
			    	     searchId = sId;
			    	 }else if(cId != ""){
			    	     searchId = cId;
				     }else{
			    		 searchId = fName;
				     } 
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 
	                 url: getContext()+'/SearchFiber',
	                 data: {
						"searchId" : searchId,
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.glist);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });        
	        }, minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {
				$("#relatedCableID").val(ui.item[0]);
				$("#relatedCableName").val(ui.item[1]);
					return false;
			}
		}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
   	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
   			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
               item[1] + '</span><br><span class="desc">' +
               item[0] + '</span></div>').appendTo(ul);
	};
	$("#relatedCableName").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});

	$("#relatedCableTubeID").autocomplete({
		 source: function(request, response,event, ui) {
		     var cName =$("#relatedCableTubeID").val(); 
		     var sId= $("#relatedCableStrandID").val();
		     if(sId != ""){
	   	     searchId = sId;
	   	   //  url="SearchStrandTube";
	   	 }else{
	   		 searchId = cName;
	   		 //url="SearchStrandTube";
		     }
		     console.log("sid "+searchId );
	        $.ajax({
	            type: "GET",
	            contentType: "application/json; charset=utf-8",
	            
	            url: getContext()+'/SearchTube',
	            data: {
					"searchId" : searchId,
				 },
	            dataType: "json",
	            success: function (data) {
	                if (data != null) {
	                    response(data.clist);
	                }
	            },
	            error: function(result) {
	                alert("Error");
	            }
	        }); 
	   }, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {

			$("#relatedCableTubeID").val(ui.item[0]);
			$("#relatedCableTubeName").val(ui.item[1]);
			$("#relatedCableID").val(ui.item[2]);
			$("#relatedCableName").val(ui.item[3]);
			
			$("#relatedCableTubeNb").val(ui.item[4]);
			$("#relatedCableTubeColor").val(ui.item[5]);
			tubeStrandSetColor("relatedCableTubeColor","relatedCableTubeNb");
	
			return false;
					}
				}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
		    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	                   item[0] + '</span><br><span class="desc">' +
	                   item[1] +', ' +item[3]+ '</span></div>').appendTo(ul);
			};
	$("#relatedCableTubeID").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	  }
	});

$("#relatedCableTubeName").autocomplete({
   source: function(request, response,event, ui) {
	     var cName =$("#relatedCableTubeName").val(); 
	     var sId= $("#relatedCableStrandID").val();
	     if(sId != ""){
   	     searchId = sId;
   	   //  url="SearchStrandTube";
   	 }else{
   		 searchId = cName;
   		// url="SearchStrandTube";
	     }
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchTube',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   }, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		$("#relatedCableTubeID").val(ui.item[0]);
		$("#relatedCableTubeName").val(ui.item[1]);
		$("#relatedCableID").val(ui.item[2]);
		$("#relatedCableName").val(ui.item[3]);
		
		$("#relatedCableTubeNb").val(ui.item[4]);
		$("#relatedCableTubeColor").val(ui.item[5]);
		tubeStrandSetColor("relatedCableTubeColor","relatedCableTubeNb");
			
		return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[1] + '</span><br><span class="desc">' +
                   item[0] +', ' +item[3]+ '</span></div>').appendTo(ul);
		};
$("#relatedCableTubeName").focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
});

$("#relatedCableLocationID").autocomplete({
	source: function(request, response,event,ui) {
	    var searchs=$("#relatedCableLocationID").val();
	    var search ="";
		var line;
	  $('input:checkbox[class="relatedCableAutoComplete"]:checked').each(function () {
		var checkedCheckbox =  $(this).attr("id");
		search = checkedCheckbox.split("_");			
		search=search[0]; 	
	});
	
	$(".relatedCableAutoComplete").change(function() {
		var checkedCheckbox = this.id;
		search = checkedCheckbox.split("_");			
		search=search[0];
	});
		console.log("searchid\ "+search);
	
		if(search=="customer"){
				url='GetAllNetworkCustomer';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if(search=="site"){
				url='GetAllWarehouse';
				dataTarget = {
		       		"WareName":searchs,
					"warehouseName" : searchs,
					"SiteId":searchs,
				 }
			}
			else if(search=="manhole"){
				url ='getManholeData';
				dataTarget = {					
					"search":searchs,
				}			
			}
			else if (search=="handhole"){
				url ='getHandholeData';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if (search=="db"){
				url ='getDbData';
				dataTarget = {					
					"search":searchs,
				}
			}
		else {
			url='emptyUrl';
		}
	if(url !="emptyUrl") {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/'+url,
			data: dataTarget,				
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
		} 
	}, minLength:0, maxShowItems: 40, scroll:true,
 		select: function(event, ui) {
 			
			if(ui.item[0].split("_")[0]=="WARE"){
			$("#relatedCableLocationID").val(ui.item ? ui.item[2] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[5]);
				$("#relatedCableLocationType").val("Site");
				console.log("city "+ui.item[0])
			}
			else if(ui.item[0].split("_")[0]=="MH"){
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Manhole");
							
			}
			else if (ui.item[0].split("_")[0]=="HH"){
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Handhole");
			}
			
			else if (ui.item[0].split("_")[0]=="DB"){
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Distribution Board");
			}
			else{
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Customer");
			}						
		return false;
	},	
}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	
	if(item[0].split("_")[0]=="CUST" ) {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1] +', '+ item[2]+ "</span></div>")
            .appendTo(ul);
	}
	else if(item[0].split("_")[0]=="WARE") {
		 return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
           item[2] + "</span><br><span class='desc'>" +
            item[5] +', '+ item[1] + "</span></div>")
        .appendTo(ul);
	}
	else {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1]  +', '+ item[2] + "</span></div>")
            .appendTo(ul);
		}
	};		    	    
						         
	$("#relatedCableLocationID").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});	
	
$("#relatedCableLocationName").autocomplete({
	source: function(request, response,event,ui) {
	    var searchs=$("#relatedCableLocationName").val();
	    var search="";
		var line;
		$('input:checkbox[class="relatedCableAutoComplete"]:checked').each(function () {
		var checkedCheckbox =  $(this).attr("id");
		search = checkedCheckbox.split("_");			
		search=search[0]; 	
	});
	
	$(".relatedCableAutoComplete").change(function() {
		var checkedCheckbox = this.id;
		search = checkedCheckbox.split("_");			
		search=search[0];
	});
		console.log("search "+search);
		if(search=="customer"){
				url='GetAllNetworkCustomer';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if(search=="site"){
				url='GetAllWarehouse';
				dataTarget = {
		       		"WareName":searchs,
					"warehouseName" : searchs,
					"SiteId":searchs,
				 }
			}
			else if(search=="manhole"){
				url ='getManholeData';
				dataTarget = {					
					"search":searchs,
				}			
			}
			else if (search=="handhole"){
				url ='getHandholeData';
				dataTarget = {					
					"search":searchs,
				}
			}
			else if (search=="db"){
				url ='getDbData';
				dataTarget = {					
					"search":searchs,
				}
			}
		else {
			url='emptyUrl';
		}
	if(url !="emptyUrl") {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/'+url,
			data: dataTarget,				
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
		} 
	}, minLength:0, maxShowItems: 40, scroll:true,
 		select: function(event, ui) {
 			
			if(ui.item[0].split("_")[0]=="WARE"){
			$("#relatedCableLocationID").val(ui.item ? ui.item[2] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[5]);
				$("#relatedCableLocationType").val("Site");
				console.log("city "+ui.item[0])
			}
			else if(ui.item[0].split("_")[0]=="MH"){
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Manhole");
							
			}
			else if (ui.item[0].split("_")[0]=="HH"){
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Handhole");
			}
			
			else if (ui.item[0].split("_")[0]=="DB"){
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Distribution Board");
			}
			else{
				$("#relatedCableLocationID").val(ui.item ? ui.item[0] : '');
				$("#relatedCableLocationName").val(ui.item[1]);
				$("#relatedCableCity").val(ui.item[2]);
				$("#relatedCableLocationType").val("Customer");
			}			
		return false;
	},	
}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	
	if(item[0].split("_")[0]=="CUST" ) {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1] +', '+ item[2]+ "</span></div>")
            .appendTo(ul);
	}
	else if(item[0].split("_")[0]=="WARE") {
		 return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
           item[2] + "</span><br><span class='desc'>" +
            item[5] +', '+ item[1] + "</span></div>")
        .appendTo(ul);
	}
	else {
		 return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
               item[0] + "</span><br><span class='desc'>" +
                item[1]  +', '+ item[2] + "</span></div>")
            .appendTo(ul);
		}
	};		    	    
						         
	$("#relatedCableLocationName").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
	
$("#otherLastMileID").autocomplete({
   source: function(request, response,event, ui) {
	     var searchId =$("#otherLastMileID").val(); 
	    
	   
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchLastMile',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   }, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		$("#otherLastMileID").val(ui.item[0]);
		$("#otherLastMileName").val(ui.item[1]);
		
			
		return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[0] + '</span><br><span class="desc">' +
                   item[1] +'</span></div>').appendTo(ul);
		};
$("#otherLastMileID").focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
});

$("#otherLastMileName").autocomplete({
   source: function(request, response,event, ui) {
	     var searchId =$("#otherLastMileName").val(); 
	    
	   
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchLastMile',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   }, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		$("#otherLastMileID").val(ui.item[0]);
		$("#otherLastMileName").val(ui.item[1]);
		
			
		return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[1] + '</span><br><span class="desc">' +
                   item[0] +'</span></div>').appendTo(ul);
		};
$("#otherLastMileName").focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
});

/*
$("#lastmileJunctionID").autocomplete({
   source: function(request, response,event, ui) {
	     var searchId =$("#lastmileJunctionID").val(); 
	    
	   
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchJunction',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   }, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		$("#lastmileJunctionID").val(ui.item[0]);
		$("#lastmileJunctionName").val(ui.item[1]);
		
			
		return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[0] + '</span><br><span class="desc">' +
                   item[1] +'</span></div>').appendTo(ul);
		};
$("#lastmileJunctionID").focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
});


$("#lastmileJunctionName").autocomplete({
   source: function(request, response,event, ui) {
	     var searchId =$("#lastmileJunctionName").val(); 
	    
	   
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchJunction',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   }, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		$("#lastmileJunctionID").val(ui.item[0]);
		$("#lastmileJunctionName").val(ui.item[1]);
		
			
		return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[1] + '</span><br><span class="desc">' +
                   item[0] +'</span></div>').appendTo(ul);
		};
$("#lastmileJunctionName").focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
});  */

}

function autocompleteforAccessJunctions(ID){

$("#LastMileJunctionID"+ID).autocomplete({
   source: function(request, response,event, ui) {
	     var searchId =$("#LastMileJunctionID"+ID).val(); 
	    
	   
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchJunction',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   }, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		$("#LastMileJunctionID"+ID).val(ui.item[0]);
		$("#LastMileJunctionName"+ID).val(ui.item[1]);
		
			
		return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[0] + '</span><br><span class="desc">' +
                   item[1] +'</span></div>').appendTo(ul);
		};
$("#LastMileJunctionID"+ID).focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
});


$("#LastMileJunctionName"+ID).autocomplete({
   source: function(request, response,event, ui) {
	     var searchId =$("#LastMileJunctionName"+ID).val(); 
	    
	   
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            
            url: getContext()+'/SearchJunction',
            data: {
				"searchId" : searchId,
			 },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.clist);
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
      
   }, minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {
		$("#LastMileJunctionID"+ID).val(ui.item[0]);
		$("#LastMileJunctionName"+ID).val(ui.item[1]);
		
			
		return false;
				}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	    	return $('<li class="each"></li>').data( "ui-autocomplete-item", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                   item[1] + '</span><br><span class="desc">' +
                   item[0] +'</span></div>').appendTo(ul);
		};
$("#LastMileJunctionName"+ID).focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
  }
}); 
}

function mapOperation(){
	clearMapOperationFeilds();
	$( "#mapOperationModal" ).modal('show');
	uncheckAutoCompleteCheckboxes("mapOperationAutoComplete");
	$("#loading").remove();
	$("#Searchh").unbind();
	mapOperationAutoComplete("mapOperationAutoComplete","Searchh","Lng","Lat");
	}
function mapFeilds(){
	clearMapOperationFeilds();
	document.getElementById('placeSearch').style.display = "none";
	 document.getElementById('Searchh').style.display = "block";     
	}
function placeFeild(){
	clearMapOperationFeilds();
document.getElementById('placeSearch').style.display = "block";
document.getElementById('Searchh').style.display = "none";
placeAutoComplete();
} 
function clearMapOperationFeilds(){
	   document.getElementById('placeSearch').value = "";
		 document.getElementById('Searchh').value = "";
		 document.getElementById('Lat').value = "";
		 document.getElementById('Lng').value = "";
}