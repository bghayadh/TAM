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
<!-- 		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script> -->
	<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	

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

		<!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>

		


	</head>

<body>
<%--  <c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
<!--  end of general head page -->
	<div class="container-fluid">     
		<div class="row">
		<div class="col-md-12">
		<p></p>
		</div>
		
		</div>
	
		<div class="row second">
			<div class="col-md-9">
			</div>
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-light" data-toggle="tooltip" 
			 				data-placement="top" title="Form View"  onclick='window.location.href = "${pageContext.request.contextPath}/ReportFormView"'> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-danger" data-toggle="tooltip" onclick='window.location.href = "${pageContext.request.contextPath}/ReportListView"'
			        			data-placement="top" title="List View" style="background: #da6815;"> 
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
	
	
	<p></p>
        
		<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top: 0px;">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;"> REPORT LIST</li>
	 
		            <li class="nav-item ml-auto">
						<button type="button" id="deleteButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-trash"></i> Delete
						</button>  
		                        
						<button type="button" id="saveButton" onclick='window.location.href = "${pageContext.request.contextPath}/ReportFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
						</button>  </li>
									
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
				<div class="table-responsive almgrid-table-div">
					<table id="gridTable" class="table table-striped table-bordered almgrid-table">
						<thead>
							<tr class="header">
								<th class="table-select-all">
								</th>
								
								<th>Report ID
									<li class="filter-dropdown dropdown">
										<button class="almgrid-filter" data-toggle="dropdown"> <i
												class="fa fa-list almgrid-filter-i"
												aria-hidden="true"></i></button>
										<ul class="dropdown-menu filter-dropdown-ul">

										</ul>
									</li>
								</th>
								
								<th>Report Name
									<li class="filter-dropdown dropdown">
										<button class="almgrid-filter" data-toggle="dropdown"> <i
												class="fa fa-list almgrid-filter-i"
												aria-hidden="true"></i></button>
										<ul class="dropdown-menu filter-dropdown-ul">

										</ul>
									</li>
								</th>

								<th>Created Date
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

							</tr>

							<tr>
								<th class="table-select-all"><input type="checkbox"
										class="table-select-all-checkbox"></th>
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

	var ListData = ${ ListGridTable };
	

	$(document).ready(function () {

		var ArrayGlobal = [];

		

		// TO READ FROM DATABASE
		$.each(ListData, function (i, value) {
			ArrayGlobal.push({
				TEST: value[0], // This is mandatory for value of checkbox, the rest are drawn in table
				ID: value[1],
				Name: value[2],
				CreationDate: value[3],
				LastModified: value[4]
			});
		});

		var almgrid = new Almgrid({
			tableId: "gridTable",
			dataArray: ArrayGlobal,
			columnLinkNb: [1],
			selectCheckbox: true
		});


		$("#deleteButton").click(function () {

			var deleteArray = [];

			$("#gridTable").find(".table-select-checkbox").each(function () {
				if ($(this).is(":checked")) {
					var checkboxVal = $(this).val();
					deleteArray.push(checkboxVal);
					console.log("checkboxVal is :"+checkboxVal);
				}

			});

			deleteArray = deleteArray.filter(function (elem, index, self) {
				return index === self.indexOf(elem);
				console.log("id is :"+deleteArray);
			});

			$.ajax({
				type: "GET",
				url: "${pageContext.request.contextPath}/ReportListViewDelete",
				dataType: "json",
				data: {
					"ID": deleteArray
				},
				success: function (data) {
					location.reload();
				},
				error: function (error) {
					console.log("The error is " + error);
				}
			});
			// console.log('slct is: ' + slct);

		});


		$(".almgrid-table").on("click", ".almgrid-link", function (e) {
			var param1 = $(this).attr('id');
			var param = "${pageContext.request.contextPath}/ReportFormView?rprtId=" + param1+"&scheduleList=scheduleList";
			window.location.href = param;
			e.preventDefault();
		});


	 $('#Fview').click(function(e) {

	 if (ArrayGlobal.length == 0) {

	 var param = "${pageContext.request.contextPath}/ReportFormView";
	 window.location.href = param;
	 e.preventDefault();
	 }

	 else {
		 
     var firstCell =  $(".almgrid-link").closest('tr').find('td:first-child input').val();
     var param = "${pageContext.request.contextPath}/ReportFormView?rprtId=" + firstCell;
	 window.location.href = param;
	 e.preventDefault();
	 }
	 
	 });

	});

</script>

	

</body>
</html>