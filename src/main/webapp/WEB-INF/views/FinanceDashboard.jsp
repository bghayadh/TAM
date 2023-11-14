<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>Finance Dashboard</title>

	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	
	<!-- Charts  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusionchartsold.charts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusionchartsold.js"></script>
	
	<!-- Google maps  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>

<style>

hr{
margin-right: 15px;
margin-left: 15px;
}

.title {
  margin:5px 0px;
  font-size: 25px;
  font-weight:600;
  font-family: 'Times New Roman', Times, serif;
}

.canvas-style2{
height: 550px !important; 
padding-bottom: 30px !important;
}

.canvas-style3{
height: 600px !important; 
}

#initCostMapContainer,#netCostMapContainer {
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
.green {
background:green;

}
.red {
background:red;

}


</style>

</head>
<body >

  <c:set var="pg" value="financedashboard" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
	<p></p>
	

<!--Top 10 Site Asset (depending on init cost) -->	

 <hr>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12" >
					<div class="card-body " style="border:  groove #FDFEFE ;">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
								<div class="card-group">
								    <div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">
												<canvas id="sitesMaxInitCost" class="canvas-style2"></canvas>
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
		
<!--Least 10 Site Asset (depending on init cost) -->	

 <hr>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12" >
					<div class="card-body " style="border:  groove #FDFEFE ;">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
								<div class="card-group">
								    <div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">
												<canvas id="sitesMinInitCost" class="canvas-style2"></canvas>
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
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12" >
					<div class="card-body " style="border:  groove #FDFEFE ;">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
								<div class="card-group">
								<div class="col-md-12  ">
								<div class="card card-primary card-tabs cards-margin">
								<div class="legendContainer">
      							<div class="card-body">
							         <div class="box stack-top" id="initCostLegendDiv" style="overflow-y:scroll;position: relative;top:230px;width: 300px; float:left; height:200px;  background:white; margin:50px;display: none">
							         <div class="legendHeader"  id="legendHeader">
 									 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6>
 									 </div>
  								<div id="tableDiv">
  								<table id="maxMinInitCostSites">   
        							<caption style="text-align: center; font-weight: bold; font-size: 2.1ex; margin-top: -75px;margin-left:4px;">Sites Initial Cost</caption>
  								<br>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>  
								 </tr>
 								 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="initCostMaxSites" onclick="showHideAllSites('initCostMaxSites');" value="green"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top 10 Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="initCostMinSites" onclick="showHideAllSites('initCostMinSites');" value="red"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least 10 Sites</label></td>   
								 </tr>
  						</table> 
		</div></div></div>
       
    <div class="card-body">
        <div class="box" id="initCostMapContainer"></div>
        </div>
       </div>
       </div></div></div></div>
						</div>
					</div>
			</div>
		</div>
	</div>

<!--Top 10 Site Asset (depending on net cost) -->	

 <hr>
	<div class="container-fluid ">
		<div class="row">
			<div class="col-md-12" >
					<div class="card-body " style="border:  groove #FDFEFE ;">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
								<div class="card-group">
								    <div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">
												<canvas id="sitesMaxNetCost" class="canvas-style2"></canvas>
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
	
	<!--Least 10 Site Asset (depending on net cost) -->	

 <hr>
	<div class="container-fluid ">
		<div class="row">
			<div class="col-md-12" >
					<div class="card-body " style="border:  groove #FDFEFE ;">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
								<div class="card-group">
								    <div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">
												<canvas id="sitesMinNetCost" class="canvas-style2"></canvas>
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
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12" >
					<div class="card-body " style="border:  groove #FDFEFE ;">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
								<div class="card-group">
								<div class="col-md-12  ">
								<div class="card card-primary card-tabs cards-margin">
								<div class="legendContainer">
      							<div class="card-body">
							         <div class="box stack-top" id="netCostLegendDiv" style="overflow-y:scroll;position: relative;top:230px;width: 300px; float:left; height:200px;  background:white; margin:50px;display: none">
							         <div class="legendHeader"  id="legendHeader">
 									 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6>
 									 </div>
  								<div id="tableDiv">
  								<table id="maxMinInitCostSites">   
        							<caption style="text-align: center; font-weight: bold; font-size: 2.1ex; margin-top: -75px;margin-left:4px;">Sites Net Cost</caption>
  								<br>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>  
								 </tr>
 								 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="netCostMaxSites" onclick="showHideAllSites('netCostMaxSites');" value="green"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top 10 Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="netCostMinSites" onclick="showHideAllSites('netCostMinSites');" value="red"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least 10 Sites</label></td>   
								 </tr>
  						</table> 
		</div></div></div>
       
    <div class="card-body">
        <div class="box" id="netCostMapContainer"></div>
        </div>
       </div>
       </div></div></div></div>
						</div>
					</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">

var initCostMap;
var netCostMap;

var maxInitCostAssetList;
var minInitCostAssetList;
var maxNetCostAssetList;
var minNetCostAssetList;
var siteName=[];
var netCost=[];
var initCost=[];
var markersMaxInitCostSites=[];
var markersMinInitCostSites=[];
var markersMaxNetCostSites=[];
var markersMinNetCostSites=[];
var infoWindowInitCost;
var infoWindowNetCost;



function initMap() {

	 maxInitCostAssetList=${sitesMaxInitCostAsset};
	 minInitCostAssetList=${sitesMinInitCostAsset};
	 maxNetCostAssetList=${sitesMaxNetCostAsset};
	 minNetCostAssetList=${sitesMinNetCostAsset};

	 initCostMap = new google.maps.Map(document.getElementById("initCostMapContainer"), {
			center: { lat: 1, lng: 38 },
			zoom: 6,
	 		mapTypeControl: true,
	 		mapTypeId: google.maps.MapTypeId.ROADMAP,
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
	  });

		const mapLegendControlDiv = document.createElement("div");
		ShowHideMapLegend(mapLegendControlDiv, initCostMap);
		initCostMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(mapLegendControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, initCostMap);
	    initCostMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);


	    netCostMap = new google.maps.Map(document.getElementById("netCostMapContainer"), {
			center: { lat: 1, lng: 38 },
			zoom: 6,
	 		mapTypeControl: true,
	 		mapTypeId: google.maps.MapTypeId.ROADMAP,
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
	  });

		const netCostMapLegendControlDiv = document.createElement("div");
		ShowHideMapLegend(netCostMapLegendControlDiv, netCostMap);
		netCostMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(netCostMapLegendControlDiv);

	    const netCostMapDefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(netCostMapDefaultZoomDiv, netCostMap);
	    netCostMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(netCostMapDefaultZoomDiv);
	  

	 infoWindowInitCost = new google.maps.InfoWindow(); // Define the info window to use it when clicking on marker
	 infoWindowNetCost = new google.maps.InfoWindow(); // Define the info window to use it when clicking on marker
	 
	 $("#initCostLegendDiv").toggle(); // to open the legend 
	 $("#netCostLegendDiv").toggle(); // to open the legend 
	 

	 fetchTopSitesMaxInitCost();
	 fetchLeastSitesMinInitCost();
	 fetchTopSitesMaxNetCost();
	 fetchLeastSitesMinNetCost();
	 createSiteMarker(maxInitCostAssetList,"green",initCostMap,markersMaxInitCostSites,"initCostMaxSites",infoWindowInitCost);
	 createSiteMarker(minInitCostAssetList,"red",initCostMap,markersMinInitCostSites,"initCostMinSites",infoWindowInitCost);
	 createSiteMarker(maxNetCostAssetList,"green",netCostMap,markersMaxNetCostSites,"netCostMaxSites",infoWindowNetCost);
	 createSiteMarker(minNetCostAssetList,"red",netCostMap,markersMinNetCostSites,"netCostMinSites",infoWindowNetCost);

}

//Add legend button under zoom control on map
function ShowHideMapLegend(controlDiv, map) {
	
    const controlUI = document.createElement("dv");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"><i class="fas fa-arrow-right fa-lg "></i></button>';
    controlUI.title = "Open Legend";
    controlDiv.appendChild(controlUI);

    controlUI.addEventListener("click", () => {
    	$("#legendDiv").toggle();        
     });

  }
 
//Add defaultZoom button under zoom control on map
function DefaultZoomControl(controlDiv, map) {
	
    const controlUI = document.createElement("div");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.style.marginTop = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;" ><i class="fa fa-undo fa-lg "></i></button>';
    controlUI.title = "Reset Default Zoom";
    controlDiv.appendChild(controlUI);

    controlUI.addEventListener("click", () => {
    	var center=new google.maps.LatLng(1,38);
        map.setCenter(center);
        map.setZoom(6);        
     });

  }
function fetchTopSitesMaxInitCost(){
	 siteName=[];
	 initCost=[];
	
for (var i = 0; i < maxInitCostAssetList.length; i++) {
	 siteName.push(maxInitCostAssetList[i][1]+" : "+maxInitCostAssetList[i][2]);
	 initCost.push(maxInitCostAssetList[i][0]);
}
		
		var ctxLine = document.getElementById('sitesMaxInitCost').getContext('2d');
		var myChartLine = new Chart(ctxLine, {
		    type: 'line',				    
		    data: {
		    	labels: siteName,
		        datasets: [{
		            label: 'Initial Cost',
		            data: initCost,
		            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
		            borderColor: '#008000',
		            borderWidth: 3,
		            fill: false
		        },
		    
		        ]
		    },
		    options: {
		    	legend: {
		    	    labels: {
		    	       fontSize: 18,
		    	       fontColor: "black",
		    	    }
		    	},
		    	 tooltips: { // This tooltips to  Format the value with commas for thousands and decimals
		                callbacks: {
		                    label: function (tooltipItem, data) {
		                        var value = tooltipItem.yLabel;
		                        return value.toLocaleString(undefined, { maximumFractionDigits: 2 });
		                    }
		                }
		            },
		    	pointLabels: { fontSize:20 },
		    	  title: {
		              display: true,
		              text: 'Top 10 Site Depending On Initial Cost',
		              fontFamily: 'sans-serif',
		              fontColor: '#08526D',
		              fontSize: 22
		          },
		     
		    }
		});
}	

function fetchLeastSitesMinInitCost(){
	
    siteName=[];
    initCost=[];
	for (var i = 0; i < minInitCostAssetList.length; i++) {
	 siteName.push(minInitCostAssetList[i][1]+" : "+minInitCostAssetList[i][2]);
	 initCost.push(minInitCostAssetList[i][0]);
	}
	var ctxLine = document.getElementById('sitesMinInitCost').getContext('2d');

	var myChartLine = new Chart(ctxLine, {
		type: 'line',				    
		data: {
			labels: siteName,
			datasets: [{
				label: 'Initial Cost',
				data: initCost,
				backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				borderColor: 'red',
				borderWidth: 3,
				fill: false
			},
		
			]
		},
		options: {
			legend: {
				labels: {
				   fontSize: 18,
				   fontColor: "black",
				}
			},
			 tooltips: { // This tooltips to  Format the value with commas for thousands and decimals
	                callbacks: {
	                    label: function (tooltipItem, data) {
	                        var value = tooltipItem.yLabel;
	                        return value.toLocaleString(undefined, { maximumFractionDigits: 2 });
	                    }
	                }
	            },
			pointLabels: { fontSize:20 },
			  title: {
				  display: true,
				  text: 'Least 10 Site Depending On Initial Cost',
				  fontFamily: 'sans-serif',
				  fontColor: '#08526D',
				  fontSize: 22
			  },
		 
		}
	});	
}	

function fetchTopSitesMaxNetCost(){
	siteName=[];
	netCost=[];
	
	 for (var i = 0; i < maxNetCostAssetList.length; i++) {
		 siteName.push(maxNetCostAssetList[i][1]+" : "+maxNetCostAssetList[i][2]);
		 netCost.push(maxNetCostAssetList[i][0]);
	 }
		
		var ctxLine = document.getElementById('sitesMaxNetCost').getContext('2d');
		var myChartLine = new Chart(ctxLine, {
		    type: 'line',				    
		    data: {
		    	labels: siteName,
		        datasets: [{
		            label: 'Net Cost',
		            data: netCost,
		            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
		            borderColor: '#008000',
		            borderWidth: 3,
		            fill: false
		        },
		    
		        ]
		    },
		    options: {
		    	legend: {
		    	    labels: {
		    	       fontSize: 18,
		    	       fontColor: "black",
		    	    }
		    	},
		    	 tooltips: { // This tooltips to  Format the value with commas for thousands and decimals
		                callbacks: {
		                    label: function (tooltipItem, data) {
		                        var value = tooltipItem.yLabel;
		                        return value.toLocaleString(undefined, { maximumFractionDigits: 2 });
		                    }
		                }
		            },
		    	pointLabels: { fontSize:20 },
		    	  title: {
		              display: true,
		              text: 'Top 10 Site Depending On Net Cost',
		              fontFamily: 'sans-serif',
		              fontColor: '#08526D',
		              fontSize: 22
		          },
		     
		    }
		});
}	

function fetchLeastSitesMinNetCost(){
	
    siteName=[];
    netCost=[];
    
	for (var i = 0; i < minNetCostAssetList.length; i++) {
		 siteName.push(minNetCostAssetList[i][1]+" : "+minNetCostAssetList[i][2]);
		 netCost.push(minNetCostAssetList[i][0]);
	}
	
	var ctxLine = document.getElementById('sitesMinNetCost').getContext('2d');

	var myChartLine = new Chart(ctxLine, {
		type: 'line',				    
		data: {
			labels: siteName,
			datasets: [{
				label: 'Net Cost',
				data: netCost,
				backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				borderColor: 'red',
				borderWidth: 3,
				fill: false
			},
		
			]
		},
		options: {
			legend: {
				labels: {
				   fontSize: 18,
				   fontColor: "black",
				}
			},
			 tooltips: { // This tooltips to  Format the value with commas for thousands and decimals
	                callbacks: {
	                    label: function (tooltipItem, data) {
	                        var value = tooltipItem.yLabel;
	                        return value.toLocaleString(undefined, { maximumFractionDigits: 2 });
	                    }
	                }
	            },
			pointLabels: { fontSize:20 },
			  title: {
				  display: true,
				  text: 'Least 10 Site Depending On Net Cost',
				  fontFamily: 'sans-serif',
				  fontColor: '#08526D',
				  fontSize: 22
			  },
		 
		}
	});	
}

function createSiteMarker(list,iconColor,map,markersArray,legendCheckboxClass,infoWindow) {

	
	var siteID;	
	var cost;
		
	if(list.length!=0){

		$('.'+legendCheckboxClass).prop('checked', true);
		$('.'+legendCheckboxClass).attr('disabled', false);
		
		for(i=0;i<list.length;i++){
			siteID=list[i][1];	

			//Format the value with commas for thousands
			cost=list[i][0].toFixed(2);
			cost = cost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
				
			
			const pos = new google.maps.LatLng(list[i][4],list[i][3]);

			 iconSite ={
					path: google.maps.SymbolPath.CIRCLE,
			        fillOpacity: 0.9,
			        strokeColor: 'transparent',
			        strokeOpacity: 0.9,
			        strokeWeight: 1,
			        scale: 9,
			        fillColor:iconColor
			 };
			var siteIdInfo ="<b style='font-size:13px;'><u>Site ID: </u></b>"+siteID;
			var siteNameInfo ="<b style='font-size:13px;'><u>Site Name: </u></b>"+list[i][2];

			if(infoWindow==infoWindowInitCost) {
				var siteCost ="<b style='font-size:13px;'><u>Initial Cost: </u></b>"+cost;
			}
			else {
				var siteCost ="<b style='font-size:13px;'><u>Net Cost: </u></b>"+cost;

			}
			
			var data="<div></br>"+siteIdInfo+"</br>"+siteNameInfo+"</br>"+siteCost+"</div>";			

			if(!markersArray[siteID]){
				siteMarker = new google.maps.Marker({
					position: pos,
					ID:siteID,
					icon:iconSite,
			        data:data
			});	
			siteMarker.metadata = { id: siteID };
			markersArray[siteID] = siteMarker;
			markersArray.push(siteMarker);
			//markerClusterFarSites.addMarker(markersFarSites[""+markerId]);
			markersArray[siteID].setMap(map);
						

			
			google.maps.event.addListener(siteMarker, "click", function (e) {
				infoWindow.close();
	            infoWindow.setContent(this.data); 
	            infoWindow.open(map,this);				
		 	});
			
					         
		}

	}
	
	}

}	   
function showHideAllSites(checkboxClass){
	var lst;
	var allMarkersArray;
	var map;

	if(checkboxClass=="initCostMaxSites") {

		allMarkersArray=markersMaxInitCostSites;
		lst=maxInitCostAssetList;
		map=initCostMap;
		
	}
	else if(checkboxClass=="initCostMinSites") {
		allMarkersArray=markersMinInitCostSites;
		lst=minInitCostAssetList;
		map=initCostMap;
		
	}
	else if(checkboxClass=="netCostMaxSites") {

		allMarkersArray=markersMaxNetCostSites;
		lst=maxNetCostAssetList;
		map=netCostMap;
	}
	else {
		allMarkersArray=markersMinNetCostSites;
		lst=minNetCostAssetList;
		map=netCostMap;

	}
	
	$('.'+checkboxClass).bind("change",function() {
		//markerClusterFarSites.clearMarkers();	
					
			if ($(this).is(':checked')){
				for(var x=0;x<lst.length;x++) {
					siteID = lst[x][1];
					if(allMarkersArray[siteID].getMap()==null){
						allMarkersArray[siteID].setMap(map);			
						//markerClusterFarSites.addMarker(markersFarSites[siteID]);
					}
				}
			}
			else {
				for(var x=0;x<lst.length;x++) {
					siteID = lst[x][1];
					allMarkersArray[siteID].setMap(null);								
				}
		   }
			
		});	
}	


</script>	
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>