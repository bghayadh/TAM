//////////////////////////////////////////////
/*         Map Section Network              */
//////////////////////////////////////////////



//Restore Button
function DefaultZoomControl(controlDiv2, map) {
			    const controlUI2 = document.createElement("div");
			    controlUI2.style.backgroundColor = "white";
			    controlUI2.style.border = "8px solid white";
			    controlUI2.style.borderRadius = "3px";
			    controlUI2.style.boxShadow = "0 2px 4px rgba(0,0,0,0.298039)";
			    controlUI2.style.cursor = "pointer";
			    controlUI2.style.marginLeft = "10.2px";
			    controlUI2.style.marginTop = "-8px";
			    controlUI2.style.width = "40px";
			 		    
			    controlUI2.innerHTML = '<button style="border:none;outline:none; background:white;font-size:13px;color:#585858" ><i class="fa fa-undo fa-lg "></i></button>';
			    controlUI2.title = "Reset Default Zoom";
			    controlDiv2.appendChild(controlUI2);
			    controlUI2.addEventListener("click", () => {			    	
 				map.setZoom(6);
 				var Nairobi=new google.maps.LatLng(0.796530,37.959529); 				
				map.setCenter(Nairobi);	        
			              });			  
			  }	
function LatLanMouse(map){		  
//mouse lat long
				map.addListener("mousemove", (mapsMouseEvent) => {					
				    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
				    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));	    
				  }); 
}
//restore default
function restoreDefault(map){
$(document).ready(function() {
		$("#initial_ul > .folder").on('click',
            function (e) {          
				//map.setZoom(6);c
			console.log("INITIAL");
 				//var Nairobi=new google.maps.LatLng(0.796530,37.959529);
			Site_Boq("");
			
 				map.setZoom(6);
 				//var Nairobi=new google.maps.LatLng(29.378586, 47.990341);
 				var Nairobi=new google.maps.LatLng(0.796530,37.959529);
				map.setCenter(Nairobi);
				
		
		//Main tree initial		
				$('.tree li > span').on('click', function (e) {
			     	$("#initial_ul").find(' > ul > li').removeClass("selected-span");
			        $(".tree li > span").removeClass("selected-span");
			        $(this).addClass("selected-span");
			        e.stopPropagation();
			     });
			$('.tree li:has(ul)').addClass('parent_li').find(' > .tree-span').attr(
			        'title', 'Collapse this branch'); 
			$("#network_tree i").css('margin-right', '5px');
					
		 	var children = $(this).parent('li.parent_li').find(' > ul > li');
            if (children.is(":visible")) {
                    children.hide();
                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
                } else {
                    children.show();
                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
                }
                e.stopPropagation();  
				
  		});
  	});
}
$(document).ready(function() {
const controlDiv2 = document.createElement("div");
DefaultZoomControl(controlDiv2, map);
map.controls[google.maps.ControlPosition.LEFT_CENTER].push(controlDiv2);
	});

function CreateMap_StNdCell(lst,map){		
	
				panPath = [];   // An array of points the current panning action will use
				panQueue = [];  // An array of subsequent panTo actions to take
				STEPS = 40;
				markers=[];
				icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
				icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
				icon2 = {
 					    path: google.maps.SymbolPath.CIRCLE,
				        fillOpacity: 0.9,
				        strokeColor: 'transparent',
				        strokeOpacity: 0.9,
				        strokeWeight: 1,
				        scale: 7
 					};
				dotmarker = {
					    path: google.maps.SymbolPath.CIRCLE,
				    fillOpacity: 0.9,
				    strokeColor: 'transparent',
				    strokeOpacity: 0.9,
				    strokeWeight: 1,
				    scale: 7
					};
				restoreDefault(map);
 				map.setZoom(6);
 				var Nairobi=new google.maps.LatLng(0.796530,37.959529); 				
 				//var Nairobi=new google.maps.LatLng(29.378586, 47.990341);
				map.setCenter(Nairobi);
 				LatLanMouse(map);			
				for(i=0;i<lst.length;i++){
						markerId=lst[i][2];	
						infowindow = new google.maps.InfoWindow();
						var latSite=lst[i][3];
						var lngSite=lst[i][4];
						const pos = new google.maps.LatLng(latSite,lngSite);
						var siteName=lst[i][1];				         					
						var siteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;	    	
						var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+lst[i][5];
						var listCellsCount=lst[i][6]+lst[i][7]+lst[i][8];
						listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+listCellsCount;
						var data="<div>"+siteName+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";
						const marker = new google.maps.Marker({
						        position: pos,
						        data:data,
						        icon:icon2,
						        ID:markerId
						         	    });
						 	marker.metadata = { id: markerId };
						    markers[markerId] = marker;
							markers.push(marker);													
	
						var IdSelectedTemp='';	
						var drawnSites=[];
						google.maps.event.addListener(marker, "click", function (e) {
						     	infowindow.setContent(this.data); 
					        	infowindow.open(map,this);
						     	map.setZoom(15);
						     	map.setCenter(pos);
						     	site_wave(map,pos);
						 						  				     	
 								var IdSelected=this.ID;
 								var Initialparent = $("#initial_ul").find(' > ul > li');
 								console.log("selected Site ID is >>> "+IdSelected );														
 								if(IdSelected!=IdSelectedTemp){
 									Initialparent.show();
 						                	$("#initial_ul").children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');   					             															 									
 									   $("ul").find("li").removeClass("selected-span"); 									  								 
 		 						       var children = $("#"+IdSelected);
 					                   if(IdSelectedTemp!=""){

 					                	// $("#"+IdSelectedTemp).hide();
							 				  	$("#"+IdSelectedTemp).removeClass("selected-span");							 				  	
							 				 	$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder');	
 					                	 Site_Boq(IdSelected);
 					                	 	$("#"+IdSelected).addClass("selected-span");
							                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');							                         
					                         	var container = $('div#network_tree'),
										                  scrollTo = $("#"+IdSelected);
					                       	container.animate({
				                		    scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop()
				                		});
 					                   }

 				                       else{
 				                    	 Site_Boq(IdSelected);
 								   			 	$("#"+IdSelected).addClass("selected-span");
 							                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');							                         
 					                         	var container = $('div#network_tree'),
 										                  scrollTo = $("#"+IdSelected);
 					                       	container.animate({
					                		    scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop()
					                		});	
 						                      	}		

 							        	 IdSelectedTemp="";
 				                         IdSelectedTemp=IdSelected;
 										}  
						     	
						     	
						 			});
				  				}
				let clusterOptions = { styles: [
				        {
				          url: 'resources/clusterIcons/blueCluster.png',
				         height: 65,
				          width:65,
				          anchorText:[-4,-4]
				        },
				      ] };

	var markerCluster = new MarkerClusterer(map,markers,clusterOptions , {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});	
	//markers.setMap(map);
		
	
	$(document).ready(function() {
	$("#Initial_ul > .folder").on('click', function (e) {
  			markerCluster.clearMarkers();	
  			markerCluster.addMarkers(markersMain);
  		  });	});
	$(document).ready(function() {
	$('.Supplier>span').on('click', function (e) {
		markerCluster.clearMarkers();
	});		});	
	$('.NodeType>span').on('click', function (e) {
		markerCluster.clearMarkers();
	});
	$('.PO>span').on('click', function (e) {
		markerCluster.clearMarkers();
	});
	
	
	}

	