<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cell Form View</title>
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
                                <span style="width:190px;" class="input-group-text" style="color:green;">Cell
                                ID
                                   </span>
                                <input type="text" id="cellid" value="${CellID}" class="form-control text-input"
                                    readonly />
                            </div>
                        </div>
                    </div>


                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:190px;" class="input-group-text">Cell Name</span>
                                <input type="text" id="cellname" value="${CellName}"
                                    class="form-control text-input"  readonly/>
                            </div>


                        </div>
                    </div>

                    <div class="pad col-md-3 hide-row"></div>
                    <div class="col-md-3 nextprvItems">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:200px;" class="input-group-text">Other Cells</span>
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




                    <div class="col-md-3">
                        <div class="btn-group pull-right" style="text-align: right;">


	                    

                            <div class="glyph" style="text-align: right;">
                            
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
                            
                            

                                <button type="button" id="Fview" class="btn btn-danger" data-toggle="tooltip"
                                    data-placement="top" title="Form View" style="background: #da6815;">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <button type="button" id="Lview" class="btn btn-light" data-toggle="tooltip"
                                    onclick='window.location.href = "${pageContext.request.contextPath}/CellListView"'
                                    data-placement="top" title="List View">
                                    <i class="fa fa-bars"></i>
                                </button>
                                

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
					style="color: gold;">Cell Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-node-tab" data-toggle="tab"
					href="#custom-tabs-node" role="tab"
					aria-controls="custom-tabs-node" aria-selected="false"
					style="color: gold;">Node Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-cell-tab" data-toggle="tab"
					href="#custom-tabs-cell" role="tab"
					aria-controls="custom-tabs-cell" aria-selected="false"
					style="color: gold;">Site Information</a></li>





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
								<hr>
																
								<h4>Active Parameters</h4>
								<hr>
								<div class="row">
								<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Technology</label> <input type="text"
												id="technology" readonly value="${Technology}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>CGI</label> <input type="text"
												id="cgi" readonly value="${CGI}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Band (900/1800/MBC)</label> <input type="text"
												id="band" readonly value="${Band}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Net TRX's Available</label> <input type="text"
												id="nettrx" readonly value="${nettrx}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Tx Power (dB)</label> <input type="text"
												id="txpower" readonly value="${Txpower}"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								
								<hr>
								
    <div id="accordion" role="tablist" aria-multiselectable="true">
        <div class="card">
            <div role="tab"  id="headingOne"  data-toggle="collapse" data-parent="#accordion" data-target="#collapseOne" >
                <h4  >
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" >
                  Other Active Parameters
                </a>
              </h4>
            </div>

            <div id="collapseOne" class="collapse in" role="tabpanel" aria-labelledby="headingOne">
                <div class="card-block">
                   <div class="row">
								<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Technology</label> <input type="text"
												id="celltechnology" readonly value="${Technology}"
												class="form-control text-input" />
										</div>
									</div>
									
								
                </div>
            </div>
        </div>
      
    </div>

		</div>						
								<hr>
								
								
								
								
								
								
								
								
								
								
								<hr>
								<h4>Passive Parameters</h4>
								<hr>
								<form action="siteSave" method="post"
									commandName="siteDetails2G" id="passiveparameterscell">
									<!-- ******************************** first row passive ************************************ -->
									<div class="row">

										<div class="col-sm-3">
											<label class='required'>Date On Air (DD-MM-YY)</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control"id="dateonair" type="date">
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Site Sub Type</label> <select
													id="sitesubtype" class="form-control select2">
													<option value="TTI_RU INDOOR"
														<c:if test = "${sitesubtype =='TTI_RU INDOOR'}"> selected </c:if>>TTI_RRU at Tower top &BBU Indoor</option>
													<option value="TTI_RU OUTDOOR"
														<c:if test = "${sitesubtype =='TTI_RU OUTDOOR'}"> selected </c:if>>TTI_RRU at Tower top &BBU Outdoor</option>
														<option value="TTI_RU_TOWERBASE_INDOOR"
														<c:if test = "${sitesubtype =='TTI_RU_TOWERBASE_INDOOR'}"> selected </c:if>>TTI_RRU at Tower base &BBU Outdoor</option>
														<option value="TTI_RU_TowerBase_OUTDOOR"
														<c:if test = "${sitesubtype =='TTI_RU_TOWERBASE_OUTDOOR'}"> selected </c:if>>TTI_RRU at Tower base &BBU Outdoor</option>
														<option value="femto"
														<c:if test = "${sitesubtype =='femto'}"> selected </c:if>>Femto</option>
														<option value="IM_INDOOR_MACRO"
														<c:if test = "${sitesubtype =='IM_INDOOR_MACRO'}"> selected </c:if>>IM_Indoor Macro</option>
														<option value="OM_OUTDOOR_MACRO"
														<c:if test = "${sitesubtype =='OM_OUTDOOR_MACRO'}"> selected </c:if>>OM_Outdoor_Macro</option>
														<option value="Pico"
														<c:if test = "${sitesubtype =='Pico'}"> selected </c:if>>Pico</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Mode Of Operation</label> <select
													id="modeofoperation" class="form-control select2">
													<option value="Combined"
														<c:if test = "${modeofoperation =='Combined'}"> selected </c:if>>Combined</option>
													<option value="Un-Combined"
														<c:if test = "${modeofoperation =='Un-Combined'}"> selected </c:if>>UnCombined</option>
														<option value="TCC"
														<c:if test = "${modeofoperation =='TCC'}"> selected </c:if>>TCC</option>
														<option value="Air Combining"
														<c:if test = "${modeofoperation =='Air Combining'}"> selected </c:if>>Air Combining</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Shared With 4G
													(Yes/No)</label> <select id="antennashared"
													class="form-control select2">
													<option value="Yes"
														<c:if test = "${antennashared =='Yes'}"> selected </c:if>>Yes</option>
													<option value="No"
														<c:if test = "${antennashared =='No'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>
									
									<!-- ******************************** second row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna 1-Manufacturer</label> <select
													id="gsmantennamanu1" class="form-control select2">
													<option value="E"
														<c:if test = "${gsmantennamanu1 =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${gsmantennamanu1 =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna Model 1</label> <select
													id="gsmantennaModel1" class="form-control select2">
													<option value="E"
														<c:if test = "${gsmantennaModel1 =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${gsmantennaModel1 =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna 2-Manufacturer</label><input type="text"
												id="gsmantennamanu2"  value="${Gsmantennamanu2}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna Model 2</label> <input type="text"
												id="gsmantennamodel2"  value="${Gsmantennamodel2}"
												class="form-control text-input" /> 
											</div>
									
									</div>

									<!-- ******************************** 3 row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Gain (dB)</label> <input type="text"
												id="antennagain"  value="${antennaGain}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Beam Width (Deg)</label> <input type="text"
												id="beamwidth"  value="${beamWidth}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Azimuth (Deg)</label> <input type="text"
												id="azimuth"  value="${Azimuth}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Height AGL(m)</label> <input type="text"
												id="antennaheight"  value="${AntennaHeight}"
												class="form-control text-input" /> 
											</div>
										</div>
									

									<!-- ******************************** 4 row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Electrical Tilt (Deg)</label> <input type="text"
												id="electricaltilt"  value="${ElectricalTilt}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Mechanical Tilt (Deg)</label> <input type="text"
												id="mechanicaltilt"  value="${MechanicalTilt}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>AT (Y/N)</label> <select
													id="AT" class="form-control select2">
													<option value="Y"
														<c:if test = "${AT =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${AT =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>RET (Y/N)</label> <select
													id="ret" class="form-control select2">
													<option value="Y"
														<c:if test = "${ret =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${ret =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>
									

									<!-- ******************************** 5 row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Feeder Size</label> <select
													id="feedersize" class="form-control select2">
													<option value="Fixed_input"
														<c:if test = "${feedersize =='Fixed_input'}"> selected </c:if>>Fixed-Input 7/8</option>
													<option value="jumper"
														<c:if test = "${feedersize =='jumper'}"> selected </c:if>>Jumper</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Approximate Feeder Length
													(m)</label> <input type="text"
												id="feederlength"  value="${FeederLength}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>TMA/MHA (Yes/No)</label> <select
													id="thamha" class="form-control select2">
													<option value="Y"
														<c:if test = "${thamha =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${thamha =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Remarks</label><input type="text"
												id="remarks"  value="${Remarks}"
												class="form-control text-input" /> 
											</div>
										</div>
									

									<!-- ******************************** 6 row passive ************************************ -->
									


										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Sector Status Active/Locked</label>
												<select id="sectorstatus" class="form-control select2">
													<option value="Active"
														<c:if test = "${sectorstatus =='Active'}"> selected </c:if>>Active</option>
													<option value="Locked"
														<c:if test = "${sectorstatus =='Locked'}"> selected </c:if>>Locked</option>
												</select>
											</div>
										</div>
	
											
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Sector Locked Date</label> <div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control" id="sectorlockeddate" type="date">
											</div>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>If Locked Reason For Locked</label>
												<input type="text"
												id="reasonforblocked"  value="${ReasonForBlocked}"
												class="form-control text-input" /> 
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Master Sector ID</label> <select
													id="masterid" class="form-control select2">
													<option value="Active"
														<c:if test = "${masterid =='Active'}"> selected </c:if>>Active</option>
													<option value="Locked"
														<c:if test = "${masterid =='Locked'}"> selected </c:if>>Locked</option>
														<option value="DIS"
														<c:if test = "${masterid =='DIS'}"> selected </c:if>>Dismantle</option>
												</select>
											</div>
										</div>
										
										
								
									
									<!-- ******************************** 7 row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Flag</label> <input type="text"
												id="flag"  value="${Flag}"
												class="form-control text-input" /> 
											</div>
										</div>
									</div>

								</form>
								
								
							</div>
							
							
							

							<div class="tab-pane fade" id="custom-tabs-node" role="tabpanel"
								aria-labelledby="custom-tabs-node-tab">
								<!--**************************** second tab **************************** -->
										
								
								<!--**************************** node tab **************************** -->
							
								
								<!-- ****************************** Active Parameters ************************************** -->
								<hr>
								<h4>Active Parameters</h4>
								
								<hr>
								
								<div class="row">
								
								
								<div class="col-md-4" hidden>
										<div class="form-group">
											<label class='required' hidden>Node ID pk</label> <input type="text"
												id="nodeidPK" readonly value="${NodeIdPK}"
												class="form-control text-input" hidden />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Node ID</label> <input type="text"
												id="nodeid" readonly value="${NodeId}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Node Name</label> <input type="text"
												id="nodename" readonly value="${NodeName}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BSC Name/ID</label> <input
												type="text" id="bscNameId" readonly value="${bscNameID}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>MSC Name/ID</label> <input
												type="text" id="mscnameid" readonly value="${MscNameID}"
												class="form-control text-input" />
										</div>
									</div>
								
								
								
							
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>SRAN Node (Yes/No)</label> <input
												type="text" id="sranNode" readonly value="${sran}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type-Main Cabinet</label> <input
												type="text" id="btstypem" readonly value="${btsType}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="btstypee1" readonly
												value="${btsType1}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 2/MBC
												Cabinet</label> <input type="text" id="btstypee2" readonly
												value="${btsType2}" class="form-control text-input" />
										</div>
									</div>
								

								
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Band BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="bandbts1" readonly
												value="${bandbts1}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>Band BTS Type Expansion 2/MBC Cabinet
												</label> <input type="text" id="bandbts2" readonly
												value="${bandbts2}" class="form-control text-input" />
										</div>
									</div>


									<div class="col-md-4">
										<div class="form-group">
											<label  >Active Technology On SRAN BTS
											</label> <input type="text" id="activetechnology" readonly value="${ActiveTechnology}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label >Swap (Y/N)</label> <input type="text"
												id="swap" readonly value="${Swap}"
												class="form-control text-input" />
										</div>
									</div>
								
								
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Swap Date</label> <input type="text"
												id="swapdate" readonly value="${SwapDate}"
												class="form-control text-input" />
										</div>
									</div>
								</div>

								<!-- ****************************** Passive Parameters ************************************** -->
								<hr>
								<h4>Passive Parameters</h4>
								<hr>
								<form action="siteSave" method="post"
									commandName="siteDetails2G" id="passiveparametersnode">
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Site Type</label> <select
													id="sitetype" class="form-control select2">
													<option value="Macro"
														<c:if test = "${sitetype =='Macro'}"> selected </c:if>>Macro</option>
													<option value="Micro"
														<c:if test = "${sitetype =='Micro'}"> selected </c:if>>Micro IBS</option>
														<option value="Pico"
														<c:if test = "${sitetype =='Pico'}"> selected </c:if>>Pico</option>
														<option value="femto"
														<c:if test = "${sitetype =='femto'}"> selected </c:if>>Femto</option>
														<option value="smallcell"
														<c:if test = "${sitetype =='smallcell'}"> selected </c:if>>Small Cell</option>
														<option value="relaycell"
														<c:if test = "${sitetype =='relaycell'}"> selected </c:if>>Relay Cell</option>
														<option value="atomcell"
														<c:if test = "${sitetype =='atomcell'}"> selected </c:if>>Atom Cell</option>
												</select>
											</div>
										</div>

										<div class="col-sm-4">
											<label class='required'>Date On Air-BTS
												Type-Expansion 1/MBC Cabinet</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
												
												</div>
												<input class="form-control"id="dateonair1" type="date">
											</div>
										</div>
										<div class="col-sm-4">
											<label class='required'>Date On Air-BTS
												Type-Expansion 2/MBC Cabinet</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control" id="dateonair2"type="date">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Swap (Y/N)</label> <select
													id="swap1" class="form-control select2">
													<option value="Y"
														<c:if test = "${swap1 =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${swap1 =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>
	
										<div class="col-sm-4">
											<label class='required'>Swap Date</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control" id="swapdate" type="date">
											</div>
										</div>
									</div>
								</form>						


								<hr>

							</div>

							<div class="tab-pane fade" id="custom-tabs-cell" role="tabpanel"
								aria-labelledby="custom-tabs-cell-tab">
								<!--**************************** third tab **************************** -->
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
												id="long" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Latitude</label> <input type="text"
												id="lat" readonly value="${Latitude}"
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
									commandName="siteDetails2G" id="passiveparameterssite">
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
								<hr>
								

							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end container -->
	</div>
	
</body>




</html>