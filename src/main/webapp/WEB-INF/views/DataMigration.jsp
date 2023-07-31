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
    
   
<title>Data Migration</title>
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
    padding: 1px 7px;
    color: blue;
    text-align: center;
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
	<div class="row" >
		<div class="col-xl-12">
			<div class="card card-primary card-tabs cards-margin block">
				<div class="title_card">Run Migration</div>
				<div class="card-body cadr">
					<div class="card-group ">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> 
                					<div class="row">
	                					<!--  <div class="col-sm-1" style="align:center; margin-top: 8px; margin-left: -14px;">
	                						<span class="circle1">3</span>
	                					</div>-->
	                					<div class="col-sm-10">                				 
	                						<button class="button3" id="finance" style="align:center; margin-left: 20px;">Migrate Financial</button>
	                					</div>
	                				</div>
                				</div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage1" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult1" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="inventory">Migrate Inventory</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage2" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult2" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="warehouse">Migrate Warehouse</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage3" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult3" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="supplier">Migrate Supplier</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage4" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult4" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row" >
		<div class="col-xl-12">
			<div class="card card-primary card-tabs cards-margin block">
				<div class="title_card">Run IT Scripts</div>
				<div class="card-body cadr">
					<div class="card-group ">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="itscripts">Run DM ALL IT scripts</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage5" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult5" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="cumulitscripts">Run CUMULATE IT scripts</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage6" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult6" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="almitscripts">Move IT scripts to Item Table</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage7" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
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
	<div class="row" >
		<div class="col-xl-12">
			<div class="card card-primary card-tabs cards-margin block">
				<div class="title_card">Run Passive scripts</div>
				<div class="card-body cadr">
					<div class="card-group ">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="passivescripts">Run DM ALL Passive scripts</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage8" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult8" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="cumulpassivescripts">Run CUMULATE Passive scripts</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage9" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}./resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult9" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="almpassivescripts">Move PASSIVE scripts to Item Table</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage10" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult10" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row" >
		<div class="col-xl-12">
			<div class="card card-primary card-tabs cards-margin block">
				<div class="title_card">Run Move Inventory /Financial to Item Table</div>
				<div class="card-body cadr">
					<div class="card-group ">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="inv2itemscripts">Run Move Inventory to Item Table</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage11" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult11" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader" style="font-size: 12px;  padding:5px"><button class="button3" id="financialexcl2itemscripts"> Run move financial exclude [IT/ PASSIVE/Inventory] to Item  Table</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage12" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult12" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="categories">Migrate Categories</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage20" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult20" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader" style="font-size: 12px;  padding:5px"> <button class="button3" id="updateitem">Run update Item code / Node type / Manufacturer in Item Table</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage13" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult13" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row" >
		<div class="col-xl-12">
			<div class="card card-primary card-tabs cards-margin block">
				<div class="title_card">Run DM of AR from IT/Passive and Inventory</div>
				<div class="card-body cadr">
					<div class="card-group ">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader" style="font-size: 12px; padding:5px"> <button class="button3" id="runAR">Run DM of AR from IT/Passive and Inventory</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage14" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult14" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="runFAR">Run DM of FAR from FINANCIAL</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage15" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult15" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="runALC">Run Asset LifeCycle from FAR</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessageALC" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResultALC" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="runPOI">Run DM of PO_ITEM from FINANCIAL</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage16" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult16" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="runcompressPOI">Run DM_POITEM_TO_PO_ITEM</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage17" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult17" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="button3" id="runPO">Run DM of PO from PO_ITEM</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage18" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult18" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					
      						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="buttonsms" id="runtoken">token</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage18" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult18" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
      					
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader"> <button class="buttonsms" id="runairtime">Air_time</button></div>
                				<div class="card-body mycard CardBody">
                					<div id="loadingmessage18" class="loadingmsg">
                						<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
                					</div>
                					<div id="loadingResult18" class="loadingRes"></div>
                				</div>
           					 </div>
      					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 
<hr style="margin-top: 0rem;">
<P>  Run Migration  </P>
<p></p>

<button type="button" id="finance">1-Migrate Financial</button>
<button type="button" id="inventory">2-Migrate Inventory</button>
<button type="button" id="warehouse">3-Migrate Warehouse</button>
<button type="button" id="supplier">4-Migrate Supplier</button>
<P>  Run IT scripts  </P>
<button type="button" id="itscripts">5-Run DM ALL IT scripts</button>
<button type="button" id="cumulitscripts">6-Run CUMULATE IT scripts</button>
<button type="button" id="almitscripts">7-Move IT scripts to Item Table</button>
<P>  Run Passive scripts  </P>
<button type="button" id="passivescripts">8-Run DM ALL Passive scripts</button>
<button type="button" id="cumulpassivescripts">9-Run CUMULATE Passive scripts </button>
<button type="button" id="almpassivescripts">10-Move PASSIVE scripts to Item Table</button>
<P>  Run Move Inventory /Financial to Item Table  </P>
<button type="button" id="inv2itemscripts">11-Run Move Inventory to Item Table</button>
<button type="button" id="financialexcl2itemscripts">12-Run move financial exclude [IT/ PASSIVE/Inventory] to Item  Table</button>
<button type="button" id="updateitem">13-Run update Item code / Node type / Manufacturer in Item Table </button>
<P>  Run DM of AR & FAR   </P>
<button type="button" id="runAR">14-Run DM of AR from IT/Passive and Inventory</button>
<button type="button" id="runFAR">15-Run DM of FAR from FINANCIAL</button>
<button type="button" id="runPOI">16-Run DM of PO_ITEM from FINANCIAL</button>
<button type="button" id="runcompressPOI">17-Run DM_POITEM_TO_PO_ITEM</button>
<button type="button" id="runPO">18-Run DM of PO from PO_ITEM</button> -->

<script>
			$("#finance").click(function() {
				$('#loadingmessage1').show();
			  $.ajax({
			            type: "GET",
			            url : '${pageContext.request.contextPath}/readfinance',
			            contentType: "application/json",
			            dataType: "json",
			            data: {
			            	//"Test" : "1234"            	
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
			});


			$("#inventory").click(function() {
				$('#loadingmessage2').show();
					  $.ajax({
					            type: "GET",
					            url : '${pageContext.request.contextPath}/readinventory',
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


				$("#warehouse").click(function() {
					$('#loadingmessage3').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/readwarehouse',
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


			$("#supplier").click(function() {
				$('#loadingmessage4').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/readsupplier',
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

			$("#categories").click(function() {
				$('#loadingmessage20').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/migrateCategories',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage20').hide();
							                $("#loadingResult20").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });
				});
				
								
				
				$("#updateitem").click(function() {
					$('#loadingmessage13').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/updateitem',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage13').hide();
							                $("#loadingResult13").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
				$("#itscripts").click(function() {
					$('#loadingmessage5').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runitscripts',
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
				
				
				$("#cumulitscripts").click(function() {
					$('#loadingmessage6').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/cumulitscripts',
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
				
				
				$("#almitscripts").click(function() {
					$('#loadingmessage7').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runalmitscripts',
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
				
				$("#passivescripts").click(function() {
					$('#loadingmessage8').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runpassivescripts',
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
				
				
				$("#cumulpassivescripts").click(function() {
					$('#loadingmessage9').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/cumulpassivescripts',
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
				
				$("#almpassivescripts").click(function() {
					$('#loadingmessage10').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runalmpassivescripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage10').hide();
							                $("#loadingResult10").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
				$("#inv2itemscripts").click(function() {
					$('#loadingmessage11').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runinv2itemscripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage11').hide();
							                $("#loadingResult11").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
				
				
				$("#financialexcl2itemscripts").click(function() {
					$('#loadingmessage12').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runfinancialexcl2itemscripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage12').hide();
							                $("#loadingResult12").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
						$("#runAR").click(function() {
							$('#loadingmessage14').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runAR2itemscripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage14').hide();
							                $("#loadingResult14").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
				$("#runFAR").click(function() {
					$('#loadingmessage15').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runFAR2itemscripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage15').hide();
							                $("#loadingResult15").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});

				$("#runALC").click(function() {
					$('#loadingmessageALC').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runALCscripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessageALC').hide();
							                $("#loadingResultALC").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
				$("#runPOI").click(function() {
					$('#loadingmessage16').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runPOI2itemscripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage16').hide();
							                $("#loadingResult16").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
				
				
				$("#runcompressPOI").click(function() {
					$('#loadingmessage17').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runcompressPOI',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage17').hide();
							                $("#loadingResult17").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});
				
				
				
				
				$("#runPO").click(function() {
					$('#loadingmessage18').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runPOscripts',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage18').hide();
							                $("#loadingResult18").html(data.Result);
											console.log("Success Result");
								          },
									        error : function(error) {
									        	console.log("Failure in returning data ");
									         }
					 });

				});

				$("#runtoken").click(function() {
					console.log("run runtoken");  
					$('#loadingmessage38').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runTOKEN',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage18').hide();
							                $("#loadingResult18").html(data.Result);
											console.log("Success Result");
								          },
								          error: function (request, status, error) {
								             // alert(request.responseText);
								        	  console.log("error is"+ status);
								          }
					 });

				});
              //AirtimeAPIWeb  
				$("#runairtime").click(function() {
					console.log("run runairtime");  
					$('#loadingmessage38').show();
							  $.ajax({
							           type: "GET",
							           url : '${pageContext.request.contextPath}/runAIRTIME',
							           contentType: "application/json",
							           dataType: "json",
							           data: {
							           	//"Test" : "1234"            	
							           },
								       success : function(data) {
								    	   $('#loadingmessage18').hide();
							                $("#loadingResult18").html(data.Result);
											console.log("Success Result");
								          },
								          error: function (request, status, error) {
								             // alert(request.responseText);
								        	  console.log("error is"+ status);
								          }
					 });

				});
</script>

</body>
</html>