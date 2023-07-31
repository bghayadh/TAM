<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script> -->
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <!-- 		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	 -->
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">

</head>
<body>
 <nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
        <a href="#" class="navbar-brand">ALM</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarmenu">
            <span class="navbar-toggler-icon"></span>
        </button>



        <div class="collapse navbar-collapse" id="navbarmenu">
            <ul class="navbar-nav">
           		<li class="nav-item"><a href="/home" class="nav-link  " style="color: #fff"><i class="fas fa-home" style="color: gold"></i> Home</a></li>
                <li class="nav-item"><a href="/Network" class="nav-link  " style="color: #fff"><span class="border-bottom active"><i class="fas fa-wifi" style="color: #20B2AA"></i> Network</span></a></li>
                <li class="nav-item"><a href="/Purchase" class="nav-link "  style="color: #fff"><i class="fas fa-money-check" style="color: gold"></i> Purchasing </a></li>
                <li class="nav-item"><a href="/Inventory" class="nav-link " style="color: #fff"><i class="fas fa-warehouse" style="color: gold"></i> Inventory </a></li>
                <li class="nav-item"><a href="/Contract" class="nav-link " style="color: #fff"><i class="fas fa-book" style="color: gold"></i> Contracts</a></li>
                <li class="nav-item"><a href="/Report" class="nav-link  " style="color: #fff"><i class="fas fa-chart-line" style="color: gold"></i> Report </a></li>
                <li class="nav-item"><a href="/Dashboard" class="nav-link " style="color: #fff"><i class="fas fa-tv" style="color: gold"></i> Dashboard </a></li>
                <li class="nav-item"><a href="/Setup" class="nav-link " style="color: #fff"><i class="fas fa-cog" style="color: gold"></i> Setup </a></li>



            </ul>
            <ul class="navbar-nav ml-auto ">
                        
                <!--  li class="nav-item"><a href="#" class="nav-link a1" style="margin-right: 30px;"><i class="fas fa-bell" style="color: gold"></i></a></li-->
                <li class="dropdown" id="notifactionDropdown"
				style="margin-right: 60px;"><a href="#" class="nav-link a1" data-toggle="dropdown">
					<span class="p1" id="bellCounter" data-count=""> 
					<i class="fa fa-bell" data-count="4b" style="font-size: 20px; color: gold;"></i>
					</span></a>
 	
						<ul class="dropdown-menu dropdown-menu-right customListPadding">
							<li><p>
								<a href="#">Site <span class="badge badge-danger float-right"id="siteTask"></span></a></p></li>
							<li><p>
								<a href="#">Node <span class="badge badge-danger float-right"id="nodeTask"></span></a></p></li>
							<li><p>
								<a href="#">Cell <span class="badge badge-danger float-right"id="cellTask"></span></a></p></li>
							<li><p>
								<a href="#">Task<span class="badge badge-danger float-right"id="taskCount"></span></a></p></li>
						</ul>
				</li>
               
               
               <!--  li class="nav-item"><a href="#" class="nav-link a1" style="color: white;"><i class="fas fa-user" style="color: gold   "></i> aya shi </a></li -->
 
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #ffbb33 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>
</body>
</html>