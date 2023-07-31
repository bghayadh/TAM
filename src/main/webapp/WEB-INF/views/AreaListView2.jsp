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
	<!-- for next and prv -->
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
            

</head>
<body>
<%--  <c:set var = "page" value = "Sales"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="Sales" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
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
			 				data-placement="top" title="Form View"  onclick='window.location.href = "${pageContext.request.contextPath}/AreaFormView"'> <i class="fa fa-edit"></i>
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
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;"> AREA LIST</li>
	 
		            <li class="nav-item ml-auto">
						<button type="button" id="deleteButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-trash"></i> Delete
						</button>  
		                        
						<button type="button" id="saveButton" onclick='window.location.href = "${pageContext.request.contextPath}/AreaFormView?type=addNew"'
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
    							<th>Area ID</th>
    							<th>Area Name</th>
    							<th>Creation Date</th>
    							<th>Last Modified Date</th>
    							
    							
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
	var yaraData = ${ListGridTable};
	console.log(yaraData);
$(document).ready(function() {
	
	
	var code='';
	var slct = [];
	//console.log("bassamData is " +bassamData + " first item code is " +bassamData[0]);
	var table = $('#example').DataTable( {
	"bProcessing": true,
    "aaData": yaraData,// <-- your array of objects
    "aoColumns":[
    	{
        	"mData": null,
          	"bSortable": false,
	        "mRender" : function ( yaraData, type, full ) { 
	            	return ''}    		
    	}, 
        
        {
            "aTargets":[ 1 ],
            "sType": "String",
            "mRender": function(url, type, full) {
           //console.log('chk data : '+full[3]);
           // how to pass parameter to ItemFormView
            	return    '<a href="#" onclick="navigate(\'' + full[1] + '\',\'' + full[2] + '\');">'+ url +'</a>';
				}
   
       },
      {
          "mData":[2],
         },
       {
           "mData":[3],
          },
          {
              "mData":[4],
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
      'order': [[4, 'desc']],
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
			url : "${pageContext.request.contextPath}/AreaListViewDelete",
			dataType : "json",
			data : {
				"AreaId" : slct
			},
			success : function(data) {
				//console.log("The returned data is " +data.YaraTest);
				location.reload();
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		console.log('slct is: ' + slct);
	 
 	});


 /*	$('.ahr').click(function(){
	    alert('perform action here');
	});*/

	
});
var param1,param2;
var setArray = "setArray";
function navigate(name,name2){
	param1=name;
	param2=name2;
   if( yaraData != null){
   	var size = new TextEncoder().encode(JSON.stringify(yaraData)).length
   	var kiloBytes = size / 1024;
   	var megaBytes = kiloBytes / 1024;
   	var browser = platform.name;
	if(browser == "Samsung Internet"){
		if(megaBytes > 2){
			setupStorage("Samsung Internet");
			}else{
				setupStorage(setArray);
				}
		}else if(browser.includes("Safari")){
			if(megaBytes > 5){
				setupStorage("Safari");
				}else{
					setupStorage(setArray);
					}
		}else {
			console.log("browser");
			if(megaBytes > 10){
				setupStorage("browser");
				}else{
					console.log("set array normal");
					setupStorage(setArray);
					}
			}
       }

	}
var navigateArray = [];
function setupStorage(browser){
	for(var i =0;i<yaraData.length;i++){
		navigateArray.push(yaraData[i][1]);
		}
	console.log(navigateArray);
	if(browser == setArray){
			localStorage.setItem("areaList", JSON.stringify(navigateArray));
			var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+param1;
			window.location.href =param;
		}else if(browser == "Samsung Internet"){

		var size = new TextEncoder().encode(JSON.stringify(navigateArray)).length
	   	var kiloBytes = size / 1024;
	   	var megaBytes = kiloBytes / 1024;
   	
	   	if(megaBytes > 2){
	   		var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+param1+"&areaList=areaList";
			window.location.href =param;
			
	    	}else{
		    	localStorage.setItem("areaList", JSON.stringify(navigateArray));
		    	}
	
	}else if(browser.includes("Safari")){
		var size = new TextEncoder().encode(JSON.stringify(navigateArray)).length
    	var kiloBytes = size / 1024;
    	var megaBytes = kiloBytes / 1024;
    	if(megaBytes > 5){

    		var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+param1+"&areaList=areaList";
			window.location.href =param;
	    	}else{
		    	localStorage.setItem("areaList", JSON.stringify(navigateArray));
		    	}
		}else if(browser.includes("browser")){
			var size = new TextEncoder().encode(JSON.stringify(navigateArray)).length
	    	var kiloBytes = size / 1024;
	    	var megaBytes = kiloBytes / 1024;
	    	if(megaBytes > 10){
	    		var param ="${pageContext.request.contextPath}/AreaFormView?AreaID="+param1+"&areaList=areaList";
				window.location.href =param;
		    	}else{
			    	localStorage.setItem("areaList", JSON.stringify(navigateArray));
			    	}
			}
}
</script>

	

</body>
</html>