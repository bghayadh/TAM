/**
 * 
 */
  
 var rowindx =0;
 var rowcountSerial =0;
 var serialRowIndex=0;
 var tablesgn='';
 var slctss=[];
 var tabletitle;
 var rowcountWOO;
 var sumQtyWos=0;
 var sumQtyWod=0;

// fct get path
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	 
function f1(rowcountWO,tableID)
 {
 	newArray = [];
 	$("input[name=itmCodeSrc]").each(function(){
 	    newArray.push($('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmCode"]').val());
 	});
 	return newArray;
        
 }
	

	
// fct get total qty for items in boq table
function getTotalAT_SumQty(){
	sumQtyWos=0;
  	sumQtyWod=0;
 $("#bisotab > tbody > tr").each(function(i, row){
 		sumQtyWos = sumQtyWos + parseFloat($(this).children('td[name="qty"]').children('input').val());
		console.log("sumQty is:"+sumQtyWos);
   }); 
$("#bisotab2 > tbody > tr").each(function(i, row){
 		sumQtyWod = sumQtyWod + parseFloat($(this).children('td[name="qty"]').children('input').val());
		console.log("sumQty is:"+sumQtyWod);
   }); 

$('#wostotqty').val(sumQtyWos);	
$('#wodtotqty').val(sumQtyWod);	     	            	
     	            	
 }


function SetIndexRow(obj)
		{
			console.log("passed the function of index of source" );
			var row_index = $(obj).parent().parent().index();
			document.getElementById('RowIndex').value = row_index;
			console.log(row_index);
		}
		
function SetIndexRowDest(obj)
		{
			console.log("passed the function of index of Dest");
			var row_index = $(obj).parent().parent().index();
			document.getElementById('RowIndexDest').value = row_index;
			console.log(row_index);
		}
		
		
		function loadData(boqArray,boqArray2){
        		
        // Fill tables rows from DB
   		for (i = 0;i<boqArray.length;i++){

   	   		var itemCode = boqArray[i].itemCode;
   	   		console.log("The itemCode is "+itemCode)
   	   		if(itemCode == null)
   	   			itemCode = "";
   	   		else
   	   			itemCode = boqArray[i].itemCode +":"+ boqArray[i].itemName;

   	   		var cc = boqArray[i].currentQty;
   	   		console.log("The CURRENT QTYYYYY is "+cc)
   	   		if(cc == null)
   	   			cc = "";
   	   		else
   	   			cc = boqArray[i].currentQty;

   	   			
   	   		var itemModel = boqArray[i].itemModel;
   	   	console.log("the item model is "+itemModel);
   	   		if(itemModel == null)
   	   			itemModel = "";
   	   		else
   	   			itemModel = boqArray[i].itemModel;

   	   		var itemPartNumber = boqArray[i].itemPartNumber;
   	   	console.log("the item part number is "+itemPartNumber);
	   		if(itemPartNumber == null)
	   			itemPartNumber = "";
	   		else
	   			itemPartNumber = boqArray[i].itemPartNumber;
	   		
   	   		var nodeId = boqArray[i].nodeId;
   	   	console.log("the node id is "+nodeId);
	   		if(nodeId == null)
	   			nodeId = "";
	   		else
	   			nodeId = boqArray[i].nodeId;

	   		var nodeName = boqArray[i].nodeName;
	   		console.log("the node name is "+nodeName);
	   		if(nodeName == null)
	   			nodeName = "";
	   		else
	   			nodeName = boqArray[i].nodeName;
	   		   	   			
         	var fromsite=boqArray[i].fromWare;
         	console.log("the from site is "+fromsite);
         	if(fromsite == null)
        		fromsite = "";
  	   		else
  	   			fromsite = boqArray[i].fromWare ;

      	    var tosite =boqArray[i].toWare;
        	   console.log("the to site is "+tosite);
           	if(tosite == null)
        	   tosite = "";
  	   		else
  	   			tosite = boqArray[i].toWare;

           	var gr=boqArray[i].grId;
           	console.log("the grid is "+gr);
            if(gr == null)
         	   gr = "";
   	   		else
 	  	   		gr = boqArray[i].grId;
	  	   		
           var pr=boqArray[i].prId;
           console.log("the prid is "+pr);
           if(pr == null)
        	   pr = "";
  	   		else
	  	   		pr = boqArray[i].prId;

           var po=boqArray[i].poId;
           console.log("the poid is "+po);
           if(po == null)
        	   po = "";
  	   		else
  	   			po = boqArray[i].poId;

           var cip=boqArray[i].cipId;
           console.log("the cipid is "+cip);
           if(cip == null)
        	   cip = "";
  	   		else
  	   			cip = boqArray[i].cipId;
	   			

           var check=boqArray[i].reconciled;
           var checkbox;
           var spandot;
           console.log("The check is "+check);
        	 if(check=='1'){
         		checkbox="<input type='checkbox' name='reconciled' style='height:20px; width:20px;'  checked='true'>";
         		
         		 spandot = "<span class='dotStatus' id='dotStatus' style='position:relative;left:3px;background-color:chartreuse;'></span>";             }

         	else{
        		 checkbox="<input type='checkbox' name='reconciled' style='height:20px; width:20px;'>";
        		 spandot = "<span class='dotStatus' id='dotStatus' style='position:relative;left:3px;background-color:orange;'></span>";             }


         var barco=boqArray[i].barcodeScanned;
         var checkbox1;
         console.log("The check is "+barco);
        	if(barco=='1'){
        	checkbox1="<input type='checkbox' name='barcodescanned' style='height:20px; width:20px;' checked='true'> ";
            }
       		 else{
        	checkbox1="<input type='checkbox' name='barcodescanned' style='height:20px; width:20px;'> ";
            }

        	  var sn=boqArray[i].serialNo;
              console.log("the serialNo is "+sn);
              if(sn == null)
           	   sn = "";
     	   		else
   	  	   		sn = boqArray[i].serialNo;
 	  	   		           
	    	
	 	  var itemRow = "<tr><td><input type='checkbox' name='record' style='width: 14px;height: 13px;position:relative;left:6px;'>" +spandot+ "<button type='button' href='#' name='popUpMenu' onclick='openPop1(this)' class='btn btn-default'  style='margin-bottom:3px;'><i class='fas fa-desktop'></i></button></td>";
	 	  itemRow = itemRow + "<td name ='itemCode'><input type='text' name='itmCode' value='" +itemCode+ "'  style='width:400px;' class='form-control ui-widget ui-widget-content ui-corner-all'/></td>";
	 	  itemRow = itemRow + "<td name ='itemModel'><input type='text' name='itmmodel' value='" +itemModel+ "' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
	 	  itemRow = itemRow + "<td name ='itemPartNumber'><input type='text' name='itmpartnumber' value='" +itemPartNumber+ "' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
	 	  itemRow = itemRow + "<td name ='currentQty'><input type='text' name='currentqty' value='" +boqArray[i].currentQty+ "' style='width:200px;' class='form-control' value= 0></td>";
	 	  itemRow = itemRow + "<td name ='qty'><input type='number' name='qty' value='" +boqArray[i].qty+ "' style='width:200px;' class='form-control' value= 0></td>";
	 	  itemRow = itemRow + "<td name ='itemFromWare'><input type='text' name='itmFWH' value='"+fromsite+"' style='width:200px;' class='form-control' ></td>";
	 	  itemRow = itemRow + "<td name ='itemToWare'><input type='text' name='itmTWH' value='"+tosite+"' style='width:200px;' class='form-control' ></td>";
	 	  itemRow = itemRow + "<td name ='nodeId'><input type='text' name='nodeid' style='width:200px;' class='form-control' value='" +nodeId+ "'></td>";
	 	  itemRow = itemRow + "<td name ='nodeName'><input type='text' name='nodename' value='" +nodeName+ "' style='width:200px;' class='form-control' ></td>";
	 	  itemRow = itemRow + "<td name ='grId'><input type='text' ondrop ='return false;' name='itmGR' value='" +gr+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	 	  itemRow = itemRow + "<td name ='prId'><input type='text' ondrop ='return false;' name='itmPR' value='" +pr+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	 	  itemRow = itemRow + "<td name ='poId'><input type='text' ondrop ='return false;' name='itmPO' value='" +po+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	 	  itemRow = itemRow + "<td name ='cipId'><input type='text' ondrop ='return false;' name='itmCIP' value='" +cip+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	 	  itemRow = itemRow + "<td name ='status'><input type='text value='" + boqArray[i].status +"' name='status' class='form-control' style='width:200px;' readonly></td>";
	 	  itemRow = itemRow + "<td name='reconciled' style='text-align:center; vertical-align: middle;'>"+checkbox+ "</td>";
	 	  itemRow = itemRow + "<td name='barcodescanned' style='text-align:center; vertical-align: middle;'>"+checkbox1+"</td>";
	 	  itemRow = itemRow + "<td name ='wosId'><input type='text' name='wosid' class='form-control' style='width:200px;' value='" + boqArray[i].id +"' readonly></td>";
		itemRow =itemRow + "<td name='serialNo'><input type='text'  style='width:200px;' value='" + sn +"'hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>";		
	 		
	     
	        $("#bisotab > tbody").append(itemRow);
	        rowcountWO =  $("#bisotab >tbody tr").length;
      		console.log("rowcount Source:" +rowcountWO);
	      	 autoComplete(rowcountWO,"bisotab");
	      	 sumQtyWos=sumQtyWos+parseFloat(boqArray[i].qty);
	     			console.log("for loop sum"+sumQtyWos);
	   }
 	   
   		$('input[name="qty"]').on('input',function (){
			console.log("Get Quantity");
			sumQtyWos=0;
			 $("#bisotab > tbody > tr").each(function(i, row){
		 
		 		sumQtyWos = sumQtyWos + parseFloat($(this).children('td[name="qty"]').children('input').val());
				console.log("The TOTAL sumQty is:"+sumQtyWos);
			
		   }); 
			 $('#wostotqty').val(sumQtyWos);	
		});


   		for (i = 0;i<boqArray2.length;i++){

   	   		var itemCode = boqArray2[i].itemCode;
  	         console.log("the ITEMCODE is "+itemCode);
    	   		
   	   		if(itemCode == null)
   	   			itemCode = "";
   	   		else
   	   			itemCode = boqArray2[i].itemCode +":"+ boqArray2[i].itemName;

   	   		var itemModel = boqArray2[i].itemModel;
   	   		if(itemModel == null)
   	   		itemModel = "";
   	   		else
   	   		itemModel = boqArray2[i].itemModel;

   	   		var itemPartNumber = boqArray2[i].itemPartNumber;
   			if(itemPartNumber == null)
   				itemPartNumber = "";
   			else
   				itemPartNumber = boqArray2[i].itemPartNumber;
   		
   	   		var nodeId = boqArray2[i].nodeId;
   			if(nodeId == null)
   				nodeId = "";
   			else
   				nodeId = boqArray2[i].nodeId;


   			var nodeName = boqArray2[i].nodeName;
   			if(nodeName == null)
   				nodeName = "";
   			else
   				nodeName = boqArray2[i].nodeName;
   			   			
   	         var fromsite=boqArray2[i].fromWare;
   	         console.log("the from site is "+fromsite);
   	         if(fromsite == null)
   	         	fromsite = "";
   	  	   	 else
   	  	   	 	fromsite = boqArray2[i].fromWare ;

   	         var tosite=boqArray2[i].toWare;
   	         console.log("the from site is "+tosite);
   	         if(tosite == null)
   	      	 	tosite = "";
   	  	  	 else
   	  	   		tosite = boqArray2[i].toWare ;

   	     	 var gr=boqArray2[i].grId;
	           console.log("the from site is "+gr);
	           if(gr == null)
	        	   gr = "";
	  	   		else
	  	   			gr = boqArray2[i].grId; 
 	   			
   	           var pr=boqArray2[i].prId;
   	           console.log("the from site is "+pr);
   	           if(pr == null)
   	        	   pr = "";
   	  	   		else
   	  	   			pr = boqArray2[i].prId;

   	           var po=boqArray2[i].poId;
   	           console.log("the from site is "+po);
   	           if(po == null)
   	        	   po = "";
   	  	   		else
   	  	   			po = boqArray2[i].poId;

   	           var cip=boqArray2[i].cipId;
   	           console.log("the from site is "+cip);
   	           if(cip == null)
   	        	   cip = "";
   	  	   		else
   	  	   		cip = boqArray2[i].cipId;


   	        var check1=boqArray2[i].reconciled;
            var checkbox2;
            var spandot;
            console.log("The check is "+check1);
           	if(check1=='1'){
           		checkbox2="<input type='checkbox' name='reconciled' style='height:20px; width:20px;'  checked='true'>";
       		 spandot = "<span class='dotStatus' id='dotStatus' style='position:relative;left:3px;background-color:chartreuse;'></span>";             }

           	else{
          	 	checkbox2="<input type='checkbox' name='reconciled' style='height:20px; width:20px;'>";
       		 spandot = "<span class='dotStatus' id='dotStatus' style='position:relative;left:3px;background-color:orange;'></span>";             }



           var barco1=boqArray2[i].barcodeScanned;
           var checkbox3;
           console.log("The check is "+barco1);
         	 if(barco1=='1'){
          		checkbox3="<input type='checkbox' name='barcodescanned' style='height:20px; width:20px;' checked='true'> ";
              }
          	else{
          		checkbox3="<input type='checkbox' name='barcodescanned' style='height:20px; width:20px;'> ";
              }
         	 var sn=boqArray2[i].serialNo;
             console.log("the serialNo is "+sn);
             if(sn == null)
          	   sn = "";
    	   		else
  	  	   		sn = boqArray2[i].serialNo;

                       	
   	 	  var itemRow = "<tr><td><input type='checkbox' name='record' style='width: 14px;height: 13px;position:relative;left:6px;'>"+spandot+"<button type='button' href='#' name='popUpMenu' onclick='openPop2(this)' class='btn btn-default'  style='margin-bottom:3px;'><i class='fas fa-desktop'></i></button></td>";
	 	  itemRow = itemRow + "<td name ='itemCode'><input type='text' name='itmCode' value='" +itemCode+ "'  style='width:400px;' class='form-control ui-widget ui-widget-content ui-corner-all'/></td>";
	 	  itemRow = itemRow + "<td name ='itemModel'><input type='text' name='itmmodel' value='" +itemModel+ "' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
	 	  itemRow = itemRow + "<td name ='itemPartNumber'><input type='text' name='itmpartnumber' value='" +itemPartNumber+ "' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
   	 	  itemRow = itemRow + "<td name ='currentQty'><input type='text' name='currentqty' value='" +boqArray2[i].currentQty+ "' style='width:200px;' class='form-control' value= 0></td>";
   	 	  itemRow = itemRow + "<td name ='qty'><input type='number' name='qty' value='" +boqArray2[i].qty+ "' style='width:200px;' class='form-control' value= 0></td>";
   	 	  itemRow = itemRow + "<td name ='itemFromWare'><input type='text' name='itmFWH' value='"+fromsite+"' style='width:200px;' class='form-control' ></td>";
   	 	  itemRow = itemRow + "<td name ='itemToWare'><input type='text' name='itmTWH' value='"+tosite+"' style='width:200px;' class='form-control' ></td>";
   	 	  itemRow = itemRow + "<td name ='nodeId'><input type='text' name='nodeid' style='width:200px;' class='form-control' value='" +nodeId+ "'></td>";
   	 	  itemRow = itemRow + "<td name ='nodeName'><input type='text' name='nodename' value='" +nodeName+ "' style='width:200px;' class='form-control' ></td>";
   	 	  itemRow = itemRow + "<td name ='grId'><input type='text' ondrop ='return false;' name='itmGR' value='" +gr+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
   	 	  itemRow = itemRow + "<td name ='prId'><input type='text' ondrop ='return false;' name='itmPR' value='" +pr+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
   	 	  itemRow = itemRow + "<td name ='poId'><input type='text' ondrop ='return false;' name='itmPO' value='" +po+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
   	 	  itemRow = itemRow + "<td name ='cipId'><input type='text' ondrop ='return false;' name='itmCIP' value='" +cip+ "' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
   	 	  itemRow = itemRow + "<td name ='status'><input type='text value='" + boqArray2[i].status +"' name='status' class='form-control' style='width:200px;' readonly></td>";
   	 	  itemRow = itemRow + "<td name='reconciled' style='text-align:center; vertical-align: middle;'>"+checkbox2+ "</td>";
   	 	  itemRow = itemRow + "<td name='barcodescanned' style='text-align:center; vertical-align: middle;'>"+checkbox3+"</td>";
   	 	  itemRow = itemRow + "<td name ='wodId'><input type='text' name='wodid' class='form-control' style='width:200px;' value='" + boqArray2[i].id +"' readonly></td>";
		itemRow =itemRow + "<td name='serialNo'><input type='text'  style='width:200px;' value='" + boqArray2[i].serialNo +"'hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>";		
   	 		
   	     // append des boq table
	        $("#bisotab2 > tbody").append(itemRow);
	        rowcountWOD =  $("#bisotab2 >tbody tr").length;
     		console.log("rowcount Destination:" +rowcountWOD);
     		//autocomplete for des boq table
	       autoComplete(rowcountWOD,"bisotab2");

			// add qty of items 
	        sumQtyWod=sumQtyWod+parseFloat(boqArray2[i].qty);
 			console.log("for loop Destination sum: "+sumQtyWod);
	   }
 	   // event to calculate change in qty for each item
   		$('input[name="qty"]').on('input',function (){
			console.log("Get Quantity");
			sumQtyWod=0;
			 $("#bisotab2 > tbody > tr").each(function(i, row){
		 
		 		sumQtyWod = sumQtyWod + parseFloat($(this).children('td[name="qty"]').children('input').val());
				console.log("The TOTAL sumQty is:"+sumQtyWod);
			
		   }); 
			 $('#wodtotqty').val(sumQtyWod);	
		});
		}
		
		function moveData(slctmove){
			for (i = 0; i < slctmove.length; ++i) {
         //delete from screen
         // check if you select rows to save or update
         
         var check=slctmove[i].Reconciled;
         var barcode=slctmove[i].BarCodeScanned;

console.log("//*/*/* the check is"+check);

         var checkbox;
         var checkbox2;
          if(check =='1'){
        	checkbox=  "<input type='checkbox' name='reconciled' style='height:20px; width:20px;'  checked='true'>";
             

              }
          else{

        	  checkbox= "<input type='checkbox' name='reconciled' style='height:20px; width:20px;'  >";

              }

          if(barcode=='1'){
        	  checkbox2 = "<input type='checkbox' name='barcodescanned' style='height:20px; width:20px;' checked='true'> ";
              }


          else{

        	  checkbox2 = "<input type='checkbox' name='barcodescanned' style='height:20px; width:20px;' > ";

              }
      
          

		  var markup = "<tr><td><input type='checkbox' name='record' style='width: 14px;height: 13px;position:relative;left:6px;'><span class='dotStatus' id='dotStatus' style='position:relative;left:3px;'></span><button type='button' href='#' name='popUpMenu' onclick='openPop2(this)' class='btn btn-default'  style='margin-bottom:3px;'><i class='fas fa-desktop'></i></button></td>";
		   markup = markup + "<td name ='itemCode'><input type='text' name='itmCode'  style='width:400px;' value = '" + slctmove[i].ItemCode +"' class='form-control ui-widget ui-widget-content ui-corner-all'/></td>";
		   markup = markup + "<td name ='itemModel'><input type='text' name='itmmodel' value = '" + slctmove[i].ItemModel +"' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
		   markup = markup + "<td name ='itemPartNumber'><input type='text' name='itmpartnumber' value = '" + slctmove[i].ItemPartNumber +"' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
		   markup = markup + "<td name ='currentQty'><input type='text' name='currentqty'  value = '" + slctmove[i].CurrentQty +"' class='form-control' style='width:200px;' value= 0></td>";
		   markup = markup + "<td name ='qty'><input type='text' name='qty' style='width:200px;' value = '" + slctmove[i].Qty +"' class='form-control' value= 0></td></td>";
		   markup = markup + "<td name ='itemFromWare'><input type='text' name='itmFWH' value = '" + slctmove[i].FromWarehouse +"' style='width:200px;' class='form-control' ></td>";
		   markup = markup + "<td name ='itemToWare'><input type='text' name='itmTWH' value = '" + slctmove[i].ToWarehouse +"' style='width:200px;' class='form-control' ></td>";
		   markup = markup + "<td name ='nodeId'><input type='text' style='width:200px;' value = '" + slctmove[i].NodeId +"' class='form-control' name='nodeid'></td>";
		   markup = markup + "<td name ='nodeName'><input type='text' class='form-control' value = '" + slctmove[i].NodeName +"' style='width:200px;' name='nodename' ></td>";
		   markup = markup + "<td name ='grId'><input type='text' ondrop ='return false;' name='itmGR' value = '" + slctmove[i].GrId +"' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
		   markup = markup + "<td name ='prId'><input type='text' ondrop ='return false;' name='itmPR' value = '" + slctmove[i].PrId +"' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
		   markup = markup + "<td name ='poId'><input type='text' ondrop ='return false;' name='itmPO' value = '" + slctmove[i].PoId +"' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
		   markup = markup + "<td name ='cipId'><input type='text' ondrop ='return false;' name='itmCIP' value = '" + slctmove[i].CipId +"' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
		   markup = markup + "<td name ='status'><input type='text'value='0' name='status' style='width:200px;' value = '" + slctmove[i].Status +"' class='form-control' readonly></td>";
		   markup = markup + "<td name='reconciled' style='text-align:center; vertical-align: middle;'>"+checkbox+"</td>";
		   markup = markup + "<td name='barcodescanned' style='text-align:center; vertical-align: middle;'>"+checkbox2+"</td>";
		   markup = markup + "<td name ='wodId'><input type='text' name='wodid' class='form-control' style='width:200px;' value='0'readonly></td>";
		   markup = markup + "<td name='serialNo'><input type='text'  style='width:200px;' value='" + slctmove[i].serialNo +"'hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>";		

			// append des boq table after move
	       $("#bisotab2 > tbody").append(markup);
	       
			// get row count after adding row
	        rowcountWOD =  $("#bisotab2 >tbody tr").length;
	  		console.log("rowcount Move Source:" +rowcountWOD);

	  		// AutoComplete
	  		autoComplete(rowcountWOD,"bisotab2");
			// calcute the sum of item qty in des boq table
			sumQtyWod=sumQtyWod+parseFloat(slctmove[i].Qty);
 			console.log("for loop Movinggg sum: "+sumQtyWod);
		

	 }$('#wodtotqty').val(sumQtyWod);

		}

// AutoComplete FCT
function autoComplete(rIndex,tableID){
							
		  	var ctx=getContextPath();
		
			  		console.log("AUTOCOMPLETE THE ROW COUNT "+rIndex);		
					console.log("tableID"+tableID);	

									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmCode"]').autocomplete({
								    	    source: function(request, response,event, ui) {
									    	    
									             $.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",
									                 
									                 url: ctx+'/getItemCode',
									                 data: {
														requestValue : request.term
														
													 },
									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.ListItemDetails);
									                     }
									                 },
									                 error: function(result) {
									                     alert("Error");
									                 }
									             });
									           
									        }, minLength:0, maxShowItems: 4, scroll:true,
											select: function(event, ui) {
												
												this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
												$(this).parents("tr").find('input[name ="itmmodel"]').val(ui.item[2]);
												$(this).parents("tr").find('input[name ="itmpartnumber"]').val(ui.item[3]);
												return false;
											}
										}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
											var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
                
											item[0] + "</span><br><span class='desc'>" +
											item[1] + "</span><span class='desc'>";
											if(item[2] != '-')
												appendString += ","+item[2] + "</span><span class='desc'>";
											if(item[3] != '-')
												appendString += ","+item[3] + "</span><span class='desc'>";
											if(item[4] != '-')
												appendString += ","+item[4];

											appendString += "</span></div>";
											return $("<li class='each'>").append(appendString).appendTo(ul);
									};
									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmCode"]').focus(function(){
										if (this.value == ""){
											$(this).autocomplete("search");
							   	        }
									});
							   


										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmmodel"]').autocomplete({
								    	    source: function(request, response) {

									             $.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",									              
									                 url:  ctx+'/getModel',
									                 data: {
														requestValue : request.term
													 },
									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.ListModels);
									                     }
									                 },
									                 error: function(result) {
									                     alert("Error");
									                 }
									             });
									        }, minLength:0, maxShowItems: 4, scroll:true,
											select: function(event, ui) {

												this.value = (ui.item ? ui.item[0] : '');
												$(this).parents("tr").find('input[name = "itmCode"]').val(ui.item[1]+":"+ui.item[2]);
												$(this).parents("tr").find('input[name ="itmpartnumber"]').val(ui.item[3]);
											
												return false;
												
											}
										}).autocomplete("instance")._renderItem = function(ul, item) {
											var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
					
											item[0] + "</span><br><span class='desc'>" +
											item[1] + "</span><span class='desc'>"+"," +
											item[2] + "</span><span class='desc'>";
											if(item[3] != '-')
												appendString += ","+item[3] + "</span><span class='desc'>";

											appendString += "</span></div>";
											return $("<li class='each'>").append(appendString).appendTo(ul);
									};

									
									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmmodel"]').focus(function(){
										if (this.value == ""){
											$(this).autocomplete("search");
							   	        }
									});
								

										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmpartnumber"]').autocomplete({
								    	    source: function(request, response) {
							        			 
									             $.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",
									                 url:  ctx+'/getPartNo',
									                 data: {
									                	requestValue : request.term

													 },
									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.ListPartNos);
									                     }
									                 },
									                 error: function(result) {
									                     alert("Error");
									                 }
									             });
									        }, minLength:0, maxShowItems: 40, scroll:true,
											select: function(event, ui) {
												
												this.value = (ui.item ? ui.item[0] : '');
												$(this).parents("tr").find('input[name ="itmCode"]').val(ui.item[1]+":"+ui.item[2]);
												$(this).parents("tr").find('input[name ="itmmodel"]').val(ui.item[3]);
												return false;
											}
										}).autocomplete("instance")._renderItem = function(ul, item) {
											var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
											item[0] + "</span><br><span class='desc'>" +
											item[1] + "</span><span class='desc'>" +","+ 
											item[2] + "</span><span class='desc'>";
											if(item[3] != '-')
												appendString += ","+item[3] + "</span><span class='desc'>";

											appendString += "</span></div>";
											return $("<li class='each'>").append(appendString).appendTo(ul);
									};
									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmpartnumber"]').focus(function(){
										if (this.value == ""){
											$(this).autocomplete("search");
							   	        }
									});
						 

									
										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmFWH"]').autocomplete({
								    	    source: function(request, response, event, ui) {
								    	    	 $.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",
									                 url:  ctx+'/GetAllWarehouse',
									                 data: {
															"warehouseName" : request.term,
													 },
									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.globalList);
									                         console.log("the data isss:"+data.globalList);
									                     }
									                 },
									                 error: function(result) {
									                     alert("Error");
									                 }
									             });
									         }, minLength:0, maxShowItems: 40, scroll:true,
											select: function(event, ui) {
												this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
												return false;
											}
										}).autocomplete("instance")._renderItem = function(ul, item) {
								    	return $('<li class="each"></li>').data( "item.autocomplete", item )
								    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
							                   item[0] + '</span><br><span class="desc">' +
							                   item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
									};
									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmFWH"]').focus(function(){
										if (this.value == ""){
											$(this).autocomplete("search");
							  	        }
									});

										
											
											$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmTWH"]').autocomplete({
									    	    source: function(request, response, event, ui) {
									    	    	 $.ajax({
										                 type: "GET",
										                 contentType: "application/json; charset=utf-8",
										                 url:  ctx+'/GetAllWarehouse',
										                 data: {
																"warehouseName" : request.term,
														 },
										                 dataType: "json",
										                 success: function (data) {
										                     if (data != null) {
										                         response(data.globalList);
										                     }
										                 },
										                 error: function(result) {
										                     alert("Error");
										                 }
										             });
										         }, minLength:0, maxShowItems: 40, scroll:true,
												select: function(event, ui) {
													this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
													return false;
												}
											}).autocomplete("instance")._renderItem = function(ul, item) {
									    	return $('<li class="each"></li>').data( "item.autocomplete", item )
									    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								                   item[0] + '</span><br><span class="desc">' +
								                   item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
										};
										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmTWH"]').focus(function(){
											if (this.value == ""){
												$(this).autocomplete("search");
								  	        }
										});

										
							
									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name="nodeid"]').autocomplete({
											source : function(request, response,event,ui){
										
												var RowIndex = document.getElementById('RowIndex').value;						
												var nodenameArray = getAllNodeNames();				
												var NodeName=nodenameArray[RowIndex];
												
												$.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",
									                 url:  ctx+'/GetNodeIdBOQ',
									                 data: {
									                	 NodeId : request.term,
													    NodeName : NodeName
													 },
									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.ListNodeId);
									                         
									                     }
									                 },
									                 error: function(result) {
									                     alert("Error");
									                 }
									             });
												
												
												}, minLength:0, maxShowItems: 40, scroll:true,

												
												select: function(event, ui) {
													this.value = (ui.item ? ui.item[0] : '');
													$(this).parents("tr").find('input[name ="nodename"]').val(ui.item[1]);
															return false;
														}
												
											}).autocomplete("instance")._renderItem = function(ul, item) {
										    	return $('<li class="each"></li>').data( "item.autocomplete", item )
								    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
							                   item[0] + '</span><br><span class="desc">').appendTo(ul);
										    	
										};
										
										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="nodeid"]').focus(function()
												{
													if(this.value==""){
														$(this).autocomplete("search");
													}
													
													});
					
									
									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="nodename"]').autocomplete({
											source : function(request, response,event,ui){

												var RowIndex = document.getElementById('RowIndex').value;
												var nodeidArray=getAllNodeIds();			
												var NodeId=nodeidArray[RowIndex];
																		
												$.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",
									                 url:  ctx+'/GetNodeNameBOQ',
									                 data: {
									                	 NodeName : request.term,
													    NodeId : NodeId
													 },
									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.ListNodeName);
									                         
									                     }
									                 },
									                 error: function(result) {
									                     alert("Error");
									                 }
									             });
												}, minLength:0, maxShowItems: 40, scroll:true,
												
												select: function(event, ui) {
													this.value = (ui.item ? ui.item[0] : '');
													$(this).parents("tr").find('input[name ="nodeid"]').val(ui.item[1]);
															return false;																	
														}
											}).autocomplete("instance")._renderItem = function(ul, item) {
										    	return $('<li class="each"></li>').data( "item.autocomplete", item )
								    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
							                   item[0] + '</span><br><span class="desc">').appendTo(ul);
										};

									$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="nodename"]').focus(function()
												{
													if(this.value==""){
														$(this).autocomplete("search");
													}
													
												});


										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmGR"]').autocomplete({
									    	    source: function(request, response, event, ui) {

									    	    	var RowIndex = document.getElementById('RowIndex').value;
										        	console.log("the row index is "+RowIndex);
										        	
										        	var newArray =f1();
									    	    	 console.log("Array is "+newArray[RowIndex]);
									    	    	 
								        			 Item_code = newArray[RowIndex];
								        			 console.log("the item code is "+Item_code);		 
								 
								                     if(Item_code != null){
								                    	 Item_code = Item_code.split(":");
									        			 Item_code = Item_code[0];
									        			 console.log("the item code after split is "+Item_code);
									                     }     
										    	    
									    	    	 $.ajax({
										                 type: "GET",
										                 contentType: "application/json; charset=utf-8",
										                 url:  ctx+'/GetAllGoodsReceivedItem',
										                 data: {
										                	 GR : request.term,
														    Item_code : Item_code,
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
													this.value = (ui.item ? ui.item[0] + ";" + ui.item[2] : '');
													return false;
												}
											}).autocomplete("instance")._renderItem = function(ul, item) {
									    	return $('<li class="each"></li>').data( "item.autocomplete", item )
									    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								                   item[0] + '</span><br><span class="desc">' +
								                   item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
										};
										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmGR"]').focus(function(){
											if (this.value == ""){
											$(this).autocomplete("search");
								  	        }
										});
									
									


										
										$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmPR"]').autocomplete({
									    	    source: function(request, response, event, ui) {

									    	    	var RowIndex = document.getElementById('RowIndex').value;
										        	console.log("the row index is "+RowIndex);
										        	
										        	var newArray =f1();
									    	    	 console.log("Array is "+newArray[RowIndex]);
									    	    	 
								        			 Item_code = newArray[RowIndex];
								        			 console.log("the item code is "+Item_code);
							                         //console.log("The size is "+Item_code.length);
								 
								 
								                     if(Item_code != null){
								                    	 Item_code = Item_code.split(":");
									        			 Item_code = Item_code[0];
									        			 console.log("the item code after split is "+Item_code);
									                     }     
										    	    
									    	    	 $.ajax({
										                 type: "GET",
										                 contentType: "application/json; charset=utf-8",
										                 url:  ctx+'/GetAllPurchaseRequestsItem',
										                 data: {
										                	 PR : request.term,
														    Item_code : Item_code,
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
													this.value = (ui.item ? ui.item[0] + ";" + ui.item[2] + ";" + ui.item[3] : '');
													return false;
												}
											}).autocomplete("instance")._renderItem = function(ul, item) {
									    	return $('<li class="each"></li>').data( "item.autocomplete", item )
									    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
								                   item[0] + '</span><br><span class="desc">' +
								                   item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
										};
										$(this).focus(function(){
											if (this.value == ""){
											$(this).autocomplete("search");
								  	        }
										});

											
											$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmPO"]').autocomplete({
										    	    source: function(request, response, event, ui) {

										    	    	var RowIndex = document.getElementById('RowIndex').value;
											        	console.log("the row index is "+RowIndex);
											        	
											        	var newArray =f1();
										    	    	 console.log("Array is "+newArray[RowIndex]);
										    	    	 
									        			 Item_code = newArray[RowIndex];
									        			 console.log("the item code is "+Item_code);
								                         //console.log("The size is "+Item_code.length);
									 
									        			 if(Item_code != null){
									                    	 Item_code = Item_code.split(":");
										        			 Item_code = Item_code[0];
									                           
								                            
										                     }

										    	    	 $.ajax({
											                 type: "GET",
											                 contentType: "application/json; charset=utf-8",
											                 url:  ctx+'/GetAllPO',
											                 data: {
											                	 PO : request.term,
															    Item_code : Item_code,
															 },
											                 dataType: "json",
											                 success: function (data) {
											                     if (data != null) {
											                         response(data.ListPos1);
											                     }
											                 },
											                 error: function(result) {
											                     alert("Error");
											                 }
											             });
											         }, minLength:0, maxShowItems: 40, scroll:true,
													select: function(event, ui) {
														this.value=(ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2] : '');
														//this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
														return false;
													}
												}).autocomplete("instance")._renderItem = function(ul, item) {
										    	return $('<li class="each"></li>').data( "item.autocomplete", item )
										    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
									                   item[0] + '</span><br><span class="desc">' +
									                   item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
											};
											$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmPO"]').focus(function(){
												if (this.value == ""){
												$(this).autocomplete("search");
									  	        }
											});

											
											$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmCIP"]').autocomplete({
										    	    source: function(request, response, event, ui) {

										    	    	var RowIndex = document.getElementById('RowIndex').value;
											        	console.log("the row index is "+RowIndex);
											        	
											        	var newArray =f1();
										    	    	 console.log("Array is "+newArray[RowIndex]);
										    	    	 
									        			 Item_code = newArray[RowIndex];
									        			 console.log("the item code is "+Item_code);
								                         //console.log("The size is "+Item_code.length);
									 
									        			 if(Item_code != null){
									                    	 Item_code = Item_code.split(":");
										        			 Item_code = Item_code[0];
									                           
								                            
										                     }

											    	    
										    	    	 $.ajax({
											                 type: "GET",
											                 contentType: "application/json; charset=utf-8",
											                 url:  ctx+'/GetAllCIPItem',
											                 data: {
											                	 CIP : request.term,
															    Item_code : Item_code,
															 },
											                 dataType: "json",
											                 success: function (data) {
											                     if (data != null) {
											                         response(data.ListCIP);
											                     }
											                 },
											                 error: function(result) {
											                     alert("Error");
											                 }
											             });
											         }, minLength:0, maxShowItems: 40, scroll:true,
													select: function(event, ui) {
														this.value=(ui.item ? ui.item[0] + ";" + ui.item[1] + ";" + ui.item[2] : '');
														return false;
													}
												}).autocomplete("instance")._renderItem = function(ul, item) {
										    	return $('<li class="each"></li>').data( "item.autocomplete", item )
										    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
									                   item[0] + '</span><br><span class="desc">' +
									                   item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
											};
											$('#'+tableID+' > tbody > tr').eq(rIndex-1).find('input[name ="itmCIP"]').focus(function(){
												if (this.value == ""){
												$(this).autocomplete("search");
									  	        }
											});
				

							}
							
// fct send input row from boq to popup
function getValuesForRow(ri){
	var w=tablesgn;
	if( w =="bisotab"){
			console.log("//-- SEND INPUT VALUES FROM SOURCE BOQ TABLE TO POPUP --//");
	}
	else if ( w =="bisotab2"){
			console.log("//-- SEND INPUT VALUES FROM DESTINATION BOQ TABLE TO POPUP --//");
	}
	//Send input values from Boq table  to popup
	$('#popupItemCode').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="itemCode"]').children('input').val());
	$('#popupItemModel') .val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="itemModel"]').children('input').val()); 
	$('#popupItemPartno').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="itemPartNumber"]').children('input').val());
	$('#popupBarcode').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="poBarCode"]').children('input').val());
	$('#popupQty').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="qty"]').children('input').val());
	$('#popupCurrentQty').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="currentQty"]').children('input').val());
	$('#popupFWH').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="itemFromWare"]').children('input').val());
	$('#popupTWH').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="itemToWare"]').children('input').val());
	$('#popupNodeId').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="nodeId"]').children('input').val());
	$('#popupNodeName').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="nodeName"]').children('input').val());
	$('#popupGrId').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="grId"]').children('input').val());
	$('#popupPrId').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="prId"]').children('input').val());
	$('#popupPoId').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="arId"]').children('input').val());
	$('#popupCipId').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="cipId"]').children('input').val());
	$('#popupStatus').val($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="status"]').children('input').val());
	
	if ($("#"+w+" >tbody").find("tr").eq(ri).find('td[name="barcodescanned"]').children('input').is(':checked')){
		$('#popupBarcode').val()=='1';	
		document.getElementById("popupBarcode").checked = true;			
		//console.log("BOQ BARCODE ////// checked");					  
		}
	else{
		 $('#popupBarcode').val()=='0';
		document.getElementById("popupBarcode").checked = false;	
		//console.log("BOQ BARCODE ///// NOT checked");
		}
				
	
	// Send hidden concatenated values of serial/Model/PartNum/Reconciled from Boq to Serial table in popup
	
		console.log("The Table id is : "+w);
		var hiddenSerialModelPartNoRec = $("#"+w+" >tbody").find("tr").eq(ri).find('td[name="serialNo"]').children('input').val() ;
		
		console.log("serialModelPartNo from boq to popup:" +hiddenSerialModelPartNoRec);
			  
		var concatSerModPartRec = hiddenSerialModelPartNoRec;
		var firstSplit =' ';
		var serModPartNumRec ='';
				 	    	 	
	//Split rows
		if (concatSerModPartRec != null){
		firstSplit = concatSerModPartRec.split(',;');
		console.log("Array length is"+ firstSplit.length-1);
			console.log("System in to FIRST split for loop");
			for (var n = 0; n < firstSplit.length-1; n++) {
					console.log("Going to split")
		console.log("First Split:" +firstSplit[n]);
		}
					console.log("System out for loop");
		}
				 	    	 	
	// Clear serialNo table in each new popup
	$("#serialNoTable > tbody").html(" ");
	
	console.log("System in to SECOND split for loop");

	// Split to get SerialNo, ItemModel, ItemPartNo, Reconciled of each row 
	for (var z = 0; z < firstSplit.length-1; z++) {
		console.log("LOOP NUM : "+z);
		serModPartNumRec = firstSplit[z].split(',');
		var idsn =serModPartNumRec[0];
		var serialNum = serModPartNumRec[1];
		var itmModel = serModPartNumRec[2];
		var itemPartNum = serModPartNumRec[3];
		var recosrc = serModPartNumRec[4];
		
		console.log("The Reconciled Value is :"+recosrc);
		var checkRec;
		if(recosrc=='1'){
			console.log("This "+serialNum+" IS Reconciled");
			checkRec ="<input type='checkbox' name='reconciled' style='position:relative;left:25px;top:10px;width:20px;height:20px;' checked='true' >";
		}
		else{
			console.log("This "+serialNum+" NOT Reconciled");
			checkRec ="<input type='checkbox' name='reconciled' style='position:relative;left:25px;top:10px;width:20px;height:20px;'>";

		}
			var itemRowSerial = "<tr>";
			itemRowSerial = itemRowSerial + "<td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>";
			itemRowSerial = itemRowSerial + "<td name='serialNumber'><input name='serialNumber' type='text' value='" + serialNum +"'  style='width:200px;position:relative;' class='form-control text-input'/></td>";
			itemRowSerial = itemRowSerial + "<td name='itmModel' style='width:200px'><input name='itmModel'   type='text' value='" + itmModel + "'  style='width:200px;position:relative;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial = itemRowSerial + "<td name='itemPart' style='width:200px'><input name='itemPart'  type='text' value='" + itemPartNum + "'  style='width:200px;position:relative;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
			itemRowSerial = itemRowSerial + "<td name='reconciled'>"+checkRec+"</td>";
			itemRowSerial = itemRowSerial + "<td style='visibility:hidden;width:0px;border-width: 0px;' name='idsn'><input name='idsn' type='text' value='" + idsn +"' hidden class='form-control text-input'/></td>";

			itemRowSerial = itemRowSerial + "</tr>";
			$("#serialNoTable > tbody").append(itemRowSerial);
			}
			console.log("System OUT SECOND for loop");

			
			console.log("//-- END SENDING TO POPUP --//");
}

// fct create new row boq table
function createNewRow(){	
	if (tablesgn=="bisotab"){
		
		var markup = "<tr><td><input type='checkbox' name='record' style='width: 14px;height: 13px;position:relative;left:6px;'><span class='dotStatus' id='dotStatus' style='position:relative;left:3px;'></span><button type='button' href='#' name='popUpMenu' onclick='openPop1(this)' class='btn btn-default'  style='margin-bottom:3px;'><i class='fas fa-desktop'></i></button></td>";
	   markup = markup + "<td name ='itemCode'><input type='text' name='itmCode'  style='width:400px;' class='form-control ui-widget ui-widget-content ui-corner-all'/></td>";
	   markup = markup + "<td name ='itemModel'><input type='text' name='itmmodel' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
	   markup = markup + "<td name ='itemPartNumber'><input type='text' name='itmpartnumber' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
	   markup = markup + "<td name ='currentQty'><input type='text' name='currentqty' style='width:200px;' class='form-control' value= 0></td>";
	   markup = markup + "<td name ='qty'><input type='text' name='qty' style='width:200px;' class='form-control' value= 0></td>";
	   markup = markup + "<td name ='itemFromWare'><input type='text' name='itmFWH' value='' style='width:200px;' class='form-control' ></td>";
	   markup = markup + "<td name ='itemToWare'><input type='text' name='itmTWH' value='' style='width:200px;' class='form-control' ></td>";
	   markup = markup + "<td name ='nodeId'><input type='text' style='width:200px;' class='form-control' name='nodeid'></td>";
	   markup = markup + "<td name ='nodeName'><input type='text' name='nodename' style='width:200px;' class='form-control' ></td>";
	   markup = markup + "<td name ='grId'><input type='text' ondrop ='return false;' name='itmGR' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='prId'><input type='text' ondrop ='return false;' name='itmPR' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='poId'><input type='text' ondrop ='return false;' name='itmPO' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='cipId'><input type='text' ondrop ='return false;' name='itmCIP' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='status'><input type='text'value='0' name='status' class='form-control' style='width:200px;' readonly></td>";
	   markup = markup + "<td name='reconciled' style='text-align:center; vertical-align: middle;'><input type='checkbox' name='reconciled' style='height:20px; width:20px;' ></td>";
	   markup = markup + "<td name='barcodescanned' style='text-align:center; vertical-align: middle;'><input type='checkbox' name='barcodescanned' style='height:20px; width:20px;' ></td>";
	   markup = markup + "<td name ='wosId'><input type='text' name='wosid' class='form-control' style='width:200px;' value='0'readonly></td>";
	   markup =markup + "<td style='visibility:hidden;width:0px;border-width: 0px;' name='serialNo'><input type='text' style='width:200px;' hidden  value =' ' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>";		
				
		return markup;
	}
	else if (tablesgn=="bisotab2"){
		
		var markup = "<tr><td><input type='checkbox' name='record' style='width: 14px;height: 13px;position:relative;left:6px;'><span class='dotStatus' id='dotStatus' style='position:relative;left:3px;'></span><button type='button' href='#' name='popUpMenu' onclick='openPop2(this)' class='btn btn-default'  style='margin-bottom:3px;'><i class='fas fa-desktop'></i></button></td>";
	   markup = markup + "<td name ='itemCode'><input type='text' name='itmCode'  style='width:400px;' class='form-control ui-widget ui-widget-content ui-corner-all'/></td>";
	   markup = markup + "<td name ='itemModel'><input type='text' name='itmmodel' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
	   markup = markup + "<td name ='itemPartNumber'><input type='text' name='itmpartnumber' class='form-control ui-widget ui-widget-content ui-corner-all' style='width:250px;'></td>";
	   markup = markup + "<td name ='currentQty'><input type='text' name='currentqty' style='width:200px;' class='form-control' value= 0></td>";
	   markup = markup + "<td name ='qty'><input type='text' name='qty' style='width:200px;' class='form-control' value= 0></td>";
	   markup = markup + "<td name ='itemFromWare'><input type='text' name='itmFWH' value='' style='width:200px;' class='form-control' ></td>";
	   markup = markup + "<td name ='itemToWare'><input type='text' name='itmTWH' value='' style='width:200px;' class='form-control' ></td>";
	   markup = markup + "<td name ='nodeId'><input type='text' style='width:200px;' class='form-control' name='nodeid'></td>";
	   markup = markup + "<td name ='nodeName'><input type='text' name='nodename' style='width:200px;' class='form-control' ></td>";
	   markup = markup + "<td name ='grId'><input type='text' ondrop ='return false;' name='itmGR' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='prId'><input type='text' ondrop ='return false;' name='itmPR' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='poId'><input type='text' ondrop ='return false;' name='itmPO' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='cipId'><input type='text' ondrop ='return false;' name='itmCIP' value='' style='width:250px;' class='form-control ui-widget ui-widget-content ui-corner-all'></td>";
	   markup = markup + "<td name ='status'><input type='text'value='0' name='status' class='form-control' style='width:200px;' readonly></td>";
	   markup = markup + "<td name='reconciled' style='text-align:center; vertical-align: middle;'><input type='checkbox' name='reconciled' style='height:20px; width:20px;' ></td>";
	   markup = markup + "<td name='barcodescanned' style='text-align:center; vertical-align: middle;'><input type='checkbox' name='barcodescanned' style='height:20px; width:20px;' ></td>";
	   markup = markup + "<td name ='wodId'><input type='text' name='wodid' class='form-control' style='width:200px;' value='0'readonly></td>";
	   markup =markup + "<td style='visibility:hidden;width:0px;border-width: 0px;' name='serialNo'><input type='text' style='width:200px;' hidden  value =' ' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>";		
				
		return markup;
	}

}

	function sendToBoq(){
		
	if( tablesgn =="bisotab"){
			console.log("//-- SEND VALUES FROM POPUP TO SOURCE BOQ TABLE --//");
	}
	else if ( tablesgn =="bisotab2"){
			console.log("//-- SEND VALUES FROM POPUP TO DESTINATION BOQ TABLE --//");
	}
	 
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="itemCode"]').children('input').val($('#popupItemCode').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="itemPartNumber"]').children('input').val($('#popupItemPartno').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="qty"]').children('input').val($('#popupQty').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="currentQty"]').children('input').val($('#popupCurrentQty').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="itemFromWare"]').children('input').val($('#popupFWH').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="itemToWare"]').children('input').val($('#popupTWH').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="nodeId"]').children('input').val($('#popupNodeId').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="nodeName"]').children('input').val($('#popupNodeName').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="grId"]').children('input').val($('#popupGrId').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="prId"]').children('input').val($('#popupPrId').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="poId"]').children('input').val($('#popupPoId').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="cipId"]').children('input').val($('#popupCipId').val());
	 $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="status"]').children('input').val($('#popupStatus').val());
	
	 if ($("#popupBarcode").is(':checked')){
	                 
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="barcodescanned"]').children('input').val('1');	
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="barcodescanned"]').children('input').prop("checked",true);						
			console.log("the barcode in BOQ is checked");					  
		}
		else{
						  
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="barcodescanned"]').children('input').val('0');	
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="barcodescanned"]').children('input').prop("checked",false);	
			console.log("the barcode IN BOQ not checked");
		 }
	console.log("//-- END SEND VALUES TO BOQ --//");
	}
	
	
	function sendSMPRtoBOQ(){
		
		if( tablesgn =="bisotab"){
			console.log("//-- SEND SERIAL NO FROM POPUP TO SOURCE BOQ TABLE --//");
	}
	else if ( tablesgn =="bisotab2"){
			console.log("//-- SEND SERIAL NO FROM POPUP TO DESTINATION BOQ TABLE --//");
	}
		
	// Send concatenated Serial Table rows to boq table
	 var serialModelPartnumRec = "";
	 var serialNoLengthTable= $("#serialNoTable > tbody >tr").length;
		 
	console.log("Rows num of serialNo Table "+serialNoLengthTable);	
		
	 var CountserialRec = 0;
	 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){

 	// Remove the row where SerialNumber is empty
	var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
		if (serNum == '') {
			$(this).parents("tr").remove();
         }
	     else {
			var ids =$(this).parent().parent().children('td[name="idsn"]').children('input').val();
			console.log("idsn to bboq is:"+ids);
	        var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	        var itemMod = $(this).parent().parent().children('td[name="itmModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
			var rec;
			
			if ($(this).parent().parent().children('td[name="reconciled"]').children('input').is(':checked')){
					rec='1';
					CountserialRec = CountserialRec +1;       
					//console.log("THE SERIALNUM IS RECONCILED");					  
			}
			else{
					rec='0';  
					//console.log("THE SERIALNUM NOT RECONCILED");
		 		}
	    			//console.log("CountserialRec:" +CountserialRec);
	    			serialModelPartnumRec += ids + ','+ serNum +',' +itemMod +',' +itemPartnum + ','+ rec + ',' +';';
	      	}
	  	});

		if(serialNoLengthTable==CountserialRec){
	                 
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="reconciled"]').children('input').val('1');	
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="reconciled"]').children('input').prop("checked",true);						
			console.log("all reconciled");					  
		}
		else{
						  
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="reconciled"]').children('input').val('0');	
			$("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="reconciled"]').children('input').prop("checked",false);	
			console.log("not all reconciled");
		 
			
		}
		console.log("The CONCAT serialModelPartnumRec : " +serialModelPartnumRec);
		
	    $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input').val(serialModelPartnumRec);
	    console.log ("The CONCAT serialModelPartnumRec ROWS:" + $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input').val ());

		console.log("//-- END Sendng  SERIAL NUMBERS TO BOQ --//");
	}

 //Open popup fct 
function openPop1(element) { 
	openPop(element,"src");
	
}
function openPop2(element) { 
	openPop(element,"des");
	
}

function openPop3(element) {
	var buttonRowIndx = $(element).closest("tr");
	rowindxx = (buttonRowIndx[0].rowIndex - 1);
	
	getValuesValidRow(rowindxx);
	
	var element = document.getElementById("popupNbb");
    element.innerHTML = "Item # " +(rowindxx+1);

   }

 function getValuesValidRow(rowindxx){
		$("#ToNodeTable >tbody tr").empty();
		$("#FromNodeTable >tbody tr").empty();
			//var buttonRowIndx = $(element).closest("tr");
			//rowindxx = (buttonRowIndx[0].rowIndex - 1);
			console.log("rowindxx "+rowindxx);
		//*************************************************************************************************************************
			//Send input values from Boq table  to popup
			$('#popupvalItemCode').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="item"]').children('input').val());
			$('#popupvalItemModel') .val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="itemModel"]').children('input').val());
			$('#popupvalItemPartno').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="itemPartNb"]').children('input').val());
		
			$('#popupTransID').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="transID"]').children('input').val());
			$('#popupTransType').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="transType"]').children('input').val());
			$('#popupElementName').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="elementName"]').children('input').val());
			//$('#popupApprovalStatus').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="approvalStatus"]').children('input').val());
			$('#case').text($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="approvalStatus"]').children('input').val());//change
			$('#popupDNID').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="DnID"]').children('input').val());
			$('#popupDNItmID').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="DniID"]').children('input').val());
		
			$('#popupFromSlot').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="fromSlot"]').children('input').val());
			$('#popupToSlot').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="toSlot"]').children('input').val());
		
			var Fromsite=$("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="siteID"]').children('input').val();
			var arr =[];
			arr =Fromsite.split(":");
			var fromsiteId=arr[2];
			var fromwareName=arr[1];
			var fromwareId=arr[0]
			
			$('#popupFromSiteID').val(fromsiteId);
			$('#popupFromWareName').val(fromwareName);
			$('#popupFromWareID').val(fromwareId);
			
			var Tosite=$("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="tositeID"]').children('input').val();
			 arr=[];
			arr= Tosite.split(":");
			var tositeId=arr[2];
			var towareName=arr[1];
			var towareId=arr[0];
			
			$('#popupToSiteID').val(tositeId);
			$('#popupToWareName').val(towareName);
			$('#popupToWareD').val(towareId);
			
			$('#popupSN').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="toSN"]').children('input').val());
			$('#popupMAC').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="macAddress"]').children('input').val());
			$('#popupFAR').val($("#wotable >tbody").find("tr").eq(rowindxx).find('td[name="FarId"]').children('input').val());
		
		
			 $("#WOValModal").modal("show");
	        // var element = document.getElementById("popupNbb");
	         //  element.innerHTML = "Item # " +(rowindxx+1);
				 
	        var toNode = JSON.parse($("#wotable > tbody").find("tr").eq(rowindxx).find('td[name="toNode"]').children('input').val());
		    populateNodeTable(toNode, "#ToNodeTable");
		    var fromNode = JSON.parse($("#wotable > tbody").find("tr").eq(rowindxx).find('td[name="fromNode"]').children('input').val());
		    populateNodeTable(fromNode, "#FromNodeTable");
	 
 }
 
 function nextValidRow(){
	// Get total number of rows in table
	var rowCount = $("#wotable >tbody tr").length;
	
	// intiate new row index   	 
	rowindxx++ ;
	var nextIndex = parseInt(rowindxx);
	
	// go to the next row when rowindxx does not exceed the row count	 
	if(rowindxx >= 0 && rowindxx < rowCount) {
		
	// get values from boq to popup	
	getValuesValidRow(nextIndex);
		
	//Update popup number in header 
	var element = document.getElementById("popupNbb");
	element.innerHTML = "Item # " +(nextIndex+1);

			 }
	else {
		$("#WOValModal").modal("hide");
		
	}
 }

// open popup Fct
 function openPop(element,target) {
	if(target=="src"){
		tablesgn="bisotab";
		tabletitle="Source";
	}
	else if(target=="des"){
		tablesgn="bisotab2";
		tabletitle="Destination";

	}
	
	console.log("the tablesgn:"+tablesgn);
	
	// intiate all Fcts in popup
 	document.getElementById("insertBelow").value=tablesgn;
	document.getElementById("insertAbove").value=tablesgn;
	document.getElementById("deleteBoqRow").value=tablesgn;
	document.getElementById("nextRow").value=tablesgn;
	
	// get the row index of clicked row element 
	var buttonRowIndx = $(element).closest("tr");
	rowindx = (buttonRowIndx[0].rowIndex - 1);
	console.log("row index:"+rowindx);
	
	console.log("The Row index of clicked button: " +rowindx);
	console.log("The POPUP clicked");   
	
	// get data from boq to popup        
	getValuesForRow(rowindx);
			
	var element = document.getElementById("popupNb");
    element.innerHTML = tabletitle+" Item # " +(parseInt(rowindx)+1);
    		
    $("#poModal").modal("show");
    		     	
     }// end open popup fct
     
  
 // Next Fct in popup
 function nextRow(tableId){
	
	// Get total number of rows in table
	var rowCount = $("#"+tableId+" >tbody tr").length;
	console.log("Row Count in BOQ:" +rowCount);
	console.log("tableId:" +tableId);
	
	// intiate new row index   	 
	rowindx++ ;
	var nextIndex = parseInt(rowindx);
	console.log("Next Index" +nextIndex);
	
	// go to the next row when rowindx does not exceed the row count	 
	if(rowindx >= 0 && rowindx < rowCount) {
	console.log("Welcome to Next function");
		
	// get values from boq to popup	
	getValuesForRow(nextIndex);
		
	//Update popup number in header 
	var element = document.getElementById("popupNb");
	element.innerHTML = tabletitle+" Item # " +nextIndex;

			 }
					 
	// Add new row when rowindx exceed the row count
	else if (rowindx >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
		console.log("the new is"+tableId);
		
		// increment the number of rows
		rowCount++;
		console.log("rowCount"+rowCount);
		
		// adding row to boq
		var markup1;
		markup1=createNewRow();
		
		// append the table
		$("#"+tableId+" > tbody").append(markup1);
		
		// autocomplete
		autoComplete(rowCount,tableId);
		
		// get values from boq to popup	
		getValuesForRow(nextIndex);
		               		
			//Update popup Nb in header 
			var element = document.getElementById("popupNb");
			element.innerHTML = tabletitle+"  Item # " +nextIndex;
			}	
  
 }// End next fct in popup  
 

   
 // Insert row below fct
 function insertRowBelow(tableId){

	console.log("Add NEW ROW using Insert Below");
	
	// adding row to boq
	var markup1=createNewRow();
	
	console.log("Previous Row of index: "+rowindx);
	
	// append the table
	$("#"+tableId+" > tbody tr").eq(rowindx).after(markup1);
	
	// intiate new index for insereted row below
	rowindx++;
	console.log("Insert Row of index: "+rowindx);
	
	// get rows count after insert below
	rowcount =  $("#"+tableId+" > tbody tr").length;
	console.log("rowcount :" +rowcount);
	
	// autocomplete for inserted row
	autoComplete(rowcount,tableId);
	
	var belowIndex = parseInt(rowindx);    
	console.log("belowIndex:" +belowIndex);
	getValuesForRow(belowIndex);
	
	//Update popup Nb in header 
	var element = document.getElementById("popupNb");
	element.innerHTML = tabletitle+" Item # " +parseInt(belowIndex);           	
 
 }
	// End insertRowBelow fct in popup   


 // Insert Row Above fct
 function insertRowAbove(tableId){
	
	console.log("Add NEW ROW using Insert Above");
	
	// adding row to boq
	var markup1=createNewRow();
	$("#"+tableId+" > tbody tr").eq(rowindx).before(markup1);
	
	// get number of rows after insert above
	rowcount =  $("#"+tableId+" > tbody tr").length;
	console.log("rowcount :" +rowcount);
	
	var targetrow=rowindx+1;
	console.log("target row: "+targetrow);
	
	// autocomplete for inserted row
	autoComplete(targetrow,tableId);

	console.log("The Acurate index of inserted row:" +rowindx);
	
	// get values from boq to popup
	getValuesForRow(rowindx);	
	            
	//Update popup Nb in header 
	var element = document.getElementById("popupNb");
	element.innerHTML = tabletitle+" Item # " +parseInt(rowindx);           	
			            
 }
	// End insertRowAbove fct in popup   

 // Delete row fct
 function deleteBoqRow(tableId) {
	console.log("RowIndx" +rowindx);
	console.log("RowIndx" +tableId);
	$("#"+tableId+" > tbody").find("tr").eq(rowindx).remove();
	
	// Get Nb of rows after delete 
	var rowCount = $("#"+tableId+" >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);
	 rowindx--;
	 
	 if (rowindx == 0 && rowCount ==0) {
	    var sumQty=0;
	    var sumTotalAt=0;
		$("#poModal").modal("hide");
		$('#ordtotqty').val(sumQty);
		$('#ordtotamnt').val(sumTotalAt);
 		
	}
  
	else if(rowindx >= 0 && rowindx < rowCount) {
	getValuesForRow(rowindx);
    	 		
			//Update popup Nb in header 
			var element = document.getElementById("popupNb");
			element.innerHTML = tabletitle+" Item # " +rowindx;  
}

    // Show previous record 
	else if (rowindx >= rowCount){

		rowindx--;
		getValuesForRow(rowindx);
			    
			//Update popup Nb in header 
			var element = document.getElementById("popupNb");
			element.innerHTML = tabletitle+" Item # " +rowindx;  
			}
 
 } // End delete fct
 
function checkSerial() {
	
	var serialModelPartnum = "";
 	$("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){

 	// Remove the row where SerialNumber is empty
	var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
		if (serNum == '') {
			$(this).parents("tr").remove();
         }
	     else {
			var ids = $(this).parent().parent().children('td[name="idsn"]').children('input').val();
	        var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	        var itemMod = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
	    
	    serialModelPartnum += ids +','+ serNum +',' +itemMod +',' +itemPartnum + ','+';';
	      }
	  	});

	    $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input').val(serialModelPartnum);
} // end check serial fct
	 
function populateNodeTable(node, tableSelector) {

    $(tableSelector + " > tbody").html("");

    if (node != null) {
        var nodeArray = (tableSelector === "#ToNodeTable") ? node.toNodeArray : node.fromNodeArray;

        $.each(nodeArray, function (i, value) {
            var nodeId = (value.NodeId === null) ? '' : value.NodeId;
            var NodeName = (value.NodeName === null) ? '' : value.NodeName;
            var NodeType = (value.NodeType === null) ? '' : value.NodeType;
            var Node = "<tr>";
            Node += "<td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>";
            Node += "<td name='NodeId'><input name='NodeId' type='text' value='" + nodeId + "' style='width:200px;position:relative;left:11px;' class='form-control text-input'/></td>";
            Node += "<td name='NodeName' style='width:200px'><input name='NodeName' type='text' value='" + NodeName + "' style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
            Node += "<td name='NodeType' style='width:200px'><input name='NodeType' type='text' value='" + NodeType + "' style='width:200px;position:relative;left:11px;' class='form-control text-input ui-widget ui-widget-content ui-corner-all' /> </td>";
            Node += "</tr>";

            $(tableSelector + " > tbody").append(Node);
        });
    }
}

 function addRowSerial(){
 
	checkSerial();
	rowcountSerial =  $("#serialNoTable >tbody tr").length;
	console.log("rowcountSerial:" +rowcountSerial);
	
    var ctx = getContextPath();

	var markup = "<tr><td><input type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
	+ "<td name='serialNumber'><input name='serialNumber'  class='form-control text-input' type='text' style='width:200px;position:relative;'/></td>"
	+ "<td name='itmModel' style='width:200px'> <input name='itmModel' id=" + "itmModel_" + rowcountSerial +" style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
	+ "<td name='itemPart' style='width:200px'> <input name='itemPart' id=" + "itemPart_" + rowcountSerial +" style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all'/></td>"
	+ "<td name='reconciled'><input type='checkbox' name='reconciled' style='position:relative;left:25px;top:10px;width:20px;height:20px;'></td>"
	+ "<td style='visibility:hidden;width:0px;border-width: 0px;' name='idsn'><input name='idsn' value='0' type='text' style='width:200px;' hidden class='form-control text-input'/></td></tr>";

	$("#serialNoTable > tbody").append(markup);
		
		$('#itemPart_'+rowcountSerial).autocomplete({
		source: function(request, response) {
		$.ajax({ 
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getPartNo',
			data: {
				requestValue : request.term
		},
			dataType: "json",
			success: function (data) {
					                 
			if (data != null) {
				response(data.ListPartNos);
			}
			},
			error: function(result) {
				console.log(222);
				                 
			}
			});
			}, minLength:0, maxShowItems: 40, scroll:true,
		
			select: function(event, ui) {
				this.value = (ui.item ? ui.item[0]  : '');
				document.getElementById("popupItemPartno").value= ui.item[0] ;
				document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
				document.getElementById("popupItemModel").value= ui.item[3] ;
				 $(this).parents("tr").find('input[name ="itmModel"]').val(ui.item[3]);
				return false;
			}
			}).autocomplete("instance")._renderItem = function(ul, item) {
				var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
				item[0] + "</span><br><span class='desc'>" +
				item[1] + "</span><span class='desc'>" +","+ 
				item[2] + "</span><span class='desc'>";
				if(item[3] != '-')
					appendString += ","+item[3] + "</span><span class='desc'>";

				appendString += "</span></div>";
				return $("<li class='each'>").append(appendString).appendTo(ul);
  		
	     };
		$('#itemPart_'+rowcountSerial).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
	
		});
	
	
    // Item Model autocomplete in 1st tab
    $('#itmModel_'+rowcountSerial ).autocomplete({
		source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url:ctx+'/getModel',
			data: {
				requestValue : request.term
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.ListModels);
				}
			},
			error: function(result) {
				console.log(222);
			}
		});
		}, minLength:0, maxShowItems: 4, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("popupItemModel").value= ui.item[0] ;
			document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
			document.getElementById("popupItemPartno").value= ui.item[3] ;
			$(this).parents("tr").find('input[name ="itemPart"]').val(ui.item[3]);
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
					
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>"+"," +
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
	  		
	};
			$('#itmModel_'+rowcountSerial ).focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
   	        }
	
		});
	 
 }
 

	
 $(function(){

var ctx = getContextPath();
 
 // Add new row in serialNo table using Enter key 
 $("#serialNoTable > tbody").keyup(function (event)  {
	if (event.keyCode == 13) {
		addRowSerial(); 
	}
 }); // End Add row using enter key 
 
 
 
 // Delete row in serialNo Table	
 $(".delete-row-serial").click(function () {
 slctDelSerialItem = [];
 
 $("#serialNoTable > tbody").find('input[name="record"]').each(function () {
 if ($(this).is(":checked")) {
	slctDelSerialItem.push($(this).parent().parent().children('td[name="serialNumber"]').children('input').val());
	console.log("The selected delete is " + slctDelSerialItem);
 }
 });
	console.log("The selected delete after is " + slctDelSerialItem);

	for (i = 0; i <= slctDelSerialItem.length; ++i) {
 	if (slctDelSerialItem.length == 0) {
    	alert(' Select Row(s) to Delete');
        return false;
     }
     }
	$("#serialNoTable > tbody").find('input[name="record"]').each(function () {
    	if ($(this).is(":checked")) {
       	$(this).parents("tr").remove();
       	}

	});
	slctss.push(slctDelSerialItem);
		
 }); // end delete row fct
 
 //Select all checkbox in serialNo table
 $('body').on('click', '#selectAllSerial', function  () {
	if ($(this).hasClass('allChecked')) {
		$('input[type="checkbox"]', '#serialNoTable').prop('checked', false);
	} 
	else {
		$('input[type="checkbox"]', '#serialNoTable').prop('checked', true);
	}
	
	$(this).toggleClass('allChecked');
	       				
 }) // end select checkbox fct 
 
 
 
    // Item Model autocomplete in 1st tab
	$("#popupItemModel").autocomplete({
	source: function(request, response) {
   	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/getModel',
		data: {
			requestValue : request.term
		              
	},
	dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.ListModels);
		}
	},
		error: function(result) {
			alert("Error");
		}
	});
	}, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0]  : '');
		document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
		document.getElementById("popupItemPartno").value= ui.item[3];												
		return false;
	}
	  }).autocomplete("instance")._renderItem = function(ul, item) {
		var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
					
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>"+"," +
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);

		};
		$("#popupItemModel").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}						
		});
   
   	
	//Item Code&name autocomplete
	$("#popupItemCode").autocomplete({
	source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: ctx+'/getItemCode',
			data: {
				requestValue : request.term
				
			},
			dataType: "json",
			success: function (data) {
				if (data != null) {
					response(data.ListItemDetails);
				}
			},
			error: function(result) {
				alert("Error");
			}
		});
		}, minLength:0, maxShowItems: 40, scroll:true,
		select: function(event, ui) {
			popupItemCode.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
			document.getElementById("popupItemModel").value= ui.item[2];
			document.getElementById("popupItemPartno").value= ui.item[3];
			return false;
		}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
                
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>";
			if(item[2] != '-')
				appendString += ","+item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";
			if(item[4] != '-')
				appendString += ","+item[4];

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
		};
		$("#popupItemCode").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}
		});		
	
	
	// ItemPartNo autocomplete in 1st tab
					
	$("#popupItemPartno").autocomplete({
	source: function(request, response) {

	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
	    url: ctx+'/getPartNo' ,
		data: {
        	requestValue : request.term
		},
		dataType: "json",
        	success: function (data) {
         		if (data != null) {
                	response(data.ListPartNos);
				}
			 },
			 error: function(result) {
              	alert("Error");
              }
         });
         
         }, minLength:0, maxShowItems: 40, scroll:true,	

		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			document.getElementById("popupItemCode").value= ui.item[1] + ":" + ui.item[2] ;
			document.getElementById("popupItemModel").value= ui.item[3];
			return false;
	    }
		}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                
			item[0] + "</span><br><span class='desc'>" +
			item[1] + "</span><span class='desc'>" +","+ 
			item[2] + "</span><span class='desc'>";
			if(item[3] != '-')
				appendString += ","+item[3] + "</span><span class='desc'>";

			appendString += "</span></div>";
			return $("<li class='each'>").append(appendString).appendTo(ul);
		};

	$("#popupItemPartno").focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
			}						
		});
	
   

	$("#popupFWH").autocomplete({
	source: function(request, response) {
   	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetAllWarehouse',
		data: {
			"warehouseName" : $("#popupFWH").val(),				 
	},
	dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.globalList);
			console.log('/*data is ;'+ data.globalList);
		}
	},
		error: function(result) {
			alert("Error");
		}
	});
	}, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, ui) {
	this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		return false;
	}
	  }).autocomplete("instance")._renderItem = function(ul, item) {
	  	return $("<li class='each'>")
	                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
				item[0] + '</span><br><span class="desc">' +
				item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
		};
		$("#popupFWH").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}						
		});
			    
	// To WareHouse autocomplete in 1st tab
	$("#popupTWH").autocomplete({
	source: function(request, response) {
   	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetAllWarehouse',
		data: {
			"warehouseName" : $("#popupTWH").val(),				 
	},
	dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.globalList);
			console.log('/*data is ;'+ data.globalList);
		}
	},
		error: function(result) {
			alert("Error");
		}
	});
	}, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, ui) {
	this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		return false;
	}
	  }).autocomplete("instance")._renderItem = function(ul, item) {
	  	return $("<li class='each'>")
	                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
				item[0] + '</span><br><span class="desc">' +
				item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
		};
		$("#popupTWH").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}						
		});
		
	// Node Id autocomplete in 1st tab
	$("#popupNodeId").autocomplete({
	source: function(request, response) {
   	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetNodeIdBOQ',
		data: {
			 "NodeId" : $("#popupNodeId").val(),
	},
	dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.ListNodeId);
			console.log('/*data is ;'+ data.ListNodeId);
		}
	},
		error: function(result) {
			alert("Error");
		}
	});
	}, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, ui) {
	this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		return false;
	}
	  }).autocomplete("instance")._renderItem = function(ul, item) {
	  	return $("<li class='each'>")
	                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
				item[0] + '</span><br><span class="desc">' +
				item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
		};
		$("#popupNodeId").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}						
		});
			// Node Name autocomplete in 1st tab
	$("#popupNodeName").autocomplete({
	source: function(request, response) {
   	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: ctx+'/GetNodeNameBOQ',
		data: {
			 "NodeName" : $("#popupNodeName").val(),
	},
	dataType: "json",
		success: function (data) {
		if (data != null) {
			response(data.ListNodeName);
			console.log('/*data is ;'+ data.ListNodeName);
		}
	},
		error: function(result) {
			alert("Error");
		}
	});
	}, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, ui) {
	this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		return false;
	}
	  }).autocomplete("instance")._renderItem = function(ul, item) {
	  	return $("<li class='each'>")
	                .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
				item[0] + '</span><br><span class="desc">' +
				item[1] +', '+ item[2] + "</span></div>").appendTo(ul);
		};
		$("#popupNodeName").focus(function(){
			if (this.value == ""){
				$(this).autocomplete("search");
			}						
		});
	// Resize and drag the popup
	$('.modal-content').resizable({
	handles: "e" ,
	
	});
	 
	$('.modal-dialog').draggable({
	handle: ".modal-header, .modal-footer"
	});
	   
	$('#poModal').on('show.bs.modal', function() {
	$(this).find('.modal-body').css({
	'max-height': '100%',
	});
	});
     
	//Reset the popup to original size after resizing 
	$('#poModal').on('hidden.bs.modal', function() {
	$(this).find('.modal-content').css({'width': '', 'height': ''});
	})
	 
	//Reset popup position after it has been dragged and closed
	$('body').on('hidden.bs.modal', function() {
	$('.modal-dialog').css({'top': '', 'left':''});
	})
			      
	// Minimize and Maximize fct for popup
	$(".modalMinimize").on("click", function(){
	$(".modal-body").slideToggle();
	$(".modal-footer").slideToggle();
	
	//Toggle between minimize/maximize icons
	var iconToChange = $('.icon-to-change');
		if(iconToChange.hasClass('fa-window-restore')){
     		iconToChange.removeClass('fa-window-restore')
    		            .addClass('fa-minus')
		}
		else{
     		iconToChange.addClass('fa-window-restore')
    		             .removeClass('fa-minus')
		}    		         
	}); // end minimize/maximize fct
    
 

// Send concatenated Serial Table rows to boq table on any change in td
  $('#serialNoTable').on('focusout', function() {
 
	var serialModelPartnumRec = "";
	 $("#serialNoTable > tbody >tr").find('input[name="record"]').each(function(){
 			var ids = $(this).parent().parent().children('td[name="idsn"]').children('input').val();
				console.log("IDSN IDSN IDSN :::::::::::::"+ ids);					  

 			var serNum = $(this).parent().parent().children('td[name="serialNumber"]').children('input').val();
	        var itemMod = $(this).parent().parent().children('td[name="itmModel"]').children('input').val();
	    	var itemPartnum = $(this).parent().parent().children('td[name="itemPart"]').children('input').val();
			var rec;	    
		if ($(this).parent().parent().children('td[name="reconciled"]').children('input').is(':checked')){
			rec='1';       
			//console.log("THE SERIALNUM IS RECONCILED");					  
		}
		else{
			rec='0';  
			//console.log("THE SERIALNUM NOT RECONCILED");
		 }
	    serialModelPartnumRec += ids +','+serNum +',' +itemMod +',' +itemPartnum + ','+rec+ ','+';';
	      
	  	});

	    $("#"+tablesgn+" >tbody").find("tr").eq(rowindx).find('td[name="serialNo"]').children('input').val(serialModelPartnumRec);

}); // end sending concatenated Serial Table rows

	    	
 	//Send input values from popup to boq when any popup input change
 $('#popupItemCode,#popupItemModel, #popupItemPartno, #popupQty,#popupCurrentQty,#popupFWH,#popupTWH,#popupNodeId,#popupNodeName,#popupGrId,#popupPrId,#popupPoId,#popupCipId,#popupStatus,#popupBarcode').on(' focusout ', function() {
	
		sendToBoq();
	    getTotalAT_SumQty();
	    
      }); // end sending in any popup imput vhange
	
	// Close popup function  				
   $("button[name='closePopup']").on("click", function(){
	  console.log("Welcome to close");
	  console.log("On close"+rowindx);
	  
		sendToBoq();
		sendSMPRtoBOQ();
	 		
	    $("#poModal").modal("hide");
	    getTotalAT_SumQty();
		 
 }); // end close popup

 
	       				 
	// Send input values from popup to boq table and close the popup using ESC 
	document.addEventListener('keydown', function(event){
    	if(event.key === "Escape"){
	// Send input values from popup to boq table
		sendToBoq();
	// Send concatenated Serial Table rows to boq table
		sendSMPRtoBOQ();
			
	    $("#poModal").modal("hide");
	    getTotalAT_SumQty();
	    //amountsUpdate();
		       						
    }
 });// end close fct using esc
    				
	// Previous function in popup
  $("button[name='previousRow']").on("click", function(){
        		        
   	if(rowindx > 0) {
   		console.log("Welcome to previous!");
		rowindx-- ;
		
   		var PrevIndex = parseInt(rowindx);
   		console.log("PrevIndex" +PrevIndex);

   		getValuesForRow(PrevIndex);

	    //Update popup Nb in header 
		var element = document.getElementById("popupNb");
		element.innerHTML = tabletitle+" Item # " +PrevIndex;
       		 }
       		         
	   	// Close popup on row 0 (end of prev fct)
       		   else if (rowindx == 0) {
       		    	$("#poModal").modal("hide");
       	      }
   	
   	});// end Previous function in popup  
  
//Previous function in validation popup
 $("button[name='valpreviousRow']").on("click", function(){
       		        
  	if(rowindxx > 0) {
		rowindxx-- ;
		
  		var PrevIndex = parseInt(rowindxx);

  		getValuesValidRow(PrevIndex);

	    //Update popup Nb in header 
		var element = document.getElementById("popupNbb");
		element.innerHTML = "Item # " +(PrevIndex+1);
      		 }
      		         
	   	// Close popup on row 0 (end of prev fct)
      		   else if (rowindxx == 0) {
      		    	$("#WOValModal").modal("hide");
      	      }
  	
  	});// end Previous function in validation popup  

  });

 