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
		<div class="col-md-12">
		<p></p>
		</div>
		
		</div>
	
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
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top: 0px;">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;text-align:center;line-height:2.75em; padding-left:5px;">ITEM LIST</li>
	 
		            <li class="nav-item ml-auto">
						<button type="button" id="deleteButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-trash"></i> Delete
						</button>  
		                        
						<button type="button" id="saveButton" onclick='window.location.href = "${pageContext.request.contextPath}/ItemFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
						</button>  </li>
									
		     </ul>
		     
		</div>
		</div>
        <!-- /.card-header -->
		<div class="card-body">
			<div class="row">
				<div class="col-sm-12">
					<table id="example" class="display table table-bordered nowrap" style="width:100%;">
						<thead class="thead-light">
    						<tr>
								<th style="text-align:center;"><input id="multiselect" name="select_all" value="1" type="checkbox"></th>
    							<th>Item Code</th>
    							<th>Name </th>
    							<th>Domain</th>
    							<th>Description </th>
    							<th>Unit</th>
    							
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
	var code='';
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
           // how to pass parameter to ItemFormView
            	return    '<a href="'+ '${pageContext.request.contextPath}/ItemFormView?itemcode='+full[1] +'&itemname='+full[2] +'">' + url + '</a>';
				}
   
       },
      {
          "mData":[2],
         },
         {
           "aTargets":[ 3 ]
         , "sType": "String"
         , "mRender": function(url, type, full) {
            //console.log('chk data : '+full[3]);
            if (full[3] == "Transport2") {return    '<a href="'+ '/Dashboard' +'">' + url + '</a>';}
            else {return full[3]; }
           }
         },
         {
     		"aTargets":[ 4 ]  
        },
        {
    		"aTargets":[ 5 ]   
       } 
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
	
	$('#example_paginate').click( function() {
		if (table.rows( '.selected', { page: 'current' }).count() != table.rows({ page: 'current' }).count()) {
			$('#multiselect').prop('checked', false);
            $('#multiselect').removeClass("selected");
            console.log(table.rows( '.selected', { page: 'current' }).count());
      	}
		else {
      		$('#multiselect').prop('checked', true);
      		$('#multiselect').addClass("selected");
      		console.log('array is: ' +slct);
      	}
	});
	
	$('#multiselect').on('click', function() {
	    var num = table.rows({ page: 'current' }).count();   
	    if ($('#multiselect').hasClass("selected")) {
	 
	    	for(i=0; i<num; i++){
				x=i;
				table.row(':eq('+x+')', { page: 'current' }).deselect();
				var pos = table.row(':eq('+x+')', { page: 'current' }).index();
	    		var row = table.row(pos).data();
				var str= new Array();
	      		str=row
	      		code=str[1];
	      		removeItem = code;
	      		console.log('item to remove: '+code);
	      		slct = jQuery.grep(slct, function(value) {
					return value != removeItem;
	      		});
				}
				$('#multiselect').prop('checked', false);
				$('#multiselect').removeClass("selected");
				slct = slct.filter(function(elem, index, self) {
					return index === self.indexOf(elem);
				});
				console.log('array is: ' +slct);
	    	
	    }
	    else {
	    	
	    	var i;
			var x=0;
			for(i=0; i<num; i++){
			x=i;
			table.row(':eq('+x+')', { page: 'current' }).select();
			var pos = table.row(':eq('+x+')', { page: 'current' }).index();
			var row = table.row(pos).data();
			var str= new Array();
	      	str=row
	      	code=str[1];
	      	slct.push(code);
	      	console.log('array is: ' +slct);
			}
			$('#multiselect').prop('checked', true);
			$('#multiselect').addClass("selected");
			slct = slct.filter(function(elem, index, self) {
				return index === self.indexOf(elem);
			});
			console.log('array is: ' +slct);
	    }
			
	});
	
	
     $('#example tbody').on( 'click', 'tr', function () {
     	var $checkbox = $(this).toggleClass('selected')
		if($(this).hasClass('selected')) {
    		var pos = table.row(this).index();
    		var row = table.row(pos).data();
    		var str= new Array();
		    str=row
		    code=str[1];
		    slct.push(code);
		    console.log(code);
		    slct = slct.filter(function(elem, index, self) {
				return index === self.indexOf(elem);
			});
		    console.log('array is: ' +slct);
		}
		
		else { 
		    $checkbox.removeAttr('checked');
		    console.log( 'unselected NO' );
		    var pos = table.row(this).index();
    		var row = table.row(pos).data();
    		var str= new Array();
		    str=row
		    code=str[1];
		    removeItem = code;
		    console.log('item to remove: '+code + ' array is: ' +slct);
		    slct = jQuery.grep(slct, function(value) {
        		return value != removeItem;
      		});
		    slct = slct.filter(function(elem, index, self) {
				return index === self.indexOf(elem);
			});
      		console.log('array after remove is: '+slct);
		}

		if (table.rows( '.selected', { page: 'current' }).count() !== table.rows({ page: 'current' }).count()) {
			$('#multiselect').prop('checked', false);
            $('#multiselect').removeClass("selected");
            console.log('array is: ' +slct+ ' array length is: ' +slct.length);
      	}
		else {
      		$('#multiselect').prop('checked', true);
      		$('#multiselect').addClass("selected");
      		console.log('array is: ' +slct);
      	}
      	console.log('array is: ' +slct);
         
		
    });
				
				
 	$("#deleteButton").click(  function() {
		console.log('delete now');
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ItemListViewDelete",
			dataType : "json",
			data : {
				"itemCode" : slct
			},
			success : function(data) {
				//console.log("The returned data is " +data.BassamTest);
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		console.log('slct is: ' + slct);
	 
 	});
				
});

 
 


</script>


           
 </body>
 </html>
