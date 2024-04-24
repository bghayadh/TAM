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
<script
	 src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
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
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />

<!--  style used for prrqitem table  -->
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

.hide-row {
	display: none;
}

.label {
	display: table-caption;
	text-align: center;
	font-size: 20px;
	font-style: bold;
}

.left_col {
	height: 60px
}

#mapContainer {
	height: 600px;
	width: 600px;
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	width: 350px;
	z-index: 9999;
}
/*	.ui-autocomplete {
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

/* 		#collapseOne {
		    overflow: hidden; 
		    height: 333px;
		    width: 666px;
		    float:right;
		} */
#div1 {
	float: left;
}

#collapseOne {
	height: 560px;
	width: 800px;
	float: right;
}

#regionTab {
	float: left; /* important */
	/* width:595px; */
	height: auto;
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
						<span class="input-group-text" style="color: green">Region
							ID</span> <input type="text" id="regionId" value="${RegionId}"
							class="form-control text-input" readonly />
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
					</div>

				</div>
			</div>

			<div class="col-md-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width: 210px;">Status</span>
					<select id="ordstat" class="form-control">
						<option value="draft"
							<c:if test = "${ordStatus =='draft'}" > selected </c:if>>Draft</option>
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
						<span class="input-group-text">Other Region</span> 
						<input type="text" id="selectnav" value="${selectnav}"
							style="width: 430px" class="form-control text-input" />

					</div>
				</div>
			</div>


			<!-- 		<div class="hide-row col-md-3 pad "></div> -->





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
					<li id="btnFrst" class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 18px;width: 53px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo;</a></li>
							<li id="btnLst" class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo; </a></li>
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
							onclick='window.location.href = "${pageContext.request.contextPath}/RegionListView"'
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
   <button type="button" 
				onclick='window.location.href = "${pageContext.request.contextPath}/RegionFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
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
						<td width=50% valign="top" class="left_col">
							<div class="form-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Region Name</span> <input
										type="text" id="regionname" value="${regionName}"
										style="width: 275px" class="form-control text-input" />
								</div>
							</div>

						</td>
						<td rowspan=10><div style="width: 40px">&nbsp;</div></td>
						<td rowspan=10 valign="top">
							<div class="panel-body" style="margin-top: 1%">
								<div class="card-body">
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
													class="buttonTog deleteCoordBtn" style='margin-left: 10px;'>
													<i class="fa fa-trash"></i> Delete Region
												</button>
											</div>
											<div class=" top-btn-filter">
												<button id="togglepolygone" name="togglepolygone"
													class="buttonTog" style='margin-left: 10px;'>
													<i class="fas fa-mountain"></i>Fill Region
												</button>
											</div>
											<div id="txtDiv">
												<input id="mapText" type='text' disabled />
											</div>
										</div>
									</div>
									<div id="mapContainer" style='height: 480px; width: 100%;'></div>

								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="left_col" style="margin-top: -50px;">
							<div class="form-group">

								<div class="input-group-prepend">
									<span class="input-group-text">Region Code</span> <input
										type="text" id="regioncode" value="${regionCode}"
										style="width: 280px" class="form-control text-input" />
								</div>
							</div>
						</td>
					</tr>


					<tr>
						<td class="left_col" style="margin-top: -50px;">
							<div>

								<form>
									<div>
										<div class="table-responsive-sm">
											<div>
												<table id="regionTab"
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
															<th style='width: 215px;' hidden>SeqId</th>



														</tr>

													</thead>
													<tbody>


													</tbody>
												</table>
												<div style="position: static; margin-right: 70%;">
													<input type="button" class="addaction-row-Area"
														id="addaction-row-Area" value="Add Row"
														onclick="addrowsArea()">
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
																type="text" id="mouseclicklong"
																value="${mouseclicklong}" style="width: 430px"
																class="form-control text-input" />
														</div>
													</div>

												</div>

											</div>
										</div>
									</div>
								</form>

							</div>

						</td>
					</tr>


					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>



				<%-- 	<p></p>
		<div class="row">
				<div class="col-md-10">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Longitude</span>
								<input type="text" id="regionlng" value="${regionLong}" class="form-control text-input"  />
							</div>
						</div>
				</div>
		</div>
		
		<p></p>
		<div class="row">
				<div class="col-md-10">
						<div class="form-group">
							<div class="input-group-prepend">
							<span class="input-group-text">Latittude</span>
							<input type="text" id="regionlatt" value="${regionLat}" class="form-control text-input"/>
							</div>
						</div>
				</div>
	     </div> --%>




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

</body>
<script>
/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////




				//	$("#btnPrva").on("click", ".almgrid-link", function (e) {
					//	var param1 = $(this).attr('id');
					//	var param ="${pageContext.request.contextPath}/RegionFormView?RegionID="+$("#regionId").val()+"&0";
						
					//	window.location.href = param;
					//	e.preventDefault();
					//});

$(".addaction-row-Area").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});
	
$(".deleteCoordBtn").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

	var i = 0 ;
	var rowCount = $('#regionTab >tbody >tr').length;

	for (i = 0;i<rowCount;i++){
		var ID =  $("#regionTab > tbody tr").eq(i).find('input[name=seqId]').val(); 
		if(ID != null){

			slctDelRegion.push(ID);

						}
	}
	
});

$(".buttonTog").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});

	
	});

$("#CoordsButton").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});

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

	var CheckCount  = 0;
	var RowSelected = 0;
	var seq=0
	var slctDelRegion = [];

	function addrowsArea(){
	var rowToAdd =0;
		 
   	$("#regionTab input[type=checkbox]:checked").each(function () {

	    var row = $(this);
	    RowSelected= $(this).closest("tr")[0];
		rowToAdd = RowSelected.rowIndex;
		CheckCount =row.length ;
	    $(this).prop('checked', false);

	 });

 	if (CheckCount ===1){
 	 		
	var markup = "<tr><td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPopArea(this)' class='btn btn-default'  style='position:relative;left:3px;'></button></td>"
	+"<td name='sequence' ><input  name='sequence'  type='text' style='width:72px;'  class='form-control text-input'></td>"
	+"<td name = 'regionLatt' ><input name='regionLatt' type='text'  style='width:100%;' class='form-control text-input'/></td>"
	+"<td name = 'regionLng' ><input name='regionLng'  type='text' style='width:215px;'style='width:200px;' class='form-control text-input' /></td></tr>"

	
   $("#regionTab > tbody tr").eq(RowSelected.rowIndex-1).after(markup);	
   $("#regionTab > tbody tr").eq(RowSelected.rowIndex ).find('input[type=checkbox]').prop('checked', true);

}
 	
else {
	    alert('You must check at most 1 checkboxes');
		 
	 }
CheckCount =0;

}

	//Beginning for deleting area borders
	
			function deleterowsArea(){
				
				slctDelRegion = [];
		
				$("#regionTab input[type=checkbox]:checked").each(function () {
		
			    var row = $(this);
			    RowSelected= $(this).closest("tr")[0];
				CheckCount ++ ;
				var ID = $(this).parent().parent().children('td[name="seqId"]').children('input').val();
				$("#regionTab > tbody tr").eq(RowSelected.rowIndex-1).remove();
				
			  		if(ID != null){

					slctDelRegion.push(ID);

								}
									});
			       	  initMap();
				       }

		     // Ending for deleting area borders
		         
  var reslatitude =null;
  var reslongtitude =null;
  var polygons = [];
  var map;
  var newReg=0;
  var checkIfPolygone =0;
  var streched=0;  
  
function initMap() {
	
	// Define the Center
	var kenya=new google.maps.LatLng(0.300 , 37.761);

	map = new google.maps.Map(document.getElementById("mapContainer"), {
    center:kenya,
    scaleControl: true,
	streetViewControl: true,
	streetViewControlOptions: {
		
	position: google.maps.ControlPosition.LEFT_TOP,
		
	},
	
  });
	  
	  map.setZoom(6);
	
	  let infoWindow = new google.maps.InfoWindow;
	  
	    //edit map when longtitude in boq changed

  $("#regionTab > tbody").on("change", 'input[name="regionLng"]', function () {

		$("#formStatus").text("Not Saved");
  		$('.dot').css({"background-color" : "orange"});
		var RowSel= $(this).closest("tr")[0];
	  	var sameRowLat =$("#regionTab > tbody tr").eq(RowSel.rowIndex-1).find('td[name="regionLatt"]').children('input').val();
	  			  
	  	if(sameRowLat=== null || sameRowLat=== "")
		  	{
	  	}
	  	else{
	  			
	  		//very important to draw again in each addition of coordinates into boq table
	  	    for (i=0; i<line.length; i++) 
	  			{

	  			  line[i].setMap(null);
	  			
	  			}
	  	var regionLatLngInputs  = [];
	  	getRegionLatLngToDrawAgain(regionLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array
	  		
	  for (i = 0;i<regionLatLngInputs.length;i++){

	  var regionLat = regionLatLngInputs[i].Latitude;
	  var regionLong = regionLatLngInputs[i].Longitude;
	  regionLatLngInputs[i] = new google.maps.LatLng(parseFloat(regionLat),parseFloat(regionLong));
	  
	  }
	  		  Path = new google.maps.Polyline({
	  		        path: regionLatLngInputs,
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
	    
	$("#regionTab > tbody").on("change", 'input[name="regionLatt"]', function () {
		
 
		$("#formStatus").text("Not Saved");
  		$('.dot').css({"background-color" : "orange"});
		var RowSel= $(this).closest("tr")[0];
  	    var sameRowLng =$("#regionTab > tbody tr").eq(RowSel.rowIndex-1).find('td[name="regionLng"]').children('input').val();

		if(sameRowLng=== null || sameRowLng=== "")
			{
		}
		else
			{
			
		//very important to draw again in each addition of coordinates into boq table
	    for (i=0; i<line.length; i++) 
			{
			  line[i].setMap(null);
			}

	    var regionLatLngInputs  = [];
		getRegionLatLngToDrawAgain(regionLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array

		for (i = 0;i<regionLatLngInputs.length;i++)
		{
			var	regionLat = regionLatLngInputs[i].Latitude;
			var regionLong = regionLatLngInputs[i].Longitude;
			regionLatLngInputs[i] = new google.maps.LatLng(parseFloat(regionLat),parseFloat(regionLong));
		}
		
		  Path = new google.maps.Polyline({
		        path: regionLatLngInputs,
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
	//delete row boq function
	$("#delrow").click(function() {
		$("#regionTab input[type=checkbox]:checked").each(function () {

		    RowSelected= $(this).closest("tr")[0];
			CheckCount =$("input:checkbox:checked").length ;
		    $(this).prop('checked', false);
			$("#regionTab > tbody tr").eq(RowSelected.rowIndex-1).remove();
			var ID = $(this).parent().parent().children('td[name="seqId"]').children('input').val();
					
				if(ID != null){
	
				slctDelRegion.push(ID);
	
				}

	});

		if(CheckCount >= 1){ //if one or more check box checked
			
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

			//very important to draw again in each addition of coordinates into boq table
		    for (i=0; i<line.length; i++) 
				{
	
				  line[i].setMap(null);
				
				}
			
		    var regionLatLngInputs  = [];
			getRegionLatLngToDrawAgain(regionLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array

			for (i = 0;i<regionLatLngInputs.length;i++){

			 var	regionLat = regionLatLngInputs[i].Latitude;
			 var regionLong = regionLatLngInputs[i].Longitude;
			 regionLatLngInputs[i] = new google.maps.LatLng(parseFloat(regionLat),parseFloat(regionLong));
			 
			  }
			  
			  Path = new google.maps.Polyline({
				  
			        path: regionLatLngInputs,
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
			else alert("Please create an area or select only 1 row to change its coordinates, thank you.");
	});

function getRegionLatLngToDrawAgain (regionLatLngInputs) {

		$("#regionTab > tbody").find('input[name="record"]').each(function(){
								   
			regionLatLngInputs.push({
			    
			"Latitude" : $(this).parent().parent().children('td[name="regionLatt"]').children('input').val(),
			"Longitude" : $(this).parent().parent().children('td[name="regionLng"]').children('input').val(),

				     });	
								  
	        	});			
	          
		}		
	
	//when clicking on map show long and lat of this point in infoWinfow
	map.addListener("click", (mapsMouseEvent) => {
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
	  
	//get coordinates button to set the map clicked coordinates in the checked row in boq
	
	google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {
					    
	document.getElementById("mouseclicklat").value=reslatitude;
	document.getElementById("mouseclicklong").value=reslongtitude;			    
	RowSelected= $('#regionTab').find('input[type=checkbox]:checked').closest("tr")[0];
	CheckCount =$('#regionTab').find('input[type=checkbox]:checked').length;//$(this).length ;

	//alert(CheckCount);
	if(CheckCount == 1){
		
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
						
	$("#regionTab input[type=checkbox]:checked").each(function () {

	var row = $(this);
	RowSelected= $(this).closest("tr")[0];
	CheckCount ++ ;
	
								});																	
							
	var rowToAdd = RowSelected.rowIndex;
								
	//very important to draw again in each addition of coordinates into boq table
	for (i=0; i<line.length; i++) 
	{
				
	line[i].setMap(null);
			
	}
	
	var regionLatLngInputs = [];
	$("#regionTab > tbody tr").eq(rowToAdd-1).find('td[name="regionLatt"]').children('input').val((Math.round(reslatitude*100000000)/100000000).toFixed(8));
	$("#regionTab > tbody tr").eq(rowToAdd-1).find('td[name="regionLng"]').children('input').val  ((Math.round(reslongtitude*100000000)/100000000).toFixed(8));	    
	getRegionLatLngToDrawAgain(regionLatLngInputs); //get again the array of latitude and longitude and fill them in  "areaLatLngInputs" array

	  for (i = 0;i<regionLatLngInputs.length;i++){
		  
	  var  regionLat = regionLatLngInputs[i].Latitude;
  	  var regionLong = regionLatLngInputs[i].Longitude;
  	  regionLatLngInputs[i] = new google.maps.LatLng(parseFloat(regionLat),parseFloat(regionLong));
  	  
  }
						  Path = new google.maps.Polyline({
						  
					        path: regionLatLngInputs,
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
		
		
    infoWindow.open(map);
  });
	  //drawing tools (line and hand)
	  
  const drawingManager = new google.maps.drawing.DrawingManager({
	  
	    drawingControl: true,
	    drawingControlOptions: {
	    position: google.maps.ControlPosition.TOP_CENTER,
	    drawingModes: [

	    google.maps.drawing.OverlayType.POLYLINE,
	      ],
	    },
	    markerOptions: {

	      icon:
	        "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png",
	    },
	    

	    polylineOptions: {
	        editable: true
	      },
	      polygonOptions: {
	        editable: true
	      },
	      map: map
	    
	  });

	// Create the initial InfoWindow.
	  $("#mapText").val(kenya);
	  
	  map.addListener("mousemove", (mapsMouseEvent)=>{

	    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));
	    	    
	  });
	  var TakingArray = [];
	  var checkedArray = [];
	  var myRoutePath=2;
	  var coordinates;
	  var line=[];
	  var line1=[];
	  var line2=[];
	  var line3=[];
	  var line4=[];
	  var Path =null;
	  var flag;
	  var i=0;
	  var clickCount = 0;
		  
	  //change Line color to red on checked checkboxes
      
    	 $("#regionTab > tbody").on("change", 'input[name="record"]', function () {
        	 
  		 TakingArray = [];
		 checkedArray = [];
		 
		if(myRoutePath !=2){
			    
		myRoutePath.setMap(null);
				
		    	for (i=0; i<line3.length; i++) 
		    	{

		    	  line3[i].setMap(null);
		    	 
		    	}
			 }
				
    		   indexRow = $(this).parent().parent().index();
    			   
    		   $('input[name="record"]').each(function(indexRow) {
    		   
    			   if (this.checked) {
        			   
    					var latt=	$("#regionTab > tbody tr").eq(indexRow).find('td[name="regionLatt"]').children('input').val();
    					var longgg=  	$("#regionTab > tbody tr").eq(indexRow).find('td[name="regionLng"]').children('input').val();

       					var pointToMark = [];

       					if(latt === "" || latt === null || longgg === "" || longgg === null){
       					}
       					else{
 		        			  
    				var pointToMark = [];
    				pointToMark = new google.maps.LatLng(parseFloat(latt),parseFloat(longgg));
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
    		
    		if(($('input[type=checkbox]:checked').length)<1){
    		
    	for (i=0; i<line3.length; i++) 
    	{

    	  line3[i].setMap(null);
    	 
    	}
    	  }
    		   }))
    		   }
    			   
    			 }  
    			  else{

        			  }
    			 })
    		   })
  	  
	  if ('${docStatus}' != "addNew" ){
		  
		  checkIfPolygone =1;///to check if the shape constructed by polygone logic
		  boqArrayBorder = [];
		  
		  if('${listRegionBorder}' != "addNew" ){
			  checkIfPolygone =1;
			  
		  boqArrayBorder = ${listRegionBorder};
		  _cupCakeHAjouzPoints = [];
		  
		  //draw the polygone on load 		  
		  for (i = 0;i<boqArrayBorder.length;i++){

			regionLatt = boqArrayBorder[i].lat;
			regionLng = boqArrayBorder[i].lng;
			_cupCakeHAjouzPoints[i] = new google.maps.LatLng(parseFloat(regionLatt),parseFloat(regionLng));

				  Path = new google.maps.Polyline({
					  
				        path: _cupCakeHAjouzPoints,
				        geodesic: true,
					    editable: true,
				        strokeColor: '#4986E7',
				        strokeOpacity: 1.0,
				        strokeWeight: 2
				        
				      });
		
				      Path.setMap(map);
				      line.push(Path);
			          line4.push(Path);
				      drawingManager.setMap(null);


					//// Construct the polygon above the polyline 
					Paths = new google.maps.Polygon({
						
					    paths: _cupCakeHAjouzPoints,
					    strokeColor: '#4986E7',
					    strokeOpacity: 0,
					    strokeWeight: 0,
					    fillColor: '#4986E7',
					    fillOpacity: 0.08,
					    
					  });
					  
					  Paths.setMap(map);
					  line.push(Paths);
					  line2.push(Paths);
									
								  }	


 
	// toggle fill region by clicking the button (Fill region )
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

    clickCount++;

	 drawingManager.setMap(null);
	    
    }
}));	

	  var TakingArray = [];
	  var checkedArray = [];
	  
	// changing color for the multiple polyline based on the selected checboxes
	  $("#regionTab > tbody").on("change", 'input[name="record"]', function () {
		
	   var i=0;
	   indexRow = $(this).parent().parent().index();
		   
	  $('input[name="record"]').each(function(indexRow) {
			   
	  if (this.checked) {
		  
	  myRoutePath.setMap(null);

		 } 

		 })
	   })
	   	  
	  
			  // END changing color for multiple polyline by clicking select all
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

				var latt=	$("#regionTab > tbody tr").eq(indexRow).find('td[name="regionLatt"]').children('input').val();
			   
				var longgg=  	$("#regionTab > tbody tr").eq(indexRow).find('td[name="regionLng"]').children('input').val();
 					var pointToMark = [];


 					
 					if(latt === "" || latt === null || longgg === "" || longgg === null){
 					}
 					else{

			pointToMark = new google.maps.LatLng(parseFloat(latt),parseFloat(longgg));
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
					
					if(checkIfPolygone===1){

					var latt=	parseFloat($(this).parent().parent().children('td[name="regionLatt"]').children('input').val());
					var longgg=	parseFloat($(this).parent().parent().children('td[name="regionLng"]').children('input').val());
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
		//delete region button
  		google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (polyline){
  	  		
		drawingManager.setMap(null);
		checkIfPolygone =0;
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
			
			$('#regionTab tbody').empty();
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

	 ///when finishing the polygon
	 google.maps.event.addListener(drawingManager, 'polygoncomplete', function (polygon) {
		 
		        coordinates = (polygon.getPath().getArray());
		        
		      });


     ///when finishing the polyline
	 google.maps.event.addListener(drawingManager, 'polylinecomplete', function (polyline) { 
			    
			  var lat,lng;
			  line=[];

		        for (i=0;i<polyline.getPath().length;i++){
			        
		        coordinates =(polyline.getPath().getArray()[i]);
			    lat=polyline.getPath().getArray()[i].lat().toFixed(8); 
		   	    lng=polyline.getPath().getArray()[i].lng().toFixed(8);     
				line.push(polyline);
				
				var markup = "<tr><td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'></td><td name='sequence'>"
					+"<input name='sequence' type='text' value='"+(parseInt(i)+1)+"' style='width:72px;'  class='form-control text-input'/></td>"
					+"<td name='regionLatt' style='width:auto;'><input name='regionLatt' type='text' value='"+lat+"' style='width:215px;' class='form-control text-input'/></td>"
					+"<td name='regionLng' style='width:auto;'>"
					+"<input name='regionLng' type='text' value='"+lng+"' style='width:215px;' class='form-control text-input'/></td></tr>";
				    $("#regionTab > tbody").append(markup);	
				    				
		        }

		        /////////resize polyline
		        drawingManager.setMap(null);
		          google.maps.event.addListener(polyline, "dragend", polylineChanged);
				  google.maps.event.addListener(polyline.getPath(), "insert_at", polylineChanged);
				  google.maps.event.addListener(polyline.getPath(), "remove_at", polylineChanged);
				  google.maps.event.addListener(polyline.getPath(), "set_at", polylineChanged);

				  //change BOQ table values when polyline changed
				  function polylineChanged(evt) {
					  streched=1;  
				  $('#regionTab tbody').empty();

					  line=[];

				   for (i=0;i<polyline.getPath().length;i++){
				   coordinates =(polyline.getPath().getArray()[i]);
				   lat=polyline.getPath().getArray()[i].lat().toFixed(8); 
				   lng=polyline.getPath().getArray()[i].lng().toFixed(8);     
				   line.push(polyline);
				   
					var markup = "<tr><td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'></td><td name='sequence'>"
						+"<input name='sequence' type='text' value='"+(parseInt(i)+1)+"' style='width:72px;'  class='form-control text-input'/></td>"
						+"<td name='regionLatt' style='width:auto;'><input name='regionLatt' type='text' value='"+lat+"' style='width:215px;' class='form-control text-input'/></td>"
						+"<td name='regionLng' style='width:auto;'>"
						+"<input name='regionLng' type='text' value='"+lng+"' style='width:215px;' class='form-control text-input'/></td></tr>";
					    $("#regionTab > tbody").append(markup);
					    					
				        }
			        
				      drawingManager.setMap(null);
					  
					 
					  
					}
					
		      });
	      
		  function getPath() {
			  streched=1;  
			  
			  var path = Path.getPath();
			  var len = path.getLength();
			  var lat=null;
			  var lng=null;
			  var coordStr = "";
			  
			  $('#regionTab tbody').empty();
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
					+"<td name='regionLatt' style='width:auto;'><input name='regionLatt' type='text' value='"+lat+"' style='width:215px;' class='form-control text-input'/></td>"
					+"<td name='regionLng' style='width:auto;'>"
					+"<input name='regionLng' type='text' value='"+lng+"' style='width:215px;' class='form-control text-input'/></td></tr>";

					$("#regionTab > tbody").append(markup);
				
			  }
			  	  
			}


			 var rowCount = $('#regionTab >tbody >tr').length;
			 if (newReg===0 && $('#regionTab >tbody >tr').length !=0){
				 
		     google.maps.event.addListener(Path, "dragend", getPath);
		 	 google.maps.event.addListener(Path.getPath(), "insert_at", getPath);
		 	 google.maps.event.addListener(Path.getPath(), "remove_at", getPath);
		 	 google.maps.event.addListener(Path.getPath(), "set_at", getPath);
		  	 newReg=1;
		  	 
}

			
	}


//to select all checkbox or unselect them all from top table of area
$('body').on('click', '#selectAllIn', function  () {

	if ($(this).hasClass('allChecked')) {
		
	$('input[type="checkbox"]', '#regionTab').prop('checked', false);
	
		} 
	else 
		{
		
	$('input[type="checkbox"]', '#regionTab').prop('checked', true);
		
		}
	
	$(this).toggleClass('allChecked');

	})
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

$("#regionname").on('keyup change', function () {
	
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

	
	
	
	  		
	
$(document).ready(function() {



	if('${SelectedIndex}' != "addNew"){
		var SelectedIndex = ${SelectedIndex};
		if('${AreasCount}' != "addNew"){
		
	var AreasCount = ${AreasCount};
	
	if(($("#regionId").val()) != "" && ($("#regionId").val()) != null){

	if(SelectedIndex === AreasCount){

		document.getElementById("btnLast").style.opacity = 0.5;
		$("#btnLast").hasClass("disabled");
		document.getElementById("btnLast").style.pointerEvents = "none";
		

		
		document.getElementById("btnNexta").style.opacity = 0.5;
		document.getElementById("btnNexta").style.pointerEvents = "none";

		
		$("#btnNexta").hasClass("disabled");
		
		}else{
			
			if(!$("#btnNexta").hasClass("disabled")){
				
				$("#btnNext").click(function(){
					
					var param ="${pageContext.request.contextPath}/RegionFormView?RegionID="+$("#regionId").val()+"&NavAction=1";

					window.location.href =param;
		
				});
	
			}
			if(!$("#btnLst").hasClass("disabled")){
				
				$("#btnLst").click(function(){
					
					var param ="${pageContext.request.contextPath}/RegionFormView?RegionID="+$("#regionId").val()+"&NavAction=4";

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
			
			var param ="${pageContext.request.contextPath}/RegionFormView?RegionID="+$("#regionId").val()+"&NavAction=0";
			window.location.href =param;
				
		}
		 });
		$("#btnFrst").click(function(){

		if(!$("#btnFrst").hasClass("disabled")){
				
			var param ="${pageContext.request.contextPath}/RegionFormView?RegionID="+$("#regionId").val()+"&NavAction=3";
			window.location.href =param;
					
			}
			 });
	}
	
	}}
}
	$("#label-1").text((SelectedIndex)+"/"+AreasCount);

	
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
	$("#selectnav").autocomplete({
			
		    source: function(request, response) {
			    
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllRegions',
		                 data: {
								"regionID" : $("#selectnav").val(),
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
						
						this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
						var param ="${pageContext.request.contextPath}/RegionFormView?RegionID="+(ui.item[0])+"&NavAction=2";
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
       					});
	
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
	



$("#saveButton").click(  function() {

	if ($("#regionname").val()== '') {
		
		alert('Region Name cannot be NULL');
	
	return false;

	}
	
	sortSequence ();
	getselectedrowsForRegion();
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


if('${listRegionBorder}' != "addNew" ){

	boqArrayBorder = ${listRegionBorder};
	var rowCount;
	var regionLat ;
	var regionLong ;
	for (i = 0;i<boqArrayBorder.length;i++){

		 regionLat = boqArrayBorder[i].lat;
		 regionLong = boqArrayBorder[i].lng;
		 seqId =  boqArrayBorder[i].id;
	
		var itemRow = "<tr>";
		
		itemRow= itemRow + "<td><input type='checkbox' name='record' style='vertical-align: middle;margin-left:15px;'></td><td name='sequence'>"
		itemRow= itemRow +"<input name='sequence' type='text' value='"+(parseInt(i)+1)+"' style='width:72px;' readonly class='form-control text-input'/></td>"	 
		itemRow =itemRow + "<td name='regionLatt' style='width:auto;'><input name='regionLatt' style='width:215px;' value='" + regionLat + "' class='form-control text-input' /></td>";
		itemRow =itemRow + "<td name='regionLng' style='width:auto;'><input  name='regionLng' style='width:215px;' value='" + regionLong + "' class='form-control text-input'/></td>";
		itemRow =itemRow + "<td name='seqId' style='width:auto;' hidden><input  name='seqId' style='width:215px;' value='" + seqId + "' class='form-control text-input' hidden/></td>";
		itemRow =itemRow + "</tr>";
		
		$("#regionTab > tbody").append(itemRow);

	}
	
}
	
}

//function to get selected rows for save or delete
function getselectedrowsForRegion () {
	
	dictRegion = [];
	$("#regionTab > tbody").find('input[name="record"]').each(function(){
		    dictRegion.push({
		    "Latitude" : $(this).parent().parent().children('td[name="regionLatt"]').children('input').val(),
		    "Longitude" : $(this).parent().parent().children('td[name="regionLng"]').children('input').val(),
		    "RegionBorderID" : $(this).parent().parent().children('td[name="seqId"]').children('input').val(),
		    "sequence" : $(this).parent().parent().children('td[name="sequence"]').children('input').val(),
			    
			     });	

    	});
	
      
}

function sortSequence () {
	
	var i = 0 ;
	var rowCount = $("#regionTab > tbody").children().length;

	for (i = 0;i<rowCount;i++){
		   $("#regionTab > tbody tr").eq(i).find('input[name=sequence]').val(i+1);

	}
      
}

	$("#deleteButton").click(  function() {
		
		var deleteArray = [];
		deleteArray.push($("#regionId").val());

		$.ajax({
			type: "GET",
			url: "${pageContext.request.contextPath}/RegionDelete",
			dataType: "json",
			data: {
				"RegionId": deleteArray
			},
			success: function (data) {
				location.replace("${pageContext.request.contextPath}/RegionListView");
			},
			error: function (error) {
				console.log("The error is " + error);
			}
		});
		
	    	//location.replace("${pageContext.request.contextPath}/RegionListView");
	})

    function saverowsintables (){
	    
	   sortSequence ();
       var RegionId = document.getElementById("regionId").value;
       var RegionName = document.getElementById("regionname").value;
       var RegionCode = document.getElementById("regioncode").value;
	   var creationDate = document.getElementById("createdate").value;
	   var lastModifieddate = document.getElementById("lstmodifdate").value;
	   var status = document.getElementById("ordstat").value; 

	   var token =  $('input[name="csrfToken"]').attr('value'); 
			
		     	$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/RegionFormSave",
					dataType : "json",
					headers: {
	                      'X-CSRFToken': token 
	                  },
					data : {
						
						"status": status,
						"type": '${docStatus}',
						"dictParameterRegion":dictRegion,
						"RegionID" : RegionId,
						"creationDate" : creationDate,
						"lastModifieddate" : lastModifieddate,
						"RegionName" : RegionName,
						"RegionCode" : RegionCode,
						"email": $("#email").val(),
						"password":$("#password").val(),
				  	    "emailTo": $("#emailTo").val(),
					    "ccmail": $("#ccmail").val(),
					    "subject": $("#subject").val(),
					    "message": $("#message").val(),
					    "slctDelRegion":slctDelRegion,
						"streched":streched,
				},
					success : function(data) {

						regionId.value=data.REGIONID;
						var param ="${pageContext.request.contextPath}/RegionFormView?RegionID="+$("#regionId").val()+"&NavAction=2";
						location.replace(param);
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

        }

});


</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
	async defer></script>

</html>