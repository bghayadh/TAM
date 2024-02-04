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

#initCostMapContainer,#netCostMapContainer,#ratiosMapContainer {
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
.blue {
background:blue;

}
.orange {
background:orange;

}


</style>

</head>
<body >

  <c:set var="pg" value="financedashboard" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
	<p></p>
	
	
	
<!--TOP 10 Site Asset (depending on rev/asset init cost ratio) -->	
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
												<canvas id="topRevToInitAssetChartContainer" class="canvas-style2"></canvas>
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
	
	<!--Least 10 Site Asset (depending on rev/asset init cost ratio) -->	
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
												<canvas id="leastRevToInitAssetChartContainer" class="canvas-style2"></canvas>
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
	
		
<!--TOP 10 Site Asset (depending on rev/asset net cost ratio) -->	
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
												<canvas id="topRevToNetAssetChartContainer" class="canvas-style2"></canvas>
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
	
	<!--Least 10 Site Asset (depending on rev/asset net cost ratio) -->	
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
												<canvas id="leastRevToNetAssetChartContainer" class="canvas-style2"></canvas>
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
		
<!--TOP 10 Site Asset (depending on population/asset initial cost ratio) -->	
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
												<canvas id="topPopulationToInitAssetChartContainer" class="canvas-style2"></canvas>
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
		
<!--Least 10 Site Asset (depending on population/asset initial cost ratio) -->	
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
												<canvas id="leastPopulationToInitAssetChartContainer" class="canvas-style2"></canvas>
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
	
		
<!--TOP 10 Site Asset (depending on population/asset net cost ratio) -->	
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
												<canvas id="topPopulationToNetAssetChartContainer" class="canvas-style2"></canvas>
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
			
<!--Least 10 Site Asset (depending on population/asset net cost ratio) -->	
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
												<canvas id="leastPopulationToNetAssetChartContainer" class="canvas-style2"></canvas>
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
							         <div class="box stack-top" id="ratiosLegendDiv" style="overflow-y:scroll;position: relative;top:230px;width: 320px; float:left; height:250px;  background:white; margin:50px;display: none">
							        <div class="legendHeader" id="legendHeader">
								    <h6 style="color: white; font-weight: bold; font-size: 2.8ex; display: inline-block; position: relative; top: 5px; left: 10px;">Legend</h6>
								    <select id="custstat" class="form-control" style="width:200px; display: inline-block;margin-left:15px;" onchange="showSitesOnMap(this)">
								        <option value="revToInit">Revenue/Asset Initial</option>
								        <option value="revToNet">Revenue/Asset Net</option>
								        <option value="popToInit">Population/Asset Initial</option>
								        <option value="popToNet">Population/Asset Net</option>
								    </select>
									</div>
							        
  								<div id="revToInitDiv">
  								
  								<table id="revPopulationToAssetRatio">     								
  								<br>
								<h6 id ='legendHeaderText' style="text-align: center; font-weight: bold; font-size: 2ex; margin-left:6px; white-space: nowrap; overflow: hidden; display: inline;">Sites - Revenue/Asset Ratio (Initial Cost)</h6>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th> 
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								 </tr>
 								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToInitTopSites" onclick="showHideAllSitesForRatios('revToInitTopSites','showHide');" value="blue"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot blue"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToInitLeastSites" onclick="showHideAllSitesForRatios('revToInitLeastSites','showHide');" value="orange"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot orange"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least Sites</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToInitMaxSites" onclick="showHideAllSitesForRatios('revToInitMaxSites','showHide');" value="green"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Site</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToInitMinSites" onclick="showHideAllSitesForRatios('revToInitMinSites','showHide');" value="red"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Site</label></td>   
								 </tr>
  						</table> 
		</div>
		
		
						<div id="revToNetDiv" style="display: none;">
  							<table id="revPopulationToAssetRatio">     								
  								<br>
								<h6 id ='legendHeaderText' style="text-align: center; font-weight: bold; font-size: 2ex; margin-left:6px; white-space: nowrap; overflow: hidden; display: inline;">Sites - Revenue/Asset Ratio (Net Cost)</h6>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th> 
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								 </tr>
 								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToNetTopSites" onclick="showHideAllSitesForRatios('revToNetTopSites','showHide');" value="blue"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot blue"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToNetLeastSites" onclick="showHideAllSitesForRatios('revToNetLeastSites','showHide');" value="orange"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot orange"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least Sites</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToNetMaxSites" onclick="showHideAllSitesForRatios('revToNetMaxSites','showHide');" value="green"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Site</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="revToNetMinSites" onclick="showHideAllSitesForRatios('revToNetMinSites','showHide');" value="red"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Site</label></td>   
								 </tr>
  						</table> 
		</div>
			<div id="populationToNetDiv" style="display: none;">
  							<table id="revPopulationToAssetRatio">     								
  								<br>
								<h6 id ='legendHeaderText' style="text-align: center; font-weight: bold; font-size: 2ex; margin-left:6px; white-space: nowrap; overflow: hidden; display: inline;">Sites - Population/Asset Ratio (Net Cost)</h6>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th> 
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								 </tr>
 								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToNetTopSites" onclick="showHideAllSitesForRatios('popToNetTopSites','showHide');" value="blue"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot blue"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToNetLeastSites" onclick="showHideAllSitesForRatios('popToNetLeastSites','showHide');" value="orange"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot orange"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least Sites</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToNetMaxSites" onclick="showHideAllSitesForRatios('popToNetMaxSites','showHide');" value="green"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Site</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToNetMinSites" onclick="showHideAllSitesForRatios('popToNetMinSites','showHide');" value="red"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Site</label></td>   
								 </tr>
  						</table> 
		</div>
		<div id="populationToInitDiv" style="display: none;">
  							<table id="revPopulationToAssetRatio">     								
  								<br>
								<h6 id ='legendHeaderText' style="text-align: center; font-weight: bold; font-size: 2ex; margin-left:6px; white-space: nowrap; overflow: hidden; display: inline;">Sites - Population/Asset Ratio (Initial Cost)</h6>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th> 
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								 </tr>
 								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToInitTopSites" onclick="showHideAllSitesForRatios('popToInitTopSites','showHide');" value="blue"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot blue"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToInitLeastSites" onclick="showHideAllSitesForRatios('popToInitLeastSites','showHide');" value="orange"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot orange"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least Sites</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToInitMaxSites" onclick="showHideAllSitesForRatios('popToInitMaxSites','showHide');" value="green"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Site</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="popToInitMinSites" onclick="showHideAllSitesForRatios('popToInitMinSites','showHide');" value="red"/></td>
								     <td style="position: relative;top:10px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:15px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Site</label></td>   
								 </tr>
  						</table> 
		</div>
		
		</div></div>
       
    <div class="card-body">
        <div class="box" id="ratiosMapContainer"></div>
        </div>
       </div>
       </div></div></div></div>
						</div>
					</div>
			</div>
		</div>
	</div>			
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
							         <div class="box stack-top" id="initCostLegendDiv" style="overflow-y:scroll;position: relative;top:230px;width: 300px; float:left; height:250px;  background:white; margin:50px;display: none">
							         <div class="legendHeader"  id="legendHeader">
 									 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6>
 									 </div>
  								<div id="tableDiv">
  								<table id="maxMinInitCostSites">   
        							<caption style="text-align: center; font-weight: bold; font-size: 2.1ex; margin-top: -145px;margin-left:4px;">Sites Initial Cost</caption>
  								<br>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th> 
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								 </tr>
 								 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="initCostTopSites" onclick="showHideAllSites('initCostTopSites');" value="blue"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot blue"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="initCostLeastSites" onclick="showHideAllSites('initCostLeastSites');" value="orange"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot orange"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least Sites</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="initCostMaxSites" onclick="showHideAllSites('initCostMaxSites');" value="green"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Site</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="initCostMinSites" onclick="showHideAllSites('initCostMinSites');" value="red"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Site</label></td>   
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
							         <div class="box stack-top" id="netCostLegendDiv" style="overflow-y:scroll;position: relative;top:230px;width: 300px; float:left; height:250px;  background:white; margin:50px;display: none">
							         <div class="legendHeader"  id="legendHeader">
 									 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6>
 									 </div>
  								<div id="tableDiv">
  								<table id="maxMinNetCostSites">   
        							<caption style="text-align: center; font-weight: bold; font-size: 2.1ex; margin-top: -145px;margin-left:4px;">Sites Net Cost</caption>
  								<br>
								 <tr>
								    <th style="position: relative;top: 5px;left:10px;"></th>
								    <th style="position: relative;top: 5px;left:10px;"></th>  
								    <th style="position: relative;top: 5px;left:10px;"></th>  
								    <th style="position: relative;top: 5px;left:10px;"></th>  
								 </tr>
 								 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="netCostTopSites" onclick="showHideAllSites('netCostTopSites');" value="blue"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot blue"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Top Sites</label></td>   
								 </tr>
    							 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="netCostLeastSites" onclick="showHideAllSites('netCostLeastSites');" value="orange"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot orange"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Least Sites</label></td>   
								 </tr>
								  <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="netCostMaxSites" onclick="showHideAllSites('netCostMaxSites');" value="green"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot green"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Site</label></td>   
								 </tr>
								 <tr>
								     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="netCostMinSites" onclick="showHideAllSites('netCostMinSites');" value="red"/></td>
								     <td style="position: relative;top:27px;left:50px;"><div class="dot red"></div></td>
								     <td style="position: relative;top:30px;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Site</label></td>   
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

var initCostMap;// Define the map of init cost sites
var netCostMap;// Define the map of net cost sites
var ratiosMap;

var topSitesInitCostList; // used to store all init cost top 10 sites details 
var leastSitesInitCostList;  // used to store all init cost least 10 sites details 
var topSitesNetCostList;  // used to store all net cost top 10 sites details  
var leastSitesNetCostList; // used to store all net cost least 10 sites details 
var topSitesRevToInitList = [];
var leastSitesRevToInitList =[];
var topSitesRevToNetList = [];
var leastSitesRevToNetList=[];
var topSitesPopulationToInitList =[];
var leastSitesPopulationToInitList=[];
var topSitesPopulationToNetList=[];
var leastSitesPopulationToNetList =[];

var siteName=[];
var netCost=[];
var initCost=[];

var infoWindowInitCost;
var infoWindowNetCost;

var infoWindowRevToInit;
var infoWindowRevToNet;
var infoWindowPopToInit;
var infoWindowPopToNet;


var initCostTopSitesMarkers=[]; // used to store the markers of init cost top sites
var initCostLeastSitesMarkers=[];  // used to store the markers of init cost least sites
var initCostMaxSitesMarkers=[]; // used to store the markers of max sites init cost
var initCostMinSitesMarkers=[];// used to store the markers of min sites init cost 

var netCostTopSitesMarkers=[];  // used to store the markers of net cost top sites
var netCostLeastSitesMarkers=[];  // used to store the markers of net cost least sites
var netCostMaxSitesMarkers=[]; // used to store the markers of max sites net cost 
var netCostMinSitesMarkers=[];// used to store the markers of min sites net cost

var initCostTopSitesList=[]; // used to check/uncheck the top sites from map 
var initCostLeastSitesList=[]; // used to check/uncheck the least sites from map 
var initCostMaxSitesList=[]; // used to check/uncheck the max sites from map 
var initCostMinSitesList=[]; // used to check/uncheck the min sites from map 

var netCostTopSitesList=[]; // used to check/uncheck the top sites from map 
var netCostLeastSitesList=[]; // used to check/uncheck the least sites from map 
var netCostMaxSitesList=[]; // used to check/uncheck the max sites from map 
var netCostMinSitesList=[]; // used to check/uncheck the min sites from map 


var revToInitTopSitesMarkers=[];// used to store the markers of top sites rev/init ratio
var revToInitMaxSitesMarkers=[]; // used to store the markers of max sites rev/init ratio
var revToInitLeastSitesMarkers=[];// used to store the markers of least sites rev/init ratio
var revToInitMinSitesMarkers=[]; // used to store the markers of min sites rev/init ratio
var revToNetTopSitesMarkers=[];
var revToNetMaxSitesMarkers =[];
var revToNetLeastSitesMarkers=[];
var revToNetMinSitesMarkers=[];
var popToNetTopSitesMarkers=[];
var popToNetMaxSitesMarkers =[];
var popToNetLeastSitesMarkers=[];
var popToNetMinSitesMarkers =[];
var popToInitTopSitesMarkers=[];
var popToInitMaxSitesMarkers =[];
var popToInitLeastSitesMarkers=[];
var popToInitMinSitesMarkers=[];

var revToInitTopSitesList=[]; // used to check/uncheck the top sites from map 
var revToInitMaxSitesList=[]; // used to check/uncheck the max sites from map 
var revToInitLeastSitesList=[]; // used to check/uncheck the top sites from map 
var revToInitMinSitesList=[]; // used to check/uncheck the max sites from map 
var revToNetTopSitesList=[];
var revToNetMaxSitesList=[];
var revToNetLeastSitesList=[];
var revToNetMinSitesList=[];
var popToNetTopSitesList=[]; // used to check/uncheck the top sites from map 
var popToNetMaxSitesList =[];
var popToNetLeastSitesList=[];
var popToNetMinSitesList=[];
var popToInitTopSitesList=[];
var popToInitMaxSitesList=[];
var popToInitLeastSitesList=[];
var popToInitMinSitesList=[]; 


function initMap() {

	 topSitesInitCostList=${topSitesInitCostAsset};
	 leastSitesInitCostList=${leastSitesInitCostAsset};
	 topSitesNetCostList=${topSitesNetCostAsset};
	 leastSitesNetCostList=${leastSitesNetCostAsset};

	 topSitesRevToInitList = ${topSitesRevToInitList};
	 leastSitesRevToInitList = ${leastSitesRevToInitList};
	 topSitesRevToNetList = ${topSitesRevToNetList};
	 leastSitesRevToNetList = ${leastSitesRevToNetList};
	 topSitesPopulationToInitList = ${topSitesPopulationToInitList};
	 leastSitesPopulationToInitList = ${leastSitesPopulationToInitList};
	 topSitesPopulationToNetList = ${topSitesPopulationToNetList};
	 leastSitesPopulationToNetList = ${leastSitesPopulationToNetList};


	 ratiosMap = new google.maps.Map(document.getElementById("ratiosMapContainer"), {
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

		const ratiosMapLegendControlDiv = document.createElement("div");
		ShowHideMapLegend(ratiosMapLegendControlDiv, ratiosMap);
		ratiosMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(ratiosMapLegendControlDiv);

	    const ratiosDefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(ratiosDefaultZoomDiv, ratiosMap);
	    ratiosMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(ratiosDefaultZoomDiv);

	 
	 
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

	  infoWindowRevToInit = new google.maps.InfoWindow();
	  infoWindowRevToNet = new google.maps.InfoWindow();
	  infoWindowPopToInit = new google.maps.InfoWindow();
	  infoWindowPopToNet = new google.maps.InfoWindow();

	 
	 $("#initCostLegendDiv").toggle(); // to open the legend 
	 $("#netCostLegendDiv").toggle(); // to open the legend 
	 $("#ratiosLegendDiv").toggle(); // to open the legend 
		 

	plotRevPopToAssetCharts(topSitesRevToInitList,'topRevToInitAssetChartContainer','Revenue/Asset Ratio (Initial Cost)','#008000','Top 10 Site Depending On Revenue/Asset Ratio (Initial Cost)')
	plotRevPopToAssetCharts(leastSitesRevToInitList,'leastRevToInitAssetChartContainer','Revenue/Asset Ratio (Initial Cost)','red','Least 10 Site Depending On Revenue/Asset Ratio (Initial Cost)')
	plotRevPopToAssetCharts(topSitesRevToNetList,'topRevToNetAssetChartContainer','Revenue/Asset Ratio (Net Cost)','#008000','Top 10 Site Depending On Revenue/Asset Ratio (Net Cost)')
	plotRevPopToAssetCharts(leastSitesRevToNetList,'leastRevToNetAssetChartContainer','Revenue/Asset Ratio (Net Cost)','red','Least 10 Site Depending On Revenue/Asset Ratio (Net Cost)')
	plotRevPopToAssetCharts(topSitesPopulationToInitList,'topPopulationToInitAssetChartContainer','Population/Asset Ratio (Initial Cost)','#008000','Top 10 Site Depending On Population/Asset Ratio (Initial Cost)')
	plotRevPopToAssetCharts(leastSitesPopulationToInitList,'leastPopulationToInitAssetChartContainer','Population/Asset Ratio (Initial Cost)','red','Least 10 Site Depending On Population/Asset Ratio (Initial Cost)')	 	 
	plotRevPopToAssetCharts(topSitesPopulationToNetList,'topPopulationToNetAssetChartContainer','Population/Asset Ratio (Net Cost)','#008000','Top 10 Site Depending On Population/Asset Ratio (Net Cost)')
	plotRevPopToAssetCharts(leastSitesPopulationToNetList,'leastPopulationToNetAssetChartContainer','Population/Asset Ratio (Net Cost)','red','Least 10 Site Depending On Population/Asset Ratio (Net Cost)')
	
	 plotTopInitialCostChart();
	 plotLeastInitialCostChart();
	 plotTopNetCostChart();
	 plotLeastNetCostChart();
	 
	 
	 createSiteMarker(topSitesInitCostList,initCostMap,"TopSitesInitCost","initCostTopSites",infoWindowInitCost);
	 createSiteMarker(leastSitesInitCostList,initCostMap,"LeastSitesInitCost","initCostLeastSites",infoWindowInitCost);
	 createSiteMarker(topSitesNetCostList,netCostMap,"TopSitesNetCost","netCostTopSites",infoWindowNetCost);
	 createSiteMarker(leastSitesNetCostList,netCostMap,"LeastSitesNetCost","netCostLeastSites",infoWindowNetCost);

	 createSiteMarkerForRatios(topSitesRevToInitList,ratiosMap,"TopSitesRevToInit","revToInitTopSites",infoWindowRevToInit);
	 createSiteMarkerForRatios(leastSitesRevToInitList,ratiosMap,"LeastSitesRevToInit","revToInitLeastSites",infoWindowRevToInit);

	 showMarkersOnMap(revToInitTopSitesMarkers,revToInitTopSitesList,ratiosMap,'revToInitTopSites');
	 showMarkersOnMap(revToInitLeastSitesMarkers,revToInitLeastSitesList,ratiosMap,'revToInitLeastSites');
	 showMarkersOnMap(revToInitMaxSitesMarkers,revToInitMaxSitesList,ratiosMap,'revToInitMaxSites');
	 showMarkersOnMap(revToInitMinSitesMarkers,revToInitMinSitesList,ratiosMap,'revToInitMinSites');

	 
	 createSiteMarkerForRatios(topSitesRevToNetList,ratiosMap,"TopSitesRevToNet","revToNetTopSites",infoWindowRevToNet);
	 createSiteMarkerForRatios(leastSitesRevToNetList,ratiosMap,"LeastSitesRevToNet","revToNetLeastSites",infoWindowRevToNet);
	 
	 createSiteMarkerForRatios(topSitesPopulationToNetList,ratiosMap,"TopSitesPopToNet","popToNetTopSites",infoWindowPopToNet);
	 createSiteMarkerForRatios(leastSitesPopulationToNetList,ratiosMap,"LeastSitesPopToNet","popToNetLeastSites",infoWindowPopToNet);

	 createSiteMarkerForRatios(topSitesPopulationToInitList,ratiosMap,"TopSitesPopToInit","popToInitTopSites",infoWindowPopToInit);
	 createSiteMarkerForRatios(leastSitesPopulationToInitList,ratiosMap,"LeastSitesPopToInit","popToInitLeastSites",infoWindowPopToInit);
	 
    // mapReload();
								 
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
function plotTopInitialCostChart(){
	 siteName=[];
	 initCost=[];
	
for (var i = 0; i < topSitesInitCostList.length; i++) {
	 siteName.push(topSitesInitCostList[i][1]+" : "+topSitesInitCostList[i][2]);
	 initCost.push(topSitesInitCostList[i][0]);
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

function plotLeastInitialCostChart(){
	
    siteName=[];
    initCost=[];
	for (var i = 0; i < leastSitesInitCostList.length; i++) {
	 siteName.push(leastSitesInitCostList[i][1]+" : "+leastSitesInitCostList[i][2]);
	 initCost.push(leastSitesInitCostList[i][0]);
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

function plotTopNetCostChart(){
	siteName=[];
	netCost=[];
	
	 for (var i = 0; i < topSitesNetCostList.length; i++) {
		 siteName.push(topSitesNetCostList[i][1]+" : "+topSitesNetCostList[i][2]);
		 netCost.push(topSitesNetCostList[i][0]);
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

function plotLeastNetCostChart(){
	
    siteName=[];
    netCost=[];
    
	for (var i = 0; i < leastSitesNetCostList.length; i++) {
		 siteName.push(leastSitesNetCostList[i][1]+" : "+leastSitesNetCostList[i][2]);
		 netCost.push(leastSitesNetCostList[i][0]);
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
function createSiteMarkerForRatios(list,map,target,legendCheckboxClass,infoWindow) {	
	var siteID;	
	var ratio;
	var ratioTemp=list[0][1];
	var markersArray;
	var iconColor;
		
	if(list.length!=0){		
		for(i=0;i<list.length;i++){
			siteID=list[i][2];				
			ratio=list[i][1];
			const pos = new google.maps.LatLng(list[i][4],list[i][3]);

			// the ratioTemp is compared to ratio in case there is more than one site having a max ratio (same for min)
			if(target == "TopSitesRevToInit" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "blue";	
				markersArray =revToInitTopSitesMarkers;
				revToInitTopSitesList.push(list[i]);
			}
			else if(target == "TopSitesRevToInit" && ratioTemp==ratio )  {
				iconColor = "green";
				markersArray =revToInitMaxSitesMarkers;
				revToInitMaxSitesList.push(list[i]);
			}
			else if(target == "LeastSitesRevToInit" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "orange";	
				markersArray =revToInitLeastSitesMarkers;
				revToInitLeastSitesList.push(list[i]);				
			}
			else if(target == "LeastSitesRevToInit" && ratioTemp==ratio) {
				iconColor = "red";	
				markersArray =revToInitMinSitesMarkers;
				revToInitMinSitesList.push(list[i]);
			}
			else if(target == "TopSitesRevToNet" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "blue";	
				markersArray =revToNetTopSitesMarkers;
				revToNetTopSitesList.push(list[i]);
		    }
			else if(target == "TopSitesRevToNet" && ratioTemp==ratio )  {
				iconColor = "green";
				markersArray =revToNetMaxSitesMarkers;
				revToNetMaxSitesList.push(list[i]);
			}
			else if(target == "LeastSitesRevToNet" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "orange";	
				markersArray =revToNetLeastSitesMarkers;
				revToNetLeastSitesList.push(list[i]);				
			}
			else if(target == "LeastSitesRevToNet" && ratioTemp==ratio) {
				iconColor = "red";	
				markersArray =revToNetMinSitesMarkers;
				revToNetMinSitesList.push(list[i]);
			}
			else if(target == "TopSitesPopToNet" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "blue";	
				markersArray =popToNetTopSitesMarkers;
				popToNetTopSitesList.push(list[i]);
			}
			else if(target == "TopSitesPopToNet" && ratioTemp==ratio )  {
				iconColor = "green";
				markersArray =popToNetMaxSitesMarkers;
				popToNetMaxSitesList.push(list[i]);
			}
			else if(target == "LeastSitesPopToNet" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "orange";	
				markersArray =popToNetLeastSitesMarkers;
				popToNetLeastSitesList.push(list[i]);				
			}
			else if(target == "LeastSitesPopToNet" && ratioTemp==ratio) {
				iconColor = "red";	
				markersArray =popToNetMinSitesMarkers;
				popToNetMinSitesList.push(list[i]);
			}
			else if(target == "TopSitesPopToInit" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "blue";	
				markersArray =popToInitTopSitesMarkers;
				popToInitTopSitesList.push(list[i]);
			}
			else if(target == "TopSitesPopToInit" && ratioTemp==ratio )  {
				iconColor = "green";
				markersArray =popToInitMaxSitesMarkers;
				popToInitMaxSitesList.push(list[i]);
			}
			else if(target == "LeastSitesPopToInit" && i !=0 && ratioTemp!=ratio ) {
				iconColor = "orange";	
				markersArray =popToInitLeastSitesMarkers;
				popToInitLeastSitesList.push(list[i]);				
			}
			else if(target == "LeastSitesPopToInit" && ratioTemp==ratio) {
				iconColor = "red";	
				markersArray =popToInitMinSitesMarkers;
				popToInitMinSitesList.push(list[i]);
			}

			
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
			var siteNameInfo ="<b style='font-size:13px;'><u>Site Name: </u></b>"+list[i][0];			
			var siteRatio ="<b style='font-size:13px;'><u>Ratio: </u></b>"+ratio;			
			var data="<div></br>"+siteIdInfo+"</br>"+siteNameInfo+"</br>"+siteRatio+"</div>";			

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
									
			google.maps.event.addListener(siteMarker, "click", function (e) {
				infoWindow.close();
	            infoWindow.setContent(this.data); 
	            infoWindow.open(map,this);				
		 	});					         
		}
	}
	}
}	

function showMarkersOnMap(markersArray,lst,map,checkboxClass){

	//Show top,least,max & min sites on map
	if(lst.length!=0){		
		for(var x=0;x<lst.length;x++) {
			siteID = lst[x][2];
			markersArray[siteID].setMap(map);			
		}
	}

	// Check or diasble checkboxes in legend
     updateCheckboxAndToggleDisableState(checkboxClass,lst);
	 updateCheckboxAndToggleDisableState(checkboxClass,lst);
     updateCheckboxAndToggleDisableState(checkboxClass,lst);
     updateCheckboxAndToggleDisableState(checkboxClass,lst);
}

function createSiteMarker(list,map,target,legendCheckboxClass,infoWindow) {

	
	var siteID;	
	var cost;
	var costTemp=list[0][0].toFixed(2);
	var markersArray;
	var iconColor;
		
	if(list.length!=0){

		$('.'+legendCheckboxClass).prop('checked', true);
		$('.'+legendCheckboxClass).attr('disabled', false);

		
		for(i=0;i<list.length;i++){
			siteID=list[i][1];				
			cost=list[i][0].toFixed(2);
			const pos = new google.maps.LatLng(list[i][4],list[i][3]);


			// the costTemp is compared to cost in case there is more than one site having a max cost (same for min)
			if(target == "TopSitesInitCost" && i !=0 && costTemp!=cost ) {
				iconColor = "blue";	
				markersArray =initCostTopSitesMarkers;
				initCostTopSitesList.push(list[i]);
			}
			else if(target == "TopSitesInitCost" && costTemp==cost )  {
				iconColor = "green";
				markersArray =initCostMaxSitesMarkers;
				$('.initCostMaxSites').prop('checked', true);
				$('.initCostMaxSites').attr('disabled', false);
				initCostMaxSitesList.push(list[i]);
			}
			else if(target == "LeastSitesInitCost" && i !=0 && costTemp!=cost ) {
				iconColor = "orange";	
				markersArray =initCostLeastSitesMarkers;
				initCostLeastSitesList.push(list[i]);				
			}
			else if(target == "LeastSitesInitCost" && costTemp==cost) {
				iconColor = "red";	
				markersArray =initCostMinSitesMarkers;
				$('.initCostMinSites').prop('checked', true);
				$('.initCostMinSites').attr('disabled', false);
				initCostMinSitesList.push(list[i]);
			}
			
			else if(target == "TopSitesNetCost" && i !=0 && costTemp!=cost ) {
				iconColor = "blue";	
				markersArray =netCostTopSitesMarkers;
				netCostTopSitesList.push(list[i]);
			}
			else if(target == "TopSitesNetCost" && costTemp==cost )  {
				iconColor = "green";
				markersArray =netCostMaxSitesMarkers;
				$('.netCostMaxSites').prop('checked', true);
				$('.netCostMaxSites').attr('disabled', false);
				netCostMaxSitesList.push(list[i]);
			}
			else if(target == "LeastSitesNetCost" && i !=0 && costTemp!=cost ) {
				iconColor = "orange";	
				markersArray =netCostLeastSitesMarkers;
				netCostLeastSitesList.push(list[i]);				
			}
			else if(target == "LeastSitesNetCost" && costTemp==cost) {
				iconColor = "red";	
				markersArray =netCostMinSitesMarkers;
				$('.netCostMinSites').prop('checked', true);
				$('.netCostMinSites').attr('disabled', false);
				netCostMinSitesList.push(list[i]);
			}
			
			
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

			//Format the value with commas for thousands
			cost = cost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			
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

	if(checkboxClass=="initCostTopSites") {
		allMarkersArray=initCostTopSitesMarkers;
		lst=initCostTopSitesList;
		map=initCostMap;
		
	}
	else if(checkboxClass=="initCostLeastSites") {
		allMarkersArray=initCostLeastSitesMarkers;
		lst=initCostLeastSitesList;
		map=initCostMap;
		
	}
	else if(checkboxClass=="initCostMaxSites") {
		allMarkersArray=initCostMaxSitesMarkers;
		lst=initCostMaxSitesList;
		map=initCostMap;
		
	}
	else if(checkboxClass=="initCostMinSites") {
		allMarkersArray=initCostMinSitesMarkers;
		lst=initCostMinSitesList;
		map=initCostMap;
	}
	else if(checkboxClass=="netCostTopSites") {
		allMarkersArray=netCostTopSitesMarkers;
		lst=netCostTopSitesList;
		map=netCostMap;
	}
	else if(checkboxClass=="netCostLeastSites") {
		allMarkersArray=netCostLeastSitesMarkers;
		lst=netCostLeastSitesList;
		map=netCostMap;
	}
	else if(checkboxClass=="netCostMaxSites") {
		allMarkersArray=netCostMaxSitesMarkers;
		lst=netCostMaxSitesList;
		map=netCostMap;
		
	}
	else if(checkboxClass=="netCostMinSites") {
		allMarkersArray=netCostMinSitesMarkers;
		lst=netCostMinSitesList;
		map=netCostMap;
	}
	$('.'+checkboxClass).bind("change",function() {
					
			if ($(this).is(':checked')){
				for(var x=0;x<lst.length;x++) {
					siteID = lst[x][1];
					if(allMarkersArray[siteID].getMap()==null){
						allMarkersArray[siteID].setMap(map);			
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

function showHideAllSitesForRatios(checkboxClass,target){
	var lst;
	var allMarkersArray;
	var map;

		
		 if(checkboxClass=="revToInitTopSites") {
				allMarkersArray=revToInitTopSitesMarkers;
				lst=revToInitTopSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="revToInitLeastSites") {
				allMarkersArray=revToInitLeastSitesMarkers;
				lst=revToInitLeastSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="revToInitMaxSites") {
				allMarkersArray=revToInitMaxSitesMarkers;
				lst=revToInitMaxSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="revToInitMinSites") {
				allMarkersArray=revToInitMinSitesMarkers;
				lst=revToInitMinSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="revToNetTopSites") {
				allMarkersArray=revToNetTopSitesMarkers;
				lst=revToNetTopSitesList;
				map=ratiosMap;
				
		}
		 else if(checkboxClass=="revToNetLeastSites") {
				allMarkersArray=revToNetLeastSitesMarkers;
				lst=revToNetLeastSitesList;
				map=ratiosMap;
				
		}
		 else if(checkboxClass=="revToNetMaxSites") {
				allMarkersArray=revToNetMaxSitesMarkers;
				lst=revToNetMaxSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="revToNetMinSites") {
				allMarkersArray=revToNetMinSitesMarkers;
				lst=revToNetMinSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="popToNetTopSites") {
				allMarkersArray=popToNetTopSitesMarkers;
				lst=popToNetTopSitesList;
				map=ratiosMap;
				
		}
		 else if(checkboxClass=="popToInitTopSites") {
				allMarkersArray=popToInitTopSitesMarkers;
				lst=popToInitTopSitesList;
				map=ratiosMap;
				
		}
		 else if(checkboxClass=="popToNetLeastSites") {
				allMarkersArray=popToNetLeastSitesMarkers;
				lst=popToNetLeastSitesList;
				map=ratiosMap;
				
		}
		 else if(checkboxClass=="popToInitLeastSites") {
				allMarkersArray=popToInitLeastSitesMarkers;
				lst=popToInitLeastSitesList;
				map=ratiosMap;
				
		}
		 else if(checkboxClass=="popToNetMaxSites") {
				allMarkersArray=popToNetMaxSitesMarkers;
				lst=popToNetMaxSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="popToInitMaxSites") {
				allMarkersArray=popToInitMaxSitesMarkers;
				lst=popToInitMaxSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="popToNetMinSites") {
				allMarkersArray=popToNetMinSitesMarkers;
				lst=popToNetMinSitesList;
				map=ratiosMap;
				
			}
		 else if(checkboxClass=="popToInitMinSites") {
				allMarkersArray=popToInitMinSitesMarkers;
				lst=popToInitMinSitesList;
				map=ratiosMap;
				
			}
	
	$('.'+checkboxClass).bind("change",function() {
					
			if ($(this).is(':checked')){
				for(var x=0;x<lst.length;x++) {
					siteID = lst[x][2];
					if(allMarkersArray[siteID].getMap()==null){
						allMarkersArray[siteID].setMap(map);			
					}
				}
			}
			else {
				for(var x=0;x<lst.length;x++) {
					siteID = lst[x][2];
					allMarkersArray[siteID].setMap(null);								
				}
		   }
			
		});	
	
}	

function plotRevPopToAssetCharts(chartDataArray,chartContainerID,labelText,borderColor,titleText) {
	siteName = [];
    revToAssetRatio = [];	
    
    for (var i = 0; i < chartDataArray.length; i++) {
        siteName.push(chartDataArray[i][0]);
        revToAssetRatio.push(chartDataArray[i][1]); 
    }
    var ctxLine = document.getElementById(chartContainerID).getContext('2d');
    var myChartLine = new Chart(ctxLine, {
		type: 'line',				    
		data: {
			labels: siteName,
			datasets: [{
				label: labelText,
				data: revToAssetRatio,
				backgroundColor: 'rgba(8, 82, 109 , 0.2)',
				borderColor: borderColor,
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
	                        return value.toLocaleString(undefined, { maximumFractionDigits: 9 });
	                    }
	                }
	            },
			pointLabels: { fontSize:20 },
			  title: {
				  display: true,
				  text: titleText,
				  fontFamily: 'sans-serif',
				  fontColor: '#08526D',
				  fontSize: 22
			  },
		 
		}
	});	
    
}

function updateCheckboxAndToggleDisableState(checkboxCLass,list) {
	
	if(list.length >0){
    	$('.'+checkboxCLass).prop('checked', true);
		$('.'+checkboxCLass).attr('disabled', false);
    }
    else {
    	$('.'+checkboxCLass).prop('checked', false);
		$('.'+checkboxCLass).attr('disabled', true);
    }
}
function mapReload() {

	 ratiosMap = new google.maps.Map(document.getElementById("ratiosMapContainer"), {
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

		const ratiosMapLegendControlDiv = document.createElement("div");
		ShowHideMapLegend(ratiosMapLegendControlDiv, ratiosMap);
		ratiosMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(ratiosMapLegendControlDiv);

	    const ratiosDefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(ratiosDefaultZoomDiv, ratiosMap);
	    ratiosMap.controls[google.maps.ControlPosition.LEFT_CENTER].push(ratiosDefaultZoomDiv);
}

function showSitesOnMap(selectedOption) {
    var selectedValue = selectedOption.value;
    
    
    if(selectedValue=="revToInit"){
        document.getElementById('revToInitDiv').style.display = 'block';
        document.getElementById('revToNetDiv').style.display = 'none';
        document.getElementById('populationToNetDiv').style.display = 'none';
        document.getElementById('populationToInitDiv').style.display = 'none';
        mapReload();
        
        showMarkersOnMap(revToInitTopSitesMarkers,revToInitTopSitesList,ratiosMap,'revToInitTopSites');
   	 	showMarkersOnMap(revToInitLeastSitesMarkers,revToInitLeastSitesList,ratiosMap,'revToInitLeastSites');
   	 	showMarkersOnMap(revToInitMaxSitesMarkers,revToInitMaxSitesList,ratiosMap,'revToInitMaxSites');
   	 	showMarkersOnMap(revToInitMinSitesMarkers,revToInitMinSitesList,ratiosMap,'revToInitMinSites');
       
     }
    
    else if(selectedValue=="revToNet"){
    	  document.getElementById('revToInitDiv').style.display = 'none';
          document.getElementById('revToNetDiv').style.display = 'block';
          document.getElementById('populationToNetDiv').style.display = 'none';
          document.getElementById('populationToInitDiv').style.display = 'none';
          mapReload();
                    
          showMarkersOnMap(revToNetTopSitesMarkers,revToNetTopSitesList,ratiosMap,'revToNetTopSites');
     	  showMarkersOnMap(revToNetLeastSitesMarkers,revToNetLeastSitesList,ratiosMap,'revToNetLeastSites');
     	  showMarkersOnMap(revToNetMaxSitesMarkers,revToNetMaxSitesList,ratiosMap,'revToNetMaxSites');
     	  showMarkersOnMap(revToNetMinSitesMarkers,revToNetMinSitesList,ratiosMap,'revToNetMinSites');          
    }
    else if(selectedValue=="popToInit"){
    	  document.getElementById('revToInitDiv').style.display = 'none';
          document.getElementById('revToNetDiv').style.display = 'none';
          document.getElementById('populationToNetDiv').style.display = 'none';
          document.getElementById('populationToInitDiv').style.display = 'block';
          mapReload();
          
          showMarkersOnMap(popToInitTopSitesMarkers,popToInitTopSitesList,ratiosMap,'popToInitTopSites');
     	  showMarkersOnMap(popToInitLeastSitesMarkers,popToInitLeastSitesList,ratiosMap,'popToInitLeastSites');
     	  showMarkersOnMap(popToInitMaxSitesMarkers,popToInitMaxSitesList,ratiosMap,'popToInitMaxSites');
     	  showMarkersOnMap(popToInitMinSitesMarkers,popToInitMinSitesList,ratiosMap,'popToInitMinSites');      	    
    }
    else if(selectedValue=="popToNet"){
    	  document.getElementById('revToInitDiv').style.display = 'none';
          document.getElementById('revToNetDiv').style.display = 'none';
          document.getElementById('populationToNetDiv').style.display = 'block';
          document.getElementById('populationToInitDiv').style.display = 'none';
          mapReload();

          showMarkersOnMap(popToNetTopSitesMarkers,popToNetTopSitesList,ratiosMap,'popToNetTopSites');
     	  showMarkersOnMap(popToNetLeastSitesMarkers,popToNetLeastSitesList,ratiosMap,'popToNetLeastSites');
     	  showMarkersOnMap(popToNetMaxSitesMarkers,popToNetMaxSitesList,ratiosMap,'popToNetMaxSites');
     	  showMarkersOnMap(popToNetMinSitesMarkers,popToNetMinSitesList,ratiosMap,'popToNetMinSites'); 
    }
    
}
</script>	
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>