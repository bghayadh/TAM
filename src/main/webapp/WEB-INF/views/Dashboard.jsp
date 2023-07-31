<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>General Dashboard</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--  
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	jquery.easypiechart.js
	-->

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

 <script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusionchartsold.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">


<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />

<!-- 	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/easypiechart.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusionchartsold.js"></script>
	
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/easypiechart-data.js"></script>
-->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>


	  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusionchartsold.charts.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link
	href="${pageContext.request.contextPath}/resources/css/all.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header.css">
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	
	
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/js/Dashboard/GeneralDashboard.js"></script>

<style>
hr{
margin-right: 15px;
margin-left: 15px;
}
.flex {
  display: flex;
  justify-content: center;
}
#mapContainer {
	width: 100%;

	height: 100%;
}
.legendContainer{
height: 800px;
position: relative;
}
.legendHeader {
overflow: hidden;
background-color: #08526d;
padding: 10px 0px;
}
.box{
width: 100%;
height: 100%;            
position: absolute;
top:1px;
left: 0;
}

.stack-top{
z-index: 10;
margin: 20px; 
}

.dot {
height: 15px;
width: 15px;
border-radius: 50%;

}
.dotYellow {
height: 15px;
width: 15px;
border-radius: 50%;

background:#FFDC00;

}

.dotBlue {
height: 15px;
width: 15px;
border-radius: 50%;

background:blue;

}
.dotRed {
height: 15px;
width: 15px;
border-radius: 50%;

background:red;

}
.flex-item + .flex-item {
  margin-left: 10px;
  margin-bottom:5px;
}

.hide-row { 
transition: all 300ms ease-in-out;
display:none !important; }

.cadr{
border:0.1em solid #08526d;
}
.title {
  margin:5px 0px;
  font-size: 25px;
  font-weight:600;
  font-family: 'Times New Roman', Times, serif;
}
.canvas-style{
height: 500px; 
}
.pieChartStyle1{
height: 500px; 
width:700px;


}
.pieChartStyle2{
height: 500px; 
width:700px;

}
.canvas-style2{
height: 550px !important; 
padding-bottom: 30px !important;
}
.canvas-style3{
height: 600px !important; 
}
.chart-container {
	height: 130px;
	top: -43px;
	position: relative;
	width: 100%;
	height: 0;
	margin-top: 100%
}

.skill-item {
	position: inherit;
	width: 100%;
	color: #555;
}

.mar {
	margin-top: 30%
}

.center {
	margin-top: 20%;
	position: relative;
	height: 100%;
	width: 100%;
	border: 1px solid black;
	text-align: center;
	position: relative;
}
.spin{
 display: block;
  position: fixed;
  z-index: 1031; /* High z-index so it is on top of the page */
  top: 50%;
  right: 50%; /* or: left: 50%; */
  margin-top: -..px; /* half of the elements height */
  margin-right: -..px;
  transition: all 300ms ease-in-out;
}


.box{
width: 100%;
height: 100%;            


left: 0;
}
</style>

</head>
<body >

  <c:set var="pg" value="dashboard" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
<%-- 	<c:set var = "page" value = "dashboard"/> --%>
<%-- 	<%@ include file="header.html" %> --%>
	<!--end of header part-->

	<p></p>

	<div class="container-fluid">
		<div class="row">
		<div id="spinner" class=" spinner-border spin " style="width: 5rem; height: 5rem; color: #08526d;" role="status">
  <span class="sr-only">Loading...</span>
</div>
		
	
	<!-- end of this row -->
</div>


	<!-- end container -->
</div>



	


	<!-- start charts -->
	<!-- ********************** first row of charts ********************** -->
<!--Technology Sites Count /Purchase Request-Purchase Order-->	
<div class="container-fluid " >
		<div class="row"  >
			<div class="col-md-12"  >
				
					<div class="card-body "  style=" border: groove #FDFEFE; ">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group">
							
									<div class="col-md-7 " style="margin-top:5px">
										
											

												<div id="pichart-container"  ></div>

										
		
									
									</div>
									
										
									<div class="col-md-5"  >

										<div class="row cards-margin" style="margin-top: 5px; ">

											<div class="col-md-6" >

												<div class="card">

													<div class="card-header" style="text-align: center; color: black; font-weight: 600;">Purchase
														Order</div>
													<div class="card-body" style="text-align: center;">
														<!-- second chart -->

														<div class="chart " id="POchart" data-bar-color="#23afe3" >
															<!-- ********************** second chart ********************** -->
															<div class="row" style="top: 110px; position: relative;">
																<span id="poPercent" class="percent col-sm"
																	style="text-align: bottom; font: bold;"></span>
															</div>

														</div>

														<div class="row" style="top: 8px; position: relative;">

															<div class="col-xs" style="text-align: right;">All
																Purchase Order:</div>
															<div class="col-sm" style="text-align: left;"
																id="poCount"></div>
														</div>

														<div class="row" style="top: 10px; position: relative;">
															<div class="col-xs" style="text-align: right;">Completed
																Purchase Order:</div>
															<div class="col-sm" style="text-align: left;"
																id="poComplete"></div>
														</div>
														</div>
													<!-- ca here -->

													<div class="card-footer text-muted"
														style="font-size: 10px; font-family: serif; font-weight: 600;">
														Purchase Order Complete / Purchase Order Count</div>
												</div>
											</div>
											<div class="col-md-6">

												<div class="card">

													<div class="card-header" style="text-align: center; color: black; font-weight: 600;">Purchase
														Request</div>
													<div class="card-body" style="text-align: center;">
														<!-- second chart -->
														<!-- ********************** third chart ********************** -->

														<div class="chart " id="PRchart" data-bar-color="#23afe3">
															<div class="row" style="top: 110px; position: relative;">
																<span class="percent col-sm"
																	style="text-align: bottom; font: bold;" id="prPercent"></span>

															</div>
														</div>

														<div class="row" style="top: 8px; position: relative;">

															<div class="col-xs" style="text-align: right;">All
																Purchase Request:</div>
															<div class="col-sm" 
																id="prCount"></div>

														</div>

														<div class="row" style="top: 10px; position: relative;">

															<div class="col-xs" style="text-align: right;">Completed
																Purchase Request:</div>

															<div class="col-sm" style="text-align: left; margin-left:-10px;" 
																id="prComplete"></div>

														</div>

													</div>

													<div class="card-footer text-muted"
														style=" font-size: 10px; font-family: serif; font-weight: 600;">Purchase Request Complete/Purchase Request Count
														 </div>
												</div>

											</div>

										</div>
										<!-- end second two charts -->
									</div>
								</div>
							</div>
						</div>
					</div>
			
			</div>

			<!-- end of 12 -->
		</div>
		<!-- end row -->
	</div>
	
	<hr class="assetfinance">
<div class="container-fluid " >
		<div class="row"  >
			<div class="col-md-12"  >
				
					<div class="card-body "  style=" border: groove #FDFEFE; ">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group">
							
									<div class="col-md-6 " style="margin-top:5px">
										
											

											<div id="RevenuePerService" style="height:420px"></div>	

										
		
									
									</div>
										
										<div class="col-md-6" style="margin-top:5px">
											<div id="RevenuePerRegion" style="height:420px"></div>
                                     
												
                                       </div>
										
		                              
									
									
										
						
								</div>
							</div>
						</div>
					</div>
			
			</div>

			<!-- end of 12 -->
		</div>
		<!-- end row -->
	</div>

			<hr class="assetfinance">
<div class="container-fluid " >
		<div class="row"  >
			<div class="col-md-12"  >
				
					<div class="card-body "  style=" border: groove #FDFEFE; ">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group">
							
									<div class="col-md-6 " style="margin-top:5px">
										
										 <div id="RevenuePerTechnology" style="height:420px"></div>	

										
									
									</div>
										
										
									<div class="col-md-6" style="margin-top:5px">
											<div id="SitesPerRegion" style="height:420px"></div>
                                     
												
                                       </div>
										
									
										
						
								</div>
							</div>
						</div>
					</div>
			
			</div>

			<!-- end of 12 -->
		</div>
		<!-- end row -->
	</div>
	
	<!-- Site Technologies Revenue Analysis -->
	
	<hr class="assetfinance">
<div class="container-fluid profitandloss">
	
		<div class="row">
		
			<div class="col-md-12">
			
	
			
					<div class="card-body " style=" border: groove #FDFEFE; ">
							<div class="row"   >
				<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1"
						data-target-input="nearest">
						<span class="input-group-text">From Date</span> <input type="text"
							id="fromDate" name="fltFormDate"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker1" />

						<div class="input-group-append" data-target="#datetimepicker1"
							data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>

					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker2"
						data-target-input="nearest">
						<span class="input-group-text">To Date</span> <input type="text"
							id="toDate" name="fltToDate"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker2" />
						<div class="input-group-append" data-target="#datetimepicker2"
							data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>

					</div>
				</div>
			</div>
	


	<div class="col-md-1">
		<div class="form-group">
			<button type="button" class="btn btn-outline-success" id="submit"
				style="width: 100%;">Submit</button>
		</div>
	</div>
			</div>
				<hr class="assetfinance">	
								<div class="row">
								
									
									<div class="col-md-12" style=" margin-left:15px ;" >
										
											<div class="card-body canvas-style3"  >
												<!--  *************************** chart container line  *************************** -->
												<canvas id="myChartLine2"  class="canvas-style2"></canvas>
												</div>
											
										
									</div>
									
									
									
				
				</div>
			</div>			
		
	
	</div>
	
	</div>
	
	
	</div>
		<!--Top 10 Site Profit -->	
	
			<hr class="profitandloss">
				<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12" >
				
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">

												<canvas id="top10revenue" class="canvas-style2"></canvas>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>
	<hr class="profitandloss">
				<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12" >
				
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">

												<canvas id="top10Less" class="canvas-style2"></canvas>

											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>
	
	
		<hr class="profitandloss">
				<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12" >
				
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											       <div class="legendContainer">
      <div class="card-body">
      
      
         <div class="box stack-top" id="legendDiv" style="overflow-y:scroll;position: relative;top:230px;width: 300px; float:left; height:200px;  background:white; margin:50px;display: none">
        
         <div class="legendHeader"  id="legendHeader">
   
 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6>
 

  </div>
  
   <div id="tableDiv">
  <table id="sitesWithTech">
  
   
  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    
    
  </tr>
  
  <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="MaxRev" onclick="ShowHideMarkers('blue');" value="blue"/></td>
    <td style="position: relative;top:27px;left:50px;"><div class="dotBlue"></div></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>
        
    
    <td style="position: relative;top: 30px;left: 40px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Revenue Sites</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="MinRev" onclick="ShowHideMarkers('red');" value="red"/></td>
        <td style="position: relative;top:27px;left:50px;"><div class="dotRed"></div></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>
    
    <td style="position: relative;top: 30px;left:40px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Revenue Sites</label></td>
    
    
  </tr>
  
    

    
   
  </table>
  
</div>
        </div>
        </div>
         
    <div class="card-body">
        <div class="box" id="mapContainer"></div>
        </div>
       </div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>

		
		
		<hr>
</body>

	<script type="text/javascript">

	// Intializing Variables 
	var map ;
	var infowindow ;
	var infowindowRed ;
	var markersMore=[];
    var markersless=[];
    var SitesMore=[];
	var SitesLess=[];
	var blueCluster ;
	var RedCluster ;
	var indicatormax='max';
    var indicatormin='min';
    var TechnologyCount = [];
	var RevenueTechnology = [];
	//var Regions=[];
	//var RevenueRegions=[];
	var RegionsSites=[];
	//var AreasSites=[];
	//var sitesRegionCount=[];
	var SitesPerRegions=[];
	var revenuePerRegions=[];
	//var less10Sites=[];
	//var more10Sites=[];
	//var MapMore=[];
	var RevenueTotal=[];
	var TotalRevenueSubmit=[];
	
    $( "#legendDiv" ).draggable(); 
	// Init Map
	function initMap() {
		  SitesMore=${Top10SitesMap};
		  SitesLess=${Less10SitesMap};
	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	 map = new google.maps.Map(document.getElementById("mapContainer"), {

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

     const centerControlDiv = document.createElement("div");
     LegendControl(centerControlDiv, map);
     map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);
	 const DefaultZoomDiv = document.createElement("div");
	 DefaultZoomControl(DefaultZoomDiv, map);
	 map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);
	 infowindow = new google.maps.InfoWindow();
	 infowindowRed = new google.maps.InfoWindow();
	    ShowHideDiv();
	    fetchmap(SitesMore,map,indicatormax);
		fetchmap(SitesLess,map,indicatormin);
		fetchtop10Revenue();
		fetchtop10RevenueLess();
		}



	 	// end of Init map
	 	
	 	
	 	
	 // Fetch Map
	 	
	 	function fetchmap(lst,map,Indicator){

		 var ctx = getContextPath();

       
   if (Indicator=='max')
    {
     if(lst.length!=0){
    
    	 $("#MaxRev").prop('checked',true);  
         
         for(i=0;i<lst.length;i++){

		var SiteName=lst[i][3];	
		var SiteRev=lst[i][2];			         
		
		var siteName="<b style='font-size:13px;'><u>Site: </u></b>"+SiteName;
		var siteLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+lst[i][1];
		var siteLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lst[i][0];
		var siteRev="<b style='font-size:13px;'><u>Revenue:</u> </b>"+SiteRev;
		var data="<div>"+siteName+"</br>"+siteLat+"</br>"+siteLong+ "</br>"+siteRev+ "</div>";
        var latSite=lst[i][1];
        var lngSite=lst[i][0];
		//console.log(latSite.type);

		const myLatLng = { lat: parseFloat(latSite), lng: parseFloat(lngSite) };
		//Definig Marker
		const marker = new google.maps.Marker({
			position: myLatLng,
			data:data,
        map,
       
       icon: {
       	  path: google.maps.SymbolPath.CIRCLE,
		        fillColor: "blue",
		        fillOpacity: 0.9,
		        strokeColor: 'transparent',
		        strokeOpacity: 0.9,
		        strokeWeight: 1,
		        scale: 7	   
           
         }
       
        	    });


		markersMore.push(marker);

		google.maps.event.addListener(marker, "click", function (e) {

	     	infowindow.setContent(this.data); 
       	infowindow.open(map,this);

			
	    		
			
	 			});


    
}		
// Define BlueCluster
 blueCluster = new MarkerClusterer(map,markersMore, {ignoreHiddenMarkers: true, 
	styles: [
	{
	url:  ctx+'/resources/clusterIcons/blueCluster.png',
	height: 65,
	width:65,
	anchorText:[-5,-5]
	},
	], });

}
     }
   


else if (Indicator=='min'){

if(lst.length!=0){
	 $("#MinRev").prop('checked',true);  
 for(i=0;i<lst.length;i++){
	var SiteName=lst[i][3];	
		var SiteRev=lst[i][2];			         
		
		var siteName="<b style='font-size:13px;'><u>Site: </u></b>"+SiteName;
		var siteLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+lst[i][1];
		var siteLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lst[i][0];
		var siteRev="<b style='font-size:13px;'><u>Revenue:</u> </b>"+SiteRev;
		
			
		var data="<div>"+siteName+"</br>"+siteLat+"</br>"+siteLong+ "</br>"+siteRev+ "</div>";
	 var latSite=lst[i][1];
		var lngSite=lst[i][0];
		//console.log(latSite.type);

		const myLatLng = { lat: parseFloat(latSite), lng: parseFloat(lngSite) };
		
		const marker = new google.maps.Marker({
			position: myLatLng,
        map,
       data:data,
      
     
       icon: {
       	  path: google.maps.SymbolPath.CIRCLE,
		        fillColor: "red",
		        fillOpacity: 0.9,
		        strokeColor: 'transparent',
		        strokeOpacity: 0.9,
		        strokeWeight: 1,
		        scale: 7	   
           
         }
       
        	    });

     
		markersless.push(marker);

		google.maps.event.addListener(marker, "click", function (e) {

			infowindowRed.setContent(this.data); 
			infowindowRed.open(map,this);

	 			});

}		

 RedCluster = new MarkerClusterer(map,markersless, {ignoreHiddenMarkers: true, 
	styles: [
	{
	url:  ctx+'/resources/clusterIcons/redCluster.png',
	height: 65,
	width:65,
	anchorText:[-5,-5]
	},
	], });





}


}
  /* var markerCluster = new MarkerClusterer(map, markers,
                {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
               
*/
        
	 }


//Legends Function
		 
	     function ShowHideMarkers(color) {
			 if(color == "blue") {
			
			if ($('#MaxRev').prop("checked") == true) {
						
				fetchmap(SitesMore,map,indicatormax);
              

               // console.log("markers"+markersMore);
					}		
					else {
              
						 for(var i=0;i<markersMore.length;i++){

							 markersMore[i].setMap(null);

							 }

						 markersMore=[];

						 if(typeof blueCluster != 'undefined'){
                        blueCluster.clearMarkers();
						 
						 }

						
			  }	
          
		
		 
			 }

			 else if(color=="red"){
		
				 if ($('#MinRev').prop("checked") == true) {
						
						fetchmap(SitesLess,map,indicatormin);
	                  

	                   // console.log("markers"+markersMore);
							}		
							else {
	                  
								 for(var i=0;i<markersless.length;i++){

									 markersless[i].setMap(null);

									 }

								 markersless=[];
								 if(typeof RedCluster != 'undefined'){RedCluster.clearMarkers();}
								 


								
					  }	


			


		            

				 }



			 
		 }
//Submit
		function SiteRevenueAnalysis(){

		      
	    	$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/SubmitLineChart",
				dataType : "json",
				data : {				
					"fromDate": $('#fromDate').val(),
					"toDate": $('#toDate').val()
					
				},
				success : function(data) {
				
					
					$("#spinner").addClass("hide-row");
				
					TotalRevenueSubmit=jQuery.parseJSON(data.totalRevenueSubmit);	
						
					fetchLineChartDataLoad(TotalRevenueSubmit);
					
					
				},
				error : function(error) {
					
					console.log("The error is " + error);
				}
			});


	        }
 
	$(document).ready(function() {

		/*var d = new Date();
		console.log(d.toLocaleDateString());
		d.setMonth(d.getMonth() - 3);
		console.log(d.toLocaleDateString());
		*/
		var today = new Date();
		//console.log("today is "+today);
		//var a=today.setMonth(today.getMonth()-1);
		//console.log("a"+a);
	    var date = today.setMonth(today.getMonth() - 1);
	  
	  
	   
	    $('#fromDate').datetimepicker({
	        defaultDate: date
	    });
	    // set end date defualt value to current
	    var dateNow = new Date();

	    $('#toDate').datetimepicker({
	        defaultDate: dateNow
	    });
	    $("#spinner").addClass("hide-row");
			var poPercent=${POPercent};
			var poCount=${POCount};
			var poCompleteCount=${POCompleteCount};
			var prPercent=${PRPercent};
			var prCount=${PRCount};
			var prCompleteCount=${PRCompleteCount};
			TechnologyCount=${TechnologyCount};	
			pieRevenue=${pieRevenue};
			RevenueTechnology=${RevenueTechnology};
			SitesPerRegions=${RegionsSites};
			//console.log(SitesPerRegions[0][0]);
			revenuePerRegions=${RegionsRevenues};
			RevenueTotal=${TotalRevenue};
		    
			$("#poPercent").text("% "+poPercent);
			$("#poCount").text(poCount);
			$("#poComplete").text(poCompleteCount);
			$('#POchart').data('easyPieChart').update(poPercent);
			$("#prPercent").text("% "+prPercent);
			$("#prCount").text(prCount);
			$("#prComplete").text(prCompleteCount);
			$('#PRchart').data('easyPieChart').update(prPercent);
			fetchTechnologyChart();
			fetchServiceRevenueChart();
			fetchTechnologyRevChart();
			fetchRevenuePerRegion();
			fetchSitesPerRegion();
			fetchLineChartDataLoad(RevenueTotal);
			
			
			
		//make change date with btn submit
		$("#submit").click(  function() {
			SiteRevenueAnalysis();
			});
		
									
	});

	
	
	</script>
	<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"
	async defer>
	</script>
	
</html>






