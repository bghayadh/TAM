
<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>WO Form View</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script
	src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/Network/NetworkMap.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />

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
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/WorkOrder/WO_BoqPopup.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />

<script
	src=https://cdnjs.cloudflare.com/ajax/libs/markerclustererplus/2.1.4/markerclusterer.min.js></script>

<style>
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


.ppuB {
	display: block;
	border: 1px solid #ced4da;
	border-radius: .25rem;
	width: 100%;
	background-color: #e9ecef;
	text-align: center;
	padding-top: 10px;
}

#popupBarcode {
	
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	z-index: 9999;
	width: 350px;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.dot {
	height: 17px;
	width: 17px;
	background-color: chartreuse;
	border-radius: 50%;
	display: inline-block;
	margin-top: 10px;
}

.dotStatus {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	margin-top: 10px;
	margin-left: 10px;
}

.hide-row {
	display: none;
}

#wrapper {
	width: 100%;
	display: grid;
	max-height: 100px;
	grid-gap: 100px;
	grid-column-gap: 10px !important;
	grid-row-gap: 8px !important;
	display: flex;
	grid-template-rows: 1fr 1fr !important;
	grid-template-columns: 1fr 1fr 1fr !important;
}

#one {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#two {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#three {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#four {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#five {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#six {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

.container {
	min-width: 100%;
}

.divv1 {
	text-align: center;
	font-family: cursive;
	color: #DCF8C6;
	border-radius: 25px 25px 0px 0px !important;
	border-style: groove;
	border-width: 2px;
	border-color: #4B8C8C;
	box-shadow: 5px 5px 5px rgb(75, 140, 140);
}

.divv2 {
	text-align: center;
	justify-content: center;
	background-color: lightgray;
	border-radius: 0px 0px 25px 25px !important;
	font-size: 13px;
	font-family: cursive;
	border-style: groove;
	border-width: 2px;
	border-color: #4B8C8C;
	box-shadow: 5px 5px 5px rgb(75, 140, 140);
	border-top: none;
}

tr {
	height: 40px;
}

table {
	align: top;
	margin-left: auto;
	margin-right: auto;
}

.TD {
	border-bottom: 1pt solid black;
}

.custom-class-assignedto-modal .modal-dialog {
	width: 100%;
}

.custom-class-assignedto-modal .modal-body {
	height: 500px;
	overflow: auto;
}

.modal-header .btnGrp {
	position: absolute;
	top: 8px;
	right: 10px;
}

.display-none {
	display: none;
}

button .fa {
	font-size: 16px;
	margin-left: 10px;
}

button:focus {
	outline: none;
}

#collapseOne {
	width: 800px;
	float: right;
}

#areaTab {
	float: left; /* important */
	/* width:595px; */
	height: auto;
}

#left {
	float: left;
	width: 40%;
	height: 100%;
}

#right {
	padding-right: 0%;
	float: right;
	width: 60%;
	height: 100%;
}

#mapText {
	border: hidden;
	width: 125px;
	height: 25px;
	border: hidden;
	background-color: #87CEEB;
	margin-left: 20px;
	text-align: center;
}

#fwhbutton {
	
}

#twhbutton {
	
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

.nav-link.active {
 background-color: #FFD966 !important;
    color: #00757c !important;
    }
    
    .case
	{
	text-align:center;
	font-size: 16px;
	color:#0000FF;
	position:relative;
	top:7px;
	}
	.btn-pop {
    background-color: #C2CBC0 !important;
    border-color: #C2CBC0;
    3
}

.btn-pop:hover {
    color: #fff;
    background-color: #8696A0 !important;
    border-color: #8696A0 !important;
}
</style>

</head>
<body>
	<script type="text/javascript">

</script>
	<c:set var="pg" value="inventory" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>

	<!--  end of general head page -->
	<p></p>
	<div class="container-fluid">
		<div class="row">

			<div class="col-md-3">
				<!-- class="form-control text-input"  -->
				<div class="form-group  ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green; width: auto;">Work
							Order ID</span> <input type="text" readonly id="wocode"
							value="${workOrdId}" class="form-control text-input" />
					</div>

				</div>
			</div>

			<div class="col-md-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width: auto;">Status</span> <select
						name="cars" id="wostat"
						style="width: 350px; text-align-last: center;"
						class="form-control text-input">

						<option value="draft"
							<c:if test = "${woStatus =='draft'}" > selected </c:if>>Draft</option>
						<option value="approved"
							<c:if test = "${woStatus =='approved'}"> selected </c:if>>Approved</option>
						<option value="completed"
							<c:if test = "${woStatus =='completed'}"> selected </c:if>>Completed</option>
						<option value="cancelled"
							<c:if test = "${woStatus =='cancelled'}"> selected </c:if>>Cancelled</option>

					</select>

				</div>
			</div>

			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Other WO's</span> <input
							type="text" id="selectnav" value="${selectnav}"
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
						<span class="input-group-text" style="width: auto;">Created
							Date</span> <input type="text" id="wocreatedate" readonly
							value="${wocreationDate}"
							class="form-control datetimepicker-input "
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
						<span class="input-group-text" style="width: auto;">Last
							Modify Date</span> <input type="text" id="wolstmodifdate" readonly
							value="${wolastModifiedDate}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker2" />
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
						<li id="btnFrst" title="Go To First" class="page-item "
							style="margin-right: 2px;"><a
							style="margin-left: 3px; width: 53px; height: 40px" id="btnFirst"
							href="#" class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous" class="page-item "
							style="margin-right: 2px;"><a
							style="width: 53px; height: 40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next" class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px; margin-right: 2px; height: 40px"
							id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item "
							style="margin-right: 2px;"><a
							style="width: 53px; height: 40px" id="btnLast" href="#"
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
							onclick='window.location.href = "${pageContext.request.contextPath}/WorkOrderListView"'
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



		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: -20px;">
								
					<li class="tab" class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">INFORMATION</a></li>
			       
						
					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-validation-tab" data-toggle="tab"
						href="#custom-tabs-one-validation" role="tab"
						aria-controls="custom-tabs-one-validation" aria-selected="false"
						style="color: gold;">VALIDATION</a></li>
			            

					<li class="nav-item ml-auto">
						<button type="button" id="sendEmail"
							class="btn btn-primary BtnActive">
							<i class="fa fa-envelope"></i> Send Email
						</button>
						
						<button type="button" id="NewWO"
						onclick='window.location.href = "${pageContext.request.contextPath}/WorkOrderFormView?type=addNew"'
							class="btn btn-primary BtnActive">
							<i class="fa fa-plus"></i> Add
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
				<div id="left">
					<div class="row">
						<div class="col-md-12">
							<div class="form-group" style="align-items: center;">
								<div class="input-group-prepend">
									<span class="input-group-text"
										style="width: auto; display: block; text-align: left">Purpose</span>
									<!--  <input type="text" id="purpose" value="${purpose}" class="form-control text-input" /> -->
									<select name="cars" id="purpose"
										style="width: 500px; text-align-last: center;"
										class="form-control text-input">
										<option value="Installation"
											<c:if test = "${Purpose =='Installation'}"> selected </c:if>>Installation</option>
										<option value="Maintenance"
											<c:if test = "${Purpose =='Maintenance'}"> selected </c:if>>Maintenance</option>
										<option value="Transfer"
											<c:if test = "${Purpose =='Transfer'}"> selected </c:if>>Transfer</option>
										<option value="Retirenment"
											<c:if test = "${Purpose =='Retirenment'}"> selected </c:if>>Retirement</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group" style="align-items: center;">
								<div class="input-group-prepend" id="datetimepicker3"
									data-target-input="nearest">
									<span class="input-group-text" style="width: auto;">Execution
										Date</span> <input type="text" id="woexecutiondate"
										value="${woExecutionDate}"
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
		
					</div>
		
		
					<div class="row">
						<div class="col-md-12">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text" style="min-width: auto;">From
										Warehouse</span> <input type="text" id="wofrmware"
										value="${woFromWare}" style="width: 675px; height: auto;"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
									<input type="text" id="fwLong" value="${frmwarelong}"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input"
										hidden='true' /> <input type="text" id="fwLat"
										value="${frmwarelat}"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input"
										hidden='true' />
		
								</div>
							</div>
						</div>
		
						<div class="col-md-12">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text" style="width: auto;">Source
										Site ID </span> <input type="text" id="siteid" value="${SiteId}"
										style="width: 675px; height: auto;"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text" style="width: auto;">Source
										Warehouse Name</span> <input type="text" id="warename"
										value="${WareName}" style="width: 675px; height: auto;"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
								</div>
							</div>
						</div>
		
		
					</div>
		
					<div class="row">
		
						<div class="col-md-12">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text" style="width: auto;">To
										Warehouse </span> <input type="text" id="wotoware" value="${woToWare}"
										style="width: 675px; height: auto;"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
									<input type="text" id="twLong" value="${towarelong}"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input"
										hidden='true' /> <input type="text" id="twLat"
										value="${towarelat}"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input"
										hidden='true' />
		
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text" style="width: auto;">Destination
										Site ID </span> <input type="text" id="siteiddest"
										value="${SiteIdDest}" style="width: 675px; height: auto;"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
		
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text" style="width: auto;">Destination
										Warehouse Name</span> <input type="text" id="warenamedest"
										value="${WareNameDest}" style="width: 675px; height: auto;"
										class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
								</div>
							</div>
						</div>
		
					</div>
				</div>
				<div id="right">
		
					<div id="collapseOne" class="panel-collapse collapse show"
						aria-labelledby="headingOne">
						<div class="panel-body" style="height: 82.5%; margin-left: 10%">
		
							<div class="card-body" style='border: hidden;'>
								<div class="btn-toolbar"
									style="text-align: left; margin-bottom: 5px; margin-top: auto;">
									<div class="btn-group flex-wrap" data-toggle="buttons"
										style="row-gap: 2px;">
										<div class=" top-btn-filter">
											<button id="fwhbutton" name="fwhbutton" class="buttonTog">
												<i class="fas fa-flag"></i> From WareHouse
											</button>
										</div>
										<div class=" top-btn-filter">
											<button id="twhbutton" name="twhbutton" class="buttonTog"
												style='margin-left: 10px;'>
												<i class="fas fa-flag"></i> To WareHouse
											</button>
										</div>
										<div class=" top-btn-filter">
											<button id="deleteselected" name="deleteselected"
												class="buttonTog" style='margin-left: 10px;'>
												<i class="fa fa-trash"></i> Remove Selection
											</button>
										</div>
		
									</div>
								</div>
								<div id="mapContainer" style='height: 390px;'></div>
							</div>
						</div>
					</div>
		
		
				</div>
		
				<div>
					<form>
		
						<div class="table-responsive-sm">
							<label class="label"> SOURCE BOQ TABLE</label>
							<table id="bisotab"
								class="table table-striped table-bordered table-sm "
								style="display: block; height: 200px; overflow-y: auto;">
		
								<thead>
									<tr class="fixed-headerr">
										<th>
											<div class="container">
												<button type="button" id="selectAllsrc" class="main">
													<span class="sub"></span>Select
												</button>
											</div>
										</th>
										<th>Item</th>
										<th>Item Model</th>
										<th>Item Part Number</th>
										<th>Current Qty</th>
										<th>Qty</th>
										<th>From WareHouse</th>
										<th>To WareHouse</th>
										<th>Node ID</th>
										<th>Node Name</th>
										<th>GR ID</th>
										<th>PR ID</th>
										<th>PO ID</th>
										<th>CIP ID</th>
										<th>Status</th>
										<th>Reconciled</th>
										<th>BarCodeScanned</th>
										<th>ID</th>
									</tr>
								</thead>
								<tbody>
		
		
								</tbody>
							</table>
						</div>
						<input type="text" id="RowIndex" value="" hidden>
						<button type="button" class="add-row" value="Add Row">
							Add Row</button>
							<button type="button" class="delete-row-src">Delete Row</button>
							<button type="button" class="move-row-src">
								Move Item</button>
								<button type="button" class="update-row-src">
									Update</button>
									<button type="button" class="download-row-src">
										Download</button>
										<button type="button" class="upload-row-src">Upload</button>
					</form>
				</div>
		
				<p></p>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text" style="width: 150px;">Total
									Qty</span> <input type="text" id="wostotqty" value="${wosTotalQty}"
									readonly class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>
		
		
				<p></p>
				<div>
					<form>
						<div class="table-responsive-sm">
							<label class="label">DESTINATION BOQ TABLE</label>
							<table id="bisotab2"
								class="table table-striped table-bordered table-sm"
								style="display: block; height: 200px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<th>
											<div class="container">
												<button type="button" id="selectAlldest" class="main">
													<span class="sub"></span>Select
												</button>
											</div>
										</th>
										<th>Item</th>
										<th>Item Model</th>
										<th>Item Part Number</th>
										<th>Current Qty</th>
										<th>Qty</th>
										<th>From WareHouse</th>
										<th>To WareHouse</th>
										<th>Node ID</th>
										<th>Node Name</th>
										<th>GR ID</th>
										<th>PR ID</th>
										<th>PO ID</th>
										<th>CIP ID</th>
										<th>Status</th>
										<th>Reconciled</th>
										<th>BarCodeScanned</th>
										<th>ID</th>
									</tr>
								</thead>
								<tbody>
		
		
								</tbody>
							</table>
						</div>
						<input type="text" id="RowIndexDest" value="" hidden>
						<button type="button" class="add-row-dest" value="Add Row">
							Add Row</button>
							<button type="button" class="delete-row-dest">Delete Row</button>
							<button type="button" class="update-row-dest">
								Update</button>
								<button type="button" class="download-row-dest">
									Download</button>
									<button type="button" class="upload-row-dest">Upload</button>
					</form>
				</div>
		
				<p></p>
				<div class="row">
		
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Qty</span> <input type="text"
									id="wodtotqty" value="${wodTotalQty}" readonly
									class="form-control text-input" />
							</div>
						</div>
					</div>
		
				</div>
			</div>
		
		<!-- here start -->
			<div class="tab-pane fade" id="custom-tabs-one-validation"
				role="tabpanel" aria-labelledby="custom-tabs-one-validation-tab">
				  <div class="table-responsive-sm"> 
						    <table id ="wotable" class="table table-striped table-bordered table-sm " style="display:block; height:400px; overflow-y: auto;">
						        <thead>
						            <tr class="fixed-headerr" >
						                <th>
								         <div style="width:80px" class="fixed-headerr"><button type="button" id="selectAll" class="fixed-headerr" >
								          <span class="sub"></span>Select</button></div></th>
						               	<th>DN ID</th>
						               	<th>DN Item ID</th>
						                <th>Item</th>
						                <th>Item Model</th>
						                <th>Item Part Number</th>
						                <th>Transaction ID</th>
						                <th>Transaction Type</div></th>
						                <th>Element Name</div></th>
						                <th>Approval Status</div></th>
						                <th>FAR ID</th>
						                <th>SN</th>
						                <th>MAC Address</th>
						               
						                
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
			
			</div>
	
	<!-- here end -->
		</div>
	</div>


	<div class="container">
		<div id="poModal" class="modal fade  custom-class-assignedto-modal"
			tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
			aria-hidden="true">
			<div
				class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #FF4F4F;">
						<h5 id="popupNb" class="modal-title"
							style="font-weight: bold; color: #3C1596; position: relative; top: 4px;"></h5>

						<button type="button" id="insertBelow" name="insertBelow"
							onclick="insertRowBelow(this.value)"
							class="btn btn-default btn-primary BtnActive  "
							style="color: white; position: relative; left: 30px; font-size: 0.9rem;">Insert
							Below</button>
						<button type="button" id="insertAbove" name="insertAbove"
							onclick="insertRowAbove(this.value)"
							class="btn btn-default btn-primary BtnActive"
							style="color: white; position: relative; left: 35px; font-size: 0.9rem;">Insert
							Above</button>
						<button type="button" id="deleteBoqRow" name="deleteBoqRow"
							onclick="deleteBoqRow(this.value)"
							class="btn btn-default btn-primary BtnActive"
							style="color: white; position: relative; left: 40px; font-size: 0.9rem;">Delete</button>
						<button name="previousRow"
							class="btn btn-default btn-primary BtnActive"
							style="color: white; position: relative; left: 45px; font-size: 0.9rem;">Previous</button>
						<button id="nextRow" name="nextRow" onclick="nextRow(this.value)"
							class="btn btn-default btn-primary BtnActive"
							style="color: white; position: relative; left: 50px; font-size: 0.9rem;">Next</button>

						<button type="button" name="closePopup" class="close"
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
								id="item-tab" style="color: gold;" data-toggle="tab"
								href="#item" role="tab" aria-controls="item"
								aria-selected="true">ITEM DETAIL</a></li>

							<li class="nav-item"><a class="nav-link " id="serialNum-tab"
								style="color: gold;" data-toggle="tab" href="#serialNum"
								role="tab" aria-controls="serialNum" aria-selected="false">SERIAL
									NUMBER</a></li>

						</ul>


						<div class="tab-content">
							<div class="tab-pane active" id="item" role="tabpanel"
								aria-labelledby="item-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">Item </span> <input
														type="text" id="popupItemCode" value="${popupItemCode}"
														style="height: 37px"
														class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
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
													<span class="input-group-text">Item Model</span> <input
														type="text"
														class="ui-widget ui-widget-content ui-corner-all form-control text-input"
														id="popupItemModel" value="${popupItemModel}"
														style="height: 37px" />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">Item Part No</span> <input
														type="text"
														class="ui-widget ui-widget-content ui-corner-all form-control text-input"
														id="popupItemPartno" value="${popupItemPart}"
														style="height: 37px" />
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
													<span class="input-group-text">From WareHouse</span> <input
														type="text" id="popupFWH" class="form-control text-input"
														value="${popupFWH}" style="" />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">To WareHouse</span> <input
														type="text" id="popupTWH" class="form-control text-input"
														value="${popupTotalTWH}" style="" />
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
													<span class="input-group-text">Node Name</span> <input
														type="text" id="popupNodeName"
														class="form-control text-input" value="${popupNodeName}"
														style="" />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">Node Id</span> <input
														type="text" id="popupNodeId"
														class="form-control text-input" value="${popupNodeId}"
														style="" />
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
													<span class="input-group-text">GR ID</span> <input
														type="text" id="popupGrId" class="form-control text-input"
														value="${popupGrId}" style="" />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">PR ID</span> <input
														type="text" id="popupPrId" class="form-control text-input"
														value="${popupPrId}" style="" />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">PO ID</span> <input
														type="text" id="popupPoId" class="form-control text-input"
														value="${popupPoId}" style="" />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">CIP ID</span> <input
														type="text" id="popupCipQty"
														class="form-control text-input" value="${popupCipQty}"
														style="" />
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
													<span class="input-group-text">Status</span> <input
														type="text" id="popupStatus"
														class="form-control text-input" value="${popupStatus}"
														style="" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">Barcode Scanned</span>
													<div class="ppuB">
														<input type='checkbox' id='popupBarcode'
															value="${popupBarcode}"
															style='height: 20px; width: 20px;'>
													</div>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">Current Qty</span> <input
														type="text" id="popupCurrentQty"
														class="form-control text-input" value="${popupCurrentQty}"
														style="" />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">

													<span class="input-group-text">Qty</span> <input
														type="text" id="popupQty" class="form-control text-input"
														value="${popupQty}" style="height: 39px" />

												</div>
											</div>
										</div>
									</div>
								</div>

							</div>


							<div class="tab-pane" id="serialNum" role="tabpanel"
								aria-labelledby="serialNum-tab">
								<div>
									<p></p>
									<form>
										<div class="table-responsive-sm">
											<table id="serialNoTable"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 320px; overflow-y: auto;">
												<thead>
													<tr class="fixed-headerr">
														<th>
															<button type="button" id="selectAllSerial" class="main">
																<span class="sub"></span>Select
															</button>

														</th>
														<th width="232px">Serial Number</th>
														<th width="232px">Item Model</th>
														<th width="232px">Item Part Number</th>
														<th width="232px">Reconciled</th>

													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
										<input type="text" id="RowIndex2" value="" hidden>
										<button type="button" class="add-row-serial"
											onclick="addRowSerial()">Add Row</button>
										<button type="button" class="delete-row-serial">Delete
											Row</button>
									</form>
								</div>


							</div>
						</div>


					</div>

					<div class="modal-footer"></div>

				</div>

			</div>
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
	
	<!-- popup -->
     
     <div class="container">
		<div id ="WOValModal" class="modal fade  custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" >
				<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
				<h5 id ="popupNbb" class="modal-title" style="font-weight:bold; color: #E9ECEF ;position: relative; bottom: 12px;"></h5>
					<div style="float: right;">
					<button  name ="valpreviousRow" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 0px; font-weight: bold; margin-top: -7px;"">Previous</button>
	            	<button  name="valnextRow" onclick="nextValidRow(this.value)" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 10px; font-weight: bold; margin-top: -7px;"">Next</button> 
					<button type="button" name="closeModPartPopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
										<a class="close modalMinimize ml-3"> <i
											class='fa fa-minus icon-to-change'></i></a>
					</div>
					</div>
		
		<div class="modal-body">
		<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
	 		  <li class="nav-item">
	   			 <a class="nav-link active" id="valitem-tab" style="color: gold;" data-toggle="tab" href="#valitem" role="tab" aria-controls="valitem" aria-selected="true">ITEM</a>
	  		</li>
	  
	  		<li class="nav-item">
	   			 <a class="nav-link " id="valnode-tab" style="color: gold;" data-toggle="tab" href="#valnode" role="tab" aria-controls="valnode" aria-selected="false">NODE</a>
	  		</li>
	  		
	  		<li class="nav-item">
	   			 <a class="nav-link " id="valslot-tab" style="color: gold;" data-toggle="tab" href="#valslot" role="tab" aria-controls="valslot" aria-selected="false">SLOT</a>
	  		</li>
	 
		</ul>
	
	<div class="tab-content">
	
	
	
	
	<div class="tab-pane" id="valslot" role="tabpanel" aria-labelledby="valslot-tab">
	 <div class="container-fluid">
	 <div  style="height:30px"></div>
		
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >From Slot</span>
						<input type="text" id="popupFromSlot" class="form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		
		
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >To Slot</span>
						<input type="text" id="popupToSlot" class="form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		</div>
	</div>
	 </div>
	
	
	
	
	
	
	 <div class="tab-pane" id="valnode" role="tabpanel" aria-labelledby="valnode-tab">
	  <div> 
	<p></p>
		<form>
			<div class="table-responsive-sm"> 
				<table id ="ToNodeTable" class="table table-striped table-bordered table-sm" style="display:block; height:290px; overflow-y: auto;">	
					<thead>
						<tr class="fixed-headerr">
						<th> 
							<button type="button" id="selectAllToNode"  on class="main">
							<span class="sub"></span>Select</button>
									         
						  </th>
						 <th width="232px">To Node Id</th>
						 <th width="232px">To Node Name</th>
	                     <th width="232px">To Node Type</th>
							              
						</tr> </thead> <tbody> </tbody> </table>
						</div>
						  	<input type="text" id="RowIndex2" value="" hidden>
						</form>
						</div> 
						
						
						
						<div> 
	<p></p>
		<form>
			<div class="table-responsive-sm"> 
				<table id ="FromNodeTable" class="table table-striped table-bordered table-sm" style="display:block; height:290px; overflow-y: auto;">	
					<thead>
						<tr class="fixed-headerr">
						<th> 
							<button type="button" id="selectAllFromNode"  class="main">
							<span class="sub"></span>Select</button>
									         
						  </th>
						 <th width="232px">From Node Id</th>
						 <th width="232px">From Node Name</th>
	                     <th width="232px">From Node Type</th>
							              
						</tr> </thead> <tbody> </tbody> </table>
						</div>
						  	<input type="text" id="RowIndex2" value="" hidden>
						</form>
						</div> 
						
						
	 </div>
	
	
	  <div class="tab-pane active" id="valitem" role="tabpanel" aria-labelledby="valitem-tab">
	  <p></p>
	<div class="container-fluid">
		<div class="row">
	  		<div class="col-sm-6">
	  			<div class="form-group">
	  				<div class="input-group-prepend">
	  					<span class="input-group-text" >Item </span>
	   					<input type="text" id="popupvalItemCode" value="" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
	  				</div>
	  			</div>
	  		</div>
	  
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >Item Model</span>
						<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupvalItemModel" value="" style="width:675px; height:37px;"  />					
					</div>
				</div>
			</div></div></div>
			
			<div class="container-fluid">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Item Part No</span>
						<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupvalItemPartno" value="" style="width:674px; height:37px"  />
					</div>
				</div>
			</div>
			
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Element Name</span>
						<input type="text" id="popupElementName" class="form-control text-input"  value=""  style="width:675px;" />
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
						<span class="input-group-text" >DNID</span>
						<input type="text" id="popupDNID" class="form-control text-input" value=""  style="width:674px; height:37px;" />
							
					</div>
				</div>
			</div>
	
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">DNItmID</span>
						<input type="text" id="popupDNItmID" class="form-control text-input" value="" style="width:675px; height:37px;"  />  
							
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
						<span class="input-group-text">Transaction ID</span>
						<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupTransID" value="" style="width:674px; height:37px"  />
					</div>
				</div>
			</div>
			
			<div class="col-sm-6">
						<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Transaction Type</span>
						<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupTransType" value="" style="width:674px; height:37px"  />
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
						 <!--  <input type="text" id="popupApprovalStatus" class="form-control text-input" hidden  value=""  style="width:250px; text-align: center;"  /> -->
						<p class='case' id = "case" style = "margin-left: auto;  font-size: 13px; width:690px;">Transaction Type is Required</p>
					</div>
				</div>
		    </div>	
		    
		    <div class="col-sm-6">
						<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">FAR</span>
						<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupFAR" value="" style="width:674px; height:37px"  />
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 
		<div class="container-fluid">
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >From Slot</span>
						<input type="text" id="popupFromSlot" class="form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		
		
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >To Slot</span>
						<input type="text" id="popupToSlot" class="form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		</div>
	</div>
	 -->
	
			<div class="container-fluid">
			<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class=" input-group-text" >From Site ID</span>
						<input type="text" id="popupFromSiteID" class="ui-widget ui-widget-content ui-corner-all form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		
		
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >To Site ID</span>
						<input type="text" id="popupToSiteID" class="form-control text-input"  value="" style="width:675px;"  />
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
						<span class=" input-group-text" >From Ware Name</span>
						<input type="text" id="popupFromWareName" class="ui-widget ui-widget-content ui-corner-all form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		
		
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >To Ware Name</span>
						<input type="text" id="popupToWareName" class="form-control text-input"  value="" style="width:675px;"  />
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
						<span class=" input-group-text" >From Ware ID</span>
						<input type="text" id="popupFromWareID" class="ui-widget ui-widget-content ui-corner-all form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		
		
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >To Ware IID</span>
						<input type="text" id="popupToWareD" class="form-control text-input"  value="" style="width:675px;"  />
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
						<span class="input-group-text" >Serial Nb</span>
						<input type="text" id="popupSN" class="form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >MAC Address</span>
						<input type="text" id="popupMAC" class="form-control text-input"  value="" style="width:675px;"  />
					</div>
				</div>
		    </div>
		</div>
		</div>	      
						
	  </div>
	
	</div>
	
	  
	  </div>
						
	<div class="modal-footer">
		
	</div>	
									                
		</div>
				
			 </div> </div>
	</div>
		 
		 <!-- popup -->
	

</body>

<script type='text/javascript'>
 
 
/*$("#sendEmail").on("click", function () {
$("#popUpDiv").fadeIn();

});

$("#cancelButton").on("click", function () {
$("#email").val('');
$("#password").val('');
$("#emailTo").val('');
$("#ccmail").val('');
$("#subject").val('');
$("#message").val('');
$("#popUpDiv").fadeOut();
});

$("#send").on("click", function () {
if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
{
alert("All Fields are required");
}
else{
$("#popUpDiv").fadeOut();
}

});*/

 
 let map;
 let service;
 let infowindow;
 let marker;
 var fwhCircle,twhCircle;
 var latitude;
	var longtitude;
	var latitudeFROM;
	var longtitudeFROM;
	var latitudeTO;
	var longtitudeTO;
	var selectedfwh=0;
	var selectedtwh=0;
	var selectWH="";
	var drawTO;
 	var drawFROM;
 	var dirLine;
 function initMap() {
	 latitude;
	 longtitude;
 	 latitudeFROM = parseFloat(document.getElementById("fwLat").value);
 	 longtitudeFROM = parseFloat(document.getElementById("fwLong").value);
 	 latitudeTO = parseFloat(document.getElementById("twLat").value);
 	 longtitudeTO = parseFloat(document.getElementById("twLong").value);

 	
	var LocationsWareHouse=[];

	var location;
	 infowindow = new google.maps.InfoWindow();
    var i; 
    
 	 LocationsWareHouse =${ListWareHouses};
 	
 	var defaultZoom = 6;
 	var myLatlng = { lat: 0.796530, lng: 37.959529 };
 
 	 
      map = new google.maps.Map(document.getElementById('mapContainer'), {
       zoom: defaultZoom,
       center: myLatlng,
       mapTypeId: google.maps.MapTypeId.ROADMAP
     });
      
  	var bounds = new google.maps.LatLngBounds();
	 			 
 		if(latitudeTO===latitudeTO && longtitudeTO===longtitudeTO){
					location={ lat:latitudeTO , lng:longtitudeTO};
	            	twhCircle=markwarehouse("green",location);
	            	selectedtwh++;
	            	drawTO=location;
	            	var latlng = new google.maps.LatLng(latitudeTO, longtitudeTO);
	                bounds.extend(latlng);
						
 	 			}
 			if(latitudeFROM===latitudeFROM && longtitudeFROM===longtitudeFROM){
				location={ lat:latitudeFROM , lng:longtitudeFROM};
            	  fwhCircle=markwarehouse("blue",location);
            	  selectedfwh++;
            	  drawFROM=location;
            	  var latlng = new google.maps.LatLng(latitudeFROM, longtitudeFROM);
	              bounds.extend(latlng);
            	  
	 			}
 			 if(drawFROM!=null && drawTO!=null){
 				map.fitBounds(bounds);
         	  dirLine = drawPol(drawFROM,drawTO);
 	               }
 				
     
 			var markerarray=[];
 		
     for (i = 0; i < LocationsWareHouse.length; i++) { 
          
       markers = new google.maps.Marker({
         position: new google.maps.LatLng(LocationsWareHouse[i].wareLat, LocationsWareHouse[i].wareLong),
         map: map
     });
       markerarray.push(markers);
 
       google.maps.event.addListener(markers, 'click', (function(markers, i) {
         return function() {
             
           infowindow.setContent(LocationsWareHouse[i].wareID+" | SITE ID: "+LocationsWareHouse[i].wareSiteID+" | NAME: "+LocationsWareHouse[i].warehouseName);
           infowindow.open(map, markers);
           location= {lat: LocationsWareHouse[i].wareLat, lng: LocationsWareHouse[i].wareLong};

           if(selectWH=="fwh"){
         	  drawFROM=location;
               if(selectedfwh==0){
            	  fwhCircle=markwarehouse("blue",location);
             	  selectedfwh++;             	  
                  
               }else if(selectedfwh==1){
                   fwhCircle.setMap(null);
            	   fwhCircle=markwarehouse("blue",location);
            	   dirLine.setMap(null);
       			
                   }
               document.getElementById("wofrmware").value=LocationsWareHouse[i].wareID;
               document.getElementById("warename").value=LocationsWareHouse[i].warehouseName;
               document.getElementById("siteid").value=LocationsWareHouse[i].wareSiteID;
                             
               }
           else if(selectWH=="twh"){
         	  drawTO=location;
        	   if(selectedtwh==0){
        		   twhCircle=markwarehouse("green",location);
             	  selectedtwh++;
                  
               }else if(selectedtwh==1){
            	   twhCircle.setMap(null);
                   twhCircle=markwarehouse("green",location);
            	   dirLine.setMap(null);

                   }
        	   document.getElementById("wotoware").value=LocationsWareHouse[i].wareID;
               document.getElementById("warenamedest").value=LocationsWareHouse[i].warehouseName;
               document.getElementById("siteiddest").value=LocationsWareHouse[i].wareSiteID;
               }
           if(drawFROM!=null && drawTO!=null){
        	  dirLine = drawPol(drawFROM,drawTO);
               }
           
         }
       })(markers, i));
       
     } 
     
     let clusterOptions = { styles: [
	        {
	          url: 'resources/clusterIcons/blueCluster.png',
	         height: 65,
	          width:65,
	          anchorText:[-4,-4]
	        },
	      ] };
var markerCluster = new MarkerClusterer(map,markerarray,clusterOptions, {ignoreHiddenMarkers: true, imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
  
 }
 function drawPol(drawFROM,drawTO){
	   const lineSymbol = {
			    path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
			  };
			  // Create the polyline and add the symbol via the 'icons' property.
			var line = new google.maps.Polyline({
			    path: [
			      drawFROM,
			      drawTO,
			    ],
			    icons: [
			      {
			        icon: lineSymbol,
			        offset: "100%",
			      },
			    ],
			    map: map,
			  });
			  return line;
	 }
 function markwarehouse(color,location){
	 var aCircle = new google.maps.Circle({
         strokeColor: color,
         strokeOpacity: 0.8,
         strokeWeight: 2,
         fillColor: color,
         fillOpacity: 0.3,
         map,
         center: location,
         radius:800,
       });
     return aCircle;
     }

 $('#fwhbutton').focus(function(){
	   selectWH="fwh";
	   $(this).css({"background-color":"red"});
	   $('#twhbutton').css({"background-color":""});
	   });
   
 $('#twhbutton').focus(function(){
	   selectWH="twh";
	   $(this).css({"background-color":"#9eeb47"});
	   $('#fwhbutton').css({"background-color":""});
	   });

 $('#deleteselected').focus(function(){
	   selectWH="";
	   $('#twhbutton').css({"background-color":""});
	   $('#fwhbutton').css({"background-color":""});
	   twhCircle.setMap(null);
	   fwhCircle.setMap(null);
	   dirLine.setMap(null);
	   drawFROM=null;
	   drawTO=null;
	   infowindow.close();

	   document.getElementById("wofrmware").value='';
       document.getElementById("warename").value='';
       document.getElementById("siteid").value='';

       document.getElementById("wotoware").value='';
       document.getElementById("warenamedest").value='';
       document.getElementById("siteiddest").value='';
	   });
   




 var navList = [];
 var iCode = "${workOrdId}";
 var indexItm;
 var tab;
 var rowcountWO=0;
 var rowcountWOD=0;

	
 function f1()
 {
 	newArray = [];
 	$("input[name=itmCodeSrc]").each(function(){
 	    newArray.push($(this).val());
 	});
 	return newArray;
        
 }

 function f2()
 {
 	newArray = [];
 	$("input[name=itmCodeDest]").each(function(){
 	    newArray.push($(this).val());
 	});
 	return newArray;
        
 }



 function getAllItemCode2()
 {
 	ItemArray = [];
 	var itemValue = "";
 	$("input[name=itmCode_"+rowcountWO+"]").each(function(){
 		if($(this).val() == "")
 			itemValue = "empty";
 		else
 			itemValue = $(this).val();
 		ItemArray.push(itemValue);
 	});
 	return ItemArray;   
 }



 function getAllItemModels2()
 {
 	ItemModelArray = [];
 	var ItemModValue = "";
 	$("input[name=itmmodel_"+rowcountWO+"]").each(function(){
 		if($(this).val() == "")
 			ItemModValue = "empty";
 		else
 			ItemModValue = $(this).val();
 		ItemModelArray.push(ItemModValue);
 	});
 	return ItemModelArray;   
 }



 function getAllItemPartNbs2()
 {
 	ItemPartNbArray = [];
 	var ItemPartNbValue = "";
 	$("input[name=itmpartnumber_"+rowcountWO+"]").each(function(){
 		if($(this).val() == "")
 			ItemPartNbValue = "empty";
 		else
 			ItemPartNbValue = $(this).val();
 		ItemPartNbArray.push(ItemPartNbValue);
 	});
 	return ItemPartNbArray;   
 }

 function getAllNodeIds()
 {
	NodeIdArray = [];
	var Nodeid="";
	$("input[name=nodeid]").each(function()
		{

		if($(this).val() == "")
			{
			Nodeid = "empty";
		}
 		else
		
			Nodeid=$(this).val();
		NodeIdArray.push(Nodeid);
		
		});

	return NodeIdArray;
 }

 function getAllNodeNames()
 {
	NodeNameArray = [];
	var Nodename="";
	$("input[namenodename]").each(function(){
		if($(this).val() == "")
			{
			Nodename = "empty";
			}
 		else
			Nodename=$(this).val();
		NodeNameArray.push(Nodename);


		});

	return NodeNameArray;
 }



 
			$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
   
			 $("#datetimepicker1").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
			
				
			$("#wofrmware").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
				
			$("#wotoware").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
			$(".add-row").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
				
			$(".delete-row-src").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	

			$(".add-row-dest").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
				
			$(".delete-row-dest").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	
			
			$("#bisotab > tbody").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	
			$("#bisotab2 > tbody").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	

			$("#purpose").change(function() {
				
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});

			$("#wostat").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});


			$(".move-row-src").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
			
		/////validation/////
			 $(function(){
		
				if ('${docStatus}' != 'addNew') {
	
					//let boqArray = [];
					boqArray=${validation};
					//console.log("validation "+boqArray.length);
					for (j = 0; j < boqArray.length; j++){
	
	
		
						 	var ItemCode=boqArray[j][1];
							if(ItemCode==null){
								ItemCode="";
							}
							//console.log("ItemCode "+boqArray[j][22]);
							var ItemName=boqArray[j][2];
							if(ItemName==null){
								ItemName="";
							}
							var ItemModel=boqArray[j][11];
							if(ItemModel==null){
								ItemModel="";
							}
							var ItemPNum=boqArray[j][12];
							if(ItemPNum==null){
								ItemPNum="";
							}
							var transID=boqArray[j][3];
							if(transID==null){
								transID="";
							}
							var transType=boqArray[j][4];
							if(transType==null){
								transType="";
							}
	
							var elementName=boqArray[j][5];
							if(elementName==null){
								elementName="";
							}
							
							var fromSlot=boqArray[j][14];
							if(fromSlot==null){
								fromSlot="";
							}
							var toSlot=boqArray[j][17];
							if(toSlot==null){
								toSlot="";
							}
							var siteID=boqArray[j][7];
							if(siteID==null){
								siteID="0";
							}
							var wareID=boqArray[j][8];
							if(wareID==null){
								wareID="0";
							}
							var wareName=boqArray[j][9];
							if(wareName==null){
								wareName="0";
							}

							var fromsite=wareID+":"+wareName+":"+siteID
							
							var tositeID=boqArray[j][18];
							if(tositeID==null || tositeID=='null'){
								tositeID="0";
							}

							var towareID=boqArray[j][20];
							if(towareID==null|| towareID=='null'){
								towareID="0";
							}
							var towareName=boqArray[j][19];
							if(towareName==null || towareName=='null'){
								towareName="0";
							}
							
							var tosite=towareID+":"+towareName+":"+tositeID
							
							var FarId=boqArray[j][15];
							if(FarId==null){
								FarId="";
							}
							var macAddress=boqArray[j][16];
							if(macAddress==null){
								macAddress="";
							}
							var SN=boqArray[j][10];
							if(SN==null){
								SN="";
							}
							var DnID=boqArray[j][0];
							if(DnID==null){
								DnID="";
							}
							var DniID=boqArray[j][21];
							if(DniID==null){
								DniID="";
							}
								///////////////////
							var ApproveStatus =boqArray[j][6];
							var trans_type = boqArray[j][4];
				            var ApprovalAction = boqArray[j][13];
				            
							if(ApproveStatus == null)
				             {
				       		 	approval = "";
				             }
				            if (ApproveStatus == "Project Manager")
				            {
				            	if(ApprovalAction != 'Approved')
				               	{
				           		 	approval = "To be Approved By Project Manager";
				               	}
				            	else
				               	{
				               	  	approval = "To be Approved By Asset Unit";
				                }
				            }
				        	if (ApproveStatus == "Asset Unit")
				            {
				            	if(ApprovalAction != 'Approved')
				               	{
				           			approval = "To be Approved By Asset Unit";
				               	}
				            	else
				               	{
				           			approval = "To be Approved By Finance";
				               	}

				            }
				            if (ApproveStatus == "Operation Manager")
				            {
				               if(trans_type =="Retirement")                {
				                 if(ApprovalAction != 'Approved')
				                 {
				            	 	approval = "be Approved By Operation Manager";
				                 }
				                 else
				                 {
				            	 	approval = "To be Approved By Finance";
				                  }
				                }

				               else
				               {
				                    if(ApprovalAction != 'Approved')
				                    {
				            	    	approval = "To be Approved By Operation Manager";
				                    }
				            	    else
				            	    {
				            	     	approval = "Completely Approved";
				            	    }
				                   }
				               }
				            if (ApproveStatus == "Finance")
				            {
				            	if(ApprovalAction != 'Approved')
				               	{
				           			approval = "To be Approved By Finance";
				           		}
				            	else
				           	    {
				           		 	approval = "Completely Approved";
				           	    }
				            }
				            if (ApproveStatus == "Completely Approved")
				            {
				        	    approval = "Completely Approved";
				        	}

				    		var toNodeArray = [];
				    		  if (boqArray[j][22] != null) {
				    		  toNodeArray.push(boqArray[j][22]);
				    		  }
				    		  else{
				    		  toNodeArray.push(null);
				    		  }
				    				var fromNodeArray = [];
				    		  if (boqArray[j][23] != null) {
				    		  fromNodeArray.push(boqArray[j][23]);
				    		  }
				    		  else{
				    		  fromNodeArray.push(null);
				    		  }

					 
					var itemRow = "<tr>"
						+"<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPop3(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			         + "<td name='DnID'><input type='text' name='dnId' class='form-control text-input' value='"+DnID+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
			         + "<td name='DniID'><input type='text' name='dniId' class='form-control text-input' value='"+DniID+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
			         + "<td name='item'><input type='text' name='itmCode' class='form-control text-input' value='"+ItemCode+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='itemModel'><input type='text' name='itmMod' class='form-control text-input' value='"+ItemModel+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='itemPartNb'><input type='text' name='itmPartNb' class='form-control text-input' value='"+ItemPNum+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='transID'><input type='text' name='itmTransID' class='form-control text-input' value='"+transID+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='transType'><input type='text' name='itmTransType' class='form-control text-input' value='"+transType+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='elementName'><input type='text' name='itmelementName' class='form-control text-input' value='"+elementName+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='approvalStatus'><input type='text' name='approvalStatus' class='form-control text-input' value='"+approval+"' readonly style='width:350px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='FarId'><input type='text' name='FarId' class='form-control text-input' value='"+FarId+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
			         + "<td name='toSN'><input type='text' name='toSerialNb' class='form-control text-input' value='"+SN+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
			         + "<td name='macAddress'><input type='text' name='macAddress' class='form-control text-input' value='"+macAddress+"' readonly style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
			         + "<td name='toNode' style='display: none;'><input type='text' style='width:200px;' value='" + toNodeArray[0] + "' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
		             + "<td name='fromNode' style='display: none;'><input type='text' style='width:200px;' value='" + fromNodeArray[0] + "' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
		             + "<td name='fromSlot' style='display: none;'><input type='text' name='fromSlot' class='form-control text-input' value='"+fromSlot+"'  style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"			
			         + "<td name='toSlot' style='display: none;'><input type='text' name='toSlot' class='form-control text-input' value='"+toSlot+"'  style='width:200px;'  class='ui-widget ui-widget-content ui-corner-all'/></td>"	
			         + "<td name='siteID' style='display: none;'><input type='text' name='siteID' class='form-control text-input' value='"+fromsite+"'  style='width:200px;'  class='ui-widget ui-widget-content ui-corner-all'/></td>"
			         + "<td name='tositeID' style='display: none;'><input type='text' name='tositeID' class='form-control text-input' value='"+tosite+"'  style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
					         
			         +"</tr>";					
			         		
	
			        $("#wotable > tbody").append(itemRow);
		        
					}
				}
					
			});
				
	$(document).ready(function() {

		if('${SelectedIndex}' != 'addNew'){
			
			var SelectedIndex = ${SelectedIndex};
			if('${workOrderCount}' != 'addNew'){
				
				var workOrderCount = ${workOrderCount};
				if(($("#wocode").val()) != "" && ($("#wocode").val()) != null){
					if(SelectedIndex === workOrderCount){
						document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";
						$("#btnNexta").hasClass("disabled");
					}else{
						console.log("else condition 1");
						if(!$("#btnNexta").hasClass("disabled")){
							
							$("#btnNext").click(function(){
								console.log("clicked");
								var param ="${pageContext.request.contextPath}/WorkOrderFormView?workOrdId="+$("#wocode").val()+"&NavAction=1";
								window.location.href =param;
							});
						}
						if(!$("#btnLst").hasClass("disabled")){
		        			$("#btnLst").click(function(){
		        				var param ="${pageContext.request.contextPath}/WorkOrderFormView?workOrdId="+$("#wocode").val()+"&NavAction=4";
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
						if(!$("#btnPrva").hasClass("disabled")){
							$("#btnPrv").click(function(){
								var param ="${pageContext.request.contextPath}/WorkOrderFormView?workOrdId="+$("#wocode").val()+"&NavAction=0";
								 window.location.href =param;
							 });
						}
						$("#btnFrst").click(function(){
		        			if(!$("#btnFrst").hasClass("disabled")){
								var param ="${pageContext.request.contextPath}/WorkOrderFormView?workOrdId="+$("#wocode").val()+"&NavAction=3";
		        				window.location.href =param;		
		        			}
		        		});

					}
					
				}else{
						console.log("else condition of the if work order is not null");
					}
			}
			$("#label-1").text((SelectedIndex)+"/"+workOrderCount);
		}//end of if('${SelectedIndex}' != "addNew")
		else{
			$("#formStatus").text("New");
			$('.dot').css({"background-color" : "orange"});
			$(".nextprvItems").addClass("hide-row ");
	 		 $(".pad").removeClass("hide-row ");
			}

		

     var alertFlagSource;
     var alertFlagDest;

		function SetCalc1(obj)
		{
			var row_index = $(obj).parent().parent().index();
			var column_index = $(obj).parent().index();
			var any_index_2 = $(obj).index();
			var cell = $(obj).val();
			var qty = parseFloat($(obj).parent().parent().children().eq(4).children().val());
			
            if (column_index == 4) 
            {

   		     if (cell == '') {
   			     cell = 0;
   			       }
            	
                // validate Qty
				if ($.isNumeric(cell) == false) 
				{
					alert('Current Qty is  not Numeric');
					obj.focus();
					return false;
				}
			}

            if (column_index == 5) 
            {

   		     if (cell == '') {
				 cell = 0;
   			       }
            	
                // validate Qty
				if ($.isNumeric(cell) == false) 
				{
					alert('Qty is  not Numeric');
					obj.focus();
					return false;
				}
			}           
		}



		function SetCalc2(obj)
		{
			var row_index = $(obj).parent().parent().index();
			var column_index = $(obj).parent().index();
			var any_index_2 = $(obj).index();
			var cell = $(obj).val();
			var qty = parseFloat($(obj).parent().parent().children().eq(5).children().val());
			
            if (column_index == 5) 
            {
   		     if (cell == '') {	     
   			     cell = 0;
   			       }
            	
                // validate Qty
				if ($.isNumeric(cell) == false) 
				{
					alert(' Qty is  not Numeric');
					obj.focus();
					return false;
				}
			}


            if (column_index == 4) 
            {

   		     if (cell == '') {
   			     cell = 0;
   			       }
            	
                // validate Qty
				if ($.isNumeric(cell) == false) 
				{
					alert(' Current Qty is  not Numeric');
					obj.focus();
					return false;
				}
			}
		}
		

//Save rows in table

 $("#saveButton").click(  function() {
			 alertFlagSource ='0';
			 alertFlagDest='0';

			// validate Ordered date cannot be null
			 val =$("#woexecutiondate").val();
		     val1 = Date.parse(val);
		     if (isNaN(val1) == true) {
		          alert(' Execution date is not valid');
		          return false;
		        }
		       
		     val2 =$("#wofrmware").val();
		     var pr1=document.getElementById('purpose').value;
		     
		     if( pr1 !="Installation"){
		    	 if(val2 == ""){
			    	 alert('From Warehouse/Site Cannot be empty');
			    	 return false;
				     }
			     }		     

		     /*val3 =$("#wotoware").val();
		     if(val3 == ""){
		    	 alert('To Warehouse/Site Cannot be empty');
		    	 return false;

			     }*/

		     if ($("#wostotqty").val() == '') {
			      wostotqty.value = 0;
			       }

		     if (($. isNumeric( $("#wostotqty").val()))== false) {
				 alert('Total Amount is  not Numeric');
				 return false;}

		     if ($("#wodtotqty").val() == '') {
		    	 	  wodtotqty.value = 0;
		    	 			   }
			   
   			if (($. isNumeric( $("#wodtotqty").val()))== false) {
		    	alert('Total Amount is  not Numeric');
		    	 return false;
		    	 }
		    	 			 
				 getallrows();
				 
				 if (alertFlagSource =='1' || alertFlagDest== '1') {

				     return false; 
				     }

// Save New Values					 
				 saverowsintables();
 })


$("#deleteButton").click(  function() {
			 selectALLrowssrc();
			 var deleteArray = [];
			 var woId = document.getElementById("wocode").value;
			 deleteArray.push(woId);
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/WoFormDelete",
					dataType : "json",
					data : {
						"workOrdId" : deleteArray
					},
					success : function(data) {
						location.replace("${pageContext.request.contextPath}/WorkOrderListView");
					},
					error : function(error) {
					}
				});
				

// to delete all the src items related to this workorder
				 
				 

				 
				
})

	$("#selectnav").autocomplete({
		showHeader: true,
		source: function(request, response) {
        	
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/GetAllWorkOrders',
                data: {
	                   
						"workOrder" : request.term,
						
				 },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.allWorkOrders);
                        console.log(data.allWorkOrders);
                    }
                },
                error: function(result) {
                    alert("Error");
                }
            });
        },minLength:0, maxShowItems: 40, scroll:true,
        select: function(event, ui) {
			this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
			var param = "${pageContext.request.contextPath}/WorkOrderFormView?workOrdId="+ (ui.item[0])+ "&NavAction=2";
			window.location.href =param;
				return false;
			}
	}).autocomplete("instance")._renderItem = function(ul, item) {
        return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'><span class='name' style='font-weight:bold; color:red;'>Source: </span>" +
            item[1] +', '+ item[2] + "</span><br><span class='desc'><span class='name' style='font-weight:bold; color:green;'>Destination: </span>" +
            item[3] +', '+ item[4] + "</span></div>")
        .appendTo(ul);
	};
	$("#selectnav").focus(function(){
		
       	if (this.value == ""){
           	$(this).autocomplete("search");
       	}						
	});
 
		//Auto Complete FROM_WAREHOUSE
		
		$("#wofrmware").autocomplete({
			 source: function(request, response) {
				 //response(LocationsWareHouse);      	
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
			                 data: {
				                   
									"warehouseName" : request.term,
									
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.globalList);
			                         console.log(data.globalList);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 40, scroll:true,		
		               
				select: function(event, ui) {
					wofrmware.value = (ui.item ? ui.item[0]  : '');
					warename.value=ui.item[1];
					siteid.value=ui.item[2];
					fwLong.value=parseFloat(ui.item[3]);
					fwLat.value=parseFloat(ui.item[4]);
					
					var location={lat: parseFloat(ui.item[4]) , lng: parseFloat(ui.item[3])};
					if(selectedfwh==0){
		            	  fwhCircle=markwarehouse("blue",location);
		            	  drawFROM=location;
		             	  selectedfwh++;
		                  
		               }else if(selectedfwh==1){
			            	  drawFROM=location;
				               
		                   fwhCircle.setMap(null);
		            	   fwhCircle=markwarehouse("blue",location);
		            	   if (drawTO!=null){
			            	   dirLine.setMap(null);
			                   }
		                   }
	               
	                   if (drawTO!=null){
		 		         	  dirLine = drawPol(drawFROM,drawTO);
			                   }
	                   
						return false;
							}
				    }).autocomplete("instance")._renderItem = function(ul, item) {
			            return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                    item[0] + "</span><br><span class='desc'>" +
		                    item[1] +', '+ item[2] + "</span></div>")
		                .appendTo(ul);
		        	};
					$("#wofrmware").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});
					$("#wofrmware").change(function(){
						if(this.value == ""){
							$("#siteid").val("");
							$("#warename").val("");
						}
						});

					// AutoComplete for from site id
					$("#siteid").autocomplete({
						showHeader: true,
				        source: function(request, response) {

		                        var warehouse=$("#wofrmware").val();
		                        var warehouseName=$("#warename").val();
					        
					             $.ajax({
					                 type: "GET",
					                 contentType: "application/json; charset=utf-8",
					                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
									  data: {
										  "warehouseName" :  request.term,					 

									 },
					                 dataType: "json",
					                 success: function (data) {
					                     if (data != null) {
					                         response(data.globalList);
					                     }
					                 },
					                 error: function(result) {
					                     alert("Error");
					                 }
					             });
					         }, minLength:0, maxShowItems: 40, scroll:true,		
				               
						select: function(event, ui) {
							siteid.value = (ui.item ? ui.item[2]   : '');							
							$("#wofrmware").val(ui.item[0]);
							$("#warename").val(ui.item[1]);

							fwLong.value=parseFloat(ui.item[3]);
							fwLat.value=parseFloat(ui.item[4]);
							
							var location={lat: parseFloat(ui.item[4]) , lng: parseFloat(ui.item[3])};
							if(selectedfwh==0){
				            	  fwhCircle=markwarehouse("blue",location);
				            	  drawFROM=location;
				             	  selectedfwh++;
				                  
				               }else if(selectedfwh==1){
					            	  drawFROM=location;
						               
				                   fwhCircle.setMap(null);
				            	   fwhCircle=markwarehouse("blue",location);
				            	   if (drawTO!=null){
					            	   dirLine.setMap(null);
					                   }
				                   }
			               
			                   if (drawTO!=null){
				 		         	  dirLine = drawPol(drawFROM,drawTO);
					                   }

								return false;
									}
						    }).autocomplete("instance")._renderItem = function(ul, item) {
								return $("<li class='each'>")
				                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
				                    item[2] + "</span><br><span class='desc'>" +
				                    item[1] +', '+ item[0] + "</span></div>")
				                .appendTo(ul);
				        	};
							$("#siteid").focus(function(){
				   	        	if (this.value == ""){
				   	            	$(this).autocomplete("search");
				   	        	}						
							});
							$("#siteid").change(function(){
								if(this.value == ""){
									$("#wofrmware").val("");
									$("#warename").val("");
								}
								});



						// AutoComplete for WareHouse Name
							$("#warename").autocomplete({
								showHeader: true,						        
						        source: function(request, response) {
				                        var warehouse=$("#wofrmware").val();
				                        var siteid=$("#siteid").val();							        
							             $.ajax({
							                 type: "GET",
							                 contentType: "application/json; charset=utf-8",
							                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
							                 data: {
													"warehouseName" :  request.term,				 

											 },
							                 dataType: "json",
							                 success: function (data) {
							                     if (data != null) {
							                         response(data.globalList);
							                     }
							                 },
							                 error: function(result) {
							                     alert("Error");
							                 }
							             });
							         }, minLength:0, maxShowItems: 40, scroll:true,		
						               
								select: function(event, ui) {
									
									warename.value = (ui.item ? ui.item[1]   : '');
									$("#wofrmware").val(ui.item[0]);
									$("#siteid").val(ui.item[2]);

									fwLong.value=parseFloat(ui.item[3]);
									fwLat.value=parseFloat(ui.item[4]);
									
									var location={lat: parseFloat(ui.item[4]) , lng: parseFloat(ui.item[3])};
									if(selectedfwh==0){
						            	  fwhCircle=markwarehouse("blue",location);
						            	  drawFROM=location;
						             	  selectedfwh++;
						                  
						               }else if(selectedfwh==1){
							            drawFROM=location;
								               
						                   fwhCircle.setMap(null);
						            	   fwhCircle=markwarehouse("blue",location);
						            	   if (drawTO!=null){
						            	   dirLine.setMap(null);
						                   }
						               }
					               
					                   if (drawTO!=null){
						 		         	  dirLine = drawPol(drawFROM,drawTO);
							                   }
										return false;
											}
								    }).autocomplete("instance")._renderItem = function(ul, item) {
										return $("<li class='each'>")
						                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						                    item[1] + "</span><br><span class='desc'>" +
						                    item[0] +', '+ item[2] + "</span></div>")
						                .appendTo(ul);
						        	};
									$("#warename").focus(function(){
						   	        	if (this.value == ""){
						   	            	$(this).autocomplete("search");
						   	        	}						
									});
									$("#warename").change(function(){
										if(this.value == ""){
											$("#wofrmware").val("");
											$("#siteid").val("");
										}
										});





// AutoComplete 
									//var columns = [{name: 'WareH Name', minWidth: '400px'}];									
									$("#wotoware").autocomplete({
										
										 source: function(request, response) {
									        	var siteId=$("#siteiddest").val();
									        	var wareName=$("#warenamedest").val();
									        	
										             $.ajax({
										                 type: "GET",
										                 contentType: "application/json; charset=utf-8",
										                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
										                 data: {
											                    
																"warehouseName" : request.term,
																
														 },
										                 dataType: "json",
										                 success: function (data) {
										                     if (data != null) {
										                         response(data.globalList);										                     
										                     }
										                 },
										                 error: function(result) {
										                     alert("Error");
										                 }
										             });
										         }, minLength:0, maxShowItems: 40, scroll:true,		
									               
									        
											select: function(event, ui) {
												wotoware.value = (ui.item ? ui.item[0]  : '');
												warenamedest.value=ui.item[1];
												siteiddest.value=ui.item[2];
												twLong.vaue=ui.item[3];
												twLat.value=ui.item[4];

												var location={lat: parseFloat(ui.item[4]) , lng: parseFloat(ui.item[3])};
												if(selectedtwh==0){
									            	  twhCircle=markwarehouse("green",location);
									            	  drawTO=location;
									             	  selectedtwh++;
									                  
									               }else if(selectedtwh==1){
									            	   drawTO=location;
									                   twhCircle.setMap(null);
									            	   twhCircle=markwarehouse("green",location);
									            	   dirLine.setMap(null);
								                   }
											    if (drawFROM!=null){
									                   
								 		         	  dirLine = drawPol(drawFROM,drawTO);
									                   }
													return false;
														}													
											    }).autocomplete("instance")._renderItem = function(ul, item) {
										            return $("<li class='each'>")
									                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                    item[0] + "</span><br><span class='desc'>" +
									                    item[1] +', '+ item[2] + "</span></div>")
									                .appendTo(ul);
									        	};
												$("#wotoware").focus(function(){
									   	        	if (this.value == ""){
									   	            	$(this).autocomplete("search");
									   	        	}						
												});
												$("#wotoware").change(function(){
													if(this.value == ""){
														$("#siteiddest").val("");
											        	$("#warenamedest").val("");
													}
													});




	// AutoComplete for Destination warehouse name

												$("#warenamedest").autocomplete({
													showHeader: true,
											        source: function(request, response) {
									                        var warehouse=$("#wotoware").val();
									                        var siteid=$("#siteiddest").val();
												             $.ajax({
												                 type: "GET",
												                 contentType: "application/json; charset=utf-8",
												                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
												                 data: {
																		"warehouseName" :  request.term,				 
																 },
												                 dataType: "json",
												                 success: function (data) {
												                     if (data != null) {
												                         response(data.globalList);
												                     }
												                 },
												                 error: function(result) {
												                     alert("Error");
												                 }
												             });
												         }, minLength:0, maxShowItems: 40, scroll:true,		
											               											        
													select: function(event, ui) {
														warenamedest.value = (ui.item ? ui.item[1]   : '');
														$("#siteiddest").val(ui.item[2]);
														$("#wotoware").val(ui.item[0]);
														twLong.vaue=ui.item[3];
														twLat.value=ui.item[4];

														var location={lat: parseFloat(ui.item[4]) , lng: parseFloat(ui.item[3])};
														if(selectedtwh==0){
											            	  twhCircle=markwarehouse("green",location);
											            	  drawTO=location;
											             	  selectedtwh++;
											                  
											               }else if(selectedtwh==1){
											            	   drawTO=location;
											                   twhCircle.setMap(null);
											            	   twhCircle=markwarehouse("green",location);
											            	   if (drawFROM!=null){
											            	   dirLine.setMap(null);
											            	   }
										                   }
													    if (drawFROM!=null){
											                   
										 		         	  dirLine = drawPol(drawFROM,drawTO);
											                   }
															return false;
																}
													    }).autocomplete("instance")._renderItem = function(ul, item) {
															return $("<li class='each'>")
											                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
											                    item[1] + "</span><br><span class='desc'>" +
											                    item[0] +', '+ item[2] + "</span></div>")
											                .appendTo(ul);
											        	};
														$("#warenamedest").focus(function(){
											   	        	if (this.value == ""){
											   	            	$(this).autocomplete("search");
											   	        	}						
														});
														$("#warenamedest").change(function(){
															if(this.value == ""){
																$("#siteiddest").val("");
													        	$("#wotoware").val("");
															}
															});
																
																



														$("#siteiddest").autocomplete({
															showHeader: true,
													        source: function(request, response) {
											                        var warehouse=$("#wotoware").val();
											                        var warehouseName=$("#warenamedest").val();
														        
														             $.ajax({
														                 type: "GET",
														                 contentType: "application/json; charset=utf-8",
														                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
														                 data: {
																				  "warehouseName" :request.term,						 
																		 },
														                 dataType: "json",
														                 success: function (data) {
														                     if (data != null) {
														                         response(data.globalList);
														                     }
														                 },
														                 error: function(result) {
														                     alert("Error");
														                 }
														             });
														         }, minLength:0, maxShowItems: 40, scroll:true,		
													               													        
															select: function(event, ui) {
																siteiddest.value = (ui.item ? ui.item[2]   : '');
																$("#wotoware").val(ui.item[0]);
																$("#warenamedest").val(ui.item[1]);
																twLong.vaue=ui.item[3];
																twLat.value=ui.item[4];

																var location={lat: parseFloat(ui.item[4]) , lng: parseFloat(ui.item[3])};
																if(selectedtwh==0){
													            	  twhCircle=markwarehouse("green",location);
													            	  drawTO=location;
													             	  selectedtwh++;
													                  
													               }else if(selectedtwh==1){
													            	   drawTO=location;
													                   twhCircle.setMap(null);
													            	   twhCircle=markwarehouse("green",location);
													            	   if (drawFROM!=null){
													            	   dirLine.setMap(null);
													            	   }
												                   }
															    if (drawFROM!=null){
													                   
												 		         	  dirLine = drawPol(drawFROM,drawTO);
													                   }
																	return false;
																		}
															    }).autocomplete("instance")._renderItem = function(ul, item) {
															return $("<li class='each'>")
													                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
													                    item[2] + "</span><br><span class='desc'>" +
													                    item[1] +', '+ item[0] + "</span></div>")
													                .appendTo(ul);
													        	};
																$("#siteiddest").focus(function(){
													   	        	if (this.value == ""){
													   	            	$(this).autocomplete("search");
													   	        	}						
																});
																$("#siteiddest").change(function(){
																	if(this.value == ""){
																		$("#warenamedest").val("");
															        	$("#wotoware").val("");
																	}
																	});									


	
	if ('${ListWorkOrderDestItem}' != 'addNew' || '${ListWorkOrderItem}' != 'addNew'){
		
		var sumTotal;
		var sumQty=0;
		var slctsave2= [];
		var slctmove= [];
    	var slctsave = [];
		var slctDelDesItem = [];
		var slctDelSrcItem = [];
		var slctDelSerialItem =[];
        var newslct = [];
        var ressplit;
        // get all colmns count per row
   		function count(array){
                var c = 0;
                for(i in array) // in returns key, not object
                    if(array[i] != undefined)
                        c++;
                return c;
         }
   		var boqArray = [];
   		var boqArray2=[];
		if('${ListWorkOrderItem}' != 'addNew'){
			
	   		
   			boqArray = ${ListWorkOrderItem};
			}
		if('${ListWorkOrderDestItem}' != 'addNew'){
			
	   		
   			boqArray2 = ${ListWorkOrderDestItem};
			}

   			// Load Data Fct
   			loadData(boqArray,boqArray2);
   	    	
   	   		
		
   		}// END OF IF 


	$(".add-row").click(function(){

		// asign table id
		tablesgn="bisotab";
		
		// add new row to source boq table
		var markupp;
		markupp=createNewRow();
		
		// append the table
		$("#bisotab > tbody").append(markupp);

		// get table rowcount after adding row
	    rowcountWO =  $("#bisotab >tbody tr").length;

		// AutoComplete
		autoComplete(rowcountWO,"bisotab");

		// Event to change quantity for source items
		$('input[name="qty"]').on('input',function (){
			sumQtyWos=0;
			 $("#bisotab > tbody > tr").each(function(i, row){
		 		sumQtyWos = sumQtyWos + parseFloat($(this).children('td[name="qty"]').children('input').val());
			
		   }); 
			 $('#wostotqty').val(sumQtyWos);	
		});

	 });

	 // end add row

								

$(".move-row-src").click(function(){
	 slctmove=[];
	 getselectedrowsmove ();
	 
	 if (slctmove.length == 0) {
     	alert('Select Row(s) to Move');
   		return false;
   }
	   
	 // Move data fct from src to des
	 moveData(slctmove);

	 // event to change quantity when move from src to des
		$('input[name="qty"]').on('input',function (){
			sumQtyWod=0;
			 $("#bisotab2 > tbody > tr").each(function(i, row){
		 		sumQtyWod = sumQtyWod + parseFloat($(this).children('td[name="qty"]').children('input').val());
		   }); 
			 $('#wodtotqty').val(sumQtyWod);	
		});


						
	        
});


// end move item
	 


	$(".add-row-dest").click(function(){

		// asign table id
		tablesgn="bisotab2";
		
		// add new row to destination boq table
		var markupp;
		markupp=createNewRow();
		
		// append the table
		$("#bisotab2 > tbody").append(markupp);
		
			// get row count after adding row	      
			rowcountWOD =  $("#bisotab2 >tbody tr").length;

	  		// AutoComplete 
			autoComplete(rowcountWOD,"bisotab2");

			// Event change quantity for des items
			$('input[name="qty"]').on('input',function (){
				sumQtyWod=0;
				 $("#bisotab2 > tbody > tr").each(function(i, row){
			 		sumQtyWod = sumQtyWod + parseFloat($(this).children('td[name="qty"]').children('input').val());
			   }); 
				 $('#wodtotqty').val(sumQtyWod);	
			});
 
 });

	

 $(".delete-row-src").click(function(){
	 slctDelSrcItem=[] ;  
	 $("#bisotab > tbody").find('input[name="record"]').each(function(){
         if($(this).is(":checked")){
        	slctDelSrcItem.push($(this).parent().parent().children('td[name="wosId"]').children('input').val());
             
         }
	 });
	 
//   var supprimerrow ='';
   	for (i = 0; i <= slctDelSrcItem.length; ++i) {
	            //delete from screen
	            // check if you select rows to save or update
	           if (slctDelSrcItem.length == 0) {
	            	alert(' Select Row(s) to Delete');
	          		return false;
	          }
   	}
	 
     $("#bisotab > tbody").find('input[name="record"]').each(function(){
         if($(this).is(":checked")){
             $(this).parents("tr").remove();
         }

         
         
     });
     getTotalAT_SumQty();
            
});




 $(".delete-row-dest").click(function(){
	 slctDelDesItem=[] ;  
	 $("#bisotab2 > tbody").find('input[name="record"]').each(function(){
         if($(this).is(":checked")){
        	slctDelDesItem.push($(this).parent().parent().children('td[name="wodId"]').children('input').val());
             
         }
	 });
	 
   	for (i = 0; i <= slctDelDesItem.length; ++i) {
	            //delete from screen
	            // check if you select rows to save or update
	           if (slctDelDesItem.length == 0) {
	            	alert(' Select Row(s) to Delete');
	          		return false;
	          }
   	}
	 
     $("#bisotab2 > tbody").find('input[name="record"]').each(function(){
         if($(this).is(":checked")){
             $(this).parents("tr").remove();
         }

         
         
     });
     getTotalAT_SumQty();
            
});
  //function to get selected rows for save 
	function getallrows () {
		
		slctsave = [];
        slctsave2=[];
        var rec;
		var barcs;
		var recdest;
		var baracd;
        obj='';
		  
	  $("#bisotab > tbody").find('input[name="record"]').each(function(){

            var ItemCode =($(this).parent().parent().children('td[name="itemCode"]').children('input').val());
				    if (ItemCode == "") {   
					    alert('ItemCode in the Source Boq table  cannot be null in Table at Row: ' +($(this).parent().parent().index()+1));
					     alertFlagSource ='1';
					   return false;				  
				    }
				    if ($(this).parent().parent().children('td[name="reconciled"]').children('input').is(':checked')){	  
				            rec='1'
							  }
						  else{
				             rec='0';
							  }

				    if ($(this).parent().parent().children('td[name="barcodescanned"]').children('input').is(':checked')){
				            barcs='1'
							  }
						  else{
				             barcs='0';
							  }
				    
			    	 slctsave.push({"ItemCode" : $(this).parent().parent().children('td[name="itemCode"]').children('input').val(),
				    	 "ItemModel" : $(this).parent().parent().children('td[name="itemModel"]').children('input').val(),
				    	 "ItemPartNumber" : $(this).parent().parent().children('td[name="itemPartNumber"]').children('input').val(),
				    	 "CurrentQty" : $(this).parent().parent().children('td[name="currentQty"]').children('input').val(),
				    	 "Qty" : $(this).parent().parent().children('td[name="qty"]').children('input').val(),
				    	 "FromWarehouse" : $(this).parent().parent().children('td[name="itemFromWare"]').children('input').val(),
				    	 "ToWarehouse" : $(this).parent().parent().children('td[name="itemToWare"]').children('input').val(),
				    	 "NodeId" : $(this).parent().parent().children('td[name="nodeId"]').children('input').val(),
				    	 "NodeName" : $(this).parent().parent().children('td[name="nodeName"]').children('input').val(),
				    	 "GrId" : $(this).parent().parent().children('td[name="grId"]').children('input').val(),
				    	 "PrId" : $(this).parent().parent().children('td[name="prId"]').children('input').val(),
				    	 "PoId" : $(this).parent().parent().children('td[name="poId"]').children('input').val(),
				    	 "CipId" : $(this).parent().parent().children('td[name="cipId"]').children('input').val(),
				    	 "Status" : $(this).parent().parent().children('td[name="status"]').children('input').val(),
				    	 "Reconciled" : rec, "BarCodeScanned" : barcs,
				    	 "WosId" : $(this).parent().parent().children('td[name="wosId"]').children('input').val(),
			    	 	"serialNum" :$(this).parent().parent().children('td[name="serialNo"]').children('input').val()});
			   
		    });     

		    $("#bisotab2 > tbody").find('input[name="record"]').each(function(){


			    var ItemCode =($(this).parent().parent().children('td[name="itemCode"]').children('input').val());
			    if (ItemCode == "") {   
				    alert('ItemCode in the Destination Boq table cannot be null in Table at Row: ' +($(this).parent().parent().index()+1));
				    alertFlagDest ='1';
				   return false;
				  
			    }
			    if ($(this).parent().parent().children('td[name="reconciled"]').children('input').is(':checked')){	  
			            recdest='1'
						  }
					  else{
			             recdest='0';
						  }
			    if ($(this).parent().parent().children('td[name="barcodescanned"]').children('input').is(':checked')){
			            barcd='1'
						  }
					  else{
			             barcd='0';
						  }
			   
	      			    				    
		    slctsave2.push({"ItemCode" : $(this).parent().parent().children('td[name="itemCode"]').children('input').val() , 
			    "ItemModel" : $(this).parent().parent().children('td[name="itemModel"]').children('input').val(),
			    "ItemPartNumber" : $(this).parent().parent().children('td[name="itemPartNumber"]').children('input').val(),  
			    "CurrentQty" : $(this).parent().parent().children('td[name="currentQty"]').children('input').val(), 
			    "Qty" : $(this).parent().parent().children('td[name="qty"]').children('input').val(), 
			    "FromWarehouse" : $(this).parent().parent().children('td[name="itemFromWare"]').children('input').val(),
		    	"ToWarehouse" : $(this).parent().parent().children('td[name="itemToWare"]').children('input').val(),
			    "NodeId" : $(this).parent().parent().children('td[name="nodeId"]').children('input').val(), 
			    "NodeName" : $(this).parent().parent().children('td[name="nodeName"]').children('input').val(), 
			    "GrId" : $(this).parent().parent().children('td[name="grId"]').children('input').val(), 
			    "PrId" : $(this).parent().parent().children('td[name="prId"]').children('input').val(), 
			    "PoId" : $(this).parent().parent().children('td[name="poId"]').children('input').val(), 
			    "CipId" : $(this).parent().parent().children('td[name="cipId"]').children('input').val(),
			    "Status" : $(this).parent().parent().children('td[name="status"]').children('input').val(),
			    "Reconciled" : recdest, "BarCodeScanned" : barcd,
			    "WodId" : $(this).parent().parent().children('td[name="wodId"]').children('input').val(),
    	 	"serialNum" :$(this).parent().parent().children('td[name="serialNo"]').children('input').val()});
	        			                             		            			    				  
		    });  

		   	                  
		            			    		}




	function getselectedrowsmove () {

		var rec;
		var barcs;
		slctmove = [];

		$("#bisotab > tbody").find('input[name="record"]').each(function(){
		    
		    if ($(this).parent().parent().children('td[name="reconciled"]').children('input').is(':checked')){
	
		            rec='1'
					  }
				  else{
		             rec='0';
					  }

		    if ($(this).parent().parent().children('td[name="barcodescanned"]').children('input').is(':checked')){
				  				  
		            barcs='1'
					  }
				  else{
		             barcs='0';
					  }
		    

	     if($(this).is(":checked")){
      			    				    
	    slctmove.push({"ItemCode" : $(this).parent().parent().children('td[name="itemCode"]').children('input').val(),
		    "ItemModel" : $(this).parent().parent().children('td[name="itemModel"]').children('input').val(),
		    "ItemPartNumber" : $(this).parent().parent().children('td[name="itemPartNumber"]').children('input').val(),
		    "CurrentQty" : $(this).parent().parent().children('td[name="currentQty"]').children('input').val(),
		    "Qty" : $(this).parent().parent().children('td[name="qty"]').children('input').val(),
		    "FromWarehouse" : $(this).parent().parent().children('td[name="itemFromWare"]').children('input').val(),
	    	"ToWarehouse" : $(this).parent().parent().children('td[name="itemToWare"]').children('input').val(),
		    "NodeId" : $(this).parent().parent().children('td[name="nodeId"]').children('input').val(), 
		    "NodeName" : $(this).parent().parent().children('td[name="nodeName"]').children('input').val(), 
		    "GrId" : $(this).parent().parent().children('td[name="grId"]').children('input').val(), 
		    "PrId" : $(this).parent().parent().children('td[name="prId"]').children('input').val(),
		    "PoId" : $(this).parent().parent().children('td[name="poId"]').children('input').val(),
		    "CipId" : $(this).parent().parent().children('td[name="cipId"]').children('input').val(),
		    "Status" : $(this).parent().parent().children('td[name="status"]').children('input').val(),
		    "Reconciled" : rec, "BarCodeScanned" : barcs,
		    "WosId" : $(this).parent().parent().children('td[name="wosId"]').children('input').val(),
	    "serialNo" : $(this).parent().parent().children('td[name="serialNo"]').children('input').val()})
	        			                        
	     }        
		  			      			    				  
	    });


	}			            
		            
	 function saverowsintables (){
	       
        var workOrderId = document.getElementById("wocode").value;
		var wocreationDate = document.getElementById("wocreatedate").value;
	    var wolastModifieddate = document.getElementById("wolstmodifdate").value;
	    var woExecutionDate = document.getElementById("woexecutiondate").value;
	    var wofrmwarehouse = document.getElementById("wofrmware").value;
	    var wotowarehouse = document.getElementById("wotoware").value;
	    var wofrmwarehousename = document.getElementById("warename").value;
	    var wotowarehousename = document.getElementById("warenamedest").value;
	    var wofrmwarehousesiteId = document.getElementById("siteid").value;
	    var wotowarehousesiteId = document.getElementById("siteiddest").value;	   
	    var wostotalQty = document.getElementById("wostotqty").value;
	    var wodtotalQty = document.getElementById("wodtotqty").value;
	    var purpose = document.getElementById("purpose").value;     
	    var status=document.getElementById("wostat").value;
	    var frmware=$("#wofrmware").val();
		var m=  frmware.length;
		var n = frmware.indexOf(";");
		var res = frmware.substring(0, n);		     	
		var wareCode = $("#wotoware").val();
		var k = wareCode.indexOf(";");
		var resware = wareCode.substring(0, k);


		     	$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/WorkOrderFormSave",
					dataType : "json",
					data : {
						"type": '${ListWorkOrderItem}',
						"type2": '${ListWorkOrderDestItem}',
						"slctDelSrcItem[]": slctDelSrcItem,
						"slctDelDesItem[]": slctDelDesItem,
						"Parameter1": slctsave,
						"Parameter2": slctsave2,
						"workOrderId" : $("#wocode").val(),
						"wocreationDate" : wocreationDate,
						"wolastModifieddate" : wolastModifieddate,
						"woFromWare" : wofrmwarehouse,
						"woToWare" :$("#wotoware").val(),
						"woFromWareName" : wofrmwarehousename,
						"woToWareName" :wotowarehousename,
						"woFromWareSiteId" : wofrmwarehousesiteId,
						"woToWareSiteId" :wotowarehousesiteId,
						"woExecutionDate" :woExecutionDate,
						"wodtotalQty"  :   $("#wodtotqty").val() ,         
						"wostotalQty" :  $("#wostotqty").val() ,
						"Status" :  $("#wostat").val() ,
						"purpose" : $("#purpose").val(),
						"email": $("#email").val(),
						"password":$("#password").val(),
				  	    "emailTo": $("#emailTo").val(),
					    "ccmail": $("#ccmail").val(),
					    "subject": $("#subject").val(),
					    "message": $("#message").val(),

					},
					success : function(data) {
					
						// update dot save to green 
                       $('#wolstmodifdate').val(data.wolastModifieddate);
						wocode.value=data.WOID;
						var param ="${pageContext.request.contextPath}/WorkOrderFormView?workOrdId="+$("#wocode").val()+"&NavAction=2";
				        location.replace(param);
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

     }


	 /// give ID to each itemCode element
	    function GetExistingRows()
	    {

	    		var arr = $("#bisotab > tbody").find('input[name ="itmCode"]').get();
	    		for(i=0; i<arr.length;i++){
					arr[i].id = 'itemCode'+i;
		    	}
		}

	 
			            $('body').on('click', '#selectAllsrc', function  () {
							   
	      					if ($(this).hasClass('allChecked')) {
	         				$('input[ name="record"]', '#bisotab').prop('checked', false);
	      						} else {
	       						$('input[name="record"]', '#bisotab').prop('checked', true);
	       						}
	       						$(this).toggleClass('allChecked');
	       				
	     					})
						
	    			
	    			//when click on save or delete to select all rows by default
	    			function selectALLrowssrc () {
		    			if ($(this).hasClass('allChecked')) {
		         				$('input[name="record"]', '#bisotab').prop('checked', false);
		      						} else {
		       						$('input[name="record"]', '#bisotab').prop('checked', true);
		       						}
		       						$(this).toggleClass('allChecked');

		       						if ($(this).hasClass('allChecked')) {
				         				$('input[name="record"]', '#bisotab2').prop('checked', false);
				      						} else {
				       						$('input[name="record"]', '#bisotab2').prop('checked', true);
				       						}
				       						$(this).toggleClass('allChecked');
								}
						

//functions for the 2nd table 
			            $('body').on('click', '#selectAlldest', function  () {
							   
	      					if ($(this).hasClass('allChecked')) {
	         				$('input[name="record"]', '#bisotab2').prop('checked', false);
	      						} else {
	       						$('input[name="record"]', '#bisotab2').prop('checked', true);
	       						}
	       						$(this).toggleClass('allChecked');
	       				
	     					})
			  			            
			          /// give ID to each itemCode element
			            function GetExistingRows2()
					    {
					    		var arr = $("#bisotab2 > tbody").find('input[name ="itmCode"]').get();
					    		for(i=0; i<arr.length;i++){
									arr[i].id = 'itemCode'+i;
						    	}
						}
						
						
	});//end ready document
			
 </script>

<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
	async defer></script>

</html>