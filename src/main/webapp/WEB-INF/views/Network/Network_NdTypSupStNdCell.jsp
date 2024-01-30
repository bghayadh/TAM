<%@ include file="NetworkCommon.jsp" %>
<title>Node Type Supplier Site Node Cell</title>
<script>	
$('#filterr').hide();
$('#removeFilter').hide();


var lst = ${listSites};
//console.log("lst...", lst);
var listNodesType = ${listNodesType};
//console.log("listNodesType: ",listNodesType);
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
	//CreateMap_StNdCell(lst,map);
	//CreateMap(lst,map);	
	CreateMap2(lst,map,arrayParam,date);
	CreateTree_NdTpSupStNdCell(listNodesType,map);
	
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='NodesType' class='AllNodesType' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

function CreateTree_NdTpSupStNdCell(listNodesType,map){ 
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='NodesType' class='AllNodesType' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type</span></li></ul>";
	$("#network_tree").append(str);
	for (n = 0; n < listNodesType.length; n++) {
		if(listNodesType[n][1]>0){
			var str = "<ul><li class='NodeType' id='"+listNodesType[n][0]+"' style='display:none; margin-left:10px'><span class='folder' onclick='NdTypSupStNdCellCore("+listNodesType[n][0] +")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'> <i class='fa fa-cogs fa-2x'></i> "+listNodesType[n][0]+"</span></span>";
			str+= "<ul><li id='" +listNodesType[n][0]+"_f' class='NodeTypeFolder parent_li' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Suppliers </span></span></li></ul></li></ul>";
			$("#initial_ul").append(str);
		}
	}
	tree_prop_selection();
	folder();
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedfolderNodeTypeIdContext=$(this).parent().attr('id');
		menuName=folderNodeType;			
		openContext(selectedfolderNodeTypeIdContext,"",folderNodeType,event);
	});
	$(".NodeType > .TreeSpan").contextmenu(function(){				
		selectedNodeTypeIdContext=$(this).parent().attr('id');
		console.log("selectedNodeTypeIdContext: ",selectedNodeTypeIdContext);
		menuName=NodeType;			
		openContext(selectedNodeTypeIdContext,"",NodeType,event);
	});
}

folderNodeType = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Site_Boq("");
			}	
		}
	]
});
NodeType = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			  NodeT_Boq("",selectedNodeTypeIdContext);
			}	
		}
	]
});

function AllSitesCheckFilter(){
	//console.log(" all sites check filter");
	markerClusterSites.clearMarkers();
	//CreateMap(lst,map);
	CreateMap2(lst,map,arrayParam,date);

	$('.AllNodesType').bind("change",function() {	
		//console.log("markersSites.length: "+markersSites.length);
			if ($(this).is(':checked')){
				 $('#network_tree input[type="checkbox"][class="SingleSiteSupp"]').prop('checked', false);
				 $('#network_tree input[type="checkbox"][class="SiteFolder"]').prop('checked', false);
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
	  var suppId_nodeType = id.split("_Site")[0].split("_");
	  var suppId = suppId_nodeType[0] + "_" + suppId_nodeType[1] + "_" + suppId_nodeType[2];
	  var nodeType =suppId_nodeType[3];
	 
	  markerClusterSites.clearMarkers();
	  var isChecked = $("#" + id).is(":checked");
	  var ulElement = document.getElementById(id.split("_Site")[0]);
	  var liElements = ulElement.querySelectorAll('.SingleSiteSupp > input'); // Retrieve only <li> elements with <input> children
	  //var count = liElements.length;
	  //console.log("Number of <li> elements:", count);
	  var markersToAdd = []; // Array to store markers that need to be added

	  for (var i = 0; i < liElements.length; i++) {
	    liElements[i].checked = isChecked;
	    if (isChecked && !markersSites[liElements[i].id.split("_" + suppId)[0]].getMap()) {
	        markersToAdd.push(markersSites[liElements[i].id.split("_" + suppId)[0]]);
	    }  
	  }
	  if (isChecked) {
		$('#network_tree input[type="checkbox"][class="AllNodesType"]').prop('checked', false);
	 	markerClusterSites.addMarkers(markersToAdd); // Add all markers at once
	    markerClusterSites.repaint();
	    //console.log("markers checked");
	  } else {
	    markerClusterSites.clearMarkers();
	    $('#network_tree input[type="checkbox"]').prop('checked', false);
	    //console.log("markers cleared");
	  }
	}


function NdTypSupStNdCellCore(id){
	var selectedNodetType= id.id;
	var NodeTypeChildrenLength=$("#" +selectedNodetType+"_f").find(' > ul > li').length;
	
	if(NodeTypeChildrenLength==0){
			
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
			  url: getContext()+'/findNodeTypeSupSiteNodeCell_Sup',
			  data: {
			          "selectedNodetType":selectedNodetType,
			          "paramEnterprise": paramEnterprise,
					  "paramTransmission":paramTransmission,
			   		  "paramRAN":paramRAN,
				      "paramCore":paramCore,
				      "date":date,
			 		},
			 dataType: "json",
			 success: function (data) {	
				 if (data != null) {	
					var listSuppliers=data.listSuppliers;		
					
					if(NodeTypeChildrenLength<listSuppliers.length){
						var dFrag = document.createDocumentFragment(); 
						for (j = 0; j < listSuppliers.length; j++){								
						     str="<li id='"+ listSuppliers[j][0] +"_"+listSuppliers[j][2]+"' class='SingleSupplier' style='display:none; margin-left:-30px'><span class='folder' onclick='RequestingSites("+ listSuppliers[j][0] +"_"+listSuppliers[j][2]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSuppliers[j][1]+"</span>";
						     str+= "<ul><li id='" + listSuppliers[j][0] +"_"+listSuppliers[j][2]+"_f' class='SiteFolder parent_li' style='display:none; margin-left:-40px'><input type='checkbox' id='" + listSuppliers[j][0] + "_" + listSuppliers[j][2] +"_Site' class='Sites' style='margin-left: 15px' onclick='showMarkersAllSitesOneNt("+ listSuppliers[j][0] + "_" + listSuppliers[j][2]+ "_Site)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites </span></span></li></ul></li>";						
						     const div = document.createElement('ul');
							div.innerHTML = str;
							dFrag.appendChild(div);          
						}
						document.getElementById(selectedNodetType+"_f").appendChild(dFrag);	
						tree_prop_selection("#" +selectedNodetType+"_f .SingleSupplier .TreeSpan");
			            Tree_PropagationAppendedNodes(selectedNodetType+"_f .SingleSupplier");
			            
			            var idSite;
			            $(".SingleSupplier > .TreeSpan").contextmenu(function(){				
			        		selectedSingleSupplierIdContext=$(this).parent().attr('id'); //suppId_nodetypeId
			        		var splitArray= selectedSingleSupplierIdContext.split("_");
			        		idSup=splitArray[0] + "_" + splitArray[1] + "_" + splitArray[2];
			        		menuName=SingleSupplier;			
			        		openContext(selectedSingleSupplierIdContext,"",SingleSupplier,event);
			        	});
			            SingleSupplier = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			        			  SuppNdTyp_Boq(idSup,selectedNodetType);
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


function RequestingSites(id) {
	var lastIndex = id.id.lastIndexOf('_');
	if (lastIndex !== -1) {
		var selectedSupp = id.id.substring(0, lastIndex);
	}
	var SelectedNodeType = id.id.substring(lastIndex + 1);
	
		var NdTypeChildrenLength=$("#" +selectedSupp +"_" + SelectedNodeType+"_f").find(' > ul > li').length;		
		
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
				url: getContext()+'/findNodeTypeSupSiteNodeCell_Site',
				data: {
					"selectedSupp":selectedSupp,
					"SelectedNodeType":SelectedNodeType,
		            "paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			     	"paramRAN":paramRAN,
				    "paramCore":paramCore,
				    "date":date,
				},
				dataType: "json",
				success: function (data) {	    
					if (data != null) {
						var listSuppSites = data.listSuppSites;
						if(NdTypeChildrenLength<listSuppSites.length){
							var dFrag = document.createDocumentFragment();
							for (n = 0; n < listSuppSites.length; n++) {	
								str="<li id='"+listSuppSites[n][1]+"_"+listSuppSites[n][2]+"' class='SingleSiteSupp' style='width:100px;display:none; margin-left:-30px'><input type='checkbox' id='" + listSuppSites[n][1]+"_"+listSuppSites[n][2] +"_SingleSite' class='SingleSiteSupp' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listSuppSites[n][1]+"_"+listSuppSites[n][2] + "_SingleSite)'></input><span class='folder' onclick='Create_Sites("+listSuppSites[n][1]+"_"+listSuppSites[n][2]+")' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'onclick='PanTreeSites("+listSuppSites[n][1]+"_"+listSuppSites[n][2]+")'><i class='fas fa-crosshairs fa-2x'></i><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSuppSites[n][0]+"</span>";
								str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][2]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes </span></span></li></ul></li></ul>"; 
								const div = document.createElement('ul');
								div.innerHTML = str;
								dFrag.appendChild(div);										  
							  }
							document.getElementById(selectedSupp+"_" + SelectedNodeType+"_f").appendChild(dFrag);
							tree_prop_selection("#" +selectedSupp+"_" +SelectedNodeType+"_f .SingleSiteSupp .TreeSpan");
				            Tree_PropagationAppendedNodes(selectedSupp+"_" +SelectedNodeType+"_f .SingleSiteSupp");
				            
				            $(".SingleSiteSupp > .TreeSpan").contextmenu(function(){				
				        		selectedSingleSiteIdContext=$(this).parent().attr('id');
				        		var index = selectedSingleSiteIdContext.indexOf("SUPP_2");
				    			if (index !== -1) {
				    				idSite = selectedSingleSiteIdContext.substring(0, index).slice(0, -1);
				    				selectedSupp = selectedSingleSiteIdContext.substring(index);
				    			}
				        		menuName=SingleSite;			
				        		openContext(selectedSingleSiteIdContext,"",SingleSite,event);
				        	});
				            SingleSite = new ContextMenu({
				        	  'theme': 'default',
				        	  'items': [
				        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				        			  SiteSupp_Boq(idSite,selectedSupp,SelectedNodeType);
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
					//}
}


function showMarkerSingleSite(id) {
	 var id=id.id;
	 var split= id.split("_SingleSite")[0];
	 
	 var index = split.indexOf("SUPP_2");
		if (index !== -1) {
			  var selectedSiteId = split.substring(0, index).slice(0, -1);
			  var supplierId = split.substring(index);
			}
	 var selectedNodeType = $("#" + split).closest("li.NodeType").attr("id");
		
	    if ($("#" + id).is(":checked")) {
	     	var checkboxes = $('[id$="'+supplierId+'_SingleSite"]');
	  		var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	     if (allChecked) {
	    	document.getElementById(supplierId+'_'+selectedNodeType+'_Site').checked = true;
		 }
	      markersSites[selectedSiteId].setMap(map);
	      markerClusterSites.addMarker(markersSites[selectedSiteId]);
	      markerClusterSites.repaint();
	    }else{
		      document.getElementById(supplierId+'_'+selectedNodeType+'_Site').checked = false;		
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

function Create_Sites(id){
	var index = id.id.indexOf("SUPP_2");
	if (index !== -1) {
		var selectedItem = id.id.substring(0, index).slice(0, -1);
		var selectedSupp = id.id.substring(index);
		}
	
	var selectedNodeType = $("#" + id.id).closest("li.NodeType").attr("id");
	
		var siteChildren=$("#"+selectedItem+"_"+selectedSupp+"_f") .find(' > ul > li').length;
		if(siteChildren == 0){	
			
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
				  url: getContext()+'/FindOnClick_NdTSupSiteNodeCell_NodeCell',
				  data: {
			                "selectedItem":selectedItem,
							"selectedSupp":selectedSupp,
							"SelectedNodeType":selectedNodeType,
							"paramEnterprise": paramEnterprise,
							"paramTransmission":paramTransmission,
						    "paramRAN":paramRAN,
							"paramCore":paramCore,
							"date":date,
				 },
				 dataType: "json",
				 success: function (data) {			        	
				     if (data != null) {
						var listSuppNodes=data.listSuppNodes;
						var listCells=data.listCells;
						if(siteChildren<listSuppNodes.length){	
							Create_TreeNode_CellGeneral(listSuppNodes,listCells,siteChildren, true,selectedItem);
							Tree_PropagationAppendedNodes(selectedItem+"_"+selectedSupp+"_f  .Node");
			            	tree_prop_selection("#" + selectedItem+"_"+selectedSupp+"_f .Node .TreeSpan");
							}
				        }
				     data = null;
				     },
				  error: function(result) {
				     alert("Error");
							         }
						     });
				//}   
		}
		//});
} 
///////////////////////////////////////////////
/* End of Supp NodeType Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////

</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

