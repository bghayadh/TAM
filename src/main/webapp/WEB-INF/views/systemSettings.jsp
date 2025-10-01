<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="ISO-8859-1">
<title>System Settings</title>

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

<style>
.p {
	font-size: large;
	font-weight: bold;
}

.box {
	font-size: large;
}

.row {
	margin-bottom: 5px;
}

.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 100px;
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
.nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
</style>

</head>


<body>
	<c:set var = "pg" value = "setup"/>

	<%@ include file="header.jsp" %>
	<p></p>
	<div class="container-fluid">
		<div class="row">

			<div class="col-md-1">
				<div class="form-group">
					<div class="input-group-prepend">

						<span class="input-group-text" style="color: green">System Settings</span> 
							&nbsp;
					</div>
				</div>
			</div>
			&emsp;
			&emsp;
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1"
						data-target-input="nearest">
						<span class="input-group-text">Created Date</span> 
						<input type="text" id="createddate" readonly value="${createdDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1" style="width:250px;" />
						<div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker2"
						data-target-input="nearest">
						<span class="input-group-text">Last Modify Date</span> 
						<input type="text" id="lastmodifieddate" readonly value="${lastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2" style="width:250px;" />
						<div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
					
			<div class="col-md-2" style="display:none;">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">ID</span> 
						<input type="text" id="sysSettingID" value="${sysSettingID}" class="form-control text-input" />
					</div>
				</div>
			</div>
					&emsp;
					&emsp;
					&emsp;
					&emsp;
					

			
			<div class="col-md-2" style="text-align:right;">
				<div class="btn-group pull-right">
				<i>&nbsp</i><span class="dot"></span>
				<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;">Saved</label>							
				</div>
			</div>
		</div>
		<p></p>

	</div>
<br>
	<div class="container-fluid">

		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: -20px;">
					<li class="nav-item"><a class="nav-link active" id="custom-tabs-one-home-tab" data-toggle="tab" href="#custom-tabs-one-home" role="tab" aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">GENERAL INFORMATION</a></li> 					

					<li class="nav-item ml-auto">
						<button type="button" id="saveButton"  class="btn btn-primary BtnActive">
						<i class="fa fa-save"></i> Save </button>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<br>
	<div class="container-fluid">
		<div class="tab-content" id="custom-tabs-one-tabContent">
			<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">			
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Country</span> 
							<input type="text" id="sysCountry" value="${country}" class="form-control text-input" />
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Latitude</span> 
							<input type="text" id="lat" value="${lat}" class="form-control text-input" />
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Longitude</span> 
							<input type="text" id="longitude" value="${longitude}" class="form-control text-input" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Language</span> 
							<input type="text" id="sysLanguage" value="${language}" class="form-control text-input" />
						</div>
					</div>
				</div>					
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Currency</span>
							<input type="text" id="sysCurrency" value="${currency}" class="form-control text-input" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Username</span> 
							<input type="text" id="username" value="${username}" class="form-control text-input" />
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Password</span> 
							<input type="text" id="pass" value="${pass}" class="form-control text-input" />
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">IP Address</span> 
							<input type="text" id="ipAddress" value="${ipAddress}" class="form-control text-input" />
						</div>
					</div>
				</div>					
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width:135px;">Upload Path</span> 
							<input type="text" id="path" value="${path}" class="form-control text-input" />
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
				

	<script type='text/javascript'>
	
		$(".text-input").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({
				"background-color" : "orange"
			});
		});

		if ('${status}' != "New") {
			$("#formStatus").text("Saved");
			$('.dot').css({
				"background-color" : "chartreuse"
			});
		} 
		else {
			$("#formStatus").text("New");
			$('.dot').css({
				"background-color" : "orange"
			});
		}
		

		$(document).ready(function() {

			$("#saveButton").click(  function() {

				crtDate =$("#createddate").val();
				parsedCrtDate = Date.parse(crtDate);
				if (isNaN(parsedCrtDate) == true) {
					 alert(' Creation Date is not valid');
					 return false;
				}

				lstModfDate =$("#lastmodifieddate").val();
				parsedLstModfDate = Date.parse(lstModfDate);
				if (isNaN(parsedLstModfDate) == true) {
					alert('Last Modified Date is not valid');
					return false;
				}

				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/sysSettingSave",
					dataType : "json",
					data : {
						"sysSettingID" : $("#sysSettingID").val(),
						 "country" : $("#sysCountry").val(),
						 "lat" : $("#lat").val(),
						 "longitude" : $("#longitude").val(),
						 "language" : $("#sysLanguage").val(),
						 "currency": $("#sysCurrency").val(),
						 "username": $("#username").val(),
						 "pass": $("#pass").val(),
						 "path":$("#path").val(),
						 "ipAddress":$("#ipAddress").val(),
						 "creationDate" : $("#createddate").val(),
					},
					success : function(data) {
						var param ="${pageContext.request.contextPath}/systemSettings";
						location.replace(param);
					},
					error : function(error) {
						console.log("The error is " + error);
					}

					});
									 

				 })

			});
	
	</script>
</body>
</html>