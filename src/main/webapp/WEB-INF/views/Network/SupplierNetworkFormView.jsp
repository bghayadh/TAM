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
	
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->

	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	 
	<script src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>
	
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
     
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
                <li class="nav-item"><a href="/Purchase" class="nav-link "  style="color: #fff"><span class="border-bottom active"><i class="fas fa-money-check" style="color: #20B2AA"></i> Purchasing </span></a></li>
                <li class="nav-item"><a href="/Inventory" class="nav-link " style="color: #fff"><i class="fas fa-warehouse" style="color: gold"></i> Inventory </a></li>
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
			<div class="col-md-12">
			<p></p>
			</div>
		
		</div>
		
			
		<div class="row second">
			<div class="col-md-4">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Supplier Form</span>
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus">Saved</label>							
						</div>

					</div>
			</div>
			
			<div class="col-md-5">
                   <div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Supplier ID</span>
							<input type="text" id="suppcode" readonly  value="${supplierID}" class="form-control text-input"   />
						</div>
					</div>
			</div>
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/SupplierListView"'
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


<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-profile-tab" data-toggle="tab"
            href="#custom-tabs-one-profile" role="tab"
            aria-controls="custom-tabs-one-profile" aria-selected="false" style="color: gold;">ADDRESS / CONTACT</a></li>
             
            
            
       
            
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
	<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

	<p></p>
    
	<div class="row">
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Supplier Name</span>
						<input type="text" id="suppname" value="${supplierName}"  class="form-control text-input" />
					</div>
				</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
						<span class="input-group-text">Created Date</span>
						<input type="text" id="screatedate" readonly value="${screationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
						   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
								<div class="input-group-text">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="slstmodifdate" readonly value="${slastModifieddate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
		</div>
	</div>
 	</div>
 	
	<div class="row">
		<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Supplier Country</span>
				<input type="text" id="suppcountry"
				 value="${sCountry}" class="form-control text-input" />
			</div>							
		</div>
		
		<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Vendor number</span>
				<input type="text" id="svendorNb" value="${svendorNb}" class="form-control text-input" />
			</div>							
		</div>
	
  	</div>
  	<p></p>
	<div class="row">
	<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Supplier Category</span>
				<input type="text" id="suppcat" value="${supplierCategory}" style="width:700px;" class="ui-widget ui-widget-content ui-corner-all"/>
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Supplier Tax</span>
					<input type="text" id="supptxid" value="${supplierTaxid}" class="form-control text-input"/>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Credit Limit</span>
					<input type="text" id="suppcrdlimit" value="${supplierCreditlimit}" class="form-control text-input" />
				</div>
			</div>
		</div>
  </div>
	
	<div class="row">
	<div class="col-md-4">
			<div class="form-group">
				
				<div class="input-group-prepend">
	             	 <label class="form-check-label" style="margin-top:-5px;">Disabled</label>
              		 <input type="checkbox" id="suppdisables" ${sDisabled}/>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<div class="row">
		<div class="col-md-8">
			<div class="input-group-prepend">
				<span class="input-group-text">Supplier Description</span>
			</div>							
		</div>
	</div>

	<div class="row">
		<div class="col-md-5">
			<div class="input-group-prepend">
				<textarea name="suppdescrip" cols="220" rows="8" id="suppdescrip"> ${supplierDescription} </textarea>
			</div>							
		</div>
	</div>
	
	</div>
	<div class="tab-pane fade" id="custom-tabs-one-profile"
		role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab">
	
<p></p>


<div class="row">

	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Website</span>
						<input type="text" id="suppweb" value="${sWebsite}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Address 1</span>
						<input type="text" id="suppadd1" value="${supplierAddress1}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	
	 <div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Address 2</span>
						<input type="text" id="suppadd2" value="${supplierAddress2}" class="form-control text-input" />
					</div>
				</div>
	 </div>
	</div>
	<div class="row">
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Phone</span>
						<input type="text" id="suppphone" value="${sPhone}" class="form-control text-input"  />
					</div>
				</div>
		</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
					<span class="input-group-text">Mobile</span>
					<input type="text" id="suppmobile" value="${sMobile}" class="form-control text-input"/>
					</div>
				</div>
		</div>
	
		<div class="col-md-4">
				<div class="input-group-prepend">
				<span class="input-group-text">Email</span>
				<input type="text" id="suppemail" value="${sEmail}" class="form-control text-input" />
				</div>							
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Contact Person</span>
				<input type="text" id="suppcontactp" value="${sContactperson}" class="form-control text-input"  />
			</div>
		</div>
		
	
	</div>
  </div>


</div>


</div>
           

           
 </body>
 
 
 <script>
 			$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
   
 
 
 $("#saveButton").click(  function() {
	 console.log('saved now');
	 
	 
	  // validate supplier code cannot be null
	/* if ($("#suppcode").val()== '') {
	 alert('Supplier ID cannot be Null');
	 return false;}*/
	 
	 
	 // validate credit limit to be number
	 if (($. isNumeric( $("#suppcrdlimit").val()))== false) {
	 alert('Credit Limit is  not Numeric');
	 return false;}
	 
	 // validate creatd date cannot be null
	 val =$("#screatedate").val();
     val1 = Date.parse(val);
     //console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
	 
	  // validate modified date cannot be null
	 val =$("#slstmodifdate").val();
     val1 = Date.parse(val);
     //console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
	 
	 
	 
	 
	 var supplierID = document.getElementById("suppcode").value;
	 var screationDate = document.getElementById("screatedate").value;
	 var slastModifieddate = document.getElementById("slstmodifdate").value;
	 var supplierName = document.getElementById("suppname").value;
	 var supplierDescription = document.getElementById("suppdescrip").value;
	 var supplierCategory = document.getElementById("suppcat").value;
	 var sWebsite = document.getElementById("suppweb").value;
	 var svendorNb = document.getElementById("svendorNb").value;
	 var sCountry = document.getElementById("suppcountry").value;
	 var supplierTaxid = document.getElementById("supptxid").value;
	 var supplierCreditlimit = document.getElementById("suppcrdlimit").value;
	 var supplierAddress1 = document.getElementById("suppadd1").value;
	 var supplierAddress2 = document.getElementById("suppadd2").value;
	 var sPhone = document.getElementById("suppphone").value;
	 var sMobile = document.getElementById("suppmobile").value;
	 var sEmail = document.getElementById("suppemail").value;
	 var sContactperson = document.getElementById("suppcontactp").value;
	 var checkBox = document.getElementById("suppdisables");
	 var sDisabled ;
	 if (checkBox.checked == true){
		  sDisabled = '1';
		  } else
			  {sDisabled = '0';}
	  
	 console.log("supplierID" +supplierID);
	 console.log("screationDate" +screationDate);
	 console.log("slastModifieddate" +slastModifieddate);
	 console.log("supplierName" +supplierName);
	 console.log("supplierDescription" +supplierDescription);
	 console.log("supplierCategory" +supplierCategory);
	 console.log("sWebsite" +sWebsite);
	 console.log("sCountry" +sCountry);
	 console.log("svendorNb" +svendorNb);
	 console.log("supplierTaxid" +supplierTaxid);
	 console.log("supplierCreditlimit" +supplierCreditlimit);
	 console.log("supplierAddress1" +supplierAddress1);
	 console.log("supplierAddress2" +supplierAddress2);
	 console.log("sPhone" +sPhone);
	 console.log("sMobile" +sMobile);
	 console.log("sEmail" +sEmail);
	 console.log("sContactperson" +sContactperson);
	 console.log("sDisabled" +sDisabled);
		 
	 //var suppCat = $("#suppcat").val();
	 //var n = suppCat.indexOf(";");
     //var res = suppCat.substring(0, n);
     
     console.log("Supplier category is " +$("#suppcat").val());
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/SupplierFormSave",
			dataType : "json",
			data : {

				
				"supplierID" : $("#suppcode").val(),
				"screationDate" : $("#screatedate").val(),
				"slastModifieddate" : $("#slstmodifdate").val(),
				"supplierName" : $("#suppname").val(),
				"supplierDescription" : $("#suppdescrip").val(),
				"supplierCategory" : $("#suppcat").val(),
				"sWebsite" : $("#suppweb").val(),
				"sCountry" : $("#suppcountry").val(),  
				"supplierTaxid" : $("#supptxid").val(),
				"supplierCreditlimit" : $("#suppcrdlimit").val(),
				"supplierAddress1" : $("#suppadd1").val(),
				"supplierAddress2" : $("#suppadd2").val(),
				"svendorNb" : $("#svendorNb").val(),
				"sPhone" : $("#suppphone").val(),
				"sMobile" : $("#suppmobile").val(),
				"sEmail" : $("#suppemail").val(),
				"sContactperson" : $("#suppcontactp").val(),
				"sDisabled" : sDisabled	 
			},
			success : function(data) {
				console.log("The returned data is " +data.BassamTest);
				$('#slstmodifdate').val(data.slastModifieddate);
				console.log("The SupplierID data is " +data.supplierID);
				$("#suppcode").val(data.supplierID);
				var param ="${pageContext.request.contextPath}/SupplierFormView?supplierID="+$("#suppcode").val();
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
 $("#deleteButton").click(  function() {
	 console.log('delete now');
	 var supplierID = document.getElementById("suppcode").value;
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/SupplierFormViewDelete",
			dataType : "json",
			data : {
				"supplierID" : $("#suppcode").val()
			},
			success : function(data) {
				//console.log("The returned data is " +data.BassamTest);
				//window.history.back();
			    location.replace("${pageContext.request.contextPath}/SupplierListView");
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
 </script>
 
 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css"/>
 
 <style>
       		.ui-autocomplete {
            	max-height: 100px;
				overflow-y: auto; /* prevent horizontal scrollbar */
				overflow-x: hidden; /* add padding to account for vertical scrollbar */
				padding-right: 100px;
        	} 
    	</style>
 <script type='text/javascript'>
	$(document).ready(function() {

				
		function customRenderItem(ul, item) {
			var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
				result = $('<li class="ui-menu-item" role="menuitem"></li>')
				.data('item.autocomplete', item)
				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
				.appendTo(ul);

			return result;
		}

		//var columns = [{name: 'ID', minWidth: '100px'}, {name: 'Name', minWidth:'100px'}, {name: 'Salary', minWidth:'70px'}],
			//colors = [['100', 'Bilal','120'], ['101', 'Bassam','110'], ['102', 'Salem','105'],['103', 'Sima','170'], ['104', 'Mary','160'], ['105', 'Roy','105']];
       	var columns = [{name: 'Supp Code', minWidth: '150px'}, {name: 'Supp Name', minWidth:'150px'}, {name: 'Supp crdlimit', minWidth:'100px'}];	
				
		$("#suppcat").mcautocomplete({
		showHeader: true,
		columns: columns,
        //source: colors, minLength:0, maxShowItems: 4, scroll:true, 
        source: function(request, response) {
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllSuppliers',
	                 data: {
							"supplierCategory;" : $("#suppcat").val(),
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListItemSupplier);
	                         //console.log('data is ;'+ data.ListItemSupplier);
	                         //colors = data.ListItemSupplier;
	                         //console.log('colors ;'+ colors);
	                     
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 4, scroll:true,		
               
        
		select: function(event, ui) {
				suppcat.value = (ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2]: '');
				return false;
			}
    }).bind('focus', function(event, ui) {       
      if(!$(this).val().trim())
            $(this).keydown();
			
	});



		if ('${ListPRqItem}' != "addNew") {
		}
		else {
		          // set status to new and green while new record
		           $("#formStatus").text("New");
						$('.dot').css({"background-color" : "orange"});
		       }	
			
			
		
		
});

 
</script>
 
 

	
 </html>
