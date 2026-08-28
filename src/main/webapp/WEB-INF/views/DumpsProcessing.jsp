<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/all.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/ListView.css">


<title>Dumps Processing</title>
<style>
.cadr {
	border: 0.1em solid #08526d;
	border-radius: .25rem;
}

.card-body {
	padding: 0px !important;
}

.cardsClass {
	min-width: 310px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px 2px;
}

.Cardheader {
	text-align: center;
	font-family: cursive;
	color: #DCF8C6;
	border-radius: 25px 25px 0px 0px !important;
	border-style: groove;
	border-width: 2px;
	border-color: #4B8C8C;
	box-shadow: 5px 5px 5px rgb(75, 140, 140);
	font-size: 14px;
}

.CardBody {
	justify-content: center;
	background-color: lightgray;
	border-radius: 0px 0px 25px 25px !important;
	font-size: 16px;
	font-family: cursive;
	border-style: groove;
	border-width: 2px;
	border-color: #4B8C8C;
	box-shadow: 5px 5px 5px rgb(75, 140, 140);
	min-height: 180px;
	border-top: none;
}

.title_card {
	text-anchor: middle;
	font-family: Verdana, sans;
	font-weight: bold;
	margin-bottom: 15px;
	font-size: 17px;
}

.card {
	margin-bottom: 20px !important;
}

.block {
	border: 1px solid #4B8C8C !important;
	padding: 10px;
}

.loadingmsg {
	margin-top: 40px;
	text-align: center;
	display: none;
}

.loadingRes {
	text-align: center;
	margin-top: 60px;
}

.center {
	text-align: center;
	margin-top: 10%;
}

.order-card {
	color: #fff;
}

.circle1 {
	height: 25px;
	width: 25px;
	background-color: yellow;
	border-radius: 100%;
	padding: 1px 7px;
	color: blue;
	text-align: center;
	font-size: 14px;
}

.card1 {
	margin-top: 10px;
	margin-bottom: 50px;
	padding: 10px 5px 10px 10px;
	border-radius: 1000px;
	-webkit-box-shadow: 0 1px 2.94px 0.06px rgba(4, 26, 55, 0.16);
	box-shadow: 0 1px 2.94px 10px rgba(4, 26, 55, 0.16);
	border-radius: 20px 40px 20px 40px;
	border-top: 5px solid gold;
	border-right: 5px solid gold;
	border-bottom: 5px solid gold;
	border-left: 5px solid gold;
	-webkit-transition: all 0.3s ease-in-out;
	transition: all 0.3s ease-in-out;
	width: 18rem;
	background-color: #dc143c;
}

m-b-20 {
	--font-family-sans-serif: -apple-system, BlinkMacSystemFont, "Segoe UI",
		Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif,
		"Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
		"Noto Color Emoji";
	--font-family-monospace: SFMono-Regular, Menlo, Monaco, Consolas,
		"Liberation Mono", "Courier New", monospace;
	color: #DCF8C6;
	font-size: 14px;
}

.card1 {
	margin-left: 70px;
}

.card1 .card-block {
	padding: 25px;
	text-align: center;
}

.card1 a h5 {
	color: white;
}

.card1 a:hover h5 {
	color: #2206A2;
}

.button3 {
	background-color: #858d8d;
	border: 1px solid gold;
	color: white;
	padding: 8px 15px;
	text-align: center;
	text-decoration: red;
	display: inline-block;
	margin: 0px 0px;
	cursor: pointer;
	border-radius: 5px;
}

.button3:hover {
	background-color: #9EA3A3;
	color: gold;
	border: 1px solid #D3D3D3;
}

.card-header {
	padding: 6px 20px !important;
}

.card-header {
	height: 54px !important;
}
</style>
</head>
<body>

	<%--  <c:set var = "page" value = "setup"/> --%>

	<%-- 	<%@ include file="header.html" %> --%>
	<c:set var="pg" value="setup" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>


	<hr style="margin-top: 0rem;">
	<div class="container-fluid assetfinance">
		<div class="row">
			<div
				class="col-md-6 showMethod d-flex justify-content-center align-items-center">
				<div class="card1 bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/SampleDataMigration"
						style="text-decoration: none">
						<div class="card-block">
							<i class="fas fa-life-ring fa-spin"
								style="color: gold; width: 40px; height: 40px"></i>
							<h5 class="m-b-20">Sample Data Migration</h5>
						</div>
					</a>
				</div>
			</div>
			<div
				class="col-md-6 showMethod d-flex justify-content-center align-items-center">
				<div class="card1 bg-c-blue order-card">
					<a href="${pageContext.request.contextPath}/AutoParser"
						style="text-decoration: none">
						<div class="card-block">
							<i class="fa fa-cogs fa-spin fa-3x fa-fw"
								style="color: gold; width: 40px; height: 40px"></i>
							<h5 class="m-b-20">Auto Parser</h5>
						</div>
					</a>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xl-12">
				<div class="card card-primary card-tabs cards-margin block">
					<div class="title_card">From Network Transaction To Discovery
						New</div>
					<div class="card-body cadr">
						<div class="card-group ">
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
										<div class="row">

											<div class="col-sm-10">
												<button class="button3" id="runDNScript"
													style="align: center; margin-left: 20px;">Auto
													Discovery</button>
											</div>
										</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage00" class="loadingmsg">
											<img
												src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult00" class="loadingRes"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xl-12">
				<div class="card card-primary card-tabs cards-margin block">
					<div class="title_card">Discovery New</div>
					<div class="card-body cadr">
						<div class="card-group ">
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
										<div class="row">

											<div class="col-sm-10">
												<button class="button3" id="runDiscoveryNewScript"
													style="align: center; margin-left: 20px;">New Node</button>
											</div>
										</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage3" class="loadingmsg">
											<img
												src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult3" class="loadingRes"></div>
									</div>
								</div>
							</div>
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
										<div class="row">

											<div class="col-sm-10">
												<button class="button3" id="transfer"
													style="align: center; margin-left: 20px;">Transfer</button>
											</div>
										</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage4" class="loadingmsg">
											<img
												src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult4" class="loadingRes"></div>
									</div>
								</div>
							</div>
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
										<div class="row">

											<div class="col-sm-10">
												<button class="button3" id="disappear"
													style="align: center; margin-left: 20px;">Disappear</button>
											</div>
										</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage5" class="loadingmsg">
											<img
												src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult5" class="loadingRes"></div>
									</div>
								</div>
							</div>
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
										<div class="row">




											<div class="col-sm-10">
												<button class="button3" id="maintenance"
													style="align: center; margin-left: 20px;">Maintenance</button>
											</div>
										</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage6" class="loadingmsg">
											<img
												src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult6" class="loadingRes"></div>
									</div>
								</div>
							</div>
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
										<div class="row">




											<div class="col-sm-10">
												<button class="button3" id="replacement"
													style="align: center; margin-left: 20px;">Replacement</button>
											</div>
										</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage7" class="loadingmsg">
											<img
												src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult7" class="loadingRes"></div>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	$("#runDiscoveryNewScript").click(function() {
		$('#loadingmessage3').show();
		$.ajax({
			type : "GET",
			url : '${pageContext.request.contextPath}/runDiscoveryNewScript',
			contentType : "application/json",
			dataType : "json",
			data : {
			//"Test" : "1234"            	
			},
			success : function(data) {
				$('#loadingmessage3').hide();
				$("#loadingResult3").html(data.Result);
				console.log("Success Result");
			},
			error : function(error) {
				console.log("Failure in returning data ");
			}
		});

	});

	$("#runDNScript").click(function() {
		$('#loadingResult00').hide();
		$('#loadingmessage00').show();
		$.ajax({
			type : "GET",
			url : '${pageContext.request.contextPath}/runDNScript',
			contentType : "application/json",
			dataType : "json",
			data : {
			//"Test" : "1234"            	
			},
			success : function(data) {
				console.log(data)
				$('#loadingResult00').show();
				$('#loadingmessage00').hide();
				$("#loadingResult00").html(data.Result);
				console.log("Success Result");
			},
			error : function(error) {
				console.log("Failure in returning data ");
			}
		});

	});

	$("#transfer").click(function() {
		$('#loadingmessage4').show();
		$.ajax({
			type : "GET",
			url : '${pageContext.request.contextPath}/transfer',
			contentType : "application/json",
			dataType : "json",
			data : {
			//"Test" : "1234"            	
			},
			success : function(data) {
				$('#loadingmessage4').hide();
				$("#loadingResult4").html(data.Result);
				console.log("Success Result");
			},
			error : function(error) {
				console.log("Failure in returning data ");
			}
		});

	});

	$("#disappear").click(function() {
		$('#loadingmessage5').show();
		$.ajax({
			type : "GET",
			url : '${pageContext.request.contextPath}/disappear',
			contentType : "application/json",
			dataType : "json",
			data : {
			//"Test" : "1234"            	
			},
			success : function(data) {
				$('#loadingmessage5').hide();
				$("#loadingResult5").html(data.Result);
				console.log("Success Result");
			},
			error : function(error) {
				console.log("Failure in returning data ");
			}
		});

	});

	$("#replacement").click(function() {
		$('#loadingmessage7').show();
		$.ajax({
			type : "GET",
			url : '${pageContext.request.contextPath}/replacement',
			contentType : "application/json",
			dataType : "json",
			data : {
			//"Test" : "1234"            	
			},
			success : function(data) {
				$('#loadingmessage7').hide();
				$("#loadingResult7").html(data.Result);
				console.log("Success Result");
			},
			error : function(error) {
				console.log("Failure in returning data ");
			}
		});

	});

	$("#maintenance").click(function() {
		$('#loadingmessage6').show();
		$.ajax({
			type : "GET",
			url : '${pageContext.request.contextPath}/maintenance',
			contentType : "application/json",
			dataType : "json",
			data : {
			//"Test" : "1234"            	
			},
			success : function(data) {
				$('#loadingmessage6').hide();
				$("#loadingResult6").html(data.Result);
				console.log("Success Result");
			},
			error : function(error) {
				console.log("Failure in returning data ");
			}
		});

	});
</script>
</html>