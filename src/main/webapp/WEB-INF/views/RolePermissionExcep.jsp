
<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Role Permission Exception</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="shortcut icon" href="">
		<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
		<script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
		<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
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
		<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
		<script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/jspdf.plugin.autotable.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/tableHTMLExport.js"></script>
		<link href="${pageContext.request.contextPath}/resources/cssgrid/bootstrap-datepicker.min.css" rel='stylesheet'	type='text/css'>
		<script src="${pageContext.request.contextPath}/resources/scriptsgrid/bootstrap-datepicker.min.js" type='text/javascript'></script>
		<!-- ALM GRID Scripts -->
		<!-- ALM GRID Scripts -->
		 <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgridRolePermission.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRolePermExcep.js"></script>
		
        
            
            
            
            
            
            
            
            
            
            
</head>
<style>
.nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
              #FieldName {
        max-height: 200px; /* Set a maximum height for the dropdown */
        overflow-y: auto;  /* Enable vertical scrolling */
    }
            </style>
<body>
  
<%--   <c:set var = "page" value = "setup"/> --%>

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
		
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  			<div class="modal-dialog" role="document">
    			<div class="modal-content">
      				<div class="modal-header">
        				<h5 class="modal-title" id="exampleModalLabel">Add Role Permission Exception</h5>
        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
         	 			<span aria-hidden="true">&times;</span>
        				</button>
      				</div>
      				<div class="modal-body">
      					<div class="row second">
      						<div class="col-md-auto">
        						<div class="form-group">
		    						<label for="screenName">Screen Name</label>
		    						 <select class="custom-select" id="screenName">
    <option value="" selected disabled>Select a Screen</option>
    <c:forEach var="screen" items="${moduleScreenList}">
        <option value="${screen[1]}">${screen[1]}</option>
    </c:forEach>
</select>



		    					</div>
							</div>
							
							<div class="col-md-auto">
								<div class="form-group">
		    						<label for="role">role</label>
		    						<select class="form-control" id="role">
		    							<option selected>Choose...</option>
		      							
		    						</select>
		    					</div>
							</div>
							
							<div class="col-md-auto">
								<div class="form-group">
		    						<label for="role">Exception Type</label>
		    						<select class="form-control" id="ExceptionType">
		    							<option selected>Choose...</option>
		      							<option>Read</option>
								    	<option>Write</option>
								    	<option>Allow</option>
								    	<option>Disallow</option>
		    						</select>
		    					</div>
							</div>
							
      					</div>
      				</div>
      				<div class="form-group" style="margin-right: 15px; margin-left: 15px;">
    <label for="FieldName">Field Name</label>
    <select class="form-control" id="FieldName" >
        <option value="" selected disabled>Select a Field</option>
    </select>
</div>
      				<div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				        <button type="button" id="saveButton" class="btn btn-primary">Add</button>
      				</div>
    			</div>
  			</div>
		</div>
	
		<div class="row second">
			<div class="col-md-auto" style="display: flex;">
				<div class="form-group">
					<div class="input-group-prepend"  style="margin-top: 5px;">
						<span class="input-group-text" style="color: green">Role Permission Exception</span>
					</div>
				</div>
			</div>
			<div class="mx-auto">
			</div>			
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group mb-3" style="margin-top: 5px;">
  						<div class="input-group-prepend">
    						<label class="input-group-text" for="inputGroupSelect01" style="margin-right: 0px; margin-top: 0px;">Screen Name</label>
  						</div>
					 <select class="custom-select" id="screenNameSearch">
    <option value="" selected disabled>Select a Screen</option>
    <c:forEach var="screen" items="${moduleScreenList}">
        <option value="${screen[1]}">${screen[1]}</option>
    </c:forEach>
</select>



					</div>
                </div>
			</div>
			

			
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group mb-3" style="margin-top: 5px;">
  						<div class="input-group-prepend">
    						<label class="input-group-text" for="inputGroupSelect01" style="margin-right: 0px; margin-top: 0px;">role</label>
  						</div>
						<select class="custom-select" id="roleSearch">
							<option selected>Choose...</option>
							
						</select>
					</div>
				</div>
			</div>
			
			<div class="mx-auto">
			</div>			
			<!-- <div class="col-md-auto">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-light" data-toggle="tooltip" 
			 				data-placement="top" title="Form View"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-danger" data-toggle="tooltip"
			        			data-placement="top" title="List View" style="background: #da6815;"> 
			        			<i class="fa fa-bars"></i>
			        	</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search">
							<i class="fa fa-search"></i>
						</button>
			        </div>  
		        </div>
			</div> -->
		</div>
	
	
	<p></p>
		<!-- <div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top: 0px;">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;">ROLE PERMISSION</class="nav-link></li>
									
		     </ul>
		     
		</div>
		</div> -->
		
		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">	
      			<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             		<li class="nav-item"><a class="nav-link active"
            			id="custom-tabs-one-general-tab" data-toggle="tab"
            			href="#custom-tabs-one-general" role="tab"
            			aria-controls="custom-tabs-one-general" aria-selected="true" style="color: gold;">GENERAL</a></li>
            
		           
		       
		            
		           <li class="nav-item ml-auto">
	
						<button type="button" data-toggle="modal" data-target="#exampleModal" 
						id="saveButton" class="btn btn-primary BtnActive">
						<i class="fa fa-save"></i> New
						</button>
						
						
						
					</li>
							
     			</ul>
     
			</div>
		</div>
</div>


<div class="container-fluid">

<div class="tab-content" id="custom-tabs-one-tabContent">

<div class="tab-pane fade show active" id="custom-tabs-one-general" role="tabpanel" aria-labelledby="custom-tabs-one-general-tab">
		
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
								<div id="grid" class="table-responsive almgrid-table-div">
									<table id="PermExcepTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>Screen Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Exception Type 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Role  
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Field Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Field Value
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Action
													
												</th>

												
											</tr>

											<tr>
													<th><input type="text" class="almgrid-search" placeholder="Search"></th>
											
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												
											    <th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th></th>
												
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

<div class="tab-pane fade" id="custom-tabs-one-field" role="tabpanel" aria-labelledby="custom-tabs-one-field-tab">

</div>

<div class="tab-pane fade" id="custom-tabs-one-action" role="tabpanel" aria-labelledby="custom-tabs-one-action-tab">

</div>

</div>
</div>		
		
</body>


<script> 
$(document).ready(function() {
	
	var tableData = ${ListGridTable};
	
	var role = ${ListRole};

	var selectSearch = document.getElementById("roleSearch");
	var select = document.getElementById("role");

	role.forEach(function(roleName) {
	    var optionSearch = document.createElement("option");
	    optionSearch.text = roleName;
	    selectSearch.appendChild(optionSearch);

	     var option = document.createElement("option");
	    option.text = roleName;
	    select.appendChild(option);
	});

	
	 var almgrid = new AlmgridTable({
	        tableId: "PermExcepTable",
	        dataArray: tableData,
	        selectCheckbox: false,
	        drawTableGrid :  function (tableId, dataArray) {
			    
		        var tableBody = document.querySelector("#" + tableId + " tbody");
		        var columnLinkNb = this.columnLinkNb;
		        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
		        var gridContainerId = tableId + "_container";
		        $(gridContainer).attr('id', gridContainerId);
		        $(tableBody).empty();

		        if (dataArray.length > 0) {
	        		var ArrayKeys = Object.keys(dataArray[0]);		       		
	  	   		}
	  
	 	        // Method for pagination almgrid-pagecount-box
	  	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
	  	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

	  	        // For global search textbox
	  	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

	  	        var paginationId = tableId + "_pagination";


	  	        // Page Rows number
	  	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
	  	        nbRows = parseInt(nbRows);

	  	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

	  	        // Drawing for the first time
	  	        if (this.initFlag == 0) {
	  	            var tables = document.getElementsByClassName('almgrid-table');
	  	            for (var i = 0; i < tables.length; i++) {
	  	                resizableGrid(tables[i]);
	  	            }
														 
	  	        }
	  	        this.initFlag++;
	  	       },
	         });


	
	

	
	
	
	$('#screenNameSearch').change(function() {
	    var selectedScreenType = $("option:selected", this).text();

	    // Filter the tableData array based on the selected screen type
	    var gridArray = (selectedScreenType === "Choose...") ? tableData : tableData.filter(function(item) {
	        return item[0] === selectedScreenType;
	    });

	   

	    // Remove existing table if present
	    $("#PermExcepTable").remove();

	    // Append the new table
	    $("#grid").append('<table id="PermExcepTable" class="table table-striped table-bordered almgrid-table">'
	        + '<thead><tr class="header">'
	        + '<th>Screen Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
	        + '<th>Exception Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	        + '<th>Role<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	        + '<th>Field Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	        + '<th>Field Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	        + '<th>Action<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	        + '</tr><tr>'
	        + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	        + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	        + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	        + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	        + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	        + '<th></th>'
	        + '</tr></thead><tbody></tbody></table>');

	    // Initialize Almgrid with the filtered data
		 var almgrid = new AlmgridTable({
		        tableId: "PermExcepTable",
		        dataArray: gridArray,
		        selectCheckbox: false,
		        drawTableGrid :  function (tableId, dataArray) {
				    
			        var tableBody = document.querySelector("#" + tableId + " tbody");
			        var columnLinkNb = this.columnLinkNb;
			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
			        var gridContainerId = tableId + "_container";
			        $(gridContainer).attr('id', gridContainerId);
			        $(tableBody).empty();

			        if (dataArray.length > 0) {
		        		var ArrayKeys = Object.keys(dataArray[0]);		       		
		  	   		}
		  
		 	        // Method for pagination almgrid-pagecount-box
		  	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
		  	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

		  	        // For global search textbox
		  	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

		  	        var paginationId = tableId + "_pagination";


		  	        // Page Rows number
		  	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
		  	        nbRows = parseInt(nbRows);

		  	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

		  	        // Drawing for the first time
		  	        if (this.initFlag == 0) {
		  	            var tables = document.getElementsByClassName('almgrid-table');
		  	            for (var i = 0; i < tables.length; i++) {
		  	                resizableGrid(tables[i]);
		  	            }
															 
		  	        }
		  	        this.initFlag++;
		  	       },
		         });

	});


	
	$(document).ready(function() {
	    $('#roleSearch').change(function() {
	        var selectedRole = $("option:selected", this).text();

	        // Filter the tableData array based on the selected role
	        var gridArray = (selectedRole === "Choose...") ? tableData : tableData.filter(function(item) {
	            return item[2] === selectedRole; // Assuming role is in index 2
	        });

	        console.log("Zeinaa");

	        // Remove existing table if present
	        $("#PermExcepTable").remove();

	        // Append the new table with the filtered data
	        $("#grid").append('<table id="PermExcepTable" class="table table-striped table-bordered almgrid-table">'
	            + '<thead><tr class="header">'
	           + '<th>Screen Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
	            + '<th>Exception Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	            + '<th>Role<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	            + '<th>Field Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	            + '<th>Field Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
	            + '<th>Action</th>'
	            + '</tr><tr>'
	            + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	            + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	            + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	            + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	            + '<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
	            + '<th></th>'
	            + '</tr></thead><tbody></tbody></table>');

	        // Initialize Almgrid with the filtered data
	   	 var almgrid = new AlmgridTable({
		        tableId: "PermExcepTable",
		        dataArray: gridArray,
		        selectCheckbox: false,
		        drawTableGrid :  function (tableId, dataArray) {
				    
			        var tableBody = document.querySelector("#" + tableId + " tbody");
			        var columnLinkNb = this.columnLinkNb;
			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
			        var gridContainerId = tableId + "_container";
			        $(gridContainer).attr('id', gridContainerId);
			        $(tableBody).empty();

			        if (dataArray.length > 0) {
		        		var ArrayKeys = Object.keys(dataArray[0]);		       		
		  	   		}
		  
		 	        // Method for pagination almgrid-pagecount-box
		  	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
		  	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

		  	        // For global search textbox
		  	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

		  	        var paginationId = tableId + "_pagination";


		  	        // Page Rows number
		  	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
		  	        nbRows = parseInt(nbRows);

		  	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

		  	        // Drawing for the first time
		  	        if (this.initFlag == 0) {
		  	            var tables = document.getElementsByClassName('almgrid-table');
		  	            for (var i = 0; i < tables.length; i++) {
		  	                resizableGrid(tables[i]);
		  	            }
															 
		  	        }
		  	        this.initFlag++;
		  	       },
		         });

	    });
	});


	
	

	
	$('#example tbody').on( 'click', '#deletePerm', function () {
		var row = table.row($(this).closest('tr')).data();
		var permID = row[0];
		
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/rolePermissionDelete",
			dataType : "json",
			data : {
			     
				"permID" : permID,
							
			},
			success : function(data) {
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		
		
		
	});
	
	

	
	
});

document.addEventListener('DOMContentLoaded', () => {
	var tableData = ${ListGridTable};
	 var almgrid = new AlmgridTable({
	        tableId: "PermExcepTable",
	        dataArray: tableData,
	        selectCheckbox: false,
	        drawTableGrid :  function (tableId, dataArray) {
			    
		        var tableBody = document.querySelector("#" + tableId + " tbody");
		        var columnLinkNb = this.columnLinkNb;
		        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
		        var gridContainerId = tableId + "_container";
		        $(gridContainer).attr('id', gridContainerId);
		        $(tableBody).empty();

		        if (dataArray.length > 0) {
	        		var ArrayKeys = Object.keys(dataArray[0]);		       		
	  	   		}
	  
	 	        // Method for pagination almgrid-pagecount-box
	  	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
	  	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

	  	        // For global search textbox
	  	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

	  	        var paginationId = tableId + "_pagination";


	  	        // Page Rows number
	  	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
	  	        nbRows = parseInt(nbRows);

	  	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

	  	        // Drawing for the first time
	  	        if (this.initFlag == 0) {
	  	            var tables = document.getElementsByClassName('almgrid-table');
	  	            for (var i = 0; i < tables.length; i++) {
	  	                resizableGrid(tables[i]);
	  	            }
														 
	  	        }
	  	        this.initFlag++;
	  	       },
	         });
    // Initialize modal save button click event
    document.getElementById('saveButton').addEventListener('click', function() {
        // Get the values from the modal inputs
        const screenName = document.getElementById('screenName').value;
        const role = document.getElementById('role').value;
        const exceptionType = document.getElementById('ExceptionType').value;
        const fieldName = document.getElementById('FieldName').value;

        // Basic validation (check if any field is empty)
        if (!screenName || !role || !exceptionType || !fieldName) {
            alert('Please fill in all fields.');
            return;
        }

        // Generate a unique ID for the new row (timestamp or UUID)
        const newId = Date.now();

        // Call the addRow method on the Almgrid instance
        addRow(screenName, role, exceptionType, fieldName, newId);

        // Reset modal inputs
        document.getElementById('screenName').value = '';
        document.getElementById('role').value = '';
        document.getElementById('ExceptionType').value = '';
        document.getElementById('FieldName').value = '';

        // Close the modal
        $('#exampleModal').modal('hide');
    });
});



function savepermExcep(rowId,screenName,exceptionType,role,fieldName,fieldValue){

	 $.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/rolePermissionExcepSave",
			dataType : "json",
			data : {
			     
				"id": rowId,
				"screenName":screenName,
				"exceptionType":exceptionType,
				"role": role,
				"fieldName": fieldName,
				"fieldValue": fieldValue,
				
							
			},
			success : function(data) {
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});





	
}



function delpermExcep(rowId){

	 $.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/rolePermissionExcepDel",
			dataType : "json",
			data : {
			     
				"id": rowId,
				
							
			},
			success : function(data) {
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});





	
}



//Add onchange event listener to the screenName select element
$('#screenName').change(function() {
    var selectedValue = $(this).val();
    
    if (selectedValue) {
        // Send AJAX request
        $.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/getScreenFields",
            dataType: "json",
            data: {
                "selectedValue": selectedValue
            },
            success: function(response) {
                if (response.fieldNames) {
                    // Handle the received field names
                	 // Populate the FieldName dropdown
                    var $fieldNameDropdown = $('#FieldName');
                    $fieldNameDropdown.empty(); // Clear previous options
                    $fieldNameDropdown.append('<option value="" selected disabled>Select a Field</option>'); // Add default option
                    response.fieldNames.forEach(function(fieldName) {
                        $fieldNameDropdown.append('<option value="' + fieldName + '">' + fieldName + '</option>');
                    });

                    // Scroll to the bottom of the dropdown
                    $fieldNameDropdown.scrollTop($fieldNameDropdown[0].scrollHeight);
                } else {
                    console.error("Unexpected response format: ", response);
                }
            },
            error: function(xhr, status, error) {
                console.error("An error occurred: ", status, error);
            }
        });
    }
});

function addRow(screenName, role, exceptionType, fieldName) {
 

    // Set up action buttons and any other necessary components
    
    // Call save function with the appropriate parameters
    savepermExcep("", screenName, exceptionType, role, fieldName, "");
}



</script>


           
 </html>
