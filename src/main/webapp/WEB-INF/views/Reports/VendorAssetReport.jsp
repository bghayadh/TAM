<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Vendor Asset Report</title>
     <link rel="shortcut icon" href="">
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
	
	    <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>		
		
		 <!--Network_Index.css is included here in order to use the css of right click menu  -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">
		
	    
	    <!--  MultiSelect Script -->
        <link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet"    >
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>	
			
			
		<!-- Google Maps Script -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	
	    <!-- export scripts -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
       
</head>
<style>

.fixed-header{
	opacity: 1;
	filter: alpha(opacity=100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}
#Domain,#subDomain,#Vendor {
    z-index: 100; 
}


#Domain * ,#subDomain * ,#Vendor *{
    z-index: 100;     
}

#showOnMap {
  color: orange;
  background-color: white;
  border-color: #939191f5;
  transition: border 0.3s; 
}

#showOnMap:hover{
    color: white;
	background-color: orange;
}

.panToSite:hover{
    color: white;
	background-color: #007bff;
}


.panToSite {
background-color:white;
color: #007bff;
  border-color: #939191f5;

}

.clearButton{
background-color:white;
color:orange;
}

.clearButton:hover{
background-color:orange;
color:white;
}


.clearButton:not(:hover){
background-color:white;
color:orange;
}

#mapContainer {
height: 700px;
}
        
.legendHeader {
overflow: hidden;
background-color: #08526d;
padding: 10px 0px;
}

.blue {
background:blue;
}

.dot {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
}

.legendContainer{
height: 800px;
position: relative;
}

.box{
width: 100%;
height: 100%;            
position: absolute;
top: 20px;
left: 0;
}

.stack-top{
z-index: 3;
margin: 70px; 
}

.title {
  margin:5px 0px;
  font-size: 25px;
  font-weight:600;
  font-family: 'Times New Roman', Times, serif;
}

.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 400px;
  overflow : auto;
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
</style>
<body>
<c:set var="pg" value="report" scope="session"  />
  <jsp:include page="../header.jsp"></jsp:include>
  
  	<div class="container-fluid">     
	     <div class="row"><p></p></div>
			<div class="row second" >	
			<div class="col-md-2" style="display: flex; ">
				<div class="form-group "style="margin-left: 15px; ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Vendor Asset Report</span>
					</div>
				</div>
			</div>
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="startDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
					   <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div></div>
				</div></div></div>
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker4" data-target-input="nearest">
					<span class="input-group-text">End Date</span>
					<input type="text" id="endDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
					   <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
                </div>
			</div>
			 <div class="col-md-2">
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
      					<input type="checkbox" value="0" id="ignoreDate" name="ignoreDate">
      					<span style="margin-left: 10px;">Ignore Date</span>
				   </div>
                  </div>
			   </div>
	     </div>	
	
			<div class="col-md-2" id="col3" style="text-align:right;">
		 		<div class="btn-group pull-right"  style="padding: 0px !important;">
		 			
		 			<div class="glyph" style="padding-top: 0px;">
						<button class="btn btn-secondary" type="button" id="export"
							style="margin-right: 5px">Export</button>

					</div>
		       
		 			<div class="glyph" Style="white-space: nowrap;overflow: hidden;">
		                  <button type="button" id="generate" class="btn btn-primary BtnActive" > Generate Report </button> 			
					</div>
			</div></div></div>
			
			<br>
	<div class="wrapper" style="margin-top:-10px;">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
 
  
  <div class="panel panel-default" style="margin-bottom:3px;" >
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" style="margin-top:-8px;">
          Grid Table
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body" >
          <!-- /.card-header -->
<div class="card-body " >
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
								
									<div id="alertMsgDiv" style="display: none;">
											<b style="color:red;font-size:12px;">The fetched data is exceeding the number of allowed data to show. Please set a filter to reduce it.
											
											<button id="clearAlert" >
											Clear alert
										</button>
											</b> 
																			
									</div>
									</div>
									<div class="col-sm-4 almgrid-global-search-box">
										Search
										<input type="text" class="form-control almgrid-global-search" />
									</div>
								</div>
										
								<div id= "tableGrid" class="table-responsive almgrid-table-div">
									<table id="gridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header fixed-header">											
												<th>Vendor Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Initial Cost
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th>Net Cost 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>		

												<th>Accumulated Depreciation
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														 </button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
																					
											<tr>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>											
											</tr>
										</thead>
										<tbody></tbody>
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
    </div>
  </div>

      </div>
      </div>
			</div>
</body>

<script type="text/javascript">

var date = new Date();
date.setDate(date.getDate() );
date.setFullYear(date.getFullYear() - 1); // Set the date one year before
date.setHours( 0,0,0,0 );

var dateEnd = new Date();
dateEnd.setDate(dateEnd.getDate());

var strtDate = Date.parse(date);

$('#startDate').datetimepicker({
	defaultDate:date
});

$('#endDate').datetimepicker({
	defaultDate:dateEnd
});



//collapse active class	
$('.panel-collapse').on('show.bs.collapse',function() {
	$(this).siblings('.panel-heading').removeClass('active');
});

$('.panel-collapse').on('hide.bs.collapse',function() {
	$(this).siblings('.panel-heading').addClass('active');
});

vendorAssetList = ${vendorAssetList}
var almgrid = new AlmgridTable(
		{
			tableId : "gridTable",
			dataArray : vendorAssetList,
			selectCheckbox : false,
			drawTableGrid : function(tableId, dataArray) {
				//let almgrid = this;
				var tableBody = document
						.querySelector("#" + tableId
								+ " tbody");
				var columnLinkNb = this.columnLinkNb;
				var gridContainer = document
						.querySelector("#" + tableId)
						.closest(".almgrid-container");
				var gridContainerId = tableId
						+ "_container";
				$(gridContainer).attr('id',
						gridContainerId);
				$(tableBody).empty();
				if (dataArray.length > 0) {
					var ArrayKeys = Object
							.keys(dataArray[0]);
				}

				// Method for pagination almgrid-pagecount-box
				$("#" + gridContainerId).find(
						".almgrid-pagecount-box").attr(
						"id", tableId + "_pagecount");
				$("#" + gridContainerId).find(
						".pagination-div").attr("id",
						tableId + "_pagination");

				// For global search textbox
				$("#" + gridContainerId)
						.find(".almgrid-global-search")
						.attr(
								"id",
								tableId
										+ "_globalsearch");

				var paginationId = tableId
						+ "_pagination";

				// Page Rows number
				var nbRows = $("#" + gridContainerId)
						.find(".almgrid-pagecount")
						.val();
				nbRows = parseInt(nbRows);

				this.pagination = new Pagination(
						{
							id : paginationId,
							tableId : tableId,
							noOfRows : nbRows,
							columnLinkNb : columnLinkNb,
							dataArray : dataArray,
							ArrayKeys : ArrayKeys,
							selectCheckbox : this.selectCheckbox
						});

				// Drawing for the first time
				if (this.initFlag == 0) {
					var tables = document
							.getElementsByClassName('almgrid-table');
					for (var i = 0; i < tables.length; i++) {
						resizableGrid(tables[i]);
					}

				}
				this.initFlag++;
			},
		});
$("#generate").click(function() {

	  var ignoreDateCheckbox = document.getElementById("ignoreDate").checked;

	  $("#gridTable").remove();
	  $("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header fixed-header"><th>Vendor Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Initial Cost<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>	<th>Net Cost <li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><th>Accumulated Depreciation<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th><tr><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th><th><input type="text" class="almgrid-search" placeholder="Search"></th></tr></thead><tbody></tbody></table>');
	  $
		.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateGridVendorAssetReport",

			data : {
				"startDate" : $("#startDate").val(),
			    "endDate" : $("#endDate").val(),
			    "ignoreDate":ignoreDateCheckbox

			},
			dataType : "json",
			success : function(data) {
				console.log(data.VendorAssetReportGrid.length);
				if (data.VendorAssetReportGrid.length == 0) {

					alert("No Data found between this two dates");
				} else {
					if (data.VendorAssetReportGrid != null) {
						vendorAssetList = data.VendorAssetReportGrid;
						almgrid = new AlmgridTable(
								{
									tableId : "gridTable",
									dataArray : vendorAssetList,
									selectCheckbox : false,
									drawTableGrid : function(
											tableId,
											dataArray) {
										//let almgrid = this;
										var tableBody = document
												.querySelector("#"
														+ tableId
														+ " tbody");
										var columnLinkNb = this.columnLinkNb;
										var gridContainer = document
												.querySelector(
														"#"
																+ tableId)
												.closest(
														".almgrid-container");
										var gridContainerId = tableId
												+ "_container";
										$(
												gridContainer)
												.attr(
														'id',
														gridContainerId);
										$(
												tableBody)
												.empty();

										var ArrayKeys = Object
												.keys(dataArray[0]);
										// Method for pagination almgrid-pagecount-box
										$(
												"#"
														+ gridContainerId)
												.find(
														".almgrid-pagecount-box")
												.attr(
														"id",
														tableId
																+ "_pagecount");
										$(
												"#"
														+ gridContainerId)
												.find(
														".pagination-div")
												.attr(
														"id",
														tableId
																+ "_pagination");

										// For global search textbox
										$(
												"#"
														+ gridContainerId)
												.find(
														".almgrid-global-search")
												.attr(
														"id",
														tableId
																+ "_globalsearch");

										var paginationId = tableId
												+ "_pagination";

										// Page Rows number
										var nbRows = $(
												"#"
														+ gridContainerId)
												.find(
														".almgrid-pagecount")
												.val();
										nbRows = parseInt(nbRows);

										this.pagination = new Pagination(
												{
													id : paginationId,
													tableId : tableId,
													noOfRows : nbRows,
													columnLinkNb : columnLinkNb,
													dataArray : dataArray,
													ArrayKeys : ArrayKeys,
													selectCheckbox : this.selectCheckbox
												});

										// Drawing for the first time
										if (this.initFlag == 0) {
											var tables = document
													.getElementsByClassName('almgrid-table');
											for (var i = 0; i < tables.length; i++) {
												resizableGrid(tables[i]);
											}

										}
										this.initFlag++;
									},
								});

					} else {

						alert("No Data found between this two dates");
					}
				}
				data = null;
			},
			error : function(error) {
				console
						.log(error.reponseText);
				console
						.log("Error");

			}
		});

});


var exportArrayGrid = [];
	//method to export the data into excel sheet
	function exportGrid() {

		downloadCSVFile(exportArrayGrid,
				"VendorAssetReport");

		function downloadCSVFile(csv, filename) {
			var csv_file, download_link;

			csv_file = new Blob([ csv ], {
				type : "text/csv"
			});

			download_link = document.createElement("a");

			download_link.download = filename;

			download_link.href = window.URL
					.createObjectURL(csv_file);

			download_link.style.display = "none";

			document.body.appendChild(download_link);

			download_link.click();
		}
	}

	$("#export").click(function() {
		console.log("clicked");
		//check if the data is filtered or not
		if (almgrid.filteredArray.length == 0) {
			fillGrid(vendorAssetList);

		} else {
			fillGrid(almgrid.filteredArray);
		}
		exportGrid();
	});

	//filling the grid with the needed data
	function fillGrid(filledGrid) {
		exportArrayGrid = [];
		var data = [];
		data.push('\r');
		data.push([ "Vendor","Initial Cost","Net Cost","Accumulated Depreciation" ]);
		var value = Object.keys(filledGrid[0]);
		for (i = 0; i < filledGrid.length; i++) {
			data.push('\r');
			for (j = 0; j < value.length; j++) {

				data.push(filledGrid[i][value[j]]);
			}

		}
		exportArrayGrid.push(data);

	}

</script>
</html>