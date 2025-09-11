<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>CommScope</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- ADDED BY AHMAD TAFECH -->
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
<!-- 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	 -->
		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>	
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
	
	<!-- ALM GRID Scripts -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	        
<style>

.hide-row { visibility: hidden; }
       
.label{ 
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
					<select id="ordstat" class="form-control">
								<option value="Enabled" selected>Enabled</option>
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
		             		<a class="dropdown-item"  type="button" id="Activatewh" >Activate</a>
		             		<a class="dropdown-item" type="button" id="Deactivatewh" >Deactivate</a>
		             		<a class="dropdown-item" type="button" id="Cancelwh" >Cancel</a>
					 		<a class="dropdown-item" type="button" id="Blockwh" >Block</a>
	    	    	 		<a class="dropdown-item" type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </a>
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
  						<button type="button" id="tokenButton" class="btn btn-primary BtnActive mb-2" onclick="token()">
  						<i class="fas fa-hashtag"></i> Token
						</button>
  						<button type="button" id="tokenButton" class="btn btn-primary BtnActive mb-2" onclick="rack()">
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

		<div class="tab-pane fade" id="custom-tabs-schedule" role="tabpanel"
			aria-labelledby="schedule-tab">
			<p></p>
		</div>
			<!-- Log form -->
		<div class="tab-pane fade" id="custom-tabs-one-logs" role="tabpanel" aria-labelledby="logs-tab">
			<p></p>
		</div>
	</div>
</div>

</body>
<script>

function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}


$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});

$("#saveButton").click(  function() {
	
});


$('#dateTimePickerID').datetimepicker({
    format: 'MM/DD/YYYY hh:mm:ss A'   // include seconds here
});

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

function login() {
	console.log("Welcome to login method");	
    if (!validation()) {
        return;  // exit login() immediately
    }
/*	
	var fields = {"#ipAddress": "IP Address", "#username": "Username", "#password": "Password"};
	for (var id in fields) {
		if ($(id).val().trim().length === 0) {
			alert(fields[id] + " is required!");
			$(id).focus();  // optional: focus the empty field
			return;         // stop execution
		}
	}
*/	
/*

	var fieldsName = {"#ipAddress": "IP Address"}
	const fields = ["#ipAddress", "#username", "#password"];
	$.each(fields, function (index, fieldId) {
		if ($(fieldId).val().trim.length === 0)) {
		}
	});
	if ($("#username").val().trim.length ===0) {
		alert("Please enter username");
		return;
	}
	if ($("#password").val().trim.length ===0) {
		alert("Please enter password");
		return;
	}
	if ($("#ipAddress").val().trim.length ===0) {
		alert("Please enter IP Address");
		return;
	}
	 	 */
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeLogin',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.responseBody.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },		
        error: function(result) {
            alert("Login ajax failed, there is error: ", result);            
        }
    });
}

function token() {

	console.log("Welcome");	
}

function controller () {
	console.log("Welcome to controller method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }

/*

	var fieldsName = {"#ipAddress": "IP Address"}
	const fields = ["#ipAddress", "#username", "#password"];
	$.each(fields, function (index, fieldId) {
		if ($(fieldId).val().trim.length === 0)) {
		}
	});
	if ($("#username").val().trim.length ===0) {
		alert("Please enter username");
		return;
	}
	if ($("#password").val().trim.length ===0) {
		alert("Please enter password");
		return;
	}
	if ($("#ipAddress").val().trim.length ===0) {
		alert("Please enter IP Address");
		return;
	}
	 	 */
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeControllerX',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),			
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.networkManagerId);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("ControllerX ajax failed, there is error: ", result);            
        }
    });
}

function getPanel () {
	console.log("Welcome to getPanel method");
	
    if (!validation({"#rackID":"Rack ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeGetPanel',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Get Panel ajax failed, there is error: ", result);            
        }
    });
}


function patches () {
	console.log("Welcome to patches method");
	
    if (!validation({"#rackID":"Rack ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopePatches',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Patches ajax failed, there is error: ", result);            
        }
    });
}


function incompletePatches () {
	console.log("Welcome to incomplete patches method");
	
    if (!validation({"#rackID":"Rack ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeIncompletePatches',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Incomplete Patches ajax failed, there is error: ", result);            
        }
    });
}

function getNetworkInterface () {
	console.log("Welcome to getNetworkInterface method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeGetNetworkInterface',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.networkManagerId);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Get Network Interface ajax failed, there is error: ", result);            
        }
    });
}


function portStatus () {
	console.log("Welcome to portStatus patches method");
	
    if (!validation({"#rackID":"Rack ID", "#kitID" :"Kit ID", "#moduleID" : "Module ID", "#portID" : "Port ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopePortStatus',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
			"kitID" : $("#kitID").val(),
			"moduleID" : $("#moduleID").val(),
			"portID" : $("#portID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Port Status ajax failed, there is error: ", result);            
        }
    });
}


function eventNote () {
	console.log("Welcome to eventNote method");
	
    if (!validation({"#eventID" : "Event ID", "#timeout" : "Timeout"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeEventNote',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"eventID" : $("#eventID").val(),
			"timeout" : $("#timeout").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Event Note ajax failed, there is error: ", result);            
        }
    });
}


function setCurrentDateTime () {
	console.log("Welcome to setCurrentDateTime method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeSetCurrentDateTime',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");
            	$("#returnedControllerDateTime").text(data.controllerTime);
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Set Current Date Time ajax failed, there is error: ", result);            
        }
    });
}


function setDateTime () {
	console.log("Welcome to setDateTime method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }

    let rawDate = $("#selectDateTime").val(); // "09/09/2025 01:28:45 AM"
    let momentDate = moment(rawDate, "MM/DD/YYYY hh:mm:ss A");    
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeSetDateTime',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"dateTime" : momentDate.format("YYYY-MM-DDTHH:mm:ss"),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");
            	$("#returnedControllerDateTime").text(data.controllerTime);
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Set Date Time ajax failed, there is error: ", result);            
        }
    });
}

function generateWO() {
	console.log("Welcome to generateWO method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }

    var token =  $('input[name="csrfToken"]').attr('value');
    var woDetails = [];
 	var task = {};
 	var type = $("#woType").val();
 	console.log("The type is " +type);
 
 	task = {
			workOrderTaskId: parseInt($("#woTaskID").val(), 10),
 	 	 	info: $("#woInfo").val(),
 	 	 	action: $("#woAction").val(),
 	 	 	type: $("#woType").val(),
 	 	 	sRackId: $("#wo_sRackId").val(),
 	 	 	sKitId: $("#wo_sKitId").val(),
 	 	 	sModule: $("#wo_sModule").val(),
 	 	 	sPort: parseInt($("#wo_sPort").val(), 10), 	 	
 	 	};

 	if (type.includes("-Equipment")) {
 	 	// Build eEquipment list
 	 	var eEquip = [];
 	 	eEquip.push({ equipmentInfo: $("#equipInfo1").val() });
 	 	eEquip.push({ equipmentInfo: $("#equipInfo2").val() });
 	 	eEquip.push({ equipmentInfo: $("#equipInfo3").val() }); 	 	 	
 	 	task.eEquipment = eEquip;
 	}
 	else {
 		task.eRackId = $("#wo_eRackId").val();
 		task.eKitId = $("#wo_eKitId").val();
// 		task.eModule =;
 		task.ePort = parseInt($("#wo_ePort").val(), 10);
 	}

 	woDetails.push(task);
 	console.log(woDetails);
	
	$.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeGenWO', // URL of Generating WO
        dataType: "json",
        headers: {
            'X-CSRFToken': token 
        },
        data: JSON.stringify({
            username: $("#username").val(),
            password: $("#password").val(),
            ipAddress: $("#ipAddress").val(),
            woDetails: woDetails
        }),
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(data.responseBody);
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Generate Work Order ajax failed, there is error: ", result);            
        }
    });
}

function getWO() {
	console.log("Welcome to getWO() method");
	
    if (!validation({"#woTaskID": "Work Order Task ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeGetWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"workOrderTaskId": parseInt($("#woTaskID").val(), 10)
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);            	
            	$("#token").val(data.accessToken);
            	$("#woInfo").val(data.responseBody.info);
            	$("#woAction").val(data.responseBody.action);
            	$("#woType").val(data.responseBody.type);
     	 	 	$("#wo_sRackId").val(data.responseBody.sRackId);
     	 	 	$("#wo_sKitId").val(data.responseBody.sKitId);
     	 	 	$("#wo_sModule").val(data.responseBody.sModule);
     	 	 	$("#wo_sPort").val(data.responseBody.sPort);
     	 	  	if (data.responseBody.eEquipment && data.responseBody.eEquipment.length > 0) {
     	 	  		$("#equipInfo1").val(data.responseBody.eEquipment[0].equipmentInfo);
     	 	  		$("#equipInfo2").val(data.responseBody.eEquipment[1].equipmentInfo);
     	 	  		$("#equipInfo3").val(data.responseBody.eEquipment[2].equipmentInfo);
     	 	  	}            	
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason")) {
            		$("#responseStatusCode").val(data.reason);
            		$("#responseStatusCodeValue").val(data.reason);
                	$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
                	$("#responseStatusCodeValue").val(data.responseCodeValue);
                	$("#responseStatus").val(data.status);            	
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
            		$("#responseStatusCodeValue").val(data.responseCodeValue);
            		$("#responseStatus").val(data.status);
                }
            }
        },
        error: function(result) {
            alert("Get Work Order ajax failed, there is error: ", result);            
        }
    });
}

function listWO() {
	console.log("Welcome to listWO() method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeListWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val()
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("List Work Order ajax failed, there is error: ", result);            
        }
    });
}

function delWO() {
	console.log("Welcome to delWO() method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeDelWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"workOrderTaskId": parseInt($("#woTaskID").val(), 10)
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Delete Work Order ajax failed, there is error: ", result);            
        }
    });
}

function delAllWO() {
	console.log("Welcome to delAllWO() method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/CommScopeDelAllWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val()
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
            }
        },
        error: function(result) {
            alert("Delete All Work Orders ajax failed, there is error: ", result);            
        }
    });
}
/*
if('${SelectedIndex}' != "addNew"){
	var SelectedIndex = ${SelectedIndex};
	if('${ClientsCount}' != "addNew"){
		var ClientsCount = ${ClientsCount};
		if(($("#clientID").val()) != "" && ($("#clientID").val()) != null){
			if(SelectedIndex === ClientsCount){
	        	document.getElementById("btnLast").style.opacity = 0.5;
	        	$("#btnLast").hasClass("disabled");
	        	document.getElementById("btnLast").style.pointerEvents = "none";	        		
	        	document.getElementById("btnNexta").style.opacity = 0.5;
	        	document.getElementById("btnNexta").style.pointerEvents = "none";
				$("#btnNexta").hasClass("disabled");					
			}else{						
				if(!$("#btnNexta").hasClass("disabled")){
					$("#btnNext").click(function(){								
						var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=1";
						window.location.href =param;					
					});				
				}
				if(!$("#btnLst").hasClass("disabled")){	        				
	        		$("#btnLst").click(function(){	        					
						var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=4";
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
						var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=0";
						window.location.href =param;
					});
				}
				$("#btnFrst").click(function(){
	        		if(!$("#btnFrst").hasClass("disabled")){
						var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=3";
	        			window.location.href =param;
	        		}
	        	});
			}
		}
	}
}
$("#label-1").text((SelectedIndex)+"/"+ClientsCount);
$("#selectnav").autocomplete({
	source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: '${pageContext.request.contextPath}/GetAllClients',
			data: {
				"client" : $("#selectnav").val(),
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.ListClient);
				}
			},
			error: function(result) {
				alert("Error");
			}
    	});
	}, minLength:0, maxShowItems: 40, scroll:true, 
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
    	var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+(ui.item[0])+"&NavAction=2";
		window.location.href =param;
        return false;
     }
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $('<li class="each">').data( "item.autocomplete", item )
		.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		item[1] + '</span><br><span class="desc">' +
		item[2] + '</span><br><span class="desc">' +
		item[0] + '</span></div></li>').appendTo(ul);
	};
	
$("#selectnav").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});
*/
    
</script>
</html>