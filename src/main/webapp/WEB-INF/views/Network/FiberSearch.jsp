<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

</head>









<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
<style>




.panel-body-info {
	border: 2px grey solid;
	padding: 4 18px;
	border-radius: 10px;
}



#collapseOne {
	padding-bottom: 0.9%;
	padding-top: 0%;
	padding-right: 7%;
	height: 500px;
	width: 100%;
	float: right;
}
 .nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
.custom-title a {
    position: relative;
    top: -9px; /* Adjust this value to push the "Customer Info" text up */
}

</style>


<body>
	<div class="container">
		<div id="fiberCitySearch" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered" >
				<div class="modal-content" style="width: 1000px; height: 1000px;">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="fiberSearchHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Fiber Search</h5>
						<div style="float: right;">
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">	<i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="citySearch" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link" id="fiber-search-tab" style="color: gold;" href="#custom-tabs-FindACable" data-toggle="tab" role="tab" aria-controls="fiber" aria-selected="true">Find a cable</a></li>
							<li class="nav-item"><a class="nav-link" id="custom-tabs-filter-tab" data-toggle="tab" href="#custom-tabs-filter" role="tab" aria-controls="#custom-tabs-filter" aria-selected="false" style="color: gold;">Filter</a></li>
							<li class="nav-item"><a class="nav-link" id="closest-search-tab" href="#closest" style="color: gold;" data-toggle="tab" role="tab" aria-controls="closest" aria-selected="false">Find Nearest Points</a></li>
						    <li class="nav-item"><a class="nav-link" id="MultyClosest-search-tab" href="#MultyClosest" style="color: gold;" data-toggle="tab" role="tab" aria-controls="closest" aria-selected="false">Find Nearest For Multi Points</a></li> 
							<li class="nav-item"><a class="nav-link" id="connectedSearch-tab" style="color: gold;" data-toggle="tab" href="#connectedS" role="tab" aria-controls="connectedSearch" aria-selected="false">Connected</a></li>
						</ul>
						<div class="tab-content" style="min-height: 300px">
							<div class="tab-pane fade" id="custom-tabs-filter" role="tabpanel" aria-labelledby="custom-tabs-filter-tab">
								<p></p>
								<div>
									<div class="container-fluid">
										<div id="filterSection"></div>
										<button id="submitFilter" class="btn btn-primary" style="color: white; font-size: 13px; height: 35px; margin-left: 5px;">Submit</button>
										<input type="checkbox"	style="margin-left: 20px; transform: translateY(5%); top: 50%;" id="currentChecked" Checked /> Current
										<input type="checkbox" style="margin-left: 20px; transform: translateY(5%); top: 50%;" id="projectChecked" /> Project 
										<input type="checkbox" style="margin-left: 20px; transform: translateY(5%); top: 50%;" id="selectedFields" /> Only Selected Fields
										<input type='checkbox' id="getRelatedPointsFilter"  style="margin-left: 20px; transform: translateY(5%); top: 50%;"  value='0'> Get Related Points
										</div><p></p></div></div>
							<div class="tab-pane" role="tabpanel" aria-labelledby="fiber-tab"
								id="custom-tabs-FindACable">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-5" id="fromCityDiv">
											<div class="form-group">
												<div class="input-group-prepend pac-container">
													<span style="font-size: 14px;" class="input-group-text"><b>From District</b></span> <input type="text" id="srcCitySearchNames" class="form-control text-input" placeholder="Enter a location" />
												</div></div></div>
										<div class="col-sm-5" id="toCityDiv">
											<div class="form-group">
												<div class="input-group-prepend pac-container">
													<span style="font-size: 14px;" class="input-group-text"><b>To District</b></span> <input type="text" id="dstCitySearchNames" class="form-control text-input" />
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<button id="viewFiberPath" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px;">View Fibers</button>
											</div></div></div><p></p><br>
									<div class="row" style="height: 500px;">
										<fieldset class="field_set">
											<legend style="width: auto;" class="fieldset_legend">Fiber Pathes:</legend>
											<table id="fiberCbleFullPath">
												<tr>
													<th style="width: 5%;" class="row-pad">Fiber ID</th>
													<th style="width: 5%;">FiberName</th>
													<th style="width: 5%;">Source</th>
													<th style="width: 5%;">Destination</th>
													<th style="width: 5%;">Distance</th>
												</tr>
												<tbody id="searchFiberTBody"></tbody></table></fieldset></div></div></div>
							<div id="closest" class="tab-pane" role="tabpanel" aria-labelledby="closest-search-tab"><p></p>
								<div class="container-fluid" >
									<div class="row">
									<div class="col-sm-2" style="min-width:150px;">
											<div class="form-group">
												<div class="input-group-prepend" >
													<span style="min-width: 90px; font-size: 12px;  width: 100%;" class="input-group-text"><b>Circle Range </b> 
													<input type='checkbox' id="circleRange" style='position: relative; margin-left: 10px' checked  ></span>
												</div></div></div>
									<div class="col-sm-5" style="min-width: 200px;">
                                   <div class="form-group">
                                <div class="input-group-prepend">
                               <span style="display: flex; align-items: center; justify-content: center; min-width: 90px; font-size: 12px; width: 100%; text-align: center;" class="input-group-text"><b>Start/End Coordinate</b>
                          <input type='checkbox' id="StartEnd" style='position: relative; margin-left: 5px;'>
                        </span>
                          </div>
                           </div>
                          </div>

									<div class="col-sm-5" style="min-width: 200px;">
    <div class="form-group">
        <div class="input-group-prepend">
            <span style="display: flex; align-items: center; justify-content: center; min-width: 90px; font-size: 12px; width: 100%;" class="input-group-text"><b>Get Related Points</b>
                <input type='checkbox' id="getRelatedPoints" style='position: relative; margin-left: 5px;' value='0'>
            </span>
        </div>
    </div>
</div>
	
									</div>
									<div class="row" id="row_circleRange">
										<div class="col-sm-2" style="" id="setCoordDiv">
											<div class="form-group">
												<button id="setCoordBtn" title="Get Coordinate From Map" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">Set Coordinate</button>
											</div>
										</div>
										<div class="col-sm-5" style="" id="closestLongDiv">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="font-size: 12px;" class="input-group-text"><b>Longitude</b></span>
													<input type="text" id="closestLongPoint" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-5" style="" id="closestLatDiv">
											<div class="form-group" >
												<div class="input-group-prepend">
													<span style="font-size: 12px;" class="input-group-text"><b>Latitude</b></span>
													<input type="text" id="closestLatPoint"	class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-2" style="">
											<div class="form-group">
												<button id="viewNearestp" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">View Nearest</button>
											</div>
										</div>
										<div class="col-sm-5" id="closestDistanceRange">
											<div class="form-group">
												<div class="input-group-prepend" style="">
													<span style="font-size: 12px;" class="input-group-text"><b>Distance Range</b></span> <input type="text" id="closestDisRange" class="form-control text-input" />
												</div>
											</div>
										</div>
																			<div class="col-sm-5" id="NoOfPoints">
											<div class="form-group">
												<div class="input-group-prepend" style="">
													<span style="font-size: 12px;" class="input-group-text"><b>No Of Points</b></span> <select class="form-control" name="noP" id="noP">
														<option value="">All</option>
														<option value="5">5</option>
														<option value="10">10</option>
														<option value="15">15</option>
													</select>
												</div>
											</div>
										</div>																		
										
										</div>
										<div class="row" id="row_setStart">
										<div class="col-sm-2" style="display:none;" id="setStartPointDiv">
											<div class="form-group">
												<button id="setStartPointBtn"	title="Get Start Point Coordinate From Map"	class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">Set Start Point</button>
											</div>
										</div>
										<div class="col-sm-5" style="display:none;" id="StartLongDiv">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Start Longitude</b></span>
													<input type="text" id="startLongPoint" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-5" style="display:none;" id="StartLatDiv">
											<div class="form-group" >
												<div class="input-group-prepend">
													<span style="font-size: 12px;" class="input-group-text"><b>Start Latitude</b></span>
													<input type="text" id="startLatPoint" class="form-control text-input" />
												</div>
											</div>
										</div>
										</div>
										<div class="row">
										<div class="col-sm-2" style="display:none;" id="setEndPointDiv">
											<div class="form-group">
												<button id="setEndPointBtn" title="Get End Point Coordinate From Map" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">Set End Point</button>
											</div>
										</div>
										<div class="col-sm-5" style="display:none;" id="EndLongDiv">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>End Longitude</b></span>
													<input type="text" id="endLongPoint" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-5" style="display:none;" id="EndLatDiv">
											<div class="form-group" >
												<div class="input-group-prepend">
													<span style="font-size: 12px;" class="input-group-text"><b>End Latitude</b></span>
													<input type="text" id="endLatPoint" class="form-control text-input" />
												</div>
											</div>
										</div>
										</div>
								
										
							
							
																				
										<div class="wrapper" style="margin-top: -10px;">
					<div class="panel-group" id="accordion" role="tablist"
						aria-multiselectable="true">
						<div class="panel panel-default" style="margin-bottom: 3px;">

							<div class="panel-heading" role="tab" id="headingOne">
    <h4 class="panel-title custom-title">
        <a role="button" data-toggle="collapse"
            data-parent="#accordion" href="#collapseThree"
            aria-expanded="true" aria-controls="collapseThree">Customer Info</a>
    </h4>
</div>



							<div id="collapseThree" class="panel-collapse collapse show"
								role="tabpanel" aria-labelledby="headingOne">
								<div class="panel-body-info"
									style='height: 100%; width: 100%; padding-top: 0%;'>
									<div class="card-body" style="width: 100%;">
									

									<div class="row">
									 
									 <div class="col-sm-4" id="customerNameID">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Customer ID</b></span>
													<input type="text" id="customerDetails" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-4" id="customerNameID">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Customer Name</b></span>
													<input type="text" id="customerName" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-4" id="serviceAppNum">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Service App No</b></span>
													<input type="text" id="serviceAppNo" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-4" id="survNearestPt">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Nearest Pt ID</b></span>
													<input type="text" id="surveyNearestPt" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-sm-6" id="survNearestPt">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Nearest Pt Distance</b></span>
													<input type="text" id="surveyNearestPtDis" class="form-control text-input" readonly />
												</div>
											</div>
										</div>		
																																					
										</div>
	<div class="row">
	
		                             <div class="col-sm-4" id="serviceRef">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Service ID</b></span>
													<input type="text" id="serviceReferenceId" class="form-control text-input" />
												</div>
											</div>
										</div>	
										<div class="col-sm-4" id="serviceRef">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Service Ref</b></span>
													<input type="text" id="serviceReference" class="form-control text-input" />
												</div>
											</div>
										</div>	
											<div class="col-sm-4" id="price">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>UPrice</b></span>
													<input type="text" id="unitPrice" class="form-control text-input" />
												</div>
											</div>
										</div>	
										<div class="col-sm-4" id="surveyId">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Survey ID</b></span>
													<input type="text" id="surveyID" class="form-control text-input" readonly />
												</div>
											</div>
										</div>	
										<div class="col-sm-3" id="saveSurv">
											<div class="form-group">
												<button id="saveSurvey" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">Save Survey</button>
											</div>
										</div>
											<div class="col-sm-3" id="updateMysd">
											<div class="form-group">
												<button id="updateOnMySD" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;" disabled>Update on MySD</button>
											</div>
										</div>	
																		
							</div>
										
									

								
									</div>
								</div>
							</div>
					
					</div>
				</div>								
							</div>
						<div class="row">
							<div class="col-sm-8" >
							<div class="form-group">
								<div id="updateonMySDResult" style="display: none;"></div>
							</div>
							</div>
							<div class="col-sm-3" >
										<div class="form-group">
												<div class="input-group-prepend"><div id="saveSurveyLoaderDiv" style="display: none;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /> Saving Survey ...</div>
											</div>
											<div class="input-group-prepend"><div id="updatingOnMySDLoaderDiv" style="display: none;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /> Updating on MySD ...</div>
											</div>												
										</div>
									</div>	
							</div>
									<ul class="nav nav-tabs" id="myTab" role="tablist"
										style="background-color: #00757C;">
										<li class="nav-item"><a class="nav-link active"
											id="manPoint-tab" style="color: gold;" data-toggle="tab"
											href="#manP" role="tab" aria-controls="fiber"
											aria-selected="true">Manhole</a></li>
										<li class="nav-item"><a class="nav-link "
											id="handPoint-tab" style="color: gold;" data-toggle="tab"
											href="#handP" role="tab" aria-controls="source_dest"
											aria-selected="false">Handhole</a></li>
										<li class="nav-item"><a class="nav-link "
											id="dboardPoint-tab" style="color: gold;" data-toggle="tab"
											href="#dboardP" role="tab" aria-controls="conduit"
											aria-selected="false">Distribution Board</a></li>
										<li class="nav-item"><a class="nav-link "
											id="fPaths-tab" style="color: gold;" data-toggle="tab"
											href="#fPaths" role="tab" aria-controls="conduit"
											aria-selected="false">Fiber Paths</a></li>
										<li class="nav-item"><a class="nav-link "
											id="nodePoint-tab" style="color: gold;" data-toggle="tab"
											href="#nodeP" role="tab" aria-controls="nodes"
											aria-selected="false">Node</a></li>
									</ul>
									<div class="tab-content" style="min-height: 180px">
										
										<div class="tab-pane active" id="manP" role="tabpanel"
											aria-labelledby="manPoint-tab">
											<p></p>
											<div class="container-fluid" style="overflow-x: auto;">
												<div id="findNearestManRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstManhole" style="display: block; height: 500px; width: 100%; overflow-y: auto;" class="searchable sortable">
																												
																												 <thead>
																													<tr>
																													    <th style="min-width: 30px;"><input type="checkbox" id="selectAllManhole"></th>
																														<th style="min-width: 150px;">Manhole ID</th>
																														<th style="min-width: 150px;">ManholeName</th>
																														<th style="min-width: 100px;">Longitude</th>
																														<th style="min-width: 150px;">Latitude</th>
																														<th style="min-width: 100px;">Linear Distance(km)</th>
																														<th style="min-width: 80px;"colspan="2">Driving Distance(km)</th>
																														<th style="min-width: 80px;">Geo Distance(km)</th>
																														<th style="padding-left:10px;">Select Nearest</th>
																													</tr>
																													 </thead>
																													<tbody id="searchManhTBody">
																													</tbody>
																												</table>
																															
													
													</fieldset>
												</div>
											</div>
									        <div id="manholeTotal" Style="Padding-top: 15px;"><b>Total Manholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalManhole" name="totalManhole" readonly>	 </div>
										</div>
										<div class="tab-pane" id="handP" role="tabpanel"
											aria-labelledby="handPoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findNearestHandRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstHandhole" class="searchable sortable"
															style="display: block; height: 500px; overflow-y: auto;">
															 <thead>
															<tr>
																<th style="min-width: 30px;"><input type="checkbox" id="selectAllHandhole"></th>
																<th style="min-width: 150px;" >Handhole ID</th>
																<th style="min-width: 150px;">HandholeName</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Linear Distance(Km)</th>
																<th style="min-width: 100px;" colspan="2">Driving Distance(km)</th>
																<th style="min-width: 80px;">Geo Distance(km)</th>
																<th style="padding-left:10px;">Select Nearest</th>
																
															</tr>
															</thead>
															
															<tbody id="searchHanhTBody">
															</tbody>
														</table>
													</fieldset>

												</div>
											</div>
                                             <div id="handholeTotal" Style="Padding-top: 15px;"><b>Total Handholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalHandhole" name="totalDB" readonly>	 </div>
										</div>
										<div class="tab-pane" id="dboardP" role="tabpanel"
											aria-labelledby="dboardPoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findNearestDbRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstDB" style="display: block; height: 500px; overflow-y: auto;">
														 <thead>															
															<tr>
																<th style="min-width: 30px;"><input type="checkbox" id="selectAllDB"></th>
																<th style="min-width: 150px;" >DBoard ID</th>
																<th style="min-width: 150px;">DBoard Name</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Linear Distance(km)</th>
																<th style="min-width: 100px;" colspan="2">Driving Distance(km)</th>	
																<th style="min-width: 80px;">Geo Distance(km)</th>
																<th style="padding-left:10px;">Select Nearest</th>
																
															</tr>
															 </thead>															
															<tbody id="searchDBoardTBody">
															</tbody>
														</table>
													</fieldset>
												 </div>
											</div>
                                            <div id="DbTotal" Style="Padding-top: 15px;"><b>Total Distribution Boards:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDB" name="totalDB" readonly>										
                                            </div>
										</div>
										
										
										
										
										
										<div class="tab-pane" id="fPaths" role="tabpanel" aria-labelledby="fPaths-tab">
											<p></p>
											<div class="container-fluid">
											<div class="row" style="height: 180px;">
										<fieldset class="field_set">
											<legend style="width: auto;" class="fieldset_legend"></legend>
											<table id="nearestStrand" style="display: block; height: 170px; overflow-y: auto;">
												<tr>
													<th style="min-width: 150px;" class="row-pad">Strand ID</th>
													<th style="min-width: 150px;">StrandName</th>
													<th style="min-width: 350px;">Source</th>
													<th style="min-width: 350px;">Destination</th>
												</tr>
												<tbody id="nearStrandId">
												</tbody>
											</table>
										</fieldset>
									</div>
									<br>
									<div class="row" style="height: 180px;">
										<fieldset class="field_set">
											<legend style="width: auto;" class="fieldset_legend"></legend>
											<table id="nearestTube" style="display: block; height: 170px; overflow-y: auto;">
												<tr>
													<th style="min-width: 150px;" class="row-pad">Tube ID</th>
													<th style="min-width: 150px;">TubeName</th>
													<th style="min-width: 350px;">Source</th>
													<th style="min-width: 350px;">Destination</th>
												</tr>
												<tbody id="nearTubeId">
												</tbody>
											</table>
										</fieldset>
									</div>
									<br>
									<div class="row" style="height: 180px;">
										<fieldset class="field_set">
											<legend style="width: auto;" class="fieldset_legend"></legend>
											<table id="nearestFiber" style="display: block; height: 170px; overflow-y: auto;">
												<tr>
													<th style="min-width: 150px;" class="row-pad">Fiber ID</th>
													<th style="min-width: 150px;">FiberName</th>
													<th style="min-width: 350px;">Source</th>
													<th style="min-width: 350px;">Destination</th>
												</tr>
												<tbody id="nearFiberId"></tbody></table></fieldset></div></div></div>
								<div class="tab-pane" id="nodeP" role="tabpanel" aria-labelledby="nodePoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findNearestNodeRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstNode" class="searchable sortable"
															style="display: block; height: 500px; overflow-y: auto;">
															 <thead>
															<tr>
																<th style="min-width: 30px;"><input type="checkbox" id="selectAllNode"></th>
																<th style="min-width: 150px;" >Node ID</th>
																<th style="min-width: 150px;">Node Name</th>
																<th style="min-width: 150px;">Node Type</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Linear Distance(Km)</th>
																<th style="min-width: 100px;" colspan="2">Driving Distance(km)</th>
																<th style="min-width: 80px;">Geo Distance(km)</th>
																<th style="padding-left:10px;">Select Nearest</th>
																
															</tr></thead>
															<tbody id="searchNodeTBody"></tbody>
														</table>
													</fieldset>
												</div>
											</div>
                                             <div id="nodeTotal" Style="Padding-top: 15px;"><b>Total Nodes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalNode" name="totalNodes" readonly></div>
										</div>
										</div></div></div>
												
								<div id="MultyClosest" class="tab-pane" role="tabpanel" aria-labelledby="MultyClosest-search-tab"><p></p>
								<div class="container-fluid" >
									<div class="row">
									<div class="col-sm-2" style="min-width:150px;">
											<div class="form-group">
												<div class="input-group-prepend" >
													<span style="min-width: 90px; font-size: 12px;  width: 100%;" class="input-group-text"><b>Circle Range </b> 
													<input type='checkbox' id="circleRange_multy" style='position: relative; margin-left: 10px' checked  ></span>
												</div></div></div>
								
									<div  class="col-sm-1">
									<button   id="uncheckAll" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width:100px;">Uncheck All</button>
		</div>
									<div class="col-sm-3">
									<div class="form-group"><div class="input-group-prepend">
									
									</div></div></div></div>
								
										<div class="row">
										<div class="col-sm-2" style="">
											<div class="form-group">
												<button id="viewNeareMulty" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">View Nearest</button>
											</div>
										</div>
									 	 <div class="col-sm-5" id="closestDistanceRange_multy">
											<div class="form-group">
												<div class="input-group-prepend" style="">
													<span style="font-size: 12px;" class="input-group-text"><b>Distance Range</b></span> <input type="text" id="closestMultyDisRange" class="form-control text-input" />
												</div>
											</div>
										</div> 
										<div class="col-sm-5" id="NoOfPoints_multy">
											<div class="form-group">
												<div class="input-group-prepend" style="">
													<span style="font-size: 12px;" class="input-group-text"><b>No Of Points</b></span> <select class="form-control" name="noP_Multy" id="noP_Multy">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</div>
										</div>		
									</div>	
								<div class="container-fluid">
								
								
								
								<div class="row">
    <div  style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                    <b>By Site</b>
          <input type='checkbox' id=Site_Autocomplete_Multy value='0' style='position: relative; margin-left: 25px' class="auxPtAutocomplete"></span>
								       </span>
            </div>
        </div>
    </div>

    <div style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                    <b>By Client</b>
                    <input type="checkbox" id="Customer_Autocomplete_Multy" style="margin-left: 15px;" class="auxPtAutocomplete" onclick="mapFeilds()">
                </span>
            </div>
        </div>
    </div>
 
   <div style="display: none;" id="manholesSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 120px; font-size: 12px;">
                        <b>By Manhole</b>
                        <input type="checkbox" id="Manhole_Autocomplete_Multy" style="margin-left: 10px;" class="auxPtAutocomplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>


   <div style="display: none;" id="handholesSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 120px; font-size: 12px;">
                        <b>By Handhole</b>
                        <input type="checkbox" id="Handhole_Autocomplete_Multy" style="margin-left: 15px;" class="auxPtAutocomplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div style="display: none;" id="dbsSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                        <b>By DB</b>
                        <input type="checkbox" id="db_Autocomplete_Multy" style="margin-left: 15px;" class="auxPtAutocomplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>

    <div style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 95px; font-size: 12px;">
                    <b>By Place</b>
                    <input type="checkbox" id="Place_Autocomplete_Multy" style="margin-left: 10px;" class="auxPtAutocomplete" onclick="placeFeild()">
                </span>
            </div>
        </div>
    </div>

    <div>
  
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 95px; font-size: 12px; margin-right: 3px;"">
                    <b>Generic</b>
                    <input type="checkbox" id="Generic_Autocomplete_Multy" style="margin-left: 10px;" class="auxPtAutocomplete" onclick="placeFeild()">
                </span>
                				<button  onclick= "setCoorMulty()" id="setCoorMulti" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; display:none">Set Coordinates</button>
		
        </div>
        </div>
        
        
    </div>
</div>
							
										
										
									</div>
									<div class="row">
									<form>
									  	<div class="table-responsive-sm" id="Multy_auxiliaryDiv"> 
											<table id="Multy_auxiliary"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 430px; overflow: auto; width:950px; margin-right:20px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														<th >
															<button  type="button" id="selectAll_Multy" class="main">
																<span class="sub"></span>Select
															</button>
														</th>
														
														<th width="60px" class="headcol">Sequence</th>
													<th>	View on Map
												</th>
														<th width="150px" >Location points</th>
														<th width=330px>Location Name </th>
														<th width=220px>Location Longitude</th>
														<th width=220px>Location Latitude</th>
														<th width=220px>Circle </th>
														<th width=220px> Square </th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										 </div>
										<button type="button" id="add_Multy">Add Row </button>
										<button type="button" id="delet_Multy" >Delete Row</button>
								
										<div>
										<br>
									    <br>
										</div>	
									</form>
								</div>
							</div>
							</div>
							
							
							<div class="tab-pane" id="connectedS" role="tabpanel" aria-labelledby="connectedSearch"><p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-2" >
											<div class="form-group" >
												<div class="input-group-prepend" style="">
													<select class="form-control" id="selectConnectedSearch" style="font-size: 13.8px;"  >
														<option value="">Sites/Clients</option>
														<option value="site">Sites</option>
														<option value="client">Clients</option>
													</select></div></div></div>
										<div class="col-sm-3" style="padding-left: 0px; padding-right: 0px;">
											<input type="text" id="autoCompleteConnectedSearch" class="form-control text-input" placeholder="search..." />
											<input type='text' id='connectedSearchLong'  hidden/> <input type='text' id='connectedSearchLat'  hidden/>
										</div>
										<div class="col-sm-3">
											<div class="input-group-prepend">
												<span style="font-size: 12px; background-color: white;" class="input-group-text">View on Map <label class="switch"> 
												<input type="checkbox" id="viewOnMap" value="off" > <span class="slider"></span>
												</label></span></div></div>
										<div class="col-sm-3"><div class="form-group"><div class="input-group-prepend">
											<span style="background-color: white;font-size: 12px;" class="input-group-text" ><b>Get Related Points</b> 
											<input type='checkbox' id="getRelatedPointsCon" style='background-color: white;position: relative; margin-left:25px'  value='0'></span>
									</div></div></div>
										<div class="col-sm-1" style="">
											<div class="form-group">
												<button id="connectedSearch" class="btn btn-primary searchHeaderButton" style="color: white; font-size: 13px; height: 38px; margin-left:10px;">Find
												</button></div></div></div>
									<ul class="nav nav-tabs" id="connInfoTab" role="tablist" style="background-color: #00757C;">
    <li class="nav-item">
        <a class="nav-link active" id="path-tab" style="color: gold;" data-toggle="tab" href="#pathInfo" role="tab" aria-controls="fiber" aria-selected="true">Path</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="dB-tab" style="color: gold;" data-toggle="tab" href="#dBInfo" role="tab" aria-controls="source_dest" aria-selected="false">DB</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="junction-tab" style="color: gold;" data-toggle="tab" href="#junctionInfo" role="tab" aria-controls="conduit" aria-selected="false">Junction</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" id="node-tab" style="color: gold;" data-toggle="tab" href="#nodeInfo" role="tab" aria-controls="nodes" aria-selected="false">Node</a>
    </li>
</ul>

<div class="tab-content" style="min-height: 180px;">
    <!-- Path Tab -->
    <div class="tab-pane active" id="pathInfo" role="tabpanel" aria-labelledby="path-tab">
        <p></p>
        <div class="container-fluid" style="overflow-x: auto;  border:none;">
            <div id="findNearestManRes"></div>
            <div class="row" style="height: 500px; border:none">
                <fieldset class="field_set" style=" border:none">
                    <legend class="fieldset_legend" style="width: auto;"></legend>
                    	<table id="connFiber"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:10px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 80px; text-align: center;">Fiber ID</th>
                                                    <th style="width: 250px; text-align: center;">Fiber Name</th>
                                                    <th style="width: 150px; text-align: center;">Tube #</th>
                                                    <th style="width: 150px; text-align: center;">Tube ID</th>
                                                    <th style="width: 150px; text-align: center;">Tube Name</th>
                                                    <th style="width: 150px; text-align: center;">Strand #</th>
                                                    <th style="width: 200px; text-align: center;">Strand ID</th>
                                                     <th style="width: 200px; text-align: center;">Strand Name</th>

														
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
                
                </fieldset>
            </div>
        </div>
    
    </div>

    <!-- DB Tab -->
    <div class="tab-pane" id="dBInfo" role="tabpanel" aria-labelledby="dB-tab">
        <p></p>
        <div class="container-fluid">
            <div id="findNearestHandRes"></div>
            <div class="row" style="height: 500px;">
                <fieldset class="field_set">
                    <legend class="fieldset_legend" style="width: auto;"></legend>
                    
                                <legend class="fieldset_legend" style="width: auto;"></legend>
                                
                                <table id="connDB"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:10px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 80px; text-align: center;">DB ID</th>
                                                    <th style="width: 300px; text-align: center;">DB Name</th>
                                                    <th style="width: 300px; text-align: center;">City</th>
                                                 

														
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
                
               
                  
                </fieldset>
            </div>
        </div>
       
    </div>

    <!-- Junction Tab -->
    <div class="tab-pane" id="junctionInfo" role="tabpanel" aria-labelledby="junction-tab">
        <p></p>
        <div class="container-fluid">
            <div id="findNearestDbRes"></div>
            <div class="row" style="height: 500px;">
                <fieldset class="field_set">
                    <legend class="fieldset_legend" style="width: auto;"></legend>
                    <table id="connJunc" style="display: block; height: 500px; overflow-y: auto;">
                        <thead>
                            <tr>
                              
                            </tr>
                        </thead>
                        <tbody id="conJuncId"></tbody>
                    </table>
                </fieldset>
            </div>
        </div>
       
    </div>

    <!-- Node Tab -->
    <div class="tab-pane" id="nodeInfo" role="tabpanel" aria-labelledby="node-tab">
        <p></p>
        <div class="container-fluid">
            <div id="findNearestNodeRes"></div>
            <div class="row" style="height: 500px;">
                <fieldset class="field_set">
                    <legend class="fieldset_legend" style="width: auto;"></legend>
                           
                                <table id="connNode"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:10px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 80px; text-align: center;">Node ID</th>
                                                    <th style="width: 300px; text-align: center;">Node Name</th>
                                                    <th style="width: 300px; text-align: center;">Node Type</th>
                                                 

														
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
                   
                </fieldset>
            </div>
        </div>
      
    </div>
</div>

									
									
									
									
									
									
									
									
									
									
									
									
								
					
									
										</div></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>

<div class="container">
		<div id="siteModalAuxiliary" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"   data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="height: 600px;">
				
				<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="SiteIdHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<button type="button" name="closePopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
						<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
					</div>
					
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="SiteAux-tab" style="color: gold;" data-toggle="tab" href="#SiteAux" role="tab" aria-labelledby="SiteAux" aria-selected="true">Site Auxiliary Points</a></li>
						</ul>
						<br>
						<div></div>
						<br>
							<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
										<li class="nav-item"><a class="nav-link active"
											id="manPoint-tab_multy" style="color: gold;" data-toggle="tab"
											href="#manP_multy" role="tab" aria-controls="fiber"
											aria-selected="true">Manhole</a></li>
											
										<li class="nav-item"><a class="nav-link "
											id="handPoint-tab_multy" style="color: gold;" data-toggle="tab"
											href="#handP_multy" role="tab" aria-controls="source_dest"
											aria-selected="false">Handhole</a></li>
											
										<li class="nav-item"><a class="nav-link "
											id="dboardPoint-tab_multy" style="color: gold;" data-toggle="tab"
											href="#dboardP_multy" role="tab" aria-controls="conduit"
											aria-selected="false">Distribution Board</a></li>
											
											<li class="nav-item"><a class="nav-link "
											id="node-tab_multy" style="color: gold;" data-toggle="tab"
											href="#nodes_multy" role="tab" aria-controls="conduit"
											aria-selected="false">Node</a></li>
											
										<li class="nav-item"><a class="nav-link "
											id="fPaths-tab_multy" style="color: gold;" data-toggle="tab"
											href="#fPaths_multy" role="tab" aria-controls="conduit"
											aria-selected="false">Fiber Paths</a></li>
									</ul>
						<div class="tab-pane" id="SiteAux" role="tabpanel" aria-labelledby="SiteAux-tab">
						<div class="tab-content" style="min-height: 180px">
							<div class="tab-pane active" id="manP_multy" role="tabpanel" aria-labelledby="manPoint-tab_multy">
								<p></p>
										
										 <div class="container-fluid" style="overflow-x: auto;  border:none;">
           
												<div id="findNearestManRes_multy" style="border:none"></div>
												            <div class="row" style="height: 500px; border:none">
                <fieldset >
                    <legend class="fieldset_legend" style="width: auto;"></legend>
                    	<table id="findNearstManhole_multy"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
																<th style="min-width: 30px;"><input type="checkbox" id="selectAllManhole_multy"></th>
													<th style="min-width: 80px; text-align: center;">Manhole ID</th>
                                                    <th style="width: 250px; text-align: center;">Manhole Name</th>
                                                    <th style="width: 150px; text-align: center;">Longitude</th>
                                                    <th style="width: 150px; text-align: center;">Latitude</th>
                                                    <th style="width: 150px; text-align: center;">Distance(km)</th>
                                                    <th style="width: 150px; text-align: center;">Driving Distance(km)</th>
                                                 
														
													</tr>
												</thead>
												<tbody id= "searchManhTBody_multy">
												</tbody>
											</table>
                
                </fieldset>
            </div>
											</div>
									        <div id="manholeTotal" Style="Padding-top: 25px;"><b>Total Manholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalManhole_multy" name="totalManhole_multy" readonly>	 </div>
										</div>
										<div class="tab-pane" id="handP_multy" role="tabpanel"
											aria-labelledby="handPoint-tab_multy">
											<p></p>
											
											 <div class="container-fluid" style="overflow-x: auto;  border:none;">
           
												<div id="findNearestHandRes_Multy" style="border:none"></div>
												            <div class="row" style="height: 500px; border:none">
                <fieldset >
                    <legend class="fieldset_legend" style="width: auto;"></legend> 
													<table id="findNearstHandhole_multy"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 30px;"><input type="checkbox" id="selectAllHandhole_multy"></th>
													<th style="min-width: 80px; text-align: center;">Handhole ID</th>
                                                    <th style="width: 250px; text-align: center;">Handhole Name</th>
                                                    <th style="width: 150px; text-align: center;">Longitude</th>
                                                    <th style="width: 150px; text-align: center;">Latitude</th>
                                                    <th style="width: 150px; text-align: center;">Distance(km)</th>
                                                    <th style="width: 150px; text-align: center;">Driving Distance(km)</th>
                                                 
														
													</tr>
												</thead>
												<tbody id= "searchHanhTBody_multy">
												</tbody>
								
														</table>
													</fieldset>

												</div>
											</div>
                                             <div id="handholeTotal" Style="Padding-top: 25px;"><b>Total Handholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalHandhole_multy" name="totalDB_Multy" readonly>	 </div>
										</div>
						
										<div class="tab-pane" id="dboardP_multy" role="tabpanel"
											aria-labelledby="dboardPoint-tab_multy">
											<p></p>
											 <div class="container-fluid" style="overflow-x: auto;  border:none;">
           
												<div id="findNearestDbRes_Multy" style="border:none"></div>
												            <div class="row" style="height: 500px; border:none">
                <fieldset >
                    <legend class="fieldset_legend" style="width: auto;"></legend> 
													<table id="findNearstDB_multy"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 30px;"><input type="checkbox" id="selectAllDB_multy"></th>
													<th style="min-width: 80px; text-align: center;">DBoard ID</th>
                                                    <th style="width: 250px; text-align: center;">DBoard Name</th>
                                                    <th style="width: 150px; text-align: center;">Longitude</th>
                                                    <th style="width: 150px; text-align: center;">Latitude</th>
                                                    <th style="width: 150px; text-align: center;">Distance(km)</th>
                                                    <th style="width: 150px; text-align: center;">Driving Distance(km)</th>
                                                 
														
													</tr>
												</thead>
												<tbody id= "searchDBoardTBody_multy">
												</tbody>
								
														</table>
													</fieldset>

												</div>
											</div>
										
										    <div id="DbTotal" Style="Padding-top: 25px;"><b>Total Distribution Boards:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDB_Multy" name="totalDB_Multy" readonly>										
                                            </div>
										</div>
										
												<div class="tab-pane" id="nodes_multy" role="tabpanel"
											aria-labelledby="node-tab_multy">
											<p></p>
											 <div class="container-fluid" style="overflow-x: auto;  border:none;">
           
												<div id="findNearestNodeRes_Multy" style="border:none"></div>
												            <div class="row" style="height: 500px; border:none">
                <fieldset >
                    <legend class="fieldset_legend" style="width: auto;"></legend> 
													<table id="findNearstNode_multy"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 30px;"><input type="checkbox" id="selectAllNode_multy"></th>
													<th style="min-width: 80px; text-align: center;">Node ID</th>
                                                    <th style="width: 250px; text-align: center;">Node Name</th>
                                                    <th style="width: 150px; text-align: center;">Longitude</th>
                                                    <th style="width: 150px; text-align: center;">Latitude</th>
                                                    <th style="width: 150px; text-align: center;">Distance(km)</th>
                                                    <th style="width: 150px; text-align: center;">Driving Distance(km)</th>
                                                 
														
													</tr>
												</thead>
												<tbody id= "searchNodeTBody_multy">
												</tbody>
								
														</table>
													</fieldset>

												</div>
											</div>
								
                                            <div id="NodeTotal" Style="Padding-top: 25px;"><b>Total Nodes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalNode_Multy" name="totalNode_Multy" readonly>										
                                            </div>
										</div>
												

										<div class="tab-pane" id="fPaths_multy" role="tabpanel" aria-labelledby="fPaths-tab_multy">
											<p></p>
											
											
													 <div class="container-fluid" style="overflow-x: auto;  border:none;">
           
												<div id="strandMulty" style="border:none"></div>
												            <div class="row" style="height: 200px; border:none">
                <fieldset >
                    <legend class="fieldset_legend" style="width: auto;"></legend> 
													<table id="nearestStrand"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 200px; overflow: auto; max-width:850px; margin-right:20px; margin-left:px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 30px;"><input type="checkbox" id="selectAllStrand_multy"></th>
													<th style="min-width: 80px; text-align: center;">Strand ID</th>
                                                    <th style="width: 250px; text-align: center;">Strand Name</th>
                                                    <th style="width: 150px; text-align: center;">Source</th>
                                                    <th style="width: 150px; text-align: center;">Destination</th>
                                               
                                                 
														
													</tr>
												</thead>
												<tbody id= "nearStrandId_multy">
												</tbody>
								
														</table>
													</fieldset>

												</div>
												<br>
												
													<div id="tubeMulty" style="border:none"></div>
												            <div class="row" style="height: 200px; border:none">
                <fieldset >
                    <legend class="fieldset_legend" style="width: auto;"></legend> 
													<table id="nearestTube"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 200px; overflow: auto; max-width:850px; margin-right:20px; margin-left:px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 30px;"><input type="checkbox" id="selectAllTube_multy"></th>
													<th style="min-width: 80px; text-align: center;">Tube ID</th>
                                                    <th style="width: 250px; text-align: center;">Tube Name</th>
                                                    <th style="width: 150px; text-align: center;">Source</th>
                                                    <th style="width: 150px; text-align: center;">Destination</th>
                                               
                                                 
														
													</tr>
												</thead>
												<tbody id= "nearTubeId_multy">
												</tbody>
								
														</table>
													</fieldset>

												</div>
												<br>
												<div id="fiberMulty" style="border:none"></div>
												            <div class="row" style="height: 500px; border:none">
                <fieldset >
                    <legend class="fieldset_legend" style="width: auto;"></legend> 
													<table id="nearestFiber"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 400px; overflow: auto; max-width:850px; margin-right:20px; margin-left:px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														
													<th style="min-width: 30px;"><input type="checkbox" id="selectAllFiber_multy"></th>
													<th style="min-width: 80px; text-align: center;">Fiber ID</th>
                                                    <th style="width: 250px; text-align: center;">Fiber Name</th>
                                                    <th style="width: 150px; text-align: center;">Source</th>
                                                    <th style="width: 150px; text-align: center;">Destination</th>
                                               
                                                 
														
													</tr>
												</thead>
												<tbody id= "nearFiberId_multy">
												</tbody>
								
														</table>
													</fieldset>

												</div>
												
												
												
												
												
												
											</div>
											
											
											
											
											
											
											
										</div></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
					
 
 	<div class="container">
		<div id="showClosePointsPopup" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 900px;">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Close Points Search</h5>
						<div style="float:right;">
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
							<i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="container-fluid" style="min-width: 50%;">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 50%;" class="input-group-text"><b>Longitude</b>
												</span>
												 <input type="text" id="closePtsLong" style="width: 50%;" class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 50%;" class="input-group-text"><b>Latitude</b>
												</span>
												 <input type="text" id="closePtsLat" style="width: 50%;" class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 50%;" class="input-group-text"><b>Distance Range</b>
												</span> <input type="text" id="closePtsDistanceRange" style="width: 50%;"  class="form-control text-input" />
											</div>
										</div>
									</div> 
									<div class="col-md-3">
										<div class="form-group">
											<div class="input-group-prepend">
												<button id="searchClosePoints" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px;width:100%">Show Close Points</button>
											</div>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<div class="input-group-prepend">
												<button id="captureNewCoordinate" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px;width:100%">Capture New Coordinate</button>
											</div>
										</div>
									</div>
								</div>
								
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="manholesClosePoint-tab" style="color: gold;" data-toggle="tab" href="#closePtsManholes" role="tab" aria-controls="fiber" aria-selected="true">Manhole</a></li>
							<li class="nav-item"><a class="nav-link " id="handholesClosePoint-tab" style="color: gold;" data-toggle="tab" href="#closePtsHandholes" role="tab" aria-controls="hand" aria-selected="false">Handhole</a></li>
							<li class="nav-item"><a class="nav-link " id="dbClosePoint-tab" style="color: gold;" data-toggle="tab" href="#closePtsDb" role="tab" aria-controls="db" aria-selected="false">Distribution Board</a></li>
							<li class="nav-item"><a class="nav-link " id="nodeClosePoint-tab" style="color: gold;" data-toggle="tab" href="#closePtsNode" role="tab" aria-controls="nodes" aria-selected="false">Node</a></li>
						</ul>
						<div class="tab-content" style="min-height: 180px">
										
						<div class="tab-pane active" id="closePtsManholes" role="tabpanel" aria-labelledby="manholesClosePoint-tab">
							<p></p>
								<div class="container-fluid">
									<div id="findCloseManholePts"></div>
												<div class="row" style="height: 100px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findCloseManhole" style="display: block; height: 150px; overflow-y: auto;" class="searchable sortable">
														 <thead>
															<tr>
															    <th style="min-width: 30px;"></th>
																<th style="min-width: 150px;" >Manhole ID</th>
																<th style="min-width: 150px;">ManholeName</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 150px;">Latitude</th>
																<th style="min-width: 100px;">Distance(m)</th>
																<th style="min-width: 80px;"colspan="2">Driving Distance(m)</th>
															</tr>
															 </thead>
															<tbody id="searchCloseManhTBody">
															</tbody>
														</table>
													</fieldset>
												</div>
											</div>
									        <div id="closeManholeTotal" Style="Padding-top: 90px;"><b>Total Manholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalCloseManhole" name="totalCloseManhole" readonly>	 </div>
										</div>
										<div class="tab-pane" id="closePtsHandholes" role="tabpanel" aria-labelledby="handholesClosePoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findCloseHandholePts"></div>
												<div class="row" style="height: 100px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findCloseHandhole" class="searchable sortable"
															style="display: block; height: 150px; overflow-y: auto;">
															 <thead>
															<tr>
																<th style="min-width: 30px;"></th>
																<th style="min-width: 150px;" >Handhole ID</th>
																<th style="min-width: 150px;">HandholeName</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Distance(m)</th>
																<th style="min-width: 100px;" colspan="2">Driving Distance(m)</th>
																
															</tr>
															</thead>
															
															<tbody id="searchCloseHandTBody">
															</tbody>
														</table>
													</fieldset>

												</div>
											</div>
                                             <div id="closeHandholeTotal" Style="Padding-top: 90px;"><b>Total Handholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalCloseHandhole" name="totalCloseHandhole" readonly>	 </div>
										</div>
										<div class="tab-pane" id="closePtsDb" role="tabpanel"
											aria-labelledby="dbClosePoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findCloseDbPts"></div>
												<div class="row" style="height: 100px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findCloseDB" style="display: block; height: 150px; overflow-y: auto;">
														 <thead>															
															<tr>
																<th style="min-width: 30px;"></th>
																<th style="min-width: 150px;" >DBoard ID</th>
																<th style="min-width: 150px;">DBoard Name</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Distance(m)</th>
																<th style="min-width: 100px;" colspan="2">Driving Distance(m)</th>								
															</tr>
															 </thead>															
															<tbody id="searchCloseDbTBody">
															</tbody>
														</table>
													</fieldset>
												 </div>
											</div>
                                            <div id="closeDbTotal" Style="Padding-top: 90px;"><b>Total Distribution Boards:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalCloseDB" name="totalCloseDB" readonly>										
                                            </div>
										</div>
								<div class="tab-pane" id="closePtsNode" role="tabpanel" aria-labelledby="nodeClosePoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findCloseNodePts"></div>
												<div class="row" style="height: 100px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findCloseNode" class="searchable sortable"
															style="display: block; height: 150px; overflow-y: auto;">
															 <thead>
															<tr>
																<th style="min-width: 30px;"></th>
																<th style="min-width: 150px;" >Node PK</th>
																<th style="min-width: 150px;" >Node ID</th>
																<th style="min-width: 150px;">Node Name</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Distance(m)</th>
																<th style="min-width: 100px;" colspan="2">Driving Distance(m)</th>
															</tr></thead>
															<tbody id="searchCloseNodeTBody"></tbody>
														</table>
													</fieldset>
												</div>
											</div>
                                             <div id="closeNodeTotal" Style="Padding-top: 90px;"><b>Total Nodes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalCloseNode" name="totalCloseNodes" readonly></div>
										</div>
										</div>
							</div>
						</div>
						<div class="modal-footer"></div>
					</div>
				</div>
			</div>
		</div>
	</div> 					
					
  					
</body>
<script>

$('.panel-collapse').on('show.bs.collapse',function() {
	$(this).siblings('.panel-heading').removeClass('active');
});

$('.panel-collapse').on('hide.bs.collapse',function() {
	$(this).siblings('.panel-heading').addClass('active');
});

$(document).ready(function() {
    // Function to handle row clicks
    $(document).on('click', '#findNearstManhole tr', function() {
        handleRowClick('#findNearstManhole', $(this));
    });

    $(document).on('click', '#Multy_auxiliary tr', function() {
        handleRowClick('#Multy_auxiliary', $(this));
    });

   $(document).on('click', '#findNearstHandhole tr', function() {
        handleRowClick('#findNearstHandhole', $(this));
    });

   $(document).on('click', '#findNearstDB tr', function() {
       handleCheckboxClick('#findNearstDB', $(this));
   });

   $(document).on('click', '#findNearstNode tr', function() {
       handleCheckboxClick('#findNearstNode', $(this));
   });

   $(document).on('click', '#nearestStrand tr', function() {
       handleCheckboxClick('#nearestStrand', $(this));
   });

   $(document).on('click', '#nearestTube tr', function() {
       handleCheckboxClick('#nearestTube', $(this));
   });

   $(document).on('click', '#nearestFiber tr', function() {
       handleCheckboxClick('#nearestFiber', $(this));
   });
   
   $(document).on('click', '#connFiber tr', function() {
       handleCheckboxClick('#connFiber', $(this));
   });
   $(document).on('click', '#connDB tr', function() {
       handleCheckboxClick('#connDB', $(this));
   });

   $(document).on('click', '#connJunc tr', function() {
       handleCheckboxClick('#connJunc', $(this));
   });

   $(document).on('click', '#connNode tr', function() {
       handleCheckboxClick('#connNode', $(this));
   });
    

    // Reusable function to handle row clicks
    function handleRowClick(tableId, clickedRow) {
        // Remove "ativeRecord" class from all rows in the specified table
        $(`${tableId} tr`).removeClass("ativeRecord");
        // Add "ativeRecord" class to the clicked row
        clickedRow.addClass("ativeRecord");
    }

    // Reusable function to handle checkbox clicks
    function handleCheckboxClick(tableId, clickedCheckbox) {
        // Remove "ativeRecord" class from all rows in the specified table
        $(`${tableId} tr`).removeClass("ativeRecord");
        // Add "ativeRecord" class to the row containing the clicked checkbox
        clickedCheckbox.closest('tr').addClass("ativeRecord");
    }
});

document.addEventListener("DOMContentLoaded", function() {
    var readDB = ${readDB}; // or fetch this value from the server if necessary
    if (readDB == 1) {
        document.getElementById("dbsSection").style.display = "block";
    }
    var readManhole = ${readManhole}; // or fetch this value from the server if necessary
    if (readManhole == 1) {
        document.getElementById("manholesSection").style.display = "block";
    }
    var readHandhole = ${readHandhole}; // Fetch this value from the server if necessary
    if (readHandhole == 1) {
        document.getElementById("handholesSection").style.display = "block";
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const genericCheckbox = document.getElementById("Generic_Autocomplete_Multy");
    const otherCheckboxes = document.querySelectorAll(".auxPtAutocomplete:not(#Generic_Autocomplete_Multy)");
    const button = document.getElementById("setCoorMulti");

    // Function to toggle button visibility based on checkbox states
    function toggleButtonVisibility() {
        const anyOtherChecked = Array.from(otherCheckboxes).some(checkbox => checkbox.checked);

        if (genericCheckbox.checked && !anyOtherChecked) {
            button.style.display = "block";
        } else {
            button.style.display = "none";
        }
    }

    toggleButtonVisibility();

    genericCheckbox.addEventListener("change", toggleButtonVisibility);
    otherCheckboxes.forEach(checkbox => checkbox.addEventListener("change", toggleButtonVisibility));
});


</script>
</html>