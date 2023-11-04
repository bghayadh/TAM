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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->

	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<!-- 

	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	 
	<script src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>
	
	 -->
	
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
	
		
    
     <!--  style used for prrqitem table  -->    
    <style>
    .hide-row { visibility: hidden; }
    
    
    .label{ 
  display: table-caption;
  text-align: center;
  font-size: 20px;
  font-style: bold;
  }
  

.ui-autocomplete {
         	max-height: 300px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: both; /* add padding to account for vertical scrollbar */
	padding-right: 100px;
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
				


#textalert{

font-size:15px;
color:red;

margin-left:10px;
display: inline-block;
margin-bottom:0px;
padding-bottom:0px;
 
}
     		 


	.checkCenter{
   margin: 0;
  position: absolute;
  top: 50%;
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
	}

	.rightpush{

  right: 50%;
 
	}
	
	
	td {
	text-align: center;
	vertical-align: middle!important;
	width:200px;
	}
	
	 .hide-row { display:none; }
	
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
		.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 100px;
					z-index:9999;
	        		}
	

	th{
	text-align:center;
	width:250px;
	}
 	</style>
</head>
<body>

<%--  	<c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
	
	
	 <p></p>
<div class="container-fluid">
		<div class="row">
			
		
			<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span style="width:200px;"class="input-group-text">Notification ID</span>
							<input type="text" readonly id="id"  value="${id}" class="form-control text-input"   />
							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
						</div>
					</div>
			</div>
			<div class="col-md-4">
				<div class="form-group"  style="text-align:center;">
					<div class="input-group-prepend" style="text-align:center;">
				
				 <span class="input-group-text" style="width:120px">
				 <input id="enabled" name="enabled"  type="checkbox" style="margin-right:10px;height:10px;"><input  type="text" id="enabledVal" val="${enabled}" hidden>  Enabled</span>
			</div>
		</div>
	</div>
			<div class="col-md-3">
				<div class="input-group-prepend">
		
				
				</div>							
			</div>
	
		
			
			<div  class="col-md-2 text-right"  >
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
						</div> 
						
						
						</div>
			
			
			<div class="row">
	
	<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span style="width:200px;" class="input-group-text">Created Date</span>
					<input type="text" id="creationDate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span style="width:200px;" class="input-group-text">Last Modified Date</span>
										<input type="text" id="lastModifiedDate" readonly value="${lastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					
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
			    		<li id="btnPrv" class="page-item " style="margin-right: 2px;"><a style="width:120px;" id="btnPrva" href="#" class="btn btn-success listItemsNavious">&laquo; Previous</a></li>				
			    		<li id="btnNext" class="page-item" style=" padding-right: 0px ! important;"><a style="width:120px;" id="btnNexta" style="width:120px;" href="#" class="btn btn-success next">Next &raquo;</a></li>
			  		</ul>
				</nav>
		</div>
		
		
	
	
	<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/NotificationListView"'
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
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">	INFORMATION</a></li>
      
       
             	<li class="nav-item"><a class="nav-link"
            id="custom-tabs-recipients-tab" data-toggle="tab"
            href="#custom-tabs-recipients" role="tab"
            aria-controls="#custom-tabs-recipients-tab" aria-selected="false" style="color: gold;">RECIPIENTS</a></li>
      
          	<li class="nav-item ml-auto">
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

<div class="tab-content" id="custom-tabs-one-tabContent">
	<div class="tab-pane fade show active" id="custom-tabs-one-home"
	role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
    
     
<p></p>
    	<!--  create Notification Information    -->
    
<div class="row">
<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Name</span>
						<input type="text" id="notificationName" value="${notificationName}" class="form-control text-input" />
					
					</div>
				</div>
	</div>

	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text"  >Channel</span>
					<select id="channel" class="form-control select2">
				  	<option value="Email" <c:if test = "${channel =='Email'}" > selected </c:if> >Email</option>
					<option value="SMS" <c:if test = "${channel =='SMS'}" > selected </c:if>>SMS</option>
				    </select>
					</div>
					</div>
	</div>

	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Module Name</span>
						<select id="moduleName" class="form-control select2">
					<option value=" " <c:if test = " " > selected </c:if> > </option>
				  	<option value="PurchaseRequest" <c:if test = "${moduleName =='PurchaseRequest'}" > selected </c:if> >Purchase Request</option>
					<option value="PurchaseOrder" <c:if test = "${moduleName =='PurchaseOrder'}" > selected </c:if>>Purchase Order</option>
					<option value="Goods Received" <c:if test = "${moduleName =='Goods Received'}" > selected </c:if> >Goods Received</option>
					<option value="Supplier" <c:if test = "${moduleName =='Supplier'}" > selected </c:if>>Supplier</option>
					<option value="Work Order" <c:if test = "${moduleName =='Work Order'}" > selected </c:if> >Work Order</option>
					<option value="Item" <c:if test = "${moduleName =='Item'}" > selected </c:if>>Item</option>
					<option value="Warehouse" <c:if test = "${moduleName =='Warehouse'}" > selected </c:if> >Warehouse</option>
					<option value="Asset Registry" <c:if test = "${moduleName =='Asset Registry'}" > selected </c:if>>Asset Registry</option>
					<option value="Agents" <c:if test = "${moduleName =='Agents'}" > selected </c:if>>Agents</option>
				    </select>
				
				</div>
			</div>
	</div>
	
 </div>
 
     <!-- Choose Category Modal -->
           
 
 <div class="row">
 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Subject</span>
						<input type="text" id="subject" value="${subject}"  class="form-control text-input" />
					
					</div>
				</div>
	</div>
	
<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text">Send Alert On</span>
					<select id="sendAlertOn" class="form-control select2">
				  	<option value="New" <c:if test = "${sendAlertOn =='New'}" > selected </c:if> >New</option>
					<option value="Save" <c:if test = "${sendAlertOn =='Save'}" > selected </c:if>>Save</option>
					<option value="Approve" <c:if test = "${sendAlertOn =='Approve'}" > selected </c:if> >Approve</option>
					<option value="Cancel" <c:if test = "${sendAlertOn =='Cancel'}" > selected </c:if>>Cancel</option>
				   </select>
					</div>
					</div>
	</div>

	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Sender</span>
				<input type="text" id="sender" value="${sender}"  class="ui-widget ui-widget-content ui-corner-all form-control" />
						<input type="text" id="password"  class="ui-widget ui-widget-content ui-corner-all form-control" hidden/>
						
				
				</div>
			</div>
	</div>
</div>

	
	<div class="row">	
	
	
	 						<div class="col-md-4">
                                <div class="input-group-prepend">
                                <span style="width:120px;" class="input-group-text">Condition</span>
                                </div>
                            </div> 

	
<div class="col-md-4">
                                <div class="input-group-prepend">
                                <span style="width:120px;" class="input-group-text">Message</span>
                                </div> </div> </div>
     <div class="row">                      
	<div class="col-md-4"><textarea name="condition" cols="100" rows="7" id="condition" class="form-control text-input">${condition}</textarea></div>



 
   <div class="col-md-8"><textarea name="message" cols="600" rows="7" id="message" class="form-control text-input">${message}</textarea></div>

	</div>
	<div class="row">
	<div class="col-md-4">
	<b>Hints:</b> <br>
	Condition is limited to TotalAmount and TotalQty<br>
	for e.g.<br>
	TotalAmount>1000 and TotalQty<20<br>
	you can use both or one of them with any of the following operators(<,>,=)
	
	</div>
	    </div>
	</div>	
	
		
 <div class="tab-pane fade" id="custom-tabs-recipients" role="tabpanel" aria-labelledby="custom-tabs-recipients-tab">


<div> 
  
		<form>
				
								
			<div class="table-responsive-sm"> 
			  <table id ="recipientsTab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
						     <thead>
						         <tr>
						            <th style="width:80px;">
								        <button type="button" id="selectAll" class="main">
								        <span class="sub"></span>Select</button></th>
								         <th>Job-Title</th>
						                 <th>Email To</th>
										<th>CC-Email</th>
										<th>Condition</th>
										 <th>ID</th>
						                
						          </tr>
						        </thead>
								
								<tbody>
						            
									
						        </tbody>
						      
						    </table>
						    <p id="my_error" style="color:red;"></p>
					    </div>
					     <input type="text" id="RowIndex" value="" hidden>
						  <input type="button" class="add-row" value="Add Row">
						  <button type="button" class="delete-row">Delete Row</button>
                   </form>
		</div>

<p></p>
</div>
</div>
</div>
	
        
 </body>
 
 
 
 <script type='text/javascript'>

/////////////////////////////////      ADD NEW ROW TO BOQ       ///////////////////////////
 $(".add-row").click(function(){
 	console.log("testing add-row");
 	var day = moment().format('L') +" "+ moment().format('LT'); 
	var markup = "<tr><td style='width:80px;'><input type='checkbox' name='record'></td>"
		+"<td name='jobTitle'><input name='jobTitle'  type='text'  class='form-control text-input'/>  </td>"
		+"<td name='emailTo'><input name='emailTo'  type='text'  class='form-control text-input'/>  </td>"
		+"<td name='ccEmail'><input  name='ccEmail' type='text' class='form-control text-input'/>   </td>"
		+"<td name='Condition'><input name='Condition' type='text'  class='form-control text-input'/>   </td>"
		+"<td name='ID' style='width:120px;'><input  type='text'  class='form-control text-input' readonly/></td>"
		
     $("#recipientsTab > tbody").append(markup);

		function SetIndexRow(obj)
		{
			var row_index = $(obj).parent().parent().index();
			$("#RowIndex").val(row_index);
		}
     // select cell per row and col
		});////end of add row
//////////////////////////////               delete-row             ///////////////////////////////					
	    
		  var slctDelRecipient= [];
	      //remove selected rows from boq
	        $(".delete-row").click(function(){
	        	console.log("testing delete-row");
	        	var checked="false"; //when no checkbox is checked
	            var recipientsId = "";  
			   
	       	   	$("#recipientsTab > tbody").find('input[name="record"]').each(function(){

	             if($(this).is(":checked")){ 
	            	 checked="true"; //when 1 or more checkbox is checked
	            			 
	       				recipientsId=$(this).parent().parent().children('td[name="ID"]').children('input').val();
	       				if( recipientsId !=0)
	       					slctDelRecipient.push(recipientsId);
	     			     $(this).parents("tr").remove();  
	       			
		    	}   //end of checked box in boq for delete
	       			});
	       	   	if(checked=="false"){
										  
	       	 	alert(' Select Row(s) to Delete');
				
	         	return false;
	       	   	}
	     	});// end delete row
	     	
	     	
/////////////////////////////         when pressing select all button in boq      ////////////////
			$('body').on('click', '#selectAll', function  () {
				   
					if ($(this).hasClass('allChecked')) {
 				$('input[type="checkbox"]', '#recipientsTab').prop('checked', false);
						} else {
						$('input[type="checkbox"]', '#recipientsTab').prop('checked', true);
						}
						$(this).toggleClass('allChecked');
				
					})//end of select all checkbox 
					
//////////////////////////////////////   changinging status to NOT SAVED when pressing add/delete-row     ////////////////////////////////////////
					 $(".add-row").click(function() {
							$("#formStatus").text("Not Saved");
							$('.dot').css({"background-color" : "orange"});
						});
					 $(".delete-row").click(function() {
							$("#formStatus").text("Not Saved");
							$('.dot').css({"background-color" : "orange"});
						});	
					 $("#recipientsTab > tbody").change(function() {
							$("#formStatus").text("Not Saved");
							$('.dot').css({"background-color" : "orange"});
						});	
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		
		
 ////////////////////////////////////////////////////////// Previous and Next  ///////////////////////
 
 
 if ('${ListNo}' == "addNew") {
	 $("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});}
		

////////////////      to get data for boq from DB           //////////////
else{

	    boqArray = [];
		boqArray = ${ListNo};
		
			
			      
			  	
		        // get all colmns count per row
		   		function count(array){
		                var c = 0;
		                for(i in array) // in returns key, not object
		                    if(array[i] != undefined)
		                        c++;
		                return c;
		         }
      
		        // Fill BOQ rows from DB
		    	for (i = 0;i<boqArray.length;i++){

		    		var ID =  boqArray[i].id;
						if(ID == null){
						ID = "";
						}
						else{
						ID =  boqArray[i].id;
						}

					var jobTitle =  boqArray[i].jobTitle;
				 		if(jobTitle == null){
				 		jobTitle = "";
				 		}
				 		else{
				 		jobTitle =  boqArray[i].jobTitle;
				 		}
				 		
			 		var emailTo =  boqArray[i].emailTo;
				 		if(emailTo == null){
				 			emailTo = "";
				 		}
				 		else{
				 			emailTo =  boqArray[i].emailTo;
				 		}
				 		
				 	var ccEmail =  boqArray[i].ccEmail;
						if(ccEmail == null){
						ccEmail = "";
						}
						else{
						ccEmail =  boqArray[i].ccEmail;
						}
				 	var Condition =  boqArray[i].condition;
						if(Condition == null){
						Condition = "";
						}
						else{
						Condition =  boqArray[i].condition;
						}


	   	  var itemRow = "<tr>";
	   	  itemRow= itemRow + "<td style='width:80px;'><input type='checkbox' name='record'></td>";
	       
	        itemRow =itemRow + "<td name='jobTitle'><input name='jobTitle'  type='text'  class='form-control text-input' value='" + boqArray[i].jobTitle + "'/> </td>";
	        itemRow =itemRow + "<td name='emailTo'><input name='emailTo'  type='text'  class='form-control text-input' value='" + boqArray[i].emailTo + "'/> </td>";
	        itemRow =itemRow + "<td name='ccEmail'><input  name='ccEmail' type='text' class='form-control text-input'  value='" + boqArray[i].ccEmail + "'/></td>";
	        itemRow =itemRow + "<td name='Condition'><input name='Condition' type='text'  class='form-control text-input'  value='" + boqArray[i].condition + "'/></td>";
	       itemRow =itemRow + "<td name='ID' style='width:150px;' ><input  type='text'  class='form-control text-input' readonly value='" + boqArray[i].id +"'/></td>";
	        $("#recipientsTab > tbody").append(itemRow);

	        
		    	}
	        
	    	}
///////////////////////////////////////////////////////////////////////
 
 var navList = [];
 var iCode = "${id}";
 var Enable="${enabled}";

 var listItemsNav = ${listItemsNav};
 var indexItm;
 console.log("welcome to form View");
 if(iCode !=""){
 if(listItemsNav != "noList" && listItemsNav.length >1){
 	var size = new TextEncoder().encode(JSON.stringify(listItemsNav)).length;
 	var kiloBytes = size / 1024;
 	var megaBytes = kiloBytes / 1024;
 	var browser = platform.name;
 	if(browser == "Samsung Internet" && megaBytes >2){
 		localStorage.setItem("notifiList", JSON.stringify(listItemsNav));
 		}else if(browser.includes("Safari") && megaBytes >5){
 			localStorage.setItem("notifiList", JSON.stringify(listItemsNav));
 			}else if(megaBytes >10){
 				localStorage.setItem("notifiList", JSON.stringify(listItemsNav));
 				}else{navList = listItemsNav;
 					localStorage.removeItem("notifiList");}
 	
 		findIndex();
 	 }else{
 		 navList = JSON.parse(localStorage.getItem("notifiList"));
 		 if(navList !=null && navList.length >1){
 			 console.log("get sto ");
 			 findIndex();
 		 }else {
 		 $(".nextprvItems").addClass("hide-row ");
 		 $(".pad").removeClass("hide-row ");
 		 }
 	 }
 }else{
 $(".nextprvItems").addClass("hide-row ");
 $(".pad").removeClass("hide-row ");
 }
 function findIndex(){
 		for(var i=0;i<navList.length;i++){
 			console.log(navList[i] +" comp "+ iCode);
 			if(navList[i] == iCode){
 				if(i == (navList.length -1)){
 					$("#btnNexta").addClass("disabled");
 					}else if(i == 0){
 						console.log("np prv");
 						$("#btnPrva").addClass("disabled");
 						}
 				indexItm = i;
 				console.log("index is "+indexItm);
 				//return;
 				}
 			
 			}
 	}
//////////////// change from Save to Not Save when changing values  /////////////
 $("input").change(function() {
 	$("#formStatus").text("Not Saved");
 	$('.dot').css({"background-color" : "orange"});
 	});
$("#notificationName").on('keyup change', function () {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		});
$("#subject").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});	
$("#message").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});
$("#condition").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});
$("#channel").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});
$("#moduleName").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});
$("#sendAlertOn").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});
$("#enabled").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});
/////////////////////////////////////////////////////////////////////////////////////
	var dict = [];
 function saverows () { //save rows in recipients boq table
	 dict = [];
		$("#recipientsTab > tbody").find('input[name="record"]').each(function(){
	  		 dict.push({
			    	 "ID" : $(this).parent().parent().children('td[name="ID"]').children('input').val(),
			    	 "jobTitle" : $(this).parent().parent().children('td[name="jobTitle"]').children('input').val(),
			    	 "emailTo" : $(this).parent().parent().children('td[name="emailTo"]').children('input').val(),
			    	 "ccEmail" : $(this).parent().parent().children('td[name="ccEmail"]').children('input').val(),
			    	 "Condition" : $(this).parent().parent().children('td[name="Condition"]').children('input').val(),
					
					 });
	  		console.log("dict parameter CC-Email value is:  "+$(this).parent().parent().children('td[name="ccEmail"]').children('input').val()); 
				});		              
	}//end of saverows

 $("#saveButton").click(  function() {
 	console.log("testing Main Save Button");
 	saverows ();
 	
 	if ($("#notificationName").val()== '') {
		 alert('Name cannot be NULL');
		 return false;}
 	if ($("#subject").val()== '') {
 		 alert('Filters Subject cannot be NULL');
 		 return false;}

     if ($("#moduleName").val()== '') {
 		 alert('Module Name cannot be NULL');
 		 return false;}
      

     if ($("#sender").val()== '') {
 		 alert('Sender cannot be NULL');
 		 return false;}
      

 	 
 	  // validate modified date cannot be null

 				console.log("creationDate testing"+$("#creationDate").val());
 		
 			$.ajax({
 					type : "GET",
 					url : "${pageContext.request.contextPath}/NotificationFormSave",
 					dataType : "json",
 					data : {
 						"type": '${ListNo}', 
 						"id" : $("#id").val(),
 						"dictParameter" : dict,
 						"slctDelRecipient":slctDelRecipient,
 						"notificationName" : $("#notificationName").val(),
 						"subject" : $("#subject").val(),
 						"moduleName" :$("#moduleName").val(),
 						"channel" : $("#channel").val(),
 						"sendAlertOn" : $("#sendAlertOn").val(),
 						"sender" : $("#sender").val(),
 						"condition": $("#condition").val(),
 						"message": $("#message").val(),
 						"creationDate": $("#creationDate").val(),
 						"lastModifiedDate": $("#lastModifiedDate").val(),
 						"enabled" : $("#enabledVal").val(),
 						
 											
 				},
 					success : function(data) {

 						console.log("all done "+ data.alitest);
 						$('#lastModifieDate').val(data.lstmodifdate)
 						console.log("the Notification ID data is"+data.id);
 						$('#id').val(data.id);
 						var param ="${pageContext.request.contextPath}/NotificationFormView?id="+$("#id").val()+"&notifiList=notifiList";
 						location.replace(param);
 					
 					},
 					error : function(error) {
 						console.log("The error is " + error);
 					}
 				});

 	});





 $("#deleteButton").click(  function() {
 	 console.log('delete now');
 	 
 var id = document.getElementById("id").value;		
 	 
 		$.ajax({
 				type : "GET",
 				url : "${pageContext.request.contextPath}/NotificationFormViewDelete",
 				dataType : "json",
 				data : {
 					"id" :  $("#id").val()
 				},
 				success : function(data) {
 					//console.log("The returned data is " +data.testGoods);
 					location.replace("${pageContext.request.contextPath}/NotificationListView");
 				},
 				error : function(error) {
 					console.log("The error is " + error);
 				}
 			});
 			//window.history.back();
 	    	
 })


 	var prev = indexItm;
 	var nxt = indexItm;
 	if(navList != "" && navList != null){
 	for(var i=0;i<5;i++){
 		prev--;
 		if(navList[prev] != 'undefined' && navList[prev] != null && prev >= 0){
 			
 			$("#selectnav").append(new Option(navList[prev], "value"));
 		}			
 		}
 	$("#selectnav").append(new Option(navList[indexItm], "value"));
 	for(var i=0;i<5;i++){
 		nxt++;
 		
 		if(navList[nxt] != 'undefined' && navList[nxt] != null){
 			
 			$("#selectnav").append(new Option(navList[nxt], "value"));
 		}			
 		}
 	$(function () {
 		  $('#selectnav option').filter(function() {
 		    return this.textContent == navList[indexItm];
 		  }).prop('selected', true);
 		});		
 	
 	 $("#selectnav").change(function(){
 	        var selected = $('#selectnav').find(":selected").text();
 	        
 	        for(var i=0;i<navList.length;i++){
 				if(navList[i] == selected){
 					if(localStorage.getItem("notifiList") != null){
 						if(localStorage.getItem("notifiList").length != 0){
 							var param ="${pageContext.request.contextPath}/NotificationFormView?id="+navList[i];
 							window.location.href =param;
 							}		
 						}else{
 							var param ="${pageContext.request.contextPath}/NotificationFormView?id="+navList[i]+"&notifiList=notifiList";
 							window.location.href =param;
 							}
 					}
 				
 				}
 	    });
 $("#btnNext").click(function(){
 		if(!$("#btnNexta").hasClass("disabled")){
 			indexItm++;
 			if(localStorage.getItem("notifiList") != null){
 			if(localStorage.getItem("notifiList").length != 0){
 				var param ="${pageContext.request.contextPath}/NotificationFormView?id="+navList[indexItm];
 				window.location.href =param;
 				}		
 			}
 		else{
 			var param ="${pageContext.request.contextPath}/NotificationFormView?id="+navList[indexItm]+"&notifiList=notifiList";
 			window.location.href =param;
 			}
 		}
 		});
 	
 $("#btnPrv").click(function(){
 		if(!$("#btnPrva").hasClass("disabled")){
 		indexItm--;
 		if(localStorage.getItem("notifiList") != null){
 		if(localStorage.getItem("notifiList").length != 0){
 			var param ="${pageContext.request.contextPath}/NotificationFormView?id="+navList[indexItm];
 			window.location.href =param;
 		}
 		}else{
 			var param ="${pageContext.request.contextPath}/NotificationFormView?id="+navList[indexItm]+"&notifiList=notifiList";
 			window.location.href =param;
 			}
 		}
 		});
 	$("#label-1").text((indexItm+1)+"/"+navList.length);
 	
 	
 	

 	
 	
 	
 	
 	}	

 
     
	//ready function starts
$(document).ready(function() {	

////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
	$("#sender").autocomplete({
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
	                         console.log('data in $("#sender").autocomplete is :  '+ data.ListItemEmailAccounts);

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
	 	
	     
				$("#sender").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});
				$("#password").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});
			
	 //////////////////////////////////////////////////////////////////////////////		  	

	
});
	if (Enable==0)
	{
	$('input[name="enabled"]').prop('checked', false);
	document.getElementById("enabledVal").value="0";
		} 
	
	else 
		{
		$('input[name="enabled"]').prop('checked', true);
		document.getElementById("enabledVal").value="1";
		}
	
	 ////////////////////                              enabled                                    ////////////////////

 	$('input[type="checkbox"]').change(function(){
      checked=true;
 		 if($(this).is(":checked"))
 		 { 
 			 document.getElementById("enabledVal").value="1";
 		 }
 		 else
 		{ 
 			 document.getElementById("enabledVal").value="0";
 		 } 
 	
 //	});

 	 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	});
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 </script>
			 

				
			 </html>