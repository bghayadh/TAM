<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>CIP Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	
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
         
    
    <style>
    
    
     /*Doaa's popup Email Div'*/
    
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
				
				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				 
				  
				}
				
				#domaine
				{
					width: 200px;
				}
				
				.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					width: 350px;
				    z-index:9999;
					
	        		}
	        		
				/*.ui-autocomplete {
            	max-height: 300px;
				overflow-y: auto; /* prevent horizontal scrollbar */
				/*overflow-x: hidden; /* add padding to account for vertical scrollbar */
				/*padding-right: 100px;
        	} */
	        		 
 	</style>
            
</head>
<body>

<%-- 	<c:set var = "page" value = "inventory"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="inventory" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
			<p></p>
			</div>
		
		</div>
		
			
		<div class="row second">
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Capital In Progress</span>
							<input type="text" id="arcode"  value="${cipID}" readonly class="form-control text-input"   />
							
														
						</div>

					</div>
			</div>

			 <div class="pad col-md-3 hide-row"></div>   
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other CIP</span>
						<input type="text" id="selectnav" value="${selectnav}"
						style="width: 430px" class="form-control text-input" />		
				</div>
			</div>
		</div>
			
			
			<div  class="col-md-6 text-right"  >
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
						</div> 
			
			</div>
			
		
			<div class="row">
			
			
			
			<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
					<input type="text" id="createdate" readonly value="${cipcreatedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${ciplastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
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
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/CapitalInProgressListView"'
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
<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">DESCRIPTION</a></li>
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-profile-tab" data-toggle="tab"
            href="#custom-tabs-one-profile" role="tab"
            aria-controls="custom-tabs-one-profile" aria-selected="false" style="color: gold;">MEASURMENT</a></li>
             
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-messages" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">TYPE</a></li> 
             
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-relatedorders" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">RELATED ORDERS</a></li> 
       
            
            <li class="nav-item ml-auto">
            
            <button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
                       <button type="button" 
				onclick='window.location.href = "${pageContext.request.contextPath}/CapitalInProgressFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
						</button>  
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
    
<div class="row">
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Item code</span>
							<input type="text" id="itmcode"  value="${cipitemCode}" style="width:600px;" class="form-control text-input"/>
						</div>
					</div>
	</div>
	
	
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Item Name</span>
						<input type="text" id="itmname" value="${cipitemName}"  class="form-control text-input" style="width:600px;"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Category</span>
					<input type="text" id="itmcat" value="${cipitemCategory}" style="width:700px;" class="form-control text-input"/>
				</div>
			</div>
	</div>
	
	
	
 </div>
	<div class="row">
	
	<div class="col-md-4">
						<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Warranty Period</span>
					<input type="text" id="warrantyper" value="${cipwarrantyPeriod}" class="form-control text-input"  />
				</div>					
	</div>
	
  </div>
  
  
  <div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">End Of Life</span>
					<input type="text" id="endoflive" value="${cipendOfLife}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
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
				<div class="input-group-prepend">
					<span class="input-group-text">Valuation Rate</span>
				<input type="text" id="valuarate" value="${cipvaluationRate}" class="form-control text-input"  />
				</div>
			</div>
	</div>
  
  
	
	<p></p>	
	
	
	
	
	</div>
	
	
  <div class="row">
  <div class="col-md-6">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Description</span>
				</div>							
	</div>
	<div class="col-md-6">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Image</span>
					<input type="text" id="itmimage" value="${cipitemImage}" class="form-control text-input" />
			</div>

				
		</div>
</div>

    <div class="row">
  <div class="col-md-6">
<textarea name="itmdescrip"  cols="90" rows="8" id="itmdescrip" class="form-control input-text"> ${cipitemDescription} </textarea>
</div>
</div>
	</div>
	<div class="tab-pane fade" id="custom-tabs-one-profile"
		role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab">
	
<p></p>


<div class="row">

	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Unit</span>
						<input type="text" id="unt" value="${cipunit}"  class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight</span>
						<input type="text" id="weit" value="${cipweight}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight UOM</span>
						<input type="text" id="weituom" value="${cipweightUOM}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Length</span>
						<input type="text" id="len" value="${ciplength}" class="form-control text-input"  />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Width</span>
					<input type="text" id="widths" value="${cipwidth}" class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="input-group-prepend">
					<span class="input-group-text">Height</span>
				<input type="text" id="heit" value="${cipheight}" class="form-control text-input" />
				</div>							
	</div>
	
	<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Size UOM</span>
					<input type="text" id="siseuom" value="${cipsizeUOM}" class="form-control text-input"  />
			</div>

				
		</div>
	
</div>
	</div>

	<div class="tab-pane fade" id="custom-tabs-one-messages"
		role="tabpanel" aria-labelledby="custom-tabs-one-messages-tab">

<p></p>

<div class="row">
	<div class="col-md-3">
			<div class="input-group-prepend">
				<span class="input-group-text">Domain</span>
				<!--  <input type="text" id="domaine" value="${cipdomain}" class="form-control text-input" /> -->
				<select name="cars" id="domaine" class="form-control text-input">
    				<option value="mad" <c:if test = "${cipdomain =='mad'}"> selected </c:if>>Mobile Access Domain</option>
    				<option value="itd" <c:if test = "${cipdomain =='itd'}"> selected </c:if>>Transport Domain</option>
    				<option value="icd" <c:if test = "${cipdomain =='icd'}"> selected </c:if>>Core Domain</option>
    				<option value="ipd" <c:if test = "${cipdomain =='ipd'}"> selected </c:if>>Passive Domain</option>
  				</select>
			</div>

				
		</div>
</div>
<p></p>
<div class="row">	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Cable Type</span>
						<input type="text" id="cbltype" value="${cipcableType}" class="form-control text-input" />
						</div>
					</div>
	</div>


	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Type</span>
					<input type="text" id="itmtyp" value="${cipitemType}" class="form-control text-input"/>
				</div>
			</div>
	</div>
</div>
	
	<div class="row">
		<div class="col-md-12">
				<div class="form-group">
					<div class="checkboxes">
					  	<span>	 
		              		 <input type="checkbox" id="servise1" ${cipservice} />Service
		              		 <input type="checkbox" id="disables" ${cipdisabled}/> Disabled
	   	              		 <input type="checkbox" id="techg2g" ${ciptech2G}/> 2G
							 <input type="checkbox" id="techg3g" ${ciptech3G}/> 3G
		              		 <input type="checkbox" id="techg4g" ${ciptech4G}/> 4G
	              		 </span>
					</div>
				</div>
		</div>
	</div>
	

</div>



	    
    <div class="tab-pane fade" id="custom-tabs-one-relatedorders" role="tabpanel" aria-labelledby="custom-tabs-one-relatedorders-tab">
		<p></p>
		<div class="row">
		<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Purchase Order</span>
							<input type="text" id="poID"  value="${poID}" readonly class="form-control text-input"   />
						</div>
					</div>
		</div>
		<div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Total QTY</span>
				<input type="text" id="totalQty" value="${totalQty}" readonly class="form-control text-input" />
				</div>							
		</div>
		
		<div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Supplier ID</span>
				<input type="text" id="supplierID" value="${supplierID}" readonly class="form-control text-input" />
				</div>							
		</div>
		
		<div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Supplier Name</span>
				<input type="text" id="supplierName" value="${supplierName}" readonly class="form-control text-input" />
				</div>							
		</div>
		
		
		
		</div>
				<!--  create table from purchaseOrderItem    -->

		<div> 
				<form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						               
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
						                <th>GR</th>
						                <th>PR</th>
						                <th>AR</th>
						                 <th>CIP</th>
						                <th>FAR</th>
						                
						                <th>PrOrditm</th>
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
						
                   </form>
		</div>

    </div>






</div>


</div>
        
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
 
 
 <script>

 /////////////////////////////////////////
 
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
	
     		$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
   
			 $("#datetimepicker3").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
			$("#itmdescrip").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
 
              $("#itmcat").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
              $("#itmcode").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
			$("#domaine").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
			
			

				
 
 $("#saveButton").click(  function() {
	 console.log('saved now');
	 
	  // validate item code cannot be null
	 /*if ($("#arcode").val()== '') {
	 alert('CIP code cannot be Null');
	 return false;}
	 */
	 // validate item code cannot be null
	 if ($("#itmcode").val()== '') {
	 alert('Itemcode cannot be Null');
	 return false;}
	 
	 
	 // validate type of valuation rate
	 if (($. isNumeric( $('#valuarate'). val()))== false) {
	 alert('valuation Rate is  not Numeric');
	 return false;}
	 
	 // validate creatd date cannot be null
	 val =$("#createdate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
        }
	 
	  // validate modified date cannot be null
	 val =$("#lstmodifdate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
        }
        
     // validate end of life date cannot be null
     val = $("#endoflive").val();
     val1 = Date.parse(val);
     console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert('End of life Date is not valid');
          return false;
        }
		
	 var cipID = document.getElementById("arcode").value;		
	 var cipitemCode = document.getElementById("itmcode").value;
	 var cipcreatedDate = document.getElementById("createdate").value;
	 var ciplastModifiedDate = document.getElementById("lstmodifdate").value;
	 var cipitemName = document.getElementById("itmname").value;
	 var cipunit = document.getElementById("unt").value;
	 var cipitemDescription = document.getElementById("itmdescrip").value;
	 var cipdomain = document.getElementById("domaine").value;
	 var cipitemCategory = document.getElementById("itmcat").value;
	 var cipitemType = document.getElementById("itmtyp").value;
	 var cipcableType = document.getElementById("cbltype").value;
	 var cipweight = document.getElementById("weit").value;
	 var cipweightUOM = document.getElementById("weituom").value;
	 var ciplength = document.getElementById("len").value;
	 var cipwidth = document.getElementById("widths").value;
	 var cipheight = document.getElementById("heit").value;
	 var cipsizeUOM = document.getElementById("siseuom").value;
	 var cipcheckBox = document.getElementById("servise");
	 var cipservice ;
	  if (servise1.checked == true){
		  cipservice = '1';
		  } else
			  {cipservice = '0';}
	 var cipendOfLife = document.getElementById("endoflive").value;
	 var cipvaluationRate = document.getElementById("valuarate").value;
	 var checkBox2 = document.getElementById("disables");
	 var cipdisabled ;
	 if (disables.checked == true){
		 cipdisabled = '1';
		  } else
			  {cipdisabled = '0';}
	 
	 var cipitemImage = document.getElementById("itmimage").value;
	 var cipwarrantyPeriod = document.getElementById("warrantyper").value;
     var checkt2 = document.getElementById("techg2g");
	 var ciptech2 ;
	 if (techg2g.checked == true){
		 ciptech2 = '1';
		  } else
			  {ciptech2 = '0';}
	 
     var checkt3 = document.getElementById("techg3g");
	 var ciptech3 ;
	 if (techg3g.checked == true){
		 ciptech3 = '1';
		  } else
			  {ciptech3 = '0';}
			  
     var checkt4 = document.getElementById("techg4g");
	 var ciptech4 ;
	 if (techg4g.checked == true){
		 ciptech4 = '1';
		  } else
			  {ciptech4 = '0';}

     
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/CapitalInProgressFormSave",
			dataType : "json",
			data : {
			     
			    "cipID" : $("#arcode").val(),
				"cipitemCode" : $("#itmcode").val(),
				"cipcreatedDate" : $("#createdate").val(),
				"ciplastModifiedDate" : $("#lstmodifdate").val(),
				"cipitemName" : $("#itmname").val(),
				"cipunit" : $("#unt").val(),
				"cipitemDescription" : $("#itmdescrip").val(),
				"cipdomain" : $("#domaine").val(),
				"cipitemCategory" : $("#itmcat").val(),  
				"cipitemType" : $("#itmtyp").val(),
				"cipcableType" : $("#cbltype").val(),
				"cipweight" : $("#weit").val(),
				"cipweightUOM" : $("#weituom").val(),
				"ciplength" : $("#len").val(),
				"cipwidth" : $("#widths").val(),
				"cipheight" : $("#heit").val(),
				"cipsizeUOM" : $("#siseuom").val(),
				"cipservice" : cipservice,	 
				"cipendOfLife" : $("#endoflive").val(),
				"cipvaluationRate" : $("#valuarate").val(),
				"cipdisabled" : cipdisabled,	
				"cipitemImage" : $("#itmimage").val(),
				"cipwarrantyPeriod" : $("#warrantyper").val()	,
				"ciptech2G" : ciptech2,	
				"ciptech3G" : ciptech3,	
				"ciptech4G" : ciptech4,
				"supplierID" : $("#supplierID").val(),
				"supplierName" : $("#supplierName").val(),
				"poID"  : $("#poID").val(),
				
			},
			success : function(data) {
				console.log("The returned data is " +data.BassamTest);
				arcode.value=data.CIPID;
				$('#lstmodifdate').val(data.ciplastModifiedDate);
				var param ="${pageContext.request.contextPath}/CapitalInProgressFormView?cipID="+$("#arcode").val()+"&NavAction=2";
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		}); 
		
	
 })
 $("#deleteButton").click(  function() {
	 console.log('delete now');
	 var cipID = document.getElementById("arcode").value;
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/CapitalInProgressFormViewDelete",
			dataType : "json",
			data : {
				"cipID" : $("#arcode").val()
			},
			success : function(data) {
			    location.replace("${pageContext.request.contextPath}/CapitalInProgressListView");
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
 

 </script>
 
 
 

       		
    	
 <script type='text/javascript'>
 if ('${docStatus}' == "addNew") {
		$("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});	
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");
	}
     
        
 
	$(document).ready(function() {
		
		 console.log("The docstatus is " +'${docstatus}');			
		function customRenderItem(ul, item) {
			var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
				result = $('<li class="ui-menu-item" role="menuitem"></li>')
				.data('item.autocomplete', item)
				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
				.appendTo(ul);

			return result;
		}


///////////////////////Item Code Autocomplete//////////////////////////////////////////////////////
$("#itmcode").autocomplete({
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
               alert("Error");
           }
       });
	}, minLength:0, maxShowItems: 40, scroll:true,		
              
       
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0]  : '');
		document.getElementById("itmname").value= ui.item[1];
		document.getElementById("itmcat").value= ui.item[5];
		return false;
		}
			
	    }).autocomplete("instance")._renderItem = function(ul, item) {
			return $("<li class='each'>")
		    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		        item[1] + "</span><br><span class='desc'>" +
		        item[0] + "</span></div>")
		        
		    .appendTo(ul);
       	};
		$("#itmcode").focus(function(){
  	        	if (this.value == ""){
  	            	$(this).autocomplete("search");
  	        	}						
		});



//////////////////////Item Category Autocomplete//////////////////////////////////////////////////////////
$("#itmcat").autocomplete({
						
						 source: function(request, response) {

					        	
						             $.ajax({
						                 type: "GET",
						                 contentType: "application/json; charset=utf-8",
						                 url: '${pageContext.request.contextPath}/GetAllCategory',
						                 data: {
						                	 "itemCategory" : $("#itmcat").val(),
										 },
						                 dataType: "json",
						                 success: function (data) {
						                     if (data != null) {
						                         response(data.ListItemCategory);
						                         //console.log('data is ;'+ data.ListItemWarehouse);
						                         //colors = data.ListItemCategory;
						                         //console.log('colors ;'+ colors);
						                     
						                     }
						                 },
						                 error: function(result) {
						                     alert("Error");
						                 }
						             });
						         }, minLength:0, maxShowItems: 40, scroll:true,		
					               
					        
							select: function(event, ui) {
								this.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2]: '');
								
								
								
								
									return false;
										}

					       
									
							    }).autocomplete("instance")._renderItem = function(ul, item) {
						            return $("<li class='each'>")
					                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					                    item[0] + "</span><br><span class='desc'>" +
					                   item[1] + "</span><br><span class='desc'>" +
					                    item[2] + "</span></div>")
					                    
					                .appendTo(ul);
					        	};
								$("#itmcat").focus(function(){
					   	        	if (this.value == ""){
					   	            	$(this).autocomplete("search");
					   	        	}						
								});



//////////////Item Name Autocomplete//////////////////////////////////////////////////
$("#itmname").autocomplete({
		
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
                alert("Error");
            }
        });
        }, minLength:0, maxShowItems: 40, scroll:true,		
               
        
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[1]  : '');
			document.getElementById("itmcode").value= ui.item[0];
			document.getElementById("itmcat").value= ui.item[5];
			return false;
		}
		    }).autocomplete("instance")._renderItem = function(ul, item) {
				return $("<li class='each'>")
			    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			    		item[0] + "</span><br><span class='desc'>" +
			         item[1] + "</span></div>")
			        
			    .appendTo(ul);
        	};
			$("#itmname").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});


// to add New in orange of a new record using add button from capitalInProgressList
    if ('${docStatus}' != "addNew" ){
	
		
	console.log("The docstatus is " +'${docstatus}');
	
		
	
	Cipitem = ${CIPTable};
	
	
	var sumTotal  ;
	var sumQty ;
	var slctsave = [];
	var slctDel = [];
    var newslct =[];
    var ressplit;
    var itmcode;
    var itmname;
    var itmqty;
    var itmrate;
    var itmdisc;
    var itmtax1;
    var itmnetrate;
    var itmtot;
    var itmtotAT;
    var itmnewarehouse;
    var prGR;
    var prPR;
    var prAR;
    var prCIP;
    var itmnordid;
    
	    
    // get all colmns count per row
		function count(array){
            var c = 0;
            for(i in array) // in returns key, not object
                if(array[i] != undefined)
                    c++;
            return c;
     }
  
    // Fill tables rows from DB
	var len=Cipitem.length;
	console.log("The lenght is " + len);
	var itemCodeId;   
	var collen= count(Cipitem[0]);
	console.log("The count is " + collen);
        var i =0;
        var itemRow = "<tr>";
        //itemRow= itemRow + "<td><input type='checkbox' name='record'></td>"
        //console.log(itemRow);
        for (j = 0; j < collen; j++) {
        	var tt1= Cipitem[i][j];
        	if (j == 0) {
     			itemRow =itemRow + "<td><input type='text' name='itmCode' value='" + tt1 +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control' readonly/></td>";
     		}
     		if (j == 9) {
     		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		if (j == 8) {
     		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		if (j == 7) {
     		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }

     		if (j == 1) {
     			itemRow =itemRow + "<td><input type='text' name='itmModel' value='" + tt1 +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control' readonly/></td>";
     		}
     		if (j == 2) {
     			itemRow =itemRow + "<td><input type='text' name='itmPartNo' value='" + tt1 +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control' readonly/></td>";
     		}
 		    
     		if (j == 3) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		if (j == 4) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		if (j == 5) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		if (j == 6) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		//if (j == 8) {    
			//	itemRow =itemRow + "<td><input type='text' name='itmWare' value='" + tt1 +"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all' readonly/></td>";
     		 //   }
     	   if (j ==10 ) {
     		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }  
        	if (j == 11) {
     		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }    
     		    if (j == 12) {
     		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		    if (j == 13) {
     		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		if (j == 14) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		
     		if (j == 15) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
     		    }
     		
     		    
    		
        }
        itemRow =itemRow + "</tr>";
        $("#bisotab > tbody").append(itemRow);
	}
	else {
		
	console.log("the add new option and the value of the doc status is "+'${docStatus}');
        // set status to new and green while new record
         $("#formStatus").text("New");
		 $('.dot').css({"background-color" : "orange"});
     }


		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${CIPCount}' != "addNew"){

							
					var CIPCount = ${CIPCount};
					
					if(($("#arcode").val()) != "" && ($("#arcode").val()) != null){

					if(SelectedIndex === CIPCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									
									var param ="${pageContext.request.contextPath}/CapitalInProgressFormView?cipID="+$("#arcode").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/CapitalInProgressFormView?cipID="+$("#arcode").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/CapitalInProgressFormView?cipID="+$("#arcode").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/CapitalInProgressFormView?cipID="+$("#arcode").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}
////Get All CIP 
$("#label-1").text((SelectedIndex)+"/"+CIPCount);

					 $("#selectnav").autocomplete({
			    			
			    		    source: function(request, response) {
			    			    
			    		             $.ajax({
			    		                 type: "GET",
			    		                 contentType: "application/json; charset=utf-8",
			    		                 url: '${pageContext.request.contextPath}/GetAllCIP',
			    		                 data: {
			    								"CIP" : $("#selectnav").val(),
			    						 },
			    		                 dataType: "json",
			    		                 success: function (data) {
			    		                     if (data != null) {
			    		                         response(data.listCIP);
			    		                     }
			    		                 },
			    		                 error: function(result) {
			    		                     alert("Error");
			    		                 }
			    		             });
			    		         }, minLength:0, maxShowItems: 40, scroll:true,

			    					select: function(event, ui) {
			    						
			    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
			    						var param ="${pageContext.request.contextPath}/CapitalInProgressFormView?cipID="+(ui.item[0])+"&NavAction=2";
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
			    	
		
	  	
});
</script>
 
 

	
 </html>
