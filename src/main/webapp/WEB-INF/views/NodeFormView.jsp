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
						</span> <input type="text" id="wareID" readonly value="${wareID}"
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
							value="${wcreationDate}"
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
							value="${wlastModifieddate}"
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
							onclick='window.location.href = "${pageContext.request.contextPath}/WarehouseListView"'
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
               aria-selected="false" style="color: gold;">CABINATE</a>
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
						style="color: gold;">MODEL</a></li>
        
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
						style="color: gold;">ANTENA</a></li>
						
						
						
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
          <span class="input-group-text">Node Id</span> <input type="text" id="warepname" value="${warehouseName}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
    <td width="10%" valign="top" class="left_col"></td>
    
    <td width="45%" valign="top" class="left_col">
    
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Node Name</span> <input type="text" id="warepname" value="${warehouseName}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
  </tr>
  <tr>
      <td width="45%" valign="top" class="left_col">
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Node Type</span> <input type="text" id="warepname" value="${warehouseName}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
    <td width="10%" valign="top" class="left_col"></td>
    
    <td width="45%" valign="top" class="left_col">
    
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Node Model</span> <input type="text" id="warepname" value="${warehouseName}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
  </tr>
  <tr>
      <td width="45%" valign="top" class="left_col">
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Site Id</span> <input type="text" id="warepname" value="${warehouseName}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
    <td width="10%" valign="top" class="left_col"></td>
    
    <td width="45%" valign="top" class="left_col">
    
      <div class="form-group">
        <div class="input-group-prepend">
          <span class="input-group-text">Site Name</span> <input type="text" id="warepname" value="${warehouseName}" class="form-control text-input" readonly/>
        </div>
      </div>
    </td>
  </tr>
  
</table>

				
			</div>
			<div class="tab-pane fade" id="custom-tabs-one-cell"
				role="tabpanel" aria-labelledby="custom-tabs-one-cell-tab">
				
				
				
				
			</div>
			<div class="tab-pane fade" id="custom-tabs-one-model"
				role="tabpanel" aria-labelledby="custom-tabs-one-model-tab">
				<p></p>
			
			</div>
			<!-- start -->

			<div class="tab-pane fade" id="custom-tabs-one-sim" role="tabpanel"
				aria-labelledby="custom-tabs-one-model-tab">
				

			</div>



           <div class="tab-pane fade" id="custom-tabs-version" role="tabpanel"
				aria-labelledby="custom-tabs-version-tab">
				
					</div>
					
					
					
					 <div class="tab-pane fade" id="custom-tabs-antena" role="tabpanel"
				aria-labelledby="custom-tabs-atena-tab">
				
					</div>
			
			 <div class="tab-pane fade" id="custom-tabs-subrack" role="tabpanel"
				aria-labelledby="custom-tabs-subrack-tab">


			</div>
			
			
			
			
			
			 <div class="tab-pane fade" id="custom-tabs-board" role="tabpanel"
				aria-labelledby="custom-tabs-board-tab">

			
			
			

			</div>
			<div class="tab-pane fade" id="custom-tabs-cabinat" role="tabpanel"
				aria-labelledby="custom-tabs-cabinat-tab">
				
		
		
		
				
				
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

 
 </script>

<!--Load the API from the specified URL -- remember to replace YOUR_API_KEY-->

</html>