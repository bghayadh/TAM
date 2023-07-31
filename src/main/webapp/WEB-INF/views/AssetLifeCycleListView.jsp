<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="shortcut icon" href="">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	
	
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>  
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
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
				  margin-right: 10px;
				  margin-left: 10px;  
				}
				
				#labelLedger, #labelBalace{
				padding : 0;
				margin: 0;
				float: none;
				}
				
				#voucherType, #UOM{
				color: gray;
				}
				
				.flex-wrapper {
                 display: flex;
                 min-height: 100vh;
                 flex-direction: column;
                 justify-content: space-between
                 }
                 
                .content {
                  min-height: calc(100vh - 70px);
                  }
                .footer {
                 position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  background-color: red;
  color: white;
  text-align: center;
                }
	        		 
 	</style>
            
</head>
<body>
  
  <nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
        <a href="#" class="navbar-brand">ALM</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarmenu">
            <span class="navbar-toggler-icon"></span>
        </button>



        <div class="collapse navbar-collapse" id="navbarmenu">
            <ul class="navbar-nav">
                <li class="nav-item"><a href="/Network" class="nav-link  " style="color: #fff"><i class="fas fa-wifi" style="color: gold"></i> Network</a></li>
                <li class="nav-item"><a href="/Purchase" class="nav-link " style="color: #fff"><i class="fas fa-money-check" style="color: gold"></i> Purchasing </a></li>
                 
                <li class="nav-item"><a href="/Inventory" class="nav-link " style="color: #fff"><span class="border-bottom active"><i class="fas fa-warehouse" style="color: #20B2AA"></i> Inventory</span> </a></li>
                <li class="nav-item"><a href="/Contract" class="nav-link " style="color: #fff"><i class="fas fa-book" style="color: gold"></i> Contracts</a></li>
                <li class="nav-item"><a href="/Report" class="nav-link  " style="color: #fff"><i class="fas fa-chart-line" style="color: gold"></i> Report </a></li>
                <li class="nav-item"><a href="/Dashboard" class="nav-link " style="color: #fff"><i class="fas fa-tv" style="color: gold"></i> Dashboard </a></li>
                <li class="nav-item"><a href="/Setup" class="nav-link " style="color: #fff"><i class="fas fa-cog" style="color: gold"></i> Setup </a></li>



            </ul>
            <ul class="navbar-nav ml-auto ">
                        
                <!--  li class="nav-item"><a href="#" class="nav-link a1" style="margin-right: 30px;"><i class="fas fa-bell" style="color: gold"></i></a></li-->
                <li class="dropdown" id="notifactionDropdown"
				style="margin-right: 60px;"><a href="#" class="nav-link a1" data-toggle="dropdown">
					<span class="p1" id="bellCounter" data-count=""> 
					<i class="fa fa-bell" data-count="4b" style="font-size: 20px; color: gold;"></i>
					</span></a>
 	
						<ul class="dropdown-menu dropdown-menu-right customListPadding">
							<li><p>
								<a href="#">Site <span class="badge badge-danger float-right"id="siteTask"></span></a></p></li>
							<li><p>
								<a href="#">Node <span class="badge badge-danger float-right"id="nodeTask"></span></a></p></li>
							<li><p>
								<a href="#">Cell <span class="badge badge-danger float-right"id="cellTask"></span></a></p></li>
							<li><p>
								<a href="#">Task<span class="badge badge-danger float-right"id="taskCount"></span></a></p></li>
						</ul>
				</li>
               
               
               <!--  li class="nav-item"><a href="#" class="nav-link a1" style="color: white;"><i class="fas fa-user" style="color: gold   "></i> aya shi </a></li -->
 
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #ffbb33 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>

   

 <!--  end of general head page -->
 
 <div class="container-fluid">     
	   <div class="row">
	   
	<p></p>
		
		</div>
	
		<div class="row second">
			
			<div class="col-md-9">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Asset Life Cycle</span>							
						</div>

					</div>
			
             </div>
       
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph" >
			 			 <div class="dropdown"  Style="margin-right:10px; height:30px;">
	                        <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Menu	</button>
	
	                       <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    	                   <a class="dropdown-item" id="edit" href="#">Edit </a>
    	                   <a class="dropdown-item" id="print" href="#">Print </a>
    	                   <a class="dropdown-item" id="export" href="#">Export </a>
    	                   <a class="dropdown-item" id="pdf" href="#">PDF </a>
    	                   <a class="dropdown-item" id="setup" href="#">Setup Auto Email </a>
    	                   <a class="dropdown-item" id="addCol" href="#">Add Column </a>
    	                  </div>
			           </div>  
			            
		        </div>
		                  <button type="button" id="generate" class="btn btn-primary BtnActive"> Generate Report </button> 
			</div>
		</div>
	
	</div>
	
  </div>		
<p></p>


 <div class="container-fluid"> 
  <div class="row">
   <div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<input type="text" id="startdate" value="${startDate}" placeholder="Start Date" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					<div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
					</div>
				</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<input type="text" id="enddate" value="${endDate}" placeholder="End Date" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					<div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
					</div>
				</div>
		</div>
	</div>
	
   <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text"  id="warcode" placeholder="Warehouse" value="${warehouse}" class="form-control text-input"   />
				</div>
		 </div>
  </div>	
	
	<div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text"  id="icode" name ="itmCode" placeholder="Item" value="${item}" class="form-control text-input"   />
				</div>
		 </div>
  </div>	
  
  
  <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text"  id="icacode"  placeholder="Item category" value="${itemCategory}" class="form-control text-input"   />
				</div>
		 </div>
  </div>	
  
  <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text"  id="imcode" name ="itmModel" placeholder="Item Model" value="${itemModel}" class="form-control text-input"   />
				</div>
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text"  id="prcode"  placeholder="Project" value="${project}" class="form-control text-input"   />
				</div>
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                  
    	             <select id="UOM" class="form-control text-input">
    	               <option value="none" selected>UOM</option>
					   <option value="nos">Nos</option>
			           <option value="units">Units</option>
			           <option value="km">km </option>
			           <option value="m">m</option>
			           <option value="kg">kg</option>
                       <option value="g">g</option>
		            </select>
				</div>
		 </div>
  </div>
  
  <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<select id="voucherType" class="form-control text-input" >
					   <option value="none">Voucher Type</option>
			           <option value="pr" selected>Purchase Request</option>
			           <option value="po">Purchase Order</option>
			           <option value="gr">Goods Received</option>
			           <option value="ar">Asset Registry</option>
                       <option value="far">Fixed Asset Registry</option>
		            </select>
				</div>
		 </div>
  </div>
  
   <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text" name="voucherNB"  id="vncode"  placeholder="Voucher #" value="${voucherNB}" class="form-control text-input"   />
				</div>
		 </div>
  </div>
  
  
  
  <div class="col-md-3">
		   <div class="form-group">
				<div class="input-group-prepend">
					<input type="text"  id="sncode"  placeholder="Serial #" value="${serialNB}" class="form-control text-input"   />
				</div>
		 </div>
  </div>	
	
  <div class="col-md-3">
            <form>
                <input type="checkbox" name="balance" id="balance" value="balance">
                <label id ="labelBalace" for="balance" >Balance</label>
                <input type="checkbox" name="ledger" id="ledger" value="ledger">
		        <label id= "labelLedger" for="ledger"> Ledger</label>

             </form>
  </div>
 </div>
 
<div> 
				<form id="checktableBalance" style="display:none">
				
								
					    <div class="table-responsive-sm" > 
						    <table id ="bisotab1" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						               
								       <th><span class="sub"></span></th>
						                <th>Item</th>
						                <th>Item model</th>
						                <th>Warehouse</th>
						                <th>UOM</th>
						                <th>Opening qty</th>
						                <th>Opening value</th>
						                <th>In value</th>
						                <th>Out value</th>
						                <th>Balance qty</th>
						                <th>Valuation rate</th>
						               
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
                   </form>
		</div>	
	
<div> 
				<form id="checktableLedger" style="display:none">
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="bisotab2" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						                <th><span class="sub"></span></th>
								        <th>Date</th>
						                <th>Item</th>
						                <th>Item Model</th>
						                <th>Warehouse</th>
						                <th>Voucher Type</th>
						                <th>Voucher#</th>
						                <th>UOM</th>
						                <th>Qty</th>
						                <th>Incoming Rate</th>
						                <th>Accumulated Depreciation</th>
						                <th>Net Price</th>
						                <th>Valuation Rate</th>
						                <th>Balance Value</th>
						                <th>Net Balance Value</th>
						                <th>Balance Qty</th>
						                
						               
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
                   </form>
		</div>	
	
<div id="divLed" Style="display:none; left: 0; bottom: 0;">	
<div class="row">
	
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Accumulated Depreciation</span>
						<input type="text" id="totalAccumulatedDepreciation" value="${totalAccumulatedDepreciation}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 
	  <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Net Price</span>
						<input type="text" id="totalNetPrice" value="${totalNetPrice}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 
	
		
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Balance Value</span>
						<input type="text" id="totalBalanceValue" value="${totalBalanceValue}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Net Balance Value</span>
						<input type="text" id="totalNetBalanceValue" value="${totalNetBalanceValue}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 
	 <div class="col-md-3">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Balance QTY</span>
				<input type="text" id="totalBalanceQtyBal" value="${totalBalanceQty}" readonly class="form-control text-input" />
				</div>
			</div>							
		</div>
		
		
	</div>	
				
</div>		

<div id="divBal" Style="display:none; left: 0; bottom: 0;">	
<div class="row">
	

	
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Opening Qty </span>
						<input type="text" id="opQtyTotal" value="${opQtyTotal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 
	  <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Opening Value </span>
						<input type="text" id="opValTotal" value="${opValTotal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">In Value Total</span>
						<input type="text" id="InvalueTotal" value="${InvalueTotal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Out Value Total</span>
						<input type="text" id="OutvalueTotal" value="${OutvalueTotal}" readonly class="form-control text-input" />
					</div>
				</div>
	 </div>
	 <div class="col-md-3">
	       <div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Total Balance QTY</span>
				<input type="text" id="totalBalanceQtyLed" value="${totalBalanceQty}" readonly class="form-control text-input" />
				</div>
			</div>							
		</div>
		
	</div>	
				
</div>				
</div>
 
 <p></p>	

</body>

 
 
 
 <script type='text/javascript'>
 
   // set start date defualt value to month before 
        var today = new Date();
        var date = (today.getMonth())+'/'+today.getDate()+'/'+ today.getFullYear();
        var time = today.getHours() + ":" + today.getMinutes() ;
        var dateTime = date+' '+time;
        var c = Date.parse(dateTime);
    $('#startdate').datetimepicker({
        defaultDate : c
    });
    // set end date defualt value to current
     var dateNow = new Date();

    $('#enddate').datetimepicker({
        defaultDate:dateNow
    });
   
 
 //checking the Balance/ledger checkbox
 
  // Get the output table
	   var table1 = document.getElementById("checktableBalance");
	   var table2 = document.getElementById("checktableLedger");
  // Get the total output of table
       var div1 = document.getElementById("divBal");
       var div2 = document.getElementById("divLed");
 
 
 
 $('input[name="balance"]'). click(function(){
		if($(this). is(":checked")){
			document.getElementById("ledger").checked = false; 
		    table2.style.display = "none";
		    div2.style.display = "none";

		    console.log("Checkbox is checked." );
		
		}
		else if($(this). is(":not(:checked)")){
			
			table1.style.display = "none";
		     div1.style.display = "none";
			  
		      
		
		    console.log("Checkbox is unchecked." );
		}
 });
			 
		$('input[name="ledger"]'). click(function(){
			if($(this). is(":checked")){
				 table1.style.display = "none";
			     div1.style.display = "none";
				 document.getElementById("balance").checked = false;
			     console.log("Checkbox is checked." );
			}
			else if($(this). is(":not(:checked)")){
				   table2.style.display = "none";
			       div2.style.display = "none";
			
			  console.log("Checkbox is unchecked." );
			}     
		 });
 
 
 
 
 $(document).ready(function() {

	 
	 
	 var sel = document.getElementById("voucherType");
	 var result = sel.options[sel.selectedIndex].value;
	 var option = $("#voucherType").children("option:selected"). val();

	//auto complete for VoucherNB 
	$("#vncode").click(function(){
		var option = $("#voucherType").children("option:selected").val();
		 alert(option);
     		var columns = [{name: 'ID', minWidth: '150px'}];	
					
			$('input[name ="voucherNB"]').autocomplete({
			showHeader: true,
			columns: columns,
	        source: function(request, response, event, ui) {
		        		
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllIDs',
		                 data: {
			                 	"voucherType" : option,
			                 	"VoucherNb" : request.term,
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {
		                         response(data.Listreq);
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 40, scroll:true,	
		         
		         
		         select: function(event, ui) {
		        	    this.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2] : '');
						return false;
							}
				    }).autocomplete("instance")._renderItem = function(ul, item) {
			            return $("<li class='each'>")
		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                    //item[0] + "</span><br><span class='desc'>" +
		                   // item[1] + "</span><br><span class='desc'>" +
		                    //item[2] + "</span></div>")
		                    item[0] + "</span><br><span class='desc'>" +
		                    item[1] +', '+ item[2] + "</span></div>")
		                .appendTo(ul);
		        	};
		        	$('input[name ="voucherNB"]').focus(function(){
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}						
					});
	               
			/*	select: function(event, ui) {
					this.value = (ui.item ? ui.item[0] : '');
					return false;
				}
	    }).bind('focus', function(event, ui) {       
	      if(!$(this).val().trim())
	            $(this).keydown();
				
		});	*/ 

	});
		/////////////end autocpmplete for VoucherNB 
	 
	//auto complete for warhouse 
		
    	//var columns = [{name: 'WareH Code', minWidth: '150px'}, {name: 'WareH Name', minWidth:'150px'}];	
				
		$("#warcode").autocomplete({
		//showHeader: true,
		//columns: columns,
     
     source: function(request, response) {
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllWarehouse',
	                 data: {
							"warehouseName" : $("#warcode").val(),
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
					prwarehouce.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2] : '');
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
	
	/////////////end autocpmplete for warhouse
	
	
		  //auto complete for Itemcode   
       	var columns = [{name: 'Item Code', minWidth: '150px'}];			
			
			$('input[name ="itmCode"]').each(function(i, el) {
				$(el).autocomplete({
	//				showHeader: true,
					columns: columns,
		    	    source: function(request, response, event, ui) {
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllitem',
				                 data: {
										"itemCode" : request.term,
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {
				                         response(data.ListItem);
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
				         }, minLength:0, maxShowItems: 4, scroll:true,		
			               
				     	select: function(event, ui) {
//							this.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2] : '');
								this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
								return false;
							}
						}).autocomplete("instance")._renderItem = function(ul, item) {
//					}).data('ui-autocomplete')._renderItem = function(ul, item) {
				    	return $('<li class="each"></li>').data( "item.autocomplete", item )
				    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
			                    //item[0] + "</span><br><span class='desc'>" +
			                   // item[1] + "</span><br><span class='desc'>" +
			                    //item[2] + "</span></div>")
			                    item[0] + '</span><br><span class="desc">' +
			                    item[1] + '</span></div>').appendTo(ul);
					};
					$('input[name ="itmCode"]').focus(function(){
						if (this.value == ""){
							$(this).autocomplete("search");
			   	        }
					});
					});			
				//// ended auto complete for itemcode
				
	   //auto complete for ItemModel   
       /*	var columns = [{name: 'Item Model', minWidth: '150px'}];			
			
			$('input[name ="itmModel"]').each(function(i, el) {
				$(el).autocomplete({
	//				showHeader: true,
					columns: columns,
		    	    source: function(request, response, event, ui) {
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetItemModel',
				                 data: {
										"itemModel" : request.term,
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {
				                         response(data.ListItem);
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
				         }, minLength:0, maxShowItems: 4, scroll:true,		
			               
				     	select: function(event, ui) {
//							this.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2] : '');
								this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
								return false;
							}
						}).autocomplete("instance")._renderItem = function(ul, item) {
//					}).data('ui-autocomplete')._renderItem = function(ul, item) {
				    	return $('<li class="each"></li>').data( "item.autocomplete", item )
				    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
			                    //item[0] + "</span><br><span class='desc'>" +
			                   // item[1] + "</span><br><span class='desc'>" +
			                    //item[2] + "</span></div>")
			                    item[0] + '</span><br><span class="desc">' +
			                    item[1] + '</span></div>').appendTo(ul);
					};
					$('input[name ="itmModel"]').focus(function(){
						if (this.value == ""){
							$(this).autocomplete("search");
			   	        }
					});
					});			*/
				//// ended auto complete for itemModel
				
				
	// generating report			 
	$("#generate").click(  function() {

		    var wareCode = $("#warcode").val();
		 	var n = wareCode.indexOf(";");
	     	var resware = wareCode.substring(0, n);
	     	
	     	$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/AssetLifeCycleListView",
					dataType : "json",
					data : {
					    
					    "item": $("#icode").val(),
						"startDate" : $("#startdate").val(),
						"endDate" : $("#enddate").val(),
						"warehouse" : resware,  
						"UOM": $("#UOM").val(),
					},
					success : function(data) {
						console.log("Doneee ");
					

						
						
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});
	     	
	     	
	     	    var boqArray = [];
	     	   boqArray = ${List};
	     	  console.log("######## The boqArray is " +boqArray);
	     	 console.log("######## The first boqArray is " +boqArray[0][0]);
	     	 
	     		//Get the checkbox
	     		 var checkBox1 = document.getElementById("balance");
	     		 var checkBox2 = document.getElementById("ledger");
	     		 // Get the output table
	     		 var table1 = document.getElementById("checktableBalance");
	     		 var table2 = document.getElementById("checktableLedger");
	     		 // Get the total output of table
                 var div1 = document.getElementById("divBal");
                 var div2 = document.getElementById("divLed");
	     		      // If the checkbox is checked, display the output table

	     		        if (checkBox1.checked == true){
	     		          
	     			       table1.style.display = "block";
	     			       div1.style.display = "block";
	     			       
	     			      
	     			      var markup1 = "<tr><td name='count'></td><td name='itm'><input type='text'/></td><td name='itmModel'><input type='text'></td><td name='warhouse'><input type='text'></td><td name='UOM'><input type='text'></td><td name='opqty'><input type='text'></td><td name='opvalue'><input type='text'></td><td name='invalue'><input type='text'></td><td name='outvalue'><input type='text'></td><td name='Bqty'><input type='text'></td><td name='Vrate'><input type='text'></td></tr>";
	     		            $("#bisotab1 > tbody").append(markup1);
	     		            
	     		     /*     
	     		           // Fill tables rows from DB
	     		           for (i = 0; i < boqArray.length; i++){
		     			         console.log("********passes the loop")
		     			        var itemRow = "<tr>";
		     			        itemRow= itemRow + "<td></td>"
		     		 			itemRow =itemRow + "<td name='itm'><input type='text'" + boqArray[i]. + "'></td>";
		     					itemRow =itemRow + "<td name='itmModel'><input type='text'" + boqArray[i].+"'></td>";
		     					itemRow =itemRow + "<td name='warhouse'><input type='text'" + boqArray[i]. +"'></td>";
		     					itemRow =itemRow + "<td name='UOM'><input type='text'" + boqArray[i]. +"'></td>";
		     					itemRow =itemRow + "<td name='opqty'><input type='text'" + boqArray[i]. +"'></td>";
		     		 		    itemRow =itemRow + "<td name='opvalue'><input type='text'" + boqArray[i]. +"' readonly></td>";
		     		 		    itemRow =itemRow +"<td name='invalue'><input type='text'" + boqArray[i].+"' readonly></td>";
		     					itemRow =itemRow + "<td name='outvalue'><input type='text'"  + boqArray[i]. +"' readonly></td>";
		     					itemRow =itemRow + "<td name='Bqty'><input type='text'" + boqArray[i]. +"' readonly></td>";
		     					itemRow =itemRow + "<td name='Vrate'><input type='text'" + boqArray[i].+"' readonly></td>";
		     			        itemRow =itemRow + "</tr>";
		     			        $("#bisotab1 > tbody").append(itemRow);
		     			     }
	     		     */     
	     		        } 
	     		        if (checkBox2.checked == true){
	     		    	  
	     			       table2.style.display = "block";
	     			       div2.style.display = "block";
	     			       
	     			     var markup2 = "<tr><td></td><td name='date'><input type='text'/></td><td name='itm'><input type='text'></td><td name='itmModel'><input type='text'></td><td name='warhouse'><input type='text'></td><td name='voType'><input type='text'></td><td name='voNb'><input type='text'></td><td name='UOM'><input type='text'></td><td name='qty'><input type='text'></td><td name='inRate'><input type='text'></td><td name='AccDep'><input type='text'></td><td name='netPrice'><input type='text'></td><td name='Vrate'><input type='text'></td><td name='Bvalue'><input type='text'></td><td name='netBvalue'><input type='text'></td><td name='Bqty'><input type='text'></td></tr>";
	     		           $("#bisotab2 > tbody").append(markup2);
	     		            
	     		          // get all colmns count per row
	     		  		function count(array){
	     		              var c = 0;
	     		              for(i in array) // in returns key, not object
	     		                  if(array[i] != undefined)
	     		                      c++;
	     		              return c;
	     		       }
	     		           // Fill tables rows from DB
	     		           for (i = 0; i < boqArray.length; i++){
	     		        	var collen= count(boqArray[i]);
	     		        	console.log("*** the count "+collen)
                            var itemRow = "<tr>";
	     		           for (j = 0; j < collen; j++){
		     			         console.log("********passes the loop")
		     			         var tt1= boqArray[i][j];
		     			       // var itemRow = "<tr>";
		     			        itemRow= itemRow + "<td></td>"
		     			       // itemRow =itemRow + "<td name='date'><input type='text'" + boqArray[i]. + "'></td>";
		     		 			//itemRow =itemRow + "<td name='itm'><input type='text'" + boqArray[i].itemCode + "'></td>";
		     					//itemRow =itemRow + "<td name='itmModel'><input type='text'" + boqArray[i].+"'></td>";
		     					itemRow =itemRow + "<td name='warhouse'><input type='text'" + tt1 +"'></td>";
		     					//itemRow =itemRow + "<td name='voType'><input type='text'" + tt1 +"'></td>";
		     					//itemRow =itemRow + "<td name='voNb'><input type='text'" + tt1 +"'></td>";
		     					itemRow =itemRow + "<td name='UOM'><input type='text'" + tt1 +"'></td>";
		     				   // itemRow =itemRow + "<td name='qty'><input type='text'" + boqArray[i]. +"' readonly></td>";
		     					//itemRow =itemRow + "<td name='inRate'><input type='text'" + boqArray[i]. +"'></td>";
		     				   // itemRow =itemRow + "<td name='AccDep'><input type='text'" + boqArray[i]. +"' readonly></td>";
		     				   // itemRow =itemRow + "<td name='netPrice'><input type='text'" + boqArray[i]. +"' readonly></td>";
		     				    //itemRow =itemRow + "<td name='Vrate'><input type='text'" + boqArray[i].+"' readonly></td>";
		     		 		   // itemRow =itemRow + "<td name='Bvalue'><input type='text'" + bogArray[i]. +"' readonly></td>";
		     		 		   // itemRow =itemRow + "<td name='netBvalue'><input type='text'" + bogArray[i]. +"' readonly></td>";
		     		 		   // itemRow =itemRow + "<td name='Bqty'><input type='text'" + boqArray[i]. +"' readonly></td>";
		     					
		     			       
		     			     }
	     		            itemRow =itemRow + "</tr>";
	     			        $("#bisotab2 > tbody").append(itemRow);
	     		           }
	     		           
	     		        /*var len=Cipitem.length;
	     		     	console.log("The lenght is " + len);
	     		     	var itemCodeId;   
	     		     	var collen= count(Cipitem[0]);
	     		     	console.log("The count is " + collen);
	     		             var i =0;
	     		             var itemRow = "<tr>";
	     		             //itemRow= itemRow + "<td><input type='checkbox' name='record'></td>"
	     		             //console.log(itemRow);
	     		             for (j = 0; j < collen; j++) {
	     		             	var tt1= Cipitem[i][j];
	     		             	if (j == 0) {
	     		          			itemRow =itemRow + "<td><input type='text' name='itmCode' value='" + tt1 +"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all' readonly/></td>";
	     		          		}
	     		          		if (j == 5) {
	     		          		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		if (j == 6) {
	     		          		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		if (j == 7) {
	     		          		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		if (j == 1) {    
	     		     				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		if (j == 2) {    
	     		     				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		if (j == 3) {    
	     		     				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		if (j == 4) {    
	     		     				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		//if (j == 8) {    
	     		     			//	itemRow =itemRow + "<td><input type='text' name='itmWare' value='" + tt1 +"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all' readonly/></td>";
	     		          		 //   }
	     		          	   if (j == 8) {
	     		          		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }  
	     		             	if (j == 9) {
	     		          		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }    
	     		          		    if (j == 10) {
	     		          		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		    if (j == 12) {
	     		          		    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		if (j == 13) {    
	     		     				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' readonly></td>";
	     		          		    }
	     		          		    
	     		         		
	     		             }
	     		             itemRow =itemRow + "</tr>";
	     		             $("#bisotab > tbody").append(itemRow);
	     		     	}
	     		         */  
	     		           
	     		           
	     		           
	     		           
	     		           
	     		            
	     		        } 
	     		 
	     		      
	     	
	     	
	     	
	     		
		
	});
		
	
		 
		
	
	 
 });
 
 
 

 
 </script>
 
 
 
 
 
 
 
 
 
 
 
 
 
 
</html>