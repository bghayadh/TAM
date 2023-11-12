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
		
<!--Less 10 Site Asset (depending on init cost) -->	

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
</body>

<script type="text/javascript">

$(document).ready(function() {

	var maxInitCostAssetList=${sitesMaxInitCostAsset};
	var minInitCostAssetList=${sitesMinInitCostAsset};
	var maxNetCostAssetList=${sitesMaxNetCostAsset};
	var minNetCostAssetList=${sitesMinNetCostAsset};
	var siteName=[];
	var netCost=[];
	var initCost=[];

	fetchTopSitesMaxInitCost();
	fetchLeastSitesMinInitCost();
	fetchTopSitesMaxNetCost();
	fetchLeastSitesMinNetCost();
	
	function fetchTopSitesMaxInitCost(){
		 siteName=[];
		 initCost=[];
		
	 for (var i = 0; i < maxInitCostAssetList.length; i++) {
		 siteName.push(maxInitCostAssetList[i][2]+" : "+maxInitCostAssetList[i][1]);
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
		 siteName.push(minInitCostAssetList[i][2]+" : "+minInitCostAssetList[i][1]);
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
			 siteName.push(maxNetCostAssetList[i][2]+" : "+maxNetCostAssetList[i][1]);
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
			 siteName.push(minNetCostAssetList[i][2]+" : "+minNetCostAssetList[i][1]);
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
		
 });

</script>	
</html>