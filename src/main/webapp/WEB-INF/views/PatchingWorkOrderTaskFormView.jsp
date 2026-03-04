
<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>WO Task Form View</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script
	src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>


<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />

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
<script
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />



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

#DbModule th {
    vertical-align: middle !important;
    text-align: center; /* optional: centers horizontally */
}
#popUpDiv {
	position: fixed;
	top: 30%;
	left: 50%;
	background-color: #eeeeee;
	border: 5px solid #08526d;
	width: 400px;
	height: 450px;
	margin-left: -150px;
	margin-top: -95px;
	-moz-border-radius: 16px;
	-webkit-border-radius: 16px;
	border-radius: 16px;
	box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px #000000;
	z-index: 9003;
	/*above nine thousand*/
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	z-index: 9999;
	width: 350px;
}


.ppuB {
	display: block;
	border: 1px solid #ced4da;
	border-radius: .25rem;
	width: 100%;
	background-color: #e9ecef;
	text-align: center;
	padding-top: 10px;
}

#popupBarcode {
	
}

.ui-autocomplete {
	max-height: 250px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 10px;
	z-index: 9999;
	width: 350px;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.dot {
	height: 17px;
	width: 17px;
	background-color: chartreuse;
	border-radius: 50%;
	display: inline-block;
	margin-top: 10px;
}

.dotStatus {
	height: 15px;
	width: 15px;
	border-radius: 50%;
	display: inline-block;
	margin-top: 10px;
	margin-left: 10px;
}

.hide-row {
	display: none;
}

#wrapper {
	width: 100%;
	display: grid;
	max-height: 100px;
	grid-gap: 100px;
	grid-column-gap: 10px !important;
	grid-row-gap: 8px !important;
	display: flex;
	grid-template-rows: 1fr 1fr !important;
	grid-template-columns: 1fr 1fr 1fr !important;
}

#one {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#two {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#three {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#four {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#five {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

#six {
	min-width: 380px;
	margin: 50px;
	min-height: 260px;
	border-radius: 10px;
	padding: 10px;
}

.container {
	min-width: 100%;
}

.divv1 {
	text-align: center;
	font-family: cursive;
	color: #DCF8C6;
	border-radius: 25px 25px 0px 0px !important;
	border-style: groove;
	border-width: 2px;
	border-color: #4B8C8C;
	box-shadow: 5px 5px 5px rgb(75, 140, 140);
}

.divv2 {
	text-align: center;
	justify-content: center;
	background-color: lightgray;
	border-radius: 0px 0px 25px 25px !important;
	font-size: 13px;
	font-family: cursive;
	border-style: groove;
	border-width: 2px;
	border-color: #4B8C8C;
	box-shadow: 5px 5px 5px rgb(75, 140, 140);
	border-top: none;
}

tr {
	height: 40px;
}

table {
	align: top;
	margin-left: auto;
	margin-right: auto;
}

.TD {
	border-bottom: 1pt solid black;
}

.custom-class-assignedto-modal .modal-dialog {
	width: 100%;
}

.custom-class-assignedto-modal .modal-body {
	height: 500px;
	overflow: auto;
}

.modal-header .btnGrp {
	position: absolute;
	top: 8px;
	right: 10px;
}

.display-none {
	display: none;
}

button .fa {
	font-size: 16px;
	margin-left: 10px;
}

button:focus {
	outline: none;
}

#collapseOne {
	width: 800px;
	float: right;
}

#areaTab {
	float: left; /* important */
	/* width:595px; */
	height: auto;
}

#left {
	float: left;
	width: 40%;
	height: 100%;
}

#right {
	padding-right: 0%;
	float: right;
	width: 60%;
	height: 100%;
}

#mapText {
	border: hidden;
	width: 125px;
	height: 25px;
	border: hidden;
	background-color: #87CEEB;
	margin-left: 20px;
	text-align: center;
}

#fwhbutton {
	
}

#twhbutton {
	
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
	.input[type=text] {
		width: 100%;
		margin-top: 0;
	}
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

.nav-link.active {
 background-color: #FFD966 !important;
    color: #00757c !important;
    }
    
    .case
	{
	text-align:center;
	font-size: 16px;
	color:#0000FF;
	position:relative;
	top:7px;
	}
	.btn-pop {
    background-color: #C2CBC0 !important;
    border-color: #C2CBC0;
    3
}

.btn-pop:hover {
    color: #fff;
    background-color: #8696A0 !important;
    border-color: #8696A0 !important;
}
</style>

</head>
<body>
	<script type="text/javascript">

</script>
	<c:set var="pg" value="inventory" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>

	<!--  end of general head page -->
	<p></p>
	<div class="container-fluid">
		<div class="row">

			<div class="col-md-3">
				<!-- class="form-control text-input"  -->
				<div class="form-group  ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green; width: auto;">Work
							Order Task ID</span> <input type="text" readonly id="wocode"
							value="${workOrdIdTask}" class="form-control text-input" />
					</div>

				</div>
			</div>

			<div class="col-md-3">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width: auto;">Status</span> <select
						name="cars" id="wostat"
						style="width: 350px; text-align-last: center;"
						class="form-control text-input">

						<option value="Completed"
							<c:if test = "${woStatus =='Completed'}" > selected </c:if>>Completed</option>
						<option value="Not Completed"
							<c:if test = "${woStatus =='Not Completed'}"> selected </c:if>>Not Completed</option>
						 <option value=""
        <c:if test="${woStatus != 'Completed' && woStatus != 'Not Completed'}">selected</c:if>>
        -- Select status --
    </option>
					</select>

				</div>
			</div>

			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Other Tasks</span> <input
							type="text" id="selectnav" value="${selectnav}"
							style="width: 430px" class="form-control text-input" />
					</div>
				</div>
			</div>

			<div class="col-md-3 text-right">
				<i>&nbsp</i><span class="dot"></span> <i>&nbsp</i> <label
					for="formStatus" id="formStatus" style="float: right;">Saved</label>
			</div>

		</div>

		<div class="row">


			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1"
						data-target-input="nearest">
						<span class="input-group-text" style="width: auto;">Created
							Date</span> <input type="text" id="wocreatedate" readonly
							value="${wocreationDate}"
							class="form-control datetimepicker-input "
							data-toggle="datetimepicker" data-target="#datetimepicker1" />
						<div class="input-group-append" data-target="#datetimepicker1"
							data-toggle="datetimepicker"></div>
					</div>
				</div>
			</div>

			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker2"
						data-target-input="nearest">
						<span class="input-group-text" style="width: auto;">Last
							Modify Date</span> <input type="text" id="wolstmodifdate" readonly
							value="${wolastModifiedDate}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker2" />
						<div class="input-group-append" data-target="#datetimepicker2"
							data-toggle="datetimepicker"></div>
					</div>
				</div>
			</div>
	
		

			<div class=" col-md-3 nextprvItems">
				<label id="label-1"
					style="width: 80px; text-align: center; margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
						<ul class="pagination">
						<li id="btnFrst" title="Go To First" class="page-item "
							style="margin-right: 2px;"><a
							style="margin-left: 3px; width: 53px; height: 40px" id="btnFirst"
							href="#" class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous" class="page-item "
							style="margin-right: 2px;"><a
							style="width: 53px; height: 40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next" class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px; margin-right: 2px; height: 40px"
							id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item "
							style="margin-right: 2px;"><a
							style="width: 53px; height: 40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>
					</ul>
				</nav>
			</div>

	
			
			
			
			
		</div>
		
		<div class="row">
		
		<div class="col-md-6">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker2"
						data-target-input="nearest">
						<span class="input-group-text" style="width: auto;">Completion
							 Date</span> <input type="text" id="wocompletiondate" readonly
							value="${wocompletionDate}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker2" />
						<div class="input-group-append" data-target="#datetimepicker2"
							data-toggle="datetimepicker"></div>
					</div>
				</div>
			</div>
			
			
					<div class="col-md-3" style="text-align: right; margin-left:370px">
				<div class="btn-group pull-right">

					<div class="glyph">

						<button type="button" id="Fview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="Form View"
							style="background: #da6815;">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-light"
							data-toggle="tooltip"
							onclick='window.location.href = "${pageContext.request.contextPath}/PatchingWorkOrderTaskListView"'
							data-placement="top" title="List View">
							<i class="fa fa-bars"></i>
						</button>
						
						
					
					</div>

				</div>
			</div>
			
			
			
			</div>
		<p></p>



		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: -20px;">
								
					<li class="tab" class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">INFORMATION</a></li>
			       	<li class="nav-item ml-auto">
					
					
					<button type="button" 
						onclick='workOrderModel()'
							class="btn btn-primary BtnActive">
							<i class="fa fa-plus"></i> Create New Work Order 
						</button>
						
						<button type="button" id="NewWO"
							class="btn btn-primary BtnActive" onclick="newTask()">
							<i class="fa fa-plus"></i> Add
						</button>

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


	<div class="container-fluid">
	
		 <div class="tab-content" id="custom-tabs-one-tabContent">
			
	
	<br>

    <!-- WO TASK ID / TASK TYPE -->
    
    
    
    
    
    
      <div class="row" style="margin-left:10px;">
   

    <div class="col-md-6">

        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;"> Work Order ID</span>
                <input type="text" id="taskPatchingId" value="${woPatchingId}" class="form-control text-input"/>
            </div>
        </div>
 
    </div>
    
     <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                   <div class="input-group-text" style="margin-right: 20px;">
										<input style="margin-left: 10px; margin-right: 10px"
											type="checkbox" checked="true" id="workOrderCheck" > <span style="color:green; width:100%" >Use same Work Order When Creating New Task </span>
									</div>
     </div>
        </div>
    </div>
</div>
       
   <div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Patching Note</span>
             <textarea id="patchingNotes"
          class="form-control text-input"
          readonly>${patchingOrderNote}</textarea>  </div>
        </div>
    </div>

    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Task Type</span>
                <select id="taskType" value="${woTaskType}" class="form-control text-input">
                    <option value="" disabled selected>-- Select Task Type --</option>
                    <option value="Add" ${woTaskType == 'Add' ? 'selected' : ''}>Add</option>
                    <option value="Remove" ${woTaskType == 'Remove' ? 'selected' : ''}>Remove</option>
                </select>
            </div>
        </div>
    </div>
</div>

<!-- LAST MODIFIED / COMPLETION -->
<!-- Add these rows for dates -->




<!-- DB ID / DB PORT -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">DB ID</span>
                <input type="text" id="dbId" value="${woDbId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">DB Port ID</span>
                <input type="text" id="dbPortId" value="${woDbPortId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- ROW COL INDEX / ROW NUMBER -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Row Col Index</span>
                <input type="number" id="rowColIndex" value="${woRowColIndex}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Row Number</span>
                <input type="number" id="rowNumber" value="${woRowNumber}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- COLUMN NUMBER / NEAR MODULE -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Column Number</span>
                <input type="number" id="columnNumber" value="${woColumnNumber}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Near Module</span>
                <input type="text" id="nearModule" value="${woNearModule}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- NEAR PORT / NEAR PATCH -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Near Port Num</span>
                <input type="text" id="nearPortNum" value="${woNearPortNum}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Near Patch Type</span>
                <input type="text" id="nearPatchType" value="${woNearPatchType}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Far Near Kit Serial Num</span>
                <input type="text" id="farNearKitSerialNum" value="${woFarNearKitSerialNum}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Far Near Module</span>
                <input type="text" id="farNearModule" value="${woFarNearModule}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Far Near Port Num</span>
                <input type="text" id="farNearPortNum" value="${woFarNearPortNum}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Location Type</span>
                <input type="text" id="fpLocationType" value="${woFpLocationType}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- FP LOCATION TYPE / LOCATION ID -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Location ID</span>
                <input type="text" id="fpLocationId" value="${woFpLocationId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Location Name</span>
                <input type="text" id="fpLocationName" value="${woFpLocationName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- FP LOCATION NAME / LOCATION -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Location</span>
                <input type="text" id="fpLocation" value="${woFpLocation}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Equipment Type</span>
                <input type="text" id="fpEquipmentType" value="${woFpEquipmentType}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- FP EQUIPMENT TYPE / EQUIPMENT -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Equipment</span>
                <input type="text" id="fpEquipment" value="${woFpEquipment}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Equipment ID</span>
                <input type="text" id="fpEquipmentId" value="${woFpEquipmentId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- FP EQUIPMENT ID / NAME -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Equipment Name</span>
                <input type="text" id="fpEquipmentName" value="${woFpEquipmentName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Address</span>
                <input type="text" id="fpAddress" value="${woFpAddress}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- ADDRESS / TUBE NB -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Tube NB</span>
                <input type="text" id="fpTubeNb" value="${woFpTubeNb}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Tube Color</span>
                <select id="fpTubeColor" class="form-control">
                    <option value="" style="background-color: white; color: black;">Select an Option</option>
                    <option value="blue" ${woFpTubeColor == 'blue' ? 'selected' : ''}>blue</option>
                    <option value="orange" ${woFpTubeColor == 'orange' ? 'selected' : ''}>orange</option>
                    <option value="green" ${woFpTubeColor == 'green' ? 'selected' : ''}>green</option>
                    <option value="brown" ${woFpTubeColor == 'brown' ? 'selected' : ''}>brown</option>
                    <option value="gray" ${woFpTubeColor == 'gray' ? 'selected' : ''}>gray</option>
                    <option value="white" ${woFpTubeColor == 'white' ? 'selected' : ''}>white</option>
                    <option value="red" ${woFpTubeColor == 'red' ? 'selected' : ''}>red</option>
                    <option value="black" ${woFpTubeColor == 'black' ? 'selected' : ''}>black</option>
                    <option value="yellow" ${woFpTubeColor == 'yellow' ? 'selected' : ''}>yellow</option>
                    <option value="violet" ${woFpTubeColor == 'violet' ? 'selected' : ''}>violet</option>
                    <option value="pink" ${woFpTubeColor == 'pink' ? 'selected' : ''}>pink</option>
                    <option value="aqua" ${woFpTubeColor == 'aqua' ? 'selected' : ''}>aqua</option>
                </select>
            </div>
        </div>
    </div>
</div>

<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Tube ID</span>
                <input type="text" id="fpTubeId" value="${woFpTubeId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Tube Name</span>
                <input type="text" id="fpTubeName" value="${woFpTubeName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- STRAND NB / STRAND COLOR -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Strand NB</span>
                <input type="text" id="fpStrandNb" value="${woFpStrandNb}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Strand Color</span>
                <select id="fpStrandColor" class="form-control">
                    <option value="" style="background-color: white; color: black;">Select an Option</option>
                    <option value="blue" ${woFpStrandColor == 'blue' ? 'selected' : ''}>blue</option>
                    <option value="orange" ${woFpStrandColor == 'orange' ? 'selected' : ''}>orange</option>
                    <option value="green" ${woFpStrandColor == 'green' ? 'selected' : ''}>green</option>
                    <option value="brown" ${woFpStrandColor == 'brown' ? 'selected' : ''}>brown</option>
                    <option value="gray" ${woFpStrandColor == 'gray' ? 'selected' : ''}>gray</option>
                    <option value="white" ${woFpStrandColor == 'white' ? 'selected' : ''}>white</option>
                    <option value="red" ${woFpStrandColor == 'red' ? 'selected' : ''}>red</option>
                    <option value="black" ${woFpStrandColor == 'black' ? 'selected' : ''}>black</option>
                    <option value="yellow" ${woFpStrandColor == 'yellow' ? 'selected' : ''}>yellow</option>
                    <option value="violet" ${woFpStrandColor == 'violet' ? 'selected' : ''}>violet</option>
                    <option value="pink" ${woFpStrandColor == 'pink' ? 'selected' : ''}>pink</option>
                    <option value="aqua" ${woFpStrandColor == 'aqua' ? 'selected' : ''}>aqua</option>
                </select>
            </div>
        </div>
    </div>
</div>

<!-- STRAND ID / STRAND NAME -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Strand ID</span>
                <input type="text" id="fpStrandId" value="${woFpStrandId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Strand Name</span>
                <input type="text" id="fpStrandName" value="${woFpStrandName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- FIBER ID / FIBER NAME -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Fiber ID</span>
                <input type="text" id="fpFiberId" value="${woFpFiberId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Fiber Name</span>
                <input type="text" id="fpFiberName" value="${woFpFiberName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- KIT SERIAL / MODULE -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Kit Serial Num</span>
                <input type="text" id="fpKitSerialNum" value="${woFpKitSerialNum}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Module</span>
                <input type="text" id="fpModule" value="${woFpModule}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- MODULE / PORT -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Port Num</span>
                <input type="text" id="fpPortNum" value="${woFpPortNum}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Junction ID</span>
                <input type="text" id="fpJunctionId" value="${woFpJunctionId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- JUNCTION NAME -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">FP Junction Name</span>
                <input type="text" id="fpJunctionName" value="${woFpJunctionName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Location Type</span>
                <input type="text" id="bpLocationType" value="${woBpLocationType}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP LOCATION ID / NAME -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Location ID</span>
                <input type="text" id="bpLocationId" value="${woBpLocationId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Location Name</span>
                <input type="text" id="bpLocationName" value="${woBpLocationName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP LOCATION / EQUIPMENT TYPE -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Location</span>
                <input type="text" id="bpLocation" value="${woBpLocation}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Equipment Type</span>
                <input type="text" id="bpEquipmentType" value="${woBpEquipmentType}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP EQUIPMENT / EQUIPMENT ID -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Equipment</span>
                <input type="text" id="bpEquipment" value="${woBpEquipment}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Equipment ID</span>
                <input type="text" id="bpEquipmentId" value="${woBpEquipmentId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP EQUIPMENT NAME / STRAND NB -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Equipment Name</span>
                <input type="text" id="bpEquipmentName" value="${woBpEquipmentName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Strand NB</span>
                <input type="text" id="bpStrandNb" value="${woBpStrandNb}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Strand Color </span>
                <select id="bpStrandColor" class="form-control">
                    <option value="" style="background-color: white; color: black;">Select an Option</option>
                    <option value="blue" ${woBpStrandColor == 'blue' ? 'selected' : ''}>blue</option>
                    <option value="orange" ${woBpStrandColor == 'orange' ? 'selected' : ''}>orange</option>
                    <option value="green" ${woBpStrandColor == 'green' ? 'selected' : ''}>green</option>
                    <option value="brown" ${woBpStrandColor == 'brown' ? 'selected' : ''}>brown</option>
                    <option value="gray" ${woBpStrandColor == 'gray' ? 'selected' : ''}>gray</option>
                    <option value="white" ${woBpStrandColor == 'white' ? 'selected' : ''}>white</option>
                    <option value="red" ${woBpStrandColor == 'red' ? 'selected' : ''}>red</option>
                    <option value="black" ${woBpStrandColor == 'black' ? 'selected' : ''}>black</option>
                    <option value="yellow" ${woBpStrandColor == 'yellow' ? 'selected' : ''}>yellow</option>
                    <option value="violet" ${woBpStrandColor == 'violet' ? 'selected' : ''}>violet</option>
                    <option value="pink" ${woBpStrandColor == 'pink' ? 'selected' : ''}>pink</option>
                    <option value="aqua" ${woBpStrandColor == 'aqua' ? 'selected' : ''}>aqua</option>
                </select>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Strand ID</span>
                <input type="text" id="bpStrandId" value="${woBpStrandId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>




<!-- BP STRAND NAME / TUBE NB -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Strand Name</span>
                <input type="text" id="bpStrandName" value="${woBpStrandName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Tube NB</span>
                <input type="text" id="bpTubeNb" value="${woBpTubeNb}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP TUBE COLOR / TUBE ID -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Tube Color</span>
                <select id="bpTubeColor" class="form-control">
                    <option value="" style="background-color: white; color: black;">Select an Option</option>
                    <option value="blue" ${woBpTubeColor == 'blue' ? 'selected' : ''}>blue</option>
                    <option value="orange" ${woBpTubeColor == 'orange' ? 'selected' : ''}>orange</option>
                    <option value="green" ${woBpTubeColor == 'green' ? 'selected' : ''}>green</option>
                    <option value="brown" ${woBpTubeColor == 'brown' ? 'selected' : ''}>brown</option>
                    <option value="gray" ${woBpTubeColor == 'gray' ? 'selected' : ''}>gray</option>
                    <option value="white" ${woBpTubeColor == 'white' ? 'selected' : ''}>white</option>
                    <option value="red" ${woBpTubeColor == 'red' ? 'selected' : ''}>red</option>
                    <option value="black" ${woBpTubeColor == 'black' ? 'selected' : ''}>black</option>
                    <option value="yellow" ${woBpTubeColor == 'yellow' ? 'selected' : ''}>yellow</option>
                    <option value="violet" ${woBpTubeColor == 'violet' ? 'selected' : ''}>violet</option>
                    <option value="pink" ${woBpTubeColor == 'pink' ? 'selected' : ''}>pink</option>
                    <option value="aqua" ${woBpTubeColor == 'aqua' ? 'selected' : ''}>aqua</option>
                </select>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Tube ID</span>
                <input type="text" id="bpTubeId" value="${woBpTubeId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP TUBE NAME / FIBER ID -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Tube Name</span>
                <input type="text" id="bpTubeName" value="${woBpTubeName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Fiber ID</span>
                <input type="text" id="bpFiberId" value="${woBpFiberId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP FIBER NAME / JUNCTION ID -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Fiber Name</span>
                <input type="text" id="bpFiberName" value="${woBpFiberName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Junction ID</span>
                <input type="text" id="bpJunctionId" value="${woBpJunctionId}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP JUNCTION NAME / STATUS -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Junction Name</span>
                <input type="text" id="bpJunctionName" value="${woBpJunctionName}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Status</span>
                <input type="text" id="bpStatus" value="${woBpStatus}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BP ADDRESS / BACK MODULE -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">BP Address</span>
                <input type="text" id="bpAddress" value="${woBpAddress}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Back Module</span>
                <input type="text" id="backModule" value="${woBackModule}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>

<!-- BACK KIT SERIAL / PORT NUM -->
<div class="row" style="margin-left:10px;">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Back Kit Serial Num</span>
                <input type="text" id="backKitSerialNum" value="${woBackKitSerialNum}" class="form-control text-input"/>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="color:green;">Back Port Num</span>
                <input type="text" id="backPortNum" value="${woBackPortNum}" class="form-control text-input"/>
            </div>
        </div>
    </div>
</div>



    

	
	
	
	
	
	
	
		</div>
	</div>
	<!-- Projects Model -->
	<div class="container">
		<div id="workOrderModel" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="workOrderHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Work Order</h5>
						<div style="float: right;">
							<button id="saveWorkOrder" onclick= "saveWorkOrder()" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"> <i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTabDb" role="tablist"
							style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="workOrderInfo-tab" style="color: gold;" data-toggle="tab" href="#workOrderInfo" role="tab" aria-controls="workOrderInfo" aria-selected="true">Information </a></li>
							</ul>
						<div class="tab-content"><p></p>
						  <div class="tab-pane active" id="workOrderInfo" role="tabpanel" 	aria-labelledby="workOrderInfo-tab">
					
						</div>
				
					
	 <div class="row" >				
 <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
                   <div class="input-group-text" style="margin-right: 20px;">
										<input style="margin-left: 10px; margin-right: 10px"
											type="checkbox" checked="true" id="workOrderTaskCheck" > <span style="color:green; width:100%" >Use this Work Order Id for the Current Task</span>
									</div>
     </div>
        </div>
    </div>				
	</div>
 <div class="row" >
 
 
 
 
        <div class="col-md-6">
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
                        <option value="Canceled">Canceled</option>
                         <option value="Completed">Completed</option>
                        <option value="Closed">Closed</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <!-- ASSIGNED TO -->
    <div class="row" >
        <div class="col-md-12">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="color:green;">Assigned To</span>
                    <input type="text" id="assignedTo" class="form-control text-input" />
                </div>
            </div>
        </div>
       
    </div>

    <!-- PLANNED / ACTUAL EXECUTION -->
    <div class="row" >
        <div class="col-md-6">
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
    <div class="row" >
        <div class="col-md-6">
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
<div class="row" >
    <div class="col-md-12">
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
							
							
							
							</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>



</body>

<script type='text/javascript'>
document.addEventListener("DOMContentLoaded", function () {

    if ( '${woStatus}' === "Completed") {
        disableTaskForm();
    } else {
        enableTaskForm();
    }

});

 
 
 
			$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
   
			

			$("#wostat").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});


		
		/////validation/////
			 $(function(){
		
				if ('${docStatus}' != 'addNew') {
	
					
				}
					
			});
				
	$(document).ready(function() {

		if('${SelectedIndex}' != 'addNew'){
			
			var SelectedIndex = ${SelectedIndex};
			if('${workOrderCount}' != 'addNew'){
				
				var workOrderCount = ${workOrderCount};
				if(($("#wocode").val()) != "" && ($("#wocode").val()) != null){
					if(SelectedIndex === workOrderCount){
						document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";
						$("#btnNexta").hasClass("disabled");
					}else{
						console.log("else condition 1");
						if(!$("#btnNexta").hasClass("disabled")){
							
							$("#btnNext").click(function(){
								console.log("clicked");
								var param ="${pageContext.request.contextPath}/PatchingWorkOrderTaskFormView?workOrdTaskId="+$("#wocode").val()+"&NavAction=1";
								window.location.href =param;
							});
						}
						if(!$("#btnLst").hasClass("disabled")){
		        			$("#btnLst").click(function(){
		        				var param ="${pageContext.request.contextPath}/PatchingWorkOrderTaskFormView?workOrdTaskId="+$("#wocode").val()+"&NavAction=4";
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
								var param ="${pageContext.request.contextPath}/PatchingWorkOrderTaskFormView?workOrdTaskId="+$("#wocode").val()+"&NavAction=0";
								 window.location.href =param;
							 });
						}
						$("#btnFrst").click(function(){
		        			if(!$("#btnFrst").hasClass("disabled")){
								var param ="${pageContext.request.contextPath}/PatchingWorkOrderTaskFormView?workOrdTaskId="+$("#wocode").val()+"&NavAction=3";
		        				window.location.href =param;		
		        			}
		        		});

					}
					
				}else{
						console.log("else condition of the if work order is not null");
					}
			}
			$("#label-1").text((SelectedIndex)+"/"+workOrderCount);
		}//end of if('${SelectedIndex}' != "addNew")
		else{
			$("#formStatus").text("New");
			$('.dot').css({"background-color" : "orange"});
			$(".nextprvItems").addClass("hide-row ");
	 		 $(".pad").removeClass("hide-row ");
			}

		

    

//Save rows in table

 $("#saveButton").click(  function() {
	 var token = $('input[name="csrfToken"]').attr('value');			 
	    // BASIC TASK INFO
	       var woTaskId = document.getElementById("wocode").value;
    var taskStatus = document.getElementById("wostat").value;   // <-- was "taskStatus"
    var taskType = document.getElementById("taskType").value;
    var taskPatchingId = document.getElementById("taskPatchingId").value;

    // DATES
    var creationDate = document.getElementById("wocreatedate").value;
    var lastModifiedDate = document.getElementById("wolstmodifdate").value;
    var completionDate = document.getElementById("wocompletiondate").value;   // <-- renamed to match (you should update the HTML id)

    // DB INFO
    var dbId = document.getElementById("dbId").value;
    var dbPortId = document.getElementById("dbPortId").value;

    // POSITIONING
    var rowColIndex = document.getElementById("rowColIndex").value;
    var rowNumber = document.getElementById("rowNumber").value;
    var columnNumber = document.getElementById("columnNumber").value;

    // NEAR SIDE
    var nearModule = document.getElementById("nearModule").value;
    var nearPortNum = document.getElementById("nearPortNum").value;
    var nearPatchType = document.getElementById("nearPatchType").value;

    // FAR NEAR
    var farNearKitSerialNum = document.getElementById("farNearKitSerialNum").value;
    var farNearModule = document.getElementById("farNearModule").value;
    var farNearPortNum = document.getElementById("farNearPortNum").value;

    // FP LOCATION
    var fpLocationType = document.getElementById("fpLocationType").value;
    var fpLocationId = document.getElementById("fpLocationId").value;
    var fpLocationName = document.getElementById("fpLocationName").value;
    var fpLocation = document.getElementById("fpLocation").value;

    // FP EQUIPMENT
    var fpEquipmentType = document.getElementById("fpEquipmentType").value;
    var fpEquipment = document.getElementById("fpEquipment").value;
    var fpEquipmentId = document.getElementById("fpEquipmentId").value;
    var fpEquipmentName = document.getElementById("fpEquipmentName").value;

    // FP ADDRESS / TUBE
    var fpAddress = document.getElementById("fpAddress").value;
    var fpTubeNb = document.getElementById("fpTubeNb").value;

    // STRAND / TUBE COLORS
    var fpStrandColor = document.getElementById("fpStrandColor").value;
    var fpTubeColor = document.getElementById("fpTubeColor").value;

    // STRAND / TUBE IDENTIFIERS
    var fpStrandName = document.getElementById("fpStrandName").value;
    var fpStrandNb = document.getElementById("fpStrandNb").value;
    var fpStrandId = document.getElementById("fpStrandId").value;

    var fpTubeId = document.getElementById("fpTubeId").value;
    var fpTubeName = document.getElementById("fpTubeName").value;

    // FIBER / KIT
    var fpFiberId = document.getElementById("fpFiberId").value;
    var fpFiberName = document.getElementById("fpFiberName").value;
    var fpKitSerialNum = document.getElementById("fpKitSerialNum").value;

    // MODULE / PORT
    var fpModule = document.getElementById("fpModule").value;
    var fpPortNum = document.getElementById("fpPortNum").value;

    // JUNCTION
    var fpJunctionId = document.getElementById("fpJunctionId").value;
    var fpJunctionName = document.getElementById("fpJunctionName").value;


    // BP LOCATION
    var bpLocationType = document.getElementById("bpLocationType").value;
    var bpLocationId = document.getElementById("bpLocationId").value;
    var bpLocationName = document.getElementById("bpLocationName").value;
    var bpLocation = document.getElementById("bpLocation").value;

    // BP EQUIPMENT
    var bpEquipmentType = document.getElementById("bpEquipmentType").value;
    var bpEquipment = document.getElementById("bpEquipment").value;
    var bpEquipmentId = document.getElementById("bpEquipmentId").value;
    var bpEquipmentName = document.getElementById("bpEquipmentName").value;

    // BP ADDRESS / STATUS
    var bpAddress = document.getElementById("bpAddress").value;
    var bpStatus = document.getElementById("bpStatus").value;

    // BP STRAND
    var bpStrandColor = document.getElementById("bpStrandColor").value;
    var bpStrandNb = document.getElementById("bpStrandNb").value;
    var bpStrandId = document.getElementById("bpStrandId").value;
    var bpStrandName = document.getElementById("bpStrandName").value;

    // BP TUBE
    var bpTubeColor = document.getElementById("bpTubeColor").value;
    var bpTubeNb = document.getElementById("bpTubeNb").value;
    var bpTubeId = document.getElementById("bpTubeId").value;
    var bpTubeName = document.getElementById("bpTubeName").value;

    // BP FIBER
    var bpFiberId = document.getElementById("bpFiberId").value;
    var bpFiberName = document.getElementById("bpFiberName").value;

    // BP JUNCTION
    var bpJunctionId = document.getElementById("bpJunctionId").value;
    var bpJunctionName = document.getElementById("bpJunctionName").value;

    // BACK
    var backModule = document.getElementById("backModule").value;
    var backKitSerialNum = document.getElementById("backKitSerialNum").value;
    var backPortNum = document.getElementById("backPortNum").value;
		
		if (!rowColIndex || !rowNumber || !columnNumber) {
		     alert(" RowColIndex, Row Number, and Column Number are required!");
		     return;  // Stop saving
		 }
		$.ajax({
		    type: "POST",
		    headers: {
		        'X-CSRFToken': token
		    },
		    url: getContext() + '/saveTaskOrder',
		    async: false,

		    data: {
		        "woTaskId": woTaskId,
		        "taskStatus": taskStatus,
		        "taskType": taskType,
				"taskPatchingId" : taskPatchingId,

		        "creationDate": creationDate,
		        "lastModifiedDate": lastModifiedDate,
		        "completionDate": completionDate,

				"farNearKitSerialNum": farNearKitSerialNum,
				 "farNearModule": farNearModule,
				  "farNearPortNum": farNearPortNum,

				  "fpStrandNb": fpStrandNb,
				  "fpStrandId": fpStrandId,
				  "bpStrandNb": bpStrandNb,
				  "bpTubeNb": bpTubeNb,
				
		        "dbId": dbId,
		        "dbPortId": dbPortId,

		        "rowColIndex": rowColIndex,
		        "rowNumber": rowNumber,
		        "columnNumber": columnNumber,

		        "nearModule": nearModule,
		        "nearPortNum": nearPortNum,
		        "nearPatchType": nearPatchType,

		        "fpLocationType": fpLocationType,
		        "fpLocationId": fpLocationId,
		        "fpLocationName": fpLocationName,
		        "fpLocation": fpLocation,

		        "fpEquipmentType": fpEquipmentType,
		        "fpEquipment": fpEquipment,
		        "fpEquipmentId": fpEquipmentId,
		        "fpEquipmentName": fpEquipmentName,

		        "fpAddress": fpAddress,
		        "fpTubeNb": fpTubeNb,

		        "fpStrandColor": fpStrandColor,
		        "fpTubeColor": fpTubeColor,

		        "fpStrandName": fpStrandName,
		        "fpTubeId": fpTubeId,
		        "fpTubeName": fpTubeName,
		        "fpFiberId": fpFiberId,

		        "fpFiberName": fpFiberName,
		        "fpKitSerialNum": fpKitSerialNum,

		        "fpModule": fpModule,
		        "fpPortNum": fpPortNum,

		        "fpJunctionId": fpJunctionId,
		        "fpJunctionName": fpJunctionName, 
				"bpLocationType": bpLocationType,
				"bpLocationId": bpLocationId,
				"bpLocationName": bpLocationName,
				"bpLocation": bpLocation,

				"bpEquipmentType": bpEquipmentType,
				"bpEquipment": bpEquipment,
				"bpEquipmentId": bpEquipmentId,
				"bpEquipmentName": bpEquipmentName,

				"bpAddress": bpAddress,
				"bpStatus": bpStatus,

				"bpStrandColor": bpStrandColor,
				"bpTubeColor": bpTubeColor,

				"bpStrandId": bpStrandId,
				"bpStrandName": bpStrandName,

				"bpTubeId": bpTubeId,
				"bpTubeName": bpTubeName,

				"bpFiberId": bpFiberId,
				"bpFiberName": bpFiberName,

				"bpJunctionId": bpJunctionId,
				"bpJunctionName": bpJunctionName,

				// ================= BACK =================
				"backModule": backModule,
				"backKitSerialNum": backKitSerialNum,
				"backPortNum": backPortNum,
				"formview" :"1"
		    },

		    dataType: "json",

			success: function (data) {
			    alert("Saved done");
			    console.log(data);
			    var param = "${pageContext.request.contextPath}/PatchingWorkOrderTaskFormView?workOrdTaskId="+ (data.woTaskId)+ "&NavAction=2";
				window.location.href =param;
			
			   
			}
,

		    error: function (result) {
		        alert("Error");
		    }
		});
			
 })


$("#deleteButton").click(  function() {
			 var woId = document.getElementById("wocode").value;
			 
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/deleteWOTask",
					dataType : "json",
					data : {
						"workOrdId" : woId
					},
					success : function(data) {
						location.replace("${pageContext.request.contextPath}/PatchingWorkOrderTaskListView");
					},
					error : function(error) {
					}
				});
				

// to delete all the src items related to this workorder
				 
				 

				 
				
})

	$("#taskPatchingId").autocomplete({
    source: debounce(function (request, response) {

        var searchValue = $("#taskPatchingId").val();

        console.log("Searching Patching ID:", searchValue);

        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + "/GetAllWorkPatchingOrders",
            data: {
                "workOrder": searchValue
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.allWorkOrders);
                }
            },
            error: function () {
                alert("Error");
            }
        });

    }, 900),

    minLength: 0,
    maxShowItems: 40,
    scroll: true,

    select: function (event, ui) {

        this.value = (ui.item ? ui.item[0] : '');
        $("#patchingNotes").val(ui.item[1]);

        return false;
    }

}).data("ui-autocomplete")._renderItem = function (ul, item) {

    return $('<li class="each"></li>')
        .data("ui-autocomplete-item", item)
        .append(
            '<div class="acItem">' +
            '<span class="name" style="font-weight:bold">' + item[0] + '</span><br>' +
            '<span class="desc">' + item[1] + '</span>' +
            '</div>'
        )
        .appendTo(ul);
};

$("#taskPatchingId").focus(function () {
    if (this.value == "") {
        $(this).autocomplete("search", "");
    }
});
 
	$("#selectnav").autocomplete({
		showHeader: true,
		source: function(request, response) {
        	
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/GetAllWorkOrdersTask',
                data: {
	                   
						"workOrder" : request.term,
						
				 },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.allWorkOrders);
                        console.log(data.allWorkOrders);
                    }
                },
                error: function(result) {
                    alert("Error");
                }
            });
        },minLength:0, maxShowItems: 40, scroll:true,
        select: function(event, ui) {
			this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
			var param = "${pageContext.request.contextPath}/PatchingWorkOrderTaskFormView?workOrdTaskId="+ (ui.item[0])+ "&NavAction=2";
			window.location.href =param;
				return false;
			}
	}).autocomplete("instance")._renderItem = function(ul, item) {
        return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] +', '+ item[2] + "</span><br><span class='desc'>" +
            item[3] +', '+ item[4] + "</span></div>")
        .appendTo(ul);
	};
	$("#selectnav").focus(function(){
		
       	if (this.value == ""){
           	$(this).autocomplete("search");
       	}						
	});



	$("#fpStrandId").autocomplete({
		source: debounce(function(request, response, event, ui) {

			let sId = $("#fpStrandId").val();
			console.log("strand id:", sId);

			$.ajax({
				type: "GET",
				url: getContext() + '/SearchMappingStrand',
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: {
					searchId: sId
				},
				success: function(data) {
					if (data != null) {
						console.log(data.glist);
						response(data.glist);
					}
				},
				error: function() {
					alert("Error");
				}
			});

		}, 900),

		minLength: 0,
		maxShowItems: 4,
		scroll: true,

		select: function(event, ui) {

			if (!ui.item) return false;

			this.value = ui.item[0];

			$("#fpStrandName").val(ui.item[1]);
			$("#fpTubeId").val(ui.item[2]);
			$("#fpFiberId").val(ui.item[3]);
			$("#fpTubeName").val(ui.item[5]);
			$("#fpFiberName").val(ui.item[4]);
			$("#fpStrandNb").val(ui.item[6]);
			$("#fpTubeNb").val(ui.item[8]);

			if (ui.item[7] !== "" && ui.item[7] != null) {

				$("#fpTubeColor").val(ui.item[9]);
				$("#fpStrandColor").val(ui.item[7]);

				tubeStrandSetColor("fpStrandColor", "fpStrandNb");
				tubeStrandSetColor("fpTubeColor", "fpTubeNb");
			}

			return false;
		}
	})
		.data("ui-autocomplete")._renderItem = function(ul, item) {

			return $("<li class='each'></li>")
				.data("ui-autocomplete-item", item)
				.append(
					"<div class='acItem'>" +
					"<span class='name' style='font-weight:bold'>" + item[0] + "</span><br>" +
					"<span class='desc'>" + item[1] + ", " + item[4] + ", " + item[5] + "</span>" +
					"</div>"
				)
				.appendTo(ul);
		};

	$("#fpStrandId").focus(function() {
		if (this.value === "") {
			$(this).autocomplete("search");
		}
	});


	$("#fpStrandName").autocomplete({
		source: debounce(function(request, response) {

			let sName = $("#fpStrandName").val();

			$.ajax({
				type: "GET",
				url: getContext() + '/SearchMappingStrand',
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: {
					"searchId": sName
				},
				success: function(data) {
					if (data != null) {
						response(data.glist);
					}
				},
				error: function() {
					alert("Error");
				}
			});

		}, 900),

		minLength: 0,
		maxShowItems: 4,
		scroll: true,

		select: function(event, ui) {

			if (!ui.item) return false;

			this.value = ui.item[1];

			$("#fpStrandId").val(ui.item[0]);
			$("#fpTubeId").val(ui.item[2]);
			$("#fpFiberId").val(ui.item[3]);
			$("#fpTubeName").val(ui.item[5]);
			$("#fpFiberName").val(ui.item[4]);
			$("#fpStrandNb").val(ui.item[6]);
			$("#fpTubeNb").val(ui.item[8]);

			// Correct condition AND correct ID names
			if (ui.item[7] !== "" && ui.item[7] != null) {

				$("#fpTubeColor").val(ui.item[9]);
				$("#fpStrandColor").val(ui.item[7]); // FIXED CAPITAL LETTER

				// Set colors correctly
				tubeStrandSetColor("fpStrandColor", "fpStrandNb");
				tubeStrandSetColor("fpTubeColor", "fpTubeNb");
			}

			return false;
		}
	})
		.data("ui-autocomplete")._renderItem = function(ul, item) {

			return $('<li class="each"></li>')
				.data("ui-autocomplete-item", item)
				.append(
					'<div class="acItem">' +
					'<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
					'<span class="desc">' + item[0] + ', ' + item[4] + ', ' + item[5] + '</span>' +
					'</div>'
				)
				.appendTo(ul);
		};

	// FIXED selector (lowercase p)
	$("#fpStrandName").focus(function() {
		if (this.value === "") {
			$(this).autocomplete("search");
		}
	});







	$("#bpStrandId").autocomplete({
		source: debounce(function(request, response, event, ui) {

			let sId = $("#bpStrandId").val();
			console.log("strand id:", sId);

			$.ajax({
				type: "GET",
				url: getContext() + '/SearchMappingStrand',
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: {
					searchId: sId
				},
				success: function(data) {
					if (data != null) {
						console.log(data.glist);
						response(data.glist);
					}
				},
				error: function() {
					alert("Error");
				}
			});

		}, 900),

		minLength: 0,
		maxShowItems: 4,
		scroll: true,

		select: function(event, ui) {

			if (!ui.item) return false;

			this.value = ui.item[0];

			$("#bpStrandName").val(ui.item[1]);
			$("#bpTubeId").val(ui.item[2]);
			$("#bpFiberId").val(ui.item[3]);
			$("#bpTubeName").val(ui.item[5]);
			$("#bpFiberName").val(ui.item[4]);
			$("#bpStrandNb").val(ui.item[6]);
			$("#bpTubeNb").val(ui.item[8]);

			if (ui.item[7] !== "" && ui.item[7] != null) {

				$("#bpTubeColor").val(ui.item[9]);
				$("#bpStrandColor").val(ui.item[7]);

				tubeStrandSetColor("bpStrandColor", "bpStrandNb");
				tubeStrandSetColor("bpTubeColor", "bpTubeNb");
			}

			return false;
		}
	})
		.data("ui-autocomplete")._renderItem = function(ul, item) {

			return $("<li class='each'></li>")
				.data("ui-autocomplete-item", item)
				.append(
					"<div class='acItem'>" +
					"<span class='name' style='font-weight:bold'>" + item[0] + "</span><br>" +
					"<span class='desc'>" + item[1] + ", " + item[4] + ", " + item[5] + "</span>" +
					"</div>"
				)
				.appendTo(ul);
		};

	$("#bpStrandId").focus(function() {
		if (this.value === "") {
			$(this).autocomplete("search");
		}
	});


	$("#bpStrandName").autocomplete({
		source: debounce(function(request, response) {

			let sName = $("#bpStrandName").val();

			$.ajax({
				type: "GET",
				url: getContext() + '/SearchMappingStrand',
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: {
					"searchId": sName
				},
				success: function(data) {
					if (data != null) {
						response(data.glist);
					}
				},
				error: function() {
					alert("Error");
				}
			});

		}, 900),

		minLength: 0,
		maxShowItems: 4,
		scroll: true,

		select: function(event, ui) {

			if (!ui.item) return false;

			this.value = ui.item[1];

			$("#bpStrandId").val(ui.item[0]);
			$("#bpTubeId").val(ui.item[2]);
			$("#bpFiberId").val(ui.item[3]);
			$("#bpTubeName").val(ui.item[5]);
			$("#bpFiberName").val(ui.item[4]);
			$("#bpStrandNb").val(ui.item[6]);
			$("#bpTubeNb").val(ui.item[8]);

			// Correct condition AND correct ID names
			if (ui.item[7] !== "" && ui.item[7] != null) {

				$("#bpTubeColor").val(ui.item[9]);
				$("#bpStrandColor").val(ui.item[7]); // FIXED CAPITAL LETTER

				// Set colors correctly
				tubeStrandSetColor("bpStrandColor", "bpStrandNb");
				tubeStrandSetColor("bpTubeColor", "bpTubeNb");
			}

			return false;
		}
	})
		.data("ui-autocomplete")._renderItem = function(ul, item) {

			return $('<li class="each"></li>')
				.data("ui-autocomplete-item", item)
				.append(
					'<div class="acItem">' +
					'<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
					'<span class="desc">' + item[0] + ', ' + item[4] + ', ' + item[5] + '</span>' +
					'</div>'
				)
				.appendTo(ul);
		};

	// FIXED selector (lowercase p)
	$("#bpStrandName").focus(function() {
		if (this.value === "") {
			$(this).autocomplete("search");
		}
	});




	$("#fpFiberId").autocomplete({
						source: debounce(function(request, response, event, ui) {
							console.log("fiber id");
							var fId = $("#fpFiberId").val();
							var cId = $("#fpFiberId").val();
							var sId = $("#fpStrandId").val();
							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fId;
							}
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
										console.log(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});
						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[0] : '');
							$("#fpFiberName").val(ui.item[1]);


							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[0] + '</span><br><span class="desc">' +
								item[1] + '</span></div>').appendTo(ul);
					};
					$("#fpFiberId").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});
					///////////////////////////////
					$("#fpFiberName").autocomplete({
						source: debounce(function(request, response, event, ui) {
							var fName = $("#fpFiberName").val();
							var cId = $("#fpTubeId").val();
							var sId = $("#fpStrandId").val();
							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fName;
							}
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",

								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});
						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {

							this.value = (ui.item ? ui.item[1] : '');
							$("#fpFiberId").val(ui.item[0]);


							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[1] + '</span><br><span class="desc">' +
								item[0] + '</span></div>').appendTo(ul);
					};
					$("#fpFiberName").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});




					$("#bpFiberId").autocomplete({
						source: debounce(function(request, response, event, ui) {
							console.log("fiber id");

							var fId = $("#bpFiberId").val();
							var cId = $("#bpTubeId").val();
							var sId = $("#bpStrandId").val();

							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fId;
							}

							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
										console.log(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900),
						minLength: 0,
						maxShowItems: 4,
						scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[0] : '');
							$("#bpFiberName").val(ui.item[1]);
							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>')
							.data("ui-autocomplete-item", item)
							.append(
								'<div class="acItem">' +
								'<span class="name" style="font-weight:bold">' + item[0] + '</span><br>' +
								'<span class="desc">' + item[1] + '</span>' +
								'</div>'
							)
							.appendTo(ul);
					};

					$("#bpFiberId").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});

					//////////////////////////////////////////

					$("#bpFiberName").autocomplete({
						source: debounce(function(request, response, event, ui) {

							var fName = $("#bpFiberName").val();
							var cId = $("#bpTubeId").val();
							var sId = $("#bpStrandId").val();

							if (sId != "") {
								searchId = sId;
							} else if (cId != "") {
								searchId = cId;
							} else {
								searchId = fName;
							}

							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchFiber',
								data: {
									"searchId": searchId
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.glist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900),
						minLength: 0,
						maxShowItems: 4,
						scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[1] : '');
							$("#bpFiberId").val(ui.item[0]);
							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>')
							.data("ui-autocomplete-item", item)
							.append(
								'<div class="acItem">' +
								'<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
								'<span class="desc">' + item[0] + '</span>' +
								'</div>'
							)
							.appendTo(ul);
					};

					$("#bpFiberName").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});







					$("#fpTubeId").autocomplete({
						source: debounce(function(request, response, event, ui) {
							var cName = $("#fpTubeId").val();
							var sId = $("#fpStrandId").val();
							if (sId != "") {
								searchId = sId;

								console.log("tube id");
							} else {
								searchId = cName;
								//url="SearchStrandTube";
							}
							console.log("sid " + searchId);
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",

								url: getContext() + '/SearchTube',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.clist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});
						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[0] : '');
							$("#fpFiberId").val(ui.item[2]);
							$("#fpTubeName").val(ui.item[1]);
							$("#fpFiberName").val(ui.item[3]);
							$("#fpTubeNb").val(ui.item[4]);

							if (ui.item[5] != "" || ui.item[5] != null) {
								$("#fpTubeColor").val(ui.item[5]);
								tubeStrandSetColor("fpTubeColor", "fpTubeNb");
							}


							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[0] + '</span><br><span class="desc">' +
								item[1] + ', ' + item[3] + '</span></div>').appendTo(ul);
					};
					$("#fpTubeId").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});

					$("#fpTubeName").autocomplete({
						source: debounce(function(request, response, event, ui) {
							var cName = $("#fpTubeName").val();
							var sId = $("#fpStrandId").val();
							if (sId != "") {
								searchId = sId;
								//  url="SearchStrandTube";
							} else {
								searchId = cName;
								// url="SearchStrandTube";
							}
							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",

								url: getContext() + '/SearchTube',
								data: {
									"searchId": searchId,
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.clist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900), minLength: 0, maxShowItems: 4, scroll: true,
						select: function(event, ui) {
							this.value = (ui.item ? ui.item[1] : '');

							$("#fpFiberId").val(ui.item[2]);
							$("#fpTubeId").val(ui.item[0]);
							$("#fpFiberName").val(ui.item[3]);
							$("#fpTubeNb").val(ui.item[4]);

							if (ui.item[5] != "" || ui.item[5] != null) {
								$("#fpTubeColor").val(ui.item[5]);
								tubeStrandSetColor("fpTubeColor", "fpTubeNb");
							}

							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {
						return $('<li class="each"></li>').data("ui-autocomplete-item", item)
							.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								item[1] + '</span><br><span class="desc">' +
								item[0] + ', ' + item[3] + '</span></div>').appendTo(ul);
					};
					$("#fpTubeName").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});



					$("#bpTubeId").autocomplete({
						source: debounce(function(request, response, event, ui) {

							var cName = $("#bpTubeId").val();
							var sId = $("#bpStrandId").val();

							if (sId != "") {
								searchId = sId;
								console.log("tube id");
							} else {
								searchId = cName;
							}

							console.log("sid " + searchId);

							$.ajax({
								type: "GET",
								contentType: "application/json; charset=utf-8",
								url: getContext() + '/SearchTube',
								data: {
									"searchId": searchId
								},
								dataType: "json",
								success: function(data) {
									if (data != null) {
										response(data.clist);
									}
								},
								error: function(result) {
									alert("Error");
								}
							});

						}, 900),
						minLength: 0,
						maxShowItems: 4,
						scroll: true,
						select: function(event, ui) {

							this.value = (ui.item ? ui.item[0] : '');

							$("#bpFiberId").val(ui.item[2]);
							$("#bpTubeName").val(ui.item[1]);
							$("#bpFiberName").val(ui.item[3]);
							$("#bpTubeNb").val(ui.item[4]);

							if (ui.item[5] != "" || ui.item[5] != null) {
								$("#bpTubeColor").val(ui.item[5]);
								tubeStrandSetColor("bpTubeColor", "bpTubeNb");
							}

							return false;
						}
					}).data("ui-autocomplete")._renderItem = function(ul, item) {

						return $('<li class="each"></li>')
							.data("ui-autocomplete-item", item)
							.append(
								'<div class="acItem">' +
								'<span class="name" style="font-weight:bold">' + item[0] + '</span><br>' +
								'<span class="desc">' + item[1] + ', ' + item[3] + '</span>' +
								'</div>'
							)
							.appendTo(ul);
					};

					$("#bpTubeId").focus(function() {
						if (this.value == "") {
							$(this).autocomplete("search");
						}
					});

					$("#bpTubeName").autocomplete({
					    source: debounce(function (request, response, event, ui) {

					        var cName = $("#bpTubeName").val();
					        var sId = $("#bpStrandId").val();
					        var searchId;

					        if (sId !== "") {
					            searchId = sId;
					        } else {
					            searchId = cName;
					        }

					        $.ajax({
					            type: "GET",
					            contentType: "application/json; charset=utf-8",
					            url: getContext() + '/SearchTube',
					            data: {
					                "searchId": searchId
					            },
					            dataType: "json",
					            success: function (data) {
					                if (data != null) {
					                    response(data.clist);
					                }
					            },
					            error: function (result) {
					                alert("Error");
					            }
					        });

					    }, 900),
					    minLength: 0,
					    maxShowItems: 4,
					    scroll: true,
					    select: function (event, ui) {

					        this.value = (ui.item ? ui.item[1] : '');

					        $("#bpFiberId").val(ui.item[2]);
					        $("#bpTubeId").val(ui.item[0]);
					        $("#bpFiberName").val(ui.item[3]);
					        $("#bpTubeNb").val(ui.item[4]);

					        // Better check: use && instead of || so it must be non-empty AND non-null
					        if (ui.item[5] !== "" && ui.item[5] != null) {
					            $("#bpTubeColor").val(ui.item[5]);
					            tubeStrandSetColor("bpTubeColor", "bpTubeNb");
					        }

					        return false;
					    }
					}).data("ui-autocomplete")._renderItem = function (ul, item) {

					    return $('<li class="each"></li>')
					        .data("ui-autocomplete-item", item)
					        .append(
					            '<div class="acItem">' +
					            '<span class="name" style="font-weight:bold">' + item[1] + '</span><br>' +
					            '<span class="desc">' + item[0] + ', ' + item[3] + '</span>' +
					            '</div>'
					        )
					        .appendTo(ul);
					};

					$("#bpTubeName").focus(function () {
					    if (this.value === "") {
					        $(this).autocomplete("search");
					    }
					});






					function debounce(fn, delay) {
					    var timer;
					    return function() {
					      var args = [].slice.call(arguments);
					      var context = this;
					      if (timer) {
					        window.clearTimeout(timer);
					      }
					      timer = window.setTimeout(function() {
					        fn.apply(context, args);
					      }, delay);
					    };
					  };




	 
			         


						function tubeStrandSetColor(colorID, numberID) {

						    if (document.getElementById(colorID).value == "blue") {
						        document.getElementById(colorID).style.backgroundColor = "blue";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "1";
						    }
						    else if (document.getElementById(colorID).value == "orange") {
						        document.getElementById(colorID).style.backgroundColor = "orange";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "2";
						    }
						    else if (document.getElementById(colorID).value == "green") {
						        document.getElementById(colorID).style.backgroundColor = "green";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "3";
						    }
						    else if (document.getElementById(colorID).value == "brown") {
						        document.getElementById(colorID).style.backgroundColor = "brown";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "4";
						    }
						    else if (document.getElementById(colorID).value == "gray") {
						        document.getElementById(colorID).style.backgroundColor = "gray";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "5";
						    }
						    else if (document.getElementById(colorID).value == "white") {
						        document.getElementById(colorID).style.backgroundColor = "white";
						        document.getElementById(colorID).style.color = "black";
						        document.getElementById(numberID).value = "6";
						    }
						    else if (document.getElementById(colorID).value == "red") {
						        document.getElementById(colorID).style.backgroundColor = "red";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "7";
						    }
						    else if (document.getElementById(colorID).value == "black") {
						        document.getElementById(colorID).style.backgroundColor = "black";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "8";
						    }
						    else if (document.getElementById(colorID).value == "yellow") {
						        document.getElementById(colorID).style.backgroundColor = "yellow";
						        document.getElementById(colorID).style.color = "black";
						        document.getElementById(numberID).value = "9";
						    }
						    else if (document.getElementById(colorID).value == "violet") {
						        document.getElementById(colorID).style.backgroundColor = "violet";
						        document.getElementById(colorID).style.color = "white";
						        document.getElementById(numberID).value = "10";
						    }
						    else if (document.getElementById(colorID).value == "pink") {
						        document.getElementById(colorID).style.backgroundColor = "pink";
						        document.getElementById(colorID).style.color = "black";
						        document.getElementById(numberID).value = "11";
						    }
						    else if (document.getElementById(colorID).value == "aqua") {
						        document.getElementById(colorID).style.backgroundColor = "aqua";
						        document.getElementById(colorID).style.color = "black";
						        document.getElementById(numberID).value = "12";
						    }
						}

						function strandTubeSetColor(strandTubeNumber, ID) {
						    if (strandTubeNumber == "1") {
						        document.getElementById(ID).value = "blue";
						        document.getElementById(ID).style.backgroundColor = "blue";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "2") {
						        document.getElementById(ID).value = "orange";
						        document.getElementById(ID).style.backgroundColor = "orange";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "3") {
						        document.getElementById(ID).value = "green";
						        document.getElementById(ID).style.backgroundColor = "green";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "4") {
						        document.getElementById(ID).value = "brown";
						        document.getElementById(ID).style.backgroundColor = "brown";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "5") {
						        document.getElementById(ID).value = "gray";
						        document.getElementById(ID).style.backgroundColor = "gray";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "6") {
						        document.getElementById(ID).value = "white";
						        document.getElementById(ID).style.backgroundColor = "white";
						        document.getElementById(ID).style.color = "black";
						    }
						    else if (strandTubeNumber == "7") {
						        document.getElementById(ID).value = "red";
						        document.getElementById(ID).style.backgroundColor = "red";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "8") {
						        document.getElementById(ID).value = "black";
						        document.getElementById(ID).style.backgroundColor = "black";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "9") {
						        document.getElementById(ID).value = "yellow";
						        document.getElementById(ID).style.backgroundColor = "yellow";
						        document.getElementById(ID).style.color = "black";
						    }
						    else if (strandTubeNumber == "10") {
						        document.getElementById(ID).value = "violet";
						        document.getElementById(ID).style.backgroundColor = "violet";
						        document.getElementById(ID).style.color = "white";
						    }
						    else if (strandTubeNumber == "11") {
						        document.getElementById(ID).value = "pink";
						        document.getElementById(ID).style.backgroundColor = "pink";
						        document.getElementById(ID).style.color = "black";
						    }
						    else if (strandTubeNumber == "12") {
						        document.getElementById(ID).value = "aqua";
						        document.getElementById(ID).style.backgroundColor = "aqua";
						        document.getElementById(ID).style.color = "black";
						    }
						    else if (strandTubeNumber > 12 || strandTubeNumber == "") {
						        document.getElementById(ID).value = "";
						        document.getElementById(ID).style.backgroundColor = "";
						        document.getElementById(ID).style.color = "";
						    }
						}

						document.addEventListener('DOMContentLoaded', function() {
							document.getElementById('fpStrandColor').addEventListener('change', function() {
								tubeStrandSetColor('fpStrandColor', 'fpStrandNb');
							});

							document.getElementById('fpStrandNb').addEventListener('change', function() {
							    const colorElement = document.getElementById('fpStrandColor');
							    const numberElement = document.getElementById('fpStrandNb');
							    tubeStrandNoChange(numberElement, colorElement);
							});

							document.getElementById('fpTubeColor').addEventListener('change', function() {
								tubeStrandSetColor('fpTubeColor', 'fpTubeNb');
							});

							document.getElementById('fpTubeNb').addEventListener('change', function() {
							    const colorElement = document.getElementById('fpTubeColor');
							    const numberElement = document.getElementById('fpTubeNb');
							    tubeStrandNoChange(numberElement, colorElement);
							});
							});
							
							
							
							document.addEventListener('DOMContentLoaded', function() {
											document.getElementById('bpStrandColor').addEventListener('change', function() {
												tubeStrandSetColor('bpStrandColor', 'bpStrandNb');
											});

											document.getElementById('bpStrandNb').addEventListener('change', function() {
											    const colorElement = document.getElementById('bpStrandColor');
											    const numberElement = document.getElementById('bpStrandNb');
											    tubeStrandNoChange(numberElement, colorElement);
											});

											document.getElementById('bpTubeColor').addEventListener('change', function() {
												tubeStrandSetColor('bpTubeColor', 'bpTubeNb');
											});

											document.getElementById('bpTubeNb').addEventListener('change', function() {
											    const colorElement = document.getElementById('bpTubeColor');
											    const numberElement = document.getElementById('bpTubeNb');
											    tubeStrandNoChange(numberElement, colorElement);
											});
											});
											
											
											
											function tubeStrandNoChange(numberElement, colorElement) {
											    const number = numberElement.value;
											    console.log("number is " + number);

											    // Mapping tube/strand numbers to colors and text colors
											    const numberMap = {
											        "1": { color: "blue", text: "white" },
											        "2": { color: "orange", text: "white" },
											        "3": { color: "green", text: "white" },
											        "4": { color: "brown", text: "white" },
											        "5": { color: "gray", text: "white" },
											        "6": { color: "white", text: "black" },
											        "7": { color: "red", text: "white" },
											        "8": { color: "black", text: "white" },
											        "9": { color: "yellow", text: "black" },
											        "10": { color: "violet", text: "white" },
											        "11": { color: "pink", text: "black" },
											        "12": { color: "aqua", text: "black" }
											    };

											    if (!numberMap[number]) {
											        // Clear if invalid or empty
											        colorElement.value = "";
											        colorElement.classList.remove("colored-select");
											        colorElement.style.backgroundColor = "";
											        colorElement.style.color = "";
											        return;
											    }

											    // Apply color
											    colorElement.value = numberMap[number].color;
											    colorElement.classList.add("colored-select");
											    colorElement.style.backgroundColor = numberMap[number].color;
											    colorElement.style.color = numberMap[number].text;
											}				
						
	});//end ready document

function workOrderModel(){

	 $("#patchingId").val('');
	    $("#patchingStatus").val('-- Select Option --');
	    $("#assignedTo").val('');
	    $("#plannedExecutionDate").val('');
	    $("#actualExecutionDate").val('');
	    $("#createdDate").val('');
	    $("#lastModifiedDate").val('');
	    $("#patchingNote").val('');

	    // reset checkbox if you want
	    $("#workOrderTaskCheck").prop("checked", true);
	 $("#workOrderModel").modal('show');	

	}


	function saveWorkOrder(){



		var patchingId = document.getElementById("patchingId").value;
		var assignedTo = document.getElementById("assignedTo").value;
		var createdDate = document.getElementById("createdDate").value;
		var lastModifiedDate = document.getElementById("lastModifiedDate").value;

		// SELECT
		var patchingStatus = document.getElementById("patchingStatus").value;

		// DATETIME
		var plannedExecutionDate = document.getElementById("plannedExecutionDate").value;
		var actualExecutionDate = document.getElementById("actualExecutionDate").value;

		// TEXTAREA
		var patchingNote = document.getElementById("patchingNote").value;
		var token = $('input[name="csrfToken"]').attr('value');
		console.log("Tree container:", $("#patchingWorkOrder_tree").length, $("#patchingWorkOrder_tree"));
		console.log("Initial UL:", $("#initial_ul_CurrentPatchingLayer").length, $("#initial_ul_CurrentPatchingLayer"));
		console.log("Container scrollable?", $("#patchingWorkOrder_tree").css("overflow-y"));

		       
		          $.ajax({
		              type: "POST",
		              headers: {
		                  'X-CSRFToken': token
		              },
		              url: getContext() + '/savePatchingOrder',
		              async: false,
		          
		
		                   data: {
							"patchingId": patchingId,
						    "patchingStatus" : patchingStatus,
						    "assignedTo" : assignedTo,
							"plannedExecutionDate" : plannedExecutionDate,
						     "actualExecutionDate" : actualExecutionDate,
							 "createdDate"  : createdDate,
							  "lastModifiedDate" : lastModifiedDate,
							  "patchingNote" : patchingNote
		                   },
		                   dataType: "json",
						   success: function(data) {
						       alert("Saved done");
						       $("#workOrderModel").modal('hide');	
						       if ($("#workOrderTaskCheck").is(":checked")) {

						    	   $("#taskPatchingId").val(data.id);
						    	   $("#patchingNotes").val(patchingNote);
							         
							
							    }

						    
						   },
						   error: function(result) {
						       alert("Error");
						   }

		               });
		
		
		
		
		
	
	}

	function ClosingConfirm(){
		var c=null;
	
		if($("#workOrderModel").is(':visible')){
		c=$("#workOrderModel");}
		var result= confirm('are you sure you want to close?')
			if (result== false){
				c.modal('show');
				}
			 if (result== true){
			   c.modal('hide');
			   
			}
		}

	  
	  function getContext() {
		   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
		}


function newTask(){
		    var baseUrl = getContext() + "/PatchingWorkOrderTaskFormView?type=addNew";

		    if ($("#workOrderCheck").is(":checked")) {

		        var patchingId = $("#taskPatchingId").val();
		        var notes = $("#patchingNotes").val();

		        // Encode values to avoid URL breaking
		        patchingId = encodeURIComponent(patchingId);
		        notes = encodeURIComponent(notes);

		        baseUrl += "&woPatchingId=" + patchingId +
		                   "&patchingOrderNote=" + notes;
		    }

		    window.location.href = baseUrl;
		};





		function disableTaskForm() {

		    // Disable all inputs, selects and textareas inside the task container
		    $("#custom-tabs-one-tabContent")
		        .find("input, select, textarea, button")
		        .prop("disabled", true);

		}

		function enableTaskForm() {

		    // Enable all inputs, selects and textareas inside the task container
		    $("#custom-tabs-one-tabContent")
		        .find("input, select, textarea, button")
		        .prop("disabled", false);

		}
 </script>



</html>