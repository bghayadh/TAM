<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>PO Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
  	<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
  	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/PurchaseOrder/PO_BoqPopup.js"></script>
    <style>

    
    #popUpDiv {
position:fixed;
top: 30%;
left: 50%;
background-color:#eeeeee;
border:5px solid #08526d;
width:400px;
height:450px;
margin-left:-150px;
margin-top:-95px;

-moz-border-radius: 16px;
-webkit-border-radius: 16px;
border-radius: 16px;
box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px#000000;

z-index: 9003;
 /*above nine thousand*/}
 
     .btn-pop {
	background-color: #C2CBC0 !important;
	border-color: #C2CBC0;
	3
}

.btn-pop:hover {
	color: #fff;
	background-color: #8696A0 !important;
	border-color: #8696A0 !important;
}

				.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					z-index:9999;
					width: 350px;
	        		}

				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				 margin-top:10px; 
				}
				.dotStatus {
				  height: 15px;
				  width: 15px;
				  background-color: orange;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				  margin-left: 10px;
				  
				}
				.fixed-headerr{
					opacity: 1;
					filter: alpha(opacity=100);
				 	background: #ebf2ef;
				  	position: sticky;
				  	top: 0;
				  	z-index: 15;
				
					}
				
 .hide-row { display:none; }
				
#wrapper {width:100%;display: grid;max-height:100px;grid-gap: 100px;grid-column-gap:10px!important;grid-row-gap:8px!important;display:flex;
			grid-template-rows:1fr 1fr!important;grid-template-columns:1fr 1fr 1fr!important;
  					
					}					
 
#one {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#two {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#three {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#four {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#five {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#six {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}
				
.container{min-width:100%;}	

.divv1{ text-align:center;font-family: cursive;color:#DCF8C6;border-radius: 25px 25px 0px 0px!important; 
 border-style: groove;border-width: 2px;border-color: #4B8C8C; box-shadow: 5px 5px 5px rgb(75,140,140);} 

.divv2{text-align:center;justify-content: center;background-color: lightgray;border-radius: 0px 0px 25px 25px!important;font-size:13px;font-family: cursive;  
border-style: groove;border-width: 2px;border-color: #4B8C8C;box-shadow: 5px 5px 5px rgb(75,140,140);border-top:none;} 	
 	
 tr{height:40px;}	
 table{align:top;margin-left:auto;margin-right:auto;}

 .TD{border-bottom: 1pt solid black;}			
		 
.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 500px;
  overflow : auto;
}
    
.modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
}
  
.display-none{display: none;}
    
button .fa{
        font-size: 16px;
        margin-left: 10px;
}

   
button:focus { outline: none; }

 		
/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .input[type=text] {
    width: 100%;
    margin-top: 0;
  }
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
  .nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }

</style>
    
</head>
<body>

  <c:set var="pg" value="purchasing" scope="session"  />
<jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
     <p></p>
<div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Purchase Order</span>
							<input type="text"    readonly  id="ordcode"  value="${ID}" class="form-control text-input"/>
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />							
						</div>

					</div>
			</div>
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:190px;" class="input-group-text">Status</span>
				<select  id="ordstat" class="form-control">
								<option value="draft" <c:if test = "${ordStatus =='draft'}" > selected </c:if> >Draft</option>
								<option value="approved" <c:if test = "${ordStatus =='approved'}" > selected </c:if>>Approved</option>
								<option value="completed" <c:if test = "${ordStatus =='completed'}" > selected </c:if>>Completed</option>
								<option value="cancelled" hidden<c:if test = "${ordStatus =='cancelled'}" > selected </c:if>>Cancelled</option>
								<option value="closed" <c:if test = "${ordStatus =='closed'}" > selected </c:if>>Closed</option></select>
				</div>							
			</div>
			 <div class="pad col-md-3 hide-row"></div>   
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span style="width:200px;" class="input-group-text">Other PO's</span>
						<input type="text" id="selectnav" value="${selectnav}"
						style="width: 430px" class="form-control text-input" />		
				</div>
			</div>
		</div>
			
							<div  class="col-md-3 text-right"  >
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
						</div> 
			
			</div>

	
			<div class ="row">
			
			
			<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span style="width:190px;" class="input-group-text">Created Date</span>
					<input type="text" id="ordcreatedate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="ordlstmodifdate" readonly value="${lastmodifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
						</div>
				</div>
		</div>
	</div>
	
	 <div class="hide-row col-md-3 pad "></div> 
		
		<div class=" col-md-3 nextprvItems">
			<label id="label-1" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
			  		<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 51px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 51px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>				  		
					</ul>
				</nav>
		</div>
			
			
			
			
			<div class="col-md-3" >
		 		<div class="btn-group pull-right"  style="text-align: right;">
 					
		 			<div class="glyph" style="text-align: right;" > 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/PurchaseOrderListView"'
			        			data-placement="top" title="List View"> 
			        			<i class="fa fa-bars"></i>
			        	</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search">
							<i class="fa fa-search"></i>
						</button>

			        </div>
			         
		        </div>
			</div>
		</div>

	
	</div>		
<p></p>
<div class="container-fluid">

<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: -20px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
       
		<li class="nav-item"><a class="nav-link"
            id="custom-tabs-overview" data-toggle="tab"
            href="#custom-tabs-one-overview" role="tab"
            aria-controls="#custom-tabs-overview" aria-selected="false" style="color: gold;">OVERVIEW</a></li>	
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-discrepancy" data-toggle="tab"
            href="#custom-tabs-one-discrepancy" role="tab"
            aria-controls="#custom-tabs-discrepancy" aria-selected="false" style="color: gold;">DISCREPANCY</a></li>	
            
            
            <li id="Buttons" class="nav-item ml-auto">
            
               <div class="dropdown" Style="display:inline-block;">
	           <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	                 aria-haspopup="true" aria-expanded="false">Actions</button>
	
	            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	            <a class="dropdown-item"  type="button" id="Approvepo">Approve</a>
	             <a class="dropdown-item"  type="button" id="completepo">Complete</a>
	             <a class="dropdown-item"  type="button" id="Closepo" >Close</a>
	             <a class="dropdown-item" type="button" id="Cancelpo" >Cancel</a>
    	          <a class="dropdown-item " id="Newgr" href="${pageContext.request.contextPath}/GoodsRcvFormView?grPOid=${ID}&type=addNewFromPO">Create Goods Received </a>
    	          </div>
    	         
    	        <button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
                       </div>	
                
                        
				<button type="button" id="deleteButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-trash"></i> Delete
				</button>  
		<button type="button"  onclick='window.location.href = "${pageContext.request.contextPath}/PurchaseOrderFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
						</button> 
     </ul>
     
</div>
</div>

</div>

<div class="container-fluid">
<div class="tab-content tab-pane fade show active" id="custom-tabs-one-tabContent">
<div role="tabpanel" class="tab-pane fade show active" id="custom-tabs-one-home"  aria-labelledby="custom-tabs-one-home-tab">
<p></p>
    
    <div class="row">
			<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span style="width:120px;" class="input-group-text">Supplier ID</span>
							<input type="text" id="ordsuppid" value="${supplier}"  style="width:500px; height: 37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
			
			
			<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span style="width:140px;" class="input-group-text">Order Date</span>
					<input type="text" id="ordorderdate" value="${orderedDate}" style="width:500px;" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
					   <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker4" data-target-input="nearest">
					<span style="width:140px;" class="input-group-text">Delivery Date</span>
					<input type="text" id="orddeliverydate" value="${delivereyDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
					   <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
			</div>
		</div>
		
		
		<div class="col-md-4">
			<div class="input-group-prepend">
					<span style="width:170px;" class="input-group-text">Supplier Name</span>
					<input type="text" id="ordsuppname" value="${ordsupplierName}" style="width:500px"; class="form-control text-input"/>
				</div>							
		</div>
			
		<div class="col-md-4">
			<div class="input-group-prepend">
					<span style="width:130px;" class="input-group-text">Supplier Address</span>
					<input type="text" id="ordsuppaddress" value="${supplierAddress}"  class="form-control text-input"/>
			</div>							
		</div>
		
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span style="width:170px;" class="input-group-text">Purchase Request</span>
					<input type="text" id="purchord" value="${ordPRQid}"  style="width:500px; height: 37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
				</div>
			</div>
		</div>
  	
  	</div>
    

<div class="row">
		<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span style="width:130px;" class="input-group-text" >Warehouse ID</span>
							<input type="text" id="wareid" value="${WareId}"  style="width:85%; height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
			
			
			 <div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span style="width:140px;" class="input-group-text"> Warehouse Name</span>
							<input type="text" id="warename" value="${WareName}" style="width:337px; height:37px;"  class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
			
			
			
			 <div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span style="width:70px;"  class="input-group-text">Site ID</span>
							<input type="text" id="siteid" value="${SiteId}" style="width:406px; height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
	
	
 </div>
	
	
	<hr>

	<div class="row">
			<div class="col-md-3">
					<div class="form-group">
						<div id="catInput" class="input-group-prepend">
							<span class="input-group-text" >Cat1</span>
							<input type="text" id="cat" value="${cat}" style=" height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
			
			
			 <div class="col-md-3">
					<div class="form-group">
						<div id="cat2Input" class="input-group-prepend">
							<span class="input-group-text"> Cat2</span>
							<input type="text" id="cat2" value="${cat2}" style=" height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
			
			
			
			 <div class="col-md-3">
					<div class="form-group">
						<div id="cat3Input" class="input-group-prepend">
							<span class="input-group-text">Cat3 </span>
							<input type="text" id="cat3" value="${cat3}" style=" height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
			
			
				 <div class="col-md-3">
					<div class="form-group">
						<div id="cat4Input" class="input-group-prepend">
							<span class="input-group-text">Cat4 </span>
							<input type="text" id="cat4" value="${cat4}" style=" height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
		</div>



		<div class="row">
		<div class="col-md-5">
					<div class="form-group">
						<div id="seqInput" class="input-group-prepend">
							<span class="input-group-text">Sequence</span>
							<input type="text" id="seq" value="${seq}" style="width:430px; height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
						</div>
					</div>
			</div>
			<div class="col-md-5">
					<div class="form-group">
						<div id="barcodeInput" class="input-group-prepend">
							<span class="input-group-text" >Barcode</span>	
								<input type="text" id="barcode" value="${barcode}" style="width:430px; height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />	
						</div>
					</div>


			</div>

 		</div>


    	<!--  create table purchaserequestItem    -->

		<div> 
				<form>
				
								
					    <div class="table-responsive-sm" id="tableContainer"> 
						    <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr id="bisotr" class="fixed-headerr">
						                <th>
						                <div class="container">
						                
								          <button type="button" id="selectAll" class="main">
								          
								          <span class="sub"></span>Select</button>
								         
								          </th>
						                <th>Item</th>
						                <th>Item Model</th>
						                <th>Item Part Number</th>
						                <th hidden>Barcode</th>
						                <th>Qty</th>
						                <th hidden>Category1</th>
						                <th hidden>Category2</th>
						                <th hidden>Category3</th>
						                <th hidden>Category4</th>
						                <th hidden>Sequence</th>
						                <th>Rate</th>
						                <th>Discount Amount</th>
						                <th>Tax (%)</th>
						                <th>Net Rate</th>
						                <th>Total</th>
						                <th>Total AT</th>
						                <th>GR</th>
						                <th>PR</th>
						                <th>AR</th>
						                <th>CIP</th>
						                <th>FAR</th>
						                <th>PO Item ID</th>
										<th hidden> Serial Number</th>
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						        
						    </table>
					    </div>
					    
					      <input type="text" id="RowIndex" value="" hidden>
						<input type="button" class="add-row" value="Add Row" onClick="addNewRow('next')">
					    <button type="button" class="delete-row">Delete Row</button>
                 </form>  
		</div>

<p></p>



<div class="row">

	<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width:200px;" class="input-group-text">Total Amount</span>
						<input type="text" id="ordtotamnt" value="${TotalAmount}" readonly class="form-control text-input" />
					</div>
				</div>
	</div>
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width:200px;" class="input-group-text">Discount Amount</span>
						<input type="text" id="orddiscamnt" value="${discAmount}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width:200px;" class="input-group-text">Discount Percent</span>
						<input type="text" id="orddiscpercent" value="${discountPercent}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	 <div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:200px;" class="input-group-text">Total QTY</span>
				<input type="text" id="ordtotqty" value="${TotalQty}" readonly class="form-control text-input" />
				</div>							
		</div>
	</div>
	<div class="row">
	
	    <div class="col-md-3">
			<div class="input-group-prepend">
				<span style="width:200px;" class="input-group-text">Net Total</span>
				<input type="text" id="ordNetTotal" value="${netTotal}"  readonly class="form-control text-input"  />
			</div>
		</div>
	    
		<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span style="width:200px;" class="input-group-text">Paid Amount</span>
						<input type="text" id="ordpaidamnt" value="${paidAmount}" class="form-control text-input"  />
					</div>
				</div>
		</div>
	
		<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
					<span style="width:200px;" class="input-group-text">Outstanding</span>
					<input type="text" id="ordoutstand" value="${ordOutstanding}"  readonly class="form-control text-input"/>
					</div>
				</div>
		</div>
	
		
	</div>

</div>

<div class="tab-pane fade" id="custom-tabs-one-discrepancy" role="tabpanel" aria-labelledby="custom-tabs-one-discrepancy">
<div style="height:20px;"></div>
 <table id ="bisotab1" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
						        <thead>
						            <tr class="fixed-headerr">
						               
						                <th>Item Code</th>
						                <th>Item Name</th>
						                <th>Item Model</th>
						                <th>Item Part Number</th>
						                <th>Discrepancy Qty</th>
						                <th>Net rate</th>
						             
						                <th>Total price</th>
						                
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						        
						    </table>

  <table id ="disctab"  class="table table-striped table-bordered table-sm" style="display:block;  overflow-y: auto;">
						       <thead>
						            <tr class="fixed-headerr">
						                
						                <th>Total Quantity</th>
						                <th><input id="discQty" style="width:220px;" type="text" readonly /></th>
						                <th>Total Discrepancy Price</th>
						                <th><input id="discPrice" style="width:220px;" type="text" readonly /></th>
						                <th>Discrepancy Qty % </th>
						                <th><input id="qty%" style="width:220px;" type="text" readonly /></th>
						                <th>Discrepancy Price %</th>
						                   <th><input id="price%" style="width:220px;" type="text" readonly /></th>
						                   </tr></thead></table>






</div>






<div class="tab-pane fade" id="custom-tabs-one-overview" role="tabpanel" aria-labelledby="custom-tabs-overview">

<div class="row container" style="display:flex;">
			
			
			
			
				
           <div id="one" class="mx-auto ">
<!-- 
                <div class="card-header"><i class="fas fa-cash-register"style="color:#DCF8C6"></i> Purchasing</div>
                <div class="card-header"><i class="fas fa-shopping-basket"style="color:#DCF8C6"></i> Purchasing</div>
-->                
                <div class="card-header divv1"><i class="fas fa-receipt"style="font-size:20px;"></i><b> PURCHASE REQUEST</b></div>
                <div class="card-body mycard divv2">
                   
                  <table>
                   
                  <th style="width:50%;align:left;margin:5px;"></th> <th id="ordStatus" style="width:50%;margin:5px;height:35px;"> </th>
                 
                   <tr>
                   
                   <td class="TD">	<b>Purchase Request ID</b>	
                   <td id="PurchReqId" class="TD">		
                   
                   
                   </tr>
               		
               		<tr>
               		
               		<td class="TD">	<b>Net Total Amount</b>	
               		<td id="prNetTot" class="TD">	
               		
               		
               		</tr>
                 
                   <tr >
                   
                   <td>	<b>Total Quantity</b>	
                   <td id="prTotQty">		
                   	
                   
                   </tr>
                  
                   </table>

                </div>
            </div>
       
   
        
            <div id="two" class=" mx-auto">   
                <div class="card-header divv1"><i class="fas fa-search-plus"style="font-size:20px;"></i><b> DISCOVERY NEW</b></div>
                <div class="card-body mycard divv2">
                   
                 <table >
                   
                  <th style="width:40%;align:left;margin:5px;"></th> <th style="width:25%;margin:5px;">All </th><th style="width:35%;margin:5px;"> Not Completed</th>
                 
                   <tr>
                   
                   <td  class="TD">	<b>No of DNs </b>	
                   <td id="dnComp" class="TD">	
                   <td id="dnNotComp" class="TD">
                   
                   </tr>
               		
               		<tr >
               		
               		<td class="TD">	<b>Net Total Amount</b>	
               		<td id="dnNetTotComp" class="TD">		
               		<td id="dnNetTotNotComp" class="TD">
               		
               		</tr>
                 
                   <tr >
                   
                   <td>	<b>Total Quantity</b>	
                   <td id="dnTotQtyComp">		
                   <td id="dnTotQtyNotComp">	
                   
                   </tr>
                  
                   </table>
                </div>
            </div>
        
   
	  
	  
            <div id="three" class="mx-auto  " >
                <div class="card-header divv1"><i class="fas fa-sign-in-alt"style="font-size:20px;"></i><b> GOODS RECEIVED</b></div>
                <div class="card-body mycard divv2">
                   
                 <table >
                   
                  <th style="width:40%;align:left;margin:5px;"></th> <th style="width:25%;margin:5px;">All </th><th style="width:35%;margin:5px;"> Not Completed</th>
                 
                   <tr >
                   
                   <td  class="TD">	<b>No of GRs </b>	
                   <td id="goodsrCom" class="TD">	
                   <td id="goodsrNotCom" class="TD">
                   
                   </tr>
               		
               		<tr>
               		
               		<td  class="TD">	<b>Net Total Amount</b>	
               		<td id="netGoodsrCom" class="TD">		
               		<td id="netGoodsrNotCom" class="TD">
               		
               		</tr>
                 
                   <tr>
                   
                   <td>	<b>Total Quantity</b>	
                   <td id="totQtyGoodsrCom">	
                   <td id="totQtyGoodsrNotCom">	
                   
                   </tr>
                  
                   </table>
                </div>
            </div>
        
		</div>
		
		<div class="row container" style="display:flex;">
            <div id="four" class="mx-auto  " >
                <div class="card-header divv1"><i class="fas fa-road"style="font-size:20px;"></i><b> CAPITAL IN PROGRESS</b></div>
                <div class="card-body mycard divv2">
                   
                 <table >
                   
                  <th style="width:50%;align:left;margin:5px;"></th> <th style="width:50%;margin:5px;"></th>
                 
                   <tr >
                   <td class="TD">	<b>No of CIPs</b>	
                   <td id="cipCouuntAll" class="TD">		
                   </tr>
               		
               		     <tr >
                   <td  class="TD">	<b>Total Amount</b>	
                   <td id="cipNeetTotAll" class="TD">		
                   
                   </tr>
               		
               		<tr >
               		
               		<td>	<b>Total Quantity</b>	
               		<td id="cipTootQtyAll">	
               		
               		
               		</tr>
                 
              
                  
                   </table>
                </div>
       </div>
       
		
           <div id="five" class="mx-auto  " >
                <div class="card-header divv1"><i class="fas fa-registered"style="font-size:20px;"></i><b> ASSET REGISTRY</b></div>
                <div class="card-body mycard divv2">    
                <table>
                   
                  <th style="width:50%;align:left;margin:5px;"></th> <th style="width:50%;margin:5px;"></th>
                 
                   <tr >
                   
                   <td  class="TD">	<b>No of ARs</b>	
                   <td id="arCountAll" class="TD">	
                   
                   
                   </tr>
               		
               		<tr>
               		
               		<td><b>Sum of Rate</b>	
               		<td id="arValRateAll">	
               		
               		
               		</tr>
                 
                 <tr  ></tr>
                  
                   </table>
                </div>
            </div>
        
        
		
		
           <div id="six" class="mx-auto">
                <div class="card-header divv1"><i class="fas fa-money-check-alt"style="font-size:20px;"></i><b> FIXED ASSET REGISTRY</b></div>
                <div class="card-body mycard divv2">
                   
                   <table>
                   
 	<th style="width:40%;align:left;margin:5px;"></th> <th style="width:25%;margin:5px;">All </th><th style="width:25%;margin:5px;"> Not Running</th>   
   
    			<tr>
                   
                   <td  class="TD">	<b>No of FARs </b>	
                   <td id="farCount" class="TD">	
                   <td id="farNotRunCount" class="TD">
                   
                </tr>
               		
               	<tr>
               		
               		<td  class="TD"><b>Initial Cost</b>	
               		<td id="faRCountAll" class="TD">		
               		<td id="farNotRunIntCost" class="TD">
               		
               	</tr>
                 
                <tr>
                   
                   <td><b>Net Cost  </b>
                   <td id="faRValRateAll">	
                   <td id="farNotRunNetCost">	
                   
                 </tr>
                  
                   </table>
                </div>
            </div>
        </div>
        </div>
        
           	</div>
        </div>
        
<div class="container">
	<div id ="poModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
			<h5 id ="popupNb" class="modal-title" style="font-weight:bold; color: #E9ECEF ;position: relative; bottom: 12px;"></h5>
				<div style="float: right;">
				<button  name="insertBelow"  onclick="insertRowBelow()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -30px; font-weight: bold; margin-top: -7px;"">Insert Below </button>
				<button  name="insertAbove"  onclick="insertRowAbove()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -20px; font-weight: bold; margin-top: -7px;"">Insert Above </button>
				<button  name="deleteBoqRow" onclick="deleteBoqRow()"   class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -10px; font-weight: bold; margin-top: -7px;"">Delete</button>
				<button  name ="previousRow" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 0px; font-weight: bold; margin-top: -7px;"">Previous</button>
	            <button  name="nextRow" onclick="nextRow()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 10px; font-weight: bold; margin-top: -7px;"">Next</button> 
				<button type="button" name="closeModPartPopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
									<a class="close modalMinimize ml-3"> <i
										class='fa fa-minus icon-to-change'></i></a>
				</div>
				</div>
	<div class="modal-body">
	<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="item-tab" style="color: gold;" data-toggle="tab" href="#item" role="tab" aria-controls="item" aria-selected="true">ITEM</a>
  		</li>
  
  		<li class="nav-item">
   			 <a class="nav-link " id="serialNum-tab" style="color: gold;" data-toggle="tab" href="#serialNum" role="tab" aria-controls="serialNum" aria-selected="false">SERIAL NUMBER</a>
  		</li>
 
	</ul>
            

<div class="tab-content">
  <div class="tab-pane active" id="item" role="tabpanel" aria-labelledby="item-tab">
  <p></p>
<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Item </span>
   					<input type="text" id="popupItemCode" value="${popupItemCode}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
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
					<span class="input-group-text" >Item Model</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupItemModel" value="${popupItemModel}" style="width:675px; height:37px"  />					
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Part No</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupItemPartno" value="${popupItemPart}" style="width:674px; height:37px"  />
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
					<span class="input-group-text" >Category1</span>
						<input type="text" id="popupCat1" class="form-control text-input" value="${popupCat1}" style="width:674px; height:37px" />
				</div>
			</div>
		</div>
				
					
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text" >Category2</span>
				<input type="text" id="popupCat2" class="form-control text-input" value="${popupCat2}" style="width:674px; height:37px" />
						
		</div>
	</div>
</div>

<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Category3</span>
				<input type="text" id="popupCat3" class="form-control text-input" value="${popupCat3}" style="width:674px; height:37px"  />  
						
		</div>
	</div>
</div>
				
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Category4</span>
				<input type="text" id="popupCat4" class="form-control text-input" value="${popupCat4}" style="width:674px; height:37px"  />  
						
		</div>
	</div>
</div>

<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Sequence</span>
				<input type="text" id="popupSeq" class="form-control text-input" value="${popupSeq}" style="width:674px; height:37px"  />  
						
		</div>
	</div>
</div>
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Barcode</span>
				<input type="text" id="popupBarcode" class="form-control text-input" value="${popupBarcode}" style="width:674px; height:37px"  />  
						
		</div>
	</div>
</div>

	</div> </div>							
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Qty</span>
					<input type="text" id="popupQty" class="form-control text-input" value="${popupQty}" style="width:120px; height:39px" />
				</div>
		    </div>
		  </div>
					
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Discount Amount</span>
					<input type="text" id="popupDiscountAmount" class="form-control text-input" value="${popupDiscountAmount}" style="width:200px;" />
						
				</div>
			</div>
		</div>

		<div class="col-sm-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Tax</span>
					<input type="text" id="popupTax" class="form-control text-input" value="${popupTax}" style="width:110px;"  />  
						
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
					<span class="input-group-text" >Rate</span>
					<input type="text" id="popupRate" class="form-control text-input" value="${popupRate}" style="width:675px;"  />
				</div>
			</div>
		</div>
				
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Net Rate</span>
					<input type="text" id="popupNetRate" class="form-control text-input"  value="${popupNetRate}" readonly style="width:675px;" />
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
					<span class="input-group-text" >Total</span>
					<input type="text" id="popupTotal" class="form-control text-input" value="${popupTotal}" readonly style="width:675px;"  />
				</div>
			</div>
	    </div>					      

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Total AT</span>
				    <input type="text" id="popupTotalAt" class="form-control text-input" readonly value="${popupTotalAt}" style="width:675px;"   />
				</div>
			</div>
	    </div>
	</div></div>
	
<div class="container-fluid">
<div class="row">
	<div class="col-sm-3">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text" >AR</span>
				<input type="text" id="popupArQty" class="form-control text-input" readonly value="${popupArQty}" style="width:120px;"  />
			 </div>
		</div> 
	</div>

	<div class="col-sm-3">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">PR</span>
				<input type="text" id="popupPrQty" class="form-control text-input" readonly value="${popupPrQty}" style="width:120px;"   />
			</div>
		 </div>
	</div>
								     
	<div class="col-sm-3">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text" >GR</span>
				<input type="text" id="popupGrQty" class="form-control text-input" value="${popupGrQty}" readonly style="width:120px;"  />		
			 </div>
		</div>
	 </div>

	<div class="col-sm-3">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">CIP</span>
				<input type="text" id="popupCipQty" class="form-control text-input" readonly value="${popupCipQty}" style="width:110px;"   />		
			</div>
		</div>
	</div> 
</div> </div>
								      

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >FAR</span>
					<input type="text" id="popupFarQty" class="form-control text-input" readonly value="${popupFarQty}" style="width:110px;"  />
				</div>
			</div>
	    </div>
	</div>
</div>	      
					
  </div>

  
  <div class="tab-pane" id="serialNum" role="tabpanel" aria-labelledby="serialNum-tab">
  <div> 
<p></p>
	<form>
		<div class="table-responsive-sm"> 
			<table id ="serialNoTable" class="table table-striped table-bordered table-sm" style="display:block; height:320px; overflow-y: auto;">	
				<thead>
					<tr class="fixed-headerr">
					<th> 
						<button type="button" id="selectAllSerial" class="main">
						<span class="sub"></span>Select</button>
								         
					  </th>
					 <th width="232px">Serial Number</th>
					 <th width="232px">Item Model</th>
                     <th width="232px">Item Part Number</th>
						              
					</tr> </thead> <tbody> </tbody> </table>
					</div>
					  	<input type="text" id="RowIndex2" value="" hidden>
						<button type="button" class="add-row-serial" onclick="addRowSerial()">Add Row</button>
						<button type="button" class="delete-row-serial">Delete Row</button>
					</form>
					</div> 
					
					
 </div>
</div>

  
  </div>
					
<div class="modal-footer">
	
</div>	
								                
	</div>
			
		 </div> </div></div>
        

  <div id="popUpDiv" style="display:none;">
  <div class="sendEmail" style="margin-top:50px;" >
 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Sender</span>
						<input type="text" id="email"   class="ui-widget ui-widget-content ui-corner-all form-control" />
						<input type="text" id="password" value="${password}"  class="ui-widget ui-widget-content ui-corner-all form-control" hidden/>
					
					</div>
				</div>
	</div>
	 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width:80px;">   Email To</span>
						<input type="text" id="emailTo" class="form-control text-input" />
					
					</div>
				</div>
	</div>
	<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width:80px;">   ccEmail </span>
						<input type="text" id="ccmail" class="form-control text-input" />
					
					</div>
				</div>
	</div>
 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Title</span>
						<input type="text" id="subject"  class="form-control text-input" />
					
					</div>
				</div>
	</div>
<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Message</span>
						<textarea  id="message"  class="form-control text-input" cols="200" rows="4" ></textarea>
					
					</div>
				</div>
	</div>
	<div class="col-md-12">
				<p></p>
				<div class="form-group"  style=" margin-left:100px; ">
	
			<button type="button" id="send"
				class="btn btn-primary BtnActive">
				<i class="fa fa-paper-plane" ></i> Send
				</button>
                        
				<button type="button" id="cancelButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-times"></i> Cancel
				</button>
								</div>
								</div>
	</div>
	</div>	
 </body>
 <script>
 $(document).on("triggerBoqListenersEvent", function () {
     $(function(){
    	 
 boqArray = ${ListPoItem};
 for(i = 0; i<boqArray.length;i++ ){
 boqAutocomplete(i);
 }
  boqInputListener();
     });
 });
 </script>
 <script>
 $(function(){
 if ('${docStatus}' != 'addNew') {
	 if('${docStatus}' != 'addNewFromPRQ') {
//////////////////// Discrepancy Report
boqArray=${discrepancy};
var totalqty=0;
var totalprice=0;
for (j = 0; j < boqArray.length; j++){

	var ItemCode=boqArray[j][0];
	if(ItemCode==null){
		ItemCode="";
	}
	var ItemName=boqArray[j][1];
	if(ItemName==null){
		ItemName="";
	}
	var ItemModel=boqArray[j][2];
	if(ItemModel==null){
		ItemModel="";
	}
	var ItemPNum=boqArray[j][3];
	if(ItemPNum==null){
		ItemPNum="";
	}
	var DiscQty=boqArray[j][4];
	if(DiscQty==null){
		DiscQty="";
	}
	var netRate=boqArray[j][5];
	if(netRate==null){
		netRate="";
	}
    var totalPrice = parseFloat(DiscQty) * parseFloat(netRate);
    var discrepancyRow="";

discrepancyRow += "<tr>"
    + "<td name='itemCode'><input type='text' name='itmCode' value='" + ItemCode + "' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>"
    + "<td name='itemName'><input type='text' name='itmName' value='" + ItemName + "' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>"
	 + "<td name='itemModel'><input name='itmModel' type='text' value='" + ItemModel + "' style='width:230px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
	 + "<td name='itemPartNo'><input name='itmPartNo' type='text' value='" + ItemPNum + "' style='width:230px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>"		 			
	 + "<td  name='poBarCode'><input type='text' style='width:230px;'  value='" + DiscQty +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='qty'><input name='qty' type='text' style='width:230px;' value='" + netRate +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='qty'><input name='qty' type='text' style='width:230px;' value='" + totalPrice +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>;"
	 $("#bisotab1 > tbody").append(discrepancyRow);
totalqty+=parseFloat(DiscQty);
totalprice+=parseFloat(totalPrice);
}
document.getElementById("discQty").value = totalqty;
document.getElementById("discPrice").value = parseFloat(totalprice).toFixed(3);
document.getElementById("qty%").value = (totalqty*100/parseFloat($("#ordtotqty").val())).toFixed(3); + "%";
document.getElementById("price%").value = (totalprice*100/parseFloat($("#ordNetTotal").val())).toFixed(3); + "%";
} // end if not addNewFromPRQ
else {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");	 
}
  
boqArray = ${ListPoItem};
var itemRow="";
var barcode ="";
var itemModel = "";
var itemPartNumber = "";
var dotStatus = "";
var span;
 // Fill tables rows from DB
for (i = 0; i < boqArray.length; i++){
	barcode =  boqArray[i].poBarCode;
	if(barcode == null){
	barcode = "";
	}
	else{
	barcode =  boqArray[i].poBarCode;
	}
	itemModel =  boqArray[i].itemModel;
   		if(itemModel == null)
   		itemModel = "";
   		else
   		itemModel =  boqArray[i].itemModel;


   	itemPartNumber = boqArray[i].itemPartNumber;
	if(itemPartNumber == null)
		itemPartNumber = "";
	else
		itemPartNumber = boqArray[i].itemPartNumber;
 	dotStatus = boqArray[i].poItemStatus;
	
 	if (boqArray[i].qty == boqArray[i].grQty || boqArray[i].qty == boqArray[i].arQty || boqArray[i].qty == boqArray[i].farQty)  	         				 
      //if(dotStatus == "1")
     {
	   span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: chartreuse;'></span>";
     }
else 
     {
	 span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: orange;'></span>";
	 }

	var serialArrays = [];
  if (boqArray[i].serial_obj != null) {
  serialArrays.push(boqArray[i].serial_obj);
  }
  else{
  serialArrays.push(null);
  }     
       
    itemRow += "<tr>"
     + "<td><input type='checkbox' name='record'>"+span+"<button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
	 + "<td name='itemCode'><input type='text' name='itmCode' value='" + boqArray[i].itemCode +":"+ boqArray[i].itemName + "' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>"
	 + "<td name='itemModel'><input name='itmModel' type='text' value='" + itemModel + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
	 + "<td name='itemPartNo'><input name='itmPartNo' type='text' value='" + itemPartNumber + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>"		 			
	 + "<td hidden name='poBarCode'><input type='text' style='width:200px;' hidden value='" + barcode +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='qty'><input name='qty' type='text' style='width:200px;' value='" + boqArray[i].qty +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='rate'><input name='rate' type='text' style='width:200px;' value='" + boqArray[i].rate +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='discountAmount'><input  name='discountAmount' type='text' style='width:200px;' value='" + boqArray[i].discountAmount +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='tax1'><input name='tax1' type='text' style='width:200px;' value='" + boqArray[i].tax1 +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
     + "<td name='netRate'><input type='text' style='width:200px;' value='" + boqArray[i].netRate +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
     + "<td name='total'><input type='text' style='width:200px;' value='" + boqArray[i].total +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='totalAt'><input type='text' style='width:200px;' value='"  + boqArray[i].totalAt +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='grpQty'><input type='text' style='width:200px;' value='" + boqArray[i].grQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='prQty'><input type='text' style='width:200px;' value='" + boqArray[i].prQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='arQty'><input type='text' style='width:200px;' value='" + boqArray[i].arQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='cipQty'><input type='text' style='width:200px;' value='" + boqArray[i].cipQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='farQty'><input type='text' style='width:200px;' value='" + boqArray[i].farQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='porItemId'><input type='text' style='width:200px;' value='" + boqArray[i].pordItemId +"'readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'><input name='poItemStatus' type='text' value='" + (boqArray[i].poItemStatus !== null ? boqArray[i].poItemStatus : "0") + "'hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
	 + "<td name='serialNo'><input type='text'  style='width:200px;' value=" + serialArrays[0] +" hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"		
	 + "</tr>";
    

}   
$("#bisotab > tbody").append(itemRow);
updateContainerHeight();

function updateContainerHeight() {
  // Get the table and container elements
  var table = document.getElementById("bisotab");
  console.log(table.offsetHeight);
  var container = document.getElementById("tableContainer");
  console.log(container.offsetHeight);
  var tr = document.getElementById("bisotr");
  console.log(tr.offsetHeight);
  console.log(boqArray.length);

  table.style.height = table.offsetHeight + (tr.offsetHeight * (boqArray.length)) + "px";
  console.log("new height : "+table.offsetHeight)
}
//trigger
$(document).trigger("triggerBoqListenersEvent");
  }
 else {
		$("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});	
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");	 
	 }
 }); // End for function();
 </script>

 <script type='text/javascript'>

 /*
 if ('${docStatus}' == "addNew" || '${docStatus}' == "addNewFromPRQ") {
		$("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});	
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");
	}
*/	

/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
//  $("#sendEmail").on("click", function () {
//      $("#popUpDiv").fadeIn();
    
//      });
 
//  $("#cancelButton").on("click", function () {
// 	 $("#email").val('');
// 	 $("#password").val('');
// 	 $("#emailTo").val('');
// 	 $("#ccmail").val('');
// 	 $("#subject").val('');
// 	 $("#message").val('');
//      $("#popUpDiv").fadeOut();
//      });

//  $("#send").on("click", function () {
// 	if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
// 		{
// 		alert("All Fields are required");
// 		}
// 	else{
// 		 $("#popUpDiv").fadeOut();
// 	}
    
//      });
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 var FormSave=[];

 $("#custom-tabs-overview").click(function() {
		if($("#ordcode").val()!=""){

		$.ajax({
	        type: "GET",
	        contentType: "application/json; charset=utf-8",
	        url: '${pageContext.request.contextPath}/GetPODATA',
	        data: {
	            
					"ID" : $("#ordcode").val(),
	        },
	        dataType: "json",
	        success: function (data) {

	        	 
	            	$('#goodsrCom').text(data.goodsrCom);
	            	$('#netGoodsrCom').text(data.netGoodsrCom);
	            	$('#totQtyGoodsrCom').text(data.totQtyGoodsrCom);

	            	$('#goodsrNotCom').text(data.goodsrNotCom);
	            	$('#netGoodsrNotCom').text(data.netGoodsrNotCom);
	            	$('#totQtyGoodsrNotCom').text(data.totQtyGoodsrNotCom);

	            	$('#PurchReqId').text(data.PurchReqId);
	            	$('#ordStatus').text(data.ordStatus);
	            	$('#prNetTot').text(data.prNetTot);
	            	$('#prTotQty').text(data.prTotQty);
	            	
	            	$('#dnComp').text(data.dnComp);
	            	$('#dnNetTotComp').text(data.dnNetTotComp);
	            	$('#dnTotQtyComp').text(data.dnTotQtyComp);

	            	$('#dnNotComp').text(data.dnNotComp);
	            	$('#dnNetTotNotComp').text(data.dnNetTotNotComp);
	            	$('#dnTotQtyNotComp').text(data.dnTotQtyNotComp);

	            	$('#cipCouuntAll').text(data.cipCouuntAll);
	            	$('#cipNeetTotAll').text(data.cipNeetTotAll);
	            	$('#cipTootQtyAll').text(data.cipTootQtyAll);

	            	$('#arCountAll').text(data.arCountAll);
	            	$('#arValRateAll').text(data.arValRateAll);

	            	$('#faRCountAll').text(data.faRCountAll);
	            	$('#faRValRateAll').text(data.faRValRateAll);

	            	$('#farCount').text(data.farCount);
	            	$('#farNotRunCount').text(data.farNotRunCount);
	            	$('#farNotRunIntCost').text(data.farNotRunIntCost);
	            	$('#farNotRunNetCost').text(data.farNotRunNetCost);
	        },		
	        error: function(result) {
	            alert("Oops errror somewhere");
	        }
	    });
		}
		}); // End of custom-tabs-overview click event

	 $("#ordstat").change(function() {
		 var ordStatus = $("#ordstat").val();
	 	if(ordStatus == 'approved'){		 	
	 		 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
			 $('#ordstat').attr('disabled',true);							
	 		$("#Approvepo").addClass('disabled'); 
	 		$("#completepo").removeClass('disabled'); 
	 		$("#Newgr").removeClass('disabled');  
	 		}
 		else if(ordStatus == 'draft') {
	 		 $("#Approvepo").removeClass('disabled');
	 		 $("#Closepo").addClass('disabled');
	 		$("#completepo").addClass('disabled');
	 		$("#Newgr").addClass('disabled');  
	 		
 			}
 		else if (ordStatus == 'completed'){
	 		$("#Approvepo").addClass('disabled');
	 		$("#Closepo").addClass('disabled');
	 		$("#completepo").addClass('disabled');
	 		$("#Newgr").addClass('disabled');  
	 		}
 		else if (ordStatus == 'closed'){ 
	 		$("#Approvepo").addClass('disabled');
	 		$("#Closepo").addClass('disabled');
	 		$("#completepo").addClass('disabled');
	 		$("#Newgr").addClass('disabled');  
	 		}
 		
 		else if (ordStatus == 'cancelled'){
			 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
			 $('#ordstat').attr('disabled',true);
			 $("#Cancelpo").addClass('disabled');
			 $("#Approvepo").addClass('disabled');
			 $("#completepo").addClass('disabled');
			 $("#Newgr").addClass('disabled');  
		 		
		
		 }
 		else {
	 		$("#Approvepo").removeClass('disabled');
	 		$("#Closepo").addClass('disabled');
	 		$("#Newgr").addClass('disabled');  
	 		$("#completepo").addClass('disabled');
	 		} 


	 	

// 	$("#formStatus").text("Not Saved");
// 	$('.dot').css({"background-color" : "orange"});
			});
 
 	
//  			$("#ordstat").change(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});

 
// 			$("input").change(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});
   
			 $("#datetimepicker3").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
			$("#datetimepicker4").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});

// 			$("#ordsuppname,#ordsuppaddress,#purchord,#ordsuppid").on('keyup change focus', function () {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 				});

// 			$("#popupItemModel,#popupItemCode,#popupItemPartno,#popupCat1,#popupCat2,#popupCat3,#popupCat4,#popupSeq,#popupBarcode").on('keyup change focus', function () {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 				});
		

// 			$("#wareid,#warename,#siteid").on('keyup change focus', function () {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 				});
// 			$("#barcode,#seq,#cat,#cat2,#cat3,#cat4").on('keyup change focus', function () {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 				});
			
// 			$(".add-row-serial").click(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});

// 			$(".delete-row-serial").click(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});
				
// 			$("input").click(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});
			
// 			$("#serialNoTable ").change(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});	
			
// 			$(".add-row").click(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});
				
// 			$(".delete-row").click(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});	
			
// 			$("#bisotab > tbody").change(function() {
// 				$("#formStatus").text("Not Saved");
// 				$('.dot').css({"background-color" : "orange"});
// 			});	
			
       		 
 
	$(document).ready(function() {
		$(function(){
		var pOrdAppFlag = 0;
		var pOrdCnclFlg = 0;
		var unsaved = false;
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
// 		$("#email").autocomplete({
// 		    source: function(request, response) {
// 		    	var password=$("#password").val();
// 		           $.ajax({
// 		                 type: "GET",
// 		                 contentType: "application/json; charset=utf-8",
// 		                 url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
// 		                 data: {
// 								"email" : request.term,
// 						 },
// 		                 dataType: "json",
// 		                 success: function (data) {
// 		                     if (data != null) {
// 		                         response(data.ListItemEmailAccounts);
// 		                         console.log('data in $("#email").autocomplete is :  '+ data.ListItemEmailAccounts);

// 		                     }
// 		                 },
// 		                 error: function(result) {
// 		                     alert("Error");
// 		                 }
// 		             });
		            
// 		         }, minLength:0, maxShowItems: 40, scroll:true,
				
// 		         select: function(event, ui) {
		        	 
// 		             this.value = (ui.item ? ui.item[0]  : '');
// 		             password.value = ui.item[1];
		             
// 		             return false;
		            
// 		         }
		        
// 		     }).autocomplete("instance")._renderItem = function(ul, item) {
// 		         return $("<li class='each'>")
// 		         .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
// 		             item[0] + "</span></div>")
// 		         .appendTo(ul);
// 		 	};
		 	
		     
// 					$("#email").focus(function(){
// 		   	        	if (this.value == ""){
// 		   	            	$(this).autocomplete("search");
// 		   	        	}						
// 					});
// 					$("#password").focus(function(){
// 		   	        	if (this.value == ""){
// 		   	            	$(this).autocomplete("search");
// 		   	        	}						
// 					});
				
		 //////////////////////////////////////////////////////////////////////////////		  	

		 
		
		var ordStatus=$("#ordstat").val();

		if(ordStatus == 'approved' || ordStatus == 'completed' || ordStatus =='closed')
 			 $("#Approvepo").addClass('disabled');
		 
 		else $("#Approvepo").removeClass('disabled');

		if(ordStatus == 'draft' || ordStatus == 'completed' || ordStatus =='closed' || ordStatus =='cancelled' ){
			 $("#Closepo").addClass('disabled');
			 $("#Newgr").addClass('disabled');
			 
		}
		
 		else $("#Closepo").removeClass('disabled');

		
		if(ordStatus == 'draft' || ordStatus == 'completed' || ordStatus =='closed' || ordStatus =='cancelled' )
			 $("#completepo").addClass('disabled');
			
		else $("#completepo").removeClass('disabled');
		
		$(window).bind('beforeunload', function() {
		    if(unsaved){
		        return "You have unsaved changes. Do you want to leave and discard?";		        
		    }
		});

		$(document).on('change', ':input', function(){
		    unsaved = true;
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
		});

		var saveButton = '<button type="button" id="saveButton" class="btn btn-primary BtnActive"><i class="fa fa-save"></i> Save </button>';
		var ammendButton = '<button type="button" id="ammendButton" class="btn btn-primary BtnActive"><i class="fa fa-edit"></i> Amend </button>';


		var ordStatus=$("#ordstat").val();
		if($("#ordcode").val() == "") $("#Cancelpo").addClass('disabled');
		if(ordStatus == "cancelled")
			
		$("#Buttons").append(ammendButton);
		
		else

		$("#Buttons").append(saveButton);


		 var dict = [];
		    
	        
		    //function to  get selected rows for save
			function getSelectedRows () {

				dict = [];
				
				$("#bisotab > tbody").find('input[name="record"]').each(function(){
	 
					    dict.push({
					     "itemCode" : $(this).parent().parent().children('td[name="itemCode"]').children('input').val(),
					     "itemModel" : $(this).parent().parent().children('td[name="itemModel"]').children('input').val(),
					     "itemPartNo" : $(this).parent().parent().children('td[name="itemPartNo"]').children('input').val(),
						 "poBarCode" :  $(this).parent().parent().children('td[name="poBarCode"]').children('input').val(),
						 "qty" : $(this).parent().parent().children('td[name="qty"]').children('input').val(),
						 "rate" : $(this).parent().parent().children('td[name="rate"]').children('input').val(),
						 "discountAmount" : $(this).parent().parent().children('td[name="discountAmount"]').children('input').val(),
						 "tax1" : $(this).parent().parent().children('td[name="tax1"]').children('input').val(),
						 "netRate" : $(this).parent().parent().children('td[name="netRate"]').children('input').val(),
						 "total" : $(this).parent().parent().children('td[name="total"]').children('input').val(),
						 "totalAt" : $(this).parent().parent().children('td[name="totalAt"]').children('input').val(),
						 "grpQty" : $(this).parent().parent().children('td[name="grpQty"]').children('input').val(),
						 "prQty" : $(this).parent().parent().children('td[name="prQty"]').children('input').val(),
						 "arQty" : $(this).parent().parent().children('td[name="arQty"]').children('input').val(),
						 "cipQty" : $(this).parent().parent().children('td[name="cipQty"]').children('input').val(),
						 "farQty" : $(this).parent().parent().children('td[name="farQty"]').children('input').val(),
						 "porItemId" : $(this).parent().parent().children('td[name="porItemId"]').children('input').val(),
						 "poItemStatus" : $(this).parent().parent().children('td[name="porItemId"]').children("input[name='poItemStatus']").val(),
						 "serialNo" : ($(this).parent().parent().children('td[name="serialNo"]').children('input').val() == "null" || $(this).parent().parent().children('td[name="serialNo"]').children('input').val() == '{"serialArray":[]}') ? "" : $(this).parent().parent().children('td[name="serialNo"]').children('input').val()			 
						 });				 					
	           		  
	            	});
	              
			}  // end of getSelectedRows
	  
		 $("#Approvepo").click(  function() {
			 var checkSaving = true;
			 $('#ordstat').val("approved");
			 
			 pOrdAppFlag = 1;
			 pOrdCnclFlg = 0;
			 FormSave.push("Approve");
			 $(function(){
			 		checkSaving = checkedDataOnAction(); // to validate the data in the fields.
			 		if(checkSaving !== false){
						 getSelectedRows ();
						 saveRowsInTables(); 
			 		}
			 	}); 			    
			});
			
			//start of  Cancel Action
		 $("#Cancelpo").click(  function() {
			 var checkSaving = true;
			 
			 pOrdAppFlag = 0;
			 pOrdCnclFlg = 1;
			 FormSave.push("Cancel");
			 unsaved = false;		 
			 $(function(){
			 		checkSaving = checkedDataOnAction();
			 		if(checkSaving !== false){
						 getSelectedRows ();
						 saveRowsInTables(); 
			 		}
			 	}); 
			 })//end of Cancel Action
			
			 $(document).on('click', '#ammendButton', function() {
			
			 $("#ammendButton").remove();
			 $('#custom-tabs-one-tabContent :input').attr('disabled',false);
			 $("#formStatus").text("Not Saved");
		     $('.dot').css({"background-color" : "orange"});
			 $("#Buttons").append(saveButton);
			 $("#ordstat").val('draft').trigger('change');
			});
			
	//start of Main Save
			 $(document).on('click', '#saveButton', function() {
				 var checkSaving = true;
				 FormSave.push("Save");
			 	unsaved = false;
			 	$(function(){
			 		checkSaving = checkedDataOnAction();
			 		if(checkSaving !== false){
						 getSelectedRows ();
						 saveRowsInTables(); 
			 		}
			 	});
		 });//end of Main Save

		 //main delete button
		 $("#deleteButton").click(  function() {
			 unsaved = false;
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/DeletePO",
					dataType : "json",
					data : {
						"ID" : [$("#ordcode").val()]
					},
					success : function(one) {
						location.replace("${pageContext.request.contextPath}/PurchaseOrderListView");
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});	 
			
		 });//end of Main Delete
		 
			$("#wareid").autocomplete({
				
				 source: function(request, response) {

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
				                     if (data != null) {
				                         response(data.globalList);
				                      
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
				         }, minLength:0, maxShowItems: 40, scroll:true,		
			               
			        
					select: function(event, ui) {
						wareid.value = (ui.item ? ui.item[0]  : '');
						warename.value=ui.item[1];
						siteid.value=ui.item[2];

						return false;
								}	
					    }).autocomplete("instance")._renderItem = function(ul, item) {
				            return $("<li class='each'>")
			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                   item[0] + "</span><br><span class='desc'>" +
			                    item[1] +', '+ item[2] + "</span></div>")
			                .appendTo(ul);
			        	};
						$("#wareid").focus(function(){
			   	        	if (this.value == ""){
			   	            	$(this).autocomplete("search");
			   	        	}						
						});

			$("#siteid").autocomplete({
				show: true,
				
					        
		        source: function(request, response) {

                        var warehouse=$("#wareid").val();
                        var warehouseName=$("#warename").val();
			        
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
			                 data: {
									  "warehouseName" :$("#siteid").val(),						 
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
					
					siteid.value = (ui.item ? ui.item[2]   : '');
                	wareid.value = ui.item[0];
                	warename.value = ui.item[1];
					return false;
							}
				    }).autocomplete("instance")._renderItem = function(ul, item) {					    
			            return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                     item[2] + "</span><br><span class='desc'>" +
			                item[1] + ', '+ item[0] + "</span></div>")
		                .appendTo(ul);
		        	};
					$("#siteid").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});


 


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
		               
		          
		                WareId = data.globalList[0];
		            

		                if(data.globalList.length == 1){
		                	 
		                	$("#wareid").val(ui.item[0]);
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
               .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[1] + "</span><br><span class='desc'>" 
                   )
               .appendTo(ul);
       	};
		$("#warename").focus(function(){
  	        	if (this.value == ""){
  	            	$(this).autocomplete("search");
  	        	}						
		});
	
	/////////////end autocpmplete for warhouse

	$("#ordsuppid").autocomplete({
    
    source: function(request, response) {

         var supplierName=$("#ordsuppname").val();
        
             $.ajax({
                 type: "GET",
                 contentType: "application/json; charset=utf-8",
                 url: '${pageContext.request.contextPath}/GetAllSupplier',
                 data: {
						"supplierId" : $("#ordsuppid").val(),
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
         }, minLength:0, maxShowItems: 40, scroll:true,

			select: function(event, ui) {
				ordsuppid.value = (ui.item ? ui.item[0]  : '');
				ordsuppname.value=ui.item[1];
				ordsuppaddress.value= ui.item[2];
				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[0] + "</span><br><span class='desc'>" +
                    item[1] + "</span></div>")
                .appendTo(ul);
        	};
			$("#ordsuppid").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});

			$("#ordsuppname").autocomplete({
			    
			    source: function(request, response) {

				    var psupplierId= $("#ordsuppid").val();
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllSupplierName',
			                 data: {
									"supplierId" :psupplierId,
									"supplierName":$("#ordsuppname").val(),
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
			         }, minLength:0, maxShowItems: 40, scroll:true,

						select: function(event, ui) {
							ordsuppname.value = (ui.item ? ui.item[0]  : '');
							 		


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
					         
					                SuppID = data.ListSuppIds[0];

					                if(data.ListSuppIds.length == 1){
					                	 
					                	$("#ordsuppid").val(SuppID);
					                	ordsuppaddress.value = ui.item[1];

						                }

					                else{

					                	$("#ordsuppid").val("");
					                	$("#ordsuppaddress").val("");
					                	

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
			                    item[0] + "</span></div>")
			                .appendTo(ul);
			        	};
						$("#ordsuppname").focus(function(){
			   	        	if (this.value == ""){
			   	            	$(this).autocomplete("search");
			   	        	}						
						});
	/////////////end autocpmplete for warhouse
	
		//auto complete for purchase request 
		
				
		$("#purchord").autocomplete({
        source: function(request, response) {
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllPurchaseRequestsOrd',
	                 data: {
							"ID" : $("#purchord").val(),
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.Listreq);
	                    

	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 4, scroll:true,		

	         select: function(event, ui) {
	        	 	purchord.value = (ui.item ? ui.item[0] : '');
					return false;
						}
			    }).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                    item[0] + "</span></div>")
	                .appendTo(ul);
	        	};
				$("#purchord").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});

		$("#barcode").autocomplete({		
			source: function(request, response) {
				var barcode=$("#barcode").val();
					  $.ajax({
						  type: "GET",
						  contentType: "application/json; charset=utf-8",
						  url: '${pageContext.request.contextPath}/getBarcode',
						  data: {
							  "requestValue": request.term
								 },
								 dataType: "json",
								 success: function (data) {
								   		 if (data != null) {
							      response(data.ListBarcode);
  
							                     }
							                 },
							                 error: function(result) {
							                     alert("Error");
							                 }
							             });
							         }, minLength:0, maxShowItems: 40, scroll:true,		
						               
							        
									select: function(event, ui) {
							
							         	this.value = (ui.item ? ui.item[0]  : '');
							            var itmCode = ui.item[1];
										itemExist= checkifItemCodeExist(itmCode);
										
										if(itemExist == "true"){
										 checkItemCodeBoqRow(itmCode);
										 $("#formStatus").text("Not Saved");
										 $('.dot').css({"background-color" : "orange"});
										 $('#cat3').val("");
										 $('#cat4').val("");
								    	 $('#cat2').val("");
						            	 $('#cat').val("");
						            	 $('#seq').val("");
										 getSumQty_totalAT();
								}

									 else
										 {

										   var itmName=ui.item[1]+":"+ui.item[2];
											var model =ui.item[3];
											var partnum = ui.item[4];
											var barcode = ui.item[0];
											$("#bisotab > tbody").append(htmlBOQRowInsertion(itmName, model, partnum, barcode));
											newRowCount =  $("#bisotab >tbody tr").length-1;
								      		boqAutocomplete(newRowCount,"bisotab");
											 $("#formStatus").text("Not Saved");
											 $('.dot').css({"background-color" : "orange"});
										     //$('#barcode').val("");
											 $('#cat3').val("");
											 $('#cat4').val("");
									    	 $('#cat2').val("");
							            	 $('#cat').val("");
							            	 $('#seq').val("");
										     $('table#bisotab tr:last td:first input').focus();
										     rowInputListener((newRowCount+1));
											 getSumQty_totalAT();
										 }

      // finish add reminder$ 
return false;
	}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
            
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>" +","+ 
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";
			if(item[4] != '-')
				appendString += ","+item[4];

			appendString += "</span></div>";

									 
            return $("<li class='each'>").append(appendString).appendTo(ul);
      	};
	$("#barcode").focus(function(){
 	        	if (this.value == ""){
 	            	$(this).autocomplete("search");
 	        	}						
	});

										
					//end

					//cat auto complete
					$("#cat").autocomplete({
						 source: function(request, response) {
								 
							 var cat1 =($("#cat").val().split(":"))[0];
					        	var cat2 = ($("#cat2").val().split(":"))[0];
					        	var cat3 = ($("#cat3").val().split(":"))[0];
					        	var cat4 = ($("#cat4").val().split(":"))[0];
						        	 
						        	
							             $.ajax({
							                 type: "GET",
							                 contentType: "application/json; charset=utf-8",
							                 url: '${pageContext.request.contextPath}/getParentCategory',
							                 data: {
							                	 	"catInput":cat1,
					 			                    "cat2Input":cat2,
					 			                    "cat3Input":cat3,
					 			                    "cat4Input":cat4,
					 						 },
							                 dataType: "json",
							                 success: function (data) {
							                     if (data != null) {
							                         response(data.ListCategory);
							                     }
							                 },
							                 error: function(result) {
							                     alert("Error");
							                 }
							             });
							         }, minLength:0, maxShowItems: 40, scroll:true,		
						               
						        
							         select: function(event, ui) {
							       
								         $("#cat").val(ui.item[0]+":"+ui.item[1]);
								         $("#barcode").val("");
								      

												return false;
													}

					 }).autocomplete("instance")._renderItem = function(ul, item) {
						return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                      item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
                		item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
		                .appendTo(ul)
						};
						$("#cat").focus(function(){
					       	if (this.value == ""){
					           	$(this).autocomplete("search");
					       	}						
						});

						$("#cat2").autocomplete({



							 source: function(request, response) {
									 
							        
								 var cat1 =($("#cat").val().split(":"))[0];
						        	var cat2 = ($("#cat2").val().split(":"))[0];
						        	var cat3 = ($("#cat3").val().split(":"))[0];
						        	var cat4 = ($("#cat4").val().split(":"))[0];
							        	 
							        	
								             $.ajax({
								                 type: "GET",
								                 contentType: "application/json; charset=utf-8",
								                 url: '${pageContext.request.contextPath}/getCat2',
								                 data: {
								                	  "catInput":cat1,
								                	  "cat2Input":cat2,
						 			                    "cat3Input":cat3,
						 			                   "cat4Input":cat4,


						 						 },
								                 dataType: "json",
								                 success: function (data) {
								                     if (data != null) {
								                         response(data.listCat);
								                          
								                     }
								                 },
								                 error: function(result) {
								                     alert("Error");
								                 }
								             });
								         }, minLength:0, maxShowItems: 40, scroll:true,		
							               
							        
									select: function(event, data) {
								         $(this).val(data.item[0]+":"+data.item[1]);
								         $("#barcode").val("");
											return false;
												}
						 }).autocomplete("instance")._renderItem = function(ul, item) {
							 return $("<li class='each'>")
				                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
				                      item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
	                    		item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
				                .appendTo(ul);
							
							};
							$("#cat2").focus(function(){
						       	if (this.value == ""){
						           	$(this).autocomplete("search");
						       	}						
							});
						
							$("#cat3").autocomplete({
						
								 source: function(request, response) {
										 
									 var cat1=($("#cat").val().split(":"))[0];
							        	var cat2 = ($("#cat2").val().split(":"))[0];
							        	var cat3 = ($("#cat3").val().split(":"))[0];
							        	var cat4 = ($("#cat4").val().split(":"))[0];
							        	 
							        
								        	
									             $.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",
									                 url: '${pageContext.request.contextPath}/getCat3',
									                 data: {
									                	  "catInput":cat1,
							 			                    "cat2Input":cat2,
							 			                   "cat3Input":cat3,
							 			                   "cat4Input":cat4,


							 						 },

									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.listCat);
									                     }
									                 },
									                 error: function(result) {
									                     alert("Error");
									                 }
									             });
									         }, minLength:0, maxShowItems: 40, scroll:true,		
								               
								        
										select: function(event, data) {
										 $("#cat3").val(data.item[0]+":"+data.item[1]);
										 $("#barcode").val("");

												return false;
													}

								

								


							 }).autocomplete("instance")._renderItem = function(ul, item) {
								 return $("<li class='each'>")
					                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					                      item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
		                    		item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
					                .appendTo(ul);
								};
								$("#cat3").focus(function(){
							       	if (this.value == ""){
							           	$(this).autocomplete("search");
							       	}						
								});


								$("#cat4").autocomplete({
									
									 source: function(request, response) {
											 
										 var cat1=($("#cat").val().split(":"))[0];
								        	var cat2 = ($("#cat2").val().split(":"))[0];
								        	var cat3 = ($("#cat3").val().split(":"))[0];
								        	var cat4 = ($("#cat4").val().split(":"))[0];
								        	 
								        
									        	
										             $.ajax({
										                 type: "GET",
										                 contentType: "application/json; charset=utf-8",
										                 url: '${pageContext.request.contextPath}/getCat4',
										                 data: {
										                	  "catInput":cat1,
								 			                    "cat2Input":cat2,
								 			                   "cat3Input":cat3,
								 			                  "cat4Input":cat4,
								 						 },
										                 dataType: "json",
										                 success: function (data) {
										                     if (data != null) {
										                         response(data.listCat);
 
										                     }
										                 },
										                 error: function(result) {
										                     alert("Error");
										                 }
										             });
										         }, minLength:0, maxShowItems: 40, scroll:true,		
									               
									        
											select: function(event, data) {
									         $(this).val(data.item[0]+":"+data.item[1]);
									         $("#barcode").val("");
													return false;
														}
								 }).autocomplete("instance")._renderItem = function(ul, item) {
									 return $("<li class='each'>")
						                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
						                      item[0] +", "+item[1]+ "</span><br><span class='desc'>" +
			                    		item[2] + "</span><span class='desc'>" +","+   item[3] + "</span></div>")
						                .appendTo(ul);
									};
									$("#cat4").focus(function(){
								       	if (this.value == ""){
								           	$(this).autocomplete("search");
								       	}						
									});

									$("#seq").autocomplete({
										
										 source: function(request, response) {
												 
											 var cat1=($("#cat").val().split(":"))[1];
									        	var cat2 = ($("#cat2").val().split(":"))[1];
									        	var cat3 = ($("#cat3").val().split(":"))[1];
									        	var cat4 = ($("#cat4").val().split(":"))[1];
									        	var seq = $("#seq").val();
									        	 
									        
										        	
											             $.ajax({
											                 type: "GET",
											                 contentType: "application/json; charset=utf-8",
											                 url: '${pageContext.request.contextPath}/getSequ',
											                 data: {
											                	  "catInput":cat1,
									 			                  "cat2Input":cat2,
									 			                  "cat3Input":cat3,
									 			                  "cat4Input":cat4,
									 			                  "seqInput":seq,
																},
											                 dataType: "json",
											                 success: function (data) {
											                     if (data != null) {
											                         response(data.listCat);
											                     }
											                 },
											                 error: function(result) {
											                     alert("Error");
											                 }
											             });
											         }, minLength:0, maxShowItems: 40, scroll:true,		
										               
										        
												select: function(event, data) {

										         $(this).val(data.item[0]);
										         var itmCode = data.item[0];
										         
													itemExist= checkifItemCodeExist(itmCode);
													 if(itemExist == "true"){
														 checkItemCodeBoqRow(itmCode);
														 $("#formStatus").text("Not Saved");
														 $('.dot').css({"background-color" : "orange"});
														 $('#cat3').val("");
														 $('#cat4').val("");
												    	 $('#cat2').val("");
										            	 $('#cat').val("");
										            	 $('#seq').val("");
										            	 getSumQty_totalAT();
												}
												
												else {
												  
													var itmName=data.item[0]+":"+data.item[1];
													var model =data.item[3];
													var partnum = data.item[4];
													var barcode = "";
													$("#bisotab > tbody").append(htmlBOQRowInsertion(itmName, model, partnum, barcode));
													newRowCount =  $("#bisotab >tbody tr").length-1;
										      		boqAutocomplete(newRowCount,"bisotab");
													 $("#formStatus").text("Not Saved");
													 $('.dot').css({"background-color" : "orange"});
												     //$('#barcode').val("");
													 $('#cat3').val("");
													 $('#cat4').val("");
											    	 $('#cat2').val("");
									            	 $('#cat').val("");
									            	 $('#seq').val("");
												     $('table#bisotab tr:last td:first input').focus();
												     rowInputListener((newRowCount+1));
													 getSumQty_totalAT();	    		   
												}
										           
								return false;
							}
							}).autocomplete("instance")._renderItem = function(ul, item) {
										var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
						                
										item[0] + "</span><br><span class='desc'>" +
										item[1] + "</span><span class='desc'>" +","+ 
										item[2] + "</span><span class='desc'>";
										if(item[3] != '-')
											appendString += ","+item[3] + "</span><span class='desc'>";
										if(item[4] != '-')
											appendString += ","+item[4];

										appendString += "</span></div>";
						                return $("<li class='each'>").append(appendString).appendTo(ul);
											
										};
										$("#seq").focus(function(){
									       	if (this.value == ""){
									           	$(this).autocomplete("search");
									       	}						
										});	
 
	     
	    if ('${docStatus}' != "addNew" ){
			if('${docStatus}' == "addNewFromPRQ" ){
		    	$("#formStatus").text("New");
				$('.dot').css({"background-color" : "orange"});	
				
				 
			}
				
	     }       
       //end clicking on new ADD button no need to laod data 
       else {
       // set status to new and green while new record
           $("#formStatus").text("New");
				$('.dot').css({"background-color" : "orange"});
				FormSave.push("New");
       }
           
     
	 
		var ordStatus = $("#ordstat").val();
		if (ordStatus == 'cancelled'){
		 $('#ordstat').attr('disabled',true);
		 $("#Cancelpo").addClass('disabled');
		 $("#Approvepo").addClass('disabled');
		 $("#Closepo").addClass('disabled');
	     $('#custom-tabs-one-tabContent :input').attr('disabled',true);
 		}
		if (ordStatus == 'approved'){
			 $('#ordstat').attr('disabled',true);
			 $("#Approvepo").addClass('disabled');
		     $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	 		}	
		if (ordStatus == 'completed'){
			 $('#ordstat').attr('disabled',true);
		     $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	 		}						   

            function saveRowsInTables (){
   		     var token =  $('input[name="csrfToken"]').attr('value');    
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/PurchaseOrdFormSave",
					dataType : "json",
			        headers: {
                     'X-CSRFToken': token 
                 },
   					data : {
   					    "type": '${docStatus}',
   					//	"checkSave": checkSave,
   					    "FormSave": FormSave,
   						"dictParameter": dict,
   						"slctDelOrd":slctDelOrd,
   					 	"ID" : $("#ordcode").val(),
   						"creationDate" : $("#ordcreatedate").val(),
   						"lastmodifiedDate" : $("#ordlstmodifdate").val(),
   						"supplier" :  $("#ordsuppid").val(),
   						"ordsupplierName" : $("#ordsuppname").val(),
   						"supplierAddress" : $("#ordsuppaddress").val(),
   						"orderedDate" : $("#ordorderdate").val(),
   						"delivereyDate" : $("#orddeliverydate").val(),
   						"WareHouse" : $("#wareid").val(), 
   						"WareName" :  $("#warename").val(),
   						"siteID" : $("#siteid").val(),
   						"TotalAmount" :  $("#ordtotamnt").val(),
   						"discAmount" : $("#orddiscamnt").val(),
   						"discountPercent" : $("#orddiscpercent").val(),
   						"paidAmount" : $("#ordpaidamnt").val(),
   						"ordOutstanding" : $("#ordoutstand").val(),
   						"ordStatus" : $("#ordstat").val(),
   						"netTotal" : $("#ordNetTotal").val(),
   						"netTotalinWord" : '0',
   						"TotalQty" : $("#ordtotqty").val(), 
   						"FarNo" : '0',
						"DarNo" : '0',
   						"ordPRQid" : $("#purchord").val(),
   						"email": $("#email").val(),
						"password":$("#password").val(),
				  	    "emailTo": $("#emailTo").val(),
					    "ccmail": $("#ccmail").val(),
					    "subject": $("#subject").val(),
					    "message": $("#message").val(),
					    "pOrdAppFlag" : pOrdAppFlag,
			 			"pOrdCnclFlg" : pOrdCnclFlg,
			 			"allDeletedSerialsArray":allDelSerials
			 			
   					},
   					success : function(data) {
   						$('#ordlstmodifdate').val(data.lastmodifiedDate);
						if(ordStatus == 'approved')	$("#Closepo").removeClass('disabled');	
						var param;		
						allDelSerials=[];									   
   						// update dot save to green 
  					   	ordcode.value=data.POID;
   						
   						
   							if(data.RevGrStatCncl){
   	   							console.log("done");
   							
   							if(data.RevGrStatCncl == "rejected")
   								alert("The purchase order has been saved, but cancelling didn't occur because the GoodsReceived  "+data.GRtobeCncld+" that is related to this PO needs cancellation, thank you!");
   								
   							else
   								alert("Cancelling occured, thank you.");
   								}
   							else if(data.RevPrcStatAprv)
   							{
						if(data.RevPrcStatAprv == "rejected")
							alert("The purchase order has been saved, but approvement didn't occur because the purchase request "+data.PRtobeAprvd+" that is related to this PO needs approvement, thank you!");
						else 
    						alert("Approved occured, thank you.");
							unsaved = false;
   							}
						
						param ="${pageContext.request.contextPath}/PurchaseOrderFormView?ID="+$("#ordcode").val()+"&NavAction=2";
   						location.replace(param);
   						
   					},
   					error : function(error) {
   						console.log("The error is " + error);
   					}
   				});
   	   				
        } // end of saveRowsInTables    	
    			     
    if('${SelectedIndex}' != "addNew" && '${poCount}' != "addNew"){
    	if('${SelectedIndex}' != "addNewFromPRQ" && '${poCount}' != "addNewFromPRQ"){
			var SelectedIndex = ${SelectedIndex};
			var poCount = ${poCount};

						
			if(($("#ordcode").val()) != "" && ($("#ordcode").val()) != null){
				$("#label-1").text((SelectedIndex)+"/"+poCount);
				if(SelectedIndex === poCount){
	        		document.getElementById("btnLast").style.opacity = 0.5;
	        		$("#btnLast").hasClass("disabled");
	        		document.getElementById("btnLast").style.pointerEvents = "none";	        		
	        		document.getElementById("btnNexta").style.opacity = 0.5;
	        		document.getElementById("btnNexta").style.pointerEvents = "none";
					$("#btnNexta").hasClass("disabled");
				}else{
					if(!$("#btnNexta").hasClass("disabled")){						
						$("#btnNext").click(function(){
							var param ="${pageContext.request.contextPath}/PurchaseOrderFormView?ID="+$("#ordcode").val()+"&NavAction=1";
							window.location.href =param;
						});
					}
					if(!$("#btnLst").hasClass("disabled")){        				
        				$("#btnLst").click(function(){
							var param ="${pageContext.request.contextPath}/PurchaseOrderFormView?ID="+$("#ordcode").val()+"&NavAction=4";
        					window.location.href =param;
        				});
        			}
				}			
				if(SelectedIndex === 1){ //first record in database				
	        		document.getElementById("btnFirst").style.opacity = 0.5;
	        		$("#btnFirst").hasClass("disabled");
	        		document.getElementById("btnFirst").style.pointerEvents = "none";	        		
	        		document.getElementById("btnPrva").style.opacity = 0.5;
	        		$("#btnPrva").hasClass("disabled");
	        		document.getElementById("btnPrv").style.pointerEvents = "none";
				}else{
					if(!$("#btnPrva").hasClass("disabled")){					
						$("#btnPrv").click(function(){						
							var param ="${pageContext.request.contextPath}/PurchaseOrderFormView?ID="+$("#ordcode").val()+"&NavAction=0";
							window.location.href =param;
						 });
					}
					$("#btnFrst").click(function(){
        				if(!$("#btnFrst").hasClass("disabled")){
							var param ="${pageContext.request.contextPath}/PurchaseOrderFormView?ID="+$("#ordcode").val()+"&NavAction=3";
	        				window.location.href =param;
        				}
        			});
				}			
			} //end if checking #ordcode 
			 $("#selectnav").autocomplete({
	    			
	    		    source: function(request, response) {
	    			    
	    		             $.ajax({
	    		                 type: "GET",
	    		                 contentType: "application/json; charset=utf-8",
	    		                 url: '${pageContext.request.contextPath}/GetAllPo',
	    		                 data: {
	    								"ID" : $("#selectnav").val(),
	    						 },
	    		                 dataType: "json",
	    		                 success: function (data) {
	    		                     if (data != null) {
	    		                         response(data.Listreq);
	    		                     }
	    		                 },
	    		                 error: function(result) {
	    		                     alert("Error");
	    		                 }
	    		             });
	    		         }, minLength:0, maxShowItems: 40, scroll:true,

	    					select: function(event, ui) {
	    	    						
	    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
	    						var param ="${pageContext.request.contextPath}/PurchaseOrderFormView?ID="+(ui.item[0])+"&NavAction=2";
	    						window.location.href =param;
	           						
	           						return false;
	           					}
	           				}).autocomplete("instance")._renderItem = function(ul, item) {
	           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
	           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	           	                 item[1] + '</span><br><span class="desc">' +
	           	                 item[0] + '</span></div></li>').appendTo(ul);
	           			};
	           					$("#selectnav").focus(function(){
	           		   	        	if (this.value == ""){
	           		   	            	$(this).autocomplete("search");
	           		   	        	}						
	           					});
					}
		}   //// ENd of Autocomplete for Area ID							  
    }); //  end $(function(){ of the ready function.
}); //end ready document

</script>
 </html>
      						