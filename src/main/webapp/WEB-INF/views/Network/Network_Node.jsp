<%@ include file="NetworkCommon.jsp" %>
<title>Network Node</title>
<script>	
$('#filterr').hide();
$('#removeFilter').hide();


var lst = ${listNodes};
//console.log("listNodes/lst...", lst);
var arrayParam = ${arrayParam};
//console.log("arrayParam...", arrayParam);
var date=$("#ParsingDate").val();

var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}


let srcCityAutocomplete, dstCityAutocomplete;
function initMap() {

	$('#nodeBtn').toggleClass('activee');
	 
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
	//CreateMap(lst,map);
	CreateMap2(lst,map,arrayParam,date);
	CreateTree_Nodes(lst,map);

		}
	
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Nodes' class='AllNodes' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
	
	////////////////////////
} /// End of init Map

///////////////////////////////////////////////
/* Start of Node Tree Method */ 
//////////////////////////////////////////////
var nodeId;
var SiteId;
function CreateTree_Nodes(lst,map){
	//Site_Boq("");         
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Nodes' class='AllNodes' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes</span></li></ul>";
	$("#network_tree").append(str);
	 var dFrag = document.createDocumentFragment();
	 for (n = 0; n < lst.length; n++){ 
		//str="<li id='"+lst[n][4]+"'  style='display:none;width:100px;'><span class='folder' onclick='NodeTree("+lst[n][4]+")' ><i class='fa fa-server fa-2x'></i> "+lst[n][2]+"</span>";
		var str = "<li class='Node' id='"+lst[n][2]+"_"+lst[n][9]+"' style='display:none'><input type='checkbox' id='" +lst[n][2]+"_"+lst[n][9] +"_SingleNode' class='SingleNode' onclick=\"showMarkerSingleSite('"+lst[n][2]+"_"+lst[n][9] +"_SingleNode')\"></input><span class='TreeSpan' style='width:395px;margin-left: 5px' onclick=\"PanTreeSites('"+lst[n][2]+"_"+lst[n][9] +"')\"> <i class='fa fa-server'></i> "+lst[n][10]+"</span></li>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);
	} 
	// Add fragment to a list: 
	 
	 document.getElementById('initial_ul').appendChild(dFrag);	  
	 tree_prop_selection();
	 folder();
	 
		$(".Initial > .TreeSpan").contextmenu(function(){				
			selectedfolderNodeIdContext=$(this).parent().attr('id');
			menuName=folderNode;			
			openContext(selectedfolderNodeIdContext,"",folderNode,event);
		});
		
    	$(".Node > .TreeSpan").contextmenu(function(){				
    		selectedNodeIdContext=$(this).parent().attr('id');
    		//console.log("selectedNodeIdContext", selectedNodeIdContext); //nodeId_wareId: 2023_NODE_Huawei_Transmission_618845_WARE_2021_13763
    		var index = selectedNodeIdContext.indexOf("WARE_2");
			if (index !== -1) {
				  nodeId = selectedNodeIdContext.substring(0, index).slice(0, -1);
				  //console.log(" nodeId : "+nodeId);
				  SiteId = selectedNodeIdContext.substring(index);
				  //console.log("SiteId : " + SiteId);
				} else{
					  var lastIndex = selectedNodeIdContext.lastIndexOf("_");
					  nodeId = selectedNodeIdContext.substring(0, lastIndex);
					  //console.log(" nodeId null: "+nodeId);
					  SiteId =null;
					  //console.log("SiteId null: " + SiteId);	
				}
    		menuName=singleNode;			
    		openContext(selectedNodeIdContext,"",singleNode,event);
    	});   	
	}

	folderNode = new ContextMenu({
		  'theme': 'default',
		  'items': [
			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
					Site_Boq("");
				}	
			}
		]
	});
	singleNode = new ContextMenu({
		  'theme': 'default',
		  'items': [
			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {		
					Node_Boq(SiteId,nodeId);
				}	
				   }
			]
	});

	function AllSitesCheckFilter(){
		$('.AllNodes').bind("change",function() {	
			markerClusterSites.clearMarkers();
				if ($(this).is(':checked')){
					$('#network_tree input[type="checkbox"][class="SingleNode"]').prop('checked', true);
					for(var x=0; x< markersSites.length; x++){	
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}									
				}					
				else{
				$('#network_tree input[type="checkbox"][class="SingleNode"]').prop('checked', false);					
					for(var x=0; x< markersSites.length; x++){
						markersSites[markersSites[x].ID].setMap(null);
						markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
							}	
					}
		});
	}

	
	function showMarkerSingleSite(id) {
		var Id= id.split("_SingleNode")[0];
		var index = id.indexOf("WARE_2");
		var indexnull = id.indexOf("SingleNode");
		if (index !== -1) {
			nodeId = id.substring(0, index).slice(0, -1);
		}else{
			var secondLastIndex = id.lastIndexOf("_", id.lastIndexOf("_") - 1); // Find the second last index of '_'
			var nodeId = id.substring(0, secondLastIndex); // Get the substring from 0 to the second last index
		}
		
		if ($("#" + id).is(":checked")) {
			var checkboxes = $('[id$="_SingleNode"]');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
				if (!checkboxes[i].checked) {
					allChecked = false;
					break;
				}
			}
			if (allChecked) {
				document.getElementById('Nodes').checked = true;
			}
			markersSites[nodeId].setMap(map);			
			markerClusterSites.addMarker(markersSites[nodeId]);
			markerClusterSites.repaint();
		}else{
			document.getElementById('Nodes').checked = false;		
			markersSites[nodeId].setMap(null);
			markerClusterSites.removeMarker(markersSites[nodeId]);
			markerClusterSites.repaint();
		}
	}


	function PanTreeSites(id){
		var index = id.indexOf("WARE_2");
		if (index !== -1) {
			nodeId = id.substring(0, index).slice(0, -1);
		}else{
			var lastIndex = id.lastIndexOf("_");
			var nodeId = id.substring(0, lastIndex);
		}
		
		if(nodeId!=markersSite)
		{
			var selMarker="";		
			markerId=nodeId;				
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
			markersSite=nodeId;		
			map.setZoom(15);
			//console.log("PANNED");
		}	
	} 
///////////////////////////////////////////////
/* End of Node Tree Method */ 

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

