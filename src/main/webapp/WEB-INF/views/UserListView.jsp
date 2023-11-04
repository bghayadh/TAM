<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>User List View</title>
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
  <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
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
      
	
 </head>
 <body>

<%-- 	<c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
		<div class="row second">
			<div class="col-md-9">
			</div>
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-light" data-toggle="tooltip" 
			 				data-placement="top" title="Form View"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-danger" data-toggle="tooltip"
			        			data-placement="top" title="List View" style="background: #da6815;"> 
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
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-right:0px">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;text-align:center; font-size: 25px; margin: 10px">UserList</li>
	 
		            <li class="nav-item ml-auto">
						<button type="button" id="deleteButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-trash"></i> Delete </button>  
		                                                                                                                         
						<button type="button" id="saveButton" onclick='window.location.href = "${pageContext.request.contextPath}/UserFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add </button>
						  </li>
									
		     </ul>
		     
		</div>
		</div>
 <!-- /.card-header -->
 <!-- /.card-header -->
		<div class="card-body">
			<div class="row">
				<div class="col-sm-12">
					<table id="example" class="display table table-bordered nowrap" style="width:100%;">
						<thead class="thead-light">
    						<tr>
								<th style="text-align:center;"><input id="multiselect" name="select_all" value="1" type="checkbox"></th>
    							<th>User Name</th>
    							<th>Email Address</th>				
    						</tr>
  						</thead>
  						<tbody>
  						</tbody>
					</table>
				</div>
			</div>
		</div>
		
	</div>
		

<script> 
$(document).ready(function() {
	var bassamData = ${ListGridTable};
	console.log(bassamData);
	var username='';
	var slct = [];
	//console.log("bassamData is " +bassamData + " first item code is " +bassamData[0]);
	var table = $('#example').DataTable( {
	"bProcessing": true,
    "aaData": bassamData,// <-- your array of objects
    "aoColumns":[
    	{
        	"mData": null,
          	"bSortable": false,
	        "mRender" : function ( bassamData, type, full ) { 
		        
	            	return ''}    		
    	}, 
        
        {
            "aTargets":[ 1 ],
            "sType": "String",
            "mRender": function(url, type, full) {
           //console.log('chk data : '+full[3]);
           // how to pass parameter to UserFormView
            	return    '<a href="'+ '${pageContext.request.contextPath}/UserFormView?username='+full[1] +'&emailAddress='+full[2] +'">' + url + '</a>';
				}
   
       },
      {
          "mData":[2],
         },
	
		],
          'columnDefs': [
         {
            'targets': 0,
            className : 'select-checkbox',
            'checkboxes': {
               'selectRow': true,
               'selectCallback': function(nodes, selected){
                  // If "Show all" is not selected
                  if($('#ctrl-show-selected').val() !== 'all'){
                     // Redraw table to include/exclude selected row
                     table.draw(false);                  
                  }            
               }
            },
         }
      ],
		select : {
			style : 'multi',
			selector : 'td:first-child'
		},  
		'order': [[2, 'asc']],
			select: true,
			order : [ [ 0, 'asc' ] ],
			'select': { style: 'multi',
      selector : 'td:first-child'
      },
      'order': [[2, 'asc']],
      	dom: 'Blfrtip',
		"paging":true,
		"pageLength":10, 
		"scrollY": 310,
		"scrollX": true,
		"ordering":true,
		"rowReorder": true,
		"info":       true,
		"filter":     true,
		"length":     true,
		"processing": true,
		"deferRender": true,
          //orderable : false,
      
   });
// checkbox
     $('#example tbody').on( 'click', 'tr', function () {
            var $checkbox = $(this).toggleClass('selected')
			if($(this).hasClass('selected')) {
    			var pos = table.row(this).index();
    			var row = table.row(pos).data();
    			var str= new Array();
		      	str=row
		      	username=str[1];
		      	slct.push(username);
		      	// console.log(username);
		      	console.log('array is: ' +slct);
		      	} else { 
		      	   $checkbox.removeAttr('checked');
		      		console.log( 'unselected NO' );
		      		var pos = table.row(this).index();
    				var row = table.row(pos).data();
    				var str= new Array();
		      		str=row
		      		username=str[1];
		      		removeItem = username;
		      		console.log('user to remove: '+username);
		      		slct = jQuery.grep(slct, function(value) {
        				return value != removeItem;
      					});
      					console.log('array after remove is: '+slct);
		      	 }
		
    } );

			$('#multiselect').on('click', function() {
			    var count=$('[name="example_length"]').val();
			    
					if ($("th.select-checkbox").hasClass("selected")) {
						table.rows().deselect();
						$("th.select-checkbox").removeClass("selected");
					} else {
						var p = table.rows({ page: 'current' }).nodes();
						var i;
						var x=0;
						for(i=0; i<=count; i++){
						x=i;
						table.row(':eq('+x+')', { page: 'current' }).select();
						}
						//table.row({page: 'current' }).select();
						//table.rows().select();
						$("th.select-checkbox").addClass("selected");
						
					}
				}).on("select deselect", function() {
					if (table.rows({
						selected : true
					}).count() !== table.rows().count()) {
			          $("th.select-checkbox").removeClass("selected");
					} else {
						$("th.select-checkbox").addClass("selected");
					}
				}); 

	 
			 $("#deleteButton").click(  function() {
			 		 console.log('delete now');
			 		var usernames ='0';
			 		var i;
			 		for (i = 0; i < slct.length; ++i) {
			 		    usernames =slct[i];
			 		    console.log("the  usennames " +usernames);
						$.ajax({
							type : "GET",
							url : "${pageContext.request.contextPath}/UserFormDelete",
							dataType : "json",
							data : {
								"userName" : usernames
							},
							success : function(data) {
								console.log("The returned data is " +data.Safatest);
								location.reload();
								
							},
							error : function(error) {
								console.log("The error is " + error);
							}
						});
						
						
					}
			 
		 })
			   
				
		} );


</script>


           
 </body>
 </html>
