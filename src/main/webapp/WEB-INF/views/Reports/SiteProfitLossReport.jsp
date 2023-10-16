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

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
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
         
   
<script
	src=https://cdnjs.cloudflare.com/ajax/libs/markerclustererplus/2.1.4/markerclusterer.min.js></script>
	
	
				<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
	
	<!--  Chart script -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
	
                
         <style>

 #mapContainer {
        height: 700px;
       
       }
       
   .legendHeader {
  overflow: hidden;
 background-image: linear-gradient(to right top, #b3b3b3, #b6b6b7, #b8b9ba, #bbbdbd, #bdc0c0, #b1b5b5, #a5abaa, #9aa09f, #7f8685, #656e6c, #4c5655, #343f3e);
  padding: 10px 0px;
  
}

.btn-color {

 background-image: linear-gradient(to right top, #b3b3b3, #b6b6b7, #b8b9ba, #bbbdbd, #bdc0c0, #b1b5b5, #a5abaa, #9aa09f, #7f8685, #656e6c, #4c5655, #343f3e);

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
    
    
    .dot {
  height: 15px;
  width: 15px;
  
  border-radius: 50%;
  display: inline-block;
}
     .red {
        background: red;
      }
      .green {
      background:green;
      }
       .orange {
      background:#FF8C00;
      }
      .blue {
     /* background:#0080ff; */
     background:#0000CD; 
     
      }
      .purple {
      background:#0080ff;
      
      }
      .yellow {
      background:yellow;
      }

</style>
	
</head>
<body>
<%--  <c:set var = "page" value = "report"/> --%>

<%-- 	<%@ include file="../header.html" %> --%>
  <c:set var="pg" value="report" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>

  
	 <div Style=" left: 0; bottom: 0;" id="plPerSiteDiv">

	<div class="container-fluid">     
	     <div class="row">
	     
	     <p></p>
		
		 </div>
	

			<div class="row second" >	
			
			<div class="col-md-3" style="display: flex;">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">  Site Profit & Loss  Report</span>
					</div>
				</div>
			</div>
				
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="startdate1" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
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
					<input type="text" id="enddate1" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
					   <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
                </div>
			</div>
			
				
	
			
			<div class="col-md-3" style="text-align: right;  ">
		 		<div class="btn-group pull-right">
		 			<button type="button" id="generateReport" class="btn btn-primary BtnActive" style=" margin-top:-8px;" > Generate Report </button>
		 		</div>
		 	</div>
			
</div>
	
<div class="row" >
				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Warehouse</span>
							<input type="text" id="warcode"
								value="${warehouse}" class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Site Name</span>
							<input type="text" id="warname"
								value="${warehouseName}" class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Site ID</span>
							<input type="text" id="site"
								value="${siteId}" class="form-control text-input" />
						</div>
					</div>
				</div>
			
			<div class="col-md-3" style="text-align: right; padding-top:-100px;">
			<div class="btn-group pull-right" >
		 			<button type="button"  id ="clearButton"class="btn btn-light" style=" margin-top:-10px;"  >Clear</button>
		 		</div>
		 	</div>
		
			</div>
			
			
			
			<div class="row" >
			
			<div class="col-md-auto" >
            	<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input style="margin-left: 10px; "type="checkbox" value="0" id="accu">
      					<span style="margin-left: 10px">Accumulated</span>
				</div>
                </div>
			</div>
			</div>
			
			<div class="col-md-auto">
            	<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input style="margin-left: 10px; "type="checkbox" value="0" id="month">
      					<span style="margin-left: 10px">Monthly</span>
				</div>
                </div>
			</div>
			</div>
			<div id="option_filter" class="row" style="display:flex;">
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
				<div class="input-group-prepend">
    	                  
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
       <div class="legendContainer">
      <div class="card-body">
      
      
         <div class="box stack-top" id="legendDiv" style="overflow-y:scroll;position: relative;top:170px;width: 300px; float:left; height:320px;  background:white; margin:37px;display: none">
        
        <div class="legendHeader">
   
 <h6 style="color:#3C1596;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Options</h6>
 <button  style=" background-color:transparent;color:#3C1596;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAll()"  id="selectUnselect" >Unselect All</button>
  </div>
  
  <table>
  
   <caption style="color:#3C1596 ;font-weight:bold; font-size:2.5ex;position: relative; top:-246px;left:20px;">Legend</caption>
  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    
  </tr>
  
   
 
     <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="legendProfit" onclick="ShowHideMarkers('#0080ff');" value="#0080ff"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot purple"></div></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Profit</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="legendLoss" onclick="ShowHideMarkers('yellow');" value="yellow"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Loss</label></td>
    
    
  </tr>
  
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="legendMaxProfit"  onclick="ShowHideMarkers('green');" value="green"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot green"></div></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Profit</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="legendMaxLoss" onclick="ShowHideMarkers('red');" value="red"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Maximum Loss</label></td>
    
    
  </tr>
     <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="legendMinProfit" onclick="ShowHideMarkers('#0000CD');" value="#0000CD"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot blue"></div></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Profit</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="legendMinLoss" onclick="ShowHideMarkers('#FF8C00');" value="#FF8C00"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot orange"></div></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Minimum Loss</label></td>
    
    
  </tr>
  
  <tr>
  
  </tr>
    
   
  </table>
  
   <table>
  
    <hr style="position: relative;top: 15px;width:90%;text-align:left;margin-left:20px"></hr>
 
  <tr>
    <th style="position: relative;top: 2px;left:10px;"></th>
    <th style="position: relative;top: 2px;left:10px;"></th>
    
  </tr>
  
  
   <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="monthlyOptions" id="accuLegend" checked/></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Accumulated</label></td>
    
    
  </tr>
     <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="monthlyOptions" id="peakMonth"   /></td>
    
    <td style="position: relative;top: 25px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Peak Monthly</label></td>
    
    
  </tr>
  
 
   </table>
  
        </div>
        </div>
         
    <div class="card-body">
        <div class="box" id="mapContainer"></div>
        </div>
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
										<div class="input-group-prepend">
											<div id="loaderDiv" style="display: none;">
												<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
											</div>
										</div>
									</div>
									<div class="col-sm-4 almgrid-global-search-box">
										Search
										<input type="text" class="form-control almgrid-global-search" />
									</div>
								</div>
								<div class="row">
								<div class="col-sm-12">
										<div class="input-group-prepend">
										<div id="alertMsgDiv" style="display: none;padding-left: 40px">
										<br>
											<b style="color:red;font-size:15px;white-space: nowrap;">The number of original fetched data is exceeding the number of allowed data to show. Please set a filter to reduce the fetched data.</b> 
											</div>
									</div></div></div>
								<div class="table-responsive almgrid-table-div">
									<table id="gridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>Site ID
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Site Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
                                                   <th>Start Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th> End Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Area Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

											
												<th>Site Owner
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Tower Type
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Data Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Voice Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												
													<th>SMS Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
													<th>Gross Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
													<th>Data Traffic MB
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
													<th>SMS Traffic OG
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
											<th>Voice Traffic OG
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
													<th>Voice Traffic IC
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												
												<th>SMS Traffic IC
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Total Opex
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Profit and loss
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
		
		
	<div style="display: none;" id="totalNumbers">
		<div class="row second" style="padding-left: 15px;">								
			

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Data Revenue</span>
							<input readonly type="text" id="dataRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
				
				
				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">SMS Revenue</span>
							<input readonly type="text" id="smsRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
				
				
				
				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Voice Revenue</span>
							<input readonly type="text" id="voiceRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
				
			<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Gross Revenue</span>
							<input readonly type="text" id="grossRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
			

			
			</div>
			
			<div class="row second" style="padding-left: 15px;">								
			

				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Data Traffic MB</span>
							<input readonly type="text" id="dataTraf"
								 class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">SMS Traffic OG</span>
							<input readonly type="text" id="smsTrafOG"
								 class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Voice Traffic OG</span>
							<input readonly type="text" id="voiceTrafOG"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
			
			
			
			
			</div>
			
			<div class="row second" style="padding-left: 15px;">								
			

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Voice Traffic IC</span>
							<input readonly type="text" id="voiceTrafIC"
								 class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">SMS Traffic IC</span>
							<input readonly type="text" id="smsTrafIC"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
                    <div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Total Opex</span>
							<input readonly type="text" id="totalOpex"
								 class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Total Profit & Loss</span>
							<input readonly type="text" id="totalPL"
								 class="form-control text-input" />
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
</body>
<script>

function initMap() {


	// Define the Center
	//var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
		var Nairobi=new google.maps.LatLng(33.81673451460087, 35.77254745477668);
	
		
  var map = new google.maps.Map(document.getElementById("mapContainer"), {
    center:Nairobi ,
    zoom: 8
    
  });
//Get sites by default on load
  $.ajax({
	  type: "GET",
	  contentType: "application/json; charset=utf-8",
	  url: '${pageContext.request.contextPath}/GetSitesByDefault',
	  data: {
						
				 },

	dataType: "json",
	success: function (data) {

		if (data != null) {
		    document.getElementById("mapContainer").innerHTML ="";

			var map = new google.maps.Map(document.getElementById("mapContainer"), {

				 mapTypeControl: false,
				 center:Nairobi ,
				 zoom: 8,
				 mapTypeControl: true,
				 mapTypeControlOptions: {
					 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
					 position: google.maps.ControlPosition.TOP_CENTER,
					 },
					 zoomControl: true,
					 zoomControlOptions: {
						 position: google.maps.ControlPosition.LEFT_CENTER,
						 },


						 scaleControl: true,
						 streetViewControl: true,
						 streetViewControlOptions: {
							 position: google.maps.ControlPosition.LEFT_TOP,
							 },
							 
							 style: 'mapbox://styles/mapbox/streets-v11',
							 fullscreenControl: true,
							 });

			 
			 UnselectAll();
			 setColor(data.listSites,map);

			
			//Add legend button under zoom control on map
				const centerControlDiv = document.createElement("dv");
			    LegendControl(centerControlDiv, map);
			    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

							    
			    
			 }
		 },
		 error: function(result) {
			 alert("Error");
			 }
		 });

  ShowHideDiv();
  
  $("#peakMonth").attr('disabled', true);
  $("#accuLegend").attr('disabled', true);

  
	  }

//Css legend button under zoom control on map
function LegendControl(controlDiv, map) {
	
    const controlUI = document.createElement("dv");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
    controlUI.style.borderRadius = "3px";
    controlUI.style.boxShadow = "0 5px 6px rgba(0,0,0,.3)";
    controlUI.style.cursor = "pointer";
   //controlUI.style.marginRight = "10px";
    controlUI.style.marginLeft = "10px";
    controlUI.style.height = "300px";
    controlUI.style.textAlign = "center";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"  onClick="ShowHideDiv()"><i class="fas fa-arrow-right fa-lg "></i></button>';
    controlUI.title = "Open Legend";
    controlDiv.appendChild(controlUI);

  }


function ShowHideDiv(){
	$("#legendDiv").toggle();
}


var markerGroups = {
		  "green": [],
		  "red": [],
		  "#6A5ACD": [],
		  "#0080ff": [],
		  "yellow": [],
		  "#ff4000": [],
		  "gray": []
		};

var yellowCluster;
var blueCluster;
yellowMarks =[];
blueMarks =[];

function AddSiteMarkers(lst,map,color){
	  
	markers=[];
  if (color=="yelow"){
	yellowMarks = lst;
  }
  if (color=="#0080ff"){
	blueMarks = lst;
  }
	//Set zoom level
	map.setZoom(8);

	//var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	//Define the center
  var Nairobi=new google.maps.LatLng(33.81673451460087, 35.77254745477668);
	
	map.setCenter(Nairobi);
	
	for(i=0;i<lst.length;i++){
         
			markerId=lst[i][2];
			SiteID=lst[i][0];
			
			infowindow = new google.maps.InfoWindow();

			var latSite=lst[i][3];
			var lngSite=lst[i][4];
			const position = new google.maps.LatLng(latSite,lngSite);

			var SiteName=lst[i][1];				         
			
			var siteName="<b style='font-size:13px;'><u>Site: </u></b>"+SiteName;
			var siteLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+lst[i][3];
			var siteLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lst[i][4];
				
			var data="<div>"+siteName+"</br>"+siteLat+"</br>"+siteLong+"</div>";

			//Add markers on map
			const marker = new google.maps.Marker({
		        position: position,
		        data:data,
		        ID:markerId,
		        Name:SiteName,
		        SiteId:SiteID,
		        color:color,
		      
		        icon: {
		        	  path: google.maps.SymbolPath.CIRCLE,
				        fillColor: color,
				        fillOpacity: 0.9,
				        strokeColor: 'transparent',
				        strokeOpacity: 0.9,
				        strokeWeight: 1,
				        scale: 7	   
		            
		          }
		        
		         	    });

	 	    
			 	marker.metadata = { id: markerId };
			    markers[color] = marker;
			    markers.push(marker);

				//Add markers to array depending on colors
			    if (!markerGroups[color]) markerGroups[color] = [];
					  markerGroups[color].push(marker);
     

			google.maps.event.addListener(marker, "click", function (e) {

			     	infowindow.setContent(this.data); 
		        	infowindow.open(map,this);
			     	map.setZoom(15);
			     	map.setCenter(position);

			     	
					var IdSelected=this.ID;
					console.log("ID SELECTED IS "+IdSelected)
					var NameSelected=this.Name;
					var SiteIDSelected=this.SiteId
					
					document.getElementById("warcode").value =IdSelected ;
					document.getElementById("warname").value =NameSelected ;
					document.getElementById("site").value =SiteIDSelected ;
			
			 			});
	  				}


	
	//Calculator fcts
  if (color=="yellow"){
	var yellowcalc = function(markers, numStyles) {

		
		  for (var i = 0; i < markerGroups[color].length; i++) {
		  
		  if ( markerGroups[color][i] == markerGroups["yellow"] [i]) {

			  //return index of yellow img in clustererplus library
			  	      return {text: markers.length, index: 2};
		    }

		  }
		  return {text:markers.length, index: 2};
		}
  }
  if(color=="#0080ff"){
	var bluecalc = function(markers, numStyles) {

		
		  for (var i = 0; i < markerGroups[color].length; i++) {
		  
		  if ( markerGroups[color][i] == markerGroups["#0080ff"] [i]) {
			  
			  //return index of blue img in clustererplus library
			  	      return {text: markers.length, index: 1};
		    }

		  }
		  
		  return {text:markers.length, index: 1};
		}
  }


		//Set style of yellow and blue clusters
		var mcOptions = {styles: [{
		
			    height:25,
			    url: "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m2",
			    width: 30,
			    offsetX: 20,
	            offsetY: 20
		  }]
		};

		var mcOptions1 = {styles: [{
				    height: 30,
				    url: "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m1",
				    width: 35,
				    offsetX: 20,
		            offsetY: 20
			  }]
			};

		//Define yellow and blue clusters
      if (color=="yellow"){
      
		 yellowCluster = new MarkerClusterer(map,markerGroups["yellow"], {ignoreHiddenMarkers: true,mcOptions });
		 yellowCluster.setCalculator(yellowcalc);
      }
		if (color=="#0080ff"){
		 blueCluster = new MarkerClusterer(map,markerGroups["#0080ff"], {ignoreHiddenMarkers: true, mcOptions1});
		 blueCluster.setCalculator(bluecalc);
      }
		 
		 markerClusterer = new MarkerClusterer(map,  markerGroups["red"], {minimumClusterSize: 100,ignoreHiddenMarkers: true, mcOptions});
		 markerClusterer = new MarkerClusterer(map,  markerGroups["green"], {minimumClusterSize: 100,ignoreHiddenMarkers: true, mcOptions});
		 markerClusterer = new MarkerClusterer(map,  markerGroups["#FF8C00"], {minimumClusterSize: 100,ignoreHiddenMarkers: true, mcOptions});
		 markerClusterer = new MarkerClusterer(map,  markerGroups["#0000CD"], {minimumClusterSize: 100,ignoreHiddenMarkers: true, mcOptions});
		 markerClusterer = new MarkerClusterer(map,  markerGroups["gray"], {minimumClusterSize: 100,ignoreHiddenMarkers: true, mcOptions});
			

}

function DeleteMarkers() {
	for  (var i = 0; i < markers.length; i++) {
		var marker = markers[i];
		marker.setMap(null);
	}
	markerGroups = {
			  "green": [],
			  "red": [],
			  "#0080ff": [],
			  "yellow": [],
			  "#0000CD": [],
			  "#FF8C00": []
			};

	
	  	 
	}

function HideClusterIcon(MarkersArray,color) {

	console.log("color of cluster is" +color);
	
	if(color == "yellow") {
		for  ( i = 0; i<markerGroups["yellow"].length; i++) {	

		//Remove yellow markers from map			
		markerGroups["yellow"][i].setMap(null);
	}
	console.log("lossList from hide is:" +lossList);

	yellowCluster.clearMarkers();
	//clear yellow markers array
	markerGroups["yellow"] =[];
		
	}

	
	if (color == "#0080ff") {

		for  ( j = 0; j<markerGroups["#0080ff"].length; j++) {	
			//Remove yellow markers from map			
			markerGroups["#0080ff"][j].setMap(null);
		}
		console.log("profitList from hide is:" +profitList);

		blueCluster.clearMarkers();

		//clear blue markers array
		markerGroups["#0080ff"] =[];
			
	}
	
	}
	
	function ShowClusterIcon(losslst,profitlst,color) {

		console.log("color of cluster is" +color);
			 var Nairobi=new google.maps.LatLng(33.81673451460087, 35.77254745477668);
				
				
			  var map = new google.maps.Map(document.getElementById("mapContainer"), {
			    center:Nairobi ,
			    zoom: 8
			    
			  });


			  document.getElementById("mapContainer").innerHTML ="";

				var map = new google.maps.Map(document.getElementById("mapContainer"), {

					 mapTypeControl: false,
					 center:Nairobi ,
					 zoom: 8,
					 mapTypeControl: true,
					 mapTypeControlOptions: {
						 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
						 position: google.maps.ControlPosition.TOP_CENTER,
						 },
						 zoomControl: true,
						 zoomControlOptions: {
							 position: google.maps.ControlPosition.LEFT_CENTER,
							 },


							 scaleControl: true,
							 streetViewControl: true,
							 streetViewControlOptions: {
								 position: google.maps.ControlPosition.LEFT_TOP,
								 },
								 
								 style: 'mapbox://styles/mapbox/streets-v11',
								 fullscreenControl: true,
								 });

				// marker group lal yellow fade ma byeb3at eza malyen byeb3at
	             if (color=="yellow"){ 
	             if (markerGroups["#0080ff"].length>0  ){
	            	//Clear previous blue markers
	            	 HideClusterIcon(markerGroups["#0080ff"],"#0080ff");
	            	 //Add blue and yellow markers
				 AddSiteMarkers(losslst,map,"yellow");
	             AddSiteMarkers(profitlst,map,"#0080ff");
	             }
	            if (markerGroups["#0080ff"].length<=0  ){
				 AddSiteMarkers(losslst,map,"yellow");
	             
	             }
	             }

	             if (color=="#0080ff"){ 
	             if (markerGroups["yellow"].length>0  ){
		             //Clear previous yellow markers
	            	 HideClusterIcon(markerGroups["yellow"],"yellow");
	            	//Add blue and yellow markers
				 AddSiteMarkers(losslst,map,"yellow");
	             AddSiteMarkers(profitlst,map,"#0080ff");
	             }
	            if (markerGroups["yellow"].length<=0  ){
				 AddSiteMarkers(profitlst,map,"#0080ff");
	             
	             }
	             }
	            //Add legend button under zoom control on map
					const centerControlDiv = document.createElement("dv");
				    LegendControl(centerControlDiv, map);
				    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);
		}


function ShowHideMarkers(color) {
	  
	console.log("markers.length" +markerGroups[color].length);
	console.log("color is"+color);

	for ( x = 0; x < markerGroups[color].length; x++) {
		
	var marker = markerGroups[color][x];	

	//Show markers using checkboxes in popup
	if (!marker.getVisible()) {
	  marker.setVisible(true);

	}
	//hide markers using checkboxes in popup
	else {
	marker.setVisible(false); 
	}
		}


// Show/Hide ClusterIcon
	if(color == "yellow") {
		if ($('#legendLoss').prop("checked") == true) {
			ShowClusterIcon(lossList,profitList,"yellow");
			}

		
		else {
			console.log("UNCHECKED!!!!");

			HideClusterIcon(markerGroups["yellow"],"yellow");
  }	

	}
	 if(color == "#0080ff") {
		if ($('#legendProfit').prop("checked") == true) {
			 ShowClusterIcon(lossList,profitList,"#0080ff");
			}
		
		else {

			HideClusterIcon(markerGroups["#0080ff"],"#0080ff");
			}
		}

		}


function UnselectAll(){

    $("input[name='legendCheckbox']").prop('checked',false);
	
}

function SelectUnselectAll() {
checkedColor=[];

	if(max_profit_index >=0) {
		checkedColor.push("green");
		}

	if(min_profit_index >=0) {
		checkedColor.push("#0000CD");
				}

	if(max_loss_index >=0) {
		checkedColor.push("red");
				}

	if(min_loss_index >=0) {
		checkedColor.push("#FF8C00");
				}

	if(lossListIndex.length >0) {
		checkedColor.push("yellow");
				}

	if(profitIndexList.length >0) {
		checkedColor.push("#0080ff");
				}
	

	console.log("checkedColor array is" +checkedColor);

	
	var element = document.getElementById("selectUnselect");

	 if (element.innerHTML == "Unselect All"){
		 element.innerHTML = "Select All";
		 
			//Add checked checkboxes to colors array
			 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			 colors = [];
			checkboxes.forEach((checkbox) => {
			    colors.push(checkbox.value);
			});

			
			//Uncheck all checkboxes by value (by color)
			for (var l= 0; l < colors.length; l++) {
			
			$('input:checkbox[value="' + colors[l] + '"]').prop("checked", false);
			 }

			 
		 for (var l= 0; l < colors.length; l++) {
			 for (var i = 0; i < markerGroups[colors[l]].length; i++) {
			 	
			 	var marker = markerGroups[colors[l]][i];
			 	console.log(" markerGroups[color][i]"+markerGroups[colors[l]][i]);
			 	
				//Show markers

			 	if (!marker.getVisible()) {
			 	  marker.setVisible(true);
			 	} 
				//hide markers

			 	else {
			 	  marker.setVisible(false);
			 	}
			 }


		//Hide yellow cluster icon
				
		 if(colors[l] == "yellow") {
				
				if  ($('#legendLoss').prop("checked") == false) {
					HideClusterIcon(markerGroups["yellow"],"yellow");
					}
				}	

		//Hide blue cluster icon

		  if(colors[l] == "#0080ff") {
			  if  ($('#legendProfit').prop("checked") == false) {
				  HideClusterIcon(markerGroups["#0080ff"],"#0080ff");
				  }
			  }
		  }
		  }

	  else {

		  element.innerHTML = "Unselect All";

  	 	 for (var l= 0; l < checkedColor.length; l++) {
			$('input:checkbox[value="' + checkedColor[l] + '"]').prop("checked", true);
					 }

  	 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
		 colors = [];
		checkboxes.forEach((checkbox) => {
		    colors.push(checkbox.value);
		});

	 for (var l= 0; l < checkedColor.length; l++) {
		 for (var i = 0; i < markerGroups[checkedColor[l]].length; i++) {
		 	
		 	var marker = markerGroups[checkedColor[l]][i];
		 	console.log(" markerGroups[checkedColor][i]"+markerGroups[checkedColor[l]][i]);


		 	if (!marker.getVisible()) {
		 	  marker.setVisible(true);
		 	} else {
		 	  marker.setVisible(false);
		 	}
		 }


		//Show yellow cluster icon
		 if(checkedColor[l] == "yellow") {
				if ($('#legendLoss').prop("checked") == true) {
					ShowClusterIcon(lossList,profitList,"yellow");
					 }
				 }
		//Show blue cluster icon
		 if(checkedColor[l] == "#0080ff") {
				if ($('#legendProfit').prop("checked") == true) {
					ShowClusterIcon(lossList,profitList,"#0080ff");
					 }
				 }
		 }
	 }
	 }

$('#peakMonth'). click(function(){
	if($(this). is(":checked")){
		document.getElementById("accuLegend").checked = false; 
		$('#accuLegend').val('0');
		$(this).val('1');		

		console.log("peakMaxProfitList" +peakMaxProfitList);


		var Nairobi=new google.maps.LatLng(33.81673451460087, 35.77254745477668);
			
     		
		    document.getElementById("mapContainer").innerHTML ="";

			var map = new google.maps.Map(document.getElementById("mapContainer"), {

				 mapTypeControl: false,
				 center:Nairobi ,
			      zoom: 8,
				 
				 mapTypeControl: true,
				 mapTypeControlOptions: {
					 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
					 position: google.maps.ControlPosition.TOP_CENTER,
					 },
					 zoomControl: true,
					 zoomControlOptions: {
						 position: google.maps.ControlPosition.LEFT_CENTER,
						 },

						 scaleControl: true,
						 streetViewControl: true,
						 streetViewControlOptions: {
							 position: google.maps.ControlPosition.LEFT_TOP,
							 },
							 
							 style: 'mapbox://styles/mapbox/streets-v11',
							 fullscreenControl: true,
							 });

			const centerControlDiv = document.createElement("dv");
		    LegendControl(centerControlDiv, map);
		    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

			UnselectAll();
			DeleteMarkers();

		
     		setColor(peakMaxProfitList,map);
     		setColor(peakMaxLossList,map);
			AddSiteMarkers(peakMinProfitList,map,"#0000CD");
			AddSiteMarkers(peakMinLossList,map,"#FF8C00");
			
			UnselectAll();

			
			
     		if (peakMaxProfIndex >=0){
     	        $("#legendMaxProfit").prop('checked',true);   
     	        }

     		if (peakMaxLossIndex >=0){
     	        $("#legendMaxLoss").prop('checked',true);   
     	        }
     		if (peakMinProfIndex >=0){
     	        $("#legendMinProfit").prop('checked',true);   
     	        }
     		if (peakMinLossIndex >=0){
     	        $("#legendMinLoss").prop('checked',true);   
     	        }
     		
     		
     	



		
	}
	else if($(this). is(":not(:checked)")){
		$(this).val('0');

		
	}
});
		 
	$('#accuLegend'). click(function(){
		if($(this). is(":checked")){
			 document.getElementById("peakMonth").checked = false;
			$('#peakMonth').val('0');
			$(this).val('1');

			console.log("accuProfitList" +accuProfitList);



			var Nairobi=new google.maps.LatLng(33.81673451460087, 35.77254745477668);
  			
     		
  		    document.getElementById("mapContainer").innerHTML ="";

  			var map = new google.maps.Map(document.getElementById("mapContainer"), {

  				 mapTypeControl: false,
  				 center:Nairobi ,
 			      zoom: 8,
   				 
  				 mapTypeControl: true,
  				 mapTypeControlOptions: {
  					 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
  					 position: google.maps.ControlPosition.TOP_CENTER,
  					 },
  					 zoomControl: true,
  					 zoomControlOptions: {
  						 position: google.maps.ControlPosition.LEFT_CENTER,
  						 },

  						 scaleControl: true,
  						 streetViewControl: true,
  						 streetViewControlOptions: {
  							 position: google.maps.ControlPosition.LEFT_TOP,
  							 },
  							 
  							 style: 'mapbox://styles/mapbox/streets-v11',
  							 fullscreenControl: true,
  							 });

  			const centerControlDiv = document.createElement("dv");
			    LegendControl(centerControlDiv, map);
			    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

				UnselectAll();
				DeleteMarkers();

			
         		setColor(accuProfitList,map);
         		
         	
			
		  
		}
		else if($(this). is(":not(:checked)")){
			$(this).val('0');

					}     
	 });



peakMaxProfitList=[];
peakMinProfitList=[];
accuProfitList=[];
peakMaxLossList=[];
peakMinLossList=[];

var peakMaxProfIndex = peakMaxLossIndex = peakMinProfIndex = peakMinLossIndex =-1;

function AddPeakAccList(lstSite, peakLst, peakLossLst,peakMinProfit,peakMinLoss) {
peakMaxProfitList=[];
peakMinProfitList=[];
accuProfitList=[];
peakMaxLossList=[];
peakMinLossList=[];

for(i=0; i<peakLst.length;i++) {
	peakMaxProfitList.push(peakLst[i]);
	peakMaxProfIndex++;

	}
console.log("peakMaxProfitList" +peakMaxProfitList);


for(i=0; i<peakMinProfit.length;i++) {
	peakMinProfitList.push(peakMinProfit[i]);
	peakMinProfIndex++;

	}
console.log("peakMinProfitList" +peakMinProfitList);


for(i=0; i<peakLossLst.length;i++) {
	peakMaxLossList.push(peakLossLst[i]);
	peakMaxLossIndex++;

	}
console.log("peakMaxLossList" +peakMaxLossList);

for(i=0; i<lstSite.length;i++) {
	accuProfitList.push(lstSite[i]);

	}
console.log("accuProfitList" +accuProfitList);

for(i=0; i<peakMinLoss.length;i++) {
	peakMinLossList.push(peakMinLoss[i]);
	peakMinLossIndex++;

	}
console.log("peakMinLossList" +peakMinLossList);

}



function setColor(lst,map){

	UnselectAll();


	maxProfitList=[];
	minProfitList=[];
	maxLossList=[];
	minLossList=[];
	maxProfitList_index=[];
	maxLossList_index=[];
	minProfitList_index=[];
	minLossList_index=[];
	profitList=[];
	lossList=[];

	 var dataSite = lst;
	    console.log("Length"+dataSite.length);
	    console.log("Datasite"+dataSite);

	   var max_profit=max_loss=min_loss_index=min_profit_index=max_loss_index=max_profit_index =-1;

	   

	    console.log("Length"+dataSite.length);
	    
			
	    for (i=0;i<dataSite.length;i++){
		    //Set max profit site & max profit index
		    if (dataSite[i][6] > max_profit &&  dataSite[i][6] >0){
		    	max_profit = dataSite[i][6];
		    	max_profit_index = i;
		    
			    }
		    //Set max loss site & max loss index
		   if (dataSite[i][6] < max_loss &&  dataSite[i][6] <0) {
			  max_loss = dataSite[i][6];
			 max_loss_index = i;
		    
			    }
	    } // end 1st loop

	    
	    for (i=0;i<dataSite.length;i++){
		    //Check if other max profit site exists & add it to array
	        if (dataSite[i][6] == max_profit &&  dataSite[i][6] >0){
	            maxProfitList_index.push(i);
	        }
	    }
	    for (i=0;i<dataSite.length;i++){
		    //Check if other max loss site exists & add it to array
	        if (dataSite[i][6] == max_loss &&  dataSite[i][6] <0){
	            maxLossList_index.push(i);
	        }
	    }




	    
	    var min_profit = max_profit;
	    var min_loss = max_loss;

	   for (i=0;i<dataSite.length;i++){
		    //Set min profit site & min profit index
		    if (dataSite[i][6] < min_profit &&  dataSite[i][6] >0){
		    	min_profit = dataSite[i][6];
		    	min_profit_index = i;
		    
			    }
		    //Set min loss site & min loss index
		    if (dataSite[i][6] > min_loss &&  dataSite[i][6] < 0) {
			   min_loss = dataSite[i][6];
			   min_loss_index = i;
	    
		    }
	    } // end 2nd loop 

	    for (i=0;i<dataSite.length;i++){
		    //Check if other min profit site exists & add it to array
	        if (dataSite[i][6] == min_profit &&  dataSite[i][6] >0){
	            minProfitList_index.push(i);
	        }
	    }
	    for (i=0;i<dataSite.length;i++){
		    //Check if other min loss site exists & add it to array
	        if (dataSite[i][6] == min_loss &&  dataSite[i][6] <0){
	            minLossList_index.push(i);
	        }
	    }

	    // Check if there is only max profit site (No mininimum profit)
	    if (min_profit == max_profit){
		    //Clear minProfit index array when no mininimum profit site exists
	        minProfitList_index=[];
	    }
	    // Check if there is only max loss site (No mininimum loss)
	    if (min_loss == max_loss){
		    //Clear minLossIndex array when no mininimum loss site exists
	        minLossList_index=[];
	    }
		console.log("max_profit_index"+max_profit_index);
		console.log("min_profit_index"+min_profit_index);
		console.log("max_loss_index"+max_loss_index);
		console.log("min_loss_index"+min_loss_index);
		console.log("min_profit"+min_profit);
		console.log("max_profit"+max_profit);
		console.log("min_loss"+min_loss);
		console.log("max_loss"+max_loss);
		console.log("maxProfitList_index"+maxProfitList_index);


	//Coloring max profit 
	    for (z=0;z<maxProfitList_index.length;z++){
	        for (j=0;j<7;j++){
		        //Add max profit site to maxProfit array
	    		maxProfitList.push(dataSite[maxProfitList_index[z]][j]);
	        }
	    AddSiteMarkers([maxProfitList],map,"green");
	    //Clear array before adding another max profit site
	    maxProfitList=[] 
	    $("#legendMaxProfit").prop('checked',true);   
	    }

	    
	// Coloring  min profit 
	    for (s=0;s<minProfitList_index.length;s++){
	        for (j=0;j<7;j++){
		        //Add min profit site to minProfit array
	    		minProfitList.push(dataSite[minProfitList_index[s]][j]);
	        }
	    AddSiteMarkers([minProfitList],map,"#0000CD");
	    //Clear array before adding another min profit site
	    minProfitList=[] ;
	    $("#legendMinProfit").prop('checked',true);   
	    }

	// Coloring max Loss 
	    for (m=0;m<maxLossList_index.length;m++){
	        for (j=0;j<7;j++){
		        //Add max loss  site to maxLoss array
	    		maxLossList.push(dataSite[maxLossList_index[m]][j]);
	        }
	    AddSiteMarkers([maxLossList],map,"red");
	    //Clear array before adding another max loss site
	    maxLossList=[] ;
	    $("#legendMaxLoss").prop('checked',true);   
	    }
	// Coloring min Loss 

	    for (n=0;n<minLossList_index.length;n++){
	        for (j=0;j<7;j++){
		        //Add min loss  site to maxLoss array
	    		minLossList.push(dataSite[minLossList_index[n]][j]);
	        }
	    AddSiteMarkers([minLossList],map,"#FF8C00");
	    //Clear array before adding another min loss site
	    minLossList=[] ;
	    $("#legendMinLoss").prop('checked',true);   
	    }

// No profit-No loss
	if (max_loss_index == -1 && min_loss_index == -1 && max_profit_index ==-1 &&min_profit_index ==-1 )  { 
	console.log("No profit, No Loss");
	AddSiteMarkers(dataSite,map,"gray");
		}


	//profit - No max/No min
	siteList=[];
	profitIndexList=[];
	for (h=0;h<dataSite.length;h++){
		siteList.push(h);
	}
	var maxMinProfitIndex=maxProfitList_index.concat(minProfitList_index);
	comp=0;
	for (r=0;r<siteList.length;r++){
	    for(f=0;f<maxMinProfitIndex.length;f++){
	        if (r==maxMinProfitIndex[f]){
	            comp=1;
	        }
	    }
	    if (comp==0 && dataSite[r][6] >0){
	    	profitIndexList.push(r);
	    }
	    comp=0;
	}



	// loss 
	lossListIndex=[];
	var maxMinLossIndex =maxLossList_index.concat(minLossList_index);
	comp=0;
	for (z=0;z<siteList.length;z++){
	    for(t=0;t<maxMinLossIndex.length;t++){
	        if (z==maxMinLossIndex[t]){
	            comp=1;
	        }
	    }
	    if (comp==0 && dataSite[z][6] <0){
	    	lossListIndex.push(z);
	    }
	    comp=0;
	}

	var ProfitList_index=0;

	//  profit coloring
	    for (u=0;u<profitIndexList.length;u++){
	        
	    		profitList.push(dataSite[profitIndexList[u]]);
	        
	  	  }

	// Loss coloring

	    for (v=0;v<lossListIndex.length;v++){
	        
	    		lossList.push(dataSite[lossListIndex[v]]);
	        

	    }

        if (profitIndexList.length > 0 && lossListIndex.length > 0 ){

        AddSiteMarkers(lossList,map,"yellow");
	    AddSiteMarkers(profitList,map,"#0080ff");
	    $("#legendProfit").prop('checked',true);
        $("#legendLoss").prop('checked',true); 
	    
	    
	}
	    if (lossListIndex.length > 0 && profitIndexList.length <= 0 ){
	    AddSiteMarkers(lossList,map,"yellow");
	    $("#legendLoss").prop('checked',true);

	    
	    }

        if (lossListIndex.length <= 0 && profitIndexList.length > 0 ){
	    AddSiteMarkers(profitList,map,"#0080ff");
	    $("#legendProfit").prop('checked',true);

	    
	    }
console.log("Zeinab lOSS PROFIT");


}


//set start date defualt value to month before 
var today = new Date();
var date = (today.getMonth())+'/'+'1'+'/'+ today.getFullYear();
var time = '00' + ":" + '00' ;
var dateTime = date+' '+time;
var c = Date.parse(dateTime);
$('#startdate1').datetimepicker({
    defaultDate:c
});
// set end date defualt value to current
 var dateNow = new Date();

$('#enddate1').datetimepicker({
    defaultDate:dateNow
}); 
$("#warcode").on('change clear',function(){
	 if (document.getElementById("accu").checked == true){
		 $("#Profitable").attr('disabled', false);
    	 $("#Loss").attr('disabled', false);
    	 $("#Max").attr('disabled', false);
    	 $("#Min").attr('disabled', false);
    	
			
		}
});
$(document).ready(function() {
var	 ReportArrayGlobal = [];
	//draw bar chart for the areas
    
    renderAllCharts();
    

	$('.panel-collapse').on('show.bs.collapse', function () {
        
	    $(this).siblings('.panel-heading').removeClass('active');
	  });

	  $('.panel-collapse').on('hide.bs.collapse', function () {
	    $(this).siblings('.panel-heading').addClass('active');
	  });





	
	  /* $('a').click(function() { 
       	//console.log("After calling ajax");
       	// Remove 'active' tag for all list items 
           for (let i = 0; i < 12; i++) {
           	//console.log("i value is "  +i);
           	//$('.side li:eq(i) a').removeClass("active");
           	$('.side ul:first li:eq(' + i +') a').removeClass('active');
//           	$('.side ul:first li:eq(1) a').removeClass('active');
//           	console.log("Text of selected link is " +$('.side ul:first li:eq(0)').text());
//           	console.log("Text of selected link is " +$('.side ul:first li:eq(1)').text());
           	
           } 
           $(this).addClass("active"); 
       }); 
*/
  /*   $("#warcode").on('change',function(){

           alert("here");
    	   if($("#warcode").val() !="")
       	  	{
				$("#option_filter").css("display", "none");
         	 }
    	   else
       	   {
    		   $("#option_filter").css("display", "flex");
       	   }  

           });

      */
	/*	$('#perSite thead th').each( function () {
	        var title = $(this).text();
	        if(title == 'Site ID' || title == 'Site Name' || title == 'Start Date' || title == 'End Date' || title == 'Area Name' || title == 'Site Owner' || title == 'Tower Type' || title == 'Data Revenue' || title == 'Voice Revenue' || title == 'SMS Revenue' || title == 'Gross Revenue' || title == 'Data Traffic MB'||  title == 'SMS Traffic OG' || title == 'Voice Traffic OG'||  title == 'Voice Traffic IC' || title == 'SMS Traffic IC' || title == 'Total OPEX'  ||title == 'Profit and Loss' )
	        $('#columnSearch').append( '<th class="inputFilter"><input type="text" placeholder="Search '+title+'" /></th>' );
	    } );
		*/

	   $('#clearButton'). click(function(){  

       

         document.getElementById('warcode').value = '';
         document.getElementById('warname').value = '';
         document.getElementById('site').value = '';




		    });

	   
	   $('#accu'). click(function(){
      		if($(this). is(":checked")){
      			document.getElementById("month").checked = false; 
      			$('#month').val('0');
      			$(this).val('1');

      		    console.log("Checkbox is checked." );

      		  $("#peakMonth").attr('disabled', true);
        	  $("#accuLegend").attr('disabled', true);
      		
      		}
      		else if($(this). is(":not(:checked)")){
      			$(this).val('0');
      			        		
      		    console.log("Checkbox is unchecked. "+$(this).val());

      		  $("#peakMonth").attr('disabled', false);
        	  $("#accuLegend").attr('disabled', false);
      		}
       });
      			 
      		$('#month'). click(function(){
      			if($(this). is(":checked")){
      				 document.getElementById("accu").checked = false;
      				$('#accu').val('0')
      				$(this).val('1')
      			     console.log("Checkbox is checked." );
      				$("#Profitable").attr('disabled', false);
                	 $("#Loss").attr('disabled', false);
                	 $("#Max").attr('disabled', false);
                	 $("#Min").attr('disabled', false);
                	
                	 $("#peakMonth").attr('disabled', false);
                     $("#accuLegend").attr('disabled', false);
                		  

      			     
      			}
      			else if($(this). is(":not(:checked)")){
      				$(this).val('0')
      			
      			  console.log("Checkbox is unchecked. "+$(this).val());
        			  
      			}     
      		 });
      		
      		
          $("#warcode").autocomplete({
      		//showHeader: true,
      		//columns: columns,
           
               source: function(request, response) {
              	 
              	 var siteId=$("#site").val();
      	         var wareName=$("#warname").val();
      	        	
      	             $.ajax({
      	                 type: "GET",
      	                 contentType: "application/json; charset=utf-8",
      	                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
      	                 data: {
      							"warehouseName" : $("#warcode").val(),
      							 "WareName":wareName,
      							 "SiteId":siteId,
      					 },
      	                 dataType: "json",
      	                 success: function (data) {
      	                	 
      	                     if (data != null) {
      	                         response(data.globalList);
      	                         console.log('data is ;'+ data.globalList);
      	                         //colors = data.ListItemCategory;
      	                         //console.log('colors ;'+ colors);
      	                     
      	                     }
      	                 },
      	                 error: function(result) {
      	                     alert("Error");
      	                 }
      	             });
      	         }, minLength:0, maxShowItems: 4, scroll:true,		
                  
           
      	         select: function(event, ui) {
      	        	    warcode.value = (ui.item ? ui.item[0]  : '');
      	        	    document.getElementById("warname").value = ui.item[1];
      	        	    document.getElementById("site").value = ui.item[2];

      	        	  if (document.getElementById("accu").checked == true){
			        		 $("#Profitable").attr('disabled', 'disabled');
	                      	 $("#Loss").attr('disabled', 'disabled');
	                      	 $("#Max").attr('disabled', 'disabled');
	                      	 $("#Min").attr('disabled', 'disabled');
	                      
		            			
		            		}
   	    
      	        	  	//$("#option_filter").css("display", "none");
      					return false;
      						}
      			    }).autocomplete("instance")._renderItem = function(ul, item) {
      		            return $("<li class='each'>")
      	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
      	                    item[0] + "</span><br><span class='desc'>" +
      	                    item[1] +', '+ item[2]  +', '+ item[3] +', '+ item[4] +  "</span></div>")
      	                .appendTo(ul);
      	        	};
      				$("#warcode").focus(function(){
      	   	        	if (this.value == ""){
      	   	            	$(this).autocomplete("search");
      	   	        	}						
      				});
      				
      				$("#site").autocomplete({
      					showHeader: true,
      					
      			        
      			        source: function(request, response) {

      	                        var warehouse=$("#warcode").val();
      	                        var warehouseName=$("#warname").val();
      				        
      				             $.ajax({
      				                 type: "GET",
      				                 contentType: "application/json; charset=utf-8",
      				                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
      				                 data: {
      									  "warehouseName" : $("#site").val(),						 

      										// It will be modified
      										
      								 },
      				                 dataType: "json",
      				                 success: function (data) {
      				                     if (data != null) {

      					                     
      				                         response(data.globalList);
      				                        // it will have some modification here 
      				                     }
      				                 },
      				                 error: function(result) {
      				                     alert("Error");
      				                 }
      				             });
      				         }, minLength:0, maxShowItems: 40, scroll:true,		
      			               
      			        
      					select: function(event, ui) {
      						
      						site.value = (ui.item ? ui.item[2]   : '');
      						//$("#option_filter").css("display", "none");

      						$.ajax({
      			                type: "GET",
      			                contentType: "application/json; charset=utf-8",
      			                url: '${pageContext.request.contextPath}/GetAllWarehouse',
      			                data: {
      			                	warehouseName : ui.item[2],
      							 },
      			                dataType: "json",
      			                success: function (data) {
      			                    if (data != null) {
      				               
      				                console.log("The list is "+data.globalList[0]) ;

      				                WareId = data.globalList[0];
      				                console.log("/*/-WareId is" +WareId);

      				                if(data.globalList.length == 1){
      				                	 console.log("/*/*Entered here");
      				                	
      				                	$("#warcode").val(WareId);
      				                	warname.value=ui.item[1];
      				                	

      					                }
      				                

      				                else{


      				                	$("#warcode").val("");
      				                	

      					                }
      				                
      										                  
      			                    }
      			                },
      			                error: function(result) {
      			                    alert("Error");
      			                }
      			            });
      						

      							return false;
      								}
      					    }).autocomplete("instance")._renderItem = function(ul, item) {
      				            return $("<li class='each'>")
      			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
      			                    //item[0] + "</span><br><span class='desc'>" +
      			                   // item[1] + "</span><br><span class='desc'>" +
      			                    //item[2] + "</span></div>")
      			                    item[0] + "</span><br><span class='desc'>" 
      			                    )
      			                .appendTo(ul);
      			        	};
      						$("#site").focus(function(){
      			   	        	if (this.value == ""){
      			   	            	$(this).autocomplete("search");
      			   	        	}						
      						});





      						$("#warname").autocomplete({
      							showHeader: true,
      							
      					        
      					        source: function(request, response) {

      			                        var warehouse=$("#warcode").val();
      			                        var siteid=$("#site").val();
      			                       
      						        
      						             $.ajax({
      						                 type: "GET",
      						                 contentType: "application/json; charset=utf-8",
      						                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
      						                 data: {
      											"warehouseName" :  $("#warename").val(),				 

      												// It will be modified
      												
      										 },
      						                 dataType: "json",
      						                 success: function (data) {
      						                     if (data != null) {

      							                     
      						                         response(data.globalList);
      						                        // it will have some modification here 
      						                     }
      						                 },
      						                 error: function(result) {
      						                     alert("Error");
      						                 }
      						             });
      						         }, minLength:0, maxShowItems: 40, scroll:true,		
      					               
      					        
      							select: function(event, ui) {
      								
      								warname.value = (ui.item ? ui.item[1]   : '');
      								 
      								
      								
      								//$("#option_filter").css("display", "none");

      								$.ajax({
      					                type: "GET",
      					                contentType: "application/json; charset=utf-8",
      					                url: '${pageContext.request.contextPath}/GetWareID',
      					                data: {
      											WareName : ui.item[1],
      									 },
      					                dataType: "json",
      					                success: function (data) {
      					                    if (data != null) {
      						               
      						                console.log("The list is "+data.ListWareIds[0]) ;

      						                WareId = data.ListWareIds[0];
      						                console.log("/*/-WareId is" +WareId);

      						                if(data.ListWareIds.length == 1){
      						                	 console.log("/*/*Entered here");
      						                	
      						                	$("#warcode").val(WareId);
      						                	site.value=ui.item[2];
      						                	

      							                }
      						                

      						                else{

      						                	$("#warcode").val("");
      						                	

      							                }
      						                
      												                  
      					                    }
      					                },
      					                error: function(result) {
      					                    alert("Error");
      					                }
      					            });
      								
      				
      									return false;
      										}
      							    }).autocomplete("instance")._renderItem = function(ul, item) {
      						            return $("<li class='each'>")
      					                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
      					                    //item[0] + "</span><br><span class='desc'>" +
      					                   // item[1] + "</span><br><span class='desc'>" +
      					                    //item[2] + "</span></div>")
      					                    item[1] + "</span><br><span class='desc'>" 
      					                    )
      					                .appendTo(ul);
      					        	};
      								$("#warname").focus(function(){
      					   	        	if (this.value == ""){
      					   	            	$(this).autocomplete("search");
      					   	        	}						
      								});
      // Datatable
      								var tableData = ${plPerSiteTableData};
      							
      					           
// Heeader filter for table


      					          var checkBox1 = document.getElementById("accu");
      							 var checkBox2 = document.getElementById("month");
      							 var checkBox3 = document.getElementById("Profitable");
      							 var checkBox4 = document.getElementById("Loss");
      							 var checkBox5 = document.getElementById("Max");
      							 var checkBox6 = document.getElementById("Min");
      							 
      					           
      					           
      					           $("#generateReport").click(  function() {
      					      		 console.log('generate now');

      					      		DeleteMarkers();
      					      	 $("#accuLegend").prop('checked',true);
     					      	  $("#peakMonth").prop('checked',false);
     					      	  
      					      		 
      					      		 if ($("#startdate1").val()== '') {
      					      		 alert('Please enter a start date');
      					      		 return false;}
      					      		 
      					      		 if ($("#enddate1").val()== '') {
      					      			 alert('Please enter an end date');
      					      			 return false;}
      					      		      		 
      					      			 
      					      		
      					      	 var  Checkvalue;
      		                	  if (checkBox1.checked == true){
      		                		  Checkvalue= "Accumulated";
      		            	          console.log("Checkbox1 is checked." );
      		            	         
      		                      }


      		                	 if (checkBox1.checked == false && checkBox2.checked == false&&checkBox5.checked==false && checkBox6.checked==false){
      		                		alert('Choose Option Accumlated or Monthly or Maximum or Minimum');
     		            	         return false;
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

      		                   
      		                 var selectedTechOption = $("#technology").children("option:selected").val();
      					      		
      					      		var startDate = document.getElementById("startdate1").value;
      					      		var endDate = document.getElementById("enddate1").value;
      					      		var wareHouse = document.getElementById("warcode").value;
      					      		var siteName = document.getElementById("warname").value;
      					      		var siteId = document.getElementById("site").value;
      					      		var accumulated = document.getElementById("accu").value;
      					      		var monthly = document.getElementById("month").value;
      					      		
      					      		
      					      		
      					      	     console.log("startDate " +startDate);
      					      		 console.log("endDate " +endDate);
      					      		 console.log("wareHouse " +wareHouse);
      					      		 console.log("siteName " +siteName);
      					      		 console.log("siteId " +siteId);
      					      		 console.log("accumulated " +accumulated);
      					      		 console.log("monthly " +monthly);

      					      		 if($("#site").val() != "" && $("#warcode").val() == "")
       					      		 {
      					      			alert('Please choose a Warehouse');
      					      			return false;
       					      		 }

      					      		if($("#warcode").val() != "" && $("#site").val() == "")
      					      		 {
     					      			alert('Please choose a Site');
     					      			return false;
      					      		 }

      					      	//	if (checkBox1.checked == false && checkBox2.checked == false){
      		                 	//	  alert('Please choose an option(Monthly/Accumulated)');
      		                 	//  }
      					      		else{
      					      			$.ajax({
      					      				type : "GET",
      					      				url : "${pageContext.request.contextPath}/ProfitAndLossPerSite",
      					      				dataType : "json",
      					      				data : {
      					      				     
      					      					"startDate" : startDate,
      					      				    "endDate" : endDate,
      					      				    "wareHouse" : wareHouse,
      					      					"siteName" : siteName,
      					      					"siteId" : siteId,
      					      					"accumulated" : accumulated,
      					      					"monthly" : monthly,
	      					      				"Profitable" : profitableChecked,
	      		        					    "Loss" : lossChecked,
	      		        					    "Max": maxChecked,
	      		        					    "Min" : minChecked,
	      		        					  	"technologies" : $("#technology").children("option:selected").val(), 
      					      								
      					      				},
      					      				success : function(data) {
      					      					console.log("dataaaa");

      					      					console.log(data.plPerSiteTableData);
      					      					tableData = data.plPerSiteTableData;


      					      		           
      		        	                          ReportArrayGlobal = [];
      		        	                         $.each(tableData, function (i, value) {
      		        	     						ReportArrayGlobal.push({
      		        	     							id:value[0],
      		        	     							siteid: value[1],
      		        	     							siteName: value[2],
      		        	     							startdate: value[3],
      		        	     							enddate: value[4],
      		        	     							AreaName: value[5],
      		        	     						    siteowner: value[6],
      		        	     						   Towertype: value[7],
      		        	     						    DataRev: value[8],
      		        	     						    Voicerev: value[9],
      		        	     						    SMSrev: value[10],
      		        	     						    Grossrev:value[11],
      		        	     						  Datatraffic:value[12],
      		        	     						  smstarffic:value[13],
      		        	     						  voicetrafficO:value[14],
      		        	     						  voicetrafficIC:value[15],
      		        	     						  SMStrafficIC:value[16],
      		        	     						  Totalopex:value[17],
      		        	     						  profitandloss:value[18]
      		        	     						
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
      	    				          			        console.log(dataArray.length);
      	    				          			        if (dataArray.length > 0) {

      	    				          			            var ArrayKeys = Object.keys(dataArray[0]);

      	    				          			            var itemRow = "";

      	    				          			       
      	    				          						 var sumDataRevCol = 0,sumVoicerevCol = 0, sumSMSrevCol = 0, sumGrossrevCol = 0, sumDatatrafficCol = 0, sumsmstarfficCol = 0, sumvoicetrafficOCol =0, sumvoicetrafficICCol = 0, sumSMStrafficICCol = 0, sumTotalopexCol = 0,  sumprofitandlossCol = 0;
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

      	    				          			                    
      	    				          			                       if( column  == "DataRev"){
      	    				          			                    	sumDataRevCol += parseInt(dataArray[i][ArrayKeys[j]]);
      	    				          			                       	  
      	    				          			                       }
      	    				          			                       if( column  == "Voicerev"){
      	    				          			                    	sumVoicerevCol += parseInt(dataArray[i][ArrayKeys[j]]);
      	    				          			                       	  
      	    				          			                       }
      	    				          			                       if( column  == "SMSrev"){
      	    				          			                    	sumSMSrevCol += parseInt(dataArray[i][ArrayKeys[j]]);
      	    				          			                       	  
      	    				          			                       }
      	    				          			                       if( column  == "Grossrev"){
      	    				          			                    	   sumGrossrevCol += parseInt(dataArray[i][ArrayKeys[j]]);
      	    				          			                       	   
      	    				          			                       }
      	    				          			                       if( column  == "Datatraffic"){
     	    				          			                    	sumDatatrafficCol += parseInt(dataArray[i][ArrayKeys[j]]);
     	    				          			                       	  
     	    				          			                       }
     	    				          			                       if( column  == "smstarffic"){
     	    				          			                    	sumsmstarfficCol += parseInt(dataArray[i][ArrayKeys[j]]);
     	    				          			                       	  
     	    				          			                       }
     	    				          			                       if( column  == "voicetrafficO"){
     	    				          			                    	sumvoicetrafficOCol += parseInt(dataArray[i][ArrayKeys[j]]);
     	    				          			                       	  
     	    				          			                       }
     	    				          			                       if( column  == "voicetrafficIC"){
     	    				          			                    	   sumvoicetrafficICCol += parseInt(dataArray[i][ArrayKeys[j]]);
     	    				          			                       	   
     	    				          			                       }   
     	    				          			                       if( column  == "SMStrafficIC"){
     	    				          			                    	sumSMStrafficICCol += parseInt(dataArray[i][ArrayKeys[j]]);
     	    				          			                       	  
     	    				          			                       }
     	    				          			                       if( column  == "Totalopex"){
     	    				          			                    	sumTotalopexCol += parseInt(dataArray[i][ArrayKeys[j]]);
     	    				          			                       	  
     	    				          			                       }
     	    				          			                       if( column  == "profitandloss"){
     	    				          			                    	   sumprofitandlossCol += parseInt(dataArray[i][ArrayKeys[j]]);
     	    				          			                       	   
     	    				          			                       }   
      	    				          			                      
      	    				          			                       
      	    				          			                     
      	    				          			                     
      	    				          			                   }

      	    				          			               }
      	    				          							 itemRow += "</tr>";
      	    				          							}

      	    				          			       
      	    				          			         
      	    				          			       dataRev.value = sumDataRevCol;
      	    				          			       voiceRev.value = sumVoicerevCol;
      	    				          			       smsRev.value = sumSMSrevCol; 
      	    				          			       grossRev.value = sumGrossrevCol;
      	    				          			       dataTraf.value = sumDatatrafficCol; 
      	    				          			       smsTrafOG.value = sumsmstarfficCol;
      	    				          			       voiceTrafOG.value = sumvoicetrafficOCol;
      	    				          			       voiceTrafIC.value = sumvoicetrafficICCol; 
      	    				          			       smsTrafIC.value = sumSMStrafficICCol;
      	    				          			       totalOpex.value = sumTotalopexCol;
      	    				          			       totalPL.value = sumprofitandlossCol;
      	    				          			         
      	    					          			        	    
      	    				          			        

      	    				          			        
      	    				          					
      	    				          			
      	    				          			
      	    				          	            $(tableBody).append(itemRow);

      	    				          	        }
      	    				          			        else{

      	    				          			        dataRev.value = 0;
       	    				          			       voiceRev.value = 0;
       	    				          			       smsRev.value = 0; 
       	    				          			       grossRev.value = 0;
       	    				          			       dataTraf.value = 0; 
       	    				          			       smsTrafOG.value = 0;
       	    				          			       voiceTrafOG.value = 0;
       	    				          			       voiceTrafIC.value = 0; 
       	    				          			       smsTrafIC.value = 0;
       	    				          			       totalOpex.value = 0;
       	    				          			       totalPL.value = 0;
       	    				          			         
       	    					          			    



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

												console.log("ReportArrayGlobal from ajax" +ReportArrayGlobal.length);
      		        	                     //draw bar chart for the sites
   	    				          			    renderAllCharts();
   	    				          	       
      					      					
      					      					
      					      					if (siteId != "" && accumulated == 1) 
      					      		          		$('#totalNumbers').css("display", "none");
      					      		      		else
      					      		      			$('#totalNumbers').css("display", "block");
      					      					


      					      					
      					      				},
      					      				error : function(error) {
      					      					console.log("The error is " + error);
      					      				}
      					      			});

      					           }


      					      	   //////////////////////////

         					      	 $.ajax({
         					     	  type: "GET",
         					     	  contentType: "application/json; charset=utf-8",
         					     	  url: '${pageContext.request.contextPath}/GetSelectedSite',
         					     	  data: {
         					     		"wareHouse" : wareHouse,
   				      				 	"siteId" : siteId,	
   				      					"startDate" : startDate,
					      				"endDate" : endDate,
					      				"monthly" : monthly,
					      				"accumulated" : accumulated,
					      				"Profitable" : profitableChecked,
					      				"Loss" : lossChecked,
  		        					    "Max": maxChecked,
  		        					    "Min" : minChecked,
  		        					  	"technologies" : $("#technology").children("option:selected").val(), 
					      								
	   				      							
         					     				 },

         					     	dataType: "json",
         					     	success: function (data) {

         					     		if (data != null) {
         					     			//var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
         					     		var Nairobi=new google.maps.LatLng(33.81673451460087, 35.77254745477668);
         					     			
             					     		
         					     		    document.getElementById("mapContainer").innerHTML ="";

         					     			var map = new google.maps.Map(document.getElementById("mapContainer"), {

         					     				 mapTypeControl: false,
         					     				 center:Nairobi ,
        					     			      zoom: 8,
          					     				 
         					     				 mapTypeControl: true,
         					     				 mapTypeControlOptions: {
         					     					 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
         					     					 position: google.maps.ControlPosition.TOP_CENTER,
         					     					 },
         					     					 zoomControl: true,
         					     					 zoomControlOptions: {
         					     						 position: google.maps.ControlPosition.LEFT_CENTER,
         					     						 },

         					     						 scaleControl: true,
         					     						 streetViewControl: true,
         					     						 streetViewControlOptions: {
         					     							 position: google.maps.ControlPosition.LEFT_TOP,
         					     							 },
         					     							 
         					     							 style: 'mapbox://styles/mapbox/streets-v11',
         					     							 fullscreenControl: true,
         					     							 });

         					     			const centerControlDiv = document.createElement("dv");
          								    LegendControl(centerControlDiv, map);
          								    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);
						if ( siteId != "" ) {
							UnselectAll();
							AddSiteMarkers(data.listSites2,map,"gray");
							}
							else {
								if( checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == false && checkBox5.checked == false && checkBox6.checked == false  ){
				             		console.log("ONLY ACC IS checked." );
				             		UnselectAll();
				             		setColor(data.listSites,map);
			             		}
    				         	if( checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox5.checked == false && checkBox6.checked == false  ){
    				         		console.log("ONLY Monthly IS checked." );
    				         		UnselectAll();
    				         		setColor(data.listSites,map);
				         		 	AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);
				         		}
    				         	if( checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == false && checkBox5.checked == true && checkBox6.checked == false  ){  
				             		console.log("ACC-MAX ARE checked." );
				             		UnselectAll();
    				         		setColor(data.listSites,map);
    				         		}

    				         	if( checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == false && checkBox5.checked == false && checkBox6.checked == true  ){  
				             		console.log("ACC-min ARE checked." );
				             		UnselectAll();
    				         		AddSiteMarkers(data.listSites,map,"#0000CD");
    				         		}
				             	if( checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox5.checked == true && checkBox6.checked == false  ){
				             		console.log("ACC-PROF-MAX ARE checked." );
				             		UnselectAll();
    				         		setColor(data.listSites,map);
    				         		}
				         		 if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false){
 				             		console.log("ACC-PROF-MIN!! ARE checked." );
				             		UnselectAll();
    				         		AddSiteMarkers(data.listSites,map,"#0000CD");
    				         		}

				         		 if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox5.checked == true && checkBox6.checked == false ){
 				             		console.log("ACC-LOSS-MAX ARE checked." );
				             		UnselectAll();
    				         		setColor(data.listSites,map);
    				         		}
 				         		if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false ){
				             		 console.log("ACC-LOSS-MIN ARE checked." );
 				             		 UnselectAll();
 				             		 setColor(data.listSites,map);
 				             		 }
			             		 if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true  && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == true ){
				            		 console.log("ACC-PRO-MIN-MAX ARE checked." );
			             			 UnselectAll();
			             			setColor(data.listSites,map); 
			             		 }
		             			 if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == true ){
				 	 	     		  console.log("ACC-LOSS-MIN-MAX ARE checked." );
			             			  UnselectAll();
				 	 	     		  setColor(data.listSites,map);
				 	 	     		  }
		             			if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == true){
					 	     		  console.log("ACC-PROF-LOSS-MIN-MAX ARE checked." );
					 	     		  UnselectAll();
					 	     		  setColor(data.listSites,map);
					 	     		  }
		             			if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true){
					 	     		  console.log("ACC-PROF-LOSS-MAX ARE checked." );
					 	     		  UnselectAll();
					 	     		  setColor(data.listSites,map);
					 	     		  }   

					 	     	 
				 	     		 if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false){
					 	     		  console.log("ACC-PROF-LOSS-MIN ARE checked." );
					 	     		  UnselectAll();
					 	     		  setColor(data.listSites,map);
					 	     		  }  
				 	     		 if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == false){
					 	     		  console.log("ACC-PROF ARE checked." );
					 	     		  UnselectAll();
					 	     		  setColor(data.listSites,map);
					 	     		  } 
				 	     		if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false){
					 	     		  console.log("ACC-LOSS ARE checked." );
					 	     		  UnselectAll();
					 	     		  setColor(data.listSites,map);
					 	     		  }  
				 	     		 if (checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false){
					 	     		  console.log("ACC-LOSS ARE checked." );
					 	     		  UnselectAll();
					 	     		  setColor(data.listSites,map);
					 	     		  } 

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="null"){
			         		 	   	  console.log("MONTHLY-PROF ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);

			         		 	    	  } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="2g"){
			         		 	   	  console.log("MONTHLY-PROF-2G ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);

			         		 	    	  } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="2g3g"){
			         		 	   	  console.log("MONTHLY-PROF-2G3G ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);

			         		 	    	  } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="2g3g4g"){
			         		 	   	  console.log("MONTHLY-PROF-2G3G4G ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);

			         		 	    	  } 

	         		 	    	  
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="null"){
			         		 	   	  console.log("MONTHLY-PROF-MAX ARE checked." );
			         		 	   	  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	  } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g"){
			         		 	   	  console.log("MONTHLY-PROF-MAX-2G ARE checked." );
			         		 	   	  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	  }      
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g3g"){
			         		 	   	  console.log("MONTHLY-PROF-MAX-2G3G ARE checked." );
			         		 	   	  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	  } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g3g4g"){
			         		 	   	  console.log("MONTHLY-PROF-MAX-2G3G4G ARE checked." );
			         		 	   	  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	  } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="null"){
				       		 	   	  console.log("MONTHLY-PROF-MIN ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      }   
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g"){
				       		 	   	  console.log("MONTHLY-PROF-MIN-2G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      }
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g"){
				       		 	   	  console.log("MONTHLY-PROF-MIN-2G3G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      }
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g4g"){
				       		 	   	  console.log("MONTHLY-PROF-MIN-2G3G4G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      }
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="null"){
				         		 	console.log("MONTHLY-PROF-MAX-MIN ARE checked." );
				         		 	UnselectAll();
				         		 	setColor(data.listSites,map); 
   				         		 	AddPeakAccList(data.listSites,data.peakListSites,[],data.peakMinProfitList,[]);
				 	     		}   

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="2g"){
				         		 	console.log("MONTHLY-PROF-MAX-MIN-2G ARE checked." );
				         		 	UnselectAll();
				         		 	setColor(data.listSites,map); 
   				         		 	AddPeakAccList(data.listSites,data.peakListSites,[],data.peakMinProfitList,[]);
				 	     		}   
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="2g3g"){
				         		 	console.log("MONTHLY-PROF-MAX-MIN-2G3G ARE checked." );
				         		 	UnselectAll();
				         		 	setColor(data.listSites,map); 
   				         		 	AddPeakAccList(data.listSites,data.peakListSites,[],data.peakMinProfitList,[]);
				 	     		}   
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="2g3g4g"){
				         		 	console.log("MONTHLY-PROF-MAX-MIN-2G3G4G ARE checked." );
				         		 	UnselectAll();
				         		 	setColor(data.listSites,map); 
   				         		 	AddPeakAccList(data.listSites,data.peakListSites,[],data.peakMinProfitList,[]);
				 	     		}   
				 	     		 if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false  && selectedTechOption=="null"){
				       		 	   	  console.log("MONTHLY-LOSS ARE checked." );
				       		 	  	  UnselectAll();
				 		 	  		  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				 		 	  		  
				 		 	    	  } 

				 	     		 if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false  && selectedTechOption=="2g"){
				       		 	   	  console.log("MONTHLY-LOSS-2G ARE checked." );
				       		 	  	  UnselectAll();
				 		 	  		  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				 		 	  		  
				 		 	    	  }  
				 	     		 if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false  && selectedTechOption=="2g3g"){
				       		 	   	  console.log("MONTHLY-LOSS-2G3G ARE checked." );
				       		 	  	  UnselectAll();
				 		 	  		  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				 		 	  		  
				 		 	    	  }  
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false  && selectedTechOption=="2g3g4g"){
				       		 	   	  console.log("MONTHLY-LOSS-2G3G4G ARE checked." );
				       		 	  	  UnselectAll();
				 		 	  		  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				 		 	  		  
				 		 	    	  }  

				 	     		 if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="null"){
			         		 	   	  console.log("MONTHLY-Loss-MAX ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				         		 	   } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g"){
			         		 	   	  console.log("MONTHLY-Loss-MAX-2g ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				         		 	   } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g3g"){
			         		 	   	  console.log("MONTHLY-Loss-MAX-2g3g ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				         		 	   } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g3g4g"){
			         		 	   	  console.log("MONTHLY-Loss-MAX-2g3g4g ARE checked." );
			         		 	   	  UnselectAll();
			         		 	      setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],[]);
				         		 	   } 
			         		 	   
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="null"){
				       		 	   	  console.log("MONTHLY-LOSS-MIN ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,[],[],[],data.peakMinLossList);
			         		 	      }  

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g"){
				       		 	   	  console.log("MONTHLY-LOSS-MIN-2G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,[],[],[],data.peakMinLossList);
			         		 	      }  

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g"){
				       		 	   	  console.log("MONTHLY-LOSS-MIN-2G3G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,[],[],[],data.peakMinLossList);
			         		 	      } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g4g"){
				       		 	   	  console.log("MONTHLY-LOSS-MIN-2G3G4G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
			         		 	      AddPeakAccList(data.listSites,[],[],[],data.peakMinLossList);
			         		 	      } 

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="null"){
   				       		 	 	console.log("MONTHLY-LOSS-MAX-MIN ARE checked." );
   				       		 	 	UnselectAll();
   				       		 	 	setColor(data.listSites,map);
			              		 	AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],data.peakMinLossList);
			              		 	}  

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="2g"){
   				       		 	 	console.log("MONTHLY-LOSS-MAX-MIN-2g ARE checked." );
   				       		 	 	UnselectAll();
   				       		 	 	setColor(data.listSites,map);
			              		 	AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],data.peakMinLossList);
			              		 	}  
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="2g3g"){
   				       		 	 	console.log("MONTHLY-LOSS-MAX-MIN-2g3g ARE checked." );
   				       		 	 	UnselectAll();
   				       		 	 	setColor(data.listSites,map);
			              		 	AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],data.peakMinLossList);
			              		 	}  
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == false && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == true && selectedTechOption=="2g3g4g"){
   				       		 	 	console.log("MONTHLY-LOSS-MAX-MIN-2g3g4g ARE checked." );
   				       		 	 	UnselectAll();
   				       		 	 	setColor(data.listSites,map);
			              		 	AddPeakAccList(data.listSites,[],data.peakMaxLossList,[],data.peakMinLossList);
			              		 	}  

				 	     		 if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="null"){
			         				 console.log("MONTHLY-LOSS-PROF ARE checked." );
			         				 UnselectAll();
			              		 	 setColor(data.listSites,map);
			              		 	 AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

			             			}

				 	     		 if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="2g"){
			         				 console.log("MONTHLY-LOSS-PROF-2G ARE checked." );
			         				 UnselectAll();
			              		 	 setColor(data.listSites,map);
			              		 	 AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

			             			}
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="2g3g"){
			         				 console.log("MONTHLY-LOSS-PROF-2G3G ARE checked." );
			         				 UnselectAll();
			              		 	 setColor(data.listSites,map);
			              		 	 AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

			             			}
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == false && selectedTechOption=="2g3g4g"){
			         				 console.log("MONTHLY-LOSS-PROF-2G3G4G ARE checked." );
			         				 UnselectAll();
			              		 	 setColor(data.listSites,map);
			              		 	 AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

			             			}
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="null"){
				         		 	   	UnselectAll();
				         		 	   	console.log("MONTHLY-PROF-LOSS-MAX ARE checked." );
				         		 	   	setColor(data.listSites,map); 
			              		 	    AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

			              		 	 } 

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g"){
			         		 	   	UnselectAll();
			         		 	   	console.log("MONTHLY-PROF-LOSS-MAX-2G ARE checked." );
			         		 	   	setColor(data.listSites,map); 
		              		 	    AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

		              		 	 } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g3g"){
			         		 	   	UnselectAll();
			         		 	   	console.log("MONTHLY-PROF-LOSS-MAX-2G3G ARE checked." );
			         		 	   	setColor(data.listSites,map); 
		              		 	    AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

		              		 	 } 
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="2g3g4g"){
			         		 	   	UnselectAll();
			         		 	   	console.log("MONTHLY-PROF-LOSS-MAX-2G3G4G ARE checked." );
			         		 	   	setColor(data.listSites,map); 
		              		 	    AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,[],[]);

		              		 	 } 

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="null"){
				         		 	   	UnselectAll();
				         		 	   	console.log("MONTHLY-PROF-LOSS-MIN ARE checked." );
				         		 	   	setColor(data.listSites,map);  
			              		 	    AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,data.peakMinLossList);
				         		 	   	 }  

				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g"){
			         		 	   	UnselectAll();
			         		 	   	console.log("MONTHLY-PROF-LOSS-MIN-2G ARE checked." );
			         		 	   	setColor(data.listSites,map);  
		              		 	    AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,data.peakMinLossList);
			         		 	   	 }   
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g"){
			         		 	   	UnselectAll();
			         		 	   	console.log("MONTHLY-PROF-LOSS-MIN-2G3G ARE checked." );
			         		 	   	setColor(data.listSites,map);  
		              		 	    AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,data.peakMinLossList);
			         		 	   	 }   
				 	     		if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g4g"){
			         		 	   	UnselectAll();
			         		 	   	console.log("MONTHLY-PROF-LOSS-MIN-2G33G4G ARE checked." );
			         		 	   	setColor(data.listSites,map);  
		              		 	    AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,data.peakMinLossList);
			         		 	   	 } 

				 	     		 if (checkBox2.checked == true && checkBox1.checked == false && checkBox3.checked == true && checkBox4.checked == true && checkBox6.checked == true && checkBox5.checked == true){
			        				 console.log("MONTHLY-LOSS-PROF-MAX-MIN ARE checked." );
			        				 UnselectAll();
			             		 	 setColor(data.listSites,map);
				              		 AddPeakAccList(data.listSites,data.peakListSites,data.peakMaxLossList,data.peakMinProfitList,data.peakMinLossList);
				             		
			            			}
				 	     		 if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption=="null"){
	   				  				 console.log("Monthly-MAX ARE CHECKED." );
	    				  			  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	
				         		 	  }
				 	     		if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption =="2g"){
	   				  				 console.log("Monthly-MAX-2G ARE CHECKED." );
	    				  			  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	
				         		 	  }
				 	     		if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption =="2g3g"){
	   				  				 console.log("Monthly-MAX-2G3G ARE CHECKED." );
	    				  			  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	
				         		 	  }
				 	     		if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true && selectedTechOption =="2g3g4g"){
	   				  				 console.log("Monthly-MAX-2G3G4G ARE CHECKED." );
	    				  			  UnselectAll();
				         		 	  setColor(data.listSites,map);
				         		 	  AddPeakAccList(data.listSites,data.peakListSites,[],[],[]);
				         		 	
				         		 	  }

				 	     		 if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="null"){
				       		 	   	  console.log("MONTHLY-MIN ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      }   
				 	     		if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g"){
				       		 	   	  console.log("MONTHLY-MIN-2G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      } 
				 	     		if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g"){
				       		 	   	  console.log("MONTHLY-MIN-2G3G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      } 
				 	     		if (checkBox1.checked == false && checkBox2.checked == true && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false && selectedTechOption=="2g3g4g"){
				       		 	   	  console.log("MONTHLY-MIN-2G3G4G ARE checked." );
				       		 	      UnselectAll();
				       		 	      setColor(data.listSites,map);
				       		 	      AddPeakAccList(data.listSites,[],[],data.peakMinProfitList,[]);
				       		 	      } 
			       		 	      
				 	     		if (checkBox1.checked == false && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == false && checkBox5.checked == true){
	   				   				 console.log("ONLY MAX IS CHECKED checked." );
					 	     	     UnselectAll();
					 	     	     setColor(data.listSites,map);

					 	     	     } 

				 	     	     if (checkBox1.checked == false && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == false){
	   				  				 console.log("ONLY MIN IS CHECKED checked." );
	    				  			  UnselectAll();
				         		 	//  setColor(data.listSites,map);
				         			  AddSiteMarkers(data.listSites,map,"#0000CD");
				         			  $("#legendMinProfit").prop('checked',true);

				         			 AddSiteMarkers(data.peakMaxLossList,map,"#FF8C00");
				         			$("#legendMinLoss").prop('checked',true);

				         			    
					         		 	  

				         		 	  }
				 	     	  
			         		 	  
				 	     	   if (checkBox1.checked == false && checkBox2.checked == false && checkBox3.checked == false && checkBox4.checked == false && checkBox6.checked == true && checkBox5.checked == true){
	   				  				 console.log("ONLY MAX-MIN ARE CHECKED checked." );
	    				  			  UnselectAll();

	    				  			  /*
				         			  AddSiteMarkers([peakListSites],map,"green");
				         			  AddSiteMarkers([peakMaxLossList],map,"red");
				         			  AddSiteMarkers([peakMinProfitList],map,"#0000CD");
				         			  AddSiteMarkers([peakMinLossList],map,"FF8C00");
				         			  
				         			  $("#legendMinProfit").prop('checked',true);
				         			  $("#legendMinLoss").prop('checked',true);
				         			  $("#legendMaxProfit").prop('checked',true);
				         			  $("#legendMaxLoss").prop('checked',true);
				         			  
				         			  */
			         			 		
					              		 AddPeakAccList([],data.peakListSites,data.peakMaxLossList,data.peakMinProfitList,data.peakMinLossList);
					              		setColor(peakMaxProfitList,map);
					             		setColor(peakMaxLossList,map);
					        			AddSiteMarkers(peakMinProfitList,map,"#0000CD");
					        			AddSiteMarkers(peakMinLossList,map,"#FF8C00");
					        			
					        			UnselectAll();

					        			
					        			
					             		if (peakMaxProfIndex >=0){
					             	        $("#legendMaxProfit").prop('checked',true);   
					             	        }

					             		if (peakMaxLossIndex >=0){
					             	        $("#legendMaxLoss").prop('checked',true);   
					             	        }
					             		if (peakMinProfIndex >=0){
					             	        $("#legendMinProfit").prop('checked',true);   
					             	        }
					             		if (peakMinLossIndex >=0){
					             	        $("#legendMinLoss").prop('checked',true);   
					             	        }
				         		 	  }

			            			
            				         	
            				         	
            				         	
          					     		   }

         					     		}
			     							 





         					     			
         					     			 
         					     		 },
         					     		 error: function(result) {
         					     			 alert("Error");
         					     			 }
         					     		 });
        					           ////////////
      					      	});



      					     	var siteNames = [];
      							var dataRevenue= [];
      							var voiceRevenue =  [];
      							var smsRevenue =  [];
      							var grossRevenue =  [];

      																					   
      							
      							function renderAllCharts(){

      								
      								siteNames = [];
      								dataRevenue=  [];
      								voiceRevenue = [];
      								smsRevenue = [];
      								grossRevenue  =[];

      								
      								//draw charts
      								fetchProfitLossByTechnology();
      								
      								
      								console.log("resolving the report chart graphs");	
      								console.log("ReportArrayGlobal from renderAllCharts" +ReportArrayGlobal.length);



      							}

      					//*********************** Multi-series Column 2D


      								var obj1 = {};
      								
      								
      								async function fetchProfitLossByTechnology(){
      									var dataRev =[];
      									var voiceRev=[];
      									var smsRev=[];
      									var grossRev=[];

      									var objRevenue ={};


      									var SiteNames=[];
      									
  										console.log("Hi");
  										console.log("ReporArrayGlobal :" +ReportArrayGlobal.length);
      									

      										if(ReportArrayGlobal !=null && ReportArrayGlobal.length !=0){
          										console.log("ReporArrayGlobal not null");
          										
      									for(var i=0 ;i<ReportArrayGlobal.length ;i++){
      										if(ReportArrayGlobal[i]["siteName"] != null){
      											SiteNames.push(ReportArrayGlobal[i]["siteName"].toString());
      											//obj1.value = ReportArrayGlobal[i]["siteName"].toString();			
      											//areaNames.push(obj1);
      											obj1 = [];
      										}else{
      											obj1.value = "No site";
      											SiteNames.push(obj1);
      											obj1 = [];
      											}

      										console.log("SiteNames" +SiteNames);
  											
      										
      										if(ReportArrayGlobal[i]["DataRev"] != null){
      											objRevenue.value=ReportArrayGlobal[i]["DataRev"];
      											dataRev.push(objRevenue);
      											objRevenue ={};
      										}else{
      											dataRev.push("0");
      											}
      										
      										if(ReportArrayGlobal[i]["Voicerev"] != null){
      											objRevenue.value=ReportArrayGlobal[i]["Voicerev"];
      											voiceRev.push(objRevenue);
      											objRevenue ={};
      											
      										}else{
      											voiceRev.push("0");
      											}
      										
      										if(ReportArrayGlobal[i]["SMSrev"] != null){
      											objRevenue.value=ReportArrayGlobal[i]["SMSrev"];
      											smsRev.push(objRevenue);
      											objRevenue={};
      										}else{
      											smsRev.push("0");
      											}

      										if(ReportArrayGlobal[i]["Grossrev"] != null){
      											objRevenue.value=ReportArrayGlobal[i]["Grossrev"];
      											grossRev.push(objRevenue);
      											objRevenue ={};
      											
      										}else{
      											grossRev.push("0");
      											}

      									}
      										}
      										else{
      											SiteNames.push('No Sites');
      											dataRev.push("0");
      											voiceRev.push("0");
      											smsRev.push("0");
      											grossRev.push("0");
      											
      											
      											
      												}

      										
      								       FusionCharts.ready(function() {
      								    	   var profitLossByTechnologyChart = new FusionCharts({
      								    	     type: 'mscolumn2d',
      								    	     renderAt: 'chartContainer2',
      								    	     width: '95%',
      								    	     height: '650',
      								    	     dataFormat: 'json',
      								    	     dataSource: {
      								    	       "chart": {
      								    	         "theme": "fusion", //ocean
      								    	         "caption": "Revenue Analysis by Site ",
      								    	         "xAxisname": "Site",
      								    	         "xAxisNameFontSize":"22px",
      								    	         "yAxisNameFontSize":"22px",
      								    	         "yAxisName": "Revenue",
      								    	         "labelFontSize":"12px",
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
      								    	         "category": (SiteNames=="No site")? SiteNames :jQuery.parseJSON(getElement(SiteNames))
      									    	  
      								    	       }],
      								    	       "dataset": [{
      									    	         "seriesname": "Data Revenue",
      									    	         "data": dataRev
      									    	       },
      									    	       {
      									    	         "seriesname": "Voice Revenue",
      										    	     "data": voiceRev
      										    	     },
      									    	       {
      									    	         "seriesname": "SMS Revenue",
      										    	     "data": smsRev
      										    	     },
      					{
      									    	         "seriesname": "Gross Revenue",
      										    	     "data": grossRev
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

      						
      																				

      						
      							function getElement(SiteNames){
      								var strArea = {};
      								
      							//if(areaNames != "No Areas"){
      								$.each(SiteNames, function(key, value){
      									if (key == 0 ) { if(SiteNames.length>1) strArea = '[{"label":'+'"'+value.toString()+'"'+'},';
      														else strArea = '[{"label":'+'"'+value.toString()+'"'+'}]';}
      									else if (key < SiteNames.length-1 && key > 0)	strArea +='{"label":'+'"'+value.toString()+'"'+'},'; 
      									else strArea += '{"label":'+'"'+value.toString()+'"'+'}]'; 
      									         
      							         });
      						        // }
      								return strArea;
      										  
      								}

      					         $('#accu').change(function() {
      		                	    if(this.checked == true){
      		                        var ware=$('#warcode').val();
      		                      var wareName=$('#warname').val();
      		                      var siteId=$('#site').val();
      		                      //  console.log("The rae is "+area);
      		                        if(ware!="" || wareName!=""|| siteId!=""){
      		                           
      		                      
      		                      	 $("#Profitable").attr('disabled', 'disabled');
      		                      	 $("#Loss").attr('disabled', 'disabled');
      		                      	 $("#Max").attr('disabled', 'disabled');
      		                      	 $("#Min").attr('disabled', 'disabled');

      		                            }
      		                        else{

      		                        	 $("#Profitable").attr('disabled', false);
      		                          	 $("#Loss").attr('disabled', false);
      		                          	 $("#Max").attr('disabled', false);
      		                          	 $("#Min").attr('disabled', false);




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