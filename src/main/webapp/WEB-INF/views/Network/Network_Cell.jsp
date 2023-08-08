<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};
//console.log("lst......",lst);

var lstCells = ${listCells};
//console.log("lst cells......",lstCells);


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

var currentUrl = window.location.href;
//console.log("currentUrl....",currentUrl);
// Check if the Enterprise exists in the URL
if (currentUrl.indexOf("Enterprise") !== -1) {
 // console.log("Enterpriseeeee");
  $('#EnterpriseBtn').toggleClass('activee');
}

let srcCityAutocomplete, dstCityAutocomplete;
function initMap() {

	$('#cellBtn').toggleClass('activee');
	 
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
	CreateTree_Cell(lstCells,map);

		}
	
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Cells' class='AllCells' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
	
	////////////////////////
} /// End of init Map

///////////////////////////////////////////////
/* Start of Node Tree Method */ 
//////////////////////////////////////////////

function CreateTree_Cell(lstCells,map){  
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Cells' class='AllCells' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells</span></li></ul>";
	$("#network_tree").append(str);
	 var dFrag = document.createDocumentFragment();
	 for (n = 0; n < lstCells.length; n++){ 
		var str = "<li class='Cell' id='"+lstCells[n][2] +"_"+lstCells[n][3]+"' style='display:none'><input type='checkbox' id='" +lstCells[n][2] +"_"+lstCells[n][3]+"_SingleCell' class='SingleCell' onclick=\"showMarkerSingleSite('"+lstCells[n][2]+"_"+lstCells[n][3]+"_SingleCell')\"></input><span class='TreeSpan' style='width:395px;margin-left: 5px' onclick=\"PanTreeSites('"+lstCells[n][2]+"_"+lstCells[n][3]+"')\"> <i class='fa fa-server'></i> "+lstCells[n][1]+"</span></li>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);
	} 
	// Add fragment to a list: 
	 document.getElementById('initial_ul').appendChild(dFrag);	  
	 tree_prop_selection();
	 folder();
	}

	function AllSitesCheckFilter(){
		$('.AllCells').bind("change",function() {	
			markerClusterSites.clearMarkers();
				if ($(this).is(':checked')){
					$('#network_tree input[type="checkbox"][class="SingleCell"]').prop('checked', true);
					for(var x=0; x< markersSites.length; x++){	
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}									
				}					
				else{
				$('#network_tree input[type="checkbox"][class="SingleCell"]').prop('checked', false);					
					for(var x=0; x< markersSites.length; x++){
						markersSites[markersSites[x].ID].setMap(null);
						markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
							}	
					}
		});
	}

	
	function showMarkerSingleSite(id) {
		var Id= id.split("_SingleCell")[0];
		
		var parts = Id.split('_');	
		
		var nodeId = parts[0] + "_" + parts[1]+ "_" + parts[2]; // 2023_NODE_1
		
		if(parts[3]!="null"){
			var wareId = parts[3] + "_" + parts[4]+ "_" + parts[5]; // WARE_2021_13730
		}else{
			var wareId = parts[3]; // WARE_2021_13730
		}		
		if ($("#" + id).is(":checked")) {
			//console.log("checked");
			var checkboxes = $('[id$="_SingleCell"]');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
				if (!checkboxes[i].checked) {
					allChecked = false;
					break;
				}
			}
			if (allChecked) {
				document.getElementById('Cells').checked = true;
			}
			markersSites[wareId].setMap(map);			
			markerClusterSites.addMarker(markersSites[wareId]);
			markerClusterSites.repaint();
		}else{
			//console.log("unchecked");
			document.getElementById('Cells').checked = false;		
			markersSites[wareId].setMap(null);
			markerClusterSites.removeMarker(markersSites[wareId]);
			markerClusterSites.repaint();
		}
	}


	function PanTreeSites(id){		
		var parts = id.split('_');		
		var nodeId = parts[0] + "_" + parts[1]+ "_" + parts[2]; 
		
		if(parts[3]!="null"){
			var selectedItem = parts[3] + "_" + parts[4]+ "_" + parts[5]; 
		}else{
			var selectedItem = parts[3]; 
		}
		
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
/* End of Node Tree Method */ 

//////////////////////////////////////////////

//Submit selection to draw Tree and Map w.r.to active button
function Sumbitselection(arr){ 

	 switch (arr)
	 {
	 //Site-Node-Cell
	 	case "li_siteBtn,li_nodeBtn,li_cellBtn":
	 	case "siteBtn,nodeBtn,cellBtn":		
	 	{	
	 		window.location.href = getContext()+"/Network_StNdCell";
	 	} 
	 break;
	 	case "li_siteBtn,li_nodeBtn,li_cellBtn,li_EnterpriseBtn":
	 	case "siteBtn,nodeBtn,cellBtn,EnterpriseBtn":		
	 	{	
	 		var param1 = 'Enterprise';
	 		//var param2 = 'value2';
	 		var url = getContext() + '/Network_StNdCell';
	 		url += '?param1=' + encodeURIComponent(param1);
	 		//url += '&param2=' + encodeURIComponent(param2);
	 		window.location.href = url;
	 	//	$('#EnterpriseBtn').addClass('activee');
	 	}
		break;
		
	 //Site-NodeType-Node-Cell
	 case "li_siteBtn,li_nodeTypeeBtn,li_nodeBtn,li_cellBtn":
	 case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn":
	  {	
		 window.location.href = getContext()+"/Network_StNdTypNdCell";	
	  } 
	 break;
	 case "li_siteBtn,li_nodeTypeeBtn,li_nodeBtn,li_cellBtn,li_EnterpriseBtn":
	 case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,EnterpriseBtn":		
	 	{	
	 		var param1 = 'Enterprise';
	 		var url = getContext() + '/Network_StNdTypNdCell';
	 		url += '?param1=' + encodeURIComponent(param1);
	 		window.location.href = url;
	 	}
	break;
	 		
	 //NodeType-Site-Node-Cell
	 case "li_nodeTypeeBtn,li_siteBtn,li_nodeBtn,li_cellBtn":
	 case "nodeTypeeBtn,siteBtn,nodeBtn,cellBtn":
	 {		 
		 window.location.href = getContext()+"/Network_NdTypStNdCell";
	 }  
	 break;
	 case "li_nodeTypeeBtn,li_siteBtn,li_nodeBtn,li_cellBtn,li_EnterpriseBtn":
	 case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,EnterpriseBtn":		
	 	{			
	 		var param1 = 'Enterprise';
	 		var url = getContext() + '/Network_NdTypStNdCell';
	 		url += '?param1=' + encodeURIComponent(param1);
	 		window.location.href = url;
	 	}
	 break;
	 
	//Supplier-Site-Node-Cell
	 case "li_supplierBtn,li_siteBtn,li_nodeBtn,li_cellBtn":
	 case "siteBtn,nodeBtn,cellBtn,supplierBtn":
	 {
		 window.location.href = getContext()+"/Network_SupStNdCell";
	 }
	 break;	
	 
	//Supplier-Site-Node type-Node-Cell
		case "li_supplierBtn,li_siteBtn,li_nodeTypeeBtn,li_nodeBtn,li_cellBtn":
		case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,supplierBtn":
 {
			window.location.href = getContext()+"/Network_SupStNdTypNdCell";
    	}
	
		break;	
		//Supplier-NodeType-Site-Node-Cell
		 case "li_supplierBtn,li_nodeTypeeBtn,li_siteBtn,li_nodeBtn,li_cellBtn":
		 case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,supplierBtn":
		  {
			 window.location.href = getContext()+"/Network_SupNdTypStNdCell"; 			 
		    }
		break;
		//PO-Site-Items
		 case "li_poBtn,li_siteBtn,li_itemBtn":
		case "siteBtn,itemBtn,poBtn":
		 	{	
			 window.location.href = getContext()+"/Network_PoSiteItem"; 			 
		 	}
		 	break;
		 	 //PO-Items-Site         
		  case "li_poBtn,li_itemBtn,li_siteBtn":
		  case "siteBtn,itemBtn,poBtn":
		  {	  
			  window.location.href = getContext()+"/Network_PoItemSite"; 			
		  }
		  break;
		//Site-PO-Items         
		  case "li_siteBtn,li_poBtn,li_itemBtn":
		 case "siteBtn,itemBtn,poBtn":
		  {	
			 window.location.href = getContext()+"/Network_SitePoItem"; 
		  }
		  break;
	 		case "li_nodeBtn,li_cellBtn,li_nodeTypeeBtn":
			 case "nodeBtn,cellBtn,nodeTypeeBtn":
			 {
				window.location.href = getContext()+"/Network_NdTypNdCell"; 			
			 } break;
			 case "li_nodeBtn,li_cellBtn,li_nodeTypeeBtn,li_EnterpriseBtn":
	 		 case "nodeBtn,cellBtn,nodeTypeeBtn,EnterpriseBtn":		
	 		 	{	
	 		 		var param1 = 'Enterprise';
	 		 		var url = getContext() + '/Network_NdTypNdCell';
	 		 		url += '?param1=' + encodeURIComponent(param1);
	 		 		window.location.href = url;
	 		 	}
	 		break;
			//Node
			 case "nodeBtn":
			{
				 window.location.href = getContext()+"/Network_Node"; 
			 } break;
			 case "li_nodeBtn,li_EnterpriseBtn":
	 		 case "nodeBtn,EnterpriseBtn":		
	 		 	{	
	 		 		var param1 = 'Enterprise';
	 		 		var url = getContext() + '/Network_Node';
	 		 		url += '?param1=' + encodeURIComponent(param1);
	 		 		window.location.href = url;
	 		 	}
	 		break;
	 		//Cell
			 case "cellBtn":
			{
				 window.location.href = getContext()+"/Network_Cell"; 
			 } break;
			 case "li_cellBtn,li_EnterpriseBtn":
	 		 case "cellBtn,EnterpriseBtn":		
	 		 	{	
	 		 		var param1 = 'Enterprise';
	 		 		var url = getContext() + '/Network_Cell';
	 		 		url += '?param1=' + encodeURIComponent(param1);
	 		 		window.location.href = url;
	 		 	}
	 		break;
		default:
		{			
			alert("Selection is not available");
			return null;
		}break;
		 
		}
	}

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

