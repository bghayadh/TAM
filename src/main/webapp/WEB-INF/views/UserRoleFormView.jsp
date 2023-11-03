<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="ISO-8859-1">
<title>Role List View</title>
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
.textarea {
  width: 400px;
  height: 100px;
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
</style>
</head>
<!-- top nav bar -->
<%-- 	<c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
<!-- end nav bar -->
<p></p>
<div class="container-fluid">
	<div class="row">

		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">

					<span class="input-group-text" style="color: green">Role
						Users</span> <i>&nbsp</i><span class="dot"></span> <i>&nbsp</i> <label
						for="formStatus" id="formStatus">Save</label>
				</div>

			</div>
		</div>
		<div class="col-md-7"></div>

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
						onclick='window.location.href = "${pageContext.request.contextPath}/UserRoleListView"'
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
					style="color: gold;">ROLE INFORMATION</a></li>





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
					<div class="form-group" style="margin-top: 30px;">
						<div class="input-group-prepend">
							<span class="input-group-text">Role Name</span> <input
								type="text" id="rolename" value="${roleName}"
								class="form-control text-input" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="input-group-prepend">
						<span class="input-group-text" style="margin-top: 30px;">Description</span> 
						<textarea name="paragraph_text" cols="90" rows="5" class="textarea" id="description">${description}</textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<body>

</body>

<script type='text/javascript'>

		if ('${listRoleUser}' === "addNew") {
			$("#formStatus").text("New");
			$('.dot').css({
				"background-color" : "orange"
			});
		}

		$("input").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({
				"background-color" : "orange"
			});

			$("#rolename").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({
					"background-color" : "orange"
				});
			});

			$("#description").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({
					"background-color" : "orange"
				});
			});
			
		});

		 
		$(document).ready(function() {

			var oldrolename = $("#rolename").val();			
			
			$("#saveButton").click(function() {

				if ($("#rolename").val() == '') {
					alert('role name cannot be Null');
					return false;	}
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/UserRoleFormSave",
					dataType : "json",
					data : {
						"oldrolename" : oldrolename,
						"rolename" : $("#rolename").val(),
						"description" : $("#description").val(),
					},
					success : function(data) {
						//console.log(data.saveStatus);
						location.replace("${pageContext.request.contextPath}/RoleListView");
					},
					error : function(error) {
						console.log("The error is "+ error);
					}
				});
				
			});

			
		$("#deleteButton").click(function() {
					console.log('delete now');
					
					
							$.ajax({
								type : "GET",
								url : "${pageContext.request.contextPath}/UserRoleFormDelete",
								dataType : "json",
								data : {
									"rolename" : $("#rolename").val(),
								},
								success : function(data) {
									//console.log(data.saveStatus);
									location.replace("${pageContext.request.contextPath}/RoleListView");
								},
								error : function(error) {
									console.log("The error is "+ error);
								}
							});
				});
		
		});
</script>	
</html>