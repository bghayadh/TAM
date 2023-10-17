<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="shortcut icon" href="">
		<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
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

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>



	</head>

<body>
<%--  <c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
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
			 				data-placement="top" title="Form View"  onclick='window.location.href = "${pageContext.request.contextPath}/EmailAccountsFormView"'> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-danger" data-toggle="tooltip" onclick='window.location.href = "${pageContext.request.contextPath}/EmailAccountsListView"'
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
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;"> EmailAccounts LIST</li>
	 
		            <li class="nav-item ml-auto">
						<button type="button" id="deleteButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-trash"></i> Delete
						</button>  
		                        
						<button type="button" id="saveButton" onclick='window.location.href = "${pageContext.request.contextPath}/EmailAccountsFormView?type=addNew"'
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
												<th>ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Email Address
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												
											<tr>
											
												<th class="table-select-all"><input type="checkbox"
														class="table-select-all-checkbox"></th>
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
	
	
	
	<script> 
	//EmailAccounts
	var arListData = ${ ListGridTable };
	//ready function starts
	$(document).ready(function() {
		var ReportArrayGlobal = [];

		console.log("testing EmailAccountsListView");
		// TO READ FROM DATABASE
		
		$.each(arListData, function (i, value) {
			 
			ReportArrayGlobal.push({
				id:value[1],
				ID: value[1],
				EmailAccounts: value[2],
				
			});

			console.log("Test1" + ReportArrayGlobal);
		});
		console.log("Test2 " + ReportArrayGlobal);
		var almgrid = new Almgrid({
			tableId: "gridTable",
			dataArray: ReportArrayGlobal,
			columnLinkNb: [1],
			selectCheckbox: true
		});


		$(".almgrid-table").on("click", ".almgrid-link", function (e) {
			var param1 = $(this).attr('ID');
			var param = "${pageContext.request.contextPath}/EmailAccountsFormView?id=" + param1;
			window.location.href = param;
			e.preventDefault();

			console.log('ID is: '+ param1);
		});

		$("#deleteButton").click(function () {

			var slct = [];

			$("#gridTable").find(".table-select-checkbox").each(function () {
				if ($(this).is(":checked")) {
					var checkboxVal = $(this).val();
					slct.push(checkboxVal);
				}

			});

			slct = slct.filter(function (elem, index, self) {
				return index === self.indexOf(elem);
			});

			$.ajax({
				type: "GET",
				url: "${pageContext.request.contextPath}/EmailAccountsListViewDelete",
				dataType: "json",
				data: {
					"ID": slct
				},
				success: function (data) {
					//console.log("The returned data is " +data.BassamTest);
					location.reload();
				},
				error: function (error) {
					console.log("The error is " + error);
				}
			});
			console.log('slct is: ' + slct);

		});


		$("#Fview").click(function () {
			var obj = [];

			$.each(arListData, function (i, value) {
				obj.push({
					ID: value[0],
					EmailAccounts: value[2]
				
				});

	
			});

			//console.log("ID " + param1);
			if (obj[0] == null){
				var param = "${pageContext.request.contextPath}/EmailAccountsFormView?type=addNew"
				}else{
			console.log("ID " + obj[0].ID);
			
			console.log("name is " + obj[0].sendAlertOn);
			var param1 = obj[0].ID;
			console.log("ID " + param1);
			var param = "${pageContext.request.contextPath}/EmailAccountsFormView?id=" + param1;}
			window.location.href = param;
		

		
		});


	
	});

	
</script>

	

</body>
</html>