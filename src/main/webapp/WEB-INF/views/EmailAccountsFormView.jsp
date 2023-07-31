<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
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
	width:380px;
	}
 	</style>
</head>
<body>
<%--  <c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
<!--  end of general head page -->
	<div class="container-fluid">     
		<div class="row">
		<div class="col-md-12">
		<p></p>
		</div>
		
		</div>
	 <p></p>
<div class="container-fluid">
		<div class="row">
			
		
			<div class="col-md-3">
				 <div class="input-group-prepend">
						<span class="input-group-text"> EMAIL ID</span>
						<input type="text" id="id" value="${id}" class="form-control text-input" readonly />
					
					</div>
			</div>
			<div class="col-md-4">
				
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

		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
		
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
			        			onclick='window.location.href = "${pageContext.request.contextPath}/EmailAccountsListView"'
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
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c;width:1300;margin-left:-15px; margin-right:-15px; margin-top: -20px;">

       
  <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab"
            role="tab"
            aria-controls="#custom-tabs-email-tab" aria-selected="false" style="color: gold;">	EMAIL ACCOUNT</a></li>
      
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
	
<div> 

  <div class="col-md-3">
  <div class="form-group">
					<div class="input-group-prepend">
					</div>
					</div>
			</div>
			<div class="row">
<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text">EMAIL ADDRESS</span>
						<input type="text" id="email" value="${email}" class="form-control text-input" />
					
					</div>
				</div>
	</div>
<div class="col-md-1"></div>
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text"> PASSWORD </span>
						<input type="password" id="password" value="${password}" class="form-control text-input" />
					
					
					</div>
					</div>
					
	</div>
	
	<div class="col-md-2">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text"> <input type="checkbox" onclick="myFunction()">  Show Password</span>
				    </div>
					</div>
	</div>
			</div>

<p></p>
</div>
<div> 
<h4 id="result"></h4>

</div>

<script type='text/javascript'>


//////////////////////////////// to validate email account ///////////////////////////

function validateEmail(email) {
  const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(String(email).toLowerCase());
}

function validate() {
  const $result = $("#result");
  const email = $("#email").val();
  $result.text("");

  if (validateEmail(email)) {
    $result.text(String(email).toLowerCase() + " is valid"); 
    $result.css("color", "green");
  } else {
    $result.text(email + " is not valid!");
    $result.css("color", "red");
  }
  return false;
}

$("#email").on("input", validate);


///////////////////////////////// end of validation  //////////////////////////////////
	
function myFunction() {
	  var x = document.getElementById("password");
	  if (x.type === "password") {
	    x.type = "text";
	  } else {
	    x.type = "password";
	  }
	} // this function is to toggle password input type

			
//////////////////////////////////////changinging status to NOT SAVED when pressing add/delete-row     ////////////////////////////////////////
			 $(".add-row").click(function() {
					$("#formStatus").text("Not Saved");
					$('.dot').css({"background-color" : "orange"});
				});
			 $(".delete-row").click(function() {
					$("#formStatus").text("Not Saved");
					$('.dot').css({"background-color" : "orange"});
				});	
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	



////////////////////////////////////////////////////////// Previous and Next  ///////////////////////


if ('${ListNo}' == "addNew") {
$("#formStatus").text("New");
$('.dot').css({"background-color" : "orange"});}

 ///////////////////////////////////////////////////////////////////////
 
 var navList = [];
 var iCode = "${id}";
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
 
////////////////change from Save to Not Save when changing values  /////////////
 $("input").change(function() {
 	$("#formStatus").text("Not Saved");
 	$('.dot').css({"background-color" : "orange"});
 	});
$("#email").on('keyup change', function () {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		});
$("#password").on('keyup change', function () {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});	
	
	///////////////////////////////////////////////////////////////
var dict = [];

	$("#saveButton").click(  function() {
	 	console.log("testing Main Save Button");
	 
	 	
	 	if ($("#email").val()== '') {
			 alert('Email Address can not be NULL');
			 return false;}
	 	if ($("#password").val()== '') {
	 		 alert('Password can not be NULL');
	 		 return false;}
	 	 
	 	  // validate modified date cannot be null

	 				console.log("creationDate testing"+$("#creationDate").val());
	 			
	 			$.ajax({
	 					type : "GET",
	 					url : "${pageContext.request.contextPath}/EmailAccountsFormSave",
	 					dataType : "json",
	 					data : {
	 						"type": '${ListNo}', 
	 						"id" : $("#id").val(),
	 						"email" :String($("#email").val()).toLowerCase(),
	 						"password" :$("#password").val(),
	 						
	 						
	 											
	 				},
	 					success : function(data) {

	 						console.log("all done "+ data.alitest);
	 						$('#id').val(data.id);
	 						var param ="${pageContext.request.contextPath}/EmailAccountsFormView?id="+$("#id").val()+"&notifiList=notifiList";
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
	 				url : "${pageContext.request.contextPath}/EmailAccountsFormViewDelete",
	 				dataType : "json",
	 				data : {
	 					"id" :  $("#id").val()
	 				},
	 				success : function(data) {
	 					//console.log("The returned data is " +data.testGoods);
	 					location.replace("${pageContext.request.contextPath}/EmailAccountsListView");
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
	 							var param ="${pageContext.request.contextPath}/EmailAccountsFormView?id="+navList[i];
	 							window.location.href =param;
	 							}		
	 						}else{
	 							var param ="${pageContext.request.contextPath}/EmailAccountsFormView?id="+navList[i]+"&notifiList=notifiList";
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
	 				var param ="${pageContext.request.contextPath}/EmailAccountsFormView?id="+navList[indexItm];
	 				window.location.href =param;
	 				}		
	 			}
	 		else{
	 			var param ="${pageContext.request.contextPath}/EmailAccountsFormView?id="+navList[indexItm]+"&notifiList=notifiList";
	 			window.location.href =param;
	 			}
	 		}
	 		});
	 	
	 $("#btnPrv").click(function(){
	 		if(!$("#btnPrva").hasClass("disabled")){
	 		indexItm--;
	 		if(localStorage.getItem("notifiList") != null){
	 		if(localStorage.getItem("notifiList").length != 0){
	 			var param ="${pageContext.request.contextPath}/EmailAccountsFormView?id="+navList[indexItm];
	 			window.location.href =param;
	 		}
	 		}else{
	 			var param ="${pageContext.request.contextPath}/EmailAccountsFormView?id="+navList[indexItm]+"&notifiList=notifiList";
	 			window.location.href =param;
	 			}
	 		}
	 		});
	 	$("#label-1").text((indexItm+1)+"/"+navList.length);
	 	
	 
	 	
	 	
	 	}	

	 
	     
</script>			
</body>
</html>