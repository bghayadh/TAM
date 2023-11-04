<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
hr{
margin-right: 15px;
margin-left: 15px;
}
.flex {
  display: flex;
  justify-content: center;
}

.test {
  position: relative;
  
  height: 100px;
  width: 300px;
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
.pie-chart{
height: 550px !important; 

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
</style>
<head>
<meta charset="utf-8">
<title></title>

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


<script type="text/javascript">
	
</script>

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
<!--Far Top 10 Supplier -->
	
	
	
	<!-- start second row of charts -->
	<div class="container-fluid assetfinance">
		<div class="row">
			<div class="col-md-12" >
				
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								

									<div class="col-md-12 "  style="border:  groove #FDFEFE ; ">

										

											<div class="card-body "  >

												<div class="tab-content">

													
														<div id="chart-container-2" class="canvas-style2"></div>

													
												</div>
											</div>
										
									</div>
										
									
								
							</div>
						</div>
					
				
			</div>
		</div>
	</div>
	
	
	
	<!--  Last 12 Month (Initial Cost....) -->
	
<hr class="assetfinance">
<div class="container-fluid assetfinance">
		<div class="row ">
			<div class="col-md-12 "  >
				
					<div class="card-body  " style="border:  groove #FDFEFE ; ">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group ">
								<div class="col-sm-12 ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body ">

												<canvas id="myChartLine" class="canvas-style2"></canvas>

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
<!--Far Top 10 Item -->

<hr class="assetfinance">
	<p></p>
	<!-- start third row of charts -->
	<div class="container-fluid assetfinance">
	
		<div class="row">
		
			<div class="col-md-12"  >
				
					<div class="card-body ">
													
								<div class="row">
								
									
									<div class="col-md-12 " style="border:  groove #FDFEFE ; ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3" style="margin: 20px;">
												<!--  *************************** chart container line  *************************** -->
												<canvas id="myChart2" class="canvas-style2"></canvas>
												</div>
											</div>
										
									</div>
									
									
								
								</div>
								
					</div>
				
			</div>			
		</div>
	
	</div>
		<!--Sites Gain/Loss -->
	<hr class="assetfinance">
	<div class="container-fluid assetfinance">
		<div class="row" style="align-content: center;">
		<div class="col-md-12">
			
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">
									<div class="card-group" >
									<div class="col-md-6 " >
									

											<div class="card-body" >

												<div class="tab-content">

														<!-- ********************** first chart ********************** -->

														<div id="chart-containerDount" "></div>

													</div>
												</div>
											
										
									</div>
									
								<div class="col-md-6 ">
									

											<div class="card-body" >

												<div class="tab-content">

														<!-- ********************** first chart ********************** -->

														<div id="chart-containerDount2"  ></div>

													</div>
												</div>
										
										
									</div>
		</div>
		</div>
		</div>
		
		</div>

		</div>
	</div>
	
<!--Sites Technologies and Trans Revenue Analysis  -->
	<hr class="profitandlloss">
	<div class="container-fluid profitandloss">
	
		<div class="row">
		
			<div class="col-md-12" >
			
					<div class="card-body " style="border:  groove #FDFEFE ;">
					
					
								<div class="row">
								
									
									<div class="col-md-12 " >
										
											<div class="card-body canvas-style3" style="padding: 20px;">
												<!--  *************************** chart container line  *************************** -->
												<canvas id="myChartLine6" width="350" class="canvas-style2"></canvas>
												</div>
										
										
									</div>
									
									
								
								</div>
								
					</div>
				
			</div>			
		</div>
	
	</div>
	
	<!--Sites Technologies and Transmission  Expenses Analysis -->
	<hr class="profitandloss">
	<div class="container-fluid profitandloss">
	
		<div class="row">
		
			<div class="col-md-12 " >
				
					<div class="card-body " style="border:  groove #FDFEFE ;">
					
					
								<div class="row">
								
									
									<div class="col-md-12 " >
										
											<div class="card-body canvas-style3" style="padding: 20px;">
												<!--  *************************** chart container line  *************************** -->
												<canvas id="myChartLine7" width="350" class="canvas-style2"></canvas>
												</div>
											
										
									</div>
									
									
								
								</div>
								
					</div>
				
			</div>			
		</div>
	
	</div>
	
		<!--Sites Technologies Expenses Analysis -->
	<hr class="profitandloss">
	<div class="container-fluid profitandloss">
	
		<div class="row">
		
			<div class="col-md-12" >
				
					<div class="card-body " style="border:  groove #FDFEFE ;">
					
					
								<div class="row">
								
									
									<div class="col-md-12 " >
										
											<div class="card-body canvas-style3" style="padding: 20px;">
												<!--  *************************** chart container line  *************************** -->
												<canvas id="myChartLine11" width="350" class="canvas-style2"></canvas>
												</div>
											</div>
										
								
									
								
								</div>
								
					</div>
				
			</div>			
		</div>
	
	</div>
	<!--Site Transmission  Type Expenses -->
	<hr class="profitandloss">
	<div class="container-fluid profitandloss">
	
		<div class="row">
		
			<div class="col-md-12" >
				
					<div class="card-body " style="border:  groove #FDFEFE ;">
					
					
								<div class="row">
								
									
									<div class="col-md-12 " >
									
											<div class="card-body canvas-style3" style="padding: 20px;">
												<!--  *************************** chart container line  *************************** -->
												<canvas id="myChart4" width="350" class="canvas-style2"></canvas>
												</div>
											</div>
										
								
									
									
								
								</div>
								
					</div>
			
			</div>			
		</div>
	
	</div>
<!--Site Transmission Type Profit and loss -->	
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
									
											<div class="card-body canvas-style3">

												<canvas id="myChart3" class="canvas-style2"></canvas>

											</div>
										</div>
								
								</div>
							</div>
						</div>
					</div>
			
			</div>
		</div>
	</div>
	
		<!--Site Technologies Profit and loss Analysis -->	
	<hr class="profitandloss">
	<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12" >
			
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12">
										
											<div class="card-body canvas-style3">

												<canvas id="myChartLine14" class="canvas-style2"></canvas>

											</div>
										
									</div>
								</div>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>
		<!--Site Technologies and  Transmission Type More Profit -->	
		
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
										
											<div class="card-body canvas-style3">

												<canvas id="myChartLine13" class="canvas-style2"></canvas>

											</div>
										
									</div>
								</div>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>	
   	   <!--Site Transmission Type More Profit-->
	<hr class="profitandloss">
	
	<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12" >
				
					<div class="card-body " style="border:  groove #FDFEFE ;" >

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12  ">
										
											<div class="card-body canvas-style3">

												<canvas id="myChart5" class="canvas-style2"></canvas>

											</div>
										
									</div>
								</div>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>	
     <!--Site Technologies More Profit-->
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
									
											<div class="card-body canvas-style3">

												<canvas id="myChart6" class="canvas-style2"></canvas>

											</div>
										
									</div>
								</div>
							</div>
						</div>
					</div>
				
			</div>
		</div>
	</div>	
	<!--Transmission Type Site Count -->			
	<hr class="profitandloss">
	<div class="container-fluid assetfinance">
		<div class="row">
			<div class="col-md-12" >
			
					<div class="card-body  " style="border:  groove #FDFEFE ;" >
					<div class="tab-content">
					

			
						
							<div id="pichart-container2" class="pieChartStyle"></div>

						
					</div>
				
				</div>
				
			</div>
			</div>
			</div>
			<!--Top 10 Site Owner -->			
			<hr class="assetfinance">
	<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12" >
			
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">

									<div class="col-md-12 " >

										

											<div class="card-body canvas-style3">

												<div class="tab-content">

													<div class="card-body canvas-style3" >

															<canvas id="myChartLine3" class="canvas-style2"></canvas>

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

									<div class="col-md-12 " >

										<div class="card card-primary card-tabs">

											<div class="card-body canvas-style3">

												<div class="tab-content">

													<div class="card-body canvas-style3" >

															<canvas id="myChartLine4" class="canvas-style2"></canvas>

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
	</div>
		<!--top 10 Site loss -->	
	<hr class="profitandloss">
	<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12">
				
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">

												<canvas id="myChartLine5" class="canvas-style2"></canvas>

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
		<!--Area Technologies Profit and analysis -->	
	<hr class="profitandloss">
		<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12">
				
					<div class="card-body "style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12 ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">

												<canvas id="myChartLine8" class="canvas-style2"></canvas>

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
		
		
			<!--Top 10 Area Loss -->	 	
		<hr class="profitandloss">
		
			<div class="container-fluid profitandloss">
		<div class="row">
			<div class="col-md-12">
				
					<div class="card-body " style="border:  groove #FDFEFE ;">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">
								<div class="col-md-12  ">
										<div class="card card-primary card-tabs cards-margin">
											<div class="card-body canvas-style3">

												<canvas id="myChartLine9" class="canvas-style2"></canvas>

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
		<!--Top 10 Area Profit -->	
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

												<canvas id="myChartLine10" class="canvas-style2"></canvas>

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
		<hr>
</body>

	<script type="text/javascript">


	/*var canvas = document.getElementById('myChart');
	new Chart(canvas, {
	    type: 'pie',    
	    data: {
	      labels: ['January', 'February', 'March'],
	      datasets: [{
	        data: [10, 30, 60],
	        backgroundColor: ['#FF6384', '#36A2EB','#FFCE56']
	      }]
	    },
	    options: {
	      responsive: true,
	      maintainAspectRatio: true,
	      plugins: {
	        labels: {
	          render: 'percentage',
	          fontColor: ['green', 'white', 'red'],
	          precision: 2
	        }
	      },
	    }
	});
*/

	/*var data = [{
		  data: [50, 55, 60, 33],
		  backgroundColor: [
		    "#4b77a9",
		    "#5f255f",
		    "#d21243",
		    "#B27200"
		  ],
		  borderColor: "#fff"
		}];

		var options = {
		  tooltips: {
		    enabled: true
		  },
		  plugins: {
		    datalabels: {
		      formatter: (value, ctx) => {

		        let sum = ctx.dataset._meta[0].total;
		        let percentage = (value * 100 / sum).toFixed(2) + "%";
		        return percentage;


		      },
		      color: '#fff',
		    }
		  }
		};


		var ctx = document.getElementById("pie-chart").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});



		var ctx = document.getElementById("pie-chart2").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});

		var ctx = document.getElementById("pie-chart2").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});
		var ctx = document.getElementById("pie-chart3").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});
		
		/*var ctx = document.getElementById("pie-chart5").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});
		var ctx = document.getElementById("pie-chart6").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});
		var ctx = document.getElementById("pie-chart7").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});
		var ctx = document.getElementById("pie-chart8").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['India', 'China', 'US', 'Canada'],
		    datasets: data
		  },
		  options: options
		});
		
		*/

	
		

		
	$("#datetimepicker3").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		});

 

        var today = new Date();
        var date = (today.getMonth()) + '/' + '1' + '/' + today.getFullYear();
        var time = '00' + ":" + '00';
        var dateTime = date + ' ' + time;
        var c = Date.parse(dateTime);
        $('#fromDate').datetimepicker({
            defaultDate: c
        });
        // set end date defualt value to current
        var dateNow = new Date();

        $('#toDate').datetimepicker({
            defaultDate: dateNow
        });
	// ************************* stacked chart *************************
	
       
	$(document).ready(function() {
		//make change date with btn submit
		/*$("#submit").click(  function() {
			SiteRevenueAnalysis();
			});*/

	/*	$( "#fromDate" ).on('input', function(){
			
			if(oldFromDate != this.value){
				getCharts();  
				this.blur();
			}
			});
	
		$( "#toDate" ).on('input', function(){
			
			if(oldToDate != this.value){
				getCharts();  
				this.blur();
			}
			});*/
		
		/*$( "#selectView" ).change(function() {
			//var conceptName = $('#selectView').find(":selected").text();
			 // alert(conceptName);
			 getCharts();
			});*/


			
		// ************************* init *************************
		var oldFromDate;
		var oldToDate;
		var topSupplierList = [];
		var initCost = [];
		var ACCUMULDEPRECAMNT = [];
		var NETCOST = [];
		var supplierName = [];
		var LineChartMonth = new Array();
		var LineChartInitCost = new Array();
		var LineChartAcc = new Array();
		var LineChartNetCost = new Array();
		var count2G;
		var count3G;
		var count4G;
		var count5G;
		var totalNameList = [];
		var itemInitCost = [];
		var itemAccCost = [];
		var itemNetCost = [];
		var domainCount = [];
		var TechnologyCount = [];
		var RevenueTechnology = [];
		var Regions=[];
		var RevenueRegions=[];
		var pieRevenue;
		var RegionsSites=[];
		var AreasSites=[];
		var SitesRegionCount=[];
		var AreaRegionCount=[];
		getCharts();
		
		// ************************* init *************************
		function getCharts(){
			
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/GetChartsDetailsAssetFinance",
			dataType : "json",
			data : {				
				"fromDate": $('#fromDate').val(),
				"toDate": $('#toDate').val()
				
			},
			success : function(data) {
				topSupplierList = [];
				initCost = [];
				ACCUMULDEPRECAMNT = [];
				NETCOST = [];
				supplierName = [];
				LineChartMonth = [];
				LineChartInitCost = [];
				LineChartAcc = [];
				LineChartNetCost = [];
				count2G;
				count3G;
				count4G;
				count5G;
				totalNameList = [];
				itemInitCost = [];
				itemAccCost = [];
				itemNetCost = [];
				domainCount = [];
				
				$("#spinner").addClass("hide-row");
			
					
				
					
					
					//$("#poPercent").text("% "+data.POPercent);
					//$("#poCount").text(data.POCount);
					////$("#poComplete").text(data.POCompleteCount);
					//$('#POchart').data('easyPieChart').update(data.POPercent);
					
					//TechnologyCount=data.TechnologyCount;
					//pieRevenue=jQuery.parseJSON(data.pieRevenue);
					//$("#prPercent").text("% "+data.PRPercent);
					//$("#prCount").text(data.PRCount);
					//$("#prComplete").text(data.PRCompleteCount);
					//$('#PRchart').data('easyPieChart').update(data.PRPercent);
					////RevenueTechnology=data.RevenueTechnology;
					//Regions=data.Regions;
					//RevenueRegions=data.RevenuesPerRegion;
					//RegionsSites=data.RegionsSites
					///AreasSites=data.RegionsAreas
					//SitesRegionCount=data.SitesPerRegions;
					//AreaRegionCount=data.AreasPerRegions


					
					topSupplierList = data.topTenSupplierList;	

					LineChartMonth= data.lineChartMonth;
					 LineChartInitCost = data.lineChartInitCost;
					 LineChartAcc = data.lineChartAcc;
					 LineChartNetCost = data.lineChartNetCost;
					
					totalNameList = data.totalNameList;
					itemInitCost = data.itemInitCost;
					itemAccCost = data.itemAccCost;
					itemNetCost = data.itemNetCost;
					
					
					//domainCount = data.domainCount;
					
					
                    //fetchDonutsChart();
                    //fetchServicesChart();
                   // fetchTechnologyRevenue();
                   // fetchRevenuePerRegion();
                   // fetchSitesPerRegion();
                   // fetchAreasPerRegion();
					fetchDoughnut2d(data.gain2g,data.gain23g,data.gain234g);
					fetchDoughnut2d2(data.loss2g,data.loss23g,data.loss234g);

					
					 //fetchRevenueService();
					 //fetchRevenueTechnology();
					// fetchDonutsChartProfitAndLoss();
					 fetchDonutsChart2(data.fiberCount,data.mwCount);
					 //fetchDonutsChart2ProfitAndLoss(data.fiberCount,data.mwCount);// number site support tech
					 fetchColumnCompaireChartData();
					
					//console.log(LineChartMonth);
					fetchLineChartData();
					//fetchLineChartData2(data.RevenueTotal);
					fetchLineChartData6(data.totalRevenueTransmission);
					fetchLineChartData7(data.expenses);
					fetchLineChartData11(data.getTechExpenses);
					fetchLineChartData12(data.getTransmissionExpenses);
					
					
					
					fetchLineChartData9(data.getTop10AreaProfite.areaName,data.getTop10AreaProfite.areaProfit);
					fetchLineChartData10(data.getTop10AreaLoss.areaName,data.getTop10AreaLoss.areaLoss);
					fetchLineChartData3(data.siteOwnerLossName,data.siteOwnerLossValue);
					fetchLineChartData4(data.top10Profit,data.top10ProfitName);
					fetchLineChartData5(data.top10Profitloss,data.top10ProfitlossName);

					     fetchTopSupplier();
						 fetchColumnCompaireChartData2(data.fiberMwTrProfitAndLoss);
						 fetchLineChartData13(data.siteProfitAndLossTechAndTx);
						 fetchLineChartData14(data.sitetechProfitAndLoss);
						 fetchLineChartData15(data.fiberVsMwMoreProfit);
						 fetchLineChartData16(data.techMoreProfit);
						 fetchLineChartData8(data.areaProfitAndLoss);
						
		
				
				
			},
			error : function(error) {
				
				console.log("The error is " + error);
			}
		});
	}

// 2nd chart 
   


  /*  async function fetchDonutsChart(){
    	var data = [{
  		  data: [TechnologyCount[0], TechnologyCount[1], TechnologyCount[2], TechnologyCount[3],TechnologyCount[4]],
  		  backgroundColor: [
  		    "#FFFF00",
  		    "#199606",
  		    "#4b77a9",
  		    "#4d4dff",
  		    "#FF0000",
  		    "#5f255f",
  		    "#d21243",
  		  ],
  		  borderColor: "#fff"
  		}];
  		
    	var noData = [{
    		  data: ["100"],
    		  backgroundColor: [
    		    "#FFFF00",
    		    
    		  ],
    		  borderColor: "#fff"
    		}];
    		
    if(TechnologyCount[0] == 0 && TechnologyCount[1]== 0 && TechnologyCount[2]== 0 && TechnologyCount[3]== 0 &&TechnologyCount[4]== 0 ){



        
    	var ctx = document.getElementById("pichart-container");
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['No Data'],
		    datasets: noData
		  },
		  showDatapoints: true,
		    options: {
		        tooltips: {
		            enabled: true
		        },
		       
		    
		        plugins: {
		            datalabels: {
		                display: false,
		            },
		            labels: {
		    	         
		    	          fontColor: 'black',
		    	          
		    	        }
		            
		        },
		        responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Technology Sites Count',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
		    }
		});
		
  
        }
    
    else{
       
    	var canvas = document.getElementById('pichart-container');
    	new Chart(canvas, {
    	    type: 'pie',    
    	    data: {
    	      labels: ['2G', '3G', '4G','2G,3G', '2G,3G,4G'],
    	      datasets: data
    	    },
    	    options: {
    	      responsive: true,
    	      maintainAspectRatio: true,
    	      layout: {
    	    	  padding: {
    	    	    left:0,
    	    	    right: 25,
    	    	    top: 0,
    	    	    bottom:70
    	    	  }
    	    	},
    	    	
    	      plugins: {
    	    	  outlabels: {
    	    		    display: true,
    	    		    borderWidth:0,
    	    		    backgroundColor: null,
    	    		    lineWidth: 1,
    	    		    lineColor:null,
    	    		    padding: 0,
    	    		    textAlign: 'center',
    	    		    stretch: 20,
    	    		    color:'black',
    	    		    font: {
    	    		      resizable: true,
    	    		     
    	    		     
    	    		    },
    	    		    valuePrecision: 1,
    	    		    percentPrecision: 2
    	    		  }
    	    		
    	      },
    	      responsive: true,
		        legend: {
		        	  display: false
		           
		        },
		        title: {
		            display: true,
		            text: 'Technology Sites Count',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
    	      
    	    }
    	});

    	
	
	}


   }
*/

  /*  function fetchServicesChart(){

    	var voiceRevenue= [];
		var smsRevenue =[];
		var dataRevneue=[];
		var vasRevenue=[]; 
		var totRev=[];
		var data = [{
	  		  data: [pieRevenue[0][0], pieRevenue[0][1], pieRevenue[0][2], pieRevenue[0][3]],
	  		  backgroundColor: [
	  		    "#FFFF00",
	  		    
	  		    "#0000FF",
	  		    "#FF0000",
	  		    "#5f255f",
	  		   
	  		  ],
	  		  borderColor: "#fff"
	  		}];



        var options = {
                tooltips: {
              enabled: true
         },
         plugins: {
             labels: {
               render: 'percentage',
               fontColor: 'black',
               precision: 2,
               position: 'outside'
             }
           },
           datalabels: {
               display: false,
           },
             responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Revenue Split Per Services',
		            fontSize: 15
		        },
         };
        
    	if(pieRevenue != "" && pieRevenue[0][4] !=0 ){
    		
    		var ctx = document.getElementById("pie-chartServices").getContext('2d');
    		var myChart = new Chart(ctx, {
    		  type: 'pie',
    		  data: {
    		  labels: ['Voice', 'SMS', 'DATA', 'VAS'],
    		    datasets: data
    		  },
    		  options: options,
    		  
    		
    		});

        	
    	}
    	else{  
    		var noData = [{
      		  data: ["100"],
      		  backgroundColor: [
      		    "#FFFF00",
      		    
      		  ],
      		  borderColor: "#fff"
      		}]; 

    		var ctx = document.getElementById("pie-chartServices").getContext('2d');
    		var myChart = new Chart(ctx, {
    		  type: 'pie',
    		  data: {
    		  labels: ['No Data'],
    		    datasets: noData
    		  },
    		  options: options,
    		  
    		
    		});


        	
        	}
    }

    

//donut chart 
    async function fetchTechnologyRevenue (){
    	var data = [{
  		  data: [RevenueTechnology[0], RevenueTechnology[1], RevenueTechnology[2]],
  		  backgroundColor: [
  		    "#FFFF00",
  		    "#4d4dff",
  		    "#1D4E08",
  		  
  		  ],
  		  borderColor: "#fff"
  		}];
  		
    	var noData = [{
    		  data: ["100"],
    		  backgroundColor: [
    		    "#FFFF00",
    		    
    		  ],
    		  borderColor: "#fff"
    		}];
    		
    if(RevenueTechnology[0] == 0 && RevenueTechnology[1]== 0 && RevenueTechnology[2]== 0 ){



        
    	var ctx = document.getElementById("RevenueTechnology");
		var myChart = new Chart(ctx, {
		  type: 'doughnut',
		  data: {
		  labels: ['No Data'],
		    datasets: noData
		  },
		  showDatapoints: true,
		    options: {
		        tooltips: {
		            enabled: true
		        },
		    
		        plugins: {
		            datalabels: {
		                display: false,
		            },
		            labels: {
		    	         
		    	          fontColor: 'black',
		    	          
		    	        }
		            
		        },
		        responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Technology Sites Count',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
		    }
		});
		
  
        }
    
    else{
       
    	var canvas = document.getElementById('RevenueTechnology');
    	new Chart(canvas, {
    	    type: 'doughnut',    
    	    data: {
    	      labels: ['2G', '2G,3G', '2G,3G,4G'],
    	      datasets: data
    	    },
    	    options: {
    	      responsive: true,
    	      maintainAspectRatio: true,
    	      plugins: {
    	        labels: {
    	          render: 'percentage',
    	          fontColor: 'black',
    	          precision: 2,
    	          position:'outside'
    	        }
    	      },
    	      responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Revenue Split Per Technology',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
    	      
    	    }
    	});

    	
	
	}


   }


// 2nd donut chart
    async function fetchRevenuePerRegion(){
    	var data = [{
  		  data: RevenueRegions,
  		  backgroundColor: [
  			"#FFFF00",
  		    "#1D4E08",
  		    "#4b77a9",
  		    "#4d4dff",
  		    "#FF0000",
  		    "#5f255f",
  		    "#d21243",
  		    
  		    "#dd1c77",
  		  ],
  		  borderColor: "#fff"
  		}];
  		
    	var noData = [{
    		  data: ["100"],
    		  backgroundColor: [
    		    "#FFFF00",
    		    
    		  ],
    		  borderColor: "#fff"
    		}];



		if (RevenueRegions=="" &&RevenueRegions.length==0){

			var ctx = document.getElementById("RevenueRegions");
			var myChart = new Chart(ctx, {
			  type: 'doughnut',
			  data: {
			  labels: ['No Data'],
			    datasets: noData,
			    
			  },
			  showDatapoints: true,
			    options: {
			        tooltips: {
			            enabled: true
			        },
			    
			        plugins: {
			            datalabels: {
			                display: false,
			            },
			            labels: {
			    	         
			    	          fontColor: 'black',
			    	          position:'outside'
			    	        }
			            
			        },
			        responsive: true,
			        legend: {
			            position: 'top',
			           
			        },
			        title: {
			            display: true,
			            text: 'Revenue Split Per Regions',
			            fontSize: 15
			        },
			        animation: {
			            animateScale: true,
			            animateRotate: true
			        }
			    }
			});



			}
   /* if(TechnologyCount[0] == 0 && TechnologyCount[1]== 0 && TechnologyCount[2]== 0 && TechnologyCount[3]== 0 &&TechnologyCount[4]== 0 ){



        
    	var ctx = document.getElementById("pichart-container");
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['No Data'],
		    datasets: noData
		  },
		  showDatapoints: true,
		    options: {
		        tooltips: {
		            enabled: true
		        },
		    
		        plugins: {
		            datalabels: {
		                display: false,
		            },
		            labels: {
		    	         
		    	          fontColor: 'black',
		    	          
		    	        }
		            
		        },
		        responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Technology Sites Count',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
		    }
		});
		
  
        }*/
    
   
       /* else{



            var canvas = document.getElementById('RevenueRegions');
    	new Chart(canvas, {
    	    type: 'doughnut',    
    	    data: {
    	      labels: Regions,
    	      datasets: data
    	    },
    	    options: {
    	      responsive: true,
    	      maintainAspectRatio: true,
    	      plugins: {
    	        labels: {
    	          render: 'percentage',
    	          fontColor: 'black',
    	          precision: 2,
    	          position:'outside'
    	        }
    	      },
    	      responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Revenue Split Per Regions',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
    	      
    	    }
    	});} 
    	

    	
   }


     ///Sites Per Region
    async function fetchSitesPerRegion(){
    	var data = [{
  		  data: SitesRegionCount,
  		  backgroundColor: [
  			"#FFFF00",
  		    "#1D4E08",
  		    "#4b77a9",
  		    "#4d4dff",
  		    "#FF0000",
  		    "#5f255f",
  		    "#d21243",
  		    
  		    "#dd1c77",
  		  ],
  		  borderColor: "#fff"
  		}];
  		
    	var noData = [{
    		  data: ["100"],
    		  backgroundColor: [
    		    "#FFFF00",
    		    
    		  ],
    		  borderColor: "#fff"
    		}];



    	if (SitesRegionCount=="" &&SitesRegionCount.length==0){

			var ctx = document.getElementById("SitesRegion");
			var myChart = new Chart(ctx, {
			  type: 'pie',
			  data: {
			  labels: ['No Data'],
			    datasets: noData
			  },
			  showDatapoints: true,
			    options: {
			        tooltips: {
			            enabled: true
			        },
			    
			        plugins: {
			            datalabels: {
			                display: false,
			            },
			           
			    	         
			            	labels: {
				    	         
				    	          fontColor: 'black',
				    	          position:'outside'
				    	        }
			    	          
			    	       
			            
			        },
			        responsive: true,
			        legend: {
			            position: 'top',
			           
			        },
			        title: {
			            display: true,
			            text: 'Sites Count Per Regions',
			            fontSize: 15
			        },
			        animation: {
			            animateScale: true,
			            animateRotate: true
			        }
			    }
			});



			}	
   /* if(TechnologyCount[0] == 0 && TechnologyCount[1]== 0 && TechnologyCount[2]== 0 && TechnologyCount[3]== 0 &&TechnologyCount[4]== 0 ){



        
    	var ctx = document.getElementById("pichart-container");
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['No Data'],
		    datasets: noData
		  },
		  showDatapoints: true,
		    options: {
		        tooltips: {
		            enabled: true
		        },
		    
		        plugins: {
		            datalabels: {
		                display: false,
		            },
		            labels: {
		    	         
		    	          fontColor: 'black',
		    	          
		    	        }
		            
		        },
		        responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Technology Sites Count',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
		    }
		});
		
  
        }*/
    
   
      /*  else{


            var canvas = document.getElementById('SitesRegion');
    	new Chart(canvas, {
    	    type: 'pie',    
    	    data: {
    	      labels: RegionsSites,
    	      datasets: data
    	    },
    	    options: {
    	      responsive: true,
    	      maintainAspectRatio: true,
    	      plugins: {
    	        labels: {
    	          render: 'percentage',
    	          fontColor: 'black',
    	          precision: 2,
    	          position:'outside'
    	        }
    	      },
    	      responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Sites Count Per Regions',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
    	      
    	    }
    	});}  
    	

    	
   }
    
   
    
//Areas Per Region
    async function fetchAreasPerRegion(){
    	var data = [{
  		  data: AreaRegionCount,
  		  backgroundColor: [
  			"#FFFF00",
  		    "#1D4E08",
  		    "#4b77a9",
  		    "#4d4dff",
  		    "#FF0000",
  		    "#5f255f",
  		    "#d21243",
  		    
  		    "#dd1c77",
  		  ],
  		  borderColor: "#fff"
  		}];
  		
    	var noData = [{
    		  data: ["100"],
    		  backgroundColor: [
    		    "#FFFF00",
    		    
    		  ],
    		  borderColor: "#fff"
    		}];




    	if (AreaRegionCount=="" &&AreaRegionCount.length==0){

			var ctx = document.getElementById("AreasRegion");
			var myChart = new Chart(ctx, {
			  type: 'pie',
			  data: {
			  labels: ['No Data'],
			    datasets: noData
			  },
			  showDatapoints: true,
			    options: {
			        tooltips: {
			            enabled: true
			        },
			    
			        plugins: {
			            datalabels: {
			                display: false,
			            },
			            labels: {
			            	display: false,
			    	          
			    	          
			    	        }
			            
			        },
			        responsive: true,
			        legend: {
			            position: 'top',
			           
			        },
			        title: {
			            display: true,
			            text: 'Areas Count Per Regions',
			            fontSize: 15
			        },
			        animation: {
			            animateScale: true,
			            animateRotate: true
			        }
			    }
			});



			}
		
    		
   /* if(TechnologyCount[0] == 0 && TechnologyCount[1]== 0 && TechnologyCount[2]== 0 && TechnologyCount[3]== 0 &&TechnologyCount[4]== 0 ){



        
    	var ctx = document.getElementById("pichart-container");
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		  labels: ['No Data'],
		    datasets: noData
		  },
		  showDatapoints: true,
		    options: {
		        tooltips: {
		            enabled: true
		        },
		    
		        plugins: {
		            datalabels: {
		                display: false,
		            },
		            labels: {
		    	         
		    	          fontColor: 'black',
		    	          
		    	        }
		            
		        },
		        responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Technology Sites Count',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
		    }
		});
		
  
        }*/
    
      /*  else{
            var canvas = document.getElementById('AreasRegion');
    	new Chart(canvas, {
    	    type: 'pie',    
    	    data: {
    	      labels: AreasSites,
    	      datasets: data
    	    },
    	    options: {
    	      responsive: true,
    	      maintainAspectRatio: true,
    	      plugins: {
    	        labels: {
    	          render: 'percentage',
    	          fontColor: 'black',
    	          precision: 2,
    	          position:'outside'
    	        }
    	      },
    	      responsive: true,
		        legend: {
		            position: 'top',
		           
		        },
		        title: {
		            display: true,
		            text: 'Areas Count Per Regions',
		            fontSize: 15
		        },
		        animation: {
		            animateScale: true,
		            animateRotate: true
		        }
    	      
    	    }
    	});
        }
       
    	
    	
   }
    


// Submit for the linechart 1st one

function SiteRevenueAnalysis(){

        
    	$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/GetSecondeChartsDetails",
			dataType : "json",
			data : {				
				"fromDate": $('#fromDate').val(),
				"toDate": $('#toDate').val()
				
			},
			success : function(data) {
			
				
				$("#spinner").addClass("hide-row");
			
					
					fetchLineChartData2(data.RevenueTotal);
					
				
				
			},
			error : function(error) {
				
				console.log("The error is " + error);
			}
		});


        }


*/


    
	//******************************* donuts chart *******************************		
	// 1st piechart
	/*async function fetchDonutsChart(){
		
	FusionCharts.ready(function() {
		var label1,label2,label3,label4,label5,label6;
//console.log("technology"+TechnologyCount);
    if(TechnologyCount==null){
	
	label1 = "NO DATA";label2="";label3="";label4=""; label5=""; label6="";

	 var piChart = new FusionCharts({
		  
		    type: "pie2d",
		    renderAt: "pichart-container",
		    width: "100%",
		       height: "413",
		    dataFormat: "json",
		    dataSource: {
		        "chart": {
		               "caption": "Technologies Sites Count",
		               "captionFontSize": "15",
		               "captionFontColor": "#08526d",
		               "captionFontBold": "1",
		               "subCaption": "",
		               "use3DLighting": "1",
		              "showPercentValues": "1",
		               "decimals": "1",
		               
		               "theme": "fusion",
		               bgColor: "#FFFFFF",
		               bgAlpha: "50",
		               
		           },
		           "data": [
		               {
		                   "label": label1,

		                   "value": 100,
		                   "color":"#FFFF00"
		                   
		                   
		               },
		               {
		                   "label": label2,

		                   "value": 0,
		                   "color":"#1D4E08"
		               },
		               {
		                   "label": label3,

		                   "value": 0
		                  
		               },
		               {
		                   "label": label4,

		                   "value": 0,
		                   "color":"#0000FF"
		                   
		               },
		               {
		                   "label": label5,

		                   "value": 0,
		                   "color":"#FF0000"
		               },

		               {
		                   "label": label6,

		                   "value":0
		                   
		               },
		           ]
		       }
		  });
	
}
		
else if(TechnologyCount[0] == 0 && TechnologyCount[1]== 0 && TechnologyCount[2]== 0 && TechnologyCount[3]== 0 &&TechnologyCount[4]== 0 &&TechnologyCount[5]== 0){
		TechnologyCount[0] = 100;
		label1 = "NO DATA";label2="";label3="";label4=""; label5=""; label6="";
		  var piChart = new FusionCharts({
			  
			    type: "pie2d",
			    renderAt: "pichart-container",
			    width: "100%",
			       height: "413",
			    dataFormat: "json",
			    dataSource: {
			        "chart": {
			               "caption": "Technologies Sites Count",
			               "captionFontSize": "15",
			               "captionFontColor": "#08526d",
			               "captionFontBold": "1",
			               "subCaption": "",
			               "use3DLighting": "1",
			              "showPercentValues": "1",
			               "decimals": "1",
			              
			               "theme": "fusion",
			               bgColor: "#FFFFFF",
			               bgAlpha: "50",
			               
			           },
			           "data": [
			               {
			                   "label": label1,

			                   "value": TechnologyCount[0],
			                    "color":"#FFFF00"
			                   
			               },
			               {
			                   "label": label2,

			                   "value": TechnologyCount[1],
			                   "color":"#1D4E08"
			               },
			               {
			                   "label": label3,

			                   "value": TechnologyCount[2],
			                  
			                   
			               },
			               {
			                   "label": label4,
			                
			                   "value": TechnologyCount[3],
			                   "color":"#0000FF"
			               },
			               {
			                   "label": label5,

			                   "value": TechnologyCount[4],
			                  "color":"#FF0000"
			               },

			               {
			                   "label": label6,

			                   "value": TechnologyCount[5]
			                   
			               },
			           ]
			       }
			  });
		}
	
	else{label1 = "2G: Count: "+TechnologyCount[0]; label2="3G:Count: "+TechnologyCount[1];label3="4G:Count: "+TechnologyCount[2];label4="2G,3G Count: "+TechnologyCount[3];label5="2G,3G,4G: Count: "+TechnologyCount[4]; label6="No Technology, Count: "+TechnologyCount[5] ;

	  var piChart = new FusionCharts({
		  
		    type: "pie2d",
		    renderAt: "pichart-container",
		    width: "100%",
		       height: "413",
		    dataFormat: "json",
		    dataSource: {
		        "chart": {
		               "caption": "Technologies Sites Count",
		               "captionFontSize": "15",
		               "captionFontColor": "#08526d",
		               "captionFontBold": "1",
		               "subCaption": "",
		               "use3DLighting": "1",
		              "showPercentValues": "1",
		               "decimals": "1",
		               
		               "theme": "fusion",
		             
		               bgColor: "#FFFFFF",
		               bgAlpha: "50",
		               
		           },
		           "data": [
		               {
		                   "label": label1,

		                   "value": TechnologyCount[0],
		                    "color":"#FFFF00"
		                   
		               },
		               {
		                   "label": label2,

		                   "value": TechnologyCount[1],
		                   "color":"#1D4E08"
		               },
		               {
		                   "label": label3,

		                   "value": TechnologyCount[2],
		                  
		                   
		               },
		               {
		                   "label": label4,
		                
		                   "value": TechnologyCount[3],
		                   "color":"#0000FF"
		               },
		               {
		                   "label": label5,

		                   "value": TechnologyCount[4],
		                  "color":"#FF0000"
		               },

		               {
		                   "label": label6,

		                   "value": TechnologyCount[5]
		                   
		               },
		           ]
		       }
		  });



	}

  piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
  piChart.render();

});
		
}

*/	












	
	/*async function fetchDonutsChartProfitAndLoss(){
		FusionCharts.ready(function() {
			var label1,label2,label3,label4;
		if(domainCount[0] == 0 && domainCount[1]== 0 && domainCount[2]== 0 && domainCount[3]== 0){
			domainCount[0] = 100;
			label1 = "NO DATA";label2="";label3="";label4="";
			}else{label1 = "2G, Count: "+domainCount[0];label2="2G,3G, Count: "+domainCount[1];label3="2G,3G,4G, Count: "+domainCount[2];label4="BLANK, Count: "+domainCount[3] ;}
	  var piChart = new FusionCharts({
		  
	    type: "pie2d",
	    renderAt: "pichart-containerPaL",
	    width: "100%",
	       height: "390",
	    dataFormat: "json",
	    dataSource: {
	        "chart": {
	               "caption": "Technologies Sites Count",
	               "captionFontSize": "15",
	               "captionFontColor": "#08526d",
	               "captionFontBold": "1",
	               "subCaption": "",
	               "use3DLighting": "1",
	              "showPercentValues": "1",
	               "decimals": "1",
	               "useDataPlotColorForLabels": "1",
	               "theme": "fusion",
	               bgColor: "#FFFFFF",
	               bgAlpha: "50",
	               
	           },
	           "data": [
	               {
	                   "label": label1,

	                   "value": domainCount[0]
	                   
	               },
	               {
	                   "label": label2,

	                   "value": domainCount[1]
	                   
	               },
	               {
	                   "label": label3,

	                   "value": domainCount[2]
	                   
	               },
	               {
	                   "label": label4,

	                   "value": domainCount[3]
	                   
	               },
	           ]
	       }
	  });
	  piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart.render();

	});
	}*/

	async function fetchDonutsChart2(fiberCount,mwCount){
		var label1,label2;
		if(fiberCount == 0 && mwCount == 0){
			fiberCount = 100;
			label1 = "No Data ";label2="";
			}else{label1 = "Fiber, Count: "+fiberCount;label2="MicroWaves, Count: "+mwCount;}
		
		FusionCharts.ready(function() {

	  var piChart2 = new FusionCharts({
		  
	    type: "pie2d",
	    renderAt: "pichart-container2",
	    width: "100%",
	       height: "418",
	    dataFormat: "json",
	    dataSource: {
	        "chart": {
	               "caption": "Transmission Type Site Count",
				    "captionFontSize": "15",
		            "captionFontColor": "#08526d",
		            "captionFontBold": "1",
	               "subCaption": "",
	               "use3DLighting": "1",
	              "showPercentValues": "1",
	               "decimals": "1",
	               "useDataPlotColorForLabels": "1",
	               "theme": "fusion",
	               bgColor: "#FFFFFF",
	               bgAlpha: "50"
	           },
	           "data": [
	               {
	                   "label": label1,

	                   "value": fiberCount
	                   
	               },
	               {
	                   "label": label2,

	                   "value": mwCount
	                   
	               },
	           ]
	       }
	  });
	  piChart2.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart2.render();

	});
	}

	/*async function fetchDonutsChart2ProfitAndLoss(fiberCount,mwCount){
		var label1,label2;
		if(fiberCount == 0 && mwCount == 0){
			fiberCount = 100;
			label1 = "No Data ";label2="";
			}else{label1 = "Fiber, Count: "+fiberCount;label2="MicroWaves, Count: "+mwCount;}
		
		FusionCharts.ready(function() {

	  var piChart2 = new FusionCharts({
		  
	    type: "pie2d",
	    renderAt: "pichart-container2PaL",
	    width: "100%",
	       height: "390",
	    dataFormat: "json",
	    dataSource: {
	        "chart": {
	               "caption": "Transmission Type Site Count",
				    "captionFontSize": "15",
		            "captionFontColor": "#08526d",
		            "captionFontBold": "1",
	               "subCaption": "",
	               "use3DLighting": "1",
	              "showPercentValues": "1",
	               "decimals": "1",
	               "useDataPlotColorForLabels": "1",
	               "theme": "fusion",
	               bgColor: "#FFFFFF",
	               bgAlpha: "50"
	           },
	           "data": [
	               {
	                   "label": label1,

	                   "value": fiberCount
	                   
	               },
	               {
	                   "label": label2,

	                   "value": mwCount
	                   
	               },
	           ]
	       }
	  });
	  piChart2.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart2.render();

	});
	}*/
	//******************************* doughnut2d chart *******************************
async function fetchDoughnut2d(gain2g,gain23g,gain234g){

	var label1,label2,label3;
		if(gain2g == 0 && gain23g == 0 && gain234g == 0){
			gain2g = 100;
			label1 = "No Data";label2="";label3="";
			}else{label1 = "2G, Gain: "+gain2g;label2="2G,3G, Gain: "+gain23g;label3="2G,3G,4G, Gain: "+gain234g;}
		
	 const dataSource = {
			  chart: {
			    caption: "Sites Gain",
			    "captionFontSize": "15",
	            "captionFontColor": "#08526d",
	            "captionFontBold": "1",
			    subcaption: "",
			    showpercentvalues: "1",
			    defaultcenterlabel: "",
			    aligncaptionwithcanvas: "0",
			    captionpadding: "0",
			    decimals: "1",
			  //  plottooltext:
			   //   "<b>$value</b> Value of Task <b>$label</b>",
			    centerlabel: "",
			    theme: "fusion",
			    bgColor: "#FFFFFF",
                bgAlpha: "50"
			  },
			  data: [
			    
			    {
			      label: label1,
			      value: gain2g
			    },
			    
			    {
			      label: label2,
			      value: gain23g
			    },
			    {
			      label: label3,
			      value: gain234g      
			    },
			  ]
			};

			FusionCharts.ready(function() {
				//console.log("inside fusionChart");
				var myChart = new FusionCharts({
			    type: "doughnut2d",
			    renderAt: "chart-containerDount",
			    width: "100%",
			    height: "450",
			    dataFormat: "json",
			    dataSource
			  });
				  //myChart.setXMLUrl("<chart></chart>");
				  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
				  myChart.render();
			});
	}

async function fetchDoughnut2d2(loss2g,loss23g,loss234g){
	var label1,label2,label3;
	if(loss2g == 0 && loss23g == 0 && loss234g == 0){
		loss2g = 100;
		label1 = "No Data";label2="";label3="";
		}else{label1 = "2G, Loss: "+loss2g;label2="2G,3G, Loss: "+loss23g;label3="2G,3G,4G, Loss: "+loss234g;}
 const dataSource = {
		  chart: {
		    caption: "Sites Loss",
		    "captionFontSize": "15",
            "captionFontColor": "#08526d",
            "captionFontBold": "1",
		    subcaption: "",
		    showpercentvalues: "1",
		    defaultcenterlabel: "",
		    aligncaptionwithcanvas: "0",
		    captionpadding: "0",
		    decimals: "1",
		  //  plottooltext:
		   //   "<b>$value</b> Value of Task <b>$label</b>",
		    centerlabel: "",
		    theme: "fusion",
		    bgColor: "#FFFFFF",
            bgAlpha: "50"
		  },
		  data: [
		    
		    {
		      label: label1,
		      value: loss2g
		    },
		    
		    {
		      label: label2,
		      value: loss23g
		    },
		    {
		      label: label3,
		      value: loss234g	      
		    },
		  ]
		};

		FusionCharts.ready(function() {
			//console.log("inside fusionChart");
			var myChart = new FusionCharts({
		    type: "doughnut2d",
		    renderAt: "chart-containerDount2",
		    width: "100%",
		    height: "450",
		    dataFormat: "json",
		    dataSource
		  });
			  //myChart.setXMLUrl("<chart></chart>");
			  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
			  myChart.render();
		});
}
			//******************************* end doughnut2d chart *******************************
			
			
			//******************************* easy pie chart *******************************
			
			$(function() {
				$('.chart').easyPieChart({
				//your options goes here
					barColor : '#ef1e25',
					trackColor : '#f9f9f9',
					scaleColor : false,
					//  scaleLength: 5,
					lineCap : 'round',
					lineWidth :10,
					trackWidth : undefined,
					
					size : 140,
					rotate : 0, // in degrees
					animate : {
						duration : 1000,
						enabled : true
					},
				});
			});

// *************************** line chart ***************************

			async function fetchLineChartData(){
			var ctxLine = document.getElementById('myChartLine').getContext('2d');
			var myChartLine = new Chart(ctxLine, {
			    type: 'line',
			    data: {
			        labels: LineChartMonth ,
			        datasets: [{
			            label: 'Initial Cost',
			            data: LineChartInitCost,
			            backgroundColor: 'rgba(0, 255, 0, 0.2)',
			            borderColor: 'rgba(0, 255, 0, 1)',
			            borderWidth: 1,
			            fill: false
			        },
			        {
			            label: 'Accumulated Depreciation',
			            data: LineChartAcc,
			            backgroundColor: 'rgba(153, 102, 255, 1)',
			            borderColor: 'rgba(153, 102, 255, 1)',
			            borderWidth: 1,
			            fill: false
			        },
			        {
			            label: 'Net Cost',
			            data: LineChartNetCost,
			            backgroundColor: 'red',
			            borderColor: 'red',
			            borderWidth: 1,
			            fill: false
			        }
			        ]
			    },
			    options: {
			    	legend: {
			    	    labels: {
			    	       fontSize: 18,
			    	       fontColor: "black",
			    	    }
			    	},
			    	title: {
			              display: true,
			              text: 'Last 12 Month (Initial Cost, Accumulated Depreciation, Net Cost)',
			              fontFamily: 'sans-serif',
			              fontColor: '#08526D',
			              fontSize: 20
			          },
			          pointLabels: { fontSize:20 },
			    	tooltips: {
			            mode: 'index'
			        },
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero: true
			                }
			            }]
			        }
			    }
			});
			
}

			async function fetchLineChartData2(RevenueTotal){
				var ctxLine2 = document.getElementById('myChartLine2').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G','2G & 3G','2G , 3G & 4G'],
				        datasets: [{
				            label: 'Voice Revenue',
				            data: RevenueTotal[0],
				            backgroundColor: '#FFFF00',
				            borderColor: '#FFFF00',
				            borderWidth: 3,
				            fill: false
				        },
				        {
				            label: 'SMS Revenue',
				            data: RevenueTotal[1],
				            backgroundColor: '#0000FF',
				            borderColor: '#0000FF',
				            borderWidth: 3,
				            fill: false
				        },
				        {
				            label: 'Data',
				            data: RevenueTotal[2],
				            backgroundColor: '#FF0000',
				            borderColor: '#FF0000',
				            borderWidth: 4,
				            fill: false
				        },
				        {
				            label: 'VAS',
				            data: RevenueTotal[3],
				            backgroundColor: '#1D4E08',
				            borderColor: '#1D4E08',
				            borderWidth: 3,
				            fill: false
				        }
				       
				        ]
				    },
				    options: {
				    	legend: {
				    	    labels: {
				    	       fontSize: 18,
				    	       fontColor: "black",
				    	    }
				    	},
				    	 title: {
				              display: true,
				              text: 'Site Technologies Revenues Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData8(profitAndLoss){
				var ctxLine2 = document.getElementById('myChartLine8').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G','2G & 3G','2G , 3G & 4G'],
				        datasets: [{
				            label: 'Profit And Loss',
				            data: profitAndLoss,
				            backgroundColor: 'rgba(0, 255, 0, 0.4)',
				            borderColor: 'rgba(0, 255, 0, 1)',
				            borderWidth: 1,
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
				    	 title: {
				              display: true,
				              text: 'Area Technologies Profit And Loss Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData13(profitAndLoss){
				var ctxLine2 = document.getElementById('myChartLine13').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G Fiber','2G MW','2G & 3G Fiber','2G & 3G MW','2G , 3G & 4G Fiber','2G , 3G & 4G MW'],
				        datasets: [{
				            label: 'Profit And Loss',
				            data: profitAndLoss,
				            backgroundColor: 'rgba(0, 255, 0, 0.4)',
				            borderColor: 'rgba(0, 255, 0, 1)',
				            borderWidth: 1,
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
				    	 title: {
				              display: true,
				              text: 'Site Technologies And Trasmission Profit And Loss Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData14(profitAndLoss){
				var ctxLine2 = document.getElementById('myChartLine14').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G ','2G & 3G','2G , 3G & 4G'],
				        datasets: [{
				            label: 'Profit And Loss',
				            data: profitAndLoss,
				            backgroundColor: 'rgba(0, 255, 0, 0.4)',
				            borderColor: 'rgba(0, 255, 0, 1)',
				            borderWidth: 1,
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
				    	 title: {
				              display: true,
				              text: 'Site Technologies Profit And Loss Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData6(totalRevenue){
				var ctxLine2 = document.getElementById('myChartLine6').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G Fiber','2G MW','2G & 3G Fiber','2G & 3G MW','2G , 3G & 4G Fiber','2G , 3G & 4G MW'],
				        datasets: [{
				            label: 'Voice Revenue',
				            data: totalRevenue[0],
				            backgroundColor: 'rgba(0, 255, 0, 0.4)',
				            borderColor: 'rgba(0, 255, 0, 1)',
				            borderWidth: 1,
				            fill: false
				        },
				        {
				            label: 'SMS Revenue',
				            data: totalRevenue[1],
				            backgroundColor: 'rgba(0, 102, 255, 0.4)',
				            borderColor: 'rgba(153, 102, 255, 1)',
				            borderWidth: 1,
				            fill: false
				        },
				        {
				            label: 'Data',
				            data: totalRevenue[2],
				            backgroundColor: 'rgba(230, 30, 236, 0.4)',
				            borderColor: 'rgba(230, 30, 236, 1)',
				            borderWidth: 1,
				            fill: false
				        },
				        {
				            label: 'Voice IC',
				            data: totalRevenue[3],
				            backgroundColor: 'rgba(230, 236, 30, 0.4)',
				            borderColor: 'rgba(230, 236, 30, 1)',
				            borderWidth: 1,
				            fill: false
				        },
				        {
				            label: 'SMS IC',
				            data: totalRevenue[4],
				            backgroundColor: 'rgba(30, 236, 234, 0.4)',
				            borderColor: 'rgba(30, 236, 234, 1)',
				            borderWidth: 1,
				            fill: false
				        }
				        ]
				    },
				    options: {
				    	legend: {
				    	    labels: {
				    	       fontSize: 18,
				    	       fontColor: "black",
				    	    }
				    	},
				    	 title: {
				              display: true,
				              text: 'Site Technologies And Transmission Revenues Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData7(expenses){
				var ctxLine2 = document.getElementById('myChartLine7').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G Fiber','2G MW','2G & 3G Fiber','2G & 3G MW','2G , 3G & 4G Fiber','2G , 3G & 4G MW'],
				        datasets: [{
				            label: 'Technologies Expenses',
				            data: expenses,
				            backgroundColor: 'rgba(0, 255, 0, 0.4)',
				            borderColor: 'rgba(0, 255, 0, 1)',
				            borderWidth: 1,
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
				    	 title: {
				              display: true,
				              text: 'Site Technologies And Transmission Expenses Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData12(expenses){
				console.log("bar ex0");
				console.log(expenses);
				var ctx2 = document.getElementById('myChart4').getContext('2d');
				var myChart2 = new Chart(ctx2, {
				    type: 'bar',
				    data: {
				    	  labels: ["Fiber","MicroWave"],
					        datasets: [{
					            label: 'Transmission Expenses',
					            data: expenses,  
								backgroundColor: [
					                'rgba(255, 99, 132, 0.5)',
					                'rgba(54, 162, 235, 0.5)',
					            ],
					            borderColor: [
					                'rgba(255, 99, 132, 1)',
					                'rgba(54, 162, 235, 1)',
					            ],      
						            borderWidth: 1
					        },],
				      
				    },
				    options: {
				    	fontStyle: "bold",
				    	legend: {
				    	    labels: {
				    	       fontSize: 18,
				    	       fontColor: "black",
				    	    }
				    	},
				    	 title: {
				              display: true,
				              text: 'Site Transmission Type Expenses',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            
			             xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    barPercentage: 0.4,
				                    fontColor: "black",
				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData15(expenses){

				console.log(expenses);
				var ctx2 = document.getElementById('myChart5').getContext('2d');
				var myChart2 = new Chart(ctx2, {
				    type: 'bar',
				    data: {
				    	  labels: ["Fiber","MicroWave"],
					        datasets: [{
					            label: 'Fiber VS MivroWave Profit And Loss',
					            data: expenses,  
								backgroundColor: [
					                'rgba(255, 99, 132, 0.5)',
					                'rgba(54, 162, 235, 0.5)',
					            ],
					            borderColor: [
					                'rgba(255, 99, 132, 1)',
					                'rgba(54, 162, 235, 1)',
					            ],      
						            borderWidth: 1
					        },],
				      
				    },
				    options: {
				    	fontStyle: "bold",
				    	legend: {
				    	    labels: {
				    	       fontSize: 18,
				    	       fontColor: "black",
				    	    }
				    	},
				    	 title: {
				              display: true,
				              text: 'Site Transmission Type More Profit',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            
			             xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    barPercentage: 0.4,
				                    fontColor: "black",
				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData16(expenses){

				console.log(expenses);
				var ctx2 = document.getElementById('myChart6').getContext('2d');
				var myChart2 = new Chart(ctx2, {
				    type: 'bar',
				    data: {
				    	  labels: ['2G ','2G & 3G','2G , 3G & 4G'],
					        datasets: [{
					            label: '2G  VS  2G&3G  VS  2G,3G&4G Profit And Loss',
					            data: expenses,  
								backgroundColor: [
					                'rgba(255, 99, 132, 0.5)',
					                'rgba(54, 162, 235, 0.5)',
					                'rgba(0, 255, 0, 0.5)'
					            ],
					            borderColor: [
					                'rgba(255, 99, 132, 1)',
					                'rgba(54, 162, 235, 1)',
					                'rgba(0, 255, 0, 1)'
					            ],      
						            borderWidth: 1
					        },],
				      
				    },
				    options: {
				    	fontStyle: "bold",
				    	legend: {
				    	    labels: {
				    	       fontSize: 18,
				    	       fontColor: "black",
				    	    }
				    	},
				    	 title: {
				              display: true,
				              text: 'Site Technologies More Profit',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            
			             xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    barPercentage: 0.4,
				                    fontColor: "black",
				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData11(expenses){
				var ctxLine2 = document.getElementById('myChartLine11').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G ','2G & 3G','2G , 3G & 4G'],
				        datasets: [{
				            label: 'Technologies Expenses',
				            data: expenses,
				            backgroundColor: 'rgba(0, 255, 0, 0.4)',
				            borderColor: 'rgba(0, 255, 0, 1)',
				            borderWidth: 1,
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
				    	 title: {
				              display: true,
				              text: 'Site Technologies Expenses Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData3(ownerNames,ownerValues){
				var ctxLine = document.getElementById('myChartLine3').getContext('2d');
				var myChartLine = new Chart(ctxLine, {
				    type: 'line',				    
				    data: {
				        labels: ownerNames ,
				        datasets: [{
				            label: 'Site Owner',
				            data: ownerValues,
				            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				            borderColor: 'rgba(8, 82, 109 , 1)',
				            borderWidth: 1,
				            fill: false
				        },
				    
				        ]
				    },
				    options: {
				    	  title: {
				              display: true,
				              text: 'Top 10 Site Owner',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 18
				          },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData4(profitValue,profitName){
				var ctxLine = document.getElementById('myChartLine4').getContext('2d');
				var myChartLine = new Chart(ctxLine, {
				    type: 'line',				    
				    data: {
				        labels: profitName ,
				        datasets: [{
				            label: 'Site Profit',
				            data: profitValue,
				            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				            borderColor: 'rgba(8, 82, 109 , 1)',
				            borderWidth: 1,
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
				    	  title: {
				              display: true,
				              text: 'Top 10 Site Profit',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				          pointLabels: { fontSize:20 },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",
				                }
				            }]
				        }
				    }
				});
			
				
	}
			async function fetchLineChartData5(lossValue,lossName){
				var ctxLine = document.getElementById('myChartLine5').getContext('2d');
				var myChartLine = new Chart(ctxLine, {
				    type: 'line',				    
				    data: {
				        labels: lossName ,
				        datasets: [{
				            label: 'Site Loss',
				            data: lossValue,
				            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				            borderColor: 'rgba(8, 82, 109 , 1)',
				            borderWidth: 1,
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
				    	pointLabels: { fontSize:20 },
				    	  title: {
				              display: true,
				              text: 'Top 10 Site Loss',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",
				                }
				            }]
				        }
				    }
				});
				
	}

			async function fetchLineChartData9(profitName,profitValue){
				var ctxLine = document.getElementById('myChartLine9').getContext('2d');
				var myChartLine = new Chart(ctxLine, {
				    type: 'line',				    
				    data: {
				        labels: profitName ,
				        datasets: [{
				            label: 'Area Profit',
				            data: profitValue,
				            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				            borderColor: 'rgba(8, 82, 109 , 1)',
				            borderWidth: 1,
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
				    	pointLabels: { fontSize:20 },
				    	  title: {
				              display: true,
				              text: 'Top 10 Area Profit',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",
				                }
				            }]
				        }
				    }
				});
				
	}
			async function fetchLineChartData10(lossName,lossValue){
				var ctxLine = document.getElementById('myChartLine10').getContext('2d');
				var myChartLine = new Chart(ctxLine, {
				    type: 'line',				    
				    data: {
				        labels: lossName ,
				        datasets: [{
				            label: 'Area Loss',
				            data: lossValue,
				            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				            borderColor: 'rgba(8, 82, 109 , 1)',
				            borderWidth: 1,
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
				    	pointLabels: { fontSize:20 },
				    	  title: {
				              display: true,
				              text: 'Top 10 Area Loss',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",
				                }
				            }]
				        }
				    }
				});
				
	}
			// *************************** bar chart compaire ***************************
			async function fetchColumnCompaireChartData(){
			var ctx2 = document.getElementById('myChart2').getContext('2d');
			var myChart2 = new Chart(ctx2, {
			    type: 'bar',
			    data: {
			        labels: totalNameList,
			        datasets: [{
			            label: 'Init Cost',
			            data: itemInitCost,
			            backgroundColor: 'blue',        
			            borderWidth: 1
			        },
			        {
			            label: 'Accumulated Depreciation',
			            data: itemAccCost,
			            backgroundColor: 'red',                  
			            borderWidth: 1
			        },
			        {
			            label: 'Net Cost',
			            data: itemNetCost,
			            backgroundColor: 'green',                  
			            borderWidth: 1
			        },
			        {
			            label: 'Item Model',
			            data: itemNetCost,
			            backgroundColor: 'yellow',                  
			            borderWidth: 1
			        }
			        ]
			    },
			    options: {
			    	legend: {
			    	    labels: {
			    	       fontSize: 18,
			    	       fontColor: "black",
			    	    }
			    	},
			    	 title: {
			              display: true,
			              text: 'FAR Top 10 Item',
			              fontFamily: 'sans-serif',
			              fontColor: '#08526D',
			              fontSize: 22
			          },
			    	 tooltips:{
			 			mode: 'index',
			             },
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero: true
			                }
			            }],
			            xAxes: [{
			                barPercentage: 0.5,
			                fontColor: "black",
			            }]
			        }
			    }
			});
	}
		
			// *************************** bar chart compaire ***************************
			async function fetchColumnCompaireChartData2(fiberMwTrRevenue){
			var ctx2 = document.getElementById('myChart3').getContext('2d');
			var myChart2 = new Chart(ctx2, {
			    type: 'bar',
			    data: {
			        labels: ["Fiber","MicroWave"],
			        datasets: [{
			            label: 'Profit And Loss',
			            data: fiberMwTrRevenue,  
						backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			            ],
			            borderColor: [
			                'rgba(255, 99, 132, 1)',
			                'rgba(54, 162, 235, 1)',
			            ],      
				            borderWidth: 1
			        },],
			      
			    },
			    options: {
			    	fontStyle: "bold",
			    	legend: {
			    	    labels: {
			    	       fontSize: 18,
			    	       fontColor: "black",
			    	    }
			    	},
			    	 title: {
			              display: true,
			              text: 'Site Transmission Type Profit And Loss',
			              fontFamily: 'sans-serif',
			              fontColor: '#08526D',
			              fontSize: 22
			          },
			    	 tooltips:{
				 			mode: 'index',
				             },
			        scales: {
			            yAxes: [{
			                ticks: {
			                    beginAtZero: true
			                }
			            }],
			            
		             xAxes: [{
			                ticks: {
			                	autoSkip: false,
			                    fontSize: 15,
			                    barPercentage: 0.5,
			                    fontColor: "black",
			                }
			            }]
			        }
			    }
			});
	}

			var obj1 = {};
			var obj2 = {};
			var obj3 = {};
			var obj4 = {};
		async function fetchTopSupplier() {
			if(topSupplierList !=null){
			console.log(topSupplierList);
			for(var i=0 ;i<topSupplierList.length ;i++){
				if(topSupplierList[i][0] != null){
					obj1.value = topSupplierList[i][0].toString()			
					initCost.push(obj1);
					obj1 = {};
				}else{
					obj1.value = "0";
					initCost.push(obj1);
					obj1 = {};
					}
				if(topSupplierList[i][1] !=null){
					obj2.value = topSupplierList[i][1].toString()			
					ACCUMULDEPRECAMNT.push(obj2);
					obj2 = {};
				}else {
					obj2.value = "0";
					ACCUMULDEPRECAMNT.push(obj2);
					obj2 = {};
					}
				if(topSupplierList[i][2] !=null){
					obj3.value = topSupplierList[i][2].toString()			
					NETCOST.push(obj3);
					obj3 = {};
				}else{
					obj3.value = "0";			
					NETCOST.push(obj3);
					obj3 = {};
					}
				if(topSupplierList[i][3] !=null){
					//var spname = topSupplierList[i][3].toString().split("/here&*/")	
					obj4.label = 	topSupplierList[i][3].toString();	
					supplierName.push(obj4);
					obj4 = {};
				}else{
					obj4.label = "";			
					supplierName.push(obj4);
					obj4 = {};			
				}
			}
		
			
			console.log("fetch chart");
			console.log(supplierName.valueOf());
			console.log(ACCUMULDEPRECAMNT.valueOf());
			console.log( JSON.stringify(NETCOST));
			}else{
				console.log(topSupplierList);
				console.log("top 10 supp null");
				supplierName = 'No Data';
				initCost = 0;
				ACCUMULDEPRECAMNT=0;
				NETCOST=0;
				} 
			var label1,label2,label3;
			if(initCost == 0 && ACCUMULDEPRECAMNT == 0 && NETCOST == 0){
				supplierName = "NO DATA";
				
				label1 = "No Data";label2="";label3="";
				}else{label1 = "Initial Cost";label2="Accumulated Depreciation";label3="Net Cost";}
			const dataSource2 = {					
		            chart: {
		              caption: "FAR Top 10 Supplier",
		              "captionFontSize": "18",
		              "captionFontColor": "#08526d",
		              "captionFontBold": "1",
		              subcaption: "",                  
		              showsum: "1",
		              //plottooltext:"$label  <b>$dataValue</b>  $seriesName",
		              theme: "fusion",
		              drawcrossline: "1",
		              bgColor: "#FFFFFF",
	                  bgAlpha: "50",
	                  //"xaxisname": "Supplier",
	                  "labelDisplay": "rotate",
	                  "slantLabel": "1",
	                  placeValuesInside : "0",
	                  showValue : "0",
	                  showsum: "0",
	                  //"labeldisplay": "AUTO",
	                  //interactivelegend: "0",
	                 // showYAxisValues : "0"
		            },
		            
				     categories: [ 
				    	 {
			                   category: supplierName                              
			              }

		            ],

		            dataset: [

		              {
		                   seriesname: label1,
		                   data: initCost                  
		              },

		              {
		                   seriesname: label2,

		                   data: ACCUMULDEPRECAMNT
		              },

		              {
		                   seriesname: label3,

		                   data: NETCOST          
		              }                   
		            ]                                
		       };

		      
		       FusionCharts.ready(function() {
		//console.log("inside chart");
		//console.log(NETCOST);
		            var myChart2 = new FusionCharts({

		              type: "stackedcolumn2d",

		              renderAt: "chart-container-2",

		              width: "100%",

		              height: "550",

		              dataFormat: "json",	            
		              
		            });
		            myChart2.configure("ChartNoDataText","No data to load. Please check the data source.");
		            myChart2.setJSONData(dataSource2);		            
		            myChart2.render();
		       });
			}
									
	});

	
	

	
	</script>
</html>






