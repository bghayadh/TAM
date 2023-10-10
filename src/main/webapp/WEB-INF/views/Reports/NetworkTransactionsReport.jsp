<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Network Transactions Report</title>
<link rel="shortcut icon" href="">
<!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
<!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>  -->
<script
	src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link
	href="${pageContext.request.contextPath}/resources/css/all.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/ListView.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">

<!-- site revenue report scripts -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Reports/SiteRevenueReport.js"></script>

<!-- ALM GRID Scripts -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
	
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />

<!--  Chart script -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusioncharts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusioncharts.charts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.candy.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.zune.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.ocean.js"></script>

<!--  MultiSelect Script -->
<link
	href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>
<!-- Tags InputScript -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tagsInputAutocomplete.js"></script>

<!-- Google Maps Script -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Reports/SpeedCoverageGoogleMaps.js"></script>

<!-- export scripts -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
<!--<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
       <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>-->

</head>
<body>
	<c:set var="pg" value="report" scope="session" />
	<jsp:include page="${request.contextPath}/headerController"></jsp:include>
	
	<div class="container-fluid">
		<div class="row">
				<p></p>
		</div>
		
		<div class="row second">

				<div class="col-md-2" style="display: flex;">
					<div class="form-group " style="margin-left: 15px;">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color: green">Network Transactions Report</span>
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend" id="datetimepicker3"
							data-target-input="nearest">
							<span class="input-group-text">Start Date</span> <input
								type="text" id="startdate"
								class="form-control datetimepicker-input"
								data-toggle="datetimepicker" data-target="#datetimepicker3" />
							<div class="input-group-append" data-target="#datetimepicker3"
								data-toggle="datetimepicker">
								<div class="input-group-text">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend" id="datetimepicker4"
							data-target-input="nearest">
							<span class="input-group-text">End Date</span> <input type="text"
								id="enddate" class="form-control datetimepicker-input"
								data-toggle="datetimepicker" data-target="#datetimepicker4" />
							<div class="input-group-append" data-target="#datetimepicker4"
								data-toggle="datetimepicker">
								<div class="input-group-text">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</div>
					</div>
				</div>




				<div class="col-md-4" id="col3" style="text-align: right;">
					<div class="btn-group pull-right" style="padding: 0px !important;">
						<!-- <div class="glyph" style="padding-top: 0px; padding-right: 10px;">
							<div class="form-group">
								<div class="input-group-prepend" data-target-input="nearest">
									<div class="input-group-text">
										<button type="button" class="btn" name="fields"
											id="FieldsOption" data-toggle="modal" data-target="#Modal">
											<i class="fa fa-filter" style="font-size: 20px"></i>
										</button>
									</div>
								</div>
							</div>
						</div> -->
						<div class="glyph" style="padding-top: 0px;">
							<div class="dropdown" Style="margin-right: 10px; height: 30px;">
								<button class="btn btn-secondary dropdown-toggle" type="button"
									id="notifactionDropdown" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">Menu</button>

								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="dropdownMenuButton" id="dropRight"
									Style="width: 10px;">
									<li class="dropdown-submenu"><a class="dropdown-item"
										id="edit" href="#">Edit</a></li>
									<li class="dropdown-submenu"><a class="dropdown-item"
										id="print" href="#">Print</a></li>
									<li class="dropdown-submenu"><a
										class="dropdown-item" id="export" href="#">Export<span
											class="caret" Style="padding-left: 30px;"></span></a>

										<ul class="dropdown-menu dropdown-menu-left" id="dropLeft"
											Style="left: -12.5rem !important; max-height: max-content !important; max-width: max-content !important;">

											<li><a class="dropdown-item" id="allExport" href="#">All</a></li>

											<li><a class="dropdown-item" id="mapExport" href="#">Map</a></li>

											<li><a class="dropdown-item" id="gridExport" href="#">Grid</a></li>

											<li><a class="dropdown-item" id="chartsExport" href="#">Charts</a></li>

										</ul></li>

									<li class="dropdown-submenu"><a class="dropdown-item"
										id="pdf" href="#">PDF</span></a></li>
									<li class="dropdown-submenu"><a class="dropdown-item"
										id="setup" href="#">Setup Auto Email</a></li>
									<li class="dropdown-submenu"><a class="dropdown-item"
										id="addCol" href="#">Add Column</a></li>
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
			
			<div class="wrapper" style="margin-top: -10px;">
					<div class="panel-group" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel panel-default" style="margin-bottom: 3px;">
							<div class="panel-heading" role="tab" id="headingOne">
								<h4 class="panel-title">
									<a role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseOne"
										aria-expanded="false" aria-controls="collapseOne"> GIS </a>
								</h4>
							</div>
							<!-- Map to be placed here -->
						</div>

						<!-- To be determined later for GRID TABLE -->
						<div class="panel panel-default" style="margin-bottom: 3px;">
							<div class="panel-heading " role="tab" id="headingTwo">
								<h4 class="panel-title">
									<a role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseTwo"
										aria-expanded="true" aria-controls="collapseTwo"> Grid
										Table </a>
								</h4>
							</div>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse show"
							role="tabpanel" aria-labelledby="headingTwo">
							<div class="panel-body">
								<!-- /.card-header -->
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
														<div class="input-group-prepend">
															<div id="loaderDiv" style="display: none;">
																<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
															</div>
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
															<tr class="header">

																<th>Element ID
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul class="dropdown-menu filter-dropdown-ul">

																		</ul>
																</li>
																</th>

																<th>Element
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul class="dropdown-menu filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																<th>ALM Trans Type
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul class="dropdown-menu filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																<th>Discovered Trans Type
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																<th>Start Date
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																<th>End Date
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>

																<th>From Site
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																<th>To Site
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																	<th>From Node
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																	<th>To Node
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																
																<th>From Circle
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																	<th>To Circle
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																<th>Approved By
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																	<th>Modified By
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																
																<th>Sent To ALM
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
																</li>
																</th><th>ALM Approval Status
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul
																			class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																		</ul>
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
																<th><input type="text" class="almgrid-search"
																	placeholder="Search"></th>
																<th><input type="text" class="almgrid-search"
																	placeholder="Search"></th>


															</tr>
														</thead>
														<tbody>

														</tbody>

													</table>
												</div>

												<hr>
												<div class="pagination-div" id="paginationDiv">
													<div class="row" id="paginationRow">
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

							</div>
						</div>

						<!-- To be determined later for CHARTS -->
						<div class="panel panel-default" style="margin-bottom: 3px;">
							<div class="panel-heading" role="tab" id="headingThree">
								<h4 class="panel-title">
									<a role="button" id="chartsPanel" data-toggle="collapse"
										data-parent="#accordion" href="#collapseThree"
										aria-expanded="false" aria-controls="collapseThree"> Charts
									</a>
								</h4>
							</div>
							<!-- Charts to be placed here  -->
							
						</div>

					</div>
				</div>
	</div>
</body>

<script>

$(document).ready(function() {
	//Set the default Date
	var date = new Date();
	date.setDate(date.getDate() - 2);


	date.setHours( 0,0,0,0 );

	var dateend = new Date();
	dateend.setDate(dateend.getDate() - 2);

	var c = Date.parse(date);
	$('#startdate').datetimepicker({
	    defaultDate:c
	});
	$('#enddate').datetimepicker({
	    defaultDate:dateend
	});

	//collapse active class	
	$('.panel-collapse').on('show.bs.collapse', function () {
	        
		    $(this).siblings('.panel-heading').removeClass('active');
		  });

	$('.panel-collapse').on('hide.bs.collapse', function () {
		    $(this).siblings('.panel-heading').addClass('active');
		  });


	NetworkTranscationsArray = ${TransactionsGrid}
	var almgrid = new AlmgridTable({
	    tableId: "gridTable",
	    dataArray: NetworkTranscationsArray,
	    selectCheckbox: false,
	    drawTableGrid :  function (tableId, dataArray) {
	    //let almgrid = this;
	       var tableBody = document.querySelector("#" + tableId + " tbody");
	       var columnLinkNb = this.columnLinkNb;
	       var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
	       var gridContainerId = tableId + "_container";
	       $(gridContainer).attr('id', gridContainerId);
	       $(tableBody).empty();
	       if (dataArray.length > 0) {
		       var ArrayKeys = Object.keys(dataArray[0]);
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

	$("#generate").click(  function() {
		  var startDate = document.getElementById("startdate").value;
		  var endDate = document.getElementById("enddate").value;
		  $("#gridTable").remove();
		  $("#tableGrid").append('<table id="gridTable"class="table table-striped table-bordered almgrid-table"><thead><tr class="header"><th>Element ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Element<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>ALM Trans Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Discovered Trans Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Start Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>End Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>From Site<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>To Site<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>From Node<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>To Node<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>From Circle<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>To Circle<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Approved By<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Modified By<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Sent To ALM<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>ALM Approval Status<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i"aria-hidden="true"></i></button><ulclass="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><tr><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th><th><input type="text" class="almgrid-search"placeholder="Search"></th></tr></thead><tbody></tbody></table>');

		  $.ajax({
				type : "GET",
				contentType: "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/GenerateNetworkTransactionsReport",
				
				data : {
					"startDate":$("#startdate").val(),
					"endDate":$("#enddate").val()
					

			},
			dataType : "json",
			success : function(data) {
					console.log(data.TransactionsGrid.length);
					if(data.TransactionsGrid.length==0){
						
						alert("No Data found between this two dates");
					}
					else{
					if(data.TransactionsGrid !=null ){
						networkTransactionGrid = data.TransactionsGrid;
						 almgrid = new AlmgridTable({
					         tableId: "gridTable",
					         dataArray: networkTransactionGrid,
					         selectCheckbox: false,
					         drawTableGrid :  function (tableId, dataArray) {
					         //let almgrid = this;
						        var tableBody = document.querySelector("#" + tableId + " tbody");
						        var columnLinkNb = this.columnLinkNb;
						        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
						        var gridContainerId = tableId + "_container";
						        $(gridContainer).attr('id', gridContainerId);
						        $(tableBody).empty();
						      
						        var ArrayKeys = Object.keys(dataArray[0]);
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

		         		
		         	
					}else{

						alert("No Data found between this two dates");
						}	
					}
					data=null;
				},
				error : function(error) {
					console.log(error.reponseText);
					console.log("Error"); 
					
				}
		});
	});
});




</script>
</html>