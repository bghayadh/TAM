<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Discovery New Form View</title>
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
<script src="${pageContext.request.contextPath}/resources/js/DiscoveryNew/DiscoveryNewAppForm.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	

	
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
<script src="${pageContext.request.contextPath}/resources/js/context-menu.js"></script>	
    
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

 .nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
 	</style>       
            
</head>
<body>

<%-- 	<c:set var = "page" value = "purchasing"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
     
  <c:set var="pg" value="Inventory" scope="session"  />
  <jsp:include page="header.jsp"></jsp:include>
     
     
     <p></p>
<div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" style="color:green;">DNitem Id</span>
							<input type="text" readonly id="DnItemId" value="${ID}" class="form-control text-input" />
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
						</div>

					</div>
			</div>
		<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" style="color:green;">Trans Id</span>
							<input type="text"  id="transId" value="${transId}" class="form-control text-input" readonly/>
							</div>

					</div>
			</div>
 
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other DN Item</span>
						<input type="text" id="selectnav" value="${selectnav}"
						style="width: 430px" class="form-control text-input" />		
				</div>
			</div>
		</div>
			 <div class="hide-row col-md-3 pad "></div>
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
			 			onclick='window.location.href = "${pageContext.request.contextPath}/DiscoveryNewApprovalFormView"'
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/DiscoveryNewApprovalListView"'
			        			data-placement="top" title="List View"> 
			        			<i class="fa fa-bars"></i>
			        	</button>
				 <button type="button" class="btn btn-light" data-toggle="tooltip"
            data-placement="top" title="Tree View"
            onclick='window.location.href = "${pageContext.request.contextPath}/DiscoveryNewTreeView"'>
            <i class="fas fa-sitemap"></i>
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
       
      
            <li id="Buttons" class="nav-item ml-auto">
           
             <div class="dropdown" style="display:inline-block;">
    <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
            aria-haspopup="true" aria-expanded="false" disabled>
      <span id="actionButtonText">Actions</span>
    </button>

    <div class="dropdown-menu" aria-labelledby="notifactionDropdown">
      <a class="dropdown-item" href="#" onclick="selectAction('Approved')">Approved</a>
      <a class="dropdown-item" href="#" onclick="selectAction('Rejected')">Rejected</a>
    </div>
  </div>	

           <button type="button" id="saveButton" onclick="saveForm()"
										class="btn btn-primary BtnActive" disabled>
									<i class="fa fa-save"></i> Save
								</button>
								<button type="button" id="deleteButton"
										class="btn btn-primary BtnActive" disabled>
									<i class="fa fa-trash"></i> Delete
									
								</button>
							<!--	<button type="button" id="addButton"
										class="btn btn-primary BtnActive" disabled>
									<i class="fa fa-plus"></i> ADD
								</button> -->
				  
                        
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
						  <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Item Code</span>
							<input type="text"  id="itemId" value="${itemId}" class="form-control text-input" />
							</div>

					</div>
					</div>
			</div>
			
			
			
			<div class="col-md-4">
			  <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Item Name</span>
							<input type="text"  id="itemName" value="${itemName}" class="form-control text-input" />
							</div>

					</div>
		</div>
		
		<div class="col-md-4">
			   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Item Model</span>
							<input type="text"  id="DitemModel" value="${itemModel}" class="form-control text-input" />
							</div>

					</div>
		</div>
			
			</div>
			
			
			<div class="row">
			<div class="col-md-4">
		   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Item PartNumber</span>
							<input type="text"  id="DitemPartNumber" value="${itemPartNb}" class="form-control text-input" />
							</div>

					</div></div>
			<div class="col-md-4">
    <div class="form-group">
        <div class="input-group-prepend">
            <span class="input-group-text">Transaction type</span>
            <select class='form-control' id="popupTransType" name="transType">
                <option value="-- Select Option --"
                    <c:if test="${transType == '-- Select Option --'}">selected</c:if>>-- Select Option --</option>

                <option value="New Node"
                    <c:if test="${transType == 'New Node'}">selected</c:if>>New Node</option>

                <option value="New Node on New Hardware"
                    <c:if test="${transType == 'New Node on New Hardware'}">selected</c:if>>New Node on New Hardware</option>

                <option value="New Node on Existed Hardware"
                    <c:if test="${transType == 'New Node on Existed Hardware'}">selected</c:if>>New Node on Existed Hardware</option>

                <option value="New Hardware on New Node"
                    <c:if test="${transType == 'New Hardware on New Node'}">selected</c:if>>New Hardware on New Node</option>

                <option value="New Hardware on Existed Node"
                    <c:if test="${transType == 'New Hardware on Existed Node'}">selected</c:if>>New Hardware on Existed Node</option>

                <option value="Existed Hardware on New Node"
                    <c:if test="${transType == 'Existed Hardware on New Node'}">selected</c:if>>Existed Hardware on New Node</option>

                <option value="Transfer"
                    <c:if test="${transType == 'Transfer'}">selected</c:if>>Transfer</option>

                <option value="Transfer from Slot to Slot"
                    <c:if test="${transType == 'Transfer from Slot to Slot'}">selected</c:if>>Transfer from Slot to Slot</option>

                <option value="Transfer from Node to Node"
                    <c:if test="${transType == 'Transfer from Node to Node'}">selected</c:if>>Transfer from Node to Node</option>

                <option value="Transfer from Site to Site"
                    <c:if test="${transType == 'Transfer from Site to Site'}">selected</c:if>>Transfer from Site to Site</option>

                <option value="Disappear"
                    <c:if test="${transType == 'Disappear'}">selected</c:if>>Disappear</option>

                <option value="Disappear for Maintenance"
                    <c:if test="${transType == 'Disappear for Maintenance'}">selected</c:if>>Disappear for Maintenance</option>

                <option value="Disappear for Transfer"
                    <c:if test="${transType == 'Disappear for Transfer'}">selected</c:if>>Disappear for Transfer</option>

                <option value="Maintenance"
                    <c:if test="${transType == 'Maintenance'}">selected</c:if>>Maintenance</option>

                <option value="Replacement"
                    <c:if test="${transType == 'Replacement'}">selected</c:if>>Replacement</option>

                <option value="Retirement"
                    <c:if test="${transType == 'Retirement'}">selected</c:if>>Retirement</option>
            </select>
        </div>
    </div>
</div>

  		<div class="col-md-4">
				<div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Description</span>
							<input type="text"  id="description" value="${description}" class="form-control text-input" />
							</div>

					</div>
			</div>
    </div>
    
    <p></p>
    
<div class="row">

			
			
		<div class="col-md-4">
    <div class="form-group">
        <div class="input-group-prepend">
            <span class="input-group-text">Element Name</span>
            <select class='form-control' id="popupElementName" name="elementName">
                <option value='-- Select Option --'
                    <c:if test="${elementName == '-- Select Option --'}">selected</c:if>>-- Select Option --</option>

                <option value='Cabinet'
                    <c:if test="${elementName == 'Cabinet'}">selected</c:if>>Cabinet</option>

                <option value='Subrack'
                    <c:if test="${elementName == 'Subrack'}">selected</c:if>>Subrack</option>

                <option value='Slot'
                    <c:if test="${elementName == 'Slot'}">selected</c:if>>Slot</option>

                <option value='Board'
                    <c:if test="${elementName == 'Board'}">selected</c:if>>Board</option>

                <option value='Port'
                    <c:if test="${elementName == 'Port'}">selected</c:if>>Port</option>

                <option value='Antenna'
                    <c:if test="${elementName == 'Antenna'}">selected</c:if>>Antenna</option>
            </select>
        </div>
    </div>
</div>
			
			
			
			 <div class="col-md-4">
				 <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Approval Pending</span>
							<input type="text"  id="approvalPen" value="${status}" class="form-control text-input" readonly/>
							</div>

					</div>
			</div>
	

	<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >PO</span>
							<input type="text"  id="PoId" value="${PO}" class="form-control text-input" />
							</div>

					</div>
			</div>
			
	
 </div>


<hr>

<div class="row">
			
			
			 <div class="col-md-4">
					  <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >WO</span>
							<input type="text"  id="WoId" value="${WO}" class="form-control text-input" />
							</div>

					</div>
			</div>
			
			
			
			 <div class="col-md-4">
				  <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >From Slot</span>
							<input type="text"  id="fromSlotId" value="${fromSlot}" class="form-control text-input" />
							</div>

					</div>
			</div>
			
			
				 <div class="col-md-4">
					  <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >To Slot</span>
							<input type="text"  id="toSlotId" value="${toSlot}" class="form-control text-input" />
							</div>

					</div>
			</div>
		
			</div>






<div class="row">
<div class="col-md-4">
					 <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >From Site</span>
							<input type="text"  id="fromSiteId" value="${fromSite}" class="form-control text-input" />
							</div>

					</div>
			</div>
<div class="col-md-4">
				 <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >To Site </span>
							<input type="text"  id="toSiteId" value="${toSite}" class="form-control text-input" />
							</div>

					</div>
<!-- 					<button id="add-row-bc">Add in poq table</button> -->

			</div>
			<div class="col-md-4">
					  <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >FAR Id</span>
							<input type="text"  id="farId" value="${farId}" class="form-control text-input" />
							</div>

					</div>
			</div>

 </div>





	<div class="row">

<div class="col-md-4">
				 <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >MAC Address </span>
							<input type="text"  id="macAddress" value="${macAddress}" class="form-control text-input" />
							</div>

					</div>
<!-- 					<button id="add-row-bc">Add in poq table</button> -->

			</div>
				<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Qty</span>
							<input type="text"  id="Qty" value="${qty}" class="form-control text-input" />
							</div>

					</div>
			</div>
			<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Rate </span>
							<input type="text"  id="rate" value="${rate}" class="form-control text-input" />
							</div>

					</div>
			</div>

 </div>
 
 
 
 <div class="row">

<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Discount Amount</span>
							<input type="text"  id="discountAmount" value="${discountAmount}" class="form-control text-input" />
							</div>

					</div>
			</div>
				<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Tax </span>
							<input type="text"  id="tax" value="${tax}" class="form-control text-input" />
							</div>

					</div>
			</div>
			<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Net Rate</span>
							<input type="text"  id="netRate" value="${netRate}" class="form-control text-input" />
							</div>

					</div>
			</div>
 </div>
 
 
 
 
 <div class="row">

<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Total AT</span>
							<input type="text"  id="totalAt" value="${totalAt}" class="form-control text-input" />
							</div>

					</div>
			</div>
				
				<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Old Serial Number</span>
							<input type="text"  id="oldSerialNum" value="${oldSerialNb}" class="form-control text-input" />
							</div>

					</div>
			</div>
			<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Serial Number</span>
							<input type="text"  id="serialNum" value="${toSerialNb}" class="form-control text-input" />
							</div>

					</div>
			</div>
 </div>
 
 
 <div class="row">
 
  <div class="col-md-6">
				 <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >Old Address</span>
							<input type="text"  id="oldAddress" value="${oldAddress}" class="form-control text-input" />
							</div>

					</div>
			</div>
			
			
			  <div class="col-md-6">
				 <div class="form-group">
						<div class="input-group-prepend">
						
							<span class="input-group-text" >DN ID</span>
							<input type="text"  id="dnID" value="${dnId}" class="form-control text-input" readonly/>
							</div>

					</div>
			</div>
 
 
 
 
 
 
 </div>
<p></p>
    	<!--  create table purchaserequestItem    -->

		<div> 
					<form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="toNodes" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            
									<tr class="fixed-headerr">
						                <th>
						                <div class="container">
								          <button type="button" id="selectAll-To-node" onClick="toggleSelectAllToNodes()" class="main">
								          <span class="sub"></span>Select</button></div></th>
								          
						                <th>To Node Id </th>
						                <th>To Node Name</th>
						                <th>To Node Type</th>
						                  </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
					    <input type="text" id="RowIndex1" value="" hidden>
						<button type="button" class="add-row" onClick="addRowToNode()">Add Row</button>
					    <button type="button" class="delete-To-node">Delete Row</button>
                   </form>
                   <br>
                   <br>
                   <form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="fromNodes" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            
									<tr class="fixed-headerr">
						                <th>
						                <div class="container">
								          <button type="button" id="selectAll-From-Node"  onClick="toggleSelectAllFromNodes()" class="main">
								          <span class="sub"></span>Select</button></div></th>
								          
						                <th>From Node Id </th>
						                <th>From Node Name</th>
						                <th>From Node Type</th>
						                  </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
					    <input type="text" id="RowIndex" value="" hidden>
					    <button type="button" class="add-row"  onClick="addRowFromNode('next')">Add Row</button>
					    <button type="button" class="delete-From-node">Delete Row</button>
                   </form>
		</div>		

    	

</div>





        </div>
        </div>
		
<!-- Popup--> 
<div class="container">
<div id ="preqModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
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


  
 </body>

<script>

var slctToNodeDel = [];
var slctFromNodeDel = [];
var ctx = getContextPath();
$('#itemId').autocomplete({
    source: function (request, response) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: ctx + '/getItemCode',
            data: {
                requestValue: request.term,
                barcode: $("#barcode").val()
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.ListItemDetails);
                }
            },
            error: function () {
                alert("Error");
            }
        });
    },
    minLength: 0,
    select: function (event, ui) {
    	this.value = (ui.item ? ui.item[0] : '');
        $('#DitemModel').val(ui.item[2]);
        $('#DitemPartNumber').val(ui.item[3]);
        $('#itemName').val(ui.item[1]);
   return false;
    }
}).autocomplete("instance")._renderItem = function (ul, item) {
    var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
        item[0] + "</span><br><span class='desc'>" +
        item[1] + "</span><span class='desc'>";
    if (item[2] !== '-') appendString += "," + item[2] + "</span><span class='desc'>";
    if (item[3] !== '-') appendString += "," + item[3] + "</span><span class='desc'>";
    if (item[4] !== '-') appendString += "," + item[4];
    appendString += "</span></div>";
    return $("<li class='each'>").append(appendString).appendTo(ul);
};

$('#itemId').focus(function () {
    if (this.value === "") {
        $(this).autocomplete("search");
    }
});


$('#DitemModel').autocomplete({
    source: function (request, response) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: ctx + '/getModel',
            data: {
                requestValue: request.term,
                barcode: $("#barcode").val()
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.ListModels);
                }
            },
            error: function () {
                alert("Error");
            }
        });
    },
    minLength: 0,
    maxShowItems: 40,
    scroll: true,
    select: function (event, ui) {
        this.value = (ui.item ? ui.item[0] : '');
        $('#itemId').val(ui.item[1]);
        $('#DitemPartNumber').val(ui.item[3]);
        $('#itemName').val(ui.item[2]);
        return false;
    }
}).autocomplete("instance")._renderItem = function (ul, item) {
    var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
        item[0] + "</span><br><span class='desc'>" +
        item[1] + "</span><span class='desc'>," +
        item[2] + "</span><span class='desc'>";
    if (item[3] !== '-') appendString += "," + item[3] + "</span><span class='desc'>";
    if (item[4] !== '-') appendString += "," + item[4];
    appendString += "</span></div>";

    return $("<li class='each'>").append(appendString).appendTo(ul);
};

// Attach focus separately — outside the autocomplete setup
$('#DitemModel').focus(function () {
    if (this.value === "") {
        $(this).autocomplete("search");
    }
});

//Starting for Warehouse auto complete in poq table	
$('#fromSiteId').autocomplete({
    source: function (request, response) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: ctx + '/GetAllWarehouse',
            data: {
                warehouseName: request.term
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function () {
                alert("Error");
            }
        });
    },
    minLength: 0,
    maxShowItems: 40,
    scroll: true,
    select: function (event, ui) {
        this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] + ":" + ui.item[2] : '');
        return false;
    }
}).autocomplete("instance")._renderItem = function (ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

// Focus handler outside the autocomplete setup
$('#fromSiteId').focus(function () {
    if (this.value === "") {
        $(this).autocomplete("search");
    }
});


/////////////end autocpmplete for warhouse	

			//Starting for Warehouse auto complete in poq table
$('#toSiteId').autocomplete({
    source: function (request, response) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: ctx + '/GetAllWarehouse',
            data: {
                warehouseName: request.term
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function () {
                alert("Error");
            }
        });
    },
    minLength: 0,
    maxShowItems: 40,
    scroll: true,
    select: function (event, ui) {
        this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] + ":" + ui.item[2] : '');
        return false;
    }
}).autocomplete("instance")._renderItem = function (ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

// Focus handler separately:
$('#toSiteId').focus(function () {
    if (this.value === "") {
        $(this).autocomplete("search");
    }
});


$('#farId').autocomplete({
    source: function (request, response) {
        if ($('#farId').prop('readonly')) {
            return;
        }

        var RowIndex = $('#RowIndex').val();
        var fromNodeInput = $("#bisotab > tbody").find("tr").eq(RowIndex).find('td[name="fromNode"]').children('input').val();

        if (fromNodeInput === "null" || fromNodeInput === '{"fromNodeArray":[]}') {
            return;
        }

        if (!fromNodeInput) {
            return;
        }

        var data = JSON.parse(fromNodeInput);
        var nodeIds = data.fromNodeArray.map(function (node) {
            return node.NodeId;
        });

        console.log(nodeIds);

        var siteId = $("#bisotab > tbody").find("tr").eq(RowIndex).find('td[name="siteID"]').children('input').val();

        if (nodeIds.length === 0 || siteId === "") {
            return;
        }

        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: ctx + '/GetAllFarDn',
            data: {
                "nodeID[]": nodeIds,
                "siteId": siteId,
                "Far": request.term
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.globalList);
                }
            },
            error: function () {
                alert("Error");
            }
        });
    },
    minLength: 0,
    maxShowItems: 40,
    scroll: true,
    autoFocus: true,
    select: function (event, ui) {
        this.value = (ui.item ? ui.item[0] : '');
        return false;
    }
}).autocomplete("instance")._renderItem = function (ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

// Focus handler using ID, not $(el):
$('#farId').focus(function () {
    if (this.value === "") {
        $(this).autocomplete("search");
    }
});


		
$('#PoId').autocomplete({
    source: function (request, response) {
        var Item_code = $('#itmCode').val() || "empty";
        if (Item_code !== "empty" && Item_code.includes(":")) {
            Item_code = Item_code.split(":")[0];
        }

        var itemModel = $('#itemModel').val() || "empty";
        var itemPartNb = $('#itmPartNb').val() || "empty";
        var WorkOrder = $('#WorkOrder').val() || "empty";
        var WoOrderID = (WorkOrder !== "empty" && WorkOrder.includes(":")) ? WorkOrder.split(":")[0] : WorkOrder;

        console.log("WoOrderID is " + WoOrderID);
        console.log("itemPartNb is " + itemPartNb);

        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: ctx + '/GetAllPoByItem',
            data: {
                "PO": request.term,
                "Item_code": Item_code,
                "itemModel": itemModel,
                "WoOrderID": WoOrderID,
                "itemPartNb": itemPartNb
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
                    response(data.ListPos);
                }
            },
            error: function () {
                alert("Error");
            }
        });
    },
    minLength: 0,
    maxShowItems: 40,
    scroll: true,
    select: function (event, ui) {
        this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] + ":" + ui.item[2] + ":" + ui.item[3] : '');
        var PoID = ui.item[0];

        var parentRow = $(this).closest('tr');

        // Check if the row and input exist before continuing:
        var ItemInput = parentRow.find('td[name="item"]').find('input');
        if (ItemInput.length > 0) {
            var ItemCode = ItemInput.val() || "empty";
            if (ItemCode !== "empty" && ItemCode.includes(":")) {
                ItemCode = ItemCode.split(":")[0];
            }

            var qty = parentRow.find('td[name="itemQty"]').find('input');
            var rate = parentRow.find('td[name="itemRate"]').find('input');
            var discountAmount = parentRow.find('td[name="itemDAmout"]').find('input');
            var tax = parentRow.find('td[name="itemTax"]').find('input');
            var obj = parentRow.find('td[name="purchaseOrder"]').find('input');

            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetPOitemDetails',
                data: {
                    PoID: PoID,
                    ItemCode: ItemCode
                },
                dataType: "json",
                success: function (data) {
                    if (data && data.PoDetails && data.PoDetails.length > 0) {
                        qty.val(1);
                        rate.val(data.PoDetails[0][1]);
                        discountAmount.val(data.PoDetails[0][2]);
                        tax.val(data.PoDetails[0][3]);
                        SetCalc(obj);
                    }
                },
                error: function () {
                    alert("Error");
                }
            });
        }

        return false;
    }
}).autocomplete("instance")._renderItem = function (ul, item) {
    return $("<li class='each'>")
        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
            item[0] + "</span><br><span class='desc'>" +
            item[1] + ', ' + item[2] + "</span></div>")
        .appendTo(ul);
};

$('#PoId').focus(function () {
    if (this.value === "") {
        $(this).autocomplete("search");
    }
});

function checkToNode() {
    var Data = {}; // Initialize a data object
    Data.toNodeArray = []; // Array to hold valid rows' data

    // Iterate through each 'record' checkbox in the table
    $("#toNodes  > tbody > tr").find('input[name="record"]').each(function () {
        // Get the value of the NodeId input field
        var nodeIdInput = $(this).parent().parent().children('td[name="NodeToId"]').children('input');
        var nodeIdValue = nodeIdInput.val();

        // If NodeId is empty, remove the row
        if (nodeIdValue === '') {
            console.warn("Removing row with empty NodeId");
            $(this).parents("tr").remove();
        } else {
            // Extract values for NodeId, NodeName, and NodeType
            var node_Name = $(this).parent().parent().children('td[name="NodeToName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeToType"]').children('input').val();

            // Add the row's data to the toNodeArray
            Data.toNodeArray.push({ NodeId: nodeIdValue, NodeName: node_Name, NodeType: node_Type });
        }
    });

    console.log("Aggregated Data:", Data.toNodeArray); // Log the aggregated data
}
function getContextPath() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
function addRowToNode() {
    checkToNode();
    var rowcountNodes = $("#toNodes >tbody tr").length;// Count existing rows
    console.log("rowcountNode:" + rowcountNodes);
	var rowcountNode= rowcountNodes+1;
	console.log(rowcountNode);
    var ctx = getContextPath();

    // Dynamically create a new row
    var markup =
        "<tr><td style='text-align:center;'><input type='checkbox' name='record' id='record_" + rowcountNode + "'></td>" +
        "<td name='NodeId'>" +
        "<input name='NodeToId' class='form-control text-input' type='text' id='NodeToId_" + rowcountNode + "' style='width:100%;box-sizing:border-box;'/></td>" +
        "<td name='NodeName'>" +
        "<input name='NodeToName' id='NodeToName_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td>" +
        "<td name='NodeType'>" +
        "<input name='NodeToType' id='NodeToType_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

    $("#toNodes > tbody").append(markup);

    // NodeId autocomplete setup
    $('#NodeToId_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: { Node: request.term },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log("Error fetching autocomplete data");
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            this.value = ui.item ? ui.item[2] : '';
            document.getElementById("NodeToName_" + rowcountNode).value = ui.item[3];
            document.getElementById("NodeToType_" + rowcountNode).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = "<div class='acItem'><span class='desc'><b>" +
            item[2] + "</b></span><br><span class='name'>" +
            item[3] + ", " + item[4] + "</span></div>";

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    // NodeName autocomplete setup
    $('#NodeToName_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: { Node: request.term },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log("Error fetching autocomplete data");
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            this.value = ui.item ? ui.item[3] : '';
            document.getElementById("NodeToId_" + rowcountNode).value = ui.item[2];
            document.getElementById("NodeToType_" + rowcountNode).value = ui.item[4];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = "<div class='acItem'><span class='desc'><b>" +
            item[3] + "</b></span><br><span class='name'>" +
            item[2] + ", " + item[4] + "</span></div>";

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    // NodeType autocomplete setup
    $('#NodeToType_' + rowcountNode).autocomplete({
        source: function (request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: ctx + '/GetAllNode',
                data: { Node: request.term },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
                        response(data.ListNode);
                    }
                },
                error: function (result) {
                    console.log("Error fetching autocomplete data");
                }
            });
        },
        minLength: 0,
        select: function (event, ui) {
            this.value = ui.item ? ui.item[4] : '';
            document.getElementById("NodeToId_" + rowcountNode).value = ui.item[2];
            document.getElementById("NodeToName_" + rowcountNode).value = ui.item[3];
            return false;
        }
    }).autocomplete("instance")._renderItem = function (ul, item) {
        var appendString = "<div class='acItem'><span class='desc'><b>" +
            item[4] + "</b></span><br><span class='name'>" +
            item[2] + ", " + item[3] + "</span></div>";

        return $("<li class='each'>").append(appendString).appendTo(ul);
    };

    // Focus triggers autocomplete search
    ['NodeToId', 'NodeToName', 'NodeToType'].forEach(function (field) {
        $('#'+field+'_' + rowcountNode).focus(function () {
            if (this.value === "") {
                $(this).autocomplete("search");
            }
        });
    });

    // Aggregate the data for all rows
    var Data = { toNodeArray: [] };
    $("#toNodes  > tbody > tr").find('input[name="record"]').each(function () {
        var node_Id = $(this).parent().parent().children('td[name="NodeToId"]').children('input').val();
        var node_Name = $(this).parent().parent().children('td[name="NodeToName"]').children('input').val();
        var node_Type = $(this).parent().parent().children('td[name="NodeToType"]').children('input').val();

        Data.toNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
    });

    
}
function checkFromNode() {
    var Data = {};
    Data.fromNodeArray = [];

    $("#fromNodes > tbody > tr").find('input[name="record"]').each(function () {
        var ID = $(this).parent().parent().children('td[name="NodeId"]').children('input')[0].value;
        if (ID === '') {
            $(this).parents("tr").remove();
        } else {
            var node_Id = $(this).parent().parent().children('td[name="NodeFromId"]').children('input').val();
            var node_Name = $(this).parent().parent().children('td[name="NodeFromName"]').children('input').val();
            var node_Type = $(this).parent().parent().children('td[name="NodeFromType"]').children('input').val();

            Data.fromNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
        }
    });

}

function addRowFromNode() {
	checkFromNode();
		    var rowcountNodes = $("#fromNodes >tbody tr").length; // Count existing rows
		 	var rowcountNode= rowcountNodes+1;
		    var ctx = getContextPath();

		    // Dynamically create a new row
		    var markup =
		        "<tr><td style='text-align:center;'><input type='checkbox' name='record' id='record_" + rowcountNode + "'></td>" +
		        "<td name='NodeId'>" +
		        "<input name='NodeFromId' class='form-control text-input' type='text' id='NodeFromId_" + rowcountNode + "' style='width:100%;box-sizing:border-box;'/></td>" +
		        "<td name='NodeName'>" +
		        "<input name='NodeFromName' id='NodeFromName_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td>" +
		        "<td name='NodeType'>" +
		        "<input name='NodeFromType' id='NodeFromType_" + rowcountNode + "' style='width:100%;box-sizing:border-box;' type='text' class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td></tr>";

		    $("#fromNodes > tbody").append(markup);

		    // NodeId autocomplete setup
		    $('#NodeFromId_' + rowcountNode).autocomplete({
		        source: function (request, response) {
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		                url: ctx + '/GetAllNode',
		                data: { Node: request.term },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.ListNode);
		                    }
		                },
		                error: function (result) {
		                    console.log("Error fetching autocomplete data");
		                }
		            });
		        },
		        minLength: 0,
		        select: function (event, ui) {
		            this.value = ui.item ? ui.item[2] : '';
		            document.getElementById("NodeFromName_" + rowcountNode).value = ui.item[3];
		            document.getElementById("NodeFromType_" + rowcountNode).value = ui.item[4];
		            return false;
		        }
		    }).autocomplete("instance")._renderItem = function (ul, item) {
		        var appendString = "<div class='acItem'><span class='desc'><b>" +
		            item[2] + "</b></span><br><span class='name'>" +
		            item[3] + ", " + item[4] + "</span></div>";

		        return $("<li class='each'>").append(appendString).appendTo(ul);
		    };

		    // NodeName autocomplete setup
		    $('#NodeFromName_' + rowcountNode).autocomplete({
		        source: function (request, response) {
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		                url: ctx + '/GetAllNode',
		                data: { Node: request.term },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.ListNode);
		                    }
		                },
		                error: function (result) {
		                    console.log("Error fetching autocomplete data");
		                }
		            });
		        },
		        minLength: 0,
		        select: function (event, ui) {
		            this.value = ui.item ? ui.item[3] : '';
		            document.getElementById("NodeFromId_" + rowcountNode).value = ui.item[2];
		            document.getElementById("NodeFromType_" + rowcountNode).value = ui.item[4];
		            return false;
		        }
		    }).autocomplete("instance")._renderItem = function (ul, item) {
		        var appendString = "<div class='acItem'><span class='desc'><b>" +
		            item[3] + "</b></span><br><span class='name'>" +
		            item[2] + ", " + item[4] + "</span></div>";

		        return $("<li class='each'>").append(appendString).appendTo(ul);
		    };

		    // NodeType autocomplete setup
		    $('#NodeFromType_' + rowcountNode).autocomplete({
		        source: function (request, response) {
		            $.ajax({
		                type: "GET",
		                contentType: "application/json; charset=utf-8",
		                url: ctx + '/GetAllNode',
		                data: { Node: request.term },
		                dataType: "json",
		                success: function (data) {
		                    if (data != null) {
		                        response(data.ListNode);
		                    }
		                },
		                error: function (result) {
		                    console.log("Error fetching autocomplete data");
		                }
		            });
		        },
		        minLength: 0,
		        select: function (event, ui) {
		            this.value = ui.item ? ui.item[4] : '';
		            document.getElementById("NodeFromId_" + rowcountNode).value = ui.item[2];
		            document.getElementById("NodeFromName_" + rowcountNode).value = ui.item[3];
		            return false;
		        }
		    }).autocomplete("instance")._renderItem = function (ul, item) {
		        var appendString = "<div class='acItem'><span class='desc'><b>" +
		            item[4] + "</b></span><br><span class='name'>" +
		            item[2] + ", " + item[3] + "</span></div>";

		        return $("<li class='each'>").append(appendString).appendTo(ul);
		    };

		    // Focus triggers autocomplete search
		    ['NodeFromId', 'NodeFromName', 'NodeFromType'].forEach(function (field) {
		        $('#'+field+'_' + rowcountNode).focus(function () {
		            if (this.value === "") {
		                $(this).autocomplete("search");
		            }
		        });
		    });

		    // Aggregate the data for all rows
		    var Data = { toNodeArray: [] };
		    $("#fromNodes > tbody > tr").find('input[name="record"]').each(function () {
		        var node_Id = $(this).parent().parent().children('td[name="NodeFromId"]').children('input').val();
		        var node_Name = $(this).parent().parent().children('td[name="NodeFromName"]').children('input').val();
		        var node_Type = $(this).parent().parent().children('td[name="NodeFromType"]').children('input').val();

		        Data.toNodeArray.push({ NodeId: node_Id, NodeName: node_Name, NodeType: node_Type });
		    });

		    
		}
		//zeee
$(".delete-To-node").click(function () {
   // Array to store selected Node IDs for deletion
    $("#toNodes > tbody").find('input[name="record"]').each(function () {
		
        if ($(this).is(":checked")) {
			const row = $(this).closest("tr");
			     const nodeId = row.find('input[name="NodeToId"]').val();
            // Get the NodeId value from the row and add it to slctDel
             slctToNodeDel.push(nodeId);
            console.log("The selecteds delete is " + slctToNodeDel);

            // Add to allDelSerials if not already included
           
        }
    });

    console.log("The selected delete after is " + slctToNodeDel);

    // Check if no rows were selected
    if (slctToNodeDel.length === 0) {
        alert('Select Row(s) to Delete');
        return false;
    }

    // Remove selected rows from the table
    $("#toNodes > tbody").find('input[name="record"]').each(function () {
        if ($(this).is(":checked")) {
            $(this).parents("tr").remove();
        }
    });
});

// Delete selected rows from the "From Nodes" table
$(".delete-From-node").click(function () {
    $("#fromNodes > tbody").find('input[name="record"]').each(function () {
        if ($(this).is(":checked")) {
        	const row = $(this).closest("tr");
    		
            // Get the NodeId value from the row and add it to slctDel
                const nodeId = row.find('input[name="NodeFromId"]').val();
                slctFromNodeDel.push(nodeId);
            console.log("The selected delete is " + slctFromNodeDel);

            // Add to allDelSerials if not already included
           
        }
    });

    console.log("The selected delete after is " + slctFromNodeDel);

    // Check if no rows were selected
    if (slctFromNodeDel.length === 0) {
        alert('Select Row(s) to Delete');
        return false;
    }

    // Remove selected rows from the table
    $("#fromNodes > tbody").find('input[name="record"]').each(function () {
        if ($(this).is(":checked")) {
            $(this).parents("tr").remove();
        }
    });
});
function toggleSelectAllToNodes() {
    $("#toNodes > tbody").find('input[name="record"]').each(function () {
		if ($(this).hasClass('allChecked')) {
			$('input[type="checkbox"]', '#toNodes').prop('checked', false);
		} 
		else {
			$('input[type="checkbox"]', '#toNodes').prop('checked', true);
		}

		$(this).toggleClass('allChecked');
});
}

function toggleSelectAllFromNodes() {
    $("#fromNodes > tbody").find('input[name="record"]').each(function () {
		if ($(this).hasClass('allChecked')) {
			$('input[type="checkbox"]', '#fromNodes').prop('checked', false);
		} 
		else {
			$('input[type="checkbox"]', '#fromNodes').prop('checked', true);
		}

		$(this).toggleClass('allChecked');
    });
}

	  


		

$("#selectnav").autocomplete({
		
	    source: function(request, response) {
		    
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllPendingDN',
	                 data: {
							"ID" : $("#selectnav").val()
					
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListPending);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 40, scroll:true,

				select: function(event, ui) {
					
					this.value = (ui.item ? ui.item[2] + ":" + ui.item[0] : '');
					var param ="${pageContext.request.contextPath}/DiscoveryNewApprovalFormView?dniID="+ui.item[0]+"&NavAction=2&status="+ui.item[4]+"";
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







function saveForm(){
    // Collect simple input fields
	var action =document.getElementById("actionButtonText").textContent;

																
	var numericFields = [
	    { id: 'Qty', label: 'Qty' },
	    { id: 'rate', label: 'Rate' },
	    { id: 'discountAmount', label: 'Discount Amount' },
	    { id: 'tax', label: 'Tax' },
	    { id: 'netRate', label: 'Net Rate' },
	    { id: 'totalAt', label: 'Total Amount' }
	];

	for (var i = 0; i < numericFields.length; i++) {
	    var field = numericFields[i];
	    var val = $('#' + field.id).val().trim();

	    // Log the current field being checked
	    console.log(`Validating field: ${field.label}, value: "${val}"`);

	    if (val === "" || isNaN(val)) {
	        alert(field.label + " must be a valid number and cannot be empty.");
	        return;
	    }
	       
	}
	var poId = $('#PoId').val().trim();
	var fromSiteId = $('#fromSiteId').val().trim();
    var toSiteId = $('#toSiteId').val().trim();
   
	if (poId === "") {
	    alert("PO ID cannot be empty.");
	    return;
	}
	if (poId === "") {
	    alert("PO ID cannot be empty.");
	    return;
	}

	if (fromSiteId === "") {
	    alert("From Site ID cannot be empty.");
	    return;
	}

	if (toSiteId === "") {
	    alert("To Site ID cannot be empty.");
	    return;
	}
    // Collect 'toNodes' table data
	// For 'toNodes'
	var toNodes = [];
	$('#toNodes tbody tr').each(function () {
	    var nodeId = $(this).find('input[name="NodeToId"]').val();
	    var nodeName = $(this).find('input[name="NodeToName"]').val();
	    var nodeType = $(this).find('input[name="NodeToType"]').val();
	    // Only add if at least one field is present
	    if (nodeId || nodeName || nodeType) {
	        toNodes.push([nodeId, nodeName, nodeType]);
	    }
	});

	// For 'fromNodes'
	var fromNodes = [];
	$('#fromNodes tbody tr').each(function () {
	    var nodeId = $(this).find('input[name="NodeFromId"]').val();
	    var nodeName = $(this).find('input[name="NodeFromName"]').val();
	    var nodeType = $(this).find('input[name="NodeFromType"]').val();
	    if (nodeId || nodeName || nodeType) {
	        fromNodes.push([nodeId, nodeName, nodeType]);
	    }
	});
  

    // Get context path (ensure this function is defined elsewhere in your code)
    var ctx = getContextPath();

    // Send AJAX request
    $.ajax({
        type: "POST",
       
        url: ctx + '/SaveFormViewDN',
        dataType: "json",
        data: {
			        itemId: $('#itemId').val(),
			        itemName: $('#itemName').val(),
			        DitemModel: $('#DitemModel').val(),
			        DitemPartNumber: $('#DitemPartNumber').val(),
			        popupTransType: $('#popupTransType').val(),
			        description: $('#description').val(),
			        popupElementName: $('#popupElementName').val(),
			        address: $('#address').val(),
			        PoId: $('#PoId').val(),
			        WoId: $('#WoId').val(),
			        fromSlotId: $('#fromSlotId').val(),
			        toSlotId: $('#toSlotId').val(),
			        fromSiteId: $('#fromSiteId').val(),
			        toSiteId: $('#toSiteId').val(),
			        farId: $('#farId').val(),
			        macAddress: $('#macAddress').val(),
			        Qty: $('#Qty').val(),
					getApproval: $('#approvalPen').val(),
			        rate: $('#rate').val(),
			        discountAmount: $('#discountAmount').val(),
			        tax: $('#tax').val(),
			        netRate: $('#netRate').val(),
			        totalAt: $('#totalAt').val(),
			        oldSerialNum: $('#oldSerialNum').val(),
			        serialNum: $('#serialNum').val(),
			        transId: $('#transId').val(),
			        DnItemId: $('#DnItemId').val(),
			        DnItemNotes: $('#DnItemNotes').val(),
					toNodes: JSON.stringify(toNodes),
					fromNodes: JSON.stringify(fromNodes),
					slctFromNodeDel : JSON.stringify(slctFromNodeDel),
					slctToNodeDel : JSON.stringify(slctToNodeDel),
				    action : action},
                  success: function (response) {
                      console.log(response);
                      if (((response.status === "Finance" || response.status === "Operation Manager") && response.action === "Approved") 
                    		    || (response.action === "Rejected")) {
                    		    
                    		    alert("Action completely done for " + response.DniID);
                    		    var param = "${pageContext.request.contextPath}/DiscoveryNewApprovalListView";

                    		} else if (response.read === "0") {  
                    		    alert("Action done for " + response.DniID);
                    		    var param = "${pageContext.request.contextPath}/DiscoveryNewApprovalListView";

                    		} else {
                    		    var param = "${pageContext.request.contextPath}/DiscoveryNewApprovalFormView?dniID=" + response.DniID + "&NavAction=2&status=" + response.status;
                    		    unsaved = false;
                    		}
						 
				window.location.href =param;
											
					
          
		
			
        },
        error: function (xhr, status, error) {
            alert("Save failed: " + error);
        }
    });
};


var toNodeArrayStr = '${toNodeJson}';
var fromNodeArrayStr = '${fromNodeJson}';



populateNodesTable(fromNodeArrayStr, toNodeArrayStr);
var unsaved = false;				 
var FormSave=new Array();
$(window).bind('beforeunload', function() {
    if(unsaved){
        return "You have unsaved changes. Do you want to leave and discard?";

																	
											
																							  
																 
    }
});
if ('${docStatus}' == "addNew") 
{
	  // set status to new and green while new record
       $("#formStatus").text("New");
			$('.dot').css({"background-color" : "orange"});
			FormSave.push("New");	
			$(".nextprvItems").addClass("hide-row");
			$(".pad").removeClass("hide-row");
																									 
		   		
}
$(document).on('change', ':input', function(){
    unsaved = true;
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});

var read= '${read}';
var write= '${write}';
var save= '${save}';
var add= '${add}';
var del= '${delete}';

$(document).ready(function() {
 // get server-side value

var approveReject= '${approveReject}';
    if (write == "0") {
    	console.log("write=0 → disable fields except view + action buttons");

        $('input:not([type=hidden]), textarea, select, button')
            .not('#Fview, #Lview, #notifactionDropdown, #saveButton, #addButton, #delButton, #selectnav, [title="Tree View"]')
            .prop('disabled', true);

        // explicitly enable these, in case some were disabled by default
        $('#Fview, #Lview, #notifactionDropdown, #saveButton, #addButton, #delButton').prop('disabled', false);

    } else {
        console.log("write!=0 → enable all fields");
        $('input:not([type=hidden]), textarea, select, button').prop('disabled', false);
    }

    if (save == "0") {
    	
        $('#saveButton').prop('disabled', true);

        // explicitly enable these, in case some were disabled by default
      

    } else {
         $('#saveButton').prop('disabled', false);
    }

    if (add == "0") {
    
        $('#addButton').prop('disabled', true);

        // explicitly enable these, in case some were disabled by default
      

    } else {
        $('#addButton').prop('disabled', false);
    }
    if (del == "0") {
    	
        $('#deleteButton').prop('disabled', true);

        // explicitly enable these, in case some were disabled by default
      

    } else {
         $('#deleteButton').prop('disabled', false);
    }
    if (approveReject == "0") {
    	
        $('#notifactionDropdown').prop('disabled', true);

        // explicitly enable these, in case some were disabled by default
      

    } else {
         $('#notifactionDropdown').prop('disabled', false);
    }


    
    if('${SelectedIndex}' != "addNew"){
		var SelectedIndex = ${SelectedIndex};
		if('${dnCount}' != "addNew"){

			
	var dnCount = ${dnCount};
	
	if(($("#DnItemId").val()) != "" && ($("#DnItemId").val()) != null){

	if(SelectedIndex === dnCount){
		
		document.getElementById("btnLast").style.opacity = 0.5;
		$("#btnLast").hasClass("disabled");
		document.getElementById("btnLast").style.pointerEvents = "none";
		
		document.getElementById("btnNexta").style.opacity = 0.5;
		document.getElementById("btnNexta").style.pointerEvents = "none";

		
		$("#btnNexta").hasClass("disabled");
		
		}else{
																																   
			
			if(!$("#btnNexta").hasClass("disabled")){
																												  
																										
																													
																												  
																													
																										
																										
																										
																										 
																												   
																																												   
									  
				
				$("#btnNext").click(function(){
										  
					
					var param ="${pageContext.request.contextPath}/DiscoveryNewApprovalFormView?dniID="+$("#DnItemId").val()+"&NavAction=1";
										   
					 
			   
			   
				  
					   
					   
												 

					window.location.href =param;
		
				});
											 
	
					  
										   
											
								 
									
							 
			 
			  
	  
							 
			}
			if(!$("#btnLst").hasClass("disabled")){
				
				$("#btnLst").click(function(){
					
					var param ="${pageContext.request.contextPath}/DiscoveryNewApprovalFormView?dniID="+$("#DnItemId").val()+"&NavAction=4";
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
		
		var param ="${pageContext.request.contextPath}/DiscoveryNewApprovalFormView?dniID="+$("#DnItemId").val()+"&NavAction=0";
		window.location.href =param;
		
	 });
}
$("#btnFrst").click(function(){

	if(!$("#btnFrst").hasClass("disabled")){
			
		var param ="${pageContext.request.contextPath}/DiscoveryNewApprovalFormView?dniID="+$("#DnItemId").val()+"&NavAction=3";
		window.location.href =param;
				
		}
		 });

}
	
	}}
}
	$("#label-1").text((SelectedIndex)+"/"+dnCount);




			

		   
											   
									 
								   
							
	 
			 
	 
					   
	
	$(document).trigger("triggerPageListenersEvent");		

});
function selectAction(action) {
	  // Change button text
	  document.getElementById("actionButtonText").textContent = action;

	 
	}
  
	
	

</script>
 

	
 </html>
 
 

