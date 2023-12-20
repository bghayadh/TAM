<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>GR Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	  <script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
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
	<script src="${pageContext.request.contextPath}/resources/js/GoodsRcv/GR_BoqPopup.js"></script>
	
    
     <!--  style used for prrqitem table  -->    
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
    
    .hide-row { display:none; }
				.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					width: 350px;
				    z-index:9999;
					
					
	        		}
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
				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				   
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
				
#wrapper {
				   width:100%;
  					display: grid;
 					max-height:100px;
  					grid-gap: 100px;
  					grid-column-gap:10px!important;
  					grid-row-gap:8px!important;
  					display:flex;
  					grid-template-rows:1fr 1fr!important;
  					grid-template-columns:1fr 1fr 1fr!important;
  					
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
 
   .modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
    }
    
.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 500px;
  overflow : auto;
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
	        		 
 	</style>



        
       
            
</head>
<body>


	<c:set var="pg" value="inventory" scope="session" />

	<jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
     <p></p>
<div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Goods Received</span>
							<input type="text"  readonly id="grcode"  value="${ID}" class="form-control text-input"   />
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
							
														
						</div>

					</div>
			</div>
		
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Status</span>
				<!--  <input type="text" id="grstat" value="${grStatus}" class="form-control text-input" style="width:500px;" />-->
				<select id="grstat" class="form-control">
								<option value="inprog" <c:if test = "${grStatus =='inprog'}" > selected </c:if> >In Progress</option>
								<option value="approved" <c:if test = "${grStatus =='approved'}" > selected </c:if> >Approved</option>
								<option value="completed" <c:if test = "${grStatus =='completed'}" > selected </c:if>>Completed</option>
								<option value="cancelled" hidden<c:if test = "${grStatus =='cancelled'}" > selected </c:if>>Cancelled</option>
								
								</select>
				</div>							
			</div>
			
		<div class="pad col-md-3 hide-row"></div>   
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other GR</span>
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
	<p></p>
	
			
			
			
			
			
			
			<div class="row">
			
								
	<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
					<input type="text" id="grcreatedate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="grlstmodifdate" readonly value="${lastmodifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
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
							style="margin-left: 3px;width: 53px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>			    		

			  		</ul>
				</nav>
		</div>
			
			
			
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/GoodsRcvListView"'
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
	<p></p>
	
	</div>		

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
      		
            <li id="Buttons" class="nav-item ml-auto">
             <div class="dropdown" Style="display:inline-block;">
	           <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	                 aria-haspopup="true" aria-expanded="false">Actions</button>
	
	            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	            <a class="dropdown-item"  type="button" id="Approvegr">Approve</a>
	             <a class="dropdown-item" type="button" id="Cancelgr" >Cancel</a>
    	         </div>
    	         <button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
              
               </div>
				<button type="button" id="deleteButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-trash"></i> Delete
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
							<span class="input-group-text">Supplier ID</span>
							<input type="text" id="grsuppid" value="${grsupplierId}"  style="width:400px; height:37.5px;" class="ui-widget ui-widget-content ui-corner-all form-control" />
						</div>
					</div>
					</div>			
	
	<div class="col-md-4">
			<div class="form-group"style="align-items: center;">
				<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Order Date</span>
					<input type="text" id="grorderdate" value="${orderedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
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
					<span class="input-group-text">Delivery Date</span>
					<input type="text" id="grdeliverydate" value="${delivereyDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
					   <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
			</div>
		</div>
	
	</div>
	

						
						
												
	<div class="row">
	
	
	
	
	<div class="col-md-6">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Supplier Name</span>
							<input type="text" id="grsuppname" value="${supplierName}"  style="width:610px; height:37.5px;" class="ui-widget ui-widget-content ui-corner-all form-control" />
						</div>
					</div>
					</div>
		  	  	<div class="col-md-6">
						<div class="input-group-prepend">
							<span class="input-group-text">Supplier Address</span>
							<input type="text" id="grsuppaddress" value="${supplierAddress}"  class="form-control text-input"/>
						</div>
						
					
	</div>
	
	
	
		 </div>					
									     
<div class="row">
		<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span style="width:120px;" class="input-group-text" >WareHouse ID</span>
							<input type="text" id="wareid" value="${WareId}"  style="width:235px; height:37.5px;" class="ui-widget ui-widget-content ui-corner-all form-control" />
						</div>
					</div>
			</div>
			
			
			 <div class="col-md-5">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> Warehouse Name</span>
							<input type="text" id="warename" value="${WareName}" style="width:550px; height:37.5px;" class="ui-widget ui-widget-content ui-corner-all form-control" />
						</div>
					</div>
			</div>
			
			
			
			 <div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Site ID</span>
							<input type="text" id="siteid" value="${SiteId}" style="width:550px; height:37.5px;" class="ui-widget ui-widget-content ui-corner-all form-control" />
						</div>
					</div>
			</div>	
				
								
		
		</div>
		
	<div class="row">	
 	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Purchase Order</span>
					<input type="text" id="poid" value="${grPOid}"  style="width:400px; height:37.5px;" class="ui-widget ui-widget-content ui-corner-all form-control" />
				</div>
			</div>
			</div>
			
					
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Purchase Request</span>
					<input type="text" id="prid" value="${grPRQid}"  style="width:400px; height:37.5px;" class="ui-widget ui-widget-content ui-corner-all form-control" />
				</div>
			</div>
		</div>
	</div>



    	<!--  create table goodsreceivedItem    -->

		<div> 
 
				<form>
			 
					    <div class="table-responsive-sm"> 
						    <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr class="fixed-headerr">
						                <th>
						                <div class="container">
								          <button type="button" id="selectAll" class="main">
								          <span class="sub"></span>Select</button></th>
						                <th>Item</th>
						                
						                <th>Item Model</th>
						                <th>Item Part Number</th>
						                <th>Qty</th>
						                <th>Rate</th>
						                <th>Discount Amount</th>
						                <th>Tax</th>
						                <th>Net Rate</th>
						                <th>Total</th>
						                <th>Total AT</th>
						                <th>PO</th>
						                <th>PR</th>
						                <th>AR</th>
						                <th>CIP</th>
						                <th>FAR</th>
						                <th>GR Item ID</th>
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

	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Amount</span>
						<input type="text" id="grtotamnt" value="${TotalAmount}" readonly class="form-control text-input" />
					</div>
				</div>
	</div>
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Discount Amount</span>
						<input type="text" id="grdiscamnt" value="${discAmount}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-2">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Discount percent</span>
						<input type="text" id="grdiscpercent" value="${discPercent}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	 <div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Total QTY</span>
				<input type="text" id="grtotqty" value="${TotalQty}" readonly class="form-control text-input" />
				</div>							
		</div>
	</div>
	<div class="row">
	
	    <div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Net Total</span>
				<input type="text" id="grtotword" value="${Nettotal}"  readonly class="form-control text-input"  />
			</div>
		</div>
	    
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Paid Amount</span>
						<input type="text" id="grpaidamnt" value="${paidAmount}" class="form-control text-input"  />
					</div>
				</div>
		</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text">Outstanding</span>
					<input type="text" id="groutstand" value="${grOutstanding}"  readonly class="form-control text-input"/>
					</div>
				</div>
		</div>
	
		
	</div>

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
                   
                  <th style="width:50%;align:left;margin:5px;"></th> <th id="grPrStatus" style="width:50%;margin:5px;height:35px;"> </th>
                 
                   <tr>
                   
                   <td class="TD">	<b>Purchase Request ID </b>	
                   <td id="grPurchReqId" class="TD">		
                   
                   
                   </tr>
               		
               		<tr>
               		
               		<td class="TD">	<b>Net Total Amount</b>	
               		<td id="grPrNetTot" class="TD">		
               		
               		
               		</tr>
                 
                   <tr >
                   
                   <td>	<b>Total Quantity</b>	
                   <td id="grPrTotQty">		
                   	
                   
                   </tr>
                  
                   </table>
 
                </div>
            </div>
       
   
        
            <div id="two" class=" mx-auto">
     
                <div class="card-header divv1"><i class="fas fa-search-plus"style="font-size:20px;"></i><b> DISCOVERY NEW </b></div>
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
    
                <div class="card-header divv1"><i class="fas fa-file-invoice-dollar"style="font-size:20px;"></i><b> PURCHASE ORDER</b></div>
                <div class="card-body mycard divv2">
                   
                    <table>
                   
                  <th style="width:50%;align:left;margin:5px;"></th> <th id="grPoStatus" style="width:50%;margin:5px;height:35px;"> </th>
                 
                   <tr>
                   
                   <td class="TD">	<b>Purchase Order ID </b>	
                   <td id="grPurchOrdId" class="TD">	
                   
                   
                   </tr>
               		
               		<tr>
               		
               		<td class="TD">	<b>Net Total Amount</b>	
               		<td id="grOrdnetTot" class="TD">	
               		
               		
               		</tr>
                 
                   <tr >
                   
                   <td>	<b>Total Quantity</b>	
                   <td id="grOrdtotQty">		
                   	
                   
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
                   
                   <td  class="TD">	<b>No of CIPs</b>	
                   <td id="grCipCountAll" class="TD">	
                   
                   
                   </tr>
                   
               		 <tr  ><td class="TD"><b>CIP Valuation Rate</b></td>
               		 <td id="grCipNetTotAll" class="TD"></tr>
               		 
               		<tr >
               		
               		<td>	<b>Total Quantity</b>	
               		<td id="grCipTotQtyAll">		
               		
               		
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
                   <td id="grArCountAll" class="TD">		
                   
                   
                   </tr>
               		
               		<tr>
               		
               		<td>	<b>Sum of Valuation Rate</b>	
               		<td id="grArValRateAll" >		
               		
               		
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
                   <td class="TD">	<b>    No of FARs   </b>	
                   <td id="farCount" class="TD">
                   <td id="farNotRunCount" class="TD">
                    </tr>   
                                   
                   <tr> 
                   <td class="TD">	<b>    Initial cost    </b>	
                   <td id="grFarCountAll" class="TD"> 
                   <td id="farNotRunIntCost" class="TD"> 
                    </tr>
               		
               		<tr>
               		
               		<td>	<b>   Net cost    </b>	
               		<td id="grFarValRateAll">   
               		<td id="farNotRunNetCost">
               		
               		</tr>
                 
                  
                   </table>
                </div>
            </div>
        </div>
        </div>
        
        
        
        
        
        
			</div>
		</div>
		
<!-- GR Popup--> 
<div class="container">
	<div id ="grModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
					<div class="modal-header" style="background-color: #007BFF; height: 56px">
			<h5 id ="popupNb" class="modal-title" style="font-weight:bold; color: gold;position:relative;top:4px;"></h5>
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
   						<input type="text" id="popupItem" value="${popupItem}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  </div></div></div>
  


</div>     </div>

<div class="container-fluid">
	<div class="row">
	
	<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text" >Item Model</span>
				<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupItemModel" value="${popupItemModel}" style="width:675px; height:37px"  />					
</div></div></div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Part No</span>
						<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupItemPart" value="${popupItemPartno}" style="width:674px; height:37px"  />
</div></div></div>





</div>    </div>					
						
								
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Qty</span>
						<input type="text" id="popupQty" class="form-control text-input" value="${popupQty}" style="width:120px; height:39px" />
				</div></div></div>
				
					
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text" >Discount Amount</span>
				<input type="text" id="popupDiscount" class="form-control text-input" value="${popupDiscount}" style="width:200px;" />
						
					</div></div></div>

<div class="col-sm-3">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Tax</span>
				<input type="text" id="popupTax" class="form-control text-input" value="${popupTax}" style="width:110px;"  />  
						
				</div></div></div>
	</div> </div>
	
	

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Rate</span>
						<input type="text" id="popupRate" class="form-control text-input" value="${popupRate}" style="width:675px;"  />
					</div></div></div>
				
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Net Rate</span>
				<input type="text" id="popupNetRate" class="form-control text-input"  value="${popupNetRate}" readonly style="width:675px;" />
					</div></div></div>
				
	
		</div></div>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Total</span>
						<input type="text" id="popupTotal" class="form-control text-input" value="${popupTotal}" readonly style="width:675px;"  />
						</div></div></div>					      

	<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
					
						<span class="input-group-text">Total AT</span>
						<input type="text" id="popupTotalAt" class="form-control text-input" readonly value="${popupTotalAt}" style="width:675px;"   />
						
					</div>
				</div>
	</div>
								</div>     </div>
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
						<span class="input-group-text" >PO</span>
						<input type="text" id="popupPoQty" class="form-control text-input" value="${popupPoQty}" readonly style="width:120px;"  />
						
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
</div></div>
								      

<div class="container-fluid">
<div class="row">
	<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >FAR</span>
						<input type="text" id="popupFarQty" class="form-control text-input" readonly value="${popupFarQty}" style="width:111px;"  />
					</div>
				</div>
	</div>
</div></div>	      
	

   </div>

  
  <div class="tab-pane" id="serialNum" role="tabpanel" aria-labelledby="serialNum-tab">
  <div>
  <p></p> 
	<form>
		<div class="table-responsive-sm"> 
			<table id ="serialNoTable" class="table table-striped table-bordered table-sm" style="display:block; height:320px; overflow-y: auto;">	
				<thead>
					<tr>
					<th> 
						<button type="button" id="selectAllSerial" class="main">
						<span class="sub"></span>Select</button>
								         
					  </th>
					 <th width="232px">Serial Number</th>
					 <th width="232px">Item Model</th>
                     <th width="232px">Item Part Number</th>
						              
					</tr> </thead> <tbody> </tbody> </table>
					</div>
						<button type="button" class="add-row-serial" onclick="addRowSerial()">Add Row</button>
						<button type="button" class="delete-row-serial">Delete Row</button>
					</form>
					</div> 
					
 </div></div>
  </div>
					
<div class="modal-footer">
</div>	
		 </div>
	 </div>
 </div>
</div>


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
		 
	 	boqArray = ${ListPRqItem};
		for (i = 0; i < boqArray.length; i++) {
			boqAutocomplete(i);
			
		}
		boqInputsListener();
	 });
 });
 </script>
 <script  type='text/javascript'>
//  $(function(){
 if ('${docStatus}' != "addNew" ){
		
		 
	boqArray = ${ListPRqItem};
	
	var itemRow="";  
	for (i = 0; i < boqArray.length; i++) {
		//buildingSavedBOQ(boqArray[i]);
		var itemModel = boqArray[i].itemModel;

		if (itemModel == null)
			itemModel = "";
		else
			itemModel = boqArray[i].itemModel;

		var itemPartNumber = boqArray[i].itemPartNumber;
		if (itemPartNumber == null)
			itemPartNumber = "";
		else
			itemPartNumber = boqArray[i].itemPartNumber;
		var dotStatus = (boqArray[i].grStatus);

		var span;

		if ('${grStatus}' == "completed")

		//if (dotStatus == "1") 
		{

			span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: chartreuse;'></span>";
		} else {

			span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: orange;'></span>";
		}

		var serialArrays = [];
		if (boqArray[i].serial_obj != null) {
			serialArrays
					.push(boqArray[i].serial_obj);
		} else {
			serialArrays.push(null);
		}

		itemRow += "<tr>"
		 + "<td><input type='checkbox' name='record'>"	+ span + "<button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:1px;'><i class='fas fa-desktop'></i></button></td>"
		
				+ "<td name='itemCode'><input type='text' name='itmCode' value='" + boqArray[i].itemCode +":"+ boqArray[i].itemName + "' style='width:200px;' class='form-control text-input'/></td>"
		
				+ "<td name='grItemModel'><input name='itmModel' type='text' value='" + itemModel + "' style='width:200px;' class='form-control text-input'/></td>"
		
				+ "<td name='grItemPartNo'><input name='itmPartNo' type='text' value='" + itemPartNumber + "' style='width:200px;' class='form-control text-input'/></td>"
		
				+ "<td name='qty'><input name='qty' type='text'  value='" + boqArray[i].qty +"' style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='rate'><input name='rate' type='text'  value='" + boqArray[i].rate +"' style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='discountAmount'><input  name='discountAmount' type='text' value='" + boqArray[i].discountAmount +"'  style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='tax1'><input name='tax1' type='text' value='" + boqArray[i].tax1 +"'  style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='netRate'><input type='text' value='" + boqArray[i].netRate +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='total'><input type='text' value='" + boqArray[i].total +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='totalAt'><input type='text' value='"  + boqArray[i].totalAt +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='poQty'><input type='text' value='" + boqArray[i].poQty +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='prQty'><input type='text' value='" + boqArray[i].prQty +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='arQty'><input type='text' value='" + boqArray[i].arQty +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='cipQty'><input type='text' value='" + boqArray[i].cipQty +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='farQty'><input type='text' value='" + boqArray[i].farQty +"' readonly style='width:200px;' class='form-control text-input'></td>"
		
				+ "<td name='grItemId'><input type='text' value='" + boqArray[i].grItemId +"'readonly style='width:200px;' class='form-control text-input'><input type='text' name='grStatus' value='" + boqArray[i].grStatus +"'hidden></td>"
		
				+ "<td name='serialNo'><input type='text'  style='width:200px;' value='" + serialArrays[0] +"'hidden class='form-control text-input'></td>"
				 + "</tr>";

		//var loadRowCount = $("#bisotab >tbody tr").length;
		}
	$("#bisotab > tbody").append(itemRow);
	 $(document).trigger("triggerBoqListenersEvent");
		
 }
//  });
 </script>
 
 <script type='text/javascript'>
 
 
// /////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
// $("#sendEmail").on("click", function () {
// console.log("Helloooo in sendEmail onClick");
// $("#popUpDiv").fadeIn();

// });

// $("#cancelButton").on("click", function () {
// console.log("Helloooo in cancelButton onClick");
// $("#email").val('');
// $("#password").val('');
// $("#emailTo").val('');
// $("#ccmail").val('');
// $("#subject").val('');
// $("#message").val('');
// $("#popUpDiv").fadeOut();
// });

// $("#send").on("click", function () {
// console.log("Helloooo in send onClick");
// if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
// {
// alert("All Fields are required");
// }
// else{
// $("#popUpDiv").fadeOut();
// }

// });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
 var FormSave=[];
 if ('${navStatus}' == "addNew") {
		$("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});	
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");	}


 //////////////////////////////////////////////////////////

 $("#custom-tabs-overview").click(function() {

	 if($("#grcode").val()!=""){

		$.ajax({
	        type: "GET",
	        contentType: "application/json; charset=utf-8",
	        url: '${pageContext.request.contextPath}/GetGRDATA',
	        data: {
	            
					"ID" : $("#grcode").val(),
	        },
	        dataType: "json",
	        success: function (data) {

	        	$('#grPurchReqId').text(data.grPurchReqId);
            	$('#grPrStatus').text(data.grPrStatus);
            	$('#grPrNetTot').text(data.grPrNetTot);
            	$('#grPrTotQty').text(data.grPrTotQty);

            	$('#grPurchOrdId').text(data.grPurchOrdId);
            	$('#grPoStatus').text(data.grPoStatus);
            	$('#grOrdtotQty').text(data.grOrdtotQty);
            	$('#grOrdnetTot').text(data.grOrdnetTot);

            	$('#dnComp').text(data.dnComp);
            	$('#dnNetTotComp').text(data.dnNetTotComp);
            	$('#dnTotQtyComp').text(data.dnTotQtyComp);

            	$('#dnNotComp').text(data.dnNotComp);
            	$('#dnNetTotNotComp').text(data.dnNetTotNotComp);
            	$('#dnTotQtyNotComp').text(data.dnTotQtyNotComp);


            	$('#grCipCountAll').text(data.grCipCountAll);
            	$('#grCipNetTotAll').text(data.grCipNetTotAll);
            	$('#grCipTotQtyAll').text(data.grCipTotQtyAll);

            	$('#grArCountAll').text(data.grArCountAll);
            	$('#grArValRateAll').text(data.grArValRateAll);

            	$('#grFarCountAll').text(data.grFarCountAll);
            	$('#grFarValRateAll').text(data.grFarValRateAll);

            	$('#farCount').text(data.farCount);
            	$('#farNotRunCount').text(data.farNotRunCount);
            	$('#farNotRunIntCost').text(data.farNotRunIntCost);
            	$('#farNotRunNetCost').text(data.farNotRunNetCost);
	            		
	            	
	        },		
	        error: function(result) {
	            alert("errror somewhere");
	        }
	    });
	 }
		});

			$("#grstat").change(function() {
				var grStatus = $("#grstat").val();
		  		  
			 	if(grStatus == 'completed'){
			 		 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
				 	 $('#grstat').attr('disabled',true);
			 		 $("#Approvegr").addClass('disabled'); }
		 		else if(grStatus == 'inprog') 
			 		 $("#Approvegr").removeClass('disabled');
		 		
		 		else if (grStatus == 'cancelled'){
					 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
					 $('#grstat').attr('disabled',true);
					 $("#Cancelgr").addClass('disabled');
					 $("#Approvegr").addClass('disabled');
				
				 }
		 		else $("#Approvegr").removeClass('disabled');
			 		 





				
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
 
			$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
   
			 $("#datetimepicker3").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
			$("#datetimepicker4").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
				
			$("#grsuppname,#grsuppid,#wareid,#warename,#siteid").on('keyup change focus', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
						
	
			
			$("#poid, #prid, #popupItem, #popupItemModel, #popupItemPart").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
			
			$(".add-row-serial").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});

			$(".delete-row-serial").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
				
			$(".add-row").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
				
			$(".delete-row").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	
			
			$("#bisotab > tbody").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	
			
			 
 
	$(document).ready(function() {


// 		 $(function(){


		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${GoodsRcvCount}' != "addNew"){

							
					var GoodsRcvCount = ${GoodsRcvCount};
					
					if((+ $("#grcode").val()) != "" && (+ $("#grcode").val()) != null){

					if(SelectedIndex === GoodsRcvCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									
									var param ="${pageContext.request.contextPath}/GoodsRcvFormView?ID="+ $("#grcode").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/GoodsRcvFormView?ID="+ $("#grcode").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/GoodsRcvFormView?ID="+ $("#grcode").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/GoodsRcvFormView?ID="+ $("#grcode").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}
					$("#label-1").text((SelectedIndex)+"/"+GoodsRcvCount);


			            $("#selectnav").autocomplete({
		    			
		    		    source: function(request, response) {
		    			    
		    		             $.ajax({
		    		                 type: "GET",
		    		                 contentType: "application/json; charset=utf-8",
		    		                 url: '${pageContext.request.contextPath}/GetAllGdRcvs',
		    		                 data: {
		    								"gdrcv" : $("#selectnav").val(),
		    						 },
		    		                 dataType: "json",
		    		                 success: function (data) {
		    		                     if (data != null) {
		    		                         response(data.listGdRcvs);
		    		                     }
		    		                 },
		    		                 error: function(result) {
		    		                     alert("Error");
		    		                 }
		    		             });
		    		         }, minLength:0, maxShowItems: 40, scroll:true,

		    					select: function(event, ui) {
		    						
		    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
		    						
		    						var param ="${pageContext.request.contextPath}/GoodsRcvFormView?ID="+(ui.item[0])+"&NavAction=2";
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
		           					});   //// ENd of Autocomplete for Area ID
		    	





		var grStatus=$("#grstat").val();
		var unsaved = false;
		var grApproveFlag = 0;
		var grCancelFlag = 0;
		if(grStatus == 'completed' )
 			 $("#Approvegr").addClass('disabled');
 		else $("#Approvegr").removeClass('disabled');

		$(window).bind('beforeunload', function() {
		    if(unsaved){
		        return "You have unsaved changes. Do you want to leave and discard?";
		    }
		});

		$(document).on('change', ':input', function(){
		    unsaved = true;
		});
		var saveButton = '<button type="button" id="saveButton" class="btn btn-primary BtnActive"><i class="fa fa-save"></i> Save </button>';
		var ammendButton = '<button type="button" id="ammendButton" class="btn btn-primary BtnActive"><i class="fa fa-ammend"></i> Ammend </button>';

		if($("#grcode").val() == "") $("#Cancelgr").addClass('disabled');
		if(grStatus == "cancelled")
			
		$("#Buttons").append(ammendButton);
		
		else

		$("#Buttons").append(saveButton);

		$("#Approvegr").click(  function() {
			grApproveFlag = 1;	
			grCancelFlag = 0;
			FormSave.push("Approve");
			 var grStatus=$("#grstat").val();
			var checkSaving = true;
			 
			 $(function(){
			 		checkSaving = checkedDataOnAction();
			 		if(checkSaving !== false){
			 			getSelectedRows ();
			 			saveRowsInTables(); 
			 		}
			 	});
			 });
		
		 $("#Cancelgr").click(  function() {
			 grCancelFlag = 1;
			 grApproveFlag = 0;
			 FormSave.push("Cancel");
			 unsaved = false;	
			 var grStatus=$("#grstat").val(); 			 
 			var checkSaving = true;
			 
			 $(function(){
			 		checkSaving = checkedDataOnAction();
			 		if(checkSaving !== false){
			 			getSelectedRows ();
			 			saveRowsInTables(); 
			 		}
			 	});
			 }); // end of Cancel Action

		 $(document).on('click', '#ammendButton', function() {
				
			 $("#ammendButton").remove();
			 $('#custom-tabs-one-tabContent :input').attr('disabled',false);
			 $("#formStatus").text("Not Saved");
		     $('.dot').css({"background-color" : "orange"});
			 $("#Buttons").append(saveButton);
			 $("#grstat").val('inprog').trigger('change');
			});
		
		
			var dict = [];
		
		//save rowsin tables
		//start of Main Save
		 $(document).on('click', '#saveButton', function() {
			 FormSave.push("Save");
			 unsaved = false;
			 var checkSaving = true;
			 
			 $(function(){
			 		checkSaving = checkedDataOnAction();
			 		if(checkSaving !== false){
			 			getSelectedRows ();
			 			saveRowsInTables(); 
			 		}
			 	});
			 
			 }); // end of Main Save
			 
		  
		 
		 
		 $("#deleteButton").click(  function() {
			 unsaved = false;
			 getSumQty_totalAT();
			 
			 
			 //delete all form request item when related to theis deleted GR
				$.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/DeleteGoodsReceive",
						dataType : "json",
						data : {
							"ID" :  [$("#grcode").val()]
						},
						success : function(data) {
							console.log("The returned data is " +data.testGoods);
							location.replace("${pageContext.request.contextPath}/GoodsRcvListView");
						},
						error : function(error) {
							console.log("The error is " + error);
						}
					});
					 
			    	
		 });
        //auto complete for supplier name	
        
	
		

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
							
							siteid.value = (ui.item ? ui.item[2]   : '');



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
				                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
				                     item[2] + "</span><br><span class='desc'>" 
				                    )
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
													  "warehouseName" : $("#warename").val(),					 
	
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
							               
							                 WareId = ui.item[0];
							                 if(data.globalList.length == 1){
							                	 	
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

	$("#grsuppid").autocomplete({
    
    source: function(request, response) {

         var supplierName=$("#grsuppname").val();
        
             $.ajax({
                 type: "GET",
                 contentType: "application/json; charset=utf-8",
                 url: '${pageContext.request.contextPath}/GetAllSupplier',
                 data: {
						"supplierId" : $("#grsuppid").val(),
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
				grsuppid.value = (ui.item ? ui.item[0]  : '');
				grsuppname.value=ui.item[1];
				grsuppaddress.value= ui.item[2];
				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[0] + "</span><br><span class='desc'>" +
                    item[1] + "</span></div>")
                .appendTo(ul);
        	};
			$("#grsuppid").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});




			$("#grsuppname").autocomplete({
			    
			    source: function(request, response) {

				    var psupplierId= $("#grsuppid").val();
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllSupplierName',
			                 data: {
									"supplierId" :psupplierId,
									"supplierName":$("#grsuppname").val(),
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
							grsuppname.value = (ui.item ? ui.item[0]  : '');
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
					              
					                console.log("The list is "+data.ListSuppIds[0]) ;

					                SuppID = data.ListSuppIds[0];

					                if(data.ListSuppIds.length == 1){
					                	 
					                	$("#grsuppid").val(SuppID);
					                	grsuppaddress.value = ui.item[1];

						                }

					                else{

					                	$("#grsuppid").val("");
					                	$("#grsuppaddress").val("");
					                	

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
						$("#grsuppname").focus(function(){
			   	        	if (this.value == ""){
			   	            	$(this).autocomplete("search");
			   	        	}						
						});
							
	
	/////////////end autocpmplete for warhouse
	///////auto complete for purchase order 
		
		$("#poid").autocomplete({
        
        source: function(request, response) {
            
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllPurchaseOrders',
	                 data: {
		                    "PreqId":$("#prid").val(),
							"ID" : $("#poid").val(), // Typing ID for PO
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.Listreq);
	                         console.log('data is ;'+ data.Listreq);

	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 40, scroll:true,		
               
	         select: function(event, ui) {
	        	    poid.value = (ui.item ? ui.item[0] : '');
	        	    prid.value=ui.item[1];
					return false;
						}
			    }).autocomplete("instance")._renderItem = function(ul, item) {
						return $("<li class='each'>")
			            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                item[0] + "</span></div>")
			            .appendTo(ul);
	        	};
				$("#poid").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				}); /// //end auto complete for purchase order

	    

		//auto complete for purchase request 
		$("#prid").autocomplete({
	        source: function(request, response) {
	        	var poId= $("#poid").val();
	              
	               if(poId==""){
	                    
	                   poId="empty";
	                   }
	             
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllPurchaseRequests',
		                 data: {
								"ID" : $("#prid").val(),
								 "PoId":poId,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.Listreq);
		                         console.log('data is ;'+ data.Listreq);

		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 4, scroll:true,		

		         select: function(event, ui) {
		        	    prid.value = (ui.item ? ui.item.value : '');

		        	    $.ajax({
			                type: "GET",
			                contentType: "application/json; charset=utf-8",
			                url: '${pageContext.request.contextPath}/GetORDID',
			                data: {
									preqID : prid.value,
							 },
			                dataType: "json",
			                success: function (data) {
			                    if (data != null) {
				                console.log("The list is "+data.ListOrdIds[0]) ;

				                POID = data.ListOrdIds[0];

				                if(data.ListOrdIds.length == 1){
				                	 
				                	$("#poid").val(POID);
				                	

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
		                    item.value + "</span></div>") //item[0]
		                .appendTo(ul);
		        	};
					$("#prid").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});

		/////////////end autocpmplete for purchase request
		
	// load data in table
	    // when clicking on new ADD button no need to laod data
	  
	    if ('${docStatus}' != "addNew" ){
			if('${docStatus}' == "addNewFromPRQ" || '${docStatus}' == "addNewFromPO"){
		    	$("#formStatus").text("New");
				$('.dot').css({"background-color" : "orange"});	
			}
			 
// 		boqArray = ${ListPRqItem};
		
    	  
// 		for (i = 0; i < boqArray.length; i++) {
// 			buildingSavedBOQ(boqArray[i]);
// 			}


			} //end clicking on new ADD button no need to laod data   
					else {
						// set status to new and green while new record
						$("#formStatus").text("New");
						$('.dot').css({
							"background-color" : "orange"
						});
						FormSave.push("New");
					}

					//function to  get selected rows for save
					function getSelectedRows() {

						dict = [];

						$("#bisotab > tbody")
								.find('input[name="record"]')
								.each(
										function() {

											dict.push({"itemCode" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="itemCode"]')
																.children(
																		'input')
																.val(),
														"itemModel" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="grItemModel"]')
																.children(
																		'input')
																.val(),
														"itemPartNo" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="grItemPartNo"]')
																.children(
																		'input')
																.val(),
														"qty" : $(this)
																.parent()
																.parent()
																.children(
																		'td[name="qty"]')
																.children(
																		'input')
																.val(),
														"rate" : $(this)
																.parent()
																.parent()
																.children(
																		'td[name="rate"]')
																.children(
																		'input')
																.val(),
														"discountAmount" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="discountAmount"]')
																.children(
																		'input')
																.val(),
														"tax1" : $(this)
																.parent()
																.parent()
																.children(
																		'td[name="tax1"]')
																.children(
																		'input')
																.val(),
														"netRate" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="netRate"]')
																.children(
																		'input')
																.val(),
														"total" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="total"]')
																.children(
																		'input')
																.val(),
														"totalAt" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="totalAt"]')
																.children(
																		'input')
																.val(),
														"poQty" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="poQty"]')
																.children(
																		'input')
																.val(),
														"prQty" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="prQty"]')
																.children(
																		'input')
																.val(),
														"arQty" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="arQty"]')
																.children(
																		'input')
																.val(),
														"cipQty" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="cipQty"]')
																.children(
																		'input')
																.val(),
														"farQty" : $(
																this)
																.parent()
																.parent()
																.children(
																		'td[name="farQty"]')
																.children(
																		'input')
																.val(),
														"grItemId" : $(
																this)
																.parent()
																		.parent()
																		.children(
																				'td[name="grItemId"]')
																		.children(
																				'input')
																		.val(),
																"grStatus" : $(
																		this)
																		.parent()
																		.parent()
																		.children(
																				'td[name="grItemId"]')
																		.children(
																				"input[name='grStatus']")
																		.val(),
																"serialNo" : ($(
																		this)
																		.parent()
																		.parent()
																		.children(
																				'td[name="serialNo"]')
																		.children(
																				'input')
																		.val() == "null" || $(
																		this)
																		.parent()
																		.parent()
																		.children(
																				'td[name="serialNo"]')
																		.children(
																				'input')
																		.val() == '{"serialArray":[]}') ? ""
																		: $(
																				this)
																				.parent()
																				.parent()
																				.children(
																						'td[name="serialNo"]')
																				.children(
																						'input')
																				.val()

															});

												});

							}
							// end select row
							var grStatus = $("#grstat").val();
							if (grStatus == 'cancelled') {
								$('#grstat').attr('disabled', true);
								$("#Cancelgr").addClass('disabled');
								$("#Approvegr").addClass('disabled');
								$('#custom-tabs-one-tabContent :input').attr(
										'disabled', true);
							}
							else if (grStatus == 'completed') {
								$('#grstat').attr('disabled', true);
								$("#Approvegr").addClass('disabled');
								$('#custom-tabs-one-tabContent :input').attr(
										'disabled', true);
							}
							//save data in db
							function saveRowsInTables() {

								var ID = document.getElementById("grcode").value;

								var creationDate = document
										.getElementById("grcreatedate").value;
								var lastmodifiedDate = document
										.getElementById("grlstmodifdate").value;
								var supplierName = document
										.getElementById("grsuppname").value;
								var supplierAddress = document
										.getElementById("grsuppaddress").value;
								var orderedDate = document
										.getElementById("grorderdate").value;
								var delivereyDate = document
										.getElementById("grdeliverydate").value;
								var warehouse = document
										.getElementById("wareid").value;
								var TotalAmount = document
										.getElementById("grtotamnt").value;
								var discAmount = document
										.getElementById("grdiscamnt").value;
								var discPercent = document
										.getElementById("grdiscpercent").value;
								var paidAmount = document
										.getElementById("grpaidamnt").value;
								var grOutstanding = document
										.getElementById("groutstand").value;
								var grStatus = document
										.getElementById("grstat").value;
								var Nettotal = document
										.getElementById("grtotword").value;
								var TotalQty = document
										.getElementById("grtotqty").value;
								var grPOid = document.getElementById("poid").value;

								var supCat = $("#grsuppid").val();
								var supplierName = $("#grsuppname").val();

								var wareCode = $("#wareid").val();
								var siteId = $("#siteid").val();
								var wareName = $("#warename").val();
								var token = $('input[name="csrfToken"]').attr(
										'value');
								$.ajax({
											type : "POST",
											url : "${pageContext.request.contextPath}/GoodsRcvFormSave",
											dataType : "json",
											headers : {
												'X-CSRFToken' : token
											},
											data : {
												"type" : '${docStatus}',
												"FormSave" : FormSave,
												"dictParameter" : dict,
												"slctDelGds" : slctDelGds,
												"ID" : $("#grcode").val(),
												"creationDate" : creationDate,
												"lastmodifiedDate" : lastmodifiedDate,
												"supplierID" : supCat,
												"supplierName" : supplierName,
												"supplierAddress" : $("#grsuppaddress").val(),
												"orderedDate" : orderedDate,
												"delivereyDate" : delivereyDate,
												"warehouse" : wareCode,
												"warehouseName" : wareName,
												"grsiteID" : siteId,
												"TotalAmount" : $("#grtotamnt").val(),
												"discAmount" : $("#grdiscamnt").val(),
												"discPercent" : $("#grdiscpercent").val(),
												"paidAmount" : $("#grpaidamnt").val(),
												"grOutstanding" : $("#groutstand").val(),
												"grStatus" : grStatus,
												"Nettotal" : $("#grtotword").val(),
												"NettotalinWord" : '0',
												"grPOid" : $("#poid").val(),
												"grPREQid" : $("#prid").val(),
												"FarNo" : '0',
												"DarNo" : '0',
												"TotalQty" : $("#grtotqty").val(),
												"grApproveFlag" : grApproveFlag,
												"grCancelFlag" : grCancelFlag,
												"allDeletedSerialsArray":allDelSerials
											// 						"email": $("#email").val(),
											// 						"password":$("#password").val(),
											// 				  	    "emailTo": $("#emailTo").val(),
											// 					    "ccmail": $("#ccmail").val(),
											// 					    "subject": $("#subject").val(),
											// 					    "message": $("#message").val(),
											},
											success : function(data) {
												unsaved = false;
												grcode.value = data.GRID;
												var param;
												allDelSerials=[];
												if(data.RevPrStatAprv){
													if(data.RevPrStatAprv == "rejected")
														alert("The Goods received has been saved, but approving didn't occur because the PR  "+data.PRtobeAprvd+" that is related to this GR needs approving, thank you!");
														
													else
														alert("Approving occured, thank you.");
														
														}
												
												if(data.RevPoStatAprv){
													if(data.RevPoStatAprv == "rejected")
														alert("The Goods received has been saved, but approving didn't occur because the PO  "+data.POtobeAprvd+" that is related to this GR needs approving, thank you!");
														
													else
														alert("Approving occured, thank you.");
														
														}
												param = "${pageContext.request.contextPath}/GoodsRcvFormView?ID="+ $("#grcode").val()+"&NavAction=2";
												location.replace(param);

											},
											error : function(error) {
												console.log("The error is "
														+ error);
											}
										});

							}
							//end save data	

							///////////////////////////////////////////////////////
							
							

							////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
							// 			$("#email").autocomplete({
							// 			    source: function(request, response) {

							// 			    	var password=$("#password").val();
							// 			           $.ajax({
							// 			                 type: "GET",
							// 			                 contentType: "application/json; charset=utf-8",
							// 			                 url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
							// 			                 data: {
							// 									"email" : request.term,
							// 									//"password":request.value,
							// 							 },
							// 			                 dataType: "json",
							// 			                 success: function (data) {
							// 			                     if (data != null) {
							// 			                         response(data.ListItemEmailAccounts);
							// 			                         console.log('data in $("#email").autocomplete is :  '+ data.ListItemEmailAccounts);

							// 			                     }
							// 			                 },
							// 			                 error: function(result) {
							// 			                     alert("Error");
							// 			                 }
							// 			             });

							// 			         }, minLength:0, maxShowItems: 40, scroll:true,

							// 			         select: function(event, ui) {

							// 			             this.value = (ui.item ? ui.item[0]  : '');
							// 			             password.value = ui.item[1];

							// 			             return false;

							// 			         }

							// 			     }).autocomplete("instance")._renderItem = function(ul, item) {
							// 			         return $("<li class='each'>")
							// 			         .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
							// 			             item[0] + "</span></div>")
							// 			         .appendTo(ul);
							// 			 	};

							// 						$("#email").focus(function(){
							// 			   	        	if (this.value == ""){
							// 			   	            	$(this).autocomplete("search");
							// 			   	        	}						
							// 						});
							// 						$("#password").focus(function(){
							// 			   	        	if (this.value == ""){
							// 			   	            	$(this).autocomplete("search");
							// 			   	        	}						
							// 						});

							//////////////////////////////////////////////////////////////////////////////		

						}); //end ready document
// 	});
	</script>
 

	
 </html>
