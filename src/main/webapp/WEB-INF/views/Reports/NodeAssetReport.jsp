<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Node Asset Report</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRowsFinancialReport.js"></script>
		
		
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
#Domain,#subDomain,#Vendor {
    z-index: 100; 
}


#Domain * ,#subDomain * ,#Vendor *{
    z-index: 100;     
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

.panToSite:hover{
    color: white;
	background-color: #007bff;
}


.panToSite {
background-color:white;
color: #007bff;
  border-color: #939191f5;

}

.clearButton{
background-color:white;
color:orange;
}

.clearButton:hover{
background-color:orange;
color:white;
}


.clearButton:not(:hover){
background-color:white;
color:orange;
}

#mapContainer {
height: 700px;
}
        
.legendHeader {
overflow: hidden;
background-color: #08526d;
padding: 10px 0px;
}

.blue {
background:blue;
}

.dot {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
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
</style>
<body>
  <c:set var="pg" value="report" scope="session"  />
  <jsp:include page="../header.jsp"></jsp:include>
	
	<div Style=" left: 0; bottom: 0;" id="NodeAssetReportDiv">
	<div class="container-fluid">     
	     <div class="row"><p></p></div>
			<div class="row second" >	
			<div class="col-md-2" style="display: flex; ">
				<div class="form-group "style="margin-left: 15px; ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Node Asset Report</span>
					</div>
				</div>
			</div>
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="startDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
					   <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div></div>
				</div></div></div>
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker4" data-target-input="nearest">
					<span class="input-group-text">End Date</span>
					<input type="text" id="endDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
					   <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
                </div>
			</div>
			 <div class="col-md-2">
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
      					<input type="checkbox" value="0" id="ignoreDate" name="ignoreDate">
      					<span style="margin-left: 10px;">Ignore Date</span>
				   </div>
                  </div>
			   </div>
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

<div class="container-fluid" >     
	  
		<div class="row" >
				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Item Code</span>
							<input type="text" id="itemCode" class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Item Name</span>
							<input type="text" id="itemName"  class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Item Model</span>
							<input type="text" id="itemModel" class="form-control text-input" />
						</div>
					</div>
				</div>

		<div class="col-md-3" style="float: right; padding-top:-100px;" >
			<div class="btn-group pull-right"  >
				<button type="button"  id ="showOnMap"class="btn"  style=" margin-top:-10px;  margin-right: 15px;"  >Show on Map</button>
		 		<button type="button"  id ="clearButton" class="btn btn-light clearButton" style=" margin-top:-10px; margin-right:-15px; "  >Clear</button>		 	
		 	</div>
		</div>
	</div>
		<div class="row">
					<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Item Part No</span>
							<input type="text" id="itemPartNo" class="form-control text-input" />
						</div>
					</div>
		</div>
		
		 <div class="col-md-3">
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="strtEndCoordinate" name="strtEndCoordinate">
      					<span style="margin-left: 10px;">Start/End Coordinate</span>
				   </div>
                  </div>
			   </div>
	     </div>	
	      <div class="col-md-3">
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="circleRange" name="circleRange">
      					<span style="margin-left: 10px;">Circle Range</span>
				   </div>
                  </div>
			   </div>
	     </div>
	     <div class="col-md-3">
			<div id="generateLoaderDiv" style="display: none;">
				<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
			</div>
		</div>
		</div>	
	<div class="row" id="row_Circle" style="display: none;">	     
			<div class="col-md-3">
			 <div class="form-group">
			   <div class="input-group-prepend" >
				<span  class="input-group-text"> Longitude</span>
				<input type="text" id="circleRangeLongitude" class="form-control text-input" />
			</div></div>
			</div>
			
			<div class="col-md-3">
			 <div class="form-group" >
			   <div class="input-group-prepend" >
					<span  class="input-group-text" >Latitude</span>
					<input type="text" id="circleRangeLatitude" class="form-control text-input" />
			</div></div>
		</div>		
		<div class="col-md-3" >
		  <div class="form-group">
		    <div class="input-group-prepend" >
				<span  class="input-group-text">Radius</span>
				<input type="text" id="circleRangeRadius" class="form-control text-input"/>
			</div></div>
		</div>
		</div>
  	
   <div class="row" id="row_setStartEnd" style="display: none;">
		<div class="col-md-3">
			 <div class="form-group">
			   <div class="input-group-prepend" >
				<span  class="input-group-text">Start Longitude</span>
				<input type="text" id="startLongitude" class="form-control text-input" />
			</div></div>
			</div>
			
		<div class="col-md-3">
			 <div class="form-group" >
			   <div class="input-group-prepend" >
					<span  class="input-group-text">Start Latitude</span>
					<input type="text" id="startLatitude" class="form-control text-input" />
			</div></div>
		</div>

		<div class="col-md-3">
		  <div class="form-group">
		    <div class="input-group-prepend" >
				<span  class="input-group-text">End Longitude</span>
				<input type="text" id="endLongitude" class="form-control text-input" />
			</div></div>
		</div>	
		<div class="col-md-3">
			 <div class="form-group" >
			   <div class="input-group-prepend" >
					<span  class="input-group-text">End Latitude</span>
					<input type="text" id="endLatitude" class="form-control text-input" />
			</div></div>
		</div>   
   
   </div>	
		
	<div class="row" >		
		<div class="col-md-3">
		   <div class="form-group">
			 <div class="input-group-prepend">
				<div  id="Domain"></div>
			</div>
	</div></div>		
		<div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
				<div id="subDomain"></div>
			</div>
			</div>
		</div>
		<div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<div id="Vendor"></div>
			</div>
			</div>
		</div>
		<div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
				<div id="Type"></div>
				</div>
			</div>
		</div>
		
  
	</div>
	

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
          <!-- /.card-header -->
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
												<th>
												<li class="filter-dropdown dropdown">
														<button disabled class="almgrid-filter" data-toggle="dropdown" style="display: none;">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>											
												<th>FAR ID 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Node ID 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Node Name 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th>Node Type 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>		

												<th>Item Code
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														 </button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												
												<th>Item Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Item Model
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Item Part Number
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
                                                   <th>Last Modified Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th> Item Serial Number
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>

												<th>Item Name Register
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Supplier
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>

											
												<th>PO ID 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
																					
												<th>Site ID 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												
												<th>Site Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Longitude
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												
												<th>Latitude 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Vendor 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Initial Cost
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Net Cost
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Accumulated Depreciation
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>											
											<tr>
												<th><input type="text" disabled class="almgrid-search" style="display:none"></th>
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
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												
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
		
		
 <div style="display: Block;" id="totalAllFAR">
	<div class="row second">	
					<div class="col-md-1">
						<div class="form-group">
            <span style=" display: inline-block;width:98px;color: white; font-size: 13px; white-space: nowrap; padding: 7px 8px; background-color: #008991; border-radius: 5px;"><b>All FAR  </b></span>
						</div>
					</div>								
			<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Initial Cost</span>
								<input type="text" id="initialCostAllFar" readonly value="${totalInitialCost}" class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Net Cost</span>
								<input type="text" id="netCostAllFar" readonly value="${totalNetCost}" class="form-control text-input" />
							</div>
						</div>
					</div>	
					<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Accumulated Depreciation</span>
								<input type="text" id="accuDeprAllFar" readonly value="${totalAccumdepr}" class="form-control text-input" />
							</div>
						</div>
					</div>
					
	</div>
		<br>
		
		<div class="row second">

					<div class="col-md-1">
						<div class="form-group">
          					  <span style="display: inline-block;width:98px;color: white; font-size: 13px; white-space: nowrap; padding: 7px 8px; background-color: #008991; border-radius: 5px;"><b>Fetched FAR </b></span>
						</div>
					</div>								
			<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Initial Cost</span>
								<input type="text" readonly id="initialCostFetchedFar" value="${totalInitialCostFetched}" class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Net Cost</span>
								<input type="text" readonly id="netCostFetchedFar"  value="${totalNetCostFetched}" class="form-control text-input" />
							</div>
						</div>
					</div>	
					<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Accumulated Depreciation</span>
								<input type="text" readonly id="accuDeprFetchedFar" value="${totalAccumdeprFetched}" class="form-control text-input" />
							</div>
						</div>
					</div>
					
	</div>
	<br>
		<div class="row second">
					<div class="col-md-1">
						<div class="form-group">
            				<span style="display: inline-block;width:98px;color: white; font-size: 13px; white-space: nowrap; padding: 7px 8px; background-color: #008991; border-radius: 5px;"><b>Filtered FAR </b></span>
						</div>
					</div>									
			<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Initial Cost</span>
								<input type="text" readonly id="initialCostFilteredFar" class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Net Cost</span>
								<input type="text" readonly id="netCostFilteredFar" class="form-control text-input" />
							</div>
						</div>
					</div>	
					<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Accumulated Depreciation</span>
								<input type="text" readonly  id="accuDeprFilteredFar" class="form-control text-input" />
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
         <div class="box stack-top" id="legendDiv" style="position: relative;top:235px;width: 280px; float:left; height:170px;  background:white; margin:37px;display: none">
         <div class="legendHeader"  id="legendHeader">
 			<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">MAP Legend</h6>
  		</div>
  
   <div id="tableDiv">
  	<table id="farFinancialReport">
   		<caption style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-50px;left:20px;">FAR Sites</caption>  
 <br>
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>  
  </tr>
  
  <tr></tr>
    <tr>
     <td style="position: relative;top:17px;left:37px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" disabled class="showHideSitesCheckbox" onclick="showHideAllSites();" value="blue"/></td>
     <td style="position: relative;top:30px;left:58px;"><div class="dot blue"></div></td>
     <td style="position: relative;top: 28px;left:65px;"><label style="color:black;font-weight:bold;font-size:2ex; " >All Sites</label></td>   
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
  	<div class="container">
		<div id="showClosePointsPopup" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 900px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Close Points Search</h5>
						<div style="float:right;">
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
							<i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="container-fluid" style="min-width: 50%;">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 50%;" class="input-group-text"><b>Longitude</b>
												</span>
												 <input type="text" id="closePtsLong" style="width: 50%;" class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 50%;" class="input-group-text"><b>Latitude</b>
												</span>
												 <input type="text" id="closePtsLat" style="width: 50%;" class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 50%;" class="input-group-text"><b>Distance Range</b>
												</span> <input type="text" id="closePtsDistanceRange" style="width: 50%;"  class="form-control text-input" />
											</div>
										</div>
									</div> 
									<div class="col-md-3">
										<div class="form-group">
											<div class="input-group-prepend">
												<button id="searchClosePoints" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px;width:100%">Show Close Points</button>
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<div class="input-group-prepend">
												<button id="captureNewCoordinate" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px;width:100%">Capture New Coordinate</button>
											</div></div></div></div>					
						<div class="tab-content" style="min-height: 200px">
						<div class="tab-pane active" id="closePtsSites" role="tabpanel" >
							<p></p>
								<div class="container-fluid">
									<div id="findCloseSitePts"></div>
												<div class="row" style="height: 100px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findCloseSite" style="display: block; height: 150px; overflow-y: auto;" class="searchable sortable">
														 <thead>
															<tr>
																<th style="min-width: 150px;" >Site ID</th>
																<th style="min-width: 150px;">Site Name</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 150px;">Latitude</th>
																<th style="min-width: 100px;">Distance(km)</th>
																<th style="min-width: 80px;"colspan="2">Driving Distance(km)</th>
															</tr>
															 </thead>
															<tbody id="searchCloseSiteTBody"></tbody>
														</table>
													</fieldset></div></div>
									        <div id="closeSiteTotal" Style="Padding-top: 90px;"><b>Total Sites:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalCloseSite" name="totalCloseSite" readonly>	 </div>
										</div></div></div></div>
						<div class="modal-footer"></div>
					</div></div></div></div></div> 	



<menu class="menu" id="mapMenu">
		<li class="menu-item mapMenuItem">
			<button type="button" id="showClosePoints" class="menu-btn"> <i class="fa fa-paste"></i> <span class="menu-text">Show Close Points</span></button>
		</li>
	</menu>
</div>

	

 
</body>

<script>

var map ;
markersFarSites=[];		
var distinctSites =[]; // used in check/uncheck all sites from legend
var mapFlag="0"; // used to check if the markers are set on map
var filteredSitesGrid=[]; // used in draw on map 
var markerClusterFarSites ;
var infoWindow;
var MapMenu;
var showCloseDistinctSites =[]; // used in show close point to remove duplicate sites

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
	 markerClusterFarSites = new MarkerClusterer();
	 markerClusterFarSites.setMap(map);

	 markerClusterFarSites.setOptions( {					  					
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

	 infoWindow = new google.maps.InfoWindow(); // Define the info window to use it when clicking on marker

	 getLongLatMouseMove(map); // Add long/lat above the map

	
	   
	   //This is the map menu that will open when right click on the google map
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
  
var domainOptions = [
	  { label: 'Transmission', value: 'Transmission' },
	  { label: "RAN", value: 'RAN'},
	  { label: "Core", value: 'Core'},
	  { label: "Enterprise", value: 'Enterprise'},
	  
	 
];
VirtualSelect.init({
	  ele: '#Domain',
	  options: domainOptions,
	  multiple: true,
	  placeholder: 'Domain'
	});

var subDomainOptions = [
	  { label: "IP", value: 'IP' },
	  { label: "Fiber Optic", value: 'FiberOptic'},
	  { label: "Microwave Link", value: 'MicrowaveLink'},	  
	 
];
VirtualSelect.init({
	  ele: '#subDomain',
	  options: subDomainOptions,
	  multiple: true,
	  placeholder: 'Sub Domain'
	});

var vendorOptions = [
	  { label: "Nokia", value: 'Nokia' },
	  { label: "Huawei", value: 'Huawei'},
	  { label: "ZTE", value: 'ZTE'},	
	  { label: "Ericsson", value: 'Ericsson'},	  
	  { label: "Tejas", value: 'Tejas'},	  
	  { label: "Alcatel", value: 'Alcatel'},	  
	  { label: "NEC", value: 'NEC'},	  	 
];
VirtualSelect.init({
	  ele: '#Vendor',
	  options: vendorOptions,
	  multiple: true,
	  placeholder: 'Vendor'
	});

var typeOptions = [
	  { label: "DWDM", value: 'DWDM' },
	  { label: "SDH", value: 'SDH'},	  	 
];
VirtualSelect.init({
	  ele: '#Type',
	  options: typeOptions,
	  multiple: true,
	  placeholder: 'Type'
	});


var date = new Date();
date.setDate(date.getDate() );
date.setFullYear(date.getFullYear() - 1); // Set the date one year before
date.setHours( 0,0,0,0 );

var dateEnd = new Date();
dateEnd.setDate(dateEnd.getDate());

var strtDate = Date.parse(date);

$('#startDate').datetimepicker({
	defaultDate:date
});

$('#endDate').datetimepicker({
	defaultDate:dateEnd
});



$(document).ready(function() {

    var ReportArrayGlobal = ${nodeAssetReportList};
    var exportArrayGrid = []; // for export 
    
   
    
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
	        if (typeof markerClusterFarSites !== 'undefined' && markerClusterFarSites !== null) {
	       		markerClusterFarSites.clearMarkers();
				var center=new google.maps.LatLng(1,38);
		        map.setCenter(center);
				map.setZoom(6);  
			}
	       
	       
	         //Clear all arrays and inputs related to map when the data in grid is filtered
	   		 	distinctSites =[];
	   			markersFarSites=[];
			 	mapFlag="0";
	   		 	$('.showHideSitesCheckbox').prop('checked', false);
				$(".showHideSitesCheckbox").attr('disabled', true);
		         document.getElementById("sitesCount").textContent = "";      

		        //Clear the table and all inputs in show close points popup when the data in grid is filtered
				 $("#searchCloseSiteTBody").empty();
				 $("#searchCloseSiteTBody").html("");
				 $("#closePtsDistanceRange").val("");
				 $("#totalCloseSite").val("");
				 document.getElementById("findCloseSitePts").innerHTML = "";
	    
	        
	        if (dataArray.length > 0) {
		        var initCost=0,netCost=0,accuDepr=0;
        		var ArrayKeys = Object.keys(dataArray[0]);
        		var columnVal;

   	    		//for export
	     		var data = [];
	     	    exportArrayGrid = [];
	    		data.push('\r');
	       		data.push(["FAR ID", "Node ID","Node Name","Node Type","Item Code", "Item Name", "Item Model","Item Part Number","Last Modified Date","Item Serial Number","Item Name Register","Supplier","PO ID","Site ID","Site Name","Longitude","Latitude","Vendor","Initial Cost","Net Cost","Accumulated Depreciation"]);

	       		filteredSitesGrid = dataArray; // used in draw on map 
	       		
        for (var i = 0; i < dataArray.length; i++) {
         
  			data.push('\r');
  			
           for (var j = 0; j < ArrayKeys.length; j++) {
           	
        	   columnVal = ArrayKeys[j];
        	   
        	   if(columnVal !="site") {
              	   data.push(dataArray[i][ArrayKeys[j]]);// for export 
            	}
           		
            
          	  if(columnVal =="initCost")  initCost+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
              if(columnVal =="netCost")   netCost+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
              if(columnVal =="accuDepr")  accuDepr+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
		                            
  		   }
  		}
      
		
           initCost = initCost.toFixed(2);
           netCost = netCost.toFixed(2);
           accuDepr = accuDepr.toFixed(2);
           
           $("#initialCostFilteredFar").val(initCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
           $("#netCostFilteredFar").val(netCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
           $("#accuDeprFilteredFar").val(accuDepr.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    
           exportArrayGrid.push(data);
  	   }
  		else{
  		    $("#initialCostFilteredFar").val('0.0');
            $("#netCostFilteredFar").val('0.0');
            $("#accuDeprFilteredFar").val('0.0');		 
            filteredSitesGrid=[];			  		
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

  	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

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


	//collapse active class	
	$('.panel-collapse').on('show.bs.collapse',function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});

	$('.panel-collapse').on('hide.bs.collapse',function() {
		$(this).siblings('.panel-heading').addClass('active');
	});
	
    $('#showOnMap'). click(function(){  
		 distinctSites =[];
		 markerClusterFarSites.clearMarkers();
		 mapFlag="1";
		
		for (var i = 0; i < filteredSitesGrid.length; i++) {			
			if(distinctSites.includes(filteredSitesGrid[i]["siteID"])==false) {
				distinctSites.push(filteredSitesGrid[i]["siteID"]);
				if(!markersFarSites[filteredSitesGrid[i]["siteID"]]){
					createSiteMarker(filteredSitesGrid[i]["siteID"],filteredSitesGrid[i]["longitude"],filteredSitesGrid[i]["latitude"],filteredSitesGrid[i]["siteName"]);
				}
				else {					
					markersFarSites[filteredSitesGrid[i]["siteID"]].setMap(map);
					markerClusterFarSites.addMarker(markersFarSites[""+filteredSitesGrid[i]["siteID"]]);

				}
			}
        } 
        if(distinctSites.length >0) {
			$('.showHideSitesCheckbox').prop('checked', true);
			$(".showHideSitesCheckbox").attr('disabled', false);
        }   
        else {
        	$('.showHideSitesCheckbox').prop('checked', false);
			$(".showHideSitesCheckbox").attr('disabled', true);
         } 
            
         document.getElementById("sitesCount").textContent = "("+distinctSites.length+")";      
		 map.setZoom(6); 
		 
		//Scroll to the map div
		 document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
		 
	         	
	});
			 
	$('#clearButton'). click(function(){  
        document.getElementById('startLongitude').value = '';
        document.getElementById('startLatitude').value = '';
        document.getElementById('endLatitude').value = '';
        document.getElementById('endLongitude').value = '';
        document.getElementById('circleRangeLongitude').value = '';
        document.getElementById('circleRangeLatitude').value = '';
        document.getElementById('circleRangeRadius').value = '';
        document.getElementById('itemCode').value = '';
        document.getElementById('itemName').value = '';
        document.getElementById('itemModel').value = '';
        document.getElementById('itemPartNo').value = '';
        $("#strtEndCoordinate").prop("checked", false); 
		$("#circleRange").prop("checked", false);
		$("#row_setStartEnd").hide();
		$("#row_Circle").hide();
        $("#ignoreDate").prop("checked", false); 
		document.querySelector('#Domain').reset();
		document.querySelector('#subDomain').reset();
		document.querySelector('#Vendor').reset();
		document.querySelector('#Type').reset();	
	}); // end clear fct


	 // Get the form fields and hidden div
    var circleRangeCheckbox = $("#circleRange");
    var rectangleRangeCheckbox = $("#strtEndCoordinate");
    var hiddenRowRect = $("#row_setStartEnd");    
    var hiddenRowCircle = $("#row_Circle");

    hiddenRowRect.hide();
    hiddenRowCircle.hide();

    rectangleRangeCheckbox.change(function() {
	    if (rectangleRangeCheckbox.is(':checked')) {
	    	hiddenRowRect.show();
	    	hiddenRowCircle.hide();
			$("#circleRange").prop("checked", false);	    	
	    	
	    } else {
	    	hiddenRowRect.hide();	    	
	      
	    }
	  });
    circleRangeCheckbox.change(function() {
	    if (circleRangeCheckbox.is(':checked')) {
	    	hiddenRowCircle.show();
	     	 hiddenRowRect.hide();
	         $("#strtEndCoordinate").prop("checked", false); 
		      
	    } else {
	    	hiddenRowCircle.hide();	    	
	      
	    }
	  });	  

 
		 
	  $("#generate").click(function() {

		  var startDate = document.getElementById("startDate").value;		  
		  var endDate = document.getElementById("endDate").value;
		  var ignoreDateCheckbox = document.getElementById("ignoreDate").checked;
		  var strtEndCheckbox = document.getElementById("strtEndCoordinate").checked;
		  var circleRangeCheckbox = document.getElementById("circleRange").checked;

		 //Disable and uncheck the checkbox in legend
		 $('.showHideSitesCheckbox').prop('checked', false);
		 $(".showHideSitesCheckbox").attr('disabled', true);

		 // Clear the map and array related to map
		 markerClusterFarSites.clearMarkers();	
		 markersFarSites=[];	  
		 mapFlag="0";
		 document.getElementById("sitesCount").textContent = "";
		 
		//Clear the map from borders in case of start/end coordinates
		 for (var i = 0; i < polylines.length; i++) {
		        polylines[i].setMap(null);
		  }
		 polylines = [];

		 
		 //Clear the table and all inputs in show close points popup
		 $("#searchCloseSiteTBody").empty();
		 $("#searchCloseSiteTBody").html("");
		 $("#closePtsDistanceRange").val("");
		 $("#totalCloseSite").val("");
		 document.getElementById("findCloseSitePts").innerHTML = "";
		 

		 //Recenter the map
		 var center=new google.maps.LatLng(1,38);
	     map.setCenter(center);
		 map.setZoom(6);  
		       
		$("#generateLoaderDiv").show(); // Show the loading data div
		  
		  
		$("#gridTable").remove();
		$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table">'
				+'<thead><tr class="header fixed-header"><th><li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown" disabled style="display:none;"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>FAR ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> '
				+'<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
					+'<th>Node ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th> Node Name <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th> Node Type <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th>Item Code<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th>Item Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th>Item Model<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th>Item Part Number<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th>Last Modified Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th>Item Serial Number<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th>Item Name Register<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th>Supplier<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th> PO ID <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th>Site ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">	</ul></li></th>'
					+'<th>Site Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'						
					+'<th>Longitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Latitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Vendor<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Initial Cost<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Net Cost<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Accumulated Depreciation<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					
					+'<tr>'
					+'<th><input type="text" disabled class="almgrid-search" style="display:none"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
					
					+'</tr></thead><tbody></tbody></table>');	  

	     
	     var selectedOptions ="";	        
		 var arrSelected = [];

	     selectedOptions= $("#Domain").val();
		  for (var i = 0; i < selectedOptions.length; i++) {
			  arrSelected.push(selectedOptions[i]);
		}
		var domainSelected = arrSelected.toString();
		arrSelected=[]; // clear the array to use it for next dropdown values

		selectedOptions= $("#subDomain").val();
		  for (var i = 0; i < selectedOptions.length; i++) {
			  arrSelected.push(selectedOptions[i]);
		}
		var subDomainSelected = arrSelected.toString();
		arrSelected=[]; // clear the array to use it for next dropdown values

		selectedOptions= $("#Vendor").val();
		  for (var i = 0; i < selectedOptions.length; i++) {
			  arrSelected.push(selectedOptions[i]);
		}
		var vendorSelected = arrSelected.toString();
		arrSelected=[]; // clear the array to use it for next dropdown values
		
		selectedOptions= $("#Type").val();
		  for (var i = 0; i < selectedOptions.length; i++) {
			  arrSelected.push(selectedOptions[i]);
		}
		var typeSelected = arrSelected.toString();
		arrSelected=[];


		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateGridNodeAssetReport",
			dataType : "json",
			data : {
			    "startDate" : $("#startDate").val(),
			    "endDate" : $("#endDate").val(),
			    "ignoreDate":ignoreDateCheckbox,
			    "itemCode":$("#itemCode").val(),
                "itemName": $("#itemName").val(),
                "itemModel":$("#itemModel").val(),
                "itemPartNo":$("#itemPartNo").val(),
			    "subDomain" :subDomainSelected ,
				"Domain":domainSelected,
				"Vendor": vendorSelected,
				"Type": typeSelected,
				"startLong":$("#startLongitude").val(),
				"startLat":$("#startLatitude").val(),
				"endLong":$("#endLongitude").val(),
				"endLat":$("#endLatitude").val(),
				"longitude":$("#circleRangeLongitude").val(),
				"latitude":$("#circleRangeLatitude").val(),
				"radius":$("#circleRangeRadius").val(),
				"strtEndCheckbox":strtEndCheckbox,
				"circleRangeCheckbox":circleRangeCheckbox	
				
			},
			success : function(data) {
			  if (data != null) {
				  if(strtEndCheckbox==true) {
					  bordersFindNearest($("#startLongitude").val(),$("#startLatitude").val(),$("#endLongitude").val(),$("#endLatitude").val())
				  }
				  else if (circleRangeCheckbox == true) {
				        const myLatLng = {
				            lat: parseFloat($("#circleRangeLatitude").val()),
				            lng: parseFloat($("#circleRangeLongitude").val())
				        };

				        new google.maps.Marker({
				            position: myLatLng,
				            map,
				            title: "Marked",
				        });

				        var circleRadius = $("#circleRangeRadius").val();

				        // Drawing find nearest circle
				        var circ = new google.maps.Circle({
				            strokeColor: "blue",
				            strokeOpacity: 0.8,
				            strokeWeight: 2,
				            fillColor: "blue",
				            fillOpacity: 0.1,
				            map,
				            center: myLatLng,
				            radius: circleRadius * 1000,
				            clickable: false
				        });

				        $("#SearchZone").click(function () {
				            if ($(this).is(":checked")) {
				                circ.setOptions({ fillOpacity: 0.35, strokeOpacity: 0.3 });
				            } else {
				                circ.setOptions({ fillOpacity: 0, strokeOpacity: 0 }); // ✅ fixed here
				            }
				        });

				        map.setCenter(myLatLng);
				        map.fitBounds(circ.getBounds());
				    }
                  ReportArrayGlobal = data.nodeAssetReportList; 
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
         			       if (typeof markerClusterFarSites !== 'undefined' && markerClusterFarSites !== null) {
         			       		markerClusterFarSites.clearMarkers(); 
         						var center=new google.maps.LatLng(1,38);
         				        map.setCenter(center);
         						map.setZoom(6);  
         					}
         			       

         			       //Clear all arrays and inputs related to map when the data in grid is filtered
         		   		 	distinctSites =[];
         		   			markersFarSites=[];
         				 	mapFlag="0";
         		   		 	$('.showHideSitesCheckbox').prop('checked', false);
         					$(".showHideSitesCheckbox").attr('disabled', true);
         			         document.getElementById("sitesCount").textContent = "";      

         			        //Clear the table and all inputs in show close points popup when the data in grid is filtered
         					 $("#searchCloseSiteTBody").empty();
         					 $("#searchCloseSiteTBody").html("");
         					 $("#closePtsDistanceRange").val("");
         					 $("#totalCloseSite").val("");
         					 document.getElementById("findCloseSitePts").innerHTML = "";


         					 
         			     if (dataArray.length > 0) {
  					 
  	                       var initCost=0,netCost=0,accuDepr=0;
  			               var ArrayKeys = Object.keys(dataArray[0]);
  			       		   var columnVal;
  			       		  
  			       		   //for export
  			       		    var data = [];
  			       	       exportArrayGrid = [];
  			       		   data.push('\r');
  			       		   data.push(["FAR ID","Node ID","Node Name","Node Type" ,"Item Code", "Item Name","Item Model","Item Part Number", "Last Modified Date","Item Serial Number","Item Name Register","Supplier","PO ID","Site ID","Site Name","Longitude","Latitude","Vendor","Initial Cost","Net Cost","Accumulated Depreciation"]);

  	  				       filteredSitesGrid = dataArray; // used in draw on map 
  	  			       		
  			       		
  			           for (var i = 0; i < dataArray.length; i++) {
		          				data.push('\r');

  			               for (var j = 0; j < ArrayKeys.length; j++) {
  			               	
  			            	 columnVal = ArrayKeys[j];

	  			            	if(columnVal !="site") { 
	  	  			            	data.push(dataArray[i][ArrayKeys[j]]);
	  			            	}

  			                       if(columnVal =="initCost")  initCost+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
  			                       if(columnVal =="netCost")   netCost+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
  			                       if(columnVal =="accuDepr")  accuDepr+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
  							   
  		          			   }
  		          		   }

  			                // for export
		                   exportArrayGrid.push(data);
  			          					
		                   initCost = initCost.toFixed(2);
		                   netCost = netCost.toFixed(2);
		                   accuDepr = accuDepr.toFixed(2);
		                   
		                  			 $("#initialCostFilteredFar").val(initCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")); // .toLocaleString() is used to add comma to the number
	                         		 $("#netCostFilteredFar").val(netCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	                          		 $("#accuDeprFilteredFar").val(accuDepr.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
  		          	        }
  		          			else{

  		          			      $("#initialCostFilteredFar").val('0.0');
  		                          $("#netCostFilteredFar").val('0.0');
  		                          $("#accuDeprFilteredFar").val('0.0');	
  		        	       		filteredSitesGrid = []; // used in draw on map 
		                          	 

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

                     // Set the total of all FAR records
                     $("#initialCostAllFar").val(data.totalInitialCost);
                     $("#netCostAllFar").val(data.totalNetCost);
                     $("#accuDeprAllFar").val(data.totalAccumdepr);

                     // Set the total of fetched FAR records
                     $("#initialCostFetchedFar").val(data.totalInitialCostFetched);
                     $("#netCostFetchedFar").val(data.totalNetCostFetched);
                     $("#accuDeprFetchedFar").val(data.totalAccumdeprFetched);
                         
			}
				$("#generateLoaderDiv").hide();
				
  },
  
  error : function(error) {
		console.log("The error is " + error);
		$("#generateLoaderDiv").hide();
		
	}
});
			  
		  
	  });	  


		$('#gridExport').click(function(){
			  const csvContent = 'data:text/csv;charset=utf-8,' + encodeURIComponent(exportArrayGrid);
			  const downloadLink = document.createElement('a');
			  downloadLink.setAttribute('href', csvContent);
			  downloadLink.setAttribute('download', "ItemAssetGridReport");

			  document.body.appendChild(downloadLink);
			  downloadLink.click();
			  document.body.removeChild(downloadLink);
      	});

		//To hide the right click menu when clicking on the page
		$("#NodeAssetReportDiv").on('click', function(){
			HideContextMenuGoolge(MenuMap);
		});
		
 
		$("#showClosePoints").on('click',function(){
			
			//Get long and lat
			window["showClosePointsLong"]=getCoords().split(" ")[1];
			window["showClosePointsLat"]=getCoords().split(" ")[0];	

			// Check if tbody is empty to set the new coordinates
			var tbody = document.getElementById("searchCloseSiteTBody");
			if (tbody.childElementCount == 0) {
				$("#closePtsLong").val(window["showClosePointsLong"]);
				$("#closePtsLat").val(window["showClosePointsLat"]);
			} 
			
			$("#showClosePointsPopup").modal('show');
		 });	

		$("#captureNewCoordinate").on('click',function(){
			$("#searchCloseSiteTBody").empty();
			$("#searchCloseSiteTBody").html("");
			$("#closePtsDistanceRange").val("");
			$("#totalCloseSite").val("");
			document.getElementById("findCloseSitePts").innerHTML = "";			
			$("#closePtsLong").val(window["showClosePointsLong"]);
			$("#closePtsLat").val(window["showClosePointsLat"]);

	   });	

		 $("#searchClosePoints").on('click',function(){
				
			var closePtLong = $("#closePtsLong").val();
			var closePtLat = $("#closePtsLat").val();
			var closePtDistRange = $("#closePtsDistanceRange").val();
				
			if(closePtDistRange =="" || closePtLong =="" || closePtLat == "" ) {
				alert ("Some input fields are missing!")
			}
			else if (isNaN(closePtLong) ==true || isNaN(closePtLat) ==true || isNaN(closePtDistRange) ==true ) {
				alert ("Incorrect Format ! Longitude, Latitude and Distance Range should be numbers.")
			}
			else {
				
				var pointDist=0;
				var closePointsDataTemp =[];
				showCloseDistinctSites =[];
				closePointsData=[];
				
				//Loop through all sites to get the distance and filter the closest sites
				for(var x=0;x<filteredSitesGrid.length;x++) {
					
					// Check if site already appended to avoid the duplication ( because filteredSitesGrid contains repeated sites)
					if(showCloseDistinctSites.includes(filteredSitesGrid[x]["siteID"])==false) {
					
						showCloseDistinctSites.push(filteredSitesGrid[x]["siteID"]);
						pointDist =Number(haversineDistance(closePtLat,closePtLong,filteredSitesGrid[x]["latitude"],filteredSitesGrid[x]["longitude"]));

					
					if (pointDist < closePtDistRange) {
						
					    closePointsDataTemp = filteredSitesGrid[x];//Add all site details to the temp array
						closePointsDataTemp.distance = (pointDist);// Append the calculated distance to the array 
						closePointsData.push(closePointsDataTemp);// Add the temp array to the main array that will contains all closest sites to append after
						closePointsDataTemp =[];//Clear the temp array to use it on next iteration
					}
				  }
				}
						appendSitesClosePoints(closePointsData);
						$("#totalCloseSite").val(closePointsData.length);
						closePointsData=[]; // clear the array used to append the rows in popup
						closePointsDataTemp =[];				
					}

			});	
				    
});



function createSiteMarker(siteID,longitude,latitude,siteName) {

	markerId=siteID;		
	const pos = new google.maps.LatLng(latitude,longitude);
	 iconSite ={
			path: google.maps.SymbolPath.CIRCLE,
	        fillOpacity: 0.9,
	        strokeColor: 'transparent',
	        strokeOpacity: 0.9,
	        strokeWeight: 1,
	        scale: 9,
	        fillColor:'blue'
	   };

		var siteIdInfo ="<b style='font-size:13px;'><u>Site ID: </u></b>"+siteID;
		var siteNameInfo ="<b style='font-size:13px;'><u>Site Name: </u></b>"+siteName;
		var data="<div></br>"+siteIdInfo+"</br>"+siteNameInfo+"</div>";			
		   
	
	if(!markersFarSites[markerId]){
		siteMarker = new google.maps.Marker({
			position: pos,
			ID:markerId,
			icon:iconSite,
	        data:data,
	});
		siteMarker.metadata = { id: markerId };
		markersFarSites[markerId] = siteMarker;
		markersFarSites.push(siteMarker);
		markerClusterFarSites.addMarker(markersFarSites[""+markerId]);
		markersFarSites[markerId].setMap(map);

		google.maps.event.addListener(siteMarker, "click", function (e) {
            infoWindow.close();
	     	infoWindow.setContent(this.data); 
        	infoWindow.open(map,this);				
	 	});
			
   }
	else{
		if(markersFarSites[markerId].map!=map){
			markersFarSites[markerId].setMap(map);
			markerClusterFarSites.addMarker(markersFarSites[""+markerId]);
		}				
		markersFarSites[markerId].setPosition(pos);
	}
	 if(mapFlag=="1"){
		infoWindow.close();
	}
		
}     		
		

function showHideAllSites(){
	$('.showHideSitesCheckbox').bind("change",function() {
		markerClusterFarSites.clearMarkers();	
					
			if ($(this).is(':checked')){
				for(var x=0;x<distinctSites.length;x++) {
					siteID = distinctSites[x];
					if(markersFarSites[siteID].getMap()==null){
						markersFarSites[siteID].setMap(map);			
						markerClusterFarSites.addMarker(markersFarSites[siteID]);
					}
				}
			}
			
		});	
}
function panToSite(ID,rowIndex){

	var longitude = filteredSitesGrid[rowIndex]["longitude"];
	var latitude = filteredSitesGrid[rowIndex]["latitude"];
	var siteName = filteredSitesGrid[rowIndex]["siteName"];

	 var latLng = new google.maps.LatLng(latitude,longitude);
	 map.setZoom(15);
	 map.panTo(latLng);
	 
		
		if(mapFlag=="0") { // Draw on map is not clicked before (markers are not set on map)
			$('.showHideSitesCheckbox').prop('checked', true);
			$(".showHideSitesCheckbox").attr('disabled', false);
	         document.getElementById("sitesCount").textContent = "";      

			if(!markersFarSites[ID]){
				distinctSites.push(ID); //  this array is used when checking all sites from legend
				createSiteMarker(ID,longitude,latitude,siteName);
			}

		}// end mapFlag condition
		 document.getElementById("sitesCount").textContent = "("+distinctSites.length+")";      

		//Scroll to the map div
		document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
				
}

function getLongLatMouseMove(map){		  
	map.addListener("mousemove", (mapsMouseEvent) => {
	   $("#mapLongLat").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(13) +" || "
	    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(13), null, 2));	    
	});					 
}

function getCoords(){
	var coords=document.getElementById('mapLongLat');
	coords=coords.value.slice(1,-1).split(" || ", 2); //This to remove the first and last double quote characters and create array of size 2 based on the separator.
	coordsPrime=coords[0] + " " +coords[1];	
	return coordsPrime;
}

function haversineDistance(latitude,longitude,pointLat,pointLong) {
	var R = 3958.8;
	var latitude = latitude * (Math.PI/180); 
	var pointLat = pointLat * (Math.PI/180); 
	var difflat = pointLat-latitude; 
	var difflon = (pointLong-longitude) * (Math.PI/180);
	
	var d = 2 * R * Math.asin(Math.sqrt(Math.sin(difflat/2)*Math.sin(difflat/2)+Math.cos(latitude)*Math.cos(pointLat)*Math.sin(difflon/2)*Math.sin(difflon/2)));
	return Math.round(1000*d)/1000;
}

function appendSitesClosePoints(sitesClosePtArray) {

	var markupSite="";
	document.getElementById("findCloseSitePts").innerHTML = "";
							
	if (sitesClosePtArray.length==0){
		document.getElementById("findCloseSitePts").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
	}
	else {
		for(var i =0 ; i<sitesClosePtArray.length;i++){
			markupSite +="<tr style='height: 30px;'><td>"+sitesClosePtArray[i]["siteID"]+"</td><td  style='min-width:250px;'>"+sitesClosePtArray[i]["siteName"]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+sitesClosePtArray[i]["longitude"]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+sitesClosePtArray[i]["latitude"]+"' readonly></input ></td><td style='width:100px;'>"+(sitesClosePtArray[i]["distance"])+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='getDrivingDistanceClosePoint(this)'>Get Distance</button> </td></tr>"
		}
	}						  
	$("#searchCloseSiteTBody").append(markupSite);
    drivingDistance("findCloseSite");
	makeAllSortable();		
}
function makeAllSortable(parent) {
	parent = parent || document.body;
	var t = parent.getElementsByTagName('table'), i = t.length;
	while (--i >= 0) makeSortable(t[i]);
}
function drivingDistance(tableId) {
	for (indxRow = 0; indxRow < 5 ; indxRow++){
		 $("#"+tableId+" >tbody").find("tr").eq(indxRow).find('td[name="DDistanceB"]').children('button').click();
    }
}
function makeSortable(table) {
	var th = table.tHead, i;
	th && (th = th.rows[0]) && (th = th.cells);
	if (th) i = th.length;
	else return; // if no `<thead>` then do nothing
	while (--i >= 0) (function (i) {
		var dir = 1;
		th[i].addEventListener('click', function () {sortTable(table, i, (dir = 1 - dir))});
	}(i));
}
function getDrivingDistanceClosePoint(e) {
	if (typeof(e) == "object") {
		var directionsService = new google.maps.DirectionsService();
		
		var lat = $(e).parent().parent().children('td[name="LATT"]').children('input').val();
		var lng = $(e).parent().parent().children('td[name="LONGG"]').children('input').val();
		
		const originept = {lat: parseFloat(parseFloat($("#closePtsLat").val())), lng: parseFloat(parseFloat($("#closePtsLong").val()))};
		const nextpt = {lat: parseFloat(lat), lng: parseFloat(lng)};
		var request  = {
			origin: originept,
			destination: nextpt,
			travelMode  : google.maps.DirectionsTravelMode.DRIVING
		}
		directionsService.route(request, function(response, status) {
			if ( status == google.maps.DirectionsStatus.OK ) {
				var resultt= ( response.routes[0].legs[0].distance.value) /1000 ;

			   }
			else {
				resultt= "no Root";
			}
			$(e).parent().parent().children('td[name="DDistanceB"]').children('button').hide();
			$(e).parent().parent().children('td[name="DDistance"]').children('input').show();
			$(e).parent().parent().children('td[name="DDistance"]').children('label').empty();
			$(e).parent().parent().children('td[name="DDistance"]').children('label').append(resultt);
			makeAllSortable();
		  });
		  
	} else {
		return false;
	}

}
var polylines = [];
function drawLine(color,path){
	 var line = new google.maps.Polyline({
	    path: path,
	    strokeColor: color,
	    strokeOpacity: 0.8,
        strokeWeight: 5,
        geodesic: true,
	    map: map
	});
	    polylines.push(line);
}
</script>
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>
