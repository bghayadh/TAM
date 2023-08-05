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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
    
   
<title>Auto Parser</title>
<style>
.cadr {
   border: 0.1em solid #08526d;
   border-radius: .25rem;
}

.card-body
{
	padding:0px !important;
}

.cardsClass {
    min-width: 270px;
    min-height: 260px;
    border-radius: 10px;
    padding: 10px 2px;
}

.Cardheader {
    text-align: center;
    font-family: cursive;
    color: #DCF8C6;
    border-radius: 25px 25px 0px 0px!important;
    border-style: groove;
    border-width: 2px;
    border-color: #4B8C8C;
    box-shadow: 5px 5px 5px rgb(75,140,140);
    font-size: 14px;
}

.CardBody {
    justify-content: center;
    background-color: lightgray;
    border-radius: 0px 0px 25px 25px!important;
    font-size: 16px;
    font-family: cursive;
    border-style: groove;
    border-width: 2px;
    border-color: #4B8C8C;
    box-shadow: 5px 5px 5px rgb(75,140,140);
    min-height: 180px;
    border-top:none;
}

.title_card
{
	text-anchor: middle;
    font-family: Verdana, sans;
    font-weight: bold;
    margin-bottom: 15px;
    font-size: 17px;
}

.card 
{
	margin-bottom: 20px !important;
}

.block
{
	border:1px solid #4B8C8C !important;
	padding: 10px;
}

.loadingmsg
{
	margin-top: 40px;
	text-align: center;
	display: none;
}

.loadingRes
{
	text-align: center;
	margin-top: 60px;
}

.card-header
{
	height: 54px !important;
}

.button
{
	background-color: #08526d;
    border: 1px solid gold;
    color: #DCF8C6;
    padding: 8px 15px;
    text-align: center;
    text-decoration: red;
    display: inline-block;
    margin: 0px 0px;
    cursor: pointer;
    border-radius: 5px;
}

.button1
{
	background-color: #2406ad;
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

.button2
{
	background-color: #DC143C;
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

.button3
{
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

.button3:hover
{
	background-color: #9EA3A3;
	color: gold;
	border: 1px solid #D3D3D3;
}

.card-header
{
	padding:6px 20px !important;
}

.circle1 {
  height: 25px;
  width: 25px;
  background-color: yellow;
  border-radius: 100%;
  padding:1px 7px;
  color: blue;

  text-align:center;
  font-size: 14px;
}

</style>
</head>
<body>
 
<%--  <c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>

<hr style="margin-top: 0rem;">
<div class="container-fluid assetfinance" >
			<div class="card card-primary card-tabs cards-margin block">
				<div class="title_card">Auto Parser</div>
				<div class="row" style="margin-bottom: 10px;">
				<div class="input-group-prepend" style="margin-left: 20px;">
				<span class="input-group-text" >Domain</span>
					<select id="loaderDomain" aria-label="Default select example" class="form-control">
									<option value="Transmission" >Transmission</option>
									<option value="Ran" >RAN</option>
									<option value="Core">Core</option>
									<option value="Enterprise">Enterprise</option>
									
					</select>
				</div>	
				<div class="input-group-prepend" style="margin-left: 20px;">
				<span class="input-group-text">Sub Domain</span>
					<select id="loaderSubDomain" class="form-control">
					                <option value="" > </option>
									<option value="IP" >IP</option>
									<option value="FiberOptic" >Fiber Optic</option>
									<option value="MicrowaveLink">Microwave Link</option>
									
					</select>
				</div>	
				
				<div class="input-group-prepend" style="margin-left: 20px;">
				<span class="input-group-text">TYPE</span>
					<select id="loaderType" class="form-control">
					                <option value="" > </option>
									<option value="DWDM" >DWDM</option>
									<option value="SDH" >SDH</option>
									
					</select>
				</div>
				
				<div class="input-group-prepend" style="margin-left: 20px;">
				<span class="input-group-text">Vendor</span>
					<select id="loaderVendor" class="form-control">
									<option value="Nokia" >NOKIA</option>
									<option value="Huawei" >Huawei</option>
									<option value="zte">ZTE</option>
									<option value="Ericsson">Ericsson</option>
									
					</select>
				</div>	
				</div>					
				
				<div class="card-body cadr">
					<div class="card-group ">
					<div class="col-lg-4">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">            				
                				<div class="card-header Cardheader">
                				<div class="row">
                				<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
                				<span class="circle1">1</span>
                				</div>
                				<div class="col-sm-10">                				
                				<button class="button3" id="loadFiles" style="align:center; margin-left: 14px;">Load Files</button>
                				</div>
                				</div>
                				</div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage1" class="loadingmsg">
                						<img src="../../resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult1" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      				</div>
      				<div class="col-lg-4">
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader">
                				<div class="row">
                				<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
                				<span class="circle1">2</span>
                				</div>
                				<div class="col-sm-10">
                				<button class="button3" id="copyParsingDataToALM" style="align:center; margin-left: 14px;">Copy Parsing Data To ALM</button>
                				</div>
                				</div>
                				</div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage2" class="loadingmsg">
                						<img src="../../resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult2" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      				</div>
      				<div class="col-lg-4">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader">
                				<div class="row">
                				<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
                				<span class="circle1">3</span>
                				</div>
                				<div class="col-sm-10">                				 
                				<button class="button3" id="firstParsing" style="align:center; margin-left: 20px;">First Parsing</button>
                				</div>
                				</div>
                				</div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage3" class="loadingmsg">
                						<img src="../../resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult3" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      				</div>
					</div>
				</div>
				

				<div class="card-body cadr">
					<div class="card-group ">
					<div class="col-lg-4">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">            				
            				
                				<div class="card-header Cardheader" style="vertical-align:middle;">
                				<div class="row">                				
                				<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
                				<span class="circle1">4</span>
                				</div>
                				<div class="col-sm-10">
                				<button class="button3" id="checkNodeMovement" style="align:center; margin-left: 25px;">Check Node Movement</button>
                				</div>                				
                				</div>
                				</div>
                				
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage4" class="loadingmsg">
                						<img src="../../resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult4" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      				</div>
					  <div class="col-lg-4">      					
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader">
                				<div class="row">                				
                				<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
                				<span class="circle1">5</span>
                				</div>
                				<div class="col-sm-10">                				
                				<button class="button3" id="checkCabinetMovement" style="align:center; margin-left: 20px;">Check Cabinet Movement</button>
                				</div>
                				</div>
                				</div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage5" class="loadingmsg">
                						<img src="../../resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult5" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      				</div>
					  <div class="col-lg-4">      					
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader">
                				<div class="row">                				
                				<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
                				<span class="circle1">6</span>
                				</div>
                				<div class="col-sm-10">                				
                				<button class="button3" id="checkBoardMovement" style="align:center; margin-left: 20px;">Check Board Movement</button>
                				</div>
                				</div>
                				</div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage6" class="loadingmsg">
                						<img src="../../resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult6" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      				</div>
				  </div>

      				</div>
					  <div class="card-body cadr">
						<div class="card-group ">
						<div class="col-lg-4">
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">            				
								
									<div class="card-header Cardheader" style="vertical-align:middle;">
									<div class="row">                				
									<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
									<span class="circle1">4</span>
									</div>
									<div class="col-sm-10">
									<button class="button3" id="checkAntennaMovement" style="align:center; margin-left: 25px;">Check Antenna Movement</button>
									</div>                				
									</div>
									</div>
									
									<div class="card-body mycard CardBody">
										<div id="loadingmessage7" class="loadingmsg">
											<img src="../../resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult7" class="loadingRes"></div>
									</div>
									</div>
							  </div>
						  </div>
						  <div class="col-lg-4">      					
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
									<div class="row">                				
									<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
									<span class="circle1">5</span>
									</div>
									<div class="col-sm-10">                				
									<button class="button3" id="checkHostVersionMovement" style="align:center; margin-left: 20px;">Check host Version Movement</button>
									</div>
									</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage8" class="loadingmsg">
											<img src="../../resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult8" class="loadingRes"></div>
									</div>
									</div>
							  </div>
						  </div>
						  <div class="col-lg-4">      					
							<div class="mx-auto cardsClass">
								<div class="card bg-light mb-3 mx-auto ">
									<div class="card-header Cardheader">
									<div class="row">                				
									<div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
									<span class="circle1">6</span>
									</div>
									<div class="col-sm-10">                				
									<button class="button3" id="NewAttribute" style="align:center; margin-left: 20px;">New Attribute</button>
									</div>
									</div>
									</div>
									<div class="card-body mycard CardBody">
										<div id="loadingmessage9" class="loadingmsg">
											<img src="../../resources/images/ajax-loader.gif">
										</div>
										<div id="loadingResult9" class="loadingRes"></div>
									</div>
									</div>
							  </div>
						  </div>
					  </div>
	
						  </div>
      			</div>
			</div>
		</div>
<script>
$("#loadFiles").click(function() {
	//$('#loadingmessage1').show();
	var loader_domain=$('#loaderDomain').val();
	var loader_subdomain=$('#loaderSubDomain').val();
	var loader_vendor=$('#loaderVendor').val();
	var loader_type=$('#loaderType').val();
	
	console.log("loaderDomain "+loader_domain);
	console.log("loader_subdomain "+loader_subdomain);
	console.log("loader_vendor "+loader_vendor);
	console.log("loader_type "+loader_type);
	
	if(loader_vendor=="Huawei"&& loader_domain=="Ran"){
		url='loadFilesAOSS';
	}
	else if(loader_vendor=="Nokia" && loader_domain=="Ran"){
		url='loadFilenokia';
	}else if(loader_vendor=="Huawei" && loader_domain=="Transmission" && loader_subdomain == "FiberOptic" && loader_type == "DWDM"){
		url='loadFileDWDMhuawei';
	
    }else if(loader_vendor=="Ericsson" && loader_domain=="Transmission" && loader_subdomain == "MicrowaveLink"){
	    url='loadFileMWERICSSON';
    }
    else if(loader_vendor=="Huawei" && loader_domain=="Enterprise"){
		url='loadFilesEntHW';
    }
    else if(loader_vendor=="Huawei" && loader_domain=="Transmission" && loader_subdomain == "IP"){
		url='loadFileIPHuawei';
    }
    else if(loader_vendor=="Nokia" && loader_domain=="Transmission" && loader_subdomain == "IP"){
		url='loadFileIPNokia';
    }
	else {
		url='emptyUrl';
	}
	if(url !="emptyUrl") {
		$('#loadingmessage1').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/'+url,
         contentType: "application/json",
         dataType: "json",
         data: {
         	//"Test" : "1234"
          	"vendor":loader_vendor,
          	"domain":loader_domain,
          	"sub_domain":loader_subdomain,
          	"type" : loader_type
         },
      success : function(data) {
   	   $('#loadingmessage1').hide();
            $("#loadingResult1").html(data.Result);
			console.log("Success Result");
         },
        error : function(error) {
        	console.log("Failure in returning data ");
         }
	 });
	}

});

$("#copyParsingDataToALM").click(function() {
	$('#loadingmessage2').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/copyParsingDataToALM',
         contentType: "application/json",
         dataType: "json",
         data: {
         	//"Test" : "1234"            	
         },
      success : function(data) {
   	   $('#loadingmessage2').hide();
            $("#loadingResult2").html(data.Result);
			console.log("Success Result");
         },
        error : function(error) {
        	console.log("Failure in returning data ");
         }
	 });

});

$("#firstParsing").click(function() {
	$('#loadingmessage3').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/firstParsing',
         contentType: "application/json",
         dataType: "json",
         data: {
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

$("#checkNodeMovement").click(function() {
	$('#loadingmessage4').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/checkNodeMovement',
         contentType: "application/json",
         dataType: "json",
         data: {
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


$("#checkCabinetMovement").click(function() {
	$('#loadingmessage5').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/checkCabinetMovement',
         contentType: "application/json",
         dataType: "json",
         data: {
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

$("#checkBoardMovement").click(function() {
	$('#loadingmessage6').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/checkBoardMovement',
         contentType: "application/json",
         dataType: "json",
         data: {
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
$("#checkAntennaMovement").click(function() {
	$('#loadingmessage7').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/checkAntennaMovement',
         contentType: "application/json",
         dataType: "json",
         data: {
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

$("#checkHostVersionMovement").click(function() {
	$('#loadingmessage8').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/checkHostVersionMovement',
         contentType: "application/json",
         dataType: "json",
         data: {
         	//"Test" : "1234"            	
         },
      success : function(data) {
   	   $('#loadingmessage8').hide();
            $("#loadingResult8").html(data.Result);
			console.log("Success Result");
         },
        error : function(error) {
        	console.log("Failure in returning data ");
         }
	 });

});

$("#NewAttribute").click(function() {
	$('#loadingmessage9').show();
	$.ajax({
         type: "GET",
         url : '${pageContext.request.contextPath}/NewAttribute',
         contentType: "application/json",
         dataType: "json",
         data: {
         	//"Test" : "1234"            	
         },
      success : function(data) {
   	   $('#loadingmessage9').hide();
            $("#loadingResult9").html(data.Result);
			console.log("Success Result");
         },
        error : function(error) {
        	console.log("Failure in returning data ");
         }
	 });

});

</script>

</body>
</html>