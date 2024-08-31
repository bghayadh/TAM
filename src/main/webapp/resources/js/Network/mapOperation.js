function MapOperationmarking(){
var search= document.getElementById("Searchh").value.toString();
var place= document.getElementById("placeSearch").value.toString();
var markerName= document.getElementById("markerN").value.toString();
var placeId=Math.floor(Math.random() * 1000)+place;
var genericId;
    if (place) {
       
        genericId = Math.floor(Math.random() * 1000) + place;
    } else {
         genericId = Math.floor(Math.random() * 1000) + markerName;
    }
var id=search.split(":")[0];
var type =id.split("_")[0];
var name=search.split(":")[1];
var Lng=document.getElementById("Lng").value;
var Lat=document.getElementById("Lat").value;
console.log(Lng);
console.log("type"+type);

       
    
if(document.getElementById("generic_operationAutoComplete").checked){
	
genericMarkers=[genericId];
createPlaceMarker(genericId,place,markerName,Lat,Lng,genericMarkers,"generic");
console.log(createPlaceMarker);
panTo(Lat, Lng);
map.setZoom(6);
				if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(place ? place : markerName);
			infowindow.open(map,genericMarkers[""+genericId]);
		
}

	
	

	
 if(document.getElementById("place_operationAutoComplete").checked){
	
	placeMarkers=[placeId];
createPlaceMarker(placeId,place,"",Lat,Lng,placeMarkers,"place");
console.log(createPlaceMarker);
panTo(Lat, Lng);
map.setZoom(6);
				if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(place);
			infowindow.open(map,placeMarkers[""+placeId]);
		
}


else if(type=="MH"){
$("#"+id).children('input:checkbox').prop('checked', true);
$("#manholeCheckAllBoq").prop('checked', true);
markerClusterManhole.addMarker(markersManhole[""+id]);
ManholeCheckFilter(id);	
panTo(Lat, Lng);
			map.setZoom(11);
			if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(id);
			infowindow.open(map,markersManhole[""+id]);
						
			
    	
		


}
else if(type=="HH"){
$("#"+id).children('input:checkbox').prop('checked', true);
$("#handholeCheckAllBoq").prop('checked', true);
markerClusterHandhole.addMarker(markersHandhole[""+id]);
HandholeCheckFilter(id);	
panTo(Lat, Lng);
map.setZoom(11);
			if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(id);
			infowindow.open(map,markersHandhole[""+id]);
						
}
else if(type=="DB"){
	$("#"+id).children('input:checkbox').prop('checked', true);
	$("#distBoardCheckAllBoq").prop('checked', true);
	
	if(window[""+id][8]=="backbone") {
		markerClusterBackboneDistBoard.addMarker(markersDistBoard[""+id]);
		DistributionBoardCheckFilter(id,"",markerClusterBackboneDistBoard);	
	}
	else if(window[""+id][8]=="metro") {
		markerClusterMetroDistBoard.addMarker(markersDistBoard[""+id]);
		DistributionBoardCheckFilter(id,"",markerClusterMetroDistBoard);	

	}
	else if(window[""+id][8]=="access") {
		markerClusterAccessDistBoard.addMarker(markersDistBoard[""+id]);
		DistributionBoardCheckFilter(id,"",markerClusterAccessDistBoard);	
	}
	panTo(Lat, Lng);
	map.setZoom(11);
			if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(id);
			infowindow.open(map,markersDistBoard[""+id]);
}
else if(type=="WARE"){
createSiteCltMarker(id,search,Lat,Lng,siteCltSrcMarkers);
panTo(Lat, Lng);
map.setZoom(6);
			if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(id);
			infowindow.open(map,siteCltSrcMarkers[""+id]);
			
}
else if(type=="CUST"){
createSiteCltMarker(id,search,Lat,Lng,siteCltSrcMarkers);
panTo(Lat, Lng);
map.setZoom(6);
			if(typeof infowindow!== 'undefined'){
				infowindow.close();
			}
			else{
				infowindow = new google.maps.InfoWindow();
			}

			infowindow.setContent(id);
			infowindow.open(map,siteCltSrcMarkers[""+id]);
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
	
	else if(checkedCheckboxAutocomplete=="customer") {

		url='GetAllNetworkCustomer';
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
			this.value = (ui.item ? ui.item[0]+':'+ui.item[1] : '');
			$("#"+srcLong).val(ui.item[4]);
			$("#"+srcLat).val(ui.item[5]);
			}	
			return false;
		},
			}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
				
					if(item[0].split("_")[0]=="WARE" || item[0].split("_")[0]=="CUST" ) {
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

function createPlaceMarker(Id, Name, markerName, Lat, Long, placeMarkers, type) {
    // Define the icon based on the type
    let icon;
    if (type.startsWith("place")) {
        icon = iconPlace;
    } else if (type.startsWith("generic")) {
        icon = iconGeneric;
    } else {
        console.error("Unknown type:", type);
        return;
    }

    // Create marker position and data
    const pos = new google.maps.LatLng(Lat, Long);
     const data = `<div>${Name ? Name : markerName}</div>`;

    // Check if the marker already exists
    if (!placeMarkers[Id]) {
        // Create a new marker
        const placeMarker = new google.maps.Marker({
            position: pos,
            icon: icon,
            map: map
        });

        // Attach metadata to the marker
        placeMarker.metadata = { id: Id };
        placeMarkers[Id] = placeMarker;

        // Create and attach the info window
        const infowindow = new google.maps.InfoWindow({
            content: data
        });

        google.maps.event.addListener(placeMarker, 'click', function () {
            infowindow.open(map, placeMarker);
        });
    } else {
        // Update existing marker
        const existingMarker = placeMarkers[Id];
        if (existingMarker.map !== map) {
            existingMarker.setMap(map);
        }
        existingMarker.setPosition(pos);
        existingMarker.setIcon(icon);

        // Update existing info window content
        const infowindow = new google.maps.InfoWindow({
            content: data
        });

        google.maps.event.clearListeners(existingMarker, 'click'); // Remove existing click listeners
        google.maps.event.addListener(existingMarker, 'click', function () {
            infowindow.open(map, existingMarker);
        });
    }

    // Make sure the marker is added to the map
    placeMarkers[Id].setMap(map);
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
		 document.getElementById('markerN').value = "";
}

