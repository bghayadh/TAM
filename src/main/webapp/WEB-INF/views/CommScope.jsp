<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>CommScope</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>	
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>

	<!--  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"> -->
		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>	
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
	
	<script src="${pageContext.request.contextPath}/resources/js/CommScope/boqPopup.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/CommScope/formStandard.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/CommScope/commScope.js"></script>
	
	<!-- ALM GRID Scripts -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	

<style>

.hide-row { visibility: hidden; }
       
.label { 
	display: table-caption;
	text-align: center;
	font-size: 20px;
	font-style: bold;
}
  
.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	width: 350px;
	z-index:9999;					
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
	color: #1D3763 !important;
}

.nav-link.active {
	background-color: #FFD966 !important;
	color: #00757c !important;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity=100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.custom-class-assignedto-modal .modal-dialog {
	max-width: 70% !important;   /* or 1000px, or any width you like */	
	width: 100%;
}
.custom-class-assignedto-modal .modal-body {
	height: 500px;
	overflow : auto;
}
    
.modal-header .btnGrp{
	position: absolute;
	top: 8px;
	right: 10px;
}

.btn-pop {
	background-color: #C2CBC0 !important;
	border-color: #C2CBC0;
}

.btn-pop:hover {
	color: #fff;
	background-color: #8696A0 !important;
	border-color: #8696A0 !important;
}

/* Extra small devices (phones, 600px and down) */
@media only screen and (max-width: 600px) {
	.modal-content, .modal-dialog {
		max-height: 100%;
		max-width: 100%;
 	}
}
/* Small devices (portrait tablets and large phones, 600px and up) */
@media only screen and (max-width: 600px) {
	.modal-content, .modal-dialog {
		max-height: 100%;
		max-width: 100%;
}
}

/* Medium devices (landscape tablets, 768px and up) */
@media only screen and (max-width: 768px) {
	.modal-content, .modal-dialog {
 		max-height: 100%;
		max-width: 100%;
	}
}

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (max-width: 992px) {
	.modal-content, .modal-dialog { 
		max-height: 100%;
    	max-width: 100%;
 	}
}

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
	.modal-content, .modal-dialog {
 		max-height: 100%;
 		max-width: 100%;
	}
}

</style>
            
</head>
<body>

<c:set var="pg" value="Setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
<p></p>
     
<div class="container-fluid">
	<div class="row">			
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">								
					<span class="input-group-text" style="color:green">CommScope Process</span>
					<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />					
				</div>
			</div>
		</div>			
		<div class="col-md-3">
			<div class="input-group-prepend">
				<span  class="input-group-text" style="width:210px;">Status</span>
					<select id="procStat" class="form-control" disabled>
						<option value="Enabled" selected>Enabled</option>
						<option value="Disabled" <c:if test = "${procStatus =='Disabled'}" > selected </c:if> >Disabled</option>
<!--								
								<option value="Draft" <c:if test = "${ordStatus =='Draft'}" > selected </c:if> >Draft</option>
								<option value="Activated" <c:if test = "${ordStatus =='Activated'}" > selected </c:if>>Activated</option>
								<option value="Deactivated" <c:if test = "${ordStatus =='Deactivated'}" > selected </c:if>>Deactivated</option>
								<option value="Cancelled" <c:if test = "${ordStatus =='Cancelled'}" > selected </c:if>>Cancelled</option>
								<option value="Blocked" <c:if test = "${ordStatus =='Blocked'}" > selected </c:if>>Blocked</option>
-->								
					</select>
			</div>							
		</div>
			
<!-- 					<div class="pad col-md-3 hide-row"></div> -->
		<div class="col-md-3 nextprvItems">
<!-- 		
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Client</span>
						<input type="text" id="selectnav" value="${selectnav}"
							style="width: 430px" class="form-control text-input" />				</div>
			</div>
 -->			
		</div>

		<div  class="col-md-3 text-right"  >
			<i>&nbsp</i><span class="dot"></span>
			<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>		
		</div>
	</div>
				
	<div class="row">		
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
						<input type="text" id="dateClient" readonly value="" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"/>
					  	<div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker"></div>
				</div>
			</div>
		</div>	
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2" />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker"></div>
				</div>
			</div>
		</div>			
		<div class=" col-md-3 nextprvItems">
<!-- 		
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
							class="btn btn-success next">&raquo;</a></li>
			  		</ul>
				</nav>
-->
		</div>
		<div class="col-md-3" style="text-align: right;">
		 	<div class="btn-group pull-right"> 					
		 		<div class="glyph">   
			 		<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 			data-placement="top" title="Form View" style="background: #da6815;"> 
			 			<i class="fa fa-edit"></i>
			        </button>
			        <button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        	onclick='window.location.href = "${pageContext.request.contextPath}/DiscoveryListView"'
			        	data-placement="top" title="List View"> 
			        	<i class="fa fa-bars"></i>
			        </button>			
				</div>			         
			</div>
		</div>
	</div>
</div>
	
<div class="container-fluid">		
	<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
			<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: auto;">
				<li class="nav-item"><a class="nav-link active" id="manual-tab" data-toggle="tab"
					href="#custom-tabs-one-manual" role="tab"
					aria-controls="custom-tabs-one-manual" aria-selected="true"
					style="color: gold;">MANUAL</a></li>
	            
	        	<li class="nav-item"><a class="nav-link" id="schedule-tab" data-toggle="tab" 
	        		href="#custom-tabs-schedule" role="tab" 
	        		aria-controls="#custom-tabs-schedule" aria-selected="false"
					style="color: gold;">SCHEDULE</a></li>
							
				<li class="nav-item"><a class="nav-link" id="logs-tab" data-toggle="tab"
	            	href="#custom-tabs-one-logs" role="tab"
	            	aria-controls="#custom-tabs-one-logs" aria-selected="false" style="color: gold;">LOGS</a></li> 
	                        
	        	<li class="nav-item ml-auto">
	       			<div class="dropdown" Style="display:inline-block;">
						<button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
		            	aria-haspopup="true" aria-expanded="true">Actions</button>	
		            	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		             		<a class="dropdown-item"  type="button" id="enableMain" onclick="enableMain()">Enable</a>
		             		<a class="dropdown-item" type="button" id="disableMain" onclick="disableMain()">Disable</a>
	           			</div> 
	       			</div>                        
					<button type="button" id="saveButton" class="btn btn-primary BtnActive">
						<i class="fas fa-save"></i> Save
					</button>
				</li>
	     	</ul>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div class="tab-content" id="custom-tabs-one-tabContent">
		<div class="tab-pane fade show active" id="custom-tabs-one-manual"
			role="tabpanel" aria-labelledby="manual-tab">
			<div class="row">
				<div class="col-6 col-sm-6 col-lg-6">
					<div class="row mt-3">
						<div class="col-xl-6">
							<div class="form-group">
								<div class="input-group-prepend">
      								<span class="input-group-text">IP Address</span>
      								<input type="text" class="form-control" id="ipAddress"/>
      							</div>
      						</div>
    					</div>
    					<div class="col-xl-6">
							<div class="form-group">
								<div class="input-group-prepend">
      								<span class="input-group-text">Port</span>
      								<input type="text" class="form-control" id="port"/>
      							</div>
      						</div>
    					</div>
  					</div>
					<div class="row mt-1">
						<div class="col-xl-6">
							<div class="form-group">
								<div class="input-group-prepend">					
      								<span class="input-group-text">Username</span>
      								<input type="text" class="form-control" id="username"/>
      							</div>
      						</div>
    					</div>
    					<div class="col-xl-6">
							<div class="form-group">
								<div class="input-group-prepend">    					
      								<span class="input-group-text">Password</span>
      								<input type="text" class="form-control" id="password"/>
      							</div>
      						</div>
    					</div>
  					</div>
  					<div class="mt-1">
    					<span class="input-group-text w-100">Token</span>
    					<input type="text" class="form-control" id="token"/>
  					</div>
  					<div class="mt-3">
						<div class="form-group">
							<div class="input-group-prepend">  					
    							<span class="input-group-text">Rack ID</span>
    							<input type="text" class="form-control" id="rackID"/>
    						</div>
    					</div>
  					</div>  					
  					<div class="mb-1">
  						<button type="button" id="loginButton" class="btn btn-primary BtnActive mb-2" onclick="login()">
						<i class="fas fa-sign-in-alt"></i> Login
						</button>
  						<button type="button" id="tokenButton" class="btn btn-primary BtnActive mb-2" onclick="newToken()">
  						<i class="fas fa-hashtag"></i> Token
						</button>
  						<button type="button" id="tokenButton" class="btn btn-primary BtnActive mb-2" onclick="getRack()">
						<i class="fas fa-layer-group"></i> Rack
						</button>
						<button type="button" id="controllerButton" class="btn btn-primary BtnActive mb-2" onclick="controller()">
  						<i class="fas fa-wave-square"></i> Controller  						
						</button>
						<button type="button" id="panelButton" class="btn btn-primary BtnActive mb-2" onclick="getPanel()">
						<i class="fas fa-ethernet"></i> Panel
						</button>
						<button type="button" id="patchButton" class="btn btn-primary BtnActive mb-2" onclick="patches()">
						<i class="fas fa-signature"></i> Patches
						</button>
					</div>
					<div class="mb-1">
						<button type="button" id="incompletePatchButton" class="btn btn-primary BtnActive mb-2" onclick="incompletePatches()">
						<i class="fas fa-signature"></i> Incomplete Patches
						</button>
						<button type="button" id="networkButton" class="btn btn-primary BtnActive mb-2" onclick="getNetworkInterface()">
							<i class="fas fa-network-wired"></i> Network Interface
						</button>						
					</div>					
					<div class="mb-3 p-3 border rounded d-flex flex-wrap align-items-end" style="gap: 15px;">
						<button type="button" id="portButton" class="btn btn-primary BtnActive mb-2" onclick="portStatus()">
						<i class="fas fa-clipboard-list"></i> Port Status
						</button>
						<div class="d-flex flex-column mb-2" style="min-width: 150px; max-width: 300px;">
							<span class="input-group-text d-flex justify-content-center">Kit ID</span>
								<input type="text" class="form-control" id="kitID" style="text-align: center;"/>
						</div>
						<div class="d-flex flex-column mb-2" style="min-width: 60px; max-width: 80px;">								
      						<span class="input-group-text d-flex justify-content-center">Module ID</span>
								<input type="text" class="form-control" id="moduleID" style="text-align: center;"/>
						</div>
						<div class="d-flex flex-column mb-2" style="min-width: 60px; max-width: 80px;">
      						<span class="input-group-text d-flex justify-content-center">Port ID</span>
								<input type="text" class="form-control" id="portID" style="text-align: center;"/>
						</div>
					</div>
					<div class="mb-3 p-3 border rounded d-flex flex-wrap align-items-end" style="gap: 15px;">
						<button type="button" id="eventNoteButton" class="btn btn-primary BtnActive mb-2" onclick="eventNote()">
						<i class="fas fa-clipboard-list"></i> Event Notifications
						</button>
						<div class="d-flex flex-column mb-2" style="min-width: 50px; max-width: 150px;">
							<span class="input-group-text d-flex justify-content-center">Event ID</span>
								<input type="text" class="form-control" id="eventID" style="text-align: center;"/>
						</div>
						<div class="d-flex flex-column mb-2" style="min-width: 150px; max-width: 300px;">
      						<span class="input-group-text d-flex justify-content-center">Timeout</span>
								<input type="text" class="form-control" id="timeout" style="text-align: center;" value=10000 />
						</div>						
					</div>
					<div class="mb-3 p-3 border rounded d-flex flex-wrap align-items-start" style="column-gap: 15px;">
						<button type="button" id="setDateTimeButton" class="btn btn-primary BtnActive mb-2" onclick="setDateTime()">
							<i class="fas fa-clock"></i> Set Date Time
						</button>					
						<div class="form-group">
							<div class="input-group-prepend" id="dateTimePickerID" data-target-input="nearest">
								<input type="text" id="selectDateTime" value="" style="max-width:500px; text-align: center;" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#dateTimePickerID" />
					   			<div class="input-group-append" data-target="#dateTimePickerID" data-toggle="datetimepicker">
									<div class="input-group-text"><i class="fa fa-calendar"></i></div>
								</div>
							</div>
						</div>
						<div class="w-100"></div>
						<div class="d-flex align-items-center">
							<button type="button" id="setCurrentDateTimeButton" class="btn btn-primary BtnActive" onclick="setCurrentDateTime()">
								<i class="fas fa-clock"></i> Set Current Date Time
							</button>
							<span id="returnedControllerDateTime" style="margin-left: 15px;"></span>
						</div>
					</div>
				</div>
				<div class="col-6 col-sm-6 col-lg-6">
					<div class="input-group flex-column mb-3">
						<span class="input-group-text">Response Body</span>							
							<textarea name="respBody" cols="120" rows="15" id="responseBody" class="form-control"></textarea>
					</div>
					<div class="input-group flex-column">
						<span class="input-group-text">Response Status Code</span>
						<input type="text" name="respStatCode" id="responseStatusCode" class="form-control">
					</div>
					<div class="input-group flex-column">
						<span class="input-group-text">Response Status Code Value</span>
						<input type="text" name="respStatCodeVal" id="responseStatusCodeValue" class="form-control">
					</div>
					<div class="input-group flex-column">
						<span class="input-group-text">Status</span>
						<input type="text" name="respStatus" id="responseStatus" class="form-control">
					</div>
				</div>				
			</div>
			<hr>
    		<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">Work Order Task ID</span>
      						<input type="text" class="form-control" id="woTaskID"/>
      					</div>
      				</div>
      			</div>
      			<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">Type</span>
      						<input type="text" class="form-control" id="woType"/>
      					</div>
      				</div>
      			</div>
      			<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">Action</span>
      						<input type="text" class="form-control" id="woAction"/>
      					</div>
      				</div>
      			</div>
  			</div>
			<div class="row">
				<div class="col-md-12">  			  				
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">Info</span>
      						<input type="text" class="form-control" id="woInfo"/>
      					</div>
      				</div>
      			</div>      			
      		</div>
    		<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">sRackId</span>
      						<input type="text" class="form-control" id="wo_sRackId"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">sKitId</span>
      						<input type="text" class="form-control" id="wo_sKitId"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">sModule</span>
      						<input type="text" class="form-control" id="wo_sModule"/>
      					</div>
      				</div>
  				</div>
  			</div>
    		<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">sPort</span>
      						<input type="text" class="form-control" id="wo_sPort"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">sPair</span>
      						<input type="text" class="form-control" id="wo_sPair"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">sStrand</span>
      						<input type="text" class="form-control" id="wo_sStrand"/>
      					</div>
      				</div>
  				</div>
  			</div>
    		<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">eRackId</span>
      						<input type="text" class="form-control" id="wo_eRackId"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">eKitId</span>
      						<input type="text" class="form-control" id="wo_eKitId"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">eModule</span>
      						<input type="text" class="form-control" id="wo_eModule"/>
      					</div>
      				</div>
  				</div>
  			</div>
    		<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">ePort</span>
      						<input type="text" class="form-control" id="wo_ePort"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">ePair</span>
      						<input type="text" class="form-control" id="wo_ePair"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">eStrand</span>
      						<input type="text" class="form-control" id="wo_eStrand"/>
      					</div>
      				</div>
  				</div>
  			</div>  		
    		<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">Equipment Info</span>
      						<input type="text" class="form-control" id="equipInfo1"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">Equipment Info</span>
      						<input type="text" class="form-control" id="equipInfo2"/>
      					</div>
      				</div>
  				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
      						<span class="input-group-text">Equipment Info</span>
      						<input type="text" class="form-control" id="equipInfo3"/>
      					</div>
      				</div>
  				</div>
  			</div>
  			
    		<div class="row" style="gap: 30px; margin-left: 0px;">
					<button type="button" id="generateWO_Button" class="btn btn-primary BtnActive" style="width: 223px;" onclick="generateWO()">
						<i class="fas fa-plus"></i> Generate Work Order
					</button>
					<button type="button" id="getWO_Button" class="btn btn-primary BtnActive" style="width: 223px;" onclick="getWO()">
						<i class="fab fa-get-pocket"></i> Get Work Order
					</button>
					<button type="button" id="listWO_Button" class="btn btn-primary BtnActive" style="width: 223px;" onclick="listWO()">
						<i class="fas fa-list"></i> List All Work Order
					</button>
					<button type="button" id="delWO_Button" class="btn btn-primary BtnActive" style="width: 223px;" onclick="delWO()">
						<i class="fas fa-trash"></i> Delete Work Order
					</button>
					<button type="button" id="delAllWO_Button" class="btn btn-primary BtnActive" style="width: 223px;" onclick="delAllWO()">
						<i class="fas fa-eraser"></i> Delete All Work Order
					</button>
			</div>
  			
  			
			<p></p>
		</div>	

		<div class="tab-pane fade" id="custom-tabs-schedule" role="tabpanel" aria-labelledby="schedule-tab">
			<div class="table-responsive-sm" id="tableContainer">
				<p></p> 
				<table id ="boqTable" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
					<thead>
						<tr id="boq_tr" class="fixed-headerr">
							<th><button type="button" id="selectAll" class="main"><span class="sub"></span>Select</button></th>
						    <th>Name</th>
						    <th>Status</th>
						    <th>Class Name</th>
						    <th>Cron Expression</th>
						    <th>Calendar Cron</th>
						    <th>Run Manually</th>
						    <th>Process ID</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<form>
				<input type = "hidden" type="text" id="RowIndex" value="">
				<input type="button" class="add-row" value="Add Row" onClick="addNewRow('next')">
				<button type="button" class="delete-row">Delete Row</button>
			</form>
		</div>
		
		
			<!-- Log form -->
		<div class="tab-pane fade" id="custom-tabs-one-logs" role="tabpanel" aria-labelledby="logs-tab">
			<p></p>
		</div>
		
	</div>
</div>

<div id ="popupModal" class="modal fade custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header" style="background-color: #2678CC ; height: 55px; display:flex; align-items:center;">
				<h5 id ="popupNb" class="modal-title" style="font-weight:bold; color: #E9ECEF ;position: relative;"></h5>
				<div style="float: right;">
					<button  name="runProcBtn"  onclick="runProc(this)" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -40px; font-weight: bold;">Run Now </button>				
					<button  name="insertBelow"  onclick="insertRowBelow()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -30px; font-weight: bold;">Insert Below </button>
					<button  name="insertAbove"  onclick="insertRowAbove()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -20px; font-weight: bold;">Insert Above </button>
					<button  name="deleteBoqRow" onclick="deleteBoqRow()"   class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -10px; font-weight: bold;">Delete</button>
					<button  name ="previousRow" onclick="prevRow()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 0px; font-weight: bold;">Previous</button>
	            	<button  name="nextRow" onclick="nextRow()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 10px; font-weight: bold;">Next</button> 
					<button type="button" name="closeModPartPopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
					<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
				</div>
			</div>
			<div class="modal-body">
				<ul class="nav nav-tabs" id="popupTab" role="tablist" style="background-color: #00757C;">
					<li class="nav-item">
						<a class="nav-link active" id="info-tab" style="color: gold;" data-toggle="tab" href="#item" role="tab" aria-controls="item" aria-selected="true">INFORMATION</a>
					</li>  
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="info-tab-content" role="tabpanel" aria-labelledby="info-tab">
						<p></p>  					
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
									<div class="input-group-prepend">
									<span class="input-group-text" >Process Name</span>
									<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupProcName" style="width:675px; height:37px"/>					
									</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
									<div class="input-group-prepend">
									<span class="input-group-text">Process Status</span>
									<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupProcStatus" style="width:674px; height:37px"/>
									</div>
									</div>
								</div>
							</div>							
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
									<div class="input-group-prepend">
									<span class="input-group-text" >Process Class Name</span>
									<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupProcClassName" style="width:675px; height:37px"/>					
									</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
									<div class="input-group-prepend">
									<span class="input-group-text">Process Cron Expression</span>
									<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupProcCronExpr" style="width:675px; height:37px"/>
									</div>
									</div>
								</div>
							</div>							
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
			</div>
		</div>
	</div>
</div>

</body>
<script>

function validation (extraFields) {
	var fields = {"#ipAddress": "IP Address", "#username": "Username", "#password": "Password"};
    if (extraFields && typeof extraFields === "object") {
        Object.assign(fields, extraFields);
    }
	for (var id in fields) {
		if ($(id).val().trim().length === 0) {
			alert(fields[id] + " is required!");
			$(id).focus();  // optional: focus the empty field
			return false;         // stop execution
		}
	}
	return true;	
}
    
</script>
</html>