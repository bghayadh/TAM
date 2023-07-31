<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Import Settings</title>
    
    
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	
	
 <style>
 
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
				   
				}
				
*,
*:before,
*:after {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}


body {
  margin: 0;
  
  font: "PT Sans", Arial, sans-serif;
  color: #5a5a5a;
}

	

			
</style>	
			
</head>



<body>

<%-- <c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
	

	<div class="row" style="margin-left:2%;">
	

	<div class="col-md-10">
		<div class="form-group">
			<div class="input-group-prepend">
					<label class="file">
  					<input type="file" id="file" class="file" aria-label="File browser example" >
  					<span class="file-custom" id="file1"></span>
					</label>
					
			</div>
		</div>	
	</div>
</div>

<div class="row" style="margin-left:2%;">
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
					<span class="input-group-text" >Source File Type</span>
							<select   id="sourcefile"  name="SourceFile" class="SourceFile form-control text-input" >
				
    				 <option value="">Please Select File Type</option>
    				 
			           <option value="csv" >.csv</option>
			            <option value="xls" >.xls</option>
			             <option value="txt" >.txt</option>
			             <option value="xlsx" >.xlsx</option>
    				
  				</select>
			</div>
		</div>	
	</div>
	
		<div class="col-md-5">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" >Destination Database Table</span>
					<select  id="selectdb" name="SelectDb" class="form-control text-input" >
  						<option value="">Please Select Destination Table</option>
					</select>
  				</div>
			</div>
		</div>
		
	
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<input type="submit" id="apply" class="input-group-text"  value="Apply" style="width:120px;display:inline-block;background-color:lightgray;">
					
				</div>
			</div>
		</div>
 	</div>
 	
 		<div class="form-group">
			<div class="input-group-prepend">
 				<div id="viewDiv" style="margin-left:3%;margin-top:4%;">
 		
 				<table id="table"> </table>
 	
 				<label id="buttonRow"></label>
 				
 				</div>
 			</div>
 		</div>
 
 	
 	<script>
 
	
 	
 	/*function checkfile(sender) {
 	    var validExts = new Array(".txt", ".xls", ".csv");
 	    var fileExt = sender.value;
 	    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
 	    if (validExts.indexOf(fileExt) < 0) {
 	      //alert("Invalid file selected, valid files are of " +
 	               validExts.toString() + " types.");
 	      return false;
 	    }
 	    else return true;
 	}
*/
	//var selectList=[];
	//selectList=${LisTables};
	//alert("hhhh"+selectList);
	
	
 
			
			
					dict = [];
					var dictObj = {};

					
					$('#file').prop('disabled', true);
					let selector=document.querySelector(".SourceFile");

			 		selector.addEventListener('change', () => {
			 			$('#file').prop('disabled', false);
				//alert($("#sourcefile").children("option:selected").val());

				 	var htmlElement = document.getElementById("file");

				 	if($("#sourcefile").children("option:selected").val()=="csv"){

						htmlElement.setAttribute("accept", ".csv");
				 	}
				 	else if($("#sourcefile").children("option:selected").val()=="xls"){
				 		htmlElement.setAttribute("accept", ".xls");
				 	}

					else if($("#sourcefile").children("option:selected").val()=="xlsx"){
				 		htmlElement.setAttribute("accept", ".xlsx");
				 	}

				 	else if($("#sourcefile").children("option:selected").val()=="txt") {
				 		htmlElement.setAttribute("accept", ".txt");
				 	}

				 	else{
				 		$('#file').prop('disabled', true);
				 	}
				
			 });


			 

			 		let input = document.querySelector('input');
					input.addEventListener('change', () => {

						let files = input.files;
						 
						if(files.length == 0) return;
						 
						const file = files[0];
						 
						let reader = new FileReader();
						 
						reader.onload = (e) => {
						        const file = e.target.result;
						        const lines = file.split(/\r\n|\n/);
						       
						    	
						 		for( j=1;j<lines.length;j++){
							 		if(lines[j].length!=""){
						       				const rows = lines[j].split(";");
											dictObj=rows;
						       				dict.push(dictObj);
						       				console.log(dictObj);
											dictObj = {};
							 			}
						        
						   			}
						       	
						   		 }
						   reader.onerror = (e) => alert(e.target.error.name);
						 
						   reader.readAsText(file); 

						
						   

						  

							   
					$('#apply').click(function(){
						if($("#selectdb").children("option:selected").val()=='')
							{
							alert("Please complete the form !!!");
							return false;
							}
						

			 $.ajax({
			        type: "GET",
			        contentType: "application/json; charset=utf-8",
			        url: '${pageContext.request.contextPath}/getExcelMHandHHData',
// 			        url: '${pageContext.request.contextPath}/getcsvData',
			        
			        data: {
						"selectedValue" : $("#selectdb").children("option:selected").val(),
						//"dictParameter":dict
					},
					success : function(data) {


						fieldsNames=[];
						console.log("FROM TABLE NAMEEEEEEEE==> " +$("#selectdb").val());
						fieldsNames=data.fieldsNames;
						//console.log(data.fieldsNames);
						
						let files = input.files;
						 
						if(files.length == 0) return;
						 
						const file = files[0];
						 
						let reader = new FileReader();
						 
						reader.onload = (e) => {
						        const file = e.target.result;
						        const lines = file.split(/\r\n|\n/);
						        var table;

						    	var elmtTable = document.getElementById('table');
								var tableRows = elmtTable.getElementsByTagName('tr');
								var rowCount = tableRows.length;

								for (var x=rowCount-1; x>=0; x--) {
								   			elmtTable.removeChild(tableRows[x]);
												 }
								 
						      	const columns = lines[0].split(";");
						      	for(var j=0;j<columns.length;j++){
						        			cols=columns[j];
						        			//console.log("column"+j+" "+cols);

						        			table="<tr><td style='width:20em;'><span id='colSpan' class='colSpan input-group-text'>"+cols+"</span></td><td><select onchange='removeChoice(this)' style='width:30em;' id='selectCol' name='SelectColumn' class='selectColumnn form-control text-input'><option value=''>Please Select Column </option>";
						        			
						        			
						        		for(var i=0;i<fieldsNames.length;i++)
						        				{
								        	//console.log(fieldsNames[i]);
						        			table=table+"<option value='"+fieldsNames[i]+"'>"+fieldsNames[i]+"</option>";
								        		}
						        			table=table+"</select></td></tr>";
						        			$('#table').append(table);
											
									    }
						      	
			        			
							    document.getElementById("buttonRow").innerHTML="<br><input type='submit' class='input-group-text' value='Apply' style='width:120px;display:inline-block;background-color:lightgray;'>";
						      	
						   		 }
						   reader.onerror = (e) => alert(e.target.error.name);
						 
						   reader.readAsText(file); 
						},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

		
				
			
		 });
					  
					   
	});    
					//let input = document.querySelector('input');
					//input.addEventListener('change', () => {	


	 
					/*	$("#table > tr > td").find('select[name="SelectColumn"]').on('change',function(){
							alert('changed');
						    var value=$(this).val();
						    $("#table >tr >td").find('select[name="SelectColumn"]').not(this).each(function(){
						        $(this).find('option[value='+value+']').remove();
						    });
						});
						*/
					$('#apply').click(function(){

						  var Val = $('#file').val(); 
					        if(Val=='') 
					        { 
					        	alert("Please complete the form !!!");
								return false;

					        } 
					});	
						

						function removeChoice(event) {
							var defaultValue="";
							if($("select option").not(this).find('option[value="'+defaultValue+'"]')!=""){
							    $("select option").removeAttr('disabled');
							
							    $("select").each(function(){
							       $("select").not(this).find('option[value="'+$(this).val()+'"]').attr('disabled','disabled');
							    });
							}
							else return false;
						       }

								
			$('#buttonRow').click(function(){
				var uncomplete=0;
				//dictt=[];
				//var dictObj={};
				var values = new Array();
				var items;

				
				

				
					//$(".colSpan").each(function() {
						$.each($("select[name='SelectColumn']"), function() {
							 values.push($(this).val());
						//dictObj.value=$(this).val();

						//dictObj.value1 = "kk";
						//console.log("objectt"+dictObj);
						//dictt.push(dictObj);
						
						
				if($(this).children("option:selected").val()=='')
							{
								 uncomplete++;
							}
					else{ 
						uncomplete=uncomplete;
						
						
						}
				
							});
						items = values.join(',');
					barArrayObj = {};
					//console.log("dictionary"+dictt);
					
					if(uncomplete!=0)
							{
					alert("Please fill the fill the form!!1");
					return false;
					
						}
					

					$.ajax({
				        type: "GET",
				        contentType: "application/json; charset=utf-8",
				        url: '${pageContext.request.contextPath}/insertData',
				        data: {

							
				        	"selectedValue" : $("#selectdb").children("option:selected").val(),
							//
							"dictParameter":dict,
							//"dictParameterbarcode":dictt,
							"items":items,
							"values":values,
							
						},
						success : function(data) {

							console.log("FROM");
							
							},
						error : function(error) {
							console.log("The error is " + error);
						}
					});


					});

					
					
				//	$('#buttonRow').click(function(){
					//		$('#selectCol').each(function() {
									

							//		if(selectedVal=='')
									//	{
								//			alert("Please complete the form !!!");
								//		return false;
									//	}
									//});
							//});
/*document.addEventListener('DOMContentLoaded', function () {
   var input = document.getElementById('selectdb');
   if (localStorage['selectdb']) { // if job is set
       input.value = localStorage['selectdb']; // set the value
   }
   input.onchange = function () {
        localStorage['selectdb'] = this.value; // change localStorage on change
    }
});*/

//
	

	$(document).ready(function() {
		
	
		$('.selectColumnn').change(function() {
			alert("changed");
			$('.selectColumnn').not(this).each(function(){
			  dropdownval = $(this).val();
			  $('.selectColumnn').not(this).find('option[value="' + dropdownval + '"]').remove();
			});
		});


		
		
/*if('${selectedValue}' == "apply")
{
	var Value='${selectedDb}';
	$("#selectdb").val(Value);
		}
		*/
		//selectList=[];
		//selectList = tablesListSelect(selectList);
		
		selectList = ${Liliane_ListTables};

		
		//alert(selectList);
		//alert(selectList.length);
		
		
	   
	   		 var tableNameItem="";
			 for (i = 0;i<selectList.length;i++){
		 	   		var tablename = selectList[i];
		 	   	//alert(tablename);
		 	   	 tableNameItem +="<option value='"+tablename+"'>"+tablename+"</option>";
		 	   
		 	   	
		 		    }
	 		   // console.log("tableNameItem is: "+tableNameItem);
			 $("#selectdb").append(tableNameItem);

		

				
		
	});




	</script>
	
	

</body>
</html>