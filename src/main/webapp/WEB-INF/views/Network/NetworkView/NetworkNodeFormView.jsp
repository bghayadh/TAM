<div style="padding-right: 10px;padding-left: 10px;padding-top: 10px;padding-bottom: 0px;">











<div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:190px;" class="input-group-text" style="color:green;">Node
                                ID
                                   </span>
                                <input type="text" id="networknodeid" value="${NetworkNodeID}" class="form-control text-input"
                                    readonly />
                            </div>
                        </div>
                    </div>


                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:190px;" class="input-group-text">Node Name</span>
                                <input type="text" id="networknodename" value="${NetworkNodeName}"
                                    class="form-control text-input"  readonly/>
                            </div>


                        </div>
                    </div>

                    <div class="pad col-md-3 hide-row"></div>
                    <div class="col-md-3 nextprvItems">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span style="width:200px;" class="input-group-text">Other Nodes</span>
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
                                    onclick='window.location.href = "${pageContext.request.contextPath}/NodeListView"'
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
			<ul class="nav nav-tabs" id="custom-tabs-all-tab" role="tablist"
				style="background-color: #00757c; margin-top: 0px;">

				<li class="nav-item"><a class="nav-link active"
					id="custom-tabs-site-tab" data-toggle="tab"
					href="#custom-tabs-site" role="tab"
					aria-controls="custom-tabs-site" aria-selected="true"
					style="color: gold;">Node Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-node-tab" data-toggle="tab"
					href="#custom-tabs-node" role="tab"
					aria-controls="custom-tabs-node" aria-selected="false"
					style="color: gold;">Cell Information</a></li>

				<li class="nav-item"><a class="nav-link"
					id="custom-tabs-cell-tab" data-toggle="tab"
					href="#custom-tabs-cell" role="tab"
					aria-controls="custom-tabs-cell" aria-selected="false"
					style="color: gold;">Site Information</a></li>

                 <li class="nav-item"><a class="nav-link"
					id="custom-tabs-cabinet-tab" data-toggle="tab"
					href="#custom-tabs-cabinet" role="tab"
					aria-controls="custom-tabs-cabinet" aria-selected="false"
					style="color: gold;">Cabinet</a></li>
					
					
                   <li class="nav-item"><a class="nav-link"
					id="custom-tabs-subrack-tab" data-toggle="tab"
					href="#custom-tabs-subrack" role="tab"
					aria-controls="custom-tabs-subrack" aria-selected="false"
					style="color: gold;">Subrack</a></li>
				
                   <li class="nav-item"><a class="nav-link"
					id="custom-tabs-slot-tab" data-toggle="tab"
					href="#custom-tabs-slot" role="tab"
					aria-controls="custom-tabs-slot" aria-selected="false"
					style="color: gold;">Slot</a></li>
					
                     <li class="nav-item"><a class="nav-link"
					id="custom-tabs-board-tab" data-toggle="tab"
					href="#custom-tabs-board" role="tab"
					aria-controls="custom-tabs-board" aria-selected="false"
					style="color: gold;">Board</a></li>
					
                    <li class="nav-item"><a class="nav-link"
					id="custom-tabs-port-tab" data-toggle="tab"
					href="#custom-tabs-port" role="tab"
					aria-controls="custom-tabs-port" aria-selected="false"
					style="color: gold;">Port</a></li>
					
                    <li class="nav-item"><a class="nav-link"
					id="custom-tabs-antenna-tab" data-toggle="tab"
					href="#custom-tabs-antenna" role="tab"
					aria-controls="custom-tabs-antenna" aria-selected="false"
					style="color: gold;">Antenna</a></li>




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
								<!--**************************** node tab **************************** -->
								
								<!-- ****************************** Active Parameters ************************************** -->
								<hr>
								<h4>Active Parameters</h4>
								<hr>
								<div class="row">
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>SRAN Node (Yes/No)</label> <input
												type="text" id="networksrannode" readonly value="${sran}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type-Main Cabinet</label> <input
												type="text" id="networkbtsmain" readonly value="${btsType}"
												class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="networkbtstype1" readonly
												value="${btsType1}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>BTS Type Expansion 2/MBC
												Cabinet</label> <input type="text" id="networkbtstype2" readonly
												value="${btsType2}" class="form-control text-input" />
										</div>
									</div>
								

								
									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Band BTS Type Expansion 1/MBC
												Cabinet</label> <input type="text" id="networkbandbts1" readonly
												value="${BandBts1}" class="form-control text-input" />
										</div>
									</div>

									<div class="col-md-4">
										<div class="form-group">
											<label class='required'>Band BTS Type Expansion 2/MBC
												Cabinet</label> <input type="text" id="networkbandbts2" readonly
												value="${Bandbts2}" class="form-control text-input" />
										</div>
									</div>
                                         <div class="col-md-4">
										<div class="form-group">
											<label class='required'>Swap Date</label> <input type="text"
												id="networkswapdate" readonly value="${swapDate}"
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
												id="networknodetechnology" readonly value="${Technology}"
												class="form-control text-input" />
										</div>
									</div>
									
								
                </div>
            </div>
        </div>
      
    </div>

		</div>						
								<hr>
								
								
								

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
												<input class="form-control" id="networkdateonairbts1" type="date">
											</div>
										</div>
										<div class="col-sm-4">
											<label class='required'>Date On Air-BTS
												Type-Expansion 2/MBC Cabinet</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control" id="networkdateonairbts2"type="date">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-3">
											<div class="form-group">
												<label class='required'>Swap (Y/N)</label> <select
													id="networkswap" class="form-control select2">
													<option value="Y"
														<c:if test = "${swap =='Y'}"> selected </c:if>>Yes</option>
													<option value="N"
														<c:if test = "${swap =='N'}"> selected </c:if>>No</option>
												</select>
											</div>
										</div>

										<div class="col-sm-4">
											<label class='required'>Swap Date</label>
											<div class="input-group mb-3">

												<div class="input-group-prepend">
													
												</div>
												<input class="form-control"id="networkswapdate" type="date">
											</div>
										</div>
									</div>
								</form>
							</div>

							<div class="tab-pane fade" id="custom-tabs-node" role="tabpanel"
								aria-labelledby="custom-tabs-node-tab">
								<!--**************************** second tab **************************** -->

								<!-- ****************************** Active Parameters ************************************** -->
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
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Cell ID <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Cell Name  <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Technologies <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
          
        </tr>
        <tr id="search_by_column">
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
  <div class="row" >
  <footer >
  <div id="pagination" class="pagination" style=" margin-left:800px; ">
                <div class="col_md_2">
                <p class="pagination-label"style="font-size:18px; color:black;  display: block; " >
                    Viewing <span style="font-size:18px; color:black;  display: inline;">1-10</span> of <span style="font-size:18px; color:black; display: inline; ">36</span>
                </p>
                </div>
             
              <a href="#" class="previous"  style="font-size:18px; color:black; text-decoration: none;    " onmouseover="changecolor3(this)" onmouseout="returncolor3(this)" >Previous</a>
                <a href="#" class="next round"  style="font-size:18px; color:black; text-decoration: none;  float:right;"onmouseover="changecolor3(this)" onmouseout="returncolor3(this)">Next</a>
                </div>  
           
</footer>
</div>
									
									
									
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
												id="networklat" readonly value="${latitude}"
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
													value="${sitePrincipleOwner}" />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label class="required" id="towerCoIdlabel">Tower Co
													ID</label> <input type="text" id="networktowerCoId" class="form-control"
													value="${towerCoId}" />
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
												<label class="required" id="buildingHeightlabel">Building
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
							</div>




<div class="tab-pane fade" id="custom-tabs-cabinet" role="tabpanel"
								aria-labelledby="custom-tabs-cabinet-tab">
								<!--**************************** second tab **************************** -->

								<!-- ****************************** Active Parameters ************************************** -->
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
    <table id="CabinetTable"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
    
        <tr class="header">
        <th style="text-align: center; width: 5% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Position <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Type <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Model <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Serial Number<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="4,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Other<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="5,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
        </tr>
        <tr id="search_by_column">
            <th></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Position." onkeyup="filterData(this,1,'CabinetTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Type.." onkeyup="filterData(this,2,'CabinetTable');"></th>
             <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Model.." onkeyup="filterData(this,3,'CabinetTable');"></th>
              <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Serial Number.." onkeyup="filterData(this,4,'CabinetTable');"></th>
               <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Other.." onkeyup="filterData(this,5,'CabinetTable');"></th>
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
							
							
							
							
							<div class="tab-pane fade" id="custom-tabs-subrack" role="tabpanel"
								aria-labelledby="custom-tabs-subrack-tab">
								<!--**************************** second tab **************************** -->

								<!-- ****************************** Active Parameters ************************************** -->
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
    <table id="SubrackTable"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
    
        <tr class="header">
        <th style="text-align: center; width: 5% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Position <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Type <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Model <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Serial Number<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="4,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Other<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="5,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
        </tr>
        <tr id="search_by_column">
            <th></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Position.." onkeyup="filterData(this,1,'SubrackTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Type.." onkeyup="filterData(this,2,'SubrackTable');"></th>
             <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Model.." onkeyup="filterData(this,3,'SubrackTable');"></th>
              <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for SeriaL Number.." onkeyup="filterData(this,4,'SubrackTable');"></th>
               <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Other." onkeyup="filterData(this,5,'SubrackTable');"></th>
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


							</div>
							
							
							
							
							<div class="tab-pane fade" id="custom-tabs-slot" role="tabpanel"
								aria-labelledby="custom-tabs-slot-tab">
								<!--**************************** second tab **************************** -->

								<!-- ****************************** Active Parameters ************************************** -->
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
    <table id="Slot"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
    
        <tr class="header">
        <th style="text-align: center; width: 5% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Position <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Type <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Model <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Serial Number<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="4,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Other<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="5,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
        </tr>
        <tr id="search_by_column">
            <th></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Position..." onkeyup="filterData(this,1,'SlotTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Type.." onkeyup="filterData(this,2,'SlotTable');"></th>
             <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Model.." onkeyup="filterData(this,3,'SlotTable');"></th>
              <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Serial Number.." onkeyup="filterData(this,4,'SlotTable');"></th>
               <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Other .." onkeyup="filterData(this,5,'SlotTable');"></th>
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

							</div>
							
							
							
							
							
							<div class="tab-pane fade" id="custom-tabs-board" role="tabpanel"
								aria-labelledby="custom-tabs-board-tab">
								<!--**************************** second tab **************************** -->

								<!-- ****************************** Active Parameters ************************************** -->
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
    <table id="BoardTable"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
    
        <tr class="header">
        <th style="text-align: center; width: 5% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Position <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Type <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Model <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Serial Number<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="4,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Other<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="5,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
        </tr>
        <tr id="search_by_column">
            <th></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Position.." onkeyup="filterData(this,1,'BoardTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Type.." onkeyup="filterData(this,2,'BoardTable');"></th>
             <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Model.." onkeyup="filterData(this,3,'BoardTable');"></th>
              <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Serial Number.." onkeyup="filterData(this,4,'BoardTable');"></th>
               <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Other.." onkeyup="filterData(this,5,'BoardTable');"></th>
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


							</div>
							
							
							
							
							
							
							
							
							
							
							
							<div class="tab-pane fade" id="custom-tabs-port" role="tabpanel"
								aria-labelledby="custom-tabs-port-tab">
								<!--**************************** second tab **************************** -->

								<!-- ****************************** Active Parameters ************************************** -->
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
    <table id="PortTable"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
    
        <tr class="header">
        <th style="text-align: center; width: 5% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Position <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Type <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Model <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Serial Number<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="4,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Other<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="5,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
        </tr>
        <tr id="search_by_column">
            <th></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Position.." onkeyup="filterData(this,1,'PortTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Type.." onkeyup="filterData(this,2,'PortTable');"></th>
             <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Model.." onkeyup="filterData(this,3,'PortTable');"></th>
              <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Serial Number.." onkeyup="filterData(this,4,'PortTable');"></th>
               <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Other.." onkeyup="filterData(this,5,'PortTable');"></th>
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


							</div>
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							<div class="tab-pane fade" id="custom-tabs-antenna" role="tabpanel"
								aria-labelledby="custom-tabs-antenna-tab">
								<!--**************************** second tab **************************** -->

								<!-- ****************************** Active Parameters ************************************** -->
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
    <table id="AntennaTable"  class="table table-striped table-bordered table-sm" style="display:block; height:245px; overflow-y: auto;">
    
        <tr class="header">
        <th style="text-align: center; width: 5% ; padding-top:15px;"><input
															id="multiselect" name="select_all" value="1"
															type="checkbox"></th>	
								 
          <th style="width:5%; text-align:center; font-size:20px; padding-top:10px;   ">Position <button style="font-size:10px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Type <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Model <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Serial Number<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="4,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          <th style="width:5%; text-align:center; font-size:20px;  padding-top:10px; ">Other<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="5,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:30px;color:black"></i></button></th>
          
        </tr>
        <tr id="search_by_column">
            <th></th>
            <th><input type="text"style="width:100%; text-align:center" id="email_filter"  placeholder="Search for Position.." onkeyup="filterData(this,1,'AntennaTable');"></th>
            <th><input type="text"style="width:100%;  text-align:center"  id="country_filter" placeholder="Search for Type.." onkeyup="filterData(this,2,'AntennaTable');"></th>
             <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Model." onkeyup="filterData(this,3,'AntennaTable');"></th>
              <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Serial Number.." onkeyup="filterData(this,4,'AntennaTable');"></th>
               <th><input type="text" style="width:100%; text-align:center" id="name_filter" placeholder="Search for Other.." onkeyup="filterData(this,5,'AntennaTable');"></th>
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


							</div>



						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- end container -->
	</div>
	
	<script> 
$(document).ready(function() {


	  var pagination = new Pagination({id:'pagination', tableID:'gridTable', noOfRows:10});
	 

	
	/*var delList = ${delList}; var writeForm = ${writeForm}; var addForm = ${addForm};
	console.log("delList is " +delList+" writeForm is " +writeForm+" addForm is " +addForm);
	var deleteButton = '<button type="button" id="deleteButton" class="btn btn-primary BtnActive"><i class="fa fa-trash"></i> Delete</button>';
	var addButton = '<button type="button" id="saveButton" onclick=\'window.location.href = "${pageContext.request.contextPath}/PurchaseReqFormView?type=addNew"\'class="btn btn-primary BtnActive"><i class="fa fa-plus"></i> Add</button>';
	
	if(delList == 1){
		$("#Buttons").prepend(deleteButton);
	}
	if(addForm == 1){
		$("#Buttons").append(addButton);
	}
	var bassamData = ${ListGridTable};
	var code='';
	var slct = [];
	//console.log("bassamData is " +bassamData + " first item code is " +bassamData[0]);
	var table = $('#example').DataTable( {
	"bProcessing": true,
    "aaData": bassamData,// <-- your array of objects
    "aoColumns":[
    	{
        	"mData": null,
          	"bSortable": false,
	        "mRender" : function ( bassamData, type, full ) { 
	            	return ''}    		
    	}, 
        
        {
            "aTargets":[ 1 ],
            "sType": "String",
            "mRender": function(url, type, full) {
           //console.log('chk data : '+full[3]);
           // how to pass parameter to ItemFormView
           		var value = "";
           		if( writeForm == 1){
           			value = '<a href="'+ '${pageContext.request.contextPath}/PurchaseReqFormView?ID='+full[1] +'&supplier='+full[2] +'">' + url + '</a>';
           		}
           		else if( writeForm == 0){
           			value = url;
           		}
            	return value;
				}
   
       },
      {
          "mData":[2],
         },
         {
           "aTargets":[ 3 ]
         , "sType": "String"
         , "mRender": function(url, type, full) {
            //console.log('chk data : '+full[3]);
            if (full[3] == "Transport2") {return    '<a href="'+ '/Inventory' +'">' + url + '</a>';}
            else {return full[3]; }
           }
         },
         {
     		"aTargets":[ 4 ]  
        },
        {
    		"aTargets":[ 5 ]   
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
	
	
	$('#example_paginate').click( function() {
		if (table.rows( '.selected', { page: 'current' }).count() != table.rows({ page: 'current' }).count()) {
			$('#multiselect').prop('checked', false);
            $('#multiselect').removeClass("selected");
            console.log(table.rows( '.selected', { page: 'current' }).count());
      	}
		else {
      		$('#multiselect').prop('checked', true);
      		$('#multiselect').addClass("selected");
      		console.log('array is: ' +slct);
      	}
	});
	
	$('#multiselect').on('click', function() {
	    var num = table.rows({ page: 'current' }).count();   
	    if ($('#multiselect').hasClass("selected")) {
	 
	    	for(i=0; i<num; i++){
				x=i;
				table.row(':eq('+x+')', { page: 'current' }).deselect();
				var pos = table.row(':eq('+x+')', { page: 'current' }).index();
	    		var row = table.row(pos).data();
				var str= new Array();
	      		str=row
	      		code=str[1];
	      		removeItem = code;
	      		console.log('item to remove: '+code);
	      		slct = jQuery.grep(slct, function(value) {
					return value != removeItem;
	      		});
				}
				$('#multiselect').prop('checked', false);
				$('#multiselect').removeClass("selected");
				slct = slct.filter(function(elem, index, self) {
					return index === self.indexOf(elem);
				});
				console.log('array is: ' +slct);
	    	
	    }
	    else {
	    	
	    	var i;
			var x=0;
			for(i=0; i<num; i++){
			x=i;
			table.row(':eq('+x+')', { page: 'current' }).select();
			var pos = table.row(':eq('+x+')', { page: 'current' }).index();
			var row = table.row(pos).data();
			var str= new Array();
	      	str=row
	      	code=str[1];
	      	slct.push(code);
	      	console.log('array is: ' +slct);
			}
			$('#multiselect').prop('checked', true);
			$('#multiselect').addClass("selected");
			slct = slct.filter(function(elem, index, self) {
				return index === self.indexOf(elem);
			});
			console.log('array is: ' +slct);
	    }
			
	});
	
	
     $('#example tbody').on( 'click', 'tr', function () {
     	var $checkbox = $(this).toggleClass('selected')
		if($(this).hasClass('selected')) {
    		var pos = table.row(this).index();
    		var row = table.row(pos).data();
    		var str= new Array();
		    str=row
		    code=str[1];
		    slct.push(code);
		    console.log(code);
		    slct = slct.filter(function(elem, index, self) {
				return index === self.indexOf(elem);
			});
		    console.log('array is: ' +slct);
		}
		
		else { 
		    $checkbox.removeAttr('checked');
		    console.log( 'unselected NO' );
		    var pos = table.row(this).index();
    		var row = table.row(pos).data();
    		var str= new Array();
		    str=row
		    code=str[1];
		    removeItem = code;
		    console.log('item to remove: '+code + ' array is: ' +slct);
		    slct = jQuery.grep(slct, function(value) {
        		return value != removeItem;
      		});
		    slct = slct.filter(function(elem, index, self) {
				return index === self.indexOf(elem);
			});
      		console.log('array after remove is: '+slct);
		}

		if (table.rows( '.selected', { page: 'current' }).count() !== table.rows({ page: 'current' }).count()) {
			$('#multiselect').prop('checked', false);
            $('#multiselect').removeClass("selected");
            console.log('array is: ' +slct+ ' array length is: ' +slct.length);
      	}
		else {
      		$('#multiselect').prop('checked', true);
      		$('#multiselect').addClass("selected");
      		console.log('array is: ' +slct);
      	}
      	console.log('array is: ' +slct);
         
		
    });
				
				
 	$("#deleteButton").click(  function() {
		console.log('delete now');
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/PurchaseReqListViewDelete",
			dataType : "json",
			data : {
				"ID" : slct
			},
			success : function(data) {
				//console.log("The returned data is " +data.BassamTest);
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		console.log('slct is: ' + slct);
	 
 	});
*/
});


</script>
	
	
	


</div>