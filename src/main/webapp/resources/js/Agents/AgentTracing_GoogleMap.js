markerGroups = [];
markerGroupsSignal=[];
markerGroupsUpload=[];
markerGroupsDownload=[];
markerGroupscolorSignal=[];
markerGroupscolorUpload=[];
markerGroupscolorDownload=[];

markerIds=[];
markerIdsDownload=[];
markerIdsSignal=[];
markerIdsUpload=[];


function ShowHideSequences(action) {
	console.log("inside");
	markerIds=[];
	if(markerIdsSignal.length > 0) {
		markerIds=markerIdsSignal;
		
		console.log(markerIds.length);
		for(l=0;l<markerIds.length;l++) {
			
		
				markerGroups[markerIds[l]]=markerGroupsSignal[markerIds[l]];
				for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
				 	
				 	var marker = markerGroups[markerIds[l]][i];
				 	  if(action == "show") {
				 		marker.setLabel({text: (String) (l+1), className:"marker-position-sequence",color:"green"});
				 	  }else {
					 	  marker.setLabel(null);
				 	  }
				 	
				 }
			}
	}
}

function AddSignalMarkers(lst,map,color){
	markers=[];
	//Set zoom level
	markerIdsSignal=[];
		for(i=0;i<lst.length;i++){
				markerIdsSignal.push(lst[i][0]);
				markerId=lst[i][0];
				infowindow = new google.maps.InfoWindow();
				var Signal=lst[i][1];
				var latSignal=lst[i][2];
				var lngSignal=lst[i][3];
				const position = new google.maps.LatLng(latSignal,lngSignal);

				var Technology=lst[i][4];	
				var AgentName=lst[i][5];		
				var AgentNumber=lst[i][6];
				var CID=lst[i][7];
					
				var signal="<b style='font-size:13px;'><u>Signal: </u></b>"+Signal+" dBm";
				var technology="<b style='font-size:13px;'><u>Technology: </u></b>"+Technology;
				var latitude="<b style='font-size:13px;'><u>Latitude: </u></b>"+latSignal;
				var longitude="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lngSignal;
				var agentName="<b style='font-size:13px;'><u>Agent Name:</u> </b>"+AgentName;
				var agentNumber="<b style='font-size:13px;'><u>Agent Number:</u> </b>"+AgentNumber;
				var cid="<b style='font-size:13px;'><u>CID:</u> </b>"+CID;

				var data="<div>"+signal+"</br>"+cid+"</br>"+technology+"</br>"+longitude+ "</br>"+latitude+"</br>"+agentName+"</br>"+agentNumber+"</div>";			

				//Add markers on map
				const marker = new google.maps.Marker({
			        position: position,
			        data:data,
			        ID:markerId,
			        Lat:latSignal,
			        Long:lngSignal,
			        color:color,
			        icon: {
			        	  path: google.maps.SymbolPath.CIRCLE,
					        fillColor: color,
					        fillOpacity: 1,
					        strokeColor: 'transparent',
					        strokeOpacity: 0.9,
					        strokeWeight: 1,
					        scale: 6 
			            
			          }
			        
			         	    });

			
				 	marker.metadata = { id: markerId };
				    markers[markerId] = marker;
				    markers.push(marker);
				
				  //Add markers to array depending on colors
				    if (!markerGroupsSignal[markerId]) markerGroupsSignal[markerId] = [];
				    	 markerGroupsSignal[markerId].push(marker);
				    	 
					if (!markerGroupscolorSignal[color]) markerGroupscolorSignal[color] = [];
						markerGroupscolorSignal[color].push(marker);
						  
						  
				google.maps.event.addListener(marker, "click", function (e) {

						  
						  
				     	infowindow.setContent(this.data); 
			        	infowindow.open(map,this);

						 						 
						 
				 			});
				google.maps.event.addListener(marker, "dblclick", function (e) {

			     	map.setZoom(15);
			     	map.setCenter(position);

			 			});
				
				
				markers[markerId].setMap(map);
				
	 			
		}
		
		
		
		
		
				
}
markerGroupscolor=[];
function AddDownSpeedMarkers(lst,map,color){


	markers=[];

		
		for(i=0;i<lst.length;i++){
	     
				markerIdsDownload.push(lst[i][0]);
				markerId=lst[i][0];
				
				
				infowindow = new google.maps.InfoWindow();
				var Signal=lst[i][1];
				var latSignal=lst[i][2];
				var lngSignal=lst[i][3];
				const position = new google.maps.LatLng(latSignal,lngSignal);

				var Technology=lst[i][4];	
				var AgentName=lst[i][5];		
				var AgentNumber=lst[i][6];
				var CID=lst[i][7];
					
				var signal="<b style='font-size:13px;'><u>Download Speed: </u></b>"+Signal+" Mbps";
				var technology="<b style='font-size:13px;'><u>Technology: </u></b>"+Technology;
				var latitude="<b style='font-size:13px;'><u>Latitude: </u></b>"+latSignal;
				var longitude="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lngSignal;
				var agentName="<b style='font-size:13px;'><u>Agent Name:</u> </b>"+AgentName;
				var agentNumber="<b style='font-size:13px;'><u>Agent Number:</u> </b>"+AgentNumber;
				var cid="<b style='font-size:13px;'><u>CID:</u> </b>"+CID;

				var data="<div>"+signal+"</br>"+cid+"</br>"+technology+"</br>"+longitude+ "</br>"+latitude+"</br>"+agentName+"</br>"+agentNumber+"</div>";			

				//Add markers on map
				const marker = new google.maps.Marker({
			        position: position,
			        data:data,
			        ID:markerId,
			        Lat:latSignal,
			        Long:lngSignal,
			        color:color,
			      
			        icon: {
			        	  path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW,
					        fillColor: color,
					        fillOpacity: 1,
					        strokeColor: 'transparent',
					        strokeOpacity: 0.9,
					        strokeWeight: 1,
					        scale: 3
			            
			          }
			        
			         	    });
	
				 	marker.metadata = { id: markerId };
				    markers[markerId] = marker;
				    markers.push(marker);
				
				  //Add markers to array depending on colors
				    if (!markerGroupsDownload[markerId]) markerGroupsDownload[markerId] = [];
				    	markerGroupsDownload[markerId].push(marker);
				    	
					if (!markerGroupscolorDownload[color]) markerGroupscolorDownload[color] = [];
						 markerGroupscolorDownload[color].push(marker);
						  
						  
				google.maps.event.addListener(marker, "click", function (e) {

						  
						  
				     	infowindow.setContent(this.data); 
			        	infowindow.open(map,this);

						 						 
						 
				 			});
				google.maps.event.addListener(marker, "dblclick", function (e) {

			     	map.setZoom(15);
			     	map.setCenter(position);

			 			});
				
				
				markers[markerId].setMap(map);
	 			
		}
		
				
}



function AddUpSpeedMarkers(lst,map,color){


	markers=[];

		
		for(i=0;i<lst.length;i++){
	     
				markerIdsUpload.push(lst[i][0]);
				markerId=lst[i][0];
				
				
				infowindow = new google.maps.InfoWindow();
				var Signal=lst[i][1];
				var latSignal=lst[i][2];
				var lngSignal=lst[i][3];
				const position = new google.maps.LatLng(latSignal,lngSignal);

				var Technology=lst[i][4];	
				var AgentName=lst[i][5];		
				var AgentNumber=lst[i][6];
				var CID=lst[i][7];
					
				var signal="<b style='font-size:13px;'><u>Upload Speed: </u></b>"+Signal+" Mbps";
				var technology="<b style='font-size:13px;'><u>Technology: </u></b>"+Technology;
				var latitude="<b style='font-size:13px;'><u>Latitude: </u></b>"+latSignal;
				var longitude="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lngSignal;
				var agentName="<b style='font-size:13px;'><u>Agent Name:</u> </b>"+AgentName;
				var agentNumber="<b style='font-size:13px;'><u>Agent Number:</u> </b>"+AgentNumber;
				var cid="<b style='font-size:13px;'><u>CID:</u> </b>"+CID;

				var data="<div>"+signal+"</br>"+cid+"</br>"+technology+"</br>"+longitude+ "</br>"+latitude+"</br>"+agentName+"</br>"+agentNumber+"</div>";			

				//Add markers on map
				const marker = new google.maps.Marker({
			        position: position,
			        data:data,
			        ID:markerId,
			        Lat:latSignal,
			        Long:lngSignal,
			        color:color,
			      
			        icon: {
			        	  path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
					        fillColor: color,
					        fillOpacity: 1,
					        strokeColor: 'transparent',
					        strokeOpacity: 0.9,
					        strokeWeight: 1,
					        scale: 3
			            
			          }
			        
			         	    });
	
				 	marker.metadata = { id: markerId };
				    markers[markerId] = marker;
				    markers.push(marker);
				
				  //Add markers to array depending on colors
				    if (!markerGroupsUpload[markerId]) markerGroupsUpload[markerId] = [];
				    	markerGroupsUpload[markerId].push(marker);
						
					if (!markerGroupscolorUpload[color]) markerGroupscolorUpload[color] = [];
						  markerGroupscolorUpload[color].push(marker);
						  
						  
				google.maps.event.addListener(marker, "click", function (e) {

						  
						  
				     	infowindow.setContent(this.data); 
			        	infowindow.open(map,this);

						 						 
						 
				 			});
				google.maps.event.addListener(marker, "dblclick", function (e) {

			     	map.setZoom(15);
			     	map.setCenter(position);

			 			});
				
				
				markers[markerId].setMap(map);
	 			
		}
		
				
}

function AddSelectedSignalColor(lst,map) {
 
		if(lst.length != 0){
		var dataSite = lst;
		disableData = dataSite;
		
		ExcellentListIndex=[];
		GoodListIndex=[];
		FairListIndex=[];
		BadListIndex=[];
		ExBadListIndex=[];
		
		Excellent_list=[];
		Good_list=[];
		Fair_list=[];
		Bad_list=[];
		ExBad_list=[];

		
		
		//fill extremely bad array
		    for (j=0;j<dataSite.length;j++){
		
		    	
		    	    	
		    	        if (dataSite[j][1] <= -95) {
		    	        	ExBadListIndex.push(j);
		    	        	ExBad_list.push(dataSite[j]);
		    	        	
		    	        	
		    	        }
		    	       else if (dataSite[j][1] > -95 && dataSite[j][1]<= -85) {
		    	    	   BadListIndex.push(j);
		    	    	   Bad_list.push(dataSite[j]);

		    	        }
		    	        else if (dataSite[j][1] > -85 && dataSite[j][1]<= -75) {
		    	        	FairListIndex.push(j);
		    	        	Fair_list.push(dataSite[j]);

		    	        }
		    	        else if (dataSite[j][1] > -75 && dataSite[j][1]<= -65) {
		    	        	GoodListIndex.push(j);
		    	        	Good_list.push(dataSite[j]);
		    	        	

		    	        }
		    	        else if (dataSite[j][1] > -65) {
		    	        	ExcellentListIndex.push(j);
		    	        	Excellent_list.push(dataSite[j]);
		    	        	

		    	        }
		    	  
		      } 
	        if (ExcellentListIndex.length > 0  ){
	        	AddSignalMarkers(Excellent_list,map,"#006400");
	   
	        }

	        if (GoodListIndex.length > 0  ){  
	        	AddSignalMarkers(Good_list,map,"#0080FF");
	       
	        }
	        
	        if (FairListIndex.length > 0  ){  
	        	AddSignalMarkers(Fair_list,map,"#FFFF00");
	        }
	        
	        if (BadListIndex.length > 0  ){  
	        	AddSignalMarkers(Bad_list,map,"#4D0207");
	        }
	        
	        if (ExBadListIndex.length > 0  ){  
	        	AddSignalMarkers(ExBad_list,map,"#FF0000");
	        }  		    	

        }
}
	
	
	    	
	
function AddSelectedDownSpeedColor(lst,map) {
	if(lst.length!=0){
	var dataSite = lst;
	disableData = dataSite;
	
	ExcellentListIndex=[];
	GoodListIndex=[];
	FairListIndex=[];
	BadListIndex=[];
	ExBadListIndex=[];
	
	Excellent_list=[];
	Good_list=[];
	Fair_list=[];
	Bad_list=[];
	ExBad_list=[];

	
	//fill extremely bad array
	    for (j=0;j<dataSite.length;j++){
	
	    	    	
	    	        if (dataSite[j][1] <= 0.250) {
	    	        	ExBadListIndex.push(j);
	    	        	ExBad_list.push(dataSite[j]);
	    	        	
	    	        }
	    	       else if (dataSite[j][1] > 0.250 && dataSite[j][1]<= 2) {
	    	    	   BadListIndex.push(j);
	    	    	   Bad_list.push(dataSite[j]);
	    	    	   

	    	        }
	    	        else if (dataSite[j][1] > 2 && dataSite[j][1]<= 15) {
	    	        	FairListIndex.push(j);
	    	        	Fair_list.push(dataSite[j]);
	    	        	

	    	        }
	    	        else if (dataSite[j][1] > 15 && dataSite[j][1]<= 30) {
	    	        	GoodListIndex.push(j);
	    	        	Good_list.push(dataSite[j]);
	    	        	
	    	        	

	    	        }
	    	        else if (dataSite[j][1] > 30) {
	    	        	ExcellentListIndex.push(j);
	    	        	Excellent_list.push(dataSite[j]);
	    	      
	    	        	

	    	        }
	    	
	      } 
	    
        
        if (ExcellentListIndex.length > 0  ){
        	AddDownSpeedMarkers(Excellent_list,map,"#006400");
        }

        if (GoodListIndex.length > 0  ){  
        	AddDownSpeedMarkers(Good_list,map,"#0080FF");
        }
        
        if (FairListIndex.length > 0  ){  
        	AddDownSpeedMarkers(Fair_list,map,"#FFFF00");
        }
        
        if (BadListIndex.length > 0  ){  
        	AddDownSpeedMarkers(Bad_list,map,"#4D0207");
        }
        
        if (ExBadListIndex.length > 0  ){  
        	AddDownSpeedMarkers(ExBad_list,map,"#FF0000");
        }  
    }
}



    	
    	

function AddSelectedUpSpeedColor(lst,map) {

	
	
	if (lst.length != 0) {
	 
	
	var dataSite = lst;
	disableData = dataSite;
	
	ExcellentListIndex=[];
	GoodListIndex=[];
	FairListIndex=[];
	BadListIndex=[];
	ExBadListIndex=[];
	
	Excellent_list=[];
	Good_list=[];
	Fair_list=[];
	Bad_list=[];
	ExBad_list=[];

	
	//fill extremely bad array
	    for (j=0;j<dataSite.length;j++){
	
	    	    	
	    	        if (dataSite[j][1] <= 0.250) {
	    	        	ExBadListIndex.push(j);
	    	        	ExBad_list.push(dataSite[j]);
	    	        	
	    	        }
	    	       else if (dataSite[j][1] > 0.250 && dataSite[j][1]<= 2) {
	    	    	   BadListIndex.push(j);
	    	    	   Bad_list.push(dataSite[j]);
	    	    	   

	    	        }
	    	        else if (dataSite[j][1] > 2 && dataSite[j][1]<= 15) {
	    	        	FairListIndex.push(j);
	    	        	Fair_list.push(dataSite[j]);
	    	        	

	    	        }
	    	        else if (dataSite[j][1] > 15 && dataSite[j][1]<= 30) {
	    	        	GoodListIndex.push(j);
	    	        	Good_list.push(dataSite[j]);
	    	        	
	    	        	

	    	        }
	    	        else if (dataSite[j][1] > 30) {
	    	        	ExcellentListIndex.push(j);
	    	        	Excellent_list.push(dataSite[j]);
	    	      
	    	        	

	    	        }
	    	
	      } 
	    
	    
        
        if (ExcellentListIndex.length > 0  ){
        	AddUpSpeedMarkers(Excellent_list,map,"#006400");
        }

        if (GoodListIndex.length > 0  ){  
        	AddUpSpeedMarkers(Good_list,map,"#0080FF");

        }
        
        if (FairListIndex.length > 0  ){  
        	AddUpSpeedMarkers(Fair_list,map,"#FFFF00");
        }
        
        if (BadListIndex.length > 0  ){  
        	AddUpSpeedMarkers(Bad_list,map,"#4D0207");
        }
        
        if (ExBadListIndex.length > 0  ){  

        }  
}



    	}
    	
	

function defaultLegend(){

	document.getElementById("tableDiv").innerHTML ="";
 	document.getElementById("tableDiv").innerHTML =divTablee;
 	
 	document.getElementById("legendHeader").innerHTML ="";
	document.getElementById("legendHeader").innerHTML='<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6> <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAll()"  id="selectUnselect" >Unselect All</button>';
	
	}


function SelectUnselectAll() {
	checkedColorr=[];
	var element = document.getElementById("selectUnselect");
	
		if(ExBadListIndex.length >0) {
			checkedColorr.push("#FF0000");
					}
		if(BadListIndex.length  >0) {
			checkedColorr.push("#4D0207");
					}

		if(FairListIndex.length  >0) {
			checkedColorr.push("#FFFF00");
		}
		
		if(GoodListIndex.length  >0) {
			checkedColorr.push("#0080FF");
		}
		
		if(ExcellentListIndex.length  >0) {
			checkedColorr.push("#006400");
		}

		
		

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = "Select All";
				//Add checked checkboxes to colors array
				 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
				 colors = [];
				checkboxes.forEach((checkbox) => {
				    colors.push(checkbox.value);
				});

				
				//Uncheck all checkboxes by value (by color)
				for (var l= 0; l < colors.length; l++) {
				
				$('input:checkbox[value="' + colors[l] + '"]').prop("checked", false);

				 }

				markerIds=[];
				if(markerIdsSignal.length > 0) {
					markerIds=markerIdsSignal;
					

					for(l=0;l<markerIds.length;l++) {
						
					
							markerGroups[markerIds[l]]=markerGroupsSignal[markerIds[l]];
					
						
							for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
							 	
							 	var marker = markerGroups[markerIds[l]][i];


							 
							 	  marker.setVisible(false);
							 	
							 }
						}
				}
				
				if(markerIdsDownload.length > 0) {
					markerIds=markerIdsDownload;
					for(l=0;l<markerIds.length;l++) {
			
							markerGroups[markerIds[l]]=markerGroupsDownload[markerIds[l]];
					
						
							for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
							 	
							 	var marker = markerGroups[markerIds[l]][i];


							 
							 	  marker.setVisible(false);
							 	
							 }
						}
				}
				
				if(markerIdsUpload.length > 0) {
					markerIds=markerIdsUpload;
					for(l=0;l<markerIds.length;l++) {
			
							markerGroups[markerIds[l]]=markerGroupsUpload[markerIds[l]];
					
						
							for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
							 	
							 	var marker = markerGroups[markerIds[l]][i];


							 
							 	  marker.setVisible(false);
							 	
							 }
						}
				}

			  }

		  else {
			  element.innerHTML = "Unselect All";
			  
			 var sitesChecked = document.querySelectorAll('input[name="legendCheckbox"]:checked');

			 for (var l= 0; l < checkedColorr.length; l++) {
					$('input:checkbox[value="' + checkedColorr[l] + '"]').prop("checked", true);
							 }

		  	 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
				 colors = [];
				checkboxes.forEach((checkbox) => {
				    colors.push(checkbox.value);
				});
				
				markerIds=[];
				if(markerIdsSignal.length > 0) {
					markerIds=markerIdsSignal;
					

					for(l=0;l<markerIds.length;l++) {
						
					
							markerGroups[markerIds[l]]=markerGroupsSignal[markerIds[l]];
					
						
							for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
							 	
							 	var marker = markerGroups[markerIds[l]][i];


							 
							 	  marker.setVisible(true);
							 	
							 }
						}
				}
				
				if(markerIdsDownload.length > 0) {
					markerIds=markerIdsDownload;
					for(l=0;l<markerIds.length;l++) {
			
							markerGroups[markerIds[l]]=markerGroupsDownload[markerIds[l]];
					
						
							for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
							 	
							 	var marker = markerGroups[markerIds[l]][i];


							 
							 	  marker.setVisible(true);
							 	
							 }
						}
				}
				
				if(markerIdsUpload.length > 0) {
					markerIds=markerIdsUpload;
					for(l=0;l<markerIds.length;l++) {
			
							markerGroups[markerIds[l]]=markerGroupsUpload[markerIds[l]];
					
						
							for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
							 	
							 	var marker = markerGroups[markerIds[l]][i];


							 
							 	  marker.setVisible(true);
							 	
							 }
						}
				}
			}
		}