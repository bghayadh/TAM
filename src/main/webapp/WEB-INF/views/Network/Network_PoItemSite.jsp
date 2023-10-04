<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};
//console.log("lst...", lst);
var listPO=${listPO};
//console.log("listPO...", listPO);
var arrayParam=${arrayParam};
//console.log("arrayParam...", arrayParam);


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

if(arrayParam[0] == 1){
	 $('#EnterpriseBtn').toggleClass('activee'); 
	 console.log("EnterpriseBtn");
}if(arrayParam[1] == 1){
	 $('#transmBtn').toggleClass('activee');  
	 console.log("transmBtn");
}if(arrayParam[2] == 1){
	 $('#accessDBtn').toggleClass('activee');
	 console.log("accessDBtn");
}if(arrayParam[3] == 1){
	 $('#CoreBtn').toggleClass('activee');
	 console.log("CoreBtn");
}

function initMap() {

	$('#itemBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#poBtn').toggleClass('activee');

    $("#default").prop('checked', true);
    $("#landscape").prop('checked', true);
    $("#water").prop('checked', true);
    $("#transit").prop('checked', true);
    $("#poi").prop('checked', true);
    $("#road").prop('checked', true);
    $("#blank").prop('checked', false);
    $("#mapgeography").prop('checked', false);
    $("#maplabels").prop('checked', false);
    $("#countrynames").prop('checked', false);
    $("#countryprovince").prop('checked', false);
    
	button = document.getElementById('customMap');
	data = button.getAttribute('data-map');

	document.getElementById("network_tree").innerHTML ="";
	$("#network_tree").resizable({
		handles: "s", 	
	});

	
	//var directionsDisplay=new google.maps.DirectionsRenderer();
	//var directionsService=new google.maps.DirectionsService();
	
	//New Map//
						 		
 map = new google.maps.Map(document.getElementById("mapContainer"), {
		mapTypeControl: false,
		zoom:3,
		center: { lat: -33.8688, lng: 151.2195 },
		mapTypeControl: true,
		
			mapTypeControlOptions: {
				style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
				position: google.maps.ControlPosition.TOP_CENTER,
			},
			zoomControl: true,
			zoomControlOptions: {
				position: google.maps.ControlPosition.LEFT_CENTER,
			},
			scaleControl: true,
			streetViewControl: true,
			streetViewControlOptions: {
				position: google.maps.ControlPosition.LEFT_TOP,
			},

			style: 'mapbox://styles/mapbox/streets-v11',
			fullscreenControl: true,
			
});

map.setOptions({ minZoom: 3, maxZoom: 28});
	
//new AutocompleteDirectionsHandler(map);
//directionsDisplay.setMap(map);

	//-----> Create the DIV to hold the control and call the CenterControl()
//-----> constructor passing in this DIV.

const centerControlDiv = document.createElement("div");
CenterControl(centerControlDiv, map);
map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);

		   /*
		    const locationButton = document.createElement("button");
		    locationButton.textContent = "Pan to Current Location";

		    locationButton.style.backgroundColor = "#fff";
		    locationButton.style.border = "2px solid #fff";
		    locationButton.style.borderRadius = "3px";
		    locationButton.style.boxShadow = "0 2px 6px rgba(0,0,0,.3)";
		    locationButton.style.cursor = "pointer";
		    
		    locationButton.style.textAlign = "center";
		    locationButton.style.lineHeight = "35px";
		    locationButton.style.paddingLeft = "5px";
		    locationButton.style.paddingRight = "5px";
		    locationButton.style.marginLeft = "10px";
		    locationButton.style.marginTop = "10px";
		    locationButton.style.fontSize="16px";
		    locationButton.classList.add("custom-map-control-button");

		    
		    map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
		    locationButton.addEventListener("click", () => {
		    	 var infowindow = new google.maps.InfoWindow();
		      /// Try HTML5 geolocation.
		      if (navigator.geolocation) {
		        navigator.geolocation.getCurrentPosition(
		          (position) => {
		            const pos = {
		              lat: position.coords.latitude,
		              lng: position.coords.longitude,
		            };
		            infowindow.setPosition(pos);
		            infowindow.setContent("<h6><u>Your Location</u><br><br>"+pos.lat+" , "+pos.lng+"</h6>");
		            infowindow.open(map);
		            map.setCenter(pos);

		            var icon = {
		            	    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		            	    scaledSize: new google.maps.Size(50, 50), // scaled size

		            	};
	            	
		            const myLocation = new google.maps.Marker({
		  	          	position: pos,
		  	         	map:map,
		  	       		animation: google.maps.Animation.DROP,
		  	          	icon: icon,
		  	          
		  	        });
		            
		          },
		          () => {
		            handleLocationError(true, infowindow, map.getCenter());
		          }
		        );
		      } else {
		        // Browser doesn't support Geolocation
		        handleLocationError(false, infowindow, map.getCenter());
		      }
		    });
*/
		    /*
		    const input = document.getElementById("pac-input");
	        const searchBox = new google.maps.places.SearchBox(input);
	        // Bias the SearchBox results towards current map's viewport.
	        map.addListener("bounds_changed", () => {
	          searchBox.setBounds(map.getBounds());
	        });
	        let markerss = [];
	        // Listen for the event fired when the user selects a prediction and retrieve
	        // more details for that place.
	        searchBox.addListener("places_changed", () => {
	          const places = searchBox.getPlaces();

	          if (places.length == 0) {
		        alert("Not found!!");
	            return;
	          }
	          // Clear out the old markers.
	          markerss.forEach((marker) => {
	            marker.setMap(null);
	          });
	          markerss = [];
	          // For each place, get the icon, name and location.
	          const bounds = new google.maps.LatLngBounds();
	          places.forEach((place) => {
	            if (!place.geometry) {
	              console.log("Returned place contains no geometry");
	              return;
	            }
	            const icon = {
	              url: place.icon,
	              size: new google.maps.Size(71, 71),
	              origin: new google.maps.Point(0, 0),
	              anchor: new google.maps.Point(17, 34),
	              scaledSize: new google.maps.Size(50, 50),
	            };
	            // Create a marker for each place.
	            markerss.push(
	              new google.maps.Marker({
	                map,
	                icon,
	                title: place.name,
	                position: place.geometry.location,
	              })
	            );

	            if (place.geometry.viewport) {
	              // Only geocodes have viewport.
	              bounds.union(place.geometry.viewport);
	            } else {
	              bounds.extend(place.geometry.location);
	            }
	          });
	          map.fitBounds(bounds);
	        });




	var markerId;
	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
	var icon2 = {
			url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
			scaledSize: new google.maps.Size(50, 50), // scaled size

		};
	*/

	if(!(lst==null || lst=="")){
		/*
		map = new google.maps.Map(document.getElementById("mapContainer"), {
				mapTypeControl: false,
				center: { lat: -33.8688, lng: 151.2195 },
						mapTypeControl: true,						 		
							mapTypeControlOptions: {
								style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
								position: google.maps.ControlPosition.TOP_CENTER,
							},
							zoomControl: true,
							zoomControlOptions: {
								position: google.maps.ControlPosition.LEFT_CENTER,
							},
							scaleControl: true,
							streetViewControl: true,
							streetViewControlOptions: {
								position: google.maps.ControlPosition.LEFT_TOP,
							},

							style: 'mapbox://styles/mapbox/streets-v11',
							fullscreenControl: true,
							
				});
*/
			map.setOptions({ minZoom: 3, maxZoom: 28});	
	CreateMap(lst,map);	
	CreateTree_PoItemSt(listPO,map);
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='POs' class='AllPOs' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> PO </span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

/* Start of PO Item Site Tree Method */ 
///////////////////////////////////////

function CreateTree_PoItemSt(listPO,map){
	//PO_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='POs' class='AllPOs' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> PO </span></li></ul>";
	$("#network_tree").append(str);
	var dFrag = document.createDocumentFragment();
	for (n = 0; n < listPO.length; n++) {	
		str="<li id='"+listPO[n]+"' class='PO' style='display:none;width:100px;'><span class='folder' onclick='PoItemStCore("+listPO[n]+")' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><i class='fa fa-file-invoice-dollar fa-2x'></i> "+listPO[n]+"</span>";
		str+= "<ul><li id='" +listPO[n]+"_f' class='Item parent_li' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Item </span></li></ul></li></ul>";	
		//PoStItemCore(n); 
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);
	}
	document.getElementById('initial_ul').appendChild(dFrag);
	tree_prop_selection();
	folder();
	
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedFolderPoIdContext=$(this).parent().attr('id');
		//console.log("selectedSiteIdContext---------"+selectedFolderSiteIdContext);
		menuName=folderPo;			
		openContext(selectedFolderPoIdContext,"",folderPo,event);
	});
	
	$(".PO > .TreeSpan").contextmenu(function(){				
		selectedPoSingleIdContext=$(this).parent().attr('id');
		menuName=folderPoSingle;			
		openContext(selectedPoSingleIdContext,"",folderPoSingle,event);
	});
}
folderPo= new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			  PO_Boq("");
			}	
		}
	]
});

folderPoSingle= new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
		  PO_Boq(selectedPoSingleIdContext);
			}	
		}
	]
});

function AllSitesCheckFilter(){
	//console.log("AllSitesCheckFilter");
	markerClusterSites.clearMarkers();
	CreateMap(lst,map);

	$('.AllPOs').bind("change",function() {	
			if ($(this).is(':checked')){
				 $('#network_tree input[type="checkbox"][class="SingleSite"]').prop('checked', false);
				 $('#network_tree input[type="checkbox"][class="AllSites"]').prop('checked', false);
				 for(var x=0; x< markersSites.length; x++){	
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}									
			}					
			else{
				//console.log("unChecked");
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}


function PoItemStCore(id)
{
	//console.log("PoItemStCore");
	var selectedPo=id.id;
	//console.log("selectedPO.....",selectedPo);
	/*
	var selectedId=n.id;
	PO_Boq(selectedId);
	tree_Prop("#"+selectedId+ "> span");
	tree_Prop("#"+selectedId+ "_f > span");
	var tempClusterArray=[];
	$("#"+selectedId+" > span").on('click',function (e) {                           
		var selectedPo=$(this).parent().attr('id');
		Create_TreeParent(selectedPo,"PO");			
	*/
	var POChildrenLength=$("#" +selectedPo+"_f").find(' > ul > li').length;
	if(POChildrenLength == 0){	
		
	 	  if(arrayParam[0]==1){
				var paramEnterprise = true;
			}else{
				var paramEnterprise = false;
			}

			if(arrayParam[1]==1){
				var paramTransmission = true;
			}else{
				var paramTransmission = false;
			}
				
			if(arrayParam[2]==1){
				var paramAccess = true;
			}else{
				var paramAccess = false;
			}

			if(arrayParam[3]==1){
				var paramCore = true;
			}else{
				var paramCore = false;
			}
	
		$.ajax({
		type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/findPOItems_sites',
			data: {
				"selectedPo":selectedPo,
				"POAlreadyCreated":"false",
				"paramEnterprise": paramEnterprise,
				"paramTransmission":paramTransmission,
		     	"paramAccess":paramAccess,
			    "paramCore":paramCore,
			},
			dataType: "json",
			success: function (data) {	        	
				if (data != null) {    
					//var POChildrenLength=$("#" +selectedPo+"_f").find(' > ul > li').length;	
					var listItem=data.listItem;	
					if(POChildrenLength<listItem.length+1){						
						for (n = POChildrenLength; n < listItem.length; n++) {							
							var str = "<ul><li class='Items' id='" + listItem[n][0] + "_" + listItem[n][2] + "' style='display:none; margin-left:-20px'><span class='folder' onclick=\"SitesRequest('" + listItem[n][0] + "_" + listItem[n][2] + "')\"><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-bahai fa-2x'></i>" + listItem[n][1] + " / " +listItem[n][0] + "</span></span>";
							str+= "<ul><li id='" +listItem[n][0]+"_"+listItem[n][2]+"_f' class='SiteFolder' style='display:none; margin-left:-40px'><input type='checkbox' id='" +listItem[n][0]+"_"+listItem[n][2] +"_Site' class='AllSites' style='margin-left: 15px' onclick=\"showMarkersAllSitesOneNt('"+listItem[n][0]+"_"+listItem[n][2]+"')\"></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites </span></li></ul></li></ul>";	
							$("#"+listItem[n][2]+"_f").append(str); 	
						}
							//tree_Prop("#"+listItem[n][0]+"_"+listItem[n][2]+"> span");
							//tree_Prop("#"+listItem[n][0]+"_"+listItem[n][2]+"_f > span");  
							tree_prop_selection("#" +selectedPo+"_f .Items .TreeSpan");
					        Tree_PropagationAppendedNodes(selectedPo+"_f .Items");
					}
				}
				data=null;
			},
			error: function(result) {
				alert("Error");
			}
		});	
	}
	//});              
}

function SitesRequest(id){
	//console.log("SitesRequest");
	//console.log("id....."+id);
	selectedItem = id.split("_")[0];
	//console.log("selectedItemmmmm ....."+selectedItem);
	selectedPo = id.split("_")[1]+"_"+id.split("_")[2]+"_"+id.split("_")[3];
	//console.log("selectedPooooo ....."+selectedPo);
	/*
	var markersSite="";  
	$("#"+listItem[n][0]+"_"+listItem[n][2]+" > span").on('click',function (e) {	 	
		var res=$(this ).parents().map(function() {
			return this.id;
		})
		.get()
		.join( "," );
		parents=res.split(",,");
		var selectedPO=parents[2];
		parents=res.split("_");
		var selectedItem=parents[0];
		Site_Boq(selectedItem);
		*/
	var ItemChildren=$("#"+selectedItem+ "_"+ selectedPo+ "_f") .find(' > ul > li').length;
	if(ItemChildren == 0){	
		
	 	  if(arrayParam[0]==1){
				var paramEnterprise = true;
			}else{
				var paramEnterprise = false;
			}

			if(arrayParam[1]==1){
				var paramTransmission = true;
			}else{
				var paramTransmission = false;
			}
				
			if(arrayParam[2]==1){
				var paramAccess = true;
			}else{
				var paramAccess = false;
			}

			if(arrayParam[3]==1){
				var paramCore = true;
			}else{
				var paramCore = false;
			}
	
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/findPOItems_sites',
			data: {     
				"selectedPo":selectedPo,
				"POAlreadyCreated":"True",
				"selectedItem":selectedItem,
				"paramEnterprise": paramEnterprise,
				"paramTransmission":paramTransmission,
		     	"paramAccess":paramAccess,
			    "paramCore":paramCore,
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					var listSites=data.listSites;
					//var ItemChildren=$("#"+selectedItem+ "_"+ selectedPO+ "_f") .find(' > ul > li').length;
					if(ItemChildren<listSites.length){
						var dFrag = document.createDocumentFragment();
						for (k = 0; k <listSites.length; k++) {
							var str="<ul><li class='SingleSite' id='" + listSites[k][2] +"_"+ listSites[k][3] + "' style='display:none; margin-left:-30px'> <input type='checkbox' id='" + listSites[k][2] + "_" + listSites[k][3] +"_SingleSite' class='SingleSite' style='margin-left: 25px' onclick=\"showMarkerSingleSite('"+listSites[k][2]+"_"+listSites[k][3]+"_SingleSite')\"></input><span class='TreeSpan' style='width:395px; margin-left:5px' onclick=\"PanTreeSites('"+listSites[k][2]+"_"+listSites[k][3]+"')\"><span class='tree-span'> <i class='fa fa-crosshairs fa-2x'></i> "+listSites[k][1]+"</span></span></li></ul>";
							$("#"+selectedItem+"_"+selectedPo+"_f").append(str); 
							//document.getElementById(selectedItem+"_"+selectedPo+"_f").appendChild(str);
						}
							tree_prop_selection("#" +selectedItem+ "_"+ selectedPo +"_f .SingleSite .TreeSpan");
					       
					        var idSite;
				            $(".SingleSite > .TreeSpan").contextmenu(function(){				
				        		selectedSingleSiteIdContext=$(this).parent().attr('id');
				        		var splitArray= selectedSingleSiteIdContext.split("_");
				        		idSite=splitArray[0] + "_" + splitArray[1] + "_" + splitArray[2];
				        		menuName=SingleSite;		
				        		openContext(selectedSingleSiteIdContext,"",SingleSite,event);
				        	});			            
				            SingleSite = new ContextMenu({
				        	  'theme': 'default',
				        	  'items': [
				        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {	
				        			  SitePO_Boq(idSite);
				        			}	
				        		}
				        	]
				        });
						}	                  
					}		
				data = null;
			},
			error: function(result) {
				alert("Error");
			}
		});	 
	}
	//}); 
}

/*
function St(k){
	var selectedId=k.id;
	tree_prop_general();
	tree_Prop("#"+selectedId+ "> span");
	tree_Prop("#"+selectedId+ "_f > span");      
	$("#"+ selectedId +" > span").on('click',function () { 	
		var res=$(this ).parents().map(function() {
			return this.id;
		})
		.get()
		.join( "," );
		parents=selectedId;
		var selectedtemp=parents[2].split("_");
		var selectedItem=selectedtemp[0];  
		Site_Boq(selectedItem); 
		
		if(selectedItem!=markersSite)
		{
			console.log("==============///////////clicked"+selectedItem);	
			var selMarker="";
			markerId=selectedItem;				
			selMarker=markers[markerId];
			var latSitee = selMarker.getPosition().lat();
			var lngSitee = selMarker.getPosition().lng();					
			position=selMarker.getPosition();
			panTo(latSitee,lngSitee);
			infowindow.setContent(selMarker.data);
			infowindow.open(map,selMarker);
														
			if(markersSite!="")
			{
				var otherMarkers=markers[markersSite];												
			}
			markersSite="";	
			markersSite=selectedItem;																				
			map.setZoom(15);
		}   
	}); 
}    
*/

function showMarkersAllSitesOneNt(id) {
  //console.log("showMarkersAllSitesOneNt");
//var id = id.id;
 // console.log("id ...."+ id);
  //var newId = id + "_Site";

  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id + "_Site").is(":checked");

  var liElements = document.getElementById(id).querySelectorAll('.SingleSite > input'); // Retrieve only <li> elements with <input> children
  //console.log("liElements ....... ",liElements);
  
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
    liElements[i].checked = isChecked;
    var idParts = liElements[i].id.split("_");
    var wareId = idParts[0] + "_" + idParts[1] + "_" + idParts[2];
   // console.log("wareId...... ",wareId);
    if (isChecked && !markersSites[wareId].getMap()) {
      markersToAdd.push(markersSites[wareId]);
    }
  }
  if (isChecked) {
	 // console.log("yes checked");
	  $('#network_tree input[type="checkbox"][class="AllPOs"]').prop('checked', false);
	//$('#network_tree input[type="checkbox"][class="SingleSite"]').prop('checked', true);
    markerClusterSites.addMarkers(markersToAdd); // Add all markers at once
    markerClusterSites.repaint();
  } else {
    markerClusterSites.clearMarkers();
    $('#network_tree input[type="checkbox"]').prop('checked', false);
  }
}


function showMarkerSingleSite(id) {
	//console.log("showMarkerSingleSite");
	// var id=id.id;
	// console.log("ID-->" + id);
	 
	 var selectedSiteId = id.split('_').slice(0, 3).join('_');
	// console.log("selectedSiteId-->" + selectedSiteId);
	 
	 var itemId= id.split('_').slice(3).join('_').split("_SingleSite")[0];
	 //console.log("itemId-->" + itemId);
	 
	 var PoId =  $("#" + id).closest("li.PO").attr("id");
	 //console.log("PoId-->" + PoId);
	 
	    if ($("#" + id).is(":checked")) {
	     	//console.log("checked");
	     	var checkboxes = $('[id$="'+itemId+'_SingleSite"]');
	     	//var checkboxes = $('[id$="'+selectedSiteId+"_"+itemId+'_SingleSite"]');
	  		var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	     if (allChecked) {
	    	document.getElementById(itemId +"_"+ PoId +'_Site').checked = true;
		 }
	      markersSites[selectedSiteId].setMap(map);
	      markerClusterSites.addMarker(markersSites[selectedSiteId]);
	      markerClusterSites.repaint();
	    }else{
		      document.getElementById(itemId +"_"+ PoId +'_Site').checked = false;		
		      markersSites[selectedSiteId].setMap(null);
		      markerClusterSites.removeMarker(markersSites[selectedSiteId]);
		      markerClusterSites.repaint();
	    }
	}

function PanTreeSites(id){
	//console.log("PanTreeSites.....");
	//console.log("id  ..... ",id);
	var selectedItem = id.split('_')[0] +"_"+ id.split('_')[1] +"_"+ id.split('_')[2];
	//console.log("selected item ..... "+selectedItem );
		//Site_Boq(selectedItem);
	if(selectedItem!=markersSite)
	{
		var selMarker="";		
		markerId=selectedItem;
		selMarker=markersSites[markerId];
		var latSitee = selMarker.getPosition().lat();
		var lngSitee = selMarker.getPosition().lng();
		position=selMarker.getPosition();							
		panTo(latSitee, lngSitee);
		//infowindow.setContent(selMarker.data);
		//infowindow.open(map,selMarker);					
		if(markersSite!="")
			{	
			var otherMarkers=markersSites[markersSite];			
			}		
		markersSite="";	
		markersSite=selectedItem;		
		map.setZoom(15);
		//console.log("PANNED");
	}	
}

///////////////////////////////////////////////
/* End of PO Item Site Tree Method */ 
//////////////////////////////////////////////

</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

