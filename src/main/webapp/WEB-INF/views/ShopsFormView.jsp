<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<%@ include file="scripts.html"%>
<!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>  -->
<script
	src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link
	href="${pageContext.request.contextPath}/resources/css/all.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
<!-- <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>  -->
<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />  -->



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
/*
li.nav-item a span.active svg {
	color: #20B2AA !important;
}

li.nav-item a svg {
	color: gold !important;
}*/
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

.left_col {
	height: 60px
}

.btn-primary:hover {
	background-color: #007bff !important;
}
/*.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
/*	overflow-x: both; /* add padding to account for vertical scrollbar */
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

.elementName {
	width: 180px !important;
}

.inputWidth {
	width: 100px !important;
}

#discountAmount {
	width: 126px !important;
}

#collapseOne {
	height: 560px;
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

.shopimg {
	display: inline;
}

/* Image popup by ahmad */
#imgpopUpDiv {
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

#imagedisplay {
	width: 400px;
	height: 350px;
	margin: 20px;
}
.nav-link.active {
  color: #1D3763 !important;
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
						<span style="width: 200px;" class="input-group-text">Shop
							ID</span> <input type="text" readonly id="shopsID" value="${shopsID}"
							class="form-control text-input" /> <input name="csrfToken"
							value="5965f0d244b7d32b334eff840" type="hidden" />
					</div>
				</div>
			</div>

			<div class="col-md-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width: 210px;">Status</span>
					<select id="ordstat" class="form-control">
						<option value="draft"
							<c:if test = "${shopStatus =='draft'}" > selected </c:if>>Draft</option>
						<option value="activated"
							<c:if test = "${shopStatus =='activated'}" > selected </c:if>>Activated</option>
						<option value="deactivated"
							<c:if test = "${shopStatus =='deactivated'}" > selected </c:if>>Deactivated</option>
						<option value="cancelled"
							<c:if test = "${shopStatus =='cancelled'}" > selected </c:if>>Cancelled</option>
					</select>
				</div>
			</div>

			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Other Shops</span> <input
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
							onclick='window.location.href = "${pageContext.request.contextPath}/ShopsListView"'
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
						id="custom-tabs-one-sim-tab" data-toggle="tab"
						href="#custom-tabs-one-sim" role="tab"
						aria-controls="custom-tabs-one-sim" aria-selected="false"
						style="color: gold;">SIM CARDS</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-Image-tab" data-toggle="tab"
						href="#custom-tabs-Image" role="tab"
						aria-controls="#custom-tabs-Image" aria-selected="false"
						style="color: gold;">IMAGES</a></li>


					<li class="nav-item ml-auto">
						<div class="dropdown" Style="display: inline-block;">
							<button class="btn btn-secondary dropdown-toggle" type="button"
								id="notifactionDropdown" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Actions</button>
							<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
								<a class="dropdown-item" type="button" id="Activatewh">Activate</a>
								<a class="dropdown-item" type="button" id="Deactivatewh">Deactivate</a>
								<a class="dropdown-item" type="button" id="Cancelwh">Cancel</a>
							</div>
							<button type="button" id="sendEmail"
								class="btn btn-primary BtnActive">
								<i class="fa fa-envelope"></i> Send Email
							</button>
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

	<div class="container-fluid">

		<div class="tab-content" id="custom-tabs-one-tabContent">
			<div class="tab-pane fade show active" id="custom-tabs-one-home"
				role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">


				<p></p>

				<div id="left">

					<div style="width: auto;">
						<div class="input-group-prepend">
							<span class="input-group-text">Owner</span> <input type="text"
								id="owner" value="${owner}" class="form-control text-input"
								style="width: max;" />
						</div>

						<p></p>

					</div>

					<div style="width: auto;">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Shop Name</span> <input
									type="text" id="shopName" value="${shopName}"
									class="form-control text-input" style="width: max;" />
							</div>
						</div>
					</div>
					<p></p>


					<div style="width: auto;">
						<div class="input-group-prepend">
							<span class="input-group-text">Longtitude</span> <input
								type="text" id="longtitude" value="${longtitude}"
								class="form-control text-input" style="width: max;" />
						</div>

					</div>
					<p></p>

					<div style="width: auto;">
						<div class="input-group-prepend">
							<span class="input-group-text">Latitude</span> <input type="text"
								id="latitude" value="${latitude}"
								class="form-control text-input" style="width: max;" />
						</div>


					</div>
					<p></p>
					<div style="width: auto;">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Address</span> <input type="text"
									id="address" value="${address}" class="form-control text-input"
									style="width: max;" />
							</div>
						</div>
					</div>
					<p></p>




					<div style="width: auto;">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Area</span> <input type="text"
									id="area" value="${area}" class="form-control text-input"
									style="width: max;" />
							</div>
						</div>
					</div>

					<p></p>






					<div style="width: auto;">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Region</span> <input type="text"
									id="region" value="${region}" class="form-control text-input"
									style="width: max;" />
							</div>
						</div>
					</div>

					<p></p>




					<div style="width: auto;">
						<div class="input-group-prepend">
							<span class="input-group-text">Sales Manager</span> <input
								type="text" id="salesman" value="${salesman}"
								class="form-control text-input" style="width: max;" />

						</div>
					</div>
					<p></p>

					<div style="width: auto;">
						<div class="input-group-prepend">
							<span class="input-group-text">Region Manager</span> <input
								type="text" id="regionManager" value="${regionManager}"
								class="form-control text-input" style="width: max;" />

						</div>
					</div>
					<p></p>

					<div style="width: auto;">

						<div class="input-group-prepend">
							<span class="input-group-text">Agent</span> <input type="text"
								id="agent" value="${agent}" class="form-control text-input"
								style="width: max;" readonly />
						</div>


					</div>

					<p></p>


				</div>

				<div id="right">

					<div id="collapseOne" class="panel-collapse collapse show"
						aria-labelledby="headingOne">
						<div class="panel-body" style="height: 520px; margin-left: 10%">

							<div class="card-body" style='border: hidden;'>
								<div class="btn-toolbar"
									style="text-align: left; margin-bottom: 5px; margin-top: auto;">
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
												<i class="fa fa-trash"></i> Delete Shop
											</button>
										</div>

										<div id="txtDiv">
											<input id="mapText" type='text' disabled />
										</div>

									</div>
								</div>
								<div id="mapContainer" style='height: 440px;'></div>
							</div>
						</div>
					</div>


				</div>


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



			<div class="tab-pane fade" id="custom-tabs-Image" role="tabpanel"
				aria-labelledby="custom-tabs-Image-tab">

				<p></p>
				<div>

					<form>

						<div class="shopimg" id="shopimage" style="display: inline;"></div>




					</form>
				</div>

				<p></p>
			</div>

			<!-- end -->

		</div>


	</div>


	<div id="imgpopUpDiv" style="display: none;">


		<div id="imagedisplay"></div>


		<div>
			<button type="button" id="imgcancelButton" style="margin-left: 20px;"
				class="btn btn-primary BtnActive">
				<i class="fa fa-times"></i> Cancel
			</button>
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

if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}


/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
$("#sendEmail").on("click", function () {
//console.log("Helloooo in sendEmail onClick");
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
console.log("Helloooo in send onClick");
if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
{
alert("All Fields are required");
}
else{
$("#popUpDiv").fadeOut();
}

});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


 //function to get selected rows for save or delete
 var Tabora = [];
 var regionPolygon=[];
 var regionPolygons=[];
 var previousRegionId=null;
 var areas = [];
 var regions = [];
 var AreaPrevBorders = null;
 var RegionPrevBorders = null;
 var areaPolygon=[];
 var areaPolygons=[];
 var previousAreaId=null;
let map;
let service;
let infowindow;
var regionToDraw= {};
var RegionSet = [];
var polygons = [];
var areacheck =2;
var regioncheck= 2;
var boqArrayArea = [];
var boqArrayRegion=[];
var areaToDraw= {}; // to be checked if needed
var selectedArea={};
var Tabora = [];

if ('${areasData}' != "addNew") areas = ${areasData};
if ('${regionsData}' != "addNew") regions = ${regionsData};

function initMap() {
	var defaultZoom =17;
	var bounds = new google.maps.LatLngBounds();
	var myLatlng = { lat: (parseFloat(document.getElementById("latitude").value)), lng: (parseFloat(document.getElementById("longtitude").value)) };

		map = new google.maps.Map(document.getElementById("mapContainer"), {
		mapTypeControl: false,
 	    center: { lat: 0.300, lng: 37.761 },

		zoom:6,
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

	
	let infoWindow = new google.maps.InfoWindow;
	infoWindow.open(map);
	let markers = [];

	function addMarker(position) {
		  const marker = new google.maps.Marker({
		    position,
		    map,
		  });
	       infoWindow = new google.maps.InfoWindow({
		          content: "Shope Name : "+$('#shopName').val()+"<p><p>"+   "Address : "+$('#address').val()
		        });
			google.maps.event.addListener(marker, "click", function (e) {
				infoWindow.open(map,marker);
	        });
		  markers.push(marker);
		}

		// Sets the map on all markers in the array.
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
		  hideMarkers();
		  markers = [];
		}

		var position1 = new google.maps.LatLng($('#latitude').val(), $('#longtitude').val());
		addMarker(position1);

		$("#mapText").val(JSON.stringify((-1.286) +" || "+(36.817), null, 2));
		map.addListener("mousemove", (mapsMouseEvent)=>{
		    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
		    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));
		    
		  });

	$('#longtitude,#latitude').on('change', function() {
		
		deleteMarkers();
		var  agentLat = $("#latitude").val();
		var agentLng = $("#longtitude").val();
		var position1 = new google.maps.LatLng(agentLat, agentLng);
		addMarker(position1);
	});

	if(areacheck==2){
		if ('${listAreaBorder}' != "addNew") {	  
			     boqArrayArea = ${listAreaBorder};			     
		  }
	}
	if(!(Object.keys(boqArrayArea).length===0 || Object.keys(boqArrayArea) === null || Object.keys(boqArrayArea) == 'null'))
	{
		for (i = 0;i<Object.keys(boqArrayArea).length;i++){
			Tabora.length=0;
			areaCoordinates=boqArrayArea[Object.keys(boqArrayArea)[i]];
			previousAreaId = Object.keys(boqArrayArea)[i];
			console.log(previousAreaId);
			for (j = 0;j<boqArrayArea[Object.keys(boqArrayArea)[i]].length;j++){
							  Tabora.push({
							      lat:parseFloat(areaCoordinates[j].lat),
							      lng: parseFloat(areaCoordinates[j].lng)
							    });
			}
			
			
					  	 areaPolygon = new google.maps.Polygon({						  	 
					  	    path: Tabora,
					  	    geodesic: false,
					  	    strokeColor: '#06038D',
					  	    strokeOpacity: 1.0,
					  	    fillOpacity: 0.01,
					  	    ID : previousAreaId,
					  	    strokeWeight: 5,
             		  	    clickable: false,  					  	  
					  	    map: map
					  	  });

					  	areaPolygon.metadata = { id: previousAreaId};
					  	areaPolygons=[];
				     	areaPolygons[previousAreaId] = areaPolygon;
				     	areaPolygons.push(areaPolygon);
				     	console.log(areaPolygons);
						AreaPrevBorders =Tabora;
			
		} //End looping inside boqArrayArea which is for area border points. 
/*				  
		for (var j = 0; j < polygons.length; j++) {
			for (var i = 0; i < polygons[j].getPath().getLength(); i++) {
				bounds.extend(polygons[j].getPath().getAt(i));
			}
			} 
				  map.fitBounds(bounds); */
	} // Ending boqArrayArea if condition for checking if it has contents.
			     
	map.addListener("click", (mapsMouseEvent) => {
                 	    // Close the current InfoWindow.
		infoWindow.close();
                 	    // Create a new InfoWindow.
		infoWindow = new google.maps.InfoWindow({
		position: mapsMouseEvent.latLng,
		});
                 	     myLatlng=JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2);
                 	     infoWindow.setContent(JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
                 	    );
                 	     
                  	    infoWindow.open(map);
                 	    
                 	    const myJSON = JSON.stringify(myLatlng);
                 	    localStorage.setItem("testJSON", myJSON);
                 	    let text = localStorage.getItem("testJSON");                 
                 	    let obj = JSON.parse(text);
                 		var arr = obj.split(",");
                 		var reslatitude =arr[0].substring(11);
                 		var reslongtitude =arr[1].substring(10);
                 		reslongtitude=reslongtitude.slice(0,-1);
                 	     
			google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {   				
				$('#latitude').val(reslatitude);
            	document.getElementById("longtitude").value = reslongtitude;
				infoWindow.close();
				deleteMarkers();
				var position1 = new google.maps.LatLng(reslatitude, reslongtitude);
				addMarker(position1);
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			}));
		}); // End for map Click to create new marker for new location (by clicking on map and then click on Get Coordinate)
		
		google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (Path) {
			deleteMarkers();
			$('#latitude').val("");
			document.getElementById("longtitude").value = "";
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
		}));

					
	if(regioncheck==2){
		if ('${listRegionBorder}' != "addNew") {
		
	   		boqArrayRegion = ${listRegionBorder};  
/*		var region = document.getElementById("region").value;
		var spliter= region.split(":");
		var regionId =spliter[0]; */
		regionToDraw = Object.assign(regionToDraw, boqArrayRegion);
		
		}
	}

	if (!(Object.keys(boqArrayRegion).length === 0 || Object.keys(boqArrayRegion) == null || Object.keys(boqArrayRegion) == 'null'))
		{
		    for (i = 0;i<Object.keys(boqArrayRegion).length;i++){
				RegionSet.length=0;
		        RegionCoordinates=boqArrayRegion[Object.keys(boqArrayRegion)[i]];
		                       			  	
		        for (j = 0;j<boqArrayRegion[Object.keys(boqArrayRegion)[i]].length;j++){		                 				  
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
			  	    fillOpacity: 0,
			  	    ID:Object.keys(regionToDraw)[i],
			  	    clickable: false,
			  	    strokeWeight:5,
			  	    map: map
			  	  });

			  	regionPolygon.metadata = { id: Object.keys(regionToDraw)[i] };
    	     	regionPolygons=[];
    	     	regionPolygons[Object.keys(regionToDraw)[i]] = regionPolygon;
    	     	regionPolygons.push(regionPolygon);
				console.log(regionPolygons);
				  RegionPrevBorders = RegionSet;
		    }
	}
} // End for initMap()


$("#ordstat").click(  function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});	
})


$("#Activatewh").click(  function() {		   
	 $("#ordstat").val('activated');
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
})
	
	$("#Deactivatewh").click(  function() {	
		$("#ordstat").val('deactivated');
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	})
	
	$("#Cancelwh").click(  function() {	   
		 $("#ordstat").val('cancelled');
		 $("#formStatus").text("Not Saved");
		 $('.dot').css({"background-color" : "orange"});	 
	})
 
 $("#deleteButton").click(  function() {
		var deleteArray = [];
		deleteArray.push($("#shopsID").val());

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ShopsDelete",
			dataType : "json",
			data : {
				"shopsID" :deleteArray
			},
			success : function(data) {
			    location.replace("${pageContext.request.contextPath}/ShopsListView");
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
 	
if ('${shopId}' == "addNew") {
	 $("#formStatus").text("New");
	 $('.dot').css({"background-color" : "orange"});
}


function show(name) {

			$("#imagedisplay").empty();
			$("#imgpopUpDiv").fadeIn();
		    $("#imagedisplay").append("<img src= /SimRegPhotos/ShopPic/" + $("#shopsID").val() +"/"+ name + " width = '350' height = '350' />");
			
			$("#imgcancelButton").on("click", function (e) {
			$("#imgpopUpDiv").fadeOut();
			$("#imagedisplay").empty();
			});
}

$(document).ready(function () {
	if ('${listShopImage}' != "addNew") {
		ShopImageName = ${listShopImage};
		var imgName;
		for(i =0 ; i<ShopImageName.length ; i++){
					 imgName = ShopImageName[i];
					 $(".shopimg").each( function() {	
						 $(this).append("<img src= /SimRegPhotos/ShopPic/" + $("#shopsID").val() +"/"+ imgName + " width = '150' height = '150' style='padding:5px;' onClick=\"show(\'" + imgName + "\');\" />");
					  });
		 }
	}
		 
				if('${SelectedIndex}' != "addNew"){
					var SelectedIndex = ${SelectedIndex};
					if('${ShopsCount}' != "addNew"){
						var ShopsCount = ${ShopsCount};
				
				if(($("#shopsID").val()) != "" && ($("#shopsID").val()) != null){

				if(SelectedIndex === ShopsCount){
					
	        		document.getElementById("btnLast").style.opacity = 0.5;
	        		$("#btnLast").hasClass("disabled");
	        		document.getElementById("btnLast").style.pointerEvents = "none";
	        		
	        		document.getElementById("btnNexta").style.opacity = 0.5;
	        		document.getElementById("btnNexta").style.pointerEvents = "none";
					
					$("#btnNexta").hasClass("disabled");					
					}else{						
						if(!$("#btnNexta").hasClass("disabled")){							
							$("#btnNext").click(function(){								
								var param ="${pageContext.request.contextPath}/ShopsFormView?shopsID="+$("#shopsID").val()+"&NavAction=1";
								window.location.href =param;					
							});				
						}
						if(!$("#btnLst").hasClass("disabled")){
	        				
	        				$("#btnLst").click(function(){
	        					
								var param ="${pageContext.request.contextPath}/ShopsFormView?shopsID="+$("#shopsID").val()+"&NavAction=4";
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
							
							var param ="${pageContext.request.contextPath}/ShopsFormView?shopsID="+$("#shopsID").val()+"&NavAction=0";
							window.location.href =param;
							
						 });
					}
					$("#btnFrst").click(function(){

	        			if(!$("#btnFrst").hasClass("disabled")){
	        					
							var param ="${pageContext.request.contextPath}/ShopsFormView?shopsID="+$("#shopsID").val()+"&NavAction=3";
	        				window.location.href =param;
	        						
	        				}
	        				 });

				}
				
				}}
			}
				$("#label-1").text((SelectedIndex)+"/"+ShopsCount);
			 
			 $("#custom-tabs-one-sim-tab").click(function(){

				 var shopsID = document.getElementById("shopsID").value;
				 					
			     $.ajax({
			        type: "GET",
			        contentType: "application/json; charset=utf-8",
			        url: '${pageContext.request.contextPath}/GetSimCardsOnSite',
			        dataType : "json",
			        data: {
							"ID" : shopsID,
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

	 				});

////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
				$("#email").autocomplete({
				    source: function(request, response) {
				         
				    	
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
				         return $("<li class='each'>")
				         .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
				          item[0] + "</span></div>")
				         .appendTo(ul);
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
						
				 //////////////////////////////////////END OF EMAIL AUTOCOMPLETE////////////////////////////////////////		  	
			 
			 $("input").change(function() {
					$("#formStatus").text("Not Saved");
					$('.dot').css({"background-color" : "orange"});
			 });
							
				
 $("#saveButton").click(  function() {	 
	 var region = document.getElementById("region").value;
	 var spliter= region.split(":");
	 var regionId =spliter[0];

	 console.log(region);
		$.ajax({

			type : "GET",
			url : "${pageContext.request.contextPath}/ShopsFormSave",
			dataType : "json",
			data : {
				"shopsID" : $("#shopsID").val(),
				"owner" : $("#owner").val(),
				"latitude" : $("#latitude").val(),
			    "longtitude" : $("#longtitude").val(),
			    "address" : $("#address").val(),
			    "shopName" : $("#shopName").val(),
			    "salesman" : $("#salesman").val(),
			    "regionManager":$("#regionManager").val(),
			    "agent" : $("#agent").val(),
			    "area":$("#area").val(),  
				"region" :  $("#region").val(),
				"regionId": regionId,
				"creationDate": $("#creationDate").val(),
				"lastModifiedDate": $("#lastModifiedDate").val(),
				"status":  $("#ordstat").val(),
				"email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(),
			},
			success : function(data) {
			
				$("#shopsID").val(data.shopsID);
				$('#creationDate').val(data.creationDate);				
				var param ="${pageContext.request.contextPath}/ShopsFormView?shopsID="+$("#shopsID").val()+"&NavAction=2";
				location.replace(param);
			},
			error : function(error) {
				
				console.log("The error is " + error);
			}
		});
})


$("#region").autocomplete({    
	source: regions,
	minLength:0, maxShowItems: 4, scroll:true,

			select: function(event, ui) {
				console.log(ui.item[0] + ":"+ ui.item[1] + ":" + ui.item[2] + ":"+ ui.item[3]);
				this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1]:'');
				console.log("previousRegionId : "+previousRegionId);
				if(previousRegionId != ui.item[0]) {
	            	if(previousRegionId != ""){
	            		if(RegionPrevBorders != "N/A" && RegionPrevBorders != null){
	                		regionPolygons[previousRegionId].setMap(null);
	                		}
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
    	                 item[0] + '</span><br><span class="desc">' +
     	                 item[1] + '</span></div></li>').appendTo(ul);
											  
        	};
			$("#region").focus(function(){
				previousRegionId = document.getElementById("region").value.split(":")[0];
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}				
			});

			$("#region").change(function(){
				
		  		if(this.value == "") {
		  			if(RegionPrevBorders != null && RegionPrevBorders != "N/A"){
		    			regionPolygons[previousRegionId].setMap(null);
		    			RegionPrevBorders = null;
		    		}
		    		document.getElementById("region").value= "";
		    		previousRegionId="";
		    	}else{
		    		previousRegionId=document.getElementById("region").value.split(":")[0];
			    	}
			});
			
			  $("#area").autocomplete({
				  source:areas,
				   minLength:0, maxShowItems: 4, scroll:true,
					select: function(event, ui) {
						this.value = (ui.item ? ui.item[0] + ":" + ui.item[1]:'');						
						
						console.log("previousAreaId == "+previousAreaId);
						console.log("ui.item[0] == "+ui.item[0]);
						console.log(areaPolygons[previousAreaId]);
						console.log("AreaPrevBorders "+AreaPrevBorders);
		  	  			if(previousAreaId != ui.item[0]) {
		  	  	  			if(previousAreaId != ""){
								if(AreaPrevBorders != "N/A" && AreaPrevBorders != null && AreaPrevBorders != ""){
									areaPolygons[previousAreaId].setMap(null);
								}
		  	  	  	  		}
		  	  				//this.value = (ui.item ? ui.item[0]:'');
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
	        	$("#area").focus(function(){
	          		previousAreaId =document.getElementById("area").value.split(":")[0];
				if (this.value == ""){
					$(this).autocomplete("search");
		        }
			});
				
	        	$("#area").change(function(){
	        		
          			if(this.value == "") {
              				if(previousAreaId != ""){
              					if(AreaPrevBorders != null && AreaPrevBorders != "N/A"){
    			    		    	areaPolygons[previousAreaId].setMap(null); 
    			    		    	AreaPrevBorders=null;
    			    		    }	
                  				}

    	          			document.getElementById("area").value="";
    	    	    		previousAreaId="";
		          		}else{
		          			previousAreaId =document.getElementById("area").value.split(":")[0];
			          		}  			    	    		
	    	    	
	    		});


	        	  $("#salesman").autocomplete({
			    	    source: function(request, response, event, ui) {

				             $.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url: '${pageContext.request.contextPath}/GetUserJobTitle',
				                 data: {
										 
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {
				                         response(data.listSalesManager);
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
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
		        	$("#salesman").focus(function(){
			        	
					if (this.value == ""){
						
						$(this).autocomplete("search");
						
			        }
				});
			

		            $("#selectnav").autocomplete({		    			
		    		    source: function(request, response) {
		    			    
		    		             $.ajax({
		    		                 type: "GET",
		    		                 contentType: "application/json; charset=utf-8",
		    		                 url: '${pageContext.request.contextPath}/GetAvailableShops',
		    		                 data: {
		    								"Shops" : $("#selectnav").val(),
		    								"getAllShops": "1" ,
		    						 },
		    		                 dataType: "json",
		    		                 success: function (data) {
		    		                     if (data != null) {
		    		                         response(data.ListShops);
		    		                     }
		    		                 },
		    		                 error: function(result) {
		    		                     alert("Error");
		    		                 }
		    		             });
		    		         }, minLength:0, maxShowItems: 40, scroll:true,

		    					select: function(event, ui) {
		    						
		    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
		    						
		    						var param ="${pageContext.request.contextPath}/ShopsFormView?shopsID="+(ui.item[0])+"&NavAction=2";
		    						window.location.href =param;
		           						
		           						return false;
		           					}
		           				}).autocomplete("instance")._renderItem = function(ul, item) {
		           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
		           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		           	                 item[1] + '</span><br><span class="desc">' +
		           	                 item[0] + '</span></div></li>').appendTo(ul);
		           			};
		           					$("#selectnav").focus(function(){
		           		   	        	if (this.value == ""){
		           		   	            	$(this).autocomplete("search");
		           		   	        	}						
		           					});   //// ENd of Autocomplete for Area ID
		    	

		        	  $("#regionManager").autocomplete({
				    	    source: function(request, response, event, ui) {

					             $.ajax({
					                 type: "GET",
					                 contentType: "application/json; charset=utf-8",
					                 url: '${pageContext.request.contextPath}/GetUserJobTitle',
					                 data: {
											 
									 },
					                 dataType: "json",
					                 success: function (data) {
					                     if (data != null) {
					                         response(data.listRegionManager);
					                     }
					                 },
					                 error: function(result) {
					                     alert("Error");
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
			        	$("#regionManager").focus(function(){
						if (this.value == ""){
							$(this).autocomplete("search");
				        }
					});
				});
			</script>

<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
	async defer></script>

</html>