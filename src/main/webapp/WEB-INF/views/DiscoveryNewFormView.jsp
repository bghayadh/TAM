<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>DN Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@ include file="scripts.html" %>		 
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
   <script src="${pageContext.request.contextPath}/resources/js/DiscoveryNew/DN_BoqPopup.js"></script>
    
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
    
	.ui-widget {
    font-family: Arial,Helvetica,sans-serif;
     font-size: 1rem;
		}
			
			
	.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					z-index:9999;
					width: 250px;
	        		}
	
	 .hide-row { display:none; }
	
	li.nav-item span.active svg {
    color: #FFD966 !important;
}
	li.nav-item a svg
	{
		color:gold !important;
	}
	select
	{
		width: 150px;
		height: 25px;
		border-collapse: collapse;
		cursor: pointer;
	}  
	.case
	{
	text-align:center;
	font-size: 16px;
	color:#0000FF;
	position:relative;
	top:7px;
	}
	#testing
	{
	text-align:center;
	font-size: 16px;
	color:#0000FF;
	position:relative;
	padding:7px;
	}
	.btnApproval
	{
		cursor: default !important;
		width: 265px !important;
		font-size: 14px;
	}  
	
	.btn-primary:hover
	{
		background-color: #007bff !important;
	} 
	
	#ProjectManagerApprove, #AssetUnitApprove, #FinanceApprove 
	{
		cursor: pointer;
	} 
	
	.transType
	{
		width:330px !important;
		
		}		
	
	.NotesInput
	{
		width: 400px !important;
	} 
	
	#fixed-headerr{
	opacity: 1;
	filter: alpha(opacity=100);
 	background: red;
  	position: sticky;
  	top: 0;

	}
	.fixed-headerr{
	opacity: 1;
	filter: alpha(opacity=100);
 	background: #ebf2ef;
  	position: sticky;
  	top: 0;
  	z-index: 15;
	}
	.elementName
	{
		width: 100% !important;
	}
	
	.inputWidth
	{
		width:100px !important;
	}
	
	#discountAmount
	{
		width: 126px !important;
	}
	
		/***************/
	.custom-class-assignedto-modal .modal-body {
  height: 500px;
  overflow : auto;
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

  <c:set var="pg" value="inventory" scope="session"  />
<jsp:include page="header.jsp"></jsp:include>
	
	
	 <p></p>
<div class="container-fluid">
		<div class="row">
			
		
			<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span style="width:200px;"class="input-group-text">DN ID</span>
							<input type="text" readonly id="dncode"  value="${dnID}" class="form-control text-input"   />
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
						</div>
					</div>
			</div>
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:200px;" class="input-group-text">Status</span>
				<input type="text" id="dnstat" value="${dnStatus}" class="form-control text-input" />
				</div>							
			</div>
			  <div class="pad col-md-3 hide-row"></div>    
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span style="width:200px;" class="input-group-text">Other DN</span>
						<input type="text" id="selectnav" value="${selectnav}"
						style="width: 430px" class="form-control text-input" />					</div>
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
					<input type="text" id="dncreatedate" readonly value="${dncreationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span style="width:200px;" class="input-group-text">Last Modify Date</span>
					<input type="text" id="dnlstmodifdate" readonly value="${dnlastModifieddate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
						</div>
				</div>
		</div>
	</div>
	
		  <div class="hide-row col-md-3 pad "></div>
		
		<div class=" col-md-3 nextprvItems ">
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
							class="btn btn-success next">&raquo;</a></li>			  		</ul>
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
			        			onclick='window.location.href = "${pageContext.request.contextPath}/DiscoveryNewListView"'
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
	
	

<div class="container-fluid">

<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: -20px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
      
            <li class="nav-item ml-auto">
            	
            	<div class="dropdown" Style="display:inline-block;">
	           		<button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	                 aria-haspopup="true" aria-expanded="false">Actions</button>
	
	            	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    	          		<a class="dropdown-item" id="ProjectManagerApprove">Project Manager Approval</a>
    	          		<a class="dropdown-item" id="AssetUnitApprove">Asset Unit Approval</a>
    	          		<a class="dropdown-item" id="FinanceApprove">Finance Approval</a>
    	        	</div>         
    	        		<button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
              
            
    	        </div>	
               
				<button type="button" id="deleteButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-trash"></i> Delete
				</button>  
                        
				<button type="button" id="saveButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-save"></i> Save
				</button>  </li>
							
     </ul>
     
</div>
</div>

</div>

<div class="container-fluid">

<p></p>
    
     

	    	<!--  create table DiscoveryNewItem    -->
		<div> 
				<form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="bisotab" class="table table-striped table-bordered table-sm " style="display:block; height:400px; overflow-y: auto;">
						        <thead>
						            <tr class="fixed-headerr" >
						                <th>
								         <div style="width:80px" class="fixed-headerr"><button type="button" id="selectAll" class="fixed-headerr" >
								          <span class="sub"></span>Select</button></div></th>
						                <th>Item</th>
						                <th>Item Model</th>
						                <th>Item Part Number</th>
						                <th><div style="width:280px">Transaction Type</div></th>
						                <th>Notes</th>
						                <th>Description</th>
						                <th><div style="width:200px">Element Name</div></th>
						                <th>Address</th>
						                <th><div style="width:280px">Approval Status</div></th>
						                <th><div style="width:200px">Approval Action</div></th>
						                <th>PO</th>
						                <th>WO</th>
						                <th>From Node ID</th>
						                <th>From Node Name</th>
						                <th>To Node ID</th>
						                <th>To Node Name</th>
						                <th>From Slot</th>
						                <th>To Slot</th>
						                <th>Qty</th>
						                <th>Rate</th>
						                <th><div id="discountAmount">Discount Amount</div></th>
						                <th>Tax</th>
						                <th>Net Rate</th>
						                <th>Total</th>
						                <th>Total AT</th>
						                <th>From Site</th>
						                <th>To Site</th>
						                <th>From SN</th>
						                <th>To SN</th>
						                <th>DNitmID</th>
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
					    <!--  Text used to indicate row index -->
					    <input type="text" id="RowIndex" value="" hidden>
						<input type="button"  onclick="addrows('addRow')" class="add-row" value="Add Row">
					    <button type="button"  onclick="deleterowsDN()" class="delete-row">Delete Row</button>
                   </form>
		</div>


<p></p>



	<div class="row">

		<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span style="width:200px;" class="input-group-text">Total Amount</span>
							<input type="text" id="dntotamnt" value="${dnTotalAmount}" readonly class="form-control text-input" />
						</div>
					</div>
		</div>
	
       <div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:200px;" class="input-group-text">Total QTY</span>
				<input type="text" id="dntotqty" value="${dnTotalQty}" readonly class="form-control text-input" />
				</div>							
		</div>
	</div>


</div>
  
  
   <!-- popup -->
     
     <div class="container">
	<div id ="DNModal" class="modal fade  custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #FF4F4F;" >
				<h5 id ="popupNb" class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;"></h5>
				
				<button type="button" name="insertBelow"  onclick="insertRowBelow()" class ="btn btn-default btn-primary BtnActive  " style="color:white;position:relative;left:50px;">Insert Below </button>
				<button type="button" name="insertAbove"  onclick="insertRowAbove()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:60px;">Insert Above </button>
			    <button type="button" name="deleteBoqRow"  onclick="deleteBoqRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:70px;">Delete</button>
			    <button name ="previousRow" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:80px;">Previous</button>
	            <button name="nextRow" onclick="nextRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:90px;">Next</button>
	            
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
   					<input type="text" id="popupItemCode" value="" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Item Model</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupItemModel" value="" style="width:675px; height:37px;"  />					
				</div>
			</div>
		</div></div></div>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Part No</span>
					<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupItemPartno" value="" style="width:674px; height:37px"  />
				</div>
			</div>
		</div>
<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Transaction type</span>
					<!--  <input type="text" id="popupTransType" class="form-control text-input" value="" style="width:215px; height:39px" /> -->
					<select class='form-control' id="popupTransType" onchange = 'getAllTypePopUp(this)'> 
					<option  value = '-- Select Option --' selected = "selected" >-- Select Option --</option>
					<option value = 'New Node'>New Node</option>
					<option value = 'New Node on New Hardware'>New Node on New Hardware</option>
					<option value = 'New Node on Existed Hardware'>New Node on Existed Hardware</option>
					<option value = 'New Hardware on New Node'>New Hardware on New Node</option>
					<option value = 'New Hardware on Existed Node'>New Hardware on Existed Node</option>
					<option value = 'Existed Hardware on New Node'>Existed Hardware on New Node</option>
					<option value = 'Transfer'>Transfer</option>
					<option value = 'Transfer from Slot to Slot'>Transfer from Slot to Slot</option>
					<option value = 'Transfer from Node to Node'>Transfer from Node to Node</option>
					<option value = 'Transfer from Site to Site'>Transfer from Site to Site</option>
					<option value = 'Disappear'>Disappear</option>
					<option value = 'Disappear for Maintenance'>Disappear for Maintenance</option>
					<option value = 'Disappear for Transfer'>Disappear for Transfer</option>
					<option value = 'Maintenance'>Maintenance</option>
					<option value = 'Replacement'>Replacement</option>
					<option value = 'Retirement'>Retirement</option>
					</select>
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
					<span class="input-group-text" >Notes</span>
					<input type="text" id="popupNotes" class="form-control text-input" value=""  style="width:674px; height:37px;" />
						
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Description</span>
					<input type="text" id="popupDescription" class="form-control text-input" value="" style="width:675px; height:37px;"  />  
						
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
					<span class="input-group-text" >Element Name</span>
					<!--  <input type="text" id="popupElementName" class="form-control text-input" value="" style="width:675px;"  />-->
					<select class='form-control' id="popupElementName" > 
					<option value = '-- Select Option --'>-- Select Option --</option>
					<option value = 'Cabinet'>Cabinet</option>
					<option value = 'Subrack'>Subrack</option>
					<option value = 'Slot'>Slot</option>
					<option value = 'Board'>Board</option>
					<option value = 'Port'>Port</option>
					<option value = 'Antenna'>Antenna</option>
					</select>
				</div>
			</div>
		</div>
				
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Address</span>
					<input type="text" id="popupAddress" class="form-control text-input"  value=""  style="width:675px;" />
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
					<span class="input-group-text" >Approval Status</span>
					  
					<p class='case' id = "case" style = "margin-left: auto; font-size: 13px; width:690px;">Transaction Type is Required</p>
				</div>
			</div>
	    </div>					      

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Approval Action</span>
				    <!-- <input type="text" id="popupApprovalAction" class="form-control text-input"  value="" style="width:675px;"   /> -->
				      <input type="text" id="popupApprovalStatus" class="form-control text-input" hidden  value=""  style="width:250px; text-align: center;"  />
				    	 <div id="popupApprovalAction" style="width:675px;"></div>
				    <!-- <select class='form-control'  id="popupApprovalAction" onclick = 'ChangeApprovePopUp(this)'> 
					<option value = '-- Select Option --'>-- Select Option --</option>
					<option value = 'Approved'>Approved</option>
					<option value = 'Not Approved'>Not Approved</option>
					<option value = 'Rejected'>Rejected</option>
					</select> -->
					  
				</div>
			</div>
	    </div>
	</div></div>
	
<div class="container-fluid">
<div class="row">
	<div class="col-sm-6">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text" >PO</span>
				<input type="text" id="popupPO" class="form-control text-input"  value="" style="width:674px; height:37px;" />
			 </div>
		</div> 
	</div>

	<div class="col-sm-6">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">WO</span>
				<input type="text" id="popupWO" class="form-control text-input"  value="" style="width:675px;"   />
			</div>
		 </div>
	</div>
	</div>
	</div>
		<div class="container-fluid">
				<div class="row">				     
	<div class="col-6">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text" >From Node ID</span>
				<input type="text" id="popupFromNodeID" class="form-control text-input" value=""  style="width:675px; height:37px;" />		
			 </div>
		</div>
	 </div>

	<div class="col-6">
		<div class="form-group">
			<div class="input-group-prepend" style="width:auto;">
				<span class="input-group-text">From Node Name</span>
				<input type="text" id="popupFromNodeName" class="form-control text-input"  value=""    />		
			</div>
		</div>
	</div> 
</div> </div>
								      

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >To Node ID</span>
					<input type="text" id="popupToNodeID" class="form-control text-input"  value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >To Node Name</span>
					<input type="text" id="popupToNodeName" class="form-control text-input"  value="" style="width:675px;"  />
					<input type="text" id="popupAlcFlg"  class="form-control text-input"  value="" style="width:675px;" hidden />
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
					<span class="input-group-text" >From Slot</span>
					<input type="text" id="popupFromSlot" class="form-control text-input"  value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >To Slot</span>
					<input type="text" id="popupToSlot" class="form-control text-input"  value="" style="width:675px;"  />
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
					<span class="input-group-text" >Qty</span>
					<input type="text" id="popupQty" class="form-control text-input"  value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Rate</span>
					<input type="text" id="popupRate" class="form-control text-input"  value="" style="width:675px;"  />
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
					<span class="input-group-text" >Discount Amount</span>
					<input type="text" id="popupDiscountAmount" class="form-control text-input"  value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Tax</span>
					<input type="text" id="popupTax" class="form-control text-input"  value="" style="width:675px;"  />
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
					<span class="input-group-text" >Net Rate</span>
					<input type="text" id="popupNetRate" class="form-control text-input" readonly value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Total</span>
					<input type="text" id="popupTotal" class="form-control text-input" readonly value="" style="width:675px;"  />
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
					<span class="input-group-text" >Total AT</span>
					<input type="text" id="popupTotalAT" class="form-control text-input" readonly value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class=" input-group-text" >From Site</span>
					<input type="text" id="popupFromSite" class="ui-widget ui-widget-content ui-corner-all form-control text-input"  value="" style="width:675px;"  />
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
					<span class="input-group-text" >To Site</span>
					<input type="text" id="popupToSite" class="form-control text-input"  value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >From SN</span>
					<input type="text" id="popupFromSN" class="form-control text-input"  value="" style="width:675px;"  />
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
					<span class="input-group-text" >To SN</span>
					<input type="text" id="popupToSN" class="form-control text-input"  value="" style="width:675px;"  />
				</div>
			</div>
	    </div>
	
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >DNitmID</span>
					<input type="text" id="popupDNitmID" class="form-control text-input" readonly value="" style="width:675px;"  />
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
		 
		 <!-- popup -->
     
 
          <div id="popUpDiv" style="display:none;">
  <div class="email" style="margin-top:50px;" >
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
						<span class="input-group-text"> Subject</span>
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
 
 
 
 <script type='text/javascript'>


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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
function getAllitemCode()
{
	ItemArray = [];
	var itemValue = "";
	$("input[name=itmCode]").each(function(){
		if($(this).val() == "")
			itemValue = "empty";
		else
			itemValue = $(this).val();
		ItemArray.push(itemValue);
	});
	return ItemArray;   
}

function getAllPurchaseOrders()
{
	PoArray = [];
	var PoValue = "";
	$("input[name=itmPO]").each(function(){
		if($(this).val() == "")
			PoValue = "empty";
		else
			PoValue = $(this).val();
		PoArray.push(PoValue);
	});
	return PoArray;   
}



function getAllWorkOrders()
{
	woArray = [];
	var WoValue = "";
	$("input[name=itmWO]").each(function(){
		if($(this).val() == "")
			WoValue = "empty";
		else
			WoValue = $(this).val();
		woArray.push(WoValue);
	});
	return woArray;   
}

function getAllItemModels()
{
	ItemModelArray = [];
	var ItemModValue = "";
	$("input[name=itmMod]").each(function(){
		if($(this).val() == "")
			ItemModValue = "empty";
		else
			ItemModValue = $(this).val();
		ItemModelArray.push(ItemModValue);
	});
	return ItemModelArray;   
}

function getAllItemPartNbs()
{
	ItemPartNbArray = [];
	var ItemPartNbValue = "";
	$("input[name=itmPartNb]").each(function(){
		if($(this).val() == "")
			ItemPartNbValue = "empty";
		else
			ItemPartNbValue = $(this).val();
		ItemPartNbArray.push(ItemPartNbValue);
	});
	return ItemPartNbArray;   
}
	
			$("input").change(function() {
				$("#dnstat").text("Not Saved");
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


			function getAllType(obj) {

                transType = $(obj).parent().parent().children('td[name="transType"]').children('select').val();
				tositeIDVal = $(obj).parent().parent().children('td[name="tositeID"]').children('input').val();
				PoValue = $(obj).parent().parent().children('td[name="purchaseOrder"]').children('input').val();
				ItemValue = $(obj).parent().parent().children('td[name="item"]').children('input').val(); 
				var aprov =$(obj).parent().parent().children('td[name="buttonApprove"]'); 
				var approvedby_inp = $(obj).parent().parent().children('td[name="approveStatus"]').children('input[name="approvedby"]');
				var buttonApprove; 
				

				if(transType == "")
				{
					aprov.html("<p class='case'>Transaction Type is Required</p>");
					approvedby_inp.val('');
				}
				if (transType == "New Node" || transType == "Transfer" || transType == "Disappear")
				{ 
					aprov.html("<p class='case'>Specify The Transaction Type</p>");
					approvedby_inp.val('');
			    }
			    if (transType == "New Node on New Hardware" || transType ==  "New Hardware on Existed Node" || transType =="Replacement" || transType == "New Hardware on New Node")
			    {
			    	aprov.html("<p class='case'>To be Approved By Project Manager</p>");
					approvedby_inp.val('Project Manager');    
                }
				if (transType == "New Node on Existed Hardware" || transType == "Transfer from Slot to Slot" || transType == "Transfer from Node to Node" || transType == "Transfer from Site to Site"
					|| transType == "Disappear for Maintenance" || transType == "Disappear for Transfer" || transType =="Maintenance" || transType =="Retirement")
				{
				   aprov.html("<p class='case'>To be Approved By Operation Manager</p>");
				   approvedby_inp.val('Operation Manager');        
                }

			}			

			function getAllTypePopUp(obj) {

              
                transType = $(obj).children("option:selected").val();
				var aprov =$("#case"); 
				var approvedby_inp = $("#popupApprovalStatus");
				var actionApprove = $("#popupApprovalAction");
				//var buttonApprove; 
								

				if(transType == "" || transType == "-- Select Option --")
				{
					//aprov.html("<p class='case'>Transaction Type is Required</p>");
					aprov.text( "Transaction Type is Required");
					approvedby_inp.val('');
					actionApprove.val('');
				}
				if (transType == "New Node" || transType == "Transfer" || transType == "Disappear")
				{ 
					aprov.text( "Specify The Transaction Type");
					approvedby_inp.val('');
					actionApprove.val('-- Select Option --');
			    }
			    if (transType == "New Node on New Hardware" || transType ==  "New Hardware on Existed Node" || transType =="Replacement" || transType == "New Hardware on New Node")
			    {
			    	aprov.text( "To be Approved By Project Manager");
					approvedby_inp.val('Project Manager');    
                }
				if (transType == "New Node on Existed Hardware" || transType == "Transfer from Slot to Slot" || transType == "Transfer from Node to Node" || transType == "Transfer from Site to Site"
					|| transType == "Disappear for Maintenance" || transType == "Disappear for Transfer" || transType =="Maintenance" || transType =="Retirement")
				{
				   aprov.text( "To be Approved By Operation Manager");
				   approvedby_inp.val('Operation Manager');        
                }

			}	
			
			/// change Approve ( used when adding new rows)
			function ChangeApprove(obj)
			{
				tositeIDVal = $(obj).parent().parent().children('td[name="tositeID"]').children('input').val();
				PoValue = $(obj).parent().parent().children('td[name="purchaseOrder"]').children('input').val();
				ItemValue = $(obj).parent().parent().children('td[name="item"]').children('input').val();
				transType = $(obj).parent().parent().children('td[name="transType"]').children('select').val();
				var approvedby = $(obj).parent().parent().children('td[name="approveStatus"]').children('input[name="approvedby"]').val();


				fromsiteIDVal = $(obj).parent().parent().children('td[name="siteID"]').children('input').val();
				fromNodeID = $(obj).parent().parent().children('td[name="itemNodeID"]').children('input').val();
				fromNodeName = $(obj).parent().parent().children('td[name="itemNodeName"]').children('input').val();
				toNodeID = $(obj).parent().parent().children('td[name="toNodeID"]').children('input').val();
				toNodeName = $(obj).parent().parent().children('td[name="itemToNodeName"]').children('input').val();
				fromSlot = $(obj).parent().parent().children('td[name="fromSlot"]').children('input').val();
				toSlot = $(obj).parent().parent().children('td[name="toSlot"]').children('input').val();

				
				//alert(transType);
				if(transType == "-- Select Option --")
				{
					$(obj).blur();
					alert('Transaction Type is Required');
					return false;
				}
				
                if (transType =="New Node" || transType =="Transfer" ||  transType =="Disappear")
    	            
				{ 
                	$(obj).blur();
					alert('Specify The Transaction Type');
					return false;
			    }

                                
				if((approvedby == "Project Manager" || approvedby == "Operation Manager") && (tositeIDVal == "")) {

					$(obj).blur();
				    alert('To Site ID cannot be null in Table at Row: ' +($(obj).parent().parent().index()+1));
				    return false;
				}
				
			   	
				if((approvedby == "Asset Unit") && (PoValue == "" || ItemValue == ""))
				{
					$(obj).blur();
					alert('Item code And Purchase Order cannot be null in Table at Row: ' +($(obj).parent().parent().index()+1));
				    return false;
				    
				}
				
				   if(transType == "Transfer from Slot to Slot" || transType == "Transfer from Node to Node" || transType == "Transfer from Site to Site")
	                 {
						var missingfields = "";

				if(fromSlot == "")
				{
					missingfields += " 'From Slot'";
				}
			if(toSlot == "")
			{
				missingfields += " 'toSlot'";
			}	
			if(toNodeID == "")
			{
				missingfields += " 'toNodeID'";
			}
			if(toNodeName == "")
			{
				missingfields += " 'toNodeName'";
			}
			if(fromNodeID == "")
			{
				missingfields += " 'fromNodeID'";
			}
			if(fromNodeName == "")
			{
				missingfields += " \'fromNodeName\'";
			}
			if(fromsiteIDVal == "")
			{
				missingfields += " 'fromsiteIDVal'";
			}
			if(tositeIDVal == "")
			{
				missingfields += " 'tositeIDVal'";
			}

			if(missingfields != "")
				{
					$(obj).blur();
					alert("Missing field(s): "+missingfields + "in Table at Row:  "+($(obj).parent().parent().index()+1));
					return false;
				}
			
    }

				
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
	
			}

			function ChangeApprovePopUp(obj)
			{
				
				//tositeIDVal = $(obj).parent().parent().children('td[name="tositeID"]').children('input').val();
				tositeIDVal = $("#popupToSite").val();
				
				//PoValue = $(obj).parent().parent().children('td[name="purchaseOrder"]').children('input').val();
				PoValue = $("#popupPO").val();
				//ItemValue = $(obj).parent().parent().children('td[name="item"]').children('input').val();
				ItemValue = $("#popupItemCode").val();
				//transType = $(obj).parent().parent().children('td[name="transType"]').children('select').val();
				transType = $("#popupTransType").children("option:selected").val();
				
				//var approvedby = $(obj).parent().parent().children('td[name="approveStatus"]').children('input[name="approvedby"]').val();
				//var approvedby = $(obj).children("option:selected").val();
				var approvedby = $("#popupApprovalStatus").val();
				

				fromsiteIDVal = $("#popupFromSite").val();
				fromNodeID = $("#popupFromNodeID").val();
				fromNodeName = $("#popupFromNodeName").val();
				toNodeID = $("#popupToNodeID").val();
				toNodeName = $("#popupToNodeName").val();
				fromSlot = $("#popupFromSlot").val();
				toSlot = $("#popupToSlot").val();
				
				//alert(transType);
				if(transType == "" || transType == "-- Select Option --" || transType == "undefined" || !transType) // not working!!!!!   || transType == "undefined"
				{
					$(obj).blur();
					alert('Transaction Type is Required');
					return false;
				}
				if(transType == "undefined") // not working!!!!!   || transType == "undefined"
				{
					$(obj).blur();
					alert('Transaction Type is Required');
					return false;
				}
				
				 if (transType =="New Node" || transType =="Transfer" ||  transType =="Disappear")
    	            
				{ 
					 $(obj).blur();
					alert('Specify The Transaction Type');
					return false;
			    }

                                
                 if((approvedby == "Project Manager" || approvedby == "Operation Manager") && (tositeIDVal == "")) {

                	 $(obj).blur();
				    alert('To Site ID cannot be null in Table at Row: ' +($(obj).parent().parent().index()+1));
				    return false;
				}
				
			   	
                if ((approvedby == "Asset Unit") && (PoValue == "" || ItemValue == ""))
				{
                	$(obj).blur();
					alert('Item code And Purchase Order cannot be null in Table at Row: ' +($(obj).parent().parent().index()+1));
				    return false;
				}

                if(transType == "Transfer from Slot to Slot" || transType == "Transfer from Node to Node" || transType == "Transfer from Site to Site")
                {
                    var missingfields = "";
					
						if(fromSlot == "")
						{
							missingfields += " 'From Slot'";
						}
					if(toSlot == "")
					{
						missingfields += " 'toSlot'";
					}	
					if(toNodeID == "")
					{
						missingfields += " 'toNodeID'";
					}
					if(toNodeName == "")
					{
						missingfields += " 'toNodeName'";
					}
					if(fromNodeID == "")
					{
						missingfields += " 'fromNodeID'";
					}
					if(fromNodeName == "")
					{
						missingfields += " \'fromNodeName\'";
					}
					if(fromsiteIDVal == "")
					{
						missingfields += " 'fromsiteIDVal'";
					}
					if(tositeIDVal == "")
					{
						missingfields += " 'tositeIDVal'";
					}

						if(missingfields != "")
							{
								$(obj).blur();
								alert("Missing field(s): "+missingfields);
								return false;
							}
						
                }
				
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			}
			

			
		 
	$(document).ready(function() {
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////

		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${DNCount}' != "addNew"){

							
					var DNCount = ${DNCount};
					
					if(($("#dncode").val()) != "" && ($("#dncode").val()) != null){

					if(SelectedIndex === DNCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									
									var param ="${pageContext.request.contextPath}/DiscoveryNewFormView?dnID="+$("#dncode").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/DiscoveryNewFormView?dnID="+$("#dncode").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/DiscoveryNewFormView?dnID="+$("#dncode").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/DiscoveryNewFormView?dnID="+$("#dncode").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}

		
					$("#label-1").text((SelectedIndex)+"/"+DNCount);

					 $("#selectnav").autocomplete({
			    			
			    		    source: function(request, response) {
			    			    
			    		             $.ajax({
			    		                 type: "GET",
			    		                 contentType: "application/json; charset=utf-8",
			    		                 url: '${pageContext.request.contextPath}/GetAllDN',
			    		                 data: {
			    								"DN" : $("#selectnav").val(),
			    						 },
			    		                 dataType: "json",
			    		                 success: function (data) {
			    		                     if (data != null) {
			    		                         response(data.listDN);
			    		                     }
			    		                 },
			    		                 error: function(result) {
			    		                     alert("Error");
			    		                 }
			    		             });
			    		         }, minLength:0, maxShowItems: 40, scroll:true,

			    					select: function(event, ui) {
			    						
			    						//this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
			    						
			    						this.value = (ui.item ? ui.item[0] : '');
			    						var param ="${pageContext.request.contextPath}/DiscoveryNewFormView?dnID="+(ui.item[0])+"&NavAction=2";
			    						window.location.href =param;
			           						
			           						return false;
			           					}
			           				}).autocomplete("instance")._renderItem = function(ul, item) {
			           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
			           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +

			  			           	                 //item[0] + '</span><br><span class="desc">' +
			           	                 //item[1] + '</span></div></li>').appendTo(ul);
						           	                 
			           	                 item[0] + '</span><br><span class="desc">').appendTo(ul);
			           			};
			           					$("#selectnav").focus(function(){
			           		   	        	if (this.value == ""){
			           		   	            	$(this).autocomplete("search");
			           		   	        	}						
			           					}); 

			           					  //// ENd of Autocomplete for GetAllDN
			    	
/*		
		$("#email").autocomplete({
		    source: function(request, response) {
		         
		    	
		           $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
		                 data: {
								"email" : request.term,
								
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.ListItemEmailAccounts);
		                         console.log('data in $("#email").autocomplete is :  '+ data.ListItemEmailAccounts);

		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		            
		         }, minLength:0, maxShowItems: 40, scroll:true,
				
		         select: function(event, ui) {
		        	 
		             this.value = (ui.item ? ui.item[0]  : '');
		             password.value = ui.item[1];
		             
		             return false;
		            
		         }
		        
		     }).autocomplete("instance")._renderItem = function(ul, item) {
		         return $("<li class='each'>")
		         .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		             item[0] + "</span></div>")
		         .appendTo(ul);
		 	};
		 	
		     
					$("#email").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});
					$("#password").focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});
*/				
		 //////////////////////////////////////END OF EMAIL AUTOCOMPLETE////////////////////////////////////////		  	
		var dict = [];
		var Errorflag;
		var flagFrom;
		var transtype_array = ["-- Select Option --","New Node","New Node on New Hardware","New Node on Existed Hardware","New Hardware on Existed Node","Transfer","Transfer from Slot to Slot","Transfer from Node to Node","Transfer from Site to Site","Disappear","Disappear for Maintenance","Disappear for Transfer","Maintenance","Replacement","Retirement"];
        var ElementName_array = ["-- Select Option --","Cabinet","Subrack","Slot","Board","Port","Antenna"];
		var Actions_array = ["-- Select Option --","Approved","Not Approved","Rejected"];

		// get the selected row index and save in RowIndex input
		function SetIndexRow(obj)
		{
			var row_index = $(obj).parent().parent().index();
			$("#RowIndex").val(row_index);
		}

		function SetCalc(obj)
		{
			var row_index = $(obj).parent().parent().index();
			var column_index = $(obj).parent().index();
			var any_index_2 = $(obj).index();
			var cell = $(obj).val();

			var qty = parseFloat($(obj).parent().parent().children('td[name="itemQty"]').children('input').val());
			var rate = parseFloat($(obj).parent().parent().children('td[name="itemRate"]').children('input').val());
			var DiscountAmount = parseFloat($(obj).parent().parent().children('td[name="itemDAmout"]').children('input').val());
			var tax = parseFloat($(obj).parent().parent().children('td[name="itemTax"]').children('input').val());

			var netrate = rate - DiscountAmount;
			tax = (tax * netrate) /100;
			var total = qty * netrate;
			var totalAT = qty * (netrate+tax);
			
         
            
            $(obj).parent().parent().children('td[name="itemNetRate"]').children('input').val(netrate);
	 		$(obj).parent().parent().children('td[name="itemTotal"]').children('input').val(total);
	 		$(obj).parent().parent().children('td[name="itemTotalAt"]').children('input').val(totalAT);
	 		getSumQty_totalAT();
		}

		
		
		 $("#saveButton").click(  function() {
			 
			  if ($("#dntotamnt").val() == '')  { dntotamnt.value = 0;  }
			  if ($("#dndiscamnt").val() == '')  { dndiscamnt.value = 0; }
			  if ($("#dndiscpercent").val() == '') { dndiscpercent.value = 0; }
			  if ($("#dntotqty").val() == '') { dntotqty.value = 0; }

			  
			 // validate Total Amount to be number 
			 if (($. isNumeric( $("#dntotamnt").val()))== false) {
			 alert('Total Amount is  not Numeric');
			 return false;}
			 
		 
			  // validate Qty to be number
			 if (($. isNumeric( $("#dntotqty").val()))== false) {
			 alert('Qty is  not Numeric');
			 return false;}
	 			 
			 getselectedrows();

		     //save all in tables DB
			 saverowsintables();

/*			 
		     if(dict.length < 10){
			     alert("YOUR ROW NUMBER IS = " +dict.length)	
			     saverowsintables();
			     } else {
                    alert(dict.length+"YOU ONLY CAN SAVE 10 RECORDS AT ONCE")
				     }	 				       
*/		 	 
		 })		 
		 
		 $("#deleteButton").click(  function() {
			 selectALLrows();
			 var dnID = document.getElementById("dncode").value;
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/DiscoveryNewFormDelete",
					dataType : "json",
					data : {
						"dnID" : $("#dncode").val()
					},
					success : function(data) {
						location.replace("${pageContext.request.contextPath}/DiscoveryNewListView");
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});
				
				});																	 

	
	//scripts for table DNItem
	   if ('${ListPRqItem}' != "addNew") {
	    	//return false;
	    boqArray = [];
	    boqArray = ${ListPRqItem};	
		
		 itemArray =${ListPRqItem};
		var sumTotal  ;
		var sumQty ;   
        var newslct =[];
        var ressplit;
	 loadData();////loadData Function exist in DiscoveryNewItem.js
	   }
	   else {
	        // set status to new and green while new record
	           $("#formStatus").text("New");
					$('.dot').css({"background-color" : "orange"});
	       }
       
     	$("#ProjectManagerApprove").on("click", function(){

     		getselectedrowsForApproval("managerApproval");
     	});

     	$("#AssetUnitApprove").on("click", function(){

     		getselectedrowsForApproval("AssetApproval");
     	});

     	$("#FinanceApprove").on("click", function(){

     		getselectedrowsForApproval("FinanceApproval");
     	});
		
       		
		function getselectedrowsForApproval(actionType)
		{
			var ApprovDict = [];
			var ApprovObjects = {};
			var NotTakingArray = [];
			var i =0;
			$("#bisotab > tbody").find('input[name="record"]').each(function(){
				
				if($(this).is(":checked")){
					
					DnItemID = $(this).parent().parent().children('td[name="itemDniID"]').children('input').val();
					aprov =$(this).parent().parent().children('td[name="buttonApprove"]').children('input').val();
					item = $(this).parent().parent().children('td[name="item"]').children('input').val();
					purchaseOrder = $(this).parent().parent().children('td[name="purchaseOrder"]').children('input').val();
					siteID = $(this).parent().parent().children('td[name="siteID"]').children('input').val();
					itemModel = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
					itemPartNb = $(this).parent().parent().children('td[name="itemPartNb"]').children('input').val();
					itemSN = $(this).parent().parent().children('td[name="itemSN"]').children('input').val();
						
					indexRow = $(this).parent().parent().index()+1;

					if(DnItemID != 0)
					{
						if(actionType == "managerApproval")
						{
							if(aprov == "To be Approved By Project Manager")
							{
								ApprovObjects.ApproveType = "Asset Unit";
								ApprovObjects.DNItem = DnItemID;
								ApprovObjects.ApprovAction = "Approved";
								ApprovObjects.item = item;
								ApprovObjects.purchaseOrder = purchaseOrder;
								ApprovObjects.siteID = siteID;
								ApprovObjects.itemModel = itemModel;
								ApprovObjects.itemPartNb = itemPartNb;
								ApprovObjects.itemSN = itemSN;
								ApprovDict.push(ApprovObjects);
							}
							else
								NotTakingArray.push(indexRow);
						}

						if(actionType == "AssetApproval")
						{
							if(aprov == "To be Approved By Asset Unit")
							{
								ApprovObjects.ApproveType = "Finance";
								ApprovObjects.DNItem = DnItemID;
								ApprovObjects.ApprovAction = "Approved";
								ApprovObjects.item = item;
								ApprovObjects.purchaseOrder = purchaseOrder;
								ApprovObjects.siteID = siteID;
								ApprovObjects.itemModel = itemModel;
								ApprovObjects.itemPartNb = itemPartNb;
								ApprovObjects.itemSN = itemSN;
								ApprovDict.push(ApprovObjects);
							}
							else
								NotTakingArray.push(indexRow);
						}

						if(actionType == "FinanceApproval")
						{
							if(aprov == "To be Approved By Finance")
							{
								ApprovObjects.ApproveType = "Complete";
								ApprovObjects.DNItem = DnItemID;
								ApprovObjects.ApprovAction = "Approved";
								ApprovObjects.item = item;
								ApprovObjects.purchaseOrder = purchaseOrder;
								ApprovObjects.siteID = siteID;
								ApprovObjects.itemModel = itemModel;
								ApprovObjects.itemPartNb = itemPartNb;
								ApprovObjects.itemSN = itemSN;
								ApprovDict.push(ApprovObjects);
							}
							else
								NotTakingArray.push(indexRow);
						}
						i++;
						ApprovObjects = {};
					} 
				} 
				
			})
			
			//alert(ApprovDict); alert(ApprovDict[0].DNItem); alert(ApprovDict[1].DNItem);
			if(NotTakingArray.length != 0)
			{
				alert("Cannot Proceed for Row(s) : "+NotTakingArray);
				return false;
			}

			if (ApprovDict.length == 0) {
	           	alert(' Select Row(s) to Approve');
	         		return false;
	         }

			else
			{
				var proceed = confirm("Are you sure you want to proceed?");
				if (proceed) {
				//proceed
				$.ajax({
        			type : "GET",
        			url : "${pageContext.request.contextPath}/setGlobalApprove",
        				dataType : "json",
        				data : {
        				    "type": actionType,
        				    "dictParameter" : ApprovDict                      
        					
        				},
        				success : function(data) {
        					var param ="${pageContext.request.contextPath}/DiscoveryNewFormView?dnID="+$("#dncode").val();
							location.replace(param);
        					
        				},
        				error : function(error) {
        					console.log("Fail");
        				}
        		});
				
				} else {
				  //don't proceed
				}
			}

	        
		}


		
	    //function to get selected rows for save or delete
		function getselectedrows() {
			dict = [];
			var dictObj = {};
				$("#bisotab > tbody").find('input[name="record"]').each(function(){
						
						dictObj.item = $(this).parent().parent().children('td[name="item"]').children('input').val(); 
	                    dictObj.transType = $(this).parent().parent().children('td[name="transType"]').children('select').val();
	                    dictObj.notes = $(this).parent().parent().children('td[name="notes"]').children('input').val();
	                    dictObj.elementName = $(this).parent().parent().children('td[name="elementName"]').children('select').val();
	                    dictObj.address = $(this).parent().parent().children('td[name="address"]').children('input').val();
	                    dictObj.approvedby = $(this).parent().parent().children('td[name="approveStatus"]').children('input[name="approvedby"]').val();
	                    if($(this).parent().parent().children('td[name="approveStatus"]').children('select'))
	                    	dictObj.aprovStatus =($(this).parent().parent().children('td[name="approveStatus"]').children('select').val());
						else
							dictObj.aprovStatus = "Completely Approved";
						     dictObj.itemModel = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
						     dictObj.itemPartNb = $(this).parent().parent().children('td[name="itemPartNb"]').children('input').val();
						     dictObj.purchaseOrder = $(this).parent().parent().children('td[name="purchaseOrder"]').children('input').val();
						     dictObj.workOrder = $(this).parent().parent().children('td[name="workOrder"]').children('input').val();
						     dictObj.itemQty = $(this).parent().parent().children('td[name="itemQty"]').children('input').val();
						     dictObj.itemRate = $(this).parent().parent().children('td[name="itemRate"]').children('input').val();
						     dictObj.itemDAmout = $(this).parent().parent().children('td[name="itemDAmout"]').children('input').val();
						     dictObj.itemTax = $(this).parent().parent().children('td[name="itemTax"]').children('input').val();
						     dictObj.itemNetRate = $(this).parent().parent().children('td[name="itemNetRate"]').children('input').val();
						     dictObj.itemTotal = $(this).parent().parent().children('td[name="itemTotal"]').children('input').val();
						     dictObj.itemTotalAt = $(this).parent().parent().children('td[name="itemTotalAt"]').children('input').val();
						     dictObj.itemNodeID = $(this).parent().parent().children('td[name="itemNodeID"]').children('input').val();
						     dictObj.toNodeID = $(this).parent().parent().children('td[name="toNodeID"]').children('input').val();
						     dictObj.itemNodeName = $(this).parent().parent().children('td[name="itemNodeName"]').children('input').val();
						     dictObj.itemToNodeName = $(this).parent().parent().children('td[name="itemToNodeName"]').children('input').val();
						     dictObj.alcflg = $(this).parent().parent().children('td[name="itemToNodeName"]').children('input[name="alcflg"]').val();
						     dictObj.fromSlot = $(this).parent().parent().children('td[name="fromSlot"]').children('input').val();
						     dictObj.itemToSlot = $(this).parent().parent().children('td[name="toSlot"]').children('input').val();
						     dictObj.siteID = $(this).parent().parent().children('td[name="siteID"]').children('input').val();
						     dictObj.tositeID = $(this).parent().parent().children('td[name="tositeID"]').children('input').val();
						     dictObj.itemSN = $(this).parent().parent().children('td[name="itemSN"]').children('input').val();
						     dictObj.toSN = $(this).parent().parent().children('td[name="toSN"]').children('input').val();
						     dictObj.itemDniID = $(this).parent().parent().children('td[name="itemDniID"]').children('input').val();
						     dictObj.description = $(this).parent().parent().children('td[name="description"]').children('input').val(); 
	                  
						dict.push(dictObj);
						dictObj = {};


/*
						if(dictObj.aprovStatus = "Completely Approved"){
                             alert("Completely Approved");
						} 
						
                        if(dictObj.aprovStatus = "-- Select Option --"){
                            alert("drafted");
                            dict.push(dictObj);
							dictObj = {};
						}
*/


					  
	            	});
					             	
				
					}
		
		   // save Data in DB
            function saverowsintables (){

             var dnID = document.getElementById("dncode").value;
			 var dncreationDate = document.getElementById("dncreatedate").value;
			 var dnlastModifieddate = document.getElementById("dnlstmodifdate").value;
			 var dnTotalAmount = document.getElementById("dntotamnt").value;
			 var dnStatus = document.getElementById("dnstat").value;
			 var dnTotalQty = document.getElementById("dntotqty").value;
			 var type = "";
			 if('${ListPRqItem}' == "addNew")
				 type = "addNew";
			 
	   		 var token =  $('input[name="csrfToken"]').attr('value');
           		
           		$.ajax({
        			type : "POST",
        			url : "${pageContext.request.contextPath}/DiscoveryNewItemFormSave",
        				dataType : "json",
	   			        headers: {
	                        'X-CSRFToken': token 
	                    },        				
        				data : {
        				    "type": type,
        				    "dictParameter" : dict,
        				    "slctDelDN":slctDelDN,
        				     "dnID" : $("#dncode").val(),
							"dncreationDate" : dncreationDate,
							"dnlastmodifDate" : dnlastModifieddate,
							"dnTotalAmount" : dnTotalAmount,                          
							"dnStatus" : $("#dnstat").val(),
							"dnTotalQty" : dnTotalQty,
							"email": $("#email").val(),
							"password":$("#password").val(),
					  	    "emailTo": $("#emailTo").val(),
						    "ccmail": $("#ccmail").val(),
						    "subject": $("#subject").val(),
						    "message": $("#message").val(),
        				},
        				success : function(data) {
        					dncode.value=data.DNID;
        					var param ="${pageContext.request.contextPath}/DiscoveryNewFormView?dnID="+$("#dncode").val()+"&NavAction=2";
							location.replace(param);
        					
        				},
        				
        				error : function(error) {
        					console.log("Fail");
        				}
        		});
      			 console.log("in savee 2222");
      	          

     	}
        //end save data
        
        function splitrow (tab1) {
         	newslct=[];
            var valData= tab1;
           	var n = valData.indexOf(";");
	            var res= valData.substring(0, n);
	            ressplit=res;
	     		newslct= valData.substring(n+1, tab1.length);
	          	
          }
        
        
    
        

        $('#bisotab tr td input').on('focus', function() {

			SetIndexRow(this);
		});


        var ctx = getContextPath();
        
        function customRenderItem(ul, item) {
			var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
				result = $('<li class="ui-menu-item" role="menuitem"></li>')
				.data('item.autocomplete', item)
				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
				.appendTo(ul);

			return result;
		}



 ////////////////////////////////////////// AHMAD UPDATING AUTOCOMPLETE  //////////////////////////////////////////
        
        
     $('input[name ="itmCode"]').each(function(i, el) {
			
			$(el).autocomplete({
				
	    	    source: function(request, response, event, ui) {
     			 
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: ctx+'/getItemCode',
		                 data: {
		                	  requestValue : request.term,
                              barcode : $("#barcode").val()
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.ListItemDetails);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		        }, minLength:0, maxShowItems: 4, scroll:true,
				select: function(event, ui) {
					this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
                    $(this).parents("tr").find('input[name ="itmMod"]').val(ui.item[2]);
                    $(this).parents("tr").find('input[name ="itmPartNb"]').val(ui.item[3]);
                    
					return false;
				}
			}).autocomplete("instance")._renderItem = function(ul, item) {
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
		$(this).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
	        }
		});
		});


 




 	$('input[name ="itmMod"]').each(function(i, el) {
 		
 		$(el).autocomplete({
     	    source: function(request, response, event, ui) {
     			 
 	             $.ajax({
 	                 type: "GET",
 	                 contentType: "application/json; charset=utf-8",
 	                 url: ctx+'/getModel',
 	                 data: {
 	                	    requestValue : request.term,
                            barcode : $("#barcode").val()
 	                	 
 					 },
 	                 dataType: "json",
 	                 success: function (data) {
 	                     if (data != null) {
 	                         response(data.ListModels);
 	                     }
 	                 },
 	                 error: function(result) {
 	                     alert("Error");
 	                 }
 	             });
 	        }, minLength:0, maxShowItems: 40, scroll:true,
 			select: function(event, ui) {
 				this.value = (ui.item ? ui.item[0] : '');
 				$(this).parents("tr").find('input[name ="itmCode"]').val(ui.item[1]+ ":" + ui.item[2]);
 			    $(this).parents("tr").find('input[name ="itmPartNb"]').val(ui.item[3]);
				
 					return false;
 			}
 		}).autocomplete("instance")._renderItem = function(ul, item) {
 			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" + 
 			item[0] + "</span><br><span class='desc'>" + 
            item[1] + "</span><span class='desc'>"+"," + 
            item[2] + "</span><span class='desc'>"; 
            if(item[3] != '-') 
             appendString += ","+item[3] + "</span><span class='desc'>"; 
            if(item[4] != '-') 
	         appendString += ","+item[4]; 
             appendString += "</span></div>"; 
             
          return $("<li class='each'>").append(appendString).appendTo(ul);
 	};
 	$(this).focus(function(){
 		if (this.value == ""){
 			$(this).autocomplete("search");
 	        }
 	});
 	});
 		

 	




	$('input[name ="itmPartNb"]').each(function(i, el) {
		$(el).autocomplete({
    	    source: function(request, response, event, ui) {
    			 
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/getPartNo',
	                 data: {
 	                	    requestValue : request.term,
                            barcode : $("#barcode").val()
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListPartNos);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	        }, minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
				this.value = (ui.item ? ui.item[0] : '');
				$(this).parents("tr").find('input[name ="itmCode"]').val(ui.item[1]+ ":" + ui.item[2]);
			    $(this).parents("tr").find('input[name ="itmMod"]').val(ui.item[3]);
				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" + 
			 item[0] + "</span><br><span class='desc'>" + 
			 item[1] + "</span><span class='desc'>"+"," + 
			  item[2] + "</span><span class='desc'>";

			  if(item[3] != '-')
               appendString += ","+item[3] + "</span><span class='desc'>"; 
              if(item[4] != '-') 
	           appendString += ","+item[4];
               appendString += "</span></div>"; 
            return $("<li class='each'>").append(appendString).appendTo(ul);
	};
	$(this).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
	});
 	




	//Starting for Warehouse auto complete in poq table	
	$('input[name ="siteID"]').each(function(i, el) {
		$(el).autocomplete({
    	    source: function(request, response, event, ui) {
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/GetAllWarehouse',
	                 data: {
	                	 "warehouseName" : request.term,
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
				this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] + ":" + ui.item[2] : '');
				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                item[0] + "</span><br><span class='desc'>" +
                item[1] +', '+ item[2] + "</span></div>")
            .appendTo(ul);
	};
	$(this).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
	});

	/////////////end autocpmplete for warhouse	
	
				//Starting for Warehouse auto complete in poq table
			$('input[name ="tositeID"]').each(function(i, el) {
				$(el).autocomplete({
		    	    source: function(request, response, event, ui) {
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetAllWarehouse',
			                 data: {
			                	 "warehouseName" : request.term,
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
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
			};
			$(this).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});
			});
			
	//auto complete for PO (when adding new rows)
	$('input[name ="itmPO"]').each(function(i, el) {
		$(el).autocomplete({
    	    source: function(request, response, event, ui) {
	    	    
    	    	 var RowIndex = document.getElementById('RowIndex').value;
    	    	 var ItemArray = getAllitemCode();
    	    	 var Item_code = ItemArray[RowIndex];
    			 if(Item_code != "empty")
        		 {
    				 Item_code = Item_code.split(":");
	        		 Item_code = Item_code[0];
	             }

    			 var ItemModelsArray = getAllItemModels();
    			 var itemModel = ItemModelsArray[RowIndex];

    			 var ItemPartNbsArray = getAllItemPartNbs();
    			 var itemPartNb = ItemPartNbsArray[RowIndex];

    			 
    			 var WoArray = getAllWorkOrders();
    			 var WorkOrder = WoArray[RowIndex];
    			 if(WorkOrder != "empty")
        		 {
    				 WoOrder = WorkOrder.split(":");
	        		 WoOrderID = WoOrder[0];
	             }
    			 else
    				 WoOrderID = WorkOrder;
    			     console.log("*/*/WoOrderID is" +WoOrderID);
    			 
    			 
    			 
    			 console.log("itemPartNb is" +itemPartNb);
    			 
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/GetAllPoByItem',
	                 data: {
	                	 	"PO" : request.term,
							"Item_code" : Item_code,
							"itemModel" : itemModel,
							"WoOrderID":WoOrderID,
							"itemPartNb" : itemPartNb
							
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                    	 response(data.ListPos);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	        }, minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
				this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] + ":" + ui.item[2] + ":" + ui.item[3] : '');
				var PoID = ui.item[0];
				
				ItemCode = ($(this).parent().parent().children('td[name="item"]').children('input').val());
				if(ItemCode != "")
				{
					ItemCode = ItemCode.split(":");
					ItemCode = ItemCode[0];

					var qty = $(this).parent().parent().children('td[name="itemQty"]').children('input');
					var rate = $(this).parent().parent().children('td[name="itemRate"]').children('input');
					var discountAmount = $(this).parent().parent().children('td[name="itemDAmout"]').children('input');
					var tax = $(this).parent().parent().children('td[name="itemTax"]').children('input');
					var obj = $(this).parent().parent().children('td[name="purchaseOrder"]').children('input');

					$.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: ctx+'/GetPOitemDetails',
		                 data: {
								PoID : PoID,
								ItemCode : ItemCode,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
			                     
				                    qty.val(1);    // fill the qty field
				                    rate.val(data.PoDetails[0][1]);  // fill the rate field 
				                    discountAmount.val(data.PoDetails[0][2]);   // fill the discountAmount field
				         			tax.val(data.PoDetails[0][3]);   // fill the tax field

				         			SetCalc(obj);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
				}
	             return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                item[0] + "</span><br><span class='desc'>" +
                item[1] +', '+ item[2] + "</span></div>")
            .appendTo(ul);
	};
	$(this).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
	});/////////////end autocpmplete for PO




	$('input[name ="itmWO"]').each(function(i, el) {
		$(el).autocomplete({
    	    source: function(request, response, event, ui) {

    	    	 var RowIndex = document.getElementById('RowIndex').value;
    	    	 var ItemArray = getAllitemCode();
    	    	 var Item_code = ItemArray[RowIndex];
    			 if(Item_code != "empty")
        		 {
    				 Item_code = Item_code.split(":");
	        		 Item_code = Item_code[0];
	             }

    			 var PoArray = getAllPurchaseOrders();
    			 var purchaseOrder = PoArray[RowIndex];
    			 if(purchaseOrder != "empty")
        		 {
    				 PrOrder = purchaseOrder.split(":");
	        		 PrOrderID = PrOrder[0];
	             }
    			 else
    				 PrOrderID = purchaseOrder;
    			     console.log("PrOrderID is " +PrOrderID);
    			    
			     var ItemModelsArray = getAllItemModels();
    			 var itemModel = ItemModelsArray[RowIndex];

    			 var ItemPartNbsArray = getAllItemPartNbs();
    			 var itemPartNb = ItemPartNbsArray[RowIndex];

    			 
    			 
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/GetALLWOByItem',
	                 data: {
		                 "woId":request.term,
	                	 "ItemPartNb" :itemPartNb,
	                	 Item_code : Item_code,
	                	 "itemModel" : itemModel,
	                	 PrOrderID : PrOrderID
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListWO);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	        }, minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
				this.value = (ui.item ? ui.item[0] + ":" + ui.item[1]  : '');
				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			return $("<li class='each'>")
            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                item[0] + "</span><br><span class='desc'>" +
                item[1] + "</span></div>")
            .appendTo(ul);
	};
	$(this).focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
	        }
	});
	});





					
		    
	    //end scipts table DN Item
	
	
	           //to select all cjeckbox or unselect them all from top table
						$('body').on('click', '#selectAll', function  () {
					   
      					if ($(this).hasClass('allChecked')) {
         				$('input[type="checkbox"]', '#bisotab').prop('checked', false);
      						} else {
       						$('input[type="checkbox"]', '#bisotab').prop('checked', true);
       						}
       						$(this).toggleClass('allChecked');
       				
     					})
					
    			
    			//when click on save or delete to select all rows by default
    			function selectALLrows () {
	    			if ($(this).hasClass('allChecked')) {
	         				$('input[type="checkbox"]', '#bisotab').prop('checked', false);
	      						} else {
	       						$('input[type="checkbox"]', '#bisotab').prop('checked', true);
	       						}
	       						$(this).toggleClass('allChecked');
							}
	
	
	                
	                
	                 function getSumQty_totalAT (){
         				var sumQntity = 0;
         				var sumtotAT = 0;
         		
         		       				         		 
         		 
         		
						$("#bisotab > tbody > tr").each(function(i, row){
					
		                    sumQntity = sumQntity + parseFloat($(this).children('td[name="itemQty"]').children('input').val());
		                    sumtotAT = sumtotAT + 	parseFloat($(this).children('td[name="itemTotalAt"]').children('input').val());				
		            	});
		                
		               
		                
		                $('#dntotqty').val(sumQntity);
		                $('#dntotamnt').val(sumtotAT);
		                
		                 
                        
					};



					  	
				    
	                
}) //end ready document

 
</script>
 

	
 </html>
