<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Strand Utilization Report</title>
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

#showOnMap {
  color: orange;
  background-color: white;
  border-color: #939191f5;
  transition: border 0.3s; 
}

#showOnMap:hover{
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
}

.legendContainer{
height: 800px;
position: relative;
}

.box{
width: 100%;
height: 100%;            
position: absolute;
top: 20px;
left: 0;
}

.stack-top{
z-index: 3;
margin: 70px; 
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
</style>
<body>
  <c:set var="pg" value="report" scope="session"  />
  <jsp:include page="../header.jsp"></jsp:include>
	
	<div Style=" left: 0; bottom: 0;" id="StrandUtilizationReportDiv">
	<div class="container-fluid">     
	     <div class="row"><p></p></div>
			<div class="row second" >	
			<div class="col-md-2" style="display: flex; ">
				<div class="form-group "style="margin-left: 15px; ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Strand Utilization Report</span>
					</div>
				</div>
			</div>
			<div class="col-md-5">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Fiber Cable</span>
							<input type="text" id="fiberCable" class="form-control text-input" style="width:300px;" />
						</div>
					</div>
		</div>
		<div class="col-md-3">
			<button type="button"  id ="showOnMap"class="btn"  style=" margin-top:-0px;  margin-left:145px;"  >Show on Map</button>
		</div>
					
			<div class="col-md-2" id="col3" style="text-align:right;">
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
												<th>Strand #
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Tube #
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th>Element Type
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
													<th>Element ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>		
													<th>Front/Back
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
													<th>Port Index
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>		
												<th>Port Row
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>		
												<th>Port Column
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
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>											
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
         <div class="box stack-top" id="legendDiv" style="position: relative;top:235px;width: 280px; float:left; height:390px;  background:white; margin:37px;display: none">
         <div class="legendHeader"  id="legendHeader">
 			<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">MAP Legend</h6>
  		</div>
  
   <div id="tableDiv">
  	<table id="strandUtilizationTableReport">
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
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideCableCheckbox" onclick="showHideCable();" value="blue"/></td>
     <td style="position: relative;top:28px;left:62px;"><div><img class='image' style='color: #08526D;'  src='${pageContext.request.contextPath}/resources/NetworkImages/fiber.png'></div></td>
     <td style="position: relative;top: 28px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Fiber Cable</label></td>   
     <td style="position: relative;top: 28px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="fiberCount" ></div></td>
    </tr>
    
  	<tr>
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllManholesCheckbox" onclick="showHidePts('showHideAllManholesCheckbox');" value="red"/></td>
     <td style="position: relative;top:28px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/manholeRed.png'></div></td>
     <td style="position: relative;top: 28px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Manholes</label></td>   
     <td style="position: relative;top: 28px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="manholesCount" ></div></td>
    </tr>
    
    <tr>
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllHandholesCheckbox" onclick="showHidePts('showHideAllHandholesCheckbox');" value="yellow"/></td>
     <td style="position: relative;top:28px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/handholeYellow.png'></div></td>
     <td style="position: relative;top: 28px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Handholes</label></td>   
     <td style="position: relative;top: 28px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="handholesCount" ></div></td>
    </tr>
    
    <tr>
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllDbCheckbox" onclick="showHidePts('showHideAllDbCheckbox');" value="blue"/></td>
     <td style="position: relative;top:28px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/backboneDB.png'></div></td>
     <td style="position: relative;top: 28px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >DB</label></td>   
     <td style="position: relative;top: 28px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="dbCount" ></div></td>
    </tr>
     <tr>
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllJctCheckbox" onclick="showHidePts('showHideAllJctCheckbox');" value="orange"/></td>
     <td style="position: relative;top:28px;left:58px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/junctionOrange.png'></div></td>
     <td style="position: relative;top: 28px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Junction</label></td>   
     <td style="position: relative;top: 28px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="jctCount" ></div></td>
    </tr>
      <tr>
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllCustCheckbox" onclick="showHidePts('showHideAllCustCheckbox');" value="red"/></td>
     <td style="position: relative;top:28px;left:58px;"><div><img class='image' style="width: 30px; height: 30px;" src='${pageContext.request.contextPath}/resources/NetworkImages/customerIcon.png'></div></td>
     <td style="position: relative;top:32px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Customers</label></td>   
     <td style="position: relative;top: 28px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="custCount" ></div></td>
    </tr>
     <tr>
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllSitesCheckbox" onclick="showHidePts('showHideAllSitesCheckbox');" value="pink"/></td>
     <td style="position: relative;top:28px;left:58px;"><div><img class='image' style="width: 25px; height: 30px;" src='${pageContext.request.contextPath}/resources/NetworkImages/redSiteIcon.png'></div></td>
     <td style="position: relative;top: 28px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Sites</label></td>   
     <td style="position: relative;top: 28px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="sitesCount" ></div></td>
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
</body>

<script>

var map ;
var markersDB=[];	
var markersJct=[];
var markersCustomer =[];
var markersSites =[];
var markersManholes =[];
var markersHandholes =[];
var fiberCableArray=[];

var distinctDB =[]; // used in check/uncheck all db from legend
var distinctJct =[]; // used in check/uncheck all jct from legend
var distinctCustomers =[]; // used in check/uncheck all cust from legend
var distinctSites =[]; // used in check/uncheck all sites from legend
var distinctManholes =[]; 
var distinctHandholes =[]; 


var markerClusterDB ;
var markerClusterJct ;
var markerClusterCustomers ;
var markerClusterSites ;
var markerClusterManholes ;
var markerClusterHandholes ;


var mapFlag="0"; // used to check if the markers are set on map
var infoWindow;
var MapMenu;
var filteredGridData=[]; // used in draw on map 
var cableID = "";

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
	 
	 
	 infoWindow = new google.maps.InfoWindow(); // Define the info window to use it when clicking on marker

	 getLongLatMouseMove(map); // Add long/lat above the map	
	     
}//end initMap

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
        map.setZoom(6);        
     });

  }

$(document).ready(function() {
	
	//collapse active class	
	$('.panel-collapse').on('show.bs.collapse',function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});

	$('.panel-collapse').on('hide.bs.collapse',function() {
		$(this).siblings('.panel-heading').addClass('active');
	});
	


	 $("#fiberCable").autocomplete({
		source: function(request, response) {
			$.ajax({
				type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/getFiberCableDetails',
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
						fiberCable.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
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
					
 
		 
	  $("#generate").click(function() {


		//Disable and uncheck the checkboxes in legend
		$('.showHideAllDbCheckbox').prop('checked', false);
		$(".showHideAllDbCheckbox").attr('disabled', true);
		$('.showHideAllJctCheckbox').prop('checked', false);
		$(".showHideAllJctCheckbox").attr('disabled', true);
		$('.showHideAllCustCheckbox').prop('checked', false);
		$(".showHideAllCustCheckbox").attr('disabled', true);
		$('.showHideAllHandholesCheckbox').prop('checked', false);
		$(".showHideAllHandholesCheckbox").attr('disabled', true);
		$('.showHideAllManholesCheckbox').prop('checked', false);
		$(".showHideAllManholesCheckbox").attr('disabled', true);
		$('.showHideAllSitesCheckbox').prop('checked', false);
		$(".showHideAllSitesCheckbox").attr('disabled', true);
		$('.showHideCableCheckbox').prop('checked', false);
		$(".showHideCableCheckbox").attr('disabled', true);

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

		 markerClusterHandholes.clearMarkers();	
		 markersHandholes=[];	  
		 document.getElementById("handholesCount").textContent = "";

		 markerClusterManholes.clearMarkers();	
		 markersManholes=[];	  
		 document.getElementById("manholesCount").textContent = "";

		 markerClusterSites.clearMarkers();	
		 markersSites=[];	  
		 document.getElementById("sitesCount").textContent = "";

		 if(fiberCableArray.length>0) {
		 	fiberCableArray[cableID].setMap(null);
		 }
		 fiberCableArray=[];
		 cableID="";	  
		 
			
		 mapFlag="0";	
		  
		 //Recenter the map
		 var center=new google.maps.LatLng(1,38);
	     map.setCenter(center);
		 map.setZoom(6);  
		  
		$("#gridTable").remove();
		$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header fixed-header">'
				+'<th>Strand #<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Tube #<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Element Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Element ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Front/Back<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Port Index<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Port Row<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Port Column<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Longitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Latitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th><li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown" disabled style="display:none;"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th><li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown" disabled style="display:none;"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<tr><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" disabled class="almgrid-search" style="display:none"></th><th><input type="text" disabled class="almgrid-search" style="display:none"></th></tr></thead><tbody></tbody></table>');
			
			

		if($("#fiberCable").val() =="") {
			alert("Please enter a cable!");

		}
		else {
			$("#generateLoaderDiv").show();
		 cableID = $("#fiberCable").val().split(":")[0];
								
		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateStrandUtilizationReport",
			dataType : "json",
			data : {
			    "cableID" : cableID
			},
			success : function(data) {
			  if (data != null) {
				
               ReportArrayGlobal = data.listStrandsUtilization; 
                  if (ReportArrayGlobal.length == 0) { 
                	  alert("There is no data to display");
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
          			  		if (typeof markerClusterHandholes !== 'undefined' && markerClusterHandholes !== null) {
          			  			markerClusterHandholes.clearMarkers(); 
					   	 	 }  
          			  		if (typeof markerClusterManholes !== 'undefined' && markerClusterManholes !== null) {
          						markerClusterManholes.clearMarkers(); 
				   	  		}  	
          					if (typeof markerClusterSites !== 'undefined' && markerClusterSites !== null) {
          						markerClusterSites.clearMarkers(); 
		   	  				}  	

          		//Clear all arrays and inputs related to map when the data in grid is filtered
 		   		 	 distinctDB =[]; 
					 distinctJct =[]; 
					 distinctCustomers =[];
					 distinctSites =[]; 
					 distinctManholes =[]; 
					 distinctHandholes =[]; 
 		   		     markersSites=[];
    				 markersManholes=[];	  
     				 markersHandholes=[];	
     				 markersCustomer=[];	  
     				 markersJct=[];	  
     				 markersDB=[];	  
 				 	 mapFlag="0"; 	
 					 document.getElementById("sitesCount").textContent = "";
 					 document.getElementById("manholesCount").textContent = "";
 					 document.getElementById("handholesCount").textContent = "";
 					 document.getElementById("custCount").textContent = "";
 					 document.getElementById("jctCount").textContent = "";
 					 document.getElementById("dbCount").textContent = "";
 				 	 
 				 	$('.showHideAllDbCheckbox').prop('checked', false);
 					$(".showHideAllDbCheckbox").attr('disabled', true);
 					$('.showHideAllJctCheckbox').prop('checked', false);
 					$(".showHideAllJctCheckbox").attr('disabled', true);
 					$('.showHideAllCustCheckbox').prop('checked', false);
 					$(".showHideAllCustCheckbox").attr('disabled', true);
 					$('.showHideAllHandholesCheckbox').prop('checked', false);
 					$(".showHideAllHandholesCheckbox").attr('disabled', true);
 					$('.showHideAllManholesCheckbox').prop('checked', false);
 					$(".showHideAllManholesCheckbox").attr('disabled', true);
 					$('.showHideAllSitesCheckbox').prop('checked', false);
 					$(".showHideAllSitesCheckbox").attr('disabled', true);
 							 	  		 		
          			  var center=new google.maps.LatLng(1,38);
   				        map.setCenter(center);
   						map.setZoom(6); 

   						
         			     if (dataArray.length > 0) {
  					 
  			               var ArrayKeys = Object.keys(dataArray[0]);
  			       		   var columnVal;
  			       		   var data = [];//for export
  			       	       exportArrayGrid = [];
  			       		   data.push('\r');
  			       		   data.push(["Strand #","Tube #","Element Type","Element ID","Front/Back","Port Index","Port Row","Port Column","Location Type" ,"Location ID", "Location Name","Location Longitude", "Location Latitude"]);  	  			       		

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

                window["mapPoints_"+cableID]=[];               
                
				window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberList[0][1],data.fiberList[0][0]));


				
				for(i=0;i<data.fiberAuxData.length;i++){//PUSH AUXILIARY POINTS OF FIBER CABLE	
					window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberAuxData[i][1],data.fiberAuxData[i][0]));
				}

				window["mapPoints_"+cableID].push(new google.maps.LatLng(data.fiberList[0][3],data.fiberList[0][2]));
				
          		buildPath(cableID,window["mapPoints_"+cableID],"#08526D",0.7,4.5,'blue',13);
          		fiberCableArray[cableID].setMap(map);
          		$('.showHideCableCheckbox').prop('checked', true);
				$(".showHideCableCheckbox").attr('disabled', false);	
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


function buildPath(Id,path,strokeColor,strokeOpacity,strokeWeight,fontColor,IdNb){

			flightPath = new google.maps.Polyline({
				path: path,							
				geodesic: false,
				strokeColor: strokeColor,
				ID:Id,			
				strokeOpacity: strokeOpacity,
				strokeWeight: strokeWeight
			  });				 
				
			flightPath.metadata = { id: Id };
			fiberCableArray[Id] = flightPath;
			fiberCableArray.push(flightPath);	

}

		
		$('#gridExport').click(function(){
			  const csvContent = 'data:text/csv;charset=utf-8,' + encodeURIComponent(exportArrayGrid);
			  const downloadLink = document.createElement('a');
			  downloadLink.setAttribute('href', csvContent);
			  downloadLink.setAttribute('download', "StrandUtilizationGridReport");

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
	}
}     

function showHideCable() {

	$('.showHideCableCheckbox').bind("change",function() {					
			if ($(this).is(':checked')){
				fiberCableArray[cableID].setMap(map);
			}
			else {
				fiberCableArray[cableID].setMap(null);
			}
			
		});	
}
function showHidePts(className){

	if(className=="showHideAllDbCheckbox") {
		clusterArray = markerClusterDB;
		markersArray = markersDB;
		distinctArray = distinctDB;
	}
	else if(className=="showHideAllJctCheckbox") {
		clusterArray = markerClusterJct;
		markersArray = markersJct;
		distinctArray = distinctJct;
	}
	else if(className=="showHideAllCustCheckbox") {
		clusterArray = markerClusterCustomers;
		markersArray = markersCustomer;
		distinctArray = distinctCustomers;
	}
	else if(className=="showHideAllHandholesCheckbox") {
		clusterArray = markerClusterHandholes;
		markersArray = markersHandholes;
		distinctArray = distinctHandholes;
	}
	else if(className=="showHideAllManholesCheckbox") {
		clusterArray = markerClusterManholes;
		markersArray = markersManholes;
		distinctArray = distinctManholes;
	}
	else if(className=="showHideAllSitesCheckbox") {
		clusterArray = markerClusterSites;
		markersArray = markersSites;
		distinctArray = distinctSites;
	}
	
	$('.'+className).bind("change",function() {
		clusterArray.clearMarkers();	
					
			if ($(this).is(':checked')){
				for(var x=0;x<distinctArray.length;x++) {
					ID = distinctArray[x];
					if(markersArray[ID].getMap()==null){
						markersArray[ID].setMap(map);			
						clusterArray.addMarker(markersArray[ID]);
					}
				}
			}
			
		});	
}


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
					//createDbMarker(ID,longitude,latitude,Name);
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

		//Scroll to the map div
		document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
				
}

function showLocation(ID,rowIndex){		

	var longitude = filteredGridData[rowIndex]["longitude"];
	var latitude = filteredGridData[rowIndex]["latitude"];
	var Name = filteredGridData[rowIndex]["locationName"];
	var locationType = filteredGridData[rowIndex]["locationType"];

	 var latLng = new google.maps.LatLng(latitude,longitude);
	 map.setZoom(15);
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
			else if(locationType =="Manhole") {
				$('.showHideAllManholesCheckbox').prop('checked', true);
				$(".showHideAllManholesCheckbox").attr('disabled', false);
		        document.getElementById("manholesCount").textContent = "";
		        if(!markersManholes[ID]){
		        	if(Name.includes("_J")) {
						var imageURL = "manholeJct.png";
					}
			        else {
						var imageURL = "manholeRed.png";
					}
		        	distinctManholes.push(ID); //  this array is used when checking all manholes from legend
					createMarker(ID,longitude,latitude,Name,imageURL,markersManholes,markerClusterManholes)
				}  
				document.getElementById("manholesCount").textContent = "("+distinctManholes.length+")";    				
			}
			else if(locationType =="Handhole") {
				$('.showHideAllHandholesCheckbox').prop('checked', true);
				$(".showHideAllHandholesCheckbox").attr('disabled', false);
		        document.getElementById("handholesCount").textContent = "";
		        if(!markersHandholes[ID]){
			        if(Name.includes("_J")) {
						var imageURL = "handholeYellowJct.png";
					}
			        else {
						var imageURL = "handholeYellow.png";
					}
		        	distinctHandholes.push(ID); //  this array is used when checking all handholes from legend
					createMarker(ID,longitude,latitude,Name,imageURL,markersHandholes,markerClusterHandholes)
				}  
				document.getElementById("handholesCount").textContent = "("+distinctHandholes.length+")";    				
			}
			else if(locationType=="DB") {
				$('.showHideAllDbCheckbox').prop('checked', true);
				$(".showHideAllDbCheckbox").attr('disabled', false);
		        document.getElementById("dbCount").textContent = "";
		        if(!markersDB[ID]){
					distinctDB.push(ID); //  this array is used when checking all db from legend
					//createDbMarker(ID,longitude,latitude,Name);
					createMarker(ID,longitude,latitude,Name,'backboneDB.png',markersDB,markerClusterDB)
					
				}  
				document.getElementById("dbCount").textContent = "("+distinctDB.length+")";    				
			}
			
		}// end mapFlag condition

		//Scroll to the map div
		document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
			
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
