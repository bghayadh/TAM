<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>
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
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
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
<link
	href='${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.min.css'
	rel='stylesheet' type='text/css'>
<script
	src='${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.min.js'
	type='text/javascript'></script>
<script
	src="${pageContext.request.contextPath}/resources/js/warehouseF_BoqPopup.js"></script>


<style>
/*Doaa's popup Email Div'*/
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

.hide-row {
	display: none;
}
/* Set the size of the div element that contains the map */
.panel-body {
	border: 2px solid #808080;
	border-radius: 30px 15px 15px 5px;
}

#map {
	height: 600px;
	width: 100%;
}

select {
	width: 260px;
}
</style>
<style>
#mapText {
	border: hidden;
	width: 110px;
	height: 25px;
	border: hidden;
	background-color: #87CEEB;
	margin-left: 20px;
	text-align: center;
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

.table thead th {
	vertical-align: middle;
}

.table thead th div {
	width: 200px !important;
}

#warehouseInfo_tbl, #warehouseInfo_tbl td {
	padding: 0;
	margin: 0;
	vertical-align: top;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.left_col {
	height: 60px
}

.siteimg{
    display:inline;
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
 background-color: #FFD966 !important;
    color: #00757c !important;
    }
</style>

</head>
<body>
	<script type="text/javascript">
</script>

	<%-- 	<c:set var = "page" value = "inventory"/> --%>

	<%-- 	<%@ include file="header.html" %> --%>
	<c:set var="pg" value="inventory" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>

	<!--  end of general head page -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<p></p>
			</div>

		</div>


		<div class="row second">
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width: 170px;">Warehouse
						</span> <input type="text" id="wareID" readonly value="${wareID}"
							class="form-control text-input" />
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
						<span class="input-group-text">Other WareHouse</span> 
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
						<span class="input-group-text" style="width: 170px;"">Created
							Date</span> <input type="text" id="wcreationDate" readonly
							value="${wcreationDate}"
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
						<span class="input-group-text" style="width: 210px;">Last
							Modify Date</span> <input type="text" id="wlastModifieddate" readonly
							value="${wlastModifieddate}"
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
							style="margin-left: 3px;width: 51px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 51px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnLast" href="#"
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
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: 0px;">
					<li class="tab" class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">INFORMATION</a></li>



					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-passive-tab" data-toggle="tab"
						href="#custom-tabs-one-passive" role="tab"
						aria-controls="custom-tabs-one-passive" aria-selected="false"
						style="color: gold;">PASSIVE</a></li>

           <li class="nav-item">
            <a class="nav-link" id="custom-tabs-Inventory-tab" data-toggle="tab"
               href="#custom-tabs-Inventory" role="tab" aria-controls="custom-tabs-Inventory"
               aria-selected="false" style="color: gold;">BoQ</a>
        </li>
        
        
        
        <li class="nav-item">
            <a class="nav-link" id="custom-tabs-node-tab" data-toggle="tab"
               href="#custom-tabs-node" role="tab" aria-controls="custom-tabs-node"
               aria-selected="false" style="color: gold;">NODE</a>
        </li>
        
        
         <li class="nav-item">
            <a class="nav-link" id="custom-tabs-cell-tab" data-toggle="tab"
               href="#custom-tabs-cell" role="tab" aria-controls="custom-tabs-cell"
               aria-selected="false" style="color: gold;">CELL</a>
        </li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-profile-tab" data-toggle="tab"
						href="#custom-tabs-one-finance" role="tab"
						aria-controls="custom-tabs-one-profile" aria-selected="false"
						style="color: gold;">FINANCE</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-sim-tab" data-toggle="tab"
						href="#custom-tabs-one-sim" role="tab"
						aria-controls="custom-tabs-one-sim" aria-selected="false"
						style="color: gold;">SIM CARDS</a></li>
						
					<!-- IMAGE TAB -->
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
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					id="warehouseInfo_tbl">
					<tr>
						<td width=20% valign="top" class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Warehouse Name</span> <input
										type="text" id="warepname" value="${warehouseName}"
										class="form-control text-input" />
								</div>
							</div>
						</td>
						<td rowspan=8></td>
						<td rowspan=8 valign="top">

							<div class="panel-body" style="height: 520px; margin-left: 10%">

								<div class="card-body">
									<!-- style='border:hidden;' -->
									<div class="btn-toolbar"
										style="text-align: left; margin-bottom: 5px; margin-top: auto;">
										<div class="btn-group flex-wrap" data-toggle="buttons"
											style="row-gap: 2px;">
											<div class="top-btn-filter">
												<button id="CoordsButton" name="CoordsButton"
													class="buttonTog">
													<i class="fas fa-map"></i> Get Coordinates
												</button>
											</div>
											<div class=" top-btn-filter">
												<button id="deleteCoordBtn" name="deleteCoordBtn"
													class="buttonTog" style='margin-left: 10px;'>
													<i class="fa fa-trash"></i> Delete Area
												</button>
											</div>
											<div id="txtDiv">
												<input id="mapText" type='text' disabled />

											</div>
										</div>
										<div id="map" style='height: 440px;'></div>
									</div>
									<div id="msg"></div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend" data-target-input="nearest">
									<div class="input-group-text" style="margin-right: 20px;">
										<input style="margin-left: 10px; margin-right: 10px"
											type="checkbox" id="warsite" ${wareSite}> <span>Site</span>
									</div>
									<span class="input-group-text">Site ID</span> <input
										type="text" id="siteId" value="${siteId}"
										class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Supported Technologies:</span>
									<div class="checkboxes"
										style="margin-top: 10px; margin-left: 10px;">
										<span> <input type="checkbox" id="techg2g" ${tech2G} />
											2G <input type="checkbox" id="techg3g" ${tech3G} /> 3G <input
											type="checkbox" id="techg4g" ${tech4G} /> 4G <input
											type="checkbox" id="techg5g" ${tech5G} /> 5G
										</span>
									</div>
								</div>
							</div>
						</td>
					</tr>

					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Combination Technology</span> <input
										type="text" id="warecomb" value="${wareCombination}"
										class="form-control text-input" readonly />
								</div>
							</div>
						</td>

					</tr>
					<tr>




						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Longitude</span> <input
										type="text" id="warelng" value="${wareLong}"
										class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>




					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Latittude</span> <input
										type="text" id="warlatt" value="${wareLat}"
										class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>



					<tr>
						<td class="left_col">

							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Site Mode</span> <input
										type="text" id="siteMode" value="${siteMode}"
										class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Cluster</span> <input
										type="text" id="clusterID" style="width: 565px; height: 38px;"
										class="form-control text-input ui-widget ui-widget-content ui-corner-all" />
								</div>
							</div>
						</td>
					</tr>

					<tr>
						<td class="left_col">
							<div class="form-group">

								<div class="input-group-prepend">
									<span class="input-group-text">Region</span> <input type="text"
										id="regionID" value="${RegionID}" style="width: 565px"
										class="form-control text-input" />
								</div>
							</div>

						</td>
					</tr>



					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Area</span> <input type="text"
										id="areaID" style="width: 580px; height: 38px;"
										class="form-control text-input ui-widget ui-widget-content ui-corner-all" />
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">City </span> <input type="text"
										id="warcity" value=
										"${wareCity}" class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Address</span> <input
										type="text" id="wareAddress" value="${wareAddress}"
										class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
			<div class="tab-pane fade" id="custom-tabs-one-passive"
				role="tabpanel" aria-labelledby="custom-tabs-one-passive-tab">
				<p></p>
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Existing/ New Town</span> <select
									id="existing_newtown" class="form-control">
									<option value=""
										<c:if test = "${existing_newtown ==''}"> selected </c:if>></option>
									<option value="E"
										<c:if test = "${existing_newtown =='E'}"> selected </c:if>>E</option>
									<option value="NT"
										<c:if test = "${existing_newtown =='NT'}"> selected </c:if>>NT</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Showcase/ Non Showcase</span> <select
									id="showcase" class="form-control">
									<option value=""
										<c:if test = "${showcase ==''}"> selected </c:if>></option>
									<option value="SC"
										<c:if test = "${showcase =='SC'}"> selected </c:if>>SC</option>
									<option value="NSC"
										<c:if test = "${showcase =='NSC'}"> selected </c:if>>NSC</option>
								</select>
							</div>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Site Principal Owner</span> <input
									type="text" id="siteOwner" value="${siteOwner}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Tower Co ID</span> <input
									type="text" id="towerCoID" value="${towerCoID}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Tower Type</span> <select
									id="towerType" class="form-control">
									<option value=""
										<c:if test = "${towerType ==''}"> selected </c:if>></option>
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
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Tower Height (m)</span> <input
									type="number" id="towerHeight" value="${towerHeight}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Building Height</span> <input
									type="number" id="buildingHeight" value="${buildingHeight}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Shared/ Non Shared</span> <select
									id="shared" class="form-control">
									<option value=""
										<c:if test = "${shared ==''}"> selected </c:if>></option>
									<option value="Shared"
										<c:if test = "${shared =='Shared'}"> selected </c:if>>Shared</option>
									<option value="Non-Shared"
										<c:if test = "${shared =='Non-Shared'}"> selected </c:if>>Non-Shared</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">ICR Category (ICR/MORAN)</span> <select
									id="icrCategory" class="form-control">
									<option value=""
										<c:if test = "${icrCategory ==''}"> selected </c:if>></option>
									<option value="NO"
										<c:if test = "${icrCategory =='NO'}"> selected </c:if>>NO</option>
									<option value="ICR"
										<c:if test = "${icrCategory =='ICR'}"> selected </c:if>>ICR</option>
									<option value="MORAN"
										<c:if test = "${icrCategory =='NO'}"> selected </c:if>>MORAN</option>
								</select>
							</div>
						</div>
					</div>

					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Transmission</span> <select
									id="transmission" class="form-control">
									<option value=""
										<c:if test = "${transmission ==''}"> selected </c:if>></option>
									<option value="Fiber"
										<c:if test = "${transmission =='Fiber'}"> selected </c:if>>Fiber</option>
									<option value="MW"
										<c:if test = "${transmission =='MW'}"> selected </c:if>>MW</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Hub Site</span> <input
									type="text" id="hubSite" value="${hubSite}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

				</div>

				<div class="row">
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-text">
								<input style="margin-right: 10px;" type="checkbox"
									id="shelterCheck" ${wshelter}> <span>Shelter(With
									or Without)</span>
							</div>
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Shelter ID</span> <input
									type="text" id="shelterID" value="${shelterID}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Shelter</span> <input type="text"
									id="shelter" value="${shelter}" class="form-control text-input" />
							</div>
						</div>
					</div>

				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Shelter Type</span> <input
									type="text" id="shelterType" value="${shelterType}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Shelter Vendor</span> <input
									type="text" id="shelterVendor" value="${shelterVendor}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Location Notes</span> <input
									type="text" id="locationNotes" value="${locationNotes}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="custom-tabs-one-finance"
				role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab">
				<p></p>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total initial Cost</span> <input
									type="text" readonly value="${InitialCost}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Accumulated
									Depreciation</span> <input type="text" readonly value="${accumPer}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Total Net Cost</span> <input
									type="text" readonly value="${totalNetCost}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>

				<div>
					<form>
						<div class="table-responsive-sm">
							<table id="bisotab"
								class="table table-striped table-bordered table-sm"
								style="display: block; height: 200px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<th>
											<button type="button" id="selectAll" class="main">
												<span class="sub"></span>Select
											</button>
										</th>
										<th><div>Start Date</div></th>
										<th><div>End Date</div></th>
										<th><div>Population</div></th>
										<th><div>Technologies</div></th>
										<th><div>2G Utilization</div></th>
										<th><div>3G Utilization</div></th>
										<th><div>4G Utilization</div></th>
										<th><div>5G Utilization</div></th>
										<th><div>Availability 2G</div></th>
										<th><div>Availability 3G</div></th>
										<th><div>Availability 4G</div></th>
										<th><div>Availability 5G</div></th>
										<th><div>Gross ADS</div></th>
										<th><div>Count of SSO</div></th>
										<th><div>Customer Base</div></th>
										<th><div>Data</div></th>
										<th><div>Voice</div></th>
										<th><div>SMS Revenue</div></th>
										<th><div>Gross Revenue</div></th>
										<th><div>Data Traffic</div></th>
										<th><div>Total SMS Traffic OG</div></th>
										<th><div>Total Voice Traffic OG</div></th>
										<th><div>Total Voice Traffic IC</div></th>
										<th><div>Total SMS Traffic IC</div></th>
										<th><div>Total OPEX Monthly</div></th>
										<th><div>Profit and Loss</div></th>
										<th><div>ID</th>
									</tr>
								</thead>
								<tbody>


								</tbody>
							</table>
						</div>

						<!--  Text used to indicate row index -->
						<input type="button" class="add-row" value="Add Row"
							onclick="addrowsFinance('addRowFinance')">
						<button type="button" class="delete-row">Delete Row</button>
						<input type="text" id="RowToDeleteFinance" hidden value="">

					</form>
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
																<span class="input-group-text">Population</span> <input
																	type="text" id="popuppopulation"
																	class="form-control text-input" value="0"
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Technologies</span>
																<div style='width: 180px; margin-left: 10px;'>

																	<input style='margin-top: 10px;' type='checkbox'
																		id='popuptech_2g' /> 2G <input
																		style='margin-top: 10px;' type='checkbox'
																		id='popuptech_3g' /> 3G <input
																		style='margin-top: 10px;' type='checkbox'
																		id='popuptech_4g' /> 4G <input
																		style='margin-top: 10px;' type='checkbox'
																		id='popuptech_5g' /> 5G
																</div>
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
																<span class="input-group-text">2G Utilization</span> <input
																	type="text" id="popup2gUtilization"
																	class="form-control text-input" value="0"
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">3G Utilization</span> <input
																	type="text" id="popup3gUtilization"
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
																<span class="input-group-text">4G Utilization</span> <input
																	type="text" id="popup4gUtilization"
																	class="form-control text-input" value="0"
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">5G Utilization</span> <input
																	type="text" id="popup5gUtilization"
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
																<span class="input-group-text">Availability 2G</span> <input
																	type="text" id="popupAvailability2G"
																	class="form-control text-input" value="0"
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Availability 3G</span> <input
																	type="text" id="popupAvailability3G"
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
																<span class="input-group-text">Availability 4G</span> <input
																	type="text" id="popupAvailability4G"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Availability 5G</span> <input
																	type="text" id="popupAvailability5G"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">Gross ADS</span> <input
																	type="text" id="popupgrossADS"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Count of SSO</span> <input
																	type="text" id="popupcountOfSSO"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">Customer Base</span> <input
																	type="text" id="popupcustomerBase"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Data</span> <input
																	type="text" id="popupdata"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">Voice</span> <input
																	type="text" id="popupvoice"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">SMS Revenue</span> <input
																	type="text" id="popupsmsRevenue"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">Gross Revenue</span> <input
																	type="text" id="popupgrossRevenue"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Data Traffic</span> <input
																	type="text" id="popupdataTraffic"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">Total SMS Traffic
																	OG</span> <input type="text" id="popuptotalSMSTrafficOG"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Total Voice
																	Traffic OG</span> <input type="text"
																	id="popuptotalVoiceTrafficOG"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">Total Voice
																	Traffic IC</span> <input type="text"
																	id="popuptotalVoiceTrafficIC"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Total SMS Traffic
																	IC</span> <input type="text" id="popuptotalSMSTrafficIC"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">Total OPEX
																	Monthly</span> <input type="text" id="popuptotalOpexMon"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Profit and Loss</span> <input
																	type="text" id="popupProfitAndLoss"
																	class="form-control text-input" value=""
																	style="width: 674px; height: 37px;" />

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
																<span class="input-group-text">ID</span> <input
																	type="text" id="popupProfitLossID" readonly
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

				<!--end of popupShops  -->

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
				<div><form><div class="siteimg" id="shopimage" style=" display: inline; "></div></form></div>
				<p></p>
			</div>
			
			 <div class="tab-pane fade" id="custom-tabs-node" role="tabpanel"
				aria-labelledby="custom-tabs-node-tab">

				<div style="height:30px;"></div>
				
				<div style="height:20px;"></div>
 				
 <table id ="NodeTable" class="table table-striped table-bordered table-sm" style="display:block; height:700px; overflow-y: auto;">
						        <thead>
						            <tr class="fixed-headerr">
						               
						                <th>Node Id</th>
						                <th>Node Name</th>
						                <th>Node Type</th>
						                 <th>Node Model</th>
						                <th>Domain</th>
						                <th>Vendor</th>
						                <th>Supported Technology</th>
						                
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						        
						    </table>

			</div>
			
			
			
			
			
			 <div class="tab-pane fade" id="custom-tabs-cell" role="tabpanel"
				aria-labelledby="custom-tabs-cell-tab">

				<div style="height:30px;"></div>
				
				<div style="height:20px;"></div>
 				
 <table id ="CellTable" class="table table-striped table-bordered table-sm" style="display:block; height:700px; overflow-y: auto;">
						        <thead>
						            <tr class="fixed-headerr">
						               
						                <th>cell Id</th>
						                <th>cell Name</th>
						                 <th>cell Technology</th>
						                
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						        
						    </table>

			</div>
			<div class="tab-pane fade" id="custom-tabs-Inventory" role="tabpanel"
				aria-labelledby="custom-tabs-Inventory-tab">
				
			<div><form><div style="height:10px"></div>
					    <h4>Inventory</h4>
						    <table id ="inventorytab" class="table table-striped table-bordered table-sm" style="display:block; height:400px; overflow-y: auto;">
						       <thead><tr class="fixed-headerr">						                
						       			<th>Item Code</th>
						                <th>Item Name</th>
						                <th>Item Model</th>
						                <th>Item Part Number</th>
						                <th> Quantity </th>
						                <th>Total Initial Cost</th>						                
						                <th>Total Depreciation </th>
										<th>Total Net Cost</th>						                
						                </tr></thead></table>
			</form></div>

				  <table id ="inventorytab"  class="table table-striped table-bordered table-sm" style="display:block;  overflow-y: auto;">
						       <thead>
						            <tr class="fixed-headerr">
						                
						                <th>Total Quantity</th>
						                <th><input id="quan" style="width:240px;" type="text" readonly /></th>
						                <th>Total Initial Cost</th>
						                <th><input id="initial" style="width:240px;" type="text" readonly /></th>
						                <th>Total Depreciation </th>
						                <th><input id="dep" style="width:240px;" type="text" readonly /></th>
						                <th>Total Net Cost</th>
						                   <th><input id="net" style="width:240px;" type="text" readonly /></th>
						                   </tr></thead></table>
				 		    <div class="table-responsive-sm"> 
				 		   
						               
					    <div style="height:5px"></div>
					    <h4>Network</h4>
						    <style>
    

    #inventorytab2 td {
        padding: 2px; /* Add some spacing between cells */
        width:150px;
    }
</style>

<table id="inventorytab2" style="display:block; height:200px;   border-collapse: collapse; overflow-y: auto;">
    <tbody>
        <tr>
            <td><b>Site Name</b></td>
             </tr>
        <tr>
            <td> <b>Nodes</b></td>
          </tr>
        <tr>
            <td><b>Node Type</b></td>
         </tr>
        <tr>
            <td><b>IDU</b></td>
         </tr>
        <tr>
            <td><b>SRansBs</b></td>
          </tr>
        <tr>
            <td><b>G-cell</b></td>
          </tr>
        <tr>
            <td><b>L-cell</b></td>
        </tr>
        <tr>
            <td><b>U-cell</b></td>
         </tr>
    </tbody>
</table>
						    </div></form>
				
				
				
				
				
				
				
				
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
						class="btn btn-primary BtnActive" >
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




<script>

boqArray = [];

$(document).ready(function () {
     $("#custom-tabs-Inventory-tab").on("click", function () {
         BoQ();
        $("#custom-tabs-Inventory").tab("show");
    });

 
});

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


$("#ordstat").click(  function() {

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
})
 
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
	 $("#deleteCoordBtn").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('activated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	
		 $("#CoordsButton").click(  function() {	
	   
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
 
//function to display image in pop up 
 function show(name) {

			$("#imgpopUpDiv").fadeIn();

		    $("#imagedisplay").append("<img src= /SimRegPhotos/SitePic/" + $("#wareID").val() +"/"+ name + " width = '350' height = '350' />");
              		
			
			$("#imgcancelButton").on("click", function (e) {
			$("#imgpopUpDiv").fadeOut();
			$("#imagedisplay").empty();
			});
	
	
}

 $(document).ready(function(){

	 /////fill the images	 
	 
	 
	 			 if ('${listSiteImage}' != "addNew") {

				 SiteImageName = [];
				 SiteImageName = ${listSiteImage};
				 var imgName;

				 for(i =0 ; i<SiteImageName.length ; i++){
					 imgName = SiteImageName[i];


					 $(".siteimg").each( function() {	
						 $(this).append("<img src= /SimRegPhotos/SitePic/" + $("#wareID").val() +"/"+ imgName + " width = '150' height = '150' style='padding:5px;' onClick=\"show(\'" + imgName + "\');\" />");
					  });			
					 
				 }

		      }
	 
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
								//"password":request.value,
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
				
		 //////////////////////////////////////////////////////////////////////////////		  	

	var Errorflag; 
	var area_ID = '${areaID}';
	var areaName = '${areaName}';
	var area;
	if(area_ID == "")
		area = "";
	else
		area = area_ID +":"+ areaName;

	
	var Region_ID = '${regionID}';
	var RegionName = '${regionName}';
	var region;
	if(Region_ID == "")
		region = "";
	else
		region = Region_ID +":"+ RegionName;


	var clusterID = '${clusterID}';
	var clusterName = '${clusterName}';
	var cluster;
	if(clusterID == "")
		cluster = "";
	else
		cluster = clusterID +":"+ clusterName;

	$("#areaID").val(area);
	$("#regionID").val(region);
	$("#clusterID").val(cluster)
	var flagFrom;


	 $("#regionID").autocomplete({
			
		    source: function(request, response) {
			    
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllRegions',
		                 data: {
								"regionID" : $("#regionID").val(),
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.listRegions);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 40, scroll:true,

					select: function(event, ui) {
						regionID.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
	 		    	return $('<li class="each">').data( "item.autocomplete", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	                 item[0] + '</span><br><span class="desc">' +
	                 item[1] + '</span></div></li>').appendTo(ul);
			};
					$("#regionID").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});   //// ENd of Autocomplete for Area ID


////Start Autocomplete for Area ID
	$("#areaID").autocomplete({
	
	    	source: function(request, response) {
		    
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllAreas',
	                 data: {
							"areaId" : $("#areaID").val(),
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
					areaID.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
					return false;
				}
			}).autocomplete("instance")._renderItem = function(ul, item) {
 		    	return $('<li class="each">').data( "item.autocomplete", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                 item[0] + '</span><br><span class="desc">' +
                 item[1] + '</span></div></li>').appendTo(ul);
		};
				$("#areaID").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});   //// ENd of Autocomplete for Area ID
$("#clusterID").autocomplete({					
	source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: '${pageContext.request.contextPath}/GetAllClusters',
				data: {
					"clusterID" : $("#clusterID").val(),
				},
				dataType: "json",
				success: function (data) {
					if (data != null) {
						response(data.listCluster);
					}
				},
				error: function(result) {
					alert("Error");
				}
			});
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		return false;
	}
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $('<li class="each">').data( "item.autocomplete", item )
		.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		item[0] + '</span><br><span class="desc">' +
		item[1] + '</span></div></li>').appendTo(ul);
};
$("#clusterID").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});   //// ENd of Autocomplete for Area ID
///////////////////////////


if('${SelectedIndex}' != "addNew"){
				var SelectedIndex = ${SelectedIndex};
				if('${wareCount}' != "addNew"){

					
			var wareCount = ${wareCount};
			
			if(($("#wareID").val()) != "" && ($("#wareID").val()) != null){

			if(SelectedIndex === wareCount){
				
        		document.getElementById("btnLast").style.opacity = 0.5;
        		$("#btnLast").hasClass("disabled");
        		document.getElementById("btnLast").style.pointerEvents = "none";
        		
        		document.getElementById("btnNexta").style.opacity = 0.5;
        		document.getElementById("btnNexta").style.pointerEvents = "none";

				
				$("#btnNexta").hasClass("disabled");
				
				}else{
					
					if(!$("#btnNexta").hasClass("disabled")){
						
						$("#btnNext").click(function(){
							
							var param ="${pageContext.request.contextPath}/WarehouseFormView?wareID="+$("#wareID").val()+"&NavAction=1";

							window.location.href =param;
				
						});
			
					}
					if(!$("#btnLst").hasClass("disabled")){
        				
        				$("#btnLst").click(function(){
        					
							var param ="${pageContext.request.contextPath}/WarehouseFormView?wareID="+$("#wareID").val()+"&NavAction=4";
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
						
						var param ="${pageContext.request.contextPath}/WarehouseFormView?wareID="+$("#wareID").val()+"&NavAction=0";
						window.location.href =param;
						
					 });
				}
				$("#btnFrst").click(function(){

        			if(!$("#btnFrst").hasClass("disabled")){
        					
						var param ="${pageContext.request.contextPath}/WarehouseFormView?wareID="+$("#wareID").val()+"&NavAction=3";
        				window.location.href =param;
        						
        				}
        				 });

			}
			
			}}
		}
			$("#label-1").text((SelectedIndex)+"/"+wareCount);

			 $("#selectnav").autocomplete({
	    			
	    		    source: function(request, response) {
	    			    
	    		             $.ajax({
	    		                 type: "GET",
	    		                 contentType: "application/json; charset=utf-8",
	    		                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
	    		                 data: {
	    								"warehouseName" : $("#selectnav").val(),
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
	    						
	    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
	    						var param ="${pageContext.request.contextPath}/WarehouseFormView?wareID="+(ui.item[0])+"&NavAction=2";
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
	    	


});

  
				
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

if ( '${docStatus}' != "addNew") {

//    boqArray = [];
    boqArray = ${listWareProLoss};
	
	for (i = 0;i<boqArray.length;i++)
	{

		startDate = boqArray[i].startDate;

		var d = new Date(startDate);
		var mm = ("0" + (d.getMonth() + 1)) .slice(-2);
		var dd = ("0" + d.getDate()).slice(-2);
		var yy = d.getFullYear();
		var start_date = yy + '-' + mm + '-' + dd; //(US)
		endDate = boqArray[i].endDate;
		var d = new Date(endDate);
		var mm = ("0" + (d.getMonth() + 1)) .slice(-2);
		var dd = ("0" + d.getDate()).slice(-2);
		var yy = d.getFullYear();
		var end_date = yy + '-' + mm + '-' + dd; //(US)
		
		var tech_value = "<div style='width:180px'>";
		if(boqArray[i].tech2G == 1)
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_2g' checked /> 2G ";
		else
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_2g' /> 2G ";


		if(boqArray[i].tech3G == 1)
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_3g' checked /> 3G ";
		else
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_3g' /> 3G ";


		if(boqArray[i].tech4G == 1)
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_4g' checked /> 4G ";
		else
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_4g' /> 4G ";


		if(boqArray[i].tech5G == 1)
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_5g' checked /> 5G ";
		else
			tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_5g' /> 5G ";

	    tech_value += "</div>";
		
		var itemRow = "<tr>";
		 itemRow= itemRow +"<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopFinance(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			
        itemRow =itemRow + "<td name='startDate'><input type='date' style='width:200px;' value='" + start_date +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='endDate'><input type='date' style='width:200px;'  value='" + end_date +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='population'><input type='text' value='" + boqArray[i].population +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='technologies'>"+tech_value +"</td>";
        itemRow =itemRow + "<td name='2gUtilization'><input type='text' value='" + boqArray[i].utilization2G +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='3gUtilization'><input type='text' value='" + boqArray[i].utilization3G +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td name='4gUtilization'><input type='text' value='" + boqArray[i].utilization4G +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td name='5gUtilization'><input type='text' value='" + boqArray[i].utilization5G +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td name='Availability2G'><input type='text' value='" + boqArray[i].availability2G +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td name='Availability3G'><input type='text' value='" + boqArray[i].availability3G +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td name='Availability4G'><input type='text' value='" + boqArray[i].availability4G +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='Availability5G'><input type='text' value='" + boqArray[i].availability5G +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='grossADS'><input type='text' value='" + boqArray[i].grossADS +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='countOfSSO'><input type='text' value='" + boqArray[i].countOfSSO +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='customerBase'><input type='text' value='" + boqArray[i].custBase +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='data'><input type='text' value='" + boqArray[i].data +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='voice'><input type='text' value='" + boqArray[i].voice +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='smsRevenue'><input type='text' value='" + boqArray[i].smsRevenu +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='grossRevenue'><input type='text' value='" + boqArray[i].grossRevenu +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='dataTraffic'><input type='text' value='" + boqArray[i].datatraffic +"'class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='totalSMSTrafficOG'><input type='text' value='" + boqArray[i].totalSmsTraOG +"'class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='totalVoiceTrafficOG'><input type='text' value='" + boqArray[i].totalVoiceTraOG +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='totalVoiceTrafficIC'><input type='text' value='" + boqArray[i].totalVoiceTraIC +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='totalSMSTrafficIC'><input type='text' value='" + boqArray[i].totalSmsTraIC +"'class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='totalOpexMon'><input type='text' value='" + boqArray[i].totalOpexMon +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='ProfitAndLoss'><input type='text' value='" + boqArray[i].profitAndLoss +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='ProfitLossID'><input type='text' value='" + boqArray[i].id +"' readonly class='form-control text-input' ></td>";
        

        itemRow =itemRow + "</tr>";
        $("#bisotab > tbody").append(itemRow);
	}	
}

$("#custom-tabs-one-sim-tab").click(function(){


    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/GetSimCardsOnSite',
        dataType : "json",
        data: {
				"ID" : $("#wareID").val(),
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


$("#saveButton").click(  function() {
	 
	Errorflag ='0';																				 
	flagFrom = "save";
	 val =$("#wcreationDate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
        }
	 
	  // validate modified date cannot be null
	 val =$("#wlastModifieddate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
        }
	 
	  // validate type of Longitude
	 if (($. isNumeric( $('#warelng'). val()))== false) {
	 alert('Longitude is  not Numeric');
	 return false;}
	 
	  // validate type of attitude
	 if (($. isNumeric( $('#warlatt'). val()))== false) {
	 alert('Attitude is  not Numeric');
	 return false;}


	 selectALLrows();
	 getselectedrows ();
	if (Errorflag =='1') {
	     return false;
	 } 							 
	 saverowsintables();
	 
 })
 var slctdelWareFinance =[];
  $(".delete-row").click(function(){


		 
	  slctdelWareFinance=[] ;  
		 
		 $("#bisotab > tbody").find('input[name="record"]').each(function(){
			 
	        if($(this).is(":checked")){
		        
	        	slctdelWareFinance.push( $(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val());
	             
	         }
	         
		 });
		 
	   	for (i = 0; i <= slctdelWareFinance.length; ++i) {
		   	
		            //delete from screen
		            // check if you select rows to save or update
		           if (slctdelWareFinance.length == 0) {
			           
		            	alert(' Select Row(s) to Delete');
		            	
		          		return false;
		          }
	   	}
		 
	     $("#bisotab > tbody").find('input[name="record"]').each(function(){
		     
	         if($(this).is(":checked")){
		         
	         $(this).parents("tr").remove();
	            
	         }
	         
	     });
			            
        });
 
 $('body').on('click', '#selectAll', function  () {

	if ($(this).hasClass('allChecked')) {
			$('input[name="record"]', '#bisotab').prop('checked', false);
		} else {
			$('input[name="record"]', '#bisotab').prop('checked', true);
			}
			$(this).toggleClass('allChecked');
	
})
     					
 function selectALLrows () {
	if ($(this).hasClass('allChecked')) {
   				$('input[name="record"]', '#bisotab').prop('checked', false);
						} else {
 						$('input[name="record"]', '#bisotab').prop('checked', true);
 						}
 						$(this).toggleClass('allChecked');
}

function getselectedrows () {

	dict = [];
	slctDel = [];
	var dictObj = {};

	$("#bisotab > tbody").find('input[name="record"]').each(function(){

		if($(this).is(":checked")){


		    if(flagFrom === "delete"){
				slctDel.push($(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val());
		    }
		    else
		    {
		    	var tech_2g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
				var tech_3g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
				var tech_4g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
				var tech_5g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
				
			    if(tech_2g == true)
			    	tech_2g = 1;
			    else
			    	tech_2g = 0;

			    if(tech_3g == true)
			    	tech_3g = 1;
			    else
			    	tech_3g = 0;


			    if(tech_4g == true)
			    	tech_4g = 1;
			    else
			    	tech_4g = 0;

			    if(tech_5g == true)
			    	tech_5g = 1;
			    else
			    	tech_5g = 0;

				dictObj.tech_2g = tech_2g;
				dictObj.tech_3g = tech_3g;
				dictObj.tech_4g = tech_4g;
				dictObj.tech_5g = tech_5g;
				var stDate = $(this).parent().parent().children('td[name="startDate"]').children('input').val();
				stDate = Date.parse(stDate);

			     if (isNaN(stDate) == true) {
			          alert(' Start Date is not valid at Row: '+($(this).parent().parent().index()+1));
			          Errorflag ='1';
			          return false;
			     }
				
				dictObj.startDate = $(this).parent().parent().children('td[name="startDate"]').children('input').val();
				dictObj.endDate = $(this).parent().parent().children('td[name="endDate"]').children('input').val();
				dictObj.population = $(this).parent().parent().children('td[name="population"]').children('input').val();
				dictObj.Utilization2g = $(this).parent().parent().children('td[name="2gUtilization"]').children('input').val();
				dictObj.Utilization3g = $(this).parent().parent().children('td[name="3gUtilization"]').children('input').val();
				dictObj.Utilization4g = $(this).parent().parent().children('td[name="4gUtilization"]').children('input').val();
				dictObj.Utilization5g = $(this).parent().parent().children('td[name="5gUtilization"]').children('input').val();
				dictObj.Availability2G = $(this).parent().parent().children('td[name="Availability2G"]').children('input').val();
				dictObj.Availability3G = $(this).parent().parent().children('td[name="Availability3G"]').children('input').val();
				dictObj.Availability4G = $(this).parent().parent().children('td[name="Availability4G"]').children('input').val();
				dictObj.Availability5G = $(this).parent().parent().children('td[name="Availability5G"]').children('input').val();
				dictObj.grossADS = $(this).parent().parent().children('td[name="grossADS"]').children('input').val();
				dictObj.countOfSSO = $(this).parent().parent().children('td[name="countOfSSO"]').children('input').val();
				dictObj.customerBase = $(this).parent().parent().children('td[name="customerBase"]').children('input').val();
				dictObj.data = $(this).parent().parent().children('td[name="data"]').children('input').val();
				dictObj.voice = $(this).parent().parent().children('td[name="voice"]').children('input').val();
				dictObj.smsRevenue = $(this).parent().parent().children('td[name="smsRevenue"]').children('input').val();
				dictObj.grossRevenue = $(this).parent().parent().children('td[name="grossRevenue"]').children('input').val();
				dictObj.dataTraffic = $(this).parent().parent().children('td[name="dataTraffic"]').children('input').val();
				dictObj.totalSMSTrafficOG = $(this).parent().parent().children('td[name="totalSMSTrafficOG"]').children('input').val();
				dictObj.totalVoiceTrafficOG = $(this).parent().parent().children('td[name="totalVoiceTrafficOG"]').children('input').val();
				dictObj.totalSMSTrafficIC = $(this).parent().parent().children('td[name="totalSMSTrafficIC"]').children('input').val();
				dictObj.totalVoiceTrafficIC = $(this).parent().parent().children('td[name="totalVoiceTrafficIC"]').children('input').val();
				dictObj.totalOpexMon = $(this).parent().parent().children('td[name="totalOpexMon"]').children('input').val();
				dictObj.ProfitAndLoss = $(this).parent().parent().children('td[name="ProfitAndLoss"]').children('input').val();
				dictObj.ProfitLossID = $(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val();
				
				dict.push(dictObj);
				dictObj = {};

		    }	
					 					
        }
		
		else {
				slctDel = jQuery.grep(slctDel, function(value) {
					return value != $(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val();
					});
					
		  }				  
		  
    	}); 
}

function saverowsintables()
{
	var wareID = document.getElementById("wareID").value;
	 var wcreationDate = document.getElementById("wcreationDate").value;
	 var wlastModifieddate = document.getElementById("wlastModifieddate").value;
	 var warehouseName = document.getElementById("warepname").value;
	 var wareCity = document.getElementById("warcity").value;
	 var wareLong = document.getElementById("warelng").value;
	 var wareLat = document.getElementById("warlatt").value;
	 var checkBox = document.getElementById("warsite").value;
	 var siteId = document.getElementById("siteId").value;
	 var areaID = document.getElementById("areaID").value;
	 var regionID = document.getElementById("regionID").value;
	 var clusterID = document.getElementById("clusterID").value;
	 var wareAddress = document.getElementById("wareAddress").value;
	 var siteMode = document.getElementById("siteMode").value;
	 var status = document.getElementById("ordstat").value; 
	 var wsite ;
	 if (warsite.checked == true){
		  wsite = '1';
		  } else
			  {wsite = '0';}
	 var checkt2 = document.getElementById("techg2g");
	 var tech2 ;
	 if (techg2g.checked == true){
		 tech2 = '1';
		  } else
			  {tech2 = '0';}
	 
     var checkt3 = document.getElementById("techg3g");
	 var tech3 ;
	 if (techg3g.checked == true){
		 tech3 = '1';
		  } else
			  {tech3 = '0';}
			  
     var checkt4 = document.getElementById("techg4g");
	 var tech4 ;
	 if (techg4g.checked == true){
		 tech4 = '1';
	 } else
	 {tech4 = '0';}
	 
	 var tech5;
	 if (techg5g.checked == true){
		 tech5 = '1';
	 } else
	 {tech5 = '0';}

	 var hubSite = document.getElementById("hubSite").value;
	 var locationNotes = document.getElementById("locationNotes").value;
	 var shelter = document.getElementById("shelter").value;
	 var shelterType = document.getElementById("shelterType").value;
	 var shelterVendor = document.getElementById("shelterVendor").value;
	 var shelterID = document.getElementById("towerHeight").value;
	 var existing_newtown = document.getElementById("existing_newtown").value;
	 var showcase = document.getElementById("showcase").value;
	 var siteOwner = document.getElementById("siteOwner").value;
	 var towerCoID = document.getElementById("towerCoID").value;
	 var towerType = document.getElementById("towerType").value;
	 var towerHeight = document.getElementById("towerHeight").value;
	 var buildingHeight = document.getElementById("buildingHeight").value;
	 var shared = document.getElementById("shared").value;
	 var icrCategory = document.getElementById("icrCategory").value;
	 var transmission = document.getElementById("transmission").value;
	 
	 var wshelter;
	 if(shelterCheck.checked == true){
		 wshelter = 1;
		 } else
			 {wshelter = 0;}


		 $.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/WarehouseFormSave",
			dataType : "json",
			data : {
				"wareID" : $("#wareID").val(),
				"wcreationDate" : $("#wcreationDate").val(),
				"wlastModifieddate" : $("#wlastModifieddate").val(),
				"warehouseName" : $("#warepname").val(),
				"wareCity" : $("#warcity").val(),
				"wareLong" : $("#warelng").val(),  
				"wareLat" : $("#warlatt").val(),
				"siteMode" : $("#siteMode").val(),
				"wareSite" : wsite,	
				"siteId" : siteId, 
				"tech2G" : tech2,
				"tech3G" : tech3,
				"tech4G" : tech4,
				"tech5G" : tech5,
				"areaID" : areaID,
				"regionID" : regionID,
				"clusterID" : clusterID,
				"wareAddress" : $("#wareAddress").val(),
				"hubSite" : $("#hubSite").val(),	 
				"locationNotes" : $("#locationNotes").val(),
				"shelter" : $("#shelter").val(),	
				"shelterID" : $("#shelterID").val(), 
				"shelterType" : $("#shelterType").val(),	 
				"shelterVendor" : $("#shelterVendor").val(),
				"wshelter" : wshelter,
				"existing_newtown" : existing_newtown,
				"showcase" : showcase,
				"siteOwner" : siteOwner,
				"towerCoID" : towerCoID,
				"towerType" : towerType,
				"towerHeight" : towerHeight,
				"buildingHeight" : buildingHeight,
				"shared" : shared,
				"icrCategory" : icrCategory,
				"transmission" : transmission,
				"status": status,
				"dictParameter": dict,
				"email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(),
				"slctdelWareFinance": slctdelWareFinance , 
			},
			success : function(data) {

				$('#wlstmodifdate').val(data.wlastModifieddate);
				$("#wareID").val(data.wareID);
				var param ="${pageContext.request.contextPath}/WarehouseFormView?wareID="+$("#wareID").val()+"&NavAction=2";
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
}
							
 $("#deleteButton").click(  function() {

		var deleteArray = [];
		deleteArray.push($("#wareID").val());

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/WarehouseDelete",
			dataType : "json",
			data : {
				"wareID" : deleteArray
			},
			success : function(data) {
				location.reload();
				
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	    location.replace("${pageContext.request.contextPath}/WarehouseListView");
		 
 })
 

 
 
  
 if ('${Listnode}' != "addNew") {
	
 	boqNode = [];
 	boqNode = ${Listnode};
	
 console.log(boqNode);
 
 for (z = 0; z < boqNode.length; z++){
	 var nodeId=boqNode[z][0];
		if(nodeId==null){
			nodeId="";

			}
      var nodeName=boqNode[z][1];
			if(nodeName==null){
				nodeName="";

				}
	   var nodeType=boqNode[z][2];
	       if(nodeType==null){
	    	   nodeType="";
               }
	       var domain=boqNode[z][3];
	       if(domain==null){
	    	   domain="";
               }

	       var vendor=boqNode[z][4];
	       if(vendor==null){
	    	   vendor="";
               }
	       var tech_2g=boqNode[z][5];
	       if(tech_2g==null){
	    	   tech_2g=0;
               }
	       else{
	    	   tech_2g=1;
		       }

	       var tech_3g=boqNode[z][6];
	       if(tech_3g==null){
	    	   tech_3g=0;
               }
	       else{
	    	   tech_3g=1;
		       }

	       var tech_4g=boqNode[z][7];
	       if(tech_4g==null){
	    	   tech_4g=0;
               }
	       else{
	    	   tech_4g=1;
		       }
	       var tech_5g=boqNode[z][8];
	       if(tech_5g==null){
	    	   tech_5g=0;
               }
	       else{
	    	   tech_5g=1;
		       }
	       var model=boqNode[z][9];
	       if(model==null){
	    	   model="";
               }

	       c = "<tr>" +
	        "<td ><input  type='text'  value='" + nodeId + "' style='width:200px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + nodeName + "' style='width:200px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + nodeType + "' style='width:200px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + model + "' style='width:200px;' class='form-control text-input' readonly/></td>" +
		     "<td ><input  type='text'  value='" + domain + "' style='width:200px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + vendor + "' style='width:200px;' class='form-control text-input' readonly/></td>" +
	        
	        "<td style='width:240px;'>" +
	        "<input type='checkbox' name='tech_2g' value='" + tech_2g + "' " + (tech_2g ? "checked" : "") + " disabled style='opacity: 2; pointer-events: auto; background-color: blue;' /> 2G " +
	        "<input type='checkbox' name='tech_3g' value='" + tech_3g + "' " + (tech_3g ? "checked" : "") + " disabled style='opacity: 2; pointer-events: auto; background-color: blue;' /> 3G " +
	        "<input type='checkbox' name='tech_4g' value='" + tech_4g + "' " + (tech_4g ? "checked" : "") + " disabled style='opacity: 2; pointer-events: auto; background-color: blue;' /> 4G " +
	        "<input type='checkbox' name='tech_5g' value='" + tech_5g + "' " + (tech_5g ? "checked" : "") + " disabled style='opacity: 2; pointer-events: auto; background-color: blue;' /> 5G " +
	    "</td></tr>";



	    $("#NodeTable").append(c);
          
	 
 	}
 }







 if ('${ListGCell}' != "addNew") {
 boqGCell = [];
 boqGCell = ${ListGCell};
	

 
 for (z = 0; z < boqGCell.length; z++){
	 var cellId=boqGCell[z][0];
		if(cellId==null){
			cellId="";

			}
      var cellName=boqGCell[z][1];
			if(cellName==null){
				cellName="";
}
			var gcell="2G";

	       c = "<tr>" +
	        "<td ><input  type='text'  value='" + cellId + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + cellName + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + gcell + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	          "</tr>";



	    $("#CellTable").append(c);
 
 }
}


if ('${ListUCell}' != "addNew") {
 
 boqUCell = [];
 boqUCell = ${ListUCell};
	

 
 for (z = 0; z < boqUCell.length; z++){
	 var cellId=boqUCell[z][0];
		if(cellId==null){
			cellId="";

			}
      var cellName=boqUCell[z][1];
			if(cellName==null){
				cellName="";
}
			var Ucell="3G";

	       c = "<tr>" +
	        "<td ><input  type='text'  value='" + cellId + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + cellName + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + Ucell + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	          "</tr>";



	    $("#CellTable").append(c);
  
	       
           
           
	 
 }
}

if ('${ListLCell}' != "addNew") {
boqLCell = [];
boqLCell = ${ListLCell};
	

 
 for (z = 0; z < boqLCell.length; z++){
	 var cellId=boqLCell[z][0];
		if(cellId==null){
			cellId="";

			}
      var cellName=boqLCell[z][1];
			if(cellName==null){
				cellName="";
}
			var Lcell="4G";

	       c = "<tr>" +
	        "<td ><input  type='text'  value='" + cellId + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + cellName + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	        "<td ><input  type='text'  value='" + Lcell + "' style='width:500px;' class='form-control text-input' readonly/></td>" +
	          "</tr>";


	    $("#CellTable").append(c);
	 
 }
}



 
	 
 
 
 
 
 
 function BoQ() {
	 
/*    $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/WarehouseBOQ",
        success: function(response) {
*/            

if ('${docStatus}' != "addNew") {

        	 $("#inventorytab2").empty();
        	 z="";
        	z+="<tr><td><b>Site Name</b></td><td >"+'${warehouseName}'+"</td></tr>"+
        	"<tr><td> <b>Nodes</b></td><td >"+'${nodes}'+"</td></tr>"+
        	"<tr>"+
        	   "<td><b>Node Type</b></td><td >"+'${nodeType}'+"</td>"+
        	"</tr>"+
        	"<tr>"+
        	  " <td><b>IDU</b></td><td >"+'${IDUcount}'+"</td>"+
        	"</tr>"+
        	"<tr><td><b>SRansBs</b></td><td >"+'${SRanBscount}'+"</td></tr>"+
        	"<tr><td><b>2G-Cell</b></td><td >"+'${g-cell}'+"</td></tr>"+
        	"<tr><td><b>3G-Cell</b></td><td >"+'${u-cell}'+"</td></tr>"+
        	"<tr><td><b>4G-Cell</b></td><td >"+'${l-cell}'+"</td></tr>";
        	
        	$("#inventorytab2").append(z);


        	// Define variables to store the sums
        	var totalInitialCost = 0;
        	var totalNetCost = 0;
        	var totalDepreciation = 0;
        	var totalQuantity = 0; // Add this variable for total quantity

        	// Define boqArray and initialize it with ${listInventory}
        	boqArray = ${listInventory};
        	var c = "";
        	for (var i = 0; i < boqArray.length; i++) {
        	    var itemcode = boqArray[i][0];
        	    var itemName = boqArray[i][1];
        	    var itemModel = boqArray[i][2];
        	    var itemPartNumber = boqArray[i][3];
        	    var quantity = boqArray[i][4];
        	    var initialcost = boqArray[i][5];
        	    var dep = boqArray[i][6];
        	    var netcost = boqArray[i][7];        	    

        	    // Handle null values
        	    if (itemcode == null) itemcode = " ";
        	    if (itemName == null) itemName = " ";
        	    if (itemModel == null) itemModel = " ";
        	    if (itemPartNumber == null) itemPartNumber = " ";
        	    if (quantity == null) quantity = " ";

        	    c = "<tr>" +
        	        "<td name='ItemCode'><input name='itmCode' type='text' id='itmCode" + i + "' value='" + itemcode + "' style='width:290px;' class='form-control text-input' readonly/></td>" +
        	        "<td name='itemName'><input name='itemName' type='text' id='itemName" + i + "' value='" + itemName + "' style='width:290px;' class='form-control text-input' readonly/></td>" +
        	        "<td name='itemModel'><input name='itemModel' type='text' id='itemModel" + i + "' value='" + itemModel + "' style='width:290px;' class='form-control text-input' readonly/></td>" +
        	        "<td name='itemPartNumber'><input name='itemPartNumber' type='text' id='itemPartNumber" + i + "' value='" + itemPartNumber + "' style='width:290px;' class='form-control text-input' readonly/></td>" +
        	        "<td name='quantity'><input name='quantity' type='text' id='quantity" + i + "' value='" + quantity + "' style='width:290px;' class='form-control text-input' readonly/></td>" +
        	        "<td name='itemModel'><input name='itemModel' type='text' id='initialcost" + i + "' value='" + initialcost + "' style='width:290px;' class='form-control text-input' readonly/></td>" +
        	        "<td name='quantity'><input name='quantity' type='text' id='dep" + i + "' value='" + dep + "' style='width:290px;' class='form-control text-input' readonly/></td>" +
        	        "<td name='itemPartNumber'><input name='itemPartNumber' type='text' id='netcost" + i + "' value='" + netcost + "' style='width:290px;' class='form-control text-input' readonly/></td></tr>";        	        

        	    $("#inventorytab").append(c);

        	    // Get the initial cost, net cost, and depreciation from the current row
        	    var initialCost = parseFloat(initialcost);
        	    var netCost = parseFloat(netcost);
        	    var depreciation = parseFloat(dep);
        	    var quantityValue = parseInt(quantity); // Parse quantity as an integer

        	    if (!isNaN(initialCost)) {
        	        totalInitialCost += initialCost;
        	    }
        	    if (!isNaN(netCost)) {
        	        totalNetCost += netCost;
        	    }
        	    if (!isNaN(depreciation)) {
        	        totalDepreciation += depreciation;
        	    }
        	    if (!isNaN(quantityValue)) {
        	        totalQuantity += quantityValue; 
        	    }
        	}

        	document.getElementById("quan").value = totalQuantity;
        	document.getElementById("initial").value = totalInitialCost;
        	document.getElementById("dep").value = totalDepreciation;
        	document.getElementById("net").value = totalNetCost;

  /*      	    

        },
        error: function(xhr, status, error) {
            console.error("AJAX request error:", error);
        }
    });
	*/
	}
}

 
 
 

 </script>


<script>
// Initialize and add the map
var map;
var newCoor = 0;

function haversine_distance(mk1, mk2) {
      var R = 3958.8; // Radius of the Earth in miles
      var rlat1 = mk1.position.lat() * (Math.PI/180); // Convert degrees to radians
      var rlat2 = mk2.position.lat() * (Math.PI/180); // Convert degrees to radians
      var difflat = rlat2-rlat1; // Radian difference (latitudes)
      var difflon = (mk2.position.lng()-mk1.position.lng()) * (Math.PI/180); // Radian difference (longitudes)

      var d = 2 * R * Math.asin(Math.sqrt(Math.sin(difflat/2)*Math.sin(difflat/2)+Math.cos(rlat1)*Math.cos(rlat2)*Math.sin(difflon/2)*Math.sin(difflon/2)));
      return d;
    }
var markers = [];
var zooming = 6;

function initMap() {
    var lat=parseFloat(0.300);
    var lng=parseFloat(37.761);
	if($("#warlatt").val()!="" && $("#warelng").val()!="" ){
      var lat=parseFloat($("#warlatt").val());
      var lng=parseFloat($("#warelng").val());
      zooming =15;
      
	}else{
		newCoor =1;
		}
	  var address = $("#wareAddress").val();
		
    var pos = new google.maps.LatLng(lat,lng);
	
      const icon = {
      	    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
      	    scaledSize: new google.maps.Size(50, 50), // scaled size

      	};
  // The map, centered on Central Park
	const center = {lat,lng};
	const options = {zoom: zooming, scaleControl: true, center: center};
	map = new google.maps.Map(
	document.getElementById('map'), options);
	let markers = [];
	var kenya=new google.maps.LatLng(0.300 , 37.761);
		
	 $("#mapText").val(kenya);
	  
	  map.addListener("mousemove", (mapsMouseEvent)=>{

	    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
	    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));
	    	    
	  });
	  
  let infoWindow = new google.maps.InfoWindow;
  infoWindow.open(map);
  var infowindow1;
  var marker ;
  function addMarker(position) {
	  var warepname = $("#warepname").val();

	  var contentOfInfoWindow = "Warehouse Name : "+warepname+"<p><p>"+   "Address : "+address;

	   marker  = new google.maps.Marker({
		    position,
		    map,
		    size: new google.maps.Size(36, 50),
		    scaledSize: new google.maps.Size(36, 50),
		 		    
	 	 	   icon: {
	 	      url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
	 	     //size: new google.maps.Size(36, 50),
	 	    scaledSize: new google.maps.Size(50, 50),
	 	    anchor: new google.maps.Point(0, 50)
	 	    }
		  });

       infowindow1 = new google.maps.InfoWindow({
          content: contentOfInfoWindow
        });

  	  markers.push(marker);
  	}
  function setMapOnAll(map) {
	  for (let i = 0; i < markers.length; i++) {
	     // infowindow1.open(map, markers[i]);
    	  
	    markers[i].setMap(map);
 		// infowindow1.open(map, markers[i]);
 	    
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
  	//addMarker(position1);
	 if (newCoor === 0){
		  addMarker(pos);
		  setMapOnAll(map);
		  infowindow1.open(map, markers[i]);
	 }
  
      map.addListener("click", (mapsMouseEvent) => {
    	  newCoor= 1;
          // Close the current InfoWindow.
          infoWindow.close();
          // Create a new InfoWindow.
          infoWindow = new google.maps.InfoWindow({
            position: mapsMouseEvent.latLng,
            
          });
          
          myLatlng=JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2);
          infoWindow.setContent(
          	    
            JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
      	    
          );
          
			const myJSON = JSON.stringify(myLatlng);
			localStorage.setItem("testJSON", myJSON);
			let text = localStorage.getItem("testJSON");         
        
       		let obj = JSON.parse(text);
	      	var arr = obj.split(",");
	      	var reslatitude =arr[0].substring(11);
	      	var reslongtitude =arr[1].substring(10);
	      	reslongtitude=reslongtitude.slice(0,-1);
            infoWindow.open(map);
           
      		google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {
          		
                infoWindow.close();

      		 var warlatt ;
     	   	 var warelng ;

      		  setTimeout(function() {
      	    	deleteMarkers();
      	         
			     	warlatt = $("#warlatt").val();
			     	warelng = $("#warelng").val();
      
			      	var position1 = new google.maps.LatLng(warlatt, warelng);
			
			      	addMarker(position1);
			      	for (let i = 0; i < markers.length; i++) {
			      	    markers[i].setMap(map);
			      	  }	
			      	  	if(newCoor==0){
			  		 infowindow1.open(map, markers[i]);}
	      	    }, 1);
      			 	 document.getElementById("warlatt").value=reslatitude;
      				 document.getElementById("warelng").value=reslongtitude;
               	    newCoor= 1;
         			 
 			}));
      		
      		 google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (Path) {
      			
 					$('#warelng').val("");
   					$('#warlatt').val("");

   	     			deleteMarkers();
 				      
   				}));
        });

      $('#warelng,#warlatt').on('keypress', function() {

    	  newCoor= 1;

    	    setTimeout(function() {

    	    	deleteMarkers();
    	    	var  warlatt = $("#warlatt").val();
    	   	 	var warelng = $("#warelng").val();
    			var  agentLat = $("#agentLat").val();
    		 	var agentLng = $("#agentLng").val();
    			var position1 = new google.maps.LatLng(warlatt, warelng);

    			addMarker(position1);
		    	for (let i = 0; i < markers.length; i++) {
		    	    markers[i].setMap(map);
		    	  }		
		 		 infowindow1.open(map, markers[i]);
		 	  	  
		    	    }, 1);

    			//  
    		 }).on('keydown', function(e) {
	    	if (e.keyCode==8){
	      	  newCoor= 1;
      	
    			setTimeout(function() {
        			
    				deleteMarkers();
   	         
    	    		var warlatt = $("#warlatt").val();
    	   			var warelng = $("#warelng").val();
    				var agentLat = $("#agentLat").val();
    		 		var agentLng = $("#agentLng").val();
    				var position1 = new google.maps.LatLng(warlatt, warelng);

    				addMarker(position1);
			    	for (let i = 0; i < markers.length; i++) {
			    	    markers[i].setMap(map);
			    	    
			    	  }		  		 
			    	  infowindow1.open(map, markers[i]);

    	    }, 1);
    	  }	
    	});
 	 if (newCoor === 0){

  	marker.addListener("click", () => {
		 infowindow1.open(map, markers[i]);
	  });
 	 }

	}  
  
   // Calculate and display the distance between markers
  ////var distance = haversine_distance(mk1, mk2);
  ////document.getElementById('msg').innerHTML = "Distance between markers: " + distance.toFixed(2) + " mi.";
 //// var line = new google.maps.Polyline({path: [dakota, frick], map: map});

    </script>
<!--Load the API from the specified URL -- remember to replace YOUR_API_KEY-->
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap">
	</script>


</html>