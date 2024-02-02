<%@ include file="NetworkCommon.jsp" %>
<title>Site PO Item</title>
<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};
//console.log("lst...", lst);
//var listPO=${listPO};
//console.log("listPO...", listPO);
var arrayParam=${arrayParam};
//console.log("arrayParam...", arrayParam);
var date=$("#ParsingDate").val();
console.log("date...", date);

var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
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
	//CreateMap(lst,map);	
	CreateMap(lst,map,arrayParam,date);
	CreateTree_StPoItem(lst,map);
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StPoItem_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

/* Start of PO Item Site Tree Method */ 
///////////////////////////////////////

///////////////////////////////////////////////
/* Start of Site PO Item Tree Method */ 
//////////////////////////////////////////////

function CreateTree_StPoItem(lst,map){
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StPoItem_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
	$("#network_tree").append(str);
	//$("#network_tree").append("<label id='loadmore' style='margin-left:35%;margin-bottom:1%;visibility:hidden;'> Scroll to load more</label>");	 
	var dFrag = document.createDocumentFragment();
	for (n = 0; n < lst.length; n++){ 
		str="<li id='"+lst[n][2]+"' class='Site' style='display:none;width:100px; margin-left:-30px'><input type='checkbox' id='" + lst[n][2]+"_SingleSite' class='SingleSite' style='margin-left: 25px' onclick='showMarkerSingleSite("+lst[n][2]+"_SingleSite)'></input><span class='folder' onclick='StPoItemCore("+lst[n][2]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px' onclick='PanTreeSites("+lst[n][2]+")'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+lst[n][1]+"</span>";
		str+= "<ul><li id='" +lst[n][2]+"_f' class='POFolder' style='display:none; margin-left: 20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> PO </span></li></ul></li></ul>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);
	} 
	// Add fragment to a list: 
	document.getElementById('initial_ul').appendChild(dFrag);		
	tree_prop_selection();
	folder();
	
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedFolderSiteIdContext=$(this).parent().attr('id');
		menuName=folderSite;			
		openContext(selectedFolderSiteIdContext,"",folderSite,event);
	});
	
	$(".Site > .TreeSpan").contextmenu(function(){				
		selectedSingleSiteIdContext=$(this).parent().attr('id');
		menuName=folderSingleSite;			
		openContext(selectedSingleSiteIdContext,"",folderSingleSite,event);
	});
}

folderSite= new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			  SitePO_Boq("");
			}	
		}
	]
});

folderSingleSite= new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			  SitePO_Boq(selectedSingleSiteIdContext);
			}	
		}
	]
});


function AllSitesCheckFilter(){
	$('.AllSites').bind("change",function() {	
		markerClusterSites.clearMarkers();
			if ($(this).is(':checked')){
				$('#network_tree input[type="checkbox"][class="SingleSite"]').prop('checked', true);
				 for(var x=0; x< markersSites.length; x++){	
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}									
			}					
			else{
				$('#network_tree input[type="checkbox"][class="SingleSite"]').prop('checked', false);
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}


function showMarkerSingleSite(id) {
	 var Id = id.id.split('_SingleSite')[0];
	 if ($("#"+id.id).is(":checked")) {	
		var checkboxes = document.querySelectorAll('.SingleSite');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
			 	if (!checkboxes[i].checked) {
			 	allChecked = false;
			    break;
			  }
			}
			if (allChecked) {
				$('#network_tree input[type="checkbox"][class="AllSites"]').prop('checked', true);
			} 		
				markersSites[Id].setMap(map);
				markerClusterSites.addMarker(markersSites[Id]);
				markerClusterSites.repaint();
			}else {		
				//console.log("UNCHECKED");
				$('#network_tree input[type="checkbox"][class="AllSites"]').prop('checked', false);
				markersSites[Id].setMap(null);
				markerClusterSites.removeMarker(markersSites[Id]);
				markerClusterSites.repaint();
			}
	}

	
function PanTreeSites(id){
	var selectedItem = id.id;
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
	}	
}


function StPoItemCore(id){ 
	//console.log(" StPoItemCore");
	//var POCreated=[];
	//var itemCreated=[];
	var selectedSite=id.id;
	//tree_prop_general();	
	//tree_Prop("#"+selectedItem+ "> span");
	//tree_Prop("#"+selectedItem+ "_f > span");
	//$("#"+selectedItem+" > span").on('click',function (e) { 
		//Site_Boq(selectedItem);
		//	if(!POCreated.includes(selectedItem))
			//{
				//POCreated.push(selectedItem); 
			var SiteChildrenLength=$("#" +selectedSite+"_f").find(' > ul > li').length;
			if(SiteChildrenLength == 0){
				
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
						var paramRAN = true;
					}else{
						var paramRAN = false;
					}

					if(arrayParam[3]==1){
						var paramCore = true;
					}else{
						var paramCore = false;
					}
				
				 $.ajax({
					type: "GET",
					contentType: "application/json; charset=utf-8",
					url: getContext()+'/findSitePO_Items',
					data: {
						"selectedSite":selectedSite,
						"POAlreadyCreated":"false",
						"paramEnterprise": paramEnterprise,
						"paramTransmission":paramTransmission,
				     	"paramRAN":paramRAN,
					    "paramCore":paramCore,
					    "date":date,
					 },
					dataType: "json",
					success: function (data) {   									        	
						if (data != null) {
							//var SiteChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;	
							var listPO=data.listPO;
					
							if(SiteChildrenLength<listPO.length){       
								for(j=0;j<listPO.length;j++){  
									//var str= "<ul><li class='PO' id='" + listPO[j][0] +"' style='display:none; margin-left:-20px'>";   										  					
									var str = "<ul><li class='PO' id='"+listPO[j][0]+"_"+listPO[j][1]+"' style='display:none; margin-left:-20px'><span class='folder' onclick=\"requestItem('"+listPO[j][0]+"_"+listPO[j][1]+"')\"><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-file-invoice-dollar fa-2x'></i> "+listPO[j][0]+"</span></span>";
									str+= "<ul><li id='" +listPO[j][1]+"_"+listPO[j][0]+"_f' class='ItemFolder parent_li' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Item </span></li></ul></li></ul>";
									$("#"+listPO[j][1]+"_f").append(str);
								}
									//tree_prop_general();	
									//tree_Prop("#"+listPO[j][0]+"_"+listPO[j][1]+"> span");
									//tree_Prop("#"+listPO[j][0]+"_"+listPO[j][1]+"_f > span");
								tree_prop_selection("#" +selectedSite+"_f .PO .TreeSpan");
						        Tree_PropagationAppendedNodes(selectedSite+"_f .PO");
						        
						        var selectedPo;
						    	$(".PO > .TreeSpan").contextmenu(function(){				
						    		selectedPoSingleIdContext=$(this).parent().attr('id');
						    		var index = selectedPoSingleIdContext.indexOf("WARE_2");
									selectedPo = selectedPoSingleIdContext.substring(0, index).slice(0, -1);
									menuName=folderPoSingle;			
						    		openContext(selectedPo,"",folderPoSingle,event);
						    	});
						    	
						    	folderPoSingle= new ContextMenu({
						    		  'theme': 'default',
						    		  'items': [
						    			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {	
						    			 	 POSite_Boq(selectedPo);
						    				}	
						    			}
						    		]
						    	});
							 }
						 }  
						data=null;
					 },
					 error: function(result) {
					     alert("Error");
										     }
					 });
				//}
			}
		//	}   				 
	//});                       
	}
	
function requestItem(id){
	//console.log("iddddddd  ",id);
		var index = id.indexOf("WARE_2");
		if (index !== -1) {
			var selectedPo = id.substring(0, index).slice(0, -1);
			var selectedSite = id.substring(index);
		}
	/*
	$("#"+listPO[j][0]+"_"+listPO[j][1]+"> span").on('click',function () {																					
		var res=$(this ).parents().map(function() {
		    return this.id;
				})
			  			.get()
			  			.join( "," );
		parents=res.split(",,");
		parents2=res.split("_");
		var selectedItem=parents[2];
		parents2=res.split("_");
		var selectedPo=parents2[0];   												 															
		
		PO_Boq(selectedPo);
		var ware = selectedItem.substring(0, 4);
		if(!itemCreated.includes(selectedPo))
		{
			itemCreated.push(selectedPo); 
	*/
	
	var PoChildrenLength=$("#"+selectedSite+ "_" +selectedPo+"_f").find(' > ul > li').length;
	if(PoChildrenLength == 0){
		
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
				var paramRAN = true;
			}else{
				var paramRAN = false;
			}

			if(arrayParam[3]==1){
				var paramCore = true;
			}else{
				var paramCore = false;
			}
		
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url:getContext()+'/findSitePO_Items',
				data: {
					"selectedPO":selectedPo,
					"POAlreadyCreated":"True",
					"selectedSite":selectedSite,
					"paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			     	"paramRAN":paramRAN,
				    "paramCore":paramCore,
				    "date":date,
				 },
				dataType: "json",
				success: function (data) {        	
					if (data != null) {
						//var PoChildrenLength=$("#" +selectedPo+"_f").find(' > ul > li').length;
						var listItem=data.itemList;	
				
						if(PoChildrenLength<listItem.length){ 
							for (k = 0; k <listItem.length; k++) {
								var str="<ul><li class='Items' id='" + listItem[k][0] + "' style='display:none; margin-left:-20px'><span class='TreeSpan' style='width:395px'><span class='tree-span' ><i class='fa fa-bahai  fa-2x'></i> "+listItem[k][1]+ " / " +listItem[k][0] +"</span></span></li></ul>";
								$("#"+listItem[k][3]+"_"+listItem[k][2]+"_f").append(str);
							}
								//tree_prop_general();	
								//tree_Prop("#"+listItem[k][3]+"_"+listItem[k][2]+"> span");
								//tree_Prop("#"+listItem[k][3]+"_"+listItem[k][2]+"_f > span");
							tree_prop_selection("#"+selectedSite+ "_" +selectedPo +"_f .Items .TreeSpan");
					  
						}          	 
					 }
					data=null;
				 },
				 error: function(result) {
				 	alert("Error");
				     					}
			 });
	}
	//	}
	//});
}
///////////////////////////////////////////////
/* End of Site PO Item Tree Method */ 
</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

