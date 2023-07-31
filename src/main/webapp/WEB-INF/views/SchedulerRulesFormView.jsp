
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
	
	
		<link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet"    >
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>
		
    
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
				
				.containerCheckBox {
  					
  					font-size: 16px;
  					-webkit-user-select: none;
  					-moz-user-select: none;
  					-ms-user-select: none;
  					user-select: none;
					}
				.container input {
					vertical-align: text-top;
  					position: absolute;
  					opacity: 0;
  					cursor: pointer;
  					height: 0;
  					width: 0;
					}
					
				
				/* When the checkbox is checked, add a blue background */
				.container input:checked ~ .checkmark {
  					background-color: #2196F3;
					}
				/* Create the checkmark/indicator (hidden when not checked) */
				.checkmark:after {
  					content: "";
  					position: absolute;
 					 display: none;
					}

				/* Show the checkmark when checked */
					.container input:checked ~ .checkmark:after {
  					display: block;
					}

				/* Style the checkmark/indicator */
					.container .checkmark:after {
  					left: 9px;
  					top: 5px;
 					width: 5px;
  					height: 10px;
  					border: solid white;
  					border-width: 0 3px 3px 0;
  					-webkit-transform: rotate(45deg);
  					-ms-transform: rotate(45deg);
  					transform: rotate(45deg);
					}
					

	
	        		 
 	</style>



        
       
            
</head>
<body>

  <%--   <c:set var = "page" value = "setup"/> --%>

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
								
							<span class="input-group-text" style="color:green">Rule ID</span>
							<input type="text" id="RuleID"  value="${RuleID}" class="form-control text-input"  readonly />
						</div>
					</div>
			</div>
			
			<div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Status</span>
							<select name="StatusAct" id="Status"  style="width: 350px;  text-align-last:center;"class="form-control text-input"    >
				
								<option value="Active" <c:if test = "${Status =='Active'}" > selected </c:if>  >Active</option>
								<option value="Not Active" <c:if test = "${Status =='Not Active'}"> selected </c:if>>Not Active</option>
								
							  </select>
					</select>
                        </div>
                    </div>
                </div>
			
			
			
			
			
			
			<div class=" col-md-3 nextprvItems">
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
					<input type="text" id="dateAutomated" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
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
	
                
           
	<div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Last Run Time</span>
                            <input type="text" id="LastRunTime" readonly value="${LastRunTime}" class="form-control text-input"  />
                        </div>
                    </div>
                </div>	
	
			
			
			
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/SchedulerRulesListView"'
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
				<i class="fa fa-delete"></i> Delete
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
							<span class="input-group-text">Rule Name</span>
							<input type="text" id="RuleName"  value="${RuleName}" class="form-control text-input"   />
					</div>
				</div>
			</div>
			
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Report ID</span>
							<input type="text" id="ReportID"  value="${ReportID}" class="ui-widget ui-widget-content ui-corner-all form-control"   />
					</div>
				</div>
			</div>

            <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Report Name</span>
							<input type="text" id="ReportName"  value="${ReportName}" class="ui-widget ui-widget-content ui-corner-all form-control"   />
					</div>
				</div>
			</div>
			
			

            
			
	</div>
		<div class="panel-body collapse in" id="TryTo" style ="border:1px solid black;">

	</div>
	<div  id="TryTo2" >

	</div>
	
		<div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Report Times</span>
                        <input type="text" id="ReportTimes" value="${ReportTimes}" class="form-control text-input"  />
                    </div>
                </div>
            </div>
		</div>
		<div class="row">
			<div class="col-md-4">
            	<div class="form-group">
                	<div class="input-group-prepend">
	                    <span class="input-group-text">Report Month Days</span>
	                    <input type="text" id="ReportMonthdays" value="${ReportMonthdays}" class="form-control text-input"  />
                  	</div>
                </div>
            </div> 
		</div>
		<div class="row">
			<div class="col-md-12">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Report Weekdays</span>
                            <label style ="margin-left: 25px;"  class="containerCheckBox">Monday
  		<input type="checkbox"  id="Mon" name="Weekdays[]" value="Mon" >
  		<span class="checkmark"></span>
		</label>
		
		<label class="containerCheckBox">Tuesday
  		<input type="checkbox" id="Tue" name="Weekdays[]" value="Tue" >
  		<span class="checkmark"></span>
		</label>
		<label class="containerCheckBox">Wednesday
  		<input type="checkbox" id="Wed" name="Weekdays[]" value="Wed" >
  		<span class="checkmark"></span>
		</label>
		<label class="containerCheckBox">Thursday
  		<input type="checkbox" id="Thu" name="Weekdays[]" value="Thu" >
  		<span class="checkmark"></span>
		</label>
		<label class="containerCheckBox">Friday
  		<input type="checkbox" id="Fri" name="Weekdays[]" value="Fri" >
  		<span class="checkmark"></span>
		</label>
		<label class="containerCheckBox">Saturday
  		<input type="checkbox" id="Sat" name="Weekdays[]" value="Sat" ">
  		<span class="checkmark"></span>
		</label>
		<label class="containerCheckBox">Sunday
  		<input type="checkbox" id="Sun" name="Weekdays[]" value="Sun" >
  		<span class="checkmark"></span>
		</label>      
                        </div>
                    </div>
                </div> 
		</div>
		
		
			<div class="row">
			
			<div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Report Destination</span>
							<select name="destination" id="ReportDestination"  style="width: 350px;  text-align-last:center;"class="form-control text-input"    >
				
								<option value="Email" <c:if test = "${ReportDestination =='Email'}" > selected </c:if>  >Email</option>
								<option value="Mobile" <c:if test = "${ReportDestination =='Mobile'}"> selected </c:if>>Mobile</option>
								
							  </select>
					</select>
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
							<span class="input-group-text">Email To</span>
							<input type="text" id="EmailTo"  value="${EmailTo}" class="form-control text-input"   />
					</div>
				</div>
			</div>
			
			<div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Phone Number</span>
                            <input type="text" id="PhoneNB" value="${PhoneNB}"  class="form-control text-input"  />
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
						style="margin-top:5px; margin-bottom:10px;" rows="3" id="EmailSubject"> ${EmailSubject} </textarea>
				</div>
			</div>
		</div>
		
		
		
		<div class ="row">
		
		<div class="col-md-3">
                    <div class="form-group">
                        <div class="input-group-prepend">
                           <button type="button" id="toggleButton" class="btn btn-primary BtnActive"> Trigger Rule </button>
                        </div>
                    </div>
                </div>
		
		</div>
		
		
		
		

	    
	
	
	
	
	</div>
	
</div>
</div>





<script>

var ReportValues=""; 

$.ajax({
    type: "GET",
    contentType: "application/json; charset=utf-8",
    url: '${pageContext.request.contextPath}/GetAllFilters',
    data: {	"ReportID": $("#ReportID").val(),
        	"RuleID": $("#RuleID").val(),
    		
			
			
	 },
    dataType: "json",
    success: function (data) {
        if (data != null) {

        	LengthOfInputs = data.ListGetAllFilters.length;
            
			var Rows = data.ListGetAllFilters.length/3;
			var je = 1;
			Rows = Math.ceil(Rows);
			for(i = 1; i <= Rows; i++) { 
                
            	$('#TryTo2').append('<div id=Row' +i+' class = "row"></div>');
            }
			var count = 1;
			
			for(i = 1;i <= data.ListGetAllFilters.length;i++){

				var options ="";
				eval('var id = "Row' + count +'";');
				
            	var FilterID = data.ListGetAllFilters[i-1][0];
            	var FilterValues = data.ListGetAllFilters[i-1][1];
            	var FilterEnable = data.ListGetAllFilters[i-1][2];
            	var FilterType = data.ListGetAllFilters[i-1][3];
            	var FilterOptions = data.ListGetAllFilters[i-1][4];
            	var IDPar = data.ListGetAllFilters[i-1][5];

            	if(FilterEnable == "Y"){

            		

            			ReportValues += (FilterID);
            			ReportValues += ("=");
            			
            			ReportValues += (FilterValues);
            			ReportValues += ("=");
            			ReportValues += (IDPar);
            			ReportValues += ("\n");

            			
                	
            		je = je+1;
            		if(je%Rows ==0){
    					count = count +1;
    					
    				}

                	if(FilterType == "autocomplete"){
            		$('#'+id).append('<div class="col-md-3">'+
                			'<div class="form-group">'+
                			'<div class="input-group-prepend">'+
                					'<span  class="input-group-text">'+FilterID+'</span>'+
                					'<input type="text" id="' + FilterID +'"   class="form-control text-input" value= "' + FilterValues + '" />'+
                			'</div>'+
                		'</div>'+
                	'</div>');
            		}
                	else if(FilterType == "dropdown"){
						
						var array = FilterOptions.split("-");
						for (var j = 0; j < array.length; j++) {
							   if(FilterValues == array[j])
								   	options += '<option value="' + array[j]+ '" selected="selected">' + array[j] + '<\/option>';
							   else
							   		options += '<option value="' + array[j]+ '">' + array[j] + '<\/option>';
							  }
						
						
						$('#'+id).append('<div class="col-md-3">'+
	                			'<div class="form-group">'+
	                			'<div class="input-group-prepend">'+
								'<select id= "' +FilterID +'" name = "' + FilterID + '"  class="form-control text-input" </select>'+
								options+
								'</div>'+
		                		'</div>'+
		                		'</div>'
								);
						}
                	else if (FilterType == "multiselect"){
						var array = FilterOptions.split("-");
						var myOptions = [];
						for(var j = 0; j< array.length; j++) {
							myOptions.push({label: array[j] , value: array[j]});
						}
						$('#'+id).append('<div class="col-md-3">'+
	                			'<div class="form-group">'+
	                			'<div class="input-group-prepend">'+
	                			'<div id ="' + FilterID + '"></div>'+
	                			'</div>'+
		                		'</div>'+
		                		'</div>');
						VirtualSelect.init({
							  ele: ('#'+FilterID),
							  options: myOptions,
							  multiple: true,
							  placeholder: FilterID

							});
                		
						
                		
						}
                	else if(FilterType == 'checkbox'){
						if(FilterOptions == 'checked')
						$('#'+id).append('<div class="col-md-3">'+
	                			'<div class="form-group">'+
	                			'<div class="input-group-prepend" data-target-input="nearest">'+
	                			'<div class="input-group-text">'+
	                			'<input  type = "checkbox" id ="' + FilterID +'" name ="' + FilterID +'" value = "' +FilterID +'" class = "input-group-text" checked>'+
	                			'<span style="margin-left: 10px;">' + FilterID + '</span>'+
	                			'</div>'+
		                		'</div>'+
		                		'</div>'+
		                		'</div>'
		                		);
						
							
	                			
						}


                	
                	}
            	
            	
        	}
            
        }
    },
    error: function(result) {
        alert("Error");
    }
});






var FilterString="";

var Filter = {};
var IDs = [];
var Types = [];
var OptionsArray = [];
var LengthOfInputs;
var d = emailTo = document.getElementById("ReportDestination").value;
emailTo = document.getElementById("EmailTo");
emailFrom = document.getElementById("EmailFrom");
emailSubject = document.getElementById("EmailSubject");
phoneNB = document.getElementById("PhoneNB");
phoneNB.disabled =true;

if(d =="Email"){
	emailTo.disabled = false;
    emailFrom.disabled =false;
    emailSubject.disabled = false;
	phoneNB.disabled =true;
		
    }
else {
	phoneNB.disabled =false;
    emailTo.disabled = true;
    emailFrom.disabled =true;
    emailSubject.disabled = true;
    
    }






function InputParamShow() {
	var ReportID = $("#ReportID").val();
	var variablename = "ReportParam";
	var j =0;
	for(j = 1; j < 12; j++) {
		eval('var ' + variablename + j + '= document.getElementById(' +"'" + variablename + j + "');");
	}
	
	

	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: '${pageContext.request.contextPath}/GetReportInputs',
        data: {
				ReportID : ReportID,
				
		 },
        dataType: "json",
        success: function (data) {
            if (data != null) {
				console.log(data.InputsList);


				
				LengthOfInputs = data.InputsList.length;
                
				var Rows = data.InputsList.length/3;
				Rows = Math.ceil(Rows);
				
                for(i = 1; i <= Rows; i++) { 
                      
                	$('#TryTo').append('<div id=Row' +i+' class = "row"></div>');
                }
                var count = 1;
                
                for(i = 1;i <= data.InputsList.length;i++){
					var options ="";
					eval('var id = "Row' + count +'";');
					var Type = data.InputsList[i-1][1];
					var Values =  data.InputsList[i-1][4];
					var ID = data.InputsList[i-1][3];
					
					IDs.push(ID);
					Types.push(Type);
					OptionsArray.push(Values);
					
					var Name = data.InputsList[i-1][2];
					if(i%Rows ==0){
							count = count +1;
						}
					
					if(Type == "autocomplete"){
						
						$('#'+id).append('<div class="col-md-3">'+
	                			'<div class="form-group">'+
	                			'<div class="input-group-prepend">'+
	                					'<span  class="input-group-text">'+Name+'</span>'+
	                					'<input type="text" id="' + ID +'"   class="form-control text-input"   />'+
	                			'</div>'+
	                		'</div>'+
	                	'</div>');
						}
					else if (Type == 'dropdown'){
						
						var array = Values.split("-");
						for (var j = 0; j < array.length; j++) {
							   options += '<option value="' + array[j]+ '">' + array[j] + '<\/option>';
							  }
						
						
						$('#'+id).append('<div class="col-md-3">'+
	                			'<div class="form-group">'+
	                			'<div class="input-group-prepend">'+
								'<select id= "' +ID +'" name = "' + Name + '" class="form-control text-input" </select>'+
								options+
								'</div>'+
		                		'</div>'+
		                		'</div>'
								);
						}
					else if(Type == 'multiselect'){
						var array = Values.split("-");
						var myOptions = [];
						for(var j = 0; j< array.length; j++) {
							myOptions.push({label: array[j] , value: array[j]});
						}
						$('#'+id).append('<div class="col-md-3">'+
	                			'<div class="form-group">'+
	                			'<div class="input-group-prepend">'+
	                			'<div id ="' + ID + '"></div>'+
	                			'</div>'+
		                		'</div>'+
		                		'</div>');
						VirtualSelect.init({
							  ele: ('#'+ID),
							  options: myOptions,
							  multiple: true,
							  placeholder: Name
							});
                		
						
                		
						}
					else if(Type == 'checkbox'){
						if(Values == 'checked')
						$('#'+id).append('<div class="col-md-3">'+
	                			'<div class="form-group">'+
	                			'<div class="input-group-prepend" data-target-input="nearest">'+
	                			'<div class="input-group-text">'+
	                			'<input  type = "checkbox" id ="' + ID +'" name ="' + Name +'" value = "' +Values +'" class = "input-group-text" checked>'+
	                			'<span style="margin-left: 10px;">' + Name + '</span>'+
	                			'</div>'+
		                		'</div>'+
		                		'</div>'+
		                		'</div>'
		                		);
						else
							$('#'+id).append('<div class="col-md-3">'+
		                			'<div class="form-group">'+
		                			'<div class="input-group-prepend" data-target-input="nearest">'+
		                			'<div class="input-group-text">'+
		                			'<input  type = "checkbox" id ="' + ID +'" name ="' + Name +'" value = "' +Values +'" class = "input-group-text" >'+
		                			'<span style="margin-left: 10px;">' + Name + '</span>'+
		                			'</div>'+
			                		'</div>'+
			                		'</div>'+
			                		'</div>'
			                		);
							
	                			
						}
					else if(Type == 'date'){
						$('#'+id).append('<div class="col-md-3">'+
								'<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">'+
								'<span class="input-group-text">'+Name+'</span>' + 
								'<input type="text" id="'+ID+'" readonly value="'+Values+'" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />'+
								'<div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">'+
								'</div>'+
		                		'</div>'+
		                		'</div>');
								
						}

                    }
                 

				

            	
        		
            	
                
            	
                
            }
        },
        error: function(result) {
            alert("Error");
        }
    });
	
	
	
	  
	}

const Collapse = async () => {
	  const result = await InputParamShow()
	  // do something else here after firstFunction completes
	  $('#TryTo').collapse('show');
	  
	  
	}






$(document).ready(function () {
    $('#toggleButton').click(function () {
        
    	alert(ReportValues);
    });
});


$("#ReportID").autocomplete({
    
    source: function(request, response) {

         var ReportName=$("#ReportName").val();
        
             $.ajax({
                 type: "GET",
                 contentType: "application/json; charset=utf-8",
                 url: '${pageContext.request.contextPath}/GetAllReport',
                 data: {
						"ReportID" : $("#ReportID").val(),
						"ReportName":ReportName,
				 },
                 dataType: "json",
                 success: function (data) {
                     if (data != null) {
                         response(data.ListGetReport);
                         
                         
                     }
                 },
                 error: function(result) {
                     alert("Error");
                 }
             });
         }, minLength:0, maxShowItems: 40, scroll:true,

			select: function(event, ui) {
				ReportID.value = (ui.item ? ui.item[0]  : '');
				ReportName.value=ui.item[1];
				Collapse();
				
				
				
				
				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[0] + "</span><br><span class='desc'>" +
                    item[1] + "</span></div>")
                .appendTo(ul);
        	};
			$("#ReportID").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	            	
   	            	
   	        	}						
			});

			$("#ReportName").autocomplete({
			    
			    source: function(request, response) {

			         var ReportName=$("#ReportName").val();
			        
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllReport',
			                 data: {
									"ReportID" : $("#ReportID").val(),
									"ReportName":ReportName,
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.ListGetReport);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 40, scroll:true,

						select: function(event, ui) {
							ReportID.value = (ui.item ? ui.item[0]  : '');
							ReportName.value=ui.item[1];
							InputParamShow();
							return false;
						}
					}).autocomplete("instance")._renderItem = function(ul, item) {
				            return $("<li class='each'>")
			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                    item[0] + "</span><br><span class='desc'>" +
			                    item[1] + "</span></div>")
			                .appendTo(ul);
			        	};
						$("#ReportName").focus(function(){
			   	        	if (this.value == ""){
			   	            	$(this).autocomplete("search");
			   	            	
			   	        	}						
						});

			









var navList = [];
var iCode = "${RuleID}";
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
		localStorage.setItem("SchedulerRulesList", JSON.stringify(listItemsNav));
		}else if(browser.includes("Safari") && megaBytes >5){
			localStorage.setItem("SchedulerRulesList", JSON.stringify(listItemsNav));
			}else if(megaBytes >10){
				localStorage.setItem("SchedulerRulesList", JSON.stringify(listItemsNav));
				}else{navList = listItemsNav;
					localStorage.removeItem("SchedulerRulesList");}
	
		findIndex();
	 }else{
		 navList = JSON.parse(localStorage.getItem("SchedulerRulesList"));
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
	
	
	d = document.getElementById("ReportDestination").value;
	
	const checkboxes = document.querySelectorAll('input[name="Weekdays"]:checked');
	let weekdayslist = [];
	checkboxes.forEach((checkbox) => {
		weekdayslist.push(checkbox.value);
	});
	WeekdaysString = weekdayslist.join("-");

	

	if ($("#RuleName").val()== '') {
		
		 alert('Rule Name cannot be NULL');
		 return false;}
	if ($("#ReportName").val()== '') {
		 alert('Report Name cannot be NULL');
		 return false;}
	if ($("#ReportID").val()== '') {
		 alert('Report ID cannot be NULL');
		 return false;}
	if ($("#ReportTimes").val()== '') {
		 alert('Report Times cannot be NULL');
		 return false;}

	if ($("#ReportWeekdays").val()== '') {
		 alert('Report Weekdays cannot be NULL');
		 return false;}
	if ($("#ReportMonthdays").val()== '') {
		 alert('Report Month Days cannot be NULL');
		 return false;}
	if ($("#Status").val()== '') {
		 alert('Status cannot be NULL');
		 return false;}

    if ($("#EmailTo").val()== '') {
		 alert('Email To cannot be NULL');
		 return false;}
	 
    if ($("#EmailFrom").val()== '') {
		 alert('Email From cannot be NULL');
		 return false;}
	 
     

	 val =$("#dateAutomated").val();
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
     if(val != null)
     val1 = Date.parse(val);
     
     
     	var IDName='"#';
		for(var i =0; i< LengthOfInputs;i++){

			FilterString += (IDs[i]);
			FilterString += ("=");
			if(document.getElementById(IDs[i]) != null){
			if(document.getElementById(IDs[i]).value.length ==0)
				FilterString+= "None";
			else
				FilterString+= document.getElementById(IDs[i]).value;
			}
			FilterString += ("=");
			FilterString += (Types[i]);
			FilterString += ("=");
			FilterString += (OptionsArray[i]);
			FilterString += ("\n");

			}
				
		
			$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/SchedulerRulesFormSave",
					dataType : "json",
					data : {

						"RuleID" : $("#RuleID").val(),
						"RuleName" : $("#RuleName").val(),
						"ReportID" : $("#ReportID").val(),
						"ReportName" : $("#ReportName").val(),
						"LastRunTime" : $("#LastRunTime").val(),
						"ReportTimes" :$("#ReportTimes").val(),
						"ReportWeekdays" :WeekdaysString,
						"ReportMonthdays" :$("#ReportMonthdays").val(),
						"ReportDestination" :$("#ReportDestination").val(),
						"EmailFrom" : $("#EmailFrom").val(),
						"EmailTo" : $("#EmailTo").val(),
						"EmailSubject" : $("#EmailSubject").val(),
						"PhoneNB" : $("#PhoneNB").val(),
						"Status" : $("#Status").val(),
						"TriggerTime": $("#TriggerTime").val(),
						"createdate": $("#dateAutomated").val(),
						"lastModifiedDate": $("#lstmodifdate").val(),
						"FilteredValues":FilterString,
						"ReportValues":ReportValues,
						

											
				},
					success : function(data) {
						
						console.log("all done "+ data.alitest);
						$('#lstmodifdate').val(data.lstmodifdate)
						console.log("the Rule ID data is"+data.RuleID);
						$('#RuleID').val(data.RuleID);
						var param ="${pageContext.request.contextPath}/SchedulerRulesFormView?RuleID="+$("#RuleID").val()+"&SchedulerRulesList=SchedulerRulesList";
						location.replace(param);
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

	});






$("#deleteButton").click(  function() {
	 console.log('delete now');
	 
var RuleID = document.getElementById("RuleID").value;		
	 
		$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/DeleteRuleFormView",
				dataType : "json",
				data : {
					"RuleID" :  $("#RuleID").val()
				},
				success : function(data) {
					
					location.replace("${pageContext.request.contextPath}/SchedulerRulesListView");
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
					if(localStorage.getItem("SchedulerRulesList") != null){
						if(localStorage.getItem("SchedulerRulesList").length != 0){
							var param ="${pageContext.request.contextPath}/SchedulerRulesFormView?RuleID="+navList[i];
							window.location.href =param;
							}		
						}else{
							var param ="${pageContext.request.contextPath}/SchedulerRulesFormView?RuleID="+navList[i]+"&SchedulerRulesList=SchedulerRulesList";
							window.location.href =param;
							}
					}
				
				}
	    });
$("#btnNext").click(function(){
		if(!$("#btnNexta").hasClass("disabled")){
			indexItm++;
			if(localStorage.getItem("SchedulerRulesList") != null){
			if(localStorage.getItem("SchedulerRulesList").length != 0){
				var param ="${pageContext.request.contextPath}/SchedulerRulesFormView?RuleID="+navList[indexItm];
				window.location.href =param;
				}		
			}
		else{
			var param ="${pageContext.request.contextPath}/SchedulerRulesFormView?RuleID="+navList[indexItm]+"&SchedulerRulesList=SchedulerRulesList";
			window.location.href =param;
			}
		}
		});
	
$("#btnPrv").click(function(){
		if(!$("#btnPrva").hasClass("disabled")){
		indexItm--;
		if(localStorage.getItem("SchedulerRulesList") != null){
		if(localStorage.getItem("SchedulerRulesList").length != 0){
			var param ="${pageContext.request.contextPath}/SchedulerRulesFormView?RuleID="+navList[indexItm];
			window.location.href =param;
		}
		}else{
			var param ="${pageContext.request.contextPath}/SchedulerRulesFormView?RuleID="+navList[indexItm]+"&SchedulerRulesList=SchedulerRulesList";
			window.location.href =param;
			}
		}
		});
	$("#label-1").text((indexItm+1)+"/"+navList.length);
	
	}	


    



</script>
     
    
</body>
</html>