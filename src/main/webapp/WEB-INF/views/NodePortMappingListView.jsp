<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<head>
		<meta charset="utf-8">
		<title>Node Port Mapping List View</title>
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
<!--  	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>  -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRowsNodePortMapping.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
		
		 <!-- export scripts -->
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
			
		
		
		
		
		



<style>
    #popUpDiv {
position:fixed;
top: 30%;
left: 50%;
background-color:#eeeeee;
border:5px solid #08526d;
width:400px;
height:auto;
margin-left:-150px;
margin-top:-95px;

-moz-border-radius: 16px;
-webkit-border-radius: 16px;
border-radius: 16px;
box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px#000000;

z-index: 9003;
 /*above nine thousand*/}

.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: auto;
  overflow : auto;
}
    
.modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
}

.viewPortMapping:hover{
    color: white;
	background-color: #007bff;
}


.viewPortMapping {
background-color:white;
color: #007bff;
 border-color: #939191f5;

}



.nav-link.active {
  color: #1D3763 !important;
}
</style>
	</head>

	<body>
<%-- 		<c:set var="page" value="Sales" /> --%>

<%-- 		<%@ include file="header.html" %> --%>
  <c:set var="pg" value="network" scope="session"  />
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
							<!--  -->
						   <button class="btn btn-secondary" type="button" id="export">Export</button>
	                     	
    	                  <!--  -->
								<button type="button" id="Fview" class="btn btn-light" data-toggle="tooltip"
									data-placement="top" title="Form View"> <i class="fa fa-edit"></i>
								</button>
								<button type="button" id="Lview" class="btn btn-danger" data-toggle="tooltip"
									data-placement="top" title="List View" style="background: #da6815;">
									<i class="fa fa-bars"></i>
								</button>
								<button type="button" id="searchNodePortMapping" class="btn btn-light" data-toggle="tooltip" data-placement="top"
									title="Search">
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
				<div class="modal-header" style="background-color: #2678CC;" >
				<h5 class="modal-title" id="myModalLabel1" style="font-weight:bold; color: #E9ECEF;position:relative;top:4px;">   Filter Options </h5>
				<button type="button" name="closePopup" id="closePopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	 <ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="date-tab" style="color: gold;" data-toggle="tab" href="#date" role="tab" aria-controls="date" aria-selected="true">Date</a>
  		</li>
  
  		<li class="nav-item">
   			 <a class="nav-link " id="clientinfo-tab" style="color: gold;" data-toggle="tab" href="#clientinfo" role="tab" aria-controls="clientinfo" aria-selected="false">Client Info</a>
  		</li>
  		
  		<li class="nav-item">
   			 <a class="nav-link " id="agentinfo-tab" style="color: gold;" data-toggle="tab" href="#agentinfo" role="tab" aria-controls="agentinfo" aria-selected="false">Agent Info</a>
  		</li>
  		
  		<li class="nav-item">
   			 <a class="nav-link " id="status-tab" style="color: gold;" data-toggle="tab" href="#status" role="tab" aria-controls="status" aria-selected="false">Status</a>
  		</li>
       <li class="nav-item">
   			 <a class="nav-link " id="region-tab" style="color: gold;" data-toggle="tab" href="#regioninfo" role="tab" aria-controls="region" aria-selected="false">Region</a>
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
  <div class="tab-pane" id="clientinfo" role="tabpanel" aria-labelledby="clientinfo-tab">
<p></p>
	<form>
		<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Mobile Number </span>
   					<input type="text" id="popupMobileNumber"  style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  
		
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >First Name</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupFname" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Last Name</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupLname" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>

    </div>
</div>	
</form>
</div> 

<div class="tab-pane" id="agentinfo" role="tabpanel" aria-labelledby="agentinfo-tab">
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Agent Number</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupAgentNumber" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Agent Name</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupAgentName" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>

    </div>
</div>	
</div>

  <div class="tab-pane" id="status" role="tabpanel" aria-labelledby="status-tab">
<p></p>
	
		<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Status </span>
   					<input type="text" id="popupStatus" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  
		
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Registration Status</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupRegStatus" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Tkash Status</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupTkashStatus" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>

    </div>
</div>	

</div> 
<div class="tab-pane" id="regioninfo" role="tabpanel" aria-labelledby="region-tab">
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Region</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupRegion" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Area</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupArea" style="width:674px; height:37px"  />
				</div>
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
<p></p>
<div class="row">
	<div class="col-12 col-sm-12 col-lg-12">
	   <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top:0px">
           <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"
           style="color: gold;text-align:center;line-height:2.75em; padding-left:5px;">NODE PORT MAPPING LISTVIEW</li>	
           
        <li class="nav-item ml-auto">
    		<button type="button" id="deleteButton" class="btn btn-primary BtnActive" disabled>
       			 <i class="fa fa-trash"></i> Delete
    		</button>

    		<button type="button" id="saveButton" disabled
		        class="btn btn-primary BtnActive">
		        <i class="fa fa-plus"></i> Add
		    </button>
		</li>

     </ul>

	</div>
</div>
				<!-- /.card-header -->
				
<div class="container-fluid">
<div class="tab-content" id="custom-tabs-one-tabContent">

<div class="tab-pane fade show active" id="custom-tabs-one-home"
	role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
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
								<div id="nodePortMappingGridTable" class="table-responsive almgrid-table-div">
									<table id="NodePortMappingTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header fixed-header">
											
												<th>
												<li class="filter-dropdown dropdown">
														<button disabled class="almgrid-filter" data-toggle="dropdown" style="display: none;">
														 <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i>
														</button>
														<ul class="dropdown-menu filter-dropdown-ul"></ul>
													</li>
												</th>	

												<th>Node Id
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Node Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Node Type
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Node Model
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Site Id
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Site Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

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
												<th><input type="text" disabled class="almgrid-search" style="display:none"></th>
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

	</div>
</div>


			<script>



				var NodePortMappingListData = ${ ListGridTable };
				var exportArrayGrid=[];				

				var tab =0;
				$(document).ready(function () {


					var almgrid = new Almgrid({
						tableId: "NodePortMappingTable",
						dataArray: NodePortMappingListData,
						columnLinkNb: [1],
						selectCheckbox: false
					});
					

        		
        			$(".almgrid-table").on("click", ".almgrid-link", function (e) {
            			
            				var param1 = $(this).parents('tr').find('td').eq(1).text();
        					var param = "${pageContext.request.contextPath}/NodePortMappingFormView?NodeID=" + param1 +"&NavAction=2";
        					window.location.href = param;
        					e.preventDefault();
                	
    					
    				});
    				
        		$("#export").click(function() {
              		if(almgrid.filteredArray.length == 0 ){
              			fillGrid(NodePortMappingListData);	
              		}
           			else{
              			fillGrid(almgrid.filteredArray);
              		}
              		exportGrid();
              	});   


				$( "#Fview" ).click(function() {
					var id =  $(".almgrid-link").closest('tr').find('td').eq(1).text();
					location.href= "${pageContext.request.contextPath}/NodePortMappingFormView?NodeID=" + id +"&NavAction=2";
				});
              	   		
        		function fillGrid(filledGrid){
        			exportArrayGrid=[];
        			exportArrayGrid.push('\r');
        			exportArrayGrid.push(["Node Id", "Node Name","Node Type","Node Model", "Site Id","Site Name", "Created Date","Last Modified Date"]);
        			var value = Object.keys(filledGrid[0]);
        			for(i=0;i<filledGrid.length;i++){
        				exportArrayGrid.push('\r');
        				for(j=1;j<value.length;j++){
        					exportArrayGrid.push(filledGrid[i][value[j]]);
        				}
        			}
        		}	
        			function exportGrid() {
	        		  const csvContent = 'data:text/csv;charset=utf-8,' + encodeURIComponent(exportArrayGrid);
	      			  const downloadLink = document.createElement('a');
	      			  downloadLink.setAttribute('href', csvContent);
	      			  downloadLink.setAttribute('download', "Node_Port_Mapping_List_Export");

	      			  document.body.appendChild(downloadLink);
	      			  downloadLink.click();
	      			  document.body.removeChild(downloadLink);  	        
        			
        			}
        			
        			
        			

        			
				});

				
			</script>



	</body>

</html>