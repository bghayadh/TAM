<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title></title>
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
            
      <!-- CHARTS Scripts -->
          <script type="text/javascript"
	        src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>
          <script type="text/javascript"
	        src="${pageContext.request.contextPath}/resources/js/fusionchartsold.js"></script>
          <script type="text/javascript"
	        src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
	
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

 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
 
 
 
      <style>

 #mapContainer {
        height: 600px;
       
       }
       
       .canvas-style2{
height: 550px !important; 
padding-bottom: 30px !important;
}
      
        </style>
 
 
 </head>
<body>
<%--  <c:set var = "page" value = "report"/> --%>

<%-- 	<%@ include file="../header.html"%> --%>
  <c:set var="pg" value="report" scope="session"  />
 <jsp:include page="../header.jsp"></jsp:include>
	 <div Style=" left: 0; bottom: 0;" id="assetReportDiv">

	<div class="container-fluid">     
	     <div class="row">
	     
	     <p></p>
		
		 </div>
	  <div class="row second">	
	      <div class="col-md-3" style="display: flex;">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Asset Accounting Voucher</span>
					</div>
				</div>
			</div>
		    <div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="fromdate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
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
					<input type="text" id="todate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
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
		 			<button type="button" id="generate" class="btn btn-primary BtnActive" style=" margin-top:-8px;" > Generate Report </button>
		 		</div>
		 </div>
					
		 
	</div>
	
	
	
	
	
	<div class="wrapper ">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

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
											<div id="loaderDiv" style="display: none;">
												<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
											</div>
									</div>
									<div class="col-sm-4 almgrid-global-search-box">
										Search
										<input type="text" class="form-control almgrid-global-search" />
									</div>
								</div>
								<div id="alertMsgDiv" style="display: none;padding-left: 40px">
								<br>
									<b style="color:red;font-size:15px;white-space: nowrap;">The number of original fetched data is exceeding the number of allowed data to show. Please set a filter to reduce the fetched data.</b> 
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
                                                   <th>New Asset
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th> Monthly Depreciation
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Retirement
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
        
        <canvas id="AssetAccountingChart" width="350" class="canvas-style2"></canvas>
        
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
<script>

function initMap() {


	// Define the Center
	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
		
  var map = new google.maps.Map(document.getElementById("mapContainer"), {
    center:Nairobi ,
    
  });
  map.setZoom(3);

  
}

// Set Default Date to month Before 
 var today = new Date();
var date = (today.getMonth())+'/'+'1'+'/'+ today.getFullYear();
var time = '00' + ":" + '00' ;
var dateTime = date+' '+time;
var c = Date.parse(dateTime);
$('#fromdate').datetimepicker({
    defaultDate:c
});
// set end date defualt value to current
 var dateNow = new Date();

$('#todate').datetimepicker({
    defaultDate:dateNow
}); 

$(document).ready(function() {



$('.panel-collapse').on('show.bs.collapse', function () {
        
	    $(this).siblings('.panel-heading').removeClass('active');
	  });

	  $('.panel-collapse').on('hide.bs.collapse', function () {
	    $(this).siblings('.panel-heading').addClass('active');
	  });



	
var assetReportData = ${AssetReportList};
//console.log(tableData);
	/*var assetReportTable = $('#bisotab1').DataTable( {
	"bProcessing": true,
    "aaData": assetReportData,// <-- your array of objects
    "aoColumns":[
    	
    	{ "mData": [0] }, 
        { "mData": [1] },
		{ "mData": [2] },
		{ "mData": [3] },
		{ "mData": [4] },   	        
        
        ],
		
	
          'columnDefs': [
          
      ],
      
      
		'order': [[0, 'asc']],
			//select: true,
			order : [ [ 0, 'asc' ] ],
	/*		'select': { style: 'multi',
      selector : 'td:first-child'
      },*/
     /* 'order': [[0, 'asc']],
      	dom: 'Blfrtip',
		"paging":true,
		"pageLength":10, 
		"scrollY": false,
		"scrollX": true,
		"scrollCollapse": false,
		"fixedColumns": true,
		"ordering":true,
		"rowReorder": true,
		"info":       true,
		"filter":     true,
		"length":     true,
		"processing": true,
		"deferRender": true,
          //orderable : false,
      
   });*/

	/*$('#bisotab1 thead th').each( function () {
        var title = $(this).text();
        if(title == 'Start Date' || title == 'End Date' || title == 'New Asset' || title == 'Monthly Depreciation' || title == 'Retirement')
        $('#columnSearch').append( '<th class="inputFilter"><input type="text" placeholder="Search '+title+'" /></th>' );
    } );*/

	// Heeader filter for table


  /*	$('#columnSearch input[type="text"]').on('keyup change clear', function(){
  		if($(this).attr('placeholder') == 'Search Start Date'){
      		console.log("Enters here");
  			table.column(0).search(this.value).draw();
  		   		}
  		if($(this).attr('placeholder') == 'Search End Date'){
      		console.log("Enters here");
  			table.column(1).search(this.value).draw();
  		       }
  		if($(this).attr('placeholder') == 'Search New Asset'){
      		console.log("Enters here");
  			table.column(2).search(this.value).draw();
  		       }
  		if($(this).attr('placeholder') == 'Search Monthly Depreciation'){
      		console.log("Enters here");
  			table.column(3).search(this.value).draw();
  		       }
  		if($(this).attr('placeholder') == 'Search Retirement'){
      		console.log("Enters here");
  			table.column(4).search(this.value).draw();
  		       }
  	});

*/
$("#generate").click(  function() {
	 getCharts();
	});

function getCharts(){
   
	  //var table1 = document.getElementById("checktablefixedasset");
	 var tbody_fixed = document.getElementById('tbody_fixed');
	// table1.style.display = "block";
		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateAccountingVoucher",
			dataType : "json",
			data : {
			    "fromdate" : $("#fromdate").val(),
			    "todate" : $("#todate").val(),
			   
			},
			success : function(data) {
				
				 if (data != null) {
                   var assetReport = data.AssetReportList;
                   
                   var StartDateArray = [];
                   var NewAssetArray = [];
                   var MonthlyDepreciationArray = [];
                   var RetirementArray = [];
                   var ReportArrayGlobal = [];
                   $.each(assetReport, function (i, value) {
                   	 //*************************** bar chart  ***************************
                   	 
                   	        //startDate//
						   StartDateArray.push(value[1]);
						   console.log("StartDate is:" + StartDateArray);

						            //New Asset//
						   NewAssetArray.push(value[3]);
						   console.log("NewAssetArray is:" + NewAssetArray);

						           // MonthlyDepreciation//
						   MonthlyDepreciationArray.push(value[4]);
						   console.log("MonthlyDepreciationArray is:" + MonthlyDepreciationArray);

				                   //Retirement//
						   RetirementArray.push(value[5]);
						   console.log("RetirementArray is:" + RetirementArray);
						   
					    	
						    var ctx2 = document.getElementById('AssetAccountingChart').getContext('2d');
						
						    				var myChart2 = new Chart(ctx2, {
						    				    type: 'bar',
						    				  
						    				  data: {
						    				    labels:StartDateArray ,
						    				    datasets: [{
						    				      label: "New Asset",
						    				      backgroundColor: "#02a499",
						    				      borderColor: "#ffffff",
						    				      borderWidth: 1,
						    				      
						    				      data: NewAssetArray
						    				  },
									        {
									            label: 'Monthly Depreciation',
									            backgroundColor: "blue",
						    				    borderColor: "#ffffff",
						    				    borderWidth: 1,
						    				   
						    				    data: MonthlyDepreciationArray
									        },
									        {
									            label: 'Retirement',
									            backgroundColor: "red",
						    				    borderColor: "#ffffff",
						    				    borderWidth: 1,
						    				   
						    				    data: RetirementArray
						    				    }]
						    				  },
						    				  options: {
						    					legend: {
					    				    	    labels: {
					    				    	       fontSize: 18,
					    				    	       fontColor: "black",
					    				    	    }
					    				    	},
					    				    	 title: {
					    				              display: true,
					    				              text: 'Asset Accounting Voucher',
					    				              fontFamily: 'sans-serif',
					    				              fontColor: '#08526D',
					    				              fontSize: 22
					    				          },
					    				    	 tooltips:{
					    				 			mode: 'index',
					    				             },
						    				    scales: {
						    				      xAxes: [{
						    				        offset: true,
						    				        type: 'time',
						    				        time: {
						    				          unit: 'Month',
						    				          source: 'data',
						    				          tooltipFormat: ' YYYY MMM DD',
						    				          displayFormats: {
					    				        		  'Month':'DD/MM/YYYY'
													  }
						    				        },
						    				        gridLines: {
						    				          display: true
						    				        }
						    				      }],
						    				    yAxes: [{
						    				        ticks: {
						    				          beginAtZero: true
						    				        },
						    				        gridLines: {
						    				          display: true
						    				        }
						    				      }]
						    				    }
						    				  }
						    				});
						  
						    				//*************************** END bar chart ***************************  
						ReportArrayGlobal.push({
							id:value[0],
							startDate: value[1],
							enddate: value[2],
							assetnew: value[3],
							monthlydepreciation: value[4],
							retirenment: value[5]
						    
						});
					});
       	                   
              

                   var almgrid = new Almgrid({
                       tableId: "gridTable",
                       dataArray: ReportArrayGlobal,
                       selectCheckbox: false
                   });
                     	                         
				 }      					
	  
             },
 
 error : function(error) {
		console.log("The error is " + error);
	}
});
        




} // getcharts 

}); 
</script>


<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"
	async defer>
	</script>
	 

</html>