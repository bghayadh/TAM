<div style="padding-right: 10px;padding-left: 10px;padding-top: 10px;padding-bottom: 0px;">

<div class="row">
			
			<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Area ID</span>
							<input type="text" id="areaId" class="form-control text-input"  readonly />
						</div>
			
				  </div>
			</div>
			<div class="col-md-4"></div>
		
		
			<div  class="col-md-4 text-right"  >
				<i>&nbsp</i><span class="dot"></span>
				<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
			</div> 
		
</div>


<div class="row">
		
		
	 <div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
					<input type="text" id="createdate" readonly class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly  class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
						</div>
				</div>
		</div>
	</div>
			
			
			
			
			<div class="col-md-4" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/AreaListView"'
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
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: auto;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab-area" data-toggle="tab"
            href="#custom-tabs-one-home-area" role="tab"
            aria-controls="custom-tabs-one-home-area" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            
            
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-finance-tab-area" data-toggle="tab"
            href="#custom-tabs-finance-area" role="tab"
            aria-controls="#custom-tabs-finance-area" aria-selected="false" style="color: gold;">FINANCE</a></li> 
       
            
            <li class="nav-item ml-auto">
				<button type="button" id="deleteButtonArea"
				class="btn btn-primary BtnActive">
				<i class="fa fa-trash"></i> Delete
				</button>  
                        
				<button type="button" id="saveButtonArea"
				class="btn btn-primary BtnActive">
				<i class="fa fa-save"></i> Save
				</button>  </li>
							
     </ul>
     
</div>
</div>


<div class="container-fluid">



<div class="tab-content" id="custom-tabs-one-tabContent">

<div class="tab-pane fade show active" id="custom-tabs-one-home-area"
	role="tabpanel" aria-labelledby="custom-tabs-one-home-tab-area">
	
	<p></p>
    
  	<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Area Name</span>
							<input type="text" id="areaname" class="form-control text-input"   />
					</div>
				</div>
			</div>
	
	
	</div>
	
	<p></p>
		<div class="row">
				<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Longitude</span>
								<input type="text" id="arealng" class="form-control text-input"  />
							</div>
						</div>
				</div>
		</div>
		
		
		<p></p>
		<div class="row">
				<div class="col-md-6">
						<div class="form-group">
							<div class="input-group-prepend">
							<span class="input-group-text">Latittude</span>
							<input type="text" id="arealatt" class="form-control text-input"/>
							</div>
						</div>
				</div>
	     </div>
	
	
	
	
	</div>
	
	



<div class="tab-pane fade" id="custom-tabs-finance-area" role="tabpanel" aria-labelledby="custom-tabs-finance-tab-area">


<div> 
<p></p>
				<form>
				
								
					    <div class="table-responsive-sm" style="margin-top:50px;"> 
						    <table id ="yaratab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						                <th>
								          <button type="button" id="areaselectAll" class="main">
								          <span class="sub"></span>Select</button></th>
						                <th>Start Date </th>
						                <th>End Date</th>
						                <th>No of 2G Sites</th>
						                <th>Profit And Loss</th>
						                <th>No of 2G & 3G Sites</th>
						                <th>Profit And Loss</th>
						                <th>No of 2G & 3G & 4G Sites</th>
						                <th>Profit And Loss</th>
						                <th>Total No Of Sites</th>
						                <th>Sum Of Profit And Loss</th>
						              <th>ID</th>
						                
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
					    <input type="text" id="RowIndex" value="" hidden>
						<input type="button" class="add-row-area" value="Add Row">
					    <button type="button" class="delete-row-area">Delete Row</button>
                   </form>
		</div>




</div>

</div>
</div>
</div>

<script>
$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});

$(".add-row-area").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});
	
$(".delete-row-area").click(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});	

$("#yaratab > tbody").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});	

$(document).ready(function(){

	

	$("#deleteButtonArea").click(  function() {
		 console.log('delete now');
		 selectALLrows();
			
		 
			$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/DeleteAreaFormView",
					dataType : "json",
					data : {
						"AreaID" :  $("#areaId").val()
					},
					success : function(data) {
						location.reload(true);						
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

	})
	
	$("#saveButtonArea").click(  function() {
		alert("enter save");
		alertFlag='0';
		if ($("#areaname").val()== '') {
			 alert('Area Name cannot be NULL');
			 return false;}

		 selectALLrows();
		 getselectedrows ();
		 if (alertFlag =='1') {
			 console.log("Passed from this function of alert");
		     return false; 
		     }
		 if (alertFlag =='2') {
			 alert("**This ID is already Taken Please Enter Another ID");
		     return false; 
		     }
		 saverowsintables();
		 });

	
	 $(".delete-row-area").click(function(){
		 var slctDel=[] ;  
		 console.log("enter 1");
		 $("#yaratab > tbody").find('input[name="record"]').each(function(){
	        if($(this).is(":checked")){
	       	slctDel.push({"Id" : $(this).parent().parent().children('td[name="id"]').children('input').val()});
	            
	        }
		 });

	  	for (i = 0; i <= slctDel.length; ++i) {
	  	  	console.log("enter 3");
		           if (slctDel.length == 0) {
		            	alert(' Select Row(s) to Delete');
		          		return false;
		          }
	  	}
		 
	    $("#yaratab > tbody").find('input[name="record"]').each(function(){
	    	console.log("enter 4");
	        if($(this).is(":checked")){
	            $(this).parents("tr").remove();
	        }
	    });
		   
		            
		});


$(".add-row-area").click(function(){
	
	var markup = "<tr><td><input type='checkbox' name='record'></td><td name='startDate'>"
		+"<input name='startdate' type='date' style='width:400px;' /></td>"
		
		+"<td name='endDate'>"
		+"<input name='enddate' type='date' style='width:400px;' /></td>"
		+"<td name='no2gSites'>"
        +"<input  type='text' style='width:250px;' value= 0></td>"
		+"<td name='profitAndLoss2g'><input style='width:250px;'type='text' value= 0></td>"
		+"<td name='no2g3gSites'><input  style='width:250px;' type='text' value= 0></td>"
			+"<td name='profitAndLoss2g3g'><input style='width:250px;' type='text'value= 0></td>"
		+"<td name='no2g3g4gSites'><input  style='width:250px;' type='text' value=0></td>"
		+"<td name='profitAndLoss2g3g4g'><input type='text'  style='width:250px;' value= 0></td>"
		+"<td name='totalSites'><input type='text' style='width:250px;' value= 0 ></td>"
		+"<td name='totalSumProfit'><input type='text' style='width:250px;' value= 0 ></td>"
		+"<td name='id' ><input name='id'type='text' style='width:250px;'value= 0 readonly></td></tr>";
	    $("#yaratab > tbody").append(markup);
   
		$('#yaratab tr td input').change(function() {
			console.log("Passes here");
			var cell = $(this).val();
			var column_name = $(this).parent().attr('name');
			
            if ((column_name == 'no2gSites') ) {
	            console.log("Passes here");
                  // validate Qty
					 if (($. isNumeric(cell))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}
					 
			}
            if ((column_name == 'profitAndLoss2g') ) {
                  // validate Qty
					 if (($. isNumeric(cell ))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}
					 
			}
            if ((column_name == 'no2g3g4gSites') ) {
                  // validate Qty
					 if (($. isNumeric(cell ))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}
			
			}
            if ((column_name == 'no2g3gSites') ) {
                  // validate Qty
					 if (($. isNumeric(cell ))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}


			}
        
            if ((column_name == 'profitAndLoss2g3g') ) {
                  // validate Qty
					 if (($. isNumeric(cell ))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}								 
			}


            if ((column_name == 'profitAndLoss2g3g4g') ) {
                  // validate Qty
					 if (($. isNumeric(cell ))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}
					 
			}
            if ((column_name == 'totalSites') ) {
                  // validate Qty
					 if (($. isNumeric(cell ))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}


					 
					 
			}
            if ((column_name == 'totalSumProfit') ) {
                  // validate Qty
					 if (($. isNumeric(cell ))== false) {
					 alert('Qty is  not Numeric');
					 this.focus();
					 return false;}
					 
			}

		});
   
});




$('body').on('click', '#areaselectAll', function  () {
	   
	if ($(this).hasClass('allChecked')) {
	$('input[type="checkbox"]', '#yaratab').prop('checked', false);
		} else {
		$('input[type="checkbox"]', '#yaratab').prop('checked', true);
		}
		$(this).toggleClass('allChecked');

	});

function selectALLrows () {
	if ($(this).hasClass('allChecked')) {
				$('input[type="checkbox"]', '#yaratab').prop('checked', false);
					} else {
					$('input[type="checkbox"]', '#yaratab').prop('checked', true);
					}
					$(this).toggleClass('allChecked');
			}

function getselectedrows () {
	
	dict = [];
	
	$("#yaratab > tbody").find('input[name="record"]').each(function(){
		var StartDate =($(this).parent().parent().children('td[name="startDate"]').children('input').val());
		console.log("/*"+StartDate);
	
	  if (StartDate == "") {   
		    alert('Start Date  cannot be null in Table at Row: ' +($(this).parent().parent().index()+1));
		     alertFlag ='1';
		   return false;
		  
	    }

	  var EndDate =($(this).parent().parent().children('td[name="endDate"]').children('input').val());
		console.log("/*"+StartDate);
	
	  if (EndDate == "") {   
		    alert('End Date  cannot be null in Table at Row: ' +($(this).parent().parent().index()+1));
		     alertFlag ='1';
		   return false;
		  
	    }
	
	
		if($(this).is(":checked")){
			
		    dict.push({
			    "StartDate" : $(this).parent().parent().children('td[name="startDate"]').children('input').val(),
			    "EndDate" : $(this).parent().parent().children('td[name="endDate"]').children('input').val(),
			    "2GSites" : $(this).parent().parent().children('td[name="no2gSites"]').children('input').val(),
				 "ProfitLoss2G" : $(this).parent().parent().children('td[name="profitAndLoss2g"]').children('input').val(),
				 "2G3GSites" : $(this).parent().parent().children('td[name="no2g3gSites"]').children('input').val(),
				 "ProfitLoss2G3G" : $(this).parent().parent().children('td[name="profitAndLoss2g3g"]').children('input').val(),
				 "2G3G4GSites" : $(this).parent().parent().children('td[name="no2g3g4gSites"]').children('input').val(),
				 "ProfitLoss2G3G4G" : $(this).parent().parent().children('td[name="profitAndLoss2g3g4g"]').children('input').val(),
				 "TotalSites" : $(this).parent().parent().children('td[name="totalSites"]').children('input').val(),
				 "SumProfitLoss" : $(this).parent().parent().children('td[name="totalSumProfit"]').children('input').val(),
				    "Id":$(this).parent().parent().children('td[name="id"]').children('input').val(),
				 
				
			    });	

		   
	
        }		  
		  
    	});
		              
}
});
function saverowsintables (){
    console.log("enter save area function");
    var AreaId = document.getElementById("areaId").value;
    	
		 var AreaName = document.getElementById("areaname").value;
		 var Longitude = document.getElementById("arealng").value;
		 var Latitude = document.getElementById("arealatt").value;
		var creationDate = document.getElementById("createdate").value;
		 var lastModifieddate = document.getElementById("lstmodifdate").value;
	
     	$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/AreaFormSave",
			dataType : "json",
			data : {
				"type": '${docStatus}',
				"dictParameter" : dict,
				"AreaID" : AreaId,
				"creationDate" : creationDate,
				"lastModifieddate" : lastModifieddate,
				"AreaName" : AreaName,
				"Longitude" : Longitude,
				"Latitude" : Latitude,
									
		},
			success : function(data) {
				areaId.value=data.AREAID;
				location.reload(true);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});

}
</script>