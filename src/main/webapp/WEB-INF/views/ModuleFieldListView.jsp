<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Module Field List View</title>
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
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
		 <!-- export scripts -->
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
</head>
<style>
.nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
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
		
		
		
	
		<div class="row second">
			
					
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group mb-3" style="margin-top: 5px;">
  						<div class="input-group-prepend">
    						<label class="input-group-text" for="inputGroupSelect01" style="margin-right: 0px; margin-top: 0px;">Screen Name</label>
  						</div>
						<select class="custom-select" id="screenTypeSearch">
							

						</select>
					</div>
                </div>
			</div>
			
			<div class="col-md-2">
            <input type="text" id="screenId" style="display:none">
			</div>
			<div class="col-md-3"></div>
				<div style="width:90px;"></div>
		  <div class="col-md-2">
			<div class="input-group-prepend"><div class="input-group-text">
			<input type="checkbox" id="initializeWithIndex" style="margin-right:20px;"   />Initialize with current index </div>
			</div>
		
	</div>
        
    </div>			
		
				

			
		
			
			<div class="mx-auto">
			</div>			
		
		     
		</div>
		</div> 
		
		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">	
      			<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             	
		       
		            
		          
							
     			</ul>
     
			</div>
		</div>
</div>

<div class="row">
					<div class="col-12 col-sm-12 col-lg-12">
						<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
							style="background-color: #007b7c; margin-top: 0px;">
							<li class="nav-item">
								<class="nav-link inactive" id="custom-tabs-one-home-tab"
									style="color: gold;text-align:center;line-height:2.75em; padding-left:5px;">MODULE FIELD LIST
									
							</li>

							<li class="nav-item ml-auto">
							
					
							<button type="button" id="initializeButton" class="btn btn-primary BtnActive">
									<i class="fa fa-map"></i> Initialize
								</button>
								<button type="button" id="deleteButton" class="btn btn-primary BtnActive">
									<i class="fa fa-trash"></i> Delete
								</button>
                              

								<button type="button" id="addButton"
									class="btn btn-primary BtnActive">
									<i class="fa fa-plus"></i> Add
								</button>
									</button>
                              
							</li>

						</ul>

					</div>
				</div>
    
<div class="container-fluid">

<div class="tab-content" id="custom-tabs-one-tabContent">

<div class="tab-pane fade show active" id="custom-tabs-one-general" role="tabpanel" aria-labelledby="custom-tabs-one-general-tab">

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
							
								
<!--------------------------------------------------  END card-header ------------------------------------------>
		
								<div id="ModuleFieldGridTable" class="table-responsive almgrid-table-div">
									<table id="ModuleFieldTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												<th class="table-select-all">
												</th>
												

												<th>Module Field ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
                                                 <th>Screen Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Screen Table
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
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

												
												<th>Creation Date
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

<div class="tab-pane fade" id="custom-tabs-one-field" role="tabpanel" aria-labelledby="custom-tabs-one-field-tab">

</div>

<div class="tab-pane fade" id="custom-tabs-one-action" role="tabpanel" aria-labelledby="custom-tabs-one-action-tab">

</div>

</div>
</div>		
		
</body>


<script>




var data = ${ListGrid};
if(data){
Grid(data);

}


	
document.addEventListener('DOMContentLoaded', function () {
    var selectElement = document.getElementById('screenTypeSearch');
    var screenIdInput = document.getElementById('screenId');
    var initializeButton = document.getElementById('initializeButton');
    var deleteButton = document.getElementById('deleteButton');
    var addButton = document.getElementById('addButton');
    // Initially disable the Initialize and Delete buttons
    initializeButton.disabled = true;
    deleteButton.disabled = true;
    addButton.disabled = true;
    var screenData = ${ListGridTable}; 
    console.log(screenData);
var screenName = "${screenName}";
    var defaultOption = document.createElement('option');
    defaultOption.value = ""; // Empty value for default option
    defaultOption.textContent = "Choose Screen Name"; // Text for default option
    defaultOption.selected = true; // Make this option selected by default
    selectElement.appendChild(defaultOption);
    var screenNameSelected = false;
    screenData.forEach(function(item) {
        var option = document.createElement('option');
        option.value = item[0]; 
        option.textContent = item[1]; 
        selectElement.appendChild(option);
      
    });

   

    selectElement.addEventListener('change', function() {
        var selectedId = this.value; 
        screenIdInput.value = selectedId;

        if (selectedId) {
            initializeButton.disabled = false;
            addButton.disabled = false;// Enable button when a screen name is selected
        } else {
            initializeButton.disabled = true;
            addButton.disabled = true; // Disable button when no screen name is selected
        }

        $.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/getTableFields",
            dataType: "json",
            data: {
                "id": selectedId
            },
            success: function(response) {
                if (response.ListGridTable) {
                    Grid(response.ListGridTable);
                    console.log(response.ListGridTable);
                } else if (!response.ListGridTable) {
                    clearGrid();
                    var selectElement = document.getElementById('screenTypeSearch');
                    var selectedOption = selectElement.options[selectElement.selectedIndex];
                    var screenName = selectedOption.textContent; // Get the text content of the selected option
             
                    if(screenName != "Choose Screen Name"){
                    alert("You have to initialize ");}
                } else {
                    console.error("Unexpected response format: ", response);
                }
            },
            error: function(xhr, status, error) {
                console.error("An error occurred: ", status, error);
            }
        });
    });

    $("#initializeButton").click(function () {
        var screenId = $('#screenId').val();
        var selectElement = document.getElementById('screenTypeSearch');
        var selectedOption = selectElement.options[selectElement.selectedIndex];
        var screenName = selectedOption.textContent; 

          var initializeWithIndexChecked = $('#initializeWithIndex').is(':checked');
        $.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/initializeFields",
            dataType: "json",
            data: {
                "Screenid": screenId,
                "screenName": screenName,
                "initializeWithIndex": initializeWithIndexChecked,
            },
            success: function(response) {
                if (response.ListGridTable) {
                    if(initializeWithIndexChecked == true){
alert ("Initialize with current index done")
                        }
                    else{
                    	alert ("Initialize done")
                    	                        }
                    Grid(response.ListGridTable);
                    console.log(response.ListGridTable);
                }
            },
            error: function(error) {
                console.log("The error is " + error);
            }
        });
    });


    $("#deleteButton").click(function () {

    	var deleteArray = [];

    	$("#ModuleFieldTable").find(".table-select-checkbox").each(function () {
    		if ($(this).is(":checked")) {

    			var checkboxVal = $(this).closest('tr').find('td:nth-child(2)').text();
    				deleteArray.push(checkboxVal);
    			
    		}

    	});

    	deleteArray = deleteArray.filter(function (elem, index, self) {
    		console.log(deleteArray);
    		return index === self.indexOf(elem);
    	});
    	console.log('delete now');
    	 var screenId = $('#screenId').val();
    	$.ajax({
    		
    		type: "GET",
    		url: "${pageContext.request.contextPath}/DeleteModuleFieldListView",
    		dataType: "json",
    		data: {
    			"ID": deleteArray,
    			"id": screenId,
    		},
    		success: function (data) {
    			clearGrid();
    			Grid(data.ListGridTable);
    	    	
    		},
    		error: function (error) {
    			console.log("The error is " + error);
    		}
    	});
});
   
    	 $("#addButton").click(function (e) {
    	        e.preventDefault(); // Prevent the default button behavior

    	        var screenId = $('#screenId').val();
    	        var selectElement = document.getElementById('screenTypeSearch');
    	        var selectedOption = selectElement.options[selectElement.selectedIndex];
    	        var screenName = selectedOption.textContent;
    	        
    	        // Construct the URL with parameters
    	        var param = "${pageContext.request.contextPath}/ModuleFieldFormView?screenId="+screenId+"&screenName="+screenName;
    	        
    	        window.location.href = param;
    	    });
});

var gridData = []; // Global variable to track grid data

function Grid(data) {
    gridData = data; // Store data in global variable
    var almgrid = new Almgrid({
        tableId: "ModuleFieldTable",
        dataArray: data,
        columnLinkNb: [1],
        selectCheckbox: true
    });
    updateDeleteButtonState(); // Update button state based on data
}

function clearGrid() {
    gridData = []; // Clear data
    var almgrid = new Almgrid({
        tableId: "ModuleFieldTable",
        dataArray: [], // Empty data
        columnLinkNb: [1],
        selectCheckbox: true
    });
    updateDeleteButtonState(); // Update button state
}

function updateDeleteButtonState() {
    var deleteButton = document.getElementById('deleteButton');
    if (gridData && gridData.length > 0) {
        deleteButton.disabled = false;
         // Enable button if there is data
    } else {
        deleteButton.disabled = true; // Disable button if no data
    }
}

$(".almgrid-table").on("click", ".almgrid-link", function (e) {
	var param1 = $(this).attr('id');
	var param = "${pageContext.request.contextPath}/ModuleFieldFormView?ID=" + param1 +"&NavAction=2";
	window.location.href = param;
	e.preventDefault();
});

</script>





           
 </html>
