<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Site Form View</title>
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

<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>


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
<style type="text/css">


.hide-row {
                display: none;
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



.required:after {
	content: " *";
	color: red;
}

.inputWithIcon input[type="text"] {
	padding-right: 40px;
}

.inputWithIcon {
	position: relative;
}

.inputWithIcon i {
	position: absolute;
	right: 0;
	top: 8px;
	padding: 9px 8px;
	transition: 0.3s;
}
</style>
</head>
<body>
	<!-- nav -->
<%-- 	<c:set var = "page" value = "network"/> --%>

<%-- 	<%@ include file="../header.html" %> --%>
  <c:set var="pg" value="network" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
	<!--  -->
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<p></p>
			</div>

		</div>

		<!-- *************************** site fields ************************************** -->

	<div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:190px;" class="input-group-text" style="color:green;">Site
                                ID
                                   </span>
                                <input type="text" id="siteid" value="${SiteID}" class="form-control text-input"
                                    readonly />
                            </div>
                        </div>
                    </div>


                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:190px;" class="input-group-text">Site Name</span>
                                <input type="text" id="sitename" value="${SiteName}"
                                    class="form-control text-input"  readonly/>
                            </div>


                        </div>
                    </div>

                    <div class="pad col-md-3 hide-row"></div>
                    <div class="col-md-3 nextprvItems">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:200px;" class="input-group-text">Other Sites</span>
                                <select id="selectnav" class="form-control select2"></select>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3 text-right">
                        <i>&nbsp</i><span class="dot"></span>
                        <i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;">Saved</label>
                    </div>

                </div>

		           <div class="row">


                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
                                <span style="width:190px;" class="input-group-text">Created Date</span>
                                <input type="text" id="createdate" readonly value="${createdDate}"
                                    class="form-control datetimepicker-input" data-toggle="datetimepicker"
                                    data-target="#datetimepicker1" />
                                <div class="input-group-append" data-target="#datetimepicker1"
                                    data-toggle="datetimepicker">

                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
                                <span class="input-group-text">Last Modify Date</span>
                                <input type="text" id="lstmodifdate" readonly value="${lastModifiedDate}"
                                    class="form-control datetimepicker-input" data-toggle="datetimepicker"
                                    data-target="#datetimepicker2" />
                                <div class="input-group-append" data-target="#datetimepicker2"
                                    data-toggle="datetimepicker">

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="hide-row col-md-3 pad "></div>

                    <div class=" col-md-3 nextprvItems">
                        <label id="label-1"
                            style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <li id="btnPrv" class="page-item " style="margin-right: 2px;"><a style="width:120px;"
                                        id="btnPrva" href="#" class="btn btn-success previous">&laquo; Previous</a></li>
                                <li id="btnNext" class="page-item" style=" padding-right: 0px ! important;"><a
                                        style="width:120px;" id="btnNexta" style="width:100px;" href="#"
                                        class="btn btn-success next">Next &raquo;</a></li>
                            </ul>
                        </nav>
                    </div>




                   <div class="col-md-3" style="text-align: right;">
				<div class="btn-group pull-right">
					<div class="glyph">


						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search" id="open-popup-btn">
							<i class="fa fa-search"></i>
						</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title=" GIS"
							onclick='window.location.href = "${pageContext.request.contextPath}/GisPage"'>
							<i class="fas fa-map-marked-alt"></i>
						</button>


						<button type="button" class="btn btn-danger" data-toggle="tooltip"
							data-placement="top" title=" Folder Tree"
							style="background: #da6815;">
							<i class="fas fa-sitemap"></i>
						</button>

						<button type="button" id="Fview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="Form View"
							onclick='window.location.href = "${pageContext.request.contextPath}/SiteFormView"'>
							<i class="fa fa-edit"></i>
						</button>
						<a href="Sitelistview">

							<button type="submit" id="Lview" class="btn btn-light"
								data-toggle="tooltip" data-placement="top" title="List View">
								<!--  onclick='window.location.href = "${pageContext.request.contextPath}/SiteListView"' -->
								<i class="fa fa-bars"></i>
							</button>
						</a>

					</div>
				</div>
			</div>
                </div>

	</div>
	<!-- ************************************* Site tab ************************************* -->
	<div class="container-fluid">
	<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">
			<ul class="nav nav-tabs" id="custom-tabs-all-tab" role="tablist"
				style="background-color: #00757c; margin-top: 0px;">

				<li class="nav-item"><a class="nav-link active"
					id="custom-tabs-site-tab" data-toggle="tab"
					href="#custom-tabs-site" role="tab"
					aria-controls="custom-tabs-site" aria-selected="true"
					style="color: gold;">Site Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-node-tab" data-toggle="tab"
					href="#custom-tabs-node" role="tab"
					aria-controls="custom-tabs-node" aria-selected="false"
					style="color: gold;">Node Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-cell-tab" data-toggle="tab"
					href="#custom-tabs-cell" role="tab"
					aria-controls="custom-tabs-cell" aria-selected="false"
					style="color: gold;">Cell Information</a></li>





				<li class="nav-item ml-auto">
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

	<!-- ************************************************************************** -->
</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card card-primary card-tabs cards-margin">
					<div class="card-body">
						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-site"
								role="tabpanel" aria-labelledby="custom-tabs-site-tab">
								<!--**************************** first tab **************************** -->
								
								
								<!-- ****************************** Active Parameters ************************************** -->
								<hr>
								<h4>Active Parameters</h4>
								<hr>
								<div class="row">
								<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Site ID</label> <input type="text"
												id="siteID" readonly value="${SiteID}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Site Name</label> <input type="text"
												id="siteName" readonly value="${SiteName}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Longitude</label> <input type="text"
												id="longitude" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Latitude</label> <input type="text"
												id="latitude" readonly value="${latitude}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Site Mode</label> <input type="text"
												id="sitemode" readonly value="${siteMode}"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<!-- ****************************** Passive Parameters ************************************** -->
								<hr>
								<h4>Passive Parameters</h4>
								<hr>
								<form action="siteSave" method="post"
									commandName="siteDetails2G" id="sitePassive2G">
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Existing / New Town</label> <select
													id="existNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${existNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${existNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Showcase / Non Showcase
													(SC/NSC)</label> <select id="showcaseNonShowcase"
													class="form-control select2">
													<option value="SC"
														<c:if test = "${showcaseNonShowcase =='SC'}"> selected </c:if>>SC</option>
													<option value="NSC"
														<c:if test = "${showcaseNonShowcase =='NSC'}"> selected </c:if>>NSC</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="sitePrincipallabel">Site
													Principal Owner (Toco Name)</label> <input id="sitePrincipleOwner"
													type="text" class="form-control"
													value="${sitePrincipleOwner}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="towerCoIdlabel">Tower Co
													ID</label> <input type="text" id="towerCoId" class="form-control"
													value="${towerCoId}" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Tower Type </label> <select
													id="towerType" class="form-control select2">
													<option value="GBT"
														<c:if test = "${towerType =='GBT'}"> selected </c:if>>GBT</option>
													<option value="RTT"
														<c:if test = "${towerType =='RTT'}"> selected </c:if>>RTT</option>
													<option value="RTP"
														<c:if test = "${towerType =='RTP'}"> selected </c:if>>RTP</option>
													<option value="Wall Mounted"
														<c:if test = "${towerType =='Wall Mounted'}"> selected </c:if>>Wall
														Mounted</option>
													<option value="IBS"
														<c:if test = "${towerType =='IBS'}"> selected </c:if>>IBS</option>
													<option value="GBP"
														<c:if test = "${towerType =='GBP'}"> selected </c:if>>GBP</option>
													<option value="COW"
														<c:if test = "${towerType =='COW'}"> selected </c:if>>COW</option>
													<option value="NOW"
														<c:if test = "${towerType =='NOW'}"> selected </c:if>>NOW</option>
													<option value="GBT + Revamp"
														<c:if test = "${towerType =='GBT + Revamp'}"> selected </c:if>>GBT
														+ Revamp</option>
													<option value="RTT + Revamp"
														<c:if test = "${towerType =='RTT + Revamp'}"> selected </c:if>>RTT
														+ Revamp</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="towerHeightlabel">Tower
													Height (m)</label> <input type="number" min='0' id="towerHeight"
													class="form-control" value="${towerHeight}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="buildingHeightlabel">Building
													Height (m)</label> <input type="number" min='0' id="buildingHeight"
													class="form-control" value="${buildingHeight}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Shared / None Shared</label> <select
													id="sharedNonShared" class="form-control select2">
													<option value="Shared"
														<c:if test = "${sharedNonShared =='Shared'}"> selected </c:if>>Shared</option>
													<option value="Non-Shared"
														<c:if test = "${sharedNonShared =='Non-Shared'}"> selected </c:if>>Non-Shared</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>ICR category (ICR/MORAN)</label> <select
													id="icrCategory" class="form-control select2">
													<option value="NO"
														<c:if test = "${icrCategory =='NO'}"> selected </c:if>>NO</option>
													<option value="ICR"
														<c:if test = "${icrCategory =='ICR'}"> selected </c:if>>ICR</option>
													<option value="MORAN"
														<c:if test = "${icrCategory =='MORAN'}"> selected </c:if>>MORAN</option>
												</select>
											</div>
										</div>
									</div>
								</form>
							</div>

							<div class="tab-pane fade" id="custom-tabs-node" role="tabpanel"
								aria-labelledby="custom-tabs-node-tab">
								<!--**************************** second tab **************************** -->
								<hr>
							<hr>
						
							
     <div class=".col-sm-2"id="combobox" style="margin-bottom:10px;">
                    Show
                    <select class="cmb-row-count" style="width:75px;">
                        <option value="5">5</option>
                        <option value="10" selected>10</option>
                        <option value="15">15</option>                        
                    </select>
                    Rows
                </div>
           
           		
									
									<div class="table-responsive-sm">
    <table id="nodeTable"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
        <tr class="header">
        
        
          <th style="text-align: center; width: 3% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Node ID <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Node Name  <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Node Type <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
          
        </tr>
        <tr id="search_by_column">
        <th></th>
            <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Cell ID.." onkeyup="filterData(this,0,'gridTable');"></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Cell Name.." onkeyup="filterData(this,1,'gridTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Technologies.." onkeyup="filterData(this,2,'gridTable');"></th>
            
        </tr>
        
      </table>
    </div>     
         

      <!-- The Modal -->
  <div class="modal fade" id="openModal" tabindex="-1" role="dialog" aria-labelledby="openModalLabel" aria-hidden="true">
  
   <div class="modal-dialog modal-sm">
   
      <div class="modal-content">
      
      	<div class="modal-header">
        				<h3 class="modal-title" id="openModalLabel">Filtering Data</h3>
        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
         	 			<span aria-hidden="true">&times;</span>
        				</button>
      				</div>
      
        <!-- Modal Header -->
        
        
        

        <!-- Modal body -->
        <div class="modal-body" style="margin-top: 10px; width:auto; height:auto;">
                 <ul  class="nav nav-pills">
                  <li ><a data-toggle="pill" href="#home"  title="Custom Filter"><div style="border:2px inset grey; width:auto; height:auto;" onmouseover="changecolor2(this)"  onmouseout="returncolor2(this)"><img src="${pageContext.request.contextPath}/resources/images/dropdown1.png" width="35" style=" margin-left: auto;margin-right: auto; display: block; padding-left:10px; padding-right:10px; padding-top:10px; padding-bottom:10px;" ></div></a></li>
                  
                  <li class="active" > <a data-toggle="pill" href="#menu1" title="Advanced Filter"><div style="border:2px inset grey; width:auto; height:auto;"onmouseover="changecolor2(this)"  onmouseout="returncolor2(this)"><img src="${pageContext.request.contextPath}/resources/images/filter.png" width="35"style=" margin-left: auto;margin-right: auto; display: block; padding-left:10px; padding-right:10px; padding-top:10px; padding-bottom:10px;"></div></a></li>
                </ul>
                
                <div class="tab-content">
                    <div id="home" class="tab-pane fade">
                        <input type="text" id="columnIndex" value="" hidden>
                        <input type="text" id="dataType" value="" hidden>
                    <table id="tbl_check" class='filter_tbl'>

                    </table>
                  </div>
                  <div id="menu1" class="tab-pane fade in active">
                    <div>
                    <table class="filter_tbl">
                        <tr>
                            <td>
                                Condition: <select id="drop1">
                                    
                                </select>
                            </td>
                        </tr>
                        <tr id="hid_filter1">
                            <td>

                            </td> 
                        </tr>
                        <tr>
                            <td style="text-align: center;">
                                <input type="radio" name="AndOrRadio" value="and"> And
                                <input type="radio" name="AndOrRadio" value="or"> OR
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <select id="drop2">
                                    <option>begins with</option>
                                    <option>in between</option>
                                    <option>contains</option>
                                    <option>empty</option>
                                    <option>ends with</option>
                                    <option>equals</option>
                                    <option>greater than</option>
                                    <option>less than</option>
                                </select>
                            </td>
                        </tr>

                        <tr id="hid_filter2">
                            <td>

                            </td>
                        </tr>
                    </table>
                    </div>
                  </div>
                </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location.reload(true);">Clear</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="getRows()">Search</button>
        </div>
        
      </div>
    </div>
  </div>
  
    
  
  <hr>
								<hr>
							</div>

							<div class="tab-pane fade" id="custom-tabs-cell" role="tabpanel"
								aria-labelledby="custom-tabs-cell-tab">
								<!--**************************** third tab **************************** -->
								<!--**************************** cell table ****************************-->
								<hr>
						
							
     <div class=".col-sm-2"id="combobox" style="margin-bottom:10px;">
                    Show
                    <select class="cmb-row-count" style="width:75px;">
                        <option value="5">5</option>
                        <option value="10" selected>10</option>
                        <option value="15">15</option>                        
                    </select>
                    Rows
                </div>
           
           		
									
									<div class="table-responsive-sm">
    <table id="gridTable"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
        <tr class="header">
        
        
          <th style="text-align: center; width: 3% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Cell ID <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Cell Name  <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Technologies <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
          
        </tr>
        <tr id="search_by_column">
        <th></th>
            <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Cell ID.." onkeyup="filterData(this,0,'gridTable');"></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Cell Name.." onkeyup="filterData(this,1,'gridTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Technologies.." onkeyup="filterData(this,2,'gridTable');"></th>
            
        </tr>
        
      </table>
    </div>     
         

      <!-- The Modal -->
  <div class="modal fade" id="openModal" tabindex="-1" role="dialog" aria-labelledby="openModalLabel" aria-hidden="true">
  
   <div class="modal-dialog modal-sm">
   
      <div class="modal-content">
      
      	<div class="modal-header">
        				<h3 class="modal-title" id="openModalLabel">Filtering Data</h3>
        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
         	 			<span aria-hidden="true">&times;</span>
        				</button>
      				</div>
      
        <!-- Modal Header -->
        
        
        

        <!-- Modal body -->
        <div class="modal-body" style="margin-top: 10px; width:auto; height:auto;">
                 <ul  class="nav nav-pills">
                  <li ><a data-toggle="pill" href="#home"  title="Custom Filter"><div style="border:2px inset grey; width:auto; height:auto;" onmouseover="changecolor2(this)"  onmouseout="returncolor2(this)"><img src="${pageContext.request.contextPath}/resources/images/dropdown1.png" width="35" style=" margin-left: auto;margin-right: auto; display: block; padding-left:10px; padding-right:10px; padding-top:10px; padding-bottom:10px;" ></div></a></li>
                  
                  <li class="active" > <a data-toggle="pill" href="#menu1" title="Advanced Filter"><div style="border:2px inset grey; width:auto; height:auto;"onmouseover="changecolor2(this)"  onmouseout="returncolor2(this)"><img src="${pageContext.request.contextPath}/resources/images/filter.png" width="35"style=" margin-left: auto;margin-right: auto; display: block; padding-left:10px; padding-right:10px; padding-top:10px; padding-bottom:10px;"></div></a></li>
                </ul>
                
                <div class="tab-content">
                    <div id="home" class="tab-pane fade">
                        <input type="text" id="columnIndex" value="" hidden>
                        <input type="text" id="dataType" value="" hidden>
                    <table id="tbl_check" class='filter_tbl'>

                    </table>
                  </div>
                  <div id="menu1" class="tab-pane fade in active">
                    <div>
                    <table class="filter_tbl">
                        <tr>
                            <td>
                                Condition: <select id="drop1">
                                    
                                </select>
                            </td>
                        </tr>
                        <tr id="hid_filter1">
                            <td>

                            </td> 
                        </tr>
                        <tr>
                            <td style="text-align: center;">
                                <input type="radio" name="AndOrRadio" value="and"> And
                                <input type="radio" name="AndOrRadio" value="or"> OR
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <select id="drop2">
                                    <option>begins with</option>
                                    <option>in between</option>
                                    <option>contains</option>
                                    <option>empty</option>
                                    <option>ends with</option>
                                    <option>equals</option>
                                    <option>greater than</option>
                                    <option>less than</option>
                                </select>
                            </td>
                        </tr>

                        <tr id="hid_filter2">
                            <td>

                            </td>
                        </tr>
                    </table>
                    </div>
                  </div>
                </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location.reload(true);">Clear</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="getRows()">Search</button>
        </div>
        
      </div>
    </div>
  </div>
  
    
  
  

							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end container -->
	</div>
</body>


<script> 

$(document).ready(function() {




	

	/*for(var i=0; i< workers.length;i++)
	{
	//creates option tag
	  jQuery('<option/>', {
	        value: workers[i],
	        html: workers[i]
	        }).appendTo('#dropdown select'); //appends to select if parent div has id dropdown
	}*/
	/*
	var nodeList = ${nodeList} ;

	var username='';
	var slct = [];
	//console.log("bassamData is " +bassamData + " first item code is " +bassamData[0]);
	var table = $('#nodeTable').DataTable( {
	"bProcessing": true,
    "aaData": nodeList,// <-- your array of objects
    "aoColumns":[
    	{
        	"mData": null,
          	"bSortable": false,
	        "mRender" : function ( nodeList, type, full ) { 
		        
	            	return ''}    		
    	}, 
        
        {
            "aTargets":[ 1 ],
            "sType": "String",
            "mRender": function(url, type, full) {
                console.log(full[1]);
            	return    '<a href="'+ '${pageContext.request.contextPath}/WarehouseFormView?wareID='+full[1] +'&warehouseName='+full[3] +'">' + url + '</a>';
				}
   
       },
      {
    	   "aTargets":[ 2 ],
           "sType": "String",
           "mRender": function(url, type, full) {
               if(full[2] == null){return '';}else{
           	return    '<a href="'+ '${pageContext.request.contextPath}/SiteFormView?siteid='+full[2] +'">'+ url + '</a>';
               }
				}
         },
         {
      	   "aTargets":[ 2 ],
             "sType": "String",
             "mRender": function(url, type, full) {
                 if(full[2] == null){return '';}else{
             	return    '<a href="'+ '${pageContext.request.contextPath}/SiteFormView?siteid='+full[2] +'">'+ url + '</a>';
                 }
  				}
           },
       
      
	
		],
          'columnDefs': [
         {
            'targets': 0,
            className : 'select-checkbox',
            'checkboxes': {
               'selectRow': true,
               'selectCallback': function(nodes, selected){
                  // If "Show all" is not selected
                  if($('#ctrl-show-selected').val() !== 'all'){
                     // Redraw table to include/exclude selected row
                     table.draw(false);                  
                  }            
               }
            },
         }
      ],
		select : {
			style : 'multi',
			selector : 'td:first-child'
		},  
		'order': [[2, 'asc']],
			select: true,
			order : [ [ 0, 'asc' ] ],
			'select': { style: 'multi',
      selector : 'td:first-child'
      },
      'order': [[2, 'asc']],
      	dom: 'Blfrtip',
		"paging":true,
		"pageLength":10, 
		"scrollY": 310,
		"scrollX": true,
		"ordering":true,
		"rowReorder": true,
		"info":       true,
		"filter":     true,
		"length":     true,
		"processing": true,
		"deferRender": true,
          //orderable : false,
      
   });

	   //************************************************************************
	   var cellList = ${cellList} ;

	var username2='';
	var slct2 = [];
	//console.log("bassamData is " +bassamData + " first item code is " +bassamData[0]);
	var table = $('#nodeTable').DataTable( {
	"bProcessing": true,
    "aaData": cellList,// <-- your array of objects
    "aoColumns":[
    	{
        	"mData": null,
          	"bSortable": false,
	        "mRender" : function ( cellList, type, full ) { 
		        
	            	return ''}    		
    	}, 
        
        {
            "aTargets":[ 1 ],
            "sType": "String",
            "mRender": function(url, type, full) {
                console.log(full[1]);
            	return    '<a href="'+ '${pageContext.request.contextPath}/WarehouseFormView?wareID='+full[1] +'&warehouseName='+full[3] +'">' + url + '</a>';
				}
   
       },
      {
    	   "aTargets":[ 2 ],
           "sType": "String",
           "mRender": function(url, type, full) {
               if(full[2] == null){return '';}else{
           	return    '<a href="'+ '${pageContext.request.contextPath}/SiteFormView?siteid='+full[2] +'">'+ url + '</a>';
               }
				}
         },
         {
      	   "aTargets":[ 3 ],
             "sType": "String",
             "mRender": function(url, type, full) {
                 if(full[2] == null){return '';}else{
             	return    '<a href="'+ '${pageContext.request.contextPath}/SiteFormView?siteid='+full[2] +'">'+ url + '</a>';
                 }
  				}
           } 
	
		],
          'columnDefs': [
         {
            'targets': 0,
            className : 'select-checkbox',
            'checkboxes': {
               'selectRow': true,
               'selectCallback': function(nodes, selected){
                  // If "Show all" is not selected
                  if($('#ctrl-show-selected').val() !== 'all'){
                     // Redraw table to include/exclude selected row
                     table.draw(false);                  
                  }            
               }
            },
         }
      ],
		select : {
			style : 'multi',
			selector : 'td:first-child'
		},  
		'order': [[2, 'asc']],
			select: true,
			order : [ [ 0, 'asc' ] ],
			'select': { style: 'multi',
      selector : 'td:first-child'
      },
      'order': [[2, 'asc']],
      	dom: 'Blfrtip',
		"paging":true,
		"pageLength":10, 
		"scrollY": 310,
		"scrollX": true,
		"ordering":true,
		"rowReorder": true,
		"info":       true,
		"filter":     true,
		"length":     true,
		"processing": true,
		"deferRender": true,
          //orderable : false,
      
   });
	   //************************************************************************
// checkbox
     $('#example tbody').on( 'click', 'tr', function () {
            var $checkbox = $(this).toggleClass('selected')
			if($(this).hasClass('selected')) {
    			var pos = table.row(this).index();
    			var row = table.row(pos).data();
    			var str= new Array();
		      	str=row
		      	username=str[1];
		      	slct.push(username);
		      	// console.log(username);
		      	console.log('array is: ' +slct);
		      	} else { 
		      	   $checkbox.removeAttr('checked');
		      		console.log( 'unselected NO' );
		      		var pos = table.row(this).index();
    				var row = table.row(pos).data();
    				var str= new Array();
		      		str=row
		      		username=str[1];
		      		removeItem = username;
		      		console.log('user to remove: '+username);
		      		slct = jQuery.grep(slct, function(value) {
        				return value != removeItem;
      					});
      					console.log('array after remove is: '+slct);
		      	 }
		
    } );

			$('#multiselect').on('click', function() {
			    var count=$('[name="example_length"]').val();
			    
					if ($("th.select-checkbox").hasClass("selected")) {
						table.rows().deselect();
						$("th.select-checkbox").removeClass("selected");
					} else {
						var p = table.rows({ page: 'current' }).nodes();
						var i;
						var x=0;
						for(i=0; i<=count; i++){
						x=i;
						table.row(':eq('+x+')', { page: 'current' }).select();
						}
						//table.row({page: 'current' }).select();
						//table.rows().select();
						$("th.select-checkbox").addClass("selected");
						
					}
				}).on("select deselect", function() {
					if (table.rows({
						selected : true
					}).count() !== table.rows().count()) {
			          $("th.select-checkbox").removeClass("selected");
					} else {
						$("th.select-checkbox").addClass("selected");
					}
				}); 

	 
			 $("#deleteButton").click(  function() {
			 		 console.log('delete now');
			 		var rolenames ='0';
			 		
			 		var i;
			 		for (i = 0; i < slct.length; ++i) {
			 		    rolenames =slct[i];
			 		    console.log("the  usennames " +rolenames);
						$.ajax({
							type : "GET",
							url : "${pageContext.request.contextPath}/UserRoleFormDelete",
							dataType : "json",
							data : {
								"rolename" : rolenames
							},
							success : function(data) {
								location.reload();
								
							},
							error : function(error) {
								console.log("The error is " + error);
							}
						});
						
						
					}
			 
		 });
	*/	   
				
		});

</script>

</html>