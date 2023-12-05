<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="ISO-8859-1">
<title>User Form View</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
   <meta charset="utf-8">
        <title></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <link rel="stylesheet"
            href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
        <script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        <link href='${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.min.css' rel='stylesheet'
            type='text/css'>
        <script src='${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.min.js'
            type='text/javascript'></script>
       
<style>
.p {
	font-size: large;
	font-weight: bold;
}

.box {
	font-size: large;
}

  .ui-autocomplete {
                max-height: 250px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
                overflow-x: both;
                /* add padding to account for vertical scrollbar */
                padding-right: 10px;
                z-index: 9999;
                width: 350px;
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
             .hide-row { display:none; }
    
</style>

</head>


<body>
<%-- 	<c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
	<p></p>
	<div class="container-fluid">
		<div class="row">

		
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">User Name</span> <input type="text"
							id="username" value="${username}" class="form-control text-input" />
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="input-group-prepend">
					<span class="input-group-text">Status</span> <input type="text"
						id="userstat" value="${userStatus}"
						class="form-control text-input" />
				</div>
			</div>
			<div class="col-md-3">
			 <div class="nextprvItems">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Other User</span>
                                <input type="text" id="selectnav" value="${selectnav}" 
                                    class="form-control text-input" />
                            </div>
                        </div>
                    </div></div>
			<div class="col-md-3 text-right">
                        <i>&nbsp</i><span class="dot"></span> <i>&nbsp</i> <label for="formStatus" id="formStatus"
                            style="float: right;">Saved</label>
                    </div>
                    
                    </div>
                      <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
                                <span class="input-group-text" style="width: 170px;"">Created Date</span> <input type=" text" id="createdate" readonly value="${createdDate}"
                                    class="form-control datetimepicker-input" data-toggle="datetimepicker"
                                    data-target="#datetimepicker1" />
                                <div class="input-group-append" data-target="#datetimepicker1"
                                    data-toggle="datetimepicker"></div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
                                <span class="input-group-text" style="width: 210px;">Last
                                    Modify Date</span> <input type="text" id="lstmodifdate" readonly
                                    value="${lastModifiedDate}" class="form-control datetimepicker-input"
                                    data-toggle="datetimepicker" data-target="#datetimepicker2" />
                                <div class="input-group-append" data-target="#datetimepicker2"
                                    data-toggle="datetimepicker"></div>
                            </div>
                        </div>
                    </div>

                    <div class=" col-md-4 ">
               <div class="nextprvItems">
			<label id="label-1" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
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
							class="btn btn-success next">&raquo;</a></li>			  		</ul>
				</nav>
		</div></div>
			<div class="col-md-2" style="text-align: right;">
				<div class="btn-group pull-right">

					<div class="glyph">

						<button type="button" id="Fview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="Form View"
							style="background: #da6815;">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-light"
							data-toggle="tooltip"
							onclick='window.location.href = "${pageContext.request.contextPath}/UserListView"'
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

	<div class="container-fluid">

		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: -20px;">
					<li class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">USER INFORMATION</a></li>

 					<li class="nav-item"><a class="nav-link"
			            id="custom-tabs-one-roles-tab" data-toggle="tab"
			            href="#custom-tabs-one-roles" role="tab"
			            aria-controls="custom-tabs-one-roles" aria-selected="false" style="color: gold;">USER ROLES</a></li>

					<!-- Here maher should place the warehouse tab header -->


					<li class="nav-item ml-auto">


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


	<p></p>


	<div class="container-fluid">
		<div class="tab-content" id="custom-tabs-one-tabContent">
			<div class="tab-pane fade show active" id="custom-tabs-one-home"
				role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">



				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">First Name</span> <input
									type="text" id="fname" value="${Fname}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Middle Name</span> <input
								type="text" id="mname" value="${Mname}"
								class="form-control text-input" />
						</div>
					</div>

					
				</div>


				<div class="row">
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Last Name</span> <input
								type="text" id="lname" value="${Lname}"
								class="form-control text-input" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Mobile Number</span> <input
									type="text" id="mobnum" value="${mobNum}"
									class="form-control text-input" />

							</div>
						</div>
					</div>

					

				</div>


				<div class="row">
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Email Address</span> <input
								type="text" id="eadd" value="${Eadd}"
								class="form-control text-input" />
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Address</span> <input type="text"
									id="address" value="${Address}" class="form-control text-input" />
							</div>
						</div>
					</div>


				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Circle City</span> <input
									type="text" id="circlecity" value="${CircleCity}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Circle Country</span> <input
									type="text" id="circlecountry" value="${circleCountry}"
									class="form-control text-input" />
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Circle State</span> <input
									type="text" id="circlestate" value="${c_state}"
									class="form-control text-input" />
							</div>
						</div>


					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Circle Id</span>
							<!-- <input type="text" id="circle_id" value="${circleId}" class="form-control text-input" /> -->
							<select name="Ids" id="circle_id">
								<option value="one"
									<c:if test = "${circleId =='one'}"> selected </c:if>>Circle
									One</option>
								<option value="two"
									<c:if test = "${circleId =='two'}"> selected </c:if>>Circle
									Two</option>
								<option value="three"
									<c:if test = "${circleId =='three'}"> selected </c:if>>Circle
									Three</option>
							</select>
						</div>
					</div>
				<div class="col-md-4">
						<div class="input-group-prepend">
							<span class="input-group-text">Job Title</span>
							<!-- <input type="text" id="job_title" value="${jobTitle}" class="form-control text-input" /> -->
							<select name="jTitle" id="job_title">
								<option value="Sales Manager"
									<c:if test = "${jobTitle =='Sales Manager'}"> selected </c:if>>Sales Manager</option>
								<option value="Region Manager"
									<c:if test = "${jobTitle =='Region Manager'}"> selected </c:if>>Region Manager</option>
								<option value="Director"
									<c:if test = "${jobTitle =='Director'}"> selected </c:if>>Director</option>
							</select>
						</div>
					</div>
				</div>
			<p></p>

				<!-- ********************************** list role ********************************** -->

				


				<p></p>


				<div class="row">
					<div class="col-md-7">
						<div class="input-group-prepend">
							<span class="input-group-text">Set New Password</span> <input
								type="password" id="pass" value="${pass}"
								class="form-control text-input" />
						</div>
					</div>
				</div>
				<p></p>
				<div class="row">
					<div class="container-fluid">

						<div class="checkboxes"
							style="margin-top: 10px; margin-left: 10px;">
							<span> <input type="checkbox" id="updatepass"
								${UpdatePass} /> Send Password Update Notification

								<p></p>
								<p></p> <input type="checkbox" id="logout" ${Logout} /> Logout
								from all devices


							</span>
						</div>
					</div>
				</div>				
			</div>

			<!--  Here Maher should place his warehouse tab area -->

			<div class="tab-pane fade" id="custom-tabs-one-roles"
				role="tabpanel" aria-labelledby="custom-tabs-one-roles-tab">
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">User roles:</span>
							</div>
						</div>
					</div>
				</div>
				
							<table id="roleTable" class="display table table-bordered nowrap"
								style="width: 100%; border: none;">
								<tbody>
								</tbody>
							</table>
						</div>
					</div>

				</div>
	
	
	
			


	<script type='text/javascript'>
		$("input").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({
				"background-color" : "orange"
			});

			$("#username").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({
					"background-color" : "orange"
				});
			});

			$("#pass").change(function() {
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
			 $(".nextprvItems").addClass("hide-row ");
				
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
							
							var roleChecked = [];
							var un = "${username}";						

							
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
														var ch = $(this).parent().parent().children('div[name="roleName'+j+'"]').children('span').text();
													//	$(this).parent().parent().children('td[name="itemCode"]').children('input').val()
														console.log(ch);
														roleChecked.push(ch);
														}else{
															console.log("no role checked");
															}
													j++;
													});

												if ($("#username").val() == '') {
													alert('user name cannot be Null');
													return false;
												}

												if ($("#pass").val() == '') {
													alert('password cannot be Null');
													return false;
												}

												if ($("#emailaddress").val() == '') {
													alert('email address cannot be Null');
													return false;
												}

												// validate creatd date cannot be null
												val = $("#createdate").val();
												val1 = Date.parse(val);
												//console.log('date is  : ' +val1);
												if (isNaN(val1) == true) {
													alert(' Create Date is not valid');
													return false;
													//$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
												}

												// validate modified date cannot be null
												val = $("#lstmodifdate").val();
												val1 = Date.parse(val);
												//console.log('date is  : ' +val1);
												if (isNaN(val1) == true) {
													alert(' Modified Date is not valid');
													return false;
													//$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
												}

												var username = document
														.getElementById("username").value;
												var fname = document
														.getElementById("fname").value;
												var mname = document
														.getElementById("mname").value;
												var createdDate = document
														.getElementById("createdate").value;
												var lastModifiedDate = document
														.getElementById("lstmodifdate").value;
												var lname = document
														.getElementById("lname").value;
												var emailAddress = document
														.getElementById("eadd").value;
												var mobileNumber = document
														.getElementById("mobnum").value;
												var address = document
														.getElementById("address").value;
												var cirlce_city = document
														.getElementById("circlecity").value;
												var cirlce_country = document
														.getElementById("circlecountry").value;
												var circle_state = document
														.getElementById("circlestate").value;
												var circleID = document
														.getElementById("circle_id").value;   
												var pass = document
														.getElementById("pass").value;              
												var circleID = document
												.getElementById("job_title").value;           //job_title
												
												var check4 = document
														.getElementById("updatepass").value;
												var update;
												if (updatepass.checked == true) {
													update = '1';
												} else {
													update = '0';
												}

												var check5 = document
														.getElementById("logout").value;
												var log;
												if (logout.checked == true) {
													log = '1';
												} else {
													log = '0';
												}

												console.log("username"
														+ username);
												console.log("pass" + pass);
												console.log("fname" + fname);
												console.log("mname" + mname);
												console.log("createdDate"
														+ createdDate);
												console.log("lastModifiedDate"
														+ lastModifiedDate);
												console.log("lname" + lname);
												console.log("emailaddress"
														+ emailAddress);
												console.log("mobnum"
														+ mobileNumber);
												console
														.log("address"
																+ address);
												console.log("circlecity"
														+ cirlce_city);
												console.log("cirlcecountry"
														+ cirlce_country);
												console.log("circlestate"
														+ circle_state);
												console.log("circle_id"
														+ circle_id);
												console.log("job_title"
														+ job_title);               //job_title
												
												console.log("update" + update);
												console.log("logout" + log);
												console.log(roleChecked);
												$
														.ajax({
															type : "GET",
															url : "${pageContext.request.contextPath}/UserFormSave",
															dataType : "json",
															data : {
																"roleChecked" : JSON.stringify(roleChecked),
																"username" : $(
																		"#username")
																		.val(),
																"fname" : $(
																		"#fname")
																		.val(),
																"mname" : $(
																		"#mname")
																		.val(),
																"createdDate" : $(
																		"#createdate")
																		.val(),
																"lastModifiedDate" : $(
																		"#lstmodifdate")
																		.val(),
																"lname" : $(
																		"#lname")
																		.val(),
																"address" : $(
																		'#address')
																		.val(),
																"emailAddress" : $(
																		'#eadd')
																		.val(),
																"mobileNumber" : $(
																		'#mobnum')
																		.val(),
																"circle_city" : $(
																		'#circlecity')
																		.val(),
																"circle_country" : $(
																		'#circlecountry')
																		.val(),
																"circle_state" : $(
																		'#circlestate')
																		.val(),
																"circleId" : $(
																		'#circle_id')
																		.val(),
																"jobTitle" : $(
																		'#job_title')
																		.val(),                    //jobTitle
																		
																"pass" : $(
																		'#pass')
																		.val(),									
																"update" : update,
																"log" : log,

															},
															success : function(data) {
																//reload 
																if(data.Login == 'Login'){
																	location.replace("${pageContext.request.contextPath}/");
																	}
																roleChecked=[];
																var param = "${pageContext.request.contextPath}/UserFormView?username="
																		+ $("#username").val()+"&NavAction=2";
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
							var username = document.getElementById("username").value;
							$
									.ajax({
										type : "GET",
										url : "${pageContext.request.contextPath}/UserFormDelete",
										dataType : "json",
										data : {
											"username" : $("#username").val(),
										},
										success : function(data) {

											location
													.replace("${pageContext.request.contextPath}/UserListView");
										},
										error : function(error) {
											console
													.log("The error is "
															+ error);
										}
									});
						});

		 $("#selectnav").autocomplete({
       	  source: debounce(function(request, response, event, ui) {
       	    $.ajax({
       	      type: "GET",
       	      contentType: "application/json; charset=utf-8",
       	      url: '${pageContext.request.contextPath}/GetAllUser',
       	      data: {
       	        "User": $("#selectnav").val(),
       	      },
       	      dataType: "json",
       	      success: function(data) {
       	        if (data != null) {
       	          response(data.ListUser);
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
       	    this.value = ui.item ? ui.item[0] + ":" + ui.item[2] : '';

       	    var param = '${pageContext.request.contextPath}/UserFormView?username=' + ui.item[1]+"&NavAction=2";
       	    window.location.href = param;
       	    return false;
       	  }
       	}).autocomplete("instance")._renderItem = function(ul, item) {
       	  return $("<li class='each'>")
       	    .append("<div class='acItem'><span class='desc'>" +
       	      item[1] + ' ' + item[2]+ ' ' +item[3] + "</span><br><span class='name' style='font-weight:bold'>" +
       	      item[0] + ', ' + item[4] + "</span>")
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



     	      if('${SelectedIndex}' != "addNew"){
  				var SelectedIndex = ${SelectedIndex};
  				if('${userCount}' != "addNew"){

  					
  			var userCount = ${userCount};
  			
  			if(($("#username").val()) != "" && ($("#username").val()) != null){

  			if(SelectedIndex === userCount){
  				
          		document.getElementById("btnLast").style.opacity = 0.5;
          		$("#btnLast").hasClass("disabled");
          		document.getElementById("btnLast").style.pointerEvents = "none";
          		
          		document.getElementById("btnNexta").style.opacity = 0.5;
          		document.getElementById("btnNexta").style.pointerEvents = "none";

  				
  				$("#btnNexta").hasClass("disabled");
  				
  				}else{
  					
  					if(!$("#btnNexta").hasClass("disabled")){
  						
  						$("#btnNext").click(function(){
  							
  							var param ="${pageContext.request.contextPath}/UserFormView?username="+$("#username").val()+"&NavAction=1";

  							window.location.href =param;
  				
  						});
  			
  					}
  					if(!$("#btnLst").hasClass("disabled")){
          				
          				$("#btnLst").click(function(){
          					
  							var param ="${pageContext.request.contextPath}/UserFormView?username="+$("#username").val()+"&NavAction=4";
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
  						
  						var param ="${pageContext.request.contextPath}/UserFormView?username="+$("#username").val()+"&NavAction=0";
  						window.location.href =param;
  						
  					 });
  				}
  				$("#btnFrst").click(function(){

          			if(!$("#btnFrst").hasClass("disabled")){
          					
  						var param ="${pageContext.request.contextPath}/UserFormView?username="+$("#username").val()+"&NavAction=3";
          				window.location.href =param;
          						
          				}
          				 });

  			}
  			
  			}}
  		}
  			$("#label-1").text((SelectedIndex)+"/"+userCount);
		
	</script>
</body>
</html>