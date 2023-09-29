<%@ include file="NetworkCommon.jsp" %>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};
//console.log("LST...", lst);
var Long=${Long};
var Lat=${Lat};
var listNodes=${listNodes};
//console.log("listNodes...", listNodes);
var listCells=${listCells};
//console.log("listCells...", listCells);
var arrayParam=${arrayParam};
//console.log("arrayParam...", arrayParam);


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

if(arrayParam[0] == 1){
	 $('#EnterpriseBtn').toggleClass('activee'); 
	 console.log("EnterpriseBtn");
}if(arrayParam[1] == 1){
	 $('#transmBtn').toggleClass('activee');  
	 console.log("transmBtn");
}if(arrayParam[2] == 1){
	 $('#accessDBtn').toggleClass('activee');
	 console.log("accessDBtn");
}if(arrayParam[3] == 1){
	 $('#CoreBtn').toggleClass('activee');
	 console.log("CoreBtn");
}

function initMap() {

	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
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
	//CreateMap_StNdCell(lst,map);
	CreateMap(lst,map);	
	CreateTree_StNdCell(lst,map);
	
	}
	else{
		console.log("hi lst null");
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked'></input> <span class='folder' id='test1'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

/*     Strat of Site Node Cell Tree Method   */ 
//////////////////////////////////////////////
function CreateTree_StNdCell(lst,map){
	console.log("CREATE TREEEEEE");
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder' id='test1'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
	$("#network_tree").append(str);	
	var dFrag = document.createDocumentFragment();
	//console.log("lst[0][2] is " +lst[0][2]);
	for (n = 0; n < lst.length; n++){ 
		str="<li id='"+lst[n][2]+"' class='Site' style='display:none;width:100px;'><input type='checkbox' id='"+ lst[n][2]+"_SingleSite' class='StNdCell_SingleSite' unchecked onclick='showMarkersChecked("+ lst[n][2] +"_SingleSite)'></input> <span class='folder' onclick='StNdCellCoreFolder("+lst[n][2]+")' ><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px' onclick='StNdCellCore("+lst[n][2]+")'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+lst[n][1]+"</span>";
		str+= "<ul><li id='" +lst[n][2]+"_f' class='NodeFolder' style='display:none; margin-left:5px'><span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node </span></li></ul></li></ul>";
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
		//console.log("selectedSiteIdContext---------"+selectedFolderSiteIdContext);
		menuName=folderSite;			
		openContext(selectedFolderSiteIdContext,"",folderSite,event);
	});
	
	$(".Site > .TreeSpan").contextmenu(function(){				
		selectedSiteIdContext=$(this).parent().attr('id');
		//console.log("selectedSiteIdContext---------"+selectedSiteIdContext);
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


function ZoomOutMap(){				    	
		map.setZoom(6);
		var Nairobi=new google.maps.LatLng(0.796530,37.959529); 				
		//var Nairobi=new google.maps.LatLng(29.378586, 47.990341);
		map.setCenter(Nairobi);
		LatLanMouse(map);	
}

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
	console.log(" showMarkersChecked");
	var id =n.id;
	var Id= id.split("_SingleSite")[0]; // This Id is is used for markersSite[]
// To be deleted		
//	$("#"+id).on('change', function() {								
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
				console.log("markerClusterSitessss....REM......", markerClusterSites);
				markerClusterSites.repaint();
			}
//		  });
	}


function StNdCellCore(n)
	{
	console.log(" StNdCellCore1");
	//console.log(" StNdCellCore1 n " , n);
	var selectedItem=n.id;
	//tree_prop_general();
	//tree_Prop("#"+selectedItem+ "> span");
	//tree_Prop("#"+selectedItem+ "_f > span");	
	//var sitesNCreated=[];
	//markersSite="";
	//console.log(" markersSite... "+ markersSite);
// to be deleted	
//	$("#"+selectedItem+" > .TreeSpan").on('click',function (e) {		
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
			//console.log(" markersSite...after... "+ markersSite);
			map.setZoom(15);	
			//console.log("panned");
		}
//	}); // to be deleted	
}

function StNdCellCoreFolder(n)
{
	console.log(" StNdCellCore Folder");
	var selectedItem = n.id;
	console.log("selectedItem........",selectedItem);
	//var sitesNCreated=[];
	markersSite="";
	//console.log(" markersSite... folder..."+ markersSite);
	// to be deleted
//	$("#"+selectedItem+" > .folder").on('click',function (e) {		
		if(selectedItem!=markersSite)
		{				
		//console.log(" StNdCellCoreFolder clicking span >>>");
		//Site_Boq(selectedItem);
		//if(!sitesNCreated.includes(selectedItem))
		//{
			//sitesNCreated.push(selectedItem);
			var SiteChildren=$("#" +selectedItem+"_f").find(' > ul > li');
			var SiteChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;
			var lstNodes=[];
			for(var n=0; n<listNodes.length; n++){
				if(listNodes[n][4]==selectedItem){
					lstNodes.push(listNodes[n]);
				}
			}	
			var lstCells=[];
			for(var n=0; n<listCells.length; n++){
				if(listCells[n][3]==selectedItem){
					lstCells.push(listCells[n]);
				}
			}
			console.log("lstNodes.....",lstNodes);
			console.log("lstCells.....",lstCells);
			if(lstNodes!=null) {
				if(SiteChildrenLength<lstNodes.length){
					Create_TreeNode_CellGeneral(lstNodes,lstCells,SiteChildrenLength,false,selectedItem);
					nodeCreated=true;
				}	
			}	
	//}
}
//}); to be deleted
}
			
	/*
	if(selectedItem!=markersSite)
	{		
		var x = 'findSiteNodes_Cells';
		var y=['"selectedItem":selectedItem,"NodesAlreadyCreated":"false"'];

		if(!sitesNCreated.includes(selectedItem))
		{
			sitesNCreated.push(selectedItem);
			$.ajax({
				type: "GET",
				contentType: "application/json; charset=utf-8",
				url: getContext()+'/resources/js/Network/NetworkTree.js/'+x,
				data: {
					"selectedItem":selectedItem,
				},
				dataType: "json",
				success: function (data) {							        	
					if (data != null) {
						var SiteChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;		
						var listNodes=data.listNodes;
						if(listNodes!=null) {
							console.log("list nodes after--> "+listNodes);
							if(SiteChildrenLength<listNodes.length){
								//Create_TreeNode_Cell(listNodes,"findSiteNodes_Cells",SiteChildrenLength,false,true,2,"null",4,"null",selectedItem);
								Create_TreeNode_CellStNdCell(listNodes,SiteChildrenLength,false,true,2,"null",4,"null",selectedItem);
								nodeCreated=true;
							}	
						}
					}
				},
				error: function(result) {
					alert("Error");
				}
			});
		}	
	}
});	
}
*/
///////////////////////////////////////////////
/*     End of Site Node Cell Tree Method   */ 

</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

