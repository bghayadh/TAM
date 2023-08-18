<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};

var listSupp=${listSupp};


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

var currentUrl = window.location.href;
console.log("currentUrl....",currentUrl);
// Check if the Enterprise exists in the URL
if (currentUrl.indexOf("Enterprise") !== -1) {
  console.log("Enterpriseeeee");
  $('#EnterpriseBtn').toggleClass('activee');
} 

function initMap() {

	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#cellBtn').toggleClass('activee');
    $('#supplierBtn').toggleClass('activee');
    
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
	CreateTree_SuppStNdCell(listSupp,map);
	
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul ><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li></ul>";
		$("#network_tree").append(str);
	}
} /// End of init Map

/* Start of Supp Site Node Cell Tree Method */ 
//////////////////////////////////////////////
//var SupSCreated=[];
function CreateTree_SuppStNdCell(listSupp,map){
	//console.log("SuppStNdCell");
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Suppliers' class='AllSuppliers' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Suppliers</span></li></ul>";
	$("#network_tree").append(str);
	var dFrag = document.createDocumentFragment();
	for (n=0; n < listSupp.length; n++) {
		var str = "<li class='Supplier' id='"+listSupp[n][0]+"' style='display:none; width:100px;'> <span class='folder' onclick='SuppStNdCellCore("+listSupp[n][0]+")'><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'><i class='fa fa-copyright fa-2x'></i>  "+listSupp[n][1]+"</span>";		
		str+= "<ul><li id='" +listSupp[n][0]+"_f' class='SiteFolder parent_li' style='display:none; margin-left:-40px'><input type='checkbox' id='"+listSupp[n][0]+"_Site' class='AllSites' style='margin-left: 15px' onclick='showMarkersAllSitesOneNt("+listSupp[n][0]+"_Site)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites </span></li></ul></li></ul>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);		
	}	
	document.getElementById('initial_ul').appendChild(dFrag);	
	tree_prop_selection();
	folder();
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedfolderSuppIdContext=$(this).parent().attr('id');
		menuName=folderSupp;			
		openContext(selectedfolderSuppIdContext,"",folderSupp,event);
	});
}
folderSupp = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Site_Boq("");
			}	
		}
	]
});

function AllSitesCheckFilter(){
	markerClusterSites.clearMarkers();
	CreateMap(lst,map);

	$('.AllSuppliers').bind("change",function() {	
		//console.log("markersSites.length: "+markersSites.length);
			if ($(this).is(':checked')){
				 $('#network_tree input[type="checkbox"][class="SingleSiteSupp"]').prop('checked', false);
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
  var suppId = id.split("_Site")[0];
 
  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id).is(":checked");
  console.log("*id ....", $("#" + id));
  console.log("isChecked ....", isChecked);
  //var ulElement = document.getElementById(suppId);
  var liElements = document.getElementById(suppId).querySelectorAll('.SingleSiteSupp > input'); // Retrieve only <li> elements with <input> children
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
    liElements[i].checked = isChecked;
    if (isChecked && !markersSites[liElements[i].id.split("_" + suppId)[0]].getMap()) {
      markersToAdd.push(markersSites[liElements[i].id.split("_" + suppId)[0]]);
    }
  }
  if (isChecked) {
	$('#network_tree input[type="checkbox"][class="AllSuppliers"]').prop('checked', false);
    markerClusterSites.addMarkers(markersToAdd); // Add all markers at once
    markerClusterSites.repaint();
  } else {
    markerClusterSites.clearMarkers();
    $('#network_tree input[type="checkbox"]').prop('checked', false);
  }
}


function SuppStNdCellCore(id){  
	//var selectedItem=id.id;
	//tree_prop_general();
	//tree_Prop("#"+selectedItem+ "> span");
	//tree_Prop("#"+selectedItem+ "_f > span");
	
	//$("#"+selectedItem+ "> span").on('click',function () {
	//	var selectedSupp=$(this).parent().attr('id');
	var selectedSupp=id.id;
		//Create_TreeParent(selectedSupp,"Supp");		
		//if(!SupSCreated.includes(selectedSupp))
		//{
			//.push(selectedSupp);
		var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
		if(SuppChildrenLength==0){
			
			if($('#EnterpriseBtn').hasClass('activee')){
				//console.log("ACTIVE ");
				var paramEnterprise = true;
			}else{
				//console.log("NOT ACTIVE");
				var paramEnterprise = false;
			}
			
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/FindOnClick_SuppSiteNodeCell',
				data: {
					"selectedSupp":selectedSupp,	
					"paramEnterprise": paramEnterprise,
				},
				dataType: "json",
				success: function (data) {							        	
					if (data != null) {
						//var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
						var listSuppSites=data.listSuppSites;
						console.log("listSuppSites......",listSuppSites);
						if(SuppChildrenLength<listSuppSites.length){
							var dFrag = document.createDocumentFragment();
							for (n = 0; n < listSuppSites.length; n++) {								 
								var str = "<li class='SingleSiteSupp' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' style='display:none; margin-left:-30px'><input type='checkbox' id='" + listSuppSites[n][1]+"_"+listSuppSites[n][4] +"_SingleSite' class='SingleSiteSupp' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listSuppSites[n][1]+"_"+listSuppSites[n][4] + "_SingleSite)'></input><span class='folder' onclick='Create_TreeSites("+listSuppSites[n][1]+"_"+listSuppSites[n][4]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px' onclick='PanTreeSites("+listSuppSites[n][1]+"_"+listSuppSites[n][4]+")'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
								str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none; margin-left:15px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes </span></span></li></ul></li>";											   						    	
								const div = document.createElement('ul');
								div.innerHTML = str;
								dFrag.appendChild(div);   
							}  
						}
						document.getElementById(selectedSupp+"_f").appendChild(dFrag);
						tree_prop_selection("#" +selectedSupp+"_f .SingleSiteSupp .TreeSpan");
			            Tree_PropagationAppendedNodes(selectedSupp+"_f .SingleSiteSupp");
			            
			            $(".SingleSiteSupp > .TreeSpan").contextmenu(function(){				
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
			        				Site_Boq(idSite);
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
	 var supplierId= id.split('_').slice(3).join('_').split("_SingleSite")[0];
	 
	    if ($("#" + id).is(":checked")) {
	     	console.log("checked");
	     	var checkboxes = $('[id$="'+supplierId+'_SingleSite"]');
	  		var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	     if (allChecked) {
	    	document.getElementById(supplierId+'_Site').checked = true;
		 }
	      markersSites[selectedSiteId].setMap(map);
	      markerClusterSites.addMarker(markersSites[selectedSiteId]);
	      markerClusterSites.repaint();
	    }else{
		      document.getElementById(supplierId+'_Site').checked = false;		
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
	var supplier = id.split('_').slice(3).join('_');
	//Site_Boq(selectedItem);
	//if(!sitesNCreated.includes(selectedItem))
	//	{
	//	sitesNCreated.push(selectedItem);	
	var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
	if (siteChildren == 0) {
		
		if($('#EnterpriseBtn').hasClass('activee')){
			//console.log("ACTIVE ");
			var paramEnterprise = true;
		}else{
			//console.log("NOT ACTIVE");
			var paramEnterprise = false;
		}
		
	$.ajax({		
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/FindOnClick_SuppSiteNodeCell',
		data: {
			"selectedItem":selectedItem,
			"selectedSupp":supplier,
			"paramEnterprise": paramEnterprise,
		},
		dataType: "json",
		success: function (data) {	
			if (data != null) {
				var listSuppNodes=data.listSuppNodes;
				var listCells=data.listCells;
				
				console.log("listSuppNodes......",listSuppNodes);
				console.log("listCells......",listCells);

				if(siteChildren<listSuppNodes.length){
					//Create_TreeNode(listSuppNodes,1,3);	
					//Create_TreeNode_Cell(listSuppNodes,"FindOnClick_SuppSiteNodeCell",siteChildren,true,true,2,"Sup",4,"Sup",selectedItem);		
					Create_TreeNode_CellGeneral(listSuppNodes,listCells,siteChildren, true,selectedItem);
		            Tree_PropagationAppendedNodes(selectedItem + "_" +supplier+"_f  .Node");
		            tree_prop_selection("#" +selectedItem + "_" +supplier+"_f .Node .TreeSpan");
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
//////////////////////////////////////////////

//////////////////////////////////////////////
	
//Submit selection to draw Tree and Map w.r.to active button
function Sumbitselection(arr){ 

	 switch (arr)
	 {
	 //Site-Node-Cell
	 	case "li_siteBtn,li_nodeBtn,li_cellBtn":
	 	case "siteBtn,nodeBtn,cellBtn":		
	 	{	
	 		console.log("site node cell");
	 		window.location.href = getContext()+"/Network_StNdCell";
	 	} 
	 break;
	 	case "li_siteBtn,li_nodeBtn,li_cellBtn,li_EnterpriseBtn":
	 	case "siteBtn,nodeBtn,cellBtn,EnterpriseBtn":		
	 	{	
	 		 console.log("site node cell Enterprise");
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
	 
	 case "li_supplierBtn,li_siteBtn,li_nodeBtn,li_cellBtn,li_EnterpriseBtn":
	 case "siteBtn,nodeBtn,cellBtn,supplierBtn,EnterpriseBtn":		
	 {
		 	var param1 = 'Enterprise';
	 		var url = getContext() + '/Network_SupStNdCell';
	 		url += '?param1=' + encodeURIComponent(param1);
	 		window.location.href = url;
	 }
	 break;	
	//Supplier-Site-Node type-Node-Cell
		case "li_supplierBtn,li_siteBtn,li_nodeTypeeBtn,li_nodeBtn,li_cellBtn":
 		case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,supplierBtn":
  {
 			window.location.href = getContext()+"/Network_SupStNdTypNdCell";
     	}
	
 		break;
 		case "li_supplierBtn,li_siteBtn,li_nodeTypeeBtn,li_nodeBtn,li_cellBtn,li_EnterpriseBtn":
 		case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,supplierBtn,EnterpriseBtn":
  		{
 		 	var param1 = 'Enterprise';
	 		var url = getContext() + '/Network_SupStNdTypNdCell';
	 		url += '?param1=' + encodeURIComponent(param1);
	 		window.location.href = url;
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

