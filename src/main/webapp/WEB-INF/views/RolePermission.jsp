<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Role Permission</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	
	 <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgridRolePermission.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRowsRolePermission.js"></script>
		
            
</head>
<style>
.nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
</style>
<body>

  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>

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
        				<h5 class="modal-title" id="exampleModalLabel">Add Role Permission</h5>
        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
         	 			<span aria-hidden="true">&times;</span>
        				</button>
      				</div>
      				<div class="modal-body">
      					<div class="row second">
      						<div class="col-md-auto">
        						<div class="form-group">
		    						<label for="screenType">Screen Type</label>
		    						<select class="form-control" id="screenType">
		    							<option selected>Choose...</option>
		      							<option>Asset Registry</option>
		      							<option>Capital in Progress</option>
		      							<option>Discovery New</option>
		      							<option>Fixed Asset Registry</option>
		      							<option>Goods Received</option>
		      							<option>Item</option>
		      							<option>Purchase Order</option>
								    	<option>Purchase Request</option>
								    	<option>Physical Layer</option>
								    	<option>Physical Layer Manhole</option>
								    	<option>Physical Layer Handhole</option>
								    	<option>Physical Layer Fiber</option>
								    	<option>Physical Layer DB</option>
								    	<option>Supplier</option>
								    	<option>User</option>
								    	<option>Warehouse</option>
								    	<option>Work Order</option>
								    	<option>Image Approval</option>
		    						</select>
		    					</div>
							</div>
							
							<div class="col-md-auto">
								<div class="form-group">
		    						<label for="roles">Roles</label>
		    						<select class="form-control" id="roles">
		    							<option selected>Choose...</option>
		      							
		    						</select>
		    					</div>
							</div>
							
							<div class="col-md-auto">
								<div class="form-group">
		    						<label for="roles">View Type</label>
		    						<select class="form-control" id="viewType">
		    							<option selected>Choose...</option>
		      							<option>List</option>
								    	<option>Form</option>
								    	<option>Tree</option>
		    						</select>
		    					</div>
							</div>
							
      					</div>
      				</div>
      				<div class="form-group" style="margin-right: 15px; margin-left: 15px;">
					    <label for="LevelInput">Level</label>
					    <input type="number" class="form-control" id="LevelInput" min="0" max="10">
					</div>
      				<div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				        <button type="button" id="saveButton" class="btn btn-primary">Save</button>
      				</div>
    			</div>
  			</div>
		</div>
	
		<div class="row second">
			<div class="col-md-auto" style="display: flex;">
				<div class="form-group">
					<div class="input-group-prepend"  style="margin-top: 5px;">
						<span class="input-group-text" style="color: green">Role Permission</span>
					</div>
				</div>
			</div>
			<div class="mx-auto">
			</div>			
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group mb-3" style="margin-top: 5px;">
  						<div class="input-group-prepend">
    						<label class="input-group-text" for="inputGroupSelect01" style="margin-right: 0px; margin-top: 0px;">Screen Type</label>
  						</div>
						<select class="custom-select" id="screenTypeSearch">
							<option selected>Choose...</option>
							<option>Asset Registry</option>
							<option>Capital in Progress</option>
							<option>Discovery New</option>
							<option>Fixed Asset Registry</option>
							<option>Goods Received</option>
							<option>Item</option>
							<option>Purchase Order</option>
							<option>Purchase Request</option>
							<option>Physical Layer</option>
							<option>Physical Layer Manhole</option>
							<option>Physical Layer Handhole</option>
							<option>Physical Layer Fiber</option>
							<option>Physical Layer DB</option>
							<option>Supplier</option>
							<option>User</option>
							<option>Warehouse</option>
							<option>Work Order</option>
							<option>Image Approval</option>

						</select>
					</div>
                </div>
			</div>
			

			
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group mb-3" style="margin-top: 5px;">
  						<div class="input-group-prepend">
    						<label class="input-group-text" for="inputGroupSelect01" style="margin-right: 0px; margin-top: 0px;">Roles</label>
  						</div>
						<select class="custom-select" id="rolesSearch">
							<option selected>Choose...</option>
							
						</select>
					</div>
				</div>
			</div>
			
			<div class="mx-auto">
			</div>			
		</div>
	
	
	<p></p>
		
		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">	
      			<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             		<li class="nav-item"><a class="nav-link active"
            			id="custom-tabs-one-general-tab" data-toggle="tab"
            			href="#custom-tabs-one-general" role="tab"
            			aria-controls="custom-tabs-one-general" aria-selected="true" style="color: gold;">GENERAL</a></li>
            
		            <li class="nav-item"><a class="nav-link"
			            id="custom-tabs-one-field-tab" data-toggle="tab"
			            href="#custom-tabs-one-field" role="tab"
			            aria-controls="custom-tabs-one-field" aria-selected="false" style="color: gold;">FIELD & BUTTON</a></li>
		             
		            
		            <li class="nav-item"><a class="nav-link"
			            id="custom-tabs-one-action-tab" data-toggle="tab"
			            href="#custom-tabs-one-action" role="tab"
			            aria-controls="#custom-tabs-one-action" aria-selected="false" style="color: gold;">ACTION & APPROVAL</a></li>
             
		       
		            
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
																						
												<th>Screen Type 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>View Type 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												<th>Roles 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	
												<th style="width:120px;">Level 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> 
														<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>		

												<th >Permissions
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														 </button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>
												
												<th>Action
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
  
  <div class="tab-pane fade" id="custom-tabs-one-field" role="tabpanel" aria-labelledby="custom-tabs-one-field-tab">
</div>

<div class="tab-pane fade" id="custom-tabs-one-action" role="tabpanel" aria-labelledby="custom-tabs-one-action-tab">
</div>
  
  </div></div>
</body>


<script> 


$(document).ready(function() {
	
	var tableData = ${ListGridTable};
	var role = ${ListRole};

	var selectSearch = document.getElementById("rolesSearch");
	var select = document.getElementById("roles");

	role.forEach(function(roleName) {
	    var optionSearch = document.createElement("option");
	    optionSearch.text = roleName;
	    selectSearch.appendChild(optionSearch);

	     var option = document.createElement("option");
	    option.text = roleName;
	    select.appendChild(option);
	});

	 var almgrid = new AlmgridTable({
	        tableId: "gridTable",
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

	 $('input[type="checkbox"]').each(function() {
			if ($(this).val() == 1) {
				$(this).prop('checked', true);
			  }
			else{
				$(this).prop('checked', false);
			}
		});	

			$('#gridTable tbody').on( 'click', '#applyPerm', function () {

				var tableId = '#gridTable';
				var permID = $(this).closest('tr').find('button').attr('name');
				console.log("permID iis "+permID)
				var Read = 0; var Write = 0; var Add = 0; var Delete = 0; var Save = 0;
				var Status = 0; var Action = 0; var Download = 0; var Export = 0; var SecondLvL =0; var FirstLvL =0;
				var searchPopup =0; var findConnected=0; var projects =0;
				$(jQuery('input[type="checkbox"]', $(this).closest('tr'))).each(function() {
					if ($(this).val() == 1) {
						switch ($(this).attr('id')){
							case 'Read':
								Read = 1;
								break;
							case 'Write':
								Write = 1;
								break;
							case 'Add':
								Add = 1;
								break;
							case 'Delete':
								Delete = 1;
								break;
							case 'Save':
								Save = 1;
								break;
							case 'Status':
								Status = 1;
								break;
							case 'Action':
								Action = 1;
								break;
							case 'Download':
								Download = 1;
								break;
							case 'Export':
								Export = 1;
								break;
							case 'First Level Validation':
								FirstLvL = 1;
								break;
							case 'Second Level Validation':
								SecondLvL = 1;
								break;
							case 'Search Popup':
								searchPopup = 1;
								break;
							case 'Find Connected':
								findConnected = 1;
								break;
							case 'Projects':
								projects = 1;
								break;						
					  }
					}
					else{
						console.log('Not Checked');
						
					}
				});
				
				$(jQuery('#viewTypeDrop', $(this).closest('tr'))).each(function() {
					viewType = $("option:selected", this).text();
				});

			$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/rolePermissionApply",
					dataType : "json",
					data : {
					     
						"permID" : permID,
						"viewType" : viewType,
						"readPerm" : Read,
						"writePerm" : Write,
						"addPerm" : Add,
						"delPerm" : Delete,
						"savePerm" : Save,
						"statusPerm" : Status,
						"actionPerm" : Action,
						"downloadPerm" : Download,
						"exportPerm" : Export,
						"secondlvlPerm" : SecondLvL,
						"firstlvlPerm":FirstLvL,
						"searchPopup" : searchPopup,
						"findConnected" : findConnected,
						"projects":projects
					},
					success : function(data) {
						location.reload();
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});
				
				
			});

			$("#saveButton").click(  function() {
				 console.log('saved now');				 
				 if ($("#screenType").val()== '') {
				 alert('Please choose a Screen Type');
				 return false;}
				 
				 if ($("#viewType").val()== '') {
					 alert('Please choose a View Type');
					 return false;}
				 
				 if ($("#roles").val()== '') {
					 alert('Please choose a role');
					 return false;}
				 
				 if ($("#LevelInput").val()== null || $("#LevelInput").val()== "") {
					 alert('Please insert Level number');
					 return false;}
				 
				 var x = '0';
				 
					$.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/rolePermissionSave",
						dataType : "json",
						data : {
						     
							"permID" : x,
						    "screen" : $("#screenType").val(),
						    "viewType" : $("#viewType").val(),
							"role" : $("#roles").val(),
							"roleLevel" : $("#LevelInput").val(),
							"readPerm" : x,
							"writePerm" : x,
							"addPerm" : x,
							"delPerm" : x,
							"savePerm" : x,
							"statusPerm" : x,
							"actionPerm" : x,
							"downloadPerm" : x,
							"exportPerm" : x,
							"secondlvlPerm" : x,
							"firstlvlPerm":x,
							"searchPopupPerm" : x,
							"findConnectedPerm" : x,
							"projectsPerm":x
										
						},
						success : function(data) {
							location.reload();
						},
						error : function(error) {
							console.log("The error is " + error);
						}
					});
			});
		
			$('#rolesSearch').change( function(){

				var selectedRole = $("option:selected", this).text();

				if(selectedRole=="Choose..."){
					var gridArray = tableData;

				}
				else {
					 // Filter the tableData array based on the selected screen type
				    var gridArray = tableData.filter(function(item) {
				        return item[2] === selectedRole;
				    });
				}

				$("#gridTable").remove();
				$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table">'
						+'<thead><tr class="header fixed-header">'
						+'<th>Screen Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
							+'<th>View Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
							+'</ul></li></th><th> Roles<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
							+'</ul></li></th><th style="width:120px;"> Level<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
							+'</ul></li></th><th>Permissions<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
							+'</ul></li></th><th>Action<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
							+'<tr>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'</tr></thead><tbody></tbody></table>');	  

            	 var almgrid = new AlmgridTable({
                     tableId: "gridTable",
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

		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox });

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
			
			$('#screenTypeSearch').change(function() {
				var selectedScreenType = $("option:selected", this).text();

				if(selectedScreenType=="Choose..."){
					var gridArray = tableData;

				}
				else {
					 // Filter the tableData array based on the selected screen type
				    var gridArray = tableData.filter(function(item) {
				        return item[0] === selectedScreenType;
				    });
				}
				        

				$("#gridTable").remove();
				$("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table">'
						+'<thead><tr class="header fixed-header">'
						+'<th>Screen Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
							+'<th>View Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
							+'</ul></li></th><th> Roles<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
							+'</ul></li></th><th style="width:120px;"> Level<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
							+'</ul></li></th><th>Permissions<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
							+'</ul></li></th><th>Action<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
							+'<tr>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
							+'</tr></thead><tbody></tbody></table>');	  

            	 var almgrid = new AlmgridTable({
                     tableId: "gridTable",
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

		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox });

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

function deletePermission(permID){
	
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
}


function ChangeCheckboxState(checkbox) {
    if (checkbox.checked) {
        checkbox.value = '1';
    } else {
        checkbox.value = '0';
    }
}

</script>          
 </html>
