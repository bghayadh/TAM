<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};

var Long=${Long};
var Lat=${Lat};

//var listNodes=${listNodes};
//var listCells=${listCells};
var listNodesType = ${listNodesType};


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

	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#cellBtn').toggleClass('activee');
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
	CreateTree_NdTypStNdCell(listNodesType,lst,map);

		}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='NodesType' class='AllNodesType' style='margin-left: 15px' unchecked></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
	////////////////////////
} /// End of init Map

///////////////////////////////////////////////
/* End of NodeType Site  Node Cell Tree Method */ 

/* Start of NodeType Site  Node Cell Tree Method */ 
//////////////////////////////////////////////

function CreateTree_NdTypStNdCell(listNodesType,lst,map){
	//console.log(" CreateTree_NdTypStNdCell");
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='NodesType' class='AllNodesType' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type</span></li></ul>";
	$("#network_tree").append(str);
	for (n = 0; n < listNodesType.length; n++) {
		if(listNodesType[n][1]>0){
			var str = "<ul><li class='NodeType' id='"+listNodesType[n][0]+"' style='display:none'><span class='folder' onclick='NdTypStNdCellCore("+listNodesType[n][0] +")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'> <i class='fa fa-cogs fa-2x'></i> "+listNodesType[n][0]+"</span></span>";
			str+= "<ul><li id='" +listNodesType[n][0]+"_f' class='NodeTypeFolder parent_li' style='display:none; margin-left:-45px'><input type='checkbox' id='" +listNodesType[n][0]+"_Site' class='AllSites' style='margin-left: 15px' unchecked onclick='showMarkersAllSitesOneNt("+listNodesType[n][0] +"_Site)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Site </span></span></li></ul></li></ul>";
			$("#initial_ul").append(str);
		}
		//tree_prop_general();
		//tree_Prop("#"+listNodesType[n][0]+ "> .folder");
		//tree_Prop("#"+listNodesType[n][0]+ "_f > .folder");
		//NdTypStNdCellCore(n);
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
	CreateMap(lst,map);

	$('.AllNodesType').bind("change",function() {	
		//console.log("markersSites.length: "+markersSites.length);
			if ($(this).is(':checked')){
				 $('#network_tree input[type="checkbox"][class="SingleSite"]').prop('checked', false);
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
  //console.log("showMarkers sites one node type");
  //console.log("showMarkers id---> ", id);
  var id = id.id;
  var nodeType = id.split("_")[0];
  var siteId = id.split("_")[1];
  //console.log("nodeType: ", nodeType);
  //console.log("siteId: ", siteId);
  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id).is(":checked");
  var ulElement = document.getElementById(nodeType);
  //console.log("ulElement elements:", ulElement);
  var liElements = ulElement.querySelectorAll('.SingleSite > input'); // Retrieve only <li> elements with <input> children
 // var count = liElements.length;
  //console.log("Number of <li> elements:", count);
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
    liElements[i].checked = isChecked;
    if (isChecked && !markersSites[liElements[i].id.split("_" + nodeType)[0]].getMap()) {
      markersToAdd.push(markersSites[liElements[i].id.split("_" + nodeType)[0]]);
    }
  }
  if (isChecked) {
	$('#network_tree input[type="checkbox"][class="AllNodesType"]').prop('checked', false);
    markerClusterSites.addMarkers(markersToAdd); // Add all markers at once
    markerClusterSites.repaint();
  } else {
    markerClusterSites.clearMarkers();
    $('#network_tree input[type="checkbox"]').prop('checked', false);
   // console.log("markers cleared");
  }
}




function NdTypStNdCellCore(id){
	//console.log("  NdTypStNdCellCore");
	//console.log(" id.id: ", id.id);
	//var selectedNodetType=id.split("_SingleSite")[0];
//	$("#"+id.id +"> .folder").on('click', function (e) {
	//	console.log("clicked span node type");
		//var selectedNodetType=$(this).parent().attr('id');	
		//Create_TreeParent(selectedNodetType	,"Nodetype");
		var selectedNodetType= id.id;
		//console.log("selectedNodetType>>  "+ selectedNodetType);
		//NodeT_Boq("",selectedNodetType);
	//	if(!NSitesCreated.includes(selectedNodetType))
	//	{
	//		NSitesCreated.push(selectedNodetType);	
			var NodeTypeChildrenLength=$("#" +selectedNodetType+"_f").find(' > ul > li').length;
			//console.log("NodeTypeChildrenLength==== " + NodeTypeChildrenLength);		
			if(NodeTypeChildrenLength==0){
				
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
				  url: getContext()+'/findNodeTypeSiteNode_Cells',
				  data: {
				          "selectedNodetType":selectedNodetType,
				          "paramEnterprise": paramEnterprise,
				 		},
				 dataType: "json",
				 success: function (data) {	
					 if (data != null) {
						// console.log("data=== "+data);
						//var NodeTypeChildren=$("#" +selectedNodetType+"_f").find(' > ul > li');
						//var NodeTypeChildrenLength=$("#" +selectedNodetType+"_f").find(' > ul > li').length;
						var listSites=data.listSites;		
					
						if(NodeTypeChildrenLength<listSites.length){
							var dFrag = document.createDocumentFragment();
							for (j = 0; j < listSites.length; j++){								
							     str="<li id='"+ listSites[j][2] +"_"+listSites[j][3]+"' class='SingleSite' style='display:none; margin-left:-30px'><input type='checkbox' id='" + listSites[j][2] + "_" + listSites[j][3] +"_SingleSite' class='SingleSite' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listSites[j][2] + "_" + listSites[j][3]+ "_SingleSite)'></input><span class='folder' onclick='StNdCellCore2folder("+ listSites[j][2] +"_"+listSites[j][3]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px' onclick='StNdCellCore2(" + listSites[j][2] + "_" + listSites[j][3] + ")'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSites[j][1]+"</span>";
							     str+= "<ul><li id='" + listSites[j][2] +"_"+listSites[j][3]+"_f' class='NodeFolder parent_li' style='display:none; margin-left:15px''><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes </span></span></li></ul></li>";						
							     const div = document.createElement('ul');
								div.innerHTML = str;
								dFrag.appendChild(div);          
							}
							document.getElementById(selectedNodetType+"_f").appendChild(dFrag);	
							tree_prop_selection("#" +selectedNodetType+"_f .SingleSite .TreeSpan");
				            Tree_PropagationAppendedNodes(selectedNodetType+"_f .SingleSite");
				            
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
				        				Site_Boq(idSite);
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
	//	}
	//});   		        			
}

function showMarkerSingleSite(id) {
	 // console.log("showMarkerSingleSite");
	 // console.log("id-->" , id.id);
	 var  id=id.id;

	//const lastUnderscoreIndex = id.lastIndexOf("_");
	 // const previousUnderscoreIndex = id.lastIndexOf("_",  id.lastIndexOf("_") - 1);
	  const Id = id.substring(0, id.lastIndexOf("_",  id.lastIndexOf("_") - 1));
	 // console.log("ID-->" + Id);
	  
	 // const lastUnderscoreIndexX = id.lastIndexOf("_");
	 // const previousUnderscoreIndexX = id.lastIndexOf("_",  id.lastIndexOf("_") - 1);
	  const value = id.substring(id.lastIndexOf("_",  id.lastIndexOf("_") - 1) + 1, id.lastIndexOf("_"));
	 // console.log("value -->", value);

	    if ($("#" + id).is(":checked")) {
	     	//console.log("checked");
	      	var checkboxes = $('[id$="'+value+'_SingleSite"]');
	  		//console.log("checkboxes......",checkboxes);  
	      	var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	     // console.log("..all checked..");
	      if (allChecked) {
	    	document.getElementById(value+'_Site').checked = true;
			//console.log("document.getElementById(value+'_Site')............"+ document.getElementById(value+'_Site'));
	      }
	      markersSites[Id].setMap(map);
	      markerClusterSites.addMarker(markersSites[Id]);
	      markerClusterSites.repaint();
	    }else{
		      //console.log("unchecked");
		      document.getElementById(value+'_Site').checked = false;		
		      markersSites[Id].setMap(null);
		      markerClusterSites.removeMarker(markersSites[Id]);
		      markerClusterSites.repaint();
	    }
	}


function StNdCellCore2folder(id) {
	// console.log("StNdCellCore2 folder");  
		var id=id.id;
		//console.log("id is ", id);
		//console.log(" markersSite...folder... "+ markersSite);
		// $("#" + id +" > .folder").on('click',function () {
		//$(document).on('click', "#" + id + " > .folder", function () {
	   // console.log("clicked folder of single site");
	//    var res = $(this).parents().map(function () {
	//      return this.id;
	//    })
	//      .get()
	//      .join(",");

	//    parents = res.split(",,");
	//    var selectedNodetType = parents[2];

//var parts = id.split("_");
var selectedNodetType = id.split("_").slice(-1).join("_");
//console.log("snt "+ selectedNodetType); // Output: "SRanBs"
var selectedItem = id.slice(0, id.indexOf("_" +selectedNodetType)); // Output: "Ware_..."
//console.log("selectedItem "+ selectedItem);
	 //   parents2 = parents[0].split("_" + selectedNodetType);
	  //  var selectedItem = parents2[0];
	    //Site_Boq(selectedItem);	    
	//    if (!sitesNCreated.includes(selectedItem)) {
	     // sitesNCreated.push(selectedItem);
	      var SiteChildrenLength = $("#" + selectedItem + "_" + selectedNodetType + "_f").find(' > ul > li').length;
	      if (SiteChildrenLength == 0) {
	    	  
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
	          url: getContext() + '/findNodeTypeSiteNode_Cells',
	          data: {
	            "selectedItem": selectedItem,
	            "selectedNodetType": selectedNodetType,
	            "paramEnterprise": paramEnterprise,
	          },
	          dataType: "json",
	          success: function (data) {
	            if (data != null) {
	              var listNodes = data.listNodes;
	              var listCells= data.listCells;
	              Create_TreeNode_CellGeneral(listNodes,listCells,SiteChildrenLength, true,selectedItem);
	              Tree_PropagationAppendedNodes(selectedItem + "_" + selectedNodetType + "_f .Node");
	              tree_prop_selection("#" +selectedItem + "_" +selectedNodetType+"_f .Node .TreeSpan");
	            }
	            data= null;
	          },
	          error: function (result) {
	            alert("Error");
	          }
	        });
	      }
	  //  }		  
	//  });
	}

function StNdCellCore2(id){
	//console.log(" StNdCellCore2");
	var id=id.id;
	//console.log("id ::: ",id);
	//$("#" + id +" > .TreeSpan").on('click',function () {
	//$(document).on('click', "#" + id + " > .TreeSpan", function () {
		/*
		console.log("selected span");
		var res=$(this ).parents().map(function() {
			return this.id;
		})
		.get()
		.join( "," );	
		parents=res.split(",,");
		var selectedNodetType=parents[2];
		parents2=parents[0].split("_"+selectedNodetType);
		var selectedItem=parents2[0];
	*/
	//var parts = id.split("_");
	var selectedNodetType = id.split("_").slice(-1).join("_");
	//console.log("snt "+ selectedNodetType); // Output: "SRanBs"
	var selectedItem = id.slice(0, id.indexOf("_" +selectedNodetType));
	//console.log("selectedItem "+ selectedItem);
		//Site_Boq(selectedItem);	
		
		if(selectedItem!=markersSite)
		{
			//console.log(" before panning");
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
			//console.log(" markersSite...after... "+ markersSite);
			map.setZoom(15);	
			//console.log("panned");
		}	
//	});     
}


///////////////////////////////////////////////
/* End of NodeType Site  Node Cell Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
 
	
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
		
		 case "li_supplierBtn,li_nodeTypeeBtn,li_siteBtn,li_nodeBtn,li_cellBtn,li_EnterpriseBtn":
		 case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,supplierBtn,EnterpriseBtn":
		  {
				var param1 = 'Enterprise';
		 		var url = getContext() + '/Network_SupNdTypStNdCell';
		 		url += '?param1=' + encodeURIComponent(param1);
		 		window.location.href = url; 			 
		    }
		break;
		//PO-Site-Items
		 case "li_poBtn,li_siteBtn,li_itemBtn":
		case "siteBtn,itemBtn,poBtn":
		 	{	
			 window.location.href = getContext()+"/Network_PoSiteItem"; 			 
		 	}
		 	break;
		 case "li_poBtn,li_siteBtn,li_itemBtn,li_EnterpriseBtn":
	 	 case "siteBtn,itemBtn,EnterpriseBtn,poBtn":
	 		 	{	
	 			var param1 = 'Enterprise';
		 		var url = getContext() + '/Network_PoSiteItem';
		 		url += '?param1=' + encodeURIComponent(param1);
		 		window.location.href = url; 	 
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

