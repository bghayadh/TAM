<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Duct Utilization Report</title>
     <link rel="shortcut icon" href="">
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
        <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/gridAppendRowsDuctUtilizationReport.js"></script>
		
		
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
        
        <!-- Including duct-utilization js dependencies  -->
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-init.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-autocomplete.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-events.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-export.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-ui.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-generate.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-grid.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-legend.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-map.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/Reports/duct-utilization/duct-utilization-show-on-map.js"></script>
        
        <!-- To Draw Borders on map for start/end coordinates -->        
        <script src="${pageContext.request.contextPath}/resources/js/Network/BordersFindNearest.js"></script>
        
</head>
<style>

.gap-2 {
    gap: 10px;
}

#ductPath {
    width: 100%;
}

/* prevents tiny vertical misalignment */
.input-group-text {
    display: flex;
    align-items: center;
}

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
}

.legendContainer{
height: 718px;
position: relative;
}

.box{
width: 100%;
height: 100%;            
position: absolute;
top: 10px;
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

.mapDropdown {
    margin-left: 10px;
    padding: 5px;
    font-size: 1.2em;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #4CAF50; 
    color: white;
    cursor: pointer;
}

.mapDropdown option {
    color: black;
}

</style>
<body>
  <c:set var="pg" value="report" scope="session"  />
  <jsp:include page="../header.jsp"></jsp:include>
	
	<div Style=" left: 0; bottom: 0;" id="DuctUtilizationReportDiv">
	<div class="container-fluid">     
	     <div class="row"><p></p></div>
		<div class="row second">	
			<div class="col-lg-2 col-md-4 col-sm-12" style="display: flex; ">
				<div class="form-group ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Duct Utilization Report</span>
					</div>
				</div>
			</div>
			<div class="col-lg-5 col-md-8 col-sm-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Duct Path</span>
							<input type="text" id="ductPath" class="form-control text-input" style="width:100%" />
					</div>
				</div>
			</div>		
							
			<div class="col-lg-5 col-md-12 col-sm-12" id="col3" style="text-align:right;">
		 		<div class="btn-group pull-right gap-2"  style="padding: 0px !important;">
		 			<button type="button" id ="showOnMap" class="btn mapButtons"  onclick="showOnMap()" >Show on Map</button>		 			
			 		<div class="dropdown"  style="height:30px;">
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
					<button type="button" id="generate" class="btn btn-primary BtnActive" > Generate Report </button>
				</div>
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

<!-- 
<div class="container-fluid" > 
-->
     
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
												 <th>
							                        <div class="tooltip-container">F/B or A/B
							                            <span class="tooltip-text">Front/Back or Side A/Side B</span>
							                        </div>
							                        <li class="filter-dropdown dropdown">
							                            <button class="almgrid-filter" data-toggle="dropdown"> 
							                                <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
							                            </button>
							                            <ul class="dropdown-menu filter-dropdown-ul"></ul>
							                        </li>
                    							</th>
                    							<th>Related Path
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
												<th>Status
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th>Equipment Type
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														 </button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th>Equipment ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														 </button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>													
												<th>Equipment Name
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


		
	<div style="display: Block;" id="totalStrandsNums">
		<div class="row" style="padding-left: 15px;">								
			<div class="col-lg-4 col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Number of Cables</span>
						<input readonly type="text" id="totalCables" class="form-control text-input" style="width:100%;"/>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Strands</span>
						<input readonly type="text" id="totalStrands" class="form-control text-input" style="width:100%;" />
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Geo Distance</span>
						<input readonly type="text" id="geoDistance" class="form-control text-input" style="width:100%;" />
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
         <div class="box stack-top" id="legendDiv" style="position: relative;bottom:50px;width: 290px; float:left; height:570px;  background:white; margin:37px;display: none">
         <div class="legendHeader"  id="legendHeader">
 			<h6 style="color:white;font-weight:bold; font-size:2.5ex;display:inline-block;position: relative;margin-left:20px;">Legend</h6>
  			 <select class="mapDropdown" id="mapDropdown">
        			<option value="gridBased">Based on grid</option>
        			<option value="cableBased">Based on cable</option>
    		</select>
  		
  		</div>
  
   <div id="tableDiv">
  	<table id="strandUtilizationTableReport">
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
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllManholesCheckbox" onclick="showHidePts('showHideAllManholesCheckbox');" value="red"/></td>
     <td style="position: relative;top:8px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/manholeRed.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Manhole</label></td>   
     <td style="position: relative;top:8px;"><div style="position: relative;left:-5px;color: black;" id="manholesCount" ></div></td>
    </tr>
    
    <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllManholesWithJctCheckbox" onclick="showHidePts('showHideAllManholesWithJctCheckbox');" value="red"/></td>
     <td style="position: relative;top:8px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/manholeJct.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Manhole with JCT</label></td>   
     <td style="position: relative;top:8px;left:65px;"><div style="position: relative;left:-5px;color: black;" id="manholesCountWithJct" ></div></td>
    </tr>
    
    <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideAllHandholesCheckbox" onclick="showHidePts('showHideAllHandholesCheckbox');" value="yellow"/></td>
     <td style="position: relative;top:8px;left:62px;"><div><img class='image' src='${pageContext.request.contextPath}/resources/NetworkImages/handholeYellow.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; ">Handhole</label></td>   
     <td style="position: relative;top:8px;"><div style="position: relative;left:-5px;color: black;" id="handholesCount" ></div></td>
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
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideSrcCheckbox" onclick="showHideSourceMarker();" value="pink"/></td>
     <td style="position: relative;top:8px;left:58px;"><div><img class='image' style="width: 25px; height: 30px;" src='${pageContext.request.contextPath}/resources/NetworkImages/SrcDest.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Source</label></td>   
    </tr>
     <tr>
     <td style="position: relative;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideDstCheckbox" onclick="showHideDestinationMarker();" value="pink"/></td>
     <td style="position: relative;top:8px;left:58px;"><div><img class='image' style="width: 25px; height: 30px;" src='${pageContext.request.contextPath}/resources/NetworkImages/SrcDest.png'></div></td>
     <td style="position: relative;top:8px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Destination</label></td>   
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
<!--         
    </div>
 -->    
    
    
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
var markersManholesWithJct =[];
var markersHandholes =[];
var markersHandholesWithJct =[];
var fiberCableArray=[];
var relatedPathArray=[];
var distinctDB =[]; // used in check/uncheck all db from legend
var distinctJct =[]; // used in check/uncheck all jct from legend
var distinctCustomers =[]; // used in check/uncheck all cust from legend
var distinctSites =[]; // used in check/uncheck all sites from legend
var distinctManholes =[]; 
var distinctManholesWithJct =[]; 
var distinctHandholesWithJct =[]; 
var distinctHandholes =[]; 
var allCables=[];
var allRelatedPathCables=[];
var markerClusterDB ;
var markerClusterJct ;
var markerClusterCustomers ;
var markerClusterSites ;
var markerClusterManholes ;
var markerClusterManholesWithJct;
var markerClusterHandholes ;
var markerClusterHandholesWithJct;
var showRelPathFlag="notOpened";
var mapFlag="0"; // used to check if the markers are set on map
var infoWindow;
var cableInfoWindow;
var MapMenu;
var filteredGridData=[]; // used in draw on map 
var cableID = "";
var srcDstCableList=[];
var jctElementsIDArray=[];
var jctElementsFlag="notOpened";
var elementsArray=[];
var generateFlag="0";
var manHandList=[];
var manHandoleList=[];
var srcID=[];
var dstID=[];

function getContext() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

</script>
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>
