<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>Mobile Dashboard</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Google Maps Script -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>

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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusionchartsold.charts.js"></script>

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
	src="${pageContext.request.contextPath}/resources/js/MobileDashboard/MobileDashboard_Speed_Coverage_Map.js"></script>
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/MobileDashboard/MobileDashboard_SimSales_GoogleMap.js"></script>


<style>
hr {
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

.legendContainer {
	height: 800px;
	position: relative;
}

.legendHeader {
	overflow: hidden;
	background-color: #08526d;
	padding: 10px 0px;
}

.box {
	width: 100%;
	height: 100%;
	position: absolute;
	top: 1px;
	left: 0;
}

.stack-top {
	z-index: 10;
	margin: 20px;
}

.legendHeader {
	overflow: hidden;
	background-color: #08526d;
	padding: 10px 0px;
}
.dot {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
}

.dotCount {
	height: 35px;
	width: 35px;
	border-radius: 50%;
	display: inline-block;
}

.downloadGreen {
	width: 0;
	height: 0;
	border-top: 20px solid green;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadBlue {
	width: 0;
	height: 0;
	border-top: 20px solid #0080ff;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadYellow {
	width: 0;
	height: 0;
	border-top: 20px solid #FFDC00;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadDarkRed {
	width: 0;
	height: 0;
	border-top: 20px solid #4D0207;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadRed {
	width: 0;
	height: 0;
	border-top: 20px solid red;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.uploadGreen {
	width: 0;
	height: 0;
	border-bottom: 20px solid green;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadBlue {
	width: 0;
	height: 0;
	border-bottom: 20px solid #0080ff;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadYellow {
	width: 0;
	height: 0;
	border-bottom: 20px solid #FFDC00;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadDarkRed {
	width: 0;
	height: 0;
	border-bottom: 20px solid #4D0207;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadRed {
	width: 0;
	height: 0;
	border-bottom: 20px solid red;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.dotYellow {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: #FFDC00;
}

.dotBlue {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: #0080FF;
}

.dotRed {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: red;
}

.green {
	background: green;
}

.redDark {
	background: #4D0207;
}

.blue {
	background: #0080ff;
}

.yellow {
	background: #FFDC00;
}

.red {
	background: red;
}

.pink {
	background: #FF00FF;
}

.purple {
	background: #8A2BE2;
}

.cadr {
	border: 0.01em solid grey;
}

.cadr2 {
	border: 0.1em solid #808080;
	padding: 40px;
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
	background: #FFDC00;
}

.dotBlue {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	background: blue;
}

.dotRed {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	background: red;
}
.dotPink {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#FF00FF;

}
.dotPurple {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#8A2BE2;

}

.flex-item+.flex-item {
	margin-left: 10px;
	margin-bottom: 5px;
}

.hide-row {
	transition: all 300ms ease-in-out;
	display: none !important;
}

.cadr {
	border: 0.1em solid #08526d;
}

.title {
	margin: 5px 0px;
	font-size: 25px;
	font-weight: 600;
	font-family: 'Times New Roman', Times, serif;
}

.canvas-style {
	height: 500px;
}

.pieChartStyle1 {
	height: 500px;
	width: 700px;
}

.pieChartStyle2 {
	height: 500px;
	width: 700px;
}

.canvas-style2 {
	height: 550px !important;
	padding-bottom: 30px !important;
}

.canvas-style3 {
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

.spin {
	display: block;
	position: fixed;
	z-index: 1031; /* High z-index so it is on top of the page */
	top: 50%;
	right: 50%; /* or: left: 50%; */
	margin-top: -. .px; /* half of the elements height */
	margin-right: -. .px;
	transition: all 300ms ease-in-out;
}

.box {
	width: 100%;
	height: 100%;
	left: 0;
}
</style>

</head>
<body>
	<c:set var="pg" value="dashboard" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>
	<p></p>
	<div class="container-fluid">
		<div class="row">
			<div id="spinner" class=" spinner-border spin "
				style="width: 5rem; height: 5rem; color: #08526d;" role="status">
				<span class="sr-only">Loading...</span>
			</div>
			<!-- end of this row -->
		</div>
		<!-- end container -->
	</div>

	<div class="container-fluid-responsive">

		<div class="row">
			<div class="col-md-12">

								<div class="card-group"
									style="border: 3px groove #FDFEFE; height: 450px; margin-left:15px; margin-right:15px;">

									<div class="col-md-9 ">
										
											<div class="tab-content" id="custom-tabs-one-tabContent" >
												<div style="text-align: center; color:#08526d;font-weight:bold; font-size:20px; vertical-align: middle;"> Speed Coverage GIS</div>
												<div class="tab-pane fade show active"
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
													<div class="legendContainer">
													<div class="card-body">
										<div class="box stack-top" id="legendSpeedCoverage"
												style="overflow-y: scroll; position: relative; top: 20px;left:50px; width: 300px; float: left; height: 320px; background: white;  display: block">

												<div class="legendHeader" id="legendHeader">

													<h6
														style="color: white; font-weight: bold; font-size: 3ex; display: inline-block; position: relative; left:20px;top: 5px;">Legends</h6>

													<button
														style="background-color: transparent; color: white; font-weight: bold; outline: none; border: none; position: relative; left:40px;top: 5px;"
														onClick="SelectUnselectAll()" id="selectUnselect">Unselect
														All</button>
												</div>

												<div id="tableSpeedCoverage"></div>
											</div>


										</div>
													<div class="box" id="mapContainer"
														style="display: block; height: 400px; overflow-y: auto;"></div>
												</div>
												</div>
											</div>
									
									</div>
									<div class="col-md-3 ">
										<div id="filter_Chart_Piez"
											style="background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); height:442px; width:355px; margin-left:-15px;">
											<div id="filterChartPiez" >
												<div style="font-weight: bold; text-align: center; padding-top:100px; font-size:20px; padding-bottom:10px;">Speed Coverage Filter</div>
												<!-- startdate -->

												<div
													style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Start
													Date</div>
												<div class="row">
													<div class="col-md-12">
														<div class="form-group"
															style="margin-left: 50px; margin-right: 50px;">
															<div class="input-group-prepend" id="datetimepicker3"
																data-target-input="nearest">
																<!-- <div class="input-group-text">Start Date</div> -->
																<input type="text" id="speedcoverageStartDate"
																	class="form-control datetimepicker-input"
																	data-toggle="datetimepicker"
																	data-target="#datetimepicker3" />
																<div class="input-group-append"
																	data-target="#datetimepicker3"
																	data-toggle="datetimepicker">
																	<div class="input-group-text">
																		<i class="fa fa-calendar"></i>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>

												<!-- enddate -->
												<div
													style="font-weight: bold; margin-left: 50px; margin-right: 50px;">End
													Date</div>
												<div class="row">
													<div class="col-md-12">
														<div class="form-group"
															style="margin-left: 50px; margin-right: 50px;">
															<div class="input-group-prepend" id="datetimepicker4"
																data-target-input="nearest">
																<!-- <div class="input-group-text">End Date</div> -->
																<input type="text" id="speedcoverageEndDate"
																	class="form-control datetimepicker-input"
																	data-toggle="datetimepicker"
																	data-target="#datetimepicker4" />
																<div class="input-group-append"
																	data-target="#datetimepicker4"
																	data-toggle="datetimepicker">
																	<div class="input-group-text">
																		<i class="fa fa-calendar"></i>
																	</div>

																</div>
															</div>
														</div>
													</div>
													<br><br><br>
												</div>

												<!-- apply button -->
												<!-- <button onclick="filterChart()" >Filter Chart</button> -->
												<div class="row flex">

													<div class="flex-item">
														<button type="button"
															class="btn btn-primary BTN advanced-filter-submit"
															id="speedcoverageApplyFilter">Apply</button>
													</div>
												</div>


											</div>

										</div>
									</div>


								</div>
			</div>

		</div>
		<div class="row">
			<div class="col-md-12">

								<div class="card-group"
									style="border: 3px groove #FDFEFE; height: 450px; margin-left:15px; margin-right:15px;">

									<div class="col-md-9 ">
										
											<div class="tab-content" id="custom-tabs-one-tabContent" >
											<div style="text-align: center; color:#08526d;font-weight:bold; font-size:20px; vertical-align: middle;">Sim Sales GIS</div>
												<div class="tab-pane fade show active"
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
													<div class="legendContainer">
      <div class="card-body">
      
      
         <div class="box stack-top" id="legendSimSales" style="overflow-y: scroll; position: relative; top: 20px;left:50px; width: 300px; float: left; height: 320px; background: white;  display: block">
        
         <div class="legendHeader"  id="legendHeader1">
   
 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:15px;left:20px;">Legends</h6>
 
 <button  style="background-color: transparent; color: white; font-weight: bold; outline: none; border: none; position: relative; top: 15px;left:40px;" onClick="SelectUnselectAllSales()"  id="selectUnselectSales" >Unselect All</button>
  </div>
  
   <div id="tableSimSales">
   <table id="agentSales">
  
   <caption style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-236px;left:20px;">Agent SIM Card Count</caption>
  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    
  </tr>
  
  <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckboxSales" id="firstCountRange" onclick="ShowHideMarkersSales('#FFDC00');" value="#FFDC00"/></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td>
        
    
    <td style="position: relative;top: 32px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex; " >1 - 2</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckboxSales" id="secondCountRange" onclick="ShowHideMarkersSales('#0080FF');" value="#0080FF"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot blue"></div></td>
    
    <td style="position: relative;top: 32px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex; " >3 - 5</label></td>
    
    
  </tr>
  
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckboxSales"  id="thirdCountRange"  onclick="ShowHideMarkersSales('red');" value="red"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td>
    
    <td style="position: relative;top: 32px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >6 - 10</label></td>
    
    
  </tr>
   <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckboxSales"  id="fourthCountRange"  onclick="ShowHideMarkersSales('#FF00FF');" value="#FF00FF"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot pink"></div></td>
    
    <td style="position: relative;top: 32px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >11 - 20 </label></td>
    
    
  </tr>
  
   <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckboxSales"  id="fifthCountRange"  onclick="ShowHideMarkersSales('#8A2BE2');" value="#8A2BE2"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot purple"></div></td>
    
    <td style="position: relative;top: 32px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >21 & Above </label></td>
    
    
  </tr>
    
    
     <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckboxSales" id="maxCount" onclick="ShowHideMarkersSales('green');" value="green"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot green"></div></td>
            <td style="position: relative;top:30px;left:60px;"><div id="max-div" class="dot"></div></td>
    
    <td style="position: relative;top: 32px;left:-22px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap; margin-left:20px; " >Maximum Agent Sales</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckboxSales" id="minCount" onclick="ShowHideMarkersSales('#4D0207');" value="#4D0207"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot redDark"></div></td>
                <td style="position: relative;top:30px;left:60px;"><div id="min-div" class="dot-min"></div></td>
    
    <td style="position: relative;top: 32px;left:-22px;"><label style="color:black;font-weight:bold;font-size:2ex; white-space: nowrap; margin-left:20px;" >Minimum Agent Sales</label></td>
    
    
  </tr>
  
  <tr>
  
  </tr>
    
   
  </table>
  
  
</div>
        </div>
        </div>
         
  <div class="box" id="mapContainer1"
														style="display: block; height: 400px; overflow-y: auto;"></div>
       </div>
													
												</div>
											</div>
									
									</div>
									<div class="col-md-3">
										<div id="filter_Chart_Piez"
											style="background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); height:442px; width:355px; margin-left:-15px;">
											<div id="filterChartPiez">
												<div style="font-weight: bold; text-align: center; padding-top:100px; font-size:20px; padding-bottom:10px;">Sim Sales Filter</div>
												<!-- startdate -->

												<div
													style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Start
													Date</div>
												<div class="row">
													<div class="col-md-12">
														<div class="form-group"
															style="margin-left: 50px; margin-right: 50px;">
															<div class="input-group-prepend" id="datetimepicker11"
																data-target-input="nearest">
																<!-- <div class="input-group-text">Start Date</div> -->
																<input type="text" id="simsalesStartDate"
																	class="form-control datetimepicker-input"
																	data-toggle="datetimepicker"
																	data-target="#datetimepicker11" />
																<div class="input-group-append"
																	data-target="#datetimepicker11"
																	data-toggle="datetimepicker">
																	<div class="input-group-text">
																		<i class="fa fa-calendar"></i>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>

												<!-- enddate -->
												<div
													style="font-weight: bold; margin-left: 50px; margin-right: 50px;">End
													Date</div>
												<div class="row">
													<div class="col-md-12">
														<div class="form-group"
															style="margin-left: 50px; margin-right: 50px;">
															<div class="input-group-prepend" id="datetimepicker12"
																data-target-input="nearest">
																<!-- <div class="input-group-text">End Date</div> -->
																<input type="text" id="simsalesEndDate"
																	class="form-control datetimepicker-input"
																	data-toggle="datetimepicker"
																	data-target="#datetimepicker12" />
																<div class="input-group-append"
																	data-target="#datetimepicker12"
																	data-toggle="datetimepicker">
																	<div class="input-group-text">
																		<i class="fa fa-calendar"></i>
																	</div>

																</div>
															</div>
														</div>
													</div>
													<br><br><br>
												</div>

												<!-- apply button -->
												<!-- <button onclick="filterChart()" >Filter Chart</button> -->
												<div class="row flex">
													<div class="flex-item">
														<button type="button"
															class="btn btn-primary BTN advanced-filter-submit"
															id="simsalesApplyFilter">Apply</button>
													</div>
												</div>


											</div>

										</div>
									</div>


								</div>
			</div>

		</div>
		<div class="container-fluid ">

			<div class="row">
				<div class="col-md-12">
					<div class="card-body " style="border: groove #FDFEFE;">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" role="tabpanel">
								<div class="card-group">

									<div class="col-md-6" style="margin-top: 5px">
										<div id="ApprovedAgent" style="height: 420px"></div>
									</div>

									<div class="col-md-6 " style="margin-top: 5px">
										<div id="SalesPerRegion" style="height: 420px"></div>
									</div>

								</div>
							</div>
						</div>
					</div>

				</div>

				<!-- end of 12 -->
			</div>
		</div>
		<!-- end row -->




	</div>



</body>

<script type="text/javascript">
	var approvedAgents = [];
	var salesPerRegion = [];
	var downloadArray=[];
	var uploadArray=[];
	var coverageArray=[];
	var agentSalesCountList=[];

	coverageArray=${coverage_GISquey};
	downloadArray=${download_GISquey};
	uploadArray=${upload_GISquey};
	agentSalesCountList=${agentSalesCountList};

	///set default date in start and end date 
	// Set the default Date
	const date = new Date();
	date.setDate(date.getDate() - 1);


	var dateend = new Date();
	dateend.setDate(dateend.getDate());
	var c = Date.parse(date);
	
	$('#speedcoverageStartDate').datetimepicker({
	    defaultDate:c
	});
	$('#speedcoverageEndDate').datetimepicker({
	    defaultDate:dateend
	});

	$('#simsalesStartDate').datetimepicker({
	    defaultDate:c
	});
	$('#simsalesEndDate').datetimepicker({
	    defaultDate:dateend
	});

	
	$(document).ready(function() {				
		approvedAgents = ${approvedAgents};
		salesPerRegion = ${salesPerRegion};

		///drag the legend
		$( "#legendSpeedCoverage" ).draggable(); 
		$( "#legendSimSales" ).draggable(); 
		
		
		  
		$("#spinner").addClass("hide-row");
		fetchApprovedAgents();
		fetchSalesPerRegion();

	});


		


	
	$("#speedcoverageApplyFilter").click(function(){

		console.log($("#speedcoverageStartDate").val());
		console.log($("#speedcoverageEndDate").val());
		
		
		
		 $.ajax({ 
             type: "GET", 
              url: "${pageContext.request.contextPath}/SpeedCoverageFilter", 
               dataType: "json", 
                data: { 
                	"startDate":$("#speedcoverageStartDate").val(),
        			"endDate":$("#speedcoverageEndDate").val(),
                      }, 
			success: function (data) { 

				var Nairobi = new google.maps.LatLng(-0.1, 36);
				document.getElementById("mapContainer").innerHTML = "";
				var map = new google.maps.Map(document.getElementById("mapContainer"),
						{

							mapTypeControl : false,
							center : Nairobi,
							zoom : 5.75,
							mapTypeControl : true,
							mapTypeControlOptions : {
								style : google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
								position : google.maps.ControlPosition.TOP_CENTER,
							},
							zoomControl : true,
							zoomControlOptions : {
								position : google.maps.ControlPosition.LEFT_CENTER,
							},

							scaleControl : true,
							streetViewControl : true,
							streetViewControlOptions : {
								position : google.maps.ControlPosition.LEFT_TOP,
							},

							style : 'mapbox://styles/mapbox/streets-v11',
							fullscreenControl : true,
						});

				//Add legend button under zoom control on map
				const centerControlDiv = document.createElement("div");
				LegendControl(centerControlDiv, map);
				map.controls[google.maps.ControlPosition.LEFT_CENTER]
						.push(centerControlDiv);

				const DefaultZoomDiv = document.createElement("div");
				DefaultZoomControl(DefaultZoomDiv, map);
				map.controls[google.maps.ControlPosition.LEFT_CENTER]
						.push(DefaultZoomDiv);



				//remove legend
				$("#upLoadLegend").remove();
				$("#upLoadLegend1").remove();
				$("#upLoadLegend2").remove();
				DeleteMarkers("upload");

				$("#coverageLegend").remove();
				$("#coverageLegend1").remove();
				$("#coverageLegend2").remove();
				DeleteMarkers("signal");


				$("#DownLoadLegend").remove();
				$("#DownLoadLegend1").remove();
				$("#DownLoadLegend2").remove();
				DeleteMarkers("download");

				
				if(data.CoverageReportGIS.length==0 && data.SpeedDownReportGIS.length==0 && data.SpeedUpReportGIS.length==0){

					alert("No Data found between this two dates");

				}
	

				 		

							AddSelectedSignalColor(data.CoverageReportGIS,map);
		
							AddSelectedUpSpeedColor(data.SpeedUpReportGIS,map);
	
							AddSelectedDownSpeedColor(data.SpeedDownReportGIS,map);
				
					 	
				
				data=null;
				  }, 
				   error: function (error) { 
					    console.log("The error is " + error); 
					     } 
				      }); 

		
		});
		
		
	$("#simsalesApplyFilter").click(function(){


		
		
		
		 $.ajax({ 
             type: "GET", 
              url: "${pageContext.request.contextPath}/SimSalesFilter", 
               dataType: "json", 
                data: { 
                	"startDate":$("#simsalesStartDate").val(),
        			"endDate":$("#simsalesEndDate").val(),
                      }, 
			success: function (data) { 
				console.log(data.agentSales);
				if(data != null){

					if(data.agentSales.length != 0){

						var Nairobi = new google.maps.LatLng(-0.1, 36);
						document.getElementById("mapContainer1").innerHTML = "";
						var map1 = new google.maps.Map(document.getElementById("mapContainer1"),
								{

									mapTypeControl : false,
									center : Nairobi,
									zoom : 5.75,
									mapTypeControl : true,
									mapTypeControlOptions : {
										style : google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
										position : google.maps.ControlPosition.TOP_CENTER,
									},
									zoomControl : true,
									zoomControlOptions : {
										position : google.maps.ControlPosition.LEFT_CENTER,
									},

									scaleControl : true,
									streetViewControl : true,
									streetViewControlOptions : {
										position : google.maps.ControlPosition.LEFT_TOP,
									},

									style : 'mapbox://styles/mapbox/streets-v11',
									fullscreenControl : true,
								});

						//Add legend button under zoom control on map
						const centerControlDiv1 = document.createElement("div");
						LegendControlSales(centerControlDiv1, map1);
						map1.controls[google.maps.ControlPosition.LEFT_CENTER]
								.push(centerControlDiv1);

						const DefaultZoomDiv1 = document.createElement("div");
						DefaultZoomControlSales(DefaultZoomDiv1, map1);
						map1.controls[google.maps.ControlPosition.LEFT_CENTER]
								.push(DefaultZoomDiv1);
						UnselectAllSales();
						const maxCountControlDiv = document.createElement("div");
					    MaxAgentSalesControl(maxCountControlDiv, map1);
					    map1.controls[google.maps.ControlPosition.TOP_CENTER].push(maxCountControlDiv);

					    const minCountControlDiv = document.createElement("div");
					    MinAgentSalesControl(minCountControlDiv, map1);
					    map1.controls[google.maps.ControlPosition.TOP_CENTER].push(minCountControlDiv);
						simCountSetColor(data.agentSales,map1);

						}else{
						alert("No Data found between this two dates");

						}

					}
				else{
						alert("No Data found between this two dates");

						}
				


				
					 	
				
				data=null;
				  }, 
				   error: function (error) { 
					    console.log("The error is " + error); 
					     } 
				      }); 

		
		});
	

	function fetchApprovedAgents() {
		FusionCharts.ready(function() {
			var label1, label2, label3, label4;
			var totalNbrAgents;
			var pending = 0, approved = 0, deactivated = 0, cancelled = 0;
			if (approvedAgents[0][0] != 0 || approvedAgents[0][1] != 0
					|| approvedAgents[0][2] != 0 || approvedAgents[0][3] != 0) {
				totalNbrAgents = approvedAgents[0][0] + approvedAgents[0][1]
						+ approvedAgents[0][2] + approvedAgents[0][3];
				console.log(totalNbrAgents);
				label1 = "Pending : " + approvedAgents[0][0];
				label2 = "Activated : " + approvedAgents[0][1];
				label3 = "Deactivated : " + approvedAgents[0][2];
				label4 = "Cancelled : " + approvedAgents[0][3];

				pending = (approvedAgents[0][0] / totalNbrAgents) * 100;
				activated = (approvedAgents[0][1] / totalNbrAgents) * 100;
				deactivated = (approvedAgents[0][2] / totalNbrAgents) * 100;
				cancelled = (approvedAgents[0][3] / totalNbrAgents) * 100;
			} else {
				label1 = "NO Data";
				label2 = "";
				label3 = "";
				label4 = "";
			}
			var piChart = new FusionCharts({

				type : "doughnut2d",
				renderAt : "ApprovedAgent",
				width : "100%",
				height : "420",
				dataFormat : "json",
				dataSource : {
					"chart" : {
						"caption" : "Approved Agents for Mobile Application",
						"captionFontSize" : "15",
						"captionFontColor" : "#08526d",
						"captionFontBold" : "1",
						"subCaption" : "",
						"use3DLighting" : "1",
						"showPercentValues" : "1",
						"decimals" : "1",
						"theme" : "fusion",
						bgColor : "#FFFFFF",
						bgAlpha : "50",

					},
					data : [ {
						label : label1,
						value : pending,
						"color" : "#FFFF00"
					}, {
						label : label2,
						value : activated,
						"color" : "#00FF00"
					}, {
						label : label3,
						value : deactivated,
						"color" : "#FF0000"
					}, {
						label : label4,
						value : cancelled,
						"color" : "#0000FF"
					} ]
				}
			});

			piChart.configure("ChartNoDataText",
					"No data to load. Please check the data source.");
			piChart.render();

		});
	}
	function fetchSalesPerRegion() {
		FusionCharts
				.ready(function() {

					var label1, label2, label3, label4, label5, label6;
					var totalNbrofSales = salesPerRegion[0];
					var coast = 0, western = 0, north = 0, central = 0, south = 0, undefined = 0;
					if (salesPerRegion[1] != 0 || salesPerRegion[2] != 0
							|| salesPerRegion[3] != 0 || salesPerRegion[4] != 0
							|| salesPerRegion[5] != 0 || salesPerRegion[6] != 0) {
						label1 = "Coast : " + salesPerRegion[1];
						label2 = "Western : " + salesPerRegion[2];
						label3 = "North Nairobi : " + salesPerRegion[3];
						label4 = "Central : " + salesPerRegion[4];
						label5 = "South Nairobi : " + salesPerRegion[5];
						label6 = "Undefined Region : " + salesPerRegion[6];

						coast = (salesPerRegion[1] / totalNbrofSales) * 100;
						western = (salesPerRegion[2] / totalNbrofSales) * 100;
						north = (salesPerRegion[3] / totalNbrofSales) * 100;
						central = (salesPerRegion[4] / totalNbrofSales) * 100;
						south = (salesPerRegion[5] / totalNbrofSales) * 100;
						undefined = (salesPerRegion[6] / totalNbrofSales) * 100;
					} else {
						label1 = "Coast : 0" ;
						label2 = "Western : 0";
						label3 = "North Nairobi : 0";
						label4 = "Central : 0";
						label5 = "South Nairobi : 0";
						label6 = "No Data";

						coast = 0;
						western = 0;
						north = 0;
						central =0;
						south = 0;
						undefined =100;
					}

					var piChart = new FusionCharts({

						type : "pie2d",
						renderAt : "SalesPerRegion",
						width : "100%",
						height : "420",
						dataFormat : "json",
						dataSource : {
							"chart" : {
								"caption" : "Percentage of Sales per Region",
								"captionFontSize" : "15",
								"captionFontColor" : "#08526d",
								"captionFontBold" : "1",
								"subCaption" : "",
								"use3DLighting" : "1",
								"showPercentValues" : "1",
								"decimals" : "1",
								"theme" : "fusion",
								bgColor : "#FFFFFF",
								bgAlpha : "50",

							},
							data : [ {
								label : label1,
								value : coast,
								"color" : "#FFFF00"
							}, {
								label : label2,
								value : western,
								"color" : "#00FF00"
							}, {
								label : label3,
								value : north,
								"color" : "#00FFFF"
							}, {
								label : label4,
								value : central,
								"color" : "#0000FF"
							}, {
								label : label5,
								value : south,
								"color" : "#FF0000"
							}, {
								label : label6,
								value : undefined,
								"color" : "#FFA500"
							} ]

						}
					});

					piChart.configure("ChartNoDataText",
							"No data to load. Please check the data source.");
					piChart.render();

				});
	}
	function initMap() {

		var Nairobi = new google.maps.LatLng(-0.1, 36);
		document.getElementById("mapContainer").innerHTML = "";
		var map = new google.maps.Map(document.getElementById("mapContainer"),
				{

					mapTypeControl : false,
					center : Nairobi,
					zoom : 5.75,
					mapTypeControl : true,
					mapTypeControlOptions : {
						style : google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
						position : google.maps.ControlPosition.TOP_CENTER,
					},
					zoomControl : true,
					zoomControlOptions : {
						position : google.maps.ControlPosition.LEFT_CENTER,
					},

					scaleControl : true,
					streetViewControl : true,
					streetViewControlOptions : {
						position : google.maps.ControlPosition.LEFT_TOP,
					},

					style : 'mapbox://styles/mapbox/streets-v11',
					fullscreenControl : true,
				});

		//Add legend button under zoom control on map
		const centerControlDiv = document.createElement("div");
		LegendControl(centerControlDiv, map);
		map.controls[google.maps.ControlPosition.LEFT_CENTER]
				.push(centerControlDiv);

		const DefaultZoomDiv = document.createElement("div");
		DefaultZoomControl(DefaultZoomDiv, map);
		map.controls[google.maps.ControlPosition.LEFT_CENTER]
				.push(DefaultZoomDiv);


		 if(coverageArray.length>0){
				AddSelectedSignalColor(coverageArray,map);
			}
		 

	
		 if(uploadArray.length>0){
			 AddSelectedUpSpeedColor(uploadArray,map);
			}
		 

	 
		 if(downloadArray.length>0){
			 AddSelectedDownSpeedColor(downloadArray,map);
			}

		var Nairobi1 = new google.maps.LatLng(-0.1, 36);
		document.getElementById("mapContainer1").innerHTML = "";
		var map1 = new google.maps.Map(document.getElementById("mapContainer1"),
				{

					mapTypeControl : false,
					center : Nairobi1,
					zoom : 5.75,
					mapTypeControl : true,
					mapTypeControlOptions : {
						style : google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
						position : google.maps.ControlPosition.TOP_CENTER,
					},
					zoomControl : true,
					zoomControlOptions : {
						position : google.maps.ControlPosition.LEFT_CENTER,
					},

					scaleControl : true,
					streetViewControl : true,
					streetViewControlOptions : {
						position : google.maps.ControlPosition.LEFT_TOP,
					},

					style : 'mapbox://styles/mapbox/streets-v11',
					fullscreenControl : true,
				});

		//Add legend button under zoom control on map
		const centerControlDiv1 = document.createElement("div1");
		LegendControlSales(centerControlDiv1, map1);
		map1.controls[google.maps.ControlPosition.LEFT_CENTER]
				.push(centerControlDiv1);

		const DefaultZoomDiv1 = document.createElement("div1");
		DefaultZoomControlSales(DefaultZoomDiv1, map1);
		map1.controls[google.maps.ControlPosition.LEFT_CENTER]
				.push(DefaultZoomDiv1);
		UnselectAllSales();
		const maxCountControlDiv = document.createElement("div");
	    MaxAgentSalesControl(maxCountControlDiv, map1);
	    map1.controls[google.maps.ControlPosition.TOP_CENTER].push(maxCountControlDiv);

	    const minCountControlDiv = document.createElement("div");
	    MinAgentSalesControl(minCountControlDiv, map1);
	    map1.controls[google.maps.ControlPosition.TOP_CENTER].push(minCountControlDiv);
		simCountSetColor(agentSalesCountList,map1);
		
	}

</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
	async defer>
	
</script>

</html>






