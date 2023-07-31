<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">		  		
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <!-- 		
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	 -->
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">	 	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link href="${pageContext.request.contextPath}/resources/cssgrid/bootstrap-datepicker.min.css" rel='stylesheet' type='text/css'>
<script src="${pageContext.request.contextPath}/resources/scriptsgrid/bootstrap-datepicker.min.js" type='text/javascript'></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pagination.class.js"></script>

	
	

            
</head>




<style>

 select
        {
            width: 170px;
            border: 1px solid blue;
            border-radius: 5px;
            padding: 4px;
            cursor: pointer;
           

        }
 #gridTable
        {
          border-collapse: collapse;
          border: 1px solid grey;
          overflow-x: auto;
          margin-top: 15px;
            
        }
        #gridTable td, #gridTable th {
        border: 1px solid grey;
        text-align: center;
        }
        #gridTable tr:first-child td {
        border-top: 0;
        }
        #gridTable tr td:first-child {
        border-left: 0;
        }
        #gridTable tr:last-child td {
        border-bottom: 0;
        }
        #gridTable tr td:last-child {
        border-right: 0;
        }

        #gridTable tr#search_by_column td 
        {
            padding: 8px;
  
  
}
         ::-webkit-input-placeholder { /* Chrome/Opera/Safari */
 font-size:12px;
}



</style>
<body>
  
  <!-- nav -->
  <c:set var="pg" value="network" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
         


 <!--  end of general head page -->
	<div class="container-fluid">     
		<div class="row">
		<div class="col-md-12">
		<p></p>
		</div>
		
		</div>
	
		<div class="row second">
			<div class="col-md-8">
			</div>
			<div class="col-md-4" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-light" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" onclick='window.location.href = "${pageContext.request.contextPath}/CellFormView"'> <i class="fa fa-edit"></i>
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
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top:0px">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"style="color: gold;text-align:center;line-height:2.75em; padding-left:5px;">CELL LIST</li>
	 
		            <li id="Buttons" class="nav-item ml-auto">
						  
		                                                                                                                         
						  </li>
									
		     </ul>
		     
		</div>
		</div>
		 <div class=".col-sm-2"id="combobox" style="padding-top:25px;  font-size:15px;  margin-left:10px; ">
                    Show
                    <select class="cmb-row-count" style="width:75px;">
                        <option value="5">5</option>
                        <option value="10" selected>10</option>
                        <option value="15">15</option>                        
                    </select>
                    Rows
                </div>
        <!-- /.card-header -->
		<div class="card-body">
			<div class="row">
				<div class="table-responsive-sm" >
			<table id="gridTable"  class="table-responsive-sm" style="display:block; height:245px; overflow-y: auto; width:1320px; margin-top:-10px;"> 
        <tr class="header">
          <th style="width:5%; text-align:center; padding-top:10px;   ">Cell ID <button style="font-size:8px; float:right; border:none; "  data-target="#openModal" data-toggle="modal"  data-whatever="1,string"  onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black;"></i></button></th>
          <th style="width:5%; text-align:center;  padding-top:10px; ">Cell Name<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="2,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black"></i></button></th>
          <th style="width:5%; text-align:center;  padding-top:10px; ">Node ID <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="3,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black"></i></button></th>
          <th style="width:12%; text-align:center;  padding-top:10px; ">Node Name <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="4,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black"></i></button></th>
          <th style="width:9%; text-align:center;   padding-top:10px; ">Site ID <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="5,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black"></i></button></th>
          <th style="width:12%; text-align:center;   padding-top:10px; ">Site Name <button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="6,string" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black"></i></button></th>
          <th style="width:10%; text-align:center;  padding-top:10px; "> Created Date<button style="font-size:10px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="7,date" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black"></i></button></th>
          <th style="width:20%; text-align:center;   padding-top:10px; "> Last Modified Date<button style="font-size:8px; float:right; border:none"  data-target="#openModal" data-toggle="modal"  data-whatever="8,date" onmouseover="changecolor(this)"  onmouseout="returncolor(this) "> <i class="fa fa-list" aria-hidden="true" style="font-size:20px;color:black"></i></button></th>
          
        </tr>
        <tr id="search_by_column">
            <th><input type="text" style="width:auto; text-align:center" id="cell_id_filter" placeholder="Search for Cell Ids .." onkeyup="filterData(this,0,'gridTable');"></th>
            <th><input type="text"style="width:auto; text-align:center" id="cell_name_filter"  placeholder="Search for Cell Names.." onkeyup="filterData(this,1,'gridTable');"></th>
            <th><input type="text"style="width:auto;  text-align:center"  id="node_id_filter" placeholder="Search for Nodes Ids.." onkeyup="filterData(this,2,'gridTable');"></th>
            <th><input type="text"style="width:auto;  text-align:center"  id="node_name_filter" placeholder="Search for Node Names .." onkeyup="filterData(this,3,'gridTable');"></th>
            <th><input type="text" style="width:auto; text-align:center" id="site_id_filter" placeholder="Search for Site Ids.." onkeyup="filterData(this,4,'gridTable');"></th>
            <th><input type="text"style="width:auto; text-align:center" id="site_name_filter"  placeholder="Search for Site Names.." onkeyup="filterData(this,5,'gridTable');"></th>
            <th><input type="text"style="width:auto;  text-align:center"  id="created_date_filter" placeholder="Search for Created Dates.." onkeyup="filterData(this,6,'gridTable');"></th>
            <th><input type="text"style="width:auto;  text-align:center"  id="last_modified_date_filter" placeholder="Search for Last Modified Dates.." onkeyup="filterData(this,7,'gridTable');"></th>
        </tr>
       
       
       
       
      </table>
			</div>
		</div>
		
		  <div class="modal fade" id="openModal" tabindex="-1" role="dialog" aria-labelledby="openModalLabel" aria-hidden="true">
  
   <div class="modal-dialog modal-sm">
   
      <div class="modal-content">
      
      	<div class="modal-header">
        				<h3 class="modal-title" id="openModalLabel">Filtering Data</h3>
        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
         	 			<span aria-hidden="true">&times;</span>
        				</button>
      				</div>
      
        <!-- Modal Header -->
        
        
        

        <!-- Modal body -->
        <div class="modal-body" style="margin-top: 10px; width:auto; height:auto;">
                 <ul  class="nav nav-pills">
                  <li ><a data-toggle="pill" href="#home"  title="Custom Filter"><div style="border:2px inset grey; width:auto; height:auto;" onmouseover="changecolor2(this)"  onmouseout="returncolor2(this)"><img src="${pageContext.request.contextPath}/resources/images/dropdown1.png" width="35" style=" margin-left: auto;margin-right: auto; display: block; padding-left:10px; padding-right:10px; padding-top:10px; padding-bottom:10px;" ></div></a></li>
                  
                  <li class="active" > <a data-toggle="pill" href="#menu1" title="Advanced Filter"><div style="border:2px inset grey; width:auto; height:auto;"onmouseover="changecolor2(this)"  onmouseout="returncolor2(this)"><img src="${pageContext.request.contextPath}/resources/images/filter.png" width="35"style=" margin-left: auto;margin-right: auto; display: block; padding-left:10px; padding-right:10px; padding-top:10px; padding-bottom:10px;"></div></a></li>
                </ul>
                
                <div class="tab-content">
                    <div id="home" class="tab-pane fade">
                        <input type="text" id="columnIndex" value="" hidden>
                        <input type="text" id="dataType" value="" hidden>
                    <table id="tbl_check" class='filter_tbl'>

                    </table>
                  </div>
                  <div id="menu1" class="tab-pane fade in active">
                    <div>
                    <table class="filter_tbl">
                        <tr>
                            <td>
                                Condition: <select id="drop1">
                                    
                                </select>
                            </td>
                        </tr>
                        <tr id="hid_filter1">
                            <td>

                            </td> 
                        </tr>
                        <tr>
                            <td style="text-align: center;">
                                <input type="radio" name="AndOrRadio" value="and"> And
                                <input type="radio" name="AndOrRadio" value="or"> OR
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <select id="drop2">
                                    <option>begins with</option>
                                    <option>in between</option>
                                    <option>contains</option>
                                    <option>empty</option>
                                    <option>ends with</option>
                                    <option>equals</option>
                                    <option>greater than</option>
                                    <option>less than</option>
                                </select>
                            </td>
                        </tr>

                        <tr id="hid_filter2">
                            <td>

                            </td>
                        </tr>
                    </table>
                    </div>
                  </div>
                </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="location.reload(true);">Clear</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="getRows()">Search</button>
        </div>
        
      </div>
    </div>
  </div>
  
    
  
  <hr>
  <div class="row" >
  <footer >
  <div id="pagination" class="pagination" style=" margin-left:800px; ">
                <div class="col_md_2">
                <p class="pagination-label"style="font-size:18px; color:black;  display: block; " >
                    Viewing <span style="font-size:18px; color:black;  display: inline;">1-10</span> of <span style="font-size:18px; color:black; display: inline; ">36</span>
                </p>
                </div>
             
              <a href="#" class="previous"  style="font-size:18px; color:black; text-decoration: none;    " onmouseover="changecolor3(this)" onmouseout="returncolor3(this)" >Previous</a>
                <a href="#" class="next round"  style="font-size:18px; color:black; text-decoration: none;  float:right;"onmouseover="changecolor3(this)" onmouseout="returncolor3(this)">Next</a>
                </div>  
           
</footer>
</div>
		
		
		
		
	</div>


<script> 
$(document).ready(function() {


	  var pagination = new Pagination({id:'pagination', tableID:'gridTable', noOfRows:10});


	
	/*var delList = ${delList}; var writeForm = ${writeForm}; var addForm = ${addForm};
	console.log("delList is " +delList+" writeForm is " +writeForm+" addForm is " +addForm);
	var deleteButton = '<button type="button" id="deleteButton" class="btn btn-primary BtnActive"><i class="fa fa-trash"></i> Delete</button>';
	var addButton = '<button type="button" id="saveButton" onclick=\'window.location.href = "${pageContext.request.contextPath}/PurchaseReqFormView?type=addNew"\'class="btn btn-primary BtnActive"><i class="fa fa-plus"></i> Add</button>';
	
	if(delList == 1){
		$("#Buttons").prepend(deleteButton);
	}
	if(addForm == 1){
		$("#Buttons").append(addButton);
	}
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
           		var value = "";
           		if( writeForm == 1){
           			value = '<a href="'+ '${pageContext.request.contextPath}/PurchaseReqFormView?ID='+full[1] +'&supplier='+full[2] +'">' + url + '</a>';
           		}
           		else if( writeForm == 0){
           			value = url;
           		}
            	return value;
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
            if (full[3] == "Transport2") {return    '<a href="'+ '/Inventory' +'">' + url + '</a>';}
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
			url : "${pageContext.request.contextPath}/PurchaseReqListViewDelete",
			dataType : "json",
			data : {
				"ID" : slct
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
*/
});


</script>


           
 </body>
 </html>
