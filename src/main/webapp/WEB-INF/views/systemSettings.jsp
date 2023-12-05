<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="ISO-8859-1">
<title>System Settings Form View</title>

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
	<c:set var = "page" value = "setup"/>

	<%@ include file="header.jsp" %>
	<p></p>
	<div class="container-fluid">
		<div class="row">

			<div class="col-md-2">
				<div class="form-group">
					<div class="input-group-prepend">

						<span class="input-group-text" style="color: green">System Settings
							Form</span> 
							&nbsp;
						
					</div>

				</div>
			</div>
			<div class="col-md-3">
						<div class="form-group">
							<div class="input-group-prepend" id="datetimepicker1"
								data-target-input="nearest">
								<span class="input-group-text">Created Date</span> <input
									type="text" id="createdate" readonly value="${createdDate}"
									class="form-control datetimepicker-input"
									data-toggle="datetimepicker" data-target="#datetimepicker1" />
								<div class="input-group-append" data-target="#datetimepicker1"
									data-toggle="datetimepicker">
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
								<span class="input-group-text">Last Modify Date</span> <input
									type="text" id="lstmodifdate" readonly
									value="${lastModifiedDate}"
									class="form-control datetimepicker-input"
									data-toggle="datetimepicker" data-target="#datetimepicker2" />
								<div class="input-group-append" data-target="#datetimepicker2"
									data-toggle="datetimepicker">
									<div class="input-group-text">
										<i class="fa fa-calendar"></i>
										
									</div>
								</div>
							</div>
						</div>
					</div>
					&emsp;
					&emsp;
					&emsp;
					&emsp;
					&emsp;
					&emsp;
					
					
				
			
			<div class="col-md-2" style="text-align:right;">
				<div class="btn-group pull-right">
				     
					<div class="glyph">
                    <span class="dot" data-placement="top"></span><label
							for="formStatus" id="formStatus">Save</label>

					</div>

				</div>
			</div>
		</div>
		<p></p>

	</div>

	<div class="container-fluid">

		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: -20px;">
					<li class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">GENERAL INFORMATION</a></li>

 					
					<!-- Here maher should place the warehouse tab header -->


					<li class="nav-item ml-auto">


						<button type="button" id="deleteButton"
							class="btn btn-primary BtnActive">
							<i class="fa fa-trash"></i> Delete
						</button>

						<button type="button" id="saveButton" onclick='window.location.href = "${pageContext.request.contextPath}/systemSettings?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-save"></i> Save </button>
						  
					</li>

				</ul>

			</div>
		</div>

	</div>


	<p></p>


	<div class="container-fluid">
		<div class="tab-content" id="custom-tabs-one-tabContent">
			<div class="tab-pane fade show active" id="custom-tabs-one-home"
				role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">



				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Country</span> <input
									type="text" id="fname" value="${Country}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Language</span> <input
								type="text" id="mname" value="${Language}"
								class="form-control text-input" />
						</div>
					</div>

					
				</div>


				<div class="row">
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Currency</span> <input
								type="text" id="lname" value="${Currency}"
								class="form-control text-input" />
						</div>
				    </div>
				</div>
				
			</div>
		</div>
	</div>
				
				
				<!-- ********************************** list role ********************************** -->

				


				


				
				
				
			

			<!--  Here Maher should place his warehouse tab area -->

	
	
	
			


	<script type='text/javascript'>
		$("#Country").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({
				"background-color" : "orange"
			});

			$("#Language").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({
					"background-color" : "orange"
				});
			});

			$("#Currency").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({
					"background-color" : "orange"
				});
			});
		});

		if ('${listUser}' != "addNew") {
		} else {
			// set status to new and green while new record
			$("#formStatus").text("New");
			$('.dot').css({
				"background-color" : "orange"
			});
		}
		

		$(document)
				.ready(
						function() {
							var roleList = [];
							var getroleList = [];
							
							//****************************** fetch Role table ******************************

							$.ajax({
										type : "GET",
										url : "${pageContext.request.contextPath}/getUserRole",
										dataType : "json",
										data : {},
										success : function(data) {
											console.log("success");
											roleList = data.roleList;
											getroleList = data.getroleList;
											if (roleList != null) {
												fetchRoleList();
											} else {
												console.log("no role data");
											}
										},
										error : function(error) {
											console
													.log("The error is "
															+ error);
										}
									});
											
							
							async function fetchRoleList(){ 
								
								
								var itemRow = "<div class='row'>";
								var index=0;
								var ischecked = "";
								for(var i =0;i<roleList.length;i++){
									if(getroleList != null){
									for(var j=0;j<getroleList.length;j++){
										
										console.log(roleList[i]);
										console.log(getroleList[j]);
										if(  JSON.stringify(getroleList[j].trim()) == JSON.stringify(roleList[i].trim())){
											console.log("true");
											ischecked = "checked";
											//break;
											}
										}
									}
									if(index === 2 || index<2){																				
										itemRow +="<div class='col-md-2' style='padding-right: 15px; padding-top: 20px;' name='roleName"+i+"'><input type='checkbox' name='check' "+ischecked+">"; 

										itemRow +="<span class='percent col-sm' style='text-align: center;'>  "+roleList[i]+"</span></div>"; 										
										index++;
										}else{
											itemRow +="</div><div class='row'>"
											itemRow +="<div class='col-md-2' style='padding-right: 15px; padding-top: 20px;  padding-bottom: 20px;' name='roleName"+i+"'><input type='checkbox' name='check' "+ischecked+">"; 
											itemRow +="<span class='percent col-sm' style='text-align: center;'>  "+roleList[i]+"</span></div>"; 										
											
											index = 1;
											}
									console.log("make table");
									ischecked = "";
									}
								itemRow +="</div>";
								$("#roleTable").append(itemRow);
								console.log("finish table");
								getroleList = [];
							}
																											
							$("#saveButton")
									.click(
											function() {
												console.log('saved now');
												var j = 0;
												$("#roleTable").find('input[name="check"]').each(function(){
													console.log("enter check");
													if($(this).is(":checked")){
														console.log("i have check");
														var ch = $(this).Country().Language().Currency('div[Country="Country'+j+'"]').Language('span').text();
													//	$(this).parent().parent().children('td[name="itemCode"]').children('input').val()
														console.log(ch);
														roleChecked.push(ch);
														}else{
															console.log("no role checked");
															}
													j++;
													});

											//	if ($("#username").val() == '') {
												//	alert('user name cannot be Null');
													//return false;
												//}

											//	if ($("#pass").val() == '') {
												//	alert('password cannot be Null');
													//return false;
												//}

											//	 if ($("#emailaddress").val() == '') {
												//	alert('email address cannot be Null');
												//	return false;
											//	}

												// validate creatd date cannot be null
												val = $("#createdate").val();
												val1 = Date.parse(val);
												console.log('date is  : ' +val1);
												if (isNaN(val1) == true) {
													alert(' Create Date is not valid');
													return false;
													$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
												}

												//validate modified date cannot be null
												val = $("#lstmodifdate").val();
												val1 = Date.parse(val);
												console.log('date is  : ' +val1);
												if (isNaN(val1) == true) {
													alert(' Modified Date is not valid');
													return false;
													$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
												}

									//			var username = document
										//				.getElementById("username").value;
												var Country = document
														.getElementById("Country").value;
												var Language = document
														.getElementById("Language").value;
												var createdDate = document
														.getElementById("createdate").value;
												var lastModifiedDate = document
														.getElementById("lstmodifdate").value;
												var Currency = document
														.getElementById("Currency").value;
											//	var emailAddress = document
												//		.getElementById("eadd").value;
											//	var mobileNumber = document
												//		.getElementById("mobnum").value;
											//	var address = document
												//		.getElementById("address").value;
											//	var cirlce_city = document
												//		.getElementById("circlecity").value;
											//	var cirlce_country = document
												//		.getElementById("circlecountry").value;
											//	var circle_state = document
												//		.getElementById("circlestate").value;
											//	var circleID = document
												//		.getElementById("circle_id").value;
											//	var pass = document
												//		.getElementById("pass").value;
												
												
							//					var check4 = document
										//				.getElementById("updatepass").value;
										//		var update;
										//		if (updatepass.checked == true) {
											//		update = '1';
											//	} else {
											//		update = '0';
											//	}

											//	var check5 = document
											//			.getElementById("logout").value;
											//	var log;
												//if (logout.checked == true) {
												//	log = '1';
												//} else {
												//	log = '0';
												//}

											//	console.log("username"
													//	+ username);
											//	console.log("pass" + pass);
												console.log("Country" + Country);
												console.log("Language" + Language);
											console.log("createdDate"
														+ createdDate);
												console.log("lastModifiedDate"
														+ lastModifiedDate);
												console.log("Currency" + Currency);
											//	console.log("emailaddress"
												//		+ emailAddress);
											//	console.log("mobnum"
												//		+ mobileNumber);
											//	console
												//		.log("address"
													//			+ address);
										//		console.log("circlecity"
											//			+ cirlce_city);
											//	console.log("cirlcecountry"
												//		+ cirlce_country);
											//	console.log("circlestate"
												//		+ circle_state);
											//	console.log("circle_id"
												//		+ circle_id);
												
												
											//	console.log("logout" + log);
												console.log(roleChecked);
												$
														.ajax({
															type : "GET",
															url : "${pageContext.request.contextPath}/systemSettingsFormSave",
															dataType : "json",
															data : {
																"roleChecked" : JSON.stringify(roleChecked),
															
																"Country" : $(
																		"#Country")
																		.val(),
																"Language" : $(
																		"#Language")
																		.val(),
																"createdDate" : $(
																		"#createdate")
																		.val(),
															
																"lastModifiedDate" : $(
																		"#lstmodifdate")
																		.val(),
																"Currency" : $(
																		"#Currency")
																		.val(),
																					
																
																

															},
															success : function(data) {
																//reload 
																if(data.Login == 'Login'){
																	location.replace("${pageContext.request.contextPath}/");
																	}
																roleChecked=[];
																var param = "${pageContext.request.contextPath}/systemSettingsFormView?Country="
																		+ $("#Country").val();
																location.replace(param);
															},
															error : function(
																	error) {
																console.log("The error is "+ error);
															}
														});
											});

						});
		$("#deleteButton")
				.click(
						function() {
							console.log('delete now');
							var username = document.getElementById("Country").value;
							$
									.ajax({
										type : "GET",
										url : "${pageContext.request.contextPath}/systemSettingsFormDelete",
										dataType : "json",
										data : {
											"Country" : $("#Country").val(),
										},
										success : function(data) {

											location
													.replace("${pageContext.request.contextPath}/systemSettingsView");
										},
										error : function(error) {
											console
													.log("The error is "
															+ error);
										}
									});
						});
	</script>
</body>
</html>