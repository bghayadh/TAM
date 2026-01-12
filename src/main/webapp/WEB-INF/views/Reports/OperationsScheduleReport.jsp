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
    <title>Operations Schedule</title>

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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>

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

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Reports/OperationsSchedule/GridManipulation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Reports/OperationsSchedule/RemainingTimeToExecute.js"></script>


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

	<div Style="left: 0; bottom: 0;" id="OperationsScheduleReportDiv">
		<div class="container-fluid">
			<div class="row">
				<p></p>
			</div>
			<div class="row second">
				<div class="col-md-2" style="display: flex;">
					<div class="form-group " style="margin-left: 15px;">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color: green">Operations Schedule Report</span>
						</div>
					</div>
				</div>
				<div class="col-md-3"></div>
				<div class="col-md-3"></div>
				<div class="col-md-2"></div>

				<div class="col-md-2" id="menuBtn" style="text-align: right;">
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
																	<th>Process Name
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Operation Name
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Status
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Class Name
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Next Execution Date
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Last Execution Date
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Cron Expression
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Creation Date
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>																	
																	<th>Last Modification Date
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
																				<i class="fa fa-list almgrid-filter-i"
																					aria-hidden="true"></i>
																			</button>
																			<ul class="dropdown-menu filter-dropdown-ul"></ul>
																	</li>
																	</th>
																	<th>Start Execution Date
																		<li class="filter-dropdown dropdown">
																			<button class="almgrid-filter" data-toggle="dropdown">
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
															Operations</span> <input readonly type="text"
															id="totalOperations" class="form-control text-input"
															style="width: 120px;" />
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Total Enabled Operations</span> <input
															readonly type="text" id="totalEnabledOperations"
															class="form-control text-input" style="width: 120px;" />
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Total Number of Processes</span> <input
															readonly type="text" id="totalProcesses"
															class="form-control text-input" style="width: 120px;" />
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
	</div>
</body>
<script>
function getContext() {
	return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
var executionCountdownInterval = null;
$(document).ready(function() {
	
	//collapse active class	
	$('.panel-collapse').on('show.bs.collapse',function() {
		$(this).siblings('.panel-heading').removeClass('active');
	});

	$('.panel-collapse').on('hide.bs.collapse',function() {
		$(this).siblings('.panel-heading').addClass('active');
	});
	
	loadGrid(${listOperationsReport});
		 
	$("#generate").click(function() {
		generateFlag="1";
		  
		$("#gridTable").remove();
		$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header fixed-header">'
				+'<th>ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Process Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Operation Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Status<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Class Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Next Execution Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Last Execution Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Cron Expression<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Creation Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'				
				+'<th>Last Modification Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<th>Start Execution Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
				+'<tr><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th></tr></thead><tbody></tbody></table>');

		$("#generateLoaderDiv").show();
		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateOperationsScheduleReport",
			dataType : "json",
			data : {
			},
			success : function(data) {
				if (data != null) {
				loadGrid(data.listOperationsReport);
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
		downloadLink.setAttribute('download', "OperationsScheduleReport");
		document.body.appendChild(downloadLink);
		downloadLink.click();
		document.body.removeChild(downloadLink);
	});
}); // The end of the document.ready
	
</script>
</html>