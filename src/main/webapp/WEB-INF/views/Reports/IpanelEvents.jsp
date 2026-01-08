<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Intelligent Panel Events</title>

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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pdfmake.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas.min.js"></script>

<!-- ALM GRID Scripts -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRowsIpanelEventsReport.js"></script>

<!--Network_Index.css is included here in order to use the css of right click menu  -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">

<!--  MultiSelect Script -->
<link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet">
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
.fixed-header {
	opacity: 1;
	filter: alpha(opacity = 100);
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

.mapButtons:hover {
	color: white;
	background-color: orange;
}

.showElement-Location:hover {
	color: white;
	background-color: #007bff;
}

.showElement-Location {
	background-color: white;
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

.legendContainer {
	height: 800px;
	position: relative;
}

.box {
	width: 100%;
	height: 100%;
	position: absolute;
	top: 10px;
	left: 0;
}

.stack-top {
	z-index: 3;
	margin: 120px;
}

.title {
	margin: 5px 0px;
	font-size: 25px;
	font-weight: 600;
	font-family: 'Times New Roman', Times, serif;
}

.custom-class-assignedto-modal .modal-dialog {
	width: 100%;
}

.custom-class-assignedto-modal .modal-body {
	height: 400px;
	overflow: auto;
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
	z-index: 9999;
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
	<c:set var="pg" value="report" scope="session" />
	<jsp:include page="../header.jsp"></jsp:include>

	<div Style="left: 0; bottom: 0;" id="IntelligentPanelEventsReportDiv">
		<div class="container-fluid">
			<div class="row">
				<p></p>
			</div>
			<div class="row second">
				<div class="col-md-2" style="display: flex;">
					<div class="form-group " style="margin-left: 15px;">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color: green">Intelligent
								Panel Events Report</span>
						</div>
					</div>
				</div>
				<div class="col-md-3">
				</div>
				<div class="col-md-3"></div>
				<div class="col-md-2">
					<button type="button" id="showOnMap" class="btn mapButtons" style="margin-left: 50px;" onclick="showOnMap()">Show on Map</button>
				</div>

				<div class="col-md-2" id="col3" style="text-align: right;">
					<div class="btn-group pull-right" style="padding: 0px !important;">

						<div class="glyph" style="padding-top: 0px;">
							<div class="dropdown" Style="margin-right: 10px; height: 30px;">
								<button class="btn btn-secondary dropdown-toggle" type="button"
									id="notifactionDropdown" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">Menu</button>

								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="dropdownMenuButton" Style="width: 10px;">
									<ul class="list-unstyled mb-0">
										<li class="dropdown-submenu"><a class="dropdown-item"
											id="edit" href="#">Edit</a></li>
										<li class="dropdown-submenu"><a class="dropdown-item"
											id="print" href="#">Print</a></li>
										<li class="dropdown-submenu"><a class="dropdown-item"
											id="gridExport" href="#">Export</a></li>
										<li class="dropdown-submenu"><a class="dropdown-item"
											id="pdf" href="#">PDF</a></li>
										<li class="dropdown-submenu"><a class="dropdown-item"
											id="setup" href="#">Setup Auto Email</a></li>
										<li class="dropdown-submenu"><a class="dropdown-item"
											id="addCol" href="#">Add Column</a></li>
									</ul>
								</div>
							</div>
						</div>

						<div class="glyph" Style="white-space: nowrap; overflow: hidden;">
							<button type="button" id="generate"
								class="btn btn-primary BtnActive">Generate Report</button>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-9"></div>
				<div class="col-md-3">
					<div id="generateLoaderDiv" style="display: none;">
						<img
							src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif"
							width="40px" /><b style="color: #800020; font-size: 15px;">
							Loading Data ... Please wait</b>
					</div>
				</div>
			</div>

			<div class="container-fluid">
				<br>
				<div class="wrapper" style="margin-top: -10px;">
					<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
						<div class="panel panel-default" style="margin-bottom: 3px;">
							<div class="panel-heading" role="tab" id="headingOne">
								<h4 class="panel-title">
									<a role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseOne"
										aria-expanded="true" aria-controls="collapseOne"
										style="margin-top: -8px;"> Grid Table </a>
								</h4>
							</div>
							<div id="collapseOne" class="panel-collapse collapse show"
								role="tabpanel" aria-labelledby="headingOne">
								<div class="panel-body">
									<div class="card-body ">
										<div class="row">
											<div class="col-sm-12">
												<div class="almgrid-container">
													<div class="row">
														<div class="col-sm-4 almgrid-pagecount-box">
															Show <select class="cmb-row-count almgrid-pagecount">
																<option value="10" selected>10</option>
																<option value="25">25</option>
																<option value="50">50</option>
																<option value="100">100</option>
																<option value="500">500</option>
																<option value="1000">1000</option>
															</select> Rows
														</div>
														<div class="col-md-4">
															<div id="loaderDiv" style="display: none;">
																<img
																	src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif"
																	width="40px" /><b
																	style="color: #800020; font-size: 15px;"> Loading
																	Data ... Please wait</b>
															</div>
														</div>
														<div class="col-sm-4 almgrid-global-search-box">
															Search <input type="text"
																class="form-control almgrid-global-search" />
														</div>
													</div>
													<div id="tableGrid"
														class="table-responsive almgrid-table-div">
														<table id="gridTable"
															class="table table-striped table-bordered almgrid-table">
															<thead>
																<tr class="header fixed-header">
																	<th>ID
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Event ID
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Event Type
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>

																	<th>Event Timestamp
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>

																	<th>Created DateTime
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>

																	<th>Controller ID
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>

																	<th>Serial Number
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>

																	<th>Raw Payload
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Location Type
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>																	
																	<th>Location ID
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Location Name
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Warehouse
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Longitude
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Latitude
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>
																		<li class="filter-dropdown dropdown">
																			<button disabled class="almgrid-filter"
																				data-toggle="dropdown" style="display: none;">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																<tr>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>
																	<th><input type="text" class="almgrid-search"
																		placeholder="Search"></th>																		
																	<th><input type="text" disabled
																		class="almgrid-search" style="display: none"></th>
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
																	Viewing <span>0-0</span> of <span>0</span>
																</p>
															</div>
															<div class="col-sm-5 pagination-buttons">
																<nav aria-label="Page navigation">
																	<ul
																		class="pagination pagination-buttons justify-content-end">
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
									<div style="display: Block;" id="totalNums">
										<div class="row second" style="padding-left: 15px;">
											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Total Number of
															Controllers</span> <input readonly type="text"
															id="totalControllers" class="form-control text-input"
															style="width: 120px;" />
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Total Sites</span> <input
															readonly type="text" id="totalSites"
															class="form-control text-input" style="width: 120px;" />
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Total Customers</span> <input
															readonly type="text" id="totalCustomers"
															class="form-control text-input" style="width: 120px;" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default" style="margin-bottom: 3px;">
							<div class="panel-heading " role="tab" id="headingTwo">
								<h4 class="panel-title">
									<a role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseTwo"
										aria-expanded="true" aria-controls="#collapseTwo"
										style="margin-top: -8px;"> GIS </a>
								</h4>
							</div>
							<div id="collapseTwo" class="panel-collapse collapse show"
								role="tabpanel" aria-labelledby="headingTwo">
								<div class="panel-body">
									<div style="text-align: center; margin-top: 10px;">
										<div>
											<input id="mapLongLat" type='text' disabled
												style="width: 300px; height: 35px; text-align: center; margin-left: 52px; position: relative; top: -1px;" />
										</div>
									</div>
									<div class="legendContainer">
										<div class="card-body">
											<div class="box stack-top" id="legendDiv"
												style="position: relative; top: 222px; width: 290px; float: left; height: 170px; background: white; margin: 37px; display: none">
												<div class="legendHeader" id="legendHeader">
													<h6
														style="color: white; font-weight: bold; font-size: 2.5ex; display: inline-block; position: relative; margin-left: 20px;">Legend</h6>
												</div>

												<div id="tableDiv">
													<table id="iPanelEventsReport">
														<tr>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
															<th style="position: relative; left: 10px;"></th>
														</tr>
														<tr>
															<td style="position: relative; left: 37px;"><input
																style="position: relative; top: 11px;" type="checkbox"
																name="legendCheckbox" disabled
																class="showHideAllCustCheckbox"
																onclick="showHidePts('showHideAllCustCheckbox');"
																value="red" /></td>
															<td style="position: relative; top: 8px; left: 58px;"><div>
																	<img class='image' style="width: 30px; height: 30px;"
																		src='${pageContext.request.contextPath}/resources/NetworkImages/customerIcon.png'>
																</div></td>
															<td style="position: relative; top: 10px; left: 65px;"><label
																style="color: black; font-weight: bold; font-size: 2ex;">Customer</label></td>
															<td style="position: relative; top: 8px;"><div
																	style="position: relative; left: 55px; color: black;"
																	id="custCount"></div></td>
														</tr>
														<tr>
															<td style="position: relative; left: 37px;"><input
																style="position: relative; top: 11px;" type="checkbox"
																name="legendCheckbox" disabled
																class="showHideAllSitesCheckbox"
																onclick="showHidePts('showHideAllSitesCheckbox');"
																value="pink" /></td>
															<td style="position: relative; top: 8px; left: 58px;"><div>
																	<img class='image' style="width: 25px; height: 30px;"
																		src='${pageContext.request.contextPath}/resources/NetworkImages/redSiteIcon.png'>
																</div></td>
															<td style="position: relative; top: 8px; left: 65px;"><label
																style="color: black; font-weight: bold; font-size: 2ex;">Site</label></td>
															<td style="position: relative; top: 8px;"><div
																	style="position: relative; left: 20px; color: black;"
																	id="sitesCount"></div></td>
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
var map;
var markersCustomer =[];
var markersSites =[];
var distinctCustomers =[]; // used in check/uncheck all cust from legend
var distinctSites =[]; // used in check/uncheck all sites from legend
var distinctControllers = [];
var markerClusterCustomers ;
var markerClusterSites ;
var mapFlag="0"; // used to check if the markers are set on map
var infoWindow;
var MapMenu;
var filteredGridData=[]; // used in draw on map 
var srcDstCableList=[];
var elementsArray=[];
var generateFlag="0";

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

    markerClusterCustomers = new MarkerClusterer();
    markerClusterCustomers.setMap(map);

    markerClusterCustomers.setOptions({
        minimumClusterSize: 2,
        styles: [
            {
                url: '${pageContext.request.contextPath}/resources/clusterIcons/customerCluster.png',
                height: 60,
                width: 60,
                anchorText: [-3, -3]
            },
        ],
        calculator: function(markers, numStyles) {
            if (markers.length >= 1) return { text: markers.length, index: 3 };
        }
    });

    markerClusterSites = new MarkerClusterer();
    markerClusterSites.setMap(map);

    markerClusterSites.setOptions({
        minimumClusterSize: 2,
        styles: [
            {
                url: '${pageContext.request.contextPath}/resources/clusterIcons/pinkCluster.png',
                height: 60,
                width: 60,
                anchorText: [-3, -3]
            },
        ],
        calculator: function(markers, numStyles) {
            if (markers.length >= 1) return { text: markers.length, index: 3 };
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
	

		 
	  $("#generate").click(function() {

		generateFlag="1";
		
		//Disable and uncheck the checkboxes in legend
		$('.showHideAllCustCheckbox').prop('checked', false);
		$(".showHideAllCustCheckbox").attr('disabled', true);
		$('.showHideAllSitesCheckbox').prop('checked', false);
		$(".showHideAllSitesCheckbox").attr('disabled', true);

		infoWindow.close();
		
		 // Clear the map and arrays related to map
		 markerClusterCustomers.clearMarkers();	
		 markersCustomer=[];	  
		 document.getElementById("custCount").textContent = "";

		 markerClusterSites.clearMarkers();	
		 markersSites=[];	  
		 document.getElementById("sitesCount").textContent = "";
			
		 mapFlag="0";	
		  
		 //Recenter the map
		 var center=new google.maps.LatLng(1,38);
	     map.setCenter(center);
		 map.setZoom(6);  
		  
		$("#gridTable").remove();
		$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header fixed-header">'
				+'<th>ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Event ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Event Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Event Timestamp<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Created Time<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Controller ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Serial Number<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Raw Payload<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'				
				+'<th>Location ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Location Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Warehouse<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Longitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Latitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th><li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown" disabled style="display:none;"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<tr><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" disabled class="almgrid-search" style="display:none"></th></tr></thead><tbody></tbody></table>');

			$("#generateLoaderDiv").show();
								
		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateiPanelEventsReport",
			dataType : "json",
			data : {
			},
			success : function(data) {
			  if (data != null) {
				
               ReportArrayGlobal = data.iPanelEvents; 
                  if (ReportArrayGlobal.length == 0) { 
                	  alert("There is no data to display");
					  $('#totalControllers').val('0');
					  $('#totalSites').val('0');
					  $('#totalCustomers').val('0');	                	  
                  }
                  
                  console.log("iPanelEvents is " ,ReportArrayGlobal);
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
          			  	 	if (typeof markerClusterCustomers !== 'undefined' && markerClusterCustomers !== null) {
          				 		markerClusterCustomers.clearMarkers(); 
  					   	 	 }    
          					if (typeof markerClusterSites !== 'undefined' && markerClusterSites !== null) {
          						markerClusterSites.clearMarkers(); 
		   	  				}  	

          		//Clear all arrays and inputs related to map when the data in grid is filtered 
					 distinctCustomers =[];
					 distinctSites =[]; 
 		   		     markersSites=[];
     				 markersCustomer=[];	  	  
 				 	 mapFlag="0"; 	
 					 document.getElementById("sitesCount").textContent = "";
 					 document.getElementById("custCount").textContent = "";
 				 	 
 					$('.showHideAllCustCheckbox').prop('checked', false);
 					$(".showHideAllCustCheckbox").attr('disabled', true);
 					$('.showHideAllSitesCheckbox').prop('checked', false);
 					$(".showHideAllSitesCheckbox").attr('disabled', true);
 							 	  		 		
          			  var center=new google.maps.LatLng(1,38);
   				        map.setCenter(center);
   						map.setZoom(6); 

   						
         			     if (dataArray.length > 0) {         					 
  			               var ArrayKeys = Object.keys(dataArray[0]);
  			               console.log("ArrayKeys is " ,ArrayKeys);
  			       		   var columnVal;
  			       		   var data = [];//for export
  			       	       exportArrayGrid = [];
  			       		   data.push('\r');
  			       		   data.push(["id","event_id","event_type" ,"event_timestamp", "created_at","controller_id", "serial_numb","raw_payload","location_type","location_id","location_name","warehouse","longitude","latitude"]);  	  			       		

  	  			       	   filteredGridData =  dataArray; // used in draw on map 
  	  			       	   
  			           for (var i = 0; i < dataArray.length; i++) {
  	  			           data.push('\r');
	  			           if (distinctControllers.includes(dataArray[i]['serial_numb']) == false) {
								distinctControllers.push(dataArray[i]['serial_numb']);
						    }
	  			           if (dataArray[i]['location_type'] == 'Site' && distinctSites.includes(dataArray[i]['location']) == false) {
						   		distinctSites.push(dataArray[i]['location']);
						   }
	  			           if (dataArray[i]['location_type'] == 'Customer' && distinctSites.includes(dataArray[i]['location']) == false) {
						   		distinctCustomers.push(dataArray[i]['location']);
						   }
  	  			           
  	  			           
  			               for (var j = 0; j < ArrayKeys.length; j++) {
  			            	 columnVal = ArrayKeys[j];
  			            	 console.log("columnVal is " +columnVal);
	  			              if(columnVal !="showLocation") {
									console.log("ArrayKeys[j] is " ,ArrayKeys[j]);
									console.log("dataArray[i][ArrayKeys[j]] " ,dataArray[i][ArrayKeys[j]]);
	  	  			            	data.push(dataArray[i][ArrayKeys[j]]);
	  			              }
							}
  		          		   }
		                   exportArrayGrid.push(data);
						   $('#totalControllers').val(distinctControllers.length);
						   $('#totalSites').val(distinctSites.length);
						   $('#totalCustomers').val(distinctCustomers.length);	                	  
		                   
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
		downloadLink.setAttribute('download', "IntelligentPanelEventsReport");
		document.body.appendChild(downloadLink);
		downloadLink.click();
		document.body.removeChild(downloadLink);
	});
}); // The end of the document.ready


function createMarker(ID,longitude,latitude,Name,iconImg,markersArray,markerClusterArray,target) {
	markerId=ID;	
	console.log("ID is " +ID + " longitude is " +longitude + " latitude is " +latitude + " Name is " +Name);
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

	if(target=="site") {

		var idInfo ="<b style='font-size:13px;'><u>Site ID: </u></b>"+ID;
		var nameInfo ="<b style='font-size:13px;'><u>Site Name: </u></b>"+Name;
		var data="<div></br>"+idInfo+"</br>"+nameInfo+"</div>";			
				
	}
	else if(target=="customer") {

		var idInfo ="<b style='font-size:13px;'><u>Customer ID: </u></b>"+ID;
		var nameInfo ="<b style='font-size:13px;'><u>Customer Name: </u></b>"+Name;
		var data="<div></br>"+idInfo+"</br>"+nameInfo+"</div>";			
	}
	
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


function showHidePts(className){
	if(className=="showHideAllCustCheckbox") {
		clusterArray = markerClusterCustomers;
		markersArray = markersCustomer;
		distinctArray = distinctCustomers;
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

function showLocation(ID,rowIndex){				
	var longitude = filteredGridData[rowIndex]["longitude"];
	var latitude = filteredGridData[rowIndex]["latitude"];
	var Name = filteredGridData[rowIndex]["location_name"];
	var locationType = filteredGridData[rowIndex]["location_type"];
	var latLng = new google.maps.LatLng(latitude,longitude);
	// isInvalid: it is method to validate.
	if (isInvalid(ID) || isInvalid(longitude) || isInvalid(latitude) || isInvalid(Name) || isInvalid(locationType)
	) {
	    alert("Location is unknown");
	}
	
	map.setZoom(16);
	map.panTo(latLng);	
	if(mapFlag=="0") { // Show on map is not clicked before (markers are not set on map)
		if(locationType =="Customer") {
			$('.showHideAllCustCheckbox').prop('checked', true);
			$(".showHideAllCustCheckbox").attr('disabled', false);
		    document.getElementById("custCount").textContent = "";
		    if(!markersCustomer[ID]){
		    	distinctCustomers.push(ID); //  this array is used when checking all cust from legend
				createMarker(ID,longitude,latitude,Name,'customerIcon.png',markersCustomer,markerClusterCustomers,"customer")
			}  
			document.getElementById("custCount").textContent = "("+distinctCustomers.length+")";    				
		}
		else if(locationType =="Site") {
			$('.showHideAllSitesCheckbox').prop('checked', true);
			$(".showHideAllSitesCheckbox").attr('disabled', false);
		    document.getElementById("sitesCount").textContent = "";
		    if(!markersSites[ID]){
		    	distinctSites.push(ID); //  this array is used when checking all sites from legend
				createMarker(ID,longitude,latitude,Name,'redSiteIcon.png',markersSites,markerClusterSites,"site")
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

function getLongLatMouseMove(map){		  
	map.addListener("mousemove", (mapsMouseEvent) => {
	   $("#mapLongLat").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(13) +" || "
	    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(13), null, 2));	    
	});					 
}

function showOnMap() {  
	distinctCustomers =[]; 
	distinctSites =[];
	   
	markerClusterCustomers.clearMarkers();
	markerClusterSites.clearMarkers();	
	mapFlag="1";
	
	for (var i = 0; i < filteredGridData.length; i++) {
		if (filteredGridData[i]["location_type"] == "Site") {				
			if(distinctSites.includes(filteredGridData[i]["location"])==false) {
				distinctSites.push(filteredGridData[i]["location"]);
				if(!markersSites[filteredGridData[i]["location"]]){
					//createSiteMarker(filteredGridData[i]["location"],filteredGridData[i]["longitude"],filteredGridData[i]["latitude"],filteredGridData[i]["location_name"]);
					createMarker(filteredGridData[i]["location"],filteredGridData[i]["longitude"],filteredGridData[i]["latitude"],filteredGridData[i]["location_name"],'redSiteIcon.png',markersSites,markerClusterSites,"site");
				}
				else {					
					markersSites[filteredGridData[i]["location"]].setMap(map);
					markerClusterSites.addMarker(markersSites[""+filteredGridData[i]["location"]]);
				}
			}			
        }
		else if (filteredGridData[i]["location_type"] == "Customer") {
			if(distinctCustomers.includes(filteredGridData[i]["location"])==false) {
				distinctCustomers.push(filteredGridData[i]["location"]);
				if(!markersCustomer[filteredGridData[i]["location"]]) {
					//createSiteMarker(filteredGridData[i]["location"],filteredGridData[i]["longitude"],filteredGridData[i]["latitude"],filteredGridData[i]["location_name"]);
					createMarker(filteredGridData[i]["location"],filteredGridData[i]["longitude"],filteredGridData[i]["latitude"],filteredGridData[i]["location_name"],'customerIcon.png',markersCustomer,markerClusterSites,"customer");
				}
				else {					
					markersCustomer[filteredGridData[i]["location"]].setMap(map);
					markerClusterCustomers.addMarker(markersCustomer[""+filteredGridData[i]["location"]]);
				}
			}
		}
	}
	document.getElementById("sitesCount").textContent = "("+distinctSites.length+")";
	document.getElementById("custCount").textContent = "("+distinctCustomers.length+")";
    if(distinctSites.length >0) {
		$('.showHideAllSitesCheckbox').prop('checked', true);
		$(".showHideAllSitesCheckbox").attr('disabled', false);
	}   
	else {
		$('.showHideAllSitesCheckbox').prop('checked', false);
		$(".showHideAllSitesCheckbox").attr('disabled', true);
	}
	if(distinctCustomers.length >0) {        
		$('.showHideAllCustCheckbox').prop('checked', true);
		$(".showHideAllCustCheckbox").attr('disabled', false);
	}
	else {
		$('.showHideAllCustCheckbox').prop('checked', false);
		$(".showHideAllCustCheckbox").attr('disabled', true);
	}
		//Scroll to the map div
		 document.getElementById("headingTwo").scrollIntoView({ behavior: "smooth" });
	}
	
function isInvalid(value) {
    return value == null || value === '' || value === 'null';
}
	

</script>
<!-- 
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
 -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly" async defer></script>

</html>