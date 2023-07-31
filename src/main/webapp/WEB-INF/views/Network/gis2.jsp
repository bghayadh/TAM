<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>GIS</title>
    <script defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp">
</script>

<script src=https://cdnjs.cloudflare.com/ajax/libs/markerclustererplus/2.1.4/markerclusterer.min.js></script>
    
    
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
	
 <style>
 
 

 
 label {
  display: block;
  margin-bottom: 5px;
}
 .label,
.badge {
  display: inline-block;
  padding: 2px 4px;
  font-size: 11.844px;
  font-weight: bold;
  line-height: 14px;
  color: #ffffff;
  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
  white-space: nowrap;
  vertical-align: baseline;
  background-color: #999999;
}



h1,h2,h3,h4,h5,h6 {
  margin: 10px 0;
  font-family: inherit;
  font-weight: bold;
  line-height: 15px;
  color: inherit;
  text-rendering: optimizelegibility;
}




 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 100px;
	        		}


				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				   
				}
				
#search{
    width:90%!important;
}

a.btn{
	text-decoration:none;
	color:#fff;
	padding: 8px 20px;
	border-radius: 25px;
	margin:0 10px 10px;
	background-color:gray;
	
 }

.searchicon {
    color:#5CB85C!important;
}

.items-collection{
    margin:20px 0 0 0!important;
}
.items-collection label.btn-default.active{
    background-color:#007ba7!important;
    color:#FFF!important;
    
}
label,button{outline: none !important;
    box-shadow: none !important;}
.items-collection label.btn-default{
    width:90%!important;
    border:1px solid #305891!important;
    margin:5px!important; 
    border-radius: 17px!important;
    color: #305891!important;
}
.items-collection label .itemcontent{
    width:100%!important;
    height:25px;
}
.items-collection .btn-group{
    width:90%!important;
}
*,
*:before,
*:after {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

.gm-style-iw {
  width: 350px !important;
  height: 100px !important;
 
}

.btnGet{
	text-decoration:none;
	color:#3F231C;
	padding: 8px 20px;
	border-radius: 25px;
	margin:20px 10px 20px;
	background-color:gray;
	
 }
 .btn:focus {
   background-color: #817D7C;
   border-color:black!important;
}
 

</style>



</head>

<body>
	<!-- nav -->
	


	<%@ include file="../header.html" %>
	

	<div class="row">



			<div class="col-md-3" style="text-align: left;margin-top:40px;">
			<span class="label label-default" style="width:150px;color:white;border-radius:15px;margin-left:20px;background-color:#08526D;"><h4><i class="fa fa-map" style="font-size:20px;color:silver;margin-left:30px;"></i> Map</h4></span>		
			
			
		</div>
			<div class="col-md-5"></div>
			
			<div class="col-md-4" style="text-align: right;margin-top:40px;">
				<div class="btn-group pull-right">
					<div class="glyph">


						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search" id="open-popup-btn">
							<i class="fa fa-search"></i>
						</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title=" GIS"
							onclick='window.location.href = "${pageContext.request.contextPath}/GisPage"'>
							<i class="fas fa-map-marked-alt"></i>
						</button>


						<button type="button" class="btn btn-danger" data-toggle="tooltip"
							data-placement="top" title=" Folder Tree"
							style="background: #da6815;">
							<i class="fas fa-sitemap"></i>
						</button>

						<button type="button" id="Fview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="Form View"
							onclick='window.location.href = "${pageContext.request.contextPath}/SiteFormView"'>
							<i class="fa fa-edit"></i>
						</button>
						<a href="Sitelistview">

							<button type="submit" id="Lview" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="List View">
								<!--  onclick='window.location.href = "${pageContext.request.contextPath}/SiteListView"' -->
								<i class="fa fa-bars"></i>
							</button>
						</a>

					</div>
				</div>
			</div>

</div>

	<hr>	
<div class="container-fluid">


<div style="width: 800px; height: 600px;align:right" id="mapContainer">

</div>
<div class=" items info-block block-info clearfix btn bizmoduleselect itemcontent" style="margin-left: 11%">
<button id="get" class="btnGet">Get Directions</button>
<button id="getDistance" class="btnGet">Get Distance</button>


<div id="msg"></div>


</div>
</div>




<script type="text/javascript">

function initMap(){


	//Markers and Icons
	
			const iconBase ="https://developers.google.com/maps/documentation/javascript/examples/full/images/";

			

	//Longtitude and latitude of saida and beirut 
			var saida=new google.maps.LatLng(33.585599,35.384544);
			var beirut=new google.maps.LatLng(33.88894,35.49442);

			 const dakota = {lat: 33.585599, lng: 35.384544};
			  const frick = {lat: 33.88894, lng: 35.49442};
			  
			var lat1=34.4333;
			var lng1=36.2086;
			var lat2=33.1194;
			var lng2=35.8437;
			var siteSource1=new google.maps.LatLng(lat1,lng1);
			var siteDest1=new google.maps.LatLng(lat2,lng2);

	//Restriction for Lebanon
	 
	 		const Lebanonn = {
        		north: 34.9,
        		south: 32.8,
       		 	west: 34.8,
        		east: 36.9,
      			};
     
	//Map options//
			var options={
				zoom:9,
				restriction: {
        			latLngBounds: Lebanonn,
        			strictBounds: false,
     	 		},
				center:beirut,
	//************Positioning the buttons zoom in and zoom out to left side plus the street view button***********
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
  	
     			fullscreenControl: true,
     
		}

	//************  End of Map styling  **************//////////
	
	
	var directionsDisplay=new google.maps.DirectionsRenderer();
	var directionsService=new google.maps.DirectionsService();
	
	//New Map//
	var map=new google.maps.Map(document.getElementById('mapContainer'),options);

	directionsDisplay.setMap(map);



	//-----> Create the DIV to hold the control and call the CenterControl()
    //-----> constructor passing in this DIV.
    
    const centerControlDiv = document.createElement("div");
    CenterControl(centerControlDiv, map);
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);


 	// Create an array of alphabetical characters used to label the markers.
    
    const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	// Add some markers to the map.
    // Note: The code uses the JavaScript Array.prototype.map() method to
    // create an array of markers based on a given "locations" array.
    // The map() method here has nothing to do with the Google Maps API.
    
     const markersArray=
	[
  		{lat:34.4333 , lng:36.2086 },
	 	{lat:33.8869 , lng:35.8097 },
 		{lat:33.5606 , lng:35.5333 },
 		{lat:33.2667 , lng:35.5833 },
 		{lat:33.9697 , lng:35.5664 },
 		{lat:33.8439 , lng:35.8936 },
 		{lat:33.3833 , lng:35.6 },
 		{lat:34.0061 , lng:35.65 },
		{lat:34.2994 , lng:36.0311 },
		{lat:33.8333 , lng:36.0797 },
		{lat:33.3667 , lng:35.4333 },
		{lat:33.8906 , lng:35.5844 },
		{lat:34.3997 , lng:35.6581 },
		{lat:34.1167 , lng:35.7842 },
		{lat:34.3853 , lng:35.5808 },
		{lat:34.5428 , lng:36.3847 },
		{lat:33.1194 , lng:35.8437 },
		{lat:33.5417 , lng:36.0972 },
		{lat:33.6269 , lng:35.8333 },
		{lat:34.2553 , lng:35.5131 },
		{lat:33.6942 , lng:35.3981 },
		{lat:34.3942 , lng:35.2 },
		{lat:33.8053 , lng:35.685 }
	];

			
      	
    var markersL = markersArray.map((location, i) => {
      	return new google.maps.Marker({
        	position: location,
        	label: labels[i % labels.length],
        	icon: iconBase + "parking_lot_maps.png",
      	});
    });

   

  

   
   /* new MarkerClusterer(map, markersL, {
        imagePath:
          "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
      });*/
    
     
	// ***********Array of markers************//
	    var markers=[
			{coordinates:{lat:34.4333 , lng:36.2086 }
	        },		      
			{coordinates:{lat:33.8869 , lng:35.8097 }
		    },
		    {coordinates:{lat:33.5606 , lng:35.5333 }		     
		    },
		    {coordinates:{lat:33.2667 , lng:35.5833 }		         
		    },
		    {coordinates:{lat:33.9697 , lng:35.5664 }		          
		    },
			{coordinates:{lat:33.8439 , lng:35.8936 }	         
		    },		       
			{coordinates:{lat:33.3833 , lng:35.6 }		    
		    },
		    {coordinates:{lat:34.0061 , lng:35.65 }		          
		    },
		    {coordinates:{lat:34.2994 , lng:36.0311 }		         
		    },    
		    {coordinates:{lat:33.8333 , lng:36.0797 }		          
		    },
			{coordinates:{lat:33.3667 , lng:35.4333 }	         
		    },		       
			{coordinates:{lat:33.8906 , lng:35.5844 }		    
		    },
		    {coordinates:{lat:34.3997 , lng:35.6581 }		          
		    },
		    {coordinates:{lat:34.1167 , lng:35.7842 }		         
		    },
		    {coordinates:{lat:34.3853 , lng:35.5808 }		          
		    },
		    {coordinates:{lat:34.5428 , lng:36.3847 }	         
		    },		       
			{coordinates:{lat:33.1194 , lng:35.8437 }		    
		    },
		    {coordinates:{lat:33.5417 , lng:36.0972 }		          
		    },
		    {coordinates:{lat:33.6269 , lng:35.8333 }		         
		    },
		    {coordinates:{lat:34.2553 , lng:35.5131 }		          
		    },
		    {coordinates:{lat:33.6942 , lng:35.3981 }	         
		    },		       
			{coordinates:{lat:34.3942 , lng:35.2 }		    
		    },
		    {coordinates:{lat:33.8053 , lng:35.685 }		          
		    },		    
			{coordinates:{lat: 33.8547, lng: 35.8623}	         
	   		},	       
			{coordinates:{lat: 33.9547, lng: 35.6623}	    
	    	},
	    	{coordinates:{lat: 34.2514, lng: 35.9072}	          
	    	},
	    	{coordinates:{lat: 33.5009, lng: 35.8623}	         
	    	},
	    	{coordinates:{lat: 33.8547, lng: 35.6156}	          
	    	},	      
	            ];

	   
	  /*  for(var i=0;i<markers.length;i++)
        {
	    new MarkerClusterer(map, markers[i].coordinates, {
	        imagePath:
	          "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
	      });
        }
*/

	    var infowindow = new google.maps.InfoWindow();
	   // set_markers(markers,map,infowindow);

	    var myNewMarkersArray= [];

	    for (var i = 0; i < markers.length; i++) {
	        const latLng = new google.maps.LatLng(
	          markers[i].coordinates.lat,
	          markers[i].coordinates.lng
	        );
	        console.log(markers[i].coordinates.lat+"/////"+markers[i].coordinates.lng);

	        const myNewmarker = new google.maps.Marker({
	          position: latLng,
	          label: labels[i % labels.length],
	          icon: iconBase + "parking_lot_maps.png",
	          
	        });
	        myNewMarkersArray.push(myNewmarker);

	        google.maps.event.addListener(myNewmarker, 'mouseover', function () {
	            infowindow.setContent('<p style="font-size:20px;"> My position is :' + this.position+'</p>');
	            infowindow.open(map, this);
	        });
	        google.maps.event.addListener(myNewmarker, 'mouseout', function () {
	            
	        	infowindow.close(map, this);
	        });


	        myNewmarker.addListener("click", () => {
				  var zoom=map.getZoom();
				  if(zoom>=22)
	    		  {
					zoom=0;
	    		  }
				  else{
	    		  zoom+=5;
				  }
				  //zoom+=8;
				 
				  console.log(zoom);
			    map.setZoom(zoom);
			    map.setCenter(myNewmarker.getPosition());
			  });


	        myNewmarker.addListener("dblclick", () => {

		        console.log("hiiiiiiii"+myNewmarker.label);
	        });
			  
	      }
	    console.log(myNewMarkersArray.length);
	     

	      markerClusterer = new MarkerClusterer(map, myNewMarkersArray, {
	        
	        imagePath:
	            "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
	        
	        
	      });

	      


	      /*  for(var i=0;i<markersArray.length;i++){

	        addMarker(markersArray[i]);
	       
	        }*/


	
		// Draw road directional path between 2 nodes
		calculateRoute(saida,beirut,directionsDisplay,directionsService);
	   	//drawStraightPath(33.585599,35.384544,33.88894,35.49442,map);
	    document.getElementById('get').onclick=function(){
		    
	        

	        calculateRoute(siteSource1,siteDest1,directionsDisplay,directionsService);
	        drawStraightPath(lat1,lng1,lat2,lng2,map);

	       
	       
	        
	    };

	 ////// Calculate and display the distance between markers
	    document.getElementById('getDistance').onclick=function(){

	    	 var mk1 = new google.maps.Marker({position: dakota, map: map});
		        var mk2 = new google.maps.Marker({position: frick, map: map});
		        haversine_distance(mk1, mk2);

		     
		        var distance = haversine_distance(mk1, mk2);
		        document.getElementById('msg').innerHTML = "<b>Distance between markers: " + distance.toFixed(2) + " miles.</b>";
	    };
	   
	   // var mapOptions = { zoom: 2, center: new google.maps.LatLng(0, 60) };
	   // var map = new google.maps.Map(document.getElementById('mapContainer'), mapOptions);
	  

}

////////////***************  Map's Functions  ***********////////////////////


/*
function set_markersssss(array,map,infowindow) {

    var map;

    for (var i = 0; i < array.length; i++) {
    	
        var single_location1 = array[i].coordinates.lat;
        var single_location2 = array[i].coordinates.lng;
        console.log(single_location1+"||||||"+single_location1);
        var myLatLng = new google.maps.LatLng(single_location1, single_location2);
        var marker = new google.maps.Marker({
            position: myLatLng,
            map: map
	          //iconImage:iconBase+'beachflag.png',
            
           
            
           
        });
		
        google.maps.event.addListener(marker, 'mouseover', function () {
            infowindow.setContent('<h3> My position is :' + this.position+'</h3>');
            infowindow.open(map, this);
        });
        google.maps.event.addListener(marker, 'mouseout', function () {
            
        	infowindow.close(map, this);
        });

        //*********Recentering the map on the marker click previously***********
        
       /* map.addListener("center_changed", () => {
		    // 3 seconds after the center of the map has changed, pan back to the
		    // marker.
		    window.setTimeout(() => {
		      map.panTo(marker.getPosition());
		    }, 1000);
		  });

		  // Marker Double clicking to zoom in
		  
		  
		  
		  
		  marker.addListener("click", () => {
			  var zoom=map.getZoom();
			  if(zoom>=22)
    		  {
				zoom=0;
    		  }
			  else{
    		  zoom+=5;
			  }
			  //zoom+=8;
			 
			  console.log(zoom);
		    map.setZoom(zoom);
		    map.setCenter(marker.getPosition());
		  });

		 
		  google.maps.event.addListener(marker, "click", function (e) { 
              
           });
    }
}


*/

//Add marker function 

/*function addMarker(props,map){
     var marker= new google.maps.Marker({
     position:props.coordinates,
     icon:props.iconImage,
     content:props.content,
     map:map

     });

    if(props.iconImage){

    //Set icon Image
     marker.setIcon(props.iconImage);
     }

    if(props.content){

     //set content for the marker
     var infoWindow=new google.maps.InfoWindow({
     content:props.content
         });
    marker.addListener('click',function(){
    infoWindow.open(map,marker);
         });
     }
 }
*/
function haversine_distance(mk1, mk2) {
    var R = 3958.8; // Radius of the Earth in miles
    var rlat1 = mk1.position.lat() * (Math.PI/180); // Convert degrees to radians
    var rlat2 = mk2.position.lat() * (Math.PI/180); // Convert degrees to radians
    var difflat = rlat2-rlat1; // Radian difference (latitudes)
    var difflon = (mk2.position.lng()-mk1.position.lng()) * (Math.PI/180); // Radian difference (longitudes)

    var d = 2 * R * Math.asin(Math.sqrt(Math.sin(difflat/2)*Math.sin(difflat/2)+Math.cos(rlat1)*Math.cos(rlat2)*Math.sin(difflon/2)*Math.sin(difflon/2)));
    return d;
  }

function calculateRoute(source,destination,directionsDisplay,directionsService){

    var request={
        origin:source,
        destination:destination,
        travelMode:'DRIVING'
    };
    directionsService.route(request,function(result,status){

        if(status=='OK'){
            directionsDisplay.setDirections(result);
        }

    });

}


//Drawing a line offside between 2 nodes
function drawStraightPath(Long1,Lat1,long2,lat2,map){ 
 var path = [
 new google.maps.LatLng(Long1,Lat1),
 new google.maps.LatLng(long2,lat2)];
 var pathOptions = { path: path, strokeColor: "red", strokeWeight: 2 }
 var myPath = new google.maps.Polyline(pathOptions);
 myPath.setMap(map);
// drawStraightPath({33.585599,35.384544},{33.88894,35.49442});
		}	


//The CenterControl adds a control to the map that recenters the map
//This constructor takes the control DIV as an argument.
	function CenterControl(controlDiv, map) {
      // Set CSS for the control border.
      const controlUI = document.createElement("div");
      controlUI.style.backgroundColor = "#fff";
      controlUI.style.border = "2px solid #fff";
      controlUI.style.borderRadius = "3px";
      controlUI.style.boxShadow = "0 2px 6px rgba(0,0,0,.3)";
      controlUI.style.cursor = "pointer";
      controlUI.style.marginTop = "10px";
      controlUI.style.textAlign = "center";
      controlUI.title = "Click to recenter the map";
      controlDiv.appendChild(controlUI);
      // Set CSS for the control interior.
      const controlText = document.createElement("div");
      controlText.style.color = "rgb(25,25,25)";
      controlText.style.fontFamily = "Roboto,Arial,sans-serif";
      controlText.style.fontSize = "16px";
      controlText.style.lineHeight = "36px";
      controlText.style.paddingLeft = "5px";
      controlText.style.paddingRight = "5px";
      controlText.innerHTML = "Center Map";
      controlUI.appendChild(controlText);
      // Setup the click event listeners: simply set the map to Chicago.
      controlUI.addEventListener("click", () => {
        map.setCenter({lat: 33.8547, lng: 35.8623});
      });
    }
	   
	    
/////////////////**********====> End of map  <====************/////////////




</script>
</body>
</html>