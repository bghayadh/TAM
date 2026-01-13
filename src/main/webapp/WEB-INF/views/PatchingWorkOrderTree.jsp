 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<title>Patching WorkOrder Tree</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">


    
    
     <script src="${pageContext.request.contextPath}/resources/js/WorkOrder/PatchingTree.js"></script>
     
     
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/xlsx.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jszip.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,300">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">




<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
<script src="${pageContext.request.contextPath}/resources/js/context-menu.js"></script>



<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
																											  
	  <script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>

	
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	







<style>
.close {
  float: right;
  font-size: 1.5rem;
  font-weight: 700;
  line-height: 1;
  color: white !important;
  text-shadow: 0 1px 0 #fff;
  opacity: .5;
}
.btn-save{
    background-color: #C2CBC0 !important ;
    border-color: #C2CBC0;
}
.btn-save:hover {
    color: #fff;
    background-color: #8696A0 !important;
    border-color: #8696A0 !important;
}
.changecolclick{
 	background-color: #F57E25 !important; 
 	border-color: #F57E25  !important;  
}
.btn-delete:hover{
    background-color: #4d8037 !important;
    border-color: #4d8037;    
    }
.nav-link.active {
  color: #1D3763 !important;
}
.tab button.active {
    background: white;
    color: #1D3763 !important;
}
.tab button.active {
    background: #FFD966;
    color: #1D3763 !important;
}
.tablinks.active {
 	width: 16%;
}

.centered-message {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 180px; /* Adjust as needed */
  text-align: center;
  color: black;
  border: 1px dashed #007b7c;
  border-radius: 12px;
  font-size: 1.3rem;
  font-weight: 500;
  box-shadow: 0 3px 15px rgba(0,50,0,0.05);
  margin: 50px auto;
  max-width: 60%;
}
</style>
</head>

<body>
	 <c:set var="pg" value="inventory" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
	<hr style="margin-top: -3px;">
	
	<div class="container-fluid">

	<div class="row second" style="margin-top: 15px;">
			<div class="col-md-9">
			</div>
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
		 			<button type="button" class="btn btn-danger" data-toggle="tooltip"
							data-placement="top" title="Tree View"
							style="background: #da6815;">
							<i class="fas fa-sitemap"></i>
						</button>
			 			<button  type="button" id="Fview"  class="btn btn-light" data-toggle="tooltip" 
			 				data-placement="top" title="Form View"
			 				onclick='window.location.href = "${pageContext.request.contextPath}/PatchingWorkOrderFormView"'> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			data-placement="top" title="List View"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/PatchingWorkOrderListView"'
			        			> 
			        			<i class="fa fa-bars"></i>
			        	</button>
						
			        </div>  
		        </div>
			</div>
		</div>
		
		<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top: 15px;">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;">PATCHING WORK ORDER TREE</li>
		             <li class="nav-item ml-auto">  
             
 

               
			
	        				

								<button type="button" id="saveButton"
        class="btn btn-primary BtnActive"
        onclick="savePatchAndTask()" disabled>
    <i class="fa fa-save"></i> Save
</button>

						<button type="button" id="deleteButton"
        class="btn btn-primary BtnActive"
        onclick="deletePatchAndTask()" disabled>
    <i class="fa fa-trash"></i> Delete
</button>
								
								 
							</li>
		           
				
				   			
		     </ul>
		     
		</div>
		</div>


            </div>
	<br>
	
	<br>
	
	<div id="mainDiv">
	
		<div id="left" style="width:500px">
			
		
			<div id="patchingWorkOrder_tree" class="tree well top-btn-filter" style=" position: static; z-index: 3;"></div>
			
			
					</div>
		<div id="right" class="first-div" style="display:none; ">
	
 <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Patching ID</span>
                    <input type="text" id="patchingId" class="form-control text-input" readonly />
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Status</span>
                    <select class="form-control" id="patchingStatus">
                        <option value="-- Select Option --">-- Select Option --</option>
                        <option value="Draft">Draft</option>
                        <option value="Approved">Approved</option>
                        <option value="Rejected">Rejected</option>
                         <option value="Completed">Completed</option>
                        <option value="Closed">Closed</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <!-- ASSIGNED TO -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-11">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Assigned To</span>
                    <input type="text" id="assignedTo" class="form-control text-input" />
                </div>
            </div>
        </div>
       
    </div>

    <!-- PLANNED / ACTUAL EXECUTION -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Planned Execution</span>
                    <input type="datetime-local" id="plannedExecutionDate" class="form-control text-input" />
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Actual Execution</span>
                    <input type="datetime-local" id="actualExecutionDate" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

 

    <!-- CREATED / LAST MODIFIED (READ ONLY) -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Created Date</span>
                    <input type="text" id="createdDate" class="form-control text-input" readonly />
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Last Modified</span>
                    <input type="text" id="lastModifiedDate" class="form-control text-input" readonly />
                </div>
            </div>
        </div>
    </div>
<div class="row" style="margin-left:90px;">
    <div class="col-md-11">
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">
                        Patching Notes
                    </span>
                </div>
                <textarea id="patchingNote"
                          class="form-control text-input"
                          rows="6"
                          placeholder="Enter patching notes here..."></textarea>
            </div>
        </div>
    </div>
</div>

		</div>
		
		
		

<div id="right" class="third-div" style="display:none;">

    <!-- WO TASK ID / TASK TYPE -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">WO Task ID</span>
                    <input type="text" id="woTaskId" class="form-control text-input" />
                </div>
            </div>
        </div>
       <div class="col-md-6">
    <div class="form-group">
        <div class="input-group-prepend">
            <span class="input-group-text" style="color:green;">Task Status</span>
            <select id="taskStatus" class="form-control text-input">
                <option value="">-- Select Task Type --</option>
                <option value="Completed">Completed</option>
                <option value="Notcompleted">Not Completed</option>
            </select>
        </div>
    </div>
</div>

    </div>

    <!-- TASK STATUS / CREATION DATE -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
    <div class="form-group">
        <div class="input-group-prepend">
            <span class="input-group-text" style="color:green;">Task Type</span>
            <select id="taskType" class="form-control text-input">
                <option value="" disabled selected>-- Select Task Type --</option>
                <option value="Add">Add</option>
                <option value="Remove">Remove</option>
            </select>
        </div>
    </div>
</div>

        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Creation Date</span>
                    <input type="datetime-local" id="creationDate" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- LAST MODIFIED / COMPLETION -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Last Modified</span>
                    <input type="datetime-local" id="lastModificationDate" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Completion Date</span>
                    <input type="datetime-local" id="completionDate" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- DB ID / DB PORT -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">DB ID</span>
                    <input type="text" id="dbId" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">DB Port ID</span>
                    <input type="text" id="dbPortId" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- ROW COL INDEX / ROW NUMBER -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Row Col Index</span>
                    <input type="number" id="rowColIndex" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Row Number</span>
                    <input type="number" id="rowNumber" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- COLUMN NUMBER / NEAR MODULE -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Column Number</span>
                    <input type="number" id="columnNumber" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Near Module</span>
                    <input type="text" id="nearModule" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- NEAR PORT / NEAR PATCH -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Near Port Num</span>
                    <input type="text" id="nearPortNum" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Near Patch Type</span>
                    <input type="text" id="nearPatchType" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- FP LOCATION TYPE / LOCATION ID -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Location Type</span>
                    <input type="text" id="fpLocationType" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Location ID</span>
                    <input type="text" id="fpLocationId" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- FP LOCATION NAME / LOCATION -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Location Name</span>
                    <input type="text" id="fpLocationName" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Location</span>
                    <input type="text" id="fpLocation" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- FP EQUIPMENT TYPE / EQUIPMENT -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Equipment Type</span>
                    <input type="text" id="fpEquipmentType" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Equipment</span>
                    <input type="text" id="fpEquipment" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- FP EQUIPMENT ID / NAME -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Equipment ID</span>
                    <input type="text" id="fpEquipmentId" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Equipment Name</span>
                    <input type="text" id="fpEquipmentName" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- ADDRESS / TUBE NB -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Address</span>
                    <input type="text" id="fpAddress" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Tube NB</span>
                    <input type="text" id="fpTubeNb" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- STRAND COLOR / TUBE COLOR -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Strand Color</span>
                    <input type="text" id="fpStrandColor" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Tube Color</span>
                    <input type="text" id="fpTubeColor" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- STRAND NAME / TUBE ID -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Strand Name</span>
                    <input type="text" id="fpStrandName" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Tube ID</span>
                    <input type="text" id="fpTubeId" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- TUBE NAME / FIBER ID -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Tube Name</span>
                    <input type="text" id="fpTubeName" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Fiber ID</span>
                    <input type="text" id="fpFiberId" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- FIBER NAME / KIT SERIAL -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Fiber Name</span>
                    <input type="text" id="fpFiberName" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Kit Serial Num</span>
                    <input type="text" id="fpKitSerialNum" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- MODULE / PORT -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Module</span>
                    <input type="text" id="fpModule" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Port Num</span>
                    <input type="text" id="fpPortNum" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>

    <!-- JUNCTION -->
    <div class="row" style="margin-left:90px;">
        <div class="col-md-5">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Junction ID</span>
                    <input type="text" id="fpJunctionId" class="form-control text-input" />
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">FP Junction Name</span>
                    <input type="text" id="fpJunctionName" class="form-control text-input" />
                </div>
            </div>
        </div>
    </div>
    
     <div class="row" style="margin-left:90px;">
       
        <div class="col-md-6">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Patching ID</span>
                    <input type="text" id="taskPatchingId" class="form-control text-input" style="display:none"/>
                </div>
            </div>
        </div>
    </div>

</div>


<div id="right" class="second-div">
  <div class="centered-message">
    Please select one of the Patching Work Oder  from the left-tree
  </div>
</div>
	
</div>			
</body>

<script>





//zeinaaaaa
var patchingResponse = ${PatchingList};
    var PatchingList = patchingResponse.PatchingList;

    CreateTree_PatchingWorkOrder(PatchingList);

</script>

</html>
