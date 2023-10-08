<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>
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
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
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
	src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
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
<link
	href='${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.min.css'
	rel='stylesheet' type='text/css'>
<script
	src='${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.min.js'
	type='text/javascript'></script>
<script
	src="${pageContext.request.contextPath}/resources/js/warehouseF_BoqPopup.js"></script>


<style>
/*Doaa's popup Email Div'*/
#collapseOne {
	height: 560px;
	width: 800px;
	float: right;
}

#areaTab {
	float: left; /* important */
	/* width:595px; */
	height: auto;
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

.hide-row {
	display: none;
}
/* Set the size of the div element that contains the map */
.panel-body {
	border: 2px solid #808080;
	border-radius: 30px 15px 15px 5px;
}

#map {
	height: 600px;
	width: 100%;
}

select {
	width: 260px;
}

.table-container {
    width: 100%;
    overflow-x: auto;
  }
</style>
<style>
#mapText {
	border: hidden;
	width: 110px;
	height: 25px;
	border: hidden;
	background-color: #87CEEB;
	margin-left: 20px;
	text-align: center;
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

.table thead th {
	vertical-align: middle;
}

.table thead th div {
	width: 200px !important;
}

#warehouseInfo_tbl, #warehouseInfo_tbl td {
	padding: 0;
	margin: 0;
	vertical-align: top;
}

.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}

.left_col {
	height: 60px
}

.siteimg{
    display:inline;
}  

/* Image popup by ahmad */

#imgpopUpDiv {
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

#imagedisplay {
	width: 400px;
	height: 350px;
    margin: 20px;
}

.nav-link.active {
 background-color: #FFD966 !important;
    color: #00757c !important;
    }
</style>

</head>
<body>
	<script type="text/javascript">
</script>

	<%-- 	<c:set var = "page" value = "inventory"/> --%>

	<%-- 	<%@ include file="header.html" %> --%>
	<c:set var="pg" value="network" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>

	<!--  end of general head page -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<p></p>
			</div>

		</div>


		<div class="row second">
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width: 170px;">Node
						</span> <input type="text" id="wareID" readonly value="${node_pk}"
							class="form-control text-input" />
					</div>
				</div>
			</div>


			


			<div class="pad col-md-3 hide-row"></div>
			<div class="col-md-3 nextprvItems">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Other Node</span> 
						<input type="text" id="selectnav" value="${selectnav}"
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
						<span class="input-group-text" style="width: 170px;"">Created
							Date</span> <input type="text" id="wcreationDate" readonly
							value="${creation_date}"
							class="form-control datetimepicker-input"
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
						<span class="input-group-text" style="width: 210px;">Last
							Modify Date</span> <input type="text" id="wlastModifieddate" readonly
							value="${update_date}"
							class="form-control datetimepicker-input"
							data-toggle="datetimepicker" data-target="#datetimepicker2" />
						<div class="input-group-append" data-target="#datetimepicker2"
							data-toggle="datetimepicker"></div>
					</div>
				</div>
			</div>
			<div class="hide-row col-md-3 pad "></div>

			<div class=" col-md-3 nextprvItems">
				<label id="label-1"
					style="width: 80px; text-align: center; margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 51px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 51px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>						
							</ul>
				</nav>
			</div>


			<div class="col-md-3" style="text-align: right;">
				<div class="btn-group pull-right">

					<div class="glyph">

						<button type="button" id="Fview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="Form View"
							style="background: #da6815;">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-light"
							data-toggle="tooltip"
							onclick='window.location.href = "${pageContext.request.contextPath}/NodeListView"'
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

		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
					style="background-color: #00757c; margin-top: 0px;">
					<li class="tab" class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">INFORMATION</a></li>



					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-cell-tab" data-toggle="tab"
						href="#custom-tabs-one-cell" role="tab"
						aria-controls="custom-tabs-one-cell" aria-selected="false"
						style="color: gold;">CELLS</a></li>

           <li class="nav-item">
            <a class="nav-link" id="custom-tabs-cabinat-tab" data-toggle="tab"
               href="#custom-tabs-cabinat" role="tab" aria-controls="custom-tabs-cabinat"
               aria-selected="false" style="color: gold;">CABINAT</a>
        </li>
        
        
        
        <li class="nav-item">
            <a class="nav-link" id="custom-tabs-subrack-tab" data-toggle="tab"
               href="#custom-tabs-subrack" role="tab" aria-controls="custom-tabs-subrack"
               aria-selected="false" style="color: gold;">SUBRACK</a>
        </li>
        <li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-model-tab" data-toggle="tab"
						href="#custom-tabs-one-model" role="tab"
						aria-controls="custom-tabs-one-profile" aria-selected="false"
						style="color: gold;">MODULE</a></li>
        
         <li class="nav-item">
            <a class="nav-link" id="custom-tabs-board-tab" data-toggle="tab"
               href="#custom-tabs-board" role="tab" aria-controls="custom-tabs-board"
               aria-selected="false" style="color: gold;">BOARD</a>
        </li>

					

					<li class="nav-item"><a class="nav-link"
						id="custom-tabs-one-port-tab" data-toggle="tab"
						href="#custom-tabs-one-sim" role="tab"
						aria-controls="custom-tabs-one-sim" aria-selected="false"
						style="color: gold;">PORT</a></li>
						
					<!-- IMAGE TAB -->
        			<li class="nav-item"><a class="nav-link"
						id="custom-tabs-antena-tab" data-toggle="tab"
						href="#custom-tabs-antena" role="tab"
						aria-controls="#custom-tabs-antena" aria-selected="false"
						style="color: gold;">ANTTENA</a></li>
						
						
						
						<li class="nav-item"><a class="nav-link"
						id="custom-tabs-version-tab" data-toggle="tab"
						href="#custom-tabs-version" role="tab"
						aria-controls="#custom-tabs-version" aria-selected="false"
						style="color: gold;">HOST VERSION</a></li>
				
                 	<li class="nav-item ml-auto">
						<div class="dropdown" Style="display: inline-block;">
							<button disabled class="btn btn-secondary dropdown-toggle" type="button"
								id="notifactionDropdown" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">Actions</button>
							<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
								<a class="dropdown-item" type="button" id="Approvewh">Approve</a>
								<a class="dropdown-item" type="button" id="Activatewh">Activate</a>
								<a class="dropdown-item" type="button" id="Deactivatewh">Deactivate</a>
								<a class="dropdown-item" type="button" id="Cancelwh">Cancel</a>
							</div>
							<button disabled type="button" id="sendEmail"
								class="btn btn-primary BtnActive">
								<i class="fa fa-envelope"></i> Send Email
							</button>

						</div>

						<button disabled type="button" id="deleteButton"
							class="btn btn-primary BtnActive">
							<i class="fa fa-trash"></i> Delete
						</button>

						<button disabled type="button" id="saveButton"
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
			<div class="tab-pane fade show active" id="custom-tabs-one-home"
				role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

				<p></p>
			<div style="height: 20px;"></div>
<table width="90%" border="0" cellpadding="0" cellspacing="0" id="warehouseInfo_tbl">
  <tr>
    <td width="45%" valign="top" class="left_col">
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Node Id</span> <input type="text" id="warepname" value="${node_id}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
    <td width="10%" valign="top" class="left_col"></td>
    
    <td width="45%" valign="top" class="left_col">
    
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Node Name</span> <input type="text" id="warepname" value="${node_name}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
  </tr>
  <tr>
      <td width="45%" valign="top" class="left_col">
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Node Type</span> <input type="text" id="warepname" value="${node_type}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
    <td width="10%" valign="top" class="left_col"></td>
    
    <td width="45%" valign="top" class="left_col">
    
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Node Model</span> <input type="text" id="warepname" value="${node_model}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
  </tr>
  <tr>
      <td width="45%" valign="top" class="left_col">
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Site Id</span> <input type="text" id="warepname" value="${site_id}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
    <td width="10%" valign="top" class="left_col"></td>
    
    <td width="45%" valign="top" class="left_col">
    
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Site Name</span> <input type="text" id="warepname" value="${ware_name}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
  </tr>
   <tr>
    <td width="45%" valign="top" class="left_col">
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Vendor</span> <input type="text" id="warepname" value="${vendor}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
    <td width="10%" valign="top" class="left_col"></td>
    
    <td width="45%" valign="top" class="left_col">
    
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Domain</span> <input type="text" id="warepname" value="${domain}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
  </tr>
</table>

				
			</div>
			<div style="height:20px;"></div>
			<div  class="tab-pane fade" id="custom-tabs-one-cell"
				role="tabpanel" aria-labelledby="custom-tabs-one-cell-tab">
				 <caption >2G CELLS</caption>
    <table id="GCellTable" class="table table-striped table-bordered table-sm" style="display: block; height: 300px; overflow: auto;">
   				
<thead>
						            <tr class="fixed-headerr">
						               <th>GCELL_ID</th>
      <th>CELLID</th>
      <th>CELLNAME</th>
      <th>MCC</th>
      <th>MNC</th>
      <th>LAC</th>
      <th>CI</th>
      <th>NCC</th>
      <th>BCC</th>
      <th>TYPE</th>
      <th>BCCHNO</th>
      <th>BASEBANDPOLICY</th>
      <th>BASEBANDEQMID</th>
      <th>GBTSFUNCTIONNAME</th>
      <th>UPDATE_DATE</th>
      <th>GLOCELLID</th>
      <th>STATUS</th>
       <th>CREATION_DATE</th>
      <th>DOMAIN</th>
      <th>VENDOR</th>
    					                
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						        
						    </table>
	<div style="height:10px;"></div>
	 <caption><b>3G CELLS</b></caption>
     
    <table id="UCellTable" class="table table-striped table-bordered table-sm" style="display: block; height: 400px; overflow-y: auto;">
      <thead>
        <tr>
          <th>UCELL_ID</th>
          <th>CELLID</th>
          <th>CELLNAME</th>
          <th>LOCELL</th>
          <th>NODEBFUNCTIONNAME</th>
          <th>ULFREQ</th>
          <th>DLFREQ</th>
          <th>MAXPOWER</th>
          <th>USERLABEL</th>
          <th>MAXTXPOWER</th>
          <th>UARFCNUPLINK</th>
          <th>UARFCNDOWNLINK</th>
          <th>PSCRAMBCODE</th>
          <th>NODEBNAME</th>
          <th>LAC</th>
          <th>SAC</th>
          <th>RAC</th>
          <th>MANUFACTURERDATA</th>
          <th>RADIUS</th>
          <th>HORAD</th>
          <th>DI</th>
          <th>UPDATE_DATE</th>
          <th>STATUS</th>
            <th>CREATION_DATE</th>
          <th>DOMAIN</th>
          <th>VENDOR</th>
           </tr>
      </thead>
      </table>

  <div style="height:10px;"></div>
  <caption>4G CELLS</caption>
      
    <table id="LCellTable" class="table table-striped table-bordered table-sm" style="display: block; height: 400px; overflow-y: auto;">
      <thead>
        <tr>
          <th>LCELL_ID</th>
          <th>LOCALCELLID</th>
          <th>CELLNAME</th>
          <th>CELLRADIUS</th>
          <th>FREQBAND</th>
          <th>ULEARFCNCFGIND</th>
          <th>ULEARFCN</th>
          <th>DLEARFCN</th>
          <th>ULBANDWIDTH</th>
          <th>DLBANDWIDTH</th>
          <th>CELLID</th>
          <th>PHYCELLID</th>
          <th>FDDTDDIND</th>
          <th>ENODEBFUNCTIONNAME</th>
          <th>NBCELLFLAG</th>
          <th>UPDATE_DATE</th>
          <th>STATUS</th>
           <th>CREATION_DATE</th>
          <th>DOMAIN</th>
          <th>VENDOR</th>
            </tr>
      </thead>
    
    </table>
			
				
				
				
				
				
				
				
				
				
			</div>
			<div class="tab-pane fade" id="custom-tabs-one-model"
				role="tabpanel" aria-labelledby="custom-tabs-one-model-tab">
				
<table id="ModuleTable" class="table table-striped table-bordered table-sm" style="display: block; height: 500px; overflow-y: auto;">
    <tr>
        <th>MODULE_ID</th>
        <th>CABINETNO</th>
        <th>MODULENO</th>
        <th>INVUNITID</th>
        <th>SOFTVER</th>
        <th>OTHERS</th>
        <th>IDENTIFICATIONCODE</th>
        <th>CONFIGDN</th>
        <th>INVUNITTYPE</th>
        <th>PARENTDN</th>
        <th>RUNTIMEDN</th>
        <th>SERIALNUMBER</th>
        <th>STATE</th>
        <th>UNITPOSITION</th>
        <th>VENDORUNITFAMILYTYPE</th>
        <th>VENDORUNITTYPENUMBER</th>
        <th>SUBRACK_SPECIFIC_TYPE</th>
        <th>USERLABEL</th>
        <th>VENDORNAME</th>
        <th>VERSION</th>
        <th>DISTNAME</th>
        <th>UPDATE_DATE</th>
        <th>STATUS</th>
        <th>CREATION_DATE</th>
        <th>DOMAIN</th>
        <th>VENDOR</th>
        <th>ANTENNA_STATUS</th>
       
    </tr>
</table>

				
				
				
				
				
				
				
			
			</div>
			

           <div class="tab-pane fade" id="custom-tabs-version" role="tabpanel"
				aria-labelledby="custom-tabs-version-tab">
				
				
				<table id="NodeHostTable" class="table table-striped table-bordered table-sm" style="display:block; height:500px; overflow: auto;">
    <thead>
        <tr class="fixed-header">
            <th>HOST_ID</th>
            <th>RACKPOSITION</th>
            <th>INVENTORYUNITID</th>
            <th>VENDORUNITFAMILYTYPE</th>
            <th>VENDORUNITTYPENUMBER</th>
            <th>VENDORNAME</th>
            <th>SERIALNUMBER</th>
            <th>HARDWAREVERSION</th>
            <th>SOFTVER</th>
            <th>DATEOFMANUFACTURE</th>
            <th>DATEOFLASTSERVICE</th>
            <th>MANUFACTURERDATA</th>
            <th>HOSTNAME</th>
            <th>NUMBEROFCPU</th>
            <th>MEMSIZE</th>
            <th>HARDDISKSIZE</th>
            <th>UPDATE_DATE</th>
            <th>STATUS</th>
            <th>DOMAIN</th>
            <th>VENDOR</th>
          </tr>
    </thead>
   </table>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
					</div>
					
					
					
									 <div class="tab-pane fade" id="custom-tabs-antena" role="tabpanel"
				aria-labelledby="custom-tabs-atena-tab">
					
								 <table id="AntinnaTable" class="table table-striped table-bordered table-sm" style="display: block; height: 300px; overflow: auto;">
   				
<thead>
						            <tr class="fixed-headerr">
      <th>ANTENNAID</th>
      <th>INVENTORYUNITID</th>
      <th>INVENTORYUNITTYPE</th>
      <th>ANTENNADEVICENO</th>
      <th>PRODNR</th>
      <th>VENDORUNITFAMILYTYPE</th>
      <th>VENDORUNITTYPENUMBER</th>
      <th>VENDORNAME</th>
      <th>SERIALNUMBER</th>
      <th>UNITPOSITION</th>
      <th>MANUFACTURERDATA</th>
      <th>ANTENNADEVICETYPE</th>
      <th>BOMCODE</th>
      <th>EXTINFO</th>
      <th>MODEL</th>
       <th>FILENAME</th>
      <th>PARENTDN</th>
      <th>CONFIGDN</th>
       <th>DISTNAME</th>
      <th>UPDATE_DATE</th>
      <th>STATUS</th>
      <th>CREATION_DATE</th>
      <th>DOMAIN</th>
      <th>VENDOR</th>
    					                
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						        
						    </table>
				
					</div>
			
			 <div class="tab-pane fade" id="custom-tabs-subrack" role="tabpanel"
				aria-labelledby="custom-tabs-subrack-tab">

 <table id="SubrackTable" class="table table-striped table-bordered table-sm" style="display: block; height: 500px; overflow: auto;">
   				   <tr>
        <th>SUBRACK_ID</th>
        <th>SITEINDEX</th>
        <th>CABINETNO</th>
        <th>SUBRACKNO</th>
        <th>INVENTORYUNITID</th>
        <th>RACKTYPE</th>
        <th>BOMRACKTYPE</th>
        <th>FRAMETYPE</th>
        <th>RACKFRAMENO</th>
        <th>MODULENO</th>
        <th>INVENTORYUNITTYPE</th>
        <th>VENDORUNITFAMILYTYPE</th>
        <th>VENDORUNITTYPENUMBER</th>
        <th>VENDORNAME</th>
        <th>SERIALNUMBER</th>
        <th>HARDWAREVERSION</th>
        <th>DATEOFMANUFACTURE</th>
        <th>DATEOFLASTSERVICE</th>
        <th>UNITPOSITION</th>
        <th>MANUFACTURERDATA</th>
        <th>USERLABEL</th>
        <th>BOMCODE</th>
        <th>MODEL</th>
        <th>ISSUENUMBER</th>
        <th>BOMFRAMETYPE</th>
        <th>CLEICODE</th>
        <th>BOM</th>
        <th>EXTINFO</th>
        <th>UPDATE_DATE</th>
        <th>STATUS</th>
        <th>DOMAIN</th>
        <th>VENDOR</th>
          </tr>
</table>










			</div>
			
			
			
			
			
			 <div class="tab-pane fade" id="custom-tabs-board" role="tabpanel"
				aria-labelledby="custom-tabs-board-tab">

			
			
			
			
			<table id ="BoardTable" class="table table-striped table-bordered table-sm" style="display:block; height:500px; overflow: auto;">
					<thead>
			            <tr class="fixed-headerr">							    
			                <th>Board Id</th>
			                <th>Site Index</th>
			                 <th>Cabinet Number</th>
			                <th>Subrack Number</th>
			                <th>Rack Number</th>
			                <th>Frame Number</th>
			                <th>Slot Number</th>
			                <th>Slot Position</th>
			                <th>Subslot Number</th>
			                <th>Inventory Unit Id</th>
			                <th>Module Number</th>
			        		<th>Board Name</th>
			                <th>Board Type</th>
			                <th>Inventory Unit Type</th>
			                <th>Vendor Unit Family Type</th>
			                <th>Vendor Unit Type Number</th>
			                <th>Vendor Name</th>		                
			                <th>Serial Number</th>
			                <th>Hardware Version</th>
			                <th>Date Of Manufacture</th>
			                <th>Date Of Last Service</th>		                
			        		<th>Unit Position</th>
			                <th>Manufacture Data</th>
			                <th>Software Version</th>
			                <th>Logic Version</th>
			                <th>Bios Version</th>
			                <th>Bios Verex</th>
			                <th>Lan Version</th>
			                <th>Mbus Version</th>
			                <th>Issue Number</th>
			                <th>Bom Code</th>
			                <th>Model</th>
			                <th>User Label</th>
			                <th>Last Modified Date</th>
			                <th>Ext Info</th>
			                <th>Apdev Info</th>
			                <th>Work Mode</th>
                            <th>Status</th>
			                <th>Created Date</th>		                			               
			            </tr>
					</thead>
					<tbody>

					</tbody>					        
				  </table>
			
			

			</div>
			<div class="tab-pane fade" id="custom-tabs-cabinat" role="tabpanel"
				aria-labelledby="custom-tabs-cabinat-tab">
				
		<table id ="CabinatTable" class="table table-striped table-bordered table-sm" style="display:block; height:500px; overflow: auto;">
					<thead>
			            <tr class="fixed-headerr">							    
						
        <th>CABINET_ID</th>
        <th>SITEINDEX</th>
        <th>CABINETNO</th>
        <th>INVENTORYUNITID</th>
        <th>RACKTYPE</th>
        <th>OTHERS</th>
        <th>BOMRACKTYPE</th>
        <th>INVENTORYUNITTYPE</th>
        <th>VENDORUNITFAMILYTYPE</th>
        <th>VENDORUNITTYPENUMBER</th>
        <th>VENDORNAME</th>
        <th>SERIALNUMBER</th>
        <th>HARDWAREVERSION</th>
        <th>DATEOFMANUFACTURE</th>
        <th>DATEOFLASTSERVICE</th>
        <th>UNITPOSITION</th>
        <th>MANUFACTURERDATA</th>
        <th>ISSUENUMBER</th>
        <th>BOMCODE</th>
        <th>EXTINFO</th>
        <th>MODEL</th>
        <th>USERLABEL</th>
        <th>SHAREMODE</th>
        <th>CLEICODE</th>
        <th>BOM</th>
        <th>UPDATE_DATE</th>
        <th>STATUS</th>
        <th>CREATION_DATE</th>
        <th>DOMAIN</th>
        <th>VENDOR</th>
         </tr></thead></table>
		
		
		
				
				
				</div>

				<p></p>
			</div>
			
			<!-- end -->
		</div>
		</div>

	<div id="imgpopUpDiv" style="display: none;">
                                        
                    
                <div id="imagedisplay"></div>    
                    
                    
                <div>
                    <button type="button" id="imgcancelButton" style="margin-left: 20px;"
						class="btn btn-primary BtnActive" >
						<i class="fa fa-times"></i> Cancel
					</button>
			    </div>

   </div>


		<div id="popUpDiv" style="display: none;">
			<div class="sendEmail" style="margin-top: 50px;">
				<div class="col-md-12">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> Sender</span> <input type="text"
								id="email"
								class="ui-widget ui-widget-content ui-corner-all form-control" />
							<input type="text" id="password" value="${password}"
								class="ui-widget ui-widget-content ui-corner-all form-control"
								hidden />

						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width: 80px;"> Email
								To</span> <input type="text" id="emailTo"
								class="form-control text-input" />

						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="width: 80px;">
								ccEmail </span> <input type="text" id="ccmail"
								class="form-control text-input" />

						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> Title</span> <input type="text"
								id="subject" class="form-control text-input" />

						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> Message</span>
							<textarea id="message" class="form-control text-input" cols="200"
								rows="4"></textarea>

						</div>
					</div>
				</div>
				<div class="col-md-12">
					<p></p>
					<div class="form-group" style="margin-left: 100px;">

						<button type="button" id="send" class="btn btn-primary BtnActive">
							<i class="fa fa-paper-plane"></i> Send
						</button>

						<button type="button" id="cancelButton"
							class="btn btn-primary BtnActive">
							<i class="fa fa-times"></i> Cancel
						</button>
					</div>
				</div>
			</div>
		</div>
</body>




<script>

var BoqArray=[];
var status='${Status}';
if(status == "old"){
BoqArray= ${listGCELL};
for(i=0; i<BoqArray.length; i++){

	  c = "<tr>" +
      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>/tr>" ;
     
  $("#GCellTable").append(c);



	
}
BoqArray= ${listLCELL};
for(i=0; i<BoqArray.length; i++){

	  c = "<tr>" +
      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
       "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>/tr>";
     
  $("#LCellTable").append(c);



	
}
BoqArray= ${listUCELL};
for(i=0; i<BoqArray.length; i++){

	  c = "<tr>" +
      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][20] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][21] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][22] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][23] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][24] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
      "<td ><input  type='text'  value='" + BoqArray[i][25] + "' style='width:190px;' class='form-control text-input' readonly/></td></tr>";
     
  $("#UCellTable").append(c);

}


BoqArray=${listBoard};


for(i=0; i<BoqArray.length; i++){
	
		  c = "<tr>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][20] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][21] + "' style='width:700px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][22] + "' style='width:750px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][23] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][24] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][25] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][26] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][27] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][28] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][29] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][30] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][31] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][32] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][33] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][34] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][35] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][36] + "' style='width:190px;' class='form-control text-input' readonly/></td>"+
	      "<td ><input  type='text'  value='" + BoqArray[i][37] + "' style='width:190px;' class='form-control text-input' readonly/></td>"+
	      "<td ><input  type='text'  value='" + BoqArray[i][38] + "' style='width:190px;' class='form-control text-input' readonly/></td></tr>";
		     

$("#BoardTable").append(c);

}



BoqArray=${listCabinet};


for(i=0; i<BoqArray.length; i++){
	
		  c = "<tr>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:700px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][20] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][21] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][22] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][23] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][24] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][25] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][26] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][27] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][28] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][29] + "' style='width:190px;' class='form-control text-input' readonly/></td></tr>";
	     

$("#CabinatTable").append(c);

}





BoqArray=${listNodeHost};
console.log(BoqArray);

for(i=0; i<BoqArray.length; i++){
	
		  c = "<tr>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:700px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
		    "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td></tr>";
	     

$("#NodeHostTable").append(c);
}



BoqArray=${listAntinna};
console.log(BoqArray);

for(i=0; i<BoqArray.length; i++){
	
		  c = "<tr>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:700px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][20] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][21] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][22] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][23] + "' style='width:190px;' class='form-control text-input' readonly/></td></tr>";
	     

$("#AntinnaTable").append(c);
}

BoqArray=${listNodeSubrack};
console.log(BoqArray);

for(i=0; i<BoqArray.length; i++){
	
		  c = "<tr>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:700px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][20] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][21] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][22] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][23] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][24] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][25] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][26] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][27] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][28] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][29] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][30] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][31] + "' style='width:190px;' class='form-control text-input' readonly/></td></tr>";
	     

$("#SubrackTable").append(c);

}


BoqArray=${listNodeModule};


for(i=0; i<BoqArray.length; i++){
	
		  c = "<tr>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][0] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][1] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][2] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][3] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][4] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][5] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][6] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][7] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][8] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][9] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][10] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][11] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][12] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][13] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][14] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][15] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][16] + "' style='width:700px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][17] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][18] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][19] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][20] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][21] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][22] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][23] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][24] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][25] + "' style='width:190px;' class='form-control text-input' readonly/></td>" +
	      "<td ><input  type='text'  value='" + BoqArray[i][26] + "' style='width:190px;' class='form-control text-input' readonly/></td></tr>";
	     

$("#ModuleTable").append(c);

}
}


 
 </script>

<!--Load the API from the specified URL -- remember to replace YOUR_API_KEY-->

</html>