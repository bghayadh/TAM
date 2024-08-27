<!DOCTYPE html>
<%@page import="com.aliat.alm.models.*"%>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
   <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/Inventory.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
    
  
    
   <style>

.center {
  text-align:center;
  margin-top: 10%;
}
.order-card {
	color:#fff;
	
}

.card {
	margin-top: 10px;
	margin-bottom: 50px;
	padding: 10px 5px 10px 10px;
	border-radius: 1000px;
	-webkit-box-shadow: 0 1px 2.94px 0.06px rgba(4, 26, 55, 0.16);
	box-shadow: 0 1px 2.94px 10px rgba(4, 26, 55, 0.16);
	border-radius: 20px 40px 20px 40px;
	border-top:5px solid gold;
	border-right:5px solid gold;
	border-bottom:5px solid gold;
	border-left:5px solid gold;
	-webkit-transition: all 0.3s ease-in-out;
	transition: all 0.3s ease-in-out;
	width: 15rem;
/*	background-color: #dc143c; */
    background-color: #006868;
   
}

.card a:hover {
    text-decoration:none;
    color:#6FA8DC!important;
}


m-b-20{
--font-family-sans-serif: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
--font-family-monospace: SFMono-Regular,Menlo,Monaco,Consolas,"Liberation Mono","Courier New",monospace;		
color:#DCF8C6;
font-size: 14px;
   
}
.card .card-block {
 justify-content: center;
	padding: 20px;
	 margin: auto;
	text-align: center;
	
}


a{
	color: white;
}



</style>
<title>Setup</title>
</head>
<body>
 
<%--  	<c:set var = "page" value = "setup"/> --%>

  <c:set var="pg" value="setup" scope="session"  />
<%-- 	<%@ include file="header.html" %> --%>
<jsp:include page="header.jsp"></jsp:include>
		 
<div style="margin-top:40px;margin-right:150px;margin-left:150px;" >
		<div class="container" style="margin-right:20px;margin-left:20px;">
		
		<div class="row" >
				<div class="col-md-3  showUser"  >
				
					<div class="card bg-c-blue order-card"   >
					<a href="${pageContext.request.contextPath}/UserListView" style="text-decoration: none">
						<div class="card-block">
							<i class="fa fa-user glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
							<h5 class="m-b-20" id="first" style="justify-content: center;" >Users</h5>
						</div>
						</a>
					</div>
					
				</div>
			<div style="margin-right:20px;"> </div>
					<div class="col-md-3 showRole">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/RoleListView" style="text-decoration: none">
						<div class="card-block" >
							<i class="fas fa-tag" style=" color:gold; width:40px; height:40px"></i>
							<h5 class="m-b-20" id="first">Role</h5>
						</div>
						</a>
					</div>
					
				</div>
				<div style="margin-right:20px;"> </div>
				<div class="col-md-3 ">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/importSettings" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-file-download glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
						    	<h5 class="m-b-20" id="first" > Import </h5>
						</div>
						</a>
					</div>
					
				</div>
				<div style="margin-right:20px;"> </div>
					<div class="col-md-2 showRolePrem">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/rolePermission" style="text-decoration: none">
						<div class="card-block">
							<i class="fa fa-key glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first">Role Permission</h5>
						</div>
						</a>
					</div>
					
				</div>
				
					</div> 
			<div class="row">
			
					<div class="col-md-3 showRolePrem">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/rolePermissionExcep" style="text-decoration: none">
						<div class="card-block">
							<i class="fa fa-key glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first">Role Permission Exception</h5>
						</div>
						</a>
					</div>
					
				</div>
				<div style="margin-right:20px;"> </div>
				<div class="col-md-3  showSetting">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/systemSettings" style="text-decoration: none">
				<div class="card-block">
							<i class="fa fa-wrench glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first" >System Settings</h5>
						</div>
						</a>
					</div>
					
				</div>
				<div style="margin-right:20px;"> </div>
				<div class="col-md-3 showMethod">
				
					<div class="card bg-c-blue  order-card">
					<a href="${pageContext.request.contextPath}/ManualMethod" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-hand-paper" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first">Manual Methods</h5>
						</div>
						</a>
					</div>
					
				</div>
				<div style="margin-right:20px;"> </div>
						<div class="col-md-2 ScheduleReport">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/SchedulerRulesListView" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-clock glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first" style="font-size:1.3em;">Scheduled  Reports</h5>
						</div>
						</a>
					</div>
					</div>
					
				</div>
				
				<div class="row">
		
					<div class="col-md-3 Notification">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/NotificationListView" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-bell glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first">Notification</h5>
						</div>
						</a>
					</div>
					
				</div>
					<div style="margin-right:20px;"> </div>
				<div class="col-md-3 ReportsList">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/ReportListView" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-clipboard-list glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first">Reports</h5>
						</div>
						</a>
					</div>
					
				</div>
					<div style="margin-right:20px;"> </div>
					<div class="col-md-3 Notification">
				
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/EmailAccountsListView" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-users glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first">Email Accounts</h5>
						</div>
						</a>
					</div>
					
				</div>
				
				<div style="margin-right:20px;"> </div>
					<div class="col-md-2">
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/Loaders" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-life-ring fa-spin glyphicon-align-center" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first" style="font-size:1.3em;">Loaders</h5>
						</div>
						</a>
					</div>
					</div>
					
					
					
				</div>
				<div class="row">
				
					<div class="col-md-3">
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/ModuleScreenListView" style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-desktop" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first" style="font-size:1.3em;">Module Screen</h5>
						</div>
						</a>
					</div>
					</div>
					<div style="margin-right:20px;"> </div>
					<div class="col-md-3">
					<div class="card bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/ModuleFieldListView" style="text-decoration: none">
						<div class="card-block">
							<i class="fa fa-map" style="color:gold; width:40px; height:40px"></i>
								<h5 class="m-b-20" id="first" style="font-size:1.3em;">Module Fields</h5>
						</div>
						</a>
					</div>
					</div></div>
				</div>
				</div>
				
	

		



<script>

if('${showDash}' == 'false'){
	  $(".showDash").hide();
	 }
	if('${showTask}' == 'false'){
		$(".showTask").hide();
	 }
	if('${showReport}' == 'false'){
	   $(".showReport").hide();
	}
	if('${showUser}' == 'false'){
	$(".showUser").hide();
	}
	if('${showRole}' == 'false'){
	$(".showRole").hide();
	}
	if('${showSetting}' == 'false'){
		$(".showSetting").hide();
	}
	if('${showMethod}' == 'false'){
		$(".showMethod").hide();
	}
	if('${showRolePerm}' == 'false'){
		$(".showRolePrem").hide();
		}
	if("${ScheduleReport}" == '0'){
		$(".ScheduleReport").hide();
    }

	if("${circlePerm}" == '0'){
		$("#circleIdHead").hide();
		
		}
		
	if("${taskNotificationPerm}" == '0'){
		$("#notifactionDropdown").hide();
		
    }
	
    
    
	$(this)
			.ready(
					function() {
						$.get("${pageContext.request.contextPath}/getNotificationData",
								function(data) {

									console.log(data["totalNode"]);
									$("#bellCounter").attr("data-count",
											data["totalCounter"]);
									$("#siteTask").html(
											data["siteMissPassive"]);
									$("#nodeTask").html(
											data["nodeMissPassive"]);
									$("#cellTask").html(
											data["cellMissPassive"]);
									$("#taskCount").html(
											data["pendingTasks"]);

								});

						$("#notifactionDropdown")
								.click(
										function() {
											$
													.get(
															"${pageContext.request.contextPath}/getNotificationData",
															function(data) {

																console
																		.log(data["pendingTasks"]);
																$(
																		"#bellCounter")
																		.attr(
																				"data-count",
																				data["totalCounter"]);
																$(
																		"#siteTask")
																		.html(
																				data["siteMissPassive"]);
																$(
																		"#nodeTask")
																		.html(
																				data["nodeMissPassive"]);
																$(
																		"#cellTask")
																		.html(
																				data["cellMissPassive"]);
																$(
																		"#taskCount")
																		.html(
																				data["pendingTasks"]);

															});
										});

					});

	<!-----------circle change ajax------------->

	$("#circleIdHead").on("change",function(){
		var circle = $("#circleIdHead option:selected").text();
		console.log("the value is "+circle);
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/changeUserCircle",
			dataType : "json",
			data : {
				"circleId" : circle,
//					"circleId" : $("#circleId").val()
			},
			success : function(data) {
	        
 console.log("circle has benn changs"+data) ;
 location.reload();
	        
			},
			error : function(error) {
	        	 console.log("The error is " +error[0]);
	        }	
	});	
		});
				
</script>

</body>
</html>