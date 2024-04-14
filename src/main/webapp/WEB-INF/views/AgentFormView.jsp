<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>
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
	src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />
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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/Agents/agent_BoqAreaPopup.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/Agents/agent_BoqShopsPopup.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/Agents/AgentsRegionPopup.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/Agents/AgentTracing_GoogleMap.js"></script>
<!--  <script src="https://smtpjs.com/v3/smtp.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/js/smtp.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
<style>

/*Doaa's popup Email Div'*/
.label {
	display: table-caption;
	text-align: center;
	font-size: 20px;
	font-style: bold;
}

.marker-position-sequence {
    bottom: -20px;
    font-weight:bold;
    fontSize: '10px';
    position: relative;
}

.left_col {
	height: 60px
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
.dot {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
}

.greenDot{
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: green;
}
.blueDot{
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: #0080ff;
}
.darkredDot{
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	background: #4D0207;
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

.dot {
	height: 15px;
	width: 15px;
	border-radius: 50%;
}

.dotYellow {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	background: #FFDC00;
}

.dotBlue {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	background: blue;
}

.dotRed {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	background: red;
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

.panel-body-info {
	border: 2px grey solid;
	padding: 0 18px;
	border-radius: 10px;
}

.btn-primary:hover {
	background-color: #007bff !important;
}

/*.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
/*overflow-x: both; /* add padding to account for vertical scrollbar */
/*	padding-right: 100px;
					z-index:9999;
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

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
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

.dot {
	height: 17px;
	width: 17px;
	background-color: chartreuse;
	border-radius: 50%;
	display: inline-block;
	margin-top: 10px;
	margin-right: 10px;
	margin-left: 10px;
}

.LINECOLORGREEN {
	height: 3.5px;
	width: 27px;
	background-color: #00ff00;
	display: inline-block;
	margin-bottom: 5px;
	margin-right: 10px;
	margin-left: 10px;
}

.LINECOLORBLUE {
	height: 3.5px;
	width: 27px;
	background-color: #06038D;
	display: inline-block;
	margin-bottom: 5px;
	margin-right: 10px;
	margin-left: 10px;
}

.dotStatus {
	height: 15px;
	width: 15px;
	background-color: orange;
	border-radius: 50%;
	display: inline-block;
	margin-top: 10px;
	margin-left: 10px;
}

#textalert {
	font-size: 15px;
	color: red;
	margin-left: 10px;
	display: inline-block;
	margin-bottom: 0px;
	padding-bottom: 0px;
}

#div1 {
	float: left;
}

#mapContainer {
	height: 450px;
	width: 100%;
}

.legendHeader {
	overflow: hidden;
	background-image: linear-gradient(to right top, #b3b3b3, #b6b6b7, #b8b9ba, #bbbdbd,
		#bdc0c0, #b1b5b5, #a5abaa, #9aa09f, #7f8685, #656e6c, #4c5655, #343f3e
		);
	padding: 10px 0px;
}

.legendContainer {
	border: 2px solid #808080;
	border-radius: 5px 5px 5px 5px;
	height: 480px;
	position: relative;
	width: 100%;
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

.dott {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
}

.red {
	background: red;
}

.green {
	background: #00ff00;
}

.orange {
	background: #FF8C00;
}

.blue {
	/* background:#0080ff; */
	background: #0000CD;
}

.purple {
	background: #0080ff;
}

.yellow {
	background: yellow;
}

#collapseOne {
	padding-bottom: 0.9%;
	padding-top: 0%;
	padding-right: 7%;
	height: 500px;
	width: 100%;
	float: right;
}

#areaTab {
	float: left; /* important */
	/* width:595px; */
	height: auto;
}

#regionTab {
	float: left; /* important */
	/* width:595px; */
	height: auto;
}

#mapText {
	border: hidden;
	width: 120px;
	height: 25px;
	border: hidden;
	background-color: #87CEEB;
	margin-left: 15px;
	text-align: center;
}

#legend {
	font-family: Arial, sans-serif;
	background: #fff;
	padding: 10px;
	margin: 10px;
	border: 3px solid #000;
}

#legend h3 {
	margin-top: 0;
}

#legend img {
	vertical-align: middle;
}

.custom-class-area-modal .modal-body {
	height: 500px;
	overflow: auto;
}

.custom-class-region-modal .modal-body {
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

.btn-tree {
	color: #474747;
	background-color: #d8d8d8;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

body {
	overflow-y: scroll !important;
}

.modal-backdrop.in {
	opacity: 0.9;
}

.bottom-container {
	padding-left: 1.5%;
	padding-right: 1.5%;
	padding-bottom: 2.5%;
	padding-top: 1%;
	width: 100%;
	height: available;
}

.leftpane {
	border-radius: 25px;
	border: 2px solid #808080;
	padding: 20px;
	width: 33.34%;
	height: 420px;
	float: left;
	border-collapse: collapse;
}

.middlepane {
	border-radius: 25px;
	border: 2px solid #808080;
	padding: 20px;
	width: 33.33%;
	height: 420px;
	float: left;
	border-collapse: collapse;
}

.rightpane {
	border-radius: 25px;
	border: 2px solid #808080;
	padding: 20px;
	width: 33.33%;
	height: 420px;
	position: relative;
	float: right;
	border-collapse: collapse;
}

#Dropdown {
	text-align: left;
}

#Dropdown a:hover {
	color: gold !important;
	text-decoration: none;
}

.toppane {
	width: 100%;
	height: 30px;
	border-collapse: collapse;
	background-color: #4da6ff;
}

.round {
	border-radius: 25px;
	border: 2px solid #808080;
	padding: 20px;
}

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
	.modal-content, .modal-dialog {
		max-height: 100%;
		max-width: 100%;
	}
}

.dropdown-submenu>a:after {
	display: block;
	content: " ";
	float: left;
	width: 0;
	height: 0;
	border-color: transparent;
	border-style: solid;
	border-width: 5px 5px 5px 0;
	border-right-color: #cccccc;
	margin-top: 5px;
	margin-left: -10px;
}

.imagesize {
	height: 350px;
	width: 350px;
	display: inline-block;
	margin-top: 10px;
}

.nav-link.active {
	color: #1D3763 !important;
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

	<%--   <c:set var = "page" value = "Sales"/> --%>

	<%-- 	<%@ include file="header.html" %> --%>
	<c:set var="pg" value="Sales" scope="session" />

	<jsp:include page="header.jsp"></jsp:include>

	<p></p>
	<div class="container-fluid">
		<div class="row">


			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width: 200px;" class="input-group-text">Agent
							ID</span> <input type="text" readonly id="agentID" value="${agentID}"
							class="form-control text-input" /> <input name="csrfToken"
							value="5965f0d244b7d32b334eff840" type="hidden" />
					</div>
				</div>
			</div>

			<div class="col-md-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width: 210px;">Status</span>
					<select id="ordstat" class="form-control">
						<option value="Draft"
							<c:if test = "${ordStatus =='Draft'}" > selected </c:if>>In
							Progress</option>
						<option value="Activated"
							<c:if test = "${ordStatus =='Activated'}" > selected </c:if>>Activated</option>
						<option value="Deactivated"
							<c:if test = "${ordStatus =='Deactivated'}" > selected </c:if>>Deactivated</option>
						<option value="Cancelled"
							<c:if test = "${ordStatus =='Cancelled'}" > selected </c:if>>Cancelled</option>
					</select>
				</div>
			</div>

			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Other Agents</span> <input
							type="text" id="selectnav" value="${selectnav}"
							style="width: 430px" class="form-control text-input" />
					</div>
				</div>
			</div>

			<!-- 		<div  class="col-md-3 text-right"  > -->

			<!-- 						</div>  -->

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
							value="${creationDate}" class="form-control datetimepicker-input"
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
							value="${lastModifiedDate}"
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
							onclick='window.location.href = "${pageContext.request.contextPath}/AgentListView"'
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
						style="color: gold;">INFORMATION</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-Image-tab" data-toggle="tab"
						href="#custom-tabs-Image" role="tab"
						aria-controls="#custom-tabs-Image" aria-selected="false"
						style="color: gold;">IMAGES</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-sim-tab" data-toggle="tab"
						href="#custom-tabs-one-sim" role="tab"
						aria-controls="custom-tabs-one-sim" aria-selected="false"
						style="color: gold;">SIM CARDS</a></li>


					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-trace-tab" data-toggle="tab"
						href="#custom-tabs-trace" role="tab"
						aria-controls="custom-tabs-one-trace" aria-selected="false"
						style="color: gold;">AGENT TRACING</a></li>






					<li class="nav-item ml-auto">
						<div class="dropdownmenu" id="mySharedown"
							Style="display: inline-block;">

							<!--  -->

							<label for="smsresult" id="smsresult" style="color: gold;"></label>

							<button class="btn btn-secondary dropdown-toggle" type="button"
								id="notifactionDropdown" onclick="CloseSubMenu();"
								data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">Actions</button>

							<div id="Dropdown" class="dropdownmenu-content">

								<ul class="dropdown-menu">
									<li><a class="dropdown-item" type="button" id="Activatewh"
										style="text-align-last: left;">Activate</a></li>
									<div class="dropdown-divider"></div>
									<li><a class="dropdown-item" type="button"
										id="Deactivatewh" style="text-align-last: left">Deactivate</a></li>
									<div class="dropdown-divider"></div>
									<li><a class="dropdown-item" type="button" id="Cancelwh"
										style="text-align-last: left">Cancel</a></li>
									<div class="dropdown-divider"></div>
									<li><a class="dropdown-item" href="#" id="GeneratePin"
										onclick="GeneratePin()" style="text-align-last: left">Generate
											PIN </a></li>
									<div class="dropdown-divider"></div>
									<li><a class="dropdown-item" type="button" id="sendEmail"
										onclick='OpenpopUpDiv(this)' style="text-align-last: left">Send
											Email</a></li>


									<div class="dropdown-divider"></div>

									<li class="dropdown-submenu"><a
										class="dropdown-item dropdown-toggle float-right"
										tabindex="-1" href="#" id="sendPin"
										style="text-align-last: right">Send PIN<span class="caret"></span></a>
										<ul class="dropdown-menu pull-right" id="sendPinStatus"
											style="margin-right: 10%">
											<li><a class="dropdown-item" tabindex="-1" href="#"
												id="SMS" style="text-align-last: left">By SMS<span
													class="caret"></span></a></li>
											<li><a class="dropdown-item" tabindex="-1" href="#"
												id="Email" style="text-align-last: left">By Email<span
													class="caret"></span></a></li>


										</ul></li>
							</div>

						</div>



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




	<div class="container-fluid" id="agentForm">

		<div class="tab-content" id="custom-tabs-one-tabContent">
			<div class="tab-pane fade show active" id="custom-tabs-one-home"
				role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">


				<p></p>
				<div class="wrapper" style="margin-top: -10px;">
					<div class="panel-group" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel panel-default" style="margin-bottom: 3px;">

							<div class="panel-heading " role="tab" id="headingOne">
								<h4 class="panel-title">
									<a role="button" data-toggle="collapse"
										data-parent="#accordion" href="#collapseThree"
										aria-expanded="true" aria-controls="collapseThree"> Agent
										Info </a>
								</h4>
							</div>
							<div id="collapseThree" class="panel-collapse collapse show"
								role="tabpanel" aria-labelledby="headingOne">
								<div class="panel-body-info"
									style='height: 100%; width: 100%; padding-top: 0%;'>
									<div class="card-body" style="width: 100%;">
										<div class="row">

											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">First Name</span> <input
															type="text" id="fname" value="${fname}"
															class="form-control text-input" />


													</div>
												</div>
											</div>


											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Last Name</span> <input
															type="text" id="lname" value="${lname}"
															class="form-control text-input" />

													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="input-group-prepend">
													<span class="input-group-text">MSISDN</span> <input
														type="text" id="MSISDN" value="${MSISDN}"
														class="form-control text-input" />
												</div>


											</div>


										</div>

										<!-- Choose Category Modal -->


										<div class="row">

											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Full Name</span> <input
															type="text" id="fullName" value="${fullName}"
															class="form-control text-input" />
													</div>
												</div>
											</div>
											<div class="col-md-4">
												<div class="input-group-prepend">
													<span class="input-group-text">PIN</span> <input
														type="text" id="PIN" value="${PIN}"
														class="form-control text-input" />
												</div>


											</div>

											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Display Name</span> <input
															type="text" id="dName" value="${dName}"
															class="form-control text-input" />
													</div>
												</div>
											</div>

										</div>

										<div class="row">



											<div class="col-md-4">
												<div class="input-group-prepend">
													<span class="input-group-text">Email</span> <input
														type="text" id="emaill" value="${emaill}"
														class="form-control text-input" />
												</div>
											</div>

											<div class="col-md-4">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Address</span> <input
															type="text" id="address" value="${address}"
															class="form-control text-input" />
													</div>
												</div>
											</div>

											<div class="col-md-2">

												<div class="form-group">
													<div class="input-group-prepend"
														data-target-input="nearest">
														<div class="input-group-text">

															<input type="checkbox" id="superAgentPermission"
																${superAgentPermission} /> <span
																style="margin-left: 10px;"> Super Agent
																Permission</span>
														</div>
													</div>
												</div>

											</div>
											<div class="col-md-2">

												<div class="form-group" style="margin-left: -15px;">
													<div class="input-group-prepend"
														data-target-input="nearest">
														<div class="input-group-text">

															<input type="checkbox" id="captureSpeed" ${captureSpeed} />
															<span style="margin-left: 10px;">Capture Speed
																Permission</span>
														</div>
													</div>
												</div>

											</div>

										</div>

										<div class="row">

											<div class="col-md-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Longtitude</span> <input
															type="text" id="agentLng" value="${agentLng}"
															class="form-control text-input" />
													</div>
												</div>
											</div>

											<div class="col-md-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span class="input-group-text">Latitude</span> <input
															type="text" id="agentLat" value="${agentLat}"
															class="form-control text-input" />
													</div>
												</div>
											</div>



										</div>
										<div class="row">
											<div class="col-md-3">

												<div class="form-group">
													<div class="input-group-prepend"
														data-target-input="nearest">
														<div class="input-group-text">

															<input type="checkbox" id="siteEngineer" ${siteEngineer} />
															<span style="margin-left: 10px;">Site Engineer
																Permission</span>
														</div>
													</div>
												</div>

											</div>

											<div class="col-md-3">
												<div class="form-group">
													<div class="input-group-prepend"
														style="margin-left: -140px">
														<span class="input-group-text">Site Engineer
															Interval Time</span> <input type="text" id="runningInterval"
															value="${runningInterval}"
															class="form-control text-input" disabled="disabled" />
													</div>
												</div>
											</div>

											<div class="col-md-3">

												<div class="form-group">
													<div class="input-group-prepend"
														data-target-input="nearest">
														<div class="input-group-text">

															<input type="checkbox" id="captureCoverage"
																${captureCoverage} /> <span style="margin-left: 10px;">Capture
																Coverage Permission</span>
														</div>
													</div>
												</div>

											</div>

											<div class="col-md-3">
												<div class="form-group" style="margin-left: 20px;">
													<div class="input-group-prepend"
														style="margin-left: -120px">
														<span class="input-group-text">Capture Coverage
															Interval Time</span> <input type="text"
															id="coverageRunningInterval"
															value="${coverageRunningInterval}"
															class="form-control text-input" disabled="disabled" />
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

				<p></p>
				<p></p>
				<div class="row">


					<div id="collapseOne" class="panel-collapse collapse show"
						aria-labelledby="headingOne">

						<div class="panel-body"
							style="height: 100%; margin-left: 10%; padding-top: 0%;">

							<div class="card-body"
								style='border: hidden; height: 100%; padding-top: 0%;'>

								<div class="legendContainer">
									<div class="card-body">
										<div class="btn-toolbar"
											style="text-align: left; margin-bottom: 5px; margin-left: 4%; margin-top: 1.35%; position: absolute; z-index: 1;">
											<div class="btn-group flex-wrap" data-toggle="buttons"
												style="row-gap: 2px;">
												<div class=" top-btn-filter">
													<button id="CoordsButton" name="CoordsButton"
														class="buttonTog">
														<i class="fas fa-map"></i> Get Coordinates
													</button>
												</div>
												<div class=" top-btn-filter">
													<button id="deleteCoordBtn" name="deleteCoordBtn"
														class="buttonTog" style='margin-left: 10px;'>
														<i class="fa fa-trash"></i> Delete Agent
													</button>



												</div>
												<div id="txtDiv">
													<input id="mapText" type='text' disabled />
												</div>

											</div>
										</div>

										<div class="box stack-top" id="legendDiv"
											style="position: relative; width: 215px; float: left; height: 270px; background: white; margin: 37px; display: none; top: 60px">

											<div class="legendHeader">

												<h6
													style="color: #3C1596; font-weight: bold; font-size: 3ex; display: inline-block; position: relative; top: 5px; left: 10px;">Legend</h6>
												<button id="SelectAll"
													style="background-color: transparent; color: #3C1596; font-weight: bold; outline: none; border: none; position: relative; left: 37px; top: 3.5px;"
													id="selectUnselect">Select All</button>

												<button id="SelectUnselectAll"
													style="background-color: transparent; color: #3C1596; font-weight: bold; outline: none; border: none; position: relative; left: 23px; top: 3.5px;"
													id="selectUnselect">Unselect All</button>
											</div>

											<table>



												<tr>
													<th style="position: relative; top: 5px; left: 10px;"></th>
													<th style="position: relative; top: 5px; left: 10px;"></th>
													<th style="position: relative; top: 5px; left: 10px;"></th>

												</tr>



												<tr>

													<td style="position: relative; top: 17px; left: 40px;"><input
														style="position: relative; top: 11px;" type="checkbox"
														name="legendCheckbox" id="AgentCHeckBox"
														onclick="ShowHideMarkers('#0080ff');" value="#0080ff"
														checked /></td>
													<td style="position: relative; top: 30px; left: 60px;">
														<div>
															<img
																src="${pageContext.request.contextPath}/resources/images/blue-marker.png"
																id="marker" style="position: relative; bottom: 3px;"
																width="20px" height="25px" />
														</div>
													</td>

													<td style="position: relative; top: 25px; left: 70px;"><label
														style="color: black; font-weight: bold; font-size: 2ex;">Agent</label></td>


												</tr>
												<tr>

													<td style="position: relative; top: 17px; left: 40px;"><input
														style="position: relative; top: 11px;" type="checkbox"
														name="legendCheckbox" id="ShopsCHeckBox"
														onclick="ShowHideMarkers('red');" value="yellow" checked /></td>
													<td style="position: relative; top: 30px; left: 60px;">
														<div>
															<img
																src="${pageContext.request.contextPath}/resources/images/red-marker.png"
																id="marker" style="position: relative; bottom: 3px;"
																width="13px" height="25px" />
														</div>
													</td>

													<td style="position: relative; top: 25px; left: 70px;"><label
														style="color: black; font-weight: bold; font-size: 2ex;">Shops</label></td>


												</tr>

												<tr>

													<td style="position: relative; top: 17px; left: 40px;"><input
														style="position: relative; top: 11px;" type="checkbox"
														name="legendCheckbox" id="AreaCHeckBox"
														onclick="ShowHideMarkers('blue');" value="green" checked /></td>
													<td style="position: relative; top: 30px; left: 60px;"><div
															class="LINECOLORBLUE"></div></td>

													<td style="position: relative; top: 25px; left: 70px;"><label
														style="color: black; font-weight: bold; font-size: 2ex;">Areas</label></td>


												</tr>
												<tr>

													<td style="position: relative; top: 17px; left: 40px;"><input
														style="position: relative; top: 11px;" type="checkbox"
														name="legendCheckbox" id="RegionCHeckBox"
														onclick="ShowHideMarkers('green');" value="red" checked /></td>
													<td style="position: relative; top: 30px; left: 60px;"><div
															class="LINECOLORGREEN"></div></td>

													<td style="position: relative; top: 25px; left: 70px;"><label
														style="color: black; font-weight: bold; font-size: 2ex;">Regions</label></td>


												</tr>



											</table>


										</div>
									</div>

									<div class="card-body"
										style="border: 2px solid #808080; border-radius: 30px 15px 15px 5px;">

										<div class="box" id="mapContainer"
											style="display: block; height: 450px; overflow-y: auto;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>


					<div class="bottom-container">
						<div class="leftpane">
							<H6 align="center">Shops</H6>
							<div>

								<form>

									<p></p>


									<div id="assign">

										<div class="table-responsive-sm">


											<table id="ShopsTab"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 300px; overflow-y: auto;">
												<thead>
													<tr class="fixed-headerr">
														<th>
															<div style="width: 80px">
																<button type="button" id="selectAllShops" class="main">
																	<span class="sub"></span>Select
																</button>
															</div>
														</th>


														<th style="text-align: center" width="150px" hidden>Shop
															Id</th>
														<th style="text-align: center" width="310px">Shop
															Name</th>
														<th style="text-align: center" width="100px" hidden>Longtitude</th>
														<th style="text-align: center" width="100px" hidden>Lattitude</th>
														<th style="text-align: center" width="150px" hidden>Creation
															Date</th>
														<th style="text-align: center" width="150px" hidden>Last
															Modified Date</th>
														<th style="text-align: center" width="150px" hidden>ID</th>

													</tr>
												</thead>

												<tbody>


												</tbody>

											</table>
											<p id="my_error" style="color: red;"></p>
										</div>
										<input type="button" class="addaction-row-Shops"
											id="adds_row-LFP" value="Add Row"
											onclick="addrowsShops('addRowShops')">
										<button type="button" onclick="deleterowsShops()"
											class="delete-row">Delete Row</button>
										<input type="text" id="RowIndexLFP" hidden value=""> <input
											type="text" id="RowIndex" hidden value=""> <input
											type="text" id="RowToDeleteShops" hidden value="">




									</div>
								</form>
							</div>
							<p></p>

						</div>
						<div class="middlepane">
							<H6 align="center">Areas</H6>

							<div>

								<form>

									<p></p>


									<div id="areas">

										<div class="table-responsive-sm">
											<table id="AreaTab"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 300px; overflow-y: auto;">
												<thead>
													<tr class="fixed-headerr">
														<th>
															<div style="width: 80px">
																<button type="button" id="selectAllArea" class="main">
																	<span class="sub"></span>Select
																</button>
															</div>
														</th>



														<th style="text-align: center" width="150px" hidden>Area
															Id</th>
														<th style="text-align: center" width="310px">Area
															Name</th>
														<th style="text-align: center" width="310px" hidden>Lng</th>
														<th style="text-align: center" width="310px" hidden>Lat</th>
														<th style="text-align: center" width="150px" hidden>SEQ_ID</th>
														<th style="text-align: center" width="150px" hidden>ID</th>

													</tr>
												</thead>

												<tbody>


												</tbody>

											</table>
											<p id="my_error" style="color: red;"></p>
										</div>
										<input type="button" class="addaction-row-Area"
											id="adds_row-LFP" value="Add Row"
											onclick="addrowsArea('addRowArea')">
										<button type="button" onclick="deleterowsArea()"
											class="delete-row">Delete Row</button>
										<input type="text" id="RowIndexLFP" hidden value=""> <input
											type="text" id="RowIndex" hidden value=""> <input
											type="text" id="RowToDeleteArea" hidden value="">


									</div>
								</form>

							</div>

							<p></p>
						</div>
						<div class="rightpane">
							<H6 align="center">Regions</H6>


							<div>

								<form>

									<p></p>


									<div id="regions">

										<div class="table-responsive-sm">
											<table id="RegionTab"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 300px; overflow-y: auto;">
												<thead>
													<tr class="fixed-headerr">
														<th>
															<div style="width: 80px">
																<button type="button" id="selectAllRegion" class="main">
																	<span class="sub"></span>Select
																</button>
															</div>

														</th>
														<th style="text-align: center" width="150px" hidden>Region
															Id</th>
														<th style="text-align: center" width="310px">Region
															Name</th>
														<th style="text-align: center" width="100px" hidden>lng</th>
														<th style="text-align: center" width="100px" hidden>lat</th>
														<th style="text-align: center" width="150px" hidden>Region
															Code</th>
														<th style="text-align: center" width="150px" hidden>ID</th>

													</tr>
												</thead>

												<tbody>


												</tbody>

											</table>
											<p id="my_error" style="color: red;"></p>
										</div>
										<input type="button" class="dsa" id="adds_row-LFP"
											value="Add Row" onclick="addrowsRegion('addRowRegion')">
										<button type="button" onclick="deleterowsRegion()"
											class="delete-row">Delete Row</button>
										<input type="text" id="RowIndexLFP" hidden value=""> <input
											type="text" id="RowIndex" hidden value=""> <input
											type="text" id="RowToDeleteRegion" hidden value="">


									</div>
								</form>

							</div>

							<p></p>


						</div>
					</div>


				</div>











			</div>


			<!-- end of information_tab interface -->

			<div class="tab-pane fade" id="custom-tabs-Image" role="tabpanel"
				aria-labelledby="custom-tabs-Image-tab">

				<p></p>
				<div>

					<form>



						<H1 align="center">Agent Image</H1>
						<H6 align="center" id="noImageAgent">
							No Image Uploaded
							</H1>
							<div style="text-align: center;">


								<img src="" alt="My test image" id="agentImage"
									class="imagesize">
							</div>

							<p></p>
							<H1 align="center">AGENT FRONT ID</H1>
							<H6 align="center" id="noImageFId">
								No Image Uploaded
								</H1>


								<div style="text-align: center;">

									<img src="" alt="My test image" id="AGENT_FRONT_ID"
										class="imagesize">

								</div>

								<p></p>
								<H1 align="center">AGENT BACK ID</H1>
								<H6 align="center" id="noImageBId">
									No Image Uploaded
									</H1>

									<div style="text-align: center;">

										<img src="" alt="My test image" id="AGENT_BACK_ID"
											class="imagesize">

									</div>
					</form>
				</div>

				<p></p>
			</div>

			<!-- start -->

			<div class="tab-pane fade" id="custom-tabs-one-sim" role="tabpanel"
				aria-labelledby="custom-tabs-one-profile-tab">
				<p></p>


				<div>
					<form>
						<div class="table-responsive-sm">
							<table id="simtab"
								class="table table-striped table-bordered table-sm"
								style="display: block; height: 200px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<!-- 						                <th> -->
										<!-- 								          <button type="button" id="selectAll" class="main"> -->
										<!-- 								          <span class="sub"></span>Select</button> -->
										<!-- 								          </th> -->
										<th><div>MSISDN</div></th>
										<th><div>Serial Number</div></th>
										<th><div>Status</div></th>
										<th><div>Creation Date</div></th>
										<th><div>Last Modified Date</div></th>
										<th><div>Sim Card ID</div></th>
									</tr>
								</thead>
								<tbody>


								</tbody>
							</table>
						</div>

						<!--  Text used to indicate row index -->
						<!-- 						<input type="button" class="add-row" value="Add Row" onclick="addrowsFinance('addRowFinance')"> -->
						<!-- 					    <button type="button" class="delete-row">Delete Row</button> -->
						<!-- 					    <input type="text" id="RowToDeleteFinance" hidden value=""> -->

					</form>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Number </span> <input
									type="text" id="totalNumberSim" value="${totalNumberSim}"
									readonly class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>

			</div>

			<!-- end -->

			<!-- Start of Tracing Tab -->
			<div class="tab-pane fade" id="custom-tabs-trace" role="tabpanel"
				aria-labelledby="custom-tabs-one-trace">

				<p></p>
				<div>

					<form>
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<div class="input-group-prepend"">
										<span class="input-group-text">MSISDN </span> <input
											type="text" id="agentMSISDN" value="${agentMSISDN}" readonly
											class="form-control text-input" style="width: auto;" />
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
										<span class="input-group-text">End Date</span> <input
											type="text" id="enddate"
											class="form-control datetimepicker-input"
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

							<div class="glyph" Style="white-space: nowrap; overflow: hidden;">

								<button type="button" id="generate"
									class="btn btn-primary BtnActive">Generate Report</button>


							</div>
						</div>

						<div class="wrapper" style="margin-top: -10px;">
							<div class="panel-group" id="accordion" role="tablist"
								aria-multiselectable="true">
								<div class="panel panel-default" style="margin-bottom: 3px;">
									<div class="panel-heading " role="tab" id="headingOne">
										<h4 class="panel-title">
											<a role="button" data-toggle="collapse"
												data-parent="#accordion" href="#collapseThree"
												aria-expanded="true" aria-controls="collapseThree"> GIS
											</a>
										</h4>
									</div>
									<div id="collapseThree" class="panel-collapse collapse show"
										role="tabpanel" aria-labelledby="headingOne">
										<div class="panel-body" style="height: 550px;">
										
											<div class="legendContainer"
												style="display: block; height: 540px; overflow-y: auto;">
												<div class="btn-toolbar"
											style="text-align: left; margin-bottom: 5px; margin-left: 4%; margin-top: 1.35%; position: absolute; z-index: 1;">
											<div class="btn-group flex-wrap" data-toggle="buttons"
												style="row-gap: 2px;">
												<div class=" top-btn-filter">
													<button id="showhideSeq"
														class="buttonTog">
														<i class="fas fa-edit"></i>Show/Hide Sequences
													</button>
												</div>
												
												

											</div>
										</div>

											<div class="box stack-top" id="legendSpeedCoverage"
												style="overflow-y: scroll; position: relative; top: 20px;left:50px; width: 300px; float: left; height: 320px; background: white;  display: block">

												<div class="legendHeader" id="legendHeader">

													<h6
														style="color: white; font-weight: bold; font-size: 3ex; display: inline-block; position: relative; left:20px;top: 5px;">Legends</h6>

													<button
														style="background-color: transparent; color: white; font-weight: bold; outline: none; border: none; position: relative; left:40px;top: 5px;"
														onClick="SelectUnselectAll()" id="selectUnselect">Unselect
														All</button>
												</div>

												<div id="tableSpeedCoverage">
													<table id="coverageLegend"><caption id="tableCaption" style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-180px;left:20px;">Signal Strength</caption>
													<tr>
													<td style="position: relative;top:30px;left:60px;"><div id="greenDot" class="greenDot"></div></td><td style="position: relative;top:30px;left:60px;"></td>
													<td style="position: relative;top: 30px;left:-20px"><label style="color:black;font-weight:bold;font-size:1.40ex; " >Excellent Signal</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="blueDot"></div></td><td style="position: relative;top:30px;left:70px;"></td>
													<td style="position: relative;top: 30px;left:-20px"><label style="color:black;font-weight:bold;font-size:1.40ex; " >Good Signal</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td><td style="position: relative;top: 30px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" >Fair Signal</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="darkredDot"></div></td><td style="position: relative;top: 30px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" >Bad Signal</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td><td style="position: relative;top: 30px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" >Extremely Bad</label></td></tr>
													<tr></tr></table>
													<table id="DownLoadLegend"><caption id="tableCaption" style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-180px;left:20px;">Download Speed</caption>
													<tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th>
													<th style="position: relative;top: 5px;left:10px;"></th></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="downloadGreen"></div></td><td style="position: relative;top:30px;left:60px;"></td>
													<td style="position: relative;top: 28px;left:-60px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " > > 30 Mbps Excellent</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="downloadBlue"></div></td><td style="position: relative;top:30px;left:60px;"></td>
													<td style="position: relative;top: 28px;left:-60px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " >15 - 30 Mbps Good</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="downloadYellow"></div></td><td style="position: relative;top: 28px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" >2 - 15 Mbps Fair</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="downloadDarkRed"></div></td><td style="position: relative;top: 28px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps - 2 Mbps Bad</label></td></tr><tr>
													<td style="position: relative;top:30px;left:60px;"><div class="downloadRed"></div></td><td style="position: relative;top: 28px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps < Ex. Bad </label></td></tr>
													<tr></tr></table>
													<table id="upLoadLegend"><caption id="tableCaption" style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-180px;left:20px;">Upload Speed</caption>
													<tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th>
													<th style="position: relative;top: 5px;left:10px;"></th></tr><tr>
													<td style="position: relative;top:27px;left:60px;"><div class="uploadGreen"></div></td><td style="position: relative;top:30px;left:60px;"></td>
													<td style="position: relative;top: 25px;left:-60px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " > > 30 Mbps Excellent</label></td></tr><tr>
													<td style="position: relative;top:27px;left:60px;"><div class="uploadBlue"></div></td><td style="position: relative;top:30px;left:60px;"></td>
													<td style="position: relative;top: 25px;left:-60px;"><label style="color:black;font-weight:bold;font-size:1.40ex; " >15 - 30 Mbps Good</label></td></tr><tr>
													<td style="position: relative;top:27px;left:60px;"><div class="uploadYellow"></div></td><td style="position: relative;top: 25px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" >2 - 15 Mbps Fair</label></td></tr><tr>
													<td style="position: relative;top:27px;left:60px;"><div class="uploadDarkRed"></div></td><td style="position: relative;top: 25px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps - 2 Mbps Bad</label></td></tr><tr>
													<td style="position: relative;top:27px;left:60px;"><div class="uploadRed"></div></td><td style="position: relative;top: 25px;left:70px;">
													<label style="color:black;font-weight:bold;font-size:1.40ex;" > 0.25 Mbps Ex. Bad < </label></td></tr>
													<tr></tr></table>
												
												</div>
												
											</div>
												
												<div class="box" id="mapContainer1"
													style="display: block; height: 500px; overflow-y: auto;">

												</div>
											</div>
										</div>
									</div>
									<!-- To be determined later for GRID TABLE -->
									<div class="panel panel-default" style="margin-bottom: 3px;">
										<div class="panel-heading" role="tab" id="headingTwo">
											<h4 class="panel-title">
												<a role="button" data-toggle="collapse"
													data-parent="#accordion" href="#collapseTwo"
													aria-expanded="true" aria-controls="collapseTwo"> Grid
													Table </a>
											</h4>
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

																				<th>Start Date
																					<li class="filter-dropdown dropdown">
																						<button class="almgrid-filter"
																							data-toggle="dropdown">
																							<i class="fa fa-list almgrid-filter-i"
																								aria-hidden="true"></i>
																						</button>
																						<ul class="dropdown-menu filter-dropdown-ul">

																						</ul>
																				</li>
																				</th>
																				<th>End Date
																					<li class="filter-dropdown dropdown">
																						<button class="almgrid-filter"
																							data-toggle="dropdown">
																							<i class="fa fa-list almgrid-filter-i"
																								aria-hidden="true"></i>
																						</button>
																						<ul class="dropdown-menu filter-dropdown-ul">

																						</ul>
																				</li>
																				</th>
																				<th>LATITUDE
																					<li class="filter-dropdown dropdown">
																						<button class="almgrid-filter"
																							data-toggle="dropdown">
																							<i class="fa fa-list almgrid-filter-i"
																								aria-hidden="true"></i>
																						</button>
																						<ul class="dropdown-menu filter-dropdown-ul">

																						</ul>
																				</li>
																				</th>
																				<th>LONGITUDE
																					<li class="filter-dropdown dropdown">
																						<button class="almgrid-filter"
																							data-toggle="dropdown">
																							<i class="fa fa-list almgrid-filter-i"
																								aria-hidden="true"></i>
																						</button>
																						<ul class="dropdown-menu filter-dropdown-ul">

																						</ul>
																				</li>
																				</th>
																				<th>Signal Strength
																					<li class="filter-dropdown dropdown">
																						<button class="almgrid-filter"
																							data-toggle="dropdown">
																							<i class="fa fa-list almgrid-filter-i"
																								aria-hidden="true"></i>
																						</button>
																						<ul
																							class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																						</ul>
																				</li>
																				</th>
																				<th>Speed Download
																					<li class="filter-dropdown dropdown">
																						<button class="almgrid-filter"
																							data-toggle="dropdown">
																							<i class="fa fa-list almgrid-filter-i"
																								aria-hidden="true"></i>
																						</button>
																						<ul
																							class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

																						</ul>
																				</li>
																				</th>
																				<th>Speed Upload
																					<li class="filter-dropdown dropdown">
																						<button class="almgrid-filter"
																							data-toggle="dropdown">
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

									</div>




								</div>


							</div>

						</div>
					</form>
				</div>

				<p></p>
			</div>

			<!-- popoup Area -->

			<div class="container">
				<div id="DNModalArea" class="modal fade custom-class-area-modal"
					tabindex="-1" role="dialog"
					aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header"
								style="background-color: #007BFF; height: 56px">
								<h5 id="popupArea" class="modal-title"
									style="font-weight: bold; color: gold; margin-top: -3px"></h5>
								<div style="float: right;">
									<button type="button" name="insertBelowArea"
										onclick="insertRowBelowArea()"
										class="btn btn-default btn-primary BtnActive btn-pop "
										style="color: black; position: relative; left: -30px; font-weight: bold; margin-top: -7px;">Insert
										Below</button>
									<button type="button" name="insertAboveArea"
										onclick="insertRowAboveArea()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: -20px; font-weight: bold; margin-top: -7px;">Insert
										Above</button>
									<button type="button" name="deleteBoqRowArea"
										onclick="deleteBoqRowArea()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: -10px; font-weight: bold; margin-top: -7px;">Delete</button>
									<button name="previousRowArea"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: 0px; font-weight: bold; margin-top: -7px;">Previous</button>
									<button name="nextRowArea" onclick="nextRowArea()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: 10px; font-weight: bold; margin-top: -7px;">Next</button>

									<button type="button" name="closeModPartPopup" class="close"
										data-dismiss="modal">
										<i class='fa fa-times'></i>
									</button>
									<a class="close modalMinimize ml-3"> <i
										class='fa fa-minus icon-to-change'></i></a>
								</div>
							</div>
							<div class="modal-body">
								<ul class="nav nav-tabs" id="myTab" role="tablist"
									style="background-color: #00757C;">
									<li class="nav-item"><a class="nav-link active"
										id="modPart-tab" style="color: gold;" data-toggle="tab"
										href="#modPart" role="tab" aria-controls="modPart"
										aria-selected="true">Areas</a></li>
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
															<span class="input-group-text">Area ID</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupareaId" value=""
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
															<span class="input-group-text">Area Name</span> <input
																type="text" id="popupareaName"
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


												<div class="col-sm-6" hidden>
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">ID</span> <input
																type="text" id="popupagentAreaId"
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


			<!-- end of popoup -->

			<div class="container">
				<div id="DNModalShops"
					class="modal fade custom-class-assignedto-modal "
					data-backdrop="static" data-keyboard="false" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header"
								style="background-color: #007BFF; height: 56px">
								<h5 id="popupShops" class="modal-title"
									style="font-weight: bold; color: gold; margin-top: -3px;"></h5>
								<div style="float: right;">
									<button type="button" name="insertBelowShops"
										onclick="insertRowBelowShops()"
										class="btn btn-default btn-primary BtnActive btn-pop "
										style="color: black; position: relative; left: -30px; font-weight: bold; margin-top: -7px;">Insert
										Below</button>
									<button type="button" name="insertAboveShops"
										onclick="insertRowAboveShops()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: -20px; font-weight: bold; margin-top: -7px;">Insert
										Above</button>
									<button type="button" name="deleteBoqRowShops"
										onclick="deleteBoqRowShops()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: -10px; font-weight: bold; margin-top: -7px;">Delete</button>
									<button name="previousRowShops"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: 0px; font-weight: bold; margin-top: -7px;">Previous</button>
									<button name="nextRowShops" onclick="nextRowShops()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: 10px; font-weight: bold; margin-top: -7px;">Next</button>

									<button type="button" name="closeModPartPopup" class="close"
										data-dismiss="modal">
										<i class='fa fa-times'></i>
									</button>
									<a class="close modalMinimize ml-3"> <i
										class='fa fa-minus icon-to-change'></i>
									</a>
								</div>
							</div>
							<div class="modal-body" style="height: 500px;">
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
										<p></p>

										<p></p>

										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Shop Id</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupShopsId" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Shop Name</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupShopsName" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<p></p>
										<p></p>







										<div class="container-fluid">
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Longtitude</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupLogntitude" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Latitude</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupLatitude" value=""
																style="width: 675px; height: 37px;" />
														</div>
													</div>
												</div>
											</div>
										</div>

										<p></p>
										<p></p>


















										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Creation Date</span> <input
																type="text" id="popupCreationDate"
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" readonly />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Last Modified Date</span>
															<input type="text" id="popupLastModifiedDate"
																class="form-control text-input" value=""
																style="width: 675px; height: 37px;" readonly />

														</div>
													</div>
												</div>
											</div>
										</div>
										<p></p>
										<p></p>
										<p></p>

										<div class="container-fluid">
											<div class="row">
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

			<!--end of popupShops  -->









			<!-- popoup Area -->

			<div class="container">
				<div id="DNModalRegion" class="modal fade custom-class-region-modal"
					tabindex="-1" role="dialog"
					aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header"
								style="background-color: #007BFF; height: 56px">
								<h5 id="popupRegion" class="modal-title"
									style="font-weight: bold; color: gold; margin-top: -7px;"></h5>
								<div style="float: right;">
									<button type="button" name="insertBelowRegion btn-pop"
										onclick="insertRowBelowRegion()"
										class="btn btn-default btn-primary BtnActive btn-pop "
										style="color: black; position: relative; left: -30px; font-weight: bold; margin-top: -7px;">Insert
										Below</button>
									<button type="button" name="insertAboveRegion"
										onclick="insertRowAboveRegion()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: -20px; font-weight: bold; margin-top: -7px;">Insert
										Above</button>
									<button type="button" name="deleteBoqRowRegion"
										onclick="deleteBoqRowRegion()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: -10px; font-weight: bold; margin-top: -7px;">Delete</button>
									<button name="previousRowRegion"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: 0px; font-weight: bold; margin-top: -7px;">Previous</button>
									<button name="nextRowRegion" onclick="nextRowRegion()"
										class="btn btn-default btn-primary BtnActive btn-pop"
										style="color: black; position: relative; left: 10px; font-weight: bold; margin-top: -7px;">Next</button>

									<button type="button" name="closeModPartPopup" class="close"
										data-dismiss="modal">
										<i class='fa fa-times'></i>
									</button>
									<a class="close modalMinimize ml-3"> <i
										class='fa fa-minus icon-to-change'></i>
									</a>
								</div>
							</div>
							<div class="modal-body">
								<ul class="nav nav-tabs" id="myTab" role="tablist"
									style="background-color: #00757C;">
									<li class="nav-item"><a class="nav-link active"
										id="modPart-tab" style="color: gold;" data-toggle="tab"
										href="#modPart" role="tab" aria-controls="modPart"
										aria-selected="true">Regions</a></li>
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
															<span class="input-group-text">Region ID</span> <input
																type="text"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupregionId" value=""
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
															<span class="input-group-text">Region Name</span> <input
																type="text" id="popupregionName"
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
															<span class="input-group-text">Region Code</span> <input
																type="text" id="popupregionCode"
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


												<div class="col-sm-6" hidden>
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Id</span> <input
																type="text" id="popupagentRegionId"
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
			<!-- end of popoup -->

			<!-- end of shops_tab interface -->


			<!-- end of Areas_tab interface -->
			<!-- end of Regions_tab interface -->
		</div>
	</div>

	<!-- emailPopup -->



	<div class="container">
		<div id="popUpDiv" class="modal fade custom-class-region-modal "
			tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
			aria-hidden="true">
			<div
				class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered sendEmail">
				<div class="modal-content" style="width: 65%;">
					<div class="modal-header" style="background-color: #007BFF;">
						<h5 id="popupEmail" class="modal-title"
							style="font-weight: bold; color: #3C1596;"></h5>


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
								aria-selected="true">Email</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="modPart" role="tabpanel"
								aria-labelledby="modPart-tab">
								<p></p>




								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text "> Sender</span> <input
														type="text" id="email" style="width: 674px; height: 37px;"
														class="ui-widget ui-widget-content ui-corner-all form-control" />
													<input type="text" id="password" value="${password}"
														class="ui-widget ui-widget-content ui-corner-all form-control"
														hidden />




												</div>
											</div>
										</div>


									</div>
								</div>

								<div class="container-fluid">
									<div class="row">

										<div class="col-sm-12">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">Email To</span> <input
														type="text" id="emailTo"
														style="width: 674px; height: 37px;"
														class="form-control text-input" />

												</div>
											</div>
										</div>
									</div>
								</div>



								<div class="container-fluid">
									<div class="row">


										<div class="col-sm-12">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">ccEmail </span> <input
														type="text" id="ccmail"
														style="width: 674px; height: 37px;"
														class="form-control text-input" />

												</div>
											</div>
										</div>


									</div>
								</div>


								<div class="container-fluid">
									<div class="row">




										<div class="col-sm-12">
											<div class="form-group">
												<div class="input-group-prepend">
													<span class="input-group-text">Title</span> <input
														type="text" id="subject"
														style="width: 674px; height: 37px;"
														class="form-control text-input" />

												</div>
											</div>
										</div>
									</div>
								</div>


								<div class="container-fluid">
									<div class="row">


										<div class="col-sm-12">
											<div class="input-group-prepend">
												<span class="input-group-text" style="width: 100px;">Message</span>

											</div>
										</div>

									</div>
									<div class="row">


										<div class="col-sm-12">
											<div class="input-group-prepend">
												<textarea id="message" class="form-control text-input"
													cols="200" rows="4"></textarea>

											</div>
										</div>

									</div>
								</div>
								<p></p>

								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-3"></div>

										<div class="col-sm-3">
											<form method="post">
												<button type="button" id="send" onclick="sendMail()"
													class="btn btn-primary BtnActive">
													<i class="fa fa-paper-plane"></i> Send
												</button>
											</form>

										</div>

										<div class="col-sm-4">
											<button type="button" id="cancelButton"
												class="btn btn-primary BtnActive">
												<i class="fa fa-times"></i> Cancel
											</button>

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
</body>



<script type='text/javascript'>

if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}


var boqArrayArea = [];     
var areaCoordinates =[];
var boqArrayAreaTable=[];
var boqArrayRegion = [];
var RegionCoordinates =[];
var prevAreaId=null;
var AreaPrevBorders = null;
var RegionPrevBorders = null;
//var prePrevAreaId=null;
//var prePrevAreaId2=null;
//const ress = [];
//const fress = [];
//var f=0;
let map,map1;
let service;
let infowindow;
var showArea=1;// indicator to show Area if 1
var showAgent=1;// indicator to show Agent if 1
var showShops;// indicator to show shops if 1
var showRegions=1;// indicator to show Region if 1
var temp=0;
var areaToDraw= {};// areas to draw
var regionToDraw= {};// regions to draw
//var selectedArea={};// when selecting or changing area from autocomplete//to be deleted
//var selectedRegion={};// when selecting or changing Region from autocomplete//to be deleted
//var ListOfShopsLng = [];//to be deleted
//var ListOfShopsLat = [];//to be deleted
//var ListOfShopsName = [];//to be deleted
//var tempPopupareaId=null; //indicate previous area_ID on change  
//var tempPopupregionId =null; //indicate previous region_ID on change
//var listShops =[]; // It is a list of hashmap shops, it will be filled by mapShop, so it is an of that type: [{}, {}, ...]. // to be deleted
//var mapShop = {}; // It will contains a keys: shopName, latitiude, longitude and their values.//to be deleted
var ShopLocation = [];
var Tabora = [];
var RegionSet = [];
var polygons = [];
var areas = [];
var regions = [];
var areaPolygon=[];
var regionPolygon=[];
var areacheck=2; // check if from load (2) or if edited or added new (1)
var regioncheck=2;// check if from load (2) or if edited or added new (1
var infowindow1;
var shopMarkers=[];
var areaPolygons=[];
var regionPolygons=[];
var rowIndex=10000000000000;
var PrevrowIndex=10000000000000;
var areanamee=" ";
var marker, i;
let markers = [];
var shopid;
polygons = [];
dictShops = [];
//maplng=37.9062;//to be deleted
//maplat= 0.0236;//to be deleted
///var latitude ;//to be deleted
//var longtitude ;//to be deleted
//var latitude ;//to be deleted
//var longtitude ;//to be deleted
//var midLat=0;//to be deleted
//var midLong=0;//to be deleted
//var cityCircle;//to be deleted
//var s=0;//to be deleted
//var ShopName;
//var ShopLat;
//var ShopLong;
 var Email = { send: function (a) { 
	 return new Promise(function (n, e) {
		  a.nocache = Math.floor(1e6 * Math.random() + 1),
		   a.Action = "Send";
		    var t = JSON.stringify(a); 
		  Email.ajaxPost("https://smtpjs.com/v3/smtpjs.aspx?", t, function (e) { n(e) })
		   }) },
		    ajaxPost: function (e, n, t) {
			     var a = Email.createCORSRequest("POST", e);
			      a.setRequestHeader("Content-type", "application/x-www-form-urlencoded"),
			       a.onload = function () {
				        var e = a.responseText;
				         null != t && t(e) },
				          a.send(n) },
				           ajax: function (e, n) {
					            var t = Email.createCORSRequest("GET", e);
					             t.onload = function () {
						              var e = t.responseText;
						               null != n && n(e) },
						                t.send() },
						                 createCORSRequest: function (e, n) {
							                  var t = new XMLHttpRequest;
							                   return "withCredentials" in t ? t.open(e, n, !0) : "undefined" != typeof XDomainRequest ? (t = new XDomainRequest).open(e, n) : t = null, t } };

	 function sendMail() {
		console.log("sending email");
	  
	 var SenderEmail = document.getElementById("email").value;
	 var emailTo = document.getElementById("emailTo").value;
	 var subject =document.getElementById("subject").value;
	 var message =document.getElementById("message").value;
	 
//this is the html template. You can also do it as used above. But is much simpler done as below

	 Email.send({
	        Host: "smtp.gmail.com",
	        Username: SenderEmail,
	        Password: "Ah5alousamiz",
	        To: emailTo,
	        From: SenderEmail,
	        Subject: subject,
	        Body: message,
	      })
	        .then(function (message) {
	          alert("mail sent successfully");
	 		 $("#popUpDiv").modal("hide");
	          
	        });
     
 }

 function OpenpopUpDiv(element) {
	console.log("Inside OpenpopUpDiv");
	 var buttonNodeRowIndx = $(element).closest("tr");
		 $("#popUpDiv").modal("show");
		 var element = document.getElementById("popupEmail");
			element.innerHTML = "Email" ;
 }
 
    function CloseSubMenu(){
    	$("#smsresult").text("");
    	  if ($('#Dropdown').css('display') == 'none') {
    		    document.getElementById('Dropdown').style.display = "block";
    		 } 
    	document.getElementById('sendPinStatus').style.display = "none";
    }; 

    
    
    window.addEventListener('click', function(e){   
    	  if (document.getElementById('mySharedown').contains(e.target)){
 			 document.getElementById('sendPinStatus').style.display = "none";
    	  } else{
    			 document.getElementById('sendPinStatus').style.display = "none";
    		   }
    	});
 
 
 window.onclick = function(event) {
     if (!event.target.matches('.dropbutton')) {
       
         var dropdowns = 
         document.getElementsByClassName("dropdownmenu-content");
           
         var i;
         for (i = 0; i < dropdowns.length; i++) {
             var openDropdown = dropdowns[i];
             if (openDropdown.classList.contains('show')) {
                 openDropdown.classList.remove('show');
             }
         }
     }
 }



 if ('${AGENT_IMAGE}' != "addNew") {

	 document.getElementById('noImageAgent').style.display = "none"; 
	 document.getElementById('agentImage').src = "/SimRegPhotos/AgentPic/${AGENT_IMAGE}.jpg";
	 
 }else{
	    document.getElementById('agentImage').style.display = 'none';
		 
	 }

 if ('${AGENT_FRONT_ID}' != "addNew") {

	 document.getElementById('noImageFId').style.display = "none"; 
	 document.getElementById('AGENT_FRONT_ID').src = "/SimRegPhotos/AgentPic/${AGENT_FRONT_ID}.jpg";
	 
 }else{
	 
	    document.getElementById('AGENT_FRONT_ID').style.display = 'none';
		 
  }

 if ('${AGENT_BACK_ID}' != "addNew") {
	 
	 document.getElementById('noImageBId').style.display = "none";
	 document.getElementById('AGENT_BACK_ID').src = "/SimRegPhotos/AgentPic/${AGENT_BACK_ID}.jpg";
	 
 }else{
	    document.getElementById('AGENT_BACK_ID').style.display = 'none';
		 
  }
 
 
 function GeneratePin(){
	 var fourdigitsrandom = Math.floor(1000 + Math.random() * 9000);
	 document.getElementById("PIN").value=fourdigitsrandom;
	 alert("Your pin is "+fourdigitsrandom);

 }
 
$('#sendPin').on("click", function(e){
	    $(this).next('ul').toggle();
	    e.stopPropagation();
	    e.preventDefault();
	  });


if ('${listAgentShops}' != "addNew") ShopLocation = ${listAgentShops};
if ('${areasData}' != "addNew") areas = ${areasData};
if ('${regionsData}' != "addNew") regions = ${regionsData};

if(ShopLocation.length==0){showShops=0;}else{showShops=1;}
function initMap() {
	var cntr = new google.maps.LatLng(0.300 , 37.761);
	var bounds = new google.maps.LatLngBounds();

     	
	map = new google.maps.Map(document.getElementById("mapContainer"), {
		zoom: 6,
		center: cntr,
		mapTypeId: "terrain",
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
		style: 'mapbox://styles/mapbox/streets-v11',
		fullscreenControl: true,
	});
     
// To be deleted     
/*     $("#peakMonth").attr('disabled', true);
     $("#accuLegend").attr('disabled', true); */
	
 	
 	
	if(showShops===1) {    	 	
		for (i = 0;i<ShopLocation.length;i++){
//to be deleted			
/*    		mapShop.shopName = ShopLocation[i][1];
    		mapShop.longtitude = ShopLocation[i][2];
    		mapShop.latitude = ShopLocation[i][3]; */
			if(parseFloat(ShopLocation[i][2]) != 0 && parseFloat(ShopLocation[i][3]) != 0){
//				listShops.push(mapShop);
// To be deleted.    				
//    				var Shopsname = $(this).parent().parent().children('td[name="Shopsname"]').children('input').val();
    			infowindow = new google.maps.InfoWindow();
    				
    			const marker = new google.maps.Marker({
    				position: new google.maps.LatLng(ShopLocation[i][3], ShopLocation[i][2]),
    				map: map,
    				ID:ShopLocation[i][4],
    				data: "<div>Shop Name: " +ShopLocation[i][1]+"<br>Longitude: "+ShopLocation[i][2]+"<br>Latitude: "+ShopLocation[i][3]+"</div>",
   				description: ShopLocation[i][1]
    			});
// to be deleted    				
//  			      var data="<div>Shop Name: " +ShopLocation[i][1]+"<br>Longitude: "+ShopLocation[i][2]+"<br>Latitude: "+ShopLocation[i][3]+"</div>";

//    				var contentwindow = data;
    		
					marker.metadata = { id: ShopLocation[i][4] };
					shopMarekrs=[];
					shopMarkers[ShopLocation[i][4]] = marker;
					shopMarkers.push(marker);

    				google.maps.event.addListener(marker, "click", function (e) {
    					infowindow.setContent(this.data); 
			        	infowindow.open(map,this);

    				});
    			} // end if statement for shop long & lat not equal zero.
    	 } // end for loop of shop location list.

     

 }	 
////////////////////////////////////////////
//area


//LONG LAT ON TIME INDICATOR
$("#mapText").val(JSON.stringify((-1.286) +" || " +(36.817), null, 2));
map.addListener("mousemove", (mapsMouseEvent)=>{
	
    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));
    
 });
 
function addMarker(position) {
	
var address =$("#address").val();
var fullName =$("#fullName").val();

// to be deleted
//	  var contentOfInfoWindow = "Agent Name : "+fullName+"<p><p>"+   "Address : "+address;

		 marker  = new google.maps.Marker({
			    position,
			    map,
			    size: new google.maps.Size(36, 50),
			    scaledSize: new google.maps.Size(36, 50),
		 	 	   icon: {
			 	      url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
			 	      scaledSize: new google.maps.Size(50, 50),
		 	    }
		  });

	       infowindow1 = new google.maps.InfoWindow({
//	          content: contentOfInfoWindow
	          content: "Agent Name : "+fullName+"<p><p>"+   "Address : "+address
	        });

	  	  	markers.push(marker);
			google.maps.event.addListener(marker, "click", function (e) {
				infowindow1.open(map,marker);
	        });
		   // to be deleted
	  	  //infowindow1.open(map, marker);
	  	  
	  	} // end of addMarker method
	
function setMapOnAll(map) {	
	  for (let i = 0; i < markers.length; i++) {
	    markers[i].setMap(map);	    
	  }
	}

	// Removes the markers from the map, but keeps them in the array.
	function hideMarkers() {
	  setMapOnAll(null);
	}

	// Shows any markers currently in the array.
	function showMarkers() {
	  setMapOnAll(map);
	}

	// Deletes all markers in the array by removing references to them.
	function deleteMarkers() {	   
	  setMapOnAll(null);		
	  hideMarkers();
	  markers = [];
	}

   let infoWindow = new google.maps.InfoWindow;
   

       
     map.addListener("click", (mapsMouseEvent) => {
         // Close the current InfoWindow.
         infoWindow.close();
         // Create a new InfoWindow.
         
         infoWindow = new google.maps.InfoWindow({           
         	position: mapsMouseEvent.latLng,
         });
//         myLatlng=JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2);
         
         infoWindow.setContent(
         	JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
         );
 		infoWindow.open(map);
 		
//        const myJSON = JSON.stringify(myLatlng);
//        localStorage.setItem("testJSON", myJSON);
        localStorage.setItem("testJSON", JSON.stringify(JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)));
        let text = localStorage.getItem("testJSON");
        let obj = JSON.parse(text);
     	var arr = obj.split(",");
     	var reslatitude =arr[0].substring(11);
     	var reslongtitude =arr[1].substring(10);
     	reslongtitude=reslongtitude.slice(0,-1);
          
     	google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {
     		deleteMarkers();
     		$("#formStatus").text("Not Saved");
    		$('.dot').css({"background-color" : "orange"});
/*     	    var agentLat = $("#agentLat").val();
     	   	var agentLng = $("#agentLng").val();
     		var agentLat =reslatitude;
     		var agentLng =reslongtitude; */
     		var position1 = new google.maps.LatLng(reslatitude, reslongtitude);
     		addMarker(position1);
     		
     		
/*     		for (let i = 0; i < markers.length; i++) {         	
     	    	markers[i].setMap(map);
     	  	} */
  			 document.getElementById("agentLat").value=reslatitude;
  			 document.getElementById("agentLng").value=reslongtitude;
		}));

// to be deleted     	
     		google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (Path) {
  			 	$('#agentLat').val("");
  				document.getElementById("agentLng").value = "";
  	     		deleteMarkers();
  	     		$("#formStatus").text("Not Saved");
  	    		$('.dot').css({"background-color" : "orange"});

  				}));

     		
       }); // End of Click map listener.
		
	if(showAgent===1){
/*		 if ('${listAreaBorder}' != "addNew") {
		 	deleteMarkers();
			var  agentLat = $("#agentLat").val();
			var agentLng = $("#agentLng").val();			 
			var position1 = new google.maps.LatLng(agentLat, agentLng);
			for (let i = 0; i < markers.length; i++) {
				markers[i].setMap(map);
			}
		  } */

		$('#agentLat,#agentLng').change(function() {	        
    		deleteMarkers();
			var  agentLat = $("#agentLat").val();
			var agentLng = $("#agentLng").val();
			var position1 = new google.maps.LatLng(agentLat, agentLng);
		addMarker(position1);
// to be deleted		
/*		for (let i = 0; i < markers.length; i++) {			
		    markers[i].setMap(map);		    
		 } */
		});
		if ($('#agentID').val() == '') {var position1 = new google.maps.LatLng(0.300, 37.761);}
		else {var position1 = new google.maps.LatLng(document.getElementById("agentLat").value, document.getElementById("agentLng").value);}		
		addMarker(position1);
// to be deleted		
/*		for (let i = 0; i < markers.length; i++) { 	
	    	markers[i].setMap(map);	    
	  	} */

 	}
	else{	// If showAgent is 0 (disabled from Legend)
		deleteMarkers();
	}

     //var latArray;
     //var lngArray;

     
     if(areacheck==2){
         
     	if ('${listAreaBorder}' != "addNew") {
     		boqArrayArea = ${listAreaBorder};
			areaToDraw = Object.assign(areaToDraw, boqArrayArea);
		 }
	  }
	 
	 if(showArea===1){
		 
		if(Object.keys(areaToDraw).length != 0){
		
			//console.log(Object.keys(areaToDraw).length);
	  for (i = 0;i<Object.keys(areaToDraw).length;i++){
		  console.log("hello");
			Tabora.length=0;
		  	areaCoordinates=areaToDraw[Object.keys(areaToDraw)[i]];
			for (j = 0;j<areaToDraw[Object.keys(areaToDraw)[i]].length;j++){
				
				Tabora.push({
					
				      lat:parseFloat(areaCoordinates[j].lat),
				      lng: parseFloat(areaCoordinates[j].lng)
				      
				    });
			}
			AreaPrevBorders = areaCoordinates;
			 areaPolygon= new google.maps.Polygon({
			  	 
		  	    path: Tabora,
		  	    geodesic: false,
		  	    strokeColor: '#06038D',
		  	  	ID : Object.keys(areaToDraw)[i],
		  	    strokeOpacity: 1.0,
		  	    fillOpacity: 0.01,
		  	    strokeWeight: 5,
 		  	    clickable: false,
			  	Editable: false,
		  	    map: map
		  	  });

		  	areaPolygon.metadata = { id: Object.keys(areaToDraw)[i] };
		  	//areaPolygons=[];
	     	areaPolygons[Object.keys(areaToDraw)[i]] = areaPolygon;
	     	areaPolygons.push(areaPolygon);
	     	
	  }	 
/*	  
	  for (var j = 0; j < polygons.length; j++) {
		  
	  	    for (var i = 0; i < polygons[j].getPath().getLength(); i++) {
		  	    
	  	      bounds.extend(polygons[j].getPath().getAt(i));
	  	      
	  	    }
	  } 
	  
	  map.fitBounds(bounds); */
	  
		}
	 }


     
    
     if(regioncheck==2){
         
      	if ('${listRegionBorder}' != "addNew") {
    	 	boqArrayRegion = ${listRegionBorder};
			regionToDraw = Object.assign(regionToDraw, boqArrayRegion);
		 }
      }
     
     if(showRegions==1){
         
		if(Object.keys(regionToDraw).length != 0){
			
	  		for (i = 0;i<Object.keys(regionToDraw).length;i++){
		
			RegionSet.length=0;
		  	RegionCoordinates=regionToDraw[Object.keys(regionToDraw)[i]];
		  	RegionPrevBorders = RegionCoordinates;
			  for (j = 0;j<regionToDraw[Object.keys(regionToDraw)[i]].length;j++){
				  
				  RegionSet.push({
					  
				      lat:parseFloat(RegionCoordinates[j].lat),
				      lng: parseFloat(RegionCoordinates[j].lng)
				    });
				  }
			  regionPolygon = new google.maps.Polygon({
	   		  	    path: RegionSet,
	   		  	    geodesic: false,
	   		  	    strokeColor: '#00ff00',
	   		  	    strokeOpacity: 1.0,
	   		  	    fillOpacity: 0.01,
	   		  	    ID:Object.keys(regionToDraw)[i],
	   		  	    strokeWeight:5,
				    clickable: false,
				    Editable: false,
	   		  	    map: map
	   		  	  });

   		  	regionPolygon.metadata = { id: Object.keys(regionToDraw)[i] };
  	     	//regionPolygons=[];
  	     	regionPolygons[Object.keys(regionToDraw)[i]] = regionPolygon;
  	     	regionPolygons.push(regionPolygon);

	  }	 
	  	  
	  /*for (var j = 0; j < polygons.length; j++) {
		  
	  	    for (var i = 0; i < polygons[j].getPath().getLength(); i++) {
		  	    
	  	    	bounds.extend(polygons[j].getPath().getAt(i));
	  	    	
	  	    }
	   } 
	   
	  map.fitBounds(bounds);*/
	}	
 }

	google.maps.event.addListenerOnce(map,'bounds_changed',function () {
		
		  map.setCenter(cntr);
		  map.setZoom(6);
		  
	})

	$("#legendDiv").show();
} // End of initMap
		 
$("#SelectAll").hide();
 
$("#SelectAll").click(function(){
	 $("#AgentCHeckBox").prop("checked", true);
	 $("#AreaCHeckBox").prop("checked", true);
	 $("#ShopsCHeckBox").prop("checked", true);
	 $("#RegionCHeckBox").prop("checked", true);
	 showAgent=1;
	 showArea=1;
	 showRegions=1;
	 showShops=1;
	 $(this).hide();
	 $("#SelectUnselectAll").show();
	 initMap();    
});
 
$("#SelectUnselectAll").click(function(){
	 $(this).hide();
	 $("#SelectAll").show();
	 $("#AgentCHeckBox").prop("checked", false);
	 $("#AreaCHeckBox").prop("checked", false);
	 $("#ShopsCHeckBox").prop("checked", false);
	 $("#RegionCHeckBox").prop("checked", false);
	 showAgent=0;
	 showArea=0;
	 showRegions=0;
	 showShops=0;
	 initMap();           
});

 //legend checkboxes to show or hide (agent, shops, areas, regions)
function ShowHideMarkers(color) {
	if (color === "#0080ff"){
		if($('#AgentCHeckBox').is(':checked')){
			showAgent=1;
			initMap();            
		}else{
			showAgent=0;
			initMap();           
		} 
	}
	if (color === "blue"){

		if($('#AreaCHeckBox').is(':checked')){
			showArea=1;
	 		initMap();
		}else{
			showArea=0;
		 	initMap();           
		 } 
	}
	if (color === "green"){
		if($('#RegionCHeckBox').is(':checked')){
			showRegions=1;
			initMap();           	
		}else{
			showRegions=0;
			initMap();           
		}
	}
	if (color === "red"){
		if($('#ShopsCHeckBox').is(':checked')){
			 showShops=1;
			 initMap();           
		}else{
			 showShops=0;
			 initMap();           
		}
	}
}

////////////////////////////////to validate email account ///////////////////////////
 function validateEmail(emaill) {
   const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
   return re.test(String(emaill).toLowerCase());
 }
 function validate() {
   const $result = $("#result");
   const emaill = $("#emaill").val();
   $result.text("");

   if (validateEmail(emaill)) {
     $result.text ("Email is valid");
     $result.css("color", "green");
   } else {
     $result.text(" Email is not valid!");
     $result.css("color", "red");
   }
   return false;
 }

 $("#emaill").on("input", validate);											   
 ///////////////////////////////// end of validation  //////////////////////////////////
/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
$("#sendEmail").on("click", function () {
console.log("Clicking on SendEmail button that is beside the Save button");
$("#popUpDiv").fadeIn();

});

$("#cancelButton").on("click", function () {
console.log("Helloooo in cancelButton onClick");
$("#email").val('');
$("#password").val('');
$("#emailTo").val('');
$("#ccmail").val('');
$("#subject").val('');
$("#message").val('');
$("#popUpDiv").fadeOut();
});

$("#send").on("click", function () {
console.log("Bilal Helloooo in send onClick");
if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
{
alert("All Fields are required");
}
else{
$("#popUpDiv").fadeOut();
}

});
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 
//////////////////////////////////////changinging status to NOT SAVED when pressing add/delete-row     ////////////////////////////////////////
 
 $("#legendDiv :input").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});

 $(".rightpane :input").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});

 $(".middlepane :input").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});

 $(".leftpane :input").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});
 $("#ShopsTab > tbody").change(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});	
	$("#AreaTab > tbody").change(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});	
 $("#RegionTab > tbody").change(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	


$("#ordstat").click(  function() {

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
})

 $("#Activatewh").click(  function() {	
	   
	var Status=$("#ordstat").val();
	$("#ordstat").val('Activated').trigger('change');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	 
 })
	
 $("#Deactivatewh").click(  function() {	
	   
	var Status=$("#ordstat").val();
	$("#ordstat").val('Deactivated').trigger('change');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	 
 })
	
 $("#Cancelwh").click(  function() {	
	   
	var Status=$("#ordstat").val();
	$("#ordstat").val('Cancelled').trigger('change');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	 
 })
 		
 $("#captureSpeed").click(function(){

	if(captureSpeed.checked==true){
		siteEngineer.checked=false;
		document.getElementById("runningInterval").disabled = true;
		captureCoverage.checked=false;
		document.getElementById("coverageRunningInterval").disabled = true;
	}

	 });

$("#siteEngineer").click(function(){

	if(siteEngineer.checked==true){
		captureSpeed.checked=false;
		document.getElementById("runningInterval").disabled = false;
		captureCoverage.checked=false;
		document.getElementById("coverageRunningInterval").disabled = true;
	}else{
		document.getElementById("runningInterval").disabled = true;


		}

	 });

$("#captureCoverage").click(function(){

	if(captureCoverage.checked==true){
		captureSpeed.checked=false;
		siteEngineer.checked=false;
		document.getElementById("coverageRunningInterval").disabled = false;
		document.getElementById("runningInterval").disabled = true;
		
	}else{
		document.getElementById("coverageRunningInterval").disabled = true;


		}

	 });
 
// SEND PIN VIA SMS
$("#SMS").click(  function() {	
	var Status=$("#ordstat").val();
	if (Status == "Activated") {
		$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/ALMSMSCRUN",
				dataType : "json",
				data : {
					"MSISDN":$("#MSISDN").val(),
					"PIN" : $("#PIN").val(),
				},
				success : function(data) {
				console.log(data);	
				},
				error: function (error) {
					//console.log(error.responseText);
					if (error.responseText=='Success') {
						console.log('SMS sent successfully'); 
						$("#smsresult").text('SMS sent successfully');
					}else {console.log('Failed');
					$("#smsresult").text('SMS Failed');
					}
					
				}
			});

			
     } else {
    	 console.log("Not activated");	
    	 $("#smsresult").text('Can not send SMS for Not Activated agent');
     }
	 
	document.getElementById("sendPinStatus").style.display = 'none';
	document.getElementById("Dropdown").style.display = 'none';
	

 })
 
 ///Agent TRACING TAB
 ///AGENT TRACING TAB
///set default date in start and end date 
//Set the default Date
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

//get all colmns count per row
function count(array){
    var c = 0;
    for(i in array) // in returns key, not object
        if(array[i] != undefined)
            c++;
    return c;
}
var downloadArray=[];
var uploadArray=[];
var coverageArray=[];
var startTracingLng=0;
var startTracingLat=0;
var endTracingLng=0;
var endTracingLat=0;
var s_tracingDate;
var e_tracingDate;
var LatLongArray=[];
var tracelength;
var agentTrace;
var tracecount;

if('${coverage_GISquey}' != "addNew"){
	  coverageArray=${coverage_GISquey};
	  }

if('${download_GISquey}' != "addNew"){
	downloadArray=${download_GISquey};
	  }

if('${upload_GISquey}' != "addNew"){
	uploadArray=${upload_GISquey};
	  }

var action = "show";
$("#custom-tabs-one-trace-tab").click(function(){
	LatLongArray=[];

	if( '${agentTracingList}' != "addNew"){
		 agentTrace = ${agentTracingList};
		 tracecount= count(agentTrace[0]);
		
		 tracelength=agentTrace.length;
		for(i=0;i<tracelength;i++){	
				
			for(j=2;j<4;j++){
				
				startTracingLng=agentTrace[0][3];
				startTracingLat=agentTrace[0][2];
				endTracingLng=agentTrace[tracelength-1][3];
				endTracingLat=agentTrace[tracelength-1][2];

		
				
				LatLongArray.push({	
				      lat:parseFloat(agentTrace[i][2]),
				      lng: parseFloat(agentTrace[i][3])
				      
				    });

				}

			}

		///fill the grid table
		almgrid = new AlmgridTable({
	         tableId: "gridTable",
	         dataArray: agentTrace,
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
		}

	initMap1();
});
var zooming=8;
function initMap1() {
	// Define the Center
	console.log(startTracingLat);
	console.log(endTracingLng);
	
	if(parseFloat(startTracingLat)==0 && parseFloat(endTracingLng)==0 ){
		startTracingLat=0.300;
		endTracingLng=37.761;
//		zooming=6;
		}
	var Nairobi=new google.maps.LatLng(startTracingLat,	endTracingLng);
	infowindow = new google.maps.InfoWindow();
	
/*   map1 = new google.maps.Map(document.getElementById("mapContainer1"), {
    center:Nairobi ,
    zoom:6
    
  }); */

  document.getElementById("mapContainer1").innerHTML ="";

	 map1 = new google.maps.Map(document.getElementById("mapContainer1"), {

		 mapTypeControl: false,
		 center:Nairobi ,
		 zoom: 6,
		 mapTypeId: "terrain",
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

	
		
	    
	 if(LatLongArray.length >0){
	 const agntTrack= new google.maps.Polyline({
	  	 
	  	    path:LatLongArray,
	  	    geodesic: false,
	  	    strokeColor: '#06038D',
	  	    strokeOpacity: 0.5,
	  	    fillOpacity: 0.1,
	  	    strokeWeight: 2,
	  	    clickable: false,
		  	Editable: false,
	  	    map: map1
	  	  });
	 
	 	agntTrack.setMap(map1);
		var startingPosition = new google.maps.LatLng(startTracingLat, startTracingLng);
		var endingPosition = new google.maps.LatLng(endTracingLat, endTracingLng);

		var startData="<div><b style='font-size:13px;'><u>Latitude: </u></b>"+startTracingLat+ "</br><b style='font-size:13px;'><u>Longtitude:</u> </b>"+startTracingLng+"</div>";			
		var endData="<div><b style='font-size:13px;'><u>Latitude: </u></b>"+endTracingLat+ "</br><b style='font-size:13px;'><u>Longtitude:</u> </b>"+endTracingLng+"</div>";			

		const startingMarker = new google.maps.Marker({
  		position: startingPosition,
 		 map: map1,
 		 data:startData,
 		  label: {
 			    color: 'red',
 			    fontWeight: 'bold',
 			    text: 'Start',
 			  },
  		icon: {
  			 url: "${pageContext.request.contextPath}/resources/markers/icons8-marker-s-50.png",
  			 labelOrigin: new google.maps.Point(22, 59),
  		},
		});

		google.maps.event.addListener(startingMarker, "click", function (e) {
	     	infowindow.setContent(this.data); 
        	infowindow.open(map,this);
        	});
		startingMarker.setMap(map1);

		const finishingMarker = new google.maps.Marker({
	  		position: endingPosition,
	 		 map: map1,
	 		data:endData,
	 		label: {
 			    color: 'red',
 			    fontWeight: 'bold',
 			    text: 'End',
 			  },
  		icon: {
  			 url: "${pageContext.request.contextPath}/resources/markers/icons8-marker-e-50.png",
  			 labelOrigin: new google.maps.Point(22, 55),
  		},
			});


		google.maps.event.addListener(finishingMarker, "click", function (e) {
	     	infowindow.setContent(this.data); 
        	infowindow.open(map,this);
        	});
			
		finishingMarker.setMap(map1);
	 }

	
		 if(coverageArray.length>0){
				AddSelectedSignalColor(coverageArray,map1);
			}
		 

	
		 if(uploadArray.length>0){
			 AddSelectedUpSpeedColor(uploadArray,map1);
			}
		 

	 
		 if(downloadArray.length>0){
			 AddSelectedDownSpeedColor(downloadArray,map1);
			}


			 
}

$("#ShowHideSequences").click(function() {
 	console.log("woorrkkkkkkkkkkkkkkkkkiiiiiiiiinnnnnnnnngggggggg");
 });
 
$("#generate").click(function(){

	  var startDate = document.getElementById("startdate").value;
	  var endDate = document.getElementById("enddate").value;

	$("#gridTable").remove();
	$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header"><th>Start Date <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>End Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Latitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Longitude<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i '
			+' class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Signal Strength<li class="filter-dropdown dropdown">'
			+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
			+'<th>Speed Download<li class="filter-dropdown dropdown">'
			+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
			+'</ul></li></th><th>Speed Upload<li class="filter-dropdown dropdown">'
			+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
			+'</ul></li></th>'
			+'</ul></li></th><tr><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th></tr></thead><tbody></tbody></table>');


	$.ajax({
		type : "GET",
		contentType: "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/GenerateAgentTracingReport",
		
		data : {
			"startDate":$("#startdate").val(),
			"endDate":$("#enddate").val(),
			"agentNumber":$("#agentMSISDN").val(),
			

	},
	dataType : "json",
	success : function(data) {
			console.log(data.generateAgentTracking);
			if(data.generateAgentTracking.length==0){

				alert("No Data found between this two dates");
			}
			else{
			if(data.generateAgentTracking!=[]  || data.generateAgentTracking !=null ){
				
				 almgrid = new AlmgridTable({
			         tableId: "gridTable",
			         dataArray: data.generateAgentTracking,
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

					LatLongArray=[];

					
					agentTrace = data.generateAgentTracking;
					tracecount= count(agentTrace[0]);
					tracelength=agentTrace.length;
					
						for(i=0;i<tracelength;i++){	
								
							for(j=0;j<tracecount;j++){
								
								startTracingLng=agentTrace[0][3];
								startTracingLat=agentTrace[0][2];
								endTracingLng=agentTrace[tracelength-1][3];
								endTracingLat=agentTrace[tracelength-1][2];

						
								
								LatLongArray.push({	
								      lat:parseFloat(agentTrace[i][2]),
								      lng: parseFloat(agentTrace[i][3])
								      
								    });

								}
						}

						coverageArray=data.CoverageReportGIS;
						console.log(coverageArray)
						downloadArray=data.SpeedDownReportGIS;
						uploadArray=data.SpeedUpReportGIS;


						initMap1();

							
		          

         		
         	
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

 //function to get selected rows for save or delete	
 $("#deleteButton").click(  function() {
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/AgentDelete",
			dataType : "json",
			data : {
				"agentID" :$("#agentID").val()
			},
			success : function(data) {
				
			},
			error : function(error) {
				
				location.reload();
		    	location.replace("${pageContext.request.contextPath}/AgentListView");
			}
		});
 })

//delete shops from shops boq table
var slctDelShops = [];
function deleterowsShops(){
	slctDelShops = [];
	var agentShopID;
	$("#ShopsTab > tbody").find('input[name="chekbox_rowShops"]').each(function(){
		if($(this).is(":checked")){
			var ID = $(this).parent().parent().children('td[name="shopsId"]').children('input').val();
			agentShopID = $(this).parent().parent().children('td[name="agentShopsId"]').children('input').val();
			if(agentShopID != ""){
				slctDelShops.push(agentShopID);
			}
			if(ID != ""){
				shopMarkers[ID].setMap(null);
			}
			$(this).parents("tr").remove();	
		}
		 
     });
	if (slctDelShops.length == 0 &&  agentShopID != "") {
		alert(' Select Row(s) to Delete');
		return false;
	}	 
}//end of delete shops from boq table


//delete area from area boq table
var slctDelArea = [];
function deleterowsArea(){
	slctDelArea = [];
	
	$("#AreaTab > tbody").find('input[name="chekbox_rowArea"]').each(function(){
		if($(this).is(":checked")){
			var agentAreaID = $(this).parent().parent().children('td[name="agentAreaId"]').children('input').val();
			var areaId = $(this).parent().parent().children('td[name="areaId"]').children('input').val();
			console.log("areaId "+areaId);
			 if(agentAreaID != null){
				 slctDelArea.push(agentAreaID);
			  }
			 if(areaId !=""){
				 areaPolygons[areaId].setMap(null);
				 }
			 $(this).parents("tr").remove();

			
							

		 } 
   });

	if (slctDelArea.length == 0) {
		alert(' Select Row(s) to Delete');
		return false;
	}	
 	
}//////end of deleterowsArea function////


//delete region from boq table
var slctDelRegion = [];
function deleterowsRegion(){
	slctDelRegion = [];
	$("#RegionTab > tbody").find('input[name="chekbox_rowRegion"]').each(function(){
		if($(this).is(":checked")){
			var ID = $(this).parent().parent().children('td[name="agentRegionId"]').children('input').val();
			var regionId=$(this).parent().parent().children('td[name="regionId"]').children('input').val();
			if(ID != null){
				slctDelRegion.push(ID);
			}
			if(regionId != ""){
				regionPolygons[regionId].setMap(null);
			}	
			$(this).parents("tr").remove();

			
		} 
	});
	if (slctDelRegion.length == 0) {
		alert(' Select Row(s) to Delete');
	    return false;
	}	
	//initMap();           
}//////end of deleterowsRegion function////

 //to be deleted
 /*function SetIndexRowLFP(obj)
	{
		var row_index = $(obj).parent().parent().index();
		$("#RowIndexLFP").val(row_index);}*/
/////////////// SetIndexRowLFP///////////////////////////////////////////		
 
 
// to be deleted
//if ('${ListPRqItem}' == "addNew") {
if ($('#agentID').val() == '') {	
	 $("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});
}

	

$(document).ready(function () {
	if(siteEngineer.checked==true){document.getElementById("runningInterval").disabled = false;}
	$( "#legendSpeedCoverage" ).draggable(); 
	if(captureCoverage.checked==true){document.getElementById("coverageRunningInterval").disabled = false;}
	
	if('${SelectedIndex}' != "addNew"){
		var SelectedIndex = ${SelectedIndex};
		if('${AgentsCount}' != "addNew"){
			var AgentsCount = ${AgentsCount};
			if(($("#agentID").val()) != "" && ($("#agentID").val()) != null){
				if(SelectedIndex === AgentsCount){
					document.getElementById("btnLast").style.opacity = 0.5;
	        		$("#btnLast").hasClass("disabled");
	        		document.getElementById("btnLast").style.pointerEvents = "none";
	        		document.getElementById("btnNexta").style.opacity = 0.5;
	        		document.getElementById("btnNexta").style.pointerEvents = "none";
					$("#btnNexta").hasClass("disabled");
				}else{
					if(!$("#btnNexta").hasClass("disabled")){
						$("#btnNext").click(function(){
							var param ="${pageContext.request.contextPath}/AgentFormView?agentID="+$("#agentID").val()+"&NavAction=1";
							window.location.href =param;
						});
					}
					if(!$("#btnLst").hasClass("disabled")){
	        			$("#btnLst").click(function(){
	        				var param ="${pageContext.request.contextPath}/AgentFormView?agentID="+$("#agentID").val()+"&NavAction=4";
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
							var param ="${pageContext.request.contextPath}/AgentFormView?agentID="+$("#agentID").val()+"&NavAction=0";
							 window.location.href =param;
						 });
					}
					$("#btnFrst").click(function(){
	        			if(!$("#btnFrst").hasClass("disabled")){
							var param ="${pageContext.request.contextPath}/AgentFormView?agentID="+$("#agentID").val()+"&NavAction=3";
	        				window.location.href =param;		
	        			}
	        		});

				}
				
			}
		}
	}//end of if('${SelectedIndex}' != "addNew")
	

	$("#label-1").text((SelectedIndex)+"/"+AgentsCount);
	
	$("#selectnav").autocomplete({
		    		source: function(request, response) {
		    			     $.ajax({
		    		                 type: "GET",
		    		                 contentType: "application/json; charset=utf-8",
		    		                 url: '${pageContext.request.contextPath}/GetAllAgents',
		    		                 data: {
		    								"agentId" : $("#selectnav").val(),
		    						 },
		    		                 dataType: "json",
		    		                 success: function (data) {
		    		                     if (data != null) {
		    		                         response(data.ListAgent);
		    		                     }
		    		                 },
		    		                 error: function(result) {
		    		                     alert("Error");
		    		                 }
		    		             });
		    		    }, minLength:0, maxShowItems: 40, scroll:true,
		    			select: function(event, ui) {
		    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
		    						var param ="${pageContext.request.contextPath}/AgentFormView?agentID="+(ui.item[0])+"&NavAction=2";
		    						window.location.href =param;
		           					return false;
		           				}
		           		}).autocomplete("instance")._renderItem = function(ul, item) {
		           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
		           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		           	                 item[1] + '</span><br><span class="desc">' +
		           	              	 item[2] + '</span><br><span class="desc">' +
		           	                 item[0] + '</span></div></li>').appendTo(ul);
		           			};
		           					$("#selectnav").focus(function(){
		           		   	        	if (this.value == ""){
		           		   	            	$(this).autocomplete("search");
		           		   	        	}						
		           					});   //// ENd of Autocomplete for select NAV
		    	
	
	var action;
	$("#custom-tabs-one-sim-tab").click(function(){
		action = "show";
		var agentID = document.getElementById("agentID").value;
		$.ajax({
	        type: "GET",
	        contentType: "application/json; charset=utf-8",
	        url: '${pageContext.request.contextPath}/GetSimCardsOnSite',
	        dataType : "json",
	        data: {
					"ID" : agentID,		
			 },
	        dataType: "json",
	        success: function (data) {
	            if (data.ListSimCards != "Empty") {
	            	$('#simtab > tbody > tr').remove();
	                boqArray = [];
	                boqArray = data.ListSimCards;         	
					var length = boqArray.length;
					
	            	for (i = 0;i<boqArray.length;i++)
	            	{
	            		var itemRow = "<tr>";
	                    itemRow =itemRow + "<td name='MSISDN'><input type='text' style='width:200px;' value='" + boqArray[i][0] +"' readonly class='form-control text-input' ></td>";
	                    itemRow =itemRow + "<td name='serialNumber'><input type='text' style='width:200px;'  value='" + boqArray[i][1] +"' readonly class='form-control text-input' ></td>";
	                    itemRow =itemRow + "<td name='status'><input type='text' value='" + boqArray[i][2]  +"' readonly class='form-control text-input' ></td>";
	                    itemRow =itemRow + "<td name='creationDate'><input type='text' value='" + boqArray[i][3] +"' readonly class='form-control text-input' ></td>";
	                    itemRow =itemRow + "<td name='lastModifiedDate'><input type='text' value='" + boqArray[i][4] +"' readonly class='form-control text-input'></td>";
	                    itemRow =itemRow + "<td name='simID'><input type='text' value='" + boqArray[i][5] +"' readonly class='form-control text-input' ></td>";
	                    itemRow =itemRow + "</tr>";
	                    $("#simtab > tbody").append(itemRow);
	                    
	            	}	
	            	
	            	$("#totalNumberSim").val(length);
	            }

	            },
	            
	        error: function(result) {
		        
	            alert("Error");
	            
	        }
		        
	    });

	});//end of sim cards tab



////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
$("#email").autocomplete({
	
source: function(request, response) {

	var password=$("#password").val();
	$.ajax({
      type: "GET",
      contentType: "application/json; charset=utf-8",
      url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
      data: {
				"email" : request.term,
		 },
      dataType: "json",
      success: function (data) {
          if (data != null) {
              response(data.ListItemEmailAccounts);

          }
      },
      error: function(result) {
          alert("Error");
      }
  });
 
}, minLength:0, maxShowItems: 40, scroll:true,

select: function(event, ui) {
	 
  this.value = (ui.item ? ui.item[0]  : '');
  password.value = ui.item[1];
  
  return false;
 
}

}).autocomplete("instance")._renderItem = function(ul, item) {
	
return $("<li class='each'>").append("<div class='acItem'><span class='name' style='font-weight:bold'>" + item[0] + "</span></div>").appendTo(ul);

};


	$("#email").focus(function(){
    	if (this.value == ""){
        	
        	$(this).autocomplete("search");
        	
    	}					
    		
	});
	
	$("#password").focus(function(){
		
    	if (this.value == ""){
        	
        	$(this).autocomplete("search");
        	
    	}				
    			
	});

//////////////////////////////////////////////////////////////////////////////		  	
			 
 $("input").change(function() {
	 
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		
  });
	


//get select row shops
function getselectedrowsShops () {
		dictShops = [];
		slctDel = [];
		var dictObjShops = {};
		 $("#ShopsTab > tbody").find('input[name="chekbox_rowShops"]').each(function(){
			if($(this).is(":checked")){
				
				//to be deleted
				//if(flagFrom === "delete"){
						
				//		slctDel.push($(this).parent().parent().children('td[name="agentShopsId"]').children('input').val());
						
				//}else{
				   // to validate that itemcode not empty in table
				/* if(Errorflag != '2'){
					   
				   }*/
				 if($(this).parent().parent().children('td[name="Shopsname"]').children('input').val() != ""){	   
				  	dictObjShops.agentShopsId  = $(this).parent().parent().children('td[name="agentShopsId"]').children('input').val();
				  	dictObjShops.shopsId  = $(this).parent().parent().children('td[name="shopsId"]').children('input').val();
				  	dictObjShops.Shopsname  = $(this).parent().parent().children('td[name="Shopsname"]').children('input').val();
				  	dictObjShops.Longtitude  = $(this).parent().parent().children('td[name="Longtitude"]').children('input').val();
				  	dictObjShops.Latitude  = $(this).parent().parent().children('td[name="Latitude"]').children('input').val();
				  	dictObjShops.createDate  = $(this).parent().parent().children('td[name="createDate"]').children('input').val();
				  	dictObjShops.lastModifiedDate  = $(this).parent().parent().children('td[name="lastModifiedDate"]').children('input').val();
					}

				//}
					dictShops.push(dictObjShops);
					dictObjShops = {};
							
                } 							
	});
}///////end of get selected rowshops//

var dictArea =[];
function getselectedrowsArea () {
	dictArea = [];
	var dictObjArea = {};
	$("#AreaTab > tbody").find('input[name="chekbox_rowArea"]').each(function(){
		 if($(this).parent().parent().children('td[name="Areaname"]').children('input').val() != ""){
			dictObjArea.agentAreaId  = $(this).parent().parent().children('td[name="agentAreaId"]').children('input').val();
			dictObjArea.areaId  = $(this).parent().parent().children('td[name="areaId"]').children('input').val();
			dictObjArea.Areaname  = $(this).parent().parent().children('td[name="Areaname"]').children('input').val();
			dictObjArea.areaLong  = $(this).parent().parent().children('td[name="areaLong"]').children('input').val();
			dictObjArea.areaLat  = $(this).parent().parent().children('td[name="areaLat"]').children('input').val();
			dictArea.push(dictObjArea);
			dictObjArea = {};	

	 		}
	});
}/////end of getselectedrowarea///

var dictRegion =[];
function getselectedrowsRegion () {
			
		dictRegion = [];
		 $("#RegionTab > tbody").find('input[name="chekbox_rowRegion"]').each(function(){
			
			  if($(this).parent().parent().children('td[name="Regionname"]').children('input').val() != ""){
			 	dictRegion.push({
				 	"agentRegionId" : $(this).parent().parent().children('td[name="agentRegionId"]').children('input').val(),
				    "regionId" : $(this).parent().parent().children('td[name="regionId"]').children('input').val(),
				    "Regionname" : $(this).parent().parent().children('td[name="Regionname"]').children('input').val(),
					"Regioncode" :  $(this).parent().parent().children('td[name="Regioncode"]').children('input').val(),
					"RegionLong" :  $(this).parent().parent().children('td[name="RegionLong"]').children('input').val(),
					"RegionLat" :  $(this).parent().parent().children('td[name="RegionLat"]').children('input').val(),
				});	
			   }			 					
		});
}/////end of getselectedrowregion///

function selectAllShops () {

		if ($(this).hasClass('allChecked')) {
				$('input[name="chekbox_rowShops"]', '#ShopsTab').prop('checked', false);
			} else {
				$('input[name="chekbox_rowShops"]', '#ShopsTab').prop('checked', true);
				}
				$(this).toggleClass('allChecked');
	}

 function selectAllRegion () {

		if ($(this).hasClass('allChecked')) {
				$('input[name="chekbox_rowRegion"]', '#RegionTab').prop('checked', false);
			} else {
				$('input[name="chekbox_rowRegion"]', '#RegionTab').prop('checked', true);
				}
				$(this).toggleClass('allChecked');
		

	}

////end of selectAllRegion//								  
 $('body').on('click', '#selectAllShops', function  () {

		if ($(this).hasClass('allChecked')) {
				$('input[name="chekbox_rowShops"]', '#ShopsTab').prop('checked', false);
			} else {
				$('input[name="chekbox_rowShops"]', '#ShopsTab').prop('checked', true);
				}
				$(this).toggleClass('allChecked');
		

});
//////////
 $('body').on('click', '#selectAllArea', function  () {

						if ($(this).hasClass('allChecked')) {
								$('input[name="chekbox_rowArea"]', '#AreaTab').prop('checked', false);
							} else {
								$('input[name="chekbox_rowArea"]', '#AreaTab').prop('checked', true);
								}
								$(this).toggleClass('allChecked');
						

 });
	////////////
 $('body').on('click', '#selectAllRegion', function  () {

						if ($(this).hasClass('allChecked')) {
								$('input[name="chekbox_rowRegion"]', '#RegionTab').prop('checked', false);
							} else {
								$('input[name="chekbox_rowRegion"]', '#RegionTab').prop('checked', true);
								}
								$(this).toggleClass('allChecked');
						
 });
	////////////
if ('${listAgentShops}' != "addNew") {
 
	  boqArrayShop = [];
	  boqArrayShop = ${listAgentShops};
	  for (i = 0;i<boqArrayShop.length;i++){
		  
			var creationDate = moment(boqArrayShop[i][5]).format('L') +" "+ moment(boqArrayShop[i][5]).format('LT'); // 04/26/2021
			var lastModifiedDate = moment(boqArrayShop[i][6]).format('L') +" "+ moment(boqArrayShop[i][6]).format('LT'); // 04/26/2021

		   		var itemActionRow = "<tr>";
		   		itemActionRow= itemActionRow +"<td style='text-align:center;'><input type='checkbox' name='chekbox_rowShops' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopShops(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   		itemActionRow =itemActionRow + "<td name='shopsId' style='width:100px' hidden><input name='shopsId' style='width:150px'  type='text' value='"+boqArrayShop[i][4]+"' style='width:150px;' class='form-control input-text'/> </td>";
		   		itemActionRow =itemActionRow + "<td name='Shopsname' style='width:310px'><input name='Shopsname' style='width:100%;'  type='text' value='"+boqArrayShop[i][1]+"' style='width:150px;' class='form-control input-text'/> </td>";
		   		itemActionRow =itemActionRow + "<td name='Longtitude' hidden style='width:100px'><input name='Longtitude' style='width:150px'  type='text' value='"+boqArrayShop[i][2]+"' style='width:100px;' class='form-control input-text'/> </td>";
		   		itemActionRow =itemActionRow + "<td name='Latitude' hidden style='width:100px'><input name='Latitude' style='width:150px'  type='text' value='"+boqArrayShop[i][3]+"' style='width:100px;' class='form-control input-text'/> </td>";
		   		itemActionRow =itemActionRow + "<td name='createDate' hidden style='width:200px'><input name='createDate'   type='text' value='" +creationDate+ "' style='width:200px;' class='form-control input-text' readonly/></td>";
		   		itemActionRow =itemActionRow + "<td name='lastModifiedDate' hidden style='width:200px'><input name='lastModifiedDate'   type='text' value='" +lastModifiedDate+ "' style='width:200px;' class='form-control input-text' readonly/></td>";
		   		itemActionRow =itemActionRow + "<td name='agentShopsId' hidden><input name='agentShopsId' type='text'  value='" +boqArrayShop[i][0]+ "' style='width:200px;' class='form-control input-text' readonly/></td>";
   		   	 
				$("#ShopsTab > tbody").append(itemActionRow);
	  }
	  
					   
}
/////////////// end of listagentshops addnew function//////

if ('${listAgentArea}' != "addNew") {
	
  boqArrayAreaTable = [];
  boqArrayAreaTable = ${listAgentArea};
  for (i = 0;i<boqArrayAreaTable.length;i++){

	   		var itemActionRow = "<tr>";
	   		itemActionRow= itemActionRow + "<td><input type='checkbox' name='chekbox_rowArea' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopArea(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	   		itemActionRow =itemActionRow + "<td name='areaId' style='width:200px' hidden><input name='areaId'  style='width:150px;'  type='text' value='"+boqArrayAreaTable[i].areaId+"' style='width:200px;' class='form-control input-text'/> </td>";
	   		itemActionRow =itemActionRow + "<td name='Areaname' style='width:310px'><input name='Areaname' style='100%;' type='text' value='"+boqArrayAreaTable[i].areaname+"' class='form-control input-text' /> </td>";
	   		itemActionRow =itemActionRow + "<td name='areaLong' style='width:310px' hidden><input name='areaLong' style='100%;' type='text' value='"+boqArrayAreaTable[i].arealong+"' class='form-control input-text'/> </td>";
	   		itemActionRow =itemActionRow + "<td name='areaLat' style='width:310px' hidden><input name='areaLat' style='100%;' type='text' value='"+boqArrayAreaTable[i].arealat+"' class='form-control input-text'/> </td>";
	   		itemActionRow =itemActionRow + "<td name='agentAreaId' hidden style='width:200px' hidden><input name='agentAreaId'hidden style='width:200px'  type='text' value='"+boqArrayAreaTable[i].agentAreaId+"' style='width:200px;' class='form-control input-text'/> </td>";
	   		
	   		itemActionRow =itemActionRow + "</tr>";
			$("#AreaTab > tbody").append(itemActionRow);
  	}
		   
}  

var boqArrayRegionTable=[];
/////////////// end of listagentarea addnew function//////		
 if ('${listAgentRegion}' != "addNew") {
	
	 boqArrayRegionTable = [];
	 boqArrayRegionTable = ${listAgentRegion};
	
	  for (i = 0;i<boqArrayRegionTable.length;i++){
	
		   		
		   		var itemActionRow = "<tr>";
		   		itemActionRow= itemActionRow + "<td><input type='checkbox' name='chekbox_rowRegion' style='margin-top:12px;'></span><button type = 'button' href='#' name='popUpMenu' onclick='openPopRegion(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   		itemActionRow =itemActionRow + "<td name='regionId' style='width:200px' hidden><input name='regionId'  style='width:150px;'  type='text' value='"+boqArrayRegionTable[i].regionId+"' style='width:200px;' class='form-control input-text'/> </td>";
		   		itemActionRow =itemActionRow + "<td name='Regionname' style='width:310px'><input name='Regionname'  style='width:100%;'  type='text' value='"+boqArrayRegionTable[i].regionname+"' style='width:200px;' class='form-control input-text' /> </td>";
		   		itemActionRow =itemActionRow + "<td name='Regioncode' style='width:200px' hidden><input name='Regioncode'  style='width:150px;'  type='text' value='"+boqArrayRegionTable[i].regioncode+"' style='width:200px;' class='form-control input-text'/> </td>";
		   		itemActionRow =itemActionRow + "<td name='agentRegionId' hidden style='width:200px' hidden><input name='agentRegionId' style='width:200px'  type='text' value='"+boqArrayRegionTable[i].agentRegionId+"' style='width:200px;' class='form-control input-text'/></td>";
		   		itemActionRow =itemActionRow + "<td name='RegionLong'  style='width:200px' hidden><input name='RegionLong' style='width:200px'  type='text' value='"+boqArrayRegionTable[i].regionlong+"' style='width:200px;' class='form-control input-text'/></td>";
		   		itemActionRow =itemActionRow + "<td name='RegionLat'  style='width:200px' hidden><input name='RegionLat' style='width:200px'  type='text' value='"+boqArrayRegionTable[i].regionlat+"' style='width:200px;' class='form-control input-text'/></td>";
	
		   		itemActionRow =itemActionRow + "</tr>";
	  		   	 
				$("#RegionTab > tbody").append(itemActionRow);
				
	  }
	  
 }   
 
 $("#fname").keyup(function(e) {
	 
	 document.getElementById("fullName").value= document.getElementById("fname").value+" "+document.getElementById("lname").value;
	 document.getElementById("dName").value= document.getElementById("fname").value+" "+document.getElementById("lname").value;

 });

 $("#lname").keyup(function(e) {
	 
	 document.getElementById("fullName").value= document.getElementById("fname").value+" "+document.getElementById("lname").value;
	 document.getElementById("dName").value= document.getElementById("fname").value+" "+document.getElementById("lname").value;

  });
/////////////// end of listagentregion addnew function//////		
 $("#saveButton").click(  function() {
	 
	 PopupDynamicDeleteShops();
	 PopupDynamicDeleteArea ();
	 PopupDynamicDeleteRegion ();
	 selectAllShops();
	 getselectedrowsShops();
	 getselectedrowsArea();
     getselectedrowsRegion();
     			 
	
	var siteengineer;
	var superAgent;
	var capturecoverage;

	//checking for site engineer permission
	if (siteEngineer.checked==true && ($.isNumeric($("#runningInterval").val()))== false) {
		alert('Running interval must be numeric');
		return false;}
	
		 
	if(siteEngineer.checked==true){
		siteengineer='1';
		}else{

			siteengineer='0';
			}
	if(siteengineer == '1' && $("#runningInterval").val()=="0"){
		alert('Running interval must not be zero');
		 return false;

	}

	//checking for capture coverage permission
	   if (captureCoverage.checked==true && ($.isNumeric($("#coverageRunningInterval").val()))== false) {
			 alert('Running interval must be numeric');
			 return false;}

		 
	if(captureCoverage.checked==true){
		capturecoverage='1';
		}else{

			capturecoverage='0';
			}
	if(capturecoverage == '1' && $("#coverageRunningInterval").val()=="0"){
		alert('Running interval must not be zero');
		 return false;

	}

	//checking for super agent permission
	if(superAgentPermission.checked==true){
		superAgent="1";
	}else{
		superAgent="0";
		}

	var capturespeed;

	if(captureSpeed.checked==true){
		capturespeed='1';
	}else{
		capturespeed='0';
		}

	console.log("capturespeed === "+capturespeed);
		
		$.ajax({

			type : "GET",
			url : "${pageContext.request.contextPath}/AgentFormSave",
			dataType : "json",
			data : {
				
				"agentID" : $("#agentID").val(),
				"dName":$("#dName").val(),
				"lname" : $("#lname").val(),
				"dictParameterArea":dictArea,
				"dictParameterRegion":dictRegion,		  
				"dictParameter":dictShops,
				"fname" : $("#fname").val(),
			    "emaill" : $("#emaill").val(),
			    "address" : $("#address").val(),
			    "MSISDN":$("#MSISDN").val(),
			    "creationDate":$("#creationDate").val(),
				"lastModifiedDate":$("#lastModifiedDate").val(),
				"status": $("#ordstat").val(),
				"slctDelShops" : slctDelShops,
				"slctDelArea" : slctDelArea,
				"slctDelRegion" : slctDelRegion,
				"email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(),
				"PIN" : $("#PIN").val(),
				"fullName" : $("#fullName").val(),
				"agentLat" : $("#agentLat").val(),
				"agentLng" : $("#agentLng").val(),
				"superAgentPermission":superAgent,
				"captureSpeed":capturespeed,
				"siteEngineer":siteengineer,
				"runningInterval":$("#runningInterval").val(),
				"captureCoverage":capturecoverage,
				"coverageRunningInterval":$("#coverageRunningInterval").val(),
				
			},
			success : function(data) {
			
				$('.dot').css({"background-color" : "orange"});					
				$("#agentID").val(data.agentID);
				
				var param ="${pageContext.request.contextPath}/AgentFormView?agentID="+$("#agentID").val()+"&NavAction=2";
				location.replace(param);
			},
			error : function(error) {
				
				console.log("The error is " + error);
			}
		});
		
})

/*function getAllShopsId()
{
	SnArray = [];
	var SnValue = "";
	$("input[name=shopsId]").each(function(){
		if($(this).val() == "")
			SnValue = "empty";
		else
			SnValue = $(this).val();
		SnArray.push(SnValue);
	});
	return SnArray;   
}
			function getAllAreaId()
			{
				AnArray = [];
				var AnValue = "";
				$("input[name=areaId]").each(function(){
					if($(this).val() == "")
						AnValue = "empty";
						else
							AnValue = $(this).val();
								AnArray.push(AnValue);
				});
				return AnArray;   
			}			

			function getAllRegionId()
			{
			RnArray = [];
						var RnValue = "";
						$("input[name=regionId]").each(function(){
							if($(this).val() == "")
								RnValue = "empty";
							else
								RnValue = $(this).val();
							RnArray.push(RnValue);
						});
						return RnArray;   
					}*/
//////////////////////////////end of getall....Id function///////		



//to be deleted
/*function SetIndexRow(obj)
{
	var row_index = $(obj).parent().parent().index();
	$("#RowIndex").val(row_index);
}

function SetIndexRowLFP(obj)
{
	var row_index = $(obj).parent().parent().index();
	$("#RowIndexLFP").val(row_index);
}*/

function getAllShopsName()
{
	SiArray = [];
	var SiValue = "";
	$("input[name=Shopsname]").each(function(){
		if($(this).val() == "")
			SiValue = "empty";
		else
			SiValue = $(this).val();
		SiArray.push(SiValue);
	});
	return SiArray;   
}
function getAllAreaName()
{
	AiArray = [];
	var AiValue = "";
	$("input[name=Areaname]").each(function(){
		if($(this).val() == "")
			AiValue = "empty";
		else
			AiValue = $(this).val();
		AiArray.push(AiValue);
	});
	return AiArray;   
}
function getAllRegionName()
{
	RiArray = [];
	var RiValue = "";
	$("input[name=Regionname]").each(function(){
		if($(this).val() == "")
			RiValue = "empty";
		else
			RiValue = $(this).val();
		RiArray.push(RiValue);
	});
	return RiArray;   
}
function getAllRegioncode()
{
	CiArray = [];
	var CiValue = "";
	$("input[name=Regionname]").each(function(){
		if($(this).val() == "")
			CiValue = "empty";
		else
			CiValue = $(this).val();
		CiArray.push(CiValue);
	});
	return CiArray;   
}
////////////////////	

//to be deleted
/*  $('input[name ="shopsId"]').each(function(i, el) {
		
		$(el).autocomplete({
			 source: function(request, response, event, ui) {
			    	
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		               url: '${pageContext.request.contextPath}/getSelectedShop',
		                data: {
								"Shops" : request.term,
								"agentID" :  $("#agentID").val(),
								 
						 },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.selectedShop);
		                        console.log(data.selectedShop);
		                    	
		                    }
		                },
		                error: function(result) {
		                    alert("Error");
		                }
		            });
		       }, minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {	
		       	
		   	    	var shopid = ui.item[0];
		   	    	var SameShop=0;
		   	    	
					$("#ShopsTab").find('tr').each(function(){

			    	  if(ui.item[1]===$(this).find('input[name ="Shopsname"]').val())	{
					          SameShop =1;

			             alert("this shop Name is already selected in this agent");
			             return false;
						}
				 });
					
					if(SameShop ===0){
						this.value = (ui.item ? ui.item[1]:'');
						$(this).parents("tr").find('input[name ="shopsId"]').val(ui.item[0]);
					    $(this).parents("tr").find('input[name ="Latitude"]').val(ui.item[3]);
					    $(this).parents("tr").find('input[name ="Longtitude"]').val(ui.item[2]);

		                
					    //ListOfShopsName.push(ui.item[1]);
						//ListOfShopsLng.push(ui.item[2]);
						//ListOfShopsLat.push(ui.item[3]);


				                    	 console.log(previousShopId);
				                    	 if(typeof previousShopId=='undefined') {
				                    		 
				                    	 }else {
				                    		 
				                    		 if(shopMarkers.length>0) {
					                    		 if(previousShopId != shopid) {shopMarkers[previousShopId].setMap(null);}
					                    	 } 
				                    	 }
				                    	
				                    	 infowindow = new google.maps.InfoWindow();
				                    	 console.log(shopMarkers);	
					                 			const marker = new google.maps.Marker({
						             				position: new google.maps.LatLng(ui.item[3], ui.item[2]),
						             				map: map,		
						             				ID:shopid,
						             				data: "<div>Shop Name: " +ui.item[1]+"<br>Longitude: "+ui.item[2]+"<br>Latitude: "+ui.item[3]+"</div>",
						             			});

					                 			marker.metadata = { id: shopid };
					                 			shopMarekrs=[];
					        				    shopMarkers[shopid] = marker;
					        				    shopMarkers.push(marker);
					        				    
					                 			google.maps.event.addListener(marker, "click", function (e) {
					            					infowindow.setContent(this.data); 
					        			        	infowindow.open(map,this);

					            				});
					                 		
					                 			console.log(shopMarkers)
				                 			}

						  return false;
		  					
		  				}
		}).autocomplete("instance")._renderItem = function(ul, item) {
            return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                item[0] + "</span><br><span class='desc'>" +
                item[1] +"</span></div>")
            .appendTo(ul);
    	};
	$(this).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
        }
        
	});
	
}); // Ending for item autocomplete in Boq table    			
*/

var previousShopId;



$('input[name ="Shopsname"]').each(function(i, el) {
if($("#agentID").val()=== null || $("#agentID").val()===""){
	
}
		$(el).autocomplete({
			 source: function(request, response, event, ui) {
			    	
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		               url: '${pageContext.request.contextPath}/getSelectedShop',
		                data: {
								"Shops" : request.term,
								"agentID" :  $("#agentID").val(),
								 
						 },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.selectedShop);
		                        console.log(data.selectedShop);
		                    	
		                    }
		                },
		                error: function(result) {
		                    alert("Error");
		                }
		            });
		       }, minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {	
		       	
		   	    	var shopid = ui.item[0];
		   	    	var SameShop=0;
		   	    	
					$("#ShopsTab").find('tr').each(function(){

			    	  if(ui.item[1]===$(this).find('input[name ="Shopsname"]').val())	{
					          SameShop =1;

			             alert("this shop Name is already selected in this agent");
			             return false;
						}
				 });
					
					if(SameShop ===0){
						this.value = (ui.item ? ui.item[1]:'');
						$(this).parents("tr").find('input[name ="shopsId"]').val(ui.item[0]);
					    $(this).parents("tr").find('input[name ="Latitude"]').val(ui.item[3]);
					    $(this).parents("tr").find('input[name ="Longtitude"]').val(ui.item[2]);

						
							
	                  if(previousShopId != shopid) {


		               shopMarkers[previousShopId].setMap(null);
	                    	  
				        
				                    	
				        infowindow = new google.maps.InfoWindow();
				        	
					     const marker = new google.maps.Marker({
						             	position: new google.maps.LatLng(ui.item[3], ui.item[2]),
						             	map: map,		
						             	ID:shopid,
						             	data: "<div>Shop Name: " +ui.item[1]+"<br>Longitude: "+ui.item[2]+"<br>Latitude: "+ui.item[3]+"</div>",
						             	});

					                 	marker.metadata = { id: shopid };
					                 	shopMarekrs=[];
					        			shopMarkers[shopid] = marker;
					        			shopMarkers.push(marker);
					        				    
					                 	google.maps.event.addListener(marker, "click", function (e) {
					            		infowindow.setContent(this.data); 
					        			infowindow.open(map,this);
										});
	                  }
       					}

						  return false;	
		  		}//end of select function
		}).autocomplete("instance")._renderItem = function(ul, item) {
            return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                item[1] + "</span><br><span class='desc'>" +
                item[0]  +"</span></div>")
            .appendTo(ul);
    	};
	$(this).focus(function(){
		
		previousShopId=$(this).parent().parent().children('td[name="shopsId"]').children('input').val();
		if (this.value == ""){
			$(this).autocomplete("search");
        }
	});

	$(this).change(function(){
 		previousShopId=$(this).parents("tr").find('input[name ="shopsId"]').val();
   		if(this.value == "") {
    		if(previousShopId != "") {
    			shopMarkers[previousShopId].setMap(null);
    		}
    		$(this).parents("tr").find('input[name ="shopsId"]').val("");
    		$(this).parents("tr").find('input[name ="Latitude"]').val("");
		    $(this).parents("tr").find('input[name ="Longtitude"]').val("");
    		previousShopId="";
    	}
	});
	}); // Ending for item autocomplete in Boq table    			


  $("#popupShopsId").autocomplete({
	  source: function(request, response, event, ui) {
          $.ajax({
              type: "GET",
              contentType: "application/json; charset=utf-8",
             url: '${pageContext.request.contextPath}/getSelectedShop',
              data: {
						"Shops" : request.term,
						"agentID" :  $("#agentID").val(),
						 
				 },
              dataType: "json",
              success: function (data) {
                  if (data != null) {
                      response(data.selectedShop);
                  	
                  }
              },
              error: function(result) {
                  alert("Error");
              }
          });
     }, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {	
     	
 	    	var shopid = ui.item[0];
 	    	var SameShop=0;
 	    	
			$("#ShopsTab").find('tr').each(function(){

	    	  if(ui.item[1]===$(this).find('input[name ="Shopsname"]').val())	{
			          SameShop =1;

	             alert("this shop Name is already selected in this agent");
	             return false;
				}
		 });
			
			if(SameShop ===0){
								
			  this.value = (ui.item ? ui.item[1]:'');
              document.getElementById("popupShopsName").value= ui.item[1];
              document.getElementById("popupShopsId").value= ui.item[0];
              document.getElementById("popupLogntitude").value= ui.item[2];
              document.getElementById("popupLatitude").value= ui.item[3];

              if(previousShopId != shopid) {
            	  if(previousShopId != "") {shopMarkers[previousShopId].setMap(null);}
	              previousShopId=shopid;
			      infowindow = new google.maps.InfoWindow();
			      const marker = new google.maps.Marker({
					             position: new google.maps.LatLng(ui.item[3], ui.item[2]),
					             map: map,		
					             ID:shopid,
					             data: "<div>Shop Name: " +ui.item[1]+"<br>Longitude: "+ui.item[2]+"<br>Latitude: "+ui.item[3]+"</div>",
					});
	
				   marker.metadata = { id: shopid };
				   shopMarekrs=[];
				   shopMarkers[shopid] = marker;
				   shopMarkers.push(marker);
				        				    
				   google.maps.event.addListener(marker, "click", function (e) {
				            		infowindow.setContent(this.data); 
				        			infowindow.open(map,this);
					});
				                 		
			        }
				}

				  return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
          return $("<li class='each'>")
          .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
              item[0] + "</span><br><span class='desc'>" +
              item[1] +","+item[2] +","+item[3] +"</span></div>")
          .appendTo(ul);
  	};

  	
  	$("#popupShopsId").focus(function(){
  	  	previousShopId= document.getElementById("popupShopsId").value;
		if (this.value == ""){
			$(this).autocomplete("search");
      }
	}); // Ending for item autocomplete in Boq table    			

	$("#popupShopsId").change(function(){
  	  	previousShopId= document.getElementById("popupShopsId").value;
   		if(this.value == "") {
    		if(previousShopId != "") {
    			shopMarkers[previousShopId].setMap(null);
    		}
    		document.getElementById("popupShopsName").value="";
            document.getElementById("popupLogntitude").value= "";
            document.getElementById("popupLatitude").value= "";
    		previousShopId="";
    	}
	});

	$("#popupShopsName").autocomplete({
	    source: function(request, response, event, ui) {
	    	
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
               url: '${pageContext.request.contextPath}/getSelectedShop',
                data: {
						"Shops" : request.term,
						"agentID" :  $("#agentID").val(),
						 
				 },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.selectedShop);
                        console.log(data.selectedShop);
                    	
                    }
                },
                error: function(result) {
                    alert("Error");
                }
            });
       }, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {	
       	
   	    	var shopid = ui.item[0];
   	    	var SameShop=0;
   	    	
			$("#ShopsTab").find('tr').each(function(){

	    	  if(ui.item[1]===$(this).find('input[name ="Shopsname"]').val())	{
			          SameShop =1;

	             alert("this shop Name is already selected in this agent");
	             return false;
				}
		 });
			
			if(SameShop ===0){
	
				this.value = (ui.item ? ui.item[1]:'');
                document.getElementById("popupShopsId").value= ui.item[0];
                document.getElementById("popupLogntitude").value= ui.item[2];
                document.getElementById("popupLatitude").value= ui.item[3];
                if(previousShopId != shopid) {
                	if(previousShopId != "") {shopMarkers[previousShopId].setMap(null);}
                
                previousShopId = shopid;
		        infowindow = new google.maps.InfoWindow();
			    const marker = new google.maps.Marker({
				             	position: new google.maps.LatLng(ui.item[3], ui.item[2]),
				             	map: map,		
				             	ID:shopid,
				  });

			      marker.metadata = { id: shopid };
			      shopMarekrs=[];
			      shopMarkers[shopid] = marker;
			      shopMarkers.push(marker);
			        				    
			       google.maps.event.addListener(marker, "click", function (e) {
			            			infowindow.setContent(this.data); 
			        			    infowindow.open(map,this);
			       });
			     }
           	 }

				  return false;
			}//end of select function
  			}).autocomplete("instance")._renderItem = function(ul, item) {
  	            return $("<li class='each'>")
  	            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
  	                item[1] + "</span><br><span class='desc'>" +
  	                item[0] +","+item[2] +","+item[3] +"</span></div>")
  	            .appendTo(ul);
  	    	};
  	   $("#popupShopsName").focus(function(){
  	    	 previousShopId= document.getElementById("popupShopsId").value;
  	  	    	
  			if (this.value == ""){
  				$(this).autocomplete("search");
  	        }
  		}); // Ending for item autocomplete in Boq table    			

  		$("#popupShopsName").focus(function(){
 	    	previousShopId= document.getElementById("popupShopsId").value;
 	    	if(this.value == "") {
 	    		if(previousShopId != "") {
 	    			shopMarkers[previousShopId].setMap(null);
 	    		}
 	    		document.getElementById("popupShopsId").value="";
 	            document.getElementById("popupLogntitude").value= "";
 	            document.getElementById("popupLatitude").value= "";
 	    		previousShopId="";
 	    	}
 		});
  		//to be deleted
  	    /*	 $('input[name ="areaId"]').each(function(i, el) {
  	  		
  	  		$(el).autocomplete({
		    	    source: function(request, response, event, ui) {
		    	    	 var RowIndex = document.getElementById('RowIndex').value;
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllAreas',
			                 data: {
									"areaId" : request.term,
									 
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.listAreas);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			        }, minLength:0, maxShowItems: 4, scroll:true,
					select: function(event, ui) {			
						this.value = (ui.item ? ui.item[0]:'');
					
						$(this).parents("tr").find('input[name ="Areaname"]').val(ui.item[1]);
						initMap();
						
						return false;
						
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                    item[0] + "</span><br><span class='desc'>" +
	                    item[1] +"</span></div>")
	                .appendTo(ul);
	        	};
	        	$(this).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			});
 			});*/

 	
  	$("#popupareaId").autocomplete({
  	     source:areas,
  	      minLength:0, maxShowItems: 4, scroll:true,
  	  	   select: function(event, ui,request, response) {	
  	  	  	   console.log(ui.item[1]);
  	  	  	   console.log(ui.item[0]);

  	  	  	  	//$("#popupareaId").val(ui.item[0]);
  	  	   		//$("#popupareaName").val(ui.item[1]);
  	  	   		
  	  	   		//document.getElementById("popupareaId").value= ui.item[0];
  	  			document.getElementById("popupareaName").value= ui.item[1];
  	  			
  	  			console.log("previousAreaId == "+previousAreaId);
  	  			if(previousAreaId != ui.item[0]) {
  	  	  			if(previousAreaId != ""){
						if(AreaPrevBorders != "N/A" && AreaPrevBorders != null){
							areaPolygons[previousAreaId].setMap(null);
						}
  	  	  	  		}
  	  				this.value = (ui.item ? ui.item[0]:'');
  	  				previousAreaId = ui.item[0];
  	  				Tabora.length=0;
  	   	    		var splittingCoordinate=[];
  	   	    		var CoordinatesArray=[];
  	   	    		
  	   	    		console.log("result == "+ui.item[2]);
  	   	    		if(ui.item[2] != "N/A"){
  	   	    		AreaPrevBorders = ui.item[2];
  	   	    		splittingCoordinate=ui.item[2].split(":");
  	   	    		for(i=0;i<splittingCoordinate.length;i++) {
  	   	    			Tabora.push({
  	     				      lat:parseFloat(splittingCoordinate[i].split(",")[0]),
  	     				      lng: parseFloat(splittingCoordinate[i].split(",")[1])
  	     				});
  	   	    		}
  	   	     		areaPolygon= new google.maps.Polygon({
  	      		  	    	path: Tabora,
  	  	       		  	    id:ui.item[0],
  	  	       		  	    geodesic: false,
  	  	       		  	    strokeColor: '#06038D',
  	  	       		  	    strokeOpacity: 1.0,
  	  	       		  	    fillOpacity: 0.01,
  	  	       		  	    strokeWeight: 5,
  	  	       		  	    map: map
  	      		  	 	});
  	   	    
  	   	     		areaPolygon.metadata = { id: ui.item[0] };
  	   	     		areaPolygons=[];
  	   	     		areaPolygons[ui.item[0]] = areaPolygon;
  	   	     		areaPolygons.push(areaPolygon);
  	   	    		}else{

							AreaPrevBorders = "N/A";
  	  	   	    		}
  	   	    	
  	  				}
  	  			return false;
  	  				
  	  			}
 				}).autocomplete("instance")._renderItem = function(ul, item) {
 		            return $("<li class='each'>")
 	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
 	                    item[0] + "</span><br><span class='desc'>" +
 	                    item[1] +"</span></div>")
 	                .appendTo(ul);
 	        	};
 	$("#popupareaId").focus(function(){
 	        		previousAreaId=document.getElementById("popupareaId").value;
 				if (this.value == ""){
 					$(this).autocomplete("search");
 		        }
 	});// Ending for item autocomplete in Boq table    			
		 
 	$("#popupareaId").change(function(){
 		
 		if(this.value == "") {
	    		if(previousAreaId != "") {
		    		if(AreaPrevBorders != null && AreaPrevBorders != "N/A"){areaPolygons[previousAreaId].setMap(null);}	
	    		}
	    		document.getElementById("popupareaName").value="";
	    		previousAreaId="";
	    	}else{

	    		previousAreaId=document.getElementById("popupareaId").value;
		    	}
	});

	$("#popupareaName").autocomplete({
		source:areas,
		 minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui,request, response) {	
			 console.log(ui.item[1]);
	  	  	   console.log(ui.item[0]);

	  	  	   $("#popupareaId").val(ui.item[0]);
	  	   		$("#popupareaName").val(ui.item[1]);
	  			//document.getElementById("popupareaName").value= ui.item[1];
	  			//document.getElementById("popupareaId").value= ui.item[0];
	  			console.log("previousAreaId == "+previousAreaId);
	  			if(previousAreaId != ui.item[0]) {
	  	  			if(previousAreaId != ""){
						if(AreaPrevBorders != "N/A" && AreaPrevBorders != null){
							areaPolygons[previousAreaId].setMap(null);
						}
	  	  	  		}
	  				this.value = (ui.item ? ui.item[1]:'');
	  				previousAreaId = ui.item[0];
	  				Tabora.length=0;
	   	    		var splittingCoordinate=[];
	   	    		var CoordinatesArray=[];
	   	    		
	   	    		console.log("result == "+ui.item[2]);
	   	    		if(ui.item[2] != "N/A"){
	   	    			AreaPrevBorders = ui.item[2];
	   	    		splittingCoordinate=ui.item[2].split(":");
	   	    		for(i=0;i<splittingCoordinate.length;i++) {
	   	    			Tabora.push({
	     				      lat:parseFloat(splittingCoordinate[i].split(",")[0]),
	     				      lng: parseFloat(splittingCoordinate[i].split(",")[1])
	     				});
	   	    		}
	   	     		areaPolygon= new google.maps.Polygon({
	      		  	    	path: Tabora,
	  	       		  	    id:ui.item[0],
	  	       		  	    geodesic: false,
	  	       		  	    strokeColor: '#06038D',
	  	       		  	    strokeOpacity: 1.0,
	  	       		  	    fillOpacity: 0.01,
	  	       		  	    strokeWeight: 5,
	  	       		  	    map: map
	      		  	 	});
	   	    
	   	     		areaPolygon.metadata = { id: ui.item[0] };
	   	     		areaPolygons=[];
	   	     		areaPolygons[ui.item[0]] = areaPolygon;
	   	     		areaPolygons.push(areaPolygon);
	   	    		}else{

							AreaPrevBorders = "N/A";
	  	   	    		}
	   	    	
	  				}
	  			return false;
	  				
	  			}
			}).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                    item[1] + "</span><br><span class='desc'>" +
	                    item[0] +"</span></div>")
	                .appendTo(ul);
	        };
	        $("#popupareaName").focus(function(){
				previousAreaId=document.getElementById("popupareaId").value;
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			});

	        $("#popupareaName").change(function(){
				
				if(this.value == "") {
		    		if(previousAreaId != "") {
			    		if(AreaPrevBorders != null && AreaPrevBorders != "N/A"){
			    			areaPolygons[previousAreaId].setMap(null);
				    	}
		    		}
		    		document.getElementById("popupareaId").value="";
		    		previousAreaId="";
		    	}else{
		    		previousAreaId=document.getElementById("popupareaId").value;
			    	}
			});

	        	
$('input[name ="Areaname"]').each(function(i, el) {
	$(el).autocomplete({
  		    source:areas,
  		     minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui,request, response) {	
				$(this).parents("tr").find('input[name ="areaId"]').val(ui.item[0]);
				$(this).parents("tr").find('input[name ="Areaname"]').val(ui.item[1]);
				console.log("previousAreaId == "+previousAreaId);
	  			if(previousAreaId != ui.item[0]) {
	  	  			if(previousAreaId != ""){
						if(AreaPrevBorders != "N/A" && AreaPrevBorders != null){
							areaPolygons[previousAreaId].setMap(null);
						}
	  	  	  		}
	  				this.value = (ui.item ? ui.item[1]:'');
	  				previousAreaId = ui.item[0];
	  				Tabora.length=0;
	   	    		var splittingCoordinate=[];
	   	    		var CoordinatesArray=[];
	   	    		
	   	    		console.log("result == "+ui.item[2]);
	   	    		if(ui.item[2] != "N/A"){
	   	    			AreaPrevBorders = ui.item[2];
	   	    		splittingCoordinate=ui.item[2].split(":");
	   	    		for(i=0;i<splittingCoordinate.length;i++) {
	   	    			Tabora.push({
	     				      lat:parseFloat(splittingCoordinate[i].split(",")[0]),
	     				      lng: parseFloat(splittingCoordinate[i].split(",")[1])
	     				});
	   	    		}
	   	     		areaPolygon= new google.maps.Polygon({
	      		  	    	path: Tabora,
	  	       		  	    id:ui.item[0],
	  	       		  	    geodesic: false,
	  	       		  	    strokeColor: '#06038D',
	  	       		  	    strokeOpacity: 1.0,
	  	       		  	    fillOpacity: 0.01,
	  	       		  	    strokeWeight: 5,
	  	       		  	    map: map
	      		  	 	});
	   	    
	   	     		areaPolygon.metadata = { id: ui.item[0] };
	   	     		areaPolygons=[];
	   	     		areaPolygons[ui.item[0]] = areaPolygon;
	   	     		areaPolygons.push(areaPolygon);
	   	    		}else{

							AreaPrevBorders = "N/A";
	  	   	    		}
	   	    	
	  				}
			return false;
				
			}
			}).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
              .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                  item[1] + "</span><br><span class='desc'>" +
                  item[0] +"</span></div>")
              .appendTo(ul);
	              
      	};
      	$(this).focus(function(){
      		previousAreaId =$(this).parent().parent().children('td[name="areaId"]').children('input').val();
			if (this.value == ""){
				$(this).autocomplete("search");
	        }
		});

		$(this).change(function(){
          	console.log(previousAreaId);
          	console.log(areaPolygons[previousAreaId]);
          	console.log(AreaPrevBorders);
      		if(this.value == "") {
	    		if(previousAreaId != "") {
	    			if(AreaPrevBorders != null && AreaPrevBorders != "N/A"){
		    			areaPolygons[previousAreaId].setMap(null);
			    	}
	    		}
	    		$(this).parents("tr").find('input[name ="areaId"]').val("");
	    		previousAreaId="";
	    	}else{
	    		previousAreaId =$(this).parent().parent().children('td[name="areaId"]').children('input').val();
		    	}
		});
	});
			 // Ending for item autocomplete in Boq table    			
	
	  $("#popupregionId").autocomplete({
		  source:regions,
		  minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {		
			//this.value = (ui.item ? ui.item[0]:'');
			document.getElementById("popupregionId").value= ui.item[0];
			document.getElementById("popupregionName").value= ui.item[1];
	        document.getElementById("popupregionCode").value= ui.item[2];
            if(previousRegionId != ui.item[0]) {
            	if(previousRegionId != ""){
            		if(RegionPrevBorders != "N/A" && RegionPrevBorders != null){
                		regionPolygons[previousRegionId].setMap(null);}
            	}
				previousRegionId = ui.item[0];
				Tabora.length=0;
	 	    	var splittingCoordinate=[];
	 	    	var CoordinatesArray=[];
	 	    	if(ui.item[3] != "N/A"){
	 	    		RegionPrevBorders = ui.item[3];
	 	    		splittingCoordinate=ui.item[3].split(":");
		 	    	for(i=0;i<splittingCoordinate.length;i++) {
		 	    		Tabora.push({
		    				lat:parseFloat(splittingCoordinate[i].split(",")[0]),
		    				lng: parseFloat(splittingCoordinate[i].split(",")[1])
		    			});
		 	    	}
					regionPolygon = new google.maps.Polygon({
		       		  	    path: Tabora,
		       		  	    geodesic: false,
		       		  	    strokeColor: '#00ff00',
		       		  	    strokeOpacity: 1.0,
		       		  	    fillOpacity: 0.01,
		       		  	    id:ui.item[0],
		       		  	    strokeWeight:5,
		       		  	    map: map
		       		  	  });
		
		       		  	regionPolygon.metadata = { id: ui.item[0] };
			    	     	regionPolygons=[];
			    	     	regionPolygons[ui.item[0]] = regionPolygon;
			    	     	regionPolygons.push(regionPolygon);
		             	}else{
		             		RegionPrevBorders = "N/A";
			             	}

		 	    	}
	 	    	
					return false;
					
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
          return $("<li class='each'>")
          .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
              item[0] + "</span><br><span class='desc'>" +
              item[1] + "</span><br><span class='desc'>" +
              item[2] +"</span></div>")
          .appendTo(ul);
  	};
  	$("#popupregionId").focus(function(){

  		previousRegionId=document.getElementById("popupregionId").value;
		if (this.value == ""){
			$(this).autocomplete("search");
      }
	});

  	$("#popupregionId").change(function(){
		//previousRegionId=document.getElementById("popupregionId").value;
		console.log(previousRegionId);
  		if(this.value == "") {
    		if(previousRegionId != "") {
    			if(RegionPrevBorders != null && RegionPrevBorders != "N/A"){
    			regionPolygons[previousRegionId].setMap(null);
    			RegionPrevBorders = null;
    		}
    		}
    		document.getElementById("popupregionName").value= "";
	        document.getElementById("popupregionCode").value= "";
    		previousRegionId="";
    	}else{
    		previousRegionId=document.getElementById("popupregionId").value;
        	}
	});


$("#popupregionName").each(function(i, el) {

	  	$(el).autocomplete({
	  	  source:regions,
	  	   minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {		
				this.value = (ui.item ? ui.item[1]:'');
				document.getElementById("popupregionId").value= ui.item[0];
	            document.getElementById("popupregionCode").value= ui.item[2];
	            if(previousRegionId != ui.item[0]) {
	            	if(previousRegionId != ""){
	            		if(RegionPrevBorders != "N/A" && RegionPrevBorders != null){
	                		regionPolygons[previousRegionId].setMap(null);}
	            	}
					previousRegionId = ui.item[0];
					Tabora.length=0;
		 	    	var splittingCoordinate=[];
		 	    	var CoordinatesArray=[];
		 	    	if(ui.item[3] != "N/A"){
		 	    		RegionPrevBorders = ui.item[3];
		 	    		splittingCoordinate=ui.item[3].split(":");
			 	    	for(i=0;i<splittingCoordinate.length;i++) {
			 	    		Tabora.push({
			    				lat:parseFloat(splittingCoordinate[i].split(",")[0]),
			    				lng: parseFloat(splittingCoordinate[i].split(",")[1])
			    			});
			 	    	}
						regionPolygon = new google.maps.Polygon({
			       		  	    path: Tabora,
			       		  	    geodesic: false,
			       		  	    strokeColor: '#00ff00',
			       		  	    strokeOpacity: 1.0,
			       		  	    fillOpacity: 0.01,
			       		  	    id:ui.item[0],
			       		  	    strokeWeight:5,
			       		  	    map: map
			       		  	  });
			
			       		  	regionPolygon.metadata = { id: ui.item[0] };
				    	     	regionPolygons=[];
				    	     	regionPolygons[ui.item[0]] = regionPolygon;
				    	     	regionPolygons.push(regionPolygon);
			             	}else{
			             		RegionPrevBorders = "N/A";
				             	}

			 	    	}
				return false;
				
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
          return $("<li class='each'>")
          .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
              item[1] + "</span><br><span class='desc'>" +
              item[0] + "</span><br><span class='desc'>" +
              item[1] +"</span></div>")
          .appendTo(ul);
  	};
  	$("#popupregionName").focus(function(){
  		previousRegionId=document.getElementById("popupregionId").value;
		if (this.value == ""){
			$(this).autocomplete("search");
      }
  	});

  	$("#popupregionName").change(function(){
  		
  		if(this.value==""){
  			if(previousRegionId != "") {
  				if(RegionPrevBorders != null && RegionPrevBorders != "N/A"){
  	    			regionPolygons[previousRegionId].setMap(null);
  	    			RegionPrevBorders = null;
  	    		}
			}
			document.getElementById("popupregionId").value= "";
        	document.getElementById("popupregionCode").value="";
			previousRegionId="";
		}else{
			previousRegionId=document.getElementById("popupregionId").value;
			}
  	});
});

	////////    			
$("#popupregionCode").autocomplete({
	source:regions,
	minLength:0, maxShowItems: 4, scroll:true,
	select: function(event, ui) {		
		this.value = (ui.item ? ui.item[2]:'');
		document.getElementById("popupregionId").value= ui.item[0];
     	document.getElementById("popupregionName").value= ui.item[1];
        if(previousRegionId != ui.item[0]) {
        	if(previousRegionId != ""){
        		if(RegionPrevBorders != "N/A" && RegionPrevBorders != null){
            		regionPolygons[previousRegionId].setMap(null);}
        	}
			previousRegionId = ui.item[0];
			Tabora.length=0;
 	    	var splittingCoordinate=[];
 	    	var CoordinatesArray=[];
 	    	if(ui.item[3] != "N/A"){
 	    		RegionPrevBorders = ui.item[3];
 	    		splittingCoordinate=ui.item[3].split(":");
	 	    	for(i=0;i<splittingCoordinate.length;i++) {
	 	    		Tabora.push({
	    				lat:parseFloat(splittingCoordinate[i].split(",")[0]),
	    				lng: parseFloat(splittingCoordinate[i].split(",")[1])
	    			});
	 	    	}
				regionPolygon = new google.maps.Polygon({
	       		  	    path: Tabora,
	       		  	    geodesic: false,
	       		  	    strokeColor: '#00ff00',
	       		  	    strokeOpacity: 1.0,
	       		  	    fillOpacity: 0.01,
	       		  	    id:ui.item[0],
	       		  	    strokeWeight:5,
	       		  	    map: map
	       		  	  });
	
	       		  	regionPolygon.metadata = { id: ui.item[0] };
		    	     	regionPolygons=[];
		    	     	regionPolygons[ui.item[0]] = regionPolygon;
		    	     	regionPolygons.push(regionPolygon);
	             	}else{
	             		RegionPrevBorders = "N/A";
		             	}

	 	    	}
			return false;
			
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
          return $("<li class='each'>")
          .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
          	item[2] + "</span><br><span class='desc'>" +
              item[0] + "</span><br><span class='desc'>" +
              item[1] +"</span></div>")
          .appendTo(ul);
  	};
  	$("#popupregionCode").focus(function(){
  		previousRegionId=document.getElementById("popupregionId").value;
		if (this.value == ""){
			$(this).autocomplete("search");
      }
	});

  	$("#popupregionCode").change(function(){
  		
  		if(this.value==""){
  			if(previousRegionId != "") {
  				if(RegionPrevBorders != null && RegionPrevBorders != "N/A"){
  	    			regionPolygons[previousRegionId].setMap(null);
  	    			RegionPrevBorders = null;
  	    		}
			}
			document.getElementById("popupregionId").value= "";
        	document.getElementById("popupregionName").value= "";
			previousRegionId="";
		}else{
			previousRegionId=document.getElementById("popupregionId").value;
			}
	});

var previousRegionId =null;
	$('input[name ="Regionname"]').each(function(i, el) {

  		$(el).autocomplete({
  			source:regions,
  			minLength:0, maxShowItems: 4, scroll:true,
			select: function(event, ui) {		
				this.value = (ui.item ? ui.item[1]:'');
				$(this).parents("tr").find('input[name ="regionId"]').val(ui.item[0]);
				$(this).parents("tr").find('input[name ="Regioncode"]').val(ui.item[2]);
				
		        if(previousRegionId != ui.item[0]) {
	            	if(previousRegionId != ""){
	            		if(RegionPrevBorders != "N/A" && RegionPrevBorders != null){
	                		regionPolygons[previousRegionId].setMap(null);}
	            	}
					previousRegionId = ui.item[0];
					Tabora.length=0;
		 	    	var splittingCoordinate=[];
		 	    	var CoordinatesArray=[];
		 	    	if(ui.item[3] != "N/A"){
		 	    		RegionPrevBorders = ui.item[3];
		 	    		splittingCoordinate=ui.item[3].split(":");
			 	    	for(i=0;i<splittingCoordinate.length;i++) {
			 	    		Tabora.push({
			    				lat:parseFloat(splittingCoordinate[i].split(",")[0]),
			    				lng: parseFloat(splittingCoordinate[i].split(",")[1])
			    			});
			 	    	}
						regionPolygon = new google.maps.Polygon({
			       		  	    path: Tabora,
			       		  	    geodesic: false,
			       		  	    strokeColor: '#00ff00',
			       		  	    strokeOpacity: 1.0,
			       		  	    fillOpacity: 0.01,
			       		  	    id:ui.item[0],
			       		  	    strokeWeight:5,
			       		  	    map: map
			       		  	  });
			
			       		  	regionPolygon.metadata = { id: ui.item[0] };
				    	     	regionPolygons=[];
				    	     	regionPolygons[ui.item[0]] = regionPolygon;
				    	     	regionPolygons.push(regionPolygon);
			             	}else{
			             		RegionPrevBorders = "N/A";
				             	}

			 	    	}
				return false;
				
			}
	}).autocomplete("instance")._renderItem = function(ul, item) {
      return $("<li class='each'>")
      .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
      	item[1] + "</span><br><span class='desc'>" +
          item[0] + "</span><br><span class='desc'>" +
          item[2] +"</span></div>")
      .appendTo(ul);
	};
		$(this).focus(function(){
			previousRegionId =$(this).parent().parent().children('td[name="regionId"]').children('input').val();
			if (this.value == ""){
			$(this).autocomplete("search");
 	 		}
		});

		$(this).change(function(){
			console.log("previousRegionId : "+previousRegionId);
			console.log("RegionPrevBorders "+RegionPrevBorders);
			if(this.value==""){
  				if(previousRegionId != "") {
  					if(RegionPrevBorders != null && RegionPrevBorders != "N/A"){
  		    			regionPolygons[previousRegionId].setMap(null);
  		    			RegionPrevBorders = null;
  		    		}
			}
  			$(this).parents("tr").find('input[name ="regionId"]').val("");
			$(this).parents("tr").find('input[name ="Regioncode"]').val("");
			previousRegionId="";
			}else{
				previousRegionId =$(this).parent().parent().children('td[name="regionId"]').children('input').val();

				}
		});
	});//end of region boq table autocomplete	

	
});// end of ready function
						


//collapse active class	
$('.panel-collapse').on('show.bs.collapse', function () {
        
	    $(this).siblings('.panel-heading').removeClass('active');
	  });

	  $('.panel-collapse').on('hide.bs.collapse', function () {
	    $(this).siblings('.panel-heading').addClass('active');
	  });



	  $("#showhideSeq").click(function() {
		  ShowHideSequences(action);
		  if(action=="show"){
			  action="hide";
		  }else{
			action="show";
		}
	   });
			</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
	async defer></script>


</html>