var selectedItems=[];
var txt;
var AllRegionsArr=[];
var checked;


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
  
  
  
  
  var infoWindow ;
function MaxRevenueControl(controlDiv3, map) {
	
	 const controlUI3 = document.createElement("div");
	 controlUI3.style.backgroundColor = "white";
	 controlUI3.style.border = "8px solid white";
	 controlUI3.style.boxShadow = "0 5px 6px rgba(0,0,0,.3)";
	 controlUI3.style.cursor = "pointer";
	 controlUI3.style.marginTop = "11px";
	 controlUI3.style.marginLeft = "5px";
	 controlUI3.style.height = "38px";
	 controlUI3.title = "Go to max revenue region";
	 controlDiv3.appendChild(controlUI3);


    const controlText = document.createElement("div");
    controlText.style.color = "rgb(25,25,25)";
    controlText.style.fontFamily = "Roboto,Arial,sans-serif";
    controlText.style.fontSize = "2px";
    controlText.style.lineHeight = "3px";
    controlText.style.paddingLeft = "5px";
    controlText.style.paddingRight = "5px";
    controlUI3.innerHTML = '<button style="border:none;outline:none; background:white;fontFamily:Roboto,Arial,sans-serif;font-Size:15px" >Maximum Revenue</button>';
    controlUI3.appendChild(controlText);
    
    controlUI3.addEventListener("click", () => {

    	 if (infoWindow) {
             infoWindow.close();
         }  
		
		if(!markerGroups["green"]) alert("Please check the Maximum Revenue inside the legend");
		
		else{
		
        if(maxRev.length >0) {            
       
            var Path =markerGroups["green"][0];
	   	 regionPool = [];
	      var bounds ;
		      latLngArray = markerGroups["green"][0].latLngs.Be[0].Be;
          
		      bounds = new google.maps.LatLngBounds();
		      
			//Arrays of lat and lng of our region.
					
			$.each(latLngArray, function( i, v ) {
			regionPool.push(new google.maps.LatLng(latLngArray[i].lat(),latLngArray[i].lng()));
            bounds.extend(regionPool[regionPool.length-1]);
			});
           
		let contentString =
		    "<b>Region Name</b> : <b> "+maxRev[0]+"</b><br>";
		   contentString += "<br><b>Total revenue</b>: "+maxRev[2].toFixed(2)+"$<br>"+
		   					"<b>Region central coordinates</b> : <br> Latitude: "+bounds.getCenter().lat().toFixed(4)+
		   					"<br><b>Longitude</b>: "+bounds.getCenter().lng().toFixed(4);
		   

  		 infoWindow = new google.maps.InfoWindow({
  			content :contentString, 
	    		});
  		
  
		  Path.addListener("dblclick", () => {

			     	map.setZoom(15);
			     	map.setCenter(bounds.getCenter());
					$("#region").val(maxRev[0] +";"+maxRev[1]);
						
			 			});
  		   
                
         if  ($('#legendMaxRevenue').is(':checked') == true ) {
             
	  		map.setZoom(6);
	       	map.setCenter(bounds.getCenter());
	      //Set Position of InfoWindow.
	        infoWindow.setPosition(bounds.getCenter());
	        infoWindow.open(map);       	 

		
		  				
  			Path.addListener("mouseout", () => {
  			infoWindow.close({
	    			anchor:bounds.getCenter(),
	    			map,
	    		      shouldFocus: false,
		    			
	    			});
  		  });
         }
         
         
     
    		
        }	
        }	
      });
    
  }

var infoWindow2;
function MinRevenueControl(controlDiv4, map) {
	
    const controlUI4 = document.createElement("div");
    controlUI4.style.backgroundColor = "white";
    controlUI4.style.border = "8px solid white";
   // controlUI4.style.borderRadius = "3px";
    controlUI4.style.boxShadow = "0 5px 6px rgba(0,0,0,.3)";
    controlUI4.style.cursor = "pointer";
    controlUI4.style.marginLeft = "5px";
    controlUI4.style.marginTop = "11px";
    controlUI4.style.height = "38px";
    controlUI4.title = "Go to min revenue region";
    controlDiv4.appendChild(controlUI4);


    const controlText = document.createElement("div");
    controlText.style.color = "rgb(25,25,25)";
    controlText.style.fontFamily = "Roboto,Arial,sans-serif";
    controlText.style.fontSize = "2px";
    controlText.style.lineHeight = "3px";
    controlText.style.paddingLeft = "5px";
    controlText.style.paddingRight = "5px";
    controlUI4.innerHTML = '<button style="border:none;outline:none; background:white;fontFamily:Roboto,Arial,sans-serif;font-Size:15px" >Minimum Revenue</button>';
    controlUI4.appendChild(controlText);
    

    controlUI4.addEventListener("click", () => {
    	 if (infoWindow2) {
             infoWindow2.close();
         }  
        
     if(!markerGroups["#4D0207"][0]) alert("Please check the Minimum Revenue inside the legend");
        
        else{
    	 if(minRev.length >0) {
    	 
    	 	 var Path =markerGroups["#4D0207"][0];
	   	 regionPool = [];
	      var bounds ;
		      latLngArray = markerGroups["#4D0207"][0].latLngs.Be[0].Be;
          
		      bounds = new google.maps.LatLngBounds();
		      
			//Arrays of lat and lng of our region.
					
			$.each(latLngArray, function( i, v ) {
			regionPool.push(new google.maps.LatLng(latLngArray[i].lat(),latLngArray[i].lng()));
            bounds.extend(regionPool[regionPool.length-1]);
			});
           
		let contentString =
		    "<b>Region Name</b> : <b> "+minRev[0]+"</b><br>";
		   contentString += "<br><b>Total revenue</b>: "+minRev[2].toFixed(2)+"$<br>"+
		   					"<b>Region central coordinates</b> : <br> Latitude: "+bounds.getCenter().lat().toFixed(4)+
		   					"<br><b>Longitude</b>: "+bounds.getCenter().lng().toFixed(4);
		   

  		 infoWindow2 = new google.maps.InfoWindow({
  			content :contentString, 
	    		});
  		
  
		  Path.addListener("dblclick", () => {

			     	map.setZoom(15);
			     	map.setCenter(bounds.getCenter());
					$("#region").val(minRev[0] +";"+minRev[1]);
						
			 			});
  		   
                
         if  ($('#legendMinRevenue').is(':checked') == true ) {
             
	  		map.setZoom(6);
	       	map.setCenter(bounds.getCenter());
	      //Set Position of infoWindow2.
	        infoWindow2.setPosition(bounds.getCenter());
	        infoWindow2.open(map);       	 

		
		  				
  			Path.addListener("mouseout", () => {
  			infoWindow2.close({
	    			anchor:bounds.getCenter(),
	    			map,
	    		      shouldFocus: false,
		    			
	    			});
  		  });
         }
         
         
     
    	 }
    	 }
      });
    
  


  }
  
 
function resetToDefault(){

	var rowCount2 = revenueColorTable.rows.length;
		 
 	if ( rowCount2 ==5  && rowCount ==4 ) {
 		revenueColorTable.deleteRow(4);
 	}
 	
	else if ( rowCount2 ==6 && rowCount ==5 ) {
   	 revenueColorTable.deleteRow(5);
   	 revenueColorTable.deleteRow(4);
   	 }
   	 
   	 else if ( rowCount2 >=6 && rowCount >5 ) {
		revenueColorTable.deleteRow(5);
	   	revenueColorTable.deleteRow(4);
	 }
	
	else if ( rowCount2 ==3 || rowCount2 ==2 || rowCount2 ==1) {
		document.getElementById("revTable").innerHTML ="";
		document.getElementById("revTable").innerHTML = divRevTable ;
	}
	
	$('#revenueColorTable').find('input').each(function(){
		var defaultVal = $(this).data('default');
		$(this).val(defaultVal);
		});
	  
	var options = document.querySelectorAll('select option');

	  for (var i = 0; i < options.length; i++) {
	    options[i].selected = options[i].defaultSelected; 	   
	  }
	
	  rowCount =  $("#revenueColorTable tr").length;
	  var element = document.getElementById("alertMsg");
	  element.innerHTML = "";
	
    
      
	  $('#revenueColorTable tr:eq(1)').css('background','#0080FF');
	  $('#revenueColorTable tr:eq(2)').css('background','#FFDC00');
	  $('#revenueColorTable tr:eq(3)').css('background','red');
	  
 
	  selectColorChanges();
	
	}


function selectColorChanges() {

	  $('select').on('change', function () {
	  	if (this.value == "#0080FF" ) {
	  		$(this).closest('tr').css('background-color','#0080FF');
	  	}
	  	else if (this.value == "#FFDC00") {
	  		$(this).closest('tr').css('background-color','#FFDC00');
	  	}
	  	else if (this.value == "red") {
	 	   $(this).closest('tr').css('background-color','red');
	   	}
	   	else if (this.value == "#FF00FF") {
	 		$(this).closest('tr').css('background-color','#FF00FF');
	 	}
	 	else if (this.value == "#8A2BE2") {
	    	$(this).closest('tr').css('background-color','#8A2BE2');
	    }
	    else if (this.value == "-- Select Color --") {
	   		$(this).closest('tr').css('background-color','white');
	    }
	    
	    });
	 }
function applyChanges(){

	$("#enable").prop('checked',true);   
	$("#disable").prop('checked',false); 


	 document.getElementById('regionsAutocomplete').value = "";
     concatenatedRegions ="";
     selectedRegions=[];

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

		//Add legend button under zoom control on map
		const centerControlDiv = document.createElement("div");
	  LegendControl(centerControlDiv, map);
	  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	  const DefaultZoomDiv = document.createElement("div");
	  DefaultZoomControl(DefaultZoomDiv, map);
	  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	  const maxRevControlDiv = document.createElement("div");
	  MaxRevenueControl(maxRevControlDiv, map);
	  map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

	  const minRevControlDiv = document.createElement("div");
	  MinRevenueControl(minRevControlDiv, map);
	  map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
	  
		UnselectAll();
		DeleteMarkers();


		RevenueSetColor(disableData,map);

		
	}





function ShowHideDiv(){
	$("#legendDiv").toggle();
}
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
selectedregions=[];
var concatenatedregions ="";
function AddRegionMarkers(lst,regLatLng,map,color){

var ctx = getContextPath();

	  
	markers=[];
	
	//Set zoom level
	//map.setZoom(6);

		//var Nairobi=new google.maps.LatLng(-1.286389,36.817223);	
		//map.setCenter(Nairobi);
		
		///////// start editing   ///////////////////
		
		   var Path =null;
	   	  var latArray=[],lngArray=[]
	   	 regionPool = [];
	      var bounds ;
	     var polygons = [];
	  listRegions = lst; 

	     
		   	  if(regLatLng.length>0 && listRegions.length>0){
		 	     
		$.each(listRegions, function( key, value ) {
		      regionPool = [];
		      regionArr = [];
		      latArray = [];
		      lngArray = [];
		      regionRevenue = [];
		      
			$.each(regLatLng, function( key2, value2 ) {
				if(listRegions[key][0] == regLatLng[key2][1]){
					latArray.push(parseFloat(jQuery.parseJSON(regLatLng[key2][3]).Lat));
			   		lngArray.push(parseFloat(jQuery.parseJSON(regLatLng[key2][4]).Lng));
   		
	   	  }    	
		});
		      bounds = new google.maps.LatLngBounds();
				
			$.each(latArray, function( i, value2 ) {     //interating among latArray is the same like iterating inside lngArray .
				
			regionPool.push(new google.maps.LatLng(latArray[i],lngArray[i]));
            bounds.extend(regionPool[regionPool.length-1]);
			
				
			});
            
            	
          regionArr.push(listRegions[key][0]);
          regionRevenue.push(listRegions[key][2]);
			console.log("it is my center !!!!! ==> "+bounds.getCenter());
			
			
		Path = new google.maps.Polygon({
		    path: regionPool,
		    strokeColor: color,//'#4986E7',
		    strokeOpacity: 1,
		    strokeWeight: 2,
		    fillColor: color,//'#4986E7',
		    fillOpacity: 0.1,
		    clickable:true,
		    
		  });

	
			polygons.push(Path);

		let contentString =
		    "<b>Region Name</b> : <b> "+[regionArr]+"</b><br>";
		   contentString += "<br><b>Total revenue</b>: "+regionRevenue[0].toFixed(2)+"$<br>"+
		   					"<b>Region central coordinates</b> : <br> Latitude: "+bounds.getCenter().lat().toFixed(4)+
		   					"<br><b>Longitude</b>: "+bounds.getCenter().lng().toFixed(4);
		   
          polygons[polygons.length-1].setMap(map);

          const marker = new google.maps.Marker({
							    position: bounds.getCenter(),
							    map,
							    title:'"'+[regionArr]+'"',
							    visible:false,
							  });
			  
  		const infoWindow = new google.maps.InfoWindow({
  			content :contentString, 
	    		});
  		
  		Path.addListener("click", () => {
  		if (infoWindow) infoWindow.close();
  		
  			infoWindow.open({
	    			anchor:marker,
	    			map,
	    		      shouldFocus: true,
		    			
	    			});
  		  });
  		  
		  Path.addListener("dblclick", () => {

			     	map.setZoom(15);
			     	map.setCenter(bounds.getCenter());
					$("#region").val(jQuery.parseJSON(marker.title) +";"+listRegions[key][1]);
						
			 			});
	 			
		  				
  		Path.addListener("mouseout", () => {
  			infoWindow.close({
	    			anchor:marker,
	    			map,
	    		      shouldFocus: false,
		    			
	    			});
  		  });
  		  
		  //Add markers to array depending on colors
				    if (!markerGroups[color]) markerGroups[color] = [];
						  markerGroups[color].push(Path);
				});
	}
		
		
		//////////////end editing
	/*	for(i=0;i<lst.length;i++){
	     
				markerId=lst[i][1];
				RegionID=lst[i][1];
				
				infowindow = new google.maps.InfoWindow();

				var latRegion=lst[i][2];
				var lngRegion=lst[i][1];
				const position = new google.maps.LatLng(latRegion,lngRegion);

				var regionName=lst[i][0];	
				var RegionRev=lst[i][2];			         
				
				var regionName="<b style='font-size:13px;'><u>Region: </u></b>"+regionName;
				var regionLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+lst[i][2];
				var regionLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lst[i][1];
				var regionRev="<b style='font-size:13px;'><u>Revenue:</u> </b>"+RegionRev;
				
					
				var data="<div>"+regionName+"</br>"+regionLat+"</br>"+regionLong+ "</br>"+regionRev+ "</div>";			

				//Add markers on map
				const marker = new google.maps.Marker({
			        position: position,
			        data:data,
			        ID:markerId,
			        Lat:latRegion,
			        Long:lngRegion,
			        Name:regionName,
			        RegionId:RegionID,
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
				

						//CHECK IF Region NAME ALREADY EXISTS  
						if(AllRegionsArr.includes(NameSelected) == false){
						
						var regionSelected = NameSelected +" ";
						AllRegionsArr.push(regionSelected);
						}
						
						
						document.getElementById("region").value =NameSelected+";"+IDSelected;
						
						//Add regions name in text area
						var regionInput=region.value;
					    var regionNameInp = regionInput.split(";");
					    regionNameInp  = regionNameInp[0]+" ";
					    
				       $("#regionsAutocomplete").tagsinput('add', regionNameInp.toString() );	
				    		
						
				 			});
				google.maps.event.addListener(marker, "dblclick", function (e) {

			     	map.setZoom(15);
			     	map.setCenter(position);

			 			});
	 			
		  				}
*/

			//Define clusters
		 if (color=="#FFDC00"){
		 	yellowCluster = markerGroups["#FFDC00"];
		 /*	new MarkerClusterer(map,markerGroups["#FFDC00"], {
		 	ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url:  ctx+'/resources/clusterIcons/yellowCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
		 	},
		 	],
		 	
		 	 });
		 	 */
		 	}
		 	
		 	else if (color=="#0080FF"){
		 	
		 	blueCluster = markerGroups["#0080FF"];
		 /*	new MarkerClusterer(map,markerGroups["#0080FF"], {ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url:  ctx+'/resources/clusterIcons/blueCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
			},
			],
			 });
			 */
			}
			
			else if (color=="red"){
				redCluster = markerGroups["red"];
			/*	new MarkerClusterer(map,markerGroups["red"], {ignoreHiddenMarkers: true, 
				styles: [
				{
				url: ctx+'/resources/clusterIcons/redCluster.png',
				height: 60,
				width:60,
				anchorText:[-3,-3]
				},
				],
				 });
				 
				 */
				
				}
				
				else if (color=="#FF00FF"){
					pinkCluster = markerGroups["#FF00FF"];
				/*	new MarkerClusterer(map,markerGroups["#FF00FF"], {ignoreHiddenMarkers: true, 
					styles: [
					{
					url: ctx+'/resources/clusterIcons/pinkCluster.png',
					height: 60,
					width:60,
					anchorText:[-3,-3]
					},
					], 
					});
					*/
		      }


			else if (color=="#8A2BE2"){
				 purpleCluster = markerGroups["#8A2BE2"];
			/*	 new MarkerClusterer(map,markerGroups["#8A2BE2"], {ignoreHiddenMarkers: true, 
					    styles: [
					        {
					          url: ctx+'/resources/clusterIcons/purpleCluster.png',
					          height: 60,
					          width:60,
					          anchorText:[-3,-3]
					        },
					      ], 
					      });
					      */
		      }
		      
			
		      
			
			 markerClusterer = markerGroups["green"];
			 markerClusterer = markerGroups["#4D0207"];
				
}


function AddSelectedRegionColor(lst,regLatLng,map) {

 	  $("#legendMaxRevenue").attr('disabled', true);
 	  $("#legendMinRevenue").attr('disabled', true);
 		
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
	 
	maxRevenueListIndex=[];
	minRevenueListIndex=[];
	maxRevenueList=[];
	minRevenueList=[] ;

	maxRev=[];
	minRev=[];
	
	var dataRegion = lst;
	disableData = dataRegion;
	
	zeroTOFiveHndrd=[];
	fiveHndrdToTwoThsnd=[];
	twoThsndToInfnty=[];
	
	zero_500_List=[];
	fiveHndrd_2000_List=[];
	twoThsnd_More_List=[];

	    for (j=0;j<dataRegion.length;j++){
	        if (dataRegion[j][4] == '0-500$') {
	        	zeroTOFiveHndrd.push(j);
	        	zero_500_List.push(dataRegion[j]);
	        	

	        }
	       else if (dataRegion[j][4] == '500-2000$') {
	        	fiveHndrdToTwoThsnd.push(j);
	        	fiveHndrd_2000_List.push(dataRegion[j]);
	        	

	        }
	        else if (dataRegion[j][4] == 'MoreThn2000$') {
	        	twoThsndToInfnty.push(j);
	        	twoThsnd_More_List.push(dataRegion[j]);
	        	

	        }
	    }



	        if (zeroTOFiveHndrd.length > 0  ){  
	            AddRegionMarkers(zero_500_List,regLatLng,map,"#FFDC00");
	            $("#to500").attr('disabled', false);
	       	    $("#500to2000").attr('disabled', true);
	       	    $("#2000andMore").attr('disabled', true);
	            $("#to500").prop('checked',true);  
	        }
	        if (fiveHndrdToTwoThsnd.length > 0  ){  
	            AddRegionMarkers(fiveHndrd_2000_List,regLatLng,map,"#0080FF");
	            $("#to500").attr('disabled', true);
	       	    $("#500to2000").attr('disabled', false);
	       	    $("#2000andMore").attr('disabled', true);
	            $("#500to2000").prop('checked',true);  
	        }
	        if (twoThsndToInfnty.length > 0  ){  
	            AddRegionMarkers(twoThsnd_More_List,regLatLng,map,"red");
	            $("#to500").attr('disabled', true);
	       	    $("#500to2000").attr('disabled', true);
	       	    $("#2000andMore").attr('disabled', false);
	            $("#2000andMore").prop('checked',true);  
	        }
	    var element = document.getElementById("min-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
  		
  		   var element = document.getElementById("max-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
     
     
	}
maxList=[];
minList=[];
maxMinList=[];
function MaxMinSetColor(lst,regLatLng,map,checkedChckbox){
	maxList=[];
	minList=[];
	maxMinList=[];
	maxRev=[];
	minRev=[];
	disableData =[];
	   
		
	if (lst.length == 0) {
	 	  $("#to500").attr('disabled', true);
	 	  $("#500to2000").attr('disabled', true);
	 	  $("#2000andMore").attr('disabled', true);
	 	  $("#legendMaxRevenue").attr('disabled', true);
	 	  $("#legendMinRevenue").attr('disabled', true);
	 	//console.log("NO DATAAAA");
	 		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {
	
		 $("#to500").attr('disabled', false);
	 	  $("#500to2000").attr('disabled', false);
	 	  $("#2000andMore").attr('disabled', false);
	 	  $("#legendMaxRevenue").attr('disabled', false);
	 	  $("#legendMinRevenue").attr('disabled', false);
	 	 var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == " "){
			 element.innerHTML = "Unselect All";
		 }
		 
  var dataRegion = lst;
	
	  
  var maxRevenue = -1;
  var min_Revenue = -1;
  maxRevenueListIndex=[];
  minRevenueListIndex=[];
  maxRevenueList=[];
  minRevenueList=[] ; 
 
  

if (dataRegion.length>=1){

	var min_Revenue = dataRegion[0][2];
 	var maxRevenue = dataRegion[0][2];
 
	for (i=0;i<dataRegion.length;i++){
	if(dataRegion[i][0] != null && dataRegion[i][0] !="No region name"){
      //Set max Revenue Value
      if (dataRegion[i][2] >= maxRevenue){
      
      if (dataRegion[i][2] != maxRevenue){
     		 maxRevenueListIndex = [];
      	}
      
      	maxRevenue = dataRegion[i][2];
       // creating a list with max Revenue value
      	maxRevenueListIndex.push(i);
       
          
          }
        //Set min Revenue Value
          if (dataRegion[i][2] <= min_Revenue){
 			if (dataRegion[i][2] != min_Revenue){
		      	minRevenueListIndex = [];
		      }
            min_Revenue = dataRegion[i][2];
            
            // creating a list with min Revenue value
            minRevenueListIndex.push(i);
                        
            }
            }
      }

        
  
  if(checkedChckbox =="max") {

	  $("#to500").attr('disabled', true);
 	  $("#500to2000").attr('disabled', true);
 	  $("#2000andMore").attr('disabled', true);
 	  $("#legendMaxRevenue").attr('disabled', false);
 	  $("#legendMinRevenue").attr('disabled', true);


	  // coloring max revenue
	  for (z=0;z<maxRevenueListIndex.length;z++){
	      for (v=0;v<=4;v++){
	          //Add max Revenue region to max Revenue array
	          maxRevenueList.push(dataRegion[maxRevenueListIndex[z]][v]);
	          maxRev.push(dataRegion[maxRevenueListIndex[z]][v]);
	      }
	      disableData =[maxRev];
          maxList.push([dataRegion[maxRevenueListIndex[z]][0],dataRegion[maxRevenueListIndex[z]][1],dataRegion[maxRevenueListIndex[z]][2],dataRegion[maxRevenueListIndex[z]][3],dataRegion[maxRevenueListIndex[z]][4],dataRegion[maxRevenueListIndex[z]][5],dataRegion[maxRevenueListIndex[z]][6],dataRegion[maxRevenueListIndex[z]][2],dataRegion[maxRevenueListIndex[z]][8]]);
         // disableData =maxRev;
	  AddRegionMarkers([maxRevenueList],regLatLng,map,"green");
	  
	   if (maxRevenueList[4] == '1' && maxRevenueList[4] == '0' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {

		 var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotYellow");
  
      }
     else if (maxRevenueList[4] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotBlue");

      }
      else if (maxRevenueList[4] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '1' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotRed");

      }
     
     var element = document.getElementById("min-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
     
	  //Clear array before adding another max Revenue region
	  maxRevenueList=[];
	  $("#legendMaxRevenue").prop('checked',true); 
	  
	  
	  }

 		

	 }
  if(checkedChckbox =="min") {

	  $("#to500").attr('disabled', true);
 	  $("#500to2000").attr('disabled', true);
 	  $("#2000andMore").attr('disabled', true);
 	  $("#legendMaxRevenue").attr('disabled', true);
 	  $("#legendMinRevenue").attr('disabled', false);

 	
	//coloring min revenue
	for (w=0;w<minRevenueListIndex.length;w++){
	  for (e=0;e<=4;e++){
	      //Add min Revenue  region to min Revenue array
	      minRevenueList.push(dataRegion[minRevenueListIndex[w]][e]);
	      minRev.push(dataRegion[minRevenueListIndex[w]][e]);
	      
	      
	  }
      disableData =[minRev];
	  
      minList.push([dataRegion[minRevenueListIndex[w]][0],dataRegion[minRevenueListIndex[w]][1],dataRegion[minRevenueListIndex[w]][2],dataRegion[minRevenueListIndex[w]][3],dataRegion[minRevenueListIndex[w]][4],dataRegion[minRevenueListIndex[w]][5],dataRegion[minRevenueListIndex[w]][6],dataRegion[minRevenueListIndex[w]][2],dataRegion[minRevenueListIndex[w]][8]]);
	  
	AddRegionMarkers([minRevenueList],regLatLng,map,"#4D0207");
	
	
	
	
	 if (minRevenueList[4] == '0-500$' ) {

		 var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotYellow");
  
      }
      else if (minRevenueList[4] == '500-2000$' ) {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotBlue");

      }
     else if (minRevenueList[4] == 'MoreThn2000$') {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotRed");

      }
	
	var element = document.getElementById("max-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
     
	//Clear array before adding another min Revenue region
	minRevenueList=[] ;
	  $("#legendMinRevenue").prop('checked',true); 
		
	
	}

	 
  }
 if(checkedChckbox =="maxMin") {
	 if (dataRegion.length ==1){
	 	UnselectAll();
	 	AddSelectedRegionColor(dataRegion,regLatLng,map);

	}

	 else {
	  $("#to500").attr('disabled', true);
	  $("#500to2000").attr('disabled', true);
	  $("#2000andMore").attr('disabled', true);
	  $("#legendMaxRevenue").attr('disabled', false);
	  $("#legendMinRevenue").attr('disabled', false);
	 
	  if(maxRevenue == min_Revenue) {
			minRevenueListIndex =[];
		}
	
// coloring max revenue
for (z=0;z<maxRevenueListIndex.length;z++){
   for (v=0;v<=4;v++){
       //Add max Revenue region to max Revenue array
       maxRevenueList.push(dataRegion[maxRevenueListIndex[z]][v]);
       maxRev.push(dataRegion[maxRevenueListIndex[z]][v]);
       
   }

   revenueMax=[maxRev];
   
   maxMinList.push([dataRegion[maxRevenueListIndex[z]][0],dataRegion[maxRevenueListIndex[z]][1],dataRegion[maxRevenueListIndex[z]][2],dataRegion[maxRevenueListIndex[z]][3],dataRegion[maxRevenueListIndex[z]][4],dataRegion[maxRevenueListIndex[z]][5],dataRegion[maxRevenueListIndex[z]][6],dataRegion[maxRevenueListIndex[z]][2],dataRegion[maxRevenueListIndex[z]][8]]);
   
AddRegionMarkers([maxRevenueList],regLatLng,map,"green");


      if (maxRevenueList[4] == '0-500$') {

		 var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotYellow");
  
      }
     else if (maxRevenueList[4] == '500-2000$') {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotBlue");

      }
      else if (maxRevenueList[4] == 'MoreThn2000$') {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotRed");

      }
//Clear array before adding another max Revenue region
maxRevenueList=[];
$("#legendMaxRevenue").prop('checked',true);  
  
}

//coloring min revenue
for (w=0;w<minRevenueListIndex.length;w++){
for (e=0;e<=4;e++){
   //Add min Revenue  region to min Revenue array
   minRevenueList.push(dataRegion[minRevenueListIndex[w]][e]);
   minRev.push(dataRegion[minRevenueListIndex[w]][e]);
   
}
revenueMin =[minRev];
maxMinList.push([dataRegion[minRevenueListIndex[w]][0],dataRegion[minRevenueListIndex[w]][1],dataRegion[minRevenueListIndex[w]][2],dataRegion[minRevenueListIndex[w]][3],dataRegion[minRevenueListIndex[w]][4],dataRegion[minRevenueListIndex[w]][5],dataRegion[minRevenueListIndex[w]][6],dataRegion[minRevenueListIndex[w]][2],dataRegion[minRevenueListIndex[w]][8]]);


 if (minRevenueList[4] == '0-500$') {

		 var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotYellow");
  
      }
      else if (minRevenueList[4] == '500-2000$') {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotBlue");

      }
     else if (minRevenueList[4] == 'MoreThn2000$') {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotRed");

      }

AddRegionMarkers([minRevenueList],regLatLng,map,"#4D0207");
//Clear array before adding another min Revenue region
minRevenueList=[] ;
$("#legendMinRevenue").prop('checked',true);  

}
disableData = revenueMax.concat(revenueMin);
}
}//end if lngth
}
}
	 zeroTOFiveHndrd=[];
	  fiveHndrdToTwoThsnd=[];
	  twoThsndToInfnty=[];
}

maxRev=[];
minRev=[];
disableData =[];

function setColor(lst,regLatLng,map){
	maxRev=[];
	minRev=[];
	disableData =[];
	
	if (lst.length == 0) {
	 	  $("#to500").attr('disabled', true);
	 	  $("#500to2000").attr('disabled', true);
	 	  $("#2000andMore").attr('disabled', true);
	 	  $("#legendMaxRevenue").attr('disabled', true);
	 	  $("#legendMinRevenue").attr('disabled', true);
	 	console.log("NO DATAAAA");
	 		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {

		 $("#to500").attr('disabled', false);
	 	  $("#500to2000").attr('disabled', false);
	 	  $("#2000andMore").attr('disabled', false);
	 	  $("#legendMaxRevenue").attr('disabled', false);
	 	  $("#legendMinRevenue").attr('disabled', false);
	 	 var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == " "){
			 element.innerHTML = "Unselect All";
		 }
		 
		  var dataRegion = lst;
		  disableData = lst;
		  maxRevenueListIndex=[];
		  minRevenueListIndex=[];
		  zeroTOFiveHndrd=[];
		  fiveHndrdToTwoThsnd=[];
		  twoThsndToInfnty=[];

		  maxRevenueList=[];
		  minRevenueList=[] ;   
		  zero_500_List=[];
		  fiveHndrd_2000_List=[];
		  twoThsnd_More_List=[];
		  var min_Revenue = -1;
          var maxRevenue = -1;
          

		  
if (dataRegion.length>1){


	var min_Revenue = dataRegion[0][2];
    var maxRevenue = dataRegion[0][2];
  for (i=0;i<dataRegion.length;i++){
      //Set max Revenue Value
	if(dataRegion[i][0] != null && dataRegion[i][0] !="No region name"){
      if (dataRegion[i][2] >= maxRevenue){
      
      
     	if (dataRegion[i][2] != maxRevenue){
     		 maxRevenueListIndex = [];
      	}
      
      	maxRevenue = dataRegion[i][2];
      	
      	// creating a list with max Revenue value
      	maxRevenueListIndex.push(i);
       
          
          }
          if (dataRegion[i][2] <= min_Revenue){
          
		     if (dataRegion[i][2] != min_Revenue){
		      	minRevenueListIndex = [];
		      }
            min_Revenue = dataRegion[i][2];
            
            // creating a list with min Revenue value
            minRevenueListIndex.push(i);
            
            }
       }     
      }
      if(maxRevenue == min_Revenue) {
			minRevenueListIndex =[];
		}
      

        }


 // creating the list index lists for 0-500$, 500-2000$ and MoreThn2000$ 
  for (j=0;j<dataRegion.length;j++){
	if(dataRegion[j][0] != null && dataRegion[j][0] !="No region name"){
      if (dataRegion[j][4] == '0-500$' && dataRegion[j][2] != maxRevenue && dataRegion[j][2] != min_Revenue ) {
      	zeroTOFiveHndrd.push(j);
      	zero_500_List.push(dataRegion[j]);
      	

      }
      else if (dataRegion[j][4] == '500-2000$' && dataRegion[j][2] != maxRevenue && dataRegion[j][2] != min_Revenue ) {
      	fiveHndrdToTwoThsnd.push(j);
       	fiveHndrd_2000_List.push(dataRegion[j]);
      	

      }
      else if (dataRegion[j][4] == 'MoreThn2000$' && dataRegion[j][2] != maxRevenue && dataRegion[j][2] != min_Revenue ) {
      	twoThsndToInfnty.push(j);
      	twoThsnd_More_List.push(dataRegion[j]);
      	

      }
}
  }
  
  // coloring max revenue
  for (z=0;z<maxRevenueListIndex.length;z++){
      for (v=0;v<=4;v++){
          //Add max Revenue region to max Revenue array
          maxRevenueList.push(dataRegion[maxRevenueListIndex[z]][v]);
          maxRev.push(dataRegion[maxRevenueListIndex[z]][v]);
      }
  AddRegionMarkers([maxRevenueList],regLatLng,map,"green");
  
      if (maxRevenueList[4] == '0-500$') {

		 var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotYellow");
  
      }
     else if (maxRevenueList[4] == '500-2000$') {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotBlue");

      }
     else if (maxRevenueList[4] == 'MoreThn2000$') {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotRed");

      }

  

   
      
  //Clear array before adding another max Revenue region
  maxRevenueList=[];
  $("#legendMaxRevenue").prop('checked',true);  
     
  }
  
//coloring min revenue
for (w=0;w<minRevenueListIndex.length;w++){
  for (e=0;e<=4;e++){
      //Add min Revenue  region to min Revenue array
      minRevenueList.push(dataRegion[minRevenueListIndex[w]][e]);
      minRev.push(dataRegion[minRevenueListIndex[w]][e]);
  }
AddRegionMarkers([minRevenueList],regLatLng,map,"#4D0207");


 if (minRevenueList[4] == '0-500$') {

		 var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotYellow");
  
      }
      else if (minRevenueList[4] == '500-2000$') {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotBlue");

      }
     else if (minRevenueList[4] == 'MoreThn2000$') {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotRed");

      }

	//Clear array before adding another min Revenue region
	minRevenueList=[] ;
	$("#legendMinRevenue").prop('checked',true);  

}


      if (zeroTOFiveHndrd.length > 0  ){  
          AddRegionMarkers(zero_500_List,regLatLng,map,"#FFDC00");
          $("#to500").prop('checked',true);  
      }
      if (fiveHndrdToTwoThsnd.length > 0  ){  
          AddRegionMarkers(fiveHndrd_2000_List,regLatLng,map,"#0080FF");
          $("#500to2000").prop('checked',true);  
      }
      if (twoThsndToInfnty.length > 0  ){  
          AddRegionMarkers(twoThsnd_More_List,regLatLng,map,"red");
          $("#2000andMore").prop('checked',true);  
      }

 
      
      
	}

}


function defaultLegend(){

	document.getElementById("tableDiv").innerHTML ="";
 	document.getElementById("tableDiv").innerHTML =divTablee;
 	
 	document.getElementById("legendHeader").innerHTML ="";
	document.getElementById("legendHeader").innerHTML='<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6> <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAll()"  id="selectUnselect" >Unselect All</button>';
	
	}

firstRangeListIndex=[];
secondRangeListIndex=[];
thirdRangeListIndex=[];
fourthRangeListIndex=[];
fifthRangeListIndex=[];


firstRangeList=[];
secondRangeList=[];
thirdRangeList=[];
fourthRangeList=[];
fifthRangeList=[];
revenueColorArr=[];
var newTableDiv;

var colorsArray = ["-- Select Color --","Blue","Yellow","Red","Pink","Purple"];
var colorsHexArray = ["-- Select Color --","#0080FF","#FFDC00","red","#FF00FF","#8A2BE2"];
var rowCount=0;
var mainRevenueColor;
function addRow(){

	rowCount =  $("#revenueColorTable tr").length;

	
if(rowCount <=5) {
	colorOptions = "<select class='ui-widget ui-corner-all form-control'>";
	var colorOptionValue = "-- Select Color --";
	for(j=0; j<colorsArray.length; j++)
	{
		if(colorsArray[j] != "-- Select Color --" && colorsHexArray[j] != "-- Select Color --")
			colorOptionValue = colorsHexArray[j];
		colorOptions += "<option value='"+colorOptionValue+"' >"+colorsArray[j]+"</option>";
	}
	colorOptions += "</select>";

	var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td>"
      	+"<td name='from'><input type='text'  name='fromValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' /></td>"
      	+"<td name='to'><input type='text' name='toValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control'></td>"
      	+"<td name='color'>"+colorOptions+"</td></tr>";

    $("#revenueColorTable").append(markup);


    selectColorChanges();
}
else {

	  var element = document.getElementById("alertMsg");
	  element.innerHTML = "No more allowed rows to add";

	  rowCount ==6;
}


	
}




fromToColor2 =[];
function SelectUnselectAllRev() {
	fromToColor2 =[];
	fromToColor2 = fromToColor;
	checkedColor=[];
	if(firstRangeListIndex.length >0) {
		if(fromToColor[2] =="blue") {				
	        fromToColor[2] = "#0080FF";
	}
	    else if(fromToColor[2] =="purple") {				
	        fromToColor[2] = "#8A2BE2";
	}
	   else if(fromToColor[2] =="pink") {				
	        fromToColor[2] = "#FF00FF";
	}

	   else if(fromToColor[2] =="yellow") {				
	        fromToColor[2] = "#FFDC00";
	}
	   
	    checkedColor.push(fromToColor[2]);
	    
	}

	if(secondRangeListIndex.length >0) {
	    
	    if(fromToColor[5] =="blue") {				
	        fromToColor[5] = "#0080FF";
	}
	    else if(fromToColor[5] =="purple") {				
	        fromToColor[5] = "#8A2BE2";
	}
	    else if(fromToColor[5] =="pink") {				
	        fromToColor[5] = "#FF00FF";
	}
	    else if(fromToColor[5] =="yellow") {				
	        fromToColor[5] = "#FFDC00";
	}
	    
	    checkedColor.push(fromToColor[5]);
	    }

	if(thirdRangeListIndex.length >0) {
	    
	    
	    if(fromToColor[8] =="blue") {				
	        fromToColor[8] = "#0080FF";
	}
	    else if(fromToColor[8] =="purple") {				
	        fromToColor[8] = "#8A2BE2";
	}
	    else if(fromToColor[8] =="pink") {				
	        fromToColor[8] = "#FF00FF";
	}
	    else if(fromToColor[8] =="yellow") {				
	        fromToColor[8] = "#FFDC00";
	}
	    
	    checkedColor.push(fromToColor[8]);
	    }

	if(fourthRangeListIndex.length >0) {
	    
	    if(fromToColor[11] =="blue") {				
	        fromToColor[11] = "#0080FF";
	}
	    else if(fromToColor[11] =="purple") {				
	        fromToColor[11] = "#8A2BE2";
	}
	    else if(fromToColor[11] =="pink") {				
	        fromToColor[11] = "#FF00FF";
	}

	    else if(fromToColor[11] =="yellow") {				
	        fromToColor[11] = "#FFDC00";
	}
	    checkedColor.push(fromToColor[11]);
	    }

	if(fifthRangeListIndex.length >0) {
	    
	    
	    if(fromToColor[14] =="blue") {				
	        fromToColor[14] = "#0080FF";
	}
	   else if(fromToColor[14] =="purple") {				
	        fromToColor[14] = "#8A2BE2";
	}
	    else if(fromToColor[14] =="pink") {				
	        fromToColor[14] = "#FF00FF";
	}
	   else if(fromToColor[14] =="yellow") {				
	        fromToColor[14] = "#FFDC00";
	}
	    
	    checkedColor.push(fromToColor[14]);
	    }



	var element = document.getElementById("selectUnselect");

	if (element.innerHTML == "Unselect All"){
		element.innerHTML = "Select All";


	   //Uncheck all checkboxes by value (by color)
	   for (var l= 0; l < checkedColor.length; l++) {
	   
	   //console.log("colorssss"+checkedColor[l]);
	   
	   $('input:checkbox[value="' + checkedColor[l] + '"]').prop("checked", false);
	    }

	    
	for (var l= 0; l < checkedColor.length; l++) {
	    for (var i = 0; i < markerGroups[checkedColor[l]].length; i++) {

	            
	        var marker = markerGroups[checkedColor[l]][i];
	        //console.log(" markerGroups[color][i]"+markerGroups[checkedColor[l]][i]);
	        
	       //Show markers

	        if (!marker.getVisible()) {
	          marker.setVisible(true);
	        } 

	       //hide markers

	        else {
	          marker.setVisible(false);
	        }
	    }

	  //Hide yellow cluster icon
	    if(checkedColor[l] == "#FFDC00") {
	   	 color = "";
	        color = "#FFDC00";
	           if  ($('#yellow').prop("checked") == false) {
	               HideClusterIcon(markerGroups[color],color);
	               }
	           }	

	   //Hide blue cluster icon
	     else if(checkedColor[l] == "#0080FF") {
	   	  color = "";
	         color = "#0080FF";
	         if  ($('#blue').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     else if(checkedColor[l] == "red") {
	   	  color = "";
	         color = "red";
	         if  ($('#red').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     else if(checkedColor[l] == "#FF00FF") {
	   	  color = "";
	         color = "#FF00FF";
	         if  ($('#pink').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     else if(checkedColor[l] == "#8A2BE2") {

	   	  color = "";
	         color = "#8A2BE2";
	         if  ($('#purple').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     }
	     }

	else {
	    element.innerHTML = "Unselect All";

	          for (var l= 0; l < checkedColor.length; l++) {
	          $('input:checkbox[value="' + checkedColor[l] + '"]').prop("checked", true);
	                   }

	       
	      for (var l= 0; l < checkedColor.length; l++) {
	          for (var i = 0; i < markerGroups[checkedColor[l]].length; i++) {
	              
	              var marker = markerGroups[checkedColor[l]][i];
	              //console.log(" markerGroups[checkedColor][i]"+markerGroups[checkedColor[l]][i]);


	              if (!marker.getVisible()) {
	                marker.setVisible(true);
	              } else {
	                marker.setVisible(false);
	              }
	          }
	         }
	         ShowClusterIconRev(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,fromToColor);


	         

	     }
	  }
	              
   
             

function ShowClusterIconRev(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,color) {

	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
    var map = new google.maps.Map(document.getElementById("mapContainer"), {
		center:Nairobi ,
		zoom: 6
		});

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

	if (firstRangeList.length>0){
		AddRegionMarkers(firstRangeList,map,fromToColor[2]);
		}
	if (secondRangeList.length>0){
		AddRegionMarkers(secondRangeList,map,fromToColor[5]);
        }               
    if (thirdRangeList.length>0){
        AddRegionMarkers(thirdRangeList,map,fromToColor[8]);
        }
    if (fourthRangeList.length>0){
         AddRegionMarkers(fourthRangeList,map,fromToColor[11]);
        }
    if (fifthRangeList.length>0){
        AddRegionMarkers(fifthRangeList,map,fromToColor[14]);
       } 
     //Add buttons on map
     const centerControlDiv = document.createElement("div");
	 LegendControl(centerControlDiv, map);
	 map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	 const DefaultZoomDiv = document.createElement("div");
	 DefaultZoomControl(DefaultZoomDiv, map);
	 map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	 const maxRevControlDiv = document.createElement("div");
	 MaxRevenueControl(maxRevControlDiv, map);
	 map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);
	 
	 const minRevControlDiv = document.createElement("div");
	 MinRevenueControl(minRevControlDiv, map);
	 map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);

	 }		

function SelectUnselectAll() {
	checkedColorr=[];

		if(maxRevenueListIndex.length >0) {
			checkedColorr.push("green");
			}

		if(minRevenueListIndex.length >0) {
			checkedColorr.push("#4D0207");
					}

		if(zeroTOFiveHndrd.length >0) {
			checkedColorr.push("#FFDC00");
					}

		if(fiveHndrdToTwoThsnd.length  >0) {
			checkedColorr.push("#0080FF");
					}

		if(twoThsndToInfnty.length  >0) {
			checkedColorr.push("red");
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
				 for (var i = 0; i < markerGroups[colors[l]].length; i++) {
				 	
				 	var marker = markerGroups[colors[l]][i];
				 	//console.log(" markerGroups[color][i]"+markerGroups[colors[l]][i]);
				 	
					//Show markers

				 	if (!marker.getVisible()) {
				 	  marker.setVisible(true);
				 	} 
					//hide markers

				 	else {
				 	  marker.setVisible(false);
				 	}
				 }


			
			  
			  }
			  }

		  else {

			  element.innerHTML = "Unselect All";

	  	 	 for (var l= 0; l < checkedColorr.length; l++) {
				$('input:checkbox[value="' + checkedColorr[l] + '"]').prop("checked", true);
						 }


	  	 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			 colors = [];
			checkboxes.forEach((checkbox) => {
			    colors.push(checkbox.value);
			});
			
			
		 for (var l= 0; l < checkedColorr.length; l++) {
			 for (var i = 0; i < markerGroups[checkedColorr[l]].length; i++) {
			 	
			 	var marker = markerGroups[checkedColorr[l]][i];


			 	if (!marker.getVisible()) {
			 	  marker.setVisible(true);
			 	} else {
			 	  marker.setVisible(false);
			 	}
			 }


			 }
		 }
		 }
		 
function DeleteMarkers() {

	if( markers.length >0){
	for  (var i = 0; i < markers.length; i++) {
		var marker = markers[i];
		marker.setMap(null);
	}
	}
markerGroups = {
					  "green": [],
					  "red": [],
					 "#FF8C00": [],
					  "#0080FF": [],
					  "#FFDC00": [],
					  "#4D0207": [],
					  "#FF00FF":[],
					  "#8A2BE2":[]

					};

	
	
	  	 
	}


function UnselectAll(){

  $("input[name='legendCheckbox']").prop('checked',false);
	
}

function UnselectAllRev(){

	  $("input[name='revenueCheckbox']").prop('checked',false);
		
	}

function ShowClusterIcon(zero_500_List,fiveHndrd_2000_List,twoThsnd_More_List,color) {

	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	var map = new google.maps.Map(document.getElementById("mapContainer"), {
		center:Nairobi ,
		zoom: 6
		});

	/*
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
*/
                             if (color=="#FFDC00"){
                                AddRegionMarkers(zero_500_List,regLatLng,map,"#FFDC00"); 
                                if (markerGroups["#0080FF"].length>0  ){
                                   //Clear previous blue markers
                                    HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
                                    //Add blue markers
                                AddRegionMarkers(fiveHndrd_2000_List,regLatLng,map,"#0080FF");
                                }
                                if (markerGroups["red"].length>0  ){
                                 HideClusterIcon(markerGroups["red"],"red");
                                 AddRegionMarkers(twoThsnd_More_List,regLatLng,map,"red");
                                 }
                             
                        
                                }
                        
                                else if (color=="#0080FF"){
                                    AddRegionMarkers(fiveHndrd_2000_List,regLatLng,map,"#0080FF"); 
                                    if (markerGroups["#FFDC00"].length>0  ){
                                    HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
                                    AddRegionMarkers(zero_500_List,regLatLng,map,"#FFDC00");
                                    }
                                    if (markerGroups["red"].length>0  ){
                                    HideClusterIcon(markerGroups["red"],"red");
                                    AddRegionMarkers(twoThsnd_More_List,regLatLng,map,"red");
                                     }
                                  
                                    }
                        
                                 else if (color=="red"){
                                        AddRegionMarkers(twoThsnd_More_List,regLatLng,map,"red"); 
                                        if (markerGroups["#FFDC00"].length>0  ){
                                        HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
                                        AddRegionMarkers(zero_500_List,regLatLng,map,"#FFDC00");
                                        }
                                        if (markerGroups["#0080FF"].length>0  ){
                                         HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
                                         AddRegionMarkers(fiveHndrd_2000_List,regLatLng,map,"#0080FF");
                                         }
                                       
                                
                                
                                        }
                                    //Add buttons on map
                                	const centerControlDiv = document.createElement("div");
                                    LegendControl(centerControlDiv, map);
                                    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

                                    const DefaultZoomDiv = document.createElement("div");
                                    DefaultZoomControl(DefaultZoomDiv, map);
                                    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

                                    const maxRevControlDiv = document.createElement("div");
                                    MaxRevenueControl(maxRevControlDiv, map);
                                    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

                                    const minRevControlDiv = document.createElement("div");
                                    MinRevenueControl(minRevControlDiv, map);
                                    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
                                    }

function ShowHideMarkers(color) {
	  

var marker = markerGroups[color][0];
	for ( x = 0; x < markerGroups[color].length; x++) {
	
	marker = markerGroups[color][x];	

	//Show markers using checkboxes in popup
	if (!marker.getVisible()) {
	  marker.setVisible(true);

	}
	//hide markers using checkboxes in popup
	else {
	marker.setVisible(false); 
	}
		}


// Show/Hide ClusterIcon
	if(color == "#FFDC00") {
		if ($('#to500').prop("checked") == true) {
			if(marker) marker.setVisible(true);
			else {
			alert("No regions with this revenue range");
			$('#to500').prop("checked", false);
			}
						//ShowClusterIcon(zero_500_List,fiveHndrd_2000_List,twoThsnd_More_List,"#FFDC00");
			}

		
		else {

			HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
  }	

	}

	if(color == "#0080FF") {
		if ($('#500to2000').prop("checked") == true) {
			//ShowClusterIcon(zero_500_List,fiveHndrd_2000_List,twoThsnd_More_List,"#0080FF");
			if(marker) marker.setVisible(true);
			else {
			alert("No regions with this revenue range");
			$('#500to2000').prop("checked", false);
			}			
			}

		
		else {

			HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
  }	

	}

	if(color == "red") {
		if ($('#2000andMore').prop("checked") == true) {
			if(marker) marker.setVisible(true);
			else {
			alert("No regions with this revenue range");
			$('#2000andMore').prop("checked", false);
			}
						//ShowClusterIcon(zero_500_List,fiveHndrd_2000_List,twoThsnd_More_List,"red");
			}

		
		else {

			HideClusterIcon(markerGroups["red"],"red");
  }	

	}



}
function HideClusterIcon(MarkersArray,color) {

  //  alert("color of cluster is" +color);
    
    if(color == "#FFDC00") {
        for  ( i = 0; i<markerGroups["#FFDC00"].length; i++) {	

        //Remove yellow markers from map			
       // markerGroups["#FFDC00"][i].setMap(null);
        markerGroups["#FFDC00"][i].setVisible(false);
    }
    

    //yellowCluster.clearMarkers();
    //clear yellow markers array
    //markerGroups["#FFDC00"] =[];
    color == "" ;  
    }

    
    else if (color == "#0080FF") {

        for  ( j = 0; j<markerGroups["#0080FF"].length; j++) {	
        markerGroups["#0080FF"][j].setVisible(false);
        }
        
       // blueCluster.clearMarkers();
        //markerGroups["#0080FF"] =[];
        color == "";
            
    }

    else if (color == "red") {

        for  ( r = 0; r<markerGroups["red"].length; r++) {	
        markerGroups["red"][r].setVisible(false);
        }
        
       // redCluster.clearMarkers();
        //markerGroups["red"] =[];
        //color = "";
            
    }
   else if (color == "#FF00FF") {


    	for  ( n = 0; n<markerGroups["#FF00FF"].length; n++) {	
        markerGroups["#FF00FF"][n].setVisible(false);
        }
        
       // pinkCluster.clearMarkers();
        //markerGroups["#FF00FF"] =[];
         // color = "";  
    }
    
    else if (color == "#8A2BE2") {

        for  ( b = 0; b<markerGroups["#8A2BE2"].length; b++) {	
        markerGroups["#8A2BE2"][b].setVisible(false);
        }
        
       // purpleCluster.clearMarkers();
        //markerGroups["#8A2BE2"] =[];
        //color = "";    
    }

    
}

function ShowHideRevMarkers(color) {


   if (color=="blue"){
       color="#0080FF"
   }
   if (color=="purple"){
       color="#8A2BE2"
   }
   if (color=="pink"){
       color="#FF00FF"
   }
   if(color =="yellow") {				
      color = "#FFDC00";
}

 
   for ( x = 0; x < markerGroups[color].length; x++) {
   
   var marker = markerGroups[color][x];	

   //Show markers using checkboxes in popup
   if (!marker.getVisible()) {
     marker.setVisible(true);

   }
   //hide markers using checkboxes in popup
   else {
   marker.setVisible(false); 
   }
       }


// Show/Hide ClusterIcon
   if(color == "#FFDC00") {
       if ($('#yellow').prop("checked") == true) {
           ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#FFDC00");
           }
       else {
           HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
 }	

   }

   else if(color == "#0080FF") {
       if ($('#blue').prop("checked") == true) {
           ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#0080FF");
           }

       
       else {
           HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
 }	

   }

  else if(color == "red") {
       if ($('#red').prop("checked") == true) {
    	   ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"red");
           }

       
       else {
           HideClusterIcon(markerGroups["red"],"red");
 }	

   }

   else if(color == "#8A2BE2") {
       if ($('#purple').prop("checked") == true) {
    	   ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#8A2BE2");
           }

       
       else {
           HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
 }	

   }

  else if(color == "#FF00FF") {
       if ($('#pink').prop("checked") == true) {
    	   ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#FF00FF");
           }

       else {
           HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
 }	

   }


}
function ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,color) {
  
    var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
    var map = new google.maps.Map(document.getElementById("mapContainer"), {
        center:Nairobi ,
        zoom: 6
        });

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

color_group=[fromToColor[2],fromToColor[5],fromToColor[8],fromToColor[11],fromToColor[14]];
for ( x2 = 0; x2 < 5; x2++) {
    if (color_group[x2]!=undefined){
        if(color_group[x2]=="yellow"){
            color_group[x2]="#FFDC00";
        }
        else if(color_group[x2]=="red"){
            color_group[x2]="red";
        }
        else if(color_group[x2]=="blue"){
            color_group[x2]="#0080FF";
        }
        else if(color_group[x2]=="purple"){
            color_group[x2]="#8A2BE2";
        }
        else if(color_group[x2]=="pink"){
            color_group[x2]="#FF00FF";
        }        
    }
}
var color1="" ;                        
color_index=0;
for ( x1 = 0; x1 < 5; x1++) {
    if (color==color_group[x1]){
        color_index=x1;
    }
}

if (color_index==0){
    AddRegionMarkers(firstRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddRegionMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddRegionMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddRegionMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddRegionMarkers(fifthRangeList,map,color_group[4]);

    }
}

}


else if (color_index==1){
    AddRegionMarkers(secondRangeList,map,color_group[color_index]);
    if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddRegionMarkers(firstRangeList,map,color_group[0]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddRegionMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddRegionMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddRegionMarkers(fifthRangeList,map,color_group[4]);

    }
}

}

else if (color_index==2){
    AddRegionMarkers(thirdRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddRegionMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddRegionMarkers(firstRangeList,map,color_group[0]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddRegionMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddRegionMarkers(fifthRangeList,map,color_group[4]);

    }
}

}


else if (color_index==3){
    AddRegionMarkers(fourthRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddRegionMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddRegionMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddRegionMarkers(firstRangeList,map,color_group[0]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddRegionMarkers(fifthRangeList,map,color_group[4]);

    }
}

}


else if (color_index==4){
    AddRegionMarkers(fifthRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddRegionMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddRegionMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddRegionMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddRegionMarkers(firstRangeList,map,color_group[0]);

    }
}

}


//Add buttons on map
  const centerControlDiv = document.createElement("div");
  LegendControl(centerControlDiv, map);
  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

  const DefaultZoomDiv = document.createElement("div");
  DefaultZoomControl(DefaultZoomDiv, map);
  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

  const maxRevControlDiv = document.createElement("div");
  MaxRevenueControl(maxRevControlDiv, map);
  map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

  const minRevControlDiv = document.createElement("div");
  MinRevenueControl(minRevControlDiv, map);
  map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
  }



 $(function(){
 
$(".deleteRow").click(function () {
	slctDel = [];
	
	$("#revenueColorTable").find('input[name="record"]').each(function () {
   	if ($(this).is(":checked")) {
       	slctDel.push($(this).parent().parent().children('td[name="color"]').children('select').val());
          // console.log("The selected delete is " + slctDel);
		 }
     });
        
        
        for (i = 0; i <= slctDel.length; ++i) {
        	if (slctDel.length == 0) {
           	alert(' Select Row(s) to Delete');
               return false;
            }
          }

         $("#revenueColorTable").find('input[name="record"]').each(function () {
         	if ($(this).is(":checked")) {
           	$(this).parents("tr").remove();
           	
            }

           });

         
          
         
}); // end delete row fct 


$('.modal-dialog').draggable({
	handle: ".modal-header, .modal-footer"
	});

//Reset popup position after it has been dragged and closed
$('body').on('hidden.bs.modal', function() {
$('.modal-dialog').css({'top': '', 'left':''});
})



//Select all checkbox in  table
$('body').on('click', '#selectAll', function  () {
	if ($(this).hasClass('allChecked')) {
		$('input[type="checkbox"]', '#revenueColorTable').prop('checked', false);
	} 
	else {
		$('input[type="checkbox"]', '#revenueColorTable').prop('checked', true);
	}
	
	$(this).toggleClass('allChecked');
	       				
}) // end select checkbox fct 



$('.modal-content').resizable({
    handles: "e"
});

//Reset the popup to original size after resizing 
$('#Modal').on('hidden.bs.modal', function() {
$(this).find('.modal-content').css({'width': '700px', 'height': ''});
})

// Minimize and Maximize fct for popup
	$(".modalMinimize").on("click", function(){
	
	$(".modal-body").slideToggle();
	$(".modal-footer").slideToggle();
	
	//Toggle between minimize/maximize icons
	var iconToChange = $('.icon-to-change');
		if(iconToChange.hasClass('fa-window-restore')){
     		iconToChange.removeClass('fa-window-restore')
    		            .addClass('fa-minus')
		}
		else{
     		iconToChange.addClass('fa-window-restore')
    		             .removeClass('fa-minus')
		}    		         
	}); // end minimize/maximize fct

      
 $('select').on('change', function () {
 	if (this.value == "#0080FF" ) {
 		$(this).closest('tr').css('background-color','#0080FF');
	        
	 } 
	else if (this.value == "#FFDC00") {
	  	 $(this).closest('tr').css('background-color','#FFDC00');
	}
	else if (this.value == "red") {
	 	 $(this).closest('tr').css('background-color','red');
	   } 

	else if (this.value == "#FF00FF") {
	 	 $(this).closest('tr').css('background-color','#FF00FF');
	   
	   	    } 

	else if (this.value == "#8A2BE2") {
	    	 $(this).closest('tr').css('background-color','#8A2BE2');
		    
	   	    } 
	  else if (this.value == "-- Select Color --") {
	   	 $(this).closest('tr').css('background-color','white');
		  } 

	   
	       
	});

 });
 
      
  