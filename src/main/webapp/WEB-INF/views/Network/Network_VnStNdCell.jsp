<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};
//console.log("lst: ",lst);
var listVen=${listVen};
//console.log("listVen: ",listVen);
var arrayParam=${arrayParam};
//console.log("arrayParam: ",arrayParam);

var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

function initMap() {

	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#cellBtn').toggleClass('activee');
    $('#vendorBtn').toggleClass('activee');
    
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
	//CreateMap_StNdCell(lst,map);
	CreateMap(lst,map);	
	CreateTree_VnStNdCell(listVen,map);
	
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Suppliers' class='AllSuppliers' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Suppliers</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

/* Start of Supp Site Node Cell Tree Method */ 
//////////////////////////////////////////////
//var SupSCreated=[];
function CreateTree_VnStNdCell(listVen,map){
	//console.log("SuppStNdCell");
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Vendors' class='AllVendors' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Vendors</span></li></ul>";
	$("#network_tree").append(str);
	var dFrag = document.createDocumentFragment();
	for (n=0; n < listVen.length; n++) {
		var str = "<li class='Vendor' id='"+listVen[n]+"' style='display:none; width:100px;'> <span class='folder' onclick='VenStNdCellCore("+listVen[n]+")'><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'><i class='fa fa-copyright fa-2x'></i>  "+listVen[n]+"</span>";		
		str+= "<ul><li id='" +listVen[n]+"_f' class='SiteFolder parent_li' style='display:none; margin-left:-40px'><input type='checkbox' id='"+listVen[n]+"_Site' class='AllSites' style='margin-left: 15px' onclick='showMarkersAllSitesOneNt("+listVen[n]+"_Site)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites </span></li></ul></li></ul>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);		
	}	
	document.getElementById('initial_ul').appendChild(dFrag);	
	tree_prop_selection();
	folder();
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedfolderVenIdContext=$(this).parent().attr('id');
		menuName=folderVen;			
		openContext(selectedfolderVenIdContext,"",folderVen,event);
	});
	$(".Vendor > .TreeSpan").contextmenu(function(){				
		selectedVenIdContext=$(this).parent().attr('id');
		//console.log("selectedVenIdContext: ",selectedVenIdContext);
		menuName=folderVen;			
		openContext(selectedVenIdContext,"",singleVen,event);
	});
}
folderVen = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Ven_Boq("");
			}	
		}
	]
});
singleVen = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Ven_Boq(selectedVenIdContext);
			}	
		}
	]
});

function AllSitesCheckFilter(){
	markerClusterSites.clearMarkers();
	CreateMap(lst,map);

	$('.AllVendors').bind("change",function() {	
		//console.log("markersSites.length: "+markersSites.length);
			if ($(this).is(':checked')){
				 $('#network_tree input[type="checkbox"][class="SingleSiteVen"]').prop('checked', false);
				 $('#network_tree input[type="checkbox"][class="AllSites"]').prop('checked', false);
				for(var x=0; x< markersSites.length; x++){	
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}									
			}					
			else{
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}


function showMarkersAllSitesOneNt(id) {
  var id = id.id;  
 // var suppId = id.split("_")[0];
  var venId = id.split("_Site")[0];
 
  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id).is(":checked");
  //var ulElement = document.getElementById(suppId);
  var liElements = document.getElementById(venId).querySelectorAll('.SingleSiteVen > input'); // Retrieve only <li> elements with <input> children
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
    liElements[i].checked = isChecked;
    if (isChecked && !markersSites[liElements[i].id.split("_" + venId)[0]].getMap()) {
      markersToAdd.push(markersSites[liElements[i].id.split("_" + venId)[0]]);
    }
  }
  if (isChecked) {
	$('#network_tree input[type="checkbox"][class="AllVendors"]').prop('checked', false);
    markerClusterSites.addMarkers(markersToAdd); // Add all markers at once
    markerClusterSites.repaint();
    //console.log("markers checked");
  } else {
    markerClusterSites.clearMarkers();
    $('#network_tree input[type="checkbox"]').prop('checked', false);
  }
}


function VenStNdCellCore(id){ 
	//var selectedItem=id.id;
	//tree_prop_general();
	//tree_Prop("#"+selectedItem+ "> span");
	//tree_Prop("#"+selectedItem+ "_f > span");
	
	//$("#"+selectedItem+ "> span").on('click',function () {
	//	var selectedSupp=$(this).parent().attr('id');
	var selectedVen=id.id;
	//console.log("selectedVen: ",selectedVen);
		//Create_TreeParent(selectedSupp,"Supp");		
		//if(!SupSCreated.includes(selectedSupp))
		//{
			//.push(selectedSupp);
		var VenChildrenLength=$("#" +selectedVen+"_f").find(' > ul > li').length;	
		if(VenChildrenLength==0){
			
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
				url: getContext()+'/FindOnClick_VenSiteNodeCell',
				data: {
					"selectedVen":selectedVen,	
					"paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			   		"paramAccess":paramAccess,
				    "paramCore":paramCore,
				},
				dataType: "json",
				success: function (data) {							        	
					if (data != null) {
						//var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
						var listVenSites=data.listVenSites;
						//console.log("listVenSites......",listVenSites);
						if(VenChildrenLength<listVenSites.length){
							var dFrag = document.createDocumentFragment();
							for (n = 0; n < listVenSites.length; n++) {								 
								var str = "<li class='SingleSiteVen' id='"+listVenSites[n][1]+"_"+listVenSites[n][4]+"' style='display:none; margin-left:-30px'><input type='checkbox' id='" + listVenSites[n][1]+"_"+listVenSites[n][4] +"_SingleSite' class='SingleSiteVen' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listVenSites[n][1]+"_"+listVenSites[n][4] + "_SingleSite)'></input><span class='folder' onclick='Create_TreeSites("+listVenSites[n][1]+"_"+listVenSites[n][4]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px' onclick='PanTreeSites("+listVenSites[n][1]+"_"+listVenSites[n][4]+")'><i class='fas fa-crosshairs fa-2x'></i>"+listVenSites[n][0]+"</span>";
								str+= "<ul><li id='" +listVenSites[n][1]+"_"+listVenSites[n][4]+"_f' class='NodeFolder' style='display:none; margin-left:15px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes </span></span></li></ul></li>";											   						    	
								const div = document.createElement('ul');
								div.innerHTML = str;
								dFrag.appendChild(div);   
							}  
						}
						document.getElementById(selectedVen+"_f").appendChild(dFrag);
						tree_prop_selection("#" +selectedVen+"_f .SingleSiteVen .TreeSpan");
			            Tree_PropagationAppendedNodes(selectedVen+"_f .SingleSiteVen");
			            
			            $(".SingleSiteVen > .TreeSpan").contextmenu(function(){				
			        		selectedSingleSiteIdContext=$(this).parent().attr('id');
			        		//console.log("selectedSingleSiteIdContext: ",selectedSingleSiteIdContext);
			        		var splitArray= selectedSingleSiteIdContext.split("_");
			        		idSite=splitArray[0] + "_" + splitArray[1] + "_" + splitArray[2];
			        		//console.log("idSite: ",idSite);
			        		idVen = splitArray[3];
			        		//console.log("idVen: ",idVen);
			        		menuName=SingleSite;			
			        		openContext(selectedSingleSiteIdContext,"",SingleSite,event);
			        	});
			            SingleSite = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			        				SiteVen_Boq(idSite,idVen);
			        			}	
			        		}
			        	]
			        });
					}
					 data=null;
				},
				error: function(result) {
					alert("Error");
				}
			});	
		}
		//}
	//}); 
}


function showMarkerSingleSite(id) {
	 var id=id.id;
	 //console.log("ID-->" + id);
	 var selectedSiteId = id.split('_').slice(0, 3).join('_');
	 var vendorId= id.split('_').slice(3).join('_').split("_SingleSite")[0];
	 
	    if ($("#" + id).is(":checked")) {
	     	//console.log("checked");
	     	var checkboxes = $('[id$="'+vendorId+'_SingleSite"]');
	  		var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	     if (allChecked) {
	    	document.getElementById(vendorId+'_Site').checked = true;
		 }
	      markersSites[selectedSiteId].setMap(map);
	      markerClusterSites.addMarker(markersSites[selectedSiteId]);
	      markerClusterSites.repaint();
	    }else{
		      document.getElementById(vendorId+'_Site').checked = false;		
		      markersSites[selectedSiteId].setMap(null);
		      markerClusterSites.removeMarker(markersSites[selectedSiteId]);
		      markerClusterSites.repaint();
	    }
	}


function PanTreeSites(id){
	var selectedItem = id.id.split('_').slice(0, 3).join('_');
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


function Create_TreeSites(id){
	var id=id.id	
	var selectedItem = id.split('_').slice(0, 3).join('_');
	var vendor = id.split('_').slice(3).join('_');
	//Site_Boq(selectedItem);
	//if(!sitesNCreated.includes(selectedItem))
	//	{
	//	sitesNCreated.push(selectedItem);	
	var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
	if (siteChildren == 0) {
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
		url: getContext()+'/FindOnClick_VenSiteNodeCell',
		data: {
			"selectedItem":selectedItem,
			"selectedVen":vendor,
			"paramEnterprise": paramEnterprise,
			"paramTransmission":paramTransmission,
	   		"paramAccess":paramAccess,
		    "paramCore":paramCore,
		},
		dataType: "json",
		success: function (data) {	
			if (data != null) {
				var listVenNodes=data.listVenNodes;
				var listCells=data.listCells;
				if(siteChildren<listVenNodes.length){
					//Create_TreeNode(listVenNodes,1,3);	
					//Create_TreeNode_Cell(listVenNodes,"FindOnClick_SuppSiteNodeCell",siteChildren,true,true,2,"Sup",4,"Sup",selectedItem);		
					Create_TreeNode_CellGeneral(listVenNodes,listCells,siteChildren, true,selectedItem);
		            Tree_PropagationAppendedNodes(selectedItem + "_" +vendor+"_f  .Node");
		            tree_prop_selection("#" +selectedItem + "_" +vendor+"_f .Node .TreeSpan");
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

///////////////////////////////////////////////
/* End of Supp Site Node Cell Tree Method */ 

</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

