
function CreateMap_StNdCell(lst,listNodes,listCells,map){

			   
				panPath = [];   // An array of points the current panning action will use
				panQueue = [];  // An array of subsequent panTo actions to take
				STEPS = 40;
				markers=[];
				icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
				icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
				icon2 = {
					    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
					    scaledSize: new google.maps.Size(50, 50), // scaled size

					};


				map.setZoom(1);

				var beirut=new google.maps.LatLng(33.88894,35.49442);
				map.setCenter(beirut);
				
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
						        icon:icon,
						        ID:markerId
						         	    });
						 	marker.metadata = { id: markerId };
						    markers[markerId] = marker;
							markers.push(marker);

						var IdSelectedTemp="";	

						google.maps.event.addListener(marker, "click", function (e) {

						     	infowindow.setContent(this.data); 
					        	infowindow.open(map,this);
						     	map.setZoom(15);
						     	map.setCenter(pos);

								var IdSelected=this.ID;
								
								console.log("selected Site ID is >>> "+IdSelected );

								
						
								if(IdSelected!=IdSelectedTemp){

									   $("ul").find("li").removeClass("selected-span");

				                       var childrenInitial=$("#initial_ul").find(' > ul > li');

		 						       var children = $("#"+IdSelected).find(' > ul > li');

					                   if(IdSelectedTemp!=""){
					 						  	//markers[IdSelectedTemp].setIcon(icon);
					 							
						                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');
		
							 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
		
							 				  	childrenTemp.hide('fast');
							 				 	$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder');;
							 						     
							 					//markers[IdSelected].setIcon(icon2);
							 					$("#"+IdSelected).addClass("selected-span");
						 						       
						 						     
							 					$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
							                    $('#initial_ul >li>span>i').removeClass('fa-folder').addClass('fa-folder-open');
							                    //$('#initial_ul').addClass("selected-span");
							                    children.show('fast');
		
						 						childrenInitial.show('fast');
							                    IdSelectedTemp=IdSelected;
					                          }

				                       else{
				   							 

				   						    	// $('#initial_ul').addClass("selected-span");
				   						    	// markers[IdSelected].setIcon(icon2);

					                         	childrenInitial.show('fast');
					 						 	children.show('fast');

								   			 	$("#"+IdSelected).addClass("selected-span");

							                 	// $('.tree li.parent_li > .tree-span').parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
							                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
					                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

						                      	}		

							        	 IdSelectedTemp="";
				                         IdSelectedTemp=IdSelected;
										}  
						 			});
				  				}
				var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});


	}



function CreateMap_StNdTypNdCell(lst,listNodesType,listNodes,listCells,map){
				
				var panPath = [];   // An array of points the current panning action will use
				var panQueue = [];  // An array of subsequent panTo actions to take
				var STEPS = 40;
				var markers=[];
				var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
				var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
				var icon2 = {
					    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
					    scaledSize: new google.maps.Size(50, 50), // scaled size

					};

				map.setZoom(1);

				var beirut=new google.maps.LatLng(33.88894,35.49442);
				map.setCenter(beirut);

				// Circles Options
				const CircleOptions=[{
				fillColor: "blue",
				fillOpacity: 0.26,
				map: map,
				radius: 100,
				strokeColor: "blue",
				strokeOpacity: 1,
				strokeWeight: 0.3},{
				
				fillColor: "blue",
				fillOpacity: 0.26,
				map: map,
				radius:400,
				strokeColor: "blue",
				strokeOpacity: 1,
				strokeWeight: 0.3
				}];
				
				// Binding Circles to the markers
				
						const circle = new google.maps.Circle(CircleOptions[0]);
						const circle2 = new google.maps.Circle(CircleOptions[1]);

						///Animation of the circles
			    	      var direction = 1;

			    	      setInterval(function() {
			    	          var radius = circle2.getRadius();
			    	          var radius2 = circle.getRadius();
			    	        
			    	          if ((radius > 300) || (radius < 150)) {
			    	              direction *= -1;
			    	          }
			    	          circle2.setRadius(radius + direction *100);
			    	          circle.setRadius(radius2 + direction * 200);
			    	      },800);

				for(i=0;i<lst.length;i++){

					 	markerId=lst[i][2];
					 	var infowindow = new google.maps.InfoWindow();
				 
					 	var latSite=lst[i][3];
					 	var lngSite=lst[i][4];
				 		const pos = new google.maps.LatLng(latSite,lngSite);
					
					 	var siteName=lst[i][1];				         

						siteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
				     
						var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+lst[i][5];
						var listCellsCount=lst[i][6]+lst[i][7]+lst[i][8];
						listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+listCellsCount;
						var data="<div>"+siteName+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";

						const marker = new google.maps.Marker({
						        position: pos,
						        data:data,
						        icon:icon,
						        ID:markerId
						         	    });
						 	marker.metadata = { id: markerId };
						    markers[markerId] = marker;
							markers.push(marker);
							
						var IdSelectedTemp="";
				
						google.maps.event.addListener(marker, "click", function (e) {
						         //Wrap the content inside an HTML DIV in order to set height and width of InfoWindow.
						     	
						     	infowindow.setContent(this.data); 
				  	        	infowindow.open(map,this);
						     	map.setZoom(15);
						     	map.setCenter(pos);
						     	
						     	
						     	
						     	var IdSelected=this.ID;
								
								console.log("selected Site ID is >>> "+IdSelected );

								
						
								if(IdSelected!=IdSelectedTemp){

									   $("ul").find("li").removeClass("selected-span");

				                       var childrenInitial=$("#initial_ul").find(' > ul > li');

		 						       var children = $("#"+IdSelected).find(' > ul > li');

					                   if(IdSelectedTemp!=""){
					 						  	//markers[IdSelectedTemp].setIcon(icon);
					 							
						                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');
		
							 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
		
							 				  	childrenTemp.hide('fast');
							 				 	$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder');;
							 						     
							 					//markers[IdSelected].setIcon(icon2);
							 					$("#"+IdSelected).addClass("selected-span");
						 						       
						 						     
							 					$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
							                    $('#initial_ul >li>span>i').removeClass('fa-folder').addClass('fa-folder-open');
							                    //$('#initial_ul').addClass("selected-span");
							                    children.show('fast');
		
						 						childrenInitial.show('fast');
							                    IdSelectedTemp=IdSelected;
					                          }

				                       else{
				   							 

				   						    	// $('#initial_ul').addClass("selected-span");
				   						    	// markers[IdSelected].setIcon(icon2);

					                         	childrenInitial.show('fast');
					 						 	children.show('fast');

								   			 	$("#"+IdSelected).addClass("selected-span");

							                 	// $('.tree li.parent_li > .tree-span').parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
							                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
					                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

						                      	}		

							        	 IdSelectedTemp="";
				                         IdSelectedTemp=IdSelected;
										}
						     	
						     	
						     	
						     	
						     });
				      

			}
			var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
			var markersSite="";

			$('.Site > span').on('click', function (e) {

				var selectedItem=$(this).parent().attr('id');
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);

				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
				 		console.log("==============///////////clicked"+selectedItem);

			 			var selMarker="";
					
						markerId=selectedItem;
						selMarker=markers[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						selMarker.setIcon(icon2);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);

						const pos = new google.maps.LatLng(latSitee,lngSitee);
						
						
						circle.setCenter(pos);
						circle2.setCenter(pos);
						
						if(markersSite!="")
							{
		 						var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
						
							}
						markersSite="";	
						markersSite=selectedItem;
					
						map.setZoom(15);
					}
				}
			});


			$(".NodeType > span").on('click',function () {
				
				var res=$(this ).parents()
  				.map(function() {
	    			return this.id;
	  								})
	  						.get()
	  						.join( "," );

				parents=res.split(",,");
				var selectedItem=parents[2];
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);

				if(ware=="WARE"){
						if(selectedItem!=markersSite)
							{
 								console.log("==============///////////clicked"+selectedItem);

								var selMarker="";
	
								markerId=selectedItem;				
								selMarker=markers[markerId];
								var latSitee = selMarker.getPosition().lat();
								var lngSitee = selMarker.getPosition().lng();					
								selMarker.setIcon(icon2);
								panTo(latSitee, lngSitee);
								infowindow.setContent(selMarker.data);
								infowindow.open(map,selMarker);

	
								if(markersSite!="")
									{
										var otherMarkers=markers[markersSite];			
										otherMarkers.setIcon(icon);
		
										}

								markersSite="";	
								markersSite=selectedItem;

								map.setZoom(15);
								}
							}
						});


			$(".Node > span").on('click',function () {
				
				var res=$(this ).parents()
  				.map(function() {
	    			return this.id;
	  								})
	  							.get()
	  							.join( "," );

				parents=res.split(",,");
				var selectedItem=parents[4];
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);

				if(ware=="WARE"){
						if(selectedItem!=markersSite)
							{
 								console.log("==============///////////clicked"+selectedItem);

								var selMarker="";
	
								markerId=selectedItem;				
								selMarker=markers[markerId];
								var latSitee = selMarker.getPosition().lat();
								var lngSitee = selMarker.getPosition().lng();					
								selMarker.setIcon(icon2);
								panTo(latSitee, lngSitee);
								infowindow.setContent(selMarker.data);
								infowindow.open(map,selMarker);

	
								if(markersSite!="")
									{
										var otherMarkers=markers[markersSite];			
										otherMarkers.setIcon(icon);
		
											}

								markersSite="";	
								markersSite=selectedItem;

								map.setZoom(15);
							}
						}
					});


			$(".Cell > span").on('click',function () {

				var res=$(this ).parents()
  								.map(function() {
	    					return this.id;
	  								})
	  							.get()
	  							.join( "," );

				parents=res.split(",,");
				var selectedItem=parents[6];
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);

				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
 						console.log("==============///////////clicked"+selectedItem);

						var selMarker="";
	
						markerId=selectedItem;				
						selMarker=markers[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						selMarker.setIcon(icon2);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);

	
						if(markersSite!="")
								{
								var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
		
									}

						markersSite="";	
						markersSite=selectedItem;

						map.setZoom(15);
					}
				}
			});

			function panTo(newLat, newLng) {

					if (panPath.length > 0) {
				    // We are already panning...queue this up for next move
				    panQueue.push([newLat, newLng]);
				  			} 
					else {
				    // Lets compute the points we'll use
				    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
				    var curLat = map.getCenter().lat();
				    var curLng = map.getCenter().lng();
				    var dLat = (newLat - curLat)/STEPS;
				    var dLng = (newLng - curLng)/STEPS;

				    for (var i=0; i < STEPS; i++) {
				      panPath.push([curLat + dLat * i, curLng + dLng * i]);
				    }
				    panPath.push([newLat, newLng]);
				    panPath.shift();      // LAZY SYNCRONIZED LOCK
				    setTimeout(doPan, 20);
				  }
				}

			function doPan() {
				  var next = panPath.shift();
				  if (next != null) {
				    // Continue our current pan action
				    map.panTo( new google.maps.LatLng(next[0], next[1]));
				    setTimeout(doPan, 20 );
				  } else {
				    // We are finished with this pan - check if there are any queue'd up locations to pan to 
				    var queued = panQueue.shift();
				    if (queued != null) {
				      panTo(queued[0], queued[1]);
				    }
				  }
				}

			}



		    
function CreateMap_StNdTpSupNdCell(suplistSites,sup_NodeTypeResult,sup_ListSupp,listNodes,listCells,map){
				var markers=[];
				var markersSup=[];
				var panPath = [];   // An array of points the current panning action will use
				var panQueue = [];  // An array of subsequent panTo actions to take
				var STEPS = 70;
				var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
				var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
				var icon2 = {
					    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
					    scaledSize: new google.maps.Size(50, 50), // scaled size

					};

				// Circles Options
				const CircleOptions=[{
				fillColor: "blue",
				fillOpacity: 0.26,
				map: map,
				radius: 100,
				strokeColor: "blue",
				strokeOpacity: 1,
				strokeWeight: 0.3},{
				
				fillColor: "blue",
				fillOpacity: 0.26,
				map: map,
				radius:400,
				strokeColor: "blue",
				strokeOpacity: 1,
				strokeWeight: 0.3
				}];


				map.setZoom(1);

				var beirut=new google.maps.LatLng(33.88894,35.49442);
				map.setCenter(beirut);
				
				// Binding Circles to the markers
				
						const circle = new google.maps.Circle(CircleOptions[0]);
						const circle2 = new google.maps.Circle(CircleOptions[1]);

						///Animation of the circles
			    	      var direction = 1;

			    	      setInterval(function() {
			    	          var radius = circle2.getRadius();
			    	          var radius2 = circle.getRadius();
			    	        
			    	          if ((radius > 300) || (radius < 150)) {
			    	              direction *= -1;
			    	          }
			    	          circle2.setRadius(radius + direction *100);
			    	          circle.setRadius(radius2 + direction * 200);
			    	      },800);

				
				for(i=0;i<suplistSites.length;i++){

					markerId=suplistSites[i][2];
					var infowindow = new google.maps.InfoWindow();
	
					var latSite=suplistSites[i][3];
					var lngSite=suplistSites[i][4];
					const pos = new google.maps.LatLng(latSite,lngSite);
	
					var siteName=suplistSites[i][1];				         

						
					var SiteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
					
					var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+suplistSites[i][5];
					var listCellsCount=suplistSites[i][6]+suplistSites[i][7]+suplistSites[i][8];
					listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+listCellsCount;
					var data="<div>"+SiteName+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";
					const marker = new google.maps.Marker({
					        position: pos,
					        data:data,
					        icon:icon,
					        ID:markerId
					         	    });
					 	marker.metadata = { id: markerId };
					    markers[markerId] = marker;
						markers.push(marker);

						
						var IdSelectedTemp="";
						
						google.maps.event.addListener(marker, "click", function (e) {
					         
					     	infowindow.setContent(this.data); 
				        	infowindow.open(map,this);
					     	map.setZoom(15);
					     	map.setCenter(pos);
					     	
					     	
					     	
					     							     	
						     	
						     	
						     	var IdSelected=this.ID;
								
								console.log("selected Site ID is >>> "+IdSelected );

								
						
								if(IdSelected!=IdSelectedTemp){

									   $("ul").find("li").removeClass("selected-span");

				                       var childrenInitial=$("#initial_ul").find(' > ul > li');

		 						       var children = $("#"+IdSelected).find(' > ul > li');

					                   if(IdSelectedTemp!=""){
					 						  	//markers[IdSelectedTemp].setIcon(icon);
					 							
						                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');
		
							 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
		
							 				  	childrenTemp.hide('fast');
							 				 	$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder');;
							 						     
							 					//markers[IdSelected].setIcon(icon2);
							 					$("#"+IdSelected).addClass("selected-span");
						 						       
						 						     
							 					$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
							                    $('#initial_ul >li>span>i').removeClass('fa-folder').addClass('fa-folder-open');
							                    //$('#initial_ul').addClass("selected-span");
							                    children.show('fast');
		
						 						childrenInitial.show('fast');
							                    IdSelectedTemp=IdSelected;
					                          }

				                       else{
				   							 

				   						    	// $('#initial_ul').addClass("selected-span");
				   						    	// markers[IdSelected].setIcon(icon2);

					                         	childrenInitial.show('fast');
					 						 	children.show('fast');

								   			 	$("#"+IdSelected).addClass("selected-span");

							                 	// $('.tree li.parent_li > .tree-span').parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
							                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
					                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

						                      	}		

							        	 IdSelectedTemp="";
				                         IdSelectedTemp=IdSelected;
										}
					     	
					     	
					     	
					     });

						var circle1 = new google.maps.Circle({
					        center: pos,
					        radius: 400, // meters
					        strokeColor: "#0000FF",
					        strokeOpacity: 0.8,
					        strokeWeight: 0.5,
					        fillColor: "#0000FF",
					        fillOpacity: 0.26
					      });

					      circle1.setMap(map);

					    

					      var bounds = circle1.getBounds();
					      //map.fitBounds(bounds);
					      var sw = bounds.getSouthWest();
					      var ne = bounds.getNorthEast();



					      for(var k = 0; k < sup_ListSupp.length; k++) {
						    
						      //var suppInSite=listSuppSites[i][6];
						      if(sup_ListSupp[k][3]==suplistSites[i][2])
							      {
							        var ptLat = Math.random() * (ne.lat() - sw.lat()) + sw.lat();
							        var ptLng = Math.random() * (ne.lng() - sw.lng()) + sw.lng();
							        var point = new google.maps.LatLng(ptLat, ptLng);

							        if (google.maps.geometry.spherical.computeDistanceBetween(point, circle1.getCenter()) < circle1.getRadius()) {
				
										var ID=sup_ListSupp[k][0];
									    var data="<div>Supplier :  "+sup_ListSupp[k][1]+"</div>";
					console.log("llllll"+ptLat);
								          const mArker = new google.maps.Marker({
								    	    position: point,
								    	    map: map,
								    	    data:data,
								    	    icon:{
									            path: google.maps.SymbolPath.CIRCLE,
									            scale: 0
								        }
							    	    
							    	});

					          	marker.metadata = { id: ID };
					          	markersSup[ID] = mArker;
					          	markersSup.push(mArker);
					          	
					          	var infowindowSup = new google.maps.InfoWindow(); 

					          	const circle3 = new google.maps.Circle({
							        center: point,
							        radius: 30, // meters
							        strokeColor: "red",
							        strokeOpacity: 0.8,
							        strokeWeight: 2,
							        fillColor: "red",
							        fillOpacity: 0.26
							      });

							      circle3.setMap(map);
							      

						    	  google.maps.event.addListener(mArker, "click", function(evt) {
						   					    	  
					    			  infowindowSup.setContent(this.data);
					    			  infowindowSup.open(map, mArker);
					    	  });
					    	  
					        }
					      }
					    }

		}

			
		var markerCluster1 = new MarkerClusterer(map,markersSup, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

								  

			
		var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

			var markersSite="";
			$('.Site > span').on('click', function (e) {

				
					var selectedItem=$(this).parent().attr('id');
					console.log("selectedItem"+selectedItem);
					console.log("markersSite"+markersSite);
					var ware = selectedItem.substring(0, 4);
	
					if(ware=="WARE"){
						if(selectedItem!=markersSite)
							{
					 		console.log("==============///////////clicked"+selectedItem);
	
				 			var selMarker="";
						
							markerId=selectedItem;
							selMarker=markers[markerId];
							var latSitee = selMarker.getPosition().lat();
							var lngSitee = selMarker.getPosition().lng();					
							selMarker.setIcon(icon2);
							panTo(latSitee, lngSitee);
							infowindow.setContent(selMarker.data);
							infowindow.open(map,selMarker);
	
							const pos = new google.maps.LatLng(latSitee,lngSitee);
							
							
							circle.setCenter(pos);
							circle2.setCenter(pos);
							
							if(markersSite!="")
								{
			 						var otherMarkers=markers[markersSite];			
									otherMarkers.setIcon(icon);
							
								}
							markersSite="";	
							markersSite=selectedItem;
						
							map.setZoom(15);
						}
					}
				});


			$(".NodeType > span").on('click',function () {
				
				var res=$(this ).parents()
					.map(function() {
						return this.id;
						})
					.get()
					.join( "," );

				parents=res.split(",,");
				var selectedItem=parents[2];
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);
				
				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
							console.log("==============///////////clicked"+selectedItem);
				
						var selMarker="";
				
						markerId=selectedItem;				
						selMarker=markers[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						selMarker.setIcon(icon2);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);
				
				
						if(markersSite!="")
								{
								var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
				
									}
				
						markersSite="";	
						markersSite=selectedItem;
				
						map.setZoom(15);
					}
				}
			});

			$(".Supplier > span").on('click',function () {
				
				var res=$(this ).parents()
					.map(function() {
					return this.id;
						})
					.get()
					.join( "," );

					parents=res.split(",,");
					var selectedItem=parents[4];
					console.log("selectedItem"+selectedItem);
					console.log("markersSite"+markersSite);
					var ware = selectedItem.substring(0, 4);
					
					if(ware=="WARE"){
						if(selectedItem!=markersSite)
							{
								console.log("==============///////////clicked"+selectedItem);
					
							var selMarker="";
					
							markerId=selectedItem;				
							selMarker=markers[markerId];
							var latSitee = selMarker.getPosition().lat();
							var lngSitee = selMarker.getPosition().lng();					
							selMarker.setIcon(icon2);
							panTo(latSitee, lngSitee);
							infowindow.setContent(selMarker.data);
							infowindow.open(map,selMarker);
					
					
							if(markersSite!="")
									{
									var otherMarkers=markers[markersSite];			
									otherMarkers.setIcon(icon);
					
										}
					
							markersSite="";	
							markersSite=selectedItem;
					
							map.setZoom(15);
						}
					}
			});
				
			$(".Node > span").on('click',function () {
								
								var res=$(this ).parents()
									.map(function() {
							return this.id;
										})
									.get()
									.join( "," );
				
				parents=res.split(",,");
				var selectedItem=parents[6];
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);
				
				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
							console.log("==============///////////clicked"+selectedItem);
				
						var selMarker="";
				
						markerId=selectedItem;				
						selMarker=markers[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						selMarker.setIcon(icon2);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);
				
				
						if(markersSite!="")
								{
								var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
				
									}
				
						markersSite="";	
						markersSite=selectedItem;
				
						map.setZoom(15);
					}
				}
			});


			$(".Cell > span").on('click',function () {

				var res=$(this ).parents()
					.map(function() {
					return this.id;
									})
							.get()
							.join( "," );

			parents=res.split(",,");
			var selectedItem=parents[8];
			console.log("selectedItem"+selectedItem);
			console.log("markersSite"+markersSite);
			var ware = selectedItem.substring(0, 4);

			if(ware=="WARE"){
				if(selectedItem!=markersSite)
					{
						console.log("==============///////////clicked"+selectedItem);

						var selMarker="";
				
						markerId=selectedItem;				
						selMarker=markers[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						selMarker.setIcon(icon2);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);


						if(markersSite!="")
								{
								var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
				
									}

						markersSite="";	
						markersSite=selectedItem;
				
						map.setZoom(15);
					}
				}
			});
			function panTo(newLat, newLng) {
				  if (panPath.length > 0) {
				    // We are already panning...queue this up for next move
				    panQueue.push([newLat, newLng]);
				  } else {
				    // Lets compute the points we'll use
				    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
				    var curLat = map.getCenter().lat();
				    var curLng = map.getCenter().lng();
				    var dLat = (newLat - curLat)/STEPS;
				    var dLng = (newLng - curLng)/STEPS;

				    for (var i=0; i < STEPS; i++) {
				      panPath.push([curLat + dLat * i, curLng + dLng * i]);
				    }
				    panPath.push([newLat, newLng]);
				    panPath.shift();      // LAZY SYNCRONIZED LOCK
				    setTimeout(doPan, 20);
				  }
				}

			function doPan() {
				  var next = panPath.shift();
				  if (next != null) {
				    // Continue our current pan action
				    map.panTo( new google.maps.LatLng(next[0], next[1]));
				    setTimeout(doPan, 20 );
				  } else {
				    // We are finished with this pan - check if there are any queue'd up locations to pan to 
				    var queued = panQueue.shift();
				    if (queued != null) {
				      panTo(queued[0], queued[1]);
				    }
				  }
				}
			}




function CreateMap_SupStNdTypNdCell(listSupp,listSuppSites,listNodesType,listSuppNodes,listCells,map){


	var circles=[];
	var panPath = [];   // An array of points the current panning action will use
	var panQueue = [];  // An array of subsequent panTo actions to take
	var STEPS = 40; 
	var markers=[];
	var sitesMarkers=[];
	var markersSup=[];
	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
	var icon2 = {
		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		    scaledSize: new google.maps.Size(50, 50), // scaled size
		};

	map.setZoom(1);

	var beirut=new google.maps.LatLng(33.88894,35.49442);
	map.setCenter(beirut);
	let mapSup = new Map();

	console.log("list of supp sites:"+listSuppSites);

	// Circles Options
	const CircleOptions=[{
							fillColor: "blue",
							fillOpacity: 0.26,
							map: map,
							radius: 100,
							strokeColor: "blue",
							strokeOpacity: 1,
							strokeWeight: 0.3},{
							
							fillColor: "blue",
							fillOpacity: 0.26,
							map: map,
							radius:400,
							strokeColor: "blue",
							strokeOpacity: 1,
							strokeWeight: 0.3
							}];


			for(var k = 0; k < listSupp.length; k++) {
				var SuppId=listSupp[k][0];
				window[''+SuppId] =new Array();
				var markerClusterSup = new MarkerClusterer();
		
				
			}
			
			
			// Binding Circles to the markers
	
			const circle = new google.maps.Circle(CircleOptions[0]);
			const circle2 = new google.maps.Circle(CircleOptions[1]);

			///Animation of the circles
    	    var direction = 1;

    	      setInterval(function() {
    	          var radius = circle2.getRadius();
    	          var radius2 = circle.getRadius();
    	        
    	          if ((radius > 300) || (radius < 150)) {
    	              direction *= -1;
    	          }
    	          circle2.setRadius(radius + direction *100);
    	          circle.setRadius(radius2 + direction * 200);
    	      },800);
	

 for(i=0;i<listSuppSites.length;i++){
	
	//window[''+listSuppSites[i][4]].push(listSuppSites[i][1]);
	
	if(!sitesMarkers.includes(listSuppSites[i][1]))
		{
		sitesMarkers.push(listSuppSites[i][1]);
	
				
		markerId=listSuppSites[i][1];
		var infowindow = new google.maps.InfoWindow();
	
		var latSite=listSuppSites[i][2];
		var lngSite=listSuppSites[i][3];
		const pos = new google.maps.LatLng(latSite,lngSite);
	
		var siteName=listSuppSites[i][0];				         
		
			
		var siteID="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
		var SiteName="";
		var CellCount=listSuppSites[i][6]+listSuppSites[i][7]+listSuppSites[i][8];
		var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+listSuppSites[i][5];
		var listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+CellCount;
		var data="<div>"+siteID+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";

		const marker = new google.maps.Marker({
		        position: pos,
		        data:data,
		        icon:icon,
		        map:map,
		        ID:listSuppSites[i][1]+"_"+listSuppSites[i][4]
		         	    });
		 	marker.metadata = { id: markerId };
		    markers[markerId] = marker;
			markers.push(marker);


			var IdSelectedTemp="";
			var IdSelectedSuppTemp="";
			google.maps.event.addListener(marker, "click", function (e) {
		         //Wrap the content inside an HTML DIV in order to set height and width of InfoWindow.
		     	//infowindow.setContent("<div>"+siteID+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>");
		     	//infowindow.open(map,selMarker);
		     	infowindow.setContent(this.data); 
	        	infowindow.open(map,this);
		     	map.setZoom(15);
		     	map.setCenter(pos);
		     	
		     	
		     	
		     	var IdSelected=this.ID;

				//var SupplierIdSel=$("#"+IdSelected).parent().parent().parent().parent().attr('id');

		    	var SupplierIdSel=$("#"+IdSelected).parents().map(function() {
		    																	return this.id;
		    																}) .get(3);


		    	var SiteFolderSel=SupplierIdSel+"_f";
				
				console.log("selected Site ID is >>> "+IdSelected );//ID:>> ware_Supp In tree
				console.log("selected Supplier ID is >>> "+SupplierIdSel );//ID:>> Supp_ In tree

				
		
				if(IdSelected!=IdSelectedTemp){

					
					   $("ul").find("li").removeClass("selected-span");

                       var childrenInitial=$("#initial_ul").find(' > ul > li');
					   var childrenSupp=$("#"+SupplierIdSel).find(' > ul > li');
					   var childrenSiteFolder=$("#"+SiteFolderSel).find(' > ul > li');
					   var children = $("#"+IdSelected).find(' > ul > li');

	                   if(IdSelectedTemp!=""){

	                	   if(SupplierIdSel!=IdSelectedSuppTemp)
		                	   {
		                	   
	                		   	var childrenSuppTemp=$("#"+IdSelectedSuppTemp).find(' > ul > li');
	                		   	childrenSuppTemp.hide('fast');
			 				  	$("#"+IdSelectedSuppTemp).removeClass("selected-span");		                		  
	                		   	$("#"+IdSelectedSuppTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder'); 

			                	   }
	 						  	//markers[IdSelectedTemp].setIcon(icon);
	 							
		                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');

			 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
			 				  	$("#"+IdSelected).addClass("selected-span");
			 				  	
			 					
		 						$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder'); 
		 						$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		 						
		 						$("#"+SupplierIdSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			                 	$("#"+SiteFolderSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');			                            
	                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

			                    childrenTemp.hide('fast');
			                    
			                    children.show('fast');
		 						childrenInitial.show('fast');
		 						childrenSupp.show('fast');
								childrenSiteFolder.show('fast');

			                    
	                          }

                       else{
   							 

   						    	
								childrenSupp.show('fast');
								childrenSiteFolder.show('fast');
								
	                         	childrenInitial.show('fast');
	 						 	children.show('fast');

				   			 	$("#"+IdSelected).addClass("selected-span");
				   			 	
				   				
			                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			                 	$("#"+SupplierIdSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			                 	$("#"+SiteFolderSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');			                            
	                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

		                      	}		

			        	 IdSelectedTemp="";
                         IdSelectedTemp=IdSelected;
                         IdSelectedSuppTemp=SupplierIdSel;
						}
		     	
		     	
		     	
		     	
		     	
		     });

			var circle1 = new google.maps.Circle({
		        center: pos,
		        radius: 400, // meters
		        strokeColor: "#0000FF",
		        strokeOpacity: 0.8,
		        strokeWeight: 0.5,
		        fillColor: "#0000FF",
		        fillOpacity: 0.26
		      });

		      circle1.setMap(map);
  
		}


	 var bounds = circle1.getBounds();
     
     var sw = bounds.getSouthWest();
     var ne = bounds.getNorthEast();
	
     for(var k = 0; k < listSupp.length; k++) {
	    
	     
	      if(listSupp[k][0]==listSuppSites[i][4])
		      {
			      var ptLat = Math.random() * (ne.lat() - sw.lat()) + sw.lat();
			      var ptLng = Math.random() * (ne.lng() - sw.lng()) + sw.lng();
			      const point = new google.maps.LatLng(ptLat, ptLng);

			      if (google.maps.geometry.spherical.computeDistanceBetween(point, circle1.getCenter()) < circle1.getRadius()) {
			
						var ID=listSupp[k][0];
					    var data="<div>Supplier :  "+listSupp[k][1]+"</div>";
				
				        const mArker = new google.maps.Marker({
				  	    position: point,				  	  	
				  	    data:data,
				  	    ID:ID,
				  	  	icon://"https://img.icons8.com/color/48/000000/c-programming.png"
						  	  	{
					            path: google.maps.SymbolPath.CIRCLE,
					            scale: 0
					        }
				  	    });
			
			        	mArker.metadata = { id: ID };
			        	markersSup[ID] = mArker;
			        	markersSup.push(mArker);
						
						var circleSup = new google.maps.Circle({
						        //center: point,
						        ID:"C"+listSupp[k][0],
						        radius: 40, // meters
						        strokeColor: "red",
						        strokeOpacity: 0.8,
						        strokeWeight: 2,
						        fillColor: "red",
						        //map:map,
						        //ID:"C_"+markersSup[i].ID,
						        fillOpacity: 0.26
						      });
							
						circles.push(circleSup);
			
						window[''+listSupp[k][0]].push(mArker);
						
			        	var infowindowSup = new google.maps.InfoWindow(); 
			
					      
			  	  		google.maps.event.addListener(mArker, "click", function(evt) {				        
					    //map.setZoom(15);
					    map.setCenter(point);	  
					  	infowindowSup.setContent(this.data);
					  	infowindowSup.open(map, mArker);
					  });
					  	 			  	  
			      }
		 	}
  		}

	}

 var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});


var markersSite="";
var markersSupplier="";

$(".Supplier > span").on('click',function () {
	//var markerClusterSup = new MarkerClusterer(map);
	
    var tempClusterArray=[];
	var selectedSupp=$(this).parent().attr('id');
	
	for(i=0,j=0;i<markersSup.length,j<circles.length;i++,j++){


		
		// && circles[i].ID=="C"+selectedSupp
		if(markersSup[i].ID==""+selectedSupp){
			
				//tempClusterArray.push(markersSup[i]);
				console.log("they are same: "+selectedSupp+"|  |"+markersSupplier+"|  ");
				//var selSupMarker="";
				console.log("circles of selected sup: "+circles.length);
				//selSupMarker=markersSup[i];
				//selSupMarker.setIcon("https://img.icons8.com/color/48/000000/c-programming.png");
				//infowindowSup.setContent(markersSup[ID].data);
				//infowindowSup.open(map,selSupMarker);
				markersSup[i].setMap(map);
				var point=markersSup[i].position;
			
				circles[i].setMap(map);
				//
				circles[i].setCenter(point);
				
		}
		
		else{
			//var otherSup;
			//otherSup=markersSup[i];
			markersSup[i].getDraggable();
			console.log("markersSup in else stat:");
			markersSup[i].setMap(null);

			circles[i].setMap(null);
			//
			circles[i].setCenter(null);
			
			
				}
		
			}
	
		if (markerClusterSup!=null) {
			markerClusterSup.clearMarkers();					
			console.log("clusterer available and not null");					
	}

		
	 	tempClusterArray=window[""+selectedSupp];
	 	
	 	markerClusterSup.setMap(map);
	 	markerClusterSup.addMarkers(tempClusterArray);
	 	
		console.log("clusterer available and not null"+markerClusterSup.getTotalClusters()+ " having this array of markers==>"+markerClusterSup.getMarkers().length);


		map.setZoom(4);
	
});

$('.Site > span').on('click', function (e) {

var res=$(this ).parents()
.map(function() {
return this.id;
})
.get()
.join( "_" );

parents=res.split("_SUP");
var selectedItem=parents[0];
console.log("selectedItem"+selectedItem);
console.log("markersSite"+markersSite);
var ware = selectedItem.substring(0, 4);

if(ware=="WARE"){
if(selectedItem!=markersSite)
	{
		console.log("==============///////////clicked"+selectedItem);

	var selMarker="";

	markerId=selectedItem;				
	selMarker=markers[markerId];
	var latSitee = selMarker.getPosition().lat();
	var lngSitee = selMarker.getPosition().lng();					
	selMarker.setIcon(icon2);
	
	panTo(latSitee, lngSitee);
	infowindow.setContent(selMarker.data);
	infowindow.open(map,selMarker);

	const pos = new google.maps.LatLng(latSitee,lngSitee);
	
	
	circle.setCenter(pos);
	circle2.setCenter(pos);

	if(markersSite!="")
			{
			var otherMarkers=markers[markersSite];			
			otherMarkers.setIcon(icon);

				}

	markersSite="";	
	markersSite=selectedItem;

	map.setZoom(15);
}
}
});



$(".NodeType > span").on('click',function () {
	


	var res=$(this ).parents()
					.map(function() {
					return this.id;
						})
					.get()
					.join( "_" );

	parents=res.split("_");
	var selectedItem=parents[1]+"_"+parents[2]+"_"+parents[3];
	console.log("selectedItem"+selectedItem);
	console.log("markersSite"+markersSite);
	var ware = selectedItem.substring(0, 4);
	
	if(ware=="WARE"){
		if(selectedItem!=markersSite)
			{
				console.log("==============///////////clicked"+selectedItem);
	
			var selMarker="";
	
			markerId=selectedItem;				
			selMarker=markers[markerId];
			var latSitee = selMarker.getPosition().lat();
			var lngSitee = selMarker.getPosition().lng();					
			selMarker.setIcon(icon2);
			panTo(latSitee, lngSitee);
			infowindow.setContent(selMarker.data);
			infowindow.open(map,selMarker);
	
	
			if(markersSite!="")
					{
					var otherMarkers=markers[markersSite];			
					otherMarkers.setIcon(icon);
	
						}
	
			markersSite="";	
			markersSite=selectedItem;
	
			map.setZoom(15);
		}
	}
});




$(".Cell > span").on('click',function () {
	


	var res=$(this ).parents()
	.map(function() {
		return this.id;
		})
	.get()
	.join( "_" );
	

	parents=res.split("_");
	var selectedItem=parents[14]+"_"+parents[15]+"_"+parents[16];
	console.log("selectedItem"+selectedItem+"|");
	console.log("markersSite"+markersSite+"|");
	var ware = selectedItem.substring(0, 4);
	
	//if(ware=="WARE"){
		if(selectedItem!=markersSite)
			{
				console.log("passed cell's if statement "+selectedItem);
	
			var selMarker="";
	
			markerId=selectedItem;				
			selMarker=markers[markerId];
			var latSitee = selMarker.getPosition().lat();
			var lngSitee = selMarker.getPosition().lng();					
			selMarker.setIcon(icon2);
			panTo(latSitee, lngSitee);
			infowindow.setContent(selMarker.data);
			infowindow.open(map,selMarker);
	
	
			if(markersSite!="")
					{
					var otherMarkers=markers[markersSite];			
					otherMarkers.setIcon(icon);
	
						}
	
			markersSite="";	
			markersSite=selectedItem;
	
			map.setZoom(15);
		}
	//}
});


$(".Node > span").on('click',function () {
	

/*

var res=$(this ).parents()
	.map(function() {
	return this.id;
		})
	.get()
	.join( "_" );
	console.log("resss"+res);
parents=res.split("__");
var selected=parents[3].split("_"+parents[5]);
var selectedItem=selected.slice(0, -1);
console.log("selectedItem"+selectedItem);
console.log("markersSite"+markersSite);

*/


			var res=$(this ).parents()
			.map(function() {
			return this.id;
				})
			.get()
			.join( "_" );
			
			parents=res.split("__");
			var selected=parents[3].split("_"+parents[5]);
			var selectedItem=selected.slice(0, -1);
			console.log("selectedItem"+selectedItem+"|");
			console.log("markersSite"+markersSite);
			//var ware = selectedItem.substring(0, 4);
			
			//if(ware=="WARE"){
			if(selectedItem!=markersSite)
			{
				
			console.log("passed the if >>>....."+selectedItem);
			
			var selMarker="";
			
			markerId=selectedItem;				
			selMarker=markers[markerId];
			var latSitee = selMarker.getPosition().lat();
			var lngSitee = selMarker.getPosition().lng();					
			selMarker.setIcon(icon2);
			panTo(latSitee, lngSitee);
			infowindow.setContent(selMarker.data);
			infowindow.open(map,selMarker);
			
			
			if(selMarker!=markers[markersSite] && markersSite!="" )
			{
			var otherMarkers=markers[markersSite];			
			otherMarkers.setIcon(icon);
			
				}
			
			markersSite="";	
			markersSite=selectedItem;
			
			map.setZoom(15);
			}
			//}
				
});	


	function panTo(newLat, newLng) {
		  if (panPath.length > 0) {
		    // We are already panning...queue this up for next move
		    panQueue.push([newLat, newLng]);
		  } else {
		    // Lets compute the points we'll use
		    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
		    var curLat = map.getCenter().lat();
		    var curLng = map.getCenter().lng();
		    var dLat = (newLat - curLat)/STEPS;
		    var dLng = (newLng - curLng)/STEPS;

		    for (var i=0; i < STEPS; i++) {
		      panPath.push([curLat + dLat * i, curLng + dLng * i]);
		    }
		    panPath.push([newLat, newLng]);
		    panPath.shift();      // LAZY SYNCRONIZED LOCK
		    setTimeout(doPan, 20);
		  }
		}

	function doPan() {
		  var next = panPath.shift();
		  if (next != null) {
		    // Continue our current pan action
		    map.panTo( new google.maps.LatLng(next[0], next[1]));
		    setTimeout(doPan, 20 );
		  } else {
		    // We are finished with this pan - check if there are any queue'd up locations to pan to 
		    var queued = panQueue.shift();
		    if (queued != null) {
		      panTo(queued[0], queued[1]);
		    }
		  }
		}
		
}




function CreateMap_StSupNdCell(listSuppSites,SuppList,listSuppNodes,listCells,map){

	var panPath = [];   // An array of points the current panning action will use
	var panQueue = [];  // An array of subsequent panTo actions to take
	var STEPS = 70; 
	
	var markers=[];
	var markersSup=[];
	
	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
	var icon2 = {
		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		    scaledSize: new google.maps.Size(50, 50), // scaled size

		};
	map.setZoom(1);

	var beirut=new google.maps.LatLng(33.88894,35.49442);
	map.setCenter(beirut);
	
	// Circles Options
	const CircleOptions=[{
	fillColor: "blue",
	fillOpacity: 0.26,
	map: map,
	radius: 100,
	strokeColor: "blue",
	strokeOpacity: 1,
	strokeWeight: 0.3},{
	
	fillColor: "blue",
	fillOpacity: 0.26,
	map: map,
	radius:400,
	strokeColor: "blue",
	strokeOpacity: 1,
	strokeWeight: 0.3
	}];
	
	// Binding Circles to the markers
	
			const circle = new google.maps.Circle(CircleOptions[0]);
			const circle2 = new google.maps.Circle(CircleOptions[1]);

			///Animation of the circles
    	      var direction = 1;

    	      setInterval(function() {
    	          var radius = circle2.getRadius();
    	          var radius2 = circle.getRadius();
    	        
    	          if ((radius > 300) || (radius < 150)) {
    	              direction *= -1;
    	          }
    	          circle2.setRadius(radius + direction *100);
    	          circle.setRadius(radius2 + direction * 200);
    	      },800);
	
	//console.log("list of supp sites:"+listSuppSites);
	for(var k = 0; k < SuppList.length; k++) {
		var SuppId=SuppList[k][1];
		window[''+SuppId] =new Array();
		
	}
	
	for(i=0;i<listSuppSites.length;i++){
		

		markerId=listSuppSites[i][2];
		var infowindow = new google.maps.InfoWindow();
	
		var latSite=listSuppSites[i][3];
		var lngSite=listSuppSites[i][4];
		const pos = new google.maps.LatLng(latSite,lngSite);
	
		var siteName=listSuppSites[i][1];				         
	
			
		var SiteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
		
 
		var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+listSuppSites[i][5];
		var listCellsCount=listSuppSites[i][6]+listSuppSites[i][7]+listSuppSites[i][8];
		listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+listCellsCount;

		var data="<div>"+SiteName+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";

		const marker = new google.maps.Marker({
		        position: pos,
		        data:data,
		        icon:icon,
		        ID:markerId
		         	    });
		 	marker.metadata = { id: markerId };
		    markers[markerId] = marker;
			markers.push(marker);

		
			var IdSelectedTemp="";		
			google.maps.event.addListener(marker, "click", function (e) {
		         //Wrap the content inside an HTML DIV in order to set height and width of InfoWindow.
		     	
		     	infowindow.setContent(this.data); 
	        	infowindow.open(map,this);
		     	map.setZoom(15);
		     	map.setCenter(pos);
		     	
		     	
		     	
		     						     	
						     	
						     	var IdSelected=this.ID;
								
								console.log("selected Site ID is >>> "+IdSelected );

								
						
								if(IdSelected!=IdSelectedTemp){

									   $("ul").find("li").removeClass("selected-span");

				                       var childrenInitial=$("#initial_ul").find(' > ul > li');

		 						       var children = $("#"+IdSelected).find(' > ul > li');

					                   if(IdSelectedTemp!=""){
					 						  	//markers[IdSelectedTemp].setIcon(icon);
					 							
						                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');
		
							 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
		
							 				  	childrenTemp.hide('fast');
							 				 	$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder');;
							 						     
							 					//markers[IdSelected].setIcon(icon2);
							 					$("#"+IdSelected).addClass("selected-span");
						 						       
						 						     
							 					$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
							                    $('#initial_ul >li>span>i').removeClass('fa-folder').addClass('fa-folder-open');
							                    //$('#initial_ul').addClass("selected-span");
							                    children.show('fast');
		
						 						childrenInitial.show('fast');
							                    IdSelectedTemp=IdSelected;
					                          }

				                       else{
				   							 

				   						    	// $('#initial_ul').addClass("selected-span");
				   						    	// markers[IdSelected].setIcon(icon2);

					                         	childrenInitial.show('fast');
					 						 	children.show('fast');

								   			 	$("#"+IdSelected).addClass("selected-span");

							                 	// $('.tree li.parent_li > .tree-span').parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
							                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
					                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

						                      	}		

							        	 IdSelectedTemp="";
				                         IdSelectedTemp=IdSelected;
										}
						     	
						     	
						     	
						     	
		     	
		     	
		     	
		     });


			var circle1 = new google.maps.Circle({
			        center: pos,
			        radius: 400, // meters
			        strokeColor: "#0000FF",
			        strokeOpacity: 0.8,
			        strokeWeight: 0.5,
			        fillColor: "#0000FF",
			        fillOpacity: 0.26
			      });

			      circle1.setMap(map);

			    

			      var bounds = circle1.getBounds();
			      //map.fitBounds(bounds);
			      var sw = bounds.getSouthWest();
			      var ne = bounds.getNorthEast();



			      for(var k = 0; k < SuppList.length; k++) {
				    
				      //var suppInSite=listSuppSites[i][6];
				      if(SuppList[k][0]==listSuppSites[i][2])
					      {
					        var ptLat = Math.random() * (ne.lat() - sw.lat()) + sw.lat();
					        var ptLng = Math.random() * (ne.lng() - sw.lng()) + sw.lng();
					        var point = new google.maps.LatLng(ptLat, ptLng);

					        if (google.maps.geometry.spherical.computeDistanceBetween(point, circle1.getCenter()) < circle1.getRadius()) {
		
								var ID=SuppList[k][1];
							    var data="<div>Supplier :  "+SuppList[k][2]+"</div>";
			
						          const mArker = new google.maps.Marker({
						    	    position: point,
						    	    map: map,
						    	    data:data,
						    	    icon:{
							            path: google.maps.SymbolPath.CIRCLE,
							            scale: 0
						        }
					    	    
					    	});

			          	marker.metadata = { id: ID };
			          	markersSup[ID] = mArker;
			          	markersSup.push(mArker);
			          	
			          	var infowindowSup = new google.maps.InfoWindow(); 

			          	const circle3 = new google.maps.Circle({
					        center: point,
					        radius: 30, // meters
					        strokeColor: "red",
					        strokeOpacity: 0.8,
					        strokeWeight: 2,
					        fillColor: "red",
					        fillOpacity: 0.26
					      });

					      circle3.setMap(map);
					      

				    	  google.maps.event.addListener(mArker, "click", function(evt) {
				   					    	  
			    			  infowindowSup.setContent(this.data);
			    			  infowindowSup.open(map, mArker);
			    	  });
			    	  
			        }
			      }
			    }

}

	
var markerCluster1 = new MarkerClusterer(map,markersSup, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

console.log("Supplier >> list >>> Size"+SuppList.length);

var markersSite="";
var markersSupplier="";
//map.setZoom(8);




$('.Site > span').on('click', function (e) {

	
	
		var selectedItem=$(this).parent().attr('id');
		//console.log("selectedItem"+selectedItem);
		//console.log("markersSite"+markersSite);
		var ware = selectedItem.substring(0, 4);
	
		if(ware=="WARE"){
			if(selectedItem!=markersSite)
				{
		 		//console.log("==============///////////clicked"+selectedItem);
	
	 			var selMarker="";
			
				markerId=selectedItem;
				
				selMarker=markers[markerId];
				var latSitee = selMarker.getPosition().lat();
				var lngSitee = selMarker.getPosition().lng();					
				selMarker.setIcon(icon2);
				panTo(latSitee, lngSitee);
				infowindow.setContent(selMarker.data);
				infowindow.open(map,selMarker);
	
				const pos = new google.maps.LatLng(latSitee,lngSitee);
				
				
				circle.setCenter(pos);
				circle2.setCenter(pos);
				
				if(markersSite!="")
					{
						var otherMarkers=markers[markersSite];			
						otherMarkers.setIcon(icon);
				
					}
				markersSite="";	
				markersSite=selectedItem;
			
				map.setZoom(15);
			}
		}
	});


	$(".Supplier > span").on('click',function () {
		
			var res=$(this ).parents()
			.map(function() {
				return this.id;
				})
			.get()
			.join( "," );
		
			parents=res.split(",,");
			var selectedItem=parents[2];

			console.log("selectedItem"+selectedItem);
			console.log("markersSite"+markersSite);

			var ware = selectedItem.substring(0, 4);
			var selectedSupp=$(this).parent().attr('id');

			selectedSupp=selectedSupp.split(parents[2]+"_");
			selectedSupp=selectedSupp.slice( 1 );
			
			console.log("selectedSupp"+selectedSupp);

	
				if(selectedItem!=markersSite)
					{
					console.log("==============///////////clicked"+selectedItem);
			
					var selMarker="";

					markerId=selectedItem;				

					selMarker=markers[markerId];

					var latSitee = selMarker.getPosition().lat();
					var lngSitee = selMarker.getPosition().lng();					
					selMarker.setIcon(icon2);
					
					
					infowindow.setContent(selMarker.data);
					infowindow.open(map,selMarker);

					panTo(latSitee, lngSitee);
					map.setZoom(15);
				

				
				markersSite="";
				markersSite=selectedItem;
				
				}

			
			
		});
	
	
$(".Node > span").on('click',function () {
	
			var res=$(this ).parents()
			.map(function() {
				return this.id;
				})
			.get()
			.join( "," );
		
			parents=res.split(",,");
			var selectedItem=parents[4];
			console.log("selectedItem"+selectedItem);
			console.log("markersSite"+markersSite);
			var ware = selectedItem.substring(0, 4);
			
			if(ware=="WARE"){
				if(selectedItem!=markersSite)
				{
					console.log("==============///////////clicked"+selectedItem);
		
					var selMarker="";
			
					markerId=selectedItem;				
					selMarker=markers[markerId];
					var latSitee = selMarker.getPosition().lat();
					var lngSitee = selMarker.getPosition().lng();					
					selMarker.setIcon(icon2);
					panTo(latSitee, lngSitee);
					infowindow.setContent(selMarker.data);
					infowindow.open(map,selMarker);
			
			
					if(markersSite!="")
							{
							var otherMarkers=markers[markersSite];			
							otherMarkers.setIcon(icon);
			
								}
			
					markersSite="";	
					markersSite=selectedItem;
			
					map.setZoom(15);
				}
			}
	});


$(".Cell > span").on('click',function () {

			var res=$(this ).parents()
			.map(function() {
				return this.id;
				})
			.get()
			.join( "," );
		
		parents=res.split(",,");
		var selectedItem=parents[6];
		console.log("selectedItem"+selectedItem);
		console.log("markersSite"+markersSite);
		var ware = selectedItem.substring(0, 4);
		
		if(ware=="WARE"){
				if(selectedItem!=markersSite)
					{
					console.log("==============///////////clicked"+selectedItem);
		
					var selMarker="";
			
					markerId=selectedItem;				
					selMarker=markers[markerId];
					var latSitee = selMarker.getPosition().lat();
					var lngSitee = selMarker.getPosition().lng();					
					selMarker.setIcon(icon2);
					panTo(latSitee, lngSitee);
					infowindow.setContent(selMarker.data);
					infowindow.open(map,selMarker);
		
		
					if(markersSite!="")
							{
							var otherMarkers=markers[markersSite];			
							otherMarkers.setIcon(icon);
			
								}
			
					markersSite="";	
					markersSite=selectedItem;
			
					map.setZoom(15);
				}
			}
		});

function panTo(newLat, newLng) {
	  if (panPath.length > 0) {
	    // We are already panning...queue this up for next move
	    panQueue.push([newLat, newLng]);
	  } else {
	    // Lets compute the points we'll use
	    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
	    var curLat = map.getCenter().lat();
	    var curLng = map.getCenter().lng();
	    var dLat = (newLat - curLat)/STEPS;
	    var dLng = (newLng - curLng)/STEPS;

	    for (var i=0; i < STEPS; i++) {
	      panPath.push([curLat + dLat * i, curLng + dLng * i]);
	    }
	    panPath.push([newLat, newLng]);
	    panPath.shift();      // LAZY SYNCRONIZED LOCK
	    setTimeout(doPan, 20);
	  }
	}

function doPan() {
	  var next = panPath.shift();
	  if (next != null) {
	    // Continue our current pan action
	    map.panTo( new google.maps.LatLng(next[0], next[1]));
	    setTimeout(doPan, 20 );
	  } else {
	    // We are finished with this pan - check if there are any queue'd up locations to pan to 
	    var queued = panQueue.shift();
	    if (queued != null) {
	      panTo(queued[0], queued[1]);
	    }
	  }
	}

  }





function CreateMap_StSupNdTpNdCell(listSuppSites,SuppList,nodeTypeStSuppResult,listSuppNodes,listCells,map){

	var panPath = [];   // An array of points the current panning action will use
	var panQueue = [];  // An array of subsequent panTo actions to take
	var STEPS = 70; 
	
	var markers=[];
	var markersSup=[];
	
	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
	var icon2 = {
		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		    scaledSize: new google.maps.Size(50, 50), // scaled size

		};
	map.setZoom(1);

	var beirut=new google.maps.LatLng(33.88894,35.49442);
	map.setCenter(beirut);
	
	// Circles Options
	const CircleOptions=[{
	fillColor: "blue",
	fillOpacity: 0.26,
	map: map,
	radius: 100,
	strokeColor: "blue",
	strokeOpacity: 1,
	strokeWeight: 0.3},{
	
	fillColor: "blue",
	fillOpacity: 0.26,
	map: map,
	radius:400,
	strokeColor: "blue",
	strokeOpacity: 1,
	strokeWeight: 0.3
	}];
	
	// Binding Circles to the markers
	
			const circle = new google.maps.Circle(CircleOptions[0]);
			const circle2 = new google.maps.Circle(CircleOptions[1]);

			///Animation of the circles
    	      var direction = 1;

    	      setInterval(function() {
    	          var radius = circle2.getRadius();
    	          var radius2 = circle.getRadius();
    	        
    	          if ((radius > 300) || (radius < 150)) {
    	              direction *= -1;
    	          }
    	          circle2.setRadius(radius + direction *100);
    	          circle.setRadius(radius2 + direction * 200);
    	      },800);
	
	//console.log("list of supp sites:"+listSuppSites);
	for(var k = 0; k < SuppList.length; k++) {
		var SuppId=SuppList[k][1];
		window[''+SuppId] =new Array();
		
	}
	
	for(i=0;i<listSuppSites.length;i++){
		

		markerId=listSuppSites[i][2];
		var infowindow = new google.maps.InfoWindow();
	
		var latSite=listSuppSites[i][3];
		var lngSite=listSuppSites[i][4];
		const pos = new google.maps.LatLng(latSite,lngSite);
	
		var siteName=listSuppSites[i][1];				         
	
			
		var SiteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
		
 
		var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+listSuppSites[i][5];
		var listCellsCount=listSuppSites[i][6]+listSuppSites[i][7]+listSuppSites[i][8];
		listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+listCellsCount;

		var data="<div>"+SiteName+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";

		const marker = new google.maps.Marker({
		        position: pos,
		        data:data,
		        icon:icon,
		        ID:markerId
		         	    });
		 	marker.metadata = { id: markerId };
		    markers[markerId] = marker;
			markers.push(marker);


			var IdSelectedTemp="";
			google.maps.event.addListener(marker, "click", function (e) {
		         //Wrap the content inside an HTML DIV in order to set height and width of InfoWindow.
		     	
		     	infowindow.setContent(this.data); 
	        	infowindow.open(map,this);
		     	map.setZoom(15);
		     	map.setCenter(pos);
		     	
		     	var IdSelected=this.ID;
								
				console.log("selected Site ID is >>> "+IdSelected );
		     	
		     	if(IdSelected!=IdSelectedTemp){

									   $("ul").find("li").removeClass("selected-span");

				                       var childrenInitial=$("#initial_ul").find(' > ul > li');

		 						       var children = $("#"+IdSelected).find(' > ul > li');

					                   if(IdSelectedTemp!=""){
					 						  	//markers[IdSelectedTemp].setIcon(icon);
					 							
						                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');
		
							 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
		
							 				  	childrenTemp.hide('fast');
							 				 	$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder');;
							 						     
							 					//markers[IdSelected].setIcon(icon2);
							 					$("#"+IdSelected).addClass("selected-span");
						 						       
						 						     
							 					$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
							                    $('#initial_ul >li>span>i').removeClass('fa-folder').addClass('fa-folder-open');
							                    //$('#initial_ul').addClass("selected-span");
							                    children.show('fast');
		
						 						childrenInitial.show('fast');
							                    IdSelectedTemp=IdSelected;
					                          }

				                       else{
				   							 

				   						    	// $('#initial_ul').addClass("selected-span");
				   						    	// markers[IdSelected].setIcon(icon2);

					                         	childrenInitial.show('fast');
					 						 	children.show('fast');

								   			 	$("#"+IdSelected).addClass("selected-span");

							                 	// $('.tree li.parent_li > .tree-span').parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
							                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
							                            
					                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

						                      	}		

							        	 IdSelectedTemp="";
				                         IdSelectedTemp=IdSelected;
										}
						     	
						     	
						     	
						     	
		     	
		     	
		     	
		     	
		     });


			var circle1 = new google.maps.Circle({
			        center: pos,
			        radius: 400, // meters
			        strokeColor: "#0000FF",
			        strokeOpacity: 0.8,
			        strokeWeight: 0.5,
			        fillColor: "#0000FF",
			        fillOpacity: 0.26
			      });

			      circle1.setMap(map);

			    

			      var bounds = circle1.getBounds();
			      //map.fitBounds(bounds);
			      var sw = bounds.getSouthWest();
			      var ne = bounds.getNorthEast();



			      for(var k = 0; k < SuppList.length; k++) {
				    
				      //var suppInSite=listSuppSites[i][6];
				      if(SuppList[k][0]==listSuppSites[i][2])
					      {
					        var ptLat = Math.random() * (ne.lat() - sw.lat()) + sw.lat();
					        var ptLng = Math.random() * (ne.lng() - sw.lng()) + sw.lng();
					        var point = new google.maps.LatLng(ptLat, ptLng);

					        if (google.maps.geometry.spherical.computeDistanceBetween(point, circle1.getCenter()) < circle1.getRadius()) {
		
								var ID=SuppList[k][1];
							    var data="<div>Supplier :  "+SuppList[k][2]+"</div>";
			
						          const mArker = new google.maps.Marker({
						    	    position: point,
						    	    map: map,
						    	    data:data,
						    	    icon:{
							            path: google.maps.SymbolPath.CIRCLE,
							            scale: 0
						        }
					    	    
					    	});

			          	marker.metadata = { id: ID };
			          	markersSup[ID] = mArker;
			          	markersSup.push(mArker);
			          	
			          	var infowindowSup = new google.maps.InfoWindow(); 

			          	const circle3 = new google.maps.Circle({
					        center: point,
					        radius: 30, // meters
					        strokeColor: "red",
					        strokeOpacity: 0.8,
					        strokeWeight: 2,
					        fillColor: "red",
					        fillOpacity: 0.26
					      });

					      circle3.setMap(map);
					      

				    	  google.maps.event.addListener(mArker, "click", function(evt) {
				   					    	  
			    			  infowindowSup.setContent(this.data);
			    			  infowindowSup.open(map, mArker);
			    	  });
			    	  
			        }
			      }
			    }

}
				for(var k = 0; k < SuppList.length; k++) {
					var SuppArr=window[''+SuppId];
							
				}
	
var markerCluster1 = new MarkerClusterer(map,markersSup, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

console.log("Supplier >> list >>> Size"+SuppList.length);

var markersSite="";
var markersSupplier="";
//map.setZoom(8);




$('.Site > span').on('click', function (e) {

	
	
		var selectedItem=$(this).parent().attr('id');
		console.log("selectedItem"+selectedItem);
		console.log("markersSite"+markersSite);
		var ware = selectedItem.substring(0, 4);
	
		if(ware=="WARE"){
			if(selectedItem!=markersSite)
				{
		 		console.log("==============///////////clicked"+selectedItem);
	
	 			var selMarker="";
			
				markerId=selectedItem;
				selMarker=markers[markerId];
				var latSitee = selMarker.getPosition().lat();
				var lngSitee = selMarker.getPosition().lng();					
				selMarker.setIcon(icon2);
				panTo(latSitee, lngSitee);
				infowindow.setContent(selMarker.data);
				infowindow.open(map,selMarker);
	
				const pos = new google.maps.LatLng(latSitee,lngSitee);
				
				
				circle.setCenter(pos);
				circle2.setCenter(pos);
				
				if(markersSite!="")
					{
							var otherMarkers=markers[markersSite];			
						otherMarkers.setIcon(icon);
				
					}
				markersSite="";	
				markersSite=selectedItem;
			
				map.setZoom(15);
			}
		}
	});


$(".Supplier > span").on('click',function () {
		
		var res=$(this ).parents()
		.map(function() {
			return this.id;
			})
		.get()
		.join( "," );
	
		parents=res.split(",,");
		var selectedItem=parents[2];

		console.log("selectedItem"+selectedItem);
		console.log("markersSite"+markersSite);

		var ware = selectedItem.substring(0, 4);
		var selectedSupp=$(this).parent().attr('id');

		selectedSupp=selectedSupp.split(parents[2]+"_");
		selectedSupp=selectedSupp.slice( 1 );
		
		console.log("selectedSupp"+selectedSupp);


			if(selectedItem!=markersSite)
				{
				console.log("==============///////////clicked"+selectedItem);
		
				var selMarker="";

				markerId=selectedItem;				

				selMarker=markers[markerId];

				var latSitee = selMarker.getPosition().lat();
				var lngSitee = selMarker.getPosition().lng();					
				selMarker.setIcon(icon2);
				
				
				infowindow.setContent(selMarker.data);
				infowindow.open(map,selMarker);

				panTo(latSitee, lngSitee);
				map.setZoom(15);
			

			
			markersSite="";
			markersSite=selectedItem;
			
			}

		
		
	});
	
	$(".NodeType > span").on('click',function () {
	
			var res=$(this ).parents()
			.map(function() {
				return this.id;
				})
			.get()
			.join( "," );
			
			parents=res.split(",,");
			var selectedItem=parents[4];
			console.log("selectedItem"+selectedItem);
			console.log("markersSite"+markersSite);
			var ware = selectedItem.substring(0, 4);
		
			if(ware=="WARE"){
				if(selectedItem!=markersSite)
					{
						console.log("==============///////////clicked"+selectedItem);
			
					var selMarker="";
			
					markerId=selectedItem;				
					selMarker=markers[markerId];
					var latSitee = selMarker.getPosition().lat();
					var lngSitee = selMarker.getPosition().lng();					
					selMarker.setIcon(icon2);
					panTo(latSitee, lngSitee);
					infowindow.setContent(selMarker.data);
					infowindow.open(map,selMarker);
			
			
					if(markersSite!="")
							{
							var otherMarkers=markers[markersSite];			
							otherMarkers.setIcon(icon);
			
								}
			
					markersSite="";	
					markersSite=selectedItem;
			
					map.setZoom(15);
				}
			}
	});


$(".Node > span").on('click',function () {
	
			var res=$(this ).parents()
			.map(function() {
				return this.id;
				})
			.get()
			.join( "," );
		
			parents=res.split(",,");
			var selectedItem=parents[6];
			console.log("selectedItem"+selectedItem);
			console.log("markersSite"+markersSite);
			var ware = selectedItem.substring(0, 4);
			
			if(ware=="WARE"){
				if(selectedItem!=markersSite)
				{
					console.log("==============///////////clicked"+selectedItem);
		
					var selMarker="";
			
					markerId=selectedItem;				
					selMarker=markers[markerId];
					var latSitee = selMarker.getPosition().lat();
					var lngSitee = selMarker.getPosition().lng();					
					selMarker.setIcon(icon2);
					panTo(latSitee, lngSitee);
					infowindow.setContent(selMarker.data);
					infowindow.open(map,selMarker);
			
			
					if(markersSite!="")
							{
							var otherMarkers=markers[markersSite];			
							otherMarkers.setIcon(icon);
			
								}
			
					markersSite="";	
					markersSite=selectedItem;
			
					map.setZoom(15);
				}
			}
	});


$(".Cell > span").on('click',function () {

			var res=$(this ).parents()
			.map(function() {
				return this.id;
				})
			.get()
			.join( "," );
		
		parents=res.split(",,");
		var selectedItem=parents[8];
		console.log("selectedItem"+selectedItem);
		console.log("markersSite"+markersSite);
		var ware = selectedItem.substring(0, 4);
		
		if(ware=="WARE"){
				if(selectedItem!=markersSite)
					{
					console.log("==============///////////clicked"+selectedItem);
		
					var selMarker="";
			
					markerId=selectedItem;				
					selMarker=markers[markerId];
					var latSitee = selMarker.getPosition().lat();
					var lngSitee = selMarker.getPosition().lng();					
					selMarker.setIcon(icon2);
					panTo(latSitee, lngSitee);
					infowindow.setContent(selMarker.data);
					infowindow.open(map,selMarker);
		
		
					if(markersSite!="")
							{
							var otherMarkers=markers[markersSite];			
							otherMarkers.setIcon(icon);
			
								}
			
					markersSite="";	
					markersSite=selectedItem;
			
					map.setZoom(15);
				}
			}
		});

function panTo(newLat, newLng) {
	  if (panPath.length > 0) {
	    // We are already panning...queue this up for next move
	    panQueue.push([newLat, newLng]);
	  } else {
	    // Lets compute the points we'll use
	    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
	    var curLat = map.getCenter().lat();
	    var curLng = map.getCenter().lng();
	    var dLat = (newLat - curLat)/STEPS;
	    var dLng = (newLng - curLng)/STEPS;

	    for (var i=0; i < STEPS; i++) {
	      panPath.push([curLat + dLat * i, curLng + dLng * i]);
	    }
	    panPath.push([newLat, newLng]);
	    panPath.shift();      // LAZY SYNCRONIZED LOCK
	    setTimeout(doPan, 20);
	  }
	}

function doPan() {
	  var next = panPath.shift();
	  if (next != null) {
	    // Continue our current pan action
	    map.panTo( new google.maps.LatLng(next[0], next[1]));
	    setTimeout(doPan, 20 );
	  } else {
	    // We are finished with this pan - check if there are any queue'd up locations to pan to 
	    var queued = panQueue.shift();
	    if (queued != null) {
	      panTo(queued[0], queued[1]);
	    }
	  }
	}

  }




function CreateMap_AreaSite(areaList,listAreaSites,map){

		   	console.log(areaList);   
		   	var panPath = [];   // An array of points the current panning action will use
		   	var panQueue = [];  // An array of subsequent panTo actions to take
		   	var STEPS = 40;
		   	var markers=[];
		   	var markersArea=[];
		   	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
		   	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
		   	var icon2 = {
		   		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		   		    scaledSize: new google.maps.Size(50, 50), // scaled size

		   		};




		   	
		   	

		   	map.setZoom(4);
		   	var beirut=new google.maps.LatLng(36,32);
		   	map.setCenter(beirut);
		   	
		   	for(i=0;i<areaList.length;i++){

		   			var areaOfSitesArray=areaList[i][0];
		   			console.log("+areaList[i][0] "+areaList[i][0])
		   			window[""+areaList[i][0]] =new Array();
		   		
		   			markerAreaId=areaList[i][0];
		   			var infowindow = new google.maps.InfoWindow();

		   			var latArea=areaList[i][2];
		   			var lngArea=areaList[i][3];
		   			console.log(latArea);
		   			console.log(lngArea);
		   			
		   			const posArea = new google.maps.LatLng(latArea,lngArea);

		   			var areaName=areaList[i][1];				         					
		   				
		   			areaName="<b style='font-size:15px;align:center;'>"+areaName+"</b>  ";
		   			
		   	 

		   			var data="<div>"+areaName+"</div>";
		   			const markerArea = new google.maps.Marker({
		   			        position: posArea,
		   			        data:data,
		   			        ID:markerAreaId,
		   			        map:map,
		   			        icon:"https://img.icons8.com/ultraviolet/40/000000/quest.png"
		   			         	    });
		   				markerArea.metadata = { id: markerAreaId };
		   			 	markersArea[markerAreaId] = markerArea;
		   			 	markersArea.push(markerArea);
		   				
					var IdSelectedTemp="";
		   			google.maps.event.addListener(markerArea, "click", function (e) {

		   			     	infowindow.setContent(this.data); 
		   		        	infowindow.open(map,this);
		   			     	map.setZoom(10);
		   			     	map.setCenter(posArea);



		   			 	var IdSelected=this.ID;
						
						console.log("selected Area ID is >>> "+IdSelected );
				     	
				     	if(IdSelected!=IdSelectedTemp){

											   $("ul").find("li").removeClass("selected-span");

						                       var childrenInitial=$("#initial_ul").find(' > ul > li');

				 						       var children = $("#"+IdSelected).find(' > ul > li');

							                   if(IdSelectedTemp!=""){
							 						  	//markers[IdSelectedTemp].setIcon(icon);
							 							
								                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');
				
									 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
				
									 				  	childrenTemp.hide('fast');
									 				 	$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder');;
									 						     
									 					//markers[IdSelected].setIcon(icon2);
									 					$("#"+IdSelected).addClass("selected-span");
								 						       
								 						     
									 					$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
									                            
									                    $('#initial_ul >li>span>i').removeClass('fa-folder').addClass('fa-folder-open');
									                    //$('#initial_ul').addClass("selected-span");
									                    children.show('fast');
				
								 						childrenInitial.show('fast');
									                    IdSelectedTemp=IdSelected;
							                          }

						                       else{
						   							 

						   						    	// $('#initial_ul').addClass("selected-span");
						   						    	// markers[IdSelected].setIcon(icon2);

							                         	childrenInitial.show('fast');
							 						 	children.show('fast');

										   			 	$("#"+IdSelected).addClass("selected-span");

									                 	// $('.tree li.parent_li > .tree-span').parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
									                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
									                            
							                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

								                      	}		

									        	 IdSelectedTemp="";
						                         IdSelectedTemp=IdSelected;
												}


		   			     	
		   			     });
		   	  
		   			var markerCluster = new MarkerClusterer(map,window[""+areaList[i][0]], {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

		   	}


		   	

		   	for(i=0;i<listAreaSites.length;i++){

		   			markerId=listAreaSites[i][1];
		   			var infowindow = new google.maps.InfoWindow();
		   	
		   			var latSite=listAreaSites[i][2];
		   			var lngSite=listAreaSites[i][3];
		   			const pos = new google.maps.LatLng(latSite,lngSite);
		   	
		   			var siteName=listAreaSites[i][0];				         
		   		


		   					
		   				
		   			var siteName="<b style='font-size:13px;'>"+siteName+"</b>";
		   			
		   	 

		   	 

		   			
		   			var data="<div>"+siteName+"</div>";
		   			const marker = new google.maps.Marker({
		   			        position: pos,
		   			        data:data,
		   			        
		   			        ID:markerId
		   			         	    });
		   			 	marker.metadata = { id: markerId };
		   			    markers[markerId] = marker;
		   				markers.push(marker);

		   				console.log("+listAreaSites[i][4]|"+listAreaSites.length+"| "+listAreaSites[i][4]);

		   				var areaId=listAreaSites[i][4];
		   				
		   				window[""+listAreaSites[i][4]].push(marker);


		   				var IdSelectedTemp="";
		   				var IdSelectedAreaTemp="";
		   				google.maps.event.addListener(marker, "click", function (e) {

		   			     	infowindow.setContent(this.data); 
		   		        	infowindow.open(map,this);
		   			     	map.setZoom(15);
		   			     	map.setCenter(pos);


		   			     var IdSelected=this.ID;


		 		    	var AreaIdSel=$("#"+IdSelected).parents().map(function() {
		 		    																	return this.id;
		 		    																}) .get(3);


		 		    	var SiteFolderSel=AreaIdSel+"_f";
		 				
		 				console.log("selected Site ID is >>> "+IdSelected );
		 				console.log("selected Supplier ID is >>> "+AreaIdSel );

		 		
		 				if(IdSelected!=IdSelectedTemp){


		 					
		 					   $("ul").find("li").removeClass("selected-span");

		                        var childrenInitial=$("#initial_ul").find(' > ul > li');
		 					   var childrenArea=$("#"+AreaIdSel).find(' > ul > li');
		 					   var childrenSiteFolder=$("#"+SiteFolderSel).find(' > ul > li');
		 					   var children = $("#"+IdSelected).find(' > ul > li');

		 	                   if(IdSelectedTemp!=""){

		 	                	   if(AreaIdSel!=IdSelectedAreaTemp)
		 		                	   {
		 		                	   
		 	                		   	var childrenAreaTemp=$("#"+IdSelectedAreaTemp).find(' > ul > li');
		 	                		   	childrenAreaTemp.hide('fast');
		 			 				  	$("#"+IdSelectedAreaTemp).removeClass("selected-span");		                		  
		 	                		   	$("#"+IdSelectedAreaTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder'); 

		 			                	   }
		 	 						  	//markers[IdSelectedTemp].setIcon(icon);
		 	 							
		 		                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');

		 			 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
		 			 				  	$("#"+IdSelected).addClass("selected-span");
		 			 				  	
		 			 					
		 		 						$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder'); 
		 		 						$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		 		 						
		 		 						$("#"+AreaIdSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		 			                 	$("#"+SiteFolderSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');			                            
		 	                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

		 			                    childrenTemp.hide('fast');
		 			                    
		 			                    children.show('fast');
		 		 						childrenInitial.show('fast');
		 		 						childrenArea.show('fast');
		 								childrenSiteFolder.show('fast');

		 			                    
		 	                          }

		                        else{
		    							 

		    						    	
		 								childrenArea.show('fast');
		 								childrenSiteFolder.show('fast');
		 								
		 	                         	childrenInitial.show('fast');
		 	 						 	children.show('fast');

		 				   			 	$("#"+IdSelected).addClass("selected-span");
		 				   			 	
		 				   				
		 			                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		 			                 	$("#"+AreaIdSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		 			                 	$("#"+SiteFolderSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');			                            
		 	                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

		 		                      	}		

		 			        	  IdSelectedTemp="";
		                          IdSelectedTemp=IdSelected;
		                          IdSelectedAreaTemp=AreaIdSel;
		 						}
		 		     	


		   			     	
		   			     });
		   	  

		   	}
		  
		   	

		   	var markerCluster = new MarkerClusterer(map,markersArea, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});


		   var markersAreaTemp="";
		   $('.Area > span').on('click', function (e) {

		   	
		   	
		   	var selectedItem=$(this).parent().attr('id');
		   	
		   	
		   	

		   	
		   		if(selectedItem!=markersAreaTemp)
		   			{
		   	 		console.log("====selectedItem"+selectedItem);
		   	 		console.log("markersAreaTemp"+markersAreaTemp);
		   	 		selMarker=markersArea[selectedItem];
		   			var latSitee = selMarker.getPosition().lat();
		   			var lngSitee = selMarker.getPosition().lng();					
		   			selMarker.setIcon("https://img.icons8.com/dusk/64/000000/waypoint-map.png");
		   			selMarker.setMap(map);
		   			panTo(latSitee, lngSitee);
		   			for(j=0;j<window[""+selectedItem].length;j++)
		   				{

		   					var siteId=window[""+selectedItem][j].ID;
		   					markers[siteId].setMap(map);
		   					markers[siteId].setIcon(icon2);
		   				
		   				}


		   			if(markersAreaTemp!="")
		   			{
		   				console.log("markersAreaTemp	inside the other marker if"+markersAreaTemp);
		   				//markersArea[markersAreaTemp].setMap(null);			
		   				markersArea[markersAreaTemp].setIcon("https://img.icons8.com/ultraviolet/40/000000/quest.png");
		   				for(j=0;j<window[""+markersAreaTemp].length;j++)
		   				{

		   					var otherSiteId=window[""+markersAreaTemp][j].ID;
		   					markers[otherSiteId].setMap(null);
		   					
		   				
		   				}
		   				
		   			}
		   			
		   				
		   			markersAreaTemp=selectedItem;
		   			
		   			map.setZoom(10);
		   			}
		   	 	
		   });

		   var markersSiteTemp="";
		   $('.Site > span').on('click', function (e) {


		   	var res=$(this ).parents()
		   	.map(function() {
		   		return this.id;
		   		})
		   	.get()
		   	.join( "," );

		   	parents=res.split(",,");
		   	var siteId=$(this).parent().attr('id');
		   	var selectedItem=parents[2];

		   	var selSiteMarker=markers[siteId];
		   	
		   	
		   	if(selectedItem!=markersAreaTemp)
		   		{


		   			
		   			
		   				
		    		selMarker=markersArea[selectedItem];
		   		//var latArea = selMarker.getPosition().lat();
		   		//var lngArea = selMarker.getPosition().lng();					
		   		selMarker.setIcon("https://img.icons8.com/dusk/64/000000/waypoint-map.png");
		   		selMarker.setMap(map);
		   		//panTo(latArea, lngArea);
		   		for(j=0;j<window[""+selectedItem].length;j++)
		   			{

		   				var siteId=window[""+selectedItem][j].ID;
		   				markers[siteId].setMap(map);
		   				markers[siteId].setIcon(icon2);
		   			
		   			}


		   		if(markersAreaTemp!="")
		   		{
		   			console.log("markersAreaTemp	inside the other marker if"+markersAreaTemp);
		   			//markersArea[markersAreaTemp].setMap(null);			
		   			markersArea[markersAreaTemp].setIcon("https://img.icons8.com/ultraviolet/40/000000/quest.png");
		   			for(j=0;j<window[""+markersAreaTemp].length;j++)
		   			{

		   				var otherSiteId=window[""+markersAreaTemp][j].ID;
		   				markers[otherSiteId].setMap(null);
		   				
		   			
		   			}
		   			
		   		}
		   		
		   			
		   		markersAreaTemp=selectedItem;
		   		map.setZoom(10);
		   		}
		   	if(siteId!=markersSiteTemp)
		   		{
		   		var latSitee = selSiteMarker.getPosition().lat();
		   		var lngSitee = selSiteMarker.getPosition().lng();
		   		panTo(latSitee, lngSitee);
		   		markersSiteTemp=siteId;
		   		}
		   });

		   function panTo(newLat, newLng) {
		   		  if (panPath.length > 0) {
		   		    // We are already panning...queue this up for next move
		   		    panQueue.push([newLat, newLng]);
		   		  } else {
		   		    // Lets compute the points we'll use
		   		    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
		   		    var curLat = map.getCenter().lat();
		   		    var curLng = map.getCenter().lng();
		   		    var dLat = (newLat - curLat)/STEPS;
		   		    var dLng = (newLng - curLng)/STEPS;

		   		    for (var i=0; i < STEPS; i++) {
		   		      panPath.push([curLat + dLat * i, curLng + dLng * i]);
		   		    }
		   		    panPath.push([newLat, newLng]);
		   		    panPath.shift();      // LAZY SYNCRONIZED LOCK
		   		    setTimeout(doPan, 20);
		   		  }
		   		}

		   	function doPan() {
		   		  var next = panPath.shift();
		   		  if (next != null) {
		   		    // Continue our current pan action
		   		    map.panTo( new google.maps.LatLng(next[0], next[1]));
		   		    setTimeout(doPan, 20 );
		   		  } else {
		   		    // We are finished with this pan - check if there are any queue'd up locations to pan to 
		   		    var queued = panQueue.shift();
		   		    if (queued != null) {
		   		      panTo(queued[0], queued[1]);
		   		    }
		   		  }
		   		}
		   	
		   }

	   

function CreateMap_SupStNdCell(listSupp,listSuppSites,listSuppNodes,listCells,map){

	circles=[];
	panPath = [];   // An array of points the current panning action will use
	panQueue = [];  // An array of subsequent panTo actions to take
	STEPS = 40; 
	markers=[];
	sitesMarkers=[];
	markersSup=[];
	icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
	icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
	icon2 = {
		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		    scaledSize: new google.maps.Size(50, 50), // scaled size
		};

	map.setZoom(1);

	var beirut=new google.maps.LatLng(33.88894,35.49442);
	map.setCenter(beirut);
	let mapSup = new Map();

	console.log("list of supp sitess:"+listSuppSites);

	// Circles Options
	const CircleOptions=[{
							fillColor: "blue",
							fillOpacity: 0.26,
							map: map,
							radius: 100,
							strokeColor: "blue",
							strokeOpacity: 1,
							strokeWeight: 0.3},{
							
							fillColor: "blue",
							fillOpacity: 0.26,
							map: map,
							radius:400,
							strokeColor: "blue",
							strokeOpacity: 1,
							strokeWeight: 0.3
							}];


			for(var k = 0; k <listSupp.length; k++) {
				SuppId=listSupp[k][0];
				window[''+SuppId] =new Array();
				markerClusterSup = new MarkerClusterer();
		
				
			}
			
			
			// Binding Circles to the markers
	
			circle = new google.maps.Circle(CircleOptions[0]);
			circle2 = new google.maps.Circle(CircleOptions[1]);

			///Animation of the circles
    	    var direction = 1;

    	      setInterval(function() {
    	          var radius = circle2.getRadius();
    	          var radius2 = circle.getRadius();
    	        
    	          if ((radius > 300) || (radius < 150)) {
    	              direction *= -1;
    	          }
    	          circle2.setRadius(radius + direction *100);
    	          circle.setRadius(radius2 + direction * 200);
    	      },800);
	

 for(i=0;i<listSuppSites.length;i++){
	
	
	if(!sitesMarkers.includes(listSuppSites[i][1]))
		{
		sitesMarkers.push(listSuppSites[i][1]);
	
				
		markerId=listSuppSites[i][1];
		var infowindow = new google.maps.InfoWindow();
	
		var latSite=listSuppSites[i][2];
		var lngSite=listSuppSites[i][3];
		const pos = new google.maps.LatLng(latSite,lngSite);
	
		var siteName=listSuppSites[i][0];				         
		
			
		var siteID="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
		var SiteName="";
		var CellCount=listSuppSites[i][6]+listSuppSites[i][7]+listSuppSites[i][8];
		var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+listSuppSites[i][5];
		var listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+CellCount;
		var data="<div>"+siteID+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";

		const marker = new google.maps.Marker({
		        position: pos,
		        data:data,
		        icon:icon,
		        map:map,
		        ID:listSuppSites[i][1]+"_"+listSuppSites[i][4]
		         	    });
		 	marker.metadata = { id: markerId };
		    markers[markerId] = marker;
			markers.push(marker);

			var IdSelectedTemp="";
			var IdSelectedSuppTemp="";
			google.maps.event.addListener(marker, "click", function (e) {
		         //Wrap the content inside an HTML DIV in order to set height and width of InfoWindow.
		     	//infowindow.setContent("<div>"+siteID+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>");
		     	//infowindow.open(map,selMarker);
		     	infowindow.setContent(this.data); 
	        	infowindow.open(map,this);
		     	map.setZoom(15);
		     	map.setCenter(pos);


		     	var IdSelected=this.ID;


		    	var SupplierIdSel=$("#"+IdSelected).parents().map(function() {
		    																	return this.id;
		    																}) .get(3);


		    	var SiteFolderSel=SupplierIdSel+"_f";
				
				console.log("selected Site ID is >>> "+IdSelected );//ID:>> ware_Supp In tree
				console.log("selected Supplier ID is >>> "+SupplierIdSel );//ID:>> Supp_ In tree

		
				if(IdSelected!=IdSelectedTemp){


					
					   $("ul").find("li").removeClass("selected-span");

                       var childrenInitial=$("#initial_ul").find(' > ul > li');
					   var childrenSupp=$("#"+SupplierIdSel).find(' > ul > li');
					   var childrenSiteFolder=$("#"+SiteFolderSel).find(' > ul > li');
					   var children = $("#"+IdSelected).find(' > ul > li');

	                   if(IdSelectedTemp!=""){

	                	   if(SupplierIdSel!=IdSelectedSuppTemp)
		                	   {
		                	   
	                		   	var childrenSuppTemp=$("#"+IdSelectedSuppTemp).find(' > ul > li');
	                		   	childrenSuppTemp.hide('fast');
			 				  	$("#"+IdSelectedSuppTemp).removeClass("selected-span");		                		  
	                		   	$("#"+IdSelectedSuppTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder'); 

			                	   }
	 						  	//markers[IdSelectedTemp].setIcon(icon);
	 							
		                      	var childrenTemp = $("#"+IdSelectedTemp).find(' > ul > li');

			 				  	$("#"+IdSelectedTemp).removeClass("selected-span");
			 				  	$("#"+IdSelected).addClass("selected-span");
			 				  	
			 					
		 						$("#"+IdSelectedTemp+" > .folder>svg").removeClass('fa-folder-open').addClass('fa fa-folder'); 
		 						$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
		 						
		 						$("#"+SupplierIdSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			                 	$("#"+SiteFolderSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');			                            
	                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

			                    childrenTemp.hide('fast');
			                    
			                    children.show('fast');
		 						childrenInitial.show('fast');
		 						childrenSupp.show('fast');
								childrenSiteFolder.show('fast');

			                    
	                          }

                       else{
   							 

   						    	
								childrenSupp.show('fast');
								childrenSiteFolder.show('fast');
								
	                         	childrenInitial.show('fast');
	 						 	children.show('fast');

				   			 	$("#"+IdSelected).addClass("selected-span");
				   			 	
				   				
			                 	$("#"+IdSelected+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			                 	$("#"+SupplierIdSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			                 	$("#"+SiteFolderSel+" > .folder>svg").removeClass('fa fa-folder').addClass('fa-folder-open');			                            
	                         	$('#initial_ul > .folder>svg').removeClass('fa fa-folder').addClass('fa-folder-open');

		                      	}		

			        	 IdSelectedTemp="";
                         IdSelectedTemp=IdSelected;
                         IdSelectedSuppTemp=SupplierIdSel;
						}
		     	

		     	
		     });

			circle1 = new google.maps.Circle({
		        center: pos,
		        radius: 400, // meters
		        strokeColor: "#0000FF",
		        strokeOpacity: 0.8,
		        strokeWeight: 0.5,
		        fillColor: "#0000FF",
		        fillOpacity: 0.26
		      });

		      circle1.setMap(map);
  
		}


	 var bounds = circle1.getBounds();
     
     var sw = bounds.getSouthWest();
     var ne = bounds.getNorthEast();
	
     for(var k = 0; k < listSupp.length; k++) {
	    
	     
	      if(listSupp[k][0]==listSuppSites[i][4])
		      {
			      var ptLat = Math.random() * (ne.lat() - sw.lat()) + sw.lat();
			      var ptLng = Math.random() * (ne.lng() - sw.lng()) + sw.lng();
			      const point = new google.maps.LatLng(ptLat, ptLng);

			      if (google.maps.geometry.spherical.computeDistanceBetween(point, circle1.getCenter()) < circle1.getRadius()) {
			
						var ID=listSupp[k][0];
					    var data="<div>Supplier :  "+listSupp[k][1]+"</div>";
				
				        const mArker = new google.maps.Marker({
				  	    position: point,				  	  	
				  	    data:data,
				  	    ID:ID,
				  	  	icon://"https://img.icons8.com/color/48/000000/c-programming.png"
						  	  	{
					            path: google.maps.SymbolPath.CIRCLE,
					            scale: 0
					        }
				  	    });
			
			        	mArker.metadata = { id: ID };
			        	markersSup[ID] = mArker;
			        	markersSup.push(mArker);
						
						var circleSup = new google.maps.Circle({
						        //center: point,
						        ID:"C"+listSupp[k][0],
						        radius: 40, // meters
						        strokeColor: "red",
						        strokeOpacity: 0.8,
						        strokeWeight: 2,
						        fillColor: "red",
						        //map:map,
						        //ID:"C_"+markersSup[i].ID,
						        fillOpacity: 0.26
						      });
							
						circles.push(circleSup);
			
						window[''+listSupp[k][0]].push(mArker);
						
			        	var infowindowSup = new google.maps.InfoWindow(); 
			
					      
			  	  		google.maps.event.addListener(mArker, "click", function(evt) {				        
					    //map.setZoom(15);
					    map.setCenter(point);	  
					  	infowindowSup.setContent(this.data);
					  	infowindowSup.open(map, mArker);
					  });
					  	 			  	  
			      }
		 	}
  		}

	}

 var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});


markersSite="";
markersSupplier="";




	function panTo(newLat, newLng) {
		  if (panPath.length > 0) {
		    // We are already panning...queue this up for next move
		    panQueue.push([newLat, newLng]);
		  } else {
		    // Lets compute the points we'll use
		    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
		    var curLat = map.getCenter().lat();
		    var curLng = map.getCenter().lng();
		    var dLat = (newLat - curLat)/STEPS;
		    var dLng = (newLng - curLng)/STEPS;

		    for (var i=0; i < STEPS; i++) {
		      panPath.push([curLat + dLat * i, curLng + dLng * i]);
		    }
		    panPath.push([newLat, newLng]);
		    panPath.shift();      // LAZY SYNCRONIZED LOCK
		    setTimeout(doPan, 20);
		  }
		}

	function doPan() {
		  var next = panPath.shift();
		  if (next != null) {
		    // Continue our current pan action
		    map.panTo( new google.maps.LatLng(next[0], next[1]));
		    setTimeout(doPan, 20 );
		  } else {
		    // We are finished with this pan - check if there are any queue'd up locations to pan to 
		    var queued = panQueue.shift();
		    if (queued != null) {
		      panTo(queued[0], queued[1]);
		    }
		  }
		}
		
}

		   
		   


 function CreateMap_StPOItem(listSites,listPO,listItem,map){
 
		   	var markers=[];
		   	var panPath = [];   // An array of points the current panning action will use
		   	var panQueue = [];  // An array of subsequent panTo actions to take
		   	var STEPS = 40;
		   	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
		   	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
		   	var icon2 = {
		   		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		   		    scaledSize: new google.maps.Size(50, 50), // scaled size
		   		};

		   	// Circles Options
		   	const CircleOptions=[{
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius: 100,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3},{
		   	
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius:400,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3
		   	}];
		   	
		   	map.setZoom(1);
 

    var beirut=new google.maps.LatLng(33.88894,35.49442);
    map.setCenter(beirut);
		   	
		   	
		   	// Binding Circles to the markers
		   	
		   			const circle = new google.maps.Circle(CircleOptions[0]);
		   			const circle2 = new google.maps.Circle(CircleOptions[1]);

		   			///Animation of the circles
		       	      var direction = 1;

		       	      setInterval(function() {
		       	          var radius = circle2.getRadius();
		       	          var radius2 = circle.getRadius();
		       	        
		       	          if ((radius > 300) || (radius < 150)) {
		       	              direction *= -1;
		       	          }
		       	          circle2.setRadius(radius + direction *100);
		       	          circle.setRadius(radius2 + direction * 200);
		       	      },800);

		   	console.log(listSites);
		   	console.log(listSites.length);
		   	
		   for(i=0;i<listSites.length;i++){
					markerId=listSites[i][0];
					var infowindow = new google.maps.InfoWindow();

					var latSite=listSites[i][3];
					var lngSite=listSites[i][4];
					const pos = new google.maps.LatLng(latSite,lngSite);

					var siteName=listSites[i][1];				         
					var PO=[];
					var Items=[];

			console.log(listPO[i][1] + "-" + markerId );

					for(j=0;j<listPO.length;j++){	 	
				    	 	
								if(listPO[j][1]==markerId){
								PO.push(listPO[j][0]);
								
								for(k=0;k<listItem.length;k++){

									if(listItem[k][2]==listPO[j][0]){
									
										Items.push(listItem[k][1]);
										
										}
										
									} 
								
					        	 }
						         
							} 
								
							
						var siteID="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
						var SiteName="";
				 
						console.log("siteName: "+siteName);
				    	console.log("Siteslen: "+siteName.length);
				    	console.log("PO: "+PO);
				    	console.log("POslen: "+PO.length);
				    	console.log("Items: "+Items);
				    	console.log("Itemslen: "+Items.length);

						var listPOCount="<b style='font-size:13px;'> Number of PO: </b>"+PO.length;
						var listItemsCount="<b style='font-size:13px;'>Number of Items: </b>"+Items.length;
						var data="<div>"+siteID+"</br>"+listPOCount+"</br>"+listItemsCount+"</div>";
						console.log(data);
						const marker = new google.maps.Marker({
						        position: pos,
						        data:data,
						        icon:icon
						         	    });
						 	marker.metadata = { id: markerId };
						    markers[markerId] = marker;
							markers.push(marker);
							

						google.maps.event.addListener(marker, "click", function (e) {

						     	infowindow.setContent(this.data); 
					        	infowindow.open(map,this);
						     	map.setZoom(15);
						     	map.setCenter(pos);
						     });
				  

				}
		   var markerCluster = new MarkerClusterer(map,markers, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

				var markersSite="";

			$('.Site > span').on('click', function (e) {

					
				var selectedItem=$(this).parent().attr('id');
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);

				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
				 		console.log("==============///////////clicked"+selectedItem);

			 			var selMarker="";
					
						markerId=selectedItem;
						selMarker=markers[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						selMarker.setIcon(icon2);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);

						const pos = new google.maps.LatLng(latSitee,lngSitee);
						
						
						circle.setCenter(pos);
						circle2.setCenter(pos);

						
						
						if(markersSite!="")
							{
		 						var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
						
							}
						markersSite="";	
						markersSite=selectedItem;
					
						map.setZoom(15);
					}
				}
			});

				console.log("markersSite"+markersSite);


			$(".PO > span").on('click',function () {
				
				
				var res=$(this ).parents()
				  				.map(function() {
					    			return this.id;
					  						})
					  			.get()
					  			.join( "," );

				parents=res.split(",,");
				var selectedItem=parents[2];
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);

				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
				 			console.log("==============///////////clicked"+selectedItem);

			 				var selMarker="";
					
							markerId=selectedItem;				
							selMarker=markers[markerId];
							var latSitee = selMarker.getPosition().lat();
							var lngSitee = selMarker.getPosition().lng();					
							selMarker.setIcon(icon2);
							panTo(latSitee, lngSitee);
							infowindow.setContent(selMarker.data);
							infowindow.open(map,selMarker);

					
							if(markersSite!="")
								{
		 							var otherMarkers=markers[markersSite];			
									otherMarkers.setIcon(icon);
						
								}

							markersSite="";	
							markersSite=selectedItem;
		
							map.setZoom(15);
						}
					}
				});


			$(".Item > span").on('click',function () {

				
				
				var res=$(this ).parents()
				  				.map(function() {
					    			return this.id;
					  						})
					  			.get()
					  			.join( "," );

				parents=res.split(",,");
				var selectedItem=parents[4];
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);

				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
				 			console.log("==============///////////clicked"+selectedItem);

			 				var selMarker="";
					
							markerId=selectedItem;				
							selMarker=markers[markerId];
							var latSitee = selMarker.getPosition().lat();
							var lngSitee = selMarker.getPosition().lng();					
							selMarker.setIcon(icon2);
							panTo(latSitee, lngSitee);
							infowindow.setContent(selMarker.data);
							infowindow.open(map,selMarker);

					
							if(markersSite!="")
								{
		 							var otherMarkers=markers[markersSite];			
									otherMarkers.setIcon(icon);
						
								}

							markersSite="";	
							markersSite=selectedItem;
		
							map.setZoom(15);
						}
					}
				});

		   function panTo(newLat, newLng) {
		   	  if (panPath.length > 0) {
		   	    // We are already panning...queue this up for next move
		   	    panQueue.push([newLat, newLng]);
		   	  } else {
		   	    // Lets compute the points we'll use
		   	    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
		   	    var curLat = map.getCenter().lat();
		   	    var curLng = map.getCenter().lng();
		   	    var dLat = (newLat - curLat)/STEPS;
		   	    var dLng = (newLng - curLng)/STEPS;

		   	    for (var i=0; i < STEPS; i++) {
		   	      panPath.push([curLat + dLat * i, curLng + dLng * i]);
		   	    }
		   	    panPath.push([newLat, newLng]);
		   	    panPath.shift();      // LAZY SYNCRONIZED LOCK
		   	    setTimeout(doPan, 20);
		   	  }
		   	}

		   function doPan() {
		   	  var next = panPath.shift();
		   	  if (next != null) {
		   	    // Continue our current pan action
		   	    map.panTo( new google.maps.LatLng(next[0], next[1]));
		   	    setTimeout(doPan, 20 );
		   	  } else {
		   	    // We are finished with this pan - check if there are any queue'd up locations to pan to 
		   	    var queued = panQueue.shift();
		   	    if (queued != null) {
		   	      panTo(queued[0], queued[1]);
		   	    }
		   	  }
		   	}
		   }



//PO-SITE-ITEM

function CreateMap_POStItem(listPO,listSites,itemList,map){
		   	var markers=[];
		   	var POId=[];
		   	var sitesMarkers=[];
		   	var panPath = [];   // An array of points the current panning action will use
		   	var panQueue = [];  // An array of subsequent panTo actions to take
		   	var STEPS = 40;
		   	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
		   	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
		   	var icon2 = {
		   		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		   		    scaledSize: new google.maps.Size(50, 50), // scaled size

		   		};

	
	
		   	// Circles Options
		   	const CircleOptions=[{
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius: 100,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3},{
		   	
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius:400,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3
		   	}];
		   	
		   	map.setZoom(1);
 

    var beirut=new google.maps.LatLng(33.88894,35.49442);
    map.setCenter(beirut);
		   	
		   	
		   	// Binding Circles to the markers   	
		   			const circle = new google.maps.Circle(CircleOptions[0]);
		   			const circle2 = new google.maps.Circle(CircleOptions[1]);

		   			///Animation of the circles
		       	      var direction = 1;

		       	      setInterval(function() {
		       	          var radius = circle2.getRadius();
		       	          var radius2 = circle.getRadius();
		       	        
		       	          if ((radius > 300) || (radius < 150)) {
		       	              direction *= -1;
		       	          }
		       	          circle2.setRadius(radius + direction *100);
		       	          circle.setRadius(radius2 + direction * 200);
		       	      },800);
	
	

  	for(i=0;i<listPO.length;i++){
           
          console.log("POOOOOSSS"+listPO[i]);
		   window[''+listPO[i]] =new Array();   
		   var markerClusterPO = new MarkerClusterer();		
                    }

 
		 //fill sites w.r.toPO		
				for(j=0;j<listSites.length;j++){		                     
	                    markerId=listSites[j][0];
	                    console.log("Tesssst"+listSites[j][0]);
	 					var infowindow = new google.maps.InfoWindow();
						var latSite=listSites[j][4];
						var lngSite=listSites[j][5];
						const pos = new google.maps.LatLng(latSite,lngSite);
						var Items=[];
						var siteName=listSites[j][1];			
						var SiteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;									 
						var listPOCount="<b style='font-size:13px;'> Number of POs: </b>"+listSites[j][7];
						var listItemsCount="<b style='font-size:13px;'>Number of Items: </b>"+listSites[j][8];
						var data="<div>"+SiteName+"</br>"+listPOCount+"</br>"+listItemsCount+"</div>";
						console.log(data);
						const marker = new google.maps.Marker({	
						        position: pos,
						        data:data,
						        icon:icon,
						        ID:markerId
						         	    });
						marker.metadata = { id: markerId };
						markers[markerId] = marker;
						markers.push(marker);
						window[''+listSites[j][2]].push(marker);
						
					if(!sitesMarkers.includes(listSites[j][0]))
						{ 
						sitesMarkers.push(listSites[j][0]);	
						console.log(sitesMarkers);
						const markerMain = new google.maps.Marker({	
						        position: pos,
						        data:data,
						        icon:icon,
						        ID:markerId
						         	    });
						markerMain.metadata = { id: markerId };
						markersMain[markerId] = markerMain;
						markersMain.push(markerMain);
						}
						
						google.maps.event.addListener(marker, "click", function (e) {

						     	infowindow.setContent(this.data); 
					        	infowindow.open(map,this);
						     	map.setZoom(15);
						     	map.setCenter(pos);
						     });
				  		

					}

	var markerCluster = new MarkerClusterer(map,markersMain, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
			
	var markerClusterPO=new MarkerClusterer();
	var PoSelectedTemp="";

	$(' #initial_ul > span').on('click', function (e) {
			
  			markerClusterPO.clearMarkers();	
  			markerCluster.addMarkers(markersMain);
  		  
  		});


	$('.PO > span').on('click', function (e) {

		var tempClusterArray=[];
		var selectedPo=$(this).parent().attr('id');	
		markerCluster.clearMarkers();		
		console.log(selectedPo);
				 
			if(selectedPo!=PoSelectedTemp){

				if(PoSelectedTemp!=""){

					console.log("temp is not null "+PoSelectedTemp);
					
					for(j=0;j<window[""+PoSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+PoSelectedTemp][j].ID];
	 					previousMarker.setMap(null);	 							 						
					}
				}				

				for(j=0;j<window[""+selectedPo].length;j++){  	           	
		 				var currentMarker=markers[window[""+selectedPo][j].ID];
		 				currentMarker.setMap(map); 	
		 									
					}

								
				PoSelectedTemp=selectedPo;
				tempClusterArray=window[""+selectedPo];		
				markerClusterPO.clearMarkers();
				markerClusterPO = new MarkerClusterer(map,tempClusterArray);
				markerClusterPO.addMarkers(tempClusterArray);
				
			}

			map.setZoom(5);
	});
				

	var markersSite="";			

		$(".Site > span").on('click',function () {
				var SiteId=$(this).parent().attr('id');
				
				
				var res=$(this).parents().map(function() {
					return this.id;
					}).get().join( "_" );

				parents=res.split("_PO");
				var selectedItem=parents[0];
				var sitePO=parents[1];

				var selectedPo=$("#"+SiteId).parents().map(function() {
					return this.id;
					}).get(3);
				
				console.log("selectedPo"+selectedPo);
								
				var ware = selectedItem.substring(0, 4);
				var tempClusterArray=[];
				
				if(sitePO!=selectedPo)
				{
						if(selectedItem!=markersSite)

							{
				 			console.log("==============///////////clicked"+selectedItem);

			 				var selMarker="";
					
							markerId=selectedItem;				
							selMarker=markers[markerId];
							var latSitee = selMarker.getPosition().lat();
							var lngSitee = selMarker.getPosition().lng();					
							selMarker.setIcon(icon2);
							selMarker.setMap(map);
							panTo(latSitee, lngSitee);
							infowindow.setContent(selMarker.data);
							infowindow.open(map,selMarker);

					
								const pos = new google.maps.LatLng(latSitee,lngSitee);
						
						
						circle.setCenter(pos);
						circle2.setCenter(pos);

						
						
						if(markersSite!="")
							{
		 						var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
						
							}
						markersSite="";	
						markersSite=selectedItem;
							markersSite=selectedItem;
							map.setZoom(15);
							
								}
						

						tempClusterArray=window[""+selectedPo];
					}
			else
			
				{
						if(selectedItem!=markersSite)

							{
				 			console.log("==============///////////clicked"+selectedItem);

			 				var selMarker="";
					
							markerId=selectedItem;				
							selMarker=markers[markerId];
							var latSitee = selMarker.getPosition().lat();
							var lngSitee = selMarker.getPosition().lng();					
							selMarker.setIcon(icon2);
							selMarker.setMap(map);
							panTo(latSitee, lngSitee);
							infowindow.setContent(selMarker.data);
							infowindow.open(map,selMarker);

					
							const pos = new google.maps.LatLng(latSitee,lngSitee);
						
						
						circle.setCenter(pos);
						circle2.setCenter(pos);

						
						
						if(markersSite!="")
							{
		 						var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
						
							}
						markersSite="";	
						markersSite=selectedItem;
							markersSite=selectedItem;
							map.setZoom(15);
							
								}
						

						tempClusterArray=window[""+selectedPo];
					}
											
				
				});


			$(".Item > span").on('click',function () {

				
				var res=$(this).parent().parents()
				.map(function() {
					return this.id;
					}).get(3);

				var res=$(this).parent().parents()
				.map(function() {
					return this.id;
					}).get(3);

				parents=res.split("_PO");

				var selectedItem=parents[0];

				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);

					if(selectedItem!=markersSite)
						{
				 			console.log("==============///////////clicked"+selectedItem);

			 				var selMarker="";
					
							markerId=selectedItem;				
							selMarker=markers[selectedItem];
							var latSitee = selMarker.getPosition().lat();
							var lngSitee = selMarker.getPosition().lng();					
							selMarker.setIcon(icon2);
							selMarker.setMap(map);
							panTo(latSitee, lngSitee);
							infowindow.setContent(selMarker.data);
							infowindow.open(map,selMarker);

					
							if(markersSite!="")
								{
		 							var otherMarkers=markers[markersSite];			
									otherMarkers.setIcon(icon);
									otherMarkers.setMap(null);
						
								}

							markersSite="";	
							markersSite=selectedItem;
		
							map.setZoom(15);
						}
					
				});

		   function panTo(newLat, newLng) {
		   	  if (panPath.length > 0) {
			   	    // We are already panning...queue this up for next move
			   	    panQueue.push([newLat, newLng]);
		   	  } else {
			   	    // Lets compute the points we'll use
			   	    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
			   	    var curLat = map.getCenter().lat();
			   	    var curLng = map.getCenter().lng();
			   	    var dLat = (newLat - curLat)/STEPS;
			   	    var dLng = (newLng - curLng)/STEPS;

		   	    for (var i=0; i < STEPS; i++) {
		   	     	 panPath.push([curLat + dLat * i, curLng + dLng * i]);
		   	    }
		   	    panPath.push([newLat, newLng]);
		   	    panPath.shift();      // LAZY SYNCRONIZED LOCK
		   	    setTimeout(doPan, 20);
		   	  }
		   	}

		   function doPan() {
		   	  var next = panPath.shift();
		   	  if (next != null) {
			   	    // Continue our current pan action
			   	    map.panTo( new google.maps.LatLng(next[0], next[1]));
			   	    setTimeout(doPan, 20 );
		   	  } else {
			   	    // We are finished with this pan - check if there are any queue'd up locations to pan to 
			   	    var queued = panQueue.shift();
			   	    if (queued != null) {
			   	      panTo(queued[0], queued[1]);
			   	    }
		   	  }
		   	}
		   }

		   


//PO-ITEM-SITE

function CreateMap_POItemSt(listPO,listItem,listSites,map){
          var markers=[];
          var markersMain=[];
		   	var POId=[];
		   	var sitesMarkers=[];
		   	var panPath = [];   // An array of points the current panning action will use
		   	var panQueue = [];  // An array of subsequent panTo actions to take
		   	var STEPS = 40
		   	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
		   	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
		   	var icon2 = {
		   		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		   		    scaledSize: new google.maps.Size(50, 50), // scaled size

		   		};

	
		   	// Circles Options
		   	const CircleOptions=[{
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius: 100,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3},{
		   	
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius:400,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3
		   	}];
		   	
		   	map.setZoom(1);


  var beirut=new google.maps.LatLng(33.88894,35.49442);
  map.setCenter(beirut);
		   	
		   	
		   	// Binding Circles to the markers   	
		   			const circle = new google.maps.Circle(CircleOptions[0]);
		   			const circle2 = new google.maps.Circle(CircleOptions[1]);

		   			///Animation of the circles
		       	      var direction = 1;

		       	      setInterval(function() {
		       	          var radius = circle2.getRadius();
		       	          var radius2 = circle.getRadius();
		       	        
		       	          if ((radius > 300) || (radius < 150)) {
		       	              direction *= -1;
		       	          }
		       	          circle2.setRadius(radius + direction *100);
		       	          circle.setRadius(radius2 + direction * 200);
		       	      },800);
	

	for(i=0;i<listPO.length;i++){
         
        console.log("POOOOOSSS"+listPO[i]);
		   window[''+listPO[i]] =new Array();   
		   var markerClusterPO = new MarkerClusterer();		
                  }
                  
  for(l=0;l<listItem.length;l++){
         
        console.log("ITEEEMS"+listItem[l]);
		   window[''+listItem[l][0]+"_"+listItem[l][2]] =new Array();
		   var markerClusterItem = new MarkerClusterer();
                  }

		 //Show sites on load		
				for(j=0;j<listSites.length;j++){	                     
	                    markerId=listSites[j][0];
	                    console.log("Tesssst"+listSites[j][0]);
	 					var infowindow = new google.maps.InfoWindow();
	 
						var latSite=listSites[j][5];
						var lngSite=listSites[j][6];
						const pos = new google.maps.LatLng(latSite,lngSite);
						var Items=[];
						var siteName=listSites[j][1];
			
						var SiteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
									 

						var listPOCount="<b style='font-size:13px;'> Number of POs: </b>"+listSites[j][8];
						var listItemsCount="<b style='font-size:13px;'>Number of Items: </b>"+listSites[j][9];
						var data="<div>"+SiteName+"</br>"+listPOCount+"</br>"+listItemsCount+"</div>";
						console.log(data);
						const marker = new google.maps.Marker({	
						        position: pos,
						        data:data,
						        icon:icon,
						        ID:markerId
						         	    });
						marker.metadata = { id: markerId };
						markers[markerId] = marker;
						markers.push(marker);

						window[''+listSites[j][3]].push(marker);
						window[''+listSites[j][2]+"_"+listSites[j][3]].push(marker);

						if(!sitesMarkers.includes(listSites[j][0]))
						{ 
						sitesMarkers.push(listSites[j][0]);
						console.log("sitesMarkers"+sitesMarkers);
						const markerMain = new google.maps.Marker({	
						        position: pos,
						        data:data,
						        icon:icon,
						        ID:markerId
						         	    });
						markerMain.metadata = { id: markerId };
						markersMain[markerId] = markerMain;
						markersMain.push(markerMain);
						}
						console.log(markersMain);
						google.maps.event.addListener(marker, "click", function (e) {

						     	infowindow.setContent(this.data); 
					        	infowindow.open(map,this);
						     	map.setZoom(15);
						     	map.setCenter(pos);
						     });
				  		

					}

	var markerCluster = new MarkerClusterer(map,markersMain, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
				

  var markersSite="";
  var markerClusterPO=new MarkerClusterer();
  var markerClusterItem=new MarkerClusterer();
  
	var PoSelectedTemp="";
  var ItemSelectedTemp="";

  		$(' #initial_ul > span').on('click', function (e) {

  			console.log("ksksks");
  			markerClusterPO.clearMarkers();	
  			markerClusterItem.clearMarkers();	
  			markerCluster.addMarkers(markersMain);
  		  

  		});
	   
		$('.PO > span').on('click', function (e) {

		var tempClusterArray=[];
		var selectedPo=$(this).parent().attr('id');			
		markerCluster.clearMarkers();
								 
			if(selectedPo!=PoSelectedTemp){

				if(PoSelectedTemp!=""){

					console.log("temp is not null "+PoSelectedTemp);
					
					for(j=0;j<window[""+PoSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+PoSelectedTemp][j].ID];
	 					previousMarker.setMap(null); 							 						
					}
				}				

				for(j=0;j<window[""+selectedPo].length;j++){ 	           	
		 				var currentMarker=markers[window[""+selectedPo][j].ID];
		 				currentMarker.setMap(map); 			 									
					}

				markerClusterItem.clearMarkers();								
				PoSelectedTemp=selectedPo;
				tempClusterArray=window[""+selectedPo];		
				markerClusterPO.clearMarkers();
				markerClusterPO = new MarkerClusterer(map,tempClusterArray);
				markerClusterPO.addMarkers(tempClusterArray);
				
			}

			map.setZoom(4);
	});
	
	
	$(".Item > span").on('click',function () {

      var tempClusterArray=[];
		var selectedItem=$(this).parent().attr('id');
		parents=selectedItem.split("_");
		var ItemPO=parents[1];
		//var ItemId=parents[0];				
			   
      var selectedPo=$("#"+selectedItem).parents().map(function() {
					return this.id;
					}).get(3);
		console.log(selectedItem);		
      console.log("selectedPo"+selectedPo);
      
      
      	if(selectedPo!=PoSelectedTemp){

				if(PoSelectedTemp!=""){

					console.log("temp is not null "+PoSelectedTemp);
					
					for(j=0;j<window[""+PoSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+PoSelectedTemp][j].ID];
	 					previousMarker.setMap(null); 							 						
					}
				}				

				for(j=0;j<window[""+selectedPo].length;j++){ 	           	
		 				var currentMarker=markers[window[""+selectedPo][j].ID];
		 				currentMarker.setMap(map); 			 									
					}  
					       
			if(selectedItem!=ItemSelectedTemp){
				if(ItemSelectedTemp!=""){
					console.log("temp is not null "+selectedItem);
					
					for(j=0;j<window[""+ItemSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+ItemSelectedTemp][j].ID];
	 					previousMarker.setMap(null);	 						 						
					}
				}				

				for(j=0;j<window[""+selectedItem].length;j++){   	           	
		 				var currentMarker=markers[window[""+selectedItem][j].ID];
		 				currentMarker.setMap(map); 			 									
					}
				
				markerClusterPO.clearMarkers();								
				ItemSelectedTemp=selectedItem;
				tempClusterArray=window[""+selectedItem];		
				markerClusterItem.clearMarkers();
				markerClusterItem = new MarkerClusterer(map,tempClusterArray);
				markerClusterItem.addMarkers(tempClusterArray);
				PoSelectedTemp=selectedPo;
				map.setZoom(5);
				
				}			
				}
				
			else
                {          
			if(selectedItem!=ItemSelectedTemp){
				if(ItemSelectedTemp!=""){
					console.log("temp is not null "+selectedItem);
					
					for(j=0;j<window[""+ItemSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+ItemSelectedTemp][j].ID];
	 					previousMarker.setMap(null);	 						 						
					}
				}				

				for(j=0;j<window[""+selectedItem].length;j++){   	           	
		 				var currentMarker=markers[window[""+selectedItem][j].ID];
		 				currentMarker.setMap(map); 			 									
					}
				
				markerClusterPO.clearMarkers();								
				ItemSelectedTemp=selectedItem;
				tempClusterArray=window[""+selectedItem];		
				markerClusterItem.clearMarkers();
				markerClusterItem = new MarkerClusterer(map,tempClusterArray);
				markerClusterItem.addMarkers(tempClusterArray);
				PoSelectedTemp=selectedPo;
				tempClusterArray=window[""+PoSelectedTemp];
				map.setZoom(5);
				
				
				}
				} 
              
				});
				
	
	$('.Site > span').on('click', function (e) {

				markerClusterPO.clearMarkers();								
				markerClusterItem.clearMarkers();	
				var selectedItem=$(this).parent().attr('id');
				console.log("selectedItem"+selectedItem);
				console.log("markersSite"+markersSite);
				var ware = selectedItem.substring(0, 4);
				var selectedItemPO=$("#"+selectedItem).parents().map(function() {
					return this.id;
					}).get(3);
				console.log("selectedItemPO"+selectedItemPO);

				if(ware=="WARE"){
					if(selectedItem!=markersSite)
						{
				 		console.log("==============///////////clicked"+selectedItem);

			 			var selMarker="";
					
						markerId=selectedItem;
						selMarker=markers[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						selMarker.setIcon(icon2);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);

						const pos = new google.maps.LatLng(latSitee,lngSitee);
						
						
						circle.setCenter(pos);
						circle2.setCenter(pos);

						
						
						if(markersSite!="")
							{
		 						var otherMarkers=markers[markersSite];			
								otherMarkers.setIcon(icon);
						
							}
						markersSite="";	
						markersSite=selectedItem;
					
						
					}
					map.setZoom(15);
				}
	});
				
	
function panTo(newLat, newLng) {
		   	  if (panPath.length > 0) {
		   	    // We are already panning...queue this up for next move
		   	    panQueue.push([newLat, newLng]);
		   	  } else {
		   	    // Lets compute the points we'll use
		   	    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
		   	    var curLat = map.getCenter().lat();
		   	    var curLng = map.getCenter().lng();
		   	    var dLat = (newLat - curLat)/STEPS;
		   	    var dLng = (newLng - curLng)/STEPS;

		   	    for (var i=0; i < STEPS; i++) {
		   	      panPath.push([curLat + dLat * i, curLng + dLng * i]);
		   	    }
		   	    panPath.push([newLat, newLng]);
		   	    panPath.shift();      // LAZY SYNCRONIZED LOCK
		   	    setTimeout(doPan, 20);
		   	  }
		   	}

		   function doPan() {
		   	  var next = panPath.shift();
		   	  if (next != null) {
		   	    // Continue our current pan action
		   	    map.panTo( new google.maps.LatLng(next[0], next[1]));
		   	    setTimeout(doPan, 20 );
		   	  } else {
		   	    // We are finished with this pan - check if there are any queue'd up locations to pan to 
		   	    var queued = panQueue.shift();
		   	    if (queued != null) {
		   	      panTo(queued[0], queued[1]);
		   	    }
		   	  }
		   	}	
	
		   }



//PO-ITEM-SITE-Nodetype-Node

function CreateMap_POItStNdtyNd(listPO,listItem,listSites,listNodesType,listNode,map){
      var markers=[];
      var markersMain=[];
		   	var POId=[];
		   	var sitesMarkers=[];
		   	var panPath = [];   // An array of points the current panning action will use
		   	var panQueue = [];  // An array of subsequent panTo actions to take
		   	var STEPS = 40
		   	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
		   	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
		   	var icon2 = {
		   		    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
		   		    scaledSize: new google.maps.Size(50, 50), // scaled size

		   		};

	
		   	// Circles Options
		   	const CircleOptions=[{
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius: 100,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3},{
		   	
		   	fillColor: "blue",
		   	fillOpacity: 0.26,
		   	map: map,
		   	radius:400,
		   	strokeColor: "blue",
		   	strokeOpacity: 1,
		   	strokeWeight: 0.3
		   	}];
		   	
		   	map.setZoom(1);


var beirut=new google.maps.LatLng(33.88894,35.49442);
map.setCenter(beirut);
		   	
		   	
		   	// Binding Circles to the markers   	
		   			const circle = new google.maps.Circle(CircleOptions[0]);
		   			const circle2 = new google.maps.Circle(CircleOptions[1]);

		   			///Animation of the circles
		       	      var direction = 1;

		       	      setInterval(function() {
		       	          var radius = circle2.getRadius();
		       	          var radius2 = circle.getRadius();
		       	        
		       	          if ((radius > 300) || (radius < 150)) {
		       	              direction *= -1;
		       	          }
		       	          circle2.setRadius(radius + direction *100);
		       	          circle.setRadius(radius2 + direction * 200);
		       	      },800);
	

	for(i=0;i<listPO.length;i++){
     
    console.log("POOOOOSSS"+listPO[i]);
		   window[''+listPO[i]] =new Array();   
		   var markerClusterPO = new MarkerClusterer();		
              }
              
for(l=0;l<listItem.length;l++){
     
    console.log("ITEEEMS"+listItem[l]);
		   window[''+listItem[l][0]+"_"+listItem[l][2]] =new Array();
		   var markerClusterItem = new MarkerClusterer();
              }

		 //Show sites on load		
				for(j=0;j<listSites.length;j++){	                     
	                    markerId=listSites[j][0];
	                    console.log("Tesssst"+listSites[j][0]);
	 					var infowindow = new google.maps.InfoWindow();
	 
						var latSite=listSites[j][5];
						var lngSite=listSites[j][6];
						const pos = new google.maps.LatLng(latSite,lngSite);
						var Items=[];
						var siteName=listSites[j][1];
			
						var SiteName="<b style='font-size:13px;'><u>Site: </u></b>  "+siteName;
									 

						var listPOCount="<b style='font-size:13px;'> Number of POs: </b>"+listSites[j][8];
						var listItemsCount="<b style='font-size:13px;'>Number of Items: </b>"+listSites[j][9];
                      var listNode="<b style='font-size:13px;'>Number of Items: </b>"+listSites[j][7];

						var data="<div>"+SiteName+"</br>"+listPOCount+"</br>"+listItemsCount+"</br>"+listNode+"</div>";
						console.log(data);
						const marker = new google.maps.Marker({	
						        position: pos,
						        data:data,
						        icon:icon,
						        ID:markerId
						         	    });
						marker.metadata = { id: markerId };
						markers[markerId] = marker;
						markers.push(marker);

						window[''+listSites[j][3]].push(marker);
						window[''+listSites[j][2]+"_"+listSites[j][3]].push(marker);

						if(!sitesMarkers.includes(listSites[j][0]))
						{ 
						sitesMarkers.push(listSites[j][0]);
						const markerMain = new google.maps.Marker({	
						        position: pos,
						        data:data,
						        icon:icon,
						        ID:markerId
						         	    });
						markerMain.metadata = { id: markerId };
						markersMain[markerId] = markerMain;
						markersMain.push(markerMain);
						}
						console.log(markersMain);
						google.maps.event.addListener(marker, "click", function (e) {

						     	infowindow.setContent(this.data); 
					        	infowindow.open(map,this);
						     	map.setZoom(15);
						     	map.setCenter(pos);
						     });
				  		

					}

	var markerCluster = new MarkerClusterer(map,markersMain, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
				

var markersSite="";
var markerClusterPO=new MarkerClusterer();
var markerClusterItem=new MarkerClusterer();

	var PoSelectedTemp="";
var ItemSelectedTemp="";

		$(' #initial_ul > span').on('click', function (e) {

			markerClusterPO.clearMarkers();	
			markerClusterItem.clearMarkers();	
			markerCluster.addMarkers(markersMain);
		  

		});
	   
		$('.PO > span').on('click', function (e) {

		var tempClusterArray=[];
		var selectedPo=$(this).parent().attr('id');			
		markerCluster.clearMarkers();
								 
			if(selectedPo!=PoSelectedTemp){

				if(PoSelectedTemp!=""){

					console.log("temp is not null "+PoSelectedTemp);
					
					for(j=0;j<window[""+PoSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+PoSelectedTemp][j].ID];
	 					previousMarker.setMap(null); 							 						
					}
				}				

				for(j=0;j<window[""+selectedPo].length;j++){ 	           	
		 				var currentMarker=markers[window[""+selectedPo][j].ID];
		 				currentMarker.setMap(map); 			 									
					}

				markerClusterItem.clearMarkers();								
				PoSelectedTemp=selectedPo;
				tempClusterArray=window[""+selectedPo];		
				markerClusterPO.clearMarkers();
				markerClusterPO = new MarkerClusterer(map,tempClusterArray);
				markerClusterPO.addMarkers(tempClusterArray);
				
			}

			map.setZoom(4);
	});
	
	
	$(".Item > span").on('click',function () {

  var tempClusterArray=[];
		var selectedItem=$(this).parent().attr('id');
		parents=selectedItem.split("_");
		var ItemPO=parents[1];
		//var ItemId=parents[0];				
			   
  var selectedPo=$("#"+selectedItem).parents().map(function() {
					return this.id;
					}).get(3);
		console.log(selectedItem);		
  console.log("selectedPo"+selectedPo);
  
  
  	if(selectedPo!=PoSelectedTemp){

				if(PoSelectedTemp!=""){

					console.log("temp is not null "+PoSelectedTemp);
					
					for(j=0;j<window[""+PoSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+PoSelectedTemp][j].ID];
	 					previousMarker.setMap(null); 							 						
					}
				}				

				for(j=0;j<window[""+selectedPo].length;j++){ 	           	
		 				var currentMarker=markers[window[""+selectedPo][j].ID];
		 				currentMarker.setMap(map); 			 									
					}  
					       
			if(selectedItem!=ItemSelectedTemp){
				if(ItemSelectedTemp!=""){
					console.log("temp is not null "+selectedItem);
					
					for(j=0;j<window[""+ItemSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+ItemSelectedTemp][j].ID];
	 					previousMarker.setMap(null);	 						 						
					}
				}				

				for(j=0;j<window[""+selectedItem].length;j++){   	           	
		 				var currentMarker=markers[window[""+selectedItem][j].ID];
		 				currentMarker.setMap(map); 			 									
					}
				
				markerClusterPO.clearMarkers();								
				ItemSelectedTemp=selectedItem;
				tempClusterArray=window[""+selectedItem];		
				markerClusterItem.clearMarkers();
				markerClusterItem = new MarkerClusterer(map,tempClusterArray);
				markerClusterItem.addMarkers(tempClusterArray);
				PoSelectedTemp=selectedPo;
				map.setZoom(5);
				
				}			
				}
				
			else
            {          
			if(selectedItem!=ItemSelectedTemp){
				if(ItemSelectedTemp!=""){
					console.log("temp is not null "+selectedItem);
					
					for(j=0;j<window[""+ItemSelectedTemp].length;j++){
	 					var previousMarker=markers[window[""+ItemSelectedTemp][j].ID];
	 					previousMarker.setMap(null);	 						 						
					}
				}				

				for(j=0;j<window[""+selectedItem].length;j++){   	           	
		 				var currentMarker=markers[window[""+selectedItem][j].ID];
		 				currentMarker.setMap(map); 			 									
					}
				
				markerClusterPO.clearMarkers();								
				ItemSelectedTemp=selectedItem;
				tempClusterArray=window[""+selectedItem];		
				markerClusterItem.clearMarkers();
				markerClusterItem = new MarkerClusterer(map,tempClusterArray);
				markerClusterItem.addMarkers(tempClusterArray);
				PoSelectedTemp=selectedPo;
				tempClusterArray=window[""+PoSelectedTemp];
				map.setZoom(5);
				
				
				}
				} 
          
				});
				
	
	$('.Site > span').on('click', function (e) {

		markerClusterPO.clearMarkers();								
		markerClusterItem.clearMarkers();	
		
		var SiteId=$(this).parent().attr('id');
		var res=$(this).parents().map(function() {
			return this.id;
			}).get().join( "_" );

		parents=res.split("_");
		var selectedItem=parents[1];		
		console.log("selectedItem"+selectedItem);
		console.log("markersSite"+markersSite);

		
		var ware = selectedItem.substring(0, 4);
		var selectedItemPO=$("#"+SiteId).parents().map(function() {
			return this.id;
			}).get(3);
		console.log("selectedItemPO"+selectedItemPO);

		if(ware=="WARE"){
			if(selectedItem!=markersSite)
				{
		 		console.log("==============///////////clicked"+selectedItem);

	 			var selMarker="";
			
				markerId=selectedItem;
				selMarker=markers[markerId];
				var latSitee = selMarker.getPosition().lat();
				var lngSitee = selMarker.getPosition().lng();					
				selMarker.setIcon(icon2);
				panTo(latSitee, lngSitee);
				infowindow.setContent(selMarker.data);
				infowindow.open(map,selMarker);

				const pos = new google.maps.LatLng(latSitee,lngSitee);
				
				
				circle.setCenter(pos);
				circle2.setCenter(pos);

				
				
				if(markersSite!="")
					{
 						var otherMarkers=markers[markersSite];			
						otherMarkers.setIcon(icon);
				
					}
				markersSite="";	
				markersSite=selectedItem;
			
				
			}
			map.setZoom(15);
		}
});
		
				
	   $(".NodeType > span").on('click',function () {

		   var res=$(this ).parents()
		   .map(function() {
		   	return this.id;
		   	})
		   .get()
		   .join( "_" );

		   parents=res.split("_");
		   var selectedItem=parents[1];
		   console.log("selectedItem"+selectedItem);
		   console.log("markersSite"+markersSite);
		   var ware = selectedItem.substring(0, 4);

		   if(ware=="WARE"){
		   	if(selectedItem!=markersSite)
		   	{
		   		console.log("==============///////////clicked"+selectedItem);

		   		var selMarker="";

		   		markerId=selectedItem;				
		   		selMarker=markers[markerId];
		   		var latSitee = selMarker.getPosition().lat();
		   		var lngSitee = selMarker.getPosition().lng();					
		   		selMarker.setIcon(icon2);
		   		panTo(latSitee, lngSitee);
		   		infowindow.setContent(selMarker.data);
		   		infowindow.open(map,selMarker);


		   		if(markersSite!="")
		   				{
		   				var otherMarkers=markers[markersSite];			
		   				otherMarkers.setIcon(icon);

		   					}

		   		markersSite="";	
		   		markersSite=selectedItem;

		   		map.setZoom(15);
		   	}
		   }
		   });

	   $(".Node > span").on('click',function () {
		   
		   var res=$(this ).parents()
		   .map(function() {
		   	return this.id;
		   	})
		   .get()
		   .join( "_" );

			console.log("rezzz"+res);
		   parents=res.split("_");
		   var selectedItem=parents[8];
		   console.log("selectedItem"+selectedItem);
		   console.log("markersSite"+markersSite);
		   var ware = selectedItem.substring(0, 4);

		   if(ware=="WARE"){
		   	if(selectedItem!=markersSite)
		   	{
		   		console.log("==============///////////clicked"+selectedItem);

		   		var selMarker="";

		   		markerId=selectedItem;				
		   		selMarker=markers[markerId];
		   		var latSitee = selMarker.getPosition().lat();
		   		var lngSitee = selMarker.getPosition().lng();					
		   		selMarker.setIcon(icon2);
		   		panTo(latSitee, lngSitee);
		   		infowindow.setContent(selMarker.data);
		   		infowindow.open(map,selMarker);


		   		if(markersSite!="")
		   				{
		   				var otherMarkers=markers[markersSite];			
		   				otherMarkers.setIcon(icon);

		   					}

		   		markersSite="";	
		   		markersSite=selectedItem;

		   		map.setZoom(15);
		   	}
		   }
		   }); 	
	   
function panTo(newLat, newLng) {
		   	  if (panPath.length > 0) {
		   	    // We are already panning...queue this up for next move
		   	    panQueue.push([newLat, newLng]);
		   	  } else {
		   	    // Lets compute the points we'll use
		   	    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
		   	    var curLat = map.getCenter().lat();
		   	    var curLng = map.getCenter().lng();
		   	    var dLat = (newLat - curLat)/STEPS;
		   	    var dLng = (newLng - curLng)/STEPS;

		   	    for (var i=0; i < STEPS; i++) {
		   	      panPath.push([curLat + dLat * i, curLng + dLng * i]);
		   	    }
		   	    panPath.push([newLat, newLng]);
		   	    panPath.shift();      // LAZY SYNCRONIZED LOCK
		   	    setTimeout(doPan, 20);
		   	  }
		   	}

		   function doPan() {
		   	  var next = panPath.shift();
		   	  if (next != null) {
		   	    // Continue our current pan action
		   	    map.panTo( new google.maps.LatLng(next[0], next[1]));
		   	    setTimeout(doPan, 20 );
		   	  } else {
		   	    // We are finished with this pan - check if there are any queue'd up locations to pan to 
		   	    var queued = panQueue.shift();
		   	    if (queued != null) {
		   	      panTo(queued[0], queued[1]);
		   	    }
		   	  }
		   	}	
	
		   }



		