<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

//var lst = ${listSites};

var lstNodeType = ${listNodesType};
//console.log("lst NodeType....",lstNodeType);
var lst = ${listNodes};
//console.log("lst nodes....",lst);
var arrayParam=${arrayParam};
//console.log("arrayParam...", arrayParam);


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

let srcCityAutocomplete, dstCityAutocomplete;
function initMap() {

	$('#nodeBtn').toggleClass('activee');
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
	CreateTree_NdTpNdCell(lstNodeType,map);

		}
	
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='NodesType' class='AllNodesType' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
	
	////////////////////////
} /// End of init Map

///////////////////////////////////////////////
/* Start of NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
function CreateTree_NdTpNdCell(lstNodeType,map){
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='NodesType' class='AllNodesType' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type</span></li></ul>";
	$("#network_tree").append(str);
	for (n = 0; n < lstNodeType.length; n++) {
		var str = "<ul><li class='NodeType' id='"+lstNodeType[n][0]+"' style='display:none;'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs fa-2x'></i> "+lstNodeType[n][0]+"</span></span>";
		str+= "<ul><li id='" +lstNodeType[n][0]+"_f' class='NodeFolder parent_li' style='display:none; margin-left:-42px'><input type='checkbox' id='" +lstNodeType[n][0]+"_Node' class='AllNodes' style='margin-left: 15px' unchecked onclick='showMarkersAllSitesOneNt("+lstNodeType[n][0]+"_Node)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes </span></span></li></ul></li></ul>";	
		$("#initial_ul").append(str);
		//NdTpNdCellCore(n);		
		
		var selectedNdTyp= lstNodeType[n][0];
		var NdTpChildrenLength=$("#" +selectedNdTyp+"_f").find(' > ul > li').length;	
		if(NdTpChildrenLength==0){
			//console.log("NdTpChildrenLength.....",NdTpChildrenLength);
			var dFrag = document.createDocumentFragment();
			for (j = 0; j < lst.length; j++)      //  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
			{			
				if(lst[j][11] == lstNodeType[n][0]){
					//console.log("equals");
					str = "<li class='Node' id='"+ lst[j][2]+"_"+lst[j][9]+"' style='display:none; margin-left:-15px'><input type='checkbox' id='" + lst[j][2]+"_"+lst[j][9]+"_"+lst[j][11]+ "' class='SingleNode' style='margin-left: 15px' onclick=\"showMarkerSingleSite('" + lst[j][2]+"_"+lst[j][9]+"_"+lst[j][11]+ "')\"></input><span class='folder' onclick=\"NdCellCore('"+lst[j][2]+"')\"> <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px' onclick=\" PanTreeSites('"+ lst[j][2]+"_"+lst[j][9]+"_"+lst[j][11]+"')\"><i class='fa fa-server'></i> "+ lst[j][10] +"</span>";
					str+= "<ul><li id='" +lst[j][2]+"_f' class='CellFolder parent_li' style='display:none; margin-left:10px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'> Cells </span></li></ul></li></ul>"; 
					const div = document.createElement('ul');
					div.innerHTML = str;
					dFrag.appendChild(div);	
				}
			}	
			document.getElementById(selectedNdTyp+"_f").appendChild(dFrag);		
			//tree_prop_selection("#" +selectedNdTyp+"_f .Node .TreeSpan");
	        //Tree_PropagationAppendedNodes(selectedNdTyp+"_f .Node");
		}
		
        
        var nodeId;
        var SiteId;
    	$(".Node > .TreeSpan").contextmenu(function(){				
    		selectedNodeIdContext=$(this).parent().attr('id');
    		var index = selectedNodeIdContext.indexOf("WARE_2");
			if (index !== -1) {
			  nodeId = selectedNodeIdContext.substring(0, index).slice(0, -1);
			  //console.log(" nodeId : "+nodeId);
			  SiteId = selectedNodeIdContext.substring(index);
			  //console.log("SiteId : " + SiteId);
			} else{
				  var lastIndex = selectedNodeIdContext.lastIndexOf("_");
				  nodeId = selectedNodeIdContext.substring(0, lastIndex);
				 // console.log(" nodeId null: "+nodeId);
				  SiteId =null;
				 // console.log("SiteId null: " + SiteId);	
			}
		
    		menuName=singleNode;			
    		openContext(selectedNodeIdContext,"",singleNode,event);
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
	$('.AllNodesType').bind("change",function() {	
		markerClusterSites.clearMarkers();
			if ($(this).is(':checked')){
				 $('#network_tree input[type="checkbox"][class="SingleNode"]').prop('checked', false);
				 $('#network_tree input[type="checkbox"][class="AllNodes"]').prop('checked', false);
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
  var nodeType = id.split("_")[0];
  
  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id).is(":checked");
  var ulElement = document.getElementById(nodeType);
  var liElements = ulElement.querySelectorAll('.SingleNode'); // Retrieve only <li> elements with <input> children
 
  //var count = liElements.length;
  //console.log("Number of <li> elements:", count);
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
    liElements[i].checked = isChecked;
    //console.log(" liElements[i].id:  ", liElements[i].id);
  
    var nodeSiteId = liElements[i].id.split("_" + nodeType)[0]; //nodeId_wareId
    var index = nodeSiteId.indexOf("WARE_2");
	if (index !== -1) {
	  var nodeId = nodeSiteId.substring(0, index).slice(0, -1);
	}else{
		var lastIndex = nodeSiteId.lastIndexOf("_");
		var nodeId = nodeSiteId.substring(0, lastIndex);
	}
	/*
    if(nodeSiteId.split("_")[3]!= "null"){
    	console.log("not null");
    	var wareId = nodeSiteId.split("_")[3] + "_" + nodeSiteId.split("_")[4] + "_" + nodeSiteId.split("_")[5];
    	console.log("wareId.....", wareId);
    }else{
    	console.log("null");
    	var wareId = nodeSiteId.split("_")[3];
        console.log("wareId null....", wareId);
    }
    */
    //if (isChecked && !markersSites[wareId].getMap()) {
    if (isChecked) {  
      markersToAdd.push(markersSites[nodeId]);
      //console.log("markersToAdd..a..", markersToAdd);
    }
  }
  if (isChecked) {
	  //console.log("ALL CHECKED");
		$('#network_tree input[type="checkbox"][class="AllNodesType"]').prop('checked', false);		
	    markerClusterSites.addMarkers(markersToAdd); // Add all markers at once
	    markerClusterSites.repaint();
	  } else {
	    markerClusterSites.clearMarkers();
	    $('#network_tree input[type="checkbox"]').prop('checked', false);
  }
}
  
  

//var NodeCreated=[];
/*
function NdTpNdCellCore(id)
{
	//tree_prop_general();	
	//tree_Prop("#"+listNodesType[n][0]+ "> span");
	//tree_Prop("#"+listNodesType[n][0]+ "_f > span");
	 var selectedNdTyp=id.id;
//	 $("#"+listNodesType[n][0]+" > span").on('click', function (e) {					
	 //var selectedNdTyp=$(this).parent().attr('id');
	// NodeT_Boq("",selectedNdTyp);
	 ////////////////
	 //Create_TreeParent(selectedNdTyp,"Nodetype");						
	 /////////////////
	//if(!NodeCreated.includes(selectedNdTyp))
	//{
	//NodeCreated.push(selectedNdTyp); 
	var NdTpChildrenLength=$("#" +selectedNdTyp+"_f").find(' > ul > li').length;	
	if(NdTpChildrenLength==0){
		
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
		url: getContext()+'/findNodeType_Nodes',
		data: {
			"selectedItem":selectedNdTyp,
			"paramEnterprise": paramEnterprise,
		 },
		 dataType: "json",
		 success: function (data) {							        	
			 if (data != null) {
				//var NdTpChildrenLength=$("#" +selectedNdTyp+"_f").find(' > ul > li').length;		
				var listNodes = data.listNodes;
				//console.log("listNodes.......",listNodes);
				var dFrag = document.createDocumentFragment();
				if(NdTpChildrenLength<listNodes.length){							            	
					for (j = 0; j < listNodes.length; j++)      //  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
					{			
						str = "<li class='Node' id='"+ listNodes[j][0]+"_"+listNodes[j][4]+"' style='display:none; margin-left:-15px'><input type='checkbox' id='" + listNodes[j][0]+"_"+listNodes[j][4]+"_"+listNodes[j][3]+ "' class='SingleNode' style='margin-left: 15px' onclick=\"showMarkerSingleSite('" + listNodes[j][0]+"_"+listNodes[j][4]+"_"+listNodes[j][3]+ "')\"></input><span class='folder' onclick=\"NdCellCore('"+listNodes[j][0]+"')\"> <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px' onclick=\" PanTreeSites('"+ listNodes[j][0]+"_"+listNodes[j][4]+"_"+listNodes[j][3]+"')\"><i class='fa fa-server'></i>"+listNodes[j][2]+"</span>";
						str+= "<ul><li id='" +listNodes[j][0]+"_f' class='CellFolder parent_li' style='display:none; margin-left:10px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'> Cells </span></li></ul></li></ul>"; 
						const div = document.createElement('ul');
						div.innerHTML = str;
						dFrag.appendChild(div);													
						}												
					document.getElementById(selectedNdTyp+"_f").appendChild(dFrag);		
					tree_prop_selection("#" +selectedNdTyp+"_f .Node .TreeSpan");
		            Tree_PropagationAppendedNodes(selectedNdTyp+"_f .Node");
		            
		            var nodeId;
		            var SiteId;
		        	$(".Node > .TreeSpan").contextmenu(function(){				
		        		selectedNodeIdContext=$(this).parent().attr('id');
		        		//console.log("selectedNodeIdContext", selectedNodeIdContext); //nodeId_wareId
		        		var parts = selectedNodeIdContext.split('_');		
		        		nodeId = parts[0] + "_" + parts[1]+ "_" + parts[2]; // 2023_NODE_1
		        		if(parts[3] !="null"){
		       				SiteId = parts[3] + "_" + parts[4]+ "_" + parts[5]; // WARE_2021_13730
		        		}else{
		        			SiteId = parts[3]; // WARE_2021_13730
		        		}
		        		menuName=singleNode;			
		        		openContext(selectedNodeIdContext,"",singleNode,event);
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
*/

function showMarkerSingleSite(id) {
	//console.log("id..."+id); //2023_NODE_Huawei_Transmission_618845_WARE_2021_13763_Router
	var index = id.indexOf("WARE_2");
	
	const parts = id.split("_");
	var nodeType =parts[parts.length - 1]; // SRanBs
	
	if (index !== -1) {
		  var nodeId = id.substring(0, index).slice(0, -1);
	}else{
		var secondLastIndex = id.lastIndexOf("_", id.lastIndexOf("_") - 1); // Find the second last index of '_'
		var nodeId = id.substring(0, secondLastIndex); // Get the substring from 0 to the second last index
	}
	
	if ($("#" + id).is(":checked")) {
		//console.log("checked");
		var checkboxes = $('[id$="_'+nodeType+'"]');
		//console.log("checkboxes....",checkboxes);
		var allChecked = true;
		for (var i = 0; i < checkboxes.length; i++) {
			if (!checkboxes[i].checked) {
				allChecked = false;
				break;
			}
		}
		if (allChecked) {
			document.getElementById(nodeType+'_Node').checked = true;
		}
		//console.log("markersSites....",markersSites);
		//console.log("markersSites[wareId]....",markersSites[wareId]);
		markersSites[nodeId].setMap(map);
		markerClusterSites.addMarker(markersSites[nodeId]);
		markerClusterSites.repaint();
	}else{
		//console.log("unchecked");
		document.getElementById(nodeType+'_Node').checked = false;		
		markersSites[nodeId].setMap(null);
		markerClusterSites.removeMarker(markersSites[nodeId]);
		markerClusterSites.repaint();
	}
}


function PanTreeSites(id){
	var index = id.indexOf("WARE_2");
	if (index !== -1) {
	  var nodeId = id.substring(0, index).slice(0, -1);
	}else{
		var secondLastIndex = id.lastIndexOf("_", id.lastIndexOf("_") - 1); // Find the second last index of '_'
		var nodeId = id.substring(0, secondLastIndex); // Get the substring from 0 to the second last index
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

function NdCellCore(id)
{
  var selectedNode=id;
  //console.log("selectedNode...",selectedNode);
  //var selectedItem=n.id;	  
  // tree_prop_general();
  // tree_Prop("#"+selectedItem+"> span");
  // tree_Prop("#"+selectedItem+"_f > span");
		  // $("#"+selectedItem+ "> span").on('click',function () {
	// var selectedNode=$(this).parent().attr('id');
	//if(!NCellCreated.includes(selectedNode))
		//{
		//NCellCreated.push(selectedNode);
	var NdChildrenLength=$("#" +selectedNode+"_f").find(' > ul > li').length;
	if(NdChildrenLength==0){
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
		 url: getContext()+'/findNode_Cells',
		 data: {
		      "selectedNode":selectedNode,
		      "paramEnterprise": paramEnterprise,
			  "paramTransmission":paramTransmission,
	   		  "paramAccess":paramAccess,
		      "paramCore":paramCore,
		},
		dataType: "json",
		success: function (data) {							        	
		    if (data != null) {
				//var NdChildrenLength=$("#" +selectedNode+"_f").find(' > ul > li').length;		
				var listCells =data.listCells;
				//console.log("listCells...",listCells);
				var dFrag = document.createDocumentFragment();
		
				if(NdChildrenLength<listCells.length){		
					
					for(var j=0; j< listCells.length; j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
					{			
						var str= "<ul><li class='Cells' id='" + listCells[j][0] +"' style='display:none; margin-left:-10px'>";					  				
						str += "<span class='TreeSpan' style='width:395px'><span class='tree-span'><i class='fa fa-vector-square'></i> "+listCells[j][1]+"</span></span></li></ul>";
						const div = document.createElement('ul'); 
						div.innerHTML = str;
						dFrag.appendChild(div);
						$("#"+selectedNode+"_f").append(str);																															
					}				
					//tree_prop_general();
					document.getElementById(selectedNode+"_f").appendChild(dFrag);
					tree_prop_selection("#" +selectedNode+"_f .Cells .TreeSpan");
		            Tree_PropagationAppendedNodes(selectedNode+"_f .Cells");
				}	
			  }
		    data=null;
		},
		error: function(result) {
		    alert("Error");
								}
							     });
					}
					//	});		
	} 
	  
			
///////////////////////////////////////////////
/* End of NodeType Node Cell Tree Method */ 

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

