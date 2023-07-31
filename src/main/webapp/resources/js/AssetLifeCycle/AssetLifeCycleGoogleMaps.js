//Add legend button to show/hide the legend popup under zoom control on map
function LegendControl(controlDiv, map) {

	const controlUI = document.createElement("dv");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"><i class="fas fa-arrow-right fa-lg "></i></button>';
    controlUI.title = "Show/Hide Legend";
    controlDiv.appendChild(controlUI);

     controlUI.addEventListener("click", () => {
    	 $("#legendDiv").toggle();
    });    
}
var infoWindow ;
var infoWindow2 ;
//Add defaultZoom button under zoom control on map
function DefaultZoomControl(controlDivDefZoom, map) {
	
    const controlUIDefZoom = document.createElement("div");
    controlUIDefZoom.style.backgroundColor = "white";
    controlUIDefZoom.style.border = "8px solid white";
    controlUIDefZoom.style.cursor = "pointer";
    controlUIDefZoom.style.marginLeft = "10px";
    controlUIDefZoom.style.marginTop = "10px";
    controlUIDefZoom.innerHTML = '<button style="border:none;outline:none; background:white;" ><i class="fa fa-undo fa-lg "></i></button>';
    controlUIDefZoom.title = "Reset Default Zoom";
    controlDivDefZoom.appendChild(controlUIDefZoom);

    controlUIDefZoom.addEventListener("click", () => {

    	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
        map.setCenter(Nairobi);
        map.setZoom(6);
        
        if (infoWindow) {
            infoWindow.close();
        }  
        if (infoWindow2) {
            infoWindow2.close();
        }       
        
              });

  }
var alcBlcQtyData =[];
function alcBalanceValSetColor(lst,map) {
	alcBlcQtyData =[];
	if (lst.length == 0) {
		$("#firstValRange").attr('disabled', true);
	    $("#secondValRange").attr('disabled', true);
	    $("#thirdValRange").attr('disabled', true);
	    $("#fourthValRange").attr('disabled', true);
	 	$("#fifthValRange").attr('disabled', true);

	 	var element = document.getElementById("selectUnselect");
	 	if (element.innerHTML == "Unselect All"){
		 	element.innerHTML = " ";
		 }
	}
	else {
		alcBlcQtyData =lst;
		$("#firstValRange").attr('disabled', false);
	    $("#secondValRange").attr('disabled', false);
	    $("#thirdValRange").attr('disabled', false);
	    $("#fourthValRange").attr('disabled', false);
	 	$("#fifthValRange").attr('disabled', false);

	 	var element = document.getElementById("selectUnselect");
		if (element.innerHTML == " "){
			element.innerHTML = "Unselect All";
		}

		var dataSite = lst;
		//disableData = lst;
		firstValRangeListIndex=[];
		secondValRangeListIndex=[];
		thirdValRangeListIndex=[];
		fourthValRangeListIndex=[];
		fifthValRangeListIndex=[];

		firstValRange=[];
		secondValRange=[];
		thirdValRange=[];
		fourthValRange=[];
		fifthValRange=[];


// add the list index arrays for balance value ranges 
for (j=0;j<dataSite.length;j++){
    if (dataSite[j][3] >= parseFloat(0) && dataSite[j][3] <= parseFloat(4999)) {
    	firstValRangeListIndex.push(j);
    	firstValRange.push(dataSite[j]);
    }
    else if (dataSite[j][3] >=parseFloat(5000) && dataSite[j][3] <= parseFloat(19999)  ) {
    	secondValRangeListIndex.push(j);
    	secondValRange.push(dataSite[j]);
    }
    else if (dataSite[j][3] >=parseFloat(20000) && dataSite[j][3] <= parseFloat(29999)) {
    	thirdValRangeListIndex.push(j);
    	thirdValRange.push(dataSite[j]);
    }
    else if (dataSite[j][3] >=parseFloat(30000) && dataSite[j][3] <= parseFloat(49999) ) {
    	fourthValRangeListIndex.push(j);
    	fourthValRange.push(dataSite[j]);
    }
    else if (dataSite[j][3] >=parseFloat(50000) ) {
    	fifthValRangeListIndex.push(j);
    	fifthValRange.push(dataSite[j]);
    }

}
if (firstValRangeListIndex.length > 0  ){
	AddBalanceMarker(firstValRange,map,"#FFDC00","val");
    $("#firstValRange").prop('checked',true);  
}
if (secondValRangeListIndex.length > 0  ){  
	AddBalanceMarker(secondValRange,map,"#0080FF","val");
    $("#secondValRange").prop('checked',true);  
}
if (thirdValRangeListIndex.length > 0  ){  
	AddBalanceMarker(thirdValRange,map,"red","val");
    $("#thirdValRange").prop('checked',true);  
}
if (fourthValRangeListIndex.length > 0  ){
	AddBalanceMarker(fourthValRange,map,"#FF00FF","val");
    $("#fourthValRange").prop('checked',true);  
}
if (fifthValRangeListIndex.length > 0  ){  
	AddBalanceMarker(fifthValRange,map,"#8A2BE2","val");
     $("#fifthValRange").prop('checked',true);  
}

//Disable the checkboxes 
if(firstValRangeListIndex.length ==0) {
	$("#firstValRange").attr('disabled', true);
}
if(secondValRangeListIndex.length ==0) {
	$("#secondValRange").attr('disabled', true);
}
if(thirdValRangeListIndex.length ==0) {
	$("#thirdValRange").attr('disabled', true);
}
if(fourthValRangeListIndex.length ==0) {
	$("#fourthValRange").attr('disabled', true);
}
if(fifthValRangeListIndex.length ==0) {
	$("#fifthValRange").attr('disabled', true);
}

	}
	
} // end of set color function 

function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
var markerGroups = {
		  "green": [],
		  "red": [],
		 "#FF8C00": [],
		  "#0080FF": [],
		  "#FFDC00": [],
		  "#4D0207": [],
		  "#FF00FF":[],
		  "#8A2BE2":[]

		};
var yellowCluster;
var blueCluster;
var redCluster;
var pinkCluster;
var purpleCluster;

markers=[];
function AddBalanceMarker(lst,map,color,blcType){

var ctx = getContextPath();
	markers=[];
	//Set zoom level
	map.setZoom(6);

		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);	
		map.setCenter(Nairobi);
		
		for(i=0;i<lst.length;i++){
	     
				markerId=lst[i][0];
				siteID=lst[i][0];
				
				infowindow = new google.maps.InfoWindow();

				var latBalanceSite =lst[i][5];
				var longBalanceSite=lst[i][4];
				
				const position = new google.maps.LatLng(latBalanceSite,longBalanceSite);

				var SiteName=lst[i][1];	
				
				var balSiteName="<b style='font-size:13px;'><u>Site: </u></b>"+SiteName;
				var balSiteLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+latBalanceSite;
				var balSiteLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+longBalanceSite;
				if(blcType=="qty"){
				var balSiteValue="<b style='font-size:13px;'><u>Site Balance Quantity:</u> </b>"+lst[i][2];
				}
				else {
					var balSiteValue="<b style='font-size:13px;'><u>Site Balance Value:</u> </b>"+lst[i][3];

				}
				var data="<div>"+balSiteName+"</br>"+balSiteLat+"</br>"+balSiteLong+"</br>"+balSiteValue+ "</div>";			

				//Define and Add markers on map
				const marker = new google.maps.Marker({
			        position: position,
			        data:data,
			        ID:markerId,
			        Lat:latBalanceSite,
			        Long:longBalanceSite,
			        Name:SiteName,
			        SiteId:siteID,
			        color:color,
			      
			        icon: {
			        	  path: google.maps.SymbolPath.CIRCLE,
					        fillColor: color,
					        fillOpacity: 0.9,
					        strokeColor: 'transparent',
					        strokeOpacity: 0.9,
					        strokeWeight: 1,
					        scale: 7	   
			            
			          }
			        
			         	    });

		 	    
				 	marker.metadata = { id: markerId };
				    markers[color] = marker;
				    markers.push(marker);

					//Add markers to array depending on colors
				    if (!markerGroups[color]) markerGroups[color] = [];
						  markerGroups[color].push(marker);
	 
					
				google.maps.event.addListener(marker, "click", function (e) {

				     	infowindow.setContent(this.data); 
			        	infowindow.open(map,this);

						var LatSelected=this.Lat;
						var LongSelected=this.Long;
						var NameSelected=this.Name;
						var IDSelected=this.ID;
						
						
						
				 });
				google.maps.event.addListener(marker, "dblclick", function (e) {

			     	map.setZoom(15);
			     	map.setCenter(position);

			 	});
	 			
		  	}

			//Define clusters
		 if (color=="#FFDC00"){
		 	yellowCluster = new MarkerClusterer(map,markerGroups["#FFDC00"], {ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url: ctx+'/resources/clusterIcons/yellowCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
		 	},
		 	], });
		 	}
		 	
		 	else if (color=="#0080FF"){
		 	
		 	blueCluster = new MarkerClusterer(map,markerGroups["#0080FF"], {ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url: ctx+'/resources/clusterIcons/blueCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
			},
			], });
			}
			
			else if (color=="red"){
				redCluster = new MarkerClusterer(map,markerGroups["red"], {ignoreHiddenMarkers: true, 
				styles: [
				{
				url: ctx+'/resources/clusterIcons/redCluster.png',
				height: 60,
				width:60,
				anchorText:[-3,-3]
				},
				], });
				
				}
			
				else if (color=="#FF00FF"){
					pinkCluster = new MarkerClusterer(map,markerGroups["#FF00FF"], {ignoreHiddenMarkers: true, 
					styles: [
					{
					url: ctx+'/resources/clusterIcons/pinkCluster.png',
					height: 60,
					width:60,
					anchorText:[-3,-3]
					},
					], });
		      }


			else if (color=="#8A2BE2"){
				 purpleCluster = new MarkerClusterer(map,markerGroups["#8A2BE2"], {ignoreHiddenMarkers: true, 
					    styles: [
					        {
					          url: ctx+'/resources/clusterIcons/purpleCluster.png',
					          height: 60,
					          width:60,
					          anchorText:[-3,-3]
					        },
					      ], });
		      }
} // end of AddAgentMarker function

function HideClusterIcon(MarkersArray,color) {
	if(color == "#FFDC00") {
	 	for  ( i = 0; i<markerGroups["#FFDC00"].length; i++) {	
	    	//Remove yellow markers from map			
	        markerGroups["#FFDC00"][i].setMap(null);
	   }
		yellowCluster.clearMarkers();
	    markerGroups["#FFDC00"] =[];
	    color == "" ;  
	}
	else if (color == "#0080FF") {
		for(j = 0; j<markerGroups["#0080FF"].length; j++) {	
	    	markerGroups["#0080FF"][j].setMap(null);
		}
		blueCluster.clearMarkers();
	    markerGroups["#0080FF"] =[];
	    color == "";
	}
	else if (color == "red") {
		for(r = 0; r<markerGroups["red"].length; r++) {	
			markerGroups["red"][r].setMap(null);
	    }
	    redCluster.clearMarkers();
	    markerGroups["red"] =[];
	}
	else if (color == "#FF00FF") {
		for(n = 0; n<markerGroups["#FF00FF"].length; n++) {
			markerGroups["#FF00FF"][n].setMap(null);
	    }
	    pinkCluster.clearMarkers();
	    markerGroups["#FF00FF"] =[];
	    color == "";
	}
	else if (color == "#8A2BE2") {
		for(b = 0; b<markerGroups["#8A2BE2"].length; b++) {	
	    	markerGroups["#8A2BE2"][b].setMap(null);
	  	}
	  purpleCluster.clearMarkers();
	  markerGroups["#8A2BE2"] =[];
	  color == "";    
	}
}
function ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,color,type) {

var Nairobi=new google.maps.LatLng(-1.286389,36.817223);


document.getElementById("mapContainer").innerHTML ="";
var map = new google.maps.Map(document.getElementById("mapContainer"), {

                 mapTypeControl: false,
                 center:Nairobi ,
                 zoom: 6,
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


	if (color=="#FFDC00"){
		AddBalanceMarker(firstValRange,map,"#FFDC00",type); 
        if (markerGroups["#0080FF"].length>0  ){
        	HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
        	AddBalanceMarker(secondValRange,map,"#0080FF",type);
           }
        if (markerGroups["red"].length>0  ){
            HideClusterIcon(markerGroups["red"],"red");
            AddBalanceMarker(thirdValRange,map,"red",type);
         }
        if (markerGroups["#FF00FF"].length>0  ){
            HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
            AddBalanceMarker(fourthValRange,map,"#FF00FF",type);
      	}
      	if (markerGroups["#8A2BE2"].length>0  ){
			HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
			AddBalanceMarker(fifthValRange,map,"#8A2BE2",type);
        }
      }
    else if (color=="#0080FF"){
    	AddBalanceMarker(secondValRange,map,"#0080FF",type);
    	if (markerGroups["#FFDC00"].length>0  ){
    		HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
    		AddBalanceMarker(firstValRange,map,"#FFDC00",type);
        }                             
    	if (markerGroups["red"].length>0  ){
        	HideClusterIcon(markerGroups["red"],"red");
            AddBalanceMarker(thirdValRange,map,"red",type);
        }                             
    	if (markerGroups["#FF00FF"].length>0  ){
            HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
            AddBalanceMarker(fourthValRange,map,"#FF00FF",type);
        }                            
    	if (markerGroups["#8A2BE2"].length>0  ){
            HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
            AddBalanceMarker(fifthValRange,map,"#8A2BE2",type);
        }                              
                                  
     }
    else if (color=="red"){
    	AddBalanceMarker(thirdValRange,map,"red",type); 
    	if (markerGroups["#FFDC00"].length>0  ){
           HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
           AddBalanceMarker(firstValRange,map,"#FFDC00",type);
        }
    	if (markerGroups["#0080FF"].length>0  ){
            HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
            AddBalanceMarker(secondValRange,map,"#0080FF",type); 
       	}                                   
    	if (markerGroups["#FF00FF"].length>0  ){
            HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
            AddBalanceMarker(fourthValRange,map,"#FF00FF",type);
        }                                  
                                        
    	if (markerGroups["#8A2BE2"].length>0  ){
            HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
            AddBalanceMarker(fifthValRange,map,"#8A2BE2",type);
        }
      }
    else if (color=="#FF00FF"){
        AddBalanceMarker(fourthValRange,map,"#FF00FF",type);
        if (markerGroups["#FFDC00"].length>0  ){
           HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
           AddBalanceMarker(firstValRange,map,"#FFDC00",type);
        }                                    
        if (markerGroups["#0080FF"].length>0  ){
            HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
            AddBalanceMarker(secondValRange,map,"#0080FF",type); 
      	}
      	if (markerGroups["red"].length>0  ){
          	HideClusterIcon(markerGroups["red"],"red");
            AddBalanceMarker(thirdValRange,map,"red",type);
       }
                                        
      if (markerGroups["#8A2BE2"].length>0  ){
            HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
            AddBalanceMarker(fifthValRange,map,"#8A2BE2",type);
       }
    }
    else if (color=="#8A2BE2"){
        AddBalanceMarker(fifthValRange,map,"#8A2BE2",type);
        if (markerGroups["#FFDC00"].length>0  ){
            HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
            AddBalanceMarker(firstValRange,map,"#FFDC00",type);
        }                                   
        if (markerGroups["#0080FF"].length>0  ){
            HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
            AddBalanceMarker(secondValRange,map,"#0080FF",type); 
      }                                  
        if (markerGroups["#FF00FF"].length>0  ){
            HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
            AddBalanceMarker(fourthValRange,map,"#FF00FF",type);
        }                                  
        if (markerGroups["red"].length>0  ){
            HideClusterIcon(markerGroups["red"],"red");
            AddBalanceMarker(thirdValRange,map,"red",type);
         }
    }
                             
        //Add legend button under zoom control on map
		const centerControlDiv = document.createElement("div");
	    LegendControl(centerControlDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

}
function SelectUnselectAllBlcQty() {

checkedColorr=[];
	
	if(firstValRangeListIndex.length >0) {
		checkedColorr.push("#FFDC00");
	}
	if(secondValRangeListIndex.length  >0) {
		checkedColorr.push("#0080FF");
	}
	if(thirdValRangeListIndex.length  >0) {
		checkedColorr.push("red");
	}
	if(fourthValRangeListIndex.length  >0) {
		checkedColorr.push("#FF00FF");
	}

	var element = document.getElementById("selectUnselectBlcQty");

	if (element.innerHTML == "Unselect All"){
		element.innerHTML = "Select All";
			 
		//Add checked checkboxes to colors array
		var checkboxes = document.querySelectorAll('input[name="blcQtyCheckbox"]:checked');
			colors = [];
			checkboxes.forEach((checkbox) => {
			colors.push(checkbox.value);
		});
			
		console.log("colors"+colors);
		//Uncheck all checkboxes by value (by color)
		for (var l= 0; l < colors.length; l++) {
				$('input:checkbox[value="' + colors[l] + '"]').prop("checked", false);
		}

 for (var l= 0; l < colors.length; l++) {
	//Hide yellow cluster icon
	if(colors[l] == "#FFDC00") {
		if  ($('#yellow').prop("checked") == false) {
			HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
		}
	}
	//Hide blue cluster icon
	else if(colors[l] == "#0080FF") {
		if  ($('#blue').prop("checked") == false) {
			HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
		}
	}

	else if(colors[l] == "red") {
		if  ($('#red').prop("checked") == false) {
			HideClusterIcon(markerGroups["red"],"red");
		}
	}
	else if(colors[l] == "#FF00FF") {
		if  ($('#pink').prop("checked") == false) {
			HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
		}
	}
		  
	}
	}
	else {
		element.innerHTML = "Unselect All";
		var balanceValRangeChecked = document.querySelectorAll('input[name="blcQtyCheckbox"]:checked');
			
		blcValRnge = [];
		balanceValRangeChecked.forEach((checkbox) => {
			blcValRnge.push(checkbox.value);
		});


	if(blcValRnge.length >0){
		for(o=0;o<blcValRnge.length;o++){
			
			
	 if(blcValRnge[o] == "#FFDC00") {
		HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
			}	
	  else if(blcValRnge[o] == "#0080FF") {
			  HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
		  }
	  else if(blcValRnge[o] == "red") {
			  HideClusterIcon(markerGroups["red"],"red");
		  }

	  else if(blcValRnge[o] == "#FF00FF") {
			  HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
		  }
	  else if(blcValRnge[o] == "#8A2BE2") {
			  HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
		  }
	  
	  }
	}
	
	 for (var l= 0; l < checkedColorr.length; l++) {
			$('input:checkbox[value="' + checkedColorr[l] + '"]').prop("checked", true);
	}

	 var checkboxes = document.querySelectorAll('input[name="blcQtyCheckbox"]:checked');
			colors = [];
			checkboxes.forEach((checkbox) => {
			colors.push(checkbox.value);
	});
	for (var l= 0; l < checkedColorr.length; l++) {
		//Show yellow cluster icon
		if(checkedColorr[l] == "#FFDC00") {
			if ($('#yellow').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FFDC00","qty");
			}
		}
		//Show blue cluster icon
		else if(checkedColorr[l] == "#0080FF") {
			if ($('#blue').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#0080FF","qty");
			}
		}
		else if(checkedColorr[l] == "red") {
			if ($('#red').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"red","qty");
			}
		}
		else if(checkedColorr[l] == "#FF00FF") {
			if ($('#pink').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FF00FF","qty");
			}
		}
	
				
	}

}
	
}

function SelectUnselectAll() {

	checkedColorr=[];
	
	if(firstValRangeListIndex.length >0) {
		checkedColorr.push("#FFDC00");
	}
	if(secondValRangeListIndex.length  >0) {
		checkedColorr.push("#0080FF");
	}
	if(thirdValRangeListIndex.length  >0) {
		checkedColorr.push("red");
	}
	if(fourthValRangeListIndex.length  >0) {
		checkedColorr.push("#FF00FF");
	}
	if(fifthValRangeListIndex.length  >0) {
		checkedColorr.push("#8A2BE2");
	}

	var element = document.getElementById("selectUnselect");

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

 for (var l= 0; l < colors.length; l++) {
	//Hide yellow cluster icon
	if(colors[l] == "#FFDC00") {
		if  ($('#firstValRange').prop("checked") == false) {
			HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
		}
	}
	//Hide blue cluster icon
	else if(colors[l] == "#0080FF") {
		if  ($('#secondValRange').prop("checked") == false) {
			HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
		}
	}

	else if(colors[l] == "red") {
		if  ($('#thirdValRange').prop("checked") == false) {
			HideClusterIcon(markerGroups["red"],"red");
		}
	}
	else if(colors[l] == "#FF00FF") {
		if  ($('#fourthValRange').prop("checked") == false) {
			HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
		}
	}
	else if(colors[l] == "#8A2BE2") {
		if  ($('#fifthValRange').prop("checked") == false) {
			HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
		}
	}
			  
	}
 
	}
	else {
		element.innerHTML = "Unselect All";
		var balanceValRangeChecked = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			
		blcValRnge = [];
		balanceValRangeChecked.forEach((checkbox) => {
			blcValRnge.push(checkbox.value);
		});


	if(blcValRnge.length >0){
		for(o=0;o<blcValRnge.length;o++){
			
			
	 if(blcValRnge[o] == "#FFDC00") {
		HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
			}	
	  else if(blcValRnge[o] == "#0080FF") {
			  HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
		  }
	  else if(blcValRnge[o] == "red") {
			  HideClusterIcon(markerGroups["red"],"red");
		  }

	  else if(blcValRnge[o] == "#FF00FF") {
			  HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
		  }
	  else if(blcValRnge[o] == "#8A2BE2") {
			  HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
		  }
	  
	  }
	}
	
	 for (var l= 0; l < checkedColorr.length; l++) {
			$('input:checkbox[value="' + checkedColorr[l] + '"]').prop("checked", true);
	}

	 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			colors = [];
			checkboxes.forEach((checkbox) => {
			colors.push(checkbox.value);
	});
	for (var l= 0; l < checkedColorr.length; l++) {
		//Show yellow cluster icon
		if(checkedColorr[l] == "#FFDC00") {
			if ($('#firstValRange').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FFDC00","val");
			}
		}
		//Show blue cluster icon
		else if(checkedColorr[l] == "#0080FF") {
			if ($('#secondValRange').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#0080FF","val");
			}
		}
		else if(checkedColorr[l] == "red") {
			if ($('#thirdValRange').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"red","val");
			}
		}
		else if(checkedColorr[l] == "#FF00FF") {
			if ($('#fourthValRange').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FF00FF","val");
			}
		}
		else if(checkedColorr[l] == "#8A2BE2") {
			if ($('#fifthValRange').prop("checked") == true) {
				ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#8A2BE2","val");
			}
		}
				
	}

}
}

function ShowHideMarkers(color) {

// Show/Hide ClusterIcon
if(color == "#FFDC00") {
	if ($('#firstValRange').prop("checked") == true) {
		ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FFDC00","val");
	}
	else {
		HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
  	}	
}
if(color == "#0080FF") {
	if ($('#secondValRange').prop("checked") == true) {
		ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#0080FF","val");
	}
	else {
		HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
  	}	
}
if(color == "red") {
	if ($('#thirdValRange').prop("checked") == true) {
		ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"red","val");
	}
	else {
		HideClusterIcon(markerGroups["red"],"red");
  	}	
}
if(color == "#FF00FF") {
	if ($('#fourthValRange').prop("checked") == true) {
		ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FF00FF","val");
	}
	else {
		HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
 	}	
}
if(color == "#8A2BE2") {
	if ($('#fifthValRange').prop("checked") == true) {
		ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#8A2BE2","val");
	}
	else {
		HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
  	}	
}
	
}// end showhidemarkers fct

function ShowHideBlcQtyMarkers(color) {
		console.log("COLOR IS"+color);
	// Show/Hide ClusterIcon
	if(color == "yellow") {
		if ($('#yellow').prop("checked") == true) {
			ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FFDC00","qty");
		}
		else {
			HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
	  	}	
	}
	if(color == "blue") {
		if ($('#blue').prop("checked") == true) {
			ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#0080FF","qty");
		}
		else {
			HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
	  	}	
	}
	if(color == "red") {
		if ($('#red').prop("checked") == true) {
			ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"red","qty");
		}
		else {
			HideClusterIcon(markerGroups["red"],"red");
	  	}	
	}
	if(color == "pink") {
		if ($('#pink').prop("checked") == true) {
			ShowClusterIcon(firstValRange,secondValRange,thirdValRange,fourthValRange,fifthValRange,"#FF00FF","qty");
		}
		else {
			HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
	 	}	
	}

		
	}// end showhidemarkers fct
function alcBalanceQtySetColor(blcQtyData,map){
    DeleteMarkers();

    if (blcQtyData.length == 0) {
        alert('No data to apply changes on it');

    }

    else {

    	firstValRangeListIndex=[];
		secondValRangeListIndex=[];
		thirdValRangeListIndex=[];
		fourthValRangeListIndex=[];
		fifthValRangeListIndex=[];

		firstValRange=[];
		secondValRange=[];
		thirdValRange=[];
		fourthValRange=[];
		fifthValRange=[];

	$("input[name='blcQtyCheckbox']").prop('checked',false);
        
     var dataSite = blcQtyData;
     var listBlcQtyColor= ["yellow","blue","red","pink"];
     var listBlcQtyHexColor= ["#FFDC00","#0080FF","red","#FF00FF"];
     var listBlcQtyFromRange =["0","5","20","50"];
     var listBlcQtyToRange =["- 4","- 19","- 49","& Above"];

     
        newTableDiv =  document.getElementById("tableDiv").innerHTML;
        
        //clear legend div content & add the new content
        document.getElementById("legendHeader").innerHTML ="";
        document.getElementById("legendHeader").innerHTML='<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6> <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAllBlcQty()"  id="selectUnselectBlcQty" >Unselect All</button>';
        
        document.getElementById("tableDiv").innerHTML ="";
        document.getElementById("tableDiv").innerHTML='<table id="mytable"><h5 style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:10px;left:20px;">Sites Balance Quantity</h5><tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th></tr></table>';

			//Redraw the legend of blc qty 
              for(var x=0;x<4;x++) {

                  var markup = "<tr> <td style='position: relative;top:17px;left:40px;'><input style='position: relative;top: -10px;' type='checkbox' name='blcQtyCheckbox' id='"+listBlcQtyColor[x]+"'  value='"+listBlcQtyHexColor[x]+"' onclick='ShowHideBlcQtyMarkers(id)' /></td>"
                      +"<td style='position: relative;top:8px;left:60px;'><div  class='dot "  +listBlcQtyColor[x]+ "'></div></td>"
                      +"<td style='position: relative;top: 8px;left:70px;'><label style='color:black;font-weight:bold;font-size:2ex;' > "+listBlcQtyFromRange[x]+ " " +listBlcQtyToRange[x]+ "</label></td></tr>";
                
                	$("#mytable").append(markup);
                    
              }
             newTableDiv =document.getElementById("tableDiv").innerHTML;

          // add the list index arrays for balance qty ranges 
             for (j=0;j<dataSite.length;j++){
                 if (dataSite[j][2] >= parseFloat(0) && dataSite[j][2] <= parseFloat(4)) {
                 	firstValRangeListIndex.push(j);
                 	firstValRange.push(dataSite[j]);
                 }
                 else if (dataSite[j][2] >=parseFloat(5) && dataSite[j][2] <= parseFloat(19)  ) {
                 	secondValRangeListIndex.push(j);
                 	secondValRange.push(dataSite[j]);
                 }
                 else if (dataSite[j][2] >=parseFloat(20) && dataSite[j][2] <= parseFloat(49)) {
                 	thirdValRangeListIndex.push(j);
                 	thirdValRange.push(dataSite[j]);
                 }
                 else if (dataSite[j][2] >=parseFloat(50)) {
                 	fourthValRangeListIndex.push(j);
                 	fourthValRange.push(dataSite[j]);
                 }
                 
             }
             if (firstValRangeListIndex.length > 0  ){
            		AddBalanceMarker(firstValRange,map,"#FFDC00","qty");
            	    $("#yellow").prop('checked',true);  
            	}
            	if (secondValRangeListIndex.length > 0  ){  
            		AddBalanceMarker(secondValRange,map,"#0080FF","qty");
            	    $("#blue").prop('checked',true);  
            	}
            	if (thirdValRangeListIndex.length > 0  ){  
            		AddBalanceMarker(thirdValRange,map,"red","qty");
            	    $("#red").prop('checked',true);  
            	}
            	if (fourthValRangeListIndex.length > 0  ){
            		AddBalanceMarker(fourthValRange,map,"#FF00FF","qty");
            	    $("#pink").prop('checked',true);  
            	}
            	
            	//Disable the checkboxes 
            	if(firstValRangeListIndex.length ==0) {
            		$("#yellow").attr('disabled', true);
            	}
            	if(secondValRangeListIndex.length ==0) {
            		$("#blue").attr('disabled', true);
            	}
            	if(thirdValRangeListIndex.length ==0) {
            		$("#red").attr('disabled', true);
            	}
            	if(fourthValRangeListIndex.length ==0) {
            		$("#pink").attr('disabled', true);
            	}
            	
             
    }   
}
function DeleteMarkers() {
	if( markers.length >0){
		for  (var i = 0; i < markers.length; i++) {
			var marker = markers[i];
			marker.setMap(null);
		}
	markerGroups = {
			"red": [],
			"#FF8C00": [],
			"#0080FF": [],
			"#FFDC00": [],
			"#4D0207": [],
			"#FF00FF":[],
			"#8A2BE2":[]
	};
	}
}
function defaultLegend(){

	document.getElementById("tableDiv").innerHTML ="";
 	document.getElementById("tableDiv").innerHTML =divTablee;
 	
 	document.getElementById("legendHeader").innerHTML ="";
	document.getElementById("legendHeader").innerHTML='<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6> <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAll()"  id="selectUnselect" >Unselect All</button>';
	
}
	