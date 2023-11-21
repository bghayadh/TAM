<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();


var lst = ${listSites};
var arrayParam=${arrayParam};

//var date=$("#ParsingDate").val();
//console.log("date...", date);

var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}


function initMap() {

	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#cellBtn').toggleClass('activee');
    $('#supplierBtn').toggleClass('activee');
    $('#nodeTypeeBtn').toggleClass('activee');
    
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
	CreateTree_StVenNdTpNdCell(lst,map);
	
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StSupNdTypNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

function CreateTree_StVenNdTpNdCell(lst,map){ 
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StSupNdTypNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
	$("#network_tree").append(str);
	for (n = 0; n < lst.length; n++) {
			var str = "<ul><li class='Site' id='"+lst[n][2]+"' style='display:none; margin-left:-10px'><input type='checkbox' id='"+ lst[n][2]+"_SingleSite' class='StSupNdTypNdCell_SingleSite' unchecked onclick='showMarkersChecked("+ lst[n][2] +"_SingleSite)'></input> <span class='folder' onclick='StVenNdTypNdCell("+lst[n][2] +")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px' onclick='PanTreeSites("+lst[n][2]+")'><span class='tree-span' style='margin-left:-15px;'> <i class='fa fa-cogs fa-2x'></i> "+lst[n][1]+"</span></span>";
			str+= "<ul><li id='" +lst[n][2]+"_f' class='VendorFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Suppliers </span></span></li></ul></li></ul>";
			$("#initial_ul").append(str);
	}
	tree_prop_selection();
	folder();
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedFolderSiteIdContext=$(this).parent().attr('id');
		menuName=folderSite;			
		openContext(selectedFolderSiteIdContext,"",folderSite,event);
	});
	
	$(".Site > .TreeSpan").contextmenu(function(){				
		selectedSiteIdContext=$(this).parent().attr('id');
		menuName=singleSite;			
		openContext(selectedSiteIdContext,"",singleSite,event);
	});
}

folderSite = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Site_Boq("");
			}	
		}
	]
});
singleSite = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Site_Boq(selectedSiteIdContext);
			}	
		 }
	]
});

function AllSitesCheckFilter(){
	$('.AllSites').bind("change",function() {	
			markerClusterSites.clearMarkers();
			if ($(this).is(':checked')){
				$(this).parent().find('.StSupNdTypNdCell_SingleSite').each(function(){		
						$(this).prop('checked', true);
					});
				for(var x=0; x< markersSites.length; x++){		
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}							
			}					
			else{		
				$(this).parent().find('.StSupNdTypNdCell_SingleSite').each(function() {
				$(this).prop('checked', false);	
			});
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}

function showMarkersChecked(id){
	var id =id.id;
	var Id= id.split("_SingleSite")[0];
		if ($("#"+id).is(":checked")) {	
			var checkboxes = document.querySelectorAll('.StSupNdTypNdCell_SingleSite');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
			  if (!checkboxes[i].checked) {
			    allChecked = false;
			    break;
			  }
			}
			if (allChecked) {
				$('#network_tree input[type="checkbox"][id="StSupNdTypNdCell_Sites"]').prop('checked', true);
			} 		
  			markersSites[Id].setMap(map);
  			markerClusterSites.addMarker(markersSites[Id]);
  			markerClusterSites.repaint();
			}else {		
				$('#network_tree input[type="checkbox"][id="StSupNdTypNdCell_Sites"]').prop('checked', false);
				markersSites[Id].setMap(null);
				markerClusterSites.removeMarker(markersSites[Id]);
				markerClusterSites.repaint();
			}
		 // });
	}


function StVenNdTypNdCell(id){
	var siteId= id.id;
	var siteChildrenLength=$("#" +siteId+"_f").find(' > ul > li').length;
	if(siteChildrenLength==0){
			
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
			  url: getContext()+'/findStSupNdTypNdCell_Sup',
			  data: {
			          "siteId":siteId,
			          "paramEnterprise": paramEnterprise,
					  "paramTransmission":paramTransmission,
			   		  "paramRAN":paramRAN,
				      "paramCore":paramCore,
			 		},
			 dataType: "json",
			 success: function (data) {	
				 if (data != null) {	
					var listSuppliers=data.listSuppliers;
					if(siteChildrenLength<listSuppliers.length){
						var dFrag = document.createDocumentFragment(); 
						for (j = 0; j < listSuppliers.length; j++){								
						     str="<li id='"+ listSuppliers[j][0] +"_"+listSuppliers[j][2]+"' class='SingleSupplier' style='display:none; margin-left:-30px'><span class='folder' onclick='RequestingNodeType("+ listSuppliers[j][0] +"_"+listSuppliers[j][2]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSuppliers[j][1]+"</span>";
						     str+= "<ul><li id='" + listSuppliers[j][0] +"_"+listSuppliers[j][2]+"_f' class='NodeTypeFolder' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type </span></span></li></ul></li>";						
						     const div = document.createElement('ul');
							div.innerHTML = str;
							dFrag.appendChild(div);          
						}
						document.getElementById(siteId+"_f").appendChild(dFrag);	
						tree_prop_selection("#" +siteId+"_f .SingleSupplier .TreeSpan");
			            Tree_PropagationAppendedNodes(siteId+"_f .SingleSupplier");
			            
			            var idSite;
			            $(".SingleSupplier > .TreeSpan").contextmenu(function(){				
			        		selectedSingleSupplierIdContext=$(this).parent().attr('id'); //venId_ware
			        		var index = selectedSingleSupplierIdContext.indexOf("WARE_2");
			        		if (index !== -1) {
			        			idSup= selectedSingleSupplierIdContext.substring(0, index-1);
			        			console.log("idSup: ",idSup);
			        		}
			        		siteId = selectedSingleSupplierIdContext.substring(index);
			        		menuName=SingleSupplier;			
			        		openContext(selectedSingleSupplierIdContext,"",SingleSupplier,event);
			        	});
			            SingleSupplier = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			        			  SupSite_Boq(idSup,siteId);
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
		} 		        			
}

function RequestingNodeType(id) {
	var index = id.id.indexOf('WARE_2');
	if (index !== -1) {
		var selectedSupp = id.id.substring(0, index-1);
	}
	var selectedItem = id.id.substring(index);
	
	var SupplierChildrenLength=$("#" +selectedSupp +"_" + selectedItem+"_f").find(' > ul > li').length;		
	if(SupplierChildrenLength==0){
		
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
				url: getContext()+'/FindOnClick_SuppStNdTypNdCell',
				data: {
					"selectedItem":selectedItem,
					"selectedSupp":selectedSupp	,
					"paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			   		"paramRAN":paramRAN,
				    "paramCore":paramCore,
				},
				dataType: "json",
				success: function (data) {					        	
				if (data != null) {					            		
					var listNodesType=data.listNodesType;
					for(j=0;j<listNodesType.length;j++)	
						{												
						var str= "<ul><li class='NodeType' id='" + listNodesType[j][0] +"_"+listNodesType[j][1]+"' style='display:none;margin-left:-20px;' class='folder'>";								  															
						str+="<span class='folder' onclick='SupNdCellCore(" + listNodesType[j][0] +"_"+listNodesType[j][1]+")'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
						str+= "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs'></i>"+listNodesType[j][0]+"</span></span></li></ul>";						
						$("#"+selectedSupp +"_" + selectedItem+"_f").append(str);
						str="<ul><li id='" +listNodesType[j][0] +"_"+listNodesType[j][1]+"_f' class='NodeFolder' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node </span></span></li></ul>";				
						$("#" + listNodesType[j][0] +"_"+listNodesType[j][1]).append(str);								

						tree_prop_selection("#" +selectedSupp +"_" + selectedItem +"_f .NodeType .TreeSpan");
				        Tree_PropagationAppendedNodes(selectedSupp +"_" + selectedItem +"_f .NodeType");
				        
				        var selectedSingleNt;
				        var selectedSite;
			            $(".NodeType > .TreeSpan").contextmenu(function(){				
			        		selectedSingleNtIdContext=$(this).parent().attr('id');
			        		var index = selectedSingleNtIdContext.indexOf('WARE_2');
			        		if (index !== -1) {
			        			selectedSingleNt = selectedSingleNtIdContext.substring(0, index-1);
			        		}
			        		var selectedItem = selectedSingleNtIdContext.substring(index);	
			        		menuName=SingleNt;	
			        		openContext(selectedSingleNtIdContext,"",SingleNt,event);
			        	});
			            SingleNt = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {
			        			  NodeTSup_Boq(selectedItem,selectedSingleNt,selectedSupp);
			        			  selectedItem="";
			        			}	
			        		}
			        	]
			        });
					}
				}
				data= null;
			},
			error: function(result) {
				alert("Error");
									}
			});    	
	}
}

function PanTreeSites(id){
	var selectedItem = id.id;
		if(selectedItem!=markersSite)
			{
			var selMarker="";		
			markerId=selectedItem;				
			selMarker=markersSites[markerId];
			var latSitee = selMarker.getPosition().lat();
			var lngSitee = selMarker.getPosition().lng();
			position=selMarker.getPosition();							
			panTo(latSitee, lngSitee);
			if(markersSite!="")
				{	
				var otherMarkers=markersSites[markersSite];			
				}		
			markersSite="";	
			markersSite=selectedItem;		
			map.setZoom(15);
	}	
}


function SupNdCellCore(id){
	var index = id.id.indexOf('WARE_2');
	if (index !== -1) {
		var selectedNodetType = id.id.substring(0, index-1);
	}
	var selectedItem = id.id.substring(index);	
	var sup_ware = $("#" + id.id).closest("li.SingleSupplier").attr("id"); 
	var index = sup_ware.indexOf('WARE_2');
	if (index !== -1) {
		var selectedSupp = sup_ware.substring(0, index-1);
	}

	var NdTypeChildrenLength=$("#" + selectedNodetType+"_"+selectedItem+"_f").find(' > ul > li').length;
	
	if(NdTypeChildrenLength==0){
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
				url:getContext()+'/FindOnClick_SuppStNdTypNdCell',
				data: {
					"selectedItem":selectedItem,
					"selectedNodetType":selectedNodetType,
					"selectedSupp":selectedSupp,
					"paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			   		"paramRAN":paramRAN,
				    "paramCore":paramCore,
				},
				dataType: "json",
				success: function (data) {														        	
					if (data != null) {
						var listNodes=data.listNodes;
						var listCells=data.listCells;
						Create_TreeNode_CellGeneral(listNodes,listCells,NdTypeChildrenLength, true,selectedItem);
			            tree_prop_selection("#" +selectedNodetType+"_"+selectedItem+"_f .Node .TreeSpan");
				}
					data= null;
				},
				error: function(result) {
					alert("Error");
				}
			});
		}
}

///////////////////////////////////////////////
/* End of Supp NodeType Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////

</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

