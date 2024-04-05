<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<%@ include file="scripts.html"%>

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<%-- 	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script> --%>

<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/js/TT_BoqPopup.js"></script>

<style>

/*Doaa's popup Email Div'*/
#popUpDiv {
	position: fixed;
	top: 30%;
	left: 50%;
	background-color: #eeeeee;
	border: 5px solid #08526d;
	width: 400px;
	height: 450px;
	margin-left: -150px;
	margin-top: -95px;
	-moz-border-radius: 16px;
	-webkit-border-radius: 16px;
	border-radius: 16px;
	box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px #000000;
	z-index: 9003;
	/*above nine thousand*/
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	z-index: 9999;
	width: 350px;
}

.checkCenter {
	margin: 0;
	position: absolute;
	top: 50%;
	-ms-transform: translateY(-50%);
	transform: translateY(-50%);
}

.rightpush {
	right: 50%;
}

th {
	text-align: center;
}

td {
	text-align: center;
	vertical-align: middle !important;
}

.hide-row {
	display: none;
}



li.nav-item a svg {
	color: gold !important;
}
li.nav-item span.active svg {
    color: #FFD966 !important;
}
select {
	width: 150px;
	height: 25px;
	border-collapse: collapse;
	cursor: pointer;
}

.case {
	text-align: center;
	font-size: 16px;
	color: #0000FF;
	position: relative;
	top: 7px;
}

#testing {
	text-align: center;
	font-size: 16px;
	color: #0000FF;
	position: relative;
	padding: 7px;
}

.btnApproval {
	cursor: default !important;
	width: 265px !important;
	font-size: 14px;
}

.btn-primary:hover {
	background-color: #007bff !important;
}

/*.ui-autocomplete {
	max-height: 300px;
	overflow-y: auto; /* prevent horizontal scrollbar */
/*	overflow-x: both; /* add padding to account for vertical scrollbar */
/*	padding-right: 100px;
	z-index: 9999;
}*/
#ProjectManagerApprove, #AssetUnitApprove, #FinanceApprove {
	cursor: pointer;
}

.transType {
	width: 330px !important;
}

.NotesInput {
	width: 400px !important;
}

.elementName {
	width: 180px !important;
}

.inputWidth {
	width: 100px !important;
}

#discountAmount {
	width: 126px !important;
}

.custom-class-assignedto-modal .modal-body {
	height: 500px;
	overflow: auto;
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

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
	.modal-content, .modal-dialog {
		max-height: 100%;
		max-width: 100%;
	}
}
</style>
</head>
<body>

	<%--   <c:set var = "page" value = "troubleTickets"/> --%>

	<%-- 	<%@ include file="header.html" %> --%>
	<c:set var="pg" value="troubleTickets" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>


	<p></p>
	<div class="container-fluid">
		<div class="row">


			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width: 200px;" class="input-group-text">Ticket
							ID</span> <input type="text" readonly id="ticketId" value="${tkID}"
							class="form-control text-input" /> <input name="csrfToken"
							value="5965f0d244b7d32b334eff840" type="hidden" />
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="input-group-prepend">

					<span style="width: 200px;" class="input-group-text">Status</span>
					<select id="status" class="form-control select2">
						<option value="Open"
							<c:if test = "${tkStatus =='Open'}" > selected </c:if>>Open</option>
						<option value="Assigned"
							<c:if test = "${tkStatus =='Assigned'}" > selected </c:if>>Assigned</option>
						<option value="Completed"
							<c:if test = "${tkStatus =='Completed'}" > selected </c:if>>Completed</option>
						<option value="draft"
							<c:if test = "${tkStatus =='draft'}" > selected </c:if>>Draft</option>
						<option value="Resolved"
							<c:if test = "${tkStatus =='Resolved'}" > selected </c:if>>Resolved</option>
						<option value="For Adjustment"
							<c:if test = "${tkStatus =='For Adjustment'}" > selected </c:if>>For
							Adjustment</option>
						<option value="Closed"
							<c:if test = "${tkStatus =='Closed'}" > selected </c:if>>Closed</option>
						<option value="Re-Opened"
							<c:if test = "${tkStatus =='Re-Opened'}" > selected </c:if>>Re-Opened</option>
						<option value="Cancelled"
							<c:if test = "${tkStatus =='Cancelled'}" > selected </c:if>>Cancelled</option>
						<option value="Paused"
							<c:if test = "${tkStatus =='Paused'}" > selected </c:if>>Paused</option>
					</select>
				</div>
			</div>
			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Other TK</span> 
							<input type="text" id="selectnav" value="${selectnav}"
							style="width: 430px" class="form-control text-input" />
					</div>
				</div>
			</div>


			<div class="col-md-3 text-right">
				<i>&nbsp</i><span class="dot"></span> <i>&nbsp</i> <label
					for="formStatus" id="formStatus" style="float: right;">Saved</label>
			</div>


		</div>










		<div class="row">

			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1"
						data-target-input="nearest">
						<span style="width: 200px;" class="input-group-text">Created
							Date</span> <input type="text" id="creationDate" readonly
							value="${TkcreationDate}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker1" />
						<div class="input-group-append" data-target="#datetimepicker1"
							data-toggle="datetimepicker"></div>
					</div>
				</div>
			</div>

			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker2"
						data-target-input="nearest">
						<span style="width: 200px;" class="input-group-text">Last
							Modify Date</span> <input type="text" id="lastModifiedDate" readonly
							value="${TklastModifiedDate}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker1" />

						<div class="input-group-append" data-target="#datetimepicker2"
							data-toggle="datetimepicker"></div>
					</div>
				</div>
			</div>

			<div class="hide-row col-md-3 pad "></div>


			<div class=" col-md-3 nextprvItems">
				<label id="label-1"
					style="width: 80px; text-align: center; margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 52px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 20px;"><a
							style="width: 52px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>
					</ul>
				</nav>
			</div>




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
							onclick='window.location.href = "${pageContext.request.contextPath}/TroubleTicketListView"'
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

		<p></p>

	</div>


	<p></p>



	<div class="container-fluid">

		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: -20px;">
					<li class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">Information</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-assign-tab" data-toggle="tab"
						href="#custom-tabs-assign" role="tab"
						aria-controls="#custom-tabs-assign" aria-selected="false"
						style="color: gold;">Assignments</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-actions-tab" data-toggle="tab"
						href="#custom-tabs-actions" role="tab"
						aria-controls="#custom-tabs-actions" aria-selected="false"
						style="color: gold;">Actions</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-resolution-tab" data-toggle="tab"
						href="#custom-tabs-resolution" role="tab"
						aria-controls="#custom-tabs-resolution" aria-selected="false"
						style="color: gold;">Resolution</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-dcproblem-tab" data-toggle="tab"
						href="#custom-tabs-dcproblem" role="tab"
						aria-controls="#custom-tabs-dcproblem" aria-selected="false"
						style="color: gold;">Devices Caused Problem</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-lfp-tab" data-toggle="tab" href="#custom-tabs-lfp"
						role="tab" aria-controls="#custom-tabs-lfp" aria-selected="false"
						style="color: gold;">Link Failure Places</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-rca-tab" data-toggle="tab" href="#custom-tabs-rca"
						role="tab" aria-controls="#custom-tabs-rca" aria-selected="false"
						style="color: gold;">Root Caused Problem</a></li>



					<li class="nav-item ml-auto">
						<div class="dropdown" Style="display: inline-block;">
							<button class="btn btn-secondary dropdown-toggle" type="button"
								id="notifactionDropdown" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Actions</button>
							<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
								<a class="dropdown-item" type="button" id="Completed">Complete</a>
								<a class="dropdown-item" type="button" id="Closed">Close</a> <a
									class="dropdown-item" type="button" id="Cancelled">Cancel</a>
								<!-- 	             <a class="dropdown-item" type="button" id="ResolveTT" >Resolved</a> -->
								<!-- 	             <a class="dropdown-item"  type="button" id="ForAdjustTT">For Adjustment</a> -->
								<!-- 	             <a class="dropdown-item"  type="button" id="CloseTT" >Closed</a> -->
								<!-- 	             <a class="dropdown-item" type="button" id="ReOpenTT" >Re-Opened</a> -->
								<!-- 	             <a class="dropdown-item" type="button" id="PauseTT" >Paused</a> -->
							</div>
						</div>
						<button type="button" id="sendEmail"
							class="btn btn-primary BtnActive">
							<i class="fa fa-envelope"></i> Send Email
						</button>

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

	</div>

	<div class="container-fluid">

		<div class="tab-content" id="custom-tabs-one-tabContent">
			<div class="tab-pane fade show active" id="custom-tabs-one-home"
				role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">


				<p></p>
				<!--  create table purchaserequestItem    -->
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Department</span> <select
									id="dep" class="form-control select2">
									<option value="Networks"
										<c:if test = "${tkdepartment =='Networks'}" > selected </c:if>>Networks</option>
									<option value="Enterprise"
										<c:if test = "${tkdepartment =='Enterprise'}" > selected </c:if>>Enterprise</option>
									<option value="Operations"
										<c:if test = "${tkdepartment =='Operations'}" > selected </c:if>>Operations</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 200px;" class="input-group-text">Priority</span>
								<select id="priority" class="form-control select2">
									<option value="Low"
										<c:if test = "${tkPriority =='Low'}" > selected </c:if>>Low</option>
									<option value="Medium"
										<c:if test = "${tkPriority =='Medium'}" > selected </c:if>>Medium</option>
									<option value="High"
										<c:if test = "${tkPriority =='High'}" > selected </c:if>>High</option>
									<option value="Critical"
										<c:if test = "${tkPriority =='Critical'}" > selected </c:if>>Critical</option>
								</select>


							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Subject</span> <input type="text"
									id="subject" value="${tksubject}"
									class="form-control text-input" />

							</div>
						</div>
					</div>



				</div>

				<!-- Choose Category Modal -->


				<div class="row">
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Client</span> <input type="text"
								id="client" value="${tkclient}" class="form-control text-input" />
						</div>


					</div>
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Region</span> <input type="text"
								id="region" value="${tkregion}" class="form-control text-input" />
						</div>

					</div>
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Service</span> <input type="text"
								id="service" value="${tkservice}"
								class="form-control text-input" />
						</div>


					</div>
				</div>
				<div class="row"></div>
				<p></p>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Service Issue</span> <input
									type="text" id="servicei" value="${tkserviceIssue}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Site ID</span> <input type="text"
									id="siteId" value="${tksiteId}" class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Site Name</span> <input
									type="text" id="siteNamee" value="${tksiteName}"
									class="form-control text-input" />
							</div>
						</div>
					</div>


				</div>

				<div class="row">


					<div class="col-md-4">
						<div class="input-group-prepend">
							<span style="width: 200px;" class="input-group-text">Description</span>

						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group-prepend" id="datetimepicker3"
							data-target-input="nearest">
							<span class="input-group-text">Issue Appeared</span> <input
								type="text" id="issueAppeared" value="${tkissueAppeared}"
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
				<div class="row">
					<div class="col-md-4">
						<textarea name="suppdescrip" cols="120" rows="7" id="descr"
							class="form-control text-input">${tkdescription}</textarea>
					</div>
				</div>


				<div class="col-md-6"></div>
			</div>


			<div class="tab-pane fade" id="custom-tabs-actions" role="tabpanel"
				aria-labelledby="custom-tabs-actions-tab">

				<p></p>
				<div>

					<form>


						<div class="table-responsive-sm">
							<table id="actionTab"
								class="table table-striped table-bordered table-sm"
								style="display: block; height: 300px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<th>
											<div style="width: 80px">
												<button type="button" id="selectAll" class="main">
													<span class="sub"></span>Select
												</button>
											</div>


										</th>
										<th style="text-align: center" width="200px">Creation
											Date</th>
										<th style="text-align: center" width="200px">Team</th>
										<th style="text-align: center" width="200px">Employee</th>
										<th style="text-align: center" width="200px">Action</th>
										<th style="text-align: center" width="300px">Description</th>
										<th style="text-align: center" width="100px">Status</th>
										<th style="text-align: center" width="100px">ActionID</th>



									</tr>
								</thead>

								<tbody>


								</tbody>

							</table>
							<p id="my_error" style="color: red;"></p>
						</div>
						<input type="button" class="addaction-row" id="adds_roww"
							onclick="addrowsActions('addRowActions')" value="Add Row">
						<button type="button" onclick="deleterows()" class="delete-row">Delete
							Row</button>
						<input type="text" id="RowToDeleteActions" hidden value="">

					</form>
				</div>

				<p></p>
			</div>

			<div class="tab-pane fade" id="custom-tabs-resolution"
				role="tabpanel" aria-labelledby="custom-tabs-resolution-tab">

				<p></p>
				<div>

					<form>


						<p></p>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group-prepend">
										<span class="input-group-text">Resolution Date</span> <input
											type="text" id="resolutionDate" value="${tkresolutionDate}"
											class="form-control text-input" />
									</div>
								</div>
							</div>

							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group-prepend">
										<span class="input-group-text">Resolution Action</span> <input
											type="text" id="resolutionAction"
											value="${tkresolutionAction}" class="form-control text-input" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">

							<div class="col-md-6">
								<div class="input-group-prepend">
									<span class="input-group-text">Resolution Description</span>
								</div>
							</div>

						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="input-group-prepend">
									<textarea name="itmdescrip" cols="120" rows="7"
										id="resolutionDescription" class="form-control text-input">${tkresolutionDescription}</textarea>
								</div>
							</div>


							<div class="col-md-6"></div>
						</div>




					</form>
				</div>

				<p></p>
			</div>




			<div class="tab-pane fade" id="custom-tabs-dcproblem" role="tabpanel"
				aria-labelledby="custom-tabs-dcproblem-tab">

				<p></p>
				<div>

					<form>


						<div class="row">
							<div class="col-md-6">
								<div class="input-group-prepend">
									<input type="checkbox" style="margin-left: 28px;"
										class="checkCenter" id="noDev" ${noDev} /> <span
										class="input-group-text rightpush" id="DCPspan"
										style="margin-left: 48px;">No Devices Caused The
										Problem</span>
								</div>

							</div>

						</div>

						<p></p>


						<div id="DCPshow">

							<div class="table-responsive-sm">
								<table id="dcproblemTab"
									class="table table-striped table-bordered table-sm"
									style="display: block; height: 300px; overflow-y: auto;">
									<thead>
										<tr class="fixed-headerr">
											<th>
												<div style="width: 80px">
													<button type="button" id="selectAllz" class="main">
														<span class="sub"></span>Select
													</button>
												</div>


											</th>
											<th style="text-align: center" width="200px">Type</th>
											<th style="text-align: center" width="200px">Site ID</th>
											<th style="text-align: center" width="200px">Site Name</th>
											<th style="text-align: center" width="200px">Node ID</th>
											<th style="text-align: center" width="300px">Node Name</th>
											<th style="text-align: center" width="100px">Cabinet</th>
											<th style="text-align: center" width="100px">Slot</th>
											<th style="text-align: center" width="100px">Board</th>
											<th style="text-align: center" width="100px">Antenna</th>
											<th style="text-align: center" width="100px">Version</th>
											<th style="text-align: center" width="300px">Note</th>
											<th style="text-align: center" width="100px">ID</th>


										</tr>
									</thead>

									<tbody>


									</tbody>

								</table>
								<p id="my_error" style="color: red;"></p>
							</div>
							<input type="button" class="addaction-row-dcproblem"
								onclick="addrowsDCP('addRowDCP')" id="adds_row-dcproblem"
								value="Add Row">
							<button type="button" onclick="deleterowsDCP()"
								class="delete-row">Delete Row</button>
							<input type='text' id='CountDCP' hidden value=''> <input
								type="text" id="RowIndex" hidden value=""> <input
								type="text" id="RowToDeleteDCP" hidden value="">


						</div>
					</form>



				</div>

				<p></p>
			</div>






			<div class="tab-pane fade" id="custom-tabs-lfp" role="tabpanel"
				aria-labelledby="custom-tabs-lfp-tab">

				<p></p>
				<div>

					<form>

						<p></p>


						<div id="LFPshow">

							<div class="table-responsive-sm">
								<table id="lfpTab"
									class="table table-striped table-bordered table-sm"
									style="display: block; height: 300px; overflow-y: auto;">
									<thead>
										<tr class="fixed-headerr">
											<th>
												<div style="width: 80px">
													<button type="button" id="selectAllLFP" class="main">
														<span class="sub"></span>Select
													</button>
												</div>

											</th>
											<th style="text-align: center" width="150px">Link ID</th>
											<th style="text-align: center" width="150px">Link Name</th>
											<th style="text-align: center" width="150px">Longtitude</th>
											<th style="text-align: center" width="150px">Latitude</th>
											<th style="text-align: center" width="150px">Reason</th>
											<th style="text-align: center" width="400px">Description</th>
											<th style="text-align: center" width="150px">ID</th>

										</tr>
									</thead>

									<tbody>


									</tbody>

								</table>
								<p id="my_error" style="color: red;"></p>
							</div>
							<input type="button" class="addaction-row-lfp" id="adds_row-LFP"
								onclick="addrows('addRow')" value="Add Row">
							<button type="button" onclick="deleterowsLFP()"
								class="delete-row">Delete Row</button>
							<input type="text" id="RowIndexLFP" hidden value=""> <input
								type="text" id="RowToDeleteLFP" hidden value="">


						</div>
					</form>



				</div>

				<p></p>
			</div>





			<div class="tab-pane fade" id="custom-tabs-rca" role="tabpanel"
				aria-labelledby="custom-tabs-rca-tab">

				<p></p>
				<div>

					<form>



						<div class="row">

							<div class="col-md-6">
								<div class="input-group-prepend">
									<span class="input-group-text">Description</span>
								</div>
							</div>
							<div class="col-md-6">
								<div class="input-group-prepend">
									<span class="input-group-text">Reason For Problem</span> <input
										type="text" id="reasonForProblem" value="${reasonForProblem}"
										class="form-control text-input" />
								</div>
							</div>

						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="input-group-prepend">
									<textarea name="itmdescrip" cols="120" rows="7"
										id="rcaDescription" class="form-control text-input">${rcaDescription}</textarea>
								</div>
							</div>
						</div>

					</form>
				</div>

				<p></p>
			</div>

			<div class="tab-pane fade" id="custom-tabs-assign" role="tabpanel"
				aria-labelledby="custom-tabs-assign-tab">

				<p></p>
				<div>
					<form>
						<p></p>

						<div id="assign">

							<div class="table-responsive-sm">
								<table id="AssignTab"
									class="table table-striped table-bordered table-sm"
									style="display: block; height: 300px; overflow-y: auto;">
									<thead>
										<tr class="fixed-headerr">
											<th>

												<div style="width: 80px">
													<button type="button" id="selectAllassign" class="main">
														<span class="sub"></span>Select
													</button>
												</div>

											</th>
											<th style="text-align: center" width="150px">Assign Date</th>
											<th style="text-align: center" width="150px">Assigned to</th>
											<th style="text-align: center" width="150px">Assigned by</th>
											<th style="text-align: center" width="150px">Action</th>
											<th style="text-align: center" width="300px">Note</th>
											<th style="text-align: center" width="300px">Creation
												Date</th>
											<th style="text-align: center" width="300px">Last
												Modified Date</th>
											<th style="text-align: center" width="150px">ID</th>

										</tr>
									</thead>

									<tbody>


									</tbody>

								</table>
								<p id="my_error" style="color: red;"></p>
							</div>
							<input type="button" class="addaction-row-assign"
								onclick="addrowsAssign('addRowAssign')" id="adds_row-LFP"
								value="Add Row">
							<button type="button" onclick="deleterowsAssign()"
								class="delete-row">Delete Row</button>
							<input type="text" id="RowIndexLFP" hidden value=""> <input
								type="text" id="RowToDeleteAssign" hidden value="">


						</div>
					</form>

				</div>

				<p></p>
			</div>

			<!-- popupLFP -->
			<div class="container">
				<div id="DNModalLFP"
					class="modal fade custom-class-assignedto-modal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #FF4F4F;">
								<h5 id="popupLFP" class="modal-title"
									style="font-weight: bold; color: #3C1596;"></h5>

								<button type="button" name="insertBelow"
									onclick="insertRowBelow()"
									class="btn btn-default btn-primary BtnActive  "
									style="color: white; position: relative; left: 50px;">Insert
									Below</button>
								<button type="button" name="insertAbove"
									onclick="insertRowAbove()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 60px;">Insert
									Above</button>
								<button type="button" name="deleteBoqRow"
									onclick="deleteBoqRow()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 70px;">Delete</button>
								<button name="previousRow"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 80px;">Previous</button>
								<button name="nextRow" onclick="nextRow()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 90px;">Next</button>

								<button type="button" name="closeModPartPopup" class="close"
									data-dismiss="modal">
									<i class='fa fa-times'></i>
								</button>
								<a class="close modalMinimize ml-3"> <i
									class='fa fa-minus icon-to-change'></i>
								</a>
							</div>
							<div class="modal-body">
								<ul class="nav nav-tabs" id="myTab" role="tablist"
									style="background-color: #00757C;">
									<li class="nav-item"><a class="nav-link active"
										id="modPart-tab" style="color: gold;" data-toggle="tab"
										href="#modPart" role="tab" aria-controls="modPart"
										aria-selected="true">MODELS AND PART NUMBERS</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="modPart" role="tabpanel"
										aria-labelledby="modPart-tab">
										<p></p>
										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Link ID </span> <input
																type="text" id="popuplinkID" value=""
																style="width: 675px; height: 37px"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Link Name</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popuplinkName" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Longtitude</span> <input
																type="text" id="popuplongg"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Latitude</span> <input
																type="text" id="popuplatitude"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Reason</span> <input
																type="text" id="popupReason"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Description</span> <input
																type="text" id="popuplfpdescription"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>
										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-3">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">ID</span> <input
																type="text" id="popuplfpID" readonly
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

											</div>
										</div>


									</div>
								</div>
							</div>
							<div class="modal-footer">
								<!-- 	<button name ="previousRowModPart" class ="btn btn-default">Previous</button>
	<button name="nextRowModPart" class ="btn btn-default">Next</button> -->
							</div>

						</div>
					</div>
				</div>
			</div>


			<!--end of popupLFP -->

			<!-- popupAssignments -->
			<div class="container">
				<div id="DNModalAssign"
					class="modal fade custom-class-assignedto-modal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #FF4F4F;">
								<h5 id="popupAssign" class="modal-title"
									style="font-weight: bold; color: #3C1596;"></h5>

								<button type="button" name="insertBelowAssign"
									onclick="insertRowBelowAssign()"
									class="btn btn-default btn-primary BtnActive  "
									style="color: white; position: relative; left: 50px;">Insert
									Below</button>
								<button type="button" name="insertAboveAssign"
									onclick="insertRowAboveAssign()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 60px;">Insert
									Above</button>
								<button type="button" name="deleteBoqRowAssign"
									onclick="deleteBoqRowAssign()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 70px;">Delete</button>
								<button name="previousRowAssign"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 80px;">Previous</button>
								<button name="nextRowAssign" onclick="nextRowAssign()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 90px;">Next</button>

								<button type="button" name="closeModPartPopup" class="close"
									data-dismiss="modal">
									<i class='fa fa-times'></i>
								</button>
								<a class="close modalMinimize ml-3"> <i
									class='fa fa-minus icon-to-change'></i>
								</a>
							</div>
							<div class="modal-body">
								<ul class="nav nav-tabs" id="myTab" role="tablist"
									style="background-color: #00757C;">
									<li class="nav-item"><a class="nav-link active"
										id="modPart-tab" style="color: gold;" data-toggle="tab"
										href="#modPart" role="tab" aria-controls="modPart"
										aria-selected="true">MODELS AND PART NUMBERS</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="modPart" role="tabpanel"
										aria-labelledby="modPart-tab">
										<p></p>
										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Assign Date</span> <input
																id="popupassignDate" type='date' style='width: 200px;'
																class='form-control text-input'>
														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Assigned To</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupassignTo" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Assign By</span> <input
																type="text" id="popupassignBy"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Action</span> <input
																type="text" id="popupAction"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>

										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Note</span> <input
																type="text" id="popupNote"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Creation Date</span> <input
																type="text" id="popupCreateDate"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" readonly />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>

										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Last Modified Date</span>
															<input type="text" id="popupLastModifiedDate"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" readonly />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Id</span> <input
																type="text" id="popupAssignId"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" readonly />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>


									</div>
								</div>
							</div>
							<div class="modal-footer">
								<!-- 	<button name ="previousRowModPart" class ="btn btn-default">Previous</button>
	<button name="nextRowModPart" class ="btn btn-default">Next</button> -->
							</div>

						</div>
					</div>
				</div>
			</div>

			<!-- end of popupAssignments -->




			<!-- popupActions -->
			<div class="container">
				<div id="DNModalActions"
					class="modal fade custom-class-assignedto-modal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #FF4F4F;">
								<h5 id="popupActions" class="modal-title"
									style="font-weight: bold; color: #3C1596;"></h5>

								<button type="button" name="insertBelowActions"
									onclick="insertRowBelowActions()"
									class="btn btn-default btn-primary BtnActive  "
									style="color: white; position: relative; left: 50px;">Insert
									Below</button>
								<button type="button" name="insertAboveActions"
									onclick="insertRowAboveActions()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 60px;">Insert
									Above</button>
								<button type="button" name="deleteBoqRowActions"
									onclick="deleteBoqRowActions()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 70px;">Delete</button>
								<button name="previousRowActions"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 80px;">Previous</button>
								<button name="nextRowActions" onclick="nextRowActions()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 90px;">Next</button>

								<button type="button" name="closeModPartPopup" class="close"
									data-dismiss="modal">
									<i class='fa fa-times'></i>
								</button>
								<a class="close modalMinimize ml-3"> <i
									class='fa fa-minus icon-to-change'></i>
								</a>
							</div>
							<div class="modal-body">
								<ul class="nav nav-tabs" id="myTab" role="tablist"
									style="background-color: #00757C;">
									<li class="nav-item"><a class="nav-link active"
										id="modPart-tab" style="color: gold;" data-toggle="tab"
										href="#modPart" role="tab" aria-controls="modPart"
										aria-selected="true">MODELS AND PART NUMBERS</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="modPart" role="tabpanel"
										aria-labelledby="modPart-tab">
										<p></p>
										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Creation Date</span> <input
																type="text" readonly
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupcreationdate" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Team</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupteam" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Employee</span> <input
																type="text" id="popupemployee"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Action</span> <input
																type="text" id="popupaction"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>

										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-12">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Description</span>
															<textarea id="popupdescription"
																class="form-control text-input" value="" cols="120"
																rows="7" id="descr" class="form-control text-input"></textarea>

														</div>
													</div>
												</div>


											</div>
										</div>
										<p></p>

										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Status</span> <input
																type="text" id="popupstatus"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">ActionID</span> <input
																type="text" id="popupactionID"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" readonly />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>


									</div>
								</div>
							</div>
							<div class="modal-footer">
								<!-- 	<button name ="previousRowModPart" class ="btn btn-default">Previous</button>
	<button name="nextRowModPart" class ="btn btn-default">Next</button> -->
							</div>

						</div>
					</div>
				</div>
			</div>

			<!--end of popupActions  -->

			<!-- popupDCP -->
			<div class="container">
				<div id="DNModalDCP"
					class="modal fade custom-class-assignedto-modal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #FF4F4F;">
								<h5 id="popupDCP" class="modal-title"
									style="font-weight: bold; color: #3C1596;"></h5>

								<button type="button" name="insertBelowDCP"
									onclick="insertRowBelowDCP()"
									class="btn btn-default btn-primary BtnActive  "
									style="color: white; position: relative; left: 50px;">Insert
									Below</button>
								<button type="button" name="insertAboveDCP"
									onclick="insertRowAboveDCP()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 60px;">Insert
									Above</button>
								<button type="button" name="deleteBoqRowDCP"
									onclick="deleteBoqRowDCP()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 70px;">Delete</button>
								<button name="previousRowDCP"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 80px;">Previous</button>
								<button name="nextRowDCP" onclick="nextRowDCP()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 90px;">Next</button>

								<button type="button" name="closeModPartPopup" class="close"
									data-dismiss="modal">
									<i class='fa fa-times'></i>
								</button>
								<a class="close modalMinimize ml-3"> <i
									class='fa fa-minus icon-to-change'></i>
								</a>
							</div>
							<div class="modal-body">
								<ul class="nav nav-tabs" id="myTab" role="tablist"
									style="background-color: #00757C;">
									<li class="nav-item"><a class="nav-link active"
										id="modPart-tab" style="color: gold;" data-toggle="tab"
										href="#modPart" role="tab" aria-controls="modPart"
										aria-selected="true">MODELS AND PART NUMBERS</a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="modPart" role="tabpanel"
										aria-labelledby="modPart-tab">
										<p></p>
										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Type</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popuptype" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Site ID</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupsiteID" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Site Name</span> <input
																type="text" id="popupsiteName"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Node ID</span> <input
																type="text" id="popupnodeID"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>

										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Node Name</span> <input
																type="text" id="popupnName"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" />

														</div>
													</div>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Cabinet</span> <input
																type="text" id="popupcabinet"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" />

														</div>
													</div>
												</div>

											</div>
										</div>
										<p></p>

										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Slot</span> <input
																type="text" id="popupslot"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Board</span> <input
																type="text" id="popupboard"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Antenna</span> <input
																type="text" id="popupantenna"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Version</span> <input
																type="text" id="popupversion"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Note</span> <input
																type="text" id="popupnote"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">ID</span> <input
																type="text" id="popupID" class="form-control text-input"
																value="" style="width: 674px; height: 37px;" readonly />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>

									</div>
								</div>
							</div>
							<div class="modal-footer">
								<!-- 	<button name ="previousRowModPart" class ="btn btn-default">Previous</button>
	<button name="nextRowModPart" class ="btn btn-default">Next</button> -->
							</div>

						</div>
					</div>
				</div>
			</div>

			<!--end of popupDCP -->
		</div>


	</div>

	<div id="popUpDiv" style="display: none;">
		<div class="sendEmail" style="margin-top: 50px;">
			<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Sender</span> <input type="text"
							id="email"
							class="ui-widget ui-widget-content ui-corner-all form-control" />
						<input type="text" id="password" value="${password}"
							class="ui-widget ui-widget-content ui-corner-all form-control"
							hidden />

					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width: 80px;"> Email
							To</span> <input type="text" id="emailTo"
							class="form-control text-input" />

					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width: 80px;">
							ccEmail </span> <input type="text" id="ccmail"
							class="form-control text-input" />

					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Title</span> <input type="text"
							id="subject" class="form-control text-input" />

					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Message</span>
						<textarea id="message" class="form-control text-input" cols="200"
							rows="4"></textarea>

					</div>
				</div>
			</div>
			<div class="col-md-12">
				<p></p>
				<div class="form-group" style="margin-left: 100px;">

					<button type="button" id="send" class="btn btn-primary BtnActive">
						<i class="fa fa-paper-plane"></i> Send
					</button>

					<button type="button" id="cancelButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-times"></i> Cancel
					</button>
				</div>
			</div>
		</div>
	</div>
</body>



<script type='text/javascript'>
	/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
	$("#sendEmail").on("click", function() {
		console.log("Helloooo in sendEmail onClick");
		$("#popUpDiv").fadeIn();

	});

	$("#cancelButton").on("click", function() {
		console.log("Helloooo in cancelButton onClick");
		$("#email").val('');
		$("#password").val('');
		$("#emailTo").val('');
		$("#ccmail").val('');
		$("#subject").val('');
		$("#message").val('');
		$("#popUpDiv").fadeOut();
	});

	$("#send").on(
			"click",
			function() {
				console.log("Helloooo in send onClick");
				if ($("#email").val() == '' || $("#emailTo").val() == ''
						|| $("#subject").val() == ''
						|| $("#message").val() == '') {
					alert("All Fields are required");
				} else {
					$("#popUpDiv").fadeOut();
				}

			});
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	var slctDelLFP = [];

	$("#Completed").click(function() {

		var Status = $("#status").val();
		$("#status").val('Completed').trigger('change');
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

	})
	$("#Closed").click(function() {

		var Status = $("#status").val();
		$("#status").val('Closed').trigger('change');
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

	})
	$("#Cancelled").click(function() {

		var Status = $("#status").val();
		$("#status").val('Cancelled').trigger('change');
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

	})

	var slctDelAssign = [];

	//delete function of Devices Caused Problem Tab
	function deleterowsAssign() {
		slctDelAssign = [];

		$("#AssignTab > tbody").find('input[name="chekbox_rowAssign"]').each(
				function() {

					if ($(this).is(":checked")) {

						var ID = $(this).parent().parent().children(
								'td[name="assignId"]').children('input').val();
						if (ID != null)
							slctDelAssign.push(ID);
						$(this).parents("tr").remove();
					}

				});

		if (slctDelAssign.length == 0) {
			alert(' Select Row(s) to Delete');
			return false;
		}
	}

	var slctDelDCP = [];

	function deleterowsDCP() {
		slctDelDCP = [];

		$("#dcproblemTab > tbody").find('input[name="chekbox_rowDCP"]').each(
				function() {

					if ($(this).is(":checked")) {

						var ID = $(this).parent().parent().children(
								'td[name="ID"]').children('input').val();
						if (ID != null)
							slctDelDCP.push(ID);
						$(this).parents("tr").remove();
					}

				});

		if (slctDelDCP.length == 0) {
			alert(' Select Row(s) to Delete');
			return false;
		}
	}
	//end of //delete function of Devices Caused Problem Tab
	var slctDelLFP = [];

	//delete function of Link Failure Places Tab
	function deleterowsLFP() {
		slctDelLFP = [];

		$("#lfpTab > tbody").find('input[name="chekbox_rowLFP"]').each(
				function() {

					if ($(this).is(":checked")) {

						var lfpID = $(this).parent().parent().children(
								'td[name="lfpID"]').children('input').val();
						if (lfpID != null)
						slctDelLFP.push(lfpID);

						$(this).parents("tr").remove();
					}
				});

		if (slctDelLFP.length == 0) {
			alert(' Select Row(s) to Delete');

			return false;
		}
	}
	//end of //delete function of Link Failure Places Tab

	var slctDelActions = [];

	//delete function of Actions Tab 
	function deleterows() {

		slctDelActions = [];
		var dictObj = {};
		$("#actionTab > tbody").find('input[name="chekbox_rowAction"]').each(
				function() {
					if ($(this).is(":checked")) {
						var actionID = $(this).parent().parent().children(
								'td[name="actionID"]').children('input').val();
						if (actionID != null)
							slctDelActions.push(actionID);
						$(this).parents("tr").remove();
					}

				});
		//end of //delete function of Actions Tab

		if (slctDelActions.length == 0) {
			alert(' Select Row(s) to Delete');
			return false;
		}
	}
	//END of //delete function of Actions Tab 

	// Delete row fctLFp
	function deleteBoqRow() {

		slctDelLFP = [];
		$("#lfpTab > tbody").each(function() {
			
			var lfpID = $("#popuplfpID").val();
			$("#lfpTab >tbody").find("tr").eq(rowindx).remove();
			var RowsToDelete = document.getElementById("RowToDeleteLFP").value;
			var Myresult = "";

			if (RowsToDelete != "") {

				Myresult = RowsToDelete;
			}
			Myresult += lfpID + ",";
			document.getElementById("RowToDeleteLFP").value = Myresult;

		});

		// Get Nb of rows after delete
		var rowNodeCount = $("#lfpTab >tbody tr").length;
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

		if (rowindx == 0 && rowNodeCount == 0) {

			$("#DNModalLFP").modal("hide");

		}
		if (rowindx >= 0 && rowindx < rowNodeCount) {
			//NodeRowIndex++;

			$('#popuplfpID').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="lfpID"]').children('input').val());
			$('#popuplinkID').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="linkID"]').children('input').val());
			$('#popuplinkName').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="linkName"]').children('input').val());
			$('#popuplongg').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="longg"]').children('input').val());
			$('#popuplatitude').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="latitude"]').children('input').val());
			$('#popupReason').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="Reason"]').children('input').val());
			$('#popuplfpdescription').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="lfpdescription"]').children('input')
							.val());
			//Update popup Nb in header 
			var element = document.getElementById("popupLFP");
			element.innerHTML = "Item # " + rowindx;
			//NodeRowIndex++;
		}
		// Show previous record 
		if (rowindx >= rowNodeCount) {
			rowindx--;

			$('#popuplfpID').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="lfpID"]').children('input').val());
			$('#popuplinkID').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="linkID"]').children('input').val());
			$('#popuplinkName').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="linkName"]').children('input').val());
			$('#popuplongg').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="longg"]').children('input').val());
			$('#popuplatitude').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="latitude"]').children('input').val());
			$('#popupReason').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="Reason"]').children('input').val());
			$('#popuplfpdescription').val(
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="lfpdescription"]').children('input')
							.val());

			//Update popup Nb in header 
			var element = document.getElementById("popupLFP");
			element.innerHTML = "Item # " + rowindx;
		}
	}// End delete fct 

	//Prev fct in popup
	$('#DNModalLFP').on('input', function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});
	});

	//Prev fct in popup
	$(function() {
		$('#DNModalLFP').on('input', function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({
				"background-color" : "orange"
			});
		});

		$("button[name='previousRow']").on(
				"click",
				function() {

					if (rowindx > 0) {
						rowindx--;

						var PrevIndex = parseInt(rowindx);

						$('#popuplinkID').val(
								$("#lfpTab >tbody").find("tr").eq(rowindx)
										.find('td[name="linkID"]').children(
												'input').val());
						$('#popuplinkName').val(
								$("#lfpTab >tbody").find("tr").eq(rowindx)
										.find('td[name="linkName"]').children(
												'input').val());
						$('#popuplongg').val(
								$("#lfpTab >tbody").find("tr").eq(rowindx)
										.find('td[name="longg"]').children(
												'input').val());

						$('#popuplatitude').val(
								$("#lfpTab >tbody").find("tr").eq(rowindx)
										.find('td[name="latitude"]').children(
												'input').val());
						$('#popupReason').val(
								$("#lfpTab >tbody").find("tr").eq(rowindx)
										.find('td[name="Reason"]').children(
												'input').val());
						$('#popuplfpdescription').val(
								$("#lfpTab >tbody").find("tr").eq(rowindx)
										.find('td[name="lfpdescription"]')
										.children('input').val());
						$('#popuplfpID').val(
								$("#lfpTab >tbody").find("tr").eq(rowindx)
										.find('td[name="lfpID"]').children(
												'input').val());

						//Update popup Nb in header
						var element = document.getElementById("popupLFP");
						element.innerHTML = "Item # " + PrevIndex;
					}

					// Close popup on row 0 (end of prev fct)
					else if (rowindx == 0) {
						$("#DNModalLFP").modal("hide");
					}

				});// end prev fct

		//Send input values from popup to boq when any popup input change
		$(
				'#popuplinkID,#popuplinkName, #popuplongg, #popuplatitude,#popupReason,#popuplfpdescription,#popuplfpID')
				.on(
						' focusout ',
						function() {

							$("#lfpTab >tbody").find("tr").eq(rowindx).find(
									'td[name="linkID"]').children('input').val(
									$('#popuplinkID').val());
							$("#lfpTab >tbody").find("tr").eq(rowindx).find(
									'td[name="linkName"]').children('input')
									.val($('#popuplinkName').val());
							$("#lfpTab >tbody").find("tr").eq(rowindx).find(
									'td[name="longg"]').children('input').val(
									$('#popuplongg').val());
							$("#lfpTab >tbody").find("tr").eq(rowindx).find(
									'td[name="latitude"]').children('input')
									.val($('#popuplatitude').val());
							//$("select[id = 'popupTransType'] option[value ='"+$('#popupTransType').val()+"'").attr("selected","selected");
							$("#lfpTab >tbody").find("tr").eq(rowindx).find(
									'td[name="Reason"]').children('input').val(
									$('#popupReason').val());
							$("#lfpTab >tbody").find("tr").eq(rowindx).find(
									'td[name="lfpdescription"]').children(
									'input').val(
									$('#popuplfpdescription').val());
							$("#lfpTab >tbody").find("tr").eq(rowindx).find(
									'td[name="lfpID"]').children('input').val(
									$('#popuplfpID').val());

							//SetCalcPopUp();
						}); // end fct

		// Close popup function
		$("button[name='closePopup']").on(
				"click",
				function() {

					// Send input values from popup to boq table
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="linkID"]').children('input').val(
							$('#popuplinkID').val());
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="linkName"]').children('input').val(
							$('#popuplinkName').val());
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="longg"]').children('input').val(
							$('#popuplongg').val());
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="latitude"]').children('input').val(
							$('#popuplatitude').val());
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="Reason"]').children('input').val(
							$('#popupReason').val());
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="lfpdescription"]').children('input').val(
							$('#popuplfpdescription').val());
					$("#lfpTab >tbody").find("tr").eq(rowindx).find(
							'td[name="lfpID"]').children('input').val(
							$('#popuplfpID').val());

					$("#DNModalLFP").modal("hide");

				}); // end close fct

		// Send input values from popup to boq table and close the popup using ESC
		document.addEventListener('keydown', function(event) {
			if (event.key === "Escape") {
				$("#lfpTab >tbody").find("tr").eq(rowindx).find(
						'td[name="linkID"]').children('input').val(
						$('#popuplinkID').val());
				$("#lfpTab >tbody").find("tr").eq(rowindx).find(
						'td[name="linkName"]').children('input').val(
						$('#popuplinkName').val());
				$("#lfpTab >tbody").find("tr").eq(rowindx).find(
						'td[name="longg"]').children('input').val(
						$('#popuplongg').val());
				$("#lfpTab >tbody").find("tr").eq(rowindx).find(
						'td[name="latitude"]').children('input').val(
						$('#popuplatitude').val());
				//$("select[id = 'popupTransType'] option[value ='"+$('#popupTransType').val()+"'").attr("selected","selected");
				$("#lfpTab >tbody").find("tr").eq(rowindx).find(
						'td[name="Reason"]').children('input').val(
						$('#popupReason').val());
				$("#lfpTab >tbody").find("tr").eq(rowindx).find(
						'td[name="lfpdescription"]').children('input').val(
						$('#popuplfpdescription').val());
				$("#lfpTab >tbody").find("tr").eq(rowindx).find(
						'td[name="lfpID"]').children('input').val(
						$('#popuplfpID').val());

				$("#DNModalLFP").modal("hide");

			}
		});// end close fct using esc

		// Resize and drag the popup
		$('.modal-content').resizable({
			handles : "e",

		});

		$('.modal-dialog').draggable({
			handle : ".modal-header, .modal-footer"
		});

		$('#DNModalLFP').on('show.bs.modal', function() {
			$(this).find('.modal-body').css({
				'max-height' : '100%',
			});
		});

		//Reset the popup to original size after resizing
		$('#DNModalLFP').on('hidden.bs.modal', function() {
			$(this).find('.modal-content').css({
				'width' : '',
				'height' : ''
			});
		})

		//Reset popup position after it has been dragged and closed
		$('body').on('hidden.bs.modal', function() {
			$('.modal-dialog').css({
				'top' : '',
				'left' : ''
			});
		})

		// Minimize and Maximize fct for popup
		$(".modalMinimize").on(
				"click",
				function() {
					$(".modal-body").slideToggle();
					$(".modal-footer").slideToggle();

					//Toggle between minimize/maximize icons
					var iconToChange = $('.icon-to-change');
					if (iconToChange.hasClass('fa-window-restore')) {
						iconToChange.removeClass('fa-window-restore').addClass(
								'fa-minus')
					} else {
						iconToChange.addClass('fa-window-restore').removeClass(
								'fa-minus')
					}
				}); // end minimize/maximize fct
	});

	var addNew = "addNew";

	if ('${ListLFP}' != "addNew") {
		var countDCProws = 0;
		boqArrayNode = [];
		boqArrayNode = ${ListLFP};

		for (i = 0; i < boqArrayNode.length; i++) {
			countLFProws = i + 1;
			var lfpId = boqArrayNode[i].lfpId;
			var linkId = boqArrayNode[i].linkId;
			var linkName = boqArrayNode[i].linkName;
			var longg = boqArrayNode[i].longg;
			var latitude = boqArrayNode[i].latitude;
			var reason = boqArrayNode[i].reason;
			var description = boqArrayNode[i].description;
			var itemActionRow = "<tr>";
			itemActionRow = itemActionRow
					+ "<td style='text-align:center;'><input type='checkbox' name='chekbox_rowLFP' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopLFP(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			itemActionRow = itemActionRow
					+ "<td name='linkID' style='width:200px'><input name='linkID' style='width:200px'  type='text' value='"+linkId+"' style='width:200px;' class='form-control input-text' /></td>";
			itemActionRow = itemActionRow
					+ "<td name='linkName'><input name='linkName'   type='text' value='" +linkName+"' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='longg' style='width:200px'><input name='longg' style='width:200px'  type='text' value='"+longg+"' style='width:200px;' class='form-control input-text' /></td>";
			itemActionRow = itemActionRow
					+ "<td name='latitude'><input name='latitude'   type='text' value='" +latitude+ "' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='Reason'><input name='Reason'   type='text' value='" +reason+ "' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='lfpdescription'><input name='lfpdescription' type='text' value='"+description+"' style='width:400px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='lfpID' style='width:200px'><input name='lfpID' style='width:200px'  type='text' value='"+lfpId+"' style='width:200px;' class='form-control input-text' readonly/></td>";

			itemActionRow = itemActionRow + "</tr>";

			$("#lfpTab > tbody").append(itemActionRow);
		}
	}

	var ListDCP = $
	{
		ListDCP
	};

	if ('${ListDCP}' != "addNew") {
		var countDCProws = 0;
		boqArrayNode = [];
		boqArrayNode = ${ListDCP};

		for (i = 0; i < boqArrayNode.length; i++) {
			countDCProws = i + 1;
			var team = boqArrayNode[i].team;
			var type = boqArrayNode[i].type;
			var siteID = boqArrayNode[i].siteId;
			var siteName = boqArrayNode[i].siteName;
			var nodeID = boqArrayNode[i].nodeId;
			var nName = boqArrayNode[i].nodeName;
			var cabinet = boqArrayNode[i].cabinet;
			var slot = boqArrayNode[i].slot;
			var board = boqArrayNode[i].board;
			var antenna = boqArrayNode[i].antenna;
			var version = boqArrayNode[i].version;
			var note = boqArrayNode[i].note;
			var ID = boqArrayNode[i].id;

			var itemActionRow = "<tr>";
			itemActionRow = itemActionRow
					+ "<td style='text-align:center;'><input type='checkbox' name='chekbox_rowDCP' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopDCP(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			itemActionRow = itemActionRow
					+ "<td name='type' style='width:200px'><input name='type' style='width:200px'  type='text' value='"+type+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='siteID' style='width:200px'><input name='siteID' style='width:200px'  type='text' value='"+siteID+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='siteName'><input name='siteName'   type='text' value='" +siteName+"' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='nodeID' style='width:200px'><input name='nodeID' style='width:200px'  type='text' value='"+nodeID+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='nName'><input name='nName'   type='text' value='" +nName+ "' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='cabinet'><input name='cabinet'   type='text' value='" +cabinet+ "' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='slot' style='width:200px'><input name='slot' style='width:200px'  type='text' value='"+slot+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='board'><input name='board' id='"+board+"'  type='text' value='" +board +"' style='width:200px;' class='form-control input-text' /></td>";
			itemActionRow = itemActionRow
					+ "<td name='antenna'><input name='antenna'   type='text' value='" +antenna +"' style='width:200px;' class='form-control input-text' /></td>";
			itemActionRow = itemActionRow
					+ "<td name='version'><input name='version'   type='text' value='" +version +"' style='width:200px;' class='form-control input-text' /></td>";
			itemActionRow = itemActionRow
					+ "<td name='note'><input name='note'  type='text' value='" +note +"' style='width:400px;' class='form-control input-text' /></td>";
			itemActionRow = itemActionRow
					+ "<td name='ID'><input name='ID'  type='text' value='" +ID +"' style='width:200px;' class='form-control input-text' readonly/></td>";

			itemActionRow = itemActionRow + "</tr>";

			$("#dcproblemTab > tbody").append(itemActionRow);

		}
		document.getElementById("CountDCP").value = countDCProws;
	}

	var addNew = "addNew";

	if ('${ListAssign}' != addNew) {

		boqArrayNode = [];
		boqArrayNode = ${ListAssign};

		for (i = 0; i < boqArrayNode.length; i++) {
			var assignId = boqArrayNode[i].assignId;
			var da = boqArrayNode[i].assign_date;
			var assignTo = boqArrayNode[i].assignTo;
			var assignBy = boqArrayNode[i].assignBy;
			var requiredAction = boqArrayNode[i].requiredAction;
			var note = boqArrayNode[i].note;
			var createDate = boqArrayNode[i].createDate;
			var lmd = boqArrayNode[i].lastModifiedDate;
			var lastModifiedDate = moment(lmd).format('L') + " "
					+ moment(lmd).format('LT'); // 04/26/2021
			var creationDate = new Date(createDate);
			var eyyy = moment(createDate).format('L') + " "
					+ moment(createDate).format('LT'); // 04/26/2021
			var d = new Date(da);
			var mm = ("0" + (d.getMonth() + 1)).slice(-2);
			var dd = ("0" + d.getDate()).slice(-2);
			var yy = d.getFullYear();
			var assign_date = yy + '-' + mm + '-' + dd; //(US)

			var itemActionRow = "<tr>";
			itemActionRow = itemActionRow
					+ "<td style='text-align:center;'><input type='checkbox' name='chekbox_rowAssign' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopAssign(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			itemActionRow = itemActionRow
					+ "<td name='assignDate' style='width:200px'><input name='assignDate' style='width:200px'  type='text' value='"+assign_date+"' style='width:200px;' class='form-control input-text' readonly/> </td>";
			itemActionRow = itemActionRow
					+ "<td name='assignTo' style='width:200px'><input name='assignTo' style='width:200px'  type='text' value='"+assignTo+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='assignBy'><input name='assignBy'   type='text' value='" +assignBy+ "' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='requiredAction' style='width:200px'><input name='requiredAction' style='width:200px'  type='text' value='"+requiredAction+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='note' style='width:400px'><input name='note'   type='text' value='" +note+ "' style='width:400px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='createDate' style='width:200px'><input name='createDate'   type='text' value='" +eyyy+ "' style='width:200px;' class='form-control input-text' readonly/></td>";
			itemActionRow = itemActionRow
					+ "<td name='lastModifiedDate' style='width:200px'><input name='lastModifiedDate'   type='text' value='" +lastModifiedDate+ "' style='width:200px;' class='form-control input-text' readonly/></td>";
			itemActionRow = itemActionRow
					+ "<td name='assignId'><input name='countt' id='assignId'  type='text'  value='" +assignId+ "' style='width:200px;' class='form-control input-text' readonly/></td>";
			itemActionRow = itemActionRow + "</tr>";

			$("#AssignTab > tbody").append(itemActionRow);
		}
	}

	if ('${ListAcArray}' != "addNew") {
		boqArrayNode = [];
		boqArrayNode = ${ListAcArray};

		for (i = 0; i < boqArrayNode.length; i++) {

			var actionId = boqArrayNode[i].actionId;
			var team = boqArrayNode[i].team;
			var employee = boqArrayNode[i].employee;
			var action = boqArrayNode[i].action;
			var description = boqArrayNode[i].description;
			var status = boqArrayNode[i].status;
			var taa = boqArrayNode[i].creationDate;
			var creationDate = new Date(taa);
			var eyyy = moment(taa).format('L') + " " + moment(taa).format('LT'); // 04/26/2021
			var c = boqArrayNode.length - 1;
			var res = "acID" + i;
			var kk = i;

			var itemActionRow = "<tr>";
			itemActionRow = itemActionRow
					+ "<td style='text-align:center;'><input type='checkbox' name='chekbox_rowAction' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopActions(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			itemActionRow = itemActionRow
					+ "<td name='creationDate' style='width:200px'><input name='creationDate' style='width:200px'  type='text' value='"+eyyy+"' style='width:200px;' class='form-control input-text' readonly/> </td>";
			itemActionRow = itemActionRow
					+ "<td name='team' style='width:200px'><input name='team' style='width:200px'  type='text' value='"+team+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='employee'><input name='employee'   type='text' value='" +employee+ "' style='width:200px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='action' style='width:200px'><input name='action' style='width:200px'  type='text' value='"+action+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='description' style='width:400px'><input name='description'   type='text' value='" +description+ "' style='width:400px;' class='form-control input-text'/></td>";
			itemActionRow = itemActionRow
					+ "<td name='status' style='width:200px'><input name='status' style='width:200px'  type='text' value='"+status+"' style='width:200px;' class='form-control input-text' /> </td>";
			itemActionRow = itemActionRow
					+ "<td name='actionID'><input name='actionId' id='"+res+"'  type='text' value='" +actionId +"' style='width:200px;' class='form-control input-text' readonly/></td>";
			itemActionRow = itemActionRow
					+ "<td name='countt'><input name='countt' id='countt'  type='text' hidden  value='" +kk+ "' style='width:200px;' class='form-control input-text' readonly/></td>";
			itemActionRow = itemActionRow + "</tr>";

			$("#actionTab > tbody").append(itemActionRow);
		}
	}


	if ('${ListPRqItem}' == "addNew") {
		$("#formStatus").text("New");
		$('.dot').css({
			"background-color" : "orange"
		});
	} else {
	}
	$(document).ready(function() {



						if('${SelectedIndex}' != "addNew"){
							var SelectedIndex = ${SelectedIndex};
							if('${TTCount}' != "addNew"){

							
						var TTCount = ${TTCount};
						
						if(($("#ticketId").val()) != "" && ($("#ticketId").val()) != null){

						if(SelectedIndex === TTCount){
							document.getElementById("btnLast").style.opacity = 0.5;
							$("#btnLast").hasClass("disabled");
							document.getElementById("btnLast").style.pointerEvents = "none";
							
							document.getElementById("btnNexta").style.opacity = 0.5;
							document.getElementById("btnNexta").style.pointerEvents = "none";

							
							$("#btnNexta").hasClass("disabled");
							
							}else{
								
								$("#btnNext").click(function(){
								if(!$("#btnNexta").hasClass("disabled")){
										
										var param ="${pageContext.request.contextPath}/TroubleTicketFormView?tkID="+$("#ticketId").val()+"&NavAction=1";
										window.location.href =param;
									}
									
									});
						
								if(!$("#btnLst").hasClass("disabled")){
									
									$("#btnLst").click(function(){
										
										var param ="${pageContext.request.contextPath}/TroubleTicketFormView?tkID="+$("#ticketId").val()+"&NavAction=4";
										window.location.href =param;
							
									});
						
								}

							}
						
						if(SelectedIndex === 1){ //first record in database
							document.getElementById("btnFirst").style.opacity = 0.5;
							$("#btnFirst").hasClass("disabled");
							document.getElementById("btnFirst").style.pointerEvents = "none";
							
							document.getElementById("btnPrva").style.opacity = 0.5;
							$("#btnPrva").hasClass("disabled");
							document.getElementById("btnPrv").style.pointerEvents = "none";
						
						}else{
							$("#btnPrv").click(function(){
								
							if(!$("#btnPrva").hasClass("disabled")){
								
									
									var areaId=$("#areaId").val();
									var param ="${pageContext.request.contextPath}/TroubleTicketFormView?tkID="+$("#ticketId").val()+"&NavAction=0";
									location.reload();

									window.location.href =param;
								}
								
								 });
							$("#btnFrst").click(function(){

								if(!$("#btnFrst").hasClass("disabled")){
										
									var param ="${pageContext.request.contextPath}/TroubleTicketFormView?tkID="+$("#ticketId").val()+"&NavAction=3";
									window.location.href =param;
											
									}
									 });

						}
						
						}}
					}
						$("#label-1").text((SelectedIndex)+"/"+TTCount);

						 
				
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
$("#email").autocomplete({
											source : function(request, response) {

												var password = $("#password")
														.val();
												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllEmailAccounts',
															data : {
																"email" : request.term,
															//"password":request.value,
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListItemEmailAccounts);
																	console
																			.log('data in $("#email").autocomplete is :  '
																					+ data.ListItemEmailAccounts);

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});

											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {

												this.value = (ui.item ? ui.item[0]
														: '');
												password.value = ui.item[1];

												return false;

											}

										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0] + "</span></div>")
									.appendTo(ul);
						};

						$("#email").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});
						$("#password").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						//////////////////////////////////////////////////////////////////////////////		  	

						$("input").change(function() {
							$("#formStatus").text("Not Saved");
							$('.dot').css({
								"background-color" : "orange"
							});
						});

						if (document.getElementById("CountDCP").value > 0) {
							$('#DCPshow').fadeIn('fast');
						} else {
							$('#DCPshow').fadeIn('fast');
						}
						if (document.getElementById("CountDCP").value > 0) {
							$('#noDev').hide();
							$('#DCPspan').hide();
						} else {
							$('#DCPshow').fadeIn('fast');
						}

						$('#noDev').change(function() {
							if (!this.checked)
								$('#DCPshow').fadeIn('fast');
							else {
								$('#DCPshow').fadeOut('fast');
							}
						});
						$('#noDev').change();

						var dictAssign = [];
						var dict = [];
						var Errorflag;
						var flagFrom;

						function selectAllRows() {

							if ($(this).hasClass('allChecked')) {
								$('input[name="chekbox_rowAction"]',
										'#actionTab').prop('checked', false);
							} else {
								$('input[name="chekbox_rowAction"]',
										'#actionTab').prop('checked', true);
							}
							$(this).toggleClass('allChecked');
						}

						function selectAllz() {

							if ($(this).hasClass('allChecked')) {
								$('input[name="chekbox_rowDCP"]',
										'#dcproblemTab').prop('checked', false);
							} else {
								$('input[name="chekbox_rowDCP"]',
										'#dcproblemTab').prop('checked', true);
							}
							$(this).toggleClass('allChecked');
						}

						$('body').on(
								'click',
								'#selectAllz',
								function() {

									if ($(this).hasClass('allChecked')) {
										$('input[name="chekbox_rowDCP"]',
												'#dcproblemTab').prop(
												'checked', false);
									} else {
										$('input[name="chekbox_rowDCP"]',
												'#dcproblemTab').prop(
												'checked', true);
									}
									$(this).toggleClass('allChecked');
								});

						function selectAllassign() {

							if ($(this).hasClass('allChecked')) {
								$('input[name="chekbox_rowAssign"]',
										'#AssignTab').prop('checked', false);
							} else {
								$('input[name="chekbox_rowAssign"]',
										'#AssignTab').prop('checked', true);
							}
							$(this).toggleClass('allChecked');
						}

						$('body').on(
								'click',
								'#selectAllassign',
								function() {

									if ($(this).hasClass('allChecked')) {
										$('input[name="chekbox_rowAssign"]',
												'#AssignTab').prop('checked',
												false);
									} else {
										$('input[name="chekbox_rowAssign"]',
												'#AssignTab').prop('checked',
												true);
									}
									$(this).toggleClass('allChecked');
								});

						function selectAllRows() {

							if ($(this).hasClass('allChecked')) {
								$('input[name="chekbox_rowAction"]',
										'#actionTab').prop('checked', false);
							} else {
								$('input[name="chekbox_rowAction"]',
										'#actionTab').prop('checked', true);
							}
							$(this).toggleClass('allChecked');
						}

						$('body').on(
								'click',
								'#selectAllz',
								function() {

									if ($(this).hasClass('allChecked')) {
										$('input[name="chekbox_rowDCP"]',
												'#dcproblemTab').prop(
												'checked', false);
									} else {
										$('input[name="chekbox_rowDCP"]',
												'#dcproblemTab').prop(
												'checked', true);
									}
									$(this).toggleClass('allChecked');
								});

						$('body').on(
								'click',
								'#selectAllLFP',
								function() {

									if ($(this).hasClass('allChecked')) {
										$('input[name="chekbox_rowLFP"]',
												'#lfpTab').prop('checked',
												false);
									} else {
										$('input[name="chekbox_rowLFP"]',
												'#lfpTab')
												.prop('checked', true);
									}
									$(this).toggleClass('allChecked');
								});

						function selectAllRowsLFP() {

							if ($(this).hasClass('allChecked')) {
								$('input[name="chekbox_rowLFP"]', '#lfpTab')
										.prop('checked', false);
							} else {
								$('input[name="chekbox_rowLFP"]', '#lfpTab')
										.prop('checked', true);
							}
							$(this).toggleClass('allChecked');
						}

						function selectAllCheckedRows() {

							if ($(this).hasClass('Checked')) {
								$('input[name="chekbox_rowAction"]',
										'#actionTab').prop('checked', false);
							} else {
								$('input[name="chekbox_rowAction"]',
										'#actionTab').prop('checked', true);
							}
							$(this).toggleClass('Checked');
						}

						$('body').on(
								'click',
								'#selectAll',
								function() {

									if ($(this).hasClass('allChecked')) {
										$('input[name="chekbox_rowAction"]',
												'#actionTab').prop('checked',
												false);
									} else {
										$('input[name="chekbox_rowAction"]',
												'#actionTab').prop('checked',
												true);
									}
									$(this).toggleClass('allChecked');
								});

						//delete fct for DCP
						function PopupDynamicDeleteDCP() {

							var RowsToDelete = document
									.getElementById("RowToDeleteDCP").value;
							RowsToDelete.slice(0, -1);
							var rows = RowsToDelete.split(",");
							$("#dcproblemTab > tbody").each(function() {

								for (let i = 0; i < rows.length; i++) {
									slctDelDCP.push(rows[i]);
								}
							});
						}
						//delete fct for lfp
						function PopupDynamicDelete() {

							var RowsToDelete = document
									.getElementById("RowToDeleteLFP").value;
							RowsToDelete.slice(0, -1);
							var rows = RowsToDelete.split(",");
							$("#lfpTab > tbody").each(function() {

								for (let i = 0; i < rows.length; i++) {
									slctDelLFP.push(rows[i]);
								}
							});
						}

						//delete fct for assign
						function PopupDynamicDeleteAssign() {

							var RowsToDelete = document
									.getElementById("RowToDeleteAssign").value;
							RowsToDelete.slice(0, -1);
							var rows = RowsToDelete.split(",");
							$("#AssignTab > tbody").each(function() {

								for (let i = 0; i < rows.length; i++) {
									slctDelAssign.push(rows[i]);
								}
							});
						}

						//delete fct for Actions
						function PopupDynamicDeleteActions() {

							var RowsToDelete = document
									.getElementById("RowToDeleteActions").value;
							RowsToDelete.slice(0, -1);
							var rows = RowsToDelete.split(",");
							$("#actionTab > tbody").each(function() {

								for (let i = 0; i < rows.length; i++) {
									slctDelActions.push(rows[i]);
								}
							});
						}

						function getselectedrowsAssign() {
							dictAssign = [];
							$("#AssignTab > tbody")
									.find('input[name="chekbox_rowAssign"]')
									.each(
											function() {
												dictAssign
														.push({
															"assignId" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="assignId"]')
																	.children(
																			'input')
																	.val(),
															"assignDate" : $(
																	this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="assignDate"]')
																	.children(
																			'input')
																	.val(),
															"createDate" : $(
																	this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="createDate"]')
																	.children(
																			'input')
																	.val(),
															"lastModifiedDate" : $(
																	this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="lastModifiedDate"]')
																	.children(
																			'input')
																	.val(),
															"assignBy" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="assignBy"]')
																	.children(
																			'input')
																	.val(),
															"assignTo" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="assignTo"]')
																	.children(
																			'input')
																	.val(),
															"requiredAction" : $(
																	this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="requiredAction"]')
																	.children(
																			'input')
																	.val(),
															"note" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="note"]')
																	.children(
																			'input')
																	.val(),
														});
											});
						}

						function getselectedrows() {
							dict = [];
							$("#actionTab > tbody")
									.find('input[name="chekbox_rowAction"]')
									.each(
											function() {
												dict
														.push({
															"actionID" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="actionID"]')
																	.children(
																			'input')
																	.val(),
															"team" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="team"]')
																	.children(
																			'input')
																	.val(),
															"creationDate" : $(
																	this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="creationDate"]')
																	.children(
																			'input')
																	.val(),
															"action" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="action"]')
																	.children(
																			'input')
																	.val(),
															"description" : $(
																	this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="description"]')
																	.children(
																			'input')
																	.val(),
															"status" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="status"]')
																	.children(
																			'input')
																	.val(),
															"note" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="note"]')
																	.children(
																			'input')
																	.val(),
															"employee" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="employee"]')
																	.children(
																			'input')
																	.val(),
														});
											});
						}

						function getselectedrowsLFP() {
							dictLFP = [];
							$("#lfpTab > tbody")
									.find('input[name="chekbox_rowLFP"]')
									.each(
											function() {
												dictLFP
														.push({
															"lfpID" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="lfpID"]')
																	.children(
																			'input')
																	.val(),
															"linkID" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="linkID"]')
																	.children(
																			'input')
																	.val(),
															"linkName" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="linkName"]')
																	.children(
																			'input')
																	.val(),
															"longg" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="longg"]')
																	.children(
																			'input')
																	.val(),
															"latitude" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="latitude"]')
																	.children(
																			'input')
																	.val(),
															"Reason" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="Reason"]')
																	.children(
																			'input')
																	.val(),
															"lfpdescription" : $(
																	this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="lfpdescription"]')
																	.children(
																			'input')
																	.val()
														});
											});
						}

						function getselectedrowsResolution() {
							dictResolution = [];
							$("#dcproblemTab > tbody")
									.find('input[name="chekbox_rowDCP"]')
									.each(
											function() {
												dictResolution
														.push({
															"siteID" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="siteID"]')
																	.children(
																			'input')
																	.val(),
															"type" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="type"]')
																	.children(
																			'input')
																	.val(),
															"ID" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="ID"]')
																	.children(
																			'input')
																	.val(),
															"siteName" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="siteName"]')
																	.children(
																			'input')
																	.val(),
															"nodeID" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="nodeID"]')
																	.children(
																			'input')
																	.val(),
															"nodeName" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="nName"]')
																	.children(
																			'input')
																	.val(),
															"cabinet" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="cabinet"]')
																	.children(
																			'input')
																	.val(),
															"slot" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="slot"]')
																	.children(
																			'input')
																	.val(),
															"board" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="board"]')
																	.children(
																			'input')
																	.val(),
															"antenna" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="antenna"]')
																	.children(
																			'input')
																	.val(),
															"version" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="version"]')
																	.children(
																			'input')
																	.val(),
															"note" : $(this)
																	.parent()
																	.parent()
																	.children(
																			'td[name="note"]')
																	.children(
																			'input')
																	.val()
														});
											});
						}

						$("#deleteButton")
								.click(
										function() {
											var deleteArray = [];
											deleteArray.push($("#ticketId")
													.val());
											alert($("#ticketId").val());

											$
													.ajax({
														type : "GET",
														url : "${pageContext.request.contextPath}/TroubleTicketDelete",
														dataType : "json",
														data : {
															"tkID" : deleteArray
														},
														success : function(data) {
															location.reload();

														},
														error : function(error) {
															console
																	.log("The error is "
																			+ error);
														}
													});
											location
													.replace("${pageContext.request.contextPath}/TroubleTicketListView");

										})

						$("#saveButton")
								.click(
										function() {
											PopupDynamicDeleteDCP();
											PopupDynamicDeleteActions();
											PopupDynamicDelete();
											PopupDynamicDeleteAssign();
											flagFrom = "save";
											console.log('saved now');

											if ($("#dep").val() == '') {
												alert('tkdepartment cannot be Null');
												return false;
											}
											val = $("#creationDate").val();
											val1 = Date.parse(val);
											if (isNaN(val1) == true) {
												alert(' Create Date is not valid');
												return false;
											}

											val = $("#lastModifiedDate").val();
											val1 = Date.parse(val);
											if (isNaN(val1) == true) {
												alert(' Modified Date is not valid');
												return false;
											}

											val = $("#issueAppeared").val();
											val1 = Date.parse(val);
											if (isNaN(val1) == true) {

												alert('issue Appeared is not valid');
												return false;
											}

											getselectedrowsAssign();
											getselectedrowsLFP();
											getselectedrowsResolution();
											getselectedrows();

											$
													.ajax({

														type : "GET",
														url : "${pageContext.request.contextPath}/TroubleTicketFormSave",
														dataType : "json",
														data : {

															"dictParameterbarcode" : dictAssign,
															"dictParameter" : dict,
															"dictParameterArea" : dictResolution,
															"dictParameternode" : dictLFP,
															"slctDelDCP" : slctDelDCP,
															"slctDelLFP" : slctDelLFP,
															"slctDelAssign" : slctDelAssign,
															"slctDelActions" : slctDelActions,

															"tkID" : $(
																	"#ticketId")
																	.val(),
															"rcaDescription" : $(
																	"#rcaDescription")
																	.val(),
															"reasonForProblem" : $(
																	"#reasonForProblem")
																	.val(),
															"tkresolutionAction" : $(
																	"#resolutionAction")
																	.val(),
															"tkresolutionDate" : $(
																	"#resolutionDate")
																	.val(),
															"tkresolutionDescription" : $(
																	"#resolutionDescription")
																	.val(),
															"tkDepartment" : $(
																	"#dep")
																	.val(),
															"tkSubject" : $(
																	"#subject")
																	.val(),
															"tkdescription" : $(
																	"#descr")
																	.val(),
															"TkCreationDate" : $(
																	"#creationDate")
																	.val(),
															"TklastModifiedDate" : $(
																	"#lastModifiedDate")
																	.val(),
															"tkService" : $(
																	"#service")
																	.val(),
															"tkClient" : $(
																	"#client")
																	.val(),
															"tkRegion" : $(
																	"#region")
																	.val(),
															"tkServiceIssue" : $(
																	"#servicei")
																	.val(),
															"tkIssueAppeared" : $(
																	"#issueAppeared")
																	.val(),
															"tkSiteId" : $(
																	"#siteId")
																	.val(),
															"tksiteName" : $(
																	"#siteNamee")
																	.val(),
															"tkStatus" : $(
																	"#status")
																	.val(),
															"tkPriority" : $(
																	"#priority")
																	.val(),
															"email" : $(
																	"#email")
																	.val(),
															"password" : $(
																	"#password")
																	.val(),
															"emailTo" : $(
																	"#emailTo")
																	.val(),
															"ccmail" : $(
																	"#ccmail")
																	.val(),
															"subject" : $(
																	"#subject")
																	.val(),
															"message" : $(
																	"#message")
																	.val(),

														},
														success : function(data) {
															$('.dot')
																	.css(
																			{
																				"background-color" : "orange"
																			});
															console
																	.log("The returned data is "
																			+ data.BassamTest);
															$(
																	'#lastModifiedDate')
																	.val(
																			data.TklastModifiedDate);
															$("#ticketId").val(
																	data.tkID);
															var param = "${pageContext.request.contextPath}/TroubleTicketFormView?tkID="+ $("#ticketId").val()+"&NavAction=2";
															location.replace(param);
														},
														error : function(error) {

															console
																	.log("The error is "
																			+ error);
														}
													});
										})

						$("#region")
								.autocomplete(
										{

											source : function(request, response) {

												var region = $("#region").val();
												$
														.ajax({

															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllRegions',
															data : {

																"regionID" : region,
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.listRegions);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {
												region.value = (ui.item ? ui.item[0]
														+ ":"
														+ ui.item[1]
														+ ":" + ui.item[2]
														: '');
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0]
											+ "</span><br><span class='desc'>"
											+ item[1]
											+ "</span><br><span class='desc'>"
											+ item[2] + "</span></div>")
									.appendTo(ul);
						};
						$("#region").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						$("#client")
								.autocomplete(
										{

											source : function(request, response) {

												var client = $("#client").val();

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllClients',

															data : {

																"client" : client,

															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListClient);

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {
												client.value = (ui.item ? ui.item[0]
														+ ":" + ui.item[1]
														: '');

												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0]
											+ "</span><br><span class='desc'>"
											+ item[1] + "</span></div>")
									.appendTo(ul);
						};
						$("#client").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});


						$("#siteId")
								.autocomplete(
										{

											source : function(request, response) {

												var siteId = $("#siteId").val();

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {

																"Site" : siteId,
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.listSite);

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {
												siteId.value = (ui.item ? ui.item[1]
														: '');
												document
														.getElementById("siteNamee").value = ui.item[0];
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[1]
											+ "</span><br><span class='desc'>"
											+ item[0] + "</span></div>")
									.appendTo(ul);
						};
						$("#siteId").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						function getAllSitesName() {
							SnArray = [];
							var SnValue = "";
							$("input[name=siteName]").each(function() {
								if ($(this).val() == "")
									SnValue = "empty";
								else
									SnValue = $(this).val();
								SnArray.push(SnValue);
							});
							return SnArray;
						}

						function SetIndexRow(obj) {
							var row_index = $(obj).parent().parent().index();
							$("#RowIndex").val(row_index);
						}
						function getAllSitesID() {
							SiArray = [];
							var SiValue = "";
							$("input[name=siteID]").each(function() {
								if ($(this).val() == "")
									SiValue = "empty";
								else
									SiValue = $(this).val();
								SiArray.push(SiValue);
							});
							return SiArray;
						}

						$('#lfpTab tr td input').on('focus', function() {

							SetIndexRowLFP(this);
						});

						function SetIndexRowLFP(obj) {
							var row_index = $(obj).parent().parent().index();
							$("#RowIndexLFP").val(row_index);
						}

						$('#dcproblemTab tr td input').on('focus', function() {

							SetIndexRow(this);
						});

						function SetIndexRowAssign(obj) {
							var row_index = $(obj).parent().parent().index();
							$("#RowIndex").val(row_index);
						}

						$('#AssignTab tr td input').on('focus', function() {

							SetIndexRowAssign(this);
						});

						function SetIndexRowAssign(obj) {
							var row_index = $(obj).parent().parent().index();
							$("#RowIndex").val(row_index);
						}

						$('input[name ="siteName"]')
								.each(
										function(i, el) {

											$(el)
													.autocomplete(
															{
																source : function(
																		request,
																		response,
																		event,
																		ui) {
																	console
																			.log("dd"
																					+ i);
																	var RowIndex = document
																			.getElementById('RowIndex').value;
																	SnArray = getAllSitesName();
																	var sitename = SnArray[RowIndex];

																	if (sitename != "empty") {
																		console
																				.log("hello");

																		siten = sitename
																				.split(":");
																		siten = sitename[0];
																	} else

																		$
																				.ajax({
																					type : "GET",
																					contentType : "application/json; charset=utf-8",
																					url : '${pageContext.request.contextPath}/GetAllWarehouse',
																					data : {
																						"Site" : request.term,

																					},
																					dataType : "json",
																					success : function(
																							data) {
																						if (data != null) {
																							response(data.listSite);
																						}
																					},
																					error : function(
																							result) {
																						alert("Error");
																					}
																				});
																},
																minLength : 0,
																maxShowItems : 4,
																scroll : true,
																select : function(
																		event,
																		ui) {
																	this.value = (ui.item ? ui.item[0]
																			: '');
																	$(this)
																			.parents(
																					"tr")
																			.find(
																					'input[name ="siteID"]')
																			.val(
																					ui.item[1]);
																	return false;

																}
															}).autocomplete(
															"instance")._renderItem = function(
													ul, item) {
												return $("<li class='each'>")
														.append(
																"<div class='acItem'><span class='name' style='font-weight:bold'>"
																		+ item[0]
																		+ "</span><br><span class='desc'>"
																		+ item[1]
																		+ "</span></div>")
														.appendTo(ul);
											};
											$(this)
													.focus(
															function() {
																if (this.value == "") {
																	$(this)
																			.autocomplete(
																					"search");
																}
															});
										});
						// Ending for item autocomplete in Boq table    			

						$('input[name ="siteID"]')
								.each(
										function(i, el) {

											$(el)
													.autocomplete(
															{
																source : function(
																		request,
																		response,
																		event,
																		ui) {
																	console
																			.log("dd"
																					+ i);
																	var RowIndex = document
																			.getElementById('RowIndex').value;
																	SnArray = getAllSitesID();
																	$
																			.ajax({
																				type : "GET",
																				contentType : "application/json; charset=utf-8",
																				url : '${pageContext.request.contextPath}/GetAllWarehouse',
																				data : {
																					"Site" : request.term,

																				},
																				dataType : "json",
																				success : function(
																						data) {
																					if (data != null) {
																						response(data.listSite);
																					}
																				},
																				error : function(
																						result) {
																					alert("Error");
																				}
																			});
																},
																minLength : 0,
																maxShowItems : 4,
																scroll : true,
																select : function(
																		event,
																		ui) {
																	this.value = (ui.item ? ui.item[1]
																			: '');
																	$(this)
																			.parents(
																					"tr")
																			.find(
																					'input[name ="siteName"]')
																			.val(
																					ui.item[0]);
																	return false;

																}
															}).autocomplete(
															"instance")._renderItem = function(
													ul, item) {
												return $("<li class='each'>")
														.append(
																"<div class='acItem'><span class='name' style='font-weight:bold'>"
																		+ item[1]
																		+ "</span><br><span class='desc'>"
																		+ item[0]
																		+ "</span></div>")
														.appendTo(ul);
											};
											$(this)
													.focus(
															function() {
																if (this.value == "") {
																	$(this)
																			.autocomplete(
																					"search");
																}
															});
										});
						// Ending for item autocomplete in Boq table    			

						$("#siteNamee")
								.autocomplete(
										{

											source : function(request, response) {

												var siteName = $("#siteNamee")
														.val();

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {

																"Site" : siteName,
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.listSite);
																	console
																			.log("dd");
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {
												siteNamee.value = (ui.item ? ui.item[0]
														: '');
												document
														.getElementById("siteId").value = ui.item[1];
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0]
											+ "</span><br><span class='desc'>"
											+ item[1] + "</span></div>")
									.appendTo(ul);
						};
						$("#siteNamee").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						//  SiteID autocomplete in DCPpopup
						$("#popupsiteID")
								.autocomplete(
										{
											source : function(request, response) {

												var siteName = $(
														"#popupsiteName").val();

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {
																"Site" : $(
																		"#popupsiteID")
																		.val(),
																"siteName" : siteName

															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.listSite);
																	console
																			.log('Site data: ;'
																					+ data.ListSiteID);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,
											select : function(event, ui) {
												document
														.getElementById("popupsiteName").value = ui.item[0];

												this.value = (ui.item ? ui.item[1]
														: '');
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0]
											+ "</span><br><span class='desc'>"
											+ item[1] + "</span></div>")
									.appendTo(ul);
						};
						$("#popupsiteID").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						}); // End SiteId autocomplete in Sitepopup


						$("#selectnav")
						.autocomplete(
								{
																	
							    source: function(request, response) {
								    
							             $.ajax({
							                 type: "GET",
							                 contentType: "application/json; charset=utf-8",
							                 url: '${pageContext.request.contextPath}/GetAllTroubleTickets',
							                 data: {
													"TTvalue" : $("#selectnav").val(),
											 },
							                 dataType: "json",
							                 success: function (data) {
							                     if (data != null) {
							                         response(data.listtroubleTickets);
							                     }
							                 },
							                 error: function(result) {
							                     alert("Error");
							                 }
							             });
							         }, minLength:0, maxShowItems: 40, scroll:true,

										select: function(event, ui) {
											
											this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
											var param ="${pageContext.request.contextPath}/TroubleTicketFormView?tkID="+(ui.item[0])+"&NavAction=2";
											window.location.href =param;
					       						
					       						return false;
					       					}
					       				}).autocomplete("instance")._renderItem = function(ul, item) {
					       	 		    	return $('<li class="each">').data( "item.autocomplete", item )
					       		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
					       	                 item[0] + '</span><br><span class="desc">' +
					       	                 item[1] + '</span></div></li>').appendTo(ul);
					       			};
					       					$("#selectnav").focus(function(){
					       		   	        	if (this.value == ""){
					       		   	            	$(this).autocomplete("search");
					       		   	        	}						
					       					});   //// ENd of Autocomplete for Area ID



											$('input[name ="assignTo"]').each(function(i, el) {
												$(el).autocomplete({
												source : function(request,response,event,ui) {
					       			    	    	console.log ("dd"+i);
					       			    	    	
					       				             $.ajax({
					       				                 type: "GET",
					       				                 contentType: "application/json; charset=utf-8",
										                 url: '${pageContext.request.contextPath}/GetUserJobTitle',
					       				                 
					       				                 data: {
					       										"users" : request.term,
					       										 
					       								 },
					       				                 dataType: "json",
					       				                 success: function (data) {
					       				                     if (data != null) {
					       				                         response(data.UsersList);
					       				                     }
					       				                 },
					       				                 error: function(result) {
					       				                     alert("Error AUTO");
					       				                 }
					       				             });
					       				        }, minLength:0, maxShowItems: 4, scroll:true,
					       						select: function(event, ui) {			
					       							this.value = (ui.item ? ui.item[0]:'');
					       							return false;
					       							
					       						}
					       					}).autocomplete("instance")._renderItem = function(ul, item) {
					       			            return $("<li class='each'>")
					       		                	.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					       				            item[0] + "</span><br><span class='desc'>" +
					       			                item[1] + " "+ item[2] + " "+  item[3] +"</span></div>")
					       			                .appendTo(ul);
					       		        	};
											$(this)
											.focus(
													function() {
														if (this.value == "") {
															$(this)
																	.autocomplete(
																			"search");
														}
													});
								});



											$('input[name ="assignBy"]').each(function(i, el) {
												$(el).autocomplete({
												source : function(request,response,event,ui) {
					       			    	    	console.log ("dd"+i);
					       			    	    	
					       				             $.ajax({
					       				                 type: "GET",
					       				                 contentType: "application/json; charset=utf-8",
										                 url: '${pageContext.request.contextPath}/GetUserJobTitle',
					       				                 
					       				                 data: {
					       										"users" : request.term,
					       										 
					       								 },
					       				                 dataType: "json",
					       				                 success: function (data) {
					       				                     if (data != null) {
					       				                         response(data.UsersList);
					       				                     }
					       				                 },
					       				                 error: function(result) {
					       				                     alert("Error AUTO");
					       				                 }
					       				             });
					       				        }, minLength:0, maxShowItems: 4, scroll:true,
					       						select: function(event, ui) {			
					       							this.value = (ui.item ? ui.item[0]:'');
					       							return false;
					       							
					       						}
					       					}).autocomplete("instance")._renderItem = function(ul, item) {
					       			            return $("<li class='each'>")
					       		                	.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					       				            item[0] + "</span><br><span class='desc'>" +
					       			                item[1] + " "+ item[2] + " "+  item[3] +"</span></div>")
					       			                .appendTo(ul);
					       		        	};
											$(this)
											.focus(
													function() {
														if (this.value == "") {
															$(this)
																	.autocomplete(
																			"search");
														}
													});
								});
					       					// Ending for item autocomplete in Boq table    			
					       				
											
						// SiteName autocomplete in Sitepopup
						$("#popupsiteName")
								.autocomplete(
										{
											source : function(request, response) {
												var siteID = $("#popupsiteID")
														.val();

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {
																"Site" : $(
																		"#popupsiteName")
																		.val(),
																"siteID" : siteID

															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.listSite);
																	console
																			.log('Site data: ;'
																					+ data.ListSiteName);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,
											select : function(event, ui) {
												this.value = (ui.item ? ui.item[0]
														: '');
												document
														.getElementById("popupsiteID").value = ui.item[1];
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0]
											+ "</span><br><span class='desc'>"
											+ item[1] + "</span></div>")
									.appendTo(ul);
						};
						$("#popupsiteName").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						}); // End SiteName autocomplete in Sitepopup

					});
</script>
</html>