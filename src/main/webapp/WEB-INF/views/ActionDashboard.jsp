<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>Action Dashboard</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!--  
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	jquery.easypiechart.js
	-->

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/fusionchartsold.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/ListView.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />

<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link
	href="${pageContext.request.contextPath}/resources/css/all.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header.css">


<style>

  #tooltip {
      position: fixed;
      width: auto;
      height: auto;
      display: none;
      margin-top:-35px;
      background-color: #FFDC00;
      color: #000000;
      padding: 5px;
      border-radius: 5px;
    }
    
fieldset {
	border: 2px solid #333;
	border-radius: 8px; /* Optional: adds rounded corners */
	padding: 10px; /* Optional: adds padding inside the fieldset */
	text-emphasis-color: #A9A9A9 !important;
	font-size: 15px;
	font-family: cursive;
	border-color: #4B8C8C;
}

legend {
	font-weight: bold;
	color: #4B8C8C;
}
</style>
</head>
<body>
	<c:set var="pg" value="dashboard" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>


	<p></p>

	<div class="container-fluid ">
		<div class="row">

			<div class="col-md-2">

				<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
						<div class="input-group-text">

							<input type="checkbox" value = "${superAgentPermission}" disabled/> <span style="margin-left: 10px;">
								Based on my Username </span>
						</div>
					</div>
				</div>

			</div>
			<div class="col-md-2">
			<div class="form-group">
					<div class="input-group-prepend">
						<input type="text" readonly value="${userFullName}"
							class="form-control text-input" style="width:auto; margin-left:-40px;"/>
					</div>
				</div>
			</div>
			<div class="col-md-2">

				<div class="form-group" >
					<div class="input-group-prepend" data-target-input="nearest">
						<div class="input-group-text">

							<input type="checkbox" id="captureSpeed" value="${captureSpeed}" checked/> <span
								style="margin-left: 10px;">All</span>
						</div>
					</div>
				</div>

			</div>

		</div>
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<legend>Discovery New</legend>
						    <table id ="dnApptab" class="table table-bordered" style="display:block; margin-left:10px; overflow-y: auto;">
						            <thead>
						            <tr>
						               
						                <th>Need Approval</th>
						                <th onmouseover="displayInputName(this)" onmouseout="hideTooltip()">Need PM or OM Approval <div id="tooltip"></div></th>
						                <th onmouseover="displayInputName(this)" onmouseout="hideTooltip()">Need AUM Approval <div id="tooltip"></div></th>
						                <th onmouseover="displayInputName(this)" onmouseout="hideTooltip()">Need FM Approval <div id="tooltip"></div></th>
						                <th>Total</th>
						                
						            </tr>
						            </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>



					<!-- Additional form elements go here -->
				</fieldset>

			</div>
		</div>
		<p></p>
		<div class="row">
			<div class="col-md-6">
				<fieldset>
					<legend>Purchase Request</legend>
						    <table id ="prApptab" class="table table-bordered" style="display:block; margin-left:10px; overflow-y: auto;">
						            <thead>
						            <tr>
						                <th>Need Approval</th>
						                <th>Need to be Completed or Closed</th>
						            </tr>
						            </thead>
						        <tbody>
						        </tbody>
						    </table>
				</fieldset>

			</div>
			<div class="col-md-6">
				<fieldset>
					<legend>Purchase Order</legend>
						    <table id ="poApptab" class="table table-bordered" style="display:block; margin-left:10px; overflow-y: auto;">
						            <thead>
						            <tr>
						                <th>Need Approval</th>
						                <th>Need to be Completed or Closed</th>
						            </tr>
						            </thead>
						        <tbody>
						        </tbody>
						    </table>
				</fieldset>

			</div>
		</div>
		
		<p></p>
		<div class="row">
			<div class="col-md-6">
				<fieldset>
					<legend>Goods Received</legend>
						    <table id ="grApptab" class="table table-bordered" style="display:block; margin-left:10px; overflow-y: auto;">
						            <thead>
						            <tr>
						                <th>Need Approval</th>
						                <th>Need to be Completed or Closed</th>
						            </tr>
						            </thead>
						        <tbody>
						        </tbody>
						    </table>
				</fieldset>

			</div>
			<div class="col-md-6">
				<fieldset>
					<legend>Work Order</legend>
						    <table id ="woApptab" class="table table-bordered" style="display:block; margin-left:10px; overflow-y: auto;">
						            <thead>
						            <tr>
						                <th>Need Approval</th>
						                <th>Need to be Completed or Closed</th>
						            </tr>
						            </thead>
						        <tbody>
						        </tbody>
						    </table>
				</fieldset>

			</div>
		</div>
	</div>

</body>

<script type="text/javascript">

//get all colmns count per row
function count(array){
    var c = 0;
    for(i in array) // in returns key, not object
        if(array[i] != undefined)
            c++;
    return c;
}
		var dnApprovalsCount = ${dnApprovalsCount};
		var prApprovalsCount = ${prApprovalsCount};
		var poApprovalsCount = ${poApprovalsCount};
		var grApprovalsCount = ${grApprovalsCount};
		var woApprovalsCount = ${woApprovalsCount};
		
	

		if(dnApprovalsCount != 'addNew'){
			var itemActionRow = null;
			var appRowCount= count(dnApprovalsCount[0]);
			for(i=0;i<dnApprovalsCount.length;i++){
				itemActionRow = "<tr>";
				for(j=0;j<appRowCount;j++){
					
			   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+dnApprovalsCount[0][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
				   	 
					}

					itemActionRow=itemActionRow+"</tr>";
				}
			$("#dnApptab > tbody").append(itemActionRow);
		}

		if(prApprovalsCount != 'addNew'){
			var itemActionRow = null;
			var appRowCount= count(prApprovalsCount[0]);
			for(i=0;i<prApprovalsCount.length;i++){
				itemActionRow = "<tr>";
				for(j=0;j<appRowCount;j++){
					
			   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+prApprovalsCount[0][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
				   	 
					}

					itemActionRow=itemActionRow+"</tr>";
				}
			$("#prApptab > tbody").append(itemActionRow);
		}

		if(poApprovalsCount != 'addNew'){
			var itemActionRow = null;
			var appRowCount= count(poApprovalsCount[0]);
			for(i=0;i<poApprovalsCount.length;i++){
				itemActionRow = "<tr>";
				for(j=0;j<appRowCount;j++){
					
			   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+poApprovalsCount[0][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
				   	 
					}

					itemActionRow=itemActionRow+"</tr>";
				}
			$("#poApptab > tbody").append(itemActionRow);
		}

		if(grApprovalsCount != 'addNew'){
			var itemActionRow = null;
			var appRowCount= count(grApprovalsCount[0]);
			for(i=0;i<grApprovalsCount.length;i++){
				itemActionRow = "<tr>";
				for(j=0;j<appRowCount;j++){
					
			   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+grApprovalsCount[0][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
				   	 
					}

					itemActionRow=itemActionRow+"</tr>";
				}
			$("#grApptab > tbody").append(itemActionRow);
		}

		if(woApprovalsCount != 'addNew'){
			var itemActionRow = null;
			var appRowCount= count(woApprovalsCount[0]);
			for(i=0;i<woApprovalsCount.length;i++){
				itemActionRow = "<tr>";
				for(j=0;j<appRowCount;j++){
					
			   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+woApprovalsCount[0][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
				   	 
					}

					itemActionRow=itemActionRow+"</tr>";
				}
			$("#woApptab > tbody").append(itemActionRow);
		}
		function displayInputName(inputElement) {
			  // Get the input value
			  console.log(inputElement.innerText)
			  var inputValue = inputElement.innerText;
				console.log("Hello");
			  // Get the tooltip element
			  var tooltip = document.getElementById("tooltip");

			  // Set the tooltip content
			  if(inputValue == "Need PM or OM Approval") {inputValue="Project Manager";}
			  if(inputValue == "Need AUM Approval") {inputValue="Asset Manager";}
			  if(inputValue == "Need FM Approval") {inputValue="Finance Manager";}
			  
			  tooltip.innerText = inputValue;

			  var rect = inputElement.getBoundingClientRect();
			  tooltip.style.left = rect.left + "px";
			  tooltip.style.top = rect.top - tooltip.offsetHeight + "px";


			  // Display the tooltip
			  tooltip.style.display = "block";
			}

			function hideTooltip() {
			  // Hide the tooltip when the mouse is not over the input
			  var tooltip = document.getElementById("tooltip");
			  tooltip.style.display = "none";
			  console.log("Bye");
			}
		

</script>
</html>