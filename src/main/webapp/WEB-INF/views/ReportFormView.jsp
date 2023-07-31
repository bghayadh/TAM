<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@ include file="scripts.html" %>	
     <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">	 
<!--     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	<style>
	
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
	  th{text-align:center;} 
	td {text-align: center;vertical-align: middle!important;}
	
	 .hide-row { display:none; }
	li.nav-item a span.active svg
	{
		color:#20B2AA !important;
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
		.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 100px;
					z-index:9999;
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
	
	.elementName
	{
		width: 180px !important;
	}
	
	.inputWidth
	{
		width:100px !important;
	}
	
	#discountAmount
	{
		width: 126px !important;
	}
 	</style>
</head>
<body>

<%--   <c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>

<%-- <c:set var="pg" value="setup" scope="session"  /> --%>
<%-- <jsp:include page="${request.contextPath}/headerController"></jsp:include> --%>
	
	
	 <p></p>
<div class="container-fluid">
		<div class="row">
			
		
			<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Report ID</span>
							<input type="text" readonly id="rprtId"  value="${rprtId}" class="form-control text-input"   />
 							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" /> 
						</div>
					</div>
			</div>


	<div class="pad col-md-3 hide-row"></div>
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Reports</span>
				  	<select id="selectnav" class="form-control select2"></select>
				</div>
			</div>
		</div>		
	<div  class="col-md-3 text-right"></div> 
				
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
					<input type="text" id="creationDate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span style="width:200px;" class="input-group-text">Last Modify Date</span>
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
			    		<li id="btnPrv" class="page-item " style="margin-right: 2px;"><a style="width:120px;" id="btnPrva" href="#" class="btn btn-success previous">&laquo; Previous</a></li>				
			    		<li id="btnNext" class="page-item" style=" padding-right: 0px ! important;"><a style="width:120px;" id="btnNexta" style="width:100px;" href="#" class="btn btn-success next">Next &raquo;</a></li>
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
			        			onclick='window.location.href = "${pageContext.request.contextPath}/ReportListView"'
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
      
        <li class="nav-item"><a class="nav-link"
            id="custom-tabs-param-tab" data-toggle="tab"
            href="#custom-tabs-param" role="tab"
            aria-controls="#custom-tabs-param" aria-selected="false" style="color: gold;">PARAMETERS</a></li>
      

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
    	<!--  create table purchaserequestItem    -->
<div class="row">

	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Name</span>
						<input type="text" id="name" value="${RprtName}" class="form-control text-input" />
					
					</div>
				</div>
	</div>


	
 </div>



	
	<div class="row">	
	
	
	 						<div class="col-md-4">
                                <div class="input-group-prepend">
                                <span style="width:200px;" class="input-group-text">Description</span>
                                                                                              
                                </div>
                            </div> 

	</div>
	<div class="row">
	<div class="col-md-4"><textarea name="rprtdescrip" cols="120" rows="7" id="descrip" class="form-control text-input">${Rprtdescription}</textarea></div>
	</div>
	
	
	
		</div>

 <div class="tab-pane fade" id="custom-tabs-param" role="tabpanel" aria-labelledby="custom-tabs-param-tab">

<p></p>
<div> 
  
<form>
			
<p></p>


<div id="parameters">
		
			<div class="table-responsive-sm"> 
			  <table id ="ParamTab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
						     <thead>
						         <tr>
						            <th>
								        <button type="button" id="selectAllparam" class="main">
								        <span class="sub"></span>Select</button></th>
<!--  								         <th style="text-align:center" width="150px">Param Input ID</th> -->
						                <th style="text-align:center" width="150px">Label</th>
										<th style="text-align:center" width="150px">Type</th>
										<th style="text-align:center" width="150px">ID</th>
										<th style="text-align:center" width="150px">Name</th>
										<th style="text-align:center" width="150px">Param Input ID</th>
<!-- 										<th style="text-align:center" width="150px">Report ID</th> -->
										
										
								       
						            </tr>
						        </thead>
								
								<tbody>
						            
									
						        </tbody>
						      
						    </table>
						    <p id="my_error" style="color:red;"></p>
					    </div>
<input type="button" class="add-row-param" id="adds_row-LFP" value="Add Row">
<button type="button" class="delete-row">Delete Row</button>
<input type="text" id="RowIndexLFP" hidden  value="">


</div>
</form>
                   
</div>

<p></p>
</div>

</div>
	
	
</div>
	
        
 </body>
 
 
 
 <script type='text/javascript'>


 
 $("#deleteButton").click(  function() {
	 var reportID = document.getElementById("rprtId").value;
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ReportFormViewDelete",
			dataType : "json",
			data : {
				"reportID" : $("#rprtId").val()
			},
			success : function(data) {

			    location.replace("${pageContext.request.contextPath}/ReportListView");
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
			function SetIndexRow(obj)
			{
				var row_index = $(obj).parent().parent().index();
				$("#RowIndex").val(row_index);
			}

 function SetIndexRowLFP(obj)
	{
		var row_index = $(obj).parent().parent().index();
		$("#RowIndexLFP").val(row_index);
	}
 
 
		var navList = [];
		var iCode = "${rprtId}";
		var listItemsNav = ${listItemsNav};
		var indexItm;
		console.log("listItemsNav"+listItemsNav)
		if(iCode !=""){
		if(listItemsNav != "noList" && listItemsNav.length >1){
			var size = new TextEncoder().encode(JSON.stringify(listItemsNav)).length;
			var kiloBytes = size / 1024;
			var megaBytes = kiloBytes / 1024;
			var browser = platform.name;
			if(browser == "Samsung Internet" && megaBytes >2){
				localStorage.setItem("listItemsNav", JSON.stringify(listItemsNav));
				}else if(browser.includes("Safari") && megaBytes >5){
					localStorage.setItem("listItemsNav", JSON.stringify(listItemsNav));
					}else if(megaBytes >10){
						localStorage.setItem("listItemsNav", JSON.stringify(listItemsNav));
						}else{navList = listItemsNav;
							localStorage.removeItem("listItemsNav");}
			
				findIndex();
			 }else{
		 navList = JSON.parse(localStorage.getItem("listItemsNav"));
		 if(navList !=null && navList.length >1){
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
				if(localStorage.getItem("clientList") != null){
					if(localStorage.getItem("clientList").length != 0){
						var param ="${pageContext.request.contextPath}/ReportFormView?rprtId="+navList[i];
						window.location.href =param;
						}		
					}else{
						var param ="${pageContext.request.contextPath}/ReportFormView?rprtId="+navList[i]+"&listItemsNav=listItemsNav";
						window.location.href =param;
						}
				}
			
			}
    });


 $("#btnPrv").click(function(){
 	if(!$("#btnPrva").hasClass("disabled")){
 	indexItm--;
 	if(localStorage.getItem("warehouseList") != null){
 	if(localStorage.getItem("warehouseList").length != 0){
 		var param ="${pageContext.request.contextPath}/ReportFormView?rprtId="+navList[indexItm];
 		window.location.href =param;
 	}
 	}else{
 		var param ="${pageContext.request.contextPath}/ReportFormView?rprtId="+navList[indexItm]+"&listItemsNav=listItemsNav";
 		window.location.href =param;
 		}
 	}
 	}); 
 $("#label-1").text((indexItm+1)+"/"+navList.length);
  	


 $("#btnNext").click(function(){
 if(!$("#btnNexta").hasClass("disabled")){
 indexItm++;
 if(localStorage.getItem("warehouseList") != null){
 if(localStorage.getItem("warehouseList").length != 0){
 	var param ="${pageContext.request.contextPath}/ReportFormView?rprtId="+navList[indexItm];
 window.location.href =param;
 }		
 }
 else{
 	var param ="${pageContext.request.contextPath}/ReportFormView?rprtId="+navList[indexItm]+"&listItemsNav=listItemsNav";
 window.location.href =param;
 }
 }
 });


 
}


 
if ('${ListPRqItem}' == "addNew") {
	 $("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});
}
else{}

	
 $(document).ready(function() {
	 console.log("test done");
		var unsaved = false;	

		$("#descrip").on('keyup change', function () {
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
			 unsaved = true;
			});
					 
		 $("input").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				 unsaved = true;
				});


			$("#ParamTab > tbody").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	

	 		$(window).bind('beforeunload', function() {
		    if(unsaved){
		        return "You have unsaved changes. Do you want to leave and discard?";
		    }
		});

	 		$(document).on('change', ':input', function(){
			    unsaved = true;
			});

		        $(".add-row-param").click(function(){
	         
					var markup = "<tr><td><input type='checkbox' name='record'><span class='dotStatus' id='dotStatus'></span></td>"
 						
						+"<td name='InputLabel'>"
						+"<input name='inputlabel' type='text' style='width:200px;' class='form-control text-input'/></td>"
						+"<td name='InputType'>"
						+"<input name='inputtype' type='text' style='width:200px;' class='form-control text-input'/></td>"
						+"<td name='InputId'>"
						+"<input name='inputid' type='text' style='width:200px;' class='form-control text-input'/></td>"
						+"<td name='InputName'>"
						+"<input name='inputname' type='text' style='width:200px;' class='form-control text-input'/></td>"
// 						+"<td name='ReportParamId'><input name='ReportParamId' type='text' readonly  style='width:200px;' class='form-control text-input'></td>"
						+"<td name='ParamId'><input  name='paramId'  type='text' readonly style='width:200px;' class='form-control text-input'></td>"
						+"</tr>";
			            $("#ParamTab > tbody").append(markup);
// test breacket here
	        				$("#formStatus").text("Not Saved");
							$('.dot').css({"background-color" : "orange"});
							 unsaved = true;
		            $('#ParamTab tr td input').on('focus', function() {

						SetIndexRow(this);
					});   

		            function customRenderItem(ul, item) {
		    			var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
		    				result = $('<li class="ui-menu-item" role="menuitem"></li>')
		    				.data('item.autocomplete', item)
		    				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
		    				.appendTo(ul);

		    			return result;
		    		}
		 

	 }); 

				$('body').on('click', '#selectAllparam', function  () {
					   
  					if ($(this).hasClass('allChecked')) {
     				$('input[type="checkbox"]', '#ParamTab').prop('checked', false);
  						} else {
   						$('input[type="checkbox"]', '#ParamTab').prop('checked', true);
   						}
   						$(this).toggleClass('allChecked');
   				
 					})
			   	 
		        var slctDel= [];
		        $(".delete-row").click(function(){
		        	
		        	var checked="false"; //when no checkbox is checked
		            var pri_Pk = "";  
				   
		       	   	$("#ParamTab > tbody").find('input[name="record"]').each(function(){

		             if($(this).is(":checked")){ 
		            	 checked="true"; //when 1 or more checkbox is checked
		            			 
		       				pri_Pk=$(this).parent().parent().children('td[name="ParamId"]').children('input').val();
		       				if( pri_Pk !=0)
		       					slctDel.push(pri_Pk);
		     			       
								  
		       				$(this).parents("tr").remove();  
		       			console.log("deleted slctDel:"+ slctDel);
			    	}   //end of checked box in boq for delete
		       			});
		       	   	if(checked=="false"){
											  
		       	 	alert(' Select Row(s) to Delete');
					
		         	return false;
		       	   	}
		       	   	
					            
		        });// end delete row
		        var dict = [];
		 		 //main SAVE button
		 		$(document).on('click', '#saveButton', function() {
		 			 unsaved = false;
		 			  if ($("#name") == null) {
				          alert(' Name is not valid');
				          return false;
				        
				        }


		 			 if ($(this).hasClass('allChecked')) {
		   				$('input[type="checkbox"]', '#ParamTab').prop('checked', false);
		 						} else {
		 						$('input[type="checkbox"]', '#ParamTab').prop('checked', true);
		 						}
		 						$(this).toggleClass('allChecked');

					 
		 			  if ($("#name").val() == '')  { alert(' Name is not valid');
			          return false;  }
		 			  if ($("#descrip").val() == '')  { descrip.value = " "; }
							 		
		 			 getselectedrows();
		 			 saverowsintables();
						 
					 
					 
		 		 }); // end of main SAVE button
		 	    //function to  get selected rows for save
		 		function getselectedrows () {
		 			dict = [];
		 			
		 		
		 	$("#ParamTab > tbody").find('input[name="record"]').each(function(){

		 	 dict.push({
			"ParamID":$(this).parent().parent().children('td[name="ParamId"]').children('input').val(),
		 	"InputLabel" : $(this).parent().parent().children('td[name="InputLabel"]').children('input').val(),
		 	"InputType" : $(this).parent().parent().children('td[name="InputType"]').children('input').val(),
		 	"InputId" : $(this).parent().parent().children('td[name="InputId"]').children('input').val(),
		 	"InputName" : $(this).parent().parent().children('td[name="InputName"]').children('input').val(), 
		 //	"ReportParamId" : $(this).parent().parent().children('td[name="ReportParamId"]').children('input').val()
		    	});
		 			});		               
		 		}//end of selectedrows

				  if ('${ListRprtItem}' != "addNew") {

					  
						 
					  boqArray = [];
					  boqArray = ${ListRprtItem};

					  for (i = 0;i<boqArray.length;i++){


						  var id =  boqArray[i].id;
				   	   		if(id == null){
				   	   		id = "";
				   	   		}
				   	   		else{
				   	   		id =  boqArray[i].id;
				   	   		}
						 
	 				      		var label =  boqArray[i].inputLabel;
	 				   	   		if(label == null){
	 				   	   		label = "";
	 				   	   		}
	 				   	   		else{
	 				   	   		label =  boqArray[i].inputLabel;
	 				   	   		}

	 				   	 	var type =  boqArray[i].inputType;
	 			   	   		if(type == null){
	 			   	   		type = "";
	 			   	   		}
	 			   	   		else{
	 			   	   		type =  boqArray[i].inputType;
	 			   	   		}

	 						  var inputid =  boqArray[i].inputID;
					   	   		if(inputid == null){
					   	   		inputid = "";
					   	   		}
					   	   		else{
					   	   		inputid =  boqArray[i].inputID;
					   	   		}

								  var name =  boqArray[i].inputName;
						   	   		if(name == null){
						   	   		name = "";
						   	   		}
						   	   		else{
						   	   		name =  boqArray[i].inputName;
						   	   		}

// 						   		  var rpid =  boqArray[i].reportId;
// 						   	   		if(rpid == null){
// 						   	   		rpid = "";
// 						   	   		}
// 						   	   		else{
// 						   	   		rpid =  boqArray[i].reportId;
// 						   	   		}


								var markup = "<tr><td><input type='checkbox' name='record'></td>"
			 						
									+"<td name='InputLabel'>"
									+"<input name='inputlabel' type='text' value='"+label+"' style='width:200px;' class='form-control text-input'/></td>"
									+"<td name='InputType'>"
									+"<input name='inputtype' type='text' value='"+type+"' style='width:200px;' class='form-control text-input'/></td>"
									+"<td name='InputId'>"
									+"<input name='inputid' type='text' value='"+inputid+"' style='width:200px;' class='form-control text-input'/></td>"
									+"<td name='InputName'>"
									+"<input name='inputname' type='text' value='"+name+"' style='width:200px;' class='form-control text-input'/></td>"
//			 						+"<td name='ReportParamId'><input name='ReportParamId' type='text' readonly  style='width:200px;' class='form-control text-input'></td>"
									+"<td name='ParamId'><input  name='paramId'  type='text' readonly value='"+id+"' style='width:200px;' class='form-control text-input'></td>"
									+"</tr>";
									$("#ParamTab > tbody").append(markup);

					  }
					  
						   
				}


		 		
	            function saverowsintables (){
        			
	    		     var token =  $('input[name="csrfToken"]').attr('value'); 
	 			    
	 		        //save rowsin tables
	 				$.ajax({
	 					type : "POST",
	 					url : "${pageContext.request.contextPath}/ReportFormSave",
	 					dataType : "json",
	 					  headers: {
	 	                        'X-CSRFToken': token 
	 	                    },
	    					data : {
	    						"type": '${ListPRqItem}',        				
	        					"dictParameter" : dict,
	        					"slctDel": slctDel,
	    						"rprtId" : $("#rprtId").val(),
	    						"creationDate" : $("#creationDate").val(),
	    						"lastmodifiedDate" : $("#lastModifiedDate").val(),
	    						"name" : $("#name ").val(),
	    						"description": $("#descrip").val(),

	    					},
	    					success : function(data) {
	    						console.log("The returned data is " + data.Iresponse);
	    						
	    						$('.dot').css({"background-color" : "orange"});					
	    						$("#rprtId").val(data.rprtId);
	    						
	    						var param ="${pageContext.request.contextPath}/ReportFormView?rprtId="+$("#rprtId").val()+"&listItemsNav=listItemsNav";
	    						location.replace(param); 						    						
	    					},
	    					error : function(error) {
	    						console.log("The error is " + error);
	    					}
	    				});
	    				
	          
	           		}   //end save data
		        
	 		
		  });
//end document ready


 

</script>
			 

				
</html>