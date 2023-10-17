<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="">
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
	
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>  
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
	
	<!-- ALM GRID Scripts -->
    <link href="${pageContext.request.contextPath}/resources/cssgrid/bootstrap-datepicker.min.css" rel='stylesheet' type='text/css'>
	<script src="${pageContext.request.contextPath}/resources/scriptsgrid/bootstrap-datepicker.min.js" type='text/javascript'></script>
	<script type="text/javascript"src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
	<script type="text/javascript"src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
	<script type="text/javascript"src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
	
	<!-- Google Maps Script -->
   	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	
	
   <script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
   <script src="${pageContext.request.contextPath}/resources/js/jspdf.plugin.autotable.min.js"></script>
   <script src="${pageContext.request.contextPath}/resources/js/tableHTMLExport.js"></script>

   <script src="${pageContext.request.contextPath}/resources/js/AssetLifeCycle/TransactionModel.js"></script> 
   <script src="${pageContext.request.contextPath}/resources/js/AssetLifeCycle/FilterOptionModel.js"></script> 
   
   <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/AssetLifeCycle/AssetLifeCycleGoogleMaps.js"></script>
   
   <style>
				.ui-autocomplete {
	            	max-height: 270px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 0px;
					z-index:9999;
					
	        		}
	        		
	        		#clearButton{
					background-color:white;
					color:orange;
					
					}
					
					#clearButton:hover{
					background-color:orange;
					color:white;
					
					
					}

				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				  margin-right: 10px;
				  margin-left: 10px;  
				}
				
				div {
				display:block;
				}
				
				#labelLedger, #labelBalace, #labelNetwork ,#labelTransaction{
				padding-top : 7px;
				margin: 0;
				float: none;
	            }
				
				#voucherType, #UOM{
				color: gray;
				}
				
			   .modal fade, .modal-content{
		
                 width: 900px !important;
				}
				
				#mapContainer {
                  height: 700px;
                }
                
                .legendContainer{
                 height: 800px;
                 position: relative;
                }
                .legendHeader {
overflow: hidden;
background-color: #08526d;
padding: 10px 0px;
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
.legendContainer{
height: 800px;
position: relative;
}
.dotYellow {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#FFDC00;

}

.dotBlue {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#0080ff;

}
.dotRed {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:red;

}
.dotPink {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#FF00FF;

}
.dotPurple {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#8A2BE2;

}
.green {
background:green;
}

.redDark {
background:#4D0207;
}
.blue {
background:#0080ff;
}

.yellow {
background:#FFDC00;
}


.red{
background:red; }

.pink{
background:#FF00FF;
}

.purple{
background:#8A2BE2;
}
                
                
                 
                
                  input[type=radio] { 
                         vertical-align: middle; 
                          position: relative; 
                           bottom: 1px; 
                           } 
          
               
	        	.btn-icon {
                    background-color: DodgerBlue; /* Blue background */
                    border: none; /* Remove borders */
                    color: white; /* White text */
                    padding: 6px 9px; /* Some padding */
                    font-size: 12px; /* Set a font size */
                    cursor: pointer; /* Mouse pointer on hover */
                 }

                /* Darker background on mouse-over */
                 .btn-icon:hover {
                  background-color: RoyalBlue;
                  }	 
                  
                  #collapseform::after {
                   
                    content: "\f107";
                     top: -2px;
                     right: 0px;
                     font-family: "FontAwesome"
                  }

                 #collapseform[aria-expanded="true"]::after {
                    content: "\f106";
                    
                 }
                 
                 #col1,#col2,#col3,#col3{
                 resize: block !important;
                 }
                 
                 
                 /* Extra small devices (phones, 600px and down) */
@media only screen and (max-width: 600px) {
  .modal-content, .modal-dialog {
  max-height: 100%;
       max-width: 100%;
  
 }
}
/* Small devices (portrait tablets and large phones, 600px and up) */
@media only screen and (max-width: 600px) {
.modal-content, .modal-dialog {
max-height: 100%;
        max-width: 100%;

}
}

/* Medium devices (landscape tablets, 768px and up) */
@media only screen and (max-width: 768px) {
 .modal-content, .modal-dialog {
  max-height: 100%;
        max-width: 100%;
  
}
} 

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (max-width: 992px) {
  .modal-content, .modal-dialog {
  
  max-height: 100%;
        max-width: 100%;
  
 }
} 

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
 .modal-content, .modal-dialog {
 
 max-height: 100%;
 max-width: 100%;
}
}
                
 	</style>
            
</head>
<body>
  
<%--   <c:set var = "page" value = "inventory"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="inventory" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
    
   
  

 <!--  end of general head page -->
 
 <div class="container-fluid" >     
	  
	   
	   <p></p>
		
		
	 
		<div class="row second">
			
			<div class="col-md-2" id="col1">
                   <div class="form-group">
						
							<span class="input-group-text" style="color:green; font-size:14px; ">Asset Life Cycle </span>							
						

					</div>
			
             </div>
   <!--     <div class="col-sm-4">
          <div class="row second">  -->  
     	  <div class="col-md-3" id="col2">
			  <div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="startdate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"  style="height:38px;" />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
                </div>
	     </div>
	
	     <div class="col-md-3"  id="col3">
			<div class="form-group">
			  <div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
				<span class="input-group-text">End Date</span>
					<input type="text" id="enddate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"  style="height:38px;" />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
					 </div>
			</div>
          </div>
	    </div>
			
			<div class="col-md-4" id="col3" style="text-align:right;">
		 		<div class="btn-group pull-right"  style="padding: 0px !important;">
		 			<div class="glyph" style="padding-top:0px; padding-right: 10px;">
                           <div class="form-group">
			               <div class="input-group-prepend" data-target-input="nearest">
					       <div class="input-group-text">	
      				       <button type="button" class="btn" name="fields" id="FieldsOption" onclick='openFilterOptionModel(this)'><i class="fa fa-filter" style="font-size:20px"></i></button>
				         </div>
                        </div>
	                </div>
	                </div>
		 			<div class="glyph" style="padding-top:0px;">
			 			 <div class="dropdown"  Style="margin-right:10px; height:30px;">
	                        <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Menu	</button>
	
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
		       
		 			<div class="glyph" Style="white-space: nowrap;overflow: hidden;">
			 			
		                  <button type="button" id="generate" class="btn btn-primary BtnActive" > Generate Report </button> 
			
			
			</div>
			</div>
		</div>
	
	</div>
</div>	
 	
<div class="container-fluid" >     
	  

<!-- Modal Filter Options -->
            <div class="modal fade" id="myModalFieldsOption" tabindex="-1" role="dialog"
                aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered" role="document" id="model" >
                    <div class="modal-content" Style="width: 1200px !important; height: 100% ;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title"  style="font-weight:bold; color: #3C1596;position:relative;top:4px;"  id="myModalLabel1">  Filter Options </h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                         <ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
					  		<li class="nav-item">
					   			<a class="nav-link active " id="fltrOpt-tab" style="color: gold;" data-toggle="tab" href="#fltrOpt" role="tab" aria-controls="fltrOpt" aria-selected="true">FILTER OPTIONS</a>
					  		</li>
					  		<li class="nav-item">
					   			 <a class="nav-link " id="BlcFltrOpt-tab" style="color: gold;" data-toggle="tab" href="#BlcFltrOpt" role="tab" aria-controls="BlcFltrOpt" aria-selected="false">BALANCE FILTER OPTIONS</a>
					  		</li>
						</ul>
						<div class="tab-content">
					  <div class="tab-pane active" id="fltrOpt" role="tabpanel" aria-labelledby="fltrOpt-tab">
   						
                            <div class="row">
  

   <div class="col-md-3">
		   <div class="form-group">
		          <span>Warehouse</span>
				  <input type="text"  id="warcode" value="${warehouse}" class="form-control text-input"   />	
		   </div>
  </div>	

	
  <div class="col-md-3">
		   <div class="form-group">
		     <span>Site</span>
		     <input type="text"  id="SiteId" value="${SiteId}" class="form-control text-input"   />
		  </div>
  </div>
  
 
       
  <div class="col-md-3">
		   <div class="form-group">
				 <span>Site Name</span>
					<input type="text"  id="siteName" value="${siteName}" class="form-control text-input"   />
		 </div>
  </div>	

	
  <div class="col-md-3">
		   <div class="form-group">
		     <span>Supplier</span>
			 <input type="text"  id="supcode" value="${supplier}" class="form-control text-input"   />
		 </div>
  </div>
  
	<div class="col-md-3">
		   <div class="form-group">
		     <span>Item Code</span>
			 <input type="text"  id="itemCode"  value="${item}" class="form-control text-input"   />
		  </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
		     <span>Item Name</span>
			 <input type="text"  id="itemName"  value="${item}" class="form-control text-input"   />
		  </div>
  </div>	
 

  	
  
  
  
  <div class="col-md-3">
		   <div class="form-group">
		     <span>Item Model</span>
			 <input type="text"  id="itemModel" name ="itmModel" value="${itemModel}" class="form-control text-input"   />
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
		     <span>Item Part Number</span>
			 <input type="text"  id="itemPartNo" name ="itemPartNo" value="${itemPartNo}" class="form-control text-input"   />
		  </div>
  </div>
  
  <div class="col-md-3">
  <span>Item Category</span>
		   <div class="input-group-prepend">
		     
			 <input type="text"  id="itemCategory" value="${itemCategory}" class="form-control text-input" readonly   />
		  </div>
  </div>
  <!-- 
  <div class="col-md-3">
		   <div class="form-group">
				<span>Serial #</span>
				<input type="text"  id="sncode" value="${serialNB}" class="form-control text-input"   />
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group"  >
				    <span>Voucher Type</span>
					<select id="voucherType" class="form-control text-input" style="height: 36px;">
					   <option value="null" selected></option>
			           <option value="PR" >Purchase Request</option>
			           <option value="PO">Purchase Order</option>
			           <option value="GR">Goods Received</option>
			           <option value="AR">Asset Registry</option>
                       <option value="FAR">Fixed Asset Registry</option>
		            </select>
				
		   </div>
  </div>
  
   <div class="col-md-3">
		   <div class="form-group">
				    <span>Voucher #</span>
					<input type="text" name="voucherNB"  id="vncode" value="${voucherNB}" class="form-control text-input"   />
		 </div>
  </div>
  
  

  <div class="col-md-3">
		   <div class="form-group">
		     <span>Domain Type</span>
			 <input type="text"  id="domainType" value="${domainType}" class="form-control text-input"   />
		 </div>
  </div>
  
  
  <div class="col-md-3">
		   <div class="form-group">
		     <span>Project</span>
			 <input type="text" id="prcode" value="${project}" class="form-control text-input"   />
		 </div>
  </div>
  
  <div class="col-md-3">
  
		   <div class="form-group">
                     <span>UOM</span>    
    	             <select id="UOM" class="form-control text-input" style="height: 36px;">
    	               <option value="none" selected></option>
					   <option value="nos">Nos</option>
			           <option value="units">Units</option>
			           <option value="km">km </option>
			           <option value="m">m</option>
			           <option value="kg">kg</option>
                       <option value="g">g</option>
		            </select>
				
		 </div>
  </div>
   -->	
  
  </div>
  <p></p>
  <button type="button"  class="btn btn-primary" style="color:white;" onclick="resetToDefault()">Reset</button>
  <button type="button" id='SubmitFieldsOption'  class="btn btn-primary" > Submit </button>
     
  </div>
      <div class="tab-pane" id="BlcFltrOpt" role="tabpanel" aria-labelledby="BlcFltrOpt-tab">
  
  <table id="alcFilters" style="width:70%;height:70%;margin-top:80px;">  
  		<tr>
		    <th style="position: relative;top: 15px;left:10px;"></th>
		    <th style="position: relative;top: 15px;left:10px;"></th>
  		</tr>
  
 	 <tr>
     <td style="position: relative;top:-8px;left:50px;"><input style="position: relative;top: 11px;" type="checkbox" name="sitesBalanceQty" id="sitesBalanceQty" /></td>
     <td style="position: relative;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Sites Balance Quantity </label></td>
     <td style="position: relative;top:-8px;left:160px;"><input style="position: relative;top: 11px;" type="checkbox" name="sitesBalanceVal" id="sitesBalanceVal" checked/></td>
     <td style="position: relative;left:170px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Sites Balance Value</label></td>
   </tr>
   
  
   
  </table>
  
  </div>                        
                    </div>
             <div class="modal-footer">
                            </div>
                    </div>
                </div>
            </div>
		
		    </div>
 
 
</div>       
 <!-- end of filter option Model -->



 <div class="container-fluid"> 
 
 
  
  <div class = "row">
  
        <div class="col-md-3">
		   <div class="form-group">
		    <div class="input-group-prepend">
		     <span style="width:50px;" class="input-group-text">Site</span>
		     <input type="text"  id="site" value="${site}" class="form-control text-input" style="width:100%;height: 38px;" />
		   </div>
		  </div>
        </div>
       
       <div class="col-md-3">
		   <div class="form-group">
		    <div class="input-group-prepend">
		     <span style="width:50px;" class="input-group-text">Item</span>
			 <input type="text"  id="icode" name ="itmCode" value="${item}" class="form-control text-input"  style="width:100%;height: 38px;" />
		   </div> 
		  </div>
       </div>
       
       <div class="col-md-3">
		   <div class="form-group">
		    <div class="input-group-prepend">
		     <span style="width:90px;" class="input-group-text">Supplier</span>
			 <input type="text"  id="supplier" name ="itmCode" value="${supplier}" class="form-control text-input"  style="width:100%;height: 38px;" />
		   </div> 
		  </div>
       </div>
       
	     <div class="col-md-3" style="float: right; padding-top:-100px;" >
			<div class="btn-group pull-right"  >
		 			<button type="button"  id ="clearButton"class="btn btn-light" style=" margin-top:-4px;"  >Clear</button>
		 		</div>
		 	</div>
            
                            
            
      
       
</div>

  <div class="wrapper" style="margin-top:-10px;">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
     <!-- tables -->	
    <!-- start of grid -->
   <div class="panel panel-default" style="margin-bottom:3px;" >
     <div class="panel-heading " role="tab" id="headingOne">
      <h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          Grid Table
         </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body" >
          <!-- /.card-header -->  
      
    
    
      <div class="card-body" >
      <div id="generateTables"> 
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
                                <!-- Ledger Table -->
                                <form id="checktableLedger">
								<div id= "gridTableLedger" class="table-responsive almgrid-table-div">
									<table id="Ltable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
											
												<th>Item
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<!-- <th>Item Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>-->

												<th>Item Model
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Warehouse
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Voucher Type
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Voucher#
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>UOM
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
											
												<th>Qty
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Incoming Rate
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Outgoing Rate
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Accumulated Depreciation
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Net Price
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Valuation Rate
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Balance Value
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Net Balance Value
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Balance Qty
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
												
											</tr>
										</thead>
										<tbody >
						         
									
						                </tbody>

									</table>
								</div>
                              </form>
                              
                              
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

<div id="divLed" Style=" left: 0; bottom: 0;">	
<div class="row">
	
	<div class="col-md-3">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Balance QTY</span>
				<input type="text" id="tBalanceQtyLed" value="${tBalanceQtyLed}" readonly class="form-control text-input" />
				</div>
			</div>							
		</div>
	
	<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Balance Value</span>
						<input type="text" id="tBalanceValLed" value="${tBalanceValLed}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 
	<!--   <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Accumulated Depreciation</span>
						<input type="text" id="tAccumulatedDeprLed" value="${tAccumulatedDeprLed}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>	
	 
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Net Balance Value</span>
						<input type="text" id="tNetBalanceValLed" value="${tNetBalanceValLed}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>-->
	 
	 
		
		
	</div>	
				
</div>	
</div>
</div>	

<!-- end of grid -->
<!-- start of map -->
    <div class="panel panel-default" style="margin-bottom:3px;">
    <div class="panel-heading" role="tab" id="headingTwo">
      <h4 class="panel-title">
       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
         GIS
       </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body">
      <div class="legendContainer">
      <div class="card-body">
        
        <div class="box stack-top" id="legendDiv" style="overflow-y:scroll;position: relative;top:170px;width: 300px; float:left; height:320px;  background:white; margin:37px;display: none">
      		<div class="legendHeader"  id="legendHeader">
      		 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6>
 
			 <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAll()"  id="selectUnselect" >Unselect All</button>
			  </div>
			
<div id="tableDiv">
  <table id="balanceValTable">
  
   <caption style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-210px;left:10px;">Sites Balance Value</caption>
  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
  </tr>
  
  <tr>
     
    <td style="position: relative;top:23px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="firstValRange" onclick="ShowHideMarkers('#FFDC00');" value="#FFDC00"/></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td>    
    <td style="position: relative;top: 32px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex;">0 $ - 4999 $</label></td>    
  </tr>
   
   <tr>
   
     <td style="position: relative;top:23px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="secondValRange" onclick="ShowHideMarkers('#0080FF');" value="#0080FF"/></td>
     <td style="position: relative;top:30px;left:60px;"><div class="dot blue"></div></td>
     <td style="position: relative;top: 32px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex; " >5000 $ - 19 999 $</label></td>
  </tr>
  
    <tr>
     
     <td style="position: relative;top:23px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="thirdValRange"  onclick="ShowHideMarkers('red');" value="red"/></td>
     <td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td>
     <td style="position: relative;top: 32px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >20 000 $ - 29 999$</label></td>    
  </tr>
   
   <tr>
     
     <td style="position: relative;top:23px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="fourthValRange"  onclick="ShowHideMarkers('#FF00FF');" value="#FF00FF"/></td>
     <td style="position: relative;top:30px;left:60px;"><div class="dot pink"></div></td>
     <td style="position: relative;top: 32px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >30 000 $ - 49 999 $ </label></td>
    
    
  </tr>
  
   <tr>
     
     <td style="position: relative;top:23px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="fifthValRange"  onclick="ShowHideMarkers('#8A2BE2');" value="#8A2BE2"/></td>
     <td style="position: relative;top:30px;left:60px;"><div class="dot purple"></div></td>
     <td style="position: relative;top:32px;left:70px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >50 000 $ & Above </label></td>
    
  </tr>
 
  </table>
  
</div>
			   </div>
			   </div>
	<div class="card-body">
        <div class="box" id="mapContainer">
         <canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
        </div>
  </div>
</div>	
</div> 
</div></div>
<!-- end of map -->
<!-- start of charts -->
<div class="panel panel-default" style="margin-bottom:3px;">
    <div class="panel-heading" role="tab" id="headingThree">
      <h4 class="panel-title">
        <a role="button" id="chartsPanel" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseThree">
         Charts
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingThree">
      <div class="panel-body" style="background:#f1f4f7 !important">
         <div class="card-body" id="chartscardBody" >
          <div id ="noCharts" style="text-align: center;vertical-align: middle;margin-top:15px;">
         
                 <h4 style="color: red;"> No Charts to Show </h4>
            </div>
			
         </div>
</div>
</div>	
</div> 
<!-- end of charts -->
</div>
</div>	
</div> 
 <p></p>	



</body>


    

 <script type='text/javascript'>

 $('#clearButton'). click(function(){  
	document.querySelector("#site").value = ""
	document.querySelector("#icode").value = ""
		
	document.querySelector("#warcode").value = ""
	document.querySelector("#SiteId").value = ""
	document.querySelector("#siteName").value = ""

	document.querySelector("#itemCode").value = ""
	document.querySelector("#itemName").value = ""
	document.querySelector("#itemCategory").value = ""
	document.querySelector("#itemModel").value = ""
	document.querySelector("#itemPartNo").value = ""
});

function resetToDefault(){
	document.querySelector("#site").value = ""
	document.querySelector("#icode").value = ""
		
	document.querySelector("#warcode").value = ""
	document.querySelector("#SiteId").value = ""
	document.querySelector("#siteName").value = ""
	document.querySelector("#supcode").value = ""

	document.querySelector("#itemCode").value = ""
	document.querySelector("#itemName").value = ""
	document.querySelector("#itemCategory").value = ""
	document.querySelector("#itemModel").value = ""
	document.querySelector("#itemPartNo").value = ""

	//document.querySelector("#sncode").value = ""
	//document.querySelector("#voucherType").value = ""
	//document.querySelector("#vncode").value = ""

	//document.querySelector("#nodeId").value = ""
	//document.querySelector("#nodeName").value = ""
	//document.querySelector("#nodeType").value = ""
	//document.querySelector("#cellId").value = ""
	//document.querySelector("#cellName").value = ""
	//document.querySelector("#domainType").value = ""
	//document.querySelector("#prcode").value = ""
	//document.querySelector("#UOM").value = ""
}

 var divTablee;		
 function initMap() {
	 divTablee= document.getElementById("tableDiv").innerHTML;

		// Define the Center
		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);

		var map = new google.maps.Map(document.getElementById("mapContainer"), {
		    center:Nairobi ,
		    zoom:6
		    
		  });

		  document.getElementById("mapContainer").innerHTML ="";

			var map = new google.maps.Map(document.getElementById("mapContainer"), {

				 mapTypeControl: false,
				 center:Nairobi ,
				 zoom: 6,
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

			$("input[name='legendCheckbox']").prop('checked',false);
			
			$("#firstValRange").attr('disabled', true);
		    $("#secondValRange").attr('disabled', true);
		    $("#thirdValRange").attr('disabled', true);
		    $("#fourthValRange").attr('disabled', true);
		 	$("#fifthValRange").attr('disabled', true);
		 	var element = document.getElementById("selectUnselect");
		 	if (element.innerHTML == "Unselect All"){
			 	element.innerHTML = " ";
			 }
			  
			//Add a button to show/hide under zoom control on map
			const centerControlDiv = document.createElement("div");
		    LegendControl(centerControlDiv, map);
		    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

		    const DefaultZoomDiv = document.createElement("div");
		    DefaultZoomControl(DefaultZoomDiv, map);
		    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

			$("#legendDiv").toggle();
}
 
   // set start date defualt value to month before 
        var today = new Date();
        console.log("today.getMonth() is "+ today.getMonth() );
        var newMonth = today.getMonth();

        //console.log("newMonth is"+ newMonth );
        if(newMonth <= 0){
            newMonth += 12;
            console.log("newMonth in if is"+ newMonth );
            var date = (newMonth)+'/'+today.getDate()+'/'+( today.getFullYear()-1);
        }
        else{
        	var date = (today.getMonth())+'/'+today.getDate()+'/'+ today.getFullYear();
        }
        var time = '00' + ":" + '00' ;
        //var time = today.getHours() + ":" + today.getMinutes() ;
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
   
 
 
 
 
   // var ArrayList = ${ AssetlifeCycleArray };
		
		
 
 $(document).ready(function() {

	 
		var type = '${Type}';
     	//console.log("***888 "+type);
	 
     var  Checkvalue = "";
	 $("#voucherType").click(function(){
		 vncode.value = null;
	 });
	 
	 
	// var sel = document.getElementById("voucherType");
	// var result = sel.options[sel.selectedIndex].value;
	// var option = $("#voucherType").children("option:selected"). val();


	 var ALCArrayGlobal = ${ALCArrayList};
	 var almgrid = new AlmgridTable({
         tableId: "Ltable",
         dataArray: ALCArrayGlobal,
         selectCheckbox: false,
         drawTableGrid :  function (tableId, dataArray) {

		        var tableBody = document.querySelector("#" + tableId + " tbody");
		        var columnLinkNb = this.columnLinkNb;
		        
		        $(tableBody).empty();

		        if (dataArray.length > 0) {
					 
		            var ArrayKeys = Object.keys(dataArray[0]);
		        }
		  
   	       },
          }); 
     
			
		

/////////////Start autocpmplete for site ID
				$("#site").autocomplete({
					showHeader: true,
			        source: function(request, response) {
				             $.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url: '${pageContext.request.contextPath}/GetAllSiteIds_ALC',
				                 data: {
				                	 "requestValue": request.term
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {
				                         response(data.ListSites);
				                       
				                     }
				                 },
				                 error: function(result) {
				                     console.log("Error");
				                 }
				             });
				         }, minLength:0, maxShowItems: 40, scroll:true,		
			               
			        
					select: function(event, ui) {
						
						site.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
						warcode.value = (ui.item ? ui.item[2]: '');
						siteName.value = (ui.item ? ui.item[1]  : '');
						document.getElementById("SiteId").value = (ui.item ? ui.item[0]  : '');
						
							return false;
								}
					    }).autocomplete("instance")._renderItem = function(ul, item) {
				            return $("<li class='each'>")
			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                    item[0] + "</span><br><span class='desc'>" +
	                    item[1] +  "</span></div>") .appendTo(ul);
			        	};
						$("#site").focus(function(){
			   	        	if (this.value == ""){
			   	            	$(this).autocomplete("search");
			   	        	}						
						});

	/////////////end autocpmplete for site ID
	
	
	  ////////auto complete for Itemcode   
      			
			
      $("#icode").autocomplete({
    	    
    	    source: function(request, response) {
    	             $.ajax({
    	                 type: "GET",
    	                 contentType: "application/json; charset=utf-8",
    	                 url: '${pageContext.request.contextPath}/getItemCode',
				                 data: {
				                	 requestValue : request.term
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {
				                         response(data.ListItemDetails);
				                     }
				                 },
				                 error: function(result) {
				                     console.log("Error");
				                 }
				             });
				         }, minLength:0, maxShowItems: 4, scroll:true,		
			               
				     	select: function(event, ui) {
				     		//icode.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
				     		this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');  // 0 is itemCode,  1 is itemName, 2 is model, 3 partNo , 4 Barcode
				     		document.getElementById("itemCode").value = (ui.item ? ui.item[0] : '');
				     		document.getElementById("itemName").value = (ui.item ? ui.item[1] : '');
				     		document.getElementById("itemModel").value = ui.item[2];
				     		document.getElementById("itemPartNo").value= ui.item[3];
				     		//sncode.value = "";
				     		$("#itemCategory").val(ui.item[5]+":"+ui.item[6]);

				     		
								return false;
							}
						}).autocomplete("instance")._renderItem = function(ul, item) {
//					}).data('ui-autocomplete')._renderItem = function(ul, item) {
			    	  var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
			          
						item[0] + "</span><br><span class='desc'>" +
						item[1] + "</span><span class='desc'>";
						if(item[2] != '-')
							appendString += ","+item[2] + "</span><span class='desc'>";
						if(item[3] != '-')
							appendString += ","+item[3] + "</span><span class='desc'>";
						if(item[4] != '-')
							appendString += ","+item[4];
			
						appendString += "</span></div>";
						return $("<li class='each'>").append(appendString).appendTo(ul);
					};
					$("#icode").focus(function(){
						if (this.value == ""){
							$(this).autocomplete("search");
			   	        }
					});
								
				//// ended auto complete for itemcode
				
				
				/// START OF Supplier auto complete
				$("#supplier").autocomplete({
					source: function(request, response) {

						//var supplierName=$("#supplierName").val();

						$.ajax({
						 type: "GET",
						 contentType: "application/json; charset=utf-8",
						 url: '${pageContext.request.contextPath}/GetAllSupplier',
						 data: {
								"supplierId" : $("#supplier").val(),
								//"supplierName":supplierName,
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
						}, minLength:0, maxShowItems: 40, scroll:true,

						select: function(event, ui) {
						/*prsuppid.value = (ui.item ? ui.item[0]  : '');
						supplierName.value=ui.item[1];
						prsuppaddress.value= ui.item[2];*/
						this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');  
			     		document.getElementById("supcode").value = (ui.item ? ui.item[0] + ";" + ui.item[1] : '');
			     		
						return false;
						}
						}).autocomplete("instance")._renderItem = function(ul, item) {
						return $("<li class='each'>")
						.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						    item[0] + "</span><br><span class='desc'>" +
						    item[1] + "</span></div>")
						.appendTo(ul);
						};
						$("#supplier").focus(function(){
							if (this.value == ""){
						   	$(this).autocomplete("search");
							}						
						});



/// end of supplier auto complete
			
		// collapse active class	
      $('.panel-collapse').on('show.bs.collapse', function () {
        
	    $(this).siblings('.panel-heading').removeClass('active');
	  });

	  $('.panel-collapse').on('hide.bs.collapse', function () {
	    $(this).siblings('.panel-heading').addClass('active');
	  });

	  $('#sitesBalanceVal'). click(function(){
			DeleteMarkers();
			if($(this). is(":checked")){
				document.getElementById("sitesBalanceQty").checked = false;
				$('#sitesBalanceQty').val('0');
				$(this).val('1');

				var Nairobi=new google.maps.LatLng(-1.286389,36.817223);		 		
				document.getElementById("mapContainer").innerHTML ="";

				var map = new google.maps.Map(document.getElementById("mapContainer"), {
							mapTypeControl: false,
							center:Nairobi ,
							zoom: 6,
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
							//Add legend button under zoom control on map
					 		const centerControlDiv = document.createElement("div");
					 		LegendControl(centerControlDiv, map);
					 		map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

						
					 		const DefaultZoomDiv = document.createElement("div");
					 	    DefaultZoomControl(DefaultZoomDiv, map);
					 	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);			

					 	   $("input[name='legendCheckbox']").prop('checked',false);
							defaultLegend();

						    alcBalanceValSetColor(alcBlcQtyData,map);
						}

					else if($(this). is(":not(:checked)")){
						$(this).val('0');
					}     
		});	  	

		$('#sitesBalanceQty'). click(function(){
		    DeleteMarkers();
			
			if($(this). is(":checked")){
				document.getElementById("sitesBalanceVal").checked = false;
				$('#sitesBalanceVal').val('0');
				$(this).val('1');

				var Nairobi=new google.maps.LatLng(-1.286389,36.817223);		 		
				document.getElementById("mapContainer").innerHTML ="";

				var map = new google.maps.Map(document.getElementById("mapContainer"), {
					mapTypeControl: false,
					center:Nairobi ,
					zoom: 6,
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
				
					//Add legend button under zoom control on map
			 		const centerControlDiv = document.createElement("div");
			 		LegendControl(centerControlDiv, map);
			 		map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

				
			 		const DefaultZoomDiv = document.createElement("div");
			 	    DefaultZoomControl(DefaultZoomDiv, map);
			 	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);			

			 	   $("input[name='legendCheckbox']").prop('checked',false);
				    alcBalanceQtySetColor(alcBlcQtyData,map);
				}

			else if($(this). is(":not(:checked)")){
				$(this).val('0');
			}     
		});		 		
// generating report			 
	$("#generate").click(  function() {

		 defaultLegend();
		 $("#sitesBalanceQty").prop('checked',false);  			  
		 $("#sitesBalanceVal").prop('checked',true); 

		 DeleteMarkers();
		 
		 var element = document.getElementById("selectUnselect");
		 if (element.innerHTML == "Select All"){
		 	element.innerHTML = "Unselect All";
		 }
		 
			    
		 Checkvalue = "ledger";
	       
			       
			    var itemCodeID = $("#itemCode").val();

			    var wareID = $("#warcode").val();
			    
			    var supplier = $("#supcode").val();
			    var x = supplier.indexOf(";");
			    var supplierID = supplier.substring(0, x);
			    
			    
			    //var nodeID = $("#nodeId").val();
			  // var cellID = $("#cellId").val();
			    
			    var site = $("#site").val();
			    var n = site.indexOf(";");
			    var siteID = site.substring(0, n);
			    
			    var siteeId = $("#site").val();
			    var b = siteeId.indexOf(";");
			    var siteeID = siteeId.substring(0, b);

			  /*  var serialNum = $("#sncode").val();
				var x = serialNum.indexOf(";");
				var serialNumber = serialNum.substring(0, x);*/


				//Generate google maps data
			    $.ajax({
		  		 	  type: "GET",
		  		 	  contentType: "application/json; charset=utf-8",
		  		 	  url: '${pageContext.request.contextPath}/GetAlcLedgerDataGIS',
		  		 	  data: {
		  				 	
		  				 	"checkedValue":Checkvalue,	
		  				 	"startDate" : $("#startdate").val(),
						    "endDate" : $("#enddate").val(),							    
						    "itemCode": itemCodeID,
						    "warehouse" : wareID,
						    "supplier" :supplierID,
						    //"voucherType" : $("#voucherType").val(),
						    //"voucherNb" : $("#vncode").val(),
						    "siteId" : siteID,
						    "siteName" : $("#siteName").val(),
						    "itemPartNo": $("#itemPartNo").val(),
						    "itemModel" :  $("#itemModel").val(),
						    //"serialNb" :serialNumber,					    		
		  		 	},

		  		 	dataType: "json",
		  		 	success: function (data) {

			  		 	if (data != null) {
				  		 	
				  		 	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
		  		 		    document.getElementById("mapContainer").innerHTML ="";
		  		 			var map = new google.maps.Map(document.getElementById("mapContainer"), {
			  		 			mapTypeControl: false,
		  		 				center:Nairobi ,
		  					    zoom: 6,
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
			  		 		
		  		 		//Add legend button under zoom control on map
		  		 		const centerControlDiv = document.createElement("div");
		  		 		LegendControl(centerControlDiv, map);
		  		 		map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

						
		  		 		const DefaultZoomDiv = document.createElement("div");
		  		 	    DefaultZoomControl(DefaultZoomDiv, map);
		  		 	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);
						
		  		 	}
			  	
			  		alcBalanceValSetColor(data.alcBalanceList,map);
					
			  	
		  			
		  		 },
		  		 error: function(result) {
		  			console.log("Error");
		  		 }
		  		 });
				
			    
		 	$.ajax({
					type : "GET",
					contentType: "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/GenerateLedgerReport",
					dataType : "json",
					data : {
					    //"checkValType": Checkvalue,
					    "itemCode": itemCodeID,
					    "startDate" : $("#startdate").val(),
					    "endDate" : $("#enddate").val(),
					    "warehouse" : wareID,
					    "supplier" :supplierID,
					    //"voucherType" : $("#voucherType").val(),
					    //"voucherNb" : $("#vncode").val(),
					    "siteId" : siteID,
					    "siteName" : $("#siteName").val(),
					    //"nodeId" : nodeID,
					    //"cellId" : cellID,
					   // "itemPartNo": $("#itemPartNo").val(),
					   // "itemModel" :  $("#itemModel").val(),
					    
					    	
					},
					success : function(data) {
						 if (data != null) {
	                         var boqArray = [];
	                        
	         	     	    boqArray = data.assetLifeCycleLedgerList;
	         	     	    console.log("start GRID "+boqArray);
	         	     	    var ReportArrayGlobal = [];
		     		   
	         	     	  // Fill tables rows from DB
		     		           $.each(boqArray, function (i, value) { 
		    					 	ReportArrayGlobal.push({
		    					 		//ID:  boqArray[i].ID,
		    					 		date: ((boqArray[i].creationDate) == null)? "" : boqArray[i].creationDate,
		    							itemCode: ((boqArray[i].itemCode) == null)? "" : boqArray[i].itemCode,
		    							ItemModel: ((boqArray[i].itemModel) == null)? "" : boqArray[i].warehouse,
		    							Warehouse: ((boqArray[i].warehouse) == null)? "" : boqArray[i].warehouse,
		    							voucherType: ((boqArray[i].voucherType) == null)? "" : boqArray[i].voucherType,
		    							voucherNB : ((boqArray[i].voucherNB) == null)? "" : boqArray[i].voucherNB,
		    							UOM: ((boqArray[i].uom) == null)? "" : boqArray[i].uom,
		    							actualQty: ((boqArray[i].actualQty) == null)? "" : boqArray[i].actualQty,
		    							inComingRate: ((boqArray[i].inComingRate) == null)? "" : boqArray[i].inComingRate,
		    							outGoingRate: ((boqArray[i].outGoingRate) == null)? "" : boqArray[i].outGoingRate,
		    							accumulatedDepreciation: ((boqArray[i].accumulatedDepreciation) == null)? "" : boqArray[i].accumulatedDepreciation,
		    							netPrice: ((boqArray[i].netPrice) == null)? "" : boqArray[i].netPrice,
		    							valuationRate: ((boqArray[i].valuationRate) == null)? "" : boqArray[i].valuationRate,
		    							balanceValue: ((boqArray[i].balanceValue) == null)? "" : boqArray[i].balanceValue,
		    							netBalanceValue: ((boqArray[i].netBalanceValue) == null)? "" : boqArray[i].netBalanceValue,
		    							balanceQty: ((boqArray[i].balanceQty) == null)? "" : boqArray[i].balanceQty,
		    			                
		    					 	});
		    					 	
		     		          });
		     		
		     		        }
		     	    
		     		    
	          			      
		     		       //// gritable
		     		       console.log("start drawing");
                            var almgrid = new AlmgridTable({
						           tableId: "Ltable",
						           dataArray: ReportArrayGlobal,
						           selectCheckbox: false,
						           drawTableGrid :  function (tableId, dataArray) {
				          				 //console.log("******** emptyyyyy");
				          				 //let almgrid = this;
				          			        var tableBody = document.querySelector("#" + tableId + " tbody");
				          			        var columnLinkNb = this.columnLinkNb;
				          			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
				          			        var gridContainerId = tableId + "_container";
				          			        $(gridContainer).attr('id', gridContainerId);
				          			        //console.log("Table id is "+tableId);
				          			        $(tableBody).empty();
				          			      	//console.log(dataArray);
				          			        console.log(dataArray.length);
				          			        if (dataArray.length > 0) {

				          			            var ArrayKeys = Object.keys(dataArray[0]);


				          			            var sumBalanceQtyCol = 0,sumInQtyCol = 0, sumInValueCol = 0, sumOutQtyCol = 0, sumOutValueCol = 0, sumBalanceValueCol = 0, sumAccumulatedDepreciationCol = 0, sumNetBalanceValueCol = 0 ;
				          						var sumBalanceQtyLed = 0, sumBalanceValLed = 0, sumAccumulatedDeprLed = 0, sumNetBalanceValLed = 0;
				          						var column;
				          			            for (var i = 0; i < dataArray.length; i++) {
				          			               for (var j = 0; j < ArrayKeys.length; j++) {
				          			               	  column = ArrayKeys[j];

				          			                if( column  == "balanceQty"){
			          			                    	   sumBalanceQtyCol += dataArray[i][ArrayKeys[j]];
			          			                       	   
			          			                       }
			          			                       if( column  == "balanceValue"){
			          			                    	   sumBalanceValueCol += dataArray[i][ArrayKeys[j]];
			          			                       	   
			          			                       }
			          			                       if( column  == "accumulatedDepreciation"){
			          			                    	   sumAccumulatedDepreciationCol += parseInt(dataArray[i][ArrayKeys[j]]);
			          			                       	   
			          			                       }
			          			                       if( column  == "netBalanceValue"){
			          			                    	   sumNetBalanceValueCol += dataArray[i][ArrayKeys[j]];
			          			                       	   
			          			                       }
				          			                     
				          			                   

				          			               }
				          							}

				          			        
				          			        		 if (dataArray.length > 1) {
				          			        			 tBalanceQtyLed.value = sumBalanceQtyCol;
							     		        		 tBalanceValLed.value = sumBalanceValueCol;
							     		        		 //tAccumulatedDeprLed.value = sumAccumulatedDepreciationCol;
							     		        		 //tNetBalanceValLed.value = sumNetBalanceValueCol;
				          			        	    }
				          			
				          	           	console.log("after append table")
				          	           	

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

				          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox });

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
                            console.log("finish drawing");
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});
		 
	}); 
 ////////////export to csv   		 
	
     $('#export').on('click',function(){
    	 if (checkBox1.checked == true){
              $("#bisotab1").tableHTMLExport({type:'csv',filename:'sample.csv'});
    	 }
       });	
   ///////////export to pdf  

       $('#pdf').on('click',function(){   
         if (checkBox1.checked == true){
        	 $("#bisotab1").tableHTMLExport({type:'pdf',orientation:'l',filename:'sample.pdf'});
         }
       });   


/////// print table
 $('#print').on('click',function(){   
     if (checkBox1.checked == true){
    	 $('#bisotab1').printThis();   
     }
   });   
	 

	 
	
 });
 

 

 
 </script>
 
 <script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
 
</html>