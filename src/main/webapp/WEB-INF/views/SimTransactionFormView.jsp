<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
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
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	
  	<script src="${pageContext.request.contextPath}/resources/js/SimTrans_BoqPopup.js"></script>
		
    
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

				.rectangle {
				  height: 35px;
				  width: 70px;
				 
				background-color" : white;
				 color: black;
				  text-align:center;
				 vertical-align: middle;
                 line-height: 32px;
	           font-size:15px;
	           font-family: Times New Roman;
				display: inline-block;
				border-style:outset;
                border-color:chartreuse;
                border-width:2px;
				  
				  
				}
		
				.btn1{
			 
               color:white;
               font-family: 'Arial';
             font-size: 15px;
              background-color: green;
              
              border-radius:30px 30px 30px 30px;
               
			cursor: not-allowed;
             pointer-events: none;
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

  <c:set var="pg" value="inventory" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
     <p></p>

	<div class="container-fluid">
		<div class="row">
			
		
			<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Transaction ID</span>
							<input type="text" readonly id="transactionID"  value="${transactionID}" class="form-control text-input"   />
 							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" /> 
						</div>
					</div>
			</div>
			
				<div class="col-md-3">
				<div class="input-group-prepend">
				<span  class="input-group-text" style="width:210px;">Status</span>
				<select  id="status" class="form-control">
								<option disabled value="In Progress" <c:if test = "${status =='In Progress'}" > selected </c:if> >In Progress</option>
								<option disabled value="Approved" <c:if test = "${status =='Approved'}" > selected </c:if>>Approved</option>
								<option disabled value="Cancelled" <c:if test = "${status =='Cancelled'}" > selected </c:if>>Cancelled</option>
								</select>
				</div>							
			</div>


	<div class="pad col-md-3 hide-row"></div>
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Transactions</span>
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
	

			<div class="row">
	
	<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span style="width:200px;" class="input-group-text">Created Date</span>
					<input type="text" id="createdate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span style="width:200px;" class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${lastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					
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
			 			onclick='window.location.href = "${pageContext.request.contextPath}/SimTransactionFormView"'
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/SimTransactionListView"'
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
	
	<p></p>
						

	
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

            <li id="Buttons" class="nav-item ml-auto">
                
               <div class="dropdown" Style="display:inline-block;">
	           <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	                 aria-haspopup="true" aria-expanded="false">Actions</button>
	
	            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				 <a class="dropdown-item"  type="button" id="Approvest">Approve</a>
	              <a class="dropdown-item" type="button" id="Cancelst" >Cancel</a>

    	        </div>
    	 	<button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
    	 	       
    	 	       <button type="button" 
				onclick='window.location.href = "${pageContext.request.contextPath}/SimTransactionFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
						</button>  
    	 	       
    	 	           	<button type="button" id="deleteButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-trash"></i> Delete
				</button>  
                        
<!-- 				<button type="button" id="saveButton" -->
<!-- 				class="btn btn-primary BtnActive"> -->
<!-- 				<i class="fa fa-save"></i> Save -->
<!-- 				</button> -->
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
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span  class="input-group-text" style="width:210px;">Transaction Type</span>
				<select id="transactionType" class="form-control">
								<option value="Incoming" <c:if test = "${transactionType =='Incoming'}" > selected </c:if>>Incoming</option>
								<option value="Outgoing" <c:if test = "${transactionType =='Outgoing'}" > selected </c:if>>Outgoing</option>
								<option value="Transfer" <c:if test = "${transactionType =='Transfer'}" > selected </c:if>>Transfer</option>
								</select>
				</div>							
			</div>
		</div>

    <p></p>
    
<hr>


	<div class="row">

		
  </div>
	
<p></p>

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
						                <th>Source Name </th>
						                <th hidden>Source Type</th>
						                <th hidden >Source ID</th>
						                <th>Source MSISDN</th>	
						                <th>Destination Name</th>
						                <th hidden>Destination Type</th>					                		
										<th hidden>Destination ID</th>
										<th>Qty</th>
										<th>Creation Date</th>
						                <th>Last Modified Date</th>
						                <th>Transaction Item ID</th>
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
				<div class="input-group-prepend">
				<span class="input-group-text">Total QTY</span>
				<input type="text" id="totqty" readonly value="${totalQty}"  class="form-control text-input" />
				</div>							
		</div>
	</div>
<p></p>

</div>






        </div>
        </div>
		
<!-- Popup--> 
<div class="container">
<div id ="trModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
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
   			 <a class="nav-link active" id="item-tab" style="color: gold;" data-toggle="tab" href="#item" role="tab" aria-controls="item" aria-selected="true">TRANSACTION</a>
  		</li>
  
  		<li class="nav-item">
   			 <a class="nav-link " id="serialNum-tab" style="color: gold;" data-toggle="tab" href="#serialNum" role="tab" aria-controls="serialNum" aria-selected="false">SERIAL & MSISDN</a>
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
					<span class="input-group-text" >Source Name</span>
						<input type="text" id="popupSource" class="form-control text-input" value="${popupSource}" style="width:674px; height:37px" />
				</div></div></div>
				
<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Source Type</span>
						<input type="text" readonly class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupSourceType" value="${popupSourceType}" style="width:674px; height:37px"  />
</div></div></div>				
</div>   
 </div>





	<div class="container-fluid">
	<div class="row">

					
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text" >Source ID</span>
				<input type="text" id="popupSourceID" class="form-control text-input" value="${popupSourceID}" style="width:674px; height:37px" />
						
					</div></div></div>

<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Source MSISDN</span>
				<input type="text" id="popupSourceMSISDN" class="form-control text-input" value="${popupSourceMSISDN}" style="width:674px; height:37px"  />  
						
				</div></div></div>
				
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Destination Name</span>
				<input type="text" id="popupDestination" class="form-control text-input" value="${popupDestination}" style="width:674px; height:37px"  />  
						
				</div></div></div>				
				
<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Destination Type</span>
				<input type="text" readonly id="popupDestinationType" class="form-control text-input" value="${popupDestinationType}" style="width:674px; height:37px"  />  
						
				</div></div></div>


								<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Destination ID</span>
				<input type="text" id="popupDestinationID" class="form-control text-input" value="${popupDestinationID}" style="width:674px; height:37px"  />  
						
				</div></div></div>
				
													
	 			<div class="col-md-6">
				<div class="input-group-prepend">
				<span class="input-group-text">Sim Cards Qty</span>
				<input type="text" id="simCardsQty" readonly  class="form-control text-input" />
				</div>							
		</div>


	</div> 


	
	</div>

<div class="container-fluid">

	<div class="row">
	
					
				
				<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Transaction Item ID</span>
				<input type="text" readonly id="popupTransactionItemID" class="form-control text-input" value="${popupTransactionItemID}" style="width:674px; height:37px"  />  
						
				</div></div></div>
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Creation Date</span>
						<input type="text" readonly id="popupCreationDate" class="form-control text-input" value="${popupCreationDate}" style="width:674px; height:37px" />
				</div></div></div>
				
					

					</div>
					<div class="row">
					<div class="col-sm-6">
					 </div>
					<div class="col-sm-6">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text" >Last Modified Date</span>
				<input type="text" readonly id="popupLastModifiedDate" class="form-control text-input" value="${popupLastModifiedDate}" style="width:674px; height:37px" />
						
					</div></div></div>
					

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
					 <th width="232px">MSISDN</th>
                     <th width="232px">SIM ID</th>
                     <th width="232px" hidden>Serial Item ID </th>
						              
					</tr> </thead> <tbody> </tbody> </table>
					</div>
					  	<input type="text" id="RowIndex2" value="" hidden>
						<button type="button" class="add-row-serial" onclick="addRowSerial()">Add Row</button>
						<button type="button" class="delete-row-serial">Delete Row</button>
					</form>
					<p></p>
					<p></p>
				<div class="row">
	 			<div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Number</span>
				<input type="text" id="totNmbr" readonly  class="form-control text-input" />
				</div>							
				</div>
				</div>
					</div> 


					
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
/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
 if ('${docStatus}' == "addNew") {
		$("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});	
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");
	}

$("#sendEmail").on("click", function () {
	 console.log("Helloooo in sendEmail onClick");
     $("#popUpDiv").fadeIn();
    
     });
 
 $("#cancelButton").on("click", function () {
	 console.log("Helloooo in cancelButton onClick");
	 $("#email").val('');
	 $("#password").val('');
	 $("#emailTo").val('');
	 $("#ccmail").val('');
	 $("#subject").val('');
	 $("#message").val('');
     $("#popUpDiv").fadeOut();
     });

 $("#send").on("click", function () {
	 console.log("Helloooo in send onClick");
	if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
		{
		alert("All Fields are required");
		}
	else{
		 $("#popUpDiv").fadeOut();
	}
    
     });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




		$("#status").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
		});

		$("input").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
		});


		$("#transactionType").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
			});

		$("#popupSource,#popupSourceID,#popupSourceMSISDN,#popupDestination,#popupDestinationID").on('keyup change focus', function () {
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
			
		$("input").click(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
		});
		
		$("#serialNoTable ").change(function() {
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

		$("#serialNoTable > tbody").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
		});	


		 $("#status").change(function() {
			 var status = $('#status :selected').text();
		 	if(status == 'Approved'){						
		 		$("#Approvest").addClass('disabled');   
		 		//$("#Cancelst").removeClass('disabled');
				// $('#custom-tabs-one-tabContent :input').attr('disabled',true);

				}	
	 		else if (status == 'Cancelled'){
				 $("#Cancelst").addClass('disabled');
				 $("#Approvest").addClass('disabled');
				 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	
			 }
	 		else {
		 		$("#Approvest").removeClass('disabled');
		 		$("#Cancelst").removeClass('disabled');
			 	}
	 		
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});			
				});
			
	 
	$(document).ready(function() {

		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${simTransCount}' != "addNew"){

					var simTransCount = ${simTransCount};
					
					if(($("#transactionID").val()) != "" && ($("#transactionID").val()) != null){

					if(SelectedIndex === simTransCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									
									var param ="${pageContext.request.contextPath}/SimTransactionFormView?transactionID="+$("#transactionID").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/SimTransactionFormView?transactionID="+$("#transactionID").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/SimTransactionFormView?transactionID="+$("#transactionID").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/SimTransactionFormView?transactionID="+$("#transactionID").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}
					$("#label-1").text((SelectedIndex)+"/"+simTransCount);

					 $("#selectnav").autocomplete({
			    			
			    		    source: function(request, response) {
			    			    
			    		             $.ajax({
			    		                 type: "GET",
			    		                 contentType: "application/json; charset=utf-8",
			    		                 url: '${pageContext.request.contextPath}/GetAllSimTrans',
			    		                 data: {
			    								"simTrans" : $("#selectnav").val(),
			    						 },
			    		                 dataType: "json",
			    		                 success: function (data) {
			    		                     if (data != null) {
			    		                         response(data.ListSimTrans);
			    		                     }
			    		                 },
			    		                 error: function(result) {
			    		                     alert("Error");
			    		                 }
			    		             });
			    		         }, minLength:0, maxShowItems: 40, scroll:true,

			    					select: function(event, ui) {
			    						
			    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
			    						
			    						var param ="${pageContext.request.contextPath}/SimTransactionFormView?transactionID="+(ui.item[0])+"&NavAction=2";
			    						window.location.href =param;
			           						
			           						return false;
			           					}
			           				}).autocomplete("instance")._renderItem = function(ul, item) {
			           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
			           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
			           	                 item[0] + '</span><br><span class="desc">' +
			           	                 item[1] + '</span></div></li>').appendTo(ul);
			           			};
			           					$("#selectnav").focus(function(){
			           		   	        	if (this.value == ""){
			           		   	            	$(this).autocomplete("search");
			           		   	        	}						
			           					});   //// ENd of Autocomplete for Area ID
			    	

	 
		var unsaved = false;
		var approveFlag=0;
		var validated = false;


		$(window).bind('beforeunload', function() {
		    if(unsaved){
		        return "You have unsaved changes. Do you want to leave and discard?";
		    }
		});

		$(document).on('change', ':input', function(){
		    unsaved = true;
		 //   jQuery("span[id=dotStatus]").css( "background-color", "orange" );
		});

		var saveButton = '<button type="button" id="saveButton" class="btn btn-primary BtnActive"><i class="fa fa-save"></i> Save </button>';
		var ammendButton = '<button type="button" id="ammendButton" class="btn btn-primary BtnActive"><i class="fa fa-ammend"></i> Ammend </button>';



			var status = $('#status :selected').text();
			if($("#transactionID").val() == "") {
				$("#Cancelst").addClass('disabled');
				$("#Approvest").addClass('disabled');
				}
			
			if(status == "Cancelled"){
				
			$("#Buttons").append(ammendButton);
			
			}else{

			$("#Buttons").append(saveButton);
			}



			

		if ('${ListTransItem}' == "addNew") {
			 $("#formStatus").text("New");
				$('.dot').css({"background-color" : "orange"});
				$("#status").val('In Progress').trigger('change');
		}
		else{}

		if ('${ListTransItem}' == "Empty") {
			 $("#formStatus").text("Saved");
				$('.dot').css({"background-color" : "chartreuse"});
			//	$("#status").val('In Progress').trigger('change');
		}
		else{}

		

		function SetIndexRow(obj)
		{
			 
			var row_index = $(obj).parent().parent().index();
			$("#RowIndex").val(row_index);
		}

 				$('body').on('click', '#selectAll', function  () {
 					   
   					if ($(this).hasClass('allChecked')) {
      				$('input[type="checkbox"]', '#bisotab').prop('checked', false);
   						} else {
    						$('input[type="checkbox"]', '#bisotab').prop('checked', true);
    						}
    						$(this).toggleClass('allChecked');
    				
  					})
  					
  				$('body').on('click', '#selectAllSerial', function  () {
 					   
   					if ($(this).hasClass('allChecked')) {
      				$('input[type="checkbox"]', '#serialNoTable').prop('checked', false);
   						} else {
    						$('input[type="checkbox"]', '#serialNoTable').prop('checked', true);
    						}
    						$(this).toggleClass('allChecked');
    				
  					})
 			   	 
 		        var slctDel= [];
 		        $(".delete-row").click(function(){
 		        	
 		        	var checked="false"; //when no checkbox is checked
 		            var pri_Pk = "";  
 				   
 		       	   	$("#bisotab > tbody").find('input[name="record"]').each(function(){

 		             if($(this).is(":checked")){ 
 		            	 checked="true"; //when 1 or more checkbox is checked
 		            			 
 		       				pri_Pk=$(this).parent().parent().children('td[name="trItemID"]').children('input').val();
 		       				if( pri_Pk !=0)
 		       					slctDel.push(pri_Pk);
 		     			       
 								  
 		       				$(this).parents("tr").remove(); 
 	 			           var rowCount = document.getElementById('bisotab').rows.length;
 	 			          rowCount=--rowCount;
 	  			          $("#totqty").val(rowCount);   
 		       			console.log("deleted slctDel:"+ slctDel);
 			    	}   //end of checked box in boq for delete
 		       			});
 		       	   	if(checked=="false"){
 											  
 		       	 	alert(' Select Row(s) to Delete');
 					
 		         	return false;
 		       	   	}
 		       	   	
 					            
 		        });// end delete row
		
 		       // Delete row in serialNo Table	
 		         var slctDel1= [];
		       $(".delete-row-serial").click(function () {
		       slctDel1 = [];
		       
		       $("#serialNoTable > tbody").find('input[name="record"]').each(function () {
		       if ($(this).is(":checked")) {
		      	slctDel1.push($(this).parent().parent().children('td[name="serialId"]').children('input').val());
		       }
		       });

		      	for (i = 0; i <= slctDel1.length; ++i) {
		       	if (slctDel1.length == 0) {
		          	alert(' Select Row(s) to Delete');
		              return false;
		           }
		           }
		      //	var rowCount = document.getElementById('serialNoTable').rows.length;
		      	$("#serialNoTable > tbody").find('input[name="record"]').each(function () {
		          	if ($(this).is(":checked")) {
		             	$(this).parents("tr").remove();	 			       
	 	 			        //  rowCount=--rowCount;
 	 			           var rowCount = document.getElementById('serialNoTable').rows.length;
 	 			          rowCount=--rowCount;
 	  			          $("#totNmbr").val(rowCount); 
 	  			          $("#simCardsQty").val(rowCount);
	 	  			       
		             	}
		           
		      	});
		       }); // end delete row fct


		var dict = [];

		 $("#Approvest").click(  function() {
 			var checked= checkTable();
				if(checked){
			 unsaved = false;	
			 approveFlag = 1;
 			 $("#status").val('Approved').trigger('change');
 		//	 getselectedrows();
		//	 saverowsintables();
				}
			});

			//Cancel Action
		 $("#Cancelst").click(  function() {
 			var checked= checkTable();
				if(checked){
			 unsaved = false;	
			 approveFlag = 0;
			 $("#status").val('Cancelled').trigger('change');
 		//	 getselectedrows();
		//	 saverowsintables();	
				}		 
			});// end of Cancel Action


		 $(document).on('click', '#ammendButton', function() {
			
			 $("#ammendButton").remove();
			 $('#custom-tabs-one-tabContent :input').attr('disabled',false);
			 $("#formStatus").text("Not Saved");
		     $('.dot').css({"background-color" : "orange"});
		 	 $("#Buttons").append(saveButton);
			 $("#status").val('In Progress').trigger('change');
			});

	 //main SAVE button
		$(document).on('click', '#saveButton', function() {
 			var checked= checkTable();
			if(checked){
			 unsaved = false;			 
			 getselectedrows();
 			 saverowsintables();
			}	 
		 }); // end of main SAVE button

		 
		 $("#deleteButton").click(  function() {
			unsaved = false;

				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/DeleteTransaction",
					dataType : "json",
					data : {
						"transactionID" : $("#transactionID").val()
					},
					success : function(one) {
						console.log(one.test);
						location.replace("${pageContext.request.contextPath}/SimTransactionListView");
						 
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});	 

		 })// end of main delete button

	    if ('${ListTransItem}' != "addNew") {

		boqArray = ${ListTransItem};

		    	for (i = 0;i<boqArray.length;i++){

					var serialArrays = [];
				 	if (boqArray[i].serial_obj != null) {
				    serialArrays.push(boqArray[i].serial_obj);
					}
				  	else{
				  	serialArrays.push(null);
				 	} 
		
			   	   	var stype =  boqArray[i].sourceType;
		   	   		if(stype == null){
		   	   		stype = "";
		   	   		}
		   	   		else{
		   	   		stype =  boqArray[i].sourceType;
		   	   		}
		 
		   	  	 	var source =  boqArray[i].source;
			   		if(source == null){
			   			source = "";
			   		}
			   		else{
			   			source =  boqArray[i].source;
			   		}
			   	  	var sID =  boqArray[i].sourceID;
		   	   		if(sID == null){
		   	   		sID = "";
		   	   		}
		   	   		else{
		   	   		sID =  boqArray[i].sourceID;
		   	   		}
		   	   		var smsisdn =  boqArray[i].sourceMSISDN;
			   		if(smsisdn == null){
			   			smsisdn = "";
			   		}
			   		else{
			   			smsisdn =  boqArray[i].sourceMSISDN;
			   		}
			   	   	var dtype =  boqArray[i].destinationType;
			   		if(dtype == null){
			   			dtype = "";
			   		}
			   		else{
			   			dtype =  boqArray[i].destinationType;
			   		}
		
			   	   	var dest =  boqArray[i].destination;
			   		if(dest == null){
			   			dest = "";
			   		}
			   		else{
			   			dest =  boqArray[i].destination;
			   		}
		
			   	   	var destID =  boqArray[i].destinationID;
			   		if(destID == null){
			   			destID = "";
			   		}
			   		else{
			   			destID =  boqArray[i].destinationID;
			   		}
		
			   		var totalQty =  boqArray[i].totalQty;
			   		if(totalQty == null){
			   			totalQty = "";
			   		}
			   		else{
			   			totalQty =  boqArray[i].totalQty;
			   		}
		
			   	   	var cdate =  boqArray[i].creationDate;
			   		if(cdate == null){
			   			cdate = "";
			   		}
			   		else{
			   			cdate =  boqArray[i].creationDate;
			   		}
				   		var creationDate = new Date(cdate);
						var eyyy = moment(cdate).format('L') +" "+ moment(cdate).format('LT');
			   	   	var lmdate =  boqArray[i].lastModifieddate;
			   	   	
			   		if(lmdate == null){
			   			lmdate = "";
			   		}
			   		else{
			   			lmdate =  boqArray[i].lastModifieddate;
			   		}
			   	
					var lastModifiedDate = moment(lmdate).format('L') +" "+ moment(lmdate).format('LT');
			   		
			   	   	var itemID =  boqArray[i].simTransactionItemID;
			   		if(itemID == null){
			   			itemID = "";
			   		}
			   		else{
			   			itemID =  boqArray[i].simTransactionItemID;
			   		}


 		        	var markup = "<tr><td><input type='checkbox' name='record' style='width: 14px;height: 13px;position:relative;left:6px;'><span onclick='showMsg(this)' class='dotStatus' id='dotStatus' style='position:relative;left:3px;'></span><button type='button' href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default'  style='margin-bottom:3px;'><i class='fas fa-desktop'></i></button></td>"
 						+"<td name='trSource'>"
 						+"<input name='source' type='text' value='" + source + "' style='width:200px;' class='form-control text-input'/></td>"
 						+"<td hidden name='trSourceType'>"
 						+"<input name='sourceType' readonly type='text' value='" + stype + "' style='width:200px;' class='form-control text-input'/></td>"
 						+"<td hidden name='trSourceID'>"
 						+"<input name='sourceID' type='text' hidden value='" + sID + "'   style='width:200px;' class='form-control text-input'/></td>"
 						+"<td  name='trSourceMSISDN'>"
 						+"<input name='sourceMSISDN' type='text' value='" + smsisdn + "'    style='width:200px;' class='form-control text-input'/></td>"
 						+"<td  name='trDestination'>"
 						+"<input name='destination' type='text' value='" + dest + "'   style='width:200px;' class='form-control text-input'/></td>"
 						+"<td hidden name='trDestinationType'>"
 						+"<input name='destinationType' readonly type='text' value='" + dtype + "'   style='width:200px;' class='form-control text-input'/></td>"
 						+"<td hidden name='trDestinationID'>"
 						+"<input name='destinationID' type='text' hidden value='" + destID + "'   style='width:200px;' class='form-control text-input'/></td>"
 						+"<td  name='trTotQty'>"
 						+"<input name='totQty' type='text' readonly  value='" + totalQty + "'   style='width:200px;' class='form-control text-input'/></td>"
 						+"<td  name='trCreationDate'>"
 						+"<input name='creationDate' type='text' value='" + eyyy + "'  style='width:200px;' class='form-control text-input' readonly/></td>"
 						+"<td  name='trLastModifiedDate'>"
 						+"<input name='lastModifiedDate' type='text' value='" + lastModifiedDate + "'  style='width:200px;' class='form-control text-input' readonly/></td>"
 						+"<td name='trItemID'><input type='text' value='" + itemID + "' readonly value= 0 style='width:200px;' class='form-control text-input'></td>"
 						+"<td hidden name='serialNo'><input type='text'  style='visibility:hidden;width:200px;' value='" + serialArrays[0] +"' hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
 						+"</tr>";
 					    $("#bisotab > tbody").append(markup);
 					    
 						var newRowCount =  $("#bisotab >tbody tr").length;
 			      		boqAutocomplete(newRowCount,"bisotab");
			        
			   }
		   
	    }
			     	
       else {
          // set status to new and green while new record
           $("#formStatus").text("New");
				$('.dot').css({"background-color" : "orange"});
			//	FormSave.push("New");	
				
       }   

		 var status = $('#status :selected').text();
		 	if(status == 'Approved'){						
		 		$("#Approvest").addClass('disabled');   
			//	 $('#custom-tabs-one-tabContent :input').attr('disabled',true);

				}
	 		else if (status == 'Cancelled'){
				 $("#Cancelst").addClass('disabled');
				 $("#Approvest").addClass('disabled');
				 $('#custom-tabs-one-tabContent :input').attr('disabled',true);
	
			 }
	 		else {
		 		$("#Approvest").removeClass('disabled');
		 		$("#Cancelst").removeClass('disabled');
			 	}

	    //function to  get selected rows for save
		function getselectedrows () {
			dict = [];
			
		
			$("#bisotab > tbody").find('input[name="record"]').each(function(){
		

				    dict.push({
				    	 "trSourceType" : $(this).parent().parent().children('td[name="trSourceType"]').children('input').val(),
				    	 "trSource" : $(this).parent().parent().children('td[name="trSource"]').children('input').val(),
						 "trSourceID" : $(this).parent().parent().children('td[name="trSourceID"]').children('input').val(),
						 "trSourceMSISDN" : $(this).parent().parent().children('td[name="trSourceMSISDN"]').children('input').val(),
						 "trDestinationType" : $(this).parent().parent().children('td[name="trDestinationType"]').children('input').val(),
						 "trDestination" : $(this).parent().parent().children('td[name="trDestination"]').children('input').val(),
						 "trDestinationID" : $(this).parent().parent().children('td[name="trDestinationID"]').children('input').val(),
						 "trTotQty" : $(this).parent().parent().children('td[name="trTotQty"]').children('input').val(),
						 "trCreationDate" : $(this).parent().parent().children('td[name="trCreationDate"]').children('input').val(),
						 "trLastModifiedDate" : $(this).parent().parent().children('td[name="trLastModifiedDate"]').children('input').val(),
						 "trItemID" : $(this).parent().parent().children('td[name="trItemID"]').children('input').val(),
						 "serialNo" : $(this).parent().parent().children('td[name="serialNo"]').children('input').val()
						    });
			
             
			});
              
		}//end of selected rows

		function checkTable () {
			var checked=true;			
			 if ($('#bisotab > tbody > tr').length == 0) {
				   alert("fill table");
				   checked= false;
				}else{		


			$("#bisotab > tbody").find('input[name="record"]').each(function(){

				    	if( $(this).parent().parent().children('td[name="trSource"]').children('input').val() == ''){
				    		$(this).parent().parent().children('td[name="trSource"]').children('input').focus();
				    		checked= false;
					    	}else
				    	
				    	if( $(this).parent().parent().children('td[name="trSourceMSISDN"]').children('input').val() == ''){
				    		$(this).parent().parent().children('td[name="trSourceMSISDN"]').children('input').focus();
				    		checked= false;
					    	}else

				    	if( $(this).parent().parent().children('td[name="trDestination"]').children('input').val() == ''){
				    		$(this).parent().parent().children('td[name="trDestination"]').children('input').focus();
				    		checked= false;
					    	}

			});

// 			$("#serialNoTable > tbody").find('input[name="record"]').each(function(){

// 		    	if( $(this).parent().parent().children('td[name="id"]').children('input').val() == ''){
// 		    	     $('#trModal').modal('show');
// 		    	     $('#serialNum-tab').tab('show');
// 		    		$(this).parent().parent().children('td[name="trSource"]').children('input').focus();
// 		    		checked= false;
// 			    	}

// 			});
				}

              return checked;
		}//end check table


	  
            function saverowsintables (){
            	//  var token =  $('input[name="csrfToken"]').attr('value');
//         			var checked= checkTable();
//       				if(checked){
             		$('.dotStatus').css({"background-color" : "orange"});
        			$('.dotStatusBoq').css({"background-color" : "orange"});
               var status = document.getElementById("status").value;
 			   var id = document.getElementById("transactionID").value;
 				$.ajax({
 					type : "POST",
 					url : "${pageContext.request.contextPath}/SimTransactionFormSave",
 					dataType : "json",
//  					  headers: {
//  	                        'X-CSRFToken': token 
//  	                    },
    					data : {
    						"type": '${ListTransItem}', 
    						//"checkSave": checkSave,
    					//	"FormSave": FormSave,
        					"dictParameter" : dict,
        					"slctDel[]": slctDel,
        					"slctDel1[]": slctDel1,
        					"approveFlag": approveFlag,
        					"transactionID" :id,
    						"creationDate" : $("#createdate").val(),
    						"lastmodifiedDate" : $("#lstmodifdate").val(),
    						"transactionType" : $("#transactionType ").val(),
    						"totalQty" : $("#totqty").val(), 
    						"status": status,
//     						"email": $("#email").val(),
//     						"password":$("#password").val(),
//     				  	    "emailTo": $("#emailTo").val(),
//     					    "ccmail": $("#ccmail").val(),
//     					    "subject": $("#subject").val(),
//     					    "message": $("#message").val(),
    					},
    					success : function(data) {

					if(data.jsonObjectErrors.length!==0){
						if(JSON.parse(data.jsonObjectErrors.incorrectIndex.length) !== 0 && approveFlag == '1'){
						jsonObjectError=data.jsonObjectErrors;
				for (let i = 0; i < jsonObjectError.incorrectIndex.length; i++) {
					$("#bisotab >tbody").find("tr").eq(jsonObjectError.incorrectIndex[i]).find('span[id="dotStatus"]').css({"background-color" : "red"});
   						}
							}
						else
							if(JSON.parse(data.jsonObjectErrors.incorrectIndex.length) !== 0){
     						$("#transactionID").val(data.transactionId);
    						var param ="${pageContext.request.contextPath}/SimTransactionFormView?transactionID="+$("#transactionID").val()+"&NavAction=2";
    						location.replace(param);
							}
   						 }
						 else{
         						$("#transactionID").val(data.transactionId);
        						var param ="${pageContext.request.contextPath}/SimTransactionFormView?transactionID="+$("#transactionID").val()+"&NavAction=2";
        						location.replace(param);
          						 }
						    						
    					},
    					error : function(error) {
    						console.log("The error is " + error);
    					}
    				});
    				
					console.log("id after request " + id);
                 //   }


           		}   //end save data


        	$("#popupSource").autocomplete({
        	    
        	    source: function(request, response) {


                     var location=$("#popupSource").val();
                    
                         $.ajax({
                             type: "GET",
                             contentType: "application/json; charset=utf-8",
                             url: '${pageContext.request.contextPath}/GetAllLocations',
                             data: {
            						
            						"location":location,
            				 },
        	                 dataType: "json",
        	                 success: function (data) {
        	                     if (data != null) {
        	                         response(data.listLocations);

        	                     }
        	                 },
        	                 error: function(result) {
        	                     alert("Error");
        	                 }
        	             });
        	         }, minLength:0, maxShowItems: 40, scroll:true,

        				select: function(event, data) {

            				if(data.item[0] == $("#popupDestinationID").val() ){
            					alert("Source and Destination can not be the same!");
	        					document.getElementById("popupSourceID").value=('');
	        					document.getElementById("popupSource").value=('');
	        					document.getElementById("popupSourceType").value=('');
                				}
            				else{
	        					document.getElementById("popupSourceID").value=(data.item ? data.item[0] : '');
	        					document.getElementById("popupSource").value=(data.item ? data.item[1]  : '');
	        					document.getElementById("popupSourceType").value=(data.item ? data.item[2]  : '');
	        					$("#formStatus").text("Not Saved");
	        					$('.dot').css({"background-color" : "orange"});
	        					 unsaved = true;
            				}
        					return false;
        				}
        			}).autocomplete("instance")._renderItem = function(ul, item) {
        		            return $("<li class='each'>")
        	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
        	                    item[1] + "</span><br><span class='desc'>" +
        	                    item[0]  +","+ item[2] + "</span></div>")
        	                .appendTo(ul);
        	        	};
        				$("#popupSource").focus(function(){
        	   	        	if (this.value == ""){
        	   	            	$(this).autocomplete("search");
        	   	        	}						
        				});

        	        	$("#popupSourceID").autocomplete({
        	        	    
        	        	    source: function(request, response) {


        	                     var location=$("#popupSourceID").val();
        	                    
        	                         $.ajax({
        	                             type: "GET",
        	                             contentType: "application/json; charset=utf-8",
        	                             url: '${pageContext.request.contextPath}/GetAllLocations',
        	                             data: {
        	            						
        	            						"location":location,
        	            				 },
        	        	                 dataType: "json",
        	        	                 success: function (data) {
        	        	                     if (data != null) {
        	        	                         response(data.listLocations);
        	        	                     }
        	        	                 },
        	        	                 error: function(result) {
        	        	                     alert("Error");
        	        	                 }
        	        	             });
        	        	         }, minLength:0, maxShowItems: 40, scroll:true,

        	        				select: function(event, data) {
            	        				alert("Source and Destination can not be the same!");
        	            				if(data.item[0] == $("#popupDestinationID").val() ){
        		        					document.getElementById("popupSourceID").value=('');
        		        					document.getElementById("popupSource").value=('');
        		        					document.getElementById("popupSourceType").value=('');
        	                				}
        	            				else{
        		        					document.getElementById("popupSourceID").value=(data.item ? data.item[0] : '');
        		        					document.getElementById("popupSource").value=(data.item ? data.item[1]  : '');
        		        					document.getElementById("popupSourceType").value=(data.item ? data.item[2]  : '');
        		        					$("#formStatus").text("Not Saved");
        		        					$('.dot').css({"background-color" : "orange"});
        		        					 unsaved = true;
        	            				}
        	        					return false;
        	        				}
        	        			}).autocomplete("instance")._renderItem = function(ul, item) {
        	        		            return $("<li class='each'>")
        	        	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
        	        	                    item[0] + "</span><br><span class='desc'>" +
        	        	                    item[1]  +","+ item[2] + "</span></div>")
        	        	                .appendTo(ul);
        	        	        	};
        	        				$("#popupSourceID").focus(function(){
        	        	   	        	if (this.value == ""){
        	        	   	            	$(this).autocomplete("search");
        	        	   	        	}						
        	        				});

	 	        				$("#popupDestination").autocomplete({
	 	        	        	    
	 	        	        	    source: function(request, response) {
	
	
	 	        	                     var location=$("#popupDestination").val();
	 	        	                    
	 	        	                         $.ajax({
	 	        	                             type: "GET",
	 	        	                             contentType: "application/json; charset=utf-8",
	 	        	                             url: '${pageContext.request.contextPath}/GetAllLocations',
	 	        	                             data: {
	 	        	            						
	 	        	            						"location":location,
	 	        	            				 },
	 	        	        	                 dataType: "json",
	 	        	        	                 success: function (data) {
	 	        	        	                     if (data != null) {
	 	        	        	                         response(data.listLocations);
	 	        	        	                     }
	 	        	        	                 },
	 	        	        	                 error: function(result) {
	 	        	        	                     alert("Error");
	 	        	        	                 }
	 	        	        	             });
	 	        	        	         }, minLength:0, maxShowItems: 40, scroll:true,
	
	 	        	        				select: function(event, data) {

	 	        	            				if(data.item[0] == $("#popupSourceID").val() ){
	 	        	            					alert("Source and Destination can not be the same!");
	 	        		        					document.getElementById("popupDestinationID").value=('');
	 	        		        					document.getElementById("popupDestination").value=('');
	 	        		        					document.getElementById("popupDestinationType").value=('');
	 	        	                				}
	 	        	            				else{
	 	        	            					
	 	        		        					document.getElementById("popupDestinationID").value=(data.item ? data.item[0] : '');
	 	        		        					document.getElementById("popupDestination").value=(data.item ? data.item[1]  : '');
	 	        		        					document.getElementById("popupDestinationType").value=(data.item ? data.item[2]  : '');
	 	        		        					$("#formStatus").text("Not Saved");
	 	        		        					$('.dot').css({"background-color" : "orange"});
	 	        		        					 unsaved = true;
	 	        	            				}

	 	        	        					return false;
	 	        	        				}
	 	        	        			}).autocomplete("instance")._renderItem = function(ul, item) {
	 	        	        		            return $("<li class='each'>")
	 	        	        	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	 	        	        	                    item[1] + "</span><br><span class='desc'>" +
	 	        	        	                    item[0]  +","+ item[2] + "</span></div>")
	 	        	        	                .appendTo(ul);
	 	        	        	        	};
	 	        	        				$("#popupDestination").focus(function(){
	 	        	        	   	        	if (this.value == ""){
	 	        	        	   	            	$(this).autocomplete("search");
	 	        	        	   	        	}						
	 	        	        				});
	
	 	        	        	        	$("#popupDestinationID").autocomplete({
	 	        	        	        	    
	 	        	        	        	    source: function(request, response) {
	
	
	 	        	        	                     var location=$("#popupDestinationID").val();
	 	        	        	                    
	 	        	        	                         $.ajax({
	 	        	        	                             type: "GET",
	 	        	        	                             contentType: "application/json; charset=utf-8",
	 	        	        	                             url: '${pageContext.request.contextPath}/GetAllLocations',
	 	        	        	                             data: {
	 	        	        	            						
	 	        	        	            						"location":location,
	 	        	        	            				 },
	 	        	        	        	                 dataType: "json",
	 	        	        	        	                 success: function (data) {
	 	        	        	        	                     if (data != null) {
	 	        	        	        	                         response(data.listLocations);
	 	        	        	        	                     }
	 	        	        	        	                 },
	 	        	        	        	                 error: function(result) {
	 	        	        	        	                     alert("Error");
	 	        	        	        	                 }
	 	        	        	        	             });
	 	        	        	        	         }, minLength:0, maxShowItems: 40, scroll:true,
	
	 	        	        	        				select: function(event, data) {
	
	 	   	 	        	            				if(data.item[0] == $("#popupSourceID").val() ){
	 	   	 	        	            					alert("Source and Destination can not be the same!");
	 		 	        		        					document.getElementById("popupDestinationID").value=('');
	 		 	        		        					document.getElementById("popupDestination").value=('');
	 		 	        		        					document.getElementById("popupDestinationType").value=('');
	 		 	        	                				}
	 		 	        	            				else{
	 		 	        	            					
	 		 	        		        					document.getElementById("popupDestinationID").value=(data.item ? data.item[0] : '');
	 		 	        		        					document.getElementById("popupDestination").value=(data.item ? data.item[1]  : '');
	 		 	        		        					document.getElementById("popupDestinationType").value=(data.item ? data.item[2]  : '');
	 		 	        		        					$("#formStatus").text("Not Saved");
	 		 	        		        					$('.dot').css({"background-color" : "orange"});
	 		 	        		        					 unsaved = true;
	 		 	        	            				}
	 	        	        	        					return false;
	 	        	        	        				}
	 	        	        	        			}).autocomplete("instance")._renderItem = function(ul, item) {
	 	        	        	        		            return $("<li class='each'>")
	 	        	        	        	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	 	        	        	        	                    item[0] + "</span><br><span class='desc'>" +
	 	        	        	        	                    item[1]  +","+ item[2] + "</span></div>")
	 	        	        	        	                .appendTo(ul);
	 	        	        	        	        	};
	 	        	        	        				$("#popupDestinationID").focus(function(){
	 	        	        	        	   	        	if (this.value == ""){
	 	        	        	        	   	            	$(this).autocomplete("search");
	 	        	        	        	   	        	}						
	 	        	        	        				});
	

			
}); //end ready document


 




 
</script>
 

	
 </html>