<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title></title>
   <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/Report.css" rel="stylesheet" />
   <!-- <link href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap4.min.css" rel="stylesheet" /> -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js"></script>
   <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script> -->
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <!-- <script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap4.min.js"></script>  -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script> 
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
	<script src="https://cdn.datatables.net/plug-ins/1.10.21/api/sum().js"></script>

	
</head>
<body>
<%--    <c:set var = "page" value = "report"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="report" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
    
       
    <div class="side"  Style="width: 300px;">
           
            <ul >
                 <li stAreaSiteyle="list-style-type:none;"><a id="assetReport" href="${pageContext.request.contextPath}/AssetAccountingVoucher"  ><i class="fas fa-newspaper"></i> Asset Accounting Voucher</a></li>
                 <li style="list-style-type:none;"><a id="areaReport" href="#"  ><i class="fas fa-newspaper"></i> Area Profit and Loss</a></li>
                <li style="list-style-type:none;"><a id ="plPerSite" href="${pageContext.request.contextPath}/SiteProfitLossReport"  ><i class="fas fa-newspaper"></i>Site Profit Loss Report</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 4</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 5</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 6</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 7</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 8</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 9</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 10</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 11</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 12</a></li>
                <li style="list-style-type:none;"><a href="#"  ><i class="fas fa-newspaper"></i> Report 13</a></li>
    
            </ul>
        </div>
  
  <!-- second report start--> 
   <div id= "areaReportDiv" Style="display:none; left: 0; bottom: 0; padding-left:300px;" >
        <div class="container-fluid">     
	     <div class="row">
	       <div class="col-md-3" >
				<div class="form-group">
					<div class="input-group-prepend"  style="margin-top: 5px;">
						<span class="input-group-text" style="color: green">Area Profit & Loss  Report</span>
					</div>
				</div>
			</div>
	         
		
		 </div>
 
		 <div class="row second">
			
		  <div class="col-md-2">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<input type="text" id="startdate" value="${startDate}" placeholder="Start Date" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					<div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
					</div>
				</div>
		    </div>
	     </div>
	
	     <div class="col-md-2">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<input type="text" id="enddate" value="${endDate}" placeholder="End Date" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					<div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
					</div>
				</div>
		   </div>
	    </div>
       
        <div class="col-md-2">
          
            <form Style="padding-top:10px;">
                <input type="checkbox" name="Accumulated" id="Accumulated" value="Accumulated" >
                <label id ="labelAccumulated" for="Accumulated" Style= "padding-right:20px;">Accumulated</label>
                
                <input type="checkbox" name="Monthly" id="Monthly" value="Monthly">
		        <label id= "labelMonthly" for="Monthly"> Monthly</label>

             </form>
          
       </div>
       <div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
					<select id="area" class="form-control text-input" >
					   <option value="all" selected>Area</option>
			           <option value="Eldoret North">Area 1</option>
			           <option value="Eldoret South">Area 2</option>
			           <option value="Embu">Area 3</option>
			           <option value="Garissa">Area 4</option>
                       <option value="5">Area 5</option>
		            </select>
				</div>
		 </div>
       </div>
		 
		 
		<div class="col-md-4" style="text-align: right;">
		 	  <div class="btn-group pull-right">
		            <button type="button" id="generateReportArea" class="btn btn-primary BtnActive"> Generate Report </button> 
			 </div>
		</div>
	
	
	
  </div>		
     <p></p>    
        
  </div>
  
  <div class="card-body">
			<div class="row">
				<div class="col-sm-12">
					<table id="example" class="display table table-bordered nowrap" style="width:100%;">
						<thead class="thead-light">
						
						 <tr id="columnSearch">
						 <th></th>
						 <th></th>
						 <th class="inputFilter"><input type="text" placeholder="Search Area" /></th>
						 </tr>
    						<tr>
								
    							<th>Start Date</th>
    							<th>End Date </th>
    							<th>Area </th>
    							<th>Number of 2G Sites</th>
    							<th>2G Profit and LOSS </th>
    							<th>Number of 2G & 3G Sites</th>
    							<th>2G & 3G Profit and LOSS</th>
    							<th>Number of 2G,3G,&4G Sites</th>
    							<th>2G,3G, & 4G Profit and LOSS</th>
    							  							
    						</tr>
  						</thead>
  						<tbody>
  						</tbody>
					</table>
				</div>
			</div>
		</div>
   <div class="container-fluid"> 
	<div class="row">
	
	  <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Profit and Loss </span>
						<input type="text" id="tPL" value="${tPL}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Number of 2G Sites</span>
						<input type="text" id="tnumberof2g" value="${tnumberof2g}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Number of 2G and 3G Sites</span>
						<input type="text" id="tnumberof2g3g" value="${tnumberof2g3g}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 <div class="col-md-4">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Number of 2G, 3G, and 4G Sites</span>
				<input type="text" id="tnumberof2g3g4g" value="${tnumberof2g3g4g}" readonly class="form-control text-input" />
				</div>
			</div>							
		</div>
		
		<div class="col-md-4">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Number of Areas</span>
				<input type="text" id="tNumberOfAreas" value="${tNumberOfAreas}" readonly class="form-control text-input" />
				</div>
			</div>							
		</div>
	</div>	
</div>				
</div>
  
  <!-- second report end--> 
  <!-- third report start -->      

<!-- third report end --> 
    <script>
    
    // start for report area div
     $('input[name="Accumulated"]'). click(function(){
		if($(this). is(":checked")){
			document.getElementById("Monthly").checked = false;

		    console.log("Checkbox is checked." );
		
		}
		else if($(this). is(":not(:checked)")){
		    console.log("Checkbox is unchecked." );
		}
 });
			 
		$('input[name="Monthly"]'). click(function(){
			if($(this). is(":checked")){
				 document.getElementById("Accumulated").checked = false;
			     console.log("Checkbox is checked." );
			}
			else if($(this). is(":not(:checked)")){
			    console.log("Checkbox is unchecked." );
			}     
		 });
 
 
		 // set start date defualt value to month before 
    var today = new Date();
    var date = (today.getMonth())+'/'+today.getDate()+'/'+ today.getFullYear();
    var time = today.getHours() + ":" + today.getMinutes() ;
    var dateTime = date+' '+time;
    var c = Date.parse(dateTime);
    $('#startdate').datetimepicker({
        defaultDate:c
    });
    // set end date defualt value to current
     var dateNow = new Date();

    $('#enddate').datetimepicker({
        defaultDate:dateNow
    }); 
    /// end for report are div
    
    
        $(document).ready(function() {
        	//console.log("on click ajax");
            $('a').click(function() { 
            	//console.log("After calling ajax");
            	// Remove 'active' tag for all list items 
                for (let i = 0; i < 12; i++) {
                	//console.log("i value is "  +i);
                	//$('.side li:eq(i) a').removeClass("active");
                	$('.side ul:first li:eq(' + i +') a').removeClass('active');
//                	$('.side ul:first li:eq(1) a').removeClass('active');
//                	console.log("Text of selected link is " +$('.side ul:first li:eq(0)').text());
//                	console.log("Text of selected link is " +$('.side ul:first li:eq(1)').text());
                	
                } 
                $(this).addClass("active"); 
            }); 
            //Change here
            /// diplay div for checked report
            var divAreaReport = document.getElementById("areaReportDiv");
            var divPLPerSite = document.getElementById("plPerSiteDiv");
            var divAssetAccounting = document.getElementById("assetaccountingDiv");
            
            $("#areaReport").click(  function() {
            	
            	 divAreaReport.style.display = "block";
            	// divPLPerSite.style.display = "none";
            });
            /*$("#plPerSite").click(  function() {
            	divAreaReport.style.display = "none";
            	
            });*/
            
            /////start for report area div
            
       // for the datatable
             var areaReportData = ${AreaReportList};
			//console.log(tableData);
           	var areaReportTable = $('#example').DataTable( {
        		"bProcessing": true,
        	    "aaData": areaReportData,// <-- your array of objects
        	    "aoColumns":[
        	    	
        	    	
        	    	
        	    	{ "mData": [0] }, 
        	        { "mData": [1] },
        			{ "mData": [2] },
        			{ "mData": [3] },
        			{ "mData": [4] },
        	        { "mData": [5] },
        	        { "mData": [6] },
        	        { "mData": [7] },
        	        { "mData": [8] }
        	        
        	        ],
        			
        		
        	          'columnDefs': [
        	          
        	      ],
        	      
        	      
        			'order': [[2, 'asc']],
        				//select: true,
        				order : [ [ 0, 'asc' ] ],
        		/*		'select': { style: 'multi',
        	      selector : 'td:first-child'
        	      },*/
        	      'order': [[2, 'asc']],
        	      	dom: 'Blfrtip',
        			"paging":true,
        			"pageLength":10, 
        			"scrollY": false,
        			"scrollX": true,
        			"scrollCollapse": false,
        			"fixedColumns": true,
        			"ordering":true,
        			"rowReorder": true,
        			"info":       true,
        			"filter":     true,
        			"length":     true,
        			"processing": true,
        			"deferRender": true,
        	          //orderable : false,
        	      
        	   });
           
        	
        	
           	$('#columnSearch input[type="text"]').on('keyup change clear', function(){
        		if($(this).attr('placeholder') == 'Search Area'){
        			table.column(2).search(this.value).draw();
        			//console.log('value is: '+this.value);
        		}
        		
        		
        	});
            
           	
            
            
           //Get the checkbox
			 var checkBox1 = document.getElementById("Accumulated");
			 var checkBox2 = document.getElementById("Monthly");
			 
			 
            // area report
            
            var div1 = document.getElementById("areaReportDiv");
            $("#areaReport").click(  function() {
               div1.style.display = "block";
             
               
              
             
                  $("#generateReportArea").click(  function() {
                	  
                	  
                	  var  Checkvalue;
                	  if (checkBox1.checked == true){
                		  Checkvalue= "Accumulated";
            	          console.log("Checkbox1 is checked." );
            	         
                      }
                	  /// Monthly
                      if (checkBox2.checked == true){
                    	  Checkvalue = "Monthly";
            	          console.log("Checkbox2 is checked." );
            	         
                       }
                  
                      if (checkBox1.checked == false && checkBox2.checked == false){
                 		  alert('Please choose an option(Monthly/Accumulated)');
                 	  }
                       else{
                		$.ajax({
        					type : "GET",
        					contentType: "application/json; charset=utf-8",
        					url : "${pageContext.request.contextPath}/GenerateAreaReport",
        					dataType : "json",
        					data : {
        					    "checkValType": Checkvalue,
        					    "startDate" : $("#startdate").val(),
        					    "endDate" : $("#enddate").val(),
        					    "area" : $("#area").val(),
        					   
        					},
        					success : function(data) {
        						
        						 if (data != null) {
        	                        
        	                         //console.log('data is '+ data.AreaReportList);
        	                         
        	                         var areaReport = data.AreaReportList;
        	                         //var newdata = Object.values(data);
        	                        // console.log('the new data is '+newdata);
        	                         //tableData = data.AreaReportList;
        	                        // tableData = newdata;
        	                        // tableData1 = areaReport;
        	                         areaReportTable.clear().rows.add(areaReport).draw();
        	                         
        	                         
        	                         console.log('the total PL is '+data.totalProfitLoss);
        	                         console.log('the total # of 2g sites is '+data.totalnumberof2g);
        	                         console.log('thetotal # of 2g and 3g sites is '+data.totalnumberof2g3g);
        	                         console.log('the total # of 2g, 3g, and 4g sites is '+data.totalnumberof2g3g4g);
        	                         
        	                         
        	                         
        	                
        	                         tPL.value = data.totalProfitLoss;
        	                         tnumberof2g.value = data.totalnumberof2g;
        	                         tnumberof2g3g.value = data.totalnumberof2g3g;
        	                         tnumberof2g3g4g.value = data.totalnumberof2g3g4g;
        	                         tNumberOfAreas.value = data.totalAreas;
        	                     
        	                        // document.getElementById("tNumberOfAreas").innerHTML = data.totalAreas;
        	                         
        						 }
        					
        					
        					
                	  
                	  
                	  
                	  
                	  
                	
                	  
        		  },
        		  
        		  error : function(error) {
						console.log("The error is " + error);
					}
                 });
               }
            }); 
               
        }); 
            /////end for report area div
            
            ///start for div plPerSite
            
          // $('#datetimepicker3').datetimepicker({format: 'DD/MM/YY'});
          // $('#datetimepicker4').datetimepicker({format: 'DD/MM/YY'});
           
        /*  $('a[href="#plPerSite"]').click(function(){
        	  console.log("hi");
        	  $('#plPerSite').css("display", "block");
          });
          
          
           $('#accu'). click(function(){
       		if($(this). is(":checked")){
       			document.getElementById("month").checked = false; 
       			$('#month').val('0');
       			$(this).val('1');

       		    console.log("Checkbox is checked." );
       		
       		}
       		else if($(this). is(":not(:checked)")){
       			$(this).val('0');
       			  
       		      
       		
       		    console.log("Checkbox is unchecked. "+$(this).val());
       		}
        });
       			 
       		$('#month'). click(function(){
       			if($(this). is(":checked")){
       				 document.getElementById("accu").checked = false;
       				$('#accu').val('0')
       				$(this).val('1')
       			     console.log("Checkbox is checked." );
       			}
       			else if($(this). is(":not(:checked)")){
       				$(this).val('0')
       			
       			  console.log("Checkbox is unchecked. "+$(this).val());
       			}     
       		 });
       		
       		
           $("#warcode").autocomplete({
       		//showHeader: true,
       		//columns: columns,
            
                source: function(request, response) {
               	 
               	 var siteId=$("#site").val();
       	         var wareName=$("#warname").val();
       	        	
       	             $.ajax({
       	                 type: "GET",
       	                 contentType: "application/json; charset=utf-8",
       	                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
       	                 data: {
       							"warehouseName" : $("#warcode").val(),
       							 "WareName":wareName,
       							 "SiteId":siteId,
       					 },
       	                 dataType: "json",
       	                 success: function (data) {
       	                	 
       	                     if (data != null) {
       	                         response(data.globalList);
       	                         console.log('data is ;'+ data.globalList);
       	                         //colors = data.ListItemCategory;
       	                         //console.log('colors ;'+ colors);
       	                     
       	                     }
       	                 },
       	                 error: function(result) {
       	                     alert("Error");
       	                 }
       	             });
       	         }, minLength:0, maxShowItems: 4, scroll:true,		
                   
            
       	         select: function(event, ui) {
       	        	    warcode.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2] : '');
       	        	    document.getElementById("warname").value = ui.item[1];
       	        	    document.getElementById("site").value = ui.item[2];
       					return false;
       						}
       			    }).autocomplete("instance")._renderItem = function(ul, item) {
       		            return $("<li class='each'>")
       	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
       	                    item[0] + "</span><br><span class='desc'>" +
       	                    item[1] +', '+ item[2]  +', '+ item[3] +', '+ item[4] +  "</span></div>")
       	                .appendTo(ul);
       	        	};
       				$("#warcode").focus(function(){
       	   	        	if (this.value == ""){
       	   	            	$(this).autocomplete("search");
       	   	        	}						
       				});
       				
       				$("#site").autocomplete({
       					showHeader: true,
       					
       			        
       			        source: function(request, response) {

       	                        var warehouse=$("#warcode").val();
       	                        var warehouseName=$("#warname").val();
       				        
       				             $.ajax({
       				                 type: "GET",
       				                 contentType: "application/json; charset=utf-8",
       				                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
       				                 data: {
       										"SiteId" : $("#site").val(),
       										 "Warehouse": warehouse,
       										 "WarehouseName": warehouseName,

       										// It will be modified
       										
       								 },
       				                 dataType: "json",
       				                 success: function (data) {
       				                     if (data != null) {

       					                     
       				                         response(data.ListSiteId);
       				                        // it will have some modification here 
       				                     }
       				                 },
       				                 error: function(result) {
       				                     alert("Error");
       				                 }
       				             });
       				         }, minLength:0, maxShowItems: 40, scroll:true,		
       			               
       			        
       					select: function(event, ui) {
       						
       						site.value = (ui.item ? ui.item[0]   : '');



       						$.ajax({
       			                type: "GET",
       			                contentType: "application/json; charset=utf-8",
       			                url: '${pageContext.request.contextPath}/GetWareID',
       			                data: {
       									WareName : ui.item[0],
       							 },
       			                dataType: "json",
       			                success: function (data) {
       			                    if (data != null) {
       				               
       				                console.log("The list is "+data.ListWareIds[0]) ;

       				                WareId = data.ListWareIds[0];
       				           

       				                if(data.ListWareIds.length == 1){
       				                
       				                	
       				                	$("#warcode").val(WareId);
       				                	warname.value=ui.item[1];
       				                	

       					                }
       				                

       				                else{


       				                	$("#warcode").val("");
       				                	

       					                }
       				                
       										                  
       			                    }
       			                },
       			                error: function(result) {
       			                    alert("Error");
       			                }
       			            });
       						

       							return false;
       								}
       					    }).autocomplete("instance")._renderItem = function(ul, item) {
       				            return $("<li class='each'>")
       			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
       			                    //item[0] + "</span><br><span class='desc'>" +
       			                   // item[1] + "</span><br><span class='desc'>" +
       			                    //item[2] + "</span></div>")
       			                    item[0] + "</span><br><span class='desc'>" 
       			                    )
       			                .appendTo(ul);
       			        	};
       						$("#site").focus(function(){
       			   	        	if (this.value == ""){
       			   	            	$(this).autocomplete("search");
       			   	        	}						
       						});





       						$("#warname").autocomplete({
       							showHeader: true,
       							
       					        
       					        source: function(request, response) {

       			                        var warehouse=$("#warcode").val();
       			                        var siteid=$("#site").val();
       			                       
       						        
       						             $.ajax({
       						                 type: "GET",
       						                 contentType: "application/json; charset=utf-8",
       						                 url: '${pageContext.request.contextPath}/GetAllSiteNames',
       						                 data: {
       												"WareHouseName" : $("#warname").val(),
       												 "Warehouse": warehouse,
       												  "SiteId":siteid,

       												// It will be modified
       												
       										 },
       						                 dataType: "json",
       						                 success: function (data) {
       						                     if (data != null) {

       							                     
       						                         response(data.ListWareName);
       						                        // it will have some modification here 
       						                     }
       						                 },
       						                 error: function(result) {
       						                     alert("Error");
       						                 }
       						             });
       						         }, minLength:0, maxShowItems: 40, scroll:true,		
       					               
       					        
       							select: function(event, ui) {
       								
       								warname.value = (ui.item ? ui.item[0]   : '');
       								


       								$.ajax({
       					                type: "GET",
       					                contentType: "application/json; charset=utf-8",
       					                url: '${pageContext.request.contextPath}/GetAllWarehouse',
       					                data: {
       											WareName : ui.item[0],
       									 },
       					                dataType: "json",
       					                success: function (data) {
       					                    if (data != null) {
       						               
       						                console.log("The list is "+data.ListWareIds[0]) ;

       						                WareId = data.ListWareIds[0];
       						            

       						                if(data.ListWareIds.length == 1){
       						                	
       						                	
       						                	$("#warcode").val(WareId);
       						                	site.value=ui.item[1];
       						                	

       							                }
       						                

       						                else{

       						                	$("#warcode").val("");
       						                	

       							                }
       						                
       												                  
       					                    }
       					                },
       					                error: function(result) {
       					                    alert("Error");
       					                }
       					            });
       								
       				
       									return false;
       										}
       							    }).autocomplete("instance")._renderItem = function(ul, item) {
       						            return $("<li class='each'>")
       					                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
       					                    //item[0] + "</span><br><span class='desc'>" +
       					                   // item[1] + "</span><br><span class='desc'>" +
       					                    //item[2] + "</span></div>")
       					                    item[0] + "</span><br><span class='desc'>" 
       					                    )
       					                .appendTo(ul);
       					        	};
       								$("#warname").focus(function(){
       					   	        	if (this.value == ""){
       					   	            	$(this).autocomplete("search");
       					   	        	}						
       								});

			var tableData = ${plPerSiteTableData};
			console.log(tableData);
           	var table = $('#perSite').DataTable( {
        		"bProcessing": true,
        	    "aaData": tableData,// <-- your array of objects
        	    "aoColumns":[
        	    	{ "mData": [0] }, 
        	        { 
        			  "mData": [1],
        			  
        	        },
        			{ "mData": [2] },
        			{ "mData": [3] },
        			{
        			  "mData": [4]
        	         },
        	         {
        	        	 "mData": [5]
        	        },
        	        { "mData": [6] },
        	        { "mData": [7] },
        	        { "mData": [8] },
        	        { "mData": [9] },
        	        { "mData": [10] },
        	        { "mData": [11] },
        	        { "mData": [12] },
        	        { "mData": [13] },
        	        { "mData": [14] },
        	        { "mData": [15] }
        	        ],
        			
        		
        	          'columnDefs': [
        	        	  {
        	        		'targets': 0,
        	        		'mData': [1],
        	        	  },
        	        	  {
        	          		'targets': 1,
        	          		'mData': [2],
        	          	  },
        	          	{
        	            		'targets': 2,
        	            		'mData': [3],
        	            		  
        	            	  },
        	            {
        	              		'targets': 3,
        	              		'mData': [4],
        	              		  
        	              	  },
        	          	  
        	         {
        	            'targets': 4,
        	            'searchable': false,
        	            'orderable': false,
        	         }
        	            	 
        	            
        	      ],
        	      
        	      
        			'order': [[2, 'asc']],
        				//select: true,
        				order : [ [ 0, 'asc' ] ],
        		/*		'select': { style: 'multi',
        	      selector : 'td:first-child'
        	      },*/
        	     /* 'order': [[2, 'asc']],
        	      	dom: 'Blfrtip',
        			"paging":true,
        			"pageLength":10, 
        			"scrollY": false,
        			"scrollX": true,
        			"scrollCollapse": false,
        			"fixedColumns": true,
        			"ordering":true,
        			"rowReorder": true,
        			"info":       true,
        			"filter":     true,
        			"length":     true,
        			"processing": true,
        			"deferRender": true,
        	          //orderable : false,
        	      
        	   });
           
           
           $("#generateReport").click(  function() {
      		 console.log('generate now');
      		 
      		 
      		 if ($("#startdate1").val()== '') {
      		 alert('Please enter a start date');
      		 return false;}
      		 
      		 if ($("#enddate1").val()== '') {
      			 alert('Please enter an end date');
      			 return false;}
      		      		 
      		 
      		
      		
      		
      		var startDate = document.getElementById("startdate1").value;
      	
      		var endDate = document.getElementById("enddate1").value;
      		var wareHouse = document.getElementById("warcode").value;
      		var siteName = document.getElementById("warname").value;
      		var siteId = document.getElementById("site").value;
      		var accumulated = document.getElementById("accu").value;
      		var monthly = document.getElementById("month").value;
      		
      		
      		
      	      console.log("startDate " +startDate);
      		 console.log("endDate " +endDate);
      		 console.log("wareHouse " +wareHouse);
      		 console.log("siteName " +siteName);
      		 console.log("siteId " +siteId);
      		 console.log("accumulated " +accumulated);
      		 console.log("monthly " +monthly);

      	     
      			$.ajax({
      				type : "GET",
      				url : "${pageContext.request.contextPath}/ProfitAndLossPerSite",
      				dataType : "json",
      				data : {
      				     
      					"startDate" : startDate,
      				    "endDate" : endDate,
      				    "wareHouse" : wareHouse,
      					"siteName" : siteName,
      					"siteId" : siteId,
      					"accumulated" : accumulated,
      					"monthly" : monthly
      								
      				},
      				success : function(data) {
      					console.log("dataaaa");
// to add the su,
      					/*console.log(data.plPerSiteTableData);
      					tableData = data.plPerSiteTableData;
      					table.clear().rows.add(tableData).draw();*/
      					
      				/*	if (siteId != "" && accumulated == 1) 
      		          		$('#totalNumbers').css("display", "none");
      		      		else
      		      			$('#totalNumbers').css("display", "block");
      					
      					var grossRevSum = table.column(8).data().sum();
      					console.log("grossRevSum: "+grossRevSum);
      					document.getElementById("grossRev").value = grossRevSum;
      					
      					var totalOpexSum = table.column(14).data().sum();
      					console.log("totalOpexSum: "+totalOpexSum);
      					document.getElementById("totalOpex").value = totalOpexSum;
      					
      					var totalPLSum = table.column(15).data().sum();
      					console.log("totalPLSum: "+totalPLSum);
      					document.getElementById("totalPL").value = totalPLSum;
      					
      					var dataTrafSum = table.column(9).data().sum();
      					console.log("dataTrafSum: "+dataTrafSum);
      					document.getElementById("dataTraf").value = dataTrafSum;
      					
      					var smsTrafOGSum = table.column(10).data().sum();
      					console.log("smsTrafOGSum: "+smsTrafOGSum);
      					document.getElementById("smsTrafOG").value = smsTrafOGSum;
      					
      					var voiceTrafOGSum = table.column(11).data().sum();
      					console.log("voiceTrafOGSum: "+voiceTrafOGSum);
      					document.getElementById("voiceTrafOG").value = voiceTrafOGSum;
      					
      					var voiceTrafICSum = table.column(12).data().sum();
      					console.log("voiceTrafICSum: "+voiceTrafICSum);
      					document.getElementById("voiceTrafIC").value = voiceTrafICSum;
      					
      					var smsTrafICSum = table.column(13).data().sum();
      					console.log("smsTrafICSum: "+smsTrafICSum);
      					document.getElementById("smsTrafIC").value = smsTrafICSum;
      					
      				
      				},
      				error : function(error) {
      					console.log("The error is " + error);
      				}
      			});
      	});
           ///end for div plPerSite
        */   
        }); 
    </script> 
           
 </body>
 </html>
