<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};

var Long=${Long};
var Lat=${Lat};

var listNodes=${listNodes};
var listCells=${listCells};
var listNodesType=${listNodesType};


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

var currentUrl = window.location.href;
// Check if the Enterprise exists in the URL
if (currentUrl.indexOf("Enterprise") !== -1) {
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

	//console.log("Data is"+data);
	document.getElementById("network_tree").innerHTML ="";
	$("#network_tree").resizable({
		handles: "s", 	

	});

	
//	var directionsDisplay=new google.maps.DirectionsRenderer();
//	var directionsService=new google.maps.DirectionsService();
	
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
/*
	$(".tree-span").contextmenu(function(e) {
		var SiteId=$(this).parent().attr('id');
		var menu = $('.MenuFortree');//get the menu
		//var BoqUrl ='${pageContext.request.contextPath}/GetBoqList?SiteId='+SiteId;
	//$('.ShowBoqLink').attr("href", BoqUrl); // Set herf value

	$('.ShowBoqLink').one('click',function () {	
		
		// request to get all nodes for speci site
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: '${pageContext.request.contextPath}/GetBoqList',
				data: {"SiteId":SiteId},
				success : function(data)
				{
				$('#boq_table').empty();
					$.each(data , function( key, value ) {
						
						var tr = "<tr>"+
									"<td class='title'> # "+key+"</td>"+
									"<td>"+value+"</td>"+
								"</tr>";

					$("#boq_table").append(tr);
					});
					} 
				}); 		
		});      

		e.preventDefault();//Prevent the default action: the normal right-click-menu to show
			menu.css({
			display: 'block',//show the menu
			top: e.pageY,//make the menu be where you click (y)
			left: e.pageX//make the menu be where you click (x)
			});
			$(document).click(function() { //When you left-click
				menu.css({ display: 'none' });//Hide the menu
			});
	});

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
	CreateTree_StNdTypNdCell(lst,map);

		}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'>  Sites</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
	/////////////////////////
/*
		  const srcInput = document.getElementById('srcCitySearchNames');
		  const dstInput = document.getElementById('dstCitySearchNames');
		    const autoCompleteOptions = {
		     // bounds: defaultBoundsNew,
		      componentRestrictions: { country: ['ke'] },
		      fields: ['place_id', 'geometry', 'name'],
		      //strictBounds: false,
		      //types: ['establishment']
		      
		     types: ['(cities)']
		    };

		    srcCityAutocomplete = new google.maps.places.Autocomplete(srcInput, autoCompleteOptions);
		    dstCityAutocomplete  = new google.maps.places.Autocomplete(dstInput, autoCompleteOptions);
		    srcCityAutocomplete.addListener('place_changed', onSrcPlaceChanged);
		    dstCityAutocomplete.addListener('place_changed', onDstPlaceChanged);
		
	function onSrcPlaceChanged(){
	    
		    const place = srcCityAutocomplete.getPlace();
		    if (!place.geometry) {
 				$("#srcCitySearchNames").placeHolder = "Enter a city";
		    }

		    
		}
	function onDstPlaceChanged(){
	    
	    const place = dstCityAutocomplete.getPlace();
	    if (!place.geometry) {
				$("#dstCitySearchNames").placeHolder = "Enter a city";
	    }

	    
	}
*/
} /// End of init Map


/* Start of Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
function CreateTree_StNdTypNdCell(lst,map){
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'>  Sites</span></li></ul>";
	$("#network_tree").append(str); 
	var dFrag = document.createDocumentFragment();
	for (n = 0; n < lst.length; n++){ 
		str="<li id='"+lst[n][2]+"' class='Site' style='display:none;width:100px;'><input type='checkbox' id='"+ lst[n][2]+"_SingleSite' class='StNdCell_SingleSite' unchecked onclick='showMarkersChecked("+ lst[n][2] +"_SingleSite)'></input> <span class='folder' onclick='StNdTypNdCellCoreFolder("+lst[n][2]+")'><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px' onclick='StNdTypNdCellCore("+lst[n][2]+")'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+lst[n][1]+"</span>";
		str+= "<ul><li id='" +lst[n][2]+"_f' class='NodeTypeFolder' style='display:none; margin-left:2px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'> NodeType </span></li></ul></li></ul>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);
	} 
	//Add fragment to a list: 
	document.getElementById('initial_ul').appendChild(dFrag);	
	//initialSelection();
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
//var sitesNtCreated=[];
//var NtNodeNCreated=[];

function AllSitesCheckFilter(){
	$('.AllSites').bind("change",function() {	
			markerClusterSites.clearMarkers();
			if ($(this).is(':checked')){
				$(this).parent().find('.StNdCell_SingleSite').each(function(){		
						$(this).prop('checked', true);
					});
				for(var x=0; x< markersSites.length; x++){		
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}							
			}					
			else{		
				$(this).parent().find('.StNdCell_SingleSite').each(function() {
				$(this).prop('checked', false);	
			});
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}


function showMarkersChecked(n){
	//console.log(" showMarkersChecked");
	var id =n.id;
	var Id= id.split("_SingleSite")[0];
	// To be deleted	
	//$("#"+id).on('change', function() {								
		if ($("#"+id).is(":checked")) {	
			var checkboxes = document.querySelectorAll('.StNdCell_SingleSite');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
			  if (!checkboxes[i].checked) {
			    allChecked = false;
			    break;
			  }
			}
			if (allChecked) {
				$('#network_tree input[type="checkbox"][id="StNdCell_Sites"]').prop('checked', true);
			} 		
  			markersSites[Id].setMap(map);
  			markerClusterSites.addMarker(markersSites[Id]);
  			markerClusterSites.repaint();
			}else {		
				$('#network_tree input[type="checkbox"][id="StNdCell_Sites"]').prop('checked', false);
				markersSites[Id].setMap(null);
				markerClusterSites.removeMarker(markersSites[Id]);
				markerClusterSites.repaint();
			}
		 // });
	}

function StNdTypNdCellCoreFolder(n) {
	//console.log("StNdTypNdCellCoreFolder");
    var markersSite = "";
    var selectedItem = n.id;
 // to be deleted
  //  $("#"+selectedItem+"> span").on('click', function (e) {  
        if(selectedItem!=markersSite)
        {
        	//console.log("StNdTypNdCellCoreFolder clicked span");
            //Site_Boq(selectedItem);   
            var lstNodesType=[];
            for(var t=0; t<listNodesType.length; t++){
                if(listNodesType[t][1]==selectedItem){
                    lstNodesType.push(listNodesType[t]);
                }
            }                    
			var NdChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;
			if(NdChildrenLength==0){ 
            	//if(lstNodesType.length > 0)
            	//	{
                //sitesNtCreated.push(selectedItem);
                if (lstNodesType != null && lstNodesType.length > 0) {          
                    for(j=0;j<lstNodesType.length;j++) 
                    {                   
                        var str= "<ul><li class='NodeType' id='" + lstNodesType[j][0] +"_"+lstNodesType[j][1]+"'  class='folder' style='display:none; margin-left:-20px'>";                                                                                                                       
                        str+= "<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> <i class='fa fa-cogs '></i> "+lstNodesType[j][0]+"</span></li></ul>";
                        $("#"+selectedItem+"_f").append(str);
                        str="<ul><li id='" +lstNodesType[j][0] +"_"+lstNodesType[j][1]+"_f' class='NodeFolder parent_li' style='display:none; margin-left:-15px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node </span></li></ul>";
                        $("#" + lstNodesType[j][0] +"_"+lstNodesType[j][1]).append(str);
	                    NdTyFolder(lstNodesType[j][0] +"_"+lstNodesType[j][1]);                     
                   		} 
               	 	}
                tree_prop_selection("#" +selectedItem+"_f .NodeType .TreeSpan");
                Tree_PropagationAppendedNodes(selectedItem+"_f .NodeType");
            	//}
                $(".NodeType > .TreeSpan").contextmenu(function(){				
            		selectedFolderNodeTypeIdContext=$(this).parent().attr('id');
            		selectedNodeType = selectedFolderNodeTypeIdContext.split("_")[0];
            		//console.log("selectedFolderNodeTypeIdContext----oooo-----"+selectedFolderNodeTypeIdContext);
            		menuName=folderNodeType;			
            		openContext(selectedFolderNodeTypeIdContext,"",folderNodeType,event);
            	});   
                folderNodeType = new ContextMenu({
              	  'theme': 'default',
              	  'items': [
              		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
                          SiteId=selectedFolderNodeTypeIdContext.substring(selectedFolderNodeTypeIdContext.indexOf("_") + 1);
                       	 NodeT_Boq(SiteId,selectedNodeType);
              			}	
              		}
              	]
              }); 
			}        
        }
   // });
}


 function NdTyFolder(id){
	 //console.log(" NdTyFolder");
	// console.log(" NdTyFolder id >>> " , id);
                $("#" + id+ " .folder").on('click',function () {             
                	//console.log("node type clicked");                       	
                    var res=$(this ).parents().map(function() {
                        return this.id;
                    })
                    .get()
                    .join( "," );               
                    parents=res.split("_WARE");
                    var selectedNodetType=parents[0];                                                                                                           
                    var selectedItem=res.split(",,");
                    selectedItem=selectedItem[2];

	//NodeT_Boq(selectedItem,selectedNodetType);		
	//if(!NtNodeNCreated.includes(selectedNodetType))
//	{
		//NtNodeNCreated.push(selectedNodetType);	 										
		var lstNodes=[];
		for(var n=0;n<listNodes.length;n++){
			if(listNodes[n][4]==selectedItem && listNodes[n][3]==selectedNodetType){
				lstNodes.push(listNodes[n]);												
			}
		}	
			if(lstNodes!=null) {												
					var NdTypeChildrenLength=$("#" + selectedNodetType+"_"+selectedItem+"_f").find(' > ul > li').length;
					if(NdTypeChildrenLength==0){																											
						Create_TreeNode_CellGeneral(lstNodes,listCells,NdTypeChildrenLength,true,selectedItem);																								               
												}
								}										
						});	    
	}
                        
						
function StNdTypNdCellCore(n){
	///console.log("StNdTypNdCellCore");
//var markersSite="";
var selectedItem=n.id;
//tree_prop_general();
//tree_Prop("#"+selectedItem+ "> span");
//tree_Prop("#"+selectedItem+ "_f > span");
//console.log(" markersSite... "+ markersSite);
//to be deleted
//$("#"+selectedItem+"> .TreeSpan").on('click', function (e) {		
	if(selectedItem!=markersSite)
	{
		//console.log(" StNdCellCore clicking span");
		//Site_Boq(selectedItem);	
		var selMarker="";			
		markerId=selectedItem;
		selMarker=markersSites[markerId];
		var latSitee = selMarker.getPosition().lat();
		var lngSitee = selMarker.getPosition().lng();
		position=selMarker.getPosition();					
		panTo(latSitee, lngSitee);			
		//window.infowindow.setContent(selMarker.data);
		//window.infowindow.open(map,selMarker);
		const pos = new google.maps.LatLng(latSitee,lngSitee);
		
		if(markersSite!="")
		{
			var otherMarkers=markersSites[markersSite];					
		}		
		markersSite="";	
		markersSite=selectedItem;
		map.setZoom(15);	
		//console.log("panned");		
				}
			//});
		}
///////////////////////////////////////////////
/* End of Site NodeType Node Cell Tree Method */ 
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

