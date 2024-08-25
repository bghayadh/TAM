<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title></title>
     <link rel="shortcut icon" href="">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>

		<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
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
	
	 <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
			
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
			
			
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
			
			<!--  Chart script -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
	
</head>
<style>

 #mapContainer {
        height: 600px;
       
       }



</style>
<body>

<%--     <c:set var = "page" value = "report"/> --%>

<%-- 	<%@ include file="../header.html" %> --%>
  <c:set var="pg" value="report" scope="session"  />
 <jsp:include page="../header.jsp"></jsp:include>
    <!--  end of general head page -->
    
    <!-- second report start--> 
   <div id= "areaReportDiv">
        <div class="container-fluid">     
	     <div class="row">
	     
	     <p></p>
		
		 </div>
 
		 <div class="row second">
			
			<div class="col-md-3" style="display: flex;">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Area Profit & Loss Report</span>
					</div>
				</div>
			</div>
			
		  <div class="col-md-3">
			  <div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="startdate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
					   <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
                </div>
	     </div>
	
	     <div class="col-md-3">
			<div class="form-group">
			  <div class="input-group-prepend" id="datetimepicker4" data-target-input="nearest">
				<span class="input-group-text">End Date</span>
					<input type="text" id="enddate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
					   <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
					 </div>
			</div>
          </div>
	    </div>
   
       <div class="col-md-3" style="text-align: right;">
       
		     <button type="button" id="generateReportArea" class="btn btn-primary BtnActive"  style="margin-top:-3px;"> Generate Report </button>
		        
	  </div>
		
	</div>
		
		 <div class="row second">
		
		  <div class="col-md-3">
		   <div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Area </span>
						<input type="text" id="area" value="${areaID}" class="form-control text-input" />
					</div>
				</div>
          </div>
		 
          <div class="col-md-auto">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Accumulated" name="Accumulated">
      					<span style="margin-left: 10px;">Accumulated</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     <div class="col-md-auto">
			
            	<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Monthly" name="Monthly" >
      					<span style="margin-left: 10px;">Monthly</span>
				   </div>
                  </div>
			
		  </div>
			
       </div>
      
      <div class="col-md-auto">
        
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Profitable" name="Profitable">
      					<span style="margin-left: 10px;">Profitable</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     
	      <div class="col-md-auto">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Loss" name="Loss">
      					<span style="margin-left: 10px;">Loss</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     
	      <div class="col-md-auto">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Max" name="Max">
      					<span style="margin-left: 10px;">Maximum</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     
	      <div class="col-md-auto">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Min" name="Min">
      					<span style="margin-left: 10px;">Minimum</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     
	     <div class="col-md-auto">
		   <div class="form-group">
				<div class="input-group-prepend" style="width:100%;">
    	                  
    	             <select id="technology" class="form-control text-input">
    	               <option value="null" selected>Technologies</option>
					   <option value="2g">2G</option>
			           <option value="2g3g">2G and 3G</option>
			           <option value="2g3g4g">2G, 3G and 4G </option>
			           
		            </select>
				</div>
		 </div>
  </div>
		 
	 
		
</div>
	
<div class="wrapper ">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default" style="margin-bottom:3px;">
    <div class="panel-heading " role="tab" id="headingOne">
      <h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
         GIS
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
      <div class="card-body">
        <div id="mapContainer"></div>
        </div>
       
      </div>
    </div>
  </div>
  <div class="panel panel-default" style="margin-bottom:3px;" >
    <div class="panel-heading" role="tab" id="headingTwo">
      <h4 class="panel-title">
       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          Grid Table
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
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
								<div class="table-responsive almgrid-table-div">
									<table id="gridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>Start Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>End Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
                                                   <th>Area Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th> 2G Sites
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>2G Profit & Loss
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>2G & 3G Sites
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												
												<th>2G & 3G Profit And Loss
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
                                                    <th>2G & 3G & 4G Sites
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												
												<th>2G & 3G & 4G Profit& Loss
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Total Number Of Sites
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
                                                  <th> Sum Of Profit And Loss
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
												
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
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
   <div id = "totalDiv">
   <div class="container-fluid"> 
	<div class="row">
	
	  <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Profit and Loss </span>
						<input type="text" id="tPL" value="${tPL}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Number of 2G Sites</span>
						<input type="text" id="tnumberof2g" value="${tnumberof2g}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Number of 2G and 3G Sites</span>
						<input type="text" id="tnumberof2g3g" value="${tnumberof2g3g}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 <div class="col-md-4">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Number of 2G, 3G and 4G Sites</span>
				<input type="text" id="tnumberof2g3g4g" value="${tnumberof2g3g4g}" readonly class="form-control text-input" />
				</div>
			</div>							
		</div>
		
		<div class="col-md-4">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Number of Areas</span>
				<input type="text" id="tNumberOfAreas" value="${tNumberOfAreas}" readonly class="form-control text-input" />
				</div>
			</div>							
		</div>
	</div>	
  </div>
</div>	
      </div>
    </div>
  </div>
  <div class="panel panel-default" style="margin-bottom:3px;">
    <div class="panel-heading" role="tab" id="headingThree">
      <h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseThree">
         Charts
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingThree">
      <div class="panel-body">
        <div class="card-body">
        
        <!-- start of chart 19-05 -->
        	<!-- start second row of charts -->
	<div class="container-fluid assetfinance">
		<div class="row">
			<div class="col-md-12">
				<div class="card card-primary card-tabs cards-margin">
					<div class="card-body cadr">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="custom-tabs-one-home"
								role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

								<div class="card-group">

									<div class="col-sm-12 cadr" >

										<div class="card card-primary card-tabs">

											<div class="card-body canvas-style3">

												<div class="tab-content">

													<div class="card card-primary card-tabs cards-margin">
														<div id="chartContainer2" ></div>
														<div id="chartContainer3" style="margin-top:70px;"></div>
														<div id="container" class="canvas-style2" style="margin-top:70px;" ></div>

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
	<!-- end of chart 19-05 -->
        </div>
       
      </div>
      </div>
    </div>
  </div>
</div>
</div>
  		
        
        
  </div>
  
  
  
  
  
			
</div>
  
  <!-- second report end--> 
  </body>
 <script>
    
    // start for report area div
   

function initMap() {


	// Define the Center
	//var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	var Beirut=new google.maps.LatLng(33.8938, 35.5018);	
    var map = new google.maps.Map(document.getElementById("mapContainer"), {
    //center:Nairobi ,
	    center:Beirut,
	    
    
  });
  map.setZoom(6);

  $.ajax({
	  type: "GET",
	  contentType: "application/json; charset=utf-8",
	  url: '${pageContext.request.contextPath}/GetAreasByDefault',
	  data: {
						
				 },

	dataType: "json",
	success: function (data) {

		if (data != null) {
		    document.getElementById("mapContainer").innerHTML ="";

			var map = new google.maps.Map(document.getElementById("mapContainer"), {
				zoom: 13,
			    center: { lat: 33.8938, lng: 35.5018},
			    //mapTypeId: "Beirut",
			});

				var areas = data.listAreas;

			              //  var destinations = [];
			              /*  $.each(data.listAreas, function (i, value) { 
			                	destinations.push({
	    					 		//ID: i,
	    					 		lat: data.listAreas[i][2],
		    					 	lng: data.listAreas[i][1],
	    					    });
			                });
			                */
			  /*              var lat = lng = Lat = Lng = finalarray = [];
			               
			                let arr3;
			                for (i = 0; i < data.listAreas.length; i++){
				                
			                		  lat = data.listAreas[i][2];
			                		  console.log("lat is: "+lat);
				                	  lng = data.listAreas[i][1];
				                	  console.log("lng is: "+lng);

				                	 
				                	 
				                	   // let n1 = lat.length;
				                	    let n1  =  lat.split(";").filter(function(str){return str!="";}).length;
				                	    console.log("lat length is: "+n1);
				                	    Lat = lat.split(";",n1);
				                	   // let n2 = lng.length;
				                	    let n2 = lng.split(";").filter(function(str){return str!="";}).length;
				                	    console.log("lng length is: "+n2);
				                	    Lng = lng.split(";",n2 );
				                	     arr3 = new Array(n1+n2);
				                	     arr3 = alternateMerge(Lat, Lng, n1, n2, arr3);

				                	   
				                	     finalarray [i] =  [arr3];
				                	  
				                	  
				                }
			                console.log("finalarray is: "+finalarray);
			                console.log("finalarray.length is: "+finalarray.length);
			                console.log("finalarray[i].length is: "+( finalarray[0]).length);
			                console.log("finalarray[i] is: "+( finalarray[0])[0][0]);
			                
			                function alternateMerge(arr1, arr2, n1, n2, arr3) {
				                let i = 0, j = 0, k = 0;
			     
			                    // Traverse both array
			                   while (i<n1 && j <n2){
			                      arr3[k++] = arr1[i++];
			                      arr3[k++] = arr2[j++];
			                   }
			                   
			                   console.log("arr3 is: "+arr3);
                               return arr3;
			                  
			                }
			                for (i = 0; i < finalarray.length; i++){
			                	 for (j = 0; j < finalarray[i].length; j++){
			                		 destinations = {
			                			lat:(finalarray[i])[j][j],
					    				lng:(finalarray[i])[j][j+1]
					    				 }

			                	 }
	    					 	
	    					 
			                }
			                	console.log("destinations is: "+destinations);
		     */            	//Replace ... with actual latitude and longitude points/arrays
		     drawPolygon(areas,map);

		     function drawPolygon(areas, map){

		    	  //Options for the map
	    	  var mapOptions = {
		    	    zoom: 13,
		    	    center: new google.maps.LatLng(33.8938, 35.5018),
		    	  }

		    	  //generate map
		    	  var map = new google.maps.Map(document.getElementById('mapContainer'), mapOptions);

		    	     var latlng = [];
			    	 var latlngfinal = [];
			    	 var fixedPath = [];
			    	 var area = null;
			    	 //var polygonOptions;
	                 //var polygon;
	                 console.log("areas is: "+areas);
			    	 for (var i= 0; i < areas.length; i++){
			    		 area = areas[i][0];
			    		 latlng = areas[i][1];
			    		 console.log(" areas[i][1] is: "+ areas[i][1]); 
			    		 console.log(" latlng[i] is: "+  latlng[0]); 

			    		 fixedPath = [];
			    		 for (var j= 0; j < latlng.length; j++){
			    		   fixedPath.push({
			    	    	  lat: parseFloat(latlng[j++]),
			    	          lng: parseFloat(latlng[j])
			    	       });
			    		 }

			    		 console.log("fixedPath is: "+fixedPath); 
			    		  //options for the polygon
				    	  var polyOptions = {
				    	    paths: fixedPath,
				    	    strokeColor: '#FF0000',
				    	    strokeWeight: 2,
				    	    strokeOpacity: 0.8,
				    	    fillColor: '#FF0000',
				    	    fillOpacity: 0.35,
				    	    editable: false, //editeren van de polygon
				    	    draggable: false, //verplaatsen van de polygon
				    	    area : area
				    	  };

				    	  //create the polygon
				    	  var polygon = new google.maps.Polygon(polyOptions);
				    	  polygon.setMap(map);
				    	  addListenersOnPolygon(polygon);
			    	 

			    		
			    		
			   		
			   		  }
			   		


		    	  // polygon event fill area name

		    	 function addListenersOnPolygon(polygon) {
                   google.maps.event.addListener(polygon, 'click', function (event) {
                     alert(polygon.area);
                     document.getElementById("area").value = polygon.area;
                     
                   });  
                 }    	

 /* 
                 var Path =null;
                 var line=[];
		    	 var latlng = [];
		    	 var latlngfinal = [];
		    	// var areas =areas;
		    	 var polygonOptions;
                 var polygon;
                 console.log("areas is: "+areas);
		    	 for (j= 0; j < areas.length; j++){
		    		 
		    		 latArray = areas[j][1].split(";");
		   		     lngArray = areas[j][2].split(";");

		   		     console.log("latArray is: "+latArray);
		   		     console.log("lngArray is: "+lngArray);
		     		  for(i = 0; i <latArray.length;i++){

		   			   
		   		    // polygon 
					    console.log("parseFloat(latArray[i]) is: "+parseFloat(latArray[i]));
					    console.log("parseFloat(lngArray[i]) is: "+parseFloat(lngArray[i]));
		   			    latlngfinal.push({lat: parseFloat(latArray[i]),lng: parseFloat(lngArray[i])});

					    
	                 
		   			  }	
		     		// console.log("latlng outside is: "+latlng);
		     		 console.log("latlngfinal is: "+latlngfinal);
		     		 console.log("latlngfinal is: "+latlngfinal[0].lat);
		     
		    	
                     
		    		 }  

		    	               polygonOptions = {
			                   paths: latlngfinal, //'path' was not a valid option - using 'paths' array according to Google Maps API
			                   strokeColor: "#EDE3D0",
			                   strokeWeight: "2",
			                   fillColor: '#598BE2',
			                   fillOpacity: 0.35,
			                   editable: false, //editeren van de polygon
				    		   draggable: false //verplaatsen van de polygon
			                   };
			                   polygon = new google.maps.Polygon(polygonOptions);

			                  //map needs to be defined somewhere outside of this loop
			                  //I'll assume you already have that.
			                   polygon.setMap(map);
			                  //latlngfinal = [];  
	    */              

		/*    	  var destinations =  [ 
              	    {lat: 33.90128047, lng: 35.49132866},
              	    {lat: 33.90113799, lng: 35.51862281},
              	    {lat: 33.88574886, lng: 35.51845115},
              	    {lat: 33.87178224, lng: 35.51364464},
              	    {lat: 33.87862331, lng: 35.48926872},
              	    {lat: 33.90128047, lng: 35.49132866}
              	  
	                ];
              var polygonOptions;
              var polygon;
            //  for (var i = 0; i < destinations.length; i++) {
                 polygonOptions = {
                 paths: destinations, //'path' was not a valid option - using 'paths' array according to Google Maps API
                 strokeColor: "#EDE3D0",
                 strokeWeight: "2",
                 fillColor: '#598BE2',
                 fillOpacity: 0.35
                 };
                 polygon = new google.maps.Polygon(polygonOptions);

                //map needs to be defined somewhere outside of this loop
                //I'll assume you already have that.
                polygon.setMap(map);
        */    
	    
	

	}

		 }
	 },
	 error: function(result) {
		 alert("Error");
		 }
	 });

	           

  
}
    $("#area").on('change',function(){
    	 if (document.getElementById("Accumulated").checked == true){
    		 $("#Profitable").attr('disabled', false);
         	 $("#Loss").attr('disabled', false);
         	 $("#Max").attr('disabled', false);
         	 $("#Min").attr('disabled', false);
         	
    			
    		}
    	

    	

           });
    
    
    
    
     $('input[name="Accumulated"]'). click(function(){
		if($(this). is(":checked")){
			document.getElementById("Monthly").checked = false;

		    console.log("Checkbox is checked." );
		    if ($("#area").val() != null){
      			document.getElementById("Profitable").checked = false;
      			document.getElementById("Loss").checked = false;
      			document.getElementById("Max").checked = false;
      			document.getElementById("Min").checked = false;
      		}
		    
		
		}
		else if($(this). is(":not(:checked)")){
		    console.log("Checkbox is unchecked." );
		}
 });
			 
		$('input[name="Monthly"]'). click(function(){
			if($(this). is(":checked")){
				 document.getElementById("Accumulated").checked = false;

				 
				 $("#Profitable").attr('disabled', false);
             	 $("#Loss").attr('disabled', false);
             	 $("#Max").attr('disabled', false);
             	 $("#Min").attr('disabled', false);
             	
				 
				 
			     console.log("Checkbox is checked." );
			}
			else if($(this). is(":not(:checked)")){
				
             
				
			    console.log("Checkbox is unchecked." );
			}     
		 });
		
		
		
	/*	$('input[name="Profitable"]'). click(function(){
			if($(this). is(":checked")){
				 document.getElementById("Accumulated").checked = false;
				 document.getElementById("Monthly").checked = false;
				 
			     console.log("Checkbox is checked." );
			}
			else if($(this). is(":not(:checked)")){
			    console.log("Checkbox is unchecked." );
			}     
		 });
		
		$('input[name="Loss"]'). click(function(){
			if($(this). is(":checked")){
				 document.getElementById("Accumulated").checked = false;
				 document.getElementById("Monthly").checked = false;
				 
			     console.log("Checkbox is checked." );
			}
			else if($(this). is(":not(:checked)")){
			    console.log("Checkbox is unchecked." );
			}     
		 });
		
		$('input[name="Max"]'). click(function(){
			if($(this). is(":checked")){
				 document.getElementById("Accumulated").checked = false;
				 document.getElementById("Monthly").checked = false;
				 
			     console.log("Checkbox is checked." );
			}
			else if($(this). is(":not(:checked)")){
			    console.log("Checkbox is unchecked." );
			}     
		 });
		
		$('input[name="Min"]'). click(function(){
			if($(this). is(":checked")){
				 document.getElementById("Accumulated").checked = false;
				 document.getElementById("Monthly").checked = false;
				 
			     console.log("Checkbox is checked." );
			}
			else if($(this). is(":not(:checked)")){
			    console.log("Checkbox is unchecked." );
			}     
		 });
 */
 
		 // set start date defualt value to month before 
//set start date defualt value to month before 
var today = new Date();
var date = (today.getMonth())+'/'+'1'+'/'+ today.getFullYear();
var time = '00' + ":" + '00' ;
var dateTime = date+' '+time;
var c = Date.parse(dateTime);
$('#startdate').datetimepicker({
    defaultDate:c
});
// set end date defualt value to current
 var dateNow = new Date();

$('#enddate').datetimepicker({
    defaultDate:dateNow
}); 



    
    // Collapse Functions
     
        $(document).ready(function() {
        	var ReportArrayGlobal = [];
        	//draw bar chart for the areas
            
            renderAllCharts();
            
        	$('.panel-collapse').on('show.bs.collapse', function () {
                
        	    $(this).siblings('.panel-heading').removeClass('active');
        	  });

        	  $('.panel-collapse').on('hide.bs.collapse', function () {
        	    $(this).siblings('.panel-heading').addClass('active');
        	  });
        	    
         /////start for report area div
            
            /// start of autocomplete of areaID
            $("#area").autocomplete({
	        source: function(request, response, event, ui) {
	        	
	        	 
		        		
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllAreasID',
		                 data: {
		                	 "areaID" : $("#area").val(),
			                 	
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.areaIDList);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 40, scroll:true,	
		         
		         
		         select: function(event, ui) {
		        	 area.value = (ui.item ? ui.item[0] + ";" + ui.item[1] : '');
		        
		        	 if (document.getElementById("Accumulated").checked == true){
		        		 $("#Profitable").attr('disabled', 'disabled');
                      	 $("#Loss").attr('disabled', 'disabled');
                      	 $("#Max").attr('disabled', 'disabled');
                      	 $("#Min").attr('disabled', 'disabled');
                      
	            			
	            		}

		        	 

		        	
						return false;
							}
				    }).autocomplete("instance")._renderItem = function(ul, item) {

			    		console.log("The item is " +item);

			    
			            return $("<li class='each'>")
			            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                    item[0] + "</span><br><span class='desc'>" +
			                    item[1] + "</span></div>")
			                .appendTo(ul);
		        	};
		        	$("#area").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});
	               ///// end autocomplete to areaID
            
            
           
            
            
            
       
            var areaReportData = ${AreaReportList};
			console.log("the data is "+areaReportData.length);
       
            
           	
            
            
           //Get the checkbox
			 var checkBox1 = document.getElementById("Accumulated");
			 var checkBox2 = document.getElementById("Monthly");
			 
			 var checkBox3 = document.getElementById("Profitable");
			 var checkBox4 = document.getElementById("Loss");
			 var checkBox5 = document.getElementById("Max");
			 var checkBox6 = document.getElementById("Min");

			 
      /*      // area report
            
            var div1 = document.getElementById("areaReportDiv");
            $("#areaReport").click(  function() {
               div1.style.display = "block";
       */      
               
              
             
                  $("#generateReportArea").click(  function() {
                	  
                	  
                	  var  Checkvalue;
                	  /// Accumulated
                	  if (checkBox1.checked == true){
                		  Checkvalue= "Accumulated";
            	          console.log("Checkbox1 is checked." );
            	         
                      }
                	  /// Monthly
                      if (checkBox2.checked == true){
                    	  Checkvalue = "Monthly";
            	          console.log("Checkbox2 is checked." );
            	         
                       }
                	  
                      /// Profitable
                      if (checkBox3.checked == true){
                    	  var profitableChecked = "Profitable";
            	          console.log("Checkbox3 is checked." );
            	         
                       }
                      
                      /// Loss
                      if (checkBox4.checked == true){
                          
                    	  var lossChecked = "Loss";
            	          console.log("Checkbox4 is checked." );
            	         
                       }
                      
                      /// Maximum
                      if (checkBox5.checked == true){
                    	  var maxChecked = "Max";

                    	  
            	          console.log("Checkbox5 is checked." );
            	         
                       }
                      /// Minimum
                      if (checkBox6.checked == true){

                    	 
                    	  var minChecked = "Min";
            	          console.log("Checkbox6 is checked." );
            	         
                       }
                      
                      if (checkBox1.checked == false && checkBox2.checked == false&&checkBox5.checked==false && checkBox6.checked==false){
	                		alert('Choose Option Accumlated or Monthly or Maximum or Minimum');
	                		return false;
	            	         
	                      }
                	  
                	  // disable totals when it is accumulated and area checked
                	  
                	  var totalDiv = document.getElementById("totalDiv");
                	  if(Checkvalue == "Accumulated" && $("#area").val() != "" || (checkBox3.checked == true || checkBox4.checked == true|| checkBox5.checked == true|| checkBox6.checked == true)){
                	  
                		 
                          totalDiv.style.display = "none";
                		  
                	  }
                	  else{
                		  totalDiv.style.display = "block";
                	  }
                	  
                      if (checkBox1.checked == false && checkBox2.checked == false &&checkBox5.checked == false && checkBox6.checked == false){
                 		  //alert('Please choose an option(Monthly/Accumulated)');
                    	  if ( checkBox3.checked == true|| checkBox4.checked == true){
                     		  alert('Please choose an option(Monthly/Accumlated)');
                     	  }
                    	 
                 	  }
                      
                   
                    
                       else{
                    	   
                    	   var area = $("#area").val();
                    	   area = area.split(";");
                    	   var areaID = area[0];
                    	   var areaName = area[1];

                    	   console.log('the area is ' +areaID + ' and the areaName is ' +areaName + ' and the area is ' +area);
                    	   
                    	   
                		$.ajax({
        					type : "GET",
        					contentType: "application/json; charset=utf-8",
        					url : "${pageContext.request.contextPath}/GenerateAreaReport",
        					dataType : "json",
        					data : {
        					    "checkValType": Checkvalue,
        					    "startDate" : $("#startdate").val(),
        					    "endDate" : $("#enddate").val(),
        					    "areaName" : areaName,
        					    "Profitable" : profitableChecked,
        					    "Loss" : lossChecked,
        					    "Max": maxChecked,
        					    "Min" : minChecked ,
        					    "technologies" : $("#technology").children("option:selected").val(),
        					   
        					},
        					success : function(data) {
        						
        						 if (data != null) {
        					        	ReportArrayGlobal = [];
                	                        
        	                       
        	                         
        	                         var areaReport = data.AreaReportList;
        	                         //var newdata = Object.values(data);
        	                        // console.log('the new data is '+newdata);
        	                         //tableData = data.AreaReportList;
        	                        // tableData = newdata;
        	                        // tableData1 = areaReport;
        	                         //areaReportTable.clear().rows.add(areaReport).draw();
        	                         
        	                         
        	                         $.each(areaReport, function (i, value) {
        	     						ReportArrayGlobal.push({
        	     							id:value[0],
        	     							startDate: (value[1].split(" "))[0],
        	     							enddate: (value[2].split(" "))[0],
        	     							area: value[3],
        	     							sites2g: value[4],
        	     							profit2g: value[5],
        	     						    sites2g3g: value[6],
        	     						   
        	     						    profit2g3g: value[7],
        	     						    sites2g3g4g: value[8],
        	     						    profit2g3g4g: value[9],
        	     						    sumsites: value[10],
        	     						    sumprofit:value[11]
        	     						});
        	     					});
        	             	                   
        	                    

        	                         var almgrid = new AlmgridTable({
                                         tableId: "gridTable",
                                         dataArray: ReportArrayGlobal,
                                         selectCheckbox: false,
                                         drawTableGrid :  function (tableid, dataarray) {
    				          				 console.log("******** emptyyyyy");
    				          					var span;
    				          					var dotStatus;
    				          					var dotItemStatus;
    				          			        var tableId = tableid;
    				          			        var dataArray = dataarray;
    				          			        var tableBody = document.querySelector("#" + tableId + " tbody");
    				          			        var columnLinkNb = this.columnLinkNb;
    				          					var view= this.view;
    				          			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
    				          			        var gridContainerId = tableId + "_container";
    				          			        $(gridContainer).attr('id', gridContainerId);
    				          			        console.log("Table id is "+tableId);
    				          			        $(tableBody).empty();
    				          			        console.log("The data array length is "+dataArray.length);
    				          			        if (dataArray.length > 0) {
													 
                                                
    				          			            var ArrayKeys = Object.keys(dataArray[0]);

    				          			            var itemRow = "";

    				          			       
    				          						 var sumsites2gCol = 0,sumsites2g3gCol = 0, sumsites2g3g4gCol = 0, sumsumprofitCol = 0;
    				          						 var column;
    				          			           for (var i = 0; i < dataArray.length; i++) {
    				          											itemRow += "<tr class='filterRows'>";
    				          			               for (var j = 0; j < ArrayKeys.length; j++) {
    				          			               	
    				          			                   column = ArrayKeys[j];
    				          			                 console.log("Table column name "+ArrayKeys[j]);
    				          			                   if (j == 0) {
    				          			                       if (this.selectCheckbox == true) {
    				          			                           itemRow += "<td class='table-select-all'><input type='checkbox' class='table-select-checkbox' value='" + dataArray[i][ArrayKeys[j]] + "'></td>";
    				          			                       }
    				          			                   } else {
    				          			                	 //console.log("Table column "+dataArray[i][ArrayKeys[j]]);
    					          			                   
    				          			                       itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>";

    				          			                    
    				          			                       if( column  == "sites2g"){
    				          			                    	   sumsites2gCol += parseInt(dataArray[i][ArrayKeys[j]]);
    				          			                       	  
    				          			                       }
    				          			                       if( column  == "sites2g3g"){
    				          			                    	 sumsites2g3gCol += parseInt(dataArray[i][ArrayKeys[j]]);
    				          			                       	  
    				          			                       }
    				          			                       if( column  == "sites2g3g4g"){
    				          			                    	   sumsites2g3g4gCol += parseInt(dataArray[i][ArrayKeys[j]]);
    				          			                       	  
    				          			                       }
    				          			                       if( column  == "sumprofit"){
    				          			                    	   sumsumprofitCol += parseInt(dataArray[i][ArrayKeys[j]]);
    				          			                       	   
    				          			                       }
    				          			                       
    				          			                     
    				          			                     
    				          			                   }

    				          			               }
    				          							 itemRow += "</tr>";
    				          							}

    				          			        
    				          			         tPL.value = sumsumprofitCol;
    		        	                         tnumberof2g.value = sumsites2gCol;
    		        	                         tnumberof2g3g.value = sumsites2g3gCol;
    		        	                         tnumberof2g3g4g.value =sumsites2g3g4gCol;
    		        	                         tNumberOfAreas.value = data.totalAreas;
    				          			        		
    					          			        	    
    				          			        

    					          			       
    				          					
    				          			
    				          			
    				          	            $(tableBody).append(itemRow);

    				          	        }
    				          			        else{

    				          			        	 tPL.value = 0;
        		        	                         tnumberof2g.value =0;
        		        	                         tnumberof2g3g.value = 0;
        		        	                         tnumberof2g3g4g.value =0;
        		        	                         tNumberOfAreas.value =0;
        				          			        


        				          			        }
    				          	       

    				         	        // Method for pagination almgrid-pagecount-box
    				          	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
    				          	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

    				          	        // For global search textbox
    				          	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

    				          	        var paginationId = tableId + "_pagination";

    				          	        // console.log("Table Drawn");

    				          	        // Page Rows number
    				          	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
    				          	        nbRows = parseInt(nbRows);

    				          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb });

    				          	        // var pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb });

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
        	                

        	                   
        	                         
        	                         //draw bar chart for the areas
        	                         
        	                         renderAllCharts();

        	                     
        	                        // document.getElementById("tNumberOfAreas").innerHTML = data.totalAreas;
        	                         
        						 }
        					
        					
                	  
        		  },
        		  
        		  error : function(error) {
						console.log("The error is " + error);
					}
                 });
               }
       
               
        });
       
		// ************************* init of renderAllCharts functions*************************

		var areaNames = [];
		var Site2g = [];
		var Site2g3g = [];
		var Site2g3g4g = [];

		
		var profit = [];
		var loss = [];
		var startDate = [];
		var endDate = [];
																			   
		
		function renderAllCharts(){
			
			areaNames = [];
			Site2g = [];
			Site2g3g = [];
			Site2g3g4g = [];

			
			profit = [];
			loss = [];
			startDate = [];
			endDate = [];

			//draw charts
			fetchProfitLossByTechnology();
			fetchProfitandLoss();
			fetchAreas(); 
			
			console.log("resolving the report chart graphs");	

	
															

	}
		// *************************** Multi-variate chart compaire ***************************
			var obj1 = {};
			var obj2 = {};
			var obj3 = {};
			var obj4 = {};
		
			async function fetchProfitandLoss(){

				if(ReportArrayGlobal !=null){
					
					for(var i=0;i<ReportArrayGlobal.length;i++){
						if(ReportArrayGlobal[i]["startDate"] != null && ReportArrayGlobal[i]["enddate"] != null){
							startDate.push(ReportArrayGlobal[i]["startDate"].toString());
							endDate.push(ReportArrayGlobal[i]["enddate"].toString());
						}else{
							//obj3.value = "No StartDate";
							startDate.push("no startDate");
							endDate.push("no endDate");
							}
						if(ReportArrayGlobal[i]["sumprofit"] != null && ReportArrayGlobal[i]["sumprofit"] > 0){
							profit.push(ReportArrayGlobal[i]["sumprofit"]);
						}else{
							profit.push("0");
							}
						if(ReportArrayGlobal[i]["sumprofit"] != null && ReportArrayGlobal[i]["sumprofit"] < 0){
							loss.push(ReportArrayGlobal[i]["sumprofit"]);
						}else{

																	  

							loss.push("0");
							}
					}
				console.log("fetch profit and loss chart");
				}else{
					console.log(" No areas to fitch profits and losses");
					profit = [];
					loss = [];
					startDate=[];
					endDate=[];
					} 
				let data = getProfitLossData(endDate,profit,loss); //[ startDate,profit,loss ]
		
				let schema = [{
				    "name": "Order Date",
				    "type": "date",
				    "format": "%-m/%-d/%Y"   //"%-Y-%-m-%d" 
															
					    }
				    	, {
				        "name": "Profit",
				        "type": "number"
				    }, {
				        "name": "Loss",
				        "type": "number"
				    }
				];  
				 
				let fusionDataStore = new FusionCharts.DataStore();
				let fusionTable = fusionDataStore.createDataTable(data, schema);
				 
			     const dataSource = {
				        data: fusionTable ,
				        chart: {
					         //caption: "Profit & Loss",
				             "theme": 'candy'

					    },
				        caption: {
				          text: 'Profit and Loss Analysis'
							},
							  yaxis: {
							        plot: {
							          type: "curve"
								        }
							      }
				      };

			       FusionCharts.ready(function() {
					      
		 		var multichart=  new FusionCharts({
				      type: 'timeseries',
				      renderAt: 'container',
				      width: "95%",
				      height: 650,
			       //   dataFormat: "json",	            
/* 				      dataSource: {
				        data: fusionTable,
				        chart: {
				        },
				        caption: {
				          text: 'Global Online Sales of a SuperStore'
										   
	  
					
				        }
				      } */
	
			     });
		 		multichart.configure("ChartNoDataText","No data to load. Please check the data source.");
		 		multichart.setJSONData(dataSource);		            
		 		multichart.render();
			
		       });

		   }
			//*********************** Multi-series Column 2D
			async function fetchProfitLossByTechnology(){
				var profitLoss2g=[];
				var profitLoss2g3g=[];
				var profitLoss2g3g4g=[];
				var objProfitLoss={};
				var	technologyAreaNames=[];
					if(ReportArrayGlobal !=null && ReportArrayGlobal.length !=0){
				for(var i=0 ;i<ReportArrayGlobal.length ;i++){
					if(ReportArrayGlobal[i]["area"] != null){
						technologyAreaNames.push(ReportArrayGlobal[i]["area"].toString());
						//obj1.value = ReportArrayGlobal[i]["area"].toString();			
						//areaNames.push(obj1);
						obj1 = [];
					}else{
						obj1.value = "No area";
						technologyAreaNames.push(obj1);
						obj1 = [];
						}
					
					if(ReportArrayGlobal[i]["profit2g"] != null){
						objProfitLoss.value=ReportArrayGlobal[i]["profit2g"];
						profitLoss2g.push(objProfitLoss);
						objProfitLoss={};
					}else{
						profitLoss2g.push("0");
						}
					
					if(ReportArrayGlobal[i]["profit2g3g"] != null){
						objProfitLoss.value=ReportArrayGlobal[i]["profit2g3g"];
						profitLoss2g3g.push(objProfitLoss);
						objProfitLoss={};
						
					}else{
						profitLoss2g3g.push("0");
						}
					
					if(ReportArrayGlobal[i]["profit2g3g4g"] != null){
						objProfitLoss.value=ReportArrayGlobal[i]["profit2g3g4g"];
						profitLoss2g3g4g.push(objProfitLoss);
						objProfitLoss={};
					}else{
						profitLoss2g3g4g.push("0");
						}

				}
					}
					else{
						technologyAreaNames.push('No Areas');
						profitLoss2g.push("0");
						profitLoss2g3g.push("0");
						profitLoss2g3g4g.push("0");
						
						
						
							}

					
			       FusionCharts.ready(function() {
			    	   var profitLossByTechnologyChart = new FusionCharts({
			    	     type: 'mscolumn2d',
			    	     renderAt: 'chartContainer3',
			    	     width: '95%',
			    	     height: '650',
			    	     dataFormat: 'json',
			    	     dataSource: {
			    	       "chart": {
			    	         "theme": "ocean",
			    	         "caption": "Comparison of Profit/Loss by Technology used",
			    	         "xAxisname": "Area",
			    	         "xAxisNameFontSize":"22px",
			    	         "yAxisNameFontSize":"22px",
			    	         "yAxisName": "Value (In USD)",
			    	         "labelFontSize":"18px",
			    	         "labelAlpha":"80",
			    	         "labelFontBold":"1",
			    	         "numberPrefix": "$",
			    	         "borderThickness": "4",
			    	         "plotFillAlpha": "80",
			    	         "divLineIsDashed": "1",
			    	         "divLineDashLen": "1",
			    	         "divLineGapLen": "1"
			    	       },
			    	       "categories": [{
			    	         "category": (technologyAreaNames=="No Areas")? technologyAreaNames :jQuery.parseJSON(getElement(technologyAreaNames))
				    	  
			    	       }],
			    	       "dataset": [{
				    	         "seriesname": "2G",
				    	         "data": profitLoss2g
				    	       },
				    	       {
				    	         "seriesname": "2G, 3G",
					    	     "data": profitLoss2g3g
					    	     },
				    	       {
				    	         "seriesname": "2G, 3G & 4G",
					    	     "data": profitLoss2g3g4g
					    	     }
				    	       ]
			    	       /* ,
			    	       "trendlines": [{
			    	         "line": [{
			    	           "startvalue": "1000",
			    	           "color": "#5D62B5",
			    	           "displayvalue": "Previous{br}Average",
			    	           "valueOnRight": "1",
			    	           "thickness": "1",
			    	           "showBelow": "1",
			    	           "tooltext": "Previous year quarterly target  : $1K"
			    	         }, {
			    	           "startvalue": "2000",
			    	           "color": "#29C3BE",
			    	           "displayvalue": "Current{br}Average",
			    	           "valueOnRight": "1",
			    	           "thickness": "1",
			    	           "showBelow": "1",
			    	           "tooltext": "Current year quarterly target  : $2K"
			    	         }, {
				    	           "startvalue": "3000",
				    	           "color": "#CCCC00",
				    	           "displayvalue": "Real{br}Average",
				    	           "valueOnRight": "1",
				    	           "thickness": "1",
				    	           "showBelow": "1",
				    	           "tooltext": "Current year quarterly target  : $3K"
				    	         }
			    	         
			    	         ]
			    	       }] */
			    	     }
			    	   });

			    	   profitLossByTechnologyChart.render();
			    	 });

				}
		// *************************** bar chart compaire ***************************
 
		async function fetchAreas() {
			var siteTechAreaNames=[];
			if(ReportArrayGlobal !=null && ReportArrayGlobal.length !=0){
			console.log(ReportArrayGlobal);
			for(var i=0 ;i<ReportArrayGlobal.length ;i++){
				if(ReportArrayGlobal[i]["area"] != null){
					siteTechAreaNames.push(ReportArrayGlobal[i]["area"].toString());
					//obj1.value = ReportArrayGlobal[i]["area"].toString();			
					//areaNames.push(obj1);
					obj1 = [];
				}else{
					obj1.value = "No area";
					siteTechAreaNames.push(obj1);
					obj1 = [];
					}
				if(ReportArrayGlobal[i]["sites2g"] !=null){
					obj2.value=ReportArrayGlobal[i]["sites2g"].toString()
					Site2g.push(obj2);		
					obj2 = {};
				}else {
					Site2g.push("0 2g Sites");
					   
					obj2 = {};
					}
				if(ReportArrayGlobal[i]["sites2g3g"] !=null){
					obj3.value=ReportArrayGlobal[i]["sites2g3g"].toString()
					Site2g3g.push(obj3);
					obj3 = {};
				}else{
					Site2g3g.push("0 2g3g Sites");
						 
					obj3 = {};
					}
				if(ReportArrayGlobal[i]["sites2g3g4g"] !=null){	
					obj4.value = ReportArrayGlobal[i]["sites2g3g4g"].toString()
					Site2g3g4g.push(obj4);	
					obj4 = {};
				}else{
					Site2g3g4g.push("0 2g3g4g Sites");			
						   
					obj4 ={};	
				}
			}
		
			
			console.log("fetch chart");
			}else{
				console.log("areas are null");
				siteTechAreaNames.push('No Areas');
				Site2g3g4g = {};
				Site2g={};
				Site2g3g={};
				} 
			var label1,label2,label3;
			
			if(Site2g == 0 && Site2g3g == 0 && Site2g3g4g == 0){
				siteTechAreaNames.push("No Areas");
				label1 = "No Areas";label2="";label3="";
				}
			else{
				label1 = "2G sites";label2="2G,3G sites";label3="2G,3G,4G sites";
				}

			  FusionCharts.ready(function () {
		            var myChart2 = new FusionCharts({

		                type: "stackedcolumn2d",
		                renderAt: "chartContainer2", // container where chart will render
		                width: "95%",
		                height: "650",
		                dataFormat: "json",	
		                dataSource: {
		                    // chart configuration
		                   chart: {
					         caption: "Area(s) Technology",
				              captionFontSize: "20",
				              captionFontColor: "#08526d",
				              captionFontBold: "1",
				              subcaption: "2G,3G and 4G sites",
				              subcaptionFontColor: "#254E7C",
				              subcaptioncaptionFontSize: "12",
					      	  xAxisname: "Areas",
				        	  yAxisName: "Count of Technology ( in Numbers)",
				    	      xAxisNameFontSize:"22px",
				    	      yAxisNameFontSize:"22px",
					          theme: "zune",
				              showsum: "1",
				              drawcrossline: "1",
				              bgColor: "#d9d9d9",
			                 // labelDisplay: "rotate",
			                  labelFont : "cex",
			                  labelFontSize: "18",
			                  labelFontColor : "#08526d",
			                  labelFontBold : "1",
			                  placeValuesInside : "0",
					       	  numberSuffix: " sites",
					       	showVLineLabelBorder:"1",
					       	labelLink : "https://www.google.com/?&hl=en"
					    },
					    	//chart categories
					    	categories: [
					        {
					        	category : (siteTechAreaNames=="No Areas")? siteTechAreaNames :jQuery.parseJSON(getElement(siteTechAreaNames))
					          
					        } 
					    ],
		                    // chart data
				      dataset: [
			              {
			                   seriesname: label1,
			                   data: Site2g                  
			              },
			              {
			                   seriesname: label2,
			                   data: Site2g3g
			              },
			              {
			                   seriesname: label3,
			                   data: Site2g3g4g         
			              }           
			            ]}            
			          });		            
					myChart2.render();
		       });
			}
		
				function getElement(areaNames){
					var strArea = {};
					
				//if(areaNames != "No Areas"){
					$.each(areaNames, function(key, value){
						if (key == 0 ) { if(areaNames.length>1) strArea = '[{"label":'+'"'+value.toString()+'"'+'},';
											else strArea = '[{"label":'+'"'+value.toString()+'"'+'}]';}
						else if (key < areaNames.length-1 && key > 0)	strArea +='{"label":'+'"'+value.toString()+'"'+'},'; 
						else strArea += '{"label":'+'"'+value.toString()+'"'+'}]'; 
						         
				         });
			        // }
					return strArea;
							  
					}

				function getProfitLossData(startDate,profit,loss){
					
					var obj = {};
					obj.arrObj = [];
					var dateSplit;
					var splittedDate;
					var lastFormat;
					$.each(startDate, function(key, value){
						splittedDate = startDate[key].split("-");
						lastFormat= parseInt(splittedDate[1])+"/"+parseInt(splittedDate[2])+"/"+splittedDate[0];
						obj.value=[lastFormat,parseFloat(profit[key]),parseFloat(loss[key])];
						obj.arrObj.push(obj.value);
							});
					console.log("arrObject is: " +obj.arrObj);
					
					return obj.arrObj;		
				}

				  /////end for report area 
 


                  $('#Accumulated').change(function() {
                	    if(this.checked == true){
                        var area=$('#area').val();

                        var checkBox2 = document.getElementById("Monthly");
                        console.log("MOnthly is "+checkBox2);
                        console.log("The rae is "+area);

                       
                        
                         if(area!=""){
                           
                      
                      	 $("#Profitable").attr('disabled', 'disabled');
                      	 $("#Loss").attr('disabled', 'disabled');
                      	 $("#Max").attr('disabled', 'disabled');
                      	 $("#Min").attr('disabled', 'disabled');
                      
                            }


                       
                        
                        
                    	   
                	         // $("#conditional_part").hide();
                	     }

                	    else{


                	    	
                	    	
                    	    
                	    	$("#Profitable").attr('disabled', false);
                         	 $("#Loss").attr('disabled', false);
                         	 $("#Max").attr('disabled', false);
                         	 $("#Min").attr('disabled', false);
                         	
                	    	
                    	    }

                	    
                	  
                	});


                  
                	

                  
              }); 
    </script> 
           
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"
	async defer>
	</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.charts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.candy.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.zune.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.ocean.js"></script>
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts-jquery-plugin.js"></script> -->








</html>