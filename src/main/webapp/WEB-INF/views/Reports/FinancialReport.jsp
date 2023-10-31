<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title></title>
     <link rel="shortcut icon" href="">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
		
	    
	    <!--  MultiSelect Script -->
        <link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet"    >
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>	
		<!-- Tags InputScript -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tagsInputAutocomplete.js"></script>
			
			
		<!-- Google Maps Script -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	
	    <!-- export scripts -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
       
</head>
<style>

.wid{
width:100%;
}

.clearShowButton{
background-color:white;
color:orange;

}

.clearShowButton:hover{
background-color:orange;
color:white;


}

.BTN{
width:90px !important;


}
.flex {
  display: flex;
  justify-content: center;
}

.flex-item + .flex-item {
  margin-left: 10px;
  margin-bottom:5px;
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
.btn-color {
background-image: linear-gradient(to right top, #b3b3b3, #b6b6b7, #b8b9ba, #bbbdbd, #bdc0c0, #b1b5b5, #a5abaa, #9aa09f, #7f8685, #656e6c, #4c5655, #343f3e);
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


.cadr{
border:0.01em solid grey;
}


.cadr2{
border:0.1em solid #808080;
padding:40px;
}

.title {
  margin:5px 0px;
  font-size: 25px;
  font-weight:600;
  font-family: 'Times New Roman', Times, serif;
}
.canvas-style{
height: 650px; 
}
.canvas-style2{
height: 400px !important; 

}
.canvas-style3{
height: 650px !important; 

}

/*This will style the icon button for chart*/
.buttonStyle{
    font-size: 20px;
    color:#444444;
	margin-top:10px;
	background: none;
	border: none;
}
    /*This should make them change their color when they are hovered*/
    .buttonStyle:hover {
         color:#08526d;
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
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
	
	<div Style=" left: 0; bottom: 0;" id="Financial Div">
	<div class="container-fluid">     
	     <div class="row"><p></p></div>
			<div class="row second" >	
			<div class="col-md-2" style="display: flex; ">
				<div class="form-group "style="margin-left: 15px; ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Financial Report</span>
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
    	                   <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" id="export" href="#" >Export<span class="caret" Style="padding-left:30px;" ></span></a>
                             
                              <ul class="dropdown-menu dropdown-menu-left" id="dropLeft" Style="left:-12.5rem !important;  max-height: max-content !important; max-width: max-content !important;">
                                 <li><a class="dropdown-item" id="gridExport" href="#" >Grid</a></li>                             
                             </ul>
                           </li>
    	                  
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="pdf" href="#">PDF</span></a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="setup" href="#">Setup Auto Email</a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="addCol" href="#">Add Column</a> </li>
    	                  </div>
			           </div>  
			            
		        </div>
		       
		 			<div class="glyph" Style="white-space: nowrap;overflow: hidden;">
			 			
		                  <button type="button" id="generate" class="btn btn-primary BtnActive" > Generate Report </button> 
			
			
			</div>
			</div>
		</div>
			
</div>

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
				<button type="button"  id ="showOnMap"class="btn btn-light clearShowButton" style=" margin-top:-10px;  margin-right: 15px;"  >Show on Map</button>
		 		<button type="button"  id ="clearButton"class="btn btn-light clearShowButton" style=" margin-top:-10px; margin-right:-15px; "  >Clear</button>		 	
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
				<div id="Domain"></div>
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
       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
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
										<div id="alertMsgDiv" style="display: none;padding-left: 40px">
										<br>
											<b style="color:red;font-size:15px;white-space: nowrap;">The number of original fetched data is exceeding the number of allowed data to show. Please set a filter to reduce the fetched data.</b> 
											</div>

								<div id= "tableGrid" class="table-responsive almgrid-table-div">
									<table id="gridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>FAR ID 
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
	<div class="row second"  style="padding-left: 15px;">								
			<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Initial Cost</span>
								<input type="text" id="initialCostAllFar" readonly value="${totalInitialCost}" class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Net Cost</span>
								<input type="text" id="netCostAllFar" readonly value="${totalNetCost}" class="form-control text-input" />
							</div>
						</div>
					</div>
	</div>
	
	<div class="row"  style="padding-left: 15px;">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Accumulated Depreciation</span>
								<input type="text" id="accuDeprAllFar" readonly value="${totalAccumdepr}" class="form-control text-input" />
							</div>
						</div>
					</div>
					
	</div>
		<br>
		<div class="row second"  style="padding-left: 15px;">								
			<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Initial Cost of Fetched FAR</span>
								<input type="text" readonly id="initialCostFetchedFar" value="${totalInitialCostFetched}" class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Net Cost of Fetched FAR</span>
								<input type="text" readonly id="netCostFetchedFar"  value="${totalNetCostFetched}" class="form-control text-input" />
							</div>
						</div>
					</div>
	</div>
	
	<div class="row"  style="padding-left: 15px;">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Accumulated Depreciation of Fetched FAR </span>
								<input type="text" readonly id="accuDeprFetchedFar" value="${totalAccumdeprFetched}" class="form-control text-input" />
							</div>
						</div>
					</div>
					
	</div>
	<br>
		<div class="row second"  style="padding-left: 15px;">								
			<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Initial Cost of Filtered FAR</span>
								<input type="text" readonly id="initialCostFilteredFar" class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-5">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Net Cost of Filtered FAR</span>
								<input type="text" readonly id="netCostFilteredFar" class="form-control text-input" />
							</div>
						</div>
					</div>
	</div>
	
	<div class="row"  style="padding-left: 15px;">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Accumulated Depreciation of Filtered FAR </span>
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
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="#collapseTwo">
         GIS
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
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
markersFarSites=[];		
var distinctSites =[];

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

	   
	    $("#legendDiv").toggle();

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

}//end initMap

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
    	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
        map.setCenter(Nairobi);
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
	  { label: "ZTE", value: 'zte'},	
	  { label: "Ericsson", value: 'Ericsson'},	  
	  { label: "Tejas", value: 'Tejas'},	  
	  { label: "Alcatel", value: 'Alcatel'},	  
	  { label: "NEC", value: 'Nec'},	  	 
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
date.setFullYear(date.getFullYear() - 1); // Set the year one year before
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

    var ReportArrayGlobal = ${financialReportList};
    var exportArrayGrid = []; // for export 
    var filteredSitesGrid=[]; // used in draw on map 
    

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
	        if (dataArray.length > 0) {
		        var initCost=0,netCost=0,accuDepr=0;
        		var ArrayKeys = Object.keys(dataArray[0]);
        		var columnVal;

   	    		//for export
	     		var data = [];
	     	    exportArrayGrid = [];
	    		data.push('\r');
	       		data.push(["FAR ID", "Item Code", "Item Name", "Last Modified Date","Item Serial Number","Item Name Register","PO ID","Site ID","Site Name","Longitude","Latitude","Initial Cost","Net Cost","Accumulated Depreciation"]);

	       		filteredSitesGrid = dataArray; // used in draw on map 
	       		
        for (var i = 0; i < dataArray.length; i++) {
         
  			data.push('\r');
  			
           for (var j = 0; j < ArrayKeys.length; j++) {
           	
        	   columnVal = ArrayKeys[j];
          	   data.push(dataArray[i][ArrayKeys[j]]);// for export 
            
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
    
    $('#showOnMap'). click(function(){  
		 distinctSites =[];
		 markerClusterFarSites.clearMarkers();
		
		for (var i = 0; i < filteredSitesGrid.length; i++) {			
			if(distinctSites.includes(filteredSitesGrid[i]["siteID"])==false) {
				distinctSites.push(filteredSitesGrid[i]["siteID"]);
				if(!markersFarSites[filteredSitesGrid[i]["siteID"]]){
					createSiteMarker(filteredSitesGrid[i]["siteID"],filteredSitesGrid[i]["longitude"],filteredSitesGrid[i]["latitude"])
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

		 $('.showHideSitesCheckbox').prop('checked', false);
		 $(".showHideSitesCheckbox").attr('disabled', true);
		 markerClusterFarSites.clearMarkers();	
		 markersFarSites=[];

		 
		$("#generateLoaderDiv").show();
		  
		  
		$("#gridTable").remove();
		$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table">'
				+'<thead><tr class="header"><th>FAR ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> '
				+'<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
					+'<th>Item Code<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th>Item Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th>Last Modified Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
					+'</ul></li></th><th> Item Serial Number<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th> Item Name Register<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th> PO ID <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
					+'</ul></li></th><th>Site ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">	</ul></li></th>'
					+'<th>Site Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'						
					+'<th>Longitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Latitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Initial Cost<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Net Cost<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					+'<th>Accumulated Depreciation<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
					+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
					
					+'<tr>'
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
			url : "${pageContext.request.contextPath}/GenerateGridFinancialReport",
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
                  ReportArrayGlobal = data.financialReportList; 
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
         			        if (dataArray.length > 0) {
  					 
  	                       var initCost=0,netCost=0,accuDepr=0;
  			               var ArrayKeys = Object.keys(dataArray[0]);
  			       		   var columnVal;
  			       		  
  			       		   //for export
  			       		    var data = [];
  			       	       exportArrayGrid = [];
  			       		   data.push('\r');
  			       		   data.push(["FAR ID", "Item Code", "Item Name", "Last Modified Date","Item Serial Number","Item Name Register","PO ID","Site ID","Site Name","Longitude","Latitude","Initial Cost","Net Cost","Accumulated Depreciation"]);

  	  				       filteredSitesGrid = dataArray; // used in draw on map 
  	  			       		
  			       		
  			           for (var i = 0; i < dataArray.length; i++) {
  							 // for export 
		          				data.push('\r');

  			               for (var j = 0; j < ArrayKeys.length; j++) {
  			               	
  			            	 columnVal = ArrayKeys[j];

  			            	 
  			                 // for export 
  			            	 data.push(dataArray[i][ArrayKeys[j]]);

			            	 	//console.log("columnVal is "+columnVal)
  			                       if(columnVal =="initCost")  initCost+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
  			                       if(columnVal =="netCost")   netCost+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
  			                       if(columnVal =="accuDepr")  accuDepr+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
  							   
  		          			   }
  		          		   }

  			                /// for export
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



	  $('#export').on('click', function(e) {		
		if (!$(this).next().hasClass('show')) {
              $(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
          }

		  var $subMenu = $(this).next(".dropdown-menu");
          $subMenu.toggleClass('show');
          $(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
              $('.dropdown-submenu .show').removeClass("show");
          });
          return false;
		 });

	// close ul for export after selecting export option
		$('#gridExport, #mapExport, #chartsExport, #allExport').click(function(){
				$(this).parents('#dropLeft').removeClass("show");
      });

		// clicking outsie menu div close ul     
		var specifiedElement = document.getElementById('notifactionDropdown');

		//I'm using "click" but it works with any event
		document.addEventListener('click', function(event) {
		   var isClickInside = specifiedElement.contains(event.target);

			if (!isClickInside) {
			   $('#dropLeft').removeClass("show");
		    }
		});	
				
		
		$('#gridExport').click(function(){      
  		  downloadCSVFile(exportArrayGrid, "grid");
		});
			      
		
    function downloadCSVFile(csv, filename) {
    	var csv_file, download_link;

    	csv_file = new Blob([csv], {type: "text/csv"});
    	download_link = document.createElement("a");
    	download_link.download = filename;
    	download_link.href = window.URL.createObjectURL(csv_file);
    	download_link.style.display = "none";
    	document.body.appendChild(download_link);
    	download_link.click();
    }
    
	function createSiteMarker(siteID,longitude,latitude) {

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
		
		if(!markersFarSites[markerId]){
			siteMarker = new google.maps.Marker({
				position: pos,
				ID:markerId,
				icon:iconSite,
		});
			siteMarker.metadata = { id: markerId };
			markersFarSites[markerId] = siteMarker;
			markersFarSites.push(siteMarker);
			markerClusterFarSites.addMarker(markersFarSites[""+markerId]);
			markersFarSites[markerId].setMap(map);
				
	   }
		else{
			if(markersFarSites[markerId].map!=map){
				markersFarSites[markerId].setMap(map);
			}				
			markersFarSites[markerId].setPosition(pos);
		}
	}     		
    		
	
});
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


</script>
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>
