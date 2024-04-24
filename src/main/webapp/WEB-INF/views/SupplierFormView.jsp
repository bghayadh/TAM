<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Supplier Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
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
	        		 
 	</style>    
  
            
</head>
<body>
	
<%-- 	<c:set var = "page" value = "purchasing"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="purchasing" scope="session"  />
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
							<span class="input-group-text" style="color:green">Supplier </span>
							<input type="text" id="suppcode" readonly  value="${supplierID}" class="form-control text-input"   />
												
						</div>

					</div>
			</div>
			
			<div class="pad col-md-3 hide-row"></div>
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Supplier</span>
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
						<input type="text" id="screatedate" readonly value="${screationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
						   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
								<div class="input-group-text">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="slstmodifdate" readonly value="${slastModifieddate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
		</div>
	</div>	
			
			
			
			<div class="hide-row col-md-3 pad "></div>
		
		<div class=" col-md-3 nextprvItems" style="min-width:300px">
			<label id="label-1" style="text-align: center;  margin-top: 5px ! important;"></label>
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
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/SupplierListView"'
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
		


<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-profile-tab" data-toggle="tab"
            href="#custom-tabs-one-profile" role="tab"
            aria-controls="custom-tabs-one-profile" aria-selected="false" style="color: gold;">ADDRESS / CONTACT</a></li>
             
            
            
       
            
            <li class="nav-item ml-auto">
            <button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
             <button type="button" 
				onclick='window.location.href = "${pageContext.request.contextPath}/SupplierFormView?type=addNew"'
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
	<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

	<p></p>
    
	<div class="row">
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Supplier Name</span>
						<input type="text" id="suppname" value="${supplierName}"  class="form-control text-input" />
					</div>
				</div>
		</div>
		
		
		<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Supplier Country</span>
				<input type="text" id="suppcountry"
				 value="${sCountry}" class="form-control text-input" />
			</div>							
		</div>
		
		<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Supplier Category</span>
				<input type="text" id="suppcat" value="${supplierCategory}" style="width:700px;" class="ui-widget ui-widget-content ui-corner-all"/>
			</div>
		</div>

 	</div>
 	
	<div class="row">
		
		
		<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Vendor number</span>
				<input type="text" id="svendorNb" value="${svendorNb}" class="form-control text-input" />
			</div>							
		</div>
		
		
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width:180px;">Supplier Tax</span>
					<input type="text" id="supptxid" value="${supplierTaxid}" class="form-control text-input"/>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width:195px;">Credit limit</span>
					<input type="text" id="suppcrdlimit" value="${supplierCreditlimit}" class="form-control text-input" />
				</div>
			</div>
		</div>
	
  	</div>
  	<p></p>

	
	<div class="row">
	<div class="col-md-4">
			<div class="form-group">
				
				<div class="input-group-prepend">
				<span class="input-group-text" style="width:125px;">
	             	 <label class="form-check-label" style="margin-top:-5px;">Disabled</label>
              		 <input type="checkbox" id="suppdisables" ${sDisabled}/></span>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<div class="row">
		<div class="col-md-8">
			<div class="input-group-prepend">
				<span class="input-group-text">Supplier Description</span>
			</div>							
		</div>
	</div>

	<div class="row">
		<div class="col-md-5">
			<div class="input-group-prepend">
				<textarea name="suppdescrip" cols="220" rows="8" id="suppdescrip"> ${supplierDescription} </textarea>
			</div>							
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
						<span class="input-group-text">Website</span>
						<input type="text" id="suppweb" value="${sWebsite}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Address 1</span>
						<input type="text" id="suppadd1" value="${supplierAddress1}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Address 2</span>
						<input type="text" id="suppadd2" value="${supplierAddress2}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	</div>
	<div class="row">
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Phone</span>
						<input type="text" id="suppphone" value="${sPhone}" class="form-control text-input"  />
					</div>
				</div>
		</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text">Mobile</span>
					<input type="text" id="suppmobile" value="${sMobile}" class="form-control text-input"/>
					</div>
				</div>
		</div>
	
		<div class="col-md-4">
				<div class="input-group-prepend">
				<span class="input-group-text">Email</span>
				<input type="text" id="suppemail" value="${sEmail}" class="form-control text-input" />
				</div>							
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Contact Person</span>
				<input type="text" id="suppcontactp" value="${sContactperson}" class="form-control text-input"  />
			</div>
		</div>
		
	
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

/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
// $("#sendEmail").on("click", function () {
// console.log("Helloooo in sendEmail onClick");
// $("#popUpDiv").fadeIn();

// });

// $("#cancelButton").on("click", function () {
// $("#email").val('');
// $("#password").val('');
// $("#emailTo").val('');
// $("#ccmail").val('');
// $("#subject").val('');
// $("#message").val('');
// $("#popUpDiv").fadeOut();
// });

// $("#send").on("click", function () {
// if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
// {
// alert("All Fields are required");
// }
// else{
// $("#popUpDiv").fadeOut();
// }

// });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}



 			$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
   

 			



			 
 $("#saveButton").click(  function() {
	 
	 

	 // validate credit limit to be number
	 if (($. isNumeric( $("#suppcrdlimit").val()))== false) {
	 alert('Credit Limit is  not Numeric');
	 return false;}
	 
	 // validate creatd date cannot be null
	 val =$("#screatedate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
        }
	 
	  // validate modified date cannot be null
	 val =$("#slstmodifdate").val();
     val1 = Date.parse(val);
     //console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
        }
	 
	 var supplierID = document.getElementById("suppcode").value;
	 var screationDate = document.getElementById("screatedate").value;
	 var slastModifieddate = document.getElementById("slstmodifdate").value;
	 var supplierName = document.getElementById("suppname").value;
	 var supplierDescription = document.getElementById("suppdescrip").value;
	 var supplierCategory = document.getElementById("suppcat").value;
	 var sWebsite = document.getElementById("suppweb").value;
	 var svendorNb = document.getElementById("svendorNb").value;
	 var sCountry = document.getElementById("suppcountry").value;
	 var supplierTaxid = document.getElementById("supptxid").value;
	 var supplierCreditlimit = document.getElementById("suppcrdlimit").value;
	 var supplierAddress1 = document.getElementById("suppadd1").value;
	 var supplierAddress2 = document.getElementById("suppadd2").value;
	 var sPhone = document.getElementById("suppphone").value;
	 var sMobile = document.getElementById("suppmobile").value;
	 var sEmail = document.getElementById("suppemail").value;
	 var sContactperson = document.getElementById("suppcontactp").value;
	 var checkBox = document.getElementById("suppdisables");
	 var sDisabled ;
	 if (checkBox.checked == true){
		  sDisabled = '1';
		  } else
			  {
			  sDisabled = '0';}
	  

     
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/SupplierFormSave",
			dataType : "json",
			data : {
				"supplierID" : $("#suppcode").val(),
				"screationDate" : $("#screatedate").val(),
				"slastModifieddate" : $("#slstmodifdate").val(),
				"supplierName" : $("#suppname").val(),
				"supplierDescription" : $("#suppdescrip").val(),
				"supplierCategory" : $("#suppcat").val(),
				"sWebsite" : $("#suppweb").val(),
				"sCountry" : $("#suppcountry").val(),  
				"supplierTaxid" : $("#supptxid").val(),
				"supplierCreditlimit" : $("#suppcrdlimit").val(),
				"supplierAddress1" : $("#suppadd1").val(),
				"supplierAddress2" : $("#suppadd2").val(),
				"svendorNb" : $("#svendorNb").val(),
				"sPhone" : $("#suppphone").val(),
				"sMobile" : $("#suppmobile").val(),
				"sEmail" : $("#suppemail").val(),
				"sContactperson" : $("#suppcontactp").val(),
				"sDisabled" : sDisabled,
				"email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(),
			},
			success : function(data) {
				$('#slstmodifdate').val(data.slastModifieddate);
				$("#suppcode").val(data.supplierID);
				var param ="${pageContext.request.contextPath}/SupplierFormView?supplierID="+$("#suppcode").val()+"&NavAction=4";
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
 $("#deleteButton").click(  function() {
	 console.log('delete now');
	 var supplierID = document.getElementById("suppcode").value;
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/SupplierDelete",
			dataType : "json",
			data : {
				"ID" : [$("#suppcode").val()]
			},
			success : function(data) {
			    location.replace("${pageContext.request.contextPath}/SupplierListView");
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
 </script>
 
 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css"/>
 
 <style>
 
 .ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					width: 350px;
				    z-index:9999;
					
	        		}

    	</style>
 <script type='text/javascript'>
	$(document).ready(function() {

	
				
		function customRenderItem(ul, item) {
			var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
				result = $('<li class="ui-menu-item" role="menuitem"></li>')
				.data('item.autocomplete', item)
				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
				.appendTo(ul);

			return result;
		}

       	var columns = [{name: 'Supp Code', minWidth: '150px'}, {name: 'Supp Name', minWidth:'150px'}, {name: 'Supp crdlimit', minWidth:'100px'}];	
				
		$("#suppcat").mcautocomplete({
		showHeader: true,
		columns: columns,
        source: function(request, response) {
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllSuppliers',
	                 data: {
							"supplierCategory;" : $("#suppcat").val(),
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListItemSupplier);
	                     
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 4, scroll:true,		
               
        
		select: function(event, ui) {
				suppcat.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2]: '');
				return false;
			}
    }).bind('focus', function(event, ui) {       
      if(!$(this).val().trim())
            $(this).keydown();
			
	});

		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${supplierCount}' != "addNew"){

							
					var supplierCount = ${supplierCount};
					
					if(($("#suppcode").val()) != "" && ($("#suppcode").val()) != null){

					if(SelectedIndex === supplierCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									
									var param ="${pageContext.request.contextPath}/SupplierFormView?supplierID="+$("#suppcode").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/SupplierFormView?supplierID="+$("#suppcode").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/SupplierFormView?supplierID="+$("#suppcode").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/SupplierFormView?supplierID="+$("#suppcode").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}
					$("#label-1").text((SelectedIndex)+"/"+supplierCount);

					 $("#selectnav").autocomplete({
			    			
			    		    source: function(request, response) {
			    			    
			    		             $.ajax({
			    		                 type: "GET",
			    		                 contentType: "application/json; charset=utf-8",
			    		                 url: '${pageContext.request.contextPath}/GetAllSupplierName',
			    		                 data: {
			    								"supplierName" : $("#selectnav").val(),
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
			    						
			    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
			    						
			    						var param ="${pageContext.request.contextPath}/SupplierFormView?supplierID="+(ui.item[2])+"&NavAction=2";
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
			    	
		  
	
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
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
				
		 //////////////////////////////////////END OF EMAIL AUTOCOMPLETE////////////////////////////////////////		  	
	  
		});
	
	
</script>
 
 
	
 </html>
