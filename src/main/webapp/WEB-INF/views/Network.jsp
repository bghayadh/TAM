<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.almdialogextend.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <!-- 		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	 -->
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">


<style type="text/css">
/********** font awesome css change color icon *************/
.body{
overflow-y: scroll;
}
.ui-autocomplete {
	            	max-height: 150px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					
	        		}

/*********************** style dialog  *****************/
.ui-dialog, .ui-dialog-content {
    box-sizing: content-box !important;
}
.ui-dialog .ui-dialog-titlebar-close {
	background-image:
		url("${pageContext.request.contextPath}/resources/images/close.png");
	background-size: cover;
	position: absolute;
	background-color: gold;
}
.ui-dialog .ui-widget-header {
  background-color: #007b7c;
  color: gold;
  font-family: "Times New Roman", Times, serif;
}
.ui-dialog .ui-dialog-content{
padding-right: 0 !important;
}
.ui-dialog .ui-widget-content{
padding-right: 0 !important;
}
/*********************** end style dialog  *****************/
/*********************** style top button  *****************/


/*********************** small button clear *******************/
.small-btn-clear-filter button{ 
		  margin-top: 30px !important;
		/*  width: 50px;
		  height:35px;*/
		  align-content: center;
		 /* padding-bottom: 10px !important; */
		  text-align: center;
		  padding: 4px 8px;
          background: linear-gradient(90deg, #D3D3D3, #C0C0C0);
		  color: #111;
		  font-size:14px;
		  font-weight:600;
		  font-family: 'Times New Roman', Times, serif;
		  border:1px solid #111;
		  border-radius: 10px;
		  box-shadow: rgba(0, 0, 0, 0.25) 1px 2px 2px;		  		  
		  outline:none;
		  cursor:pointer;
		  transition: all 300ms ease-in-out;
		  
        } 
.small-btn-clear-filter button:hover{ 
		/*  width: 51px !important;
		  height:36px;*/
          background: linear-gradient(90deg, #F0E68C, #FFDF00);
		  color: #08526d;
		  padding: 4px 8px;
		  font-size:14px;
		  font-weight:600;
		  font-family: 'Times New Roman', Times, serif;
		  border:2px solid #111;
		  border-radius: 10px;
		  box-shadow: rgba(0, 0, 0, 0.25) 1px 2px 2px;		  		  
		  outline:none;
		  cursor:pointer;
        }
.small-btn-clear-filter-click button{ 
          background: linear-gradient(90deg, #CFB53B, #D4AF37) !important;
		  transition: all 300ms ease-in-out;
        }
/*********************** end small button clear *******************/
.top-btn-filter button{ 
          background: #fff;
		  color: #111;
		  padding: 8px 15px;
		  font-size:14px;
		  font-weight:600;
		  font-family: 'Times New Roman', Times, serif;
		  border:1px solid #111;
		  border-radius: 10px;
		  box-shadow: rgba(0, 0, 0, 0.25) 1px 2px 2px;		  		  
		  outline:none;
		  cursor:pointer;
		  transition: all 300ms ease-in-out;
        } 
.top-btn-filter button:hover{ 
          background: linear-gradient(90deg, #1288B3, #08526d);
		  color: gold;
		  padding: 8px 15px;
		  font-size:14px;
		  font-weight:600;
		  font-family: 'Times New Roman', Times, serif;
		  border:1px solid #111;
		  border-radius: 10px;
		  box-shadow: rgba(0, 0, 0, 0.25) 1px 2px 2px;		  		  
		  outline:none;
		  cursor:pointer;
        }
.top-btn-filter-click button{ 
          background: linear-gradient(90deg, #1288B3, #1ABAF4) !important;
		  color: gold !important;
		  border:3px solid #111 !important;
		  transition: all 300ms ease-in-out;
        }
.btn-filter-some-check button{ 
          background: linear-gradient(90deg, #F0F3F4, #B3B6B7) !important;
		  color: #111 !important;
		  border:2px solid #111 !important;
		  box-shadow: rgba(0, 0, 0, 0.25) 2px 3px 3px !important;
		  transition: all 300ms ease-in-out;
        }
.top-btn-filter-pressed button{ 
          background: linear-gradient(90deg, #F0E68C, #FFD700) !important;
		  color: #111 !important;
		  border:3px solid #111 !important;
		  box-shadow: rgba(0, 0, 0, 0.5) 3px 4px 4px !important;
		  transition: all 300ms ease-in-out;
        }
        /**************************************************/
.Apply-btn-filter button{ 
          background: #007b7c;
		  color: gold;
		  padding: 8px 15px;
		  font-size:14px;
		  font-weight:600;
		  font-family: 'Times New Roman', Times, serif;
		  border:1px solid gold;
		  border-radius: 10px;
		  box-shadow: rgba(0, 0, 0, 0.25) 1px 2px 2px;		  		  
		  outline:none;
		  cursor:pointer;
		  transition: all 300ms ease-in-out;
        } 
.Apply-btn-filter button:hover{ 
          background: #0AA2A3;
		  box-shadow: rgba(0, 0, 0, 0.5) 2px 3px 3px;	
		  border:2px solid gold;	  		  
		  transition: all 300ms ease-in-out;
        } 
.clear-all-btn-filter button{ 
          background: #08526d;
          margin-bottom:15px;
		  color: #fff;
		  padding: 8px 15px;
		  font-size:14px;
		  font-weight:600;
		  font-family: 'Times New Roman', Times, serif;
		  border:1px solid #111;
		  border-radius: 10px;
		  box-shadow: rgba(0, 0, 0, 0.25) 1px 2px 2px;		  		  
		  outline:none;
		  cursor:pointer;
		  transition: all 300ms ease-in-out;
        }            
.clear-all-btn-filter button:hover{ 
          background: linear-gradient(90deg, #1288B3, #08526d);
		  color: gold;
		  padding: 8px 15px;
		  font-size:14px;
		  font-weight:600;
		  font-family: 'Times New Roman', Times, serif;
		  border:1px solid #111;
		  border-radius: 10px;
		  box-shadow: rgba(0, 0, 0, 0.25) 1px 2px 2px;		  		  
		  outline:none;
		  cursor:pointer;
        } 
.clear-all-btn-clicked button{ 
          background: linear-gradient(90deg, #1280AA, #159FD4) !important;
		  color: gold;
		  border:3px solid #111 !important;
		  box-shadow: rgba(0, 0, 0, 0.5) 2px 3px 3px;	
		  transition: all 150ms ease-in-out;	  		  
        }

.input-font-size{
style= font-size: 12px;
}   
.row-fix {
  padding-bottom: 10px;
  padding-top: 10px;
  margin-bottom: 10px;
}    
/*********************** end style top button  *****************/
/*********************** check box style *****************/
.cb {
	display: none;
}

label {
	display: inline-block;
	position: relative;
	padding-left: 20px;
	font-size: 16px;
	line-height: 20px;
}

label:before {
	line-height: 20px;
	content: "";
	display: inline-block;
	width: 16px;
	height: 16px;
	position: absolute;
	left: 0;
	background-color: #ffffff;
	border: 1px solid #08526d;
}

input[type=checkbox]:checked+label:before, label:hover:before {
	content: "\2713";
	color: #08526d;
	text-align: center;
	line-height: 16px;
}
/*********************** ENDD check box style *****************/
</style>



</head>
<body>
	<!-- nav -->
	
<%-- 	<c:set var = "page" value = "network"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="network" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
	
	<!--  -->
	<!-- ************************************************ -->
	<p></p>
	<div class="container-fluid">


		<div class="row second">

			<div class="col-md-4">
				<div class="input-icons">


					<input type="text" id="autocompliteSearch"
						style="width: 475px; height: 40px; "
						class="input-field form-control" placeholder="Search here.." />


				</div>
			</div>

			<div class="col-md-5"></div>
			<div class="col-md-3" style="text-align: right;">
				<div class="btn-group pull-right">
					<div class="glyph">


						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search" id="open-popup-btn">
							<i class="fa fa-search"></i>
						</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title=" GIS"
							onclick='window.location.href = "${pageContext.request.contextPath}/gisPage"'>
							<i class="fas fa-map-marked-alt"></i>
						</button>


						<button type="button" class="btn btn-danger" data-toggle="tooltip"
							data-placement="top" title=" Folder Tree"
							style="background: #da6815;">
							<i class="fas fa-sitemap"></i>
						</button>

						<button type="button" id="Fview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="Form View"
							onclick='window.location.href = "${pageContext.request.contextPath}/SiteFormView"'>
							<i class="fa fa-edit"></i>
						</button>
						<a href="Sitelistview">

							<button type="submit" id="Lview" class="btn btn-light"
								data-toggle="tooltip" data-placement="top" title="List View">
								<!--  onclick='window.location.href = "${pageContext.request.contextPath}/SiteListView"' -->
								<i class="fa fa-bars"></i>
							</button>
						</a>

					</div>
				</div>
			</div>
		</div>
	</div>
	<hr>

<!-- ^********************************************** dialog ******************************************************* -->

	<div id="dialog" title="Network Search" style="visibility: hidden; font-family: 'Times New Roman', Times, serif;" >
	<div class="container-fluid">

		<!-- create tab -->
		<div class="row">
			<div class=" col-xs-12 col-md-12 col-sm-12 col-lg-12">
				<ul class="nav nav-tabs mynav" id="custom-tabs-all-tab"
					role="tablist" style="margin-top: 0px;">

					<li class="nav-item"><a class="nav-link active"
						id="search-tab-m" data-toggle="tab" href="#search-tab" role="tab"
						aria-controls="search-tab" aria-selected="true"
						style="color: gold;">Search</a></li>

					<li class="nav-item"><a class="nav-link"
						id="advanced-search-tab-m" data-toggle="tab"
						href="#advanced-search-tab" role="tab"
						aria-controls="advanced-search-tab" aria-selected="false"
						style="color: gold;">Advanced Search</a></li>

				</ul>

			</div>
		</div>

		<div class="row">
		<div class="col-xs-12 col-md-12 col-sm-12 col-lg-12">
				<div class="card card-primary card-tabs cards-margin">
					<div class="card-body">
						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" id="search-tab"
								role="tabpanel" aria-labelledby="search-tab-m">
								<!-- ********************************* dialog top btn *********************************-->
								
								<div class="row" style="padding: 5px; ">
								<div class="col-md-12 col-sm-12 col-lg-12 col-xs-12">
								<div class="btn-toolbar">
								<div class="btn-group flex-wrap" data-toggle="buttons">
									<div class=" top-btn-filter" id="popupSuppBtndiv" style="height: 60px; width: 110px;">
										<button id="popupSuppBtn" >Supplier</button>
									</div>
									
									 <div class=" top-btn-filter" style="height: 60px;  width: 120px;" id="nodeTypeBtndiv">
								     <button id="nodeTypeBtn" >Node Type</button>
								     </div>
									
									<div class="  top-btn-filter"  id="popupItmBtndiv" style="height: 60px;  width: 90px;">
										<button id="popupItmBtn">Item</button>
									</div>
									
									<div class=" top-btn-filter"  style="height: 60px;  width: 90px;" id="popupAreaBtndiv">
										<button id="popupAreaBtn">Area</button>
									</div>

									<div class=" top-btn-filter">

									<div class="input-group" style="margin-top: 0px;" id="popupCheckBtndiv">
										<button id="popupCheckBtn" style="width: 130px;">Site/Node/Cell</button>
										
										<input type="checkbox" id="checksite" name="site" value="site" class=" cb" >
											 <label for="checksite" style="font-size: 11px; margin-left: 10px;">Site</label> 
											<input id="checknode" type="checkbox" name="node" value="node" class="cb ">
										<label for="checknode" style="font-size: 11px;">Node</label> 
										<input id="checkcell" type="checkbox" name="cell" value="cell" class="cb"> 
											<label for="checkcell" style="font-size: 11px;">Cell</label>
											
											
											
										</div>
									</div>
									
									</div>
									</div>
								 </div>
								</div>
								
								<!-- ********************************* end dialog top btn *********************************-->
								<hr style="background: #08526d;">
								<p></p>
								
								<!-- ********************************* dialog body *********************************-->
									<div class="btn-toolbar">
								
								  		<div class="btn-group">	
								  		<div class=" small-btn-clear-filter" id="clearItmdiv" style=" width: 52px; height: 37px;">
								    		<button id="clearWare">
								     			clear
								    		</button>
					    				</div>
					    				</div>
					    				
					    				<div class="input-group" >
											<div class="form-group" style="width: 390px;  padding-left: 10px;">
												<span>Warehouse ID</span> 
												<input type="text" id="wareid" value="${WareId}"  class="form-control input-font-size"/>
											</div>
										</div>
					
					    				<div class="input-group" >
											<div class="form-group" style="width: 390px; padding-left: 10px;">
												<span>Warehouse Name</span> 
												<input type="text" id="warename" value="${WareName}"  class="form-control input-font-size"/>
											</div>
										</div>
										
										<div class="input-group" >
										
											<div class="form-group" style="width: 300px;  padding-left: 10px;">
												<span>Site ID</span> 
												<input type="text" id="siteid" value="${siteId}"  class="form-control input-font-size"/>
											</div>
										</div>
					    				
					    		</div>
								
					    		
					    			<!-- ********************************* second row *********************************-->
					    			
					    			<div class="btn-toolbar">
										<div class="btn-group">	
								  		<div class=" small-btn-clear-filter" id="clearItmdiv" style=" width: 52px; height: 37px;">
								    		<button id="clearNode">
								     			clear
								    		</button>
					    				</div>
					    				</div>
					    				
					    				<div class="input-group" >
											<div class="form-group" style="width: 390px;  padding-left: 10px;">
												<span>Node ID</span> 
												<input type="text" id="nodeid" value="${nodeId}"  class="form-control input-font-size"/>
											</div>
										</div>
										
					    				<div class="input-group" >
											<div class="form-group" style="width: 390px;  padding-left: 10px;">
												<span>Node Name</span> 
												<input type="text" id="nodeName" value="${nodeName}"  class="form-control input-font-size"/>
											</div>
										</div>
										
										<div class="input-group" >
											<div class="form-group"  style="width: 300px;  padding-left: 10px;">
												<span>Node Type</span> 
												<input type="text" id="nodeType" value="${nodeType}"  class="form-control input-font-size"/>
											</div>
										</div>
					    				
					    				
					    				</div>
					    		
					    			
					    			<!-- ********************************* end second row *********************************-->
					    			
					    				<!-- ********************************* 3 row *********************************-->
					    			<div class="btn-toolbar">
										
										<div class="btn-group">	
								  		<div class=" small-btn-clear-filter" id="clearItmdiv" style=" width: 52px; height: 37px;">
								    		<button id="clearItm">
								     			clear
								    		</button>
					    				</div>
					    				</div>
					    				<div class="input-group" >
											<div class="form-group" style="width: 390px;  padding-left: 10px;">
												<span>Item ID</span> 
												<input type="text" id="itmCode" value="${ItmCode}"  class="form-control input-font-size" />
											</div>
										</div>
					    				
					    				<div class="input-group" >
											<div class="form-group" style="width: 390px;  padding-left: 10px;">
												<span>Item Name</span> 
												<input type="text" id="itmModel" value="${ItmModel}"  class="form-control input-font-size" />
											</div>
										</div>
										
										<div class="input-group" >
											<div class="form-group" style="width: 300px;  padding-left: 10px;">
												<span>Item Part Number</span> 
												<input type="text" id="itmPartNo" value="${ItmPartNo}"  class="form-control input-font-size" />
											</div>
										</div>
					    				
					    		</div>
					    			
					    			<!-- ********************************* end 3 row *********************************-->
					    			
					    				<!-- ********************************* 3 row *********************************-->
					    			<div class="btn-toolbar">
					    			
									<div class="btn-group">	
								  		<div class=" small-btn-clear-filter" id="clearItmdiv" style=" width: 52px; height: 37px;">
								    		<button id="clearSupp">
								     			clear
								    		</button>
					    				</div>
					    				</div>
					    				<div class="input-group" >
											<div class="form-group" style="width: 390px;  padding-left: 10px;">
												<span>Supplier ID</span> 
												<input type="text" id="prsuppid" value="${prsupplierID}"  class="form-control input-font-size"/>
											</div>
										</div>
					    				
					    				<div class="input-group">
											<div class="form-group"  style="width: 390px;  padding-left: 10px;">
												<span>Supplier Name</span> 
												<input type="text" id="supplierName" value="${supplier}"  class="form-control input-font-size"/>
											</div>
										</div>
										
								
					    				
					    		</div>
					    			
					    			<!-- ********************************* end 3 row *********************************-->
					    			<hr>
					    			<!-- *********************************last button row *********************************-->
					    			<div class="btn-toolbar justify-content-between">
										<div class="btn-group flex-wrap">
								
					    			<div class=" Apply-btn-filter" style=" width: 70px; height: 60px;">
					    			<button id="applyBtn">Apply</button>
					    			</div>
					    			
					    			<div class=" clear-all-btn-filter" id="ClearAllBtndiv" style="padding-left: 10px;  width: 110px; height: 60px;">
					    			<button id="ClearAllBtn">Clear All</button>
					    				</div>
					    			</div>
					    			
					    			
											<div class="btn-group pull-right" style="text-align: right;">
												<div class="glyph">
							
							
													<button type="button" class="btn btn-light" data-toggle="tooltip"
														data-placement="top" title=" GIS" id="popup-gis-1">
														<i class="fas fa-map-marked-alt "></i>
													</button>
							
							
													<button type="button" class="btn btn-light" data-toggle="tooltip"
														data-placement="top" title=" Folder Tree" id="popup-tree-1">
														<i class="fas fa-sitemap"></i>
													</button>
							
													<button type="button" class="btn btn-light"
														data-toggle="tooltip" data-placement="top" title="Form View" id="popup-formview-1">
														<i class="fa fa-edit"></i>
													</button>
													
							
														<button type="submit" class="btn btn-light"
															data-toggle="tooltip" data-placement="top" title="List View" id="popup-listview-1">
															
															<i class="fa fa-bars"></i>
														</button>
							
													</div>
												
										</div>	
					    			
					    			</div>
					    			<!-- ********************************* end last button row *********************************-->
					    			<!-- ********************************* end dialog body *********************************-->
							</div>
								<div class="tab-pane fade" id="advanced-search-tab"
									role="tabpanel" aria-labelledby="advanced-search-tab-m">
									<h1>2</h1>
								</div>
						</div>
					</div>
			</div>
			</div>
		</div>
</div>

</div>


		<!--********************************* tabs ********************************* -->



		<!-- ******************************************************************* -->
<script type="text/javascript">
var suppBtnPopupCheck = "suppBtnPopupCheck";
var NodeTypeBtnPopupCheck = "NodeTypeBtnPopupCheck";
var itemBtnPopupCheck = "itemBtnPopupCheck";
var areaBtnPopupCheck = "areaBtnPopupCheck";
//
var siteCheckboxPopupCheck = "siteCheckboxPopupCheck";
var nodeCheckboxPopupCheck = "nodeCheckboxPopupCheck";
var cellCheckboxPopupCheck = "cellCheckboxPopupCheck";
//view btn
var gisBtnPopupCheck = "gisBtnPopupCheck";
var treeBtnPopupCheck = "treeBtnPopupCheck";
var formViewBtnPopupCheck = "formViewBtnPopupCheck";
var listViewBtnPopupCheck = "listViewBtnPopupCheck";
//navigation btn
var btnPopupGis1 = "goToGis";
var btnPopupTree1 = "goToTree";
var btnPopupListView1 = "goToListView";
var btnPopupFormView1 = "goToFormView";
var arrayPopupBtnNavigationChecked = [];
//array set btn
var arrayPopupBtnChecked = [];
//
var checkpopupSuppBtn = false;
$("#popupSuppBtn").click(  function() {
	//$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	if(checkpopupSuppBtn){arrayPopupBtnChecked.pop(suppBtnPopupCheck); checkpopupSuppBtn = false;}
	else{arrayPopupBtnChecked.push(suppBtnPopupCheck); checkpopupSuppBtn = true;}
	
	console.log("toggle class");
	$("#popupSuppBtndiv").toggleClass("top-btn-filter-click");
	 setTimeout(
	            function() {
	               console.log("remove style")
	               //$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	               $("#popupSuppBtndiv").toggleClass("top-btn-filter-click");
	               $("#popupSuppBtndiv").toggleClass("top-btn-filter-pressed"); 
	               document.getElementById("popupSuppBtn").blur();
	            },
	            150);
	});
	var checkpopupNodeTypeBtn = false;
$("#nodeTypeBtn").click(  function() {
	//$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	if(checkpopupNodeTypeBtn){arrayPopupBtnChecked.pop(NodeTypeBtnPopupCheck ); checkpopupNodeTypeBtn = false;}
	else{arrayPopupBtnChecked.push(NodeTypeBtnPopupCheck ); checkpopupNodeTypeBtn = true;}
	console.log("toggle class");
	$("#nodeTypeBtndiv").toggleClass("top-btn-filter-click");
	 setTimeout(
	            function() {
	               console.log("remove style")
	               //$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	               $("#nodeTypeBtndiv").toggleClass("top-btn-filter-click");
	               $("#nodeTypeBtndiv").toggleClass("top-btn-filter-pressed"); 
	               document.getElementById("nodeTypeBtn").blur();
	            },
	            150);
	});
	var checkpopupItemBtn = false;
$("#popupItmBtn").click(  function() {
	//$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	if(checkpopupItemBtn){arrayPopupBtnChecked.pop(itemBtnPopupCheck  ); checkpopupItemBtn = false;}
	else{arrayPopupBtnChecked.push(itemBtnPopupCheck  ); checkpopupItemBtn = true;}
	console.log("toggle class");
	$("#popupItmBtndiv").toggleClass("top-btn-filter-click");
	 setTimeout(
	            function() {
	               console.log("remove style")
	               //$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	               $("#popupItmBtndiv").toggleClass("top-btn-filter-click");
	               $("#popupItmBtndiv").toggleClass("top-btn-filter-pressed"); 
	               document.getElementById("popupItmBtn").blur();
	            },
	            150);
	});popupAreaBtn

	var checkpopupAreaBtn = false;
$("#popupAreaBtn").click(  function() {
	//$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	if(checkpopupAreaBtn){arrayPopupBtnChecked.pop(areaBtnPopupCheck  ); checkpopupAreaBtn = false;}
	else{arrayPopupBtnChecked.push(areaBtnPopupCheck  ); checkpopupAreaBtn = true;}
	console.log("toggle class");
	$("#popupAreaBtndiv").toggleClass("top-btn-filter-click");
	 setTimeout(
	            function() {
	               console.log("remove style")
	               //$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	               $("#popupAreaBtndiv").toggleClass("top-btn-filter-click");
	               $("#popupAreaBtndiv").toggleClass("top-btn-filter-pressed"); 
	               document.getElementById("popupAreaBtn").blur();
	            },
	            150);
	});


//********************** button and check box (site node cell) ****************************
var btnCheckbox = false;
$("#popupCheckBtn").click(  function() {
	//$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	$("#popupCheckBtndiv").toggleClass("top-btn-filter-click");
		var cnode = $('#checknode').is(':checked');
		var ccell = $('#checkcell').is(':checked');
		var csite = $('#checksite').is(':checked');
	setTimeout(
	            function() {
	               console.log("remove style")
	               //$("#popupSuppBtndiv").toggleClass("top-btn-filter");
	               
	              // $("#popupCheckBtndiv").toggleClass("top-btn-filter-pressed"); 
	               
	               $("#popupCheckBtndiv").toggleClass("top-btn-filter-click");
	           	if(cnode == true && ccell == true && csite == true){
	           		$("#popupCheckBtndiv").removeClass("btn-filter-some-check");
	           		$("#popupCheckBtndiv").removeClass("top-btn-filter-pressed");
	                 		$("#checksite").prop('checked', false);
	            			$("#checknode").prop('checked', false);
	            			$("#checkcell").prop('checked', false);
	            			btnCheckbox = false;
	            			//remove from array
	            			arrayPopupBtnChecked.pop(siteCheckboxPopupCheck);
	            			arrayPopupBtnChecked.pop(nodeCheckboxPopupCheck);
	            			arrayPopupBtnChecked.pop(cellCheckboxPopupCheck);
	                        }else{
	                        	$("#popupCheckBtndiv").removeClass("btn-filter-some-check");
	        	           		$("#popupCheckBtndiv").addClass("top-btn-filter-pressed");
	                     	  if(cnode == true || ccell == true || csite == true){
	                     	   	   $("#popupCheckBtndiv").removeClass("btn-filter-some-check");
	                     	          }
	                     		$("#checksite").prop('checked', true);
	                    		$("#checknode").prop('checked', true);
	                    		$("#checkcell").prop('checked', true);
	                    		//check for array
	                    		if(!arrayPopupBtnChecked.includes(siteCheckboxPopupCheck)){
		                    		//add site to array
	                    			arrayPopupBtnChecked.push(siteCheckboxPopupCheck);
		                    		}
	                    		if(!arrayPopupBtnChecked.includes(nodeCheckboxPopupCheck)){
		                    		//add node to array
	                    			arrayPopupBtnChecked.push(nodeCheckboxPopupCheck);
		                    		}
	                    		if(!arrayPopupBtnChecked.includes(cellCheckboxPopupCheck)){
		                    		//add cell to array
	                    			arrayPopupBtnChecked.push(cellCheckboxPopupCheck);
		                    		}
	                    		btnCheckbox = true;
	           	               }
	               document.getElementById("popupCheckBtn").blur();	           
	            },
	            150);
		
	
	
	  
	});
	
$("#checksite").change(  function() {		
		var cnode = $('#checknode').is(':checked');
		var ccell = $('#checkcell').is(':checked');
		var csite = $('#checksite').is(':checked');
		if(csite){arrayPopupBtnChecked.push(siteCheckboxPopupCheck);}
		else{arrayPopupBtnChecked.pop(siteCheckboxPopupCheck);}
		if(cnode == true && ccell == true && csite == true){
			$("#popupCheckBtndiv").removeClass("btn-filter-some-check");
			$("#popupCheckBtndiv").addClass("top-btn-filter-pressed");
			}else if(cnode == true && ccell == true && csite == false){
				$("#popupCheckBtndiv").removeClass("top-btn-filter-pressed");
				$("#popupCheckBtndiv").addClass("btn-filter-some-check");
				}else if(ccell == true || cnode == true){
					console.log("have some check");
					}else if(csite == true){
						$("#popupCheckBtndiv").addClass("btn-filter-some-check");
						}else{$("#popupCheckBtndiv").removeClass("btn-filter-some-check");
						$("#popupCheckBtndiv").removeClass("top-btn-filter-pressed");
						}
		});
	
$("#checknode").change(  function() {						
	var cnode = $('#checknode').is(':checked');
	var ccell = $('#checkcell').is(':checked');
	var csite = $('#checksite').is(':checked');
	
	if(cnode){arrayPopupBtnChecked.push(nodeCheckboxPopupCheck);}
	else{arrayPopupBtnChecked.pop(nodeCheckboxPopupCheck);}
	
	if(cnode == true && ccell == true && csite == true){
		$("#popupCheckBtndiv").removeClass("btn-filter-some-check");
		$("#popupCheckBtndiv").addClass("top-btn-filter-pressed");
		}else if(cnode == false && ccell == true && csite == true){
			$("#popupCheckBtndiv").removeClass("top-btn-filter-pressed");
			$("#popupCheckBtndiv").addClass("btn-filter-some-check");
			}else if(ccell == true || csite == true){
				console.log("have some check");
				}else if(cnode == true){
					$("#popupCheckBtndiv").addClass("btn-filter-some-check");
				}else{$("#popupCheckBtndiv").removeClass("btn-filter-some-check");}
	});
	
$("#checkcell").change(  function() {						
	var cnode = $('#checknode').is(':checked');
	var ccell = $('#checkcell').is(':checked');
	var csite = $('#checksite').is(':checked');

	if(ccell){arrayPopupBtnChecked.push(cellCheckboxPopupCheck);}
	else{arrayPopupBtnChecked.pop(cellCheckboxPopupCheck);}
	
	if(cnode == true && ccell == true && csite == true){
		$("#popupCheckBtndiv").removeClass("btn-filter-some-check");
		$("#popupCheckBtndiv").addClass("top-btn-filter-pressed");
		}else if(cnode == true && ccell == false && csite == true){
			$("#popupCheckBtndiv").removeClass("top-btn-filter-pressed");
			$("#popupCheckBtndiv").addClass("btn-filter-some-check");
			}else if(cnode == true || csite == true){
				console.log("have some check");
				}else if(ccell == true){
					$("#popupCheckBtndiv").addClass("btn-filter-some-check");
				}else{$("#popupCheckBtndiv").removeClass("btn-filter-some-check");}
	});
	//********************** end button and check box (site node cell) ****************************
	
$(document).ready(function() {
	var popOpen = false;
	var dialogOptions = {
			
			autoOpen: false,
		     // "width" : 1300,
		      //"height" : 580,
		      maxHeight: 630,
			   "width" : 'auto',
			   "height" : 'auto',
		        maxWidth: 1300,
		        fluid: true,
		   	 // minWidth: 950,
		      "modal" : false,
		      "resizable" : true,
		      "draggable" : true,
		  	show : {
				effect : "blind",
				duration : 300
			},
			hide : {
				effect : "fade",
				duration : 300
			},
			 create: function() {
				  $(this).closest('div.ui-dialog')
                  .find('.ui-dialog-titlebar-close')
                  .click(function(e) {
               	   var styleObject = $('#open-popup-btn').prop('style'); 
               	  	  styleObject.removeProperty('background-color');
               	  	  $("#open-popup-btn").toggleClass("btn-light");
               	  	  $("#open-popup-btn").toggleClass("btn-danger");
               	  	  document.getElementById("open-popup-btn").blur();
               	  	  popOpen = false;
                      e.preventDefault();
                  });
		            
		        },
		        open: function(event, ui) {
		        	 
		        	 fluidDialog();
		        },
		        resize: function() {
		        	  var heightPadding = parseInt($(this).css('padding-top'), 10) + parseInt($(this).css('padding-bottom'), 10),
		        	    widthPadding = parseInt($(this).css('padding-left'), 10) + parseInt($(this).css('padding-right'), 10),
		        	    titlebarMargin = parseInt($(this).prev('.ui-dialog-titlebar').css('margin-bottom'), 10);
		        	  $(this).height($(this).parent().height() - $(this).prev('.ui-dialog-titlebar').outerHeight(true) - heightPadding - titlebarMargin);
		        	  $(this).width($(this).prev('.ui-dialog-titlebar').outerWidth(true) - widthPadding);
		        	  fluidDialog();
		        	},
		    };
	var dialogExtendOptions = {
		      "closable" : true,
		      "maximizable" : true,
		      "minimizable" : true,
		      "minimizeLocation" : "left",
		      "collapsable" : true,
		      "dblclick" : "maximize",
		    };
	var dlg = $("#dialog").dialog(dialogOptions).dialogExtend(dialogExtendOptions);
			var styleObject = $('#dialog').prop('style');

			styleObject.removeProperty('visibility');

			$(window).resize(function () {
			    fluidDialog();
			});

			// catch dialog if opened within a viewport smaller than the dialog width
			$(document).on("dialogopen", ".ui-dialog", function (event, ui) {
			    fluidDialog();
			});

			function fluidDialog() {
				console.log("fluid call");
			    var $visible = $(".ui-dialog:visible");
			    // each open dialog
			    $visible.each(function () {
			        var $this = $(this);
			        var dialog = $this.find(".ui-dialog-content").data("ui-dialog");
			        // if fluid option == true
			        if (dialog.options.fluid) {
			            var wWidth = $(window).width();
			            var wHeight = $(window).height();
			            // check window width against dialog width
			             if (wHeight > (parseInt(dialog.options.maxHeight) +50) )  {
			                // keep dialog from filling entire screen
			                $this.css("max-height", "90%");

			                $(dlg[0]).dialog("widget").css("position", "relative").find(".ui-dialog-content").show().dialog("widget").find(".ui-dialog-buttonpane").show().end().find(".ui-dialog-content").dialog("option", {
         			                    
         			                  //  "width":  $(this).width($(this).prev('.ui-dialog-titlebar').outerWidth(true) - 10),
    			            	//	"width":"90%",
    			            		//"height":"90%"
    			            		//"height":$(window).height()-50,
    			            		"height":$(window).height()-60,
    			            		"maxHeight": $(window).height()-50 
    			                  });

			            }
			             if (wHeight < (parseInt(dialog.options.maxHeight)) + 50)  {
			                // keep dialog from filling entire screen
			            //    $this.css("max-height", $(window).height()-50);

			                $this.css("max-height", "90%");
			                $(dlg[0]).dialog("widget").css("position", "relative").find(".ui-dialog-content").show().dialog("widget").find(".ui-dialog-buttonpane").show().end().find(".ui-dialog-content").dialog("option", {
         			                    
         			                  //  "width":  $(this).width($(this).prev('.ui-dialog-titlebar').outerWidth(true) - 10),
    			            	//	"width":"90%",
    			            		//"height":"90%"
    			            		//"height":$(window).height()-50,
    			            		"height":$(window).height()-60,
    			            		"maxHeight": $(window).height()-50 
    			                  });

			            } else {
			            	$this.css("max-height", "90%");
			               // $this.css("max-height", $(window).height()-50 + "px");
			             //  $this.css("max-height", dialog.options.maxWidth + "px");
			               /* $(dlg[0]).dialog("widget").css("position", "relative").find(".ui-dialog-content").show().dialog("widget").find(".ui-dialog-buttonpane").show().end().find(".ui-dialog-content").dialog("option", {

			                	"height":$(window).height()-60,
			            		//"maxHeight": $(window).height()-50 
			                  });*/
			           
			            }
			             if (wWidth > (parseInt(dialog.options.maxWidth) + 50))  {
				                // keep dialog from filling entire screen
				                $this.css("max-width", "90%");

				                $(dlg[0]).dialog("widget").css("position", "relative").find(".ui-dialog-content").show().dialog("widget").find(".ui-dialog-buttonpane").show().end().find(".ui-dialog-content").dialog("option", {
	         			                    
	         			                  //  "width":  $(this).width($(this).prev('.ui-dialog-titlebar').outerWidth(true) - 10),
	         			              // "height":$(window).height()-60,
				            			//"maxHeight": $(window).height()-50,
	    			            		//"width":"90%",
	    			            		//"height":"90%"
	    			            		//"maxWidth": "90%", 
	    			                  });

				            }
			            if (wWidth < (parseInt(dialog.options.maxWidth) + 50))  {
			                // keep dialog from filling entire screen
			                
			                $this.css("max-width", "90%");// add padding right and left edge

			                $(dlg[0]).dialog("widget").css("position", "relative").find(".ui-dialog-content").show().dialog("widget").find(".ui-dialog-buttonpane").show().end().find(".ui-dialog-content").dialog("option", {
         			                    
         			                  //  "width":  $(this).width($(this).prev('.ui-dialog-titlebar').outerWidth(true) - 10),
         			              //  "height":$(window).height()-60,
			            		//	"maxHeight": $(window).height()-50,
    			            		//"width":"90%",
    			            		//"height":"90%"
    			            		"maxWidth": "90%" 
    			                  });

			            } else {
			            	$this.css("max-width", "90%");
			             //   $this.css("max-width", dialog.options.maxWidth + "px");

			            /*    $(dlg[0]).dialog("widget").css("position", "relative").find(".ui-dialog-content").show().dialog("widget").find(".ui-dialog-buttonpane").show().end().find(".ui-dialog-content").dialog("option", {
 			                    
   			                  //  "width":  $(this).width($(this).prev('.ui-dialog-titlebar').outerWidth(true) - 10),
   			                  //  "height":$(window).height()-60,
			            	//	"maxHeight": $(window).height()-50,
			            		//"width":dialog.options.maxWidth,
			            		//"height":"90%"
			            //		"width":"90%",
			            	//	"maxWidth":dialog.options.maxWidth  
			                  }); */
			            }
			            //reposition dialog
			           // dialog.option("position", { my: "center", at: "center", of: window });
			            $("#dialog").dialog("option", "position", {my: "center", at: "center", of: window});
			        }
			    });

			}
			//********************** autocomplite warehouse ****************************
	$("#wareid").on('input', function(){		
		 $("#warename").val('');
		 $("#siteid").val('');

	});

//******************* main search ******************

var searchkey = $("#autocompliteSearch").val();

			$("#autocompliteSearch").autocomplete({
				
				 source: function(request, response) {
					 var searchkey = $("#autocompliteSearch").val();
					  $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetSearchEngine',
			                 data: {
				                    "searchkey":searchkey,
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.listSearch);			                     
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
					 }, 
			    minLength:1,
			    maxShowItems: 10,
			    scroll:true,
			    autoFocus: true	

		});

//******************* end main search *****************

	
$("#wareid").autocomplete({
				
		 source: function(request, response) {
			 console.log("enter for ware name");
	        	var siteId=$("#siteid").val();
	        	var wareName=$("#warename").val();
	        	
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
		                 data: {
			                    "WareName":wareName,
								"warehouseName" : $("#wareid").val(),
								"SiteId":siteId,
						 },
		                 dataType: "json",
		                 success: function (data) {
			                 console.log("set data");
		                     if (data != null) {
		                         response(data.globalList);
		                         console.log(data.globalList);
		                     
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 20, scroll:true,		
	               
	        
			select: function(event, ui) {
				wareid.value = (ui.item ? ui.item[0]  : '');
				warename.value=ui.item[1];
				siteid.value=ui.item[2];

	          
					return false;
						}

	       
					
			    }).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold; font-size:12px;'>" +
	                    item[0] + "</span><br><span class='desc'>" +
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
	        	};
				$("#wareid").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});
	// ^^^^^^^^^^^^^^^^^^^^^^^^ warehouse name
	
	$("#warename").autocomplete({
		
		showHeader: true,
		
        source: function(request, response) {

                      var warehouse=$("#wareid").val();
                      var siteid=$("#siteid").val();
                     
	        
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
	                 data: {
							  "warehouseName" :$("#warename").val()					 
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
	         }, minLength:0, maxShowItems: 20, scroll:true,		
               
        
		select: function(event, ui) {
			
			warename.value = (ui.item ? ui.item[1]   : '');
			


			$.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/GetAllWarehouse',
                data: {
                	warehouseName : ui.item[1],
				 },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
	               
	                console.log("The list is "+data.globalList[0]) ;

	                WareId = data.globalList[0];
	                console.log("/*/-WareId is" +WareId);

	                if(data.globalList.length == 1){
	                	 console.log("/*/*Entered here");
	                	
	                	$("#wareid").val(WareId);
	                	siteid.value=ui.item[2];
		                }
	                else{
	                	$("#wareid").val("");
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
                .append("<div class='acItem'><span class='name' style='font-weight:bold; font-size:12px;' >" +
                    //item[0] + "</span><br><span class='desc'>" +
                   // item[1] + "</span><br><span class='desc'>" +
                    //item[2] + "</span></div>")
                    item[1] + "</span><br><span class='desc'>" 
                    )
                .appendTo(ul);
        	};
			$("#warename").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});
				
	// ^^^^^^^^^^^^^^^^^^^^^^^^ site id
	$("#siteid").autocomplete({
	showHeader: true, 
       source: function(request, response) {

                     var warehouse=$("#wareid").val();
                     var warehouseName=$("#warename").val();
        
             $.ajax({
                 type: "GET",
                 contentType: "application/json; charset=utf-8",
                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
                 data: {
						  "warehouseName" : $("#siteid").val(),					 
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
         }, minLength:0, maxShowItems: 20, scroll:true,		
              
       
	select: function(event, ui) {
		
		siteid.value = (ui.item ? ui.item[2]   : '');

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
                	
                	$("#wareid").val(WareId);
                	warename.value=ui.item[1];
	                }
                

                else{
                	$("#wareid").val("");
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
               .append("<div class='acItem'><span class='name' style='font-weight:bold; font-size:12px;' >" +
                   //item[0] + "</span><br><span class='desc'>" +
                  // item[1] + "</span><br><span class='desc'>" +
                   //item[2] + "</span></div>")
                   item[0] + "</span><br><span class='desc'>" 
                   )
               .appendTo(ul);
       	};
		$("#siteid").focus(function(){
  	        	if (this.value == ""){
  	            	$(this).autocomplete("search");
  	        	}						
		});
	//********************** end autocomplite warehouse ****************************
	//**********************  autocomplite node ****************************

	//**********************  end autocomplite node ****************************


	//**********************  autocomplite item ****************************
$('#itmCode').autocomplete({			
	source: function(request, response) {
		 console.log("click itm");
    	    var itemModel =  document.getElementById('itmModel').value;
    	    var itemPartNb =  document.getElementById('itmPartNo').value;
    	    var itemCode =  document.getElementById('itmCode').value;
    	   if(itemModel == ""){
    		   itemModel = "empty";
	    	   }
    	   if(itemPartNb == ""){
    		   itemPartNb = "empty";
	    	   }
    	   
    		   console.log("seect itm");
  	    	 $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
               
                url: '${pageContext.request.contextPath}/GetAllItemModelPopup',
                data: {
					"Item_Model" : request.term,
					Item_code : "",
					"Item_PartNum" : "1",
					
			 },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListItemModelPopup);
                    }
                },
                error: function(result) {
                    alert("Error");
                }
            });
        									
	   }, minLength:0, maxShowItems: 20, scroll:true,
		select: function(event, ui) {
			console.log("log"+ui.item[1] + ":" + ui.item[2]);
			itmCode.value =(ui.item ? ui.item[1] + ":" + ui.item[2] : '');																			
			itmModel.value = ui.item[0];
			itmPartNo.value = ui.item[3];
			return false;
		}
	}).autocomplete("instance")._renderItem = function(ul, item) {
   	return $('<li class="each"></li>').data( "item.autocomplete", item )
   			.append('<div class="acItem"><span class="name" style="font-weight:bold; font-size:12px;">' +
            
                  item[1] + '</span><br><span class="desc">' +
                  item[2] + '</span></div>').appendTo(ul);
};
$('#itmCode').focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
 	        }
});


$('#itmModel').autocomplete({
	//				
		    	    source: function(request, response, event, ui) {
		    	    	 var itemPartNb =  document.getElementById('itmPartNo').value;
	        			 var Item_code = $('#itmCode').val();
	        			 if(Item_code == ""){
	        				 Item_code = "empty";
		        			 }
	        			 if(Item_code != "empty")
		        		 {
	        				 Item_code = Item_code.split(":");
			        		 Item_code = Item_code[0];
			             }
	        			 if(itemPartNb == "" || itemPartNb.length == 0){
				    		   itemPartNb = "empty";
					    	   }
	        			 

			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 
			              
			                 url: '${pageContext.request.contextPath}/GetAllItemModelPopup',
			                 data: {
	 			                	"Item_Model" : request.term,
	 			                	Item_code : Item_code,
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.ListItemModelPopup);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			        }, minLength:0, maxShowItems: 20, scroll:true,
					select: function(event, ui) {
					itmModel.value = (ui.item ? ui.item[0]  : '');
						
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
//			}).data('ui-autocomplete')._renderItem = function(ul, item) {
		    	return $('<li class="each"></li>').data( "item.autocomplete", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold; font-size:12px;">' +
	               
	                    item[0] + '</span><br><span class="desc">' +
	                    item[1] + '</span></div>').appendTo(ul);
			};
			$('#itmModel').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});
//GetItemPartNumbersBOQ before
			$('#itmPartNo').autocomplete({
	    	    source: function(request, response, event, ui) {

	    	    	 var Item_code = $('#itmCode').val();
	    	    	 if(Item_code == ""){			    	    	 
		    	    	 Item_code = "empty";
		    	    	 }
        			 if(Item_code != "empty")
	        		 {
        				 Item_code = Item_code.split(":");
		        		 Item_code = Item_code[0];
		             }

        			  var itemModel =  document.getElementById('itmModel').value;
			    	   
			    	   if(itemModel == "" || itemModel.length == 0){
			    		   itemModel = "empty";
				    	   }
        			 
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllItemModelPopup',
		                 data: {
								"Item_Model" : request.term,
								Item_code : Item_code,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.ListItemModelPopup);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		        }, minLength:0, maxShowItems: 20, scroll:true,
				select: function(event, ui) {
					itmPartNo.value = (ui.item ? ui.item[0]  : '');						
					return false;
				}
			}).autocomplete("instance")._renderItem = function(ul, item) {
				return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold; font-size:12px;'>" +
                    item[0] + "</span></div>")
                .appendTo(ul);
		};
		$("#itmPartNo").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
		});

	//**********************  end autocomplite item ****************************
	//**********************   autocomplite supp ****************************
$("#itmCode").on('input', function(){		
		$("#itmModel").val('');
		 $("#itmPartNo").val('');
	
	});
	$("#prsuppid").on('input', function(){
		 $("#supplierName").val('');
	
	});
		$("#prsuppid").autocomplete({
		    
		    source: function(request, response) {

		         var supplierName=$("#supplierName").val();
		        
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllSupplier',
		                 data: {
								"supplierId" : $("#prsuppid").val(),
								"supplierName":supplierName,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.ListGetSupplier);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems:20, scroll:true,

					select: function(event, ui) {
						prsuppid.value = (ui.item ? ui.item[0]  : '');
						supplierName.value=ui.item[1];
						//prsuppaddress.value= ui.item[2];
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
			            return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold; font-size:12px;'>" +
		                    item[0] + "</span><br><span class='desc'>" +
		                    item[1] + "</span></div>")
		                .appendTo(ul);
		        	};
					$("#prsuppid").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});

$("#supplierName").autocomplete({
    
    source: function(request, response) {
	    console.log("sup name click");
	    var psupplierId= $("#prsuppid").val();
             $.ajax({
                 type: "GET",
                 contentType: "application/json; charset=utf-8",
                 url: '${pageContext.request.contextPath}/GetAllSupplierName',
                 data: {
						"supplierId" :psupplierId,
						"supplierName":$("#supplierName").val(),
				 },
                 dataType: "json",
                 success: function (data) {
                     if (data != null) {
                         response(data.ListSupplierName);


                         
                     }
                 },
                 error: function(result) {
                     alert("Error");
                 }
             });
         }, minLength:0, maxShowItems: 20, scroll:true,

			select: function(event, ui) {
				supplierName.value = (ui.item ? ui.item[0]  : '');
				//prsuppaddress.value = ui.item[1];
				


				$.ajax({
	                type: "GET",
	                contentType: "application/json; charset=utf-8",
	                url: '${pageContext.request.contextPath}/GetSuppID',
	                data: {
							SuppName : ui.item[0],
					 },
	                dataType: "json",
	                success: function (data) {
	                    if (data != null) {
		                console.log("Entered here")  ;
		                console.log("The list is "+data.ListSuppIds[0]) ;

		                SuppID = data.ListSuppIds[0];

		                if(data.ListSuppIds.length == 1){
		                	 console.log("/*/*Entered here");
		                	
		                	$("#prsuppid").val(SuppID);
		                	prsuppaddress.value = ui.item[1];

			                }

		                else{

		                	$("#prsuppid").val("");
		                	$("#prsuppaddress").val("");
		                	

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
                .append("<div class='acItem'><span class='name' style='font-weight:bold; font-size:12px;'>" +
                    item[0] + "</span></div>")
                .appendTo(ul);
        	};
			$("#supplierName").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});
					//**********************   end autocomplite supp ****************************
					//**********************   open popup button ****************************

$("#open-popup-btn").click(  function() {
	  if(popOpen){

		  $('#dialog').dialog("close");  
	  var styleObject = $('#open-popup-btn').prop('style'); 

	  styleObject.removeProperty('background-color');
	  $("#open-popup-btn").toggleClass("btn-light");
	  $("#open-popup-btn").toggleClass("btn-danger");
	  document.getElementById("open-popup-btn").blur();			 
 	  popOpen = false;
  }else{
	  $('#dialog').dialog("open");
	  $("#open-popup-btn").css('background', '#da6815');			  
	  $("#open-popup-btn").toggleClass("btn-light");
	  $("#open-popup-btn").toggleClass("btn-danger");
	  popOpen = true;

	  }
	
});

$(document).on('keydown', function(event) {
    if (event.key == "Escape") {
      
     // $("#dialog").focus();
    // if (e.keyCode == 27) { }
    if(popOpen){
         $("#dialog").dialog('close');

      var styleObject = $('#open-popup-btn').prop('style'); 
  	  styleObject.removeProperty('background-color');
  	 // $("#open-popup-btn").toggleClass("btn-light");
  	 // $("#open-popup-btn").toggleClass("btn-danger");
  	  $("#open-popup-btn").removeClass("btn-danger");
	  $("#open-popup-btn").addClass("btn-light");
  	  document.getElementById("open-popup-btn").blur();
  	  popOpen = false;
    }
    }
});	

/*jQuery('body').bind('clickoutside',function(e){
  if( jQuery('#dialog').dialog('isOpen')
   && !jQuery(e.target).is('.ui-dialog, a')
   && !jQuery(e.target).closest('.ui-dialog').length
  ){
   jQuery('#dialog').dialog('close');
  }
  
  $('#dialog').bind('clickoutside',function(){
	$('#dialog').dialog('close');
});
 });*/
 //******************************* clear popup section **************************************
$("#clearWare").click(  function() {
	 $("#clearWarediv").toggleClass("small-btn-clear-filter-click");
	console.log("clear");
	$("#wareid").val('');
	 $("#warename").val('');
	 $("#siteid").val('');
	 setTimeout(
	            function() {
	               console.log("remove style")
	               $("#clearWarediv").toggleClass("small-btn-clear-filter-click");
	               document.getElementById("clearWare").blur();
	            },
	            150);
	});
	
$("#clearNode").click(  function() {
	console.log("clear");
	  $("#clearNodediv").toggleClass("small-btn-clear-filter-click");
	$("#nodeid").val('');
	 $("#nodename").val('');
	 $("#nodeType").val('');
	 setTimeout(
	            function() {
	               console.log("remove style")
	               $("#clearNodediv").toggleClass("small-btn-clear-filter-click");
	               document.getElementById("clearNode").blur();
	            },
	            150);
	});
	
$("#clearItm").click(  function() {
	 $("#clearItmdiv").toggleClass("small-btn-clear-filter-click");
	console.log("clear");
	$("#itmCode").val('');
	 $("#itmModel").val('');
	 $("#itmPartNo").val('');
	 setTimeout(
	            function() {
	               console.log("remove style")
	               $("#clearItmdiv").toggleClass("small-btn-clear-filter-click");
	               document.getElementById("clearItm").blur();
	            },
	            150);
	});
$("#clearSupp").click(  function() {
	 $("#clearSuppdiv").toggleClass("small-btn-clear-filter-click");
	console.log("clear");
	 $("#prsuppid").val('');
		$("#supplierName").val('');
		 setTimeout(
		            function() {
		               console.log("remove style")
		               $("#clearSuppdiv").toggleClass("small-btn-clear-filter-click");
		               document.getElementById("clearSupp").blur();
		            },
		            150);
	});
// clear all button

$("#ClearAllBtn").click(  function() {
		
$("#ClearAllBtndiv").toggleClass("clear-all-btn-clicked");
 setTimeout( function() {
       console.log("remove style")
       $("#ClearAllBtndiv").toggleClass("clear-all-btn-clicked"); 
       document.getElementById("ClearAllBtn").blur();
    },
    150);

	//warehouse
	 $("#wareid").val('');
	 $("#warename").val('');
	 $("#siteid").val('');
	 // node
	 $("#nodeid").val('');
	 $("#nodename").val('');
	 $("#nodeType").val('');
	 // item
	 $("#itmCode").val('');
	 $("#itmModel").val('');
	 $("#itmPartNo").val('');
	 //supplier
	 $("#prsuppid").val('');
	 $("#supplierName").val('');
	 //check for clear button
	 if(arrayPopupBtnChecked.includes(suppBtnPopupCheck)){$("#popupSuppBtndiv").removeClass("top-btn-filter-pressed");}
	 if(arrayPopupBtnChecked.includes(NodeTypeBtnPopupCheck)){$("#nodeTypeBtndiv").removeClass("top-btn-filter-pressed");}
	 if(arrayPopupBtnChecked.includes(itemBtnPopupCheck)){$("#popupItmBtndiv").removeClass("top-btn-filter-pressed");}
	 if(arrayPopupBtnChecked.includes(areaBtnPopupCheck)){$("#popupAreaBtndiv").removeClass("top-btn-filter-pressed");}
	 if(arrayPopupBtnChecked.includes(siteCheckboxPopupCheck ) || arrayPopupBtnChecked.includes(nodeCheckboxPopupCheck ) || arrayPopupBtnChecked.includes(cellCheckboxPopupCheck )){
		 $("#popupCheckBtndiv").removeClass("btn-filter-some-check");
		 $("#popupCheckBtndiv").removeClass("top-btn-filter-pressed");
		 $("#checksite").prop('checked', false);
		 $("#checknode").prop('checked', false);
		 $("#checkcell").prop('checked', false);
		 }
	 //check for nav btn in popup
	 if(arrayPopupBtnNavigationChecked.includes(btnPopupTree1 )){
				  $("#popup-tree-1").removeClass("btn-danger");
				  $("#popup-tree-1").addClass("btn-light"); 
				  var styleObject = $('#popup-tree-1').prop('style'); 
				  styleObject.removeProperty('background-color');
				  document.getElementById("popup-tree-1").blur();	
				  arrayPopupBtnNavigationChecked.pop(btnPopupTree1);
				  }else if(arrayPopupBtnNavigationChecked.includes(btnPopupListView1)){
						$("#popup-listview-1").removeClass("btn-danger");
						  $("#popup-listview-1").addClass("btn-light"); 
						  var styleObject = $('#popup-listview-1').prop('style'); 
						  styleObject.removeProperty('background-color');
						  document.getElementById("popup-listview-1").blur();
						  arrayPopupBtnNavigationChecked.pop(btnPopupListView1);
						  }else  if(arrayPopupBtnNavigationChecked.includes(btnPopupFormView1)){
								 $("#popup-formview-1").removeClass("btn-danger");
								  $("#popup-formview-1").addClass("btn-light"); 
								  var styleObject = $('#popup-formview-1').prop('style'); 
								  styleObject.removeProperty('background-color');
								  document.getElementById("popup-formview-1").blur();	
								  arrayPopupBtnNavigationChecked.pop(btnPopupFormView1);
								  }else  if(arrayPopupBtnNavigationChecked.includes(btnPopupGis1)){
									  $("#popup-gis-1").removeClass("btn-danger");
									  $("#popup-gis-1").addClass("btn-light"); 
									  var styleObject = $('#popup-gis-1').prop('style'); 
									  styleObject.removeProperty('background-color');
									  document.getElementById("popup-gis-1").blur();	
									  arrayPopupBtnNavigationChecked.pop(btnPopupGis1);}
	 arrayPopupBtnChecked = [];
});
//******************************* end clear popup section **************************************
//******************************* set nav btn **************************************
$("#popup-gis-1").click(  function() {
if(arrayPopupBtnNavigationChecked.length == 0){
	console.log("set to click");
	$("#popup-gis-1").css('background', '#da6815');
	$("#popup-gis-1").removeClass("btn-light");
	$("#popup-gis-1").addClass("btn-danger");
	arrayPopupBtnNavigationChecked.push(btnPopupGis1);
}else{
	 if(arrayPopupBtnNavigationChecked.includes(btnPopupGis1)){
		  $("#popup-gis-1").removeClass("btn-danger");
		  $("#popup-gis-1").addClass("btn-light"); 
		  var styleObject = $('#popup-gis-1').prop('style'); 
		  styleObject.removeProperty('background-color');
		  document.getElementById("popup-gis-1").blur();	
		  arrayPopupBtnNavigationChecked.pop(btnPopupGis1);
			}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupTree1 )){
				  $("#popup-tree-1").removeClass("btn-danger");
				  $("#popup-tree-1").addClass("btn-light"); 
				  var styleObject = $('#popup-tree-1').prop('style'); 
				  styleObject.removeProperty('background-color');
				  document.getElementById("popup-tree-1").blur();	
				  arrayPopupBtnNavigationChecked.pop(btnPopupTree1);
				  $("#popup-gis-1").css('background', '#da6815');
				  $("#popup-gis-1").removeClass("btn-light");
				  $("#popup-gis-1").addClass("btn-danger");
				  arrayPopupBtnNavigationChecked.push(btnPopupGis1);
					}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupListView1)){
						$("#popup-listview-1").removeClass("btn-danger");
						  $("#popup-listview-1").addClass("btn-light"); 
						  var styleObject = $('#popup-listview-1').prop('style'); 
						  styleObject.removeProperty('background-color');
						  document.getElementById("popup-listview-1").blur();
						  arrayPopupBtnNavigationChecked.pop(btnPopupListView1);
						  $("#popup-gis-1").css('background', '#da6815');
						  $("#popup-gis-1").removeClass("btn-light");
						  $("#popup-gis-1").addClass("btn-danger");
						  arrayPopupBtnNavigationChecked.push(btnPopupGis1);
							}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupFormView1)){
								 $("#popup-formview-1").removeClass("btn-danger");
								  $("#popup-formview-1").addClass("btn-light"); 
								  var styleObject = $('#popup-formview-1').prop('style'); 
								  styleObject.removeProperty('background-color');
								  document.getElementById("popup-formview-1").blur();	
								  
								  arrayPopupBtnNavigationChecked.pop(btnPopupFormView1);
								  $("#popup-gis-1").css('background', '#da6815');
								  $("#popup-gis-1").removeClass("btn-light");
								  $("#popup-gis-1").addClass("btn-danger");
								  arrayPopupBtnNavigationChecked.push(btnPopupGis1);
									}
}
	
});


//second btn for tree
$("#popup-tree-1").click(  function() {
if(arrayPopupBtnNavigationChecked.length == 0){
	$("#popup-tree-1").css('background', '#da6815');
	$("#popup-tree-1").removeClass("btn-light");
	$("#popup-tree-1").addClass("btn-danger");
	arrayPopupBtnNavigationChecked.push(btnPopupTree1);
}else{
	 if(arrayPopupBtnNavigationChecked.includes(btnPopupTree1)){
		  $("#popup-tree-1").removeClass("btn-danger");
		  $("#popup-tree-1").addClass("btn-light"); 
		  var styleObject = $('#popup-tree-1').prop('style'); 
		  styleObject.removeProperty('background-color');
		  document.getElementById("popup-tree-1").blur();	
		  arrayPopupBtnNavigationChecked.pop(btnPopupTree1);
			}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupGis1)){
				  $("#popup-gis-1").removeClass("btn-danger");
				  $("#popup-gis-1").addClass("btn-light"); 
				  var styleObject = $('#popup-gis-1').prop('style'); 
				  styleObject.removeProperty('background-color');
				  document.getElementById("popup-gis-1").blur();	
				  arrayPopupBtnNavigationChecked.pop(btnPopupGis1);
				  $("#popup-tree-1").css('background', '#da6815');
				  $("#popup-tree-1").removeClass("btn-light");
				  $("#popup-tree-1").addClass("btn-danger");
					arrayPopupBtnNavigationChecked.push(btnPopupTree1);
					}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupListView1)){
						 $("#popup-listview-1").removeClass("btn-danger");
						  $("#popup-listview-1").addClass("btn-light"); 
						  var styleObject = $('#popup-listview-1').prop('style'); 
						  styleObject.removeProperty('background-color');
						  document.getElementById("popup-listview-1").blur();
						  arrayPopupBtnNavigationChecked.pop(btnPopupListView1);
						  
						  $("#popup-tree-1").css('background', '#da6815');
							$("#popup-tree-1").removeClass("btn-light");
							$("#popup-tree-1").addClass("btn-danger");
							arrayPopupBtnNavigationChecked.push(btnPopupTree1);
							}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupFormView1)){
								 $("#popup-formview-1").removeClass("btn-danger");
								  $("#popup-formview-1").addClass("btn-light"); 
								  var styleObject = $('#popup-formview-1').prop('style'); 
								  styleObject.removeProperty('background-color');
								  document.getElementById("popup-formview-1").blur();
								 	
								  arrayPopupBtnNavigationChecked.pop(btnPopupFormView1);
								  
								    $("#popup-tree-1").css('background', '#da6815');
									$("#popup-tree-1").removeClass("btn-light");
									$("#popup-tree-1").addClass("btn-danger");
									arrayPopupBtnNavigationChecked.push(btnPopupTree1);
									}
}
	
});

//3 btn for form view
$("#popup-formview-1").click(  function() {
if(arrayPopupBtnNavigationChecked.length == 0){
	$("#popup-formview-1").css('background', '#da6815');
	$("#popup-formview-1").removeClass("btn-light");
	$("#popup-formview-1").addClass("btn-danger");
	arrayPopupBtnNavigationChecked.push(btnPopupFormView1);
}else{
	 if(arrayPopupBtnNavigationChecked.includes(btnPopupFormView1)){
		  $("#popup-formview-1").removeClass("btn-danger");
		  $("#popup-formview-1").addClass("btn-light"); 
		  var styleObject = $('#popup-formview-1').prop('style'); 
		  styleObject.removeProperty('background-color');
		  document.getElementById("popup-formview-1").blur();	
		  arrayPopupBtnNavigationChecked.pop(btnPopupFormView1 );
			}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupGis1)){
				  $("#popup-gis-1").removeClass("btn-danger");
				  $("#popup-gis-1").addClass("btn-light"); 
				  var styleObject = $('#popup-gis-1').prop('style'); 
				  styleObject.removeProperty('background-color');
				  document.getElementById("popup-gis-1").blur();	
				  arrayPopupBtnNavigationChecked.pop(btnPopupGis1);
				  
				  $("#popup-formview-1").css('background', '#da6815');
					$("#popup-formview-1").removeClass("btn-light");
					$("#popup-formview-1").addClass("btn-danger");
					arrayPopupBtnNavigationChecked.push(btnPopupFormView1 );
					}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupTree1)){
						  $("#popup-tree-1").removeClass("btn-danger");
						  $("#popup-tree-1").addClass("btn-light"); 
						  var styleObject = $('#popup-tree-1').prop('style'); 
						  styleObject.removeProperty('background-color');
						  document.getElementById("popup-tree-1").blur();	
						  arrayPopupBtnNavigationChecked.pop(btnPopupTree1);
						  
						  $("#popup-formview-1").css('background', '#da6815');
							$("#popup-formview-1").removeClass("btn-light");
							$("#popup-formview-1").addClass("btn-danger");
							arrayPopupBtnNavigationChecked.push(btnPopupFormView1 );
							}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupListView1)){
								  $("#popup-listview-1").removeClass("btn-danger");
								  $("#popup-listview-1").addClass("btn-light"); 
								  var styleObject = $('#popup-listview-1').prop('style'); 
								  styleObject.removeProperty('background-color');
								  document.getElementById("popup-listview-1").blur();	
								  arrayPopupBtnNavigationChecked.pop(btnPopupListView1);
								  
								  $("#popup-formview-1").css('background', '#da6815');
									$("#popup-formview-1").removeClass("btn-light");
									$("#popup-formview-1").addClass("btn-danger");
									arrayPopupBtnNavigationChecked.push(btnPopupFormView1);
									}
}
	
});


//4 btn for form view
$("#popup-listview-1").click(  function() {
if(arrayPopupBtnNavigationChecked.length == 0){
	$("#popup-listview-1").css('background', '#da6815');
	$("#popup-listview-1").removeClass("btn-light");
	$("#popup-listview-1").addClass("btn-danger");
	arrayPopupBtnNavigationChecked.push(btnPopupListView1);
}else{
	 if(arrayPopupBtnNavigationChecked.includes(btnPopupListView1)){
		  $("#popup-listview-1").removeClass("btn-danger");
		  $("#popup-listview-1").addClass("btn-light"); 
		  var styleObject = $('#popup-listview-1').prop('style'); 
		  styleObject.removeProperty('background-color');
		  document.getElementById("popup-listview-1").blur();	
		  arrayPopupBtnNavigationChecked.pop(btnPopupListView1 );
			}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupGis1)){
				  $("#popup-gis-1").removeClass("btn-danger");
				  $("#popup-gis-1").addClass("btn-light"); 
				  var styleObject = $('#popup-gis-1').prop('style'); 
				  styleObject.removeProperty('background-color');
				  document.getElementById("popup-gis-1").blur();	
				  arrayPopupBtnNavigationChecked.pop(btnPopupGis1);
				  
				  $("#popup-listview-1").css('background', '#da6815');
					$("#popup-listview-1").removeClass("btn-light");
					$("#popup-listview-1").addClass("btn-danger");
					arrayPopupBtnNavigationChecked.push(btnPopupListView1);
					}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupTree1)){
						  $("#popup-tree-1").removeClass("btn-danger");
						  $("#popup-tree-1").addClass("btn-light"); 
						  var styleObject = $('#popup-tree-1').prop('style'); 
						  styleObject.removeProperty('background-color');
						  document.getElementById("popup-tree-1").blur();	
						  arrayPopupBtnNavigationChecked.pop(btnPopupTree1);
						  
						  $("#popup-listview-1").css('background', '#da6815');
							$("#popup-listview-1").removeClass("btn-light");
							$("#popup-listview-1").addClass("btn-danger");
							arrayPopupBtnNavigationChecked.push(btnPopupListView1);
							}else  if(arrayPopupBtnNavigationChecked.includes(btnPopupFormView1)){
								  $("#popup-formview-1").removeClass("btn-danger");
								  $("#popup-formview-1").addClass("btn-light"); 
								  var styleObject = $('#popup-formview-1').prop('style'); 
								  styleObject.removeProperty('background-color');
								  document.getElementById("popup-formview-1").blur();	
								  arrayPopupBtnNavigationChecked.pop(btnPopupFormView1);
								  
								  $("#popup-listview-1").css('background', '#da6815');
									$("#popup-listview-1").removeClass("btn-light");
									$("#popup-listview-1").addClass("btn-danger");
									arrayPopupBtnNavigationChecked.push(btnPopupListView1);
									}
}
	
});
//******************************* end set nav btn **************************************

});

	
</script>
</body>
</html>