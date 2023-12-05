<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Speed Coverage Report</title>
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

<style>
.pos {
	position: relative;
	padding: 10px;
	text-align: center;
	font-size: 20px;
	color: #4169E1;
}

.wid {
	width: 100%;
}

.ui-autocomplete {
	max-height: 270px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 0px;
	font-size: 15px;
	z-index: 9999;
}

#clearButton {
	background-color: white;
	color: orange;
}

#clearButton:hover {
	background-color: orange;
	color: white;
}

.BTN {
	width: 90px !important;
}

.flex {
	display: flex;
	justify-content: center;
}

.flex-item+.flex-item {
	margin-left: 10px;
	margin-bottom: 5px;
}

#mapContainer {
	height: 700px;
}

.legendHeader {
	overflow: hidden;
	background-color: #08526d;
	padding: 10px 0px;
}

.btn-color {
	background-image: linear-gradient(to right top, #b3b3b3, #b6b6b7, #b8b9ba, #bbbdbd,
		#bdc0c0, #b1b5b5, #a5abaa, #9aa09f, #7f8685, #656e6c, #4c5655, #343f3e
		);
}

.legendContainer {
	height: 800px;
	position: relative;
}

.box {
	width: 100%;
	height: 100%;
	position: absolute;
	top: 20px;
	left: 0;
}

.stack-top {
	z-index: 3;
	margin: 70px;
}

.dot {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
}

.dotCount {
	height: 35px;
	width: 35px;
	border-radius: 50%;
	display: inline-block;
}

.downloadGreen {
	width: 0;
	height: 0;
	border-top: 20px solid green;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadBlue {
	width: 0;
	height: 0;
	border-top: 20px solid #0080ff;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadYellow {
	width: 0;
	height: 0;
	border-top: 20px solid #FFDC00;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadDarkRed {
	width: 0;
	height: 0;
	border-top: 20px solid #4D0207;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.downloadRed {
	width: 0;
	height: 0;
	border-top: 20px solid red;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
	-webkit-transform: rotate(360deg);
}

.uploadGreen {
	width: 0;
	height: 0;
	border-bottom: 20px solid green;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadBlue {
	width: 0;
	height: 0;
	border-bottom: 20px solid #0080ff;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadYellow {
	width: 0;
	height: 0;
	border-bottom: 20px solid #FFDC00;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadDarkRed {
	width: 0;
	height: 0;
	border-bottom: 20px solid #4D0207;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.uploadRed {
	width: 0;
	height: 0;
	border-bottom: 20px solid red;
	border-left: 10px solid transparent;
	border-right: 10px solid transparent;
}

.dotYellow {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: #FFDC00;
}

.dotBlue {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: #0080ff;
}

.dotRed {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: red;
}

.green {
	background: green;
}

.redDark {
	background: #4D0207;
}

.blue {
	background: #0080ff;
}

.yellow {
	background: #FFDC00;
}

.red {
	background: red;
}

.pink {
	background: #FF00FF;
}

.purple {
	background: #8A2BE2;
}

.cadr {
	border: 0.01em solid grey;
}

.cadr2 {
	border: 0.1em solid #808080;
	padding: 40px;
}

.title {
	margin: 5px 0px;
	font-size: 25px;
	font-weight: 600;
	font-family: 'Times New Roman', Times, serif;
}

.canvas-style {
	height: 650px;
}

.canvas-style2 {
	height: 400px !important;
}

.canvas-style3 {
	height: 650px !important;
}

/*This will style the icon button for chart*/
.buttonStyle {
	font-size: 20px;
	color: #444444;
	margin-top: 10px;
	background: none;
	border: none;
}
/*This should make them change their color when they are hovered*/
.buttonStyle:hover {
	color: #08526d;
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

.tags-input {
	width: 100%;
}

.tags-input input {
	border: none;
	min-height: 100px;
}

.tags-input input:focus {
	border: none;
	box-shadow: none;
	outline: none;
	padding-top: 0px !important;
	padding-bottom: 0px !important;
}

.tags-input .tag {
	padding-top: 2px;
	padding-bottom: 2px;
	border: 2px solid #5f6368;
	border-radius: 10px;
	padding-left: 7px;
	padding-right: 7px;
	font-size: 12px;
	color: black;
	margin: 5px;
	display: inline-block;
}

.tags-input .tag .text {
	margin-right: 5px;
}

.tags-input .tag .close {
	border-radius: 50%;
	min-height: 20px;
	padding-left: 4px;
	cursor: -webkit-grabbing;
	cursor: grabbing;
	padding-right: 4px;
	color: black;
	font-weight: bolder;
}

.bootstrap-tagsinput .tag [data-role="remove"]:after {
	content: "x";
	padding: 0px 5px;
}
</style>
<body>
	<c:set var="pg" value="report" scope="session" />
	<jsp:include page="../header.jsp"></jsp:include>

	<div Style="left: 0; bottom: 0;" id="Revenue Div">

		<div class="container-fluid">
			<div class="row">
				<p></p>
			</div>

			<div class="row second">

				<div class="col-md-2" style="display: flex;">
					<div class="form-group " style="margin-left: 15px;">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color: green">Speed
								and Coverage Report</span>
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
						<div class="glyph" style="padding-top: 0px; padding-right: 10px;">
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
						</div>
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
										class="dropdown-item dropdown-toggle" id="export" href="#">Export<span
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



			<div class="container-fluid">
				<!-- Modal Filter Options -->
				<div class="modal fade custom-class-assignedto-modal" id="Modal"
					tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
					aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered"
						role="document" id="model">
						<div class="modal-content" style="width: 700px !important;">
							<div class="modal-header" style="background-color: #FF4F4F;">
								<h5 class="modal-title" id="myModalLabel1"
									style="font-weight: bold; color: #3C1596; position: relative; top: 4px;">
									Filter Options</h5>

								<button type="button"
									style="border: none; outline: none; position: relative; top: 7px;"
									name="closePopup" class="close" data-dismiss="modal"
									id="mapoperations">
									<i class='fa fa-times'></i>
								</button>

								<a class="close modalMinimize ml-3"
									style="position: relative; top: 7px;"> <i
									class='fa fa-minus icon-to-change'></i>
								</a>



							</div>
							<div class="modal-body">
								<ul class="nav nav-tabs" id="myTab" role="tablist"
									style="background-color: #00757C;">
									<li class="nav-item"><a class="nav-link active "
										id="revenueColor-tab" style="color: gold;" data-toggle="tab"
										href="#revenueColor" role="tab" aria-controls="revenueColor"
										aria-selected="true">REVENUE COLOR</a></li>
									<li class="nav-item"><a class="nav-link "
										id="notExistingSite-tab" style="color: gold;"
										data-toggle="tab" href="#notExistingSites" role="tab"
										aria-controls="notExistingSites" aria-selected="false">OTHER
											SITES</a></li>
								</ul>
								<div class="tab-content">


									<div class="tab-pane active" id="revenueColor" role="tabpanel"
										aria-labelledby="revenueColor-tab">

										<table id="sitesTech">

											<tr>
												<th style="position: relative; top: 5px; left: 10px;"></th>
												<th style="position: relative; top: 5px; left: 10px;"></th>
												<th style="position: relative; top: 5px; left: 10px;"></th>
												<th style="position: relative; top: 5px; left: 10px;"></th>

											</tr>

											<tr>

												<td style="position: relative; top: -8px; left: 50px;"><input
													style="position: relative; top: 11px;" type="checkbox"
													name="enable" id="enable" /></td>

												<td style="position: relative; left: 60px;"><label
													style="color: black; font-weight: bold; font-size: 2ex;">ENABLE</label></td>

												<td style="position: relative; top: -8px; left: 160px;"><input
													style="position: relative; top: 11px;" type="checkbox"
													name="disable" id="disable" checked /></td>

												<td style="position: relative; left: 170px;"><label
													style="color: black; font-weight: bold; font-size: 2ex;">DISABLE</label></td>


											</tr>




										</table>

										<div>
											<p></p>
											<form>
												<div class="table-responsive-sm " id="revTable">
													<h5 id="alertMsg"
														style="font-weight: bold; color: red; position: relative; top: 4px; right: -150px;"></h5>

													<table id="revenueColorTable"
														class="table table-striped table-bordered table-sm"
														style="display: fixed; overflow-y: auto;">
														<thead>
															<tr>
																<th style="background-color: #00757C; color: gold;"><div
																		style="width: 80px;">
																		<button type="button" id="selectAll"
																			style="margin-left: 10px" class="main">
																			<span class="sub"></span>Select
																		</button>
																	</div></th>
																<th
																	style="background-color: #00757C; color: gold; width: 232px">From</th>
																<th
																	style="background-color: #00757C; color: gold; width: 232px">To</th>
																<th
																	style="background-color: #00757C; color: gold; width: 232px"><div
																		style="width: 200px">Color</div></th>

															</tr>

															<tr style='background-color: #0080FF'>
																<td style='text-align: center;'><input
																	type='checkbox' name='record' style='margin-top: 12px;'></td>
																<td name='from'><input type='text' name='fromValue'
																	style='width: 150px;'
																	class='ui-widget ui-widget-content ui-corner-all form-control'
																	data-default="0" value="0" /></td>
																<td name='to'><input type='text' name='toValue'
																	style='width: 150px;'
																	class='ui-widget ui-widget-content ui-corner-all form-control'
																	data-default="350" value="350"></td>
																<td name='color'><select
																	class='ui-widget ui-corner-all form-control'>
																		<option value="#0080FF" selected>Blue</option>
																		<option value="#FFDC00">Yellow</option>
																		<option value="red">Red</option>
																		<option value="#FF00FF">Pink</option>
																		<option value="#8A2BE2">Purple</option>


																</select></td>

															</tr>




															<tr style='background-color: #FFDC00'>
																<td style='text-align: center;'><input
																	type='checkbox' name='record' style='margin-top: 12px;'></td>
																<td name='from'><input type='text' name='fromValue'
																	style='width: 150px;'
																	class='ui-widget ui-widget-content ui-corner-all form-control'
																	data-default="351" value="351" /></td>
																<td name='to'><input type='text' name='toValue'
																	style='width: 150px;'
																	class='ui-widget ui-widget-content ui-corner-all form-control'
																	data-default="700" value="700"></td>
																<td name='color'><select
																	class='ui-widget ui-corner-all form-control'>
																		<option value="#0080FF">Blue</option>
																		<option value="#FFDC00" selected>Yellow</option>
																		<option value="red">Red</option>
																		<option value="#FF00FF">Pink</option>
																		<option value="#8A2BE2">Purple</option>


																</select></td>

															</tr>

															<tr style='background-color: red'>
																<td style='text-align: center;'><input
																	type='checkbox' name='record' style='margin-top: 12px;'></td>
																<td name='from'><input type='text' name='fromValue'
																	style='width: 150px;'
																	class='ui-widget ui-widget-content ui-corner-all form-control'
																	data-default="701" value="701" /></td>
																<td name='to'><input type='text' name='toValue'
																	style='width: 150px;'
																	class='ui-widget ui-widget-content ui-corner-all form-control'
																	data-default="999" value="999"></td>
																<td name='color'><select
																	class='ui-widget ui-corner-all form-control'>
																		<option value="#0080FF">Blue</option>
																		<option value="#FFDC00">Yellow</option>
																		<option value="red" selected>Red</option>
																		<option value="#FF00FF">Pink</option>
																		<option value="#8A2BE2">Purple</option>


																</select></td>

															</tr>


														</thead>
														<tbody>
														</tbody>
													</table>
												</div>

												<button type="button" class="btn btn-primary"
													style="color: white;" onclick="addRow()">Add Row</button>
												<button type="button" class="btn btn-primary deleteRow"
													style="color: white;">Delete Row</button>
												<button type="button" class="btn btn-primary"
													style="color: white;" onclick="applyChanges()">Apply
													Change</button>
												<button type="button" class="btn btn-primary"
													style="color: white;" onclick="resetToDefault()">Reset</button>
											</form>
										</div>
									</div>

									<div class="tab-pane" id="notExistingSites" role="tabpanel"
										aria-labelledby="notExistingSite-tab">
										<table id="notExistingSitesTable"
											style="width: 70%; height: 70%; margin-top: 80px;">
											<tr>
												<th style="position: relative; top: 15px; left: 10px;"></th>
												<th style="position: relative; top: 15px; left: 10px;"></th>
												<th style="position: relative; top: 15px; left: 10px;"></th>
												<th style="position: relative; top: 15px; left: 10px;"></th>
											</tr>

											<tr>
												<td style="position: relative; top: -8px; left: 50px;"><input
													style="position: relative; top: 11px;" type="checkbox"
													name="warehouseAllSites" id="warehouseAllSites" /></td>
												<td style="position: relative; left: 60px;"><label
													style="color: black; font-weight: bold; font-size: 2ex;">Warehouse
														Sites</label></td>
												<td style="position: relative; top: -8px; left: 160px;"><input
													style="position: relative; top: 11px;" type="checkbox"
													name="prepaidRevenueSites" id="prepaidRevenueSites" checked /></td>
												<td style="position: relative; left: 170px;"><label
													style="color: black; font-weight: bold; font-size: 2ex;">Prepaid
														Revenue Sites</label></td>
											</tr>

											<tr>
												<td style="position: relative; top: -8px; left: 50px;"><input
													style="position: relative; top: 11px;" type="checkbox"
													name="allSites" id="allSites" /></td>
												<td style="position: relative; left: 60px;"><label
													style="color: black; font-weight: bold; font-size: 2ex;">All
														Sites</label></td>
											</tr>



										</table>
									</div>
								</div>

								<div class="modal-footer"></div>
							</div>
						</div>
					</div>
				</div>

				<div class="row second">

					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">

								<select id="period" class="form-control text-input"
									style="height: 37px;">
									<option value="null">No Period Selected</option>
									<option value="Daily">Daily</option>
									<option value="Weekly">Weekly</option>
									<option value="Monthly">Monthly</option>
									<option value="Accu" selected>Accumulated</option>


								</select>
							</div>
						</div>
					</div>

					<div class="col-md-auto">

						<div class="form-group">
							<div class="input-group-prepend" data-target-input="nearest">
								<div class="input-group-text">

									<input type="checkbox" id="byCoverage" name="byCoverage"
										${byCoverage} /> <span style="margin-left: 10px;">By
										Signal Strength</span>
								</div>
							</div>
						</div>

					</div>

					<div class="col-md-auto">

						<div class="form-group">
							<div class="input-group-prepend" data-target-input="nearest">
								<div class="input-group-text">

									<input type="checkbox" id="byUpload" name="byUpload"
										${byUpload}> <span style="margin-left: 10px;">By
										Upload Speed</span>
								</div>
							</div>
						</div>

					</div>

					<div class="col-md-auto">

						<div class="form-group">
							<div class="input-group-prepend" data-target-input="nearest">
								<div class="input-group-text">

									<input type="checkbox" id="byDownload" name="byDownload"
										${byDownload}> <span style="margin-left: 10px;">By
										Download Speed</span>
								</div>
							</div>
						</div>

					</div>
					<div class="col-md-auto">

						<div class="form-group">
							<div class="input-group-prepend" data-target-input="nearest">
								<div class="input-group-text">

									<input type="checkbox" value="0" id="Max" name="Max"> <span
										style="margin-left: 10px;">Maximum</span>
								</div>
							</div>
						</div>

					</div>

					<div class="col-md-auto">

						<div class="form-group">
							<div class="input-group-prepend" data-target-input="nearest">
								<div class="input-group-text">

									<input type="checkbox" value="0" id="Min" name="Min"> <span
										style="margin-left: 10px;">Minimum</span>
								</div>
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
										aria-expanded="true" aria-controls="collapseOne"> GIS </a>
								</h4>
							</div>
							<div id="collapseOne" class="panel-collapse collapse show"
								role="tabpanel" aria-labelledby="headingOne">
								<div class="panel-body">
									<div class="legendContainer">
										<div class="card-body">
											<div class="box stack-top" id="legendDiv"
												style="overflow-y: scroll; position: relative; top: 170px; width: 300px; float: left; height: 320px; background: white; margin: 37px; display: none">

												<div class="legendHeader" id="legendHeader">

													<h6
														style="color: white; font-weight: bold; font-size: 3ex; display: inline-block; position: relative; top: 5px; left: 10px;">Legends</h6>

													<button
														style="background-color: transparent; color: white; font-weight: bold; outline: none; border: none; position: relative; left: 100px; top: 2px;"
														onClick="SelectUnselectAll()" id="selectUnselect">Unselect
														All</button>
												</div>

												<div id="tableDiv"></div>
											</div>


										</div>

										<div class="card-body">
											<div class="box" id="mapContainer">
												<canvas class="mt-5 w-100" id="doughnutChart"
													style="display: none;"></canvas>
											</div>
										</div>
									</div>
								</div>
							</div>
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
															<div id="loaderDiv" style="display: none;">
																<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
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

																<th>CID
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul class="dropdown-menu filter-dropdown-ul">

																		</ul>
																</li>
																</th>

																<th>LAC
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul class="dropdown-menu filter-dropdown-ul">

																		</ul>
																</li>
																</th>
																<th>Start Date
																	<li class="filter-dropdown dropdown">
																		<button class="almgrid-filter" data-toggle="dropdown">
																			<i class="fa fa-list almgrid-filter-i"
																				aria-hidden="true"></i>
																		</button>
																		<ul class="dropdown-menu filter-dropdown-ul">

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

																<th>Average Coverage Signal
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

																<th>Coverage Signal Classification
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
																<th>Average Upload Speed
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

																<th>Average Download Speed
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
																<th>Technology
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
										aria-expanded="true" aria-controls="collapseThree"> Charts
									</a>
								</h4>
							</div>

							<div id="collapseThree" class="panel-collapse collapse show"
								role="tabpanel" aria-labelledby="headingThree">
								<div class="panel-body" style="background: #f1f4f7 !important">
									<div class="card-body">

										<div class="container-fluid-responsive">

											<br>


											<div class="row" id="coveragePieChart"
												style="background: white !important; border: 3px groove #FDFEFE;">
												<div class="col-md-12">

													<div class="card card-primary card-tabs cards-margin ">
														<div class="card-body ">

															<div class="tab-content" id="custom-tabs-one-tabContent">

																<div class="tab-pane fade show active" role="tabpanel">

																	<div class="card-group"
																		style="border: 3px groove #FDFEFE;">

																		<div class="col-md-6 ">
																			<div class="card card-primary card-tabs ">

																				<div class="card-body ">
																					<div class="tab-content"
																						id="custom-tabs-one-tabContent">
																						<div class="tab-pane fade show active"
																							id="custom-tabs-one-home" role="tabpanel"
																							aria-labelledby="custom-tabs-one-home-tab">
																							<div id="countChartPie" style="height: 400px;"></div>




																						</div>
																					</div>
																				</div>



																			</div>
																		</div>


																		<div class="col-md-6 ">
																			<div id="filter_Chart_Piez"
																				style="background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); width: 93%; margin-left: 20px;">
																				<div id="filterChartPiez">
																					<div style="font-weight: bold; margin-top: 20px;"
																						class="pos">Pie Charts Filter</div>
																					<!-- startdate -->

																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Start
																						Date</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend"
																									id="datetimepicker11"
																									data-target-input="nearest">
																									<!-- <div class="input-group-text">Start Date</div> -->
																									<input type="text" id="startdate5"
																										class="form-control datetimepicker-input"
																										data-toggle="datetimepicker"
																										data-target="#datetimepicker11" />
																									<div class="input-group-append"
																										data-target="#datetimepicker11"
																										data-toggle="datetimepicker">
																										<div class="input-group-text">
																											<i class="fa fa-calendar"></i>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																					</div>

																					<!-- enddate -->
																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">End
																						Date</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend"
																									id="datetimepicker12"
																									data-target-input="nearest">
																									<!-- <div class="input-group-text">End Date</div> -->
																									<input type="text" id="enddate5"
																										class="form-control datetimepicker-input"
																										data-toggle="datetimepicker"
																										data-target="#datetimepicker12" />
																									<div class="input-group-append"
																										data-target="#datetimepicker12"
																										data-toggle="datetimepicker">
																										<div class="input-group-text">
																											<i class="fa fa-calendar"></i>
																										</div>

																									</div>
																								</div>
																							</div>
																						</div>
																					</div>

																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Agents</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend">
																									<input type="text" id="agentPieChart"
																										value="${agentPieChart}"
																										class="form-control text-input"
																										style="margin-bottom: 20px;" />

																									<div class="input-group-append"
																										id=pieChartOpenPopup
																										onclick="pieChartOpenPopup()">
																										<div class="input-group-text">
																											<i class="fas fa-edit"></i>
																										</div>
																									</div>

																								</div>
																							</div>
																						</div>
																					</div>

																					<div
																						style="font-weight: bold; margin-right: 50px; margin-left: 50px;">Filter
																						options</div>
																					<div class="row">
																						<div class="col-md-12">
																							<select id="countPieChangeOptions"
																								style="margin-right: 50px; margin-left: 50px; width: 82%;"
																								class="form-control text-input"
																								onchange="changeFunc();" disabled>
																								<option value="null"></option>
																								<option value="isBetween">In between</option>
																							</select>
																						</div>
																					</div>
																					&nbsp;
																					<div id="countPieFilterDiv"
																						style="margin-right: 50px; margin-left: 50px;">
																						<div class="row">
																							<div class="col-md-12">
																								<select id="countPieSimSalesOpt"
																									class="form-control text-input"
																									onchange="changeSalesFunc();"
																									style="display: none; margin-bottom: 20px;"
																									disabled>
																									<option value="SIM_SALES">SIM Sales</option>

																								</select>
																							</div>
																						</div>
																						<div class="row" style="margin-bottom: 5px;">
																							<div class="col-md-12">
																								<span id="countPieTxtBtn" style="display: none;"><b>Insert
																										your values:</span>
																							</div>
																						</div>

																						<div class="row">
																							<input type="text"
																								class="form-control text-input"
																								id="minValPiesFltr" placeholder="Min:"
																								style="display: none; width: 100px; margin-bottom: 20px; margin-left: 16px;"
																								title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values">
																							<!-- display:none; -->

																							<input type="text"
																								class="form-control text-input"
																								id="maxValPiesFltr" placeholder="Max:"
																								style="display: none; width: 100px; margin-bottom: 20px; margin-left: 53px;"
																								title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">

																						</div>
																					</div>




																					<!-- apply button -->
																					<!-- <button onclick="filterChart()" >Filter Chart</button> -->
																					<div class="row flex">

																						<div class="flex-item">
																							<button type="button"
																								class="btn btn-primary BTN "
																								id="countPieClearFilter">Clear All</button>
																						</div>
																						<div class="flex-item">
																							<button type="button"
																								class="btn btn-primary BTN advanced-filter-submit"
																								id="countPieApplyFilter">Apply</button>
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

											<div class="row" id="DownloadSpeedPieChart"
												style="background: white !important; border: 3px groove #FDFEFE;">
												<div class="col-md-12">

													<div class="card card-primary card-tabs cards-margin ">
														<div class="card-body ">

															<div class="tab-content" id="custom-tabs-one-tabContent">

																<div class="tab-pane fade show active" role="tabpanel">

																	<div class="card-group"
																		style="border: 3px groove #FDFEFE;">


																		<div class="col-md-6 ">
																			<div class="card card-primary card-tabs ">

																				<!--  *************************** piechart chart tech  *************************** -->

																				<div class="card-body ">
																					<div class="tab-content"
																						id="custom-tabs-one-tabContent">
																						<div class="tab-pane fade show active"
																							id="custom-tabs-one-home" role="tabpanel"
																							aria-labelledby="custom-tabs-one-home-tab">
																							<div id="DownloadChartContainer"
																								style="height: 400px;"></div>
																						</div>

																					</div>
																				</div>

																			</div>



																		</div>
															
																		<div class="col-md-6 ">
																			<div id="filter_Chart_Piez"
																				style="background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); width: 93%; margin-left: 20px;">
																				<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->


																				<div id="filterChartPiez">
																					<div style="font-weight: bold; margin-top: 20px;"
																						class="pos">Pie Charts Filter</div>
																					<!-- startdate -->
																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Start
																						Date</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend"
																									id="datetimepicker13"
																									data-target-input="nearest">
																									<!-- <div class="input-group-text">Start Date</div> -->
																									<input type="text" id="startdate6"
																										class="form-control datetimepicker-input"
																										data-toggle="datetimepicker"
																										data-target="#datetimepicker13" />
																									<div class="input-group-append"
																										data-target="#datetimepicker13"
																										data-toggle="datetimepicker">
																										<div class="input-group-text">
																											<i class="fa fa-calendar"></i>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																					</div>

																					<!-- enddate -->
																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">End
																						Date</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend"
																									id="datetimepicker14"
																									data-target-input="nearest">
																									<!-- <div class="input-group-text">End Date</div> -->
																									<input type="text" id="enddate6"
																										class="form-control datetimepicker-input"
																										data-toggle="datetimepicker"
																										data-target="#datetimepicker14" />
																									<div class="input-group-append"
																										data-target="#datetimepicker14"
																										data-toggle="datetimepicker">
																										<div class="input-group-text">
																											<i class="fa fa-calendar"></i>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																					</div>

																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Agents</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend">
																									<input type="text" id="agentRegStatusChart"
																										value="${agentRegStatusChart}"
																										class="form-control text-input"
																										style="margin-bottom: 20px;" />

																									<div class="input-group-append"
																										id=regStatusChartOpenPopup
																										onclick="regStatusChartOpenPopup()">
																										<div class="input-group-text">
																											<i class="fas fa-edit"></i>
																										</div>
																									</div>

																								</div>
																							</div>
																						</div>
																					</div>

																					<div
																						style="font-weight: bold; margin-right: 50px; margin-left: 50px;">Filter
																						options</div>
																					<div class="row">
																						<div class="col-md-12">

																							<select id="regStatusPiechangeOptions"
																								style="margin-right: 50px; margin-left: 50px; width: 82%;"
																								class="form-control text-input"
																								onchange="changeFunc();" disabled>
																								<option value="null"></option>

																								<option value="isBetween">In between</option>

																							</select>

																						</div>
																					</div>
																					&nbsp;
																					<div id="regStatusPieFilterDiv"
																						style="margin-right: 50px; margin-left: 50px;">
																						<div class="row">
																							<div class="col-md-12">

																								<select id="regStatusPieSimSalesOpt"
																									class="form-control text-input"
																									onchange="changeSalesFunc();"
																									style="display: none; margin-bottom: 20px;"
																									disabled>
																									<option value="SIM_SALES">SIM Sales</option>


																								</select>

																							</div>
																						</div>
																						<div class="row" style="margin-bottom: 5px;">
																							<div class="col-md-12">
																								<span id="regStatusPieTxtBtn"
																									style="display: none;"><b>Insert
																										your values:</span>
																							</div>
																						</div>
																						<div class="row">
																							<input type="text"
																								class="form-control text-input"
																								id="minValRegStatFltr" placeholder="Min:"
																								style="display: none; width: 100px; margin-bottom: 20px; margin-left: 16px;"
																								title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values">
																							<!-- display:none; -->

																							<input type="text"
																								class="form-control text-input"
																								id="maxValRegStatFltr" placeholder="Max:"
																								style="display: none; width: 100px; margin-bottom: 20px; margin-left: 53px;"
																								title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">

																						</div>
																					</div>
																				
																					<!-- apply button -->
																					<!-- <button onclick="filterChart()" >Filter Chart</button> -->
																					<div class="row flex">

																						<div class="flex-item">
																							<button type="button"
																								class="btn btn-primary BTN "
																								id="regStatPieClearFilter">Clear All</button>
																						</div>
																						<div class="flex-item">
																							<button type="button"
																								class="btn btn-primary BTN advanced-filter-submit"
																								id="regStatPieApplyFilter">Apply</button>
																						</div>
																					</div>

																				</div>
																			</div>
																			<!--  ***************************  end filter for chart  *************************** -->
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>

												</div>
											</div>
											<br>
											<div class="row" id="UploadSpeedPieChart"
												style="background: white !important; border: 3px groove #FDFEFE;">
												<div class="col-md-12">

													<div class="card card-primary card-tabs cards-margin ">
														<div class="card-body ">

															<div class="tab-content" id="custom-tabs-one-tabContent">

																<div class="tab-pane fade show active" role="tabpanel">

																	<div class="card-group"
																		style="border: 3px groove #FDFEFE;">


																		<div class="col-md-6 ">
																			<div class="card card-primary card-tabs ">

																				<!--  *************************** piechart chart tech  *************************** -->

																				<div class="card-body ">
																					<div class="tab-content"
																						id="custom-tabs-one-tabContent">
																						<div class="tab-pane fade show active"
																							id="custom-tabs-one-home" role="tabpanel"
																							aria-labelledby="custom-tabs-one-home-tab">
																							<div id="UploadChartContainer"
																								style="height: 400px;"></div>
																						</div>

																					</div>
																				</div>

																			</div>



																		</div>


																		<div class="col-md-6 ">
																			<div id="filter_Chart_Piez"
																				style="background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); width: 93%; margin-left: 20px;">
																				<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->


																				<div id="filterChartPiez">
																					<div style="font-weight: bold; margin-top: 20px;"
																						class="pos">Pie Charts Filter</div>
																					<!-- startdate -->
																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Start
																						Date</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend"
																									id="datetimepicker13"
																									data-target-input="nearest">
																									<!-- <div class="input-group-text">Start Date</div> -->
																									<input type="text" id="startdate6"
																										class="form-control datetimepicker-input"
																										data-toggle="datetimepicker"
																										data-target="#datetimepicker13" />
																									<div class="input-group-append"
																										data-target="#datetimepicker13"
																										data-toggle="datetimepicker">
																										<div class="input-group-text">
																											<i class="fa fa-calendar"></i>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																					</div>

																					<!-- enddate -->
																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">End
																						Date</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend"
																									id="datetimepicker14"
																									data-target-input="nearest">
																									<!-- <div class="input-group-text">End Date</div> -->
																									<input type="text" id="enddate6"
																										class="form-control datetimepicker-input"
																										data-toggle="datetimepicker"
																										data-target="#datetimepicker14" />
																									<div class="input-group-append"
																										data-target="#datetimepicker14"
																										data-toggle="datetimepicker">
																										<div class="input-group-text">
																											<i class="fa fa-calendar"></i>
																										</div>
																									</div>
																								</div>
																							</div>
																						</div>
																					</div>

																					<div
																						style="font-weight: bold; margin-left: 50px; margin-right: 50px;">Agents</div>
																					<div class="row">
																						<div class="col-md-12">
																							<div class="form-group"
																								style="margin-left: 50px; margin-right: 50px;">
																								<div class="input-group-prepend">
																									<input type="text" id="agentRegStatusChart"
																										value="${agentRegStatusChart}"
																										class="form-control text-input"
																										style="margin-bottom: 20px;" />

																									<div class="input-group-append"
																										id=regStatusChartOpenPopup
																										onclick="regStatusChartOpenPopup()">
																										<div class="input-group-text">
																											<i class="fas fa-edit"></i>
																										</div>
																									</div>

																								</div>
																							</div>
																						</div>
																					</div>

																					<div
																						style="font-weight: bold; margin-right: 50px; margin-left: 50px;">Filter
																						options</div>
																					<div class="row">
																						<div class="col-md-12">

																							<select id="regStatusPiechangeOptions"
																								style="margin-right: 50px; margin-left: 50px; width: 82%;"
																								class="form-control text-input"
																								onchange="changeFunc();" disabled>
																								<option value="null"></option>

																								<option value="isBetween">In between</option>

																							</select>

																						</div>
																					</div>
																					&nbsp;
																					<div id="regStatusPieFilterDiv"
																						style="margin-right: 50px; margin-left: 50px;">
																						<div class="row">
																							<div class="col-md-12">

																								<select id="regStatusPieSimSalesOpt"
																									class="form-control text-input"
																									onchange="changeSalesFunc();"
																									style="display: none; margin-bottom: 20px;"
																									disabled>
																									<option value="SIM_SALES">SIM Sales</option>


																								</select>

																							</div>
																						</div>
																						<div class="row" style="margin-bottom: 5px;">
																							<div class="col-md-12">
																								<span id="regStatusPieTxtBtn"
																									style="display: none;"><b>Insert
																										your values:</span>
																							</div>
																						</div>
																						<div class="row">
																							<input type="text"
																								class="form-control text-input"
																								id="minValRegStatFltr" placeholder="Min:"
																								style="display: none; width: 100px; margin-bottom: 20px; margin-left: 16px;"
																								title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values">
																							<!-- display:none; -->

																							<input type="text"
																								class="form-control text-input"
																								id="maxValRegStatFltr" placeholder="Max:"
																								style="display: none; width: 100px; margin-bottom: 20px; margin-left: 53px;"
																								title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">

																						</div>
																					</div>
																				

																					<!-- apply button -->
																					<!-- <button onclick="filterChart()" >Filter Chart</button> -->
																					<div class="row flex">

																						<div class="flex-item">
																							<button type="button"
																								class="btn btn-primary BTN "
																								id="regStatPieClearFilter">Clear All</button>
																						</div>
																						<div class="flex-item">
																							<button type="button"
																								class="btn btn-primary BTN advanced-filter-submit"
																								id="regStatPieApplyFilter">Apply</button>
																						</div>
																					</div>

																				</div>
																			</div>
																			<!--  ***************************  end filter for chart  *************************** -->
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>

												</div>
											</div>
											<br>
											<div id="noAccuCharts"></div>

										</div>
									</div>
								</div>
								<!-- end of charts-->

							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script>
var downloadArray=[];
var uploadArray=[];
var coverageArray=[];

function initMap() {
		
		    
		divTablee= document.getElementById("tableDiv").innerHTML;
		divRevTable =document.getElementById("revTable").innerHTML;
		
		// Define the Center
		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
		
		
	  var map = new google.maps.Map(document.getElementById("mapContainer"), {
	    center:Nairobi ,
	    zoom:6
	    
	  });

	  document.getElementById("mapContainer").innerHTML ="";

		var map = new google.maps.Map(document.getElementById("mapContainer"), {

			 mapTypeControl: false,
			 center:Nairobi ,
			 zoom: 6,
			 mapTypeControl: true,
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
						 
						 style: 'mapbox://styles/mapbox/streets-v11',
						 fullscreenControl: true,
						 });

		
	

		
		
	 
	   	

	  //Add buttons on map
		const centerControlDiv = document.createElement("div");
	    LegendControl(centerControlDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	    ShowHideDiv();			 

		 coverageArray=${coverage_GISquey};
		 uploadArray=${upload_GISquey};
		 downloadArray=${download_GISquey};
		
		if(coverageArray.length>0){
			coveragePieChart(coverageArray);
			$("#byCoverage").prop("checked", true);
			AddSelectedSignalColor(coverageArray,map);
		}
		if(uploadArray.length>0){
			uploadPieChart(uploadArray)
			$("#byUpload").prop("checked", true);
			AddSelectedUpSpeedColor(uploadArray,map);
		}
		if(downloadArray.length>0){
			downloadPieChart(downloadArray);
			$("#byDownload").prop("checked", true);
			AddSelectedDownSpeedColor(downloadArray,map);
		}
		
		  }




///set default date in start and end date 
// Set the default Date
const date = new Date();
date.setDate(date.getDate() - 1);


var dateend = new Date();
dateend.setDate(dateend.getDate());
var c = Date.parse(date);
$('#startdate').datetimepicker({
    defaultDate:c
});
$('#enddate').datetimepicker({
    defaultDate:dateend
});


///drag the legend
$( "#legendDiv" ).draggable(); 
// Changing The startdate and enddate when choosing weekly or  monthly or daily
$('#period').change(function() {

	var startDate;
	var a; var d;
    if ($(this).val() === 'Weekly') {
        
    	 startDate = $("#enddate").val();
        d = new Date(startDate);
    	d.setDate(d.getDate() - 7);
    	d.setHours( 0,0,0,0 );
        a=d.toLocaleString();
    	$("#startdate").val(a).trigger('change');
      	
    }

    else if ($(this).val() === 'Daily') {
        
   	 startDate = $("#enddate").val();
       d = new Date(startDate);
       d.setHours( 0,0,0,0 );
       a=d.toLocaleString();
   	$("#startdate").val(a).trigger('change');
     	
   }
    else if ($(this).val() === 'Monthly') {
    	 startDate = $("#enddate").val();
    	 d = new Date(startDate);
    	 d.setMonth(d.getMonth() - 3);
    	 d.setHours( 0,0,0,0 );
    	 a=d.toLocaleString();
    	$("#startdate").val(a).trigger('change');
    	
    }

});




$("#byCoverage").change(function() {

	
	if(byCoverage.checked==true){
		
			var Nairobi=new google.maps.LatLng(-1.286389,36.817223);            		
		  	 document.getElementById("mapContainer").innerHTML ="";
		      var map = new google.maps.Map(document.getElementById("mapContainer"), {

		    	  mapTypeControl: false,
		  		  center:Nairobi ,
		  		  zoom: 6,
		  			 mapTypeControl: true,
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
		  						 
		  						 style: 'mapbox://styles/mapbox/streets-v11',
		  						 fullscreenControl: true,
		  						 });


		      AddSelectedSignalColor(coverageArray,map);

			     
			    

					if(byDownload.checked==true){
						 $("#DownLoadLegend").remove();
							$("#DownLoadLegend1").remove();
							$("#DownLoadLegend2").remove();
							DeleteMarkers("download");
						 AddSelectedDownSpeedColor(downloadArray,map);
						
						}
					if(byUpload.checked==true){
						$("#upLoadLegend").remove();
						$("#upLoadLegend1").remove();
						$("#upLoadLegend2").remove();
						DeleteMarkers("upload");
						AddSelectedUpSpeedColor(uploadArray,map);
						
					}


		      

					//Add legend button under zoom control on map
			  		const centerControlDiv = document.createElement("div");
			  	    LegendControl(centerControlDiv, map);
			  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

			  	    const DefaultZoomDiv = document.createElement("div");
			  	    DefaultZoomControl(DefaultZoomDiv, map);
			  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);
		
	}else{
		$("#coverageLegend").remove();
		$("#coverageLegend1").remove();
		$("#coverageLegend2").remove();
		DeleteMarkers("signal");

		}

});


$("#byUpload").change(function() {


	
	if(byUpload.checked==true){

			var Nairobi=new google.maps.LatLng(-1.286389,36.817223);            		
		  	 document.getElementById("mapContainer").innerHTML ="";
		      var map = new google.maps.Map(document.getElementById("mapContainer"), {

		    	  mapTypeControl: false,
		  		  center:Nairobi ,
		  		  zoom: 6,
		  			 mapTypeControl: true,
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
		  						 
		  						 style: 'mapbox://styles/mapbox/streets-v11',
		  						 fullscreenControl: true,
		  						 });


		     AddSelectedUpSpeedColor(uploadArray,map);
		    

				if(byDownload.checked==true){
					 $("#DownLoadLegend").remove();
						$("#DownLoadLegend1").remove();
						$("#DownLoadLegend2").remove();
						DeleteMarkers("download");
					 AddSelectedDownSpeedColor(downloadArray,map);
					}
				if(byCoverage.checked==true){
					$("#coverageLegend").remove();
					$("#coverageLegend1").remove();
					$("#coverageLegend2").remove();
					DeleteMarkers("signal");
					 AddSelectedSignalColor(coverageArray,map);
				}

			

					//Add legend button under zoom control on map
			  		const centerControlDiv = document.createElement("div");
			  	    LegendControl(centerControlDiv, map);
			  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

			  	    const DefaultZoomDiv = document.createElement("div");
			  	    DefaultZoomControl(DefaultZoomDiv, map);
			  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);


		}else{


			$("#upLoadLegend").remove();
			$("#upLoadLegend1").remove();
			$("#upLoadLegend2").remove();
			DeleteMarkers("upload");

			}

});

$("#byDownload").change(function() {


	
	if(byDownload.checked==true){
	
	
			var Nairobi=new google.maps.LatLng(-1.286389,36.817223);            		
		  	 document.getElementById("mapContainer").innerHTML ="";
		      var map = new google.maps.Map(document.getElementById("mapContainer"), {

		    	  mapTypeControl: false,
		  		  center:Nairobi ,
		  		  zoom: 6,
		  			 mapTypeControl: true,
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
		  						 
		  						 style: 'mapbox://styles/mapbox/streets-v11',
		  						 fullscreenControl: true,
		  						 });

		    //Add buttons on map
				const centerControlDiv = document.createElement("div");
			    LegendControl(centerControlDiv, map);
			    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

			    const DefaultZoomDiv = document.createElement("div");
			    DefaultZoomControl(DefaultZoomDiv, map);
			    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);
				
			  // ShowHideDiv();	
			
			AddSelectedDownSpeedColor(downloadArray,map);

			if(byUpload.checked==true){
				$("#upLoadLegend").remove();
				$("#upLoadLegend1").remove();
				$("#upLoadLegend2").remove();
				DeleteMarkers("upload");
				AddSelectedUpSpeedColor(uploadArray,map)
				}
			if(byCoverage.checked==true){
				$("#coverageLegend").remove();
				$("#coverageLegend1").remove();
				$("#coverageLegend2").remove();
				DeleteMarkers("signal");
				 AddSelectedSignalColor(coverageArray,map);
			}
		}else{


			$("#DownLoadLegend").remove();
			$("#DownLoadLegend1").remove();
			$("#DownLoadLegend2").remove();
			DeleteMarkers("download");

			}

});



///end of change function

$("#generate").click(function() {

	  var startDate = document.getElementById("startdate").value;
	  var endDate = document.getElementById("enddate").value;

	
		

	$("#gridTable").remove();

	//$("#paginationRow").remove();

	$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header"><th>CID <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>LAC<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Start Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th> End Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Average Coverage Signal<li class="filter-dropdown dropdown">'
			+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
			+'</ul></li></th><th>Coverage Signal Classification<li class="filter-dropdown dropdown">'
			+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
			+'</ul></li></th><th>Average Upload Speed <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
			+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Average Download Speed <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Technology<li class="filter-dropdown dropdown">'
			+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
			+'</ul></li></th><tr><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th></tr></thead><tbody></tbody></table>');


	/*$("paginationDiv").append('<div class="row" id="paginationRow"><div class="col-sm-7"><p class="pagination-label">Viewing <span>0-0</span>of<span>0</span></p></div><div class="col-sm-5 pagination-buttons">'
			+'<nav aria-label="Page navigation"><ul class="pagination pagination-buttons justify-content-end"><li class="page-item"><button type="button"'
			+' class="page-link pagination-previous pagination-button shadow-none">Prev</button></li><li class="page-item dropdown-pagination-numbers"><div class="input-group page-number-group-div">'
			+'<input type="text" class="form-control page-number-select shadow-none" /><input type="text" class="form-control page-number-span shadow-none" /></div></li>'
			+'<li class="page-item"><button type="button" class="page-link pagination-next pagination-button shadow-none">Next</button></li></ul></nav></div></div>');*/



	$.ajax({
		type : "GET",
		contentType: "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/GenerateSpeedCoverageReport",
		
		data : {
			"startDate":$("#startdate").val(),
			"endDate":$("#enddate").val(),
			

	},
	dataType : "json",
	success : function(data) {

			if(data.length==0){

				alert("No Data found between this two dates");
			}
			else{
			if(data!=null ){
				
		//append markers to map
		
			//remove legend
			$("#upLoadLegend").remove();
			$("#upLoadLegend1").remove();
			$("#upLoadLegend2").remove();
			DeleteMarkers("upload");

			$("#coverageLegend").remove();
			$("#coverageLegend1").remove();
			$("#coverageLegend2").remove();
			DeleteMarkers("signal");


			$("#DownLoadLegend").remove();
			$("#DownLoadLegend1").remove();
			$("#DownLoadLegend2").remove();
			DeleteMarkers("download");

			byCoverage.checked=false;
			byUpload.checked=false;
			byDownload.checked=false;


			
		divTablee= document.getElementById("tableDiv").innerHTML;
		divRevTable =document.getElementById("revTable").innerHTML;
		
		// Define the Center
		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
		
		
	  var map = new google.maps.Map(document.getElementById("mapContainer"), {
	    center:Nairobi ,
	    zoom:6
	    
	  });

	  document.getElementById("mapContainer").innerHTML ="";

		var map = new google.maps.Map(document.getElementById("mapContainer"), {

			 mapTypeControl: false,
			 center:Nairobi ,
			 zoom: 6,
			 mapTypeControl: true,
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
						 
						 style: 'mapbox://styles/mapbox/streets-v11',
						 fullscreenControl: true,
						 });

		 downloadArray=data.SpeedDownReportGIS;
		 uploadArray=data.SpeedUpReportGIS;
		 coverageArray=data.CoverageReportGIS;

		if(coverageArray.length>0){
			$("#byCoverage").prop("checked", true);
			 AddSelectedSignalColor(coverageArray,map);
			}
		if(downloadArray.length>0){
			$("#byDownload").prop("checked", true);
			AddSelectedDownSpeedColor(downloadArray,map);
			}
		if(uploadArray.length>0){
			$("#byUpload").prop("checked", true);
			AddSelectedUpSpeedColor(uploadArray,map);
			}
			
		
		
	 
	   	

	  //Add buttons on map
		const centerControlDiv = document.createElement("div");
	    LegendControl(centerControlDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);
				
				//Fill the Grid
				speedCoverageGrid = Object.values(data.speedCoverageReportGrid);
				
				for(i=0;i<speedCoverageGrid.length;i++){
					if(speedCoverageGrid[i][6].startsWith('.')){
						speedCoverageGrid[i][6]='0'+speedCoverageGrid[i][6];
						}

					if(speedCoverageGrid[i][7].startsWith('.')){
						speedCoverageGrid[i][7]='0'+speedCoverageGrid[i][7];
						}
					}
				 almgrid = new AlmgridTable({
			         tableId: "gridTable",
			         dataArray: speedCoverageGrid,
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
			 
			
		}
	});

});




speedCoverageGrid=Object.values(${speedCoverageReportGrid});

$(document).ready(function () {



	
	 var almgrid = new AlmgridTable({
         tableId: "gridTable",
         dataArray: speedCoverageGrid,
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
		 
        // var dataRev=0,smsRev=0,voiceRev=0,grossRev=0;
         var ArrayKeys = Object.keys(dataArray[0]);
        // var itemRow = "";
    		var columnVal;
    		
      		////for export
      		/*var data = [];
      	    exportArrayGrid = [];
     		data.push('\r');
      		data.push(["Site", "Area", "Start Date", "End Date","Voice Revenue","SMS Revenue","Data Revenue","VAS Revenue","Combination Technology"]);*/
      		
       /* for (var i = 0; i < dataArray.length; i++) {
	           
				itemRow += "<tr class='filterRows'>";
				// for export 
   			data.push('\r');
   			
            for (var j = 0; j < ArrayKeys.length; j++) {
            	
         	 columnVal = ArrayKeys[j];
         	// for export 
           	// data.push(dataArray[i][ArrayKeys[j]]);
              
           //   itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>"; 
            // // if(columnVal =="smsRevenue")   smsRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
          //    if(columnVal =="dataRevneue")   dataRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
           //   if(columnVal =="voiceRevenue")   voiceRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
           //   if(columnVal =="vasRevenue")   grossRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
   			                  
				   
   			}
   			  itemRow += "</tr>";
   			}*/

             /// for export
            // exportArrayGrid.push(data);
            
		         // var roundedsms = Math.round((smsRev + Number.EPSILON) * 100) / 100;
		        //  var roundedvoice=Math.round((voiceRev + Number.EPSILON) * 100) / 100;
		        //  var roundedgross=Math.round((grossRev + Number.EPSILON) * 100) / 100;
		        //  var roundedData=Math.round((dataRev + Number.EPSILON) * 100) / 100;
		        //  var roundedtotal=Math.round((smsRev+voiceRev+grossRev+dataRev + Number.EPSILON) * 100) / 100;
		
		 
				// $('#smsRev').val(roundedsms);
				// $('#dataRev').val(roundedData);
				// $('#voiceRev').val(roundedvoice);
				// $('#grossRev').val(roundedgross);
				// $('#totalRev').val(roundedtotal);
							
   	        // $(tableBody).append(itemRow);

   	        }
   		    else{
								 
   			  		//$('#smsRev').val('0');
					//	$('#dataRev').val('0');
					//	$('#voiceRev').val('0');
					//	$('#grossRev').val('0');
					//	$('#totalRev').val('0');
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
  
});


//collapse active class	
$('.panel-collapse').on('show.bs.collapse', function () {
        
	    $(this).siblings('.panel-heading').removeClass('active');
	  });

$('.panel-collapse').on('hide.bs.collapse', function () {
	    $(this).siblings('.panel-heading').addClass('active');
	  });



function coveragePieChart(coverageArray){

	ExcellentListIndex=[];
	GoodListIndex=[];
	FairListIndex=[];
	BadListIndex=[];
	ExBadListIndex=[];
	
	Excellent_list=[];
	Good_list=[];
	Fair_list=[];
	Bad_list=[];
	ExBad_list=[];

	
	
	//fill extremely bad array
	    for (j=0;j<coverageArray.length;j++){
	
	    	    	
	    	        if (coverageArray[j][1] <= -95) {
	    	        	ExBadListIndex.push(j);
	    	        	ExBad_list.push(coverageArray[j]);
	    	        	
	    	        }
	    	       else if (coverageArray[j][1] > -95 && coverageArray[j][1]<= -85) {
	    	    	   BadListIndex.push(j);
	    	    	   Bad_list.push(coverageArray[j]);
	    	    	   

	    	        }
	    	        else if (coverageArray[j][1] > -85 && coverageArray[j][1]<= -75) {
	    	        	FairListIndex.push(j);
	    	        	Fair_list.push(coverageArray[j]);
	    	        	

	    	        }
	    	        else if (coverageArray[j][1] > -75 && coverageArray[j][1]<= -65) {
	    	        	GoodListIndex.push(j);
	    	        	Good_list.push(coverageArray[j]);
	    	        	
	    	        	

	    	        }
	    	        else if (coverageArray[j][1] > -65 && coverageArray[j][1]<= -55) {
	    	        	ExcellentListIndex.push(j);
	    	        	Excellent_list.push(dataSite[j]);
	    	      
	    	        	

	    	        }
	    	  
	      } 

    	var Total=ExBadListIndex.length+ExcellentListIndex.length+GoodListIndex.length+FairListIndex.length+BadListIndex.length;
	      
       
	var label1,label2,label3,label4,label5;
	var firstRngCount=0,secondRngCount=0,thirdRngCount=0,fourthRngCount=0;fifthRngCount=0,totCount=0;

			
			if(	coverageArray.length!=0){
			
				    label1 = "Excellent";label2="Good";label3="Fair";label4="Bad";label5="Extremely Bad";
				    firstRngCount =(ExcellentListIndex.length/Total)*100;
				    secondRngCount=(GoodListIndex.length/Total)*100;
				    thirdRngCount=(FairListIndex.length/Total)*100;
				    fourthRngCount=(BadListIndex.length/Total)*100;
				    fifthRngCount=(ExBadListIndex.length/Total)*100;
					totCount=Total;
						
			}
				else{
					label1 = "NO Data";label2="";label3="";label4="";label5="";
					
					firstRngCount =100;
					secondRngCount=0;
					thirdRngCount=0;
					fourthRngCount=0;
					fifthRngCount=0;
					totCount=0;
				}
								
		


	const dataSource = {
			
	  chart: {
		    caption: "Coverage Signal",
		    subcaption: "For a whole-count of "+totCount+" Coverage Capture",
		    captionFontSize:"16",
		    captionOnTop: "0",
		    showvalues: "1",
            subcaptionFontColor: "#254E7C",   
		    showpercentintooltip: "0",
		    numberprefix: "%",
		    enablemultislicing: "1",
		    theme: "zune",
		    "bgColor": "EEEEEE,CCCCCC",
	        "bgratio": "60,40",
	        "bgAlpha": "70,80",
	        "bgAngle": "180",
	  },

	  data: [
	    {
	      label: label1,
	      value: firstRngCount,
	      "color":"#006400"
	      
	    },
	    {
	      label: label2,
	      value: secondRngCount,
	      "color":"#0080FF"
	    },
	    {
	      label: label3,
	      value: thirdRngCount,
	      "color":"#FFFF00"
	    },
	    {
		      label: label4,
		      value: fourthRngCount,
		      "color":"#4D0207"
		},
		{
		      label: label5,
		      value: fifthRngCount,
		      "color":"#FF0000"
		}
	  ]
	};
	
	FusionCharts.ready(function() {
	  var myChart = new FusionCharts({
		    type: "pie2d",
		    renderAt: "countChartPie",
		    width: "100%",
		    height: "410",
		    dataFormat: "json",
		    dataSource
	  });
	  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  myChart.setJSONData(dataSource);		            
	  myChart.render();
	});
}

function downloadPieChart(downloadArray){

	ExcellentListIndex=[];
	GoodListIndex=[];
	FairListIndex=[];
	BadListIndex=[];
	ExBadListIndex=[];
	
	Excellent_list=[];
	Good_list=[];
	Fair_list=[];
	Bad_list=[];
	ExBad_list=[];

	
	
	//fill extremely bad array
    for (j=0;j<downloadArray.length;j++){

    	    	
    	        if (downloadArray[j][1] <= 0.250) {
    	        	ExBadListIndex.push(j);
    	        	ExBad_list.push(downloadArray[j]);
    	        	
    	        }
    	       else if (downloadArray[j][1] > 0.250 && downloadArray[j][1]<= 2) {
    	    	   BadListIndex.push(j);
    	    	   Bad_list.push(downloadArray[j]);
    	    	   

    	        }
    	        else if (downloadArray[j][1] > 2 && downloadArray[j][1]<= 15) {
    	        	FairListIndex.push(j);
    	        	Fair_list.push(downloadArray[j]);
    	        	

    	        }
    	        else if (downloadArray[j][1] > 15 && downloadArray[j][1]<= 30) {
    	        	GoodListIndex.push(j);
    	        	Good_list.push(downloadArray[j]);
    	        	
    	        	

    	        }
    	        else if (downloadArray[j][1] > 30) {
    	        	ExcellentListIndex.push(j);
    	        	Excellent_list.push(downloadArray[j]);
    	      
    	        	

    	        }
    	
      } 
    
    

    	var Total=ExBadListIndex.length+ExcellentListIndex.length+GoodListIndex.length+FairListIndex.length+BadListIndex.length;
	      
       
	var label1,label2,label3,label4,label5;
	var firstRngCount=0,secondRngCount=0,thirdRngCount=0,fourthRngCount=0;fifthRngCount=0,totCount=0;

			
			if(	coverageArray.length!=0){
			
				    label1 = "Excellent Speed";label2="Good Speed";label3="Fair Speed";label4="Bad Speed";label5="Extremely Bad Speed";
				    firstRngCount =(ExcellentListIndex.length/Total)*100;
				    secondRngCount=(GoodListIndex.length/Total)*100;
				    thirdRngCount=(FairListIndex.length/Total)*100;
				    fourthRngCount=(BadListIndex.length/Total)*100;
				    fifthRngCount=(ExBadListIndex.length/Total)*100;
					totCount=Total;
						
			}
				else{
					label1 = "NO Data";label2="";label3="";label4="";label5="";
					
					firstRngCount =100;
					secondRngCount=0;
					thirdRngCount=0;
					fourthRngCount=0;
					fifthRngCount=0;
					totCount=0;
				}
								
		


	const dataSource = {
			
	  chart: {
		    caption: "Internet Download Speed",
		    subcaption: "For a whole-count of "+totCount+" Download Speed Capture",
		    captionFontSize:"16",
		    captionOnTop: "0",
		    showvalues: "1",
            subcaptionFontColor: "#254E7C",   
		    showpercentintooltip: "0",
		    numberprefix: "%",
		    enablemultislicing: "1",
		    theme: "zune",
		    "bgColor": "EEEEEE,CCCCCC",
	        "bgratio": "60,40",
	        "bgAlpha": "70,80",
	        "bgAngle": "180",
	  },

	  data: [
	    {
	      label: label1,
	      value: firstRngCount,
	      "color":"#006400"
	      
	    },
	    {
	      label: label2,
	      value: secondRngCount,
	      "color":"#0080FF"
	    },
	    {
	      label: label3,
	      value: thirdRngCount,
	      "color":"#FFFF00"
	    },
	    {
		      label: label4,
		      value: fourthRngCount,
		      "color":"#4D0207"
		},
		{
		      label: label5,
		      value: fifthRngCount,
		      "color":"#FF0000"
		}
	  ]
	};
	
	FusionCharts.ready(function() {
	  var myChart = new FusionCharts({
		    type: "pie2d",
		    renderAt: "DownloadChartContainer",
		    width: "100%",
		    height: "410",
		    dataFormat: "json",
		    dataSource
	  });
	  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  myChart.setJSONData(dataSource);		            
	  myChart.render();
	});
}

function uploadPieChart(downloadArray){

	ExcellentListIndex=[];
	GoodListIndex=[];
	FairListIndex=[];
	BadListIndex=[];
	ExBadListIndex=[];
	
	Excellent_list=[];
	Good_list=[];
	Fair_list=[];
	Bad_list=[];
	ExBad_list=[];

	
	
	//fill extremely bad array
    for (j=0;j<downloadArray.length;j++){

    	    	
    	        if (downloadArray[j][1] <= 0.250) {
    	        	ExBadListIndex.push(j);
    	        	ExBad_list.push(downloadArray[j]);
    	        	
    	        }
    	       else if (downloadArray[j][1] > 0.250 && downloadArray[j][1]<= 2) {
    	    	   BadListIndex.push(j);
    	    	   Bad_list.push(downloadArray[j]);
    	    	   

    	        }
    	        else if (downloadArray[j][1] > 2 && downloadArray[j][1]<= 15) {
    	        	FairListIndex.push(j);
    	        	Fair_list.push(downloadArray[j]);
    	        	

    	        }
    	        else if (downloadArray[j][1] > 15 && downloadArray[j][1]<= 30) {
    	        	GoodListIndex.push(j);
    	        	Good_list.push(downloadArray[j]);
    	        	
    	        	

    	        }
    	        else if (downloadArray[j][1] > 30) {
    	        	ExcellentListIndex.push(j);
    	        	Excellent_list.push(downloadArray[j]);
    	      
    	        	

    	        }
    	
      } 
    
    

    	var Total=ExBadListIndex.length+ExcellentListIndex.length+GoodListIndex.length+FairListIndex.length+BadListIndex.length;
	      
       
	var label1,label2,label3,label4,label5;
	var firstRngCount=0,secondRngCount=0,thirdRngCount=0,fourthRngCount=0;fifthRngCount=0,totCount=0;

			
			if(	coverageArray.length!=0){
			
				    label1 = "Excellent Speed";label2="Good Speed";label3="Fair Speed";label4="Bad Speed";label5="Extremely Bad Speed";
				    firstRngCount =(ExcellentListIndex.length/Total)*100;
				    secondRngCount=(GoodListIndex.length/Total)*100;
				    thirdRngCount=(FairListIndex.length/Total)*100;
				    fourthRngCount=(BadListIndex.length/Total)*100;
				    fifthRngCount=(ExBadListIndex.length/Total)*100;
					totCount=Total;
						
			}
				else{
					label1 = "NO Data";label2="";label3="";label4="";label5="";
					
					firstRngCount =100;
					secondRngCount=0;
					thirdRngCount=0;
					fourthRngCount=0;
					fifthRngCount=0;
					totCount=0;
				}
								
		


	const dataSource = {
			
	  chart: {
		    caption: "Internet Upload Speed",
		    subcaption: "For a whole-count of "+totCount+" Upload Speed Capture",
		    captionFontSize:"16",
		    captionOnTop: "0",
		    showvalues: "1",
            subcaptionFontColor: "#254E7C",   
		    showpercentintooltip: "0",
		    numberprefix: "%",
		    enablemultislicing: "1",
		    theme: "zune",
		    "bgColor": "EEEEEE,CCCCCC",
	        "bgratio": "60,40",
	        "bgAlpha": "70,80",
	        "bgAngle": "180",
	  },

	  data: [
	    {
	      label: label1,
	      value: firstRngCount,
	      "color":"#006400"
	      
	    },
	    {
	      label: label2,
	      value: secondRngCount,
	      "color":"#0080FF"
	    },
	    {
	      label: label3,
	      value: thirdRngCount,
	      "color":"#FFFF00"
	    },
	    {
		      label: label4,
		      value: fourthRngCount,
		      "color":"#4D0207"
		},
		{
		      label: label5,
		      value: fifthRngCount,
		      "color":"#FF0000"
		}
	  ]
	};
	
	FusionCharts.ready(function() {
	  var myChart = new FusionCharts({
		    type: "pie2d",
		    renderAt: "UploadChartContainer",
		    width: "100%",
		    height: "410",
		    dataFormat: "json",
		    dataSource
	  });
	  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  myChart.setJSONData(dataSource);		            
	  myChart.render();
	});
}
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
	async defer></script>

</html>