var deleting = false;
var layer_arr=[];
selectedFiberMode="";
var showPointsType="";
var showHidePointsArray =[];
var allTreePoints=[];

 $(document).on("triggerListenersEvent", function () {
$(function(){
	
	
	
viewNearestPointEvent();	
viewNearestMultyPointEvent();									
searchConnectedButtonEvents();

//$(document).ready(function (){

//    $(".menu-item").on('click',function(){
    $(".mapMenuItem").on('click',function(){
    	console.log("*****menu Id "+$(this).parent().attr('id'));
    	document.getElementById("mapMenu").classList.remove('show-menu');
/*    	
    	console.log("*****menu Id "+$(this).parent().attr('id'));
    	menu = $(this).parent().attr('id');
    	if(menu !=undefined)
    	document.getElementById(""+menu+"").classList.remove('show-menu');
    	*/
    });
    
    $(".mapSubMenuItem").on('click',function(){
    	console.log("*****menu Id "+$(this).parent().parent().parent().attr('id'));
    	document.getElementById("mapMenu").classList.remove('show-menu');
/*    	console.log("*****menu Id "+$(this).parent().attr('id'));
    	menu = $(this).parent().attr('id');
    	if(menu !=undefined)
    	document.getElementById(""+menu+"").classList.remove('show-menu'); */
    });
    
    $(".delPathMenuItem").on('click',function(){
    	console.log("*****menu Id "+$(this).parent().attr('id'));
    	document.getElementById("deletePathMenu").classList.remove('show-menu');
    });
    
   
    
    $("#deleteMan").click(function() {
		deletePhysicalLayers(layer_arr[0],layer_arr[1],layer_arr[2]);
		$("#DeleteModal").modal('hide');
	});

	 $("#deleteFiber").click(function() {
		deleteFiberCable(layer_arr[0],layer_arr[1],layer_arr[2]);
		$("#DeleteModal").modal('hide');
	});
	
	 $("#deleteTrench").click(function() {
		deleteTrenchPath(layer_arr[0],layer_arr[1],layer_arr[2]);
		console.log("para "+layer_arr[0]+"par2 "+layer_arr[1] +"par3 "+layer_arr[2])
		$("#deleteTrench").hide();
		$("#DeleteModal").modal('hide');
		
	});
    
    $("#deleteTermination").click(function() {
		$("#DeleteModal").modal('hide');
	});
    $('#getRelatedPoints'). click(function(){
    	if($(this). is(":checked")){
    		$(this).val('1');
    	}
    	else {
    		$(this).val('0');
    	}
    }); 
    $('#getRelatedPointsCon'). click(function(){
    	if($(this). is(":checked")){
    		$(this).val('1');
    	}
    	else {
    		$(this).val('0');
    	}
    }); 
    $('#getRelatedPointsFilter'). click(function(){
    	if($(this). is(":checked")){
    		$(this).val('1');
    	}
    	else {
    		$(this).val('0');
    	}
    });  
	$("#addManhole").on('click',function(){
		console.log("addd manholeee");
		document.getElementById("projectIdManhole").style.display = "block";
		document.getElementById("projectNameManhole").style.display = "block";
		document.getElementById("ManholeDMDiv").style.display = "none";

		var city = "";
		$('#manholeModal').find('input:text').val('');
		$("#manholeHeader").text("Manhole: ");
		$("#manholeModal").modal('show');
		actionManholeContext="Insert";

		geocoder = new google.maps.Geocoder();
		//IdNodeSelectedTemp="CurrentPhysicalLayer";
		var latlng = new google.maps.LatLng(getCoords().split(" ")[0], getCoords().split(" ")[1]);
		geocoder.geocode({'latLng': latlng}, function(results, status) {
		 if (status == google.maps.GeocoderStatus.OK) {
	   
			if (results[2]) {
			city = results[2].formatted_address;
			}
			else if (results[3]) {
			city = results[3].formatted_address;
			}
			else if (results[4]) {
			city = results[4].formatted_address;
			}
			else if (results[5]) {
			city = results[5].formatted_address;
			}
			else {
			  alert("No results found");
			}
			
		
		  } else {
			alert("getting the latitude, longitude and the city failed due to a connection problem, please try again");
		  }
		 
		$("#ManholeLat").val(""+getCoords().split(" ")[0]);		
		$("#ManholeLong").val(""+getCoords().split(" ")[1]);		
		$("#ManholeCity").val(city.split(", ")[0]);
		});

	});
	
	$("#addHandhole").on('click',function(){
		
		document.getElementById("projectIdHandhole").style.display = "block";
	 	document.getElementById("projectNameHandhole").style.display = "block";
		document.getElementById("HandholeDMDiv").style.display = "none";
		
		var city = "";
		$('#handholeModal').find('input:text').val('');
		$("#handholeHeader").text("Handhole: ");
		$("#handholeModal").modal('show');
		actionHandholeContext="Insert";	
		geocoder = new google.maps.Geocoder();
		//IdNodeSelectedTemp="CurrentPhysicalLayer";
		
		var latlng = new google.maps.LatLng(getCoords().split(" ")[0], getCoords().split(" ")[1]);
		geocoder.geocode({'latLng': latlng}, function(results, status) {
		 if (status == google.maps.GeocoderStatus.OK) {
	   
			if (results[2]) {
			city = results[2].formatted_address;
			}
			else if (results[3]) {
			city = results[3].formatted_address;
			}
			else if (results[4]) {
			city = results[4].formatted_address;
			}
			else if (results[5]) {
			city = results[5].formatted_address;
			}
			else {
			  alert("No results found");
			}
		  } else {
			alert("getting the latitude, longitude and the city failed due to a connection problem, please try again");
		  }
		  $("#HandholeLat").val(""+getCoords().split(" ")[0]);
			$("#HandholeLong").val(""+getCoords().split(" ")[1]);
			$("#HandholeCity").val(city.split(", ")[0]);
		});

	});

	$("#addDistributionBoard").on('click',function(){
		document.getElementById("projectIdDB").style.display = "block";
	 	document.getElementById("projectNameDB").style.display = "block";
		$("#DistributionBoardheader").text("Distribution Board: ");
		$('#distributionBoardModal').find('input:text').val('');
		$("#distributionBoardModal").modal('show');						
		$("#DbMappingTable > tbody").empty();
		$(".nav-link").removeClass('active');
		$("#distBoard-tab").addClass('active');
		geocoder = new google.maps.Geocoder();
		actiondistBoardContext="Insert";
		
		//
		$("#site_DBAutoComplete").prop("checked",true);
		$('#site_DBAutoComplete').val('1');
		document.getElementById("client_DBAutoComplete").checked = false;
		$('#client_DBAutoComplete').val('0');
		document.getElementById("DBClientId").style.display = "none";
		document.getElementById("DBClientName").style.display = "none";
		document.getElementById("BDClientPhoneNb").style.display = "none";
		document.getElementById("BDWarehouse").style.display = "block";
		document.getElementById("DBSite").style.display = "block";
		document.getElementById("DBSiteName").style.display = "block";
		//

	var latlng = new google.maps.LatLng(getCoords().split(" ")[0], getCoords().split(" ")[1]);

		geocoder.geocode({'latLng': latlng}, function(results, status) {
	if (status == google.maps.GeocoderStatus.OK) {

		if (results[2]) {
		city = results[2].formatted_address;
		}
		else if (results[3]) {
		city = results[3].formatted_address;
		}
		else if (results[4]) {
		city = results[4].formatted_address;
		}
		else if (results[5]) {
		city = results[5].formatted_address;
		}
		else {
		alert("No results found");
		}
	} else {
		alert("getting the latitude, longitude and the city failed due to a connection problem, please try again");
	}
	$("#DistributionBoardLat").val(""+getCoords().split(" ")[0]);
		$("#DistributionBoardLong").val(""+getCoords().split(" ")[1]);
		$("#boardCity").val(city.split(", ")[0]);
		
	});
		$(".tab-pane").removeClass('active');
		$("#D_Board").addClass('active');
	});
	
	$("#selectCable").on('click', function () {
		//console.log("select Cablee is "+selectedPath +" and id "+selectedPathId);
		listenerPathDelete = map.addListener('click', deleteAuxPath);
		deleting = true;
		
	});
	$("#approveDelete").on('click', function (e) {
		console.log("approveDelete selected Cablee is "+selectedPathId);
		console.log("MarkerArrayId "+MarkerArrayId);
		
		 $.ajax({
	         type: "GET",
	         contentType: "application/json; charset=utf-8",
	         url: getContext()+'/DeleteFiberPathAux',
	         data: {
	        	 "markersArray": MarkerArrayId,
	        	 "fiberpathID" : selectedPathId
	         },
	         dataType: "json",
	         success: function (data) {
	        	 //console.log("selected Cablee mapPoints beforee "+window["mapPoints_"+selectedPathId]);
	        	 //console.log("array of marker position "+selectedMarkersPos);
	        	 
	        	 if(PathsArray.length > 0){
	        		// console.log("delete arraay "); 
	         		for(d=0;d<selectedMarkersPos.length;d++){
	         			 var selectedMarkersId = selectedMarkersPos[d].Id;
	         			 for (var i = 0; i < window["mapPoints_"+selectedPathId].length; i++) { 
	     					 //console.log("WINDOW " +window["mapPoints_"+selectedPathId][i]);
	     					 let longLatStr =  String(window["mapPoints_"+selectedPathId][i]);
	     					 longLatStr = longLatStr.substring(1,longLatStr.length -1).split(", ");
	     					 //console.log("enter "+longLatStr);
	     				    //console.log("enter window "+longLatStr[0]+" and "+longLatStr[1]);
	     					//console.log("enter pos "+selectedMarkersPos[d].lat+" and "+selectedMarkersPos[d].long);
	     					 if (longLatStr[0] === String(selectedMarkersPos[d].lat) && longLatStr[1] === String(selectedMarkersPos[d].long)) { 
	     						 console.log("EQUAL ");
	     						 window["mapPoints_"+selectedPathId].splice(i, 1);
	     					     break;
	     					  } 
	     				}
	     			}
	        	 }
	        	 
	        	 eventDelete = 0;
	        	 MarkerArrayId = [];
	        	 selectedMarkersPos = [];
	        	 MarkersArray = [];
	        	 PathsArray = [];
	        	 selectedMarkers = [];
	        	 deleting=false;
	        	 for(i=0;i<MarkerAuxArray.length;i++){
	 				if(MarkerAuxArray.length>0){
	 					MarkerAuxArray[i].setMap(null);
	 					
	 				}
	 			}
	        	 alert(MarkerAuxArray.length+" points have been deleted");
	        	 MarkerAuxArray = [];
	        	 if(typeof listenerPathDelete !== 'undefined'){
	        		google.maps.event.removeListener(listenerPathDelete); 									
	        	 } 
	        	 
	        	
	        	 fiberArray[selectedPathId].setMap(null);
	        	 buildPath(selectedPathId,window["mapPoints_"+selectedPathId],fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+selectedPathId][22]],0.7,4.5,'blue',13);
	     		 fiberArray[selectedPathId].setMap(map);	
	     		 //console.log("selected Cablee mapPoints after "+window["mapPoints_"+selectedPathId]);
	     		
	         },
	         error: function(result) {
	             alert("Error");
	         }
	     });
		 
		
	});
	//cancel from delete menu path
	$("#cancelDeletePathMenu").on('click', function () {
		
		 if(typeof listenerPathDelete !== 'undefined'){
     		google.maps.event.removeListener(listenerPathDelete); 									
     	 } 
		deleting = false;
		for(i=0;i<MarkerAuxArray.length;i++){
				if(MarkerAuxArray.length>0){
					MarkerAuxArray[i].setMap(null);
					
				}
	    }
		
	});

$('.fiberMode').on('click', function() {
  // in the handler, 'this' refers to the box clicked on
  var $choice = $(this);
  if ($choice.is(":checked")) {
    $('.fiberMode').prop("checked", false);
    $choice.prop("checked", true);
    selectedFiberMode=$(this).attr('name');
    console.log(selectedFiberMode);
  }
});		

$("#viewFiberPath").on('click', function (e) {
	 $.ajax({
         type: "GET",
         contentType: "application/json; charset=utf-8",
         url: getContext()+'/fiberPathSrcToDst',
         data: {},
         dataType: "json",
         success: function (data) {
             if (data.fiberPathSrcDst != null) {
            	  fetchSearchedFiberPath(data.fiberPathSrcDst); 	                  
             }
             else
                 alert("No fiber cables were found!");
         },
         error: function(result) {
             alert("Error");
         }
     });
});



var tabhref = "custom-tabs-FindACable";
$('#fiberCitySearch a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	  var target = $(e.target).attr("href") // activated tab
	  tabhref = target;
	});

$("#open-popup-btn").click(function() {
	//$('a[href="#custom-tabs-FindACable"]').click();
	$(' a[href="#' + tabhref + '"]').click();
   	$("#fiberCitySearch").modal('show');	 	
	   
});
	  	  
$("#selectHeaderSearch").on('change',function(){ 
		$("#autoCompleteHeaderSearch").val("");
});
		      	
$("#autoCompleteConnectedSearch").on('focusout',function(){
				    	
				      	 var ss=$("#autoCompleteConnectedSearch").val();
				      	 
			      		$("#autoCompleteHeaderSearch").val(ss);
			      		$("#headerSearchLong").val($("#connectedSearchLong").val());
				      	$("#headerSearchLat").val($("#connectedSearchLat").val());


				      	console.log(""+$("#autoCompleteHeaderSearch").val());
				      	console.log(""+$("#headerSearchLong").val());
				    	console.log(""+$("#headerSearchLat").val());
				      	});
			      	
$("#selectConnectedSearch").on('change',function(){ 
			      		$("#selectHeaderSearch").val($("#selectConnectedSearch").val());
			      		$("#autoCompleteConnectedSearch").val("");
			      		
				      	});


	

	$("#getCoordinatePoint").on('click',function(){
		
		window["getCoorPointLong"]=getCoords().split(" ")[1];
		window["getCoorPointLat"]=getCoords().split(" ")[0];

		console.log("getCoorPointLong"+window["getCoorPointLong"]);
		console.log("getCoorPointLat"+window["getCoorPointLat"]);

		});
		
		$("#setCoordBtn").on('click',function(){
		
		$("#closestLongPoint").val(window["getCoorPointLong"]);
		$("#closestLatPoint").val(window["getCoorPointLat"]);

		console.log("closestLongPoint"+$("#closestLongPoint").val());
		console.log("closestLatPoint"+$("#closestLatPoint").val());

		});
		$("#setStartPointBtn").on('click',function(){
			$("#startLongPoint").val(window["getCoorPointLong"]);
			$("#startLatPoint").val(window["getCoorPointLat"]);
		});
		
		$("#setEndPointBtn").on('click',function(){
			$("#endLongPoint").val(window["getCoorPointLong"]);
			$("#endLatPoint").val(window["getCoorPointLat"]);
			
			/*var temp = "";
			if($("#endLatPoint").val() != "" &&  $("#startLatPoint").val() != ""){
				if(Number(parseFloat($("#endLatPoint").val())) > Number(parseFloat($("#startLatPoint").val()))){
					console.log("grreater ");
					temp = $("#startLatPoint").val();
					$("#startLatPoint").val($("#endLatPoint").val());
					$("#endLatPoint").val(temp);
				}
                if(Number(parseFloat($("#endLongPoint").val())) > Number(parseFloat($("#startLongPoint").val()))){
                	temp = $("#startLongPoint").val();
					$("#startLongPoint").val($("#endLongPoint").val());
					$("#endLongPoint").val(temp);
				}
			
			}*/
		});
		
$("#setCoordinateFiberAux").on('click',function(){
	var checkedRow;
	
	if($("#auxiliaryTable input[type=checkbox]:checked").length>1) {
		alert("Please check only one row to set coordinate.")
	}
	else {
	 $("#auxiliaryTable input[type=checkbox]:checked").each(function(){
         checkedRow = $(this).closest("tr")[0].rowIndex ;
     });
     $("#auxiliaryTable >tbody").find("tr").eq(checkedRow-1).find('td[name="auxiliary_Longitude"]').children('input').val(window["getCoorPointLong"]);
     $("#auxiliaryTable >tbody").find("tr").eq(checkedRow-1).find('td[name="auxiliary_Latitude"]').children('input').val(window["getCoorPointLat"]);
	       
	calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
	}
});

$("#setCoordinateStrandAux").on('click',function(){
	
	  $('.rowAboveBelowStrand').each(function() {
	        if (this.checked) {
		        var rowIndex = $(this).closest('tr');
				var currentIndex = rowIndex.index();
				
				$(this).parents("tr").find('input[name ="auxStrand_Lat"]').val(window["getCoorPointLat"]); 
				$(this).parents("tr").find('input[name ="auxStrand_Long"]').val(window["getCoorPointLong"]); 
				
	        }
	calculateDistanceSourceDestination($("#sourcelatstrand").val(),$("#sourcelongstrand").val(),$("#destinationlatstrand").val(),$("#destinationlongstrand").val(),"auxiliaryTableStrands");
	
});
});
$("#setCoordinateTrenchAux").on('click',function(){
	
	  $('.rowAboveBelowTrench').each(function() {
	        if (this.checked) {
		        var rowIndex = $(this).closest('tr');
				var currentIndex = rowIndex.index();
				
				$(this).parents("tr").find('input[name ="aux_Lattrench"]').val(window["getCoorPointLat"]); 
				$(this).parents("tr").find('input[name ="aux_Longtrench"]').val(window["getCoorPointLong"]); 
				
	        }
    	calculateDistanceSourceDestination($("#SourceTrenchLat").val(),$("#SourceTrenchLng").val(),$("#DestinationTrenchLat").val(),$("#DestinationTrenchLng").val(),"auxiliary_trenchTable");
	
});
});
$("#setCoordinateDuctAux").on('click',function(){
	
	  $('.rowAboveBelowDuct').each(function() {
	        if (this.checked) {
		        var rowIndex = $(this).closest('tr');
				var currentIndex = rowIndex.index();
				
				$(this).parents("tr").find('input[name ="aux_Latduct"]').val(window["getCoorPointLat"]); 
				$(this).parents("tr").find('input[name ="aux_Longduct"]').val(window["getCoorPointLong"]); 
				
	        }
    	calculateDistanceSourceDestination($("#SourceDuctLat").val(),$("#SourceDuctLng").val(),$("#DestinationDuctLat").val(),$("#DestinationDuctLng").val(),"auxiliary_ductTable");
	
});
});
$("#setCoordinateTubeAux").on('click',function(){
	
	  $('.rowAboveBelowTube').each(function() {
	        if (this.checked) {
		        var rowIndex = $(this).closest('tr');
				var currentIndex = rowIndex.index();
				
				$(this).parents("tr").find('input[name ="auxTube_Lat"]').val(window["getCoorPointLat"]); 
				$(this).parents("tr").find('input[name ="auxTube_Long"]').val(window["getCoorPointLong"]); 
				
	        }
		calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");
	
});
});

			autoCompleteSearchHeader('autoCompleteHeaderSearch','selectHeaderSearch','headerSearchLong','headerSearchLat');
			autoCompleteSearchHeader('autoCompleteConnectedSearch','selectConnectedSearch','connectedSearchLong','connectedSearchLat');


});
});
 
////>>>>>>>>>>>> function for tree --> map clicks events of <<<Manholes>>>  /////		 
	
 selectedMarkers = [];
 IdSelectedTemp="";
 IdSelectedTempHandhole="";	
 
 

/*	
function createManhole_Marker_Click(Id,Name,Long,Lat,markersManhole,markerClusterManhole,Type){
	
	const pos = new google.maps.LatLng(Lat,Long);
	var data="<div>" +Name+ "</div>";
	var iconMan;
 if(Type == "No_Junction"){
	 iconMan = iconManhole;
 }
 else {
	 iconMan = iconManholeJct; 
 }
 
 if(!markersManhole[Id]){
	markerManhole = new google.maps.Marker({
		position: pos,
		data: data,
		icon: iconMan,
		ID: Id,
	});
	markerManhole.metadata = { id: Id };
	markersManhole[Id] = markerManhole;
	markersManhole.push(markerManhole);
			 
	google.maps.event.addListener(markerManhole, "click", function (e) {
		var IdSelected=this.ID;
		nodeFileId = $("#"+IdSelected).parents().eq(3).attr("id").split("initial_ul_")[1];
		var projectInitial=$("#initial_ul_Projects").find('>ul > #'+nodeFileId);
		var projectRel=$("#"+nodeFileId+"").find('>ul > #initial_ul_'+nodeFileId);
		var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
		var children = $("#Manhole_f_"+nodeFileId+"").find(' > ul > li');
		
		if(IdSelected!=IdSelectedTemp){	
			if(IdSelectedTemp!=""){
				$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
				$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
			}
			//appendManholeBoq(nodeFileId,Id,ManholeSel,text,this.position.lng(),this.position.lat(), ManholeCity,DMName);
			IdSelectedTemp=IdSelected;							
		}
		childrenInitial.show('fast');
		if(!children.is(":visible")){
			children.show();
		}
		projectInitial.show('fast');
		projectRel.show('fast');

		$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
		$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");

		$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		$("#initial_ul_Projects > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		$("#"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
		$("#Manhole_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

		offset=$("#"+IdSelected).offset().top;
		projectOffset=$("#initial_ul_"+nodeFileId).offset().top;
		offsetTotal=(offset-projectOffset);
		$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
		if(draw==true){
			createPathh(e);
		}

		if(deleting == true){ 
			deleteAuxPath(e);
			MarkerArrayId.push(Id+":"+Name);
		}	
	
	 });
	}

	else{
		if(markersManhole[Id].map!=map){
			markersManhole[Id].setMap(map);
		}
		markersManhole[Id].setPosition(pos);
		markersManhole[Id].data=data;
	}
	
$("#"+Id+" > .TreeSpan").on('click', function () {
	
	checkedChildrenLayer="";
	//showBoq();
	 if($(this).parents().eq(4).attr('id') == "initial_ul_CurrentPhysicalLayer"){
		   nodeFileId = "CurrentPhysicalLayer";
	  }
	  else if($(this).parents().eq(4).attr('id').split("_", 3) == "initial,ul,PROJECT"){
		   nodeFileParentId = $(this).parents().eq(4).attr('id').split("initial_ul_");
		   nodeFileId = nodeFileParentId[1];
	 }
	//appendManholeBoq(nodeFileId,Id,ManholeSel,text,Long,Lat,ManholeCity,DMName);

	if(markersTempoHandhole){
		$("#"+markersTempoHandhole+"> span>img").attr('src', getContext() + "/resources/NetworkImages/handholeYellow.png");
		markersHandhole[markersTempoHandhole].setIcon(iconHandhole);							
	}
	else if(markersTempoHandholeJct){
		$("#"+markersTempoHandholeJct+"> span>img").attr('src', getContext() + "/resources/NetworkImages/handholeYellow.png");
		markersHandhole[markersTempoHandholeJct].setIcon(iconHandholeJct);			
	}
	if(markersTempoDistBoard){
		$("#"+markersTempoDistBoard+"> span>img").attr('src', getContext() + "/resources/NetworkImages/electrical-panel.png");
		markersDistBoard[markersTempoDistBoard].setIcon(iconDistBoard);			
	}
	markersTempoHandhole="";// to clear the temp variable holding the latest clicked manhole
	markersTempoDistBoard="";
	markersTempoHandholeJct="";
	var selectedManhole=$(this).parent().attr('id');

 if(Type=="No_Junction"){						
	if(selectedManhole!=markersTempo){
		markersManhole[selectedManhole].setIcon(iconManhole);
		$("#"+selectedManhole+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
		//const pos = new google.maps.LatLng(Lat,Long);
		if(markersTempo!=""){
			markersManhole[markersTempo].setIcon(iconManhole);
			$("#"+markersTempo+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
		}
					
		markersTempo=selectedManhole;
		panTo(markersManhole[selectedManhole].getPosition().lat(), markersManhole[selectedManhole].getPosition().lng());
					
		}
	}
 else {
	 if(selectedManhole!=markersJctTempo){
		
		 markersManhole[selectedManhole].setIcon(iconManholeJct);						
		$("#"+selectedManhole+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
		const pos = new google.maps.LatLng(Lat,Long);
			
		if(markersJctTempo!=""){			
			markersManhole[markersJctTempo].setIcon(iconManholeJct);
			$("#"+markersJctTempo+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
		}
		markersJctTempo=selectedManhole;
		panTo(markersManhole[selectedManhole].getPosition().lat(), markersManhole[selectedManhole].getPosition().lng());
	}
 }
		map.setZoom(11);
		if(typeof infowindow!== 'undefined'){
			//console.log("info window defined and will be closed");
			infowindow.close();
		}
		else{
			infowindow = new google.maps.InfoWindow();
			//console.log("info window not defined");
		}
		//console.log("info window defined and appending data");

		infowindow.setContent(markersManhole[selectedManhole].data);
		infowindow.open(map,markersManhole[selectedManhole]);
				
	
		});
}

	
	
////>>>>>>>>>>>> function for tree --> map clicks events of <<<handholes>>>  /////		 
IdSelectedTempHandhole="";	
function createHandhole_Marker_Click(Id,Name,Long,Lat,markersHandhole,markerClusterHandhole,Type){

	window["Boq"+Id]=[];
	const pos = new google.maps.LatLng(Lat,Long);
	var data="<div>" +Name+ "</div>";
	var iconHand;
	
	if(Type == "No_Junction"){
		 iconHand = iconHandhole;
	 }
	 else {
		 iconHand = iconHandholeJct; 
	 }
	if(!markersHandhole[Id]){

		markerHandhole = new google.maps.Marker({
			position: pos,
			data:data,
			icon:iconHand,
			ID:Id,
		});
		markerHandhole.metadata = { id: Id };
		markersHandhole[Id] = markerHandhole;
		markersHandhole.push(markerHandhole);

		google.maps.event.addListener(markerHandhole, "click", function (e) {
			var IdSelected=this.ID;
			nodeFileId=$("#"+IdSelected).parents().eq(3).attr('id').split("initial_ul_")[1];
			var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
			var projectInitial=$("#initial_ul_Projects").find('>ul > #'+nodeFileId);
			var projectRel=$("#"+nodeFileId+"").find('>ul > #initial_ul_'+nodeFileId);
			var children =  $("#Handhole_f_"+nodeFileId+"").find(' > ul > li');

		   	if(IdSelected!=IdSelectedTemp){				
				if(IdSelectedTemp!=""){
					$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
					$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
				}
				//showBoq();									  
				//appendHandholeBoq(nodeFileId,IdSelected,HandholeSel,text,this.position.lng(),this.position.lat(), city, DMName);
				IdSelectedTemp=IdSelected;
		   	}
		   	if(!children.is(":visible")){
				children.show();
			}
			childrenInitial.show('fast');
			projectInitial.show('fast');
			projectRel.show('fast');
			
			$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
			
			$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#initial_ul_Projects > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
			$("#Handhole_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

			offset=$("#"+IdSelected).offset().top;
			projectOffset=$("#initial_ul_"+nodeFileId).offset().top;
			offsetTotal=(offset-projectOffset);
			$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
			
			if(draw==true){
					createPathh(e);
				}
			

			if(deleting == true){ 
				deleteAuxPath(e);
				MarkerArrayId.push(Id+":"+Name);
			}	
		
			});
		}
		else{
			if(markersHandhole[Id].map!=map){
				markersHandhole[Id].setMap(map);
			}
			markersHandhole[Id].setPosition(pos);
			markersHandhole[Id].data=data;
	}
	
	$("#"+Id+" > .TreeSpan").bind('click', function (e) {
		checkedChildrenLayer="";
		//showBoq();
		 if($(this).parents().eq(4).attr('id') == "initial_ul_CurrentPhysicalLayer"){
			   nodeFileId = "CurrentPhysicalLayer";
		   }
		   else if($(this).parents().eq(4).attr('id').split("_", 3) == "initial,ul,PROJECT"){
			   nodeFileParentId = $(this).parents().eq(4).attr('id').split("initial_ul_");
			   nodeFileId = nodeFileParentId[1];			   
		   }
		 //appendHandholeBoq(nodeFileId,Id,HanholeSel,text,Long,Lat, city, DMName);		
		if(markersTempo){
			$("#"+markersTempo+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
			markersManhole[markersTempo].setIcon(iconManhole);			
		}
		if(markersJctTempo){
			$("#"+markersJctTempo+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
			markersManhole[markersJctTempo].setIcon(iconManholeJct);			
		}
		if(markersTempoDistBoard){
			$("#"+markersTempoDistBoard+"> span>img").attr('src', getContext()+"/resources/NetworkImages/electrical-panel.png"); 
			markersDistBoard[markersTempoDistBoard].setIcon(iconDistBoard);			
		}
		markersTempoDistBoard="";
		markersTempo="";// to clear the temp variable holding the latest clicked manhole
		markersJctTempo="";
		var selectedHandhole=$(this).parent().attr('id');

	if(Type=="No_Junction"){						
		if(selectedHandhole!=markersTempoHandhole){
			markersHandhole[selectedHandhole].setIcon(iconHandhole);				
			$("#"+selectedHandhole+"> span>img").attr('src', getContext() + "/resources/NetworkImages/handholeYellow.png");

		//const pos = new google.maps.LatLng(latHandhole,lngHandhole);
		if(markersTempoHandhole!=""){
			markersHandhole[markersTempoHandhole].setIcon(iconHandhole);					
			$("#"+markersTempoHandhole+"> span>img").attr('src', getContext() + "/resources/NetworkImages/handholeYellow.png");
						
		}
		markersTempoHandhole=selectedHandhole;
		panTo(markersHandhole[selectedHandhole].getPosition().lat(), markersHandhole[selectedHandhole].getPosition().lng());
				
		}
	}
	else {
		if(selectedHandhole!=markersTempoHandholeJct){
			markersHandhole[selectedHandhole].setIcon(iconHandholeJct);
			$("#"+selectedHandhole+"> span>img").attr('src', getContext() + "/resources/NetworkImages/handholeYellow.png");
		
		if(markersTempoHandholeJct!=""){
			markersHandhole[markersTempoHandholeJct].setIcon(iconHandholeJct);
			$("#"+markersTempoHandholeJct+"> span>img").attr('src', getContext() + "/resources/NetworkImages/handholeYellow.png");
		}
		markersTempoHandholeJct=selectedHandhole;
		panTo(markersHandhole[selectedHandhole].getPosition().lat(), markersHandhole[selectedHandhole].getPosition().lng());
		
		}
	}
	
	map.setZoom(11);
	if(typeof infowindow!== 'undefined'){

		infowindow.close();
		
	}
	else{
		infowindow = new google.maps.InfoWindow();
	}

	infowindow.setContent(markersHandhole[selectedHandhole].data);
	infowindow.open(map,markersHandhole[selectedHandhole]);
	});
}
	

	////>>>>>>>>>>>> end of function tree --> map clicks events of <<<handholes>>>  /////		
		
	
	
	
	////>>>>>>>>>>>> function for tree --> map clicks events of <<< Distribution Boards >>>  /////		 
	function createDistBoard_Marker_Click(Id,Name,Long,Lat,markersDistBoard,markerClusterDistBoard, city){
	
		markerId=Id;		
		
		window["Boq"+Id]=[];
		const pos = new google.maps.LatLng(Lat,Long);
		
		var DistBoardName=Name;				         
		
		var data="<div>" +DistBoardName+ "</div>";
		if(!markersDistBoard[markerId]){

			markerDistBoard = new google.maps.Marker({
				position: pos,
				data:data,
				icon:iconDistBoard,
				ID:markerId
						 });
		markerDistBoard.metadata = { id: markerId };
		markersDistBoard[markerId] = markerDistBoard;
		markersDistBoard.push(markerDistBoard);

		markersDistBoardFilter[markerId] = markerDistBoard;
		markersDistBoardFilter.push(markerDistBoard);

		//markerClusterDistBoard.addMarker(markerDistBoard);

		google.maps.event.addListener(markersDistBoard[markerId], "click", function (e) {
				
			var IdSelected=this.ID;
			nodeFileId=$("#"+IdSelected).parents().eq(3).attr('id').split("initial_ul_")[1];
			var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
			var projectInitial=$("#initial_ul_Projects").find('>ul > #'+nodeFileId);
			var projectRel=$("#"+nodeFileId+"").find('>ul > #initial_ul_'+nodeFileId);
			var children =  $("#DistributionBoard_f_"+nodeFileId+"").find(' > ul > li');

			if(IdSelected!=IdSelectedTemp){

				   $("ul").find("li").removeClass("selected-span");

				   if(IdSelectedTemp!=""){
							 
							var childrenTemp = $("#initial_ul_"+nodeFileId+"").find("#"+IdSelectedTemp);

							$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
							$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
						
						  }
			  
				var text=$("#"+IdSelected).text().trim();									
				var DistBoardSel=IdSelected+":"+text;
								
				IdSelectedTemp=IdSelected;
				}
				if(!children.is(":visible")){
					children.show();
				}
			   childrenInitial.show('fast');
			   projectInitial.show('fast');
			   projectRel.show('fast');
			   $("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			   $("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");

			   $("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			   $("#initial_ul_Projects > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			   $("#"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			   $("#DistributionBoard_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

				offset=$("#"+IdSelected).offset().top;
							
				offset1=$("#DistributionBoard_f_"+nodeFileId).offset().top;
					
				offset2=$("#initial_ul_CurrentPhysicalLayer").offset().top;
				offsettot=(offset-offset2);
				
				$("#network_tree").animate({ scrollTop: offsettot}, "slow");

				if(draw==true){

					createPathh(e);
				}

				if(deleting == true){ 
					deleteAuxPath(e);
					MarkerArrayId.push(Id+":"+Name);
				}	
			
			});

		}

		else{
			if(markersDistBoard[markerId].map!=map){
				markersDistBoard[markerId].setMap(map);
			}
			markersDistBoard[markerId].setPosition(pos);
			markersDistBoard[markerId].data=data;
	
		}
		
		markersTempoDistBoard="";
		
		$("#"+Id+" > .TreeSpan").bind('click', function (e) {
			checkedChildrenLayer="";
			var DistBoardSel=Id+":"+$(this).text().trim();
			var text=$(this).text();
			console.log("inside createDistBoard_Marker_Click");
			
			if(markersTempo){
				
				$("#"+markersTempo+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
				markersManhole[markersTempo].setIcon(iconManhole);			
				
			}
			if(markersJctTempo){
				
				$("#"+markersJctTempo+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
				markersManhole[markersJctTempo].setIcon(iconManholeJct);			
				
			}
	
			if(markersTempoHandhole){
				
				$("#"+markersTempoHandhole+"> span>img").attr('src', getContext()+"/resources/NetworkImages/handholeYellow.png");
				markersHandhole[markersTempoHandhole].setIcon(iconHandhole);			
				
			}
				if(markersTempoHandholeJct){
				
				$("#"+markersTempoHandholeJct+"> span>img").attr('src', getContext()+"/resources/NetworkImages/handholeYellow.png");
				markersHandhole[markersTempoHandholeJct].setIcon(iconHandholeJct);			
				
			}
			markersTempoHandhole="";// to clear the temp variable holding the latest clicked manhole
			markersTempo="";
			markersTempoHandholeJct="";
			markersJctTempo="";
			var selectedDistBoard=$(this).parent().attr('id');
			//console.log("selectedDistBoard "+selectedDistBoard);
			//console.log("markersTempoDistBoard "+markersTempoDistBoard);
			var selMarker="";
			markerId=selectedDistBoard;				
			selMarker=markersDistBoard[markerId];
			
				if(selectedDistBoard!=markersTempoDistBoard)
					{
				
					selMarker.setIcon(iconDistBoard);
					//markersDistBoard[selectedDistBoard].setIcon(iconDistBoard);

					$("#"+selectedDistBoard+"> span>img").attr('src', getContext() + "/resources/NetworkImages/electrical-panel.png"); 
					
					
					if(markersTempoDistBoard!="")
						{
							 var otherMarkers=markersDistBoard[markersTempoDistBoard];			
							otherMarkers.setIcon(iconDistBoard);
							$("#"+markersTempoDistBoard+"> span>img").attr('src', getContext() + "/resources/NetworkImages/electrical-panel.png"); 
							
						}
						
					markersTempoDistBoard=selectedDistBoard;
					panTo(selMarker.getPosition().lat(), selMarker.getPosition().lng());
				}
				map.setZoom(11);
					if(typeof infowindow!== 'undefined'){
						console.log("info window defined and will be closed");

						infowindow.close();
						
					}
					else{
						infowindow = new google.maps.InfoWindow();
						console.log("info window not defined");
					}
					console.log("info window defined and appending data");

					infowindow.setContent(selMarker.data);
					infowindow.open(map,selMarker);
	
				
	
		});
	
		
	}

////>>>>>>>>>>>> end of function tree --> map clicks events of <<< Distribution Boards >>>  /////
	
*/	
	
//funtion to show DB for fiber,tube and strands from conectx menu	
	function showDB(selectedContext,target,name){
		 $("#DB").empty();// clear div
		 markerClusterDistBoard.clearMarkers(); // clear map
		 opentab(event, 'DB');
		 $("#distributionBoardBtn").removeClass("tablinks").addClass("tablinks active");
			$.ajax({
				type: "GET",
				async: false,
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/showDBs',
				data: {
					"selectedContext":selectedContext,
					"target": target
				},									
				dataType: "json",
				success: function (data) {	
					//console.log("entered "+ data.DBData);
					//console.log("entered target "+ target);
					var Cablename = "<tr>"+"<td><b> "+target+" Name:  </b>"+name+" / "+selectedContext+"</td></tr>";
					$("#DB").append(Cablename); 
					var distributionBoardList = data.DBData ;
					var stringDiv="<ul style='list-style-type:none;padding:0px;cursor: pointer;'><li id='DistributionBoard_f_showDB' ' class='DistributionBoard_f_DB'><input type='checkbox' checked class='AllDistBoards checkFilter'></input> <span id='DistribBoard_spanFolder'  class='Parentfolder'><i class='fa fa-folder' style='color: #08526D'></i></span><span id='DistribBoard_span' class='TreeSpan' style='color:black;width:395px;margin-left:15px;'>Distribution Board </span></li></ul>";
					$("#DB").append(stringDiv); 
					 
					if(distributionBoardList!=null){
						//console.log("entered if "+ distributionBoardList);
						$("#distBoardCheckAllBoq").prop("checked",true);
						MouseHoveringSpans("#DistributionBoard_f_showDB .TreeSpan");
						tree_prop_selection("#DistributionBoard_f_showDB .TreeSpan");
						treeCollapseFolder("#DistributionBoard_f_showDB .Parentfolder",null,".Parentfolder");
								for(i=0;i<distributionBoardList.length;i++){
									stringDiv="<ul style='list-style-type:none;cursor: pointer;'><li id='"+distributionBoardList[i][0]+"_showDB' style='display:none;' class='DistributionBoard'><input type='checkbox' class='DistBoard checkFilter' checked name='Element' ></input> <span class='TreeSpan' style='color:black;width:355px;margin-left:10px;'><img class='image' src='"+getContext()+"/resources/NetworkImages/electrical-panel.png'> "+distributionBoardList[i][3]+" </span></li></ul>";
									$("#DistributionBoard_f_showDB").append(stringDiv);
									
									MouseHoveringSpans("#" +distributionBoardList[i][0]+"_showDB .TreeSpan");
									tree_prop_selection("#"+distributionBoardList[i][0]+"_showDB .TreeSpan");
									create_Marker_Click(distributionBoardList[i][0]+"_showDB",distributionBoardList[i][3],distributionBoardList[i][1],distributionBoardList[i][2],markersDistBoard,markerClusterDistBoard,"",distributionBoardList[i][7]);
									//markerClusterDistBoard.addMarker(markersDistBoard[""+distributionBoardList[i][0]+"_showDB"]);
									markerClusterDistBoard.addMarker(markersDistBoard[""+distributionBoardList[i][0]]);
									DistributionBoardCheckFilter(distributionBoardList[i][0]+"_showDB","DB");
									
								}
								
								AllDistributionBoardFilter("DB","DB");
								
									
							}
					distributionBoardList = null;
				},
				error: function (result) {
					alert("Error");
				}
			});
	}
	
	
	


// Function to create markers oon manhole, handhole and distribution board

	 function create_Marker_Click(Id,Name,Long,Lat,markers,marker_Cluster,Type,city) {

			const pos = new google.maps.LatLng(Lat,Long);
			var data="<div>" +Name+ "</div>"; 
			var mapIcon;
			var markerType="";

			 if(markers == markersManhole && marker_Cluster == markerClusterManhole) {
					 if(Type == "Junction"){
						 mapIcon = iconManholeJct; 
					 }
					 else {
						 mapIcon = iconManhole;
					 }
					 markerType="Manhole";
			 }
			 
			 else if (markers == markersDistBoard && marker_Cluster == markerClusterDistBoard) {
				 mapIcon = iconDistBoard; 
				 markerType="DistributionBoard";
			 }
			 
			  else if (markers == markersNodes && marker_Cluster == markerClusterNodes) {
				 mapIcon = iconNodes; 
				 markerType="Entreprise";
			 }
			 
			 else {
					if(Type == "Junction"){
						 mapIcon = iconHandholeJct; 
					 }
					 else {
						 mapIcon = iconHandhole;
					 }
					 markerType="Handhole";
			 }
			 
			 
			 

			 if(!markers[Id]){
				
				 Mapmarker = new google.maps.Marker({
					position: pos,
					data: data,
					icon: mapIcon,
					ID: Id,
				});
				markers.metadata = { id: Id };
				markers[Id] = Mapmarker;
				markers.push(Mapmarker);
				
				
				google.maps.event.addListener(Mapmarker, "click", function (e) {
					var IdSelected=this.ID;
					nodeFileId = $("#"+IdSelected).parents().eq(3).attr("id").split("initial_ul_")[1];
					var projectInitial=$("#initial_ul_Projects").find('>ul > #'+nodeFileId);
					var projectRel=$("#"+nodeFileId+"").find('>ul > #initial_ul_'+nodeFileId);
					var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
					var children = $("#"+markerType+"_f_"+nodeFileId+"").find(' > ul > li');
					
					if(IdSelected!=IdSelectedTemp){	
						if(IdSelectedTemp!=""){
							$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
							$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
						}
						IdSelectedTemp=IdSelected;							
					}
					childrenInitial.show('fast');
					if(!children.is(":visible")){
						children.show();
					}
					projectInitial.show('fast');
					projectRel.show('fast');

					$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
					$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");

					$("#initial_ul_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
					$("#"+markerType+"_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');

					offset=$("#"+IdSelected).offset().top;
					projectOffset=$("#initial_ul_"+nodeFileId).offset().top;
					offsetTotal=(offset-projectOffset);
					$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
					if(draw==true){
						createPathh(e);
					}
					
					if(deleting == true){ 
						deleteAuxPath(e);
						MarkerArrayId.push(Id);
					}
				 });
			 } 

				else{
					if(markers[Id].map!=map){
						markers[Id].setMap(map);
					}
					markers[Id].setPosition(pos);
					markers[Id].data=data;
				}
			 

			 
			$("#"+Id+" .TreeSpan").not($("#"+Id+"_f"+" .TreeSpan")).on('click', function () {

			panTo(markers[Id].getPosition().lat(), markers[Id].getPosition().lng());
			map.setZoom(11);
			if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(markers[Id].data);
			infowindow.open(map,markers[Id]);
						
			});

		 
	 }


function Create_FiberPath(fiberId){
	$("#"+fiberId+" > .TreeSpan").on('click',function(){	
		if(fiberId.includes("__showPath")){
			var fiberID=$(this).parent().attr('id');	 
	   	  	fiberID = fiberID.split("__showPath")[0];      
		}
		else{
			var fiberID=$(this).parent().attr('id');	     
	    }
		map.fitBounds(window["bounds_"+fiberID]);
	});
}


	/// beginning of tube click events to create tube and and boq data/// 
function Create_FiberTubeClick_Event(tubeId){

	$("#"+tubeId+ "> .TreeSpan").on('click',function(){
		if(tubeId.includes("__showPath")){
		      var tubeID=$(this).parent().attr('id');	
		      tubeID = tubeID.split("__showPath")[0];      
         }
         else {
         		var tubeID=$(this).parent().attr('id');	   
         }
		if(!tubeArray[tubeID]){
			  buildPath(tubeID,window["mapPoints_"+tubeID],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);				
		}
		map.fitBounds(window["bounds_"+tubeID]);
	});
}
	/// end of tube click events to create tube /// 

function Create_FiberStrandClick_Event(strandId){
	$("#"+strandId+"> .TreeSpan").on('click',function(){
		if(strandId.includes("__showPath")){
			   var strandID=$(this).parent().attr('id');	 
			   strandID = strandID.split("__showPath")[0];      
		}
		else{
		      var strandID=$(this).parent().attr('id');	   
		 }
	      
		if(!strandArray[strandID]){						
			buildPath(strandID,window["mapPoints_"+strandID],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
		}							
			map.fitBounds(window["bounds_"+strandID]);									
	});	
}

function createSiteCltMarker(Id,Name,Lat,Long,siteCltSrcMarkers){
	//console.log("Id is "+Id)
	if(Id.startsWith("null")==true || Id.startsWith("AUXILIARY_PT_")==true || Id.startsWith("AUXILIARY_TUBE_PT")==true || Id.startsWith("AUXILIARY_STRAND_PT")==true
			|| Id.startsWith("AUXILIARY_TRENCH_PT")==true || Id.startsWith("AUXILIARY_DUCT_PT")==true) {
		var icon = iconAuxPoint;
	}
	else if(Id.startsWith("CLT")==true) {
		var icon = iconClient;
	}
	else if(Id.startsWith("WARE")==true) {
		var icon = iconSite;
	}
	else{
	    var icon = iconPlace;
	}
		
	if(Id.split("_")[0]=="WARE") {
		if(allSites.includes(Name) == false){	
			allSites.push(Name);
		}
	}
	else if(Id.split("_")[0]=="CLT") {
		if(allClients.includes(Name) == false){	
			allClients.push(Name);
		}
	}		
			
	markerId=Id;		
	const pos = new google.maps.LatLng(Lat,Long);
	var Name=Name;				         
	var data="<div>" +Name+ "</div>";
	
	if(!siteCltSrcMarkers[markerId]){
		siteCltMarker = new google.maps.Marker({
			position: pos,
			data:data,
			ID:markerId,
			icon:icon,
	});
		
		siteCltMarker.metadata = { id: markerId };
		siteCltSrcMarkers[markerId] = siteCltMarker;
		siteCltSrcMarkers.push(siteCltMarker);	
	}
	else{
		if(siteCltSrcMarkers[markerId].map!=map){
			siteCltSrcMarkers[markerId].setMap(map);
		}				
		siteCltSrcMarkers[markerId].setPosition(pos);
		siteCltSrcMarkers[markerId].data=data;
	}
	siteCltSrcMarkers[markerId].setMap(map);
	// for deleting from map
	google.maps.event.addListener(siteCltSrcMarkers[markerId], "click", function (e) {
	if(deleting == true){ 
		deleteAuxPath(e);
		MarkerArrayId.push(markerId);
	}	
	});
}
		
	/// beginning of strand click events to create strand and and boq data/// 
	
	function Create_FiberStrand(strandId){
		var path=window["mapPoints_"+strandId];
	
		$("#"+strandId+"> .TreeSpan").on('click',function(){
			
			//TO BE DELETED
			//showBoq();
	
			if(!strandArray[strandId]){	
	
				//buildStrandPath(strandId,path);				  
				buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0)
			   }
				map.fitBounds(window["bounds_"+strandId]);				
				
				checkedChildrenLayer="";
				//TO BE DELETED
				/*
				var tr = "<tr>"+"<th>"+window[""+strandId][0]+"</th></tr>"
					+"<tr>"+"<th>Tube  		   : </th> <td> "+$(this).parents().eq(4).attr('id')+"</td></tr><hr>"
					+"<tr>"+"<th>Fiber Cable   : </th> <td> "+$(this).parents().eq(8).attr('id')+"</td></tr><hr>"
					
					+"<tr>"+"<th>From 		   : </th> <td> "+window[""+strandId][6]+"</td></tr><hr>"
					+"<tr>"+"<th>To 		   : </th> <td> "+window[""+strandId][7]+"</td></tr>";
				
				$("#boq_table").append(tr);	*/		
	
		});
	
	}
	
	/// end of tube click events to create tube ///

// creating markers to  delete from aux points from map
	eventDelete = 0;
	MarkerAuxArray = [];
	MarkerArrayId = [];
	selectedMarkersPos = [];
	MarkersArray = [];
	PathsArray = [];
	selectedMarkers = [];

	function deleteAuxPath(event) {		
		eventDelete++;
		var pos =[event.latLng.lat().toFixed(8),event.latLng.lng().toFixed(8)];
		const posFiberMarker = new google.maps.LatLng(event.latLng.lat().toFixed(8),event.latLng.lng().toFixed(8));	
		var ID="Marker"+eventDelete;	
	    var fiber_Path_Marker = new google.maps.Marker({
				position: posFiberMarker,
				ID:ID,
				icon:getContext()+"/resources/NetworkImages/remove.png",											        
				map:map
		 });
		
		 selectedMarkersPos.push({Id: ID, long: event.latLng.lng(), lat: event.latLng.lat() }); 
		 //console.log("delete postion is "+event.latLng.lng() +", "+event.latLng.lat());
		 PathsArray.push(pos);	
		 var position_Index = PathsArray.indexOf(pos);
		 MarkerAuxArray.push(fiber_Path_Marker);
		 fiber_Path_Marker.metadata = { id: ID };
		 MarkersArray[ID] = fiber_Path_Marker;
		 MarkersArray.push(fiber_Path_Marker);
		 var Marker_Index = MarkersArray.indexOf(MarkersArray[ID]);

			// marker listener if clicked to splice from markers array temp and remove from map
		 fiber_Path_Marker.addListener("click", () => {
				eventCreate--;
				fiber_Path_Marker.setMap(null);
				PathsArray.splice(position_Index, 1);
						
				MarkersArray.splice(Marker_Index, 1);
				MarkerAuxArray.splice(Marker_Index, 1);	
		});
		 console.log("delete fiber_Path_Marker is "+fiber_Path_Marker);
	}
// creating the markers while drawing from map 
function createPathh(event) {
	console.log("drawing"+draw);					
	if(draw==true){
	///////////////////////// map listener start /////////////////////
		console.log("drawing");
	   eventCreate++;
	   var pos1=[event.latLng.lat().toFixed(8),event.latLng.lng().toFixed(8)];
	   const posFiberMarker = new google.maps.LatLng(event.latLng.lat().toFixed(8),event.latLng.lng().toFixed(8));								   

	   Path_Array.push(pos1);								   

	   var ID="Marker"+eventCreate;								  
	   var position_Index = Path_Array.indexOf(pos1);
	   
	   var  markerFiber_Path = new google.maps.Marker({
				position: posFiberMarker,
		 //       data:pos1,
				ID:ID,
				icon:getContext()+"/resources/NetworkImages/google_pin.png",											        
				map:map
						 });

		markerArrayAux.push(markerFiber_Path);
		markerFiber_Path.metadata = { id: ID };
		MarkerArray[ID] = markerFiber_Path;
		MarkerArray.push(markerFiber_Path);
		var Marker_Index = MarkerArray.indexOf(MarkerArray[ID]);

		// marker listener if clicked to splice from markers array temp and remove from map
		markerFiber_Path.addListener("click", () => {
			eventCreate--;
			markerFiber_Path.setMap(null);
			Path_Array.splice(position_Index, 1);
					
			MarkerArray.splice(Marker_Index, 1);
			markerArrayAux.splice(Marker_Index, 1);	
		});

	}
}

function Create_Trench(trench_Id){
	$("#"+trench_Id+" > .TreeSpan").bind('click',function(){
		if(!trenchArray[trench_Id]){
			STEPS = 100;
			buildPath(trench_Id,window["mapPoints_"+trench_Id],trenchArray,allTrenches,"Trench_f_",'brown',0.4,20,'brown',1);
	   }
	   map.fitBounds(window["bounds_"+trench_Id]);
	});
}
function Create_Duct(duct_Id){
	$("#"+duct_Id+" > .TreeSpan").bind('click',function(){
		if(!ductArray[duct_Id]){
			STEPS = 100;
			buildPath(duct_Id,window["mapPoints_"+duct_Id],ductArray,allDucts,"Duct",'#8e8080',0.6,10,'#8e8080',1);
	   }
	   map.fitBounds(window["bounds_"+duct_Id]);				
	});

}

// creation of juction listener map & tree
function CreateJunctionClickEvent (junctionID,physicalLayer){
		$("#"+junctionID+"> .TreeSpan").on('click',function(){		
			panTo(window[""+junctionID][8], window[""+junctionID][7]);
			map.setZoom(11);	
		});
	}




/* tO BE DELETED
 * function buildDrivingFiberPath(Id,path) {
	

var service = new google.maps.DirectionsService;
routeDisplay = [];

var orderArray = [];

for (var s = 0; s < path.length; s = s + 10) {
	orderArray.push(path[s]);
}
orderArray.push(path.slice(-1).pop());



// Divide route to several parts because max stations limit is 25 (23 waypoints + 1 origin + 1 destination)
for (var i = 0, parts = [], max = 25 - 1; i < orderArray.length; i = i + max)
    parts.push(orderArray.slice(i, i + max + 1));

// Service callback to process service results
var service_callback = function(response, status) {
    if (status != 'OK') {
        console.log('Directions request failed due to ' + status);
        return;
    }
    
    var renderer = new google.maps.DirectionsRenderer;    
    renderer.setMap(map);
    renderer.setOptions({ ID:Id, suppressMarkers: true, preserveViewport: true});
    renderer.setDirections(response);
    routeDisplay.push(renderer);

};




// Send requests to service to get route (for stations count <= 25 only one request will be sent)
for (var i = 0; i < parts.length; i++) {
    // Waypoints does not include first station (origin) and last station (destination)
    var waypoints = [];
    for (var j = 1; j < parts[i].length - 1; j++)
        //waypoints.push({location: parts[i][j], stopover: false});
        waypoints.push({location: parts[i][0], stopover: false});  // get only first auxiliary from the splited array to 25 aux
    
    // Service options
    var service_options = {
        origin: parts[i][0],
        destination: parts[i][parts[i].length - 1],
        waypoints: waypoints,
        travelMode: 'DRIVING'
    };
    // Send request
    service.route(service_options, service_callback);
}
directionHashmap.set(Id,routeDisplay);

lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1);
text=$("#"+Id+"> .TreeSpan").text().split("/")[0];

directionHashmap.get(Id).mapLabel = new MapLabel({ 
	text: text, 
	position: lablCoords,	
	fontSize: 13, 
	fontColor: 'blue', 
	align: 'top', 
	}); 


}
*/

/* To BE DELETED 
 * 
 * function buildDrivingTube(Id,path) {
	
var service = new google.maps.DirectionsService;
routeDisplayTube = [];

var orderArray = [];

for (var s = 0; s < path.length; s = s + 10) {
	orderArray.push(path[s]);
}
orderArray.push(path.slice(-1).pop());



// Divide route to several parts because max stations limit is 25 (23 waypoints + 1 origin + 1 destination)
for (var i = 0, parts = [], max = 25 - 1; i < orderArray.length; i = i + max)
    parts.push(orderArray.slice(i, i + max + 1));

// Service callback to process service results
var service_callback = function(response, status) {
    if (status != 'OK') {
        console.log('Directions request failed due to ' + status);
        return;
    }
    
    var renderer = new google.maps.DirectionsRenderer;    
    renderer.setMap(map);
    renderer.setOptions({ ID:Id, suppressMarkers: true, preserveViewport: true, polylineOptions: { strokeColor: 'green' } });
    renderer.setDirections(response);
    routeDisplayTube.push(renderer);

};



// Send requests to service to get route (for stations count <= 25 only one request will be sent)
for (var i = 0; i < parts.length; i++) {
    // Waypoints does not include first station (origin) and last station (destination)
    var waypoints = [];
    for (var j = 1; j < parts[i].length - 1; j++)
        //waypoints.push({location: parts[i][j], stopover: false});
        waypoints.push({location: parts[i][0], stopover: false});  // get only first auxiliary from the splited array to 25 aux
    
    // Service options
    var service_options = {
        origin: parts[i][0],
        destination: parts[i][parts[i].length - 1],
        waypoints: waypoints,
        travelMode: 'DRIVING',        
    };
    // Send request
    service.route(service_options, service_callback);
}
directionHashmapTube.set(Id,routeDisplayTube);

lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1);
text=$("#"+Id+"> .TreeSpan").text().split("/")[0];

directionHashmapTube.get(Id).mapLabel = new MapLabel({ 
	text: text, 
	position: lablCoords,	
	fontSize: 13, 
	fontColor: 'green', 
	align: 'top', 
	}); 


}
*/

/* tO BE DELETED
function buildDrivingStrand(Id,path) {
	
var service = new google.maps.DirectionsService;
routeDisplayStrand = [];

var orderArray = [];

for (var s = 0; s < path.length; s = s + 10) {
	orderArray.push(path[s]);
}
orderArray.push(path.slice(-1).pop());



// Divide route to several parts because max stations limit is 25 (23 waypoints + 1 origin + 1 destination)
for (var i = 0, parts = [], max = 25 - 1; i < orderArray.length; i = i + max)
    parts.push(orderArray.slice(i, i + max + 1));

// Service callback to process service results
var service_callback = function(response, status) {
    if (status != 'OK') {
        console.log('Directions request failed due to ' + status);
        return;
    }
    
    var renderer = new google.maps.DirectionsRenderer;    
    renderer.setMap(map);
    renderer.setOptions({ ID:Id, suppressMarkers: true, preserveViewport: true, polylineOptions: { strokeColor: 'purple' } });
    renderer.setDirections(response);
    routeDisplayStrand.push(renderer);

};



// Send requests to service to get route (for stations count <= 25 only one request will be sent)
for (var i = 0; i < parts.length; i++) {
    // Waypoints does not include first station (origin) and last station (destination)
    var waypoints = [];
    for (var j = 1; j < parts[i].length - 1; j++)
        //waypoints.push({location: parts[i][j], stopover: false});
        waypoints.push({location: parts[i][0], stopover: false});  // get only first auxiliary from the splited array to 25 aux
    
    // Service options
    var service_options = {
        origin: parts[i][0],
        destination: parts[i][parts[i].length - 1],
        waypoints: waypoints,
        travelMode: 'DRIVING',        
    };
    // Send request
    service.route(service_options, service_callback);
}
directionHashmapStrand.set(Id,routeDisplayStrand);

lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1);
text=$("#"+Id+"> .TreeSpan").text().split("/")[0];

directionHashmapStrand.get(Id).mapLabel = new MapLabel({ 
	text: text, 
	position: lablCoords,	
	fontSize: 13, 
	fontColor: 'purple', 
	align: 'top', 
	}); 


}
*/

var routeDisplay = [];
let directionHashmap = new Map();

var routeDisplayTube = [];
let directionHashmapTube = new Map();

var routeDisplayStrand = [];
let directionHashmapStrand = new Map();

var routeDisplayTrench = [];
let directionHashmapTrench = new Map();

var routeDisplayDuct = [];
let directionHashmapDuct = new Map();

function buildDrivingPath(Id,path,target,color,directionHash) {
	
var service = new google.maps.DirectionsService;
routeDisplayTube = [];
routeDisplayStrand = [];
routeDisplay= [];
routeDisplayDuct = [];

var orderArray = [];

for (var s = 0; s < path.length; s = s + 10) {
	orderArray.push(path[s]);
}
orderArray.push(path.slice(-1).pop());



// Divide route to several parts because max stations limit is 25 (23 waypoints + 1 origin + 1 destination)
for (var i = 0, parts = [], max = 25 - 1; i < orderArray.length; i = i + max)
    parts.push(orderArray.slice(i, i + max + 1));

// Service callback to process service results
var service_callback = function(response, status) {
    if (status != 'OK') {
        console.log('Directions request failed due to ' + status);
        return;
    }
    
    var renderer = new google.maps.DirectionsRenderer;    
    renderer.setMap(map);
    renderer.setOptions({ ID:Id, suppressMarkers: true, preserveViewport: true, polylineOptions: { strokeColor: color,strokeWeight:5 } });
    renderer.setDirections(response);
    if(target=="tube") {
        routeDisplayTube.push(renderer);
    }
    else if(target=="trench") {
        routeDisplayTrench.push(renderer);
    }
    else if(target=="duct") {
        routeDisplayDuct.push(renderer);
    }
    else if(target=="FiberPath_f_") {
        routeDisplay.push(renderer);
    }
    else {
        routeDisplayStrand.push(renderer);

    }
    

};



// Send requests to service to get route (for stations count <= 25 only one request will be sent)
for (var i = 0; i < parts.length; i++) {
    // Waypoints does not include first station (origin) and last station (destination)
    var waypoints = [];
    for (var j = 1; j < parts[i].length - 1; j++)
        //waypoints.push({location: parts[i][j], stopover: false});
        waypoints.push({location: parts[i][0], stopover: false});  // get only first auxiliary from the splited array to 25 aux
    
    // Service options
    var service_options = {
        origin: parts[i][0],
        destination: parts[i][parts[i].length - 1],
        waypoints: waypoints,
        travelMode: 'DRIVING',        
    };
    // Send request
    service.route(service_options, service_callback);
}
if(target=="tube") {
	directionHash.set(Id,routeDisplayTube);
}
else if(target=="trench") {
	directionHash.set(Id,routeDisplayTrench);
}
else if(target=="duct") {
	directionHash.set(Id,routeDisplayDuct);
}
else if(target=="FiberPath_f_") {
	directionHash.set(Id,routeDisplay);
}
else {
	directionHash.set(Id,routeDisplayStrand);
}

lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1);
text=$("#"+Id+"> .TreeSpan").text().split("/")[0];


	directionHash.get(Id).mapLabel = new MapLabel({ 
		text: text, 
		position: lablCoords,	
		fontSize: 13, 
		fontColor: color, 
		align: 'top', 
		}); 

}
var selectedPathId = "";
var selectedPath = [];
//Creation of  path and listener click marker rendering in tree and boq
function buildPath(Id,path,pathArray,allPathArray,folder,strokeColor,strokeOpacity,strokeWeight,fontColor,IdNb){

	flightPath = new google.maps.Polyline({
		path: path,							
		geodesic: false,
		strokeColor: strokeColor,
		ID:Id,			
		strokeOpacity: strokeOpacity,
		strokeWeight: strokeWeight
	  });				 
		
	flightPath.metadata = { id: Id };
	pathArray[Id] = flightPath;
	//console.log("before push, size for pathArray is " +pathArray.length);
	pathArray.push(flightPath);	
	//console.log("after push, size for pathArray is " +pathArray.length);

	if(!allPathArray.includes(Id)){
		allPathArray.push(Id);							
	}

	// get the point half-way between this marker and the home marker
	lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1); 
	if(IdNb != 0){
		text=window[""+Id][IdNb];
	}else{
		text=$("#"+Id+"> .TreeSpan").text().split("/")[0];
	}
	
	//console.log("------------------------- text>>>>> "+text);
	pathArray[Id].mapLabel = new MapLabel({
		text: text,
		position: lablCoords,			
		fontSize: 13,				
		fontColor: fontColor,
		align: 'top',
	  });
	
	// add an event listener, if you click the line, it will tell you the id you clicked
	flightPath.addListener('click', function(e) {
		var IdSelected=this.ID;
		pathMapListener(IdSelected,folder);
		if(draw==true){ createPathh(e);}		
	});
	// add an event listener, if you right click the line, it will tell show menu
	  menuBox = document.getElementById("deletePathMenu");
	  google.maps.event.addListener(flightPath, 'rightclick', function(e) { 
		  for (prop in e) {
		    if (e[prop] instanceof MouseEvent) {
		     	ShowContextMenuGoolge(menuBox, e[prop].clientX,e[prop].clientY); 
			    break;
		    }
		  }
	    /** this stop NOT stopping DOM 'context-menu' event from firing */
	    e.stop();
	    selectedPathId = Id;
	    selectedPath = flightPath;
	    if(deleting == true){ deleteAuxPath(e);}
	    /*if(typeof listenerPathDelete !== 'undefined'){
    		google.maps.event.removeListener(listenerPathDelete); 									
    	 }*/
		  google.maps.event.addListener(map, 'click', function () {	
		        HideContextMenuGoolge(menuBox);

		});
    });
}

///$('#selectCable').click(function(){


// to be deleted
/*
// Creation of fiber path and listener click marker rendering in tree and boq
function buildFiberPath(Id,path){

	flightPath = new google.maps.Polyline({
		path: path,							
		geodesic: false,
		strokeColor: '#4986E7',
		ID:Id,			
		strokeOpacity: 0.7,
		strokeWeight: 3.5
	  });				 
		
	flightPath.metadata = { id: Id };
	fiberArray[Id] = flightPath;
	fiberArray.push(flightPath);

	if(!allFiberCables.includes(Id)){
		allFiberCables.push(Id);							
	}

	// get the point half-way between this marker and the home marker
	lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1); 
	text=window[""+Id][9];
	console.log("------------------------- text>>>>> "+text);
	fiberArray[Id].mapLabel = new MapLabel({
		text: text,
		position: lablCoords,			
		fontSize: 13,				
		fontColor:'blue',
		align: 'top',
	  });
	// add an event listener, if you click the line, it will tell you the id you clicked
	flightPath.addListener('click', function(e) {
		var IdSelected=this.ID;
		pathMapListener(IdSelected,"FiberPath_f_");
		if(draw==true){ createPathh(e);}		
	});
}

*/
trenchArray=[];
allTrenches=[];
// to be deleted
/*
// Creation of trench and listener click marker rendering in tree and boq
function buildTrench(Id,path){

	flightPath = new google.maps.Polyline({
		path: path,							
		geodesic: false,
		strokeColor: 'brown',
		ID:Id,					
		strokeOpacity: 0.4,
		strokeWeight: 15
	  });				 
		
	flightPath.metadata = { id: Id };
	trenchArray[Id] = flightPath;	
	trenchArray.push(flightPath);

	if(!allTrenches.includes(Id)){
		allTrenches.push(Id);							
	}

	// get the point half-way between this marker and the home marker
	lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1); 
	text=window[""+Id][1];
	console.log("------------------------- text>>>>> "+text);
	trenchArray[Id].mapLabel = new MapLabel({
		text: text,
		position: lablCoords,			
		fontSize: 13,				
		fontColor:'gray',
		align: 'top',
		
		
	  });
	// add an event listener, if you click the line, it will tell you the id you clicked
	flightPath.addListener('click', function(e) {			

		var IdSelected=this.ID;
		console.log("+++++++++++++ "+e.latLng.lng().toFixed(7));
		pathMapListener(IdSelected,"Trench_f_");
		
		
		/*showBoq();
		var tr = "<tr>"+"<td><b>ID: </b>"+window[""+IdSelected][0]+"</td></tr>"
				+"<tr>"+"<td><b>Name: </b>"+window[IdSelected][1]+"</td></tr><hr>"
				+"<tr>"+"<td><b>From: </b>"+window[IdSelected][2]+" ("+window[IdSelected][4]+"/"+window[IdSelected][5]+")</td></tr><hr>"
				+"<tr>"+"<td><b>To: </b>"+window[IdSelected][3]+" ("+window[IdSelected][7]+"/"+window[IdSelected][6]+")</td></tr>"
				+"<tr>"+"<td><b>From City: </b>"+window[IdSelected][8]+"</td></tr><hr>"
				+"<tr>"+"<td><b>To City: </b>"+window[IdSelected][9]+"</td></tr>"
				+"<tr>"+"<td><b>Total Length: </b>"+window[IdSelected][12]+"</td></tr>"
				+"<tr>"+"<td><b>Featured Ducts: </b>"+window[IdSelected][10]+"</td></tr>"
				+"<tr>"+"<td><b>Implemented Ducts: </b> N/A </td></tr>";
			
			
		$("#boq_table").append(tr);*/
/*		if(draw==true){

			createPathh(e);
		}
		
	});

}
*/
ductArray=[];
allDucts=[];
// to be deleted
/*
// Creation of duct and listener click marker rendering in tree and boq
function buildDuct(Id,path){

	flightPath = new google.maps.Polyline({
		path: path,							
		geodesic: false,
		strokeColor: 'orange',
		ID:Id,					
		strokeOpacity: 0.6,
		strokeWeight: 7
	  });				 
		
	flightPath.metadata = { id: Id };
	ductArray[Id] = flightPath;	
	ductArray.push(flightPath);

	if(!allDucts.includes(Id)){
		allDucts.push(Id);							
	}

	// get the point half-way between this marker and the home marker
	lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1); 
	text=window[""+Id][1];
	console.log("------------------------- text>>>>> "+text);
	ductArray[Id].mapLabel = new MapLabel({
		text: text,
		position: lablCoords,			
		fontSize: 13,				
		fontColor:'orange',
		align: 'top',
		
		
	  });
	// add an event listener, if you click the line, it will tell you the id you clicked
	flightPath.addListener('click', function(e) {			

		var IdSelected=this.ID;
		pathMapListener(IdSelected,"Duct");

		//TO BE DELETED
	/*	showBoq();
		var tr = "<tr>"+"<td><b>ID: </b>"+window[""+IdSelected][0]+"</td></tr>"
				+"<tr>"+"<td><b>Name: </b>"+window[IdSelected][1]+"</td></tr><hr>"
				+"<tr>"+"<td><b>Trench: </b>"+window[IdSelected][14]+"</td></tr>"
				+"<tr>"+"<td><b>From: </b>"+window[IdSelected][2]+" ("+window[IdSelected][4]+"/"+window[IdSelected][5]+")</td></tr><hr>"
				+"<tr>"+"<td><b>To: </b>"+window[IdSelected][3]+" ("+window[IdSelected][7]+"/"+window[IdSelected][6]+")</td></tr>"
				+"<tr>"+"<td><b>From City: </b>"+window[IdSelected][8]+"</td></tr><hr>"
				+"<tr>"+"<td><b>To City: </b>"+window[IdSelected][9]+"</td></tr>"
				+"<tr>"+"<td><b>Total Length: </b>"+window[IdSelected][13]+"</td></tr>"
				+"<tr>"+"<td><b>Num Fiber Cables: </b>"+window[IdSelected][10]+"</td></tr>"
				+"<tr>"+"<td><b>Num Fiber Tubes: </b>"+window[IdSelected][11]+"</td></tr>"
				+"<tr>"+"<td><b>Num Fiber Strands: </b>"+window[IdSelected][12]+"</td></tr>";		

		$("#boq_table").append(tr);*/
/*		if(draw==true){

			createPathh(e);
		}
		
	});

}
*/
// to be deleted
/* // Creation of tube and listener click marker rendering in tree and boq
function buildTubePath(Id,path){

		flightPath = new google.maps.Polyline({
			path:path ,			
			geodesic: false,
			strokeColor: 'green',
			ID:Id,
			strokeOpacity: 0.7,
			strokeWeight: 2.3,
			
		  });   
		flightPath.metadata = { id: Id };
		tubeArray[Id] = flightPath;
		tubeArray.push(flightPath);
		  
		// get the point half-way between this marker and the home marker
		lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1); 
		text=$("#"+Id+"> .TreeSpan").text();
		
		console.log("------------------------- text>>>>> "+text);
		tubeArray[Id].mapLabel = new MapLabel({
			text: text,
			position: lablCoords,			
			fontSize: 13,					
			fontColor:'green',
			align: 'top',
			
			
		  });  				 
		

		if(!allTubes.includes(Id)){
			allTubes.push(Id);							
		}

		flightPath.addListener('click', function(e) {			

			var IdSelected=this.ID;
			pathMapListener(IdSelected,"Tube");
			// to be deleted
			/*nodeFileId=$("#"+IdSelected).parents().eq(5).attr('id').split("_f_")[1];
			
			if(IdSelected!=IdSelectedTemp){	
				fiber=$("#"+IdSelected).parents().eq(3).attr('id')
				   
				var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
				var childrenfiber=$("#FiberPath_f_"+nodeFileId+"").find("> ul > li");				 
				var childrenTubeFolder=$("#"+fiber).find('> ul > li');				   
				var children = $("#"+fiber+"_f").find("ul > #"+IdSelected);
				console.log("kkkk "+$("#"+fiber).find("ul > #"+IdSelected).attr('id'))
				//var childrenInitial=$("#initial_ul").find(' > ul > li > ul > #'+fiber+' > ul > #'+fiber+"_f > ul > #"+IdSelected);
				   
					if(IdSelectedTemp!=""){
							console.log("IdSelectedTemp "+IdSelectedTemp);   
							$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
							$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
							$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
							$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
							children.show('fast');
							childrenTubeFolder.show('fast');
							childrenInitial.show('fast');
							childrenfiber.show('fast');
							$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							 
							 $("#FiberPath_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
						  }

				   else{
					  
							childrenInitial.show('fast');
							children.show('fast');
							childrenfiber.show('fast');
							childrenTubeFolder.show('fast');
							$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
							$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
							$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							 
							$("#FiberPath_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

					}
					IdSelectedTemp=IdSelected;
				}
			var tube = document.getElementById(""+IdSelected);				
			tube.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
*/
	/*		if(draw==true){

				createPathh(e);
			}
			});


	}

// Creation of strand and listener click marker rendering in tree and boq
function buildStrandPath(Id,path){

	flightPath = new google.maps.Polyline({
		path:path ,			
		geodesic: false,
		strokeColor: 'purple',
		ID:Id,
		strokeOpacity: 0.7,
		strokeWeight: 1.8,
		
	  });   
	flightPath.metadata = { id: Id };
	strandArray[Id] = flightPath;
	strandArray.push(flightPath);


	// get the point half-way between this marker and the home marker
	lablCoords = google.maps.geometry.spherical.interpolate(path[0], path[1],1); 
	text=$("#"+Id+"> .TreeSpan").text();
	console.log("------------------------- text>>>>> "+text);
	strandArray[Id].mapLabel = new MapLabel({
		text: text,
		position: lablCoords,			
		fontSize: 13,					
		fontColor:'purple',
		align: 'top',
		
		
	  });

	if(!allStrands.includes(Id)){
		allStrands.push(Id);							
	}

	flightPath.addListener('click', function(e) {			

		var IdSelected=this.ID;
		console.log("+++++++++++++ "+IdSelected);
		pathMapListener(IdSelected,"Strand");
	// to be deleted
		/*(f(IdSelected!=IdSelectedTemp){	
			fiber=$("#"+IdSelected).parents().eq(7).attr('id')
			nodeFileId=$("#"+IdSelected).parents().eq(9).attr('id').split("_f_")[1];			 
			tube=$("#"+IdSelected).parents().eq(3).attr('id')  
			

			var childrenInitial=$("#initial_ul_"+nodeFileId+"").find('>ul > li');
			var childrenfiber=$("#FiberPath_f_"+nodeFileId+"").find('> ul > #'+fiber);				 
			var childrenTubeFolder=$("#"+fiber).find('> ul > li');				   
			var children = $("#"+fiber+"_f").find("> ul > #"+tube);
			var childrenStrandfolder = $("#"+tube).find("> ul > li");
			var childrenStrandfinal = $("#"+tube+"_f").find("> ul > #"+IdSelected);
			console.log("kkkk "+$("#"+fiber).find("ul > li > ul > #"+IdSelected).attr('id'))
			//var childrenInitial=$("#initial_ul").find(' > ul > li > ul > #'+fiber+' > ul > #'+fiber+"_f > ul > #"+IdSelected);
			   
				if(IdSelectedTemp!=""){
						console.log("IdSelectedTemp "+IdSelectedTemp);   
						$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
						$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
						$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
						$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
						children.show('fast');
						
						childrenInitial.show('fast');
						childrenfiber.show('fast');
						childrenTubeFolder.show('fast');
						childrenStrandfolder.show('fast');
						childrenStrandfinal.show('fast');
						$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
						 
						 $("#FiberPath_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					  }

			   else{
				  
						childrenInitial.show('fast');
						children.show('fast');
						childrenfiber.show('fast');
						childrenTubeFolder.show('fast');
						childrenStrandfolder.show('fast');
						childrenStrandfinal.show('fast');

						$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
						$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
						$("#initial_ul_"+nodeFileId+"> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
						 
						$("#FiberPath_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

				}
				IdSelectedTemp=IdSelected;
			}
		var strand = document.getElementById(""+IdSelected);				
		strand.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
		*/
/*		});


}
*/
////>>>>>>>>>> function checkbox events for filtering of <<<< Manholes >>>> ////
	
function ManholeCheckFilter(Id){
	$("#"+Id).children('input').on("change",function() {
	var folderID = $(this).parents().eq(4).attr('id');
	
	var ManId=$(this).parent().attr('id');
	console.log("change into "+ManId);

	if ($(this).is(':checked')){

		markersManhole[ManId].setMap(map);
		markerClusterManhole.addMarker(markersManhole[ManId]);					
		
		$(this).parent().find('li').each(function(){
			$(this).children('input:checkbox').prop('checked', true);		
		});
}
	else{
		if(folderID == "initial_ul_CurrentPhysicalLayer"){
				$("#Manhole_f_CurrentPhysicalLayer > .AllManholes").prop("checked",false);				
		}
		else {
				$("#Manhole_f_"+folderID+ " > .AllManholes").prop("checked",false);				
		}
		
		//$('.AllManholes').prop('checked', false);				
		markersManhole[ManId].setMap(null);				
		markerClusterManhole.removeMarker(markersManhole[ManId]);

			$(this).parent().find('li').each(function(){
				$(this).children('input:checkbox').prop('checked', false);		
			});

		}				
		 if ($(this).parents().eq(2).find('.Manhole:checked').length == $(this).parents().eq(2).find('.Manhole').length) {
			$(this).parents().eq(2).children('input').prop('checked', true);
		 }
		 else{
			$(this).parents().eq(2).children('input').prop('checked', false);
		 }
		
		if( $("#Manhole_f_CurrentPhysicalLayer").find(".Manhole:checked" ).length ==0){
			$("#manholeCheckAllBoq").prop("checked",false);
	
		}
		else{
			$("#manholeCheckAllBoq").prop("checked",true);
	
		}
});
}

////>>>>>>>>>> function checkbox events for filtering Manholes directories ////
function AllManholeCheckFilter(){
$('.AllManholes').bind("change",function() {

		markerClusterManhole.clearMarkers();	
				
		if ($(this).is(':checked')){
			$(this).parent().find('.Manhole').each(function(){		
				$(this).prop('checked', true);
			});
			
			$("#network_tree").find(".Manhole:checked" ).each(function(){
				id=$(this).parent().attr('id');
				if(markersManhole[id].getMap()==null){
					markersManhole[id].setMap(map);			
					markerClusterManhole.addMarker(markersManhole[id]);
				}		
			});	
			
			$(this).parent().find('li').each(function(){																	
				$(this).children('input:checkbox').prop('checked', true);		
			});	
			
			if($("#Manhole_f_CurrentPhysicalLayer > .AllManholes").is(":checked") ) {	
				$("#manholeCheckAllBoq").prop("checked",true);
			}																	
		}
		
		else{
			
			$(this).parent().find('.Manhole').each(function(){
				$(this).prop('checked', false);
			});
			
			$("#manholeCheckAllBoq").prop("checked",false);
			
			if($("#Manhole_f_CurrentPhysicalLayer > .AllManholes").is(":checked") ) {	
				$("#manholeCheckAllBoq").prop("checked",true);
			}	

			$("#network_tree").find(".Manhole:checked" ).each(function(){

				id=$(this).parent().attr('id');
				if(markersManhole[id].getMap()==null){
					markersManhole[id].setMap(map);			
					markerClusterManhole.addMarker(markersManhole[id]);	
				}		
			});
			
			$(this).parent().find('li').each(function(){																	   
				$(this).children('input:checkbox').prop('checked', false);		
			});	
			
		
		}
	});	
}

////>>>>>>>>>> function checkbox events for filtering of <<<< Handholes >>>> ////
function HandholeCheckFilter(Id){	
$("#"+Id).children('input').on("change",function() {
		var HandId=$(this).parent().attr('id');
		var folderID = $(this).parents().eq(4).attr('id');
		
		if ($(this).is(":checked")){
			markersHandhole[HandId].setMap(map);
			markerClusterHandhole.addMarker(markersHandhole[HandId]);
				
			$(this).parent().find('li').each(function(){															 
				$(this).children('input:checkbox').prop('checked', true);		
			});		
		}
		else{
			if(folderID == "initial_ul_CurrentPhysicalLayer"){
				$("#Handhole_f_CurrentPhysicalLayer > .AllHandholes").prop("checked",false);				
			}
			else {
				//$('.AllHandholes').prop('checked', false);
				$("#Handhole_f_"+folderID+ " > .AllHandholes").prop("checked",false);				
				
			}
			markersHandhole[HandId].setMap(null);		
			
			markerClusterHandhole.removeMarker(markersHandhole[HandId]);	
			
			$(this).parent().find('li').each(function(){
				$(this).children('input:checkbox').prop('checked',false);		
			});
		
		}
	
		if ($(this).parents().eq(2).find('.Handhole:checked').length == $(this).parents().eq(2).find('.Handhole').length) {
			$(this).parents().eq(2).children('input').prop('checked', true);
		}
		else{
			$(this).parents().eq(2).children('input').prop('checked', false);
		}
		
		if($("#Handhole_f_CurrentPhysicalLayer > .AllHandholes").is(":checked") ) {	
			$("#handholeCheckAllBoq").prop("checked",true);
		}	
		else {
			$("#handholeCheckAllBoq").prop("checked",false);
		
		}
		if( $("#Handhole_f_CurrentPhysicalLayer").find(".Handhole:checked" ).length ==0){
			$("#handholeCheckAllBoq").prop("checked",false);
			
		}
		else{
			$("#handholeCheckAllBoq").prop("checked",true);
			
		}
	});
	

}
function AllHandholeCheckFilter(){

	$('.AllHandholes').bind("change",function() {
		
		markerClusterHandhole.clearMarkers();
		if ($(this).is(':checked')){
			$(this).parent().find('.Handhole').each(function(){
				$(this).prop('checked', true);
			});				 
			
			$("#network_tree").find(".Handhole:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(markersHandhole[id].getMap()==null){
						markersHandhole[id].setMap(map);			
						markerClusterHandhole.addMarker(markersHandhole[id]);
					}				
			});			
			$(this).parent().find('li').each(function(){													   
				$(this).children('input:checkbox').prop('checked', true);	
			});
			
			if($("#Handhole_f_CurrentPhysicalLayer > .AllHandholes").is(":checked") ) {	
					$("#handholeCheckAllBoq").prop("checked",true);
				}	
		}
		
		else{

			$(this).parent().find('.Handhole').each(function(){				
				$(this).prop('checked', false);   
			});			
			$('#handholeCheckAllBoq').prop('checked', false);
				
			if($("#Handhole_f_CurrentPhysicalLayer > .AllHandholes").is(":checked") ) {	
				$("#handholeCheckAllBoq").prop("checked",true);
			}	
	   
			$("#network_tree").find(".Handhole:checked" ).each(function(){

				id=$(this).parent().attr('id');
				if(markersHandhole[id].getMap()==null){
					markersHandhole[id].setMap(map);			
					markerClusterHandhole.addMarker(markersHandhole[id]);
				}		
			});
			$(this).parent().find('li').each(function(){														   
				$(this).children('input:checkbox').prop('checked', false);		
			});	 
		 }

	});
}

function AllNodesCheckFilter(){
$('.AllEntreprise').bind("change",function() {
		markerClusterNodes.clearMarkers();	
				
		if ($(this).is(':checked')){
			$(this).parent().find('.Nodes').each(function(){		
				$(this).prop('checked', true);
			});
			
			$("#network_tree").find(".Nodes:checked" ).each(function(){
				id=$(this).parent().attr('id');
				if(markersNodes[id].getMap()==null){
					markersNodes[id].setMap(map);			
					markerClusterNodes.addMarker(markersNodes[id]);
				}		
			});	
			
			$(this).parent().find('li').each(function(){																	
				$(this).children('input:checkbox').prop('checked', true);		
			});	
			
			if($("#Entreprise_f_CurrentPhysicalLayer > .AllEntreprise").is(":checked") ) {	
				$("#entrepriseCheckAllBoq").prop("checked",true);
			}																
		}
		
		else{
			
			$(this).parent().find('.Nodes').each(function(){
				$(this).prop('checked', false);
			});
			
			$("#entrepriseCheckAllBoq").prop("checked",false);
			
			if($("#Entreprise_f_CurrentPhysicalLayer > .AllEntreprise").is(":checked") ) {	
				$("#entrepriseCheckAllBoq").prop("checked",true);
			}	

			$("#network_tree").find(".Nodes:checked" ).each(function(){

				id=$(this).parent().attr('id');
				if(markersNodes[id].getMap()==null){
					markersNodes[id].setMap(map);			
					markerClusterNodes.addMarker(markersNodes[id]);
				}	
			});
			
			$(this).parent().find('li').each(function(){																	   
				$(this).children('input:checkbox').prop('checked', false);		
			});		
		}
	});	
}

function NodesCheckFilter(Id){
	$("#"+Id).children('input').on("change",function() {
	var folderID = $(this).parents().eq(4).attr('id');
	
	var NodID=$(this).parent().attr('id');

	if ($(this).is(':checked')){

		markersNodes[NodID].setMap(map);
		markerClusterNodes.addMarker(markersNodes[NodID]);				
		
		
		$(this).parent().find('li').each(function(){
			$(this).children('input:checkbox').prop('checked', true);		
		});
}
	else{
		if(folderID == "initial_ul_CurrentPhysicalLayer"){
				$("#Entreprise_f_CurrentPhysicalLayer > .AllEntreprise").prop("checked",false);			
				
		}
		else {
				$("#Entreprise_f_CurrentPhysicalLayer  > .AllEntreprise").prop("checked",false);
								
		}
					
		markersNodes[NodID].setMap(null);				
		markerClusterNodes.removeMarker(markersNodes[NodID]);

			$(this).parent().find('li').each(function(){
				$(this).children('input:checkbox').prop('checked', false);		
			});

		}				
		 if ($(this).parents().eq(2).find('.Nodes:checked').length == $(this).parents().eq(2).find('.Nodes').length) {
			$(this).parents().eq(2).children('input').prop('checked', true);
		 }
		 else{
			$(this).parents().eq(2).children('input').prop('checked', false);
		 }
		
		if( $("#Entreprise_f_CurrentPhysicalLayer").find(".Nodes:checked" ).length ==0){
			$("#entrepriseCheckAllBoq").prop("checked",false);
	
		}
		else{
			$("#entrepriseCheckAllBoq").prop("checked",true);
	
		}
});
}
//  Checkbox events for filtering of  fiber cables/tubes/strand
function FiberCableCheckFilter(){
	 
	// console.log("Welcome to fiber cable check");
	
	 $('.FiberCable').bind("change",function() {
			var folderID = $(this).parents().eq(4).attr('id');
			SelFiberId=$(this).parent().attr('id');
			
			//Hide the tube in case of driving
			$("#"+SelFiberId+"_f").find(' > ul > li   ').each(function(){	
				if(directionHashmapTube.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
						directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});		
			
			//Hide the strand in case of driving
			$("#"+SelFiberId+"_f").find(' > ul > li >ul >li >ul >li  ').each(function(){	
				if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
						directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			
			if ($(this).is(':checked')){
				if (window[""+SelFiberId][20] == "DRIVING") {
					buildDrivingPath(SelFiberId,window["mapPoints_"+SelFiberId],"FiberPath_f_",window['FiberColor_'+window[''+SelFiberId][22]],directionHashmap);
				} 
				else if(fiberArray[SelFiberId]){
					fiberArray[SelFiberId].setMap(map);																	 
				}
				else{
					if (window[""+SelFiberId][20] == "DRIVING") {
						buildDrivingPath(SelFiberId,window["mapPoints_"+SelFiberId],"FiberPath_f_",window['FiberColor_'+window[''+SelFiberId][22]],directionHashmap);
					} else {
						//buildFiberPath(SelFiberId,window["mapPoints_"+SelFiberId]);
						buildPath(SelFiberId,window["mapPoints_"+SelFiberId],fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+SelFiberId][22]],0.7,4.5,'blue',13);
						fiberArray[SelFiberId].setMap(map);		
					} 
				}
				
				// Show and Hide the label of each cable depending on the checkLabel dropdown
				if($("#fiberMapCheck_Labels").prop("checked")==true){
					if(directionHashmap.get(SelFiberId)) {
		                directionHashmap.get(SelFiberId).mapLabel.setMap(map);
	                }
					if(fiberArray[SelFiberId]){
						fiberArray[SelFiberId].mapLabel.setMap(map);
					}
				}
				else {
					if(directionHashmap.get(SelFiberId)) {
		                directionHashmap.get(SelFiberId).mapLabel.setMap(null);
	                }
					if(fiberArray[SelFiberId]){
						fiberArray[SelFiberId].mapLabel.setMap(null);
					}
				}
				
				$(this).parent().find('input:checkbox').each(function(){

					$(this).prop('checked', true);
					if($(this).parent().hasClass('TUBE')){
						var tubeId=$(this).parent().attr('id');
						if(tubeArray[tubeId]){						
							tubeArray[tubeId].setMap(map);	
						}
						else{
							//buildTubePath(tubeId,window["mapPoints_"+tubeId]);
							buildPath(tubeId,window["mapPoints_"+tubeId],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
							tubeArray[tubeId].setMap(map);
						}
					
						// Show and Hide the label of each tube depending on the checkLabel dropdown
						if($("#tubeMapCheck_Labels").prop("checked")==true){
							tubeArray[tubeId].mapLabel.setMap(map);
						}
						else {
							tubeArray[tubeId].mapLabel.setMap(null);
						}							
					
					}

					else if($(this).parent().hasClass('STRAND')){
						var strandId=$(this).parent().attr('id');
						if(strandArray[strandId]){						
							strandArray[strandId].setMap(map);	
						}
						else{
							//buildStrandPath(strandId,window["mapPoints_"+strandId]);
							buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[strandId].setMap(map);
						}	
						// Show and Hide the label of each strand depending on the checkLabel dropdown
						if($("#strandMapCheck_Labels").prop("checked")==true){
							strandArray[strandId].mapLabel.setMap(map);
						}
						else {
							strandArray[strandId].mapLabel.setMap(null);
						}							
					
					}
				});

			}
			else{
			
				if(folderID == "initial_ul_CurrentPhysicalLayer"){
					$("#FiberPath_f_CurrentPhysicalLayer > .AllFiberCables").prop("checked",false);				
				}
				else {
					$("#FiberPath_f_"+folderID+ " > .AllFiberCables").prop("checked",false);				
				}
				
				
				if(fiberArray[SelFiberId] && directionHashmap.get(SelFiberId) != undefined){
					fiberArray[SelFiberId].setMap(null);	
					fiberArray[SelFiberId].mapLabel.setMap(null);
					
					for(i = 0 ; i < directionHashmap.get(SelFiberId).length; i++) {
						if(SelFiberId == directionHashmap.get(SelFiberId)[i].ID ) {
							directionHashmap.get(SelFiberId)[i].setMap(null);
							directionHashmap.get(SelFiberId).mapLabel.setMap(null);
						    routeDisplay = [];
							/*if(fiberArray[SelFiberId]){			
								fiberArray[SelFiberId].mapLabel.setMap(null);
							}
							*/
					}
				}
					directionHashmap.delete(SelFiberId);
				}
				else if(fiberArray[SelFiberId] && directionHashmap.get(SelFiberId) == undefined){
					fiberArray[SelFiberId].setMap(null);	
					fiberArray[SelFiberId].mapLabel.setMap(null);
				}
				
				else if (window[""+SelFiberId][20] == "DRIVING") {	// no line of site case		 
					if(directionHashmap.get(SelFiberId) != undefined) {
						for(i = 0 ; i < directionHashmap.get(SelFiberId).length; i++) {
						    directionHashmap.get(SelFiberId)[i].setMap(null);
						    directionHashmap.get(SelFiberId).mapLabel.setMap(null);
						    routeDisplay = [];
							/*if(fiberArray[SelFiberId]){			
								fiberArray[SelFiberId].mapLabel.setMap(null);
							}
							*/
						}
					}
				    directionHashmap.delete(SelFiberId);
				}
				else {
					fiberArray[SelFiberId].setMap(null);
					fiberArray[SelFiberId].mapLabel.setMap(null);
				}
				/*if (window[""+SelFiberId][20] == "DRIVING") {
					for(i = 0 ; i < directionHashmap.get(SelFiberId).length; i++) {
					    directionHashmap.get(SelFiberId)[i].setMap(null);
					    directionHashmap.get(SelFiberId).mapLabel.setMap(null);
					    routeDisplay = [];
						if(fiberArray[SelFiberId]){			
						fiberArray[SelFiberId].mapLabel.setMap(null);
						}
					}
				    directionHashmap.delete(SelFiberId);
				} 
				
				else {
				fiberArray[SelFiberId].setMap(null);
				fiberArray[SelFiberId].mapLabel.setMap(null);
				}
				*/
				$(this).parent().find('input:checkbox').each(function(){
					$(this).prop('checked', false);
				
					if($(this).parent().hasClass('TUBE')){		
						var tubeId = $(this).parent().attr('id');
						tubeArray[tubeId].setMap(null);	
						tubeArray[tubeId].mapLabel.setMap(null);
					}

					else if($(this).parent().hasClass('STRAND')){											
						strandArray[$(this).parent().attr('id')].setMap(null);	
						strandArray[$(this).parent().attr('id')].mapLabel.setMap(null);
					}
				});
													 
				}

				parentLi=$(this).parent().parent().parent();
													  
				 
				if (parentLi.find("li > input[type='checkbox']:checked").length == parentLi.find('li').length) {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
				}
				else {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
				}

				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberCable:checked" ).length ==0){
					$("#fiberCheckAllBoq").prop("checked",false);
				}
				else{
					$("#fiberCheckAllBoq").prop("checked",true);
				}
				
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTubes:checked" ).length ==0) {
					$("#tubeCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#tubeCheckAllBoq").prop('checked', true);				
				}
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
					$("#strandCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#strandCheckAllBoq").prop('checked', true);				
				}
		});
	
	 $('.AllFiberCables').bind("change",function() {	
			
			if ($(this).is(':checked')){			

				$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){	
					if(directionHashmap.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
							directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
		                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
				//Hide the tube in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li >ul >li').each(function(){
					if(directionHashmapTube.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
							directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
				//Hide the strand in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li >ul >li').each(function(){
					if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
							directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});

	/*			
				// delete the driving path in checkAll is checked
				if (routeDisplay.length != 0 && (window[""+SelFiberId][16] == "DRIVING")) {
					for(i = 0 ; i < directionHashmap.get(SelFiberId).length; i++) {
					    directionHashmap.get(SelFiberId)[i].setMap(null);
					    routeDisplay = [];
					}
				    directionHashmap.delete(SelFiberId);
				}
	*/			
				
				$(this).parent().find('input:checkbox').each(function(){
					$(this).prop('checked', true);
					
					if($(this).parent().hasClass('FIBER')){
						var fiberId=$(this).parent().attr('id');
						$("#fiberCheckAllBoq").prop("checked",true);
						
						if(fiberArray[fiberId]){						
	/*
							if (routeDisplay.length != 0) {
								for(i = 0 ; i <= routeDisplay.length -1 ; i++) {
								routeDisplay[i].setMap(null);
								}
							}
	*/						
							fiberArray[fiberId].setMap(map);	 
						}
						else{   														
							var path=window["mapPoints_"+fiberId];							 
									  
							//buildFiberPath(fiberId,path);
							buildPath(fiberId,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberId][22]],0.7,4.5,'blue',13);
							fiberArray[fiberId].setMap(map);
									  
						}
						
						// Show and Hide the label of each cable depending on the checkLabel dropdown
						if($("#fiberMapCheck_Labels").prop("checked")==true){
							if(directionHashmap.get(fiberId)) {
				                directionHashmap.get(fiberId).mapLabel.setMap(map);
			                }
							if(fiberArray[fiberId]){
								fiberArray[fiberId].mapLabel.setMap(map);
							}
						}
						else {
							if(directionHashmap.get(fiberId)) {
				                directionHashmap.get(fiberId).mapLabel.setMap(null);
			                }
							if(fiberArray[fiberId]){
								fiberArray[fiberId].mapLabel.setMap(null);
							}
						}
					}

					else if($(this).parent().hasClass('TUBE')){
								   
						var tubeId=$(this).parent().attr('id');  
						if(tubeArray[tubeId]){						
							tubeArray[tubeId].setMap(map);	
						}
						else{	 
							buildPath(tubeId,window["mapPoints_"+tubeId],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
							tubeArray[tubeId].setMap(map);
						}
						
						// Show and Hide the label of each tube depending on the checkLabel dropdown
						if($("#tubeMapCheck_Labels").prop("checked")==true){
							tubeArray[tubeId].mapLabel.setMap(map);
						}
						else {
							tubeArray[tubeId].mapLabel.setMap(null);
						}
						
						$('#tubeCheckAllBoq').prop('checked', true);
					}

					else if($(this).parent().hasClass('STRAND')){
						var strandId=$(this).parent().attr('id');
						if(strandArray[strandId]){						
							strandArray[strandId].setMap(map);	
						}
						else{
							//buildStrandPath(strandId,window["mapPoints_"+strandId]);
							buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[strandId].setMap(map);

						}
						// Show and Hide the label of each strand depending on the checkLabel dropdown
						if($("#strandMapCheck_Labels").prop("checked")==true){
							strandArray[strandId].mapLabel.setMap(map);
						}
						else {
							strandArray[strandId].mapLabel.setMap(null);
						}
						
						$('#strandCheckAllBoq').prop('checked', true);
								
					}
				});

			}
			else{
				$('#tubeCheckAllBoq').prop('checked', false);
				$('#strandCheckAllBoq').prop('checked', false);
				$("#fiberCheckAllBoq").prop("checked",false);	
				
				//Hide the tube in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li >ul >li').each(function(){
					if(directionHashmapTube.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
							directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
				
				//Hide the strand in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li >ul >li').each(function(){
					if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
							directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
			
				$(this).parent().find('input:checkbox').each(function(){

					$(this).prop('checked', false);
					
					if($(this).parent().hasClass('FIBER')){

						var fiberId=$(this).parent().attr('id');
						if(fiberArray[fiberId]){				
						fiberArray[$(this).parent().attr('id')].setMap(null);	
						fiberArray[$(this).parent().attr('id')].mapLabel.setMap(null);
	/*
						  if (routeDisplay.length != 0) {
							  for(i = 0 ; i <= routeDisplay.length -1 ; i++) {
							  routeDisplay[i].setMap(null);
							  }
						   }
	*/					   	
						$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li').each(function(){	
							if(directionHashmap.get($(this).attr('id')) != undefined) {
								for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
									directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
				                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
								}
							}
						});
						}
					}

					else if($(this).parent().hasClass('TUBE')){										
						tubeArray[$(this).parent().attr('id')].setMap(null);
						tubeArray[$(this).parent().attr('id')].mapLabel.setMap(null);					
												
					}

					else if($(this).parent().hasClass('STRAND')){											
						strandArray[$(this).parent().attr('id')].setMap(null);									
						strandArray[$(this).parent().attr('id')].mapLabel.setMap(null);					

					}
				});
		
				$("#network_tree").find(".FiberCable:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(fiberArray[id]){						
							fiberArray[id].setMap(map);	
						}
						else{														
							var path=window["mapPoints_"+id];
							//buildFiberPath(id,path);
							buildPath(id,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+id][22]],0.7,4.5,'blue',13);
							fiberArray[id].setMap(map);
						}	
					});
					$("#network_tree").find(".FiberTube:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(tubeArray[id]){						
							tubeArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildTubePath(id,path);
							buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
							tubeArray[id].setMap(map);
						}	
					});
						$("#network_tree").find(".FiberStrand:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(strandArray[id]){						
							strandArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildStrandPath(id,path);
							buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[id].setMap(map);									
						}		
					});		  		
			}
		
		});	
		
	 $('.BackboneFiber').bind("change",function() {	

			if ($(this).is(':checked')){			
				$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){	
					if(directionHashmap.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
							directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
		                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
				//Hide the tube in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li ').each(function(){
					if(directionHashmapTube.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
							directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
				//Hide the strand in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li ').each(function(){
					if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
							directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});

				$(this).parent().find('input:checkbox').each(function(){
					$(this).prop('checked', true);
					if($(this).parent().hasClass('FIBER')){
						var fiberId=$(this).parent().attr('id');
						if(fiberArray[fiberId]){						
							fiberArray[fiberId].setMap(map);	 
						}
						else{   														
							var path=window["mapPoints_"+fiberId];							 
							buildPath(fiberId,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberId][22]],0.7,4.5,'blue',13);
							fiberArray[fiberId].setMap(map);
						}
					
						// Show and Hide the label of each cable depending on the checkLabel dropdown
						if($("#fiberMapCheck_Labels").prop("checked")==true){
							if(directionHashmap.get(fiberId)) {
				                directionHashmap.get(fiberId).mapLabel.setMap(map);
			                }
							if(fiberArray[fiberId]){
								fiberArray[fiberId].mapLabel.setMap(map);
							}
						}
						else {
							if(directionHashmap.get(fiberId)) {
				                directionHashmap.get(fiberId).mapLabel.setMap(null);
			                }
							if(fiberArray[fiberId]){
								fiberArray[fiberId].mapLabel.setMap(null);
							}
						}
					
					}

					else if($(this).parent().hasClass('TUBE')){
								   
						var tubeId=$(this).parent().attr('id');
		
	   
						if(tubeArray[tubeId]){						
			 
							tubeArray[tubeId].setMap(map);	
						}
						else{	 
							//buildTubePath(tubeId,window["mapPoints_"+tubeId]);
							buildPath(tubeId,window["mapPoints_"+tubeId],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
							tubeArray[tubeId].setMap(map);
						}
						
						// Show and Hide the label of each tube depending on the checkLabel dropdown
						if($("#tubeMapCheck_Labels").prop("checked")==true){
							tubeArray[tubeId].mapLabel.setMap(map);
						}
						else {
							tubeArray[tubeId].mapLabel.setMap(null);
						}	
						
						$('#tubeCheckAllBoq').prop('checked', true);
					}

					else if($(this).parent().hasClass('STRAND')){
						var strandId=$(this).parent().attr('id');
						if(strandArray[strandId]){						
							strandArray[strandId].setMap(map);	
						}
						else{
							//buildStrandPath(strandId,window["mapPoints_"+strandId]);
							buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[strandId].setMap(map);

						}	
						// Show and Hide the label of each strand depending on the checkLabel dropdown
						if($("#strandMapCheck_Labels").prop("checked")==true){
							strandArray[strandId].mapLabel.setMap(map);
						}
						else {
							strandArray[strandId].mapLabel.setMap(null);
						}	
						$('#strandCheckAllBoq').prop('checked', true);
								
					}
				});
							
					$("#fiberCheckAllBoq").prop("checked",true);
			}
			else{
				
				$("#fiberCheckAllBoq").prop("checked",false);	
				
				//Hide the tube in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li ').each(function(){
					if(directionHashmapTube.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
							directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
				//Hide the strand in case of driving
				$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li ').each(function(){
					if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
							directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
							directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
						}
					}
				});
			
				$(this).parent().find('input:checkbox').each(function(){

					$(this).prop('checked', false);
					
					if($(this).parent().hasClass('FIBER')){

						var fiberId=$(this).parent().attr('id');
						
					if(fiberArray[fiberId]){				
						fiberArray[$(this).parent().attr('id')].setMap(null);	
						fiberArray[$(this).parent().attr('id')].mapLabel.setMap(null);
	   	
						$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li').each(function(){	
							if(directionHashmap.get($(this).attr('id')) != undefined) {
								for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
									directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
				                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
								}
							}
						});
						}
						
					}

					else if($(this).parent().hasClass('TUBE')){										
						tubeArray[$(this).parent().attr('id')].setMap(null);
						tubeArray[$(this).parent().attr('id')].mapLabel.setMap(null);
							
						//$("#tubeCheckAllBoq").prop("checked",false);
												
					}

					else if($(this).parent().hasClass('STRAND')){											
						strandArray[$(this).parent().attr('id')].setMap(null);
						strandArray[$(this).parent().attr('id')].mapLabel.setMap(null);
						//$("#strandCheckAllBoq").prop("checked",false);				
	 
					}
				});
				
				if($("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTubes:checked" ).length ==0) {
					$("#tubeCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#tubeCheckAllBoq").prop('checked', true);				
				}
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
					$("#strandCheckAllBoq").prop('checked', false);				
				}
				else {
				
					$("#strandCheckAllBoq").prop('checked', true);				
				
				}
				
				$("#network_tree").find(".FiberCable:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(fiberArray[id]){						
							fiberArray[id].setMap(map);	
						}
						else{														
							var path=window["mapPoints_"+id];
							//buildFiberPath(id,path);
							buildPath(id,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+id][22]],0.7,4.5,'blue',13);
							fiberArray[id].setMap(map);
						}	
					});
					$("#network_tree").find(".FiberTube:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(tubeArray[id]){						
							tubeArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildTubePath(id,path);
							buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
							tubeArray[id].setMap(map);
						}	
					});
						$("#network_tree").find(".FiberStrand:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(strandArray[id]){						
							strandArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildStrandPath(id,path);
							buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[id].setMap(map);									
						}		
					});		  		
			}
			
			if ($('.BackboneFiber').is(':checked') && $('.MetroFiber').is(':checked') && $('.AccessFiber').is(':checked')){			
				$('.AllFiberCables').prop('checked', true);
			}
			else {
				$('.AllFiberCables').prop('checked', false);
			}
			if($(".MetroFiber").is(":checked") || $(".BackboneFiber").is(":checked") || $('.AccessFiber').is(':checked') ) {	
				$("#fiberCheckAllBoq").prop("checked",true);
			}	
			
		
		});	
	$('.MetroFiber').bind("change",function() {	
		
		if ($(this).is(':checked')){			
			$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){	
				if(directionHashmap.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
						directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
	                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			//Hide the tube in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li ').each(function(){
				if(directionHashmapTube.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
						directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});

			//Hide the strand in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li ').each(function(){
				if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
						directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});

			$(this).parent().find('input:checkbox').each(function(){
				$(this).prop('checked', true);
				if($(this).parent().hasClass('FIBER')){
					var fiberId=$(this).parent().attr('id');					
					if(fiberArray[fiberId]){						
						fiberArray[fiberId].setMap(map);	 
					}
					else{   														
						var path=window["mapPoints_"+fiberId];							 
						buildPath(fiberId,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberId][22]],0.7,4.5,'blue',13);
						fiberArray[fiberId].setMap(map);
					}
				
					// Show and Hide the label of each cable depending on the checkLabel dropdown
					if($("#fiberMapCheck_Labels").prop("checked")==true){
						if(directionHashmap.get(fiberId)) {
			                directionHashmap.get(fiberId).mapLabel.setMap(map);
		                }
						if(fiberArray[fiberId]){
							fiberArray[fiberId].mapLabel.setMap(map);
						}
					}
					else {
						if(directionHashmap.get(fiberId)) {
			                directionHashmap.get(fiberId).mapLabel.setMap(null);
		                }
						if(fiberArray[fiberId]){
							fiberArray[fiberId].mapLabel.setMap(null);
						}
					}
				
				
				}

				else if($(this).parent().hasClass('TUBE')){
							   
					var tubeId=$(this).parent().attr('id');
					if(tubeArray[tubeId]){						
						tubeArray[tubeId].setMap(map);	
					}
					else{	 
						buildPath(tubeId,window["mapPoints_"+tubeId],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[tubeId].setMap(map);
					}
					
					// Show and Hide the label of each tube depending on the checkLabel dropdown
					if($("#tubeMapCheck_Labels").prop("checked")==true){
						tubeArray[tubeId].mapLabel.setMap(map);
					}
					else {
						tubeArray[tubeId].mapLabel.setMap(null);
					}	
					
					$('#tubeCheckAllBoq').prop('checked', true);
				}

				else if($(this).parent().hasClass('STRAND')){
					var strandId=$(this).parent().attr('id');
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{
						//buildStrandPath(strandId,window["mapPoints_"+strandId]);
						buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);

					}
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
						strandArray[strandId].mapLabel.setMap(map);
					}
					else {
						strandArray[strandId].mapLabel.setMap(null);
					}	
					$('#strandCheckAllBoq').prop('checked', true);
							
				}
			});						
				$("#fiberCheckAllBoq").prop("checked",true);
		
		}
		else{
			
			$("#fiberCheckAllBoq").prop("checked",false);	
			
			//Hide the tube in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li ').each(function(){
				if(directionHashmapTube.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
						directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			
			//Hide the strand in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li ').each(function(){
				if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
						directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
		
			$(this).parent().find('input:checkbox').each(function(){

				$(this).prop('checked', false);
				
				if($(this).parent().hasClass('FIBER')){

					var fiberId=$(this).parent().attr('id');					
					if(fiberArray[fiberId]){				
					fiberArray[$(this).parent().attr('id')].setMap(null);	
					fiberArray[$(this).parent().attr('id')].mapLabel.setMap(null);
		
					$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li').each(function(){	
						if(directionHashmap.get($(this).attr('id')) != undefined) {
							for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
								directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
			                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
							}
						}
					});
					
					}
				}

				else if($(this).parent().hasClass('TUBE')){										
					tubeArray[$(this).parent().attr('id')].setMap(null);
					tubeArray[$(this).parent().attr('id')].mapLabel.setMap(null);
				
				}

				else if($(this).parent().hasClass('STRAND')){											
					strandArray[$(this).parent().attr('id')].setMap(null);
					strandArray[$(this).parent().attr('id')].mapLabel.setMap(null);

				}
			});
		
			if($("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTubes:checked" ).length ==0) {
				$("#tubeCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#tubeCheckAllBoq").prop('checked', true);				
			}
			if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
				$("#strandCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#strandCheckAllBoq").prop('checked', true);				
			}
			
			$("#network_tree").find(".FiberCable:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(fiberArray[id]){						
						fiberArray[id].setMap(map);	
					}
					else{														
						var path=window["mapPoints_"+id];
						//buildFiberPath(id,path);
						buildPath(id,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+id][22]],0.7,4.5,'blue',13);
						fiberArray[id].setMap(map);
					}	
				});
				$("#network_tree").find(".FiberTube:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(tubeArray[id]){						
						tubeArray[id].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+id];
						//buildTubePath(id,path);
						buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[id].setMap(map);
					}	
				});
					$("#network_tree").find(".FiberStrand:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(strandArray[id]){						
						strandArray[id].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+id];
						//buildStrandPath(id,path);
						buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[id].setMap(map);									
					}		
				});		  		
		}
		if ($('.BackboneFiber').is(':checked') && $('.MetroFiber').is(':checked') && $('.AccessFiber').is(':checked')){			
			$('.AllFiberCables').prop('checked', true);
		}
		else {
			$('.AllFiberCables').prop('checked', false);
		}
		
		if($(".MetroFiber").is(":checked") || $(".BackboneFiber").is(":checked") || $('.AccessFiber').is(':checked') ) {	
			$("#fiberCheckAllBoq").prop("checked",true);
		}	
		
	});
	$('.AccessFiber').bind("change",function() {	
		
		if ($(this).is(':checked')){			
			$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){	
				if(directionHashmap.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
						directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
	                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			//Hide the tube in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li ').each(function(){
				if(directionHashmapTube.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
						directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			
			//Hide the strand in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li ').each(function(){
				if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
						directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});

			$(this).parent().find('input:checkbox').each(function(){
				$(this).prop('checked', true);
				if($(this).parent().hasClass('FIBER')){
					var fiberId=$(this).parent().attr('id');
					if(fiberArray[fiberId]){						
						fiberArray[fiberId].setMap(map);	 
					}
					else{   														
						var path=window["mapPoints_"+fiberId];							 
						buildPath(fiberId,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberId][22]],0.7,4.5,'blue',13);
						fiberArray[fiberId].setMap(map);
								  
					}
					
					
					// Show and Hide the label of each cable depending on the checkLabel dropdown
					if($("#fiberMapCheck_Labels").prop("checked")==true){
						if(directionHashmap.get(fiberId)) {
			                directionHashmap.get(fiberId).mapLabel.setMap(map);
		                }
						if(fiberArray[fiberId]){
							fiberArray[fiberId].mapLabel.setMap(map);
						}
					}
					else {
						if(directionHashmap.get(fiberId)) {
			                directionHashmap.get(fiberId).mapLabel.setMap(null);
		                }
						if(fiberArray[fiberId]){
							fiberArray[fiberId].mapLabel.setMap(null);
						}
					}
				}

				else if($(this).parent().hasClass('TUBE')){
							   
					var tubeId=$(this).parent().attr('id');
					if(tubeArray[tubeId]){						
						tubeArray[tubeId].setMap(map);	
					}
					else{	 
						buildPath(tubeId,window["mapPoints_"+tubeId],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[tubeId].setMap(map);
					}
					
					// Show and Hide the label of each tube depending on the checkLabel dropdown
					if($("#tubeMapCheck_Labels").prop("checked")==true){
						tubeArray[tubeId].mapLabel.setMap(map);
					}
					else {
						tubeArray[tubeId].mapLabel.setMap(null);
					}	
						
					$("#tubeCheckAllBoq").prop("checked",true);
				}

				else if($(this).parent().hasClass('STRAND')){
					var strandId=$(this).parent().attr('id');
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{
						//buildStrandPath(strandId,window["mapPoints_"+strandId]);
						buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);

					}
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
						strandArray[strandId].mapLabel.setMap(map);
					}
					else {
						strandArray[strandId].mapLabel.setMap(null);
					}	
					$('#strandCheckAllBoq').prop('checked', true);
							
				}
			});						
				$("#fiberCheckAllBoq").prop("checked",true);
		
		}
		else{
			
			$("#fiberCheckAllBoq").prop("checked",false);	
			
			//Hide the tube in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li ').each(function(){
				if(directionHashmapTube.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
						directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			
			//Hide the tube in case of driving
			$(this).parent().find('> ul > li >ul >li >ul >li >ul >li >ul >li ').each(function(){
				if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
						directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
		
			$(this).parent().find('input:checkbox').each(function(){

				$(this).prop('checked', false);
				
				if($(this).parent().hasClass('FIBER')){

					var fiberId=$(this).parent().attr('id');
				if(fiberArray[fiberId]){				
					fiberArray[$(this).parent().attr('id')].setMap(null);	
					fiberArray[$(this).parent().attr('id')].mapLabel.setMap(null);
		
					$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li').each(function(){	
						if(directionHashmap.get($(this).attr('id')) != undefined) {
							for(ii = 0; ii < directionHashmap.get($(this).attr('id')).length; ii++) {
								directionHashmap.get($(this).attr('id'))[ii].setMap(null); 
			                    directionHashmap.get($(this).attr('id')).mapLabel.setMap(null);
							}
						}
					});
					}
				}

				else if($(this).parent().hasClass('TUBE')){										
					tubeArray[$(this).parent().attr('id')].setMap(null);
					tubeArray[$(this).parent().attr('id')].mapLabel.setMap(null);						
				}

				else if($(this).parent().hasClass('STRAND')){											
					strandArray[$(this).parent().attr('id')].setMap(null);
					strandArray[$(this).parent().attr('id')].mapLabel.setMap(null);						

				}
			});
			if($("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTubes:checked" ).length ==0) {
				$("#tubeCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#tubeCheckAllBoq").prop('checked', true);				
			}
			if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
				$("#strandCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#strandCheckAllBoq").prop('checked', true);				
			}
			
			$("#network_tree").find(".FiberCable:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(fiberArray[id]){						
						fiberArray[id].setMap(map);	
					}
					else{														
						var path=window["mapPoints_"+id];
						//buildFiberPath(id,path);
						buildPath(id,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+id][22]],0.7,4.5,'blue',13);
						fiberArray[id].setMap(map);
					}	
					//$('#fiberCheckAllBoq').prop('checked', true);

				});
				$("#network_tree").find(".FiberTube:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(tubeArray[id]){						
						tubeArray[id].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+id];
						//buildTubePath(id,path);
						buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[id].setMap(map);
					}	

				});
					$("#network_tree").find(".FiberStrand:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(strandArray[id]){						
						strandArray[id].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+id];
						//buildStrandPath(id,path);
						buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[id].setMap(map);									
					}	

				});		  		
		}
		if ($('.BackboneFiber').is(':checked') && $('.MetroFiber').is(':checked') && $('.AccessFiber').is(':checked')){			
			$('.AllFiberCables').prop('checked', true);
		}
		else {
			$('.AllFiberCables').prop('checked', false);
		}
		
		if($(".MetroFiber").is(":checked") || $(".BackboneFiber").is(":checked") || $('.AccessFiber').is(':checked') ) {	
			$("#fiberCheckAllBoq").prop("checked",true);
		}		
});		  		
	
	
	$('.FiberTube').bind("change",function(){
		var fiberID = $(this).parents().eq(2).attr('id');
		var tubeId=$(this).parent().attr('id');		
		
		if ($(this).is(':checked')){
			
			//Hide the strand in case of driving
			$(this).parent().find('input:checkbox').each(function(){
				if($(this).parent().hasClass('STRAND')){
					if(directionHashmapStrand.get($(this).parent().attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get($(this).parent().attr('id')).length; ii++) {
							directionHashmapStrand.get($(this).parent().attr('id'))[ii].setMap(null); 
							directionHashmapStrand.get($(this).parent().attr('id')).mapLabel.setMap(null);
						}
					}
				}
			});
						
			if (window[""+tubeId][14] == "DRIVING") {
				buildDrivingPath(tubeId,window["mapPoints_"+tubeId],"tube","green",directionHashmapTube);
			}
			else if(tubeArray[tubeId]){
				tubeArray[tubeId].setMap(map);	
			}
			else{
				if (window[""+tubeId][14] == "DRIVING") {
					buildDrivingPath(tubeId,window["mapPoints_"+tubeId],"tube","green",directionHashmapTube);
				}
				else {
					var path=window["mapPoints_"+tubeId];
					buildPath(tubeId,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
					tubeArray[tubeId].setMap(map);
				}
		  }
			// Show and Hide the label of each tube depending on the checkLabel dropdown
			if($("#tubeMapCheck_Labels").prop("checked")==true){
				if(directionHashmapTube.get(tubeId)) {
					directionHashmapTube.get(tubeId).mapLabel.setMap(map);
               }
				if(tubeArray[tubeId]){
					tubeArray[tubeId].mapLabel.setMap(map);
				}
			}
			else {
				if(directionHashmapTube.get(tubeId)) {
					directionHashmapTube.get(tubeId).mapLabel.setMap(null);
               }
				if(tubeArray[tubeId]){
					tubeArray[tubeId].mapLabel.setMap(null);
				}
			}
			
			$(this).parent().find('input:checkbox').each(function(){
				$(this).prop('checked', true);
				
				if($(this).parent().hasClass('STRAND')){
					var strandId=$(this).parent().attr('id');
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{
						//buildStrandPath(strandId,window["mapPoints_"+strandId]);
						buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);
					}				
				
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
						strandArray[strandId].mapLabel.setMap(map);
					}
					else {
						strandArray[strandId].mapLabel.setMap(null);
					}	
				
				
				}
				
				
			});					
	
		}
		else{				
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
			
			//Hide the strand in case of driving
			$(this).parent().find('input:checkbox').each(function(){
				if($(this).parent().hasClass('STRAND')){
					if(directionHashmapStrand.get($(this).parent().attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get($(this).parent().attr('id')).length; ii++) {
							directionHashmapStrand.get($(this).parent().attr('id'))[ii].setMap(null); 
							directionHashmapStrand.get($(this).parent().attr('id')).mapLabel.setMap(null);
						}
					}
				}
			});
				
			
			
			if(tubeArray[tubeId] && directionHashmapTube.get(tubeId) != undefined){
				tubeArray[tubeId].setMap(null);	
				tubeArray[tubeId].mapLabel.setMap(null);
				
				for(i = 0 ; i < directionHashmapTube.get(tubeId).length; i++) {
					if(tubeId == directionHashmapTube.get(tubeId)[i].ID ) {
						directionHashmapTube.get(tubeId)[i].setMap(null);
						directionHashmapTube.get(tubeId).mapLabel.setMap(null);
					    routeDisplayTube = [];
						if(tubeArray[tubeId]){			
							tubeArray[tubeId].mapLabel.setMap(null);
						}
				}
			}
				directionHashmapTube.delete(tubeId);
			}
			else if(tubeArray[tubeId] && directionHashmapTube.get(tubeId) == undefined){
				tubeArray[tubeId].setMap(null);	
				tubeArray[tubeId].mapLabel.setMap(null);
			}
			else  if (window[""+tubeId][14] == "DRIVING" ) {	// no line of site case		 
				if(directionHashmapTube.get(tubeId) != undefined) {
					for(i = 0 ; i < directionHashmapTube.get(tubeId).length; i++) {
						if(tubeId == directionHashmapTube.get(tubeId)[i].ID ) {
							directionHashmapTube.get(tubeId)[i].setMap(null);
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						    routeDisplayTube = [];
							if(tubeArray[tubeId]){			
								tubeArray[tubeId].mapLabel.setMap(null);
							}
						}
					}
				}
				directionHashmapTube.delete(tubeId);
			}
			else {
				tubeArray[tubeId].setMap(null);
				tubeArray[tubeId].mapLabel.setMap(null);
			}
			
			$("#tubeCheckAllBoq").prop('checked', false);			
			
			$(this).parent().find('input:checkbox').each(function(){	   
					$(this).prop('checked', false);
					
					if($(this).parent().hasClass('STRAND')){											
						strandArray[$(this).parent().attr('id')].setMap(null);	
						strandArray[$(this).parent().attr('id')].mapLabel.setMap(null);
					}
				});
			
	}			
			var folderFiberId =$(this).parent().parent().parent().attr('id');	
			var tubeChildrenLength =$("#"+folderFiberId+" > ul").children().length;
			var tubeCheckedLength = $(this).parent().parent().parent().find(".FiberTube:checked").length;
			
			
			//To check the tube folder when all tube children are checked
			if (tubeChildrenLength == tubeCheckedLength) {
				$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
			}
			
			if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTube:checked" ).length ==0) {
				$("#tubeCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#tubeCheckAllBoq").prop('checked', true);				
			
			}
			if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
				$("#strandCheckAllBoq").prop('checked', false);				
			}
			else {
			
				$("#strandCheckAllBoq").prop('checked', true);				
			
			}
});	
	
$('.FiberStrand').bind("change",function() {
		
		strandId=$(this).parent().attr('id');
		if ($(this).is(':checked')){
			
			if (window[""+strandId][14] == "DRIVING") {
				//buildDrivingStrand(strandId,window["mapPoints_"+strandId]);
				buildDrivingPath(strandId,window["mapPoints_"+strandId],"strand","purple",directionHashmapStrand);
			}
			else if(strandArray[strandId]){
				strandArray[strandId].setMap(map);	
			}
			else{
				if (window[""+strandId][14] == "DRIVING") {
					//buildDrivingStrand(strandId,window["mapPoints_"+strandId]);
					buildDrivingPath(strandId,window["mapPoints_"+strandId],"strand","purple",directionHashmapStrand);
				}
				else {
					var path=window["mapPoints_"+strandId];
					buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
					strandArray[strandId].setMap(map);
					}
		  }
			
			// Show and Hide the label of each strand depending on the checkLabel dropdown
			if($("#strandMapCheck_Labels").prop("checked")==true){
				if(directionHashmapStrand.get(strandId)) {
					directionHashmapStrand.get(strandId).mapLabel.setMap(map);
			    }
				if(strandArray[strandId]){
					strandArray[strandId].mapLabel.setMap(map);
				}
			}
			else {
				if(directionHashmapStrand.get(strandId)) {
					directionHashmapStrand.get(strandId).mapLabel.setMap(null);
			     }
				if(strandArray[strandId]){
					strandArray[strandId].mapLabel.setMap(null);
				}
			}
			
		}
		
		else{
			if(strandArray[strandId] && directionHashmapStrand.get(strandId) != undefined){
				strandArray[strandId].setMap(null);	
				strandArray[strandId].mapLabel.setMap(null);
				
				for(i = 0 ; i < directionHashmapStrand.get(strandId).length; i++) {
					if(strandId == directionHashmapStrand.get(strandId)[i].ID ) {
						directionHashmapStrand.get(strandId)[i].setMap(null);
						directionHashmapStrand.get(strandId).mapLabel.setMap(null);
					    routeDisplayStrand = [];
						if(strandArray[strandId]){			
							strandArray[strandId].mapLabel.setMap(null);
						}
				}
			}
				directionHashmapStrand.delete(strandId);
			}
			else if(strandArray[strandId] && directionHashmapStrand.get(strandId) == undefined){
				strandArray[strandId].setMap(null);	
				strandArray[strandId].mapLabel.setMap(null);
			}
			else  if (window[""+strandId][14] == "DRIVING" ) {	// no line of site case		 
				if(directionHashmapStrand.get(strandId) != undefined) {
					for(i = 0 ; i < directionHashmapStrand.get(strandId).length; i++) {
						if(strandId == directionHashmapStrand.get(strandId)[i].ID ) {
							directionHashmapStrand.get(strandId)[i].setMap(null);
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						    routeDisplayStrand = [];
							if(strandArray[strandId]){			
								strandArray[strandId].mapLabel.setMap(null);
							}
						}
					}
				}
				directionHashmapStrand.delete(strandId);
			}
			else {
				strandArray[strandId].setMap(null);
				strandArray[strandId].mapLabel.setMap(null);
			}
			
			
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
			$("#strandCheckAllBoq").prop('checked', false);		
		
		}
			var folderTubeId =$(this).parent().parent().parent().attr('id');	
			var strandChildrenLength =$("#"+folderTubeId+" > ul").children().length;
			var strandCheckedLength = $(this).parent().parent().parent().find("li > input[type='checkbox']:checked").length;
			
			//To check the strand folder when all strand children are checked
			if (strandChildrenLength == strandCheckedLength) {
				$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
			}

			if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberStrand:checked" ).length ==0) {
				$("#strandCheckAllBoq").prop('checked', false);				
			}
			else {
			
				$("#strandCheckAllBoq").prop('checked', true);				
			
			}
});

	
$(".FiberTubes").bind("change",function(){
	//console.log("clicked on tubes folder checkbox");
	
	if ($(this).is(':checked')){			
		
		//Hide the tube in case of driving
		$(this).parent().find(' > ul > li').each(function(){
			if(directionHashmapTube.get($(this).attr('id')) != undefined) {
				for(ii = 0; ii < directionHashmapTube.get($(this).attr('id')).length; ii++) {
					directionHashmapTube.get($(this).attr('id'))[ii].setMap(null); 
					directionHashmapTube.get($(this).attr('id')).mapLabel.setMap(null);
				}
			}
		});
		//Hide the strand in case of driving
		$(this).parent().find(' > ul > li >ul >li >ul >li').each(function(){
			if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
				for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
					directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
					directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
				}
			}
		});
		
		//Check the strand folder
		$(this).parent().find(' > ul > li >ul >li').each(function(){
			$(this).children('input:checkbox').prop('checked', true);		
		});
		
		$(this).parent().find('> ul > li').each(function(){
			var tubeId=$(this).attr('id');
			$(this).children('input:checkbox').prop('checked', true);
				
			if(tubeArray[tubeId]){
				tubeArray[tubeId].setMap(map);
				
				$("#"+tubeId).find(' > ul > li >ul >li').each(function(){
						
					$(this).children('input:checkbox').prop('checked', true);
					var strandId=$(this).attr('id');
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{
						var path=window["mapPoints_"+strandId];
						buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);	
					}
					
					/*if(!strandArray[strandId]){																		
						var path=window["mapPoints_"+strandId];
						buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,1.8,'purple',0);
					}
					strandArray[strandId].setMap(map);	
					*/
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
						strandArray[strandId].mapLabel.setMap(map);
					}
					else {
						strandArray[strandId].mapLabel.setMap(null);
					}
				});
		}

		else{

			var path=window["mapPoints_"+tubeId];
			buildPath(tubeId,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
			tubeArray[tubeId].setMap(map);
			   $("#"+tubeId).find(' > ul > li >ul >li').each(function(){
				
				var strandId=$(this).attr('id');
				$(this).find('input:checkbox:first').prop('checked', true);	
				var path=window["mapPoints_"+strandId];
				
				
				if(strandArray[strandId]){						
					strandArray[strandId].setMap(map);	
				}
				else{
					var path=window["mapPoints_"+strandId];
					buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
					strandArray[strandId].setMap(map);	
				}
				// Show and Hide the label of each strand depending on the checkLabel dropdown
				if($("#strandMapCheck_Labels").prop("checked")==true){
					strandArray[strandId].mapLabel.setMap(map);
				}
				else {
					strandArray[strandId].mapLabel.setMap(null);
				}
			
			});
			  }				
			
			// Show and Hide the label of each tube depending on the checkLabel dropdown
			if($("#tubeMapCheck_Labels").prop("checked")==true){
				tubeArray[tubeId].mapLabel.setMap(map);
			}
			else {
				tubeArray[tubeId].mapLabel.setMap(null);
			}
			
		
		});
		
	}
	else{
	
		$('#tubeCheckAllBoq').prop('checked', false);
		$('#strandCheckAllBoq').prop('checked', false);
		
		//Hide the strand in case of driving
		$(this).parent().find('> ul > li >ul >li >ul >li').each(function(){
			if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
				for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
					directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
					directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
				}
			}
		});
		
		$(this).parent().find(' > ul > li >ul >li').each(function(){
			$(this).children('input:checkbox').prop('checked', false);		
		});

			$(this).parent().find('> ul > li').each(function(){
				
				tubeId=$(this).attr('id');
				if(tubeArray[tubeId] && directionHashmapTube.get(tubeId) != undefined){
					tubeArray[tubeId].setMap(null);	
					tubeArray[tubeId].mapLabel.setMap(null);
					
					for(i = 0 ; i < directionHashmapTube.get(tubeId).length; i++) {
						if(tubeId == directionHashmapTube.get(tubeId)[i].ID ) {
							directionHashmapTube.get(tubeId)[i].setMap(null);
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						    routeDisplayTube = [];
							if(tubeArray[tubeId]){			
								tubeArray[tubeId].mapLabel.setMap(null);
							}
					}
				}
					directionHashmapTube.delete(tubeId);
				}
				else if(tubeArray[tubeId] && directionHashmapTube.get(tubeId) == undefined){
					tubeArray[tubeId].setMap(null);	
					tubeArray[tubeId].mapLabel.setMap(null);
				}
				else  if (window[""+tubeId][14] == "DRIVING" ) {	// no line of site case		 
					if(directionHashmapTube.get(tubeId) != undefined) {
						for(i = 0 ; i < directionHashmapTube.get(tubeId).length; i++) {
							if(tubeId == directionHashmapTube.get(tubeId)[i].ID ) {
								directionHashmapTube.get(tubeId)[i].setMap(null);
								directionHashmapTube.get(tubeId).mapLabel.setMap(null);
							    routeDisplayTube = [];
								if(tubeArray[tubeId]){			
									tubeArray[tubeId].mapLabel.setMap(null);
								}
							}
						}
					}
					directionHashmapTube.delete(tubeId);
				}
				else {
					tubeArray[tubeId].setMap(null);
					tubeArray[tubeId].mapLabel.setMap(null);
				}
				
				
				//tubeArray[tubeId].setMap(null);
				//tubeArray[tubeId].mapLabel.setMap(null);
								
				$(this).children('input:checkbox').prop('checked', false);
				
				$("#"+tubeId).find(' > ul > li >ul >li').each(function(){
					$(this).children('input:checkbox').prop('checked', false);
					var strandId=$(this).attr('id');
					strandArray[strandId].setMap(null);	
					strandArray[strandId].mapLabel.setMap(null);

				});
			});
			
		}
	if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTube:checked" ).length ==0) {
			$("#tubeCheckAllBoq").prop('checked', false);				
	}
	else {
		$("#tubeCheckAllBoq").prop('checked', true);				
		
	}
	if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
		$("#strandCheckAllBoq").prop('checked', false);				
	}
	else {
		$("#strandCheckAllBoq").prop('checked', true);				
		
	}
		/*$("#network_tree").find(".FiberTube:checked" ).each(function(){

				id=$(this).parent().attr('id');
				if(tubeArray[id]){						
					tubeArray[id].setMap(map);	
				}
				else{						
					var path=window["mapPoints_"+id];
					//buildTubePath(id,path);
					buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,2.3,'green',0);
					tubeArray[id].setMap(map);
				}	
		});
		*/
		$("#network_tree").find(".FiberStrand:checked" ).each(function(){

				id=$(this).parent().attr('id');
				if(strandArray[id]){						
					strandArray[id].setMap(map);	
				}
				else{						
					var path=window["mapPoints_"+id];
					//buildStrandPath(id,path);
					buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
					strandArray[id].setMap(map);									
				}		
		});		  
});
			
$(".TubeStrands").bind("change",function(){
	
	if ($(this).is(':checked')){
		
		//Hide the strand in case of driving
		$(this).parent().find(' > ul > li').each(function(){
			if(directionHashmapStrand.get($(this).attr('id')) != undefined) {
				for(ii = 0; ii < directionHashmapStrand.get($(this).attr('id')).length; ii++) {
					directionHashmapStrand.get($(this).attr('id'))[ii].setMap(null); 
					directionHashmapStrand.get($(this).attr('id')).mapLabel.setMap(null);
				}
			}
		});
		
		$(this).parent().find('> ul > li').each(function(){
			var strandId=$(this).attr('id');
			$(this).children('input:checkbox').prop('checked', true);
				
			if(strandArray[strandId]){
				strandArray[strandId].setMap(map);
			}
			else{
				var path=window["mapPoints_"+strandId];
				buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
				strandArray[strandId].setMap(map);
			}				
		
			// Show and Hide the label of each tube depending on the checkLabel dropdown
			if($("#strandMapCheck_Labels").prop("checked")==true){
				strandArray[strandId].mapLabel.setMap(map);
			}
			else {
				strandArray[strandId].mapLabel.setMap(null);
			}
						
		});	
		
		
	}
	else{
		
		$('#strandCheckAllBoq').prop('checked', false);
		
		$(this).parent().find('> ul > li').each(function(){
			
			strandId=$(this).attr('id');
			if(strandArray[strandId] && directionHashmapStrand.get(strandId) != undefined){
				strandArray[strandId].setMap(null);	
				strandArray[strandId].mapLabel.setMap(null);
				
				for(i = 0 ; i < directionHashmapStrand.get(strandId).length; i++) {
					if(strandId == directionHashmapStrand.get(strandId)[i].ID ) {
						directionHashmapStrand.get(strandId)[i].setMap(null);
						directionHashmapStrand.get(strandId).mapLabel.setMap(null);
					    routeDisplayStrand = [];
						if(strandArray[strandId]){			
							strandArray[strandId].mapLabel.setMap(null);
						}
				}
			}
				directionHashmapStrand.delete(strandId);
			}
			else if(strandArray[strandId] && directionHashmapStrand.get(strandId) == undefined){
				strandArray[strandId].setMap(null);	
				strandArray[strandId].mapLabel.setMap(null);
			}
			else  if (window[""+strandId][14] == "DRIVING" ) {	// no line of site case		 
				if(directionHashmapStrand.get(strandId) != undefined) {
					for(i = 0 ; i < directionHashmapStrand.get(strandId).length; i++) {
						if(strandId == directionHashmapStrand.get(strandId)[i].ID ) {
							directionHashmapStrand.get(strandId)[i].setMap(null);
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						    routeDisplayStrand = [];
							if(strandArray[strandId]){			
								strandArray[strandId].mapLabel.setMap(null);
							}
						}
					}
				}
				directionHashmapStrand.delete(strandId);
			}
			else {
				strandArray[strandId].setMap(null);
				strandArray[strandId].mapLabel.setMap(null);
			}			
							
			$(this).children('input:checkbox').prop('checked', false);
			
			
		});
	}
	
	if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberStrand:checked" ).length ==0) {
		$("#strandCheckAllBoq").prop('checked', false);				
	}
	else {
		$("#strandCheckAllBoq").prop('checked', true);				
			
	}	
	
 });				  

}	
////>>>>>>>>>> function checkbox events for filtering of <<<< Distribution Boards >>>> ////
	
 function DistributionBoardCheckFilter(Id,folder){
		$("#"+Id).children('input').bind("change",function() {
			var folderID = $(this).parents().eq(4).attr('id');
			if(folder == "DB"){
				folderID = "DB";
				Id = Id.split("_showDB")[0];
			}
			//console.log("entered if folderID "+ folderID);
			if ($(this).is(':checked')){
				markersDistBoard[Id].setMap(map);					
				markerClusterDistBoard.addMarker(markersDistBoard[Id]);
			}
			else{
				//$('.AllDistBoards').prop('checked', false);
				if(folderID == "initial_ul_CurrentPhysicalLayer"){
					$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",false);				
				}
				else {
					$("#DistributionBoard_f_"+folderID+ " > .AllDistBoards").prop("checked",false);				
				}
				markersDistBoard[Id].setMap(null);							
				markerClusterDistBoard.removeMarker(markersDistBoard[Id]);			
			}
		 if ($(this).parents().eq(2).find('.DistBoard:checked').length == $(this).parents().eq(2).find('.DistBoard').length) {
				$(this).parents().eq(2).children('input').prop('checked', true);
			 }
			 else{
				$(this).parents().eq(2).children('input').prop('checked', false);
			 }	
		 
			if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked" ).length == 0 ){
				$("#distBoardCheckAllBoq").prop("checked",false);			
			}
			else{
				$("#distBoardCheckAllBoq").prop("checked",true);			
			}
		 
		});
	}	
    
	function AllDistributionBoardFilter(folderTree,folderID){
			$(".AllDistBoards").bind("change",function() {
				markerClusterDistBoard.clearMarkers();
				if ($(this).is(':checked')){
					$(this).parent().find('.DistBoard').each(function(){				
						$(this).prop('checked', true);
						// for map
						id=$(this).parent().attr('id');
						if(folderTree == "DB"){
							id = id.split("_showDB")[0];
						}
						
						if(markersDistBoard[id].getMap()==null){
							markersDistBoard[id].setMap(map);			
							markerClusterDistBoard.addMarker(markersDistBoard[id]);
						}
					});
					
					/*$("#"+folderTree).find(".DistBoard:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(markersDistBoard[id].getMap()==null){
							markersDistBoard[id].setMap(map);			
							markerClusterDistBoard.addMarker(markersDistBoard[id]);
						}									
					});	*/
					
					if($("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").is(":checked") ) {	
						$("#distBoardCheckAllBoq").prop("checked",true);
					}				
				}
				
				else{		
					$(this).parent().find('.DistBoard').each(function(){				
						$(this).prop('checked', false);
						// for map
						id=$(this).parent().attr('id');
						if(folderTree == "DB"){
							id = id.split("_showDB")[0];
						}
						markersDistBoard[id].setMap(null);							
						markerClusterDistBoard.removeMarker(markersDistBoard[id]);	
			
					});

					$("#distBoardCheckAllBoq").prop("checked",false);
					
					if($("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").is(":checked") ) {	
						$("#distBoardCheckAllBoq").prop("checked",true);
					}
					
					/*$("#"+folderTree).find(".DistBoard:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(markersDistBoard[id].getMap()==null){
							markersDistBoard[id].setMap(map);			
							markerClusterDistBoard.addMarker(markersDistBoard[id]);
						}		
					});	*/																 
				}	
			});
		}
																																								  

//function checkbox events for filtering of junctions  ////
function junctionCheckFilter(physicalLayer,manholeId){

	if(physicalLayer=="Manhole") {

		$('.JctManholes').bind("change",function() {
	
			parentLi=$(this).parent().parent().parent();
			console.log("checked "+parentLi.find("li > input[type='checkbox']:checked").length);
			console.log("checked "+parentLi.find('li').length);
			if (parentLi.find("li > input[type='checkbox']:checked").length == parentLi.find('li').length) {
				$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
			}
			else {
				$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
	
			}		
		});	
		
	$(".ManholeJct").bind("change",function(){
		if ($(this).is(':checked')){
		
			$(this).parent().find(' > ul > li').each(function(){
				$(this).children('input:checkbox').prop('checked', true);		
				//var junctionId=$(this).attr('id');
			});
		}
		else{
			$(this).parent().find(' > ul > li').each(function(){
				$(this).children('input:checkbox').prop('checked', false);
			//	var junctionId=$(this).attr('id');
						//console.log(" //////////junctionId"+junctionId);
			});
		}				
	});
	
	}
	else {
	
	$('.JctHandholes').bind("change",function() {
	//$("#"+junctionID).bind("change",function() {				
	
		parentLi=$(this).parent().parent().parent();
		if (parentLi.find("li > input[type='checkbox']:checked").length == parentLi.find('li').length) {
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
		}
		else {
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
	
		}
});


		
	$(".HandholeJct").bind("change",function(){
	
				if ($(this).is(':checked')){
		
					$(this).parent().find(' > ul > li').each(function(){
						
						$(this).children('input:checkbox').prop('checked', true);		
						//var junctionId=$(this).attr('id');
	
					});
				}
				else{
				
					$(this).parent().find(' > ul > li').each(function(){
						$(this).children('input:checkbox').prop('checked', false);
						//var junctionId=$(this).attr('id');
						//console.log(" //////////junctionId"+junctionId);
						
					});
					
				}

				
	});
	}
	
}

function pathCheckFilter(Target,type,Id,drawingTypeIndex,pathArray,allPathArray,drivingPathArray,pathRouteDisplay,chldrnClass,chldrnPathArray,chldrnDrivPathArray,chldrnAllPathArray) {

	if(Target.Trgt=="Tube") {
		var chldrnTarget = TargetStrand;
	}
	else if(Target.Trgt=="FiberPath_f_") {
		var chldrnTarget = TargetTube;
	}
	else if(Target.Trgt=="Trench_f_") {
		var chldrnTarget = TargetDuct;
	}	
	
if(type=="parentFolderCheck" || type == "parentFolderCheck__showPath") {
	
	if(Target=="initial_ul") { // Case of main Trench Folder, Fiber Path Folder , Backbone / Metro / Access Folder
		
	$("#"+Id).children('input').bind("change",function() {
		if ($(this).is(':checked')){				
				
			$(this).parent().find('input:checkbox').each(function(){
				$(this).prop('checked', true);
				if($(this).parent().hasClass('FIBER')){
					var fiberId=$(this).parent().attr('id');
					if(fiberId.includes("__showPath") == true ){
						fiberId = fiberId.split("__showPath")[0];
					}
					if(directionHashmap.get(fiberId) != undefined) {
						for(ii = 0; ii < directionHashmap.get(fiberId).length; ii++) {
							directionHashmap.get(fiberId)[ii].setMap(null); 
		                    directionHashmap.get(fiberId).mapLabel.setMap(null);
						}
					}
					if(fiberArray[fiberId]){						
						fiberArray[fiberId].setMap(map);	 
					}
					else{   														
						var path=window["mapPoints_"+fiberId];							 
						buildPath(fiberId,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberId][22]],0.7,4.5,'blue',13);
						fiberArray[fiberId].setMap(map);
					}
					
					// Show and Hide the label of each cable depending on the checkLabel dropdown
					if($("#fiberMapCheck_Labels").prop("checked")==true){
						var fiberId=$(this).parent().attr('id');
						if(fiberId.includes("__showPath") == true ){
							fiberId = fiberId.split("__showPath")[0];
						}
						fiberArray[fiberId].mapLabel.setMap(map);
					}
					else {
						fiberArray[fiberId].mapLabel.setMap(null);
					}
					$("#fiberCheckAllBoq").prop("checked",true);
				}
				else if($(this).parent().hasClass('TUBE')){
					var tubeId=$(this).parent().attr('id');
					if(tubeId.includes("__showPath") == true ){
						tubeId = tubeId.split("__showPath")[0];
					}
					if(directionHashmapTube.get(tubeId) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
							directionHashmapTube.get(tubeId)[ii].setMap(null); 
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						}
					}
					if(tubeArray[tubeId]){						
						tubeArray[tubeId].setMap(map);	
					}
					else{	 
						buildPath(tubeId,window["mapPoints_"+tubeId],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[tubeId].setMap(map);
					}
					
					// Show and Hide the label of each tube depending on the checkLabel dropdown
					if($("#tubeMapCheck_Labels").prop("checked")==true){
						var tubeId=$(this).parent().attr('id');
						if(tubeId.includes("__showPath") == true ){
						tubeId = tubeId.split("__showPath")[0];
						}
						tubeArray[tubeId].mapLabel.setMap(map);
					}
					else {
						tubeArray[tubeId].mapLabel.setMap(null);
					}	
					$('#tubeCheckAllBoq').prop('checked', true);						
			}
			else if($(this).parent().hasClass('STRAND')){
					var strandId=$(this).parent().attr('id');
					if(strandId.includes("__showPath") == true ){
						strandId = strandId.split("__showPath")[0];
					}
					if(directionHashmapStrand.get(strandId) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
							directionHashmapStrand.get(strandId)[ii].setMap(null); 
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						}
					}
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{
						buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);
					}	
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
					    var strandId=$(this).parent().attr('id');
						if(strandId.includes("__showPath") == true){
						strandId = strandId.split("__showPath")[0];
						}
						strandArray[strandId].mapLabel.setMap(map);
					}
					else {
						strandArray[strandId].mapLabel.setMap(null);
					}	
					$('#strandCheckAllBoq').prop('checked', true);
			}					
				else if($(this).parent().hasClass('Trench')){
					trenchId=$(this).parent().attr('id');
					if(directionHashmapTrench.get(trenchId) != undefined) {
						for(ii = 0; ii < directionHashmapTrench.get(trenchId).length; ii++) {
							directionHashmapTrench.get(trenchId)[ii].setMap(null); 
							directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
						}
					}
					if(trenchArray[trenchId]){						
						trenchArray[trenchId].setMap(map);	
					}
					else{
						buildPath(trenchId,window["mapPoints_"+trenchId],trenchArray,allTrenches,"Trench_f_",'brown',0.4,20,'brown',1);
						trenchArray[trenchId].setMap(map);
					}	
					// Show and Hide the label of each trench depending on the checkLabel dropdown
					if($("#trenchMapCheck_Labels").prop("checked")==true){
							trenchArray[trenchId].mapLabel.setMap(map);
					}
					else {
							trenchArray[trenchId].mapLabel.setMap(null);
					}	
				 }	
				else if($(this).parent().hasClass('Duct')){
					ductId=$(this).parent().attr('id');
					if(directionHashmapDuct.get(ductId) != undefined) {
						for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
							directionHashmapDuct.get(ductId)[ii].setMap(null); 
							directionHashmapDuct.get(ductId).mapLabel.setMap(null);
						}
					}
					if(ductArray[ductId]){						
						ductArray[ductId].setMap(map);	
					}
					else{
						buildPath(ductId,window["mapPoints_"+ductId],ductArray,allDucts,"Duct",'#8e8080',0.6,10,'#8e8080',1);
						ductArray[ductId].setMap(map);
					}	
					// Show and Hide the label of each duct depending on the checkLabel dropdown
					if($("#ductMapCheck_Labels").prop("checked")==true){
						ductArray[ductId].mapLabel.setMap(map);
					}
					else {
						ductArray[ductId].mapLabel.setMap(null);
					}							
				}		
					
				});
		}// end check  case 
			
		// uncheck case 
			else{		
			
				$(this).parent().find('input:checkbox').each(function(){
					$(this).prop('checked', false);
					if($(this).parent().hasClass('FIBER')){
						var fiberId=$(this).parent().attr('id');
						if(fiberId.includes("__showPath") == true ){
							fiberId = fiberId.split("__showPath")[0];
						}
						if(fiberArray[fiberId]){
							if(directionHashmap.get(fiberId) != undefined) {
								for(ii = 0; ii < directionHashmap.get(fiberId).length; ii++) {
									directionHashmap.get(fiberId)[ii].setMap(null); 
				                    directionHashmap.get(fiberId).mapLabel.setMap(null);
								}
							}
							fiberArray[fiberId].setMap(null);	
							fiberArray[fiberId].mapLabel.setMap(null);
						}
						$("#fiberCheckAllBoq").prop("checked",false);	
					}
					else if($(this).parent().hasClass('TUBE')){	
						var tubeId = $(this).parent().attr('id');
						if(tubeId.includes("__showPath") == true ){
							tubeId = tubeId.split("__showPath")[0];
						}	
						if(directionHashmapTube.get(tubeId) != undefined) {
							for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
								directionHashmapTube.get(tubeId)[ii].setMap(null); 
								directionHashmapTube.get(tubeId).mapLabel.setMap(null);
							}
						}
						tubeArray[tubeId].setMap(null);
						tubeArray[tubeId].mapLabel.setMap(null);
						$("#tubeCheckAllBoq").prop("checked",false);
					}
					else if($(this).parent().hasClass('STRAND')){	
						var strandId = $(this).parent().attr('id');
						if(strandId.includes("__showPath") == true ){
							strandId = strandId.split("__showPath")[0];
						}
						if(directionHashmapStrand.get(strandId) != undefined) {
							for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
								directionHashmapStrand.get(strandId)[ii].setMap(null); 
								directionHashmapStrand.get(strandId).mapLabel.setMap(null);
							}
						}
						strandArray[strandId].setMap(null);
						strandArray[strandId].mapLabel.setMap(null);
						$("#strandCheckAllBoq").prop("checked",false);				
					}
					else if($(this).parent().hasClass('Trench')){
						trenchId=$(this).parent().attr('id');
						if(directionHashmapTrench.get(trenchId) != undefined) {
							for(ii = 0; ii < directionHashmapTrench.get(trenchId).length; ii++) {
								directionHashmapTrench.get(trenchId)[ii].setMap(null); 
								directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
							}
						}
						trenchArray[trenchId].setMap(null);	
						trenchArray[trenchId].mapLabel.setMap(null);	
						$("#trenchCheckAllBoq").prop("checked",false);				
					 }	
					else if($(this).parent().hasClass('Duct')){
						ductId=$(this).parent().attr('id');
						if(directionHashmapDuct.get(ductId) != undefined) {
							for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
								directionHashmapDuct.get(ductId)[ii].setMap(null); 
								directionHashmapDuct.get(ductId).mapLabel.setMap(null);
							}
						}
						ductArray[ductId].setMap(null);	
						ductArray[ductId].mapLabel.setMap(null);
						$("#ductCheckAllBoq").prop("checked",false);				
					}		
				});
				
				if($("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTubes:checked" ).length ==0) {
					$("#tubeCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#tubeCheckAllBoq").prop('checked', true);				
				}
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
					$("#strandCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#strandCheckAllBoq").prop('checked', true);				
				}
			
				/*
				$("#network_tree").find(".FiberCable:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(fiberArray[id]){						
							fiberArray[id].setMap(map);	
						}
						else{														
							var path=window["mapPoints_"+id];
							//buildFiberPath(id,path);
							buildPath(id,path,fiberArray,allFiberCables,"FiberPath_f_",'#4986E7',0.7,3.5,'blue',13);
							fiberArray[id].setMap(map);
						}	
					});
					$("#network_tree").find(".FiberTube:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(tubeArray[id]){						
							tubeArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildTubePath(id,path);
							buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,2.3,'green',0);
							tubeArray[id].setMap(map);
						}	
					});
						$("#network_tree").find(".FiberStrand:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(strandArray[id]){						
							strandArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildStrandPath(id,path);
							buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,1.8,'purple',0);
							strandArray[id].setMap(map);									
						}		
					});
						
						$("#network_tree").find(".TRENCH:checked" ).each(function(){
							trenchId=$(this).parent().attr('id');
							if(trenchArray[trenchId]){						
								trenchArray[trenchId].setMap(map);	
							}
									else{
										//buildTrench(trenchId,window["mapPoints_"+trenchId]);
										buildPath(trenchId,window["mapPoints_"+trenchId],trenchArray,allTrenches,"Trench_f_",'brown',0.4,15,'brown',1);
										trenchArray[trenchId].setMap(map);
									}
						});
						
				*/
						
			}
			
			if(type == "parentFolderCheck__showPath") {
				if($(".MetroFiber").is(":checked") || $(".BackboneFiber").is(":checked") || $('.AccessFiber').is(':checked') ) {	
					$('.AllFiberCables__showPath').prop('checked', true);
				}
				else {
					$('.AllFiberCables__showPath').prop('checked', false);
				}
			}
			
			else {
				if ($('.BackboneFiber').is(':checked') && $('.MetroFiber').is(':checked') && $('.AccessFiber').is(':checked')){			
					$('.AllFiberCables').prop('checked', true);
				}
				else {
					$('.AllFiberCables').prop('checked', false);
				}
				if($(".MetroFiber").is(":checked") || $(".BackboneFiber").is(":checked") || $('.AccessFiber').is(':checked') ) {	
					$("#fiberCheckAllBoq").prop("checked",true);
				}	
			}
		});	
	}
	//Folders case (Strands,Ducts,Tubes,Cables,Trenchs folders)
	else {		
		$("#"+Id+"_f").children('input').bind("change",function() {
			console.log("check fiber folder");
			if ($(this).is(':checked')){
				$("#"+Id+"_f").find('> ul > li').each(function(){
					var pathId=$(this).attr('id');
					if(pathId.includes("__showPath") ){
						pathId = pathId.split("__showPath")[0];
					}
					//Hide the path in case of driving
					if(drivingPathArray.get(pathId) != undefined) {
						for(ii = 0; ii < drivingPathArray.get(pathId).length; ii++) {
							drivingPathArray.get(pathId)[ii].setMap(null); 
							drivingPathArray.get(pathId).mapLabel.setMap(null);
						}
					}
					$(this).children('input:checkbox').prop('checked', true);
					if(pathArray[pathId]){
						pathArray[pathId].setMap(map);
					}
					else{
						buildPath(pathId,window["mapPoints_"+pathId],pathArray,allPathArray,Target.Trgt,Target.color,0.7,Target.strokeWeight,Target.color,Target.IdNb);
						pathArray[pathId].setMap(map);
					}	
					// Show and Hide the label of each path depending on the checkLabel dropdown
					if($("#"+Target.labelId).prop("checked")==true){
						pathArray[pathId].mapLabel.setMap(map);
					}
					else {
						pathArray[pathId].mapLabel.setMap(null);
					}
								
				});	
				
				if(Target.Trgt !="Duct" && Target.Trgt !="Strand" && type != "parentFolderCheck__showPath" ) {
					
					//Check the strand folder
					$(this).parent().find(' > ul > li >ul >li').each(function(){
						$(this).children('input:checkbox').prop('checked', true);		
					});
					$(this).parent().find(' > ul > li >ul >li >ul >li').each(function(){
						var chldrnId=$(this).attr('id');
						   //Hide path in case of driving
						   if(chldrnDrivPathArray.get(chldrnId) != undefined) {
								for(ii = 0; ii < chldrnDrivPathArray.get(chldrnId).length; ii++) {
									chldrnDrivPathArray.get(chldrnId)[ii].setMap(null); 
									chldrnDrivPathArray.get(chldrnId).mapLabel.setMap(null);
								}
							}
						   
						$(this).children('input:checkbox').prop('checked', true);		
							if(chldrnPathArray[chldrnId]){						
								chldrnPathArray[chldrnId].setMap(map);	
							}
							else{
								buildPath(chldrnId,window["mapPoints_"+chldrnId],chldrnPathArray,chldrnAllPathArray,chldrnTarget.Trgt,chldrnTarget.color,0.7,chldrnTarget.strokeWeight,chldrnTarget.color,chldrnTarget.IdNb);
								chldrnPathArray[chldrnId].setMap(map);
							} 
							
							// Show and Hide the label of each path depending on the checkLabel dropdown
							if($("#"+chldrnTarget.labelId).prop("checked")==true){
								chldrnPathArray[chldrnId].mapLabel.setMap(map);
							}
							else {
								chldrnPathArray[chldrnId].mapLabel.setMap(null);
							}	
					   });
				}				
			}
			else{
				$(this).parent().find('> ul > li').each(function(){
				var ID=$(this).attr('id');
				if (ID.includes("__showPath")){
					ID = ID.split("__showPath")[0]; 
				}
					if(pathArray[ID] && drivingPathArray.get(ID) != undefined){
						pathArray[ID].setMap(null);	
						pathArray[ID].mapLabel.setMap(null);
						
						for(i = 0 ; i < drivingPathArray.get(ID).length; i++) {
							if(ID == drivingPathArray.get(ID)[i].ID ) {
								drivingPathArray.get(ID)[i].setMap(null);
								drivingPathArray.get(ID).mapLabel.setMap(null);
								pathRouteDisplay = [];
								if(pathArray[ID]){			
									pathArray[ID].mapLabel.setMap(null);
								}
						}
					}
						drivingPathArray.delete(ID);
					}
					else if(pathArray[ID] && drivingPathArray.get(ID) == undefined){
						pathArray[ID].setMap(null);	
						pathArray[ID].mapLabel.setMap(null);
					}
					else  if (window[""+ID][drawingTypeIndex] == "DRIVING" ) {	// no line of site case		 
						if(drivingPathArray.get(ID) != undefined) {
							for(i = 0 ; i < drivingPathArray.get(ID).length; i++) {
								if(ID == drivingPathArray.get(ID)[i].ID ) {
									drivingPathArray.get(ID)[i].setMap(null);
									drivingPathArray.get(ID).mapLabel.setMap(null);
									pathRouteDisplay = [];
									if(pathArray[ID]){			
										pathArray[ID].mapLabel.setMap(null);
									}
								}
							}
						}
						drivingPathArray.delete(ID);
					}
					else {
						pathArray[ID].setMap(null);
						pathArray[ID].mapLabel.setMap(null);
					}			
					$(this).children('input:checkbox').prop('checked', false);				
				});
				
				if(Target.Trgt !="Duct" && Target.Trgt !="Strand" && type != "parentFolderCheck__showPath") {
					$(this).parent().find(' > ul > li >ul >li').each(function(){
						$(this).children('input:checkbox').prop('checked', false);		
					});
					
					$(this).parent().find(' > ul > li >ul >li >ul >li').each(function(){
						   var chldrnId=$(this).attr('id');					   
						   //Hide path in case of driving
						   if(chldrnDrivPathArray.get(chldrnId) != undefined) {
								for(ii = 0; ii < chldrnDrivPathArray.get(chldrnId).length; ii++) {
									chldrnDrivPathArray.get(chldrnId)[ii].setMap(null); 
									chldrnDrivPathArray.get(chldrnId).mapLabel.setMap(null);
								}
							}						   
							$(this).children('input:checkbox').prop('checked', false);
							chldrnPathArray[chldrnId].setMap(null);	
							chldrnPathArray[chldrnId].mapLabel.setMap(null);	
					   });
				}
			}
			
			if(type == "parentFolderCheck__showPath"){
				var folderId = $(this).parent().parent().parent().attr('id');	
				var childrenLength =$("#"+folderId+" > ul").children().length;
				var checkedLength = $("#"+folderId+" > ul").find("li > input[id='BackboneMetroAcc__showPath']:checked").length;				

				//To check the folder when all  children are checked
				if (childrenLength == checkedLength) {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
				}
				else {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);

				}
			}	
			
		else {	
			if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberStrand:checked" ).length ==0) {
				$("#strandCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#strandCheckAllBoq").prop('checked', true);				
			}	
			if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTube:checked" ).length ==0) {
				$("#tubeCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#tubeCheckAllBoq").prop('checked', true);
			}
			/*if( $("#FiberPath_f__showPath").find(".FiberTube:checked" ).length ==0) {
			    $("#tubeCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#tubeCheckAllBoq").prop('checked', true);
			}*/
			if( $("#Trench_f_CurrentPhysicalLayer").find(".TRENCH:checked" ).length ==0) {
				$("#trenchCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#trenchCheckAllBoq").prop('checked', true);				
			}
			if( $("#Trench_f_CurrentPhysicalLayer").find(".DUCT:checked" ).length ==0) {
				$("#ductCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#ductCheckAllBoq").prop('checked', true);				
			}
		}
		 });				
	}
	
	
}


//Single path case
else {
	
	$("#"+Id).children('input').bind("change",function() {
		
		if(Id.includes("__showPath")){
			Id = Id.split("__showPath")[0];
		}
		
		if ($(this).is(':checked')){
			if (window[""+Id][drawingTypeIndex] == "DRIVING") {
				buildDrivingPath(Id,window["mapPoints_"+Id],Target.Trgt,Target.color,drivingPathArray);
			}
			else if(pathArray[Id]){
				pathArray[Id].setMap(map);	
			}
			else{
				if (window[""+Id][drawingTypeIndex] == "DRIVING") {
					buildDrivingPath(Id,window["mapPoints_"+Id],Target.Trgt,Target.color,drivingPathArray);
				}
				else {
					var path=window["mapPoints_"+Id];
					if(Target.Trgt == "FiberPath_f_"){
						console.log("entered if Target "+Target.Trgt);
						buildPath(Id,path,pathArray,allPathArray,Target.Trgt,window['FiberColor_'+window[''+Id][22]],0.7,Target.strokeWeight,Target.color,Target.IdNb);	
					}else{
						buildPath(Id,path,pathArray,allPathArray,Target.Trgt,Target.color,0.7,Target.strokeWeight,Target.color,Target.IdNb);
					}					
					pathArray[Id].setMap(map);
				}
			}
			
			// Show and Hide the label of each path depending on the checkLabel dropdown
			if($("#"+Target.labelId).prop("checked")==true){
				if(drivingPathArray.get(Id)) {
					drivingPathArray.get(Id).mapLabel.setMap(map);
			    }
				if(pathArray[Id]){
					pathArray[Id].mapLabel.setMap(map);
				}
			}
			else {
				if(drivingPathArray.get(Id)) {
					drivingPathArray.get(Id).mapLabel.setMap(null);
			     }
				if(pathArray[Id]){
					pathArray[Id].mapLabel.setMap(null);
				}
			}
				
			if(Target.Trgt !="Duct" && Target.Trgt !="Strand" && type != "showPath") {
				$(this).parent().find('input:checkbox').each(function(){
					$(this).prop('checked', true);
					if($(this).parent().hasClass(''+chldrnClass)){						
						var Id=$(this).parent().attr('id');
						//Hide the children path in case of driving
						if(chldrnDrivPathArray.get($(this).parent().attr('id')) != undefined) {
							for(ii = 0; ii < chldrnDrivPathArray.get($(this).parent().attr('id')).length; ii++) {
								chldrnDrivPathArray.get($(this).parent().attr('id'))[ii].setMap(null); 
								chldrnDrivPathArray.get($(this).parent().attr('id')).mapLabel.setMap(null);
							}
						}
						
						if(chldrnPathArray[Id]){						
							chldrnPathArray[Id].setMap(map);	
						}
						else{
							buildPath(Id,window["mapPoints_"+Id],chldrnPathArray,chldrnAllPathArray,chldrnTarget.Trgt,chldrnTarget.color,0.7,chldrnTarget.strokeWeight,chldrnTarget.color,chldrnTarget.IdNb);
							chldrnPathArray[Id].setMap(map);
						}				
					
						// Show and Hide the label of each path depending on the checkLabel dropdown
						if($("#"+chldrnTarget.labelId).prop("checked")==true){  
							chldrnPathArray[Id].mapLabel.setMap(map);
						}
						else {
							chldrnPathArray[Id].mapLabel.setMap(null);
						}	
					}					
				
					if(Target.Trgt =="FiberPath_f_") {
						  if($(this).parent().hasClass('STRAND')){
							  var strandId=$(this).parent().attr('id');
							  //Hide the strand path in case of driving
								if(directionHashmapStrand.get($(this).parent().attr('id')) != undefined) {
									for(ii = 0; ii < directionHashmapStrand.get($(this).parent().attr('id')).length; ii++) {
										directionHashmapStrand.get($(this).parent().attr('id'))[ii].setMap(null); 
										directionHashmapStrand.get($(this).parent().attr('id')).mapLabel.setMap(null);
									}
								}
								
								if(strandArray[strandId]){						
									strandArray[strandId].setMap(map);	
								}
								else{
									buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
									strandArray[strandId].setMap(map);
								}	
								// Show and Hide the label of each strand depending on the checkLabel dropdown
								if($("#strandMapCheck_Labels").prop("checked")==true){
									strandArray[strandId].mapLabel.setMap(map);
								}
								else {
									strandArray[strandId].mapLabel.setMap(null);
								}							
						}					
					}//end fiber case
			});	

			}
		}// end check case
	
	// uncheck case
		else{
			if(pathArray[Id] && drivingPathArray.get(Id) != undefined){
				pathArray[Id].setMap(null);	
				pathArray[Id].mapLabel.setMap(null);
				
				for(i = 0 ; i < drivingPathArray.get(Id).length; i++) {
					if(Id == drivingPathArray.get(Id)[i].ID ) {
						drivingPathArray.get(Id)[i].setMap(null);
						drivingPathArray.get(Id).mapLabel.setMap(null);
						pathRouteDisplay = [];
						if(pathArray[Id]){			
							pathArray[Id].mapLabel.setMap(null);
						}
				}
			}
				drivingPathArray.delete(Id);
			}
			else if(pathArray[Id] && drivingPathArray.get(Id) == undefined){
				pathArray[Id].setMap(null);	
				pathArray[Id].mapLabel.setMap(null);
			}
			else if (window[""+Id][drawingTypeIndex] == "DRIVING" ) {			 
				if(drivingPathArray.get(Id) != undefined) {
					for(i = 0 ; i < drivingPathArray.get(Id).length; i++) {
						if(Id == drivingPathArray.get(Id)[i].ID ) {
							drivingPathArray.get(Id)[i].setMap(null);
							drivingPathArray.get(Id).mapLabel.setMap(null);
							pathRouteDisplay = [];
							if(pathArray[Id]){			
								pathArray[Id].mapLabel.setMap(null);
							}
						}
					}
				}
				drivingPathArray.delete(Id);
			}
			else {
				pathArray[Id].setMap(null);
				pathArray[Id].mapLabel.setMap(null);
			}
			
			
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
			
			if(Target.Trgt !="Duct" && Target.Trgt !="Strand"  && type != "showPath"  ) {
			
				$(this).parent().find('input:checkbox').each(function(){
					$(this).prop('checked', false);
					if($(this).parent().hasClass(''+chldrnClass)){
					
						//Hide the children path in case of driving
						if(chldrnDrivPathArray.get($(this).parent().attr('id')) != undefined) {
							for(ii = 0; ii < chldrnDrivPathArray.get($(this).parent().attr('id')).length; ii++) {
								chldrnDrivPathArray.get($(this).parent().attr('id'))[ii].setMap(null); 
								chldrnDrivPathArray.get($(this).parent().attr('id')).mapLabel.setMap(null);
							}
						}
						
						chldrnPathArray[$(this).parent().attr('id')].setMap(null);	
						chldrnPathArray[$(this).parent().attr('id')].mapLabel.setMap(null);
					}
					
					if(Target.Trgt =="FiberPath_f_" || Target.Trgt =="FiberPath_f_showPath") {					
						if($(this).parent().hasClass('STRAND')){	
							
							if(directionHashmapStrand.get($(this).parent().attr('id')) != undefined) {
								for(ii = 0; ii < directionHashmapStrand.get($(this).parent().attr('id')).length; ii++) {
									directionHashmapStrand.get($(this).parent().attr('id'))[ii].setMap(null); 
									directionHashmapStrand.get($(this).parent().attr('id')).mapLabel.setMap(null);
								}
							}
							strandArray[$(this).parent().attr('id')].setMap(null);	
							strandArray[$(this).parent().attr('id')].mapLabel.setMap(null);
						}
					}
				
				});
			}
		}
		
			var folderId =$(this).parent().parent().parent().attr('id');	
			var childrenLength =$("#"+folderId+" > ul").children().length;
			
		if(Target.Trgt =="Tube" ) {					

				var checkedLength = $(this).parent().parent().parent().find(".FiberTube:checked").length;
				
				//To check the folder when all  children are checked
				if (childrenLength == checkedLength) {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
				}

				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTube:checked" ).length ==0) {
					$("#tubeCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#tubeCheckAllBoq").prop('checked', true);				
				}
				if( $("#FiberPath_f_showPath").find(".FiberTube:checked" ).length ==0) {
					$("#tubeCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#tubeCheckAllBoq").prop('checked', true);				
				}
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
					$("#strandCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#strandCheckAllBoq").prop('checked', true);				
				
				}
			}
		else if(Target.Trgt=="Trench_f_") {
				var checkedLength = $(this).parent().parent().parent().find(".TRENCH:checked").length;

				//To check the folder when all  children are checked
				if (childrenLength == checkedLength) {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
				}

				if( $("#Trench_f_CurrentPhysicalLayer").find(".TRENCH:checked" ).length ==0) {
					$("#trenchCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#trenchCheckAllBoq").prop('checked', true);				
				
				}
				
				if( $("#Trench_f_CurrentPhysicalLayer").find(".DUCT:checked" ).length ==0) {
					$("#ductCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#ductCheckAllBoq").prop('checked', true);				
				}
			}
		else if(Target.Trgt=="Duct") {
			var checkedLength = $(this).parent().parent().parent().find("li > input[type='checkbox']:checked").length;
			
			//To check the folder when all  children are checked
			if (childrenLength == checkedLength) {
				$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
			}
			else {
				$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
			}
			
			if( $("#Trench_f_CurrentPhysicalLayer").find(".DUCT:checked").length ==0) {
				$("#ductCheckAllBoq").prop('checked', false);				
			}
			else {
				$("#ductCheckAllBoq").prop('checked', true);				
			
			}
			
		}
		else if(Target.Trgt=="FiberPath_f_" ) {
				var checkedLength = $(this).parent().parent().parent().find(".FiberCable:checked").length;
				//To check the folder when all  children are checked
				if (childrenLength == checkedLength) {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
				}
				else {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);
				}
				
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberCable:checked" ).length ==0){
					$("#fiberCheckAllBoq").prop("checked",false);
				}
				else{
					$("#fiberCheckAllBoq").prop("checked",true);
				}
				
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberTubes:checked" ).length ==0) {
					$("#tubeCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#tubeCheckAllBoq").prop('checked', true);				
				}
				
				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".TubeStrands:checked" ).length ==0) {
					$("#strandCheckAllBoq").prop('checked', false);				
				}
				else {
					$("#strandCheckAllBoq").prop('checked', true);				
				}
			}
			else {
				var checkedLength = $(this).parent().parent().parent().find("li > input[type='checkbox']:checked").length;
				//To check the folder when all  children are checked
				if (childrenLength == checkedLength) {
					$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
				}

				if( $("#FiberPath_f_CurrentPhysicalLayer").find(".FiberStrand:checked" ).length ==0) {
					$("#strandCheckAllBoq").prop('checked', false);				
				}
				else {
				
					$("#strandCheckAllBoq").prop('checked', true);				
				
				}
			}
					
}); 
	} // end children case
}

/* TO BE DELETED
function trenchCheckFilter(){
	//$(".TRENCH").unbind('change');
	
	$(".TRENCH").bind('change',function(){
		var folderID = $(this).parents().eq(4).attr('id');
		trenchId=$(this).parent().attr('id');			
		
		if($(this).prop("checked")){			
			//Hide the duct in case of driving
			$(this).parent().find('input:checkbox').each(function(){
				if($(this).parent().hasClass('Duct')){
					if(directionHashmapDuct.get($(this).parent().attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapDuct.get($(this).parent().attr('id')).length; ii++) {
							directionHashmapDuct.get($(this).parent().attr('id'))[ii].setMap(null); 
							directionHashmapDuct.get($(this).parent().attr('id')).mapLabel.setMap(null);
						}
					}
				}
			});
			
			
			if (window[""+trenchId][18] == "DRIVING") {
				buildDrivingPath(trenchId,window["mapPoints_"+trenchId],"trench","brown",directionHashmapTrench);
			}
			else if(trenchArray[trenchId]){
				trenchArray[trenchId].setMap(map);	
			}
			else{
				if (window[""+trenchId][14] == "DRIVING") {
					buildDrivingPath(trenchId,window["mapPoints_"+trenchId],"trench","brown",directionHashmapTrench);
				}
				else {
					var path=window["mapPoints_"+trenchId];
					buildPath(trenchId,window["mapPoints_"+trenchId],trenchArray,allTrenches,"Trench_f_",'brown',0.4,15,'brown',1);
					trenchArray[trenchId].setMap(map);
				}
			}
			
			// Show and Hide the label of each trench depending on the checkLabel dropdown
			if($("#trenchMapCheck_Labels").prop("checked")==true){
				if(directionHashmapTrench.get(trenchId)) {
					directionHashmapTrench.get(trenchId).mapLabel.setMap(map);
                }
				if(trenchArray[trenchId]){
					trenchArray[trenchId].mapLabel.setMap(map);
				}
			}
			else {
				if(directionHashmapTrench.get(trenchId)) {
					directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
                }
				if(trenchArray[trenchId]){
					trenchArray[trenchId].mapLabel.setMap(null);
				}
			}
			

			$(this).parent().find('input:checkbox').each(function(){
				$(this).prop('checked', true);				
				if($(this).parent().hasClass('Duct')){
					var ductId=$(this).parent().attr('id');
					if(ductArray[ductId]){						
						ductArray[ductId].setMap(map);	
					}
					else{
						//buildDuct(ductId,window["mapPoints_"+ductId]);
						buildPath(ductId,window["mapPoints_"+ductId],ductArray,allDucts,"Duct",'#8e8080',0.6,7,'#8e8080',1);
						ductArray[ductId].setMap(map);
					}
				
					// Show and Hide the label of each duct depending on the checkLabel dropdown
					if($("#ductMapCheck_Labels").prop("checked")==true){
						ductArray[ductId].mapLabel.setMap(map);
					}
					else {
						ductArray[ductId].mapLabel.setMap(null);
					}					
				}
			});
		}
		else{
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);

			//Hide the duct in case of driving
			$(this).parent().find('input:checkbox').each(function(){
				if($(this).parent().hasClass('Duct')){
					if(directionHashmapDuct.get($(this).parent().attr('id')) != undefined) {
						for(ii = 0; ii < directionHashmapDuct.get($(this).parent().attr('id')).length; ii++) {
							directionHashmapDuct.get($(this).parent().attr('id'))[ii].setMap(null); 
							directionHashmapDuct.get($(this).parent().attr('id')).mapLabel.setMap(null);
						}
					}
				}
			});
			
			if(trenchArray[trenchId] && directionHashmapTrench.get(trenchId) != undefined){
				trenchArray[trenchId].setMap(null);	
				trenchArray[trenchId].mapLabel.setMap(null);
				
				for(i = 0 ; i < directionHashmapTrench.get(trenchId).length; i++) {
					if(trenchId == directionHashmapTrench.get(trenchId)[i].ID ) {
						directionHashmapTrench.get(trenchId)[i].setMap(null);
						directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
					    routeDisplayTrench = [];
						if(trenchArray[trenchId]){			
							trenchArray[trenchId].mapLabel.setMap(null);
						}
				}
			}
				directionHashmapTrench.delete(trenchId);
			}
			else if(trenchArray[trenchId] && directionHashmapTrench.get(trenchId) == undefined){
				trenchArray[trenchId].setMap(null);	
				trenchArray[trenchId].mapLabel.setMap(null);
			}
			else  if (window[""+trenchId][18] == "DRIVING" ) {	// no line of site case		 
				if(directionHashmapTrench.get(trenchId) != undefined) {
					for(i = 0 ; i < directionHashmapTrench.get(trenchId).length; i++) {
						if(trenchId == directionHashmapTrench.get(trenchId)[i].ID ) {
							directionHashmapTrench.get(trenchId)[i].setMap(null);
							directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
						    routeDisplayTrench = [];
							if(trenchArray[trenchId]){			
								trenchArray[trenchId].mapLabel.setMap(null);
							}
						}
					}
				}
				directionHashmapTrench.delete(trenchId);
			}
			else {
				trenchArray[trenchId].setMap(null);
				trenchArray[trenchId].mapLabel.setMap(null);
			}
			$("#trenchCheckAllBoq").prop('checked', false);	
			
			$(this).parent().find('input:checkbox').each(function(){
				$(this).prop('checked', false);
				if($(this).parent().hasClass('Duct')){										
					ductArray[$(this).parent().attr('id')].setMap(null);
					ductArray[$(this).parent().attr('id')].mapLabel.setMap(null);

				}
			});
			
			
		}
	
		var folderTrenchId =$(this).parent().parent().parent().attr('id');	
		var trenchChildrenLength =$("#"+folderTrenchId+" > ul").children().length;
		var trenchCheckedLength = $(this).parent().parent().parent().find(".TRENCH:checked").length;
		
		//To check the trench folder when all trenches children are checked
		if (trenchChildrenLength == trenchCheckedLength) {
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
		}
		
		if( $("#Trench_f_CurrentPhysicalLayer").find(".TRENCH:checked" ).length ==0) {
			$("#trenchCheckAllBoq").prop('checked', false);				
		}
		else {
			$("#trenchCheckAllBoq").prop('checked', true);				
		
		}
		
		if( $("#Trench_f_CurrentPhysicalLayer").find(".DUCT:checked" ).length ==0) {
			$("#ductCheckAllBoq").prop('checked', false);				
		}
		else {
			$("#ductCheckAllBoq").prop('checked', true);				
		}
		

	});

	$(".AllTrenches").on('change',function(){
	if($(this).prop("checked")){
		
		$("#Trench_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){	
			if(directionHashmapTrench.get($(this).attr('id')) != undefined) {
				for(ii = 0; ii < directionHashmapTrench.get($(this).attr('id')).length; ii++) {
					directionHashmapTrench.get($(this).attr('id'))[ii].setMap(null); 
					directionHashmapTrench.get($(this).attr('id')).mapLabel.setMap(null);
				}
			}
		});
		
		//Hide the duct in case of driving
		$(this).parent().find(' > ul > li >ul >li >ul >li').each(function(){
			if(directionHashmapDuct.get($(this).attr('id')) != undefined) {
				for(ii = 0; ii < directionHashmapDuct.get($(this).attr('id')).length; ii++) {
					directionHashmapDuct.get($(this).attr('id'))[ii].setMap(null); 
					directionHashmapDuct.get($(this).attr('id')).mapLabel.setMap(null);
				}
			}
		});
		
		$("#trenchCheckAllBoq").prop("checked",true);

			$(this).parent().find('input:checkbox').each(function(){

				$(this).prop('checked', true);
				
				if($(this).parent().hasClass('Trench')){
					trenchId=$(this).parent().attr('id');

					if(trenchArray[trenchId]){						
						trenchArray[trenchId].setMap(map);	
					}
					else{
						buildPath(trenchId,window["mapPoints_"+trenchId],trenchArray,allTrenches,"Trench_f_",'brown',0.4,15,'brown',1);
						trenchArray[trenchId].setMap(map);
					}
					
					// Show and Hide the label of each trench depending on the checkLabel dropdown
					if($("#trenchMapCheck_Labels").prop("checked")==true){
						if(directionHashmapTrench.get(trenchId)) {
							directionHashmapTrench.get(trenchId).mapLabel.setMap(map);
		                }
						if(trenchArray[trenchId]){
							trenchArray[trenchId].mapLabel.setMap(map);
						}
					}
					else {
						if(directionHashmapTrench.get(trenchId)) {
							directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
		                }
						if(trenchArray[trenchId]){
							trenchArray[trenchId].mapLabel.setMap(null);
						}
					}
			 }	
			else if($(this).parent().hasClass('Duct')){
					ductId=$(this).parent().attr('id');
					if(ductArray[ductId]){						
						ductArray[ductId].setMap(map);	
					}
					else{
						//buildDuct(ductId,window["mapPoints_"+ductId]);
						buildPath(ductId,window["mapPoints_"+ductId],ductArray,allDucts,"Duct",'#8e8080',0.6,7,'#8e8080',1);
						ductArray[ductId].setMap(map);
					}
					
					// Show and Hide the label of each duct depending on the checkLabel dropdown
					if($("#ductMapCheck_Labels").prop("checked")==true){
						ductArray[ductId].mapLabel.setMap(map);
					}
					else {
						ductArray[ductId].mapLabel.setMap(null);
					}
					
					//$('#ductCheckAllBoq').prop('checked', true);

				}		
			});			
				
			
		}
		else{
			$("#trenchCheckAllBoq").prop("checked",false);
			$("#ductCheckAllBoq").prop("checked",false);
			
			//Hide the duct in case of driving
			$(this).parent().find(' > ul > li >ul >li >ul >li').each(function(){
				if(directionHashmapDuct.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapDuct.get($(this).attr('id')).length; ii++) {
						directionHashmapDuct.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapDuct.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			
			$(this).parent().find('input:checkbox').each(function(){
				$(this).prop('checked', false);
				
				if($(this).parent().hasClass('Trench')){
					trenchId=$(this).parent().attr('id');	
					if(trenchArray[trenchId]) {
						trenchArray[trenchId].setMap(null);
						trenchArray[trenchId].mapLabel.setMap(null);
						
						$("#Trench_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){	
							if(directionHashmapTrench.get($(this).attr('id')) != undefined) {
								for(ii = 0; ii < directionHashmapTrench.get($(this).attr('id')).length; ii++) {
									directionHashmapTrench.get($(this).attr('id'))[ii].setMap(null); 
									directionHashmapTrench.get($(this).attr('id')).mapLabel.setMap(null);
								}
							}
						});

					}
				}						
				else if($(this).parent().hasClass('Duct')){
					ductId=$(this).parent().attr('id');						
					ductArray[ductId].setMap(null);						
				}	
				
			});	
		

	/*	var nbDuctFolderChecked = $("#Trench_f_CurrentPhysicalLayer").find(".AllTrenches:checked" ).length;
		var nbDuctFolder = $("#Trench_f_CurrentPhysicalLayer").find(".AllTrenches" ).length;
		
		if(nbDuctFolderChecked==nbDuctFolder) {	
				$("#trenchCheckAllBoq").prop("checked",true);
				$("#ductCheckAllBoq").prop("checked",true);
		}	*/
		
	/*	$("#network_tree").find(".TRENCH:checked" ).each(function(){
			trenchId=$(this).parent().attr('id');
			if(trenchArray[trenchId]){						
				trenchArray[trenchId].setMap(map);	
			}
					else{
						//buildTrench(trenchId,window["mapPoints_"+trenchId]);
						buildPath(trenchId,window["mapPoints_"+trenchId],trenchArray,allTrenches,"Trench_f_",'brown',0.4,15,'brown',1);
						trenchArray[trenchId].setMap(map);
					}
		});
			
		}
	
/*	if( $("#Trench_f_CurrentPhysicalLayer").find(".TRENCH:checked" ).length ==0) {
		$("#trenchCheckAllBoq").prop('checked', false);				
	}
	else {
		$("#trenchCheckAllBoq").prop('checked', true);				
		
	}
	if( $("#Trench_f_CurrentPhysicalLayer").find(".DUCT:checked" ).length ==0) {
		$("#ductCheckAllBoq").prop('checked', false);				
	}
	else {
		$("#ductCheckAllBoq").prop('checked', true);				
			
	}
*/
	/*});
}  
*/

/* TO BE DELETED
function ductCheckFilter(){

	//$(".trenchDucts").unbind('change');
	//$(".DUCT").unbind('change');
	
	$(".trenchDucts").bind('change',function(){
		
		if($(this).prop("checked")){	
			
			//Hide the duct in case of driving
			$(this).parent().find(' > ul > li').each(function(){
				if(directionHashmapDuct.get($(this).attr('id')) != undefined) {
					for(ii = 0; ii < directionHashmapDuct.get($(this).attr('id')).length; ii++) {
						directionHashmapDuct.get($(this).attr('id'))[ii].setMap(null); 
						directionHashmapDuct.get($(this).attr('id')).mapLabel.setMap(null);
					}
				}
			});
			$(this).parent().find(' > ul > li').each(function(){
				$(this).children('input:checkbox').prop('checked', true);
				var ductId=$(this).attr('id');

				if(ductArray[ductId]){
					ductArray[ductId].setMap(map);
				}
				else{
					buildPath(ductId,window["mapPoints_"+ductId],ductArray,allDucts,"Duct",'#8e8080',0.6,7,'#8e8080',1);
					ductArray[ductId].setMap(map);
				}
				// Show and Hide the label of each duct depending on the checkLabel dropdown
				if($("#ductMapCheck_Labels").prop("checked")==true){
					ductArray[ductId].mapLabel.setMap(map);
				}
				else {
					ductArray[ductId].mapLabel.setMap(null);
				}
			});
		}
		else{
			$('#ductCheckAllBoq').prop('checked', false);

			$(this).parent().find(' > ul > li').each(function(){
				$(this).children('input:checkbox').prop('checked', false);
				var ductId = $(this).attr('id');
				
				if(ductArray[ductId] && directionHashmapDuct.get(ductId) != undefined){
					ductArray[ductId].setMap(null);	
					ductArray[ductId].mapLabel.setMap(null);
					
					for(i = 0 ; i < directionHashmapDuct.get(ductId).length; i++) {
						if(ductId == directionHashmapDuct.get(ductId)[i].ID ) {
							directionHashmapDuct.get(ductId)[i].setMap(null);
							directionHashmapDuct.get(ductId).mapLabel.setMap(null);
						    routeDisplayDuct = [];
							if(ductArray[ductId]){			
								ductArray[ductId].mapLabel.setMap(null);
							}
					}
				}
					directionHashmapDuct.delete(ductId);
				}	
				else if(ductArray[ductId] && directionHashmapDuct.get(ductId) == undefined){
					ductArray[ductId].setMap(null);	
					ductArray[ductId].mapLabel.setMap(null);
				}
				else  if (window[""+ductId][19] == "DRIVING" ) {	// no line of site case		 
					if(directionHashmapDuct.get(ductId) != undefined) {
						for(i = 0 ; i < directionHashmapDuct.get(ductId).length; i++) {
							if(ductId == directionHashmapDuct.get(ductId)[i].ID ) {
								directionHashmapDuct.get(ductId)[i].setMap(null);
								directionHashmapDuct.get(ductId).mapLabel.setMap(null);
							    routeDisplayDuct = [];
								if(ductArray[ductId]){			
									ductArray[ductId].mapLabel.setMap(null);
								}
							}
						}
					}
					directionHashmapDuct.delete(ductId);
			}
			else {
				ductArray[ductId].setMap(null);
				ductArray[ductId].mapLabel.setMap(null);
			}	
				
		});
	}
		
		if( $("#Trench_f_CurrentPhysicalLayer").find(".DUCT:checked" ).length ==0) {
			$("#ductCheckAllBoq").prop('checked', false);				
		}
		else {
			$("#ductCheckAllBoq").prop('checked', true);				
				
		}
		

	});
	
	

	$('.DUCT').bind('change',function(){

		if($(this).prop("checked")){					
			
			var ductID = $(this).parent().attr('id');

			if (window[""+ductID][19] == "DRIVING") {
				buildDrivingPath(ductID,window["mapPoints_"+ductID],"duct","#8e8080",directionHashmapDuct);
			}
			else if(ductArray[ductID]){
				ductArray[ductID].setMap(map);
			}
			else{
				if (window[""+ductID][19] == "DRIVING") {
					buildDrivingPath(ductID,window["mapPoints_"+ductID],"duct","#8e8080",directionHashmapDuct);
				}
				else {
					var path=window["mapPoints_"+ductID];
					buildPath(ductID,window["mapPoints_"+ductID],ductArray,allDucts,"Duct",'#8e8080',0.6,7,'#8e8080',1);
					ductArray[ductID].setMap(map);
				}
		  }
			
			// Show and Hide the label of each duct depending on the checkLabel dropdown
			if($("#ductMapCheck_Labels").prop("checked")==true){
				if(directionHashmapDuct.get(ductID)) {
					directionHashmapDuct.get(ductID).mapLabel.setMap(map);
			    }
				if(ductArray[ductID]){
					ductArray[ductID].mapLabel.setMap(map);
				}
			}
			else {
				if(directionHashmapDuct.get(ductID)) {
					directionHashmapDuct.get(ductID).mapLabel.setMap(null);
			     }
				if(ductArray[ductID]){
					ductArray[ductID].mapLabel.setMap(null);
				}
			}
			
		}
		else{		
		
			var ductID = $(this).parent().attr('id');
			
			if(ductArray[ductID] && directionHashmapDuct.get(ductID) != undefined){
				ductArray[ductID].setMap(null);	
				ductArray[ductID].mapLabel.setMap(null);
					
				for(i = 0 ; i < directionHashmapDuct.get(ductID).length; i++) {
						if(ductID == directionHashmapDuct.get(ductID)[i].ID ) {
							directionHashmapDuct.get(ductID)[i].setMap(null);
							directionHashmapDuct.get(ductID).mapLabel.setMap(null);
						    routeDisplayDuct = [];
							if(ductArray[ductID]){			
								ductArray[ductID].mapLabel.setMap(null);
							}
					}
				}
				directionHashmapDuct.delete(ductID);
			}
			else if(ductArray[ductID] && directionHashmapDuct.get(ductID) == undefined){
				ductArray[ductID].setMap(null);	
				ductArray[ductID].mapLabel.setMap(null);
			}
			else if (window[""+ductID][19] == "DRIVING" ) {	// no line of site case		 
				if(directionHashmapDuct.get(ductID) != undefined) {
					for(i = 0 ; i < directionHashmapDuct.get(ductID).length; i++) {
						if(ductID == directionHashmapDuct.get(ductID)[i].ID ) {
							directionHashmapDuct.get(ductID)[i].setMap(null);
							directionHashmapDuct.get(ductID).mapLabel.setMap(null);
						    routeDisplayDuct = [];
							if(ductArray[ductID]){			
								ductArray[ductID].mapLabel.setMap(null);
							}
						}
					}
				}
				directionHashmapDuct.delete(ductID);
			}	
			else {
				ductArray[ductID].setMap(null);
				ductArray[ductID].mapLabel.setMap(null);
			}				
		
			$("#ductCheckAllBoq").prop('checked', false);		

		
		}
		var folderDuctId =$(this).parent().parent().parent().attr('id');			
		var ductChildrenLength =$("#"+folderDuctId+" > ul").children().length;
		var ductCheckedLength = $(this).parent().parent().parent().find("li > input[type='checkbox']:checked").length;

		//To check the ducts folder when all ducts children are checked
		if (ductChildrenLength == ductCheckedLength) {
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', true);
		}
		else {
			$(this).parent().parent().parent().children('input:checkbox').prop('checked', false);

		}
		
		if( $("#Trench_f_CurrentPhysicalLayer").find(".DUCT:checked").length ==0) {
			$("#ductCheckAllBoq").prop('checked', false);				
		}
		else {
			$("#ductCheckAllBoq").prop('checked', true);				
		
		}
		
	});
	
}
*/
//function checkbox events for filtering of items in Boq Div
function boqCheckFilter(){
	$("#manholeCheckAllBoq").change(function(){
		if($(this).is(":checked")){
			
			$("#Manhole_f_CurrentPhysicalLayer > .AllManholes").prop("checked",true);				
			$("#Manhole_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			

			var ManId=$(this).attr('id');
					
			if(markersManhole[ManId].getMap()==null){						
				markersManhole[ManId].setMap(map);			
				markerClusterManhole.addMarker(markersManhole[ManId]);
				$("#"+ManId).children(':checkbox').prop( "checked", true );
			}
			$("#"+ManId).find(' > ul > li >ul >li').each(function(){
				var jctId =$(this).attr('id');						
				$("#"+jctId).children(':checkbox').prop( "checked", true );
		  	});	
		  	$("#"+ManId).find(' > ul > li ').each(function(){
				var jctId =$(this).attr('id');						
				$("#"+jctId).children(':checkbox').prop( "checked", true );
		  	});
		  																
			});
				
			}
			else{
			
				$("#Manhole_f_CurrentPhysicalLayer > .AllManholes").prop("checked",false);				
				$("#Manhole_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			

					var ManId=$(this).attr('id');					
									
						markersManhole[ManId].setMap(null);	
						$("#"+ManId).children(':checkbox').prop( "checked", false );
						markerClusterManhole.clearMarkers();
												
					
					$("#"+ManId).find(' > ul > li >ul >li').each(function(){
						var jctId =$(this).attr('id');						
						$("#"+jctId).children(':checkbox').prop( "checked", false );
		  			});	
		  			$("#"+ManId).find(' > ul > li ').each(function(){
						var jctId =$(this).attr('id');						
						$("#"+jctId).children(':checkbox').prop( "checked", false );
		  			});
					
		  		});
		  		
		  		$("#network_tree").find(".Manhole:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(markersManhole[id].getMap()==null){
						markersManhole[id].setMap(map);			
						markerClusterManhole.addMarker(markersManhole[id]);
					}		
				});
		  			
			}
	})
	
		
		$("#entrepriseCheckAllBoq").change(function(){
		if($(this).is(":checked")){
			
			$("#Entreprise_f_CurrentPhysicalLayer > .AllEntreprise").prop("checked",true);				
			$("#Entreprise_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			

			var NodeId=$(this).attr('id');
					
			if(markersNodes[NodeId].getMap()==null){						
				markersNodes[NodeId].setMap(map);			
				markerClusterNodes.addMarker(markersNodes[NodeId]);
				$("#"+NodeId).children(':checkbox').prop( "checked", true );
			}
		  																
			});
				
			}
			else{
			
				$("#Entreprise_f_CurrentPhysicalLayer > .AllEntreprise").prop("checked",false);				
				$("#Entreprise_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			

					var NodeId=$(this).attr('id');					
									
						markersNodes[NodeId].setMap(null);	
						$("#"+NodeId).children(':checkbox').prop( "checked", false );
						markerClusterNodes.clearMarkers();
					
		  		});
		  		
		  		$("#network_tree").find(".Nodes:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(markersNodes[id].getMap()==null){
						markersNodes[id].setMap(map);			
						markerClusterNodes.addMarker(markersNodes[id]);
					}		
				});
		  			
			}
	})

	$("#handholeCheckAllBoq").change(function(){

			if($(this).is(":checked")){
				$("#Handhole_f_CurrentPhysicalLayer > .AllHandholes").prop("checked",true);				
				$("#Handhole_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			
				
				var HandId=$(this).attr('id');
					
				if(markersHandhole[HandId].getMap()==null){						
					markersHandhole[HandId].setMap(map);			
					markerClusterHandhole.addMarker(markersHandhole[HandId]);
					$("#"+HandId).children(':checkbox').prop( "checked", true );
				}
				$("#"+HandId).find(' > ul > li >ul >li').each(function(){
					var jctId =$(this).attr('id');						
					$("#"+jctId).children(':checkbox').prop( "checked", true );
		  		});	
		  		$("#"+HandId).find(' > ul > li ').each(function(){
					var jctId =$(this).attr('id');						
					$("#"+jctId).children(':checkbox').prop( "checked", true );
		  		});
				
				});	
				
			}
			else{
				$("#Handhole_f_CurrentPhysicalLayer > .AllHandholes").prop("checked",false);				
				$("#Handhole_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			
				
				var HandId=$(this).attr('id');
					
				markersHandhole[HandId].setMap(null);			
				$("#"+HandId).children(':checkbox').prop( "checked", false );
				markerClusterHandhole.clearMarkers();
				
				$("#"+HandId).find(' > ul > li >ul >li').each(function(){
					var jctId =$(this).attr('id');						
					$("#"+jctId).children(':checkbox').prop( "checked", false );
		  		});	
		  		$("#"+HandId).find(' > ul > li ').each(function(){
					var jctId =$(this).attr('id');						
					$("#"+jctId).children(':checkbox').prop( "checked", false );
		  		});
				
				});	
				
				$("#network_tree").find(".Handhole:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(markersHandhole[id].getMap()==null){						
						markersHandhole[id].setMap(map);			
						markerClusterHandhole.addMarker(markersHandhole[id]);
					}
					
				});
			}
		})

	$("#distBoardCheckAllBoq").change(function(){
			if($(this).is(":checked")){
				$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",true);				
				$("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			
				
				var distBoardId=$(this).attr('id');
					
				if(markersDistBoard[distBoardId].getMap()==null){						
					markersDistBoard[distBoardId].setMap(map);			
					markerClusterDistBoard.addMarker(markersDistBoard[distBoardId]);
					$("#"+distBoardId).children(':checkbox').prop( "checked", true );
				}
					
				});
			}
			else{
				$("#DistributionBoard_f_CurrentPhysicalLayer > .AllDistBoards").prop("checked",false);				
				$("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			
				
				var distBoardId=$(this).attr('id');
				
				markersDistBoard[distBoardId].setMap(null);	
				$("#"+distBoardId).children(':checkbox').prop( "checked", false );
				markerClusterDistBoard.clearMarkers();
				});
		  		
		  		$("#network_tree").find(".DistBoard:checked" ).each(function(){

					id=$(this).parent().attr('id');
					if(markersDistBoard[id].getMap()==null){
						markersDistBoard[id].setMap(map);			
						markerClusterDistBoard.addMarker(markersDistBoard[id]);
					}		
				});							
			}
	});
	$('#fiberCheckAllBoq').bind("change",function() {	
		
		if ($(this).is(':checked')){
			$("#FiberPath_f_CurrentPhysicalLayer > .AllFiberCables").prop("checked",true);	
			$("#Backbone__CurrentPhysicalLayer").prop("checked",true);	
			$("#Metro__CurrentPhysicalLayer").prop("checked",true);	
			$("#Access__CurrentPhysicalLayer").prop("checked",true);	
			
			
			$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){
				var fiberId=$(this).attr('id');
				
				//Hide the cable in case of driving 
				if(directionHashmap.get(fiberId) != undefined) {
					for(ii = 0; ii < directionHashmap.get(fiberId).length; ii++) {
						directionHashmap.get(fiberId)[ii].setMap(null); 
	                    directionHashmap.get(fiberId).mapLabel.setMap(null);
					}
				}
				
				if(fiberArray[fiberId]){
					fiberArray[fiberId].setMap(map);	
				}
				else{
					var path=window["mapPoints_"+fiberId];
					//buildFiberPath(fiberId,path);
					buildPath(fiberId,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberId][22]],0.7,4.5,'blue',13);
					fiberArray[fiberId].setMap(map);
				}
				$("#"+fiberId).children(':checkbox').prop( "checked", true );
				
				// Show and Hide the label of each cable depending on the checkLabel dropdown
				if($("#fiberMapCheck_Labels").prop("checked")==true){
					fiberArray[fiberId].mapLabel.setMap(map);
				}
				else {
					fiberArray[fiberId].mapLabel.setMap(null);
				}
				$("#"+fiberId).find(' > ul > li ').each(function(){
						var tubeFolderId =$(this).attr('id');						
						$("#"+tubeFolderId).children(':checkbox').prop( "checked", true );
			  	});
					
				$("#"+fiberId).find(' > ul > li >ul >li').each(function(){
					var tubeId =$(this).attr('id');		
					//Hide the tube in case of driving 
					if(directionHashmapTube.get(tubeId) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
							directionHashmapTube.get(tubeId)[ii].setMap(null); 
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						}
					}
					
					if(tubeArray[tubeId]){						
						tubeArray[tubeId].setMap(map);	
					}
					else{
						var path=window["mapPoints_"+tubeId];
						//buildTubePath(tubeId,path);
						buildPath(tubeId,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[tubeId].setMap(map);
					}			
					$("#"+tubeId).children(':checkbox').prop( "checked", true );
					
					// Show and Hide the label of each tube depending on the checkLabel dropdown
					if($("#tubeMapCheck_Labels").prop("checked")==true){
						tubeArray[tubeId].mapLabel.setMap(map);
					}
					else {
						tubeArray[tubeId].mapLabel.setMap(null);
					}
					$('#tubeCheckAllBoq').prop('checked', true);
			  	});	
			  		
			  		$("#"+fiberId).find(' > ul > li >ul >li >ul >li').each(function(){
						var strandFolderId =$(this).attr('id');						
						$("#"+strandFolderId).children(':checkbox').prop( "checked", true );
			  		});	
			  		$("#"+fiberId).find(' > ul > li >ul >li >ul >li >ul >li').each(function(){
						var strandId =$(this).attr('id');	
						
						//Hide the strand in case of driving 
						if(directionHashmapStrand.get(strandId) != undefined) {
							for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
								directionHashmapStrand.get(strandId)[ii].setMap(null); 
								directionHashmapStrand.get(strandId).mapLabel.setMap(null);
							}
						}
						
						if(strandArray[strandId]){						
							strandArray[strandId].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+strandId];
							//buildStrandPath(strandId,path);
							buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[strandId].setMap(map);									
						}					
						$("#"+strandId).children(':checkbox').prop( "checked", true );
						
						// Show and Hide the label of each tube depending on the checkLabel dropdown
						if($("#strandMapCheck_Labels").prop("checked")==true){
							strandArray[strandId].mapLabel.setMap(map);
						}
						else {
							strandArray[strandId].mapLabel.setMap(null);
						}
						$('#strandCheckAllBoq').prop('checked', true);
			  		});	
					
					});

				}
				else{
					$("#FiberPath_f_CurrentPhysicalLayer > .AllFiberCables").prop("checked",false);	
					$("#Backbone__CurrentPhysicalLayer").prop("checked",false);	
					$("#Metro__CurrentPhysicalLayer").prop("checked",false);	
					$("#Access__CurrentPhysicalLayer").prop("checked",false);	
					
					$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){			
						
						var fiberId=$(this).attr('id');
						if(directionHashmap.get(fiberId) != undefined) {
							for(ii = 0; ii < directionHashmap.get(fiberId).length; ii++) {
								directionHashmap.get(fiberId)[ii].setMap(null); 
			                    directionHashmap.get(fiberId).mapLabel.setMap(null);
							}
						}
						
						if(fiberArray[fiberId]) {
							fiberArray[fiberId].setMap(null);
							fiberArray[fiberId].mapLabel.setMap(null);
						}
						$("#"+fiberId).children(':checkbox').prop( "checked", false );
						
					$("#"+fiberId).find(' > ul > li ').each(function(){
						var tubeFolderId =$(this).attr('id');						
						$("#"+tubeFolderId).children(':checkbox').prop( "checked", false );
			  		});
					
					$("#"+fiberId).find(' > ul > li >ul >li').each(function(){
						var tubeId =$(this).attr('id');		
						if(directionHashmapTube.get(tubeId) != undefined) {
							for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
								directionHashmapTube.get(tubeId)[ii].setMap(null); 
								directionHashmapTube.get(tubeId).mapLabel.setMap(null);
							}
						}
						if(tubeArray[tubeId]) {
							tubeArray[tubeId].setMap(null);
							tubeArray[tubeId].mapLabel.setMap(null);
						}
						$("#"+tubeId).children(':checkbox').prop( "checked", false );
						$('#tubeCheckAllBoq').prop('checked', false);
			  		});	
			  		
					$("#"+fiberId).find(' > ul > li >ul >li >ul >li').each(function(){
						var strandFolderId =$(this).attr('id');						
						$("#"+strandFolderId).children(':checkbox').prop( "checked", false );
			  		});	
			  		$("#"+fiberId).find(' > ul > li >ul >li >ul >li >ul >li').each(function(){
						var strandId =$(this).attr('id');	
						if(directionHashmapStrand.get(strandId) != undefined) {
							for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
								directionHashmapStrand.get(strandId)[ii].setMap(null); 
								directionHashmapStrand.get(strandId).mapLabel.setMap(null);
							}
						}
						if(strandArray[strandId]) {
							strandArray[strandId].setMap(null);
							strandArray[strandId].mapLabel.setMap(null);

						}
									
						$("#"+strandId).children(':checkbox').prop( "checked", false );
						$('#strandCheckAllBoq').prop('checked', false);
			  		});	
			  	});
			  		
			  		$("#network_tree").find(".FiberCable:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(fiberArray[id]){						
							fiberArray[id].setMap(map);	
						}
						else{														
							var path=window["mapPoints_"+id];
							//buildFiberPath(id,path);
							buildPath(id,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+id][22]],0.7,4.5,'blue',13);
							fiberArray[id].setMap(map);
						}	
					});
					$("#network_tree").find(".FiberTube:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(tubeArray[id]){						
							tubeArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildTubePath(id,path);
							buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
							tubeArray[id].setMap(map);
						}	
					});
						$("#network_tree").find(".FiberStrand:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(strandArray[id]){						
							strandArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildStrandPath(id,path);
							buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[id].setMap(map);									
						}		
					});
				}
			});
			
	$('#tubeCheckAllBoq').bind("change",function() {
		
		if ($(this).is(':checked')){
			
			$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){	
				var fiberId=$(this).attr('id');		
				// Check tube folder
				$("#"+fiberId).find(' > ul > li ').each(function(){
					var tubeFolderId =$(this).attr('id');						
					$("#"+tubeFolderId).children(':checkbox').prop( "checked", true );
			  	});
			  	
			  	//Check each tube children and show on map
			  	$("#"+fiberId).find(' > ul > li >ul >li').each(function(){
					var tubeId =$(this).attr('id');	
					if(directionHashmapTube.get(tubeId) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
							directionHashmapTube.get(tubeId)[ii].setMap(null); 
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						}
					}
					if(tubeArray[tubeId]){						
						tubeArray[tubeId].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+tubeId];
						buildPath(tubeId,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[tubeId].setMap(map);
					}		
					// Show and Hide the label of each tube depending on the checkLabel dropdown
					if($("#tubeMapCheck_Labels").prop("checked")==true){
						tubeArray[tubeId].mapLabel.setMap(map);
					}
					else {
						tubeArray[tubeId].mapLabel.setMap(null);
					}
						$("#"+tubeId).children(':checkbox').prop( "checked", true );
						$('#tubeCheckAllBoq').prop('checked', true);
			  	});	
			  	
				$("#"+fiberId).find(' > ul > li >ul >li >ul >li').each(function(){
					var strandFolderId =$(this).attr('id');						
					$("#"+strandFolderId).children(':checkbox').prop( "checked", true );
			  	});	
			  	
			  	$("#"+fiberId).find(' > ul > li >ul >li >ul >li >ul >li').each(function(){
					var strandId =$(this).attr('id');	
					if(directionHashmapStrand.get(strandId) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
							directionHashmapStrand.get(strandId)[ii].setMap(null); 
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						}
					}
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+strandId];
						buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);									
					}					
					$("#"+strandId).children(':checkbox').prop( "checked", true );
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
						strandArray[strandId].mapLabel.setMap(map);
					}
					else {
						strandArray[strandId].mapLabel.setMap(null);
					}
					$('#strandCheckAllBoq').prop('checked', true);
			  	});			  
			});
		
		}
		else{
			$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){			
				
				var fiberId=$(this).attr('id');
			
			//UnCheck tube folder for each fiber
				$("#"+fiberId).find(' > ul > li ').each(function(){
					var tubeFolderId =$(this).attr('id');						
					$("#"+tubeFolderId).children(':checkbox').prop( "checked", false );
			  	});
			 
			 //UnCheck each tube and hide from Map 
			  	$("#"+fiberId).find(' > ul > li >ul >li').each(function(){
					var tubeId =$(this).attr('id');	
					if(directionHashmapTube.get(tubeId) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
							directionHashmapTube.get(tubeId)[ii].setMap(null); 
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						}
					}
					if(tubeArray[tubeId]) {
						tubeArray[tubeId].setMap(null);	
						tubeArray[tubeId].mapLabel.setMap(null);
					}
					$("#"+tubeId).children(':checkbox').prop( "checked", false );
					$('#tubeCheckAllBoq').prop('checked', false);
			  	});	
			  		
				$("#"+fiberId).find(' > ul > li >ul >li >ul >li').each(function(){
					var strandFolderId =$(this).attr('id');						
					$("#"+strandFolderId).children(':checkbox').prop( "checked", false );
			  	});	
			  	$("#"+fiberId).find(' > ul > li >ul >li >ul >li >ul >li').each(function(){
					var strandId =$(this).attr('id');
					if(directionHashmapStrand.get(strandId) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
							directionHashmapStrand.get(strandId)[ii].setMap(null); 
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						}
					}
					if(strandArray[strandId]) {
						strandArray[strandId].setMap(null);	
						strandArray[strandId].mapLabel.setMap(null);
					}				
					$("#"+strandId).children(':checkbox').prop( "checked", false );
					$('#strandCheckAllBoq').prop('checked', false);
			  	});
			  	
			  		
			  	});
			  	$("#network_tree").find(".FiberTube:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(tubeArray[id]){						
							tubeArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							//buildTubePath(id,path);
							buildPath(id,path,tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
							tubeArray[id].setMap(map);
						}	
					});
					
						$("#network_tree").find(".FiberStrand:checked" ).each(function(){

						id=$(this).parent().attr('id');
						if(strandArray[id]){						
							strandArray[id].setMap(map);	
						}
						else{						
							var path=window["mapPoints_"+id];
							buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
							strandArray[id].setMap(map);									
						}		
					});

				}
		});
		
	$('#strandCheckAllBoq').bind("change",function() {
		
		if ($(this).is(':checked')){
			$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li ').each(function(){	
				var fiberId=$(this).attr('id');		
			
			//Check strand folder for each fiber
			$("#"+fiberId).find(' > ul > li >ul >li >ul >li').each(function(){
				var strandFolderId =$(this).attr('id');						
				$("#"+strandFolderId).children(':checkbox').prop( "checked", true );
			 });
			 
			 //Check each strand 
			 $("#"+fiberId).find(' > ul > li >ul >li >ul >li >ul >li').each(function(){
					var strandId =$(this).attr('id');
					if(directionHashmapStrand.get(strandId) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
							directionHashmapStrand.get(strandId)[ii].setMap(null); 
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						}
					}
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+strandId];
						//buildStrandPath(strandId,path);
						buildPath(strandId,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);									
					}					
					$("#"+strandId).children(':checkbox').prop( "checked", true );
					
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
						if(strandArray[strandId]){
							strandArray[strandId].mapLabel.setMap(map);
						}
					}
					else {
						if(strandArray[strandId]){
							strandArray[strandId].mapLabel.setMap(null);
						}
					}
					$('#strandCheckAllBoq').prop('checked', true);
			 });		
		 });				
		}
		else{
		
		$("#FiberPath_f_CurrentPhysicalLayer").find(' > ul > li >ul >li').each(function(){	
				var fiberId=$(this).attr('id');		
			
			//UnCheck strand folder for each fiber
			$("#"+fiberId).find(' > ul > li >ul >li >ul >li').each(function(){
				var strandFolderId =$(this).attr('id');						
				$("#"+strandFolderId).children(':checkbox').prop( "checked", false );
			 });
			 
			 //UnCheck each strand 
			 $("#"+fiberId).find(' > ul > li >ul >li >ul >li >ul >li').each(function(){
					var strandId =$(this).attr('id');	
					
					if(directionHashmapStrand.get(strandId) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
							directionHashmapStrand.get(strandId)[ii].setMap(null); 
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						}
					}
					if(strandArray[strandId]) {
						strandArray[strandId].setMap(null);
						strandArray[strandId].mapLabel.setMap(null);
					}
					$("#"+strandId).children(':checkbox').prop( "checked", false );
					$('#strandCheckAllBoq').prop('checked', false);
			 });		
		 });
		 
		 $("#network_tree").find(".FiberStrand:checked" ).each(function(){

			id=$(this).parent().attr('id');
			if(strandArray[id]){						
				strandArray[id].setMap(map);	
			}
			else{						
				var path=window["mapPoints_"+id];
				//buildStrandPath(id,path);
				buildPath(id,path,strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
				strandArray[id].setMap(map);									
			}		
		});			
				
		}
	 });


	$('#trenchCheckAllBoq').bind("change",function() {
		if ($(this).is(':checked')){

			$("#Trench_f_CurrentPhysicalLayer > .AllTrenches").prop("checked",true);	
			
			$("#Trench_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			
				
				trenchId=$(this).attr('id');
				$(this).children('input:checkbox').prop('checked', true);
				
				if(directionHashmapTrench.get(trenchId) != undefined) {
					for(ii = 0; ii < directionHashmapTrench.get(trenchId).length; ii++) {
						directionHashmapTrench.get(trenchId)[ii].setMap(null); 
						directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
					}
				}

				if(trenchArray[trenchId]){
			
					if(trenchArray[trenchId].getMap()==null){
						trenchArray[trenchId].setMap(map);
					}
				$("#"+trenchId+" .Duct").each(function(){
								
					$(this).children('input:checkbox').prop('checked', true);
								
					var ductId=$(this).attr('id');
					
					if(directionHashmapDuct.get(ductId) != undefined) {
						for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
							directionHashmapDuct.get(ductId)[ii].setMap(null); 
							directionHashmapDuct.get(ductId).mapLabel.setMap(null);
						}
					}
				
					if(!ductArray[ductId]){
						var path=window["mapPoints_"+ductId];
						buildPath(ductId,path,ductArray,allDucts,"Duct",'#8e8080',0.6,10,'#8e8080',1);
					}
					if(ductArray[ductId].getMap()==null){
						ductArray[ductId].setMap(map);
					}
					
					// Show and Hide the label of each duct depending on the checkLabel dropdown
					if($("#ductMapCheck_Labels").prop("checked")==true){
						ductArray[ductId].mapLabel.setMap(map);
					}
					else {
						ductArray[ductId].mapLabel.setMap(null);
					}
				
				$('#ductCheckAllBoq').prop('checked', true);
				
				$("#"+trenchId).find(' > ul > li ').each(function(){
					var ductFolderId =$(this).attr('id');						
					$("#"+ductFolderId).children(':checkbox').prop( "checked", true );
				});	
								
				});

				}
				else{
					var path=window["mapPoints_"+trenchId];
					buildPath(trenchId,path,trenchArray,allTrenches,"Trench_f_",'brown',0.4,20,'brown',1);
					trenchArray[trenchId].setMap(map);
					
					$("#"+trenchId).find(' > ul > li >ul >li').each(function(){ 

						var ductId=$(this).attr('id');
						$(this).find('input:checkbox:first').prop('checked', true);
								
						var path=window["mapPoints_"+ductId];
						buildPath(ductId,path,ductArray,allDucts,"Duct",'#8e8080',0.6,10,'#8e8080',1);
						ductArray[ductId].setMap(map);		
						
					});
					$("#"+trenchId).find(' > ul > li ').each(function(){
						var ductFolderId =$(this).attr('id');						
						$("#"+ductFolderId).children(':checkbox').prop( "checked", true );
						$('#ductCheckAllBoq').prop('checked', true);
					});		
				}			
			});		
			$('#trenchCheckAllBoq').prop('checked', true);

		}
		else{
			
			$("#Trench_f_CurrentPhysicalLayer > .AllTrenches").prop("checked",false);	
			
			$("#Trench_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){	
				var trenchId=$(this).attr('id');
				$(this).children('input:checkbox').prop('checked', false);
				
				if(directionHashmapTrench.get(trenchId) != undefined) {
					for(ii = 0; ii < directionHashmapTrench.get(trenchId).length; ii++) {
						directionHashmapTrench.get(trenchId)[ii].setMap(null); 
						directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
					}
				}
				
				if(trenchArray[trenchId]) {
					trenchArray[trenchId].setMap(null);
					trenchArray[trenchId].mapLabel.setMap(null);
				}

				$("#"+trenchId+" .Duct").each(function(){
					$(this).children('input:checkbox').prop('checked', false);
					var ductId=$(this).attr('id');	
					
					if(directionHashmapDuct.get(ductId) != undefined) {
						for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
							directionHashmapDuct.get(ductId)[ii].setMap(null); 
							directionHashmapDuct.get(ductId).mapLabel.setMap(null);
						}
					}
					
					if(ductArray[ductId]) {
						ductArray[ductId].setMap(null);
						ductArray[ductId].mapLabel.setMap(null);
					}
					$('#ductCheckAllBoq').prop('checked', false);
					
				});		
				$("#"+trenchId).find(' > ul > li ').each(function(){
					var ductFolderId =$(this).attr('id');						
					$("#"+ductFolderId).children(':checkbox').prop( "checked", false );
				});				
	   
			});
				}
	});
	
	$('#ductCheckAllBoq').bind("change",function() {
		if ($(this).is(':checked')){
			$("#Trench_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){			
				
				var trenchId=$(this).attr('id');	
				$("#"+trenchId+" .Duct").each(function(){
						$(this).children('input:checkbox').prop('checked', true);
						ductId=$(this).attr('id');
						
					if(directionHashmapDuct.get(ductId) != undefined) {
							for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
								directionHashmapDuct.get(ductId)[ii].setMap(null); 
								directionHashmapDuct.get(ductId).mapLabel.setMap(null);
							}
					}
					if(ductArray[ductId]){						
						ductArray[ductId].setMap(map);	
					}
					else{						
						var path=window["mapPoints_"+ductId];
						buildPath(ductId,path,ductArray,allDucts,"Duct",'#8e8080',0.6,10,'#8e8080',1);
						ductArray[ductId].setMap(map);									
					}		
					// Show and Hide the label of each duct depending on the checkLabel dropdown
					if($("#ductMapCheck_Labels").prop("checked")==true){
						if(ductArray[ductId]){
							ductArray[ductId].mapLabel.setMap(map);
						}
					}
					else {
						if(ductArray[ductId]){
							ductArray[ductId].mapLabel.setMap(null);
						}
					}	
						
						
					/*	if(!ductArray[ductId]){
					
							var path=window["mapPoints_"+ductId];
							//buildDuct(ductId,path);
							buildPath(ductId,path,ductArray,allDucts,"Duct",'#8e8080',0.6,7,'#8e8080',1);
						}
						if(ductArray[ductId].getMap()==null){
							ductArray[ductId].setMap(map);
						}
						
						*/
						$("#"+trenchId).find(' > ul > li ').each(function(){
							var ductFolderId =$(this).attr('id');						
							$("#"+ductFolderId).children(':checkbox').prop( "checked", true );
						});		

				  });
				});
					$('#ductCheckAllBoq').prop('checked', true);
			}
			else{
					$("#Trench_f_CurrentPhysicalLayer").find(' > ul > li ').each(function(){	
						var trenchId=$(this).attr('id');	
						$("#"+trenchId+" .Duct").each(function(){
							var ductId=$(this).attr('id');
							$(this).children('input:checkbox').prop('checked', false);
							
							if(directionHashmapDuct.get(ductId) != undefined) {
								for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
									directionHashmapDuct.get(ductId)[ii].setMap(null); 
									directionHashmapDuct.get(ductId).mapLabel.setMap(null);
								}
							}
							if(ductArray[ductId]) {
								ductArray[ductId].setMap(null);
								ductArray[ductId].mapLabel.setMap(null);
							}
					 });
						
						
					$("#"+trenchId).find(' > ul > li ').each(function(){
							var ductFolderId =$(this).attr('id');						
							$("#"+ductFolderId).children(':checkbox').prop( "checked", false );
					});	
				});
					$('#ductCheckAllBoq').prop('checked', false);
				}
			});

											   
	}

//Event for tree-Map view/hide from Map for all elements 	
function allElementsCheckFilter(){

	$(".allElements").bind("change",function() {
		var start=Date.now();
		//console.log("changed checkbox of :"+$(this).parent().attr('id'));

		markerClusterManhole.clearMarkers();
		markerClusterHandhole.clearMarkers();
		markerClusterDistBoard.clearMarkers();
		markerClusterNodes.clearMarkers();
		
		$("#distBoardCheckAllBoq").prop("checked",false);
		$("#manholeCheckAllBoq").prop("checked",false);
		$("#handholeCheckAllBoq").prop("checked",false);
		$("#entrepriseCheckAllBoq").prop("checked",false);
			

		if ($(this).is(':checked')){		
			$(this).parent().find('ul > li').each(function(){
				
				$(this).children('input:checkbox').prop('checked', true);

				if($(this).hasClass('FIBER')){
					var fiberId=$(this).attr('id');
					if(directionHashmap.get(fiberId) != undefined) {
						for(ii = 0; ii < directionHashmap.get(fiberId).length; ii++) {
							directionHashmap.get(fiberId)[ii].setMap(null); 
		                    directionHashmap.get(fiberId).mapLabel.setMap(null);
						}
					}
					if(fiberArray[fiberId]){						
						fiberArray[fiberId].setMap(map);	
					}
					else{
						var path=window["mapPoints_"+fiberId];
						//buildFiberPath(fiberId,path);
						buildPath(fiberId,path,fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberId][22]],0.7,4.5,'blue',13);
						fiberArray[fiberId].setMap(map);

					}
					$("#fiberCheckAllBoq").prop("checked",true);
			
					// Show and Hide the label of each cable depending on the checkLabel dropdown
					if($("#fiberMapCheck_Labels").prop("checked")==true){
						fiberArray[fiberId].mapLabel.setMap(map);
					}
					else {
						fiberArray[fiberId].mapLabel.setMap(null);
					}
				}

				else if($(this).hasClass('TUBE')){
					var tubeId=$(this).attr('id');
					//console.log("has tubes "+tubeId);
					if(directionHashmapTube.get(tubeId) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
							directionHashmapTube.get(tubeId)[ii].setMap(null); 
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						}
					}
					if(tubeArray[tubeId]){						
						tubeArray[tubeId].setMap(map);	
					}
					else{
						//buildTubePath(tubeId,window["mapPoints_"+tubeId]);
						buildPath(tubeId,window["mapPoints_"+tubeId],tubeArray,allTubes,"Tube",'green',0.7,3.3,'green',0);
						tubeArray[tubeId].setMap(map);
					}
					
					// Show and Hide the label of each cable depending on the checkLabel dropdown
					if($("#tubeMapCheck_Labels").prop("checked")==true){
						tubeArray[tubeId].mapLabel.setMap(map);
					}
					else {
						tubeArray[tubeId].mapLabel.setMap(null);
					}
					
					
					$("#tubeCheckAllBoq").prop("checked",true);
				
				}

				else if($(this).hasClass('STRAND')){
					var strandId=$(this).attr('id');
					//console.log("Has strand : "+strandId);
					if(directionHashmapStrand.get(strandId) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
							directionHashmapStrand.get(strandId)[ii].setMap(null); 
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						}
					}
					if(strandArray[strandId]){						
						strandArray[strandId].setMap(map);	
					}
					else{
						//buildStrandPath(strandId,window["mapPoints_"+strandId]);
						buildPath(strandId,window["mapPoints_"+strandId],strandArray,allStrands,"Strand",'purple',0.7,2.8,'purple',0);
						strandArray[strandId].setMap(map);

					}
					// Show and Hide the label of each strand depending on the checkLabel dropdown
					if($("#strandMapCheck_Labels").prop("checked")==true){
						strandArray[strandId].mapLabel.setMap(map);
					}
					else {
						strandArray[strandId].mapLabel.setMap(null);
					}
					
					$("#strandCheckAllBoq").prop("checked",true);
					
				}
				
				else if($(this).hasClass('Trench')){
					var trenchId=$(this).attr('id');
					if(directionHashmapTrench.get(trenchId) != undefined) {
						for(ii = 0; ii < directionHashmapTrench.get(trenchId).length; ii++) {
							directionHashmapTrench.get(trenchId)[ii].setMap(null); 
							directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
						}
					}
					if(trenchArray[trenchId]){						
						trenchArray[trenchId].setMap(map);	
					}
					else{
						buildPath(trenchId,window["mapPoints_"+trenchId],trenchArray,allTrenches,"Trench_f_",'brown',0.4,20,'brown',1);
						trenchArray[trenchId].setMap(map);
					}
					// Show and Hide the label of each trench depending on the checkLabel dropdown
					if($("#trenchMapCheck_Labels").prop("checked")==true){
						trenchArray[trenchId].mapLabel.setMap(map);
					}
					else {
						trenchArray[trenchId].mapLabel.setMap(null);
					}
					$("#trenchCheckAllBoq").prop("checked",true);
					
				}


				else if($(this).hasClass('Duct')){
					var ductId=$(this).attr('id');
					if(directionHashmapDuct.get(ductId) != undefined) {
						for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
							directionHashmapDuct.get(ductId)[ii].setMap(null); 
							directionHashmapDuct.get(ductId).mapLabel.setMap(null);
						}
					}
					if(ductArray[ductId]){						
						ductArray[ductId].setMap(map);	
					}
					else{
						buildPath(ductId,window["mapPoints_"+ductId],ductArray,allDucts,"Duct",'#8e8080',0.6,10,'#8e8080',1);
						ductArray[ductId].setMap(map);
					}
					$("#ductCheckAllBoq").prop("checked",true);
				
					// Show and Hide the label of each duct depending on the checkLabel dropdown
					if($("#ductMapCheck_Labels").prop("checked")==true){
						ductArray[ductId].mapLabel.setMap(map);
					}
					else {
						ductArray[ductId].mapLabel.setMap(null);
					}
					
				}					
			});
		 
			$("#network_tree").find("input:checked" ).each(function(){
				
				if($(this).hasClass('Manhole')){
					id=$(this).parent().attr('id');
					if(markersManhole[id].getMap()==null){
						markersManhole[id].setMap(map);			
						markerClusterManhole.addMarker(markersManhole[id]);
						
						$("#manholeCheckAllBoq").prop("checked",true);
						
					}
				}
				
				else if($(this).hasClass('Handhole')){
					id=$(this).parent().attr('id');
					if(markersHandhole[id].getMap()==null){
						markersHandhole[id].setMap(map);			
						markerClusterHandhole.addMarker(markersHandhole[id]);
						$("#handholeCheckAllBoq").prop("checked",true);
						
					}
				}

				else if($(this).hasClass('DistBoard')){
					id=$(this).parent().attr('id');
					if(markersDistBoard[id].getMap()==null){

						markersDistBoard[id].setMap(map);			
						markerClusterDistBoard.addMarker(markersDistBoard[id]);
						$("#distBoardCheckAllBoq").prop("checked",true);
						
					}
				}
				else if($(this).hasClass('Nodes')){
					id=$(this).parent().attr('id');
					if(markersNodes[id].getMap()==null){

						markersNodes[id].setMap(map);			
						markerClusterNodes.addMarker(markersNodes[id]);
						$("#entrepriseCheckAllBoq").prop("checked",true);
						
					}
				}

			});
			
		}
	
		else{
				
			$(this).parent().find('ul > li').each(function(){
				
				$(this).children('input:checkbox').prop('checked', false);

			
				if($(this).hasClass('FIBER')){
					var fiberId=$(this).attr('id');
					//console.log("has fiber")
					if(directionHashmap.get(fiberId) != undefined) {
						for(ii = 0; ii < directionHashmap.get(fiberId).length; ii++) {
							directionHashmap.get(fiberId)[ii].setMap(null); 
		                    directionHashmap.get(fiberId).mapLabel.setMap(null);
						}
					}
					$("#fiberCheckAllBoq").prop("checked",false);
					
					if(fiberArray[fiberId]){
						fiberArray[fiberId].setMap(null);	
						fiberArray[fiberId].mapLabel.setMap(null);
					}
				}

				else if($(this).hasClass('TUBE')){
					var tubeId=$(this).attr('id');
					if(directionHashmapTube.get(tubeId) != undefined) {
						for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
							directionHashmapTube.get(tubeId)[ii].setMap(null); 
							directionHashmapTube.get(tubeId).mapLabel.setMap(null);
						}
					}
					
					$("#tubeCheckAllBoq").prop("checked",false);
					
					if(tubeArray[tubeId]){
						tubeArray[tubeId].setMap(null);
						tubeArray[tubeId].mapLabel.setMap(null);
					}
					
					
				}

				else if($(this).hasClass('STRAND')){
					var strandId=$(this).attr('id');
					if(directionHashmapStrand.get(strandId) != undefined) {
						for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
							directionHashmapStrand.get(strandId)[ii].setMap(null); 
							directionHashmapStrand.get(strandId).mapLabel.setMap(null);
						}
					}
					if(strandArray[strandId]){
						strandArray[strandId].setMap(null);
						strandArray[strandId].mapLabel.setMap(null);
					}
					$("#strandCheckAllBoq").prop("checked",false);
					
				}
				else if($(this).hasClass('Trench')){
					var trenchId=$(this).attr('id');
					if(directionHashmapTrench.get(trenchId) != undefined) {
						for(ii = 0; ii < directionHashmapTrench.get(trenchId).length; ii++) {
							directionHashmapTrench.get(trenchId)[ii].setMap(null); 
							directionHashmapTrench.get(trenchId).mapLabel.setMap(null);
						}
					}
					$("#trenchCheckAllBoq").prop("checked",false);
					
					if(trenchArray[trenchId]){
						trenchArray[trenchId].setMap(null);
						trenchArray[trenchId].mapLabel.setMap(null);
					}
					
				}
				else if($(this).hasClass('Duct')){
					var ductId=$(this).attr('id');
					if(directionHashmapDuct.get(ductId) != undefined) {
						for(ii = 0; ii < directionHashmapDuct.get(ductId).length; ii++) {
							directionHashmapDuct.get(ductId)[ii].setMap(null); 
							directionHashmapDuct.get(ductId).mapLabel.setMap(null);
						}
					}
					if(ductArray[ductId]){
						ductArray[ductId].setMap(null);
						ductArray[ductId].mapLabel.setMap(null);
					}
					$("#ductCheckAllBoq").prop("checked",false);
					
				}							  
			});
			
			$("#network_tree").find("input:checked").each(function(){
				if($(this).hasClass('Manhole')){
					id=$(this).parent().attr('id');
				   if(markersManhole[id].getMap()==null){
					   markersManhole[id].setMap(map);			
					   markerClusterManhole.addMarker(markersManhole[id]);
				   }
				}
				
				else if($(this).hasClass('Handhole')){
					id=$(this).parent().attr('id');
				   if(markersHandhole[id].getMap()==null){
					   markersHandhole[id].setMap(map);			
					   markerClusterHandhole.addMarker(markersHandhole[id]);
					}
				}

				else if($(this).hasClass('DistributionBoard')){
				   if(markersDistBoard[$(this).attr('id')].getMap()==null){

					   markersDistBoard[$(this).attr('id')].setMap(map);			
					   markerClusterDistBoard.addMarker(markersDistBoard[$(this).attr('id')]);
					}
			   }
			   
			   else if($(this).hasClass('Nodes')){
				   if(markersNodes[$(this).attr('id')].getMap()==null){

					   markersNodes[$(this).attr('id')].setMap(map);			
					   markerClusterNodes.addMarker(markersNodes[$(this).attr('id')]);
					}
			   }

			});		 
			for(var t=0;t<siteCltSrcMarkers.length;t++) {
				siteCltSrcMarkers[siteCltSrcMarkers[t].ID].setMap(null);
			}
			for (var u =0;u<allCablesShowPoint.length;u++) {
				window['fiberCheckPoints_'+allCablesShowPoint[u]] = "unchecked";
			}
			for (var z =0;z<allTubesShowPoint.length;z++) {
				window['tubeCheckPoints_'+allTubesShowPoint[z]] = "unchecked";
			}
			for (var a =0;a<allStrandsShowPoint.length;a++) {
				window['strandCheckPoints_'+allStrandsShowPoint[a]] = "unchecked";
			}
			for (var l =0;l<allTrenchsShowPoint.length;l++) {
				window['trenchCheckPoints_'+allTrenchsShowPoint[l]] = "unchecked";
			}
			for (var w =0;w<allDuctsShowPoint.length;w++) {
				window['ductCheckPoints_'+allDuctsShowPoint[w]] = "unchecked";
			}
			for (var x =0;x<allCablesShowRealPoint.length;x++) {
				window['fiberCheckRealPoints_'+allCablesShowRealPoint[x]] = "unchecked";
			}
			for (var o =0;o<allTubesShowRealPoint.length;o++) {
				window['tubeCheckRealPoints_'+allTubesShowRealPoint[o]] = "unchecked";
			}
			for (var b =0;b<allStrandsShowRealPoint.length;b++) {
				window['strandCheckRealPoints_'+allStrandsShowRealPoint[b]] = "unchecked";
			}
			for (var m =0;m<allTrenchsShowRealPoint.length;m++) {
				window['trenchCheckRealPoints_'+allTrenchsShowRealPoint[m]] = "unchecked";
			}
			for (var q =0;q<allDuctsShowRealPoint.length;q++) {
				window['ductCheckRealPoints_'+allDuctsShowRealPoint[q]] = "unchecked";
			}
			for (var y =0;y<allCablesShowSeq.length;y++) {
				window['fiberCheckSequence_'+allCablesShowSeq[y]] = "unchecked";
			}
			for (var v =0;v<allTubesShowSeq.length;v++) {
				window['tubeCheckSequence_'+allTubesShowSeq[v]] = "unchecked";
			}
			for (var c =0;c<allStrandsShowSeq.length;c++) {
				window['strandCheckSequence_'+allStrandsShowSeq[c]] = "unchecked";
			}
			for (var b =0;b<allDuctsShowSeq.length;b++) {
				window['ductCheckSequence_'+allDuctsShowSeq[b]] = "unchecked";
			}
			for (var a =0;a<allTrenchsShowSeq.length;a++) {
				window['trenchCheckSequence_'+allTrenchsShowSeq[a]] = "unchecked";
			}
			
			
		}
		console.log("it took "+(Date.now()-start)+" millisecs");
	
	});	
}




/// show/hide map circle
function displayZoneMap (circle){
	$("#SearchZone").click(function(){
		if($(this).is(":checked")){
		//	console.log("cheked zone");
			//google.maps.event.addListener(circle, function () {
	         circle.setOptions({fillOpacity: 0.35, strokeOpacity:0.3});  
		    ////});
			
		}
		else{
			//google.maps.event.addListener(circle, 'mouseover', function () {
		         circle.setOptions({fillOpacity:0, strokeOpacity:0});
			//});
		}
	});
}
// Appending searched fiber cables to the table of popup.
var geocoder;
function fetchSearchedFiberPath(data){
		var chk = false;
	$("#viewFiberPath").prop('disabled', true);
	geocoder = new google.maps.Geocoder();
	$("#searchFiberTBody").html("");
		
	if($("#srcCitySearchNames").val() != "" && $("#dstCitySearchNames").val() != ""){
	 	$.each(data, function( i, v ) {
	 		if(i == data.length - 1)
		           chk = true;
//  			setTimeout(function(){
	 			multiLocationFiberSearch(data[i], data[i][2], data[i][5], $("#srcCitySearchNames").val(), $("#dstCitySearchNames").val(), chk);
	 			multiLocationFiberSearch(data[i], data[i][5], data[i][2], $("#srcCitySearchNames").val(), $("#dstCitySearchNames").val(), chk);
//  			}, 50);
		 		
	});	
 
	}
	else if($("#srcCitySearchNames").val() == "" && $("#dstCitySearchNames").val() != ""){
	 	$.each(data, function( i, v ) {
	 		if(i == data.length - 1)
		           chk = true;
// 	 		setTimeout(function(){
		 			insertSearchedFiberRow(data[i][5],data[i],$("#dstCitySearchNames").val(), chk);
// 	 		 }, 50);
// 	 		setTimeout(function(){
	 				insertSearchedFiberRow(data[i][2],data[i],$("#dstCitySearchNames").val(), chk);
//  		 	 }, 50);
	 		
	});
 
	}
	else if($("#srcCitySearchNames").val() != "" && $("#dstCitySearchNames").val() == ""){
	 	$.each(data, function( i, v ) {
	 		if(i == data.length - 1)
		           chk = true;
	 		// setTimeout(function(){
	 				insertSearchedFiberRow(data[i][2],data[i],$("#srcCitySearchNames").val(), chk);
	 		// }, 50);
	 		//setTimeout(function(){
	 				insertSearchedFiberRow(data[i][5],data[i],$("#srcCitySearchNames").val(), chk);
	 		// }, 50);
	 		
	});	
	 	
	
	}
	else{
		$.each(data, function( i, v ) {
	 		if(i == data.length - 1)
		           chk = true;
	 		// setTimeout(function(){
	 				insertSearchedFiberRow(data[i][2],data[i],"", chk);
	 		// }, 50);
	 		//setTimeout(function(){
// 	 				insertSearchedFiberRow(data[i][5],data[i],$("#srcCitySearchNames").val(), chk);
	 		// }, 50);
	 		
	});}
	
	}
		
function multiLocationFiberSearch(fiberDataArray, dataSrc,dataDst,srcInput, dstInput, checkFlag){
		var checkDouble = false;							
		geocoder.geocode({
	       'address': dataSrc
	   	}, function(results, status) {
	       if (status === google.maps.GeocoderStatus.OK && results.length > 0) {
	    	   if(checkFlag) {
		    	   checkDouble = true;
	    	   }
		       if(results[0].address_components[1].long_name.includes(srcInput.split(", ")[0]) || srcInput.includes(dataSrc)){
		        	insertSearchedFiberRow(dataDst,fiberDataArray,dstInput, checkFlag);  		
		     	}
		       else{  //// needed because we have 3 kinds of speeds, 1st : spead of normal execution, 
		        	 //// 2nd : speed of service according to the connection, 
		        	 //// 3rd : speed of geocoder according to its query over-limit, and (ingoing & outgoing) geolocation timeout
		        	 if(checkDouble){
	    		   setTimeout(function(){	
	    			   $("#viewFiberPath").prop('disabled', false);			        	
			        	}, 10000);
		        	 }
			       }
	       } 
	       else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
	     	   setTimeout(function(){ multiLocationFiberSearch(fiberDataArray, dataSrc,dataDst,srcInput, dstInput, checkFlag)}, 50);
	        }
	       else {
	          alert("error in geolocation service with a message of : "+status);
													   
	       }
	   });
	 								   
	}

function insertSearchedFiberRow(address,FiberDataArray,location, checkFlag){
																					  
	geocoder.geocode({
	    'address': address
	}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK && results.length > 0) {

		    if(results[0].address_components[1].long_name.includes(location.split(", ")[0]) || location.includes(address)){ 
					
			       row ="<tr ><td style='width:5%;' class='row-pad'>"+FiberDataArray[0]+"</td><td style='width:5%;'>"+FiberDataArray[1]+"</td><td style='width:5%;'>"+FiberDataArray[2]+"</td><td style='width:5%;'>"+FiberDataArray[5]+"</td><td style='width:5%;'>distance</td></tr>";
		           $("#searchFiberTBody").append(row);
		           if(checkFlag == true)
		       		$("#viewFiberPath").prop('disabled', false);
		           
	           }   
    } else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
 	   setTimeout(function(){ insertSearchedFiberRow(address, FiberDataArray, location, checkFlag)}, 50);
    }
   else {
	   alert("error in geolocation service with a message of : "+status);}
	});
	
}




function opentab(evt, functionality) {
	  var i, tabcontent, tablinks;
	  tabcontent = document.getElementsByClassName("tabcontent");
	  for (i = 0; i < tabcontent.length; i++) {
	    tabcontent[i].style.display = "none";
	  }
	  tablinks = document.getElementsByClassName("tablinks");
	  for (i = 0; i < tablinks.length; i++) {
	    tablinks[i].className = tablinks[i].className.replace(" active", "");
	  }
	  document.getElementById(functionality).style.display = "block";
	  evt.currentTarget.className += " active";
	}


function viewNearestMultyPointEvent(){
	$("#viewNeareMulty").on('click', function(){
		var urlString = "";
		var nop = document.getElementById("noP_Multy").value;
		var circleRange_multy = document.getElementById("circleRange_multy").value;
		var closestDisRange = document.getElementById("closestMultyDisRange").value;
		var checkbox = document.getElementById("getRelatedPoints_Multy");
        var checkboxValue = checkbox.checked ? '1' : '0';
		
$("#Multy_auxiliary > tbody").find('input[name="record"]').each(function(){
  var $row = $(this).closest('tr');
  var indexSite = $row.index();
  var seq = $row.find('input[id="Seq_Multy' + indexSite + '"]').val();
  var name = $row.find('td[name="siteId_Multy"] input').val();
  var lng = $row.find('td[name="siteLng_Multy"] input').val();
  var lat = $row.find('td[name="siteLat_Multy"] input').val();
  
		 if($("#circleRange_multy").prop('checked')){
		     checkedOption = "circleRange_multy";  
			     urlString += "&seq="+seq+"";
			     urlString += "&name="+name+"";
			     urlString += "&lng="+lng+"";
			     urlString += "&lat="+lat+"";
			     urlString += "&closestDisRange="+closestDisRange+"";
			     urlString += "&nop="+$("#noP_Multy").val()+"";
			     urlString += "&getRelatedPoints="+checkboxValue+"";	
				 window.location.href = getContext()+"/NetworkPhysicalLayer?Checked="+checkedOption+urlString;  
		 }
		}); 
			 }); 
}

function  openFindNearestMultySite(checkedOption,rowInfo,noP,closestDisRange,ptList,ptData,getRelatedPoints){
	 $("#fiberCitySearch").modal("show");
     $('a[href="#MultyClosest"]').click();
     $("#circleRange_multy").prop("checked",true);
     
     $("#noP_Multy").val(noP);
     $("#getRelatedPoints_Multy").val(getRelatedPoints);
     $("#closestMultyDisRange").val(closestDisRange);
     showPointsType =getRelatedPoints;
	     if(getRelatedPoints == '1') {
			$("#getRelatedPoints").prop('checked', true);
		 }
		else {
			$("#getRelatedPoints").prop('checked', false);
		}
      
   var rowData = JSON.parse(rowInfo);
   var tableBody = $("#Multy_auxiliary > tbody");
   tableBody.empty(); // Clear existing table rows

   for (var key in rowData) {
    if (rowData.hasOwnProperty(key)) {
      var row = rowData[key];
      var indexSite = 0;
      //console.log(row);
      var markup = "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px;' name='record'></td>"
        + "<td class='headcol' name='Seq'><input name='Seq_Multy' id='Seq_Multy" + indexSite + "' class='form-control text-input' type='text' style='max-width:60px;position:relative;' value='" + row[2] + "'></td>"
        + "<td name='auxRefSite'><a href='#' class='auxRefSiteLink' data-index='" + indexSite + "' style='width:350px;'><p style='height:10px;margin-left:20px;color:#00757C;margin-top:10px;width:150px;'>View Result</p></a></td>"
        + "<td name='siteId_Multy'><input name='siteId_Multy" + indexSite + "' id='siteId_Multy" + indexSite + "' class='form-control' type='text' style='width:330px;position:relative;' value='" + row[1] + "'></td>"
        + "<td name='siteLng_Multy'><input name='siteLng_Multy" + indexSite + "' id='siteLng_Multy" + indexSite + "' class='form-control' type='text' style='width:220px;position:relative;' value='" + row[0] + "'></td>"
        + "<td name='siteLat_Multy'><input name='siteLat_Multy" + indexSite + "' id='siteLat_Multy" + indexSite + "' class='form-control' type='text' style='width:220px;position:relative;' value='" + row[3] + "'></td></tr>";
      tableBody.append(markup);
      autoCompleteMultiPoint("siteId_Multy","siteLng_Multy","siteLat_Multy",indexSite,"auxPtAutocomplete");
       indexSite++; // Increment the counter for the next row
       
       var lat = row[3];
       var lng = row[0];
       const myLatLng = {
		  lat: parseFloat(lat),
		  lng: parseFloat(lng)
		};
		
		new google.maps.Marker({
			 position: myLatLng,
		     map,
			 title: "Marked",
		});
		var circleRadius = closestDisRange *1.609344 *1.609344;
		var circ = new google.maps.Circle({
	         strokeColor: "blue",
	         strokeOpacity: 0.8,
	         strokeWeight: 2,
	         fillColor: "blue",
	         fillOpacity: 0.1,
	         map,
	         center: myLatLng,
	         radius: circleRadius * 1000,
	       });
	       displayZoneMap(circ);
		   map.setCenter(myLatLng);
		   map.fitBounds(circ.getBounds());
}
}  
    //  $("#auxRefSite"+indexSite).on('click', function(e){
		$(document).on('click', '.auxRefSiteLink', function(e) {
		           e.preventDefault();
                       var indexSite = $(this).data('index');
					   var relSiteId=$(this).parent().parent().children('td[name="siteId_Multy"]').children('input').val();
					   var thisID = $(this).attr("id");
					 
						    $("#siteModalAuxiliary").modal('show');	
							$('#siteModalAuxiliary').find('input:file').val('');
		                    $('#siteModalAuxiliary').find('input:checkbox').prop("checked",false);
						    showTubeStrandAuxiliaryPopup("siteModalAuxiliary",relSiteId,"SiteIdHeader","Site_Autocomplete_Multy");
					    
						var ptListObject = JSON.parse(ptList);
						var ptDataObject = JSON.parse(ptData);
						var finalArrayFibers = [];
						
						// Loop over each ptList
						for (var i = 0; i < Object.keys(ptListObject).length; i++) {
						  var ptListKey = "ptList" + i;
						  
						  if (ptListObject.hasOwnProperty(ptListKey)) {
							  if (ptListObject[ptListKey].hasOwnProperty("Manhole")) {
							        var arrayManhole = ptListObject[ptListKey].Manhole;
							  } 
						      if (ptListObject[ptListKey].hasOwnProperty("Handhole")) {
						           var arrayHandhole = ptListObject[ptListKey].Handhole;
							  } 
							  if (ptListObject[ptListKey].hasOwnProperty("Distribution_Board")) {
						           var arrayDB = ptListObject[ptListKey].Distribution_Board;
							  }  
							   if (ptListObject[ptListKey].hasOwnProperty("fiber")) {
						           var arrayFibers = ptListObject[ptListKey].fiber;
							}
							}
						}
						// Loop over each ptData
						for (var i = 0; i < Object.keys(ptDataObject).length; i++) {
						  var ptDataKey = "ptData" + i;
						  
						  if (ptDataObject.hasOwnProperty(ptDataKey)) {
							  if (ptDataObject[ptDataKey].hasOwnProperty("fiber_Tubes")) {
							        var arrayTubes = ptDataObject[ptDataKey].fiber_Tubes;
							  } 
							   if (ptDataObject[ptDataKey].hasOwnProperty("fiber_Strands")) {
							        var arrayStrands = ptDataObject[ptDataKey].fiber_Strands;
							 	} 
							 	}
							}
								 finalArrayFibers.push(arrayStrands);
								 finalArrayFibers.push(arrayTubes);
								 finalArrayFibers.push(arrayFibers);
								 appendNearestFiberPathsTableMulty(finalArrayFibers);
								 appendNearestManholesTableMulty(arrayManhole);
					       	     appendNearestHandholesTableMulty(arrayHandhole);
					       	     appendNearestDBoardTableMulty(arrayDB);
									
								 $("#totalManhole_multy").val(arrayManhole.length);
								 $("#totalHandhole_multy").val(arrayHandhole.length);
								 $("#totalDB_Multy").val(arrayDB.length);
				  });
				  
							  $("#siteModalAuxiliary").on('hidden.bs.modal', function() {
								     $("#searchManhTBody_multy").empty();
								     $("#searchHanhTBody_multy").empty();
								     $("#searchDBoardTBody_multy").empty();
								     $("#nearStrandId_multy").empty();
								     $("#nearTubeId_multy").empty();
								     $("#nearFiberId_multy").empty();
								});	 
}


function appendNearestManholesTableMulty(result){
		var markupManh="";
		document.getElementById("findNearestManRes_Multy");
						
		if (result.length==0){
		document.getElementById("findNearestManRes_Multy").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		
		}else {
		   for(var i =0 ; i<result.length;i++){
			if($("#circleRange_multy").is(":checked")){
				markupManh +="<tr style='height: 30px;'><td ><input type='checkbox' class='ManholeBOQ' id=BOOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='manholeId' style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"
		    }
		    else{
			
		    	if(result[i][9] == null || result[i][9]==""){
				markupManh +="<tr style='height: 30px;'><td ><input type='checkbox' class='ManholeBOQ' id=BOOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='manholeId' style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][7])+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"
			    }else{
				markupManh +="<tr style='height: 30px;' ><td><input type='checkbox' class='ManholeBOQ' id=BOOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='manholeId' style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][7])+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][8])+"</label></td></tr>"
			    }
		}
		}
}			  
		$("#searchManhTBody_multy").append(markupManh);
		if($("#circleRange_multy").is(":checked")){
			drivingDistance("findNearstManhole_multy");
		}
		makeAllSortable();
		
		$("#selectAllManhole_multy").click(function(){
			if($(this).is(":checked")){
			
			$('input[type="checkbox"]', '#findNearstManhole_multy').prop('checked', true);
			$(".Manhole").prop('checked', true);
			$(".AllManholes").prop("checked",true);
			markerClusterManhole.clearMarkers();
			$("#network_tree").find(".Manhole:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersManhole[id].setMap(map);			
				markerClusterManhole.addMarker(markersManhole[id]);
						
			});
			}
			else{
				$('input[type="checkbox"]', '#findNearstManhole_multy').prop('checked', false);
				$(".Manhole").prop('checked', false);
				$(".AllManholes").prop("checked",false);
				markerClusterManhole.clearMarkers();
			}
		});
		
		// checking single row checbox from boq
		$('.ManholeBOQ').click(function(){
				var ManId = $(this).attr('id').split("BOOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+ManId).children('input:checkbox').prop('checked', true);
					markersManhole[ManId].setMap(map);
					markerClusterManhole.addMarker(markersManhole[ManId]);
				}
				else{
					$("#"+ManId).children('input:checkbox').prop('checked', false);
		    		markersManhole[ManId].setMap(null);				
					markerClusterManhole.removeMarker(markersManhole[ManId]);
				}
		});
	}	
	
function appendNearestHandholesTableMulty(result){
		var markupHandh="";		
		document.getElementById("findNearestHandRes_Multy");
		
		if (result.length==0){
			document.getElementById("findNearestHandRes_Multy").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		}
		else {
			for(var i =0 ; i<result.length;i++){
				if($("#circleRange_multy").is(":checked")){
					markupHandh +="<tr style='height: 30px;'><td ><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='handholeId' style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"
			    }
			    else{
			    	if(result[i][8] == null || result[i][8]==""){
						markupHandh +="<tr style='height: 30px;'><td><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][7])+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"
					}else{
						markupHandh +="<tr style='height: 30px;' ><td><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][7])+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][8])+"</label></td></tr>"
					}
			    }
		}
		}
		$("#searchHanhTBody_multy").append(markupHandh);
		if($("#circleRange_multy").is(":checked")){
			drivingDistance("findNearstHandhole_multy");
		}
		makeAllSortable();
		
		$("#selectAllHandhole_multy").click(function(){
			if($(this).is(":checked")){
			$('input[type="checkbox"]', '#findNearstHandhole_multy').prop('checked', true);
			$(".Handhole").prop('checked', true);
			$(".AllHandholes").prop("checked",true);
			markerClusterHandhole.clearMarkers();
			 $("#network_tree").find(".Handhole:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersHandhole[id].setMap(map);			
				markerClusterHandhole.addMarker(markersHandhole[id]);		
		     });	
			}
			else{
				$('input[type="checkbox"]', '#findNearstHandhole_multy').prop('checked', false);
				$(".Handhole").prop('checked', false);
				$(".AllHandholes").prop("checked",false);
				markerClusterHandhole.clearMarkers();
			}
		});
		
		// checking single row checbox from boq
		$('.HandholeBOQ').click(function(){
				var HandId = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+HandId).children('input:checkbox').prop('checked', true);
					markersHandhole[HandId].setMap(map);
					markerClusterHandhole.addMarker(markersHandhole[HandId]);
				}
				else{
					$("#"+HandId).children('input:checkbox').prop('checked', false);
					markersHandhole[HandId].setMap(null);				
		    		markerClusterHandhole.removeMarker(markersHandhole[HandId]);
				}			
		});

}

function appendNearestDBoardTableMulty(result){
		var markupDBoard="";
		document.getElementById("findNearestDbRes_Multy");
			
		if (result.length==0){
			document.getElementById("findNearestDbRes_Multy").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		}
		else { 
				for(var i =0 ; i<result.length;i++){
					if($("#circleRange_multy").is(":checked")){
						markupDBoard +="<tr style='height: 30px;'><td ><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][3]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"
				    }
				    else{
				    	if(result[i][9] == null || result[i][9]==""){
							markupDBoard +="<tr style='height: 30px;'><td><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td><td style='min-width:150px;'>"+result[i][0]+"</td><td style='min-width:150px;'>"+result[i][3]+"</td><td  name='LONGG' style='min-width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='min-width:150px;'  name='LATT'><input name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='min-width:50px;'>"+result[i][8]+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"
						}else{
							markupDBoard +="<tr style='height: 30px;'><td><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td><td style='min-width:150px;'>"+result[i][0]+"</td><td style='min-width:150px;'>"+result[i][3]+"</td><td style='min-width:150px;'>"+result[i][1]+"</td><td style='min-width:150px;'>"+result[i][2]+"</td><td style='min-width:50px;'>"+result[i][8]+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][9])+"</label></td></tr>"
						}	
				    }
				}
		}
		$("#searchDBoardTBody_multy").append(markupDBoard);
		if($("#circleRange_multy").is(":checked")){
			drivingDistance("findNearstDB_multy");
		}
        makeAllSortable();

		$("#selectAllDB_multy").click(function(){
			if($(this).is(":checked")){
			$('input[type="checkbox"]', '#findNearstDB_multy').prop('checked', true);
			$(".DistBoard").prop('checked', true);
			$(".AllDistBoards").prop("checked",true);
			markerClusterDistBoard.clearMarkers();
			$("#network_tree").find(".DistBoard:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersDistBoard[id].setMap(map);			
				markerClusterDistBoard.addMarker(markersDistBoard[id]);
													
			});	
			}
			else{
				$('input[type="checkbox"]', '#findNearstDB_multy').prop('checked', false);
				$(".DistBoard").prop('checked', false);
				$(".AllDistBoards").prop("checked",false);
				markerClusterDistBoard.clearMarkers();
			}
			
		});
		
		// checking single row checbox from boq
		$('.DBBOQ').click(function(){		
				var DBdId = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+DBdId).children('input:checkbox').prop('checked', true);
					markersDistBoard[DBdId].setMap(map);					
					markerClusterDistBoard.addMarker(markersDistBoard[DBdId]);
				}
				else{
					$("#"+DBdId).children('input:checkbox').prop('checked', false);
					markersDistBoard[DBdId].setMap(null);				
					markerClusterDistBoard.removeMarker(markersDistBoard[DBdId]);
				}
		});
																	
	}
	
function appendNearestFiberPathsTableMulty(result){
		var markupNearStrand="";
		var markupNearTube="";
		var markupNearFiber="";		

		if (result.length==0){
			markupNearStrand ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
		   if (result[0]) {
			result[0].forEach((res) => 
				markupNearStrand +="<tr ><td style='min-width:150px;' class='row-pad'>"+res[0]+"</td><td style='min-width:150px;'>"+res[13]+"</td><td style='min-width:350px;'>"+res[6]+"</td><td style='min-width:350px;'>"+res[9]+"</td></tr>"
				);
				}
		}
		$("#nearStrandId_multy").append(markupNearStrand);
		
		if (result.length==0){
			markupNearTube ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
		  if (result[1]) {
			result[1].forEach((res) => 
		markupNearTube +="<tr ><td style='min-width: 150px;' class='row-pad'>"+res[0]+"</td><td style='min-width: 150px;'>"+res[13]+"</td><td style='min-width: 350px;'>"+res[6]+"</td><td style='min-width: 350px;'>"+res[9]+"</td></tr>"
			);
			}
		}						  
		$("#nearTubeId_multy").append(markupNearTube);
		
		if (result.length==0){
			markupNearFiber ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
		  if (result[2]) {
			result[2].forEach((res) => 
				 markupNearFiber +="<tr ><td style='min-width: 150px;' class='row-pad'>"+res[4]+"</td><td style='min-width: 150px;'>"+res[13]+"</td><td style='min-width: 350px;'>"+res[6]+"</td><td style='min-width: 350px;'>"+res[9]+"</td></tr>"
			);
			}
		}						  
		$("#nearFiberId_multy").append(markupNearFiber);
																	
}	

function viewNearestPointEvent(){
	$("#viewNearestp").on('click', function(){
		$("#searchManhTBody").empty();
		$("#searchHanhTBody").empty();
		$("#searchDBoardTBody").empty();
		$("#searchManhTBody").html("");
		$("#searchHanhTBody").html("");
		$("#searchDBoardTBody").html("");
		$("#nearStrandId").html("");
		$("#nearFiberId").html("");
		$("#nearTubeId").html("");
		$('#removeS').show();
		$("#SearchZone").prop("checked",true);
		$("#selectAllHandhole").prop('checked', false);
		$("#selectAllManhole").prop('checked', false);
		$("#selectAllDB").prop('checked', false);
		$('#removeFilter').hide();
		/*$("#closestLongPoint").val("");
		$("#closestLatPoint").val("");
		$("#closestDisRange").val("");
		$("#startLongPoint").val("");
	    $("#startLatPoint").val("");
	    $("#endLongPoint").val("");
	    $("#endLatPoint").val("");
		//$("#totalManhole").val("");
		//$("#totalHandhole").val("");
		//$("#totalDB").val("");*/
		
		 var closestDisRange = document.getElementById("closestDisRange").value;
		 var closestLatPoint = document.getElementById("closestLatPoint").value;
		 var closestLongPoint = document.getElementById("closestLongPoint").value;
		 var startLongPoint = document.getElementById("startLongPoint").value;
		 var startLatPoint = document.getElementById("startLatPoint").value;
		 var endLongPoint = document.getElementById("endLongPoint").value;
		 var endLatPoint = document.getElementById("endLatPoint").value;
		 
		 var urlString = "";
		 
		 if($("#circleRange").prop('checked')){
		  checkedOption = "circleRange";  
		  
			  if (closestDisRange === "" || closestLongPoint === "" || closestLatPoint === "" || isNaN(closestDisRange) || isNaN(closestLongPoint) || isNaN(closestLatPoint)) {
			    alert("Please enter a numeber in the input field.");
			    
			  }else{
			     urlString += "&closestLatPoint="+$("#closestLatPoint").val()+"";
			     urlString += "&closestLongPoint="+$("#closestLongPoint").val()+"";
			     urlString += "&closestDisRange="+$("#closestDisRange").val()+"";
			     urlString += "&noP="+$("#noP").val()+"";
			     urlString += "&getRelatedPoints="+$("#getRelatedPoints").val()+"";	
			 	 window.location.href = getContext()+"/NetworkPhysicalLayer?Checked="+checkedOption+urlString;
			    }	
			    	
		 }else{
			 checkedOption = "StartEnd";
			 
			 if (startLongPoint === "" || startLatPoint === "" || endLongPoint === "" || endLatPoint === "" || isNaN(startLongPoint) || isNaN(startLatPoint) || isNaN(endLongPoint) || isNaN(endLatPoint)) {
			    alert("Please enter a numeber in the input field.");
			    
			 }else{
			 urlString += "&startLongPoint="+$("#startLongPoint").val()+"";
			 urlString += "&startLatPoint="+$("#startLatPoint").val()+"";
			 urlString += "&endLongPoint="+$("#endLongPoint").val()+"";
			 urlString += "&endLatPoint="+$("#endLatPoint").val()+"";
			 urlString += "&getRelatedPoints="+$("#getRelatedPoints").val()+"";	
			 window.location.href = getContext()+"/NetworkPhysicalLayer?Checked="+checkedOption+urlString;
            }
		 }
	
			
	/*		
			var nm=[]; // list of nearest manhole
			var nh=[];// list of nearest handhole
			var ndb=[];// list of nearest distribution board
		//	var nfp=[];
			var nfc=[];// list of nearest fiber cable
			var nfcA=[];// list of nearest fiber cable aux
			var nf=[];
			var ns=[];
			var nc=[];
			var nt=[];
			if($("#circleRange").is(":checked")){ 
				var noOfPoints=$("#noP").val();
				var closestLatPoint = $("#closestLatPoint").val();
				var closestLongPoint = $("#closestLongPoint").val();
				var closestDisRange = $("#closestDisRange").val()*1.609344;
				
				if(closestLatPoint != '' &&  closestLongPoint != '' && closestDisRange != ''){
					$.ajax({
				         type: "GET",
				         contentType: "application/json; charset=utf-8",
				         url: getContext()+'/FindNearestPoints',
				         data: {
				        	 "noOfPoints": noOfPoints,
				        	 "closestDisRange" : closestDisRange,
				        	 "closestLatPoint" : closestLatPoint,
				        	 "closestLongPoint" : closestLongPoint,
				        	// "target" : "Manhole",
				        	
				         },
				         dataType: "json",
				         success: function (data) {
				        	 console.log("data "+data.nearstPointsManhole);
				        	 nm = data.nearstPointsManhole; 
				        	 //nm=findNearestPointsMan(noOfPoints,closestDisRange,initialHashmap['Manhole'],closestLatPoint,closestLongPoint,"Manhole");
							 appendNearestManholesTable(nm);
							 //nh=findNearestPointsHand(noOfPoints,closestDisRange,initialHashmap['Handhole'],closestLatPoint,closestLongPoint,"Handhole");
							 nh = data.nearstPointsHandhole; 
							 appendNearestHandholesTable(nh);					        			
							 //ndb=findNearestPointsDb(noOfPoints,closestDisRange,hashMapList['Distribution_Board'],closestLatPoint,closestLongPoint,"DBoard");
							 ndb = data.nearstPointsDistribBoard;
							 appendNearestDBoardTable(ndb);
							 nfc.push([]);
							 nfc.push([]);
							 nfc.push(data.nearstPointsfiberCable);
							 appendNearestFiberPathsTable(nfc);
							 nfcA = data.nearstPointsfiberCableAuxiliaries;
							 
							 $("#totalManhole").val(nm.length);
							 $("#totalHandhole").val(nh.length);
							 $("#totalDB").val(ndb.length);
							 const myLatLng = { lat: parseFloat($("#closestLatPoint").val()), lng: parseFloat($("#closestLongPoint").val()) };

							restMAP();
							$('#filterr').show();

							CreateTree_PhysicalLayer(hashMapList['Project'],nm,nh,nfc[2],ndb,nfc[1],nfc[0],nfcA,Hashmap['tubes_Auxiliaries'],Hashmap['strands_Auxiliaries'],hashMapList['trench'],nt,0,"",1); 
							CreateMap_PhysicalLayer(hashMapList['Project'],nm,nh,nfc[2],ndb,nfc[1],nfc[0],nfcA,Hashmap['tubes_Auxiliaries'],Hashmap['strands_Auxiliaries'],hashMapList['trench'],nt); 
																	
							new google.maps.Marker({
									 position: myLatLng,
								     map,
									 title: "Marked",
							});
							//var circleRadius = closestDisRange * 1.609344;
							var circleRadius = $("#closestDisRange").val()*1.609344;
							console.log("circleRadius "+circleRadius);
							var circ = new google.maps.Circle({
							         strokeColor: "blue",
							         strokeOpacity: 0.8,
							         strokeWeight: 2,
							         fillColor: "blue",
							         fillOpacity: 0.1,
							         map,
							         center: myLatLng,
							         radius: circleRadius * 1000,
							       });		
								displayZoneMap(circ);
						        map.setCenter(myLatLng);
							    map.fitBounds(circ.getBounds());
				     		
				         },
				         error: function(result) {
				             alert("Error");
				         }
				     });

	    		} else {
	    			alert("Please Fill All Fields");
	    		}
	    		closestLatPoint='';
	    		closestLongPoint='';
	    		closestDisRange='';
														
													
			}else if($("#StartEnd").is(":checked")){ 
				var  startLongPoint = Number(parseFloat($("#startLongPoint").val()));
				var  startLatPoint = Number(parseFloat($("#startLatPoint").val()));
				var  endLongPoint = Number(parseFloat($("#endLongPoint").val()));
				var  endLatPoint = Number(parseFloat($("#endLatPoint").val()));
				
				if(startLongPoint != '' &&  startLatPoint != '' && endLongPoint != '' && endLatPoint != ''){
					
					$.ajax({
				         type: "GET",
				         contentType: "application/json; charset=utf-8",
				         url: getContext()+'/findMarkerPointsBetween',
				         data: {
				        	 "startLongPoint": startLongPoint,
				        	 "startLatPoint" : startLatPoint,
				        	 "endLongPoint" : endLongPoint,
				        	 "endLatPoint" : endLatPoint,
				        	
				        	
				         },
				         dataType: "json",
				         success: function (data) {
				        	 console.log("data "+data.ManholeMarkerPointsBetween);
				        	 //nm=findMarkerPointsBetween(initialHashmap['Manhole'],startLongPoint,startLatPoint,endLongPoint,endLatPoint,"Manhole");
				        	 nm = data.ManholeMarkerPointsBetween;
							 appendNearestManholesTable(nm);
							 //nh=findMarkerPointsBetween(initialHashmap['Handhole'],startLongPoint,startLatPoint,endLongPoint,endLatPoint,"Handhole");
							 nh = data.HandholeMarkerPointsBetween;
							 appendNearestHandholesTable(nh);					        			
							 //ndb=findMarkerPointsBetween(hashMapList['Distribution_Board'],startLongPoint,startLatPoint,endLongPoint,endLatPoint,"DBoard");
							 ndb = data.DistribBoardMarkerPointsBetween;
							 appendNearestDBoardTable(ndb);
							// nfp=findNearestFiberPaths(nm,nh,ndb,hashMapList['fiber'],Hashmap['fiber_Tubes'],Hashmap['fiber_Strands']);
							 nfc.push([]);
							 nfc.push([]);
							 nfc.push(data.fiberCableMarkerPointsBetween);
							 appendNearestFiberPathsTable(nfc);
							 nfcA = data.fiberAuxiliaryMarkerPointsBetween

							$("#totalManhole").val(nm.length);
							$("#totalHandhole").val(nh.length);
							$("#totalDB").val(ndb.length);
								
							restMAP();
							$('#filterr').show();
								
							CreateTree_PhysicalLayer(hashMapList['Project'],nm,nh,nfc[2],ndb,nfc[1],nfc[0],nfcA,Hashmap['tubes_Auxiliaries'],Hashmap['strands_Auxiliaries'],hashMapList['trench'],nt,0,"",1); 
							CreateMap_PhysicalLayer(hashMapList['Project'],nm,nh,nfc[2],ndb,nfc[1],nfc[0],nfcA,Hashmap['tubes_Auxiliaries'],Hashmap['strands_Auxiliaries'],hashMapList['trench'],nt); 
							    
								startLongPoint = Number(parseFloat($("#startLongPoint").val()));
								startLatPoint = Number(parseFloat($("#startLatPoint").val()));
								endLongPoint = Number(parseFloat($("#endLongPoint").val()));
								endLatPoint = Number(parseFloat($("#endLatPoint").val()));	
								
								startlangPath =[new google.maps.LatLng(startLatPoint,startLongPoint), new google.maps.LatLng(endLatPoint,startLongPoint)];
								drawLine("#FF0000",startlangPath);
								startlatgPath =[new google.maps.LatLng(startLatPoint,startLongPoint), new google.maps.LatLng(startLatPoint,endLongPoint)];
								drawLine("#FF0000",startlatgPath);
								endlangPath =[new google.maps.LatLng(endLatPoint,endLongPoint), new google.maps.LatLng(startLatPoint,endLongPoint)];
								drawLine("#006400",endlangPath);
								endlatgPath =[new google.maps.LatLng(endLatPoint,endLongPoint), new google.maps.LatLng(endLatPoint, startLongPoint)];
								drawLine("#006400",endlatgPath);
								const myLatLng = { lat: parseFloat((parseFloat(startLatPoint) + parseFloat(endLatPoint)) / 2.0), lng: parseFloat((parseFloat(startLongPoint) + parseFloat(endLongPoint)) / 2.0) };
								map.setCenter(myLatLng);
								map.fitBounds(new google.maps.LatLngBounds(
										  //bottom left
										  new google.maps.LatLng(parseFloat(endLatPoint), parseFloat(endLongPoint)),
										  //top right
										  new google.maps.LatLng(parseFloat(startLatPoint), parseFloat(startLongPoint))
								));
				        	
				     		
				         },
				         error: function(result) {
				             alert("Error");
				         }
				     });
					
							
				
			    } else {
				alert("Please Fill All Fields");
			    }
				
				startLongPoint = '';
				startLatPoint = '';
			    endLongPoint = '';
			    endLatPoint = '';

			}
			*/
		    
		});		

	
}

 var rowData = {};
function openFindNearMultySite(rowData) {

    console.log(rowData);
     Object.values(rowData).forEach(function(row) {
      var seq = row.seq;
      var siteId = row.siteId;
      var lat = row.lat;
      var lng = row.lng;
      console.log("Seq: " + seq);
      console.log("Site ID: " + siteId);
      console.log("Lat: " + lat);
      console.log("Lng: " + lng);
    });
/*var tableRow = '<tr>' +
    '<td>' + seq + '</td>' +
    '<td>' + siteId + '</td>' +
    '<td>' + lng + '</td>' +
    '<td>' + lat + '</td>' +
    '</tr>';
    $('#Multy_auxiliary').append(tableRow);
    */
	/* document.getElementById("closestDistanceRange_multy");
	 document.getElementById("NoOfPoints_multy");															
  
	 $('a[href="#MultyClosest"]').click();
     $("#circleRange_multy").prop("checked",true);
  
    
		if(lng != '' &&  lat != '' && closestDistanceRange_multy != ''){
			$("#noP_Multy").val(noP);
			$("#Seq_Multy").val(seq);
			$("#siteId_Multy").val(siteId);
			$("#siteLat_Multy").val(lat);
			$("#siteLng_Multy").val(lng);
			$("#closestDistanceRange_multy").val(closestDisRange_multy);
			showPointsType =getRelatedPoints;
			
		     if(getRelatedPoints == '1') {
				$("#getRelatedPoints_Multy").prop('checked', true);
			 }
			else {
				$("#getRelatedPoints_Multy").prop('checked', false);
			}
		} else {
			alert("Please Fill All Fields");
		} */
}

function  openFindNearest(checkedOption,closestLatPoint,closestLongPoint,closestDisRange,noP,arrayManhole,arrayHandhole,arrayDB,arrayFibers,arrayStrands,arrayTubes,getRelatedPoints){
	 $("#StartEnd").prop("checked",false);
	 document.getElementById("closestLongDiv").style.display = "block";
	 document.getElementById("closestLatDiv").style.display = "block";
	 document.getElementById("closestDistanceRange").style.display = "block";
	 document.getElementById("setCoordDiv").style.display = "block";
	 document.getElementById("NoOfPoints").style.display = "block";
	 document.getElementById("StartLatDiv").style.display = "none";
	 document.getElementById("EndLatDiv").style.display = "none";
	 document.getElementById("StartLongDiv").style.display = "none";
	 document.getElementById("EndLongDiv").style.display = "none";
	 document.getElementById("setEndPointDiv").style.display = "none";
	 document.getElementById("setStartPointDiv").style.display = "none";
	 $('a[href="#closest"]').click();
	 $("#fiberCitySearch").modal("show");
    $("#circleRange").prop("checked",true);
    
    	

		if(closestLatPoint != '' &&  closestLongPoint != '' && closestDisRange != ''){
			var finalArrayFibers = [];
			$("#noP").val(noP);
			$("#closestLatPoint").val(closestLatPoint);
			$("#closestLongPoint").val(closestLongPoint);
			$("#closestDisRange").val(closestDisRange);
			showPointsType =getRelatedPoints;
			
		     if(getRelatedPoints == '1') {
				$("#getRelatedPoints").prop('checked', true);
			 }
			else {
				$("#getRelatedPoints").prop('checked', false);
			}
			
			
					 appendNearestManholesTable(arrayManhole);
					 appendNearestHandholesTable(arrayHandhole);					        			
					 appendNearestDBoardTable(arrayDB);
					 finalArrayFibers.push(arrayStrands);
					 finalArrayFibers.push(arrayTubes);
					 finalArrayFibers.push(arrayFibers);
					 appendNearestFiberPathsTable(finalArrayFibers);

					 $("#totalManhole").val(arrayManhole.length);
					 $("#totalHandhole").val(arrayHandhole.length);
					 $("#totalDB").val(arrayDB.length);
					 const myLatLng = { lat: parseFloat(closestLatPoint), lng: parseFloat(closestLongPoint) };
				   
					//restMAP();
					//$('#filterr').show();

																
					new google.maps.Marker({
							 position: myLatLng,
						     map,
							 title: "Marked",
					});
					//var circleRadius = closestDisRange * 1.609344;
					var circleRadius = closestDisRange *1.609344 *1.609344;
					console.log("circleRadius "+circleRadius);
					var circ = new google.maps.Circle({
					         strokeColor: "blue",
					         strokeOpacity: 0.8,
					         strokeWeight: 2,
					         fillColor: "blue",
					         fillOpacity: 0.1,
					         map,
					         center: myLatLng,
					         radius: circleRadius * 1000,
					       });		
						displayZoneMap(circ);
				        map.setCenter(myLatLng);
					    map.fitBounds(circ.getBounds());
		     		
		      

		} else {
			alert("Please Fill All Fields");
		}
		closestLatPoint='';
		closestLongPoint='';
		closestDisRange='';
}

function openFindBetweenMarkers(checkedOption,startLongPoint,startLatPoint,endLongPoint,endLatPoint,arrayManhole,arrayHandhole,arrayDB,arrayFibers,arrayStrands,arrayTubes,getRelatedPoints){
	 $("#circleRange").prop("checked",false);
	 document.getElementById("closestLongDiv").style.display = "none";
	 document.getElementById("closestLatDiv").style.display = "none";
	 document.getElementById("closestDistanceRange").style.display = "none";
	 document.getElementById("setCoordDiv").style.display = "none";
	 document.getElementById("NoOfPoints").style.display = "none";
	 document.getElementById("StartLatDiv").style.display = "block";
	 document.getElementById("EndLatDiv").style.display = "block";
	 document.getElementById("StartLongDiv").style.display = "block";
	 document.getElementById("EndLongDiv").style.display = "block";
	 document.getElementById("setEndPointDiv").style.display = "block";
	 document.getElementById("setStartPointDiv").style.display = "block";
		
	 $('a[href="#closest"]').click();
	 $("#fiberCitySearch").modal("show");
	 $("#StartEnd").prop("checked",true);
	 if(startLongPoint != '' &&  startLatPoint != '' && endLongPoint != '' && endLatPoint != ''){
		$("#startLongPoint").val(startLongPoint);
		$("#startLatPoint").val(startLatPoint);
		$("#endLongPoint").val(endLongPoint);
		$("#endLatPoint").val(endLatPoint);	
		
		showPointsType =getRelatedPoints;
	     if(getRelatedPoints == '1') {
			$("#getRelatedPoints").prop('checked', true);
		 }
		else {
			$("#getRelatedPoints").prop('checked', false);
		}
	     
		var finalArrayFibers = [];
			 appendNearestManholesTable(arrayManhole);
			 appendNearestHandholesTable(arrayHandhole);					        			
			 appendNearestDBoardTable(arrayDB);
			 finalArrayFibers.push(arrayStrands);
			 finalArrayFibers.push(arrayTubes);
			 finalArrayFibers.push(arrayFibers);
			 appendNearestFiberPathsTable(finalArrayFibers);

				$("#totalManhole").val(arrayManhole.length);
				$("#totalHandhole").val(arrayHandhole.length);
				$("#totalDB").val(arrayDB.length);
					
					
				startlangPath =[new google.maps.LatLng(startLatPoint,startLongPoint), new google.maps.LatLng(endLatPoint,startLongPoint)];
				drawLine("#FF0000",startlangPath);
				startlatgPath =[new google.maps.LatLng(startLatPoint,startLongPoint), new google.maps.LatLng(startLatPoint,endLongPoint)];
				drawLine("#FF0000",startlatgPath);
				endlangPath =[new google.maps.LatLng(endLatPoint,endLongPoint), new google.maps.LatLng(startLatPoint,endLongPoint)];
				drawLine("#006400",endlangPath);
				endlatgPath =[new google.maps.LatLng(endLatPoint,endLongPoint), new google.maps.LatLng(endLatPoint, startLongPoint)];
				drawLine("#006400",endlatgPath);
				const myLatLng = { lat: parseFloat((parseFloat(startLatPoint) + parseFloat(endLatPoint)) / 2.0), lng: parseFloat((parseFloat(startLongPoint) + parseFloat(endLongPoint)) / 2.0) };
				map.setCenter(myLatLng);
				var btmLeftLat, btmLeftLng, topRgtLat, topRgtLng;
				if (parseFloat(startLatPoint) <= parseFloat(endLatPoint)) {
					btmLeftLat = startLatPoint;
					topRgtLat = endLatPoint;
				}
				else {
					btmLeftLat = endLatPoint;
					topRgtLat = startLatPoint;
				}
				if (parseFloat(startLongPoint) <= parseFloat(endLongPoint)) {
					btmLeftLng = startLongPoint;
					topRgtLng = endLongPoint;
				}
				else {
					btmLeftLng = endLongPoint;
					topRgtLng = startLongPoint;
				}
				map.fitBounds(new google.maps.LatLngBounds(
							  //bottom left
							  new google.maps.LatLng(parseFloat(btmLeftLat), parseFloat(btmLeftLng)),
							  //top right
							  new google.maps.LatLng(parseFloat(topRgtLat), parseFloat(topRgtLng))
				));
	        	
   } else {
	alert("Please Fill All Fields");
   }
	
	startLongPoint = '';
	startLatPoint = '';
   endLongPoint = '';
   endLatPoint = '';
	
}


function drawLine(color,path){
	var line = new google.maps.Polyline({
	    path: path,
	    strokeColor: color,
	    strokeOpacity: 0.8,
        strokeWeight: 5,
        geodesic: true,
	    map: map
	});
	
}

function searchConnectedButtonEvents(hash_Project,hash_manhole,hash_handhole,hash_fiber,hash_DBoard,hash_fiberTubes,hash_FiberStrands,hash_FiberAuxiliaries,hash_TubeAuxiliaries,hash_strandsAuxiliaries,hash_trensh,hash_trenchAuxiliary){
	
	document.getElementById("viewOnMap").addEventListener("change", function (event) {
		 if (event.target.checked) {
			 $(this).attr('value', 'true');
		 } else {
			 $(this).attr('value', 'false');
		 }
	});
    
	$(".searchHeaderButton").on('click',function(){
        var urlString = "";
      	$("#conStrandId").html("");
    	$("#conFiberId").html("");
    	$("#conTubeId").html("");
		$("#conDBId").html("");
		
		checkedOption = "connected";
	    urlString += "&siteId="+$("#autoCompleteConnectedSearch").val()+"";
	    urlString += "&selectConnectedSearch="+document.querySelector('#selectConnectedSearch').value+"";
	    urlString += "&connectedSearchLong="+$("#connectedSearchLong").val()+"";
	    urlString += "&connectedSearchLat="+$("#connectedSearchLat").val()+"";
	    urlString += "&connectedViewOnMap="+$("#viewOnMap").val()+"";
		urlString += "&getRelatedPoints="+$("#getRelatedPointsCon").val()+"";	

		window.location.href = getContext()+"/NetworkPhysicalLayer?Checked="+checkedOption+urlString;
		
		// console.log("selectHeaderSearch1 "+document.querySelector('#selectHeaderSearch1').value);
      	
    	});
		
}

function openSearchConnected(checkedOption,siteId,selectConnectedSearch,connectedSearchLong,connectedSearchLat,connectedViewOnMap,arrayStrands,arrayTubes,arrayFibers,arrayManhole,arrayHandhole,arrayDB,distribBoardListSize,getRelatedPoints){
	//console.log("selectConnectedSearch "+selectConnectedSearch);
	$('a[href="#connectedS"]').click();
	$("#fiberCitySearch").modal('show');
 	$("#selectConnectedSearch").val(selectConnectedSearch);
 	$("#autoCompleteConnectedSearch").val(siteId);
 	$("#connectedSearchLong").val(connectedSearchLong);
 	$("#connectedSearchLat").val(connectedSearchLat);
	
 	showPointsType =getRelatedPoints;
    if(getRelatedPoints == '1') {
		$("#getRelatedPointsCon").prop('checked', true);
	 }
	else {
		$("#getRelatedPointsCon").prop('checked', false);
	}
 	const myLatLng = { lat: parseFloat(connectedSearchLat), lng: parseFloat(connectedSearchLong) };
 	 var siteArray = [];
 	siteArray.push(arrayStrands);
 	siteArray.push(arrayTubes);
 	siteArray.push(arrayFibers);
 	siteArray.push(arrayDB.splice(0,distribBoardListSize));
 	appendConnectedTable(siteArray);
 	
 	 
 	if (connectedViewOnMap == "true") {
		$("#viewOnMap").prop("checked",true);
	  
 	    	var markerName = $("#autoCompleteConnectedSearch").val().split(":")[1]+":" +$("#autoCompleteConnectedSearch").val().split(":")[0]+":"+$("#autoCompleteConnectedSearch").val().split(":")[2];
			createSiteCltMarker($("#autoCompleteConnectedSearch").val().split(":")[1],markerName,connectedSearchLat,connectedSearchLong,siteCltSrcMarkers);	  
				
			var circ = new google.maps.Circle({
		         strokeColor: "blue",
		         strokeOpacity: 0.8,
		         strokeWeight: 2,
		         fillColor: "blue",
		         fillOpacity: 0.1,
		         map,
		         center: myLatLng,
		         radius: 500,
		       });		
		 displayZoneMap(circ);
		 map.setCenter(myLatLng);
		 map.fitBounds(circ.getBounds());
		 
		
     } else {
    	 $("#viewOnMap").prop("checked",false);
     }
	
}

function calculateDistanceSourceDestination(sLat,sLng,dLat,dLng,tId){
		var auxArrayP=[];
		var countrowAuxiliaryFO =$('#'+tId+' >tbody >tr').length;
			
					$('#'+tId+' >tbody >tr').each(function(){
											
							var latAux = $(this).find("td:eq(4) input[type='text']").val();
							var lngAux =$(this).find("td:eq(3) input[type='text']").val();
							auxArrayP.push({ lat:latAux,lng:lngAux });
										
					});
										
					if(countrowAuxiliaryFO>0){
						var sourceFirstAux = haversine_distance(sLat,sLng,auxArrayP[0].lat,auxArrayP[0].lng);

						var betweenAux = 0;
						var totalDistanceis=0;
						$("#"+tId+" >tbody >tr").eq(0).find('td').eq(5).children().val(parseFloat(sourceFirstAux).toFixed(3));
		
						for (var c=0; c<countrowAuxiliaryFO-1; c++){
							var strictBetween = haversine_distance(auxArrayP[c].lat,auxArrayP[c].lng,auxArrayP[c+1].lat,auxArrayP[c+1].lng);				
							betweenAux += strictBetween;
							$("#"+tId+" >tbody >tr").eq(c+1).find('td').eq(5).children().val(parseFloat(strictBetween).toFixed(3));

						}
										
						var destinationLastAux = haversine_distance(auxArrayP[countrowAuxiliaryFO-1].lat,auxArrayP[countrowAuxiliaryFO-1].lng,dLat,dLng);
										
						totalDistanceis=sourceFirstAux+betweenAux+destinationLastAux;
										

						if(tId == 'auxiliaryTable'){
							
							$("#FiberLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#totalDistance").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDest").val(parseFloat(destinationLastAux).toFixed(3));
						}
						else if(tId == 'auxiliaryTableStrands'){
						
							$("#totalDistanceStrand").val(parseFloat(totalDistanceis).toFixed(3));
							$("#StrandLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestStrnd").val(parseFloat(destinationLastAux).toFixed(3));
						}
						else if(tId == 'auxiliaryTableTubes'){
						
							$("#totalDistanceTube").val(parseFloat(totalDistanceis).toFixed(3));
							$("#TubeLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestTube").val(parseFloat(destinationLastAux).toFixed(3));
						}
						else if(tId == 'auxiliary_trenchTable'){
							$("#trenchLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestTrench").val(parseFloat(destinationLastAux).toFixed(3));
							$("#totalDistanceTrench").val(parseFloat(totalDistanceis).toFixed(3));
						}
						else if(tId == 'auxiliary_ductTable'){
							$("#ductsLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestDuct").val(parseFloat(destinationLastAux).toFixed(3));
							$("#totalDistanceDuct").val(parseFloat(totalDistanceis).toFixed(3));
							
							
						}
						//console.log("calculateDistanceSourceDestination: "+$("#FiberLength").val());
						
					} else {
						totalDistanceis = haversine_distance(sLat,sLng,dLat,dLng);
						
						if(tId == 'auxiliaryTable'){
							$("#FiberLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#totalDistance").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDest").val('0.0');
						}
						else if(tId == 'auxiliaryTableStrands'){
							$("#StrandLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#totalDistanceStrand").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestStrnd").val('0.0');
						}
						else if(tId == 'auxiliaryTableTubes'){
							$("#totalDistanceTube").val(parseFloat(totalDistanceis).toFixed(3));
							$("#TubeLength").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestTube").val('0.0');
						}
						else if(tId == 'auxiliary_ductTable'){
							$("#totalDistanceDuct").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestDuct").val('0.0');
						}
						else if(tId == 'auxiliary_trenchTable'){
							$("#totalDistanceTrench").val(parseFloat(totalDistanceis).toFixed(3));
							$("#distanceLstAuxToDestTrench").val('0.0');
						}
					}
					auxArrayP=[];
	}
	
function calculateDistanceAuxTube(sLat,sLng,dLat,dLng,tId,cId,indexForAuxs){
	
	var auxArrayP=[];
	var auxTarget="";
	var fCableLength = parseInt($("#FiberLength").val());
	var distanceLstAuxToDestID ="";
	var totalDistanceID="";
	
	if(tId=="TubeAuxTable"){
		auxTarget=tId;
		$('#'+tId+' >tbody >tr').each(function(){
			var latAux = $(this).find("td:eq(4) input[type='text']").val();
			var lngAux =$(this).find("td:eq(3) input[type='text']").val();
			auxArrayP.push({ lat:latAux,lng:lngAux });
		});	
		distanceLstAuxToDestID="distanceLstAuxToDestFiberTube";
		totalDistanceID="totalDistanceFiberTube";
	}
	else if(tId=="StrandAuxTable" ){
		auxTarget=tId;
		$('#'+tId+' >tbody >tr').each(function(){
			var latAux = $(this).find("td:eq(4) input[type='text']").val();
			var lngAux =$(this).find("td:eq(3) input[type='text']").val();
			auxArrayP.push({ lat:latAux,lng:lngAux });
		});	
		distanceLstAuxToDestID="distanceLstAuxToDestFiberStrand";
		totalDistanceID="totalDistanceFiberStrand";
	}
	else if(tId=="tubesTable"){
		auxTarget="TubeAuxTable";
		if(window["Auxpts_Tubes"+cId]) {
			for(k=0;k<window["Auxpts_Tubes"+cId].length;k++){
				var latAux = window["Auxpts_Tubes"+cId][k].aux_Latitude;
				var lngAux = window["Auxpts_Tubes"+cId][k].aux_Longitude;
				auxArrayP.push({ lat:latAux,lng:lngAux });
			}
			distanceLstAuxToDestID="distanceLstAuxToDestFiberTube";
			totalDistanceID="totalDistanceFiberTube";
		}
	}
	else if(tId=="strandsTable"){
		auxTarget="StrandAuxTable";
		if(window["Auxpts_Strands"+cId]) {
			for(k=0;k<window["Auxpts_Strands"+cId].length;k++){
				var latAux = window["Auxpts_Strands"+cId][k].aux_Latitude;
				var lngAux = window["Auxpts_Strands"+cId][k].aux_Longitude;
				auxArrayP.push({ lat:latAux,lng:lngAux });
			}
			distanceLstAuxToDestID="distanceLstAuxToDestFiberStrand";
			totalDistanceID="totalDistanceFiberStrand";
		}
	}
	
	if(auxArrayP.length>0){
		var sourceFirstAux = haversine_distance(sLat,sLng,auxArrayP[0].lat,auxArrayP[0].lng);
		$("#"+auxTarget+" >tbody >tr").eq(0).find('td').eq(5).children().val(parseFloat(sourceFirstAux).toFixed(3));
		
		var betweenAux = 0;
		var totalDistanceis=0;
		for (var c=0; c<auxArrayP.length-1; c++){
			var strictBetween = haversine_distance(auxArrayP[c].lat,auxArrayP[c].lng,auxArrayP[c+1].lat,auxArrayP[c+1].lng);			
			betweenAux += strictBetween;
			$("#"+auxTarget+" >tbody >tr").eq(c+1).find('td').eq(5).children().val(parseFloat(strictBetween).toFixed(3));
		}
									
		var destinationLastAux = haversine_distance(auxArrayP[auxArrayP.length-1].lat,auxArrayP[auxArrayP.length-1].lng,dLat,dLng);
		$("#"+distanceLstAuxToDestID).val(parseFloat(destinationLastAux).toFixed(3));

		totalDistanceis=sourceFirstAux+betweenAux+destinationLastAux;
														
	}
	else{
		totalDistanceis = parseFloat(haversine_distance(sLat,sLng,dLat,dLng)).toFixed(3);
	}
	$("#"+totalDistanceID).val(parseFloat(totalDistanceis).toFixed(3));

	auxArrayP=[];
	return totalDistanceis;
}
// to be deleted	
/*	function findNearestPointsHand (noPoints,closestRange,coordsArray,closestLat,closestLong,target) {
		var nearstPoints=[];
		var drivinDisAr =[];
		var isPaused = false;
	
		var pointDist=0;
		var len = coordsArray.length;
		var result=[];
		let directionsService = new google.maps.DirectionsService();
	  
		var lngg =0;
		var latt =0;
		nearstPoints=[];
		  for (i = 0; i < len; i++) {
		var nPointData=[];
	
				 pointDist =Number(haversine_distance(closestLat,closestLong,coordsArray[i][3],coordsArray[i][2]));
			
				
				if (pointDist < closestRange) {
	
					coordsArray[i].push(pointDist);
					coordsArray[i].push("");
	
					nearstPoints.push(coordsArray[i]);
				
					}
	

		  }
		  //alert(nearstPoints);

	setTimeout(function() {
			
	
	
	var delayFactor = 0;
	
	var resultLength = nearstPoints.length ;
	
	if(nearstPoints.length <= 10){
	resultLength=nearstPoints.length;
	}else{
	resultLength =10;
	
	}
	for(let j =0 ; j< nearstPoints.length;j++){
			if(j>=3){
	
	
			}else{
				const originept = {lat: parseFloat(closestLat), lng: parseFloat(closestLong)};
				const nextpt = {lat: parseFloat(nearstPoints[j][3]), lng: parseFloat(nearstPoints[j][2])};
				var request  = {
					origin: originept,
					destination: nextpt,
					travelMode  : google.maps.DirectionsTravelMode.DRIVING
				}
				getDrivingDistance(request);
				function getDrivingDistance (request) {
				directionsService.route(request, function(response, status) {
					if ( status == google.maps.DirectionsStatus.OK ) {
						if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
							setTimeout(function () {
								getDrivingDistance(request);
							},  100);}
							else{
					 var resultt= ( response.routes[0].legs[0].distance.value) /1000 ;
					   drivinDisAr.push(resultt);
					   result[j][10]=resultt;}
			
					}else if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
						setTimeout(function () {
							getDrivingDistance(request);
						},  100);}
					else {
						result[j][10]="no Root";
					}
					$("#searchHanhTBody").empty();
			
			
						appendNearestHandholesTable(result);
				  });
				  
				}
	
			}

	}
	}, 1000);
	///////////////////////////////////////
	
	isPaused = false;
	
			
				function SortArray(a, b) {

					if (a[9] > b[9]) {
						return 1;
					  } else if (b[9] > a[9]) {
						return -1;
					  } else {
						return 0;
					  }		
					}
					nearstPoints.sort(SortArray);
					
		
		if(noPoints==""){
			result = nearstPoints;
	
		}else {
			result = nearstPoints.slice(0,noPoints);
		}
	
			return result;
	
		}
		*/

	// to be deleted
	/*
	 function findMarkerPointsBetween(coordsArray,startLongPoint,startLatPoint,endLongPoint,endLatPoint,target){
		var pointsBetween = [];
		var lat = 0.0;
		var long = 0.0;
		for (i = 0; i < coordsArray.length; i++) {
			coordsArray[i].push(00000);
			if(target == "Manhole"){
				lat = coordsArray[i][3];
				long = coordsArray[i][2];
			}else if(target == "Handhole"){
				lat = coordsArray[i][3];
				long = coordsArray[i][2];
			}else if(target == "DBoard"){
				lat = coordsArray[i][2];
				long = coordsArray[i][1];
			}
			
			if(Number(long) > endLongPoint  &&  Number(lat) >  endLatPoint  && Number(long) <  startLongPoint &&  Number(lat) < startLatPoint){
	            //if(Number(long) > Number(endLongPoint)  &&  Number(lat) >  Number(endLatPoint)  &&  Number(long) <  Number(startLongPoint) &&  Number(lat) <  Number(startLatPoint)){
	            	pointsBetween.push(coordsArray[i]);
	    			//console.log("lat is "+lat+" and long is "+long);
	    			//console.log("start long: "+startLongPoint+ " start lat: "+startLatPoint+" end long: "+endLongPoint+" end lat: "+endLatPoint);
	        }
            
		}
		//console.log(pointsBetween);
		return pointsBetween;
	}
	*/
	// to be deleted
/*
		function findNearestPointsMan (noPoints,closestRange,coordsArray,closestLat,closestLong,target) {

			var nearstPoints=[];
			var drivinDisAr =[];
			var isPaused = false;
		
			var pointDist=0;
			var len = coordsArray.length;
			var result=[];
			let directionsService = new google.maps.DirectionsService();
			let directionsRenderer = new google.maps.DirectionsRenderer();
			directionsRenderer.setMap(map); // Existing map object displays directions
		  
			var lngg =0;
			var latt =0;
			  for (i = 0; i < len; i++) {
				coordsArray[i].push(00000);

			var nPointData=[];
		
					 pointDist =Number(haversine_distance(closestLat,closestLong,coordsArray[i][3],coordsArray[i][2]));
				
					if (pointDist < closestRange) {
						coordsArray[i][9]=(pointDist);
						coordsArray[i].push("");

						nearstPoints.push(coordsArray[i]);

						}
		
			  }
			  console.log(nearstPoints);
			  /////////// driving distance /////////////

			setTimeout(function() {
		var delayFactor = 0;
		
		var resultLength = nearstPoints.length ;
		
		if(nearstPoints.length <= 10){
		resultLength=nearstPoints.length;
		}else{
		resultLength =10;
		
		}
		for(let j =0 ; j< nearstPoints.length;j++){
				if(j<=nearstPoints.length -4){
		
		
				}else{
					const originept = {lat: parseFloat(closestLat), lng: parseFloat(closestLong)};
					const nextpt = {lat: parseFloat(nearstPoints[j][3]), lng: parseFloat(nearstPoints[j][2])};
					var request  = {
						origin: originept,
						destination: nextpt,
						travelMode  : google.maps.DirectionsTravelMode.DRIVING
					}
					getDrivingDistance(request);
					function getDrivingDistance (request) {
					directionsService.route(request, function(response, status) {
						if ( status == google.maps.DirectionsStatus.OK ) {
							if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
								setTimeout(function () {
									getDrivingDistance(request);
								},  100);}
								else{
						 var resultt= ( response.routes[0].legs[0].distance.value) /1000 ;
						   drivinDisAr.push(resultt);
						   result[j][10]=resultt;
						}
				
						}else if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
							setTimeout(function () {
								getDrivingDistance(request);
							},  100);}
						else {
							result[j][10]="no Root";
						}
						$("#searchManhTBody").empty();
				
						//alert("drivinDisAr "+drivinDisAr);
				
							appendNearestManholesTable(result);
					  });
					  
					}
		
				}
		
					
		
		}
		}, 1000);
		///////////////////////////////////////
		isPaused = false;
			function SortArray(a, b) {

			if (b[9] > a[9]) {
				return 1;
			  } else if (a[9] > b[9]) {
				return -1;
			  } else {
				return 0;
			  }		
			}
			nearstPoints.sort(SortArray);



			
			if(noPoints==""){
				result = nearstPoints;
		
			}else {
				result = nearstPoints.slice(0,noPoints);
			}
		
				return result;
		
			}
		
function findNearestPointsDb (noPoints,closestRange,coordsArray,closestLat,closestLong,target) {
	var nearstPoints=[];
	var drivinDisAr =[];
	var isPaused = false;

	var pointDist=0;
	var len = coordsArray.length;
	var result=[];
	let directionsService = new google.maps.DirectionsService();
	let directionsRenderer = new google.maps.DirectionsRenderer();
	directionsRenderer.setMap(map); // Existing map object displays directions
	var lngg =0;
	var latt =0;

	  for (i = 0; i < len; i++) {
	var nPointData=[];

			 pointDist =Number(haversine_distance(closestLat,closestLong,coordsArray[i][2],coordsArray[i][1]));
		
			
			if (pointDist < closestRange) {
				//alert("coordsArray[i] "+coordsArray[i]);

				coordsArray[i].push(pointDist);
				coordsArray[i].push("");

				nearstPoints.push(coordsArray[i]);


				
				}


		   
			   
	  }

	/////////// drivin distance /////////////
setTimeout(function() {
		


var delayFactor = 0;

var resultLength = nearstPoints.length ;

if(nearstPoints.length <= 10){
resultLength=nearstPoints.length;
}else{
resultLength =10;

}
for(let j =0 ; j< nearstPoints.length;j++){
		if(j>=3){


		}else{
			const originept = {lat: parseFloat(closestLat), lng: parseFloat(closestLong)};
			const nextpt = {lat: parseFloat(nearstPoints[j][2]), lng: parseFloat(nearstPoints[j][1])};
			var request  = {
				origin: originept,
				destination: nextpt,
				travelMode  : google.maps.DirectionsTravelMode.DRIVING
			}
			getDrivingDistance(request);
			function getDrivingDistance (request) {
			directionsService.route(request, function(response, status) {
				if ( status == google.maps.DirectionsStatus.OK ) {
					if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
						setTimeout(function () {
							getDrivingDistance(request);
						},  100);}
						else{
				 var resultt= ( response.routes[0].legs[0].distance.value) /1000 ;
				   drivinDisAr.push(resultt);
				   result[j][9]=resultt;}
		
				}else if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
					setTimeout(function () {
						getDrivingDistance(request);
					},  100);}
				else {
					if (status === google.maps.DirectionsStatus.OVER_QUERY_LIMIT) {
						setTimeout(function () {
							getDrivingDistance(request);
						},  100);}
				}
				$("#searchDBoardTBody").empty();
		
				appendNearestDBoardTable(result);
					
			  });
			  
			}

		}

			

}
}, 1000);
///////////////////////////////////////

isPaused = false;

function SortArray(a, b) {

	if (a[8] > b[8]) {
		return 1;
	  } else if (b[8] > a[8]) {
		return -1;
	  } else {
		return 0;
	  }		
	}
	nearstPoints.sort(SortArray);

	
	if(noPoints==""){
		result = nearstPoints;

	}else {
		result = nearstPoints.slice(0,noPoints);
	}

		return result;

	}
*/

	// to be deleted
/*function findNearestFiberPaths(nmanhole,nhandhole,ndboard,fibersArray,tubesArray,strandsArray){

			var nearstFilterdFibers=[];
			var nearestFilterdTubes=[];
			var nearestFilterdStrands=[];
			var appendresult=[];
			var treeresult=[];
			var fibersResult=[];
			var tubesResult=[];
			var strandsResult=[];
			
			nmanhole.forEach(myFun);	
			nhandhole.forEach(myFun);
			
		function myFun(item){
			
				var fibers = fibersArray.filter(function (el){
					var f=item[0]+":"+item[1];
					if(el[6]==f || el[5]==f){
						 fibersResult.push(el);
					}
					});	
				
				var tubes = tubesArray.filter(function (el){
					var c=item[0]+":"+item[1];
					if(el[6]==c || el[5]==c){
						 tubesResult.push(el);
					}
					});		
					
					
				var strands = strandsArray.filter(function (el){
					var s=item[0]+":"+item[1];
					if(el[6]==s || el[5]==s){
						 strandsResult.push(el);
					}
					});		
						

		nearstFilterdFibers= fibersResult.filter((item, index) => {
			 return fibersResult.indexOf(item) === index;
		});
		
		nearestFilterdTubes= tubesResult.filter((item, index) => {
			 return tubesResult.indexOf(item) === index;
		});
		
		nearestFilterdStrands= strandsResult.filter((item, index) => {
			 return strandsResult.indexOf(item) === index;
		});
		
	}
		
		appendresult.push(nearestFilterdStrands);
		appendresult.push(nearestFilterdTubes);
		appendresult.push(nearstFilterdFibers);
		appendNearestFiberPathsTable(appendresult);
		
		
		if(nearestFilterdStrands.length!==0 || nearestFilterdTubes.length!==0){
			var sTubes=[];
			var sFibers=[];
			
			if(nearestFilterdStrands.length!==0){
				for(var i=0;i<nearestFilterdStrands.length;i++){
				sTubes.push(nearestFilterdStrands[i][7]);
				sFibers.push(nearestFilterdStrands[i][8]);
				}
			}
			
			if(nearestFilterdTubes.length!==0){
				for(var i=0;i<nearestFilterdTubes.length;i++){
				sFibers.push(nearestFilterdTubes[i][8]);
				}
						
			}
			sFibers.forEach(myFun2);
			sTubes.forEach(myFun3);
			
			
			function myFun2(item){
				
				var sF = fibersArray.filter(function (el){
					var f=item;
					if(el[4]==f){
						 fibersResult.push(el);
					}
					});	
			}
			
			function myFun3(item){
				
				var sC = tubesArray.filter(function (el){
					var c=item;
					if(el[0]==c){
						tubesResult.push(el);
					}
					});	
			}
			
		nearstFilterdFibers= fibersResult.filter((item, index) => {
			 return fibersResult.indexOf(item) === index;
		});
		
		nearestFilterdTubes= tubesResult.filter((item, index) => {
			 return tubesResult.indexOf(item) === index;
		});
		treeresult.push(nearestFilterdStrands);
		treeresult.push(nearestFilterdTubes);
		treeresult.push(nearstFilterdFibers);
		return treeresult;
			
		} else{
			
			return appendresult;
		}
				
	}
	*/		
function appendNearestFiberPathsTable(result){
		console.log(result);
		var markupNearStrand="";
		var markupNearTube="";
		var markupNearFiber="";		

		if (result.length==0){
			markupNearStrand ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			result[0].forEach((res) => 
				markupNearStrand +="<tr ><td style='min-width:150px;' class='row-pad'>"+res[0]+"</td><td style='min-width:150px;'>"+res[13]+"</td><td style='min-width:350px;'>"+res[6]+"</td><td style='min-width:350px;'>"+res[9]+"</td></tr>"
				);
		}
		$("#nearStrandId").append(markupNearStrand);
		
		if (result.length==0){
			markupNearTube ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			result[1].forEach((res) => 
		markupNearTube +="<tr ><td style='min-width: 150px;' class='row-pad'>"+res[0]+"</td><td style='min-width: 150px;'>"+res[13]+"</td><td style='min-width: 350px;'>"+res[6]+"</td><td style='min-width: 350px;'>"+res[9]+"</td></tr>"
			);
		}						  
		$("#nearTubeId").append(markupNearTube);
		
		if (result.length==0){
			markupNearFiber ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			result[2].forEach((res) => 
				 markupNearFiber +="<tr ><td style='min-width: 150px;' class='row-pad'>"+res[4]+"</td><td style='min-width: 150px;'>"+res[13]+"</td><td style='min-width: 350px;'>"+res[6]+"</td><td style='min-width: 350px;'>"+res[9]+"</td></tr>"
			);
		}						  
		$("#nearFiberId").append(markupNearFiber);
																	
	}
			
function appendNearestDBoardTable(result){
		var markupDBoard="";
		document.getElementById("findNearestDbRes").innerHTML = "";
			
		if (result.length==0){
			document.getElementById("findNearestDbRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
			//markupDBoard ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}
		else { 
				for(var i =0 ; i<result.length;i++){
					//alert(result[i]);
					if($("#StartEnd").is(":checked")){
						markupDBoard +="<tr style='height: 30px;'><td ><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][3]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"

				    }
				    else{
				    	if(result[i][9] == null || result[i][9]==""){
							//markupDBoard +="<tr ><td style='min-width:250px;' class='row-pad'>"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][3]+"</td><td  name='LONGG' style='min-width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='min-width:150px;'  name='LATT'><input name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='min-width:50px;'>"+result[i][8]/1.60934+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='SomeDeleteRowFunction(this)'>Get Distance</button> </td></tr>"
							markupDBoard +="<tr style='height: 30px;'><td><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td><td style='min-width:150px;'>"+result[i][0]+"</td><td style='min-width:150px;'>"+result[i][3]+"</td><td  name='LONGG' style='min-width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='min-width:150px;'  name='LATT'><input name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='min-width:50px;'>"+result[i][8]+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"

						}else{
							markupDBoard +="<tr style='height: 30px;'><td><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td><td style='min-width:150px;'>"+result[i][0]+"</td><td style='min-width:150px;'>"+result[i][3]+"</td><td style='min-width:150px;'>"+result[i][1]+"</td><td style='min-width:150px;'>"+result[i][2]+"</td><td style='min-width:50px;'>"+result[i][8]+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][9])+"</label></td></tr>"

						}
				    	
				    }
					
		
		
				}


		}
		$("#searchDBoardTBody").append(markupDBoard);
		if($("#circleRange").is(":checked")){
			console.log("entered !!!!!!!!!!!!!!!");
			drivingDistance("findNearstDB");
		}
        makeAllSortable();
		
		$("#selectAllDB").click(function(){
			if($(this).is(":checked")){
			console.log("entered "+ $(this).attr('id'));
			$('input[type="checkbox"]', '#findNearstDB').prop('checked', true);
			$(".DistBoard").prop('checked', true);
			$(".AllDistBoards").prop("checked",true);
			markerClusterDistBoard.clearMarkers();
			$("#network_tree").find(".DistBoard:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersDistBoard[id].setMap(map);			
				markerClusterDistBoard.addMarker(markersDistBoard[id]);
													
			});	
			}
			else{
				$('input[type="checkbox"]', '#findNearstDB').prop('checked', false);
				$(".DistBoard").prop('checked', false);
				$(".AllDistBoards").prop("checked",false);
				markerClusterDistBoard.clearMarkers();
			}
			
		});
		
		// checking single row checbox from boq
		$('.DBBOQ').click(function(){		
				console.log("entered "+ $(this).attr('id').split("BOQ_")[1]);
				var DBdId = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+DBdId).children('input:checkbox').prop('checked', true);
					markersDistBoard[DBdId].setMap(map);					
					markerClusterDistBoard.addMarker(markersDistBoard[DBdId]);
				}
				else{
					$("#"+DBdId).children('input:checkbox').prop('checked', false);
					markersDistBoard[DBdId].setMap(null);				
					markerClusterDistBoard.removeMarker(markersDistBoard[DBdId]);
				}
		});
																	
	}
   

	function appendNearestHandholesTable(result){
		var markupHandh="";		
		document.getElementById("findNearestHandRes").innerHTML = "";
		
		if (result.length==0){
			document.getElementById("findNearestHandRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
			//markupHandh ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}
		else {
			for(var i =0 ; i<result.length;i++){
			//alert(result[i]);
				if($("#StartEnd").is(":checked")){
					markupHandh +="<tr style='height: 30px;'><td ><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='handholeId' style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"

			    }
			    else{
			    	if(result[i][8] == null || result[i][8]==""){
						//markupHandh +="<tr style='height: 30px;'><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='SomeDeleteRowFunction(this)'>Get Distance</button> </td></tr>"
						markupHandh +="<tr style='height: 30px;'><td><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][7])+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"


					}else{
						//markupHandh +="<tr style='height: 30px;' ><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][10])+"</label></td></tr>"
						markupHandh +="<tr style='height: 30px;' ><td><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][7])+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][8])+"</label></td></tr>"
					}
			    	
			    }
				


		}
	

		}
		$("#searchHanhTBody").append(markupHandh);
		if($("#circleRange").is(":checked")){
			drivingDistance("findNearstHandhole");
		}
		makeAllSortable();
		
		$("#selectAllHandhole").click(function(){
			if($(this).is(":checked")){
			console.log("entered "+ $(this).attr('id'));
			$('input[type="checkbox"]', '#findNearstHandhole').prop('checked', true);
			$(".Handhole").prop('checked', true);
			$(".AllHandholes").prop("checked",true);
			markerClusterHandhole.clearMarkers();
			 $("#network_tree").find(".Handhole:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersHandhole[id].setMap(map);			
				markerClusterHandhole.addMarker(markersHandhole[id]);
								
		     });	
			}
			else{
				$('input[type="checkbox"]', '#findNearstHandhole').prop('checked', false);
				$(".Handhole").prop('checked', false);
				$(".AllHandholes").prop("checked",false);
				markerClusterHandhole.clearMarkers();
			}
			
		});
		
		// checking single row checbox from boq
		$('.HandholeBOQ').click(function(){
				console.log("entered "+ $(this).attr('id').split("BOQ_")[1]);
				var HandId = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+HandId).children('input:checkbox').prop('checked', true);
					markersHandhole[HandId].setMap(map);
					markerClusterHandhole.addMarker(markersHandhole[HandId]);
				}
				else{
					$("#"+HandId).children('input:checkbox').prop('checked', false);
					markersHandhole[HandId].setMap(null);				
		    		markerClusterHandhole.removeMarker(markersHandhole[HandId]);
				}			
		});

	}
	
	function drivingDistance(tableId) {
		for (indxRow = 0; indxRow < 5 ; indxRow++){
			 console.log("entered indxRow "+ indxRow);
			 $("#"+tableId+" >tbody").find("tr").eq(indxRow).find('td[name="DDistanceB"]').children('button').click();
	    }
	}
	
	function getDrivingDistance(e) {
		if (typeof(e) == "object") {
			var directionsService = new google.maps.DirectionsService();
			
			var lat = $(e).parent().parent().children('td[name="LATT"]').children('input').val();
			var lng = $(e).parent().parent().children('td[name="LONGG"]').children('input').val();
			
			const originept = {lat: parseFloat(parseFloat($("#closestLatPoint").val())), lng: parseFloat(parseFloat($("#closestLongPoint").val()))};
			const nextpt = {lat: parseFloat(lat), lng: parseFloat(lng)};
			var request  = {
				origin: originept,
				destination: nextpt,
				travelMode  : google.maps.DirectionsTravelMode.DRIVING
			}
			directionsService.route(request, function(response, status) {
				if ( status == google.maps.DirectionsStatus.OK ) {
					var resultt= ( response.routes[0].legs[0].distance.value) /1000 ;

				   }
				else {
					resultt= "no Root";
					//alert("no resultt ");
				}
				$(e).parent().parent().children('td[name="DDistanceB"]').children('button').hide();
				$(e).parent().parent().children('td[name="DDistance"]').children('input').show();
				$(e).parent().parent().children('td[name="DDistance"]').children('label').empty();
				$(e).parent().parent().children('td[name="DDistance"]').children('label').append(resultt);
				makeAllSortable();
			  });
			  
		} else {
			return false;
		}

	}

	
	function sortTable(table, col, reverse) {
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
	}
	
	function makeSortable(table) {
		var th = table.tHead, i;
		th && (th = th.rows[0]) && (th = th.cells);
		if (th) i = th.length;
		else return; // if no `<thead>` then do nothing
		while (--i >= 0) (function (i) {
			var dir = 1;
			th[i].addEventListener('click', function () {sortTable(table, i, (dir = 1 - dir))});
		}(i));
	}
	
	function makeAllSortable(parent) {
		parent = parent || document.body;
		var t = parent.getElementsByTagName('table'), i = t.length;
		while (--i >= 0) makeSortable(t[i]);
	}
	function appendNearestManholesTable(result){
		var markupManh="";
		document.getElementById("findNearestManRes").innerHTML = "";
								
		if (result.length==0){
		document.getElementById("findNearestManRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		//markupManh ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}
		else {for(var i =0 ; i<result.length;i++){
			//alert(result[i]);
			if($("#StartEnd").is(":checked")){
				markupManh +="<tr style='height: 30px;'><td ><input type='checkbox' class='ManholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='manholeId' style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"

		    }
		    else{
			
		    	if(result[i][9] == null || result[i][9]==""){
				//markupManh +="<tr style='height: 30px;'><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='SomeDeleteRowFunction(this)'>Get Distance</button> </td></tr>"
				markupManh +="<tr style='height: 30px;'><td ><input type='checkbox' class='ManholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='manholeId' style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][7])+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistance(this)'>Get Distance</button> </td></tr>"

			    }else{
				//markupManh +="<tr style='height: 30px;' ><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][10])+"</label></td></tr>"
				markupManh +="<tr style='height: 30px;' ><td><input type='checkbox' class='ManholeBOQ' id=BOQ_"+result[i][0]+" ></td><td  >"+result[i][0]+"</td><td name ='manholeId' style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][7])+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][8])+"</label></td></tr>"

			    }
		}


		}
		}						  
		$("#searchManhTBody").append(markupManh);
		if($("#circleRange").is(":checked")){
			drivingDistance("findNearstManhole");
		}
		makeAllSortable();
		
		$("#selectAllManhole").click(function(){
			if($(this).is(":checked")){
			console.log("entered "+ $(this).attr('id'));
			
			$('input[type="checkbox"]', '#findNearstManhole').prop('checked', true);
			$(".Manhole").prop('checked', true);
			$(".AllManholes").prop("checked",true);
			markerClusterManhole.clearMarkers();
			$("#network_tree").find(".Manhole:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersManhole[id].setMap(map);			
				markerClusterManhole.addMarker(markersManhole[id]);
						
			});
			}
			else{
				$('input[type="checkbox"]', '#findNearstManhole').prop('checked', false);
				$(".Manhole").prop('checked', false);
				$(".AllManholes").prop("checked",false);
				markerClusterManhole.clearMarkers();
			}
		});
		
		// checking single row checbox from boq
		$('.ManholeBOQ').click(function(){
				console.log("entered "+ $(this).attr('id').split("BOQ_")[1]);
				var ManId = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+ManId).children('input:checkbox').prop('checked', true);
					markersManhole[ManId].setMap(map);
					markerClusterManhole.addMarker(markersManhole[ManId]);
				}
				else{
					$("#"+ManId).children('input:checkbox').prop('checked', false);
		    		markersManhole[ManId].setMap(null);				
					markerClusterManhole.removeMarker(markersManhole[ManId]);
				}
			
		});

	}
	
	function appendConnectedTable(result){
		console.log("array of sites "+result);
		var markupConStrand="";
		var markupConTube="";
		var markupConFiber="";
	    var markupConDb="";

	    if (result.length==0){
	    	markupConStrand ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			result[0].forEach((res) => 
			markupConStrand +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[0][0][0]+"</td><td style='min-width:150px;'>"+result[0][0][9]+"</td><td style='min-width:300px;'>"+result[0][0][5]+"</td><td style='min-width:300px;'>"+result[0][0][6]+"</td></tr>"
			
		    );
		}
	    $("#conStrandId").append(markupConStrand);
		
		if (result.length==0){
			markupConTube ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			result[1].forEach((res) => 
			markupConTube +="<tr ><td style='min-width:150px;' class='row-pad'>"+res[0]+"</td><td style='min-width:150px;'>"+res[10]+"</td><td style='min-width:300px;'>"+res[6]+"</td><td style='min-width:300px;'>"+res[9]+"</td></tr>"
		
			);
		}						  
		$("#conTubeId").append(markupConTube);
		
		if (result.length==0){
			markupConFiber ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			result[2].forEach((res) => 
			markupConFiber +="<tr ><td style='min-width:150px;' class='row-pad'>"+res[4]+"</td><td style='min-width:150px;'>"+res[13]+"</td><td style='min-width:300px;'>"+res[6]+"</td><td style='min-width:300px;'>"+res[9]+"</td></tr>"
			);
		}						  
		$("#conFiberId").append(markupConFiber);
																	

		if (result.length==0){
			markupConDb ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			result[3].forEach((res) => 
		    markupConDb +="<tr ><td style='min-width:150px;' class='row-pad'>"+res[0]+"</td><td style='min-width:150px;'>"+res[3]+"</td><td style='min-width:500px;'>"+res[7]+"</td></tr>"
			);
		}						  
		$("#conDBId").append(markupConDb);	
	}
	
// to be deleted
/*
function appendConnectedStrandTable(result,type){
		//console.log(result);
		var markupConStrand="";
		var markupConTube="";
		var markupConFiber="";
	    var markupConDb="";
	    
		if(type=="strand"){
			
			//No fiber and tube to append 
			if (result.length == 1){
			
				markupConStrand +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[0][0][0]+"</td><td style='min-width:150px;'>"+result[0][0][9]+"</td><td style='min-width:300px;'>"+result[0][0][5]+"</td><td style='min-width:300px;'>"+result[0][0][6]+"</td></tr>"
				$("#conStrandId").append(markupConStrand);
							
				markupConTube ="<tr style='height:20px;'><td>There is no result<td></tr>";
				$("#conTubeId").append(markupConTube);
				
				markupConFiber ="<tr style='height:20px;'><td>There is no result<td></tr>"
				$("#conFiberId").append(markupConFiber);
				
				markupConDb ="<tr style='height:20px;'><td>There is no result<td></tr>"
				$("#conDBId").append(markupConDb);
				
				
			}
			
			
			// Append corresponding tube and fiber 
			else {
				
				if(result[2][0][5] !="null"){
					src =result[2][0][5]+":" +result[2][0][7]+":"+result[2][0][6];		
				}
				else {
					if (result[2][0][6].split("_")[0]=="MH" || result[2][0][6].split("_")[0]=="HH" || result[2][0][6].split("_")[0]=="DB" || result[2][0][6].split("_")[0]=="CLT") {
						src  = result[2][0][6]+":" +result[2][0][7];	
					}
					else {
						src = result[2][0][7];
					}
				}
				
				if(result[2][0][8] !="null"){
					dst =result[2][0][8]+":" +result[2][0][10]+":"+result[2][0][9];		
				}
				else {
					if (result[2][0][9].split("_")[0]=="MH" || result[2][0][9].split("_")[0]=="HH" || result[2][0][9].split("_")[0]=="DB" || result[2][0][9].split("_")[0]=="CLT") {
						dst  = result[2][0][9]+":" +result[2][0][10];	
					}
					else {
						dst = result[2][0][10];
					}
				}
				
	
				markupConStrand +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[0][0][0]+"</td><td style='min-width:150px;'>"+result[0][0][9]+"</td><td style='min-width:300px;'>"+result[0][0][5]+"</td><td style='min-width:300px;'>"+result[0][0][6]+"</td></tr>"
				$("#conStrandId").append(markupConStrand);
				
				markupConTube +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[1][0][0]+"</td><td style='min-width:150px;'>"+result[1][0][9]+"</td><td style='min-width:300px;'>"+result[1][0][5]+"</td><td style='min-width:300px;'>"+result[1][0][6]+"</td></tr>"
				$("#conTubeId").append(markupConTube);
				
				markupConFiber +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[2][0][4]+"</td><td style='min-width:150px;'>"+result[2][0][13]+"</td><td style='min-width:300px;'>"+src+"</td><td style='min-width:300px;'>"+dst+"</td></tr>"
				$("#conFiberId").append(markupConFiber);
				
				markupConDb ="<tr style='height:20px;'><td>There is no result<td></tr>"
				$("#conDBId").append(markupConDb);	
				
			}
			
		}
		
	else if(type=="cable"){
		
		if(result[0][0][5] !="null"){
			src =result[0][0][5]+":" +result[0][0][7]+":"+result[0][0][6];		
		}
		else {
			if (result[0][0][6].split("_")[0]=="MH" || result[0][0][6].split("_")[0]=="HH" || result[0][0][6].split("_")[0]=="DB" || result[0][0][6].split("_")[0]=="CLT") {
				src  = result[0][0][6]+":" +result[0][0][7];	
			}
			else {
				src = result[0][0][7];
			}
		}
		
		if(result[0][0][8] !="null"){
			dst =result[0][0][8]+":" +result[0][0][10]+":"+result[0][0][9];		
		}
		else {
			if (result[0][0][9].split("_")[0]=="MH" || result[0][0][9].split("_")[0]=="HH" || result[0][0][9].split("_")[0]=="DB" || result[0][0][9].split("_")[0]=="CLT") {
				dst  = result[0][0][9]+":" +result[0][0][10];	
			}
			else {
				dst = result[0][0][10];
			}
		}
		
			
		if (result.length == 1){
			
			
			markupConFiber +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[0][0][4]+"</td><td style='min-width:150px;'>"+result[0][0][11]+"</td><td style='min-width:300px;'>"+src+"</td><td style='min-width:300px;'>"+dst+"</td></tr>"
			$("#conFiberId").append(markupConFiber);
							
			markupConTube ="<tr style='height:20px;'><td>There is no result<td></tr>";
			$("#conTubeId").append(markupConTube);
			
			markupConStrand ="<tr style='height:20px;'><td>There is no result<td></tr>"
			$("#conStrandId").append(markupConStrand);
					
			markupConDb ="<tr style='height:20px;'><td>There is no result<td></tr>"
			$("#conDBId").append(markupConDb);	
		}
		else if	(result.length == 2){
		
			markupConFiber +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[0][0][4]+"</td><td style='min-width:150px;'>"+result[0][0][11]+"</td><td style='min-width:300px;'>"+src+"</td><td style='min-width:300px;'>"+dst+"</td></tr>"
			$("#conFiberId").append(markupConFiber);
							
			markupConTube +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[1][0][0]+"</td><td style='min-width:150px;'>"+result[1][0][9]+"</td><td style='min-width:300px;'>"+result[1][0][5]+"</td><td style='min-width:300px;'>"+result[1][0][6]+"</td></tr>"
			$("#conTubeId").append(markupConTube);
			
			markupConStrand ="<tr style='height:20px;'><td>There is no result<td></tr>"
			$("#conStrandId").append(markupConStrand);
					
			markupConDb ="<tr style='height:20px;'><td>There is no result<td></tr>"
			$("#conDBId").append(markupConDb);
		}
		else {
			markupConFiber +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[0][0][4]+"</td><td style='min-width:150px;'>"+result[0][0][11]+"</td><td style='min-width:300px;'>"+src+"</td><td style='min-width:300px;'>"+dst+"</td></tr>"
			$("#conFiberId").append(markupConFiber);
							
			markupConTube +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[1][0][0]+"</td><td style='min-width:150px;'>"+result[1][0][9]+"</td><td style='min-width:300px;'>"+result[1][0][5]+"</td><td style='min-width:300px;'>"+result[1][0][6]+"</td></tr>"
			$("#conTubeId").append(markupConTube);
			
			markupConStrand +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[2][0][0]+"</td><td style='min-width:150px;'>"+result[2][0][9]+"</td><td style='min-width:300px;'>"+result[2][0][5]+"</td><td style='min-width:300px;'>"+result[2][0][6]+"</td></tr>"
			$("#conStrandId").append(markupConStrand);
					
			markupConDb ="<tr style='height:20px;'><td>There is no result<td></tr>"
			$("#conDBId").append(markupConDb);
		
		}
	}
		
	
	else {
		
		if (result.length==0){
			markupConStrand ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			
			 markupConStrand +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[0][0][0]+"</td><td style='min-width:150px;'>"+result[0][0][9]+"</td><td style='min-width:300px;'>"+result[0][0][5]+"</td><td style='min-width:300px;'>"+result[0][0][6]+"</td></tr>"

		}						  
		$("#conStrandId").append(markupConStrand);
		
		if (result.length==0){
			markupConTube ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			
			markupConTube +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[1][0][0]+"</td><td style='min-width:150px;'>"+result[1][0][9]+"</td><td style='min-width:300px;'>"+result[1][0][5]+"</td><td style='min-width:300px;'>"+result[1][0][6]+"</td></tr>"

		}						  
		$("#conTubeId").append(markupConTube);
		
		if (result.length==0){
			markupConFiber ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			if(result[2][0][5] !="null"){
				src =result[2][0][5]+":" +result[2][0][7]+":"+result[2][0][6];		
			}
			else {
				if (result[2][0][6].split("_")[0]=="MH" || result[2][0][6].split("_")[0]=="HH" || result[2][0][6].split("_")[0]=="DB" || result[2][0][6].split("_")[0]=="CLT") {
					src  = result[2][0][6]+":" +result[2][0][7];	
				}
				else {
					src = result[2][0][7];
				}
			}
			
			if(result[2][0][8] !="null"){
				dst =result[2][0][8]+":" +result[2][0][10]+":"+result[2][0][9];		
			}
			else {
				if (result[2][0][9].split("_")[0]=="MH" || result[2][0][9].split("_")[0]=="HH" || result[2][0][9].split("_")[0]=="DB" || result[2][0][9].split("_")[0]=="CLT") {
					dst  = result[2][0][9]+":" +result[2][0][10];	
				}
				else {
					dst = result[2][0][10];
				}
			}
				 markupConFiber +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[2][0][4]+"</td><td style='min-width:150px;'>"+result[2][0][13]+"</td><td style='min-width:300px;'>"+src+"</td><td style='min-width:300px;'>"+dst+"</td></tr>"

		}						  
		$("#conFiberId").append(markupConFiber);
		
		if (result.length==0){
			markupConDb ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}else {
			
			 markupConDb +="<tr ><td style='min-width:150px;' class='row-pad'>"+result[5][0][0]+"</td><td style='min-width:150px;'>"+result[5][0][3]+"</td><td style='min-width:500px;'>"+result[5][0][7]+"</td></tr>"

		}	
							  
		$("#conDBId").append(markupConDb);
	}
 }
	
function searchConnected(intendedTarget,arrayStrands,arrayTubes,arrayFibers,arrayManhole,arrayHandhole,arrayDB){
							
		var intendedArray=[];
		var filterResult=[];
		var manholeOrigin=[];
		var handholeOrigin=[];
				
		if(intendedTarget!=="" && intendedTarget!==null){
			//console.log(intendedTarget);
				
		 $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url:  getContext()+'/getConnected',
		                 data: {
								"intendedTarget" : intendedTarget,	
						 },
		                 dataType: "json",
		                 success: function (data) {
		       if (data != null) {
		       
		       //Strand connecting 2 sites 
		       	if (data.strandSearch.length >0) {
		       			       	
			       	filterResult.push(data.strandSearch);
			       	strandSearchDataArray=data.strandSearch;
			       
			       // if the strand has a tube
			        if(strandSearchDataArray[0][7] !=null){			        
			       		var tubeResult = arrayTubes.filter(function (el){			       		
 							 return el[0] ==strandSearchDataArray[0][7];
							});							
																												
						filterResult.push(tubeResult);
			       }
			       // if the strand has a fiber cable
			        if(strandSearchDataArray[0][8] !=null){	
			        		        
			       		var fiberResult = arrayFibers.filter(function (el){
 							 return el[4] ==strandSearchDataArray[0][8];
							});							
																												
						filterResult.push(fiberResult);
			       }
			       
			      
			       appendConnectedStrandTable(filterResult,"strand");
			       
		       	
		       	}
		       	 //fiber connecting 2 sites 
		       	else if (data.cableSearch.length >0) {
		       			       	
			       	filterResult.push(data.cableSearch);
			       	cableSearchDataArray=data.cableSearch;
			       
			       // if the cable has a tube
			       var tubeResult = arrayTubes.filter(function (el){
 							 return el[8] ==cableSearchDataArray[0][4];
					});		
								
					if(tubeResult.length >0){																							
					 filterResult.push(tubeResult);
				    } 
			   
			       appendConnectedStrandTable(filterResult,"cable");		       	
		    }
		       	
		   //Strand as BP in DB
		   else {		       
	          intendedArray=data.searchResult;
								
		if(intendedArray.length!=0){
								
			if(intendedArray[0][0]!==null){				
			
			var dbResult = arrayDB.filter(function (el){
			
 					return el[0] ==intendedArray[0][0];
				});
							
			}
							
			if(intendedArray[0][2]!==null){
										
			var strandResult = arrayStrands.filter(function (el){
 							 return el[0] ==intendedArray[0][2];
							});	
									
		    var strandDesOrigin=strandResult[0][6].split(":")[0];
							
					if(strandDesOrigin.charAt(0)=="M"){
						var manArray = arrayManhole.filter(function (el){
 							 return el[0] ==strandDesOrigin;
							});
							manholeOrigin.push(manArray[0]);
					
					}else if(strandDesOrigin.charAt(0)=="H"){
						var hanArray = arrayHandhole.filter(function (el){
 							 return el[0] ==strandDesOrigin;
							});
							handholeOrigin.push(hanArray[0]);					
					}
							
							filterResult.push(strandResult);
							
		}
		
		if(intendedArray[0][4]!==null){
			var tubeResult = arrayTubes.filter(function (el){
 							 return el[0] ==intendedArray[0][4];
							});
							var tubeDesOrigin=tubeResult[0][6].split(":")[0];
							
							if(tubeDesOrigin.charAt(0)=="M"){
							var manArray = arrayManhole.filter(function (el){
 							 return el[0] ==tubeDesOrigin;
							});
							manholeOrigin.push(manArray[0]);
							
							}else if(tubeDesOrigin.charAt(0)=="H"){
								var hanArray = arrayHandhole.filter(function (el){
		 							 return el[0] ==tubeDesOrigin;
									});
									handholeOrigin.push(hanArray[0]);					
							}
																					
							filterResult.push(tubeResult);
							
		}
				
		if(intendedArray[0][6]!=null){
			var fiberResult = arrayFibers.filter(function (el){
 							 return el[4] ==intendedArray[0][6];
							});
							
							var fiberSrcOrigin=fiberResult[0][6];
							var fiberDesOrigin=fiberResult[0][9];
							
							if(fiberDesOrigin.charAt(0)=="M"){
								var manArray = arrayManhole.filter(function (el){
 							 return el[0] ==fiberDesOrigin;
							});
							manholeOrigin.push(manArray[0]);
					
							}else if(fiberDesOrigin.charAt(0)=="H"){
								var hanArray = arrayHandhole.filter(function (el){
 							 return el[0] ==fiberDesOrigin;
							});
							handholeOrigin.push(hanArray[0]);					
					}
							
							if(fiberSrcOrigin.charAt(0)=="M"){
								var manArray = arrayManhole.filter(function (el){
 							 return el[0] ==fiberSrcOrigin;
							});
							manholeOrigin.push(manArray[0]);
					
							}else if(fiberSrcOrigin.charAt(0)=="H"){
								var hanArray = arrayHandhole.filter(function (el){
 							 return el[0] ==fiberSrcOrigin;
							});
							handholeOrigin.push(hanArray[0]);					
					}
							filterResult.push(fiberResult);
		}
		
			
						filterResult.push(handholeOrigin);
						filterResult.push(manholeOrigin);
						filterResult.push(dbResult);
						}
						
					appendConnectedStrandTable(filterResult,"db");
		                     } 
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });

			return filterResult;
		}else{
			
			 filterResult=[];
			appendConnectedStrandTable(filterResult,"db");
			return filterResult;
			
		}		
	}
	
*/
function autoCompleteSearchHeader(ID,searchTarget,longitude,latitude){
	var url ="emptyURL";
	var dataTarget="";
	
	$("#"+ID).autocomplete({
		source: function(request, response) {
		var target = $("#"+searchTarget).val();
		var searchkey = $("#"+ID).val();
		
		if(target=="site"){
			url='GetAllWarehouse';
			dataTarget = {
	       		"WareName":searchkey,
				"warehouseName" : searchkey,
				"SiteId":searchkey,
			 }
		}
		else if(target=="client"){
			url='GetAllNetworkClients';
			dataTarget = {					
					"search":searchkey,
				}
		}
		else {
			url ="emptyURL";
		}
		if (url !="emptyURL") {
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
					this.value = (ui.item ? ui.item[0]+':'+ui.item[1]+':'+ui.item[2] : '');
					$("#"+longitude).val(ui.item[3]);
					$("#"+latitude).val(ui.item[4]);	
				//	console.log("long: "+$("#"+longitude).val()+" lat: "+$("#"+latitude).val());
  	
					return false;
					}
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {		
				if(item[0].split("_")[0]=="WARE") {
					 return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                   item[0] + "</span><br><span class='desc'>" +
		                    item[1] +', '+ item[2] + "</span></div>")
		                .appendTo(ul);
				}
				else if (item[0].split("_")[0]=="CLT") {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                   item[0] + "</span><br><span class='desc'>" +
	                    item[1] + ' ' + item[2] + ', ' + item[3] +"</span></div>")
	                .appendTo(ul);						
				}
			};
			
		$("#"+ID).focus(function(){
	        	if (this.value == ""){
	            	$(this).autocomplete("search");
	        	}						
		});
		
	}

function projectAutocomplete(){
						
	/////////////Start autocpmplete for project ID
	$("#ManholeProjectId").autocomplete({
		showHeader: true,
        source: function(request, response) {
                 var projectId = "";
                 projectId = $("#ManholeProjectId").val();
             
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllProjectID',
	                 data: {
							"ProjectId" : projectId,
							
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListProjectId);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 40, scroll:true,		
               
        
		select: function(event, ui) {
			console.log("add hereeee");
               document.getElementById("ManholeProjectId").value = ui.item[0];
               document.getElementById("ManholeProjectName").value = ui.item[1];
          
				return false;
					}
		    }).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] +  "</span></div>") .appendTo(ul);
        	};
			$("#ManholeProjectId").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});

/////////////start autocpmplete for project ID Handhole

$("#HandholeProjectId").autocomplete({
		showHeader: true,
        source: function(request, response) {
                 var projectId = "";
                 projectId = $("#HandholeProjectId").val();
       
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllProjectID',
	                 data: {
							"ProjectId" : projectId,
							
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {

	                         response(data.ListProjectId);
	                       
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 40, scroll:true,		
               
        
		select: function(event, ui) {

			document.getElementById("HandholeProjectId").value = ui.item[0];
            document.getElementById("HandholeProjectName").value = ui.item[1];

				return false;
					}
		    }).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] +  "</span></div>") .appendTo(ul);
        	};
			$("#HandholeProjectId").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});

/////////////end autocpmplete for project ID Handhole

/////////////start autocpmplete for project ID Handhole

$("#DBProjectId").autocomplete({
		showHeader: true,
        source: function(request, response) {
                 var projectId = "";
                 projectId = $("#DBProjectId").val();
               
             
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllProjectID',
	                 data: {
							"ProjectId" : projectId,
							
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListProjectId);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 40, scroll:true,		
               
        
		select: function(event, ui) {
			  	document.getElementById("DBProjectId").value = ui.item[0];
            	document.getElementById("DBProjectName").value = ui.item[1];
				return false;
					}
		    }).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] +  "</span></div>") .appendTo(ul);
        	};
			$("#DBProjectId").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});
	
}




function FindFiberData(selectedFiberContext){
	$('#fiberPathModal').find('input:text').val('');
	$('#fiberPathModal').find('input:file').val('');
	$('#fiberPathModal').find('input:checkbox').prop("checked",false);

	uncheckAutoCompleteCheckboxes("fiberTubeAutocomplete");
	uncheckAutoCompleteCheckboxes("srcDestCableAutoComplete");
	uncheckAutoCompleteCheckboxes("fiberStrandAutocomplete");
		

	$.ajax({
		type: "GET",
		async: false,
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/findFiberDetails',
		data: {
				 
			  "selectedFiberContext":selectedFiberContext 
		},
		dataType: "json",
		success: function (data) {
			$("#tubesTable > tbody").empty();
			$("#strandsTable > tbody").empty();										
			$("#auxiliaryTable > tbody").empty();
			$("#relatedCableJctTable > tbody").empty();
								
			allAuxDictTube=[];
			allAuxDictStrand=[];	
			numberOfTubes=0;    
			numberOfStrands=0;
			
			fiberCableAuxData=[];
			fiberCableAuxData=data.fiberAuxData;
			
			$("#FiberPathId").val(selectedFiberContext);
			
			if(data.fiberDetails[0][0] !="null"){
				src =data.fiberDetails[0][0]+":" +data.fiberDetails[0][2]+":"+data.fiberDetails[0][1];	
				$("#SourceType").val("Site");
			}
			else {
				if (data.fiberDetails[0][1].split("_")[0]=="MH") {
					src  = data.fiberDetails[0][1]+":" +data.fiberDetails[0][2];
					$("#SourceType").val("Manhole");
				}
				else if (data.fiberDetails[0][1].split("_")[0]=="HH") {
					src  = data.fiberDetails[0][1]+":" +data.fiberDetails[0][2];
					$("#SourceType").val("Handhole");
				}
				else if (data.fiberDetails[0][1].split("_")[0]=="DB") {
					src  = data.fiberDetails[0][1]+":" +data.fiberDetails[0][2];	
					$("#SourceType").val("Distribution Board");
				}
				else if (data.fiberDetails[0][1].split("_")[0]=="CLT") {
					src  = data.fiberDetails[0][1]+":" +data.fiberDetails[0][2];
					$("#SourceType").val("Client");
				}
				else {
					src = data.fiberDetails[0][2];
					$("#SourceType").val("Unregistered Node");
				}
			}
			
			if(data.fiberDetails[0][3] !="null"){
				dst =data.fiberDetails[0][3]+":" +data.fiberDetails[0][5]+":"+data.fiberDetails[0][4];
				$("#DestinationType").val("Site");
				
			}
			else {
				if (data.fiberDetails[0][4].split("_")[0]=="MH") {
					dst = data.fiberDetails[0][4]+":" +data.fiberDetails[0][5];	
					$("#DestinationType").val("Manhole");
				}
				else if (data.fiberDetails[0][4].split("_")[0]=="HH" ) {
					dst = data.fiberDetails[0][4]+":" +data.fiberDetails[0][5];	
					$("#DestinationType").val("Handhole");
				}
				else if (data.fiberDetails[0][4].split("_")[0]=="DB" ) {
					dst = data.fiberDetails[0][4]+":" +data.fiberDetails[0][5];	
					$("#DestinationType").val("Distribution Board")
				}
				else if (data.fiberDetails[0][4].split("_")[0]=="CLT") {
					dst = data.fiberDetails[0][4]+":" +data.fiberDetails[0][5];
					$("#DestinationType").val("Client")
				}
				else {
					dst = data.fiberDetails[0][5];
					$("#DestinationType").val("Unregistered Node");
				}
			}
			
			$("#Source").val(src);
			$("#srcCity").val(data.fiberDetails[0][18]);
			$("#Destination").val(dst);
			$("#dstCity").val(data.fiberDetails[0][19]);			
			$("#fibertype").val(data.fiberDetails[0][20]);
			$("#fiberdeployment").val(data.fiberDetails[0][21]);
			$("#fibernetlevel").val(data.fiberDetails[0][22]);
			$("#fiberowner").val(data.fiberDetails[0][23]);
			$("#crtdByFiberCable").val(data.fiberDetails[0][24]);
			$("#modifiedByFiberCable").val(data.fiberDetails[0][25]);
			$("#popupCreateDateFiber").val(data.fiberDetails[0][26]);
			$("#popupLastModifiedDateFiber").val(data.fiberDetails[0][27]);
			
			if(data.fiberDetails[0][17]!=null){
				$("#fiberName").val(data.fiberDetails[0][17]);
				$("#fiberHeader").text("FIBER Cable: "+data.fiberDetails[0][17]);
			}											

			if(data.fiberDetails[0][6]!=null){
				$("#ItemCodeId").val(data.fiberDetails[0][6]);
			}
			if(data.fiberDetails[0][7]!=null){
				$("#NumStrands").val(data.fiberDetails[0][7]);
				numberOfStrands=data.fiberDetails[0][7];
			}
			if(data.fiberDetails[0][8]!=null){
				$("#NumTubes").val(data.fiberDetails[0][8]);
				numberOfTubes=data.fiberDetails[0][8];
				}
			if(data.fiberDetails[0][9]!=null){
				$("#FiberLength").val((data.fiberDetails[0][9]).toFixed(3));
			}
			if(data.fiberDetails[0][28]!=null){
			    $("#totalDistanceDrivg").val((data.fiberDetails[0][28]).toFixed(3)); 
			    $("#FiberDrivDist").val((data.fiberDetails[0][28]).toFixed(3));
			}
			if(data.fiberDetails[0][29]!=null){
			    $("#totalGeoDistance").val((data.fiberDetails[0][29]).toFixed(3));
			   // document.querySelector("#totalGeoDistance").innerHTML = data.fiberDetails[0][29].toFixed(3);
			}
			if(data.fiberDetails[0][10]!=null){
				$("#Condiut_Id").val(data.fiberDetails[0][10]);
			}
			if(data.fiberDetails[0][11]!=null){
				$("#Condiut_Name").val(data.fiberDetails[0][11]);
			}
			if(data.fiberDetails[0][30]=="DRIVING"){
				$("#drawingBy").val("DRIVING");
			} else {
				$("#drawingBy").val("LINEOFSITE");
			}
			
			if(data.fiberDetails[0][16]=="Single Mode"){
				$("#SingleMode").prop("checked", true);
			}
			
			if(data.fiberDetails[0][16]=="Multimode"){
				$("#Multimode").prop("checked", true);
			}
			
			if(data.fiberDetails[0][31]!=null){
			    $("#distanceLstAuxToDest").val((data.fiberDetails[0][31]).toFixed(3));
			}
			if(data.fiberDetails[0][32]!=null){
			    $("#distanceLstAuxToDestDrivg").val((data.fiberDetails[0][32]).toFixed(3));
			}
			else {
			    $("#distanceLstAuxToDestDrivg").val(0);
	
			}	
			$("#SourceLng").val(data.fiberDetails[0][12]);
			$("#SourceLat").val(data.fiberDetails[0][13]);
			$("#DestinationLng").val(data.fiberDetails[0][14]);
			$("#DestinationLat").val(data.fiberDetails[0][15]);
			$("#Source").unbind();
			$("#Destination").unbind();
			$("#ItemCodeId").unbind();
			
			//related cable
			var netLevel=data.fiberDetails[0][22];
			relatedCableTabEvents(netLevel);
			autoCompleteforRelatedCable();
			if(data.fiberDetails[0][22]=="access"){
				//var netLevel=data.fiberDetails[0][22];
				//showHideRelatedTab(netLevel);
				//autoCompleteforRelatedCable();
				$("#relatedCableStrandNb").val(data.fiberDetails[0][33]);
				//$("#relatedCableStrandColor").val(data.fiberDetails[0][34]);
				$("#relatedCableStrandID").val(data.fiberDetails[0][35]);
				$("#relatedCableStrandName").val(data.fiberDetails[0][36]);
				$("#relatedCableTubeNb").val(data.fiberDetails[0][37]);
				//$("#relatedCableTubeColor").val(data.fiberDetails[0][38]);
				$("#relatedCableTubeID").val(data.fiberDetails[0][39]);
				$("#relatedCableTubeName").val(data.fiberDetails[0][40]);
				$("#relatedCableID").val(data.fiberDetails[0][41]);
				$("#relatedCableName").val(data.fiberDetails[0][42]);
				$("#otherLastMileID").val(data.fiberDetails[0][43]);
				$("#otherLastMileName").val(data.fiberDetails[0][44]);
				$("#relatedCableLocationID").val(data.fiberDetails[0][45]);
				$("#relatedCableLocationName").val(data.fiberDetails[0][46]);
				$("#relatedCableCity").val(data.fiberDetails[0][47]);
				$("#relatedCableLocationType").val(data.fiberDetails[0][48]);
				strandTubeSetColor(data.fiberDetails[0][33],"relatedCableStrandColor");
				strandTubeSetColor(data.fiberDetails[0][37],"relatedCableTubeColor");
				//console.log("data realted "+data.fiberDetails[0][45].split("_")[0]);
				if(data.fiberDetails[0][45]!=null){ 
					if(data.fiberDetails[0][45].split("_")[0] =="CLT"){
						$(".relatedLocationName").hide();
						$(".relatedLocationID").hide();
						$("#relatedLocationCity").hide();
						$("#relatedClientID").show();
						$("#relatedClientName").show();
						$("#relatedClientPhoneNb").show();
						
					}
				
					else if(data.fiberDetails[0][45].split("_")[0] =="MH"){
						$(".relatedLocationName").hide();
						$(".relatedLocationID").hide();
						$("#relatedClientPhoneNb").hide();
						$("#relatedManholeName").show();
						$("#relatedManholeID").show();
						$("#relatedLocationCity").show();
					}
					else if(data.fiberDetails[0][45].split("_")[0] =="HH"){
						$(".relatedLocationName").hide();
						$(".relatedLocationID").hide();
						$("#relatedClientPhoneNb").hide();
						$("#relatedHandholeName").show();
						$("#relatedHandholeID").show();
						$("#relatedLocationCity").show();
					}
					else if(data.fiberDetails[0][45].split("_")[0] =="DB"){
						$(".relatedLocationName").hide();
						$(".relatedLocationID").hide();
						$("#relatedClientPhoneNb").hide();
						$("#relatedDistBoardName").show();
						$("#relatedDistBoardID").show();
						$("#relatedLocationCity").show();
					}
					
					else {
						$(".relatedLocationName").hide();
						$(".relatedLocationID").hide();
						$("#relatedClientPhoneNb").hide();
						$("#relatedSiteName").show();
						$("#relatedSiteID").show();
						$("#relatedLocationCity").show();
					}
				}
				
					//access junction
					if(data.accessJunctions){
						junBoqIndex=0;
						for(i=0;i<data.accessJunctions.length;i++){
							
							 
							var markup = "<tr id='"+data.accessJunctions[i][0]+"'><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
										+"<td name='LastMileJunctionID'><input name='lastMileJunctionID' value='"+data.accessJunctions[i][1]+"' id='LastMileJunctionID"+junBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
										+"<td name='LastMileJunctionName'><input name='lastMileJunctionName' value='"+data.accessJunctions[i][2]+"' id='LastMileJunctionName"+junBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td></tr>"
							$("#relatedCableJctTable > tbody").append(markup);
							autocompleteforAccessJunctions(junBoqIndex);
							junBoqIndex++;
						}
					
					
					}
			}
			
			//to be deleted 
			//calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
			SourceDestinationAutoComplete("srcDestCableAutoComplete","SourceType","Source","SourceLng","SourceLat","srcCity","DestinationLat","DestinationLng","auxiliaryTable","source");
			SourceDestinationAutoComplete("srcDestCableAutoComplete","DestinationType","Destination","DestinationLng","DestinationLat","dstCity","SourceLat","SourceLng","auxiliaryTable","destination");
			
			if(data.fiberTubes!=null){
				for(i=0;i<data.fiberTubes.length;i++){
					tubeId=data.fiberTubes[i][0];
					tubeName=data.fiberTubes[i][12];
					
					if(data.fiberTubes[i][6] !="null"){
						tubeSrc = data.fiberTubes[i][6]+":" +data.fiberTubes[i][8]+":"+data.fiberTubes[i][7];		
					}
					else {
					 if (data.fiberTubes[i][7].split("_")[0]=="MH" || data.fiberTubes[i][7].split("_")[0]=="HH" ||data.fiberTubes[i][7].split("_")[0]=="DB" || data.fiberTubes[i][7].split("_")[0]=="CLT") {
						 tubeSrc  = data.fiberTubes[i][7]+":" +data.fiberTubes[i][8];	
						}
						else {
							tubeSrc = data.fiberTubes[i][8];
						}
					}
					
					if(data.fiberTubes[i][9] !="null"){
						tubeDst = data.fiberTubes[i][9]+":" +data.fiberTubes[i][11]+":"+data.fiberTubes[i][10];		
					}
					else {
						if (data.fiberTubes[i][10].split("_")[0]=="MH" || data.fiberTubes[i][10].split("_")[0]=="HH" || data.fiberTubes[i][10].split("_")[0]=="DB" || data.fiberTubes[i][10].split("_")[0]=="CLT") {
							tubeDst  = data.fiberTubes[i][10]+":" +data.fiberTubes[i][11];	
						}
						else {
							tubeDst = data.fiberTubes[i][11];
						}
					}
					
					tube_BoqAppendMarkup(tubeId,tubeSrc,data.fiberTubes[i][2],data.fiberTubes[i][3],tubeDst,data.fiberTubes[i][4],data.fiberTubes[i][5],data.fiberTubes[i][12],data.fiberTubes[i][15],data.fiberTubes[i][16],data.fiberTubes[i][17],data.fiberTubes[i][18],data.fiberTubes[i][13],data.fiberTubes[i][14],data.fiberTubes[i][19],data.fiberTubes[i][20],data.fiberTubes[i][21],data.fiberTubes[i][22],data.fiberTubes[i][23],data.fiberTubes[i][24],data.fiberTubes[i][25]);
					
					//Used in tube autocomplete (in strand tab)
					tubeListFromBoq.push(tubeId);													
						
				$("#auxRefTube"+indexTube).click(function(){
					var relTubeId=$(this).parent().parent().children('td[name="tubeId"]').children('input').val();
					var thisID = $(this).attr("id");
					indexTubeForAuxs = parseInt(thisID.substr(thisID.length-1));
					idTubeForAuxs =relTubeId;
					var fiberTubeDrawType = $(this).parent().parent().children('td[name="drawingTypeFiberTube"]').children('select').val();
					var fiberTubetotalGeoDist = $(this).parent().parent().children('td[name="fiberTubeTotalGeoDistance"]').children('input').val();
					var fiberTubetotalDrivDist = $(this).parent().parent().children('td[name="fiberTubeTotalDrivDistance"]').children('input').val();
					var fiberTubetotalDistLstAuxToDest = $(this).parent().parent().children('td[name="fiberTubeDistLstAuxToDest"]').children('input').val();
					var fiberTubeDrivDistLstAuxToDest = $(this).parent().parent().children('td[name="fiberTubeDrivDistLstAuxToDest"]').children('input').val();
					var totalLength = $(this).parent().parent().children('td[name="tubeLength"]').children('input').val();

					
					if(typeof window["Auxpts_Tubes"+relTubeId] !='undefined' ){
						indexTubeAux = 0;
						$("#TubeAuxTable > tbody").empty();							
						TubeStrandAuxAppendBOQ(window["Auxpts_Tubes"+relTubeId],"",TargetFiberTube,indexTubeAux);
						
						if(fiberTubeDrawType =="DRIVING"){
							$("#fiberTubeDrawingBy").val("DRIVING");
						} 
						else {
							$("#fiberTubeDrawingBy").val("LINEOFSITE");
						}
						$("#totalGeoDistanceFiberTube").val(parseFloat(fiberTubetotalGeoDist).toFixed(3));
						$("#fiberTubeTotalDistanceDrivg").val(parseFloat(fiberTubetotalDrivDist).toFixed(3));
						$("#distanceLstAuxToDestFiberTube").val(parseFloat(fiberTubetotalDistLstAuxToDest).toFixed(3));
						$("#fiberTubeDistanceLstAuxToDestDrivg").val(parseFloat(fiberTubeDrivDistLstAuxToDest).toFixed(3));
						$("#totalDistanceFiberTube").val(parseFloat(totalLength).toFixed(3));
					}							
					showTubeStrandAuxiliaryPopup("tubeModalAuxiliary",relTubeId,"tubeIdHeader","FiberTubeAuxAutoComplete");
					calculateDistanceAuxTube($("#tubeSource_Lat"+indexTubeForAuxs).val(),$("#tubeSource_Long"+indexTubeForAuxs).val(),$("#tubeDestination_Lat"+indexTubeForAuxs).val(),$("#tubeDestination_Long"+indexTubeForAuxs).val(),"TubeAuxTable",tubeId,indexTubeForAuxs);

					checkedPoints =[];	  
			// Hide/Show the marker on check/uncheck the checkbox
			$("#TubeAuxTable input[name='record']").on('change',function(e){	
					
				//if only one checkbox is checked , hide all points 
				if($(".rowAboveBelowFiberTube:checked").length !=0 && (window['tubeCheckPoints_'+relTubeId] == "checked" || window['tubeCheckRealPoints_'+relTubeId] == "checked" ) ) {
			
				showHideAllPoints(relTubeId,"tubeCheckSequence","Hide");

				 window['tubeCheckPoints_'+relTubeId] = "unchecked";
				 window['tubeCheckRealPoints_'+relTubeId] = "unchecked";
				 window['tubeCheckSequence_'+relTubeId] = "unchecked";
				
				 //Remove the unchecked tube points from array 
				 var index = allTubesShowPoint.indexOf(relTubeId);
				 if (index > -1) {
					 allTubesShowPoint.splice(index, 1);
				}
						
				//Remove the unchecked tube points from array 
				var index = allTubesShowRealPoint.indexOf(relTubeId);
				if (index > -1) {
					allTubesShowRealPoint.splice(index, 1);
				} 
				//Hide auxiliary points
				//showHideTubeStrandAuxPoints(window["Auxpts_Tubes"+relTubeId],"","","","","",relTubeId,"tubeCheckSequence","Hide","Auxiliary");
			}	
				
					// Show the point and add label to it
					if($(this).is(":checked")) {
						
						checkedPoints.push({ lat:$(this).parent().parent().children('td[name="TubeAuxiliary_Latitude"]').children('input').val(),lng:$(this).parent().parent().children('td[name="TubeAuxiliary_Longitude"]').children('input').val()});				  
						
						if($(".rowAboveBelowFiberTube:checked").length ==1) {
							map.setZoom(15);
							panTo($(this).parent().parent().children('td[name="TubeAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="TubeAuxiliary_Longitude"]').children('input').val());
						}
						else{
							
							checkedPoints.sort((a, b)=> (a.lat - b.lat) && (a.lng - b.lng))
							
							window["checkedPoints_"+relTubeId] = new google.maps.LatLngBounds();			
							var myLatLng = new google.maps.LatLng(checkedPoints[0].lat,checkedPoints[0].lng);
							window["checkedPoints_"+relTubeId].extend(myLatLng);
							myLatLng = new google.maps.LatLng(checkedPoints[checkedPoints.length-1].lat,checkedPoints[checkedPoints.length-1].lng);
							window["checkedPoints_"+relTubeId].extend(myLatLng);
							map.fitBounds(window["checkedPoints_"+relTubeId]);
						}			
					
					checkLabel="checked";
					
					if($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val()=="null"){
						var AuxId = "null".concat("_"+$(this).parent().parent().children('td[name="fiberTubeAuxSeq"]').children('input').val()+"_"+relTubeId);
						createSiteCltMarker(AuxId,AuxId,$(this).parent().parent().children('td[name="TubeAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="TubeAuxiliary_Longitude"]').children('input').val(),siteCltSrcMarkers);						
						siteCltSrcMarkers[AuxId].setLabel({text: $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val(), className:"marker-position-ware",color:"green"});								
					}		
					else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().includes("Auxiliary_Point")==true){
						var wareID=$(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
						createSiteCltMarker(wareID,wareID,$(this).parent().parent().children('td[name="TubeAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="TubeAuxiliary_Longitude"]').children('input').val(),siteCltSrcMarkers);						
						siteCltSrcMarkers[wareID].setLabel({text: $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-ware",color:"green"});

					}				
					else if($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="WARE"){
						var wareID=$(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
						createSiteCltMarker(wareID,$(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val(),$(this).parent().parent().children('td[name="TubeAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="TubeAuxiliary_Longitude"]').children('input').val(),siteCltSrcMarkers);						
						siteCltSrcMarkers[wareID].setLabel({text: $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0]+" : "+ $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[2], className:"marker-position-ware",color:"red"});

					}
					else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="MH"){
						var ManId = $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
						if(markersManhole[ManId]){
							markerClusterManhole.removeMarker(markersManhole[ManId]);	
							markersManhole[ManId].setMap(map);
							markerClusterManhole.addMarker(markersManhole[ManId]);	
							$("#"+ManId).children(':checkbox').prop( "checked", true );
						    $("#manholeCheckAllBoq").prop( "checked", true );
						    markersManhole[ManId].setLabel({text: $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-manhole",color:"red"});
						}
					}
					else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="HH"){
						var HandId = $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
						if(markersHandhole[HandId]){
							markerClusterHandhole.removeMarker(markersHandhole[HandId]);
							markersHandhole[HandId].setMap(map);
							markerClusterHandhole.addMarker(markersHandhole[HandId]);
							$("#"+HandId).children(':checkbox').prop( "checked", true );
							$("#handholeCheckAllBoq").prop( "checked", true );
							markersHandhole[HandId].setLabel({text: $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
						}										
					}			
					else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="DB") {
						var Id = $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
						if(markersDistBoard[Id]){	
							markerClusterDistBoard.removeMarker(markersDistBoard[Id]);
							markersDistBoard[Id].setMap(map);					
							markerClusterDistBoard.addMarker(markersDistBoard[Id]);
							$("#"+Id).children(':checkbox').prop( "checked", true );
							$("#distBoardCheckAllBoq").prop( "checked", true );
							markersDistBoard[Id].setLabel({text: $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-dB",color:"#5665F9"});
						}
					}			
				}	
					//Aux point is unchecked (Hide the points from map)
					else {
						
						if($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val()=="null"){
							var AuxId = "null".concat("_"+$(this).parent().parent().children('td[name="fiberTubeAuxSeq"]').children('input').val()+"_"+relTubeId);
							siteCltSrcMarkers[AuxId].setMap(null);	
							siteCltSrcMarkers[AuxId].setLabel(null);
						}						
						else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().includes("Auxiliary_Point")==true){
							var wareID=$(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
							siteCltSrcMarkers[wareID].setMap(null);
							siteCltSrcMarkers[wareID].setLabel(null);
						}								
						else if($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="WARE" ){
							var wareID=$(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
							siteCltSrcMarkers[wareID].setMap(null);
							siteCltSrcMarkers[wareID].setLabel(null);
						}		
						else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="MH"){
							var ManId = $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
							markersManhole[ManId].setMap(null);
							markerClusterManhole.removeMarker(markersManhole[ManId]);	
							$("#"+ManId).children(':checkbox').prop( "checked", false );
							markersManhole[ManId].setLabel(null);
						}		
						else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="HH"){
							var HandId = $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
							markersHandhole[HandId].setMap(null);
							markerClusterHandhole.removeMarker(markersHandhole[HandId]);
							$("#"+HandId).children(':checkbox').prop( "checked", false );
							markersHandhole[HandId].setLabel(null);
						}						
						else if ($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split("_")[0]=="DB"){
							var Id = $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val().split(":")[0];
							markersDistBoard[Id].setMap(null);					
							markerClusterDistBoard.removeMarker(markersDistBoard[Id]);
							$("#"+Id).children(':checkbox').prop( "checked", false );
							markersDistBoard[Id].setLabel(null);
						}
					}						
					if( $("#Manhole_f_CurrentPhysicalLayer").find(".Manhole:checked" ).length ==0){
						$("#manholeCheckAllBoq").prop("checked",false);
					}
					else{
						$("#manholeCheckAllBoq").prop("checked",true);
					}
					
					if( $("#Handhole_f_CurrentPhysicalLayer").find(".Handhole:checked" ).length ==0){
						$("#handholeCheckAllBoq").prop("checked",false);
					}
					else{
						$("#handholeCheckAllBoq").prop("checked",true);
					}
					if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked" ).length ==0){
						$("#distBoardCheckAllBoq").prop("checked",false);
					}
					else{
						$("#distBoardCheckAllBoq").prop("checked",true);
					}						
					
				});				
		});							
				var td = calculateDistanceAuxTube(data.fiberTubes[i][3],data.fiberTubes[i][2],data.fiberTubes[i][5],data.fiberTubes[i][4],"tubesTable",tubeId,indexTube);
				var totalDistanceValue = (parseFloat(td)+parseFloat($("#FiberLength").val())).toFixed(3);
				$("#tube_length"+indexTube).val(parseFloat(td).toFixed(3));
				$("#tube_total_length"+indexTube).val(totalDistanceValue);
				/*$("#tube_total_length"+indexTube).hover(function(){
						$(this).tooltip();	
				});
				*/
					indexTube++;
				}
			}
				
			if(data.fiberStrands!=null){
				for(i=0;i<data.fiberStrands.length;i++){
					strandId=data.fiberStrands[i][0];		
					if(data.fiberStrands[0][7] !="null"){
						src =data.fiberStrands[0][7]+":" +data.fiberStrands[0][9]+":"+data.fiberStrands[0][8];	
					}
					else {
						if (data.fiberStrands[0][8].split("_")[0]=="MH") {
							src  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];
						}
						else if (data.fiberStrands[0][8].split("_")[0]=="HH") {
							src  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];
						}
						else if (data.fiberStrands[0][8].split("_")[0]=="DB") {
							src  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];	
						}
						else if (data.fiberStrands[0][8].split("_")[0]=="CLT") {
							src  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];
						}
						else {
							src = data.fiberStrands[0][9];
						}
					}
					
					if(data.fiberStrands[0][10] !="null"){
						dst =data.fiberStrands[0][10]+":" +data.fiberStrands[0][12]+":"+data.fiberStrands[0][11];	
					}
					else {
						if (data.fiberStrands[0][11].split("_")[0]=="MH") {
							dst  = data.fiberStrands[0][11]+":" +data.fiberStrands[0][12];
						}
						else if (data.fiberStrands[0][11].split("_")[0]=="HH") {
							dst  = data.fiberStrands[0][11]+":" +data.fiberStrands[0][12];
						}
						else if (data.fiberStrands[0][11].split("_")[0]=="DB") {
							dst  = data.fiberStrands[0][11]+":" +data.fiberStrands[0][12];	
						}
						else if (data.fiberStrands[0][11].split("_")[0]=="CLT") {
							dst  = data.fiberStrands[0][11]+":" +data.fiberStrands[0][12];
						}
						else {
							dst = data.fiberStrands[0][12];
						}
					}
					strand_BoqAppendMarkup(strandId,data.fiberStrands[i][6],src,data.fiberStrands[i][2],data.fiberStrands[i][3],dst,data.fiberStrands[i][4],data.fiberStrands[i][5],data.fiberStrands[i][13],data.fiberStrands[i][16],data.fiberStrands[i][17],data.fiberStrands[i][18],data.fiberStrands[i][19],data.fiberStrands[i][14],data.fiberStrands[i][15],data.fiberStrands[i][20],data.fiberStrands[i][21],data.fiberStrands[i][22],data.fiberStrands[i][23],data.fiberStrands[i][24],data.fiberStrands[i][25],data.fiberStrands[i][26]);
					
		
		$("#auxRefStrand"+indexStrand).click(function(){
			var relStrandId=$(this).parent().parent().children('td[name="Strand_ID"]').children('input').val();
			var thisID = $(this).attr("id");
			indexStrandForAuxs = parseInt(thisID.substr(thisID.length-1));
			idStrandForAuxs =relStrandId;
			
			var fiberStrandDrawType = $(this).parent().parent().children('td[name="drawingTypeFiberStrand"]').children('select').val();
			var fiberStrandtotalGeoDist = $(this).parent().parent().children('td[name="fiberStrandTotalGeoDistance"]').children('input').val();
			var fiberStrandtotalDrivDist = $(this).parent().parent().children('td[name="fiberStrandTotalDrivDistance"]').children('input').val();
			var fiberStrandtotalDistLstAuxToDest = $(this).parent().parent().children('td[name="fiberStrandDistLstAuxToDest"]').children('input').val();
			var fiberStrandDrivDistLstAuxToDest = $(this).parent().parent().children('td[name="fiberStrandDrivDistLstAuxToDest"]').children('input').val();
			var totalLength = $(this).parent().parent().children('td[name="strandLength"]').children('input').val();

			if(typeof window["Auxpts_Strands"+relStrandId] !='undefined' ){
				indexStrandAux = 0;
				$("#StrandAuxTable > tbody").empty();
				StrandRefTube=[$(this).parent().parent().children('td[name="Tube_ID"]').children('input').val(),$(this).parent().parent().children('td[name="strandSource_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="strandSource_Longitude"]').children('input').val()];
				TubeStrandAuxAppendBOQ(window["Auxpts_Strands"+relStrandId],"",TargetFiberStrand,indexStrandAux);
				
				if(fiberStrandDrawType =="DRIVING"){
					$("#fiberStrandDrawingBy").val("DRIVING");
				} 
				else {
					$("#fiberStrandDrawingBy").val("LINEOFSITE");
				}
				$("#totalGeoDistanceFiberStrand").val(parseFloat(fiberStrandtotalGeoDist).toFixed(3));
				$("#fiberStrandTotalDistanceDrivg").val(parseFloat(fiberStrandtotalDrivDist).toFixed(3));
				$("#distanceLstAuxToDestFiberStrand").val(parseFloat(fiberStrandtotalDistLstAuxToDest).toFixed(3));
				$("#fiberStrandDistanceLstAuxToDestDrivg").val(parseFloat(fiberStrandDrivDistLstAuxToDest).toFixed(3));
				$("#totalDistanceFiberStrand").val(parseFloat(totalLength).toFixed(3));
			}
			showTubeStrandAuxiliaryPopup("strandModalAuxiliary",relStrandId,"strandIdHeader","FiberStrandAuxAutoComplete");
			calculateDistanceAuxTube($("#strandSource_Lat"+indexStrandForAuxs).val(),$("#strandSource_Long"+indexStrandForAuxs).val(),$("#strandDestination_Lat"+indexStrandForAuxs).val(),$("#strandDestination_Long"+indexStrandForAuxs).val(),"StrandAuxTable",strandId,indexStrandForAuxs);

				
		checkedPoints =[];	  
		// Hide/Show the marker on check/uncheck the checkbox
		$("#StrandAuxTable input[name='record']").on('change',function(e){	
			
			//if only one checkbox is checked , hide all points 
			if($(".rowAboveBelowFiberStrand:checked").length !=0 && (window['strandCheckPoints_'+relStrandId] == "checked" || window['strandCheckRealPoints_'+relStrandId] == "checked" ) ) {
	
				showHideAllPoints(relStrandId,"strandCheckSequence","Hide");
			 window['strandCheckPoints_'+relStrandId] = "unchecked";
			 window['strandCheckRealPoints_'+relStrandId] = "unchecked";
			 window['strandCheckSequence_'+relStrandId] = "unchecked";
			
			 //Remove the unchecked tube points from array 
			 var index = allStrandsShowPoint.indexOf(relStrandId);
			 if (index > -1) {
				 allStrandsShowPoint.splice(index, 1);
			}
					
			//Remove the unchecked strand points from array 
			var index = allStrandsShowRealPoint.indexOf(relStrandId);
			if (index > -1) {
				allStrandsShowRealPoint.splice(index, 1);
			} 
		//Hide auxiliary points
		//showHideTubeStrandAuxPoints(window["Auxpts_Strands"+relStrandId],"","","","","",relStrandId,"strandCheckSequence","Hide","Auxiliary");			
	}	
			// Show the point and add label to it
			if($(this).is(":checked")) {
				
				checkedPoints.push({ lat:$(this).parent().parent().children('td[name="StrandAuxiliary_Latitude"]').children('input').val(),lng:$(this).parent().parent().children('td[name="StrandAuxiliary_Longitude"]').children('input').val()});				  
				
				if($(".rowAboveBelowFiberStrand:checked").length ==1) {
					map.setZoom(15);
					panTo($(this).parent().parent().children('td[name="StrandAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="StrandAuxiliary_Longitude"]').children('input').val());
				}
				else{
					
					checkedPoints.sort((a, b)=> (a.lat - b.lat) && (a.lng - b.lng))
					
					window["checkedPoints_"+relStrandId] = new google.maps.LatLngBounds();			
					var myLatLng = new google.maps.LatLng(checkedPoints[0].lat,checkedPoints[0].lng);
					window["checkedPoints_"+relStrandId].extend(myLatLng);
					myLatLng = new google.maps.LatLng(checkedPoints[checkedPoints.length-1].lat,checkedPoints[checkedPoints.length-1].lng);
					window["checkedPoints_"+relStrandId].extend(myLatLng);
					map.fitBounds(window["checkedPoints_"+relStrandId]);
				}			

			checkLabel="checked";

			if($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val()=="null"){
				var AuxId = "null".concat("_"+$(this).parent().parent().children('td[name="fiberStrandAuxSeq"]').children('input').val()+"_"+relStrandId);
				createSiteCltMarker(AuxId,AuxId,$(this).parent().parent().children('td[name="StrandAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="StrandAuxiliary_Longitude"]').children('input').val(),siteCltSrcMarkers);						
				siteCltSrcMarkers[AuxId].setLabel({text: $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val(), className:"marker-position-ware",color:"green"});								
			}		
			else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().includes("Auxiliary_Point")==true){
				var wareID=$(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
				createSiteCltMarker(wareID,wareID,$(this).parent().parent().children('td[name="StrandAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="StrandAuxiliary_Longitude"]').children('input').val(),siteCltSrcMarkers);						
				siteCltSrcMarkers[wareID].setLabel({text: $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-ware",color:"green"});

			}				
			else if($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="WARE"){
				var wareID=$(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
				createSiteCltMarker(wareID,$(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val(),$(this).parent().parent().children('td[name="StrandAuxiliary_Latitude"]').children('input').val(),$(this).parent().parent().children('td[name="StrandAuxiliary_Longitude"]').children('input').val(),siteCltSrcMarkers);						
				siteCltSrcMarkers[wareID].setLabel({text: $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0]+" : "+ $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[2], className:"marker-position-ware",color:"red"});

			}
			else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="MH"){
				var ManId = $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
				if(markersManhole[ManId]){
					markerClusterManhole.removeMarker(markersManhole[ManId]);	
					markersManhole[ManId].setMap(map);
					markerClusterManhole.addMarker(markersManhole[ManId]);	
					$("#"+ManId).children(':checkbox').prop( "checked", true );
				    $("#manholeCheckAllBoq").prop( "checked", true );
				    markersManhole[ManId].setLabel({text: $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-manhole",color:"red"});
				}
			}
			else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="HH"){
				var HandId = $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
				if(markersHandhole[HandId]){
					markerClusterHandhole.removeMarker(markersHandhole[HandId]);
					markersHandhole[HandId].setMap(map);
					markerClusterHandhole.addMarker(markersHandhole[HandId]);
					$("#"+HandId).children(':checkbox').prop( "checked", true );
					$("#handholeCheckAllBoq").prop( "checked", true );
					markersHandhole[HandId].setLabel({text: $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
				}										
			}			
			else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="DB") {
				var Id = $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
				if(markersDistBoard[Id]){	
					markerClusterDistBoard.removeMarker(markersDistBoard[Id]);
					markersDistBoard[Id].setMap(map);					
					markerClusterDistBoard.addMarker(markersDistBoard[Id]);
					$("#"+Id).children(':checkbox').prop( "checked", true );
					$("#distBoardCheckAllBoq").prop( "checked", true );
					markersDistBoard[Id].setLabel({text: $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[1], className:"marker-position-dB",color:"#5665F9"});
				}
			}			
			}	
			//Aux point is unchecked (Hide the points from map)
			else {
				
				if($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val()=="null"){
					var AuxId = "null".concat("_"+$(this).parent().parent().children('td[name="fiberStrandAuxSeq"]').children('input').val()+"_"+relStrandId);
					siteCltSrcMarkers[AuxId].setMap(null);	
					siteCltSrcMarkers[AuxId].setLabel(null);
				}						
				else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().includes("Auxiliary_Point")==true){
					var wareID=$(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
					siteCltSrcMarkers[wareID].setMap(null);
					siteCltSrcMarkers[wareID].setLabel(null);
				}								
				else if($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="WARE" ){
					var wareID=$(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
					siteCltSrcMarkers[wareID].setMap(null);
					siteCltSrcMarkers[wareID].setLabel(null);
				}		
				else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="MH"){
					var ManId = $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
					markersManhole[ManId].setMap(null);
					markerClusterManhole.removeMarker(markersManhole[ManId]);	
					$("#"+ManId).children(':checkbox').prop( "checked", false );
					markersManhole[ManId].setLabel(null);
				}		
				else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="HH"){
					var HandId = $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
					markersHandhole[HandId].setMap(null);
					markerClusterHandhole.removeMarker(markersHandhole[HandId]);
					$("#"+HandId).children(':checkbox').prop( "checked", false );
					markersHandhole[HandId].setLabel(null);
				}						
				else if ($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split("_")[0]=="DB"){
					var Id = $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val().split(":")[0];
					markersDistBoard[Id].setMap(null);					
					markerClusterDistBoard.removeMarker(markersDistBoard[Id]);
					$("#"+Id).children(':checkbox').prop( "checked", false );
					markersDistBoard[Id].setLabel(null);
				}
			}						
			if( $("#Manhole_f_CurrentPhysicalLayer").find(".Manhole:checked" ).length ==0){
				$("#manholeCheckAllBoq").prop("checked",false);
			}
			else{
				$("#manholeCheckAllBoq").prop("checked",true);
			}

			if( $("#Handhole_f_CurrentPhysicalLayer").find(".Handhole:checked" ).length ==0){
				$("#handholeCheckAllBoq").prop("checked",false);
			}
			else{
				$("#handholeCheckAllBoq").prop("checked",true);
			}
			if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked" ).length ==0){
				$("#distBoardCheckAllBoq").prop("checked",false);
			}
			else{
				$("#distBoardCheckAllBoq").prop("checked",true);
			}							
				
			});
		});
		var td = calculateDistanceAuxTube(data.fiberStrands[i][3],data.fiberStrands[i][2],data.fiberStrands[i][5],data.fiberStrands[i][4],"strandsTable",strandId,indexStrand);
		$("#strand_length"+indexStrand).val(td);						
		indexStrand++;
		}
	}	
				data = null;
		},
		error: function (result) {
			alert("Error");
		}
	}); 
	
						
}


function FindFiberStrand(selectedStrand){
	
	$('#StrandModal').find('input:text').val('');
	$('#StrandModal').find('select').val('');
	$('#StrandModal').find('input:file').val('');
	$('#StrandModal').find('input:checkbox').prop("checked",false);

	 uncheckAutoCompleteCheckboxes("srcDestStrandAutoComplete");		

	$.ajax({ 
		type: "GET",
		async: false,
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/findStrandDetails',
		data: {
			  "ID":selectedStrand 
		},
		dataType: "json",
		success: function (data) {
			strandAuxData=[];
			strandAuxData=data.auxData;
			
			$("#auxiliaryTableStrands > tbody").empty();

			$("#StrandID").val(selectedStrand);
			$("#StrandName").val(data.fiberStrands[0][1]);
			$("#fibercableStrand").val(data.fiberStrands[0][2]);
			$("#fibertubeStrand").val(data.fiberStrands[0][3]);
						
			if(data.fiberStrands[0][4] !="null"){
				src =data.fiberStrands[0][4]+":" +data.fiberStrands[0][6]+":"+data.fiberStrands[0][5];	
				$("#SourceTypeStrand").val("Site");
			}
			else {
				if (data.fiberStrands[0][5].split("_")[0]=="MH") {
					src  = data.fiberStrands[0][5]+":" +data.fiberStrands[0][6];
					$("#SourceTypeStrand").val("Manhole");
				}
				else if (data.fiberStrands[0][5].split("_")[0]=="HH") {
					src  = data.fiberStrands[0][5]+":" +data.fiberStrands[0][6];
					$("#SourceTypeStrand").val("Handhole");
				}
				else if (data.fiberStrands[0][5].split("_")[0]=="DB") {
					src  = data.fiberStrands[0][5]+":" +data.fiberStrands[0][6];	
					$("#SourceTypeStrand").val("Distribution Board");
				}
				else if (data.fiberStrands[0][5].split("_")[0]=="CLT") {
					src  = data.fiberStrands[0][5]+":" +data.fiberStrands[0][6];
					$("#SourceTypeStrand").val("Client");
				}
				else {
					src = data.fiberStrands[0][6];
					$("#SourceTypeStrand").val("Unregistered Node");
				}
			}
			$("#sourcestrand").val(""+src);

			if(data.fiberStrands[0][7] !="null"){
				dst =data.fiberStrands[0][7]+":" +data.fiberStrands[0][9]+":"+data.fiberStrands[0][8];	
				$("#DestinationTypeStrand").val("Site");
			}
			else {
				if (data.fiberStrands[0][8].split("_")[0]=="MH") {
					dst  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];
					$("#DestinationTypeStrand").val("Manhole");
				}
				else if (data.fiberStrands[0][8].split("_")[0]=="HH") {
					dst  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];
					$("#DestinationTypeStrand").val("Handhole");
				}
				else if (data.fiberStrands[0][8].split("_")[0]=="DB") {
					dst  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];	
					$("#DestinationTypeStrand").val("Distribution Board");
				}
				else if (data.fiberStrands[0][8].split("_")[0]=="CLT") {
					dst  = data.fiberStrands[0][8]+":" +data.fiberStrands[0][9];
					$("#DestinationTypeStrand").val("Client");
				}
				else {
					dst = data.fiberStrands[0][9];
					$("#DestinationTypeStrand").val("Unregistered Node");
				}
			}
			$("#destinationstrand").val(""+dst);

			
			$("#sourcelongstrand").val(data.fiberStrands[0][10]);
			$("#destinationlongstrand").val(data.fiberStrands[0][12]);
			$("#sourcelatstrand").val(data.fiberStrands[0][11]);
			$("#destinationlatstrand").val(data.fiberStrands[0][13]);
			$("#frmstrandtype").val(data.fiberStrands[0][16]);
			$("#frmstranddep").val(data.fiberStrands[0][17]);
			$("#frmstrandnetlevel").val(data.fiberStrands[0][18]);
			$("#frmstrandowner").val(data.fiberStrands[0][19]);
			$("#crtdByFiberStrand").val(data.fiberStrands[0][20]);
			$("#modifiedByFiberStrand").val(data.fiberStrands[0][21]);
			$("#popupCreateDateStrand").val(data.fiberStrands[0][22]);
			$("#popupLastModifiedDateStrand").val(data.fiberStrands[0][23]);
			$("#srcCityStrand").val(data.fiberStrands[0][14]);
			$("#dstCityStrand").val(data.fiberStrands[0][15]);
			$("#strandNumber").val(data.fiberStrands[0][30]);
			$("#strandColor").val(data.fiberStrands[0][31]);
						
			if(data.fiberStrands[0][1]!=null){
				$("#StrandHeader").text("Strand: "+data.fiberStrands[0][1]);
			}
			
			if(data.fiberStrands[0][24]!=null){
			    $("#strandTotalDistanceDrivg").val((data.fiberStrands[0][24]).toFixed(3));
			    $("#strandDrivDist").val((data.fiberStrands[0][24]).toFixed(3));
			}
			if(data.fiberStrands[0][25]!=null){
			    $("#totalGeoDistanceStrand").val((data.fiberStrands[0][25]).toFixed(3));
			}
			
			if(data.fiberStrands[0][26]!=null){
				$("#StrandLength").val(""+data.fiberStrands[0][26].toFixed(5));
			}
			if(data.fiberStrands[0][27]=="DRIVING"){
				$("#strandDrawingBy").val("DRIVING");
			} 
			else {
				$("#strandDrawingBy").val("LINEOFSITE");
			}
			
			if(data.fiberStrands[0][28]!=null){
			    $("#distanceLstAuxToDestStrnd").val((data.fiberStrands[0][28]).toFixed(3));
			}
			if(data.fiberStrands[0][29]!=null){
			    $("#strandDistanceLstAuxToDestDrivg").val((data.fiberStrands[0][29]).toFixed(3));
			}
			else {
			    $("#strandDistanceLstAuxToDestDrivg").val(0);
			}
			
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
		},
		error: function (result) {
			alert("Error");
		}
	}); 
	
}

function showHideRealPoints(pathID,checkSeqWindowID,action) {
	showHidePointsArray=[];
	if(window["mapPointsNames_"+pathID] != undefined) {

		if( (filterFlag==2 || filterFlag==1) && showPointsType=="0") {	

			$('#Manhole_f_CurrentPhysicalLayer').find(' > ul > li ').each(function(){		
				var manHandDbName = $(this).text().trim();
				if(manHandDbName.includes("Junctions")) {
					manHandDbName=manHandDbName.split("Junctions")[0].replaceAll(' ', '');
				}
				allTreePoints.push($(this).attr('id')+":"+manHandDbName);
			});
			$('#Handhole_f_CurrentPhysicalLayer').find(' > ul > li ').each(function(){		
				var manHandDbName = $(this).text().trim();
				if(manHandDbName.includes("Junctions")) {
					manHandDbName=manHandDbName.split("Junctions")[0].replaceAll(' ', '');
				}
				allTreePoints.push($(this).attr('id')+":"+manHandDbName);
			});
			$('#DistributionBoard_f_CurrentPhysicalLayer').find(' > ul > li ').each(function(){		
				allTreePoints.push($(this).attr('id')+":"+$(this).text().trim());
			});
			
			window["mapPointsNamesTemp"]=[];
				for(var x=0;x<window["mapPointsNames_"+pathID].length;x++) {
					if(window["mapPointsNames_"+pathID][x].includes("MH_") || window["mapPointsNames_"+pathID][x].includes("HH_")
							|| window["mapPointsNames_"+pathID][x].includes("DB_")) {
						
						if(allTreePoints.includes(window["mapPointsNames_"+pathID][x])==true) {
							window["mapPointsNamesTemp"].push(window["mapPointsNames_"+pathID][x]);
						}
						else {
							window["mapPointsNamesTemp"].push("empty");
						}
					}
					else {
						window["mapPointsNamesTemp"].push(window["mapPointsNames_"+pathID][x]);
					}		
				}
				
				showHidePointsArray=window["mapPointsNamesTemp"];
				window["mapPointsNamesTemp"]=[];
				allTreePoints=[];		
		}
		else {
			showHidePointsArray = window["mapPointsNames_"+pathID];
		}
	if(action=="Show") {
		for(var x=0;x<showHidePointsArray.length;x++) {
			
			if (x==0) {
				var type="Source";
			}
			else if (x == showHidePointsArray.length-1) {
				var type ="Destination";
			}
			else {
				var type =String(x);
			}
			
			if(showHidePointsArray[x].startsWith("WARE_")==true) {
				var wareID = showHidePointsArray[x].split(":")[0];
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');
				
				createSiteCltMarker(wareID,showHidePointsArray[x],longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);
			
				//Show seq if site label is checked 
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {
						siteCltSrcMarkers[wareID].setLabel({text: type+" / "+wareID+" : "+showHidePointsArray[x].split(":")[1], className:"marker-position-site",color:"red"});
					}
					else {
							siteCltSrcMarkers[wareID].setLabel({text: type , className:"marker-position-sequence",color:"red"}); 
					}
				}
				//Show Seq is unchecked
				else {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {
						siteCltSrcMarkers[wareID].setLabel({text: wareID+" : "+showHidePointsArray[x].split(":")[1], className:"marker-position-site",color:"red"});
					}
					else {
						if(siteCltSrcMarkers[wareID].getLabel()!="undefined") {
							siteCltSrcMarkers[wareID].setLabel(null);
						}
					}
				}
			}
			else if(showHidePointsArray[x].startsWith("CLT_")==true) {
				var cltID = showHidePointsArray[x].split(":")[0];
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');
	
				createSiteCltMarker(cltID,showHidePointsArray[x],longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);
				
				//Show seq is checked 
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("clientsMapCheck_Labels")==true) {
						siteCltSrcMarkers[cltID].setLabel({text: type+" / "+showHidePointsArray[x] , className:"marker-position-site",color:"red"});
					}
					else {
						siteCltSrcMarkers[cltID].setLabel({text: type, className:"marker-position-sequence",color:"red"}); 
					}
				}
				//Show Seq is unchecked
				else {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("clientsMapCheck_Labels")==true) {
						siteCltSrcMarkers[cltID].setLabel({text: showHidePointsArray[x] , className:"marker-position-site",color:"red"});
					}
					else {
						if(siteCltSrcMarkers[cltID].getLabel()!="undefined") {
							siteCltSrcMarkers[cltID].setLabel(null);
						}
					}
				}
			}
			else if(showHidePointsArray[x].startsWith("N/A")==true) {
				var idNA = showHidePointsArray[x].concat("_"+type+"_"+pathID);
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');
				createSiteCltMarker(idNA,idNA,longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);	
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					siteCltSrcMarkers[idNA].setLabel({text: type , className:"marker-position-sequence",color:"red"}); 
				}
				else {
					if(siteCltSrcMarkers[idNA].getLabel()!="undefined") {
						siteCltSrcMarkers[idNA].setLabel(null);
					}
				}	
			}
			else if(showHidePointsArray[x].startsWith("MH_")==true) {
				var manID = showHidePointsArray[x].split(":")[0];
				if(markersManhole[manID]) {
					if(markersManhole[manID].getMap() ==null) {
						markerClusterManhole.removeMarker(markersManhole[manID]);	
						markersManhole[manID].setMap(map);
						markerClusterManhole.addMarker(markersManhole[manID]);	
						$("#"+manID).children(':checkbox').prop( "checked", true );
						$("#manholeCheckAllBoq").prop( "checked", true );
					 }		
					//Show seq is checked 
					if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {
							markersManhole[manID].setLabel({text: type +" / "+ showHidePointsArray[x].split(":")[1], className:"marker-position-manhole",color:"red"});
						}
						else {
							markersManhole[manID].setLabel({text: type , className:"marker-position-sequence",color:"red"}); 
						}
					}
					//Show Seq is unchecked
					else {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {
							markersManhole[manID].setLabel({text: showHidePointsArray[x].split(":")[1] , className:"marker-position-manhole",color:"red"});
						}
						else {
							if(markersManhole[manID].getLabel()!="undefined") {
								markersManhole[manID].setLabel(null);
							}
						}
					}				
				}			
			}
			else if(showHidePointsArray[x].startsWith("HH_")==true) {
				var handID = showHidePointsArray[x].split(":")[0];
				if(markersHandhole[handID]) {
					if(markersHandhole[handID].getMap() ==null) {
						markerClusterHandhole.removeMarker(markersHandhole[handID]);	
						markersHandhole[handID].setMap(map);
						markerClusterHandhole.addMarker(markersHandhole[handID]);
						$("#"+handID).children(':checkbox').prop( "checked", true );
						$("#handholeCheckAllBoq").prop( "checked", true );
					}		
					//Show Sequence is checked
					if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {
							markersHandhole[handID].setLabel({text: type +" / "+ showHidePointsArray[x].split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
						}
						else {
							markersHandhole[handID].setLabel({text: type , className:"marker-position-sequence",color:"#E5C523"}); 
						}
					}
					//Show Sequence is unchecked
					else {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {
							markersHandhole[handID].setLabel({text: showHidePointsArray[x].split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
						}
						else {
							if(markersHandhole[handID].getLabel()!="undefined") {
								markersHandhole[handID].setLabel(null);
							}
						}
					}
				}						
			}
			else if(showHidePointsArray[x].startsWith("DB_")==true) {
				var dbID = showHidePointsArray[x].split(":")[0];
				if(markersDistBoard[dbID]) {
					if(markersDistBoard[dbID].getMap() ==null) {
						markerClusterDistBoard.removeMarker(markersDistBoard[dbID]);	
						markersDistBoard[dbID].setMap(map);					
						markerClusterDistBoard.addMarker(markersDistBoard[dbID]);
						$("#"+dbID).children(':checkbox').prop( "checked", true );
						$("#distBoardCheckAllBoq").prop( "checked", true );
					}
					if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("dBMapCheck_Labels")==true) {
							markersDistBoard[dbID].setLabel({text: type+" / "+ showHidePointsArray[x].split(":")[1], className:"marker-position-dB",color:"#5665F9"});
						}
						else {
							markersDistBoard[dbID].setLabel({text: type , className:"marker-position-sequence",color:"#5665F9"}); 
						}
					}
					else {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("dBMapCheck_Labels")==true) {
							markersDistBoard[dbID].setLabel({text: showHidePointsArray[x].split(":")[1], className:"marker-position-dB",color:"#5665F9"});
						}
						else {
							if(markersDistBoard[dbID].getLabel()!="undefined") {
								markersDistBoard[dbID].setLabel(null);
							}
						}
					}			
				}
			}
			// Case of null auxiliary point
			else if(showHidePointsArray[x] ==null || showHidePointsArray[x] =="null"){
				var AuxId = "null".concat("_"+type+"_"+pathID);
				if(siteCltSrcMarkers[AuxId]) {
			    	siteCltSrcMarkers[AuxId].setMap(null);
			    }					
			}
			else if(showHidePointsArray[x].includes("Auxiliary_Point")){
				var auxID = showHidePointsArray[x].split(":")[0];
				if(siteCltSrcMarkers[auxID]) {
			    	siteCltSrcMarkers[auxID].setMap(null);
			    }
			}			
		}
	}

	//Action is hide points
	else {
		for(var x=0;x<showHidePointsArray.length;x++) {
			if (x==0) {
				var type="Source";
			}
			else if (x == showHidePointsArray.length-1) {
				var type ="Destination";
			}
			else {
				var type =String(x);
			}
			
			
			if(showHidePointsArray[x].startsWith("WARE_")==true) {
				var wareID = showHidePointsArray[x].split(":")[0];
				if(siteCltSrcMarkers[wareID]) {
				    siteCltSrcMarkers[wareID].setMap(null);
				}
			}
			else if(showHidePointsArray[x].startsWith("CLT_")==true) {
				var cltID = showHidePointsArray[x].split(":")[0];
				if(siteCltSrcMarkers[cltID]) {
			    	siteCltSrcMarkers[cltID].setMap(null);
			    }	
			}				
			else if(showHidePointsArray[x].startsWith("N/A")==true) {
				var idNA = showHidePointsArray[x].concat("_"+type+"_"+pathID);
				if(siteCltSrcMarkers[idNA]) {
			    	siteCltSrcMarkers[idNA].setMap(null);
			    }
			}
			else if(showHidePointsArray[x].startsWith("MH_")==true) {
				var manID = showHidePointsArray[x].split(":")[0];	
				if(markersManhole[manID]) {
					markersManhole[manID].setMap(null);				
					markerClusterManhole.removeMarker(markersManhole[manID]);	
					$("#"+manID).children(':checkbox').prop( "checked", false );	
				}
			}
			else if(showHidePointsArray[x].startsWith("HH_")==true) {
				var handID = showHidePointsArray[x].split(":")[0];	
				if(markersHandhole[handID]) {
					markersHandhole[handID].setMap(null);
					markerClusterHandhole.removeMarker(markersHandhole[handID]);
					$("#"+handID).children(':checkbox').prop( "checked", false );	
				}
				
			}
			else if(showHidePointsArray[x].startsWith("DB_")==true) {
				var dbID = showHidePointsArray[x].split(":")[0];
				if(markersDistBoard[dbID]) {
					markersDistBoard[dbID].setMap(null);					
					markerClusterDistBoard.removeMarker(markersDistBoard[dbID]);
					$("#"+dbID).children(':checkbox').prop( "checked", false );	
				}					
			}								
		}		
	}

	if( $("#Manhole_f_CurrentPhysicalLayer").find(".Manhole:checked" ).length ==0){
		$("#manholeCheckAllBoq").prop("checked",false);
	}
	else{
		$("#manholeCheckAllBoq").prop("checked",true);
	}
	
	if( $("#Handhole_f_CurrentPhysicalLayer").find(".Handhole:checked" ).length ==0){
		$("#handholeCheckAllBoq").prop("checked",false);
	}
	else{
		$("#handholeCheckAllBoq").prop("checked",true);
	}
	if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked" ).length ==0){
		$("#distBoardCheckAllBoq").prop("checked",false);
	}
	else{
		$("#distBoardCheckAllBoq").prop("checked",true);
	}	
	}
	
}
function showHideAllSequence(pathID,action) {
	 if(action=="Show") {
		 for(var x=0;x<window["mapPointsNames_"+pathID].length;x++) {				
			if (x==0) {
				var type="Source";
			}
			else if (x == window["mapPointsNames_"+pathID].length-1) {
				var type ="Destination";
			}
			else {
				var type =String(x);
			}
			console.log("The type is "+type);
			
			
			if(window["mapPointsNames_"+pathID][x].startsWith("WARE_")==true) {
				var wareID = window["mapPointsNames_"+pathID][x].split(":")[0];
				if(siteCltSrcMarkers[wareID]) {
					//if(siteCltSrcMarkers[wareID].getLabel()!="undefined" && siteCltSrcMarkers[wareID].getLabel()!=null) {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {	
						siteCltSrcMarkers[wareID].setLabel({text: type+" / "+wareID+":"+window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-site",color:"red"});
					}
					else {
						siteCltSrcMarkers[wareID].setLabel({text: type , className:"marker-position-sequence",color:"red"});
					}	
				}			
			}		 
			else if(window["mapPointsNames_"+pathID][x].startsWith("CLT_")==true) {
				var cltID = window["mapPointsNames_"+pathID][x].split(":")[0];	
			
				if(siteCltSrcMarkers[cltID]) {
					//if(siteCltSrcMarkers[wareID].getLabel()!="undefined" && siteCltSrcMarkers[wareID].getLabel()!=null) {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("clientsMapCheck_Labels")==true) {	
						siteCltSrcMarkers[cltID].setLabel({text: type+" / "+window["mapPointsNames_"+pathID][x], className:"marker-position-site",color:"red"});
					}
					else {
						siteCltSrcMarkers[cltID].setLabel({text: type , className:"marker-position-sequence",color:"red"});
					}	
				}	
			}		 
			else if(window["mapPointsNames_"+pathID][x].startsWith("N/A")==true) {
				var idNA = window["mapPointsNames_"+pathID][x].concat("_"+type+"_"+pathID);
				if(siteCltSrcMarkers[idNA]) {
					siteCltSrcMarkers[idNA].setLabel({text: type , className:"marker-position-sequence",color:"red"});										
				}
			}
			else if(window["mapPointsNames_"+pathID][x] ==null || window["mapPointsNames_"+pathID][x] =="null"){
				var AuxId = "null".concat("_"+type+"_"+pathID);
				if(siteCltSrcMarkers[AuxId]) {
					siteCltSrcMarkers[AuxId].setLabel({text: type , className:"marker-position-sequence",color:"green"});										
				}
			}
			else if(window["mapPointsNames_"+pathID][x].includes("Auxiliary_Point")){
				var auxID = window["mapPointsNames_"+pathID][x].split(":")[0];
				if(siteCltSrcMarkers[auxID]) {
					siteCltSrcMarkers[auxID].setLabel({text: type , className:"marker-position-sequence",color:"green"});	
				}			
			}			
			else if(window["mapPointsNames_"+pathID][x].startsWith("MH_")==true) {
				var manID = window["mapPointsNames_"+pathID][x].split(":")[0];
				if(markersManhole[manID]){
					//if(markersManhole[manID].getLabel()!=undefined && markersManhole[auxiliaryArray[A][4]].getLabel()!=null) {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {	
						markersManhole[manID].setLabel({text: type+" / "+ window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-manhole",color:"red"});
					}
					else {
						markersManhole[manID].setLabel({text: type, className:"marker-position-sequence",color:"red"});											
					}
				}
		 }	
		else if(window["mapPointsNames_"+pathID][x].startsWith("HH_")==true) {
				var handID = window["mapPointsNames_"+pathID][x].split(":")[0];
				if(markersHandhole[handID]){
					//if(markersManhole[manID].getLabel()!=undefined && markersManhole[auxiliaryArray[A][4]].getLabel()!=null) {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {	
						markersHandhole[handID].setLabel({text: type+" / "+ window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
					}
					else {
						markersHandhole[handID].setLabel({text: type, className:"marker-position-sequence",color:"#E5C523"});											
					}
				}
		 }	
		else if(window["mapPointsNames_"+pathID][x].startsWith("DB_")==true) {
			var dbID = window["mapPointsNames_"+pathID][x].split(":")[0];
			if(markersDistBoard[dbID]){
				if(allcheckedLabels.length >0 && allcheckedLabels.includes("dBMapCheck_Labels")==true) {	
					markersDistBoard[dbID].setLabel({text: type+" / "+ window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-dB",color:"#5665F9"});
				}
				else {
					markersDistBoard[dbID].setLabel({text: type, className:"marker-position-sequence",color:"#5665F9"});											
				}
			}
			}			 
		 }//end loop 
	 }
	 //Action is hide
	 else {
		 for(var x=0;x<window["mapPointsNames_"+pathID].length;x++) {				
				if (x==0) {
					var type="Source";
				}
				else if (x == window["mapPointsNames_"+pathID].length-1) {
					var type ="Destination";
				}
				else {
					var type =String(x);
				}				
				
				if(window["mapPointsNames_"+pathID][x].startsWith("WARE_")==true) {
					var wareID = window["mapPointsNames_"+pathID][x].split(":")[0];
					if(siteCltSrcMarkers[wareID]) {
						//if(siteCltSrcMarkers[wareID].getLabel()!="undefined" && siteCltSrcMarkers[wareID].getLabel()!=null) {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {	
							siteCltSrcMarkers[wareID].setLabel({text: wareID+":"+window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-site",color:"red"});
						}
						else {
							siteCltSrcMarkers[wareID].setLabel(null);
						}	
					}			
				}		 
				else if(window["mapPointsNames_"+pathID][x].startsWith("CLT_")==true) {
					var cltID = window["mapPointsNames_"+pathID][x].split(":")[0];	
				
					if(siteCltSrcMarkers[cltID]) {
						//if(siteCltSrcMarkers[wareID].getLabel()!="undefined" && siteCltSrcMarkers[wareID].getLabel()!=null) {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("clientsMapCheck_Labels")==true) {	
							siteCltSrcMarkers[cltID].setLabel({text: window["mapPointsNames_"+pathID][x], className:"marker-position-site",color:"red"});
						}
						else {
							siteCltSrcMarkers[cltID].setLabel(null);
						}	
					}	
				}		 
				else if(window["mapPointsNames_"+pathID][x].startsWith("N/A")==true) {
					var idNA = window["mapPointsNames_"+pathID][x].concat("_"+type+"_"+pathID);
					if(siteCltSrcMarkers[idNA]) {
						siteCltSrcMarkers[idNA].setLabel(null);										
					}
				}
				else if(window["mapPointsNames_"+pathID][x] ==null || window["mapPointsNames_"+pathID][x] =="null"){
					var AuxId = "null".concat("_"+type+"_"+pathID);
					if(siteCltSrcMarkers[AuxId]) {
						siteCltSrcMarkers[AuxId].setLabel(null);										
					}
				}
				else if(window["mapPointsNames_"+pathID][x].includes("Auxiliary_Point")){
					var auxID = window["mapPointsNames_"+pathID][x].split(":")[0];
					if(siteCltSrcMarkers[auxID]) {
						siteCltSrcMarkers[auxID].setLabel(null);	
					}			
				}			
				else if(window["mapPointsNames_"+pathID][x].startsWith("MH_")==true) {
					var manID = window["mapPointsNames_"+pathID][x].split(":")[0];
					if(markersManhole[manID]){
						//if(markersManhole[manID].getLabel()!=undefined && markersManhole[auxiliaryArray[A][4]].getLabel()!=null) {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {	
							markersManhole[manID].setLabel({text: window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-manhole",color:"red"});
						}
						else {
							markersManhole[manID].setLabel(null);											
						}
					}
			 }	
			else if(window["mapPointsNames_"+pathID][x].startsWith("HH_")==true) {
					var handID = window["mapPointsNames_"+pathID][x].split(":")[0];
					if(markersHandhole[handID]){
						//if(markersManhole[manID].getLabel()!=undefined && markersManhole[auxiliaryArray[A][4]].getLabel()!=null) {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {	
							markersHandhole[handID].setLabel({text: window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
						}
						else {
							markersHandhole[handID].setLabel(null);											
						}
					}
			 }	
			else if(window["mapPointsNames_"+pathID][x].startsWith("DB_")==true) {
				var dbID = window["mapPointsNames_"+pathID][x].split(":")[0];
				if(markersDistBoard[dbID]){
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("dBMapCheck_Labels")==true) {	
						markersDistBoard[dbID].setLabel({text: window["mapPointsNames_"+pathID][x].split(":")[1], className:"marker-position-dB",color:"#5665F9"});
					}
					else {
						markersDistBoard[dbID].setLabel(null);											
					}
				}
				}			 
			 }//end loop 		 	 
	 }
}




function showHideAllPoints(pathID,checkSeqWindowID,action) {
showHidePointsArray=[];
if(window["mapPointsNames_"+pathID] != undefined) {

	if( (filterFlag==2 || filterFlag==1) && showPointsType=="0") {	

		$('#Manhole_f_CurrentPhysicalLayer').find(' > ul > li ').each(function(){		
			var manHandDbName = $(this).text().trim();
			if(manHandDbName.includes("Junctions")) {
				manHandDbName=manHandDbName.split("Junctions")[0].replaceAll(' ', '');
			}
			allTreePoints.push($(this).attr('id')+":"+manHandDbName);
		});
		$('#Handhole_f_CurrentPhysicalLayer').find(' > ul > li ').each(function(){		
			var manHandDbName = $(this).text().trim();
			if(manHandDbName.includes("Junctions")) {
				manHandDbName=manHandDbName.split("Junctions")[0].replaceAll(' ', '');
			}
			allTreePoints.push($(this).attr('id')+":"+manHandDbName);
		});
		$('#DistributionBoard_f_CurrentPhysicalLayer').find(' > ul > li ').each(function(){		
			allTreePoints.push($(this).attr('id')+":"+$(this).text().trim());
		});
		
		window["mapPointsNamesTemp"]=[];
			for(var x=0;x<window["mapPointsNames_"+pathID].length;x++) {
				if(window["mapPointsNames_"+pathID][x].includes("MH_") || window["mapPointsNames_"+pathID][x].includes("HH_")
						|| window["mapPointsNames_"+pathID][x].includes("DB_")) {
					
					if(allTreePoints.includes(window["mapPointsNames_"+pathID][x])==true) {
						window["mapPointsNamesTemp"].push(window["mapPointsNames_"+pathID][x]);
					}
					else {
						window["mapPointsNamesTemp"].push("empty");
					}
				}
				else {
					window["mapPointsNamesTemp"].push(window["mapPointsNames_"+pathID][x]);
				}		
			}
			
			showHidePointsArray=window["mapPointsNamesTemp"];
			window["mapPointsNamesTemp"]=[];
			allTreePoints=[];		
	}
	else {
		showHidePointsArray = window["mapPointsNames_"+pathID];
	}
	
	if(action=="Show") {
		for(var x=0;x<showHidePointsArray.length;x++) {			
			if (x==0) {
				var type="Source";
			}
			else if (x == showHidePointsArray.length-1) {
				var type ="Destination";
			}
			else {
				var type =String(x);
			}
			
			if(showHidePointsArray[x].startsWith("WARE_")==true) {
				var wareID = showHidePointsArray[x].split(":")[0];
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');
				
				createSiteCltMarker(wareID,showHidePointsArray[x],longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);
			
				//Show seq if site label is checked 
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {
						siteCltSrcMarkers[wareID].setLabel({text: type+" / "+wareID+" : "+showHidePointsArray[x].split(":")[1], className:"marker-position-site",color:"red"});
					}
					else {
							siteCltSrcMarkers[wareID].setLabel({text: type , className:"marker-position-sequence",color:"red"}); 
					}
				}
				//Show Seq is unchecked
				else {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {
						siteCltSrcMarkers[wareID].setLabel({text: wareID+" : "+showHidePointsArray[x].split(":")[1], className:"marker-position-site",color:"red"});
					}
					else {
						if(siteCltSrcMarkers[wareID].getLabel()!="undefined") {
							siteCltSrcMarkers[wareID].setLabel(null);
						}
					}
				}
			}
			else if(showHidePointsArray[x].startsWith("CLT_")==true) {
				var cltID = showHidePointsArray[x].split(":")[0];
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');
	
				createSiteCltMarker(cltID,showHidePointsArray[x],longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);
				
				//Show seq is checked 
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("clientsMapCheck_Labels")==true) {
						siteCltSrcMarkers[cltID].setLabel({text: type+" / "+showHidePointsArray[x], className:"marker-position-site",color:"red"});
					}
					else {
						siteCltSrcMarkers[cltID].setLabel({text: type, className:"marker-position-sequence",color:"red"}); 
					}
				}
				//Show Seq is unchecked
				else {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("clientsMapCheck_Labels")==true) {
						siteCltSrcMarkers[cltID].setLabel({text: showHidePointsArray[x] , className:"marker-position-site",color:"red"});
					}
					else {
						if(siteCltSrcMarkers[cltID].getLabel()!="undefined") {
							siteCltSrcMarkers[cltID].setLabel(null);
						}
					}
				}
			}
			else if(showHidePointsArray[x].startsWith("N/A")==true) {
				var idNA = showHidePointsArray[x].concat("_"+type+"_"+pathID);
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');
				createSiteCltMarker(idNA,idNA,longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);	
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					siteCltSrcMarkers[idNA].setLabel({text: type , className:"marker-position-sequence",color:"red"}); 
				}
				else {
					if(siteCltSrcMarkers[idNA].getLabel()!="undefined") {
						siteCltSrcMarkers[idNA].setLabel(null);
					}
				}	
			}
			else if(showHidePointsArray[x].startsWith("MH_")==true) {
				var manID = showHidePointsArray[x].split(":")[0];
				if(markersManhole[manID]) {
					if(markersManhole[manID].getMap() ==null) {
						markerClusterManhole.removeMarker(markersManhole[manID]);	
						markersManhole[manID].setMap(map);
						markerClusterManhole.addMarker(markersManhole[manID]);	
						$("#"+manID).children(':checkbox').prop( "checked", true );
						$("#manholeCheckAllBoq").prop( "checked", true );
					 }	
					//Show seq is checked 
					if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {
							markersManhole[manID].setLabel({text: type +" / "+ showHidePointsArray[x].split(":")[1], className:"marker-position-manhole",color:"red"});
						}
						else {
							markersManhole[manID].setLabel({text: type , className:"marker-position-sequence",color:"red"}); 
						}
					}
					//Show Seq is unchecked
					else {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {
							markersManhole[manID].setLabel({text: showHidePointsArray[x].split(":")[1] , className:"marker-position-manhole",color:"red"});
						}
						else {
							//if(markersManhole[manID].getLabel()!="undefined") {
								markersManhole[manID].setLabel(null);
							//}
						}
					}				
				}			
			}
			else if(showHidePointsArray[x].startsWith("HH_")==true) {
				var handID = showHidePointsArray[x].split(":")[0];
				if(markersHandhole[handID]) {
					if(markersHandhole[handID].getMap() ==null) {
						markerClusterHandhole.removeMarker(markersHandhole[handID]);	
						markersHandhole[handID].setMap(map);
						markerClusterHandhole.addMarker(markersHandhole[handID]);
						$("#"+handID).children(':checkbox').prop( "checked", true );
						$("#handholeCheckAllBoq").prop( "checked", true );
					}		
					//Show Sequence is checked
					if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {
							markersHandhole[handID].setLabel({text: type +" / "+ showHidePointsArray[x].split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
						}
						else {
							markersHandhole[handID].setLabel({text: type , className:"marker-position-sequence",color:"#E5C523"}); 
						}
					}
					//Show Sequence is unchecked
					else {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {
							markersHandhole[handID].setLabel({text: showHidePointsArray[x].split(":")[1], className:"marker-position-handhole",color:"#E5C523"});
						}
						else {
							if(markersHandhole[handID].getLabel()!="undefined") {
								markersHandhole[handID].setLabel(null);
							}
						}
					}
				}						
			}
			else if(showHidePointsArray[x].startsWith("DB_")==true) {
				var dbID = showHidePointsArray[x].split(":")[0];
				if(markersDistBoard[dbID]) {	
					if(markersDistBoard[dbID].getMap() ==null) {
						markerClusterDistBoard.removeMarker(markersDistBoard[dbID]);	
						markersDistBoard[dbID].setMap(map);					
						markerClusterDistBoard.addMarker(markersDistBoard[dbID]);
						$("#"+dbID).children(':checkbox').prop( "checked", true );
						$("#distBoardCheckAllBoq").prop( "checked", true );
					}
					if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("dBMapCheck_Labels")==true) {
							markersDistBoard[dbID].setLabel({text: type+" / "+ showHidePointsArray[x].split(":")[1], className:"marker-position-dB",color:"#5665F9"});
						}
						else {
							markersDistBoard[dbID].setLabel({text: type , className:"marker-position-sequence",color:"#5665F9"}); 
						}
					}
					else {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("dBMapCheck_Labels")==true) {
							markersDistBoard[dbID].setLabel({text: showHidePointsArray[x].split(":")[1], className:"marker-position-dB",color:"#5665F9"});
						}
						else {
							if(markersDistBoard[dbID].getLabel()!="undefined") {
								markersDistBoard[dbID].setLabel(null);
							}
						}
					}			
				}
			}
			// Case of null auxiliary point
			else if(showHidePointsArray[x] ==null || showHidePointsArray[x] =="null"){
				var AuxId = "null".concat("_"+type+"_"+pathID);
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');
								
				createSiteCltMarker(AuxId,AuxId,longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					siteCltSrcMarkers[AuxId].setLabel({text: type , className:"marker-position-sequence",color:"green"}); 
				}
				else {
					if(siteCltSrcMarkers[AuxId].getLabel()!="undefined") {
						siteCltSrcMarkers[AuxId].setLabel(null);
					}
				}						
			}
			else if(showHidePointsArray[x].includes("Auxiliary_Point")){
				var auxID = showHidePointsArray[x].split(":")[0];
				var longLat = String(window["mapPoints_"+pathID][x]).replaceAll(/[( )]/g, '');

				createSiteCltMarker(auxID,auxID,longLat.split(",")[0],longLat.split(",")[1],siteCltSrcMarkers);
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					siteCltSrcMarkers[auxID].setLabel({text: type , className:"marker-position-sequence",color:"green"}); 
				}
				else {
					if(siteCltSrcMarkers[auxID].getLabel()!="undefined") {
						siteCltSrcMarkers[auxID].setLabel(null);
					}
				}
			}			
			
		}
	}
	//Action is hide points
	else {
		
		for(var x=0;x<showHidePointsArray.length;x++) {
			
			if (x==0) {
				var type="Source";
			}
			else if (x == showHidePointsArray.length-1) {
				var type ="Destination";
			}
			else {
				var type =String(x);
			}
			
			if(showHidePointsArray[x].startsWith("WARE_")==true) {
				var wareID = showHidePointsArray[x].split(":")[0];
				if(siteCltSrcMarkers[wareID]) {
			    	siteCltSrcMarkers[wareID].setMap(null);
			    }
			}
			else if(showHidePointsArray[x].startsWith("CLT_")==true) {
				var cltID = showHidePointsArray[x].split(":")[0];
				if(siteCltSrcMarkers[cltID]) {
			    	siteCltSrcMarkers[cltID].setMap(null);
			    }	
			}
			else if(showHidePointsArray[x].startsWith("N/A")==true) {
				var idNA = showHidePointsArray[x].concat("_"+type+"_"+pathID);
				if(siteCltSrcMarkers[idNA]) {
			    	siteCltSrcMarkers[idNA].setMap(null);
			    }
			}
			else if(showHidePointsArray[x].startsWith("MH_")==true) {
				var manID = showHidePointsArray[x].split(":")[0];	
				if(markersManhole[manID]) {
					markersManhole[manID].setMap(null);				
					markerClusterManhole.removeMarker(markersManhole[manID]);	
					$("#"+manID).children(':checkbox').prop( "checked", false );	
				}
			}
			else if(showHidePointsArray[x].startsWith("HH_")==true) {
				var handID = showHidePointsArray[x].split(":")[0];	
				if(markersHandhole[handID]) {
					markersHandhole[handID].setMap(null);
					markerClusterHandhole.removeMarker(markersHandhole[handID]);
					$("#"+handID).children(':checkbox').prop( "checked", false );	
				}
				
			}
			else if(showHidePointsArray[x].startsWith("DB_")==true) {
				var dbID = showHidePointsArray[x].split(":")[0];
				if(markersDistBoard[dbID]) {
					markersDistBoard[dbID].setMap(null);					
					markerClusterDistBoard.removeMarker(markersDistBoard[dbID]);
					$("#"+dbID).children(':checkbox').prop( "checked", false );	
				}					
			}
			else if(showHidePointsArray[x] ==null || showHidePointsArray[x] =="null"){
				var AuxId = "null".concat("_"+type+"_"+pathID);		
				if(siteCltSrcMarkers[AuxId]) {
					siteCltSrcMarkers[AuxId].setMap(null);
				}
			}
			else if(showHidePointsArray[x].includes("Auxiliary_Point")){
				var auxID = showHidePointsArray[x].split(":")[0];
				if(siteCltSrcMarkers[auxID]) {
					siteCltSrcMarkers[auxID].setMap(null);
				}
			}			
		}
	}
	showHidePointsArray=[];
}
}

function FindFiberTube(selectedTube){
	$('#TubeModal').find('input:text').val('');
	$('#TubeModal').find('input:file').val('');	 
	$('#TubeModal').find('input:checkbox').prop("checked",false);

	uncheckAutoCompleteCheckboxes("srcDestTubeAutoComplete");

	$.ajax({
		type: "GET",
		async: false,
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/findTubeDetails',
		data: {				 
			  "ID":selectedTube 
		},
		dataType: "json",
		success: function (data) {
											
			$("#auxiliaryTableTubes > tbody").empty();
			
			tubeAuxData=[];
			tubeAuxData=data.auxData;

			$("#TubeID").val(selectedTube);
			$("#TubeName").val(data.fiberTubes[0][1]);
			$("#fiberCable").val(data.fiberTubes[0][2]);
			
			if(data.fiberTubes[0][3] !="null"){
				src =data.fiberTubes[0][3]+":" +data.fiberTubes[0][5]+":"+data.fiberTubes[0][4];	
				$("#SourceTypeTube").val("Site");
			}
			else {
				if (data.fiberTubes[0][4].split("_")[0]=="MH") {
					src  = data.fiberTubes[0][4]+":" +data.fiberTubes[0][5];
					$("#SourceTypeTube").val("Manhole");
				}
				else if (data.fiberTubes[0][4].split("_")[0]=="HH") {
					src  = data.fiberTubes[0][4]+":" +data.fiberTubes[0][5];
					$("#SourceTypeTube").val("Handhole");
				}
				else if (data.fiberTubes[0][4].split("_")[0]=="DB") {
					src  = data.fiberTubes[0][4]+":" +data.fiberTubes[0][5];	
					$("#SourceTypeTube").val("Distribution Board");
				}
				else if (data.fiberTubes[0][4].split("_")[0]=="CLT") {
					src  = data.fiberTubes[0][4]+":" +data.fiberTubes[0][5];
					$("#SourceTypeTube").val("Client");
				}
				else {
					src = data.fiberTubes[0][5];
					$("#SourceTypeTube").val("Unregistered Node");
				}
			}
			$("#SourceTube").val(""+src);
			
			if(data.fiberTubes[0][6] !="null"){
				dst =data.fiberTubes[0][6]+":" +data.fiberTubes[0][8]+":"+data.fiberTubes[0][7];	
				$("#DestinationTypeTube").val("Site");
			}
			else {
				if (data.fiberTubes[0][7].split("_")[0]=="MH") {
					dst  = data.fiberTubes[0][7]+":" +data.fiberTubes[0][8];
					$("#DestinationTypeTube").val("Manhole");
				}
				else if (data.fiberTubes[0][7].split("_")[0]=="HH") {
					dst  = data.fiberTubes[0][7]+":" +data.fiberTubes[0][8];
					$("#DestinationTypeTube").val("Handhole");
				}
				else if (data.fiberTubes[0][7].split("_")[0]=="DB") {
					dst  = data.fiberTubes[0][7]+":" +data.fiberTubes[0][8];	
					$("#DestinationTypeTube").val("Distribution Board");
				}
				else if (data.fiberTubes[0][7].split("_")[0]=="CLT") {
					dst  = data.fiberTubes[0][7]+":" +data.fiberTubes[0][8];
					$("#DestinationTypeTube").val("Client");
				}
				else {
					dst = data.fiberTubes[0][8];
					$("#DestinationTypeTube").val("Unregistered Node");
				}
			}
			$("#DestinationTube").val(dst);
			
			$("#sourcelong").val(data.fiberTubes[0][9]);
			$("#destinationlong").val(data.fiberTubes[0][11]);
			$("#sourcelat").val(data.fiberTubes[0][10]);
			$("#destinationlat").val(data.fiberTubes[0][12]);
			$("#frmtubetype").val(data.fiberTubes[0][15]);
			$("#frmtubedep").val(data.fiberTubes[0][16]);
			$("#frmtubenetlevel").val(data.fiberTubes[0][17]);
			$("#frmtubeowner").val(data.fiberTubes[0][18]);
			$("#crtdByFiberTube").val(data.fiberTubes[0][19]);
			$("#modifiedByFiberTube").val(data.fiberTubes[0][20]);
			$("#popupCreateDateTube").val(data.fiberTubes[0][21]);
			$("#popupLastModifiedDateTube").val(data.fiberTubes[0][22]);
			$("#srcCityTube").val(data.fiberTubes[0][13]);
			$("#dstCityTube").val(data.fiberTubes[0][14]);
			$("#tubeNumber").val(data.fiberTubes[0][29]);
			$("#tubeColor").val(data.fiberTubes[0][30]);
			console.log("nbr tube " + data.fiberTubes[0][29]);
			console.log("clr tube " + data.fiberTubes[0][30]);
			
			if(data.fiberTubes[0][1]!=null){
				$("#TubeHeader").text("Tube: "+data.fiberTubes[0][1]);
			}	
			
			if(data.fiberTubes[0][23]!=null){
			    $("#tubeTotalDistanceDrivg").val((data.fiberTubes[0][23]).toFixed(3));
			    $("#tubeDrivDist").val((data.fiberTubes[0][23]).toFixed(3));
			}
			if(data.fiberTubes[0][24]!=null){
			    $("#totalGeoDistanceTube").val((data.fiberTubes[0][24]).toFixed(3));
			}
			if(data.fiberTubes[0][25]=="DRIVING"){
				$("#tubeDrawingBy").val("DRIVING");
			} 
			else {
				$("#tubeDrawingBy").val("LINEOFSITE");
			}
		
			if(data.fiberTubes[0][26]!=null){
			    $("#distanceLstAuxToDestTube").val((data.fiberTubes[0][26]).toFixed(3));
			}
			if(data.fiberTubes[0][27]!=null){
			    $("#tubeDistanceLstAuxToDestDrivg").val((data.fiberTubes[0][27]).toFixed(3));
			}
			else {
			    $("#tubeDistanceLstAuxToDestDrivg").val(0);
	
			}
			
			if(data.fiberTubes[0][28]!=null){
				$("#TubeLength").val(data.fiberTubes[0][28].toFixed(5));
			}
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
		},
		error: function (result) {
			alert("Error");
		}
	}); 	
}

function FindTrenchData(selectedTrenchContext){

	$('#trenchModal').find('input:text').val('');
	$('#trenchModal').find('input:file').val('');
	$('#trenchModal').find('input:checkbox').prop("checked",false);
	uncheckAutoCompleteCheckboxes("srcDestTrenchAutoComplete");

	$.ajax({
		type: "GET",
		async: false,
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/findTrenchDetails',
		data: {				 
			  "ID":selectedTrenchContext 
		},
		dataType: "json",
		success: function (data) {
											
			$("#auxiliary_trenchTable > tbody").empty();				
			
			$("#trenchId").val(data.listTrench[0][0]);
			$("#trenchName").val(data.listTrench[0][1]);
			
			if(data.listTrench[0][2] !="null"){
				src =data.listTrench[0][2]+":" +data.listTrench[0][4]+":"+data.listTrench[0][3];	
				$("#SourceTrenchType").val("Site");
			}
			else {
				if (data.listTrench[0][3].split("_")[0]=="MH") {
					src  = data.listTrench[0][3]+":" +data.listTrench[0][4];
					$("#SourceTrenchType").val("Manhole");
				}
				else if (data.listTrench[0][3].split("_")[0]=="HH") {
					src  = data.listTrench[0][3]+":" +data.listTrench[0][4];
					$("#SourceTrenchType").val("Handhole");
				}
				else if (data.listTrench[0][3].split("_")[0]=="DB") {
					src  = data.listTrench[0][3]+":" +data.listTrench[0][4];	
					$("#SourceTrenchType").val("Distribution Board");
				}
				else if (data.listTrench[0][3].split("_")[0]=="CLT") {
					src  = data.listTrench[0][3]+":" +data.listTrench[0][4];
					$("#SourceTrenchType").val("Client");
				}
				else {
					src = data.listTrench[0][4];
					$("#SourceTrenchType").val("Unregistered Node");
				}
			}
			$("#SourceTrench").val(src);
			
			if(data.listTrench[0][5] !="null"){
				dst =data.listTrench[0][5]+":" +data.listTrench[0][7]+":"+data.listTrench[0][6];	
				$("#DestinationTrenchType").val("Site");
			}
			else {
				if (data.listTrench[0][6].split("_")[0]=="MH") {
					dst  = data.listTrench[0][6]+":" +data.listTrench[0][7];
					$("#DestinationTrenchType").val("Manhole");
				}
				else if (data.listTrench[0][6].split("_")[0]=="HH") {
					dst  = data.listTrench[0][6]+":" +data.listTrench[0][7];
					$("#DestinationTrenchType").val("Handhole");
				}
				else if (data.listTrench[0][6].split("_")[0]=="DB") {
					dst  = data.listTrench[0][6]+":" +data.listTrench[0][7];	
					$("#DestinationTrenchType").val("Distribution Board");
				}
				else if (data.listTrench[0][6].split("_")[0]=="CLT") {
					dst  = data.listTrench[0][6]+":" +data.listTrench[0][7];
					$("#DestinationTrenchType").val("Client");
				}
				else {
					dst = data.listTrench[0][7];
					$("#DestinationTrenchType").val("Unregistered Node");
				}
			}
			$("#DestinationTrench").val(dst);
			
			$("#SourceTrenchLng").val(data.listTrench[0][8]);
			$("#SourceTrenchLat").val(data.listTrench[0][9]);
			$("#DestinationTrenchLng").val(data.listTrench[0][10]);
			$("#DestinationTrenchLat").val(data.listTrench[0][11]);
			$("#srcCityTrench").val(data.listTrench[0][12]);
			$("#dstCityTrench").val(data.listTrench[0][13]);
			$("#trenchCapacity").val(data.listTrench[0][14]);
			$("#NumDucts").val(data.listTrench[0][15]);
			$("#trenchLength").val(data.listTrench[0][16]);
			$("#popupCreateDateTrench").val(data.listTrench[0][17]);
			$("#popupLastModifiedDateTrench").val(data.listTrench[0][18]);
			$("#crtdByTrench").val(data.listTrench[0][19]);
			$("#modifiedByTrench").val(data.listTrench[0][20]);
			
			if(data.listTrench[0][21]!=null){
			    $("#trenchTotalDistanceDrivg").val((data.listTrench[0][21]).toFixed(3));
			    $("#trenchDrivDist").val((data.listTrench[0][21]).toFixed(3));
			}
			if(data.listTrench[0][22]!=null){
			    $("#totalGeoDistanceTrench").val((data.listTrench[0][22]).toFixed(3));
			}
			
			if(data.listTrench[0][23]!=null){
			    $("#distanceLstAuxToDestTrench").val((data.listTrench[0][23]).toFixed(3));
			}
			if(data.listTrench[0][24]!=null){
			    $("#trenchDistanceLstAuxToDestDrivg").val((data.listTrench[0][24]).toFixed(3));
			}
			else {
			    $("#trenchDistanceLstAuxToDestDrivg").val(0);
	
			}
			
			if(data.listTrench[0][25]=="DRIVING"){
				$("#trenchDrawingBy").val("DRIVING");
			} 
			else {
				$("#trenchDrawingBy").val("LINEOFSITE");
			}
			$("#trenchHeader").text("Trench: "+data.listTrench[0][1]);
			
			
			trenchAuxData=[];
			trenchAuxData=data.auxData;

		},
		error: function (result) {
			alert("Error");
		}
	}); 			
}


function FindDuctData(selectedDuctContext){

	$('#ductModal').find('input:text').val('');
	$('#ductModal').find('input:file').val('');
	$('#ductModal').find('input:checkbox').prop("checked",false);
	
	$.ajax({
		type: "GET",
		async: false,
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/findDuctDetails',
		data: {
			  "ID":selectedDuctContext 
		},
		dataType: "json",
		success: function (data) {
											
			$("#auxiliary_ductTable > tbody").empty();

			$("#ductId").val(selectedDuctContext);
			$("#ductName").val(data.listDuct[0][1]);
			 $("#ductTrenchId").val(selectedTrenchContext);

			if(data.listDuct[0][2] !="null"){
				src =data.listDuct[0][2]+":" +data.listDuct[0][4]+":"+data.listDuct[0][3];	
				$("#SourceDuctType").val("Site");
			}
			else {
				if (data.listDuct[0][3].split("_")[0]=="MH") {
					src  = data.listDuct[0][3]+":" +data.listDuct[0][4];
					$("#SourceDuctType").val("Manhole");
				}
				else if (data.listDuct[0][3].split("_")[0]=="HH") {
					src  = data.listDuct[0][3]+":" +data.listDuct[0][4];
					$("#SourceDuctType").val("Handhole");
				}
				else if (data.listDuct[0][3].split("_")[0]=="DB") {
					src  = data.listDuct[0][3]+":" +data.listDuct[0][4];	
					$("#SourceDuctType").val("Distribution Board");
				}
				else if (data.listDuct[0][3].split("_")[0]=="CLT") {
					src  = data.listDuct[0][3]+":" +data.listDuct[0][4];
					$("#SourceDuctType").val("Client");
				}
				else {
					src = data.listDuct[0][4];
					$("#SourceDuctType").val("Unregistered Node");
				}
			}
			$("#SourceDuct").val(src);
			
			if(data.listDuct[0][5] !="null"){
				dst =data.listDuct[0][5]+":" +data.listDuct[0][7]+":"+data.listDuct[0][6];	
				$("#DestinationDuctType").val("Site");
			}
			else {
				if (data.listDuct[0][6].split("_")[0]=="MH") {
					dst  = data.listDuct[0][6]+":" +data.listDuct[0][7];
					$("#DestinationDuctType").val("Manhole");
				}
				else if (data.listDuct[0][6].split("_")[0]=="HH") {
					dst  = data.listDuct[0][6]+":" +data.listDuct[0][7];
					$("#DestinationDuctType").val("Handhole");
				}
				else if (data.listDuct[0][6].split("_")[0]=="DB") {
					dst  = data.listDuct[0][6]+":" +data.listDuct[0][7];	
					$("#DestinationDuctType").val("Distribution Board");
				}
				else if (data.listDuct[0][6].split("_")[0]=="CLT") {
					dst  = data.listDuct[0][6]+":" +data.listDuct[0][7];
					$("#DestinationDuctType").val("Client");
				}
				else {
					dst = data.listDuct[0][7];
					$("#DestinationDuctType").val("Unregistered Node");
				}
			}
			$("#DestinationDuct").val(dst);			
			$("#SourceDuctLng").val(data.listDuct[0][8]);
			$("#SourceDuctLat").val(data.listDuct[0][9]);
			$("#DestinationDuctLng").val(data.listDuct[0][10]);
			$("#DestinationDuctLat").val(data.listDuct[0][11]);
			$("#srcCityDuct").val(data.listDuct[0][12]);
			$("#dstCityDuct").val(data.listDuct[0][13]);
			$("#ductsFiberCables").val(data.listDuct[0][14]);
			$("#ductsTubes").val(data.listDuct[0][15]);
			$("#ductsStrands").val(data.listDuct[0][16]);
			$("#ductsLength").val(data.listDuct[0][17]);
			$("#popupCreateDateDuct").val(data.listDuct[0][18]);
			$("#popupLastModifiedDateDuct").val(data.listDuct[0][19]);
			$("#crtdByDuct").val(data.listDuct[0][20]);
			$("#modifiedByDuct").val(data.listDuct[0][21]);
			
			if(data.listDuct[0][22]!=null){
			    $("#ductTotalDistanceDrivg").val((data.listDuct[0][22]).toFixed(3));
			    $("#ductDrivDist").val((data.listDuct[0][22]).toFixed(3));
			}
			if(data.listDuct[0][23]!=null){
			    $("#totalGeoDistanceDuct").val((data.listDuct[0][23]).toFixed(3));
			}
			
			if(data.listDuct[0][24]!=null){
			    $("#distanceLstAuxToDestDuct").val((data.listDuct[0][24]).toFixed(3));
			}
			if(data.listDuct[0][25]!=null){
			    $("#ductDistanceLstAuxToDestDrivg").val((data.listDuct[0][25]).toFixed(3));
			}
			else {
			    $("#ductDistanceLstAuxToDestDrivg").val(0);
	
			}
			if(data.listDuct[0][26]=="DRIVING"){
				$("#ductDrawingBy").val("DRIVING");
			} 
			else {
				$("#ductDrawingBy").val("LINEOFSITE");
			}
			if(data.listDuct[0][1]!=null){
				$("#ductHeader").text("Duct: "+data.listDuct[0][1]);
			}	
		},
		error: function (result) {
			alert("Error");
		}
	}); 			
}

// Delete functions for fiber cable,Manhole,Handhole and DB

allSelectedLayer=[];
function deletePhysicalLayers(layer,subLayer,selectedIdContext){
	//console.log("yessssssss selectedIdContext is "+selectedIdContext);
	//console.log("yessssssss IdNodeSelectedTemp is "+IdNodeSelectedTemp);
	allSelectedLayer=[];
	checkIfLayerDel="";
	
if (subLayer==""){
    	
    	// Get manhole/handole or DB using id from context menu
    		allSelectedLayer.push(selectedIdContext);
    		// projet id
    		 if (layer=="Project"){
           	  var allSelectedLayerProject = allSelectedLayer[0].split("Project_span_")[1];
             	  allSelectedLayer=[];
             	  allSelectedLayer.push(allSelectedLayerProject);           	
    		}
    	
    	}
    	
    	// Delete from main Manhole/Handhole OR DB 
    	else{
    	
    		//Main physical layer checkbox is checked(ALL Single physical layer INSIDE IT ARE CHECKED)
	    	if($("."+layer+":checked").length>0){				
	    		//Get all checked single manholes/handoles or DB
				$("#"+subLayer+"_f_"+selectedIdContext+"").find(' > ul > li ').each(function(){			
		    		console.log("yessssssss ");	//all is selected from check box		
					allSelectedLayer.push($(this).attr('id'));
							
				}); 
	    		
	    	}
	    	
	    	//Main physical layer checkbox is unchecked (SOME checked/unchecked manholes/handholes/DB inside it )
	    	else {
	    		//Delete CHECKED single physical layer from main physical Layer right click context menu
	    			console.log(" 123456789");
	    			//Get all checked single manholes/handoles or DB
	    			$("#"+subLayer+"_f_"+selectedIdContext+"").find(' > ul > li ').each(function(){	
						if($(this).find('input:checkbox').is(':checked')) {		
							allSelectedLayer.push($(this).attr('id'));
							console.log("main folder");//when delete from  main folder a group of selected 
						}
		    		});
	    		 
	    	
	    	}// end unchecked main checkbox
    	}// end else delete from main physical layer
	
	// project physicall layer all delete manhole/handhole/DB
	allManholeProject = [];
    allHandholeProject = [];
    allDBProject = [];
    allFiberPathProject = [];
    FibertubeProject = [];
    FiberStrandProject = [];
    allTrenchPathProject = [];
    TrenchDuctProject = [];
    if (layer=="Project"){
    	
    	$("#Manhole_f_"+allSelectedLayer+"").find(' > ul > li ').each(function(){			
    		//console.log("yessssssss ");			
    		allManholeProject.push($(this).attr('id'));
					
		}); 
		//console.log("deleteeeeezzz allManholeProject "+allManholeProject);
		$("#Handhole_f_"+allSelectedLayer+"").find(' > ul > li ').each(function(){			
    		//console.log("yessssssss ");			
    		allHandholeProject.push($(this).attr('id'));
					
		}); 
		//console.log("deleteeeeezzz allHandholeProject "+allHandholeProject);
		$("#DistributionBoard_f_"+allSelectedLayer+"").find(' > ul > li ').each(function(){			
    		//console.log("yessssssss ");			
    		allDBProject.push($(this).attr('id'));
					
		}); 
		//console.log("deleteeeeezzz allDBProject "+allDBProject);
		$("#FiberPath_f_"+allSelectedLayer+"").find(' > ul > li ').each(function(){			
    		//console.log("yessssssss ");			
    		allFiberPathProject.push($(this).attr('id'));
    		for(p=0;p<allFiberPathProject.length;p++){
    			
    			$("#"+allFiberPathProject[p]).find(' > ul > li >ul >li').each(function(){
    				FibertubeProject.push($(this).attr('id'));	
    		    });
    	        $("#"+allFiberPathProject[p]).find(' > ul > li >ul >li >ul >li >ul >li ').each(function(){			
    	        	//var strandId=$(this).attr('id');
    			    FiberStrandProject.push($(this).attr('id'));
    	        });
    		}
					
		}); 
		//console.log("deleteeeeezzz allFiberPathProject "+allFiberPathProject);
		$("#Trench_f_"+allSelectedLayer+"").find(' > ul > li ').each(function(){			
    		console.log("yessssssss ");			
    		allTrenchPathProject.push($(this).attr('id'));
    		for(T=0;T<allTrenchPathProject.length;T++){
    			$("#"+allTrenchPathProject[T]).find(' > ul > li >ul >li').each(function(){
    				TrenchDuctProject.push($(this).attr('id'));	
    		    });
    		}
					
		}); 
		//console.log("deleteeeeezzz allTrenchPathProject "+allTrenchPathProject);
    }
		
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/deletePhysicalLayer',
				data: {
				"physicalLayerID":allSelectedLayer,
				"physicalLayer":layer,
				"NodeId":IdNodeSelectedTemp,
				},
				dataType: "json",
				success: function (data) {
				
				checkIfLayerDel="True";
				
				if(layer=="Manhole" || layer =="AllManholes" ) {	
					
					//markerClusterManhole.clearMarkers();
				 
					//Remove the deleted Manhole from tree
					for(var k=0;k<allSelectedLayer.length;k++){
					
						var node = document.getElementById(allSelectedLayer[k]);
						if (node.parentNode) {
						  node.parentNode.removeChild(node);
						}
						//Remove the deleted Manholes Markers from map and array
						markersManhole[allSelectedLayer[k]].setMap(null);
						markersManhole.splice(allSelectedLayer[k], 1);
						markerClusterManhole.removeMarker(markersManhole[allSelectedLayer[k]]);
					}
				}
				
				else if(layer=="Handhole" || layer =="AllHandholes" ) {	
					 
					//Remove the deleted Handhole from tree
					for(var k=0;k<allSelectedLayer.length;k++){
					
						var node = document.getElementById(allSelectedLayer[k]);
						if (node.parentNode) {
						  node.parentNode.removeChild(node);
						}
						
						//Remove the deleted Handholes Markers from map and array
						markersHandhole[allSelectedLayer[k]].setMap(null);
						markersHandhole.splice(allSelectedLayer[k], 1);
						markerClusterHandhole.removeMarker(markersHandhole[allSelectedLayer[k]]);
					}
				}
				else if(layer == "DistBoard" || layer =="AllDistBoards") {
				
					//markerClusterDistBoard.clearMarkers();
					
					//Remove the deleted DB from tree
					for(var k=0;k<allSelectedLayer.length;k++){
						var node = document.getElementById(allSelectedLayer[k]);
						if (node.parentNode) {
						  node.parentNode.removeChild(node);
					}
					
					//Remove the deleted DB Markers from map and array
					markersDistBoard[allSelectedLayer[k]].setMap(null);
					markersDistBoard.splice(allSelectedLayer[k], 1);
					markerClusterDistBoard.removeMarker(markersDistBoard[allSelectedLayer[k]]);
					
				}				
				}
				else if(layer=="Project"){
					
					for(var k=0;k<allSelectedLayer.length;k++){
					
						var node = document.getElementById(allSelectedLayer[k]);
						if (node.parentNode) {
						    node.parentNode.removeChild(node);
						}
						//Remove the deleted Manholes Markers from map and array
						if(allManholeProject.length>0){
							//markerClusterManhole.clearMarkers();
							for(var M=0;M<allManholeProject.length;M++){
								markersManhole[allManholeProject[M]].setMap(null);
								markersManhole.splice(allManholeProject[M], 1);
								markerClusterManhole.removeMarker(markersManhole[allManholeProject[M]]);
							}
						}
						//Remove the deleted Handholes Markers from map and array
						if(allHandholeProject.length>0){
							//markerClusterHandhole.clearMarkers();
							for(var H=0;H<allHandholeProject.length;H++){
								markersHandhole[allHandholeProject[H]].setMap(null);
								markersHandhole.splice(allHandholeProject[H], 1);
								markerClusterHandhole.removeMarker(markersHandhole[allHandholeProject[H]]);
							}
						}
						//Remove the deleted DB Markers from map and array
						if(allDBProject.length>0){
							//markerClusterDistBoard.clearMarkers();
							for(var D=0;D<allDBProject.length;D++){
								markersDistBoard[allDBProject[D]].setMap(null);
								markersDistBoard.splice(allDBProject[D], 1);
								markerClusterDistBoard.removeMarker(markersDistBoard[allDBProject[D]]);
							}
						}

						//Remove the deleted Fiber path  from map and array
						if(allFiberPathProject.length>0){
							//Hide cables from map 
							for(var F=0;F<allFiberPathProject.length;F++){
								 
								  fiberArray[allFiberPathProject[F]].setMap(null);
								  removeItemAll(allFiberCables, allFiberPathProject[F]);
								
							}
							//Remove tube from Map and array
							for(C=0;C<FibertubeProject.length;C++){
								tubeArray[FibertubeProject[C]].setMap(null);
								removeItemAll(tubeArray,FibertubeProject[C]);
							}
							
							//Remove strand From Map and array
							for(S=0;S<FiberStrandProject.length;S++){
								strandArray[FiberStrandProject[S]].setMap(null);
								removeItemAll(strandArray, FiberStrandProject[S]);
							}
					    }
						
						//Remove the deleted Trench path from map and array
						if(allTrenchPathProject.length>0){
							//Hide cables from map 
							for(var T=0;T<allTrenchPathProject.length;T++){
								removeItemAll(allTrenches,allTrenchPathProject[T]);							
								trenchArray[allTrenchPathProject[T]].setMap(null);
								removeItemAll(trenchArray,allTrenchPathProject[T]);
							}
							//Remove Duct from Map and array
							for(D=0;D<TrenchDuctProject.length;D++){
								removeItemAll(allDucts,TrenchDuctProject[D]);						
								ductArray[TrenchDuctProject[D]].setMap(null);
								removeItemAll(ductArray,TrenchDuctProject[D]);							
							}
					    }						
					}
				}
				},
				error: function (result) {
					alert("Error");
				}
					}); 		
}

selectedFiberPath=[];
checkedFibertube=[];
checkedFiberStrand=[];
function deleteFiberCable(layer,subLayer,selectedIdContext){
	console.log("layer is "+layer);
	console.log("subLayer is "+subLayer);
	console.log("yesss fiber/tube/strand cable and id is  "+selectedIdContext);
	selectedFiberTubeStrandPath=[]; // fiber/tube/strand path id array
	selectedFiberTubeStrandContext =""; // id from selectedIdContext
	checkIfLayerDel=""; 
	var layerCheckedLength; //checking if main fiber/tube/strand path checked
	var subLayerCheckedLength; //checking if single fiber/tube/strand path checked
	checkedFiberStrand=[]; //strands id of fiber path deleting
	checkedFiberTube_TubeStrand=[]; // tubes/strands id of fiber/tube path deleting
		
	//deleting fiber path from main of single all cases
	if(layer == "FiberCable" || subLayer=="FiberCable" || layer=="FiberTube" || subLayer=="FiberTube" || layer=="FiberStrand" || subLayer=="FiberStrand"){
		
		//Delete single fiber/tube/strand path from context menu right click
if (subLayer==""){
			
			// TO CHECK IF FIBER PATH DISPLAYED ON MAP
			layerCheckedLength= $("."+layer+":checked").length;
		    // Get fiber/tube/strand cable using id from context menu
			selectedFiberTubeStrandPath.push(selectedIdContext);
			console.log("111");//delete single item fiber/tube strand
		}
		// Delete all fiber/tube/strand path from main context menu right click
		else{
			// TO CHECK IF FIBER/TUBE/STRAND PATH DISPLAYED ON MAP
			subLayerCheckedLength=$("."+subLayer+":checked").length;
			if (subLayer == "FiberCable"){// if fiber is the main
		    	selectedFiberTubeStrandContext = "#FiberPath_f_"+selectedIdContext+"";
		    	console.log("2222");//get context of showed item on map used in 4444
		    }
		    else{// the main is tube/strand 
		    	selectedFiberTubeStrandContext = "#"+selectedIdContext+"";
		    	console.log("3333");//get context of showed item on map used in 5555
		    }
			
				//Main fiber/tube/strand path folder checkbox is checked(ALL Single physical layer INSIDE IT ARE CHECKED) so delete all 
				if($("."+layer+":checked").length>0){
					//Get all checked single fiber cable
					$(selectedFiberTubeStrandContext).find(' > ul > li > ul > li ').each(function(){
						if($(this).find('input:checkbox').is(':checked')) {						
							selectedFiberTubeStrandPath.push($(this).attr('id'));
							console.log("4444");//delete from fiber directory  when selected item (not working) 
						}
					});	
					
				}
				
				//Main fiber/tube/strand path folder checkbox is unchecked (SOME checked/unchecked cables inside it ) so delete only checked
				else {
					
					//Delete CHECKED single cable from main physical Layer right click context menu
					if($("."+subLayer+":checked").length >0){
						//get ID of the  CHECKED single fiber/tube/strand path from the main folder right click context menu
						$(selectedFiberTubeStrandContext).find(' > ul > li ').each(function(){	
							if($(this).find('input:checkbox').is(':checked')) {		
								selectedFiberTubeStrandPath.push($(this).attr('id'));
							
							}
			    		});
					}	
					
				
				}// end unchecked main checkbox
			}// end else delete from main physical layer
		
		if(layer == "FiberCable" || subLayer=="FiberCable" || layer=="FiberTube" || subLayer=="FiberTube"){
			for(p=0;p<selectedFiberTubeStrandPath.length;p++){
				// get related tubes if it is fiber path or related strands if it is tube path
				$("#"+selectedFiberTubeStrandPath[p]).find(' > ul > li >ul >li').each(function(){
					checkedFiberTube_TubeStrand.push($(this).attr('id'));	
			    });
				// get related strand for related fiber path
				if(layer == "FiberCable" || subLayer=="FiberCable"){
					 $("#"+selectedFiberTubeStrandPath[p]).find(' > ul > li >ul >li >ul >li >ul >li ').each(function(){			
							checkedFiberStrand.push($(this).attr('id'));    
				     });
				}
		       
			}
		}
		}
				
		$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/deletePhysicalLayer',
				data: {
				"physicalLayerID":selectedFiberTubeStrandPath,
				"physicalLayer":layer,
				"NodeId":IdNodeSelectedTemp,
				},
				dataType: "json",
				success: function (data) {
				
				console.log("Delete done");	
				checkIfLayerDel="True";
				
				//////////////////////////////////////////////////	
		        
				if(layer == "FiberCable" || subLayer=="FiberCable" || layer=="FiberTube" || subLayer=="FiberTube" || layer=="FiberStrand" || subLayer=="FiberStrand"){
					console.log("selectedFiberTubeStrandPath is "+selectedFiberTubeStrandPath);
					if(selectedFiberTubeStrandPath.length>0){
						// if dispalyed on the map set null and remove from arrays
						if(layerCheckedLength>0 || subLayerCheckedLength>0){
							console.log(" displayed on map");
							// delete related tubes if it is fiber path or related strands if it is tube path
							if(layer == "FiberCable" || subLayer=="FiberCable" || layer=="FiberTube" || subLayer=="FiberTube"){
								
								for(C=0;C<checkedFiberTube_TubeStrand.length;C++){
									//Remove tube from Map and array related to fiber
									if(layer == "FiberCable" || subLayer=="FiberCable"){
										tubeArray[checkedFiberTube_TubeStrand[C]].setMap(null);
										removeItemAll(tubeArray,checkedFiberTube_TubeStrand[C]);
									
									}else{//Remove strand from Map and array related to tube
										strandArray[checkedFiberTube_TubeStrand[C]].setMap(null);
								    	removeItemAll(strandArray, checkedFiberTube_TubeStrand[C]);
									}
							    }
							
								if(layer == "FiberCable" || subLayer=="FiberCable"){
									//Remove strand From Map and array related to fiber
									for(S=0;S<checkedFiberStrand.length;S++){
										strandArray[checkedFiberStrand[S]].setMap(null);
								    	removeItemAll(strandArray, checkedFiberStrand[S]);
									}
								}
							
							
							}
				            for(var j=0;j<selectedFiberTubeStrandPath.length;j++){
				            	 //Remove the deleted fiber cable from arrays
				            	if(layer == "FiberCable" || subLayer=="FiberCable"){
				            	  removeItemAll(allFiberCables, selectedFiberTubeStrandPath[j]);
					              
				            	  // delete fiber path from map display
					              if(directionHashmap.get(selectedFiberTubeStrandPath[j]) != undefined) {
										for(ii = 0; ii < directionHashmap.get(selectedFiberTubeStrandPath[j]).length; ii++) {
											directionHashmap.get(selectedFiberTubeStrandPath[j])[ii].setMap(null); 
										}
									} else {
							              fiberArray[selectedFiberTubeStrandPath[j]].setMap(null);
									}
					              
						          removeItemAll(fiberArray, selectedFiberTubeStrandPath[j]);
				            	}
				            	else if(layer=="FiberTube" || subLayer=="FiberTube"){// remove deleted tube path from map and tree
				            		//tubeArray[selectedFiberTubeStrandPath[j]].setMap(null);
				            		if(directionHashmapTube.get(selectedFiberTubeStrandPath[j]) != undefined) {
										for(ii = 0; ii < directionHashmapTube.get(selectedFiberTubeStrandPath[j]).length; ii++) {
											directionHashmapTube.get(selectedFiberTubeStrandPath[j])[ii].setMap(null); 
										}
									} 
				            		else {
					            		tubeArray[selectedFiberTubeStrandPath[j]].setMap(null);
									}
				            		
				            		removeItemAll(tubeArray,selectedFiberTubeStrandPath[j]);
				            	}
				            	else{//Remove deleted strand From Map and array
				            		strandArray[selectedFiberTubeStrandPath[j]].setMap(null);
							    	removeItemAll(strandArray, selectedFiberTubeStrandPath[j]);
				            		
				            	}
						         //Remove fiber/tube/strand cable from tree
						         var node = document.getElementById(selectedFiberTubeStrandPath[j]);
						         if (node.parentNode) {
						        	 node.parentNode.removeChild(node);
						         }	
				          }
					    }
						// not displayed on map so just remove from the arrays 
						else{
							console.log("Nothing displayed on map");

							console.log(" displayed on map");
							// delete related tubes if it is fiber path or related strands if it is tube path
							if(layer == "FiberCable" || subLayer=="FiberCable" || layer=="FiberTube" || subLayer=="FiberTube"){
								
								for(C=0;C<checkedFiberTube_TubeStrand.length;C++){
									//Remove tube from Map and array related to fiber
									if(layer == "FiberCable" || subLayer=="FiberCable"){
										removeItemAll(tubeArray,checkedFiberTube_TubeStrand[C]);
									
									}else{//Remove strand from Map and array related to tube
								    	removeItemAll(strandArray, checkedFiberTube_TubeStrand[C]);
									}
							    }
							
								if(layer == "FiberCable" || subLayer=="FiberCable"){
									//Remove strand From Map and array related to fiber
									for(S=0;S<checkedFiberStrand.length;S++){
								    	removeItemAll(strandArray, checkedFiberStrand[S]);
									}
								}
							
							
							}
				            for(var j=0;j<selectedFiberTubeStrandPath.length;j++){
				            	 //Remove the deleted fiber cable from arrays
				            	if(layer == "FiberCable" || subLayer=="FiberCable"){
				            	  removeItemAll(allFiberCables, selectedFiberTubeStrandPath[j]);
						          removeItemAll(fiberArray, selectedFiberTubeStrandPath[j]);
				            	}
				            	else if(layer=="FiberTube" || subLayer=="FiberTube"){// remove deleted tube path from map and tree
									removeItemAll(tubeArray,selectedFiberTubeStrandPath[j]);
				            	}
				            	else{//Remove deleted strand From Map and array
							    	removeItemAll(strandArray, selectedFiberTubeStrandPath[j]);
				            		
				            	}
						         //Remove fiber/tube/strand cable from tree
						         var node = document.getElementById(selectedFiberTubeStrandPath[j]);
						         if (node.parentNode) {
						        	 node.parentNode.removeChild(node);
						         }	
				          }
				}					
	     }
	}
				},
				error: function (result) {
					alert("Error");
				}
		}); 
		
		
}

function deleteTrenchPath(layer,subLayer,selectedTrenchContext){
	selectedTrenchesDuctsForDel=[];
	selectedDuctsForDel=[];
	selectedTrenchDuctContext=[];
	subLayerCheckedLength = 0, layerCheckedLength = 0;
	
	// deleting from main or single trench/Duct
	if(layer == "TRENCH" || subLayer=="TRENCH" || layer == "DUCT" || subLayer=="DUCT"){
		//Delete single trench/Duct from context menu right click
		if (subLayer==""){
			//console.log("selectedTrenchContext is single "+selectedTrenchContext);
			// Get single trench/Duct using id from context menu
			selectedTrenchesDuctsForDel.push(selectedTrenchContext);
			console.log("case 1 "+selectedTrenchesDuctsForDel);	
			// TO CHECK IF trench/Duct PATH DISPLAYED ON MAP
			layerCheckedLength= $("."+layer+":checked").length;
		}
		else{// delete from the main trench/Duct folder right click menu
			
			subLayerCheckedLength=$("."+subLayer+":checked").length;// TO CHECK IF TRENCH PATH DISPLAYED ON MAP
		    if (subLayer=="TRENCH"){// if trench is the main
		    	selectedTrenchDuctContext = "#Trench_f_"+selectedTrenchContext+"";
		    }
		    else{// the main is duct 
		    	selectedTrenchDuctContext = "#"+selectedTrenchContext+"";
		    }
				//Main Trench/Duct path folder checkbox is checked(ALL Single trench INSIDE IT ARE CHECKED) so delete all 
				if($("."+layer+":checked").length>0){
					//Get all checked single trench /Duct
					//console.log("selectedTrenchContext all is"+selectedTrenchContext);
					$(selectedTrenchDuctContext).find(' > ul > li ').each(function(){
						if($(this).find('input:checkbox').is(':checked')) {						
						selectedTrenchesDuctsForDel.push($(this).attr('id'));
						console.log("case 2");	
						}
					});
					
				}
				
				//Main trench/Duct path folder checkbox is unchecked (SOME checked/unchecked cables inside it ) so delete only checked
				else {
					console.log(subLayer+" checked "+$("."+subLayer+":checked").length);
					//Delete CHECKED single trench/Duct from main folder right click context menu
					if($("."+subLayer+":checked").length >0){
						//get ID of the  CHECKED single trench/Duct from the main folder right click context menu
						$(selectedTrenchDuctContext).find(' > ul > li ').each(function(){	
							if($(this).find('input:checkbox').is(':checked')) {		
								selectedTrenchesDuctsForDel.push($(this).attr('id'));
								console.log("case 3");	
								//console.log("selectedTrenchContext some checked is"+selectedTrenchesDuctsForDel);
							
							}
			    		});
					}
					
					//Delete All single trenches/Ducts from main trench path folder right click context menu
					/*else {
						//console.log("nothing checked");
						// get all ids of the all trench paths			
						$(selectedTrenchDuctContext).find(' > ul > li ').each(function(){					
							selectedTrenchesDuctsForDel.push($(this).attr('id'));
							console.log("case 4");	
						});
						//console.log("selectedTrenchContext nothing checked is"+selectedTrenchesDuctsForDel);
					}  */
				}
				
				// getting related ducts if exists for trench
				if (subLayer=="TRENCH"){
					if(selectedTrenchesDuctsForDel.length > 0){
						for(d=0;d<selectedTrenchesDuctsForDel.length;d++){
							$("#"+selectedTrenchesDuctsForDel[d]).find(' > ul > li >ul >li').each(function(){
								if($(this).find('input:checkbox').is(':checked')) {	
									selectedDuctsForDel.push($(this).attr('id'));	
									console.log("case 5");	
								}
						    });
					    }
					
					}
		      }
		}
	
		console.log("len to be deleted "+selectedTrenchesDuctsForDel.length);
	}//end delete from trench 
	//else if (layer=="Duct") {}
	
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/deletePhysicalLayer',
		data: {
		"physicalLayerID":selectedTrenchesDuctsForDel,
		"physicalLayer":layer,
		"NodeId":IdNodeSelectedTemp,
		},
		dataType: "json",
		success: function (data) {
			if(layer == "TRENCH" || subLayer=="TRENCH" || layer == "DUCT" || subLayer=="DUCT"){
				if(selectedTrenchesDuctsForDel.length>0){
					// if dispalyed on the map set null and remove from arrays
					if(layerCheckedLength>0 || subLayerCheckedLength>0){
						// deleting  related ducts related to trench from array and map
						for(D=0;D<selectedDuctsForDel.length;D++){
							removeItemAll(allDucts,selectedDuctsForDel[D]);						
							ductArray[selectedDuctsForDel[D]].setMap(null);
							removeItemAll(ductArray,selectedDuctsForDel[D]);
						}
		
						// deleting trench/duct from array and map
						for(T=0;T<selectedTrenchesDuctsForDel.length;T++){
							if(layer == "TRENCH" || subLayer=="TRENCH"){
								removeItemAll(allTrenches,selectedTrenchesDuctsForDel[T]);							
								trenchArray[selectedTrenchesDuctsForDel[T]].setMap(null);
								removeItemAll(trenchArray,selectedTrenchesDuctsForDel[T]);
								
							}else{
								removeItemAll(allDucts,selectedTrenchesDuctsForDel[T]);						
								ductArray[selectedTrenchesDuctsForDel[T]].setMap(null);
								removeItemAll(ductArray,selectedTrenchesDuctsForDel[T]);
							}
							  
							var node = document.getElementById(selectedTrenchesDuctsForDel[T]);
							if (node.parentNode) {
								node.parentNode.removeChild(node);
							}
							
						}
				    }
			        else{
			    	// deleting  related ducts related to trench from array and map
					for(D=0;D<selectedDuctsForDel.length;D++){
						removeItemAll(allDucts,selectedDuctsForDel[D]);						
						removeItemAll(ductArray,selectedDuctsForDel[D]);
					}
	
					// deleting trench/duct from array and map
					for(T=0;T<selectedTrenchesDuctsForDel.length;T++){
						if(layer == "TRENCH" || subLayer=="TRENCH"){
							removeItemAll(allTrenches,selectedTrenchesDuctsForDel[T]);							
							removeItemAll(trenchArray,selectedTrenchesDuctsForDel[T]);
						}else{
							removeItemAll(allDucts,selectedTrenchesDuctsForDel[T]);						
							removeItemAll(ductArray,selectedTrenchesDuctsForDel[T]);
						}
						 
						var node = document.getElementById(selectedTrenchesDuctsForDel[T]);
						if (node.parentNode) {
							node.parentNode.removeChild(node);
						}
					}			    	
			    }
			}
		  }
		},
		error: function (result) {
			alert("Error");
		}
	});
}

var physicalLayer_Id=[];
function deleteJunction(physicalLayer,subLayer,physLayerID,manHandholeId){
	physicalLayer_Id=[];
	checkedJunctions=[];
	junctionsAfterDel=[];
    
    var manHandholeID = manHandholeId ;
	var manHandoleName = window[""+manHandholeID][1];
	//console.log("manHandoleNameEE is " +manHandoleName);
	
	if(physicalLayer =="Manhole") {
			$("#"+selectedManIdContext).find(' > ul > li >ul >li').each(function(){
					var jctId =$(this).attr('id');
					if($(this).children('input:checkbox').is(':checked')){
						checkedJunctions.push(jctId);
					}
		  	});
	}
		 else {
		  	$("#"+selectedHandIdContext).find(' > ul > li >ul >li').each(function(){
					var jctId =$(this).attr('id');
					if($(this).children('input:checkbox').is(':checked')){
						checkedJunctions.push(jctId);
					}
		  	});
		  	
		 }
				console.log("checkedJunctions is" +checkedJunctions);
			if(checkedJunctions.length >0) {
			physicalLayer_Id =checkedJunctions;
			}
			else {
			
			physicalLayer_Id.push(physLayerID);
			}
    	console.log("physicalLayer_Id" +physicalLayer_Id);
    	
    	
    
		$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/deletePhysicalLayer',
				data: {
				"physicalLayerID":physicalLayer_Id,
				"physicalLayer":subLayer,
				"manHandholeID" :manHandholeID,
				"manHandoleName":manHandoleName
				},
				dataType: "json",
				success: function (data) {

				checkIfLayerDel="True";
				
					action = "delete";
				var deletedJunctions=[];
				//Remove the deleted JCT from tree
					for(var k=0;k<physicalLayer_Id.length;k++){
						var node = document.getElementById(physicalLayer_Id[k]);
						if (node.parentNode) {
						  node.parentNode.removeChild(node);
						}
						
					}
					
			if(physicalLayer =="Manhole") {
								console.log("DELETE DONE");
				
			$("#"+selectedManIdContext).find(' > ul > li >ul >li').each(function(){
					var jctId =$(this).attr('id');
					junctionsAfterDel.push(jctId);
		  		});
		  		
				  
		  		//if no jct exists we need to change the manhole/handhole icon 
				if(junctionsAfterDel.length ==0){
					var handArray;
								 
				$("#"+selectedManIdContext+"> .folder").remove();					 
				$("#"+selectedManIdContext+"> ul").remove();
				
				var selectedManhole=selectedManIdContext;
				//console.log("selectedManhole is" +selectedManhole);
			
				//markerClusterManholeJct.removeMarker(markersManholeJct[selectedManhole]);
				//markerClusterManholeJct.clearMarkers();
				
				//var selectedManhole=window[""+juncID][4];
				var selMarker="";
				markerId=selectedManhole;						
				selMarker=markersManhole[markerId];
				
				//$("#"+selectedManhole+"> span>img").attr('src', getContext()+"/resources/NetworkImages/manholeRed.png");
				$("#"+selectedManIdContext+"> .TreeSpan").html("<img style='color: #08526D;' src='"+getContext()+"/resources/NetworkImages/manholeRed.png'>  " +data.ManHandholeNewName+"  ");					 
				
				markerClusterManhole.removeMarker(markersManhole[selectedManhole]);
				markerClusterManhole.clearMarkers();
					
				markersManhole[selectedManhole].setMap(null);
				selMarker.setIcon(iconManhole);
				
				AllManholeCheckFilter();
					
				markersManhole.splice(selectedManhole, 1);
					
				$(".Manhole").prop('checked', false);
				markerClusterManhole.clearMarkers();
				$(".AllManholes").prop("checked",false);
				$('.JctManholes').prop('checked', false);
				$('.ManholeJct').prop('checked', false);
				
				
				window[""+selectedManhole][1] = data.ManHandholeNewName;
				  create_Marker_Click(selectedManhole,data.ManHandholeNewName,window[""+selectedManhole][2],window[""+selectedManhole][3],markersManhole,markerClusterManhole,"No_Junction","");							 
				 //createManhole_Marker_Click(selectedManhole,data.ManHandholeNewName,window[""+selectedManhole][2],window[""+selectedManhole][3],markersManhole,markerClusterManhole,"No_Junction");				
				 markerClusterManhole.addMarker(markersManhole[""+selectedManhole]);
				 markersManhole[selectedManhole].setMap(map);
				 
				 $("#"+selectedManhole).children(':checkbox').prop( "checked", true );				 
				console.log("DELETE DONE");
		  	}
		  	}
		  	else {
		  	$("#"+selectedHandIdContext).find(' > ul > li >ul >li').each(function(){
					var jctId =$(this).attr('id');
					junctionsAfterDel.push(jctId);
		  		});

		  		//if no jct exists we need to change the manhole/handhole icon 
				if(junctionsAfterDel.length ==0){
							 
				$("#"+selectedHandIdContext+"> .folder").remove();					 
				$("#"+selectedHandIdContext+"> ul").remove();
				
				var selectedHandhole=selectedHandIdContext;
				//console.log("selectedHandIdContext is" +selectedHandhole);
			
				var selMarker="";
				markerId=selectedHandhole;						
				selMarker=markersHandhole[markerId];
				
				//$("#"+selectedHandhole+"> span>img").attr('src', getContext()+"/resources/NetworkImages/handholeYellow.png");
				$("#"+selectedHandhole+"> .TreeSpan").html("<img style='color: #08526D;' src='"+getContext()+"/resources/NetworkImages/handholeYellow.png'>  " +data.ManHandholeNewName+"  ");					 
				
				markerClusterHandhole.removeMarker(markersHandhole[selectedHandhole]);
				markerClusterHandhole.clearMarkers();
					
				markersHandhole[selectedHandhole].setMap(null);
				selMarker.setIcon(iconHandhole);
				
				AllHandholeCheckFilter();
					
				markersHandhole.splice(selectedHandhole, 1);
					
				$(".Handhole").prop('checked', false);
				markerClusterHandhole.clearMarkers();
				$(".AllHandholes").prop("checked",false);
				$('.JctHandholes').prop('checked', false);
				$('.HandholeJct').prop('checked', false);
				
				window[""+selectedHandhole][1]=data.ManHandholeNewName;
				 create_Marker_Click(selectedHandhole,window[""+selectedHandhole][1],window[""+selectedHandhole][2],window[""+selectedHandhole][3],markersHandhole,markerClusterHandhole,window[""+selectedHandhole][1],"No_Junction", window[""+selectedHandhole][8],"");				
				 //createHandhole_Marker_Click(selectedHandhole,window[""+selectedHandhole][1],window[""+selectedHandhole][2],window[""+selectedHandhole][3],markersHandhole,markerClusterHandhole,window[""+selectedHandhole][1],"No_Junction", window[""+selectedHandhole][8]);				
				 markersHandhole[selectedHandhole].setMap(map);
				 
				 $("#"+selectedHandhole).children(':checkbox').prop( "checked", true );
				 
				console.log("DELETE DONE");
				}
			}
		},
		error: function (result) {
			alert("Error");
		}
	}); 

}
function pathMapListener(IdSelected,folder){
	//console.log("IdSelected "+IdSelected);
	//console.log("IdSelectedTemp "+IdSelectedTemp);   

	if(IdSelected!=IdSelectedTemp  || oldNtwLevel != fibernetlevel){					
	   if(folder == "Duct"){
		   
		   var parentTrench=$("#"+IdSelected).parents().eq(3).attr('id');
		   var parentDuctFolder=$("#"+IdSelected).parents().eq(1).attr('id');		   
		   
		   var childrenInitial=$("#initial_ul").find(' > ul > li');
		  // var children = $("#Trench_f").find(' > ul > li');
		   var children = $("#"+parentTrench+"_f").find(' > ul > li');
		   var childrenDuctFolder=$("#"+parentTrench).find(' >ul >li');		  

		   var childrenDuct=$("#"+parentDuctFolder).find("#"+IdSelected); 
	   }
	   else if(folder == "Tube"){
		   
		   nodeFileId=$("#"+IdSelected).parents().eq(9).attr('id').split("_")[2];
		   fiber=$("#"+IdSelected).parents().eq(3).attr('id');
		   
			var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
			var childrenfiber=$("#FiberPath_f_"+nodeFileId+"").find("> ul > li");				 
			var childrenTubeFolder=$("#"+fiber).find('> ul > li');				   
			var children = $("#"+fiber+"_f").find("ul > #"+IdSelected);
		    var networkLevelFolder =  $("#FiberPath_f_"+nodeFileId+"").find(' > ul > li >ul >li');
			var childrenTubeFolder1 =$("#"+fiber).find('> ul > li >ul >li');				   		  
			//var childrenInitial=$("#initial_ul").find(' > ul > li > ul > #'+fiber+' > ul > #'+fiber+"_f > ul > #"+IdSelected);	   
	   }
	   else if(folder == "Strand"){ 
		   
		   fiber=$("#"+IdSelected).parents().eq(7).attr('id')
		   nodeFileId=$("#"+IdSelected).parents().eq(9).attr('id').split("__")[1];		
		   tube=$("#"+IdSelected).parents().eq(3).attr('id')  	
					
			var childrenInitial=$("#initial_ul_"+nodeFileId+"").find('>ul > li');
			var childrenfiber=$("#FiberPath_f_"+nodeFileId+"").find('> ul > li');				 
			var childrenTubeFolder=$("#"+fiber).find('> ul > li');				  
					
			var children = $("#"+fiber+"_f").find("> ul >li ");
			var childrenStrandfolder = $("#"+tube).find("> ul > li");
			var childrenStrandfinal = $("#"+tube+"_f").find("> ul > #"+IdSelected);
			var childrenTubeFolder1 =$("#"+tube).find('> ul > li >ul >li');		
		    var networkLevelFolder =  $("#FiberPath_f_"+nodeFileId+"").find(' > ul > li >ul >li');


	   }
	   else if(folder == "Trench_f_"){		   	
			nodeFileId=$("#"+IdSelected).parent().parent().attr('id').split("_")[2];
		    var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
		    var children =  $("#"+folder+nodeFileId+"").find(' > ul > li');
	   }
	   else{
			nodeFileId=$("#"+IdSelected).parent().parent().attr('id').split("__")[1];
		    var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
		    var children =  $("#"+folder+nodeFileId+"").find(' > ul > li');
		    var networkLevelFolder =  $("#"+folder+nodeFileId+"").find(' > ul > li >ul >li');

	   }

	   if(IdSelectedTemp!=""){
			
				$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
				$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
				$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
				$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
				
				children.show('fast');
				childrenInitial.show('fast');
				
				if(folder == "Duct"){ 
					childrenDuct.show('fast');
					childrenDuctFolder.show('fast');  
					
				}else if(folder == "Tube"){
					childrenTubeFolder.show('fast');
					childrenfiber.show('fast');
					networkLevelFolder.show('fast'); 
					childrenTubeFolder1.show('fast');
				}
				else if(folder == "Strand"){ 
					childrenfiber.show('fast');
					childrenTubeFolder.show('fast');
					childrenStrandfolder.show('fast');
					childrenStrandfinal.show('fast');
					networkLevelFolder.show('fast')
					childrenTubeFolder1.show('fast');
					
				}
				else if(folder == "FiberPath_f_") {
					networkLevelFolder.show('fast'); 
				}
				
				$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				if(folder == "Duct"){
					 $("#Trench_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					 $("#"+parentTrench + " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					 $("#"+parentTrench +"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				}
				else if(folder == "Tube"){
					$("#FiberPath_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+ " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#FiberPath_f_"+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
					
			    }
				else if (folder == "Strand" ){
					$("#FiberPath_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#FiberPath_f_"+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+ " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				
					$("#"+tube+ " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+tube+"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
					
				}
				
				else if(folder=="Trench_f_") {
					$("#"+folder+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+folder+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
				
				}
				else{
					$("#"+folder+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+folder+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
				}				
			  }

	   else{
				children.show('fast'); 
				childrenInitial.show('fast');
				
				$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
				$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
				
				
				if(folder == "Duct"){
					childrenDuct.show('fast');
					childrenDuctFolder.show('fast');   
				}
				else if(folder == "Tube"){ 
					childrenTubeFolder.show('fast');
					childrenfiber.show('fast');
					networkLevelFolder.show('fast'); 
					childrenTubeFolder1.show('fast');

				}
				else if(folder == "Strand"){ 
					childrenfiber.show('fast');
					childrenTubeFolder.show('fast');
					childrenStrandfolder.show('fast');
					childrenStrandfinal.show('fast');
					networkLevelFolder.show('fast')
					childrenTubeFolder1.show('fast');
				}
				else if(folder == "FiberPath_f_") {
					networkLevelFolder.show('fast'); 
				}
				
				$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				
				if(folder == "Duct"){
					 $("#Trench_f> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					 $("#"+parentTrench + " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					 $("#"+parentTrench +"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				}
				else if(folder == "Tube"){					
					$("#FiberPath_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+ " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
					$("#FiberPath_f_"+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');

			    }
				else if (folder == "Strand" ){
					
					$("#FiberPath_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#FiberPath_f_"+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+ " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+fiber+"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				
					$("#"+tube+ " > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+tube+"_f > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				
				}
				
				else{
					$("#"+folder+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#"+folder+nodeFileId+"").find(' > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
				}

		}
		IdSelectedTemp=IdSelected;
		oldNtwLevel="";
	}
	/*else {
		tree_prop_generalPhysical();
	}
	*/
	if(document.getElementById(""+IdSelected)!=null){
		
		/*if(IdNodeSelectedTemp=="CurrentPhysicalLayer") {
			//console.log("enter here")
			projectOffset=($("#FiberPath_f_"+nodeFileId+"").offset().top);
		}
		else {
			projectOffset=($("#initial_ul_CurrentPhysicalLayer").offset().top)-($("#initial_ul_"+IdNodeSelectedTemp).offset().top);
		}	
		
		
		offset=$("#"+IdSelected).offset().top;																
		offsetTotal=(offset-projectOffset);
		$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
		*/
		var cableScrollTo = document.getElementById(""+IdSelected);				
		cableScrollTo.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});		
	}
}


// to be deleted
/*
function junctionMapListener(IdSelected,physLayer){
	if (physLayer=="Manhole") {
		//console.log("IdSelectedTemp" +IdSelectedTemp);
		//console.log("IdSelected" +IdSelected);
		if(IdSelected!=IdSelectedTemp){	
		
		  var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
		   var children = $("#Manhole_f_"+nodeFileId+"").find(' > ul > li');
			  
		   if(IdSelectedTemp!=""){
					$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
					$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
					$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
					$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
					children.show('fast');

					childrenInitial.show('fast');
			 
					 $("#initial_ul _"+nodeFileId+"> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					 $("#Manhole_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				 
					 }

		   else{
			  
					childrenInitial.show('fast');
					children.show('fast');

					$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
					$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
					$("#initial_ul _"+nodeFileId+"> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#Manhole_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				  

			}
			IdSelectedTemp=IdSelected;
		}
	var junctionManh = document.getElementById(""+IdSelected);				
	junctionManh.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
	
	}
	else {
	
	if(IdSelected!=IdSelectedTemp){	
		   var childrenInitial=$("#initial_ul").find(' > ul > li');

		   var children = $("#Handhole_f").find(' > ul > li');
			   
		   if(IdSelectedTemp!=""){
					$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
					$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
					$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
					$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
					children.show('fast');

					childrenInitial.show('fast');
					$("#initial_ul _"+nodeFileId+"> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#Handhole_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				  
				}

		   else{
			  
					childrenInitial.show('fast');
					children.show('fast');

					$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
					$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
					$("#initial_ul _"+nodeFileId+"> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#Handhole_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				  
			}
			IdSelectedTemp=IdSelected;
		}
	var junctionHand = document.getElementById(""+IdSelected);				
	junctionHand.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
	
	
	}

}


function fiberCableMapListener(IdSelected){
	console.log("IdSelected "+IdSelected);
	nodeFileId=$("#"+IdSelected).parent().parent().attr('id').split("FiberPath_f_")[1];
	if(IdSelected!=IdSelectedTemp){	
		//fiberArray[IdSelected].label.setMap(map);
		
		console.log("initial_ul_"+nodeFileId);
	   var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');

	   var children = $("#FiberPath_f_"+nodeFileId+"").find(' > ul > li');
		   
	   if(IdSelectedTemp!=""){
				console.log("IdSelectedTemp "+IdSelectedTemp);   
				$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
				$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
				$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
				$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
				
				//children.show('fast');
				if(!children.is(":visible")){
					children.show();
				}
				childrenInitial.show('fast');
		 
				$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				 
				 $("#FiberPath_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			  }

	   else{
		  
				childrenInitial.show('fast');
				children.show('fast');

				$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
				$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
				$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				 
				$("#FiberPath_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

		}
		IdSelectedTemp=IdSelected;
	}
var fiber = document.getElementById(""+IdSelected);				
fiber.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
}

function trenchMapListener(IdSelected){
nodeFileId=$("#"+IdSelected).parent().parent().attr('id').split("Trench_f_")[1];

if(IdSelected!=IdSelectedTemp){	
	//fiberArray[IdSelected].label.setMap(map);
	var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');

	var children = $("#Trench_f_"+nodeFileId+"").find(' > ul > li');
		
	if(IdSelectedTemp!=""){
			console.log("IdSelectedTemp "+IdSelectedTemp);   
			$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
			$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
			$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
			children.show('fast');

			childrenInitial.show('fast');
		
			$("#initial_ul _"+nodeFileId+"> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				
				$("#Trench_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			}

	else{
		
			childrenInitial.show('fast');
			children.show('fast');

			$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
			$("#initial_ul_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				
			$("#Trench_f_"+nodeFileId+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

	}
	IdSelectedTemp=IdSelected;
}
var trench = document.getElementById(""+IdSelected);				
trench.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
}


function ductMapListener(IdSelected){

	if(IdSelected!=IdSelectedTemp){	
		//fiberArray[IdSelected].label.setMap(map);
	   var childrenInitial=$("#initial_ul").find(' > ul > li');

	   var children = $("#Trench_f").find(' > ul > li');
		var parentTrench=$("#"+IdSelected).parents().eq(3).attr('id');
		console.log("parentTrench "+parentTrench);
		var parentDuctFolder=$("#"+IdSelected).parents().eq(1).attr('id');
		console.log("parentDuctFolder "+parentDuctFolder);
		var childrenDuctFolder=$("#"+parentTrench).find("#"+parentTrench+"_f");   
		var childrenDuct=$("#"+parentDuctFolder).find("#"+IdSelected); 
	   if(IdSelectedTemp!=""){
				console.log("IdSelectedTemp "+IdSelectedTemp);   
				$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
				$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
				$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
				$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");								
				children.show('fast');

				childrenInitial.show('fast');
				childrenDuct.show('fast');
				childrenDuctFolder.show('fast');
				$("#initial_ul > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				 
				 $("#Trench_f> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			  }

	   else{
		  
				childrenInitial.show('fast');
				children.show('fast');
				childrenDuct.show('fast');
				childrenDuctFolder.show('fast');
				$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
				$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");
				$("#initial_ul > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
				 
				$("#Trench_f> .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');

		}
		IdSelectedTemp=IdSelected;
	}
	console.log("IdSelected "+IdSelected);

	if(document.getElementById(""+IdSelected)!=null){
		console.log("selected li "+$(""+IdSelected).html());
		var duct = document.getElementById(""+IdSelected);				
		duct.scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"});
	}
}
*/

function ductAuxiliary_BoqAppendMarkup(name,long,lat,seqDuct){
	
	var markup = "<tr id='auxiliaryDuct_Row"+indexduct+"'><td><input type='checkbox' class='rowAboveBelowDuct'  id='Auxduct"+indexduct+"' style='position:relative;left:20px;top:10px' name='record'></td>"
			+"<td name='DuctSeq'><input name='DuctSeq' class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+seqDuct+"' readonly/></td>"
			+"<td name='auxiliaryDuct_Name' id='auxiliaryDuct_Name"+indexduct+"'><input id='aux_PointDuct"+indexduct+"' class='form-control text-input' type='text' style='width:180px;position:relative;left:11px;'/></td>"
			+"<td name='auxiliary_LongitudeDuct'><input id='aux_Longduct"+indexduct+"' name ='aux_Longduct' class='form-control text-input' type='text' style='width:150px;position:relative;' /></td>"
			+ "<td name='auxiliary_LatitudeDuct' style='width:200px'> <input id='aux_Latduct"+indexduct+"' name ='aux_Latduct' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		    +"<td name='auxiliaryDuct_Length'> <input id='aux_LenDuct"+indexduct+"'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";
	
	   calculateDistanceSourceDestination($("#SourceDuctLat").val(),$("#SourceDuctLng").val(),$("#DestinationDuctLat").val(),$("#DestinationDuctLng").val(),"auxiliary_ductTable");
	
	$("#auxiliary_ductTable > tbody").append(markup);
	$("#aux_PointDuct"+indexduct).val(name);
	$("#aux_Longduct"+indexduct).val(long);
	$("#aux_Latduct"+indexduct).val(lat);
	
	$("#Auxduct"+indexduct).on('change',function(){
	if(!isNaN($(this).parent().parent().children("td[name=auxiliary_LatitudeDuct]").children('input').val()) && !isNaN($(this).parent().parent().children("td[name=auxiliary_LongitudeDuct]").children('input').val())){											
			map.setZoom(15);
			panTo($(this).parent().parent().children("td[name=auxiliary_LatitudeDuct]").children('input').val(),$(this).parent().parent().children("td[name=auxiliary_LongitudeDuct]").children('input').val());
		}
		
	});

}





	//calculate total distance and optimize the waypoints order and appending tables
	function calculateTotalDistance(result) {

		var totalDist = 0;

		console.log("inside computeTotalDistance")
		var myroute = result.routes[0];
		for (i = 0; i < myroute.legs.length; i++) {
		totalDist += myroute.legs[i].distance.value;
		}
		console.log(totalDist)
		return totalDist;
	}

	/*function get_random (list) {
		return list[Math.floor((Math.random()*list.length))];
	  }*/


/*	function getTotalDrivingDist(){
		let fiberId= document.getElementById("FiberPathId").value;
		let srcLatitude = document.querySelector("#SourceLat").value;
		let srcLong = document.querySelector("#SourceLng").value;
		let destLatitude = document.querySelector("#DestinationLat").value;
		let destLong = document.querySelector("#DestinationLng").value;

		
		if(srcLong !="" && destLong != "" && (fiberId == "" || (fiberId!= "" && fiberAction != ""))){

		console.log("inside getTotalDrivingDist")								   
		getSelectedFiberCableRows(srcLatitude,srcLong,fiberId);

		console.log(srcLong,destLong,dict.length)

		var mapPoints = [];
		var start, end;
		var waypts = [];
		var directionsService = []; 
		var directionsDisplay = [];
		var totalDist = 0;

		//console.log(dict[0])
		if(dict.length > 0){
		
		mapPoints.push(new google.maps.LatLng(dict[0].aux_Latitude,dict[0].aux_Longitude));

		//console.log(dict[dict.length-1])
		mapPoints.push(new google.maps.LatLng(dict[dict.length-1].aux_Latitude,dict[dict.length-1].aux_Longitude));

		let middleArrayIndex = Math.floor(dict.length / 2)
		//console.log(dict[Math.floor(dict.length / 2)]);
		mapPoints.push(new google.maps.LatLng(dict[middleArrayIndex].aux_Latitude,dict[middleArrayIndex].aux_Longitude));

		// get the original data from dictionary
		for(h= 1 ; h < dict.length-1; h++) {

			if(h === 23)
				break

			if(h != middleArrayIndex){
				mapPoints.push(new google.maps.LatLng(dict[h].aux_Latitude,dict[h].aux_Longitude));
			}
		}

		console.log(mapPoints)

		
		//get start , destination and waypoints coordinates
		for (var k = 0; k < mapPoints.length; k++) {
			waypts.push({
			location: mapPoints[k],
			stopover: true
			});
		
		}
		//console.log(srcLatitude,srcLong)
		//console.log(destLatitude,destLong)				  
	}
		let source = new google.maps.LatLng(srcLatitude,srcLong)
		start = source;
		
		let destination = new google.maps.LatLng(destLatitude,destLong)
		end = destination;

		
		
		//google maps driving travel mode with direction API request
		var request = {
			origin: start,
			destination: end,
			waypoints: waypts,
			optimizeWaypoints: true,
			travelMode: google.maps.TravelMode.DRIVING,
			unitSystem: google.maps.UnitSystem.METRIC
		};
		//console.log(request)				
		directionsService.push(new google.maps.DirectionsService());
		var instance = directionsService.length - 1;
		directionsDisplay.push(new google.maps.DirectionsRenderer({
		preserveViewport: true
		}));
		directionsService[instance].route(request, function (response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
				console.log("1@@@")
				totalDist = calculateTotalDistance(response);
				console.log(totalDist)
				$("#totalDistanceDrivg").val((totalDist/1000).toFixed(3));
				$("#FiberDrivDist").val((totalDist/1000).toFixed(3));										 
		} else {
			console.log("2@@@")
		alert("directions response " + status);
		}
		});

		fiberAction = ""		  
		} // end of if condtion (if src and dest != null)
	
	}
*/	
function getTotalDrivingDistance(pathId,srcLat,srcLongt,destLat,destLongt,type,totalDrivngDst,DrivngDist){
	
	var pathId= document.getElementById(""+pathId).value;
	
	var srcLatitude = document.querySelector("#"+srcLat).value;
	var srcLong = document.querySelector("#"+srcLongt).value;
	var destLatitude = document.querySelector("#"+destLat).value;
	var destLong = document.querySelector("#"+destLongt).value;

	if(type=="fiber") {
		var pathAction = fiberAction;
	}
	else if(type=="strand") {
		var pathAction = strandAction;
	}
	else if(type=="trench") {
		var pathAction = trenchAction;
	}
	else {
		var pathAction = tubeAction;
	
	}
	var allAuxData=[];

	if(srcLong !="" && destLong != "" && (pathId == "" || (pathId!= "" && pathAction != ""))){
		
		if(type=="fiber") {
			getSelectedFiberCableRows(srcLatitude,srcLong,pathId);
			allAuxData=dict;
			fiberAction = "";		  
		}
		else if(type=="strand") {
			getSelectedStrandRows(srcLatitude,srcLong);
			allAuxData=dictStrandsAuxiliary;
			strandAction = "";
		}
		else if(type=="trench") {
			getSelectedTrenchAux(srcLatitude,srcLong);
			allAuxData=dict;
			trenchAction = "";
		}
		else {
			getSelectedTubeRows(srcLatitude,srcLong);
			allAuxData=dictTubesAuxiliary;
			tubeAction = "";		  
		
		}
		//console.log(srcLong,destLong,dict.length)

		var mapPoints = [];
		var start, end;
		var waypts = [];
		var directionsService = []; 
		var directionsDisplay = [];
		var totalDist = 0;

		//console.log(dict[0])
		if(allAuxData.length > 0){
		
		mapPoints.push(new google.maps.LatLng(allAuxData[0].aux_Latitude,allAuxData[0].aux_Longitude));

		//console.log(dict[dict.length-1])
		mapPoints.push(new google.maps.LatLng(allAuxData[allAuxData.length-1].aux_Latitude,allAuxData[allAuxData.length-1].aux_Longitude));

		let middleArrayIndex = Math.floor(dict.length / 2)
		//console.log(dict[Math.floor(dict.length / 2)]);
		mapPoints.push(new google.maps.LatLng(allAuxData[middleArrayIndex].aux_Latitude,allAuxData[middleArrayIndex].aux_Longitude));

		// get the original data from dictionary
		for(h= 1 ; h < allAuxData.length-1; h++) {

			if(h === 23)
				break

			if(h != middleArrayIndex){
				mapPoints.push(new google.maps.LatLng(allAuxData[h].aux_Latitude,allAuxData[h].aux_Longitude));
			}
		}

		console.log(mapPoints)

		
		//get start , destination and waypoints coordinates
		for (var k = 0; k < mapPoints.length; k++) {
			waypts.push({
			location: mapPoints[k],
			stopover: true
			});
		
		}
		//console.log(srcLatitude,srcLong)
		//console.log(destLatitude,destLong)				  
	}
		let source = new google.maps.LatLng(srcLatitude,srcLong)
		start = source;
		
		let destination = new google.maps.LatLng(destLatitude,destLong)
		end = destination;

		
		
		//google maps driving travel mode with direction API request
		var request = {
			origin: start,
			destination: end,
			waypoints: waypts,
			optimizeWaypoints: true,
			travelMode: google.maps.TravelMode.DRIVING,
			unitSystem: google.maps.UnitSystem.METRIC
		};
		//console.log(request)				
		directionsService.push(new google.maps.DirectionsService());
		var instance = directionsService.length - 1;
		directionsDisplay.push(new google.maps.DirectionsRenderer({
		preserveViewport: true
		}));
		directionsService[instance].route(request, function (response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
				//console.log("1@@@")
				totalDist = calculateTotalDistance(response);
				//console.log(totalDist)
				$("#"+totalDrivngDst).val((totalDist/1000).toFixed(3));
				$("#"+DrivngDist).val((totalDist/1000).toFixed(3));										 
		} else {
			alert("directions response " + status);
			$("#"+totalDrivngDst).val("0");
			$("#"+DrivngDist).val("0");	
		}
		});

		} // end of if condtion (if src and dest != null)
	
	}

function AuxAppendBOQ(auxArray,insertType,OrigiTermination,Target,indx){

	var markup = "";
	var longitude = "";
	var latitude = "";
	var length = "";
	var auxName = "";
	var auxDrivingDist = 0;
	var auxGeoDistance = 0;	
	
	var flag = 0; // the flag is needed when append to aux from map (because 2arrays are combined in one: Auxs from DB and the aux points from map.)
	console.log("Index is "+indx)

	$.each(auxArray, function (i, value) {

		if(insertType == "createFromMap"){
			
			console.log(`length is ${auxArray[indx].length}`)									  
			if(auxArray[indx].length == 11){// to know that this array coming from db
				
				longitude = value[0]
				latitude = value[1]
				length = value[2]
					
				if(value[3] =="") {
					auxName="";
				}
				//Case of wareHouse
				else if(value[3] !="null"){
					auxName = value[3]+":" +value[5]+":"+value[4];		
				}
				else {
					if (value[4].split("_")[0]=="MH" || value[4].split("_")[0]=="HH" || value[4].split("_")[0]=="DB") {
						auxName = value[4]+":" +value[5];	
					}
					else {
						auxName = value[5];
					}
			}
								
				
				auxDrivingDist = value[8];
				auxGeoDistance = value[9];
			}
			else{

				if(OrigiTermination == "NoOrigiNotermination" || OrigiTermination == "terminationOnly" ){
					longitude = MarkerArray[indx+1].position.lng();
					latitude = MarkerArray[indx+1].position.lat();
				}
				else if(OrigiTermination == "originationOnly" ||  OrigiTermination == "origi&termination" ){//note
					longitude = MarkerArray[indx].position.lng();
					latitude = MarkerArray[indx].position.lat();
				}
				else{
					longitude = MarkerArray[flag].position.lng();
					latitude = MarkerArray[flag].position.lat();
				}
				

				if (auxArray[indx][0] == 'NULL' && auxArray[indx][1] == 'NULL' && auxArray[indx][2] == 'NULL' ){
					auxName = "Auxiliary_Point "+parseInt(indx+1)
					
				}
				else {
					auxName = auxArray[indx][0]+":"+auxArray[indx][1];
					//Sequance = parseInt(index)+1
				}

				flag++
			}

		}
		else{
			longitude = value[0]
			latitude = value[1]
			length = value[2] 
			
			if(Target.Action=="Import") {
				if(value[3] ==null){
					auxName = "null";
				}
				else {
					auxName = value[3];	
				}
			}
			
			
			else {

				//Case of insert new row
				if(value[3] =="") {
					auxName="";
				}
				//Case of wareHouse
				else if(value[3] !="null" && value[3] !=null){
					auxName = value[3]+":" +value[5]+":"+value[4];		
				}
				else if (value[5].includes("Auxiliary_Point")==true) {
					auxName = value[10]+":" +value[5];	
				}
					
				else {
					if (value[4].split("_")[0]=="MH" || value[4].split("_")[0]=="HH" || value[4].split("_")[0]=="DB") {
						auxName = value[4]+":" +value[5];	
					}
					else {
						auxName = value[5];
					}
				}
			}
			
					auxDrivingDist = value[8];
					auxGeoDistance = value[9];

					auxDrivingDist = value[8] === undefined ? 0 : value[8]
					auxGeoDistance = value[9] === undefined ? 0 : value[9]
					//if(Sequance == "" || Sequance==undefined){//note same
						//Sequance = parseInt(index)+1
					//}
				}

		//note
		if(Target.target=="fiber") {
			markup += "<tr id='auxiliary_Row"+index+"'><td name='AuxFiber'><input class='rowAboveBelow' id='AuxFiber"+index+"' type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
			+"<td name='fiberSeq' class='headcol'><input name='fiberCableSeq'  class='form-control text-input' type='text' value='"+(index+1)+"' readonly/></td>"
			+"<td name='auxiliary_Name' id='auxiliary_Name"+index+"'><input id='aux_Point"+index+"'  class='form-control text-input' type='text' style='width:250px;position:relative;' value='"+auxName+"'  /></td>"
			+"<td name='auxiliary_Longitude'><input onchange='geoDistanceFlag()' id='aux_Long"+index+"'  name='aux_Long' class='form-control text-input' type='text' style='width:200px;position:relative;' value='"+longitude+"'/></td>"
			+ "<td name='auxiliary_Latitude' style='width:200px'> <input onchange='geoDistanceFlag()' id='aux_Lat"+index+"' name='aux_Lat' style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+latitude+"' /></td>"
			+"<td name='auxiliary_Length'> <input id='aux_Len"+index+"'style='width:110px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"' /></td>"
			+"<td name='auxDrivingDist'> <input id='auxDrivingDist"+index+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
			+"<td name='auxGeoDistance'> <input id='auxGeoDistance"+index+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
			+"</tr>"; 
			
			index++;
			indx=index;

		
		}
		else if (Target.target=="strand") {
			 markup += "<tr id='auxiliary_Row"+index+"'><td name='AuxStrand' ><input class='rowAboveBelowStrand' type='checkbox' id='AuxStrand"+index+"' style='position:relative;left:20px;top:10px' name='record'></td>"
						+"<td name='fiberSeq'><input name='strandSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(index+1)+"' readonly/></td>"
						+"<td name='auxiliary_NameStrand' id='auxiliary_NameStrand"+index+"'><input id='aux_PointStrand"+index+"'  class='form-control text-input' type='text' value='"+auxName+"' style='width:200px;position:relative;'/></td>"
						+"<td name='auxiliary_Longitude'><input id='auxStrand_Long"+index+"' name='auxStrand_Long'  class='form-control text-input' type='text' value='"+longitude+"' style='width:150px;position:relative;'/></td>"
						+ "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxStrand_Lat"+index+"' name='auxStrand_Lat' style='width:150px;position:relative;'  type='text' value='"+latitude+"'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='auxiliary_Length'> <input id='aux_LenStrand"+index+"'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"' /></td>"
					    +"<td name='auxDrivingDistStrand'> <input id='auxStrandDrivingDist"+index+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='auxGeoDistanceStrand'> <input id='auxStrandGeoDistance"+index+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";   
				
			 index++;
			 indx=index;


		}
		else if (Target.target=="trench") {
	
			 markup += "<tr id='auxiliaryTrench_Row"+indextrench+"'><td name='AuxTrench' ><input class='rowAboveBelowTrench' type='checkbox' id='Auxtrench"+indextrench+"' style='position:relative;left:20px;top:10px' name='record'></td>"
						+"<td name='TrenchSeq'><input name='TrenchSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(indextrench+1)+"' readonly/></td>"
						+"<td name='auxiliaryTrench_Name' id='auxiliaryTrench_Name"+indextrench+"'><input id='aux_PointTrench"+indextrench+"'  class='form-control text-input' type='text' value='"+auxName+"' style='width:200px;position:relative;'/></td>"
						+"<td name='auxiliaryTrench_Longitude'><input id='aux_Longtrench"+indextrench+"' name='aux_Longtrench'  class='form-control text-input' type='text' value='"+longitude+"' style='width:150px;position:relative;'/></td>"
						+ "<td name='auxiliaryTrench_Latitude' style='width:200px'> <input id='aux_Lattrench"+indextrench+"' name='aux_Lattrench' style='width:150px;position:relative;'  type='text' value='"+latitude+"'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='auxiliaryTrench_Length'> <input id='aux_LenTrench"+indextrench+"'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"' /></td>"
					    +"<td name='auxDrivingDistTrench'> <input id='auxTrenchDrivingDist"+indextrench+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='auxGeoDistanceTrench'> <input id='auxTrenchGeoDistance"+indextrench+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";   
				
			 indextrench++;
			 indx=indextrench;
		}
		else if (Target.target=="duct") {
			
			markup += "<tr id='auxiliaryDuct_Row"+indexduct+"'><td name='AuxDuct' ><input class='rowAboveBelowDuct' type='checkbox' id='Auxduct"+indexduct+"' style='position:relative;left:20px;top:10px' name='record'></td>"
						+"<td name='DuctSeq'><input name='DuctSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(indexduct+1)+"' readonly/></td>"
						+"<td name='auxiliaryDuct_Name' id='auxiliaryDuct_Name"+indexduct+"'><input id='aux_PointDuct"+indexduct+"'  class='form-control text-input' type='text' value='"+auxName+"' style='width:200px;position:relative;'/></td>"
						+"<td name='auxiliary_LongitudeDuct'><input id='aux_Longduct"+indexduct+"' name='aux_Longduct'  class='form-control text-input' type='text' value='"+longitude+"' style='width:150px;position:relative;'/></td>"
						+ "<td name='auxiliary_LatitudeDuct' style='width:200px'> <input id='aux_Latduct"+indexduct+"' name='aux_Latduct' style='width:150px;position:relative;'  type='text' value='"+latitude+"'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='auxiliaryDuct_Length'> <input id='aux_LenDuct"+indexduct+"'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"' /></td>"
					    +"<td name='auxDrivingDistDuct'> <input id='auxDuctDrivingDist"+indexduct+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='auxGeoDistanceDuct'> <input id='auxDuctGeoDistance"+indexduct+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";   
				
			 indexduct++;
			 indx=indexduct;
		}
		else if(Target.target=="tube") {
			 markup += "<tr id='auxiliary_Row"+index+"'><td name='AuxTube' ><input class='rowAboveBelowTube' id='AuxTube"+index+"'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
			+"<td name='fiberSeq' class='headcol'><input name='tubeSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(index+1)+"' readonly/></td>"
			+"<td name='auxiliary_NameTube' id='auxiliary_NameTube"+index+"'><input id='aux_PointTube"+index+"'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='"+auxName+"' /></td>"
			+"<td name='auxiliary_Longitude'><input id='auxTube_Long"+index+"' name='auxTube_Long' class='form-control text-input' type='text' value='"+longitude+"' style='width:150px;position:relative;' /></td>"
			+ "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxTube_Lat"+index+"' name='auxTube_Lat' style='width:150px;position:relative;' value='"+latitude+"'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		    +"<td name='auxiliary_Length'> <input id='aux_LenTube"+index+"'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"'  /></td>"
			+"<td name='auxDrivingDistTube'> <input id='auxTubeDrivingDist"+index+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		 	+"<td name='auxGeoDistanceTube'> <input id='auxTubeGeoDistance"+index+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"   
			+ "</tr>";
		
			index++;
			indx=index;
	
		}
	   
   });
	
   	if(insertType == "rowAboveChecked"){
		if($('.'+Target.rowAboveBelow+':checked').length > 0){

			var indexAbove = 0;//note
			var indexAb = 0;

			$('.'+Target.rowAboveBelow).each(function() {  //note  
				if (this.checked) {
					indexAb=indexAbove; 
				}
			
				indexAbove++;
			});
			$("#"+Target.auxiliaryTable +" tr").eq(indexAb+1).before(markup);
			
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").find('td[name="'+Target.Aux+'"]').children('input').prop("checked", false);
			
			
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").eq(indexAb).find('td[name="'+Target.Aux+'"]').children('input').prop("checked", true);
			var position = $('#'+Target.auxiliaryTable+' tr:eq('+indexAb+')').offset().top;    		
			// Scroll to new added row
			$('#'+Target.auxiliaryDiv).animate({
			scrollTop: $('#'+Target.auxiliaryDiv).scrollTop() + position
			}, 500);

			//$('table#auxiliaryTable tr:eq('+(indexAb+1)+') td:nth-child(3) input').focus();    
		}
		else{

			$("#"+Target.auxiliaryTable +" > tbody").append(markup);
			//missing lines 
		}

		newRowIndx =indx-1;
			AuxPointAutoComplete(Target.auxPtAutocomplete,Target.aux_Point+newRowIndx,Target.aux_Long+newRowIndx,Target.aux_Lat+newRowIndx,Target.SourceLat,Target.SourceLng,Target.DestinationLat,Target.DestinationLng,Target.auxiliaryTable,newRowIndx);
	
			$("input[id='"+Target.aux_Long+newRowIndx+"']").focusout(function () {
				calculateDistanceSourceDestination($("#"+Target.SourceLat).val(),$("#"+Target.SourceLng).val(),$("#"+Target.DestinationLat).val(),$("#"+Target.DestinationLng).val(),Target.auxiliaryTable);
			});
	
			$("input[id='"+Target.aux_Lat+newRowIndx+"']").focusout(function () {
				calculateDistanceSourceDestination($("#"+Target.SourceLat).val(),$("#"+Target.SourceLng).val(),$("#"+Target.DestinationLat).val(),$("#"+Target.DestinationLng).val(),Target.auxiliaryTable);
			}); 
			
			$("input[id='"+Target.aux_Point+newRowIndx+"']").focusout(function () {
				getTotalDrivingDistance(Target.TargetId,Target.SourceLat,Target.SourceLng,Target.DestinationLat,Target.DestinationLng,Target.target,Target.totalDistanceDrivg,Target.FiberDrivDist);
			});
		
		
	}
   	else if(insertType == "rowBelowChecked"){
		if($('.'+ Target.rowAboveBelow +':checked').length > 0){

			var indexBelow = 0;
			var indexBel = 0;

			$('.'+Target.rowAboveBelow).each(function() {    
				if (this.checked) { 
       				indexBel= indexBelow ;  
				}
			
				indexBelow++;
			});
			$("#"+Target.auxiliaryTable +" tr").eq(indexBel+1).after(markup);
			
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").find('td[name="'+Target.Aux+'"]').children('input').prop("checked", false);
			
			//for(var t=0;t<siteCltSrcMarkers.length;t++) {//differ from tube and missing lines
				//siteCltSrcMarkers[siteCltSrcMarkers[t].ID].setMap(null);
			//}
			
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").eq(indexBel+1).find('td[name="'+Target.Aux+'"]').children('input').prop("checked", true);
		$("#"+Target.auxiliaryTable+" tr").removeClass("ativeRecord")
    	$("#"+Target.auxiliaryTable +" > tbody").find("tr").eq(indexBel+1).addClass("ativeRecord");
		

			//$('table#auxiliaryTable tr:eq('+(indexBel+2)+') td:nth-child(3) input').focus();

			/*var position = $('#'+auxiliaryTable+' tr:eq('+indexBel+')').offset().top;    		
			// Scroll to new added row
			$('#'+auxiliaryDiv).animate({
			scrollTop: $('#'+auxiliaryDiv).scrollTop() + position
			}, 500);*/
		}
		else{

			$("#"+Target.auxiliaryTable +" > tbody").append(markup);
			//missing lines 
		}

		newRowIndx =indx-1;
		console.log(newRowIndx)
		
			AuxPointAutoComplete(Target.auxPtAutocomplete,Target.aux_Point+newRowIndx,Target.aux_Long+newRowIndx,Target.aux_Lat+newRowIndx,Target.SourceLat,Target.SourceLng,Target.DestinationLat,Target.DestinationLng,Target.auxiliaryTable,newRowIndx);
	
			$("input[id='"+Target.aux_Long+newRowIndx+"']").focusout(function () {
				calculateDistanceSourceDestination($("#"+Target.SourceLat).val(),$("#"+Target.SourceLng).val(),$("#"+Target.DestinationLat).val(),$("#"+Target.DestinationLng).val(),Target.auxiliaryTable);
			});
	
			$("input[id='"+Target.aux_Lat+newRowIndx+"']").focusout(function () {
				calculateDistanceSourceDestination($("#"+Target.SourceLat).val(),$("#"+Target.SourceLng).val(),$("#"+Target.DestinationLat).val(),$("#"+Target.DestinationLng).val(),Target.auxiliaryTable);
			});
			
			$("input[id='"+Target.aux_Point+newRowIndx+"']").focusout(function () {
				getTotalDrivingDistance(Target.TargetId,Target.SourceLat,Target.SourceLng,Target.DestinationLat,Target.DestinationLng,Target.target,Target.totalDistanceDrivg,Target.FiberDrivDist);
			});		
	}
	else if(insertType != "rowBelowChecked" && insertType != "rowAboveChecked"){
		console.log("nothing")
		$("#"+Target.auxiliaryTable +" > tbody").append(markup);
		
		//console.log("lngth is "+$("#auxiliaryTable > tbody").children().length)
		
			$("td[name='"+Target.auxiliary_Name+"']").each(function (ind) {
				AuxPointAutoComplete(Target.auxPtAutocomplete,Target.aux_Point+ind,Target.aux_Long+ind,Target.aux_Lat+ind,Target.SourceLat,Target.SourceLng,Target.DestinationLat,Target.DestinationLng,Target.auxiliaryTable,ind);
			});
				
			$("input[name='"+Target.aux_Long+"']").focusout(function () {
				calculateDistanceSourceDestination($("#"+Target.SourceLat).val(),$("#"+Target.SourceLng).val(),$("#"+Target.DestinationLat).val(),$("#"+Target.DestinationLng).val(),Target.auxiliaryTable);
			});
	
			$("input[name='"+Target.aux_Lat+"']").focusout(function () {
				calculateDistanceSourceDestination($("#"+Target.SourceLat).val(),$("#"+Target.SourceLng).val(),$("#"+Target.DestinationLat).val(),$("#"+Target.DestinationLng).val(),Target.auxiliaryTable);
			}); 
			
			/*$("td[name='"+Target.auxiliary_Name+"']").focusout(function () {
				getTotalDrivingDistance(Target.TargetId,Target.SourceLat,Target.SourceLng,Target.DestinationLat,Target.DestinationLng,Target.target,Target.totalDistanceDrivg,Target.FiberDrivDist);
			});*/
		
		
	}
	
		calculateDistanceSourceDestination($("#"+Target.SourceLat).val(),$("#"+Target.SourceLng).val(),$("#"+Target.DestinationLat).val(),$("#"+Target.DestinationLng).val(),Target.auxiliaryTable);
	
		$("table[id='"+Target.auxiliaryTable+"'] tr").focusin(function () {
			$("table[id='"+Target.auxiliaryTable+"'] tr").removeClass("ativeRecord")
			  $(this).addClass("ativeRecord");
		});
	
		/*objDiv = document.getElementById("auxiliaryTable");
		objDiv.scrollTop = objDiv.scrollHeight;*/
	
		//getTotalDrivingDistance(Target.TargetId,Target.SourceLat,Target.SourceLng,Target.DestinationLat,Target.DestinationLng,Target.target,Target.totalDistanceDrivg,Target.FiberDrivDist);
	
	
}

function TubeStrandAuxAppendBOQ(pathArray,insertType,Target,indx) {
	var markup = "";
	var longitude = "";
	var latitude = "";
	var length = "";
	var auxName = "";
	var auxDrivingDist = 0;
	var auxGeoDistance = 0;	
	
	if(Target.target=="fiberTube") {
		var indxPathForAuxs=indexTubeForAuxs;
	}
	else {
		var indxPathForAuxs=indexStrandForAuxs;
	}

	//$.each(pathArray, function (i, value) {
	if(Target.Action=="Import") {
		$.each(pathArray, function (i, value) {
			longitude = value[0];
			latitude = value[1];
			length = value[2];
					
			if(value[3] ==null){
				auxName = "null";
			}
			else {
				auxName = value[3];	
			}
					
			if(Target.target=="fiberTube") {

				 markup += "<tr id='tubeAux_Row"+indexTubeAux+"'><td name='AuxFiberTube' ><input class='rowAboveBelowFiberTube' id='AuxFiberTube"+indexTubeAux+"'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
						+"<td name='fiberTubeAuxSeq' class='headcol' ><input name='tubeAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(indexTubeAux+1)+"' readonly/></td>"
						+"<td name='TubeAuxiliary' id='TubeAuxiliary"+indexTubeAux+"' ><input id='tubeAuxiliary"+indexTubeAux+"'  class='form-control text-input' type='text' style='width:200px;position:relative;'  value='"+auxName+"' /></td>"
						+"<td name='TubeAuxiliary_Longitude'><input id='tubeAuxiliary_Lng"+indexTubeAux+"' name='tubeAuxiliary_Lng' class='form-control text-input' type='text' style='width:150px;position:relative;'  value='"+longitude+"' /></td>"
						+ "<td name='TubeAuxiliary_Latitude' style='width:200px'> <input id='tubeAuxiliary_Lat"+indexTubeAux+"' name = 'tubeAuxiliary_Lat' style='width:150px;position:relative;'  type='text' value='"+latitude+"'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='TubeAuxiliaryLng'> <input id='tubeAuxiliary_Len"+indexTubeAux+"'style='width:150px;position:relative;'  readonly type='text' value='"+length+"'   class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				 		+"<td name='auxDrivingDistFiberTube'> <input id='auxFiberTubeDrivingDist"+indexTubeAux+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				 		+"<td name='auxGeoDistanceFiberTube'> <input id='auxFiberTubeGeoDistance"+indexTubeAux+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"   
				 		+ "</tr>";

				 indexTubeAux++;
				 indx=indexTubeAux;			
			}	
			else if(Target.target=="fiberStrand") {				

				 markup += "<tr id='strandAux_Row"+indexStrandAux+"' ><td name='AuxFiberStrand' ><input class='rowAboveBelowFiberStrand' id='AuxFiberStrand"+indexStrandAux+"' type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
						+"<td name='fiberStrandAuxSeq' class='headcol' ><input name='strandAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(indexStrandAux+1)+"' readonly/></td>"
						+"<td name='StrandAuxiliary' id='StrandAuxiliary"+indexStrandAux+"' ><input id='strandAuxiliary"+indexStrandAux+"'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='"+auxName+"' /></td>"
						+"<td name='StrandAuxiliary_Longitude'><input id='strandAuxiliary_Lng"+indexStrandAux+"' name='strandAuxiliary_Lng'  class='form-control text-input' type='text' style='width:150px;position:relative;' value='"+longitude+"' /></td>"
						+ "<td name='StrandAuxiliary_Latitude' style='width:200px'> <input id='strandAuxiliary_Lat"+indexStrandAux+"' name = 'strandAuxiliary_Lat' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'  value='"+latitude+"' /></td>"
					    +"<td name='StrandAuxiliary_Length'> <input id='strandAuxiliary_Len"+indexStrandAux+"'style='width:150px;position:relative;'  readonly type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"' /></td>"
					    +"<td name='auxDrivingDistFiberStrand'> <input id='auxFiberStrandDrivingDist"+indexStrandAux+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
						+"<td name='auxGeoDistanceFiberStrand'> <input id='auxFiberStrandGeoDistance"+indexStrandAux+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"   
					    + "</tr>";
				
				indexStrandAux++;
				indx=indexStrandAux;			
			}			
		});	
	
	}
	
	else {
	for(i=0;i<pathArray.length;i++){																
		if(insertType=="rowAboveChecked" || insertType=="rowBelowChecked") {
			 longitude = "";
			 latitude = "";
			 length = "";
			 auxName = "";
		}
		else {
			longitude = pathArray[i].aux_Longitude;
			latitude = pathArray[i].aux_Latitude;
			length = pathArray[i].distance_From_Source;			
			auxName = pathArray[i].aux_Name;	
			auxDrivingDist = pathArray[i].drivingDistance === undefined ? 0 : pathArray[i].drivingDistance;
			auxGeoDistance = pathArray[i].geoDistance === undefined ? 0 : pathArray[i].geoDistance;
		}
		if(Target.target=="fiberTube") {
			markup += "<tr id='tubeAux_Row"+indexTubeAux+"'><td name='AuxFiberTube' ><input class='rowAboveBelowFiberTube' id='AuxFiberTube"+indexTubeAux+"'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
						+"<td name='fiberTubeAuxSeq' class='headcol' ><input name='tubeAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(indexTubeAux+1)+"' readonly/></td>"
						+"<td name='TubeAuxiliary' id='TubeAuxiliary"+indexTubeAux+"' ><input id='tubeAuxiliary"+indexTubeAux+"'  class='form-control text-input' type='text' style='width:200px;position:relative;'  value='"+auxName+"' /></td>"
						+"<td name='TubeAuxiliary_Longitude'><input id='tubeAuxiliary_Lng"+indexTubeAux+"' name='tubeAuxiliary_Lng' class='form-control text-input' type='text' style='width:150px;position:relative;'  value='"+longitude+"' /></td>"
						+ "<td name='TubeAuxiliary_Latitude' style='width:200px'> <input id='tubeAuxiliary_Lat"+indexTubeAux+"' name = 'tubeAuxiliary_Lat' style='width:150px;position:relative;'  type='text' value='"+latitude+"'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					    +"<td name='TubeAuxiliaryLng'> <input id='tubeAuxiliary_Len"+indexTubeAux+"'style='width:150px;position:relative;'  readonly type='text' value='"+length+"'   class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
						+"<td name='auxDrivingDistFiberTube'> <input id='auxFiberTubeDrivingDist"+indexTubeAux+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				 		+"<td name='auxGeoDistanceFiberTube'> <input id='auxFiberTubeGeoDistance"+indexTubeAux+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"   
				 		+ "</tr>";
				 indexTubeAux++;
				 indx=indexTubeAux;			
		}
		else if(Target.target=="fiberStrand") {
			
			 markup += "<tr id='strandAux_Row"+indexStrandAux+"' ><td name='AuxFiberStrand' ><input class='rowAboveBelowFiberStrand' id='AuxFiberStrand"+indexStrandAux+"' type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
					+"<td name='fiberStrandAuxSeq' class='headcol' ><input name='strandAuxSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(indexStrandAux+1)+"' readonly/></td>"
					+"<td name='StrandAuxiliary' id='StrandAuxiliary"+indexStrandAux+"' ><input id='strandAuxiliary"+indexStrandAux+"'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='"+auxName+"' /></td>"
					+"<td name='StrandAuxiliary_Longitude'><input id='strandAuxiliary_Lng"+indexStrandAux+"' name='strandAuxiliary_Lng'  class='form-control text-input' type='text' style='width:150px;position:relative;' value='"+longitude+"' /></td>"
					+ "<td name='StrandAuxiliary_Latitude' style='width:200px'> <input id='strandAuxiliary_Lat"+indexStrandAux+"' name = 'strandAuxiliary_Lat' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'  value='"+latitude+"' /></td>"
				    +"<td name='StrandAuxiliary_Length'> <input id='strandAuxiliary_Len"+indexStrandAux+"'style='width:150px;position:relative;'  readonly type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"' /></td>"
				    +"<td name='auxDrivingDistFiberStrand'> <input id='auxFiberStrandDrivingDist"+indexStrandAux+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
					+"<td name='auxGeoDistanceFiberStrand'> <input id='auxFiberStrandGeoDistance"+indexStrandAux+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"   
				    + "</tr>";
			
			indexStrandAux++;
			indx=indexStrandAux;			
		}		
	}
	}
	if(insertType == "rowAboveChecked"){
		if($('.'+Target.rowAboveBelow+':checked').length > 0){
			var indexAbove = 0;
			var indexAb = 0;

			$('.'+Target.rowAboveBelow).each(function() {  //note  
				if (this.checked) {
					indexAb=indexAbove; 
				}
			
				indexAbove++;
			});					
			$("#"+Target.auxiliaryTable +" tr").eq(indexAb+1).before(markup);
			
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").find('td[name="'+Target.Aux+'"]').children('input').prop("checked", false);
			
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").eq(indexAb).find('td[name="'+Target.Aux+'"]').children('input').prop("checked", true);
			var position = $('#'+Target.auxiliaryTable+' tr:eq('+indexAb+')').offset().top;    		
			// Scroll to new added row
			$('#'+Target.auxiliaryDiv).animate({
			scrollTop: $('#'+Target.auxiliaryDiv).scrollTop() + position
			}, 500);					

		}
		else{
			$("#"+Target.auxiliaryTable +" > tbody").append(markup);
		}
		newRowIndx =indx-1;
		
		AuxPointAutoComplete(Target.auxPtAutocomplete,Target.aux_Point+newRowIndx,Target.aux_Long+newRowIndx,Target.aux_Lat+newRowIndx,"","","","",Target.auxiliaryTable,newRowIndx);
		
		$("input[id='"+Target.aux_Long+newRowIndx+"']").focusout(function () {
			calculateDistanceAuxTube($("#"+Target.SourceLat+indxPathForAuxs).val(),$("#"+Target.SourceLng+indxPathForAuxs).val(),$("#"+Target.DestinationLat+indxPathForAuxs).val(),$("#"+Target.DestinationLng+indxPathForAuxs).val(),Target.auxiliaryTable,$("#"+Target.TargetId+indxPathForAuxs).val(),indxPathForAuxs);
		});
		$("input[id='"+Target.aux_Lat+newRowIndx+"']").focusout(function () {
			calculateDistanceAuxTube($("#"+Target.SourceLat+indxPathForAuxs).val(),$("#"+Target.SourceLng+indxPathForAuxs).val(),$("#"+Target.DestinationLat+indxPathForAuxs).val(),$("#"+Target.DestinationLng+indxPathForAuxs).val(),Target.auxiliaryTable,$("#"+Target.TargetId+indxPathForAuxs).val(),indxPathForAuxs);
		});
		
	}
	else if(insertType == "rowBelowChecked"){
		if($('.'+ Target.rowAboveBelow +':checked').length > 0){
			var indexBelow = 0;
			var indexBel = 0;

			$('.'+Target.rowAboveBelow).each(function() {    
				if (this.checked) { 
       				indexBel= indexBelow ;  
				}
				indexBelow++;
			});				
			$("#"+Target.auxiliaryTable +" tr").eq(indexBel+1).after(markup);
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").find('td[name="'+Target.Aux+'"]').children('input').prop("checked", false);
			
			$("#"+Target.auxiliaryTable +" >tbody").find("tr").eq(indexBel+1).find('td[name="'+Target.Aux+'"]').children('input').prop("checked", true);
			$("#"+Target.auxiliaryTable+" tr").removeClass("ativeRecord")
			$("#"+Target.auxiliaryTable +" > tbody").find("tr").eq(indexBel+1).addClass("ativeRecord");
		}
		else{
			$("#"+Target.auxiliaryTable +" > tbody").append(markup);
		}					
		newRowIndx =indx-1;
		AuxPointAutoComplete(Target.auxPtAutocomplete,Target.aux_Point+newRowIndx,Target.aux_Long+newRowIndx,Target.aux_Lat+newRowIndx,"","","","",Target.auxiliaryTable,newRowIndx);

		$("input[id='"+Target.aux_Long+newRowIndx+"']").focusout(function () {
			calculateDistanceAuxTube($("#"+Target.SourceLat+indxPathForAuxs).val(),$("#"+Target.SourceLng+indxPathForAuxs).val(),$("#"+Target.DestinationLat+indxPathForAuxs).val(),$("#"+Target.DestinationLng+indxPathForAuxs).val(),Target.auxiliaryTable,$("#"+Target.TargetId+indxPathForAuxs).val(),indxPathForAuxs);
		});
		$("input[id='"+Target.aux_Lat+newRowIndx+"']").focusout(function () {
			calculateDistanceAuxTube($("#"+Target.SourceLat+indxPathForAuxs).val(),$("#"+Target.SourceLng+indxPathForAuxs).val(),$("#"+Target.DestinationLat+indxPathForAuxs).val(),$("#"+Target.DestinationLng+indxPathForAuxs).val(),Target.auxiliaryTable,$("#"+Target.TargetId+indxPathForAuxs).val(),indxPathForAuxs);
		});
		
	}
	else if(insertType != "rowBelowChecked" && insertType != "rowAboveChecked"){
		console.log("nothing")

		$("#"+Target.auxiliaryTable +" > tbody").append(markup);
				
		calculateDistanceAuxTube($("#"+Target.SourceLat+indxPathForAuxs).val(),$("#"+Target.SourceLng+indxPathForAuxs).val(),$("#"+Target.DestinationLat+indxPathForAuxs).val(),$("#"+Target.DestinationLng+indxPathForAuxs).val(),Target.auxiliaryTable,$("#"+Target.TargetId+indxPathForAuxs).val(),indxPathForAuxs);
		
		$("td[name='"+Target.auxiliary_Name+"']").each(function (ind) {
			AuxPointAutoComplete(Target.auxPtAutocomplete,Target.aux_Point+ind,Target.aux_Long+ind,Target.aux_Lat+ind,"","","","",Target.auxiliaryTable,ind);
		});
		
		$("input[name='"+Target.aux_Long+"']").focusout(function () {
			calculateDistanceAuxTube($("#"+Target.SourceLat+indxPathForAuxs).val(),$("#"+Target.SourceLng+indxPathForAuxs).val(),$("#"+Target.DestinationLat+indxPathForAuxs).val(),$("#"+Target.DestinationLng+indxPathForAuxs).val(),Target.auxiliaryTable,$("#"+Target.TargetId+indxPathForAuxs).val(),indxPathForAuxs);
		});
		$("input[name='"+Target.aux_Lat+"']").focusout(function () {
			calculateDistanceAuxTube($("#"+Target.SourceLat+indxPathForAuxs).val(),$("#"+Target.SourceLng+indxPathForAuxs).val(),$("#"+Target.DestinationLat+indxPathForAuxs).val(),$("#"+Target.DestinationLng+indxPathForAuxs).val(),Target.auxiliaryTable,$("#"+Target.TargetId+indxPathForAuxs).val(),indxPathForAuxs);
		});
					
	}
	
	$("table[id='"+Target.auxiliaryTable+"'] tr").focusin(function () {
		$("table[id='"+Target.auxiliaryTable+"'] tr").removeClass("ativeRecord")
		  $(this).addClass("ativeRecord");
	});	
}


/* to be deleted 
function AuxAppendBOQ(auxArray,insertType,OrigiTermination){

	let markup = ""

	let longitude = ""
	let latitude = ""
	let length = ""
	let auxName = ""
	let fiberCableSeq = ""
	let auxDrivingDist = 0
	let auxGeoDistance = 0

	let flag = 0; // the flag is needed when append to aux from map (because 2arrays are combined in one: Auxs from DB and the aux points from map.)
//	console.log("auxArray is "+auxArray)

	$.each(auxArray, function (i, value) {

		if(insertType == "createFromMap"){
			
			console.log(`length is ${auxArray[index].length}`)									  
			if(auxArray[index].length == 11){// to know that this array coming from db
				
				longitude = value[0]
				latitude = value[1]
				length = value[2]
					
				
				if(value[3] =="") {
					auxName="";
				}
				//Case of wareHouse
				else if(value[3] !="null"){
					auxName = value[3]+":" +value[5]+":"+value[4];		
				}
				else if (value[5].includes("Auxiliary_Point")==true) {
					auxName = value[10]+":" +value[5];	
				}
				else {
					if (value[4].split("_")[0]=="MH" || value[4].split("_")[0]=="HH" || value[4].split("_")[0]=="DB") {
						auxName = value[4]+":" +value[5];	
					}
					else {
						auxName = value[5];
					}
			}
								
				fiberCableSeq = value[7];
				auxDrivingDist = value[8];
				auxGeoDistance = value[9];
			}
			else{

				if(OrigiTermination == "NoOrigiNotermination" || OrigiTermination == "terminationOnly" ){
					longitude = MarkerArray[index+1].position.lng();
					latitude = MarkerArray[index+1].position.lat();
				}
				else if(OrigiTermination == "originationOnly" ||  OrigiTermination == "origi&termination" ){
					longitude = MarkerArray[index].position.lng();
					latitude = MarkerArray[index].position.lat();
				}
				else{
					longitude = MarkerArray[flag].position.lng();
					latitude = MarkerArray[flag].position.lat();
				}
				

				if (auxArray[index][0] == 'NULL' && auxArray[index][1] == 'NULL' && auxArray[index][2] == 'NULL' ){
					//console.log("here")
					auxName = "Auxiliary_Point "+parseInt(index+1)
					fiberCableSeq = parseInt(index)+1
				}
				else {
					//console.log("there")
					auxName = auxArray[index][0]+":"+auxArray[index][1];
					fiberCableSeq = parseInt(index)+1
				}

				flag++
			}
			

		}
		else{
			longitude = value[0]
			latitude = value[1]
			length = value[2] 
			//auxName = value[3]
					
			if(fiberAction=="Import") {
				if(value[3] ==null){
					auxName = "null";
				}
				else {
					auxName = value[3];	
				}
			}
			/*else if(insertType == "createFromTree"){
				//auxName = value[3];	
				if(value[3] =="") {
					auxName="";
				}
				//Case of wareHouse
				else if(value[3] !="null" && value[3] !=null){
					auxName = value[3]+":" +value[5]+":"+value[4];		
				}
				else if (value[5].includes("Auxiliary_Point")==true) {
					auxName = value[10]+":" +value[5];	
				}
					
				else {
					if (value[4].split("_")[0]=="MH" || value[4].split("_")[0]=="HH" || value[4].split("_")[0]=="DB") {
						auxName = value[4]+":" +value[5];	
					}
					else {
						auxName = value[5];
					}
				}

			}
			*/
		/*	else {

				//Case of insert new row
				if(value[3] =="") {
					auxName="";
				}
				//Case of wareHouse
				else if(value[3] !="null" && value[3] !=null){
					auxName = value[3]+":" +value[5]+":"+value[4];		
				}
				else if (value[5].includes("Auxiliary_Point")==true) {
					auxName = value[10]+":" +value[5];	
				}
					
				else {
					if (value[4].split("_")[0]=="MH" || value[4].split("_")[0]=="HH" || value[4].split("_")[0]=="DB") {
						auxName = value[4]+":" +value[5];	
					}
					else {
						auxName = value[5];
					}
				}
			}
			
					fiberCableSeq = value[7];
					auxDrivingDist = value[8];
					auxGeoDistance = value[9];

					auxDrivingDist = value[8] === undefined ? 0 : value[8]
					auxGeoDistance = value[9] === undefined ? 0 : value[9]
					if(fiberCableSeq == "" || fiberCableSeq==undefined){
						fiberCableSeq = parseInt(index)+1
					}
				}


		markup += "<tr id='auxiliary_Row"+index+"'><td name='AuxFiber'><input class='rowAboveBelow' id='AuxFiber"+index+"' type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
		+"<td name='fiberSeq' class='headcol'><input name='fiberCableSeq'  class='form-control text-input' type='text' value='"+fiberCableSeq+"' readonly/></td>"
		+"<td name='auxiliary_Name' id='auxiliary_Name"+index+"'><input id='aux_Point"+index+"'  class='form-control text-input' type='text' style='width:250px;position:relative;' value='"+auxName+"'  /></td>"
		+"<td name='auxiliary_Longitude'><input onchange='geoDistanceFlag()' id='aux_Long"+index+"'  name='aux_Long' class='form-control text-input' type='text' style='width:200px;position:relative;' value='"+longitude+"'/></td>"
		+ "<td name='auxiliary_Latitude' style='width:200px'> <input onchange='geoDistanceFlag()' id='aux_Lat"+index+"' name='aux_Lat' style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+latitude+"' /></td>"
		+"<td name='auxiliary_Length'> <input id='aux_Len"+index+"'style='width:110px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"' /></td>"
		+"<td name='auxDrivingDist'> <input id='auxDrivingDist"+index+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		+"<td name='auxGeoDistance'> <input id='auxGeoDistance"+index+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		+"</tr>"; 

		index++
	   
   });


   
   	if(insertType == "rowAboveChecked"){
		if($('.rowAboveBelow:checked').length > 0){

			let indexAbove = 0;
			let indexAb = 0;

			$('.rowAboveBelow').each(function() {    
				if (this.checked) {
					indexAb=indexAbove; 
				}
			
				indexAbove++;
			});
			$("#auxiliaryTable tr").eq(indexAb+1).before(markup);
			
			$("#auxiliaryTable >tbody").find("tr").find('td[name="AuxFiber"]').children('input').prop("checked", false);
			for(var t=0;t<siteCltSrcMarkers.length;t++) {
				siteCltSrcMarkers[siteCltSrcMarkers[t].ID].setMap(null);
			}
			
			$("#auxiliaryTable >tbody").find("tr").eq(indexAb).find('td[name="AuxFiber"]').children('input').prop("checked", true);
		

			//$('table#auxiliaryTable tr:eq('+(indexAb+1)+') td:nth-child(3) input').focus();    
		}
		else{

			$("#auxiliaryTable > tbody").append(markup);
		}

		newRowIndx =index-1;

		AuxPointAutoComplete("auxPtAutocomplete","aux_Point"+newRowIndx,"aux_Long"+newRowIndx,"aux_Lat"+newRowIndx,"SourceLat","SourceLng","DestinationLat","DestinationLng","auxiliaryTable",newRowIndx);

		$("input[id='aux_Long"+newRowIndx+"']").focusout(function () {
			calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
		});

		$("input[id='aux_Lat"+newRowIndx+"']").focusout(function () {
			calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
		}); 

	}
	if(insertType == "rowBelowChecked"){
		if($('.rowAboveBelow:checked').length > 0){

			let indexBelow = 0;
			let indexBel = 0;

			$('.rowAboveBelow').each(function() {    
				if (this.checked) { 
       				indexBel= indexBelow ;  
				}
			
				indexBelow++;
			});
			$("#auxiliaryTable tr").eq(indexBel+1).after(markup);
			
			$("#auxiliaryTable >tbody").find("tr").find('td[name="AuxFiber"]').children('input').prop("checked", false);
			
			for(var t=0;t<siteCltSrcMarkers.length;t++) {
				siteCltSrcMarkers[siteCltSrcMarkers[t].ID].setMap(null);
			}
			
			$("#auxiliaryTable >tbody").find("tr").eq(indexBel+1).find('td[name="AuxFiber"]').children('input').prop("checked", true);
		

			//$('table#auxiliaryTable tr:eq('+(indexBel+2)+') td:nth-child(3) input').focus();

			/*var position = $('#auxiliaryTable tr:eq('+indexBel+')').offset().top;    		
			// Scroll to new added row
			$('#auxiliaryDiv').animate({
			scrollTop: $('#auxiliaryDiv').scrollTop() + position
			}, 500);*/
	/*	}
		else{

			$("#auxiliaryTable > tbody").append(markup);
		}

		newRowIndx =index-1;
		console.log(newRowIndx)

		AuxPointAutoComplete("auxPtAutocomplete","aux_Point"+newRowIndx,"aux_Long"+newRowIndx,"aux_Lat"+newRowIndx,"SourceLat","SourceLng","DestinationLat","DestinationLng","auxiliaryTable",newRowIndx);

		$("input[id='aux_Long"+newRowIndx+"']").focusout(function () {
			calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
		});

		$("input[id='aux_Lat"+newRowIndx+"']").focusout(function () {
			calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
		});
		
	}
	else if(insertType != "rowBelowChecked" && insertType != "rowAboveChecked"){
		console.log("nothing")
		$("#auxiliaryTable > tbody").append(markup);
		
		console.log("lngth is "+$("#auxiliaryTable > tbody").children().length)

		$("td[name='auxiliary_Name']").each(function (ind) {
			AuxPointAutoComplete("auxPtAutocomplete","aux_Point"+ind,"aux_Long"+ind,"aux_Lat"+ind,"SourceLat","SourceLng","DestinationLat","DestinationLng","auxiliaryTable",ind);
		});
			
		$("input[name='aux_Long']").focusout(function () {
			calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
		});

		$("input[name='aux_Lat']").focusout(function () {
			calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");
		}); 
	}

	calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");

	$("table[id='auxiliaryTable'] tr").focusin(function () {
		$("table[id='auxiliaryTable'] tr").removeClass("ativeRecord")
		  $(this).addClass("ativeRecord");
	});

	/*objDiv = document.getElementById("auxiliaryTable");
	objDiv.scrollTop = objDiv.scrollHeight;*/

	//getTotalDrivingDist()
/*	getTotalDrivingDistance("FiberPathId","SourceLat","SourceLng","DestinationLat","DestinationLng","fiber","totalDistanceDrivg","FiberDrivDist");

}*/
/* to be deleted 
function TubeAuxAppendBOQ(auxArray,insertType,OrigiTermination){

var markup = "";
var longitude = "";
var latitude = "";
var length = "";
var auxName = "";
var tubeSeq = "";
var auxDrivingDist = 0;
var auxGeoDistance = 0;


var flag = 0; // the flag is needed when append to aux from map (because 2arrays are combined in one: Auxs from DB and the aux points from map.)
$.each(auxArray, function (i, value) {

	//console.log(`length is ${auxArray[index].length}`)									  

	if(insertType == "createFromMap"){
		
		if(auxArray[index].length == 11){// Append to auxiliary from map case 
			
			longitude = value[0];
			latitude = value[1];
			length = value[2];
				
			if(value[3] =="") {
				auxName="";
			}
			//Case of wareHouse
			else if(value[3] !="null"){
				auxName = value[3]+":" +value[5]+":"+value[4];		
			}
			else {
				if (value[4].split("_")[0]=="MH" || value[4].split("_")[0]=="HH" || value[4].split("_")[0]=="DB") {
					auxName = value[4]+":" +value[5];	
				}
				else {
					auxName = value[5];
				}
		}
							
			tubeSeq = value[7];
			auxDrivingDist = value[8];
			auxGeoDistance = value[9];
		}
		else{

			if(OrigiTermination == "NoOrigiNotermination" || OrigiTermination == "terminationOnly" ){
				longitude = MarkerArray[index+1].position.lng();
				latitude = MarkerArray[index+1].position.lat();
			}
			else if(OrigiTermination == "originationOnly" || OrigiTermination == "originationTermination"){
				longitude = MarkerArray[index].position.lng();
				latitude = MarkerArray[index].position.lat();
			}
			else{
				longitude = MarkerArray[flag].position.lng();
				latitude = MarkerArray[flag].position.lat();
			}
			
			if (auxArray[index][0] == 'NULL' && auxArray[index][1] == 'NULL' && auxArray[index][2] == 'NULL' ){
				auxName = "Auxiliary_Point "+parseInt(index+1)
				tubeSeq = parseInt(index)+1
			}
			else {
				auxName = auxArray[index][0]+":"+auxArray[index][1];
				tubeSeq = parseInt(index)+1
			}

			flag++
		}
		
	/*	longitude = MarkerArray[index+1].position.lng();
		latitude = MarkerArray[index+1].position.lat();
		

		if (auxArray[index][0] == 'NULL' && auxArray[index][1] == 'NULL' && auxArray[index][2] == 'NULL' ){
			auxName = "Auxiliary_Point "+parseInt(index+1)
			tubeSeq = parseInt(index)+1
		}
		else {
			auxName = auxArray[index][0]+":"+auxArray[index][1];
			tubeSeq = parseInt(index)+1
		}
		*/
		
		
/*	}
	else{
		
		longitude = value[0];
		latitude = value[1];
		length = value[2];
				
		if(tubeAction=="Import") {
			if(value[3] ==null){
				auxName = "null";
			}
			else {
				auxName = value[3];	
			}
		}
		else {

			if(value[3] =="") {
				auxName="";
			}
			//Case of wareHouse
			else if(value[3] !="null" && value[3] !=null){
				auxName = value[3]+":" +value[5]+":"+value[4];		
			}
			else if (value[5].includes("Auxiliary_Point")==true) {
				auxName = value[10]+":" +value[5];	
			}
				
			else {
				if (value[4].split("_")[0]=="MH" || value[4].split("_")[0]=="HH" || value[4].split("_")[0]=="DB") {
					auxName = value[4]+":" +value[5];	
				}
				else {
					auxName = value[5];
				}
			}
		}
		
		tubeSeq = value[4];
		if(tubeSeq == "" || tubeSeq==undefined){
			tubeSeq = parseInt(index)+1
		}
	
		auxDrivingDist = value[8];
		auxGeoDistance = value[9];

		auxDrivingDist = value[8] === undefined ? 0 : value[8];
		auxGeoDistance = value[9] === undefined ? 0 : value[9];
			
		/*if(fiberCableSeq == "" || fiberCableSeq==undefined){
					fiberCableSeq = parseInt(index)+1
				}
				*/
/*	}// end else create from map

	 markup += "<tr id='auxiliary_Row"+index+"'><td name='AuxTube' ><input class='rowAboveBelowTube' id='AuxTube"+index+"'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
			+"<td name='fiberSeq' class='headcol'><input name='tubeSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+(index+1)+"' readonly/></td>"
			+"<td name='auxiliary_NameTube' id='auxiliary_NameTube"+index+"'><input id='aux_PointTube"+index+"'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='"+auxName+"' /></td>"
			+"<td name='auxiliary_Longitude'><input id='auxTube_Long"+index+"' name='auxTube_Long' class='form-control text-input' type='text' value='"+longitude+"' style='width:150px;position:relative;' /></td>"
			+ "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxTube_Lat"+index+"' name='auxTube_Lat' style='width:150px;position:relative;' value='"+latitude+"'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		    +"<td name='auxiliary_Length'> <input id='aux_LenTube"+index+"'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='"+length+"'  /></td>"
			+"<td name='auxDrivingDistTube'> <input id='auxTubeDrivingDist"+index+"' value='"+auxDrivingDist+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		 	+"<td name='auxGeoDistanceTube'> <input id='auxTubeGeoDistance"+index+"' value='"+auxGeoDistance+"' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"   
			+ "</tr>";

	 index++;

});

if(insertType == "rowBelowChecked"){
	if($('.rowAboveBelowTube:checked').length > 0){

		var indexBelow = 0;
		var indexBel = 0;

		$('.rowAboveBelowTube').each(function() {    
			if (this.checked) { 
   				indexBel= indexBelow ;  
			}
			indexBelow++;
		});
		
		$("#auxiliaryTableTubes tr").eq(indexBel+1).after(markup);
		$("#auxiliaryTableTubes >tbody").find("tr").find('td[name="AuxTube"]').children('input').prop("checked", false);			
		$("#auxiliaryTableTubes >tbody").find("tr").eq(indexBel+1).find('td[name="AuxTube"]').children('input').prop("checked", true);
	
		
		var position = $('#auxiliaryTableTubes tr:eq('+indexBel+')').offset().top;    		
    	// Scroll to added row
		 $('#auxiliaryDivTube').animate({
		     scrollTop: $('#auxiliaryDivTube').scrollTop() + position
		 }, 500);
		
		// $('table#auxiliaryTableTubes tr:eq('+(indexBel+2)+') td:nth-child(3) input').focus();	
	  	
	}
	else{
		$("#auxiliaryTableTubes > tbody").append(markup);
		//$('table#auxiliaryTableTubes tr:eq('+(index)+') td:nth-child(3) input').focus();   
		
		 objDiv = document.getElementById("auxiliaryTableTubes");
		 objDiv.scrollTop = objDiv.scrollHeight;
	}
	
	newRowIndx =index-1;
	AuxPointAutoComplete("auxPtTubeAutocomplete","aux_PointTube"+newRowIndx,"auxTube_Long"+newRowIndx,"auxTube_Lat"+newRowIndx,"sourcelat","sourcelong","destinationlat","destinationlong","auxiliaryTableTubes",newRowIndx);

	$("input[id='auxTube_Long"+newRowIndx+"']").focusout(function () {
		calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");
	});

	$("input[id='auxTube_Lat"+newRowIndx+"']").focusout(function () {
		calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");
	}); 	
}
else if(insertType == "rowAboveChecked"){
	if($('.rowAboveBelowTube:checked').length > 0){

		var indexAbove = 0;
		var indexAb = 0;

		$('.rowAboveBelowTube').each(function() {    
			if (this.checked) {
				indexAb=indexAbove; 
			}
			indexAbove++;
		});
		$("#auxiliaryTableTubes tr").eq(indexAb+1).before(markup);
		
		$("#auxiliaryTableTubes >tbody").find("tr").find('td[name="AuxTube"]').children('input').prop("checked", false);
		
		$("#auxiliaryTableTubes >tbody").find("tr").eq(indexAb).find('td[name="AuxTube"]').children('input').prop("checked", true);
		
		//Scroll to the added row
		var position = $('#auxiliaryTableTubes tr:eq('+indexAb+')').offset().top;    		
		$('#auxiliaryTableTubes').animate({
		      scrollTop: $('#auxiliaryTableTubes').scrollTop() + position
		}, 500);
		
		//$('table#auxiliaryTableTubes tr:eq('+(indexAb+1)+') td:nth-child(3) input').focus();
		
	}
	else{

		$("#auxiliaryTableTubes > tbody").append(markup);
		//$('table#auxiliaryTableTubes tr:eq('+(index)+') td:nth-child(3) input').focus();
		//Scroll to the added row
		objDiv = document.getElementById("auxiliaryTableTubes");
		objDiv.scrollTop = objDiv.scrollHeight;
    	

	}

	newRowIndx =index-1;
	
	AuxPointAutoComplete("auxPtTubeAutocomplete","aux_PointTube"+newRowIndx,"auxTube_Long"+newRowIndx,"auxTube_Lat"+newRowIndx,"sourcelat","sourcelong","destinationlat","destinationlong","auxiliaryTableTubes",newRowIndx);

	$("input[id='auxTube_Long"+newRowIndx+"']").focusout(function () {
		calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");
	});

	$("input[id='auxTube_Lat"+newRowIndx+"']").focusout(function () {
		calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");
	}); 

}
else if(insertType != "rowBelowChecked" && insertType != "rowAboveChecked"){
	console.log("nothing")
	$("#auxiliaryTableTubes > tbody").append(markup);
	
	$("td[name='auxiliary_NameTube']").each(function (indTubee) {
		AuxPointAutoComplete("auxPtTubeAutocomplete","aux_PointTube"+indTubee,"auxTube_Long"+indTubee,"auxTube_Lat"+indTubee,"sourcelat","sourcelong","destinationlat","destinationlong","auxiliaryTableTubes",indTubee);
	});
		
	$("input[name='auxTube_Long']").focusout(function () {
		calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");
	});

	$("input[name='auxTube_Lat']").focusout(function () {
		calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");
	}); 
	
}

calculateDistanceSourceDestination($("#sourcelat").val(),$("#sourcelong").val(),$("#destinationlat").val(),$("#destinationlong").val(),"auxiliaryTableTubes");

$("table[id='auxiliaryTableTubes'] tr").focusin(function () {
	$("table[id='auxiliaryTableTubes'] tr").removeClass("ativeRecord")
	  $(this).addClass("ativeRecord");
});


//getTotalDrivingDist();
getTotalDrivingDistance("TubeID","sourcelat","sourcelong","destinationlat","destinationlong","tube","tubeTotalDistanceDrivg","tubeDrivDist");

}
*/


function fiberAuxiliary_BoqAppendMarkup(name="",long="",lat="",seqFiber="",auxlength="",auxDrivingDist="",auxGeoDistance=""){

			//console.log(`seqFiber is ${seqFiber}`)
    	  var markup = "<tr><td><input type='checkbox' class='rowAboveBelow' id='AuxFiber"+index+"' style='position:relative;left:20px;top:10px' name='record'></td>"
			+"<td name='fiberSeq'><input name='fiberCableSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+seqFiber+"' readonly/></td>"
			+"<td name='auxiliary_Name'><input id='aux_Point"+index+"' value='"+name+"' class='form-control text-input' type='text' style='width:250px;position:relative;'/></td>"
			+"<td name='auxiliary_Longitude'><input onchange='geoDistanceFlag()' id='aux_Long"+index+"' name='aux_Long' value='"+long+"'  class='form-control text-input' type='text' style='width:200px;position:relative;'/></td>"
			+ "<td name='auxiliary_Latitude'> <input onchange='geoDistanceFlag()' id='aux_Lat"+index+"' name='aux_Lat' value='"+lat+"'   style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
			+"<td name='auxiliary_Length'> <input id='aux_Len"+index+"' value='"+auxlength+"' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
			+"<td name='auxDrivingDist'> <input id='auxDrivingDist"+index+"' value='"+auxDrivingDist+"' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
			+"<td name='auxGeoDistance'> <input id='auxGeoDistance"+index+"' value='"+auxGeoDistance+"' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
			+"</tr>";

			return markup
  		
    	/* 
		calculateDistanceSourceDestination($("#SourceLat").val(),$("#SourceLng").val(),$("#DestinationLat").val(),$("#DestinationLng").val(),"auxiliaryTable");

	$("#auxiliaryTable > tbody").append(markup);
	
	$("#AuxFiber"+index).on('change',function(){
		if(!isNaN($(this).parent().parent().children("td[name=auxiliary_Latitude]").children('input').val()) && !isNaN($(this).parent().parent().children("td[name=auxiliary_Longitude]").children('input').val())){											
			map.setZoom(15);
			panTo($(this).parent().parent().children("td[name=auxiliary_Latitude]").children('input').val(),$(this).parent().parent().children("td[name=auxiliary_Longitude]").children('input').val());
		}
	})*/
}



function trenchAuxiliary_BoqAppendMarkup(name,long,lat,seqTrench){
	
	console.log("ENTER TO APPEND");
	var markup = "<tr id='#auxiliaryTrench_Row"+indextrench+"'><td><input type='checkbox' class='rowAboveBelowTrench'  id='Auxtrench"+indextrench+"' style='position:relative;left:20px;top:10px' name='record'></td>"
			+"<td name='TrenchSeq'><input name='TrenchSeq' class='form-control text-input' type='text' style='width:60px;position:relative;' value='"+seqTrench+"' readonly/></td>"
			+"<td name='auxiliaryTrench_Name' id='auxiliaryTrench_Name"+indextrench+"'><input id='aux_PointTrench"+indextrench+"' class='form-control text-input' type='text' style='width:180px;position:relative;left:11px;'/></td>"
			+"<td name='auxiliaryTrench_Longitude'><input id='aux_Longtrench"+indextrench+"'class='form-control text-input' type='text' style='width:150px;position:relative;' /></td>"
			+ "<td name='auxiliaryTrench_Latitude' style='width:200px'> <input id='aux_Lattrench"+indextrench+"' style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
		    +"<td name='auxiliaryTrench_Length'> <input id='aux_LenTrench"+indextrench+"'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";
	
    calculateDistanceSourceDestination($("#SourceTrenchLat").val(),$("#SourceTrenchLng").val(),$("#DestinationTrenchLat").val(),$("#DestinationTrenchLng").val(),"auxiliary_trenchTable");	 
	 
	$("#auxiliary_trenchTable > tbody").append(markup);
	$("#aux_PointTrench"+indextrench).val(name);
	$("#aux_Longtrench"+indextrench).val(long);
	$("#aux_Lattrench"+indextrench).val(lat);
	
	$("#Auxtrench"+indextrench).on('change',function(){
	if(!isNaN($(this).parent().parent().children("td[name=auxiliaryTrench_Longitude]").children('input').val()) && !isNaN($(this).parent().parent().children("td[name=auxiliaryTrench_Latitude]").children('input').val())){											
			map.setZoom(15);
			panTo($(this).parent().parent().children("td[name=auxiliaryTrench_Latitude]").children('input').val(),$(this).parent().parent().children("td[name=auxiliaryTrench_Longitude]").children('input').val());
		}
	})
	//indextrench++;

}

function tube_BoqAppendMarkup(tubeId,tubeSource,tubeSource_Longitude,tubeSource_Latitude,tubeDestination,tubeDestination_Longitude,tubeDestination_Latitude,tubeName,tubetype,tubedeployment,tubenetlevel,tubeowner,srcCity,dstCity,tubeNumber,tubeColor,drawingTypeFiberTube,totalGeoDistance,totalDrivDistance,distLstAuxToDest,drivDistLstAuxToDest){

	var markup = "<tr id='tube_Row"+indexTube+"'><td><input type='checkbox' id='tubeCheck"+indexTube+"' style='position:relative;left:20px;top:10px' name='record'></td>"
				+"<td name='Auxiliary' ><a href='#' id='auxRefTube"+indexTube+"' style='width:350px;'><p style='height:10px;margin-left:20px;color:#00757C;margin-top:10px'>View or Add</p></a></td>"
				+ "<td style='background-color:#00757C'></td>"
				+"<td name='tubeId' ><input id='tubeId"+indexTube+"' disabled class='form-control text-input' type='text' style='width:250px;position:relative;'/></td>"
				+"<td name='tubeSource'><input id='tubeSource"+indexTube+"' class='form-control text-input' type='text' style='width:250px;position:relative;'/></td>"
				+ "<td name='tubeSource_Longitude' > <input id='tubeSource_Long"+indexTube+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='tubeSource_Latitude' > <input id='tubeSource_Lat"+indexTube+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='tubeSource_City' > <input id='tubeSource_City"+indexTube+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td style='background-color:#00757C'></td>"
				+ "<td name='tubeDestination'> <input id='tubeDestination"+indexTube+"' style='width:250px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='tubeDestination_Longitude'> <input id='tubeDestination_Long"+indexTube+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='tubeDestination_Latitude'> <input id='tubeDestination_Lat"+indexTube+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='tubeDestination_City' > <input id='tubeDestination_City"+indexTube+"' style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td style='background-color:#00757C'></td>"
				+ "<td name='tubeLength'> <input id='tube_length"+indexTube+"' style='width:150px;position:relative;'  type='text' disabled class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='tubetotalLength'> <input id='tube_total_length"+indexTube+"' title='Length of Fiber Cable and this tube' style='width:150px;position:relative;'  type='text' disabled class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='tubeName'> <input id='tubeName"+indexTube+"' style='width:180px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+"<td name='tubetype'><select id='tubetype"+indexTube+"' class='form-control'><option value=''></option><option value='g561'>G.651</option><option value='g562'>G.652</option><option value='g563'>G.653</option></select></td>"
				+"<td name='tubedeployment'><select id='tubedeployment"+indexTube+"' class='form-control'><option value='' selected></option><option value='aerial'>Aerial</option><option value='submarine'>Submarine</option><option value='underground'>Underground</option></select></td>"
				+ "<td name='tubenetlevel'> <input id='tubenetlevel"+indexTube+"' style='width:150px;position:relative;'  type='text' disabled class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+"<td name='tubeowner'><select id='tubeowner"+indexTube+"' class='form-control'><option value='' selected></option><option value='tkl'>TKL</option><option value='nofbi'>NOFBI</option><option value='others'>Others</option></select></td>"
				+ "<td name='fiberTubeNumber'> <input id='fiberTubeNumber"+indexTube+"' style='width:180px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+"<td name='fiberTubeColor'><select name ='fiberTubeColor' id='fiberTubeColor"+indexTube+"' class='form-control'><option value='' style='background-color: white;' selected></option><option value='blue' style='background-color: white; color:black'>Blue</option><option value='orange' style='background-color: white;color:black'>Orange</option>"
				+ "<option value='green' style='background-color: white;color:black'>Green</option><option value='brown' style='background-color: white;color:black'>Brown</option><option value='gray' style='background-color: white;color:black'>Gray</option><option value='white' style='background-color: white;color:black'>White</option><option value='red' style='background-color: white;color:black'>Red</option>"
				+ "<option value='black' style='background-color: white;color:black'>Black</option><option value='yellow' style='background-color: white;color:black'>Yellow</option><option value='violet' style='background-color: white;color:black'>Violet</option><option value='pink' style='background-color: white;color:black'>Pink</option><option value='aqua' style='background-color: white;color:black'>Aqua</option></select></td>"
				+"<td name='drawingTypeFiberTube'><select id='drawingTypeFiberTube"+indexTube+"' class='form-control'><option value='DEFAULT' selected></option><option value='DRIVING'>Driving</option><option value='LINEOFSITE'>Line of Site</option></select></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberTubeTotalGeoDistance'><input type='text' id='fiberTubeTotalGeoDistance"+indexTube+"' style='width:0px;' hidden  value ='0' ></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberTubeTotalDrivDistance'><input type='text' id='fiberTubeTotalDrivDistance"+indexTube+"' style='width:0px;' hidden  value ='0' ></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberTubeDistLstAuxToDest'><input type='text' id='fiberTubeDistLstAuxToDest"+indexTube+"' style='width:0px;' hidden  value ='0' ></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberTubeDrivDistLstAuxToDest'><input type='text' id='fiberTubeDrivDistLstAuxToDest"+indexTube+"' style='width:0px;' hidden  value ='0' ></td>"
				+"</tr>";
	
	$("#tubesTable > tbody").append(markup);

	$("#tubeId"+indexTube).val(tubeId);
	$("#tubeSource"+indexTube).val(tubeSource);
	$("#tubeSource_Long"+indexTube).val(tubeSource_Longitude);
	$("#tubeSource_Lat"+indexTube).val(tubeSource_Latitude);
	$("#tubeSource_City"+indexTube).val(srcCity);
	$("#tubeDestination"+indexTube).val(tubeDestination);
	$("#tubeDestination_Long"+indexTube).val(tubeDestination_Longitude);
	$("#tubeDestination_Lat"+indexTube).val(tubeDestination_Latitude);
	$("#tubeDestination_City"+indexTube).val(dstCity);
	$("#tubeName"+indexTube).val(tubeName);
	$("#tubetype"+indexTube).val(tubetype);
	$("#tubedeployment"+indexTube).val(tubedeployment);
	$("#tubenetlevel"+indexTube).val(tubenetlevel);
	$("#tubeowner"+indexTube).val(tubeowner);
	$("#fiberTubeNumber"+indexTube).val(tubeNumber);
	$("#fiberTubeColor"+indexTube).val(tubeColor);
	$("#drawingTypeFiberTube"+indexTube).val(drawingTypeFiberTube);
	$("#fiberTubeTotalGeoDistance"+indexTube).val(totalGeoDistance);
	$("#fiberTubeTotalDrivDistance"+indexTube).val(totalDrivDistance);	
	$("#fiberTubeDistLstAuxToDest"+indexTube).val(distLstAuxToDest);
	$("#fiberTubeDrivDistLstAuxToDest"+indexTube).val(drivDistLstAuxToDest);

	if(tubenetlevel=="") {
		$("#tubenetlevel"+indexTube).val($("#fibernetlevel").val());
	}
	
	strandTubeSetColor(tubeNumber,"fiberTubeColor"+indexTube);
		
	//On option change , change the color of cell		
	document.getElementById("fiberTubeNumber"+indexTube).addEventListener ("input" ,function() {
			var tubeNum =$(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val();
			 if (tubeNum=="1"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'blue','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("blue");
			 }
			 else if (tubeNum=="2"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'orange','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("orange");
			 }
			 else if (tubeNum=="3"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'green','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("green");
			 }
			 else if (tubeNum=="4"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'brown','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("brown");
			 }
			 else if (tubeNum=="5"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'gray','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("gray");
			 }
			 else if (tubeNum=="6"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'white','color':'black'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("white");
			 }
			 else if (tubeNum=="7"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'red','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("red");
			 }
			 else if (tubeNum=="8"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'black','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("black");
			 }
			 else if (tubeNum=="9"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'yellow','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("yellow");
			 }
			 else if (tubeNum=="10"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'violet','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("violet");
			 }
			 else if (tubeNum=="11"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'pink','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("pink");
			 }
			 else if (tubeNum=="12"){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'aqua','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("aqua");
			 }
			 else if (tubeNum >12 || tubeNum==""){
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'','color':'white'});
				 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("");
			 }			 
		});
	//On color change , change the tube number
	$("select[id='fiberTubeColor"+indexTube+"']").change(function () {
		var color =$(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val();
	
		 if (color=="blue"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'blue','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("blue");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("1");
		 }
		 else if (color=="orange"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'orange','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("orange");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("2"); 
		 }
		 else if (color=="green"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'green','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("green");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("3");
		 }
		 else if (color=="brown"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'brown','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("brown");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("4");
		 }
		 else if (color=="gray"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'gray','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("gray");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("5");
		 }
		 else if (color=="white"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'white','color':'black'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("white");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("6");
		 }
		 else if (color=="red"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'red','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("red");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("7");
		 }
		 else if (color=="black"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'black','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("black");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("8");
		 }
		 else if (color=="yellow"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'yellow','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("yellow");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("9");
		 }
		 else if (color=="violet"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'violet','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("violet");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("10");
		 }
		 else if (color=="pink"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'pink','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("pink");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("11");
		 }
		 else if (color=="aqua"){
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').css({'background':'aqua','color':'white'});
			 $(this).parent().parent().children("td[name=fiberTubeColor]").children('select').val("aqua");
			 $(this).parent().parent().children("td[name=fiberTubeNumber]").children('input').val("12");
		 }
	
	});	
	//Bound on src and dst of tube
	$("#tubeCheck"+indexTube).on("change",function(){
		var srcLng=$(this).parent().parent().children("td[name=tubeSource_Longitude]").children('input').val();
		var srcLat=$(this).parent().parent().children("td[name=tubeSource_Latitude]").children('input').val();
		var destLng=$(this).parent().parent().children("td[name=tubeDestination_Longitude]").children('input').val();
		var destLat=$(this).parent().parent().children("td[name=tubeDestination_Latitude]").children('input').val();
		
		if(!isNaN(srcLng) && !isNaN(srcLat) && !isNaN(destLng) && !isNaN(destLat)){
			var bounds = new google.maps.LatLngBounds();
			var extendPoint1 = new google.maps.LatLng(srcLat,srcLng);
			bounds.extend(extendPoint1);
			extendPoint1 = new google.maps.LatLng(destLat,destLng);
			bounds.extend(extendPoint1);
			map.fitBounds(bounds);
		}
	});
	
	AuxPointAutoComplete("fiberTubeAutocomplete","tubeSource"+indexTube,"tubeSource_Long"+indexTube,"tubeSource_Lat"+indexTube,"","","","","tubesTable",indexTube,"tubeSource_City"+indexTube);
	AuxPointAutoComplete("fiberTubeAutocomplete","tubeDestination"+indexTube,"tubeDestination_Long"+indexTube,"tubeDestination_Lat"+indexTube,"","","","","tubesTable",indexTube,"tubeDestination_City"+indexTube);
	
	objDiv = document.getElementById("tubesTable");
	objDiv.scrollTop = objDiv.scrollHeight;
	
}

function strand_BoqAppendMarkup(strandId,tubeId,source,strandSource_Long,strandSource_Lat,destination,strandDestination_Long,strandDestination_Lat,strandName,strandtype,stranddeployment,strandnetlevel,strandowner,srcCity,dstCity,strandNumber,strandColor,drawingTypeFiberStrand,totalGeoDistance,totalDrivDistance,distLstAuxToDest,drivDistLstAuxToDest){

	var markup = "<tr id='strand_Row"+indexStrand+"'><td><input type='checkbox' id='strandCheck"+indexStrand+"' style='position:relative;left:20px;top:10px' name='record'></td>"
				+"<td name='Auxiliary'><a href='#' id='auxRefStrand"+indexStrand+"'  style='width:350px;'><p style='height:10px;margin-left:20px;color:#00757C;margin-top:10px'>View or Add</p></a></td>"
				+ "<td style='background-color:#00757C'></td>"
				+"<td id='strand_ID"+indexStrand+"' name='Strand_ID'><input id='strandId"+indexStrand+"' value='"+strandId+"' class='form-control text-input' type='text' style='width:200px;position:relative;'readonly/></td>"
				+"<td id='tube_ID"+indexStrand+"' name='Tube_ID'><input id='tubeID"+indexStrand+"' value='"+tubeId+"' class='form-control text-input' type='text' style='width:200px;position:relative;'/></td>"
				+"<td name='strandSource'><input id='strandSource"+indexStrand+"' value='"+source+"' class='form-control text-input' type='text' style='width:200px;position:relative;'/></td>"
				+ "<td name='strandSource_Longitude'> <input id='strandSource_Long"+indexStrand+"' value='"+strandSource_Long+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='strandSource_Latitude' > <input id='strandSource_Lat"+indexStrand+"' value='"+strandSource_Lat+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='strandSource_City' > <input id='strandSource_City"+indexStrand+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td style='background-color:#00757C'></td>"
				+ "<td name='strandDestination' > <input id='strandDestination"+indexStrand+"' value='"+destination+"' style='width:250px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='strandDestination_Longitude' > <input id='strandDestination_Long"+indexStrand+"' value='"+strandDestination_Long+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='strandDestination_Latitude'> <input id='strandDestination_Lat"+indexStrand+"' value='"+strandDestination_Lat+"' style='width:100px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='strandDestination_City' > <input id='strandDestination_City"+indexStrand+"' style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td style='background-color:#00757C'></td>"
				+ "<td name='strandLength'> <input id='strand_length"+indexStrand+"' style='width:150px;position:relative;'  type='text' disabled class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+ "<td name='strandName'> <input id='strandName"+indexStrand+"' value='"+strandName+"' style='width:180px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+"<td name='strandtype'><select id='strandtype"+indexStrand+"' class='form-control'><option value=''></option><option value='g561'>G.651</option><option value='g562'>G.652</option><option value='g563'>G.653</option></select></td>"
			    +"<td name='stranddeployment'><select id='stranddeployment"+indexStrand+"' class='form-control'><option value='' selected></option><option value='aerial'>Aerial</option><option value='submarine'>Submarine</option><option value='underground'>Underground</option></select></td>"
			  	+ "<td name='strandnetlevel'> <input id='strandnetlevel"+indexStrand+"' style='width:150px;position:relative;'  type='text' disabled class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
			    +"<td name='strandowner'><select id='strandowner"+indexStrand+"' class='form-control'><option value='' selected></option><option value='tkl'>TKL</option><option value='nofbi'>NOFBI</option><option value='others'>Others</option></select></td>"
				+ "<td name='fiberStrandNumber'> <input id='fiberStrandNumber"+indexStrand+"' style='width:180px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
				+"<td name='fiberStrandColor'><select name ='fiberStrandColor' id='fiberStrandColor"+indexStrand+"' class='form-control'><option value='' style='background-color: white;' selected></option><option value='blue' style='background-color: white; color:black'>Blue</option><option value='orange' style='background-color: white;color:black'>Orange</option>"
				+ "<option value='green' style='background-color: white;color:black'>Green</option><option value='brown' style='background-color: white;color:black'>Brown</option><option value='gray' style='background-color: white;color:black'>Gray</option><option value='white' style='background-color: white;color:black'>White</option><option value='red' style='background-color: white;color:black'>Red</option>"
				+ "<option value='black' style='background-color: white;color:black'>Black</option><option value='yellow' style='background-color: white;color:black'>Yellow</option><option value='violet' style='background-color: white;color:black'>Violet</option><option value='pink' style='background-color: white;color:black'>Pink</option><option value='aqua' style='background-color: white;color:black'>Aqua</option></select></td>"
				+"<td name='drawingTypeFiberStrand'><select id='drawingTypeFiberStrand"+indexStrand+"' class='form-control'><option value='DEFAULT' selected></option><option value='DRIVING'>Driving</option><option value='LINEOFSITE'>Line of Site</option></select></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberStrandTotalGeoDistance'><input type='text' id='fiberStrandTotalGeoDistance"+indexStrand+"' style='width:0px;' hidden  value ='0' ></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberStrandTotalDrivDistance'><input type='text' id='fiberStrandTotalDrivDistance"+indexStrand+"' style='width:0px;' hidden  value ='0' ></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberStrandDistLstAuxToDest'><input type='text' id='fiberStrandDistLstAuxToDest"+indexStrand+"' style='width:0px;' hidden  value ='0' ></td>"
				+"<td style='visibility:hidden;width:0px;border-width:0px;' name='fiberStrandDrivDistLstAuxToDest'><input type='text' id='fiberStrandDrivDistLstAuxToDest"+indexStrand+"' style='width:0px;' hidden  value ='0' ></td>"				
				+"</tr>";

	$("#strandsTable > tbody").append(markup);
	
	autoCompleteTubeId("#tubeID"+indexStrand);
	$("#strandtype"+indexStrand).val(strandtype);
	$("#stranddeployment"+indexStrand).val(stranddeployment);
	$("#strandnetlevel"+indexStrand).val(strandnetlevel);
	$("#strandowner"+indexStrand).val(strandowner);
	$("#strandSource_City"+indexStrand).val(srcCity);
	$("#strandDestination_City"+indexStrand).val(dstCity);
	$("#fiberStrandNumber"+indexStrand).val(strandNumber);
	$("#fiberStrandColor"+indexStrand).val(strandColor);
	$("#drawingTypeFiberStrand"+indexStrand).val(drawingTypeFiberStrand);
	$("#fiberStrandTotalGeoDistance"+indexStrand).val(totalGeoDistance);
	$("#fiberStrandTotalDrivDistance"+indexStrand).val(totalDrivDistance);	
	$("#fiberStrandDistLstAuxToDest"+indexStrand).val(distLstAuxToDest);
	$("#fiberStrandDrivDistLstAuxToDest"+indexStrand).val(drivDistLstAuxToDest);

	strandTubeSetColor(strandNumber,"fiberStrandColor"+indexStrand);

	if(strandnetlevel=="") {
		$("#strandnetlevel"+indexStrand).val($("#fibernetlevel").val());
	}
	
	//On num change , change the color of cell		
		document.getElementById("fiberStrandNumber"+indexStrand).addEventListener ("input" ,function() {
				var strandNum =$(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val();
				 if (strandNum=="1"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'blue','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("blue");
				 }
				 else if (strandNum=="2"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'orange','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("orange");
				 }
				 else if (strandNum=="3"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'green','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("green");
				 }
				 else if (strandNum=="4"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'brown','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("brown");
				 }
				 else if (strandNum=="5"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'gray','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("gray");
				 }
				 else if (strandNum=="6"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'white','color':'black'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("white");
				 }
				 else if (strandNum=="7"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'red','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("red");
				 }
				 else if (strandNum=="8"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'black','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("black");
				 }
				 else if (strandNum=="9"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'yellow','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("yellow");
				 }
				 else if (strandNum=="10"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'violet','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("violet");
				 }
				 else if (strandNum=="11"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'pink','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("pink");
				 }
				 else if (strandNum=="12"){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'aqua','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("aqua");
				 }
				 else if (strandNum >12 || strandNum==""){
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'','color':'white'});
					 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("");
				 }			 
			});
		//On color change , change the strand number
		$("select[id='fiberStrandColor"+indexStrand+"']").change(function () {
			var color =$(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val();
		
			 if (color=="blue"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'blue','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("blue");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("1");
			 }
			 else if (color=="orange"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'orange','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("orange");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("2"); 
			 }
			 else if (color=="green"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'green','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("green");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("3");
			 }
			 else if (color=="brown"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'brown','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("brown");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("4");
			 }
			 else if (color=="gray"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'gray','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("gray");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("5");
			 }
			 else if (color=="white"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'white','color':'black'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("white");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("6");
			 }
			 else if (color=="red"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'red','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("red");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("7");
			 }
			 else if (color=="black"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'black','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("black");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("8");
			 }
			 else if (color=="yellow"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'yellow','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("yellow");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("9");
			 }
			 else if (color=="violet"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'violet','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("violet");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("10");
			 }
			 else if (color=="pink"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'pink','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("pink");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("11");
			 }
			 else if (color=="aqua"){
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').css({'background':'aqua','color':'white'});
				 $(this).parent().parent().children("td[name=fiberStrandColor]").children('select').val("aqua");
				 $(this).parent().parent().children("td[name=fiberStrandNumber]").children('input').val("12");
			 }
		
		});	
		//Bound on src and dst of strand	
	$("#strandCheck"+indexStrand).on("change",function(){
		var srcLng=$(this).parent().parent().children("td[name=strandSource_Longitude]").children('input').val();
		var srcLat=$(this).parent().parent().children("td[name=strandSource_Latitude]").children('input').val();
		var destLng=$(this).parent().parent().children("td[name=strandDestination_Longitude]").children('input').val();
		var destLat=$(this).parent().parent().children("td[name=strandDestination_Latitude]").children('input').val();
		
		if(!isNaN(srcLng) && !isNaN(srcLat) && !isNaN(destLng) && !isNaN(destLat)){
			var bounds = new google.maps.LatLngBounds();
			var extendPoint1 = new google.maps.LatLng(srcLat,srcLng);
			bounds.extend(extendPoint1);
			extendPoint1 = new google.maps.LatLng(destLat,destLng);
			bounds.extend(extendPoint1);
			map.fitBounds(bounds);
		}
	})
	
	AuxPointAutoComplete("fiberStrandAutocomplete","strandSource"+indexStrand,"strandSource_Long"+indexStrand,"strandSource_Lat"+indexStrand,"","","","","strandsTable",indexStrand,"strandSource_City"+indexStrand);
	AuxPointAutoComplete("fiberStrandAutocomplete","strandDestination"+indexStrand,"strandDestination_Long"+indexStrand,"strandDestination_Lat"+indexStrand,"","","","","strandsTable",indexStrand,"strandDestination_City"+indexStrand);
	
	objDiv = document.getElementById("strandsTable");
	objDiv.scrollTop = objDiv.scrollHeight;

}


function uncheckAutoCompleteCheckboxes(checkboxclass){
	$("."+checkboxclass).each(function(){
	$(this).prop("checked",false);
	});
	

}
function checkOnlyOneCheckbox(checkboxesID){
for (let i=0; i<checkboxesID.length; i++){
		document.getElementById(checkboxesID[i]).checked = false;
		}
       
}

/* To be deleted 
// select all boqs in the fiber popup in addition to tube,tubeAux,strand, and strand aux...
function selectALLrows () {
	//console.log("Flag is "+Flag);
		if (Flag =='Cable'){
			if ($(this).hasClass('allChecked')) {
				$('input[type="checkbox"]', '#auxiliaryTable').prop('checked', false);
					} else {
					$('input[type="checkbox"]', '#auxiliaryTable').prop('checked', true);
					}
					$(this).toggleClass('allChecked');


	if ($(this).hasClass('allChecked')) {
		$('input[type="checkbox"]', '#strandsTable').prop('checked', false);
			} else {
			$('input[type="checkbox"]', '#strandsTable').prop('checked', true);
			}
			$(this).toggleClass('allChecked');


	if ($(this).hasClass('allChecked')) {
		$('input[type="checkbox"]', '#tubesTable').prop('checked', false);
			} else {
			$('input[type="checkbox"]', '#tubesTable').prop('checked', true);
			}
			$(this).toggleClass('allChecked');


		}

	   else if (Flag=='Tube'){
			if ($(this).hasClass('allChecked')) {
				$('input[type="checkbox"]', '#auxiliaryTableTubes').prop('checked', false);
					} else {
					$('input[type="checkbox"]', '#auxiliaryTableTubes').prop('checked', true);
					}
					$(this).toggleClass('allChecked');


		}

		else if (Flag=='Strand'){
			//console.log("here in the Strand");
			if ($(this).hasClass('allChecked')) {
				$('input[type="checkbox"]', '#auxiliaryTableStrands').prop('checked', false);
					} else {
					$('input[type="checkbox"]', '#auxiliaryTableStrands').prop('checked', true);
					}
					$(this).toggleClass('allChecked');

		}
					
	}
*/
function getSelectedRowsManholeJctMapping() {

	insertJctDictArr=[];
	updateJctDictArr=[];
	if(actionManholeJct=="Update"){
	
		$("#manholeJctMappingTable > tbody").find('input[name="record"]').each(function(){
				
				var mJctSeq =$(this).parent().parent().children('td[name="mJctSeq"]').children('input').val();
				var mJctStrandIdSideA =$(this).parent().parent().children('td[name="mJctStrandIdSideA"]').children('input').val();
				var mJctStrandNameSideA =$(this).parent().parent().children('td[name="mJctStrandNameSideA"]').children('input').val();
				var mJctTubeIdSideA =$(this).parent().parent().children('td[name="mJctTubeIdSideA"]').children('input').val();
				var mJctTubeNameSideA =$(this).parent().parent().children('td[name="mJctTubeNameSideA"]').children('input').val();
				var mJctFiberIdSideA =$(this).parent().parent().children('td[name="mJctFiberIdSideA"]').children('input').val();
				var mJctFiberNameSideA =$(this).parent().parent().children('td[name="mJctFiberNameSideA"]').children('input').val();
				
				var mJctStrandIdSideB =$(this).parent().parent().children('td[name="mJctStrandIdSideB"]').children('input').val();
				var mJctStrandNameSideB =$(this).parent().parent().children('td[name="mJctStrandNameSideB"]').children('input').val();
				var mJctTubeIdSideB =$(this).parent().parent().children('td[name="mJctTubeIdSideB"]').children('input').val();
				var mJctTubeNameSideB =$(this).parent().parent().children('td[name="mJctTubeNameSideB"]').children('input').val();
				var mJctFiberIdSideB =$(this).parent().parent().children('td[name="mJctFiberIdSideB"]').children('input').val();
				var mJctFiberNameSideB =$(this).parent().parent().children('td[name="mJctFiberNameSideB"]').children('input').val();
				
				
				var mJctLocationTypeSideA=$(this).parent().parent().children('td[name="mJctLocationTypeSideA"]').children('select').val();
				var mJctLocationIdSideA =$(this).parent().parent().children('td[name="mJctLocationIdSideA"]').children('input').val();
				var mJctLocationNameSideA =$(this).parent().parent().children('td[name="mJctLocationNameSideA"]').children('input').val();
				var mJctWarehouseIdSideA =$(this).parent().parent().children('td[name="mJctWarehouseIdSideA"]').children('input').val();
				//
				var mJctStrandNBSideA =$(this).parent().parent().children('td[name="mJctStrandNBSideA"]').children('input').val();
				var mJctTubeNBSideA=$(this).parent().parent().children('td[name="mJctTubeNBSideA"]').children('input').val();
				var mJctNetworkLevelSideA=$(this).parent().parent().children('td[name="mJctNetworkLevelSideA"]').children('select').val();
				
				var mJctStrandNBSideB =$(this).parent().parent().children('td[name="mJctStrandNBSideB"]').children('input').val();
				var mJctTubeNBSideB=$(this).parent().parent().children('td[name="mJctTubeNBSideB"]').children('input').val();
				var mJctNetworkLevelSideB=$(this).parent().parent().children('td[name="mJctNetworkLevelSideB"]').children('select').val();
				
				var mJctLocationTypeSideB=$(this).parent().parent().children('td[name="mJctLocationTypeSideB"]').children('select').val();
				var mJctLocationIdSideB =$(this).parent().parent().children('td[name="mJctLocationIdSideB"]').children('input').val();
				var mJctLocationNameSideB =$(this).parent().parent().children('td[name="mJctLocationNameSideB"]').children('input').val();
				var mJctWarehouseIdSideB =$(this).parent().parent().children('td[name="mJctWarehouseIdSideB"]').children('input').val();
				
				
				
				
				
				
				var jctBoq=[mJctSeq,mJctStrandIdSideA,mJctStrandNameSideA,mJctTubeIdSideA,mJctTubeNameSideA,mJctFiberIdSideA,mJctFiberNameSideA,
					mJctStrandIdSideB,mJctStrandNameSideB,mJctTubeIdSideB,mJctTubeNameSideB,mJctFiberIdSideB,mJctFiberNameSideB,mJctLocationTypeSideA,
					mJctLocationIdSideA,mJctLocationNameSideA,mJctWarehouseIdSideA,mJctStrandNBSideA,mJctTubeNBSideA,mJctNetworkLevelSideA,mJctStrandNBSideB,
					mJctTubeNBSideB,mJctNetworkLevelSideB,mJctLocationTypeSideB,mJctLocationIdSideB,mJctLocationNameSideB,mJctWarehouseIdSideB];
				
				
				if(window["JCT_Mapper"+selectedManholeJct]){
				
				var jctMappingId=$(this).parent().parent().attr('id');
				
				index = window["JCT_Mapper"+selectedManholeJct].findIndex(x => x==""+mJctSeq+","+mJctStrandIdSideA+","+mJctStrandNameSideA+","+mJctTubeIdSideA+","+mJctTubeNameSideA+","+mJctFiberIdSideA+","+mJctFiberNameSideA+","+
					mJctStrandIdSideB+","+mJctStrandNameSideB+","+mJctTubeIdSideB+","+mJctTubeNameSideB+","+mJctFiberIdSideB+","+mJctFiberNameSideB+","+mJctLocationTypeSideA+","+mJctLocationIdSideA+","+mJctLocationNameSideA
					+","+mJctWarehouseIdSideA+","+mJctStrandNBSideA+","+mJctTubeNBSideA+","+mJctNetworkLevelSideA+","+mJctStrandNBSideB+","+mJctTubeNBSideB+","+mJctNetworkLevelSideB+","+mJctLocationTypeSideB+","+mJctLocationIdSideB+","+mJctLocationNameSideB+","+mJctWarehouseIdSideB);
					
				if(index ==-1 && !window["MAP_JCT"+jctMappingId]){
					
						insertJctDictArr.push({
							
							"JctSeq" : mJctSeq,	
							"JctLocationTypeSideA" : mJctLocationTypeSideA,
							"JctLocationIdSideA"   : mJctLocationIdSideA,
							"JctLocationNameSideA" : mJctLocationNameSideA,
							"JctWarehouseIdSideA"  : mJctWarehouseIdSideA,					    
							"JctStrandIdSideA" : mJctStrandIdSideA,
							"JctStrandNameSideA" : mJctStrandNameSideA,
							"JctTubeIdSideA":mJctTubeIdSideA,
							"JctTubeNameSideA":mJctTubeNameSideA,
							"JctFiberIdSideA":mJctFiberIdSideA,
							"JctFiberNameSideA":mJctFiberNameSideA,
							
							"JctStrandIdSideB" : mJctStrandIdSideB,
							"JctStrandNameSideB" : mJctStrandNameSideB,
							"JctTubeIdSideB":mJctTubeIdSideB,
							"JctTubeNameSideB":mJctTubeNameSideB,
							"JctFiberIdSideB":mJctFiberIdSideB,
							"JctFiberNameSideB":mJctFiberNameSideB,
							"JctStrandNBSideA"     :   mJctStrandNBSideA,
							"JctTubeNBSideA"       : mJctTubeNBSideA,
							"JctNetworkLevelSideA" :  mJctNetworkLevelSideA,
							"JctStrandNBSideB"     :  mJctStrandNBSideB,
							"JctTubeNBSideB"       : mJctTubeNBSideB,
							"JctNetworkLevelSideB" :  mJctNetworkLevelSideB,
							"JctLocationTypeSideB" :  mJctLocationTypeSideB,
							"JctLocationIdSideB"   :  mJctLocationIdSideB,
							"JctLocationNameSideB" :  mJctLocationNameSideB,
							"JctWarehouseIdSideB"  :  mJctWarehouseIdSideB
						});		
			
					} 		
	
					else if(index==-1 && window["MAP_JCT"+jctMappingId] ||( mJctSeq !=window["MAP_JCT"+jctMappingId][0] || mJctStrandIdSideA!=window["MAP_JCT"+jctMappingId][1] || mJctStrandNameSideA !=window["MAP_JCT"+jctMappingId][2] || mJctTubeIdSideA !=window["MAP_JCT"+jctMappingId][3] 
					|| mJctTubeNameSideA !=window["MAP_JCT"+jctMappingId][4] || mJctFiberIdSideA !=window["MAP_JCT"+jctMappingId][5] || mJctFiberNameSideA !=window["MAP_JCT"+jctMappingId][6] || mJctStrandIdSideB !=window["MAP_JCT"+jctMappingId][7] 
					|| mJctStrandNameSideB !=window["MAP_JCT"+jctMappingId][8] || mJctTubeIdSideB !=window["MAP_JCT"+jctMappingId][9] || mJctTubeNameSideB !=window["MAP_JCT"+jctMappingId][10] 
					|| mJctFiberIdSideB !=window["MAP_JCT"+jctMappingId][11]  || mJctFiberNameSideB !=window["MAP_JCT"+jctMappingId][12] || mJctLocationTypeSideA !=window["MAP_JCT"+jctMappingId][17] || mJctLocationIdSideA !=window["MAP_JCT"+jctMappingId][18] || mJctLocationNameSideA !=window["MAP_JCT"+jctMappingId][19] || mJctWarehouseIdSideA !=window["MAP_JCT"+jctMappingId][20] 
					|| mJctStrandNBSideA !=window["MAP_JCT"+jctMappingId][21] || mJctTubeNBSideA !=window["MAP_JCT"+jctMappingId][22] || mJctNetworkLevelSideA !=window["MAP_JCT"+jctMappingId][23] || mJctStrandNBSideB !=window["MAP_JCT"+jctMappingId][24] || mJctTubeNBSideB!=window["MAP_JCT"+jctMappingId][25] || mJctNetworkLevelSideB!=window["MAP_JCT"+jctMappingId][26] 
					|| mJctLocationTypeSideB !=window["MAP_JCT"+jctMappingId][27] || mJctLocationIdSideB !=window["MAP_JCT"+jctMappingId][28] || mJctLocationNameSideB !=window["MAP_JCT"+jctMappingId][29] || mJctWarehouseIdSideB!=window["MAP_JCT"+jctMappingId][30])){
						
						// Update to be done
						updateJctDictArr.push({
							"JctSeq" : mJctSeq,	
							"JctLocationTypeSideA" : mJctLocationTypeSideA,
							"JctLocationIdSideA"   : mJctLocationIdSideA,
							"JctLocationNameSideA" : mJctLocationNameSideA,
							"JctWarehouseIdSideA"  : mJctWarehouseIdSideA,								    
							"JctStrandIdSideA" : mJctStrandIdSideA,
							"JctStrandNameSideA" : mJctStrandNameSideA,
							"JctTubeIdSideA":mJctTubeIdSideA,
							"JctTubeNameSideA":mJctTubeNameSideA,
							"JctFiberIdSideA":mJctFiberIdSideA,
							"JctFiberNameSideA":mJctFiberNameSideA,
							
							"JctStrandIdSideB" : mJctStrandIdSideB,
							"JctStrandNameSideB" : mJctStrandNameSideB,
							"JctTubeIdSideB":mJctTubeIdSideB,
							"JctTubeNameSideB":mJctTubeNameSideB,
							"JctFiberIdSideB":mJctFiberIdSideB,
							"JctFiberNameSideB":mJctFiberNameSideB,
							"JctStrandNBSideA"     :   mJctStrandNBSideA,
							"JctTubeNBSideA"       : mJctTubeNBSideA,
							"JctNetworkLevelSideA" :  mJctNetworkLevelSideA,
							"JctStrandNBSideB"     :  mJctStrandNBSideB,
							"JctTubeNBSideB"       : mJctTubeNBSideB,
							"JctNetworkLevelSideB" :  mJctNetworkLevelSideB,
							"JctLocationTypeSideB" :  mJctLocationTypeSideB,
							"JctLocationIdSideB"   :  mJctLocationIdSideB,
							"JctLocationNameSideB" :  mJctLocationNameSideB,
							"JctWarehouseIdSideB"  :  mJctWarehouseIdSideB,
							"jctMappingId":jctMappingId
						});	
								
					} 				
				}
				else{
						insertJctDictArr.push({
							"JctSeq" : mJctSeq,	
							"JctLocationTypeSideA" : mJctLocationTypeSideA,
							"JctLocationIdSideA"   : mJctLocationIdSideA,
							"JctLocationNameSideA" : mJctLocationNameSideA,
							"JctWarehouseIdSideA"  : mJctWarehouseIdSideA,								    
							"JctStrandIdSideA" : mJctStrandIdSideA,
							"JctStrandNameSideA" : mJctStrandNameSideA,
							"JctTubeIdSideA":mJctTubeIdSideA,
							"JctTubeNameSideA":mJctTubeNameSideA,
							"JctFiberIdSideA":mJctFiberIdSideA,
							"JctFiberNameSideA":mJctFiberNameSideA,
							
							"JctStrandIdSideB" : mJctStrandIdSideB,
							"JctStrandNameSideB" : mJctStrandNameSideB,
							"JctTubeIdSideB":mJctTubeIdSideB,
							"JctTubeNameSideB":mJctTubeNameSideB,
							"JctFiberIdSideB":mJctFiberIdSideB,
							"JctFiberNameSideB":mJctFiberNameSideB,
							"JctStrandNBSideA"     :   mJctStrandNBSideA,
							"JctTubeNBSideA"       : mJctTubeNBSideA,
							"JctNetworkLevelSideA" :  mJctNetworkLevelSideA,
							"JctStrandNBSideB"     :  mJctStrandNBSideB,
							"JctTubeNBSideB"       : mJctTubeNBSideB,
							"JctNetworkLevelSideB" :  mJctNetworkLevelSideB,
							"JctLocationTypeSideB" :  mJctLocationTypeSideB,
							"JctLocationIdSideB"   :  mJctLocationIdSideB,
							"JctLocationNameSideB" :  mJctLocationNameSideB,
							"JctWarehouseIdSideB"  :  mJctWarehouseIdSideB
						});									
					}					
				
			});
			
	}
	else {
	
		$("#manholeJctMappingTable > tbody").find('input[name="record"]').each(function(){
			
				var mJctSeq =$(this).parent().parent().children('td[name="mJctSeq"]').children('input').val();
				var mJctStrandIdSideA =$(this).parent().parent().children('td[name="mJctStrandIdSideA"]').children('input').val();
				var mJctStrandNameSideA =$(this).parent().parent().children('td[name="mJctStrandNameSideA"]').children('input').val();
				var mJctTubeIdSideA =$(this).parent().parent().children('td[name="mJctTubeIdSideA"]').children('input').val();
				var mJctTubeNameSideA =$(this).parent().parent().children('td[name="mJctTubeNameSideA"]').children('input').val();
				var mJctFiberIdSideA =$(this).parent().parent().children('td[name="mJctFiberIdSideA"]').children('input').val();
				var mJctFiberNameSideA =$(this).parent().parent().children('td[name="mJctFiberNameSideA"]').children('input').val();
				
				var mJctStrandIdSideB =$(this).parent().parent().children('td[name="mJctStrandIdSideB"]').children('input').val();
				var mJctStrandNameSideB =$(this).parent().parent().children('td[name="mJctStrandNameSideB"]').children('input').val();
				var mJctTubeIdSideB =$(this).parent().parent().children('td[name="mJctTubeIdSideB"]').children('input').val();
				var mJctTubeNameSideB =$(this).parent().parent().children('td[name="mJctTubeNameSideB"]').children('input').val();
				var mJctFiberIdSideB =$(this).parent().parent().children('td[name="mJctFiberIdSideB"]').children('input').val();
				var mJctFiberNameSideB =$(this).parent().parent().children('td[name="mJctFiberNameSideB"]').children('input').val();
				
				var mJctLocationTypeSideA=$(this).parent().parent().children('td[name="mJctLocationTypeSideA"]').children('select').val();				
				var mJctLocationIdSideA =$(this).parent().parent().children('td[name="mJctLocationIdSideA"]').children('input').val();
				var mJctLocationNameSideA =$(this).parent().parent().children('td[name="mJctLocationNameSideA"]').children('input').val();
				var mJctWarehouseIdSideA =$(this).parent().parent().children('td[name="mJctWarehouseIdSideA"]').children('input').val();
			
				var mJctStrandNBSideA =$(this).parent().parent().children('td[name="mJctStrandNBSideA"]').children('input').val();
				var mJctTubeNBSideA=$(this).parent().parent().children('td[name="mJctTubeNBSideA"]').children('input').val();
				var mJctNetworkLevelSideA=$(this).parent().parent().children('td[name="mJctNetworkLevelSideA"]').children('select').val();
				
				var mJctStrandNBSideB =$(this).parent().parent().children('td[name="mJctStrandNBSideB"]').children('input').val();
				var mJctTubeNBSideB=$(this).parent().parent().children('td[name="mJctTubeNBSideB"]').children('input').val();
				var mJctNetworkLevelSideB=$(this).parent().parent().children('td[name="mJctNetworkLevelSideB"]').children('select').val();
				
				var mJctLocationTypeSideB=$(this).parent().parent().children('td[name="mJctLocationTypeSideB"]').children('select').val();
				var mJctLocationIdSideB =$(this).parent().parent().children('td[name="mJctLocationIdSideB"]').children('input').val();
				var mJctLocationNameSideB =$(this).parent().parent().children('td[name="mJctLocationNameSideB"]').children('input').val();
				var mJctWarehouseIdSideB =$(this).parent().parent().children('td[name="mJctWarehouseIdSideB"]').children('input').val();	
				
				insertJctDictArr.push({
							"JctSeq" : mJctSeq,
							"JctLocationTypeSideA" : mJctLocationTypeSideA,
							"JctLocationIdSideA"   : mJctLocationIdSideA,
							"JctLocationNameSideA" : mJctLocationNameSideA,
							"JctWarehouseIdSideA"  : mJctWarehouseIdSideA,									    
							"JctStrandIdSideA" : mJctStrandIdSideA,
							"JctStrandNameSideA" : mJctStrandNameSideA,
							"JctTubeIdSideA":mJctTubeIdSideA,
							"JctTubeNameSideA":mJctTubeNameSideA,
							"JctFiberIdSideA":mJctFiberIdSideA,
							"JctFiberNameSideA":mJctFiberNameSideA,
							
							"JctStrandIdSideB" : mJctStrandIdSideB,
							"JctStrandNameSideB" : mJctStrandNameSideB,
							"JctTubeIdSideB":mJctTubeIdSideB,
							"JctTubeNameSideB":mJctTubeNameSideB,
							"JctFiberIdSideB":mJctFiberIdSideB,
							"JctFiberNameSideB":mJctFiberNameSideB,
							"JctStrandNBSideA"     :   mJctStrandNBSideA,
							"JctTubeNBSideA"       : mJctTubeNBSideA,
							"JctNetworkLevelSideA" :  mJctNetworkLevelSideA,
							"JctStrandNBSideB"     :  mJctStrandNBSideB,
							"JctTubeNBSideB"       : mJctTubeNBSideB,
							"JctNetworkLevelSideB" :  mJctNetworkLevelSideB,
							"JctLocationTypeSideB" :  mJctLocationTypeSideB,
							"JctLocationIdSideB"   :  mJctLocationIdSideB,
							"JctLocationNameSideB" :  mJctLocationNameSideB,
							"JctWarehouseIdSideB"  :  mJctWarehouseIdSideB
				});	
			
		});
	}
}
function getSelectedRowsHandholeJctMapping() {
    
    insertJctDictArr=[];
	updateJctDictArr=[];
	if(actionHandholeJct=="Update"){
	
		$("#handholeJctMappingTable > tbody").find('input[name="record"]').each(function(){
			
				var hJctSeq =$(this).parent().parent().children('td[name="hJctSeq"]').children('input').val();
				var hJctStrandIdSideA =$(this).parent().parent().children('td[name="hJctStrandIdSideA"]').children('input').val();
				var hJctStrandNameSideA =$(this).parent().parent().children('td[name="hJctStrandNameSideA"]').children('input').val();
				var hJctTubeIdSideA =$(this).parent().parent().children('td[name="hJctTubeIdSideA"]').children('input').val();
				var hJctTubeNameSideA =$(this).parent().parent().children('td[name="hJctTubeNameSideA"]').children('input').val();
				var hJctFiberIdSideA =$(this).parent().parent().children('td[name="hJctFiberIdSideA"]').children('input').val();
				var hJctFiberNameSideA =$(this).parent().parent().children('td[name="hJctFiberNameSideA"]').children('input').val();
				var hJctStrandIdSideB =$(this).parent().parent().children('td[name="hJctStrandIdSideB"]').children('input').val();
				var hJctStrandNameSideB =$(this).parent().parent().children('td[name="hJctStrandNameSideB"]').children('input').val();
				var hJctTubeIdSideB =$(this).parent().parent().children('td[name="hJctTubeIdSideB"]').children('input').val();
				var hJctTubeNameSideB =$(this).parent().parent().children('td[name="hJctTubeNameSideB"]').children('input').val();
				var hJctFiberIdSideB =$(this).parent().parent().children('td[name="hJctFiberIdSideB"]').children('input').val();
				var hJctFiberNameSideB =$(this).parent().parent().children('td[name="hJctFiberNameSideB"]').children('input').val();
				
				var hJctLocationTypeSideA=$(this).parent().parent().children('td[name="hJctLocationTypeSideA"]').children('select').val();
				var hJctLocationIdSideA =$(this).parent().parent().children('td[name="hJctLocationIdSideA"]').children('input').val();
				var hJctLocationNameSideA =$(this).parent().parent().children('td[name="hJctLocationNameSideA"]').children('input').val();
				var hJctWarehouseIdSideA =$(this).parent().parent().children('td[name="hJctWarehouseIdSideA"]').children('input').val();
				
				
				var jctBoq=[hJctSeq,hJctStrandIdSideA,hJctStrandNameSideA,hJctTubeIdSideA,hJctTubeNameSideA,hJctFiberIdSideA,hJctFiberNameSideA,
					hJctStrandIdSideB,hJctStrandNameSideB,hJctTubeIdSideB,hJctTubeNameSideB,hJctFiberIdSideB,hJctFiberNameSideB,hJctLocationTypeSideA,hJctLocationIdSideA,hJctLocationNameSideA,hJctWarehouseIdSideA];
				
				
				console.log(">><<<"+window["HANDHOLE_JCT_Mapper"+selectedHandholeJct]);
				
				if(window["HANDHOLE_JCT_Mapper"+selectedHandholeJct]){
					var jctHandholeMappingId=$(this).parent().parent().attr('id');
				
				index = window["HANDHOLE_JCT_Mapper"+selectedHandholeJct].findIndex(x => x==""+hJctSeq+","+hJctStrandIdSideA+","+hJctStrandNameSideA+","+hJctTubeIdSideA+","+hJctTubeNameSideA+","+hJctFiberIdSideA+","+hJctFiberNameSideA+","+
					hJctStrandIdSideB+","+hJctStrandNameSideB+","+hJctTubeIdSideB+","+hJctTubeNameSideB+","+hJctFiberIdSideB+","+hJctFiberNameSideB+","+hJctLocationTypeSideA+","+hJctLocationIdSideA+","+hJctLocationNameSideA+","+hJctWarehouseIdSideA);
				
					
				if(index ==-1 && !window["HANDHOLE_MAP_JCT"+jctHandholeMappingId]){
					
						insertJctDictArr.push({
						
							"JctSeq" : hJctSeq,	
							"JctLocationTypeSideA" : hJctLocationTypeSideA,
							"JctLocationIdSideA"   : hJctLocationIdSideA,
							"JctLocationNameSideA" : hJctLocationNameSideA,
							"JctWarehouseIdSideA"  : hJctWarehouseIdSideA,							    
							"JctStrandIdSideA" : hJctStrandIdSideA,
							"JctStrandNameSideA" : hJctStrandNameSideA,
							"JctTubeIdSideA":hJctTubeIdSideA,
							"JctTubeNameSideA":hJctTubeNameSideA,
							"JctFiberIdSideA":hJctFiberIdSideA,
							"JctFiberNameSideA":hJctFiberNameSideA,
							
							"JctStrandIdSideB" : hJctStrandIdSideB,
							"JctStrandNameSideB" : hJctStrandNameSideB,
							"JctTubeIdSideB":hJctTubeIdSideB,
							"JctTubeNameSideB":hJctTubeNameSideB,
							"JctFiberIdSideB":hJctFiberIdSideB,
							"JctFiberNameSideB":hJctFiberNameSideB
						
						});										
					} 		
	
					else if(index==-1 && window["HANDHOLE_MAP_JCT"+jctHandholeMappingId] ||( hJctSeq !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][0] || hJctStrandIdSideA!=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][1] || hJctStrandNameSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][2] || hJctTubeIdSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][3] 
					|| hJctTubeNameSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][4] || hJctFiberIdSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][5] || hJctFiberNameSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][6] || hJctStrandIdSideB !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][7] 
					|| hJctStrandNameSideB !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][8] || hJctTubeIdSideB !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][9] ||hJctTubeNameSideB !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][10] 
					 || hJctFiberIdSideB !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][11]  || hJctFiberNameSideB !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][12]  || hJctLocationTypeSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][17]  || hJctLocationIdSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][18] || hJctLocationNameSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][19] || hJctWarehouseIdSideA !=window["HANDHOLE_MAP_JCT"+jctHandholeMappingId][20])){
					
					
						// Update to be done
						updateJctDictArr.push({
						
							"JctSeq" : hJctSeq,	
							"JctLocationTypeSideA" : hJctLocationTypeSideA,
							"JctLocationIdSideA"   : hJctLocationIdSideA,
							"JctLocationNameSideA" : hJctLocationNameSideA,
							"JctWarehouseIdSideA"  : hJctWarehouseIdSideA,								    
							"JctStrandIdSideA" : hJctStrandIdSideA,
							"JctStrandNameSideA" : hJctStrandNameSideA,
							"JctTubeIdSideA":hJctTubeIdSideA,
							"JctTubeNameSideA":hJctTubeNameSideA,
							"JctFiberIdSideA":hJctFiberIdSideA,
							"JctFiberNameSideA": hJctFiberNameSideA,
							"JctStrandIdSideB" : hJctStrandIdSideB,
							"JctStrandNameSideB" : hJctStrandNameSideB,
							"JctTubeIdSideB": hJctTubeIdSideB,
							"JctTubeNameSideB": hJctTubeNameSideB,
							"JctFiberIdSideB": hJctFiberIdSideB,
							"JctFiberNameSideB": hJctFiberNameSideB,
							"jctMappingId":jctHandholeMappingId
						});		
										
						} 				
				}
				else{

						insertJctDictArr.push({
						
							"JctSeq" : hJctSeq,	
							"JctLocationTypeSideA" : hJctLocationTypeSideA,
							"JctLocationIdSideA"   : hJctLocationIdSideA,
							"JctLocationNameSideA" : hJctLocationNameSideA,
							"JctWarehouseIdSideA"  : hJctWarehouseIdSideA,								    
							"JctStrandIdSideA" : hJctStrandIdSideA,
							"JctStrandNameSideA" : hJctStrandNameSideA,
							"JctTubeIdSideA": hJctTubeIdSideA,
							"JctTubeNameSideA": hJctTubeNameSideA,
							"JctFiberIdSideA": hJctFiberIdSideA,
							"JctFiberNameSideA": hJctFiberNameSideA,
							"JctStrandIdSideB" : hJctStrandIdSideB,
							"JctStrandNameSideB" :hJctStrandNameSideB,
							"JctTubeIdSideB": hJctTubeIdSideB,
							"hJctTubeNameSideB": hJctTubeNameSideB,
							"JctFiberIdSideB": hJctFiberIdSideB,
							"JctFiberNameSideB": hJctFiberNameSideB
							
						});	
							
					}
					
			
			});
			
	}
	else {
	
		$("#handholeJctMappingTable > tbody").find('input[name="record"]').each(function(){
			
				var hJctSeq =$(this).parent().parent().children('td[name="hJctSeq"]').children('input').val();
				var hJctStrandIdSideA =$(this).parent().parent().children('td[name="hJctStrandIdSideA"]').children('input').val();
				var hJctStrandNameSideA =$(this).parent().parent().children('td[name="hJctStrandNameSideA"]').children('input').val();
				var hJctTubeIdSideA =$(this).parent().parent().children('td[name="hJctTubeIdSideA"]').children('input').val();
				var hJctTubeNameSideA =$(this).parent().parent().children('td[name="hJctTubeNameSideA"]').children('input').val();
				var hJctFiberIdSideA =$(this).parent().parent().children('td[name="hJctFiberIdSideA"]').children('input').val();
				var hJctFiberNameSideA =$(this).parent().parent().children('td[name="hJctFiberNameSideA"]').children('input').val();
				var hJctStrandIdSideB =$(this).parent().parent().children('td[name="hJctStrandIdSideB"]').children('input').val();
				var hJctStrandNameSideB =$(this).parent().parent().children('td[name="hJctStrandNameSideB"]').children('input').val();
				var hJctTubeIdSideB =$(this).parent().parent().children('td[name="hJctTubeIdSideB"]').children('input').val();
				var hJctTubeNameSideB =$(this).parent().parent().children('td[name="hJctTubeNameSideB"]').children('input').val();
				var hJctFiberIdSideB =$(this).parent().parent().children('td[name="hJctFiberIdSideB"]').children('input').val();
				var hJctFiberNameSideB =$(this).parent().parent().children('td[name="hJctFiberNameSideB"]').children('input').val();
				var hJctLocationTypeSideA=$(this).parent().parent().children('td[name="hJctLocationTypeSideA"]').children('select').val();
				var hJctLocationIdSideA =$(this).parent().parent().children('td[name="hJctLocationIdSideA"]').children('input').val();
				var hJctLocationNameSideA =$(this).parent().parent().children('td[name="hJctLocationNameSideA"]').children('input').val();
				var hJctWarehouseIdSideA =$(this).parent().parent().children('td[name="hJctWarehouseIdSideA"]').children('input').val();
			insertJctDictArr.push({
							"JctSeq" : hJctSeq,	
							"JctLocationTypeSideA" : hJctLocationTypeSideA,
							"JctLocationIdSideA"   : hJctLocationIdSideA,
							"JctLocationNameSideA" : hJctLocationNameSideA,
							"JctWarehouseIdSideA"  : hJctWarehouseIdSideA,								    
							"JctStrandIdSideA" : hJctStrandIdSideA,
							"JctStrandNameSideA" : hJctStrandNameSideA,
							"JctTubeIdSideA": hJctTubeIdSideA,
							"JctTubeNameSideA": hJctTubeNameSideA,
							"JctFiberIdSideA": hJctFiberIdSideA,
							"JctFiberNameSideA": hJctFiberNameSideA,
							"JctStrandIdSideB" : hJctStrandIdSideB,
							"JctStrandNameSideB" :hJctStrandNameSideB,
							"JctTubeIdSideB": hJctTubeIdSideB,
							"JctTubeNameSideB": hJctTubeNameSideB,
							"JctFiberIdSideB": hJctFiberIdSideB,
							"JctFiberNameSideB": hJctFiberNameSideB
							
			});			
		
	
		});
	}
}
//colorchange for fiber
function changeColor(){

	
	 $("#changeCableColorModal").modal("show");
	 $("#colorChangecableTBody").empty();
	 //console.log("hiiii "+fiberOwnerList);
	 var removeDuplicatesOwnerArray =[];
					
	removeDuplicatesOwnerArray = fiberOwnerList.filter(function(item, pos) {
		if (item != null)
			return fiberOwnerList.indexOf(item) == pos;
	});
	
	var tr = "";
	for(var r = 0 ; r < removeDuplicatesOwnerArray.length; r++) {
		var str = "";		
		/*if(removeDuplicatesOwnerArray[r] == "tkl"){			
			tr +="<tr ><td style='min-width:150px; font-size: 17px;' name='owner' class='row-pad'>"+removeDuplicatesOwnerArray[r].toUpperCase()+"</td><td style='min-width:150px;' name='color'><select class='ui-widget ui-corner-all form-control' id='colorOptions"+removeDuplicatesOwnerArray[r]+"' style='background-color: blue; color:white'><option value='#0080FF' style='background-color: white; color:black' selected>blue</option></select></td></tr>";
		}else{*/
			if(window['FiberColor_'+removeDuplicatesOwnerArray[r]] == null){
				tr +="<tr><td><input type='checkbox' style='position:relative;top:10px;' name='showFibersOwner' id='"+removeDuplicatesOwnerArray[r]+"'></td><td style='min-width:150px; font-size: 17px;' name='owner' class='row-pad'>"+removeDuplicatesOwnerArray[r].toUpperCase()+"</td><td style='min-width:150px;' name='color'><select class='ui-widget ui-corner-all form-control'  id='colorOptions"+removeDuplicatesOwnerArray[r]+"' style='background-color: white; color:black' ><option value=''>Please select a color</option><option value='#4986E7'  style='background-color: white;color:black' >blue</option><option value='#36f21d'  style='background-color: white;color:black' >green</option><option value='#ff8800'  style='background-color: white;color:black' >orange</option><option value='#ff0088'  style='background-color: white;color:black' >pink</option></select></td></tr>";	
			}else{
				var colorName = "";
				if (window['FiberColor_'+removeDuplicatesOwnerArray[r]] == "#36f21d"){
					colorName = "green";     
				}
				else if (window['FiberColor_'+removeDuplicatesOwnerArray[r]] == "#ff8800"){
					colorName = "orange"; 
				}
				else if (window['FiberColor_'+removeDuplicatesOwnerArray[r]] == "#ff0088"){
					colorName = "pink";  	
				}
				else if (window['FiberColor_'+removeDuplicatesOwnerArray[r]] == "#4986E7"){
					colorName = "blue"; 
				}
				
				str += "<select class='ui-widget ui-corner-all form-control'  id='colorOptions"+removeDuplicatesOwnerArray[r]+"' style='background-color: "+window['FiberColor_'+removeDuplicatesOwnerArray[r]]+"; color:white' ><option value='"+window['FiberColor_'+removeDuplicatesOwnerArray[r]]+"' style='background-color: white;color:black'  >"+colorName+"</option>";
				if(window['FiberColor_'+removeDuplicatesOwnerArray[r]] != "#36f21d"){
					str += "<option value='#36f21d' style='background-color: white;color:black' >green</option>";
				}
				if(window['FiberColor_'+removeDuplicatesOwnerArray[r]] != "#ff8800"){
					str += "<option value='#ff8800' style='background-color: white;color:black' >orange</option>";
				}
				if(window['FiberColor_'+removeDuplicatesOwnerArray[r]] != "#ff0088"){
					str += "<option value='#ff0088' style='background-color: white;color:black' >pink</option>";
				}
				if(window['FiberColor_'+removeDuplicatesOwnerArray[r]] != "#4986E7"){
					str += "<option value='#4986E7' style='background-color: white;color:black' >blue</option>";
				}
				
				str += "</select>";
				tr +="<tr><td><input type='checkbox' style='position:relative;top:10px;' name='showFibersOwner'  id='"+removeDuplicatesOwnerArray[r]+"' ></td><td style='min-width:150px; font-size: 17px;' name='owner' class='row-pad'>"+removeDuplicatesOwnerArray[r].toUpperCase()+"</td><td style='min-width:150px;' name='color'>"+str+"</td></tr>";
			}
		//}		
	}
			
	$("#changeCableColorTable").append(tr);							
	
	var selectId  = "";
	for(var c = 0 ; c < removeDuplicatesOwnerArray.length; c++) {
		// for changing color of select 
		 selectId  = "colorOptions"+removeDuplicatesOwnerArray[c];
		 //console.log("selectId is  "+selectId);
		 //console.log("element as html id is  "+document.getElementById(selectId).value);

		document.getElementById("colorOptions"+removeDuplicatesOwnerArray[c]).onchange = function() {
			console.log("entered changing color event id is "+$(this).attr('id'));
			if ($(this).val() == "#36f21d"){
				$(this).css('background-color','#36f21d');
				$(this).css('color','white');     
			}
			else if ($(this).val() == "#ff8800"){
			     $(this).css('background-color','#ff8800');
					$(this).css('color','white');
			}
			else if ($(this).val() == "#ff0088"){
				$(this).css('background-color','#ff0088');
				$(this).css('color','white');
			   	
			}
			else if ($(this).val() == "#4986E7"){
				$(this).css('background-color','#4986E7');
				$(this).css('color','white');
			}
			 
		}
		
	}
	
	removeDuplicatesOwnerArray=[];
					
					
			    	
					
	
						$("#saveFiberPathColor").click(function(){
							 $("#changeCableColorModal").modal("hide");
                           var ownerColorList = [];
                           var id = 0;
                          
							$("#changeCableColorTable > tbody ").find("tr").each(function(){
								var cableColor=$(this).find('td[name="color"]').children('select').val();
								var cableOwner=$(this).find('td[name="owner"]').text();
								//console.log("color "+cableColor);
								//console.log("cableOwner "+cableOwner);
								window['FiberColor_'+cableOwner.toLowerCase()] = cableColor;
								ownerColorList.push({"id" : cableOwner.toLowerCase()+"_"+id++,"owner" : cableOwner.toLowerCase() ,"color": cableColor });
								//console.log("hiiii "+window['FiberColor_'+cableOwner.toLowerCase()]);
							});
							
							
							$.ajax({
				    			   type: "GET",
				    			   contentType: "application/json; charset=utf-8",
				    			   url: getContext()+'/saveCableColor',
				    			   data: {										 
				    				   
				    				   "dictParameter" : ownerColorList,
				    				  // "update" : update
				    			   },
				    			   dataType: "json",
				    			   success: function (data) {   
				    				   //console.log("done ");
				    			   },
				    			   error: function (result) {
				    				   alert("Error");
				    			   }
				    		   });
							 
							 for(var f = 0 ; f < fiberIdList.length; f++) {// when change color to set new color for shown fibers on map
								 //console.log("fiberIdList[i] "+fiberIdList[f]+" owner "+window[''+fiberIdList[f]][22]+ " and color "+window['FiberColor_'+window[''+fiberIdList[f]][22]]);
								 if(fiberArray[fiberIdList[f]]){	
								     fiberArray[fiberIdList[f]].setOptions({ strokeColor: window['FiberColor_'+window[''+fiberIdList[f]][22]] });
							     }
						   }
							 
						});
						
						 $('table tr input[name="showFibersOwner"]').on('change', function() {// for checkbox of the owner to show related fibers
							 if ($(this).is(":checked")) {
								 $('.AllFiberCables').prop('checked', false);
								 $("#FiberPath_f_CurrentPhysicalLayer").parent().find('input:checkbox').each(function(){
									 //console.log("hii  "+$(this).attr("id"));
										$(this).prop('checked', false);
										if($(this).parent().hasClass('FIBER')){
											var fiberId=$(this).parent().attr('id');
											if(fiberId.includes("__showPath") == true ){
												fiberId = fiberId.split("__showPath")[0];
											}
											if(fiberArray[fiberId]){
												if(directionHashmap.get(fiberId) != undefined) {
													for(ii = 0; ii < directionHashmap.get(fiberId).length; ii++) {
														directionHashmap.get(fiberId)[ii].setMap(null); 
									                    directionHashmap.get(fiberId).mapLabel.setMap(null);
													}
												}
												fiberArray[fiberId].setMap(null);	
												fiberArray[fiberId].mapLabel.setMap(null);
											}
											$("#fiberCheckAllBoq").prop("checked",false);	
										}
										else if($(this).parent().hasClass('TUBE')){	
											var tubeId = $(this).parent().attr('id');
											if(tubeId.includes("__showPath") == true ){
												tubeId = tubeId.split("__showPath")[0];
											}	
											if(directionHashmapTube.get(tubeId) != undefined) {
												for(ii = 0; ii < directionHashmapTube.get(tubeId).length; ii++) {
													directionHashmapTube.get(tubeId)[ii].setMap(null); 
													directionHashmapTube.get(tubeId).mapLabel.setMap(null);
												}
											}
											if(tubeArray[tubeId]){
												tubeArray[tubeId].setMap(null);
												tubeArray[tubeId].mapLabel.setMap(null);
											}
											
											$("#tubeCheckAllBoq").prop("checked",false);
										}
										else if($(this).parent().hasClass('STRAND')){	
											var strandId = $(this).parent().attr('id');
											if(strandId.includes("__showPath") == true ){
												strandId = strandId.split("__showPath")[0];
											}
											if(directionHashmapStrand.get(strandId) != undefined) {
												for(ii = 0; ii < directionHashmapStrand.get(strandId).length; ii++) {
													directionHashmapStrand.get(strandId)[ii].setMap(null); 
													directionHashmapStrand.get(strandId).mapLabel.setMap(null);
												}
											}
											if(strandArray[strandId]){
												strandArray[strandId].setMap(null);
												strandArray[strandId].mapLabel.setMap(null);
											}
											
											$("#strandCheckAllBoq").prop("checked",false);				
										}
								 });
								 
								 var ownerName = $(this).attr("id");
									 for(var f = 0 ; f < fiberIdList.length; f++) { 
										 if(ownerName == window[''+fiberIdList[f]][22]){
											 $("#"+fiberIdList[f]+"").children(':checkbox').prop( "checked", true );
											// console.log("match");
											 if(fiberArray[fiberIdList[f]]){
												 var path=window["mapPoints_"+fiberIdList[f]]; 
												 //console.log("path is "+window["mapPoints_"+fiberIdList[f]]);
												 fiberArray[fiberIdList[f]].setPath(path);
											     fiberArray[fiberIdList[f]].setMap(map);
											 }else{
												 buildPath(fiberIdList[f],window["mapPoints_"+fiberIdList[f]],fiberArray,allFiberCables,"FiberPath_f_",window['FiberColor_'+window[''+fiberIdList[f]][22]],0.7,4.5,'blue',13);
									     		 fiberArray[fiberIdList[f]].setMap(map);
											 }
											 
										 }
										
								   }
							 }
						 });
		

}
// remove items from arrays 
function removeItemAll(arr, value) {
	var index = arr.indexOf(value);
	if (index > -1) {
	  arr.splice(index, 1);
	}
	return arr;
  }
  

function getSelectedTubesAux(tubeId,indexTubeAux,indexTube){
	
	slctDelTubesAux = [];
	allAuxDictTube=[]
	window["Auxpts_Tubes"+tubeId]=[];
	

	$("#TubeAuxTable > tbody").find('input[name="record"]').each(function(){
		
		//if($(this).is(":checked")){		  
			
			
			slctDelTubesAux.push($(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val());


			var name=$(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="TubeAuxiliary_Longitude"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="TubeAuxiliary_Latitude"]').children('input').val();
			var seqSorting = $(this).parent().parent().children('td[name="fiberTubeAuxSeq"]').children('input').val();
			
			var sourceLat=$("#DestinationLat").val();
			var sourceLng=$("#DestinationLng").val();
			distanceTube=haversine_distance(sourceLat,sourceLng,lat,lng);
			
			if(name=="") {
				name = "Auxiliary_Point "+seqSorting;
			}
			var drivingDistance = $(this).parent().parent().children('td[name="auxDrivingDistFiberTube"]').children('input').val();
			var geoDistance = $(this).parent().parent().children('td[name="auxGeoDistanceFiberTube"]').children('input').val();																									  
			
			if (drivingDistance == "null" || drivingDistance == null) {
				drivingDistance = 0;
			}
			if (geoDistance == "null" || geoDistance == null) {
				geoDistance = 0;	
			}
			
			
			window["Auxpts_Tubes"+tubeId].push({
				"tubeId":tubeId,
				"aux_Name" : name,								    
				"aux_Longitude" : lng,
				"aux_Latitude" : lat,
				"distance_From_Source":distanceTube,
				"seqSorting" : seqSorting,
				"drivingDistance":drivingDistance,
				"geoDistance":geoDistance
			});
							 					
      /*  }
		
		else {
			slctDelTubesAux = jQuery.grep(window["Auxpts_Tubes"+tubeId], function(value) {
					return value != $(this).parent().parent().children('td[name="TubeAuxiliary"]').children('input').val();
					});
				console.log("window aux ::::: "+window["Auxpts_Tubes"+tubeId][0].auxTube_Longitude);
					
		  }				  
		*/
		
    	});
	
}
function getSelectedStrandsAux(sourceLat,sourceLng,strandId){

	dictStrandsAux=[];
	slctDelStrandsAux = [];
	allAuxDictStrand=[];
	window["Auxpts_Strands"+strandId]=[];

	$("#StrandAuxTable > tbody").find('input[name="record"]').each(function(){

		//if($(this).is(":checked")){		  		   
			slctDelStrandsAux.push($(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val());

			var name=$(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="StrandAuxiliary_Longitude"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="StrandAuxiliary_Latitude"]').children('input').val();
			var seqSorting = $(this).parent().parent().children('td[name="fiberStrandAuxSeq"]').children('input').val();
			var drivingDistance = $(this).parent().parent().children('td[name="auxDrivingDistFiberStrand"]').children('input').val();
			var geoDistance = $(this).parent().parent().children('td[name="auxGeoDistanceFiberStrand"]').children('input').val();																									  
			
			if (drivingDistance == "null" || drivingDistance == null) {
				drivingDistance = 0;
			}
			if (geoDistance == "null" || geoDistance == null) {
				geoDistance = 0;	
			}
			
			if(name=="") {
				name = "Auxiliary_Point "+seqSorting;
			}
			
			
			//var tubeId=selectedTube;
			//console.log("strandId::::: "+strandId);
			
			distanceStrand=haversine_distance(sourceLat,sourceLng,lat,lng);

			window["Auxpts_Strands"+strandId].push({
				"strandId":strandId,
			    "aux_Name" : name,								    
			    "aux_Longitude" : lng,
			    "aux_Latitude" : lat,
			    "distance_From_Source":distanceStrand,
			    "seqSorting" : seqSorting,
			    "drivingDistance":drivingDistance,
				"geoDistance":geoDistance
			 });
			
			
     /*   }
		
		else {
				slctDelStrandsAux = jQuery.grep(window["Auxpts_Strands"+strandId], function(value) {
					return value != $(this).parent().parent().children('td[name="StrandAuxiliary"]').children('input').val();
					});
				console.log("window aux Strand ::::: "+window["Auxpts_Strands"+strandId]);

					
		  }				  
		*/
		
    	});
	
}

function getSelectedTubeRows(sourceLat,sourceLng){
	dictTubesAuxiliary=[];	
	$("#auxiliaryTableTubes > tbody").find('input[name="record"]').each(function(){
			var name=$(this).parent().parent().children('td[name="auxiliary_NameTube"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="auxiliary_Longitude"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="auxiliary_Latitude"]').children('input').val();
			var aux_seqSorting=$(this).parent().parent().children('td[name="fiberSeq"]').children('input').val();
			var driving_distance = $(this).parent().parent().children('td[name="auxDrivingDistTube"]').children('input').val();
			var geo_distance = $(this).parent().parent().children('td[name="auxGeoDistanceTube"]').children('input').val();																									  
			
			if (driving_distance == "null" || driving_distance == null) {
				driving_distance = 0;
			}
			if (geo_distance == "null" || geo_distance == null) {
				geo_distance = 0;	
			}
			
			distance=haversine_distance(sourceLat,sourceLng,lat,lng);
			
			if(name=="") {
				name = "Auxiliary_Point "+aux_seqSorting;
			}
			
			dictTubesAuxiliary.push({
			"aux_Name" : name,								    
			"aux_Longitude" : lng,
			"aux_Latitude" : lat,
			"distance_From_Source":distance,
			"aux_seqSorting":aux_seqSorting,
			"driving_distance":driving_distance,
			"geo_distance":geo_distance
			});
			
		});	
	if(actionFiberContext === "Update") {
	 if (window['tubeCheckPoints_'+selectedTube] == "checked" || window['tubeCheckRealPoints_'+selectedTube] == "checked" || checkLabel =="checked" ) {
		 showHideAllPoints(selectedTube,"tubeCheckSequence","Hide");		 
	 }
	if(window['tubeCheckRealPoints_'+selectedTube] == "checked") {		
		window['tubeCheckRealPoints_'+selectedTube] = "unchecked";
	}	
	if(window['tubeCheckSequence_'+selectedTube] == "checked") {		
		window['tubeCheckSequence_'+selectedTube] = "unchecked";
	}
	if(window['tubeCheckPoints_'+selectedTube] == "checked") {		
		window['tubeCheckPoints_'+selectedTube] = "unchecked";
	}
	
	}
}

function getSelectedStrandRows(sourceLat,sourceLng){
	dictStrandsAuxiliary=[];	
	$("#auxiliaryTableStrands > tbody").find('input[name="record"]').each(function(){
			var name=$(this).parent().parent().children('td[name="auxiliary_NameStrand"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="auxiliary_Longitude"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="auxiliary_Latitude"]').children('input').val();
			var auxSeqSorting=$(this).parent().parent().children('td[name="fiberSeq"]').children('input').val();
			var drivingDistance = $(this).parent().parent().children('td[name="auxDrivingDistStrand"]').children('input').val();
			var geoDistance = $(this).parent().parent().children('td[name="auxGeoDistanceStrand"]').children('input').val();																									  
			
			if (drivingDistance == "null" || drivingDistance == null) {
				drivingDistance = 0;
			}
			if (geoDistance == "null" || geoDistance == null) {
				geoDistance = 0;	
			}
			
			distance=haversine_distance(sourceLat,sourceLng,lat,lng);
			if(name=="") {
				name = "Auxiliary_Point "+auxSeqSorting;
			}
			
			dictStrandsAuxiliary.push({
			"aux_Name" : name,								    
			"aux_Longitude" : lng,
			"aux_Latitude" : lat,
			"distance_From_Source":distance,
			"aux_seqSorting":auxSeqSorting,
			"drivingDistance":drivingDistance,
			"geoDistance":geoDistance
			
			});		
			
	});
	
	if(actionFiberContext === "Update") {
		
		 if (window['strandCheckPoints_'+selectedStrand] == "checked" || window['strandCheckRealPoints_'+selectedStrand] == "checked" || checkLabel =="checked" ) {
			 showHideAllPoints(selectedStrand,"strandCheckSequence","Hide");
		 }
		if(window['strandCheckRealPoints_'+selectedStrand] == "checked") {		
			window['strandCheckRealPoints_'+selectedStrand] = "unchecked";
		}	
		if(window['strandCheckSequence_'+selectedStrand] == "checked") {		
			window['strandCheckSequence_'+selectedStrand] = "unchecked";
		}
		if(window['strandCheckPoints_'+selectedStrand] == "checked") {		
			window['strandCheckPoints_'+selectedStrand] = "unchecked";
		}
		
		}
	  
}
function getSelectedFiberCableRows(sourceLat,sourceLng,fiberId){
	dict = [];
	dictTubes=[];
	dictStrands=[];
	dictTubesAuxiliary=[];
	dictStrandsAuxiliary=[];
	var fiberID ="";

	$("#auxiliaryTable > tbody").find('input[name="record"]').each(function(){

			var name=$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="auxiliary_Longitude"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="auxiliary_Latitude"]').children('input').val();
			var aux_seqSorting=$(this).parent().parent().children('td[name="fiberSeq"]').children('input').val();
			var driving_distance = $(this).parent().parent().children('td[name="auxDrivingDist"]').children('input').val();
			var geo_distance = $(this).parent().parent().children('td[name="auxGeoDistance"]').children('input').val();																									  
			//console.log("name::::: "+name);
			if (driving_distance == "null" || driving_distance == null) 
				driving_distance = 0;
			if (geo_distance == "null" || geo_distance == null)
				geo_distance = 0;				
			distance=haversine_distance(sourceLat,sourceLng,lat,lng);
			
			if(name=="") {
				name = "Auxiliary_Point "+aux_seqSorting;
			}
			dict.push({
			"aux_Name" : name,								    
			"aux_Longitude" : lng,
			"aux_Latitude" : lat,
			"distance_From_Source":distance,
			"driving_distance":driving_distance,
			"geo_distance":geo_distance,
			"aux_seqSorting":aux_seqSorting
			
			});
		
	});
	
	if(actionFiberContext === "Update") {
		 if (window['fiberCheckPoints_'+selectedFiberContext] == "checked" || window['fiberCheckRealPoints_'+selectedFiberContext] == "checked" || checkLabel =="checked" ) {
			 showHideAllPoints(selectedFiberContext,"fiberCheckSequence","Hide");
		 }
	
	if(window['fiberCheckRealPoints_'+selectedFiberContext] == "checked") {		
		window['fiberCheckRealPoints_'+selectedFiberContext] = "unchecked";
	 }	
	 if(window['fiberCheckSequence_'+selectedFiberContext] == "checked") {		
		window['fiberCheckSequence_'+selectedFiberContext] = "unchecked";
	  }
	  if(window['fiberCheckPoints_'+selectedFiberContext] == "checked") {		
		  window['fiberCheckPoints_'+selectedFiberContext] = "unchecked";
	  } 
		 
	}
	
	
	
	$("#tubesTable > tbody").find('input[name="record"]').each(function(){
	
			var tubeId=$(this).parent().parent().children('td[name="tubeId"]').children('input').val();
			var tubeSource=$(this).parent().parent().children('td[name="tubeSource"]').children('input').val();
			var tubeDestination=$(this).parent().parent().children('td[name="tubeDestination"]').children('input').val();
			var srcLng=$(this).parent().parent().children('td[name="tubeSource_Longitude"]').children('input').val();
			var srcLat=$(this).parent().parent().children('td[name="tubeSource_Latitude"]').children('input').val();
			var destLng=$(this).parent().parent().children('td[name="tubeDestination_Longitude"]').children('input').val();
			var destLat=$(this).parent().parent().children('td[name="tubeDestination_Latitude"]').children('input').val();
			var tubeName=$(this).parent().parent().children('td[name="tubeName"]').children('input').val();
			var tubetype=$(this).parent().parent().children('td[name="tubetype"]').children('select').val();
			var tubedeployment=$(this).parent().parent().children('td[name="tubedeployment"]').children('select').val();
			var tubenetlevel=$(this).parent().parent().children('td[name="tubenetlevel"]').children('select').val();
			var tubeowner=$(this).parent().parent().children('td[name="tubeowner"]').children('select').val();
			var srcCity=$(this).parent().parent().children('td[name="tubeSource_City"]').children('input').val();
			var dstCity=$(this).parent().parent().children('td[name="tubeDestination_City"]').children('input').val();
			var tubeLength=$(this).parent().parent().children('td[name="tubeLength"]').children('input').val();
			var tubeNumber=$(this).parent().parent().children('td[name="fiberTubeNumber"]').children('input').val();
			var tubeColor=$(this).parent().parent().children('td[name="fiberTubeColor"]').children('select').val();
			var drawType=$(this).parent().parent().children('td[name="drawingTypeFiberTube"]').children('select').val();
			var totalGeoDist=$(this).parent().parent().children('td[name="fiberTubeTotalGeoDistance"]').children('input').val();
			var totalDrivDist=$(this).parent().parent().children('td[name="fiberTubeTotalDrivDistance"]').children('input').val();
			var distanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberTubeDistLstAuxToDest"]').children('input').val();
			var drivDistanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberTubeDrivDistLstAuxToDest"]').children('input').val();
			
			dictTubes.push({
			"tubeId":tubeId,
			"tubeSource" : tubeSource,
			"tubeDestination" : tubeDestination,		    
			"tubeSource_Longitude" : srcLng,
			"tubeSource_Latitude" : srcLat,
			"tubeDestination_Longitude" : destLng,
			"tubeDestination_Latitude" : destLat,
			"tubeName":tubeName,
			"tubetype":tubetype,
			"tubedeployment":tubedeployment,
			"tubenetlevel":tubenetlevel,
			"tubeowner":tubeowner,
			"SourceCity": srcCity,
			"DestCity":dstCity,
			"tubeLength":tubeLength,
			"tubeNumber":tubeNumber,
			"tubeColor":tubeColor,
			"drawingType":drawType,
			"totalLength":tubeLength,
			"distanceLstAuxToDest":distanceLstAuxToDest,
			"totalDrivDistance":totalDrivDist,
			"totalGeoDistance":totalGeoDist,
			"drivDistanceLstAuxToDest":drivDistanceLstAuxToDest
			});
			showHideAllPoints(tubeId,"tubeCheckSequence","Hide");
			
			if(typeof window["Auxpts_Tubes"+tubeId] !=='undefined'){
				//showHideTubeStrandAuxPoints(window["Auxpts_Tubes"+tubeId],"","","","","",tubeId,"tubeCheckSequence","Hide","Auxiliary");
				allAuxDictTube=allAuxDictTube.concat(window["Auxpts_Tubes"+tubeId]); 
					

			}else{
				allAuxDictTube=allAuxDictTube.concat(window["Auxpts_Tubes_Save"+tubeId]); 
			}
	});

	$("#strandsTable > tbody").find('input[name="record"]').each(function(){
	
			var strandId=$(this).parent().parent().children('td[name="Strand_ID"]').children('input').val();
			var tubeId=$(this).parent().parent().children('td[name="Tube_ID"]').children('input').val();
			var strandSource=$(this).parent().parent().children('td[name="strandSource"]').children('input').val();
			var strandDestination=$(this).parent().parent().children('td[name="strandDestination"]').children('input').val();
			var srcLng=$(this).parent().parent().children('td[name="strandSource_Longitude"]').children('input').val();
			var srcLat=$(this).parent().parent().children('td[name="strandSource_Latitude"]').children('input').val();
			var destLng=$(this).parent().parent().children('td[name="strandDestination_Longitude"]').children('input').val();
			var destLat=$(this).parent().parent().children('td[name="strandDestination_Latitude"]').children('input').val();
			var StrandName=$(this).parent().parent().children('td[name="strandName"]').children('input').val();
			var strandtype=$(this).parent().parent().children('td[name="strandtype"]').children('select').val();
			var stranddeployment=$(this).parent().parent().children('td[name="stranddeployment"]').children('select').val();
			var strandnetlevel=$(this).parent().parent().children('td[name="strandnetlevel"]').children('select').val();
			var strandowner=$(this).parent().parent().children('td[name="strandowner"]').children('select').val();
			var strandLength=$(this).parent().parent().children('td[name="strandLength"]').children('input').val();
			var srcCity=$(this).parent().parent().children('td[name="strandSource_City"]').children('input').val();
			var dstCity=$(this).parent().parent().children('td[name="strandDestination_City"]').children('input').val();
			var strandNumber=$(this).parent().parent().children('td[name="fiberStrandNumber"]').children('input').val();
			var strandColor=$(this).parent().parent().children('td[name="fiberStrandColor"]').children('select').val();
			var drawType=$(this).parent().parent().children('td[name="drawingTypeFiberStrand"]').children('select').val();
			var totalGeoDist=$(this).parent().parent().children('td[name="fiberStrandTotalGeoDistance"]').children('input').val();
			var totalDrivDist=$(this).parent().parent().children('td[name="fiberStrandTotalDrivDistance"]').children('input').val();
			var distanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberStrandDistLstAuxToDest"]').children('input').val();
			var drivDistanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberStrandDrivDistLstAuxToDest"]').children('input').val();
			
			
			dictStrands.push({
			"tubeId":tubeId,
			"strandId":strandId,
			"strandSource" : strandSource,
			"strandDestination" : strandDestination,
			"strandSource_Longitude" : srcLng,
			"strandSource_Latitude" : srcLat,
			"strandDestination_Longitude" : destLng,
			"strandDestination_Latitude" : destLat,
			"StrandName":StrandName,
			"strandtype":strandtype,
			"stranddeployment":stranddeployment,
			"strandnetlevel":strandnetlevel,
			"strandowner":strandowner,
			"strandLength":strandLength,
			"SourceCity": srcCity,
			"DestCity":dstCity,
			"strandNumber":strandNumber,
			"strandColor":strandColor,
			"distanceLstAuxToDest":distanceLstAuxToDest,
			"totalDrivDistance":totalDrivDist,
			"totalGeoDistance":totalGeoDist,
			"drivDistanceLstAuxToDest":drivDistanceLstAuxToDest
			
			});
			showHideAllPoints(strandId,"strandCheckSequence","Hide");
			
			if(typeof window["Auxpts_Strands"+strandId] !=='undefined'){
				//showHideTubeStrandAuxPoints(window["Auxpts_Strands"+strandId],"","","","","",strandId,"strandCheckSequence","Hide","Auxiliary");
				allAuxDictStrand=allAuxDictStrand.concat(window["Auxpts_Strands"+strandId]);								
			}else{

				allAuxDictStrand=allAuxDictStrand.concat(window["Auxpts_Strands_Save"+strandId]);
			}
	});
}



// get the selected ports of distribition board
  function getSelectedRowsDbMapping(){

	dict=[];
	dictUpdate=[];
	var portId= "";
	
	$("#DbMappingTable > tbody").find('input[name="record"]').each(function(){

		if($(this).is(":checked")){
			
			var rowColIndex=$(this).parent().parent().children('td[name="Index"]').children('input').val();
			var rowNum=$(this).parent().parent().children('td[name="RowIndex"]').children('input').val();
			var colNum=$(this).parent().parent().children('td[name="ColIndex"]').children('input').val();			
			
			var fP_Status=$(this).parent().parent().children('td[name="FP_Status"]').children('select').val();
			var fP_LocationType=$(this).parent().parent().children('td[name="FP_LocationType"]').children('select').val();
			var fP_LocationID=$(this).parent().parent().children('td[name="FP_LocationID"]').children('input').val();
			var fP_LocationM=$(this).parent().parent().children('td[name="FP_LocationM"]').children('input').val();
			var fP_Location=$(this).parent().parent().children('td[name="FP_Location"]').children('input').val();
			var fP_Equipment=$(this).parent().parent().children('td[name="FP_Equipment"]').children('select').val();
			var fP_EquipmentType=$(this).parent().parent().children('td[name="FP_EquipmentType"]').children('input').val();
			var fP_EquipmentID=$(this).parent().parent().children('td[name="FP_EquipmentID"]').children('input').val();
			var fP_EquipmentName=$(this).parent().parent().children('td[name="FP_EquipmentName"]').children('input').val();
			var fP_Address=$(this).parent().parent().children('td[name="FP_Address"]').children('input').val();
			var fP_JunctionID=$(this).parent().parent().children('td[name="FP_JunctionID"]').children('input').val();
			var fP_JunctionName=$(this).parent().parent().children('td[name="FP_JunctionName"]').children('input').val();
			//added
			var fP_StrandNb=$(this).parent().parent().children('td[name="FP_StrandNb"]').children('input').val();
			var fP_StrandColor=$(this).parent().parent().children('td[name="FP_StrandColor"]').children('select').val();
			var fP_StrandID=$(this).parent().parent().children('td[name="FP_StrandID"]').children('input').val();
			var fP_StrandName=$(this).parent().parent().children('td[name="FP_StrandName"]').children('input').val();
			var fP_TubeNb=$(this).parent().parent().children('td[name="FP_TubeNb"]').children('input').val();
			var fP_TubeColor=$(this).parent().parent().children('td[name="FP_TubeColor"]').children('select').val();
			var fP_TubeID=$(this).parent().parent().children('td[name="FP_TubeID"]').children('input').val();
			var fP_TubeName=$(this).parent().parent().children('td[name="FP_TubeName"]').children('input').val();
			var fP_FiberID=$(this).parent().parent().children('td[name="FP_FiberID"]').children('input').val();
			var fP_FiberName=$(this).parent().parent().children('td[name="FP_FiberName"]').children('input').val();
			
			var bP_LocationType=$(this).parent().parent().children('td[name="BP_LocationType"]').children('select').val();
			var bP_LocationID=$(this).parent().parent().children('td[name="BP_LocationID"]').children('input').val();
			var bP_LocationM=$(this).parent().parent().children('td[name="BP_LocationM"]').children('input').val();
			var bP_Location=$(this).parent().parent().children('td[name="BP_Location"]').children('input').val();
			var bP_Equipment=$(this).parent().parent().children('td[name="BP_Equipment"]').children('select').val();
			var bP_EquipmentType=$(this).parent().parent().children('td[name="BP_EquipmentType"]').children('input').val();
			var bP_EquipmentID=$(this).parent().parent().children('td[name="BP_EquipmentID"]').children('input').val();
			var bP_EquipmentName=$(this).parent().parent().children('td[name="BP_EquipmentName"]').children('input').val();
			var bP_Address=$(this).parent().parent().children('td[name="BP_Address"]').children('input').val();
			var bP_JunctionID=$(this).parent().parent().children('td[name="BP_JunctionID"]').children('input').val();
			var bP_JunctionName=$(this).parent().parent().children('td[name="BP_JunctionName"]').children('input').val();
			//
			
			var bP_Status=$(this).parent().parent().children('td[name="BP_Status"]').children('select').val();
			var bP_StrandNb=$(this).parent().parent().children('td[name="BP_StrandNb"]').children('input').val();
			var bP_StrandColor=$(this).parent().parent().children('td[name="BP_StrandColor"]').children('select').val();
			var bP_StrandID=$(this).parent().parent().children('td[name="BP_StrandID"]').children('input').val();
			var bP_StrandName=$(this).parent().parent().children('td[name="BP_StrandName"]').children('input').val();
			var bP_TubeNb=$(this).parent().parent().children('td[name="BP_TubeNb"]').children('input').val();
			var bP_TubeColor=$(this).parent().parent().children('td[name="BP_TubeColor"]').children('select').val();
			var bP_TubeID=$(this).parent().parent().children('td[name="BP_TubeID"]').children('input').val();
			var bP_TubeName=$(this).parent().parent().children('td[name="BP_TubeName"]').children('input').val();
			var bP_FiberID=$(this).parent().parent().children('td[name="BP_FiberID"]').children('input').val();
			var bP_FiberName=$(this).parent().parent().children('td[name="BP_FiberName"]').children('input').val();
			
			
		if(actiondistBoardContext=="Update"){
		   if(window["DB_Mapper"+selectedDistBoardContext] !=""){
			   portId = $(this).parent().parent().attr('id');
			   if(typeof portId == 'undefined'){ 
				   portId = "";
			   }
			   index = window["DB_Mapper"+selectedDistBoardContext].findIndex(x => x==""+rowColIndex+","+rowNum+","+colNum+","+portId+","+fP_Status+","+fP_LocationType+","+fP_LocationID+","+fP_LocationM+","+fP_Location+","+fP_EquipmentType+","+fP_Equipment+","+fP_EquipmentID+","+fP_EquipmentName+","+fP_Address+","+fP_StrandNb+","+fP_StrandID+","+fP_StrandName+","+fP_TubeNb+","+fP_TubeID+","+fP_TubeName+","+fP_FiberID+","+fP_FiberName+","+bP_Status+","+bP_LocationType+","+bP_LocationID+","+bP_LocationM+","+bP_Location+","+bP_EquipmentType+","+bP_Equipment+","+bP_EquipmentID+","+bP_EquipmentName+","+bP_Address+","+bP_StrandNb+","+bP_StrandID+","+bP_StrandName+","+bP_TubeNb+","+bP_TubeID+","+bP_TubeName+","+bP_FiberID+","+bP_FiberName);
			   if(index==-1 && !window["DB_"+portId]){
				   dict.push({
						"rowColIndex" : rowColIndex,
						"rowNum" : rowNum,								    
						"colNum" : colNum,
						"portId":portId,
						"fP_Status":fP_Status,
						"fP_LocationType":fP_LocationType,
						"fP_LocationID":fP_LocationID,
						"fP_LocationM":fP_LocationM,
						//"fP_Location":(fP_Location  == "" ||fP_Location == null ? fP_Location : "" ) ,
						"fP_Location":fP_Location,
						"fP_EquipmentType":fP_EquipmentType,
						"fP_Equipment":fP_Equipment,
						"fP_EquipmentID":fP_EquipmentID,
						"fP_EquipmentName":fP_EquipmentName,
						"fP_Address":fP_Address,
						"fP_JunctionID":fP_JunctionID,
						"fP_JunctionName":fP_JunctionName,
						//added
						"fP_StrandNb":fP_StrandNb,
						"fP_StrandColor":fP_StrandColor,
						"fP_StrandID":fP_StrandID,
						"fP_StrandName":fP_StrandName,
						"fP_TubeNb":fP_TubeNb,
						"fP_TubeColor":fP_TubeColor,
						"fP_TubeID":fP_TubeID,
						"fP_TubeName":fP_TubeName,
						"fP_FiberID":fP_FiberID,
						"fP_FiberName":fP_FiberName,
						//
						"bP_Status":bP_Status,
						//added
						"bP_LocationType":bP_LocationType,
						"bP_LocationID":bP_LocationID,
						"bP_LocationM":bP_LocationM,
						//"bP_Location":(bP_Location  == "" ||bP_Location == null ? bP_Location : "" ) ,
						"bP_Location":bP_Location,
						"bP_EquipmentType":bP_EquipmentType,
						"bP_Equipment":bP_Equipment,
						"bP_EquipmentID":bP_EquipmentID,
						"bP_EquipmentName":bP_EquipmentName,
						"bP_Address":bP_Address,
						"bP_JunctionID":bP_JunctionID,
						"bP_JunctionName":bP_JunctionName,
						//
						"bP_StrandNb":bP_StrandNb,
						"bP_StrandColor":bP_StrandColor,
						"bP_StrandID":bP_StrandID,
						"bP_StrandName":bP_StrandName,
						"bP_TubeNb":bP_TubeNb,
						"bP_TubeColor":bP_TubeColor,
						"bP_TubeID":bP_TubeID,
						"bP_TubeName":bP_TubeName,
						"bP_FiberID":bP_FiberID,
						"bP_FiberName":bP_FiberName
						});		
				   
			   }
			   else if(index==-1 && window["DB_"+portId] ||( rowNum!=window["DB_"+portId][0] || colNum!=window["DB_"+portId][1] || fP_Equipment!=window["DB_"+portId][6] || bP_StrandName!=window["DB_"+portId][8])){
					// Insert to be done
					dict.push({
						"rowColIndex" : rowColIndex,
						"rowNum" : rowNum,								    
						"colNum" : colNum,
						"portId":portId,
						"fP_Status":fP_Status,
						"fP_LocationType":fP_LocationType,
						"fP_LocationID":fP_LocationID,
						"fP_LocationM":fP_LocationM,
						"fP_Location":fP_Location,
						"fP_EquipmentType":fP_EquipmentType,
						"fP_Equipment":fP_Equipment,
						"fP_EquipmentID":fP_EquipmentID,
						"fP_EquipmentName":fP_EquipmentName,
						"fP_Address":fP_Address,
						"fP_JunctionID":fP_JunctionID,
						"fP_JunctionName":fP_JunctionName,
						//added
						"fP_StrandNb":fP_StrandNb,
						"fP_StrandColor":fP_StrandColor,
						"fP_StrandID":fP_StrandID,
						"fP_StrandName":fP_StrandName,
						"fP_TubeNb":fP_TubeNb,
						"fP_TubeColor":fP_TubeColor,
						"fP_TubeID":fP_TubeID,
						"fP_TubeName":fP_TubeName,
						"fP_FiberID":fP_FiberID,
						"fP_FiberName":fP_FiberName,
						//
						"bP_Status":bP_Status,
						//added
						"bP_LocationType":bP_LocationType,
						"bP_LocationID":bP_LocationID,
						"bP_LocationM":bP_LocationM,
						"bP_Location":bP_Location,
						"bP_EquipmentType":bP_EquipmentType,
						"bP_Equipment":bP_Equipment,
						"bP_EquipmentID":bP_EquipmentID,
						"bP_EquipmentName":bP_EquipmentName,
						"bP_Address":bP_Address,
						"bP_JunctionID":bP_JunctionID,
						"bP_JunctionName":bP_JunctionName,
						//
						"bP_StrandNb":bP_StrandNb,
						"bP_StrandColor":bP_StrandColor,
						"bP_StrandID":bP_StrandID,
						"bP_StrandName":bP_StrandName,
						"bP_TubeNb":bP_TubeNb,
						"bP_TubeColor":bP_TubeColor,
						"bP_TubeID":bP_TubeID,
						"bP_TubeName":bP_TubeName,
						"bP_FiberID":bP_FiberID,
						"bP_FiberName":bP_FiberName
					});		
	
				} 				
			
		   }
		   else{
				dict.push({
					"rowColIndex" : rowColIndex,
					"rowNum" : rowNum,								    
					"colNum" : colNum,
					"portId":portId,
					"fP_Status":fP_Status,
					"fP_LocationType":fP_LocationType,
					"fP_LocationID":fP_LocationID,
					"fP_LocationM":fP_LocationM,
					"fP_Location":fP_Location,
					"fP_EquipmentType":fP_EquipmentType,
					"fP_Equipment":fP_Equipment,
					"fP_EquipmentID":fP_EquipmentID,
					"fP_EquipmentName":fP_EquipmentName,
					"fP_Address":fP_Address,
					"fP_JunctionID":fP_JunctionID,
					"fP_JunctionName":fP_JunctionName,
					//added
					"fP_StrandNb":fP_StrandNb,
					"fP_StrandColor":fP_StrandColor,
					"fP_StrandID":fP_StrandID,
					"fP_StrandName":fP_StrandName,
					"fP_TubeNb":fP_TubeNb,
					"fP_TubeColor":fP_TubeColor,
					"fP_TubeID":fP_TubeID,
					"fP_TubeName":fP_TubeName,
					"fP_FiberID":fP_FiberID,
					"fP_FiberName":fP_FiberName,
					//
					"bP_Status":bP_Status,
					//added
					"bP_LocationType":bP_LocationType,
					"bP_LocationID":bP_LocationID,
					"bP_LocationM":bP_LocationM,
					"bP_Location":bP_Location,
					"bP_EquipmentType":bP_EquipmentType,
					"bP_Equipment":bP_Equipment,
					"bP_EquipmentID":bP_EquipmentID,
					"bP_EquipmentName":bP_EquipmentName,
					"bP_Address":bP_Address,
					"bP_JunctionID":bP_JunctionID,
					"bP_JunctionName":bP_JunctionName,
					//
					"bP_StrandNb":bP_StrandNb,
					"bP_StrandColor":bP_StrandColor,
					"bP_StrandID":bP_StrandID,
					"bP_StrandName":bP_StrandName,
					"bP_TubeNb":bP_TubeNb,
					"bP_TubeColor":bP_TubeColor,
					"bP_TubeID":bP_TubeID,
					"bP_TubeName":bP_TubeName,
					"bP_FiberID":bP_FiberID,
					"bP_FiberName":bP_FiberName
			});		
	}
		}
		   else{ 
			   dict.push({
		   
					"rowColIndex" : rowColIndex,
					"rowNum" : rowNum,								    
					"colNum" : colNum,
					"portId":portId,
					"fP_Status":fP_Status,
					"fP_LocationType":fP_LocationType,
					"fP_LocationID":fP_LocationID,
					"fP_LocationM":fP_LocationM,
					//"fP_Location":(fP_Location  == "" || fP_Location == null ? fP_Location : "" ) ,
					"fP_Location":fP_Location,
					"fP_EquipmentType":fP_EquipmentType,
					"fP_Equipment":fP_Equipment,
					"fP_EquipmentID":fP_EquipmentID,
					"fP_EquipmentName":fP_EquipmentName,
					"fP_Address":fP_Address,
					"fP_JunctionID":fP_JunctionID,
					"fP_JunctionName":fP_JunctionName,
					//added
					"fP_StrandNb":fP_StrandNb,
					"fP_StrandColor":fP_StrandColor,
					"fP_StrandID":fP_StrandID,
					"fP_StrandName":fP_StrandName,
					"fP_TubeNb":fP_TubeNb,
					"fP_TubeColor":fP_TubeColor,
					"fP_TubeID":fP_TubeID,
					"fP_TubeName":fP_TubeName,
					"fP_FiberID":fP_FiberID,
					"fP_FiberName":fP_FiberName,
					//
					
					"bP_Status":bP_Status,
					//added
					"bP_LocationType":bP_LocationType,
					"bP_LocationID":bP_LocationID,
					"bP_LocationM":bP_LocationM,
					//"bP_Location":(bP_Location  == "" ||bP_Location == null ? bP_Location : "" ) ,
					"bP_Location":bP_Location,
					"bP_EquipmentType":bP_EquipmentType,
					"bP_Equipment":bP_Equipment,
					"bP_EquipmentID":bP_EquipmentID,
					"bP_EquipmentName":bP_EquipmentName,
					"bP_Address":bP_Address,
					"bP_JunctionID":bP_JunctionID,
					"bP_JunctionName":bP_JunctionName,
					//
					"bP_StrandNb":bP_StrandNb,
					"bP_StrandColor":bP_StrandColor,
					"bP_StrandID":bP_StrandID,
					"bP_StrandName":bP_StrandName,
					"bP_TubeNb":bP_TubeNb,
					"bP_TubeColor":bP_TubeColor,
					"bP_TubeID":bP_TubeID,
					"bP_TubeName":bP_TubeName,
					"bP_FiberID":bP_FiberID,
					"bP_FiberName":bP_FiberName
					});					
		          }
			}	
		
		});
	
	
}

  function getSelectedTrenchAux(sourceLat,sourceLng){
		dict=[];
		slctDel = [];
		slctDelTrenches = [];
		dictTrenchAuxiliary=[];
		
		$("#auxiliary_trenchTable > tbody").find('input[name="record"]').each(function(){
			
		//if($(this).is(":checked")){
				
			slctDelTrenches.push($(this).parent().parent().children('td[name="auxiliaryTrench_Longitude"]').children('input').val());
		
			
			var name=$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="auxiliaryTrench_Longitude"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="auxiliaryTrench_Latitude"]').children('input').val();
			var aux_seqSorting=$(this).parent().parent().children('td[name="TrenchSeq"]').children('input').val();
			var drivingDistance = $(this).parent().parent().children('td[name="auxDrivingDistTrench"]').children('input').val();
			var geoDistance = $(this).parent().parent().children('td[name="auxGeoDistanceTrench"]').children('input').val();																									  
			if (drivingDistance == "null" || drivingDistance == null) {
				drivingDistance = 0;
			}
			if (geoDistance == "null" || geoDistance == null) {
				geoDistance = 0;	
			}
			
			distance=haversine_distance(sourceLat,sourceLng,lat,lng);
			if(name=="") {
				name = "Auxiliary_Point "+aux_seqSorting;
			}
			
				dict.push({						
					"auxTrench_Name" : name,								    
					"aux_Longitude" : lng,
					"aux_Latitude" : lat,
					"distance_From_Source":distance,
					"aux_seqSorting":aux_seqSorting,
					"drivingDistance":drivingDistance,
					"geoDistance":geoDistance
				});
		
		});
		
		if(actionTrenchContext == "Update" || trenchAction == "Append from map") {
			 if (window['trenchCheckPoints_'+selectedTrenchContext] == "checked" || window['trenchCheckRealPoints_'+selectedTrenchContext] == "checked" || checkLabel =="checked" ) {
				 showHideAllPoints(selectedTrenchContext,"trenchCheckSequence","Hide");
			 }
			 if(window['trenchCheckRealPoints_'+selectedTrenchContext] == "checked") {		
				window['trenchCheckRealPoints_'+selectedTrenchContext] = "unchecked";
			}	
			if(window['trenchCheckSequence_'+selectedTrenchContext] == "checked") {		
				window['trenchCheckSequence_'+selectedTrenchContext] = "unchecked";
			}
			if(window['trenchCheckPoints_'+selectedTrenchContext] == "checked") {		
				window['trenchCheckPoints_'+selectedTrenchContext] = "unchecked";
			}
		}
	}


  function getSelectedDuctAux(sourceLat,sourceLng){
		dict=[];
		$("#auxiliary_ductTable > tbody").find('input[name="record"]').each(function(){
		
			var name=$(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="auxiliary_LongitudeDuct"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="auxiliary_LatitudeDuct"]').children('input').val();
			var aux_seqSorting=$(this).parent().parent().children('td[name="DuctSeq"]').children('input').val();
			var drivingDistance = $(this).parent().parent().children('td[name="auxDrivingDistDuct"]').children('input').val();
			var geoDistance = $(this).parent().parent().children('td[name="auxGeoDistanceDuct"]').children('input').val();																									  
			if (drivingDistance == "null" || drivingDistance == null) {
				drivingDistance = 0;
			}
			if (geoDistance == "null" || geoDistance == null) {
				geoDistance = 0;	
			}
			
			if(name=="") {
				name = "Auxiliary_Point "+aux_seqSorting;
			}
			
			distance=haversine_distance(sourceLat,sourceLng,lat,lng);
			
				dict.push({						
					"auxDuct_Name" : name,								    
					"aux_Longitude" : lng,
					"aux_Latitude" : lat,
					"distance_From_Source":distance,
					"aux_seqSorting":aux_seqSorting,
					"drivingDistance":drivingDistance,
					"geoDistance":geoDistance
				});										 			
		});
		
		if(actionDuctContext == "Update" || ductAction == "Append from map") {
			 if (window['ductCheckPoints_'+selectedDuctContext] == "checked" || window['ductCheckRealPoints_'+selectedDuctContext] == "checked" || checkLabel =="checked" ) {
				 showHideAllPoints(selectedDuctContext,"ductCheckSequence","Hide");
			 }
			 if(window['ductCheckRealPoints_'+selectedDuctContext] == "checked") {		
				window['ductCheckRealPoints_'+selectedDuctContext] = "unchecked";
			}	
			if(window['ductCheckSequence_'+selectedDuctContext] == "checked") {		
				window['ductCheckSequence_'+selectedDuctContext] = "unchecked";
			}
			if(window['ductCheckPoints_'+selectedDuctContext] == "checked") {		
				window['ductCheckPoints_'+selectedDuctContext] = "unchecked";
			}
		}
	}
  


function selectALLDb_Mapping () {

	if ($(this).hasClass('allChecked')) {
		$('input[type="checkbox"]', '#DbMappingTable').prop('checked', false);
	} 
	else {
		$('input[type="checkbox"]', '#DbMappingTable').prop('checked', true);
	}
	$(this).toggleClass('allChecked');
	
}



  // select all mapping ports of distribution board
  function selectALL_DB_MappingRows () {

	if ($(this).hasClass('allChecked')) {

		$('input[type="checkbox"]', '#DbMappingTable').prop('checked', false);
	} 
	else {

		$('input[type="checkbox"]', '#DbMappingTable').prop('checked', true);
	}
	$(this).toggleClass('allChecked');
}


//show the tube pop up only independent to fiber 
function TubeCreatePopup(){

	var tubeChildren=$("#"+selectedFiberContext+"_f > ul").children().length;
	var TubeFiber=window[""+selectedFiberContext][17];

	if(TubeFiber>tubeChildren){

		$('#TubeModal').find('input:text').val('');
		$('#TubeModal').find('select').val('');
		$('#TubeModal').find('input:file').val('');
		$('#TubeModal').find('input:checkbox').prop("checked",false);
		$("#TubeHeader").text("Tube : ");
		$("#fiberCable").val(selectedFiberContext);
		$("#auxiliaryTableTubes > tbody").empty();		   
		// active the first tab
		$('#TubeModal ul.nav-tabs li a').removeClass('active');
		$('#TubeModal ul.nav-tabs li:first-child a').addClass('active');

		// active the first form
		$('#TubeModal .tab-pane').removeClass('active');
		$('#TubeModal .tab-pane:first-child').addClass('active');
		actionFiberContext="Insert";

		//Uncheck all autocomplete checkboxes when opening the popup
		uncheckAutoCompleteCheckboxes("auxPtTubeAutocomplete");
		uncheckAutoCompleteCheckboxes("srcDestTubeAutoComplete");		
		$("#TubeModal").modal('show');
		SourceDestinationAutoComplete("srcDestTubeAutoComplete","SourceTypeTube","SourceTube","sourcelong","sourcelat","srcCityTube","destinationlat","destinationlong","auxiliaryTableTubes","source");
		SourceDestinationAutoComplete("srcDestTubeAutoComplete","DestinationTypeTube","DestinationTube","destinationlong","destinationlat","dstCityTube","sourcelat","sourcelong","auxiliaryTableTubes","destination");
			
	}
	else{
		alert("You have reached the maximum number of tubes defined");
	}
	
}

// show the strand's popup independent to the relative fiber and tube
function StrandCreatePopup(){
	var StrandChildren=$("#"+selectedTube+"_f > ul").children().length;
	var StrandFiber=window[""+selectedFiberContext][18];
	

	if (StrandFiber>StrandChildren){

		$('#StrandModal').find('input:text').val('');
		$('#StrandModal').find('select').val('');
		$('#StrandModal').find('input:file').val('');
		$('#StrandModal').find('input:checkbox').prop("checked",false);
		$("#StrandHeader").text("Strand: ");
		$("#fibercableStrand").val(selectedFiberContext);
		$("#fibertubeStrand").val(selectedTube);
		$("#auxiliaryTableStrands > tbody").empty();
		
		// active the first tab
		$('#StrandModal ul.nav-tabs li a').removeClass('active');
		$('#StrandModal ul.nav-tabs li:first-child a').addClass('active');

		// active the first form
		$('#StrandModal .tab-pane').removeClass('active');
		$('#StrandModal .tab-pane:first-child').addClass('active');
		
		//Uncheck all autocomplete checkboxes when opening the popup
		uncheckAutoCompleteCheckboxes("auxPtStrandAutocomplete");
		uncheckAutoCompleteCheckboxes("srcDestStrandAutoComplete");
		
					
		$("#StrandModal").modal('show');
		actionFiberContext="Insert";
		SourceDestinationAutoComplete("srcDestStrandAutoComplete","SourceTypeStrand","sourcestrand","sourcelongstrand","sourcelatstrand","srcCityStrand","destinationlatstrand","destinationlongstrand","auxiliaryTableStrands","source");
		SourceDestinationAutoComplete("srcDestStrandAutoComplete","DestinationTypeStrand","destinationstrand","destinationlongstrand","destinationlatstrand","dstCityStrand","sourcelatstrand","sourcelongstrand","auxiliaryTableStrands","destination");
		
	}

	else{

		alert("You have reached the maximum number of strands defined. ");

	}	
}

/* TO BE DELETED
function TubeModalReset(){

	$("#TubeModal").modal('hide');

	$("#TubeModal").find("input,textarea,select")
			.val('').end().find("input[type=checkbox], input[type=radio]")
			.prop("checked", "")
			.end();					

	$('.pushPoints').attr("hidden",true);
	$('.cancelPoints').attr("hidden",true);	
}
*/
/* TO BE DELETED 
function StrandModalReset(){

	$("#StrandModal").modal('hide');

	$("#StrandModal").find("input,textarea,select")
			.val('').end().find("input[type=checkbox], input[type=radio]")
			.prop("checked", "")
			.end();					

	$('.pushPoints').attr("hidden",true);
	$('.cancelPoints').attr("hidden",true);	
}
*/
function ShowContextMenuGoolge(ContextMenu, eventX,eventY) {
	//console.log("Id ");
	const w = window.innerWidth;
    const h = window.innerHeight;
   
    const mw = ContextMenu.offsetWidth;
    const mh = ContextMenu.offsetHeight;
    console.log("window width "+w+" height "+h+" menu w "+mw+" menu h "+mh);
    x = eventX;
    y = eventY;
    
    if (x + mw > w) { x = x - mw; }
    if (y + mh > h) { y = y - mh; }
    //console.log(" x is "+ x+" and y is "+ y);
    ContextMenu.style.top = y + "px";
    ContextMenu.style.left = x + "px";
    ContextMenu.classList.add('show-menu');
    
}
function HideContextMenuGoolge(ContextMenu) {
    ContextMenu.classList.remove('show-menu');

}

/*function showMenu(x, y){
	MenuMap.style.left = x + 'px';
	MenuMap.style.top = y + 'px';
	MenuMap.classList.add('show-menu');
}

function hideMenu(){
	MenuMap.classList.remove('show-menu');
}

function onContextMenu(e){
	e.preventDefault();
	showMenu(e.pageX, e.pageY);
	document.addEventListener('click', onClick, false);
	
}

function onClick(e){
	hideMenu();
	document.removeEventListener('click', onClick);
	document.removeEventListener('contextmenu', hideMenu);
}*/



//show the tube /strand auxiliary popup in fiber cable popup 
function showTubeStrandAuxiliaryPopup(modalId,pathId,headerId,checkboxId){
	$('#'+modalId).find('input:file').val('');
	$('#'+modalId).find('input:checkbox').prop("checked",false);
	uncheckAutoCompleteCheckboxes(checkboxId);
	$("#"+modalId).modal('show');	 
	$("#"+headerId).text(pathId);
	
}

function showBoq(){

	var tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	var tablinks = document.getElementsByClassName("tablinks");
	
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	
	document.getElementById("Boq").style.display = "block";
	document.getElementById("boqBtn").className += " active";

	$('#boq_table').empty();
}

function showPath(){

	var tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	var tablinks = document.getElementsByClassName("tablinks");
	
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	
	document.getElementById("Path").style.display = "block";
	document.getElementById("pathBtn").className += " active";

	$('#path_table').empty();
}

// get the type of element (manhole,handhole,distribution Board)
function getTypeById(Id){
	var type=Id.substring(0,4);
	console.log("typeeeeeeeee: "+type);
	
	if(Id.substring(0,4)=="MH"){
		
		return "Manhole";
	}
	else if(Id.substring(0,4)=="HH"){

		return "Handhole";
	}
	else if(Id.substring(0,2)=="DB"){

		return "Distribution Board";
	}
	else {

		return "Unknown";
	}
}


/* TO BE DELETED
function getValueById(Id){
	var type=Id.substring(0,4);
	//console.log("typeeeeeeeee: "+type);
	
	if(Id.substring(0,4)!="MANH" ||Id.substring(0,4)=="HAND"||Id.substring(0,2)=="DB"){

		return "N/A";
	}
	
}
*/

// geocoding get city from lat lng
function fillCityByGeocoding(Id,lat,lng,geocoder){
	result="";
	
	var latlng = new google.maps.LatLng(lat,lng);
	geocoder = new google.maps.Geocoder();
	geocoder.geocode({'latLng': latlng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
	
			if (results[2]) {
				
				$("#"+Id).val(results[2].formatted_address.split(", ")[0]);
				
			}
			else if (results[3]) {
				$("#"+Id).val(results[3].formatted_address.split(", ")[0]);
				
			}
			else if (results[4]) {
				$("#"+Id).val(results[4].formatted_address.split(", ")[0]);
				
			}
			else if (results[5]) {
				$("#"+Id).val(results[5].formatted_address.split(", ")[0]);
				
			}
			else {
				alert("No results found");						
			}
			
		}
		else {
			alert("Something went wrong");
			
		}
		
	});
	
}
// fiber name 
$("#fiberName").on("input",function(){
	$("#fiberHeader").text("Fiber Cable: "+$(this).val());
});

// For tube name

$("#tubeName").on("input",function(){	
	$("#TubeHeader").text("Tube: "+$(this).val());
});


// For Strand name 

$("#StrandName").on("input",function(){	
	$("#StrandHeader").text("Strand: "+$(this).val());
});

$("#sortByDistance").on('click',function(){
	console.log("clicked sort button");
	sortDictionary();
	
});
function HandManJuncFormat(ManHandhole,IdSelected,ID) {
		
	var childrenInitial=$("#initial_ul_"+IdSelected+"").find(' > ul > li');
	//var children = $("#"+ManHandhole+"_f_"+IdSelected+"").find(' > ul > li');
	var child = $("#"+ManHandhole+"_f_"+IdSelected+"").find(' > ul > li');
	
	if (!child.is(":visible") || $("#"+ManHandhole+"_f_"+IdSelected+"").children('.Parentfolder').find('> svg').hasClass('fa-folder') ) {
		
		if(IdNodeSelectedTemp=="CurrentPhysicalLayer") {
			$("#"+ManHandhole+"_f_"+IdSelected+"").children('.Parentfolder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');
			$("#initial_ul_"+IdSelected+"").children('.Parentfolder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');			
		}
		else {
			var projectInitial=$("#initial_ul_Projects").find('>ul > #'+IdSelected);
			var projectRel=$("#"+IdSelected+"").find('>ul > #initial_ul_'+IdSelected);
			projectRel=$("#initial_ul_"+IdSelected);
		
			$("#initial_ul_Projects").children('.Parentfolder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');
			$("#"+IdSelected+"").children('.Parentfolder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open'); // The Project it self, example: Chebaa Project with ID: Project-2022-1
			$("#initial_ul_"+IdSelected).children('.Parentfolder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open'); // Physical Layer under Project
			$("#"+ManHandhole+"_f_"+IdSelected+"").children('.Parentfolder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open'); // Manhole or Handhole folder under the Physical Layer which is under the Project Name			
			
			projectInitial.show('fast');
			projectRel.show('fast');			
		}
	}
	child.show('fast');
//	children.show('fast');
	childrenInitial.show('fast');	
	
	if(IdNodeSelectedTemp=="CurrentPhysicalLayer") {
		projectOffset=$("#initial_ul_"+IdSelected).offset().top;
	}
	else {
		projectOffset=($("#initial_ul_CurrentPhysicalLayer").offset().top)-($("#initial_ul_"+IdSelected).offset().top);
	}	
	// scroll to the created handhole/manhole/jct
	offset=$("#"+ID).offset().top;
	offsetTotal=(offset-projectOffset);
	$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
}


function ClosingConfirm(){
var c=null;
if($("#handholeModal").is(':visible')){
c=$("#handholeModal");}
else if($("#manholeModal").is(':visible')){
c=$("#manholeModal");}
else if($("#manholeJunctionModal").is(':visible')){
c=$("#manholeJunctionModal");}
else if($("#handholeJunctionModal").is(':visible')){
c=$("#handholeJunctionModal");}
else if($("#fiberPathModal").is(':visible')){
c=$("#fiberPathModal");}
else if($("#distributionBoardModal").is(':visible')){
c=$("#distributionBoardModal");}
else if($("#trenchModal").is(':visible')){
c=$("#trenchModal");}
else if($("#projectModal").is(':visible')){
c=$("#projectModal");}
else if($("#TubeModal").is(':visible')){
c=$("#TubeModal");}
else if($("#fiberCitySearch").is(':visible')){
c=$("#fiberCitySearch");}
else if($("#StrandModal").is(':visible')){
c=$("#StrandModal");}
else if($("#ductModal").is(':visible')){
c=$("#ductModal");}
else if($("#mapOperationModal").is(':visible')){
c=$("#mapOperationModal");}
else if($("#DeleteModal").is(':visible')){
c=$("#DeleteModal");}
else if($("#dB_MappingModal").is(':visible')){
c=$("#dB_MappingModal");}
else if($("#changeCableColorModal").is(':visible')){
c=$("#changeCableColorModal");}
else if($("#distributionBoardLoaderModal").is(':visible')){
c=$("#distributionBoardLoaderModal");}
else if($("#LoaderConfirmationModal").is(':visible')){
c=$("#LoaderConfirmationModal");}
else if($("#nodesModal").is(':visible')){
c=$("#nodesModal");}
var result= confirm('are you sure you want to close?')
	if (result== false){
		c.modal('show');
		}
	 if (result== true){
	   c.modal('hide');
	   localStorage.clear();
	}
}

function mapOperationAutoComplete(checkboxClass,srcID,srcLong,srcLat){

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
       		"WareName":search,
			"warehouseName" : search,
			"SiteId":search,
		 }
	}
	
	else if(checkedCheckboxAutocomplete=="client") {

		url='GetAllNetworkClients';
		dataTarget = {					
				"search":search,
			}
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
			}	
			else if(ui.item[0].split("_")[0]=="HH"){
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				$("#"+srcLong).val(ui.item[3]);
				$("#"+srcLat).val(ui.item[4]);
				}
			else if(ui.item[0].split("_")[0]=="DB"){
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
				$("#"+srcLong).val(ui.item[3]);
				$("#"+srcLat).val(ui.item[4]);
			}		
			else if(ui.item[0].split("_")[0]=="WARE"){
				$("#"+srcLong).val(ui.item[3]);
				$("#"+srcLat).val(ui.item[4]);
				this.value = (ui.item ? ui.item[0]+':'+ui.item[1]+':'+ui.item[2] : '');
		}	
		else{
			this.value = (ui.item ? ui.item[0]+':'+ui.item[1]+':'+ui.item[2] : '');
			$("#"+srcLong).val(ui.item[4]);
			$("#"+srcLat).val(ui.item[5]);
			}	
			return false;
		},
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
				
					if(item[0].split("_")[0]=="WARE" || item[0].split("_")[0]=="CLT" ) {
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
		$("#"+srcID).focus(function(){
			   if (this.value == ""){
				   $(this).autocomplete("search");
			   }						
		});
} 
function addNetworkLevel(pathID,target) {
	 $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: getContext()+'/getNetworkLevel',
        data: {
		 "pathID":pathID,
		 "target":target
	 	},
        dataType: "json",
        success: function (data) {
			$("#frm"+target+"netlevel").val(""+data.networkLevel[0]);            
        },
        error: function(result) {
            alert("Error");
        }
    });
	 
}
function placeAutoComplete() {


	      var address = (document.getElementById('placeSearch'));
	      var autocomplete = new google.maps.places.Autocomplete(address);
	      autocomplete.setTypes(['geocode']);
	      google.maps.event.addListener(autocomplete, 'place_changed', function() {
	          var place = autocomplete.getPlace();
	          if (!place.geometry) {
	              return;
	          }

	      var address = '';
	      if (place.address_components) {
	          address = [
	              (place.address_components[0] && place.address_components[0].short_name || ''),
	              (place.address_components[1] && place.address_components[1].short_name || ''),
	              (place.address_components[2] && place.address_components[2].short_name || '')
	              ].join(' ');
	      }
	      document.getElementById('Lat').value = place.geometry.location.lat();
	      document.getElementById('Lng').value = place.geometry.location.lng();
	
	      
	      });
	

	 google.maps.event.addDomListener(window, 'load', placeAutoComplete);
	 }
	 
function numberofselectedFiber(layer,subLayer,selectedIdContext){

	selectedFiberTubeStrandPath=[]; 
	selectedFiberTubeStrandContext =""; 
	checkIfLayerDel=""; 
	var layerCheckedLength; 
	var subLayerCheckedLength; 
	checkedFiberStrand=[]; 
	checkedFiberTube_TubeStrand=[]; 
	//////////////////////////////////////////////////			
	if(layer == "FiberCable" || subLayer=="FiberCable" || layer=="FiberTube" || subLayer=="FiberTube" || layer=="FiberStrand" || subLayer=="FiberStrand"){
		
		if (subLayer==""){
			
			layerCheckedLength= $("."+layer+":checked").length;
			selectedFiberTubeStrandPath.push(selectedIdContext);
			console.log("111");//delete single item fiber/tube strand
		}
		else{
			subLayerCheckedLength=$("."+subLayer+":checked").length;
			if (subLayer == "FiberCable"){
		    	selectedFiberTubeStrandContext = "#FiberPath_f_"+selectedIdContext+"";
		    }
		    else{
		    	selectedFiberTubeStrandContext = "#"+selectedIdContext+"";
		    }
			
				if($("."+layer+":checked").length>0){
					$(selectedFiberTubeStrandContext).find(' > ul > li > ul > li ').each(function(){
						if($(this).find('input:checkbox').is(':checked')) {						
							selectedFiberTubeStrandPath.push($(this).attr('id'));
						}
					});	
					
				}
				
				else {
					
					if($("."+subLayer+":checked").length >0){
						$(selectedFiberTubeStrandContext).find(' > ul > li ').each(function(){	
							if($(this).find('input:checkbox').is(':checked')) {		
								selectedFiberTubeStrandPath.push($(this).attr('id'));
							
							}
			    		});
					}	
				} }
		}
	}
	 
function exists(arr, search) {
    return arr.some(row => row.includes(search));
}

function numberofselected(layer,subLayer,selectedIdContext){

allSelectedLayer=[];
	if (subLayer==""){		
		if($("."+layer+":checked").length >0){
			$("."+layer+"").each(function () {
				if ($(this).is(':checked')) {
			    	allSelectedLayer.push($(this).parent().attr('id'));
			     }
    		});
    	}
    	
    	else {
    		allSelectedLayer.push(selectedIdContext);
    		 if (layer=="Project"){
           	  var allSelectedLayerProject = allSelectedLayer[0].split("Project_span_")[1];
             	  allSelectedLayer=[];
             	  allSelectedLayer.push(allSelectedLayerProject);           	
             }
    	}
    	
    	}
    	
    	else{
    	
	    	if($("."+layer+":checked").length>0){
				$("#"+subLayer+"_f_"+selectedIdContext+"").find(' > ul > li ').each(function(){			
		    		console.log("yessssssss ");			
					allSelectedLayer.push($(this).attr('id'));
							
				}); 
	    		
	    	}
	    	
	    	else {
	    		//if($("."+subLayer+":checked").length >0){	
	    			$("#"+subLayer+"_f_"+selectedIdContext+"").find(' > ul > li ').each(function(){	
						if($(this).find('input:checkbox').is(':checked')) {		
							allSelectedLayer.push($(this).attr('id'));
						}
		    		});
	    		//}
	    		
	    		 
	    	
	    	}// end unchecked main checkbox
    	}// end else delete from main physical layer
    	
	 }
	 
function numberofselectedTrench(layer,subLayer,selectedTrenchContext){
	selectedTrenchesDuctsForDel=[];
	selectedDuctsForDel=[];
	selectedTrenchDuctContext=[];
	subLayerCheckedLength = 0, layerCheckedLength = 0;
	
	// deleting from main or single trench/Duct
	if(layer == "TRENCH" || subLayer=="TRENCH" || layer == "DUCT" || subLayer=="DUCT"){
		
		    if (subLayer=="TRENCH"){// if trench is the main
		    	selectedTrenchDuctContext = "#Trench_f_"+selectedTrenchContext+"";
		    }
		    else{// the main is duct 
		    	selectedTrenchDuctContext = "#"+selectedTrenchContext+"";
		    }
				if($("."+layer+":checked").length>0){
					$(selectedTrenchDuctContext).find(' > ul > li ').each(function(){
						if($(this).find('input:checkbox').is(':checked')) {						
						selectedTrenchesDuctsForDel.push($(this).attr('id'));
						console.log("case 2");	
						}
					});
					
				}
				
				else {
					console.log(subLayer+" checked "+$("."+subLayer+":checked").length);
					if($("."+subLayer+":checked").length >0){
						$(selectedTrenchDuctContext).find(' > ul > li ').each(function(){	
							if($(this).find('input:checkbox').is(':checked')) {		
								selectedTrenchesDuctsForDel.push($(this).attr('id'));
								console.log("case 3");	
							
							}
			    		});
					}
					
				}
				
				if (subLayer=="TRENCH"){
					if(selectedTrenchesDuctsForDel.length > 0){
						for(d=0;d<selectedTrenchesDuctsForDel.length;d++){
							$("#"+selectedTrenchesDuctsForDel[d]).find(' > ul > li >ul >li').each(function(){
								if($(this).find('input:checkbox').is(':checked')) {	
									selectedDuctsForDel.push($(this).attr('id'));	
									console.log("case 5");	
								}
						    });
					    }
					
					}
		      }
		}
	
		console.log("len to be deleted "+selectedTrenchesDuctsForDel.length);
	
}
	 
function deleteprop(layer,subLayer,selectedIdContext){
layer_arr=[];
layer_arr.push(layer);
layer_arr.push(subLayer);
layer_arr.push(selectedIdContext);
}

function DBMappingData(DistBoardMappingPts){

		if(DistBoardMappingPts){
			window["DB_Mapper"+selectedDistBoardContext]=[];
			window["DB_Mapper"+selectedDistBoardContext]=DistBoardMappingPts;
			//console.log("db mapper "+window["DB_Mapper"+selectedDistBoardContext]);
			var addMark="";
			var addMark2="";
			var addMark3="";
			var fp_strandcolor="";
			var bp_strandcolor="";
			var fp_tubecolor="";
			var bp_tubecolor="";
			var addMark4='<option value='+'""'+' style='+'"background-color: white;"'+'></option>'+	
							'<option value='+'"blue"'+' style='+'"background-color: white; color:black"'+'>blue</option>'+
							'<option value='+'"orange"'+' style='+'"background-color: white; color:black"'+'>orange</option>'+
							'<option value='+'"green"'+' style='+'"background-color: white; color:black"'+'>green</option>'+
							'<option value='+'"brown"'+' style='+'"background-color: white; color:black"'+'>brown</option>'+
							'<option value='+'"gray"'+' style='+'"background-color: white; color:black"'+'>gray</option>'+
							'<option value='+'"white"'+' style='+'"background-color: white; color:black"'+'>white</option>'+
							'<option value='+'"red"'+' style='+'"background-color: white; color:black"'+'>red</option>'+
							'<option value='+'"black"'+' style='+'"background-color: white; color:black"'+'>black</option>'+
							'<option value='+'"yellow"'+' style='+'"background-color: white; color:black"'+'>yellow</option>'+
							'<option value='+'"violet"'+' style='+'"background-color: white; color:black"'+'>violet</option>'+
							'<option value='+'"pink"'+' style='+'"background-color: white; color:black"'+'>pink</option>'+
							'<option value='+'"aqua"'+' style='+'"background-color: white; color:black"'+'>aqua</option>';
							
			dBBoqIndex=0;			
			
			
			for(i=0;i<DistBoardMappingPts.length;i++){
				
				/*
				if(DistBoardMappingPts[i][3]=="Customer"){
					addMark += "<option value='Customer'>Customer</option>"
							+"<option value='Site'>Site</option>"
							+"<option value='Manhole'>Manhole</option>"
							+"<option value='Handhole'>Handhole</option>";
					
				} else if(DistBoardMappingPts[i][3]=="Site"){
					addMark += "<option value='Site'>Site</option>"
							+"<option value='Customer'>Customer</option>"
							+"<option value='Manhole'>Manhole</option>"
							+"<option value='Handhole'>Handhole</option>";
					
				}else if(DistBoardMappingPts[i][3]=="Manhole"){
					addMark += "<option value='Manhole'>Manhole</option>"
							+"<option value='Site'>Site</option>"
							+"<option value='Customer'>Customer</option>"
							+"<option value='Handhole'>Handhole</option>";
					
				} else if(data.DistBoardMappingPts[i][3]=="Handhole"){
					addMark += "<option value='Handhole'>Handhole</option>"
							+"<option value='Site'>Site</option>"
							+"<option value='Manhole'>Manhole</option>"
							+"<option value='Customer'>Customer</option>";
				}
			*/
		
			
			var locationTypeOption = DistBoardMappingPts[i][5];
			locationOptions = "<select class='form-control' name='FP_locationType' id='FP_LocationType"+dBBoqIndex+"'>";
			locationOptionValue = "Select an Option";
			var locationArray = ["Select an Option","Customer","Site","Manhole","Handhole"];
			
			for(j=0; j<locationArray.length; j++)
			{
				if(locationTypeOption == locationArray[j]){
					selected_option = "selected='selected'";
				}
				else{
					selected_option = "";
				}
				if(locationArray[j] != 'Select an Option'){
					locationOptionValue = locationArray[j];
				}
				locationOptions += "<option value='"+locationOptionValue+"' "+selected_option+" >"+locationArray[j]+"</option>";
			}
			locationOptions += "</select>";
			
					
			
					if (DistBoardMappingPts[i][5]=="Customer"){
						addMark2 = "<option value='Custom'>Custom</option>"
						+"<option value='Node'>Node</option>"
						+"<option value='DistBoard'>DB</option>"
								
							
					} else if (DistBoardMappingPts[i][5]=="Site"){
						addMark2 = "<option value='Node'>Node</option>"
						+"<option value='DistBoard'>DB</option>"
			
							
					} 
					else if (DistBoardMappingPts[i][5]=="Manhole" || DistBoardMappingPts[i][5]=="Handhole"){
						addMark2 = "<option value='Node'>Node</option>"
						+"<option value='DistBoard'>DB</option>"
			
							
					} 
			   
					window["DB_"+DistBoardMappingPts[i][3]]=[];
					window["DB_"+DistBoardMappingPts[i][3]]=DistBoardMappingPts[i];			
				/*	var markup = "<tr id='"+DistBoardMappingPts[i][4]+"'><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
					+"<td name='RowIndex'><input name='rowIndex' value='"+DistBoardMappingPts[i][0]+"'  class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
					+"<td name='ColIndex'><input name='colIndex' value='"+data.DistBoardMappingPts[i][1]+"'  class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
					+"<td name='FrontPort'><input name='frontPort' id='Front"+dBBoqIndex+"' value='"+data.DistBoardMappingPts[i][2]+"'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
					
					+ "<td name='BackPort'> <input name='frontPort' id='Back"+dBBoqIndex+"' value='"+data.DistBoardMappingPts[i][3]+"' style='width:190px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>"
					*/
					
				////added	
					var BP_locationTypeOption = DistBoardMappingPts[i][27];
			   		BP_locationOptions = "<select class='form-control' name='BP_locationType' id='BP_LocationType"+dBBoqIndex+"'>";
			   		BP_locationOptionValue = "Select an Option";
			   		var BP_locationArray = ["Select an Option","Customer","Site","Manhole","Handhole"];
					
			   	   	for(j=0; j<BP_locationArray.length; j++)
					{
						if(BP_locationTypeOption == BP_locationArray[j]){
							selected_option = "selected='selected'";
						}
						else{
							selected_option = "";
						}
						if(BP_locationArray[j] != 'Select an Option'){
							BP_locationOptionValue = BP_locationArray[j];
						}
						BP_locationOptions += "<option value='"+BP_locationOptionValue+"' "+selected_option+" >"+BP_locationArray[j]+"</option>";
					}
			   	 BP_locationOptions += "</select>";
			   	 
			   	if (DistBoardMappingPts[i][27]=="Customer"){
					addMark3 = "<option value='Custom'>Custom</option>"
					+"<option value='Node'>Node</option>"
					+"<option value='DistBoard'>DB</option>"
								
						
				} else if (DistBoardMappingPts[i][27]=="Site"){
					addMark3 = "<option value='Node'>Node</option>"
					+"<option value='DistBoard'>DB</option>"
			
						
				} 
				else if (DistBoardMappingPts[i][27]=="Manhole" || DistBoardMappingPts[i][27]=="Handhole"){
					addMark3 = "<option value='Node'>Node</option>"
					+"<option value='DistBoard'>DB</option>"
			
						
				} 
					////
					
					    var f_statusOption = "", b_statusOption = "";
					    if (DistBoardMappingPts[i][4]=="Active"){
							f_statusOption = "<option value='Active' selected >Active</option><option value='InActive'>Inactive</option>";
						}
					    else if (DistBoardMappingPts[i][4]=="InActive")
					    {
							f_statusOption = "<option value='InActive' selected >Inactive</option><option value='Active'>Active</option>";
					    }
					    else
					    {
					    	f_statusOption = "<option value='None' selected>Select an Option</option><option value='Active'>Active</option><option value='InActive'>Inactive</option>";
					    }
					    	
						if(DistBoardMappingPts[i][14]== "Active"){
							b_statusOption = "<option value='Active' selected >Active</option><option value='InActive'>Inactive</option>";
						}
					    else if (DistBoardMappingPts[i][14]=="InActive") {
					    	b_statusOption = "<option value='InActive' selected >Inactive</option><option value='Active'>Active</option>";
					    }
						else{
							b_statusOption = "<option value='None' selected>Select an Option</option><option value='Active'>Active</option><option value='InActive'>Inactive</option>";
						}
							
					var markup = "<tr id='"+DistBoardMappingPts[i][3]+"''><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
					    +"<td name='Index'><input name='Index' value='"+DistBoardMappingPts[i][0]+"' class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
					    +"<td name='RowIndex'><input name='rowIndex' value='"+DistBoardMappingPts[i][1]+"'  class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
						+"<td name='ColIndex'><input name='colIndex' value='"+DistBoardMappingPts[i][2]+"'  class='form-control text-input' type='text' style='width:60px;position:relative;'/></td>"
					
						+"<td style='background-color:#00757C' width='-10px'></td>"
						+"<td name='FP_Status'><select class='form-control' name='FP_Status' id='FP_Status"+dBBoqIndex+"'>"+f_statusOption+"</select></td>"
						+"<td name='FP_LocationType'>"+locationOptions+"</td>"
						
						//+"<td name='FP_LocationType'>"
						//	+"<select class='form-control' name='FP_locationType' id='FP_LocationType"+dBBoqIndex+"'>"+addMark+"</select>"
						//+"</td>"
						+"<td name='FP_LocationID'><input name='FP_locationID' value='"+DistBoardMappingPts[i][6]+"' id='FP_LocationID"+dBBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_LocationM'><input name='FP_locationM' value='"+DistBoardMappingPts[i][7]+"' id='FP_LocationM"+dBBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_Location'><input name='FP_location' value='"+DistBoardMappingPts[i][8]+"' id='FP_Location"+dBBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_Equipment'>"
							+"<select class='form-control' name='FP_equipment' id='FP_equipment"+dBBoqIndex+"'>"+addMark2+"</select>"
						+"<td name='FP_EquipmentType'><input name='FP_equipmentType' value='"+DistBoardMappingPts[i][9]+"' id='FP_equipmentType"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_EquipmentID'><input name='FP_equipmentID' value='"+DistBoardMappingPts[i][11]+"' id='FP_equipmentID"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_EquipmentName'><input name='FP_equipmentName' value='"+DistBoardMappingPts[i][12]+"' id='FP_equipmentName"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_Address'><input name='FP_Address' value='"+DistBoardMappingPts[i][13]+"' id='FP_Address"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						
						+"<td name='FP_JunctionID'><input name='FP_junctionID' value='"+DistBoardMappingPts[i][44]+"' id='FP_junctionID"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_JunctionName'><input name='FP_junctionName' value='"+DistBoardMappingPts[i][45]+"' id='FP_junctionName"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						
						//added
						+"<td name='FP_StrandNb'><input name='FP_strandNb' value='"+DistBoardMappingPts[i][36]+"' id='FP_strandNb"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
						+"<td name='FP_StrandColor'>"
							+"<select class='form-control' name='FP_strandcolor' id='FP_strandcolor"+dBBoqIndex+"'>"+addMark4+"</select>"
						+"<td name='FP_StrandID'><input name='FP_strandID' value='"+DistBoardMappingPts[i][21]+"' id='FP_strandID"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_StrandName'><input name='FP_strandName' value='"+DistBoardMappingPts[i][22]+"' id='FP_strandName"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='FP_TubeNb'><input name='FP_tubeNb' value='"+DistBoardMappingPts[i][37]+"' id='FP_tubeNb"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
						+"<td name='FP_TubeColor'>"
							+"<select class='form-control' name='FP_tubecolor' id='FP_tubecolor"+dBBoqIndex+"'>"+addMark4+"</select>"
						+"<td name='FP_TubeID'><input name='FP_tubeID' value='"+DistBoardMappingPts[i][23]+"' id='FP_tubeID"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
						+"<td name='FP_TubeName'><input name='FP_tubeName' value='"+DistBoardMappingPts[i][24]+"' id='FP_tubeName"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
						+"<td name='FP_FiberID'><input name='FP_fiberID' value='"+DistBoardMappingPts[i][25]+"' id='FP_fiberID"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
						+"<td name='FP_FiberName'><input name='FP_fiberName' value='"+DistBoardMappingPts[i][26]+"' id='FP_fiberName"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
						//					
						+"<td style='background-color:#00757C' width='-10px'></td>"
						+"<td name='BP_Status'><select class='form-control' name='BP_Status' id='BP_Status"+dBBoqIndex+"'>"+b_statusOption+"</select></td>"
						//added
						+"<td name='BP_LocationType'>"+BP_locationOptions+"</td>"
						+"<td name='BP_LocationID'><input name='BP_locationID' value='"+DistBoardMappingPts[i][28]+"' id='BP_LocationID"+dBBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_LocationM'><input name='BP_locationM' value='"+DistBoardMappingPts[i][29]+"' id='BP_LocationM"+dBBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_Location'><input name='BP_location' value='"+DistBoardMappingPts[i][30]+"' id='BP_Location"+dBBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_Equipment'>"
							+"<select class='form-control' name='BP_equipment' id='BP_equipment"+dBBoqIndex+"'>"+addMark3+"</select>"
						+"<td name='BP_EquipmentType'><input name='BP_equipmentType' value='"+DistBoardMappingPts[i][31]+"' id='BP_equipmentType"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_EquipmentID'><input name='BP_equipmentID' value='"+DistBoardMappingPts[i][33]+"' id='BP_equipmentID"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_EquipmentName'><input name='BP_equipmentName' value='"+DistBoardMappingPts[i][34]+"' id='BP_equipmentName"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_Address'><input name='BP_Address' value='"+DistBoardMappingPts[i][35]+"'id='BP_Address"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						
						+"<td name='BP_JunctionID'><input name='BP_junctionID' value='"+DistBoardMappingPts[i][46]+"' id='BP_junctionID"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_JunctionName'><input name='BP_junctionName' value='"+DistBoardMappingPts[i][47]+"' id='BP_junctionName"+dBBoqIndex+"' class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						//
						+"<td name='BP_StrandNb'><input name='BP_strandNb' value='"+DistBoardMappingPts[i][38]+"' id='BP_strandNb"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
						+"<td name='BP_StrandColor'>"
							+"<select class='form-control' name='BP_strandcolor' id='BP_strandcolor"+dBBoqIndex+"'>"+addMark4+"</select>"
						+"<td name='BP_StrandID'><input name='BP_strandID' value='"+DistBoardMappingPts[i][15]+"' id='BP_strandID"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_StrandName'><input name='BP_strandName' value='"+DistBoardMappingPts[i][16]+"' id='BP_strandName"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;'/></td>"
						+"<td name='BP_TubeNb'><input name='BP_tubeNb' value='"+DistBoardMappingPts[i][39]+"' id='BP_tubeNb"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:80px;position:relative;'/></td>"
						+"<td name='BP_TubeColor'>"
							+"<select class='form-control' name='BP_tubecolor' id='BP_tubecolor"+dBBoqIndex+"'>"+addMark4+"</select>"
						+"<td name='BP_TubeID'><input name='BP_tubeID' value='"+DistBoardMappingPts[i][17]+"' id='BP_tubeID"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
						+"<td name='BP_TubeName'><input name='BP_tubeName' value='"+DistBoardMappingPts[i][18]+"' id='BP_tubeName"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
						+"<td name='BP_FiberID'><input name='BP_fiberID' value='"+DistBoardMappingPts[i][19]+"' id='BP_fiberID"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td>"
						+"<td name='BP_FiberName'><input name='BP_fiberName' value='"+DistBoardMappingPts[i][20]+"' id='BP_fiberName"+dBBoqIndex+"'  class='form-control text-input' type='text' style='width:190px;position:relative;' /></td></tr>"
						$("#DbMappingTable > tbody").append(markup);
											//autoCompleteUpdated("Front"+dBBoqIndex,"DbMappingTable",DistBoardMappingPts[i][4],"SearchForStrand");
											//autoCompleteUpdated("Back"+dBBoqIndex,"DbMappingTable",DistBoardMappingPts[i][4],"SearchForStrand");
				
				
				
					if(DistBoardMappingPts[i][40] !=null){
						$("#FP_strandcolor"+dBBoqIndex).val(DistBoardMappingPts[i][40]);
					}
					
					if(DistBoardMappingPts[i][41] !=null){
						$("#FP_tubecolor"+dBBoqIndex).val(DistBoardMappingPts[i][41]);
					}
					
					
					if(DistBoardMappingPts[i][42] !=null){
						$("#BP_strandcolor"+dBBoqIndex).val(DistBoardMappingPts[i][42]);
					}
					
					
					if(DistBoardMappingPts[i][43] !=null){
						$("#BP_tubecolor"+dBBoqIndex).val(DistBoardMappingPts[i][43]);
					}
					
					if(DistBoardMappingPts[i][10] !=null){
					$("#FP_equipment"+dBBoqIndex).val(DistBoardMappingPts[i][10]);
					}
					
					if(DistBoardMappingPts[i][32] !=null){
						$("#BP_equipment"+dBBoqIndex).val(DistBoardMappingPts[i][32]);
					}
			
			var FP_LocType = $('#FP_LocationType'+dBBoqIndex).find('option:selected').text();
			if(FP_LocType=="Manhole" || FP_LocType=="Handhole" || FP_LocType=="Customer"){
				$('#FP_Location'+dBBoqIndex).prop("readonly", true);
				$('#FP_Location'+dBBoqIndex).val('');
			}	
			
			var BP_LocType = $('#BP_LocationType'+dBBoqIndex).find('option:selected').text();
			if(BP_LocType=="Manhole" || BP_LocType=="Handhole" || BP_LocType=="Customer"){
				$('#BP_Location'+dBBoqIndex).prop("readonly", true);
				$('#BP_Location'+dBBoqIndex).val('');
			}	
					
			$("#FP_LocationType"+dBBoqIndex).change(function(){
						var thisID = $(this).attr("id");
						console.log(thisID+"kkkkkkkkkk");
						console.log("the Index is " +(parseInt(dBBoqIndex)-1));
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('FP_LocationType','');
						//var xyz=$('#FP_LocationType'+indexFor).find('option:selected').text();
						//console.log("this loctaion "+xyz);
						$('#FP_LocationM'+indexFor).val('');
						$('#FP_Location'+indexFor).val('');
						$('#FP_LocationID'+indexFor).val('');
						if($(this).val()=="Customer"){					
							$('#FP_equipment'+indexFor).children('option').remove();
							$('#FP_equipment'+indexFor).append("<option value='none'>select an option</option><option value='Custom'>Custom</option><option value='Node'>Node</option><option value='DistBoard'>DB</option>");
							$('#FP_equipmentType'+indexFor).val('');	
							$('#FP_Location'+indexFor).prop("readonly", true);					
						} else if($(this).val()=="Manhole" || $(this).val()=="Handhole"){
							$('#FP_equipment'+indexFor).children('option').remove();
							$('#FP_equipment'+indexFor).append("<option value='Node'>Node</option><option value='DistBoard'>DB</option>");
						    $('#FP_equipmentType'+indexFor).val('');
						    $('#FP_Location'+indexFor).prop("readonly", true);
						}else if( $(this).val()=="Site") {
							$('#FP_equipment'+indexFor).children('option').remove();
							$('#FP_equipment'+indexFor).append("<option value='Node'>Node</option><option value='DistBoard'>DB</option>");
						    $('#FP_equipmentType'+indexFor).val('');
						    $('#FP_Location'+indexFor).prop("readonly", false);
						}else if($(this).val()=="None"){
							$('#FP_equipment'+indexFor).children('option').remove();
							$('#FP_equipmentType'+indexFor).val('');
						}
					});
				
				$("#BP_LocationType"+dBBoqIndex).change(function(){
					var thisID = $(this).attr("id");
					console.log(thisID+"kkkkkkkkkk");
					console.log("the Index is " +(parseInt(dBBoqIndex)-1));
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('BP_LocationType','');
					$('#BP_LocationM'+indexFor).val('');
					$('#BP_Location'+indexFor).val('');
					$('#BP_LocationID'+indexFor).val('');
					if($(this).val()=="Customer"){					
						$('#BP_equipment'+indexFor).children('option').remove();
						$('#BP_equipment'+indexFor).append("<option value='none'>select an option</option><option value='Custom'>Custom</option><option value='Node'>Node</option><option value='DistBoard'>DB</option>");
						$('#BP_equipmentType'+indexFor).val('');
						$('#BP_Location'+indexFor).prop("readonly", true);						
					} else if($(this).val()=="Manhole" || $(this).val()=="Handhole"){
						$('#BP_equipment'+indexFor).children('option').remove();
						$('#BP_equipment'+indexFor).append("<option value='Node'>Node</option><option value='DistBoard'>DB</option>");
					    $('#BP_equipmentType'+indexFor).val('');
					    $('#BP_Location'+indexFor).prop("readonly", true);
					}else if($(this).val()=="Site"){
						$('#BP_equipment'+indexFor).children('option').remove();
						$('#BP_equipment'+indexFor).append("<option value='Node'>Node</option><option value='DistBoard'>DB</option>");
					    $('#BP_equipmentType'+indexFor).val('');
					    $('#BP_Location'+indexFor).prop("readonly", false);
					} else if($(this).val()=="None"){
						$('#BP_equipment'+indexFor).children('option').remove();
						$('#BP_equipmentType'+indexFor).val('');
					}
				});
					
					$("#BP_tubecolor"+dBBoqIndex).change(function(){
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('BP_tubecolor','');
					colorId="BP_tubecolor"+indexFor;
					numberId="BP_tubeNb"+indexFor;
					tubeStrandSetColor(colorId,numberId);	 
				});
				
				$("#FP_tubecolor"+dBBoqIndex).change(function(){
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('FP_tubecolor','');
					colorId="FP_tubecolor"+indexFor;
					numberId="FP_tubeNb"+indexFor;
					tubeStrandSetColor(colorId,numberId);	 
				});
				
				$("#BP_strandcolor"+dBBoqIndex).change(function(){
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('BP_strandcolor','');
					colorId="BP_strandcolor"+indexFor;
					numberId="BP_strandNb"+indexFor;
					tubeStrandSetColor(colorId,numberId);
				});
				
				$("#FP_strandcolor"+dBBoqIndex).change(function(){
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('FP_strandcolor','');
					colorId="FP_strandcolor"+indexFor;
					numberId="FP_strandNb"+indexFor;
					tubeStrandSetColor(colorId,numberId);
				});
				
				document.getElementById("FP_tubeNb"+dBBoqIndex).addEventListener ("input" ,function() {
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('FP_tubeNb','');
					colorId="FP_tubecolor"+indexFor;
					numberId="FP_tubeNb"+indexFor;
					number=document.getElementById(numberId).value;
					strandTubeSetColor(number,colorId);
				
				});
				
				document.getElementById("BP_tubeNb"+dBBoqIndex).addEventListener ("input" ,function() {
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('BP_tubeNb','');
					colorId="BP_tubecolor"+indexFor;
					numberId="BP_tubeNb"+indexFor;
					number=document.getElementById(numberId).value;
					strandTubeSetColor(number,colorId);
				
				});
				
				document.getElementById("FP_strandNb"+dBBoqIndex).addEventListener ("input" ,function() {
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('FP_strandNb','');
					colorId="FP_strandcolor"+indexFor;
					numberId="FP_strandNb"+indexFor;
					number=document.getElementById(numberId).value;
					strandTubeSetColor(number,colorId);
				
				});
				
				document.getElementById("BP_strandNb"+dBBoqIndex).addEventListener ("input" ,function() {
					var thisID = $(this).attr("id");
					//var indexFor = parseInt(thisID.substr(thisID.length-1));
					var indexFor = thisID.replace('BP_strandNb','');
					colorId="BP_strandcolor"+indexFor;
					numberId="BP_strandNb"+indexFor;
					number=document.getElementById(numberId).value;
					strandTubeSetColor(number,colorId);
				
				});
										 
					autoCompleteForMapping(dBBoqIndex,"DbMappingTable",DistBoardMappingPts[i][1],"FP_Location","FP_LocationID","FP_LocationM","FP_LocationType","FP_equipment","FP_equipmentID","FP_equipmentName","FP_equipmentType","FP_strandID","FP_strandName","FP_tubeID","FP_tubeName","FP_fiberID","FP_fiberName","FP_strandNb","FP_strandcolor","FP_tubeNb","FP_tubecolor","FP_junctionID","FP_junctionName");
					autoCompleteForMapping(dBBoqIndex,"DbMappingTable",DistBoardMappingPts[i][1],"BP_Location","BP_LocationID","BP_LocationM","BP_LocationType","BP_equipment","BP_equipmentID","BP_equipmentName","BP_equipmentType","BP_strandID","BP_strandName","BP_tubeID","BP_tubeName","BP_fiberID","BP_fiberName","BP_strandNb","BP_strandcolor","BP_tubeNb","BP_tubecolor","BP_junctionID","BP_junctionName");
					tubeStrandSetColor("FP_strandcolor"+dBBoqIndex,"FP_strandNb"+dBBoqIndex);
					tubeStrandSetColor("BP_strandcolor"+dBBoqIndex,"BP_strandNb"+dBBoqIndex);
					tubeStrandSetColor("FP_tubecolor"+dBBoqIndex,"FP_tubeNb"+dBBoqIndex);
					tubeStrandSetColor("BP_tubecolor"+dBBoqIndex,"BP_tubeNb"+dBBoqIndex);
					dBBoqIndex++;
						
						}
					}						
}

function tubeStrandSetColor(colorID,numberID) {

	if (document.getElementById(colorID).value=="blue"){
	   	 document.getElementById(colorID).style.backgroundColor = "blue"; 
	   	 document.getElementById(colorID).style.color = "white";
	     document.getElementById(numberID).value= "1";
	    }
	else if (document.getElementById(colorID).value=="orange"){
	     document.getElementById(colorID).style.backgroundColor = "orange"; 
	     document.getElementById(colorID).style.color = "white";
	     document.getElementById(numberID).value= "2";
	    }
	else if (document.getElementById(colorID).value=="green"){
	   	 document.getElementById(colorID).style.backgroundColor = "green";
	   	 document.getElementById(colorID).style.color = "white";
	   	 document.getElementById(numberID).value= "3";
	   	 }
	 else if(document.getElementById(colorID).value=="brown") {
	   	 document.getElementById(colorID).style.backgroundColor = "brown";
	   	 document.getElementById(colorID).style.color = "white";
	   	 document.getElementById(numberID).value= "4";
	    }
	 else if(document.getElementById(colorID).value=="gray") {
	   	 document.getElementById(colorID).style.backgroundColor = "gray"; 
	   	 document.getElementById(colorID).style.color = "white";
	   	 document.getElementById(numberID).value= "5";
	    }
	 else if(document.getElementById(colorID).value=="white") {
	   	 document.getElementById(colorID).style.backgroundColor = "white"; 
	   	 document.getElementById(colorID).style.color = "black";
	   	 document.getElementById(numberID).value= "6";
	    }	
	 else if(document.getElementById(colorID).value=="red"){
	     document.getElementById(colorID).style.backgroundColor = "red";
		 document.getElementById(colorID).style.color = "white";
		 document.getElementById(numberID).value= "7";
	 }
	else if(document.getElementById(colorID).value=="black") {
	  	 document.getElementById(colorID).style.backgroundColor = "black";
	  	 document.getElementById(colorID).style.color = "white";
	  	 document.getElementById(numberID).value= "8";
	   } 
	else if(document.getElementById(colorID).value=="yellow") {
	  	 document.getElementById(colorID).style.backgroundColor = "yellow";
	  	 document.getElementById(colorID).style.color = "black";
	  	 document.getElementById(numberID).value= "9";
	   } 	
	else if(document.getElementById(colorID).value=="violet") {
		 document.getElementById(colorID).style.backgroundColor = "violet"; 
		 document.getElementById(colorID).style.color = "white";
		 document.getElementById(numberID).value= "10";
	  }         
	else if(document.getElementById(colorID).value=="pink") {
		 document.getElementById(colorID).style.backgroundColor = "pink";
		 document.getElementById(colorID).style.color = "black";
		 document.getElementById(numberID).value= "11";
	  }
	else if(document.getElementById(colorID).value=="aqua") {
		 document.getElementById(colorID).style.backgroundColor = "aqua";
		 document.getElementById(colorID).style.color = "black";
		 document.getElementById(numberID).value= "12";
	 }
	
/*	if(document.getElementById(ID).value=="red"){
		document.getElementById(ID).style.backgroundColor = "red";
    	document.getElementById(ID).style.color = "white";
	 }
    else if (document.getElementById(ID).value=="orange"){
    	document.getElementById(ID).style.backgroundColor = "orange";
    	document.getElementById(ID).style.color = "white";
    }
    else if (document.getElementById(ID).value=="green"){
    	document.getElementById(ID).style.backgroundColor = "green";
    	document.getElementById(ID).style.color = "white";
   	 } 
    else if (document.getElementById(ID).value=="blue"){
    	document.getElementById(ID).style.backgroundColor = "blue";
    	document.getElementById(ID).style.color = "white";
    }
    else if(document.getElementById(ID).value=="gray") {
    	document.getElementById(ID).style.backgroundColor = "gray";
    	document.getElementById(ID).style.color = "white";
    }
    else if(document.getElementById(ID).value=="purple") {
    	document.getElementById(ID).style.backgroundColor = "purple";
    	document.getElementById(ID).style.color = "white";
    }
    else if(document.getElementById(ID).value=="yellow") {
    	document.getElementById(ID).style.backgroundColor = "yellow";
    	document.getElementById(ID).style.color = "black";
    }     
    else if(document.getElementById(ID).value=="black") {
    	document.getElementById(ID).style.backgroundColor = "black";
    	document.getElementById(ID).style.color = "white";
    }     
    else if(document.getElementById(ID).value=="pink") {
    	document.getElementById(ID).style.backgroundColor = "pink";
    	document.getElementById(ID).style.color = "white";
    }
    else if(document.getElementById(ID).value=="brown") {
    	document.getElementById(ID).style.backgroundColor = "brown";
    	document.getElementById(ID).style.color = "white";
    }   */
}
function strandTubeSetColor(strandTubeNumber,ID) {
	
	 if (strandTubeNumber=="1"){
		 document.getElementById(ID).value = "blue";
	   	 document.getElementById(ID).style.backgroundColor = "blue"; 
	   	 document.getElementById(ID).style.color = "white";
	 }
	 else if (strandTubeNumber=="2"){
		 document.getElementById(ID).value ="orange";
	     document.getElementById(ID).style.backgroundColor = "orange"; 
	     document.getElementById(ID).style.color = "white";	  
	 }
	 else if (strandTubeNumber=="3"){
		 document.getElementById(ID).value ="green";
	   	 document.getElementById(ID).style.backgroundColor = "green";
	   	 document.getElementById(ID).style.color = "white";
	 }
	 else if (strandTubeNumber=="4"){
		 document.getElementById(ID).value ="brown";
	   	 document.getElementById(ID).style.backgroundColor = "brown";
	   	 document.getElementById(ID).style.color = "white";
	 }
	 else if (strandTubeNumber=="5"){
		 document.getElementById(ID).value ="gray";
	   	 document.getElementById(ID).style.backgroundColor = "gray"; 
	   	 document.getElementById(ID).style.color = "white";  
	 }
	 else if (strandTubeNumber=="6"){
		 document.getElementById(ID).value ="white";
	   	 document.getElementById(ID).style.backgroundColor = "white"; 
	   	 document.getElementById(ID).style.color = "black";
	 }
	 else if (strandTubeNumber=="7"){
		 document.getElementById(ID).value ="red";
         document.getElementById(ID).style.backgroundColor = "red";
    	 document.getElementById(ID).style.color = "white";
	 }
	 else if (strandTubeNumber=="8"){
		 document.getElementById(ID).value ="black";
      	 document.getElementById(ID).style.backgroundColor = "black";
      	 document.getElementById(ID).style.color = "white";
	 }
	 else if (strandTubeNumber=="9"){
		 document.getElementById(ID).value ="yellow";
      	 document.getElementById(ID).style.backgroundColor = "yellow";
      	 document.getElementById(ID).style.color = "black";
	 }
	 else if (strandTubeNumber=="10"){
		 document.getElementById(ID).value ="violet";
   		 document.getElementById(ID).style.backgroundColor = "violet"; 
   		 document.getElementById(ID).style.color = "white";
	 }
	 else if (strandTubeNumber=="11"){
		 document.getElementById(ID).value ="pink";
   		 document.getElementById(ID).style.backgroundColor = "pink";
   		 document.getElementById(ID).style.color = "black"; 	
	 }
	 else if (strandTubeNumber=="12"){
		 document.getElementById(ID).value ="aqua";
  		 document.getElementById(ID).style.backgroundColor = "aqua";
  		 document.getElementById(ID).style.color = "black";
	 }
	 else if (strandTubeNumber >12 || strandTubeNumber==""){
		 document.getElementById(ID).value ="";
  		 document.getElementById(ID).style.backgroundColor = "";
  		 document.getElementById(ID).style.color = "";	
	 }			 

}

function debounce(fn, delay) {
    var timer;
    return function() {
      var args = [].slice.call(arguments);
      var context = this;
      if (timer) {
        window.clearTimeout(timer);
      }
      timer = window.setTimeout(function() {
        fn.apply(context, args);
      }, delay);
    };
  };
  
//hide or show related tab in fiber cable popup 
function relatedCableTabEvents(netLevel){
	
	if(netLevel =="access"){
		$("#relatedcable-tab").show();
		$("#lastmilejunction-tab").show();
	}
	else{
		$("#relatedcable-tab").hide();
		$("#lastmilejunction-tab").hide();
	}
	
	$("#fibernetlevel").change(function(){
		if (document.getElementById("fibernetlevel").value=="access"){
				$("#relatedcable-tab").show();
				$("#lastmilejunction-tab").show();
		 }
		else{
			$("#relatedcable-tab").hide();
			$("#lastmilejunction-tab").hide();
		}
	});
	
	
	$("#relatedCableStrandColor").change(function(){
		tubeStrandSetColor("relatedCableStrandColor","relatedCableStrandNb");
	});
	
	document.getElementById("relatedCableStrandNb").addEventListener ("input" ,function() {
		number=document.getElementById("relatedCableStrandNb").value;
		strandTubeSetColor(number,"relatedCableStrandColor");
	});
	
	$("#relatedCableTubeColor").change(function(){
		tubeStrandSetColor("relatedCableTubeColor","relatedCableTubeNb");
	});
	
	document.getElementById("relatedCableTubeNb").addEventListener ("input" ,function() {
		number=document.getElementById("relatedCableTubeNb").value;
		strandTubeSetColor(number,"relatedCableTubeColor");
	});
	
	
	$('.relatedCableAutoComplete').change(function() {
		$('.relatedCableAutoComplete').not(this).prop('checked', false);
		$('input:checkbox[class=relatedCableAutoComplete]:checked').each(function () {
			var checkedCheckbox =  $(this).attr("id");
			checkedCheckboxAutocomplete = checkedCheckbox.split("_");			
			checkedCheckboxAutocomplete=checkedCheckboxAutocomplete[0]; 
			if(checkedCheckboxAutocomplete=="client"){
				$(".relatedLocationName").hide();
				$(".relatedLocationID").hide();
				$("#relatedLocationCity").hide();
				$("#relatedClientID").show();
				$("#relatedClientName").show();
				$("#relatedClientPhoneNb").show();
			}
			
			else if(checkedCheckboxAutocomplete=="site"){
			    $(".relatedLocationName").hide();
				$(".relatedLocationID").hide();
				$("#relatedClientPhoneNb").hide();
				$("#relatedSiteName").show();
				$("#relatedSiteID").show();
				$("#relatedLocationCity").show();
				
			}
			else if(checkedCheckboxAutocomplete=="manhole"){
			    $(".relatedLocationName").hide();
				$(".relatedLocationID").hide();
				$("#relatedClientPhoneNb").hide();
				$("#relatedManholeName").show();
				$("#relatedManholeID").show();
				$("#relatedLocationCity").show();
				
			}
			else if(checkedCheckboxAutocomplete=="handhole"){
			    $(".relatedLocationName").hide();
				$(".relatedLocationID").hide();
				$("#relatedClientPhoneNb").hide();
				$("#relatedHandholeName").show();
				$("#relatedHandholeID").show();
				$("#relatedLocationCity").show();
				
			}
			
			else if(checkedCheckboxAutocomplete=="db"){
			    $(".relatedLocationName").hide();
				$(".relatedLocationID").hide();
				$("#relatedClientPhoneNb").hide();
				$("#relatedDistBoardName").show();
				$("#relatedDistBoardID").show();
				$("#relatedLocationCity").show();
				
			}
			
		});
	});
	
	
	uncheckAutoCompleteCheckboxes("relatedCableAutoComplete");

}

//Show / Hide tube and strand auxiliaries in fiber cable popup
function showHideTubeStrandAuxPoints(auxiliaryArray,wareID,srcDstID,name,longitude,latitude,pathID,checkSeqWindowID,action,type) {
	 
	  if(action=="Show") {	  
		if(auxiliaryArray.length>0){
			for(var A=0;A<auxiliaryArray.length;A++) {			
				var seqNumber = String(auxiliaryArray[A].seqSorting);	
				var auxPt = auxiliaryArray[A].aux_Name;
				var latitude = auxiliaryArray[A].aux_Latitude;
				var longitude = auxiliaryArray[A].aux_Longitude;
				
				
			if(auxPt.split("_")[0]=="WARE") {
					wareID = auxPt.split(":")[0];
					srcName = auxPt.split(":")[1];
					srcID = auxPt.split(":")[2];
					
					createSiteCltMarker(wareID,auxPt,latitude,longitude,siteCltSrcMarkers);		
					if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
						  if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {
							  siteCltSrcMarkers[wareID].setLabel({text: seqNumber+" / "+wareID+":"+srcID, className:"marker-position-site",color:"red"});
						  }
						else {
							siteCltSrcMarkers[wareID].setLabel({text: seqNumber , className:"marker-position-sequence",color:"red"}); 
						}
					}
					else {
						if(allcheckedLabels.length >0 && allcheckedLabels.includes("sitesMapCheck_Labels")==true) {
							siteCltSrcMarkers[wareID].setLabel({text: wareID+":"+srcID , className:"marker-position-site",color:"red"});
						}
						else {
							if(siteCltSrcMarkers[wareID].getLabel()!="undefined") {
								siteCltSrcMarkers[wareID].setLabel(null);
							}
						}
					}
					 
				}
			else {
			 if(auxPt==null || auxPt =="null"){
				var AuxId = "null".concat("_"+seqNumber+"_"+pathID);
				createSiteCltMarker(AuxId,AuxId,latitude,longitude,siteCltSrcMarkers);
				
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					siteCltSrcMarkers[AuxId].setLabel({text: seqNumber , className:"marker-position-sequence",color:"green"}); 
				}
				else {
					if(siteCltSrcMarkers[AuxId].getLabel()!="undefined") {
						siteCltSrcMarkers[AuxId].setLabel(null);
					}
				}						
			}
		else if(auxPt.includes("Auxiliary_Point")){
			createSiteCltMarker(auxPt.split(":")[0],auxPt.split(":")[0],latitude,longitude,siteCltSrcMarkers);
			
			if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
				siteCltSrcMarkers[auxPt.split(":")[0]].setLabel({text: seqNumber , className:"marker-position-sequence",color:"green"}); 
			}
			else {
				if(siteCltSrcMarkers[auxPt.split(":")[0]].getLabel()!="undefined") {
					siteCltSrcMarkers[auxPt.split(":")[0]].setLabel(null);
				}
			}
		}
		else if(auxPt.split('_')[0]=="MH"){
			if(markersManhole[auxPt.split(':')[0]]){
				markerClusterManhole.removeMarker(markersManhole[auxPt.split(':')[0]]);	
				if(markersManhole[auxPt.split(':')[0]].getMap() ==null) {
					markersManhole[auxPt.split(':')[0]].setMap(map);
					markerClusterManhole.addMarker(markersManhole[auxPt.split(':')[0]]);	
					$("#"+auxPt.split(':')[0]).children(':checkbox').prop( "checked", true );
					$("#manholeCheckAllBoq").prop( "checked", true );													
							
				}
				
				//Show seq is checked 
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {
						markersManhole[auxPt.split(':')[0]].setLabel({text: seqNumber+" / "+ auxPt.split(':')[1], className:"marker-position-manhole",color:"red"});
					}
					else {
						markersManhole[auxPt.split(':')[0]].setLabel({text: seqNumber , className:"marker-position-sequence",color:"red"}); 
					}
				}
				//Show Seq is unchecked
				else {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("manholesMapCheck_Labels")==true) {
						markersManhole[auxPt.split(':')[0]].setLabel({text: auxPt.split(':')[1], className:"marker-position-manhole",color:"red"});
					}
					else {
						if(markersManhole[auxPt.split(':')[0]].getLabel()!="undefined") {
							markersManhole[auxPt.split(':')[0]].setLabel(null);
						}
					}
				}	
			}
		}   		
		else if(auxPt.split('_')[0]=="HH"){
			if(markersHandhole[auxPt.split(':')[0]]){
				markerClusterHandhole.removeMarker(markersHandhole[auxPt.split(':')[0]]);	
				if(markersHandhole[auxPt.split(':')[0]].getMap() ==null) {
					markersHandhole[auxPt.split(':')[0]].setMap(map);
					markerClusterHandhole.addMarker(markersHandhole[auxPt.split(':')[0]]);
					$("#"+auxPt.split(':')[0]).children(':checkbox').prop( "checked", true );
					$("#handholeCheckAllBoq").prop( "checked", true );
				
				}
				//Show Sequence is checked
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {
						markersHandhole[auxPt.split(':')[0]].setLabel({text: seqNumber+" / "+auxPt.split(':')[1], className:"marker-position-handhole",color:"#E5C523"});
					}
					else {
						markersHandhole[auxPt.split(':')[0]].setLabel({text: seqNumber , className:"marker-position-sequence",color:"#E5C523"}); 
					}
				}
				//Show Sequence is unchecked
				else {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("handholesMapCheck_Labels")==true) {
						markersHandhole[auxPt.split(':')[0]].setLabel({text: auxPt.split(':')[1], className:"marker-position-handhole",color:"#E5C523"});
					}
					else {
						if(markersHandhole[auxPt.split(':')[0]].getLabel()!="undefined") {
							markersHandhole[auxPt.split(':')[0]].setLabel(null);
						}
					}
				}
				
			}
		}			 
		else if(auxPt.split('_')[0]=="DB"){
			if(markersDistBoard[auxPt.split(':')[0]]){
				markerClusterDistBoard.removeMarker(markersDistBoard[auxPt.split(':')[0]]);
				if(markersDistBoard[auxPt.split(':')[0]].getMap() ==null) {
					markersDistBoard[auxPt.split(':')[0]].setMap(map);	
					markerClusterDistBoard.addMarker(markersDistBoard[auxPt.split(':')[0]]);
					$("#"+auxPt.split(':')[0]).children(':checkbox').prop( "checked", true );
					$("#distBoardCheckAllBoq").prop( "checked", true );
				}
				if(window[''+checkSeqWindowID+'_'+pathID] == "checked") {
					if(allcheckedLabels.length >0 && allcheckedLabels.includes("dBMapCheck_Labels")==true) {
						markersDistBoard[auxPt.split(':')[0]].setLabel({text: seqNumber+" / "+ auxPt.split(':')[1], className:"marker-position-dB",color:"#5665F9"});
					}
					else {
						markersDistBoard[auxPt.split(':')[0]].setLabel({text: seqNumber , className:"marker-position-sequence",color:"#5665F9"}); 
					}
				}
				else {
					if(markersDistBoard[auxPt.split(':')[0]].getLabel()!="undefined") {
						markersDistBoard[auxPt.split(':')[0]].setLabel(null);
					}
					
				}
			}
		}
					
		}		  
		}
			}

	  }	
	 
	  //Hide points
	  else {

		  if(auxiliaryArray.length>0){
				for(var A=0;A<auxiliaryArray.length;A++) {
				
					var seqNumber = String(auxiliaryArray[A].seqSorting);	
					var auxPt = auxiliaryArray[A].aux_Name;
					
				if(auxPt.split("_")[0]=="WARE") {
					if(siteCltSrcMarkers[auxPt.split(":")[0]]) {
						siteCltSrcMarkers[auxPt.split(":")[0]].setMap(null);
					}					 
				}
				else {
				 if(auxPt==null || auxPt =="null"){
					var AuxId = "null".concat("_"+seqNumber+"_"+pathID);
					if(siteCltSrcMarkers[AuxId]) {
						siteCltSrcMarkers[AuxId].setMap(null);
					}				
				}
			else if(auxPt.includes("Auxiliary_Point")){
				if(siteCltSrcMarkers[auxPt.split(":")[0]]) {
					siteCltSrcMarkers[auxPt.split(":")[0]].setMap(null);	
				}	
			}
			else if(auxPt.split('_')[0]=="MH"){
				if(markersManhole[auxPt.split(':')[0]]) {
					markersManhole[auxPt.split(':')[0]].setMap(null);				
					markerClusterManhole.removeMarker(markersManhole[auxPt.split(':')[0]]);	
					$("#"+auxPt.split(':')[0]).children(':checkbox').prop( "checked", false );	
				}			
			}   		
			else if(auxPt.split('_')[0]=="HH"){
				if(markersHandhole[auxPt.split(':')[0]]) {
					markersHandhole[auxPt.split(':')[0]].setMap(null);
					markerClusterHandhole.removeMarker(markersHandhole[auxPt.split(':')[0]]);
					$("#"+auxPt.split(':')[0]).children(':checkbox').prop( "checked", false );	
				}
			
			}			 
			else if(auxPt.split('_')[0]=="DB"){
				if(markersDistBoard[auxPt.split(':')[0]]) {
					markersDistBoard[auxPt.split(':')[0]].setMap(null);					
					markerClusterDistBoard.removeMarker(markersDistBoard[auxPt.split(':')[0]]);
					$("#"+auxPt.split(':')[0]).children(':checkbox').prop( "checked", false );	
					
			}	
			}
						
			}		  
			}
				
		  }
	  }

			if( $("#Manhole_f_CurrentPhysicalLayer").find(".Manhole:checked" ).length ==0){
				$("#manholeCheckAllBoq").prop("checked",false);
			}
			else{
				$("#manholeCheckAllBoq").prop("checked",true);
			}
			
			if( $("#Handhole_f_CurrentPhysicalLayer").find(".Handhole:checked" ).length ==0){
				$("#handholeCheckAllBoq").prop("checked",false);
			}
			else{
				$("#handholeCheckAllBoq").prop("checked",true);
			}
			if( $("#DistributionBoard_f_CurrentPhysicalLayer").find(".DistBoard:checked" ).length ==0){
				$("#distBoardCheckAllBoq").prop("checked",false);
			}
			else{
				$("#distBoardCheckAllBoq").prop("checked",true);
			}	
			
		}


