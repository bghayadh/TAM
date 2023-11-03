<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<head>
		<meta charset="utf-8">
		<title></title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="shortcut icon" href="">
		<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
		<script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
		<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
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
		<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
		<script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/jspdf.plugin.autotable.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/tableHTMLExport.js"></script>
		<link href="${pageContext.request.contextPath}/resources/cssgrid/bootstrap-datepicker.min.css" rel='stylesheet'	type='text/css'>
		<script src="${pageContext.request.contextPath}/resources/scriptsgrid/bootstrap-datepicker.min.js" type='text/javascript'></script>
		<!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
		 <!-- export scripts -->
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
			
		
		
		
		
		



<style>

#Dropdown {
	text-align: left;
}

#Dropdown a:hover {
	color: gold !important;
	text-decoration: none;
}


    #popUpDiv {
position:fixed;
top: 30%;
left: 50%;
background-color:#eeeeee;
border:5px solid #08526d;
width:400px;
height:auto;
margin-left:-150px;
margin-top:-95px;

-moz-border-radius: 16px;
-webkit-border-radius: 16px;
border-radius: 16px;
box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px#000000;

z-index: 9003;
 /*above nine thousand*/}

.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: auto;
  overflow : auto;
}

.css-row
{ 
    background:#409BFF;
}
    
.modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
}

/* added for viewing images */
#imgpopUpDiv {
	position:fixed;
	background-color: #eeeeee;
	border: 5px solid #08526d;
	width: 80%;
	height: auto;
	padding: 10px;
	-moz-border-radius: 16px;
	-webkit-border-radius: 16px;
	border-radius: 16px;
	box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px #000000;
	z-index: 9003;
	/*above nine thousand*/
}


.nav-link.active {
  color: #1D3763 !important;
}
</style>
	</head>

	<body>
<%-- 		<c:set var="page" value="Sales" /> --%>

<%-- 		<%@ include file="header.html" %> --%>
  <c:set var="pg" value="Sales" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
			<!--  end of general head page -->
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-12">
						<p></p>
					</div>

				</div>

				<div class="row second">
					<div class="col-md-9">
					</div>
					<div class="col-md-3" style="text-align: right;">
						<div class="btn-group pull-right">
						<div class="glyph" id = "exportButton">
							<!--  -->
							<!-- <button class="btn btn-secondary" type="button" id="export">Export</button> -->   
	                     		
    	         
								<button type="button" id="Lview" class="btn btn-danger" data-toggle="tooltip"
									data-placement="top" title="List View" style="background: #da6815;">
									<i class="fa fa-bars"></i>
								</button>
								<button type="button" id="searchClient" class="btn btn-light" data-toggle="tooltip" data-placement="top"
									title="Search">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</div>
				</div>

<div class="container">
	<div id ="poModal" class="modal fade  custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #2678CC;" >
				<h5 class="modal-title" id="myModalLabel1" style="font-weight:bold; color: #E9ECEF;position:relative;top:4px;">  Filter Options </h5>
				<button type="button" name="closePopup" id="closePopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	 <ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="date-tab" style="color: gold;" data-toggle="tab" href="#date" role="tab" aria-controls="date" aria-selected="true">Date</a>
  		</li>
  
  		<li class="nav-item">
   			 <a class="nav-link " id="clientinfo-tab" style="color: gold;" data-toggle="tab" href="#clientinfo" role="tab" aria-controls="clientinfo" aria-selected="false">Client Info</a>
  		</li>
  		
  		<li class="nav-item">
   			 <a class="nav-link " id="agentinfo-tab" style="color: gold;" data-toggle="tab" href="#agentinfo" role="tab" aria-controls="agentinfo" aria-selected="false">Agent Info</a>
  		</li>
  		
  		<li class="nav-item">
   			 <a class="nav-link " id="status-tab" style="color: gold;" data-toggle="tab" href="#status" role="tab" aria-controls="status" aria-selected="false">Status</a>
  		</li>
  		 <li class="nav-item">
   			 <a class="nav-link " id="region-tab" style="color: gold;" data-toggle="tab" href="#regioninfo" role="tab" aria-controls="region" aria-selected="false">Region</a>
  		</li>
 
	</ul>
            

<div class="tab-content">
  <div class="tab-pane active" id="date" role="tabpanel" aria-labelledby="date-tab">
  <p></p>
<div class="container-fluid">
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Start Date</span> <input
																	type="date"
																	class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																	id="popupstartdate" value="${popupstartdate}"
																	style="width: 675px; height: 37px;" />
															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">End Date</span> <input
																	type="date"
																	class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																	id="popupenddate" value=""
																	style="width: 675px; height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
  </div>
  <div class="tab-pane" id="clientinfo" role="tabpanel" aria-labelledby="clientinfo-tab">
<p></p>
	<form>
		<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Mobile Number </span>
   					<input type="text" id="popupMobileNumber"  style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  
		
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >First Name</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupFname" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Last Name</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupLname" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>

    </div>
</div>	
</form>
</div> 

<div class="tab-pane" id="agentinfo" role="tabpanel" aria-labelledby="agentinfo-tab">
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Agent Number</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupAgentNumber" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Agent Name</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupAgentName" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>

    </div>
</div>	
</div>

  <div class="tab-pane" id="status" role="tabpanel" aria-labelledby="status-tab">
<p></p>
	
		<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Status </span>
   					<input type="text" id="popupStatus" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  
		
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Approval Status</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupApprovalStatus" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Tkash Status</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupTkashStatus" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>

    </div>
</div>	


</div> 
<div class="tab-pane" id="regioninfo" role="tabpanel" aria-labelledby="region-tab">
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Region</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupRegion" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Area</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupArea" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>

    </div>
</div>	
</div>

</div>
</div>
					
<div class="modal-footer">
	<button type="button" id="popUpSubmit" class ="btn btn-default btn-primary BtnActive  " style="color:white;position:relative;">Submit</button>
	 <button type="button" id="popupClearFields" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;">Clear Fields</button>
	<button type="button" id="popUpCancel" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;">Cancel</button>
</div>							                
</div>			
</div> 
</div>
</div>
<div id="imgpopUpDiv" style="display: none;margin:auto;">
<p></p>

<div class="row">
					
						<div id="imageback" style="margin: auto;"></div>
						<div id="imagefront" style="margin: auto;"></div>
						<div id="clientData" style="margin: auto;"></div>
				

				</div>
		
		<p></p>

		<div class="row">
		
		<div class="col-md-6" >
		
		<button type="button" id="imgcancelButton" 
				class="btn btn-primary BtnActive">
				<i class="fa fa-times"></i> Close
			</button>
		
		</div>
			<div class="col-md-6" style="margin-left:-170px;">
			<button type="button" id="imgApproveButton" 
				style ="width:100px; height:30px; background-color:#008000">
				 Approve
			</button>
			
			
			<button style ="width:100px; height:30px; background-color:#FF0000" id="rejreason"
			data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" class="dropdown-toggle">
			Reject
			 </button>
			<div id="Dropdown" class="dropdownmenu-content">

								<ul class="dropdown-menu">
									<li><a type="button" class="dropdown-item" id="duplicate">Duplicate</a></li>
									<div class="dropdown-divider"></div>
									<li><a type="button" class="dropdown-item" id="invalid">Invalid Image</a></li>
									<div class="dropdown-divider"></div>
									<li><a type="button" class="dropdown-item" id="oneSide">One Side Only</a></li>
									<div class="dropdown-divider"></div>
									<li><a type="button" class="dropdown-item" id="wrongID">Wrong ID</a></li>
									<div class="dropdown-divider"></div>
									<li><a type="button" class="dropdown-item" id="fraud">Fraud</a></li>
									<div class="dropdown-divider"></div>
									<li><a type="button" class="dropdown-item" id="unclear">Unclear Image</a></li>
								</ul>
							</div>
					
			
		
			</div>
			
			
		</div>

	</div>
</div> 
				<p></p>

				<!-- /.card-header -->
				

<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top:0px">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"
		             style="color: gold;text-align:center;line-height:2.75em; padding-left:5px;">Client Image Approval LIST</li>

		           
		                                                                                                                         
						  </li>
									
		     </ul>
		     
		</div>
</div>
	<div class="card-body">
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
								<div id="alertMsgDiv" style="display: none;padding-left: 90px">
								<br>
									<b style="color:red;font-size:15px;white-space: nowrap;">The number of original fetched data is exceeding the number of allowed data to show. Please set a filter to reduce the fetched data from this button  </b> 
									<i class="fa fa-search" style="color:red;border:1px solid black;fontSize:22px;background-color:white;"></i>										
								</div>
								<div id="ImgclientsGridTable" class="table-responsive almgrid-table-div">
									<table id="ImgClientsTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												<th class="table-select-all">
												</th>
												

												<th>Mobile Number
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Client Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
													<th>Approval Status
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Rejection Reason
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Actions
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Client ID Number
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Last Modified Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Agent Number
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Agent Full Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Back ID Image
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Front ID Image
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Username
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
											
											</tr>

											<tr>
												<th class="table-select-all"><input type="checkbox"
														class="table-select-all-checkbox"></th>
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
										<tbody>

										</tbody>

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



			<script>

			if(${exportList} == 1){
				$("#exportButton").prepend('<button class="btn btn-secondary" type="button" id="export">Export</button>');
				}

				var ClientsListData = ${ ListGridTable };

				console.log(ClientsListData);
				var selectedRow;
				var tab =0;
				var clientIDnumber = null;
				var clientMobilenumber = null;
				var clientID = null;
				var clientName = null;
				$(document).ready(function () {

					 var almgrid = new Almgrid({
						tableId: "ImgClientsTable",
						dataArray: ClientsListData,
						columnLinkNb: [5],
						selectCheckbox: true
					});

					 var prevColor = null;
					 var prevselectedRow = null;

					 

					    
					$(".almgrid-table").on("click", ".almgrid-link", function (e) {

							selectedRow = $(this).closest('tr');
							prevColor = selectedRow.css("background");
							
							//prevColor = selectedRow.css("background");
							
							if(prevselectedRow == null){
								selectedRow.css('background','#FFFF00');
							}else{
								if(selectedRow != prevselectedRow ){
									selectedRow.css('background','yellow');
									prevselectedRow.css('background',prevColor);
									prevselectedRow.find('td:nth-child(4)').css('color','black');}
							}

							var status = $(this).closest('tr').find('td:nth-child(4)').text();
							$("#clientData").empty();
							if(${firstlvlList} == 0){
								alert("You don't have any permission at level of validation");
								return false;
							}
							if(${secondlvlList} == 0 && status != "Pending Approval"){
								alert("You don't have the permission for the second level of validation");
								return false;
							}
							
							
						
							var back = $(this).closest('tr').find('td:nth-child(11)').text();
	    					var front = $(this).closest('tr').find('td:nth-child(12)').text();
	    					clientIDnumber = $(this).closest('tr').find('td:nth-child(7)').text();
	    					clientMobilenumber = $(this).closest('tr').find('td:nth-child(2)').text();
	    					clientName =  $(this).closest('tr').find('td:nth-child(3)').text();
	    					clientID = $(this).parents('tr').children('td').find('input').val();
	    					$("#imageback").empty();
	    					$("#imagefront").empty();
	    					$("#imgpopUpDiv").fadeIn();
	    				    $("#imageback").append('<img id ="backimg" src= /SimRegPhotos/ClientPic/'+back+'.jpg width = "250" height = "350" />');
							const bckrotate = document.getElementById('backimg');
							bckrotate.style.transform='rotate(90deg)';
	    				    $("#imagefront").append('<img id="frontimg" src= /SimRegPhotos/ClientPic/'+front+'.jpg width = "250" height = "350" />');
	    				    const frntrotate = document.getElementById('frontimg');
	    				    frntrotate.style.transform='rotate(90deg)';
	    				    fillClientDatainPoPUP();
	    					$("#imgcancelButton").on("click", function (e) {
	    						$("#imgpopUpDiv").fadeOut();
	    						$("#imgpopUpDiv").hide();
	    						$("#imageback").empty();
	    						$("#imagefront").empty();
	    						$("#clientData").empty();
	    						reason = null;
	    						prevselectedRow = selectedRow;
	    						
	    					
	    					});
    					
    					
    				});

					var username = '${userFullName}';
					var statusResult=null;
					var reason = null;
					//selecting reason
					$("#fraud").click(function(){reason = "Rejected_Fraud"; addReasonofRejection(reason,username)});
					$("#wrongID").click(function(){reason = "Rejected_Wrong_ID"; addReasonofRejection(reason,username)});
					$("#invalid").click(function(){reason = "Rejected_Invalid_Image"; addReasonofRejection(reason,username)});
					$("#duplicate").click(function(){reason = "Rejected_Duplicate"; addReasonofRejection(reason,username)});
					$("#oneSide").click(function(){reason = "Rejected_One_Side_Only"; addReasonofRejection(reason,username)});
					$("#unclear").click(function(){reason = "Rejected_Unclear_Image"; addReasonofRejection(reason,username)});
						
				
					
					
					

					
	     			$("#popUpSubmit").on("click", function (e) {

						
		     			if(isNaN($("#popupAgentNumber").val())){
						alert("Agent Number should be Numeric");
						return false;
			     		}

		     			if(isNaN($("#popupMobileNumber").val())){
							alert("Mobile Number should be Numeric");
							return false;
				     		}
		     			
                		$("#poModal").modal("show");
    					$("#ImgClientsTable").remove();

    					$("#ImgclientsGridTable").append('<table id="ImgClientsTable" class="table table-striped table-bordered almgrid-table">'
    					+'<thead><tr class="header"><th class="table-select-all"></th>'
    					+'<th>Mobile Number <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown">'
    					+' <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
    					+'<th>Client Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
    					+'<th>Approval Status<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button> <ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
    					+'<th>Rejection Reason<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button> <ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
    					+'<th>Actions <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button> <ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
    					+'<th>Client ID Number<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'			
    					+'<th>Last Modified Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'				
    					+'<th>Agent Number<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'					
    					+'<th>Agent Full Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'							
    					+'<th>Back ID Image<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'							
    					+'<th>Front ID Image<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
    					+'<th>Username<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th></tr>'						
    					+'<tr><th class="table-select-all"><input type="checkbox" class="table-select-all-checkbox"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
    					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'				
    					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'				
    					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'		
    					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
    					+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th></tr></thead><tbody></tbody></table>');

                	$.ajax({
        					type : "GET",
        					contentType: "application/json; charset=utf-8",
        					url : "${pageContext.request.contextPath}/FilteredClientsImageListView",
        					
        					data : {
        						"startDate":$("#popupstartdate").val(),
        						"endDate":$("#popupenddate").val(),
        						"mobileNumber":$("#popupMobileNumber").val(),
        						"Fname":$("#popupFname").val(),
        						"Lname":$("#popupLname").val(),
        						"agentName":$("#popupAgentName").val(),
        						"agentNumber":$("#popupAgentNumber").val(),
                				"status":$("#popupStatus").val(),
                				"appStatus":$("#popupApprovalStatus").val(),
                				"TkashStatus":$("#popupTkashStatus").val(),
                				"region":$("#popupRegion").val(),
                				"area":$("#popupArea").val(),
			
        				},
        				dataType : "json",
        				success : function(data) {
        						console.log("Success");
        						if(data!=null){
        							ClientsListData = data;
        							almgrid = new Almgrid({
        							tableId: "ImgClientsTable",
        							dataArray: data,
        							columnLinkNb: [1],
        							selectCheckbox: true
        							});

        		             		document.getElementById('searchClient').className = 'btn btn-danger'
        			             	document.getElementById('Lview').className = 'btn btn-light'
        			             	$("#Lview").css('background', '#FFFFFF');	
        		             		$("#searchClient").css('background', '#da6815');
									document.getElementById("alertMsgDiv").style.display = "none";


        		             		
										$(".almgrid-table").on("click", ".almgrid-link", function (e) {
											console.log("after filtering");
	        								var param1 = $(this).parents('tr').children('td').find('input').val();
	        								if(tab == 0){
												var param = "${pageContext.request.contextPath}/ClientsFormView?clientID=" + param1 +"&NavAction=2";
		        								window.location.href = param;
		        								e.preventDefault();
		        							}
        								});
        						}	
        					
        					},
        					error : function(error) {
        						console.log("Failed");
        						 
        						
        					}
        				});
            			});
				

					

//////////////////////////////////////////////////////////////////////////export button
var exportArrayGrid=[];
//method to export the data into excel sheet
function exportGrid() {
	const d = new Date();
	let date = d.toLocaleDateString();
	var csv_file, download_link;
	csv_file = new Blob([exportArrayGrid], {type: "text/csv"});
	download_link = document.createElement("a");
	download_link.download = "ClientsImg_List_Export_"+date;
	download_link.href = window.URL.createObjectURL(csv_file);
	download_link.style.display = "none";
	document.body.appendChild(download_link);
	download_link.click();	
}
//////////////////////////////////////////////////////////////////////////fill data in listview
//filling the grid with the needed data
function fillGrid(filledGrid){
	exportArrayGrid=[];
	exportArrayGrid.push('\r');
	exportArrayGrid.push(["Customer_MSISDN", "Customer_Name", "Approval_Status","Customer_ID_Number ","Last_Modified_Date","Sales_Agent_MSISDN", "Sales_Agent_Name","Username"]);
	var value = Object.keys(filledGrid[0]);
	for(i=0;i<filledGrid.length;i++){
		exportArrayGrid.push('\r');
	    for(j=1;j<value.length;j++){
	    	if(j!=3 && j!=5 && j!=10 && j!=11){
				if(filledGrid[i][value[4]] == "-"){
					filledGrid[i][value[4]] = "Pending Approval";
				}

				if(filledGrid[i][value[4]] == "null"){
					filledGrid[i][value[4]] = "Approved";
				}
		    	exportArrayGrid.push(filledGrid[i][value[j]]);}
	    }
	}
}

$("#export").click(function() {
//check if the data is filtered or not
if(almgrid.filteredArray.length == 0 ){
fillGrid(ClientsListData);	
}else{
fillGrid(almgrid.filteredArray);
}
exportGrid();
//reload the page to clear the data array
//location.reload();

});


	             	$("#searchClient").click(function() {
	             		$("#poModal").modal("show");
	             		document.getElementById('searchClient').className = 'btn btn-danger';
		             	document.getElementById('Lview').className = 'btn btn-light';
		             	$("#Lview").css('background', '#FFFFFF');	
	             		$("#searchClient").css('background', '#da6815');	
	             	
	             		var todayDate = new Date().toISOString().slice(0, 10);
	             		var olderdate = new Date(todayDate);
	             		olderdate.setMonth(olderdate.getMonth() -3);
	             		
	             		$("#popupstartdate").val(olderdate.toISOString().slice(0, 10));
	             		$("#popupenddate").val(todayDate);
					});

	             // Resize and drag the image popup
	            	$('#imgpopUpDiv').draggable({
	            	handles: "e" ,
	            	
	            	});
	             // Resize and drag the popup
	            	$('.modal-content').resizable({
	            	handles: "e" ,
	            	
	            	});
	            	 
	            	$('.modal-dialog').draggable({
	            	handle: ".modal-header, .modal-footer"
	            	});
	            	   
	            	$('#poModal').on('show.bs.modal', function() {
	            	$(this).find('.modal-body').css({
	            	'max-height': '100%',
	            	});
	            	});
	                 
	            	//Reset the popup to original size after resizing 
	            	$('#poModal').on('hidden.bs.modal', function() {
	            	$(this).find('.modal-content').css({'width': '', 'height': ''});
	            	})
	            	 
	            	//Reset popup position after it has been dragged and closed
	            	$('body').on('hidden.bs.modal', function() {
	            	$('.modal-dialog').css({'top': '', 'left':''});
	            	})
	            			      
	            	// Minimize and Maximize fct for popup
	            	$(".modalMinimize").on("click", function(){
	            	$(".modal-body").slideToggle();
	            	$(".modal-footer").slideToggle();
	            	
	            	//Toggle between minimize/maximize icons
	            	var iconToChange = $('.icon-to-change');
	            		if(iconToChange.hasClass('fa-window-restore')){
	                 		iconToChange.removeClass('fa-window-restore')
	                		            .addClass('fa-minus')
	            		}
	            		else{
	                 		iconToChange.addClass('fa-window-restore')
	                		             .removeClass('fa-minus')
	            		}    		         
	            	}); // end minimize/maximize fct

	            	// Close popup function  				
	            	   $("#closePopup").on("click", function(){
	            	              	  
	            		    $("#poModal").modal("hide");
	            		    //clearFields();
	            		    
	            		    
	            			 
	            	 }); // end close fct

	            	 
	            	$("#popUpCancel").on("click", function (e) {
	            		$("#poModal").modal("hide");
	            		//clearFields();
	        			});
	            	  $("#popupClearFields").on("click", function (e) {
	         			 $('#poModal').find('input:text').val('');	
	         			 var strtDate = new Date().toISOString().slice(0, 10);
	         	     	 var endDatee = new Date(strtDate);
	         	     	 endDatee.setMonth(endDatee.getMonth() -3);
	         	     	 $("#popupstartdate").val(endDatee.toISOString().slice(0, 10));
	         	     	 $("#popupenddate").val(strtDate);		
	         	   });
		         	   
	            	 //	function clearFields()
        			//{$("#popupstartdate").val('');	$("#popupenddate").val('');$("#popupMobileNumber").val('');$("#popupFname").val('');
        			//	$("#popupLname").val('');$("#popupAgentName").val('');$("#popupAgentNumber").val('');$("#popupStatus").val('');
        			//	$("#popupApprovalStatus").val('');$("#popupTkashStatus").val('');$("#popupRegion").val('');$("#popupArea").val('');}
				});


				function fillClientDatainPoPUP(){

					var tr = "<tr>"+"<th>Client Name          : </th> <td> "+clientName+"</td></tr><br>"
							+"<tr>"+"<th>Client ID Number	  : </th> <td> "+clientIDnumber+"</td></tr><br>"
							+"<tr>"+"<th>Client Mobile Number         : </th> <td> "+clientMobilenumber+"</td></tr>";			
					$("#clientData").append(tr);
				}

				function addReasonofRejection(reason,username){
						$("#clientData").empty();
						fillClientDatainPoPUP();
						
						
						$.ajax({
							type : "GET",
							contentType: "application/json; charset=utf-8",
							url : "${pageContext.request.contextPath}/ImageValidation2ndLevel",
							data : {
								"clientIDnumber" : clientIDnumber,
								"clientMobilenumber" : clientMobilenumber,
								"status": "4",
								"clientID" : clientID,
								"rejReason" : reason,
								"userName": username
		    				},
		    				dataType : "json",
		    				
		    				success : function(data) {
			    				if(data.Result != null){
			    					if(data.Result == "Images had been rejected."){
				    					
			    						statusResult = "Rejected";
				    					selectedRow.find('td:nth-child(4)').css('color','red');
				    					selectedRow.find('td:nth-child(4)').html("Rejected");
				    					selectedRow.find('td:nth-child(5)').html(reason);
				    					$("#clientData").empty();
										fillClientDatainPoPUP();
				    					alert(data.Result);
				    					var tr = "<tr>"+"<th>Reason of Rejection: </th> <td><span class='desc'><span class='name' style='font-weight:bold; color:red;'>"+reason+"</span> </td></tr>";
										$("#clientData").append(tr);
				    					for(i=0 ; i<ClientsListData.length;i++){

											if(ClientsListData[i][0] == clientID ){
												ClientsListData[i][3] = statusResult;
												ClientsListData[i][4] = reason;
												return;
											}
					    				}

					    				alert(data.Result);
				    				}else{

				    					alert(data.Result);
					    			}
			    					
				    			}else{

			    					alert(data.Result);
				    			}
			    				
		    						
		    					},
							error : function(error) {
								alert(error.responseText);
							}
						});
				}//addReasonofRejection

				//used for approval from image approval
				$("#imgApproveButton").click(function(){
					$("#clientData").empty();
					fillClientDatainPoPUP();
					
					$.ajax({
						type : "GET",
						contentType: "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/ImageValidation2ndLevel",
						data : {
							"clientIDnumber" : clientIDnumber,
							"clientMobilenumber" : clientMobilenumber,
							"status": "2",
							"clientID" : clientID,
							"userName": '${userFullName}'
	    				},
	    				
	    				dataType : "json",
	    				
	    				success : function(data) {
		    				if(data.Result != null){

		    					if(data.Result == "Images approval done successfully."){
			    					
		    						alert(data.Result);
		    						var tr = "<tr>"+"<th>Approval Status: </th> <td><span class='desc'><span class='name' style='font-weight:bold; color:green;'>Approved</span> </td></tr>";
			    					$("#clientData").append(tr);
			    					statusResult = "Approved";
			    					selectedRow.find('td:nth-child(4)').css('color','green');
			    					selectedRow.find('td:nth-child(4)').html("Approved");
			    					selectedRow.find('td:nth-child(5)').html("-");
			    					for(i=0 ; i<ClientsListData.length;i++){

										if(ClientsListData[i][0] == clientID ){
											ClientsListData[i][3] = statusResult;
											return;
										}
				    				}
			    				}else{
			    					alert(data.Result);
				    			}
		    				}else{
		    					alert(data.Result);
			    			}

	    						
	    					},
						error : function(error) {
							console.log("Failed");
							alert(error.responseText);
						}
					});
					
					});
			</script>
	</body>

</html>