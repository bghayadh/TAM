<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Site Form View</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />

<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>


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
<style type="text/css">
.required:after {
	content: " *";
	color: red;
}

.inputWithIcon input[type="text"] {
	padding-right: 40px;
}

.inputWithIcon {
	position: relative;
}

.inputWithIcon i {
	position: absolute;
	right: 0;
	top: 8px;
	padding: 9px 8px;
	transition: 0.3s;
}
</style>
</head>
<body>
	<!-- nav -->
	<jsp:include page="navBarNetwork.jsp" />
	<!--  -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<p></p>
			</div>

		</div>

		<!-- *************************** site fields ************************************** -->

		<div class="row second">
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Cell
							Form</span> <i>&nbsp</i><span class="dot"></span> <i>&nbsp</i> <label
							for="formStatus" id="formStatus">Saved</label>
					</div>

				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Site ID</span> <input type="text"
							id="siteID" readonly value="${siteID}"
							class="form-control text-input" />
					</div>
				</div>
			</div>


			<div class="col-md-2"></div>

			<div class="col-md-3" style="text-align: right;">
				<div class="btn-group pull-right">
					<div class="glyph">
						<button type="button" id="Fview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="Form View"
							style="background: #da6815;">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-light"
							data-toggle="tooltip"
							onclick='window.location.href = "${pageContext.request.contextPath}/WarehouseListView"'
							data-placement="top" title="List View">
							<i class="fa fa-bars"></i>
						</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1"
						data-target-input="nearest">
						<span class="input-group-text">Created Date</span> <input
							type="text" id="wcreatedate" readonly value="${screationDate}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker1" />
						<div class="input-group-append" data-target="#datetimepicker1"
							data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1"
						data-target-input="nearest">
						<span class="input-group-text">Created Date</span> <input
							type="text" id="wcreatedate" readonly value="${screationDate}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker1" />
						<div class="input-group-append" data-target="#datetimepicker1"
							data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- ************************************* Site tab ************************************* -->
	<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">
			<ul class="nav nav-tabs" id="custom-tabs-all-tab" role="tablist"
				style="background-color: #00757c; margin-top: 0px;">

				<li class="nav-item"><a class="nav-link active"
					id="custom-tabs-site-tab" data-toggle="tab"
					href="#custom-tabs-site" role="tab"
					aria-controls="custom-tabs-site" aria-selected="true"
					style="color: gold;">Node Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-node-tab" data-toggle="tab"
					href="#custom-tabs-node" role="tab"
					aria-controls="custom-tabs-node" aria-selected="false"
					style="color: gold;">Cell Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-cell-tab" data-toggle="tab"
					href="#custom-tabs-cell" role="tab"
					aria-controls="custom-tabs-cell" aria-selected="false"
					style="color: gold;">Site Information</a></li>





				<li class="nav-item ml-auto">
					<button type="button" id="deleteButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-trash"></i> Delete
					</button>

					<button type="button" id="saveButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-save"></i> Save
					</button>
				</li>

			</ul>

		</div>
	</div>

	<!-- ************************************************************************** -->

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card card-primary card-tabs cards-margin">
					<div class="card-body">
						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-site"
								role="tabpanel" aria-labelledby="custom-tabs-site-tab">
								<!--**************************** first tab **************************** -->
								<hr>
																
								<h4>Active Parameters</h4>
								<hr>
								<div class="row">
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Cell ID</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Cell Name</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>CGI</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Band (900/1800/MBC)</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="row">
								<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Net TRX's Available</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Tx Power (dB)</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<hr>
								<h4>Passive Parameters</h4>
								<hr>
								<form action="siteSave" method="post"
									commandName="siteDetails2G" id="sitePassive2G">
									<!-- ******************************** first row passive ************************************ -->
									<div class="row">

										<div class="col-sm-3">
											<label class='required'>Date On Air (DD-MM-YY)</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fa fa-calendar"></i></span>
												</div>
												<input class="form-control" type="text">
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Site Sub Type</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Mode Of Operation</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Shared With 4G
													(Yes/No)</label> <select id="existNewTown"
													class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
									</div>
									<!-- ******************************** second row passive ************************************ -->
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna 1-Manufacturer</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna Model 1</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna 2-Manufacturer</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna Model 2</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
									</div>

									<!-- ******************************** 3 row passive ************************************ -->
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Gain (dB)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Beam Width (Deg)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Azimuth (Deg)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Height AGL(m)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
									</div>

									<!-- ******************************** 4 row passive ************************************ -->
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Electrical Tilt (Deg)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Mechanical Tilt (Deg)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>AT (Y/N)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>RET (Y/N)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
									</div>

									<!-- ******************************** 5 row passive ************************************ -->
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Feeder Size</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Approximate Feeder Length
													(m)</label> <select id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>TMA/MHA (Yes/No)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Remarks</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
									</div>

									<!-- ******************************** 6 row passive ************************************ -->
									<div class="row">


										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Sector Status Active/Locked</label>
												<select id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Sector Locked Date</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>If Locked Reason For Locked</label>
												<select id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Master Sector ID</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
										
										
									</div>
									
									<!-- ******************************** 7 row passive ************************************ -->
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Flag</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
									</div>

								</form>
								<hr>
								
							</div>
							
							
							

							<div class="tab-pane fade" id="custom-tabs-node" role="tabpanel"
								aria-labelledby="custom-tabs-node-tab">
								<!--**************************** second tab **************************** -->
										
								
								<!--**************************** node tab **************************** -->
								<hr>
								<div class="row">
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Node ID</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Node Name</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>BSC Name/ID</label> <input
												type="text" id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>MSC Name/ID</label> <input
												type="text" id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<!-- ****************************** Active Parameters ************************************** -->
								<hr>
								<h4>Active Parameters</h4>
								<hr>
								<div class="row">
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>SRAN Node (Yes/No)</label> <input
												type="text" id="siteID" readonly value="${sran}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>BTS Type-Main Cabinet</label> <input
												type="text" id="siteID" readonly value="${btsType}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="siteID" readonly
												value="${btsType}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 2/MBC
												Cabinet</label> <input type="text" id="siteID" readonly
												value="${btsType}" class="form-control text-input" />
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Band BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="siteID" readonly
												value="${sran}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Band BTS Type Expansion 2/MBC
												Cabinet</label> <input type="text" id="siteID" readonly
												value="${btsType}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Active Technology On SRAN BTS
											</label> <input type="text" id="siteID" readonly value="${btsType}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Swap (Y/N)</label> <input type="text"
												id="siteID" readonly value="${btsType}"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Swap Date</label> <input type="text"
												id="siteID" readonly value="${btsType}"
												class="form-control text-input" />
										</div>
									</div>
								</div>

								<!-- ****************************** Passive Parameters ************************************** -->
								<hr>
								<h4>Passive Parameters</h4>
								<hr>
								<form action="siteSave" method="post"
									commandName="siteDetails2G" id="sitePassive2G">
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Site Type</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-4">
											<label class='required'>Date On Air-BTS
												Type-Expansion 1/MBC Cabinet</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fa fa-calendar"></i></span>
												</div>
												<input class="form-control" type="text">
											</div>
										</div>
										<div class="col-sm-4">
											<label class='required'>Date On Air-BTS
												Type-Expansion 2/MBC Cabinet</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fa fa-calendar"></i></span>
												</div>
												<input class="form-control" type="text">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Swap (Y/N)</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-4">
											<label class='required'>Swap Date</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													<span class="input-group-text"> <i
														class="fa fa-calendar"></i></span>
												</div>
												<input class="form-control" type="text">
											</div>
										</div>
									</div>
								</form>						


								<hr>

							</div>

							<div class="tab-pane fade" id="custom-tabs-cell" role="tabpanel"
								aria-labelledby="custom-tabs-cell-tab">
								<!--**************************** third tab **************************** -->
								<!-- ****************************** Active Parameters ************************************** -->
								<hr>
								<h4>Active Parameters</h4>
								<hr>
								<div class="row">
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Longitude</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Latitude</label> <input type="text"
												id="siteID" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Site Mode</label> <input type="text"
												id="siteID" readonly value="${siteMode}"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<!-- ****************************** Passive Parameters ************************************** -->
								<hr>
								<h4>Passive Parameters</h4>
								<hr>
								<form action="siteSave" method="post"
									commandName="siteDetails2G" id="sitePassive2G">
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Existing / New Town</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Showcase / Non Showcase
													(SC/NSC)</label> <select id="showcaseNonShowcase"
													class="form-control select2">
													<option value="SC"
														<c:if test = "${showcaseNonShowcase =='SC'}"> selected </c:if>>SC</option>
													<option value="NSC"
														<c:if test = "${showcaseNonShowcase =='NSC'}"> selected </c:if>>NSC</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="sitePrincipallabel">Site
													Principal Owner (Toco Name)</label> <input id="sitePrincipleOwner"
													type="text" class="form-control"
													value="${sitePrincipleOwner}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="towerCoIdlabel">Tower Co
													ID</label> <input type="text" id="towerCoId" class="form-control"
													value="${towerCoId}" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Tower Type </label> <select
													id="towerType" class="form-control select2">
													<option value="GBT"
														<c:if test = "${towerType =='GBT'}"> selected </c:if>>GBT</option>
													<option value="RTT"
														<c:if test = "${towerType =='RTT'}"> selected </c:if>>RTT</option>
													<option value="RTP"
														<c:if test = "${towerType =='RTP'}"> selected </c:if>>RTP</option>
													<option value="Wall Mounted"
														<c:if test = "${towerType =='Wall Mounted'}"> selected </c:if>>Wall
														Mounted</option>
													<option value="IBS"
														<c:if test = "${towerType =='IBS'}"> selected </c:if>>IBS</option>
													<option value="GBP"
														<c:if test = "${towerType =='GBP'}"> selected </c:if>>GBP</option>
													<option value="COW"
														<c:if test = "${towerType =='COW'}"> selected </c:if>>COW</option>
													<option value="NOW"
														<c:if test = "${towerType =='NOW'}"> selected </c:if>>NOW</option>
													<option value="GBT + Revamp"
														<c:if test = "${towerType =='GBT + Revamp'}"> selected </c:if>>GBT
														+ Revamp</option>
													<option value="RTT + Revamp"
														<c:if test = "${towerType =='RTT + Revamp'}"> selected </c:if>>RTT
														+ Revamp</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="towerHeightlabel">Tower
													Height (m)</label> <input type="number" min='0' id="towerHeight"
													class="form-control" value="${towerHeight}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="buildingHeightlabel">Building
													Height (m)</label> <input type="number" min='0' id="buildingHeight"
													class="form-control" value="${buildingHeight}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Shared / None Shared</label> <select
													id="sharedNonShared" class="form-control select2">
													<option value="Shared"
														<c:if test = "${sharedNonShared =='Shared'}"> selected </c:if>>Shared</option>
													<option value="Non-Shared"
														<c:if test = "${sharedNonShared =='Non-Shared'}"> selected </c:if>>Non-Shared</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>ICR category (ICR/MORAN)</label> <select
													id="icrCategory" class="form-control select2">
													<option value="NO"
														<c:if test = "${icrCategory =='NO'}"> selected </c:if>>NO</option>
													<option value="ICR"
														<c:if test = "${icrCategory =='ICR'}"> selected </c:if>>ICR</option>
													<option value="MORAN"
														<c:if test = "${icrCategory =='MORAN'}"> selected </c:if>>MORAN</option>
												</select>
											</div>
										</div>
									</div>
								</form>
								<hr>
								

							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end container -->
	</div>
</body>




</html>