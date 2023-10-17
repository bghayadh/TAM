<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<head>
		<meta charset="utf-8">
		<title></title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="shortcut icon" href="">
		<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>

		<script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
		
		<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
		<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
		<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
		<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
		<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
		<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
		<script type="text/javascript"
			src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
		<script type="text/javascript"
			src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>


		<!-- 	 <script src="${pageContext.request.contextPath}/resources/js/FileSaver.min.js"></script> -->
		<!--  <script src="${pageContext.request.contextPath}/resources/js/xlsx.core.min.js"></script> -->
		<script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/jspdf.plugin.autotable.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/tableHTMLExport.js"></script>
		<!--   <script src="${pageContext.request.contextPath}/resources/js/tableExport.js"></script>-->

		<link href="${pageContext.request.contextPath}/resources/cssgrid/bootstrap-datepicker.min.css" rel='stylesheet'
			type='text/css'>
		<script src="${pageContext.request.contextPath}/resources/scriptsgrid/bootstrap-datepicker.min.js"
			type='text/javascript'></script>
		<!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/areasitereport.css" /> -->

		<!-- ALM GRID Scripts -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>


 <!-- export scripts -->
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
	</head>

<body>
  
<%--   <c:set var = "page" value = "inventory"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
	<c:set var="pg" value="inventory" scope="session" />
	<jsp:include page="header.jsp"></jsp:include>
 <!--  end of general head page -->
	<div class="container-fluid">     
		<div class="row">
		<div class="col-md-12">
		<p></p>
		</div>
		
		</div>
	
		<div class="row second">
			<div class="col-md-8">
			</div>
			<div class="col-md-4" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
		 			
		 				<button class="btn btn-secondary" type="button" id="export">Export</button>
			 			<button  type="button" id="Fview"  class="btn btn-light" data-toggle="tooltip" 
			 				data-placement="top" title="Form View"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-danger" data-toggle="tooltip"
			        			data-placement="top" title="List View" style="background: #da6815;"> 
			        			<i class="fa fa-bars"></i>
			        	</button>
						<button type="button" id="searchWarehouse" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search">
							<i class="fa fa-search"></i>
						</button>						
			        </div>  
		        </div>
			</div>
		</div>
	
	<div class="container">
	<div id ="poModal" class="modal fade  custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #2678CC ;" >
				<h5 class="modal-title" id="myModalLabel1" style="font-weight:bold; color: #E9ECEF;position:relative;top:4px;">  Filter Options </h5>
				<button type="button" name="closePopup" id="closePopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	 <ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="date-tab" style="color: gold;" data-toggle="tab" href="#date" role="tab" aria-controls="date" aria-selected="true">Date</a>
  		</li>
  
  		<li class="nav-item">
   			 <a class="nav-link " id="regioninfo-tab" style="color: gold;" data-toggle="tab" href="#regioninfo" role="tab" aria-controls="regioninfo" aria-selected="false">Region</a>
  		</li>
  		
  		<li class="nav-item">
   			 <a class="nav-link " id="areainfo-tab" style="color: gold;" data-toggle="tab" href="#areainfo" role="tab" aria-controls="areainfo" aria-selected="false">Area Info</a>
  		</li>
  		<li class="nav-item">
   			 <a class="nav-link " id="longlatinfo-tab" style="color: gold;" data-toggle="tab" href="#longlatinfo" role="tab" aria-controls="longlatinfo" aria-selected="false">LONG & LAT</a>
  		</li>
  		
  	
 
	</ul>
            

<div class="tab-content">
  <div class="tab-pane active" id="date" role="tabpanel" aria-labelledby="date-tab">
  <p></p>
<div class="container-fluid">
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Start Date</span> <input
																	type="date"
																	class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																	id="popupstartdate" value="${popupstartdate}"
																	style="width: 675px; height: 37px;" />
															</div>
														</div>
													</div>

													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">End Date</span> <input
																	type="date"
																	class="ui-widget ui-widget-content ui-corner-all form-control text-input"
																	id="popupenddate" value=""
																	style="width: 675px; height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
  </div>
  <div class="tab-pane" id="regioninfo" role="tabpanel" aria-labelledby="regioninfo-tab">
<p></p>
	<form>
	<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Region ID  </span>
   					<input type="text" id="popupRegionid"  style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>	
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Region Name</span>
   					<input type="text" id="popupRegionname"  style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>	
	</div>
</div>
</form>
</div> 

<div class="tab-pane" id="areainfo" role="tabpanel" aria-labelledby="areainfo-tab">
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Area ID </span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupAreaid" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Area Name </span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupAreaname" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		

    </div>
</div>	
</div>

<div id="longlatinfo" class="tab-pane" role="tabpanel" aria-labelledby="longlatinfo-tab"><p></p>
								<div class="container-fluid" >
									<div class="row">
									<div class="col-sm-2" style="min-width:150px;">
											<div class="form-group">
												<div class="input-group-prepend" >
													<span style="min-width: 90px; font-size: 12px;  width: 100%;" class="input-group-text"><b>By Rectangle</b> 
													<input type='checkbox' id="Rectangle"  name="fooby[1][]" style='position: relative; margin-left: 10px'   ></span>
												</div></div></div>
									<div class="col-sm-3" style="min-width:150px;">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 90px; font-size: 12px; width:100%;" class="input-group-text" ><b>By Circle </b> 
													<input type='checkbox' id="Circle"  name="fooby[1][]" style='position: relative; margin-left:15px' ></span>
												</div></div></div>
								
									</div>
									
									<div class="row" id="row_Rectangle" >
										<div class="col-sm-5" style="" id="closestLongDiv">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Start Longitude</b></span>
													<input type="text" id="startlong" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-5" style="" id="closestLatDiv">
											<div class="form-group" >
												<div class="input-group-prepend">
													<span style="font-size: 12px;" class="input-group-text"><b>Start Latitude</b></span>
													<input type="text" id="startlat"	class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-5" id="closestDistanceRange">
											<div class="form-group">
												<div class="input-group-prepend" style="">
													<span style="font-size: 12px;" class="input-group-text"><b>End Longitude</b></span> <input type="text" id="endlong" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-5" id="NoOfPoints">
											<div class="form-group">
												<div class="input-group-prepend" style="">
													<span style="font-size: 12px;" class="input-group-text"><b>End Latitude</b></span> <input type="text" id="endlat" class="form-control text-input" />
												</div>
											</div>
										</div>		
										</div>
										
										
										<div class="row" id="row_Circle" >
										<div class="col-sm-5" style="" id="StartLongDiv">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b> Longitude</b></span>
													<input type="text" id="longg" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-5" style="" id="StartLatDiv">
											<div class="form-group" >
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b> Latitude</b></span>
													<input type="text" id="lat" class="form-control text-input" />
												</div>
											</div>
										</div>
											<div class="col-sm-5" style="" id="EndLongDiv">
											<div class="form-group">
												<div class="input-group-prepend" style="width: auto;">
													<span style="font-size: 12px;" class="input-group-text"><b>Radius</b></span>
													<input type="text" id="radius" class="form-control text-input" />
												</div>
											</div>
										</div>
										</div>
										
									</div>
</div>
</div>
					
<div class="modal-footer">
	<button type="button" id="popUpSubmit" class ="btn btn-default btn-primary BtnActive  " style="color:white;position:relative;">Submit</button>
	<button type="button" id="popupClearFields" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;">Clear Fields</button>
	<button type="button" id="popUpCancel" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;">Cancel</button>
</div>							                
</div>			
</div> 
</div>
</div>

		 </div> 
        
		<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top:0px">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"style="color: gold;text-align:center;line-height:2.75em; padding-left:5px;">WAREHOUSE LIST</li>
	 <li class="nav-item ml-auto">
								<button type="button" id="deleteButton" class="btn btn-primary BtnActive">
									<i class="fa fa-trash"></i> Delete
								</button>

								<button type="button" id="saveButton"
									onclick='window.location.href = "${pageContext.request.contextPath}/WarehouseFormView?type=addNew"'
									class="btn btn-primary BtnActive">
									<i class="fa fa-plus"></i> Add
								</button>
							</li>
		           
		                                                                                                                         
						  </li>
									
		     </ul>
		     
		</div>
		</div>
        <!-- /.card-header -->
		<div class="card-body">
					<div class="row">
						<div class="col-sm-12">
							<div class="almgrid-container">
								<div class="row">
									<div class="col-sm-4 almgrid-pagecount-box">
										Show
										<select class="cmb-row-count almgrid-pagecount">
											<option value="10" selected>10</option>
											<option value="25">25</option>
											<option value="50">50</option>
											<option value="100">100</option>
											<option value="500">500</option>
											<option value="1000">1000</option>
										</select>
										Rows
									</div>
									<div class="col-md-4">
											<div id="loaderDiv" style="display: none;">
												<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
											</div>
									</div>
									<div class="col-sm-4 almgrid-global-search-box">
										Search
										<input type="text" class="form-control almgrid-global-search" />
									</div>
								</div>
								<div id="alertMsgDiv" style="display: none;padding-left: 90px">
								<br>
									<b style="color:red;font-size:15px;white-space: nowrap;">The number of original fetched data is exceeding the number of allowed data to show. Please set a filter to reduce the fetched data from this button  </b> 
									<i class="fa fa-search" style="color:red;border:1px solid black;fontSize:22px;background-color:white;"></i>										
								</div>


								<div id="WarehouseTable" class="table-responsive almgrid-table-div">
									<table id="WarehousegridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												<th class="table-select-all">
												</th>
												<th>Warehouse Code
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Warehouse Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Site ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Last Modified Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Area
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>


												<th>Longtitude
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>



												<th>Latitude
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>



												<th>City
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

													<th>Region
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												

		
											</tr>

											<tr>
												<th class="table-select-all"><input type="checkbox"
														class="table-select-all-checkbox"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
											</tr>
										</thead>
										<tbody>

										</tbody>

									</table>
								</div>

								<hr>
								<div class="pagination-div">
									<div class="row">
										<div class="col-sm-7">
											<p class="pagination-label">
												Viewing <span>0-0</span>
												of
												<span>0</span>
											</p>
										</div>
										<div class="col-sm-5 pagination-buttons">
											<nav aria-label="Page navigation">
												<ul class="pagination pagination-buttons justify-content-end">
													<li class="page-item"><button type="button"
															class="page-link pagination-previous pagination-button shadow-none">Prev</button>
													</li>
													<li class="page-item dropdown-pagination-numbers">
														<!-- <select class="form-control page-number-select shadow-none">
														</select> -->
														<div class="input-group page-number-group-div">
															<input type="text"
																class="form-control page-number-select shadow-none" />
															<input type="text"
																class="form-control page-number-span shadow-none" />
														</div>
													</li>
													<li class="page-item"><button type="button"
															class="page-link pagination-next pagination-button shadow-none">Next</button>
													</li>
												</ul>
											</nav>
										</div>
									</div>


								</div>

							</div>

						</div>
					</div>
				</div>
		
	</div>


<script> 






var WarehouseListData = ${ ListGridTable };

$(document).ready(function () {


	var almgrid = new Almgrid({
		tableId: "WarehousegridTable",
		dataArray: WarehouseListData,
		columnLinkNb: [1],
		selectCheckbox: true
	});


	$("#deleteButton").click(function () {

		var deleteArray = [];

		$("#WarehousegridTable").find(".table-select-checkbox").each(function () {
			if ($(this).is(":checked")) {
				var checkboxVal = $(this).closest('tr').find('td:nth-child(2)').text();
				deleteArray.push(checkboxVal);
			}
		});

		deleteArray = deleteArray.filter(function (elem, index, self) {
			return index === self.indexOf(elem);
		});

		$.ajax({
			type: "GET",
			url : "${pageContext.request.contextPath}/WarehouseDelete",
			dataType: "json",
			data: {
				"wareID": deleteArray
			},
			success: function (data) {
				location.reload();
			},
			error: function (error) {
				console.log("The error is " + error);
			}
		});		
	});


	$(".almgrid-table").on("click", ".almgrid-link", function (e) {
		var param1 = $(this).attr('id');
		var param = "${pageContext.request.contextPath}/WarehouseFormView?wareID=" + param1+"&NavAction=2";
		window.location.href = param;
		e.preventDefault();
	});

		 $('#Fview').click(function(e) {
				var obj = [];
				$.each(WarehouseListData, function (i, value) {
					obj.push({
						wareID: value[1],
					
					});
					
				});
		 if (ReportArrayGlobal.length == 0) {

		 var param = "${pageContext.request.contextPath}/WarehouseFormView";
		 window.location.href = param;
		 e.preventDefault();
		 }

		 else {
			 
	     var firstCell =  obj[0].wareID;
	     var param = "${pageContext.request.contextPath}/WarehouseFormView?wareID=" + firstCell+"&NavAction=2";
		 window.location.href = param;
		 e.preventDefault();
		 }
		 
		 });

		 var exportArrayGrid=[];
	       //method to export the data into excel sheet
	      	function exportGrid() {
	    	
	    		  downloadCSVFile(exportArrayGrid, "WareHouseReport");

	    		  function downloadCSVFile(csv, filename) {
	    			var csv_file, download_link;

	    			csv_file = new Blob([csv], {type: "text/csv"});

	    			download_link = document.createElement("a");

	    			download_link.download = filename;

	    			download_link.href = window.URL.createObjectURL(csv_file);

	    			download_link.style.display = "none";

	    			document.body.appendChild(download_link);

	    			download_link.click();
	    		}
	       }

	    	$("#export").click(function() {
						console.log("clicked");
	           		//check if the data is filtered or not
		             	if(almgrid.filteredArray.length == 0 ){
		             		fillGrid(WarehouseListData);
		             		
					     }else{
			            	 fillGrid(almgrid.filteredArray);
				             }
						exportGrid();
					});
	    		
	    	

				//filling the grid with the needed data
	      	function fillGrid(filledGrid){
	      		exportArrayGrid=[];
	      		var data=[];
					data.push('\r');
					data.push(["Ware ID","Ware Code","Warehouse Name", "Site ID", "Last Modified Date","Area","Longitude","Latitude","City","Region"]);
					var value = Object.keys(filledGrid[0]);
					
					
					
					for(i=0;i<filledGrid.length;i++){
						data.push('\r');
						for(j=0;j<value.length;j++){
							
							data.push(filledGrid[i][value[j]]);
						
							}
						
					}

					
					exportArrayGrid.push(data);

	          	}



	      	$("#searchWarehouse").click(function() {
	     		$("#poModal").modal("show");
	     		document.getElementById('searchWarehouse').className = 'btn btn-danger';
	         	document.getElementById('Lview').className = 'btn btn-light';
	         	$("#Lview").css('background', '#FFFFFF');	
	     		$("#searchWarehouse").css('background', '#da6815');	
	     	
	     		var todayDate = new Date().toISOString().slice(0, 10);
	     		var olderdate = new Date(todayDate);
	     		olderdate.setMonth(olderdate.getMonth() -3);
	     		
	     		$("#popupstartdate").val(olderdate.toISOString().slice(0, 10));
	     		$("#popupenddate").val(todayDate);
			});



	      	$("#popUpSubmit").click(function() {
	      		$("#poModal").modal("show");
	     		$("#WarehousegridTable").remove();
	     		$("#WarehouseTable").append('<table id="WarehousegridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header"><th class="table-select-all"></th>'
	     				+'<th>Warehouse Code <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
	     				+'<ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Warehouse Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown">'
	     				+'<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Site ID<li class="filter-dropdown dropdown">'
	     				+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
	     				+'</ul></li></th><th>Last Modified Date <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Area <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
	     				+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Longtitude <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown">'
	     				+'<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	     				+'<th>Latitude <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
	     				+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>City<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown">'
	     				+'<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	     				+'<th>Region <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
	     				+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th></tr><tr><th class="table-select-all"><input type="checkbox" class="table-select-all-checkbox"></th>'
	     				+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	     				+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	     				+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th></th><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	     				+'<th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th></tr></thead><tbody></tbody></table>');
		      	
	     		$.ajax({
					type : "GET",
					contentType: "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/FilteredWarehouseListView",
					
					data : {
						"startDate":$("#popupstartdate").val(),
						"endDate":$("#popupenddate").val(),
						"regionid":$("#popupRegionid").val(),
						"regionname":$("#popupRegionname").val(),
						"areaid":$("#popupAreaid").val(),
						"areaname":$("#popupAreaname").val(),
						"startlong":$("#startlong").val(),
						"startlat":$("#startlat").val(),
						"endlong":$("#endlong").val(),
						"endlat":$("#endlat").val(),
						"longg":$("#longg").val(),
						"lat":$("#lat").val(),
						"radius":$("#radius").val(),
	    		
			    				

				},
				dataType : "json",
				success : function(data) {
						
						if(data!=null){
							WarehouseListData = data;
							almgrid = new Almgrid({
							tableId: "WarehousegridTable",
							dataArray: data.listwarehouse,
							columnLinkNb: [1],
							selectCheckbox: true
							});

		             		document.getElementById('searchWarehouse').className = 'btn btn-danger'
			             	document.getElementById('Lview').className = 'btn btn-light'
			             	$("#Lview").css('background', '#FFFFFF');	
		             		$("#searchWarehouse").css('background', '#da6815');
							document.getElementById("alertMsgDiv").style.display = "none";


		             		
		             		$(".almgrid-table").on("click", ".almgrid-link", function (e) {
		             			var param1 = $(this).attr('id');
		             			var param = "${pageContext.request.contextPath}/WarehouseFormView?wareID=" + param1+"&NavAction=2";
		             			window.location.href = param;
		             			e.preventDefault();
		             		});
						}	
					
					},
					error : function(error) {
						console.log("Failed");
						console.log(error.reponseText);
						 
						
					}
				});


		      	});
	        // Resize and drag the popup
	    	$('.modal-content').resizable({
	    	handles: "e" ,
	    	
	    	});
	    	 
	    	$('.modal-dialog').draggable({
	    	handle: ".modal-header, .modal-footer"
	    	});
	    	   
	    	$('#poModal').on('show.bs.modal', function() {
	    	$(this).find('.modal-body').css({
	    	'max-height': '100%',
	    	});
	    	});
	         
	    	//Reset the popup to original size after resizing 
	    	$('#poModal').on('hidden.bs.modal', function() {
	    	$(this).find('.modal-content').css({'width': '', 'height': ''});
	    	})
	    	 
	    	//Reset popup position after it has been dragged and closed
	    	$('body').on('hidden.bs.modal', function() {
	    	$('.modal-dialog').css({'top': '', 'left':''});
	    	})
	    			      
	    	// Minimize and Maximize fct for popup
	    	$(".modalMinimize").on("click", function(){
	    	$(".modal-body").slideToggle();
	    	$(".modal-footer").slideToggle();
	    	
	    	//Toggle between minimize/maximize icons
	    	var iconToChange = $('.icon-to-change');
	    		if(iconToChange.hasClass('fa-window-restore')){
	         		iconToChange.removeClass('fa-window-restore')
	        		            .addClass('fa-minus')
	    		}
	    		else{
	         		iconToChange.addClass('fa-window-restore')
	        		             .removeClass('fa-minus')
	    		}    		         
	    	}); // end minimize/maximize fct

	    	// Close popup function  				
	    	   $("#closePopup").on("click", function(){
	    	              	  
	    		    $("#poModal").modal("hide");
	    		   // clearFields();
	    		    
	    		    
	    			 
	    	 }); // end close fct

	    	 
	    	$("#popUpCancel").on("click", function (e) {
	    		$("#poModal").modal("hide");
	    		//clearFields();
				});
	    	$("#popupClearFields").on("click", function (e) {
				 $('#poModal').find('input:text').val('');
				 var strtDate = new Date().toISOString().slice(0, 10);
		     	 var endDatee = new Date(strtDate);
		     	 endDatee.setMonth(endDatee.getMonth() -3);
		     	 $("#popupstartdate").val(endDatee.toISOString().slice(0, 10));
		     	 $("#popupenddate").val(strtDate);	
				 $("#row_Rectangle").hide();
				 $("#row_Circle").hide();
				 $("#Rectangle").prop("checked", false); 
				 $("#Circle").prop("checked", false);
			});

	 // 	function clearFields()
	//	{$("#popupstartdate").val('');	$("#popupenddate").val('');$("#popupWhname").val('');$("#popupArea").val('');$("#popupCity").val('');$("#popupSiteID").val('');}
	    	
	 $(function() {
	    		  // Get the form fields and hidden div
	    		  var checkbox1 = $("#Circle");
	    		  var checkbox2 = $("#Rectangle");
	    		  var hidden = $("#row_Rectangle");
	    		  var hiddenn = $("#row_Circle");

	    		  hidden.hide();
	    		  hiddenn.hide();
	    		  checkbox2.change(function() {
	    		    if (checkbox2.is(':checked')) {
	    		      hidden.show();
	    		      hiddenn.hide();
	    		    } else {
	    		      hidden.hide();
	    		      
	    		    }
	    		  });
	    		  checkbox1.change(function() {
		    		    if (checkbox1.is(':checked')) {
		    		      hiddenn.show();
		    		      hidden.hide();
		    		    } else {
		    		      hiddenn.hide();
		    		      
		    		    }
		    		  });
	    		});
	    	//to let you check only one checkbox
	    	$("input:checkbox").on('click', function() {
	    	
	    		  var $box = $(this);
	    		  if ($box.is(":checked")) {
	    		  
	    		    var group = "input:checkbox[name='" + $box.attr("name") + "']";
	    		    
	    		    $(group).prop("checked", false);
	    		    $box.prop("checked", true);
	    		  } else {
	    		    $box.prop("checked", false);
	    		  }
	    		});
	    	
});




</script>

 </body>
 </html>
