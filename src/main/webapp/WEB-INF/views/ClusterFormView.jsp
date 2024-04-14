
<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
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
<script
	src="${pageContext.request.contextPath}/resources/js/areaF_BoqPopup.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />

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

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	width: 350px;
	z-index: 9999;
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

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

#textalert {
	font-size: 15px;
	color: red;
	margin-left: 10px;
	display: inline-block;
	margin-bottom: 0px;
	padding-bottom: 0px;
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
	<!--  end of general head page -->
	<p></p>

	<div class="container-fluid">
		<div class="row">

			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Cluster
							ID</span> <input type="text" id="clusterid" value="${clusterId}"
							class="form-control text-input" style="width: 300px;" readonly />
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
						<span class="input-group-text">Other Cluster</span> <input type="text" id="selectnav" value="${selectnav}"
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
							onclick='window.location.href = "${pageContext.request.contextPath}/ClusterListView"'
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

				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Cluster Name</span> <input
									type="text" id="clustername" value="${clusterName}"
									class="form-control text-input" />
							</div>
						</div>
					</div>


				</div>



				<div class="row">
					<div class="col-md-6">
						<div class="form-group">

							<div class="input-group-prepend">
								<span class="input-group-text">Area</span> <input type="text"
									id="areaID" value="${AreaID}" style="width: 550px"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>








				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Longitude</span> <input
									type="text" id="arealng" value="${clusterLong}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>



				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Latittude</span> <input
									type="text" id="arealatt" value="${clusterLat}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
				</div>




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
											<button type="button" id="selectAll" class="main">
												<span class="sub"></span>Select
											</button>
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
							type="button" class="add-row" value="Add Row">
						<button type="button" class="delete-row">Delete Row</button>
					</form>
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

	$("#Approvewh").click(function() {

		var Status = $("#ordstat").val();
		$("#ordstat").val('approved').trigger('change');
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

	})

	$("#Activatewh").click(function() {

		var Status = $("#ordstat").val();
		$("#ordstat").val('activated').trigger('change');
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

	})

	$("#Deactivatewh").click(function() {

		var Status = $("#ordstat").val();
		$("#ordstat").val('deactivated').trigger('change');
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

	})

	$("#Cancelwh").click(function() {

		var Status = $("#ordstat").val();
		$("#ordstat").val('cancelled').trigger('change');
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});

	})

	var navList = [];
	var iCode = "${clusterId}";
	var listItemsNav = $
	{
		listItemsNav
	};
	var indexItm;

	if (iCode != "") {
		if (listItemsNav != "noList" && listItemsNav.length > 1) {
			var size = new TextEncoder().encode(JSON.stringify(listItemsNav)).length;
			var kiloBytes = size / 1024;
			var megaBytes = kiloBytes / 1024;
			var browser = platform.name;
			if (browser == "Samsung Internet" && megaBytes > 2) {
				localStorage.setItem("clusterList", JSON
						.stringify(listItemsNav));
			} else if (browser.includes("Safari") && megaBytes > 5) {
				localStorage.setItem("clusterList", JSON
						.stringify(listItemsNav));
			} else if (megaBytes > 10) {
				localStorage.setItem("clusterList", JSON
						.stringify(listItemsNav));
			} else {
				navList = listItemsNav;
				localStorage.removeItem("clusterList");
			}

			findIndex();
		} else {
			navList = JSON.parse(localStorage.getItem("clusterList"));
			if (navList != null && navList.length > 1) {

				findIndex();
			} else {
				$(".nextprvItems").addClass("hide-row ");
				$(".pad").removeClass("hide-row ");
			}
		}
	} else {
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");
	}
	function findIndex() {
		for (var i = 0; i < navList.length; i++) {

			if (navList[i] == iCode) {
				if (i == (navList.length - 1)) {
					$("#btnNexta").addClass("disabled");
				} else if (i == 0) {

					$("#btnPrva").addClass("disabled");
				}
				indexItm = i;

				//return;
			}

		}
	}

	/////////////////////////////////
	$("input").change(function() {

		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});
	});

	if ('${docStatus}' == "addNew") {
		$("#formStatus").text("New");
		$('.dot').css({
			"background-color" : "orange"
		});
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");
	}

	$("#clustername").on('keyup change', function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});
	});

	$("#areaID").on('keyup change', function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});
	});
	$(".add-row").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});
	});

	$(".delete-row").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});
	});

	$("#yaratab > tbody").change(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({
			"background-color" : "orange"
		});
	});

	$(document).ready(function() {

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

						// ADD NEW Row in finance Tab
						var dict = [];
						var alertFlag;

	$("#saveButton").click(function() {
			alertFlag = '0';

			// Validate the area Id and name cannot be null
			if ($("#areaID").val() == '') {
				alert('Area  cannot be NULL');
				return false;
			}

			if ($("#clustername").val() == '') {
				alert('cluster Name cannot be NULL');
				return false;
			}

			getselectedrows();

			if (alertFlag == '1') {

				consol.log("Passed from this function of alert");
				return false;
			}

			if (alertFlag == '2') {

				alert("**This ID is already Taken Please Enter Another ID");
				return false;
			}

			saverowsintables();

		});

	

		boqArray = [];
     	if ('${ListClusterFinance}' != "addNew") {
    		
		boqArray = ${ListClusterFinance};

		for (i = 0; i < boqArray.length; i++) {

			var itemRow = "<tr>";
			
			itemRow = itemRow + "<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td>"
			itemRow = itemRow + "<td name='startDate'><input type='date' name='startdate' value='" + boqArray[i].startDate + "' style='width:200px;' class='form-control text-input'/></td>";
			itemRow = itemRow + "<td name='endDate'><input type='date' name='enddate' value='" + boqArray[i].endDate + "' style='width:200px;' class='form-control text-input' /></td>";
			itemRow = itemRow + "<td name='no2gSites'><input type='text'  value='" + boqArray[i].number2gSites +"' style='width:200px;' class='form-control text-input'></td>";
			itemRow = itemRow + "<td name='profitAndLoss2g'><input type='text'  value='" + boqArray[i].pl2g +"' style='width:200px;' class='form-control text-input'></td>";
			itemRow = itemRow + "<td name='no2g3gSites'><input type='text'  value='" + boqArray[i].number2g3gSites +"' style='width:200px;' class='form-control text-input'></td>";
			itemRow = itemRow + "<td name='profitAndLoss2g3g'><input type='text'  value='" + boqArray[i].pl2g3g +"' style='width:200px;' class='form-control text-input'></td>";
			itemRow = itemRow + "<td name='no2g3g4gSites'><input type='text'  value='" + boqArray[i].number2g3g4gSites +"' style='width:200px;' class='form-control text-input'></td>";
			itemRow = itemRow + "<td name='profitAndLoss2g3g4g'><input type='text'  value='" + boqArray[i].pl2g3g4g +"' style='width:200px;' class='form-control text-input'></td>";
			itemRow = itemRow + "<td name='totalSites'><input type='text'  value='" + boqArray[i].totalSites +"' style='width:200px;' class='form-control text-input' ></td>";
			itemRow = itemRow + "<td name='totalSumProfit'><input type='text'  value='" + boqArray[i].sumProfitLoss +"' style='width:200px;' class='form-control text-input' ></td>";
			itemRow = itemRow + "<td name='id'><input type='text'  value='" + boqArray[i].id +"' style='width:200px;' readonly class='form-control text-input'></td>";

			itemRow = itemRow + "</tr>";
			$("#yaratab > tbody").append(itemRow);

		}
     	}
	

$("#deleteButton").click(function() {
	var deleteArray = [];
	deleteArray.push($("#clusterid").val());


	$.ajax({
			type : "GET",
			url: "${pageContext.request.contextPath}/ClusterDelete",
			
			dataType : "json",
			data : {
				"clusterID" : deleteArray
			},
			success : function(data) {


			},
			error : function(error) {
				console.log("The error is "+ error);
			}
		});

	location.replace("${pageContext.request.contextPath}/ClusterListView");
	
				})

$(".add-row").click(function() {

		var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td><td name='startDate'>"
				+ "<input name='startdate' type='date' style='width:200px;'class='form-control text-input' /></td>"

				+ "<td name='endDate'>"
				+ "<input name='enddate' type='date' style='width:200px;' class='form-control text-input' /></td>"
				+ "<td name='no2gSites'>"
				+ "<input  type='text' style='width:200px;' value= 0 class='form-control text-input'></td>"
				+ "<td name='profitAndLoss2g'><input style='width:200px;'type='text' value= 0 class='form-control text-input'></td>"
				+ "<td name='no2g3gSites'><input  style='width:200px;' type='text' value= 0 class='form-control text-input'></td>"
				+ "<td name='profitAndLoss2g3g'><input style='width:200px;' type='text'value= 0 class='form-control text-input'></td>"
				+ "<td name='no2g3g4gSites'><input  style='width:200px;' type='text' value=0 class='form-control text-input'></td>"
				+ "<td name='profitAndLoss2g3g4g'><input type='text'  style='width:200px;' value= 0 class='form-control text-input'></td>"
				+ "<td name='totalSites'><input type='text' style='width:200px;' value= 0 class='form-control text-input'></td>"
				+ "<td name='totalSumProfit'><input type='text' style='width:200px;' value= 0 class='form-control text-input'></td>"
				+ "<td name='id' ><input name='id'type='text' style='width:200px;'value= 0 readonly class='form-control text-input'></td></tr>"

				$("#yaratab > tbody").append(markup);

		var myDiv = document.getElementById("yaratab");
		myDiv.scrollTop = myDiv.scrollHeight;
		//auto complete for Itemcode   
	$('#yaratab tr td input').change(function() {

		var cell = $(this).val();
		var column_name = $(this).parent().attr('name');
	
		if ((column_name == 'no2gSites')) {

			// validate Qty
			if (($.isNumeric(cell)) == false) {
				
				alert('Qty is  not Numeric');
				this.focus();
				
				return false;
			}

		}
		if ((column_name == 'profitAndLoss2g')) {
			
			// validate Qty
			if (($.isNumeric(cell)) == false) {
				
				alert('Qty is  not Numeric');
				this.focus();
				
				return false;
			}
	
		}
		if ((column_name == 'no2g3g4gSites')) {
			
			// validate Qty
			if (($.isNumeric(cell)) == false) {
				alert('Qty is  not Numeric');
				
				this.focus();
				
				return false;
			}

		}
		if ((column_name == 'no2g3gSites')) {
			// validate Qty
			
			if (($.isNumeric(cell)) == false) {
				
				alert('Qty is  not Numeric');
				
				this.focus();
				
				return false;
			}
	
		}
	
		if ((column_name == 'profitAndLoss2g3g')) {
			// validate Qty
			if (($.isNumeric(cell)) == false) {
				
				alert('Qty is  not Numeric');
				
				this.focus();
				
				return false;
			}
	
		}
	
		if ((column_name == 'profitAndLoss2g3g4g')) {
			// validate Qty
			if (($.isNumeric(cell)) == false) {
				
				alert('Qty is  not Numeric');
				
				this.focus();
				
				return false;
			}

		}
	
		if ((column_name == 'totalSites')) {
			// validate Qty
			if (($.isNumeric(cell)) == false) {
				
				alert('Qty is  not Numeric');
				
				this.focus();
				
				return false;
				
			}
	
		}
	
		if ((column_name == 'totalSumProfit')) {
			// validate Qty
			if (($.isNumeric(cell)) == false) {
				
				alert('Qty is  not Numeric');
				
				this.focus();
				
				return false;
			}
	
		}
	
	});
	
});
	
			$('body').on('click','#selectAll',function() {
	
						if ($(this).hasClass('allChecked')) {
							$('input[type="checkbox"]', '#yaratab')
									.prop('checked', false);
						} else {
							$('input[type="checkbox"]', '#yaratab')
									.prop('checked', true);
						}
						$(this).toggleClass('allChecked');
	
					})
	
					var slctDel = [];
	
		$(".delete-row").click(function() {
				
				slctDel = [];
				$("#yaratab > tbody").find('input[name="record"]').each(function() {if ($(this).is(":checked")) {

					slctDel.push($(this).parent().parent().children('td[name="id"]').children('input').val());

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

		$("#yaratab > tbody").find('input[name="record"]').each(function() {

			if ($(this).is(":checked")) {$(this).parents("tr").remove();

			}
	
			});
		});
	
			// end delete row

						//function to get selected rows for save or delete
						function getselectedrows() {

							dict = [];

							$("#yaratab > tbody").find('input[name="record"]').each(function() {

								var StartDate = ($(this).parent().parent().children('td[name="startDate"]').children('input').val());

									if (StartDate == "") {
										alert('Start Date  cannot be null in Table at Row: '+ ($(this).parent().parent().index() + 1));
										alertFlag = '1';
										return false;

									}

								var EndDate = ($(this).parent().parent().children('td[name="endDate"]').children('input').val());

									if (EndDate == "") {
										alert('End Date  cannot be null in Table at Row: '+ ($(this).parent().parent().index() + 1));
										alertFlag = '1';
										return false;

									}

						if ($(this).is(":checked")) {



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
								"Id" : $(this).parent().parent().children('td[name="id"]').children('input').val(),

							});

				}


											});

						}
						$("#areaID")
								.autocomplete(
										{

											source : function(request, response) {

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllAreas',
															data : {
																"areaId" : $(
																		"#areaID")
																		.val(),
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.listAreas);
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
												areaID.value = (ui.item ? ui.item[0]
														+ ":" + ui.item[1]
														: '');
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $('<li class="each">').data(
									"item.autocomplete", item).append(
									'<div class="acItem"><span class="name" style="font-weight:bold">'
											+ item[0]
											+ '</span><br><span class="desc">'
											+ item[1] + '</span></div></li>')
									.appendTo(ul);
						};
						$("#areaID").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						}); //// ENd of Autocomplete for Area ID

						function saverowsintables() {

							var clusterId = document
									.getElementById("clusterid").value;

							var clusterName = document
									.getElementById("clustername").value;
							var area = document.getElementById("areaID").value;
							var Longitude = document.getElementById("arealng").value;
							var Latitude = document.getElementById("arealatt").value;
							var creationDate = document
									.getElementById("createdate").value;
							var lastModifieddate = document
									.getElementById("lstmodifdate").value;
							var status = document.getElementById("ordstat").value;

							$
									.ajax({
										type : "GET",
										url : "${pageContext.request.contextPath}/ClusterFormSave",
										dataType : "json",
										data : {
											"status" : status,
											"type" : '${docStatus}',
											"dictParameter" : dict,
											"clusterID" : clusterId,
											"clusterName" : clusterName,
											"creationDate" : creationDate,
											"lastModifieddate" : lastModifieddate,
											"Area" : area,
											"Longitude" : Longitude,
											"Latitude" : Latitude,
											"email" : $("#email").val(),
											"password" : $("#password").val(),
											"emailTo" : $("#emailTo").val(),
											"ccmail" : $("#ccmail").val(),
											"subject" : $("#subject").val(),
											"message" : $("#message").val(),
											"slctDel" : slctDel,

										},
										success : function(data) {

											clusterid.value = data.clusterID;

											var param = "${pageContext.request.contextPath}/ClusterFormView?clusterID="+ $("#clusterid").val()+"&NavAction=2";
											location.replace(param);

										},
										error : function(error) {
											console.log("The error is "+ error);
										}
									});

						}

						var prev = indexItm;
						var nxt = indexItm;
						if (navList != "" && navList != null) {

							for (var i = 0; i < 5; i++) {
								prev--;
								if (navList[prev] != 'undefined'
										&& navList[prev] != null && prev >= 0) {

									$("#selectnav").append(
											new Option(navList[prev], "value"));
								}
							}
							$("#selectnav").append(
									new Option(navList[indexItm], "value"));
							for (var i = 0; i < 5; i++) {
								nxt++;

								if (navList[nxt] != 'undefined'
										&& navList[nxt] != null) {

									$("#selectnav").append(
											new Option(navList[nxt], "value"));
								}
							}

							if('${SelectedIndex}' != "addNew"){
								var SelectedIndex = ${SelectedIndex};
								if('${ClustersCount}' != "addNew"){

									
							var ClustersCount = ${ClustersCount};
							
							if(($("#clusterid").val()) != "" && ($("#clusterid").val()) != null){

							if(SelectedIndex === ClustersCount){
								
				        		document.getElementById("btnLast").style.opacity = 0.5;
				        		$("#btnLast").hasClass("disabled");
				        		document.getElementById("btnLast").style.pointerEvents = "none";
				        		
				        		document.getElementById("btnNexta").style.opacity = 0.5;
				        		document.getElementById("btnNexta").style.pointerEvents = "none";

								
								$("#btnNexta").hasClass("disabled");
								
								}else{
									
									if(!$("#btnNexta").hasClass("disabled")){
										
										$("#btnNext").click(function(){
											
											var param ="${pageContext.request.contextPath}/ClusterFormView?clusterID="+ $("#clusterid").val()+"&NavAction=2";

											window.location.href =param;
								
										});
							
									}
									if(!$("#btnLst").hasClass("disabled")){
				        				
				        				$("#btnLst").click(function(){
				        					
											var param ="${pageContext.request.contextPath}/ClusterFormView?clusterID="+ $("#clusterid").val()+"&NavAction=4";
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
										
										var param ="${pageContext.request.contextPath}/ClusterFormView?clusterID="+ $("#clusterid").val()+"&NavAction=0";
										window.location.href =param;
										
									 });
								}
								$("#btnFrst").click(function(){

				        			if(!$("#btnFrst").hasClass("disabled")){
				        					
										var param ="${pageContext.request.contextPath}/ClusterFormView?clusterID="+ $("#clusterid").val()+"&NavAction=3";
				        				window.location.href =param;
				        						
				        				}
				        				 });

							}
							
							}}
						}
							$("#label-1").text((SelectedIndex)+"/"+ClustersCount);
							

							 $("#selectnav").autocomplete({
					    			
					    		    source: function(request, response) {
					    			    
					    		             $.ajax({
					    		                 type: "GET",
					    		                 contentType: "application/json; charset=utf-8",
					    		                 url: '${pageContext.request.contextPath}/GetAllClusters',
					    		                 data: {
					    								"clusterID" : $("#selectnav").val(),
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
					    						
					    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
					    						
					    						var param ="${pageContext.request.contextPath}/ClusterFormView?clusterID="+(ui.item[0])+"&NavAction=2";
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
					    	

						}

					});
</script>



</html>