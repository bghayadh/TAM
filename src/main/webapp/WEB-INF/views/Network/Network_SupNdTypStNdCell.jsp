<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();


var lst = ${listSites};
//console.log("lst...", lst);
var listSupp=${listSupp};
//console.log("listSupp...", listSupp);
var arrayParam=${arrayParam};
//console.log("arrayParam...", arrayParam);


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
	CreateMap(lst,map);	
	CreateTree_SupNdTpStNdCell(listSupp,map);
	
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

function CreateTree_SupNdTpStNdCell(listSupp,map){
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Suppliers' class='AllSuppliers' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Suppliers</span></li></ul>";
	$("#network_tree").append(str);
	var dFrag = document.createDocumentFragment();
	for (n=0; n < listSupp.length; n++) {
		var str = "<li class='Supplier' id='"+listSupp[n][0]+"' style='display:none; width:100px;'> <span class='folder' onclick='SuppNdTypStNdCellCore("+listSupp[n][0]+")'><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'><i class='fa fa-copyright fa-2x'></i> "+listSupp[n][1]+"</span>";				
		str+= "<ul><li id='" +listSupp[n][0]+"_f' class='NodeTypeFolder parent_li' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> NodeType </span></span></li></ul></li></ul>";						   						    	
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
				Supp_Boq("");
			}	
		}
	]
});

function AllSitesCheckFilter(){
	markerClusterSites.clearMarkers();
	CreateMap(lst,map);

	$('.AllSuppliers').bind("change",function() {	
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

function SuppNdTypStNdCellCore(id){
	var selectedSupp=id.id;
	//tree_Prop("#"+selectedItem+ "> span");
	//tree_Prop("#"+selectedItem+ "_f > span");
	//$("#"+selectedItem+ "> span").on('click',function () {
		//var selectedSupp=$(this).parent().attr('id');
		//Create_TreeParent(selectedSupp,"Supp");
		//if(!SupNtCreated.includes(selectedSupp))
		//{
			//SupNtCreated.push(selectedSupp);
		var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;			
		if(SuppChildrenLength==0){
			
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
			url: getContext()+'/FindOnClick_SuppNdTSiteNodeCell',
			data: {
			        "selectedSupp":selectedSupp,	
		            "paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			     	"paramAccess":paramAccess,
				    "paramCore":paramCore,
			},
			dataType: "json",
			success: function (data) {							        	
				if (data != null) {
					var listNodeType=data.listNodeType;
					if(SuppChildrenLength<listNodeType.length){						            	
						for (n = 0; n<listNodeType.length; n++) {							            								   		 
							var str = "<ul><li class='NodeType' id='"+listNodeType[n][0]+"_"+listNodeType[n][1]+"' style='display:none; margin-left:-20px'><span class='folder' onclick='RequestingSites("+listNodeType[n][0]+"_"+listNodeType[n][1]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs fa-2x'></i>"+listNodeType[n][0]+"</span></span>";
							str+= "<ul><li id='" +listNodeType[n][0]+"_"+listNodeType[n][1]+"_f' class='NodeFolder parent_li' style='display:none; margin-left:-40px'><input type='checkbox' id='"+listNodeType[n][0]+"_"+listNodeType[n][1]+"_Site' class='AllSites' style='margin-left: 15px' onclick='showMarkersAllSitesOneNt("+listNodeType[n][0]+"_"+listNodeType[n][1]+"_Site)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'>Sites </span></span></li></ul></li></ul>";							
							$("#"+selectedSupp+"_f").append(str);
							//tree_prop_general();	
							//tree_Prop("#"+listNodeType[n][0]+"_"+listNodeType[n][1]+"> span");
							//tree_Prop("#"+listNodeType[n][0]+"_"+listNodeType[n][1]+"_f > span");		
						     }               
						tree_prop_selection("#" +selectedSupp+ "_f .NodeType .TreeSpan");
			            Tree_PropagationAppendedNodes(selectedSupp+ "_f .NodeType");
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
	//}); 
}


function showMarkersAllSitesOneNt(id) {
  var id = id.id;
 // console.log("showMarkers id---> ", id); //eNodeB_SUPP_2023_256_Site

  //var startIndex = id.indexOf("_") + 1;
  //var endIndex = id.lastIndexOf("_");
  var suppId = id.substring(id.indexOf("_") + 1, id.lastIndexOf("_")); 
  //console.log("suppId: ", suppId); //SUPP_2023_256
  
  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id).is(":checked");
  //var ulElement = document.getElementById(id.split("_Site")[0]);
  var liElements = document.getElementById(id.split("_Site")[0]).querySelectorAll('.SingleSiteSupp > input'); // Retrieve only <li> elements with <input> children
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
	var result = liElements[i].id.split("_").slice(0, 3).join("_");	  
    liElements[i].checked = isChecked;
    if (isChecked && !markersSites[result].getMap()) {
      markersToAdd.push(markersSites[result]);
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

function RequestingSites(id) {
	var selectedSupp = id.id.substring(id.id.indexOf("_") + 1);	
	var SelectedNodeType = id.id.substring(0, id.id.indexOf("_"));
/*
	var res=$(this).parents().map(function() {
			return this.id;
			})
		.get()
		.join( "_" );
		parents=res.split("_");
		var SelectedNodeType=parents[0]; 
*/
		//if(!NtSiteCreated.includes(SelectedNodeType))
		  //{
			//NtSiteCreated.push(SelectedNodeType);
		var NdTypeChildrenLength=$("#" +SelectedNodeType+"_" +selectedSupp+"_f").find(' > ul > li').length;							            	
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
				url: getContext()+'/FindOnClick_SuppNdTSiteNodeCell',
				data: {
					"selectedSupp":selectedSupp,
					"SelectedNodeType":SelectedNodeType,
		            "paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			     	"paramAccess":paramAccess,
				    "paramCore":paramCore,
				},
				dataType: "json",
				success: function (data) {	    
					if (data != null) {
						var listSuppSites = data.listSuppSites;
						if(NdTypeChildrenLength<listSuppSites.length){
							var dFrag = document.createDocumentFragment();
							for (n = 0; n < listSuppSites.length; n++) {	
								str="<li id='"+listSuppSites[n][1]+"_"+listSuppSites[n][5]+"' class='SingleSiteSupp' style='width:100px;display:none; margin-left:-20px'><input type='checkbox' id='" + listSuppSites[n][1]+"_"+listSuppSites[n][5] +"_SingleSite' class='SingleSiteSupp' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listSuppSites[n][1]+"_"+listSuppSites[n][5] + "_SingleSite)'></input><span class='folder' onclick='Create_Sites("+listSuppSites[n][1]+"_"+listSuppSites[n][5]+")' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'onclick='PanTreeSites("+listSuppSites[n][1]+"_"+listSuppSites[n][5]+")'><i class='fas fa-crosshairs fa-2x'></i><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSuppSites[n][0]+"</span>";
								str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][5]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes </span></span></li></ul></li></ul>"; 
								//$("#"+SelectedNodeType+"_" +selectedSupp+"_f").append(str);
								const div = document.createElement('ul');
								div.innerHTML = str;
								dFrag.appendChild(div);										  
							  }
							document.getElementById(SelectedNodeType+"_" +selectedSupp+"_f").appendChild(dFrag);
							tree_prop_selection("#" +SelectedNodeType+"_" +selectedSupp+"_f .SingleSiteSupp .TreeSpan");
				            Tree_PropagationAppendedNodes(SelectedNodeType+"_" +selectedSupp+"_f .SingleSiteSupp");
				            
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
	 const selectedSiteId = id.id.split('_').slice(0, 3).join('_');
	 var nodeTypeId= id.id.split('_').slice(3).join('_').split("_SingleSite")[0];
	 	  
	var liElements =  $("#" + id.id).closest("li.NodeFolder").attr("id");
	var suppId = liElements.split("_").slice(1, 4).join("_");
	 
	    if ($("#" + id.id).is(":checked")) {
	     	//console.log("checked");
	     	var checkboxes = $('[id$="'+nodeTypeId+'_SingleSite"]');
	  		var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	     if (allChecked) {
	    	document.getElementById(nodeTypeId+"_"+suppId+'_Site').checked = true;
		}
	      markersSites[selectedSiteId].setMap(map);
	      markerClusterSites.addMarker(markersSites[selectedSiteId]);
	      markerClusterSites.repaint();
	    }else{
		      //console.log("unchecked");
		      document.getElementById(nodeTypeId+"_"+suppId+'_Site').checked = false;		
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
	//var sitesNCreated=[];
	//tree_prop_general();
	//console.log("id.....",id);
	
	var splitArray = id.id.split("_");
	var selectedItem = splitArray[0] + "_" + splitArray[1] + "_" + splitArray[2];
	var selectedNodeType = splitArray[3];
	
	//var parentLi = $("#" + id.id).closest("li.Supplier");		  
	var selectedSupp = $("#" + id.id).closest("li.Supplier").attr("id");

	//tree_prop_general();
	//tree_Prop("#"+selectedId+ "> span");
	//tree_Prop("#"+selectedId+ "_f > span"); 
	/*
	$("#"+selectedId+ "> span").on('click',function () {
			var res=$(this ).parents().map(function() {
				return this.id;
				})
				.get()
				.join( "," );	
			parents=res.split(",,");
			var selectedSupp=parents[4];
			parents2=selectedId.split("_");
			var SelectedNodeType=parents2[3];
			parents2=parents[0].split("_"+SelectedNodeType);
			var selectedItem=parents2[0];
		*/
		//Site_Boq(selectedItem);		

		//if(!sitesNCreated.includes(selectedItem))
			//{
			 //sitesNCreated.push(selectedItem);
		var siteChildren=$("#"+selectedItem+"_"+selectedNodeType+"_f") .find(' > ul > li').length;
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
				  url: getContext()+'/FindOnClick_SuppNdTSiteNodeCell',
				  data: {
			                "selectedItem":selectedItem,
							"selectedSupp":selectedSupp,
							"SelectedNodeType":selectedNodeType,
							"paramEnterprise": paramEnterprise,
							"paramTransmission":paramTransmission,
						    "paramAccess":paramAccess,
							"paramCore":paramCore,
				 },
				 dataType: "json",
				 success: function (data) {			        	
				     if (data != null) {
						var listSuppNodes=data.listSuppNodes;
						var listCells=data.listCells;
						if(siteChildren<listSuppNodes.length){	
							//Create_TreeNode_Cell(listSuppNodes,"FindOnClick_SuppNdTSiteNodeCell",siteChildren,true,true,2,"Sup",4,"Sup",selectedItem);	
							Create_TreeNode_CellGeneral(listSuppNodes,listCells,siteChildren, true,selectedItem);
							Tree_PropagationAppendedNodes(selectedItem+"_"+selectedNodeType+"_f  .Node");
			            	tree_prop_selection("#" + selectedItem+"_"+selectedNodeType+"_f .Node .TreeSpan");
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

