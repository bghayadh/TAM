<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
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
	
<!-- 	 <script src="${pageContext.request.contextPath}/resources/js/FileSaver.min.js"></script> -->
	 <!--  <script src="${pageContext.request.contextPath}/resources/js/xlsx.core.min.js"></script> -->
   <script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
   <script src="${pageContext.request.contextPath}/resources/js/jspdf.plugin.autotable.min.js"></script>
   <script src="${pageContext.request.contextPath}/resources/js/tableHTMLExport.js"></script>
 <!--   <script src="${pageContext.request.contextPath}/resources/js/tableExport.js"></script>-->

  <script src="${pageContext.request.contextPath}/resources/js/AssetLifeCycle/TransactionModel.js"></script> 
  <script src="${pageContext.request.contextPath}/resources/js/AssetLifeCycle/FilterOptionModel.js"></script> 
   
  <!--  <script src="https://cdn.datatables.net/plug-ins/1.10.21/api/sum().js">-->
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
 <jsp:include page="header.jsp"></jsp:include>
    
   
  

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
<!--  	</div>
	   </div>-->
<!-- 	   <div class="col-md-2">
		   <div class="form-group">
		     <span>Site</span>
		     <input type="text"  id="site" value="${site}" class="form-control text-input"   />
		  </div>
       </div>
       
       <div class="col-md-2">
		   <div class="form-group">
		     <span>Item</span>
			 <input type="text"  id="icode" name ="itmCode" value="${item}" class="form-control text-input"   />
		  </div>
       </div>
    -->    
			
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
  
  
  
<!--         <div class="col-md-2">
			<div class="form-group">
			    <span>Start Date</span>
				<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<input type="text" id="startdate" value="${startDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					<div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
					</div>
				</div>
		</div>
	</div>
	
	
	<div class="col-md-2">
			<div class="form-group">
			    <span>End Date</span>
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<input type="text" id="enddate" value="${endDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					<div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
					</div>
				</div>
		</div>
	</div>
-->	
<!-- 	<div class="col-md-auto">
	 <div class="form-group" style="padding-top: 30px;">
		   			
      			<input type="checkbox" name="allDays" id="allDays" value="allDays">
      			<span>All</span>
			
	</div>
 </div>
-->	
   <div class="col-md-3">
		   <div class="form-group">
		          <span>Warehouse</span>
				  <input type="text"  id="warcode" value="${warehouse}" class="form-control text-input"   />	
		   </div>
  </div>	
	
<!--  <div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text"  id="warname" placeholder="Warehouse Name" value="${warehouseName}" class="form-control text-input"   />
				</div>
		 </div>
  </div>
-->	
	
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
		     
			 <input type="text"  id="itemCategory" value="${itemCategory}" class="form-control text-input"   />
		  </div>
  </div>
  
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
		     <span>Node</span>
			 <input type="text"  id="nodeId" value="${nodeId}" class="form-control text-input"   />
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
				 <span>Node Name</span>
					<input type="text"  id="nodeName"  value="${nodeName}" class="form-control text-input"   />
				
		 </div>
  </div>
 
   <div class="col-md-3">
		   <div class="form-group">
		      <span>Node Type</span>
			  <input type="text"  id="nodeType" name="nodeType" value="${nodeType}" class="form-control text-input"   />
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
		     <span>Cell</span>
			 <input type="text"  id="cellId" name="cellId" value="${cellId}" class="form-control text-input"   />
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
				 <span>Cell Name</span>
					<input type="text"  id="cellName"  value="${cellName}" class="form-control text-input"   />
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
  
  </div>
  <!--  <button type="button" class="btn btn-secondary"   data-dismiss="modal" id='closeFieldsOption'>Close</button>-->
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
       
                
       <div class="col-md-1">
          
           <div class="form-group">
				 <div class="input-group-prepend" data-target-input="nearest">
					 <div class="input-group-text">
						
      					  <input type="checkbox" name="balance" id="balance" value="balance" checked>
      					  <span style="margin-left: 10px;">Balance</span>
				     </div>
                 </div>
	      </div>
			
	   </div>
            
       <div class="col-md-1">
          
           <div class="form-group">
		      <div class="input-group-prepend" data-target-input="nearest">
				<div class="input-group-text">
						
      					   <input type="checkbox" name="ledger" id="ledger" value="ledger">
      					   <span style="margin-left: 10px;">Ledger</span>
			    </div>
               </div>
		   </div>
			
	    </div>
	                         
	    <div class="col-md-1">
          
          <div class="form-group">
			<div class="input-group-prepend" data-target-input="nearest">
				 <div class="input-group-text">
						
      					     <input type="checkbox" name="network" id="network" value="network">
      					     <span style="margin-left: 10px;">Network</span>
				 </div>
            </div>
	     </div>
			
	   </div>
	                         
	   <div class="col-md-1">
          
            <div class="form-group">
				<div class="input-group-prepend" data-target-input="nearest">
					  <div class="input-group-text">
						
      					     <input type="checkbox" name="transaction" id="transaction" value="transaction">
      					     <span style="margin-left: 10px;">Transaction</span>
      					     <button type="button" class="btn" name= "transCol" id="transColumns" onclick='openTransactionModel(this)'> <i class="fa fa-bars"></i></button>
				      </div>
                </div>
		   </div>
			
	     </div>
	     
	     <div class="col-md-2" style="float: right; padding-top:-100px;" >
			<div class="btn-group pull-right"  >
		 			<button type="button"  id ="clearButton"class="btn btn-light" style=" margin-top:-4px;"  >Clear</button>
		 		</div>
		 	</div>
            
                            
            
      
       
</div>

 
    <!-- Modal transaction-->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
                aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered" role="document" id="model">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="myModalLabel"> Transaction Table</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                            <script>
                            var x = false;
                            </script>
                            <div class="col-md-auto">
          
            	              <div class="form-group">
				                <div class="input-group-prepend" data-target-input="nearest">
					             <div class="input-group-text">
						
      					            <input type="radio" name="Site" id="Site" value="Site" onmousedown="if (this.checked) { x = true; }" onclick="if (x) {this.checked = false; x = false; return true;}">
      					            <span style="margin-left: 10px;">Site</span>
				                   </div>
                                 </div>
			                   </div>
			
	                         </div>
	                         
	                         <div class="col-md-auto">
          
            	              <div class="form-group">
				                <div class="input-group-prepend" data-target-input="nearest">
					             <div class="input-group-text">
						
      					           <input type="radio" name="Node" id="Node" value="Node" onmousedown="if (this.checked) { x = true; }" onclick="if (x) {this.checked = false; x = false; return true;}">
      					            <span style="margin-left: 10px;">Node</span>
				                   </div>
                                 </div>
			                   </div>
			
	                         </div>
                            
                            <div class="col-md-auto">
          
            	              <div class="form-group">
				                <div class="input-group-prepend" data-target-input="nearest">
					             <div class="input-group-text">
						
      					            <input type="radio" name="Cabinet" id="Cabinet" value="Cabinet" onmousedown="if (this.checked) { x = true; }" onclick="if (x) {this.checked = false; x = false; return true;}">
      					            <span style="margin-left: 10px;">Cabinet</span>
				                   </div>
                                 </div>
			                   </div>
			
	                         </div>
                            
                            <div class="col-md-auto">
          
            	              <div class="form-group">
				                <div class="input-group-prepend" data-target-input="nearest">
					             <div class="input-group-text">
						
      					           <input type="radio" name="Board" id="Board" value="Board" onmousedown="if (this.checked) { x = true; }" onclick="if (x) {this.checked = false; x = false; return true;}">
      					            <span style="margin-left: 10px;">Board</span>
				                   </div>
                                 </div>
			                   </div>
			
	                         </div>
                            
                            <div class="col-md-auto">
          
            	              <div class="form-group">
				                <div class="input-group-prepend" data-target-input="nearest">
					             <div class="input-group-text">
						
      					           <input type="radio" name="Antenna" id="Antenna" value="Antenna" onmousedown="if (this.checked) { x = true; }" onclick="if (x) {this.checked = false; x = false; return true;}">
      					            <span style="margin-left: 10px;">Antenna</span>
				                   </div>
                                 </div>
			                   </div>
			
	                         </div>
                            
                            <div class="col-md-auto">
          
            	              <div class="form-group">
				                <div class="input-group-prepend" data-target-input="nearest">
					             <div class="input-group-text">
						
      					            <input type="radio" name="Cell" id="Cell" value="Cell" onmousedown="if (this.checked) { x = true; }" onclick="if (x) {this.checked = false; x = false; return true;}"> 
      					            <span style="margin-left: 10px;">Cell</span>
				                   </div>
                                 </div>
			                   </div>
			
	                         </div>
                            
                            <div class="col-md-auto">
          
            	              <div class="form-group">
				                <div class="input-group-prepend" data-target-input="nearest">
					             <div class="input-group-text">
						
      					            <input type="radio" name="Host" id="Host" value="Host" onmousedown="if (this.checked) { x = true; }" onclick="if (x) {this.checked = false; x = false; return true;}">
      					            <span style="margin-left: 10px;">Host</span>
				                   </div>
                                 </div>
			                   </div>
			
	                         </div>
                            
                            
                            
                            
                               
                            </div>
                            
                             <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                          <form>
                                           
		                                    <label id= "labelSiteId" for="siteId" style=" display: block; "> <input type="checkbox" name="siteId" id="siteId" value="siteId"  disabled="disabled"> Site ID</label>
		                                    <label id= "labelSiteNameTrans" for="siteNameTrans" style=" display: block; "> <input type="checkbox" name="siteNameTrans" id="siteNameTrans" value="siteNameTrans"  disabled="disabled"> Site Name</label>
		                                   
		                                   </form>
                                           
                                        </div>
                                    </div>
                                </div>
                                
                           
                            
                             
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                          <form>
                                           <label id= "labelNodeIDTr" for="nodeIdTr" style=" display: block; "> <input type="checkbox" name="nodeIdTr" id="nodeIdTr" value="nodeIdTr"  disabled="disabled"> Node ID</label>
		                                   <label id= "labelNodeNameTr" for="nodeNameTr" style=" display: block; "> <input type="checkbox" name="nodeNameTr" id="nodeNameTr" value="nodeNameTr"  disabled="disabled"> Node Name</label>
		                                   <label id= "labelNodeSNo" for="nodeSNo" style=" display: block; "> <input type="checkbox" name="nodeSNo" id="nodeSNo" value="nodeSNo"  disabled="disabled"> Node SNo.</label>
		                                   <label id= "labelNodeTypeTr" for="nodeTypeTr" style=" display: block; "> <input type="checkbox" name="nodeTypeTr" id="nodeTypeTr" value="nodeTypeTr"  disabled="disabled"> Node Type</label>
		                                   <label id= "labelNodeTransType" for="nodeTransType" style=" display: block; "> <input type="checkbox" name="nodeTransType" id="nodeTransType" value="nodeTransType"  disabled="disabled"> Node Trans Type</label>
		                                   </form> 
                                        </div>
                                    </div>
                                </div>
                          
                            
                             
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                          <form>
                                            <label id= "labelCabinetPosition" for="cabinetPosition" style=" display: block; "> <input type="checkbox" name="cabinetPosition" id="cabinetPosition" value="cabinetPosition"  disabled="disabled"> Cabinet Position</label>
		                                    <label id= "labelCabinetModel" for="cabinetModel" style=" display: block; "> <input type="checkbox" name="cabinetModel" id="cabinetModel" value="cabinetModel"  disabled="disabled"> Cabinet Model</label>
		                                    <label id= "labelCabinetSNo" for="cabinetSNo" style=" display: block; "> <input type="checkbox" name="cabinetSNo" id="cabinetSNo" value="cabinetSNo"  disabled="disabled"> Cabinet SNo.</label>
		                                    <label id= "labelCabinetTransType" for="cabinetTransType" style=" display: block; "> <input type="checkbox" name="cabinetTransType" id="cabinetTransType" value="cabinetTransType" disabled="disabled"> Cabinet Trans Type</label>
		                                   </form> 
                                        </div>
                                    </div>
                                </div>
                           
                            
                           
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                          <form>
                                            <label id= "labelBoardPosition" for="boardPosition" style=" display: block; "> <input type="checkbox" name="boardPosition" id="boardPosition" value="boardPosition"  disabled="disabled"> Board Position</label>
		                                    <label id= "labelBoardModel" for="boardModel" style=" display: block; "> <input type="checkbox" name="boardModel" id="boardModel" value="boardModel"  disabled="disabled"> Board Model </label>
		                                    <label id= "labelBoardSNo" for="boardSNo" style=" display: block; "> <input type="checkbox" name="boardSNo" id="boardSNo" value="boardSNo"  disabled="disabled"> Board SNo.</label>
		                                    <label id= "labelBoardTransType" for="boardTransType" style=" display: block; "> <input type="checkbox" name="boardTransType" id="boardTransType" value="boardTransType"  disabled="disabled"> Board Trans Type</label>
		                                   </form> 
                                        </div>
                                    </div>
                                </div>
                          
                            
                            
                               
                            
                             
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                         <form>
		                                    <label id= "labelAntennaId" for="antennaId" style=" display: block; "> <input type="checkbox" name="antennaId" id="antennaId" value="antennaId"  disabled="disabled"> Antenna ID</label>
		                                    <label id= "labelAntennaModel" for="antennaModel" style=" display: block; "> <input type="checkbox" name="antennaModel" id="antennaModel" value="antennaModel"  disabled="disabled"> Antenna Model </label>
		                                    <label id= "labelAntennaSNo" for="antennaSNo" style=" display: block; "> <input type="checkbox" name="antennaSNo" id="antennaSNo" value="antennaSNo"  disabled="disabled"> Antenna SNo.</label>
		                                    <label id= "labelAntennaTransType" for="antennaTransType" style=" display: block; "> <input type="checkbox" name="antennaTransType" id="antennaTransType" value="antennaTransType"  disabled="disabled"> Antenna Trans Type</label>
		                                  </form>
                                        </div>
                                    </div>
                                </div>
                          
                            
                             
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                           <form>
		                                    <label id= "labelCellIdTr" for="cellIdTr" style=" display: block; "> <input type="checkbox" name="cellIdTr" id="cellIdTr" value="cellIdTr"  disabled="disabled"> Cell ID</label>
		                                    <label id= "labelCellNameTr" for="cellNameTr" style=" display: block; "> <input type="checkbox" name="cellNameTr" id="cellNameTr" value="cellNameTr"  disabled="disabled"> Cell Name </label>
		                                    <label id= "labelCellTransType" for="cellTransType" style=" display: block; "> <input type="checkbox" name="cellTransType" id="cellTransType" value="cellTransType"  disabled="disabled"> Cell Trans Type</label>
		                                   </form>
                                        </div>
                                    </div>
                                </div>
                           
                            
                              
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                           <form>
		                                   <label id= "labelHostVersion" for="hostVersion" style=" display: block; "> <input type="checkbox" name="hostVersion" id="hostVersion" value="hostVersion"  disabled="disabled"> Host Version </label>
		                                   <label id= "labelHostVersionTrans" for="hostVersionTrans" style=" display: block; "> <input type="checkbox" name="hostVersionTrans" id="hostVersionTrans" value="hostVersionTrans"  disabled="disabled"> Host Version Trans</label>
		                                   </form> 
                                        </div>
                                    </div>
                                </div>
                            
                            
                        </div>
                            <div class="modal-footer" >
                            <button type="button" class="btn btn-secondary" data-dismiss="modal" id='clearTrans'>Clear</button>
                            <button type="button" id='saveTrans' class="btn btn-primary"> Save </button>
                        </div>
                    </div>
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
      <div id="generateTables" style="display:none;Padding:0 px;"> 
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
                                <!-- Balance Table -->
                                <form id="checktableBalance" style="display:none;">
								<div id= "gridTableBalance" class="table-responsive almgrid-table-div">
									<table id="Btable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>Item
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Item Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

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
												
												<th>Opening Qty
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Opening Value
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
											
												<th>In Qty
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Out Qty
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
												
												<th>In Value
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Out Value
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
												
											<!--  	<th>Net Balance Value
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>-->
												
												<!--  <th>Accumulated Depreciation
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>-->
												
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
												<!--  <th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>-->
												
											</tr>
										</thead>
										<tbody >
						         
									
						                </tbody>

									</table>
								</div>
                              </form>
                              
                              <!-- Ledger Table -->
                                <form id="checktableLedger" style="display:none">
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
                               <!-- Transaction Table -->
                                <form id="checktabletransaction" style="display:none">
								<div class="table-responsive almgrid-table-div">
									<table id="Ttable" class="table table-striped table-bordered almgrid-table">
										<thead id="head_Trans">
											
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

		
<div id="divLed" Style="display:none; left: 0; bottom: 0;">	
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

<div id="divBal" Style="display:none; left: 0; bottom: 0;">	
<div class="row">
	
	 <div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Total In Qty</span>
				<input type="text" id="tInQtyBal" value="${tInQtyBal}" readonly class="form-control text-input" />
			</div>
		</div>
    </div> 
	
    <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total In Value</span>
						<input type="text" id="tInvalueBal" value="${tInvalueBal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	  <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Out Qty</span>
						<input type="text" id="tOutQtyBal" value="${tOutQtyBal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Out Value</span>
						<input type="text" id="tOutValBal" value="${tOutValBal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	</div>
	<div class="row">
	
	 <div class="col-md-3">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Balance QTY</span>
				<input type="text" id="tBalanceQtyBal" value="${tBalanceQtyBal}" readonly class="form-control text-input" />
				</div>
			</div>							
	</div>
	
	 <div class="col-md-3">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Balance Value</span>
				<input type="text" id="tBalanceValBal" value="${tBalanceVal}" readonly class="form-control text-input" />
				</div>
			</div>							
	</div>	
	
	<!--  <div class="col-md-3">
			 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Accumulated Depreciation</span>
						<input type="text" id="tAccumulatedDeprBal" value="${tAccumulatedDeprBal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>	
		
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Net Balance Value</span>
						<input type="text" id="tNetBalanceValBal" value="${tNetBalanceValBal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 	
	</div> -->	
				
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

	document.querySelector("#sncode").value = ""
	document.querySelector("#voucherType").value = ""
	document.querySelector("#vncode").value = ""

	document.querySelector("#nodeId").value = ""
	document.querySelector("#nodeName").value = ""
	document.querySelector("#nodeType").value = ""
	document.querySelector("#cellId").value = ""
	document.querySelector("#cellName").value = ""
	document.querySelector("#domainType").value = ""
	document.querySelector("#prcode").value = ""
	document.querySelector("#UOM").value = ""
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

	 
     
	 // by defualt display balance
	 document.getElementById("checktableBalance").style.display = "block";
	 document.getElementById("Btable").style.display = "block";
	 document.getElementById("generateTables").style.display = "block";
		var type = '${Type}';
     	//console.log("***888 "+type);
	 
     var  Checkvalue = "";
	 $("#voucherType").click(function(){
		 vncode.value = null;
	 });
	 
	 
	 var sel = document.getElementById("voucherType");
	 var result = sel.options[sel.selectedIndex].value;
	 var option = $("#voucherType").children("option:selected"). val();


	 var ALCArrayGlobal = ${ALCArrayList};
	 var almgrid = new AlmgridTable({
         tableId: "Btable",
         dataArray: ALCArrayGlobal,
         selectCheckbox: false,
         drawTableGrid :  function (tableId, dataArray) {

        	 //let almgrid = this;
		        var tableBody = document.querySelector("#" + tableId + " tbody");
		        var columnLinkNb = this.columnLinkNb;
		        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
		        var gridContainerId = tableId + "_container";
		        $(gridContainer).attr('id', gridContainerId);
		        //console.log("Table id is "+tableId);
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

   	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb,dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox });

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
     
	
//checking the Balance/ledger checkbox
	 
	 // Get the output table
		   var table1 = document.getElementById("checktableBalance");
		   var table2 = document.getElementById("checktableLedger");
		   var table3 = document.getElementById("checktabletransaction");
	 // Get the total output of table
	      var div1 = document.getElementById("divBal");
	      var div2 = document.getElementById("divLed");

	 $('input[name="balance"]'). click(function(){
			if($(this). is(":checked")){
				console.log("hereeee");
				$('input[type="checkbox"]').not(this).prop("checked", false);
				table1.style.display = "block";
			    table2.style.display = "none";
			    div2.style.display = "none";
			    
			    table3.style.display = "none";
			    //div3.style.display = "none";

			    console.log("Checkbox is checked." );
			
			}
			else if($(this). is(":not(:checked)")){
				
				 table1.style.display = "none";
			     div1.style.display = "none";
				  
			     document.getElementById("balance").checked = false;
			     document.getElementById("generateTables").style.display = "none";
			     console.log("Checkbox is unchecked." );
			}
	});
				 
			$('input[name="ledger"]'). click(function(){
				if($(this). is(":checked")){
					 $('input[type="checkbox"]').not(this).prop("checked", false); 
					 
					 table1.style.display = "none";
				     div1.style.display = "none";
				     
				     table3.style.display = "none";
				     //div3.style.display = "none";
				    
				     console.log("Checkbox is checked." );
				}
				else if($(this). is(":not(:checked)")){
					   table2.style.display = "none";
				       div2.style.display = "none";
				       document.getElementById("ledger").checked = false;
				       document.getElementById("generateTables").style.display = "none";
				  console.log("Checkbox is unchecked." );
				}     
			 });
			
			$('input[name="network"]'). click(function(){
				if($(this). is(":checked")){
					  $('input[type="checkbox"]').not(this).prop("checked", false);
					 
					  table1.style.display = "none";
				      div1.style.display = "none";
				     
				      table2.style.display = "none";
				      div2.style.display = "none";
				     
				      table3.style.display = "none";
				      //div3.style.display = "none";
				     
				     console.log("Checkbox is checked." );
				}
				else if($(this). is(":not(:checked)")){
					   //table4.style.display = "none";
				       //div4.style.display = "none";
				  document.getElementById("network").checked = false;
				  document.getElementById("generateTables").style.display = "none";
				  console.log("Checkbox is unchecked." );
				}     
			 });


			$('input[name="transaction"]'). click(function(){
				if($(this). is(":checked")){
					 $('input[type="checkbox"]').not(this).prop("checked", false);
					 
					 table1.style.display = "none";
				     div1.style.display = "none";
				     
				     table2.style.display = "none";
				     div2.style.display = "none";
				     
				    
				     console.log("Checkbox is checked." );
				     
				     
				     
				}
				else if($(this). is(":not(:checked)")){
					   table3.style.display = "none";
				      // div3.style.display = "none";
				       document.getElementById("generateTables").style.display = "none";
				  console.log("Checkbox is unchecked." );
				}     
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
				     		sncode.value = "";
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
		 
		// clearing tables
		 $("#Btable").remove();
         $("#gridTableBalance").append('<table id="Btable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header"><th>Item<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'+ 
                 '<th>Item Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
                 +'<th>Item Model<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
                 +'<th>Warehouse<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
                 +'<th>UOM<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
                 +'<th>Opening Qty<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>Opening Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>In Qty<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>Out Qty<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>Balance Qty<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>In Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>Out Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>Balance Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				// +'<th>Net Balance Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				// +'<th>Accumulated Depreciation<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
				 +'<th>Valuation Rate<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th></tr>'
				 +'<tr><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				// +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 //+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
				 +'</tr></thead><tbody></tbody></table>');


  	   $("#Ltable").remove();
  	   $("#gridTableLedger").append('<table id="Ltable" class="table table-striped table-bordered almgrid-table"><thead><tr class="header"><th>Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Item<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
  		  	   +'<!-- <th>Item Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>-->'
  		  	   +'<th>Item Model<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Warehouse<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Voucher Type<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Voucher#<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>UOM<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Qty<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"><i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Incoming Rate<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Outgoing Rate<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Accumulated Depreciation<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Net Price<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Valuation Rate<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Balance Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Net Balance Value<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
  		  	   +'<th>Balance Qty<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th></tr>'
  		  	   +'<tr><th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		  	   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		  	   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		       +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		       +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		       +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		       +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		       +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
  		       +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
			   +'<th><input type="text" class="almgrid-search" placeholder="Search"></th></tr></thead><tbody></tbody></table>');
  		
		
				//Get the checkbox
				 var checkBox1 = document.getElementById("balance");
				 var checkBox2 = document.getElementById("ledger");
				 var checkBox3 = document.getElementById("transaction");
				 // Get the output table
				 var tables = document.getElementById("generateTables");
				 var table1 = document.getElementById("checktableBalance");
				 var table2 = document.getElementById("checktableLedger");
				 var table3 = document.getElementById("checktabletransaction");
				 
				 // Get the total output of table
		        var div1 = document.getElementById("divBal");
		        var div2 = document.getElementById("divLed");
		       // var div3= document.getElementById("divBal");
		        var tbody_ledger = document.getElementById('tbody_ledger');
		        var tbody_balance = document.getElementById('tbody_balance');
		        var table;
		        
			 // If the checkbox is checked, display the output table
				        
		        if (checkBox1.checked == true){
		        	table = "Btable";
		        	tables.style.display = "block";
			        table1.style.display = "block";
			       //div1.style.display = "block";

			     
			       
			   /*  	if(tbody_ledger != null)
				     {
				     	if (tbody_ledger.parentNode) {
				     		tbody_ledger.parentNode.removeChild(tbody_ledger);
						} 
		        	 }
*/
			       Checkvalue = "balance";
			  
		        }
			 

		        if (checkBox2.checked == true){
		        	table = "Ltable";
		        	tables.style.display = "block";
  			        table2.style.display = "block";
  			       // div2.style.display = "block";

			/*	if(tbody_balance != null)
				{
	  		     	if (tbody_balance.parentNode) {
	  		     		tbody_balance.parentNode.removeChild(tbody_balance);
					}
				}
*/
  			      Checkvalue = "ledger";
			       
		        }
		        
		       
				var transactionTableOption = "";
		        if (checkBox3.checked == true){
   		    	 // $('#myModal').modal('show');
   		    	   table = "Ttable";
   		    	   tables.style.display = "block";
   		    	   var siteTable = document.getElementById("Site");
  		           var cellTable = document.getElementById("Cell")
 				   var nodeTable = document.getElementById("Node"); 
 				   var cabinetTable = document.getElementById("Cabinet");
 				   var boardTable = document.getElementById("Board");
 				   var antennaTable = document.getElementById("Antenna");
 				   var hostTable = document.getElementById("Host");
   		    	  
   		    	  
   		    		Checkvalue = "transaction";
   		    		var count= 0;
   			        var arrayColumn =[];
   			        var arrayColumnGetter =[];
   			        
   			        arrayColumn.push("Date");
			        arrayColumn.push("Trans Source");
			        arrayColumn.push("Trans Type");
			        arrayColumn.push("Trans ID");
			        arrayColumn.push("Active Record");
				 
   			     if(cellTable.checked == true){
					  
					  if(document.getElementById("cellIdTr").checked = true){
						  arrayColumn.push("Cell ID");
						  arrayColumnGetter.push("cellId");
						  count++;
					  }
					  if(document.getElementById("cellNameTr").checked = true){
						  arrayColumn.push("Cell Name");
						  arrayColumnGetter.push("cellName");
						  count++;
					  }
					  if(document.getElementById("nodeIdTr").checked = true){
						  arrayColumn.push("Node ID");
						  arrayColumnGetter.push("nodeId");
						  count++;
					  }
					  if(document.getElementById("nodeNameTr").checked = true){
						  arrayColumn.push("Node Name");
						  arrayColumnGetter.push("nodeName");
						  count++;
					  }
					  if(document.getElementById("siteId").checked = true){
						  arrayColumn.push("Site ID");
						  arrayColumnGetter.push("siteId");
						  count++;
					  }
					  if(document.getElementById("siteNameTrans").checked = true){
						  arrayColumn.push("Site Name");
						  arrayColumnGetter.push("siteName");
						  count++;
					  }
					  
					/*  if(document.getElementById("cellTransType").checked = true){
						  arrayColumn.push("Cell Trans Type");
						  arrayColumnGetter.push("cellTransType");
						  count++;
					  }*/
					  
					  transactionTableOption += "Ce";
						
				  }
				  if(siteTable.checked == true){
					  
					  console.log("the transactionTableOption in "+transactionTableOption);
					  if(document.getElementById("siteId").checked = true){
						  arrayColumn.push("Site ID");
						  arrayColumnGetter.push("siteId");
						  count++;
					  }
					  if(document.getElementById("siteNameTrans").checked = true){
						  arrayColumn.push("Site Name");
						  arrayColumnGetter.push("siteName");
						  count++;
					  }
					  
					  transactionTableOption += "S";
				 
				  }
				  
				  if(nodeTable.checked == true){
					  
					  console.log("the transactionTableOption in "+transactionTableOption);
					  
					  if(document.getElementById("cellIdTr").checked = true){
						  arrayColumn.push("Cell ID");
						  arrayColumnGetter.push("cellId");
						  count++;
					  }
					  if(document.getElementById("cellNameTr").checked = true){
						  arrayColumn.push("Cell Name");
						  arrayColumnGetter.push("cellName");
						  count++;
					  }
					  if(document.getElementById("nodeIdTr").checked = true){
						  arrayColumn.push("Node ID");
						  arrayColumnGetter.push("nodeId");
						  count++;
					  }
					  if(document.getElementById("nodeNameTr").checked = true){
						  arrayColumn.push("Node Name");
						  arrayColumnGetter.push("nodeName");
						  count++;
					  }
					  if(document.getElementById("nodeSNo").checked = true){
						  arrayColumn.push("Node SNo.");
						  arrayColumnGetter.push("nodeSNo");
						  count++;
					  }
					  if(document.getElementById("nodeTypeTr").checked = true){
						  arrayColumn.push("Node Type");
						  arrayColumnGetter.push("nodeType");
						  count++;
					  }
					  if(document.getElementById("siteId").checked = true){
						  arrayColumn.push("Site ID");
						  arrayColumnGetter.push("siteId");
						  count++;
					  }
					  if(document.getElementById("siteNameTrans").checked = true){
						  arrayColumn.push("Site Name");
						  arrayColumnGetter.push("siteName");
						  count++;
					  }
					  
					 /* if(document.getElementById("nodeIdTr").checked = true){
						  arrayColumn.push("Node ID");
						  arrayColumnGetter.push("nodeId");
						  count++;
					  }
					  if(document.getElementById("nodeNameTr").checked = true){
						  arrayColumn.push("Node Name");
						  arrayColumnGetter.push("nodeName");
						  count++;
					  }
					  if(document.getElementById("nodeSNo").checked = true){
						  arrayColumn.push("Node SNo.");
						  arrayColumnGetter.push("nodeSNo");
						  count++;
					  }
					  if(document.getElementById("nodeTypeTr").checked = true){
						  arrayColumn.push("Node Type");
						  arrayColumnGetter.push("nodeType");
						  count++;
					  }
					  if(document.getElementById("nodeTransType").checked = true){
						  arrayColumn.push("Node Trans Type");
						  arrayColumnGetter.push("nodeTransType");
						  count++;
					  }*/
					  
					     document.getElementById("cellIdTr").checked = true;
					     document.getElementById("cellNameTr").checked = true;
						 
						 document.getElementById("siteId").checked = true;
					     document.getElementById("siteNameTrans").checked = true;
							
					     
					  
					  transactionTableOption += "N";
						
				  }
				  if(cabinetTable.checked == true){
					  
					  if(document.getElementById("cellIdTr").checked = true){
						  arrayColumn.push("Cell ID");
						  arrayColumnGetter.push("cellId");
						  count++;
					  }
					  if(document.getElementById("cellNameTr").checked = true){
						  arrayColumn.push("Cell Name");
						  arrayColumnGetter.push("cellName");
						  count++;
					  }
					  if(document.getElementById("nodeIdTr").checked = true){
						  arrayColumn.push("Node ID");
						  arrayColumnGetter.push("nodeId");
						  count++;
					  }
					  if(document.getElementById("nodeNameTr").checked = true){
						  arrayColumn.push("Node Name");
						  arrayColumnGetter.push("nodeName");
						  count++;
					  }
					  if(document.getElementById("siteId").checked = true){
						  arrayColumn.push("Site ID");
						  arrayColumnGetter.push("siteId");
						  count++;
					  }
					  if(document.getElementById("siteNameTrans").checked = true){
						  arrayColumn.push("Site Name");
						  arrayColumnGetter.push("siteName");
						  count++;
					  }
					  
					  
					  if(document.getElementById("cabinetPosition").checked = true){
						  arrayColumn.push("Cabinet Position");
						  arrayColumnGetter.push("cabinetPosition");
						  count++;
					  }
					  if(document.getElementById("cabinetModel").checked = true){
						  arrayColumn.push("Cabinet Model");
						  arrayColumnGetter.push("cabinetModel");
						  count++;
					  }
					  if(document.getElementById("cabinetSNo").checked = true){
						  arrayColumn.push("Cabinet SNo.");
						  arrayColumnGetter.push("cabinetSNo");
						  count++;
					  }
					/*  if(document.getElementById("cabinetTransType").checked = true){
						  arrayColumn.push("Cabinet Trans Type");
						  arrayColumnGetter.push("cabinetTransType");
						  count++;
					  }*/
					  
					     document.getElementById("cellIdTr").checked = true;
					     document.getElementById("cellNameTr").checked = true;
						 
						 document.getElementById("siteId").checked = true;
					     document.getElementById("siteNameTrans").checked = true;
							
					     	
						 document.getElementById("nodeIdTr").checked = true;
						 document.getElementById("nodeNameTr").checked = true;
						
					  
					  transactionTableOption += "Ca";
					
				  }
				  if(boardTable.checked == true){
					  
					  if(document.getElementById("cellIdTr").checked = true){
						  arrayColumn.push("Cell ID");
						  arrayColumnGetter.push("cellId");
						  count++;
					  }
					  if(document.getElementById("cellNameTr").checked = true){
						  arrayColumn.push("Cell Name");
						  arrayColumnGetter.push("cellName");
						  count++;
					  }
					  if(document.getElementById("nodeIdTr").checked = true){
						  arrayColumn.push("Node ID");
						  arrayColumnGetter.push("nodeId");
						  count++;
					  }
					  if(document.getElementById("nodeNameTr").checked = true){
						  arrayColumn.push("Node Name");
						  arrayColumnGetter.push("nodeName");
						  count++;
					  }
					  if(document.getElementById("siteId").checked = true){
						  arrayColumn.push("Site ID");
						  arrayColumnGetter.push("siteId");
						  count++;
					  }
					  if(document.getElementById("siteNameTrans").checked = true){
						  arrayColumn.push("Site Name");
						  arrayColumnGetter.push("siteName");
						  count++;
					  }
					  
					  
					  if(document.getElementById("cabinetPosition").checked = true){
						  arrayColumn.push("Cabinet Position");
						  arrayColumnGetter.push("cabinetPosition");
						  count++;
					  }
					  if(document.getElementById("cabinetModel").checked = true){
						  arrayColumn.push("Cabinet Model");
						  arrayColumnGetter.push("cabinetModel");
						  count++;
					  }
					  if(document.getElementById("cabinetSNo").checked = true){
						  arrayColumn.push("Cabinet SNo.");
						  arrayColumnGetter.push("cabinetSNo");
						  count++;
					  }
					  
					  if(document.getElementById("boardPosition").checked = true){
						  arrayColumn.push("Board Position");
						  arrayColumnGetter.push("boardPosition");
						  count++;
					  }
					  if(document.getElementById("boardModel").checked = true){
						  arrayColumn.push("Board Model");
						  arrayColumnGetter.push("boardModel");
						  count++;
					  }
					  if(document.getElementById("boardSNo").checked = true){
						  arrayColumn.push("Board SNo.");
						  arrayColumnGetter.push("boardSNo");
						  count++;
					  }
					 /* if(document.getElementById("boardTransType").checked = true){
						  arrayColumn.push("Board Trans Type");
						  arrayColumnGetter.push("boardTransType");
						  count++;
					  }*/
					  
					  transactionTableOption += "B";
					  
					    document.getElementById("siteId").checked = true;
						document.getElementById("siteNameTrans").checked = true;
						
						document.getElementById("cellIdTr").checked = true;
						document.getElementById("cellNameTr").checked = true;
						
						
						document.getElementById("nodeIdTr").checked = true;
						document.getElementById("nodeNameTr").checked = true;
						
				  }
				  if(antennaTable.checked == true){
					  
					  if(document.getElementById("antennaId").checked = true){
						  arrayColumn.push("Antenna ID");
						  arrayColumnGetter.push("antennaId");
						  count++;  
					  }
					  if(document.getElementById("antennaModel").checked = true){
						  arrayColumn.push("Antenna Model");
						  arrayColumnGetter.push("antennaModel");
						  count++;
					  }
					  if(document.getElementById("antennaSNo").checked = true){
						  arrayColumn.push("Antenna SNo.");
						  arrayColumnGetter.push("antennaSNo");
						  count++;
					  }
					/*  if(document.getElementById("antennaTransType").checked = true){
						  arrayColumn.push("Antenna Trans Type");
						  arrayColumnGetter.push("antennaTransType");
						  count++;
					  }*/
					  
					  transactionTableOption += "A";
						
				  }
				  if(hostTable.checked == true){
					 
					  if(document.getElementById("hostVersion").checked = true){
						  arrayColumn.push("Host Version");
						  arrayColumnGetter.push("hostVersion");
						  count++;
					  }
					/*  if(document.getElementById("hostVersionTrans").checked = true){
						  arrayColumn.push("Host Version Trans");
						  arrayColumnGetter.push("hostVersionTrans");
						  count++;
					  }*/
					  
					  transactionTableOption += "H";
					 
				  }
				  if(cabinetTable.checked == false && boardTable.checked == false && antennaTable.checked == false && hostTable.checked == false && cellTable.checked == false && nodeTable.checked == false && siteTable.checked == false){
					  count = 10;
					      //arrayColumn.push("Date");
					      //arrayColumn.push("Trans Source");
					      //arrayColumn.push("Trans Type");
					      //arrayColumn.push("Trans ID");
					      
					      arrayColumn.push("Cell ID");
					      arrayColumn.push("Cell Name");
						  //arrayColumnGetter.push("cellName");
					  	  //arrayColumn.push("Cell Trans Type");
						  //arrayColumnGetter.push("cellTransType");
						
				 		  arrayColumn.push("Site ID");
						  //arrayColumnGetter.push("siteId");
						  arrayColumn.push("Site Name");
						  //arrayColumnGetter.push("siteName");
						 
				  	      arrayColumn.push("Node ID");
						  //arrayColumnGetter.push("nodeId");
						  arrayColumn.push("Node Name");
						  //arrayColumnGetter.push("nodeName");
						  arrayColumn.push("Node SNo.");
						  //arrayColumnGetter.push("nodeSNo");
						  arrayColumn.push("Node Type");
						  //arrayColumnGetter.push("nodeType");
						 // arrayColumn.push("Node Trans Type");
						  //arrayColumnGetter.push("nodeTransType");
						  
						  transactionTableOption += "CeSN";
						  
						   
							document.getElementById("cellIdTr").checked = true;
							document.getElementById("cellNameTr").checked = true;
							//document.getElementById("cellTransType").checked = true;
							
							document.getElementById("nodeIdTr").checked = true;
							document.getElementById("nodeNameTr").checked = true;
							document.getElementById("nodeSNo").checked = true;
							document.getElementById("nodeTypeTr").checked = true;
							//document.getElementById("nodeTransType").checked = true;
						
							 document.getElementById("siteId").checked = true;
							 document.getElementById("siteNameTrans").checked = true;
								
				  
				  }
				  console.log("the columns array "+arrayColumn);
				   
				  //constracting table header
				  $("#head_Trans").children().remove();
				  var itemRow = "<tr class='header'>";
				 
				  //itemRow= itemRow + "<th><span></span></th>";
				  for (i = 0; i < arrayColumn.length; i++){
		        	console.log("********passes the loop of arrayColumn");
		        	
 		 			itemRow= itemRow + "<th>" + arrayColumn[i] +"<li class='filter-dropdown dropdown'><button class='almgrid-filter' data-toggle='dropdown'><i class='fa fa-list almgrid-filter-i' aria-hidden='true'></i></button><ul class='dropdown-menu dropdown-menu-right filter-dropdown-ul' Style = 'border: 1px solid red'></ul></li></th>";  
 		 			
				  
				  } 
				  itemRow =itemRow + "</tr>"+ "<tr>";
				  for (i = 0; i < arrayColumn.length; i++){
					  itemRow = itemRow +" <th><input type='text' class='almgrid-search' placeholder='Search'></th>"
				  }  
				  itemRow =itemRow + "</tr>";
			      //$("#Ttable > thead").append(itemRow);
			      if(arrayColumn.length >0) {
			    	  table3.style.display = "block";
			      }
			      
			   
			     // console.log("******** transactionTableOption before"+ transactionTableOption);
   		    	//});
   		    	
   		    	 console.log("******** transactionTableOption after "+ transactionTableOption);
   		    	  
   		    	  
   		    	   
   		       }
		        
			       
		        var itemCode = $("#icode").val();
				var n = itemCode.indexOf(":");
			    var itemCodeID = itemCode.substring(0, n);
			    console.log("itemCodeID is: "+itemCodeID);

			    var item = $("#icode").val();
		        var codeItemID = item.split(":");
		        codeItemID  = codeItemID[1];

			    
			    var wareID = $("#warcode").val();
			    /*var j = warehouse.indexOf(";");
			    console.log(warehouse);
			    var wareID = warehouse.substring(0, j);
			    console.log("wareID is : "+wareID);*/
			    
			    var supplier = $("#supcode").val();
			    var x = supplier.indexOf(";");
			    var supplierID = supplier.substring(0, x);
			    
			    /*var node = $("#nodeId").val();
			    var z = node.indexOf(";");
			    var nodeID = node.substring(0, z);*/
			    
			    var nodeID = $("#nodeId").val();
			    
			    //console.log('nodeID is '+ nodeID);
			    
			   /* var cell = $("#cellId").val();
			    var m = cell.indexOf(";");
			    var cellID = cell.substring(0, m);
			   */
			   var cellID = $("#cellId").val();
			    //console.log('cellID is '+ cellID);
			    
			    var site = $("#site").val();
			    var n = site.indexOf(";");
			    var siteID = site.substring(0, n);
			    
			    //console.log('siteID is '+ siteID);
			    
			    var siteeId = $("#site").val();
			    var b = siteeId.indexOf(";");
			    var siteeID = siteeId.substring(0, b);

			    var serialNum = $("#sncode").val();
				var x = serialNum.indexOf(";");
				var serialNumber = serialNum.substring(0, x);


				//Generate google maps data
			    $.ajax({
		  		 	  type: "GET",
		  		 	  contentType: "application/json; charset=utf-8",
		  		 	  url: '${pageContext.request.contextPath}/GetAlcDataGIS',
		  		 	  data: {
		  				 	
		  				 	"checkedValue":Checkvalue,	
		  				 	"startDate" : $("#startdate").val(),
						    "endDate" : $("#enddate").val(),							    
						    "itemCode": itemCodeID,
						    "warehouse" : wareID,
						    "supplier" :supplierID,
						    "voucherType" : $("#voucherType").val(),
						    "voucherNb" : $("#vncode").val(),
						    "transactionTableOption": transactionTableOption,
						    "siteId" : siteID,
						    "siteName" : $("#siteName").val(),
						    "itemPartNo": $("#itemPartNo").val(),
						    "itemModel" :  $("#itemModel").val(),
						    "serialNb" :serialNumber,					    		
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
			  	if(checkBox1.checked == true && checkBox2.checked == false && checkBox3.checked == false){
					  	//console.log("data.alcBalanceList is: "+data.alcBalanceList);	 	
			  		 	alcBalanceValSetColor(data.alcBalanceList,map);
					}
			  	else {

			  		$("#firstValRange").attr('disabled', true);
				    $("#secondValRange").attr('disabled', true);
				    $("#thirdValRange").attr('disabled', true);
				    $("#fourthValRange").attr('disabled', true);
				 	$("#fifthValRange").attr('disabled', true);
				 	var element = document.getElementById("selectUnselect");
				 	if (element.innerHTML == "Unselect All"){
					 	element.innerHTML = " ";
					 }
					  
				  	}
		  			
		  		 },
		  		 error: function(result) {
		  			console.log("Error");
		  		 }
		  		 });
				
			    
		 if(checkBox1.checked == true || checkBox2.checked == true || checkBox3.checked == true){
			 
	     	$.ajax({
					type : "GET",
					contentType: "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/GenerateReport",
					dataType : "json",
					data : {
					    "checkValType": Checkvalue,
					    "item_code": itemCodeID,
					    "startDate" : $("#startdate").val(),
					    "endDate" : $("#enddate").val(),
					    "warehouse" : wareID,
					    "supplier" :supplierID,
					    "voucherType" : $("#voucherType").val(),
					    "voucherNb" : $("#vncode").val(),
					    "transactionTableOption": transactionTableOption,
					    "siteId" : siteID,
					    "siteName" : $("#siteName").val(),
					    "nodeId" : nodeID,
					    //"nodeName" : $("#nodeName").val(),
					    //"nodeType" : $("#nodeType").val(),
					    "cellId" : cellID,
					    //"cellName" : $("#cellName").val(),
					    "itemPartNo": $("#itemPartNo").val(),
					    "itemModel" :  $("#itemModel").val(),
					    
					    	
					},
					success : function(data) {
						 if (data != null) {
	                         var boqArray = [];
		                  //console.log('data is '+ data.AssetlifeCycleArray[0].itemCode);
	                        //console.log('Balance totals is '+ data.valiationShowTotalsBalance);
	                        //console.log('Ledger totals is '+ data.valiationShowTotalsLedger);
	         	     	    boqArray = data.AssetlifeCycleArray;
	         	     	    var totalsBalanceDiv = data.valiationShowTotalsBalance;
	         	     	    var totalsLedgerDiv = data.valiationShowTotalsLedger;
	         	     	    var transTable = [];
	         	     	    var transTable = data.transList;
	         	     	  // If the checkbox is checked, display the output table
                           
	         	     	    var ReportArrayGlobal = [];
	         	     	  
	         	     	
	    					 	
	    				
	    					 	
	    				
	   
					   
				  
		     		        if (checkBox1.checked == true){
		     		        	if (totalsBalanceDiv == 1){
		     		        		div1.style.display = "block";
		     		        		tInQtyBal.value = data.totalInQtyBalance;
		     		        		tInvalueBal.value = data.totalInValueBalance;
		     		        		tOutQtyBal.value = data.totalOutQtyBalance;
		     		        		tOutValBal.value = data.totalOutValueBalance;
		     		        		tBalanceQtyBal.value = data.totalBalanceQtyBalance;
		     		        		//tAccumulatedDeprBal.value = data.totalAccDepBalance;
		     		        		//tNetBalanceValBal.value = data.totalNetBalanceValueBalance;
		     		        		tBalanceValBal.value =  data.totalBalanceValueBalance;
		     		        			
		     		        		
		     		        	}
		     		        	else{
		     		        		div1.style.display = "none";
		     		        	}
		     		        	
		     		        	 //for (var i = 1; i < boqArray.length; i++){
		         	     		 $.each(boqArray, function (i, value) { 
		    					 	ReportArrayGlobal.push({
		    					 		//ID:  boqArray[i].ID,
		    							itemCode: ((boqArray[i].itemCode) == null)? "" : boqArray[i].itemCode,
		    							ItemName: ((boqArray[i].itemName) == null)? "" : boqArray[i].itemName,
		    							ItemModel: ((boqArray[i].itemModel) == null)? "" : boqArray[i].itemModel,
		    							Warehouse: ((boqArray[i].warehouse) == null)? "" : boqArray[i].warehouse,
		    							UOM: ((boqArray[i].uom) == null)? "" : boqArray[i].uom,
		    							OpeningQty: ((boqArray[i].openingQty) == null)? "" : boqArray[i].openingQty,
		    			                OpeningValue: ((boqArray[i].openingValue) == null)? "" : boqArray[i].openingValue,
		    			                InQty: ((boqArray[i].inQty) == null)? "" : boqArray[i].inQty,
		    			                OutQty: ((boqArray[i].outQty) == null)? "" : boqArray[i].outQty,
		    			                BalanceQty: ((boqArray[i].balanceQty) == null)? "" : boqArray[i].balanceQty,
		    			                InValue: ((boqArray[i].inValue) == null)? "" : boqArray[i].inValue,
		    			                OutValue: ((boqArray[i].outValue) == null)? "" : boqArray[i].outValue,
		    			                BalanceValue: ((boqArray[i].balanceValue) == null)? "" : boqArray[i].balanceValue,
		    			               // NetBalanceValue: ((boqArray[i].netBalanceVale) == null)? "" : boqArray[i].netBalanceVale,
		    			               // AccumulatedDepreciation: ((boqArray[i].accumulatedDepreciation) == null)? "" : boqArray[i].accumulatedDepreciation,
		    			                ValuationRate: ((boqArray[i].valuationRate) == null)? "" : boqArray[i].valuationRate,
		    			                
		    					 	});
		    					 	
		         	     		});
		     		        	
		/*     		        	 $("#tbody_balance").children().remove();
		     		           // Fill tables rows from DB
		     		           for (i = 0; i < boqArray.length; i++){
		     		        	  console.log("********passes the loop");
		     		        	 
			     			        var itemRow = "<tr >";
			     			        itemRow= itemRow + "<td>"+(i+1)+"</td>";
			     		 			itemRow =itemRow + "<td name='itm'>" + boqArray[i].itemCode + "</td>";
			     		 			itemRow =itemRow + "<td name='itmName'>" + boqArray[i].itemName+"</td>";
			     					itemRow =itemRow + "<td name='itmModel'>" + boqArray[i].itemModel+"</td>";
			     					itemRow =itemRow + "<td name='warhouse'>" + boqArray[i].warehouse +"</td>";
			     					itemRow =itemRow + "<td name='UOM'>" + boqArray[i].uom +"</td>";
			     					itemRow =itemRow + "<td name='opqty'>"+boqArray[i].openingQty+"</td>";
			     		 		    itemRow =itemRow + "<td name='opvalue'>"+boqArray[i].openingValue+"</td>";
			     		 		    itemRow =itemRow + "<td name='inqty'>"+boqArray[i].inQty+"</td>";
			     					itemRow =itemRow + "<td name='outqty'>"+boqArray[i].outQty+"</td>";
			     					itemRow =itemRow + "<td name='Bqty'>" + boqArray[i].balanceQty +"</td>";
			     		 		    itemRow =itemRow +"<td name='invalue'>" + boqArray[i].inValue +"</td>";
			     					itemRow =itemRow + "<td name='outvalue'>" + boqArray[i].outValue +"</td>";
			     					itemRow =itemRow + "<td name='Bvalue'>"+ boqArray[i].balanceValue+"</td>";
			     					itemRow =itemRow + "<td name='netBvalue'>" + boqArray[i].netBalanceVale +"</td>";
			     					itemRow =itemRow + "<td name='AccDep'>" + boqArray[i].accumulatedDepreciation +"</td>";
			     					itemRow =itemRow + "<td name='Vrate'>" + boqArray[i].valuationRate+"</td>";
			     			        itemRow =itemRow + "</tr>";
			     			        $("#bisotab1 > tbody").append(itemRow);
			     			     }
		     		          */
		     		        }
		     	    
		     		        else if (checkBox2.checked == true){
		     		        	if (totalsLedgerDiv == 1){
		     		        		div2.style.display = "block";
		     		        		tBalanceQtyLed.value = data.totalBalanceQtyLedger;
		     		        		tBalanceValLed.value = data.totalBalanceValueLedger;
		     		        		tAccumulatedDeprLed.value = data.totalAccDepLedger;
		     		        		tNetBalanceValLed.value = data.totalNetBalanceValueLedger;
		     		        		
		     		        		
		     		        	}
		     		        	else{
		     		        		div2.style.display = "none";
		     		        	}
		     		        	//alert('hiiii' + $("#tbody_ledger")find("tr td:first-child").text());
		     		        	//alert($("#tbody_ledger").children());
		     		        	 $("#tbody_Ledger").children().remove();
		     		           // Fill tables rows from DB
		     		           $.each(boqArray, function (i, value) { 
		    					 	ReportArrayGlobal.push({
		    					 		ID:  boqArray[i].ID,
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
		     		           /*for (i = 0; i < boqArray.length; i++){
			     		           //console.log("item Code is: "+boqArray[i].itemCode);
			     		           //console.log("Warehouse is: "+boqArray[i].warehuse);
			     		          
			     			       var itemRow = "<tr >";
			     			        itemRow= itemRow + "<td>"+(i+1)+"</td>";
			     			        itemRow =itemRow + "<td name='date'> "+ boqArray[i].creationDate + "</td>";
			     		 		    itemRow =itemRow + "<td name='itm'>" + boqArray[i].itemCode + "</td>";
			     					itemRow =itemRow + "<td name='itmModel'>" + boqArray[i].itemModel + "</td>";
			     					itemRow =itemRow + "<td name='warhouse'>" + boqArray[i].warehouse +"</td>";
			     					itemRow =itemRow + "<td name='voType'>" + boqArray[i].voucherType +"</td>";
			     					itemRow =itemRow + "<td name='voNb'>" + boqArray[i].voucherNB +"</td>";
			     					itemRow =itemRow + "<td name='UOM'>" + boqArray[i].uom +"</td>";
			     				    itemRow =itemRow + "<td name='qty'>" + boqArray[i].actualQty +"</td>";
			     					itemRow =itemRow + "<td name='inRate'>" + boqArray[i].inComingRate+"</td>";
			     					itemRow =itemRow + "<td name='outRate'>" + boqArray[i].outGoingRate+"</td>";
			     				    itemRow =itemRow + "<td name='AccDep'>" + boqArray[i].accumulatedDepreciation +"</td>";
			     				    itemRow =itemRow + "<td name='netPrice'>" + boqArray[i].netPrice +"</td>";
			     				    itemRow =itemRow + "<td name='Vrate'>" + boqArray[i].valuationRate+"</td>";
			     		 		    itemRow =itemRow + "<td name='Bvalue'>" + boqArray[i].balanceValue+"</td>";
			     		 		    itemRow =itemRow + "<td name='netBvalue'>" + boqArray[i].netBalanceValue +"</td>";
			     		 		    itemRow =itemRow + "<td name='Bqty'>" + boqArray[i].balanceQty +"</td>";
		     		                itemRow =itemRow + "</tr>";
		     			            $("#bisotab2 > tbody").append(itemRow);
		     		           }
		     		        	//alert('After' + $("#tbody_ledger").find("tr td:first-child").text());
                               */
		     		        } 
		     		        
		     		        else if (checkBox3.checked == true){
		     		    	   //$('#myModal').modal('show');
		     		    	  //$("#almgrid-filter").data("kendoGrid").dataSource.filter({});
		     		    	 var transArray = {};
		     		    	 $.each(transTable, function (i, value) { 
		     		    		  transArray = {};
		     		    		        transArray.ID =  i;
		    					 		transArray.date = ((transTable[i].creationDate) == null)? "" :transTable[i].creationDate;
		    					 		transArray.transSource = ((transTable[i].transSource) == null)? "" :transTable[i].transSource;
		    					 		transArray.transType = ((transTable[i].transType) == null)? "" :transTable[i].transType;
		    					 		transArray.transID = ((transTable[i].transID) == null)? "" :transTable[i].transID;
		    					 		transArray.activeRecord = ((transTable[i].activeRecord) == null)? "" :transTable[i].activeRecord;
		    					 		
		    					 	
		    					 	
		    					 	 if(document.getElementById("cellIdTr").checked == true){
		    					 		 
		    					 		  transArray.cellId = (( transTable[i].cellId) == null)? "" : transTable[i].cellId;
		    					 		  console.log("the cell "+cellId)
				    				 }
				    			     if(document.getElementById("cellNameTr").checked == true){
				    			    	  transArray.cellName = ((transTable[i].cellName) == null)? "" : transTable[i].cellName;
				    				 }
				    			     
				    			     if(document.getElementById("nodeIdTr").checked == true){
				    			    	  transArray.nodeId = ((transTable[i].nodeId) == null)? "" :transTable[i].nodeId;
			    					  }
			    					  if(document.getElementById("nodeNameTr").checked == true){
			    						  transArray.nodeName = ((transTable[i].nodeName) == null)? "" : transTable[i].nodeName;
			    					  }
			    					  if(document.getElementById("nodeSNo").checked == true){
			    						  transArray.nodeSNo = ((transTable[i].nodeSNo) == null)? "" : transTable[i].nodeSNo;
			    					  }
			    					  if(document.getElementById("nodeTypeTr").checked == true){
			    						  transArray.nodeType = ((transTable[i].nodeType) == null)? "" : transTable[i].nodeType;
			    					  }
			    					  
			    					  if(document.getElementById("siteId").checked == true){
			    						  transArray.siteId = ((transTable[i].siteId) == null)? "" : transTable[i].siteId;
			    					  }
			    					  if(document.getElementById("siteNameTrans").checked == true){
			    						  transArray.siteName = ((transTable[i].siteName) == null)? "" : transTable[i].siteName;
			    					  }
			    					  
			    					  
			    					  if(document.getElementById("cabinetPosition").checked == true){
			    						  transArray.cabinetPosition = ((transTable[i].cabinetPosition) == null)? "" : transTable[i].cabinetPosition;
			    					  }
			    					  if(document.getElementById("cabinetModel").checked == true){
			    						  transArray.cabinetModel = ((transTable[i].cabinetModel) == null)? "" : transTable[i].cabinetModel;
			    					  }
			    					  if(document.getElementById("cabinetSNo").checked == true){
			    						  transArray.cabinetSNo = ((transTable[i].cabinetSNo) == null)? "" : transTable[i].cabinetSNo;
			    					  }
			    					  
			    					  if(document.getElementById("boardPosition").checked == true){
			    						  transArray.boardPosition = ((transTable[i].boardPosition) == null)? "" : transTable[i].boardPosition;
			    					  }
			    					  if(document.getElementById("boardModel").checked == true){
			    						  transArray.boardModel = ((transTable[i].boardModel) == null)? "" : transTable[i].boardModel;
			    					  }
			    					  if(document.getElementById("boardSNo").checked == true){
			    						  transArray.boardSNo = ((transTable[i].boardSNo) == null)? "" : transTable[i].boardSNo;
			    					  }
			    					  
			    					  if(document.getElementById("antennaId").checked == true){
			    						  transArray.antennaId = ((transTable[i].antennaId) == null)? "" : transTable[i].antennaId;
			    					  }
			    					  if(document.getElementById("antennaModel").checked == true){
			    						  transArray.antennaModel = ((transTable[i].antennaModel) == null)? "" : transTable[i].antennaModel;
			    					  }
			    					  if(document.getElementById("antennaSNo").checked == true){
			    						  transArray.antennaSNo = ((transTable[i].antennaSNo) == null)? "" : transTable[i].antennaSNo;
			    					  }
			    					  if(document.getElementById("antennaTransType").checked == true){
			    						  transArray.antennaTransType = ((transTable[i].antennaTransType) == null)? "" : transTable[i].antennaTransType;
			    					  }
			    						
			    				  
			    					  if(document.getElementById("hostVersion").checked == true){
			    						  transArray.hostVersion = ((transTable[i].hostVersion) == null)? "" : transTable[i].hostVersion;
			    					  }
			    					  if(document.getElementById("hostVersionTrans").checked == true){
			    						  transArray.hostVersionTrans = ((transTable[i].hostVersionTrans) == null)? "" : transTable[i].hostVersionTrans;
			    					  }
			    				
			    					  ReportArrayGlobal.push(transArray); 
			    					  console.log("the ReportArrayGlobal "+ReportArrayGlobal)
		     		           });
		     		    	 
		     		    	 
		     		    	 
		     		    	 
		     		    	 

	/*	     		           for (i = 0; i < transTable.length; i++){
			     		           //console.log("item Code is: "+boqArray[i].itemCode);
			     		           
			     		           var itemRow = "<tr >";
			     			       itemRow= itemRow + "<td>"+(i+1)+"</td>";
			     			       itemRow =itemRow + "<td name='date'> "+ transTable[i].creationDate+ "</td>";
			     			       itemRow =itemRow + "<td name='transSource'> "+ transTable[i].transSource+ "</td>"; 
			     			       itemRow =itemRow + "<td name='transType'> "+ transTable[i].transType+ "</td>";
			     			       itemRow =itemRow + "<td name='transID'> "+ transTable[i].transID+ "</td>";
			     			       itemRow =itemRow + "<td name='activeRecord'> "+ transTable[i].activeRecord+ "</td>";
			     			     
		    					  
		    					  if(document.getElementById("cellIdTr").checked == true){
		    						  itemRow =itemRow + "<td name='cellIdTr'> "+ transTable[i].cellId+ "</td>";
		    					  }
		    					  if(document.getElementById("cellNameTr").checked == true){
		    						  itemRow =itemRow + "<td name='cellNameTr'> "+ transTable[i].cellName+ "</td>"; 
		    					  }
		    					  /*if(document.getElementById("cellTransType").checked == true){
		    						  itemRow =itemRow + "<td name='cellTransType'> "+ transTable[i].transType+ "</td>";
		    					  }*/
		    					 
		    					  
/*		    					  if(document.getElementById("nodeIdTr").checked == true){
		    						  itemRow =itemRow + "<td name='nodeId'> "+ transTable[i].nodeId+ "</td>";
		    					  }
		    					  if(document.getElementById("nodeNameTr").checked == true){
		    						  itemRow =itemRow + "<td name='nodeName'> "+ transTable[i].nodeName+ "</td>";
		    					  }
		    					  if(document.getElementById("nodeSNo").checked == true){
		    						  itemRow =itemRow + "<td name='nodeSNo'> "+ transTable[i].nodeSNo+ "</td>";
		    					  }
		    					  if(document.getElementById("nodeTypeTr").checked == true){
		    						  itemRow =itemRow + "<td name='nodeType'> "+ transTable[i].nodeType+ "</td>";
		    					  }
		    					  /*if(document.getElementById("nodeTransType").checked == true){
		    						  itemRow =itemRow + "<td name='nodeTransType'> "+ transTable[i].nodeTransType+ "</td>";  
		    					  }*/
		    						
		    				
/*		    					  if(document.getElementById("siteId").checked == true){
		    						  itemRow =itemRow + "<td name='siteId'> "+ transTable[i].siteId+ "</td>";
		    					  }
		    					  if(document.getElementById("siteNameTrans").checked == true){
		    						  itemRow =itemRow + "<td name='siteName'> "+ transTable[i].siteName+ "</td>";
		    					  }
		    					  
		    					  
		    					  if(document.getElementById("cabinetPosition").checked == true){
		    						  itemRow =itemRow + "<td name='cabinetPosition'> "+ transTable[i].cabinetPosition+ "</td>"; 
		    					  }
		    					  if(document.getElementById("cabinetModel").checked == true){
		    						  itemRow =itemRow + "<td name='cabinetModel'> "+ transTable[i].cabinetModel+ "</td>";
		    					  }
		    					  if(document.getElementById("cabinetSNo").checked == true){
		    						  itemRow =itemRow + "<td name='cabinetSNo'> "+ transTable[i].cabinetSNo+ "</td>";
		    					  }
		    					  /*if(document.getElementById("cabinetTransType").checked == true){
		    						  itemRow =itemRow + "<td name='cabinetTransType'> "+ transTable[i].cabinetTransType+ "</td>";
		    					  }*/
		    					
		    				  
		    					  
/*		    					  if(document.getElementById("boardPosition").checked == true){
		    						  itemRow =itemRow + "<td name='boardPosition'> "+ transTable[i].boardPosition+ "</td>";
		    					  }
		    					  if(document.getElementById("boardModel").checked == true){
		    						  itemRow =itemRow + "<td name='boardModel'> "+ transTable[i].boardModel+ "</td>";
		    					  }
		    					  if(document.getElementById("boardSNo").checked == true){
		    						  itemRow =itemRow + "<td name='boardSNo'> "+ transTable[i].boardSNo+ "</td>";
		    					  }
		    					  /*if(document.getElementById("boardTransType").checked == true){
		    						  itemRow =itemRow + "<td name='boardTransType'> "+ transTable[i].boardTransType+ "</td>";
		    					  }*/
		    						
		    				 
		    					  
/*		    					  if(document.getElementById("antennaId").checked == true){
		    						  itemRow =itemRow + "<td name='antennaId'> "+ transTable[i].antennaId+ "</td>";  
		    					  }
		    					  if(document.getElementById("antennaModel").checked == true){
		    						  itemRow =itemRow + "<td name='antennaModel'> "+ transTable[i].antennaModel+ "</td>"; 
		    					  }
		    					  if(document.getElementById("antennaSNo").checked == true){
		    						  itemRow =itemRow + "<td name='antennaSNo'> "+ transTable[i].antennaSNo+ "</td>";
		    					  }
		    					  if(document.getElementById("antennaTransType").checked == true){
		    						  itemRow =itemRow + "<td name='antennaTransType'> "+ transTable[i].antennaTransType+ "</td>";
		    					  }
		    						
		    				  
		    					  if(document.getElementById("hostVersion").checked == true){
		    						  itemRow =itemRow + "<td name='hostVersion'> "+ transTable[i].hostVersion+ "</td>";
		    					  }
		    					  if(document.getElementById("hostVersionTrans").checked == true){
		    						  console.log(" PASS HOST");
		    						  itemRow =itemRow + "<td name='hostVersionTrans'> "+ transTable[i].hostVersionTrans+ "</td>"; 
		    					  }
		    					 
		    				  
		    					 

		     		                 
		     		           
		     		           itemRow =itemRow + "</tr>";
	     			           $("#bisotab3 > tbody").append(itemRow);
	     		           }
		     		    	 
		     		    	 
		     		    	 
		     		    	 // }	 
		     		    	 
*/		     		    	 
		     		    	 
		     		    	 


		     		       }
		     		       
		     		     /*  $("#" + table).find(".filter-dropdown-ul").each(function () {
	          			          $(this).empty();
	          			          console.log("******** emptyyyyy");
	          			      });*/
	          			      
		     		       //// gritable
		     		       console.log("start drawing");
                            var almgrid = new AlmgridTable({
						           tableId: table,
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

				          			            var itemRow = "";

				          			            var sumBalanceQtyCol = 0,sumInQtyCol = 0, sumInValueCol = 0, sumOutQtyCol = 0, sumOutValueCol = 0, sumBalanceValueCol = 0, sumAccumulatedDepreciationCol = 0, sumNetBalanceValueCol = 0 ;
				          						var sumBalanceQtyLed = 0, sumBalanceValLed = 0, sumAccumulatedDeprLed = 0, sumNetBalanceValLed = 0;
				          						var column;
				          			            for (var i = 0; i < dataArray.length; i++) {
				          							 itemRow += "<tr class='filterRows'>";
				          			               for (var j = 0; j < ArrayKeys.length; j++) {
				          			               	  column = ArrayKeys[j];
				          			                 // console.log("Table column name "+ArrayKeys[j]);
				          			                   itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>";

				          			                     if ( tableId == "Btable"){
				          			                       if( column  == "InQty"){
				          			                    	   sumInQtyCol += dataArray[i][ArrayKeys[j]];
				          			                       	  
				          			                       }
				          			                       if( column  == "InValue"){
				          			                    	   sumInValueCol += dataArray[i][ArrayKeys[j]];
				          			                       	  
				          			                       }
				          			                       if( column  == "OutQty"){
				          			                    	   sumOutQtyCol += dataArray[i][ArrayKeys[j]];
				          			                       	  
				          			                       }
				          			                       if( column  == "OutValue"){
				          			                    	   sumOutValueCol += dataArray[i][ArrayKeys[j]];
				          			                       	   
				          			                       }
				          			                       if( column  == "BalanceQty"){
				          			                    	   sumBalanceQtyCol += dataArray[i][ArrayKeys[j]];
				          			                       	   
				          			                       }
				          			                       if( column  == "BalanceValue"){
				          			                    	   sumBalanceValueCol += dataArray[i][ArrayKeys[j]];
				          			                       	   
				          			                       }
				          			                       /*if( column  == "AccumulatedDepreciation"){
				          			                    	   sumAccumulatedDepreciationCol += dataArray[i][ArrayKeys[j]];
				          			                       	   
				          			                       }
				          			                       if( column  == "NetBalanceValue"){
				          			                    	   sumNetBalanceValueCol += dataArray[i][ArrayKeys[j]];
				          			                       	   
				          			                       }*/
				          			                     }
				          			                     else if ( tableId == "Ltable"){
					          			                     
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
				          							 itemRow += "</tr>";
				          							}

				          			        
				          			        	 if ( tableId == "Btable"){
				          			        		 if (dataArray.length > 1) {
				          			        			div1.style.display = "block";
					          			        	    tInQtyBal.value = sumInQtyCol;
				          			    	            console.log("in ACL tInQtyBal "+tInQtyBal.value);
				          			    		        tInvalueBal.value = sumInValueCol;
				          			    		        tOutQtyBal.value = sumOutQtyCol;
				          			    		        tOutValBal.value = sumOutValueCol;
				          			    		        tBalanceQtyBal.value = sumBalanceQtyCol;;
				          			    		        //tAccumulatedDeprBal.value = sumAccumulatedDepreciationCol;
				          			    		       // tNetBalanceValBal.value = sumNetBalanceValueCol;
				          			    		        tBalanceValBal.value = sumBalanceValueCol ;
				          			        	    }else{
				    		     		        		div1.style.display = "none";
				    		     		        	}
					    		     		        console.log("Inside if condition of balance table")
					          			        	    
				          			        	 }
				          			        	 else if ( tableId == "Ltable"){ 
				          			        		 if (dataArray.length > 1) {
				          			        			div2.style.display = "block";
				          			        		    tBalanceQtyLed.value = sumBalanceQtyCol;
						     		        		    tBalanceValLed.value = sumBalanceValueCol;
						     		        		    tAccumulatedDeprLed.value = sumAccumulatedDepreciationCol;
						     		        		    tNetBalanceValLed.value = sumNetBalanceValueCol;
					          			        	 
					          			        	 }else{
					 		     		        		div2.style.display = "none";
					 		     		        		}
				          			         }


					          			          // }
				          					
				          			
				          			
				          	           // $(tableBody).append(itemRow);
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
	          			      
	          			    /*       			      /// search filter total changes
	          			    $("#" + table).on("keyup change clear", ".almgrid-search", function () {
	          		     		console.log("In the almgrid-search");
	          		     		var arrays = [];
	          		     		$("#" + table).eq(0).find('tr').each((r,row) => arrays.push($(row).find('td,th').map((c,cell) => $(cell).text()).toArray()));
	          		           console.log("in ACL "+arrays);
	          		           console.log("in ACL arrays "+arrays[2][0]);
	          		           console.log("in ACL arrays.lenght "+arrays.length);
	          		         
	          		           
	          		           
	          		           /// total balance after filtring 
	          		         if (checkBox1.checked == true){
			     		        	if (arrays.length > 3){
			     		        		div1.style.display = "block";
			     		        		
			     		        	    tInQtyBal.value = sum(arrays,7);
			     		        	    console.log("in ACL tInQtyBal "+tInQtyBal.value);
			     		        		tInvalueBal.value = sum(arrays,10);
			     		        		tOutQtyBal.value = sum(arrays,8);
			     		        		tOutValBal.value = sum(arrays,11);
			     		        		tBalanceQtyBal.value = sum(arrays,9);
			     		        		tAccumulatedDeprBal.value = sum(arrays,14);
			     		        		tNetBalanceValBal.value = sum(arrays,13);
			     		        		tBalanceValBal.value =  sum(arrays,12);
			     		        		
			     		        		
			     		        	}
			     		        	else{
			     		        		div1.style.display = "none";
			     		        	}
			     		        	
	          		         }
	          		       /// total ledger after filtring 
	          		       else if (checkBox2.checked == true){
	          		    	      if (arrays.length > 3){
		     		        		div2.style.display = "block";
		     		        		tBalanceQtyLed.value = sum(arrays,14);
		     		        		tBalanceValLed.value = sum(arrays,12);
		     		        		tAccumulatedDeprLed.value = sum(arrays,9);
		     		        		tNetBalanceValLed.value = sum(arrays,13);
		     		        		
		     		        		
		     		        	}
		     		        	else{
		     		        		div2.style.display = "none";
		     		        	}
	          		       }
	          		           
	          		       function sum(array, index){
	          	             
	          		    	 if (toString.call(array) !== "[object Array]")
	          		    	    return false;
	          		    	      
	          		    	            var total =  0;
	          		    	         
	          		    	            for(var i=2;i<array.length;i++)
	          		    	              {                  
	          		    	                if(isNaN(array[i][index])){
	          		    	                continue;
	          		    	                 }
	          		    	                  total += Number(array[i][index]);
	          		    	               }
	          		    	             return total;
	          		     }
	          		      
	       			         
	       			          
	          			    });
		     		     */  
                           
						}
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

 }
		 else{alert("Please choose an option: "+ "\r\n"+"Balance, Ledger, Network, or Transaction");
		 }
	}); 

/*	     	$("#" + table).on("keyup change clear", ".almgrid-search", function () {
	     		console.log("In the almgrid-search");
	     		 var columnNumber = 1;
	             var dataArrayKeys = Object.keys(ReportArrayGlobal[0]);
	   
	             filteredArray = dataArray;

	             $("#" + table + " .almgrid-search").each(function () {
	                 var FilterValue = $(this).val().toUpperCase();
	                 var KeyName = dataArrayKeys[columnNumber];


	                 filteredArray = getFilteredArray(filteredArray, KeyName, FilterValue);
	                 //almgrid.drawTableGrid(tableId, filteredArray);
	                 console.log("in ACL "+filteredArray);
	                 columnNumber++;
	             });
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		//  var th = $("#" + table +' th').eq($(this).index());
	     		//  console.log(" the th is "+ th.text());
	     		// var colAIndex =  $(this).index();
	     		// console.log(" the column is "+ colAIndex);
	     		
	     	//var rowIndex = $(this).parent().parent().children().index($(this).parent());
            //var colIndex = $(this).parent().children().index($(this));
            
            // var colIdx = grid.select().closest("td").index();  
            // var columnheader= this.options.columns[colIdx].title;  
            //var colIndex =  $(this).closest("td").index();
            //  console.log(" the column is "+ colIndex);
	     	 
            
            /* var total_qty = 0;
	     	    var $td = $(this).closest('td');
	     	    var col = $td.parent().children("td").index($td);
	     	   console.log(" the column is"+col);
	     	    $('table tbody tr').each(function() {
	     	        var $td = $(this).find('td:eq('+col+')');
	     	        var qty = $td.find('input').val();
	     	        if(qty != '') total_qty += qty;
	     	    });
	     	    console.log(total_qty);
	     		
	     		*/
	     		//var colIdx =  $(this).closest("td");
	     		// console.log(" the column is "+ colIdx);
	     		 
	     		//var currentRow=$(this).closest("tr"); 
	     		//console.log(" the column is "+ currentRow);
	           // var col1=currentRow.find("td:eq(0)").text();
	           // console.log(" the column is "+ col1);
	            
	     		
	     		/*var rows =  $(this).table.getElementsByTagName('tr'),
	     		    i, j, cells, customerId;
	     		 console.log(" the rows is "+ rows);
	     		for (i = 0, j = rows.length; i < j; ++i) {
	     		    cells = rows[i].getElementsByTagName('td');
	     		    if (!cells.length) {
	     		        continue;
	     		    }
	     		    customerId = cells[0].innerHTML;
	     		}*/
	     		
	    // 	});
	     	
	
	//////////////export data to excel

   /* document.getElementById('export').onclick=function(){
        var tableId= document.getElementById('bisotab1').id;
        htmlTableToExcel(tableId, filename = '');
    }


        var htmlTableToExcel= function exportTableToExcel(tableID, filename = ''){
	    var downloadLink;
	    var dataType = 'application/vnd.ms-excel';
	    var tableSelect = document.getElementById(tableID);
	    var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
	    
	    // Specify file name
	    filename = filename?filename+'.xls':'excel_data.xls';
	    
	    // Create download link element
	    downloadLink = document.createElement("a");
	    
	    document.body.appendChild(downloadLink);
	    
	    if(navigator.msSaveOrOpenBlob){
	        var blob = new Blob(['\ufeff', tableHTML], {
	            type: dataType
	        });
	        navigator.msSaveOrOpenBlob( blob, filename);
	    }else{
	        // Create a link to the file
	        downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
	    
	        // Setting the file name
	        downloadLink.download = filename;
	        
	        //triggering the function
	        downloadLink.click();
	    }
	}
		 
	///////////////////// create pdf
    jQuery("#pdf").on('click', function (event) { 
        exportTableToCSV.apply(this, [jQuery('.dataTable'), 'pdf']);
        window.location.reload();
    });
       

          //  $("#pdf").click(function() {
            	function exportTableToCSV($table, filename) {
            	    
                    var $rows = $table.find('tr:has(td),tr:has(th)'),
                
                        // Temporary delimiter characters unlikely to be typed by keyboard
                        // This is to avoid accidentally splitting the actual contents
                        tmpColDelim = String.fromCharCode(11), // vertical tab character
                        tmpRowDelim = String.fromCharCode(0), // null character
                
                        // actual delimiter characters for CSV format
                        colDelim = '","',
                        rowDelim = '"\r\n"',
                
                        // Grab text from table into CSV formatted string
                        csv = '"' + $rows.map(function (i, row) {
                            var $row = jQuery(row), $cols = $row.find('td,th');
                
                            return $cols.map(function (j, col) {
                                var $col = jQuery(col), text = $col.text();
                
                                return text.replace(/"/g, '""'); // escape double quotes
                
                            }).get().join(tmpColDelim);
                
                        }).get().join(tmpRowDelim)
                            .split(tmpRowDelim).join(rowDelim)
                            .split(tmpColDelim).join(colDelim) + '"',
                
                        
                
                        // Data URI
                        csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);
                        
                        console.log(csv);
                        
                      if (window.navigator.msSaveBlob) { // IE 10+
                        //alert('IE' + csv);
                        window.navigator.msSaveOrOpenBlob(new Blob([csv], {type: "text/plain;charset=utf-8;"}), "csvname.csv")
                      } 
                      else {
                        jQuery(this).attr({ 'download': filename, 'href': csvData, 'target': '_blank' }); 
                      }
                }
          //  });

     */  
 ////////////export to csv   		 
	
     $('#export').on('click',function(){
    	 if (checkBox1.checked == true){
              $("#bisotab1").tableHTMLExport({type:'csv',filename:'sample.csv'});
    	 }
    	 if (checkBox2.checked == true){
             $("#bisotab2").tableHTMLExport({type:'csv',filename:'sample.csv'});
         }
       });	
   ///////////export to pdf  

       $('#pdf').on('click',function(){   
         if (checkBox1.checked == true){
        	 $("#bisotab1").tableHTMLExport({type:'pdf',orientation:'l',filename:'sample.pdf'});
         }
         if (checkBox2.checked == true){
        	 $("#bisotab2").tableHTMLExport({type:'pdf', orientation: 'l',filename:'sample.pdf'});
         }
       });   

/*$('#pdf').on('click',function(){
     $('#bisotab1').tableExport({type:'pdf',
         jspdf: {orientation: 'l',
             format: 'a3',
             margins: {left:10, right:10, top:20, bottom:20},
             autotable: {styles: {fillColor: 'inherit', 
                                  textColor: 'inherit'},
                         tableWidth: 'auto'}
            }
    
    });
}); */ 


/////// print table
 $('#print').on('click',function(){   
     if (checkBox1.checked == true){
    	 $('#bisotab1').printThis();   
     }
     if (checkBox2.checked == true){
    	 $('#bisotab2').printThis();    
     }
   });   
	 

	 
	
 });
 

 

 
 </script>
 
 <script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
 
</html>