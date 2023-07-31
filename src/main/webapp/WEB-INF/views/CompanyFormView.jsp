<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
    <!-- ADDED BY AHMAD TAFECH -->
	<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
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
	
		<!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
			
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>

		
    
     <!--  style used for prrqitem table  -->    
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
    .hide-row { visibility: hidden; }
    
    
    .label{ 
  display: table-caption;
  text-align: center;
  font-size: 20px;
  font-style: bold;
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
				/*	overflow-x: both; /* add padding to account for vertical scrollbar */
				/*	padding-right: 100px;
	        		}*/

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
	  
        
 	</style>



        
       
            
</head>
<body>

<%--   <c:set var = "page" value = "Sales"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="account" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
     <!--  end of general head page -->
   <p></p>
     
     <div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
								
							<span class="input-group-text" style="color:green">COMPANY ID</span>
							<input type="text" id="clientID"  value="${clientID}" class="form-control text-input"  readonly />
						</div>
					</div>
			</div>
			
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span  class="input-group-text" style="width:210px;">Status</span>
				<select id="ordstat" class="form-control">
								<option value="Draft" <c:if test = "${ordStatus =='Draft'}" > selected </c:if> >Draft</option>
								<option value="Activated" <c:if test = "${ordStatus =='Activated'}" > selected </c:if>>Activated</option>
								<option value="Deactivated" <c:if test = "${ordStatus =='Deactivated'}" > selected </c:if>>Deactivated</option>
								<option value="Cancelled" <c:if test = "${ordStatus =='Cancelled'}" > selected </c:if>>Cancelled</option>
								<option value="Blocked" <c:if test = "${ordStatus =='Blocked'}" > selected </c:if>>Blocked</option>
								</select>
				</div>							
			</div>
			
<!-- 					<div class="pad col-md-3 hide-row"></div> -->
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Company</span>
						<input type="text" id="selectnav" value="${selectnav}"
							style="width: 430px" class="form-control text-input" />				</div>
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
					<span class="input-group-text">Created Date</span>
					<input type="text" id="dateClient" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${lastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
						</div>
				</div>
		</div>
	</div>
			
			
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
			        			onclick='window.location.href = "${pageContext.request.contextPath}/CompanyListView"'
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

		
		
	</div>

	
	<div class="container-fluid">
		
<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: auto;">
             <li class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">INFORMATION</a></li>
            
            <li class="nav-item"><a class="nav-link"
						id="custom-tabs-Image-tab" data-toggle="tab" href="#custom-tabs-Image"
						role="tab" aria-controls="#custom-tabs-Image" aria-selected="false"
						style="color: gold;">ADDRESS</a></li>
						
			
            
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
	<table width="100%" border="0" cellpadding="0" cellspacing="0" id="warehouseInfo_tbl">
	<tr>
	<td width=50% valign="top" class="left_col">
			<div class="form-group">
				<div class="input-group-prepend">
						<span class="input-group-text">First Name</span>
						<input type="text" id="firstName"  value="${firstName}" class="form-control text-input"    />
				</div>
			</div>
		</td>
		<td rowspan=8></td>

		</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Last Name</span>
							<input type="text" id="lastName"  value="${lastName}" class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Mobile</span>
							<input type="text" id="mobile"  value="${mobile}" class="form-control text-input"    />
                	</div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Parent Company</span>
							<input type="text" id="dspName"  value="${dspName}" class="form-control text-input"    />
                	</div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		

   		</td>
   	</tr>
   		

   	
</table>


	</div>
	
	<div class="tab-pane fade" id="custom-tabs-Image" role="tabpanel"
				aria-labelledby="custom-tabs-Image-tab">

				<p></p>
				<div>

					<form>

<div class="input-group-prepend" data-target-input="nearest">
                            <span class="input-group-text">Address</span>
                            <input type="text" id="loc" value="${loc}" class="form-control text-input" Style="width:50% " />
                        
                    </div>



					</form>
				</div>

				<p></p>
			</div>
			
	
		


         
       
</body>

<script>
if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}

function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
var map;
var marker;
let markers = [];

function initMap() {
	
  

  function addMarker(position) {
	  var clientName = $("#firstName").val()+" "+$("#lastName").val();
	  var address = $("#loc").val();
	  var contentOfInfoWindow = "<b>Client Name </b>: "+clientName+"<p><p>"+   "<b>Address</b> : "+address;
	  var ctx = getContextPath();
	

  
  	}
	





  	

	}
	



// get all colmns count per row
function count(array){
    var c = 0;
    for(i in array) // in returns key, not object
        if(array[i] != undefined)
            c++;
    return c;
}


if('${regStatusLog}' != 'addNew'){
	regStatusLog=${regStatusLog};
	var regRowCount= count(regStatusLog[0]);
	var itemActionRow = "<tr>";
	for(i=0;i<regStatusLog.length;i++){

		for(j=0;j<regRowCount;j++){
			
			
	   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+regStatusLog[i][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
		   	 
			}

			itemActionRow=itemActionRow+"</tr>";
		}
	$("#regtab > tbody").append(itemActionRow)

}



/////////////////////////////////////////
if ('${ListClient}' == "addNew") {
	 $("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});
}


$("#ordstat").click(  function() {

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
})


$("#Activatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Activated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#Deactivatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Deactivated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
		 $("#Cancelwh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Cancelled').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
		 $("#Blockwh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Blocked').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})




$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});




$("#saveButton").click(  function() {
	
	if ($("#firstName").val()== '') {
		 alert('First Name cannot be NULL');
		 return false;}

    if ($("#lastName").val()== '') {
		 alert('Last Name cannot be NULL');
		 return false;}
     if (($.isNumeric($("#mobile").val()))== false) {
		 alert('Mobile is not numeric');
		 return false;}

	val =$("#dateClient").val();
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
		
			$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/CompanyFormSave",
					dataType : "json",
					data : {
						"status":document.getElementById("ordstat").value,
						"clientID" : $("#clientID").val(),
						"DisplayName" : $("#dspName").val(),
						"FirstName" : $("#firstName").val(),
						"LastName" :$("#lastName").val(),
						"Mobile" : $("#mobile").val(),
						
						"Location" : $("#loc").val(),
						"createdate": $("#dateClient").val(),
						"lastModifiedDate": $("#lstmodifdate").val(),
						"clientDesc":$("#descr").val(),
						
																
				},
					success : function(data) {
						$('#lstmodifdate').val(data.lstmodifdate)

						$('#clientID').val(data.clientID);
						var param ="${pageContext.request.contextPath}/CompanyFormView?clientID="+$("#clientID").val()+"&NavAction=2";
						location.replace(param);
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

	});


$("#deleteButton").click(  function() {

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/CompanyDelete",
			dataType : "json",
			data : {
				"clientID" : $("#clientID").val()
			},
			success : function(data) {
				
				
			},
			error : function(error) {
	
				location.reload();
		    	location.replace("${pageContext.request.contextPath}/CompanyListView");
			}
		});
	
		
		
	 
 })


	if('${SelectedIndex}' != "addNew"){
					var SelectedIndex = ${SelectedIndex};
					if('${ClientsCount}' != "addNew"){

						
				var ClientsCount = ${ClientsCount};
				
				if(($("#clientID").val()) != "" && ($("#clientID").val()) != null){

				if(SelectedIndex === ClientsCount){
					
	        		document.getElementById("btnLast").style.opacity = 0.5;
	        		$("#btnLast").hasClass("disabled");
	        		document.getElementById("btnLast").style.pointerEvents = "none";
	        		
	        		document.getElementById("btnNexta").style.opacity = 0.5;
	        		document.getElementById("btnNexta").style.pointerEvents = "none";

					
					$("#btnNexta").hasClass("disabled");
					
					}else{
						
						if(!$("#btnNexta").hasClass("disabled")){
							
							$("#btnNext").click(function(){
								
								var param ="${pageContext.request.contextPath}/CompanyFormView?clientID="+$("#clientID").val()+"&NavAction=1";

								window.location.href =param;
					
							});
				
						}
						if(!$("#btnLst").hasClass("disabled")){
	        				
	        				$("#btnLst").click(function(){
	        					
								var param ="${pageContext.request.contextPath}/CompanyFormView?clientID="+$("#clientID").val()+"&NavAction=4";
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
							
							var param ="${pageContext.request.contextPath}/CompanyFormView?clientID="+$("#clientID").val()+"&NavAction=0";
							window.location.href =param;
							
						 });
					}
					$("#btnFrst").click(function(){

	        			if(!$("#btnFrst").hasClass("disabled")){
	        					
							var param ="${pageContext.request.contextPath}/CompanyFormView?clientID="+$("#clientID").val()+"&NavAction=3";
	        				window.location.href =param;
	        						
	        				}
	        				 });

				}
				
				}}



					
			}
				$("#label-1").text((SelectedIndex)+"/"+ClientsCount);


		            $("#selectnav").autocomplete({
	    			
	    		    source: function(request, response) {
	    			    
	    		             $.ajax({
	    		                 type: "GET",
	    		                 contentType: "application/json; charset=utf-8",
	    		                 url: '${pageContext.request.contextPath}/GetAllCompanies',
	    		                 data: {
	    								"client" : $("#selectnav").val(),
	    						 },
	    		                 dataType: "json",
	    		                 success: function (data) {
	    		                     if (data != null) {
	    		                         response(data.ListClient);
	    		                     }
	    		                 },
	    		                 error: function(result) {
	    		                     alert("Error");
	    		                 }
	    		             });
	    		         }, minLength:0, maxShowItems: 40, scroll:true,

	    					select: function(event, ui) {
	    						
	    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
	    						
	    						var param ="${pageContext.request.contextPath}/CompanyFormView?clientID="+(ui.item[0])+"&NavAction=2";
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
	    	






</script>
    

</html>