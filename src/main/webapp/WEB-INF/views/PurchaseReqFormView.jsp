<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
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
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	
  <script src="${pageContext.request.contextPath}/resources/js/PurchaseRequest/PRQ_BoqPopup.js"></script>
		
    
     <!--  style used for prrqitem table  -->    
    <style>
    
       /*Doaa's popUp send Email Div'*/
    
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
	        		
	        		
	        		
	        		
				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				  margin-left: 10px;
				  
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

/* 				.rectangle { */
/* 				  height: 35px; */
/* 				  width: 70px; */
				 
/* 				background-color" : white; */
/* 				 color: black; */
/* 				  text-align:center; */
/* 				 vertical-align: middle; */
/*                  line-height: 32px; */
/* 	           font-size:15px; */
/* 	           font-family: Times New Roman; */
/* 				display: inline-block; */
/* 				border-style:outset; */
/*                 border-color:chartreuse; */
/*                 border-width:2px; */
				  
				  
/* 				} */
		
/* 				.btn1{ */
			 
/*                color:white; */
/*                font-family: 'Arial'; */
/*              font-size: 15px; */
/*               background-color: green; */
              
/*               border-radius:30px 30px 30px 30px; */
               
/* 			cursor: not-allowed; */
/*              pointer-events: none; */
/* 				} */
				
				.fixed-headerr{
				opacity: 1;
				filter: alpha(opacity=100);
			 	background: #ebf2ef;
			  	position: sticky;
			  	top: 0;
			  	z-index: 15;

	}
				 
/* #wrapper { */
/* 				   width:100%; */
/*   					display: grid; */
/*  					max-height:100px; */
/*   					grid-gap: 100px; */
/*   					grid-column-gap:10px!important; */
/*   					grid-row-gap:8px!important; */
/*   					display:flex; */
/*   					grid-template-rows:1fr 1fr!important; */
/*   					grid-template-columns:1fr 1fr 1fr!important; */
  					
/* 					}					 */
 
#one {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#two {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#three {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#four {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#five {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#six {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}
				
.container{min-width:100%;}	
	
.divv1{ text-align:center;font-family: cursive;color:#DCF8C6;border-radius: 25px 25px 0px 0px!important; 
 border-style: groove;border-width: 2px;border-color: #4B8C8C;box-shadow: 5px 5px 5px rgb(75,140,140); } 

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
 	</style>       
            
</head>
<body>

<%-- 	<c:set var = "page" value = "purchasing"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
     
  <c:set var="pg" value="purchasing" scope="session"  />
  <jsp:include page="header.jsp"></jsp:include>
     
     
     <p></p>
<div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" style="color:green;">Purchase Request</span>
							<input type="text" readonly id="prcode" value="${ID}" class="form-control text-input" />
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
						</div>

					</div>
			</div>
			
			<div class="col-md-3">
				<div id="statusInput" class="input-group-prepend">
				<span style="width:190px;" class="input-group-text">Status</span>
				
				</div>							
			</div>
			  <div class="pad col-md-3 hide-row"></div>   
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other PRQ</span>
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
					<input type="text" id="prcreatedate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="prlstmodifdate" readonly value="${lastmodifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
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
			
			
			
			        <div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 			onclick='window.location.href = "${pageContext.request.contextPath}/PurchaseReqFormView"'
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/PurchaseReqListView"'
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
			<a class="dropdown-item"  type="button" id="approvePRq">Approve</a>
	             <a class="dropdown-item"  type="button" id="closePRq" >Close</a>
	             <a class="dropdown-item" type="button" id="cancelPRq" >Cancel</a>
    	          <a class="dropdown-item" id="Newpo" href="${pageContext.request.contextPath}/PurchaseOrderFormView?ordPRQid=${ID}&type=addNewFromPRQ">Create Purchase order</a>
    	          <a class="dropdown-item" id="Newgr" href="${pageContext.request.contextPath}/GoodsRcvFormView?grPRQid=${ID}&type=addNewFromPRQ">Create Goods Received </a>
    	        
    	        </div>
    	 	<button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
             </div>	
               
				  
                        
				  </li>
							
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
						<div id="supIdInput" class="input-group-prepend ">
							<span class="input-group-text ">Supplier ID</span>
							
						</div>
					</div>
			</div>
			
			
			
			<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span id="orderDateInput" class="input-group-text">Order Date</span>
					
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
					<span id="deliveryDateInput" class="input-group-text">Delivery Date</span>
					
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
						<div id="supNameInput" class="input-group-prepend">
							<span class="input-group-text">Supplier Name</span>
							
						</div>							
			</div>
			<div class="col-md-6">
						<div id="supAddInput" class="input-group-prepend">
							<span class="input-group-text">Supplier Address</span>
							
						</div>							
			</div>
  		
    </div>
    
    <p></p>
    
<div class="row">
<div class="col-md-3">
					<div class="form-group">
						<div id="wareIdInput" class="input-group-prepend">
							<span class="input-group-text" >WareHouse ID</span>
							
						</div>
					</div>
			</div>
			
			
			 <div class="col-md-5">
					<div class="form-group">
						<div id="wareNameInput" class="input-group-prepend">
							<span class="input-group-text"> Warehouse Name</span>
							
						</div>
					</div>
			</div>
			
			
			
			 <div class="col-md-4">
					<div class="form-group">
						<div id="siteIdInput" class="input-group-prepend">
							<span class="input-group-text">Site ID </span>
							
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
							
						</div>
					</div>
			</div>
			
			
			 <div class="col-md-3">
					<div class="form-group">
						<div id="cat2Input" class="input-group-prepend">
							<span class="input-group-text"> Cat2</span>
							
						</div>
					</div>
			</div>
			
			
			
			 <div class="col-md-3">
					<div class="form-group">
						<div id="cat3Input" class="input-group-prepend">
							<span class="input-group-text">Cat3 </span>
							
						</div>
					</div>
			</div>
			
			
				 <div class="col-md-3">
					<div class="form-group">
						<div id="cat4Input" class="input-group-prepend">
							<span class="input-group-text">Cat4 </span>
							
						</div>
					</div>
			</div>
		
			</div>






<div class="row">
<div class="col-md-5">
					<div class="form-group">
						<div id="seqInput" class="input-group-prepend">
							<span class="input-group-text">Sequence</span>
							
						</div>
					</div>
			</div>
<div class="col-md-5">
					<div class="form-group">
						<div id="barcodeInput" class="input-group-prepend">
							<span class="input-group-text" >Barcode</span>		
						</div>
					</div>
<!-- 					<button id="add-row-bc">Add in poq table</button> -->

			</div>

 </div>





	<div class="row">

		
  </div>
	
<p></p>
    	<!--  create table purchaserequestItem    -->

		<div> 
				<form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            
									<tr class="fixed-headerr">
						                <th>
						                <div class="container">
								          <button type="button" id="selectAll" class="main">
								          <span class="sub"></span>Select</button></div></th>
								          
						                <th>Item </th>
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
						                <th>Tax</th>
						                <th>Net Rate</th>
						                <th>Total</th>
						                <th>Total AT</th>
						                <th>PO</th>
						                <th>GR</th>
						                <th>AR</th>
						                <th>CIP</th>
						                <th>FAR</th>					                
						                <th>PRQ Item ID</th>
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
						<input type="text" id="prtotamnt" value="${TotalAmount}" readonly class="form-control text-input" />
					</div>
				</div>
	</div>
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Discount Amount</span>
						<input type="text" id="prdiscamnt" value="${discAmnt}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-2">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Discount percent</span>
						<input type="text" id="prdiscpercent" value="${discountPercent}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	 <div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Total QTY</span>
				<input type="text" id="prtotqty" value="${TotalQty}" readonly class="form-control text-input" />
				</div>							
		</div>
	</div>
	<div class="row">
	
	    <div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Net Total</span>
				<input type="text" id="prtotword" value="${netTotal}"  readonly class="form-control text-input"  />
			</div>
		</div>
	    
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Paid Amount</span>
						<input type="text" id="prpaidamnt" value="${paidAmount}" class="form-control text-input" />
						
					</div>
				</div>
		</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text">Outstanding</span>
					<input type="text" id="proutstand" value="${prOutstanding}"  readonly class="form-control text-input"/>
					</div>
				</div>
		</div>
	
		
	</div>

</div>





 <div class="tab-pane fade" id="custom-tabs-one-overview" role="tabpanel" aria-labelledby="custom-tabs-overview">

<div class="row container" style="display:flex;">
			
			
			
			
				
           <div id="one" class="  mx-auto  ">
	 
																												   
																													 
				   
                <div class="card-header divv1"><i class="fas fa-file-invoice-dollar" style="font-size:20px;"></i><b> PURCHASE ORDER</b></div>
                <div class="card-body mycard divv2">
                   
                 <table>
                   
                  <th style="width:40%;align:left;margin:5px;"></th><th style="width:25%;margin:5px;">All </th><th style="width:35%;margin:5px;"> Not Completed</th>
                 
                   <tr>
                   
                   <td class="TD">	<b>No of POs </b>	
                   <td  id="poCom" class="TD">		
                   <td id="ponotCom" class="TD">
                   
                   </tr>
               		
               		<tr>
               		
               		<td  class="TD">	<b>Net Total Amount</b>	
               		<td id="netPOCom"class="TD">	
               		<td id="netponotCom" class="TD">
               		
               		</tr>
                 
                   <tr>
                   
                   <td >	<b>Total Quantity</b>	
                   <td id="totQtyPOCom">		
                   <td id="totqtypoNotCom">
                   
                   </tr>
                  
                   </table>
                   <!--   <ul style="margin-right:30px;">
                        <li id="PO_first" style="font-family: cursive;font-size:13px;">${display_PO_features}</li>
                        
                        <li id="PO_second" style="font-family: cursive;margin-top:13px;font-size: 15px;">${display_PO_count}</li>

                    </ul>-->
                </div>
            </div>
       
   
        
        
    
	  
            <div id="two" class="mx-auto " >
	 
																												   
																													 
				   
                <div class="card-header divv1"><i class="fas fa-sign-in-alt" style="font-size:20px;"></i><b> GOODS RECEIVED </b></div>
               
                <div class="card-body mycard divv2">
                   
                 <table>
                   
                  <th style="width:40%;align:left;margin:5px;"></th> <th style="width:25%;margin:5px;">All </th><th style="width:35%;margin:5px;"> Not Completed</th>
                 
                   <tr >
                   
                   <td class="TD">	<b>No of GRs </b>	
                   <td id="grCom" class="TD">	
                   <td id="grNotCom" class="TD">
                   
                   </tr>
               		
               		<tr>
               		
               		<td class="TD">	<b>Net Total Amount</b>	
               		<td id="netGrCom" class="TD">	
               		<td id="netGRNotCom" class="TD">
               		
               		</tr>
                 
                   <tr>
                   
                   <td>	<b>Total Quantity</b>	
                   <td id="totQtyGrCom">	
                   <td id="totqtyGRNotCom">	
                   
                   </tr>
                  
                   </table>
														 
																												  
						
																																 

							
                </div>
            </div>
		
		
		
		<div id="three" class="mx-auto  ">
              <div class="card-header divv1"><i class="fas fa-search-plus" style="font-size:20px;"></i><b> DISCOVERY NEW</b></div>
               
               
                <div class="card-body mycard divv2">
                   
                 <table>
                   
                  <th style="width:40%;align:left;margin:5px;"></th> <th style="width:25%;margin:5px;">All </th><th style="width:35%;margin:5px;"> Not Completed</th>
                 
                   <tr>
                   
                   <td  class="TD">	<b>No of DNs  </b>	
                   <td id="dnComp" class="TD">	
                   <td id="dnNotComp" class="TD">
                   
                   </tr>
               		
               		<tr>
               		
               		<td class="TD">	<b>Net Total Amount</b>	
               		<td id="dnNetTotComp" class="TD">	
               		<td id="dnNetTotNotComp" class="TD">
               		
               		</tr>
                
                   <tr>
                   
                   <td>	<b>Total Quantity</b>	
                   <td id="dnTotQtyComp">	
                   <td id="dnTotQtyNotComp">
                   
                   </tr>
                  
                   </table>
														 
																												  
						
																																 

							
                </div>
            </div>
	  
		</div>
		
		<div class="row container" style="display:flex;">
		
		
		 <div id="four" class="mx-auto">
	 
																												   
																													 
				   
                <div class="card-header divv1"><i class="fas fa-road"style="font-size:20px;"></i><b> CAPITAL IN PROGRESS</b></div>
                <div class="card-body mycard divv2">
                   
                 <table>
                  
                  <th style="width:50%;align:left;margin:5px;"></th> <th style="width:50%;margin:5px;"></th>
                 
                   <tr>
                   <td class="TD">	<b>No of CIPs</b>	
                   <td id="cipCountAll" class="TD">	
                   
                   </tr>
               		
               		<tr>
               		<td class="TD">	<b>CIP Valuation Rate</b>	
               		<td id="cipNetTotAll" class="TD">	
               		 </tr>
               		
               		<tr>
               		<td>	<b>Total Quantity</b>	
               		<td id="cipTotQtyAll">	
               		 </tr>
                 
                
                  
                   </table>
														 
																												  
						
																																 

							
                </div>
            </div>
       
       
		
            <div id="five" class="mx-auto">
<!-- 
                <div class="card-header"><i class="fas fa-cash-register"style="color:#DCF8C6"></i> Purchasing</div>
                <div class="card-header"><i class="fas fa-shopping-basket"style="color:#DCF8C6"></i> Purchasing</div>
-->                
                <div class="card-header divv1"><i class="fas fa-registered"style="font-size:20px;"></i><b> ASSET REGISTRY</b></div>
                <div class="card-body mycard divv2">    
                <table>
                   
                  <th style="width:50%;align:left;margin:5px;"></th> <th style="width:50%;margin:5px;"></th>
                
                   <tr> 
                   <td class="TD">	<b>No of ARs</b>	
                   <td id="arCountAll" class="TD">	
                    </tr>
               		
               		<tr>
               		<td><b>Sum of Valuation Rate</b>	
               		<td id="arValRateAll">	
               		</tr>
                 
                 <tr></tr>
                  
                   </table>
                </div>
            </div>
        
		
		
            <div id="six" class="mx-auto">
	 
																												   
																													 
				   
                <div class="card-header divv1"><i class="fas fa-money-check-alt"style="font-size:20px;"></i><b> FIXED ASSET REGISTRY</b></div>
                <div class="card-body mycard divv2">
                   
                   <table>
  <th style="width:40%;align:left;margin:5px;"></th> <th style="width:25%;margin:5px;">All </th><th style="width:25%;margin:5px;"> Not Running</th>
                            
   <!--      <th style="width:50%;align:left;margin:5px;"></th> <th style="width:50%;margin:5px;"></th> -->
                
              <tr> 
                   <td class="TD">	<b>    No of FARs   </b>	
                   <td id="farCount" class="TD">
                   <td id="farNotRunCount" class="TD">
                    </tr>  
                    
                   <tr> 
                   <td class="TD">	<b>    Initial cost   </b>	
                   <td id="farCountAll" class="TD">
                   <td id="farNotRunIntCost" class="TD">
                    </tr>
               		
               		<tr>
               		
               		<td>	<b>   Net cost   </b>	
               		<td id="farValRateAll">
               		<td id="farNotRunNetCost">
               		
               		</tr>
                 
              
                  
                   </table>
                </div>
            </div>
                   
                </div>
            </div>
        </div>
        </div>
		
<!-- Popup--> 
<div class="container">
<div id ="preqModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #FF4F4F;" >
				<h5 id ="popupNb" class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;"></h5>
				
				<button  name="insertBelow"  onclick="insertRowBelow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:50px;">Insert Below </button>
				<button  name="insertAbove"  onclick="insertRowAbove()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:60px;">Insert Above </button>
				<button  name="deleteBoqRow" onclick="deleteBoqRow()"   class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:70px;">Delete</button>
				<button  name ="previousRow" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:80px;">Previous</button>
	            <button  name="nextRow" onclick="nextRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:90px;">Next</button> 
				
				<button type="button" name="closePopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="item-tab" style="color: gold;" data-toggle="tab" href="#item" role="tab" aria-controls="item" aria-selected="true">ITEM</a>
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



</div>    </div>

	<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Category1</span>
						<input type="text" id="popupCat1" class="form-control text-input" value="${popupCat1}" style="width:674px; height:37px" />
				</div></div></div>
				
					
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text" >Category2</span>
				<input type="text" id="popupCat2" class="form-control text-input" value="${popupCat2}" style="width:674px; height:37px" />
						
					</div></div></div>

<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Category3</span>
				<input type="text" id="popupCat3" class="form-control text-input" value="${popupCat3}" style="width:674px; height:37px"  />  
						
				</div></div></div>
				
				<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Category4</span>
				<input type="text" id="popupCat4" class="form-control text-input" value="${popupCat4}" style="width:674px; height:37px"  />  
						
				</div></div></div>
				
				
				<!-- SS -->
				<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Sequence</span>
				<input type="text" id="popupSeq" class="form-control text-input" value="${popupSeq}" style="width:674px; height:37px"  />  
						
				</div></div></div>
								<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Barcode</span>
				<input type="text" id="popupBarcode" class="form-control text-input" value="${popupBarcode}" style="width:674px; height:37px"  />  
						
				</div></div></div>
	</div> </div>

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
				<input type="text" id="popupNetrate" class="form-control text-input"  value="${popupNetRate}" readonly style="width:675px;" />
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
						<input type="text" id="popupTotalat" class="form-control text-input" readonly value="${popupTotalAt}" style="width:675px;"   />
						
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
					<input type="text" id="popupArQty" class="form-control text-input" readonly value="${pAr}" style="width:120px;"  />
						
					</div>
				</div>
	</div>

	<div class="col-sm-3">
				<div class="form-group">
					<div class="input-group-prepend">
					
						<span class="input-group-text">GR</span>
						<input type="text" id="popupGrQty" class="form-control text-input" readonly value="${pGr}" style="width:120px;"   />
						
						
					</div>
				</div>
	</div> 
	<div class="col-sm-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >PO</span>
						<input type="text" id="popupPoQty" class="form-control text-input" value="${pPo}" readonly style="width:120px;"  />
						
					</div>
				</div>
	</div>
	
	<div class="col-sm-3">
				<div class="form-group">
					<div class="input-group-prepend">
					
						<span class="input-group-text">CIP</span>
						<input type="text" id="popupCipQty" class="form-control text-input" readonly value="${pCip}" style="width:110px;"   />
						
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
						<input type="text" id="popupFarQty" class="form-control text-input" readonly value="${pFar}" style="width:111px;"  />
						
					</div>
				</div>
	</div>
					</div>		</div>	      
	

   </div>

  </div>

  
  </div>
					
<div class="modal-footer">
	
</div>	
								                
	</div>
			
</div> </div>
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
						<span class="input-group-text" style="width:80px;" >   ccEmail </span>
						<input type="text" id="ccmail" class="form-control text-input" placeholder=""/>
					
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
     // Fires LAST
     ////////
     $(function(){
    	 var start = performance.now();
         
    	 boqArray = ${ListPRqItem};
    	 for(i = 0; i< boqArray.length; i++){
        	 
    		 boqAutocomplete(i,"bisotab");
		 					 
    		 }
    	 calcFooterDataOnChangeListener(); //to put listener for calculating the footer data(total amount, )			
    	 var end = performance.now();
    	 console.log("time needed for autoComplete & focusing on the boq already saved: " +((end-start)/1000) + " seconds");
						   
         });
     						  
 });
 </script>
 <script>
 if ('${ListPRqItem}' != "addNew") {
 var c = "";   
 
 boqArray = ${ListPRqItem};
 for (i = 0;i<boqArray.length;i++){
		
		var itemModel =  boqArray[i].prItemModel;
		var barcode =  boqArray[i].prBarCode;
	
		var itemPartNumber = boqArray[i].prItemPartNumber;
		var dotStatus = (boqArray[i].prqPoStatus);
		var span;
		var itemRow;
		var loadRowCount;
		
		if(itemModel == null)
		itemModel = "";
		else itemModel =  boqArray[i].prItemModel;
		if(barcode == null) barcode = "";
		else barcode =  boqArray[i].prBarCode;
	
		if(itemPartNumber == null)
			itemPartNumber = "";
		else 
			itemPartNumber = boqArray[i].prItemPartNumber;

		  if(dotStatus == "1")
		 {
		   span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: chartreuse;'></span>";
								  
	     }
 	else 
	     {
 	 span = "<span class='dotStatus' name='dotStatus' value='"+dotStatus+"' style='background-color: orange;'></span>";
		    }	  
	

	c+="<tr>"+
	"<td><input type='checkbox' name='record' >"+span+"<button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"+
	"<td name='prItemCode'><input name='itmCode' type='text' id = 'itmCode"+i+"'   value='" + boqArray[i].prItemCode +":"+ boqArray[i].prItemName + "' style='width:300px;' class='form-control text-input'/></td>"+
	"<td name='prItemModel'><input name='itmModel' type='text' id = 'itmModel"+i+"' value='" + itemModel + "' style='width:200px;' class='form-control text-input'/></td>"+
	"<td name='prItemPartNo'><input name='itmPartNo' type='text' id = 'itmPartNo"+i+"' value='" + itemPartNumber + "' style='width:200px;' class='form-control text-input'/></td>"+
	"<td hidden name='prBarCode'><input name='barcode' type='text' hidden value='" + barcode + "' style='width:200px;' class='form-control text-input'/></td>"+
	"<td name='prQty'><input type='text' value='" + boqArray[i].prQty +"'style='width:200px;' class='form-control text-input'></td>"+
	"<td name='prRate'><input type='text' value='" + boqArray[i].prRate +"'style='width:200px;' class='form-control text-input'></td>"+
	"<td name='prDiscountAmount'><input type='text' value='" + boqArray[i].prDiscountAmount +"'style='width:200px;' class='form-control text-input'></td>"+
	"<td name='prTax1'><input type='text' value='" + boqArray[i].prTax1 +"'style='width:200px;' class='form-control text-input'></td>"+
	"<td name='prNetRate'><input  type='text' value='" + boqArray[i].prNetRate +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='prTotal'><input  type='text' value='" + boqArray[i].prTotal +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='prTotalAt'><input  type='text' value='"  + boqArray[i].prTotalAt +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='poQty'><input type='text' value='" + boqArray[i].poQty +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='grQty'><input type='text' value='" + boqArray[i].grQty +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='arQty'><input type='text' value='" + boqArray[i].arQty +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='cipQty'><input type='text' value='" + boqArray[i].cipQty +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='farQty'><input type='text' value='" + boqArray[i].farQty +"' readonly style='width:200px;' class='form-control text-input'></td>"+
	"<td name='prqItemId'><input type='text' value='" + boqArray[i].prqItemId +"'readonly style='width:200px;' class='form-control text-input'><input name='prqPoStatus' type='text' value='" + boqArray[i].prqPoStatus +"'hidden></td>"+		
	"</tr>";
											  
}
 $("#bisotab").append(c);  
 $(document).trigger("triggerBoqListenersEvent");
										
 }
 </script>
<script>
//  if('${docStatus}' != "addNew"){




	 $(document).ready(function() {
		 $(function(){
			var pRqAppFlag = 0;
			var pRqCnclFlg = 0;
		////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
//		 		$("#email").autocomplete({
//		 		    source: function(request, response) {
				         
//		 		    	var password=$("#password").val();
//		 		           $.ajax({
//		 		                 type: "GET",
//		 		                 contentType: "application/json; charset=utf-8",
//		 		                 url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
//		 		                 data: {
//		 								"email" : request.term,
//		 								//"password":request.value,
//		 						 },
//		 		                 dataType: "json",
//		 		                 success: function (data) {
//		 		                     if (data != null) {
//		 		                         response(data.ListItemEmailAccounts);
//		 		                         console.log('data in $("#email").autocomplete is :  '+ data.ListItemEmailAccounts);

//		 		                     }
//		 		                 },
//		 		                 error: function(result) {
//		 		                     alert("Error");
//		 		                 }
//		 		             });
				            
//		 		         }, minLength:0, maxShowItems: 40, scroll:true,
						
//		 		         select: function(event, ui) {
				        	 
//		 		             this.value = (ui.item ? ui.item[0]  : '');
//		 		             password.value = ui.item[1];
				             
//		 		             return false;
				            
//		 		         }
				        
//		 		     }).autocomplete("instance")._renderItem = function(ul, item) {
//		 		         return $("<li class='each'>")
//		 		         .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
//		 		             item[0] + "</span></div>")
//		 		         .appendTo(ul);
//		 		 	};
				 	
				     
//		 					$("#email").focus(function(){
//		 		   	        	if (this.value == ""){
//		 		   	            	$(this).autocomplete("search");
//		 		   	        	}						
//		 					});
//		 					$("#password").focus(function(){
//		 		   	        	if (this.value == ""){
//		 		   	            	$(this).autocomplete("search");
//		 		   	        	}						
//		 					});
						
				 //////////////////////////////////////////////////////////////////////////////		  	


	var FormSave=new Array();	
	FormSave=[];
	var checkSave="";
 
											 
				  
																			   
	   
	 

												
					 
	 
				
				var readForm = ${readForm}; var writeForm = ${writeForm}; var delForm = ${delForm}; var saveForm = ${saveForm};
				
				function getInput ( checkRead, get ){
					
					var statusInput = '<select onchange="prstatCheck()" id="prstat" class="form-control">'
						+'<option value="inprog" <c:if test = "${prStatus =='inprog'}" > selected </c:if> >In Progress</option>'
						+'<option value="approved" <c:if test = "${prStatus =='approved'}" > selected </c:if>>Approved</option>'
						+'<option value="completed" <c:if test = "${prStatus =='completed'}" > selected </c:if>>Completed</option>'
						+'<option value="closed" <c:if test = "${prstatus =='closed'}" > selected </c:if>>Closed</option>'
						+'<option  value="cancelled" hidden <c:if test = "${prStatus =='cancelled'}"  > selected </c:if>>Cancelled</option>'
						+'</select></div></div>';
					var supIdInput = '<input type="text"' +checkRead+ 'id="prsuppid" value="${prsupplierID}"  style="width:675px"'
										+ 'class="ui-widget ui-widget-content ui-corner-all form-control" />';
					var orderDateInput = '<input type="text"' +checkRead+ 'id="prorderdate" value="${orderedDate}"'
										+ 'class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />';
					var deliveryDateInput = '<input type="text"' +checkRead+ 'id="prdeliverydate" value="${delivereyDate}"'
										+ 'class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />';
					var supNameInput = '<input type="text"' +checkRead+ 'id="supplierName" value="${supplier}"'
										+ 'style="width:675px"class="ui-widget ui-widget-content ui-corner-all form-control"/>';
					var supAddInput = '<input type="text"' +checkRead+ 'id="prsuppaddress" value="${supplierAddress}"  class="form-control text-input"/>';
					var wareIdInput = '<input type="text"' +checkRead+ 'id="wareid" value="${WareId}"'
										+ 'style="width:215px;" class="ui-widget ui-widget-content ui-corner-all form-control" />';
					var wareNameInput = '<input type="text"' +checkRead+ 'id="warename" value="${WareName}"'
										+ 'style="width:675px;"  class="ui-widget ui-widget-content ui-corner-all form-control" />';
					var siteIdInput = '<input type="text"' +checkRead+ 'id="siteid" value="${SiteId}" style="width:675px;" class="ui-widget ui-widget-content ui-corner-all form-control" />';

					 var barcodeInput = '<input type="text"' +checkRead+ 'id="barcode" value="${barcode}"'
						+ 'style="width:425px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control" />';
						 var catInput = '<input type="text"' +checkRead+ 'id="cat"  value="${cat}"'
							+ 'style="width:250px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control" />';
							 var cat2Input = '<input type="text"' +checkRead+ 'id="cat2"  value="${cat2}"'
								+ 'style="width:250px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control" />';
								 var cat3Input = '<input type="text"' +checkRead+ 'id="cat3"  value="${cat3}"'
									+ 'style="width:250px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control" />';
									 var cat4Input = '<input type="text"' +checkRead+ 'id="cat4"  value="${cat4}"'
										+ 'style="width:250px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control" />';
									 var seqInput = '<input type="text"' +checkRead+ 'id="seq" value="${seq}"'
										+ 'style="width:425px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control" />';
					

					var disAmountInput = '<input type="text"' +checkRead+ 'id="prdiscamnt" value="${discAmnt}" class="form-control text-input " />';
					var disPercentInput = '<input type="text"' +checkRead+ 'id="prdiscpercent" value="${discountPercent}" class="form-control text-input " />';
					var paidAmountInput = '<input type="text"' +checkRead+ 'id="prpaidamnt" value="${paidAmount}" class="form-control text-input"  />';
			
					var prStatus=$("#prstat").val();

					if(prStatus == 'approved' || prStatus == 'completed' || prStatus =='closed')
			 			 $("#approvePRq").addClass('disabled');
			 		else $("#approvePRq").removeClass('disabled');

					if(prStatus == 'inprog' || prStatus == 'completed' || prStatus =='closed')
						 $("#closePRq").addClass('disabled');
			 		else $("#closePRq").removeClass('disabled');
			 		
				
					switch(get){
						case "statusInput": return statusInput;
						case "supIdInput": return supIdInput;
						case "orderDateInput": return orderDateInput;
						case "deliveryDateInput": return deliveryDateInput;
						case "supNameInput": return supNameInput;
						case "supAddInput": return supAddInput;
						case "wareIdInput": return wareIdInput;
						case "wareNameInput": return wareNameInput;

						case "barcodeInput": return barcodeInput;
						case "catInput": return catInput;
						case "cat2Input": return cat2Input;
						case "cat3Input": return cat3Input;
						case "cat4Input": return cat4Input;
						case "seqInput": return seqInput;

						case "siteIdInput": return siteIdInput;
						case "disAmountInput": return disAmountInput;
						case "disPercentInput": return disPercentInput;
						case "paidAmountInput": return paidAmountInput;
					}			
				}
				
				if( writeForm == 1 ){
					$('#statusInput').append(getInput( " ", "statusInput" ));
					$('#supIdInput').append(getInput( " ", "supIdInput" ));
					$('#orderDateInput').after(getInput( " ", "orderDateInput" ));
					$('#deliveryDateInput').after(getInput( " ", "deliveryDateInput" ));
					$('#supNameInput').append(getInput( " ", "supNameInput" ));
					$('#supAddInput').append(getInput( " ", "supAddInput" ));
					$('#wareIdInput').append(getInput( " ", "wareIdInput" ));
					$('#wareNameInput').append(getInput( " ", "wareNameInput" ));
					$('#siteIdInput').append(getInput( " ", "siteIdInput" ));

					$('#barcodeInput').append(getInput( " ", "barcodeInput" ));
					$('#seqInput').append(getInput( " ", "seqInput" ));
					$('#cat4Input').append(getInput( " ", "cat4Input" ));
					$('#cat3Input').append(getInput( " ", "cat3Input" ));
					$('#cat2Input').append(getInput( " ", "cat2Input" ));
					$('#catInput').append(getInput( " ", "catInput" ));

					$('#disAmountInput').append(getInput( " ", "disAmountInput" ));
					$('#disPercentInput').append(getInput( " ", "disPercentInput" ));
					$('#paidAmountInput').append(getInput( " ", "paidAmountInput" ));
				}
				else {
					$('#statusInput').append(getInput( " readonly ", "statusInput" ));
					$('#supIdInput').append(getInput( " readonly ", "supIdInput" ));
					$('#orderDateInput').after(getInput( " readonly ", "orderDateInput" ));
					$('#deliveryDateInput').after(getInput( " readonly ", "deliveryDateInput" ));
					$('#supNameInput').append(getInput( " readonly ", "supNameInput" ));
					$('#supAddInput').append(getInput( " readonly ", "supAddInput" ));
					$('#wareIdInput').append(getInput( " readonly ", "wareIdInput" ));
					$('#wareNameInput').append(getInput( " readonly ", "wareNameInput" ));

					$('#barcodeInput').append(getInput( " readonly ", "barcodeInput" ));
					$('#seqInput').append(getInput( " readonly ", "seqInput" ));
					$('#cat4Input').append(getInput( " readonly ", "cat4Input" ));
					$('#cat3Input').append(getInput( " readonly ", "cat3Input" ));
					$('#cat2Input').append(getInput( " readonly ", "cat2Input" ));
					$('#catInput').append(getInput( " readonly ", "catInput" ));

					$('#siteIdInput').append(getInput( " readonly ", "siteIdInput" ));
					$('#disAmountInput').append(getInput( " readonly ", "disAmountInput" ));
					$('#disPercentInput').append(getInput( " readonly ", "disPercentInput" ));
					$('#paidAmountInput').append(getInput( " readonly ", "paidAmountInput" ));
				}
				
				var deleteButton = '<button type="button" id="deleteButton" class="btn btn-primary BtnActive"><i class="fa fa-trash"></i> Delete </button>';
				var saveButton = '<button type="button" id="saveButton" class="btn btn-primary BtnActive"><i class="fa fa-save"></i> Save </button>';
				var ammendButton = '<button type="button" id="ammendButton" class="btn btn-primary BtnActive"><i class="fa fa-edit"></i> Amend </button>';

				if(delForm == 1){
					$("#Buttons").append(deleteButton).append(" ");
				}
				if(saveForm == 1){
					var prStatus=$("#prstat").val();
					if($("#prcode").val() == "") {
						$("#cancelPRq").addClass('disabled');
						$("#approvePRq").addClass('disabled');
						}
					if(prStatus == "cancelled")
						
					$("#Buttons").append(ammendButton);
					
					else

					$("#Buttons").append(saveButton);
				}

				$(document).on('click', '#ammendButton', function() {
					
					 $("#ammendButton").remove();
					 $('#custom-tabs-one-tabContent :input').attr('disabled',false);
								
					 $("#formStatus").text("Not Saved");
				     $('.dot').css({"background-color" : "orange"});
					 $("#Buttons").append(saveButton);
					 $("#prstat").val('inprog').trigger('change');
					});
				// this code is for changing from saved to not saved if any changes happened
				
// 				$("input").change(function() {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 					});
		   
// 					 $("#datetimepicker3").click(function() {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});
						
// 					$("#datetimepicker4").click(function() {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});
						
						
// 					$("#prsuppid").on('keyup change', function () {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"}
// 						);
// 						});

					
// 					$("#supplierName").on('keyup change', function () {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});
						
						
// 					$("#wareid").on('keyup change', function () {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});

// 					$("#warename").on('keyup change', function () {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});
// 					$("#siteid").on('keyup change', function () {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});

// 					$("#barcode").on('keyup change', function () {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});

// 					$("#seq").on('keyup change', function () {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 						});
					
// 					$(".add-row").click(function() {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 					});
						
// 					$(".delete-row").click(function() {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 					});	
					
// 					$("#bisotab > tbody").change(function() {
// 						$("#formStatus").text("Not Saved");
// 						$('.dot').css({"background-color" : "orange"});
// 					});	

					//end for code if any changes happened, change from saved to not saved
				
  


						   
	
													
								 
   


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
									}}).autocomplete("instance")._renderItem = function(ul, item) {
					            return $("<li class='each'>")
				                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
				               
				                    item[0] + "</span><br><span class='desc'>" +
				                    item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
				        	};
							$("#wareid").focus(function(){
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
													var partNum = ui.item[4];
													var barcode = ui.item[0];
													newRowCount =  $("#bisotab >tbody tr").length-1;
												 	$("#bisotab > tbody").append(boqRowInsrt(newRowCount, itmName, model, partNum, barcode));
										      		boqAutocomplete(newRowCount,"bisotab");
													 $("#formStatus").text("Not Saved");
													 $('.dot').css({"background-color" : "orange"});
												     //$('#barcode').val("");
													 $('#cat3').val("");
													 $('#cat4').val("");
											    	 $('#cat2').val("");
									            	 $('#cat').val("");
									            	 $('#seq').val("");
												     $('table#bisotab tr:last td:nth-child(2) input').focus();
												     calcFooterDataOnChangeListener();
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
								        	 //getParentCategory
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
									        	 $(this).val(ui.item[0]+":"+ui.item[1]);
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
														var partNum = data.item[4];
														var barcode = "";
													  

														newRowCount =  $("#bisotab >tbody tr").length-1;
													 	$("#bisotab > tbody").append(boqRowInsrt(newRowCount, itmName, model, partNum, barcode));
											      		boqAutocomplete(newRowCount,"bisotab");
														 $("#formStatus").text("Not Saved");
														 $('.dot').css({"background-color" : "orange"});
													     $('#barcode').val("");
														 $('#cat3').val("");
														 $('#cat4').val("");
												    	 $('#cat2').val("");
										            	 $('#cat').val("");
										            	 $('#seq').val("");
													     $('table#bisotab tr:last td:nth-child(2) input').focus();
													     calcFooterDataOnChangeListener();
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
												$("#seq").focus(function(){
											       	if (this.value == ""){
											           	$(this).autocomplete("search");
											       	}						
												});
							// end add by category
							

						
		/////// end auto complete for supplier name

				var dict = [];
				

				 var prStatus = $("#prstat").val();
					if (prStatus == 'cancelled'){
					 $('#statusInput :input').attr('disabled',true);
					 $("#cancelPRq").addClass('disabled');
					 $("#approvePRq").addClass('disabled');
					 $("#closePRq").addClass('disabled');
					 $("#Newpo").addClass('disabled');
					 $("#Newgr").addClass('disabled');
		 			 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
			 		}
					else if (prStatus == 'approved'){
						 $('#statusInput :input').attr('disabled',true);
						 $("#approvePRq").addClass('disabled');
						 $("#Newpo").removeClass('disabled');
						 $("#Newgr").removeClass('disabled');
						 
			 			 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
			 			 
				 		}
					else if (prStatus == 'inprog'){
						$("#Newpo").addClass('disabled');
						 $("#Newgr").addClass('disabled');
						 
			 			 
				 		}
					else if (prStatus == 'closed'){
						 $('#statusInput :input').attr('disabled',true);
						 $("#approvePRq").addClass('disabled');
						 $("#closePRq").addClass('disabled');
						 $("#Newpo").addClass('disabled');
						 $("#Newgr").addClass('disabled');
						 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
				 		}
					else if (prStatus == 'completed'){
						console.log("done");
						 $('#statusInput :input').attr('disabled',true);
						 $("#approvePRq").addClass('disabled');
						 $("#closePRq").addClass('disabled');
						 $("#Newpo").addClass('disabled');
						 $("#Newgr").addClass('disabled');
						 
			 			 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
				 		}
			// load data in table
			    // when clicking on new ADD button no need to load data
			    if ('${ListPRqItem}' == "addNew") 
				{
			    	  // set status to new and green while new record
			           $("#formStatus").text("New");
							$('.dot').css({"background-color" : "orange"});
							FormSave.push("New");	
							$(".nextprvItems").addClass("hide-row ");
							$(".pad").removeClass("hide-row ");
																													 
			    		   		
			    }
																																											
		            if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${prCount}' != "addNew"){

							
					var prCount = ${prCount};
					
					if(($("#prcode").val()) != "" && ($("#prcode").val()) != null){

					if(SelectedIndex === prCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
																																				   
							
							if(!$("#btnNexta").hasClass("disabled")){
																																  
																														
																																	
																																  
																																	
																														
																														
																														
																														 
																																   
																																																   
													  
								
								$("#btnNext").click(function(){
														  
									
									var param ="${pageContext.request.contextPath}/PurchaseReqFormView?ID="+$("#prcode").val()+"&NavAction=1";
														   
									 
							   
							   
								  
									   
									   
																 

									window.location.href =param;
						
								});
															 
					
									  
														   
															
												 
													
											 
							 
							  
					  
											 
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/PurchaseReqFormView?ID="+$("#prcode").val()+"&NavAction=4";
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
						
						var param ="${pageContext.request.contextPath}/PurchaseReqFormView?ID="+$("#prcode").val()+"&NavAction=0";
						window.location.href =param;
						
					 });
				}
				$("#btnFrst").click(function(){

        			if(!$("#btnFrst").hasClass("disabled")){
        					
						var param ="${pageContext.request.contextPath}/PurchaseReqFormView?ID="+$("#prcode").val()+"&NavAction=3";
        				window.location.href =param;
        						
        				}
        				 });

			}
					
					}}
				}
					$("#label-1").text((SelectedIndex)+"/"+prCount);

				
  
	
							
			  
						   
															   
													 
												   
											
					 
							 
					 
									   
			    	
					$(document).trigger("triggerPageListenersEvent");		
 		 });
		}); //end ready document

	 
 </script>
 
				
		

					 
	
 
 <script>
 $(document).on("triggerPageListenersEvent", function () {
	 $(function(){
							 
		   

				  
												   
										  
		
	var unsaved = false;				 
	var FormSave=new Array();
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
                	warehouseName : ui.item[2],
				 },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
	               
	            

	                if(data.globalList.length == 1){
	                	
	                	
	                	$("#wareid").val(ui.item[0]);
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
}, minLength:0, maxShowItems: 40, scroll:true,

select: function(event, ui) {
prsuppid.value = (ui.item ? ui.item[0]  : '');
supplierName.value=ui.item[1];
prsuppaddress.value= ui.item[2];
return false;
}
}).autocomplete("instance")._renderItem = function(ul, item) {
return $("<li class='each'>")
.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
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
     }, minLength:0, maxShowItems: 40, scroll:true,

		select: function(event, ui) {
			supplierName.value = (ui.item ? ui.item[0]  : '');
			 	


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
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                item[0] + "</span></div>")
            .appendTo(ul);
    	};
		$("#supplierName").focus(function(){
	        	if (this.value == ""){
	            	$(this).autocomplete("search");
	        	}						
		});
	
										  
									  
												   
					   
		 
		
	  
										   



			


					   



	
	 $("#closePRq").click(  function() {	
		 var prStatus=$("#prstat").val();
		 $("#prstat").val('closed').trigger('change');
		});
		
	 $("#approvePRq").click(  function() {
		 unsaved = false;	
		 pRqAppFlag = 1;
		 pRqCnclFlg = 0;
		 FormSave.push("Approve");											   
		 //var prStatus=$("#prstat").val();
		 $("#prstat").val('approved').trigger('change');
		var checkSaving = true;
		 
		 $(function(){
	 		checkSaving = checkedDataOnAction();
	 		if(checkSaving !== false){
	 			getselectedrows();
				saverowsintables(); 
	 		}
		 	});
		 
		});

		//Cancel Action
	 $("#cancelPRq").click(  function() {
		 unsaved = false;	
		 pRqAppFlag = 0;
		 pRqCnclFlg = 1;
		 FormSave.push("Cancel");
		var checkSaving = true;
		 
		 $(function(){
	 		checkSaving = checkedDataOnAction();
	 		if(checkSaving !== false){
	 			getselectedrows();
				saverowsintables(); 
	 		}
		 	});			 
		});// end of Cancel Action


						 
	 
			
							 


														
   
								
	
		      

//main SAVE button
	$(document).on('click', '#saveButton', function() {
		 unsaved = false;
		 pRqCnclFlg = 0;
		 pRqAppFlag = 0;
		 FormSave.push("Save");

		 var checkSaving = true;
		 
		 $(function(){
	 		checkSaving = checkedDataOnAction();
	 		if(checkSaving !== false){
	 			getselectedrows();
				saverowsintables(); 
	 		}
		 	});
		
		 
	 }); // end of main SAVE button

	 
	 $("#deleteButton").click(  function() {
		unsaved = false;
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/DeletePRQ",
				dataType : "json",
				data : {
					"ID" : [$("#prcode").val()]
				},
				success : function(one) {
					location.replace("${pageContext.request.contextPath}/PurchaseReqListView"); 
				},
				error : function(error) {
					console.log("The error is " + error);
				}
			});	 
		
			
		 
	 });// end of main delete button

	// save Data in DB
   
	
	
								 
   

					  
															
										
				   
							

							   
	
				   
				
						 
   
						  
					   
		  
									 
										 
		
											
	   

													
														
								 
		
										   
								
											   
										
						
						 
				 
										
			
		 
										 
							 
											  
										
					   
						 
				
									   

		   
		
													
								 
											  
											
					   
						
				
									   

		   
		
										
								 
											  
									   
					   
						
				
									   
		   
		

		   
		
	  
		 
			 
														 
										
	  var prStatus = $("#prstat").val();
						  
	
		   
	 
		
									
								
												   
										 
										  
										
									 
									 
																   
	 
									
													
										   
									   
									   
																	
	  
	  
								  
													
										   
										 
									   
									   
																	
	  
									 
													
										   
										 
									   
									   
																	
	  
 
											  
							   
			 
   
																	  
				   
																											 
																											   
																												 
																										   
																								
																								  
																								  
																								  
																								  
																								  
																								  
																													  
																								  
																										
																									
																										
																								
																								
																							   
																								  
																								  
																										
																															 
			
   
			 
     function saverowsintables (){
         			
		     var token =  $('input[name="csrfToken"]').attr('value'); 
		    
	        //save rowsin tables
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/PurchaseReqFormSave",
				dataType : "json",
				  headers: {
                      'X-CSRFToken': token 
                  },
					data : {
						"type": '${ListPRqItem}',
						"FormSave": FormSave,
 					"dictParameter" : dict,
 					"slctDel": slctDel,
						"ID" : $("#prcode").val(),
						"creationDate" : $("#prcreatedate").val(),
						"lastmodifiedDate" : $("#prlstmodifdate").val(),
						"supplier" : $("#prsuppid ").val(),
						"supplierName": $("#supplierName").val(),
						"supplierAddress" : $("#prsuppaddress").val(),
						"orderedDate" : $("#prorderdate").val(),
						"delivereyDate" : $("#prdeliverydate").val(),
						"WareHouse" :$("#wareid").val(),
						"WareName" : $("#warename").val(),
						"siteID" : $("#siteid").val(),  
						"TotalAmount" :    $("#prtotamnt").val(),
						"discAmnt" : $("#prdiscamnt").val(),
						"discountPercent" : $("#prdiscpercent").val(),
						"paidAmount" : $("#prpaidamnt").val(),
						"prOutstanding" : $("#proutstand").val(),
						"prStatus" :  $("#prstat").val(),
						"netTotal" : $("#prtotword").val(),
						"netTotalinWord" : '0',
						"TotalQty" : $("#prtotqty").val(), 
						"priFarNo" : '0',
						"priDarNo" : '0',
						"email": $("#email").val(),
						"password":$("#password").val(),
				  	    "emailTo": $("#emailTo").val(),
					    "ccmail": $("#ccmail").val(),
					    "subject": $("#subject").val(),
					    "message": $("#message").val(),
					    "pRqAppFlag" : pRqAppFlag,
					    "pRqCnclFlg" : pRqCnclFlg,
					},
					success : function(data) {
						unsaved = false;
						 
						$('#prlstmodifdate').val(data.lastmodifiedDate);
						if(prStatus == 'approved')	$("#closePRq").removeClass('disabled');		  
						// update dot save to green 
						
						prcode.value=data.PREQID;
						  
						if(data.RevPrcStatCancel)
 						{
 						if(data.RevPrcStatCancel == "rejected")
 							alert("The purchase request has been saved, but cancelation didn't occur because the purchase order "+data.POtobeCanceled+" that is related to this PR needs cancelation, thank you!");
 						else 
     						alert("Cancelation occured, thank you.");
							}
						if(data.GrStatCancel){
							if(data.GrStatCancel == "rejected")
 							alert("The purchase request has been saved, but cancelation didn't occur because the Goods Received "+data.GRtobeCanceled+" that is related to this PR needs cancelation, thank you!");
 						else 
     						alert("Cancelation occured, thank you.");
							}
						var param ="${pageContext.request.contextPath}/PurchaseReqFormView?ID="+$("#prcode").val()+"&NavAction=2";
						location.replace(param);  						    						
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});
				
   
    		}   //end save data 
 $("#statusInput").change(function() {
	 var prStatus = $("#prstat").val();
		   
  
 
	 if(prStatus == 'approved'){
	 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	 $('#statusInput :input').attr('disabled',true);
	 $("#approvePRq").addClass('disabled'); 
	 $("#Newpo").removeClass('disabled');
	 $("#Newgr").removeClass('disabled');
	  
	 }
 else if(prStatus == 'inprog') {
	 $("#approvePRq").removeClass('disabled');
	 $("#closePRq").addClass('disabled');
	 $("#Newpo").addClass('disabled');
	 $("#Newgr").addClass('disabled');
	 
																  

								 
	
																 
											 
																		  
		  
																  
																		   

	
										
	
		  
	 
											  
	  
									 
	   
 }
 else if (prStatus == 'completed'){
	 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	 $('#statusInput :input').attr('disabled',true);
	 $("#approvePRq").addClass('disabled');
	 $("#closePRq").addClass('disabled');}
 else if (prStatus == 'closed'){ 
	 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	 $('#statusInput :input').attr('disabled',true);
	 $("#approvePRq").addClass('disabled');
	 $("#closePRq").addClass('disabled');}
	 
 else if (prStatus == 'cancelled'){
	 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	 $('#statusInput :input').attr('disabled',true);
	 $("#cancelPRq").addClass('disabled');
	 $("#approvePRq").addClass('disabled');
			
										  
 }
 else {
	 $("#approvePRq").removeClass('disabled');
	 $("#closePRq").addClass('disabled');
	 }

			
	
			 
		   
	 
   
													  
	
																  
											  
																		   
		  
																 
											 
																		 
   
		 
											
});
								   
	  
																												
								  
	  
		 
	 
								   

												   
			 
																												
										
			  
			 
				

 $("#selectnav").autocomplete({
		
	    source: function(request, response) {
		    
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllPurchaseRequestsOrd',
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
					
					this.value = (ui.item ? ui.item[2] + ":" + ui.item[0] : '');
					var param ="${pageContext.request.contextPath}/PurchaseReqFormView?ID="+(ui.item[0])+"&NavAction=2";
					window.location.href =param;
						
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
	 		    	return $('<li class="each">').data( "item.autocomplete", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	                 item[0] + '</span><br><span class="desc">' +
	                 item[2] +','+item[3]+ '</span></div></li>').appendTo(ul);
			};
					$("#selectnav").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});   //// ENd of Autocomplete for Area ID
 
	
 ///////////////////////////////////////////
 $("#custom-tabs-overview").click(function() {
 	if($("#prcode").val()!=""){

 	$.ajax({
         type: "GET",
         contentType: "application/json; charset=utf-8",
         url: '${pageContext.request.contextPath}/GetPrqData',
         data: {
             
 				"ID" : $("#prcode").val(),
         },
         dataType: "json",
         success: function (data) {

         	 
             	$('#poCom').text(data.poCom);
             	$('#totQtyPOCom').text(data.totQtyPOCom);
             	$('#netPOCom').text(data.netPOCom);
             	$('#ponotCom').text(data.ponotCom);
             	$('#netponotCom').text(data.netponotCom);
             	$('#totqtypoNotCom').text(data.totqtypoNotCom);

             	$('#grCom').text(data.grCom);
             	$('#netGrCom').text(data.netGrCom);
             	$('#totQtyGrCom').text(data.totQtyGrCom);

             	$('#grNotCom').text(data.grNotCom);
             	$('#netGRNotCom').text(data.netGRNotCom);
             	$('#totqtyGRNotCom').text(data.totqtyGRNotCom);

             	$('#dnComp').text(data.dnComp);
             	$('#dnNetTotComp').text(data.dnNetTotComp);
             	$('#dnTotQtyComp').text(data.dnTotQtyComp);

             	$('#dnNotComp').text(data.dnNotComp);
             	$('#dnNetTotNotComp').text(data.dnNetTotNotComp);
             	$('#dnTotQtyNotComp').text(data.dnTotQtyNotComp);

             	$('#cipCountAll').text(data.cipCountAll);
             	$('#cipNetTotAll').text(data.cipNetTotAll);
             	$('#cipTotQtyAll').text(data.cipTotQtyAll);

             	$('#arCountAll').text(data.arCountAll);
             	$('#arValRateAll').text(data.arValRateAll);

             	$('#farCountAll').text(data.farCountAll);
             	$('#farValRateAll').text(data.farValRateAll);


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

	 });
	 
 });
function prstatCheck(){
	var prStatus = $("#prstat").val();
 	if(prStatus == 'approved'){
 		$("#Newgr").removeClass('disabled');  
		$("#Newpo").removeClass('disabled');  	
 	}
 	else {
 		$("#Newgr").addClass('disabled');  
		$("#Newpo").addClass('disabled'); 
		}
 	
}


	
	
	

</script>
 

	
 </html>
 
 

