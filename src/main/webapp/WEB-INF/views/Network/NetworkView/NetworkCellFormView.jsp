<div style="padding-right: 10px;padding-left: 10px;padding-top: 10px;padding-bottom: 0px;">


<div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:190px;" class="input-group-text" style="color:green;">Cell
                                ID
                                   </span>
                                <input type="text" id="networkcellId" value="${NetworkCellID}" class="form-control text-input"
                                    readonly />
                            </div>
                        </div>
                    </div>


                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:190px;" class="input-group-text">Cell Name</span>
                                <input type="text" id="networkcellName" value="${NetworkCellName}"
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
                                <input type="text" id="networkcreatedate" readonly value="${createdDate}"
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
                                <input type="text" id="networklstmodifdate" readonly value="${lastModifiedDate}"
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
												id="networktechnology" readonly value="${NetworkTechnology}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>CGI</label> <input type="text"
												id="networkcgi" readonly value="${NetworkCGI}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Band (900/1800/MBC)</label> <input type="text"
												id="networkband" readonly value="${NetworkBand}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Net TRX's Available</label> <input type="text"
												id="networknettrx" readonly value="${networknettrx}"
												class="form-control text-input" />
										</div>
									</div>
									
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Tx Power (dB)</label> <input type="text"
												id="networktxpower" readonly value="${NetworkTxpower}"
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
												id="networkcelltechnology" readonly value="${NetworkCellTechnology}"
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
													<span class="input-group-text"> <i
														class="fa fa-calendar"></i></span>
												</div>
												<input class="form-control" type="text">
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Site Sub Type</label> <select
													id="networksitesubtype" class="form-control select2">
													<option value="TTI_RU INDOOR"
														<c:if test = "${networksitesubtype =='TTI_RU INDOOR'}"> selected </c:if>>TTI_RRU at Tower top &BBU Indoor</option>
													<option value="TTI_RU OUTDOOR"
														<c:if test = "${networksitesubtype =='TTI_RU OUTDOOR'}"> selected </c:if>>TTI_RRU at Tower top &BBU Outdoor</option>
														<option value="TTI_RU_TOWERBASE_INDOOR"
														<c:if test = "${networksitesubtype =='TTI_RU_TOWERBASE_INDOOR'}"> selected </c:if>>TTI_RRU at Tower base &BBU Outdoor</option>
														<option value="TTI_RU_TowerBase_OUTDOOR"
														<c:if test = "${networksitesubtype =='TTI_RU_TOWERBASE_OUTDOOR'}"> selected </c:if>>TTI_RRU at Tower base &BBU Outdoor</option>
														<option value="femto"
														<c:if test = "${networksitesubtype =='femto'}"> selected </c:if>>Femto</option>
														<option value="IM_INDOOR_MACRO"
														<c:if test = "${networksitesubtype =='IM_INDOOR_MACRO'}"> selected </c:if>>IM_Indoor Macro</option>
														<option value="OM_OUTDOOR_MACRO"
														<c:if test = "${networksitesubtype =='OM_OUTDOOR_MACRO'}"> selected </c:if>>OM_Outdoor_Macro</option>
														<option value="Pico"
														<c:if test = "${networksitesubtype =='Pico'}"> selected </c:if>>Pico</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Mode Of Operation</label> <select
													id="networkmodeofoperation" class="form-control select2">
													<option value="Combined"
														<c:if test = "${networkmodeofoperation =='Combined'}"> selected </c:if>>Combined</option>
													<option value="Un-Combined"
														<c:if test = "${networkmodeofoperation =='Un-Combined'}"> selected </c:if>>UnCombined</option>
														<option value="TCC"
														<c:if test = "${networkmodeofoperation =='TCC'}"> selected </c:if>>TCC</option>
														<option value="Air Combining"
														<c:if test = "${networkmodeofoperation =='Air Combining'}"> selected </c:if>>Air Combining</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Shared With 4G
													(Yes/No)</label> <select id="networkantennashared"
													class="form-control select2">
													<option value="Yes"
														<c:if test = "${networkantennashared =='Yes'}"> selected </c:if>>Yes</option>
													<option value="No"
														<c:if test = "${network antennashared =='No'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>
									
									<!-- ******************************** second row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna 1-Manufacturer</label> <select
													id="networkgsmantennamanu1" class="form-control select2">
													<option value="E"
														<c:if test = "${networkgsmantennamanu1 =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${networkgsmantennamanu1 =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna Model 1</label> <select
													id="networkgsmantennaModel1" class="form-control select2">
													<option value="E"
														<c:if test = "${networkgsmantennaModel1 =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${networkgsmantennaModel1 =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>

						                    <div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna 2-Manufacturer</label><input type="text"
												id="networkgsmantennamanu2"  value="${Gsmantennamanu2}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>GSM Antenna Model 2</label> <input type="text"
												id="networkgsmantennamodel2"  value="${Gsmantennamodel2}"
												class="form-control text-input" /> 
											</div>
									
									</div>
									<!-- ******************************** 3 row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Gain (dB)</label> <input type="text"
												id="networkantennagain"  value="${NetworkantennaGain}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Beam Width (Deg)</label> <input type="text"
												id="networkbeamwidth"  value="${NetworkbeamWidth}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Azimuth (Deg)</label> <input type="text"
												id="networkazimuth"  value="${NetworkAzimuth}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Antenna Height AGL(m)</label> <input type="text"
												id="networkantennaheight"  value="${NetworkAntennaHeight}"
												class="form-control text-input" /> 
											</div>
										</div>

									<!-- ******************************** 4 row passive ************************************ -->
									
											<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Electrical Tilt (Deg)</label> <input type="text"
												id="networkelectricaltilt"  value="${networkElectricalTilt}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Mechanical Tilt (Deg)</label> <input type="text"
												id="networkmechanicaltilt"  value="${networkMechanicalTilt}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>AT (Y/N)</label> <select
													id="networkAT" class="form-control select2">
													<option value="Y"
														<c:if test = "${networkAT =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${networkAT =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>RET (Y/N)</label> <select
													id="networkret" class="form-control select2">
													<option value="Y"
														<c:if test = "${networkret =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${networkret =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>
									

									<!-- ******************************** 5 row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Feeder Size</label> <select
													id="networkfeedersize" class="form-control select2">
													<option value="Fixed_input"
														<c:if test = "${networkfeedersize =='Fixed_input'}"> selected </c:if>>Fixed-Input 7/8</option>
													<option value="jumper"
														<c:if test = "${networkfeedersize =='jumper'}"> selected </c:if>>Jumper</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Approximate Feeder Length
													(m)</label> <input type="text"
												id="networkfeederlength"  value="${networkFeederLength}"
												class="form-control text-input" /> 
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>TMA/MHA (Yes/No)</label> <select
													id="networkthamha" class="form-control select2">
													<option value="Y"
														<c:if test = "${networkthamha =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${networkthamha =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Remarks</label><input type="text"
												id="networkremarks"  value="${networkRemarks}"
												class="form-control text-input" /> 
											</div>
										</div>
									

									<!-- ******************************** 6 row passive ************************************ -->
									


										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Sector Status Active/Locked</label>
												<select id="networksectorstatus" class="form-control select2">
													<option value="Active"
														<c:if test = "${networksectorstatus =='Active'}"> selected </c:if>>Active</option>
													<option value="Locked"
														<c:if test = "${networksectorstatus =='Locked'}"> selected </c:if>>Locked</option>
												</select>
											</div>
										</div>
	
											
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Sector Locked Date</label> <div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control" id="networksectorlockeddate" type="date">
											</div>
											</div>
										</div>

										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>If Locked Reason For Locked</label>
												<input type="text"
												id="networkreasonforblocked"  value="${networkReasonForBlocked}"
												class="form-control text-input" /> 
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Master Sector ID</label> <select
													id="networkmasterid" class="form-control select2">
													<option value="Active"
														<c:if test = "${networkmasterid =='Active'}"> selected </c:if>>Active</option>
													<option value="Locked"
														<c:if test = "${networkmasterid =='Locked'}"> selected </c:if>>Locked</option>
														<option value="DIS"
														<c:if test = "${networkmasterid =='DIS'}"> selected </c:if>>Dismantle</option>
												</select>
											</div>
										</div>
										
										
								
									
									<!-- ******************************** 7 row passive ************************************ -->
									
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Flag</label> <input type="text"
												id="networkflag"  value="${networkFlag}"
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
												id="networknodeidPK" readonly value="${networkNodeIdPK}"
												class="form-control text-input" hidden />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Node ID</label> <input type="text"
												id="networknodeid" readonly value="${networkNodeId}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Node Name</label> <input type="text"
												id="networknodename" readonly value="${networkNodeName}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BSC Name/ID</label> <input
												type="text" id="networkbscNameId" readonly value="${networkbscNameID}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>MSC Name/ID</label> <input
												type="text" id="networkmscnameid" readonly value="${networkMscNameID}"
												class="form-control text-input" />
										</div>
									</div>
								
								
								
							
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>SRAN Node (Yes/No)</label> <input
												type="text" id="networksranNode" readonly value="${networksran}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type-Main Cabinet</label> <input
												type="text" id="networkbtstypem" readonly value="${networkbtsType}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="networkbtstypee1" readonly
												value="${networkbtsType1}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 2/MBC
												Cabinet</label> <input type="text" id="networkbtstypee2" readonly
												value="${networkbtsType2}" class="form-control text-input" />
										</div>
									</div>
								

								
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Band BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="networkbandbts1" readonly
												value="${networkbandbts1}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label>Band BTS Type Expansion 2/MBC Cabinet
												</label> <input type="text" id="networkbandbts2" readonly
												value="${networkbandbts2}" class="form-control text-input" />
										</div>
									</div>


									<div class="col-md-4">
										<div class="form-group">
											<label  >Active Technology On SRAN BTS
											</label> <input type="text" id="networkactivetechnology" readonly value="${networkActiveTechnology}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label >Swap (Y/N)</label> <input type="text"
												id="networkswap" readonly value="${networkSwap}"
												class="form-control text-input" />
										</div>
									</div>
								
								
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Swap Date</label> <input type="text"
												id="networkswapdate" readonly value="${networkSwapDate}"
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
													id="networksitetype" class="form-control select2">
													<option value="Macro"
														<c:if test = "${networksitetype =='Macro'}"> selected </c:if>>Macro</option>
													<option value="Micro"
														<c:if test = "${networksitetype =='Micro'}"> selected </c:if>>Micro IBS</option>
														<option value="Pico"
														<c:if test = "${networksitetype =='Pico'}"> selected </c:if>>Pico</option>
														<option value="femto"
														<c:if test = "${networksitetype =='femto'}"> selected </c:if>>Femto</option>
														<option value="smallcell"
														<c:if test = "${networksitetype =='smallcell'}"> selected </c:if>>Small Cell</option>
														<option value="relaycell"
														<c:if test = "${networksitetype =='relaycell'}"> selected </c:if>>Relay Cell</option>
														<option value="atomcell"
														<c:if test = "${networksitetype =='atomcell'}"> selected </c:if>>Atom Cell</option>
												</select>
											</div>
										</div>

										<div class="col-sm-4">
											<label class='required'>Date On Air-BTS
												Type-Expansion 1/MBC Cabinet</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
												
												</div>
												<input class="form-control"id="networkdateonair1" type="date">
											</div>
										</div>
										<div class="col-sm-4">
											<label class='required'>Date On Air-BTS
												Type-Expansion 2/MBC Cabinet</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control" id="networkdateonair2"type="date">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Swap (Y/N)</label> <select
													id="networkswap1" class="form-control select2">
													<option value="Y"
														<c:if test = "${networkswap1 =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${networkswap1 =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>
	
										<div class="col-sm-4">
											<label class='required'>Swap Date</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control" id="networkswapdate" type="date">
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
												id="networksiteID" readonly value="${SiteID}"
												class="form-control text-input" />
										</div>
									</div>
								<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Site Name</label> <input type="text"
												id="networksiteName" readonly value="${SiteName}"
												class="form-control text-input" />
										</div>
									</div>
								
								
								
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Longitude</label> <input type="text"
												id="networklong" readonly value="${longitude}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Latitude</label> <input type="text"
												id="networklat" readonly value="${Latitude}"
												class="form-control text-input" />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label class='required'>Site Mode</label> <input type="text"
												id="networksitemode" readonly value="${siteMode}"
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
													id="networkexistNewTown" class="form-control select2">
													<option value="E"
														<c:if test = "${networkexistNewTown =='E'}"> selected </c:if>>E</option>
													<option value="NT"
														<c:if test = "${networkexistNewTown =='NT'}"> selected </c:if>>NT</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Showcase / Non Showcase
													(SC/NSC)</label> <select id="networkshowcaseNonShowcase"
													class="form-control select2">
													<option value="SC"
														<c:if test = "${networkshowcaseNonShowcase =='SC'}"> selected </c:if>>SC</option>
													<option value="NSC"
														<c:if test = "${networkshowcaseNonShowcase =='NSC'}"> selected </c:if>>NSC</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="networksitePrincipallabel">Site
													Principal Owner (Toco Name)</label> <input id="networksitePrincipleOwner"
													type="text" class="form-control"
													value="${networksitePrincipleOwner}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="towerCoIdlabel">Tower Co
													ID</label> <input type="text" id="networktowerCoId" class="form-control"
													value="${networktowerCoId}" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Tower Type </label> <select
													id="networktowerType" class="form-control select2">
													<option value="GBT"
														<c:if test = "${networktowerType =='GBT'}"> selected </c:if>>GBT</option>
													<option value="RTT"
														<c:if test = "${networktowerType =='RTT'}"> selected </c:if>>RTT</option>
													<option value="RTP"
														<c:if test = "${networktowerType =='RTP'}"> selected </c:if>>RTP</option>
													<option value="Wall Mounted"
														<c:if test = "${networktowerType =='Wall Mounted'}"> selected </c:if>>Wall
														Mounted</option>
													<option value="IBS"
														<c:if test = "${networktowerType =='IBS'}"> selected </c:if>>IBS</option>
													<option value="GBP"
														<c:if test = "${networktowerType =='GBP'}"> selected </c:if>>GBP</option>
													<option value="COW"
														<c:if test = "${networktowerType =='COW'}"> selected </c:if>>COW</option>
													<option value="NOW"
														<c:if test = "${networktowerType =='NOW'}"> selected </c:if>>NOW</option>
													<option value="GBT + Revamp"
														<c:if test = "${networktowerType =='GBT + Revamp'}"> selected </c:if>>GBT
														+ Revamp</option>
													<option value="RTT + Revamp"
														<c:if test = "${networktowerType =='RTT + Revamp'}"> selected </c:if>>RTT
														+ Revamp</option>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="networktowerHeightlabel">Tower
													Height (m)</label> <input type="number" min='0' id="networktowerHeight"
													class="form-control" value="${towerHeight}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="networkbuildingHeightlabel">Building
													Height (m)</label> <input type="number" min='0' id="networkbuildingHeight"
													class="form-control" value="${buildingHeight}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Shared / None Shared</label> <select
													id="networksharedNonShared" class="form-control select2">
													<option value="Shared"
														<c:if test = "${networksharedNonShared =='Shared'}"> selected </c:if>>Shared</option>
													<option value="Non-Shared"
														<c:if test = "${networksharedNonShared =='Non-Shared'}"> selected </c:if>>Non-Shared</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>ICR category (ICR/MORAN)</label> <select
													id="networkicrCategory" class="form-control select2">
													<option value="NO"
														<c:if test = "${networkicrCategory =='NO'}"> selected </c:if>>NO</option>
													<option value="ICR"
														<c:if test = "${networkicrCategory =='ICR'}"> selected </c:if>>ICR</option>
													<option value="MORAN"
														<c:if test = "${networkicrCategory =='MORAN'}"> selected </c:if>>MORAN</option>
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












</div>