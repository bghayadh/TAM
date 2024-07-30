<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Cable Break Report</title>
     <link rel="shortcut icon" href="">
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
        <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
        <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
	
	    <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRowsStrandUtilizationReport.js"></script>
		
		
		 <!--Network_Index.css is included here in order to use the css of right click menu  -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">
		
	    
	    <!--  MultiSelect Script -->
        <link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet"    >
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>	
			
			
		<!-- Google Maps Script -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	
	    <!-- export scripts -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
        
        <!-- To Draw Borders on map for start/end coordinates -->
        <script src="${pageContext.request.contextPath}/resources/js/Network/BordersFindNearest.js"></script>
       
</head>
<style>

.fixed-header{
	opacity: 1;
	filter: alpha(opacity=100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.mapButtons {
  color: orange;
  background-color: white;
  border-color: #939191f5;
  transition: border 0.3s; 
}

.mapButtons:hover{
    color: white;
	background-color: orange;
}

.showElement-Location:hover{
    color: white;
	background-color: #007bff;
}


.showElement-Location {
background-color:white;
color: #007bff;
 border-color: #939191f5;

}


#mapContainer {
height: 700px;
}
        
.legendHeader {
overflow: hidden;
background-color: #08526d;
padding: 10px 0px;
text-align: center;
}

.legendContainer{
height: 800px;
position: relative;
}

.box{
width: 100%;
height: 100%;            
position: absolute;
top: 70px;
left: 0;
}

.stack-top{
z-index: 3;
margin: 120px; 
}

.title {
  margin:5px 0px;
  font-size: 25px;
  font-weight:600;
  font-family: 'Times New Roman', Times, serif;
}

.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 400px;
  overflow : auto;
}

 		
/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .input[type=text] {
    width: 100%;
    margin-top: 0;
  }
}



/* Extra small devices (phones, 600px and down) */
@media only screen and (max-width: 600px) {
  .modal-content, .modal-dialog {
  max-height: 100%;
  max-width: 100%;
  
 }
}
/* Small devices (portrait tablets and large phones, 600px and up) */
@media only screen and (max-width: 600px) {
.modal-content, .modal-dialog {
max-height: 100%;
max-width: 100%;

}
}

/* Medium devices (landscape tablets, 768px and up) */
@media only screen and (max-width: 768px) {
 .modal-content, .modal-dialog {
  max-height: 100%;
  max-width: 100%;
  
}
} 

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (max-width: 992px) {
  .modal-content, .modal-dialog {
  
  max-height: 100%;
        max-width: 100%;
  
 }
} 

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
 .modal-content, .modal-dialog {
 
 max-height: 100%;
 max-width: 100%;
}
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	width: 350px;
	z-index:9999;
}

.tooltip-container {
	position: relative;
    display: inline-block;
}
.tooltip-container .tooltip-text {
	visibility: hidden;
	width: 300px;
	background-color: gray;
    color: #fff;
    text-align: center;
    border-radius: 5px;
	padding: 5px;
	position: absolute;
	z-index: 1;
	bottom: 100%;
	left: 50%;
	margin-left: -100px;
	opacity: 0;
	transition: opacity 0.3s;            
}

.tooltip-container:hover .tooltip-text {
	visibility: visible;
	opacity: 1;
}

</style>
<body>
  <c:set var="pg" value="report" scope="session"  />
  <jsp:include page="../header.jsp"></jsp:include>
	
	<div Style=" left: 0; bottom: 0;" id="CableBreakReportDiv">
	<div class="container-fluid">     
	     <div class="row"><p></p></div>
			<div class="row second" >	
			<div class="col-md-2" style="display: flex; ">
				<div class="form-group "style="margin-left: 15px; ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Cable Break Report</span>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Cable ID</span>
						<input type="text" id="fiberCable" class="form-control text-input" style="width:300px;" />
					</div>
				</div>
		    </div>	
		    
		    <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Cable Name</span>
						<input type="text" id="fiberCableName" class="form-control text-input" style="width:300px;" />
					</div>
				</div>
		    </div>	
		    
		    <div class="col-md-2" >
				<button type="button"  id ="showOnMap" class="btn mapButtons"  style="margin-left:50px;"  >Show on Map</button>
			</div>
		    	
					
			<div class="col-md-2" id="col3"  style="text-align:right;">
		 		<div class="btn-group pull-right"  style="padding: 0px !important;">
		 			
		 			<div class="glyph" style="padding-top:0px;">
			 			 <div class="dropdown"  Style="margin-right:10px; height:30px;">
	                        <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Menu	</button>
	
	                       <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton" Style="width:10px;">
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="edit" href="#">Edit</a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="print" href="#">Print</a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="gridExport" href="#" >Export</a></li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="pdf" href="#">PDF</span></a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="setup" href="#">Setup Auto Email</a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="addCol" href="#">Add Column</a> </li>
    	                  </div>
			           </div>  
		        </div>
		       
		 			<div class="glyph" Style="white-space: nowrap;overflow: hidden;">
		                  <button type="button" id="generate" class="btn btn-primary BtnActive" > Generate Report </button> 			
					</div>
			</div></div></div>
			
			<div class="row"> 
				<div class="col-md-3" style="margin-right:50px; margin-left:12px;">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Break Point Longitude</span>
							<input type="text" id="pointLong" class="form-control text-input" style="width:300px;" />
						</div>
					</div>
			    </div>	
			    
			     <div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Break Point Latitude</span>
							<input type="text" id="pointLat" class="form-control text-input" style="width:300px;" />
						</div>
					</div>
			    </div>	
			    
			    <div class="col-md-2" >
					<button type="button"  id ="SetCableBreakCoordinate" class="btn mapButtons"  style="margin-left:40px;"  >Set Coordinate</button>
				</div>
			    
			    		
			</div>

	     <div class="row">
	     	<div class="col-md-9"></div>
	     	<div class="col-md-3">
			<div id="generateLoaderDiv" style="display: none;">
				<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
			</div>
			</div>
	     </div>

<div class="container-fluid" >     
	<br>			
<div class="wrapper" style="margin-top:-10px;">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
 
  
  <div class="panel panel-default" style="margin-bottom:3px;" >
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" style="margin-top:-8px;">
          Grid Table
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body" >
		<div class="card-body " >
					<div class="row">
						<div class="col-sm-12">
							<div class="almgrid-container">
								<div class="row">
									<div class="col-sm-4 almgrid-pagecount-box">
										Show
										<select class="cmb-row-count almgrid-pagecount">
											<option value="10" selected>10</option>
											<option value="25">25</option>
											<option value="50">50</option>
											<option value="100">100</option>
											<option value="500">500</option>
											<option value="1000">1000</option>
										</select>
										Rows
									</div>
									<div class="col-md-4">
											<div id="loaderDiv" style="display: none;">
												<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
											</div>
									</div>
									<div class="col-sm-4 almgrid-global-search-box">
										Search
										<input type="text" class="form-control almgrid-global-search" />
									</div>
								</div>
								<div id= "tableGrid" class="table-responsive almgrid-table-div">
									<table id="gridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header fixed-header">											
												<th>Location ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Location Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th>Location Type
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th>Warehouse ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												
												<th>Location Longitude
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														 </button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												
												<th>Location Latitude
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														 </button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>

												<th>
												<li class="filter-dropdown dropdown">
														<button disabled class="almgrid-filter" data-toggle="dropdown" style="display: none;">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
													
												<th>
												<li class="filter-dropdown dropdown">
														<button disabled class="almgrid-filter" data-toggle="dropdown" style="display: none;">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
																					
											<tr>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" disabled class="almgrid-search" style="display:none"></th>
												<th><input type="text" disabled class="almgrid-search" style="display:none"></th>
																							
											</tr>
										</thead>
										<tbody></tbody>
									</table>
								</div>				
							
								<hr>
								<div class="pagination-div">
									<div class="row">
										<div class="col-sm-7">
											<p class="pagination-label">
												Viewing <span>0-0</span>
												of
												<span>0</span>
											</p>
										</div>
										<div class="col-sm-5 pagination-buttons">
											<nav aria-label="Page navigation">
												<ul class="pagination pagination-buttons justify-content-end">
													<li class="page-item"><button type="button"
															class="page-link pagination-previous pagination-button shadow-none">Prev</button>
													</li>
													<li class="page-item dropdown-pagination-numbers">
														<!-- <select class="form-control page-number-select shadow-none">
														</select> -->
														<div class="input-group page-number-group-div">
															<input type="text"
																class="form-control page-number-select shadow-none" />
															<input type="text"
																class="form-control page-number-span shadow-none" />
														</div>
													</li>
													<li class="page-item"><button type="button"
															class="page-link pagination-next pagination-button shadow-none">Next</button>
													</li>
												</ul>
											</nav>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>			


		
	<div style="display: Block;" id="totalaffected">
		<div class="row second" style="padding-left: 15px;">								
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Affected</span>
						<input readonly type="text" id="totalAffected" class="form-control text-input" style="width:120px;"/>
					</div>
				</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Total Affected Clients</span>
					<input readonly type="text" id="totalAffectedClients" class="form-control text-input" style="width:120px;" />
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Total Affected Sites</span>
					<input readonly type="text" id="totalAffectedSites" class="form-control text-input" style="width:120px;" />
				</div>
			</div>
		</div>
				
	</div>
		</div>
      </div>
    </div>
  </div>

  <div class="panel panel-default" style="margin-bottom:3px;" >
  <div class="panel-heading " role="tab" id="headingTwo" >
      <h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="#collapseTwo" style="margin-top:-8px;">
         GIS
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
			<div style="text-align: center; margin-top: 10px;">
		<div><input id="mapLongLat" type='text' disabled style="width:300px;height:35px; text-align: center; margin-left:52px;position:relative;top:-1px;" /></div>
		</div>
      <div class="legendContainer">
      <div class="card-body">      
         <div class="box stack-top" id="legendDiv" style="position: relative;top:215px;width: 290px; float:left; height:470px;  background:white; margin:37px;display: none">
         <div class="legendHeader"  id="legendHeader">
 			<h6 style="color:white;font-weight:bold; font-size:2.5ex;display:inline-block;position: relative;">Map Legend</h6>
  		</div>
  
   <div id="tableDiv">
  	<table id="cableBreakTableReport">
  <tr>
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>    
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>
    <th style="position: relative;left:10px;"></th>
  </tr>
  
  	<tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideCableCheckbox" onclick="showHideCable();" value="blue"/></td>
     <td style="position: relative;top:10px;left:62px;"><div><img class='image' style='color: #08526D;'  src='${pageContext.request.contextPath}/resources/NetworkImages/fiber.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Fiber Cable</label></td>   
     <td style="position: relative;top:8px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="fiberCount" ></div></td>
    </tr>
    
    <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideRelatedCableCheckbox" onclick="showCableRelatedPath();" /></td>
     <td style="position: relative;top:10px;left:62px;"><div><img class='image' style='color: #08526D;'  src='${pageContext.request.contextPath}/resources/NetworkImages/fiber.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Show Related Path</label></td>   
     <td style="position: relative;top:8px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="relatedPathCount" ></div></td>
    </tr>
    
    <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllManholesWithJctCheckbox" onclick="showHidePts('showHideAllManholesWithJctCheckbox');" value="red"/></td>
     <td style="position: relative;top:8px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/manholeJct.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Manhole with JCT</label></td>   
     <td style="position: relative;top:8px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="manholesCountWithJct" ></div></td>
    </tr>
    
      <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllHandholesWithJctCheckbox" onclick="showHidePts('showHideAllHandholesWithJctCheckbox');" value="yellow"/></td>
     <td style="position: relative;top:8px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/handholeYellowJct.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Handhole with JCT</label></td>   
     <td style="position: relative;top:8px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="handholesCountWithJct" ></div></td>
    </tr>
    
    <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllDbCheckbox" onclick="showHidePts('showHideAllDbCheckbox');" value="blue"/></td>
     <td style="position: relative;top:8px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/backboneDB.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >DB</label></td>   
     <td style="position: relative;top:8px;"><div style="position: relative;left:-5px;color: black;" id="dbCount" ></div></td>
    </tr>
     <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllJctCheckbox" onclick="showHidePts('showHideAllJctCheckbox');" value="orange"/></td>
     <td style="position: relative;top:8px;left:58px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/junctionOrange.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Junction</label></td>   
     <td style="position: relative;top:8px;"><div style="position: relative;left:-5px;color: black;" id="jctCount" ></div></td>
    </tr>
      <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllCustCheckbox" onclick="showHidePts('showHideAllCustCheckbox');" value="red"/></td>
     <td style="position: relative;top:8px;left:58px;"><div><img class='image' style="width: 30px; height: 30px;" src='${pageContext.request.contextPath}/resources/NetworkImages/customerIcon.png'></div></td>
     <td style="position: relative;top:10px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Customer</label></td>   
     <td style="position: relative;top:8px;"><div style="position: relative;left:-5px;color: black;" id="custCount" ></div></td>
    </tr>
     <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllSitesCheckbox" onclick="showHidePts('showHideAllSitesCheckbox');" value="pink"/></td>
     <td style="position: relative;top:8px;left:58px;"><div><img class='image' style="width: 25px; height: 30px;" src='${pageContext.request.contextPath}/resources/NetworkImages/redSiteIcon.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Site</label></td>   
     <td style="position: relative;top:8px;"><div style="position: relative;left:-5px;color: black;" id="sitesCount" ></div></td>
    </tr>
    <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideSrcDestCheckbox" onclick="showHideSrcDest();" value="pink"/></td>
     <td style="position: relative;top:8px;left:58px;"><div><img class='image' style="width: 25px; height: 30px;" src='${pageContext.request.contextPath}/resources/NetworkImages/SrcDest.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Src/Dest</label></td>   
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
  
	 <menu class="menu" id="mapMenu">
		<li class="menu-item mapMenuItem">
			<button type="button" id="getCableBreakCoordinate" class="menu-btn"> <i class="fa fa-paste"></i> <span class="menu-text">Get Coordinate</span></button>
		</li>
	</menu>
  
  
</div>
</body>

<script>

var map ;
var markersDB=[];	
var markersJct=[];
var markersCustomer =[];
var markersSites =[];
var markersManholesWithJct =[];
var markersHandholesWithJct =[];
var markersManholes =[];
var markersHandholes =[];
var fiberCableArray=[];
var relatedPathArray=[];
var distinctDB =[]; // used in check/uncheck all db from legend
var distinctJct =[]; // used in check/uncheck all jct from legend
var distinctCustomers =[]; // used in check/uncheck all cust from legend
var distinctSites =[]; // used in check/uncheck all sites from legend
var distinctManholesWithJct =[]; 
var distinctHandholesWithJct =[]; 
var distinctHandholes = [];
var distinctManholes = [];
var allCables=[];
var allRelatedPathCables=[];
var markerClusterDB ;
var markerClusterJct ;
var markerClusterCustomers ;
var markerClusterSites ;
var markerClusterManholesWithJct;
var markerClusterHandholesWithJct;
var markerClusterManholes;
var markerClusterHandholes;
var showRelPathFlag="notOpened";
var mapFlag="0"; // used to check if the markers are set on map
var infoWindow;
var cableInfoWindow;
var MapMenu;
var filteredGridData=[]; // used in draw on map 
var affectedElement=[];
var cableID = "";
var pointLong ="";
var pointLat ="";
var getCoorLong ="";
var getCoorLat ="";
let breakmarker =null;
var srcDestID = [];


function initMap() {	
	 map = new google.maps.Map(document.getElementById("mapContainer"), {
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

	
		//Add legend button under zoom control on map
		const mapLegendControlDiv = document.createElement("div");
		ShowHideMapLegend(mapLegendControlDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(mapLegendControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	   
	    $("#legendDiv").toggle(); // to open the legend 

	 // Define the cluster
	 markerClusterDB = new MarkerClusterer();
	 markerClusterDB.setMap(map);

	 markerClusterDB.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/blueCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });

	 
	 markerClusterJct = new MarkerClusterer();
	 markerClusterJct.setMap(map);

	 markerClusterJct.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/orangeCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });

	 
	 markerClusterCustomers = new MarkerClusterer();
	 markerClusterCustomers.setMap(map);

	 markerClusterCustomers.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/yellowCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });

	 markerClusterSites = new MarkerClusterer();
	 markerClusterSites.setMap(map);

	 markerClusterSites.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/pinkCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });


	 markerClusterManholesWithJct= new MarkerClusterer();
	 markerClusterManholesWithJct.setMap(map);

	 markerClusterManholesWithJct.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/redCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });

	 markerClusterManholes = new MarkerClusterer();
	 markerClusterManholes.setMap(map);

	 markerClusterManholes.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/redCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });

	 

	 markerClusterHandholesWithJct  = new MarkerClusterer();
	 markerClusterHandholesWithJct.setMap(map);

	 markerClusterHandholesWithJct.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/yellowCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });

	 markerClusterHandholes = new MarkerClusterer();
	 markerClusterHandholes.setMap(map);

	 markerClusterHandholes.setOptions( {					  					
	 	minimumClusterSize: 2,
	 	styles: [
	 	         {
	 	        	 url:'${pageContext.request.contextPath}/resources/clusterIcons/yellowCluster.png',
	 		         height: 60,
	 		         width:60,
	 		         anchorText:[-3,-3]
	 		      },
	 	],
	 	calculator: function(markers, numStyles) {
	 	if (markers.length >= 1) return {text: markers.length, index: 3}; 
	 	}                   
	 });


	   MenuMap = document.getElementById("mapMenu");
	    google.maps.event.addListener(map, 'rightclick', function (e) {
	    	for (prop in e) {
	    		if (e[prop] instanceof MouseEvent) {
				       ShowContextMenuGoolge(MenuMap, e[prop].clientX,e[prop].clientY); 
			           break;
			        }
			    }   
	    e.stop();
	    });

	    google.maps.event.addListener(map, 'click', function () {	
		       HideContextMenuGoolge(MenuMap);
		    });	

	
	 
	 
	 infoWindow = new google.maps.InfoWindow(); // Define the info window to use it when clicking on marker
	 cableInfoWindow =  new google.maps.InfoWindow();
	 getLongLatMouseMove(map); // Add long/lat above the map	
	     
}//end initMap

function ShowContextMenuGoolge(ContextMenu, eventX, eventY) {

    // Calculate the actual position for the context menu
    let x = eventX + window.scrollX;
    let y = eventY + window.scrollY;

    // Adjust the position if it goes outside the window bounds
    const mw = ContextMenu.offsetWidth;
    const mh = ContextMenu.offsetHeight;

    const windowWidth = window.innerWidth + window.scrollX;
    const windowHeight = window.innerHeight + window.scrollY;

    if (x + mw > windowWidth) {
        x = windowWidth - mw;
    }

    if (y + mh > windowHeight) {
        y = windowHeight - mh;
    }

    ContextMenu.style.top = y + "px";
    ContextMenu.style.left = x + "px";
    ContextMenu.classList.add('show-menu');
}

function HideContextMenuGoolge(ContextMenu) {
    ContextMenu.classList.remove('show-menu');

}

function getContext() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
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
		map.setZoom(7);	
     });

  }

$(document).ready(function() {

	$( "#legendDiv" ).draggable(); 
	
	//collapse active class	
	$('.panel-collapse').on('show.bs.collapse',function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});

	$('.panel-collapse').on('hide.bs.collapse',function() {
		$(this).siblings('.panel-heading').addClass('active');
	});

	//To hide the right click menu when clicking on the page
	$("#CableBreakReportDiv").on('click', function(){
		HideContextMenuGoolge(MenuMap);
	});

	$("#getCableBreakCoordinate").on('click',function(){
		
		 getCoorLong =getCoords().split(" ")[1];
		 getCoorLat =getCoords().split(" ")[0];
		 createBreakId(getCoorLong,getCoorLat);

		});

	$("#SetCableBreakCoordinate").on('click',function(){
		
		$("#pointLong").val(getCoorLong);
		$("#pointLat").val(getCoorLat);

		});
	

			
	   $('#showOnMap'). click(function(){  
		   distinctDB =[]; 
		   distinctJct =[]; 
		   distinctCustomers =[]; 
		   distinctSites =[];
		   distinctManholesWithJct =[]; 
		   distinctHandholesWithJct =[];
		   distinctHandholes = [];
		   distinctManholes = []; 
		   srcDestID = [];
		   markerClusterDB.clearMarkers();
		   markerClusterJct.clearMarkers();
		   markerClusterCustomers.clearMarkers();
		   markerClusterHandholesWithJct.clearMarkers();
		   markerClusterManholesWithJct.clearMarkers();	
		   markerClusterSites.clearMarkers();
		   markerClusterManholes.clearMarkers();
		   markerClusterHandholes.clearMarkers(); 	
		   mapFlag="1";

		   if(relatedPathArray.length>0) {
				 for(var b=0;b<allRelatedPathCables.length;b++){
					 relatedPathArray[allRelatedPathCables[b]].setMap(null);
				}
			 }

		showPointsArray=[];
		//build src dest markers
		if(window["mapPointsNames_"+cableID] != undefined) {
			showPointsArray = window["mapPointsNames_"+cableID];
			//console.log("showPointsArray "+showPointsArray)
			for(var x=0;x<showPointsArray.length;x++) {
				if(x ==0 || x ==(showPointsArray.length-1)){// only src and destination 
				if(showPointsArray[x].startsWith("WARE_")==true) {
					var wareID = showPointsArray[x].split(":")[1];
					var longLat = String(window["mapPoints_"+cableID][x]).replaceAll(/[( )]/g, '');
					srcDestID.push([wareID,"warehouse"]);
					if(distinctSites.includes(wareID)==false) {
						distinctSites.push(wareID);
						if(!markersSites[wareID]){
							createMarker(wareID,longLat.split(",")[1],longLat.split(",")[0],showPointsArray[x].split(":")[2],'redSiteIcon.png',markersSites,markerClusterSites)
						}
						else {					
							markersSites[wareID].setMap(map);
							markerClusterSites.addMarker(markersSites[""+wareID]);
						}
					}
					
				}
				else if(showPointsArray[x].startsWith("CUST_")==true) {
					var ID = showPointsArray[x].split(":")[0];
					var longLat = String(window["mapPoints_"+cableID][x]).replaceAll(/[( )]/g, '');
					srcDestID.push([ID,"customer"]);
					if(distinctCustomers.includes(ID)==false) {
						distinctCustomers.push(ID);
						if(!markersCustomer[ID]){
							createMarker(ID,longLat.split(",")[1],longLat.split(",")[0],showPointsArray[x].split(":")[1],'customerIcon.png',markersCustomer,markerClusterCustomers)
						}
						else {					
							markersCustomer[ID].setMap(map);
							markerClusterCustomers.addMarker(markersCustomer[""+ID]);
						}
					}
				}
				
				else if(showPointsArray[x].startsWith("MH_")==true) {
					var ID = showPointsArray[x].split(":")[0];
					var longLat = String(window["mapPoints_"+cableID][x]).replaceAll(/[( )]/g, '');
					var manholeName = showPointsArray[x].split(":")[1];
					
					if(manholeName.endsWith("_J")) {
						srcDestID.push([ID,"manholewithJct"]);
						if(distinctManholesWithJct.includes(ID)==false) {
							distinctManholesWithJct.push(ID);
							if(!markersManholesWithJct[ID]){
								createMarker(ID,longLat.split(",")[1],longLat.split(",")[0],showPointsArray[x].split(":")[1],"manholeJct.png",markersManholesWithJct,markerClusterManholesWithJct)
							}
							else {					
								markersManholesWithJct[ID].setMap(map);
								markerClusterManholesWithJct.addMarker(markersManholesWithJct[""+ID]);
							}
						}
					}
					else {
						srcDestID.push([ID,"manhole"]);
						if(distinctManholes.includes(ID)==false) {
							distinctManholes.push(ID);
							if(!markersManholes[ID]){
								createMarker(ID,longLat.split(",")[1],longLat.split(",")[0],showPointsArray[x].split(":")[1],"manholeRed.png",markersManholes,markerClusterManholes)
							}
							else {					
								markersManholes[ID].setMap(map);
								markerClusterManholes.addMarker(markersManholes[""+ID]);
							}
						}
						
					}					
				}
				else if(showPointsArray[x].startsWith("HH_")==true) {
					var ID = showPointsArray[x].split(":")[0];
					var longLat = String(window["mapPoints_"+cableID][x]).replaceAll(/[( )]/g, '');
					var handholeName = showPointsArray[x].split(":")[1];
					if(handholeName.endsWith("_J")) {
						srcDestID.push([ID,"handholewithJct"]);
						if(distinctHandholesWithJct.includes(ID)==false) {
							distinctHandholesWithJct.push(ID);
							if(!markersHandholesWithJct[ID]){
								createMarker(ID,longLat.split(",")[1],longLat.split(",")[0],showPointsArray[x].split(":")[1],"handholeYellowJct.png",markersHandholesWithJct,markerClusterHandholesWithJct)
							}
							else {					
								markersHandholesWithJct[ID].setMap(map);
								markerClusterHandholesWithJct.addMarker(markersHandholesWithJct[""+ID]);
							}
						}
					}
					else {
						srcDestID.push([ID,"handhole"]);
						if(distinctHandholes.includes(ID)==false) {
							distinctHandholes.push(ID);
							if(!markersHandholes[ID]){
								createMarker(ID,longLat.split(",")[1],longLat.split(",")[0],showPointsArray[x].split(":")[1],"handholeYellow.png",markersHandholes,markerClusterHandholes)
							}
							else {					
								markersHandholes[ID].setMap(map);
								markerClusterHandholes.addMarker(markersHandholes[""+ID]);
							}
						}
						
					}		


					
					
				} 
				else if(showPointsArray[x].startsWith("DB_")==true) {
					var ID = showPointsArray[x].split(":")[0];
					var longLat = String(window["mapPoints_"+cableID][x]).replaceAll(/[( )]/g, '');
					srcDestID.push([ID,"DB"]);
					if(distinctDB.includes(ID)==false) {
						distinctDB.push(ID);
						if(!markersDB[ID]){
							createMarker(ID,longLat.split(",")[1],longLat.split(",")[0],showPointsArray[x].split(":")[1],'backboneDB.png',markersDB,markerClusterDB)
						}
						else {					
							markersDB[ID].setMap(map);
							markerClusterDB.addMarker(markersDB[""+ID]);
						}
					}
				}				
			}
		}

		}
				   
			
		
			for (var i = 0; i < filteredGridData.length; i++) {

				if(filteredGridData[i]["locationType"] =="Customer"){
					if(distinctCustomers.includes(filteredGridData[i]["locationId"])==false) {
						ID = filteredGridData[i]["locationId"];
						distinctCustomers.push(ID);
						if(!markersCustomer[ID]){
							createMarker(ID,filteredGridData[i]["locationLongitude"],filteredGridData[i]["locationLatitude"],filteredGridData[i]["locationName"],'customerIcon.png',markersCustomer,markerClusterCustomers)
						}
						else {					
							markersCustomer[ID].setMap(map);
							markerClusterCustomers.addMarker(markersCustomer[""+ID]);
						}
					}
				}// end customer case		
				else if(filteredGridData[i]["locationType"] =="Site"){
					if(distinctSites.includes(filteredGridData[i]["locationId"])==false) {
						ID = filteredGridData[i]["locationId"];
						distinctSites.push(ID);
						if(!markersSites[ID]){
							createMarker(ID,filteredGridData[i]["locationLongitude"],filteredGridData[i]["locationLatitude"],filteredGridData[i]["locationName"],'redSiteIcon.png',markersSites,markerClusterSites)
						}
						else {					
							markersSites[ID].setMap(map);
							markerClusterSites.addMarker(markersSites[""+ID]);
						}
					}
				}// end site case	
				
	        } 
			////////////////7
			//show affected element on the map
			for (var i = 0; i < affectedElement.length; i++) {
				if(affectedElement[i][4] =="DB"){
					if(distinctDB.includes(affectedElement[i][0])==false) {
						ID = affectedElement[i][0];
						var longitude = affectedElement[i][2];
						var latitude = affectedElement[i][3];
						var Name = affectedElement[i][1];
						distinctDB.push(ID);
						if(!markersDB[ID]){
							createMarker(ID,longitude,latitude,Name,'backboneDB.png',markersDB,markerClusterDB)
						}
						else {					
							markersDB[ID].setMap(map);
							markerClusterDB.addMarker(markersDB[""+ID]);
						}
					}
				}// end db case

				else if(affectedElement[i][4] =="Junction"){
					if(affectedElement[i][5].startsWith("MH")){
						if(distinctManholesWithJct.includes(affectedElement[i][0])==false) {
							ID = affectedElement[i][0];
							var longitude = affectedElement[i][2];
							var latitude = affectedElement[i][3];
							var Name = affectedElement[i][1];
							distinctManholesWithJct.push(ID);
							if(!markersManholesWithJct[ID]){
								createMarker(ID,longitude,latitude,Name,"manholeJct.png",markersManholesWithJct,markerClusterManholesWithJct)
							}
							else {					
								markersManholesWithJct[ID].setMap(map);
								markerClusterManholesWithJct.addMarker(markersManholesWithJct[""+ID]);
							}
						}
					}
					else if(affectedElement[i][5].startsWith("HH")){

						if(distinctHandholesWithJct.includes(affectedElement[i][0])==false) {
							ID = affectedElement[i][0];
							var longitude = affectedElement[i][2];
							var latitude = affectedElement[i][3];
							var Name = affectedElement[i][1];
							distinctHandholesWithJct.push(ID);
							if(!markersHandholesWithJct[ID]){
								createMarker(ID,longitude,latitude,Name,"handholeYellowJct.png",markersHandholesWithJct,markerClusterHandholesWithJct)
							}
							else {					
								markersHandholesWithJct[ID].setMap(map);
								markerClusterHandholesWithJct.addMarker(markersHandholesWithJct[""+ID]);
							}
						}


						}

					//independent junctions 
					else{
						if(distinctJct.includes(affectedElement[i][0])==false) {
							ID = affectedElement[i][0];
							var longitude = affectedElement[i][2];
							var latitude = affectedElement[i][3];
							var Name = affectedElement[i][1];
							distinctJct.push(ID);
							if(!markersJct[ID]){
								createMarker(ID,longitude,latitude,Name,'junctionOrange.png',markersJct,markerClusterJct)
							}
							else {					
								markersJct[ID].setMap(map);
								markerClusterJct.addMarker(markersJct[""+ID]);
							}
						}
					}
					
				}// end jct case
				
			}
	        //////7777777777
	        if(distinctCustomers.length >0) {
				$('.showHideAllCustCheckbox').prop('checked', true);
				$(".showHideAllCustCheckbox").attr('disabled', false);
	        }   
	        else {
	        	$('.showHideAllCustCheckbox').prop('checked', false);
				$(".showHideAllCustCheckbox").attr('disabled', true);
	         } 
	        if(distinctSites.length >0) {
				$('.showHideAllSitesCheckbox').prop('checked', true);
				$(".showHideAllSitesCheckbox").attr('disabled', false);
	        }   
	        else {
	        	$('.showHideAllSitesCheckbox').prop('checked', false);
				$(".showHideAllSitesCheckbox").attr('disabled', true);
	         } 
	       
	        if(distinctManholesWithJct.length >0) {
				$('.showHideAllManholesWithJctCheckbox').prop('checked', true);
				$(".showHideAllManholesWithJctCheckbox").attr('disabled', false);
	        }   
	        else {
	        	$('.showHideAllManholesWithJctCheckbox').prop('checked', false);
				$(".showHideAllManholesWithJctCheckbox").attr('disabled', true);
	         } 
	        if(distinctHandholesWithJct.length >0) {
				$('.showHideAllHandholesWithJctCheckbox').prop('checked', true);
				$(".showHideAllHandholesWithJctCheckbox").attr('disabled', false);
	        }   
	        else {
	        	$('.showHideAllHandholesWithJctCheckbox').prop('checked', false);
				$(".showHideAllHandholesWithJctCheckbox").attr('disabled', true);
	         }
	        if(distinctDB.length >0) {
				$('.showHideAllDbCheckbox').prop('checked', true);
				$(".showHideAllDbCheckbox").attr('disabled', false);
	        }   
	        else {
	        	$('.showHideAllDbCheckbox').prop('checked', false);
				$(".showHideAllDbCheckbox").attr('disabled', true);
	         } 
	        if(distinctJct.length >0) {
				$('.showHideAllJctCheckbox').prop('checked', true);
				$(".showHideAllJctCheckbox").attr('disabled', false);
	        }   
	        else {
	        	$('.showHideAllJctCheckbox').prop('checked', false);
				$(".showHideAllJctCheckbox").attr('disabled', true);
	         } 

	        if(srcDestID.length >0) {
				$('.showHideSrcDestCheckbox').prop('checked', true);
				$(".showHideSrcDestCheckbox").attr('disabled', false);
	        }   
	        else {
	        	$('.showHideSrcDestCheckbox').prop('checked', false);
				$(".showHideSrcDestCheckbox").attr('disabled', true);
	         } 
	         
	         
	        document.getElementById("sitesCount").textContent = "("+distinctSites.length+")";  
	    	document.getElementById("manholesCountWithJct").textContent = "("+distinctManholesWithJct.length+")";
			document.getElementById("handholesCountWithJct").textContent = "("+distinctHandholesWithJct.length+")";
			document.getElementById("custCount").textContent = "("+distinctCustomers.length+")";
			document.getElementById("jctCount").textContent = "("+distinctJct.length+")";
			document.getElementById("dbCount").textContent = "("+distinctDB.length+")";  

			map.fitBounds(window["bounds_"+cableID]);
			
			//Scroll to the map div
			 document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
			 
		         	
		});// show on map end

	   function getFiberPath(cableID){

			
			 $.ajax({
		         type: "GET",
		         contentType: "application/json; charset=utf-8",
		         url: getContext() + '/getCableBreakFiberPath',
		         data: {
		             "cableID": cableID
		         },
		         dataType: "json",
		         success: function (data) {
		        	 if (data != null) {

		            	//Disable and uncheck the checkboxes in legend
		         		$('.showHideAllDbCheckbox').prop('checked', false);
		         		$(".showHideAllDbCheckbox").attr('disabled', true);
		         		$('.showHideAllJctCheckbox').prop('checked', false);
		         		$(".showHideAllJctCheckbox").attr('disabled', true);
		         		$('.showHideAllCustCheckbox').prop('checked', false);
		         		$(".showHideAllCustCheckbox").attr('disabled', true);
		         		$
		         		$('.showHideAllHandholesWithJctCheckbox').prop('checked', false);
		         		$(".showHideAllHandholesWithJctCheckbox").attr('disabled', true);
		         		
		         		$('.showHideAllManholesWithJctCheckbox').prop('checked', false);
		         		$(".showHideAllManholesWithJctCheckbox").attr('disabled', true)
		         		$('.showHideAllSitesCheckbox').prop('checked', false);
		         		$(".showHideAllSitesCheckbox").attr('disabled', true);
		         		$('.showHideCableCheckbox').prop('checked', false);
		         		$(".showHideCableCheckbox").attr('disabled', true);
		         		$('.showHideSrcDestCheckbox').prop('checked', false);
		         		$(".showHideSrcDestCheckbox").attr('disabled', true);

		         		$('.showHideRelatedCableCheckbox').prop('checked', false);
		        		$(".showHideRelatedCableCheckbox").attr('disabled', true);
		        		document.getElementById("relatedPathCount").textContent = "";

		        		if(relatedPathArray.length>0) {
		       			 for(var b=0;b<allRelatedPathCables.length;b++){
		       				 relatedPathArray[allRelatedPathCables[b]].setMap(null);
		       			}
		       		 }
		         		
		         		//showRelPathFlag="notOpened";

		         		infoWindow.close();
		         		cableInfoWindow.close();
		         		
		         		 // Clear the map and arrays related to map
		         		 markerClusterDB.clearMarkers();	
		         		 markersDB=[];	  
		         		 document.getElementById("dbCount").textContent = "";

		         		 markerClusterJct.clearMarkers();	
		         		 markersJct=[];	  
		         		 document.getElementById("jctCount").textContent = "";

		         		 markerClusterCustomers.clearMarkers();	
		         		 markersCustomer=[];	  
		         		 document.getElementById("custCount").textContent = "";

		         		 markerClusterHandholesWithJct.clearMarkers();
		         		 markersHandholesWithJct=[];	   
		         		 document.getElementById("handholesCountWithJct").textContent = "";
		         		 
		         		 markerClusterManholesWithJct.clearMarkers();
		         		 markersManholesWithJct=[];	  
		         		 document.getElementById("manholesCountWithJct").textContent = "";

		         		 markerClusterSites.clearMarkers();	
		         		 markersSites=[];
		         		 document.getElementById("sitesCount").textContent = "";

		         		markerClusterManholes.clearMarkers();
		         		markersManholes =[];
		         		
		     		    markerClusterHandholes.clearMarkers();
		     		    markersHandholes =[];


		         		//Clear all arrays and inputs related to map when the data in grid is filtered
			   		 	 distinctDB =[]; 
						 distinctJct =[]; 
						 distinctCustomers =[];
						 distinctSites =[];  
						 distinctManholesWithJct =[]; 
						 distinctHandholesWithJct =[]; 
						 distinctHandholes = [];
						 distinctManholes = []; 
						 filteredGridData=[];
						 affectedElement=[];
						 srcDestID =[]
						 
						//clear break pt marker
						 if (breakmarker) {
								breakmarker.setMap(null);
					        }
						 	  
					 		

					 	if(fiberCableArray.length>0) {
							 for(var v=0;v<allCables.length;v++){
					          	fiberCableArray[allCables[v]].setMap(null);
							}
						 }
						 
						 
						 fiberCableArray=[];
						 //cableID="";	  
						 allCables=[];

						 mapFlag="0"; 	


						window["mapPointsNames_"+cableID]=[];
						
						
						//Push src
						if(data.fiberList[0][4] !="null"){
							src = data.fiberList[0][4]+":" +data.fiberList[0][5]+":"+data.fiberList[0][6];		
						}
						else {
						 if (data.fiberList[0][5].startsWith("MH") ==true || data.fiberList[0][5].startsWith("HH") ==true  || data.fiberList[0][5].startsWith("DB") ==true  || data.fiberList[0][5].startsWith("CUST") ==true ) {
								src  = data.fiberList[0][5]+":" +data.fiberList[0][6];	
							}
							else {
								src = data.fiberList[0][6];
							}
						}
						window["mapPointsNames_"+cableID].push(src);

						//Push dst
						if(data.fiberList[0][7] !="null"){
							dst = data.fiberList[0][7]+":" +data.fiberList[0][8]+":"+data.fiberList[0][9];		
						}
						else {
						 if (data.fiberList[0][8].startsWith("MH") ==true || data.fiberList[0][8].startsWith("HH") ==true  || data.fiberList[0][8].startsWith("DB") ==true  || data.fiberList[0][8].startsWith("CUST") ==true ) {
							 dst  = data.fiberList[0][8]+":" +data.fiberList[0][9];	
							}
							else {
								dst = data.fiberList[0][9];
							}
						}
						window["mapPointsNames_"+cableID].push(dst);

		               
		                         
		                window["mapPoints_"+cableID]=[]; 
						window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberList[0][1],data.fiberList[0][0]));

						window["bounds_"+cableID] = new google.maps.LatLngBounds();			
						window["bounds_"+cableID].extend(new google.maps.LatLng(data.fiberList[0][1],data.fiberList[0][0]));
						window["bounds_"+cableID].extend(new google.maps.LatLng(data.fiberList[0][3],data.fiberList[0][2]));
						
						 
						
						for(i=0;i<data.fiberAuxData.length;i++){//PUSH AUXILIARY POINTS OF FIBER CABLE	
							window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberAuxData[i][1],data.fiberAuxData[i][0]));
							window["bounds_"+cableID].extend(new google.maps.LatLng(data.fiberAuxData[i][1],data.fiberAuxData[i][0]));

							if(data.fiberAuxData[i][2] =="") {
								auxPoint="";
							}
							else if(data.fiberAuxData[i][2] !="null"){
								auxPoint = data.fiberAuxData[i][2]+":" +data.fiberAuxData[i][3]+":"+data.fiberAuxData[i][4];		
							}
							else {
								
								if (data.fiberAuxData[i][3].startsWith("MH") ==true  || data.fiberAuxData[i][3].startsWith("HH") ==true  ||data.fiberAuxData[i][3].startsWith("DB") ==true ) {
									auxPoint = data.fiberAuxData[i][3]+":" +data.fiberAuxData[i][4];	
								}
								else if (data.fiberAuxData[i][4].includes("Auxiliary_Point")==true) {
									auxPoint = data.fiberAuxData[i][6]+":"+data.fiberAuxData[i][4];
								}
								else {
									auxPoint = data.fiberAuxData[i][4];
								}
							}
							window["mapPointsNames_"+cableID].splice(window["mapPointsNames_"+cableID].length-1, 0,auxPoint);// insert at before last index which is the destination
						}
						

						window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberList[0][3],data.fiberList[0][2]));


						if(allCables.includes(cableID) ==false){
							allCables.push(cableID);
						}
		          		buildPath(cableID,fiberCableArray,$("#fiberCable").val().split(":")[1],window["mapPoints_"+cableID],data.fiberList[0][10],0.7,4.5,'blue',13);
		          		fiberCableArray[cableID].setMap(map);
		          		$('.showHideCableCheckbox').prop('checked', true);
						$(".showHideCableCheckbox").attr('disabled', false);

						map.fitBounds(window["bounds_"+cableID]);
							

						




					 	 

		                 }
		             
		         },
		         error: function (result) {
		             alert("Error");
		         }
		     });
			
		}//end getfiberpath
		

	 $("#fiberCable").autocomplete({
		source: function(request, response) {
			$.ajax({
				type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/getBreakFiberCable',
                data: {
						"requestValue" : $("#fiberCable").val(),
				 },
				 dataType: "json",
		         success: function (data) {
			         if (data != null) {
		             	response(data.FiberCableList);
		             }
		          },
		          error: function(result) {
		              alert("Error");
		           }
		       });
		         }, minLength:0, maxShowItems: 40, scroll:true,

		         select: function(event, ui) {
						fiberCable.value = (ui.item ? ui.item[0]  : '');
						fiberCableName.value = (ui.item[1]);
						cableID = ui.item[0];
						getFiberPath(cableID);
						$('#tableGrid tbody').empty();
						return false;
				}
				}).autocomplete("instance")._renderItem = function(ul, item) {
	 		    	return $('<li class="each">').data( "item.autocomplete", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	                 item[0] + '</span><br><span class="desc">' +
	                 item[1] + '</span></div></li>').appendTo(ul);
			};
		$("#fiberCable").focus(function(){
			if (this.value == ""){
		   	    $(this).autocomplete("search");
		   	}						
		});   // end of cable autocomplete

		 $("#fiberCableName").autocomplete({
				source: function(request, response) {
					$.ajax({
						type: "GET",
		                contentType: "application/json; charset=utf-8",
		                url: '${pageContext.request.contextPath}/getBreakFiberCable',
		                data: {
								"requestValue" : $("#fiberCableName").val(),
						 },
						 dataType: "json",
				         success: function (data) {
					         if (data != null) {
				             	response(data.FiberCableList);
				             }
				          },
				          error: function(result) {
				              alert("Error");
				           }
				       });
				         }, minLength:0, maxShowItems: 40, scroll:true,

				         select: function(event, ui) {
								fiberCable.value = (ui.item ? ui.item[0]  : '');
								fiberCableName.value = (ui.item[1]);
								cableID = ui.item[0];
								getFiberPath(cableID);
								$('#tableGrid tbody').empty();
								return false;
						}
						}).autocomplete("instance")._renderItem = function(ul, item) {
			 		    	return $('<li class="each">').data( "item.autocomplete", item )
				    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
			                 item[1] + '</span><br><span class="desc">' +
			                 item[0] + '</span></div></li>').appendTo(ul);
					};
				$("#fiberCableName").focus(function(){
					if (this.value == ""){
				   	    $(this).autocomplete("search");
				   	}						
				});
					
 
		 
	  $("#generate").click(function() {


		//Disable and uncheck the checkboxes in legend
		$('.showHideAllDbCheckbox').prop('checked', false);
		$(".showHideAllDbCheckbox").attr('disabled', true);
		$('.showHideAllJctCheckbox').prop('checked', false);
		$(".showHideAllJctCheckbox").attr('disabled', true);
		$('.showHideAllCustCheckbox').prop('checked', false);
		$(".showHideAllCustCheckbox").attr('disabled', true);
		$
		$('.showHideAllHandholesWithJctCheckbox').prop('checked', false);
		$(".showHideAllHandholesWithJctCheckbox").attr('disabled', true);
		
		$('.showHideAllManholesWithJctCheckbox').prop('checked', false);
		$(".showHideAllManholesWithJctCheckbox").attr('disabled', true)
		$('.showHideAllSitesCheckbox').prop('checked', false);
		$(".showHideAllSitesCheckbox").attr('disabled', true);
		$('.showHideCableCheckbox').prop('checked', false);
		$(".showHideCableCheckbox").attr('disabled', true);
		$('.showHideSrcDestCheckbox').prop('checked', false);
		$(".showHideSrcDestCheckbox").attr('disabled', true);

		$('.showHideRelatedCableCheckbox').prop('checked', false);
		$(".showHideRelatedCableCheckbox").attr('disabled', false);
		
		showRelPathFlag="notOpened";

		infoWindow.close();
		cableInfoWindow.close();
		
		 // Clear the map and arrays related to map
		 markerClusterDB.clearMarkers();	
		 markersDB=[];	  
		 document.getElementById("dbCount").textContent = "";

		 markerClusterJct.clearMarkers();	
		 markersJct=[];	  
		 document.getElementById("jctCount").textContent = "";

		 markerClusterCustomers.clearMarkers();	
		 markersCustomer=[];	  
		 document.getElementById("custCount").textContent = "";

		 markerClusterHandholesWithJct.clearMarkers();
		 markersHandholesWithJct=[];	   
		 document.getElementById("handholesCountWithJct").textContent = "";
		 
		 markerClusterManholesWithJct.clearMarkers();
		 markersManholesWithJct=[];	  
		 document.getElementById("manholesCountWithJct").textContent = "";

		 markerClusterSites.clearMarkers();	
		 markersSites=[];	  
		 document.getElementById("sitesCount").textContent = "";

		markerClusterManholes.clearMarkers();
  		markersManholes =[];
  		
	    markerClusterHandholes.clearMarkers();
	    markersHandholes =[];

		 if(fiberCableArray.length>0) {
			 for(var v=0;v<allCables.length;v++){
	          	fiberCableArray[allCables[v]].setMap(null);
			}
		 }

		 if(relatedPathArray.length>0) {
			 for(var b=0;b<allRelatedPathCables.length;b++){
				 relatedPathArray[allRelatedPathCables[b]].setMap(null);
			}
		 }
	
		 fiberCableArray=[];
		 relatedPathArray=[];
		 cableID="";	  
		 allCables=[];		
		 allRelatedPathCables=[];
		 document.getElementById("relatedPathCount").textContent = "";		
		 
		 mapFlag="0";	
		  
		 //Recenter the map
		 var center=new google.maps.LatLng(1,38);
	     map.setCenter(center);
		 map.setZoom(6);  
		  
		$("#gridTable").remove();
		$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header fixed-header">'
				+'<th>Location ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Warehouse ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Longitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Latitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th><li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown" disabled style="display:none;"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th><li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown" disabled style="display:none;"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<tr><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" disabled class="almgrid-search" style="display:none"></th><th><input type="text" disabled class="almgrid-search" style="display:none"></th></tr></thead><tbody></tbody></table>');
			
			

		if($("#fiberCable").val() =="") {
			alert("Please enter a cable!");

		}
		else if($("#pointLong").val() =="") {
			alert("Please enter a Point Longitude!");

		}
		else if($("#pointLat").val() =="") {
			alert("Please enter a Point latitude!");

		}

		else {
			$("#generateLoaderDiv").show();
		 cableID = $("#fiberCable").val().split(":")[0];
		 pointLong = $("#pointLong").val();
		 pointLat = $("#pointLat").val();
								
		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateBreakPointReport",
			dataType : "json",
			data : {
			    "cableID" : cableID,
			    "pointLong" : pointLong,
			    "pointLat" : pointLat
			},
			success : function(data) {
			  if (data != null) {
				
               ReportArrayGlobal = data.listAffectedClientSite; 
               
                  if (ReportArrayGlobal.length == 0) { 
                	  alert("There is no data to display");
					  $('#totalAffected').val('0');
					  $('#totalAffectedClients').val('0');
					  $('#totalAffectedSites').val('0');	                	  
                  }
                 	 var almgrid = new AlmgridTable({
                          tableId: "gridTable",
                          dataArray: ReportArrayGlobal,
                          selectCheckbox: false,
                          drawTableGrid :  function (tableId, dataArray) {

         			        var tableBody = document.querySelector("#" + tableId + " tbody");
         			        var columnLinkNb = this.columnLinkNb;
         			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
         			        var gridContainerId = tableId + "_container";
         			        $(gridContainer).attr('id', gridContainerId);
         			        $(tableBody).empty();    


         			       // Clear the map when the data in grid is filtered
          			       if (typeof markerClusterDB !== 'undefined' && markerClusterDB !== null) {
          			    	 	markerClusterDB.clearMarkers(); 
          					}   
          			     	if (typeof markerClusterJct !== 'undefined' && markerClusterJct !== null) {
          			    		markerClusterJct.clearMarkers(); 
       					   	}
          			  	 	if (typeof markerClusterCustomers !== 'undefined' && markerClusterCustomers !== null) {
          				 		markerClusterCustomers.clearMarkers(); 
  					   	 	 }    
          			  		
					   	 	if (typeof markerClusterHandholesWithJct !== 'undefined' && markerClusterHandholesWithJct !== null) {
					   	 		markerClusterHandholesWithJct.clearMarkers(); 
					   	 	 }
          			  		
          			  		if (typeof markerClusterManholesWithJct !== 'undefined' && markerClusterManholesWithJct !== null) {
          			  			markerClusterManholesWithJct.clearMarkers(); 
			   	  			} 
          					if (typeof markerClusterSites !== 'undefined' && markerClusterSites !== null) {
          						markerClusterSites.clearMarkers(); 
		   	  				}  
          					if (typeof markerClusterManholes !== 'undefined' && markerClusterManholes !== null) {
          						markerClusterManholes.clearMarkers(); 
		   	  				}	
          					if (typeof markerClusterHandholes !== 'undefined' && markerClusterHandholes !== null) {
          						markerClusterHandholes.clearMarkers(); 
		   	  				}

          					

          		//Clear all arrays and inputs related to map when the data in grid is filtered
 		   		 	 distinctDB =[]; 
					 distinctJct =[]; 
					 distinctCustomers =[];
					 distinctSites =[];  
					 distinctManholesWithJct =[]; 
					 distinctHandholesWithJct =[]; 
					 distinctHandholes = [];
					 distinctManholes = []; 
 		   		     markersSites=[];
    				 markersManholesWithJct=[];	
    				 srcDestID =[];  	  
     				 	
     				 markersHandholesWithJct=[];	
     				 markersCustomer=[];	  
     				 markersJct=[];	  
     				 markersDB=[];	  
 				 	 mapFlag="0"; 	
 					 document.getElementById("sitesCount").textContent = "";
 					 document.getElementById("manholesCountWithJct").textContent = "";
 					 document.getElementById("handholesCountWithJct").textContent = "";
 					 document.getElementById("custCount").textContent = "";
 					 document.getElementById("jctCount").textContent = "";
 					 document.getElementById("dbCount").textContent = "";
 				 	 
 				 	$('.showHideAllDbCheckbox').prop('checked', false);
 					$(".showHideAllDbCheckbox").attr('disabled', true);
 					$('.showHideAllJctCheckbox').prop('checked', false);
 					$(".showHideAllJctCheckbox").attr('disabled', true);
 					$('.showHideAllCustCheckbox').prop('checked', false);
 					$(".showHideAllCustCheckbox").attr('disabled', true);
 					
 					$('.showHideAllHandholesWithJctCheckbox').prop('checked', false);
 					$(".showHideAllHandholesWithJctCheckbox").attr('disabled', true);
 					
 					$('.showHideAllManholesWithJctCheckbox').prop('checked', false);
 					$(".showHideAllManholesWithJctCheckbox").attr('disabled', true);
 					$('.showHideAllSitesCheckbox').prop('checked', false);
 					$(".showHideAllSitesCheckbox").attr('disabled', true);

 					$('.showHideSrcDestCheckbox').prop('checked', false);
 					$(".showHideSrcDestCheckbox").attr('disabled', true);
 							 	  		 		
          			  var center=new google.maps.LatLng(1,38);
   				        map.setCenter(center);
   						map.setZoom(6); 

   						
         			     if (dataArray.length > 0) {
  					 
  			               var ArrayKeys = Object.keys(dataArray[0]);
  			       		   var columnVal;
  			       		   var data = [];//for export
  			       		   
  			       	       exportArrayGrid = [];
  			       		   data.push('\r');
  			       		   data.push(["Location Id","Location Name","Location Type","Warehouse Id","Longitude","Latitude"]);  	  			       		

  	  			       	   filteredGridData =  dataArray; // used in draw on map 
  	  			       	   
  			           for (var i = 0; i < dataArray.length; i++) {
  	  			           data.push('\r');
  			               for (var j = 0; j < ArrayKeys.length; j++) {
  			            	 columnVal = ArrayKeys[j];
	  			              if(columnVal !="showLocation" && columnVal !="showElement") { 
	  	  			            	data.push(dataArray[i][ArrayKeys[j]]);
	  			            	}

									
								} 
	          			   
  		          		   }

		                   exportArrayGrid.push(data);
  			          					
		              }
  		          	else{
  		          		filteredGridData=[];
  						$('#totalAffected').val('0');
  					  	$('#totalAffectedClients').val('0');
  					 	$('#totalAffectedSites').val('0');
						
  		          		
  	      			}
  				  
  		         	        // Method for pagination almgrid-pagecount-box
  		          	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
  		          	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

  		          	        // For global search textbox
  		          	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

  		          	        var paginationId = tableId + "_pagination";


  		          	        // Page Rows number
  		          	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
  		          	        nbRows = parseInt(nbRows);

  		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox });

  		          	        // Drawing for the first time
  		          	        if (this.initFlag == 0) {
  		          	            var tables = document.getElementsByClassName('almgrid-table');
  		          	            for (var i = 0; i < tables.length; i++) {
  		          	                resizableGrid(tables[i]);
  		          	            }
  																	 
  		          	        }
  		          	        this.initFlag++;
  		          	       },
                           });
				
				window["mapPointsNames_"+cableID]=[];
				
				
				//Push src
				if(data.fiberList[0][4] !="null"){
					src = data.fiberList[0][4]+":" +data.fiberList[0][5]+":"+data.fiberList[0][6];		
				}
				else {
				 if (data.fiberList[0][5].startsWith("MH") ==true || data.fiberList[0][5].startsWith("HH") ==true  || data.fiberList[0][5].startsWith("DB") ==true  || data.fiberList[0][5].startsWith("CUST") ==true ) {
						src  = data.fiberList[0][5]+":" +data.fiberList[0][6];	
					}
					else {
						src = data.fiberList[0][6];
					}
				}
				window["mapPointsNames_"+cableID].push(src);

				//Push dst
				if(data.fiberList[0][7] !="null"){
					dst = data.fiberList[0][7]+":" +data.fiberList[0][8]+":"+data.fiberList[0][9];		
				}
				else {
				 if (data.fiberList[0][8].startsWith("MH") ==true || data.fiberList[0][8].startsWith("HH") ==true  || data.fiberList[0][8].startsWith("DB") ==true  || data.fiberList[0][8].startsWith("CUST") ==true ) {
					 dst  = data.fiberList[0][8]+":" +data.fiberList[0][9];	
					}
					else {
						dst = data.fiberList[0][9];
					}
				}
				window["mapPointsNames_"+cableID].push(dst);

               
                         
                window["mapPoints_"+cableID]=[]; 
				window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberList[0][1],data.fiberList[0][0]));

				window["bounds_"+cableID] = new google.maps.LatLngBounds();			
				window["bounds_"+cableID].extend(new google.maps.LatLng(data.fiberList[0][1],data.fiberList[0][0]));
				window["bounds_"+cableID].extend(new google.maps.LatLng(data.fiberList[0][3],data.fiberList[0][2]));
				
				 
				
				for(i=0;i<data.fiberAuxData.length;i++){//PUSH AUXILIARY POINTS OF FIBER CABLE	
					window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberAuxData[i][1],data.fiberAuxData[i][0]));
					window["bounds_"+cableID].extend(new google.maps.LatLng(data.fiberAuxData[i][1],data.fiberAuxData[i][0]));

					if(data.fiberAuxData[i][2] =="") {
						auxPoint="";
					}
					else if(data.fiberAuxData[i][2] !="null"){
						auxPoint = data.fiberAuxData[i][2]+":" +data.fiberAuxData[i][3]+":"+data.fiberAuxData[i][4];		
					}
					else {
						
						if (data.fiberAuxData[i][3].startsWith("MH") ==true  || data.fiberAuxData[i][3].startsWith("HH") ==true  ||data.fiberAuxData[i][3].startsWith("DB") ==true ) {
							auxPoint = data.fiberAuxData[i][3]+":" +data.fiberAuxData[i][4];	
						}
						else if (data.fiberAuxData[i][4].includes("Auxiliary_Point")==true) {
							auxPoint = data.fiberAuxData[i][6]+":"+data.fiberAuxData[i][4];
						}
						else {
							auxPoint = data.fiberAuxData[i][4];
						}
					}
					window["mapPointsNames_"+cableID].splice(window["mapPointsNames_"+cableID].length-1, 0,auxPoint);// insert at before last index which is the destination
				}
				

				window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberList[0][3],data.fiberList[0][2]));


				
				var totalAffected = data.totalAffectd;
				$('#totalAffected').val(totalAffected);

				var totalAffectedClients =data.totalAffectdClients ;
				$('#totalAffectedClients').val(totalAffectedClients);


				var totalAffectedSites =data.totalAffectdSites;
				$('#totalAffectedSites').val(totalAffectedSites);

				//get affected element list
				affectedElement=data.ElementList;

					

	             
				if(allCables.includes(cableID) ==false){
					allCables.push(cableID);
				}
          		buildPath(cableID,fiberCableArray,$("#fiberCable").val().split(":")[1],window["mapPoints_"+cableID],data.fiberList[0][10],0.7,4.5,'blue',13);
          		fiberCableArray[cableID].setMap(map);
          		$('.showHideCableCheckbox').prop('checked', true);
				$(".showHideCableCheckbox").attr('disabled', false);

				map.fitBounds(window["bounds_"+cableID]);
				createBreakId(pointLong,pointLat);



				for(var c =0;c<data.relatedPathCables.length;c++) {
					var pathID = data.relatedPathCables[c][0];
					if(allRelatedPathCables.includes(pathID) ==false){
						allRelatedPathCables.push(pathID);
					}
					window["mapPoints_"+pathID]=[];
					window["mapPoints_"+pathID].push(new google.maps.LatLng(data.relatedPathCables[c][2],data.relatedPathCables[c][1]));	

					for(var y=0;y<data.fiberAuxDataRelatedPath.length;y++) {
						if(data.fiberAuxDataRelatedPath[y][0] == pathID ) {
							window["mapPoints_"+pathID].push(new google.maps.LatLng(data.fiberAuxDataRelatedPath[y][2],data.fiberAuxDataRelatedPath[y][1]));	
						}

					}
					window["mapPoints_"+pathID].push(new google.maps.LatLng(data.relatedPathCables[c][4],data.relatedPathCables[c][3]));	
	          		buildPath(pathID,relatedPathArray,data.relatedPathCables[c][11],window["mapPoints_"+pathID],data.relatedPathCables[c][12],0.7,4.5,'blue',13);
				}
					

				}

				
			
				$("#generateLoaderDiv").hide();
				
  },
  
  error : function(error) {
		console.log("The error is " + error);
		$("#generateLoaderDiv").hide();
		
	}
		
});
		}		  
		  
	  });	  


function buildPath(Id,fiberArray,Name,path,strokeColor,strokeOpacity,strokeWeight,fontColor,IdNb){

	if(!fiberArray[Id]){

		var idInfo ="<b style='font-size:13px;'><u>ID: </u></b>"+Id;
		var nameInfo ="<b style='font-size:13px;'><u>Name: </u></b>"+Name;
		var data="<div></br>"+idInfo+"</br>"+nameInfo+"</div>";			
		
			flightPath = new google.maps.Polyline({
				path: path,							
				geodesic: false,
				strokeColor: strokeColor,
				ID:Id,			
				strokeOpacity: strokeOpacity,
				strokeWeight: strokeWeight,
		        data:data,
				
			  });				 
				
			flightPath.metadata = { id: Id };
			fiberArray[Id] = flightPath;
			fiberArray.push(flightPath);


				   
			
			// Add click event listener to the polyline
	       /* google.maps.event.addListener(flightPath, 'click', function(event) {
	        	cableInfoWindow.close();
	        	cableInfoWindow.setContent(this.data); 
	        	cableInfoWindow.setPosition(event.latLng);
	        	cableInfoWindow.open(map);
	        });*/
	        
	        
	}
}

		
		$('#gridExport').click(function(){
			  const csvContent = 'data:text/csv;charset=utf-8,' + encodeURIComponent(exportArrayGrid);
			  const downloadLink = document.createElement('a');
			  downloadLink.setAttribute('href', csvContent);
			  downloadLink.setAttribute('download', "CableBreakGridReport");

			  document.body.appendChild(downloadLink);
			  downloadLink.click();
			  document.body.removeChild(downloadLink);
      	});

		
 
});



function createMarker(ID,longitude,latitude,Name,iconImg,markersArray,markerClusterArray) {

	markerId=ID;		
	const pos = new google.maps.LatLng(latitude,longitude);
	if(iconImg=="customerIcon.png"){
		markerIcon = {
				url:getContext()+"/resources/NetworkImages/"+iconImg, 
				scaledSize: new google.maps.Size(40, 40),
		};
	}
	else if(iconImg=="redSiteIcon.png"){
		markerIcon = {
				url:getContext()+"/resources/NetworkImages/"+iconImg, 
				scaledSize: new google.maps.Size(35,35),
		};
	}
	else if(iconImg=="handholeGreen.png") {
		markerIcon = {
				url:getContext()+"/resources/NetworkImages/"+iconImg, 
				scaledSize: new google.maps.Size(10,10),
		};
	}
	
	else {
		markerIcon = {
				url:getContext()+"/resources/NetworkImages/"+iconImg, 
				scaledSize: new google.maps.Size(20, 20),
		};
	}
	var idInfo ="<b style='font-size:13px;'><u>ID: </u></b>"+ID;
	var nameInfo ="<b style='font-size:13px;'><u>Name: </u></b>"+Name;
	var data="<div></br>"+idInfo+"</br>"+nameInfo+"</div>";			
		   
	
	if(!markersArray[markerId]){
		elementMarker = new google.maps.Marker({
			position: pos,
			ID:markerId,
			icon:markerIcon,
	        data:data,
	});
		elementMarker.metadata = { id: markerId };
		markersArray[markerId] = elementMarker;
		markersArray.push(elementMarker);
		markerClusterArray.addMarker(markersArray[""+markerId]);
		markersArray[markerId].setMap(map);

		google.maps.event.addListener(elementMarker, "click", function (e) {
            infoWindow.close();
	     	infoWindow.setContent(this.data); 
        	infoWindow.open(map,this);				
	 	});
			
   }
	else{
		if(markersArray[markerId].map!=map){
			markersArray[markerId].setMap(map);
			markerClusterArray.addMarker(markersArray[""+markerId]);
			
		}				
		markersArray[markerId].setPosition(pos);
	}
	 if(mapFlag=="1"){
		infoWindow.close();
		cableInfoWindow.close();
	}
}     

function showHideCable() {

	$('.showHideCableCheckbox').bind("change",function() {					
			if ($(this).is(':checked')){
				if(fiberCableArray.length>0) {
					 for(var v=0;v<allCables.length;v++){
			          	fiberCableArray[allCables[v]].setMap(map);
					}
				 }
			}
			else {
				if(fiberCableArray.length>0) {
					 for(var v=0;v<allCables.length;v++){
			          	fiberCableArray[allCables[v]].setMap(null);
					}
				 }
			}
			
		});	
}

function showCableRelatedPath(){

	if(showRelPathFlag=="notOpened"){
		showRelPathFlag="Opened";
		document.getElementById("relatedPathCount").textContent = "("+allRelatedPathCables.length+")";
	}

	if(relatedPathArray.length>0) {
		$('.showHideRelatedCableCheckbox').bind("change",function() {					
			if ($(this).is(':checked')){
					 for(var v=0;v<allRelatedPathCables.length;v++){
						 relatedPathArray[allRelatedPathCables[v]].setMap(map);
					}			
			}
			else {
					 for(var v=0;v<allRelatedPathCables.length;v++){
						 relatedPathArray[allRelatedPathCables[v]].setMap(null);
					}
			}
			
		});	
	}
	else {
		alert("No Related Path to show or hide!")
		
	}

}


function showHideSrcDest(){

	$('.showHideSrcDestCheckbox').bind("change",function() {
		console.log("srcDestID "+srcDestID.length)
			for(var x=0;x<srcDestID.length;x++) {
				if(srcDestID[x][1]=="warehouse"){
					markersArray = markersSites;
					clusterArray = markerClusterSites;
				}
				else if(srcDestID[x][1]=="customer"){
					markersArray = markersCustomer;
					clusterArray = markerClusterCustomers;
				}
				else if(srcDestID[x][1]=="manhole"){
					markersArray = markersManholes;
					clusterArray = markerClusterManholes;
				}
				else if(srcDestID[x][1]=="manholewithJct"){
					markersArray = markersManholesWithJct;
					clusterArray = markerClusterManholesWithJct;
				}
				else if(srcDestID[x][1]=="handhole"){
					markersArray = markersHandholes;
					clusterArray = markerClusterHandholes;
				}
				else if(srcDestID[x][1]=="handholewithJct"){
					markersArray = markersHandholesWithJct;
					clusterArray = markerClusterHandholesWithJct;
				}
				else if(srcDestID[x][1]=="DB"){
					markersArray = markersDB;
					clusterArray = markerClusterDB;
				}
				ID = srcDestID[x][0];
				if ($(this).is(':checked')){
					if(markersArray[ID].getMap()==null){
						clusterArray.removeMarker(markersArray[ID]);
						markersArray[ID].setMap(map);			
						clusterArray.addMarker(markersArray[ID]);
					}
				}
				else{
					markersArray[ID].setMap(null);
					clusterArray.removeMarker(markersArray[ID]);
					}
			}
		
		
	});		
	
}


function showHidePts(className){

	if(className=="showHideAllDbCheckbox") {
		clusterArray = markerClusterDB;
		markersArray = markersDB;
		distinctArray = distinctDB;
		clusterArray.clearMarkers();	
		
	}
	else if(className=="showHideAllJctCheckbox") {
		clusterArray = markerClusterJct;
		markersArray = markersJct;
		distinctArray = distinctJct;
		clusterArray.clearMarkers();	
		
	}
	else if(className=="showHideAllCustCheckbox") {
		clusterArray = markerClusterCustomers;
		markersArray = markersCustomer;
		distinctArray = distinctCustomers;
		clusterArray.clearMarkers();	
		
	}
	
	else if(className=="showHideAllHandholesWithJctCheckbox") {
		clusterArray = markerClusterHandholesWithJct;
		markersArray = markersHandholesWithJct;
		distinctArray = distinctHandholesWithJct;
		clusterArray.clearMarkers();
	}
	
	else if(className=="showHideAllManholesWithJctCheckbox") {
		clusterArray = markerClusterManholesWithJct;
		markersArray = markersManholesWithJct;
		distinctArray = distinctManholesWithJct;
		clusterArray.clearMarkers();	
		
	}
	else if(className=="showHideAllSitesCheckbox") {
		clusterArray = markerClusterSites;
		markersArray = markersSites;
		distinctArray = distinctSites;
		clusterArray.clearMarkers();	
		
	}
	
	$('.'+className).bind("change",function() {
			if ($(this).is(':checked')){
				for(var x=0;x<distinctArray.length;x++) {
					ID = distinctArray[x];
					if(markersArray[ID].getMap()==null){
						clusterArray.removeMarker(markersArray[ID]);
						markersArray[ID].setMap(map);			
						clusterArray.addMarker(markersArray[ID]);
					}
				}
			}
			
		});	
}

//will be when updating the affected query 
/*
function showElement(concatIDLongLat,rowIndex){

	var ID = concatIDLongLat.split(":")[0].trim();
	var longitude = concatIDLongLat.split(":")[1].trim();
	var latitude = concatIDLongLat.split(":")[2].trim();
	var Name = concatIDLongLat.split(":")[3].trim();


	 var latLng = new google.maps.LatLng(latitude,longitude);
	 map.setZoom(15);
	 map.panTo(latLng);
	 
		
		if(mapFlag=="0") { // Show on map is not clicked before (markers are not set on map)

			if(ID.includes("DB_")) {
				$('.showHideAllDbCheckbox').prop('checked', true);
				$(".showHideAllDbCheckbox").attr('disabled', false);
		        document.getElementById("dbCount").textContent = "";
		        if(!markersDB[ID]){
					distinctDB.push(ID); //  this array is used when checking all db from legend
					createMarker(ID,longitude,latitude,Name,'backboneDB.png',markersDB,markerClusterDB)
					
				}  
				document.getElementById("dbCount").textContent = "("+distinctDB.length+")";    				
			}
			else if(ID.includes("JCT_")) {
				$('.showHideAllJctCheckbox').prop('checked', true);
				$(".showHideAllJctCheckbox").attr('disabled', false);
		        document.getElementById("jctCount").textContent = "";   
		        if(!markersJct[ID]){
					distinctJct.push(ID); //  this array is used when checking all jct from legend
					createMarker(ID,longitude,latitude,Name,'junctionOrange.png',markersJct,markerClusterJct)
				}  
				document.getElementById("jctCount").textContent = "("+distinctJct.length+")";   
			}
			
		}// end mapFlag condition
		else {
			if(ID.includes("DB_")) {
				if(markersDB[ID]){
					if(markersDB[siteID].getMap()==null){
						markersDB[siteID].setMap(map);			
						markerClusterDB.addMarker(markersDB[siteID]);
					}
				}
			}
			else if(ID.includes("JCT_")) {

				if(markersJct[ID]){
					if(markersJct[ID].getMap()==null){
						markersJct[ID].setMap(map);			
						markerClusterJct.addMarker(markersJct[ID]);
					}
				}				
		   }
		}

		//Scroll to the map div
		document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
				
}*/

function showLocation(ID,rowIndex){		

	var longitude = filteredGridData[rowIndex]["locationLongitude"];
	var latitude = filteredGridData[rowIndex]["locationLatitude"];
	var Name = filteredGridData[rowIndex]["locationName"];
	var locationType = filteredGridData[rowIndex]["locationType"];

	 var latLng = new google.maps.LatLng(latitude,longitude);
	 map.setZoom(16);
	 map.panTo(latLng);
	 
		
		if(mapFlag=="0") { // Show on map is not clicked before (markers are not set on map)

			if(locationType =="Customer") {
				$('.showHideAllCustCheckbox').prop('checked', true);
				$(".showHideAllCustCheckbox").attr('disabled', false);
		        document.getElementById("custCount").textContent = "";
		        if(!markersCustomer[ID]){
		        	distinctCustomers.push(ID); //  this array is used when checking all cust from legend
					createMarker(ID,longitude,latitude,Name,'customerIcon.png',markersCustomer,markerClusterCustomers)
				}  
				document.getElementById("custCount").textContent = "("+distinctCustomers.length+")";    				
			}
			else if(locationType =="Site") {
				$('.showHideAllSitesCheckbox').prop('checked', true);
				$(".showHideAllSitesCheckbox").attr('disabled', false);
		        document.getElementById("sitesCount").textContent = "";
		        if(!markersSites[ID]){
		        	distinctSites.push(ID); //  this array is used when checking all sites from legend
					createMarker(ID,longitude,latitude,Name,'redSiteIcon.png',markersSites,markerClusterSites)
				}  
				document.getElementById("sitesCount").textContent = "("+distinctSites.length+")";    				
			}
		
			
			
			
		}// end mapFlag condition
		else {

			if(locationType =="Customer") {
		        if(markersCustomer[ID]){
		        	if(markersCustomer[ID].getMap()==null){
		        		markersCustomer[ID].setMap(map);			
		        		markerClusterCustomers.addMarker(markersCustomer[ID]);
					}
				}  
			}
			else if(locationType =="Site") {
		        if(markersSites[ID]){
		        	if(markersSites[ID].getMap()==null){
		        		markersSites[ID].setMap(map);			
		        		markerClusterSites.addMarker(markersSites[ID]);
					}
				}  
			}
				
		}

		//Scroll to the map div
		document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
			
}

function createBreakId(breakLong,breakLat){

	if (breakmarker) {
		breakmarker.setMap(null);
    }
	
	markerId="break_Marker";
	const pos = new google.maps.LatLng(breakLat,breakLong);



	markerIcon = {
		url:getContext()+"/resources/NetworkImages/BreakIcon.png", 
		scaledSize: new google.maps.Size(25, 25),
	};


	elementMarker = new google.maps.Marker({
				position: pos,
				ID:markerId,
				icon:markerIcon,
		        
		});
	elementMarker.metadata = { id: markerId };
	breakmarker = elementMarker;
	breakmarker.setMap(map);
	
}

function getCoords(){
	var coords=document.getElementById('mapLongLat');
	coords=coords.value.slice(1,-1).split(" || ", 2); //This to remove the first and last double quote characters and create array of size 2 based on the separator.
	coordsPrime=coords[0] + " " +coords[1];	
	return coordsPrime;
}

function getLongLatMouseMove(map){		  
	map.addListener("mousemove", (mapsMouseEvent) => {
	   $("#mapLongLat").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(13) +" || "
	    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(13), null, 2));	    
	});					 
}


</script>
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>
