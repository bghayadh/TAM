<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<title>Item Form View</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/JsBarCode.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
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
	width: 350px;
	z-index: 9999;
}

.hide-row {
	display: none;
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

svg {
	width: 200px;
	height: 70px;
}

th {
	text-align: center;
}

td {
	text-align: center;
	vertical-align: middle !important;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

/*  .ui-autocomplete {
                max-height: 100px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
/*    overflow-x: hidden;
                /* add padding to account for vertical scrollbar */
/*     padding-right: 100px;
            }*/
.label {
	display: table-caption;
	text-align: center;
	font-size: 20px;
	font-style: bold;
}

#chooseCategoryButton {
	cursor: pointer;
}

.tree li {
	list-style-type: none;
	margin: 0;
	padding: 10px 5px 0 5px;
	position: relative
}

.tree li::before, .tree li::after {
	content: '';
	left: -20px;
	position: absolute;
	right: auto
}

.tree li::before {
	border-left: 1px solid #999;
	bottom: 50px;
	height: 100%;
	top: 0;
	width: 1px
}

.tree li::after {
	border-top: 1px solid #999;
	height: 20px;
	top: 25px;
	width: 25px
}

.tree li span {
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	/* border: 1px solid #999; */
	border-radius: 5px;
	display: inline-block;
	padding: 3px 8px;
	text-decoration: none;
	cursor: pointer
}

.tree li.parent_li>span {
	cursor: pointer
}

.tree>ul>li::before, .tree>ul>li::after {
	border: 0
}

.tree li:last-child::before {
	/* height: 30px */
	
}

.tree li.parent_li>span:hover, .tree li.parent_li>span:hover+ul li span
	{
	/* background: #eee; */
	/* border: 1px solid #94a0b4; */
	/* color: #000 */
	
}

.tree-component {
	background: #eee;
	padding: 4px;
	text-decoration: none;
	color: #474747;
}

.fa-folder, .fa-folder-open, .fa-circle {
	color: grey;
}

.tree li ul>li {
	display: none;
}

.
*, *::before, *::after {
	box-sizing: border-box;
}

#cat-tree {
	color: #595959;
	font-family: "Roboto", sans-serif;
	font-size: 16px;
	font-weight: 300;
	line-height: 1.5;
}

.container {
	margin: 0 auto;
	padding: 0 24px;
	max-width: 960px;
}

/* primary header */
.primary-header {
	padding: 24px 0;
	text-align: center;
	border-bottom: solid 2px #cfcfcf;
}

.primary-header__title {
	color: #393939;
	font-size: 36px;
}

.primary-header__title small {
	font-size: 18px;
	color: #898989;
}

/* content */
.content {
	padding: 48px 0;
	border-bottom: solid 2px #cfcfcf;
}

.content__footer {
	margin-top: 12px;
	text-align: center;
}

/* footer */
.primary-footer {
	padding: 24px 0;
	color: #898989;
	font-size: 14px;
	text-align: center;
}

/* tasks */
.tasks {
	/* list-style: none;
  margin: 0;
  padding: 0;*/
	
}

.task {
	/*display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: solid 1px #dfdfdf;*/
	
}

.task:last-child {
	border-bottom: red;
	width: 205px;
}

/* context menu */
.context-menu {
	display: none;
	position: absolute;
	z-index: 10;
	padding: 12px 0;
	width: 240px;
	background-color: #fff;
	border: solid 1px #dfdfdf;
	box-shadow: 1px 1px 2px #cfcfcf;
}

.context-menu--active {
	display: block;
}

.context-menu__items {
	list-style: none;
	margin: 0;
	padding: 0;
}

.context-menu__item {
	display: block;
	margin-bottom: 4px;
}

.context-menu__item:last-child {
	margin-bottom: 0;
}

.context-menu__link {
	display: block;
	padding: 4px 12px;
	color: #0066aa;
	text-decoration: none;
}

.context-menu__link:hover {
	color: #fff;
	background-color: #0066aa;
}

.btn-tree {
	color: #474747;
	background-color: #d8d8d8;
}

.selected-span {
	color: #08526d;
	font-weight: bold;
}

.last-node::before {
	height: 26px !important;
}
.nav-link.active {
 color: #1D3763 !important;
 }
</style>

</head>

<body>
	<%--         <c:set var="page" value="inventory" /> --%>

	<%--         <%@ include file="header.html" %> --%>
	   <c:set var="pg" value="inventory" scope="session"  />
 
   <jsp:include page="header.jsp"></jsp:include>
	
		<!--  end of general head page -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<p></p>
			</div>

		</div>
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width: 190px;" class="input-group-text"
							style="color:green;">Item Code</span> <input type="text"
							id="itmcode" value="${itemCode}" class="form-control text-input"
							readonly />
					</div>
				</div>
			</div>


			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width: 190px;" class="input-group-text">Status</span>
						<input type="text" id="itmStatus" value="${itemStatus}"
							class="form-control text-input" />
					</div>


				</div>
			</div>

			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width: 200px;" class="input-group-text">Other
							Items</span><input type="text" id="selectnav" value="${selectnav}"
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
						<span style="width: 190px;" class="input-group-text">Created
							Date</span> <input type="text" id="createdate" readonly
							value="${createdDate}" class="form-control datetimepicker-input"
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




			<div class="col-md-3">
				<div class="btn-group pull-right" style="text-align: right;">

					<div class="glyph" style="text-align: right;">

						<button type="button" id="Fview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="Form View"
							style="background: #da6815;">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-light"
							data-toggle="tooltip"
							onclick='window.location.href = "${pageContext.request.contextPath}/ItemListView"'
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

	<p></p>
	<div class="container-fluid">
		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: 0px;">
					<li class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">DESCRIPTION</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-itemPartnum-tab" data-toggle="tab"
						href="#custom-tabs-partNum" role="tab"
						aria-controls="#custom-tabs-partNum" aria-selected="false"
						style="color: gold; font-size: 14px;">MODELS AND PART NUMBERS</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-profile-tab" data-toggle="tab"
						href="#custom-tabs-one-profile" role="tab"
						aria-controls="custom-tabs-one-profile" aria-selected="false"
						style="color: gold;">MEASURMENT</a></li>


					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-messages-tab" data-toggle="tab"
						href="#custom-tabs-one-messages" role="tab"
						aria-controls="#custom-tabs-one-messages" aria-selected="false"
						style="color: gold;">TYPE</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-finance-tab" data-toggle="tab"
						href="#custom-tabs-finance" role="tab"
						aria-controls="#custom-tabs-finance" aria-selected="false"
						style="color: gold;">FINANCE</a></li>

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-barcode-tab" data-toggle="tab"
						href="#custom-tabs-barcode" role="tab"
						aria-controls="#custom-tabs-barcode" aria-selected="false"
						style="color: gold;">BARCODE</a></li>



					<li class="nav-item ml-auto ">
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

				<div class="row">

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 200px;" class="input-group-text">Item
									Name</span> <input type="text" id="itmname" value="${itemName}"
									class="form-control text-input" />
							</div>
						</div>
					</div>


					<div class="col-md-4">
						<div class="input-group-prepend">
							<span style="width: 200px;" class="input-group-text">Item
								Category</span> <input type="text" id="itmcat" value="${itemCategoryDetails}" 
								class="form-control text-input" />
							<div class="input-group-append" id="chooseCategoryButton"
								onclick="chooseCategoryFunction()">
								<div class="input-group-text" style="margin-left: 10px;">
									<i class="fa fa-sitemap fa-rotate-270"></i>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 200px;" class="input-group-text">Item Root Category</span> 
									<input type="text" id="itmrootcat" value="${itemRootCat}" class="form-control text-input" readonly />
									<input type="text" id="itmcatid" value="${itemCatID}" hidden />

							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 200px;" class="input-group-text">SKU
									No.</span> <input type="text" id="itmSkuNo" value="${itemSkuNo}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 190px;" class="input-group-text">UUID</span>
								<input type="text" id="itmUUID" value="${itemUUID}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 200px;" class="input-group-text">Item
									Manufacturer</span> <input type="text" id="itemManufact"
									value="${itemManufact}" class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">


					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 200px;" class="input-group-text">OS</span> <input
									type="text" id="itmOS" value="${itemOS}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span style="width: 200px;" class="input-group-text">Physical
									RAM</span> <input type="text" id="itmPhysRAM" value="${itemPhysRAM}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="input-group-prepend">
							<span style="width: 200px;" class="input-group-text">Item
								Image</span> <input type="text" id="itmimage" value="${itemImage}"
								class="form-control text-input" />
						</div>
					</div>

				</div>

				<div class="row"></div>

				<div class="row">
					<div class="col-sm-6">
						<div class="input-group-prepend">
							<span class="input-group-text">Item Description</span>
							<!-- 
				<textarea name="itmdescrip" cols="120" rows="7" id="itmdescrip"> ${itemDescription} </textarea>
			-->
						</div>
					</div>

				</div>
				<div class="row">
					<div class="col-sm-6 ">
						<div class="input-group-prepend">
							<textarea name="suppdescrip" cols="220"
								class="form-control text-input" style="margin-top: 5px;"
								rows="8" id="itmdescrip"> ${itemDescription} </textarea>
						</div>
					</div>
				</div>
				<p></p>

				<div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<div class="checkboxes">
								<span><input type="checkbox" id="disabled" ${disabled} />
									Disabled </span>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="tab-pane fade" id="custom-tabs-partNum" role="tabpanel"
				aria-labelledby="custom-tabs-itemPartnum-tab">

				<p></p>


				<div>

					<form>


						<div class="table-responsive-sm">
							<table id="PartNTab"
								class="table table-striped table-bordered table-sm"
								style="display: block; height: 300px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<th>
											<button type="button" id="selectAllPNum" class="main">
												<span class="sub"></span>Select
											</button>
										</th>
										<th width="220px">Item Model</th>
										<th width="220px">Item Part Number</th>
										<th width="220px">Quantity</th>
										<th width="160px">Primary</th>



									</tr>
								</thead>

								<tbody>


								</tbody>

							</table>
							<p id="my_error" style="color: red;"></p>
						</div>
						<input type="button" class="add-row-pNum" id="add_roww_pNum"
							value="Add Row">
						<button type="button" class="delete-row-pNum">Delete Row</button>
					</form>
				</div>

				<p></p>






			</div>

			<div class="tab-pane fade" id="custom-tabs-one-profile"
				role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab">

				<p></p>


				<div class="row">

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Unit</span> <input type="text"
									id="unt" value="${unit}" class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Weight</span> <input type="text"
									id="weit" value="${weight}" class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Weight UOM</span> <input
									type="text" id="weituom" value="${weightUOM}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Length</span> <input type="text"
									id="len" value="${length}" class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Width</span> <input type="text"
									id="widths" value="${width}" class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Height</span> <input type="text"
								id="heit" value="${height}" class="form-control text-input" />
						</div>
					</div>

					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Size UOM</span> <input type="text"
								id="siseuom" value="${sizeUOM}" class="form-control text-input" />
						</div>


					</div>

				</div>
			</div>

			<div class="tab-pane fade" id="custom-tabs-one-messages"
				role="tabpanel" aria-labelledby="custom-tabs-one-messages-tab">

				<p></p>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Item Kind</span> <input
									type="text" id="itmKind" value="${itemKind}"
									class="form-control text-input" />
							</div>
						</div>
					</div>


					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Cable Type</span> <input
									type="text" id="cbltype" value="${cableType}"
									class="form-control text-input" />
							</div>
						</div>
					</div>


					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Item Type</span> <input
									type="text" id="itmtyp" value="${itemType}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Item Mode</span> <input
									type="text" id="itmMode" value="${itemMode}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Domain</span>
							<!--  <input type="text" id="domaine" value="${domain}" class="form-control text-input" /> -->
							<select name="cars" id="domaine">
								<option value="mad"
									<c:if test="${domain =='mad'}"> selected </c:if>>Mobile
									Access Domain</option>
								<option value="itd"
									<c:if test="${domain =='itd'}"> selected </c:if>>Transport
									Domain</option>
								<option value="icd"
									<c:if test="${domain =='icd'}"> selected </c:if>>Core
									Domain</option>
								<option value="ipd"
									<c:if test="${domain =='ipd'}"> selected </c:if>>Passive
									Domain</option>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="checkboxes">
								<span><input type="checkbox" style="margin-top: 10px;"
									id="servise" ${service} /> Service</span>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
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
					</div>
				</div>
			</div>

			<div class="tab-pane fade" id="custom-tabs-finance" role="tabpanel"
				aria-labelledby="custom-tabs-finance-tab">
				<p></p>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Warranty Period</span> <input
									type="text" id="warrantyper" value="${warrantyPeriod}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Useful Life Months</span> <input
									type="text" id="useLifeMonths" value="${useLifeMonths}"
									class="form-control text-input" />
							</div>
						</div>
					</div>

					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend" id="datetimepicker3"
								data-target-input="nearest">
								<span class="input-group-text">End Of Life</span> <input
									type="text" id="endoflive" value="${endOfLife}"
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
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Valuation Rate</span> <input
									type="text" id="valuarate" value="${valuationRate}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Depreciation Code</span> <input
									type="text" id="deprec_Code" value="${deprec_Code}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Accumulated Depreciation
									Code</span> <input type="text" id="accumDeprec_Code"
									value="${accumDeprec_Code}" class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>

			</div>
			<div class="tab-pane fade" id="custom-tabs-barcode" role="tabpanel"
				aria-labelledby="custom-tabs-barcode-tab">

				<p></p>

				<div>
					<form>

						<div class="table-responsive-sm">
							<table id="bisotab"
								class="table table-striped table-bordered table-sm"
								style="display: block; height: 300px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<th>
											<button type="button" id="selectAll" class="main">
												<span class="sub"></span>Select
											</button>
										</th>
										<th width="220px">Barcode No</th>
										<th width="220px">Barcode Shape</th>
										<th width="160px" class="active_Br" name="active_Br">Active
											Barcode</th>
										<th width="160px">Action</th>


									</tr>
								</thead>
								<tbody>


								</tbody>
							</table>
							<p id="my_error" style="color: red;"></p>
						</div>
						<input type="button" class="add-row" id="add_roww" value="Add Row">
						<button type="button" class="delete-row">Delete Row</button>
					</form>
				</div>

				<p></p>

			</div>

		</div>
	</div>

	<!-- Choose Category Modal -->
	<div class="modal fade" id="categoryModal" tabindex="-1" role="dialog"
		aria-labelledby="categoryModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="categoryModalLabel">Choose
						Category</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div id="cat-tree" class="tree well"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<!-- <button type="button" id='CategorySave' class="btn btn-primary">Save changes</button> -->
				</div>
			</div>
		</div>
	</div>

	<div id="popUpDiv" style="display: none;">
		<div class="email" style="margin-top: 50px;">
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
						<span class="input-group-text"> Subject</span> <input type="text"
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
console.log("Helloooo in sendEmail onClick");
$("#popUpDiv").fadeIn();

});
if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}


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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
        function onlyOne(checkbox) {
            var checkboxes = document.getElementsByName('primary')
            checkboxes.forEach((item) => {
               if (item !== checkbox) item.checked = false
            })
        }



        var flagFrom;
        var flag_exist;
        var exists;
        
        function getselectedrowsnew() {

            barArray = [];
            slctbarDel = [];
            var barArrayObj = {};


            $("#bisotab > tbody").find('input[name="record"]').each(function () {


                    if (flagFrom === "delete") {

                        slctbarDel.push($(this).parent().parent().children('td[name="itbarcodenoo"]').children('input').val());
                    }
                    else {
                        if ($(this).parent().parent().children('td[name="itbarcodenoo"]').children('input').val() == "") {
                            event.preventDefault();
                        }
                        else {
                            barArrayObj.barcodeno = $(this).parent().parent().children('td[name="itbarcodenoo"]').children('input').val();
                            barArrayObj.itemCode = $("#itmcode").val();

                            if ($(this).parent().parent().children('td[name="Select_Active"]').children('input').is(':checked')) {
                                barArrayObj.active = '1';

                            }
                            else {
                                barArrayObj.active = '0';
                            }
                            barArray.push(barArrayObj);
                            barArrayObj = {};
                        }
                    }

            });

        }

        function getselectedrowsItemPNum() {

            pNumArray = [];
            slctPNumDel = [];
            var pNumArrayObj = {};

            $("#PartNTab > tbody").find('input[name="record"]').each(function () {

                    if (flagFrom === "delete") {
                        slctPNumDel.push($(this).parent().parent().children('td[name="itemModel"]').children('input').val());
                    }
                    else {
                        pNumArrayObj.itemPartNum = $(this).parent().parent().children('td[name="itemPartNumber"]').children('input').val();
                        pNumArrayObj.itemCode = $("#itmcode").val();
                        pNumArrayObj.itemModel = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
                        pNumArrayObj.Quantity = $(this).parent().parent().children('td[name="Quantity"]').children('input').val();

                        if ($(this).parent().parent().children('td[name="itemPrim"]').children('input').is(':checked')) {
                            pNumArrayObj.active = '1';
                        }
                        else {
                            pNumArrayObj.active = '0';
                        }

                        pNumArray.push(pNumArrayObj);
                        pNumArrayObj = {};
                    }

            });

        }

        $("input").change(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $("#datetimepicker3").click(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $("#itmdescrip").change(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $("#itmcat").on('keyup change', function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $("#domaine").change(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $("#saveButton").click(function () {
            console.log('saved now');
            flagFrom = "save";
            // validate item code cannot be null
/*            if ($("#itmcode").val() == '') {
                alert('Itemcode cannot be Null');
                return false;
            }
*/

            // validate type of valuation rate
            if (($.isNumeric($('#valuarate').val())) == false) {
                alert('valuation Rate is  not Numeric');
                return false;
            }

            if ($("#useLifeMonths").val() == '') {
                alert('Useful Life Months cannot be Null');
                return false;
            }
            // validate creatd date cannot be null
            val = $("#createdate").val();
            val1 = Date.parse(val);
            if (isNaN(val1) == true) {
                alert(' Create Date is not valid');
                return false;
            }
            // validate modified date cannot be null
            val = $("#lstmodifdate").val();
            val1 = Date.parse(val);
            if (isNaN(val1) == true) {
                alert(' Modified Date is not valid');
                return false;
            }
            // validate end of life date cannot be null
            val = $("#endoflive").val();
            val1 = Date.parse(val);
            console.log('date is  : ' + val1);
            if (isNaN(val1) == true) {
                alert('End of life Date is not valid');
                return false;
            }
            
            getselectedrowsnew();
            getselectedrowsItemPNum();

            var itemCode = document.getElementById("itmcode").value;
            var checkBox = document.getElementById("servise");
            var service;
            if (checkBox.checked == true) {
                service = '1';
            } else { service = '0'; }

            var checkBox2 = document.getElementById("disabled");
            var disabled;
            if (checkBox2.checked == true) {
                disabled = '1';
            } else { disabled = '0'; }

            var checkt2 = document.getElementById("techg2g");
            var tech2;
            if (techg2g.checked == true) {
                tech2 = '1';
            } else { tech2 = '0'; }

            var checkt3 = document.getElementById("techg3g");
            var tech3;
            if (techg3g.checked == true) {
                tech3 = '1';
            } else { tech3 = '0'; }

            var checkt4 = document.getElementById("techg4g");
            var tech4;
            if (techg4g.checked == true) {
                tech4 = '1';
            } else { tech4 = '0'; }
            var tech5;
            if (techg5g.checked == true) {
                tech5 = '1';
            } else { tech5 = '0'; }


            var itemCat = ($("#itmcat").val().split(":"))[0];
        	var itemCatCode = ($("#itmcat").val().split(":"))[1];


            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/ItemFormSave",
                dataType: "json",
                data: {
//                    "type": '${ListPRqItem}',
                    "itemCode": $("#itmcode").val(),
                    "createdDate": $("#createdate").val(),
                    "lastModifiedDate": $("#lstmodifdate").val(),
                    "itemName": $("#itmname").val(),
                    "itemModel": $("#itmModel").val(),
                    "unit": $("#unt").val(),
                    "itemDescription": $("#itmdescrip").val(),
                    "itemImage": $("#itmimage").val(),
                    "domain": $("#domaine").val(),
                    "itemCategory": itemCat,
                    "itmCatCode": itemCatCode,
                    "itemCatID" : $("#itmcatid").val(),
    				"itemRootCat" : $("#itmrootcat").val(),
                    "itmPartNo": $("#itmPartNo").val(),
                    "itmSkuNo": $("#itmSkuNo").val(),
                    "itmUUID": $("#itmUUID").val(),
                    "itemManufact": $("#itemManufact").val(),
                    "itmOS": $("#itmOS").val(),
                    "itmPhysRAM": $("#itmPhysRAM").val(),
                    "itemProcType": $("#itemProcType").val(),
                    "itmProcVendor": $("#itmProcVendor").val(),
                    "itmKind": $("#itmKind").val(),
                    "itemType": $("#itmtyp").val(),
                    "itmMode": $("#itmMode").val(),
                    "cableType": $("#cbltype").val(),
                    "weight": $("#weit").val(),
                    "weightUOM": $("#weituom").val(),
                    "length": $("#len").val(),
                    "width": $("#widths").val(),
                    "height": $("#heit").val(),
                    "sizeUOM": $("#siseuom").val(),
                    "service": service,
                    "tech2G": tech2,
                    "tech3G": tech3,
                    "tech4G": tech4,
                    "tech5G": tech5,
                    "endOfLife": $("#endoflive").val(),
                    "valuationRate": $("#valuarate").val(),
                    "disabled": disabled,
                    "warrantyPeriod": $("#warrantyper").val(),
                    "useLifeMonths": $("#useLifeMonths").val(),
                    "deprec_Code": $("#deprec_Code").val(),
                    "accumDeprec_Code": $("#accumDeprec_Code").val(),
                    "dictParameterbarcode": barArray,
                    "dictParameteritemPartnum": pNumArray,
                    "barcodeNum": $("#barcodeNum").val(),
                    "email": $("#email").val(),
    				"password":$("#password").val(),
    		  	    "emailTo": $("#emailTo").val(),
    			    "ccmail": $("#ccmail").val(),
    			    "subject": $("#subject").val(),
    			    "message": $("#message").val(),
                },
                success: function (data) {

                    if (data.existsbarcode != "") {

                        alert("The barcode(s): " + data.existsbarcode + " exists or may be attached to another item, Please change!");
                        document.getElementById("my_error").innerHTML = "<u>WARNING: Some barcodes need to be changed !!!</u>";
                        return false;
                    }

                    else {
                        document.getElementById("my_error").innerHTML = "";
                        $('#lstmodifdate').val(data.lastModifiedDate);
//                        var param = "${pageContext.request.contextPath}/ItemFormView?itemcode="+$("#itmcode").val()+"&NavAction=2";
                        var param = "${pageContext.request.contextPath}/ItemFormView?itemcode="+data.itemCode+"&NavAction=2";
                        location.replace(param);
                    }

                },
                error: function (error) {
                    console.log("The error is " + error);
                }
            });


        });
        $("#deleteButton").click(function () {
    		var deleteArray = [];
    		deleteArray.push($("#itmcode").val());

    		flagFrom = "delete";
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/ItemDelete",
                dataType: "json",
                data: {
                    "itemCode": deleteArray
                },
                success: function (data) {
                	location.reload();

                     },
                error: function (error) {
                    console.log("The error is " + error);
                }
            });
        	location.replace("${pageContext.request.contextPath}/ItemListView");

        });

        if ('${ListPRqItem}' != "addNew") {
            //return false;
            var checkboxValue = "";
            boqArray = [];
            boqArray = ${ ListPRqItem };

            for (i = 0; i < boqArray.length; i++) {

                var itemBarrCode = boqArray[i].barcodeNb;

                if (itemBarrCode == null)
                    itemBarrCode = "";

                else {
                    itemBarrCode = boqArray[i].barcodeNb;
                    checkboxValue = boqArray[i].activebcode;

                    if (checkboxValue == '1') { checkboxValue = "checked"; }
                    else { checkboxValue = ""; }

                }
                var itemBarcodeRow = "<tr>";
                itemBarcodeRow = itemBarcodeRow + "<td><input type='checkbox' name='record'></td>"
                itemBarcodeRow = itemBarcodeRow + "<td name='itbarcodenoo'><input name='barcodenoo'   type='text' maxlength='10' value='" + itemBarrCode + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all 'readonly/></td>";
                itemBarcodeRow = itemBarcodeRow + "<td name='barcodeshape' style='width:200px'> <svg name='barcode'></svg>    </td>";
                itemBarcodeRow = itemBarcodeRow + "<td name='Select_Active' style='width:120px'>	<input  type='checkbox' class='checkbox' name='ActiveSelect' " + checkboxValue + " /></td>";
                itemBarcodeRow = itemBarcodeRow + "<td name='action'></td>"
                itemBarcodeRow = itemBarcodeRow + "</tr>";

                $("#bisotab > tbody").append(itemBarcodeRow);
                ////////////////////////////////////////////////////////////////
                $('#bisotab tbody tr td input[name=generatee]').on('click', function () {
                    var hash = $(this).parent().parent().children().children('input[name=barcodeno]').val();


                    var barcodeinput = $(this).parent().parent().children().children('svg[name=barcode]');

                    barcodeinput.JsBarcode(hash, {
                        width: 0.5,
                        height: 25,
                        lineColor: "#000",

                    });
                });

            }
        }

        if ('${ListPRqItemPNum}' != "addNew") {

            boqArrayPNum = [];
            boqArrayPNum = ${ ListPRqItemPNum };

            for (i = 0; i < boqArrayPNum.length; i++) {

                var itemPartNum = boqArrayPNum[i].itemPartNum;
                var primary = boqArrayPNum[i].primary;
                var itemModel = boqArrayPNum[i].itemModel;
                var qtyPartNum = boqArrayPNum[i].qtyPartNum;

                if (itemPartNum == null) {
                    itemPartNum = ""
                }

                if (itemModel == null) {
                    itemModel = ""
                }

                if (primary == '1') { primary = "checked"; }
                else { primary = ""; }

                var itemPNumRow = "<tr>";
                itemPNumRow = itemPNumRow + "<td><input type='checkbox' name='record'></td>"
                itemPNumRow = itemPNumRow + "<td name='itemModel'><input name='itemModel'   type='text' value='" + itemModel + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all 'readonly/></td>";
                itemPNumRow = itemPNumRow + "<td name='itemPartNumber' style='width:200px'><input name='itemPartNumber' style='width:200px'  type='text' value='" + itemPartNum + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all' /> </td>";
                itemPNumRow = itemPNumRow + "<td name='Quantity' style='width:200px'><input name='Quantity' style='width:200px'  type='text' value='" + qtyPartNum + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all' /> </td>";
                itemPNumRow = itemPNumRow + "<td name='itemPrim' style='width:120px'><input  type='checkbox' class='itemPrim' onclick='onlyOne(this)' name='primary' " + primary + "  /></td>";
                itemPNumRow = itemPNumRow + "</tr>";

                $("#PartNTab > tbody").append(itemPNumRow);
                ////////////////////////////////////////////////////////////////

            }
        }

        $(".delete-row").click(function () {
            slctDel = [];
            $("#bisotab > tbody").find('input[name="record"]').each(function () {
                if ($(this).is(":checked")) {
                    slctDel.push({ "Id": $(this).parent().parent().children('td[name="id"]').children('input').val() });
                }
            });
            for (i = 0; i <= slctDel.length; ++i) {
                //delete from screen
                // check if you select rows to save or update
                if (slctDel.length == 0) {
                    alert(' Select Row(s) to Delete');
                    return false;
                }
            }

            $("#bisotab > tbody").find('input[name="record"]').each(function () {
                if ($(this).is(":checked")) {
                    $(this).parents("tr").remove();
                }

            });

        });
        // end delete row

        $(".delete-row-pNum").click(function () {
            slctDel = [];
            $("#PartNTab > tbody").find('input[name="record"]').each(function () {
                if ($(this).is(":checked")) {
                    slctDel.push($(this).parent().parent().children('td[name="itemModel"]').children('input').val());

                }
            });

            //   var supprimerrow ='';
            for (i = 0; i <= slctDel.length; ++i) {
                //delete from screen
                // check if you select rows to save or update
                if (slctDel.length == 0) {
                    alert(' Select Row(s) to Delete');
                    return false;
                }
            }

            $("#PartNTab > tbody").find('input[name="record"]').each(function () {
                if ($(this).is(":checked")) {
                    $(this).parents("tr").remove();
                }

            });


        });


        $(".add-row").click(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $(".delete-row").click(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $("#bisotab > tbody").change(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $(".add-row-pNum").click(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });
        $(".delete-row-pNum").click(function () {
            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });
        });

        $(".add-row").click(function () {
            console.log("add row clicked");

            var markup = "<tr><td><input type='checkbox' name='record'></td><td name='itbarcodenoo'>"
                + "<input name='barcodeno' id='hi' maxlength='10' style='width:200px'  type='text' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='barcodeshape' style='width:200px'> <svg name='barcode'></svg> </td>"
                + "<td name='Select_Active' style='margin-left:auto margin-right:auto width:120px'>	<input type='checkbox' class='active_Br' name='ActiveSelect' /></td>"
                + "<td name='action'>	<input type='button' name= 'generate' value= Generate ></td></tr>";
            $("#bisotab > tbody").append(markup);

            var myDiv = document.getElementById("bisotab");
    	    myDiv.scrollTop = myDiv.scrollHeight;
    	    
            $('.active_Br').each(function () { this.checked = true; });

            $("input[name='barcodeno']").each(function () {

                var input = $(this).parent().parent().children().children('input[name=barcodeno]');
                $(this).keypress(function (e) {
                    if (flag_exist == 1) {
                        if (e.which == 13) {
                            var input2 = input.val();

                            var barcodeinput = $(this).parent().parent().children().children('svg[name=barcode]');
                            barcodeinput.JsBarcode(input2, {
                                width: 0.5,
                                height: 25,
                                lineColor: "#000",

                            });
                            // it will be stopped if there is an action after
                            return false;
                        }
                    }
                });
            });

            function getRandomString(length) {
                var randomChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
                var result = '';
                for (var i = 0; i < length; i++) {
                    result += randomChars.charAt(Math.floor(Math.random() * randomChars.length));
                }
                return result;
            }

            $('#bisotab tbody tr td input[name=generate]').on('click', function () {

                var flag_exist = 0;
                var itemBarrCode = 0;
                const add_rowbutton = document.getElementById('add_roww');
                const savebutton = document.getElementById('saveButton');
                var hash = getRandomString(10);

                $(this).parent().parent().children().children('input[name=barcodeno]').val(hash);

                var barcodeinput = $(this).parent().parent().children().children('svg[name=barcode]');

                barcodeinput.JsBarcode(hash, {
                    width: 0.5,
                    height: 25,
                    lineColor: "#000",

                });
				console.log(${ListPRqItem});
                if('${ListPRqItem}' != "addNew")
                {
                	boqArray = [];
                    console.log('${ ListPRqItem }');
                    boqArray = ${ ListPRqItem };
                    
                    for (i = 0; i < boqArray.length; i++) {
                        itemBarrCode = boqArray[i].barcodeNb;
                        if (hash == itemBarrCode) {
                            flag_exist = 1;
                            alert("this barcode already exists please change!!");
                            document.getElementById("my_error").innerHTML = "<u>some barcodes need to be changed</u>";
                        }
                        else {
                            savebutton.disabled = false;
                            add_rowbutton.disabled = false;
                            document.getElementById("my_error").innerHTML = "";

                        }
                }
                

                }
            });


            $("input[name='barcodeno']").on('change input', function () {

                var input = $(this).parent().parent().children().children('input[name=barcodeno]');

                value = $(this).val();
                var input2 = input.val();

                if (value != "") {
                    var barcodeinput = $(this).parent().parent().children().children('svg[name=barcode]');
                    barcodeinput.JsBarcode(input2, {
                        width: 0.5,
                        height: 25,
                        lineColor: "#000",

                    });
                }
                else {
                    input = null;
                    var barcodeinput = $(this).parent().parent().children().children('svg[name=barcode]');
                    barcodeinput.JsBarcode(input, {
                        width: 0.5,
                        height: 25,
                        lineColor: "#000",
                    });
                }
            });

        });

        $(".add-row-pNum").click(function () {

            var markup = "<tr><td><input type='checkbox' name='record'></td><td name='itemModel'>"
                + "<input name='itemModell' style='width:200px'  type='text' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='itemPartNumber' style='width:200px'> <input name='itemPartNumberr' style='width:200px'  type='text' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='Quantity'><input style='width:200px'  type='text' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
                + "<td name='itemPrim' style='margin-left:auto margin-right:auto width:120px'><input type='checkbox' onclick='onlyOne(this)' class='itemPrim' name='primary' /></td></tr>";
            $("#PartNTab > tbody").append(markup);
            
            var myDiv = document.getElementById("PartNTab");
    	    myDiv.scrollTop = myDiv.scrollHeight;

        });

        // Find and remove selected table rows
        $(".delete-row").click(function () {
            getselectedrowsnew();

            if (slctDel.length == 0) {
                return false;
            }
            $("#bisotab > tbody").find('input[name="record"]').each(function () {
                if ($(this).is(":checked")) {
                    $(this).parents("tr").remove();
                }
            });
        });
        // end delete row


        $(".delete-row").click(function () {

            getselectedrowsnew();
            if (slctDel.length == 0) {
                return false;
            }
            $("#bisotab > tbody").find('input[name="record"]').each(function () {
                if ($(this).is(":checked")) {
                    $(this).parents("tr").remove();
                }
            });

        });
        // end delete row

        $(".delete-row").click(function () {
            getselectedrowsnew();

            if (slctDel.length == 0) {

                return false;
            }
            $("#bisotab > tbody").find('input[name="record"]').each(function () {
                if ($(this).is(":checked")) {
                    $(this).parents("tr").remove();
                }
            });

        });

        $(document).ready(function () {

        	
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
        	
            $('body').on('click', '#selectAll', function () {

                if ($(this).hasClass('allChecked')) {
                    $('input[name="record"]', '#bisotab').prop('checked', false);
                } else {
                    $('input[name="record"]', '#bisotab').prop('checked', true);
                }
                $(this).toggleClass('allChecked');

            });

            $('body').on('click', '#selectAllPNum', function () {

                if ($(this).hasClass('allChecked')) {
                    $('input[name="record"]', '#PartNTab').prop('checked', false);
                } else {
                    $('input[name="record"]', '#PartNTab').prop('checked', true);
                }
                $(this).toggleClass('allChecked');

            });

            var columns = [{ name: 'Cat Code', minWidth: '250px' }];
            $("#selectnav").autocomplete({
          	  source: debounce(function(request, response, event, ui) {
          		    $.ajax({
          		      type: "GET",
          		      contentType: "application/json; charset=utf-8",
          		      url: '${pageContext.request.contextPath}/GetItemsAutocomplete',
          		      data: {
          		        "itemCategory": $("#selectnav").val(),
          		      },
          		      dataType: "json",
          		      success: function(data) {
          		        if (data != null) {
          		          response(data.ListItemCategory);
          		        }
          		      },
          		      error: function(result) {
          		        alert("Error");
          		      }
          		    });
          		  }, 900),
          		  minLength: 0,
          		  maxShowItems: 40,
          		  scroll: true,
          		  select: function(event, ui) {
          			  this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
          				var param ="${pageContext.request.contextPath}/ItemFormView?itemcode="+(ui.item[1])+"&NavAction=2";
          				window.location.href =param;
          					
          					return false;
          				}

          		   
          		  
          		}).autocomplete("instance")._renderItem = function(ul, item) {
          		  return $("<li class='each'>")
          		    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
          		      item[0] + "</span><br><span class='desc'>" +
          		      item[1] + "</span></div>")
          		    .appendTo(ul);
          		};


            $("#selectnav").focus(function () {
                
                if (this.value == "") {
                    $(this).autocomplete("search");
                }
            });
            function debounce(fn, delay) {
        	    var timer;
        	    return function() {
        	      var args = [].slice.call(arguments);
        	      var context = this;
        	      if (timer) {
        	        window.clearTimeout(timer);
        	      }
        	      timer = window.setTimeout(function() {
        	        fn.apply(context, args);
        	      }, delay);
        	    };
        	  };
   //// ENd of Autocomplete for Area ID
    	
            $("#itmcat").autocomplete({
                showHeader: true,
                columns: columns,

                source: function (request, response) {
                    $.ajax({
                        type: "GET",
                        contentType: "application/json; charset=utf-8",
                        url: '${pageContext.request.contextPath}/GetAllCategory',
                        data: {
                            "itemCategory": $("#itmcat").val(),
                        },
                        dataType: "json",
                        success: function (data) {
                            if (data != null) {
                                response(data.ListItemCategory);
                            }
                        },
                        error: function (result) {
                            alert("Error");
                        }
                    });
                }, minLength: 0, maxShowItems: 4, scroll: true,
                select: function (event, ui) {
                    $("#itmcat").val(ui.item[0] + ":" + ui.item[1]);
                    $("#itmrootcat").val(ui.item[2]);
                    $("#itmcatid").val(ui.item[3]);

/*                    if ('${ListPRqItem}' == "addNew") {
                        $.ajax({
                            type: "GET",
                            url: "${pageContext.request.contextPath}/GetAllItemModelPopup",
                            dataType: "json",
                            data: {
                                "itemCategoryCode": ui.item[2]
                            },
                            success: function (data) {
                                var CodeCount = data.ListItemModelPopup + 1;
                                $("#itmcode").val(ui.item[2] + "-" + CodeCount);
                            },
                            error: function (error) {
                                console.log("The error is " + error);
                            }
                        });
                    } */
                    return false;
                }
            }).autocomplete("instance")._renderItem = function (ul, item) {
                return $("<li class='each'>")
                    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                        item[0] + "</span><br><span class='desc'>" +
                        item[2] + ', ' + item[1] + "</span></div>")
                    .appendTo(ul);
            };

            $("#itmcat").focus(function () {
                if (this.value == "") {
                    $(this).autocomplete("search");
                }
            });

            // to add New in orange of a new record using add button from itemList	
            if ('${ListPRqItem}' != "addNew") {
            }
            else {
                // set status to new and green while new record
                $("#formStatus").text("New");
                $('.dot').css({ "background-color": "orange" });
                // hide next and prv items 
                $("#nextprvItems").addClass("hide-row ");
            }

        	if('${SelectedIndex}' != "addNew"){
        		var SelectedIndex = ${SelectedIndex};
        		if('${ItemsCount}' != "addNew"){


            		
        	var ItemsCount = ${ItemsCount};
        	if(($("#itmcode").val()) != "" && ($("#itmcode").val()) != null){

        	if(SelectedIndex === ItemsCount){
        		document.getElementById("btnLast").style.opacity = 0.5;
        		$("#btnLast").hasClass("disabled");
        		document.getElementById("btnLast").style.pointerEvents = "none";
        		
        		document.getElementById("btnNexta").style.opacity = 0.5;
        		document.getElementById("btnNexta").style.pointerEvents = "none";

        		
        		$("#btnNexta").hasClass("disabled");
        		
        		}else{
        			
        			$("#btnNext").click(function(){
        			if(!$("#btnNexta").hasClass("disabled")){
        					        		        	
        					
        					var param ="${pageContext.request.contextPath}/ItemFormView?itemcode="+$("#itmcode").val()+"&NavAction=1";
        					window.location.href =param;
        				}
        				
        				});
        	
        			if(!$("#btnLst").hasClass("disabled")){
        				
        				$("#btnLst").click(function(){
        					
        					var param ="${pageContext.request.contextPath}/ItemFormView?itemcode="+$("#itmcode").val()+"&NavAction=4";
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
        				var param ="${pageContext.request.contextPath}/ItemFormView?itemcode="+$("#itmcode").val()+"&NavAction=0";
        				location.reload();

        				window.location.href =param;
        			}
        			
        			 });
        		$("#btnFrst").click(function(){

        			if(!$("#btnFrst").hasClass("disabled")){
        					
        				var param ="${pageContext.request.contextPath}/ItemFormView?itemcode="+$("#itmcode").val()+"&NavAction=3";
        				window.location.href =param;
        						
        				}
        				 });

        	}
        	
        	}}
        }
        	$("#label-1").text((SelectedIndex)+"/"+ItemsCount);
          

        });

        $("svg[name='barcode']").each(function () {

            var hash = $(this).parent().parent().children().children("input[name='barcodenoo']").val();
            var svg = $(this).parent().parent().children().children("svg[name='barcode']");


            var barcodeinput = svg;

            barcodeinput.JsBarcode(hash, {
                width: 0.5,
                height: 25,
                lineColor: "#000",

            });

        });

        /* ----------- Script Part Related to Category Tree ----------- */

        function chooseCategoryFunction() {

            $("#cat-tree").empty();

            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/getCategories",
                dataType: "json",
                data: {
                    "categoryAll": "All"
                },
                success: function (data) {
                    var lst = $.parseJSON(data.ListOfsubtree);
                    var right = [];
                    var row = [];

                    for (n = 0; n < lst.length; n++) {

                        title_p1 = lst[n][3];
                        lft_p2 = lst[n][1];
                        rgt_p3 = lst[n][2];
                        id_p0 = lst[n][0];
                        code_p4 = lst[n][4];

                        row.push({
                            title: title_p1,
                            lft: lft_p2,
                            rgt: rgt_p3,
                            id: id_p0,
                            code: code_p4
                        });
                    }

                    i = 0;
                    str = "<ul><li data-id='item_" + row[0]["id"] + "'>";

                    for (i = 0; i < row.length; i++) {

                        var d = right.length;
                        var length = right.length;

                        for (j = 0; j < length; j++) {

                            lastValue_right = parseInt(right[d - 1]);

                            d = d - 1;

                            row_right = parseInt(row[i]['rgt']);

                            if (lastValue_right < row_right) {
                                right.pop();
                                str += "</li></ul>";
                            }
                        }

                        if (right.length != 0)
                            str += "<ul><li data-id='item_" + row[i]["id"] + "'>";

                        str += "<span class='tree-span'><i class='fa fa-folder'></i>" + row[i]["title"] + " - " + row[i]["code"] + "</span>";

                        right.push(row[i]["rgt"]);
                    }

                    $("#cat-tree").append(str);
                    buildtree();

                    $('.tree li > span').on('click', function (e) {

                        $("#tree-actions").remove();
                        $(".tree li > span").removeClass("selected-span");

                        $(this).addClass("selected-span");
                        
                        var action_div = "<div id='tree-actions' class='btn-group btn-group-sm' role='group' aria-label='Action Buttons'>";
                        action_div += "<button type='button' id='selectCategoryButton' class='btn btn-tree' onClick='selectCategoryFunction(this)'>Select</button>";
                        action_div += "</div>";

                        $(this).after(action_div);
                        e.stopPropagation();
                        
                    });

                    $('#categoryModal').modal('show');
                },
                error: function (error) {
                    console.log("The error is " + error);
                }
            });

        }

        function buildtree() {
            $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr(
                'title', 'Expand this branch');

            $('.tree li.parent_li > span').on(
                'click',
                function (e) {
                    var children = $(this).parent('li.parent_li').find(
                        ' > ul > li');
                    if (children.is(":visible")) {
                        children.hide('fast');
                        $(this).attr('title', 'Expand this branch').find(
                            ' > svg').addClass('fa-folder').removeClass(
                                'fa-folder-open');

                    } else {
                        children.show('fast');
                        $(this).attr('title', 'Collapse this branch').find(
                            ' > svg').addClass('fa-folder-open')
                            .removeClass('fa-folder');
                    }

                    e.stopPropagation();
                });

            $("#cat-tree ul li").each(function () {
                if ($(this).find("ul").length) {
                } else {
                    $(this).attr('title', 'Expand this branch').find(
                        ' > span > i').addClass('fa-circle')
                        .removeClass('fa-folder');

                    $(this).attr('title', 'Expand this branch').find(
                        ' > span > i').css('font-size', '0.6rem');

                }
            });

            $("i").css('margin-right', '5px');

            var last_ul = $(".parent_li").find("ul:last-child");
			var last_li = $(last_ul).find("li:last-child");			
			$(last_ul).addClass("last-ul");
			$(".last-ul").children("li").addClass("last-node");

        }

        function selectCategoryFunction(e) {
        	
        	console.log("Select button clicked");

            $("#formStatus").text("Not Saved");
            $('.dot').css({ "background-color": "orange" });

            var categoryID = $(e).parents('li').data('id');
            var categorySpan = $(e).parents('li').children(':eq(0)').text();
            var categoryspan = categorySpan.split(" - ");
            var categoryName = categoryspan[0];
            var categoryCode = categoryspan[1];

            $('#currentNode').removeAttr('id');
            var currentNode = $(e).parents('li').children(':eq(0)');
            $(currentNode).attr('id', 'currentNode');

            var categoryId = categoryID.split("_");
            categoryId = categoryId[1];

            $("#itmcat").val(categoryName + ":" + categoryCode);

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/GetAllCategory',
                data: {
                    "itemCategory": categoryName,
                },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        var item = data.ListItemCategory;
                        console.log(" ITEM data.ListItemCategory " + data.ListItemCategory);
                        
                        if(data.ListItemCategory == "")
                    	{
                    		alert("The item should have a specific category ");
                    		$("#itmcat").val("");
                       	}else{
                           	$("#itmcat").val(item[0][0] + ":" + item[0][1]);
                          	$("#itmrootcat").val(item[0][2]);
                          	$("#itmcatid").val(item[0][3]);
                       	}
                      
                      	/*
                        if ('${ListPRqItem}' == "addNew") {                        	
                            $.ajax({
                                type: "GET",
                                url: "${pageContext.request.contextPath}/GetCountItemsAutocomplete",
                                dataType: "json",
                                data: {
                                    "itemCategoryCode": item[0][2]
                                },
                                success: function (data) {
                                    var CodeCount = data.CountItems+ 1;
                                    console.log("item sequence is " +data.CountItems);
                                    $("#itmcode").val(item[0][2] + "-" + CodeCount+1);
                                },
                                error: function (error) {
                                    console.log("The error is " + error);
                                }
                            });
                        	
                        }
                      	*/
                    }
                },
                error: function (result) {
                    alert("Error");
                }
            });

            $('#categoryModal').modal('hide');
        }

        function debounce(fn, delay) {
    	    var timer;
    	    return function() {
    	      var args = [].slice.call(arguments);
    	      var context = this;
    	      if (timer) {
    	        window.clearTimeout(timer);
    	      }
    	      timer = window.setTimeout(function() {
    	        fn.apply(context, args);
    	      }, delay);
    	    };
    	  };

    </script>

</html>