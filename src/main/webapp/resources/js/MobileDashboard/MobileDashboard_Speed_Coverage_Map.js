//Add legend button under zoom control on map
function LegendControl(controlDiv, map) {
	
    const controlUI = document.createElement("dv");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
   // controlUI.style.borderRadius = "3px";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"  onClick="ShowHideDiv()"><i class="fas fa-arrow-right fa-lg "></i></button>';
    controlUI.title = "Open Legend";
    controlDiv.appendChild(controlUI);

  }
  
 //Add defaultZoom button under zoom control on map
function DefaultZoomControl(controlDiv2, map) {
	
    const controlUI2 = document.createElement("div");
    controlUI2.style.backgroundColor = "white";
    controlUI2.style.border = "8px solid white";
    controlUI2.style.cursor = "pointer";
    controlUI2.style.marginLeft = "10px";
    controlUI2.style.marginTop = "10px";
    controlUI2.innerHTML = '<button style="border:none;outline:none; background:white;" ><i class="fa fa-undo fa-lg "></i></button>';
    controlUI2.title = "Reset Default Zoom";
    controlDiv2.appendChild(controlUI2);

    controlUI2.addEventListener("click", () => {

    	var Nairobi=new google.maps.LatLng(-0.1,36);
        map.setCenter(Nairobi);
        map.setZoom(5.75);
        
        if (infowindow) {
            infowindow.close();
        }  
             
        
              });

  }

function ShowHideDiv(){
	$("#legendSpeedCoverage").toggle();
}
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

markers=[];
selectedSites=[];
var concatenatedSites ="";
colormarker=[];


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
function AddSignalMarkers(lst,map,color){

var ctx = getContextPath();

	markers=[];
	
	//Set zoom level
	map.setZoom(5.75);

		var Nairobi=new google.maps.LatLng(-0.1, 36);	
		map.setCenter(Nairobi);
		
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

var ctx = getContextPath();

	markers=[];
	
	//Set zoom level
	map.setZoom(5.75);

		var Nairobi=new google.maps.LatLng(-0.1, 36);	
		map.setCenter(Nairobi);
		
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

var ctx = getContextPath();

	markers=[];
	
	//Set zoom level
	map.setZoom(5.75);

		var Nairobi=new google.maps.LatLng(-0.1, 36);	
		map.setCenter(Nairobi);
		
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
	
	
		$("#tableSpeedCoverage").append('<table id="coverageLegend"><caption id="tableCaption" style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-160px;left:20px;">Signal Strength</caption>'
				+'		<tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th>'
					+'<th style="position: relative;top: 5px;left:10px;"></th></tr><tr><td style="position: relative;top:17px;left:40px;">'
					+'<input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="excellent" value="#006400"/></td>'
					+'<td style="position: relative;top:30px;left:60px;"><div id="greenDot" class="dot green"></div></td><td style="position: relative;top:30px;left:60px;"></td>'
					+'<td style="position: relative;top: 30px;left:-5px"><label style="color:black;font-weight:bold;font-size:1.40ex; " >Excellent Signal</label></td></tr><tr>'
					+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="good" value="#0080FF"/></td>'
					+'<td style="position: relative;top:30px;left:60px;"><div class="dot blue"></div></td><td style="position: relative;top:30px;left:70px;"></td>'
					+'<td style="position: relative;top: 30px;left:-5px"><label style="color:black;font-weight:bold;font-size:1.40ex; " >Good Signal</label></td></tr><tr>'
					+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="fair"  value="#FFFF00"/></td>'
					+'<td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td><td style="position: relative;top: 30px;left:70px;">'
					+'<label style="color:black;font-weight:bold;font-size:1.40ex;" >Fair Signal</label></td></tr><tr>'
					+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="bad"  value="#4D0207"/></td>'
					+'<td style="position: relative;top:30px;left:60px;"><div class="dot redDark"></div></td><td style="position: relative;top: 30px;left:70px;">'
					+'<label style="color:black;font-weight:bold;font-size:1.40ex;" >Bad Signal</label></td></tr><tr>'
					+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="nosignal"  value="#FF0000"/></td>'
					+'<td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td><td style="position: relative;top: 30px;left:70px;">'
					+'<label style="color:black;font-weight:bold;font-size:1.40ex;" >Extremely Bad</label></td></tr>'
					+'<tr></tr></table>'
				);
		
		
		
		if (lst.length == 0) {
		 	  $("#excellent").attr('disabled', true);
		 	  $("#good").attr('disabled', true);
		 	  $("#fair").attr('disabled', true);
		 	  $("#bad").attr('disabled', true);
		 	  $("#nosignal").attr('disabled', true);
		 		
			var element = document.getElementById("selectUnselect");

			 if (element.innerHTML == "Unselect All"){
				 element.innerHTML = " ";
			 }
		
		}
		else {

			
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == " "){
			 element.innerHTML = "Unselect All";
		 }
		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = "Select All";
		 }
		  if (element.innerHTML == "Select All"){
			 element.innerHTML = "Unselect All";
		 }
		 
		
		var dataSite = lst;
		
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
		    	        else if (dataSite[j][1] > -65 && dataSite[j][1]<= -55) {
		    	        	ExcellentListIndex.push(j);
		    	        	Excellent_list.push(dataSite[j]);
		    	      
		    	        	

		    	        }
		    	  
		      } 
	        if (ExcellentListIndex.length > 0  ){
	        	AddSignalMarkers(Excellent_list,map,"#006400");
	            $("#excellent").prop('checked',true);  
	            $("#excellent").attr('disabled', false);
	        }else {
	        	
	        	$("#excellent").attr('disabled', true);
	        	element.innerHTML = "Unselect All";
	        }

	        if (GoodListIndex.length > 0  ){  
	        	AddSignalMarkers(Good_list,map,"#0080FF");
	            $("#good").prop('checked',true);  
	            $("#good").attr('disabled', false);
	        }else {
	        	$("#good").attr('disabled', true);
	        	element.innerHTML = "Unselect All";
	        }
	        
	        if (FairListIndex.length > 0  ){  
	        	AddSignalMarkers(Fair_list,map,"#FFFF00");
	            $("#fair").prop('checked',true);  
	        	$("#fair").attr('disabled', false);
	        }else {
	        	$("#fair").attr('disabled', true);
	        	element.innerHTML = "Unselect All";
	        }
	        
	        if (BadListIndex.length > 0  ){  
	        	AddSignalMarkers(Bad_list,map,"#4D0207");
	            $("#bad").prop('checked',true);  
	        	$("#bad").attr('disabled', false);
	        }else {
	        	$("#bad").attr('disabled', true);
	        	element.innerHTML = "Unselect All";
	        }
	        
	        if (ExBadListIndex.length > 0  ){  
	        	AddSignalMarkers(ExBad_list,map,"#FF0000");
	            $("#nosignal").prop('checked',true);  
	        	$("#nosignal").attr('disabled', false);
	        } else {
	        	$("#nosignal").attr('disabled', true);
	        	element.innerHTML = "Unselect All";
	        }  
		    	
		    	var Total=ExBadListIndex.length+ExcellentListIndex.length+GoodListIndex.length+FairListIndex.length+BadListIndex.length;
		    	
		    	var average = ((ExBadListIndex.length*1)+(BadListIndex.length*2)+(FairListIndex.length*3)+(GoodListIndex.length*4)+(ExcellentListIndex.length*5))/Total;
		    	
		    	if(average >= 1 && average <2) {
		    		dot= "dot red";
		    	}else if(average >=2 && average < 3) {
		    		dot="dot redDark";
		    	}else if(average >=3 && average < 4) {
		    		dot="dot yellow";
		    	}else if(average >= 4 && average <4.5) {
		    		dot="dot blue";
		    	}else if(average >= 4.5) {
		    		dot="dot green";
		    	}
		    	var averagePercent =((average /5)*100).toFixed();

		    	$("#tableSpeedCoverage").append('<table id="coverageLegend1">'
		    			+'<tr><td style="position: relative;top:-30px;left:20px"><label style="color:#08526d;font-weight:bold;font-size:1.80ex;" >Count:</td></tr>'
		    			+'<tr>'
		    			+'<td style="position: relative;top:-37px;left:20px;"><div class="dot green"></div></td><td style="position: relative;top:-37px;left:-5px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+ExcellentListIndex.length+'</label></td>'
		    			+'<td style="position: relative;top:-37px;left:10px;"><div  class="dot blue"></div></td><td style="position: relative;top:-37px;left:10px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+GoodListIndex.length+'</label></td>'
		    			+'<td style="position: relative;top:-37px;left:20px;"><div  class="dot yellow"></div></td><td style="position: relative;top:-37px;left:20px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+FairListIndex.length+'</label></td>'
		    			+'<td style="position: relative;top:-37px;left:35px;"><div  class="dot redDark"></div></td><td style="position: relative;top:-37px;left:35px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+BadListIndex.length+'</label></td>'
		    			+'<td style="position: relative;top:-37px;left:43px;"><div  class="dot red"></div></td><td style="position: relative;top:-37px;left:46px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+ExBadListIndex.length+'</label></td>'
		    			+'</tr>'
		    			+'</table>'
		    			);
		    	$("#tableSpeedCoverage").append('<table id="coverageLegend2">'
		    			
		    			+'<tr><td style="position: relative;top:-37px;left:15px;"><label style="color:#08526d;font-weight:bold;font-size:1.80ex;" >Network Average Strength Signal</label></td>'
		    				+'<td style="position: relative;top:-40px;left:15px;"><div id="greenDot" class="'+dot+'"></div></td>'
		    				+'<td style="position: relative;top:-37px; left:15px;"><label style="color:black;font-weight:bold;font-size:1.80ex; " >'+averagePercent+'%</label></td></tr>'
		    				+'</table>'
		    			);
		    	
		    	
		    	
		    	$("#excellent").change(function() {

		    		ShowHideMarkers('#006400',"signal");
		    	
		    		
		    		});
		    	$("#good").change(function() {

		    		ShowHideMarkers('#0080FF',"signal");
		    		
		    		});
		    	$("#fair").change(function() {

		    		ShowHideMarkers('#FFFF00',"signal");
		    		
		    		});
		    	$("#bad").change(function() {

		    		ShowHideMarkers('#4D0207',"signal");
		    		
		    		});
		    	$("#nosignal").change(function() {

		    		ShowHideMarkers('#FF0000',"signal");
		    		
		    		});
		    		}
	
	
	
	    	}
	
function AddSelectedDownSpeedColor(lst,map) {
	
	
	$("#tableSpeedCoverage").append('<table id="DownLoadLegend"><caption id="tableCaption" style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-170px;left:20px;">Download Speed</caption>'
			+'<tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th>'
			+'<th style="position: relative;top: 5px;left:10px;"></th></tr><tr><td style="position: relative;top:17px;left:40px;">'
			+'<input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="excellent_down" value="#006400"/></td>'
			+'<td style="position: relative;top:30px;left:60px;"><div class="downloadGreen"></div></td><td style="position: relative;top:30px;left:60px;"></td>'
			+'<td style="position: relative;top: 28px;left:-50px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " > > 30 Mbps Excellent</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="good_down" value="#0080FF"/></td>'
			+'<td style="position: relative;top:30px;left:60px;"><div class="downloadBlue"></div></td><td style="position: relative;top:30px;left:60px;"></td>'
			+'<td style="position: relative;top: 28px;left:-50px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " >15 - 30 Mbps Good</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="fair_down"   value="#FFFF00"/></td>'
			+'<td style="position: relative;top:30px;left:60px;"><div class="downloadYellow"></div></td><td style="position: relative;top: 28px;left:70px;">'
			+'<label style="color:black;font-weight:bold;font-size:1.40ex;" >2 - 15 Mbps Fair</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="bad_down"  value="#4D0207"/></td>'
			+'<td style="position: relative;top:30px;left:60px;"><div class="downloadDarkRed"></div></td><td style="position: relative;top: 28px;left:70px;">'
			+'<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps - 2 Mbps Bad</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="nosignal_down"  value="#FF0000"/></td>'
			+'<td style="position: relative;top:30px;left:60px;"><div class="downloadRed"></div></td><td style="position: relative;top: 28px;left:70px;">'
			+'<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps < Ex. Bad </label></td></tr>'
			+'<tr></tr></table>');
	
	
	
	if (lst.length == 0) {
	 	  $("#excellent_down").attr('disabled', true);
	 	  $("#good_down").attr('disabled', true);
	 	  $("#fair_down").attr('disabled', true);
	 	  $("#bad_down").attr('disabled', true);
	 	  $("#nosignal_down").attr('disabled', true);
	 		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {


	var element = document.getElementById("selectUnselect");

	 if (element.innerHTML == " "){
		 element.innerHTML = "Unselect All";
	 }
	 if (element.innerHTML == "Unselect All"){
		 element.innerHTML = "Select All";
	 }
	  if (element.innerHTML == "Select All"){
		 element.innerHTML = "Unselect All";
	 }
	 
	
	var dataSite = lst;
	
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
            $("#excellent_down").prop('checked',true);  
            $("#excellent_down").attr('disabled', false);
        }else {
        	
        	$("#excellent_down").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }

        if (GoodListIndex.length > 0  ){  
        	AddDownSpeedMarkers(Good_list,map,"#0080FF");
            $("#good_down").prop('checked',true);  
            $("#good_down").attr('disabled', false);
        }else {
        	$("#good_down").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }
        
        if (FairListIndex.length > 0  ){  
        	AddDownSpeedMarkers(Fair_list,map,"#FFFF00");
            $("#fair_down").prop('checked',true);  
        	$("#fair_down").attr('disabled', false);
        }else {
        	$("#fair_down").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }
        
        if (BadListIndex.length > 0  ){  
        	AddDownSpeedMarkers(Bad_list,map,"#4D0207");
            $("#bad_down").prop('checked',true);  
        	$("#bad_down").attr('disabled', false);
        }else {
        	$("#bad_down").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }
        
        if (ExBadListIndex.length > 0  ){  
        	AddDownSpeedMarkers(ExBad_list,map,"#FF0000");
            $("#nosignal_down").prop('checked',true);  
        	$("#nosignal_down").attr('disabled', false);
        } else {
        	$("#nosignal_down").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }  
	    	
	    	var Total=ExBadListIndex.length+ExcellentListIndex.length+GoodListIndex.length+FairListIndex.length+BadListIndex.length;
	    	
	    	var average = ((ExBadListIndex.length*1)+(BadListIndex.length*2)+(FairListIndex.length*3)+(GoodListIndex.length*4)+(ExcellentListIndex.length*5))/Total;
	    	
	    	if(average >= 1 && average <2) {
	    		dot= "downloadRed";
	    	}else if(average >=2 && average < 3) {
	    		dot="downloadDarkRed";
	    	}else if(average >=3 && average < 4) {
	    		dot="downloadYellow";
	    	}else if(average >= 4 && average <4.5) {
	    		dot="downloadBlue";
	    	}else if(average >= 4.5) {
	    		dot="downloadGreen";
	    	}
	    	var averagePercent =((average /5)*100).toFixed();

	    	$("#tableSpeedCoverage").append('<table id="DownLoadLegend1">'
	    			+'<tr><td style="position: relative;top:-30px;left:20px"><label style="color:#08526d;font-weight:bold;font-size:1.80ex;" >Count:</td></tr>'
	    			+'<tr>'
	    			+'<td style="position: relative;top:-37px;left:20px;"><div class="downloadGreen"></div></td><td style="position: relative;top:-37px;left:-5px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+ExcellentListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:10px;"><div  class="downloadBlue"></div></td><td style="position: relative;top:-37px;left:10px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+GoodListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:20px;"><div  class="downloadYellow"></div></td><td style="position: relative;top:-37px;left:20px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+FairListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:35px;"><div  class="downloadDarkRed"></div></td><td style="position: relative;top:-37px;left:35px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+BadListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:43px;"><div  class="downloadRed"></div></td><td style="position: relative;top:-37px;left:46px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+ExBadListIndex.length+'</label></td>'
	    			+'</tr>'
	    			);
	    	$("#tableSpeedCoverage").append('<table id="DownLoadLegend2">'
	    			
	    			+'<tr><td style="position: relative;top:-37px;left:15px;"><label style="color:#08526d;font-weight:bold;font-size:1.80ex;" >Network Average Download Speed</label></td>'
	    				+'<td style="position: relative;top:-40px;left:15px;"><div id="greenDot" class="'+dot+'"></div></td>'
	    				+'<td style="position: relative;top:-37px; left:15px;"><label style="color:black;font-weight:bold;font-size:1.80ex; " >'+averagePercent+'%</label></td></tr>'
	    				+'</table>'
	    			);
	    	
	    	
	    	
	    	$("#excellent_down").change(function() {

	    		ShowHideMarkers('#006400',"download");
	    	
	    		
	    		});
	    	$("#good_down").change(function() {

	    		ShowHideMarkers('#0080FF',"download");
	    		
	    		});
	    	$("#fair_down").change(function() {

	    		ShowHideMarkers('#FFFF00',"download");
	    		
	    		});
	    	$("#bad_down").change(function() {

	    		ShowHideMarkers('#4D0207',"download");
	    		
	    		});
	    	$("#nosignal_down").change(function() {

	    		ShowHideMarkers('#FF0000',"download");
	    		
	    		});
	    		}



    	}
    	

function AddSelectedUpSpeedColor(lst,map) {
	
	
	$("#tableSpeedCoverage").append('<table id="upLoadLegend"><caption id="tableCaption" style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-170px;left:20px;">Upload Speed</caption>'
			+'<tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th>'
			+'<th style="position: relative;top: 5px;left:10px;"></th></tr><tr><td style="position: relative;top:17px;left:40px;">'
			+'<input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="excellent_up" value="#006400"/></td>'
			+'<td style="position: relative;top:27px;left:60px;"><div class="uploadGreen"></div></td><td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>'
			+'<td style="position: relative;top: 25px;left:-50px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " > > 30 Mbps Excellent</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="good_up" value="#0080FF"/></td>'
			+'<td style="position: relative;top:27px;left:60px;"><div class="uploadBlue"></div></td><td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>'
			+'<td style="position: relative;top: 25px;left:-50px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " >15 - 30 Mbps Good</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="fair_up"   value="#FFFF00"/></td>'
			+'<td style="position: relative;top:27px;left:60px;"><div class="uploadYellow"></div></td><td style="position: relative;top: 25px;left:70px;">'
			+'<label style="color:black;font-weight:bold;font-size:1.40ex;" >2 - 15 Mbps Fair</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="bad_up"  value="#4D0207"/></td>'
			+'<td style="position: relative;top:27px;left:60px;"><div class="uploadDarkRed"></div></td><td style="position: relative;top: 25px;left:70px;">'
			+'<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps - 2 Mbps Bad</label></td></tr><tr>'
			+'<td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="nosignal_up"  value="#FF0000"/></td>'
			+'<td style="position: relative;top:27px;left:60px;"><div class="uploadRed"></div></td><td style="position: relative;top: 25px;left:70px;">'
			+'<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps Ex. Bad < </label></td></tr>'
			+'<tr></tr></table>');
	
	
	
	if (lst.length == 0) {
	 	  $("#excellent_up").attr('disabled', true);
	 	  $("#good_up").attr('disabled', true);
	 	  $("#fair_up").attr('disabled', true);
	 	  $("#bad_up").attr('disabled', true);
	 	  $("#nosignal_up").attr('disabled', true);
	 		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {


	var element = document.getElementById("selectUnselect");

	 if (element.innerHTML == " "){
		 element.innerHTML = "Unselect All";
	 }
	 if (element.innerHTML == "Unselect All"){
		 element.innerHTML = "Select All";
	 }
	  if (element.innerHTML == "Select All"){
		 element.innerHTML = "Unselect All";
	 }
	 
	
	var dataSite = lst;
	
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
            $("#excellent_up").prop('checked',true);  
            $("#excellent_up").attr('disabled', false);
        }else {
        	
        	$("#excellent_up").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }

        if (GoodListIndex.length > 0  ){  
        	AddUpSpeedMarkers(Good_list,map,"#0080FF");
            $("#good_up").prop('checked',true);  
            $("#good_up").attr('disabled', false);
        }else {
        	$("#good_up").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }
        
        if (FairListIndex.length > 0  ){  
        	AddUpSpeedMarkers(Fair_list,map,"#FFFF00");
            $("#fair_up").prop('checked',true);  
        	$("#fair_up").attr('disabled', false);
        }else {
        	$("#fair_up").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }
        
        if (BadListIndex.length > 0  ){  
        	AddUpSpeedMarkers(Bad_list,map,"#4D0207");
            $("#bad_up").prop('checked',true);  
        	$("#bad_up").attr('disabled', false);
        }else {
        	$("#bad_up").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }
        
        if (ExBadListIndex.length > 0  ){  
        	AddUpSpeedMarkers(ExBad_list,map,"#FF0000");
            $("#nosignal_up").prop('checked',true);  
        	$("#nosignal_up").attr('disabled', false);
        } else {
        	$("#nosignal_up").attr('disabled', true);
        	element.innerHTML = "Unselect All";
        }  
	    	
	    	var Total=ExBadListIndex.length+ExcellentListIndex.length+GoodListIndex.length+FairListIndex.length+BadListIndex.length;
	    	
	    	var average = ((ExBadListIndex.length*1)+(BadListIndex.length*2)+(FairListIndex.length*3)+(GoodListIndex.length*4)+(ExcellentListIndex.length*5))/Total;
	    	
	    	if(average >= 1 && average <2) {
	    		dot= "uploadRed";
	    	}else if(average >=2 && average < 3) {
	    		dot="uploadDarkRed";
	    	}else if(average >=3 && average < 4) {
	    		dot="uploadYellow";
	    	}else if(average >= 4 && average <4.5) {
	    		dot="uploadBlue";
	    	}else if(average >= 4.5) {
	    		dot="uploadGreen";
	    	}
	    	var averagePercent =((average /5)*100).toFixed();

	    	$("#tableSpeedCoverage").append('<table id="upLoadLegend1">'
	    			+'<tr><td style="position: relative;top:-30px;left:20px"><label style="color:#08526d;font-weight:bold;font-size:1.80ex;" >Count:</td></tr>'
	    			+'<tr>'
	    			+'<td style="position: relative;top:-37px;left:20px;"><div class="uploadGreen"></div></td><td style="position: relative;top:-37px;left:-5px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+ExcellentListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:10px;"><div  class="uploadBlue"></div></td><td style="position: relative;top:-37px;left:10px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+GoodListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:20px;"><div  class="uploadYellow"></div></td><td style="position: relative;top:-37px;left:20px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+FairListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:35px;"><div  class="uploadDarkRed"></div></td><td style="position: relative;top:-37px;left:35px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+BadListIndex.length+'</label></td>'
	    			+'<td style="position: relative;top:-37px;left:43px;"><div  class="uploadRed"></div></td><td style="position: relative;top:-37px;left:46px;"><label style="color:black;font-weight:bold;font-size:1.6ex;" >'+ExBadListIndex.length+'</label></td>'
	    			+'</tr>'
	    			);
	    	$("#tableSpeedCoverage").append('<table id="upLoadLegend2">'
	    			
	    			+'<tr><td style="position: relative;top:-37px;left:15px;"><label style="color:#08526d;font-weight:bold;font-size:1.80ex;" >Network Average Upload Speed</label></td>'
	    				+'<td style="position: relative;top:-40px;left:15px;"><div id="greenDot" class="'+dot+'"></div></td>'
	    				+'<td style="position: relative;top:-37px; left:15px;"><label style="color:black;font-weight:bold;font-size:1.80ex; " >'+averagePercent+'%</label></td></tr>'
	    				+'</table>'
	    			);
	    	
	    	
	    	
	    	$("#excellent_up").change(function() {

	    		ShowHideMarkers('#006400',"upload");
	    	
	    		
	    		});
	    	$("#good_up").change(function() {

	    		ShowHideMarkers('#0080FF',"upload");
	    		
	    		});
	    	$("#fair_up").change(function() {

	    		ShowHideMarkers('#FFFF00',"upload");
	    		
	    		});
	    	$("#bad_up").change(function() {

	    		ShowHideMarkers('#4D0207',"upload");
	    		
	    		});
	    	$("#nosignal_up").change(function() {

	    		ShowHideMarkers('#FF0000',"upload");
	    		
	    		});
	    		}



    	}
    	
	

function defaultLegend(){

	document.getElementById("tableSpeedCoverage").innerHTML ="";
 	document.getElementById("tableSpeedCoverage").innerHTML =divTablee;
 	
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
				
				$('#tableSpeedCoverage input:checkbox[value="' + colors[l] + '"]').prop("checked", false);

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


function DeleteMarkers(type) {

	if(type=="signal") {
		markerIds=markerIdsSignal;
		
	}else if(type=="download") {
		markerIds=markerIdsDownload;
	
	}else if(type=="upload") {
		markerIds=markerIdsUpload;

	}
	
	for(l=0;l<markerIds.length;l++) {
		
		if(type=="signal") {
			markerGroups[markerIds[l]]=markerGroupsSignal[markerIds[l]];
		}else if(type=="download") {
			markerGroups[markerIds[l]]=markerGroupsDownload[markerIds[l]];
		}else if(type=="upload") {
			markerGroups[markerIds[l]]=markerGroupsUpload[markerIds[l]];
		}
			for (var i = 0; i < markerGroups[markerIds[l]].length; i++) {
			 	
			 	var marker = markerGroups[markerIds[l]][i];


			 
			 	  marker.setMap(null);
			 	
			 }
		}
  	 
}


function UnselectAll(){

  $("input[name='legendCheckbox']").prop('checked',false);
	
}




function ShowHideMarkers(color,type) {
	
	if(type=="signal") {
		markerGroupscolor[color]=markerGroupscolorSignal[color];
	}else if(type=="download") {
		markerGroupscolor[color]=markerGroupscolorDownload[color];
	}else if(type=="upload") {
		markerGroupscolor[color]=markerGroupscolorUpload[color];
	}
	
	for ( x = 0; x < markerGroupscolor[color].length; x++) {
	
	var marker = markerGroupscolor[color][x];	
	
	
	
		
	
	
	//Show markers using checkboxes in popup
	if (!marker.getVisible()) {
	  marker.setVisible(true);

	}
	//hide markers using checkboxes in popup
	else {
	
        if (infowindow) {
            infowindow.close();
        }
           
	marker.setVisible(false); 
	}
	

}

}
