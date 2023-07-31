
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
	        		 
 	</style>



        
       
            
</head>
<body>

<%--   <c:set var = "page" value = "Setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
     <!--  end of general head page -->
   <p></p>
     
     <div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Rule Id</span>
                            <input type="text" id="RuleId" value="${RuleId}" class="form-control text-input"  />
                        </div>
                    </div>
                </div>
			<div class=" col-md-6 nextprvItems">
				<label id="label-1" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
					<nav aria-label="Page navigation">
						  <ul class="pagination">
							<li id="btnPrv" class="page-item " style="margin-right: 2px;"><a id="btnPrva" href="#" class="btn btn-success previous">&laquo; Previous</a></li>				
							<li id="btnNext" class="page-item" style=" padding-right: 0px ! important;"><a id="btnNexta" style="width:100px;" href="#" class="btn btn-success next">Next &raquo;</a></li>
						  </ul>
					</nav>
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
					<input type="text" id="dateSchedule" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
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
			
			
			
			
			<div class="col-md-6" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/ScheduleReportListView"'
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
            role="tab" aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            
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
    
  	<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Rule Name</span>
							<input type="text" id="RuleName"  value="${RuleName}" class="form-control text-input"   />
					</div>
				</div>
			</div>

            <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Email To</span>
							<input type="text" id="EmailTo"  value="${EmailTo}" class="form-control text-input"   />
					</div>
				</div>
			</div>

            <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Email From</span>
							<input type="text" id="EmailFrom"  value="${EmailFrom}" class="form-control text-input"   />
					</div>
				</div>
			</div>

	<div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Last Run Time</span>
                            <input type="text" id="LastRunTime" value="${LastRunTime}" class="form-control text-input"  />
                        </div>
                    </div>
                </div>
	
	
	</div>

		<div class="row">

                <div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Email/Phone</span>
							<select name="cars" id="recipient"  style="width: 350px;  text-align-last:center;"class="form-control text-input"    >
				
								<option value="Email" <c:if test = "${dep =='em'}" > selected </c:if>  >Email</option>
								<option value="Mobile" <c:if test = "${dep =='mob'}"> selected </c:if>>Mobile</option>
								
							  </select>
					</select>
                        </div>
                    </div>
                </div>

				

                <div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Phone Number</span>
                            <input type="text" id="PhoneNumber" value="${PhoneNumber}" class="form-control text-input"  />
                        </div>
                    </div>
                </div>
                
                
                <div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Schedule Time</span>
                            <input type="text" id="ScheduleTime" value="${ScheduleTime}" class="form-control text-input"  />
                        </div>
                    </div>
                </div>
                
                
                
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group-prepend">
					<span class="input-group-text">Email Subject</span>

				</div>
			</div>

		</div>
		<div class="row">
			<div class="col-sm-6 ">
				<div class="input-group-prepend">
					<textarea name="emaildescrip" cols="220" class="form-control text-input"
						style="margin-top:5px;" rows="8" id="EmailSubject"> ${EmailSubject} </textarea>
				</div>
			</div>
		</div>

	    
	
	
	
	
	</div>
	
</div>
</div>





<script>

var navList = [];
var iCode = "${ruleID}";
var listItemsNav = ${listItemsNav};
var indexItm;
console.log("hihiihihihihi")
if(iCode !=""){
if(listItemsNav != "noList" && listItemsNav.length >1){
	var size = new TextEncoder().encode(JSON.stringify(listItemsNav)).length;
	var kiloBytes = size / 1024;
	var megaBytes = kiloBytes / 1024;
	var browser = platform.name;
	if(browser == "Samsung Internet" && megaBytes >2){
		localStorage.setItem("scheduleList", JSON.stringify(listItemsNav));
		}else if(browser.includes("Safari") && megaBytes >5){
			localStorage.setItem("scheduleList", JSON.stringify(listItemsNav));
			}else if(megaBytes >10){
				localStorage.setItem("scheduleList", JSON.stringify(listItemsNav));
				}else{navList = listItemsNav;
					localStorage.removeItem("scheduleList");}
	
		findIndex();
	 }else{
		 navList = JSON.parse(localStorage.getItem("scheduleList"));
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

$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});




$("#saveButton").click(  function() {
	console.log("saveeeeeeeeeeeee1")

	

	if ($("#RuleName").val()== '') {
		 alert('Rule Name cannot be NULL');
		 return false;}

    if ($("#EmailTo").val()== '') {
		 alert('Email To cannot be NULL');
		 return false;}
	 
    if ($("#EmailFrom").val()== '') {
		 alert('Email From cannot be NULL');
		 return false;}
	 
     if (($.isNumeric($("#PhoneNumber").val()))== false) {
		 alert('Mobile is not numeric');
		 return false;}

	 val =$("#dateSchedule").val();
     val1 = Date.parse(val);
     //console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
	 
	  // validate modified date cannot be null
	 val =$("#lstmodifdate").val();
     val1 = Date.parse(val);
     //console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }

     val =$("#LastRunTime").val();
     val1 = Date.parse(val);
     
     if (isNaN(val1) == true) {
          alert(' Last Run Date is not valid');
          return false;
          
        }
     
		
				
		
			$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/ScheduleReportFormSave",
					dataType : "json",
					data : {

						"RuleId" : $("#RuleId").val(),
						"RuleName" : $("#RuleName").val(),
						"LastRunTime" : $("#LastRunTime").val(),
						"ScheduleTime" :$("#ScheduleTime").val(),
						"PhoneNumber" : $("#PhoneNumber").val(),
						"EmailTo" : $("#EmailTo").val(),
						"EmailFrom" : $("#EmailFrom").val(),
						"EmailSubject" : $("#EmailSubject").val(),
						"createdate": $("#dateSchedule").val(),
						"lastModifiedDate": $("#lstmodifdate").val(),
						

											
				},
					success : function(data) {

						console.log("all done "+ data.alitest);
						$('#lstmodifdate').val(data.lstmodifdate)
						console.log("the rule ID data is"+data.ruleID);
						$('#ruleID').val(data.ruleID);
						var param ="${pageContext.request.contextPath}/ScheduleReportFormView?RuleId="+$("#ruleID").val()+"&scheduleList=scheduleList";
						location.replace(param);
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

	});






$("#deleteButton").click(  function() {
	 console.log('delete now');
	 
var RuleId = document.getElementById("RuleId").value;		
	 
		$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/DeleteScheduleFormView",
				dataType : "json",
				data : {
					"RuleId" :  $("#RuleId").val()
				},
				success : function(data) {
					
					location.replace("${pageContext.request.contextPath}/ScheduleReportListView");
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
					if(localStorage.getItem("scheduleList") != null){
						if(localStorage.getItem("scheduleList").length != 0){
							var param ="${pageContext.request.contextPath}/ScheduleReportFormView?RuleId="+navList[i];
							window.location.href =param;
							}		
						}else{
							var param ="${pageContext.request.contextPath}/ScheduleReportFormView?RuleId="+navList[i]+"&scheduleList=scheduleList";
							window.location.href =param;
							}
					}
				
				}
	    });
$("#btnNext").click(function(){
		if(!$("#btnNexta").hasClass("disabled")){
			indexItm++;
			if(localStorage.getItem("scheduleList") != null){
			if(localStorage.getItem("scheduleList").length != 0){
				var param ="${pageContext.request.contextPath}/ScheduleReportFormView?RuleId="+navList[indexItm];
				window.location.href =param;
				}		
			}
		else{
			var param ="${pageContext.request.contextPath}/ScheduleReportFormView?RuleId="+navList[indexItm]+"&scheduleList=scheduleList";
			window.location.href =param;
			}
		}
		});
	
$("#btnPrv").click(function(){
		if(!$("#btnPrva").hasClass("disabled")){
		indexItm--;
		if(localStorage.getItem("scheduleList") != null){
		if(localStorage.getItem("scheduleList").length != 0){
			var param ="${pageContext.request.contextPath}/ScheduleReportFormView?RuleId="+navList[indexItm];
			window.location.href =param;
		}
		}else{
			var param ="${pageContext.request.contextPath}/SchedulereportFormView?RuleId="+navList[indexItm]+"&scheduleList=scheduleList";
			window.location.href =param;
			}
		}
		});
	$("#label-1").text((indexItm+1)+"/"+navList.length);
	
	}	


    



</script>
     
    
</body>
</html>