<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <head>
        <meta charset="utf-8">
        <title></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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


        <!-- 	 <script src="${pageContext.request.contextPath}/resources/js/FileSaver.min.js"></script> -->
        <!--  <script src="${pageContext.request.contextPath}/resources/js/xlsx.core.min.js"></script> -->
        <script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jspdf.plugin.autotable.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/tableHTMLExport.js"></script>
        <!--   <script src="${pageContext.request.contextPath}/resources/js/tableExport.js"></script>-->

        <link href="${pageContext.request.contextPath}/resources/cssgrid/bootstrap-datepicker.min.css" rel='stylesheet'
            type='text/css'>
        <script src="${pageContext.request.contextPath}/resources/scriptsgrid/bootstrap-datepicker.min.js"
            type='text/javascript'></script>

        <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/areasitereport.css" /> -->

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

 .ui-autocomplete {
                max-height: 100px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
                overflow-x: both;
                /* add padding to account for vertical scrollbar */
                padding-right: 100px;
            }


           
        </style>


    </head>

    <body>

<%--         <c:set var="page" value="report" /> --%>

<%--         <%@ include file="../header.html" %> --%>
  <c:set var="pg" value="report" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>

            <div class="container-fluid">
                <div class="row">

                    <p></p>

                </div>

                <div class="row second">

                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" style="color:green">Area Sites Report</span>
                            </div>

                        </div>

                    </div>


                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
                                <span class="input-group-text">Start Date</span>
                                <input type="text" id="startdate" class="form-control datetimepicker-input"
                                    data-toggle="datetimepicker" data-target="#datetimepicker3" />
                                <div class="input-group-append" data-target="#datetimepicker3"
                                    data-toggle="datetimepicker">
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
                                <input type="text" id="enddate" class="form-control datetimepicker-input"
                                    data-toggle="datetimepicker" data-target="#datetimepicker4" />
                                <div class="input-group-append" data-target="#datetimepicker4"
                                    data-toggle="datetimepicker">
                                    <div class="input-group-text">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="col-md-3" style="text-align: right;">
                        <div class="btn-group pull-right">
                            <div class="glyph">
                                <div class="dropdown" Style="margin-right:10px; margin-top:-8px; height:30px;">
                                    <button class="btn btn-secondary dropdown-toggle" type="button"
                                        id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">Menu </button>

                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item" id="edit" href="#">Edit </a>
                                        <a class="dropdown-item" id="print" href="#">Print </a>
                                        <a class="dropdown-item" id="export" href="#">Export </a>
                                        <a class="dropdown-item" id="pdf" href="#">PDF </a>
                                        <a class="dropdown-item" id="setup" href="#">Setup Auto Email </a>
                                        <a class="dropdown-item" id="addCol" href="#">Add Column </a>
                                    </div>
                                </div>

                            </div>
                            <button type="button" id="generate" class="btn btn-primary BtnActive"
                                style=" margin-top:-8px;"> Generate Report </button>
                        </div>
                    </div>

                </div>

            </div>

            <div class="container-fluid">
                <div class="row">

                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Area </span>
                                <input type="text" id="area" value="${areaID}" class="form-control text-input" />
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
          <!-- /.card-header -->
<div class="card-body">
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
                                    <th>Area Name
                                        <li class="filter-dropdown dropdown">
                                            <button class="almgrid-filter" data-toggle="dropdown"> <i
                                                    class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>
                                            <ul class="dropdown-menu filter-dropdown-ul">

                                            </ul>
                                        </li>
                                    </th>

                                    <th>WareHouse ID
                                        <li class="filter-dropdown dropdown">
                                            <button class="almgrid-filter" data-toggle="dropdown"> <i
                                                    class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>
                                            <ul class="dropdown-menu filter-dropdown-ul">

                                            </ul>
                                        </li>
                                    </th>

                                    <th>WareHouse Name
                                        <li class="filter-dropdown dropdown">
                                            <button class="almgrid-filter" data-toggle="dropdown"> <i
                                                    class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>
                                            <ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

                                            </ul>
                                        </li>
                                    </th>

                                    <th>Site ID
                                        <li class="filter-dropdown dropdown">
                                            <button class="almgrid-filter" data-toggle="dropdown"> <i
                                                    class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>
                                            <ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

                                            </ul>
                                        </li>
                                    </th>

                                </tr>

                                <tr>
                                    <th><input type="text" class="almgrid-search" placeholder="Search for Area.."></th>
                                    <th><input type="text" class="almgrid-search" placeholder="Search for WareHouse..">
                                    </th>
                                    <th><input type="text" class="almgrid-search"
                                            placeholder="Search for WareHouse Names..">
                                    </th>
                                    <th><input type="text" class="almgrid-search" placeholder="Search for Site Ids..">
                                    </th>
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




            
                var today = new Date();
                var date = (today.getMonth()) + '/' + '1' + '/' + today.getFullYear();
                var time = '00' + ":" + '00';
                var dateTime = date + ' ' + time;
                var c = Date.parse(dateTime);
                $('#startdate').datetimepicker({
                    defaultDate: c
                });
                // set end date defualt value to current
                var dateNow = new Date();

                $('#enddate').datetimepicker({
                    defaultDate: dateNow
                });

            </script>

            <script>

                $(document).ready(function () {




                	$('.panel-collapse').on('show.bs.collapse', function () {
                        
                	    $(this).siblings('.panel-heading').removeClass('active');
                	  });

                	  $('.panel-collapse').on('hide.bs.collapse', function () {
                	    $(this).siblings('.panel-heading').addClass('active');
                	  });
                                	
                    // Generate Report And Draw it in Grid View
                    $("#generate").click(function () {




                        
                        var Area = $("#area").val();
                        var n = Area.indexOf(";");
                        var AreaName = Area.substring(n + 1, Area.length);
                        
                        $.ajax({
                            type: "GET",
                            contentType: "application/json; charset=utf-8",
                            url: "${pageContext.request.contextPath}/GenerateAreaSitesReport",
                            dataType: "json",
                            data: {
                                "startDate": $("#startdate").val(),
                                "endDate": $("#enddate").val(),
                                "Area": AreaName,
                            },
                            success: function (data) {
                                // $("#gridTable tbody").empty();

                                if (data.Login != null && data.Login == 'Login') {
                                    location.replace("${pageContext.request.contextPath}/");
                                }
                                if (data != null) {
                                	  
                                    var ReportArray = data.AreaSitesReportArray;
                                    var ReportArrayGlobal = [];

                                    // Filling array that will be included in table
                                    $.each(ReportArray, function (i, value) {
                                        ReportArrayGlobal.push({
                                            ID: value.id, // This is mandatory for value of checkbox, the rest are drawn in table
                                            areaName: value.areaName,
                                            wareID: value.wareID,
                                            wareName: value.wareName,
                                            siteID: value.siteID
                                        });
                                    });

                                    var almgrid = new Almgrid({
                                        tableId: "gridTable",
                                        dataArray: ReportArrayGlobal,
                                        selectCheckbox: false
                                    });
                                }

                            },
                            error: function (error) {
                                console.log("The error is " + error);
                            }
                        });

                    });

                    // auto complete for area
                    $("#area").autocomplete({
                        source: function (request, response, event, ui) {

                            $.ajax({
                                type: "GET",
                                contentType: "application/json; charset=utf-8",
                                url: '${pageContext.request.contextPath}/GetAllAreasID',
                                data: {
                                    "areaID": $("#area").val(),

                                },
                                dataType: "json",
                                success: function (data) {
                                    if (data != null) {
                                        response(data.areaIDList);
                                    }
                                },
                                error: function (result) {
                                    alert("Error");
                                }
                            });
                        }, minLength: 0, maxShowItems: 40, scroll: true,


                        select: function (event, ui) {
                            area.value = (ui.item ? ui.item[0] + ";" + ui.item[1] : '');
                            return false;
                        }
                    }).autocomplete("instance")._renderItem = function (ul, item) {

                        // console.log("The item is " + item);

                        return $("<li class='each'>")
                            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                                item[0] + "</span><br><span class='desc'>" +
                                item[1] + "</span></div>")
                            .appendTo(ul);
                    };
                    $("#area").focus(function () {
                        if (this.value == "") {
                            $(this).autocomplete("search");
                        }
                    });

                });

            </script>



 <script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"
	async defer>
	</script>
 

</html>