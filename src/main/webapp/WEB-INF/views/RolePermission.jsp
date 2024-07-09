<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Role Permission</title>
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
<style>
.nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
            </style>
<body>
  
<%--   <c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>

 <!--  end of general head page -->
	<div class="container-fluid">     
		<div class="row">
		<div class="col-md-12">
		<p></p>
		</div>
		
		</div>
		
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  			<div class="modal-dialog" role="document">
    			<div class="modal-content">
      				<div class="modal-header">
        				<h5 class="modal-title" id="exampleModalLabel">Add Role Permission</h5>
        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
         	 			<span aria-hidden="true">&times;</span>
        				</button>
      				</div>
      				<div class="modal-body">
      					<div class="row second">
      						<div class="col-md-auto">
        						<div class="form-group">
		    						<label for="screenType">Screen Type</label>
		    						<select class="form-control" id="screenType">
		    							<option selected>Choose...</option>
		      							<option>Asset Registry</option>
		      							<option>Capital in Progress</option>
		      							<option>Discovery New</option>
		      							<option>Fixed Asset Registry</option>
		      							<option>Goods Received</option>
		      							<option>Item</option>
		      							<option>Purchase Order</option>
								    	<option>Purchase Request</option>
								    	<option>Physical Layer</option>
								    	<option>Physical Layer Manhole</option>
								    	<option>Physical Layer Handhole</option>
								    	<option>Supplier</option>
								    	<option>User</option>
								    	<option>Warehouse</option>
								    	<option>Work Order</option>
								    	<option>Image Approval</option>
		    						</select>
		    					</div>
							</div>
							
							<div class="col-md-auto">
								<div class="form-group">
		    						<label for="roles">Roles</label>
		    						<select class="form-control" id="roles">
		    							<option selected>Choose...</option>
		      							
		    						</select>
		    					</div>
							</div>
							
							<div class="col-md-auto">
								<div class="form-group">
		    						<label for="roles">View Type</label>
		    						<select class="form-control" id="viewType">
		    							<option selected>Choose...</option>
		      							<option>List</option>
								    	<option>Form</option>
								    	<option>Tree</option>
		    						</select>
		    					</div>
							</div>
							
      					</div>
      				</div>
      				<div class="form-group" style="margin-right: 15px; margin-left: 15px;">
					    <label for="LevelInput">Level</label>
					    <input type="number" class="form-control" id="LevelInput" min="0" max="10">
					</div>
      				<div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				        <button type="button" id="saveButton" class="btn btn-primary">Save</button>
      				</div>
    			</div>
  			</div>
		</div>
	
		<div class="row second">
			<div class="col-md-auto" style="display: flex;">
				<div class="form-group">
					<div class="input-group-prepend"  style="margin-top: 5px;">
						<span class="input-group-text" style="color: green">Role Permission</span>
					</div>
				</div>
			</div>
			<div class="mx-auto">
			</div>			
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group mb-3" style="margin-top: 5px;">
  						<div class="input-group-prepend">
    						<label class="input-group-text" for="inputGroupSelect01" style="margin-right: 0px; margin-top: 0px;">Screen Type</label>
  						</div>
						<select class="custom-select" id="screenTypeSearch">
							<option selected>Choose...</option>
							<option>Asset Registry</option>
							<option>Capital in Progress</option>
							<option>Discovery New</option>
							<option>Fixed Asset Registry</option>
							<option>Goods Received</option>
							<option>Item</option>
							<option>Purchase Order</option>
							<option>Purchase Request</option>
							<option>Physical Layer</option>
							<option>Physical Layer Manhole</option>
							<option>Physical Layer Handhole</option>
							<option>Supplier</option>
							<option>User</option>
							<option>Warehouse</option>
							<option>Work Order</option>
							<option>Image Approval</option>

						</select>
					</div>
                </div>
			</div>
			

			
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group mb-3" style="margin-top: 5px;">
  						<div class="input-group-prepend">
    						<label class="input-group-text" for="inputGroupSelect01" style="margin-right: 0px; margin-top: 0px;">Roles</label>
  						</div>
						<select class="custom-select" id="rolesSearch">
							<option selected>Choose...</option>
							
						</select>
					</div>
				</div>
			</div>
			
			<div class="mx-auto">
			</div>			
			<!-- <div class="col-md-auto">
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
			</div> -->
		</div>
	
	
	<p></p>
		<!-- <div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top: 0px;">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;">ROLE PERMISSION</class="nav-link></li>
									
		     </ul>
		     
		</div>
		</div> -->
		
		<div class="row">
			<div class="col-12 col-sm-12 col-lg-12">	
      			<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             		<li class="nav-item"><a class="nav-link active"
            			id="custom-tabs-one-general-tab" data-toggle="tab"
            			href="#custom-tabs-one-general" role="tab"
            			aria-controls="custom-tabs-one-general" aria-selected="true" style="color: gold;">GENERAL</a></li>
            
		            <li class="nav-item"><a class="nav-link"
			            id="custom-tabs-one-field-tab" data-toggle="tab"
			            href="#custom-tabs-one-field" role="tab"
			            aria-controls="custom-tabs-one-field" aria-selected="false" style="color: gold;">FIELD & BUTTON</a></li>
		             
		            
		            <li class="nav-item"><a class="nav-link"
			            id="custom-tabs-one-action-tab" data-toggle="tab"
			            href="#custom-tabs-one-action" role="tab"
			            aria-controls="#custom-tabs-one-action" aria-selected="false" style="color: gold;">ACTION & APPROVAL</a></li>
             
		       
		            
		           <li class="nav-item ml-auto">
	
						<button type="button" data-toggle="modal" data-target="#exampleModal" 
						id="saveButton" class="btn btn-primary BtnActive">
						<i class="fa fa-save"></i> New
						</button>
						
					</li>
							
     			</ul>
     
			</div>
		</div>
</div>


<div class="container-fluid">

<div class="tab-content" id="custom-tabs-one-tabContent">

<div class="tab-pane fade show active" id="custom-tabs-one-general" role="tabpanel" aria-labelledby="custom-tabs-one-general-tab">
		
        <!-- /.card-header -->
		<div class="card-body">
			<div class="row">
				<div class="col-sm-12">
					<table id="example" class="display table table-bordered nowrap" style="width:100%;">
						<thead class="thead-light">
						<tr id="columnSearch"></tr>
    						<tr>
    							<th>Screen Type</th>
    							<th>View Type</th>
    							<th>Roles</th>
    							<th>Level</th>
    							<th>Permissions</th>
    							<th>Action</th>  							
    						</tr>
  						</thead>
  						<tbody>
  						</tbody>
					</table>
				</div>
			</div>
		</div>
</div>

<div class="tab-pane fade" id="custom-tabs-one-field" role="tabpanel" aria-labelledby="custom-tabs-one-field-tab">

</div>

<div class="tab-pane fade" id="custom-tabs-one-action" role="tabpanel" aria-labelledby="custom-tabs-one-action-tab">

</div>

</div>
</div>		
		
</body>


<script> 
$(document).ready(function() {
	
	var tableData = ${ListGridTable};
	var role = ${ListRole};

	var selectSearch = document.getElementById("rolesSearch");
	var select = document.getElementById("roles");

	role.forEach(function(roleName) {
	    var optionSearch = document.createElement("option");
	    optionSearch.text = roleName;
	    selectSearch.appendChild(optionSearch);

	     var option = document.createElement("option");
	    option.text = roleName;
	    select.appendChild(option);
	});

	var code='';
	var slct = [];
	var viewType = '';
	//console.log("tableData is " +tableData + " first item code is " +tableData[0]);
	function checkbox(box,value, Type){
		var array = [];
		var checkbox= ['Read', 'Search Popup', 'Find Connected',  'Projects']
		for (i=0; i<box.length; i++){
			if(Type=='Physical Layer'){
				array [i+1] = '<input type="checkbox" class="form-check-input" value="'+value[i]+'" id="'+box[i]+'"><label style="margin-top: 0px;margin-right: 25px;font-size: 12px;float: none;">'+checkbox[i]+'</label>';	
				

				}
			else{
			array [i+1] = '<input type="checkbox" class="form-check-input" value="'+value[i]+'" id="'+box[i]+'"><label style="margin-top: 0px;margin-right: 25px;font-size: 12px;float: none;">'+box[i]+'</label>';	
		}
		}
		array.unshift('<div class="form-check">');
		array.push('</div>');
		return array	
	}
	
	$('#example thead th').each( function () {
        var title = $(this).text();
        if(title == 'Screen Type' || title == 'View Type' || title == 'Roles' || title == 'Level')
        $('#columnSearch').append( '<th class="inputFilter"><input type="text" placeholder="Search '+title+'" /></th>' );
    } );
	
	
	var table = $('#example').DataTable( {
	"bProcessing": true,
    "aaData": tableData,// <-- your array of objects
    "aoColumns":[
    	{ "mData": [1] }, 
        { 
		  "mData": [2],
		  "sType": "string",
   	      "mRender" : function ( data, type, row, meta ) {
   	    	if (type === 'display'){
   	    	  if (data === 'List'){
   	    		  var dropDown = '<select class="custom-select" id="viewTypeDrop"><option selected>'+data+'</option><option>Form</option><option>Tree</option></select>'
   	    			return dropDown
   	    	  }
   	    	  else if (data === 'Form'){
				var dropDown = '<select class="custom-select" id="viewTypeDrop"><option>List</option><option selected>'+data+'</option><option>Tree</option></select>'
				return dropDown
   	    	  }
   	    	 else if (data === 'Tree'){
 				var dropDown = '<select class="custom-select" id="viewTypeDrop"><option>List</option><option>Form</option><option selected>'+data+'</option></select>'
 				return dropDown
    	    	  }
   	    	}
   	    		
   	    	//console.log('table.cell(0,1) is: '+table.cell(2,1).nodes());
   	    	if(type === 'filter' || type === 'sort'){
   	    		return data
   	    		}
   	    		
   	    		
   	     }
        },
		{ "mData": [3] },
		{ "mData": [4] },
		{
		  "mData": null,
          "bSortable": false,
 	      "mRender" : function ( tableData, type, row ) {
 	    	 var read = tableData[5]; var write = tableData[6]; var add = tableData[7]; var del = tableData[8];
 	    	 var save = tableData[9]; var status = tableData[10]; var action = tableData[11]; var download = tableData[12];
 	    	 var exports = tableData[13]; var secondlvl = tableData[14]; var firstlvl = tableData[15];

 	    	 if(tableData[1] == 'Image Approval' && tableData[2] == 'List'){
 	    			var box = ['Read','Export','First Level Validation', 'Second Level Validation'];
	        		var value = [read,exports, firstlvl,secondlvl];
 	 	    	 }
 	    	 else{ 
 	 	    	 if(tableData[2] === 'List'){
 	        		var box = ['Read', 'Delete'];
 	        		var value = [read, del];
 	      		}
 	        	
 	        	else if(tableData[1] === 'Physical Layer'){
 	        		var box = ['Read', 'Write', 'Add',  'Save'];
 	        		var value = [read, write, add, save] ;
 	        	}
 	        	else {
 	        		var box = ['Read', 'Write', 'Add', 'Delete', 'Save'];
 	        		var value = [read, write, add, del, save];
 	        	}
 	    	 }
 	            	return checkbox(box,value,tableData[1]).join('')}
         },
         {
        	 "mData": null,
            	"bSortable": false,
  	        "mRender" : function ( tableData, type, full ) { 
  	            	return '<button id="applyPerm" class="btn btn-success btn-sm" style="margin:5px;"><i class="fas fa-check-square"></i></button><button id="deletePerm" class="btn btn-danger btn-sm" style="margin:5px;"><i class="fa fa-trash"></i></button>'
  	            }
        },
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
      'order': [[2, 'asc']],
      	dom: 'Blfrtip',
		"paging":true,
		"pageLength":10, 
		"scrollY": 310,
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
	
	$.fn.dataTable.ext.search.push(
            function (settings, data, dataIndex, rowData, counter) {

                var searchText = $('*[placeholder="Search View Type"]').val();         
                var textFound = true;
                
                if (searchText.length) {
                    var pattern = new RegExp(searchText, 'i');
                    if (pattern.test(rowData[2])) {
                    	//console.log('rowData[2] is: ' +rowData[2]);
                        textFound = true;
                    }else{
                        textFound = false;
                    }                 
                }
                return textFound;

            }
        );
	

	$('#columnSearch input[type="text"]').on('keyup change clear', function(){
		if($(this).attr('placeholder') == 'Search Screen Type'){
			table.column(0).search(this.value).draw();
			//console.log('value is: '+this.value);
		}
		if($(this).attr('placeholder') == 'Search View Type'){
			if (table.column(1).search() !== this.value) {
				table.column(1)
		           .search(this.value)
		           .draw();
		       }
		}
		if($(this).attr('placeholder') == 'Search Roles'){
			table.column(2).search(this.value).draw();
		}
		if($(this).attr('placeholder') == 'Search Level'){
			table.column(3).search(this.value).draw();
		}
		
	});
	
	
	$('#screenTypeSearch').change( function(){
		var search = $("option:selected", this).text();
		table.column( 0 ).search( search ).draw();
		if (search === 'Choose...'){
			table.column( 0 ).search( '' ).draw();
		}
	});
	
	$('#rolesSearch').change( function(){
		var search = $("option:selected", this).text();
		table.column( 2 ).search( search ).draw();
		if (search === 'Choose...'){
			table.column( 2 ).search( '' ).draw();
		}
	});
	
	$('input[type="checkbox"]').each(function() {
		if ($(this).val() == 1) {
			$(this).prop('checked', true);
		  }
		else{
			$(this).prop('checked', false);
		}
	});
	
	$('input[type="checkbox"]').click( function() {
		if($(this).is(":checked")){
			$(this).val('1')
			console.log('Checkbox with id: '+$(this).attr('id')+' is checked and its value is: '+$(this).val());
		}
		else{
			$(this).val('0')
			console.log('Checkbox with id: '+$(this).attr('id')+' is unchecked and its value is: '+$(this).val());
		}
	});
	
	$('#example tbody').on( 'change', '#viewTypeDrop', function () {
   		viewType = $("option:selected", this).text();
   		var row = table.row($(this).closest('tr')).data();
		var permID = row[0];
		var Read = 0; var Write = 0; var Add = 0; var Delete = 0; var Save = 0;
		var Status = 0; var Action = 0; var Download = 0; var Export =0; var SecondLvl = 0; var FirstLvl = 0;
	
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/rolePermissionApply",
			dataType : "json",
			data : {
			     
				"permID" : permID,
				"viewType" : viewType,
				"readPerm" : Read,
				"writePerm" : Write,
				"addPerm" : Add,
				"delPerm" : Delete,
				"savePerm" : Save,
				"statusPerm" : Status,
				"actionPerm" : Action,
				"downloadPerm" : Download,
				"exportPerm" : Export,
				"secondlvlPerm" : SecondLvl,
				"firstlvlPerm" : FirstLvl
							
			},
			success : function(data) {
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
   		
   	});
	
	$('#example tbody').on( 'click', '#deletePerm', function () {
		var row = table.row($(this).closest('tr')).data();
		var permID = row[0];
		
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/rolePermissionDelete",
			dataType : "json",
			data : {
			     
				"permID" : permID,
							
			},
			success : function(data) {
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		
		
		
	});
	
	
	$('#example tbody').on( 'click', '#applyPerm', function () {
		var row = table.row($(this).closest('tr')).data();
		var permID = row[0];
		var Read = 0; var Write = 0; var Add = 0; var Delete = 0; var Save = 0;
		var Status = 0; var Action = 0; var Download = 0; var Export = 0; var SecondLvL =0; var FirstLvL =0;
		//console.log(jQuery('input[type="checkbox"]', $(this).closest('tr')));
		$(jQuery('input[type="checkbox"]', $(this).closest('tr'))).each(function() {
			if ($(this).val() == 1) {
				switch ($(this).attr('id')){
					case 'Read':
						Read = 1;
						break;
					case 'Write':
						Write = 1;
						break;
					case 'Add':
						Add = 1;
						break;
					case 'Delete':
						Delete = 1;
						break;
					case 'Save':
						Save = 1;
						break;
					case 'Status':
						Status = 1;
						break;
					case 'Action':
						Action = 1;
						break;
					case 'Download':
						Download = 1;
						break;
					case 'Export':
						Export = 1;
						break;
					case 'First Level Validation':
						FirstLvL = 1;
						break;
					case 'Second Level Validation':
						SecondLvL = 1;
						break;
				console.log('YES Checked');
				//slct.push($(this).attr('id'));
				//perm[($(this).attr('id'))] =1;
				//console.log('Add is: '+Add);
			  }
			}
			else{
				console.log('Not Checked');
				//perm[($(this).attr('id'))] =0;
				//console.log('Save is: '+Save);
			}
			console.log('Add is: '+Add+' Read is: '+Read);

			
		});
		
		$(jQuery('#viewTypeDrop', $(this).closest('tr'))).each(function() {
			
			viewType = $("option:selected", this).text();
			console.log('viewType is: '+viewType);

		});
		//slct.unshift(permID);
		//console.log('Checked array is: '+slct);
		
		console.log(FirstLvL);
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/rolePermissionApply",
			dataType : "json",
			data : {
			     
				"permID" : permID,
				"viewType" : viewType,
				"readPerm" : Read,
				"writePerm" : Write,
				"addPerm" : Add,
				"delPerm" : Delete,
				"savePerm" : Save,
				"statusPerm" : Status,
				"actionPerm" : Action,
				"downloadPerm" : Download,
				"exportPerm" : Export,
				"secondlvlPerm" : SecondLvL,
				"firstlvlPerm":FirstLvL
							
			},
			success : function(data) {
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		
		
	});
	
	
	$("#saveButton").click(  function() {
		 console.log('saved now');
		 
		 
		 if ($("#screenType").val()== '') {
		 alert('Please choose a Screen Type');
		 return false;}
		 
		 if ($("#viewType").val()== '') {
			 alert('Please choose a View Type');
			 return false;}
		 
		 if ($("#roles").val()== '') {
			 alert('Please choose a role');
			 return false;}
		 
		 if ($("#LevelInput").val()== null || $("#LevelInput").val()== "") {
			 alert('Please insert Level number');
			 return false;}
		 
		 var x = '0';
		 
		 
	      //console.log("screen " +screen);
		 //console.log("role " +role);
		 //console.log("roleLevel " +roleLevel);
	     
	     
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/rolePermissionSave",
				dataType : "json",
				data : {
				     
					"permID" : x,
				    "screen" : $("#screenType").val(),
				    "viewType" : $("#viewType").val(),
					"role" : $("#roles").val(),
					"roleLevel" : $("#LevelInput").val(),
					"readPerm" : x,
					"writePerm" : x,
					"addPerm" : x,
					"delPerm" : x,
					"savePerm" : x,
					"statusPerm" : x,
					"actionPerm" : x,
					"downloadPerm" : x,
					"exportPerm" : x,
					"secondlvlPerm" : x,
					"firstlvlPerm":x
								
				},
				success : function(data) {
					location.reload();
				},
				error : function(error) {
					console.log("The error is " + error);
				}
			});
	});
});



</script>


           
 </html>
