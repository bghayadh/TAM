
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
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
<%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script> --%>
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />
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
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
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

<!-- ALM GRID Scripts -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
<!-- 	
<script
	src="${pageContext.request.contextPath}/resources/js/areaF_BoqPopup.js"></script>
	 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />

<style>
.tooltip .tooltiptext {
  visibility: hidden;
  width: 120px;
  background-color: black;
  color: #fff;
  text-align: center;
  padding: 5px 0;
  border-radius: 6px;
 
  /* Position the tooltip text - see examples below! */
  position: absolute;
  z-index: 1;
}
.tooltip {
  position: relative;
  display: inline-block;
  border-bottom: 1px dotted black; /* If you want dots under the hoverable text */
}
.tooltip:hover .tooltiptext {
  visibility: visible;
}

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

.hide-row {
	display: none;
}

.left_col {
	height: 60px
}

.label {
	display: table-caption;
	text-align: center;
	font-size: 20px;
	font-style: bold;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	width: 350px;
	z-index: 9999;
}

/*.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
/*	overflow-x: both; /* add padding to account for vertical scrollbar */
/*	padding-right: 100px;
	        		}*/
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

#mapContainer {
	height: 600px;
	width: 600px;
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
.nav-link.active {
 color: #1D3763 !important;
 }
</style>






</head>
<body>

	<%--   <c:set var = "page" value = "Sales"/> --%>

	<%-- 	<%@ include file="header.html" %> --%>
   <c:set var="pg" value="Sales" scope="session"  />
 
   <jsp:include page="header.jsp"></jsp:include>
	<!--  end of general head page -->
	<p></p>

	<div class="container-fluid">
		<div class="row">

			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Area ID</span>
						<input type="text" id="areaId" value="${AreaId}"
							class="form-control text-input" readonly />
							
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
					</div>

				</div>
			</div>

			<div class="col-md-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width: 210px;">Status</span>
					<select id="ordstat" class="form-control">
						<option value="inprog"
							<c:if test = "${ordStatus =='inprog'}" > selected </c:if>>In
							Progress</option>
						<option value="approved"
							<c:if test = "${ordStatus =='approved'}" > selected </c:if>>Approved</option>
						<option value="activated"
							<c:if test = "${ordStatus =='activated'}" > selected </c:if>>Activated</option>
						<option value="deactivated"
							<c:if test = "${ordStatus =='deactivated'}" > selected </c:if>>Deactivated</option>
						<option value="cancelled"
							<c:if test = "${ordStatus =='cancelled'}" > selected </c:if>>Cancelled</option>
					</select>
				</div>
			</div>

			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Other Area</span>
						<!-- 	<select id="selectnav" class="form-control select2"></select> -->
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
						<span class="input-group-text">Created Date</span> <input
							type="text" id="createdate" readonly value="${creationDate}"
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
						<span class="input-group-text">Last Modify Date</span> <input
							type="text" id="lstmodifdate" readonly
							value="${lastModifiedDate}"
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
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 53px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnLast" href="#"
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
							onclick='window.location.href = "${pageContext.request.contextPath}/AreaListView"'
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



	</div>


	<div class="container-fluid">


		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: auto;">
					<li class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">INFORMATION</a></li>



					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-finance-tab" data-toggle="tab"
						href="#custom-tabs-finance" role="tab"
						aria-controls="#custom-tabs-finance" aria-selected="false"
						style="color: gold;">FINANCE</a></li>


					<li class="nav-item ml-auto">
						<div class="dropdown" Style="display: inline-block;">
							<button class="btn btn-secondary dropdown-toggle" type="button"
								id="notifactionDropdown" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Actions</button>
							<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
								<a class="dropdown-item" type="button" id="Approvewh">Approve</a>
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
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width=50% valign="top" class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Area Name</span> <input
										type="text" id="areaname" value="${areaName}"
										style="width: 400px" class="form-control text-input" />
								</div>
							</div>
						</td>
						<td rowspan=10 valign="top">
							<div class="panel-body" style="margin-top: 1%">
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
													class="delete-roww" style='margin-left: 10px;'>
													<i class="fa fa-trash"></i> Delete Area
												</button>
											</div>
											<div class=" top-btn-filter">
												<button id="togglepolygone" name="togglepolygone"
													class="buttonTog" style='margin-left: 10px;'>
													<i class="fas fa-mountain"></i>Fill Area
												</button>
											</div>
											<div id="txtDiv">
												<input id="mapText" type='text' disabled />
											</div>
										</div>
									</div>
									<div id="mapContainer" style='height: 480px; width: 100%;'>
									</div>
									<div id="info"></div>


								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left_col" style="margin-top: -50px;">
							<div class="form-group">

								<div class="input-group-prepend">
									<span class="input-group-text">Region</span> <input type="text"
										id="regionID" value="${RegionID}" style="width: 430px"
										class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>


					<tr>
						<td class="left_col" style="margin-top: -50px;">
							<form>
								<div>
									<div class="table-responsive-sm ">
										<div>
											<table id="areaTab"
												class="table table-striped table-bordered table-sm"
												style="display: block; margin-top: 20px; overflow-y: auto; width: 590px;">
												<!--  -->
												<thead>
													<tr>
														<th>
															<button type="button" id="selectAllIn" class="main">
																<span class="sub"></span>Select
															</button>
														</th>



														<th style='width: 90px;'>Sequence</th>
														<th style='width: 215px;'>latitude</th>
														<th style='width: 215px;'>longitude</th>
														<th style="" 150px" hidden>SEQ_ID</th>





													</tr>

												</thead>
												<tbody>


												</tbody>
											</table>
											<div style="position: static; margin-right: 70%;">


												<input type="text" id="AreaCoordinates" hidden
													value="${AreaCoordinates}"> <input type="button"
													class="addaction-row-Area" id="addaction-row-Area"
													value="Add Row" onclick="addrowsArea()">
												<button type="button" id="delrow" class="delete-row-area">Delete
													Row</button>


												<div class="form-group">

													<div class="input-group-prepend" hidden>
														<span class="input-group-text">lat</span> <input
															type="text" id="mouseclicklat" value="${mouseclicklat}"
															style="width: 430px" class="form-control text-input" />
													</div>
												</div>

												<div class="form-group">

													<div class="input-group-prepend" hidden>
														<span class="input-group-text">long</span> <input
															type="text" id="mouseclicklong" value="${mouseclicklong}"
															style="width: 430px" class="form-control text-input" />
													</div>
												</div>
											</div>

										</div>

									</div>
								</div>
							</form>
						</td>
					</tr>

					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>



			</div>




			<div class="tab-pane fade" id="custom-tabs-finance" role="tabpanel"
				aria-labelledby="custom-tabs-finance-tab">


				<div>
					<p></p>
					<form>


						<div class="table-responsive-sm" style="margin-top: 50px;">
							<table id="yaratab"
								class="table table-striped table-bordered table-sm"
								style="display: block; height: 200px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<th>
											<div style="width: 80px">
												<button type="button" id="selectAll" class="main">
													<span class="sub"></span>Select
												</button>
											</div>


										</th>


										<th>Start Date</th>
										<th>End Date</th>
										<th>No of 2G Sites</th>
										<th>Profit And Loss</th>
										<th>No of 2G & 3G Sites</th>
										<th>Profit And Loss</th>
										<th>No of 2G & 3G & 4G Sites</th>
										<th>Profit And Loss</th>
										<th>Total No Of Sites</th>
										<th>Sum Of Profit And Loss</th>
										<th>ID</th>

									</tr>
								</thead>
								<tbody>


								</tbody>
							</table>
						</div>
						<input type="text" id="RowIndex" value="" hidden> <input
							type="button" class="a" value="Add Row"
							onclick="addrowsFinance('addRowActions')">
						<button type="button" class="delete-row">Delete Row</button>
						<input type="text" id="RowToDeleteFinance" hidden value="">
					</form>
				</div>




			</div>

			<!-- popupFinance  -->

			<div class="container">
				<div id="DNModalFinance"
					class="modal fade custom-class-assignedto-modal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div
						class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header" style="background-color: #FF4F4F;">
								<h5 id="popupFinance" class="modal-title"
									style="font-weight: bold; color: #3C1596;"></h5>

								<button type="button" name="insertBelowFinance"
									onclick="insertRowBelowFinance()"
									class="btn btn-default btn-primary BtnActive  "
									style="color: white; position: relative; left: 50px;">Insert
									Below</button>
								<button type="button" name="insertAboveFinance"
									onclick="insertRowAboveFinance()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 60px;">Insert
									Above</button>
								<button type="button" name="deleteBoqRowFinance"
									onclick="deleteBoqRowFinance()"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 70px;">Delete</button>
								<button name="previousRowFinance"
									class="btn btn-default btn-primary BtnActive"
									style="color: white; position: relative; left: 80px;">Previous</button>
								<button name="nextRowFinance" onclick="nextRowFinance()"
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
															<span class="input-group-text">Start Date</span> <input
																type="date"
																class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																id="popupstartdate" value=""
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

										<p></p>
										<p></p>
										<p></p>
										<p></p>
										<div class="container-fluid">
											<div class="row">


												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text"> No of 2G Sites</span> <input
																type="text" id="popupno2gSites"
																class="form-control text-input" value="0"
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>

												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Profit And Loss</span> <input
																type="text" id="popupprofitAndLoss2g"
																class="form-control text-input" value="0"
																style="width: 675px; height: 37px;" />

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
															<span class="input-group-text">No of 2G & 3G
																Sites</span> <input type="text" id="popupno2g3gSites"
																class="form-control text-input" value="0"
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Profit And Loss</span> <input
																type="text" id="popupprofitAndLoss2g3g"
																class="form-control text-input" value="0"
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
															<span class="input-group-text">No of 2G & 3G & 4G
																Sites</span> <input type="text" id="popupno2g3g4gSites"
																class="form-control text-input" value="0"
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Profit And Loss</span> <input
																type="text" id="popupprofitAndLoss2g3g4g"
																class="form-control text-input" value="0"
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
															<span class="input-group-text">Total No Of Sites</span>
															<input type="text" id="popuptotalSites"
																class="form-control text-input" value="0"
																style="width: 674px; height: 37px;" />

														</div>
													</div>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text">Sum Of Profit And
																Loss</span> <input type="text" id="popuptotalSumProfit"
																class="form-control text-input" value="0"
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
															<span class="input-group-text">ID</span> <input
																type="text" id="popupID" readonly
																class="form-control text-input" value=""
																style="width: 674px; height: 37px;" />

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

			<!--end of popupFinance  -->



		</div>
	</div>
	</div>

	<div id="popUpDiv" style="display: none;">
		<div class="sendEmail" style="margin-top: 50px;">
			<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Sender</span> <input type="text"
							id="email" class="form-control text-input" /> <input type="text"
							id="password" value="${password}" class="form-control text-input"
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

<script>





/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
$("#sendEmail").on("click", function () {

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

});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////changinging status to NOT SAVED when pressing add/delete-row     ////////////////////////////////////////
$(".addaction-row-Finance").click(function() {
	
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

});

$("#deleteCoordBtn").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

	var i = 0 ;
	var rowCount = $('#areaTab >tbody >tr').length;

	for (i = 0;i<rowCount;i++){
		var ID =  $("#areaTab > tbody tr").eq(i).find('input[name=seqId]').val(); 
		if(ID != null){

			slctDelAreaBorder.push(ID);

						}
	}

});

$(".delete-roww").click(function() {
	
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

});	

$("#yaratab > tbody").change(function() {
	
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

});	

$(".addaction-row-Area").click(function() {
	
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
		
	});

$("#Approvewh").click(  function() {	
	   
	var Status=$("#ordstat").val();
	$("#ordstat").val('approved').trigger('change');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	 
	})

$("#Activatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
	 $("#ordstat").val('activated').trigger('change');
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	})

$("#Deactivatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
	 $("#ordstat").val('deactivated').trigger('change');
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	})
	
$("#Cancelwh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
	 $("#ordstat").val('cancelled').trigger('change');
	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});
	 
	})
	
	var CheckCount  = 0;
	var RowSelected = 0;
	var RowNew=0;
	
	function addrowsArea(){

 $("#areaTab input[type=checkbox]:checked").each(function () {

	var row = $(this);
	RowSelected= $(this).closest("tr")[0];
	CheckCount =row.length ;
	$(this).prop('checked', false);

	});


if (CheckCount ===1){
	 
	var rowCount= $("#areaTab >tbody tr").length;
	
	var markup = "<tr><td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopArea(this)' class='btn btn-default'  style='position:relative;left:3px;'></button></td>"
	+"<td name='sequence' ><input  name='sequence'  type='text' style='width:72px;' class='form-control text-input'></td>"
	+"<td name = 'arealatt' ><input name='arealatt' type='text'  style='width:100%;' class='form-control text-input'/></td>"
	+"<td name = 'arealng' ><input name='arealng'  type='text' style='width:215px;'style='width:200px;' class='form-control text-input' /></td></tr>"
	
   $("#areaTab > tbody tr").eq(RowSelected.rowIndex-1).after(markup);

    var rowCountfinal= $("#areaTab >tbody tr").length;
	RowNew=1;
    $("#areaTab > tbody tr").eq(RowSelected.rowIndex ).find('input[type=checkbox]').prop('checked', true);
    
}

else 
{
	    alert('You must create area if not existed or check at least one checkbox');
		 
	}
	 
CheckCount =0;

	}
	

	function deleterowsArea(){

		$("#areaTab input[type=checkbox]:checked").each(function () {

		    var row = $(this);
		    RowSelected= $(this).closest("tr")[0];
			CheckCount ++ ;
		    $(this).prop('checked', false);
			$("#areaTab > tbody tr").eq(RowSelected.rowIndex-1).remove();

		});
		
       	  initMap();
       	  
	       }
    
  var slctDelAreaBorder = [];
  var slctDelAreaFinance = [];
  var regioncheck= 2;// to know if recgion changed or not (1 means changed from load)
  var regionToDraw= {};
  var RegionSet = [];
  var polygons = [];
  var Tabora = [];
  var map;
  var newReg=0;
  var availableRegion=0;
  var streched =0;
  var regions =[];
  var RegionPrevBorders = null;
  var previousRegionId = null;
  var regionPolygons=[];
  var regionPolygon=[];
function initMap() {
	  var reslatitude;
	  var reslongitude;
	  var coordinates;
	  var line=[];
	  var line1=[];
	  var line2=[];
	  var line3=[];
	  var line4=[];
	  var Path =null;
	  var flag;
	  var checkIfPolygone =0;
      var myRoutePath =2;
	  var TakingArray = [];
	  var checkedArray = [];
	// Define the Center
	var kenya=new google.maps.LatLng(0.300 , 37.761);
	
	map = new google.maps.Map(document.getElementById("mapContainer"), {
	    center:kenya,
	    scaleControl: true,
		streetViewControl: true,
	  });
  map.setZoom(6);
  
  $("#mapText").val(kenya);
  
  map.addListener("mousemove", (mapsMouseEvent)=>{

    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));
    	    
  });

  if ('${regionsData}' != "addNew") regions = ${regionsData};
  //Create the initial InfoWindow.
  	let infoWindow = new google.maps.InfoWindow;

  //drawing tools (line and hand)
  	 const drawingManager = new google.maps.drawing.DrawingManager({
  		  
 	    drawingControl: true,
 	    drawingControlOptions: {
 	    position: google.maps.ControlPosition.TOP_CENTER,
 	    drawingModes: [

 	        google.maps.drawing.OverlayType.POLYLINE,
 	      ],
 	    },

 	    polylineOptions: {
 	        editable: true
 	      },
 	      polygonOptions: {
 	        editable: true
 	      },
 	      map: map
 	    
 	  });
 	  
 	  drawingManager.setMap(map);

    //edit map when longtitude in boq changed
	$("#areaTab > tbody").on("change", 'input[name="arealng"]', function () {

	    var RowSel= $(this).closest("tr")[0];
		var sameRowLng =$("#areaTab > tbody tr").eq(RowSel.rowIndex - 1).find('td[name="arealatt"]').children('input').val();
		
		document.getElementById("mouseclicklat").value=reslatitude;
		document.getElementById("mouseclicklong").value=reslongitude;
		CheckCount =$('#areaTab').find('input[type=checkbox]:checked').length;//$(this).length ;

		if(sameRowLng === null || sameRowLng === "")
			{
		}
		else
			{
			
	    	for (i=0; i<line.length; i++) 
			{

			  line[i].setMap(null);
			
			}
			
	    var areaLatLngInputs = [];
	    getAreaLatLngToDrawAgain(areaLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array
	    
  		for (i = 0;i<areaLatLngInputs.length;i++){

  		areaLat = areaLatLngInputs[i].Latitude;
  	 	areaLong = areaLatLngInputs[i].Longitude;
  		areaLatLngInputs[i] = new google.maps.LatLng(parseFloat(areaLat),parseFloat(areaLong));
  		
 		 }
		  Path = new google.maps.Polyline({
			  
		        path: areaLatLngInputs,
		        geodesic: true,
			    editable: true,
		        strokeColor: '#4986E7',
		        strokeOpacity: 1.0,
		        strokeWeight: 2
		        
		      });
	      
				line.push(Path);
		        Path.setMap(map);
		        
		}
		
		      infoWindow.close();
		
	})
	
	    //edit map when Latitude in boq changed
	$("#areaTab > tbody").on("change", 'input[name="arealatt"]', function () {
		
		  var RowSel= $(this).closest("tr")[0];
		  var sameRowLng =$("#areaTab > tbody tr").eq(RowSel.rowIndex - 1).find('td[name="arealng"]').children('input').val();
			 
		  $("#formStatus").text("Not Saved");
		  $('.dot').css({"background-color" : "orange"});
				
		  document.getElementById("mouseclicklat").value=reslatitude;
		  document.getElementById("mouseclicklong").value=reslongitude;
		  CheckCount =$('#areaTab').find('input[type=checkbox]:checked').length;//$(this).length 
		  
		  if(sameRowLng=== null || sameRowLng=== "")
			  {
		  }
		  else{

		//very important to draw again in each addition of coordinates into boq table
	    for (i=0; i<line.length; i++) 
			{

			  line[i].setMap(null);
			
			}
		
	    var areaLatLngInputs = [];
	    getAreaLatLngToDrawAgain(areaLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array
	    
  		for (i = 0;i<areaLatLngInputs.length;i++){

	    areaLat = areaLatLngInputs[i].Latitude;
	  	areaLong = areaLatLngInputs[i].Longitude;
	  	areaLatLngInputs[i] = new google.maps.LatLng(parseFloat(areaLat),parseFloat(areaLong));
	  	
	    }
		  Path = new google.maps.Polyline({
			  
		        path: areaLatLngInputs,
		        geodesic: true,
			    editable: true,
		        strokeColor: '#4986E7',
		        strokeOpacity: 1.0,
		        strokeWeight: 2
		        
		      });
	      
				line.push(Path);
	      		Path.setMap(map);
	      		
		}
		      infoWindow.close();
		        
	})
		
	
		var row=0;
		
	//delete row function
	$("#delrow").click(function() {
		
		slctDelAreaBorder = [];
		
		$("#areaTab input[type=checkbox]:checked").each(function () {

		    RowSelected= $(this).closest("tr")[0];
			CheckCount =$("input:checkbox:checked").length ;
		    $(this).prop('checked', false);
			$("#areaTab > tbody tr").eq(RowSelected.rowIndex-1).remove();
			var ID = $(this).parent().parent().children('td[name="seqId"]').children('input').val();
					
			if(ID != null){

				slctDelAreaBorder.push(ID);

			 }

	});
		
		if(CheckCount >= 1){
			
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

			//very important to draw again in each addition of coordinates into boq table
		    for (i=0; i<line.length; i++) 
				{
	
				  line[i].setMap(null);
				
				}
			
		    var areaLatLngInputs = [];
			getAreaLatLngToDrawAgain(areaLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array

		    for (i = 0;i<areaLatLngInputs.length;i++){

	   		areaLat = areaLatLngInputs[i].Latitude;
	  	 	areaLong = areaLatLngInputs[i].Longitude;
	  		areaLatLngInputs[i] = new google.maps.LatLng(parseFloat(areaLat),parseFloat(areaLong));
	  		
	  		}
	  		
			  Path = new google.maps.Polyline({
				  
			        path: areaLatLngInputs,
			        geodesic: true,
				    editable: true,
			        strokeColor: '#4986E7',
			        strokeOpacity: 1.0,
			        strokeWeight: 2
			        
			      });
		      
					line.push(Path);
			      	Path.setMap(map);
			      	infoWindow.close();

    		}
			else	    alert("Please create an area or select only 1 row to change its coordinates, thank you.");

	});

	//save updated long and lat in areaLatLngInputs
	function getAreaLatLngToDrawAgain (areaLatLngInputs) {

		$("#areaTab > tbody").find('input[name="record"]').each(function(){
		
		areaLatLngInputs.push({
		    
		"Latitude" : $(this).parent().parent().children('td[name="arealatt"]').children('input').val(),
		"Longitude" : $(this).parent().parent().children('td[name="arealng"]').children('input').val(),
		
		     });	
		
			});

		}


//when clicking on map show long and lat of this point in infoWinfow
  map.addListener("click", (mapsMouseEvent) => {
	  
    // Close the past InfoWindow.
    if(infoWindow) infoWindow.close();
    
    // Create a new InfoWindow.
    infoWindow = new google.maps.InfoWindow({
        
      position: mapsMouseEvent.latLng,
      content:JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2),
      
    });
    
    infoWindow.open(map);
	reslatitude =mapsMouseEvent.latLng.lat();
	reslongitude =mapsMouseEvent.latLng.lng();

	//get coordinates button to set the map clicked coordinates in the checked row in boq
	google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {

		 document.getElementById("mouseclicklat").value=reslatitude;
		 document.getElementById("mouseclicklong").value=reslongitude;
		 CheckCount=0;

	     RowSelected= $('#areaTab').find('input[type=checkbox]:checked').closest("tr")[0];
		 CheckCount =$('#areaTab').find('input[type=checkbox]:checked').length;//$(this).length ;
		
		 if(CheckCount == 1){
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				
			$("#areaTab input[type=checkbox]:checked").each(function () {

			    RowSelected= $(this).closest("tr")[0];
					 CheckCount ++ ;

			});
						
			var rowToAdd = RowSelected.rowIndex;
					
				$("#areaTab > tbody tr").eq(rowToAdd -1).find('td[name="arealatt"]').children('input').val((Math.round(reslatitude*100000000)/100000000).toFixed(8));
				$("#areaTab > tbody tr").eq(rowToAdd -1 ).find('td[name="arealng"]').children('input').val((Math.round(reslongitude*100000000)/100000000).toFixed(8));
				 																				
				//very important to draw again in each addition of coordinates into boq table
			    for (i=0; i<line.length; i++) 
					{
		
					  line[i].setMap(null);
					
					}
				
			    var areaLatLngInputs = [];
			    getAreaLatLngToDrawAgain(areaLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array

			  for (i = 0;i<areaLatLngInputs.length;i++){

				  areaLat = areaLatLngInputs[i].Latitude;
				  areaLong = areaLatLngInputs[i].Longitude;
				  areaLatLngInputs[i] = new google.maps.LatLng(parseFloat(areaLat),parseFloat(areaLong));
				  
			  }
					Path = new google.maps.Polyline({
						  
					        path: areaLatLngInputs,
					        geodesic: true,
						    editable: true,
					        strokeColor: '#4986E7',
					        strokeOpacity: 1.0,
					        strokeWeight: 2
					        
					      });
				      
					line.push(Path);
				    Path.setMap(map);
				    infoWindow.close();
					      
		    }
		    
					else	    alert("Please create an area or select only 1 row to change its coordinates, thank you.");

		}));
	
  });
  
 
	

	  //change Line color to red on checked checkboxes
       $("#areaTab > tbody").on("change", 'input[name="record"]', function () {
        	
    		TakingArray = [];
		    checkedArray = [];
		    indexRow = $(this).parent().parent().index();
				   
	   $('input[name="record"]').each(function(indexRow) {
		   
		    if(myRoutePath !=2){

			 myRoutePath.setMap(null);
				
		    	 for (i=0; i<line3.length; i++) 
		    	 {
	
		    	   line3[i].setMap(null);
		    	 
		    	 }
			}
		  if (this.checked) {

			var lat= $("#areaTab > tbody tr").eq(indexRow).find('td[name="arealatt"]').children('input').val();
			var lng= $("#areaTab > tbody tr").eq(indexRow).find('td[name="arealng"]').children('input').val();
			var pointToMark = [];


			
			if(lat === "" || lat === null || lng === "" || lng === null){
			}
			else{

			 pointToMark = new google.maps.LatLng(parseFloat(lat),parseFloat(lng));
			
			checkedArray.push(indexRow);
			   
	   		TakingArray.push((pointToMark));
	   
		  	myRoutePath = new google.maps.Polyline({
							   path: TakingArray,
							   geodesic: true,
							   editable: true,
							   strokeColor: 'red',
							   strokeOpacity: 1.0,
							   strokeWeight: 5
							 });
						   
			   
							 myRoutePath.setMap(map);
							
							line1.push(myRoutePath);
							line3.push(myRoutePath);
							drawingManager.setMap(null);
							 // end changing..//
							 
							 //when checked drow the lines of polylines in red
		google.maps.event.addDomListener($(document).on("change", 'input[name="record"]', function (polyline){
			   
			myRoutePath.setMap(map); 

			//
			if(($('input[type=checkbox]:checked').length)<1){
			
				for (i=0; i<line3.length; i++) 
				{
		
				  line3[i].setMap(null);
				 
				}
		  }
			   }))
			   
		  }
				 }  

				 })
				 //end
			   })
			   
	  if ('${docStatus}' != "addNew" ){
		  
		  checkIfPolygone =1;///to check if the shape constructed by polygone logic
		  boqArrayBorder = [];
		  
		  if('${listAreaBorder}' != "addNew" ){
			  
		  checkIfPolygone =1;
		  boqArrayBorder = ${listAreaBorder};
		  _cupCakeHAjouzPoints = [];

		  for (i = 0;i<boqArrayBorder.length;i++){

		    areaLat = boqArrayBorder[i].lat;
		  	areaLong = boqArrayBorder[i].lng;
			_cupCakeHAjouzPoints[i] = new google.maps.LatLng(parseFloat(areaLat),parseFloat(areaLong));

				  Path = new google.maps.Polyline({
				        path: _cupCakeHAjouzPoints,
				        geodesic: true,
					    editable: true,
				        strokeColor: '#4986E7',
				        strokeOpacity: 1.0,
				        strokeWeight: 2,
		 		  	    clickable: false
				        
				      });
					
		
				      Path.setMap(map);
					 
				      line.push(Path);
			          line4.push(Path);
				      drawingManager.setMap(null);

////////// Construct the polygon above the polyline 
Paths = new google.maps.Polygon({
    paths: _cupCakeHAjouzPoints,
    strokeColor: '#4986E7',
    strokeOpacity: 0,
    strokeWeight: 0,
    fillColor: '#4986E7',
    fillOpacity: 0.08,
    Editable: false,
	clickable: false
	    
  });
  Paths.setMap(map);
  line.push(Paths);
  line2.push(Paths);
				
			  }	


			   // toggle fill area by clicking the button (Fill area )
 
			   var clickCount = 0;

	google.maps.event.addDomListener($(document).on('click', '#togglepolygone', function (polygon){
	if (flag!=1){
    clickCount = (clickCount == 2) ? 0:clickCount;
    if(clickCount == 0){

		for (i=0; i<line2.length; i++) {
			line2[i].setMap(null);
		}
    }
	else if(clickCount == 1){
		for (i=0; i<line2.length; i++) {
			
			line2[i].setMap(map);
		}
	
    }

    clickCount++;}
}));	

// changing color for the multiple polyline based on the selected checboxes
	  	   var checkedArray = [];
	  	 var pointToMark = [];
	  $("#areaTab > tbody").on("change", 'input[name="record"]', function () {
		
	   var TakingArray = [];
	   var i=0;
	   indexRow = $(this).parent().parent().index();
	   pointToMark = [];
	   $('input[name="record"]').each(function(indexRow) {

		 })
	   })
		}
}

    // changing color for multiple polyline by clicking select all
	     
		google.maps.event.addDomListener($(document).on('click', '#selectAllIn', function (polyline){
		   var checkedValArray=[];
		   var notcheckedValArray=[];
		   var TakingArray=[];
		   var notTakingArray=[];
		   if ($(this).hasClass('allChecked'))
				{
					for (i=0; i<line3.length; i++) 
				{
					line3[i].setMap(null);
				}
		   $('input[name="record"]').each(function(indexRow) {
	  
	   if (this.checked) {
			var latt=	$("#areaTab > tbody tr").eq(indexRow).find('td[name="arealatt"]').children('input').val();
			var longgg=  	$("#areaTab > tbody tr").eq(indexRow).find('td[name="arealng"]').children('input').val();
			var pointToMark = [];


			
			if(latt === "" || latt === null || longgg === "" || longgg === null){
			}
			else{
				
			var pointToMark = [];
				
			 pointToMark = new google.maps.LatLng(parseFloat(latt),parseFloat(longgg));
			
				   checkedArray.push(indexRow);
			   
	   TakingArray.push((pointToMark));
	   

		   
		   checkedValArray.push(indexRow)
		   myRoutePath = new google.maps.Polyline({
							   path: TakingArray,
							   geodesic: true,
								   editable: true,
							   strokeColor: 'red',
							   strokeOpacity: 1.0,
							   strokeWeight: 5
							 });
						   
			   
							 myRoutePath.setMap(map);
							
							line1.push(myRoutePath);
							line3.push(myRoutePath);
							 drawingManager.setMap(null);
							 // end changing..//
	   }
	   }

	})
}
else
for (i=0; i<line3.length; i++) 
{
	line3[i].setMap(null);
}
$('input[name="record"]').each(function(indexRow) {
	
	if($(this).is(":not(:checked)")) {

		//check if polygone draw it in blue color else if its a polyline draw it in black color
		if(checkIfPolygone ===1){
		
		  var latt= parseFloat($(this).parent().parent().children('td[name="arealatt"]').children('input').val());
		  var longgg= parseFloat($(this).parent().parent().children('td[name="arealng"]').children('input').val());
		  var pointToMark = [];
				
		  pointToMark = new google.maps.LatLng(parseFloat(latt),parseFloat(longgg));
			
		  checkedArray.push(indexRow);
			   
  	      notTakingArray.push((pointToMark));
		  myRoutePath = new google.maps.Polyline({
							  path: notTakingArray,
							  geodesic: true,
							editable: true,
							  strokeColor: '#4986E7',
							  strokeOpacity: 1.0,
							  strokeWeight: 2
							});
						  
			  
							myRoutePath.setMap(map);
						   
						   line1.push(myRoutePath);
						   line3.push(myRoutePath);
							drawingManager.setMap(null);
							// end changing..//

	  }
}

  })
	   }))
	  
			  // END changing color for multiple polyline by clicking select all

		// delete area button
  		google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (polyline){
		checkIfPolygone=0;//no polygone available
  	    document.getElementById("AreaCoordinates").value = "1"

  	        	           flag=1;
			for (i=0; i<line.length; i++) 
			{

			  line[i].setMap(null);
			
			}
			for (i=0; i<line1.length; i++) 
			{

				line1[i].setMap(null);
			
			}
			for (i=0; i<line3.length; i++) 
			{

				line3[i].setMap(null);
			
			}
			
			$('#areaTab tbody').empty();
			  drawingManager.setMap(map);

		})); 

		//remove myRoutePath polyline (red lines)
		google.maps.event.addDomListener($(document).on("change", 'input[name="record"]', function (polyline){
		  if($(this).is(":not(:checked)")){
		
		for (i=0; i<line1.length; i++) 
		{
		
		  line1[i].setMap(null);
		 
		}
		 
		 }
		 
		}));


	
     ///construct boq table when finishing the polyline
		 google.maps.event.addListener(drawingManager, 'polylinecomplete', function (polyline) { 
			  checkIfPolygone=0; 
			  var lat,lng;
			  line=[];

		        for (i=0;i<polyline.getPath().length;i++){
		        	coordinates =(polyline.getPath().getArray()[i]);
			   lat=polyline.getPath().getArray()[i].lat().toFixed(8); 
		   	   lng=polyline.getPath().getArray()[i].lng().toFixed(8);     
				line.push(polyline);
				var markup = "<tr><td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'></td><td name='sequence'>"
					+"<input name='sequence' type='text' value='"+(parseInt(i)+1)+"' style='width:72px;'  class='form-control text-input'/></td>"
					+"<td name='arealatt' style='width:auto;'><input name='arealatt' type='text' value='"+lat+"' style='width:215px;' class='form-control text-input'/></td>"
					+"<td name='arealng' style='width:auto;'>"
					+"<input name='arealng' type='text' value='"+lng+"' style='width:215px;' class='form-control text-input'/></td></tr>";
				    $("#areaTab > tbody").append(markup);					
		        }
		        drawingManager.setMap(null);
		        //events when polyline streched
		          google.maps.event.addListener(polyline, "dragend", polylineChanged);
				  google.maps.event.addListener(polyline.getPath(), "insert_at", polylineChanged);
				  google.maps.event.addListener(polyline.getPath(), "remove_at", polylineChanged);
				  google.maps.event.addListener(polyline.getPath(), "set_at", polylineChanged);
				  
//when streched update the boq table
				  function polylineChanged(evt) {
					  
					$('#areaTab tbody').empty();
					$("#formStatus").text("Not Saved");
					$('.dot').css({"background-color" : "orange"});
					  
					streched =1;
					$('#areaTab tbody').empty();

					line=[];

				    for (i=0;i<polyline.getPath().length;i++){
				    coordinates =(polyline.getPath().getArray()[i]);
					lat=polyline.getPath().getArray()[i].lat().toFixed(8); 
				   	lng=polyline.getPath().getArray()[i].lng().toFixed(8);     
					line.push(polyline);
					
					var markup = "<tr><td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'></td><td name='sequence'>"
						+"<input name='sequence' type='text' value='"+(parseInt(i)+1)+"' style='width:72px;'  class='form-control text-input'/></td>"
						+"<td name='arealatt' style='width:auto;'><input name='arealatt' type='text' value='"+lat+"' style='width:215px;' class='form-control text-input'/></td>"
						+"<td name='arealng' style='width:auto;'>"
						+"<input name='arealng' type='text' value='"+lng+"' style='width:215px;' class='form-control text-input'/></td></tr>";
					    $("#areaTab > tbody").append(markup);					
				        }
			        
						  drawingManager.setMap(map);
					      drawingManager.setMap(null);

					}

			      drawingManager.setMap(null);
					
		      });
	      
		 function getPath() {
			 
			  streched =1;
			  var path = Path.getPath();
			  var len = path.getLength();
			  var lat=null;
			  var lng=null;
			  var coordStr = "";
				$('#areaTab tbody').empty();
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				  
			  for (var i = 0; i < len; i++) {
				if(i<len-1)
					coordStr += path.getAt(i).toUrlValue(6) +";";
				else  coordStr += path.getAt(i).toUrlValue(6);
				lat = path.getAt(i).toJSON().lat.toFixed(8);
				lng = path.getAt(i).toJSON().lng.toFixed(8);
				var markup = "<tr><td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'></td><td name='sequence'>"
					+"<input name='sequence' type='text' value='"+(parseInt(i)+1)+"' style='width:72px;'  class='form-control text-input'/></td>"
					+"<td name='arealatt' style='width:auto;'><input name='arealatt' type='text' value='"+lat+"' style='width:215px;' class='form-control text-input'/></td>"
					+"<td name='arealng' style='width:auto;'>"
					+"<input name='arealng' type='text' value='"+lng+"' style='width:215px;' class='form-control text-input'/></td></tr>";

					 $("#areaTab > tbody").append(markup);
				
			  }
			}
	

			var rowCount = $('#areaTab >tbody >tr').length;

		if (newReg===0 && $('#areaTab >tbody >tr').length !=0){
			//when polygone streched
		 	google.maps.event.addListener(Path, "dragend", getPath);
		 	google.maps.event.addListener(Path.getPath(), "insert_at", getPath);
		  	google.maps.event.addListener(Path.getPath(), "remove_at", getPath);
		  	google.maps.event.addListener(Path.getPath(), "set_at", getPath);

		  newReg=1;
		  
			}
		
		  var CountRowIn = 0;
  		 	var bounds = new google.maps.LatLngBounds();
  		 	
			//region drawing part
		   if(regioncheck==2){       //cehcking if data from load
				if ('${listRegionBorder}' != "addNew") {
					availableRegion=1;
			   	 boqArrayRegion = ${listRegionBorder};
			   	 
						regionToDraw = Object.assign(regionToDraw, boqArrayRegion);
				}else{
					availableRegion=0;
					}
			}

			console.log("regionToDraw : "+Object.keys(regionToDraw));
			previousRegionId = Object.keys(regionToDraw);
		//inner ajax
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
     		  	regionPolygon.metadata = { id: previousRegionId };
	    	     	regionPolygons=[];
	    	     	regionPolygons[previousRegionId] = regionPolygon;
	    	     	regionPolygons.push(regionPolygon);
			  
			   
	    	     	//regionPolygons.setMap(map);
	   

	 
	 //polygons.setMap(map);
        }
       

        $("#regionID").autocomplete({
			
		    source: regions,
		    minLength:0, maxShowItems: 5, scroll:true,

					select: function(event, ui) {
						regionID.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');

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
       	 		    	return $('<li class="each">').data( "item.autocomplete", item )
       		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
       	                 item[0] + '</span><br><span class="desc">' +
       	                 item[1] + '</span></div></li>').appendTo(ul);
       			};
       					$("#regionID").focus(function(){
//           					console.log(regionPolygons[previousRegionId]);
       						//previousRegionId = document.getElementById("regionID").value.split(":")[0];
       		   	        	if (this.value == ""){
       		   	            	$(this).autocomplete("search");
       		   	        	}						
       					}); 

       					$("#regionID").change(function(){
       						//previousRegionId=document.getElementById("region").value.split(":")[0];
       				  		if(this.value == "") {
       				  		if(previousRegionId != "") {
       			    			if(RegionPrevBorders != null && RegionPrevBorders != "N/A"){
       			    				regionPolygons[previousRegionId].setMap(null);
       			    			RegionPrevBorders = null;
       			    		}
       			    		}
       				    		document.getElementById("regionID").value= "";
       				    		previousRegionId="";
       				    	}
       					});  //// ENd of Autocomplete for Area ID

}
					                         
	



$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});

if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}

$("#areaname").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});

$("#regionID").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});
	
$(".add-row").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});


	
$(".delete-row").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});	

$("#yaratab > tbody").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});	



$(document).ready(function() {
	

	if('${SelectedIndex}' != "addNew"){
		var SelectedIndex = ${SelectedIndex};
		if('${AreasCount}' != "addNew"){

		
	var AreasCount = ${AreasCount};
	
	if(($("#areaId").val()) != "" && ($("#areaId").val()) != null){

	if(SelectedIndex === AreasCount){
		document.getElementById("btnLast").style.opacity = 0.5;
		$("#btnLast").hasClass("disabled");
		document.getElementById("btnLast").style.pointerEvents = "none";
		
		document.getElementById("btnNexta").style.opacity = 0.5;
		document.getElementById("btnNexta").style.pointerEvents = "none";

		
		$("#btnNexta").hasClass("disabled");
		
		}else{
			
			$("#btnNext").click(function(){
			if(!$("#btnNexta").hasClass("disabled")){
					
					var areaId=$("#areaId").val();
					var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+areaId+"&NavAction=1";
					window.location.href =param;
				}
				
				});
	
			if(!$("#btnLst").hasClass("disabled")){
				var areaId=$("#areaId").val();
				
				$("#btnLst").click(function(){
					
					var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+areaId+"&NavAction=4";
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
			var areaId=$("#areaId").val();
			
		if(!$("#btnPrva").hasClass("disabled")){
			
				
				var areaId=$("#areaId").val();
				var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+areaId+"&NavAction=0";
				location.reload();

				window.location.href =param;
			}
			
			 });
		$("#btnFrst").click(function(){
			var areaId=$("#areaId").val();

			if(!$("#btnFrst").hasClass("disabled")){
					
				var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+areaId+"&NavAction=3";
				window.location.href =param;
						
				}
				 });

	}
	
	}}
}
	$("#label-1").text((SelectedIndex)+"/"+AreasCount);

	
// ADD NEW Row in finance Tab
	var dict = [];
	var dictArray = [];
	var alertFlag;

$("#saveButton").click( function() {
	
	alertFlag='0';
	
	if ($("#regionID").val()== '') {
		
		 alert('Region  cannot be NULL');
		 
		 return false;
	}
	

	if ($("#areaname").val()== '') {
		
		alert('Area Name cannot be NULL');
		 
	 	return false;
	 
	 }
	sortSequence ();
	selectALLrows();
	getselectedrows ();
	getselectedrowsForArea();
	
	if (alertFlag =='1') {

	     return false; 
	     
	 }

	 if (alertFlag =='2') {

		 alert("**This ID is already Taken Please Enter Another ID");
		 
	     return false; 
	     
	  }

	 saverowsintables();

	 });
	 
if ('${docStatus}' != "addNew" ){

	function count(array){
	
   		var c = 0;
   		for(i in array) // in returns key, not object
        	if(array[i] != undefined)
           		c++;
    return c;
    
}

	var latArray;
	var lngArray;
	var seqId;
	boqArray = [];
	boqArray = ${ListAreaFinance};
	boqArrayBorder = [];
	
if('${listAreaBorder}' != "addNew" ){

	boqArrayBorder = ${listAreaBorder};
	var rowCount;
	
	for (i = 0;i<boqArrayBorder.length;i++){
	
 	 areaLat = boqArrayBorder[i].lat;
	 areaLong = boqArrayBorder[i].lng;
	 seqId =  boqArrayBorder[i].id;

	 var itemRow = "<tr>";
	
	 itemRow= itemRow + "<td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'></td><td name='sequence'>"
	 itemRow= itemRow +"<input name='sequence' type='text' value='"+(parseInt(i)+1)+"' style='width:72px;' class='form-control text-input'/></td>"	 
	 itemRow =itemRow + "<td name='arealatt' style='width:auto;'><input name='arealatt' style='width:215px;' value='" + areaLat + "' class='form-control text-input' /></td>";
	 itemRow =itemRow + "<td name='arealng' style='width:auto;'><input  name='arealng' style='width:215px;' value='" + areaLong+ "' class='form-control text-input'/></td>";
	 itemRow =itemRow + "<td name='seqId' style='width:auto;' hidden ><input  name='seqId' style='width:215px;' value='" + seqId+ "' class='form-control text-input'/></td>";

	 itemRow =itemRow + "</tr>";
	 
	 $("#areaTab > tbody").append(itemRow);

}

}


for (i = 0;i<boqArray.length;i++){

 var itemRow = "<tr>";
 
 itemRow= itemRow +"<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopFinance(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>" 
 itemRow =itemRow + "<td name='startDate'><input type='date' name='startdate' value='" + boqArray[i].startDate + "' style='width:400px;' /></td>";
 itemRow =itemRow + "<td name='endDate'><input type='date' name='enddate' value='" + boqArray[i].endDate + "' style='width:400px;' /></td>";
 itemRow =itemRow + "<td name='no2gSites'><input type='text'  value='" + boqArray[i].number2gSites +"' style='width:250px;'></td>";
 itemRow =itemRow + "<td name='profitAndLoss2g'><input type='text'  value='" + boqArray[i].pl2g +"' style='width:250px;'></td>";
 itemRow =itemRow + "<td name='no2g3gSites'><input type='text'  value='" + boqArray[i].number2g3gSites +"' style='width:250px;'></td>";
 itemRow =itemRow + "<td name='profitAndLoss2g3g'><input type='text'  value='" + boqArray[i].pl2g3g +"' style='width:250px;'></td>";
 itemRow =itemRow + "<td name='no2g3g4gSites'><input type='text'  value='" + boqArray[i].number2g3g4gSites +"' style='width:250px;'></td>";
 itemRow =itemRow + "<td name='profitAndLoss2g3g4g'><input type='text'  value='" + boqArray[i].pl2g3g4g +"' style='width:250px;'></td>";
 itemRow =itemRow + "<td name='totalSites'><input type='text'  value='" + boqArray[i].totalSites +"' style='width:250px;' ></td>";
 itemRow =itemRow + "<td name='totalSumProfit'><input type='text'  value='" + boqArray[i].sumProfitLoss +"' style='width:250px;' ></td>";
 itemRow =itemRow + "<td name='id'><input type='text'  value='" + boqArray[i].id +"' style='width:250px;' readonly></td>";
	
 itemRow =itemRow + "</tr>";
 
 $("#yaratab > tbody").append(itemRow);

}
	
}

$("#deleteButton").click(  function() {

	 	selectALLrows();
		var deleteArray = [];
		deleteArray.push($("#areaId").val());
		
		$.ajax({
			type: "GET",
			url: "${pageContext.request.contextPath}/AreaDelete",
			dataType: "json",
			data: {
				"AreaId": deleteArray
			},
			success: function (data) {
				location.replace("${pageContext.request.contextPath}/AreaListView");
			},
			error: function (error) {
				console.log("The error is " + error);
			}
		});
	    	
})

var rowindxFinance=0;

// end of openPopupShops
	 
	 $(".add-row").click(function(){
		 
		var now = new Date();
		var lastday  = new Date(now.getFullYear(), now.getMonth(), 0);
        var firstday = new Date(lastday.getFullYear(), lastday.getMonth(), 1);
        var lastMonth = firstday.getDate()+'/'+(firstday.getMonth()+1)+'/'+firstday.getFullYear()+' - '+lastday.getDate()+'/'+(firstday.getMonth()+1)+'/'+lastday.getFullYear();

		var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopFinance(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td><td name='startDate'>"
	    	+"<input name='startdate' type='date' value='" + firstday + "' style='width:400px;' /></td>"
			+"<td name='endDate'>"
			+"<input name='enddate' type='date' style='width:400px;' /></td>"
			+"<td name='no2gSites'>"
	        +"<input  type='text' style='width:250px;' value= 0></td>"
			+"<td name='profitAndLoss2g'><input style='width:250px;'type='text' value= 0></td>"
			+"<td name='no2g3gSites'><input  style='width:250px;' type='text' value= 0></td>"
			+"<td name='profitAndLoss2g3g'><input style='width:250px;' type='text'value= 0></td>"
			+"<td name='no2g3g4gSites'><input  style='width:250px;' type='text' value=0></td>"
			+"<td name='profitAndLoss2g3g4g'><input type='text'  style='width:250px;' value= 0></td>"
			+"<td name='totalSites'><input type='text' style='width:250px;' value= 0 ></td>"
			+"<td name='totalSumProfit'><input type='text' style='width:250px;' value= 0 ></td>"
			+"<td name='id' ><input name='id'type='text' style='width:250px;'value= 0 readonly></td></tr>";
		    $("#yaratab > tbody").append(markup);

		    var myDiv = document.getElementById("yaratab");
    	    myDiv.scrollTop = myDiv.scrollHeight;
    	    
         		//auto complete for Itemcode   
				$('#yaratab tr td input').change(function() {
					
					var cell = $(this).val();
					var column_name = $(this).parent().attr('name');
					
		            if ((column_name == 'no2gSites') ) {
			            
		                  // validate Qty
							 if (($. isNumeric(cell))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
							 
					}
		            if ((column_name == 'profitAndLoss2g') ) {
		                  // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
							 
					}
		            if ((column_name == 'no2g3g4gSites') ) {
		                  // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
					}
		            if ((column_name == 'no2g3gSites') ) {
		                  // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}

					}
		        
		            if ((column_name == 'profitAndLoss2g3g') ) {
		                  // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
					}


		            if ((column_name == 'profitAndLoss2g3g4g') ) {
		                  // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
					}

		            if ((column_name == 'totalSites') ) {
			            
		                  // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
					}

		            if ((column_name == 'totalSumProfit') ) {
			            
		                  // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
					}
					
				});
	       
      });



		$('body').on('click', '#selectAll', function  () {
			   
				if ($(this).hasClass('allChecked')) {
					
				$('input[type="checkbox"]', '#yaratab').prop('checked', false);
				
					}
				
				 else {
					 
					$('input[type="checkbox"]', '#yaratab').prop('checked', true);
					
					}
				
					$(this).toggleClass('allChecked');
			
				})

		function selectALLrows () {
			
			if ($(this).hasClass('allChecked')) {
				
     				$('input[type="checkbox"]', '#yaratab').prop('checked', false);
     				
  						} 
				else {
					
   						$('input[type="checkbox"]', '#yaratab').prop('checked', true);
   						
   						}
				
   						$(this).toggleClass('allChecked');
   						
					}


        //to select all cjeckbox or unselect them all from top table of area
		$('body').on('click', '#selectAllIn', function  () {
	   
			if ($(this).hasClass('allChecked')) {
				
			$('input[type="checkbox"]', '#areaTab').prop('checked', false);
			
				} 
			else {
				
				$('input[type="checkbox"]', '#areaTab').prop('checked', true);
				
				}
			
				$(this).toggleClass('allChecked');
		
			})


//when click on save or delete to select all rows by default
	


		 $(".delete-row").click(function(){
			 
			 slctDelAreaFinance=[] ;  
			 
			 $("#yaratab > tbody").find('input[name="record"]').each(function(){
				 
		        if($(this).is(":checked")){
			        
		        	slctDelAreaFinance.push( $(this).parent().parent().children('td[name="id"]').children('input').val());
		             
		         }
		         
			 });
			 
		   	for (i = 0; i <= slctDelAreaFinance.length; ++i) {
			            //delete from screen
			            // check if you select rows to save or update
			           if (slctDelAreaFinance.length == 0) {
				           
			            	alert(' Select Row(s) to Delete');
			            	
			          		return false;
			          }
		   	}
			 
		     $("#yaratab > tbody").find('input[name="record"]').each(function(){
			     
		         if($(this).is(":checked")){
			         
		         $(this).parents("tr").remove();
		            
		         }
		         
		     });
		});

		// end delete row
	 $("#selectnav").autocomplete({
			
		    source: function(request, response) {
			    
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllAreas',
		                 data: {
								"areaId" : $("#selectnav").val(),
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
		         }, minLength:0, maxShowItems: 40, scroll:true,

					select: function(event, ui) {
						
						this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
						var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+(ui.item[0])+"&NavAction=2";
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
	
	    //function to get selected rows for save or delete
		function getselectedrows () {
			
			dict = [];
			
			$("#yaratab > tbody").find('input[name="record"]').each(function(){
				
			var StartDate =($(this).parent().parent().children('td[name="startDate"]').children('input').val());
			
			  if (StartDate == "") {  
				   
				    alert('Start Date  cannot be null in Table at Row: ' +($(this).parent().parent().index()+1));
				    alertFlag ='1';
				     
				   return false;
				  
			    }

			  var EndDate =($(this).parent().parent().children('td[name="endDate"]').children('input').val());
			
			  if (EndDate == "") {  
				   
				    alert('End Date  cannot be null in Table at Row: ' +($(this).parent().parent().index()+1));
				    alertFlag ='1';
				    
				   return false;
				  
			    }
			
				if($(this).is(":checked")){
					
				    dict.push({
					    
					     "StartDate" : $(this).parent().parent().children('td[name="startDate"]').children('input').val(),
					     "EndDate" : $(this).parent().parent().children('td[name="endDate"]').children('input').val(),
					     "2GSites" : $(this).parent().parent().children('td[name="no2gSites"]').children('input').val(),
						 "ProfitLoss2G" : $(this).parent().parent().children('td[name="profitAndLoss2g"]').children('input').val(),
						 "2G3GSites" : $(this).parent().parent().children('td[name="no2g3gSites"]').children('input').val(),
						 "ProfitLoss2G3G" : $(this).parent().parent().children('td[name="profitAndLoss2g3g"]').children('input').val(),
						 "2G3G4GSites" : $(this).parent().parent().children('td[name="no2g3g4gSites"]').children('input').val(),
						 "ProfitLoss2G3G4G" : $(this).parent().parent().children('td[name="profitAndLoss2g3g4g"]').children('input').val(),
						 "TotalSites" : $(this).parent().parent().children('td[name="totalSites"]').children('input').val(),
						 "SumProfitLoss" : $(this).parent().parent().children('td[name="totalSumProfit"]').children('input').val(),
						 "Id":$(this).parent().parent().children('td[name="id"]').children('input').val(),

					    });	

                }
				  
            	});

		}

		
		
	    //function to get selected rows for save or delete
		function getselectedrowsForArea () {
			
			dictArea = [];
			
			$("#areaTab > tbody").find('input[name="record"]').each(function(){

				   
				    dictArea.push({
					    
					    "Latitude" : $(this).parent().parent().children('td[name="arealatt"]').children('input').val(),
					    "Longitude" : $(this).parent().parent().children('td[name="arealng"]').children('input').val(),
					    "areaBorderID" : $(this).parent().parent().children('td[name="seqId"]').children('input').val(),
					    "sequence" : $(this).parent().parent().children('td[name="sequence"]').children('input').val(),

					     });
				     	
									
            	});
              
		}
		function sortSequence () {
			
			var i = 0 ;
			var rowCount = $("#areaTab > tbody").children().length;

			for (i = 0;i<rowCount;i++){
				
				   $("#areaTab > tbody tr").eq(i).find('input[name=sequence]').val(i+1);

			}
		      
		}
        function saverowsintables (){

            var AreaCoordinates = document.getElementById("AreaCoordinates").value;

            
            if ('${listAreaBorder}' == "addNew" ){
                
      	        document.getElementById("AreaCoordinates").value = "1"
      	        AreaCoordinates="1";

                }

            var token =  $('input[name="csrfToken"]').attr('value'); 
		     	$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/AreaFormSave",
					dataType : "json",
					headers: {
	                      'X-CSRFToken': token 
	                  },
					data : {
						
						"status":$("#ordstat").val(),
						"type": '${docStatus}',
						"dictParameter" : dict,
						"dictParameterArea" : dictArea,
						"AreaID" :$("#areaId").val(),
						"creationDate" : $("#createdate").val(),
						"lastModifieddate" : $("#lstmodifdate").val(),
						"AreaName" :$("#areaname").val(),
						"Region" :$("#regionID").val(),
						"email": $("#email").val(),
						"password":$("#password").val(),
				  	    "emailTo": $("#emailTo").val(),
					    "ccmail": $("#ccmail").val(),
					    "subject": $("#subject").val(),
					    "message": $("#message").val(),
					    "CoordinatesChange":AreaCoordinates,
					    "slctDelAreaBorder" : slctDelAreaBorder,
					    "slctDelAreaFinance": slctDelAreaFinance,
					    "streched":streched,
					    
				},
					success : function(data) {
						var areaID =data.AREAID;
						var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+areaID+"&NavAction=2";
						location.replace(param);
					
					},
					error : function(error) {
						
						console.log("The error is " + error);
						
					}
				});

        }

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
});


</script>
<!--   <script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&libraries=drawing" async defer>
	</script> -->
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
	async defer></script>


</html>