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
	<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
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
margin-top: 10px;
				  
}								
.hide-row {
	display: none;
}				
 </style>       
            
</head>
<body>
<%-- 	<c:set var = "page" value = "inventory"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="inventory" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
     <p></p>
     
    <div class="container-fluid">
		<div class="row second">
		   <div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
					     <span class="input-group-text">Serial Number ID</span>
						<input type="text" id="serialNumberID" value="${serialNumberID}" class="form-control text-input" readonly/>
                        </div>
					</div>
			</div>	
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:190px;" class="input-group-text">Status</span>
				<select id="ordstat" class="form-control">
								<option value="draft" <c:if test = "${ordStatus =='draft'}" > selected </c:if> >Draft</option>
								<option value="approved" <c:if test = "${ordStatus =='approved'}" > selected </c:if>>Approved</option>
				</select></div>							
			</div>
		<div class="pad col-md-3 hide-row"></div>   
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Serial Number</span>
						<input type="text" id="selectnav" value="${selectnav}" style="width: 430px" class="form-control text-input" />	
				</div>
			</div>
		</div>						
		<div  class="col-md-3 text-right"  >
				    <i>&nbsp</i><span class="dot"></span>
					<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;" >Saved</label>
		</div> 			
		</div>
	<div class="row">		
		 <div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
					     <span class="input-group-text">Serial Number</span>
						<input type="text" id="serialNumber" value="${serialNumber}" class="form-control text-input" />
                        </div>
					</div>
			</div>						
		<div class="col-md-4"><span id="serialCheckValidity" ></span></div>
	</div>	
		<div class="row">							
	      <div class="col-md-3">
		    <div class="form-group">
	            <div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span style="width: 170px;" class="input-group-text">Creation Date</span>
					<input type="text" id="creationDate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
			</div> </div> </div>			
				<div class="col-md-3">
					<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text" style="width: 210px;" >Last Modified Date</span>
					<input type="text" id="lastModifiedDate" readonly value="${lastModifieddate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
			</div>
		</div> 
		<div class="hide-row col-md-3 pad "></div>
		<div class=" col-md-3 nextprvItems">
			<label id="nextPrevCountlabel" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
			  		<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;">
							<a style="margin-left: 3px;width: 51px;height:40px" id="btnFirst" href="#" class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;">
							<a style="width: 51px;height:40px" id="btnPrva" href="#" class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item" style="padding-right: 0px ! important;">
							<a style="width: 51px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#" class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;">
							<a style="width: 51px;height:40px" id="btnLast" href="#" class="btn btn-success next">&raquo;</a></li>		
					</ul>
				</nav>
		</div>
		<div class="col-md-3" style="text-align: right;">
				<div class="btn-group pull-right">
		 			<div class="glyph"> 
		 			  	<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" onclick='window.location.href = "${pageContext.request.contextPath}/SerialNumberFormView?type=addNew"'	data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i></button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip" onclick='window.location.href = "${pageContext.request.contextPath}/SerialNumberListView"' data-placement="top" title="List View"><i class="fa fa-bars"></i></button>
						<button type="button" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="Search"><i class="fa fa-search"></i></button>
			        </div>  
		        </div>
			</div>
			</div>
			<p></p><p></p>
        <div class="row">
                <div class="col-12 col-sm-12 col-lg-12">
                
		<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: -20px;">
            <li class="nav-item"><a class="nav-link active" id="custom-tabs-one-home-tab" data-toggle="tab" href="#custom-tabs-one-home" role="tab" aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            <li class="nav-item ml-auto">
            	<button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
            		   <button type="button" 
				onclick='window.location.href = "${pageContext.request.contextPath}/SerialNumberFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
						</button>  
				<button type="button" id="deleteSerialNumber" class="btn btn-primary BtnActive"><i class="fa fa-trash"></i> Delete</button>  
				<button type="button" id="saveSerialNumber" class="btn btn-primary BtnActive"><i class="fa fa-save"></i> Save</button>  
			</li>
	     </ul>
	</div>
	</div>
	</div>

<p></p> 

	<div class="container-fluid">
  	    <div class="row">
		   <div class="col-sm-6">
						<div class="input-group-prepend">
						<span class="input-group-text">Item Code</span>
						<input type="text" id="itemCode" value="${itemCode}" style="width:675px; height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all"/>							
					</div>
				</div>
	<div class="col-sm-6">
				<div class="form-group">
						<div class="input-group-prepend">
						<span class="input-group-text">Item Name</span>
							<input type="text" id="itemName"  value="${itemName}" style="width:675px; height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all"   />
						</div>
				</div>
			</div>
	</div> </div>
<p></p>
<div class="container-fluid">
  	    <div class="row">
		   <div class="col-sm-6">
		   <div class="input-group-prepend">
		   		<span class="input-group-text">Item Model</span>
				<input type="text" id="itemModel"  value="${itemModel}" style="width:675px; height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all"   />
				</div>
			</div>
	<div class="col-sm-6">
				<div class="form-group">
						<div class="input-group-prepend">
						<span class="input-group-text">Item Part No</span>
						<input type="text" id="itemPartNumber" value="${itemPart}" style="width:675px;height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all"/>
					</div>
				</div>
			</div>
	</div>
<p></p>
		<div class="row">			
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Supplier ID</span>
							<input type="text" id="supplierId" value="${supplierId}" style="width:675px;height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all" />
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
						<div class="input-group-prepend">
						<span class="input-group-text">Supplier Name</span>
						<input type="text" id="supplierName"  value="${supplierName}" style="width:675px;height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all"/>
				</div>
				</div>
			</div>
	</div>
<p></p>					 	
<p></p>
		<div class="row">			
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Purchase Order ID</span>
							<input type="text" id="purchOrderId"  value="${poId}"  style="width:675px;height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all"  />
					</div>
				</div>
			</div>
				<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Purchase Order Item ID</span>
							<input type="text" id=purchOrderItemId  value="${poItemId}"  style="width:675px;height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all" readonly />
					</div>
				</div></div>
	</div>
	<p></p>
	 <div class="row">	
	 <div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Goods Received ID</span>
							<input type="text" id="goodsRcvId"  value="${grId}"   style="width:675px;height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all"  />
					</div>
				</div>
			</div>						
				<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Goods Received Item ID</span>
							<input type="text" id="goodsRcvItemId"  value="${grItemId}"  style="width:675px;height:38px;" class="form-control text-input ui-widget ui-widget-content ui-corner-all" readonly />
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
	<p></p><div class="form-group"  style=" margin-left:100px; ">
			<button type="button" id="send" class="btn btn-primary BtnActive"><i class="fa fa-paper-plane" ></i> Send</button>
             <button type="button" id="cancelButton" class="btn btn-primary BtnActive"><i class="fa fa-times"></i> Cancel</button>
			</div>
	</div>
	</div>
	</div>			
	 </body>

<script type="text/javascript"> 
	 
$("#sendEmail").on("click", function () {
	$("#popUpDiv").fadeIn();
});

$("#cancelButton").on("click", function () {
	$("#email").val('');
	$("#password").val('');
	$("#emailTo").val('');
	$("#ccmail").val('');
	$("#subject").val('');
	$("#message").val('');
	$("#popUpDiv").fadeOut();
});

$("#send").on("click", function () {
	if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' ){
		alert("Missing Fields");
	}
	else{
		$("#popUpDiv").fadeOut();
	}

});

$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});
	 
if ('${type}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");	
}

if('${SelectedIndex}' != "addNew"){
	var selectedIndex = ${SelectedIndex};
	if('${serialNoCount}' != "addNew"){		
		var serialNoCount = ${serialNoCount};
		if( ($("#serialNumber").val()) != "" && ($("#serialNumber").val()) != null ){
			if(selectedIndex === serialNoCount){
				document.getElementById("btnLast").style.opacity = 0.5;
        		$("#btnLast").hasClass("disabled");
        		document.getElementById("btnLast").style.pointerEvents = "none";
        		
				document.getElementById("btnNexta").style.opacity = 0.5;
				document.getElementById("btnNexta").style.pointerEvents = "none";	
				$("#btnNexta").hasClass("disabled");
			}
			else{
				if(!$("#btnNexta").hasClass("disabled")){
					$("#btnNext").click(function(){
						var param ="${pageContext.request.contextPath}/SerialNumberFormView?serialNumber="+$("#serialNumber").val()+"&NavAction=1";
						window.location.href =param;
					});
				}
				if(!$("#btnLst").hasClass("disabled")){
					$("#btnLst").click(function(){
						var param ="${pageContext.request.contextPath}/SerialNumberFormView?serialNumber="+$("#serialNumber").val()+"&NavAction=4";
						window.location.href =param;
					});
				}
			}
			if(selectedIndex === 1){ //first record in database
				document.getElementById("btnFirst").style.opacity = 0.5;
				$("#btnFirst").hasClass("disabled");
				document.getElementById("btnFirst").style.pointerEvents = "none";
				document.getElementById("btnPrva").style.opacity = 0.5;
				$("#btnPrva").hasClass("disabled");
				document.getElementById("btnPrv").style.pointerEvents = "none";
			}
			else{
				if(!$("#btnPrva").hasClass("disabled")){
					$("#btnPrv").click(function(){
						var param ="${pageContext.request.contextPath}/SerialNumberFormView?serialNumber="+$("#serialNumber").val()+"&NavAction=0";
						window.location.href =param;
		 			});
				}
				$("#btnFrst").click(function(){
					if(!$("#btnFrst").hasClass("disabled")){
						var param ="${pageContext.request.contextPath}/SerialNumberFormView?serialNumber="+$("#serialNumber").val()+"&NavAction=3";
						window.location.href =param;
					}
			 	});
			}
		}
	}
}
$("#nextPrevCountlabel").text((selectedIndex)+"/"+serialNoCount);

$(document).ready(function() {

	$("#saveSerialNumber").click(  function() {

		// VALIDATE THE ITEM CODE NOT TO BE NULL
		if ($("#itemCode").val()== '') {
			alert('Item Code cannot be NULL.');
			return false;
		}
		// VALIDATE SERIAL NUMBER NOT TO BE NULL
		if ($("#serialNumber").val()== '') {
			alert('Serial Number cannot be NULL.');
			return false;
		}
		
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/SerialNumberFormSave",
			dataType : "json",
			data : {
				"serialNumber" : $("#serialNumber").val(),
				"creationDate" : $("#creationDate").val(),
				"lastModifieddate" : $("#lastModifiedDate").val(),
				"supplierId" : $("#supplierId").val(),
				"supplierName" : $("#supplierName").val(),
				"itemCode" : $("#itemCode").val(),
				"itemName" : $("#itemName").val(),
				"poId" : $("#purchOrderId").val(),
				"grId" : $("#goodsRcvId").val(),
				"itemModel" : $("#itemModel").val(),
				"itemPart" : $("#itemPartNumber").val(),
				"poItemId" : $("#purchOrderItemId").val(),
				"grItemId" : $("#goodsRcvItemId").val(),
				"email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(),
			    "serialNumberID":$("#serialNumberID").val(),
			    "status": $("#ordstat").val()
			},
			success : function(data) {
				$('#lastModifiedDate').val(data.lastModifiedDate);
				$("#serialNumberID").val(data.serialNumberID);
				var param ="${pageContext.request.contextPath}/SerialNumberFormView?serialNumber="+$("#serialNumber").val()+"&NavAction=2";
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}					
				
			 }); 

		 });
				 
$("#deleteSerialNumber").click(  function() {
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/SerialNumberFormViewDelete",
		dataType : "json",
		data : {
			"serialNumber" : $("#serialNumber").val()
		},
		success : function(data) {
			location.replace("${pageContext.request.contextPath}/SerialNumberListView");
		},
		error : function(error) {
			console.log("The error is " + error);
		}
	});
 });	 
	
function customRenderItem(ul, item) {
	var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
		result = $('<li class="ui-menu-item" role="menuitem"></li>')
		.data('item.autocomplete', item)
		.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
		.appendTo(ul);

	return result;
}

$("#supplierId").autocomplete({
	source: function(request, response) {
		$.ajax({
			type: "GET",
            contentType: "application/json; charset=utf-8",
            url: '${pageContext.request.contextPath}/getSupplierIdName',
            data: {
				"searchKey" : $("#supplierId").val()
			},    	
			dataType: "json",
        	success: function (data) {
            if (data != null) {
               response(data.searchResult);
            }
        },
        error: function(result) {
            alert("Error");
         }
       });
     }, minLength:0, maxShowItems: 40, scroll:true,

     select: function(event, ui) {
    	 supplierId.value = (ui.item ? ui.item[0]  : '');
    	 supplierName.value=ui.item[1];
		return false;
	}

}).autocomplete("instance")._renderItem = function(ul, item) {
    return $("<li class='each'>")
    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
        item[0] + "</span><br><span class='desc'>" +
        item[1] + "</span></div>")
    .appendTo(ul);
};
$("#supplierId").focus(function(){
   	if (this.value == ""){
       	$(this).autocomplete("search");
   	}						
});


$("#supplierName").autocomplete({
    source: function(request, response) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: '${pageContext.request.contextPath}/getSupplierIdName',
            data: {
				 "searchKey":$("#supplierName").val()
			 },

			dataType: "json",
            success: function (data) {
                 if (data != null) {
                     response(data.searchResult);
                 }
             },
             error: function(result) {
                 alert("Error");
             }
         });
    }, minLength:0, maxShowItems: 40, scroll:true,

    select: function(event, ui) {
   	 supplierId.value = (ui.item ? ui.item[0]  : '');
   	 supplierName.value=ui.item[1];
		return false;
	}

}).autocomplete("instance")._renderItem = function(ul, item) {
   return $("<li class='each'>")
   .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
       item[0] + "</span><br><span class='desc'>" +
       item[1] + "</span></div>")
   .appendTo(ul);
};
$("#supplierName").focus(function(){
   	if (this.value == ""){
       	$(this).autocomplete("search");
   	}						
});

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
			 	
/*	$("#password").focus(function(){
       	if (this.value == ""){
           	$(this).autocomplete("search");
       	}						
	});
	*/			     

$("#itemCode").autocomplete({
	source: function(request, response) {
       $.ajax({
           type: "GET",
           contentType: "application/json; charset=utf-8",
           url: '${pageContext.request.contextPath}/GetAllItemSerial',
           data: {
               	"itemCode" : $("#itemCode").val(),
               	"poId": $("#purchOrderId").val(),
               	"grId": $("#goodsRcvId").val()
			},
		dataType: "json",
		success: function (data) {
			if (data != null) {
				response(data.ListItemser);
			}
		},
		error: function(result) {
			alert("Error");
		}

	});
   	}, minLength:0, maxShowItems: 40, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("itemName").value= ui.item[1];
            document.getElementById("itemModel").value= ui.item[2];
            document.getElementById("itemPartNumber").value= ui.item[3];
			return false;
		}
	}).autocomplete("instance")._renderItem = function(ul, item) {
			    return $("<li class='each'>")
			    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			     item[0] + "</span><br><span class='desc'>" +
			     item[1] + "</span><br><span class='desc'>" +
			     item[2] + "</span><br><span class='desc'>" +
			     item[3] + "</span></div>").appendTo(ul);
	};
	$("#itemCode").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
   	     }						
	});					
					
$("#itemName").autocomplete({
	source: function(request, response) {                     
		$.ajax({
            type: "GET",
			contentType: "application/json; charset=utf-8",
            url: '${pageContext.request.contextPath}/GetAllItemNameFormSerial',
			data: {
	           "itemName" : $("#itemName").val(),
	           "itemCode":$("#itemCode").val()
			},
			dataType: "json",
       		success: function (data) {
           		if (data != null) {
                     response(data.ListItemNameSerial);
			     }
	   },
       error: function(result) {
               alert("Error");
     }
      });
    }, minLength:0, maxShowItems: 40, scroll:true,	
			
    select: function(event, ui) {
		this.value = (ui.item ? ui.item[0]  : '');	
		document.getElementById("itemCode").value= ui.item[1];
        document.getElementById("itemModel").value= ui.item[2];
        document.getElementById("itemPartNumber").value= ui.item[3];						
		return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
	    return $("<li class='each'>")
	    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	    item[0] + "</span></div>")
	    .appendTo(ul);
	};


$("#itemName").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
    }						
});         

$("#itemModel").autocomplete({
	source: function(request, response) {
		$.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: '${pageContext.request.contextPath}/GetAllItemModelFormSerial',
            data: {
	              "itemModel" : $("#itemModel").val(),
	              "itemCode":$("#itemCode").val()
	        },

	       dataType: "json",
	 	   success: function (data) {
		 	   if (data != null) {
			 		response(data.ListItemModelSerial);
	 		    }
	 		},
	 		error: function(result) {
	 		     alert("Error");
	 		}
	 	});
	}, minLength:0, maxShowItems: 40, scroll:true,	

		select: function(event, ui) {
		this.value = (ui.item ? ui.item[0]  : '');
		 document.getElementById("itemCode").value= ui.item[1];
         document.getElementById("itemPartNumber").value= ui.item[3];
         document.getElementById("itemName").value= ui.item[2];												
		return false;
		}
	}).autocomplete("instance")._renderItem = function(ul, item) {
    return $("<li class='each'>")
    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
    item[0] + "</span></div>")
    .appendTo(ul);
	};

	$("#itemModel").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
				}						
	});

$("#itemPartNumber").autocomplete({
	source: function(request, response) {
		$.ajax({
	    	 type: "GET",
             contentType: "application/json; charset=utf-8",
             url: '${pageContext.request.contextPath}/GetAllItemPartnoFormSerial',
             data: {
	              "itemPartNumber" : $("#itemPartNumber").val(),
	              "itemCode":$("#itemCode").val()
	          },
	          dataType: "json",
	          success: function (data) {
		          if (data != null) {
                      response(data.ListItemPartnoSerial);
				}
			},
			error: function(result) {
				alert("Error");
			}
			});

		}, minLength:0, maxShowItems: 40, scroll:true,
			select: function(event, ui) {
			this.value = (ui.item ? ui.item[3]  : '');
			document.getElementById("itemCode").value= ui.item[1];
            document.getElementById("itemModel").value= ui.item[0];
            document.getElementById("itemName").value= ui.item[2];
			return false;
	}	
	}).autocomplete("instance")._renderItem = function(ul, item) {
    return $("<li class='each'>")
    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
    item[3] + "</span></div>")
    .appendTo(ul);
	};

	$("#itemPartNumber").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		}						
	});
	
$("#purchOrderId").autocomplete({
	source: function(request, response) {
	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: '${pageContext.request.contextPath}/GetAllItemPoidFormSerial',
		data: {
			 "poId" : $("#purchOrderId").val(),
		     "itemCode":$("#itemCode").val(),
		     "grId":$("#goodsRcvId").val()
		  },
		  dataType: "json",
		  success: function (data) {
			  if (data != null) {
				  response(data.ListItemPoidSerial);
		       }
		   },
		   error: function(result) {
			   alert("Error");
		   }
		   });
	   }, minLength:0, maxShowItems: 40, scroll:true,	

	   		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			return false;
	   }
	  }).autocomplete("instance")._renderItem = function(ul, item) {
	      return $("<li class='each'>")
	      .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	      item[0] + "</span></div>")
	      .appendTo(ul);
	};

$("#purchOrderId").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});

$("#goodsRcvId").autocomplete({
	source: function(request, response) {
        $.ajax({
            type: "GET",
		    contentType: "application/json; charset=utf-8",
		    url: '${pageContext.request.contextPath}/GetAllItemGridFormSerial',
			data: {
				"grId" : $("#goodsRcvId").val(),
				"itemCode":$("#itemCode").val(),
				 "poId":$("#purchOrderId").val()
				 },
			dataType: "json",
			success: function (data){
				if (data != null) {
					response(data.ListItemGridSerial);
                 }
              },
              error: function(result) {
                  alert("Error");
               }

              });
        }, minLength:0, maxShowItems: 40, scroll:true,
        select: function(event, ui) {
            this.value = (ui.item ? ui.item[0]  : '');
            document.getElementById("purchOrderId").value= ui.item[1];
            return false;
          }

        }).autocomplete("instance")._renderItem = function(ul, item) {
                 return $("<li class='each'>")
                 .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                 item[0] + "</span></div>")
                 .appendTo(ul);
        };

        $("#goodsRcvId").focus(function(){
            if (this.value == ""){
                $(this).autocomplete("search");
             }
         });

//Check if serial number already exists
$('#serialNumber').change(function(){
	$.ajax({
	type: "GET",
	contentType: "application/json; charset=utf-8",
    url: '${pageContext.request.contextPath}/SerialValidation',
    data: {
        "serialNumber":$("#serialNumber").val()
    },
    dataType: "json",
    success: function (serialExists) {
    	if (serialExists == true){
            $("#serialCheckValidity").html("Serial Number exists, Please enter new one");
     		$("#serialCheckValidity").css("color","red" );
     		$("#serialCheckValidity").css("font-size","16px" );
     		$("#serialCheckValidity").css("font-weight","bold" );
     		$("#serialCheckValidity").val("");
     	}
     	else {
         	$("#serialCheckValidity").html("Serial Number valid, You can proceed");
     	    $("#serialCheckValidity").css ("color","#228B22");
     	    $("#serialCheckValidity").css ("font-size","16px" );
     		$("#serialCheckValidity").css("font-weight","bold" );
     		$("#serialCheckValidity").val("");
     	 }            

       	 },
       	 error: function(serialExists) {
           	 alert("Error");
           	 }
       	 });
	 });

$("#selectnav").autocomplete({
	source: function(request, response) {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/GetAllOtherSerialNum',
        data: {
            "serialSearch" : $("#selectnav").val(),
		},
		 dataType: "json",
         success: function (data) {
             if (data != null) {
                 response(data.listOtherSerial);
             }
         },
         error: function(result) {
             alert("Error");
         }
     });
    }, minLength:0, maxShowItems: 40, scroll:true,
    select: function(event, ui) {
        this.value = (ui.item ? ui.item[0]  : '');
		var param ="${pageContext.request.contextPath}/SerialNumberFormView?serialNumber="+(ui.item[0])+"&NavAction=2";
		window.location.href =param;
		return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
	    return $("<li class='each'>")
	    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	    item[0] + "</span></div>")
	    .appendTo(ul);
	};
	$("#selectnav").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		 }						
	});  

});

 </script>


	  
</html>



