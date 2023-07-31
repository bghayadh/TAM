<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title></title>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css"/>



		<style>
			    table{
			        width: 100%;
			        margin: 10px 0;
			        border-collapse: collapse;
			    }
			    table, th, td{
			        border: 1px solid #cdcdcd;
			    }
			    table th, table td{
			        padding: 3px;
			        text-align: left;
			        
			    }
	       			.ui-autocomplete {
	            	max-height: 100px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: hidden; /* add padding to account for vertical scrollbar */
					padding-right: 100px;
	        		} 
		</style>


<script>
    $(document).ready(function(){
    
        // load data in table
    	itemArray = ${ListPRqItem};
    	var slctsave = [];
		var slctDel = [];
        var newslct =[];
        var ressplit;
        var itmcode;
        var itmname;
        var itmqty;
        var itmrate;
        var itmdisc;
        var itmnetrate;
        var itmtot;
        var itmtotAT;
        var itmnewarehouse;
        var itmnprid;
        
    	//console.log( " And the 0 0 item is " +itemArray[0][0] );
    	//console.log("The size is " +itemArray.length);
    	//console.log("Number of columns in array is " +count(itemArray[0]));
    
        // get all colmns count per row
   		function count(array){
                var c = 0;
                for(i in array) // in returns key, not object
                    if(array[i] != undefined)
                        c++;
                return c;
         }
      
        // Fill tables rows from DB
    	var len=itemArray.length;
    	var itemCodeId;   
    	var collen= count(itemArray[0]);
    	
	     for (i = 0; i < len; i++) {
	        var itemRow = "<tr>";
	        itemRow= itemRow + "<td><input type='checkbox' name='record'></td>"
	        //console.log(itemRow);
	        for (j = 0; j < collen; j++) {
	        	var tt1= itemArray[i][j];
	        	if (j == 0) {
	     			itemRow =itemRow + "<td><input type='text' name='itmCode' value='" + tt1 +"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all'/></td>";
	     		}
	     		else
	     		{
					itemRow =itemRow + "<td><input type='text' value='" + tt1 +"'></td>";
	     		}
	    		
	        }
	        itemRow =itemRow + "</tr>";
	        //console.log(itemRow);
	        $("#bisotab > tbody").append(itemRow);
	     }       
            
     
		// ADD NEW ROW
        $(".add-row").click(function(){
            var name = $("#name").val();
            var email = $("#email").val();
           // var markup = "<tr><td><input type='checkbox' name='record'></td><td>" + name + "</td><td>" + email + "</td></tr>";
			var markup = "<tr><td><input type='checkbox' name='record'></td><td><input type='text' name='itmCode' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all'/></td><td><input type='text' name='itmqty'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td><td><input type='text'></td></tr>";
            $("#bisotab > tbody").append(markup);
              
            function customRenderItem(ul, item) {
							var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
								result = $('<li class="ui-menu-item" role="menuitem"></li>')
								.data('item.autocomplete', item)
								.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
								.appendTo(ul);
				
							return result;
						}
				
				       	var columns = [{name: 'Item Code', minWidth: '150px'}, {name: 'Item Name', minWidth:'100px'}];	
								
						$('input[name ="itmCode"]').mcautocomplete({
						showHeader: true,
						columns: columns,
				        source: function(request, response, event, ui) {
				       // console.log("the value is " +request.term);
				        		
					             $.ajax({
					                 type: "GET",
					                 contentType: "application/json; charset=utf-8",
					                 url: '${pageContext.request.contextPath}/GetAllitemPrREQ',
					                 data: {
											"itemCode" : request.term,
									 },
					                 dataType: "json",
					                 success: function (data) {
					                     if (data != null) {
					                         response(data.ListItemprreq);
					                     }
					                 },
					                 error: function(result) {
					                     alert("Error");
					                 }
					             });
					         }, minLength:0, maxShowItems: 4, scroll:true,		
				               
				        
						select: function(event, ui) {
								this.value = (ui.item ? ui.item[0] + ":" + ui.item[1]  : '');
								return false;
							}
				    }).bind('focus', function(event, ui) {       
				      if(!$(this).val().trim())
				            $(this).keydown();
							
					});	 	
            
     
        });
        //end new add row
        
        
        // Find and remove selected table rows
        $(".delete-row").click(function(){
            getselectedrows ();
            var supprimerrow ='';
            	for (i = 0; i < slctDel.length; ++i) {
			            //delete from screen
			            // check if you select rows to save or update
			           if (slctDel.length == 0) {
			            	alert(' Select Row(s) to Delete');
			          		return false;
			          }
			            //console.log('slctDel is :'+ slctDel[0]);
			            //console.log('slctDel is :'+ slctDel[1]);
			            $("#bisotab > tbody").find('input[name="record"]').each(function(){
			                if($(this).is(":checked")){
			                    $(this).parents("tr").remove();
			                }
			            });
			            supprimerrow=slctDel[i];
			            //delete from database
			            
					$.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/PurchaseReqItemFormDelete",
						dataType : "json",
						data : {
							"prqItemId" : supprimerrow
						},
						success : function(data) {
							//console.log("The returned data is " +data.BassamTest);
						},
						error : function(error) {
							console.log("The error is " + error);
						}
					});
				}
			            
        });
		// end delete row
		
		//function to get selected rows for save or delete
		function getselectedrows () {
				slctsave = [];
				slctDel = [];
				obj='';
				$("#bisotab > tbody").find('input[name="record"]').each(function(){
				if($(this).is(":checked")){
				    slctDel.push($(this).parent().parent().children(':nth-child(10)').children('input').val());
				    
                    var obj = $(this).parent().parent().children(':nth-child(2)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(3)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(4)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(5)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(6)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(7)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(8)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(9)').children('input').val();
                    obj=obj+";" +$(this).parent().parent().children(':nth-child(10)').children('input').val();
					slctsave.push(obj);
                } 
				else {
				        //var removeitem =$(this).parent().parent().children(':nth-child(10)').v();
						slctDel = jQuery.grep(slctDel, function(value) {
        				return value != $(this).parent().parent().children(':nth-child(10) input').val();
      					});
				  }
				  
            	});
				console.log('selected Save is:' +slctsave);
				console.log('selected Del is:' +slctDel);

		}
        // end select row
        
        // save Data in DB
        $("#btnSave").click (function (){
           getselectedrows ();
           // check if you select rows to save or update
           if (slctsave.length == 0) {
            	alert(' Select Row(s) to Save');
          		return false;
          }
       
           itmcode='';
           itmname='';
           itmqty='';
           itmrate='';
           itmdisc='';
           itmnetrate='';
           itmtot='';
           itmtotAT='';
           itmnewarehouse='';
           itmnprid='';
           
            console.log('slctsave is :'+ slctsave[0]);
           //console.log('slctsave is :'+ slctsave[1]);
           			for (i = 0; i < slctsave.length; ++i) {
			                 newslct =slctsave[i];
			         	
			                 splitrow(newslct); 
			                // itmcode=ressplit;
			                // console.log(itmcode);
			                 
			                 var itemnbr = ressplit;
	 						 var n = itemnbr.indexOf(":");
     						 var itmcode = itemnbr.substring(0, n);
     						 var itmname = itemnbr.substring(n+1, itemnbr.length);
     						 console.log(itmcode);
     						 console.log(itmname);
			                 
			                 
			                 
			                 splitrow(newslct); 
			                 itmqty=ressplit;
			                 console.log(itmqty);
			    		     
			                 splitrow(newslct); 
			                 itmrate=ressplit;
			                 console.log(itmrate);
			                 
			                 splitrow(newslct); 
			                 itmdisc=ressplit;
			                 console.log(itmdisc);
			            
			                 splitrow(newslct); 
			                 itmnetrate=ressplit;
			                 console.log(itmnetrate);
			            
			                 splitrow(newslct); 
			                 itmtot=ressplit;
			                 console.log(itmtot);
			            
			                 splitrow(newslct); 
			                 itmtotAT=ressplit;
			                 console.log(itmtotAT);
			                 
			                 splitrow(newslct); 
			                 itmnewarehouse=ressplit;
			                 console.log(itmnewarehouse);
			                 
			                 itmnprid=newslct;
			                 console.log(itmnprid);
			            
			                 $.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}/PurchaseReqItemFormSave",
						dataType : "json",
						data : {
							"prqItemId" : itmnprid,
							"CreationDate" : null,
							"ModifiedDate" : null,
							"ItemCode" : itmcode,
							"ItemName" : itmname,
							"Rate" : itmrate,
							"DiscAmnt" : itmdisc,
							"DiscPercent" : 0,  
							"NetRate" : itmnetrate,
							"Tax1" : 0,
							"Tax2" : 0,
							"Total" : itmtot,
							"TotalAt" : itmtotAT,
							"prqiWarehouse" : itmnewarehouse,
							"PqNo" : '1',
							"prqiPrNo" : '1',
							"ArNo" : '1' ,
							"FarNo" : '1',
							"Qty" :itmqty,
							"PRQId" : 'r1'
						},
						success : function(data) {
							console.log("The returned data is " +data.BassamTest);
						},
						error : function(error) {
							console.log("The error is " + error);
						}
					});
           		}
           //console.log('Save Done');
         });
        //end save data
        
        function splitrow (tab1) {
         	newslct=[];
            var valData= tab1;
           	var n = valData.indexOf(";");
	            var res= valData.substring(0, n);
	            ressplit=res;
	     		//console.log(res );
	            newslct= valData.substring(n+1, tab1.length);
	          	//console.log(newslct );
          }
             
        
        // Get select rows  to run multiple select or unselect
		$("#btngetrow").click (function (){
			getselectedrows ();	
			
        });
        
        
       
         	            			
            // select cell per row and col
			$('#bisotab tr td input').change(function() {
					var row_index = $(this).parent().parent().index();
					var column_index = $(this).parent().index();
					var cell = $(this).val();
				
					if ((column_index == 2) ) {
                          // validate Qty
							 if (($. isNumeric(cell ))== false) {
							 alert('Qty is  not Numeric');
							 this.focus();
							 return false;}
                    }
                      if ((column_index == 3) ) {
                          // validate Rate
							 if (($. isNumeric(cell ))== false) {
							 alert('Rate is  not Numeric');
							 this.focus();
							 return false;}
                    }
                      if ((column_index == 4) ) {
                          // validate Discount
							 if (($. isNumeric(cell ))== false) {
							 alert('Discount is  not Numeric');
							 this.focus();
							 return false;}
                    }
                    if ((column_index == 5) ) {
                          // validate Net Rate
							 if (($. isNumeric(cell ))== false) {
							 alert('Net Rate is  not Numeric');
							 this.focus();
							 return false;}
                    }
                    if ((column_index == 6) ) {
                          // validate Total
							 if (($. isNumeric(cell ))== false) {
							 alert('Total is  not Numeric');
							 this.focus();
							 return false;}
                    }
                    if ((column_index == 7) ) {
                          // validate Total AT
							 if (($. isNumeric(cell ))== false) {
							 alert('Total AT is  not Numeric');
							 this.focus();
							 return false;}
                    }
                 
					console.log("the cell is " +cell);
					console.log("the current row index is " +row_index);
					console.log("the column index is " +column_index);
   
    			});
    			// end select cell per row and col
    			
    			
    			 // autocomplete run to get itemcode
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
				       	var columns = [{name: 'Item Code', minWidth: '150px'}, {name: 'Item Name', minWidth:'100px'}];	
								
						$('input[name ="itmCode"]').mcautocomplete({
						showHeader: true,
						columns: columns,
				        source: function(request, response, event, ui) {
				       // console.log("the value is " +request.term);
				        		
					             $.ajax({
					                 type: "GET",
					                 contentType: "application/json; charset=utf-8",
					                 url: '${pageContext.request.contextPath}/GetAllitemPrREQ',
					                 data: {
											"itemCode" : request.term,
									 },
					                 dataType: "json",
					                 success: function (data) {
					                     if (data != null) {
					                         response(data.ListItemprreq);
					                     }
					                 },
					                 error: function(result) {
					                     alert("Error");
					                 }
					             });
					         }, minLength:0, maxShowItems: 4, scroll:true,		
				               
				        
						select: function(event, ui) {
								this.value = (ui.item ? ui.item[0] + ":" + ui.item[1]  : '');
								return false;
							}
				    }).bind('focus', function(event, ui) {       
				      if(!$(this).val().trim())
				            $(this).keydown();
							
					});	 
					
					
					//to select all from top table
						$('body').on('click', '#selectAll', function  () {
					   
      					if ($(this).hasClass('allChecked')) {
         				$('input[type="checkbox"]', '#bisotab').prop('checked', false);
      						} else {
       						$('input[type="checkbox"]', '#bisotab').prop('checked', true);
       						}
       						$(this).toggleClass('allChecked');
       				
     					})
					
    			
    			
    			function selectALLrows () {
	    			if ($(this).hasClass('allChecked')) {
	         				$('input[type="checkbox"]', '#bisotab').prop('checked', false);
	      						} else {
	       						$('input[type="checkbox"]', '#bisotab').prop('checked', true);
	       						}
	       						$(this).toggleClass('allChecked');
							}
    			
    			 
    			 // Select All rows  to run multiple select or unselect
					$("#btnchk").click (function (){
					    selectALLrows();
						});	
			
        		
    			

    }); 
    //end ready document
    
   
</script>
</head>
<body>
<style>
table {
    width: 100%;
    border-spacing: 0;
}

thead, tbody, tr, th, td { display: block; }

thead tr {
    /* fallback */
    width: 97%;
    /* minus scroll bar width */
    width: -webkit-calc(100% - 16px);
    width:    -moz-calc(100% - 16px);
    width:         calc(100% - 16px);
}

tr:after {  /* clearing float */
    content: ' ';
    display: block;
    visibility: hidden;
    clear: both;
}

tbody {
    height: 200px;
    overflow-y: auto;
    overflow-x: both;
}

tbody td, thead th {
    width: 10%;  /* 19% is less than (100% / 5 cols) = 20% */
    float: left;
}
 </style>

<div class="container-fluid">
  <div class="row">
   <div class="table-responsive-sm  table-wrapper-scroll-y my-custom-scrollbar"> 
    <table id ="bisotab" class="table table-striped table-bordered table-sm">
                                
        <thead>
            <tr>
                <th>
          <button type="button" id="selectAll" class="main">
          <span class="sub"></span>Select</button></th>
                <th>Item_Code</th>
                <th>Qty</th>
                <th>Rate</th>
                <th>Discount</th>
                <th>Net Rate</th>
                <th>Total</th>
                <th>Total AT</th>
                <th>Warehouse</th>
                <th>PrReqitm</th>
            </tr>
        </thead>
        <tbody>
            


			
        </tbody>
    </table>
    </div>
	<input type="button" class="add-row" value="Add Row">
    <button type="button" class="delete-row">Delete Row</button>
	<button type="button" id="btngetrow" >Get Row</button>
	<button type="button" id="btnSave" class="Save" >Save</button>
	<button type="button" id="btnchk" class="Save" >check all</button>
 	</div>
  </div>
	
</body> 
</html>